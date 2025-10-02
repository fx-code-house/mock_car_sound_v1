package com.thor.app.di;

import com.thor.app.room.ThorDatabase;
import com.thor.app.room.dao.ShopSguSoundPackageDao;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public final class DatabaseModule_ProvideShopSguSoundPackageDaoFactory implements Factory<ShopSguSoundPackageDao> {
    private final Provider<ThorDatabase> appDatabaseProvider;

    public DatabaseModule_ProvideShopSguSoundPackageDaoFactory(Provider<ThorDatabase> appDatabaseProvider) {
        this.appDatabaseProvider = appDatabaseProvider;
    }

    @Override // javax.inject.Provider
    public ShopSguSoundPackageDao get() {
        return provideShopSguSoundPackageDao(this.appDatabaseProvider.get());
    }

    public static DatabaseModule_ProvideShopSguSoundPackageDaoFactory create(Provider<ThorDatabase> appDatabaseProvider) {
        return new DatabaseModule_ProvideShopSguSoundPackageDaoFactory(appDatabaseProvider);
    }

    public static ShopSguSoundPackageDao provideShopSguSoundPackageDao(ThorDatabase appDatabase) {
        return (ShopSguSoundPackageDao) Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideShopSguSoundPackageDao(appDatabase));
    }
}
