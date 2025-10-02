package com.google.android.exoplayer2;

import android.os.Bundle;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.device.DeviceListener;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.FlagSet;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.base.Objects;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface Player {
    public static final int COMMAND_ADJUST_DEVICE_VOLUME = 25;
    public static final int COMMAND_CHANGE_MEDIA_ITEMS = 19;
    public static final int COMMAND_GET_AUDIO_ATTRIBUTES = 20;
    public static final int COMMAND_GET_CURRENT_MEDIA_ITEM = 15;
    public static final int COMMAND_GET_DEVICE_VOLUME = 22;
    public static final int COMMAND_GET_MEDIA_ITEMS_METADATA = 17;
    public static final int COMMAND_GET_TEXT = 27;
    public static final int COMMAND_GET_TIMELINE = 16;
    public static final int COMMAND_GET_VOLUME = 21;
    public static final int COMMAND_INVALID = -1;
    public static final int COMMAND_PLAY_PAUSE = 1;
    public static final int COMMAND_PREPARE_STOP = 2;
    public static final int COMMAND_SEEK_BACK = 10;
    public static final int COMMAND_SEEK_FORWARD = 11;
    public static final int COMMAND_SEEK_IN_CURRENT_WINDOW = 4;
    public static final int COMMAND_SEEK_TO_DEFAULT_POSITION = 3;
    public static final int COMMAND_SEEK_TO_NEXT = 8;
    public static final int COMMAND_SEEK_TO_NEXT_WINDOW = 7;
    public static final int COMMAND_SEEK_TO_PREVIOUS = 6;
    public static final int COMMAND_SEEK_TO_PREVIOUS_WINDOW = 5;
    public static final int COMMAND_SEEK_TO_WINDOW = 9;
    public static final int COMMAND_SET_DEVICE_VOLUME = 24;
    public static final int COMMAND_SET_MEDIA_ITEMS_METADATA = 18;
    public static final int COMMAND_SET_REPEAT_MODE = 14;
    public static final int COMMAND_SET_SHUFFLE_MODE = 13;
    public static final int COMMAND_SET_SPEED_AND_PITCH = 12;
    public static final int COMMAND_SET_VIDEO_SURFACE = 26;
    public static final int COMMAND_SET_VOLUME = 23;
    public static final int DISCONTINUITY_REASON_AUTO_TRANSITION = 0;
    public static final int DISCONTINUITY_REASON_INTERNAL = 5;
    public static final int DISCONTINUITY_REASON_REMOVE = 4;
    public static final int DISCONTINUITY_REASON_SEEK = 1;
    public static final int DISCONTINUITY_REASON_SEEK_ADJUSTMENT = 2;
    public static final int DISCONTINUITY_REASON_SKIP = 3;
    public static final int EVENT_AVAILABLE_COMMANDS_CHANGED = 14;
    public static final int EVENT_IS_LOADING_CHANGED = 4;
    public static final int EVENT_IS_PLAYING_CHANGED = 8;
    public static final int EVENT_MAX_SEEK_TO_PREVIOUS_POSITION_CHANGED = 19;
    public static final int EVENT_MEDIA_ITEM_TRANSITION = 1;
    public static final int EVENT_MEDIA_METADATA_CHANGED = 15;
    public static final int EVENT_PLAYBACK_PARAMETERS_CHANGED = 13;
    public static final int EVENT_PLAYBACK_STATE_CHANGED = 5;
    public static final int EVENT_PLAYBACK_SUPPRESSION_REASON_CHANGED = 7;
    public static final int EVENT_PLAYER_ERROR = 11;
    public static final int EVENT_PLAYLIST_METADATA_CHANGED = 16;
    public static final int EVENT_PLAY_WHEN_READY_CHANGED = 6;
    public static final int EVENT_POSITION_DISCONTINUITY = 12;
    public static final int EVENT_REPEAT_MODE_CHANGED = 9;
    public static final int EVENT_SEEK_BACK_INCREMENT_CHANGED = 17;
    public static final int EVENT_SEEK_FORWARD_INCREMENT_CHANGED = 18;
    public static final int EVENT_SHUFFLE_MODE_ENABLED_CHANGED = 10;

    @Deprecated
    public static final int EVENT_STATIC_METADATA_CHANGED = 3;
    public static final int EVENT_TIMELINE_CHANGED = 0;
    public static final int EVENT_TRACKS_CHANGED = 2;
    public static final int MEDIA_ITEM_TRANSITION_REASON_AUTO = 1;
    public static final int MEDIA_ITEM_TRANSITION_REASON_PLAYLIST_CHANGED = 3;
    public static final int MEDIA_ITEM_TRANSITION_REASON_REPEAT = 0;
    public static final int MEDIA_ITEM_TRANSITION_REASON_SEEK = 2;
    public static final int PLAYBACK_SUPPRESSION_REASON_NONE = 0;
    public static final int PLAYBACK_SUPPRESSION_REASON_TRANSIENT_AUDIO_FOCUS_LOSS = 1;
    public static final int PLAY_WHEN_READY_CHANGE_REASON_AUDIO_BECOMING_NOISY = 3;
    public static final int PLAY_WHEN_READY_CHANGE_REASON_AUDIO_FOCUS_LOSS = 2;
    public static final int PLAY_WHEN_READY_CHANGE_REASON_END_OF_MEDIA_ITEM = 5;
    public static final int PLAY_WHEN_READY_CHANGE_REASON_REMOTE = 4;
    public static final int PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST = 1;
    public static final int REPEAT_MODE_ALL = 2;
    public static final int REPEAT_MODE_OFF = 0;
    public static final int REPEAT_MODE_ONE = 1;
    public static final int STATE_BUFFERING = 2;
    public static final int STATE_ENDED = 4;
    public static final int STATE_IDLE = 1;
    public static final int STATE_READY = 3;
    public static final int TIMELINE_CHANGE_REASON_PLAYLIST_CHANGED = 0;
    public static final int TIMELINE_CHANGE_REASON_SOURCE_UPDATE = 1;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Command {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface DiscontinuityReason {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Event {
    }

    @Deprecated
    public interface EventListener {
        default void onAvailableCommandsChanged(Commands availableCommands) {
        }

        default void onEvents(Player player, Events events) {
        }

        default void onIsLoadingChanged(boolean isLoading) {
        }

        default void onIsPlayingChanged(boolean isPlaying) {
        }

        @Deprecated
        default void onLoadingChanged(boolean isLoading) {
        }

        default void onMaxSeekToPreviousPositionChanged(int maxSeekToPreviousPositionMs) {
        }

        default void onMediaItemTransition(MediaItem mediaItem, int reason) {
        }

        default void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
        }

        default void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
        }

        default void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        }

        default void onPlaybackStateChanged(int playbackState) {
        }

        default void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
        }

        default void onPlayerError(PlaybackException error) {
        }

        default void onPlayerErrorChanged(PlaybackException error) {
        }

        @Deprecated
        default void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        }

        default void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
        }

        @Deprecated
        default void onPositionDiscontinuity(int reason) {
        }

        default void onPositionDiscontinuity(PositionInfo oldPosition, PositionInfo newPosition, int reason) {
        }

        default void onRepeatModeChanged(int repeatMode) {
        }

        default void onSeekBackIncrementChanged(long seekBackIncrementMs) {
        }

        default void onSeekForwardIncrementChanged(long seekForwardIncrementMs) {
        }

        @Deprecated
        default void onSeekProcessed() {
        }

        default void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        }

        @Deprecated
        default void onStaticMetadataChanged(List<Metadata> metadataList) {
        }

        default void onTimelineChanged(Timeline timeline, int reason) {
        }

        default void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        }
    }

    public interface Listener extends VideoListener, AudioListener, TextOutput, MetadataOutput, DeviceListener, EventListener {
        default void onAudioAttributesChanged(AudioAttributes audioAttributes) {
        }

        default void onAudioSessionIdChanged(int audioSessionId) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onAvailableCommandsChanged(Commands availableCommands) {
        }

        default void onCues(List<Cue> cues) {
        }

        default void onDeviceInfoChanged(DeviceInfo deviceInfo) {
        }

        default void onDeviceVolumeChanged(int volume, boolean muted) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onEvents(Player player, Events events) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onIsLoadingChanged(boolean isLoading) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onIsPlayingChanged(boolean isPlaying) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onMediaItemTransition(MediaItem mediaItem, int reason) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
        }

        default void onMetadata(Metadata metadata) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPlaybackStateChanged(int playbackState) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPlayerError(PlaybackException error) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPlayerErrorChanged(PlaybackException error) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onPositionDiscontinuity(PositionInfo oldPosition, PositionInfo newPosition, int reason) {
        }

        @Override // com.google.android.exoplayer2.video.VideoListener
        default void onRenderedFirstFrame() {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onRepeatModeChanged(int repeatMode) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onSeekBackIncrementChanged(long seekBackIncrementMs) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onSeekForwardIncrementChanged(long seekForwardIncrementMs) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        }

        default void onSkipSilenceEnabledChanged(boolean skipSilenceEnabled) {
        }

        @Override // com.google.android.exoplayer2.video.VideoListener
        default void onSurfaceSizeChanged(int width, int height) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onTimelineChanged(Timeline timeline, int reason) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        default void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        }

        @Override // com.google.android.exoplayer2.video.VideoListener
        default void onVideoSizeChanged(VideoSize videoSize) {
        }

        default void onVolumeChanged(float volume) {
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaItemTransitionReason {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayWhenReadyChangeReason {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaybackSuppressionReason {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatMode {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface TimelineChangeReason {
    }

    @Deprecated
    void addListener(EventListener listener);

    void addListener(Listener listener);

    void addMediaItem(int index, MediaItem mediaItem);

    void addMediaItem(MediaItem mediaItem);

    void addMediaItems(int index, List<MediaItem> mediaItems);

    void addMediaItems(List<MediaItem> mediaItems);

    void clearMediaItems();

    void clearVideoSurface();

    void clearVideoSurface(Surface surface);

    void clearVideoSurfaceHolder(SurfaceHolder surfaceHolder);

    void clearVideoSurfaceView(SurfaceView surfaceView);

    void clearVideoTextureView(TextureView textureView);

    void decreaseDeviceVolume();

    Looper getApplicationLooper();

    AudioAttributes getAudioAttributes();

    Commands getAvailableCommands();

    int getBufferedPercentage();

    long getBufferedPosition();

    long getContentBufferedPosition();

    long getContentDuration();

    long getContentPosition();

    int getCurrentAdGroupIndex();

    int getCurrentAdIndexInAdGroup();

    List<Cue> getCurrentCues();

    long getCurrentLiveOffset();

    Object getCurrentManifest();

    MediaItem getCurrentMediaItem();

    int getCurrentPeriodIndex();

    long getCurrentPosition();

    @Deprecated
    List<Metadata> getCurrentStaticMetadata();

    Timeline getCurrentTimeline();

    TrackGroupArray getCurrentTrackGroups();

    TrackSelectionArray getCurrentTrackSelections();

    int getCurrentWindowIndex();

    DeviceInfo getDeviceInfo();

    int getDeviceVolume();

    long getDuration();

    int getMaxSeekToPreviousPosition();

    MediaItem getMediaItemAt(int index);

    int getMediaItemCount();

    MediaMetadata getMediaMetadata();

    int getNextWindowIndex();

    boolean getPlayWhenReady();

    PlaybackParameters getPlaybackParameters();

    int getPlaybackState();

    int getPlaybackSuppressionReason();

    PlaybackException getPlayerError();

    MediaMetadata getPlaylistMetadata();

    int getPreviousWindowIndex();

    int getRepeatMode();

    long getSeekBackIncrement();

    long getSeekForwardIncrement();

    boolean getShuffleModeEnabled();

    long getTotalBufferedDuration();

    VideoSize getVideoSize();

    float getVolume();

    @Deprecated
    boolean hasNext();

    boolean hasNextWindow();

    @Deprecated
    boolean hasPrevious();

    boolean hasPreviousWindow();

    void increaseDeviceVolume();

    boolean isCommandAvailable(int command);

    boolean isCurrentWindowDynamic();

    boolean isCurrentWindowLive();

    boolean isCurrentWindowSeekable();

    boolean isDeviceMuted();

    boolean isLoading();

    boolean isPlaying();

    boolean isPlayingAd();

    void moveMediaItem(int currentIndex, int newIndex);

    void moveMediaItems(int fromIndex, int toIndex, int newIndex);

    @Deprecated
    void next();

    void pause();

    void play();

    void prepare();

    @Deprecated
    void previous();

    void release();

    @Deprecated
    void removeListener(EventListener listener);

    void removeListener(Listener listener);

    void removeMediaItem(int index);

    void removeMediaItems(int fromIndex, int toIndex);

    void seekBack();

    void seekForward();

    void seekTo(int windowIndex, long positionMs);

    void seekTo(long positionMs);

    void seekToDefaultPosition();

    void seekToDefaultPosition(int windowIndex);

    void seekToNext();

    void seekToNextWindow();

    void seekToPrevious();

    void seekToPreviousWindow();

    void setDeviceMuted(boolean muted);

    void setDeviceVolume(int volume);

    void setMediaItem(MediaItem mediaItem);

    void setMediaItem(MediaItem mediaItem, long startPositionMs);

    void setMediaItem(MediaItem mediaItem, boolean resetPosition);

    void setMediaItems(List<MediaItem> mediaItems);

    void setMediaItems(List<MediaItem> mediaItems, int startWindowIndex, long startPositionMs);

    void setMediaItems(List<MediaItem> mediaItems, boolean resetPosition);

    void setPlayWhenReady(boolean playWhenReady);

    void setPlaybackParameters(PlaybackParameters playbackParameters);

    void setPlaybackSpeed(float speed);

    void setPlaylistMetadata(MediaMetadata mediaMetadata);

    void setRepeatMode(int repeatMode);

    void setShuffleModeEnabled(boolean shuffleModeEnabled);

    void setVideoSurface(Surface surface);

    void setVideoSurfaceHolder(SurfaceHolder surfaceHolder);

    void setVideoSurfaceView(SurfaceView surfaceView);

    void setVideoTextureView(TextureView textureView);

    void setVolume(float audioVolume);

    void stop();

    @Deprecated
    void stop(boolean reset);

    public static final class Events {
        private final FlagSet flags;

        public Events(FlagSet flags) {
            this.flags = flags;
        }

        public boolean contains(int event) {
            return this.flags.contains(event);
        }

        public boolean containsAny(int... events) {
            return this.flags.containsAny(events);
        }

        public int size() {
            return this.flags.size();
        }

        public int get(int index) {
            return this.flags.get(index);
        }

        public int hashCode() {
            return this.flags.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Events) {
                return this.flags.equals(((Events) obj).flags);
            }
            return false;
        }
    }

    public static final class PositionInfo implements Bundleable {
        public static final Bundleable.Creator<PositionInfo> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.Player$PositionInfo$$ExternalSyntheticLambda0
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return Player.PositionInfo.fromBundle(bundle);
            }
        };
        private static final int FIELD_AD_GROUP_INDEX = 4;
        private static final int FIELD_AD_INDEX_IN_AD_GROUP = 5;
        private static final int FIELD_CONTENT_POSITION_MS = 3;
        private static final int FIELD_PERIOD_INDEX = 1;
        private static final int FIELD_POSITION_MS = 2;
        private static final int FIELD_WINDOW_INDEX = 0;
        public final int adGroupIndex;
        public final int adIndexInAdGroup;
        public final long contentPositionMs;
        public final int periodIndex;
        public final Object periodUid;
        public final long positionMs;
        public final int windowIndex;
        public final Object windowUid;

        public PositionInfo(Object windowUid, int windowIndex, Object periodUid, int periodIndex, long positionMs, long contentPositionMs, int adGroupIndex, int adIndexInAdGroup) {
            this.windowUid = windowUid;
            this.windowIndex = windowIndex;
            this.periodUid = periodUid;
            this.periodIndex = periodIndex;
            this.positionMs = positionMs;
            this.contentPositionMs = contentPositionMs;
            this.adGroupIndex = adGroupIndex;
            this.adIndexInAdGroup = adIndexInAdGroup;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PositionInfo positionInfo = (PositionInfo) o;
            return this.windowIndex == positionInfo.windowIndex && this.periodIndex == positionInfo.periodIndex && this.positionMs == positionInfo.positionMs && this.contentPositionMs == positionInfo.contentPositionMs && this.adGroupIndex == positionInfo.adGroupIndex && this.adIndexInAdGroup == positionInfo.adIndexInAdGroup && Objects.equal(this.windowUid, positionInfo.windowUid) && Objects.equal(this.periodUid, positionInfo.periodUid);
        }

        public int hashCode() {
            return Objects.hashCode(this.windowUid, Integer.valueOf(this.windowIndex), this.periodUid, Integer.valueOf(this.periodIndex), Integer.valueOf(this.windowIndex), Long.valueOf(this.positionMs), Long.valueOf(this.contentPositionMs), Integer.valueOf(this.adGroupIndex), Integer.valueOf(this.adIndexInAdGroup));
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putInt(keyForField(0), this.windowIndex);
            bundle.putInt(keyForField(1), this.periodIndex);
            bundle.putLong(keyForField(2), this.positionMs);
            bundle.putLong(keyForField(3), this.contentPositionMs);
            bundle.putInt(keyForField(4), this.adGroupIndex);
            bundle.putInt(keyForField(5), this.adIndexInAdGroup);
            return bundle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PositionInfo fromBundle(Bundle bundle) {
            return new PositionInfo(null, bundle.getInt(keyForField(0), -1), null, bundle.getInt(keyForField(1), -1), bundle.getLong(keyForField(2), C.TIME_UNSET), bundle.getLong(keyForField(3), C.TIME_UNSET), bundle.getInt(keyForField(4), -1), bundle.getInt(keyForField(5), -1));
        }

        private static String keyForField(int field) {
            return Integer.toString(field, 36);
        }
    }

    public static final class Commands implements Bundleable {
        private static final int FIELD_COMMANDS = 0;
        private final FlagSet flags;
        public static final Commands EMPTY = new Builder().build();
        public static final Bundleable.Creator<Commands> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.Player$Commands$$ExternalSyntheticLambda0
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return Player.Commands.fromBundle(bundle);
            }
        };

        public static final class Builder {
            private static final int[] SUPPORTED_COMMANDS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};
            private final FlagSet.Builder flagsBuilder;

            public Builder() {
                this.flagsBuilder = new FlagSet.Builder();
            }

            private Builder(Commands commands) {
                FlagSet.Builder builder = new FlagSet.Builder();
                this.flagsBuilder = builder;
                builder.addAll(commands.flags);
            }

            public Builder add(int command) {
                this.flagsBuilder.add(command);
                return this;
            }

            public Builder addIf(int command, boolean condition) {
                this.flagsBuilder.addIf(command, condition);
                return this;
            }

            public Builder addAll(int... commands) {
                this.flagsBuilder.addAll(commands);
                return this;
            }

            public Builder addAll(Commands commands) {
                this.flagsBuilder.addAll(commands.flags);
                return this;
            }

            public Builder addAllCommands() {
                this.flagsBuilder.addAll(SUPPORTED_COMMANDS);
                return this;
            }

            public Builder remove(int command) {
                this.flagsBuilder.remove(command);
                return this;
            }

            public Builder removeIf(int command, boolean condition) {
                this.flagsBuilder.removeIf(command, condition);
                return this;
            }

            public Builder removeAll(int... commands) {
                this.flagsBuilder.removeAll(commands);
                return this;
            }

            public Commands build() {
                return new Commands(this.flagsBuilder.build());
            }
        }

        private Commands(FlagSet flags) {
            this.flags = flags;
        }

        public Builder buildUpon() {
            return new Builder();
        }

        public boolean contains(int command) {
            return this.flags.contains(command);
        }

        public int size() {
            return this.flags.size();
        }

        public int get(int index) {
            return this.flags.get(index);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Commands) {
                return this.flags.equals(((Commands) obj).flags);
            }
            return false;
        }

        public int hashCode() {
            return this.flags.hashCode();
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int i = 0; i < this.flags.size(); i++) {
                arrayList.add(Integer.valueOf(this.flags.get(i)));
            }
            bundle.putIntegerArrayList(keyForField(0), arrayList);
            return bundle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Commands fromBundle(Bundle bundle) {
            ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList(keyForField(0));
            if (integerArrayList == null) {
                return EMPTY;
            }
            Builder builder = new Builder();
            for (int i = 0; i < integerArrayList.size(); i++) {
                builder.add(integerArrayList.get(i).intValue());
            }
            return builder.build();
        }

        private static String keyForField(int field) {
            return Integer.toString(field, 36);
        }
    }
}
