package com.thor.app.gui.fragments.shop.main;

import com.thor.app.gui.fragments.shop.main.MainShopViewModel_HiltModules;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: classes3.dex */
public final class MainShopViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
    @Override // javax.inject.Provider
    public String get() {
        return provide();
    }

    public static MainShopViewModel_HiltModules_KeyModule_ProvideFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static String provide() {
        return (String) Preconditions.checkNotNullFromProvides(MainShopViewModel_HiltModules.KeyModule.provide());
    }

    private static final class InstanceHolder {
        private static final MainShopViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new MainShopViewModel_HiltModules_KeyModule_ProvideFactory();

        private InstanceHolder() {
        }
    }
}
