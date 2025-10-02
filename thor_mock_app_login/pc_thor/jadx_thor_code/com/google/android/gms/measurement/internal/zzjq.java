package com.google.android.gms.measurement.internal;

import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.internal.zzju;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzjq<T extends Context & zzju> {
    private final T zza;

    public zzjq(T t) {
        Preconditions.checkNotNull(t);
        this.zza = t;
    }

    public final void zza() throws IllegalStateException {
        zzfu.zza(this.zza, null, null).zzq().zzw().zza("Local AppMeasurementService is starting up");
    }

    public final void zzb() throws IllegalStateException {
        zzfu.zza(this.zza, null, null).zzq().zzw().zza("Local AppMeasurementService is shutting down");
    }

    public final int zza(final Intent intent, int i, final int i2) throws IllegalStateException {
        final zzeq zzeqVarZzq = zzfu.zza(this.zza, null, null).zzq();
        if (intent == null) {
            zzeqVarZzq.zzh().zza("AppMeasurementService started with null intent");
            return 2;
        }
        String action = intent.getAction();
        zzeqVarZzq.zzw().zza("Local AppMeasurementService called. startId, action", Integer.valueOf(i2), action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            zza(new Runnable(this, i2, zzeqVarZzq, intent) { // from class: com.google.android.gms.measurement.internal.zzjt
                private final zzjq zza;
                private final int zzb;
                private final zzeq zzc;
                private final Intent zzd;

                {
                    this.zza = this;
                    this.zzb = i2;
                    this.zzc = zzeqVarZzq;
                    this.zzd = intent;
                }

                @Override // java.lang.Runnable
                public final void run() throws IllegalStateException {
                    this.zza.zza(this.zzb, this.zzc, this.zzd);
                }
            });
        }
        return 2;
    }

    private final void zza(Runnable runnable) throws IllegalStateException {
        zzkl zzklVarZza = zzkl.zza(this.zza);
        zzklVarZza.zzp().zza(new zzjv(this, zzklVarZza, runnable));
    }

    public final IBinder zza(Intent intent) throws IllegalStateException {
        if (intent == null) {
            zzc().zze().zza("onBind called with null intent");
            return null;
        }
        String action = intent.getAction();
        if ("com.google.android.gms.measurement.START".equals(action)) {
            return new zzfz(zzkl.zza(this.zza));
        }
        zzc().zzh().zza("onBind received unknown action", action);
        return null;
    }

    public final boolean zzb(Intent intent) throws IllegalStateException {
        if (intent == null) {
            zzc().zze().zza("onUnbind called with null intent");
            return true;
        }
        zzc().zzw().zza("onUnbind called for intent. action", intent.getAction());
        return true;
    }

    public final boolean zza(final JobParameters jobParameters) throws IllegalStateException {
        final zzeq zzeqVarZzq = zzfu.zza(this.zza, null, null).zzq();
        String string = jobParameters.getExtras().getString("action");
        zzeqVarZzq.zzw().zza("Local AppMeasurementJobService called. action", string);
        if (!"com.google.android.gms.measurement.UPLOAD".equals(string)) {
            return true;
        }
        zza(new Runnable(this, zzeqVarZzq, jobParameters) { // from class: com.google.android.gms.measurement.internal.zzjs
            private final zzjq zza;
            private final zzeq zzb;
            private final JobParameters zzc;

            {
                this.zza = this;
                this.zzb = zzeqVarZzq;
                this.zzc = jobParameters;
            }

            @Override // java.lang.Runnable
            public final void run() throws IllegalStateException {
                this.zza.zza(this.zzb, this.zzc);
            }
        });
        return true;
    }

    public final void zzc(Intent intent) throws IllegalStateException {
        if (intent == null) {
            zzc().zze().zza("onRebind called with null intent");
        } else {
            zzc().zzw().zza("onRebind called. action", intent.getAction());
        }
    }

    private final zzeq zzc() {
        return zzfu.zza(this.zza, null, null).zzq();
    }

    final /* synthetic */ void zza(zzeq zzeqVar, JobParameters jobParameters) throws IllegalStateException {
        zzeqVar.zzw().zza("AppMeasurementJobService processed last upload request.");
        this.zza.zza(jobParameters, false);
    }

    final /* synthetic */ void zza(int i, zzeq zzeqVar, Intent intent) throws IllegalStateException {
        if (this.zza.zza(i)) {
            zzeqVar.zzw().zza("Local AppMeasurementService processed last upload request. StartId", Integer.valueOf(i));
            zzc().zzw().zza("Completed wakeful intent.");
            this.zza.zza(intent);
        }
    }
}
