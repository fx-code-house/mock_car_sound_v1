package com.google.android.gms.internal.play_billing;

import java.io.IOException;
import java.util.List;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
final class zzbj implements zzey {
    private final zzbi zza;

    private zzbj(zzbi zzbiVar) {
        byte[] bArr = zzcg.zzd;
        this.zza = zzbiVar;
        zzbiVar.zza = this;
    }

    public static zzbj zza(zzbi zzbiVar) {
        zzbj zzbjVar = zzbiVar.zza;
        return zzbjVar != null ? zzbjVar : new zzbj(zzbiVar);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzA(int i, int i2) throws IOException {
        this.zza.zzp(i, (i2 >> 31) ^ (i2 + i2));
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzC(int i, long j) throws IOException {
        this.zza.zzr(i, (j >> 63) ^ (j + j));
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    @Deprecated
    public final void zzE(int i) throws IOException {
        this.zza.zzo(i, 3);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzF(int i, String str) throws IOException {
        this.zza.zzm(i, str);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzH(int i, int i2) throws IOException {
        this.zza.zzp(i, i2);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzJ(int i, long j) throws IOException {
        this.zza.zzr(i, j);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzb(int i, boolean z) throws IOException {
        this.zza.zzd(i, z);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzd(int i, zzba zzbaVar) throws IOException {
        this.zza.zze(i, zzbaVar);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zze(int i, List list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zza.zze(i, (zzba) list.get(i2));
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzf(int i, double d) throws IOException {
        this.zza.zzh(i, Double.doubleToRawLongBits(d));
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    @Deprecated
    public final void zzh(int i) throws IOException {
        this.zza.zzo(i, 4);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzi(int i, int i2) throws IOException {
        this.zza.zzj(i, i2);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzk(int i, int i2) throws IOException {
        this.zza.zzf(i, i2);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzm(int i, long j) throws IOException {
        this.zza.zzh(i, j);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzo(int i, float f) throws IOException {
        this.zza.zzf(i, Float.floatToRawIntBits(f));
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzq(int i, Object obj, zzdp zzdpVar) throws IOException {
        zzbi zzbiVar = this.zza;
        zzbiVar.zzo(i, 3);
        zzdpVar.zzi((zzdf) obj, zzbiVar.zza);
        zzbiVar.zzo(i, 4);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzr(int i, int i2) throws IOException {
        this.zza.zzj(i, i2);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzt(int i, long j) throws IOException {
        this.zza.zzr(i, j);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzv(int i, Object obj, zzdp zzdpVar) throws IOException {
        zzdf zzdfVar = (zzdf) obj;
        zzbf zzbfVar = (zzbf) this.zza;
        zzbfVar.zzq((i << 3) | 2);
        zzbfVar.zzq(((zzak) zzdfVar).zza(zzdpVar));
        zzdpVar.zzi(zzdfVar, zzbfVar.zza);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzw(int i, int i2) throws IOException {
        this.zza.zzf(i, i2);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzy(int i, long j) throws IOException {
        this.zza.zzh(i, j);
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzG(int i, List list) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzcn)) {
            while (i2 < list.size()) {
                this.zza.zzm(i, (String) list.get(i2));
                i2++;
            }
            return;
        }
        zzcn zzcnVar = (zzcn) list;
        while (i2 < list.size()) {
            Object objZzf = zzcnVar.zzf(i2);
            if (objZzf instanceof String) {
                this.zza.zzm(i, (String) objZzf);
            } else {
                this.zza.zze(i, (zzba) objZzf);
            }
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzI(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzp(i, ((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int iZzx = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzx += zzbi.zzx(((Integer) list.get(i3)).intValue());
        }
        this.zza.zzq(iZzx);
        while (i2 < list.size()) {
            this.zza.zzq(((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzK(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzr(i, ((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int iZzy = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzy += zzbi.zzy(((Long) list.get(i3)).longValue());
        }
        this.zza.zzq(iZzy);
        while (i2 < list.size()) {
            this.zza.zzs(((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzc(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzd(i, ((Boolean) list.get(i2)).booleanValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Boolean) list.get(i4)).booleanValue();
            i3++;
        }
        this.zza.zzq(i3);
        while (i2 < list.size()) {
            this.zza.zzb(((Boolean) list.get(i2)).booleanValue() ? (byte) 1 : (byte) 0);
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzl(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzf(i, ((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Integer) list.get(i4)).intValue();
            i3 += 4;
        }
        this.zza.zzq(i3);
        while (i2 < list.size()) {
            this.zza.zzg(((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzn(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzh(i, ((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Long) list.get(i4)).longValue();
            i3 += 8;
        }
        this.zza.zzq(i3);
        while (i2 < list.size()) {
            this.zza.zzi(((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzs(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzj(i, ((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int iZzu = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzu += zzbi.zzu(((Integer) list.get(i3)).intValue());
        }
        this.zza.zzq(iZzu);
        while (i2 < list.size()) {
            this.zza.zzk(((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzB(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                zzbi zzbiVar = this.zza;
                int iIntValue = ((Integer) list.get(i2)).intValue();
                zzbiVar.zzp(i, (iIntValue >> 31) ^ (iIntValue + iIntValue));
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int iZzx = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            int iIntValue2 = ((Integer) list.get(i3)).intValue();
            iZzx += zzbi.zzx((iIntValue2 >> 31) ^ (iIntValue2 + iIntValue2));
        }
        this.zza.zzq(iZzx);
        while (i2 < list.size()) {
            zzbi zzbiVar2 = this.zza;
            int iIntValue3 = ((Integer) list.get(i2)).intValue();
            zzbiVar2.zzq((iIntValue3 >> 31) ^ (iIntValue3 + iIntValue3));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzD(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                zzbi zzbiVar = this.zza;
                long jLongValue = ((Long) list.get(i2)).longValue();
                zzbiVar.zzr(i, (jLongValue >> 63) ^ (jLongValue + jLongValue));
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int iZzy = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            long jLongValue2 = ((Long) list.get(i3)).longValue();
            iZzy += zzbi.zzy((jLongValue2 >> 63) ^ (jLongValue2 + jLongValue2));
        }
        this.zza.zzq(iZzy);
        while (i2 < list.size()) {
            zzbi zzbiVar2 = this.zza;
            long jLongValue3 = ((Long) list.get(i2)).longValue();
            zzbiVar2.zzs((jLongValue3 >> 63) ^ (jLongValue3 + jLongValue3));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzg(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzh(i, Double.doubleToRawLongBits(((Double) list.get(i2)).doubleValue()));
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Double) list.get(i4)).doubleValue();
            i3 += 8;
        }
        this.zza.zzq(i3);
        while (i2 < list.size()) {
            this.zza.zzi(Double.doubleToRawLongBits(((Double) list.get(i2)).doubleValue()));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzj(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzj(i, ((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int iZzu = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzu += zzbi.zzu(((Integer) list.get(i3)).intValue());
        }
        this.zza.zzq(iZzu);
        while (i2 < list.size()) {
            this.zza.zzk(((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzp(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzf(i, Float.floatToRawIntBits(((Float) list.get(i2)).floatValue()));
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Float) list.get(i4)).floatValue();
            i3 += 4;
        }
        this.zza.zzq(i3);
        while (i2 < list.size()) {
            this.zza.zzg(Float.floatToRawIntBits(((Float) list.get(i2)).floatValue()));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzu(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzr(i, ((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int iZzy = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzy += zzbi.zzy(((Long) list.get(i3)).longValue());
        }
        this.zza.zzq(iZzy);
        while (i2 < list.size()) {
            this.zza.zzs(((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzx(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzf(i, ((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Integer) list.get(i4)).intValue();
            i3 += 4;
        }
        this.zza.zzq(i3);
        while (i2 < list.size()) {
            this.zza.zzg(((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzey
    public final void zzz(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzh(i, ((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Long) list.get(i4)).longValue();
            i3 += 8;
        }
        this.zza.zzq(i3);
        while (i2 < list.size()) {
            this.zza.zzi(((Long) list.get(i2)).longValue());
            i2++;
        }
    }
}
