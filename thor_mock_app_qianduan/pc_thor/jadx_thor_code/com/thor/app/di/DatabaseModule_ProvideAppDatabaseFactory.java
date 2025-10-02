package com.thor.app.di;

import android.content.Context;
import com.thor.app.room.ThorDatabase;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes2.dex */
public final class DatabaseModule_ProvideAppDatabaseFactory implements Factory<ThorDatabase> {
    private final Provider<Context> contextProvider;

    public DatabaseModule_ProvideAppDatabaseFactory(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    @Override // javax.inject.Provider
    public ThorDatabase get() {
        return provideAppDatabase(this.contextProvider.get());
    }

    public static DatabaseModule_ProvideAppDatabaseFactory create(Provider<Context> contextProvider) {
        return new DatabaseModule_ProvideAppDatabaseFactory(contextProvider);
    }

    public static ThorDatabase provideAppDatabase(Context context) {
        return (ThorDatabase) Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAppDatabase(context));
    }
}
