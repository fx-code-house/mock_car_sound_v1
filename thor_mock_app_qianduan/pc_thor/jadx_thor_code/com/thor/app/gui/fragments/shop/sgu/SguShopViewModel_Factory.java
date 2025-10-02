package com.thor.app.gui.fragments.shop.sgu;

import com.thor.app.billing.BillingManager;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.UsersManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class SguShopViewModel_Factory implements Factory<SguShopViewModel> {
    private final Provider<BillingManager> billingManagerProvider;
    private final Provider<BleManager> bleManagerProvider;
    private final Provider<UsersManager> usersManagerProvider;

    public SguShopViewModel_Factory(Provider<UsersManager> usersManagerProvider, Provider<BillingManager> billingManagerProvider, Provider<BleManager> bleManagerProvider) {
        this.usersManagerProvider = usersManagerProvider;
        this.billingManagerProvider = billingManagerProvider;
        this.bleManagerProvider = bleManagerProvider;
    }

    @Override // javax.inject.Provider
    public SguShopViewModel get() {
        return newInstance(this.usersManagerProvider.get(), this.billingManagerProvider.get(), this.bleManagerProvider.get());
    }

    public static SguShopViewModel_Factory create(Provider<UsersManager> usersManagerProvider, Provider<BillingManager> billingManagerProvider, Provider<BleManager> bleManagerProvider) {
        return new SguShopViewModel_Factory(usersManagerProvider, billingManagerProvider, bleManagerProvider);
    }

    public static SguShopViewModel newInstance(UsersManager usersManager, BillingManager billingManager, BleManager bleManager) {
        return new SguShopViewModel(usersManager, billingManager, bleManager);
    }
}
