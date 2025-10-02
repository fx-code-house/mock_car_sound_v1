package com.thor.app.di;

import android.content.Context;
import com.thor.app.managers.PushNotificationManager;
import com.thor.app.utils.NotificationCountHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public final class AppModule_ProvidePushNotificationManagerFactory implements Factory<PushNotificationManager> {
    private final Provider<Context> contextProvider;
    private final Provider<NotificationCountHelper> notificationCountHelperProvider;

    public AppModule_ProvidePushNotificationManagerFactory(Provider<Context> contextProvider, Provider<NotificationCountHelper> notificationCountHelperProvider) {
        this.contextProvider = contextProvider;
        this.notificationCountHelperProvider = notificationCountHelperProvider;
    }

    @Override // javax.inject.Provider
    public PushNotificationManager get() {
        return providePushNotificationManager(this.contextProvider.get(), this.notificationCountHelperProvider.get());
    }

    public static AppModule_ProvidePushNotificationManagerFactory create(Provider<Context> contextProvider, Provider<NotificationCountHelper> notificationCountHelperProvider) {
        return new AppModule_ProvidePushNotificationManagerFactory(contextProvider, notificationCountHelperProvider);
    }

    public static PushNotificationManager providePushNotificationManager(Context context, NotificationCountHelper notificationCountHelper) {
        return (PushNotificationManager) Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providePushNotificationManager(context, notificationCountHelper));
    }
}
