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
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.thor.app.room.entity.ShopSguSoundPackageEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public final class ShopSguSoundPackageDao_Impl implements ShopSguSoundPackageDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<ShopSguSoundPackageEntity> __insertionAdapterOfShopSguSoundPackageEntity;
    private final SharedSQLiteStatement __preparedStmtOfDeleteAllElements;
    private final SharedSQLiteStatement __preparedStmtOfDeleteById;

    public ShopSguSoundPackageDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfShopSguSoundPackageEntity = new EntityInsertionAdapter<ShopSguSoundPackageEntity>(__db) { // from class: com.thor.app.room.dao.ShopSguSoundPackageDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `ShopSguSoundPackages` (`id`,`SetName`,`SetImageUrl`,`SetStatus`,`Price`) VALUES (?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, ShopSguSoundPackageEntity value) {
                stmt.bindLong(1, value.getId());
                if (value.getSetName() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getSetName());
                }
                if (value.getSetImageUrl() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getSetImageUrl());
                }
                stmt.bindLong(4, value.getSetStatus());
                stmt.bindLong(5, value.getPrice());
            }
        };
        this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) { // from class: com.thor.app.room.dao.ShopSguSoundPackageDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM ShopSguSoundPackages WHERE id = ?";
            }
        };
        this.__preparedStmtOfDeleteAllElements = new SharedSQLiteStatement(__db) { // from class: com.thor.app.room.dao.ShopSguSoundPackageDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM ShopSguSoundPackages";
            }
        };
    }

    @Override // com.thor.app.room.dao.ShopSguSoundPackageDao
    public Completable insert(final ShopSguSoundPackageEntity... entity) {
        return Completable.fromCallable(new Callable<Void>() { // from class: com.thor.app.room.dao.ShopSguSoundPackageDao_Impl.4
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                ShopSguSoundPackageDao_Impl.this.__db.beginTransaction();
                try {
                    ShopSguSoundPackageDao_Impl.this.__insertionAdapterOfShopSguSoundPackageEntity.insert((Object[]) entity);
                    ShopSguSoundPackageDao_Impl.this.__db.setTransactionSuccessful();
                    ShopSguSoundPackageDao_Impl.this.__db.endTransaction();
                    return null;
                } catch (Throwable th) {
                    ShopSguSoundPackageDao_Impl.this.__db.endTransaction();
                    throw th;
                }
            }
        });
    }

    @Override // com.thor.app.room.dao.ShopSguSoundPackageDao
    public Completable deleteById(final int packageId) {
        return Completable.fromCallable(new Callable<Void>() { // from class: com.thor.app.room.dao.ShopSguSoundPackageDao_Impl.5
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = ShopSguSoundPackageDao_Impl.this.__preparedStmtOfDeleteById.acquire();
                supportSQLiteStatementAcquire.bindLong(1, packageId);
                ShopSguSoundPackageDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    ShopSguSoundPackageDao_Impl.this.__db.setTransactionSuccessful();
                    ShopSguSoundPackageDao_Impl.this.__db.endTransaction();
                    ShopSguSoundPackageDao_Impl.this.__preparedStmtOfDeleteById.release(supportSQLiteStatementAcquire);
                    return null;
                } catch (Throwable th) {
                    ShopSguSoundPackageDao_Impl.this.__db.endTransaction();
                    ShopSguSoundPackageDao_Impl.this.__preparedStmtOfDeleteById.release(supportSQLiteStatementAcquire);
                    throw th;
                }
            }
        });
    }

    @Override // com.thor.app.room.dao.ShopSguSoundPackageDao
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

    @Override // com.thor.app.room.dao.ShopSguSoundPackageDao
    public Flowable<List<ShopSguSoundPackageEntity>> getEntitiesFlow() {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM ShopSguSoundPackages", 0);
        return RxRoom.createFlowable(this.__db, false, new String[]{"ShopSguSoundPackages"}, new Callable<List<ShopSguSoundPackageEntity>>() { // from class: com.thor.app.room.dao.ShopSguSoundPackageDao_Impl.6
            @Override // java.util.concurrent.Callable
            public List<ShopSguSoundPackageEntity> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(ShopSguSoundPackageDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, TtmlNode.ATTR_ID);
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "SetName");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "SetImageUrl");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "SetStatus");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "Price");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        arrayList.add(new ShopSguSoundPackageEntity(cursorQuery.getInt(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2), cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3), cursorQuery.getInt(columnIndexOrThrow4), cursorQuery.getInt(columnIndexOrThrow5)));
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

    @Override // com.thor.app.room.dao.ShopSguSoundPackageDao
    public Single<List<ShopSguSoundPackageEntity>> getEntities() {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM ShopSguSoundPackages", 0);
        return RxRoom.createSingle(new Callable<List<ShopSguSoundPackageEntity>>() { // from class: com.thor.app.room.dao.ShopSguSoundPackageDao_Impl.7
            @Override // java.util.concurrent.Callable
            public List<ShopSguSoundPackageEntity> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(ShopSguSoundPackageDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, TtmlNode.ATTR_ID);
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "SetName");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "SetImageUrl");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "SetStatus");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "Price");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        arrayList.add(new ShopSguSoundPackageEntity(cursorQuery.getInt(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2), cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3), cursorQuery.getInt(columnIndexOrThrow4), cursorQuery.getInt(columnIndexOrThrow5)));
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

    @Override // com.thor.app.room.dao.ShopSguSoundPackageDao
    public Flowable<ShopSguSoundPackageEntity> getEntityById(final int id) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM ShopSguSoundPackages WHERE id = ?", 1);
        roomSQLiteQueryAcquire.bindLong(1, id);
        return RxRoom.createFlowable(this.__db, false, new String[]{"ShopSguSoundPackages"}, new Callable<ShopSguSoundPackageEntity>() { // from class: com.thor.app.room.dao.ShopSguSoundPackageDao_Impl.8
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public ShopSguSoundPackageEntity call() throws Exception {
                ShopSguSoundPackageEntity shopSguSoundPackageEntity = null;
                Cursor cursorQuery = DBUtil.query(ShopSguSoundPackageDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, TtmlNode.ATTR_ID);
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "SetName");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "SetImageUrl");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "SetStatus");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "Price");
                    if (cursorQuery.moveToFirst()) {
                        shopSguSoundPackageEntity = new ShopSguSoundPackageEntity(cursorQuery.getInt(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2), cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3), cursorQuery.getInt(columnIndexOrThrow4), cursorQuery.getInt(columnIndexOrThrow5));
                    }
                    return shopSguSoundPackageEntity;
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
