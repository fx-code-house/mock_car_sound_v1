package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.measurement.zzml;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzir extends zzg {
    private final zzjl zza;
    private zzei zzb;
    private volatile Boolean zzc;
    private final zzai zzd;
    private final zzke zze;
    private final List<Runnable> zzf;
    private final zzai zzg;

    protected zzir(zzfu zzfuVar) {
        super(zzfuVar);
        this.zzf = new ArrayList();
        this.zze = new zzke(zzfuVar.zzl());
        this.zza = new zzjl(this);
        this.zzd = new zziq(this, zzfuVar);
        this.zzg = new zzja(this, zzfuVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    protected final boolean zzy() {
        return false;
    }

    public final boolean zzaa() {
        zzc();
        zzv();
        return this.zzb != null;
    }

    protected final void zzab() {
        zzc();
        zzv();
        zza(new zzjc(this, zzb(true)));
    }

    protected final void zza(boolean z) {
        if (zzml.zzb() && zzs().zza(zzas.zzcg)) {
            zzc();
            zzv();
            if (z) {
                zzi().zzaa();
            }
            if (zzai()) {
                zza(new zzjf(this, zzb(false)));
            }
        }
    }

    final void zza(zzei zzeiVar, AbstractSafeParcelable abstractSafeParcelable, zzn zznVar) throws Throwable {
        int size;
        zzc();
        zzv();
        int i = 100;
        int i2 = 0;
        while (i2 < 1001 && i == 100) {
            ArrayList arrayList = new ArrayList();
            List<AbstractSafeParcelable> listZza = zzi().zza(100);
            if (listZza != null) {
                arrayList.addAll(listZza);
                size = listZza.size();
            } else {
                size = 0;
            }
            if (abstractSafeParcelable != null && size < 100) {
                arrayList.add(abstractSafeParcelable);
            }
            int size2 = arrayList.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj = arrayList.get(i3);
                i3++;
                AbstractSafeParcelable abstractSafeParcelable2 = (AbstractSafeParcelable) obj;
                if (abstractSafeParcelable2 instanceof zzaq) {
                    try {
                        zzeiVar.zza((zzaq) abstractSafeParcelable2, zznVar);
                    } catch (RemoteException e) {
                        zzq().zze().zza("Failed to send event to the service", e);
                    }
                } else if (abstractSafeParcelable2 instanceof zzku) {
                    try {
                        zzeiVar.zza((zzku) abstractSafeParcelable2, zznVar);
                    } catch (RemoteException e2) {
                        zzq().zze().zza("Failed to send user property to the service", e2);
                    }
                } else if (abstractSafeParcelable2 instanceof zzz) {
                    try {
                        zzeiVar.zza((zzz) abstractSafeParcelable2, zznVar);
                    } catch (RemoteException e3) {
                        zzq().zze().zza("Failed to send conditional user property to the service", e3);
                    }
                } else {
                    zzq().zze().zza("Discarding data. Unrecognized parcel type.");
                }
            }
            i2++;
            i = size;
        }
    }

    protected final void zza(zzaq zzaqVar, String str) {
        Preconditions.checkNotNull(zzaqVar);
        zzc();
        zzv();
        zza(new zzje(this, true, zzi().zza(zzaqVar), zzaqVar, zzb(true), str));
    }

    protected final void zza(zzz zzzVar) {
        Preconditions.checkNotNull(zzzVar);
        zzc();
        zzv();
        zza(new zzjh(this, true, zzi().zza(zzzVar), new zzz(zzzVar), zzb(true), zzzVar));
    }

    protected final void zza(AtomicReference<List<zzz>> atomicReference, String str, String str2, String str3) throws IllegalStateException {
        zzc();
        zzv();
        zza(new zzjg(this, atomicReference, str, str2, str3, zzb(false)));
    }

    protected final void zza(com.google.android.gms.internal.measurement.zzw zzwVar, String str, String str2) throws IllegalStateException {
        zzc();
        zzv();
        zza(new zzjj(this, str, str2, zzb(false), zzwVar));
    }

    protected final void zza(AtomicReference<List<zzku>> atomicReference, String str, String str2, String str3, boolean z) throws IllegalStateException {
        zzc();
        zzv();
        zza(new zzji(this, atomicReference, str, str2, str3, z, zzb(false)));
    }

    protected final void zza(com.google.android.gms.internal.measurement.zzw zzwVar, String str, String str2, boolean z) throws IllegalStateException {
        zzc();
        zzv();
        zza(new zzit(this, str, str2, z, zzb(false), zzwVar));
    }

    protected final void zza(zzku zzkuVar) {
        zzc();
        zzv();
        zza(new zzis(this, zzi().zza(zzkuVar), zzkuVar, zzb(true)));
    }

    protected final void zza(AtomicReference<List<zzku>> atomicReference, boolean z) throws IllegalStateException {
        zzc();
        zzv();
        zza(new zziv(this, atomicReference, zzb(false), z));
    }

    protected final void zzac() {
        zzc();
        zzv();
        zzn zznVarZzb = zzb(false);
        zzi().zzaa();
        zza(new zziu(this, zznVarZzb));
    }

    public final void zza(AtomicReference<String> atomicReference) {
        zzc();
        zzv();
        zza(new zzix(this, atomicReference, zzb(false)));
    }

    public final void zza(com.google.android.gms.internal.measurement.zzw zzwVar) throws IllegalStateException {
        zzc();
        zzv();
        zza(new zziw(this, zzb(false), zzwVar));
    }

    protected final void zzad() throws IllegalStateException {
        zzc();
        zzv();
        zzn zznVarZzb = zzb(true);
        zzi().zzab();
        zza(new zziz(this, zznVarZzb));
    }

    protected final void zza(zzij zzijVar) {
        zzc();
        zzv();
        zza(new zziy(this, zzijVar));
    }

    public final void zza(Bundle bundle) {
        zzc();
        zzv();
        zza(new zzjb(this, bundle, zzb(false)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzaj() {
        zzc();
        this.zze.zza();
        this.zzd.zza(zzas.zzai.zza(null).longValue());
    }

    final void zzae() {
        zzc();
        zzv();
        if (zzaa()) {
            return;
        }
        if (zzak()) {
            this.zza.zzb();
            return;
        }
        if (zzs().zzy()) {
            return;
        }
        List<ResolveInfo> listQueryIntentServices = zzm().getPackageManager().queryIntentServices(new Intent().setClassName(zzm(), "com.google.android.gms.measurement.AppMeasurementService"), 65536);
        if (listQueryIntentServices != null && listQueryIntentServices.size() > 0) {
            Intent intent = new Intent("com.google.android.gms.measurement.START");
            intent.setComponent(new ComponentName(zzm(), "com.google.android.gms.measurement.AppMeasurementService"));
            this.zza.zza(intent);
            return;
        }
        zzq().zze().zza("Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest");
    }

    final Boolean zzaf() {
        return this.zzc;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final boolean zzak() throws java.lang.IllegalStateException {
        /*
            Method dump skipped, instructions count: 265
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzir.zzak():boolean");
    }

    protected final void zza(zzei zzeiVar) throws IllegalStateException {
        zzc();
        Preconditions.checkNotNull(zzeiVar);
        this.zzb = zzeiVar;
        zzaj();
        zzam();
    }

    public final void zzag() {
        zzc();
        zzv();
        this.zza.zza();
        try {
            ConnectionTracker.getInstance().unbindService(zzm(), this.zza);
        } catch (IllegalArgumentException | IllegalStateException unused) {
        }
        this.zzb = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(ComponentName componentName) throws IllegalStateException {
        zzc();
        if (this.zzb != null) {
            this.zzb = null;
            zzq().zzw().zza("Disconnected from device MeasurementService", componentName);
            zzc();
            zzae();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzal() throws IllegalStateException {
        zzc();
        if (zzaa()) {
            zzq().zzw().zza("Inactivity, disconnecting from the service");
            zzag();
        }
    }

    private final void zza(Runnable runnable) throws IllegalStateException {
        zzc();
        if (zzaa()) {
            runnable.run();
        } else {
            if (this.zzf.size() >= 1000) {
                zzq().zze().zza("Discarding data. Max runnable queue size reached");
                return;
            }
            this.zzf.add(runnable);
            this.zzg.zza(60000L);
            zzae();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzam() throws IllegalStateException {
        zzc();
        zzq().zzw().zza("Processing queued up service tasks", Integer.valueOf(this.zzf.size()));
        Iterator<Runnable> it = this.zzf.iterator();
        while (it.hasNext()) {
            try {
                it.next().run();
            } catch (Exception e) {
                zzq().zze().zza("Task exception while flushing queue", e);
            }
        }
        this.zzf.clear();
        this.zzg.zzc();
    }

    private final zzn zzb(boolean z) {
        return zzf().zza(z ? zzq().zzx() : null);
    }

    public final void zza(com.google.android.gms.internal.measurement.zzw zzwVar, zzaq zzaqVar, String str) throws IllegalStateException {
        zzc();
        zzv();
        if (zzo().zza(12451000) != 0) {
            zzq().zzh().zza("Not bundling data. Service unavailable or out of date");
            zzo().zza(zzwVar, new byte[0]);
        } else {
            zza(new zzjd(this, zzaqVar, str, zzwVar));
        }
    }

    final boolean zzah() {
        zzc();
        zzv();
        return !zzak() || zzo().zzi() >= 200900;
    }

    final boolean zzai() {
        zzc();
        zzv();
        if (zzs().zza(zzas.zzci)) {
            return !zzak() || zzo().zzi() >= zzas.zzcj.zza(null).intValue();
        }
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzd, com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zza zzd() {
        return super.zzd();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzhb zze() {
        return super.zze();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzen zzf() {
        return super.zzf();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzir zzg() {
        return super.zzg();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzii zzh() {
        return super.zzh();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzem zzi() {
        return super.zzi();
    }

    @Override // com.google.android.gms.measurement.internal.zzd
    public final /* bridge */ /* synthetic */ zzjx zzj() {
        return super.zzj();
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

    static /* synthetic */ zzei zza(zzir zzirVar, zzei zzeiVar) {
        zzirVar.zzb = null;
        return null;
    }
}
