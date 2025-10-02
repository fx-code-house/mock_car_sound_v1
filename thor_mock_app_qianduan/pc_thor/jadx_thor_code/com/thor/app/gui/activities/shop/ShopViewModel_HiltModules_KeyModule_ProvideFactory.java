package com.thor.app.gui.activities.shop;

import com.thor.app.gui.activities.shop.ShopViewModel_HiltModules;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: classes3.dex */
public final class ShopViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
    @Override // javax.inject.Provider
    public String get() {
        return provide();
    }

    public static ShopViewModel_HiltModules_KeyModule_ProvideFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static String provide() {
        return (String) Preconditions.checkNotNullFromProvides(ShopViewModel_HiltModules.KeyModule.provide());
    }

    private static final class InstanceHolder {
        private static final ShopViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new ShopViewModel_HiltModules_KeyModule_ProvideFactory();

        private InstanceHolder() {
        }
    }
}
