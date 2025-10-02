package com.google.android.gms.internal.vision;

import android.net.Uri;
import androidx.collection.ArrayMap;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzbj {
    private static final ArrayMap<String, Uri> zzgp = new ArrayMap<>();

    public static synchronized Uri getContentProviderUri(String str) {
        Uri uri;
        ArrayMap<String, Uri> arrayMap = zzgp;
        uri = arrayMap.get(str);
        if (uri == null) {
            String strValueOf = String.valueOf(Uri.encode(str));
            uri = Uri.parse(strValueOf.length() != 0 ? "content://com.google.android.gms.phenotype/".concat(strValueOf) : new String("content://com.google.android.gms.phenotype/"));
            arrayMap.put(str, uri);
        }
        return uri;
    }
}
