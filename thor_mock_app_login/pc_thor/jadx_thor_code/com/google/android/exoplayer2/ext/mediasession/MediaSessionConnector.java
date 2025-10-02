package com.google.android.exoplayer2.ext.mediasession;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* loaded from: classes.dex */
public final class MediaSessionConnector {
    public static final long ALL_PLAYBACK_ACTIONS = 6554447;
    private static final int BASE_MEDIA_SESSION_FLAGS = 3;
    private static final long BASE_PLAYBACK_ACTIONS = 6554119;
    public static final long DEFAULT_PLAYBACK_ACTIONS = 2360143;
    private static final int EDITOR_MEDIA_SESSION_FLAGS = 7;
    public static final String EXTRAS_SPEED = "EXO_SPEED";
    private static final MediaMetadataCompat METADATA_EMPTY;
    private CaptionCallback captionCallback;
    private final ArrayList<CommandReceiver> commandReceivers;
    private final ComponentListener componentListener;
    private ControlDispatcher controlDispatcher;
    private Map<String, CustomActionProvider> customActionMap;
    private CustomActionProvider[] customActionProviders;
    private final ArrayList<CommandReceiver> customCommandReceivers;
    private Pair<Integer, CharSequence> customError;
    private Bundle customErrorExtras;
    private boolean dispatchUnsupportedActionsEnabled;
    private long enabledPlaybackActions;
    private ErrorMessageProvider<? super PlaybackException> errorMessageProvider;
    private final Looper looper;
    private MediaButtonEventHandler mediaButtonEventHandler;
    private MediaMetadataProvider mediaMetadataProvider;
    public final MediaSessionCompat mediaSession;
    private boolean metadataDeduplicationEnabled;
    private PlaybackPreparer playbackPreparer;
    private Player player;
    private QueueEditor queueEditor;
    private QueueNavigator queueNavigator;
    private RatingCallback ratingCallback;

    public interface CaptionCallback extends CommandReceiver {
        boolean hasCaptions(Player player);

        void onSetCaptioningEnabled(Player player, boolean enabled);
    }

    public interface CommandReceiver {
        boolean onCommand(Player player, @Deprecated ControlDispatcher controlDispatcher, String command, Bundle extras, ResultReceiver cb);
    }

    public interface CustomActionProvider {
        PlaybackStateCompat.CustomAction getCustomAction(Player player);

        void onCustomAction(Player player, @Deprecated ControlDispatcher controlDispatcher, String action, Bundle extras);
    }

    public interface MediaButtonEventHandler {
        boolean onMediaButtonEvent(Player player, @Deprecated ControlDispatcher controlDispatcher, Intent mediaButtonEvent);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaybackActions {
    }

    public interface PlaybackPreparer extends CommandReceiver {
        public static final long ACTIONS = 257024;

        long getSupportedPrepareActions();

        void onPrepare(boolean playWhenReady);

        void onPrepareFromMediaId(String mediaId, boolean playWhenReady, Bundle extras);

        void onPrepareFromSearch(String query, boolean playWhenReady, Bundle extras);

        void onPrepareFromUri(Uri uri, boolean playWhenReady, Bundle extras);
    }

    public interface QueueEditor extends CommandReceiver {
        void onAddQueueItem(Player player, MediaDescriptionCompat description);

        void onAddQueueItem(Player player, MediaDescriptionCompat description, int index);

        void onRemoveQueueItem(Player player, MediaDescriptionCompat description);
    }

    public interface QueueNavigator extends CommandReceiver {
        public static final long ACTIONS = 4144;

        long getActiveQueueItemId(Player player);

        long getSupportedQueueNavigatorActions(Player player);

        void onCurrentWindowIndexChanged(Player player);

        void onSkipToNext(Player player, @Deprecated ControlDispatcher controlDispatcher);

        void onSkipToPrevious(Player player, @Deprecated ControlDispatcher controlDispatcher);

        void onSkipToQueueItem(Player player, @Deprecated ControlDispatcher controlDispatcher, long id);

        void onTimelineChanged(Player player);
    }

    public interface RatingCallback extends CommandReceiver {
        void onSetRating(Player player, RatingCompat rating);

        void onSetRating(Player player, RatingCompat rating, Bundle extras);
    }

