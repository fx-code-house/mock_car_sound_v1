package com.google.android.exoplayer2.source.dash;

import android.os.Handler;
import android.os.Message;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.metadata.emsg.EventMessageDecoder;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes.dex */
public final class PlayerEmsgHandler implements Handler.Callback {
    private static final int EMSG_MANIFEST_EXPIRED = 1;
    private final Allocator allocator;
    private boolean chunkLoadedCompletedSinceLastManifestRefreshRequest;
    private long expiredManifestPublishTimeUs;
    private boolean isWaitingForManifestRefresh;
    private DashManifest manifest;
    private final PlayerEmsgCallback playerEmsgCallback;
    private boolean released;
    private final TreeMap<Long, Long> manifestPublishTimeToExpiryTimeUs = new TreeMap<>();
    private final Handler handler = Util.createHandlerForCurrentLooper(this);
    private final EventMessageDecoder decoder = new EventMessageDecoder();

    public interface PlayerEmsgCallback {
        void onDashManifestPublishTimeExpired(long expiredManifestPublishTimeUs);

        void onDashManifestRefreshRequested();
    }

    public PlayerEmsgHandler(DashManifest manifest, PlayerEmsgCallback playerEmsgCallback, Allocator allocator) {
        this.manifest = manifest;
        this.playerEmsgCallback = playerEmsgCallback;
        this.allocator = allocator;
    }

    public void updateManifest(DashManifest newManifest) {
        this.isWaitingForManifestRefresh = false;
        this.expiredManifestPublishTimeUs = C.TIME_UNSET;
        this.manifest = newManifest;
        removePreviouslyExpiredManifestPublishTimeValues();
    }

    public PlayerTrackEmsgHandler newPlayerTrackEmsgHandler() {
        return new PlayerTrackEmsgHandler(this.allocator);
    }

    public void release() {
        this.released = true;
        this.handler.removeCallbacksAndMessages(null);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (this.released) {
            return true;
        }
        if (message.what != 1) {
            return false;
        }
        ManifestExpiryEventInfo manifestExpiryEventInfo = (ManifestExpiryEventInfo) message.obj;
        handleManifestExpiredMessage(manifestExpiryEventInfo.eventTimeUs, manifestExpiryEventInfo.manifestPublishTimeMsInEmsg);
        return true;
    }

    boolean maybeRefreshManifestBeforeLoadingNextChunk(long presentationPositionUs) {
        boolean z = false;
        if (!this.manifest.dynamic) {
            return false;
        }
        if (this.isWaitingForManifestRefresh) {
            return true;
        }
        Map.Entry<Long, Long> entryCeilingExpiryEntryForPublishTime = ceilingExpiryEntryForPublishTime(this.manifest.publishTimeMs);
        if (entryCeilingExpiryEntryForPublishTime != null && entryCeilingExpiryEntryForPublishTime.getValue().longValue() < presentationPositionUs) {
            this.expiredManifestPublishTimeUs = entryCeilingExpiryEntryForPublishTime.getKey().longValue();
            notifyManifestPublishTimeExpired();
            z = true;
        }
        if (z) {
            maybeNotifyDashManifestRefreshNeeded();
        }
        return z;
    }

    void onChunkLoadCompleted(Chunk chunk) {
        this.chunkLoadedCompletedSinceLastManifestRefreshRequest = true;
    }

    boolean onChunkLoadError(boolean isForwardSeek) {
        if (!this.manifest.dynamic) {
            return false;
        }
        if (this.isWaitingForManifestRefresh) {
            return true;
        }
        if (!isForwardSeek) {
            return false;
        }
        maybeNotifyDashManifestRefreshNeeded();
        return true;
    }

    private void handleManifestExpiredMessage(long eventTimeUs, long manifestPublishTimeMsInEmsg) {
        Long l = this.manifestPublishTimeToExpiryTimeUs.get(Long.valueOf(manifestPublishTimeMsInEmsg));
        if (l == null) {
            this.manifestPublishTimeToExpiryTimeUs.put(Long.valueOf(manifestPublishTimeMsInEmsg), Long.valueOf(eventTimeUs));
        } else if (l.longValue() > eventTimeUs) {
            this.manifestPublishTimeToExpiryTimeUs.put(Long.valueOf(manifestPublishTimeMsInEmsg), Long.valueOf(eventTimeUs));
        }
    }

    private Map.Entry<Long, Long> ceilingExpiryEntryForPublishTime(long publishTimeMs) {
        return this.manifestPublishTimeToExpiryTimeUs.ceilingEntry(Long.valueOf(publishTimeMs));
    }

