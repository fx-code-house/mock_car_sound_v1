package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzdm implements zzhg {
    private final zzdk zzgo;

    public static zzdm zza(zzdk zzdkVar) {
        return zzdkVar.zzgy != null ? zzdkVar.zzgy : new zzdm(zzdkVar);
    }

    private zzdm(zzdk zzdkVar) {
        zzdk zzdkVar2 = (zzdk) zzeb.zza(zzdkVar, "output");
        this.zzgo = zzdkVar2;
        zzdkVar2.zzgy = this;
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final int zzay() {
        return zzdx.zze.zzkx;
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzm(int i, int i2) throws IOException {
        this.zzgo.zzf(i, i2);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzi(int i, long j) throws IOException {
        this.zzgo.zza(i, j);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzj(int i, long j) throws IOException {
        this.zzgo.zzc(i, j);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i, float f) throws IOException {
        this.zzgo.zza(i, f);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i, double d) throws IOException {
        this.zzgo.zza(i, d);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzn(int i, int i2) throws IOException {
        this.zzgo.zzc(i, i2);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i, long j) throws IOException {
        this.zzgo.zza(i, j);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzc(int i, int i2) throws IOException {
        this.zzgo.zzc(i, i2);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzc(int i, long j) throws IOException {
        this.zzgo.zzc(i, j);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzf(int i, int i2) throws IOException {
        this.zzgo.zzf(i, i2);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i, boolean z) throws IOException {
        this.zzgo.zza(i, z);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i, String str) throws IOException {
        this.zzgo.zza(i, str);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i, zzct zzctVar) throws IOException {
        this.zzgo.zza(i, zzctVar);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzd(int i, int i2) throws IOException {
        this.zzgo.zzd(i, i2);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zze(int i, int i2) throws IOException {
        this.zzgo.zze(i, i2);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzb(int i, long j) throws IOException {
        this.zzgo.zzb(i, j);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i, Object obj, zzfu zzfuVar) throws IOException {
        this.zzgo.zza(i, (zzfh) obj, zzfuVar);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzb(int i, Object obj, zzfu zzfuVar) throws IOException {
        zzdk zzdkVar = this.zzgo;
        zzdkVar.zzb(i, 3);
        zzfuVar.zza((zzfh) obj, zzdkVar.zzgy);
        zzdkVar.zzb(i, 4);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzab(int i) throws IOException {
        this.zzgo.zzb(i, 3);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzac(int i) throws IOException {
        this.zzgo.zzb(i, 4);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i, Object obj) throws IOException {
        if (obj instanceof zzct) {
            this.zzgo.zzb(i, (zzct) obj);
        } else {
            this.zzgo.zza(i, (zzfh) obj);
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzgo.zzb(i, 2);
            int iZzt = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzt += zzdk.zzt(list.get(i3).intValue());
            }
            this.zzgo.zzp(iZzt);
            while (i2 < list.size()) {
                this.zzgo.zzo(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zzc(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzb(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzgo.zzb(i, 2);
            int iZzw = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzw += zzdk.zzw(list.get(i3).intValue());
            }
            this.zzgo.zzp(iZzw);
            while (i2 < list.size()) {
                this.zzgo.zzr(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zzf(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzc(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzgo.zzb(i, 2);
            int iZze = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZze += zzdk.zze(list.get(i3).longValue());
            }
            this.zzgo.zzp(iZze);
            while (i2 < list.size()) {
                this.zzgo.zzb(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zza(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzd(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzgo.zzb(i, 2);
            int iZzf = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzf += zzdk.zzf(list.get(i3).longValue());
            }
            this.zzgo.zzp(iZzf);
            while (i2 < list.size()) {
                this.zzgo.zzb(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zza(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zze(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzgo.zzb(i, 2);
            int iZzh = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzh += zzdk.zzh(list.get(i3).longValue());
            }
            this.zzgo.zzp(iZzh);
            while (i2 < list.size()) {
                this.zzgo.zzd(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zzc(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzf(int i, List<Float> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzgo.zzb(i, 2);
            int iZzb = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzb += zzdk.zzb(list.get(i3).floatValue());
            }
            this.zzgo.zzp(iZzb);
            while (i2 < list.size()) {
                this.zzgo.zza(list.get(i2).floatValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zza(i, list.get(i2).floatValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzg(int i, List<Double> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzgo.zzb(i, 2);
            int iZzb = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzb += zzdk.zzb(list.get(i3).doubleValue());
            }
            this.zzgo.zzp(iZzb);
            while (i2 < list.size()) {
                this.zzgo.zza(list.get(i2).doubleValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zza(i, list.get(i2).doubleValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzh(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzgo.zzb(i, 2);
            int iZzy = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzy += zzdk.zzy(list.get(i3).intValue());
            }
            this.zzgo.zzp(iZzy);
            while (i2 < list.size()) {
                this.zzgo.zzo(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zzc(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzi(int i, List<Boolean> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzgo.zzb(i, 2);
            int iZzf = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzf += zzdk.zzf(list.get(i3).booleanValue());
            }
            this.zzgo.zzp(iZzf);
            while (i2 < list.size()) {
                this.zzgo.zze(list.get(i2).booleanValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zza(i, list.get(i2).booleanValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i, List<String> list) throws IOException {
        int i2 = 0;
        if (list instanceof zzeo) {
            zzeo zzeoVar = (zzeo) list;
            while (i2 < list.size()) {
                Object objZzad = zzeoVar.zzad(i2);
                if (objZzad instanceof String) {
                    this.zzgo.zza(i, (String) objZzad);
                } else {
                    this.zzgo.zza(i, (zzct) objZzad);
                }
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zza(i, list.get(i2));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzb(int i, List<zzct> list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zzgo.zza(i, list.get(i2));
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzj(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzgo.zzb(i, 2);
            int iZzu = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzu += zzdk.zzu(list.get(i3).intValue());
            }
            this.zzgo.zzp(iZzu);
            while (i2 < list.size()) {
                this.zzgo.zzp(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zzd(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzk(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzgo.zzb(i, 2);
            int iZzx = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzx += zzdk.zzx(list.get(i3).intValue());
            }
            this.zzgo.zzp(iZzx);
            while (i2 < list.size()) {
                this.zzgo.zzr(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zzf(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzl(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzgo.zzb(i, 2);
            int iZzi = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzi += zzdk.zzi(list.get(i3).longValue());
            }
            this.zzgo.zzp(iZzi);
            while (i2 < list.size()) {
                this.zzgo.zzd(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zzc(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzm(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzgo.zzb(i, 2);
            int iZzv = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzv += zzdk.zzv(list.get(i3).intValue());
            }
            this.zzgo.zzp(iZzv);
            while (i2 < list.size()) {
                this.zzgo.zzq(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zze(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzn(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzgo.zzb(i, 2);
            int iZzg = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzg += zzdk.zzg(list.get(i3).longValue());
            }
            this.zzgo.zzp(iZzg);
            while (i2 < list.size()) {
                this.zzgo.zzc(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzgo.zzb(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i, List<?> list, zzfu zzfuVar) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zza(i, list.get(i2), zzfuVar);
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzb(int i, List<?> list, zzfu zzfuVar) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzb(i, list.get(i2), zzfuVar);
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final <K, V> void zza(int i, zzey<K, V> zzeyVar, Map<K, V> map) throws IOException {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.zzgo.zzb(i, 2);
            this.zzgo.zzp(zzds.zza(zzeyVar.zzmi, 1, entry.getKey()) + zzds.zza(zzeyVar.zzmj, 2, entry.getValue()));
            zzdk zzdkVar = this.zzgo;
            K key = entry.getKey();
            V value = entry.getValue();
            zzds.zza(zzdkVar, zzeyVar.zzmi, 1, key);
            zzds.zza(zzdkVar, zzeyVar.zzmj, 2, value);
        }
    }
}
