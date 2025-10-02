package com.google.android.gms.measurement.internal;

import androidx.collection.ArrayMap;
import com.google.android.gms.internal.measurement.zzcd;
import com.google.android.gms.internal.measurement.zzmx;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzt {
    private String zza;
    private boolean zzb;
    private zzcd.zzi zzc;
    private BitSet zzd;
    private BitSet zze;
    private Map<Integer, Long> zzf;
    private Map<Integer, List<Long>> zzg;
    private final /* synthetic */ zzr zzh;

    private zzt(zzr zzrVar, String str) {
        this.zzh = zzrVar;
        this.zza = str;
        this.zzb = true;
        this.zzd = new BitSet();
        this.zze = new BitSet();
        this.zzf = new ArrayMap();
        this.zzg = new ArrayMap();
    }

    private zzt(zzr zzrVar, String str, zzcd.zzi zziVar, BitSet bitSet, BitSet bitSet2, Map<Integer, Long> map, Map<Integer, Long> map2) {
        this.zzh = zzrVar;
        this.zza = str;
        this.zzd = bitSet;
        this.zze = bitSet2;
        this.zzf = map;
        this.zzg = new ArrayMap();
        if (map2 != null) {
            for (Integer num : map2.keySet()) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(map2.get(num));
                this.zzg.put(num, arrayList);
            }
        }
        this.zzb = false;
        this.zzc = zziVar;
    }

    final void zza(zzu zzuVar) {
        int iZza = zzuVar.zza();
        if (zzuVar.zzc != null) {
            this.zze.set(iZza, zzuVar.zzc.booleanValue());
        }
        if (zzuVar.zzd != null) {
            this.zzd.set(iZza, zzuVar.zzd.booleanValue());
        }
        if (zzuVar.zze != null) {
            Long l = this.zzf.get(Integer.valueOf(iZza));
            long jLongValue = zzuVar.zze.longValue() / 1000;
            if (l == null || jLongValue > l.longValue()) {
                this.zzf.put(Integer.valueOf(iZza), Long.valueOf(jLongValue));
            }
        }
        if (zzuVar.zzf != null) {
            List<Long> arrayList = this.zzg.get(Integer.valueOf(iZza));
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.zzg.put(Integer.valueOf(iZza), arrayList);
            }
            if (zzuVar.zzb()) {
                arrayList.clear();
            }
            if (zzmx.zzb() && this.zzh.zzs().zzd(this.zza, zzas.zzbb) && zzuVar.zzc()) {
                arrayList.clear();
            }
            if (zzmx.zzb() && this.zzh.zzs().zzd(this.zza, zzas.zzbb)) {
                long jLongValue2 = zzuVar.zzf.longValue() / 1000;
                if (arrayList.contains(Long.valueOf(jLongValue2))) {
                    return;
                }
                arrayList.add(Long.valueOf(jLongValue2));
                return;
            }
            arrayList.add(Long.valueOf(zzuVar.zzf.longValue() / 1000));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.measurement.zzcd$zza$zza, com.google.android.gms.internal.measurement.zzhy$zzb] */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.lang.Iterable] */
    /* JADX WARN: Type inference failed for: r8v5, types: [com.google.android.gms.internal.measurement.zzcd$zzi$zza] */
    final zzcd.zza zza(int i) {
        ArrayList arrayList;
        ?? arrayList2;
        ?? Zzh = zzcd.zza.zzh();
        Zzh.zza(i);
        Zzh.zza(this.zzb);
        zzcd.zzi zziVar = this.zzc;
        if (zziVar != null) {
            Zzh.zza(zziVar);
        }
        ?? Zza = zzcd.zzi.zzi().zzb(zzkr.zza(this.zzd)).zza(zzkr.zza(this.zze));
        if (this.zzf == null) {
            arrayList = null;
        } else {
            arrayList = new ArrayList(this.zzf.size());
            Iterator<Integer> it = this.zzf.keySet().iterator();
            while (it.hasNext()) {
                int iIntValue = it.next().intValue();
                arrayList.add((zzcd.zzb) ((com.google.android.gms.internal.measurement.zzhy) zzcd.zzb.zze().zza(iIntValue).zza(this.zzf.get(Integer.valueOf(iIntValue)).longValue()).zzy()));
            }
        }
        Zza.zzc(arrayList);
        if (this.zzg == null) {
            arrayList2 = Collections.emptyList();
        } else {
            arrayList2 = new ArrayList(this.zzg.size());
            for (Integer num : this.zzg.keySet()) {
                zzcd.zzj.zza zzaVarZza = zzcd.zzj.zze().zza(num.intValue());
                List<Long> list = this.zzg.get(num);
                if (list != null) {
                    Collections.sort(list);
                    zzaVarZza.zza(list);
                }
                arrayList2.add((zzcd.zzj) ((com.google.android.gms.internal.measurement.zzhy) zzaVarZza.zzy()));
            }
        }
        Zza.zzd(arrayList2);
        Zzh.zza(Zza);
        return (zzcd.zza) ((com.google.android.gms.internal.measurement.zzhy) Zzh.zzy());
    }

    /* synthetic */ zzt(zzr zzrVar, String str, zzcd.zzi zziVar, BitSet bitSet, BitSet bitSet2, Map map, Map map2, zzq zzqVar) {
        this(zzrVar, str, zziVar, bitSet, bitSet2, map, map2);
    }

    /* synthetic */ zzt(zzr zzrVar, String str, zzq zzqVar) {
        this(zzrVar, str);
    }
}
