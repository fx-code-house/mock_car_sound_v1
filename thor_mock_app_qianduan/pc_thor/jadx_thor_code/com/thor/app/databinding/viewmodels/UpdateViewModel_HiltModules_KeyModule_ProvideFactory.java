package com.thor.app.databinding.viewmodels;

import com.thor.app.databinding.viewmodels.UpdateViewModel_HiltModules;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: classes2.dex */
public final class UpdateViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
    @Override // javax.inject.Provider
    public String get() {
        return provide();
    }

    public static UpdateViewModel_HiltModules_KeyModule_ProvideFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static String provide() {
        return (String) Preconditions.checkNotNullFromProvides(UpdateViewModel_HiltModules.KeyModule.provide());
    }

    private static final class InstanceHolder {
        private static final UpdateViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new UpdateViewModel_HiltModules_KeyModule_ProvideFactory();

        private InstanceHolder() {
        }
    }
}
