package com.thor.app.managers;

import android.content.Context;
import com.thor.app.utils.NotificationCountHelper;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PushNotificationManager.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lcom/thor/app/managers/PushNotificationManager;", "", "contextApp", "Landroid/content/Context;", "notificationCountHelper", "Lcom/thor/app/utils/NotificationCountHelper;", "(Landroid/content/Context;Lcom/thor/app/utils/NotificationCountHelper;)V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class PushNotificationManager {
    private final Context contextApp;
    private final NotificationCountHelper notificationCountHelper;

    @Inject
    public PushNotificationManager(Context contextApp, NotificationCountHelper notificationCountHelper) {
        Intrinsics.checkNotNullParameter(contextApp, "contextApp");
        Intrinsics.checkNotNullParameter(notificationCountHelper, "notificationCountHelper");
        this.contextApp = contextApp;
        this.notificationCountHelper = notificationCountHelper;
    }
}
