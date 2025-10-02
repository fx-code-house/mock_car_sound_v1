package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import com.google.android.exoplayer2.DefaultLivePlaybackSpeedControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.audio.AuxEffectInfo;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.device.DeviceListener;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.video.spherical.CameraMotionListener;
import java.util.List;

/* loaded from: classes.dex */
public interface ExoPlayer extends Player {
    public static final long DEFAULT_RELEASE_TIMEOUT_MS = 500;

    public interface AudioComponent {
        @Deprecated
        void addAudioListener(AudioListener listener);

        void clearAuxEffectInfo();

        AudioAttributes getAudioAttributes();

        int getAudioSessionId();

        boolean getSkipSilenceEnabled();

        float getVolume();

        @Deprecated
        void removeAudioListener(AudioListener listener);

        void setAudioAttributes(AudioAttributes audioAttributes, boolean handleAudioFocus);

        void setAudioSessionId(int audioSessionId);

        void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

        void setSkipSilenceEnabled(boolean skipSilenceEnabled);

        void setVolume(float audioVolume);
    }

    public interface AudioOffloadListener {
        default void onExperimentalOffloadSchedulingEnabledChanged(boolean offloadSchedulingEnabled) {
        }

        default void onExperimentalSleepingForOffloadChanged(boolean sleepingForOffload) {
        }
    }

    public interface DeviceComponent {
        @Deprecated
        void addDeviceListener(DeviceListener listener);

        void decreaseDeviceVolume();

        DeviceInfo getDeviceInfo();

        int getDeviceVolume();

        void increaseDeviceVolume();

        boolean isDeviceMuted();

        @Deprecated
        void removeDeviceListener(DeviceListener listener);

        void setDeviceMuted(boolean muted);

        void setDeviceVolume(int volume);
    }

    public interface MetadataComponent {
        @Deprecated
        void addMetadataOutput(MetadataOutput output);

        @Deprecated
        void removeMetadataOutput(MetadataOutput output);
    }

    public interface TextComponent {
        @Deprecated
        void addTextOutput(TextOutput listener);

        List<Cue> getCurrentCues();

        @Deprecated
        void removeTextOutput(TextOutput listener);
    }

    public interface VideoComponent {
        @Deprecated
        void addVideoListener(VideoListener listener);

        void clearCameraMotionListener(CameraMotionListener listener);

        void clearVideoFrameMetadataListener(VideoFrameMetadataListener listener);

        void clearVideoSurface();

        void clearVideoSurface(Surface surface);

        void clearVideoSurfaceHolder(SurfaceHolder surfaceHolder);

        void clearVideoSurfaceView(SurfaceView surfaceView);

        void clearVideoTextureView(TextureView textureView);

        int getVideoScalingMode();

        VideoSize getVideoSize();

        @Deprecated
        void removeVideoListener(VideoListener listener);

        void setCameraMotionListener(CameraMotionListener listener);

        void setVideoFrameMetadataListener(VideoFrameMetadataListener listener);

        void setVideoScalingMode(int videoScalingMode);

        void setVideoSurface(Surface surface);

        void setVideoSurfaceHolder(SurfaceHolder surfaceHolder);

        void setVideoSurfaceView(SurfaceView surfaceView);

        void setVideoTextureView(TextureView textureView);
    }

    void addAudioOffloadListener(AudioOffloadListener listener);

    void addMediaSource(int index, MediaSource mediaSource);

    void addMediaSource(MediaSource mediaSource);

    void addMediaSources(int index, List<MediaSource> mediaSources);

    void addMediaSources(List<MediaSource> mediaSources);

    PlayerMessage createMessage(PlayerMessage.Target target);

    boolean experimentalIsSleepingForOffload();

    void experimentalSetOffloadSchedulingEnabled(boolean offloadSchedulingEnabled);

    AudioComponent getAudioComponent();

    Clock getClock();

    DeviceComponent getDeviceComponent();

    MetadataComponent getMetadataComponent();

    boolean getPauseAtEndOfMediaItems();

    Looper getPlaybackLooper();

    @Override // com.google.android.exoplayer2.Player
    ExoPlaybackException getPlayerError();

    int getRendererCount();

    int getRendererType(int index);

    SeekParameters getSeekParameters();

    TextComponent getTextComponent();

    TrackSelector getTrackSelector();

    VideoComponent getVideoComponent();

    @Deprecated
    void prepare(MediaSource mediaSource);

    @Deprecated
    void prepare(MediaSource mediaSource, boolean resetPosition, boolean resetState);

    void removeAudioOffloadListener(AudioOffloadListener listener);

    @Deprecated
    void retry();

    void setForegroundMode(boolean foregroundMode);

    void setMediaSource(MediaSource mediaSource);

    void setMediaSource(MediaSource mediaSource, long startPositionMs);

    void setMediaSource(MediaSource mediaSource, boolean resetPosition);

    void setMediaSources(List<MediaSource> mediaSources);

    void setMediaSources(List<MediaSource> mediaSources, int startWindowIndex, long startPositionMs);

    void setMediaSources(List<MediaSource> mediaSources, boolean resetPosition);

    void setPauseAtEndOfMediaItems(boolean pauseAtEndOfMediaItems);

    void setSeekParameters(SeekParameters seekParameters);

