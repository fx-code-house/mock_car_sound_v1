package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzhn implements zzlr {
    private final zzhl zztz;

    public static zzhn zza(zzhl zzhlVar) {
        return zzhlVar.zzuo != null ? zzhlVar.zzuo : new zzhn(zzhlVar);
    }

    private zzhn(zzhl zzhlVar) {
        zzhl zzhlVar2 = (zzhl) zzie.zza(zzhlVar, "output");
        this.zztz = zzhlVar2;
        zzhlVar2.zzuo = this;
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final int zzgd() {
        return zzid.zzf.zzys;
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzt(int i, int i2) throws IOException {
        this.zztz.zzm(i, i2);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzi(int i, long j) throws IOException {
        this.zztz.zza(i, j);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzj(int i, long j) throws IOException {
        this.zztz.zzc(i, j);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zza(int i, float f) throws IOException {
        this.zztz.zza(i, f);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zza(int i, double d) throws IOException {
        this.zztz.zza(i, d);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzu(int i, int i2) throws IOException {
        this.zztz.zzj(i, i2);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zza(int i, long j) throws IOException {
        this.zztz.zza(i, j);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzj(int i, int i2) throws IOException {
        this.zztz.zzj(i, i2);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzc(int i, long j) throws IOException {
        this.zztz.zzc(i, j);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzm(int i, int i2) throws IOException {
        this.zztz.zzm(i, i2);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zza(int i, boolean z) throws IOException {
        this.zztz.zza(i, z);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zza(int i, String str) throws IOException {
        this.zztz.zza(i, str);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zza(int i, zzgs zzgsVar) throws IOException {
        this.zztz.zza(i, zzgsVar);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzk(int i, int i2) throws IOException {
        this.zztz.zzk(i, i2);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzl(int i, int i2) throws IOException {
        this.zztz.zzl(i, i2);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzb(int i, long j) throws IOException {
        this.zztz.zzb(i, j);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zza(int i, Object obj, zzkc zzkcVar) throws IOException {
        this.zztz.zza(i, (zzjn) obj, zzkcVar);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzb(int i, Object obj, zzkc zzkcVar) throws IOException {
        zzhl zzhlVar = this.zztz;
        zzhlVar.writeTag(i, 3);
        zzkcVar.zza((zzjn) obj, zzhlVar.zzuo);
        zzhlVar.writeTag(i, 4);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzbq(int i) throws IOException {
        this.zztz.writeTag(i, 3);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzbr(int i) throws IOException {
        this.zztz.writeTag(i, 4);
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zza(int i, Object obj) throws IOException {
        if (obj instanceof zzgs) {
            this.zztz.zzb(i, (zzgs) obj);
        } else {
            this.zztz.zza(i, (zzjn) obj);
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zza(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zztz.writeTag(i, 2);
            int iZzbi = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzbi += zzhl.zzbi(list.get(i3).intValue());
            }
            this.zztz.zzbe(iZzbi);
            while (i2 < list.size()) {
                this.zztz.zzbd(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zzj(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzb(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zztz.writeTag(i, 2);
            int iZzbl = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzbl += zzhl.zzbl(list.get(i3).intValue());
            }
            this.zztz.zzbe(iZzbl);
            while (i2 < list.size()) {
                this.zztz.zzbg(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zzm(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzc(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zztz.writeTag(i, 2);
            int iZzv = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzv += zzhl.zzv(list.get(i3).longValue());
            }
            this.zztz.zzbe(iZzv);
            while (i2 < list.size()) {
                this.zztz.zzs(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zza(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzd(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zztz.writeTag(i, 2);
            int iZzw = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzw += zzhl.zzw(list.get(i3).longValue());
            }
            this.zztz.zzbe(iZzw);
            while (i2 < list.size()) {
                this.zztz.zzs(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zza(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zze(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zztz.writeTag(i, 2);
            int iZzy = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzy += zzhl.zzy(list.get(i3).longValue());
            }
            this.zztz.zzbe(iZzy);
            while (i2 < list.size()) {
                this.zztz.zzu(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zzc(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzf(int i, List<Float> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zztz.writeTag(i, 2);
            int iZzt = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzt += zzhl.zzt(list.get(i3).floatValue());
            }
            this.zztz.zzbe(iZzt);
            while (i2 < list.size()) {
                this.zztz.zzs(list.get(i2).floatValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zza(i, list.get(i2).floatValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzg(int i, List<Double> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zztz.writeTag(i, 2);
            int iZzb = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzb += zzhl.zzb(list.get(i3).doubleValue());
            }
            this.zztz.zzbe(iZzb);
            while (i2 < list.size()) {
                this.zztz.zza(list.get(i2).doubleValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zza(i, list.get(i2).doubleValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzh(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zztz.writeTag(i, 2);
            int iZzbn = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzbn += zzhl.zzbn(list.get(i3).intValue());
            }
            this.zztz.zzbe(iZzbn);
            while (i2 < list.size()) {
                this.zztz.zzbd(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zzj(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzi(int i, List<Boolean> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zztz.writeTag(i, 2);
            int iZzl = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzl += zzhl.zzl(list.get(i3).booleanValue());
            }
            this.zztz.zzbe(iZzl);
            while (i2 < list.size()) {
                this.zztz.zzk(list.get(i2).booleanValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zza(i, list.get(i2).booleanValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zza(int i, List<String> list) throws IOException {
        int i2 = 0;
        if (list instanceof zziu) {
            zziu zziuVar = (zziu) list;
            while (i2 < list.size()) {
                Object objZzbt = zziuVar.zzbt(i2);
                if (objZzbt instanceof String) {
                    this.zztz.zza(i, (String) objZzbt);
                } else {
                    this.zztz.zza(i, (zzgs) objZzbt);
                }
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zza(i, list.get(i2));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzb(int i, List<zzgs> list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zztz.zza(i, list.get(i2));
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzj(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zztz.writeTag(i, 2);
            int iZzbj = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzbj += zzhl.zzbj(list.get(i3).intValue());
            }
            this.zztz.zzbe(iZzbj);
            while (i2 < list.size()) {
                this.zztz.zzbe(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zzk(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzk(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zztz.writeTag(i, 2);
            int iZzbm = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzbm += zzhl.zzbm(list.get(i3).intValue());
            }
            this.zztz.zzbe(iZzbm);
            while (i2 < list.size()) {
                this.zztz.zzbg(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zzm(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzl(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zztz.writeTag(i, 2);
            int iZzz = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzz += zzhl.zzz(list.get(i3).longValue());
            }
            this.zztz.zzbe(iZzz);
            while (i2 < list.size()) {
                this.zztz.zzu(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zzc(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzm(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zztz.writeTag(i, 2);
            int iZzbk = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzbk += zzhl.zzbk(list.get(i3).intValue());
            }
            this.zztz.zzbe(iZzbk);
            while (i2 < list.size()) {
                this.zztz.zzbf(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zzl(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzn(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zztz.writeTag(i, 2);
            int iZzx = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzx += zzhl.zzx(list.get(i3).longValue());
            }
            this.zztz.zzbe(iZzx);
            while (i2 < list.size()) {
                this.zztz.zzt(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zztz.zzb(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zza(int i, List<?> list, zzkc zzkcVar) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zza(i, list.get(i2), zzkcVar);
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final void zzb(int i, List<?> list, zzkc zzkcVar) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzb(i, list.get(i2), zzkcVar);
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlr
    public final <K, V> void zza(int i, zzje<K, V> zzjeVar, Map<K, V> map) throws IOException {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.zztz.writeTag(i, 2);
            this.zztz.zzbe(zzjf.zza(zzjeVar, entry.getKey(), entry.getValue()));
            zzjf.zza(this.zztz, zzjeVar, entry.getKey(), entry.getValue());
        }
    }
}
