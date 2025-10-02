package com.thor.app.di;

import android.content.Context;
import com.thor.app.settings.Settings;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public final class AppModule_ProvideSettingsFactory implements Factory<Settings> {
    private final Provider<Context> contextProvider;

    public AppModule_ProvideSettingsFactory(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    @Override // javax.inject.Provider
    public Settings get() {
        return provideSettings(this.contextProvider.get());
    }

    public static AppModule_ProvideSettingsFactory create(Provider<Context> contextProvider) {
        return new AppModule_ProvideSettingsFactory(contextProvider);
    }

    public static Settings provideSettings(Context context) {
        return (Settings) Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideSettings(context));
    }
}
