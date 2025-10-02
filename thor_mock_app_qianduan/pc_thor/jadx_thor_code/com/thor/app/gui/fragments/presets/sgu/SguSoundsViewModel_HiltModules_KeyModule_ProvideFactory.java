package com.thor.app.gui.fragments.presets.sgu;

import com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel_HiltModules;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: classes3.dex */
public final class SguSoundsViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
    @Override // javax.inject.Provider
    public String get() {
        return provide();
    }

    public static SguSoundsViewModel_HiltModules_KeyModule_ProvideFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static String provide() {
        return (String) Preconditions.checkNotNullFromProvides(SguSoundsViewModel_HiltModules.KeyModule.provide());
    }

    private static final class InstanceHolder {
        private static final SguSoundsViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new SguSoundsViewModel_HiltModules_KeyModule_ProvideFactory();

        private InstanceHolder() {
        }
    }
}
