package com.thor.app.gui.activities.preset.sgu;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;
import dagger.multibindings.StringKey;

/* loaded from: classes3.dex */
public final class AddSguPresetViewModel_HiltModules {
    private AddSguPresetViewModel_HiltModules() {
    }

    @Module
    public static abstract class BindsModule {
        @Binds
        @IntoMap
        @StringKey("com.thor.app.gui.activities.preset.sgu.AddSguPresetViewModel")
        public abstract ViewModel binds(AddSguPresetViewModel vm);

        private BindsModule() {
        }
    }

    @Module
    public static final class KeyModule {
        @Provides
        @IntoSet
        public static String provide() {
            return "com.thor.app.gui.activities.preset.sgu.AddSguPresetViewModel";
        }

        private KeyModule() {
        }
    }
}
