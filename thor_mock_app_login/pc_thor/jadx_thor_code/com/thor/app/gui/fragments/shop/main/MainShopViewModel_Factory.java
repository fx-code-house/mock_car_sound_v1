package com.thor.app.gui.fragments.shop.main;

import com.thor.app.billing.BillingManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class MainShopViewModel_Factory implements Factory<MainShopViewModel> {
    private final Provider<BillingManager> billingManagerProvider;
    private final Provider<ThorDatabase> databaseProvider;
    private final Provider<UsersManager> usersManagerProvider;

    public MainShopViewModel_Factory(Provider<UsersManager> usersManagerProvider, Provider<ThorDatabase> databaseProvider, Provider<BillingManager> billingManagerProvider) {
        this.usersManagerProvider = usersManagerProvider;
        this.databaseProvider = databaseProvider;
        this.billingManagerProvider = billingManagerProvider;
    }

    @Override // javax.inject.Provider
    public MainShopViewModel get() {
        return newInstance(this.usersManagerProvider.get(), this.databaseProvider.get(), this.billingManagerProvider.get());
    }

    public static MainShopViewModel_Factory create(Provider<UsersManager> usersManagerProvider, Provider<ThorDatabase> databaseProvider, Provider<BillingManager> billingManagerProvider) {
        return new MainShopViewModel_Factory(usersManagerProvider, databaseProvider, billingManagerProvider);
    }

    public static MainShopViewModel newInstance(UsersManager usersManager, ThorDatabase database, BillingManager billingManager) {
        return new MainShopViewModel(usersManager, database, billingManager);
    }
}
