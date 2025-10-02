package com.thor.app.managers;

import android.content.Context;
import com.thor.app.utils.NotificationCountHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class PushNotificationManager_Factory implements Factory<PushNotificationManager> {
    private final Provider<Context> contextAppProvider;
    private final Provider<NotificationCountHelper> notificationCountHelperProvider;

    public PushNotificationManager_Factory(Provider<Context> contextAppProvider, Provider<NotificationCountHelper> notificationCountHelperProvider) {
        this.contextAppProvider = contextAppProvider;
        this.notificationCountHelperProvider = notificationCountHelperProvider;
    }

    @Override // javax.inject.Provider
    public PushNotificationManager get() {
        return newInstance(this.contextAppProvider.get(), this.notificationCountHelperProvider.get());
    }

    public static PushNotificationManager_Factory create(Provider<Context> contextAppProvider, Provider<NotificationCountHelper> notificationCountHelperProvider) {
        return new PushNotificationManager_Factory(contextAppProvider, notificationCountHelperProvider);
    }

    public static PushNotificationManager newInstance(Context contextApp, NotificationCountHelper notificationCountHelper) {
        return new PushNotificationManager(contextApp, notificationCountHelper);
    }
}
