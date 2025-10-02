package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzfg {
    private final String zza;
    private final long zzb;
    private boolean zzc;
    private long zzd;
    private final /* synthetic */ zzfc zze;

    public zzfg(zzfc zzfcVar, String str, long j) {
        this.zze = zzfcVar;
        Preconditions.checkNotEmpty(str);
        this.zza = str;
        this.zzb = j;
    }

    public final long zza() {
        if (!this.zzc) {
            this.zzc = true;
            this.zzd = this.zze.zzf().getLong(this.zza, this.zzb);
        }
        return this.zzd;
    }

    public final void zza(long j) {
        SharedPreferences.Editor editorEdit = this.zze.zzf().edit();
        editorEdit.putLong(this.zza, j);
        editorEdit.apply();
        this.zzd = j;
    }
}
