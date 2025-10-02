package com.google.android.exoplayer2.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.util.Log;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

/* loaded from: classes.dex */
public final class ExoDatabaseProvider extends SQLiteOpenHelper implements DatabaseProvider {
    public static final String DATABASE_NAME = "exoplayer_internal.db";
    private static final String TAG = "ExoDatabaseProvider";
    private static final int VERSION = 1;

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ExoDatabaseProvider(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        wipeDatabase(db);
    }

    private static void wipeDatabase(SQLiteDatabase db) {
        Cursor cursorQuery = db.query("sqlite_master", new String[]{SessionDescription.ATTR_TYPE, AppMeasurementSdk.ConditionalUserProperty.NAME}, null, null, null, null, null);
        while (cursorQuery.moveToNext()) {
            try {
                String string = cursorQuery.getString(0);
                String string2 = cursorQuery.getString(1);
                if (!"sqlite_sequence".equals(string2)) {
                    String string3 = new StringBuilder(String.valueOf(string).length() + 16 + String.valueOf(string2).length()).append("DROP ").append(string).append(" IF EXISTS ").append(string2).toString();
                    try {
                        db.execSQL(string3);
                    } catch (SQLException e) {
                        String strValueOf = String.valueOf(string3);
                        Log.e(TAG, strValueOf.length() != 0 ? "Error executing ".concat(strValueOf) : new String("Error executing "), e);
                    }
                }
            } catch (Throwable th) {
                if (cursorQuery != null) {
                    try {
                        cursorQuery.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
    }
}
