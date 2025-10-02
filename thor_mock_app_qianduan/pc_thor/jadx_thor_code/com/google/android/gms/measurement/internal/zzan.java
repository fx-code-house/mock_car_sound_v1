package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzan {
    final String zza;
    final String zzb;
    final long zzc;
    final long zzd;
    final zzap zze;
    private final String zzf;

    private zzan(zzfu zzfuVar, String str, String str2, String str3, long j, long j2, zzap zzapVar) throws IllegalStateException {
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotEmpty(str3);
        Preconditions.checkNotNull(zzapVar);
        this.zza = str2;
        this.zzb = str3;
        this.zzf = TextUtils.isEmpty(str) ? null : str;
        this.zzc = j;
        this.zzd = j2;
        if (j2 != 0 && j2 > j) {
            zzfuVar.zzq().zzh().zza("Event created with reverse previous/current timestamps. appId, name", zzeq.zza(str2), zzeq.zza(str3));
        }
        this.zze = zzapVar;
    }

    zzan(zzfu zzfuVar, String str, String str2, String str3, long j, long j2, Bundle bundle) throws IllegalStateException {
        zzap zzapVar;
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotEmpty(str3);
        this.zza = str2;
        this.zzb = str3;
        this.zzf = TextUtils.isEmpty(str) ? null : str;
        this.zzc = j;
        this.zzd = j2;
        if (j2 != 0 && j2 > j) {
            zzfuVar.zzq().zzh().zza("Event created with reverse previous/current timestamps. appId", zzeq.zza(str2));
        }
        if (bundle != null && !bundle.isEmpty()) {
            Bundle bundle2 = new Bundle(bundle);
            Iterator<String> it = bundle2.keySet().iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (next == null) {
                    zzfuVar.zzq().zze().zza("Param name can't be null");
                    it.remove();
                } else {
                    Object objZza = zzfuVar.zzh().zza(next, bundle2.get(next));
                    if (objZza == null) {
                        zzfuVar.zzq().zzh().zza("Param value can't be null", zzfuVar.zzi().zzb(next));
                        it.remove();
                    } else {
                        zzfuVar.zzh().zza(bundle2, next, objZza);
                    }
                }
            }
            zzapVar = new zzap(bundle2);
        } else {
            zzapVar = new zzap(new Bundle());
        }
        this.zze = zzapVar;
    }

    final zzan zza(zzfu zzfuVar, long j) {
        return new zzan(zzfuVar, this.zzf, this.zza, this.zzb, this.zzc, j, this.zze);
    }

    public final String toString() {
        String str = this.zza;
        String str2 = this.zzb;
        String strValueOf = String.valueOf(this.zze);
        return new StringBuilder(String.valueOf(str).length() + 33 + String.valueOf(str2).length() + String.valueOf(strValueOf).length()).append("Event{appId='").append(str).append("', name='").append(str2).append("', params=").append(strValueOf).append('}').toString();
    }
}
