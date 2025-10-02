package com.thor.app.gui.activities.settings;

import com.thor.app.managers.UsersManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class SettingsActivityViewModel_Factory implements Factory<SettingsActivityViewModel> {
    private final Provider<UsersManager> usersManagerProvider;

    public SettingsActivityViewModel_Factory(Provider<UsersManager> usersManagerProvider) {
        this.usersManagerProvider = usersManagerProvider;
    }

    @Override // javax.inject.Provider
    public SettingsActivityViewModel get() {
        return newInstance(this.usersManagerProvider.get());
    }

    public static SettingsActivityViewModel_Factory create(Provider<UsersManager> usersManagerProvider) {
        return new SettingsActivityViewModel_Factory(usersManagerProvider);
    }

    public static SettingsActivityViewModel newInstance(UsersManager usersManager) {
        return new SettingsActivityViewModel(usersManager);
    }
}
