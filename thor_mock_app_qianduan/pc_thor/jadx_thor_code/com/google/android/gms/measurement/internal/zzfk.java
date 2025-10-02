package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzfk implements ServiceConnection {
    final /* synthetic */ zzfl zza;
    private final String zzb;

    zzfk(zzfl zzflVar, String str) {
        this.zza = zzflVar;
        this.zzb = str;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) throws IllegalStateException {
        if (iBinder == null) {
            this.zza.zza.zzq().zzh().zza("Install Referrer connection returned with null binder");
            return;
        }
        try {
            com.google.android.gms.internal.measurement.zzd zzdVarZza = com.google.android.gms.internal.measurement.zzg.zza(iBinder);
            if (zzdVarZza == null) {
                this.zza.zza.zzq().zzh().zza("Install Referrer Service implementation was not found");
            } else {
                this.zza.zza.zzq().zzw().zza("Install Referrer Service connected");
                this.zza.zza.zzp().zza(new zzfn(this, zzdVarZza, this));
            }
        } catch (Exception e) {
            this.zza.zza.zzq().zzh().zza("Exception occurred while calling Install Referrer API", e);
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) throws IllegalStateException {
        this.zza.zza.zzq().zzw().zza("Install Referrer Service disconnected");
    }
}
