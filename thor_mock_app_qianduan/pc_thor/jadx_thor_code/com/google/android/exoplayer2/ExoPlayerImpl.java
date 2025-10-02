package com.google.android.exoplayer2;

import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerImplInternal;
import com.google.android.exoplayer2.MediaSourceList;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.FlagSet;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.android.exoplayer2.util.ListenerSet;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes.dex */
final class ExoPlayerImpl extends BasePlayer implements ExoPlayer {
    private static final String TAG = "ExoPlayerImpl";
    private final AnalyticsCollector analyticsCollector;
    private final Looper applicationLooper;
    private final CopyOnWriteArraySet<ExoPlayer.AudioOffloadListener> audioOffloadListeners;
    private Player.Commands availableCommands;
    private final BandwidthMeter bandwidthMeter;
    private final Clock clock;
    final TrackSelectorResult emptyTrackSelectorResult;
    private boolean foregroundMode;
    private final ExoPlayerImplInternal internalPlayer;
    private final ListenerSet<Player.EventListener> listeners;
    private int maskingPeriodIndex;
    private int maskingWindowIndex;
    private long maskingWindowPositionMs;
    private MediaMetadata mediaMetadata;
    private final MediaSourceFactory mediaSourceFactory;
    private final List<MediaSourceHolderSnapshot> mediaSourceHolderSnapshots;
    private boolean pauseAtEndOfMediaItems;
    private boolean pendingDiscontinuity;
    private int pendingDiscontinuityReason;
    private int pendingOperationAcks;
    private int pendingPlayWhenReadyChangeReason;
    private final Timeline.Period period;
    final Player.Commands permanentAvailableCommands;
    private PlaybackInfo playbackInfo;
    private final HandlerWrapper playbackInfoUpdateHandler;
    private final ExoPlayerImplInternal.PlaybackInfoUpdateListener playbackInfoUpdateListener;
    private MediaMetadata playlistMetadata;
    private final Renderer[] renderers;
    private int repeatMode;
    private final long seekBackIncrementMs;
    private final long seekForwardIncrementMs;
    private SeekParameters seekParameters;
    private boolean shuffleModeEnabled;
    private ShuffleOrder shuffleOrder;
    private final TrackSelector trackSelector;
    private final boolean useLazyPreparation;

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface() {
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface(Surface surface) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceHolder(SurfaceHolder surfaceHolder) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceView(SurfaceView surfaceView) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoTextureView(TextureView textureView) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void decreaseDeviceVolume() {
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public ExoPlayer.AudioComponent getAudioComponent() {
        return null;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public ExoPlayer.DeviceComponent getDeviceComponent() {
        return null;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getDeviceVolume() {
        return 0;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getMaxSeekToPreviousPosition() {
        return 3000;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public ExoPlayer.MetadataComponent getMetadataComponent() {
        return null;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public ExoPlayer.TextComponent getTextComponent() {
        return null;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public ExoPlayer.VideoComponent getVideoComponent() {
        return null;
    }

    @Override // com.google.android.exoplayer2.Player
    public float getVolume() {
        return 1.0f;
    }

    @Override // com.google.android.exoplayer2.Player
    public void increaseDeviceVolume() {
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isDeviceMuted() {
        return false;
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceMuted(boolean muted) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceVolume(int volume) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurface(Surface surface) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceHolder(SurfaceHolder surfaceHolder) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceView(SurfaceView surfaceView) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoTextureView(TextureView textureView) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVolume(float audioVolume) {
    }

    public ExoPlayerImpl(Renderer[] renderers, TrackSelector trackSelector, MediaSourceFactory mediaSourceFactory, LoadControl loadControl, BandwidthMeter bandwidthMeter, AnalyticsCollector analyticsCollector, boolean useLazyPreparation, SeekParameters seekParameters, long seekBackIncrementMs, long seekForwardIncrementMs, LivePlaybackSpeedControl livePlaybackSpeedControl, long releaseTimeoutMs, boolean pauseAtEndOfMediaItems, Clock clock, Looper applicationLooper, Player wrappingPlayer, Player.Commands additionalPermanentAvailableCommands) {
        String hexString = Integer.toHexString(System.identityHashCode(this));
        String str = Util.DEVICE_DEBUG_INFO;
        Log.i(TAG, new StringBuilder(String.valueOf(hexString).length() + 30 + String.valueOf(str).length()).append("Init ").append(hexString).append(" [ExoPlayerLib/2.15.0] [").append(str).append("]").toString());
        Assertions.checkState(renderers.length > 0);
        this.renderers = (Renderer[]) Assertions.checkNotNull(renderers);
        this.trackSelector = (TrackSelector) Assertions.checkNotNull(trackSelector);
        this.mediaSourceFactory = mediaSourceFactory;
        this.bandwidthMeter = bandwidthMeter;
        this.analyticsCollector = analyticsCollector;
        this.useLazyPreparation = useLazyPreparation;
        this.seekParameters = seekParameters;
        this.seekBackIncrementMs = seekBackIncrementMs;
        this.seekForwardIncrementMs = seekForwardIncrementMs;
        this.pauseAtEndOfMediaItems = pauseAtEndOfMediaItems;
        this.applicationLooper = applicationLooper;
        this.clock = clock;
        this.repeatMode = 0;
        final Player player = wrappingPlayer != null ? wrappingPlayer : this;
        this.listeners = new ListenerSet<>(applicationLooper, clock, new ListenerSet.IterationFinishedEvent() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda15
            @Override // com.google.android.exoplayer2.util.ListenerSet.IterationFinishedEvent
            public final void invoke(Object obj, FlagSet flagSet) {
                ((Player.EventListener) obj).onEvents(player, new Player.Events(flagSet));
            }
        });
        this.audioOffloadListeners = new CopyOnWriteArraySet<>();
        this.mediaSourceHolderSnapshots = new ArrayList();
        this.shuffleOrder = new ShuffleOrder.DefaultShuffleOrder(0);
        TrackSelectorResult trackSelectorResult = new TrackSelectorResult(new RendererConfiguration[renderers.length], new ExoTrackSelection[renderers.length], null);
        this.emptyTrackSelectorResult = trackSelectorResult;
        this.period = new Timeline.Period();
        Player.Commands commandsBuild = new Player.Commands.Builder().addAll(1, 2, 12, 13, 14, 15, 16, 17, 18, 19).addAll(additionalPermanentAvailableCommands).build();
        this.permanentAvailableCommands = commandsBuild;
        this.availableCommands = new Player.Commands.Builder().addAll(commandsBuild).add(3).add(9).build();
        this.mediaMetadata = MediaMetadata.EMPTY;
        this.playlistMetadata = MediaMetadata.EMPTY;
        this.maskingWindowIndex = -1;
        this.playbackInfoUpdateHandler = clock.createHandler(applicationLooper, null);
        ExoPlayerImplInternal.PlaybackInfoUpdateListener playbackInfoUpdateListener = new ExoPlayerImplInternal.PlaybackInfoUpdateListener() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda16
            @Override // com.google.android.exoplayer2.ExoPlayerImplInternal.PlaybackInfoUpdateListener
            public final void onPlaybackInfoUpdate(ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate) {
                this.f$0.m128lambda$new$2$comgoogleandroidexoplayer2ExoPlayerImpl(playbackInfoUpdate);
            }
        };
        this.playbackInfoUpdateListener = playbackInfoUpdateListener;
        this.playbackInfo = PlaybackInfo.createDummy(trackSelectorResult);
        if (analyticsCollector != null) {
            analyticsCollector.setPlayer(player, applicationLooper);
            addListener((Player.Listener) analyticsCollector);
            bandwidthMeter.addEventListener(new Handler(applicationLooper), analyticsCollector);
        }
        this.internalPlayer = new ExoPlayerImplInternal(renderers, trackSelector, trackSelectorResult, loadControl, bandwidthMeter, this.repeatMode, this.shuffleModeEnabled, analyticsCollector, seekParameters, livePlaybackSpeedControl, releaseTimeoutMs, pauseAtEndOfMediaItems, applicationLooper, clock, playbackInfoUpdateListener);
    }

    /* renamed from: lambda$new$2$com-google-android-exoplayer2-ExoPlayerImpl, reason: not valid java name */
    /* synthetic */ void m128lambda$new$2$comgoogleandroidexoplayer2ExoPlayerImpl(final ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate) {
        this.playbackInfoUpdateHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m127lambda$new$1$comgoogleandroidexoplayer2ExoPlayerImpl(playbackInfoUpdate);
            }
        });
    }

    public void experimentalSetForegroundModeTimeoutMs(long timeoutMs) {
        this.internalPlayer.experimentalSetForegroundModeTimeoutMs(timeoutMs);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void experimentalSetOffloadSchedulingEnabled(boolean offloadSchedulingEnabled) {
        this.internalPlayer.experimentalSetOffloadSchedulingEnabled(offloadSchedulingEnabled);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public boolean experimentalIsSleepingForOffload() {
        return this.playbackInfo.sleepingForOffload;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public Looper getPlaybackLooper() {
        return this.internalPlayer.getPlaybackLooper();
    }

    @Override // com.google.android.exoplayer2.Player
    public Looper getApplicationLooper() {
        return this.applicationLooper;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public Clock getClock() {
        return this.clock;
    }

    @Override // com.google.android.exoplayer2.Player
    public void addListener(Player.Listener listener) {
        addListener((Player.EventListener) listener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addListener(Player.EventListener listener) {
        this.listeners.add(listener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeListener(Player.Listener listener) {
        removeListener((Player.EventListener) listener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeListener(Player.EventListener listener) {
        this.listeners.remove(listener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addAudioOffloadListener(ExoPlayer.AudioOffloadListener listener) {
        this.audioOffloadListeners.add(listener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void removeAudioOffloadListener(ExoPlayer.AudioOffloadListener listener) {
        this.audioOffloadListeners.remove(listener);
    }

    @Override // com.google.android.exoplayer2.Player
    public Player.Commands getAvailableCommands() {
        return this.availableCommands;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackState() {
        return this.playbackInfo.playbackState;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackSuppressionReason() {
        return this.playbackInfo.playbackSuppressionReason;
    }

    @Override // com.google.android.exoplayer2.Player
    public ExoPlaybackException getPlayerError() {
        return this.playbackInfo.playbackError;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void retry() {
        prepare();
    }

    @Override // com.google.android.exoplayer2.Player
    public void prepare() {
        if (this.playbackInfo.playbackState != 1) {
            return;
        }
        PlaybackInfo playbackInfoCopyWithPlaybackError = this.playbackInfo.copyWithPlaybackError(null);
        PlaybackInfo playbackInfoCopyWithPlaybackState = playbackInfoCopyWithPlaybackError.copyWithPlaybackState(playbackInfoCopyWithPlaybackError.timeline.isEmpty() ? 4 : 2);
        this.pendingOperationAcks++;
        this.internalPlayer.prepare();
        updatePlaybackInfo(playbackInfoCopyWithPlaybackState, 1, 1, false, false, 5, C.TIME_UNSET, -1);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void prepare(MediaSource mediaSource) {
        setMediaSource(mediaSource);
        prepare();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void prepare(MediaSource mediaSource, boolean resetPosition, boolean resetState) {
        setMediaSource(mediaSource, resetPosition);
        prepare();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> mediaItems, boolean resetPosition) {
        setMediaSources(createMediaSources(mediaItems), resetPosition);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> mediaItems, int startWindowIndex, long startPositionMs) {
        setMediaSources(createMediaSources(mediaItems), startWindowIndex, startPositionMs);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource) {
        setMediaSources(Collections.singletonList(mediaSource));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource, long startPositionMs) {
        setMediaSources(Collections.singletonList(mediaSource), 0, startPositionMs);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource, boolean resetPosition) {
        setMediaSources(Collections.singletonList(mediaSource), resetPosition);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> mediaSources) {
        setMediaSources(mediaSources, true);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> mediaSources, boolean resetPosition) {
        setMediaSourcesInternal(mediaSources, -1, C.TIME_UNSET, resetPosition);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> mediaSources, int startWindowIndex, long startPositionMs) {
        setMediaSourcesInternal(mediaSources, startWindowIndex, startPositionMs, false);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItems(int index, List<MediaItem> mediaItems) {
        addMediaSources(Math.min(index, this.mediaSourceHolderSnapshots.size()), createMediaSources(mediaItems));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSource(MediaSource mediaSource) {
        addMediaSources(Collections.singletonList(mediaSource));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSource(int index, MediaSource mediaSource) {
        addMediaSources(index, Collections.singletonList(mediaSource));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSources(List<MediaSource> mediaSources) {
        addMediaSources(this.mediaSourceHolderSnapshots.size(), mediaSources);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSources(int index, List<MediaSource> mediaSources) {
        Assertions.checkArgument(index >= 0);
        Timeline currentTimeline = getCurrentTimeline();
        this.pendingOperationAcks++;
        List<MediaSourceList.MediaSourceHolder> listAddMediaSourceHolders = addMediaSourceHolders(index, mediaSources);
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, getPeriodPositionAfterTimelineChanged(currentTimeline, timelineCreateMaskingTimeline));
        this.internalPlayer.addMediaSources(index, listAddMediaSourceHolders, this.shuffleOrder);
        updatePlaybackInfo(playbackInfoMaskTimelineAndPosition, 0, 1, false, false, 5, C.TIME_UNSET, -1);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeMediaItems(int fromIndex, int toIndex) {
        PlaybackInfo playbackInfoRemoveMediaItemsInternal = removeMediaItemsInternal(fromIndex, Math.min(toIndex, this.mediaSourceHolderSnapshots.size()));
        updatePlaybackInfo(playbackInfoRemoveMediaItemsInternal, 0, 1, false, !playbackInfoRemoveMediaItemsInternal.periodId.periodUid.equals(this.playbackInfo.periodId.periodUid), 4, getCurrentPositionUsInternal(playbackInfoRemoveMediaItemsInternal), -1);
    }

    @Override // com.google.android.exoplayer2.Player
    public void moveMediaItems(int fromIndex, int toIndex, int newFromIndex) {
        Assertions.checkArgument(fromIndex >= 0 && fromIndex <= toIndex && toIndex <= this.mediaSourceHolderSnapshots.size() && newFromIndex >= 0);
        Timeline currentTimeline = getCurrentTimeline();
        this.pendingOperationAcks++;
        int iMin = Math.min(newFromIndex, this.mediaSourceHolderSnapshots.size() - (toIndex - fromIndex));
        Util.moveItems(this.mediaSourceHolderSnapshots, fromIndex, toIndex, iMin);
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, getPeriodPositionAfterTimelineChanged(currentTimeline, timelineCreateMaskingTimeline));
        this.internalPlayer.moveMediaSources(fromIndex, toIndex, iMin, this.shuffleOrder);
        updatePlaybackInfo(playbackInfoMaskTimelineAndPosition, 0, 1, false, false, 5, C.TIME_UNSET, -1);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setShuffleOrder(ShuffleOrder shuffleOrder) {
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, getPeriodPositionOrMaskWindowPosition(timelineCreateMaskingTimeline, getCurrentWindowIndex(), getCurrentPosition()));
        this.pendingOperationAcks++;
        this.shuffleOrder = shuffleOrder;
        this.internalPlayer.setShuffleOrder(shuffleOrder);
        updatePlaybackInfo(playbackInfoMaskTimelineAndPosition, 0, 1, false, false, 5, C.TIME_UNSET, -1);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlayWhenReady(boolean playWhenReady) {
        setPlayWhenReady(playWhenReady, 0, 1);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setPauseAtEndOfMediaItems(boolean pauseAtEndOfMediaItems) {
        if (this.pauseAtEndOfMediaItems == pauseAtEndOfMediaItems) {
            return;
        }
        this.pauseAtEndOfMediaItems = pauseAtEndOfMediaItems;
        this.internalPlayer.setPauseAtEndOfWindow(pauseAtEndOfMediaItems);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public boolean getPauseAtEndOfMediaItems() {
        return this.pauseAtEndOfMediaItems;
    }

    public void setPlayWhenReady(boolean playWhenReady, int playbackSuppressionReason, int playWhenReadyChangeReason) {
        if (this.playbackInfo.playWhenReady == playWhenReady && this.playbackInfo.playbackSuppressionReason == playbackSuppressionReason) {
            return;
        }
        this.pendingOperationAcks++;
        PlaybackInfo playbackInfoCopyWithPlayWhenReady = this.playbackInfo.copyWithPlayWhenReady(playWhenReady, playbackSuppressionReason);
        this.internalPlayer.setPlayWhenReady(playWhenReady, playbackSuppressionReason);
        updatePlaybackInfo(playbackInfoCopyWithPlayWhenReady, 0, playWhenReadyChangeReason, false, false, 5, C.TIME_UNSET, -1);
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getPlayWhenReady() {
        return this.playbackInfo.playWhenReady;
    }

    @Override // com.google.android.exoplayer2.Player
    public void setRepeatMode(final int repeatMode) {
        if (this.repeatMode != repeatMode) {
            this.repeatMode = repeatMode;
            this.internalPlayer.setRepeatMode(repeatMode);
            this.listeners.queueEvent(9, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda12
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onRepeatModeChanged(repeatMode);
                }
            });
            updateAvailableCommands();
            this.listeners.flushEvents();
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public int getRepeatMode() {
        return this.repeatMode;
    }

    @Override // com.google.android.exoplayer2.Player
    public void setShuffleModeEnabled(final boolean shuffleModeEnabled) {
        if (this.shuffleModeEnabled != shuffleModeEnabled) {
            this.shuffleModeEnabled = shuffleModeEnabled;
            this.internalPlayer.setShuffleModeEnabled(shuffleModeEnabled);
            this.listeners.queueEvent(10, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda17
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onShuffleModeEnabledChanged(shuffleModeEnabled);
                }
            });
            updateAvailableCommands();
            this.listeners.flushEvents();
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getShuffleModeEnabled() {
        return this.shuffleModeEnabled;
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isLoading() {
        return this.playbackInfo.isLoading;
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekTo(int windowIndex, long positionMs) {
        Timeline timeline = this.playbackInfo.timeline;
        if (windowIndex < 0 || (!timeline.isEmpty() && windowIndex >= timeline.getWindowCount())) {
            throw new IllegalSeekPositionException(timeline, windowIndex, positionMs);
        }
        this.pendingOperationAcks++;
        if (isPlayingAd()) {
            Log.w(TAG, "seekTo ignored because an ad is playing");
            ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate = new ExoPlayerImplInternal.PlaybackInfoUpdate(this.playbackInfo);
            playbackInfoUpdate.incrementPendingOperationAcks(1);
            this.playbackInfoUpdateListener.onPlaybackInfoUpdate(playbackInfoUpdate);
            return;
        }
        int i = getPlaybackState() != 1 ? 2 : 1;
        int currentWindowIndex = getCurrentWindowIndex();
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo.copyWithPlaybackState(i), timeline, getPeriodPositionOrMaskWindowPosition(timeline, windowIndex, positionMs));
        this.internalPlayer.seekTo(timeline, windowIndex, C.msToUs(positionMs));
        updatePlaybackInfo(playbackInfoMaskTimelineAndPosition, 0, 1, true, true, 1, getCurrentPositionUsInternal(playbackInfoMaskTimelineAndPosition), currentWindowIndex);
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekBackIncrement() {
        return this.seekBackIncrementMs;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekForwardIncrement() {
        return this.seekForwardIncrementMs;
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        if (playbackParameters == null) {
            playbackParameters = PlaybackParameters.DEFAULT;
        }
        if (this.playbackInfo.playbackParameters.equals(playbackParameters)) {
            return;
        }
        PlaybackInfo playbackInfoCopyWithPlaybackParameters = this.playbackInfo.copyWithPlaybackParameters(playbackParameters);
        this.pendingOperationAcks++;
        this.internalPlayer.setPlaybackParameters(playbackParameters);
        updatePlaybackInfo(playbackInfoCopyWithPlaybackParameters, 0, 1, false, false, 5, C.TIME_UNSET, -1);
    }

    @Override // com.google.android.exoplayer2.Player
    public PlaybackParameters getPlaybackParameters() {
        return this.playbackInfo.playbackParameters;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setSeekParameters(SeekParameters seekParameters) {
        if (seekParameters == null) {
            seekParameters = SeekParameters.DEFAULT;
        }
        if (this.seekParameters.equals(seekParameters)) {
            return;
        }
        this.seekParameters = seekParameters;
        this.internalPlayer.setSeekParameters(seekParameters);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public SeekParameters getSeekParameters() {
        return this.seekParameters;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setForegroundMode(boolean foregroundMode) {
        if (this.foregroundMode != foregroundMode) {
            this.foregroundMode = foregroundMode;
            if (this.internalPlayer.setForegroundMode(foregroundMode)) {
                return;
            }
            stop(false, ExoPlaybackException.createForUnexpected(new ExoTimeoutException(2), 1003));
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public void stop(boolean reset) {
        stop(reset, null);
    }

    public void stop(boolean reset, ExoPlaybackException error) {
        PlaybackInfo playbackInfoCopyWithLoadingMediaPeriodId;
        if (reset) {
            playbackInfoCopyWithLoadingMediaPeriodId = removeMediaItemsInternal(0, this.mediaSourceHolderSnapshots.size()).copyWithPlaybackError(null);
        } else {
            PlaybackInfo playbackInfo = this.playbackInfo;
            playbackInfoCopyWithLoadingMediaPeriodId = playbackInfo.copyWithLoadingMediaPeriodId(playbackInfo.periodId);
            playbackInfoCopyWithLoadingMediaPeriodId.bufferedPositionUs = playbackInfoCopyWithLoadingMediaPeriodId.positionUs;
            playbackInfoCopyWithLoadingMediaPeriodId.totalBufferedDurationUs = 0L;
        }
        PlaybackInfo playbackInfoCopyWithPlaybackState = playbackInfoCopyWithLoadingMediaPeriodId.copyWithPlaybackState(1);
        if (error != null) {
            playbackInfoCopyWithPlaybackState = playbackInfoCopyWithPlaybackState.copyWithPlaybackError(error);
        }
        PlaybackInfo playbackInfo2 = playbackInfoCopyWithPlaybackState;
        this.pendingOperationAcks++;
        this.internalPlayer.stop();
        updatePlaybackInfo(playbackInfo2, 0, 1, false, playbackInfo2.timeline.isEmpty() && !this.playbackInfo.timeline.isEmpty(), 4, getCurrentPositionUsInternal(playbackInfo2), -1);
    }

    @Override // com.google.android.exoplayer2.Player
    public void release() {
        String hexString = Integer.toHexString(System.identityHashCode(this));
        String str = Util.DEVICE_DEBUG_INFO;
        String strRegisteredModules = ExoPlayerLibraryInfo.registeredModules();
        Log.i(TAG, new StringBuilder(String.valueOf(hexString).length() + 36 + String.valueOf(str).length() + String.valueOf(strRegisteredModules).length()).append("Release ").append(hexString).append(" [ExoPlayerLib/2.15.0] [").append(str).append("] [").append(strRegisteredModules).append("]").toString());
        if (!this.internalPlayer.release()) {
            this.listeners.sendEvent(11, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda10
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onPlayerError(ExoPlaybackException.createForUnexpected(new ExoTimeoutException(1), 1003));
                }
            });
        }
        this.listeners.release();
        this.playbackInfoUpdateHandler.removeCallbacksAndMessages(null);
        AnalyticsCollector analyticsCollector = this.analyticsCollector;
        if (analyticsCollector != null) {
            this.bandwidthMeter.removeEventListener(analyticsCollector);
        }
        PlaybackInfo playbackInfoCopyWithPlaybackState = this.playbackInfo.copyWithPlaybackState(1);
        this.playbackInfo = playbackInfoCopyWithPlaybackState;
        PlaybackInfo playbackInfoCopyWithLoadingMediaPeriodId = playbackInfoCopyWithPlaybackState.copyWithLoadingMediaPeriodId(playbackInfoCopyWithPlaybackState.periodId);
        this.playbackInfo = playbackInfoCopyWithLoadingMediaPeriodId;
        playbackInfoCopyWithLoadingMediaPeriodId.bufferedPositionUs = playbackInfoCopyWithLoadingMediaPeriodId.positionUs;
        this.playbackInfo.totalBufferedDurationUs = 0L;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public PlayerMessage createMessage(PlayerMessage.Target target) {
        return new PlayerMessage(this.internalPlayer, target, this.playbackInfo.timeline, getCurrentWindowIndex(), this.clock, this.internalPlayer.getPlaybackLooper());
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentPeriodIndex() {
        if (this.playbackInfo.timeline.isEmpty()) {
            return this.maskingPeriodIndex;
        }
        return this.playbackInfo.timeline.getIndexOfPeriod(this.playbackInfo.periodId.periodUid);
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentWindowIndex() {
        int currentWindowIndexInternal = getCurrentWindowIndexInternal();
        if (currentWindowIndexInternal == -1) {
            return 0;
        }
        return currentWindowIndexInternal;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getDuration() {
        if (isPlayingAd()) {
            MediaSource.MediaPeriodId mediaPeriodId = this.playbackInfo.periodId;
            this.playbackInfo.timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
            return C.usToMs(this.period.getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup));
        }
        return getContentDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getCurrentPosition() {
        return C.usToMs(getCurrentPositionUsInternal(this.playbackInfo));
    }

    @Override // com.google.android.exoplayer2.Player
    public long getBufferedPosition() {
        if (isPlayingAd()) {
            if (this.playbackInfo.loadingMediaPeriodId.equals(this.playbackInfo.periodId)) {
                return C.usToMs(this.playbackInfo.bufferedPositionUs);
            }
            return getDuration();
        }
        return getContentBufferedPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getTotalBufferedDuration() {
        return C.usToMs(this.playbackInfo.totalBufferedDurationUs);
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isPlayingAd() {
        return this.playbackInfo.periodId.isAd();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdGroupIndex() {
        if (isPlayingAd()) {
            return this.playbackInfo.periodId.adGroupIndex;
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdIndexInAdGroup() {
        if (isPlayingAd()) {
            return this.playbackInfo.periodId.adIndexInAdGroup;
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentPosition() {
        if (isPlayingAd()) {
            this.playbackInfo.timeline.getPeriodByUid(this.playbackInfo.periodId.periodUid, this.period);
            if (this.playbackInfo.requestedContentPositionUs == C.TIME_UNSET) {
                return this.playbackInfo.timeline.getWindow(getCurrentWindowIndex(), this.window).getDefaultPositionMs();
            }
            return this.period.getPositionInWindowMs() + C.usToMs(this.playbackInfo.requestedContentPositionUs);
        }
        return getCurrentPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentBufferedPosition() {
        if (this.playbackInfo.timeline.isEmpty()) {
            return this.maskingWindowPositionMs;
        }
        if (this.playbackInfo.loadingMediaPeriodId.windowSequenceNumber != this.playbackInfo.periodId.windowSequenceNumber) {
            return this.playbackInfo.timeline.getWindow(getCurrentWindowIndex(), this.window).getDurationMs();
        }
        long j = this.playbackInfo.bufferedPositionUs;
        if (this.playbackInfo.loadingMediaPeriodId.isAd()) {
            Timeline.Period periodByUid = this.playbackInfo.timeline.getPeriodByUid(this.playbackInfo.loadingMediaPeriodId.periodUid, this.period);
            long adGroupTimeUs = periodByUid.getAdGroupTimeUs(this.playbackInfo.loadingMediaPeriodId.adGroupIndex);
            j = adGroupTimeUs == Long.MIN_VALUE ? periodByUid.durationUs : adGroupTimeUs;
        }
        return C.usToMs(periodPositionUsToWindowPositionUs(this.playbackInfo.timeline, this.playbackInfo.loadingMediaPeriodId, j));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public int getRendererCount() {
        return this.renderers.length;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public int getRendererType(int index) {
        return this.renderers[index].getTrackType();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public TrackSelector getTrackSelector() {
        return this.trackSelector;
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackGroupArray getCurrentTrackGroups() {
        return this.playbackInfo.trackGroups;
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackSelectionArray getCurrentTrackSelections() {
        return new TrackSelectionArray(this.playbackInfo.trackSelectorResult.selections);
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public List<Metadata> getCurrentStaticMetadata() {
        return this.playbackInfo.staticMetadata;
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getMediaMetadata() {
        return this.mediaMetadata;
    }

    public void onMetadata(Metadata metadata) {
        MediaMetadata mediaMetadataBuild = this.mediaMetadata.buildUpon().populateFromMetadata(metadata).build();
        if (mediaMetadataBuild.equals(this.mediaMetadata)) {
            return;
        }
        this.mediaMetadata = mediaMetadataBuild;
        this.listeners.sendEvent(15, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda14
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                this.f$0.m129lambda$onMetadata$6$comgoogleandroidexoplayer2ExoPlayerImpl((Player.EventListener) obj);
            }
        });
    }

    /* renamed from: lambda$onMetadata$6$com-google-android-exoplayer2-ExoPlayerImpl, reason: not valid java name */
    /* synthetic */ void m129lambda$onMetadata$6$comgoogleandroidexoplayer2ExoPlayerImpl(Player.EventListener eventListener) {
        eventListener.onMediaMetadataChanged(this.mediaMetadata);
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getPlaylistMetadata() {
        return this.playlistMetadata;
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaylistMetadata(MediaMetadata playlistMetadata) {
        Assertions.checkNotNull(playlistMetadata);
        if (playlistMetadata.equals(this.playlistMetadata)) {
            return;
        }
        this.playlistMetadata = playlistMetadata;
        this.listeners.sendEvent(16, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda11
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                this.f$0.m130xadc0e460((Player.EventListener) obj);
            }
        });
    }

    /* renamed from: lambda$setPlaylistMetadata$7$com-google-android-exoplayer2-ExoPlayerImpl, reason: not valid java name */
    /* synthetic */ void m130xadc0e460(Player.EventListener eventListener) {
        eventListener.onPlaylistMetadataChanged(this.playlistMetadata);
    }

    @Override // com.google.android.exoplayer2.Player
    public Timeline getCurrentTimeline() {
        return this.playbackInfo.timeline;
    }

    @Override // com.google.android.exoplayer2.Player
    public AudioAttributes getAudioAttributes() {
        return AudioAttributes.DEFAULT;
    }

    @Override // com.google.android.exoplayer2.Player
    public VideoSize getVideoSize() {
        return VideoSize.UNKNOWN;
    }

    @Override // com.google.android.exoplayer2.Player
    public ImmutableList<Cue> getCurrentCues() {
        return ImmutableList.of();
    }

    @Override // com.google.android.exoplayer2.Player
    public DeviceInfo getDeviceInfo() {
        return DeviceInfo.UNKNOWN;
    }

    private int getCurrentWindowIndexInternal() {
        if (this.playbackInfo.timeline.isEmpty()) {
            return this.maskingWindowIndex;
        }
        return this.playbackInfo.timeline.getPeriodByUid(this.playbackInfo.periodId.periodUid, this.period).windowIndex;
    }

    private long getCurrentPositionUsInternal(PlaybackInfo playbackInfo) {
        if (playbackInfo.timeline.isEmpty()) {
            return C.msToUs(this.maskingWindowPositionMs);
        }
        if (playbackInfo.periodId.isAd()) {
            return playbackInfo.positionUs;
        }
        return periodPositionUsToWindowPositionUs(playbackInfo.timeline, playbackInfo.periodId, playbackInfo.positionUs);
    }

    private List<MediaSource> createMediaSources(List<MediaItem> mediaItems) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < mediaItems.size(); i++) {
            arrayList.add(this.mediaSourceFactory.createMediaSource(mediaItems.get(i)));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handlePlaybackInfo, reason: merged with bridge method [inline-methods] */
    public void m127lambda$new$1$comgoogleandroidexoplayer2ExoPlayerImpl(ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate) {
        long j;
        boolean z;
        this.pendingOperationAcks -= playbackInfoUpdate.operationAcks;
        boolean z2 = true;
        if (playbackInfoUpdate.positionDiscontinuity) {
            this.pendingDiscontinuityReason = playbackInfoUpdate.discontinuityReason;
            this.pendingDiscontinuity = true;
        }
        if (playbackInfoUpdate.hasPlayWhenReadyChangeReason) {
            this.pendingPlayWhenReadyChangeReason = playbackInfoUpdate.playWhenReadyChangeReason;
        }
        if (this.pendingOperationAcks == 0) {
            Timeline timeline = playbackInfoUpdate.playbackInfo.timeline;
            if (!this.playbackInfo.timeline.isEmpty() && timeline.isEmpty()) {
                this.maskingWindowIndex = -1;
                this.maskingWindowPositionMs = 0L;
                this.maskingPeriodIndex = 0;
            }
            if (!timeline.isEmpty()) {
                List<Timeline> childTimelines = ((PlaylistTimeline) timeline).getChildTimelines();
                Assertions.checkState(childTimelines.size() == this.mediaSourceHolderSnapshots.size());
                for (int i = 0; i < childTimelines.size(); i++) {
                    this.mediaSourceHolderSnapshots.get(i).timeline = childTimelines.get(i);
                }
            }
            boolean z3 = this.pendingDiscontinuity;
            long jPeriodPositionUsToWindowPositionUs = C.TIME_UNSET;
            if (z3) {
                if (playbackInfoUpdate.playbackInfo.periodId.equals(this.playbackInfo.periodId) && playbackInfoUpdate.playbackInfo.discontinuityStartPositionUs == this.playbackInfo.positionUs) {
                    z2 = false;
                }
                if (z2) {
                    if (timeline.isEmpty() || playbackInfoUpdate.playbackInfo.periodId.isAd()) {
                        jPeriodPositionUsToWindowPositionUs = playbackInfoUpdate.playbackInfo.discontinuityStartPositionUs;
                    } else {
                        jPeriodPositionUsToWindowPositionUs = periodPositionUsToWindowPositionUs(timeline, playbackInfoUpdate.playbackInfo.periodId, playbackInfoUpdate.playbackInfo.discontinuityStartPositionUs);
                    }
                }
                j = jPeriodPositionUsToWindowPositionUs;
                z = z2;
            } else {
                j = -9223372036854775807L;
                z = false;
            }
            this.pendingDiscontinuity = false;
            updatePlaybackInfo(playbackInfoUpdate.playbackInfo, 1, this.pendingPlayWhenReadyChangeReason, false, z, this.pendingDiscontinuityReason, j, -1);
        }
    }

    private void updatePlaybackInfo(final PlaybackInfo playbackInfo, final int timelineChangeReason, final int playWhenReadyChangeReason, boolean seekProcessed, boolean positionDiscontinuity, final int positionDiscontinuityReason, long discontinuityWindowStartPositionUs, int oldMaskingWindowIndex) {
        PlaybackInfo playbackInfo2 = this.playbackInfo;
        this.playbackInfo = playbackInfo;
        Pair<Boolean, Integer> pairEvaluateMediaItemTransitionReason = evaluateMediaItemTransitionReason(playbackInfo, playbackInfo2, positionDiscontinuity, positionDiscontinuityReason, !playbackInfo2.timeline.equals(playbackInfo.timeline));
        boolean zBooleanValue = ((Boolean) pairEvaluateMediaItemTransitionReason.first).booleanValue();
        final int iIntValue = ((Integer) pairEvaluateMediaItemTransitionReason.second).intValue();
        MediaMetadata mediaMetadataBuild = this.mediaMetadata;
        if (zBooleanValue) {
            mediaItem = playbackInfo.timeline.isEmpty() ? null : playbackInfo.timeline.getWindow(playbackInfo.timeline.getPeriodByUid(playbackInfo.periodId.periodUid, this.period).windowIndex, this.window).mediaItem;
            mediaMetadataBuild = mediaItem != null ? mediaItem.mediaMetadata : MediaMetadata.EMPTY;
        }
        if (!playbackInfo2.staticMetadata.equals(playbackInfo.staticMetadata)) {
            mediaMetadataBuild = mediaMetadataBuild.buildUpon().populateFromMetadata(playbackInfo.staticMetadata).build();
        }
        boolean z = !mediaMetadataBuild.equals(this.mediaMetadata);
        this.mediaMetadata = mediaMetadataBuild;
        if (!playbackInfo2.timeline.equals(playbackInfo.timeline)) {
            this.listeners.queueEvent(0, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda18
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    Player.EventListener eventListener = (Player.EventListener) obj;
                    eventListener.onTimelineChanged(playbackInfo.timeline, timelineChangeReason);
                }
            });
        }
        if (positionDiscontinuity) {
            final Player.PositionInfo previousPositionInfo = getPreviousPositionInfo(positionDiscontinuityReason, playbackInfo2, oldMaskingWindowIndex);
            final Player.PositionInfo positionInfo = getPositionInfo(discontinuityWindowStartPositionUs);
            this.listeners.queueEvent(12, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda1
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$updatePlaybackInfo$9(positionDiscontinuityReason, previousPositionInfo, positionInfo, (Player.EventListener) obj);
                }
            });
        }
        if (zBooleanValue) {
            this.listeners.queueEvent(1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda2
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onMediaItemTransition(mediaItem, iIntValue);
                }
            });
        }
        if (playbackInfo2.playbackError != playbackInfo.playbackError) {
            this.listeners.queueEvent(11, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda3
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onPlayerErrorChanged(playbackInfo.playbackError);
                }
            });
            if (playbackInfo.playbackError != null) {
                this.listeners.queueEvent(11, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda4
                    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                    public final void invoke(Object obj) {
                        ((Player.EventListener) obj).onPlayerError(playbackInfo.playbackError);
                    }
                });
            }
        }
        if (playbackInfo2.trackSelectorResult != playbackInfo.trackSelectorResult) {
            this.trackSelector.onSelectionActivated(playbackInfo.trackSelectorResult.info);
            final TrackSelectionArray trackSelectionArray = new TrackSelectionArray(playbackInfo.trackSelectorResult.selections);
            this.listeners.queueEvent(2, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda5
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    Player.EventListener eventListener = (Player.EventListener) obj;
                    eventListener.onTracksChanged(playbackInfo.trackGroups, trackSelectionArray);
                }
            });
        }
        if (!playbackInfo2.staticMetadata.equals(playbackInfo.staticMetadata)) {
            this.listeners.queueEvent(3, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda6
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onStaticMetadataChanged(playbackInfo.staticMetadata);
                }
            });
        }
        if (z) {
            final MediaMetadata mediaMetadata = this.mediaMetadata;
            this.listeners.queueEvent(15, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda7
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onMediaMetadataChanged(mediaMetadata);
                }
            });
        }
        if (playbackInfo2.isLoading != playbackInfo.isLoading) {
            this.listeners.queueEvent(4, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda8
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$updatePlaybackInfo$16(playbackInfo, (Player.EventListener) obj);
                }
            });
        }
        if (playbackInfo2.playbackState != playbackInfo.playbackState || playbackInfo2.playWhenReady != playbackInfo.playWhenReady) {
            this.listeners.queueEvent(-1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda9
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    PlaybackInfo playbackInfo3 = playbackInfo;
                    ((Player.EventListener) obj).onPlayerStateChanged(playbackInfo3.playWhenReady, playbackInfo3.playbackState);
                }
            });
        }
        if (playbackInfo2.playbackState != playbackInfo.playbackState) {
            this.listeners.queueEvent(5, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda19
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onPlaybackStateChanged(playbackInfo.playbackState);
                }
            });
        }
        if (playbackInfo2.playWhenReady != playbackInfo.playWhenReady) {
            this.listeners.queueEvent(6, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda20
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    Player.EventListener eventListener = (Player.EventListener) obj;
                    eventListener.onPlayWhenReadyChanged(playbackInfo.playWhenReady, playWhenReadyChangeReason);
                }
            });
        }
        if (playbackInfo2.playbackSuppressionReason != playbackInfo.playbackSuppressionReason) {
            this.listeners.queueEvent(7, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda21
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onPlaybackSuppressionReasonChanged(playbackInfo.playbackSuppressionReason);
                }
            });
        }
        if (isPlaying(playbackInfo2) != isPlaying(playbackInfo)) {
            this.listeners.queueEvent(8, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda22
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onIsPlayingChanged(ExoPlayerImpl.isPlaying(playbackInfo));
                }
            });
        }
        if (!playbackInfo2.playbackParameters.equals(playbackInfo.playbackParameters)) {
            this.listeners.queueEvent(13, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda23
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onPlaybackParametersChanged(playbackInfo.playbackParameters);
                }
            });
        }
        if (seekProcessed) {
            this.listeners.queueEvent(-1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda24
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onSeekProcessed();
                }
            });
        }
        updateAvailableCommands();
        this.listeners.flushEvents();
        if (playbackInfo2.offloadSchedulingEnabled != playbackInfo.offloadSchedulingEnabled) {
            Iterator<ExoPlayer.AudioOffloadListener> it = this.audioOffloadListeners.iterator();
            while (it.hasNext()) {
                it.next().onExperimentalOffloadSchedulingEnabledChanged(playbackInfo.offloadSchedulingEnabled);
            }
        }
        if (playbackInfo2.sleepingForOffload != playbackInfo.sleepingForOffload) {
            Iterator<ExoPlayer.AudioOffloadListener> it2 = this.audioOffloadListeners.iterator();
            while (it2.hasNext()) {
                it2.next().onExperimentalSleepingForOffloadChanged(playbackInfo.sleepingForOffload);
            }
        }
    }

    static /* synthetic */ void lambda$updatePlaybackInfo$9(int i, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, Player.EventListener eventListener) {
        eventListener.onPositionDiscontinuity(i);
        eventListener.onPositionDiscontinuity(positionInfo, positionInfo2, i);
    }

    static /* synthetic */ void lambda$updatePlaybackInfo$16(PlaybackInfo playbackInfo, Player.EventListener eventListener) {
        eventListener.onLoadingChanged(playbackInfo.isLoading);
        eventListener.onIsLoadingChanged(playbackInfo.isLoading);
    }

    private Player.PositionInfo getPreviousPositionInfo(int positionDiscontinuityReason, PlaybackInfo oldPlaybackInfo, int oldMaskingWindowIndex) {
        int i;
        Object obj;
        Object obj2;
        int indexOfPeriod;
        long requestedContentPositionUs;
        long requestedContentPositionUs2;
        Timeline.Period period = new Timeline.Period();
        if (oldPlaybackInfo.timeline.isEmpty()) {
            i = oldMaskingWindowIndex;
            obj = null;
            obj2 = null;
            indexOfPeriod = -1;
        } else {
            Object obj3 = oldPlaybackInfo.periodId.periodUid;
            oldPlaybackInfo.timeline.getPeriodByUid(obj3, period);
            int i2 = period.windowIndex;
            obj2 = obj3;
            indexOfPeriod = oldPlaybackInfo.timeline.getIndexOfPeriod(obj3);
            obj = oldPlaybackInfo.timeline.getWindow(i2, this.window).uid;
            i = i2;
        }
        if (positionDiscontinuityReason == 0) {
            requestedContentPositionUs = period.positionInWindowUs + period.durationUs;
            if (oldPlaybackInfo.periodId.isAd()) {
                requestedContentPositionUs = period.getAdDurationUs(oldPlaybackInfo.periodId.adGroupIndex, oldPlaybackInfo.periodId.adIndexInAdGroup);
                requestedContentPositionUs2 = getRequestedContentPositionUs(oldPlaybackInfo);
            } else {
                if (oldPlaybackInfo.periodId.nextAdGroupIndex != -1 && this.playbackInfo.periodId.isAd()) {
                    requestedContentPositionUs = getRequestedContentPositionUs(this.playbackInfo);
                }
                requestedContentPositionUs2 = requestedContentPositionUs;
            }
        } else if (oldPlaybackInfo.periodId.isAd()) {
            requestedContentPositionUs = oldPlaybackInfo.positionUs;
            requestedContentPositionUs2 = getRequestedContentPositionUs(oldPlaybackInfo);
        } else {
            requestedContentPositionUs = period.positionInWindowUs + oldPlaybackInfo.positionUs;
            requestedContentPositionUs2 = requestedContentPositionUs;
        }
        return new Player.PositionInfo(obj, i, obj2, indexOfPeriod, C.usToMs(requestedContentPositionUs), C.usToMs(requestedContentPositionUs2), oldPlaybackInfo.periodId.adGroupIndex, oldPlaybackInfo.periodId.adIndexInAdGroup);
    }

    private Player.PositionInfo getPositionInfo(long discontinuityWindowStartPositionUs) {
        Object obj;
        int indexOfPeriod;
        Object obj2;
        int currentWindowIndex = getCurrentWindowIndex();
        if (this.playbackInfo.timeline.isEmpty()) {
            obj = null;
            indexOfPeriod = -1;
            obj2 = null;
        } else {
            Object obj3 = this.playbackInfo.periodId.periodUid;
            this.playbackInfo.timeline.getPeriodByUid(obj3, this.period);
            indexOfPeriod = this.playbackInfo.timeline.getIndexOfPeriod(obj3);
            obj2 = this.playbackInfo.timeline.getWindow(currentWindowIndex, this.window).uid;
            obj = obj3;
        }
        long jUsToMs = C.usToMs(discontinuityWindowStartPositionUs);
        return new Player.PositionInfo(obj2, currentWindowIndex, obj, indexOfPeriod, jUsToMs, this.playbackInfo.periodId.isAd() ? C.usToMs(getRequestedContentPositionUs(this.playbackInfo)) : jUsToMs, this.playbackInfo.periodId.adGroupIndex, this.playbackInfo.periodId.adIndexInAdGroup);
    }

    private static long getRequestedContentPositionUs(PlaybackInfo playbackInfo) {
        Timeline.Window window = new Timeline.Window();
        Timeline.Period period = new Timeline.Period();
        playbackInfo.timeline.getPeriodByUid(playbackInfo.periodId.periodUid, period);
        if (playbackInfo.requestedContentPositionUs == C.TIME_UNSET) {
            return playbackInfo.timeline.getWindow(period.windowIndex, window).getDefaultPositionUs();
        }
        return period.getPositionInWindowUs() + playbackInfo.requestedContentPositionUs;
    }

    private Pair<Boolean, Integer> evaluateMediaItemTransitionReason(PlaybackInfo playbackInfo, PlaybackInfo oldPlaybackInfo, boolean positionDiscontinuity, int positionDiscontinuityReason, boolean timelineChanged) {
        Timeline timeline = oldPlaybackInfo.timeline;
        Timeline timeline2 = playbackInfo.timeline;
        if (timeline2.isEmpty() && timeline.isEmpty()) {
            return new Pair<>(false, -1);
        }
        int i = 3;
        if (timeline2.isEmpty() != timeline.isEmpty()) {
            return new Pair<>(true, 3);
        }
        if (timeline.getWindow(timeline.getPeriodByUid(oldPlaybackInfo.periodId.periodUid, this.period).windowIndex, this.window).uid.equals(timeline2.getWindow(timeline2.getPeriodByUid(playbackInfo.periodId.periodUid, this.period).windowIndex, this.window).uid)) {
            if (positionDiscontinuity && positionDiscontinuityReason == 0 && oldPlaybackInfo.periodId.windowSequenceNumber < playbackInfo.periodId.windowSequenceNumber) {
                return new Pair<>(true, 0);
            }
            return new Pair<>(false, -1);
        }
        if (positionDiscontinuity && positionDiscontinuityReason == 0) {
            i = 1;
        } else if (positionDiscontinuity && positionDiscontinuityReason == 1) {
            i = 2;
        } else if (!timelineChanged) {
            throw new IllegalStateException();
        }
        return new Pair<>(true, Integer.valueOf(i));
    }

    private void updateAvailableCommands() {
        Player.Commands commands = this.availableCommands;
        Player.Commands availableCommands = getAvailableCommands(this.permanentAvailableCommands);
        this.availableCommands = availableCommands;
        if (availableCommands.equals(commands)) {
            return;
        }
        this.listeners.queueEvent(14, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.ExoPlayerImpl$$ExternalSyntheticLambda13
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                this.f$0.m131x2097ed41((Player.EventListener) obj);
            }
        });
    }

    /* renamed from: lambda$updateAvailableCommands$23$com-google-android-exoplayer2-ExoPlayerImpl, reason: not valid java name */
    /* synthetic */ void m131x2097ed41(Player.EventListener eventListener) {
        eventListener.onAvailableCommandsChanged(this.availableCommands);
    }

    private void setMediaSourcesInternal(List<MediaSource> mediaSources, int startWindowIndex, long startPositionMs, boolean resetToDefaultPosition) {
        int i;
        long j;
        int currentWindowIndexInternal = getCurrentWindowIndexInternal();
        long currentPosition = getCurrentPosition();
        this.pendingOperationAcks++;
        if (!this.mediaSourceHolderSnapshots.isEmpty()) {
            removeMediaSourceHolders(0, this.mediaSourceHolderSnapshots.size());
        }
        List<MediaSourceList.MediaSourceHolder> listAddMediaSourceHolders = addMediaSourceHolders(0, mediaSources);
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        if (!timelineCreateMaskingTimeline.isEmpty() && startWindowIndex >= timelineCreateMaskingTimeline.getWindowCount()) {
            throw new IllegalSeekPositionException(timelineCreateMaskingTimeline, startWindowIndex, startPositionMs);
        }
        if (resetToDefaultPosition) {
            int firstWindowIndex = timelineCreateMaskingTimeline.getFirstWindowIndex(this.shuffleModeEnabled);
            j = C.TIME_UNSET;
            i = firstWindowIndex;
        } else if (startWindowIndex == -1) {
            i = currentWindowIndexInternal;
            j = currentPosition;
        } else {
            i = startWindowIndex;
            j = startPositionMs;
        }
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, getPeriodPositionOrMaskWindowPosition(timelineCreateMaskingTimeline, i, j));
        int i2 = playbackInfoMaskTimelineAndPosition.playbackState;
        if (i != -1 && playbackInfoMaskTimelineAndPosition.playbackState != 1) {
            i2 = (timelineCreateMaskingTimeline.isEmpty() || i >= timelineCreateMaskingTimeline.getWindowCount()) ? 4 : 2;
        }
        PlaybackInfo playbackInfoCopyWithPlaybackState = playbackInfoMaskTimelineAndPosition.copyWithPlaybackState(i2);
        this.internalPlayer.setMediaSources(listAddMediaSourceHolders, i, C.msToUs(j), this.shuffleOrder);
        updatePlaybackInfo(playbackInfoCopyWithPlaybackState, 0, 1, false, (this.playbackInfo.periodId.periodUid.equals(playbackInfoCopyWithPlaybackState.periodId.periodUid) || this.playbackInfo.timeline.isEmpty()) ? false : true, 4, getCurrentPositionUsInternal(playbackInfoCopyWithPlaybackState), -1);
    }

    private List<MediaSourceList.MediaSourceHolder> addMediaSourceHolders(int index, List<MediaSource> mediaSources) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < mediaSources.size(); i++) {
            MediaSourceList.MediaSourceHolder mediaSourceHolder = new MediaSourceList.MediaSourceHolder(mediaSources.get(i), this.useLazyPreparation);
            arrayList.add(mediaSourceHolder);
            this.mediaSourceHolderSnapshots.add(i + index, new MediaSourceHolderSnapshot(mediaSourceHolder.uid, mediaSourceHolder.mediaSource.getTimeline()));
        }
        this.shuffleOrder = this.shuffleOrder.cloneAndInsert(index, arrayList.size());
        return arrayList;
    }

    private PlaybackInfo removeMediaItemsInternal(int fromIndex, int toIndex) {
        boolean z = false;
        Assertions.checkArgument(fromIndex >= 0 && toIndex >= fromIndex && toIndex <= this.mediaSourceHolderSnapshots.size());
        int currentWindowIndex = getCurrentWindowIndex();
        Timeline currentTimeline = getCurrentTimeline();
        int size = this.mediaSourceHolderSnapshots.size();
        this.pendingOperationAcks++;
        removeMediaSourceHolders(fromIndex, toIndex);
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, getPeriodPositionAfterTimelineChanged(currentTimeline, timelineCreateMaskingTimeline));
        if (playbackInfoMaskTimelineAndPosition.playbackState != 1 && playbackInfoMaskTimelineAndPosition.playbackState != 4 && fromIndex < toIndex && toIndex == size && currentWindowIndex >= playbackInfoMaskTimelineAndPosition.timeline.getWindowCount()) {
            z = true;
        }
        if (z) {
            playbackInfoMaskTimelineAndPosition = playbackInfoMaskTimelineAndPosition.copyWithPlaybackState(4);
        }
        this.internalPlayer.removeMediaSources(fromIndex, toIndex, this.shuffleOrder);
        return playbackInfoMaskTimelineAndPosition;
    }

    private void removeMediaSourceHolders(int fromIndex, int toIndexExclusive) {
        for (int i = toIndexExclusive - 1; i >= fromIndex; i--) {
            this.mediaSourceHolderSnapshots.remove(i);
        }
        this.shuffleOrder = this.shuffleOrder.cloneAndRemove(fromIndex, toIndexExclusive);
    }

    private Timeline createMaskingTimeline() {
        return new PlaylistTimeline(this.mediaSourceHolderSnapshots, this.shuffleOrder);
    }

    private PlaybackInfo maskTimelineAndPosition(PlaybackInfo playbackInfo, Timeline timeline, Pair<Object, Long> periodPosition) {
        long adDurationUs;
        Assertions.checkArgument(timeline.isEmpty() || periodPosition != null);
        Timeline timeline2 = playbackInfo.timeline;
        PlaybackInfo playbackInfoCopyWithTimeline = playbackInfo.copyWithTimeline(timeline);
        if (timeline.isEmpty()) {
            MediaSource.MediaPeriodId dummyPeriodForEmptyTimeline = PlaybackInfo.getDummyPeriodForEmptyTimeline();
            long jMsToUs = C.msToUs(this.maskingWindowPositionMs);
            PlaybackInfo playbackInfoCopyWithLoadingMediaPeriodId = playbackInfoCopyWithTimeline.copyWithNewPosition(dummyPeriodForEmptyTimeline, jMsToUs, jMsToUs, jMsToUs, 0L, TrackGroupArray.EMPTY, this.emptyTrackSelectorResult, ImmutableList.of()).copyWithLoadingMediaPeriodId(dummyPeriodForEmptyTimeline);
            playbackInfoCopyWithLoadingMediaPeriodId.bufferedPositionUs = playbackInfoCopyWithLoadingMediaPeriodId.positionUs;
            return playbackInfoCopyWithLoadingMediaPeriodId;
        }
        Object obj = playbackInfoCopyWithTimeline.periodId.periodUid;
        boolean z = !obj.equals(((Pair) Util.castNonNull(periodPosition)).first);
        MediaSource.MediaPeriodId mediaPeriodId = z ? new MediaSource.MediaPeriodId(periodPosition.first) : playbackInfoCopyWithTimeline.periodId;
        long jLongValue = ((Long) periodPosition.second).longValue();
        long jMsToUs2 = C.msToUs(getContentPosition());
        if (!timeline2.isEmpty()) {
            jMsToUs2 -= timeline2.getPeriodByUid(obj, this.period).getPositionInWindowUs();
        }
        if (z || jLongValue < jMsToUs2) {
            Assertions.checkState(!mediaPeriodId.isAd());
            PlaybackInfo playbackInfoCopyWithLoadingMediaPeriodId2 = playbackInfoCopyWithTimeline.copyWithNewPosition(mediaPeriodId, jLongValue, jLongValue, jLongValue, 0L, z ? TrackGroupArray.EMPTY : playbackInfoCopyWithTimeline.trackGroups, z ? this.emptyTrackSelectorResult : playbackInfoCopyWithTimeline.trackSelectorResult, z ? ImmutableList.of() : playbackInfoCopyWithTimeline.staticMetadata).copyWithLoadingMediaPeriodId(mediaPeriodId);
            playbackInfoCopyWithLoadingMediaPeriodId2.bufferedPositionUs = jLongValue;
            return playbackInfoCopyWithLoadingMediaPeriodId2;
        }
        if (jLongValue == jMsToUs2) {
            int indexOfPeriod = timeline.getIndexOfPeriod(playbackInfoCopyWithTimeline.loadingMediaPeriodId.periodUid);
            if (indexOfPeriod == -1 || timeline.getPeriod(indexOfPeriod, this.period).windowIndex != timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).windowIndex) {
                timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
                if (mediaPeriodId.isAd()) {
                    adDurationUs = this.period.getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup);
                } else {
                    adDurationUs = this.period.durationUs;
                }
                playbackInfoCopyWithTimeline = playbackInfoCopyWithTimeline.copyWithNewPosition(mediaPeriodId, playbackInfoCopyWithTimeline.positionUs, playbackInfoCopyWithTimeline.positionUs, playbackInfoCopyWithTimeline.discontinuityStartPositionUs, adDurationUs - playbackInfoCopyWithTimeline.positionUs, playbackInfoCopyWithTimeline.trackGroups, playbackInfoCopyWithTimeline.trackSelectorResult, playbackInfoCopyWithTimeline.staticMetadata).copyWithLoadingMediaPeriodId(mediaPeriodId);
                playbackInfoCopyWithTimeline.bufferedPositionUs = adDurationUs;
            }
        } else {
            Assertions.checkState(!mediaPeriodId.isAd());
            long jMax = Math.max(0L, playbackInfoCopyWithTimeline.totalBufferedDurationUs - (jLongValue - jMsToUs2));
            long j = playbackInfoCopyWithTimeline.bufferedPositionUs;
            if (playbackInfoCopyWithTimeline.loadingMediaPeriodId.equals(playbackInfoCopyWithTimeline.periodId)) {
                j = jLongValue + jMax;
            }
            playbackInfoCopyWithTimeline = playbackInfoCopyWithTimeline.copyWithNewPosition(mediaPeriodId, jLongValue, jLongValue, jLongValue, jMax, playbackInfoCopyWithTimeline.trackGroups, playbackInfoCopyWithTimeline.trackSelectorResult, playbackInfoCopyWithTimeline.staticMetadata);
            playbackInfoCopyWithTimeline.bufferedPositionUs = j;
        }
        return playbackInfoCopyWithTimeline;
    }

    private Pair<Object, Long> getPeriodPositionAfterTimelineChanged(Timeline oldTimeline, Timeline newTimeline) {
        long contentPosition = getContentPosition();
        if (oldTimeline.isEmpty() || newTimeline.isEmpty()) {
            boolean z = !oldTimeline.isEmpty() && newTimeline.isEmpty();
            int currentWindowIndexInternal = z ? -1 : getCurrentWindowIndexInternal();
            if (z) {
                contentPosition = -9223372036854775807L;
            }
            return getPeriodPositionOrMaskWindowPosition(newTimeline, currentWindowIndexInternal, contentPosition);
        }
        Pair<Object, Long> periodPosition = oldTimeline.getPeriodPosition(this.window, this.period, getCurrentWindowIndex(), C.msToUs(contentPosition));
        Object obj = ((Pair) Util.castNonNull(periodPosition)).first;
        if (newTimeline.getIndexOfPeriod(obj) != -1) {
            return periodPosition;
        }
        Object objResolveSubsequentPeriod = ExoPlayerImplInternal.resolveSubsequentPeriod(this.window, this.period, this.repeatMode, this.shuffleModeEnabled, obj, oldTimeline, newTimeline);
        if (objResolveSubsequentPeriod != null) {
            newTimeline.getPeriodByUid(objResolveSubsequentPeriod, this.period);
            return getPeriodPositionOrMaskWindowPosition(newTimeline, this.period.windowIndex, newTimeline.getWindow(this.period.windowIndex, this.window).getDefaultPositionMs());
        }
        return getPeriodPositionOrMaskWindowPosition(newTimeline, -1, C.TIME_UNSET);
    }

    private Pair<Object, Long> getPeriodPositionOrMaskWindowPosition(Timeline timeline, int windowIndex, long windowPositionMs) {
        if (timeline.isEmpty()) {
            this.maskingWindowIndex = windowIndex;
            if (windowPositionMs == C.TIME_UNSET) {
                windowPositionMs = 0;
            }
            this.maskingWindowPositionMs = windowPositionMs;
            this.maskingPeriodIndex = 0;
            return null;
        }
        if (windowIndex == -1 || windowIndex >= timeline.getWindowCount()) {
            windowIndex = timeline.getFirstWindowIndex(this.shuffleModeEnabled);
            windowPositionMs = timeline.getWindow(windowIndex, this.window).getDefaultPositionMs();
        }
        return timeline.getPeriodPosition(this.window, this.period, windowIndex, C.msToUs(windowPositionMs));
    }

    private long periodPositionUsToWindowPositionUs(Timeline timeline, MediaSource.MediaPeriodId periodId, long positionUs) {
        timeline.getPeriodByUid(periodId.periodUid, this.period);
        return positionUs + this.period.getPositionInWindowUs();
    }

    private static boolean isPlaying(PlaybackInfo playbackInfo) {
        return playbackInfo.playbackState == 3 && playbackInfo.playWhenReady && playbackInfo.playbackSuppressionReason == 0;
    }

    private static final class MediaSourceHolderSnapshot implements MediaSourceInfoHolder {
        private Timeline timeline;
        private final Object uid;

        public MediaSourceHolderSnapshot(Object uid, Timeline timeline) {
            this.uid = uid;
            this.timeline = timeline;
        }

        @Override // com.google.android.exoplayer2.MediaSourceInfoHolder
        public Object getUid() {
            return this.uid;
        }

        @Override // com.google.android.exoplayer2.MediaSourceInfoHolder
        public Timeline getTimeline() {
            return this.timeline;
        }
    }
}
