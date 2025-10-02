package com.thor.app.services;

import com.thor.app.managers.PushNotificationManager;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class FmcService_MembersInjector implements MembersInjector<FmcService> {
    private final Provider<PushNotificationManager> pushNotificationManagerProvider;

    public FmcService_MembersInjector(Provider<PushNotificationManager> pushNotificationManagerProvider) {
        this.pushNotificationManagerProvider = pushNotificationManagerProvider;
    }

    public static MembersInjector<FmcService> create(Provider<PushNotificationManager> pushNotificationManagerProvider) {
        return new FmcService_MembersInjector(pushNotificationManagerProvider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(FmcService instance) {
        injectPushNotificationManager(instance, this.pushNotificationManagerProvider.get());
    }

    public static void injectPushNotificationManager(FmcService instance, PushNotificationManager pushNotificationManager) {
        instance.pushNotificationManager = pushNotificationManager;
    }
}
