package com.google.android.gms.internal.measurement;

import android.net.Uri;
import java.util.Map;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzdd {
    private final Map<String, Map<String, String>> zza;

    zzdd(Map<String, Map<String, String>> map) {
        this.zza = map;
    }

    @Nullable
    public final String zza(@Nullable Uri uri, @Nullable String str, @Nullable String str2, String str3) {
        if (uri == null) {
            return null;
        }
        Map<String, String> map = this.zza.get(uri.toString());
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
