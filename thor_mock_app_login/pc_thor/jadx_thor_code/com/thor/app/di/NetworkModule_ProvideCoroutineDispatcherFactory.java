package com.thor.app.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes2.dex */
public final class NetworkModule_ProvideCoroutineDispatcherFactory implements Factory<CoroutineDispatcher> {
    @Override // javax.inject.Provider
    public CoroutineDispatcher get() {
        return provideCoroutineDispatcher();
    }

    public static NetworkModule_ProvideCoroutineDispatcherFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static CoroutineDispatcher provideCoroutineDispatcher() {
        return (CoroutineDispatcher) Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideCoroutineDispatcher());
    }

    private static final class InstanceHolder {
        private static final NetworkModule_ProvideCoroutineDispatcherFactory INSTANCE = new NetworkModule_ProvideCoroutineDispatcherFactory();

        private InstanceHolder() {
        }
    }
}
