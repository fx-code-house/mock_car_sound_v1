package com.thor.app.di;

import android.content.Context;
import com.thor.app.utils.NotificationCountHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public final class AppModule_ProvideNotificationCountHelperFactory implements Factory<NotificationCountHelper> {
    private final Provider<Context> contextProvider;

    public AppModule_ProvideNotificationCountHelperFactory(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    @Override // javax.inject.Provider
    public NotificationCountHelper get() {
        return provideNotificationCountHelper(this.contextProvider.get());
    }

    public static AppModule_ProvideNotificationCountHelperFactory create(Provider<Context> contextProvider) {
        return new AppModule_ProvideNotificationCountHelperFactory(contextProvider);
    }

    public static NotificationCountHelper provideNotificationCountHelper(Context context) {
        return (NotificationCountHelper) Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideNotificationCountHelper(context));
    }
}
