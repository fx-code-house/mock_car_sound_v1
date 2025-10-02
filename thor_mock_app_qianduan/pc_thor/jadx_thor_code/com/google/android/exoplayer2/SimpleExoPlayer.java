package com.google.android.exoplayer2;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.AudioTrack;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import com.google.android.exoplayer2.AudioBecomingNoisyManager;
import com.google.android.exoplayer2.AudioFocusManager;
import com.google.android.exoplayer2.DefaultLivePlaybackSpeedControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.StreamVolumeManager;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AuxEffectInfo;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.device.DeviceListener;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.ConditionVariable;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoDecoderOutputBufferRenderer;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.video.spherical.CameraMotionListener;
import com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView;
import com.google.android.gms.wearable.WearableStatusCodes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public class SimpleExoPlayer extends BasePlayer implements ExoPlayer, ExoPlayer.AudioComponent, ExoPlayer.VideoComponent, ExoPlayer.TextComponent, ExoPlayer.MetadataComponent, ExoPlayer.DeviceComponent {
    public static final long DEFAULT_DETACH_SURFACE_TIMEOUT_MS = 2000;
    private static final String TAG = "SimpleExoPlayer";
    private final AnalyticsCollector analyticsCollector;
    private final Context applicationContext;
    private AudioAttributes audioAttributes;
    private final AudioBecomingNoisyManager audioBecomingNoisyManager;
    private DecoderCounters audioDecoderCounters;
    private final AudioFocusManager audioFocusManager;
    private Format audioFormat;
    private final CopyOnWriteArraySet<AudioListener> audioListeners;
    private int audioSessionId;
    private float audioVolume;
    private CameraMotionListener cameraMotionListener;
    private final ComponentListener componentListener;
    private final ConditionVariable constructorFinished;
    private List<Cue> currentCues;
    private final long detachSurfaceTimeoutMs;
    private DeviceInfo deviceInfo;
    private final CopyOnWriteArraySet<DeviceListener> deviceListeners;
    private final FrameMetadataListener frameMetadataListener;
    private boolean hasNotifiedFullWrongThreadWarning;
    private boolean isPriorityTaskManagerRegistered;
    private AudioTrack keepSessionIdAudioTrack;
    private final CopyOnWriteArraySet<MetadataOutput> metadataOutputs;
    private Surface ownedSurface;
    private final ExoPlayerImpl player;
    private boolean playerReleased;
    private PriorityTaskManager priorityTaskManager;
    protected final Renderer[] renderers;
    private boolean skipSilenceEnabled;
    private SphericalGLSurfaceView sphericalGLSurfaceView;
    private final StreamVolumeManager streamVolumeManager;
    private int surfaceHeight;
    private SurfaceHolder surfaceHolder;
    private boolean surfaceHolderSurfaceIsVideoOutput;
    private int surfaceWidth;
    private final CopyOnWriteArraySet<TextOutput> textOutputs;
    private TextureView textureView;
    private boolean throwsWhenUsingWrongThread;
    private DecoderCounters videoDecoderCounters;
    private Format videoFormat;
    private VideoFrameMetadataListener videoFrameMetadataListener;
    private final CopyOnWriteArraySet<VideoListener> videoListeners;
    private Object videoOutput;
    private int videoScalingMode;
    private VideoSize videoSize;
    private final WakeLockManager wakeLockManager;
    private final WifiLockManager wifiLockManager;

    /* JADX INFO: Access modifiers changed from: private */
    public static int getPlayWhenReadyChangeReason(boolean playWhenReady, int playerCommand) {
        return (!playWhenReady || playerCommand == 1) ? 1 : 2;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public ExoPlayer.AudioComponent getAudioComponent() {
        return this;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public ExoPlayer.DeviceComponent getDeviceComponent() {
        return this;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public ExoPlayer.MetadataComponent getMetadataComponent() {
        return this;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public ExoPlayer.TextComponent getTextComponent() {
        return this;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public ExoPlayer.VideoComponent getVideoComponent() {
        return this;
    }

    public static final class Builder {
        private AnalyticsCollector analyticsCollector;
        private AudioAttributes audioAttributes;
        private BandwidthMeter bandwidthMeter;
        private boolean buildCalled;
        private Clock clock;
        private final Context context;
        private long detachSurfaceTimeoutMs;
        private long foregroundModeTimeoutMs;
        private boolean handleAudioBecomingNoisy;
        private boolean handleAudioFocus;
        private LivePlaybackSpeedControl livePlaybackSpeedControl;
        private LoadControl loadControl;
        private Looper looper;
        private MediaSourceFactory mediaSourceFactory;
        private boolean pauseAtEndOfMediaItems;
        private PriorityTaskManager priorityTaskManager;
        private long releaseTimeoutMs;
        private final RenderersFactory renderersFactory;
        private long seekBackIncrementMs;
        private long seekForwardIncrementMs;
        private SeekParameters seekParameters;
        private boolean skipSilenceEnabled;
        private TrackSelector trackSelector;
        private boolean useLazyPreparation;
        private int videoScalingMode;
        private int wakeMode;

        public Builder(Context context) {
            this(context, new DefaultRenderersFactory(context), new DefaultExtractorsFactory());
        }

        public Builder(Context context, RenderersFactory renderersFactory) {
            this(context, renderersFactory, new DefaultExtractorsFactory());
        }

        public Builder(Context context, ExtractorsFactory extractorsFactory) {
            this(context, new DefaultRenderersFactory(context), extractorsFactory);
        }

        public Builder(Context context, RenderersFactory renderersFactory, ExtractorsFactory extractorsFactory) {
            this(context, renderersFactory, new DefaultTrackSelector(context), new DefaultMediaSourceFactory(context, extractorsFactory), new DefaultLoadControl(), DefaultBandwidthMeter.getSingletonInstance(context), new AnalyticsCollector(Clock.DEFAULT));
        }

        public Builder(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, MediaSourceFactory mediaSourceFactory, LoadControl loadControl, BandwidthMeter bandwidthMeter, AnalyticsCollector analyticsCollector) {
            this.context = context;
            this.renderersFactory = renderersFactory;
            this.trackSelector = trackSelector;
            this.mediaSourceFactory = mediaSourceFactory;
            this.loadControl = loadControl;
            this.bandwidthMeter = bandwidthMeter;
            this.analyticsCollector = analyticsCollector;
            this.looper = Util.getCurrentOrMainLooper();
            this.audioAttributes = AudioAttributes.DEFAULT;
            this.wakeMode = 0;
            this.videoScalingMode = 1;
            this.useLazyPreparation = true;
            this.seekParameters = SeekParameters.DEFAULT;
            this.seekBackIncrementMs = 5000L;
            this.seekForwardIncrementMs = C.DEFAULT_SEEK_FORWARD_INCREMENT_MS;
            this.livePlaybackSpeedControl = new DefaultLivePlaybackSpeedControl.Builder().build();
            this.clock = Clock.DEFAULT;
            this.releaseTimeoutMs = 500L;
            this.detachSurfaceTimeoutMs = SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS;
        }

        public Builder experimentalSetForegroundModeTimeoutMs(long timeoutMs) {
            Assertions.checkState(!this.buildCalled);
            this.foregroundModeTimeoutMs = timeoutMs;
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

        public Builder setPriorityTaskManager(PriorityTaskManager priorityTaskManager) {
            Assertions.checkState(!this.buildCalled);
            this.priorityTaskManager = priorityTaskManager;
            return this;
        }

        public Builder setAudioAttributes(AudioAttributes audioAttributes, boolean handleAudioFocus) {
            Assertions.checkState(!this.buildCalled);
            this.audioAttributes = audioAttributes;
            this.handleAudioFocus = handleAudioFocus;
            return this;
        }

        public Builder setWakeMode(int wakeMode) {
            Assertions.checkState(!this.buildCalled);
            this.wakeMode = wakeMode;
            return this;
        }

        public Builder setHandleAudioBecomingNoisy(boolean handleAudioBecomingNoisy) {
            Assertions.checkState(!this.buildCalled);
            this.handleAudioBecomingNoisy = handleAudioBecomingNoisy;
            return this;
        }

        public Builder setSkipSilenceEnabled(boolean skipSilenceEnabled) {
            Assertions.checkState(!this.buildCalled);
            this.skipSilenceEnabled = skipSilenceEnabled;
            return this;
        }

        public Builder setVideoScalingMode(int videoScalingMode) {
            Assertions.checkState(!this.buildCalled);
            this.videoScalingMode = videoScalingMode;
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

        public Builder setSeekBackIncrementMs(long seekBackIncrementMs) {
            Assertions.checkArgument(seekBackIncrementMs > 0);
            Assertions.checkState(!this.buildCalled);
            this.seekBackIncrementMs = seekBackIncrementMs;
            return this;
        }

        public Builder setSeekForwardIncrementMs(long seekForwardIncrementMs) {
            Assertions.checkArgument(seekForwardIncrementMs > 0);
            Assertions.checkState(!this.buildCalled);
            this.seekForwardIncrementMs = seekForwardIncrementMs;
            return this;
        }

        public Builder setReleaseTimeoutMs(long releaseTimeoutMs) {
            Assertions.checkState(!this.buildCalled);
            this.releaseTimeoutMs = releaseTimeoutMs;
            return this;
        }

        public Builder setDetachSurfaceTimeoutMs(long detachSurfaceTimeoutMs) {
            Assertions.checkState(!this.buildCalled);
            this.detachSurfaceTimeoutMs = detachSurfaceTimeoutMs;
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

        public SimpleExoPlayer build() {
            Assertions.checkState(!this.buildCalled);
            this.buildCalled = true;
            return new SimpleExoPlayer(this);
        }
    }

    @Deprecated
    protected SimpleExoPlayer(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, MediaSourceFactory mediaSourceFactory, LoadControl loadControl, BandwidthMeter bandwidthMeter, AnalyticsCollector analyticsCollector, boolean useLazyPreparation, Clock clock, Looper applicationLooper) {
        this(new Builder(context, renderersFactory).setTrackSelector(trackSelector).setMediaSourceFactory(mediaSourceFactory).setLoadControl(loadControl).setBandwidthMeter(bandwidthMeter).setAnalyticsCollector(analyticsCollector).setUseLazyPreparation(useLazyPreparation).setClock(clock).setLooper(applicationLooper));
    }

    protected SimpleExoPlayer(Builder builder) throws Throwable {
        SimpleExoPlayer simpleExoPlayer;
        ConditionVariable conditionVariable = new ConditionVariable();
        this.constructorFinished = conditionVariable;
        try {
            Context applicationContext = builder.context.getApplicationContext();
            this.applicationContext = applicationContext;
            AnalyticsCollector analyticsCollector = builder.analyticsCollector;
            this.analyticsCollector = analyticsCollector;
            this.priorityTaskManager = builder.priorityTaskManager;
            this.audioAttributes = builder.audioAttributes;
            this.videoScalingMode = builder.videoScalingMode;
            this.skipSilenceEnabled = builder.skipSilenceEnabled;
            this.detachSurfaceTimeoutMs = builder.detachSurfaceTimeoutMs;
            ComponentListener componentListener = new ComponentListener();
            this.componentListener = componentListener;
            FrameMetadataListener frameMetadataListener = new FrameMetadataListener();
            this.frameMetadataListener = frameMetadataListener;
            this.videoListeners = new CopyOnWriteArraySet<>();
            this.audioListeners = new CopyOnWriteArraySet<>();
            this.textOutputs = new CopyOnWriteArraySet<>();
            this.metadataOutputs = new CopyOnWriteArraySet<>();
            this.deviceListeners = new CopyOnWriteArraySet<>();
            Handler handler = new Handler(builder.looper);
            Renderer[] rendererArrCreateRenderers = builder.renderersFactory.createRenderers(handler, componentListener, componentListener, componentListener, componentListener);
            this.renderers = rendererArrCreateRenderers;
            this.audioVolume = 1.0f;
            if (Util.SDK_INT < 21) {
                this.audioSessionId = initializeKeepSessionIdAudioTrack(0);
            } else {
                this.audioSessionId = C.generateAudioSessionIdV21(applicationContext);
            }
            this.currentCues = Collections.emptyList();
            this.throwsWhenUsingWrongThread = true;
            try {
                ExoPlayerImpl exoPlayerImpl = new ExoPlayerImpl(rendererArrCreateRenderers, builder.trackSelector, builder.mediaSourceFactory, builder.loadControl, builder.bandwidthMeter, analyticsCollector, builder.useLazyPreparation, builder.seekParameters, builder.seekBackIncrementMs, builder.seekForwardIncrementMs, builder.livePlaybackSpeedControl, builder.releaseTimeoutMs, builder.pauseAtEndOfMediaItems, builder.clock, builder.looper, this, new Player.Commands.Builder().addAll(20, 21, 22, 23, 24, 25, 26, 27).build());
                simpleExoPlayer = this;
                try {
                    simpleExoPlayer.player = exoPlayerImpl;
                    exoPlayerImpl.addListener(componentListener);
                    exoPlayerImpl.addAudioOffloadListener(componentListener);
                    if (builder.foregroundModeTimeoutMs > 0) {
                        exoPlayerImpl.experimentalSetForegroundModeTimeoutMs(builder.foregroundModeTimeoutMs);
                    }
                    AudioBecomingNoisyManager audioBecomingNoisyManager = new AudioBecomingNoisyManager(builder.context, handler, componentListener);
                    simpleExoPlayer.audioBecomingNoisyManager = audioBecomingNoisyManager;
                    audioBecomingNoisyManager.setEnabled(builder.handleAudioBecomingNoisy);
                    AudioFocusManager audioFocusManager = new AudioFocusManager(builder.context, handler, componentListener);
                    simpleExoPlayer.audioFocusManager = audioFocusManager;
                    audioFocusManager.setAudioAttributes(builder.handleAudioFocus ? simpleExoPlayer.audioAttributes : null);
                    StreamVolumeManager streamVolumeManager = new StreamVolumeManager(builder.context, handler, componentListener);
                    simpleExoPlayer.streamVolumeManager = streamVolumeManager;
                    streamVolumeManager.setStreamType(Util.getStreamTypeForAudioUsage(simpleExoPlayer.audioAttributes.usage));
                    WakeLockManager wakeLockManager = new WakeLockManager(builder.context);
                    simpleExoPlayer.wakeLockManager = wakeLockManager;
                    wakeLockManager.setEnabled(builder.wakeMode != 0);
                    WifiLockManager wifiLockManager = new WifiLockManager(builder.context);
                    simpleExoPlayer.wifiLockManager = wifiLockManager;
                    wifiLockManager.setEnabled(builder.wakeMode == 2);
                    simpleExoPlayer.deviceInfo = createDeviceInfo(streamVolumeManager);
                    simpleExoPlayer.videoSize = VideoSize.UNKNOWN;
                    simpleExoPlayer.sendRendererMessage(1, 102, Integer.valueOf(simpleExoPlayer.audioSessionId));
                    simpleExoPlayer.sendRendererMessage(2, 102, Integer.valueOf(simpleExoPlayer.audioSessionId));
                    simpleExoPlayer.sendRendererMessage(1, 3, simpleExoPlayer.audioAttributes);
                    simpleExoPlayer.sendRendererMessage(2, 4, Integer.valueOf(simpleExoPlayer.videoScalingMode));
                    simpleExoPlayer.sendRendererMessage(1, 101, Boolean.valueOf(simpleExoPlayer.skipSilenceEnabled));
                    simpleExoPlayer.sendRendererMessage(2, 6, frameMetadataListener);
                    simpleExoPlayer.sendRendererMessage(6, 7, frameMetadataListener);
                    conditionVariable.open();
                } catch (Throwable th) {
                    th = th;
                    simpleExoPlayer.constructorFinished.open();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                simpleExoPlayer = this;
            }
        } catch (Throwable th3) {
            th = th3;
            simpleExoPlayer = this;
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void experimentalSetOffloadSchedulingEnabled(boolean offloadSchedulingEnabled) {
        verifyApplicationThread();
        this.player.experimentalSetOffloadSchedulingEnabled(offloadSchedulingEnabled);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public boolean experimentalIsSleepingForOffload() {
        verifyApplicationThread();
        return this.player.experimentalIsSleepingForOffload();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setVideoScalingMode(int videoScalingMode) {
        verifyApplicationThread();
        this.videoScalingMode = videoScalingMode;
        sendRendererMessage(2, 4, Integer.valueOf(videoScalingMode));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public int getVideoScalingMode() {
        return this.videoScalingMode;
    }

    @Override // com.google.android.exoplayer2.Player
    public VideoSize getVideoSize() {
        return this.videoSize;
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface() {
        verifyApplicationThread();
        removeSurfaceCallbacks();
        setVideoOutputInternal(null);
        maybeNotifySurfaceSizeChanged(0, 0);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface(Surface surface) {
        verifyApplicationThread();
        if (surface == null || surface != this.videoOutput) {
            return;
        }
        clearVideoSurface();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurface(Surface surface) {
        verifyApplicationThread();
        removeSurfaceCallbacks();
        setVideoOutputInternal(surface);
        int i = surface == null ? 0 : -1;
        maybeNotifySurfaceSizeChanged(i, i);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceHolder(SurfaceHolder surfaceHolder) {
        verifyApplicationThread();
        if (surfaceHolder == null) {
            clearVideoSurface();
            return;
        }
        removeSurfaceCallbacks();
        this.surfaceHolderSurfaceIsVideoOutput = true;
        this.surfaceHolder = surfaceHolder;
        surfaceHolder.addCallback(this.componentListener);
        Surface surface = surfaceHolder.getSurface();
        if (surface != null && surface.isValid()) {
            setVideoOutputInternal(surface);
            Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
            maybeNotifySurfaceSizeChanged(surfaceFrame.width(), surfaceFrame.height());
        } else {
            setVideoOutputInternal(null);
            maybeNotifySurfaceSizeChanged(0, 0);
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceHolder(SurfaceHolder surfaceHolder) {
        verifyApplicationThread();
        if (surfaceHolder == null || surfaceHolder != this.surfaceHolder) {
            return;
        }
        clearVideoSurface();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceView(SurfaceView surfaceView) {
        verifyApplicationThread();
        if (surfaceView instanceof VideoDecoderOutputBufferRenderer) {
            removeSurfaceCallbacks();
            setVideoOutputInternal(surfaceView);
            setNonVideoOutputSurfaceHolderInternal(surfaceView.getHolder());
        } else {
            if (surfaceView instanceof SphericalGLSurfaceView) {
                removeSurfaceCallbacks();
                this.sphericalGLSurfaceView = (SphericalGLSurfaceView) surfaceView;
                this.player.createMessage(this.frameMetadataListener).setType(10000).setPayload(this.sphericalGLSurfaceView).send();
                this.sphericalGLSurfaceView.addVideoSurfaceListener(this.componentListener);
                setVideoOutputInternal(this.sphericalGLSurfaceView.getVideoSurface());
                setNonVideoOutputSurfaceHolderInternal(surfaceView.getHolder());
                return;
            }
            setVideoSurfaceHolder(surfaceView == null ? null : surfaceView.getHolder());
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceView(SurfaceView surfaceView) {
        verifyApplicationThread();
        clearVideoSurfaceHolder(surfaceView == null ? null : surfaceView.getHolder());
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoTextureView(TextureView textureView) {
        verifyApplicationThread();
        if (textureView == null) {
            clearVideoSurface();
            return;
        }
        removeSurfaceCallbacks();
        this.textureView = textureView;
        if (textureView.getSurfaceTextureListener() != null) {
            Log.w(TAG, "Replacing existing SurfaceTextureListener.");
        }
        textureView.setSurfaceTextureListener(this.componentListener);
        SurfaceTexture surfaceTexture = textureView.isAvailable() ? textureView.getSurfaceTexture() : null;
        if (surfaceTexture == null) {
            setVideoOutputInternal(null);
            maybeNotifySurfaceSizeChanged(0, 0);
        } else {
            setSurfaceTextureInternal(surfaceTexture);
            maybeNotifySurfaceSizeChanged(textureView.getWidth(), textureView.getHeight());
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoTextureView(TextureView textureView) {
        verifyApplicationThread();
        if (textureView == null || textureView != this.textureView) {
            return;
        }
        clearVideoSurface();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addAudioOffloadListener(ExoPlayer.AudioOffloadListener listener) {
        this.player.addAudioOffloadListener(listener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void removeAudioOffloadListener(ExoPlayer.AudioOffloadListener listener) {
        this.player.removeAudioOffloadListener(listener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    @Deprecated
    public void addAudioListener(AudioListener listener) {
        Assertions.checkNotNull(listener);
        this.audioListeners.add(listener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    @Deprecated
    public void removeAudioListener(AudioListener listener) {
        this.audioListeners.remove(listener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setAudioAttributes(AudioAttributes audioAttributes, boolean handleAudioFocus) {
        verifyApplicationThread();
        if (this.playerReleased) {
            return;
        }
        if (!Util.areEqual(this.audioAttributes, audioAttributes)) {
            this.audioAttributes = audioAttributes;
            sendRendererMessage(1, 3, audioAttributes);
            this.streamVolumeManager.setStreamType(Util.getStreamTypeForAudioUsage(audioAttributes.usage));
            this.analyticsCollector.onAudioAttributesChanged(audioAttributes);
            Iterator<AudioListener> it = this.audioListeners.iterator();
            while (it.hasNext()) {
                it.next().onAudioAttributesChanged(audioAttributes);
            }
        }
        AudioFocusManager audioFocusManager = this.audioFocusManager;
        if (!handleAudioFocus) {
            audioAttributes = null;
        }
        audioFocusManager.setAudioAttributes(audioAttributes);
        boolean playWhenReady = getPlayWhenReady();
        int iUpdateAudioFocus = this.audioFocusManager.updateAudioFocus(playWhenReady, getPlaybackState());
        updatePlayWhenReady(playWhenReady, iUpdateAudioFocus, getPlayWhenReadyChangeReason(playWhenReady, iUpdateAudioFocus));
    }

    @Override // com.google.android.exoplayer2.Player
    public AudioAttributes getAudioAttributes() {
        return this.audioAttributes;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setAudioSessionId(int audioSessionId) {
        verifyApplicationThread();
        if (this.audioSessionId == audioSessionId) {
            return;
        }
        if (audioSessionId == 0) {
            if (Util.SDK_INT < 21) {
                audioSessionId = initializeKeepSessionIdAudioTrack(0);
            } else {
                audioSessionId = C.generateAudioSessionIdV21(this.applicationContext);
            }
        } else if (Util.SDK_INT < 21) {
            initializeKeepSessionIdAudioTrack(audioSessionId);
        }
        this.audioSessionId = audioSessionId;
        sendRendererMessage(1, 102, Integer.valueOf(audioSessionId));
        sendRendererMessage(2, 102, Integer.valueOf(audioSessionId));
        this.analyticsCollector.onAudioSessionIdChanged(audioSessionId);
        Iterator<AudioListener> it = this.audioListeners.iterator();
        while (it.hasNext()) {
            it.next().onAudioSessionIdChanged(audioSessionId);
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public int getAudioSessionId() {
        return this.audioSessionId;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setAuxEffectInfo(AuxEffectInfo auxEffectInfo) {
        verifyApplicationThread();
        sendRendererMessage(1, 5, auxEffectInfo);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void clearAuxEffectInfo() {
        setAuxEffectInfo(new AuxEffectInfo(0, 0.0f));
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVolume(float audioVolume) {
        verifyApplicationThread();
        float fConstrainValue = Util.constrainValue(audioVolume, 0.0f, 1.0f);
        if (this.audioVolume == fConstrainValue) {
            return;
        }
        this.audioVolume = fConstrainValue;
        sendVolumeToRenderers();
        this.analyticsCollector.onVolumeChanged(fConstrainValue);
        Iterator<AudioListener> it = this.audioListeners.iterator();
        while (it.hasNext()) {
            it.next().onVolumeChanged(fConstrainValue);
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public float getVolume() {
        return this.audioVolume;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public boolean getSkipSilenceEnabled() {
        return this.skipSilenceEnabled;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setSkipSilenceEnabled(boolean skipSilenceEnabled) {
        verifyApplicationThread();
        if (this.skipSilenceEnabled == skipSilenceEnabled) {
            return;
        }
        this.skipSilenceEnabled = skipSilenceEnabled;
        sendRendererMessage(1, 101, Boolean.valueOf(skipSilenceEnabled));
        notifySkipSilenceEnabledChanged();
    }

    public AnalyticsCollector getAnalyticsCollector() {
        return this.analyticsCollector;
    }

    public void addAnalyticsListener(AnalyticsListener listener) {
        Assertions.checkNotNull(listener);
        this.analyticsCollector.addListener(listener);
    }

    public void removeAnalyticsListener(AnalyticsListener listener) {
        this.analyticsCollector.removeListener(listener);
    }

    public void setHandleAudioBecomingNoisy(boolean handleAudioBecomingNoisy) {
        verifyApplicationThread();
        if (this.playerReleased) {
            return;
        }
        this.audioBecomingNoisyManager.setEnabled(handleAudioBecomingNoisy);
    }

    public void setPriorityTaskManager(PriorityTaskManager priorityTaskManager) {
        verifyApplicationThread();
        if (Util.areEqual(this.priorityTaskManager, priorityTaskManager)) {
            return;
        }
        if (this.isPriorityTaskManagerRegistered) {
            ((PriorityTaskManager) Assertions.checkNotNull(this.priorityTaskManager)).remove(0);
        }
        if (priorityTaskManager != null && isLoading()) {
            priorityTaskManager.add(0);
            this.isPriorityTaskManagerRegistered = true;
        } else {
            this.isPriorityTaskManagerRegistered = false;
        }
        this.priorityTaskManager = priorityTaskManager;
    }

    public Format getVideoFormat() {
        return this.videoFormat;
    }

    public Format getAudioFormat() {
        return this.audioFormat;
    }

    public DecoderCounters getVideoDecoderCounters() {
        return this.videoDecoderCounters;
    }

    public DecoderCounters getAudioDecoderCounters() {
        return this.audioDecoderCounters;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    @Deprecated
    public void addVideoListener(VideoListener listener) {
        Assertions.checkNotNull(listener);
        this.videoListeners.add(listener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    @Deprecated
    public void removeVideoListener(VideoListener listener) {
        this.videoListeners.remove(listener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setVideoFrameMetadataListener(VideoFrameMetadataListener listener) {
        verifyApplicationThread();
        this.videoFrameMetadataListener = listener;
        this.player.createMessage(this.frameMetadataListener).setType(6).setPayload(listener).send();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void clearVideoFrameMetadataListener(VideoFrameMetadataListener listener) {
        verifyApplicationThread();
        if (this.videoFrameMetadataListener != listener) {
            return;
        }
        this.player.createMessage(this.frameMetadataListener).setType(6).setPayload(null).send();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setCameraMotionListener(CameraMotionListener listener) {
        verifyApplicationThread();
        this.cameraMotionListener = listener;
        this.player.createMessage(this.frameMetadataListener).setType(7).setPayload(listener).send();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void clearCameraMotionListener(CameraMotionListener listener) {
        verifyApplicationThread();
        if (this.cameraMotionListener != listener) {
            return;
        }
        this.player.createMessage(this.frameMetadataListener).setType(7).setPayload(null).send();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.TextComponent
    @Deprecated
    public void addTextOutput(TextOutput listener) {
        Assertions.checkNotNull(listener);
        this.textOutputs.add(listener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.TextComponent
    @Deprecated
    public void removeTextOutput(TextOutput listener) {
        this.textOutputs.remove(listener);
    }

    @Override // com.google.android.exoplayer2.Player
    public List<Cue> getCurrentCues() {
        verifyApplicationThread();
        return this.currentCues;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.MetadataComponent
    @Deprecated
    public void addMetadataOutput(MetadataOutput output) {
        Assertions.checkNotNull(output);
        this.metadataOutputs.add(output);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.MetadataComponent
    @Deprecated
    public void removeMetadataOutput(MetadataOutput output) {
        this.metadataOutputs.remove(output);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public Looper getPlaybackLooper() {
        return this.player.getPlaybackLooper();
    }

    @Override // com.google.android.exoplayer2.Player
    public Looper getApplicationLooper() {
        return this.player.getApplicationLooper();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public Clock getClock() {
        return this.player.getClock();
    }

    @Override // com.google.android.exoplayer2.Player
    public void addListener(Player.Listener listener) {
        Assertions.checkNotNull(listener);
        addAudioListener(listener);
        addVideoListener(listener);
        addTextOutput(listener);
        addMetadataOutput(listener);
        addDeviceListener(listener);
        addListener((Player.EventListener) listener);
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void addListener(Player.EventListener listener) {
        Assertions.checkNotNull(listener);
        this.player.addListener(listener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeListener(Player.Listener listener) {
        Assertions.checkNotNull(listener);
        removeAudioListener(listener);
        removeVideoListener(listener);
        removeTextOutput(listener);
        removeMetadataOutput(listener);
        removeDeviceListener(listener);
        removeListener((Player.EventListener) listener);
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void removeListener(Player.EventListener listener) {
        this.player.removeListener(listener);
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackState() {
        verifyApplicationThread();
        return this.player.getPlaybackState();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackSuppressionReason() {
        verifyApplicationThread();
        return this.player.getPlaybackSuppressionReason();
    }

    @Override // com.google.android.exoplayer2.Player
    public ExoPlaybackException getPlayerError() {
        verifyApplicationThread();
        return this.player.getPlayerError();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void retry() {
        verifyApplicationThread();
        prepare();
    }

    @Override // com.google.android.exoplayer2.Player
    public Player.Commands getAvailableCommands() {
        verifyApplicationThread();
        return this.player.getAvailableCommands();
    }

    @Override // com.google.android.exoplayer2.Player
    public void prepare() {
        verifyApplicationThread();
        boolean playWhenReady = getPlayWhenReady();
        int iUpdateAudioFocus = this.audioFocusManager.updateAudioFocus(playWhenReady, 2);
        updatePlayWhenReady(playWhenReady, iUpdateAudioFocus, getPlayWhenReadyChangeReason(playWhenReady, iUpdateAudioFocus));
        this.player.prepare();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void prepare(MediaSource mediaSource) {
        prepare(mediaSource, true, true);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void prepare(MediaSource mediaSource, boolean resetPosition, boolean resetState) {
        verifyApplicationThread();
        setMediaSources(Collections.singletonList(mediaSource), resetPosition);
        prepare();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> mediaItems, boolean resetPosition) {
        verifyApplicationThread();
        this.player.setMediaItems(mediaItems, resetPosition);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> mediaItems, int startWindowIndex, long startPositionMs) {
        verifyApplicationThread();
        this.player.setMediaItems(mediaItems, startWindowIndex, startPositionMs);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> mediaSources) {
        verifyApplicationThread();
        this.player.setMediaSources(mediaSources);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> mediaSources, boolean resetPosition) {
        verifyApplicationThread();
        this.player.setMediaSources(mediaSources, resetPosition);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> mediaSources, int startWindowIndex, long startPositionMs) {
        verifyApplicationThread();
        this.player.setMediaSources(mediaSources, startWindowIndex, startPositionMs);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource) {
        verifyApplicationThread();
        this.player.setMediaSource(mediaSource);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource, boolean resetPosition) {
        verifyApplicationThread();
        this.player.setMediaSource(mediaSource, resetPosition);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource, long startPositionMs) {
        verifyApplicationThread();
        this.player.setMediaSource(mediaSource, startPositionMs);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItems(int index, List<MediaItem> mediaItems) {
        verifyApplicationThread();
        this.player.addMediaItems(index, mediaItems);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSource(MediaSource mediaSource) {
        verifyApplicationThread();
        this.player.addMediaSource(mediaSource);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSource(int index, MediaSource mediaSource) {
        verifyApplicationThread();
        this.player.addMediaSource(index, mediaSource);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSources(List<MediaSource> mediaSources) {
        verifyApplicationThread();
        this.player.addMediaSources(mediaSources);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSources(int index, List<MediaSource> mediaSources) {
        verifyApplicationThread();
        this.player.addMediaSources(index, mediaSources);
    }

    @Override // com.google.android.exoplayer2.Player
    public void moveMediaItems(int fromIndex, int toIndex, int newIndex) {
        verifyApplicationThread();
        this.player.moveMediaItems(fromIndex, toIndex, newIndex);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeMediaItems(int fromIndex, int toIndex) {
        verifyApplicationThread();
        this.player.removeMediaItems(fromIndex, toIndex);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setShuffleOrder(ShuffleOrder shuffleOrder) {
        verifyApplicationThread();
        this.player.setShuffleOrder(shuffleOrder);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlayWhenReady(boolean playWhenReady) {
        verifyApplicationThread();
        int iUpdateAudioFocus = this.audioFocusManager.updateAudioFocus(playWhenReady, getPlaybackState());
        updatePlayWhenReady(playWhenReady, iUpdateAudioFocus, getPlayWhenReadyChangeReason(playWhenReady, iUpdateAudioFocus));
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getPlayWhenReady() {
        verifyApplicationThread();
        return this.player.getPlayWhenReady();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setPauseAtEndOfMediaItems(boolean pauseAtEndOfMediaItems) {
        verifyApplicationThread();
        this.player.setPauseAtEndOfMediaItems(pauseAtEndOfMediaItems);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public boolean getPauseAtEndOfMediaItems() {
        verifyApplicationThread();
        return this.player.getPauseAtEndOfMediaItems();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getRepeatMode() {
        verifyApplicationThread();
        return this.player.getRepeatMode();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setRepeatMode(int repeatMode) {
        verifyApplicationThread();
        this.player.setRepeatMode(repeatMode);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setShuffleModeEnabled(boolean shuffleModeEnabled) {
        verifyApplicationThread();
        this.player.setShuffleModeEnabled(shuffleModeEnabled);
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getShuffleModeEnabled() {
        verifyApplicationThread();
        return this.player.getShuffleModeEnabled();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isLoading() {
        verifyApplicationThread();
        return this.player.isLoading();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekTo(int windowIndex, long positionMs) {
        verifyApplicationThread();
        this.analyticsCollector.notifySeekStarted();
        this.player.seekTo(windowIndex, positionMs);
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekBackIncrement() {
        verifyApplicationThread();
        return this.player.getSeekBackIncrement();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekForwardIncrement() {
        verifyApplicationThread();
        return this.player.getSeekForwardIncrement();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getMaxSeekToPreviousPosition() {
        verifyApplicationThread();
        return this.player.getMaxSeekToPreviousPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        verifyApplicationThread();
        this.player.setPlaybackParameters(playbackParameters);
    }

    @Override // com.google.android.exoplayer2.Player
    public PlaybackParameters getPlaybackParameters() {
        verifyApplicationThread();
        return this.player.getPlaybackParameters();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setSeekParameters(SeekParameters seekParameters) {
        verifyApplicationThread();
        this.player.setSeekParameters(seekParameters);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public SeekParameters getSeekParameters() {
        verifyApplicationThread();
        return this.player.getSeekParameters();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setForegroundMode(boolean foregroundMode) {
        verifyApplicationThread();
        this.player.setForegroundMode(foregroundMode);
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void stop(boolean reset) {
        verifyApplicationThread();
        this.audioFocusManager.updateAudioFocus(getPlayWhenReady(), 1);
        this.player.stop(reset);
        this.currentCues = Collections.emptyList();
    }

    @Override // com.google.android.exoplayer2.Player
    public void release() {
        AudioTrack audioTrack;
        verifyApplicationThread();
        if (Util.SDK_INT < 21 && (audioTrack = this.keepSessionIdAudioTrack) != null) {
            audioTrack.release();
            this.keepSessionIdAudioTrack = null;
        }
        this.audioBecomingNoisyManager.setEnabled(false);
        this.streamVolumeManager.release();
        this.wakeLockManager.setStayAwake(false);
        this.wifiLockManager.setStayAwake(false);
        this.audioFocusManager.release();
        this.player.release();
        this.analyticsCollector.release();
        removeSurfaceCallbacks();
        Surface surface = this.ownedSurface;
        if (surface != null) {
            surface.release();
            this.ownedSurface = null;
        }
        if (this.isPriorityTaskManagerRegistered) {
            ((PriorityTaskManager) Assertions.checkNotNull(this.priorityTaskManager)).remove(0);
            this.isPriorityTaskManagerRegistered = false;
        }
        this.currentCues = Collections.emptyList();
        this.playerReleased = true;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public PlayerMessage createMessage(PlayerMessage.Target target) {
        verifyApplicationThread();
        return this.player.createMessage(target);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public int getRendererCount() {
        verifyApplicationThread();
        return this.player.getRendererCount();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public int getRendererType(int index) {
        verifyApplicationThread();
        return this.player.getRendererType(index);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public TrackSelector getTrackSelector() {
        verifyApplicationThread();
        return this.player.getTrackSelector();
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackGroupArray getCurrentTrackGroups() {
        verifyApplicationThread();
        return this.player.getCurrentTrackGroups();
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackSelectionArray getCurrentTrackSelections() {
        verifyApplicationThread();
        return this.player.getCurrentTrackSelections();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public List<Metadata> getCurrentStaticMetadata() {
        verifyApplicationThread();
        return this.player.getCurrentStaticMetadata();
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getMediaMetadata() {
        return this.player.getMediaMetadata();
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getPlaylistMetadata() {
        return this.player.getPlaylistMetadata();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaylistMetadata(MediaMetadata mediaMetadata) {
        this.player.setPlaylistMetadata(mediaMetadata);
    }

    @Override // com.google.android.exoplayer2.Player
    public Timeline getCurrentTimeline() {
        verifyApplicationThread();
        return this.player.getCurrentTimeline();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentPeriodIndex() {
        verifyApplicationThread();
        return this.player.getCurrentPeriodIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentWindowIndex() {
        verifyApplicationThread();
        return this.player.getCurrentWindowIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getDuration() {
        verifyApplicationThread();
        return this.player.getDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getCurrentPosition() {
        verifyApplicationThread();
        return this.player.getCurrentPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getBufferedPosition() {
        verifyApplicationThread();
        return this.player.getBufferedPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getTotalBufferedDuration() {
        verifyApplicationThread();
        return this.player.getTotalBufferedDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isPlayingAd() {
        verifyApplicationThread();
        return this.player.isPlayingAd();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdGroupIndex() {
        verifyApplicationThread();
        return this.player.getCurrentAdGroupIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdIndexInAdGroup() {
        verifyApplicationThread();
        return this.player.getCurrentAdIndexInAdGroup();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentPosition() {
        verifyApplicationThread();
        return this.player.getContentPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentBufferedPosition() {
        verifyApplicationThread();
        return this.player.getContentBufferedPosition();
    }

    @Deprecated
    public void setHandleWakeLock(boolean z) {
        setWakeMode(z ? 1 : 0);
    }

    public void setWakeMode(int wakeMode) {
        verifyApplicationThread();
        if (wakeMode == 0) {
            this.wakeLockManager.setEnabled(false);
            this.wifiLockManager.setEnabled(false);
        } else if (wakeMode == 1) {
            this.wakeLockManager.setEnabled(true);
            this.wifiLockManager.setEnabled(false);
        } else {
            if (wakeMode != 2) {
                return;
            }
            this.wakeLockManager.setEnabled(true);
            this.wifiLockManager.setEnabled(true);
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.DeviceComponent
    @Deprecated
    public void addDeviceListener(DeviceListener listener) {
        Assertions.checkNotNull(listener);
        this.deviceListeners.add(listener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer.DeviceComponent
    @Deprecated
    public void removeDeviceListener(DeviceListener listener) {
        this.deviceListeners.remove(listener);
    }

    @Override // com.google.android.exoplayer2.Player
    public DeviceInfo getDeviceInfo() {
        verifyApplicationThread();
        return this.deviceInfo;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getDeviceVolume() {
        verifyApplicationThread();
        return this.streamVolumeManager.getVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isDeviceMuted() {
        verifyApplicationThread();
        return this.streamVolumeManager.isMuted();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceVolume(int volume) {
        verifyApplicationThread();
        this.streamVolumeManager.setVolume(volume);
    }

    @Override // com.google.android.exoplayer2.Player
    public void increaseDeviceVolume() {
        verifyApplicationThread();
        this.streamVolumeManager.increaseVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public void decreaseDeviceVolume() {
        verifyApplicationThread();
        this.streamVolumeManager.decreaseVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceMuted(boolean muted) {
        verifyApplicationThread();
        this.streamVolumeManager.setMuted(muted);
    }

    @Deprecated
    public void setThrowsWhenUsingWrongThread(boolean throwsWhenUsingWrongThread) {
        this.throwsWhenUsingWrongThread = throwsWhenUsingWrongThread;
    }

    private void removeSurfaceCallbacks() {
        if (this.sphericalGLSurfaceView != null) {
            this.player.createMessage(this.frameMetadataListener).setType(10000).setPayload(null).send();
            this.sphericalGLSurfaceView.removeVideoSurfaceListener(this.componentListener);
            this.sphericalGLSurfaceView = null;
        }
        TextureView textureView = this.textureView;
        if (textureView != null) {
            if (textureView.getSurfaceTextureListener() != this.componentListener) {
                Log.w(TAG, "SurfaceTextureListener already unset or replaced.");
            } else {
                this.textureView.setSurfaceTextureListener(null);
            }
            this.textureView = null;
        }
        SurfaceHolder surfaceHolder = this.surfaceHolder;
        if (surfaceHolder != null) {
            surfaceHolder.removeCallback(this.componentListener);
            this.surfaceHolder = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSurfaceTextureInternal(SurfaceTexture surfaceTexture) {
        Surface surface = new Surface(surfaceTexture);
        setVideoOutputInternal(surface);
        this.ownedSurface = surface;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVideoOutputInternal(Object videoOutput) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        Renderer[] rendererArr = this.renderers;
        int length = rendererArr.length;
        int i = 0;
        while (true) {
            z = true;
            if (i >= length) {
                break;
            }
            Renderer renderer = rendererArr[i];
            if (renderer.getTrackType() == 2) {
                arrayList.add(this.player.createMessage(renderer).setType(1).setPayload(videoOutput).send());
            }
            i++;
        }
        Object obj = this.videoOutput;
        if (obj == null || obj == videoOutput) {
            z = false;
        } else {
            try {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((PlayerMessage) it.next()).blockUntilDelivered(this.detachSurfaceTimeoutMs);
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            } catch (TimeoutException unused2) {
            }
            z = false;
            Object obj2 = this.videoOutput;
            Surface surface = this.ownedSurface;
            if (obj2 == surface) {
                surface.release();
                this.ownedSurface = null;
            }
        }
        this.videoOutput = videoOutput;
        if (z) {
            this.player.stop(false, ExoPlaybackException.createForUnexpected(new ExoTimeoutException(3), 1003));
        }
    }

    private void setNonVideoOutputSurfaceHolderInternal(SurfaceHolder nonVideoOutputSurfaceHolder) {
        this.surfaceHolderSurfaceIsVideoOutput = false;
        this.surfaceHolder = nonVideoOutputSurfaceHolder;
        nonVideoOutputSurfaceHolder.addCallback(this.componentListener);
        Surface surface = this.surfaceHolder.getSurface();
        if (surface != null && surface.isValid()) {
            Rect surfaceFrame = this.surfaceHolder.getSurfaceFrame();
            maybeNotifySurfaceSizeChanged(surfaceFrame.width(), surfaceFrame.height());
        } else {
            maybeNotifySurfaceSizeChanged(0, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeNotifySurfaceSizeChanged(int width, int height) {
        if (width == this.surfaceWidth && height == this.surfaceHeight) {
            return;
        }
        this.surfaceWidth = width;
        this.surfaceHeight = height;
        this.analyticsCollector.onSurfaceSizeChanged(width, height);
        Iterator<VideoListener> it = this.videoListeners.iterator();
        while (it.hasNext()) {
            it.next().onSurfaceSizeChanged(width, height);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendVolumeToRenderers() {
        sendRendererMessage(1, 2, Float.valueOf(this.audioVolume * this.audioFocusManager.getVolumeMultiplier()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySkipSilenceEnabledChanged() {
        this.analyticsCollector.onSkipSilenceEnabledChanged(this.skipSilenceEnabled);
        Iterator<AudioListener> it = this.audioListeners.iterator();
        while (it.hasNext()) {
            it.next().onSkipSilenceEnabledChanged(this.skipSilenceEnabled);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePlayWhenReady(boolean playWhenReady, int playerCommand, int playWhenReadyChangeReason) {
        int i = 0;
        boolean z = playWhenReady && playerCommand != -1;
        if (z && playerCommand != 1) {
            i = 1;
        }
        this.player.setPlayWhenReady(z, i, playWhenReadyChangeReason);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWakeAndWifiLock() {
        int playbackState = getPlaybackState();
        if (playbackState != 1) {
            if (playbackState == 2 || playbackState == 3) {
                this.wakeLockManager.setStayAwake(getPlayWhenReady() && !experimentalIsSleepingForOffload());
                this.wifiLockManager.setStayAwake(getPlayWhenReady());
                return;
            } else if (playbackState != 4) {
                throw new IllegalStateException();
            }
        }
        this.wakeLockManager.setStayAwake(false);
        this.wifiLockManager.setStayAwake(false);
    }

    private void verifyApplicationThread() {
        this.constructorFinished.blockUninterruptible();
        if (Thread.currentThread() != getApplicationLooper().getThread()) {
            String invariant = Util.formatInvariant("Player is accessed on the wrong thread.\nCurrent thread: '%s'\nExpected thread: '%s'\nSee https://exoplayer.dev/issues/player-accessed-on-wrong-thread", Thread.currentThread().getName(), getApplicationLooper().getThread().getName());
            if (this.throwsWhenUsingWrongThread) {
                throw new IllegalStateException(invariant);
            }
            Log.w(TAG, invariant, this.hasNotifiedFullWrongThreadWarning ? null : new IllegalStateException());
            this.hasNotifiedFullWrongThreadWarning = true;
        }
    }

    private void sendRendererMessage(int trackType, int messageType, Object payload) {
        for (Renderer renderer : this.renderers) {
            if (renderer.getTrackType() == trackType) {
                this.player.createMessage(renderer).setType(messageType).setPayload(payload).send();
            }
        }
    }

    private int initializeKeepSessionIdAudioTrack(int audioSessionId) {
        AudioTrack audioTrack = this.keepSessionIdAudioTrack;
        if (audioTrack != null && audioTrack.getAudioSessionId() != audioSessionId) {
            this.keepSessionIdAudioTrack.release();
            this.keepSessionIdAudioTrack = null;
        }
        if (this.keepSessionIdAudioTrack == null) {
            this.keepSessionIdAudioTrack = new AudioTrack(3, WearableStatusCodes.TARGET_NODE_NOT_CONNECTED, 4, 2, 2, 0, audioSessionId);
        }
        return this.keepSessionIdAudioTrack.getAudioSessionId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DeviceInfo createDeviceInfo(StreamVolumeManager streamVolumeManager) {
        return new DeviceInfo(0, streamVolumeManager.getMinVolume(), streamVolumeManager.getMaxVolume());
    }

    private final class ComponentListener implements VideoRendererEventListener, AudioRendererEventListener, TextOutput, MetadataOutput, SurfaceHolder.Callback, TextureView.SurfaceTextureListener, SphericalGLSurfaceView.VideoSurfaceListener, AudioFocusManager.PlayerControl, AudioBecomingNoisyManager.EventListener, StreamVolumeManager.Listener, Player.EventListener, ExoPlayer.AudioOffloadListener {
        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        private ComponentListener() {
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoEnabled(DecoderCounters counters) {
            SimpleExoPlayer.this.videoDecoderCounters = counters;
            SimpleExoPlayer.this.analyticsCollector.onVideoEnabled(counters);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
            SimpleExoPlayer.this.analyticsCollector.onVideoDecoderInitialized(decoderName, initializedTimestampMs, initializationDurationMs);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoInputFormatChanged(Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
            SimpleExoPlayer.this.videoFormat = format;
            SimpleExoPlayer.this.analyticsCollector.onVideoInputFormatChanged(format, decoderReuseEvaluation);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onDroppedFrames(int count, long elapsed) {
            SimpleExoPlayer.this.analyticsCollector.onDroppedFrames(count, elapsed);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoSizeChanged(VideoSize videoSize) {
            SimpleExoPlayer.this.videoSize = videoSize;
            SimpleExoPlayer.this.analyticsCollector.onVideoSizeChanged(videoSize);
            Iterator it = SimpleExoPlayer.this.videoListeners.iterator();
            while (it.hasNext()) {
                VideoListener videoListener = (VideoListener) it.next();
                videoListener.onVideoSizeChanged(videoSize);
                videoListener.onVideoSizeChanged(videoSize.width, videoSize.height, videoSize.unappliedRotationDegrees, videoSize.pixelWidthHeightRatio);
            }
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onRenderedFirstFrame(Object output, long renderTimeMs) {
            SimpleExoPlayer.this.analyticsCollector.onRenderedFirstFrame(output, renderTimeMs);
            if (SimpleExoPlayer.this.videoOutput == output) {
                Iterator it = SimpleExoPlayer.this.videoListeners.iterator();
                while (it.hasNext()) {
                    ((VideoListener) it.next()).onRenderedFirstFrame();
                }
            }
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoDecoderReleased(String decoderName) {
            SimpleExoPlayer.this.analyticsCollector.onVideoDecoderReleased(decoderName);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoDisabled(DecoderCounters counters) {
            SimpleExoPlayer.this.analyticsCollector.onVideoDisabled(counters);
            SimpleExoPlayer.this.videoFormat = null;
            SimpleExoPlayer.this.videoDecoderCounters = null;
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoFrameProcessingOffset(long totalProcessingOffsetUs, int frameCount) {
            SimpleExoPlayer.this.analyticsCollector.onVideoFrameProcessingOffset(totalProcessingOffsetUs, frameCount);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoCodecError(Exception videoCodecError) {
            SimpleExoPlayer.this.analyticsCollector.onVideoCodecError(videoCodecError);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioEnabled(DecoderCounters counters) {
            SimpleExoPlayer.this.audioDecoderCounters = counters;
            SimpleExoPlayer.this.analyticsCollector.onAudioEnabled(counters);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
            SimpleExoPlayer.this.analyticsCollector.onAudioDecoderInitialized(decoderName, initializedTimestampMs, initializationDurationMs);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioInputFormatChanged(Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
            SimpleExoPlayer.this.audioFormat = format;
            SimpleExoPlayer.this.analyticsCollector.onAudioInputFormatChanged(format, decoderReuseEvaluation);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioPositionAdvancing(long playoutStartSystemTimeMs) {
            SimpleExoPlayer.this.analyticsCollector.onAudioPositionAdvancing(playoutStartSystemTimeMs);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
            SimpleExoPlayer.this.analyticsCollector.onAudioUnderrun(bufferSize, bufferSizeMs, elapsedSinceLastFeedMs);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioDecoderReleased(String decoderName) {
            SimpleExoPlayer.this.analyticsCollector.onAudioDecoderReleased(decoderName);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioDisabled(DecoderCounters counters) {
            SimpleExoPlayer.this.analyticsCollector.onAudioDisabled(counters);
            SimpleExoPlayer.this.audioFormat = null;
            SimpleExoPlayer.this.audioDecoderCounters = null;
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onSkipSilenceEnabledChanged(boolean skipSilenceEnabled) {
            if (SimpleExoPlayer.this.skipSilenceEnabled == skipSilenceEnabled) {
                return;
            }
            SimpleExoPlayer.this.skipSilenceEnabled = skipSilenceEnabled;
            SimpleExoPlayer.this.notifySkipSilenceEnabledChanged();
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioSinkError(Exception audioSinkError) {
            SimpleExoPlayer.this.analyticsCollector.onAudioSinkError(audioSinkError);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioCodecError(Exception audioCodecError) {
            SimpleExoPlayer.this.analyticsCollector.onAudioCodecError(audioCodecError);
        }

        @Override // com.google.android.exoplayer2.text.TextOutput
        public void onCues(List<Cue> cues) {
            SimpleExoPlayer.this.currentCues = cues;
            Iterator it = SimpleExoPlayer.this.textOutputs.iterator();
            while (it.hasNext()) {
                ((TextOutput) it.next()).onCues(cues);
            }
        }

        @Override // com.google.android.exoplayer2.metadata.MetadataOutput
        public void onMetadata(Metadata metadata) {
            SimpleExoPlayer.this.analyticsCollector.onMetadata(metadata);
            SimpleExoPlayer.this.player.onMetadata(metadata);
            Iterator it = SimpleExoPlayer.this.metadataOutputs.iterator();
            while (it.hasNext()) {
                ((MetadataOutput) it.next()).onMetadata(metadata);
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder holder) {
            if (SimpleExoPlayer.this.surfaceHolderSurfaceIsVideoOutput) {
                SimpleExoPlayer.this.setVideoOutputInternal(holder.getSurface());
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(width, height);
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (SimpleExoPlayer.this.surfaceHolderSurfaceIsVideoOutput) {
                SimpleExoPlayer.this.setVideoOutputInternal(null);
            }
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(0, 0);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
            SimpleExoPlayer.this.setSurfaceTextureInternal(surfaceTexture);
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(width, height);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(width, height);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            SimpleExoPlayer.this.setVideoOutputInternal(null);
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(0, 0);
            return true;
        }

        @Override // com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView.VideoSurfaceListener
        public void onVideoSurfaceCreated(Surface surface) {
            SimpleExoPlayer.this.setVideoOutputInternal(surface);
        }

        @Override // com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView.VideoSurfaceListener
        public void onVideoSurfaceDestroyed(Surface surface) {
            SimpleExoPlayer.this.setVideoOutputInternal(null);
        }

        @Override // com.google.android.exoplayer2.AudioFocusManager.PlayerControl
        public void setVolumeMultiplier(float volumeMultiplier) {
            SimpleExoPlayer.this.sendVolumeToRenderers();
        }

        @Override // com.google.android.exoplayer2.AudioFocusManager.PlayerControl
        public void executePlayerCommand(int playerCommand) {
            boolean playWhenReady = SimpleExoPlayer.this.getPlayWhenReady();
            SimpleExoPlayer.this.updatePlayWhenReady(playWhenReady, playerCommand, SimpleExoPlayer.getPlayWhenReadyChangeReason(playWhenReady, playerCommand));
        }

        @Override // com.google.android.exoplayer2.AudioBecomingNoisyManager.EventListener
        public void onAudioBecomingNoisy() {
            SimpleExoPlayer.this.updatePlayWhenReady(false, -1, 3);
        }

        @Override // com.google.android.exoplayer2.StreamVolumeManager.Listener
        public void onStreamTypeChanged(int streamType) {
            DeviceInfo deviceInfoCreateDeviceInfo = SimpleExoPlayer.createDeviceInfo(SimpleExoPlayer.this.streamVolumeManager);
            if (deviceInfoCreateDeviceInfo.equals(SimpleExoPlayer.this.deviceInfo)) {
                return;
            }
            SimpleExoPlayer.this.deviceInfo = deviceInfoCreateDeviceInfo;
            Iterator it = SimpleExoPlayer.this.deviceListeners.iterator();
            while (it.hasNext()) {
                ((DeviceListener) it.next()).onDeviceInfoChanged(deviceInfoCreateDeviceInfo);
            }
        }

        @Override // com.google.android.exoplayer2.StreamVolumeManager.Listener
        public void onStreamVolumeChanged(int streamVolume, boolean streamMuted) {
            Iterator it = SimpleExoPlayer.this.deviceListeners.iterator();
            while (it.hasNext()) {
                ((DeviceListener) it.next()).onDeviceVolumeChanged(streamVolume, streamMuted);
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onIsLoadingChanged(boolean isLoading) {
            if (SimpleExoPlayer.this.priorityTaskManager != null) {
                if (!isLoading || SimpleExoPlayer.this.isPriorityTaskManagerRegistered) {
                    if (isLoading || !SimpleExoPlayer.this.isPriorityTaskManagerRegistered) {
                        return;
                    }
                    SimpleExoPlayer.this.priorityTaskManager.remove(0);
                    SimpleExoPlayer.this.isPriorityTaskManagerRegistered = false;
                    return;
                }
                SimpleExoPlayer.this.priorityTaskManager.add(0);
                SimpleExoPlayer.this.isPriorityTaskManagerRegistered = true;
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackStateChanged(int playbackState) {
            SimpleExoPlayer.this.updateWakeAndWifiLock();
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
            SimpleExoPlayer.this.updateWakeAndWifiLock();
        }

        @Override // com.google.android.exoplayer2.ExoPlayer.AudioOffloadListener
        public void onExperimentalSleepingForOffloadChanged(boolean sleepingForOffload) {
            SimpleExoPlayer.this.updateWakeAndWifiLock();
        }
    }

    private static final class FrameMetadataListener implements VideoFrameMetadataListener, CameraMotionListener, PlayerMessage.Target {
        public static final int MSG_SET_CAMERA_MOTION_LISTENER = 7;
        public static final int MSG_SET_SPHERICAL_SURFACE_VIEW = 10000;
        public static final int MSG_SET_VIDEO_FRAME_METADATA_LISTENER = 6;
        private CameraMotionListener cameraMotionListener;
        private CameraMotionListener internalCameraMotionListener;
        private VideoFrameMetadataListener internalVideoFrameMetadataListener;
        private VideoFrameMetadataListener videoFrameMetadataListener;

        private FrameMetadataListener() {
        }

        @Override // com.google.android.exoplayer2.PlayerMessage.Target
        public void handleMessage(int messageType, Object message) {
            if (messageType == 6) {
                this.videoFrameMetadataListener = (VideoFrameMetadataListener) message;
                return;
            }
            if (messageType == 7) {
                this.cameraMotionListener = (CameraMotionListener) message;
                return;
            }
            if (messageType != 10000) {
                return;
            }
            SphericalGLSurfaceView sphericalGLSurfaceView = (SphericalGLSurfaceView) message;
            if (sphericalGLSurfaceView == null) {
                this.internalVideoFrameMetadataListener = null;
                this.internalCameraMotionListener = null;
            } else {
                this.internalVideoFrameMetadataListener = sphericalGLSurfaceView.getVideoFrameMetadataListener();
                this.internalCameraMotionListener = sphericalGLSurfaceView.getCameraMotionListener();
            }
        }

        @Override // com.google.android.exoplayer2.video.VideoFrameMetadataListener
        public void onVideoFrameAboutToBeRendered(long presentationTimeUs, long releaseTimeNs, Format format, MediaFormat mediaFormat) {
            VideoFrameMetadataListener videoFrameMetadataListener = this.internalVideoFrameMetadataListener;
            if (videoFrameMetadataListener != null) {
                videoFrameMetadataListener.onVideoFrameAboutToBeRendered(presentationTimeUs, releaseTimeNs, format, mediaFormat);
            }
            VideoFrameMetadataListener videoFrameMetadataListener2 = this.videoFrameMetadataListener;
            if (videoFrameMetadataListener2 != null) {
                videoFrameMetadataListener2.onVideoFrameAboutToBeRendered(presentationTimeUs, releaseTimeNs, format, mediaFormat);
            }
        }

        @Override // com.google.android.exoplayer2.video.spherical.CameraMotionListener
        public void onCameraMotion(long timeUs, float[] rotation) {
            CameraMotionListener cameraMotionListener = this.internalCameraMotionListener;
            if (cameraMotionListener != null) {
                cameraMotionListener.onCameraMotion(timeUs, rotation);
            }
            CameraMotionListener cameraMotionListener2 = this.cameraMotionListener;
            if (cameraMotionListener2 != null) {
                cameraMotionListener2.onCameraMotion(timeUs, rotation);
            }
        }

        @Override // com.google.android.exoplayer2.video.spherical.CameraMotionListener
        public void onCameraMotionReset() {
            CameraMotionListener cameraMotionListener = this.internalCameraMotionListener;
            if (cameraMotionListener != null) {
                cameraMotionListener.onCameraMotionReset();
            }
            CameraMotionListener cameraMotionListener2 = this.cameraMotionListener;
            if (cameraMotionListener2 != null) {
                cameraMotionListener2.onCameraMotionReset();
            }
        }
    }
}
