package com.thor.app.gui.activities.shop;

import com.thor.app.managers.UsersManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class ShopViewModel_Factory implements Factory<ShopViewModel> {
    private final Provider<UsersManager> usersManagerProvider;

    public ShopViewModel_Factory(Provider<UsersManager> usersManagerProvider) {
        this.usersManagerProvider = usersManagerProvider;
    }

    @Override // javax.inject.Provider
    public ShopViewModel get() {
        return newInstance(this.usersManagerProvider.get());
    }

    public static ShopViewModel_Factory create(Provider<UsersManager> usersManagerProvider) {
        return new ShopViewModel_Factory(usersManagerProvider);
    }

    public static ShopViewModel newInstance(UsersManager usersManager) {
        return new ShopViewModel(usersManager);
    }
}
