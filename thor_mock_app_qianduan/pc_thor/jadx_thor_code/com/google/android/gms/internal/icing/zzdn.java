package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdu;
import java.io.IOException;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
abstract class zzdn<T extends zzdu<T>> {
    zzdn() {
    }

    abstract int zza(Map.Entry<?, ?> entry);

    abstract void zza(zzhg zzhgVar, Map.Entry<?, ?> entry) throws IOException;

    abstract zzds<T> zzd(Object obj);

    abstract zzds<T> zze(Object obj);

    abstract boolean zze(zzfh zzfhVar);

    abstract void zzf(Object obj);
}
