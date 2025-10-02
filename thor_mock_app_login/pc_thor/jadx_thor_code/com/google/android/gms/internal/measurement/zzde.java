package com.google.android.gms.internal.measurement;

import android.net.Uri;
import androidx.collection.ArrayMap;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzde {
    private static final ArrayMap<String, Uri> zza = new ArrayMap<>();

    public static synchronized Uri zza(String str) {
        Uri uri;
        ArrayMap<String, Uri> arrayMap = zza;
        uri = arrayMap.get(str);
        if (uri == null) {
            String strValueOf = String.valueOf(Uri.encode(str));
            uri = Uri.parse(strValueOf.length() != 0 ? "content://com.google.android.gms.phenotype/".concat(strValueOf) : new String("content://com.google.android.gms.phenotype/"));
            arrayMap.put(str, uri);
        }
        return uri;
    }
}
