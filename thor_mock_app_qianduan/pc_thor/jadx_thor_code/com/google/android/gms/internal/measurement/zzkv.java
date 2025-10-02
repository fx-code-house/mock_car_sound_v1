package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzkv extends zzkt<zzks, zzks> {
    zzkv() {
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final boolean zza(zzjy zzjyVar) {
        return false;
    }

    /* renamed from: zza, reason: avoid collision after fix types in other method */
    private static void zza2(Object obj, zzks zzksVar) {
        ((zzhy) obj).zzb = zzksVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final void zzd(Object obj) {
        ((zzhy) obj).zzb.zzc();
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ int zzf(zzks zzksVar) {
        return zzksVar.zze();
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ int zze(zzks zzksVar) {
        return zzksVar.zzd();
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ zzks zzc(zzks zzksVar, zzks zzksVar2) {
        zzks zzksVar3 = zzksVar;
        zzks zzksVar4 = zzksVar2;
        return zzksVar4.equals(zzks.zza()) ? zzksVar3 : zzks.zza(zzksVar3, zzksVar4);
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ void zzb(zzks zzksVar, zzlm zzlmVar) throws IOException {
        zzksVar.zza(zzlmVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ void zza(zzks zzksVar, zzlm zzlmVar) throws IOException {
        zzksVar.zzb(zzlmVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ void zzb(Object obj, zzks zzksVar) {
        zza2(obj, zzksVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ zzks zzc(Object obj) {
        zzks zzksVar = ((zzhy) obj).zzb;
        if (zzksVar != zzks.zza()) {
            return zzksVar;
        }
        zzks zzksVarZzb = zzks.zzb();
        zza2(obj, zzksVarZzb);
        return zzksVarZzb;
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ zzks zzb(Object obj) {
        return ((zzhy) obj).zzb;
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* bridge */ /* synthetic */ void zza(Object obj, zzks zzksVar) {
        zza2(obj, zzksVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ zzks zza(zzks zzksVar) {
        zzks zzksVar2 = zzksVar;
        zzksVar2.zzc();
        return zzksVar2;
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ zzks zza() {
        return zzks.zzb();
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ void zza(zzks zzksVar, int i, zzks zzksVar2) {
        zzksVar.zza((i << 3) | 3, zzksVar2);
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ void zza(zzks zzksVar, int i, zzgp zzgpVar) {
        zzksVar.zza((i << 3) | 2, zzgpVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ void zzb(zzks zzksVar, int i, long j) {
        zzksVar.zza((i << 3) | 1, Long.valueOf(j));
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ void zza(zzks zzksVar, int i, int i2) {
        zzksVar.zza((i << 3) | 5, Integer.valueOf(i2));
    }

    @Override // com.google.android.gms.internal.measurement.zzkt
    final /* synthetic */ void zza(zzks zzksVar, int i, long j) {
        zzksVar.zza(i << 3, Long.valueOf(j));
    }
}
