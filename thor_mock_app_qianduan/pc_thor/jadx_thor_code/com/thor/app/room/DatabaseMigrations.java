package com.thor.app.room;

import android.database.SQLException;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/* loaded from: classes3.dex */
public final class DatabaseMigrations {
    public static final Migration MIGRATION_1_2;
    public static final Migration MIGRATION_2_3;
    public static final Migration MIGRATION_3_4;
    public static final Migration MIGRATION_4_5;
    public static final Migration MIGRATION_5_6;
    public static final Migration MIGRATION_6_7;
    public static final Migration MIGRATION_7_8;
    public static final Migration MIGRATION_8_9 = new Migration(8, 9) { // from class: com.thor.app.room.DatabaseMigrations.8
        @Override // androidx.room.migration.Migration
        public void migrate(SupportSQLiteDatabase database) throws SQLException {
            database.execSQL("ALTER TABLE `InstalledSoundPackages` ADD COLUMN `damage_pck` INTEGER NOT NULL DEFAULT 0");
        }
    };

    static {
        int i = 2;
        MIGRATION_1_2 = new Migration(1, i) { // from class: com.thor.app.room.DatabaseMigrations.1
            @Override // androidx.room.migration.Migration
            public void migrate(final SupportSQLiteDatabase database) throws SQLException {
                database.execSQL("CREATE TABLE IF NOT EXISTS `InstalledSoundPackages` (`package_id` INTEGER NOT NULL,`version_id` INTEGER NOT NULL, `mode` INTEGER NOT NULL, PRIMARY KEY(`package_id`))");
            }
        };
        int i2 = 3;
        MIGRATION_2_3 = new Migration(i, i2) { // from class: com.thor.app.room.DatabaseMigrations.2
            @Override // androidx.room.migration.Migration
            public void migrate(final SupportSQLiteDatabase database) throws SQLException {
                database.execSQL("CREATE TABLE IF NOT EXISTS `InstalledSguSounds` (`sound_id` INTEGER NOT NULL,`is_favorite` INTEGER NOT NULL, PRIMARY KEY(`sound_id`))");
                database.execSQL("CREATE TABLE IF NOT EXISTS `ShopSguSoundPackages` (`id` INTEGER NOT NULL, `SetName` TEXT,`SetImageUrl` TEXT,`SetStatus` INTEGER NOT NULL, `Price` INTEGER NOT NULL, PRIMARY KEY(`id`))");
            }
        };
        int i3 = 4;
        MIGRATION_3_4 = new Migration(i2, i3) { // from class: com.thor.app.room.DatabaseMigrations.3
            @Override // androidx.room.migration.Migration
            public void migrate(SupportSQLiteDatabase database) {
            }
        };
        int i4 = 5;
        MIGRATION_4_5 = new Migration(i3, i4) { // from class: com.thor.app.room.DatabaseMigrations.4
            @Override // androidx.room.migration.Migration
            public void migrate(SupportSQLiteDatabase database) throws SQLException {
                database.execSQL("DROP TABLE IF EXISTS `InstalledSguSounds`");
            }
        };
        int i5 = 6;
        MIGRATION_5_6 = new Migration(i4, i5) { // from class: com.thor.app.room.DatabaseMigrations.5
            @Override // androidx.room.migration.Migration
            public void migrate(SupportSQLiteDatabase database) throws SQLException {
                database.execSQL("CREATE TABLE IF NOT EXISTS `currentversions`(`id` INTEGER PRIMARY KEY AUTOINCREMENT, `versionOfFirmware` TEXT NOT NULL, `versionOfHardware` TEXT NOT NULL)");
            }
        };
        int i6 = 7;
        MIGRATION_6_7 = new Migration(i5, i6) { // from class: com.thor.app.room.DatabaseMigrations.6
            @Override // androidx.room.migration.Migration
            public void migrate(SupportSQLiteDatabase database) {
            }
        };
        MIGRATION_7_8 = new Migration(i5, i6) { // from class: com.thor.app.room.DatabaseMigrations.7
            @Override // androidx.room.migration.Migration
            public void migrate(SupportSQLiteDatabase database) {
            }
        };
    }
}
