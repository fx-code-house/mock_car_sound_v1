package com.thor.app.di;

import com.thor.app.room.ThorDatabase;
import com.thor.app.room.dao.InstalledSoundPackageDao;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public final class DatabaseModule_ProvideInstalledSoundPackageDaoFactory implements Factory<InstalledSoundPackageDao> {
    private final Provider<ThorDatabase> appDatabaseProvider;

    public DatabaseModule_ProvideInstalledSoundPackageDaoFactory(Provider<ThorDatabase> appDatabaseProvider) {
        this.appDatabaseProvider = appDatabaseProvider;
    }

    @Override // javax.inject.Provider
    public InstalledSoundPackageDao get() {
        return provideInstalledSoundPackageDao(this.appDatabaseProvider.get());
    }

    public static DatabaseModule_ProvideInstalledSoundPackageDaoFactory create(Provider<ThorDatabase> appDatabaseProvider) {
        return new DatabaseModule_ProvideInstalledSoundPackageDaoFactory(appDatabaseProvider);
    }

    public static InstalledSoundPackageDao provideInstalledSoundPackageDao(ThorDatabase appDatabase) {
        return (InstalledSoundPackageDao) Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideInstalledSoundPackageDao(appDatabase));
    }
}
