package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzht;
import java.io.IOException;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
abstract class zzhn<T extends zzht<T>> {
    zzhn() {
    }

    abstract int zza(Map.Entry<?, ?> entry);

    abstract zzhr<T> zza(Object obj);

    abstract Object zza(zzhl zzhlVar, zzjj zzjjVar, int i);

    abstract <UT, UB> UB zza(zzjy zzjyVar, Object obj, zzhl zzhlVar, zzhr<T> zzhrVar, UB ub, zzkt<UT, UB> zzktVar) throws IOException;

    abstract void zza(zzgp zzgpVar, Object obj, zzhl zzhlVar, zzhr<T> zzhrVar) throws IOException;

    abstract void zza(zzjy zzjyVar, Object obj, zzhl zzhlVar, zzhr<T> zzhrVar) throws IOException;

    abstract void zza(zzlm zzlmVar, Map.Entry<?, ?> entry) throws IOException;

    abstract boolean zza(zzjj zzjjVar);

    abstract zzhr<T> zzb(Object obj);

    abstract void zzc(Object obj);
}
