package com.thor.app.gui.activities.shop.main;

import com.thor.app.billing.BillingManager;
import com.thor.app.managers.UsersManager;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class SubscriptionsActivity_MembersInjector implements MembersInjector<SubscriptionsActivity> {
    private final Provider<BillingManager> billingManagerProvider;
    private final Provider<UsersManager> usersManagerProvider;

    public SubscriptionsActivity_MembersInjector(Provider<BillingManager> billingManagerProvider, Provider<UsersManager> usersManagerProvider) {
        this.billingManagerProvider = billingManagerProvider;
        this.usersManagerProvider = usersManagerProvider;
    }

    public static MembersInjector<SubscriptionsActivity> create(Provider<BillingManager> billingManagerProvider, Provider<UsersManager> usersManagerProvider) {
        return new SubscriptionsActivity_MembersInjector(billingManagerProvider, usersManagerProvider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(SubscriptionsActivity instance) {
        SubscriptionCheckActivity_MembersInjector.injectBillingManager(instance, this.billingManagerProvider.get());
        injectUsersManager(instance, this.usersManagerProvider.get());
    }

    public static void injectUsersManager(SubscriptionsActivity instance, UsersManager usersManager) {
        instance.usersManager = usersManager;
    }
}
