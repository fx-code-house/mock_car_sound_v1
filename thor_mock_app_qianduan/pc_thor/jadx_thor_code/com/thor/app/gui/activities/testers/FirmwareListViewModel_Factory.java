package com.thor.app.gui.activities.testers;

import com.thor.app.managers.UsersManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class FirmwareListViewModel_Factory implements Factory<FirmwareListViewModel> {
    private final Provider<UsersManager> usersManagerProvider;

    public FirmwareListViewModel_Factory(Provider<UsersManager> usersManagerProvider) {
        this.usersManagerProvider = usersManagerProvider;
    }

    @Override // javax.inject.Provider
    public FirmwareListViewModel get() {
        return newInstance(this.usersManagerProvider.get());
    }

    public static FirmwareListViewModel_Factory create(Provider<UsersManager> usersManagerProvider) {
        return new FirmwareListViewModel_Factory(usersManagerProvider);
    }

    public static FirmwareListViewModel newInstance(UsersManager usersManager) {
        return new FirmwareListViewModel(usersManager);
    }
}
