package com.thor.app.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/* loaded from: classes2.dex */
public final class NetworkModule_ProvideRxJavaToCallAdapterFactoryFactory implements Factory<RxJava2CallAdapterFactory> {
    @Override // javax.inject.Provider
    public RxJava2CallAdapterFactory get() {
        return provideRxJavaToCallAdapterFactory();
    }

    public static NetworkModule_ProvideRxJavaToCallAdapterFactoryFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static RxJava2CallAdapterFactory provideRxJavaToCallAdapterFactory() {
        return (RxJava2CallAdapterFactory) Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideRxJavaToCallAdapterFactory());
    }

    private static final class InstanceHolder {
        private static final NetworkModule_ProvideRxJavaToCallAdapterFactoryFactory INSTANCE = new NetworkModule_ProvideRxJavaToCallAdapterFactoryFactory();

        private InstanceHolder() {
        }
    }
}
