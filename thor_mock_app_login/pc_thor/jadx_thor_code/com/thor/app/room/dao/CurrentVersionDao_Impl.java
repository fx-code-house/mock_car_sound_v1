package com.thor.app.room.dao;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.thor.app.room.entity.CurrentVersionsEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* loaded from: classes3.dex */
public final class CurrentVersionDao_Impl implements CurrentVersionDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<CurrentVersionsEntity> __insertionAdapterOfCurrentVersionsEntity;
    private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

    public CurrentVersionDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfCurrentVersionsEntity = new EntityInsertionAdapter<CurrentVersionsEntity>(__db) { // from class: com.thor.app.room.dao.CurrentVersionDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `current_versions` (`id`,`versionOfFirmware`,`versionOfHardware`) VALUES (nullif(?, 0),?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, CurrentVersionsEntity value) {
                stmt.bindLong(1, value.getId());
                if (value.getVersionOfFirmware() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getVersionOfFirmware());
                }
                if (value.getVersionOfHardware() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getVersionOfHardware());
                }
            }
        };
        this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) { // from class: com.thor.app.room.dao.CurrentVersionDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM current_versions";
            }
        };
    }

    @Override // com.thor.app.room.dao.CurrentVersionDao
    public Object insert(final List<CurrentVersionsEntity> version, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.thor.app.room.dao.CurrentVersionDao_Impl.3
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                CurrentVersionDao_Impl.this.__db.beginTransaction();
                try {
                    CurrentVersionDao_Impl.this.__insertionAdapterOfCurrentVersionsEntity.insert((Iterable) version);
                    CurrentVersionDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    CurrentVersionDao_Impl.this.__db.endTransaction();
                }
            }
        }, continuation);
    }

    @Override // com.thor.app.room.dao.CurrentVersionDao
    public Object deleteAll(final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.thor.app.room.dao.CurrentVersionDao_Impl.4
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = CurrentVersionDao_Impl.this.__preparedStmtOfDeleteAll.acquire();
                CurrentVersionDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    CurrentVersionDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    CurrentVersionDao_Impl.this.__db.endTransaction();
                    CurrentVersionDao_Impl.this.__preparedStmtOfDeleteAll.release(supportSQLiteStatementAcquire);
                }
            }
        }, continuation);
    }

    @Override // com.thor.app.room.dao.CurrentVersionDao
    public Object getCurrentVersion(final Continuation<? super List<CurrentVersionsEntity>> continuation) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM current_versions", 0);
        return CoroutinesRoom.execute(this.__db, false, DBUtil.createCancellationSignal(), new Callable<List<CurrentVersionsEntity>>() { // from class: com.thor.app.room.dao.CurrentVersionDao_Impl.5
            @Override // java.util.concurrent.Callable
            public List<CurrentVersionsEntity> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(CurrentVersionDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, TtmlNode.ATTR_ID);
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "versionOfFirmware");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "versionOfHardware");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        arrayList.add(new CurrentVersionsEntity(cursorQuery.getInt(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2), cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3)));
                    }
                    return arrayList;
                } finally {
                    cursorQuery.close();
                    roomSQLiteQueryAcquire.release();
                }
            }
        }, continuation);
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
