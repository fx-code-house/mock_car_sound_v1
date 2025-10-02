package com.thor.app.gui.activities.preset.sgu;

import com.thor.app.managers.BleManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class AddSguPresetViewModel_Factory implements Factory<AddSguPresetViewModel> {
    private final Provider<BleManager> bleManagerProvider;
    private final Provider<ThorDatabase> databaseProvider;
    private final Provider<UsersManager> usersManagerProvider;

    public AddSguPresetViewModel_Factory(Provider<UsersManager> usersManagerProvider, Provider<BleManager> bleManagerProvider, Provider<ThorDatabase> databaseProvider) {
        this.usersManagerProvider = usersManagerProvider;
        this.bleManagerProvider = bleManagerProvider;
        this.databaseProvider = databaseProvider;
    }

    @Override // javax.inject.Provider
    public AddSguPresetViewModel get() {
        return newInstance(this.usersManagerProvider.get(), this.bleManagerProvider.get(), this.databaseProvider.get());
    }

    public static AddSguPresetViewModel_Factory create(Provider<UsersManager> usersManagerProvider, Provider<BleManager> bleManagerProvider, Provider<ThorDatabase> databaseProvider) {
        return new AddSguPresetViewModel_Factory(usersManagerProvider, bleManagerProvider, databaseProvider);
    }

    public static AddSguPresetViewModel newInstance(UsersManager usersManager, BleManager bleManager, ThorDatabase database) {
        return new AddSguPresetViewModel(usersManager, bleManager, database);
    }
}
