package com.thor.app.room.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.RxRoom;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.thor.app.room.entity.InstalledSoundPackageEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public final class InstalledSoundPackageDao_Impl implements InstalledSoundPackageDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<InstalledSoundPackageEntity> __insertionAdapterOfInstalledSoundPackageEntity;
    private final SharedSQLiteStatement __preparedStmtOfDeleteAllElements;
    private final SharedSQLiteStatement __preparedStmtOfDeleteById;
    private final SharedSQLiteStatement __preparedStmtOfUpdateDamagePck;
    private final SharedSQLiteStatement __preparedStmtOfUpdateDamagePckEndReturnId;
    private final SharedSQLiteStatement __preparedStmtOfUpdateEndReturnId;

    public InstalledSoundPackageDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfInstalledSoundPackageEntity = new EntityInsertionAdapter<InstalledSoundPackageEntity>(__db) { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `InstalledSoundPackages` (`package_id`,`version_id`,`mode`,`damage_pck`) VALUES (?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, InstalledSoundPackageEntity installedSoundPackageEntity) {
                supportSQLiteStatement.bindLong(1, installedSoundPackageEntity.getPackageId());
                supportSQLiteStatement.bindLong(2, installedSoundPackageEntity.getVersionId());
                supportSQLiteStatement.bindLong(3, installedSoundPackageEntity.getMode());
                supportSQLiteStatement.bindLong(4, installedSoundPackageEntity.getDamagePck() ? 1L : 0L);
            }
        };
        this.__preparedStmtOfUpdateEndReturnId = new SharedSQLiteStatement(__db) { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE InstalledSoundPackages SET version_id = ?, mode = ? WHERE package_id = ?";
            }
        };
        this.__preparedStmtOfUpdateDamagePckEndReturnId = new SharedSQLiteStatement(__db) { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE InstalledSoundPackages SET damage_pck = 0 WHERE package_id = ?";
            }
        };
        this.__preparedStmtOfUpdateDamagePck = new SharedSQLiteStatement(__db) { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.4
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE InstalledSoundPackages SET damage_pck = 1 WHERE package_id = ?";
            }
        };
        this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.5
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM InstalledSoundPackages WHERE package_id = ?";
            }
        };
        this.__preparedStmtOfDeleteAllElements = new SharedSQLiteStatement(__db) { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.6
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM InstalledSoundPackages";
            }
        };
    }

    @Override // com.thor.app.room.dao.InstalledSoundPackageDao
    public Completable insert(final InstalledSoundPackageEntity entity) {
        return Completable.fromCallable(new Callable<Void>() { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.7
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                InstalledSoundPackageDao_Impl.this.__db.beginTransaction();
                try {
                    InstalledSoundPackageDao_Impl.this.__insertionAdapterOfInstalledSoundPackageEntity.insert((EntityInsertionAdapter) entity);
                    InstalledSoundPackageDao_Impl.this.__db.setTransactionSuccessful();
                    InstalledSoundPackageDao_Impl.this.__db.endTransaction();
                    return null;
                } catch (Throwable th) {
                    InstalledSoundPackageDao_Impl.this.__db.endTransaction();
                    throw th;
                }
            }
        });
    }

    @Override // com.thor.app.room.dao.InstalledSoundPackageDao
    public Maybe<Long> insertEndReturnId(final InstalledSoundPackageEntity entity) {
        return Maybe.fromCallable(new Callable<Long>() { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.8
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Long call() throws Exception {
                InstalledSoundPackageDao_Impl.this.__db.beginTransaction();
                try {
                    long jInsertAndReturnId = InstalledSoundPackageDao_Impl.this.__insertionAdapterOfInstalledSoundPackageEntity.insertAndReturnId(entity);
                    InstalledSoundPackageDao_Impl.this.__db.setTransactionSuccessful();
                    return Long.valueOf(jInsertAndReturnId);
                } finally {
                    InstalledSoundPackageDao_Impl.this.__db.endTransaction();
                }
            }
        });
    }

    @Override // com.thor.app.room.dao.InstalledSoundPackageDao
    public Maybe<Integer> updateEndReturnId(final int packageId, final int versionId, final int mode) {
        return Maybe.fromCallable(new Callable<Integer>() { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.9
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Integer call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = InstalledSoundPackageDao_Impl.this.__preparedStmtOfUpdateEndReturnId.acquire();
                supportSQLiteStatementAcquire.bindLong(1, versionId);
                supportSQLiteStatementAcquire.bindLong(2, mode);
                supportSQLiteStatementAcquire.bindLong(3, packageId);
                InstalledSoundPackageDao_Impl.this.__db.beginTransaction();
                try {
                    Integer numValueOf = Integer.valueOf(supportSQLiteStatementAcquire.executeUpdateDelete());
                    InstalledSoundPackageDao_Impl.this.__db.setTransactionSuccessful();
                    return numValueOf;
                } finally {
                    InstalledSoundPackageDao_Impl.this.__db.endTransaction();
                    InstalledSoundPackageDao_Impl.this.__preparedStmtOfUpdateEndReturnId.release(supportSQLiteStatementAcquire);
                }
            }
        });
    }

    @Override // com.thor.app.room.dao.InstalledSoundPackageDao
    public Completable updateDamagePckEndReturnId(final int packageId) {
        return Completable.fromCallable(new Callable<Void>() { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.10
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = InstalledSoundPackageDao_Impl.this.__preparedStmtOfUpdateDamagePckEndReturnId.acquire();
                supportSQLiteStatementAcquire.bindLong(1, packageId);
                InstalledSoundPackageDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    InstalledSoundPackageDao_Impl.this.__db.setTransactionSuccessful();
                    InstalledSoundPackageDao_Impl.this.__db.endTransaction();
                    InstalledSoundPackageDao_Impl.this.__preparedStmtOfUpdateDamagePckEndReturnId.release(supportSQLiteStatementAcquire);
                    return null;
                } catch (Throwable th) {
                    InstalledSoundPackageDao_Impl.this.__db.endTransaction();
                    InstalledSoundPackageDao_Impl.this.__preparedStmtOfUpdateDamagePckEndReturnId.release(supportSQLiteStatementAcquire);
                    throw th;
                }
            }
        });
    }

    @Override // com.thor.app.room.dao.InstalledSoundPackageDao
    public Completable updateDamagePck(final int packageId) {
        return Completable.fromCallable(new Callable<Void>() { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.11
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = InstalledSoundPackageDao_Impl.this.__preparedStmtOfUpdateDamagePck.acquire();
                supportSQLiteStatementAcquire.bindLong(1, packageId);
                InstalledSoundPackageDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    InstalledSoundPackageDao_Impl.this.__db.setTransactionSuccessful();
                    InstalledSoundPackageDao_Impl.this.__db.endTransaction();
                    InstalledSoundPackageDao_Impl.this.__preparedStmtOfUpdateDamagePck.release(supportSQLiteStatementAcquire);
                    return null;
                } catch (Throwable th) {
                    InstalledSoundPackageDao_Impl.this.__db.endTransaction();
                    InstalledSoundPackageDao_Impl.this.__preparedStmtOfUpdateDamagePck.release(supportSQLiteStatementAcquire);
                    throw th;
                }
            }
        });
    }

    @Override // com.thor.app.room.dao.InstalledSoundPackageDao
    public Completable deleteById(final int packageId) {
        return Completable.fromCallable(new Callable<Void>() { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.12
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = InstalledSoundPackageDao_Impl.this.__preparedStmtOfDeleteById.acquire();
                supportSQLiteStatementAcquire.bindLong(1, packageId);
                InstalledSoundPackageDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    InstalledSoundPackageDao_Impl.this.__db.setTransactionSuccessful();
                    InstalledSoundPackageDao_Impl.this.__db.endTransaction();
                    InstalledSoundPackageDao_Impl.this.__preparedStmtOfDeleteById.release(supportSQLiteStatementAcquire);
                    return null;
                } catch (Throwable th) {
                    InstalledSoundPackageDao_Impl.this.__db.endTransaction();
                    InstalledSoundPackageDao_Impl.this.__preparedStmtOfDeleteById.release(supportSQLiteStatementAcquire);
                    throw th;
                }
            }
        });
    }

    @Override // com.thor.app.room.dao.InstalledSoundPackageDao
    public void deleteAllElements() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDeleteAllElements.acquire();
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleteAllElements.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // com.thor.app.room.dao.InstalledSoundPackageDao
    public Flowable<List<InstalledSoundPackageEntity>> getEntities() {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM InstalledSoundPackages", 0);
        return RxRoom.createFlowable(this.__db, false, new String[]{"InstalledSoundPackages"}, new Callable<List<InstalledSoundPackageEntity>>() { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.13
            @Override // java.util.concurrent.Callable
            public List<InstalledSoundPackageEntity> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(InstalledSoundPackageDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "package_id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "version_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "mode");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "damage_pck");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        arrayList.add(new InstalledSoundPackageEntity(cursorQuery.getInt(columnIndexOrThrow), cursorQuery.getInt(columnIndexOrThrow2), cursorQuery.getInt(columnIndexOrThrow3), cursorQuery.getInt(columnIndexOrThrow4) != 0));
                    }
                    return arrayList;
                } finally {
                    cursorQuery.close();
                }
            }

            protected void finalize() {
                roomSQLiteQueryAcquire.release();
            }
        });
    }

    @Override // com.thor.app.room.dao.InstalledSoundPackageDao
    public Flowable<InstalledSoundPackageEntity> getEntityById(final int id) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM InstalledSoundPackages WHERE package_id = ?", 1);
        roomSQLiteQueryAcquire.bindLong(1, id);
        return RxRoom.createFlowable(this.__db, false, new String[]{"InstalledSoundPackages"}, new Callable<InstalledSoundPackageEntity>() { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.14
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public InstalledSoundPackageEntity call() throws Exception {
                InstalledSoundPackageEntity installedSoundPackageEntity = null;
                Cursor cursorQuery = DBUtil.query(InstalledSoundPackageDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "package_id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "version_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "mode");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "damage_pck");
                    if (cursorQuery.moveToFirst()) {
                        installedSoundPackageEntity = new InstalledSoundPackageEntity(cursorQuery.getInt(columnIndexOrThrow), cursorQuery.getInt(columnIndexOrThrow2), cursorQuery.getInt(columnIndexOrThrow3), cursorQuery.getInt(columnIndexOrThrow4) != 0);
                    }
                    return installedSoundPackageEntity;
                } finally {
                    cursorQuery.close();
                }
            }

            protected void finalize() {
                roomSQLiteQueryAcquire.release();
            }
        });
    }

    @Override // com.thor.app.room.dao.InstalledSoundPackageDao
    public Maybe<Boolean> getDamagePck(final int packageId) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT damage_pck FROM InstalledSoundPackages WHERE package_id = ?", 1);
        roomSQLiteQueryAcquire.bindLong(1, packageId);
        return Maybe.fromCallable(new Callable<Boolean>() { // from class: com.thor.app.room.dao.InstalledSoundPackageDao_Impl.15
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Boolean call() throws Exception {
                Boolean boolValueOf = null;
                Cursor cursorQuery = DBUtil.query(InstalledSoundPackageDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    if (cursorQuery.moveToFirst()) {
                        Integer numValueOf = cursorQuery.isNull(0) ? null : Integer.valueOf(cursorQuery.getInt(0));
                        if (numValueOf != null) {
                            boolValueOf = Boolean.valueOf(numValueOf.intValue() != 0);
                        }
                    }
                    return boolValueOf;
                } finally {
                    cursorQuery.close();
                }
            }

            protected void finalize() {
                roomSQLiteQueryAcquire.release();
            }
        });
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
