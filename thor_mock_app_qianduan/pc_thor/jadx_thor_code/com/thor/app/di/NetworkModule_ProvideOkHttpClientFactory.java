package com.thor.app.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/* loaded from: classes2.dex */
public final class NetworkModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
    private final Provider<HttpLoggingInterceptor> loggingInterceptorProvider;

    public NetworkModule_ProvideOkHttpClientFactory(Provider<HttpLoggingInterceptor> loggingInterceptorProvider) {
        this.loggingInterceptorProvider = loggingInterceptorProvider;
    }

    @Override // javax.inject.Provider
    public OkHttpClient get() {
        return provideOkHttpClient(this.loggingInterceptorProvider.get());
    }

    public static NetworkModule_ProvideOkHttpClientFactory create(Provider<HttpLoggingInterceptor> loggingInterceptorProvider) {
        return new NetworkModule_ProvideOkHttpClientFactory(loggingInterceptorProvider);
    }

    public static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return (OkHttpClient) Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOkHttpClient(loggingInterceptor));
    }
}
