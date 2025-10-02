package com.thor.app.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import okhttp3.logging.HttpLoggingInterceptor;

/* loaded from: classes2.dex */
public final class NetworkModule_ProvideLoggingInterceptorFactory implements Factory<HttpLoggingInterceptor> {
    private final Provider<HttpLoggingInterceptor.Logger> loggerProvider;

    public NetworkModule_ProvideLoggingInterceptorFactory(Provider<HttpLoggingInterceptor.Logger> loggerProvider) {
        this.loggerProvider = loggerProvider;
    }

    @Override // javax.inject.Provider
    public HttpLoggingInterceptor get() {
        return provideLoggingInterceptor(this.loggerProvider.get());
    }

    public static NetworkModule_ProvideLoggingInterceptorFactory create(Provider<HttpLoggingInterceptor.Logger> loggerProvider) {
        return new NetworkModule_ProvideLoggingInterceptorFactory(loggerProvider);
    }

    public static HttpLoggingInterceptor provideLoggingInterceptor(HttpLoggingInterceptor.Logger logger) {
        return (HttpLoggingInterceptor) Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideLoggingInterceptor(logger));
    }
}
