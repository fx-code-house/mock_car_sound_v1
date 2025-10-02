package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzhy;
import java.io.IOException;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzhp extends zzhn<zzhy.zzc> {
    zzhp() {
    }

    @Override // com.google.android.gms.internal.measurement.zzhn
    final boolean zza(zzjj zzjjVar) {
        return zzjjVar instanceof zzhy.zzd;
    }

    @Override // com.google.android.gms.internal.measurement.zzhn
    final zzhr<zzhy.zzc> zza(Object obj) {
        return ((zzhy.zzd) obj).zzc;
    }

    @Override // com.google.android.gms.internal.measurement.zzhn
    final zzhr<zzhy.zzc> zzb(Object obj) {
        return ((zzhy.zzd) obj).zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzhn
    final void zzc(Object obj) {
        zza(obj).zzb();
    }

    @Override // com.google.android.gms.internal.measurement.zzhn
    final <UT, UB> UB zza(zzjy zzjyVar, Object obj, zzhl zzhlVar, zzhr<zzhy.zzc> zzhrVar, UB ub, zzkt<UT, UB> zzktVar) throws IOException {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.measurement.zzhn
    final int zza(Map.Entry<?, ?> entry) {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.measurement.zzhn
    final void zza(zzlm zzlmVar, Map.Entry<?, ?> entry) throws IOException {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.measurement.zzhn
    final Object zza(zzhl zzhlVar, zzjj zzjjVar, int i) {
        return zzhlVar.zza(zzjjVar, i);
    }

    @Override // com.google.android.gms.internal.measurement.zzhn
    final void zza(zzjy zzjyVar, Object obj, zzhl zzhlVar, zzhr<zzhy.zzc> zzhrVar) throws IOException {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.measurement.zzhn
    final void zza(zzgp zzgpVar, Object obj, zzhl zzhlVar, zzhr<zzhy.zzc> zzhrVar) throws IOException {
        throw new NoSuchMethodError();
    }
}
