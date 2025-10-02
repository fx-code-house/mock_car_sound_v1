package com.thor.app.gui.activities.shop.main;

import com.thor.app.billing.BillingManager;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class SubscriptionCheckActivity_MembersInjector implements MembersInjector<SubscriptionCheckActivity> {
    private final Provider<BillingManager> billingManagerProvider;

    public SubscriptionCheckActivity_MembersInjector(Provider<BillingManager> billingManagerProvider) {
        this.billingManagerProvider = billingManagerProvider;
    }

    public static MembersInjector<SubscriptionCheckActivity> create(Provider<BillingManager> billingManagerProvider) {
        return new SubscriptionCheckActivity_MembersInjector(billingManagerProvider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(SubscriptionCheckActivity instance) {
        injectBillingManager(instance, this.billingManagerProvider.get());
    }

    public static void injectBillingManager(SubscriptionCheckActivity instance, BillingManager billingManager) {
        instance.billingManager = billingManager;
    }
}
