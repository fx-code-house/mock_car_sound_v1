package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzfe {
    private final String zza;
    private final boolean zzb;
    private boolean zzc;
    private boolean zzd;
    private final /* synthetic */ zzfc zze;

    public zzfe(zzfc zzfcVar, String str, boolean z) {
        this.zze = zzfcVar;
        Preconditions.checkNotEmpty(str);
        this.zza = str;
        this.zzb = z;
    }

    public final boolean zza() {
        if (!this.zzc) {
            this.zzc = true;
            this.zzd = this.zze.zzf().getBoolean(this.zza, this.zzb);
        }
        return this.zzd;
    }

    public final void zza(boolean z) {
        SharedPreferences.Editor editorEdit = this.zze.zzf().edit();
        editorEdit.putBoolean(this.zza, z);
        editorEdit.apply();
        this.zzd = z;
    }
}
