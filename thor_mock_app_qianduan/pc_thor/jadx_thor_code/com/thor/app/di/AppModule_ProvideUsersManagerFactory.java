package com.thor.app.di;

import android.content.Context;
import com.thor.app.managers.UsersManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public final class AppModule_ProvideUsersManagerFactory implements Factory<UsersManager> {
    private final Provider<Context> contextProvider;

    public AppModule_ProvideUsersManagerFactory(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    @Override // javax.inject.Provider
    public UsersManager get() {
        return provideUsersManager(this.contextProvider.get());
    }

    public static AppModule_ProvideUsersManagerFactory create(Provider<Context> contextProvider) {
        return new AppModule_ProvideUsersManagerFactory(contextProvider);
    }

    public static UsersManager provideUsersManager(Context context) {
        return (UsersManager) Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideUsersManager(context));
    }
}
