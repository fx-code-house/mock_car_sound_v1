package com.thor.app.room;

import android.database.SQLException;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.thor.app.room.dao.CurrentVersionDao;
import com.thor.app.room.dao.CurrentVersionDao_Impl;
import com.thor.app.room.dao.InstalledSoundPackageDao;
import com.thor.app.room.dao.InstalledSoundPackageDao_Impl;
import com.thor.app.room.dao.ShopSguSoundPackageDao;
import com.thor.app.room.dao.ShopSguSoundPackageDao_Impl;
import com.thor.app.room.dao.ShopSoundPackageDao;
import com.thor.app.room.dao.ShopSoundPackageDao_Impl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public final class ThorDatabase_Impl extends ThorDatabase {
    private volatile CurrentVersionDao _currentVersionDao;
    private volatile InstalledSoundPackageDao _installedSoundPackageDao;
    private volatile ShopSguSoundPackageDao _shopSguSoundPackageDao;
    private volatile ShopSoundPackageDao _shopSoundPackageDao;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
        return configuration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(configuration.context).name(configuration.name).callback(new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(10) { // from class: com.thor.app.room.ThorDatabase_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(SupportSQLiteDatabase _db) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase _db) throws SQLException {
                _db.execSQL("CREATE TABLE IF NOT EXISTS `ShopSoundPackages` (`id` INTEGER NOT NULL, `PkgName` TEXT, `PkgVer` INTEGER NOT NULL, `PkgImageUrl` TEXT, `PkgStatus` INTEGER NOT NULL, `Price` INTEGER NOT NULL, `ModeTypes` TEXT, PRIMARY KEY(`id`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `InstalledSoundPackages` (`package_id` INTEGER NOT NULL, `version_id` INTEGER NOT NULL, `mode` INTEGER NOT NULL, `damage_pck` INTEGER NOT NULL, PRIMARY KEY(`package_id`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `ShopSguSoundPackages` (`id` INTEGER NOT NULL, `SetName` TEXT, `SetImageUrl` TEXT, `SetStatus` INTEGER NOT NULL, `Price` INTEGER NOT NULL, PRIMARY KEY(`id`))");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `current_versions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `versionOfFirmware` TEXT, `versionOfHardware` TEXT)");
                _db.execSQL(RoomMasterTable.CREATE_QUERY);
                _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7f10c1d0082c39a3c765c3ba242421a3')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase _db) throws SQLException {
                _db.execSQL("DROP TABLE IF EXISTS `ShopSoundPackages`");
                _db.execSQL("DROP TABLE IF EXISTS `InstalledSoundPackages`");
                _db.execSQL("DROP TABLE IF EXISTS `ShopSguSoundPackages`");
                _db.execSQL("DROP TABLE IF EXISTS `current_versions`");
                if (ThorDatabase_Impl.this.mCallbacks != null) {
                    int size = ThorDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) ThorDatabase_Impl.this.mCallbacks.get(i)).onDestructiveMigration(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onCreate(SupportSQLiteDatabase _db) {
                if (ThorDatabase_Impl.this.mCallbacks != null) {
                    int size = ThorDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) ThorDatabase_Impl.this.mCallbacks.get(i)).onCreate(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase _db) {
                ThorDatabase_Impl.this.mDatabase = _db;
                ThorDatabase_Impl.this.internalInitInvalidationTracker(_db);
                if (ThorDatabase_Impl.this.mCallbacks != null) {
                    int size = ThorDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) ThorDatabase_Impl.this.mCallbacks.get(i)).onOpen(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPreMigrate(SupportSQLiteDatabase _db) throws SQLException {
                DBUtil.dropFtsSyncTriggers(_db);
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
                HashMap map = new HashMap(7);
                map.put(TtmlNode.ATTR_ID, new TableInfo.Column(TtmlNode.ATTR_ID, "INTEGER", true, 1, null, 1));
                map.put("PkgName", new TableInfo.Column("PkgName", "TEXT", false, 0, null, 1));
                map.put("PkgVer", new TableInfo.Column("PkgVer", "INTEGER", true, 0, null, 1));
                map.put("PkgImageUrl", new TableInfo.Column("PkgImageUrl", "TEXT", false, 0, null, 1));
                map.put("PkgStatus", new TableInfo.Column("PkgStatus", "INTEGER", true, 0, null, 1));
                map.put("Price", new TableInfo.Column("Price", "INTEGER", true, 0, null, 1));
                map.put("ModeTypes", new TableInfo.Column("ModeTypes", "TEXT", false, 0, null, 1));
                TableInfo tableInfo = new TableInfo("ShopSoundPackages", map, new HashSet(0), new HashSet(0));
                TableInfo tableInfo2 = TableInfo.read(_db, "ShopSoundPackages");
                if (!tableInfo.equals(tableInfo2)) {
                    return new RoomOpenHelper.ValidationResult(false, "ShopSoundPackages(com.thor.app.room.entity.ShopSoundPackageEntity).\n Expected:\n" + tableInfo + "\n Found:\n" + tableInfo2);
                }
                HashMap map2 = new HashMap(4);
                map2.put("package_id", new TableInfo.Column("package_id", "INTEGER", true, 1, null, 1));
                map2.put("version_id", new TableInfo.Column("version_id", "INTEGER", true, 0, null, 1));
                map2.put("mode", new TableInfo.Column("mode", "INTEGER", true, 0, null, 1));
                map2.put("damage_pck", new TableInfo.Column("damage_pck", "INTEGER", true, 0, null, 1));
                TableInfo tableInfo3 = new TableInfo("InstalledSoundPackages", map2, new HashSet(0), new HashSet(0));
                TableInfo tableInfo4 = TableInfo.read(_db, "InstalledSoundPackages");
                if (!tableInfo3.equals(tableInfo4)) {
                    return new RoomOpenHelper.ValidationResult(false, "InstalledSoundPackages(com.thor.app.room.entity.InstalledSoundPackageEntity).\n Expected:\n" + tableInfo3 + "\n Found:\n" + tableInfo4);
                }
                HashMap map3 = new HashMap(5);
                map3.put(TtmlNode.ATTR_ID, new TableInfo.Column(TtmlNode.ATTR_ID, "INTEGER", true, 1, null, 1));
                map3.put("SetName", new TableInfo.Column("SetName", "TEXT", false, 0, null, 1));
                map3.put("SetImageUrl", new TableInfo.Column("SetImageUrl", "TEXT", false, 0, null, 1));
                map3.put("SetStatus", new TableInfo.Column("SetStatus", "INTEGER", true, 0, null, 1));
                map3.put("Price", new TableInfo.Column("Price", "INTEGER", true, 0, null, 1));
                TableInfo tableInfo5 = new TableInfo("ShopSguSoundPackages", map3, new HashSet(0), new HashSet(0));
                TableInfo tableInfo6 = TableInfo.read(_db, "ShopSguSoundPackages");
                if (!tableInfo5.equals(tableInfo6)) {
                    return new RoomOpenHelper.ValidationResult(false, "ShopSguSoundPackages(com.thor.app.room.entity.ShopSguSoundPackageEntity).\n Expected:\n" + tableInfo5 + "\n Found:\n" + tableInfo6);
                }
                HashMap map4 = new HashMap(3);
                map4.put(TtmlNode.ATTR_ID, new TableInfo.Column(TtmlNode.ATTR_ID, "INTEGER", true, 1, null, 1));
                map4.put("versionOfFirmware", new TableInfo.Column("versionOfFirmware", "TEXT", false, 0, null, 1));
                map4.put("versionOfHardware", new TableInfo.Column("versionOfHardware", "TEXT", false, 0, null, 1));
                TableInfo tableInfo7 = new TableInfo("current_versions", map4, new HashSet(0), new HashSet(0));
                TableInfo tableInfo8 = TableInfo.read(_db, "current_versions");
                if (!tableInfo7.equals(tableInfo8)) {
                    return new RoomOpenHelper.ValidationResult(false, "current_versions(com.thor.app.room.entity.CurrentVersionsEntity).\n Expected:\n" + tableInfo7 + "\n Found:\n" + tableInfo8);
                }
                return new RoomOpenHelper.ValidationResult(true, null);
            }
        }, "7f10c1d0082c39a3c765c3ba242421a3", "f37749394bc2ebcb15821d50814cfb07")).build());
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "ShopSoundPackages", "InstalledSoundPackages", "ShopSguSoundPackages", "current_versions");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() throws SQLException {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("DELETE FROM `ShopSoundPackages`");
            writableDatabase.execSQL("DELETE FROM `InstalledSoundPackages`");
            writableDatabase.execSQL("DELETE FROM `ShopSguSoundPackages`");
            writableDatabase.execSQL("DELETE FROM `current_versions`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!writableDatabase.inTransaction()) {
                writableDatabase.execSQL("VACUUM");
            }
        }
    }

    @Override // androidx.room.RoomDatabase
    protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
        HashMap map = new HashMap();
        map.put(ShopSoundPackageDao.class, ShopSoundPackageDao_Impl.getRequiredConverters());
        map.put(InstalledSoundPackageDao.class, InstalledSoundPackageDao_Impl.getRequiredConverters());
        map.put(ShopSguSoundPackageDao.class, ShopSguSoundPackageDao_Impl.getRequiredConverters());
        map.put(CurrentVersionDao.class, CurrentVersionDao_Impl.getRequiredConverters());
        return map;
    }

    @Override // androidx.room.RoomDatabase
    public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
        return new HashSet();
    }

    @Override // androidx.room.RoomDatabase
    public List<Migration> getAutoMigrations(Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
        return Arrays.asList(new Migration[0]);
    }

    @Override // com.thor.app.room.ThorDatabase
    public ShopSoundPackageDao shopSoundPackageDao() {
        ShopSoundPackageDao shopSoundPackageDao;
        if (this._shopSoundPackageDao != null) {
            return this._shopSoundPackageDao;
        }
        synchronized (this) {
            if (this._shopSoundPackageDao == null) {
                this._shopSoundPackageDao = new ShopSoundPackageDao_Impl(this);
            }
            shopSoundPackageDao = this._shopSoundPackageDao;
        }
        return shopSoundPackageDao;
    }

    @Override // com.thor.app.room.ThorDatabase
    public InstalledSoundPackageDao installedSoundPackageDao() {
        InstalledSoundPackageDao installedSoundPackageDao;
        if (this._installedSoundPackageDao != null) {
            return this._installedSoundPackageDao;
        }
        synchronized (this) {
            if (this._installedSoundPackageDao == null) {
                this._installedSoundPackageDao = new InstalledSoundPackageDao_Impl(this);
            }
            installedSoundPackageDao = this._installedSoundPackageDao;
        }
        return installedSoundPackageDao;
    }

    @Override // com.thor.app.room.ThorDatabase
    public ShopSguSoundPackageDao shopSguSoundPackageDao() {
        ShopSguSoundPackageDao shopSguSoundPackageDao;
        if (this._shopSguSoundPackageDao != null) {
            return this._shopSguSoundPackageDao;
        }
        synchronized (this) {
            if (this._shopSguSoundPackageDao == null) {
                this._shopSguSoundPackageDao = new ShopSguSoundPackageDao_Impl(this);
            }
            shopSguSoundPackageDao = this._shopSguSoundPackageDao;
        }
        return shopSguSoundPackageDao;
    }

    @Override // com.thor.app.room.ThorDatabase
    public CurrentVersionDao currentVersionDao() {
        CurrentVersionDao currentVersionDao;
        if (this._currentVersionDao != null) {
            return this._currentVersionDao;
        }
        synchronized (this) {
            if (this._currentVersionDao == null) {
                this._currentVersionDao = new CurrentVersionDao_Impl(this);
            }
            currentVersionDao = this._currentVersionDao;
        }
        return currentVersionDao;
    }
}
