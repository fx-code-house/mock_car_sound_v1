package com.google.android.gms.internal.wearable;

import java.io.IOException;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzbc {
    private final zzbb zza;

    private zzbc(zzbb zzbbVar) {
        zzca.zzb(zzbbVar, "output");
        this.zza = zzbbVar;
        zzbbVar.zza = this;
    }

    public static zzbc zza(zzbb zzbbVar) {
        zzbc zzbcVar = zzbbVar.zza;
        return zzbcVar != null ? zzbcVar : new zzbc(zzbbVar);
    }

    public final void zzE(int i, List<String> list) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzch)) {
            while (i2 < list.size()) {
                this.zza.zzh(i, list.get(i2));
                i2++;
            }
            return;
        }
        zzch zzchVar = (zzch) list;
        while (i2 < list.size()) {
            Object objZzg = zzchVar.zzg(i2);
            if (objZzg instanceof String) {
                this.zza.zzh(i, (String) objZzg);
            } else {
                this.zza.zzi(i, (zzau) objZzg);
            }
            i2++;
        }
    }

    public final void zzF(int i, List<zzau> list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zza.zzi(i, list.get(i2));
        }
    }

    public final void zzb(int i, int i2) throws IOException {
        this.zza.zzd(i, i2);
    }

    public final void zzc(int i, long j) throws IOException {
        this.zza.zze(i, j);
    }

    public final void zzd(int i, long j) throws IOException {
        this.zza.zzf(i, j);
    }

    public final void zze(int i, float f) throws IOException {
        this.zza.zzd(i, Float.floatToRawIntBits(f));
    }

    public final void zzf(int i, double d) throws IOException {
        this.zza.zzf(i, Double.doubleToRawLongBits(d));
    }

    public final void zzg(int i, int i2) throws IOException {
        this.zza.zzb(i, i2);
    }

    public final void zzh(int i, long j) throws IOException {
        this.zza.zze(i, j);
    }

    public final void zzi(int i, int i2) throws IOException {
        this.zza.zzb(i, i2);
    }

    public final void zzj(int i, long j) throws IOException {
        this.zza.zzf(i, j);
    }

    public final void zzk(int i, int i2) throws IOException {
        this.zza.zzd(i, i2);
    }

    public final void zzl(int i, boolean z) throws IOException {
        this.zza.zzg(i, z);
    }

    public final void zzm(int i, String str) throws IOException {
        this.zza.zzh(i, str);
    }

    public final void zzn(int i, zzau zzauVar) throws IOException {
        this.zza.zzi(i, zzauVar);
    }

    public final void zzo(int i, int i2) throws IOException {
        this.zza.zzc(i, i2);
    }

    public final void zzp(int i, int i2) throws IOException {
        this.zza.zzc(i, (i2 >> 31) ^ (i2 + i2));
    }

    public final void zzq(int i, long j) throws IOException {
        this.zza.zze(i, (j >> 63) ^ (j + j));
    }

    public final void zzr(int i, Object obj, zzdi zzdiVar) throws IOException {
        zzcx zzcxVar = (zzcx) obj;
        zzaz zzazVar = (zzaz) this.zza;
        zzazVar.zzl((i << 3) | 2);
        zzaf zzafVar = (zzaf) zzcxVar;
        int iZzJ = zzafVar.zzJ();
        if (iZzJ == -1) {
            iZzJ = zzdiVar.zze(zzafVar);
            zzafVar.zzK(iZzJ);
        }
        zzazVar.zzl(iZzJ);
        zzdiVar.zzm(zzcxVar, zzazVar.zza);
    }

    public final void zzs(int i, Object obj, zzdi zzdiVar) throws IOException {
        zzbb zzbbVar = this.zza;
        zzbbVar.zza(i, 3);
        zzdiVar.zzm((zzcx) obj, zzbbVar.zza);
        zzbbVar.zza(i, 4);
    }

    public final void zzt(int i) throws IOException {
        this.zza.zza(i, 3);
    }

    public final void zzu(int i) throws IOException {
        this.zza.zza(i, 4);
    }

    public final void zzC(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzb(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zza(i, 2);
        int iZzv = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzv += zzbb.zzv(list.get(i3).intValue());
        }
        this.zza.zzl(iZzv);
        while (i2 < list.size()) {
            this.zza.zzk(list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzD(int i, List<Boolean> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzg(i, list.get(i2).booleanValue());
                i2++;
            }
            return;
        }
        this.zza.zza(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).booleanValue();
            i3++;
        }
        this.zza.zzl(i3);
        while (i2 < list.size()) {
            this.zza.zzj(list.get(i2).booleanValue() ? (byte) 1 : (byte) 0);
            i2++;
        }
    }

    public final void zzG(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzc(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zza(i, 2);
        int iZzw = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzw += zzbb.zzw(list.get(i3).intValue());
        }
        this.zza.zzl(iZzw);
        while (i2 < list.size()) {
            this.zza.zzl(list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzH(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzd(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zza(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).intValue();
            i3 += 4;
        }
        this.zza.zzl(i3);
        while (i2 < list.size()) {
            this.zza.zzm(list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzI(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzf(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zza.zza(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).longValue();
            i3 += 8;
        }
        this.zza.zzl(i3);
        while (i2 < list.size()) {
            this.zza.zzo(list.get(i2).longValue());
            i2++;
        }
    }

    public final void zzJ(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                zzbb zzbbVar = this.zza;
                int iIntValue = list.get(i2).intValue();
                zzbbVar.zzc(i, (iIntValue >> 31) ^ (iIntValue + iIntValue));
                i2++;
            }
            return;
        }
        this.zza.zza(i, 2);
        int iZzw = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            int iIntValue2 = list.get(i3).intValue();
            iZzw += zzbb.zzw((iIntValue2 >> 31) ^ (iIntValue2 + iIntValue2));
        }
        this.zza.zzl(iZzw);
        while (i2 < list.size()) {
            zzbb zzbbVar2 = this.zza;
            int iIntValue3 = list.get(i2).intValue();
            zzbbVar2.zzl((iIntValue3 >> 31) ^ (iIntValue3 + iIntValue3));
            i2++;
        }
    }

    public final void zzK(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                zzbb zzbbVar = this.zza;
                long jLongValue = list.get(i2).longValue();
                zzbbVar.zze(i, (jLongValue >> 63) ^ (jLongValue + jLongValue));
                i2++;
            }
            return;
        }
        this.zza.zza(i, 2);
        int iZzx = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            long jLongValue2 = list.get(i3).longValue();
            iZzx += zzbb.zzx((jLongValue2 >> 63) ^ (jLongValue2 + jLongValue2));
        }
        this.zza.zzl(iZzx);
        while (i2 < list.size()) {
            zzbb zzbbVar2 = this.zza;
            long jLongValue3 = list.get(i2).longValue();
            zzbbVar2.zzn((jLongValue3 >> 63) ^ (jLongValue3 + jLongValue3));
            i2++;
        }
    }

    public final void zzv(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzb(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zza(i, 2);
        int iZzv = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzv += zzbb.zzv(list.get(i3).intValue());
        }
        this.zza.zzl(iZzv);
        while (i2 < list.size()) {
            this.zza.zzk(list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzw(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzd(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zza(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).intValue();
            i3 += 4;
        }
        this.zza.zzl(i3);
        while (i2 < list.size()) {
            this.zza.zzm(list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzx(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zze(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zza.zza(i, 2);
        int iZzx = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzx += zzbb.zzx(list.get(i3).longValue());
        }
        this.zza.zzl(iZzx);
        while (i2 < list.size()) {
            this.zza.zzn(list.get(i2).longValue());
            i2++;
        }
    }

    public final void zzy(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zze(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zza.zza(i, 2);
        int iZzx = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzx += zzbb.zzx(list.get(i3).longValue());
        }
        this.zza.zzl(iZzx);
        while (i2 < list.size()) {
            this.zza.zzn(list.get(i2).longValue());
            i2++;
        }
    }

    public final void zzz(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzf(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zza.zza(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).longValue();
            i3 += 8;
        }
        this.zza.zzl(i3);
        while (i2 < list.size()) {
            this.zza.zzo(list.get(i2).longValue());
            i2++;
        }
    }

    public final void zzA(int i, List<Float> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzd(i, Float.floatToRawIntBits(list.get(i2).floatValue()));
                i2++;
            }
            return;
        }
        this.zza.zza(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).floatValue();
            i3 += 4;
        }
        this.zza.zzl(i3);
        while (i2 < list.size()) {
            this.zza.zzm(Float.floatToRawIntBits(list.get(i2).floatValue()));
            i2++;
        }
    }

    public final void zzB(int i, List<Double> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzf(i, Double.doubleToRawLongBits(list.get(i2).doubleValue()));
                i2++;
            }
            return;
        }
        this.zza.zza(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).doubleValue();
            i3 += 8;
        }
        this.zza.zzl(i3);
        while (i2 < list.size()) {
            this.zza.zzo(Double.doubleToRawLongBits(list.get(i2).doubleValue()));
            i2++;
        }
    }
}
