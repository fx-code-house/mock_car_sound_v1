package com.thor.app.gui.fragments.shop.main;

import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class MainShopFragment_MembersInjector implements MembersInjector<MainShopFragment> {
    private final Provider<ThorDatabase> databaseProvider;
    private final Provider<UsersManager> usersManagerProvider;

    public MainShopFragment_MembersInjector(Provider<ThorDatabase> databaseProvider, Provider<UsersManager> usersManagerProvider) {
        this.databaseProvider = databaseProvider;
        this.usersManagerProvider = usersManagerProvider;
    }

    public static MembersInjector<MainShopFragment> create(Provider<ThorDatabase> databaseProvider, Provider<UsersManager> usersManagerProvider) {
        return new MainShopFragment_MembersInjector(databaseProvider, usersManagerProvider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(MainShopFragment instance) {
        injectDatabase(instance, this.databaseProvider.get());
        injectUsersManager(instance, this.usersManagerProvider.get());
    }

    public static void injectDatabase(MainShopFragment instance, ThorDatabase database) {
        instance.database = database;
    }

    public static void injectUsersManager(MainShopFragment instance, UsersManager usersManager) {
        instance.usersManager = usersManager;
    }
}
