package com.thor.app.di;

import android.content.Context;
import com.thor.app.billing.BillingManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public final class AppModule_ProvideBillingManagerFactory implements Factory<BillingManager> {
    private final Provider<Context> contextProvider;

    public AppModule_ProvideBillingManagerFactory(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    @Override // javax.inject.Provider
    public BillingManager get() {
        return provideBillingManager(this.contextProvider.get());
    }

    public static AppModule_ProvideBillingManagerFactory create(Provider<Context> contextProvider) {
        return new AppModule_ProvideBillingManagerFactory(contextProvider);
    }

    public static BillingManager provideBillingManager(Context context) {
        return (BillingManager) Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBillingManager(context));
    }
}
