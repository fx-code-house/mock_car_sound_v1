package com.thor.app.gui.activities.splash;

import com.thor.app.room.dao.CurrentVersionDao;
import com.thor.networkmodule.network.ApiServiceNew;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class SplashActivityViewModel_Factory implements Factory<SplashActivityViewModel> {
    private final Provider<ApiServiceNew> apiNewServiceProvider;
    private final Provider<CurrentVersionDao> currentVersionProvider;

    public SplashActivityViewModel_Factory(Provider<CurrentVersionDao> currentVersionProvider, Provider<ApiServiceNew> apiNewServiceProvider) {
        this.currentVersionProvider = currentVersionProvider;
        this.apiNewServiceProvider = apiNewServiceProvider;
    }

    @Override // javax.inject.Provider
    public SplashActivityViewModel get() {
        return newInstance(this.currentVersionProvider.get(), this.apiNewServiceProvider.get());
    }

    public static SplashActivityViewModel_Factory create(Provider<CurrentVersionDao> currentVersionProvider, Provider<ApiServiceNew> apiNewServiceProvider) {
        return new SplashActivityViewModel_Factory(currentVersionProvider, apiNewServiceProvider);
    }

    public static SplashActivityViewModel newInstance(CurrentVersionDao currentVersion, ApiServiceNew apiNewService) {
        return new SplashActivityViewModel(currentVersion, apiNewService);
    }
}
