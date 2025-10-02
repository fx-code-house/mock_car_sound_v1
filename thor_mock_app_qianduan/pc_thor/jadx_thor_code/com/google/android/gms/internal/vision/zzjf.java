package com.google.android.gms.internal.vision;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzjf<K, V> {
    static <K, V> void zza(zzhl zzhlVar, zzje<K, V> zzjeVar, K k, V v) throws IOException {
        zzht.zza(zzhlVar, zzjeVar.zzaai, 1, k);
        zzht.zza(zzhlVar, zzjeVar.zzaak, 2, v);
    }

    static <K, V> int zza(zzje<K, V> zzjeVar, K k, V v) {
        return zzht.zza(zzjeVar.zzaai, 1, k) + zzht.zza(zzjeVar.zzaak, 2, v);
    }
}
