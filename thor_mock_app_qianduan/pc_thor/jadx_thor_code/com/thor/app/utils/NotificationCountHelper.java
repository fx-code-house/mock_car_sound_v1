package com.thor.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import javax.inject.Singleton;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotificationCountHelper.kt */
@Singleton
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tJ\u000e\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000bR\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/thor/app/utils/NotificationCountHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "notificationHelper", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "getNotificationUpdateFirmwareSent", "", "getUnreadNotificationCount", "", "setNotificationUpdateFirmwareSent", "", "isSent", "setUnreadNotificationCount", "unreadNotificationCount", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class NotificationCountHelper {
    private static final String NOTIFICATION_HELPER_PREF = "notification_preferences";
    private static final String NOTIFICATION_UPDATE_FIRMWARE_SENT = "firmware_update_sent";
    private static final String UNREAD_NOTIFICATION_COUNT = "unread_notification_count";
    private final SharedPreferences notificationHelper;

    public NotificationCountHelper(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.notificationHelper = context.getSharedPreferences(NOTIFICATION_HELPER_PREF, 0);
    }

    public final void setUnreadNotificationCount(int unreadNotificationCount) {
        Log.i("UNREAD NOTIFICATIONS", "CAPTURED " + unreadNotificationCount);
        this.notificationHelper.edit().putInt(UNREAD_NOTIFICATION_COUNT, unreadNotificationCount).apply();
    }

    public final int getUnreadNotificationCount() {
        int i = this.notificationHelper.getInt(UNREAD_NOTIFICATION_COUNT, 0);
        Log.i("UNREAD NOTIFICATIONS", String.valueOf(i));
        return i;
    }

    public final boolean getNotificationUpdateFirmwareSent() {
        return this.notificationHelper.getBoolean(NOTIFICATION_UPDATE_FIRMWARE_SENT, false);
    }

    public final void setNotificationUpdateFirmwareSent(boolean isSent) {
        this.notificationHelper.edit().putBoolean(NOTIFICATION_UPDATE_FIRMWARE_SENT, isSent).apply();
    }
}
