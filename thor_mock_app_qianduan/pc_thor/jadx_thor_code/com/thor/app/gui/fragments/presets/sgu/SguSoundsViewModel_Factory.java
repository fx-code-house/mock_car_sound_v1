package com.thor.app.gui.fragments.presets.sgu;

import android.content.Context;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class SguSoundsViewModel_Factory implements Factory<SguSoundsViewModel> {
    private final Provider<BleManager> bleManagerProvider;
    private final Provider<Context> contextProvider;
    private final Provider<ThorDatabase> databaseProvider;
    private final Provider<UsersManager> usersManagerProvider;

    public SguSoundsViewModel_Factory(Provider<Context> contextProvider, Provider<UsersManager> usersManagerProvider, Provider<ThorDatabase> databaseProvider, Provider<BleManager> bleManagerProvider) {
        this.contextProvider = contextProvider;
        this.usersManagerProvider = usersManagerProvider;
        this.databaseProvider = databaseProvider;
        this.bleManagerProvider = bleManagerProvider;
    }

    @Override // javax.inject.Provider
    public SguSoundsViewModel get() {
        return newInstance(this.contextProvider.get(), this.usersManagerProvider.get(), this.databaseProvider.get(), this.bleManagerProvider.get());
    }

    public static SguSoundsViewModel_Factory create(Provider<Context> contextProvider, Provider<UsersManager> usersManagerProvider, Provider<ThorDatabase> databaseProvider, Provider<BleManager> bleManagerProvider) {
        return new SguSoundsViewModel_Factory(contextProvider, usersManagerProvider, databaseProvider, bleManagerProvider);
    }

    public static SguSoundsViewModel newInstance(Context context, UsersManager usersManager, ThorDatabase database, BleManager bleManager) {
        return new SguSoundsViewModel(context, usersManager, database, bleManager);
    }
}
