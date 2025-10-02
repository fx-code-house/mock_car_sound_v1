package com.thor.app.di;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import com.thor.app.room.DatabaseMigrations;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.dao.CurrentVersionDao;
import com.thor.app.room.dao.InstalledSoundPackageDao;
import com.thor.app.room.dao.ShopSguSoundPackageDao;
import com.thor.app.room.dao.ShopSoundPackageDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Singleton;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DatabaseModule.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\u0004H\u0007¨\u0006\u0010"}, d2 = {"Lcom/thor/app/di/DatabaseModule;", "", "()V", "provideAppDatabase", "Lcom/thor/app/room/ThorDatabase;", "context", "Landroid/content/Context;", "provideCurrentVersionDao", "Lcom/thor/app/room/dao/CurrentVersionDao;", "appDatabase", "provideInstalledSoundPackageDao", "Lcom/thor/app/room/dao/InstalledSoundPackageDao;", "provideShopSguSoundPackageDao", "Lcom/thor/app/room/dao/ShopSguSoundPackageDao;", "provideShopSoundPackageDao", "Lcom/thor/app/room/dao/ShopSoundPackageDao;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Module
/* loaded from: classes2.dex */
public final class DatabaseModule {
    public static final DatabaseModule INSTANCE = new DatabaseModule();

    private DatabaseModule() {
    }

    @Provides
    @Singleton
    public final ThorDatabase provideAppDatabase(@ApplicationContext Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        RoomDatabase.Builder builderDatabaseBuilder = Room.databaseBuilder(context, ThorDatabase.class, "database");
        Migration MIGRATION_1_2 = DatabaseMigrations.MIGRATION_1_2;
        Intrinsics.checkNotNullExpressionValue(MIGRATION_1_2, "MIGRATION_1_2");
        RoomDatabase.Builder builderAddMigrations = builderDatabaseBuilder.addMigrations(MIGRATION_1_2);
        Migration MIGRATION_2_3 = DatabaseMigrations.MIGRATION_2_3;
        Intrinsics.checkNotNullExpressionValue(MIGRATION_2_3, "MIGRATION_2_3");
        RoomDatabase.Builder builderAddMigrations2 = builderAddMigrations.addMigrations(MIGRATION_2_3);
        Migration MIGRATION_3_4 = DatabaseMigrations.MIGRATION_3_4;
        Intrinsics.checkNotNullExpressionValue(MIGRATION_3_4, "MIGRATION_3_4");
        RoomDatabase.Builder builderAddMigrations3 = builderAddMigrations2.addMigrations(MIGRATION_3_4);
        Migration MIGRATION_4_5 = DatabaseMigrations.MIGRATION_4_5;
        Intrinsics.checkNotNullExpressionValue(MIGRATION_4_5, "MIGRATION_4_5");
        RoomDatabase.Builder builderAddMigrations4 = builderAddMigrations3.addMigrations(MIGRATION_4_5);
        Migration MIGRATION_5_6 = DatabaseMigrations.MIGRATION_5_6;
        Intrinsics.checkNotNullExpressionValue(MIGRATION_5_6, "MIGRATION_5_6");
        RoomDatabase.Builder builderAddMigrations5 = builderAddMigrations4.addMigrations(MIGRATION_5_6);
        Migration MIGRATION_6_7 = DatabaseMigrations.MIGRATION_6_7;
        Intrinsics.checkNotNullExpressionValue(MIGRATION_6_7, "MIGRATION_6_7");
        RoomDatabase.Builder builderAddMigrations6 = builderAddMigrations5.addMigrations(MIGRATION_6_7);
        Migration MIGRATION_7_8 = DatabaseMigrations.MIGRATION_7_8;
        Intrinsics.checkNotNullExpressionValue(MIGRATION_7_8, "MIGRATION_7_8");
        RoomDatabase.Builder builderAddMigrations7 = builderAddMigrations6.addMigrations(MIGRATION_7_8);
        Migration MIGRATION_8_9 = DatabaseMigrations.MIGRATION_8_9;
        Intrinsics.checkNotNullExpressionValue(MIGRATION_8_9, "MIGRATION_8_9");
        return (ThorDatabase) builderAddMigrations7.addMigrations(MIGRATION_8_9).fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    public final ShopSoundPackageDao provideShopSoundPackageDao(ThorDatabase appDatabase) {
        Intrinsics.checkNotNullParameter(appDatabase, "appDatabase");
        return appDatabase.shopSoundPackageDao();
    }

    @Provides
    @Singleton
    public final InstalledSoundPackageDao provideInstalledSoundPackageDao(ThorDatabase appDatabase) {
        Intrinsics.checkNotNullParameter(appDatabase, "appDatabase");
        return appDatabase.installedSoundPackageDao();
    }

    @Provides
    @Singleton
    public final ShopSguSoundPackageDao provideShopSguSoundPackageDao(ThorDatabase appDatabase) {
        Intrinsics.checkNotNullParameter(appDatabase, "appDatabase");
        return appDatabase.shopSguSoundPackageDao();
    }

    @Provides
    @Singleton
    public final CurrentVersionDao provideCurrentVersionDao(ThorDatabase appDatabase) {
        Intrinsics.checkNotNullParameter(appDatabase, "appDatabase");
        return appDatabase.currentVersionDao();
    }
}
