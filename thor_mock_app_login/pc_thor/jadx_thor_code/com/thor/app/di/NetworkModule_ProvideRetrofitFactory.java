package com.thor.app.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes2.dex */
public final class NetworkModule_ProvideRetrofitFactory implements Factory<Retrofit> {
    private final Provider<GsonConverterFactory> gsonConverterFactoryProvider;
    private final Provider<OkHttpClient> okHttpClientProvider;
    private final Provider<RxJava2CallAdapterFactory> rxJavaToCallAdapterFactoryProvider;
    private final Provider<String> serverUrlProvider;

    public NetworkModule_ProvideRetrofitFactory(Provider<String> serverUrlProvider, Provider<GsonConverterFactory> gsonConverterFactoryProvider, Provider<RxJava2CallAdapterFactory> rxJavaToCallAdapterFactoryProvider, Provider<OkHttpClient> okHttpClientProvider) {
        this.serverUrlProvider = serverUrlProvider;
        this.gsonConverterFactoryProvider = gsonConverterFactoryProvider;
        this.rxJavaToCallAdapterFactoryProvider = rxJavaToCallAdapterFactoryProvider;
        this.okHttpClientProvider = okHttpClientProvider;
    }

    @Override // javax.inject.Provider
    public Retrofit get() {
        return provideRetrofit(this.serverUrlProvider.get(), this.gsonConverterFactoryProvider.get(), this.rxJavaToCallAdapterFactoryProvider.get(), this.okHttpClientProvider.get());
    }

    public static NetworkModule_ProvideRetrofitFactory create(Provider<String> serverUrlProvider, Provider<GsonConverterFactory> gsonConverterFactoryProvider, Provider<RxJava2CallAdapterFactory> rxJavaToCallAdapterFactoryProvider, Provider<OkHttpClient> okHttpClientProvider) {
        return new NetworkModule_ProvideRetrofitFactory(serverUrlProvider, gsonConverterFactoryProvider, rxJavaToCallAdapterFactoryProvider, okHttpClientProvider);
    }

    public static Retrofit provideRetrofit(String serverUrl, GsonConverterFactory gsonConverterFactory, RxJava2CallAdapterFactory rxJavaToCallAdapterFactory, OkHttpClient okHttpClient) {
        return (Retrofit) Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideRetrofit(serverUrl, gsonConverterFactory, rxJavaToCallAdapterFactory, okHttpClient));
    }
}
