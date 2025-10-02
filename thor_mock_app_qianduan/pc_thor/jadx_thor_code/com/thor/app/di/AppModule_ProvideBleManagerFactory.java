package com.thor.app.di;

import android.content.Context;
import com.thor.app.managers.BleManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public final class AppModule_ProvideBleManagerFactory implements Factory<BleManager> {
    private final Provider<Context> contextProvider;

    public AppModule_ProvideBleManagerFactory(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    @Override // javax.inject.Provider
    public BleManager get() {
        return provideBleManager(this.contextProvider.get());
    }

    public static AppModule_ProvideBleManagerFactory create(Provider<Context> contextProvider) {
        return new AppModule_ProvideBleManagerFactory(contextProvider);
    }

    public static BleManager provideBleManager(Context context) {
        return (BleManager) Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBleManager(context));
    }
}
