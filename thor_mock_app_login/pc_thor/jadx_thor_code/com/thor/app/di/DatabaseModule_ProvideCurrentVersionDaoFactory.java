package com.thor.app.di;

import com.thor.app.room.ThorDatabase;
import com.thor.app.room.dao.CurrentVersionDao;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public final class DatabaseModule_ProvideCurrentVersionDaoFactory implements Factory<CurrentVersionDao> {
    private final Provider<ThorDatabase> appDatabaseProvider;

    public DatabaseModule_ProvideCurrentVersionDaoFactory(Provider<ThorDatabase> appDatabaseProvider) {
        this.appDatabaseProvider = appDatabaseProvider;
    }

    @Override // javax.inject.Provider
    public CurrentVersionDao get() {
        return provideCurrentVersionDao(this.appDatabaseProvider.get());
    }

    public static DatabaseModule_ProvideCurrentVersionDaoFactory create(Provider<ThorDatabase> appDatabaseProvider) {
        return new DatabaseModule_ProvideCurrentVersionDaoFactory(appDatabaseProvider);
    }

    public static CurrentVersionDao provideCurrentVersionDao(ThorDatabase appDatabase) {
        return (CurrentVersionDao) Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCurrentVersionDao(appDatabase));
    }
}
