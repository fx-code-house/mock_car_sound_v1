package com.thor.app.gui.activities.settings;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;
import dagger.multibindings.StringKey;

/* loaded from: classes3.dex */
public final class SettingsActivityViewModel_HiltModules {
    private SettingsActivityViewModel_HiltModules() {
    }

    @Module
    public static abstract class BindsModule {
        @Binds
        @IntoMap
        @StringKey("com.thor.app.gui.activities.settings.SettingsActivityViewModel")
        public abstract ViewModel binds(SettingsActivityViewModel vm);

        private BindsModule() {
        }
    }

    @Module
    public static final class KeyModule {
        @Provides
        @IntoSet
        public static String provide() {
            return "com.thor.app.gui.activities.settings.SettingsActivityViewModel";
        }

        private KeyModule() {
        }
    }
}