    private void removePreviouslyExpiredManifestPublishTimeValues() {
        Iterator<Map.Entry<Long, Long>> it = this.manifestPublishTimeToExpiryTimeUs.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getKey().longValue() < this.manifest.publishTimeMs) {
                it.remove();
            }
        }
    }

    private void notifyManifestPublishTimeExpired() {
        this.playerEmsgCallback.onDashManifestPublishTimeExpired(this.expiredManifestPublishTimeUs);
    }

    private void maybeNotifyDashManifestRefreshNeeded() {
        if (this.chunkLoadedCompletedSinceLastManifestRefreshRequest) {
            this.isWaitingForManifestRefresh = true;
            this.chunkLoadedCompletedSinceLastManifestRefreshRequest = false;
            this.playerEmsgCallback.onDashManifestRefreshRequested();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getManifestPublishTimeMsInEmsg(EventMessage eventMessage) {
        try {
            return Util.parseXsDateTime(Util.fromUtf8Bytes(eventMessage.messageData));
        } catch (ParserException unused) {
            return C.TIME_UNSET;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPlayerEmsgEvent(String schemeIdUri, String value) {
        return "urn:mpeg:dash:event:2012".equals(schemeIdUri) && (IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(value) || "2".equals(value) || ExifInterface.GPS_MEASUREMENT_3D.equals(value));
    }

    public final class PlayerTrackEmsgHandler implements TrackOutput {
        private final SampleQueue sampleQueue;
        private final FormatHolder formatHolder = new FormatHolder();
        private final MetadataInputBuffer buffer = new MetadataInputBuffer();
        private long maxLoadedChunkEndTimeUs = C.TIME_UNSET;

        PlayerTrackEmsgHandler(Allocator allocator) {
            this.sampleQueue = SampleQueue.createWithoutDrm(allocator);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void format(Format format) {
            this.sampleQueue.format(format);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public int sampleData(DataReader input, int length, boolean allowEndOfInput, int sampleDataPart) throws IOException {
            return this.sampleQueue.sampleData(input, length, allowEndOfInput);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleData(ParsableByteArray data, int length, int sampleDataPart) {
            this.sampleQueue.sampleData(data, length);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleMetadata(long timeUs, int flags, int size, int offset, TrackOutput.CryptoData cryptoData) {
            this.sampleQueue.sampleMetadata(timeUs, flags, size, offset, cryptoData);
            parseAndDiscardSamples();
        }

        public boolean maybeRefreshManifestBeforeLoadingNextChunk(long presentationPositionUs) {
            return PlayerEmsgHandler.this.maybeRefreshManifestBeforeLoadingNextChunk(presentationPositionUs);
        }

        public void onChunkLoadCompleted(Chunk chunk) {
            if (this.maxLoadedChunkEndTimeUs == C.TIME_UNSET || chunk.endTimeUs > this.maxLoadedChunkEndTimeUs) {
                this.maxLoadedChunkEndTimeUs = chunk.endTimeUs;
            }
            PlayerEmsgHandler.this.onChunkLoadCompleted(chunk);
        }

        public boolean onChunkLoadError(Chunk chunk) {
            long j = this.maxLoadedChunkEndTimeUs;
            return PlayerEmsgHandler.this.onChunkLoadError(j != C.TIME_UNSET && j < chunk.startTimeUs);
        }

        public void release() {
            this.sampleQueue.release();
        }

        private void parseAndDiscardSamples() {
            while (this.sampleQueue.isReady(false)) {
                MetadataInputBuffer metadataInputBufferDequeueSample = dequeueSample();
                if (metadataInputBufferDequeueSample != null) {
                    long j = metadataInputBufferDequeueSample.timeUs;
                    Metadata metadataDecode = PlayerEmsgHandler.this.decoder.decode(metadataInputBufferDequeueSample);
                    if (metadataDecode != null) {
                        EventMessage eventMessage = (EventMessage) metadataDecode.get(0);
                        if (PlayerEmsgHandler.isPlayerEmsgEvent(eventMessage.schemeIdUri, eventMessage.value)) {
                            parsePlayerEmsgEvent(j, eventMessage);
                        }
                    }
                }
            }
            this.sampleQueue.discardToRead();
        }

        private MetadataInputBuffer dequeueSample() {
            this.buffer.clear();
            if (this.sampleQueue.read(this.formatHolder, this.buffer, 0, false) != -4) {
                return null;
            }
            this.buffer.flip();
            return this.buffer;
        }

        private void parsePlayerEmsgEvent(long eventTimeUs, EventMessage eventMessage) {
            long manifestPublishTimeMsInEmsg = PlayerEmsgHandler.getManifestPublishTimeMsInEmsg(eventMessage);
            if (manifestPublishTimeMsInEmsg == C.TIME_UNSET) {
                return;
            }
            onManifestExpiredMessageEncountered(eventTimeUs, manifestPublishTimeMsInEmsg);
        }

        private void onManifestExpiredMessageEncountered(long eventTimeUs, long manifestPublishTimeMsInEmsg) {
            PlayerEmsgHandler.this.handler.sendMessage(PlayerEmsgHandler.this.handler.obtainMessage(1, new ManifestExpiryEventInfo(eventTimeUs, manifestPublishTimeMsInEmsg)));
        }
    }

    private static final class ManifestExpiryEventInfo {
        public final long eventTimeUs;
        public final long manifestPublishTimeMsInEmsg;

        public ManifestExpiryEventInfo(long eventTimeUs, long manifestPublishTimeMsInEmsg) {
            this.eventTimeUs = eventTimeUs;
            this.manifestPublishTimeMsInEmsg = manifestPublishTimeMsInEmsg;
        }
    }
}
