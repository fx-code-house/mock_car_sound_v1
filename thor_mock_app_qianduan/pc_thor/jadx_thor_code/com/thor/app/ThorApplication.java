package com.thor.app;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import com.orhanobut.hawk.Hawk;
import com.thor.app.gui.dialog.CanFileManager;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.CarManager;
import com.thor.app.managers.SchemaEmergencySituationsManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.network.Api;
import com.thor.app.room.DatabaseMigrations;
import com.thor.app.room.ThorDatabase;
import com.thor.app.settings.Settings;
import dagger.hilt.android.HiltAndroidApp;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: ThorApplication.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0011\u001a\u00020\u0006J\u0006\u0010\u0012\u001a\u00020\bJ\u0006\u0010\u0013\u001a\u00020\nJ\u0006\u0010\u0014\u001a\u00020\u0004J\u0006\u0010\u0015\u001a\u00020\fJ\u0006\u0010\u0016\u001a\u00020\u000eJ\u0006\u0010\u0017\u001a\u00020\u0010J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0019H\u0002J\b\u0010\u001b\u001a\u00020\u0019H\u0002J\b\u0010\u001c\u001a\u00020\u0019H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/thor/app/ThorApplication;", "Landroid/app/Application;", "()V", "carManager", "Lcom/thor/app/managers/CarManager;", "mApi", "Lcom/thor/app/network/Api;", "mBleManager", "Lcom/thor/app/managers/BleManager;", "mCanFileManager", "Lcom/thor/app/gui/dialog/CanFileManager;", "mSchemaEmergencySituationsManager", "Lcom/thor/app/managers/SchemaEmergencySituationsManager;", "mThorDatabase", "Lcom/thor/app/room/ThorDatabase;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getApi", "getBleManager", "getCanFileManager", "getCarManager", "getSchemaEmergencySituationsManager", "getThorDatabase", "getUsersManager", "initDatabase", "", "initHawk", "initTimber", "onCreate", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@HiltAndroidApp
/* loaded from: classes.dex */
public final class ThorApplication extends Hilt_ThorApplication {
    private static volatile ThorApplication instance;
    private CarManager carManager;
    private Api mApi;
    private BleManager mBleManager;
    private CanFileManager mCanFileManager;
    private SchemaEmergencySituationsManager mSchemaEmergencySituationsManager;
    private ThorDatabase mThorDatabase;
    private UsersManager usersManager;

    private final void initTimber() {
    }

    @Override // com.thor.app.Hilt_ThorApplication, android.app.Application
    public void onCreate() {
        super.onCreate();
        instance = this;
        initDatabase();
        initTimber();
        initHawk();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Timber.INSTANCE.i("Formatted flash: %s", Boolean.valueOf(Settings.INSTANCE.isFormattedFlash()));
        Settings.saveIsCheckedEmrgencySituations(false);
        Settings.INSTANCE.saveLaunchApp(true);
        Settings.INSTANCE.saveCurrentLocale(getApplicationContext().getResources().getConfiguration().getLocales().get(0));
    }

    private final void initDatabase() {
        RoomDatabase.Builder builderDatabaseBuilder = Room.databaseBuilder(this, ThorDatabase.class, "database");
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
        this.mThorDatabase = (ThorDatabase) builderAddMigrations4.addMigrations(MIGRATION_5_6).allowMainThreadQueries().build();
    }

    public final ThorDatabase getThorDatabase() {
        ThorDatabase thorDatabase = this.mThorDatabase;
        if (thorDatabase != null) {
            return thorDatabase;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mThorDatabase");
        return null;
    }

    public final Api getApi() {
        if (this.mApi == null) {
            this.mApi = new Api(getApplicationContext());
        }
        Api api = this.mApi;
        if (api != null) {
            return api;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mApi");
        return null;
    }

    private final void initHawk() {
        Hawk.init(this).build();
    }

    public final BleManager getBleManager() {
        if (this.mBleManager == null) {
            this.mBleManager = new BleManager(this);
        }
        BleManager bleManager = this.mBleManager;
        if (bleManager != null) {
            return bleManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mBleManager");
        return null;
    }

    public final SchemaEmergencySituationsManager getSchemaEmergencySituationsManager() {
        if (this.mSchemaEmergencySituationsManager == null) {
            this.mSchemaEmergencySituationsManager = new SchemaEmergencySituationsManager(this);
        }
        SchemaEmergencySituationsManager schemaEmergencySituationsManager = this.mSchemaEmergencySituationsManager;
        if (schemaEmergencySituationsManager != null) {
            return schemaEmergencySituationsManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mSchemaEmergencySituationsManager");
        return null;
    }

    public final CanFileManager getCanFileManager() {
        if (this.mCanFileManager == null) {
            this.mCanFileManager = new CanFileManager(this);
        }
        CanFileManager canFileManager = this.mCanFileManager;
        if (canFileManager != null) {
            return canFileManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mCanFileManager");
        return null;
    }

    public final UsersManager getUsersManager() {
        if (this.usersManager == null) {
            ThorApplication thorApplication = this;
            ThorDatabase thorDatabase = this.mThorDatabase;
            if (thorDatabase == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mThorDatabase");
                thorDatabase = null;
            }
            this.usersManager = new UsersManager(thorApplication, thorDatabase);
        }
        UsersManager usersManager = this.usersManager;
        if (usersManager != null) {
            return usersManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("usersManager");
        return null;
    }

    public final CarManager getCarManager() {
        if (this.carManager == null) {
            this.carManager = new CarManager(this);
        }
        CarManager carManager = this.carManager;
        if (carManager != null) {
            return carManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("carManager");
        return null;
    }
}
