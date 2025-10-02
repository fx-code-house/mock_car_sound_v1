package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.ForwardingTimeline;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.SinglePeriodTimeline;
import com.google.android.exoplayer2.source.rtsp.RtpDataChannel;
import com.google.android.exoplayer2.source.rtsp.RtspMediaPeriod;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;

/* loaded from: classes.dex */
public final class RtspMediaSource extends BaseMediaSource {
    public static final long DEFAULT_TIMEOUT_MS = 8000;
    private final MediaItem mediaItem;
    private final RtpDataChannel.Factory rtpDataChannelFactory;
    private boolean timelineIsLive;
    private boolean timelineIsSeekable;
    private final Uri uri;
    private final String userAgent;
    private long timelineDurationUs = C.TIME_UNSET;
    private boolean timelineIsPlaceholder = true;

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() {
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void releaseSourceInternal() {
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.rtsp");
    }

    public static final class Factory implements MediaSourceFactory {
        private boolean forceUseRtpTcp;
        private long timeoutMs = RtspMediaSource.DEFAULT_TIMEOUT_MS;
        private String userAgent = ExoPlayerLibraryInfo.VERSION_SLASHY;

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public int[] getSupportedTypes() {
            return new int[]{3};
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setDrmHttpDataSourceFactory(HttpDataSource.Factory drmHttpDataSourceFactory) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setDrmSessionManager(DrmSessionManager drmSessionManager) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmSessionManagerProvider(DrmSessionManagerProvider drmSessionManager) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setDrmUserAgent(String userAgent) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            return this;
        }

        public Factory setForceUseRtpTcp(boolean forceUseRtpTcp) {
            this.forceUseRtpTcp = forceUseRtpTcp;
            return this;
        }

        public Factory setUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Factory setTimeoutMs(long timeoutMs) {
            Assertions.checkArgument(timeoutMs > 0);
            this.timeoutMs = timeoutMs;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public RtspMediaSource createMediaSource(MediaItem mediaItem) {
            RtpDataChannel.Factory udpDataSourceRtpDataChannelFactory;
            Assertions.checkNotNull(mediaItem.playbackProperties);
            if (this.forceUseRtpTcp) {
                udpDataSourceRtpDataChannelFactory = new TransferRtpDataChannelFactory(this.timeoutMs);
            } else {
                udpDataSourceRtpDataChannelFactory = new UdpDataSourceRtpDataChannelFactory(this.timeoutMs);
            }
            return new RtspMediaSource(mediaItem, udpDataSourceRtpDataChannelFactory, this.userAgent);
        }
    }

    public static final class RtspPlaybackException extends IOException {
        public RtspPlaybackException(String message) {
            super(message);
        }

        public RtspPlaybackException(Throwable e) {
            super(e);
        }

        public RtspPlaybackException(String message, Throwable e) {
            super(message, e);
        }
    }

    RtspMediaSource(MediaItem mediaItem, RtpDataChannel.Factory rtpDataChannelFactory, String userAgent) {
        this.mediaItem = mediaItem;
        this.rtpDataChannelFactory = rtpDataChannelFactory;
        this.userAgent = userAgent;
        this.uri = ((MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties)).uri;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    protected void prepareSourceInternal(TransferListener mediaTransferListener) {
        notifySourceInfoRefreshed();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.mediaItem;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator, long startPositionUs) {
        return new RtspMediaPeriod(allocator, this.rtpDataChannelFactory, this.uri, new RtspMediaPeriod.Listener() { // from class: com.google.android.exoplayer2.source.rtsp.RtspMediaSource$$ExternalSyntheticLambda0
            @Override // com.google.android.exoplayer2.source.rtsp.RtspMediaPeriod.Listener
            public final void onSourceInfoRefreshed(RtspSessionTiming rtspSessionTiming) {
                this.f$0.m194xb2d0ac94(rtspSessionTiming);
            }
        }, this.userAgent);
    }

    /* renamed from: lambda$createPeriod$0$com-google-android-exoplayer2-source-rtsp-RtspMediaSource, reason: not valid java name */
    /* synthetic */ void m194xb2d0ac94(RtspSessionTiming rtspSessionTiming) {
        this.timelineDurationUs = C.msToUs(rtspSessionTiming.getDurationMs());
        this.timelineIsSeekable = !rtspSessionTiming.isLive();
        this.timelineIsLive = rtspSessionTiming.isLive();
        this.timelineIsPlaceholder = false;
        notifySourceInfoRefreshed();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((RtspMediaPeriod) mediaPeriod).release();
    }

    private void notifySourceInfoRefreshed() {
        Timeline singlePeriodTimeline = new SinglePeriodTimeline(this.timelineDurationUs, this.timelineIsSeekable, false, this.timelineIsLive, (Object) null, this.mediaItem);
        if (this.timelineIsPlaceholder) {
            singlePeriodTimeline = new ForwardingTimeline(this, singlePeriodTimeline) { // from class: com.google.android.exoplayer2.source.rtsp.RtspMediaSource.1
                @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
                public Timeline.Window getWindow(int windowIndex, Timeline.Window window, long defaultPositionProjectionUs) {
                    super.getWindow(windowIndex, window, defaultPositionProjectionUs);
                    window.isPlaceholder = true;
                    return window;
                }

                @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
                public Timeline.Period getPeriod(int periodIndex, Timeline.Period period, boolean setIds) {
                    super.getPeriod(periodIndex, period, setIds);
                    period.isPlaceholder = true;
                    return period;
                }
            };
        }
        refreshSourceInfo(singlePeriodTimeline);
    }
}
