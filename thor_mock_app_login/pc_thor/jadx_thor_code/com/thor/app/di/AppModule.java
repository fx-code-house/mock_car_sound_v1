package com.thor.app.di;

import android.content.Context;
import com.orhanobut.hawk.Hawk;
import com.thor.app.billing.BillingManager;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.PushNotificationManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.NotificationCountHelper;
import com.thor.app.utils.logs.loggers.FileLogger;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Singleton;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AppModule.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u000b\u001a\u00020\f2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u001a\u0010\r\u001a\u00020\u000e2\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\fH\u0007J\u0012\u0010\u0010\u001a\u00020\u00112\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0012\u001a\u00020\u00132\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0014"}, d2 = {"Lcom/thor/app/di/AppModule;", "", "()V", "provideBillingManager", "Lcom/thor/app/billing/BillingManager;", "context", "Landroid/content/Context;", "provideBleManager", "Lcom/thor/app/managers/BleManager;", "provideFileLogger", "Lcom/thor/app/utils/logs/loggers/FileLogger;", "provideNotificationCountHelper", "Lcom/thor/app/utils/NotificationCountHelper;", "providePushNotificationManager", "Lcom/thor/app/managers/PushNotificationManager;", "notificationCountHelper", "provideSettings", "Lcom/thor/app/settings/Settings;", "provideUsersManager", "Lcom/thor/app/managers/UsersManager;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Module
/* loaded from: classes2.dex */
public final class AppModule {
    public static final AppModule INSTANCE = new AppModule();

    private AppModule() {
    }

    @Provides
    @Singleton
    public final BillingManager provideBillingManager(@ApplicationContext Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return new BillingManager(context);
    }

    @Provides
    @Singleton
    public final BleManager provideBleManager(@ApplicationContext Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return BleManager.INSTANCE.from(context);
    }

    @Provides
    @Singleton
    public final Settings provideSettings(@ApplicationContext Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Hawk.init(context).build();
        return Settings.INSTANCE;
    }

    @Provides
    @Singleton
    public final FileLogger provideFileLogger(@ApplicationContext Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, context, null, 2, null);
    }

    @Provides
    @Singleton
    public final UsersManager provideUsersManager(@ApplicationContext Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return UsersManager.INSTANCE.from(context);
    }

    @Provides
    @Singleton
    public final PushNotificationManager providePushNotificationManager(@ApplicationContext Context context, NotificationCountHelper notificationCountHelper) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(notificationCountHelper, "notificationCountHelper");
        return new PushNotificationManager(context, notificationCountHelper);
    }

    @Provides
    @Singleton
    public final NotificationCountHelper provideNotificationCountHelper(@ApplicationContext Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return new NotificationCountHelper(context);
    }
}
