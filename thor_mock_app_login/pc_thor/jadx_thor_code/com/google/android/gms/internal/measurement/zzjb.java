package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzjb<K, V> {
    static <K, V> void zza(zzhi zzhiVar, zzja<K, V> zzjaVar, K k, V v) throws IOException {
        zzhr.zza(zzhiVar, zzjaVar.zza, 1, k);
        zzhr.zza(zzhiVar, zzjaVar.zzc, 2, v);
    }

    static <K, V> int zza(zzja<K, V> zzjaVar, K k, V v) {
        return zzhr.zza(zzjaVar.zza, 1, k) + zzhr.zza(zzjaVar.zzc, 2, v);
    }
}
