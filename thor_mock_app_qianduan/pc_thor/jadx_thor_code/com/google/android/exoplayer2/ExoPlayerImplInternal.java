package com.google.android.exoplayer2;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import com.google.android.exoplayer2.DefaultMediaClock;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaSourceList;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.TextRenderer;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSourceException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
final class ExoPlayerImplInternal implements Handler.Callback, MediaPeriod.Callback, TrackSelector.InvalidationListener, MediaSourceList.MediaSourceListInfoRefreshListener, DefaultMediaClock.PlaybackParametersListener, PlayerMessage.Sender {
    private static final int ACTIVE_INTERVAL_MS = 10;
    private static final int IDLE_INTERVAL_MS = 1000;
    private static final long MIN_RENDERER_SLEEP_DURATION_MS = 2000;
    private static final int MSG_ADD_MEDIA_SOURCES = 18;
    private static final int MSG_ATTEMPT_RENDERER_ERROR_RECOVERY = 25;
    private static final int MSG_DO_SOME_WORK = 2;
    private static final int MSG_MOVE_MEDIA_SOURCES = 19;
    private static final int MSG_PERIOD_PREPARED = 8;
    private static final int MSG_PLAYBACK_PARAMETERS_CHANGED_INTERNAL = 16;
    private static final int MSG_PLAYLIST_UPDATE_REQUESTED = 22;
    private static final int MSG_PREPARE = 0;
    private static final int MSG_RELEASE = 7;
    private static final int MSG_REMOVE_MEDIA_SOURCES = 20;
    private static final int MSG_SEEK_TO = 3;
    private static final int MSG_SEND_MESSAGE = 14;
    private static final int MSG_SEND_MESSAGE_TO_TARGET_THREAD = 15;
    private static final int MSG_SET_FOREGROUND_MODE = 13;
    private static final int MSG_SET_MEDIA_SOURCES = 17;
    private static final int MSG_SET_OFFLOAD_SCHEDULING_ENABLED = 24;
    private static final int MSG_SET_PAUSE_AT_END_OF_WINDOW = 23;
    private static final int MSG_SET_PLAYBACK_PARAMETERS = 4;
    private static final int MSG_SET_PLAY_WHEN_READY = 1;
    private static final int MSG_SET_REPEAT_MODE = 11;
    private static final int MSG_SET_SEEK_PARAMETERS = 5;
    private static final int MSG_SET_SHUFFLE_ENABLED = 12;
    private static final int MSG_SET_SHUFFLE_ORDER = 21;
    private static final int MSG_SOURCE_CONTINUE_LOADING_REQUESTED = 9;
    private static final int MSG_STOP = 6;
    private static final int MSG_TRACK_SELECTION_INVALIDATED = 10;
    private static final String TAG = "ExoPlayerImplInternal";
    private final long backBufferDurationUs;
    private final BandwidthMeter bandwidthMeter;
    private final Clock clock;
    private boolean deliverPendingMessageAtStartPositionRequired;
    private final TrackSelectorResult emptyTrackSelectorResult;
    private int enabledRendererCount;
    private boolean foregroundMode;
    private final HandlerWrapper handler;
    private final HandlerThread internalPlaybackThread;
    private boolean isRebuffering;
    private final LivePlaybackSpeedControl livePlaybackSpeedControl;
    private final LoadControl loadControl;
    private final DefaultMediaClock mediaClock;
    private final MediaSourceList mediaSourceList;
    private int nextPendingMessageIndexHint;
    private boolean offloadSchedulingEnabled;
    private boolean pauseAtEndOfWindow;
    private SeekPosition pendingInitialSeekPosition;
    private final ArrayList<PendingMessageInfo> pendingMessages;
    private boolean pendingPauseAtEndOfPeriod;
    private ExoPlaybackException pendingRecoverableRendererError;
    private final Timeline.Period period;
    private PlaybackInfo playbackInfo;
    private PlaybackInfoUpdate playbackInfoUpdate;
    private final PlaybackInfoUpdateListener playbackInfoUpdateListener;
    private final Looper playbackLooper;
    private final MediaPeriodQueue queue;
    private final long releaseTimeoutMs;
    private boolean released;
    private final RendererCapabilities[] rendererCapabilities;
    private long rendererPositionUs;
    private final Renderer[] renderers;
    private int repeatMode;
    private boolean requestForRendererSleep;
    private final boolean retainBackBufferFromKeyframe;
    private SeekParameters seekParameters;
    private long setForegroundModeTimeoutMs;
    private boolean shouldContinueLoading;
    private boolean shuffleModeEnabled;
    private final TrackSelector trackSelector;
    private final Timeline.Window window;

    public interface PlaybackInfoUpdateListener {
        void onPlaybackInfoUpdate(PlaybackInfoUpdate playbackInfo);
    }

    public static final class PlaybackInfoUpdate {
        public int discontinuityReason;
        private boolean hasPendingChange;
        public boolean hasPlayWhenReadyChangeReason;
        public int operationAcks;
        public int playWhenReadyChangeReason;
        public PlaybackInfo playbackInfo;
        public boolean positionDiscontinuity;

        public PlaybackInfoUpdate(PlaybackInfo playbackInfo) {
            this.playbackInfo = playbackInfo;
        }

        public void incrementPendingOperationAcks(int operationAcks) {
            this.hasPendingChange |= operationAcks > 0;
            this.operationAcks += operationAcks;
        }

        public void setPlaybackInfo(PlaybackInfo playbackInfo) {
            this.hasPendingChange |= this.playbackInfo != playbackInfo;
            this.playbackInfo = playbackInfo;
        }

        public void setPositionDiscontinuity(int discontinuityReason) {
            if (this.positionDiscontinuity && this.discontinuityReason != 5) {
                Assertions.checkArgument(discontinuityReason == 5);
                return;
            }
            this.hasPendingChange = true;
            this.positionDiscontinuity = true;
            this.discontinuityReason = discontinuityReason;
        }

        public void setPlayWhenReadyChangeReason(int playWhenReadyChangeReason) {
            this.hasPendingChange = true;
            this.hasPlayWhenReadyChangeReason = true;
            this.playWhenReadyChangeReason = playWhenReadyChangeReason;
        }
    }

    public ExoPlayerImplInternal(Renderer[] renderers, TrackSelector trackSelector, TrackSelectorResult emptyTrackSelectorResult, LoadControl loadControl, BandwidthMeter bandwidthMeter, int repeatMode, boolean shuffleModeEnabled, AnalyticsCollector analyticsCollector, SeekParameters seekParameters, LivePlaybackSpeedControl livePlaybackSpeedControl, long releaseTimeoutMs, boolean pauseAtEndOfWindow, Looper applicationLooper, Clock clock, PlaybackInfoUpdateListener playbackInfoUpdateListener) {
        this.playbackInfoUpdateListener = playbackInfoUpdateListener;
        this.renderers = renderers;
        this.trackSelector = trackSelector;
        this.emptyTrackSelectorResult = emptyTrackSelectorResult;
        this.loadControl = loadControl;
        this.bandwidthMeter = bandwidthMeter;
        this.repeatMode = repeatMode;
        this.shuffleModeEnabled = shuffleModeEnabled;
        this.seekParameters = seekParameters;
        this.livePlaybackSpeedControl = livePlaybackSpeedControl;
        this.releaseTimeoutMs = releaseTimeoutMs;
        this.setForegroundModeTimeoutMs = releaseTimeoutMs;
        this.pauseAtEndOfWindow = pauseAtEndOfWindow;
        this.clock = clock;
        this.backBufferDurationUs = loadControl.getBackBufferDurationUs();
        this.retainBackBufferFromKeyframe = loadControl.retainBackBufferFromKeyframe();
        this.playbackInfo = PlaybackInfo.createDummy(emptyTrackSelectorResult);
        this.playbackInfoUpdate = new PlaybackInfoUpdate(this.playbackInfo);
        this.rendererCapabilities = new RendererCapabilities[renderers.length];
        for (int i = 0; i < renderers.length; i++) {
            renderers[i].setIndex(i);
            this.rendererCapabilities[i] = renderers[i].getCapabilities();
        }
        this.mediaClock = new DefaultMediaClock(this, clock);
        this.pendingMessages = new ArrayList<>();
        this.window = new Timeline.Window();
        this.period = new Timeline.Period();
        trackSelector.init(this, bandwidthMeter);
        this.deliverPendingMessageAtStartPositionRequired = true;
        Handler handler = new Handler(applicationLooper);
        this.queue = new MediaPeriodQueue(analyticsCollector, handler);
        this.mediaSourceList = new MediaSourceList(this, analyticsCollector, handler);
        HandlerThread handlerThread = new HandlerThread("ExoPlayer:Playback", -16);
        this.internalPlaybackThread = handlerThread;
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        this.playbackLooper = looper;
        this.handler = clock.createHandler(looper, this);
    }

    public void experimentalSetForegroundModeTimeoutMs(long setForegroundModeTimeoutMs) {
        this.setForegroundModeTimeoutMs = setForegroundModeTimeoutMs;
    }

    public void experimentalSetOffloadSchedulingEnabled(boolean z) {
        this.handler.obtainMessage(24, z ? 1 : 0, 0).sendToTarget();
    }

    public void prepare() {
        this.handler.obtainMessage(0).sendToTarget();
    }

    public void setPlayWhenReady(boolean z, int i) {
        this.handler.obtainMessage(1, z ? 1 : 0, i).sendToTarget();
    }

    public void setPauseAtEndOfWindow(boolean z) {
        this.handler.obtainMessage(23, z ? 1 : 0, 0).sendToTarget();
    }

    public void setRepeatMode(int repeatMode) {
        this.handler.obtainMessage(11, repeatMode, 0).sendToTarget();
    }

    public void setShuffleModeEnabled(boolean z) {
        this.handler.obtainMessage(12, z ? 1 : 0, 0).sendToTarget();
    }

    public void seekTo(Timeline timeline, int windowIndex, long positionUs) {
        this.handler.obtainMessage(3, new SeekPosition(timeline, windowIndex, positionUs)).sendToTarget();
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.handler.obtainMessage(4, playbackParameters).sendToTarget();
    }

    public void setSeekParameters(SeekParameters seekParameters) {
        this.handler.obtainMessage(5, seekParameters).sendToTarget();
    }

    public void stop() {
        this.handler.obtainMessage(6).sendToTarget();
    }

    public void setMediaSources(List<MediaSourceList.MediaSourceHolder> mediaSources, int windowIndex, long positionUs, ShuffleOrder shuffleOrder) {
        this.handler.obtainMessage(17, new MediaSourceListUpdateMessage(mediaSources, shuffleOrder, windowIndex, positionUs)).sendToTarget();
    }

    public void addMediaSources(int index, List<MediaSourceList.MediaSourceHolder> mediaSources, ShuffleOrder shuffleOrder) {
        this.handler.obtainMessage(18, index, 0, new MediaSourceListUpdateMessage(mediaSources, shuffleOrder, -1, C.TIME_UNSET)).sendToTarget();
    }

    public void removeMediaSources(int fromIndex, int toIndex, ShuffleOrder shuffleOrder) {
        this.handler.obtainMessage(20, fromIndex, toIndex, shuffleOrder).sendToTarget();
    }

    public void moveMediaSources(int fromIndex, int toIndex, int newFromIndex, ShuffleOrder shuffleOrder) {
        this.handler.obtainMessage(19, new MoveMediaItemsMessage(fromIndex, toIndex, newFromIndex, shuffleOrder)).sendToTarget();
    }

    public void setShuffleOrder(ShuffleOrder shuffleOrder) {
        this.handler.obtainMessage(21, shuffleOrder).sendToTarget();
    }

