package com.google.android.gms.internal.vision;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzkw extends zzku<zzkx, zzkx> {
    zzkw() {
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final boolean zza(zzkd zzkdVar) {
        return false;
    }

    private static void zza(Object obj, zzkx zzkxVar) {
        ((zzid) obj).zzxz = zzkxVar;
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final void zzj(Object obj) {
        ((zzid) obj).zzxz.zzej();
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ int zzu(zzkx zzkxVar) {
        return zzkxVar.zzgz();
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ int zzaa(zzkx zzkxVar) {
        return zzkxVar.zzjd();
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ zzkx zzh(zzkx zzkxVar, zzkx zzkxVar2) {
        zzkx zzkxVar3 = zzkxVar;
        zzkx zzkxVar4 = zzkxVar2;
        return zzkxVar4.equals(zzkx.zzjb()) ? zzkxVar3 : zzkx.zza(zzkxVar3, zzkxVar4);
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ void zzc(zzkx zzkxVar, zzlr zzlrVar) throws IOException {
        zzkxVar.zza(zzlrVar);
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ void zza(zzkx zzkxVar, zzlr zzlrVar) throws IOException {
        zzkxVar.zzb(zzlrVar);
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ void zzg(Object obj, zzkx zzkxVar) {
        zza(obj, zzkxVar);
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ zzkx zzz(Object obj) {
        zzkx zzkxVar = ((zzid) obj).zzxz;
        if (zzkxVar != zzkx.zzjb()) {
            return zzkxVar;
        }
        zzkx zzkxVarZzjc = zzkx.zzjc();
        zza(obj, zzkxVarZzjc);
        return zzkxVarZzjc;
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ zzkx zzy(Object obj) {
        return ((zzid) obj).zzxz;
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ void zzf(Object obj, zzkx zzkxVar) {
        zza(obj, zzkxVar);
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ zzkx zzq(zzkx zzkxVar) {
        zzkx zzkxVar2 = zzkxVar;
        zzkxVar2.zzej();
        return zzkxVar2;
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ zzkx zzja() {
        return zzkx.zzjc();
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ void zza(zzkx zzkxVar, int i, zzkx zzkxVar2) {
        zzkxVar.zzb((i << 3) | 3, zzkxVar2);
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ void zza(zzkx zzkxVar, int i, zzgs zzgsVar) {
        zzkxVar.zzb((i << 3) | 2, zzgsVar);
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ void zzb(zzkx zzkxVar, int i, long j) {
        zzkxVar.zzb((i << 3) | 1, Long.valueOf(j));
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ void zzd(zzkx zzkxVar, int i, int i2) {
        zzkxVar.zzb((i << 3) | 5, Integer.valueOf(i2));
    }

    @Override // com.google.android.gms.internal.vision.zzku
    final /* synthetic */ void zza(zzkx zzkxVar, int i, long j) {
        zzkxVar.zzb(i << 3, Long.valueOf(j));
    }
}
