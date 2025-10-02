package com.thor.app.databinding.viewmodels;

import android.content.Context;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.dao.CurrentVersionDao;
import com.thor.networkmodule.network.ApiServiceNew;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public final class UpdateViewModel_Factory implements Factory<UpdateViewModel> {
    private final Provider<ApiServiceNew> apiNewServiceProvider;
    private final Provider<Context> contextProvider;
    private final Provider<CurrentVersionDao> currentVersionProvider;
    private final Provider<UsersManager> usersManagerProvider;

    public UpdateViewModel_Factory(Provider<UsersManager> usersManagerProvider, Provider<CurrentVersionDao> currentVersionProvider, Provider<ApiServiceNew> apiNewServiceProvider, Provider<Context> contextProvider) {
        this.usersManagerProvider = usersManagerProvider;
        this.currentVersionProvider = currentVersionProvider;
        this.apiNewServiceProvider = apiNewServiceProvider;
        this.contextProvider = contextProvider;
    }

    @Override // javax.inject.Provider
    public UpdateViewModel get() {
        return newInstance(this.usersManagerProvider.get(), this.currentVersionProvider.get(), this.apiNewServiceProvider.get(), this.contextProvider.get());
    }

    public static UpdateViewModel_Factory create(Provider<UsersManager> usersManagerProvider, Provider<CurrentVersionDao> currentVersionProvider, Provider<ApiServiceNew> apiNewServiceProvider, Provider<Context> contextProvider) {
        return new UpdateViewModel_Factory(usersManagerProvider, currentVersionProvider, apiNewServiceProvider, contextProvider);
    }

    public static UpdateViewModel newInstance(UsersManager usersManager, CurrentVersionDao currentVersion, ApiServiceNew apiNewService, Context context) {
        return new UpdateViewModel(usersManager, currentVersion, apiNewService, context);
    }
}
