package com.google.android.gms.measurement.internal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.util.Clock;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzkh extends zzki {
    private final AlarmManager zzb;
    private final zzai zzc;
    private Integer zzd;

    protected zzkh(zzkl zzklVar) {
        super(zzklVar);
        this.zzb = (AlarmManager) zzm().getSystemService(NotificationCompat.CATEGORY_ALARM);
        this.zzc = new zzkg(this, zzklVar.zzu(), zzklVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzki
    protected final boolean zzd() {
        this.zzb.cancel(zzw());
        zzu();
        return false;
    }

    private final void zzu() {
        ((JobScheduler) zzm().getSystemService("jobscheduler")).cancel(zzv());
    }

    public final void zza(long j) {
        zzaj();
        Context contextZzm = zzm();
        if (!zzfm.zza(contextZzm)) {
            zzq().zzv().zza("Receiver not registered/enabled");
        }
        if (!zzkv.zza(contextZzm, false)) {
            zzq().zzv().zza("Service not registered/enabled");
        }
        zze();
        zzq().zzw().zza("Scheduling upload, millis", Long.valueOf(j));
        zzl().elapsedRealtime();
        if (j < Math.max(0L, zzas.zzw.zza(null).longValue()) && !this.zzc.zzb()) {
            this.zzc.zza(j);
        }
        Context contextZzm2 = zzm();
        ComponentName componentName = new ComponentName(contextZzm2, "com.google.android.gms.measurement.AppMeasurementJobService");
        int iZzv = zzv();
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString("action", "com.google.android.gms.measurement.UPLOAD");
        com.google.android.gms.internal.measurement.zzh.zza(contextZzm2, new JobInfo.Builder(iZzv, componentName).setMinimumLatency(j).setOverrideDeadline(j << 1).setExtras(persistableBundle).build(), "com.google.android.gms", "UploadAlarm");
    }

    private final int zzv() {
        if (this.zzd == null) {
            String strValueOf = String.valueOf(zzm().getPackageName());
            this.zzd = Integer.valueOf((strValueOf.length() != 0 ? "measurement".concat(strValueOf) : new String("measurement")).hashCode());
        }
        return this.zzd.intValue();
    }

    public final void zze() {
        zzaj();
        zzq().zzw().zza("Unscheduling upload");
        this.zzb.cancel(zzw());
        this.zzc.zzc();
        zzu();
    }

    private final PendingIntent zzw() {
        Context contextZzm = zzm();
        return PendingIntent.getBroadcast(contextZzm, 0, new Intent().setClassName(contextZzm, "com.google.android.gms.measurement.AppMeasurementReceiver").setAction("com.google.android.gms.measurement.UPLOAD"), 0);
    }

    @Override // com.google.android.gms.measurement.internal.zzkj
    public final /* bridge */ /* synthetic */ zzjr zzf() {
        return super.zzf();
    }

    @Override // com.google.android.gms.measurement.internal.zzkj
    public final /* bridge */ /* synthetic */ zzkr f_() {
        return super.f_();
    }

    @Override // com.google.android.gms.measurement.internal.zzkj
    public final /* bridge */ /* synthetic */ zzr zzh() {
        return super.zzh();
    }

    @Override // com.google.android.gms.measurement.internal.zzkj
    public final /* bridge */ /* synthetic */ zzaf zzi() {
        return super.zzi();
    }

    @Override // com.google.android.gms.measurement.internal.zzkj
    public final /* bridge */ /* synthetic */ zzfo zzj() {
        return super.zzj();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzak zzk() {
        return super.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ Clock zzl() {
        return super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ Context zzm() {
        return super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzeo zzn() {
        return super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzkv zzo() {
        return super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ zzfr zzp() {
        return super.zzp();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ zzeq zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzfc zzr() {
        return super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzab zzs() {
        return super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ zzw zzt() {
        return super.zzt();
    }
}
