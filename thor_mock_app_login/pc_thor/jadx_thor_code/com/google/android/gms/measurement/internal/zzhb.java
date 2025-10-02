package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.google.android.exoplayer2.C;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.measurement.zzlo;
import com.google.android.gms.internal.measurement.zzml;
import com.google.android.gms.internal.measurement.zzmy;
import com.google.android.gms.internal.measurement.zznj;
import com.google.android.gms.internal.measurement.zznw;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.BooleanUtils;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzhb extends zzg {
    protected zzhy zza;
    final zzo zzb;
    private zzgw zzc;
    private final Set<zzgz> zzd;
    private boolean zze;
    private final AtomicReference<String> zzf;
    private final Object zzg;
    private zzac zzh;
    private int zzi;
    private final AtomicLong zzj;
    private long zzk;
    private int zzl;
    private boolean zzm;
    private final zzky zzn;

    protected zzhb(zzfu zzfuVar) {
        super(zzfuVar);
        this.zzd = new CopyOnWriteArraySet();
        this.zzg = new Object();
        this.zzm = true;
        this.zzn = new zzhq(this);
        this.zzf = new AtomicReference<>();
        this.zzh = new zzac(null, null);
        this.zzi = 100;
        this.zzk = -1L;
        this.zzl = 100;
        this.zzj = new AtomicLong(0L);
        this.zzb = new zzo(zzfuVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    protected final boolean zzy() {
        return false;
    }

    public final void zzaa() {
        if (zzm().getApplicationContext() instanceof Application) {
            ((Application) zzm().getApplicationContext()).unregisterActivityLifecycleCallbacks(this.zza);
        }
    }

    public final Boolean zzab() {
        AtomicReference atomicReference = new AtomicReference();
        return (Boolean) zzp().zza(atomicReference, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS, "boolean test flag value", new zzhc(this, atomicReference));
    }

    public final String zzac() {
        AtomicReference atomicReference = new AtomicReference();
        return (String) zzp().zza(atomicReference, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS, "String test flag value", new zzhm(this, atomicReference));
    }

    public final Long zzad() {
        AtomicReference atomicReference = new AtomicReference();
        return (Long) zzp().zza(atomicReference, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS, "long test flag value", new zzht(this, atomicReference));
    }

    public final Integer zzae() {
        AtomicReference atomicReference = new AtomicReference();
        return (Integer) zzp().zza(atomicReference, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS, "int test flag value", new zzhs(this, atomicReference));
    }

    public final Double zzaf() {
        AtomicReference atomicReference = new AtomicReference();
        return (Double) zzp().zza(atomicReference, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS, "double test flag value", new zzhv(this, atomicReference));
    }

    public final void zza(Boolean bool) throws IllegalStateException {
        zzv();
        zzp().zza(new zzhu(this, bool));
    }

    public final void zza(Bundle bundle, int i, long j) throws IllegalStateException {
        if (zzml.zzb() && zzs().zza(zzas.zzcg)) {
            zzv();
            String strZza = zzac.zza(bundle);
            if (strZza != null) {
                zzq().zzj().zza("Ignoring invalid consent setting", strZza);
                zzq().zzj().zza("Valid consent values are 'granted', 'denied'");
            }
            zza(zzac.zzb(bundle), i, j);
        }
    }

    public final void zza(zzac zzacVar, int i, long j) {
        boolean z;
        zzac zzacVar2;
        boolean z2;
        boolean z3;
        if (zzml.zzb() && zzs().zza(zzas.zzcg)) {
            zzv();
            if ((!zzs().zza(zzas.zzch) || i != 20) && zzacVar.zzb() == null && zzacVar.zzd() == null) {
                zzq().zzj().zza("Discarding empty consent settings");
                return;
            }
            synchronized (this.zzg) {
                z = false;
                if (zzac.zza(i, this.zzi)) {
                    boolean zZza = zzacVar.zza(this.zzh);
                    if (zzacVar.zze() && !this.zzh.zze()) {
                        z = true;
                    }
                    zzac zzacVarZzc = zzacVar.zzc(this.zzh);
                    this.zzh = zzacVarZzc;
                    this.zzi = i;
                    z3 = z;
                    z = true;
                    zzacVar2 = zzacVarZzc;
                    z2 = zZza;
                } else {
                    zzacVar2 = zzacVar;
                    z2 = false;
                    z3 = false;
                }
            }
            if (!z) {
                zzq().zzu().zza("Ignoring lower-priority consent settings, proposed settings", zzacVar2);
                return;
            }
            long andIncrement = this.zzj.getAndIncrement();
            if (z2) {
                zza((String) null);
                zzp().zzb(new zzhx(this, zzacVar2, j, i, andIncrement, z3));
            } else if (zzs().zza(zzas.zzch) && (i == 40 || i == 20)) {
                zzp().zzb(new zzhw(this, zzacVar2, i, andIncrement, z3));
            } else {
                zzp().zza(new zzhz(this, zzacVar2, i, andIncrement, z3));
            }
        }
    }

    final void zza(zzac zzacVar) {
        zzc();
        boolean z = (zzacVar.zze() && zzacVar.zzc()) || zzg().zzai();
        if (z != this.zzy.zzac()) {
            this.zzy.zzb(z);
            Boolean boolZzv = zzr().zzv();
            if (!z || boolZzv == null || boolZzv.booleanValue()) {
                zza(Boolean.valueOf(z), false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(Boolean bool, boolean z) throws IllegalStateException {
        zzc();
        zzv();
        zzq().zzv().zza("Setting app measurement enabled (FE)", bool);
        zzr().zza(bool);
        if (zzml.zzb() && zzs().zza(zzas.zzcg) && z) {
            zzr().zzb(bool);
        }
        if (zzml.zzb() && zzs().zza(zzas.zzcg) && !this.zzy.zzac() && bool.booleanValue()) {
            return;
        }
        zzal();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzac zzacVar, int i, long j, boolean z, boolean z2) throws IllegalStateException {
        zzc();
        zzv();
        if (j <= this.zzk && zzac.zza(this.zzl, i)) {
            zzq().zzu().zza("Dropped out-of-date consent setting, proposed settings", zzacVar);
            return;
        }
        if (zzr().zza(zzacVar, i)) {
            this.zzk = j;
            this.zzl = i;
            zzg().zza(z);
            if (z2) {
                zzg().zza(new AtomicReference<>());
                return;
            }
            return;
        }
        zzq().zzu().zza("Lower precedence consent source ignored, proposed source", Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzal() throws IllegalStateException {
        zzc();
        String strZza = zzr().zzn.zza();
        if (strZza != null) {
            if ("unset".equals(strZza)) {
                zza("app", "_npa", (Object) null, zzl().currentTimeMillis());
            } else {
                zza("app", "_npa", Long.valueOf(BooleanUtils.TRUE.equals(strZza) ? 1L : 0L), zzl().currentTimeMillis());
            }
        }
        if (this.zzy.zzaa() && this.zzm) {
            zzq().zzv().zza("Recording app launch after enabling measurement for the first time (FE)");
            zzah();
            if (zznj.zzb() && zzs().zza(zzas.zzbp)) {
                zzj().zza.zza();
            }
            if (zzmy.zzb() && zzs().zza(zzas.zzbs)) {
                if (!(this.zzy.zze().zza.zzb().zzi.zza() > 0)) {
                    zzfl zzflVarZze = this.zzy.zze();
                    zzflVarZze.zza(zzflVarZze.zza.zzm().getPackageName());
                }
            }
            if (zzs().zza(zzas.zzcc)) {
                zzp().zza(new zzhe(this));
                return;
            }
            return;
        }
        zzq().zzv().zza("Updating Scion state (FE)");
        zzg().zzab();
    }

    public final void zza(String str, String str2, Bundle bundle) {
        zza(str, str2, bundle, true, true, zzl().currentTimeMillis());
    }

    final void zzb(String str, String str2, Bundle bundle) throws IllegalStateException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        zzc();
        zza(str, str2, zzl().currentTimeMillis(), bundle);
    }

    final void zza(String str, String str2, long j, Bundle bundle) throws IllegalStateException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        zzc();
        zza(str, str2, j, bundle, true, this.zzc == null || zzkv.zzd(str2), false, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0152  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected final void zza(java.lang.String r20, java.lang.String r21, long r22, android.os.Bundle r24, boolean r25, boolean r26, boolean r27, java.lang.String r28) throws java.lang.IllegalStateException, java.lang.IllegalAccessException, java.lang.ClassNotFoundException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 1209
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzhb.zza(java.lang.String, java.lang.String, long, android.os.Bundle, boolean, boolean, boolean, java.lang.String):void");
    }

    public final void zza(String str, String str2, Bundle bundle, String str3) throws IllegalStateException {
        zza();
        zzb(str, str2, zzl().currentTimeMillis(), bundle, false, true, false, str3);
    }

    public final void zza(String str, String str2, Bundle bundle, boolean z, boolean z2, long j) throws IllegalStateException {
        String str3 = str == null ? "app" : str;
        Bundle bundle2 = bundle == null ? new Bundle() : bundle;
        if (zzs().zza(zzas.zzbu) && zzkv.zzc(str2, FirebaseAnalytics.Event.SCREEN_VIEW)) {
            zzh().zza(bundle2, j);
            return;
        }
        zzb(str3, str2, j, bundle2, z2, !z2 || this.zzc == null || zzkv.zzd(str2), !z, null);
    }

    private final void zzb(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) throws IllegalStateException {
        zzp().zza(new zzhj(this, str, str2, j, zzkv.zzb(bundle), z, z2, z3, str3));
    }

    public final void zza(String str, String str2, Object obj, boolean z) throws IllegalStateException {
        zza(str, str2, obj, true, zzl().currentTimeMillis());
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(java.lang.String r11, java.lang.String r12, java.lang.Object r13, boolean r14, long r15) throws java.lang.IllegalStateException {
        /*
            r10 = this;
            r6 = r10
            r2 = r12
            r0 = r13
            if (r11 != 0) goto L8
            java.lang.String r1 = "app"
            goto L9
        L8:
            r1 = r11
        L9:
            r3 = 0
            r4 = 24
            if (r14 == 0) goto L18
            com.google.android.gms.measurement.internal.zzkv r5 = r10.zzo()
            int r5 = r5.zzb(r12)
        L16:
            r9 = r5
            goto L39
        L18:
            com.google.android.gms.measurement.internal.zzkv r5 = r10.zzo()
            java.lang.String r7 = "user property"
            boolean r8 = r5.zza(r7, r12)
            r9 = 6
            if (r8 != 0) goto L26
            goto L39
        L26:
            java.lang.String[] r8 = com.google.android.gms.measurement.internal.zzgx.zza
            boolean r8 = r5.zza(r7, r8, r12)
            if (r8 != 0) goto L31
            r5 = 15
            goto L16
        L31:
            boolean r5 = r5.zza(r7, r4, r12)
            if (r5 != 0) goto L38
            goto L39
        L38:
            r9 = r3
        L39:
            r5 = 1
            if (r9 == 0) goto L5e
            r10.zzo()
            java.lang.String r0 = com.google.android.gms.measurement.internal.zzkv.zza(r12, r4, r5)
            if (r2 == 0) goto L49
            int r3 = r12.length()
        L49:
            com.google.android.gms.measurement.internal.zzfu r1 = r6.zzy
            com.google.android.gms.measurement.internal.zzkv r1 = r1.zzh()
            com.google.android.gms.measurement.internal.zzky r2 = r6.zzn
            java.lang.String r4 = "_ev"
            r11 = r1
            r12 = r2
            r13 = r9
            r14 = r4
            r15 = r0
            r16 = r3
            r11.zza(r12, r13, r14, r15, r16)
            return
        L5e:
            if (r0 == 0) goto La7
            com.google.android.gms.measurement.internal.zzkv r7 = r10.zzo()
            int r7 = r7.zzb(r12, r13)
            if (r7 == 0) goto L96
            r10.zzo()
            java.lang.String r1 = com.google.android.gms.measurement.internal.zzkv.zza(r12, r4, r5)
            boolean r2 = r0 instanceof java.lang.String
            if (r2 != 0) goto L79
            boolean r2 = r0 instanceof java.lang.CharSequence
            if (r2 == 0) goto L81
        L79:
            java.lang.String r0 = java.lang.String.valueOf(r13)
            int r3 = r0.length()
        L81:
            com.google.android.gms.measurement.internal.zzfu r0 = r6.zzy
            com.google.android.gms.measurement.internal.zzkv r0 = r0.zzh()
            com.google.android.gms.measurement.internal.zzky r2 = r6.zzn
            java.lang.String r4 = "_ev"
            r11 = r0
            r12 = r2
            r13 = r7
            r14 = r4
            r15 = r1
            r16 = r3
            r11.zza(r12, r13, r14, r15, r16)
            return
        L96:
            com.google.android.gms.measurement.internal.zzkv r3 = r10.zzo()
            java.lang.Object r5 = r3.zzc(r12, r13)
            if (r5 == 0) goto La6
            r0 = r10
            r2 = r12
            r3 = r15
            r0.zza(r1, r2, r3, r5)
        La6:
            return
        La7:
            r5 = 0
            r0 = r10
            r2 = r12
            r3 = r15
            r0.zza(r1, r2, r3, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzhb.zza(java.lang.String, java.lang.String, java.lang.Object, boolean, long):void");
    }

    private final void zza(String str, String str2, long j, Object obj) throws IllegalStateException {
        zzp().zza(new zzhi(this, str, str2, obj, j));
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final void zza(java.lang.String r9, java.lang.String r10, java.lang.Object r11, long r12) throws java.lang.IllegalStateException {
        /*
            r8 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r9)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r10)
            r8.zzc()
            r8.zzv()
            java.lang.String r0 = "allow_personalized_ads"
            boolean r0 = r0.equals(r10)
            if (r0 == 0) goto L63
            boolean r0 = r11 instanceof java.lang.String
            java.lang.String r1 = "_npa"
            if (r0 == 0) goto L53
            r0 = r11
            java.lang.String r0 = (java.lang.String) r0
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L53
            java.util.Locale r10 = java.util.Locale.ENGLISH
            java.lang.String r10 = r0.toLowerCase(r10)
            java.lang.String r11 = "false"
            boolean r10 = r11.equals(r10)
            r2 = 1
            if (r10 == 0) goto L35
            r4 = r2
            goto L37
        L35:
            r4 = 0
        L37:
            java.lang.Long r10 = java.lang.Long.valueOf(r4)
            com.google.android.gms.measurement.internal.zzfc r0 = r8.zzr()
            com.google.android.gms.measurement.internal.zzfi r0 = r0.zzn
            r4 = r10
            java.lang.Long r4 = (java.lang.Long) r4
            long r4 = r10.longValue()
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 != 0) goto L4e
            java.lang.String r11 = "true"
        L4e:
            r0.zza(r11)
            r6 = r10
            goto L61
        L53:
            if (r11 != 0) goto L63
            com.google.android.gms.measurement.internal.zzfc r10 = r8.zzr()
            com.google.android.gms.measurement.internal.zzfi r10 = r10.zzn
            java.lang.String r0 = "unset"
            r10.zza(r0)
            r6 = r11
        L61:
            r3 = r1
            goto L65
        L63:
            r3 = r10
            r6 = r11
        L65:
            com.google.android.gms.measurement.internal.zzfu r10 = r8.zzy
            boolean r10 = r10.zzaa()
            if (r10 != 0) goto L7b
            com.google.android.gms.measurement.internal.zzeq r9 = r8.zzq()
            com.google.android.gms.measurement.internal.zzes r9 = r9.zzw()
            java.lang.String r10 = "User property not set since app measurement is disabled"
            r9.zza(r10)
            return
        L7b:
            com.google.android.gms.measurement.internal.zzfu r10 = r8.zzy
            boolean r10 = r10.zzaf()
            if (r10 != 0) goto L84
            return
        L84:
            com.google.android.gms.measurement.internal.zzku r10 = new com.google.android.gms.measurement.internal.zzku
            r2 = r10
            r4 = r12
            r7 = r9
            r2.<init>(r3, r4, r6, r7)
            com.google.android.gms.measurement.internal.zzir r9 = r8.zzg()
            r9.zza(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzhb.zza(java.lang.String, java.lang.String, java.lang.Object, long):void");
    }

    public final List<zzku> zza(boolean z) throws IllegalStateException {
        zzv();
        zzq().zzw().zza("Getting user properties (FE)");
        if (zzp().zzf()) {
            zzq().zze().zza("Cannot get all user properties from analytics worker thread");
            return Collections.emptyList();
        }
        if (zzw.zza()) {
            zzq().zze().zza("Cannot get all user properties from main thread");
            return Collections.emptyList();
        }
        AtomicReference atomicReference = new AtomicReference();
        this.zzy.zzp().zza(atomicReference, 5000L, "get user properties", new zzhl(this, atomicReference, z));
        List<zzku> list = (List) atomicReference.get();
        if (list != null) {
            return list;
        }
        zzq().zze().zza("Timed out waiting for get user properties, includeInternal", Boolean.valueOf(z));
        return Collections.emptyList();
    }

    public final String zzag() {
        return this.zzf.get();
    }

    final void zza(String str) {
        this.zzf.set(str);
    }

    final void zza(long j, boolean z) throws IllegalStateException {
        zzc();
        zzv();
        zzq().zzv().zza("Resetting analytics data (FE)");
        zzjx zzjxVarZzj = zzj();
        zzjxVarZzj.zzc();
        zzjxVarZzj.zzb.zza();
        boolean zZzaa = this.zzy.zzaa();
        zzfc zzfcVarZzr = zzr();
        zzfcVarZzr.zzh.zza(j);
        if (!TextUtils.isEmpty(zzfcVarZzr.zzr().zzu.zza())) {
            zzfcVarZzr.zzu.zza(null);
        }
        if (zznj.zzb() && zzfcVarZzr.zzs().zza(zzas.zzbp)) {
            zzfcVarZzr.zzp.zza(0L);
        }
        if (!zzfcVarZzr.zzs().zzf()) {
            zzfcVarZzr.zzb(!zZzaa);
        }
        zzfcVarZzr.zzv.zza(null);
        zzfcVarZzr.zzw.zza(0L);
        zzfcVarZzr.zzx.zza(null);
        if (z) {
            zzg().zzac();
        }
        if (zznj.zzb() && zzs().zza(zzas.zzbp)) {
            zzj().zza.zza();
        }
        this.zzm = !zZzaa;
    }

    public final void zzah() {
        zzc();
        zzv();
        if (this.zzy.zzaf()) {
            if (zzs().zza(zzas.zzbc)) {
                Boolean boolZzf = zzs().zzf("google_analytics_deferred_deep_link_enabled");
                if (boolZzf != null && boolZzf.booleanValue()) {
                    zzq().zzv().zza("Deferred Deep Link feature enabled.");
                    zzp().zza(new Runnable(this) { // from class: com.google.android.gms.measurement.internal.zzhd
                        private final zzhb zza;

                        {
                            this.zza = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() throws IllegalStateException {
                            zzhb zzhbVar = this.zza;
                            zzhbVar.zzc();
                            if (zzhbVar.zzr().zzs.zza()) {
                                zzhbVar.zzq().zzv().zza("Deferred Deep Link already retrieved. Not fetching again.");
                                return;
                            }
                            long jZza = zzhbVar.zzr().zzt.zza();
                            zzhbVar.zzr().zzt.zza(1 + jZza);
                            if (jZza < 5) {
                                zzhbVar.zzy.zzag();
                            } else {
                                zzhbVar.zzq().zzh().zza("Permanently failed to retrieve Deferred Deep Link. Reached maximum retries.");
                                zzhbVar.zzr().zzs.zza(true);
                            }
                        }
                    });
                }
            }
            zzg().zzad();
            this.zzm = false;
            String strZzy = zzr().zzy();
            if (TextUtils.isEmpty(strZzy)) {
                return;
            }
            zzk().zzab();
            if (strZzy.equals(Build.VERSION.RELEASE)) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("_po", strZzy);
            zza("auto", "_ou", bundle);
        }
    }

    public final void zza(zzgw zzgwVar) {
        zzgw zzgwVar2;
        zzc();
        zzv();
        if (zzgwVar != null && zzgwVar != (zzgwVar2 = this.zzc)) {
            Preconditions.checkState(zzgwVar2 == null, "EventInterceptor already set.");
        }
        this.zzc = zzgwVar;
    }

    public final void zza(zzgz zzgzVar) throws IllegalStateException {
        zzv();
        Preconditions.checkNotNull(zzgzVar);
        if (this.zzd.add(zzgzVar)) {
            return;
        }
        zzq().zzh().zza("OnEventListener already registered");
    }

    public final void zzb(zzgz zzgzVar) throws IllegalStateException {
        zzv();
        Preconditions.checkNotNull(zzgzVar);
        if (this.zzd.remove(zzgzVar)) {
            return;
        }
        zzq().zzh().zza("OnEventListener had not been registered");
    }

    public final void zza(Bundle bundle) throws IllegalStateException {
        zza(bundle, zzl().currentTimeMillis());
    }

    public final void zza(Bundle bundle, long j) throws IllegalStateException {
        Preconditions.checkNotNull(bundle);
        Bundle bundle2 = new Bundle(bundle);
        if (!TextUtils.isEmpty(bundle2.getString("app_id"))) {
            zzq().zzh().zza("Package name should be null when calling setConditionalUserProperty");
        }
        bundle2.remove("app_id");
        Preconditions.checkNotNull(bundle2);
        zzgs.zza(bundle2, "app_id", String.class, null);
        zzgs.zza(bundle2, "origin", String.class, null);
        zzgs.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.NAME, String.class, null);
        zzgs.zza(bundle2, "value", Object.class, null);
        zzgs.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, String.class, null);
        zzgs.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, Long.class, 0L);
        zzgs.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, String.class, null);
        zzgs.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, Bundle.class, null);
        zzgs.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, String.class, null);
        zzgs.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, Bundle.class, null);
        zzgs.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, Long.class, 0L);
        zzgs.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, String.class, null);
        zzgs.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, Bundle.class, null);
        Preconditions.checkNotEmpty(bundle2.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
        Preconditions.checkNotEmpty(bundle2.getString("origin"));
        Preconditions.checkNotNull(bundle2.get("value"));
        bundle2.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, j);
        String string = bundle2.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
        Object obj = bundle2.get("value");
        if (zzo().zzb(string) != 0) {
            zzq().zze().zza("Invalid conditional user property name", zzn().zzc(string));
            return;
        }
        if (zzo().zzb(string, obj) != 0) {
            zzq().zze().zza("Invalid conditional user property value", zzn().zzc(string), obj);
            return;
        }
        Object objZzc = zzo().zzc(string, obj);
        if (objZzc == null) {
            zzq().zze().zza("Unable to normalize conditional user property value", zzn().zzc(string), obj);
            return;
        }
        zzgs.zza(bundle2, objZzc);
        long j2 = bundle2.getLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT);
        if (!TextUtils.isEmpty(bundle2.getString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME)) && (j2 > 15552000000L || j2 < 1)) {
            zzq().zze().zza("Invalid conditional user property timeout", zzn().zzc(string), Long.valueOf(j2));
            return;
        }
        long j3 = bundle2.getLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE);
        if (j3 > 15552000000L || j3 < 1) {
            zzq().zze().zza("Invalid conditional user property time to live", zzn().zzc(string), Long.valueOf(j3));
        } else {
            zzp().zza(new zzhn(this, bundle2));
        }
    }

    public final void zzc(String str, String str2, Bundle bundle) throws IllegalStateException {
        long jCurrentTimeMillis = zzl().currentTimeMillis();
        Preconditions.checkNotEmpty(str);
        Bundle bundle2 = new Bundle();
        bundle2.putString(AppMeasurementSdk.ConditionalUserProperty.NAME, str);
        bundle2.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, jCurrentTimeMillis);
        if (str2 != null) {
            bundle2.putString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, str2);
            bundle2.putBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, bundle);
        }
        zzp().zza(new zzhp(this, bundle2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzc(Bundle bundle) throws IllegalStateException {
        zzc();
        zzv();
        Preconditions.checkNotNull(bundle);
        Preconditions.checkNotEmpty(bundle.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
        Preconditions.checkNotEmpty(bundle.getString("origin"));
        Preconditions.checkNotNull(bundle.get("value"));
        if (!this.zzy.zzaa()) {
            zzq().zzw().zza("Conditional property not set since app measurement is disabled");
            return;
        }
        try {
            zzg().zza(new zzz(bundle.getString("app_id"), bundle.getString("origin"), new zzku(bundle.getString(AppMeasurementSdk.ConditionalUserProperty.NAME), bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP), bundle.get("value"), bundle.getString("origin")), bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP), false, bundle.getString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME), zzo().zza(bundle.getString("app_id"), bundle.getString(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME), bundle.getBundle(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS), bundle.getString("origin"), 0L, true, false, zzlo.zzb() && zzs().zza(zzas.zzck)), bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT), zzo().zza(bundle.getString("app_id"), bundle.getString(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME), bundle.getBundle(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS), bundle.getString("origin"), 0L, true, false, zzlo.zzb() && zzs().zza(zzas.zzck)), bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE), zzo().zza(bundle.getString("app_id"), bundle.getString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME), bundle.getBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS), bundle.getString("origin"), 0L, true, false, zzlo.zzb() && zzs().zza(zzas.zzck))));
        } catch (IllegalArgumentException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzd(Bundle bundle) throws IllegalStateException {
        zzc();
        zzv();
        Preconditions.checkNotNull(bundle);
        Preconditions.checkNotEmpty(bundle.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
        if (!this.zzy.zzaa()) {
            zzq().zzw().zza("Conditional property not cleared since app measurement is disabled");
        } else {
            try {
                zzg().zza(new zzz(bundle.getString("app_id"), bundle.getString("origin"), new zzku(bundle.getString(AppMeasurementSdk.ConditionalUserProperty.NAME), 0L, null, null), bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP), bundle.getBoolean(AppMeasurementSdk.ConditionalUserProperty.ACTIVE), bundle.getString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME), null, bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT), null, bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE), zzo().zza(bundle.getString("app_id"), bundle.getString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME), bundle.getBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS), bundle.getString("origin"), bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP), true, false, zzlo.zzb() && zzs().zza(zzas.zzck))));
            } catch (IllegalArgumentException unused) {
            }
        }
    }

    public final ArrayList<Bundle> zza(String str, String str2) throws IllegalStateException {
        if (zzp().zzf()) {
            zzq().zze().zza("Cannot get conditional user properties from analytics worker thread");
            return new ArrayList<>(0);
        }
        if (zzw.zza()) {
            zzq().zze().zza("Cannot get conditional user properties from main thread");
            return new ArrayList<>(0);
        }
        AtomicReference atomicReference = new AtomicReference();
        this.zzy.zzp().zza(atomicReference, 5000L, "get conditional user properties", new zzho(this, atomicReference, null, str, str2));
        List list = (List) atomicReference.get();
        if (list == null) {
            zzq().zze().zza("Timed out waiting for get conditional user properties", null);
            return new ArrayList<>();
        }
        return zzkv.zzb((List<zzz>) list);
    }

    public final Map<String, Object> zza(String str, String str2, boolean z) throws IllegalStateException {
        if (zzp().zzf()) {
            zzq().zze().zza("Cannot get user properties from analytics worker thread");
            return Collections.emptyMap();
        }
        if (zzw.zza()) {
            zzq().zze().zza("Cannot get user properties from main thread");
            return Collections.emptyMap();
        }
        AtomicReference atomicReference = new AtomicReference();
        this.zzy.zzp().zza(atomicReference, 5000L, "get user properties", new zzhr(this, atomicReference, null, str, str2, z));
        List<zzku> list = (List) atomicReference.get();
        if (list == null) {
            zzq().zze().zza("Timed out waiting for handle get user properties, includeInternal", Boolean.valueOf(z));
            return Collections.emptyMap();
        }
        ArrayMap arrayMap = new ArrayMap(list.size());
        for (zzku zzkuVar : list) {
            arrayMap.put(zzkuVar.zza, zzkuVar.zza());
        }
        return arrayMap;
    }

    public final String zzai() {
        zzij zzijVarZzaa = this.zzy.zzu().zzaa();
        if (zzijVarZzaa != null) {
            return zzijVarZzaa.zza;
        }
        return null;
    }

    public final String zzaj() {
        zzij zzijVarZzaa = this.zzy.zzu().zzaa();
        if (zzijVarZzaa != null) {
            return zzijVarZzaa.zzb;
        }
        return null;
    }

    public final String zzak() throws IllegalStateException {
        if (this.zzy.zzn() != null) {
            return this.zzy.zzn();
        }
        try {
            return zzig.zza(zzm(), "google_app_id");
        } catch (IllegalStateException e) {
            this.zzy.zzq().zze().zza("getGoogleAppId failed with exception", e);
            return null;
        }
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

    final /* synthetic */ void zzb(Bundle bundle) throws IllegalStateException {
        if (zznw.zzb() && zzs().zza(zzas.zzby)) {
            if (bundle == null) {
                zzr().zzx.zza(new Bundle());
                return;
            }
            Bundle bundleZza = zzr().zzx.zza();
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                if (obj != null && !(obj instanceof String) && !(obj instanceof Long) && !(obj instanceof Double)) {
                    zzo();
                    if (zzkv.zza(obj)) {
                        zzo().zza(this.zzn, 27, (String) null, (String) null, 0);
                    }
                    zzq().zzj().zza("Invalid default event parameter type. Name, value", str, obj);
                } else if (zzkv.zzd(str)) {
                    zzq().zzj().zza("Invalid default event parameter name. Name", str);
                } else if (obj == null) {
                    bundleZza.remove(str);
                } else if (zzo().zza("param", str, 100, obj)) {
                    zzo().zza(bundleZza, str, obj);
                }
            }
            zzo();
            if (zzkv.zza(bundleZza, zzs().zzd())) {
                zzo().zza(this.zzn, 26, (String) null, (String) null, 0);
                zzq().zzj().zza("Too many default event parameters set. Discarding beyond event parameter limit");
            }
            zzr().zzx.zza(bundleZza);
            zzg().zza(bundleZza);
        }
    }
}
