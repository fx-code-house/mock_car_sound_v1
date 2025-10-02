package com.google.android.exoplayer2.source.rtsp;

import android.os.Handler;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.source.rtsp.RtpDataChannel;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes.dex */
final class RtpDataLoadable implements Loader.Loadable {
    private final EventListener eventListener;
    private RtpExtractor extractor;
    private volatile boolean loadCancelled;
    private volatile long nextRtpTimestamp;
    private final ExtractorOutput output;
    private final RtpDataChannel.Factory rtpDataChannelFactory;
    public final RtspMediaTrack rtspMediaTrack;
    public final int trackId;
    private final Handler playbackThreadHandler = Util.createHandlerForCurrentLooper();
    private volatile long pendingSeekPositionUs = C.TIME_UNSET;

    public interface EventListener {
        void onTransportReady(String transport, RtpDataChannel rtpDataChannel);
    }

    public RtpDataLoadable(int trackId, RtspMediaTrack rtspMediaTrack, EventListener eventListener, ExtractorOutput output, RtpDataChannel.Factory rtpDataChannelFactory) {
        this.trackId = trackId;
        this.rtspMediaTrack = rtspMediaTrack;
        this.eventListener = eventListener;
        this.output = output;
        this.rtpDataChannelFactory = rtpDataChannelFactory;
    }

    public void setTimestamp(long timestamp) {
        if (timestamp == C.TIME_UNSET || ((RtpExtractor) Assertions.checkNotNull(this.extractor)).hasReadFirstRtpPacket()) {
            return;
        }
        this.extractor.setFirstTimestamp(timestamp);
    }

    public void setSequenceNumber(int sequenceNumber) {
        if (((RtpExtractor) Assertions.checkNotNull(this.extractor)).hasReadFirstRtpPacket()) {
            return;
        }
        this.extractor.setFirstSequenceNumber(sequenceNumber);
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public void cancelLoad() {
        this.loadCancelled = true;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public void load() throws IOException {
        final RtpDataChannel rtpDataChannelCreateAndOpenDataChannel = null;
        try {
            rtpDataChannelCreateAndOpenDataChannel = this.rtpDataChannelFactory.createAndOpenDataChannel(this.trackId);
            final String transport = rtpDataChannelCreateAndOpenDataChannel.getTransport();
            this.playbackThreadHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.rtsp.RtpDataLoadable$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m191x79854435(transport, rtpDataChannelCreateAndOpenDataChannel);
                }
            });
            DefaultExtractorInput defaultExtractorInput = new DefaultExtractorInput((DataReader) Assertions.checkNotNull(rtpDataChannelCreateAndOpenDataChannel), 0L, -1L);
            RtpExtractor rtpExtractor = new RtpExtractor(this.rtspMediaTrack.payloadFormat, this.trackId);
            this.extractor = rtpExtractor;
            rtpExtractor.init(this.output);
            while (!this.loadCancelled) {
                if (this.pendingSeekPositionUs != C.TIME_UNSET) {
                    this.extractor.seek(this.nextRtpTimestamp, this.pendingSeekPositionUs);
                    this.pendingSeekPositionUs = C.TIME_UNSET;
                }
                if (this.extractor.read(defaultExtractorInput, new PositionHolder()) == -1) {
                    break;
                }
            }
        } finally {
            Util.closeQuietly(rtpDataChannelCreateAndOpenDataChannel);
        }
    }

    /* renamed from: lambda$load$0$com-google-android-exoplayer2-source-rtsp-RtpDataLoadable, reason: not valid java name */
    /* synthetic */ void m191x79854435(String str, RtpDataChannel rtpDataChannel) {
        this.eventListener.onTransportReady(str, rtpDataChannel);
    }

    public void resetForSeek() {
        ((RtpExtractor) Assertions.checkNotNull(this.extractor)).preSeek();
    }

    public void seekToUs(long positionUs, long nextRtpTimestamp) {
        this.pendingSeekPositionUs = positionUs;
        this.nextRtpTimestamp = nextRtpTimestamp;
    }
}
