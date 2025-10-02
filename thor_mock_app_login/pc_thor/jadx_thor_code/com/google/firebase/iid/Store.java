package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-iid@@21.0.0 */
/* loaded from: classes2.dex */
class Store {
    final Context context;
    final SharedPreferences store;
    private final Map<String, Long> subtypeCreationTimes = new ArrayMap();

    public Store(Context context) {
        this.context = context;
        this.store = context.getSharedPreferences("com.google.android.gms.appid", 0);
        checkForRestore("com.google.android.gms.appid-no-backup");
    }

    /* compiled from: com.google.firebase:firebase-iid@@21.0.0 */
    static class Token {
        private static final long REFRESH_PERIOD_MILLIS = TimeUnit.DAYS.toMillis(7);
        final String appVersion;
        final long timestamp;
        final String token;

        private Token(String str, String str2, long j) {
            this.token = str;
            this.appVersion = str2;
            this.timestamp = j;
        }

        static Token parse(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            if (str.startsWith("{")) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    return new Token(jSONObject.getString("token"), jSONObject.getString("appVersion"), jSONObject.getLong("timestamp"));
                } catch (JSONException e) {
                    String strValueOf = String.valueOf(e);
                    Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(strValueOf).length() + 23).append("Failed to parse token: ").append(strValueOf).toString());
                    return null;
                }
            }
            return new Token(str, null, 0L);
        }

        static String encode(String str, String str2, long j) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("token", str);
                jSONObject.put("appVersion", str2);
                jSONObject.put("timestamp", j);
                return jSONObject.toString();
            } catch (JSONException e) {
                String strValueOf = String.valueOf(e);
                Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(strValueOf).length() + 24).append("Failed to encode token: ").append(strValueOf).toString());
                return null;
            }
        }

        static String getTokenOrNull(Token token) {
            if (token == null) {
                return null;
            }
            return token.token;
        }

        boolean needsRefresh(String str) {
            return System.currentTimeMillis() > this.timestamp + REFRESH_PERIOD_MILLIS || !str.equals(this.appVersion);
        }
    }

    private void checkForRestore(String str) {
        File file = new File(ContextCompat.getNoBackupFilesDir(this.context), str);
        if (file.exists()) {
            return;
        }
        try {
            if (!file.createNewFile() || isEmpty()) {
                return;
            }
            Log.i("FirebaseInstanceId", "App restored, clearing state");
            deleteAll();
            FirebaseInstanceId.getInstance().resetStorage();
        } catch (IOException e) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String strValueOf = String.valueOf(e.getMessage());
                Log.d("FirebaseInstanceId", strValueOf.length() != 0 ? "Error creating file in no backup dir: ".concat(strValueOf) : new String("Error creating file in no backup dir: "));
            }
        }
    }

    public synchronized boolean isEmpty() {
        return this.store.getAll().isEmpty();
    }

    private String createTokenKey(String str, String str2, String str3) {
        return new StringBuilder(String.valueOf(str).length() + 4 + String.valueOf(str2).length() + String.valueOf(str3).length()).append(str).append("|T|").append(str2).append("|").append(str3).toString();
    }

    static String createSubtypeInfoKey(String str, String str2) {
        return new StringBuilder(String.valueOf(str).length() + 3 + String.valueOf(str2).length()).append(str).append("|S|").append(str2).toString();
    }

    public synchronized void deleteAll() {
        this.subtypeCreationTimes.clear();
        this.store.edit().clear().commit();
    }

    public synchronized Token getToken(String str, String str2, String str3) {
        return Token.parse(this.store.getString(createTokenKey(str, str2, str3), null));
    }

    public synchronized void saveToken(String str, String str2, String str3, String str4, String str5) {
        String strEncode = Token.encode(str4, str5, System.currentTimeMillis());
        if (strEncode == null) {
            return;
        }
        SharedPreferences.Editor editorEdit = this.store.edit();
        editorEdit.putString(createTokenKey(str, str2, str3), strEncode);
        editorEdit.commit();
    }

    public synchronized void deleteToken(String str, String str2, String str3) {
        String strCreateTokenKey = createTokenKey(str, str2, str3);
        SharedPreferences.Editor editorEdit = this.store.edit();
        editorEdit.remove(strCreateTokenKey);
        editorEdit.commit();
    }

    public synchronized long getCreationTime(String str) {
        Long l = this.subtypeCreationTimes.get(str);
        if (l != null) {
            return l.longValue();
        }
        return getCreationTimeFromSharedPreferences(str);
    }

    private long getCreationTimeFromSharedPreferences(String str) {
        String string = this.store.getString(createSubtypeInfoKey(str, "cre"), null);
        if (string == null) {
            return 0L;
        }
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException unused) {
            return 0L;
        }
    }

    public synchronized long setCreationTime(String str) {
        long jWriteCreationTimeToSharedPreferences;
        jWriteCreationTimeToSharedPreferences = writeCreationTimeToSharedPreferences(str);
        this.subtypeCreationTimes.put(str, Long.valueOf(jWriteCreationTimeToSharedPreferences));
        return jWriteCreationTimeToSharedPreferences;
    }

    private long writeCreationTimeToSharedPreferences(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (!this.store.contains(createSubtypeInfoKey(str, "cre"))) {
            SharedPreferences.Editor editorEdit = this.store.edit();
            editorEdit.putString(createSubtypeInfoKey(str, "cre"), String.valueOf(jCurrentTimeMillis));
            editorEdit.commit();
            return jCurrentTimeMillis;
        }
        return getCreationTimeFromSharedPreferences(str);
    }
}
