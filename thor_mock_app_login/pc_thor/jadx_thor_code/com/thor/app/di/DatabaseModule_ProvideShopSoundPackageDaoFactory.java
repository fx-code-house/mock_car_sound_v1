package com.thor.app.di;

import com.thor.app.room.ThorDatabase;
import com.thor.app.room.dao.ShopSoundPackageDao;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public final class DatabaseModule_ProvideShopSoundPackageDaoFactory implements Factory<ShopSoundPackageDao> {
    private final Provider<ThorDatabase> appDatabaseProvider;

    public DatabaseModule_ProvideShopSoundPackageDaoFactory(Provider<ThorDatabase> appDatabaseProvider) {
        this.appDatabaseProvider = appDatabaseProvider;
    }

    @Override // javax.inject.Provider
    public ShopSoundPackageDao get() {
        return provideShopSoundPackageDao(this.appDatabaseProvider.get());
    }

    public static DatabaseModule_ProvideShopSoundPackageDaoFactory create(Provider<ThorDatabase> appDatabaseProvider) {
        return new DatabaseModule_ProvideShopSoundPackageDaoFactory(appDatabaseProvider);
    }

    public static ShopSoundPackageDao provideShopSoundPackageDao(ThorDatabase appDatabase) {
        return (ShopSoundPackageDao) Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideShopSoundPackageDao(appDatabase));
    }
}
