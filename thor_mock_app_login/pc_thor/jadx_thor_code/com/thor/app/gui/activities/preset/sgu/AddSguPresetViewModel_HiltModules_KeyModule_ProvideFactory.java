package com.thor.app.gui.activities.preset.sgu;

import com.thor.app.gui.activities.preset.sgu.AddSguPresetViewModel_HiltModules;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: classes3.dex */
public final class AddSguPresetViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
    @Override // javax.inject.Provider
    public String get() {
        return provide();
    }

    public static AddSguPresetViewModel_HiltModules_KeyModule_ProvideFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static String provide() {
        return (String) Preconditions.checkNotNullFromProvides(AddSguPresetViewModel_HiltModules.KeyModule.provide());
    }

    private static final class InstanceHolder {
        private static final AddSguPresetViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new AddSguPresetViewModel_HiltModules_KeyModule_ProvideFactory();

        private InstanceHolder() {
        }
    }
}
