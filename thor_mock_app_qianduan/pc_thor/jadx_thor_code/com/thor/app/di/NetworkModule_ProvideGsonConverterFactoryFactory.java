package com.thor.app.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes2.dex */
public final class NetworkModule_ProvideGsonConverterFactoryFactory implements Factory<GsonConverterFactory> {
    @Override // javax.inject.Provider
    public GsonConverterFactory get() {
        return provideGsonConverterFactory();
    }

    public static NetworkModule_ProvideGsonConverterFactoryFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static GsonConverterFactory provideGsonConverterFactory() {
        return (GsonConverterFactory) Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideGsonConverterFactory());
    }

    private static final class InstanceHolder {
        private static final NetworkModule_ProvideGsonConverterFactoryFactory INSTANCE = new NetworkModule_ProvideGsonConverterFactoryFactory();

        private InstanceHolder() {
        }
    }
}
