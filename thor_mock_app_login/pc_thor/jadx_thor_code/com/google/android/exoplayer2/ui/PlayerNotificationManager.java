package com.google.android.exoplayer2.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.session.MediaSessionCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.media.app.NotificationCompat;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import com.thor.businessmodule.bluetooth.util.BleCommands;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class PlayerNotificationManager {
    private static final String ACTION_DISMISS = "com.google.android.exoplayer.dismiss";
    public static final String ACTION_FAST_FORWARD = "com.google.android.exoplayer.ffwd";
    public static final String ACTION_NEXT = "com.google.android.exoplayer.next";
    public static final String ACTION_PAUSE = "com.google.android.exoplayer.pause";
    public static final String ACTION_PLAY = "com.google.android.exoplayer.play";
    public static final String ACTION_PREVIOUS = "com.google.android.exoplayer.prev";
    public static final String ACTION_REWIND = "com.google.android.exoplayer.rewind";
    public static final String ACTION_STOP = "com.google.android.exoplayer.stop";
    public static final String EXTRA_INSTANCE_ID = "INSTANCE_ID";
    private static final int MSG_START_OR_UPDATE_NOTIFICATION = 0;
    private static final int MSG_UPDATE_NOTIFICATION_BITMAP = 1;
    private static int instanceIdCounter;
    private int badgeIconType;
    private NotificationCompat.Builder builder;
    private List<NotificationCompat.Action> builderActions;
    private final String channelId;
    private int color;
    private boolean colorized;
    private final Context context;
    private ControlDispatcher controlDispatcher;
    private int currentNotificationTag;
    private final CustomActionReceiver customActionReceiver;
    private final Map<String, NotificationCompat.Action> customActions;
    private int defaults;
    private final PendingIntent dismissPendingIntent;
    private String groupKey;
    private final int instanceId;
    private final IntentFilter intentFilter;
    private boolean isNotificationStarted;
    private final Handler mainHandler;
    private final MediaDescriptionAdapter mediaDescriptionAdapter;
    private MediaSessionCompat.Token mediaSessionToken;
    private final NotificationBroadcastReceiver notificationBroadcastReceiver;
    private final int notificationId;
    private final NotificationListener notificationListener;
    private final NotificationManagerCompat notificationManager;
    private final Map<String, NotificationCompat.Action> playbackActions;
    private Player player;
    private final Player.Listener playerListener;
    private int priority;
    private int smallIconResourceId;
    private boolean useChronometer;
    private boolean useFastForwardAction;
    private boolean useFastForwardActionInCompactView;
    private boolean useNextAction;
    private boolean useNextActionInCompactView;
    private boolean usePlayPauseActions;
    private boolean usePreviousAction;
    private boolean usePreviousActionInCompactView;
    private boolean useRewindAction;
    private boolean useRewindActionInCompactView;
    private boolean useStopAction;
    private int visibility;

    public interface CustomActionReceiver {
        Map<String, NotificationCompat.Action> createCustomActions(Context context, int instanceId);

        List<String> getCustomActions(Player player);

        void onCustomAction(Player player, String action, Intent intent);
    }

    public interface MediaDescriptionAdapter {
        PendingIntent createCurrentContentIntent(Player player);

        CharSequence getCurrentContentText(Player player);

        CharSequence getCurrentContentTitle(Player player);

        Bitmap getCurrentLargeIcon(Player player, BitmapCallback callback);

        default CharSequence getCurrentSubText(Player player) {
            return null;
        }
    }

    public interface NotificationListener {
        default void onNotificationCancelled(int notificationId, boolean dismissedByUser) {
        }

        default void onNotificationPosted(int notificationId, Notification notification, boolean ongoing) {
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Priority {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    public static class Builder {
        private int channelDescriptionResourceId;
        private final String channelId;
        private int channelImportance;
        private int channelNameResourceId;
        private final Context context;
        private CustomActionReceiver customActionReceiver;
        private int fastForwardActionIconResourceId;
        private String groupKey;
        private MediaDescriptionAdapter mediaDescriptionAdapter;
        private int nextActionIconResourceId;
        private final int notificationId;
        private NotificationListener notificationListener;
        private int pauseActionIconResourceId;
        private int playActionIconResourceId;
        private int previousActionIconResourceId;
        private int rewindActionIconResourceId;
        private int smallIconResourceId;
        private int stopActionIconResourceId;

        @Deprecated
        public Builder(Context context, int notificationId, String channelId, MediaDescriptionAdapter mediaDescriptionAdapter) {
            this(context, notificationId, channelId);
            this.mediaDescriptionAdapter = mediaDescriptionAdapter;
        }

        public Builder(Context context, int notificationId, String channelId) {
            Assertions.checkArgument(notificationId > 0);
            this.context = context;
            this.notificationId = notificationId;
            this.channelId = channelId;
            this.channelImportance = 2;
            this.mediaDescriptionAdapter = new DefaultMediaDescriptionAdapter(null);
            this.smallIconResourceId = R.drawable.exo_notification_small_icon;
            this.playActionIconResourceId = R.drawable.exo_notification_play;
            this.pauseActionIconResourceId = R.drawable.exo_notification_pause;
            this.stopActionIconResourceId = R.drawable.exo_notification_stop;
            this.rewindActionIconResourceId = R.drawable.exo_notification_rewind;
            this.fastForwardActionIconResourceId = R.drawable.exo_notification_fastforward;
            this.previousActionIconResourceId = R.drawable.exo_notification_previous;
            this.nextActionIconResourceId = R.drawable.exo_notification_next;
        }

        public Builder setChannelNameResourceId(int channelNameResourceId) {
            this.channelNameResourceId = channelNameResourceId;
            return this;
        }

        public Builder setChannelDescriptionResourceId(int channelDescriptionResourceId) {
            this.channelDescriptionResourceId = channelDescriptionResourceId;
            return this;
        }

        public Builder setChannelImportance(int channelImportance) {
            this.channelImportance = channelImportance;
            return this;
        }

        public Builder setNotificationListener(NotificationListener notificationListener) {
            this.notificationListener = notificationListener;
            return this;
        }

        public Builder setCustomActionReceiver(CustomActionReceiver customActionReceiver) {
            this.customActionReceiver = customActionReceiver;
            return this;
        }

        public Builder setSmallIconResourceId(int smallIconResourceId) {
            this.smallIconResourceId = smallIconResourceId;
            return this;
        }

        public Builder setPlayActionIconResourceId(int playActionIconResourceId) {
            this.playActionIconResourceId = playActionIconResourceId;
            return this;
        }

        public Builder setPauseActionIconResourceId(int pauseActionIconResourceId) {
            this.pauseActionIconResourceId = pauseActionIconResourceId;
            return this;
        }

        public Builder setStopActionIconResourceId(int stopActionIconResourceId) {
            this.stopActionIconResourceId = stopActionIconResourceId;
            return this;
        }

        public Builder setRewindActionIconResourceId(int rewindActionIconResourceId) {
            this.rewindActionIconResourceId = rewindActionIconResourceId;
            return this;
        }

        public Builder setFastForwardActionIconResourceId(int fastForwardActionIconResourceId) {
            this.fastForwardActionIconResourceId = fastForwardActionIconResourceId;
            return this;
        }

        public Builder setPreviousActionIconResourceId(int previousActionIconResourceId) {
            this.previousActionIconResourceId = previousActionIconResourceId;
            return this;
        }

        public Builder setNextActionIconResourceId(int nextActionIconResourceId) {
            this.nextActionIconResourceId = nextActionIconResourceId;
            return this;
        }

        public Builder setGroup(String groupKey) {
            this.groupKey = groupKey;
            return this;
        }

        public Builder setMediaDescriptionAdapter(MediaDescriptionAdapter mediaDescriptionAdapter) {
            this.mediaDescriptionAdapter = mediaDescriptionAdapter;
            return this;
        }

        public PlayerNotificationManager build() {
            int i = this.channelNameResourceId;
            if (i != 0) {
                NotificationUtil.createNotificationChannel(this.context, this.channelId, i, this.channelDescriptionResourceId, this.channelImportance);
            }
            return new PlayerNotificationManager(this.context, this.channelId, this.notificationId, this.mediaDescriptionAdapter, this.notificationListener, this.customActionReceiver, this.smallIconResourceId, this.playActionIconResourceId, this.pauseActionIconResourceId, this.stopActionIconResourceId, this.rewindActionIconResourceId, this.fastForwardActionIconResourceId, this.previousActionIconResourceId, this.nextActionIconResourceId, this.groupKey);
        }
    }

    public final class BitmapCallback {
        private final int notificationTag;

        private BitmapCallback(int notificationTag) {
            this.notificationTag = notificationTag;
        }

        public void onBitmap(final Bitmap bitmap) {
            if (bitmap != null) {
                PlayerNotificationManager.this.postUpdateNotificationBitmap(bitmap, this.notificationTag);
            }
        }
    }

    private PlayerNotificationManager(Context context, String channelId, int notificationId, MediaDescriptionAdapter mediaDescriptionAdapter, NotificationListener notificationListener, CustomActionReceiver customActionReceiver, int smallIconResourceId, int playActionIconResourceId, int pauseActionIconResourceId, int stopActionIconResourceId, int rewindActionIconResourceId, int fastForwardActionIconResourceId, int previousActionIconResourceId, int nextActionIconResourceId, String groupKey) {
        Map<String, NotificationCompat.Action> mapEmptyMap;
        Context applicationContext = context.getApplicationContext();
        this.context = applicationContext;
        this.channelId = channelId;
        this.notificationId = notificationId;
        this.mediaDescriptionAdapter = mediaDescriptionAdapter;
        this.notificationListener = notificationListener;
        this.customActionReceiver = customActionReceiver;
        this.smallIconResourceId = smallIconResourceId;
        this.groupKey = groupKey;
        this.controlDispatcher = new DefaultControlDispatcher();
        int i = instanceIdCounter;
        instanceIdCounter = i + 1;
        this.instanceId = i;
        this.mainHandler = Util.createHandler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.google.android.exoplayer2.ui.PlayerNotificationManager$$ExternalSyntheticLambda0
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.f$0.handleMessage(message);
            }
        });
        this.notificationManager = NotificationManagerCompat.from(applicationContext);
        this.playerListener = new PlayerListener();
        this.notificationBroadcastReceiver = new NotificationBroadcastReceiver();
        this.intentFilter = new IntentFilter();
        this.usePreviousAction = true;
        this.useNextAction = true;
        this.usePlayPauseActions = true;
        this.useRewindAction = true;
        this.useFastForwardAction = true;
        this.colorized = true;
        this.useChronometer = true;
        this.color = 0;
        this.defaults = 0;
        this.priority = -1;
        this.badgeIconType = 1;
        this.visibility = 1;
        Map<String, NotificationCompat.Action> mapCreatePlaybackActions = createPlaybackActions(applicationContext, i, playActionIconResourceId, pauseActionIconResourceId, stopActionIconResourceId, rewindActionIconResourceId, fastForwardActionIconResourceId, previousActionIconResourceId, nextActionIconResourceId);
        this.playbackActions = mapCreatePlaybackActions;
        Iterator<String> it = mapCreatePlaybackActions.keySet().iterator();
        while (it.hasNext()) {
            this.intentFilter.addAction(it.next());
        }
        if (customActionReceiver != null) {
            mapEmptyMap = customActionReceiver.createCustomActions(applicationContext, this.instanceId);
        } else {
            mapEmptyMap = Collections.emptyMap();
        }
        this.customActions = mapEmptyMap;
        Iterator<String> it2 = mapEmptyMap.keySet().iterator();
        while (it2.hasNext()) {
            this.intentFilter.addAction(it2.next());
        }
        this.dismissPendingIntent = createBroadcastIntent(ACTION_DISMISS, applicationContext, this.instanceId);
        this.intentFilter.addAction(ACTION_DISMISS);
    }

    public final void setPlayer(Player player) {
        boolean z = true;
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        if (player != null && player.getApplicationLooper() != Looper.getMainLooper()) {
            z = false;
        }
        Assertions.checkArgument(z);
        Player player2 = this.player;
        if (player2 == player) {
            return;
        }
        if (player2 != null) {
            player2.removeListener(this.playerListener);
            if (player == null) {
                stopNotification(false);
            }
        }
        this.player = player;
        if (player != null) {
            player.addListener(this.playerListener);
            postStartOrUpdateNotification();
        }
    }

    @Deprecated
    public final void setControlDispatcher(ControlDispatcher controlDispatcher) {
        if (this.controlDispatcher != controlDispatcher) {
            this.controlDispatcher = controlDispatcher;
            invalidate();
        }
    }

    public void setUseNextAction(boolean useNextAction) {
        if (this.useNextAction != useNextAction) {
            this.useNextAction = useNextAction;
            invalidate();
        }
    }

    public void setUsePreviousAction(boolean usePreviousAction) {
        if (this.usePreviousAction != usePreviousAction) {
            this.usePreviousAction = usePreviousAction;
            invalidate();
        }
    }

    public void setUseNextActionInCompactView(boolean useNextActionInCompactView) {
        if (this.useNextActionInCompactView != useNextActionInCompactView) {
            this.useNextActionInCompactView = useNextActionInCompactView;
            if (useNextActionInCompactView) {
                this.useFastForwardActionInCompactView = false;
            }
            invalidate();
        }
    }

    public void setUsePreviousActionInCompactView(boolean usePreviousActionInCompactView) {
        if (this.usePreviousActionInCompactView != usePreviousActionInCompactView) {
            this.usePreviousActionInCompactView = usePreviousActionInCompactView;
            if (usePreviousActionInCompactView) {
                this.useRewindActionInCompactView = false;
            }
            invalidate();
        }
    }

    public void setUseFastForwardAction(boolean useFastForwardAction) {
        if (this.useFastForwardAction != useFastForwardAction) {
            this.useFastForwardAction = useFastForwardAction;
            invalidate();
        }
    }

    public void setUseRewindAction(boolean useRewindAction) {
        if (this.useRewindAction != useRewindAction) {
            this.useRewindAction = useRewindAction;
            invalidate();
        }
    }

    public void setUseFastForwardActionInCompactView(boolean useFastForwardActionInCompactView) {
        if (this.useFastForwardActionInCompactView != useFastForwardActionInCompactView) {
            this.useFastForwardActionInCompactView = useFastForwardActionInCompactView;
            if (useFastForwardActionInCompactView) {
                this.useNextActionInCompactView = false;
            }
            invalidate();
        }
    }

    public void setUseRewindActionInCompactView(boolean useRewindActionInCompactView) {
        if (this.useRewindActionInCompactView != useRewindActionInCompactView) {
            this.useRewindActionInCompactView = useRewindActionInCompactView;
            if (useRewindActionInCompactView) {
                this.usePreviousActionInCompactView = false;
            }
            invalidate();
        }
    }

    public final void setUsePlayPauseActions(boolean usePlayPauseActions) {
        if (this.usePlayPauseActions != usePlayPauseActions) {
            this.usePlayPauseActions = usePlayPauseActions;
            invalidate();
        }
    }

    public final void setUseStopAction(boolean useStopAction) {
        if (this.useStopAction == useStopAction) {
            return;
        }
        this.useStopAction = useStopAction;
        invalidate();
    }

    public final void setMediaSessionToken(MediaSessionCompat.Token token) {
        if (Util.areEqual(this.mediaSessionToken, token)) {
            return;
        }
        this.mediaSessionToken = token;
        invalidate();
    }

    public final void setBadgeIconType(int badgeIconType) {
        if (this.badgeIconType == badgeIconType) {
            return;
        }
        if (badgeIconType == 0 || badgeIconType == 1 || badgeIconType == 2) {
            this.badgeIconType = badgeIconType;
            invalidate();
            return;
        }
        throw new IllegalArgumentException();
    }

    public final void setColorized(boolean colorized) {
        if (this.colorized != colorized) {
            this.colorized = colorized;
            invalidate();
        }
    }

    public final void setDefaults(int defaults) {
        if (this.defaults != defaults) {
            this.defaults = defaults;
            invalidate();
        }
    }

    public final void setColor(int color) {
        if (this.color != color) {
            this.color = color;
            invalidate();
        }
    }

    public final void setPriority(int priority) {
        if (this.priority == priority) {
            return;
        }
        if (priority == -2 || priority == -1 || priority == 0 || priority == 1 || priority == 2) {
            this.priority = priority;
            invalidate();
            return;
        }
        throw new IllegalArgumentException();
    }

    public final void setSmallIcon(int smallIconResourceId) {
        if (this.smallIconResourceId != smallIconResourceId) {
            this.smallIconResourceId = smallIconResourceId;
            invalidate();
        }
    }

    public final void setUseChronometer(boolean useChronometer) {
        if (this.useChronometer != useChronometer) {
            this.useChronometer = useChronometer;
            invalidate();
        }
    }

    public final void setVisibility(int visibility) {
        if (this.visibility == visibility) {
            return;
        }
        if (visibility == -1 || visibility == 0 || visibility == 1) {
            this.visibility = visibility;
            invalidate();
            return;
        }
        throw new IllegalStateException();
    }

    public void invalidate() {
        if (this.isNotificationStarted) {
            postStartOrUpdateNotification();
        }
    }

    private void startOrUpdateNotification(Player player, Bitmap bitmap) {
        boolean ongoing = getOngoing(player);
        NotificationCompat.Builder builderCreateNotification = createNotification(player, this.builder, ongoing, bitmap);
        this.builder = builderCreateNotification;
        if (builderCreateNotification == null) {
            stopNotification(false);
            return;
        }
        Notification notificationBuild = builderCreateNotification.build();
        this.notificationManager.notify(this.notificationId, notificationBuild);
        if (!this.isNotificationStarted) {
            this.context.registerReceiver(this.notificationBroadcastReceiver, this.intentFilter);
        }
        NotificationListener notificationListener = this.notificationListener;
        if (notificationListener != null) {
            notificationListener.onNotificationPosted(this.notificationId, notificationBuild, ongoing || !this.isNotificationStarted);
        }
        this.isNotificationStarted = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopNotification(boolean dismissedByUser) {
        if (this.isNotificationStarted) {
            this.isNotificationStarted = false;
            this.mainHandler.removeMessages(0);
            this.notificationManager.cancel(this.notificationId);
            this.context.unregisterReceiver(this.notificationBroadcastReceiver);
            NotificationListener notificationListener = this.notificationListener;
            if (notificationListener != null) {
                notificationListener.onNotificationCancelled(this.notificationId, dismissedByUser);
            }
        }
    }

    protected NotificationCompat.Builder createNotification(Player player, NotificationCompat.Builder builder, boolean ongoing, Bitmap largeIcon) {
        NotificationCompat.Action action;
        if (player.getPlaybackState() == 1 && player.getCurrentTimeline().isEmpty()) {
            this.builderActions = null;
            return null;
        }
        List<String> actions = getActions(player);
        ArrayList arrayList = new ArrayList(actions.size());
        for (int i = 0; i < actions.size(); i++) {
            String str = actions.get(i);
            if (this.playbackActions.containsKey(str)) {
                action = this.playbackActions.get(str);
            } else {
                action = this.customActions.get(str);
            }
            if (action != null) {
                arrayList.add(action);
            }
        }
        if (builder == null || !arrayList.equals(this.builderActions)) {
            builder = new NotificationCompat.Builder(this.context, this.channelId);
            this.builderActions = arrayList;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                builder.addAction((NotificationCompat.Action) arrayList.get(i2));
            }
        }
        NotificationCompat.MediaStyle mediaStyle = new NotificationCompat.MediaStyle();
        MediaSessionCompat.Token token = this.mediaSessionToken;
        if (token != null) {
            mediaStyle.setMediaSession(token);
        }
        mediaStyle.setShowActionsInCompactView(getActionIndicesForCompactView(actions, player));
        mediaStyle.setShowCancelButton(!ongoing);
        mediaStyle.setCancelButtonIntent(this.dismissPendingIntent);
        builder.setStyle(mediaStyle);
        builder.setDeleteIntent(this.dismissPendingIntent);
        builder.setBadgeIconType(this.badgeIconType).setOngoing(ongoing).setColor(this.color).setColorized(this.colorized).setSmallIcon(this.smallIconResourceId).setVisibility(this.visibility).setPriority(this.priority).setDefaults(this.defaults);
        if (Util.SDK_INT >= 21 && this.useChronometer && player.isPlaying() && !player.isPlayingAd() && !player.isCurrentWindowDynamic() && player.getPlaybackParameters().speed == 1.0f) {
            builder.setWhen(System.currentTimeMillis() - player.getContentPosition()).setShowWhen(true).setUsesChronometer(true);
        } else {
            builder.setShowWhen(false).setUsesChronometer(false);
        }
        builder.setContentTitle(this.mediaDescriptionAdapter.getCurrentContentTitle(player));
        builder.setContentText(this.mediaDescriptionAdapter.getCurrentContentText(player));
        builder.setSubText(this.mediaDescriptionAdapter.getCurrentSubText(player));
        if (largeIcon == null) {
            MediaDescriptionAdapter mediaDescriptionAdapter = this.mediaDescriptionAdapter;
            int i3 = this.currentNotificationTag + 1;
            this.currentNotificationTag = i3;
            largeIcon = mediaDescriptionAdapter.getCurrentLargeIcon(player, new BitmapCallback(i3));
        }
        setLargeIcon(builder, largeIcon);
        builder.setContentIntent(this.mediaDescriptionAdapter.createCurrentContentIntent(player));
        String str2 = this.groupKey;
        if (str2 != null) {
            builder.setGroup(str2);
        }
        builder.setOnlyAlertOnce(true);
        return builder;
    }

    protected List<String> getActions(Player player) {
        boolean zIsCommandAvailable = player.isCommandAvailable(6);
        boolean z = player.isCommandAvailable(10) && this.controlDispatcher.isRewindEnabled();
        boolean z2 = player.isCommandAvailable(11) && this.controlDispatcher.isFastForwardEnabled();
        boolean zIsCommandAvailable2 = player.isCommandAvailable(8);
        ArrayList arrayList = new ArrayList();
        if (this.usePreviousAction && zIsCommandAvailable) {
            arrayList.add(ACTION_PREVIOUS);
        }
        if (this.useRewindAction && z) {
            arrayList.add(ACTION_REWIND);
        }
        if (this.usePlayPauseActions) {
            if (shouldShowPauseButton(player)) {
                arrayList.add(ACTION_PAUSE);
            } else {
                arrayList.add(ACTION_PLAY);
            }
        }
        if (this.useFastForwardAction && z2) {
            arrayList.add(ACTION_FAST_FORWARD);
        }
        if (this.useNextAction && zIsCommandAvailable2) {
            arrayList.add(ACTION_NEXT);
        }
        CustomActionReceiver customActionReceiver = this.customActionReceiver;
        if (customActionReceiver != null) {
            arrayList.addAll(customActionReceiver.getCustomActions(player));
        }
        if (this.useStopAction) {
            arrayList.add(ACTION_STOP);
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected int[] getActionIndicesForCompactView(java.util.List<java.lang.String> r7, com.google.android.exoplayer2.Player r8) {
        /*
            r6 = this;
            java.lang.String r0 = "com.google.android.exoplayer.pause"
            int r0 = r7.indexOf(r0)
            java.lang.String r1 = "com.google.android.exoplayer.play"
            int r1 = r7.indexOf(r1)
            boolean r2 = r6.usePreviousActionInCompactView
            r3 = -1
            if (r2 == 0) goto L18
            java.lang.String r2 = "com.google.android.exoplayer.prev"
            int r2 = r7.indexOf(r2)
            goto L24
        L18:
            boolean r2 = r6.useRewindActionInCompactView
            if (r2 == 0) goto L23
            java.lang.String r2 = "com.google.android.exoplayer.rewind"
            int r2 = r7.indexOf(r2)
            goto L24
        L23:
            r2 = r3
        L24:
            boolean r4 = r6.useNextActionInCompactView
            if (r4 == 0) goto L2f
            java.lang.String r4 = "com.google.android.exoplayer.next"
            int r7 = r7.indexOf(r4)
            goto L3b
        L2f:
            boolean r4 = r6.useFastForwardActionInCompactView
            if (r4 == 0) goto L3a
            java.lang.String r4 = "com.google.android.exoplayer.ffwd"
            int r7 = r7.indexOf(r4)
            goto L3b
        L3a:
            r7 = r3
        L3b:
            r4 = 3
            int[] r4 = new int[r4]
            r5 = 0
            if (r2 == r3) goto L44
            r4[r5] = r2
            r5 = 1
        L44:
            boolean r8 = r6.shouldShowPauseButton(r8)
            if (r0 == r3) goto L52
            if (r8 == 0) goto L52
            int r8 = r5 + 1
            r4[r5] = r0
        L50:
            r5 = r8
            goto L5b
        L52:
            if (r1 == r3) goto L5b
            if (r8 != 0) goto L5b
            int r8 = r5 + 1
            r4[r5] = r1
            goto L50
        L5b:
            if (r7 == r3) goto L62
            int r8 = r5 + 1
            r4[r5] = r7
            r5 = r8
        L62:
            int[] r7 = java.util.Arrays.copyOf(r4, r5)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.PlayerNotificationManager.getActionIndicesForCompactView(java.util.List, com.google.android.exoplayer2.Player):int[]");
    }

    protected boolean getOngoing(Player player) {
        int playbackState = player.getPlaybackState();
        return (playbackState == 2 || playbackState == 3) && player.getPlayWhenReady();
    }

    private boolean shouldShowPauseButton(Player player) {
        return (player.getPlaybackState() == 4 || player.getPlaybackState() == 1 || !player.getPlayWhenReady()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postStartOrUpdateNotification() {
        if (this.mainHandler.hasMessages(0)) {
            return;
        }
        this.mainHandler.sendEmptyMessage(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postUpdateNotificationBitmap(Bitmap bitmap, int notificationTag) {
        this.mainHandler.obtainMessage(1, notificationTag, -1, bitmap).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleMessage(Message msg) {
        int i = msg.what;
        if (i == 0) {
            Player player = this.player;
            if (player != null) {
                startOrUpdateNotification(player, null);
            }
        } else {
            if (i != 1) {
                return false;
            }
            if (this.player != null && this.isNotificationStarted && this.currentNotificationTag == msg.arg1) {
                startOrUpdateNotification(this.player, (Bitmap) msg.obj);
            }
        }
        return true;
    }

    private static Map<String, NotificationCompat.Action> createPlaybackActions(Context context, int instanceId, int playActionIconResourceId, int pauseActionIconResourceId, int stopActionIconResourceId, int rewindActionIconResourceId, int fastForwardActionIconResourceId, int previousActionIconResourceId, int nextActionIconResourceId) {
        HashMap map = new HashMap();
        map.put(ACTION_PLAY, new NotificationCompat.Action(playActionIconResourceId, context.getString(R.string.exo_controls_play_description), createBroadcastIntent(ACTION_PLAY, context, instanceId)));
        map.put(ACTION_PAUSE, new NotificationCompat.Action(pauseActionIconResourceId, context.getString(R.string.exo_controls_pause_description), createBroadcastIntent(ACTION_PAUSE, context, instanceId)));
        map.put(ACTION_STOP, new NotificationCompat.Action(stopActionIconResourceId, context.getString(R.string.exo_controls_stop_description), createBroadcastIntent(ACTION_STOP, context, instanceId)));
        map.put(ACTION_REWIND, new NotificationCompat.Action(rewindActionIconResourceId, context.getString(R.string.exo_controls_rewind_description), createBroadcastIntent(ACTION_REWIND, context, instanceId)));
        map.put(ACTION_FAST_FORWARD, new NotificationCompat.Action(fastForwardActionIconResourceId, context.getString(R.string.exo_controls_fastforward_description), createBroadcastIntent(ACTION_FAST_FORWARD, context, instanceId)));
        map.put(ACTION_PREVIOUS, new NotificationCompat.Action(previousActionIconResourceId, context.getString(R.string.exo_controls_previous_description), createBroadcastIntent(ACTION_PREVIOUS, context, instanceId)));
        map.put(ACTION_NEXT, new NotificationCompat.Action(nextActionIconResourceId, context.getString(R.string.exo_controls_next_description), createBroadcastIntent(ACTION_NEXT, context, instanceId)));
        return map;
    }

    private static PendingIntent createBroadcastIntent(String action, Context context, int instanceId) {
        Intent intent = new Intent(action).setPackage(context.getPackageName());
        intent.putExtra(EXTRA_INSTANCE_ID, instanceId);
        return PendingIntent.getBroadcast(context, instanceId, intent, Util.SDK_INT >= 23 ? 201326592 : BleCommands.SETTINGS_DAT_FILE_ID);
    }

    private static void setLargeIcon(NotificationCompat.Builder builder, Bitmap largeIcon) {
        builder.setLargeIcon(largeIcon);
    }

    private class PlayerListener implements Player.Listener {
        private PlayerListener() {
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onEvents(Player player, Player.Events events) {
            if (events.containsAny(5, 6, 8, 0, 13, 12, 9, 10, 15)) {
                PlayerNotificationManager.this.postStartOrUpdateNotification();
            }
        }
    }

    private class NotificationBroadcastReceiver extends BroadcastReceiver {
        private NotificationBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Player player = PlayerNotificationManager.this.player;
            if (player != null && PlayerNotificationManager.this.isNotificationStarted && intent.getIntExtra(PlayerNotificationManager.EXTRA_INSTANCE_ID, PlayerNotificationManager.this.instanceId) == PlayerNotificationManager.this.instanceId) {
                String action = intent.getAction();
                if (PlayerNotificationManager.ACTION_PLAY.equals(action)) {
                    if (player.getPlaybackState() == 1) {
                        PlayerNotificationManager.this.controlDispatcher.dispatchPrepare(player);
                    } else if (player.getPlaybackState() == 4) {
                        PlayerNotificationManager.this.controlDispatcher.dispatchSeekTo(player, player.getCurrentWindowIndex(), C.TIME_UNSET);
                    }
                    PlayerNotificationManager.this.controlDispatcher.dispatchSetPlayWhenReady(player, true);
                    return;
                }
                if (PlayerNotificationManager.ACTION_PAUSE.equals(action)) {
                    PlayerNotificationManager.this.controlDispatcher.dispatchSetPlayWhenReady(player, false);
                    return;
                }
                if (PlayerNotificationManager.ACTION_PREVIOUS.equals(action)) {
                    PlayerNotificationManager.this.controlDispatcher.dispatchPrevious(player);
                    return;
                }
                if (PlayerNotificationManager.ACTION_REWIND.equals(action)) {
                    PlayerNotificationManager.this.controlDispatcher.dispatchRewind(player);
                    return;
                }
                if (PlayerNotificationManager.ACTION_FAST_FORWARD.equals(action)) {
                    PlayerNotificationManager.this.controlDispatcher.dispatchFastForward(player);
                    return;
                }
                if (PlayerNotificationManager.ACTION_NEXT.equals(action)) {
                    PlayerNotificationManager.this.controlDispatcher.dispatchNext(player);
                    return;
                }
                if (PlayerNotificationManager.ACTION_STOP.equals(action)) {
                    PlayerNotificationManager.this.controlDispatcher.dispatchStop(player, true);
                    return;
                }
                if (PlayerNotificationManager.ACTION_DISMISS.equals(action)) {
                    PlayerNotificationManager.this.stopNotification(true);
                } else {
                    if (action == null || PlayerNotificationManager.this.customActionReceiver == null || !PlayerNotificationManager.this.customActions.containsKey(action)) {
                        return;
                    }
                    PlayerNotificationManager.this.customActionReceiver.onCustomAction(player, action, intent);
                }
            }
        }
    }
}
