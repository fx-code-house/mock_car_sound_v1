package com.thor.app.gui.activities.notifications;

import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.settings.Settings;
import com.thor.app.utils.NotificationCountHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class NotificationsViewModel_Factory implements Factory<NotificationsViewModel> {
    private final Provider<ThorDatabase> databaseProvider;
    private final Provider<NotificationCountHelper> notificationCountHelperProvider;
    private final Provider<Settings> settingsProvider;
    private final Provider<UsersManager> usersManagerProvider;

    public NotificationsViewModel_Factory(Provider<UsersManager> usersManagerProvider, Provider<Settings> settingsProvider, Provider<ThorDatabase> databaseProvider, Provider<NotificationCountHelper> notificationCountHelperProvider) {
        this.usersManagerProvider = usersManagerProvider;
        this.settingsProvider = settingsProvider;
        this.databaseProvider = databaseProvider;
        this.notificationCountHelperProvider = notificationCountHelperProvider;
    }

    @Override // javax.inject.Provider
    public NotificationsViewModel get() {
        return newInstance(this.usersManagerProvider.get(), this.settingsProvider.get(), this.databaseProvider.get(), this.notificationCountHelperProvider.get());
    }

    public static NotificationsViewModel_Factory create(Provider<UsersManager> usersManagerProvider, Provider<Settings> settingsProvider, Provider<ThorDatabase> databaseProvider, Provider<NotificationCountHelper> notificationCountHelperProvider) {
        return new NotificationsViewModel_Factory(usersManagerProvider, settingsProvider, databaseProvider, notificationCountHelperProvider);
    }

    public static NotificationsViewModel newInstance(UsersManager usersManager, Settings settings, ThorDatabase database, NotificationCountHelper notificationCountHelper) {
        return new NotificationsViewModel(usersManager, settings, database, notificationCountHelper);
    }
}
