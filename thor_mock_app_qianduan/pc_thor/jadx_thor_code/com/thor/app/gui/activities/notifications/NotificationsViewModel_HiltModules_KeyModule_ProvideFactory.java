package com.thor.app.gui.activities.notifications;

import com.thor.app.gui.activities.notifications.NotificationsViewModel_HiltModules;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: classes3.dex */
public final class NotificationsViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
    @Override // javax.inject.Provider
    public String get() {
        return provide();
    }

    public static NotificationsViewModel_HiltModules_KeyModule_ProvideFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static String provide() {
        return (String) Preconditions.checkNotNullFromProvides(NotificationsViewModel_HiltModules.KeyModule.provide());
    }

    private static final class InstanceHolder {
        private static final NotificationsViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new NotificationsViewModel_HiltModules_KeyModule_ProvideFactory();

        private InstanceHolder() {
        }
    }
}
