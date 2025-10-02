package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import java.lang.reflect.InvocationTargetException;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzkb implements Runnable {
    long zza;
    long zzb;
    final /* synthetic */ zzjy zzc;

    zzkb(zzjy zzjyVar, long j, long j2) {
        this.zzc = zzjyVar;
        this.zza = j;
        this.zzb = j2;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zzc.zza.zzp().zza(new Runnable(this) { // from class: com.google.android.gms.measurement.internal.zzka
            private final zzkb zza;

            {
                this.zza = this;
            }

            @Override // java.lang.Runnable
            public final void run() throws IllegalStateException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
                zzkb zzkbVar = this.zza;
                zzjy zzjyVar = zzkbVar.zzc;
                long j = zzkbVar.zza;
                long j2 = zzkbVar.zzb;
                zzjyVar.zza.zzc();
                zzjyVar.zza.zzq().zzv().zza("Application going to the background");
                boolean z = true;
                if (zzjyVar.zza.zzs().zza(zzas.zzbu)) {
                    zzjyVar.zza.zzr().zzr.zza(true);
                }
                Bundle bundle = new Bundle();
                if (!zzjyVar.zza.zzs().zzh().booleanValue()) {
                    zzjyVar.zza.zzb.zzb(j2);
                    if (zzjyVar.zza.zzs().zza(zzas.zzbl)) {
                        bundle.putLong("_et", zzjyVar.zza.zza(j2));
                        zzii.zza(zzjyVar.zza.zzh().zza(true), bundle, true);
                    } else {
                        z = false;
                    }
                    zzjyVar.zza.zza(false, z, j2);
                }
                zzjyVar.zza.zze().zza("auto", "_ab", j, bundle);
            }
        });
    }
}
