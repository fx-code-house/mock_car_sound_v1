package com.google.android.gms.measurement.internal;

import android.os.Bundle;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzeu {
    public String zza;
    public Bundle zzb;
    private String zzc;
    private long zzd;

    private zzeu(String str, String str2, Bundle bundle, long j) {
        this.zza = str;
        this.zzc = str2;
        this.zzb = bundle == null ? new Bundle() : bundle;
        this.zzd = j;
    }

    public static zzeu zza(zzaq zzaqVar) {
        return new zzeu(zzaqVar.zza, zzaqVar.zzc, zzaqVar.zzb.zzb(), zzaqVar.zzd);
    }

    public final zzaq zza() {
        return new zzaq(this.zza, new zzap(new Bundle(this.zzb)), this.zzc, this.zzd);
    }

    public final String toString() {
        String str = this.zzc;
        String str2 = this.zza;
        String strValueOf = String.valueOf(this.zzb);
        return new StringBuilder(String.valueOf(str).length() + 21 + String.valueOf(str2).length() + String.valueOf(strValueOf).length()).append("origin=").append(str).append(",name=").append(str2).append(",params=").append(strValueOf).toString();
    }
}
