package com.thor.app.di;

import com.thor.networkmodule.network.ApiService;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import retrofit2.Retrofit;

/* loaded from: classes2.dex */
public final class NetworkModule_ProvideApiServiceFactory implements Factory<ApiService> {
    private final Provider<Retrofit> retrofitProvider;

    public NetworkModule_ProvideApiServiceFactory(Provider<Retrofit> retrofitProvider) {
        this.retrofitProvider = retrofitProvider;
    }

    @Override // javax.inject.Provider
    public ApiService get() {
        return provideApiService(this.retrofitProvider.get());
    }

    public static NetworkModule_ProvideApiServiceFactory create(Provider<Retrofit> retrofitProvider) {
        return new NetworkModule_ProvideApiServiceFactory(retrofitProvider);
    }

    public static ApiService provideApiService(Retrofit retrofit) {
        return (ApiService) Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideApiService(retrofit));
    }
}
