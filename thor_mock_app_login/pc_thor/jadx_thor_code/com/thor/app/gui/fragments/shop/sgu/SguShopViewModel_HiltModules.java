package com.thor.app.gui.fragments.shop.sgu;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;
import dagger.multibindings.StringKey;

/* loaded from: classes3.dex */
public final class SguShopViewModel_HiltModules {
    private SguShopViewModel_HiltModules() {
    }

    @Module
    public static abstract class BindsModule {
        @Binds
        @IntoMap
        @StringKey("com.thor.app.gui.fragments.shop.sgu.SguShopViewModel")
        public abstract ViewModel binds(SguShopViewModel vm);

        private BindsModule() {
        }
    }

    @Module
    public static final class KeyModule {
        @Provides
        @IntoSet
        public static String provide() {
            return "com.thor.app.gui.fragments.shop.sgu.SguShopViewModel";
        }

        private KeyModule() {
        }
    }
}
