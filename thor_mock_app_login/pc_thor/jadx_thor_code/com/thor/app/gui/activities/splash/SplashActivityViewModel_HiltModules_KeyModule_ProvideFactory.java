package com.thor.app.gui.activities.splash;

import com.thor.app.gui.activities.splash.SplashActivityViewModel_HiltModules;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: classes3.dex */
public final class SplashActivityViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
    @Override // javax.inject.Provider
    public String get() {
        return provide();
    }

    public static SplashActivityViewModel_HiltModules_KeyModule_ProvideFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static String provide() {
        return (String) Preconditions.checkNotNullFromProvides(SplashActivityViewModel_HiltModules.KeyModule.provide());
    }

    private static final class InstanceHolder {
        private static final SplashActivityViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new SplashActivityViewModel_HiltModules_KeyModule_ProvideFactory();

        private InstanceHolder() {
        }
    }
}
