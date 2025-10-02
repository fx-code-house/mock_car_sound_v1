package com.thor.app.gui.fragments.shop.sgu;

import com.thor.app.gui.fragments.shop.sgu.SguShopViewModel_HiltModules;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: classes3.dex */
public final class SguShopViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
    @Override // javax.inject.Provider
    public String get() {
        return provide();
    }

    public static SguShopViewModel_HiltModules_KeyModule_ProvideFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static String provide() {
        return (String) Preconditions.checkNotNullFromProvides(SguShopViewModel_HiltModules.KeyModule.provide());
    }

    private static final class InstanceHolder {
        private static final SguShopViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new SguShopViewModel_HiltModules_KeyModule_ProvideFactory();

        private InstanceHolder() {
        }
    }
}
