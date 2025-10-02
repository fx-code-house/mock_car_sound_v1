package com.google.android.gms.internal.vision;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public class zzaq {
    private static HashMap<String, String> zzfe;
    private static Object zzfj;
    private static boolean zzfk;
    public static final Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static final Uri zzfa = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    public static final Pattern zzfb = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    private static final Pattern zzfc = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static final AtomicBoolean zzfd = new AtomicBoolean();
    private static final HashMap<String, Boolean> zzff = new HashMap<>();
    private static final HashMap<String, Integer> zzfg = new HashMap<>();
    private static final HashMap<String, Long> zzfh = new HashMap<>();
    private static final HashMap<String, Float> zzfi = new HashMap<>();
    private static String[] zzfl = new String[0];

    public static String zza(ContentResolver contentResolver, String str, String str2) {
        synchronized (zzaq.class) {
            if (zzfe == null) {
                zzfd.set(false);
                zzfe = new HashMap<>();
                zzfj = new Object();
                zzfk = false;
                contentResolver.registerContentObserver(CONTENT_URI, true, new zzat(null));
            } else if (zzfd.getAndSet(false)) {
                zzfe.clear();
                zzff.clear();
                zzfg.clear();
                zzfh.clear();
                zzfi.clear();
                zzfj = new Object();
                zzfk = false;
            }
            Object obj = zzfj;
            if (zzfe.containsKey(str)) {
                String str3 = zzfe.get(str);
                return str3 != null ? str3 : null;
            }
            for (String str4 : zzfl) {
                if (str.startsWith(str4)) {
                    if (!zzfk || zzfe.isEmpty()) {
                        zzfe.putAll(zza(contentResolver, zzfl));
                        zzfk = true;
                        if (zzfe.containsKey(str)) {
                            String str5 = zzfe.get(str);
                            return str5 != null ? str5 : null;
                        }
                    }
                    return null;
                }
            }
            Cursor cursorQuery = contentResolver.query(CONTENT_URI, null, null, new String[]{str}, null);
            if (cursorQuery == null) {
                return null;
            }
            try {
                if (!cursorQuery.moveToFirst()) {
                    zza(obj, str, (String) null);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
                String string = cursorQuery.getString(1);
                if (string != null && string.equals(null)) {
                    string = null;
                }
                zza(obj, str, string);
                String str6 = string != null ? string : null;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return str6;
            } finally {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
        }
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (zzaq.class) {
            if (obj == zzfj) {
                zzfe.put(str, str2);
            }
        }
    }

    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor cursorQuery = contentResolver.query(zzfa, null, null, strArr, null);
        TreeMap treeMap = new TreeMap();
        if (cursorQuery == null) {
            return treeMap;
        }
        while (cursorQuery.moveToNext()) {
            try {
                treeMap.put(cursorQuery.getString(0), cursorQuery.getString(1));
            } finally {
                cursorQuery.close();
            }
        }
        return treeMap;
    }
}
