package com.thor.app.gui.activities.main;

import com.thor.app.managers.UsersManager;
import com.thor.app.room.dao.CurrentVersionDao;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class MainActivityViewModel_Factory implements Factory<MainActivityViewModel> {
    private final Provider<CurrentVersionDao> currentVersionProvider;
    private final Provider<UsersManager> usersManagerProvider;

    public MainActivityViewModel_Factory(Provider<UsersManager> usersManagerProvider, Provider<CurrentVersionDao> currentVersionProvider) {
        this.usersManagerProvider = usersManagerProvider;
        this.currentVersionProvider = currentVersionProvider;
    }

    @Override // javax.inject.Provider
    public MainActivityViewModel get() {
        return newInstance(this.usersManagerProvider.get(), this.currentVersionProvider.get());
    }

    public static MainActivityViewModel_Factory create(Provider<UsersManager> usersManagerProvider, Provider<CurrentVersionDao> currentVersionProvider) {
        return new MainActivityViewModel_Factory(usersManagerProvider, currentVersionProvider);
    }

    public static MainActivityViewModel newInstance(UsersManager usersManager, CurrentVersionDao currentVersion) {
        return new MainActivityViewModel(usersManager, currentVersion);
    }
}
