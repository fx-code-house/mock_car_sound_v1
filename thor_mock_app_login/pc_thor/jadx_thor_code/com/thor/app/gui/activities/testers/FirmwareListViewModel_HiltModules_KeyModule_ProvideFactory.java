package com.thor.app.gui.activities.testers;

import com.thor.app.gui.activities.testers.FirmwareListViewModel_HiltModules;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: classes3.dex */
public final class FirmwareListViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
    @Override // javax.inject.Provider
    public String get() {
        return provide();
    }

    public static FirmwareListViewModel_HiltModules_KeyModule_ProvideFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static String provide() {
        return (String) Preconditions.checkNotNullFromProvides(FirmwareListViewModel_HiltModules.KeyModule.provide());
    }

    private static final class InstanceHolder {
        private static final FirmwareListViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new FirmwareListViewModel_HiltModules_KeyModule_ProvideFactory();

        private InstanceHolder() {
        }
    }
}
