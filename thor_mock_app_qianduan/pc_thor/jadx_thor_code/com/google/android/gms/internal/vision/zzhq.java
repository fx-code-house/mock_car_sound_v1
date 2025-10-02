package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzhv;
import java.io.IOException;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
abstract class zzhq<T extends zzhv<T>> {
    zzhq() {
    }

    abstract int zza(Map.Entry<?, ?> entry);

    abstract Object zza(zzho zzhoVar, zzjn zzjnVar, int i);

    abstract <UT, UB> UB zza(zzkd zzkdVar, Object obj, zzho zzhoVar, zzht<T> zzhtVar, UB ub, zzku<UT, UB> zzkuVar) throws IOException;

    abstract void zza(zzgs zzgsVar, Object obj, zzho zzhoVar, zzht<T> zzhtVar) throws IOException;

    abstract void zza(zzkd zzkdVar, Object obj, zzho zzhoVar, zzht<T> zzhtVar) throws IOException;

    abstract void zza(zzlr zzlrVar, Map.Entry<?, ?> entry) throws IOException;

    abstract boolean zze(zzjn zzjnVar);

    abstract zzht<T> zzh(Object obj);

    abstract zzht<T> zzi(Object obj);

    abstract void zzj(Object obj);
}
