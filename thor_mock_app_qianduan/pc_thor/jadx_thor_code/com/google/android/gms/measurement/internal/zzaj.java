package com.google.android.gms.measurement.internal;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzaj {
    private static Set<String> zza(SQLiteDatabase sQLiteDatabase, String str) {
        HashSet hashSet = new HashSet();
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery(new StringBuilder(String.valueOf(str).length() + 22).append("SELECT * FROM ").append(str).append(" LIMIT 0").toString(), null);
        try {
            Collections.addAll(hashSet, cursorRawQuery.getColumnNames());
            return hashSet;
        } finally {
            cursorRawQuery.close();
        }
    }

    static void zza(zzeq zzeqVar, SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String[] strArr) throws IllegalStateException, SQLException {
        if (zzeqVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        if (!zza(zzeqVar, sQLiteDatabase, str)) {
            sQLiteDatabase.execSQL(str2);
        }
        try {
            if (zzeqVar == null) {
                throw new IllegalArgumentException("Monitor must not be null");
            }
            Set<String> setZza = zza(sQLiteDatabase, str);
            for (String str4 : str3.split(",")) {
                if (!setZza.remove(str4)) {
                    throw new SQLiteException(new StringBuilder(String.valueOf(str).length() + 35 + String.valueOf(str4).length()).append("Table ").append(str).append(" is missing required column: ").append(str4).toString());
                }
            }
            if (strArr != null) {
                for (int i = 0; i < strArr.length; i += 2) {
                    if (!setZza.remove(strArr[i])) {
                        sQLiteDatabase.execSQL(strArr[i + 1]);
                    }
                }
            }
            if (setZza.isEmpty()) {
                return;
            }
            zzeqVar.zzh().zza("Table has extra columns. table, columns", str, TextUtils.join(", ", setZza));
        } catch (SQLiteException e) {
            zzeqVar.zze().zza("Failed to verify columns on table that was just created", str);
            throw e;
        }
    }

    private static boolean zza(zzeq zzeqVar, SQLiteDatabase sQLiteDatabase, String str) {
        if (zzeqVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = sQLiteDatabase.query("SQLITE_MASTER", new String[]{AppMeasurementSdk.ConditionalUserProperty.NAME}, "name=?", new String[]{str}, null, null, null);
                boolean zMoveToFirst = cursorQuery.moveToFirst();
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return zMoveToFirst;
            } catch (SQLiteException e) {
                zzeqVar.zzh().zza("Error querying for table", str, e);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return false;
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    static void zza(zzeq zzeqVar, SQLiteDatabase sQLiteDatabase) throws IllegalStateException {
        if (zzeqVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        File file = new File(sQLiteDatabase.getPath());
        if (!file.setReadable(false, false)) {
            zzeqVar.zzh().zza("Failed to turn off database read permission");
        }
        if (!file.setWritable(false, false)) {
            zzeqVar.zzh().zza("Failed to turn off database write permission");
        }
        if (!file.setReadable(true, true)) {
            zzeqVar.zzh().zza("Failed to turn on database read permission for owner");
        }
        if (file.setWritable(true, true)) {
            return;
        }
        zzeqVar.zzh().zza("Failed to turn on database write permission for owner");
    }
}
