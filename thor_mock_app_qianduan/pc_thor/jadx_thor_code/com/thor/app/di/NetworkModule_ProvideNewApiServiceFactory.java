package com.thor.app.di;

import com.thor.networkmodule.network.ApiServiceNew;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import retrofit2.Retrofit;

/* loaded from: classes2.dex */
public final class NetworkModule_ProvideNewApiServiceFactory implements Factory<ApiServiceNew> {
    private final Provider<Retrofit> retrofitProvider;

    public NetworkModule_ProvideNewApiServiceFactory(Provider<Retrofit> retrofitProvider) {
        this.retrofitProvider = retrofitProvider;
    }

    @Override // javax.inject.Provider
    public ApiServiceNew get() {
        return provideNewApiService(this.retrofitProvider.get());
    }

    public static NetworkModule_ProvideNewApiServiceFactory create(Provider<Retrofit> retrofitProvider) {
        return new NetworkModule_ProvideNewApiServiceFactory(retrofitProvider);
    }

    public static ApiServiceNew provideNewApiService(Retrofit retrofit) {
        return (ApiServiceNew) Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideNewApiService(retrofit));
    }
}
