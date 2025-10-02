package com.google.android.gms.measurement.internal;

import java.util.Map;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzr extends zzki {
    private String zzb;
    private Set<Integer> zzc;
    private Map<Integer, zzt> zzd;
    private Long zze;
    private Long zzf;

    zzr(zzkl zzklVar) {
        super(zzklVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzki
    protected final boolean zzd() {
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:215:0x061c, code lost:
    
        r7 = zzq().zzh();
        r9 = com.google.android.gms.measurement.internal.zzeq.zza(r46.zzb);
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x062e, code lost:
    
        if (r8.zza() == false) goto L218;
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x0630, code lost:
    
        r8 = java.lang.Integer.valueOf(r8.zzb());
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x0639, code lost:
    
        r8 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x063a, code lost:
    
        r7.zza("Invalid property filter ID. appId, id", r9, java.lang.String.valueOf(r8));
        r8 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:112:0x02cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final java.util.List<com.google.android.gms.internal.measurement.zzcd.zza> zza(java.lang.String r47, java.util.List<com.google.android.gms.internal.measurement.zzcd.zzc> r48, java.util.List<com.google.android.gms.internal.measurement.zzcd.zzk> r49, java.lang.Long r50, java.lang.Long r51) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1782
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzr.zza(java.lang.String, java.util.List, java.util.List, java.lang.Long, java.lang.Long):java.util.List");
    }

    private final zzt zza(int i) {
        if (this.zzd.containsKey(Integer.valueOf(i))) {
            return this.zzd.get(Integer.valueOf(i));
        }
        zzt zztVar = new zzt(this, this.zzb, null);
        this.zzd.put(Integer.valueOf(i), zztVar);
        return zztVar;
    }

    private final boolean zza(int i, int i2) {
        if (this.zzd.get(Integer.valueOf(i)) == null) {
            return false;
        }
        return this.zzd.get(Integer.valueOf(i)).zzd.get(i2);
    }
}
