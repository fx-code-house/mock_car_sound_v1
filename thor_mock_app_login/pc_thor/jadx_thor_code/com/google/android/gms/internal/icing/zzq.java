package com.google.android.gms.internal.icing;

import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzq {
    static final String[] zzy;
    private static final Map<String, Integer> zzz;

    public static String zza(int i) {
        if (i < 0) {
            return null;
        }
        String[] strArr = zzy;
        if (i >= strArr.length) {
            return null;
        }
        return strArr[i];
    }

    public static int zzb(String str) {
        Integer num = zzz.get(str);
        if (num == null) {
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 44).append("[").append(str).append("] is not a valid global search section name").toString());
        }
        return num.intValue();
    }

    static {
        String[] strArr = {"text1", "text2", "icon", "intent_action", "intent_data", "intent_data_id", "intent_extra_data", "suggest_large_icon", "intent_activity", "thing_proto"};
        zzy = strArr;
        zzz = new HashMap(strArr.length);
        int i = 0;
        while (true) {
            String[] strArr2 = zzy;
            if (i >= strArr2.length) {
                return;
            }
            zzz.put(strArr2[i], Integer.valueOf(i));
            i++;
        }
    }
}
