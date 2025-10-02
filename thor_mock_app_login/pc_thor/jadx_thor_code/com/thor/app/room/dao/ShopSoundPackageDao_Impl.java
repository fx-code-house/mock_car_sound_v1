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
import com.google.gson.JsonIOException;
import com.thor.app.room.entity.ShopSoundPackageEntity;
import com.thor.app.room.typeconverters.ModeTypeConverter;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public final class ShopSoundPackageDao_Impl implements ShopSoundPackageDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<ShopSoundPackageEntity> __insertionAdapterOfShopSoundPackageEntity;
    private final ModeTypeConverter __modeTypeConverter = new ModeTypeConverter();
    private final SharedSQLiteStatement __preparedStmtOfDeleteAllElements;
    private final SharedSQLiteStatement __preparedStmtOfDeleteById;

    public ShopSoundPackageDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfShopSoundPackageEntity = new EntityInsertionAdapter<ShopSoundPackageEntity>(__db) { // from class: com.thor.app.room.dao.ShopSoundPackageDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `ShopSoundPackages` (`id`,`PkgName`,`PkgVer`,`PkgImageUrl`,`PkgStatus`,`Price`,`ModeTypes`) VALUES (?,?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, ShopSoundPackageEntity value) throws JsonIOException {
                stmt.bindLong(1, value.getId());
                if (value.getPkgName() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getPkgName());
                }
                stmt.bindLong(3, value.getPkgVer());
                if (value.getPkgImageUrl() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getPkgImageUrl());
                }
                stmt.bindLong(5, value.getPkgStatus());
                stmt.bindLong(6, value.getPrice());
                String json = ShopSoundPackageDao_Impl.this.__modeTypeConverter.toJson(value.getModeTypes());
                if (json == null) {
                    stmt.bindNull(7);
                } else {
                    stmt.bindString(7, json);
                }
            }
        };
        this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) { // from class: com.thor.app.room.dao.ShopSoundPackageDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM ShopSoundPackages WHERE id = ?";
            }
        };
        this.__preparedStmtOfDeleteAllElements = new SharedSQLiteStatement(__db) { // from class: com.thor.app.room.dao.ShopSoundPackageDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM ShopSoundPackages";
            }
        };
    }

    @Override // com.thor.app.room.dao.ShopSoundPackageDao
    public Completable insert(final ShopSoundPackageEntity... entity) {
        return Completable.fromCallable(new Callable<Void>() { // from class: com.thor.app.room.dao.ShopSoundPackageDao_Impl.4
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                ShopSoundPackageDao_Impl.this.__db.beginTransaction();
                try {
                    ShopSoundPackageDao_Impl.this.__insertionAdapterOfShopSoundPackageEntity.insert((Object[]) entity);
                    ShopSoundPackageDao_Impl.this.__db.setTransactionSuccessful();
                    ShopSoundPackageDao_Impl.this.__db.endTransaction();
                    return null;
                } catch (Throwable th) {
                    ShopSoundPackageDao_Impl.this.__db.endTransaction();
                    throw th;
                }
            }
        });
    }

    @Override // com.thor.app.room.dao.ShopSoundPackageDao
    public Completable update(final ShopSoundPackageEntity entity) {
        return Completable.fromCallable(new Callable<Void>() { // from class: com.thor.app.room.dao.ShopSoundPackageDao_Impl.5
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                ShopSoundPackageDao_Impl.this.__db.beginTransaction();
                try {
                    ShopSoundPackageDao_Impl.this.__insertionAdapterOfShopSoundPackageEntity.insert((EntityInsertionAdapter) entity);
                    ShopSoundPackageDao_Impl.this.__db.setTransactionSuccessful();
                    ShopSoundPackageDao_Impl.this.__db.endTransaction();
                    return null;
                } catch (Throwable th) {
                    ShopSoundPackageDao_Impl.this.__db.endTransaction();
                    throw th;
                }
            }
        });
    }

    @Override // com.thor.app.room.dao.ShopSoundPackageDao
    public Completable deleteById(final int packageId) {
        return Completable.fromCallable(new Callable<Void>() { // from class: com.thor.app.room.dao.ShopSoundPackageDao_Impl.6
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = ShopSoundPackageDao_Impl.this.__preparedStmtOfDeleteById.acquire();
                supportSQLiteStatementAcquire.bindLong(1, packageId);
                ShopSoundPackageDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    ShopSoundPackageDao_Impl.this.__db.setTransactionSuccessful();
                    ShopSoundPackageDao_Impl.this.__db.endTransaction();
                    ShopSoundPackageDao_Impl.this.__preparedStmtOfDeleteById.release(supportSQLiteStatementAcquire);
                    return null;
                } catch (Throwable th) {
                    ShopSoundPackageDao_Impl.this.__db.endTransaction();
                    ShopSoundPackageDao_Impl.this.__preparedStmtOfDeleteById.release(supportSQLiteStatementAcquire);
                    throw th;
                }
            }
        });
    }

    @Override // com.thor.app.room.dao.ShopSoundPackageDao
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

    @Override // com.thor.app.room.dao.ShopSoundPackageDao
    public Flowable<List<ShopSoundPackageEntity>> getEntitiesFlow() {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM ShopSoundPackages", 0);
        return RxRoom.createFlowable(this.__db, false, new String[]{"ShopSoundPackages"}, new Callable<List<ShopSoundPackageEntity>>() { // from class: com.thor.app.room.dao.ShopSoundPackageDao_Impl.7
            @Override // java.util.concurrent.Callable
            public List<ShopSoundPackageEntity> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(ShopSoundPackageDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, TtmlNode.ATTR_ID);
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "PkgName");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "PkgVer");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "PkgImageUrl");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "PkgStatus");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "Price");
                    int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "ModeTypes");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        arrayList.add(new ShopSoundPackageEntity(cursorQuery.getInt(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2), cursorQuery.getInt(columnIndexOrThrow3), cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4), cursorQuery.getInt(columnIndexOrThrow5), cursorQuery.getInt(columnIndexOrThrow6), ShopSoundPackageDao_Impl.this.__modeTypeConverter.toList(cursorQuery.isNull(columnIndexOrThrow7) ? null : cursorQuery.getString(columnIndexOrThrow7))));
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

    @Override // com.thor.app.room.dao.ShopSoundPackageDao
    public Single<List<ShopSoundPackageEntity>> getEntities() {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM ShopSoundPackages", 0);
        return RxRoom.createSingle(new Callable<List<ShopSoundPackageEntity>>() { // from class: com.thor.app.room.dao.ShopSoundPackageDao_Impl.8
            @Override // java.util.concurrent.Callable
            public List<ShopSoundPackageEntity> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(ShopSoundPackageDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, TtmlNode.ATTR_ID);
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "PkgName");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "PkgVer");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "PkgImageUrl");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "PkgStatus");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "Price");
                    int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "ModeTypes");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        arrayList.add(new ShopSoundPackageEntity(cursorQuery.getInt(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2), cursorQuery.getInt(columnIndexOrThrow3), cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4), cursorQuery.getInt(columnIndexOrThrow5), cursorQuery.getInt(columnIndexOrThrow6), ShopSoundPackageDao_Impl.this.__modeTypeConverter.toList(cursorQuery.isNull(columnIndexOrThrow7) ? null : cursorQuery.getString(columnIndexOrThrow7))));
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

    @Override // com.thor.app.room.dao.ShopSoundPackageDao
    public Flowable<ShopSoundPackageEntity> getEntityById(final int id) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM ShopSoundPackages WHERE id = ?", 1);
        roomSQLiteQueryAcquire.bindLong(1, id);
        return RxRoom.createFlowable(this.__db, false, new String[]{"ShopSoundPackages"}, new Callable<ShopSoundPackageEntity>() { // from class: com.thor.app.room.dao.ShopSoundPackageDao_Impl.9
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public ShopSoundPackageEntity call() throws Exception {
                ShopSoundPackageEntity shopSoundPackageEntity = null;
                String string = null;
                Cursor cursorQuery = DBUtil.query(ShopSoundPackageDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, TtmlNode.ATTR_ID);
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "PkgName");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "PkgVer");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "PkgImageUrl");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "PkgStatus");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "Price");
                    int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "ModeTypes");
                    if (cursorQuery.moveToFirst()) {
                        int i = cursorQuery.getInt(columnIndexOrThrow);
                        String string2 = cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2);
                        int i2 = cursorQuery.getInt(columnIndexOrThrow3);
                        String string3 = cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4);
                        int i3 = cursorQuery.getInt(columnIndexOrThrow5);
                        int i4 = cursorQuery.getInt(columnIndexOrThrow6);
                        if (!cursorQuery.isNull(columnIndexOrThrow7)) {
                            string = cursorQuery.getString(columnIndexOrThrow7);
                        }
                        shopSoundPackageEntity = new ShopSoundPackageEntity(i, string2, i2, string3, i3, i4, ShopSoundPackageDao_Impl.this.__modeTypeConverter.toList(string));
                    }
                    return shopSoundPackageEntity;
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