    void setShuffleOrder(ShuffleOrder shuffleOrder);

    @Deprecated
    public static final class Builder {
        private AnalyticsCollector analyticsCollector;
        private BandwidthMeter bandwidthMeter;
        private boolean buildCalled;
        private Clock clock;
        private LivePlaybackSpeedControl livePlaybackSpeedControl;
        private LoadControl loadControl;
        private Looper looper;
        private MediaSourceFactory mediaSourceFactory;
        private boolean pauseAtEndOfMediaItems;
        private long releaseTimeoutMs;
        private final Renderer[] renderers;
        private SeekParameters seekParameters;
        private long setForegroundModeTimeoutMs;
        private TrackSelector trackSelector;
        private boolean useLazyPreparation;

        public Builder(Context context, Renderer... renderers) {
            this(renderers, new DefaultTrackSelector(context), new DefaultMediaSourceFactory(context), new DefaultLoadControl(), DefaultBandwidthMeter.getSingletonInstance(context));
        }

        public Builder(Renderer[] renderers, TrackSelector trackSelector, MediaSourceFactory mediaSourceFactory, LoadControl loadControl, BandwidthMeter bandwidthMeter) {
            Assertions.checkArgument(renderers.length > 0);
            this.renderers = renderers;
            this.trackSelector = trackSelector;
            this.mediaSourceFactory = mediaSourceFactory;
            this.loadControl = loadControl;
            this.bandwidthMeter = bandwidthMeter;
            this.looper = Util.getCurrentOrMainLooper();
            this.useLazyPreparation = true;
            this.seekParameters = SeekParameters.DEFAULT;
            this.livePlaybackSpeedControl = new DefaultLivePlaybackSpeedControl.Builder().build();
            this.clock = Clock.DEFAULT;
            this.releaseTimeoutMs = 500L;
        }

        public Builder experimentalSetForegroundModeTimeoutMs(long timeoutMs) {
            Assertions.checkState(!this.buildCalled);
            this.setForegroundModeTimeoutMs = timeoutMs;
            return this;
        }

        public Builder setTrackSelector(TrackSelector trackSelector) {
            Assertions.checkState(!this.buildCalled);
            this.trackSelector = trackSelector;
            return this;
        }

        public Builder setMediaSourceFactory(MediaSourceFactory mediaSourceFactory) {
            Assertions.checkState(!this.buildCalled);
            this.mediaSourceFactory = mediaSourceFactory;
            return this;
        }

        public Builder setLoadControl(LoadControl loadControl) {
            Assertions.checkState(!this.buildCalled);
            this.loadControl = loadControl;
            return this;
        }

        public Builder setBandwidthMeter(BandwidthMeter bandwidthMeter) {
            Assertions.checkState(!this.buildCalled);
            this.bandwidthMeter = bandwidthMeter;
            return this;
        }

        public Builder setLooper(Looper looper) {
            Assertions.checkState(!this.buildCalled);
            this.looper = looper;
            return this;
        }

        public Builder setAnalyticsCollector(AnalyticsCollector analyticsCollector) {
            Assertions.checkState(!this.buildCalled);
            this.analyticsCollector = analyticsCollector;
            return this;
        }

        public Builder setUseLazyPreparation(boolean useLazyPreparation) {
            Assertions.checkState(!this.buildCalled);
            this.useLazyPreparation = useLazyPreparation;
            return this;
        }

        public Builder setSeekParameters(SeekParameters seekParameters) {
            Assertions.checkState(!this.buildCalled);
            this.seekParameters = seekParameters;
            return this;
        }

        public Builder setReleaseTimeoutMs(long releaseTimeoutMs) {
            Assertions.checkState(!this.buildCalled);
            this.releaseTimeoutMs = releaseTimeoutMs;
            return this;
        }

        public Builder setPauseAtEndOfMediaItems(boolean pauseAtEndOfMediaItems) {
            Assertions.checkState(!this.buildCalled);
            this.pauseAtEndOfMediaItems = pauseAtEndOfMediaItems;
            return this;
        }

        public Builder setLivePlaybackSpeedControl(LivePlaybackSpeedControl livePlaybackSpeedControl) {
            Assertions.checkState(!this.buildCalled);
            this.livePlaybackSpeedControl = livePlaybackSpeedControl;
            return this;
        }

        public Builder setClock(Clock clock) {
            Assertions.checkState(!this.buildCalled);
            this.clock = clock;
            return this;
        }

        public ExoPlayer build() {
            Assertions.checkState(!this.buildCalled);
            this.buildCalled = true;
            ExoPlayerImpl exoPlayerImpl = new ExoPlayerImpl(this.renderers, this.trackSelector, this.mediaSourceFactory, this.loadControl, this.bandwidthMeter, this.analyticsCollector, this.useLazyPreparation, this.seekParameters, 5000L, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS, this.livePlaybackSpeedControl, this.releaseTimeoutMs, this.pauseAtEndOfMediaItems, this.clock, this.looper, null, Player.Commands.EMPTY);
            long j = this.setForegroundModeTimeoutMs;
            if (j > 0) {
                exoPlayerImpl.experimentalSetForegroundModeTimeoutMs(j);
            }
            return exoPlayerImpl;
        }
    }
}
