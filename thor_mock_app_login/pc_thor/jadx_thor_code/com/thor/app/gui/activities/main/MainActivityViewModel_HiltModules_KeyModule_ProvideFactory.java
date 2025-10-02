package com.thor.app.gui.activities.main;

import com.thor.app.gui.activities.main.MainActivityViewModel_HiltModules;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: classes3.dex */
public final class MainActivityViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
    @Override // javax.inject.Provider
    public String get() {
        return provide();
    }

    public static MainActivityViewModel_HiltModules_KeyModule_ProvideFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static String provide() {
        return (String) Preconditions.checkNotNullFromProvides(MainActivityViewModel_HiltModules.KeyModule.provide());
    }

    private static final class InstanceHolder {
        private static final MainActivityViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new MainActivityViewModel_HiltModules_KeyModule_ProvideFactory();

        private InstanceHolder() {
        }
    }
}
