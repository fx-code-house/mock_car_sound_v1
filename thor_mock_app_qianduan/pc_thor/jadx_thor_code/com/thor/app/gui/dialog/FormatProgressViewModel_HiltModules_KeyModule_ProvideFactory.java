package com.thor.app.gui.dialog;

import com.thor.app.gui.dialog.FormatProgressViewModel_HiltModules;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: classes3.dex */
public final class FormatProgressViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
    @Override // javax.inject.Provider
    public String get() {
        return provide();
    }

    public static FormatProgressViewModel_HiltModules_KeyModule_ProvideFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static String provide() {
        return (String) Preconditions.checkNotNullFromProvides(FormatProgressViewModel_HiltModules.KeyModule.provide());
    }

    private static final class InstanceHolder {
        private static final FormatProgressViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new FormatProgressViewModel_HiltModules_KeyModule_ProvideFactory();

        private InstanceHolder() {
        }
    }
}
