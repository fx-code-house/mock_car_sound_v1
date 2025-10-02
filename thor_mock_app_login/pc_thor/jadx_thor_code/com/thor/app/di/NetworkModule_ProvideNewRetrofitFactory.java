package com.thor.app.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes2.dex */
public final class NetworkModule_ProvideNewRetrofitFactory implements Factory<Retrofit> {
    private final Provider<GsonConverterFactory> gsonConverterFactoryProvider;
    private final Provider<OkHttpClient> okHttpClientProvider;
    private final Provider<String> serverUrlProvider;

    public NetworkModule_ProvideNewRetrofitFactory(Provider<String> serverUrlProvider, Provider<GsonConverterFactory> gsonConverterFactoryProvider, Provider<OkHttpClient> okHttpClientProvider) {
        this.serverUrlProvider = serverUrlProvider;
        this.gsonConverterFactoryProvider = gsonConverterFactoryProvider;
        this.okHttpClientProvider = okHttpClientProvider;
    }

    @Override // javax.inject.Provider
    public Retrofit get() {
        return provideNewRetrofit(this.serverUrlProvider.get(), this.gsonConverterFactoryProvider.get(), this.okHttpClientProvider.get());
    }

    public static NetworkModule_ProvideNewRetrofitFactory create(Provider<String> serverUrlProvider, Provider<GsonConverterFactory> gsonConverterFactoryProvider, Provider<OkHttpClient> okHttpClientProvider) {
        return new NetworkModule_ProvideNewRetrofitFactory(serverUrlProvider, gsonConverterFactoryProvider, okHttpClientProvider);
    }

    public static Retrofit provideNewRetrofit(String serverUrl, GsonConverterFactory gsonConverterFactory, OkHttpClient okHttpClient) {
        return (Retrofit) Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideNewRetrofit(serverUrl, gsonConverterFactory, okHttpClient));
    }
}
