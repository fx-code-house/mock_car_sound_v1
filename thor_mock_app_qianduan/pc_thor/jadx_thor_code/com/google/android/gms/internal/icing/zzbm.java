package com.google.android.gms.internal.icing;

import android.net.Uri;
import java.util.Map;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzbm {
    private final Map<String, Map<String, String>> map;

    zzbm(Map<String, Map<String, String>> map) {
        this.map = map;
    }

    @Nullable
    public final String zza(@Nullable Uri uri, @Nullable String str, @Nullable String str2, String str3) {
        if (uri != null) {
            str = uri.toString();
        } else if (str == null) {
            return null;
        }
        Map<String, String> map = this.map.get(str);
        if (map == null) {
            return null;
        }
        if (str2 != null) {
            String strValueOf = String.valueOf(str2);
            String strValueOf2 = String.valueOf(str3);
            str3 = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
        }
        return map.get(str3);
    }
}