    private static int getMediaSessionPlaybackState(int exoPlayerPlaybackState, boolean playWhenReady) {
        return exoPlayerPlaybackState != 2 ? exoPlayerPlaybackState != 3 ? exoPlayerPlaybackState != 4 ? 0 : 1 : playWhenReady ? 3 : 2 : playWhenReady ? 6 : 2;
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.mediasession");
        METADATA_EMPTY = new MediaMetadataCompat.Builder().build();
    }

    public interface MediaMetadataProvider {
        MediaMetadataCompat getMetadata(Player player);

        default boolean sameAs(MediaMetadataCompat oldMetadata, MediaMetadataCompat newMetadata) {
            if (oldMetadata == newMetadata) {
                return true;
            }
            if (oldMetadata.size() != newMetadata.size()) {
                return false;
            }
            Set<String> setKeySet = oldMetadata.keySet();
            Bundle bundle = oldMetadata.getBundle();
            Bundle bundle2 = newMetadata.getBundle();
            for (String str : setKeySet) {
                Object obj = bundle.get(str);
                Object obj2 = bundle2.get(str);
                if (obj != obj2) {
                    if ((obj instanceof Bitmap) && (obj2 instanceof Bitmap)) {
                        if (!((Bitmap) obj).sameAs((Bitmap) obj2)) {
                            return false;
                        }
                    } else if ((obj instanceof RatingCompat) && (obj2 instanceof RatingCompat)) {
                        RatingCompat ratingCompat = (RatingCompat) obj;
                        RatingCompat ratingCompat2 = (RatingCompat) obj2;
                        if (ratingCompat.hasHeart() != ratingCompat2.hasHeart() || ratingCompat.isRated() != ratingCompat2.isRated() || ratingCompat.isThumbUp() != ratingCompat2.isThumbUp() || ratingCompat.getPercentRating() != ratingCompat2.getPercentRating() || ratingCompat.getStarRating() != ratingCompat2.getStarRating() || ratingCompat.getRatingStyle() != ratingCompat2.getRatingStyle()) {
                            return false;
                        }
                    } else if (!Util.areEqual(obj, obj2)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public MediaSessionConnector(MediaSessionCompat mediaSession) {
        this.mediaSession = mediaSession;
        Looper currentOrMainLooper = Util.getCurrentOrMainLooper();
        this.looper = currentOrMainLooper;
        ComponentListener componentListener = new ComponentListener();
        this.componentListener = componentListener;
        this.commandReceivers = new ArrayList<>();
        this.customCommandReceivers = new ArrayList<>();
        this.controlDispatcher = new DefaultControlDispatcher();
        this.customActionProviders = new CustomActionProvider[0];
        this.customActionMap = Collections.emptyMap();
        this.mediaMetadataProvider = new DefaultMediaMetadataProvider(mediaSession.getController(), null);
        this.enabledPlaybackActions = DEFAULT_PLAYBACK_ACTIONS;
        mediaSession.setFlags(3);
        mediaSession.setCallback(componentListener, new Handler(currentOrMainLooper));
    }

    public void setPlayer(Player player) {
        Assertions.checkArgument(player == null || player.getApplicationLooper() == this.looper);
        Player player2 = this.player;
        if (player2 != null) {
            player2.removeListener((Player.Listener) this.componentListener);
        }
        this.player = player;
        if (player != null) {
            player.addListener((Player.Listener) this.componentListener);
        }
        invalidateMediaSessionPlaybackState();
        invalidateMediaSessionMetadata();
    }

    public void setPlaybackPreparer(PlaybackPreparer playbackPreparer) {
        PlaybackPreparer playbackPreparer2 = this.playbackPreparer;
        if (playbackPreparer2 != playbackPreparer) {
            unregisterCommandReceiver(playbackPreparer2);
            this.playbackPreparer = playbackPreparer;
            registerCommandReceiver(playbackPreparer);
            invalidateMediaSessionPlaybackState();
        }
    }

    @Deprecated
    public void setControlDispatcher(ControlDispatcher controlDispatcher) {
        if (this.controlDispatcher != controlDispatcher) {
            this.controlDispatcher = controlDispatcher;
            invalidateMediaSessionPlaybackState();
        }
    }

    public void setMediaButtonEventHandler(MediaButtonEventHandler mediaButtonEventHandler) {
        this.mediaButtonEventHandler = mediaButtonEventHandler;
    }

    public void setEnabledPlaybackActions(long enabledPlaybackActions) {
        long j = enabledPlaybackActions & ALL_PLAYBACK_ACTIONS;
        if (this.enabledPlaybackActions != j) {
            this.enabledPlaybackActions = j;
            invalidateMediaSessionPlaybackState();
        }
    }

    public void setErrorMessageProvider(ErrorMessageProvider<? super PlaybackException> errorMessageProvider) {
        if (this.errorMessageProvider != errorMessageProvider) {
            this.errorMessageProvider = errorMessageProvider;
            invalidateMediaSessionPlaybackState();
        }
    }

    public void setQueueNavigator(QueueNavigator queueNavigator) {
        QueueNavigator queueNavigator2 = this.queueNavigator;
        if (queueNavigator2 != queueNavigator) {
            unregisterCommandReceiver(queueNavigator2);
            this.queueNavigator = queueNavigator;
            registerCommandReceiver(queueNavigator);
        }
    }

    public void setQueueEditor(QueueEditor queueEditor) {
        QueueEditor queueEditor2 = this.queueEditor;
        if (queueEditor2 != queueEditor) {
            unregisterCommandReceiver(queueEditor2);
            this.queueEditor = queueEditor;
            registerCommandReceiver(queueEditor);
            this.mediaSession.setFlags(queueEditor == null ? 3 : 7);
        }
    }

    public void setRatingCallback(RatingCallback ratingCallback) {
        RatingCallback ratingCallback2 = this.ratingCallback;
        if (ratingCallback2 != ratingCallback) {
            unregisterCommandReceiver(ratingCallback2);
            this.ratingCallback = ratingCallback;
            registerCommandReceiver(ratingCallback);
        }
    }

    public void setCaptionCallback(CaptionCallback captionCallback) {
        CaptionCallback captionCallback2 = this.captionCallback;
        if (captionCallback2 != captionCallback) {
            unregisterCommandReceiver(captionCallback2);
            this.captionCallback = captionCallback;
            registerCommandReceiver(captionCallback);
        }
    }

    public void setCustomErrorMessage(CharSequence message) {
        setCustomErrorMessage(message, message == null ? 0 : 1);
    }

    public void setCustomErrorMessage(CharSequence message, int code) {
        setCustomErrorMessage(message, code, null);
    }

    public void setCustomErrorMessage(CharSequence message, int code, Bundle extras) {
        this.customError = message == null ? null : new Pair<>(Integer.valueOf(code), message);
        if (message == null) {
            extras = null;
        }
        this.customErrorExtras = extras;
        invalidateMediaSessionPlaybackState();
    }

    public void setCustomActionProviders(CustomActionProvider... customActionProviders) {
        if (customActionProviders == null) {
            customActionProviders = new CustomActionProvider[0];
        }
        this.customActionProviders = customActionProviders;
        invalidateMediaSessionPlaybackState();
    }

    public void setMediaMetadataProvider(MediaMetadataProvider mediaMetadataProvider) {
        if (this.mediaMetadataProvider != mediaMetadataProvider) {
            this.mediaMetadataProvider = mediaMetadataProvider;
            invalidateMediaSessionMetadata();
        }
    }

    public void setDispatchUnsupportedActionsEnabled(boolean dispatchUnsupportedActionsEnabled) {
        this.dispatchUnsupportedActionsEnabled = dispatchUnsupportedActionsEnabled;
    }

    public void setMetadataDeduplicationEnabled(boolean metadataDeduplicationEnabled) {
        this.metadataDeduplicationEnabled = metadataDeduplicationEnabled;
    }

    public final void invalidateMediaSessionMetadata() {
        MediaMetadataCompat metadata;
        MediaMetadataCompat metadata2;
        Player player;
        MediaMetadataProvider mediaMetadataProvider = this.mediaMetadataProvider;
        if (mediaMetadataProvider != null && (player = this.player) != null) {
            metadata = mediaMetadataProvider.getMetadata(player);
        } else {
            metadata = METADATA_EMPTY;
        }
        MediaMetadataProvider mediaMetadataProvider2 = this.mediaMetadataProvider;
        if (!this.metadataDeduplicationEnabled || mediaMetadataProvider2 == null || (metadata2 = this.mediaSession.getController().getMetadata()) == null || !mediaMetadataProvider2.sameAs(metadata2, metadata)) {
            this.mediaSession.setMetadata(metadata);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0118 A[PHI: r5
      0x0118: PHI (r5v3 int) = (r5v1 int), (r5v2 int) binds: [B:45:0x0116, B:48:0x011b] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void invalidateMediaSessionPlaybackState() {
        /*
            Method dump skipped, instructions count: 308
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.invalidateMediaSessionPlaybackState():void");
    }

    public final void invalidateMediaSessionQueue() {
        Player player;
        QueueNavigator queueNavigator = this.queueNavigator;
        if (queueNavigator == null || (player = this.player) == null) {
            return;
        }
        queueNavigator.onTimelineChanged(player);
    }

    public void registerCustomCommandReceiver(CommandReceiver commandReceiver) {
        if (commandReceiver == null || this.customCommandReceivers.contains(commandReceiver)) {
            return;
        }
        this.customCommandReceivers.add(commandReceiver);
    }

    public void unregisterCustomCommandReceiver(CommandReceiver commandReceiver) {
        if (commandReceiver != null) {
            this.customCommandReceivers.remove(commandReceiver);
        }
    }

    private void registerCommandReceiver(CommandReceiver commandReceiver) {
        if (commandReceiver == null || this.commandReceivers.contains(commandReceiver)) {
            return;
        }
        this.commandReceivers.add(commandReceiver);
    }

    private void unregisterCommandReceiver(CommandReceiver commandReceiver) {
        if (commandReceiver != null) {
            this.commandReceivers.remove(commandReceiver);
        }
    }

    private long buildPrepareActions() {
        PlaybackPreparer playbackPreparer = this.playbackPreparer;
        if (playbackPreparer == null) {
            return 0L;
        }
        return playbackPreparer.getSupportedPrepareActions() & PlaybackPreparer.ACTIONS;
    }

    private long buildPlaybackActions(Player player) {
        boolean z;
        boolean zIsCommandAvailable = player.isCommandAvailable(4);
        boolean z2 = false;
        boolean z3 = player.isCommandAvailable(10) && this.controlDispatcher.isRewindEnabled();
        boolean z4 = player.isCommandAvailable(11) && this.controlDispatcher.isFastForwardEnabled();
        if (player.getCurrentTimeline().isEmpty() || player.isPlayingAd()) {
            z = false;
        } else {
            boolean z5 = this.ratingCallback != null;
            CaptionCallback captionCallback = this.captionCallback;
            z = captionCallback != null && captionCallback.hasCaptions(player);
            z2 = z5;
        }
        long j = zIsCommandAvailable ? 6554375L : BASE_PLAYBACK_ACTIONS;
        if (z4) {
            j |= 64;
        }
        if (z3) {
            j |= 8;
        }
        long supportedQueueNavigatorActions = this.enabledPlaybackActions & j;
        QueueNavigator queueNavigator = this.queueNavigator;
        if (queueNavigator != null) {
            supportedQueueNavigatorActions |= queueNavigator.getSupportedQueueNavigatorActions(player) & QueueNavigator.ACTIONS;
        }
        if (z2) {
            supportedQueueNavigatorActions |= 128;
        }
        return z ? supportedQueueNavigatorActions | PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED : supportedQueueNavigatorActions;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @EnsuresNonNullIf(expression = {"player"}, result = true)
    public boolean canDispatchPlaybackAction(long action) {
        return this.player != null && ((action & this.enabledPlaybackActions) != 0 || this.dispatchUnsupportedActionsEnabled);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @EnsuresNonNullIf(expression = {"playbackPreparer"}, result = true)
    public boolean canDispatchToPlaybackPreparer(long action) {
        PlaybackPreparer playbackPreparer = this.playbackPreparer;
        return playbackPreparer != null && ((action & playbackPreparer.getSupportedPrepareActions()) != 0 || this.dispatchUnsupportedActionsEnabled);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @EnsuresNonNullIf(expression = {"player", "queueNavigator"}, result = true)
    public boolean canDispatchToQueueNavigator(long action) {
        QueueNavigator queueNavigator;
        Player player = this.player;
        return (player == null || (queueNavigator = this.queueNavigator) == null || ((action & queueNavigator.getSupportedQueueNavigatorActions(player)) == 0 && !this.dispatchUnsupportedActionsEnabled)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @EnsuresNonNullIf(expression = {"player", "ratingCallback"}, result = true)
    public boolean canDispatchSetRating() {
        return (this.player == null || this.ratingCallback == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @EnsuresNonNullIf(expression = {"player", "captionCallback"}, result = true)
    public boolean canDispatchSetCaptioningEnabled() {
        return (this.player == null || this.captionCallback == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @EnsuresNonNullIf(expression = {"player", "queueEditor"}, result = true)
    public boolean canDispatchQueueEdit() {
        return (this.player == null || this.queueEditor == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @EnsuresNonNullIf(expression = {"player", "mediaButtonEventHandler"}, result = true)
    public boolean canDispatchMediaButtonEvent() {
        return (this.player == null || this.mediaButtonEventHandler == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void seekTo(Player player, int windowIndex, long positionMs) {
        this.controlDispatcher.dispatchSeekTo(player, windowIndex, positionMs);
    }

    public static final class DefaultMediaMetadataProvider implements MediaMetadataProvider {
        private final MediaControllerCompat mediaController;
        private final String metadataExtrasPrefix;

        public DefaultMediaMetadataProvider(MediaControllerCompat mediaController, String metadataExtrasPrefix) {
            this.mediaController = mediaController;
            this.metadataExtrasPrefix = metadataExtrasPrefix == null ? "" : metadataExtrasPrefix;
        }

        @Override // com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector.MediaMetadataProvider
        public MediaMetadataCompat getMetadata(Player player) {
            if (player.getCurrentTimeline().isEmpty()) {
                return MediaSessionConnector.METADATA_EMPTY;
            }
            MediaMetadataCompat.Builder builder = new MediaMetadataCompat.Builder();
            if (player.isPlayingAd()) {
                builder.putLong("android.media.metadata.ADVERTISEMENT", 1L);
            }
            builder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, (player.isCurrentWindowDynamic() || player.getDuration() == C.TIME_UNSET) ? -1L : player.getDuration());
            long activeQueueItemId = this.mediaController.getPlaybackState().getActiveQueueItemId();
            if (activeQueueItemId != -1) {
                List<MediaSessionCompat.QueueItem> queue = this.mediaController.getQueue();
                int i = 0;
                while (true) {
                    if (queue == null || i >= queue.size()) {
                        break;
                    }
                    MediaSessionCompat.QueueItem queueItem = queue.get(i);
                    if (queueItem.getQueueId() == activeQueueItemId) {
                        MediaDescriptionCompat description = queueItem.getDescription();
                        Bundle extras = description.getExtras();
                        if (extras != null) {
                            for (String str : extras.keySet()) {
                                Object obj = extras.get(str);
                                if (obj instanceof String) {
                                    String strValueOf = String.valueOf(this.metadataExtrasPrefix);
                                    String strValueOf2 = String.valueOf(str);
                                    builder.putString(strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf), (String) obj);
                                } else if (obj instanceof CharSequence) {
                                    String strValueOf3 = String.valueOf(this.metadataExtrasPrefix);
                                    String strValueOf4 = String.valueOf(str);
                                    builder.putText(strValueOf4.length() != 0 ? strValueOf3.concat(strValueOf4) : new String(strValueOf3), (CharSequence) obj);
                                } else if (obj instanceof Long) {
                                    String strValueOf5 = String.valueOf(this.metadataExtrasPrefix);
                                    String strValueOf6 = String.valueOf(str);
                                    builder.putLong(strValueOf6.length() != 0 ? strValueOf5.concat(strValueOf6) : new String(strValueOf5), ((Long) obj).longValue());
                                } else if (obj instanceof Integer) {
                                    String strValueOf7 = String.valueOf(this.metadataExtrasPrefix);
                                    String strValueOf8 = String.valueOf(str);
                                    builder.putLong(strValueOf8.length() != 0 ? strValueOf7.concat(strValueOf8) : new String(strValueOf7), ((Integer) obj).intValue());
                                } else if (obj instanceof Bitmap) {
                                    String strValueOf9 = String.valueOf(this.metadataExtrasPrefix);
                                    String strValueOf10 = String.valueOf(str);
                                    builder.putBitmap(strValueOf10.length() != 0 ? strValueOf9.concat(strValueOf10) : new String(strValueOf9), (Bitmap) obj);
                                } else if (obj instanceof RatingCompat) {
                                    String strValueOf11 = String.valueOf(this.metadataExtrasPrefix);
                                    String strValueOf12 = String.valueOf(str);
                                    builder.putRating(strValueOf12.length() != 0 ? strValueOf11.concat(strValueOf12) : new String(strValueOf11), (RatingCompat) obj);
                                }
                            }
                        }
                        CharSequence title = description.getTitle();
                        if (title != null) {
                            String strValueOf13 = String.valueOf(title);
                            builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, strValueOf13);
                            builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, strValueOf13);
                        }
                        CharSequence subtitle = description.getSubtitle();
                        if (subtitle != null) {
                            builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, String.valueOf(subtitle));
                        }
                        CharSequence description2 = description.getDescription();
                        if (description2 != null) {
                            builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION, String.valueOf(description2));
                        }
                        Bitmap iconBitmap = description.getIconBitmap();
                        if (iconBitmap != null) {
                            builder.putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, iconBitmap);
                        }
                        Uri iconUri = description.getIconUri();
                        if (iconUri != null) {
                            builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, String.valueOf(iconUri));
                        }
                        String mediaId = description.getMediaId();
                        if (mediaId != null) {
                            builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, mediaId);
                        }
                        Uri mediaUri = description.getMediaUri();
                        if (mediaUri != null) {
                            builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, String.valueOf(mediaUri));
                        }
                    } else {
                        i++;
                    }
                }
            }
            return builder.build();
        }
    }

    private class ComponentListener extends MediaSessionCompat.Callback implements Player.Listener {
        private int currentWindowCount;
        private int currentWindowIndex;

        private ComponentListener() {
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onEvents(Player player, Player.Events events) {
            boolean z;
            boolean z2;
            boolean z3 = true;
            if (events.contains(12)) {
                if (this.currentWindowIndex != player.getCurrentWindowIndex()) {
                    if (MediaSessionConnector.this.queueNavigator != null) {
                        MediaSessionConnector.this.queueNavigator.onCurrentWindowIndexChanged(player);
                    }
                    z = true;
                } else {
                    z = false;
                }
                z2 = true;
            } else {
                z = false;
                z2 = false;
            }
            if (events.contains(0)) {
                int windowCount = player.getCurrentTimeline().getWindowCount();
                int currentWindowIndex = player.getCurrentWindowIndex();
                if (MediaSessionConnector.this.queueNavigator != null) {
                    MediaSessionConnector.this.queueNavigator.onTimelineChanged(player);
                } else {
                    if (this.currentWindowCount != windowCount || this.currentWindowIndex != currentWindowIndex) {
                    }
                    this.currentWindowCount = windowCount;
                    z = true;
                }
                z2 = true;
                this.currentWindowCount = windowCount;
                z = true;
            }
            this.currentWindowIndex = player.getCurrentWindowIndex();
            if (events.containsAny(5, 6, 8, 9, 13)) {
                z2 = true;
            }
            if (events.containsAny(10)) {
                MediaSessionConnector.this.invalidateMediaSessionQueue();
            } else {
                z3 = z2;
            }
            if (z3) {
                MediaSessionConnector.this.invalidateMediaSessionPlaybackState();
            }
            if (z) {
                MediaSessionConnector.this.invalidateMediaSessionMetadata();
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onPlay() {
            if (MediaSessionConnector.this.canDispatchPlaybackAction(4L)) {
                if (MediaSessionConnector.this.player.getPlaybackState() == 1) {
                    if (MediaSessionConnector.this.playbackPreparer != null) {
                        MediaSessionConnector.this.playbackPreparer.onPrepare(true);
                    } else {
                        MediaSessionConnector.this.controlDispatcher.dispatchPrepare(MediaSessionConnector.this.player);
                    }
                } else if (MediaSessionConnector.this.player.getPlaybackState() == 4) {
                    MediaSessionConnector mediaSessionConnector = MediaSessionConnector.this;
                    mediaSessionConnector.seekTo(mediaSessionConnector.player, MediaSessionConnector.this.player.getCurrentWindowIndex(), C.TIME_UNSET);
                }
                MediaSessionConnector.this.controlDispatcher.dispatchSetPlayWhenReady((Player) Assertions.checkNotNull(MediaSessionConnector.this.player), true);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onPause() {
            if (MediaSessionConnector.this.canDispatchPlaybackAction(2L)) {
                MediaSessionConnector.this.controlDispatcher.dispatchSetPlayWhenReady(MediaSessionConnector.this.player, false);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onSeekTo(long positionMs) {
            if (MediaSessionConnector.this.canDispatchPlaybackAction(256L)) {
                MediaSessionConnector mediaSessionConnector = MediaSessionConnector.this;
                mediaSessionConnector.seekTo(mediaSessionConnector.player, MediaSessionConnector.this.player.getCurrentWindowIndex(), positionMs);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onFastForward() {
            if (MediaSessionConnector.this.canDispatchPlaybackAction(64L)) {
                MediaSessionConnector.this.controlDispatcher.dispatchFastForward(MediaSessionConnector.this.player);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onRewind() {
            if (MediaSessionConnector.this.canDispatchPlaybackAction(8L)) {
                MediaSessionConnector.this.controlDispatcher.dispatchRewind(MediaSessionConnector.this.player);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onStop() {
            if (MediaSessionConnector.this.canDispatchPlaybackAction(1L)) {
                MediaSessionConnector.this.controlDispatcher.dispatchStop(MediaSessionConnector.this.player, true);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onSetShuffleMode(int shuffleMode) {
            if (MediaSessionConnector.this.canDispatchPlaybackAction(PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE)) {
                boolean z = true;
                if (shuffleMode != 1 && shuffleMode != 2) {
                    z = false;
                }
                MediaSessionConnector.this.controlDispatcher.dispatchSetShuffleModeEnabled(MediaSessionConnector.this.player, z);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onSetRepeatMode(int mediaSessionRepeatMode) {
            if (MediaSessionConnector.this.canDispatchPlaybackAction(PlaybackStateCompat.ACTION_SET_REPEAT_MODE)) {
                int i = 1;
                if (mediaSessionRepeatMode != 1) {
                    i = 2;
                    if (mediaSessionRepeatMode != 2 && mediaSessionRepeatMode != 3) {
                        i = 0;
                    }
                }
                MediaSessionConnector.this.controlDispatcher.dispatchSetRepeatMode(MediaSessionConnector.this.player, i);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onSetPlaybackSpeed(float speed) {
            if (!MediaSessionConnector.this.canDispatchPlaybackAction(PlaybackStateCompat.ACTION_SET_PLAYBACK_SPEED) || speed <= 0.0f) {
                return;
            }
            MediaSessionConnector.this.controlDispatcher.dispatchSetPlaybackParameters(MediaSessionConnector.this.player, MediaSessionConnector.this.player.getPlaybackParameters().withSpeed(speed));
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onSkipToNext() {
            if (MediaSessionConnector.this.canDispatchToQueueNavigator(32L)) {
                MediaSessionConnector.this.queueNavigator.onSkipToNext(MediaSessionConnector.this.player, MediaSessionConnector.this.controlDispatcher);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onSkipToPrevious() {
            if (MediaSessionConnector.this.canDispatchToQueueNavigator(16L)) {
                MediaSessionConnector.this.queueNavigator.onSkipToPrevious(MediaSessionConnector.this.player, MediaSessionConnector.this.controlDispatcher);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onSkipToQueueItem(long id) {
            if (MediaSessionConnector.this.canDispatchToQueueNavigator(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM)) {
                MediaSessionConnector.this.queueNavigator.onSkipToQueueItem(MediaSessionConnector.this.player, MediaSessionConnector.this.controlDispatcher, id);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onCustomAction(String action, Bundle extras) {
            if (MediaSessionConnector.this.player == null || !MediaSessionConnector.this.customActionMap.containsKey(action)) {
                return;
            }
            ((CustomActionProvider) MediaSessionConnector.this.customActionMap.get(action)).onCustomAction(MediaSessionConnector.this.player, MediaSessionConnector.this.controlDispatcher, action, extras);
            MediaSessionConnector.this.invalidateMediaSessionPlaybackState();
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onCommand(String command, Bundle extras, ResultReceiver cb) {
            if (MediaSessionConnector.this.player != null) {
                for (int i = 0; i < MediaSessionConnector.this.commandReceivers.size(); i++) {
                    if (((CommandReceiver) MediaSessionConnector.this.commandReceivers.get(i)).onCommand(MediaSessionConnector.this.player, MediaSessionConnector.this.controlDispatcher, command, extras, cb)) {
                        return;
                    }
                }
                for (int i2 = 0; i2 < MediaSessionConnector.this.customCommandReceivers.size() && !((CommandReceiver) MediaSessionConnector.this.customCommandReceivers.get(i2)).onCommand(MediaSessionConnector.this.player, MediaSessionConnector.this.controlDispatcher, command, extras, cb); i2++) {
                }
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onPrepare() {
            if (MediaSessionConnector.this.canDispatchToPlaybackPreparer(16384L)) {
                MediaSessionConnector.this.playbackPreparer.onPrepare(false);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onPrepareFromMediaId(String mediaId, Bundle extras) {
            if (MediaSessionConnector.this.canDispatchToPlaybackPreparer(PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID)) {
                MediaSessionConnector.this.playbackPreparer.onPrepareFromMediaId(mediaId, false, extras);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onPrepareFromSearch(String query, Bundle extras) {
            if (MediaSessionConnector.this.canDispatchToPlaybackPreparer(PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH)) {
                MediaSessionConnector.this.playbackPreparer.onPrepareFromSearch(query, false, extras);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onPrepareFromUri(Uri uri, Bundle extras) {
            if (MediaSessionConnector.this.canDispatchToPlaybackPreparer(PlaybackStateCompat.ACTION_PREPARE_FROM_URI)) {
                MediaSessionConnector.this.playbackPreparer.onPrepareFromUri(uri, false, extras);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            if (MediaSessionConnector.this.canDispatchToPlaybackPreparer(1024L)) {
                MediaSessionConnector.this.playbackPreparer.onPrepareFromMediaId(mediaId, true, extras);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onPlayFromSearch(String query, Bundle extras) {
            if (MediaSessionConnector.this.canDispatchToPlaybackPreparer(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH)) {
                MediaSessionConnector.this.playbackPreparer.onPrepareFromSearch(query, true, extras);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onPlayFromUri(Uri uri, Bundle extras) {
            if (MediaSessionConnector.this.canDispatchToPlaybackPreparer(PlaybackStateCompat.ACTION_PLAY_FROM_URI)) {
                MediaSessionConnector.this.playbackPreparer.onPrepareFromUri(uri, true, extras);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onSetRating(RatingCompat rating) {
            if (MediaSessionConnector.this.canDispatchSetRating()) {
                MediaSessionConnector.this.ratingCallback.onSetRating(MediaSessionConnector.this.player, rating);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onSetRating(RatingCompat rating, Bundle extras) {
            if (MediaSessionConnector.this.canDispatchSetRating()) {
                MediaSessionConnector.this.ratingCallback.onSetRating(MediaSessionConnector.this.player, rating, extras);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onAddQueueItem(MediaDescriptionCompat description) {
            if (MediaSessionConnector.this.canDispatchQueueEdit()) {
                MediaSessionConnector.this.queueEditor.onAddQueueItem(MediaSessionConnector.this.player, description);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onAddQueueItem(MediaDescriptionCompat description, int index) {
            if (MediaSessionConnector.this.canDispatchQueueEdit()) {
                MediaSessionConnector.this.queueEditor.onAddQueueItem(MediaSessionConnector.this.player, description, index);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onRemoveQueueItem(MediaDescriptionCompat description) {
            if (MediaSessionConnector.this.canDispatchQueueEdit()) {
                MediaSessionConnector.this.queueEditor.onRemoveQueueItem(MediaSessionConnector.this.player, description);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public void onSetCaptioningEnabled(boolean enabled) {
            if (MediaSessionConnector.this.canDispatchSetCaptioningEnabled()) {
                MediaSessionConnector.this.captionCallback.onSetCaptioningEnabled(MediaSessionConnector.this.player, enabled);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.Callback
        public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
            return (MediaSessionConnector.this.canDispatchMediaButtonEvent() && MediaSessionConnector.this.mediaButtonEventHandler.onMediaButtonEvent(MediaSessionConnector.this.player, MediaSessionConnector.this.controlDispatcher, mediaButtonEvent)) || super.onMediaButtonEvent(mediaButtonEvent);
        }
    }
}
