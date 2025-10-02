package com.thor.app.di;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import okhttp3.logging.HttpLoggingInterceptor;

/* loaded from: classes2.dex */
public final class NetworkModule_ProviderHttpLoggingInterceptorLoggerFactory implements Factory<HttpLoggingInterceptor.Logger> {
    private final Provider<Context> contextProvider;

    public NetworkModule_ProviderHttpLoggingInterceptorLoggerFactory(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    @Override // javax.inject.Provider
    public HttpLoggingInterceptor.Logger get() {
        return providerHttpLoggingInterceptorLogger(this.contextProvider.get());
    }

    public static NetworkModule_ProviderHttpLoggingInterceptorLoggerFactory create(Provider<Context> contextProvider) {
        return new NetworkModule_ProviderHttpLoggingInterceptorLoggerFactory(contextProvider);
    }

    public static HttpLoggingInterceptor.Logger providerHttpLoggingInterceptorLogger(Context context) {
        return (HttpLoggingInterceptor.Logger) Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.providerHttpLoggingInterceptorLogger(context));
    }
}