    @Override // com.google.android.exoplayer2.PlayerMessage.Sender
    public synchronized void sendMessage(PlayerMessage message) {
        if (!this.released && this.internalPlaybackThread.isAlive()) {
            this.handler.obtainMessage(14, message).sendToTarget();
            return;
        }
        Log.w(TAG, "Ignoring messages sent after release.");
        message.markAsProcessed(false);
    }

    public synchronized boolean setForegroundMode(boolean foregroundMode) {
        if (!this.released && this.internalPlaybackThread.isAlive()) {
            if (foregroundMode) {
                this.handler.obtainMessage(13, 1, 0).sendToTarget();
                return true;
            }
            final AtomicBoolean atomicBoolean = new AtomicBoolean();
            this.handler.obtainMessage(13, 0, 0, atomicBoolean).sendToTarget();
            Objects.requireNonNull(atomicBoolean);
            waitUninterruptibly(new Supplier() { // from class: com.google.android.exoplayer2.ExoPlayerImplInternal$$ExternalSyntheticLambda2
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return Boolean.valueOf(atomicBoolean.get());
                }
            }, this.setForegroundModeTimeoutMs);
            return atomicBoolean.get();
        }
        return true;
    }

    public synchronized boolean release() {
        if (!this.released && this.internalPlaybackThread.isAlive()) {
            this.handler.sendEmptyMessage(7);
            waitUninterruptibly(new Supplier() { // from class: com.google.android.exoplayer2.ExoPlayerImplInternal$$ExternalSyntheticLambda0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return this.f$0.m132x37739a9a();
                }
            }, this.releaseTimeoutMs);
            return this.released;
        }
        return true;
    }

    /* renamed from: lambda$release$0$com-google-android-exoplayer2-ExoPlayerImplInternal, reason: not valid java name */
    /* synthetic */ Boolean m132x37739a9a() {
        return Boolean.valueOf(this.released);
    }

    public Looper getPlaybackLooper() {
        return this.playbackLooper;
    }

    @Override // com.google.android.exoplayer2.MediaSourceList.MediaSourceListInfoRefreshListener
    public void onPlaylistUpdateRequested() {
        this.handler.sendEmptyMessage(22);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
    public void onPrepared(MediaPeriod source) {
        this.handler.obtainMessage(8, source).sendToTarget();
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
    public void onContinueLoadingRequested(MediaPeriod source) {
        this.handler.obtainMessage(9, source).sendToTarget();
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector.InvalidationListener
    public void onTrackSelectionsInvalidated() {
        this.handler.sendEmptyMessage(10);
    }

    @Override // com.google.android.exoplayer2.DefaultMediaClock.PlaybackParametersListener
    public void onPlaybackParametersChanged(PlaybackParameters newPlaybackParameters) {
        this.handler.obtainMessage(16, newPlaybackParameters).sendToTarget();
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message msg) throws Throwable {
        int i;
        MediaPeriodHolder readingPeriod;
        try {
            switch (msg.what) {
                case 0:
                    prepareInternal();
                    break;
                case 1:
                    setPlayWhenReadyInternal(msg.arg1 != 0, msg.arg2, true, 1);
                    break;
                case 2:
                    doSomeWork();
                    break;
                case 3:
                    seekToInternal((SeekPosition) msg.obj);
                    break;
                case 4:
                    setPlaybackParametersInternal((PlaybackParameters) msg.obj);
                    break;
                case 5:
                    setSeekParametersInternal((SeekParameters) msg.obj);
                    break;
                case 6:
                    stopInternal(false, true);
                    break;
                case 7:
                    releaseInternal();
                    return true;
                case 8:
                    handlePeriodPrepared((MediaPeriod) msg.obj);
                    break;
                case 9:
                    handleContinueLoadingRequested((MediaPeriod) msg.obj);
                    break;
                case 10:
                    reselectTracksInternal();
                    break;
                case 11:
                    setRepeatModeInternal(msg.arg1);
                    break;
                case 12:
                    setShuffleModeEnabledInternal(msg.arg1 != 0);
                    break;
                case 13:
                    setForegroundModeInternal(msg.arg1 != 0, (AtomicBoolean) msg.obj);
                    break;
                case 14:
                    sendMessageInternal((PlayerMessage) msg.obj);
                    break;
                case 15:
                    sendMessageToTargetThread((PlayerMessage) msg.obj);
                    break;
                case 16:
                    handlePlaybackParameters((PlaybackParameters) msg.obj, false);
                    break;
                case 17:
                    setMediaItemsInternal((MediaSourceListUpdateMessage) msg.obj);
                    break;
                case 18:
                    addMediaItemsInternal((MediaSourceListUpdateMessage) msg.obj, msg.arg1);
                    break;
                case 19:
                    moveMediaItemsInternal((MoveMediaItemsMessage) msg.obj);
                    break;
                case 20:
                    removeMediaItemsInternal(msg.arg1, msg.arg2, (ShuffleOrder) msg.obj);
                    break;
                case 21:
                    setShuffleOrderInternal((ShuffleOrder) msg.obj);
                    break;
                case 22:
                    mediaSourceListUpdateRequestedInternal();
                    break;
                case 23:
                    setPauseAtEndOfWindowInternal(msg.arg1 != 0);
                    break;
                case 24:
                    setOffloadSchedulingEnabledInternal(msg.arg1 == 1);
                    break;
                case 25:
                    attemptRendererErrorRecovery();
                    break;
                default:
                    return false;
            }
        } catch (ExoPlaybackException e) {
            e = e;
            if (e.type == 1 && (readingPeriod = this.queue.getReadingPeriod()) != null) {
                e = e.copyWithMediaPeriodId(readingPeriod.info.id);
            }
            if (e.isRecoverable && this.pendingRecoverableRendererError == null) {
                Log.w(TAG, "Recoverable renderer error", e);
                this.pendingRecoverableRendererError = e;
                HandlerWrapper handlerWrapper = this.handler;
                handlerWrapper.sendMessageAtFrontOfQueue(handlerWrapper.obtainMessage(25, e));
            } else {
                ExoPlaybackException exoPlaybackException = this.pendingRecoverableRendererError;
                if (exoPlaybackException != null) {
                    exoPlaybackException.addSuppressed(e);
                    e = this.pendingRecoverableRendererError;
                }
                Log.e(TAG, "Playback error", e);
                stopInternal(true, false);
                this.playbackInfo = this.playbackInfo.copyWithPlaybackError(e);
            }
        } catch (ParserException e2) {
            if (e2.dataType == 1) {
                i = e2.contentIsMalformed ? 3001 : 3003;
            } else {
                if (e2.dataType == 4) {
                    i = e2.contentIsMalformed ? 3002 : 3004;
                }
                handleIoException(e2, i);
            }
            i = i;
            handleIoException(e2, i);
        } catch (DrmSession.DrmSessionException e3) {
            handleIoException(e3, e3.errorCode);
        } catch (BehindLiveWindowException e4) {
            handleIoException(e4, 1002);
        } catch (DataSourceException e5) {
            handleIoException(e5, e5.reason);
        } catch (IOException e6) {
            handleIoException(e6, 2000);
        } catch (RuntimeException e7) {
            ExoPlaybackException exoPlaybackExceptionCreateForUnexpected = ExoPlaybackException.createForUnexpected(e7, ((e7 instanceof IllegalStateException) || (e7 instanceof IllegalArgumentException)) ? 1004 : 1000);
            Log.e(TAG, "Playback error", exoPlaybackExceptionCreateForUnexpected);
            stopInternal(true, false);
            this.playbackInfo = this.playbackInfo.copyWithPlaybackError(exoPlaybackExceptionCreateForUnexpected);
        }
        maybeNotifyPlaybackInfoChanged();
        return true;
    }

    private void handleIoException(IOException e, int errorCode) {
        ExoPlaybackException exoPlaybackExceptionCreateForSource = ExoPlaybackException.createForSource(e, errorCode);
        MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
        if (playingPeriod != null) {
            exoPlaybackExceptionCreateForSource = exoPlaybackExceptionCreateForSource.copyWithMediaPeriodId(playingPeriod.info.id);
        }
        Log.e(TAG, "Playback error", exoPlaybackExceptionCreateForSource);
        stopInternal(false, false);
        this.playbackInfo = this.playbackInfo.copyWithPlaybackError(exoPlaybackExceptionCreateForSource);
    }

    private synchronized void waitUninterruptibly(Supplier<Boolean> condition, long timeoutMs) {
        long jElapsedRealtime = this.clock.elapsedRealtime() + timeoutMs;
        boolean z = false;
        while (!condition.get().booleanValue() && timeoutMs > 0) {
            try {
                this.clock.onThreadBlocked();
                wait(timeoutMs);
            } catch (InterruptedException unused) {
                z = true;
            }
            timeoutMs = jElapsedRealtime - this.clock.elapsedRealtime();
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
    }

    private void setState(int state) {
        if (this.playbackInfo.playbackState != state) {
            this.playbackInfo = this.playbackInfo.copyWithPlaybackState(state);
        }
    }

    private void maybeNotifyPlaybackInfoChanged() {
        this.playbackInfoUpdate.setPlaybackInfo(this.playbackInfo);
        if (this.playbackInfoUpdate.hasPendingChange) {
            this.playbackInfoUpdateListener.onPlaybackInfoUpdate(this.playbackInfoUpdate);
            this.playbackInfoUpdate = new PlaybackInfoUpdate(this.playbackInfo);
        }
    }

    private void prepareInternal() {
        this.playbackInfoUpdate.incrementPendingOperationAcks(1);
        resetInternal(false, false, false, true);
        this.loadControl.onPrepared();
        setState(this.playbackInfo.timeline.isEmpty() ? 4 : 2);
        this.mediaSourceList.prepare(this.bandwidthMeter.getTransferListener());
        this.handler.sendEmptyMessage(2);
    }

    private void setMediaItemsInternal(MediaSourceListUpdateMessage mediaSourceListUpdateMessage) throws Throwable {
        this.playbackInfoUpdate.incrementPendingOperationAcks(1);
        if (mediaSourceListUpdateMessage.windowIndex != -1) {
            this.pendingInitialSeekPosition = new SeekPosition(new PlaylistTimeline(mediaSourceListUpdateMessage.mediaSourceHolders, mediaSourceListUpdateMessage.shuffleOrder), mediaSourceListUpdateMessage.windowIndex, mediaSourceListUpdateMessage.positionUs);
        }
        handleMediaSourceListInfoRefreshed(this.mediaSourceList.setMediaSources(mediaSourceListUpdateMessage.mediaSourceHolders, mediaSourceListUpdateMessage.shuffleOrder), false);
    }

    private void addMediaItemsInternal(MediaSourceListUpdateMessage addMessage, int insertionIndex) throws Throwable {
        this.playbackInfoUpdate.incrementPendingOperationAcks(1);
        MediaSourceList mediaSourceList = this.mediaSourceList;
        if (insertionIndex == -1) {
            insertionIndex = mediaSourceList.getSize();
        }
        handleMediaSourceListInfoRefreshed(mediaSourceList.addMediaSources(insertionIndex, addMessage.mediaSourceHolders, addMessage.shuffleOrder), false);
    }

    private void moveMediaItemsInternal(MoveMediaItemsMessage moveMediaItemsMessage) throws Throwable {
        this.playbackInfoUpdate.incrementPendingOperationAcks(1);
        handleMediaSourceListInfoRefreshed(this.mediaSourceList.moveMediaSourceRange(moveMediaItemsMessage.fromIndex, moveMediaItemsMessage.toIndex, moveMediaItemsMessage.newFromIndex, moveMediaItemsMessage.shuffleOrder), false);
    }

    private void removeMediaItemsInternal(int fromIndex, int toIndex, ShuffleOrder shuffleOrder) throws Throwable {
        this.playbackInfoUpdate.incrementPendingOperationAcks(1);
        handleMediaSourceListInfoRefreshed(this.mediaSourceList.removeMediaSourceRange(fromIndex, toIndex, shuffleOrder), false);
    }

    private void mediaSourceListUpdateRequestedInternal() throws Throwable {
        handleMediaSourceListInfoRefreshed(this.mediaSourceList.createTimeline(), true);
    }

    private void setShuffleOrderInternal(ShuffleOrder shuffleOrder) throws Throwable {
        this.playbackInfoUpdate.incrementPendingOperationAcks(1);
        handleMediaSourceListInfoRefreshed(this.mediaSourceList.setShuffleOrder(shuffleOrder), false);
    }

    private void notifyTrackSelectionPlayWhenReadyChanged(boolean playWhenReady) {
        for (MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod(); playingPeriod != null; playingPeriod = playingPeriod.getNext()) {
            for (ExoTrackSelection exoTrackSelection : playingPeriod.getTrackSelectorResult().selections) {
                if (exoTrackSelection != null) {
                    exoTrackSelection.onPlayWhenReadyChanged(playWhenReady);
                }
            }
        }
    }

    private void setPlayWhenReadyInternal(boolean z, int i, boolean z2, int i2) throws ExoPlaybackException {
        this.playbackInfoUpdate.incrementPendingOperationAcks(z2 ? 1 : 0);
        this.playbackInfoUpdate.setPlayWhenReadyChangeReason(i2);
        this.playbackInfo = this.playbackInfo.copyWithPlayWhenReady(z, i);
        this.isRebuffering = false;
        notifyTrackSelectionPlayWhenReadyChanged(z);
        if (!shouldPlayWhenReady()) {
            stopRenderers();
            updatePlaybackPositions();
        } else if (this.playbackInfo.playbackState == 3) {
            startRenderers();
            this.handler.sendEmptyMessage(2);
        } else if (this.playbackInfo.playbackState == 2) {
            this.handler.sendEmptyMessage(2);
        }
    }

    private void setPauseAtEndOfWindowInternal(boolean pauseAtEndOfWindow) throws ExoPlaybackException {
        this.pauseAtEndOfWindow = pauseAtEndOfWindow;
        resetPendingPauseAtEndOfPeriod();
        if (!this.pendingPauseAtEndOfPeriod || this.queue.getReadingPeriod() == this.queue.getPlayingPeriod()) {
            return;
        }
        seekToCurrentPosition(true);
        handleLoadingMediaPeriodChanged(false);
    }

    private void setOffloadSchedulingEnabledInternal(boolean offloadSchedulingEnabled) {
        if (offloadSchedulingEnabled == this.offloadSchedulingEnabled) {
            return;
        }
        this.offloadSchedulingEnabled = offloadSchedulingEnabled;
        int i = this.playbackInfo.playbackState;
        if (offloadSchedulingEnabled || i == 4 || i == 1) {
            this.playbackInfo = this.playbackInfo.copyWithOffloadSchedulingEnabled(offloadSchedulingEnabled);
        } else {
            this.handler.sendEmptyMessage(2);
        }
    }

    private void setRepeatModeInternal(int repeatMode) throws ExoPlaybackException {
        this.repeatMode = repeatMode;
        if (!this.queue.updateRepeatMode(this.playbackInfo.timeline, repeatMode)) {
            seekToCurrentPosition(true);
        }
        handleLoadingMediaPeriodChanged(false);
    }

    private void setShuffleModeEnabledInternal(boolean shuffleModeEnabled) throws ExoPlaybackException {
        this.shuffleModeEnabled = shuffleModeEnabled;
        if (!this.queue.updateShuffleModeEnabled(this.playbackInfo.timeline, shuffleModeEnabled)) {
            seekToCurrentPosition(true);
        }
        handleLoadingMediaPeriodChanged(false);
    }

    private void seekToCurrentPosition(boolean sendDiscontinuity) throws ExoPlaybackException {
        MediaSource.MediaPeriodId mediaPeriodId = this.queue.getPlayingPeriod().info.id;
        long jSeekToPeriodPosition = seekToPeriodPosition(mediaPeriodId, this.playbackInfo.positionUs, true, false);
        if (jSeekToPeriodPosition != this.playbackInfo.positionUs) {
            this.playbackInfo = handlePositionDiscontinuity(mediaPeriodId, jSeekToPeriodPosition, this.playbackInfo.requestedContentPositionUs, this.playbackInfo.discontinuityStartPositionUs, sendDiscontinuity, 5);
        }
    }

    private void startRenderers() throws ExoPlaybackException {
        this.isRebuffering = false;
        this.mediaClock.start();
        for (Renderer renderer : this.renderers) {
            if (isRendererEnabled(renderer)) {
                renderer.start();
            }
        }
    }

    private void stopRenderers() throws ExoPlaybackException {
        this.mediaClock.stop();
        for (Renderer renderer : this.renderers) {
            if (isRendererEnabled(renderer)) {
                ensureStopped(renderer);
            }
        }
    }

    private void attemptRendererErrorRecovery() throws ExoPlaybackException {
        seekToCurrentPosition(true);
    }

    private void updatePlaybackPositions() throws ExoPlaybackException {
        MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
        if (playingPeriod == null) {
            return;
        }
        long discontinuity = playingPeriod.prepared ? playingPeriod.mediaPeriod.readDiscontinuity() : -9223372036854775807L;
        if (discontinuity != C.TIME_UNSET) {
            resetRendererPosition(discontinuity);
            if (discontinuity != this.playbackInfo.positionUs) {
                this.playbackInfo = handlePositionDiscontinuity(this.playbackInfo.periodId, discontinuity, this.playbackInfo.requestedContentPositionUs, discontinuity, true, 5);
            }
        } else {
            long jSyncAndGetPositionUs = this.mediaClock.syncAndGetPositionUs(playingPeriod != this.queue.getReadingPeriod());
            this.rendererPositionUs = jSyncAndGetPositionUs;
            long periodTime = playingPeriod.toPeriodTime(jSyncAndGetPositionUs);
            maybeTriggerPendingMessages(this.playbackInfo.positionUs, periodTime);
            this.playbackInfo.positionUs = periodTime;
        }
        this.playbackInfo.bufferedPositionUs = this.queue.getLoadingPeriod().getBufferedPositionUs();
        this.playbackInfo.totalBufferedDurationUs = getTotalBufferedDurationUs();
        if (this.playbackInfo.playWhenReady && this.playbackInfo.playbackState == 3 && shouldUseLivePlaybackSpeedControl(this.playbackInfo.timeline, this.playbackInfo.periodId) && this.playbackInfo.playbackParameters.speed == 1.0f) {
            float adjustedPlaybackSpeed = this.livePlaybackSpeedControl.getAdjustedPlaybackSpeed(getCurrentLiveOffsetUs(), getTotalBufferedDurationUs());
            if (this.mediaClock.getPlaybackParameters().speed != adjustedPlaybackSpeed) {
                this.mediaClock.setPlaybackParameters(this.playbackInfo.playbackParameters.withSpeed(adjustedPlaybackSpeed));
                handlePlaybackParameters(this.playbackInfo.playbackParameters, this.mediaClock.getPlaybackParameters().speed, false, false);
            }
        }
    }

    private void notifyTrackSelectionRebuffer() {
        for (MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod(); playingPeriod != null; playingPeriod = playingPeriod.getNext()) {
            for (ExoTrackSelection exoTrackSelection : playingPeriod.getTrackSelectorResult().selections) {
                if (exoTrackSelection != null) {
                    exoTrackSelection.onRebuffer();
                }
            }
        }
    }

    private void doSomeWork() throws ExoPlaybackException, IOException {
        boolean z;
        boolean z2;
        boolean z3;
        long jUptimeMillis = this.clock.uptimeMillis();
        updatePeriods();
        if (this.playbackInfo.playbackState == 1 || this.playbackInfo.playbackState == 4) {
            this.handler.removeMessages(2);
            return;
        }
        MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
        if (playingPeriod == null) {
            scheduleNextWork(jUptimeMillis, 10L);
            return;
        }
        TraceUtil.beginSection("doSomeWork");
        updatePlaybackPositions();
        if (playingPeriod.prepared) {
            long jElapsedRealtime = SystemClock.elapsedRealtime() * 1000;
            playingPeriod.mediaPeriod.discardBuffer(this.playbackInfo.positionUs - this.backBufferDurationUs, this.retainBackBufferFromKeyframe);
            z = true;
            z2 = true;
            int i = 0;
            while (true) {
                Renderer[] rendererArr = this.renderers;
                if (i >= rendererArr.length) {
                    break;
                }
                Renderer renderer = rendererArr[i];
                if (isRendererEnabled(renderer)) {
                    renderer.render(this.rendererPositionUs, jElapsedRealtime);
                    z = z && renderer.isEnded();
                    boolean z4 = playingPeriod.sampleStreams[i] != renderer.getStream();
                    boolean z5 = z4 || (!z4 && renderer.hasReadStreamToEnd()) || renderer.isReady() || renderer.isEnded();
                    z2 = z2 && z5;
                    if (!z5) {
                        renderer.maybeThrowStreamError();
                    }
                }
                i++;
            }
        } else {
            playingPeriod.mediaPeriod.maybeThrowPrepareError();
            z = true;
            z2 = true;
        }
        long j = playingPeriod.info.durationUs;
        boolean z6 = z && playingPeriod.prepared && (j == C.TIME_UNSET || j <= this.playbackInfo.positionUs);
        if (z6 && this.pendingPauseAtEndOfPeriod) {
            this.pendingPauseAtEndOfPeriod = false;
            setPlayWhenReadyInternal(false, this.playbackInfo.playbackSuppressionReason, false, 5);
        }
        if (z6 && playingPeriod.info.isFinal) {
            setState(4);
            stopRenderers();
        } else if (this.playbackInfo.playbackState == 2 && shouldTransitionToReadyState(z2)) {
            setState(3);
            this.pendingRecoverableRendererError = null;
            if (shouldPlayWhenReady()) {
                startRenderers();
            }
        } else if (this.playbackInfo.playbackState == 3 && (this.enabledRendererCount != 0 ? !z2 : !isTimelineReady())) {
            this.isRebuffering = shouldPlayWhenReady();
            setState(2);
            if (this.isRebuffering) {
                notifyTrackSelectionRebuffer();
                this.livePlaybackSpeedControl.notifyRebuffer();
            }
            stopRenderers();
        }
        if (this.playbackInfo.playbackState == 2) {
            int i2 = 0;
            while (true) {
                Renderer[] rendererArr2 = this.renderers;
                if (i2 >= rendererArr2.length) {
                    break;
                }
                if (isRendererEnabled(rendererArr2[i2]) && this.renderers[i2].getStream() == playingPeriod.sampleStreams[i2]) {
                    this.renderers[i2].maybeThrowStreamError();
                }
                i2++;
            }
            if (!this.playbackInfo.isLoading && this.playbackInfo.totalBufferedDurationUs < 500000 && isLoadingPossible()) {
                throw new IllegalStateException("Playback stuck buffering and not loading");
            }
        }
        if (this.offloadSchedulingEnabled != this.playbackInfo.offloadSchedulingEnabled) {
            this.playbackInfo = this.playbackInfo.copyWithOffloadSchedulingEnabled(this.offloadSchedulingEnabled);
        }
        if ((shouldPlayWhenReady() && this.playbackInfo.playbackState == 3) || this.playbackInfo.playbackState == 2) {
            z3 = !maybeScheduleWakeup(jUptimeMillis, 10L);
        } else {
            if (this.enabledRendererCount != 0 && this.playbackInfo.playbackState != 4) {
                scheduleNextWork(jUptimeMillis, 1000L);
            } else {
                this.handler.removeMessages(2);
            }
            z3 = false;
        }
        if (this.playbackInfo.sleepingForOffload != z3) {
            this.playbackInfo = this.playbackInfo.copyWithSleepingForOffload(z3);
        }
        this.requestForRendererSleep = false;
        TraceUtil.endSection();
    }

    private long getCurrentLiveOffsetUs() {
        return getLiveOffsetUs(this.playbackInfo.timeline, this.playbackInfo.periodId.periodUid, this.playbackInfo.positionUs);
    }

    private long getLiveOffsetUs(Timeline timeline, Object periodUid, long periodPositionUs) {
        timeline.getWindow(timeline.getPeriodByUid(periodUid, this.period).windowIndex, this.window);
        return (this.window.windowStartTimeMs != C.TIME_UNSET && this.window.isLive() && this.window.isDynamic) ? C.msToUs(this.window.getCurrentUnixTimeMs() - this.window.windowStartTimeMs) - (periodPositionUs + this.period.getPositionInWindowUs()) : C.TIME_UNSET;
    }

    private boolean shouldUseLivePlaybackSpeedControl(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId) {
        if (mediaPeriodId.isAd() || timeline.isEmpty()) {
            return false;
        }
        timeline.getWindow(timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).windowIndex, this.window);
        return this.window.isLive() && this.window.isDynamic && this.window.windowStartTimeMs != C.TIME_UNSET;
    }

    private void scheduleNextWork(long thisOperationStartTimeMs, long intervalMs) {
        this.handler.removeMessages(2);
        this.handler.sendEmptyMessageAtTime(2, thisOperationStartTimeMs + intervalMs);
    }

    private boolean maybeScheduleWakeup(long operationStartTimeMs, long intervalMs) {
        if (this.offloadSchedulingEnabled && this.requestForRendererSleep) {
            return false;
        }
        scheduleNextWork(operationStartTimeMs, intervalMs);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00ac A[Catch: all -> 0x0153, TryCatch #0 {all -> 0x0153, blocks: (B:22:0x00a2, B:24:0x00ac, B:27:0x00b2, B:29:0x00b8, B:30:0x00bb, B:32:0x00c1, B:34:0x00cb, B:36:0x00d3, B:40:0x00db, B:42:0x00e5, B:44:0x00f5, B:46:0x00fc, B:48:0x0103, B:52:0x0117, B:56:0x0120), top: B:72:0x00a2 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void seekToInternal(com.google.android.exoplayer2.ExoPlayerImplInternal.SeekPosition r19) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 356
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.seekToInternal(com.google.android.exoplayer2.ExoPlayerImplInternal$SeekPosition):void");
    }

    private long seekToPeriodPosition(MediaSource.MediaPeriodId periodId, long periodPositionUs, boolean forceBufferingState) throws ExoPlaybackException {
        return seekToPeriodPosition(periodId, periodPositionUs, this.queue.getPlayingPeriod() != this.queue.getReadingPeriod(), forceBufferingState);
    }

    private long seekToPeriodPosition(MediaSource.MediaPeriodId periodId, long periodPositionUs, boolean forceDisableRenderers, boolean forceBufferingState) throws ExoPlaybackException {
        stopRenderers();
        this.isRebuffering = false;
        if (forceBufferingState || this.playbackInfo.playbackState == 3) {
            setState(2);
        }
        MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
        MediaPeriodHolder next = playingPeriod;
        while (next != null && !periodId.equals(next.info.id)) {
            next = next.getNext();
        }
        if (forceDisableRenderers || playingPeriod != next || (next != null && next.toRendererTime(periodPositionUs) < 0)) {
            for (Renderer renderer : this.renderers) {
                disableRenderer(renderer);
            }
            if (next != null) {
                while (this.queue.getPlayingPeriod() != next) {
                    this.queue.advancePlayingPeriod();
                }
                this.queue.removeAfter(next);
                next.setRendererOffset(0L);
                enableRenderers();
            }
        }
        if (next != null) {
            this.queue.removeAfter(next);
            if (!next.prepared) {
                next.info = next.info.copyWithStartPositionUs(periodPositionUs);
            } else {
                if (next.info.durationUs != C.TIME_UNSET && periodPositionUs >= next.info.durationUs) {
                    periodPositionUs = Math.max(0L, next.info.durationUs - 1);
                }
                if (next.hasEnabledTracks) {
                    long jSeekToUs = next.mediaPeriod.seekToUs(periodPositionUs);
                    next.mediaPeriod.discardBuffer(jSeekToUs - this.backBufferDurationUs, this.retainBackBufferFromKeyframe);
                    periodPositionUs = jSeekToUs;
                }
            }
            resetRendererPosition(periodPositionUs);
            maybeContinueLoading();
        } else {
            this.queue.clear();
            resetRendererPosition(periodPositionUs);
        }
        handleLoadingMediaPeriodChanged(false);
        this.handler.sendEmptyMessage(2);
        return periodPositionUs;
    }

    private void resetRendererPosition(long periodPositionUs) throws ExoPlaybackException {
        MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
        if (playingPeriod != null) {
            periodPositionUs = playingPeriod.toRendererTime(periodPositionUs);
        }
        this.rendererPositionUs = periodPositionUs;
        this.mediaClock.resetPosition(periodPositionUs);
        for (Renderer renderer : this.renderers) {
            if (isRendererEnabled(renderer)) {
                renderer.resetPosition(this.rendererPositionUs);
            }
        }
        notifyTrackSelectionDiscontinuity();
    }

    private void setPlaybackParametersInternal(PlaybackParameters playbackParameters) throws ExoPlaybackException {
        this.mediaClock.setPlaybackParameters(playbackParameters);
        handlePlaybackParameters(this.mediaClock.getPlaybackParameters(), true);
    }

    private void setSeekParametersInternal(SeekParameters seekParameters) {
        this.seekParameters = seekParameters;
    }

    private void setForegroundModeInternal(boolean foregroundMode, AtomicBoolean processedFlag) {
        if (this.foregroundMode != foregroundMode) {
            this.foregroundMode = foregroundMode;
            if (!foregroundMode) {
                for (Renderer renderer : this.renderers) {
                    if (!isRendererEnabled(renderer)) {
                        renderer.reset();
                    }
                }
            }
        }
        if (processedFlag != null) {
            synchronized (this) {
                processedFlag.set(true);
                notifyAll();
            }
        }
    }

    private void stopInternal(boolean z, boolean z2) {
        resetInternal(z || !this.foregroundMode, false, true, false);
        this.playbackInfoUpdate.incrementPendingOperationAcks(z2 ? 1 : 0);
        this.loadControl.onStopped();
        setState(1);
    }

    private void releaseInternal() {
        resetInternal(true, false, true, false);
        this.loadControl.onReleased();
        setState(1);
        this.internalPlaybackThread.quit();
        synchronized (this) {
            this.released = true;
            notifyAll();
        }
    }

    private void resetInternal(boolean resetRenderers, boolean resetPosition, boolean releaseMediaSourceList, boolean resetError) {
        long j;
        MediaSource.MediaPeriodId mediaPeriodId;
        boolean z;
        long j2;
        long j3;
        this.handler.removeMessages(2);
        this.pendingRecoverableRendererError = null;
        this.isRebuffering = false;
        this.mediaClock.stop();
        this.rendererPositionUs = 0L;
        for (Renderer renderer : this.renderers) {
            try {
                disableRenderer(renderer);
            } catch (ExoPlaybackException | RuntimeException e) {
                Log.e(TAG, "Disable failed.", e);
            }
        }
        if (resetRenderers) {
            for (Renderer renderer2 : this.renderers) {
                try {
                    renderer2.reset();
                } catch (RuntimeException e2) {
                    Log.e(TAG, "Reset failed.", e2);
                }
            }
        }
        this.enabledRendererCount = 0;
        MediaSource.MediaPeriodId mediaPeriodId2 = this.playbackInfo.periodId;
        long j4 = this.playbackInfo.positionUs;
        if (this.playbackInfo.periodId.isAd() || isUsingPlaceholderPeriod(this.playbackInfo, this.period)) {
            j = this.playbackInfo.requestedContentPositionUs;
        } else {
            j = this.playbackInfo.positionUs;
        }
        if (resetPosition) {
            this.pendingInitialSeekPosition = null;
            Pair<MediaSource.MediaPeriodId, Long> placeholderFirstMediaPeriodPosition = getPlaceholderFirstMediaPeriodPosition(this.playbackInfo.timeline);
            MediaSource.MediaPeriodId mediaPeriodId3 = (MediaSource.MediaPeriodId) placeholderFirstMediaPeriodPosition.first;
            long jLongValue = ((Long) placeholderFirstMediaPeriodPosition.second).longValue();
            z = !mediaPeriodId3.equals(this.playbackInfo.periodId);
            mediaPeriodId = mediaPeriodId3;
            j2 = jLongValue;
            j3 = -9223372036854775807L;
        } else {
            mediaPeriodId = mediaPeriodId2;
            z = false;
            j2 = j4;
            j3 = j;
        }
        this.queue.clear();
        this.shouldContinueLoading = false;
        this.playbackInfo = new PlaybackInfo(this.playbackInfo.timeline, mediaPeriodId, j3, j2, this.playbackInfo.playbackState, resetError ? null : this.playbackInfo.playbackError, false, z ? TrackGroupArray.EMPTY : this.playbackInfo.trackGroups, z ? this.emptyTrackSelectorResult : this.playbackInfo.trackSelectorResult, z ? ImmutableList.of() : this.playbackInfo.staticMetadata, mediaPeriodId, this.playbackInfo.playWhenReady, this.playbackInfo.playbackSuppressionReason, this.playbackInfo.playbackParameters, j2, 0L, j2, this.offloadSchedulingEnabled, false);
        if (releaseMediaSourceList) {
            this.mediaSourceList.release();
        }
    }

    private Pair<MediaSource.MediaPeriodId, Long> getPlaceholderFirstMediaPeriodPosition(Timeline timeline) {
        if (timeline.isEmpty()) {
            return Pair.create(PlaybackInfo.getDummyPeriodForEmptyTimeline(), 0L);
        }
        Pair<Object, Long> periodPosition = timeline.getPeriodPosition(this.window, this.period, timeline.getFirstWindowIndex(this.shuffleModeEnabled), C.TIME_UNSET);
        MediaSource.MediaPeriodId mediaPeriodIdResolveMediaPeriodIdForAds = this.queue.resolveMediaPeriodIdForAds(timeline, periodPosition.first, 0L);
        long jLongValue = ((Long) periodPosition.second).longValue();
        if (mediaPeriodIdResolveMediaPeriodIdForAds.isAd()) {
            timeline.getPeriodByUid(mediaPeriodIdResolveMediaPeriodIdForAds.periodUid, this.period);
            jLongValue = mediaPeriodIdResolveMediaPeriodIdForAds.adIndexInAdGroup == this.period.getFirstAdIndexToPlay(mediaPeriodIdResolveMediaPeriodIdForAds.adGroupIndex) ? this.period.getAdResumePositionUs() : 0L;
        }
        return Pair.create(mediaPeriodIdResolveMediaPeriodIdForAds, Long.valueOf(jLongValue));
    }

    private void sendMessageInternal(PlayerMessage message) throws ExoPlaybackException {
        if (message.getPositionMs() == C.TIME_UNSET) {
            sendMessageToTarget(message);
            return;
        }
        if (this.playbackInfo.timeline.isEmpty()) {
            this.pendingMessages.add(new PendingMessageInfo(message));
            return;
        }
        PendingMessageInfo pendingMessageInfo = new PendingMessageInfo(message);
        if (resolvePendingMessagePosition(pendingMessageInfo, this.playbackInfo.timeline, this.playbackInfo.timeline, this.repeatMode, this.shuffleModeEnabled, this.window, this.period)) {
            this.pendingMessages.add(pendingMessageInfo);
            Collections.sort(this.pendingMessages);
        } else {
            message.markAsProcessed(false);
        }
    }

    private void sendMessageToTarget(PlayerMessage message) throws ExoPlaybackException {
        if (message.getLooper() == this.playbackLooper) {
            deliverMessage(message);
            if (this.playbackInfo.playbackState == 3 || this.playbackInfo.playbackState == 2) {
                this.handler.sendEmptyMessage(2);
                return;
            }
            return;
        }
        this.handler.obtainMessage(15, message).sendToTarget();
    }

    private void sendMessageToTargetThread(final PlayerMessage message) {
        Looper looper = message.getLooper();
        if (!looper.getThread().isAlive()) {
            Log.w("TAG", "Trying to send message on a dead thread.");
            message.markAsProcessed(false);
        } else {
            this.clock.createHandler(looper, null).post(new Runnable() { // from class: com.google.android.exoplayer2.ExoPlayerImplInternal$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m133xacec0129(message);
                }
            });
        }
    }

    /* renamed from: lambda$sendMessageToTargetThread$1$com-google-android-exoplayer2-ExoPlayerImplInternal, reason: not valid java name */
    /* synthetic */ void m133xacec0129(PlayerMessage playerMessage) {
        try {
            deliverMessage(playerMessage);
        } catch (ExoPlaybackException e) {
            Log.e(TAG, "Unexpected error delivering message on external thread.", e);
            throw new RuntimeException(e);
        }
    }

    private void deliverMessage(PlayerMessage message) throws ExoPlaybackException {
        if (message.isCanceled()) {
            return;
        }
        try {
            message.getTarget().handleMessage(message.getType(), message.getPayload());
        } finally {
            message.markAsProcessed(true);
        }
    }

    private void resolvePendingMessagePositions(Timeline newTimeline, Timeline previousTimeline) {
        if (newTimeline.isEmpty() && previousTimeline.isEmpty()) {
            return;
        }
        for (int size = this.pendingMessages.size() - 1; size >= 0; size--) {
            if (!resolvePendingMessagePosition(this.pendingMessages.get(size), newTimeline, previousTimeline, this.repeatMode, this.shuffleModeEnabled, this.window, this.period)) {
                this.pendingMessages.get(size).message.markAsProcessed(false);
                this.pendingMessages.remove(size);
            }
        }
        Collections.sort(this.pendingMessages);
    }

    /* JADX WARN: Code restructure failed: missing block: B:72:0x0047, code lost:
    
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0078, code lost:
    
        r3 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void maybeTriggerPendingMessages(long r7, long r9) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            Method dump skipped, instructions count: 256
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.maybeTriggerPendingMessages(long, long):void");
    }

    private void ensureStopped(Renderer renderer) throws ExoPlaybackException {
        if (renderer.getState() == 2) {
            renderer.stop();
        }
    }

    private void disableRenderer(Renderer renderer) throws ExoPlaybackException {
        if (isRendererEnabled(renderer)) {
            this.mediaClock.onRendererDisabled(renderer);
            ensureStopped(renderer);
            renderer.disable();
            this.enabledRendererCount--;
        }
    }

    private void reselectTracksInternal() throws ExoPlaybackException {
        float f = this.mediaClock.getPlaybackParameters().speed;
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        boolean z = true;
        for (MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod(); playingPeriod != null && playingPeriod.prepared; playingPeriod = playingPeriod.getNext()) {
            TrackSelectorResult trackSelectorResultSelectTracks = playingPeriod.selectTracks(f, this.playbackInfo.timeline);
            if (trackSelectorResultSelectTracks.isEquivalent(playingPeriod.getTrackSelectorResult())) {
                if (playingPeriod == readingPeriod) {
                    z = false;
                }
            } else {
                if (z) {
                    MediaPeriodHolder playingPeriod2 = this.queue.getPlayingPeriod();
                    boolean zRemoveAfter = this.queue.removeAfter(playingPeriod2);
                    boolean[] zArr = new boolean[this.renderers.length];
                    long jApplyTrackSelection = playingPeriod2.applyTrackSelection(trackSelectorResultSelectTracks, this.playbackInfo.positionUs, zRemoveAfter, zArr);
                    boolean z2 = (this.playbackInfo.playbackState == 4 || jApplyTrackSelection == this.playbackInfo.positionUs) ? false : true;
                    this.playbackInfo = handlePositionDiscontinuity(this.playbackInfo.periodId, jApplyTrackSelection, this.playbackInfo.requestedContentPositionUs, this.playbackInfo.discontinuityStartPositionUs, z2, 5);
                    if (z2) {
                        resetRendererPosition(jApplyTrackSelection);
                    }
                    boolean[] zArr2 = new boolean[this.renderers.length];
                    int i = 0;
                    while (true) {
                        Renderer[] rendererArr = this.renderers;
                        if (i >= rendererArr.length) {
                            break;
                        }
                        Renderer renderer = rendererArr[i];
                        zArr2[i] = isRendererEnabled(renderer);
                        SampleStream sampleStream = playingPeriod2.sampleStreams[i];
                        if (zArr2[i]) {
                            if (sampleStream != renderer.getStream()) {
                                disableRenderer(renderer);
                            } else if (zArr[i]) {
                                renderer.resetPosition(this.rendererPositionUs);
                            }
                        }
                        i++;
                    }
                    enableRenderers(zArr2);
                } else {
                    this.queue.removeAfter(playingPeriod);
                    if (playingPeriod.prepared) {
                        playingPeriod.applyTrackSelection(trackSelectorResultSelectTracks, Math.max(playingPeriod.info.startPositionUs, playingPeriod.toPeriodTime(this.rendererPositionUs)), false);
                    }
                }
                handleLoadingMediaPeriodChanged(true);
                if (this.playbackInfo.playbackState != 4) {
                    maybeContinueLoading();
                    updatePlaybackPositions();
                    this.handler.sendEmptyMessage(2);
                    return;
                }
                return;
            }
        }
    }

    private void updateTrackSelectionPlaybackSpeed(float playbackSpeed) {
        for (MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod(); playingPeriod != null; playingPeriod = playingPeriod.getNext()) {
            for (ExoTrackSelection exoTrackSelection : playingPeriod.getTrackSelectorResult().selections) {
                if (exoTrackSelection != null) {
                    exoTrackSelection.onPlaybackSpeed(playbackSpeed);
                }
            }
        }
    }

    private void notifyTrackSelectionDiscontinuity() {
        for (MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod(); playingPeriod != null; playingPeriod = playingPeriod.getNext()) {
            for (ExoTrackSelection exoTrackSelection : playingPeriod.getTrackSelectorResult().selections) {
                if (exoTrackSelection != null) {
                    exoTrackSelection.onDiscontinuity();
                }
            }
        }
    }

    private boolean shouldTransitionToReadyState(boolean renderersReadyOrEnded) {
        if (this.enabledRendererCount == 0) {
            return isTimelineReady();
        }
        if (!renderersReadyOrEnded) {
            return false;
        }
        if (!this.playbackInfo.isLoading) {
            return true;
        }
        long targetLiveOffsetUs = shouldUseLivePlaybackSpeedControl(this.playbackInfo.timeline, this.queue.getPlayingPeriod().info.id) ? this.livePlaybackSpeedControl.getTargetLiveOffsetUs() : C.TIME_UNSET;
        MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
        return (loadingPeriod.isFullyBuffered() && loadingPeriod.info.isFinal) || (loadingPeriod.info.id.isAd() && !loadingPeriod.prepared) || this.loadControl.shouldStartPlayback(getTotalBufferedDurationUs(), this.mediaClock.getPlaybackParameters().speed, this.isRebuffering, targetLiveOffsetUs);
    }

    private boolean isTimelineReady() {
        MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
        long j = playingPeriod.info.durationUs;
        return playingPeriod.prepared && (j == C.TIME_UNSET || this.playbackInfo.positionUs < j || !shouldPlayWhenReady());
    }

    private void handleMediaSourceListInfoRefreshed(Timeline timeline, boolean isSourceRefresh) throws Throwable {
        int i;
        int i2;
        boolean z;
        PositionUpdateForPlaylistChange positionUpdateForPlaylistChangeResolvePositionForPlaylistChange = resolvePositionForPlaylistChange(timeline, this.playbackInfo, this.pendingInitialSeekPosition, this.queue, this.repeatMode, this.shuffleModeEnabled, this.window, this.period);
        MediaSource.MediaPeriodId mediaPeriodId = positionUpdateForPlaylistChangeResolvePositionForPlaylistChange.periodId;
        long j = positionUpdateForPlaylistChangeResolvePositionForPlaylistChange.requestedContentPositionUs;
        boolean z2 = positionUpdateForPlaylistChangeResolvePositionForPlaylistChange.forceBufferingState;
        long jSeekToPeriodPosition = positionUpdateForPlaylistChangeResolvePositionForPlaylistChange.periodPositionUs;
        boolean z3 = (this.playbackInfo.periodId.equals(mediaPeriodId) && jSeekToPeriodPosition == this.playbackInfo.positionUs) ? false : true;
        SeekPosition seekPosition = null;
        long j2 = C.TIME_UNSET;
        try {
            if (positionUpdateForPlaylistChangeResolvePositionForPlaylistChange.endPlayback) {
                if (this.playbackInfo.playbackState != 1) {
                    setState(4);
                }
                resetInternal(false, false, false, true);
            }
            try {
                if (z3) {
                    i2 = 4;
                    z = false;
                    if (!timeline.isEmpty()) {
                        for (MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod(); playingPeriod != null; playingPeriod = playingPeriod.getNext()) {
                            if (playingPeriod.info.id.equals(mediaPeriodId)) {
                                playingPeriod.info = this.queue.getUpdatedMediaPeriodInfo(timeline, playingPeriod.info);
                                playingPeriod.updateClipping();
                            }
                        }
                        jSeekToPeriodPosition = seekToPeriodPosition(mediaPeriodId, jSeekToPeriodPosition, z2);
                    }
                } else {
                    try {
                        i2 = 4;
                        z = false;
                        if (!this.queue.updateQueuedPeriods(timeline, this.rendererPositionUs, getMaxRendererReadPositionUs())) {
                            seekToCurrentPosition(false);
                        }
                    } catch (Throwable th) {
                        th = th;
                        i = 4;
                        Timeline timeline2 = this.playbackInfo.timeline;
                        MediaSource.MediaPeriodId mediaPeriodId2 = this.playbackInfo.periodId;
                        if (positionUpdateForPlaylistChangeResolvePositionForPlaylistChange.setTargetLiveOffset) {
                            j2 = jSeekToPeriodPosition;
                        }
                        SeekPosition seekPosition2 = seekPosition;
                        updateLivePlaybackSpeedControl(timeline, mediaPeriodId, timeline2, mediaPeriodId2, j2);
                        if (z3 || j != this.playbackInfo.requestedContentPositionUs) {
                            Object obj = this.playbackInfo.periodId.periodUid;
                            Timeline timeline3 = this.playbackInfo.timeline;
                            this.playbackInfo = handlePositionDiscontinuity(mediaPeriodId, jSeekToPeriodPosition, j, this.playbackInfo.discontinuityStartPositionUs, z3 && isSourceRefresh && !timeline3.isEmpty() && !timeline3.getPeriodByUid(obj, this.period).isPlaceholder, timeline.getIndexOfPeriod(obj) == -1 ? i : 3);
                        }
                        resetPendingPauseAtEndOfPeriod();
                        resolvePendingMessagePositions(timeline, this.playbackInfo.timeline);
                        this.playbackInfo = this.playbackInfo.copyWithTimeline(timeline);
                        if (!timeline.isEmpty()) {
                            this.pendingInitialSeekPosition = seekPosition2;
                        }
                        handleLoadingMediaPeriodChanged(false);
                        throw th;
                    }
                }
                updateLivePlaybackSpeedControl(timeline, mediaPeriodId, this.playbackInfo.timeline, this.playbackInfo.periodId, positionUpdateForPlaylistChangeResolvePositionForPlaylistChange.setTargetLiveOffset ? jSeekToPeriodPosition : -9223372036854775807L);
                if (z3 || j != this.playbackInfo.requestedContentPositionUs) {
                    Object obj2 = this.playbackInfo.periodId.periodUid;
                    Timeline timeline4 = this.playbackInfo.timeline;
                    this.playbackInfo = handlePositionDiscontinuity(mediaPeriodId, jSeekToPeriodPosition, j, this.playbackInfo.discontinuityStartPositionUs, (!z3 || !isSourceRefresh || timeline4.isEmpty() || timeline4.getPeriodByUid(obj2, this.period).isPlaceholder) ? z : true, timeline.getIndexOfPeriod(obj2) == -1 ? i2 : 3);
                }
                resetPendingPauseAtEndOfPeriod();
                resolvePendingMessagePositions(timeline, this.playbackInfo.timeline);
                this.playbackInfo = this.playbackInfo.copyWithTimeline(timeline);
                if (!timeline.isEmpty()) {
                    this.pendingInitialSeekPosition = null;
                }
                handleLoadingMediaPeriodChanged(z);
            } catch (Throwable th2) {
                th = th2;
                seekPosition = null;
            }
        } catch (Throwable th3) {
            th = th3;
            i = 4;
        }
    }

    private void updateLivePlaybackSpeedControl(Timeline newTimeline, MediaSource.MediaPeriodId newPeriodId, Timeline oldTimeline, MediaSource.MediaPeriodId oldPeriodId, long positionForTargetOffsetOverrideUs) {
        if (newTimeline.isEmpty() || !shouldUseLivePlaybackSpeedControl(newTimeline, newPeriodId)) {
            if (this.mediaClock.getPlaybackParameters().speed != this.playbackInfo.playbackParameters.speed) {
                this.mediaClock.setPlaybackParameters(this.playbackInfo.playbackParameters);
                return;
            }
            return;
        }
        newTimeline.getWindow(newTimeline.getPeriodByUid(newPeriodId.periodUid, this.period).windowIndex, this.window);
        this.livePlaybackSpeedControl.setLiveConfiguration((MediaItem.LiveConfiguration) Util.castNonNull(this.window.liveConfiguration));
        if (positionForTargetOffsetOverrideUs != C.TIME_UNSET) {
            this.livePlaybackSpeedControl.setTargetLiveOffsetOverrideUs(getLiveOffsetUs(newTimeline, newPeriodId.periodUid, positionForTargetOffsetOverrideUs));
            return;
        }
        if (Util.areEqual(!oldTimeline.isEmpty() ? oldTimeline.getWindow(oldTimeline.getPeriodByUid(oldPeriodId.periodUid, this.period).windowIndex, this.window).uid : null, this.window.uid)) {
            return;
        }
        this.livePlaybackSpeedControl.setTargetLiveOffsetOverrideUs(C.TIME_UNSET);
    }

    private long getMaxRendererReadPositionUs() {
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        if (readingPeriod == null) {
            return 0L;
        }
        long rendererOffset = readingPeriod.getRendererOffset();
        if (!readingPeriod.prepared) {
            return rendererOffset;
        }
        int i = 0;
        while (true) {
            Renderer[] rendererArr = this.renderers;
            if (i >= rendererArr.length) {
                return rendererOffset;
            }
            if (isRendererEnabled(rendererArr[i]) && this.renderers[i].getStream() == readingPeriod.sampleStreams[i]) {
                long readingPositionUs = this.renderers[i].getReadingPositionUs();
                if (readingPositionUs == Long.MIN_VALUE) {
                    return Long.MIN_VALUE;
                }
                rendererOffset = Math.max(readingPositionUs, rendererOffset);
            }
            i++;
        }
    }

    private void updatePeriods() throws ExoPlaybackException, IOException {
        if (this.playbackInfo.timeline.isEmpty() || !this.mediaSourceList.isPrepared()) {
            return;
        }
        maybeUpdateLoadingPeriod();
        maybeUpdateReadingPeriod();
        maybeUpdateReadingRenderers();
        maybeUpdatePlayingPeriod();
    }

    private void maybeUpdateLoadingPeriod() throws ExoPlaybackException {
        MediaPeriodInfo nextMediaPeriodInfo;
        this.queue.reevaluateBuffer(this.rendererPositionUs);
        if (this.queue.shouldLoadNextMediaPeriod() && (nextMediaPeriodInfo = this.queue.getNextMediaPeriodInfo(this.rendererPositionUs, this.playbackInfo)) != null) {
            MediaPeriodHolder mediaPeriodHolderEnqueueNextMediaPeriodHolder = this.queue.enqueueNextMediaPeriodHolder(this.rendererCapabilities, this.trackSelector, this.loadControl.getAllocator(), this.mediaSourceList, nextMediaPeriodInfo, this.emptyTrackSelectorResult);
            mediaPeriodHolderEnqueueNextMediaPeriodHolder.mediaPeriod.prepare(this, nextMediaPeriodInfo.startPositionUs);
            if (this.queue.getPlayingPeriod() == mediaPeriodHolderEnqueueNextMediaPeriodHolder) {
                resetRendererPosition(mediaPeriodHolderEnqueueNextMediaPeriodHolder.getStartPositionRendererTime());
            }
            handleLoadingMediaPeriodChanged(false);
        }
        if (this.shouldContinueLoading) {
            this.shouldContinueLoading = isLoadingPossible();
            updateIsLoading();
        } else {
            maybeContinueLoading();
        }
    }

    private void maybeUpdateReadingPeriod() {
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        if (readingPeriod == null) {
            return;
        }
        int i = 0;
        if (readingPeriod.getNext() == null || this.pendingPauseAtEndOfPeriod) {
            if (!readingPeriod.info.isFinal && !this.pendingPauseAtEndOfPeriod) {
                return;
            }
            while (true) {
                Renderer[] rendererArr = this.renderers;
                if (i >= rendererArr.length) {
                    return;
                }
                Renderer renderer = rendererArr[i];
                SampleStream sampleStream = readingPeriod.sampleStreams[i];
                if (sampleStream != null && renderer.getStream() == sampleStream && renderer.hasReadStreamToEnd()) {
                    setCurrentStreamFinal(renderer, (readingPeriod.info.durationUs == C.TIME_UNSET || readingPeriod.info.durationUs == Long.MIN_VALUE) ? -9223372036854775807L : readingPeriod.getRendererOffset() + readingPeriod.info.durationUs);
                }
                i++;
            }
        } else if (hasReadingPeriodFinishedReading()) {
            if (readingPeriod.getNext().prepared || this.rendererPositionUs >= readingPeriod.getNext().getStartPositionRendererTime()) {
                TrackSelectorResult trackSelectorResult = readingPeriod.getTrackSelectorResult();
                MediaPeriodHolder mediaPeriodHolderAdvanceReadingPeriod = this.queue.advanceReadingPeriod();
                TrackSelectorResult trackSelectorResult2 = mediaPeriodHolderAdvanceReadingPeriod.getTrackSelectorResult();
                if (mediaPeriodHolderAdvanceReadingPeriod.prepared && mediaPeriodHolderAdvanceReadingPeriod.mediaPeriod.readDiscontinuity() != C.TIME_UNSET) {
                    setAllRendererStreamsFinal(mediaPeriodHolderAdvanceReadingPeriod.getStartPositionRendererTime());
                    return;
                }
                for (int i2 = 0; i2 < this.renderers.length; i2++) {
                    boolean zIsRendererEnabled = trackSelectorResult.isRendererEnabled(i2);
                    boolean zIsRendererEnabled2 = trackSelectorResult2.isRendererEnabled(i2);
                    if (zIsRendererEnabled && !this.renderers[i2].isCurrentStreamFinal()) {
                        boolean z = this.rendererCapabilities[i2].getTrackType() == 7;
                        RendererConfiguration rendererConfiguration = trackSelectorResult.rendererConfigurations[i2];
                        RendererConfiguration rendererConfiguration2 = trackSelectorResult2.rendererConfigurations[i2];
                        if (!zIsRendererEnabled2 || !rendererConfiguration2.equals(rendererConfiguration) || z) {
                            setCurrentStreamFinal(this.renderers[i2], mediaPeriodHolderAdvanceReadingPeriod.getStartPositionRendererTime());
                        }
                    }
                }
            }
        }
    }

    private void maybeUpdateReadingRenderers() throws ExoPlaybackException {
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        if (readingPeriod == null || this.queue.getPlayingPeriod() == readingPeriod || readingPeriod.allRenderersInCorrectState || !replaceStreamsOrDisableRendererForTransition()) {
            return;
        }
        enableRenderers();
    }

    private boolean replaceStreamsOrDisableRendererForTransition() throws ExoPlaybackException {
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        TrackSelectorResult trackSelectorResult = readingPeriod.getTrackSelectorResult();
        int i = 0;
        boolean z = false;
        while (true) {
            Renderer[] rendererArr = this.renderers;
            if (i >= rendererArr.length) {
                return !z;
            }
            Renderer renderer = rendererArr[i];
            if (isRendererEnabled(renderer)) {
                boolean z2 = renderer.getStream() != readingPeriod.sampleStreams[i];
                if (!trackSelectorResult.isRendererEnabled(i) || z2) {
                    if (!renderer.isCurrentStreamFinal()) {
                        renderer.replaceStream(getFormats(trackSelectorResult.selections[i]), readingPeriod.sampleStreams[i], readingPeriod.getStartPositionRendererTime(), readingPeriod.getRendererOffset());
                    } else if (renderer.isEnded()) {
                        disableRenderer(renderer);
                    } else {
                        z = true;
                    }
                }
            }
            i++;
        }
    }

    private void maybeUpdatePlayingPeriod() throws ExoPlaybackException {
        boolean z = false;
        while (shouldAdvancePlayingPeriod()) {
            if (z) {
                maybeNotifyPlaybackInfoChanged();
            }
            MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
            MediaPeriodHolder mediaPeriodHolderAdvancePlayingPeriod = this.queue.advancePlayingPeriod();
            PlaybackInfo playbackInfoHandlePositionDiscontinuity = handlePositionDiscontinuity(mediaPeriodHolderAdvancePlayingPeriod.info.id, mediaPeriodHolderAdvancePlayingPeriod.info.startPositionUs, mediaPeriodHolderAdvancePlayingPeriod.info.requestedContentPositionUs, mediaPeriodHolderAdvancePlayingPeriod.info.startPositionUs, true, 0);
            this.playbackInfo = playbackInfoHandlePositionDiscontinuity;
            updateLivePlaybackSpeedControl(playbackInfoHandlePositionDiscontinuity.timeline, mediaPeriodHolderAdvancePlayingPeriod.info.id, this.playbackInfo.timeline, playingPeriod.info.id, C.TIME_UNSET);
            resetPendingPauseAtEndOfPeriod();
            updatePlaybackPositions();
            z = true;
        }
    }

    private void resetPendingPauseAtEndOfPeriod() {
        MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
        this.pendingPauseAtEndOfPeriod = playingPeriod != null && playingPeriod.info.isLastInTimelineWindow && this.pauseAtEndOfWindow;
    }

    private boolean shouldAdvancePlayingPeriod() {
        MediaPeriodHolder playingPeriod;
        MediaPeriodHolder next;
        return shouldPlayWhenReady() && !this.pendingPauseAtEndOfPeriod && (playingPeriod = this.queue.getPlayingPeriod()) != null && (next = playingPeriod.getNext()) != null && this.rendererPositionUs >= next.getStartPositionRendererTime() && next.allRenderersInCorrectState;
    }

    private boolean hasReadingPeriodFinishedReading() {
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        if (!readingPeriod.prepared) {
            return false;
        }
        int i = 0;
        while (true) {
            Renderer[] rendererArr = this.renderers;
            if (i >= rendererArr.length) {
                return true;
            }
            Renderer renderer = rendererArr[i];
            SampleStream sampleStream = readingPeriod.sampleStreams[i];
            if (renderer.getStream() != sampleStream || (sampleStream != null && !renderer.hasReadStreamToEnd() && !hasReachedServerSideInsertedAdsTransition(renderer, readingPeriod))) {
                break;
            }
            i++;
        }
        return false;
    }

    private boolean hasReachedServerSideInsertedAdsTransition(Renderer renderer, MediaPeriodHolder reading) {
        MediaPeriodHolder next = reading.getNext();
        return reading.info.isFollowedByTransitionToSameStream && next.prepared && ((renderer instanceof TextRenderer) || renderer.getReadingPositionUs() >= next.getStartPositionRendererTime());
    }

    private void setAllRendererStreamsFinal(long streamEndPositionUs) {
        for (Renderer renderer : this.renderers) {
            if (renderer.getStream() != null) {
                setCurrentStreamFinal(renderer, streamEndPositionUs);
            }
        }
    }

    private void setCurrentStreamFinal(Renderer renderer, long streamEndPositionUs) {
        renderer.setCurrentStreamFinal();
        if (renderer instanceof TextRenderer) {
            ((TextRenderer) renderer).setFinalStreamEndPositionUs(streamEndPositionUs);
        }
    }

    private void handlePeriodPrepared(MediaPeriod mediaPeriod) throws ExoPlaybackException {
        if (this.queue.isLoading(mediaPeriod)) {
            MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
            loadingPeriod.handlePrepared(this.mediaClock.getPlaybackParameters().speed, this.playbackInfo.timeline);
            updateLoadControlTrackSelection(loadingPeriod.getTrackGroups(), loadingPeriod.getTrackSelectorResult());
            if (loadingPeriod == this.queue.getPlayingPeriod()) {
                resetRendererPosition(loadingPeriod.info.startPositionUs);
                enableRenderers();
                this.playbackInfo = handlePositionDiscontinuity(this.playbackInfo.periodId, loadingPeriod.info.startPositionUs, this.playbackInfo.requestedContentPositionUs, loadingPeriod.info.startPositionUs, false, 5);
            }
            maybeContinueLoading();
        }
    }

    private void handleContinueLoadingRequested(MediaPeriod mediaPeriod) {
        if (this.queue.isLoading(mediaPeriod)) {
            this.queue.reevaluateBuffer(this.rendererPositionUs);
            maybeContinueLoading();
        }
    }

    private void handlePlaybackParameters(PlaybackParameters playbackParameters, boolean acknowledgeCommand) throws ExoPlaybackException {
        handlePlaybackParameters(playbackParameters, playbackParameters.speed, true, acknowledgeCommand);
    }

    private void handlePlaybackParameters(PlaybackParameters playbackParameters, float currentPlaybackSpeed, boolean updatePlaybackInfo, boolean acknowledgeCommand) throws ExoPlaybackException {
        if (updatePlaybackInfo) {
            if (acknowledgeCommand) {
                this.playbackInfoUpdate.incrementPendingOperationAcks(1);
            }
            this.playbackInfo = this.playbackInfo.copyWithPlaybackParameters(playbackParameters);
        }
        updateTrackSelectionPlaybackSpeed(playbackParameters.speed);
        for (Renderer renderer : this.renderers) {
            if (renderer != null) {
                renderer.setPlaybackSpeed(currentPlaybackSpeed, playbackParameters.speed);
            }
        }
    }

    private void maybeContinueLoading() {
        boolean zShouldContinueLoading = shouldContinueLoading();
        this.shouldContinueLoading = zShouldContinueLoading;
        if (zShouldContinueLoading) {
            this.queue.getLoadingPeriod().continueLoading(this.rendererPositionUs);
        }
        updateIsLoading();
    }

    private boolean shouldContinueLoading() {
        long periodTime;
        if (!isLoadingPossible()) {
            return false;
        }
        MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
        long totalBufferedDurationUs = getTotalBufferedDurationUs(loadingPeriod.getNextLoadPositionUs());
        if (loadingPeriod == this.queue.getPlayingPeriod()) {
            periodTime = loadingPeriod.toPeriodTime(this.rendererPositionUs);
        } else {
            periodTime = loadingPeriod.toPeriodTime(this.rendererPositionUs) - loadingPeriod.info.startPositionUs;
        }
        return this.loadControl.shouldContinueLoading(periodTime, totalBufferedDurationUs, this.mediaClock.getPlaybackParameters().speed);
    }

    private boolean isLoadingPossible() {
        MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
        return (loadingPeriod == null || loadingPeriod.getNextLoadPositionUs() == Long.MIN_VALUE) ? false : true;
    }

    private void updateIsLoading() {
        MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
        boolean z = this.shouldContinueLoading || (loadingPeriod != null && loadingPeriod.mediaPeriod.isLoading());
        if (z != this.playbackInfo.isLoading) {
            this.playbackInfo = this.playbackInfo.copyWithIsLoading(z);
        }
    }

    private PlaybackInfo handlePositionDiscontinuity(MediaSource.MediaPeriodId mediaPeriodId, long positionUs, long contentPositionUs, long discontinuityStartPositionUs, boolean reportDiscontinuity, int discontinuityReason) {
        TrackGroupArray trackGroupArray;
        TrackSelectorResult trackSelectorResult;
        List list;
        TrackGroupArray trackGroups;
        TrackSelectorResult trackSelectorResult2;
        this.deliverPendingMessageAtStartPositionRequired = (!this.deliverPendingMessageAtStartPositionRequired && positionUs == this.playbackInfo.positionUs && mediaPeriodId.equals(this.playbackInfo.periodId)) ? false : true;
        resetPendingPauseAtEndOfPeriod();
        TrackGroupArray trackGroupArray2 = this.playbackInfo.trackGroups;
        TrackSelectorResult trackSelectorResult3 = this.playbackInfo.trackSelectorResult;
        List listOf = this.playbackInfo.staticMetadata;
        if (this.mediaSourceList.isPrepared()) {
            MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
            if (playingPeriod == null) {
                trackGroups = TrackGroupArray.EMPTY;
            } else {
                trackGroups = playingPeriod.getTrackGroups();
            }
            if (playingPeriod == null) {
                trackSelectorResult2 = this.emptyTrackSelectorResult;
            } else {
                trackSelectorResult2 = playingPeriod.getTrackSelectorResult();
            }
            List listExtractMetadataFromTrackSelectionArray = extractMetadataFromTrackSelectionArray(trackSelectorResult2.selections);
            if (playingPeriod != null && playingPeriod.info.requestedContentPositionUs != contentPositionUs) {
                playingPeriod.info = playingPeriod.info.copyWithRequestedContentPositionUs(contentPositionUs);
            }
            trackGroupArray = trackGroups;
            trackSelectorResult = trackSelectorResult2;
            list = listExtractMetadataFromTrackSelectionArray;
        } else {
            if (!mediaPeriodId.equals(this.playbackInfo.periodId)) {
                trackGroupArray2 = TrackGroupArray.EMPTY;
                trackSelectorResult3 = this.emptyTrackSelectorResult;
                listOf = ImmutableList.of();
            }
            trackGroupArray = trackGroupArray2;
            trackSelectorResult = trackSelectorResult3;
            list = listOf;
        }
        if (reportDiscontinuity) {
            this.playbackInfoUpdate.setPositionDiscontinuity(discontinuityReason);
        }
        return this.playbackInfo.copyWithNewPosition(mediaPeriodId, positionUs, contentPositionUs, discontinuityStartPositionUs, getTotalBufferedDurationUs(), trackGroupArray, trackSelectorResult, list);
    }

    private ImmutableList<Metadata> extractMetadataFromTrackSelectionArray(ExoTrackSelection[] trackSelections) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        boolean z = false;
        for (ExoTrackSelection exoTrackSelection : trackSelections) {
            if (exoTrackSelection != null) {
                Format format = exoTrackSelection.getFormat(0);
                if (format.metadata == null) {
                    builder.add((ImmutableList.Builder) new Metadata(new Metadata.Entry[0]));
                } else {
                    builder.add((ImmutableList.Builder) format.metadata);
                    z = true;
                }
            }
        }
        return z ? builder.build() : ImmutableList.of();
    }

    private void enableRenderers() throws ExoPlaybackException {
        enableRenderers(new boolean[this.renderers.length]);
    }

    private void enableRenderers(boolean[] rendererWasEnabledFlags) throws ExoPlaybackException {
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        TrackSelectorResult trackSelectorResult = readingPeriod.getTrackSelectorResult();
        for (int i = 0; i < this.renderers.length; i++) {
            if (!trackSelectorResult.isRendererEnabled(i)) {
                this.renderers[i].reset();
            }
        }
        for (int i2 = 0; i2 < this.renderers.length; i2++) {
            if (trackSelectorResult.isRendererEnabled(i2)) {
                enableRenderer(i2, rendererWasEnabledFlags[i2]);
            }
        }
        readingPeriod.allRenderersInCorrectState = true;
    }

    private void enableRenderer(int rendererIndex, boolean wasRendererEnabled) throws ExoPlaybackException {
        Renderer renderer = this.renderers[rendererIndex];
        if (isRendererEnabled(renderer)) {
            return;
        }
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        boolean z = readingPeriod == this.queue.getPlayingPeriod();
        TrackSelectorResult trackSelectorResult = readingPeriod.getTrackSelectorResult();
        RendererConfiguration rendererConfiguration = trackSelectorResult.rendererConfigurations[rendererIndex];
        Format[] formats = getFormats(trackSelectorResult.selections[rendererIndex]);
        boolean z2 = shouldPlayWhenReady() && this.playbackInfo.playbackState == 3;
        boolean z3 = !wasRendererEnabled && z2;
        this.enabledRendererCount++;
        renderer.enable(rendererConfiguration, formats, readingPeriod.sampleStreams[rendererIndex], this.rendererPositionUs, z3, z, readingPeriod.getStartPositionRendererTime(), readingPeriod.getRendererOffset());
        renderer.handleMessage(103, new Renderer.WakeupListener() { // from class: com.google.android.exoplayer2.ExoPlayerImplInternal.1
            @Override // com.google.android.exoplayer2.Renderer.WakeupListener
            public void onSleep(long wakeupDeadlineMs) {
                if (wakeupDeadlineMs >= 2000) {
                    ExoPlayerImplInternal.this.requestForRendererSleep = true;
                }
            }

            @Override // com.google.android.exoplayer2.Renderer.WakeupListener
            public void onWakeup() {
                ExoPlayerImplInternal.this.handler.sendEmptyMessage(2);
            }
        });
        this.mediaClock.onRendererEnabled(renderer);
        if (z2) {
            renderer.start();
        }
    }

    private void handleLoadingMediaPeriodChanged(boolean loadingTrackSelectionChanged) {
        long bufferedPositionUs;
        MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
        MediaSource.MediaPeriodId mediaPeriodId = loadingPeriod == null ? this.playbackInfo.periodId : loadingPeriod.info.id;
        boolean z = !this.playbackInfo.loadingMediaPeriodId.equals(mediaPeriodId);
        if (z) {
            this.playbackInfo = this.playbackInfo.copyWithLoadingMediaPeriodId(mediaPeriodId);
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        if (loadingPeriod == null) {
            bufferedPositionUs = playbackInfo.positionUs;
        } else {
            bufferedPositionUs = loadingPeriod.getBufferedPositionUs();
        }
        playbackInfo.bufferedPositionUs = bufferedPositionUs;
        this.playbackInfo.totalBufferedDurationUs = getTotalBufferedDurationUs();
        if ((z || loadingTrackSelectionChanged) && loadingPeriod != null && loadingPeriod.prepared) {
            updateLoadControlTrackSelection(loadingPeriod.getTrackGroups(), loadingPeriod.getTrackSelectorResult());
        }
    }

    private long getTotalBufferedDurationUs() {
        return getTotalBufferedDurationUs(this.playbackInfo.bufferedPositionUs);
    }

    private long getTotalBufferedDurationUs(long bufferedPositionInLoadingPeriodUs) {
        MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
        if (loadingPeriod == null) {
            return 0L;
        }
        return Math.max(0L, bufferedPositionInLoadingPeriodUs - loadingPeriod.toPeriodTime(this.rendererPositionUs));
    }

    private void updateLoadControlTrackSelection(TrackGroupArray trackGroups, TrackSelectorResult trackSelectorResult) {
        this.loadControl.onTracksSelected(this.renderers, trackGroups, trackSelectorResult.selections);
    }

    private boolean shouldPlayWhenReady() {
        return this.playbackInfo.playWhenReady && this.playbackInfo.playbackSuppressionReason == 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01e0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.google.android.exoplayer2.ExoPlayerImplInternal.PositionUpdateForPlaylistChange resolvePositionForPlaylistChange(com.google.android.exoplayer2.Timeline r30, com.google.android.exoplayer2.PlaybackInfo r31, com.google.android.exoplayer2.ExoPlayerImplInternal.SeekPosition r32, com.google.android.exoplayer2.MediaPeriodQueue r33, int r34, boolean r35, com.google.android.exoplayer2.Timeline.Window r36, com.google.android.exoplayer2.Timeline.Period r37) {
        /*
            Method dump skipped, instructions count: 523
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.resolvePositionForPlaylistChange(com.google.android.exoplayer2.Timeline, com.google.android.exoplayer2.PlaybackInfo, com.google.android.exoplayer2.ExoPlayerImplInternal$SeekPosition, com.google.android.exoplayer2.MediaPeriodQueue, int, boolean, com.google.android.exoplayer2.Timeline$Window, com.google.android.exoplayer2.Timeline$Period):com.google.android.exoplayer2.ExoPlayerImplInternal$PositionUpdateForPlaylistChange");
    }

    private static boolean isUsingPlaceholderPeriod(PlaybackInfo playbackInfo, Timeline.Period period) {
        MediaSource.MediaPeriodId mediaPeriodId = playbackInfo.periodId;
        Timeline timeline = playbackInfo.timeline;
        return timeline.isEmpty() || timeline.getPeriodByUid(mediaPeriodId.periodUid, period).isPlaceholder;
    }

    private static boolean resolvePendingMessagePosition(PendingMessageInfo pendingMessageInfo, Timeline newTimeline, Timeline previousTimeline, int repeatMode, boolean shuffleModeEnabled, Timeline.Window window, Timeline.Period period) {
        if (pendingMessageInfo.resolvedPeriodUid == null) {
            Pair<Object, Long> pairResolveSeekPosition = resolveSeekPosition(newTimeline, new SeekPosition(pendingMessageInfo.message.getTimeline(), pendingMessageInfo.message.getWindowIndex(), pendingMessageInfo.message.getPositionMs() == Long.MIN_VALUE ? C.TIME_UNSET : C.msToUs(pendingMessageInfo.message.getPositionMs())), false, repeatMode, shuffleModeEnabled, window, period);
            if (pairResolveSeekPosition == null) {
                return false;
            }
            pendingMessageInfo.setResolvedPosition(newTimeline.getIndexOfPeriod(pairResolveSeekPosition.first), ((Long) pairResolveSeekPosition.second).longValue(), pairResolveSeekPosition.first);
            if (pendingMessageInfo.message.getPositionMs() == Long.MIN_VALUE) {
                resolvePendingMessageEndOfStreamPosition(newTimeline, pendingMessageInfo, window, period);
            }
            return true;
        }
        int indexOfPeriod = newTimeline.getIndexOfPeriod(pendingMessageInfo.resolvedPeriodUid);
        if (indexOfPeriod == -1) {
            return false;
        }
        if (pendingMessageInfo.message.getPositionMs() == Long.MIN_VALUE) {
            resolvePendingMessageEndOfStreamPosition(newTimeline, pendingMessageInfo, window, period);
            return true;
        }
        pendingMessageInfo.resolvedPeriodIndex = indexOfPeriod;
        previousTimeline.getPeriodByUid(pendingMessageInfo.resolvedPeriodUid, period);
        if (period.isPlaceholder && previousTimeline.getWindow(period.windowIndex, window).firstPeriodIndex == previousTimeline.getIndexOfPeriod(pendingMessageInfo.resolvedPeriodUid)) {
            Pair<Object, Long> periodPosition = newTimeline.getPeriodPosition(window, period, newTimeline.getPeriodByUid(pendingMessageInfo.resolvedPeriodUid, period).windowIndex, pendingMessageInfo.resolvedPeriodTimeUs + period.getPositionInWindowUs());
            pendingMessageInfo.setResolvedPosition(newTimeline.getIndexOfPeriod(periodPosition.first), ((Long) periodPosition.second).longValue(), periodPosition.first);
        }
        return true;
    }

    private static void resolvePendingMessageEndOfStreamPosition(Timeline timeline, PendingMessageInfo messageInfo, Timeline.Window window, Timeline.Period period) {
        int i = timeline.getWindow(timeline.getPeriodByUid(messageInfo.resolvedPeriodUid, period).windowIndex, window).lastPeriodIndex;
        messageInfo.setResolvedPosition(i, period.durationUs != C.TIME_UNSET ? period.durationUs - 1 : Long.MAX_VALUE, timeline.getPeriod(i, period, true).uid);
    }

    private static Pair<Object, Long> resolveSeekPosition(Timeline timeline, SeekPosition seekPosition, boolean trySubsequentPeriods, int repeatMode, boolean shuffleModeEnabled, Timeline.Window window, Timeline.Period period) {
        Pair<Object, Long> periodPosition;
        Object objResolveSubsequentPeriod;
        Timeline timeline2 = seekPosition.timeline;
        if (timeline.isEmpty()) {
            return null;
        }
        Timeline timeline3 = timeline2.isEmpty() ? timeline : timeline2;
        try {
            periodPosition = timeline3.getPeriodPosition(window, period, seekPosition.windowIndex, seekPosition.windowPositionUs);
        } catch (IndexOutOfBoundsException unused) {
        }
        if (timeline.equals(timeline3)) {
            return periodPosition;
        }
        if (timeline.getIndexOfPeriod(periodPosition.first) != -1) {
            return (timeline3.getPeriodByUid(periodPosition.first, period).isPlaceholder && timeline3.getWindow(period.windowIndex, window).firstPeriodIndex == timeline3.getIndexOfPeriod(periodPosition.first)) ? timeline.getPeriodPosition(window, period, timeline.getPeriodByUid(periodPosition.first, period).windowIndex, seekPosition.windowPositionUs) : periodPosition;
        }
        if (trySubsequentPeriods && (objResolveSubsequentPeriod = resolveSubsequentPeriod(window, period, repeatMode, shuffleModeEnabled, periodPosition.first, timeline3, timeline)) != null) {
            return timeline.getPeriodPosition(window, period, timeline.getPeriodByUid(objResolveSubsequentPeriod, period).windowIndex, C.TIME_UNSET);
        }
        return null;
    }

    static Object resolveSubsequentPeriod(Timeline.Window window, Timeline.Period period, int repeatMode, boolean shuffleModeEnabled, Object oldPeriodUid, Timeline oldTimeline, Timeline newTimeline) {
        int indexOfPeriod = oldTimeline.getIndexOfPeriod(oldPeriodUid);
        int periodCount = oldTimeline.getPeriodCount();
        int nextPeriodIndex = indexOfPeriod;
        int indexOfPeriod2 = -1;
        for (int i = 0; i < periodCount && indexOfPeriod2 == -1; i++) {
            nextPeriodIndex = oldTimeline.getNextPeriodIndex(nextPeriodIndex, period, window, repeatMode, shuffleModeEnabled);
            if (nextPeriodIndex == -1) {
                break;
            }
            indexOfPeriod2 = newTimeline.getIndexOfPeriod(oldTimeline.getUidOfPeriod(nextPeriodIndex));
        }
        if (indexOfPeriod2 == -1) {
            return null;
        }
        return newTimeline.getUidOfPeriod(indexOfPeriod2);
    }

    private static Format[] getFormats(ExoTrackSelection newSelection) {
        int length = newSelection != null ? newSelection.length() : 0;
        Format[] formatArr = new Format[length];
        for (int i = 0; i < length; i++) {
            formatArr[i] = newSelection.getFormat(i);
        }
        return formatArr;
    }

    private static boolean isRendererEnabled(Renderer renderer) {
        return renderer.getState() != 0;
    }

    private static final class SeekPosition {
        public final Timeline timeline;
        public final int windowIndex;
        public final long windowPositionUs;

        public SeekPosition(Timeline timeline, int windowIndex, long windowPositionUs) {
            this.timeline = timeline;
            this.windowIndex = windowIndex;
            this.windowPositionUs = windowPositionUs;
        }
    }

    private static final class PositionUpdateForPlaylistChange {
        public final boolean endPlayback;
        public final boolean forceBufferingState;
        public final MediaSource.MediaPeriodId periodId;
        public final long periodPositionUs;
        public final long requestedContentPositionUs;
        public final boolean setTargetLiveOffset;

        public PositionUpdateForPlaylistChange(MediaSource.MediaPeriodId periodId, long periodPositionUs, long requestedContentPositionUs, boolean forceBufferingState, boolean endPlayback, boolean setTargetLiveOffset) {
            this.periodId = periodId;
            this.periodPositionUs = periodPositionUs;
            this.requestedContentPositionUs = requestedContentPositionUs;
            this.forceBufferingState = forceBufferingState;
            this.endPlayback = endPlayback;
            this.setTargetLiveOffset = setTargetLiveOffset;
        }
    }

    private static final class PendingMessageInfo implements Comparable<PendingMessageInfo> {
        public final PlayerMessage message;
        public int resolvedPeriodIndex;
        public long resolvedPeriodTimeUs;
        public Object resolvedPeriodUid;

        public PendingMessageInfo(PlayerMessage message) {
            this.message = message;
        }

        public void setResolvedPosition(int periodIndex, long periodTimeUs, Object periodUid) {
            this.resolvedPeriodIndex = periodIndex;
            this.resolvedPeriodTimeUs = periodTimeUs;
            this.resolvedPeriodUid = periodUid;
        }

        @Override // java.lang.Comparable
        public int compareTo(PendingMessageInfo other) {
            Object obj = this.resolvedPeriodUid;
            if ((obj == null) != (other.resolvedPeriodUid == null)) {
                return obj != null ? -1 : 1;
            }
            if (obj == null) {
                return 0;
            }
            int i = this.resolvedPeriodIndex - other.resolvedPeriodIndex;
            return i != 0 ? i : Util.compareLong(this.resolvedPeriodTimeUs, other.resolvedPeriodTimeUs);
        }
    }

    private static final class MediaSourceListUpdateMessage {
        private final List<MediaSourceList.MediaSourceHolder> mediaSourceHolders;
        private final long positionUs;
        private final ShuffleOrder shuffleOrder;
        private final int windowIndex;

        private MediaSourceListUpdateMessage(List<MediaSourceList.MediaSourceHolder> mediaSourceHolders, ShuffleOrder shuffleOrder, int windowIndex, long positionUs) {
            this.mediaSourceHolders = mediaSourceHolders;
            this.shuffleOrder = shuffleOrder;
            this.windowIndex = windowIndex;
            this.positionUs = positionUs;
        }
    }

    private static class MoveMediaItemsMessage {
        public final int fromIndex;
        public final int newFromIndex;
        public final ShuffleOrder shuffleOrder;
        public final int toIndex;

        public MoveMediaItemsMessage(int fromIndex, int toIndex, int newFromIndex, ShuffleOrder shuffleOrder) {
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.newFromIndex = newFromIndex;
            this.shuffleOrder = shuffleOrder;
        }
    }
}
