package com.thor.app.gui.dialog;

import dagger.internal.Factory;

/* loaded from: classes3.dex */
public final class FormatProgressViewModel_Factory implements Factory<FormatProgressViewModel> {
    @Override // javax.inject.Provider
    public FormatProgressViewModel get() {
        return newInstance();
    }

    public static FormatProgressViewModel_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static FormatProgressViewModel newInstance() {
        return new FormatProgressViewModel();
    }

    private static final class InstanceHolder {
        private static final FormatProgressViewModel_Factory INSTANCE = new FormatProgressViewModel_Factory();

        private InstanceHolder() {
        }
    }
}
