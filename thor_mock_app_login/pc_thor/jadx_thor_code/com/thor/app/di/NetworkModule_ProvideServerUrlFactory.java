package com.thor.app.di;

import com.thor.app.settings.Settings;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public final class NetworkModule_ProvideServerUrlFactory implements Factory<String> {
    private final Provider<Settings> settingsProvider;

    public NetworkModule_ProvideServerUrlFactory(Provider<Settings> settingsProvider) {
        this.settingsProvider = settingsProvider;
    }

    @Override // javax.inject.Provider
    public String get() {
        return provideServerUrl(this.settingsProvider.get());
    }

    public static NetworkModule_ProvideServerUrlFactory create(Provider<Settings> settingsProvider) {
        return new NetworkModule_ProvideServerUrlFactory(settingsProvider);
    }

    public static String provideServerUrl(Settings settings) {
        return (String) Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideServerUrl(settings));
    }
}
