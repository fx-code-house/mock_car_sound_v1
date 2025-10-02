package com.thor.app.auto.media;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import com.carsystems.thor.app.R;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;

/* compiled from: AutoNotificationManager.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0014B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/thor/app/auto/media/AutoNotificationManager;", "", "context", "Landroid/content/Context;", "sessionToken", "Landroid/support/v4/media/session/MediaSessionCompat$Token;", "notificationListener", "Lcom/google/android/exoplayer2/ui/PlayerNotificationManager$NotificationListener;", "(Landroid/content/Context;Landroid/support/v4/media/session/MediaSessionCompat$Token;Lcom/google/android/exoplayer2/ui/PlayerNotificationManager$NotificationListener;)V", "notificationManager", "Lcom/google/android/exoplayer2/ui/PlayerNotificationManager;", "serviceJob", "Lkotlinx/coroutines/CompletableJob;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "hideNotification", "", "showNotificationForPlayer", "player", "Lcom/google/android/exoplayer2/Player;", "DescriptionAdapter", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AutoNotificationManager {
    private final Context context;
    private final PlayerNotificationManager notificationManager;
    private final CompletableJob serviceJob;
    private final CoroutineScope serviceScope;

    public AutoNotificationManager(Context context, MediaSessionCompat.Token sessionToken, PlayerNotificationManager.NotificationListener notificationListener) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(sessionToken, "sessionToken");
        Intrinsics.checkNotNullParameter(notificationListener, "notificationListener");
        this.context = context;
        CompletableJob completableJobSupervisorJob$default = SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null);
        this.serviceJob = completableJobSupervisorJob$default;
        this.serviceScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getMain().plus(completableJobSupervisorJob$default));
        PlayerNotificationManager playerNotificationManagerBuild = new PlayerNotificationManager.Builder(context, AutoNotificationManagerKt.NOW_PLAYING_NOTIFICATION_ID, AutoNotificationManagerKt.NOW_PLAYING_CHANNEL_ID).setChannelNameResourceId(R.string.notification_channel).setChannelDescriptionResourceId(R.string.notification_channel_description).setMediaDescriptionAdapter(new DescriptionAdapter(this, new MediaControllerCompat(context, sessionToken))).setNotificationListener(notificationListener).build();
        Intrinsics.checkNotNullExpressionValue(playerNotificationManagerBuild, "Builder(\n            con…ner)\n            .build()");
        playerNotificationManagerBuild.setMediaSessionToken(sessionToken);
        playerNotificationManagerBuild.setSmallIcon(R.drawable.ic_notification);
        this.notificationManager = playerNotificationManagerBuild;
    }

    public final void hideNotification() {
        this.notificationManager.setPlayer(null);
    }

    public final void showNotificationForPlayer(Player player) {
        Intrinsics.checkNotNullParameter(player, "player");
        this.notificationManager.setPlayer(player);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AutoNotificationManager.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u001e\u0010\u0018\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0013\u001a\u00020\u00142\n\u0010\u0019\u001a\u00060\u001aR\u00020\u001bH\u0016J\u001b\u0010\u001c\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001d\u001a\u00020\fH\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u001eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001f"}, d2 = {"Lcom/thor/app/auto/media/AutoNotificationManager$DescriptionAdapter;", "Lcom/google/android/exoplayer2/ui/PlayerNotificationManager$MediaDescriptionAdapter;", "controller", "Landroid/support/v4/media/session/MediaControllerCompat;", "(Lcom/thor/app/auto/media/AutoNotificationManager;Landroid/support/v4/media/session/MediaControllerCompat;)V", "currentBitmap", "Landroid/graphics/Bitmap;", "getCurrentBitmap", "()Landroid/graphics/Bitmap;", "setCurrentBitmap", "(Landroid/graphics/Bitmap;)V", "currentIconUri", "Landroid/net/Uri;", "getCurrentIconUri", "()Landroid/net/Uri;", "setCurrentIconUri", "(Landroid/net/Uri;)V", "createCurrentContentIntent", "Landroid/app/PendingIntent;", "player", "Lcom/google/android/exoplayer2/Player;", "getCurrentContentText", "", "getCurrentContentTitle", "getCurrentLargeIcon", "callback", "Lcom/google/android/exoplayer2/ui/PlayerNotificationManager$BitmapCallback;", "Lcom/google/android/exoplayer2/ui/PlayerNotificationManager;", "resolveUriAsBitmap", "uri", "(Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    final class DescriptionAdapter implements PlayerNotificationManager.MediaDescriptionAdapter {
        private final MediaControllerCompat controller;
        private Bitmap currentBitmap;
        private Uri currentIconUri;
        final /* synthetic */ AutoNotificationManager this$0;

        public DescriptionAdapter(AutoNotificationManager autoNotificationManager, MediaControllerCompat controller) {
            Intrinsics.checkNotNullParameter(controller, "controller");
            this.this$0 = autoNotificationManager;
            this.controller = controller;
        }

        public final Uri getCurrentIconUri() {
            return this.currentIconUri;
        }

        public final void setCurrentIconUri(Uri uri) {
            this.currentIconUri = uri;
        }

        public final Bitmap getCurrentBitmap() {
            return this.currentBitmap;
        }

        public final void setCurrentBitmap(Bitmap bitmap) {
            this.currentBitmap = bitmap;
        }

        @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
        public PendingIntent createCurrentContentIntent(Player player) {
            Intrinsics.checkNotNullParameter(player, "player");
            return this.controller.getSessionActivity();
        }

        @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
        public String getCurrentContentText(Player player) {
            Intrinsics.checkNotNullParameter(player, "player");
            return String.valueOf(this.controller.getMetadata().getDescription().getSubtitle());
        }

        @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
        public String getCurrentContentTitle(Player player) {
            Intrinsics.checkNotNullParameter(player, "player");
            return String.valueOf(this.controller.getMetadata().getDescription().getTitle());
        }

        @Override // com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
        public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
            Bitmap bitmap;
            Intrinsics.checkNotNullParameter(player, "player");
            Intrinsics.checkNotNullParameter(callback, "callback");
            Uri iconUri = this.controller.getMetadata().getDescription().getIconUri();
            if (Intrinsics.areEqual(this.currentIconUri, iconUri) && (bitmap = this.currentBitmap) != null) {
                return bitmap;
            }
            this.currentIconUri = iconUri;
            BuildersKt__Builders_commonKt.launch$default(this.this$0.serviceScope, null, null, new AutoNotificationManager$DescriptionAdapter$getCurrentLargeIcon$1(this, iconUri, callback, null), 3, null);
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Object resolveUriAsBitmap(Uri uri, Continuation<? super Bitmap> continuation) {
            return BuildersKt.withContext(Dispatchers.getIO(), new AutoNotificationManager$DescriptionAdapter$resolveUriAsBitmap$2(this.this$0, uri, null), continuation);
        }
    }
}
