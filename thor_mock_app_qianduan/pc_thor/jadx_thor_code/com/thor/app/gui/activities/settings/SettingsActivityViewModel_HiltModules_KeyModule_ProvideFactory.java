package com.thor.app.gui.activities.settings;

import com.thor.app.gui.activities.settings.SettingsActivityViewModel_HiltModules;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: classes3.dex */
public final class SettingsActivityViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
    @Override // javax.inject.Provider
    public String get() {
        return provide();
    }

    public static SettingsActivityViewModel_HiltModules_KeyModule_ProvideFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static String provide() {
        return (String) Preconditions.checkNotNullFromProvides(SettingsActivityViewModel_HiltModules.KeyModule.provide());
    }

    private static final class InstanceHolder {
        private static final SettingsActivityViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new SettingsActivityViewModel_HiltModules_KeyModule_ProvideFactory();

        private InstanceHolder() {
        }
    }
}
