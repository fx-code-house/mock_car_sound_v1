package com.google.android.exoplayer2.source;

import android.net.Uri;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class SilenceMediaSource extends BaseMediaSource {
    private static final int CHANNEL_COUNT = 2;
    private static final Format FORMAT;
    public static final String MEDIA_ID = "SilenceMediaSource";
    private static final MediaItem MEDIA_ITEM;
    private static final int PCM_ENCODING = 2;
    private static final int SAMPLE_RATE_HZ = 44100;
    private static final byte[] SILENCE_SAMPLE;
    private final long durationUs;
    private final MediaItem mediaItem;

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() {
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
    }

    public static final class Factory {
        private long durationUs;
        private Object tag;

        public Factory setDurationUs(long durationUs) {
            this.durationUs = durationUs;
            return this;
        }

        public Factory setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        public SilenceMediaSource createMediaSource() {
            Assertions.checkState(this.durationUs > 0);
            return new SilenceMediaSource(this.durationUs, SilenceMediaSource.MEDIA_ITEM.buildUpon().setTag(this.tag).build());
        }
    }

    static {
        Format formatBuild = new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_RAW).setChannelCount(2).setSampleRate(SAMPLE_RATE_HZ).setPcmEncoding(2).build();
        FORMAT = formatBuild;
        MEDIA_ITEM = new MediaItem.Builder().setMediaId(MEDIA_ID).setUri(Uri.EMPTY).setMimeType(formatBuild.sampleMimeType).build();
        SILENCE_SAMPLE = new byte[Util.getPcmFrameSize(2, 2) * 1024];
    }

    public SilenceMediaSource(long durationUs) {
        this(durationUs, MEDIA_ITEM);
    }

    private SilenceMediaSource(long durationUs, MediaItem mediaItem) {
        Assertions.checkArgument(durationUs >= 0);
        this.durationUs = durationUs;
        this.mediaItem = mediaItem;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(TransferListener mediaTransferListener) {
        refreshSourceInfo(new SinglePeriodTimeline(this.durationUs, true, false, false, (Object) null, this.mediaItem));
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        return new SilenceMediaPeriod(this.durationUs);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.mediaItem;
    }

    private static final class SilenceMediaPeriod implements MediaPeriod {
        private static final TrackGroupArray TRACKS = new TrackGroupArray(new TrackGroup(SilenceMediaSource.FORMAT));
        private final long durationUs;
        private final ArrayList<SampleStream> sampleStreams = new ArrayList<>();

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean continueLoading(long positionUs) {
            return false;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void discardBuffer(long positionUs, boolean toKeyframe) {
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getBufferedPositionUs() {
            return Long.MIN_VALUE;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getNextLoadPositionUs() {
            return Long.MIN_VALUE;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean isLoading() {
            return false;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void maybeThrowPrepareError() {
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long readDiscontinuity() {
            return C.TIME_UNSET;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public void reevaluateBuffer(long positionUs) {
        }

        public SilenceMediaPeriod(long durationUs) {
            this.durationUs = durationUs;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void prepare(MediaPeriod.Callback callback, long positionUs) {
            callback.onPrepared(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public TrackGroupArray getTrackGroups() {
            return TRACKS;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long selectTracks(ExoTrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
            long jConstrainSeekPosition = constrainSeekPosition(positionUs);
            for (int i = 0; i < selections.length; i++) {
                SampleStream sampleStream = streams[i];
                if (sampleStream != null && (selections[i] == null || !mayRetainStreamFlags[i])) {
                    this.sampleStreams.remove(sampleStream);
                    streams[i] = null;
                }
                if (streams[i] == null && selections[i] != null) {
                    SilenceSampleStream silenceSampleStream = new SilenceSampleStream(this.durationUs);
                    silenceSampleStream.seekTo(jConstrainSeekPosition);
                    this.sampleStreams.add(silenceSampleStream);
                    streams[i] = silenceSampleStream;
                    streamResetFlags[i] = true;
                }
            }
            return jConstrainSeekPosition;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long seekToUs(long positionUs) {
            long jConstrainSeekPosition = constrainSeekPosition(positionUs);
            for (int i = 0; i < this.sampleStreams.size(); i++) {
                ((SilenceSampleStream) this.sampleStreams.get(i)).seekTo(jConstrainSeekPosition);
            }
            return jConstrainSeekPosition;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
            return constrainSeekPosition(positionUs);
        }

        private long constrainSeekPosition(long positionUs) {
            return Util.constrainValue(positionUs, 0L, this.durationUs);
        }
    }

    private static final class SilenceSampleStream implements SampleStream {
        private final long durationBytes;
        private long positionBytes;
        private boolean sentFormat;

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return true;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() {
        }

        public SilenceSampleStream(long durationUs) {
            this.durationBytes = SilenceMediaSource.getAudioByteCount(durationUs);
            seekTo(0L);
        }

        public void seekTo(long positionUs) {
            this.positionBytes = Util.constrainValue(SilenceMediaSource.getAudioByteCount(positionUs), 0L, this.durationBytes);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, int readFlags) {
            if (!this.sentFormat || (readFlags & 2) != 0) {
                formatHolder.format = SilenceMediaSource.FORMAT;
                this.sentFormat = true;
                return -5;
            }
            long j = this.durationBytes;
            long j2 = this.positionBytes;
            long j3 = j - j2;
            if (j3 != 0) {
                buffer.timeUs = SilenceMediaSource.getAudioPositionUs(j2);
                buffer.addFlag(1);
                int iMin = (int) Math.min(SilenceMediaSource.SILENCE_SAMPLE.length, j3);
                if ((readFlags & 4) == 0) {
                    buffer.ensureSpaceForWrite(iMin);
                    buffer.data.put(SilenceMediaSource.SILENCE_SAMPLE, 0, iMin);
                }
                if ((readFlags & 1) == 0) {
                    this.positionBytes += iMin;
                }
                return -4;
            }
            buffer.addFlag(4);
            return -4;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long positionUs) {
            long j = this.positionBytes;
            seekTo(positionUs);
            return (int) ((this.positionBytes - j) / SilenceMediaSource.SILENCE_SAMPLE.length);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getAudioByteCount(long durationUs) {
        return Util.getPcmFrameSize(2, 2) * ((durationUs * 44100) / 1000000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getAudioPositionUs(long bytes) {
        return ((bytes / Util.getPcmFrameSize(2, 2)) * 1000000) / 44100;
    }
}
