package com.google.android.exoplayer2;

import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.video.VideoSize;
import java.util.List;

/* loaded from: classes.dex */
public class ForwardingPlayer implements Player {
    private final Player player;

    public ForwardingPlayer(Player player) {
        this.player = player;
    }

    @Override // com.google.android.exoplayer2.Player
    public Looper getApplicationLooper() {
        return this.player.getApplicationLooper();
    }

    @Override // com.google.android.exoplayer2.Player
    public void addListener(Player.EventListener listener) {
        this.player.addListener(new ForwardingEventListener(listener));
    }

    @Override // com.google.android.exoplayer2.Player
    public void addListener(Player.Listener listener) {
        this.player.addListener((Player.Listener) new ForwardingListener(this, listener));
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeListener(Player.EventListener listener) {
        this.player.removeListener(new ForwardingEventListener(listener));
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeListener(Player.Listener listener) {
        this.player.removeListener((Player.Listener) new ForwardingListener(this, listener));
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> mediaItems) {
        this.player.setMediaItems(mediaItems);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> mediaItems, boolean resetPosition) {
        this.player.setMediaItems(mediaItems, resetPosition);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> mediaItems, int startWindowIndex, long startPositionMs) {
        this.player.setMediaItems(mediaItems, startWindowIndex, startPositionMs);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItem(MediaItem mediaItem) {
        this.player.setMediaItem(mediaItem);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItem(MediaItem mediaItem, long startPositionMs) {
        this.player.setMediaItem(mediaItem, startPositionMs);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItem(MediaItem mediaItem, boolean resetPosition) {
        this.player.setMediaItem(mediaItem, resetPosition);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItem(MediaItem mediaItem) {
        this.player.addMediaItem(mediaItem);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItem(int index, MediaItem mediaItem) {
        this.player.addMediaItem(index, mediaItem);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItems(List<MediaItem> mediaItems) {
        this.player.addMediaItems(mediaItems);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItems(int index, List<MediaItem> mediaItems) {
        this.player.addMediaItems(index, mediaItems);
    }

    @Override // com.google.android.exoplayer2.Player
    public void moveMediaItem(int currentIndex, int newIndex) {
        this.player.moveMediaItem(currentIndex, newIndex);
    }

    @Override // com.google.android.exoplayer2.Player
    public void moveMediaItems(int fromIndex, int toIndex, int newIndex) {
        this.player.moveMediaItems(fromIndex, toIndex, newIndex);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeMediaItem(int index) {
        this.player.removeMediaItem(index);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeMediaItems(int fromIndex, int toIndex) {
        this.player.removeMediaItems(fromIndex, toIndex);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearMediaItems() {
        this.player.clearMediaItems();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isCommandAvailable(int command) {
        return this.player.isCommandAvailable(command);
    }

    @Override // com.google.android.exoplayer2.Player
    public Player.Commands getAvailableCommands() {
        return this.player.getAvailableCommands();
    }

    @Override // com.google.android.exoplayer2.Player
    public void prepare() {
        this.player.prepare();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackState() {
        return this.player.getPlaybackState();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackSuppressionReason() {
        return this.player.getPlaybackSuppressionReason();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isPlaying() {
        return this.player.isPlaying();
    }

    @Override // com.google.android.exoplayer2.Player
    public PlaybackException getPlayerError() {
        return this.player.getPlayerError();
    }

    @Override // com.google.android.exoplayer2.Player
    public void play() {
        this.player.play();
    }

    @Override // com.google.android.exoplayer2.Player
    public void pause() {
        this.player.pause();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlayWhenReady(boolean playWhenReady) {
        this.player.setPlayWhenReady(playWhenReady);
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getPlayWhenReady() {
        return this.player.getPlayWhenReady();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setRepeatMode(int repeatMode) {
        this.player.setRepeatMode(repeatMode);
    }

    @Override // com.google.android.exoplayer2.Player
    public int getRepeatMode() {
        return this.player.getRepeatMode();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setShuffleModeEnabled(boolean shuffleModeEnabled) {
        this.player.setShuffleModeEnabled(shuffleModeEnabled);
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getShuffleModeEnabled() {
        return this.player.getShuffleModeEnabled();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isLoading() {
        return this.player.isLoading();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToDefaultPosition() {
        this.player.seekToDefaultPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToDefaultPosition(int windowIndex) {
        this.player.seekToDefaultPosition(windowIndex);
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekTo(long positionMs) {
        this.player.seekTo(positionMs);
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekTo(int windowIndex, long positionMs) {
        this.player.seekTo(windowIndex, positionMs);
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekBackIncrement() {
        return this.player.getSeekBackIncrement();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekBack() {
        this.player.seekBack();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekForwardIncrement() {
        return this.player.getSeekForwardIncrement();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekForward() {
        this.player.seekForward();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public boolean hasPrevious() {
        return this.player.hasPrevious();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean hasPreviousWindow() {
        return this.player.hasPreviousWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void previous() {
        this.player.previous();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToPreviousWindow() {
        this.player.seekToPreviousWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToPrevious() {
        this.player.seekToPrevious();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getMaxSeekToPreviousPosition() {
        return this.player.getMaxSeekToPreviousPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public boolean hasNext() {
        return this.player.hasNext();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean hasNextWindow() {
        return this.player.hasNextWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void next() {
        this.player.next();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToNextWindow() {
        this.player.seekToNextWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToNext() {
        this.player.seekToNext();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.player.setPlaybackParameters(playbackParameters);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaybackSpeed(float speed) {
        this.player.setPlaybackSpeed(speed);
    }

    @Override // com.google.android.exoplayer2.Player
    public PlaybackParameters getPlaybackParameters() {
        return this.player.getPlaybackParameters();
    }

    @Override // com.google.android.exoplayer2.Player
    public void stop() {
        this.player.stop();
    }

    @Override // com.google.android.exoplayer2.Player
    public void stop(boolean reset) {
        this.player.stop(reset);
    }

    @Override // com.google.android.exoplayer2.Player
    public void release() {
        this.player.release();
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackGroupArray getCurrentTrackGroups() {
        return this.player.getCurrentTrackGroups();
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackSelectionArray getCurrentTrackSelections() {
        return this.player.getCurrentTrackSelections();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public List<Metadata> getCurrentStaticMetadata() {
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
    public Object getCurrentManifest() {
        return this.player.getCurrentManifest();
    }

    @Override // com.google.android.exoplayer2.Player
    public Timeline getCurrentTimeline() {
        return this.player.getCurrentTimeline();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentPeriodIndex() {
        return this.player.getCurrentPeriodIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentWindowIndex() {
        return this.player.getCurrentWindowIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getNextWindowIndex() {
        return this.player.getNextWindowIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPreviousWindowIndex() {
        return this.player.getPreviousWindowIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaItem getCurrentMediaItem() {
        return this.player.getCurrentMediaItem();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getMediaItemCount() {
        return this.player.getMediaItemCount();
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaItem getMediaItemAt(int index) {
        return this.player.getMediaItemAt(index);
    }

    @Override // com.google.android.exoplayer2.Player
    public long getDuration() {
        return this.player.getDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getCurrentPosition() {
        return this.player.getCurrentPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getBufferedPosition() {
        return this.player.getBufferedPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getBufferedPercentage() {
        return this.player.getBufferedPercentage();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getTotalBufferedDuration() {
        return this.player.getTotalBufferedDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isCurrentWindowDynamic() {
        return this.player.isCurrentWindowDynamic();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isCurrentWindowLive() {
        return this.player.isCurrentWindowLive();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getCurrentLiveOffset() {
        return this.player.getCurrentLiveOffset();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isCurrentWindowSeekable() {
        return this.player.isCurrentWindowSeekable();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isPlayingAd() {
        return this.player.isPlayingAd();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdGroupIndex() {
        return this.player.getCurrentAdGroupIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdIndexInAdGroup() {
        return this.player.getCurrentAdIndexInAdGroup();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentDuration() {
        return this.player.getContentDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentPosition() {
        return this.player.getContentPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentBufferedPosition() {
        return this.player.getContentBufferedPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public AudioAttributes getAudioAttributes() {
        return this.player.getAudioAttributes();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVolume(float audioVolume) {
        this.player.setVolume(audioVolume);
    }

    @Override // com.google.android.exoplayer2.Player
    public float getVolume() {
        return this.player.getVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public VideoSize getVideoSize() {
        return this.player.getVideoSize();
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface() {
        this.player.clearVideoSurface();
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface(Surface surface) {
        this.player.clearVideoSurface(surface);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurface(Surface surface) {
        this.player.setVideoSurface(surface);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceHolder(SurfaceHolder surfaceHolder) {
        this.player.setVideoSurfaceHolder(surfaceHolder);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceHolder(SurfaceHolder surfaceHolder) {
        this.player.clearVideoSurfaceHolder(surfaceHolder);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceView(SurfaceView surfaceView) {
        this.player.setVideoSurfaceView(surfaceView);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceView(SurfaceView surfaceView) {
        this.player.clearVideoSurfaceView(surfaceView);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoTextureView(TextureView textureView) {
        this.player.setVideoTextureView(textureView);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoTextureView(TextureView textureView) {
        this.player.clearVideoTextureView(textureView);
    }

    @Override // com.google.android.exoplayer2.Player
    public List<Cue> getCurrentCues() {
        return this.player.getCurrentCues();
    }

    @Override // com.google.android.exoplayer2.Player
    public DeviceInfo getDeviceInfo() {
        return this.player.getDeviceInfo();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getDeviceVolume() {
        return this.player.getDeviceVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isDeviceMuted() {
        return this.player.isDeviceMuted();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceVolume(int volume) {
        this.player.setDeviceVolume(volume);
    }

    @Override // com.google.android.exoplayer2.Player
    public void increaseDeviceVolume() {
        this.player.increaseDeviceVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public void decreaseDeviceVolume() {
        this.player.decreaseDeviceVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceMuted(boolean muted) {
        this.player.setDeviceMuted(muted);
    }

    private static class ForwardingEventListener implements Player.EventListener {
        private final Player.EventListener eventListener;
        private final ForwardingPlayer forwardingPlayer;

        private ForwardingEventListener(ForwardingPlayer forwardingPlayer, Player.EventListener eventListener) {
            this.forwardingPlayer = forwardingPlayer;
            this.eventListener = eventListener;
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTimelineChanged(Timeline timeline, int reason) {
            this.eventListener.onTimelineChanged(timeline, reason);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onMediaItemTransition(MediaItem mediaItem, int reason) {
            this.eventListener.onMediaItemTransition(mediaItem, reason);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            this.eventListener.onTracksChanged(trackGroups, trackSelections);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        @Deprecated
        public void onStaticMetadataChanged(List<Metadata> metadataList) {
            this.eventListener.onStaticMetadataChanged(metadataList);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
            this.eventListener.onMediaMetadataChanged(mediaMetadata);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
            this.eventListener.onPlaylistMetadataChanged(mediaMetadata);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onIsLoadingChanged(boolean isLoading) {
            this.eventListener.onIsLoadingChanged(isLoading);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onLoadingChanged(boolean isLoading) {
            this.eventListener.onIsLoadingChanged(isLoading);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onAvailableCommandsChanged(Player.Commands availableCommands) {
            this.eventListener.onAvailableCommandsChanged(availableCommands);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            this.eventListener.onPlayerStateChanged(playWhenReady, playbackState);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackStateChanged(int playbackState) {
            this.eventListener.onPlaybackStateChanged(playbackState);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
            this.eventListener.onPlayWhenReadyChanged(playWhenReady, reason);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
            this.eventListener.onPlaybackSuppressionReasonChanged(playbackSuppressionReason);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onIsPlayingChanged(boolean isPlaying) {
            this.eventListener.onIsPlayingChanged(isPlaying);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onRepeatModeChanged(int repeatMode) {
            this.eventListener.onRepeatModeChanged(repeatMode);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            this.eventListener.onShuffleModeEnabledChanged(shuffleModeEnabled);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerError(PlaybackException error) {
            this.eventListener.onPlayerError(error);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerErrorChanged(PlaybackException error) {
            this.eventListener.onPlayerErrorChanged(error);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPositionDiscontinuity(int reason) {
            this.eventListener.onPositionDiscontinuity(reason);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPositionDiscontinuity(Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
            this.eventListener.onPositionDiscontinuity(oldPosition, newPosition, reason);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            this.eventListener.onPlaybackParametersChanged(playbackParameters);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onSeekBackIncrementChanged(long seekBackIncrementMs) {
            this.eventListener.onSeekBackIncrementChanged(seekBackIncrementMs);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onSeekForwardIncrementChanged(long seekForwardIncrementMs) {
            this.eventListener.onSeekForwardIncrementChanged(seekForwardIncrementMs);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onMaxSeekToPreviousPositionChanged(int maxSeekToPreviousPositionMs) {
            this.eventListener.onMaxSeekToPreviousPositionChanged(maxSeekToPreviousPositionMs);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onSeekProcessed() {
            this.eventListener.onSeekProcessed();
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onEvents(Player player, Player.Events events) {
            this.eventListener.onEvents(this.forwardingPlayer, events);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ForwardingEventListener)) {
                return false;
            }
            ForwardingEventListener forwardingEventListener = (ForwardingEventListener) o;
            if (this.forwardingPlayer.equals(forwardingEventListener.forwardingPlayer)) {
                return this.eventListener.equals(forwardingEventListener.eventListener);
            }
            return false;
        }

        public int hashCode() {
            return (this.forwardingPlayer.hashCode() * 31) + this.eventListener.hashCode();
        }
    }

    private static final class ForwardingListener extends ForwardingEventListener implements Player.Listener {
        private final Player.Listener listener;

        public ForwardingListener(ForwardingPlayer forwardingPlayer, Player.Listener listener) {
            super(listener);
            this.listener = listener;
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.video.VideoListener
        public void onVideoSizeChanged(VideoSize videoSize) {
            this.listener.onVideoSizeChanged(videoSize);
        }

        @Override // com.google.android.exoplayer2.video.VideoListener
        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
            this.listener.onVideoSizeChanged(width, height, unappliedRotationDegrees, pixelWidthHeightRatio);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.video.VideoListener
        public void onSurfaceSizeChanged(int width, int height) {
            this.listener.onSurfaceSizeChanged(width, height);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.video.VideoListener
        public void onRenderedFirstFrame() {
            this.listener.onRenderedFirstFrame();
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
        public void onAudioSessionIdChanged(int audioSessionId) {
            this.listener.onAudioSessionIdChanged(audioSessionId);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
        public void onAudioAttributesChanged(AudioAttributes audioAttributes) {
            this.listener.onAudioAttributesChanged(audioAttributes);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
        public void onVolumeChanged(float volume) {
            this.listener.onVolumeChanged(volume);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
        public void onSkipSilenceEnabledChanged(boolean skipSilenceEnabled) {
            this.listener.onSkipSilenceEnabledChanged(skipSilenceEnabled);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.text.TextOutput
        public void onCues(List<Cue> cues) {
            this.listener.onCues(cues);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.metadata.MetadataOutput
        public void onMetadata(Metadata metadata) {
            this.listener.onMetadata(metadata);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.device.DeviceListener
        public void onDeviceInfoChanged(DeviceInfo deviceInfo) {
            this.listener.onDeviceInfoChanged(deviceInfo);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.device.DeviceListener
        public void onDeviceVolumeChanged(int volume, boolean muted) {
            this.listener.onDeviceVolumeChanged(volume, muted);
        }
    }
}
