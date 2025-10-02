package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.api.internal.GoogleServices;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzml;
import com.google.android.gms.internal.measurement.zzms;
import com.google.android.gms.internal.measurement.zznw;
import com.google.firebase.messaging.Constants;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public class zzfu implements zzgt {
    private static volatile zzfu zzb;
    final long zza;
    private Boolean zzaa;
    private long zzab;
    private volatile Boolean zzac;
    private Boolean zzad;
    private Boolean zzae;
    private volatile boolean zzaf;
    private int zzag;
    private final Context zzc;
    private final String zzd;
    private final String zze;
    private final String zzf;
    private final boolean zzg;
    private final zzw zzh;
    private final zzab zzi;
    private final zzfc zzj;
    private final zzeq zzk;
    private final zzfr zzl;
    private final zzjx zzm;
    private final zzkv zzn;
    private final zzeo zzo;
    private final Clock zzp;
    private final zzii zzq;
    private final zzhb zzr;
    private final zza zzs;
    private final zzid zzt;
    private zzem zzu;
    private zzir zzv;
    private zzak zzw;
    private zzen zzx;
    private zzfl zzy;
    private boolean zzz = false;
    private AtomicInteger zzah = new AtomicInteger(0);

    private zzfu(zzgy zzgyVar) throws IllegalStateException {
        boolean z = false;
        Preconditions.checkNotNull(zzgyVar);
        zzw zzwVar = new zzw(zzgyVar.zza);
        this.zzh = zzwVar;
        zzeg.zza = zzwVar;
        Context context = zzgyVar.zza;
        this.zzc = context;
        this.zzd = zzgyVar.zzb;
        this.zze = zzgyVar.zzc;
        this.zzf = zzgyVar.zzd;
        this.zzg = zzgyVar.zzh;
        this.zzac = zzgyVar.zze;
        this.zzaf = true;
        com.google.android.gms.internal.measurement.zzae zzaeVar = zzgyVar.zzg;
        if (zzaeVar != null && zzaeVar.zzg != null) {
            Object obj = zzaeVar.zzg.get("measurementEnabled");
            if (obj instanceof Boolean) {
                this.zzad = (Boolean) obj;
            }
            Object obj2 = zzaeVar.zzg.get("measurementDeactivated");
            if (obj2 instanceof Boolean) {
                this.zzae = (Boolean) obj2;
            }
        }
        com.google.android.gms.internal.measurement.zzdh.zza(context);
        Clock defaultClock = DefaultClock.getInstance();
        this.zzp = defaultClock;
        this.zza = zzgyVar.zzi != null ? zzgyVar.zzi.longValue() : defaultClock.currentTimeMillis();
        this.zzi = new zzab(this);
        zzfc zzfcVar = new zzfc(this);
        zzfcVar.zzac();
        this.zzj = zzfcVar;
        zzeq zzeqVar = new zzeq(this);
        zzeqVar.zzac();
        this.zzk = zzeqVar;
        zzkv zzkvVar = new zzkv(this);
        zzkvVar.zzac();
        this.zzn = zzkvVar;
        zzeo zzeoVar = new zzeo(this);
        zzeoVar.zzac();
        this.zzo = zzeoVar;
        this.zzs = new zza(this);
        zzii zziiVar = new zzii(this);
        zziiVar.zzw();
        this.zzq = zziiVar;
        zzhb zzhbVar = new zzhb(this);
        zzhbVar.zzw();
        this.zzr = zzhbVar;
        zzjx zzjxVar = new zzjx(this);
        zzjxVar.zzw();
        this.zzm = zzjxVar;
        zzid zzidVar = new zzid(this);
        zzidVar.zzac();
        this.zzt = zzidVar;
        zzfr zzfrVar = new zzfr(this);
        zzfrVar.zzac();
        this.zzl = zzfrVar;
        if (zzgyVar.zzg != null && zzgyVar.zzg.zzb != 0) {
            z = true;
        }
        boolean z2 = !z;
        if (context.getApplicationContext() instanceof Application) {
            zzhb zzhbVarZzg = zzg();
            if (zzhbVarZzg.zzm().getApplicationContext() instanceof Application) {
                Application application = (Application) zzhbVarZzg.zzm().getApplicationContext();
                if (zzhbVarZzg.zza == null) {
                    zzhbVarZzg.zza = new zzhy(zzhbVarZzg, null);
                }
                if (z2) {
                    application.unregisterActivityLifecycleCallbacks(zzhbVarZzg.zza);
                    application.registerActivityLifecycleCallbacks(zzhbVarZzg.zza);
                    zzhbVarZzg.zzq().zzw().zza("Registered activity lifecycle callback");
                }
            }
        } else {
            zzq().zzh().zza("Application context is not an Application");
        }
        zzfrVar.zza(new zzfw(this, zzgyVar));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzgy zzgyVar) throws IllegalStateException {
        String strConcat;
        zzes zzesVarZzu;
        zzp().zzc();
        zzak zzakVar = new zzak(this);
        zzakVar.zzac();
        this.zzw = zzakVar;
        zzen zzenVar = new zzen(this, zzgyVar.zzf);
        zzenVar.zzw();
        this.zzx = zzenVar;
        zzem zzemVar = new zzem(this);
        zzemVar.zzw();
        this.zzu = zzemVar;
        zzir zzirVar = new zzir(this);
        zzirVar.zzw();
        this.zzv = zzirVar;
        this.zzn.zzad();
        this.zzj.zzad();
        this.zzy = new zzfl(this);
        this.zzx.zzx();
        zzq().zzu().zza("App measurement initialized, version", 33025L);
        zzq().zzu().zza("To enable debug logging run: adb shell setprop log.tag.FA VERBOSE");
        String strZzaa = zzenVar.zzaa();
        if (TextUtils.isEmpty(this.zzd)) {
            if (zzh().zze(strZzaa)) {
                zzesVarZzu = zzq().zzu();
                strConcat = "Faster debug mode event logging enabled. To disable, run:\n  adb shell setprop debug.firebase.analytics.app .none.";
            } else {
                zzes zzesVarZzu2 = zzq().zzu();
                String strValueOf = String.valueOf(strZzaa);
                strConcat = strValueOf.length() != 0 ? "To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ".concat(strValueOf) : new String("To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ");
                zzesVarZzu = zzesVarZzu2;
            }
            zzesVarZzu.zza(strConcat);
        }
        zzq().zzv().zza("Debug-level message logging enabled");
        if (this.zzag != this.zzah.get()) {
            zzq().zze().zza("Not all components initialized", Integer.valueOf(this.zzag), Integer.valueOf(this.zzah.get()));
        }
        this.zzz = true;
    }

    protected final void zza(com.google.android.gms.internal.measurement.zzae zzaeVar) throws IllegalStateException {
        zzac zzacVarZzb;
        zzp().zzc();
        if (zzml.zzb() && this.zzi.zza(zzas.zzcg)) {
            zzac zzacVarZzx = zzb().zzx();
            int iZzw = zzb().zzw();
            int i = 40;
            if (this.zzi.zza(zzas.zzch)) {
                Boolean boolZzi = this.zzi.zzi();
                Boolean boolZzj = this.zzi.zzj();
                if ((boolZzi != null || boolZzj != null) && zzb().zza(20)) {
                    zzacVarZzb = new zzac(boolZzi, boolZzj);
                    i = 20;
                } else {
                    if (!TextUtils.isEmpty(zzx().zzab()) && (iZzw == 30 || iZzw == 40)) {
                        zzg().zza(zzac.zza, 20, this.zza);
                    } else if (zzaeVar != null && zzaeVar.zzg != null && zzb().zza(40)) {
                        zzacVarZzb = zzac.zzb(zzaeVar.zzg);
                        if (zzacVarZzb.equals(zzac.zza)) {
                        }
                    }
                    i = 0;
                    zzacVarZzb = null;
                }
                if (zzacVarZzb != null) {
                    zzg().zza(zzacVarZzb, i, this.zza);
                    zzacVarZzx = zzacVarZzb;
                }
                zzg().zza(zzacVarZzx);
            } else {
                if (zzaeVar != null && zzaeVar.zzg != null && zzb().zza(40)) {
                    zzacVarZzb = zzac.zzb(zzaeVar.zzg);
                    if (!zzacVarZzb.equals(zzac.zza)) {
                        zzg().zza(zzacVarZzb, 40, this.zza);
                        zzacVarZzx = zzacVarZzb;
                    }
                }
                zzg().zza(zzacVarZzx);
            }
        }
        if (zzb().zzc.zza() == 0) {
            zzb().zzc.zza(this.zzp.currentTimeMillis());
        }
        if (Long.valueOf(zzb().zzh.zza()).longValue() == 0) {
            zzq().zzw().zza("Persisting first open", Long.valueOf(this.zza));
            zzb().zzh.zza(this.zza);
        }
        if (this.zzi.zza(zzas.zzcc)) {
            zzg().zzb.zzb();
        }
        if (!zzaf()) {
            if (zzaa()) {
                if (!zzh().zzc("android.permission.INTERNET")) {
                    zzq().zze().zza("App is missing INTERNET permission");
                }
                if (!zzh().zzc("android.permission.ACCESS_NETWORK_STATE")) {
                    zzq().zze().zza("App is missing ACCESS_NETWORK_STATE permission");
                }
                if (!Wrappers.packageManager(this.zzc).isCallerInstantApp() && !this.zzi.zzy()) {
                    if (!zzfm.zza(this.zzc)) {
                        zzq().zze().zza("AppMeasurementReceiver not registered/enabled");
                    }
                    if (!zzkv.zza(this.zzc, false)) {
                        zzq().zze().zza("AppMeasurementService not registered/enabled");
                    }
                }
                zzq().zze().zza("Uploading is not possible. App measurement disabled");
            }
        } else {
            if (!TextUtils.isEmpty(zzx().zzab()) || !TextUtils.isEmpty(zzx().zzac())) {
                zzh();
                if (zzkv.zza(zzx().zzab(), zzb().zzg(), zzx().zzac(), zzb().zzh())) {
                    zzq().zzu().zza("Rechecking which service to use due to a GMP App Id change");
                    zzb().zzj();
                    zzj().zzaa();
                    this.zzv.zzag();
                    this.zzv.zzae();
                    zzb().zzh.zza(this.zza);
                    zzb().zzj.zza(null);
                }
                zzb().zzb(zzx().zzab());
                zzb().zzc(zzx().zzac());
            }
            if (zzml.zzb() && this.zzi.zza(zzas.zzcg) && !zzb().zzx().zze()) {
                zzb().zzj.zza(null);
            }
            zzg().zza(zzb().zzj.zza());
            if (zzms.zzb() && this.zzi.zza(zzas.zzbo) && !zzh().zzj() && !TextUtils.isEmpty(zzb().zzu.zza())) {
                zzq().zzh().zza("Remote config removed with active feature rollouts");
                zzb().zzu.zza(null);
            }
            if (!TextUtils.isEmpty(zzx().zzab()) || !TextUtils.isEmpty(zzx().zzac())) {
                boolean zZzaa = zzaa();
                if (!zzb().zzz() && !this.zzi.zzf()) {
                    zzb().zzb(!zZzaa);
                }
                if (zZzaa) {
                    zzg().zzah();
                }
                zzd().zza.zza();
                zzv().zza(new AtomicReference<>());
                if (zznw.zzb() && this.zzi.zza(zzas.zzby)) {
                    zzv().zza(zzb().zzx.zza());
                }
            }
        }
        zzb().zzo.zza(this.zzi.zza(zzas.zzax));
    }

    @Override // com.google.android.gms.measurement.internal.zzgt
    public final zzw zzt() {
        return this.zzh;
    }

    public final zzab zza() {
        return this.zzi;
    }

    public final zzfc zzb() {
        zza((zzgr) this.zzj);
        return this.zzj;
    }

    @Override // com.google.android.gms.measurement.internal.zzgt
    public final zzeq zzq() {
        zzb(this.zzk);
        return this.zzk;
    }

    public final zzeq zzc() {
        zzeq zzeqVar = this.zzk;
        if (zzeqVar == null || !zzeqVar.zzaa()) {
            return null;
        }
        return this.zzk;
    }

    @Override // com.google.android.gms.measurement.internal.zzgt
    public final zzfr zzp() {
        zzb(this.zzl);
        return this.zzl;
    }

    public final zzjx zzd() {
        zzb(this.zzm);
        return this.zzm;
    }

    public final zzfl zze() {
        return this.zzy;
    }

    final zzfr zzf() {
        return this.zzl;
    }

    public final zzhb zzg() {
        zzb(this.zzr);
        return this.zzr;
    }

    public final zzkv zzh() {
        zza((zzgr) this.zzn);
        return this.zzn;
    }

    public final zzeo zzi() {
        zza((zzgr) this.zzo);
        return this.zzo;
    }

    public final zzem zzj() {
        zzb(this.zzu);
        return this.zzu;
    }

    private final zzid zzah() {
        zzb(this.zzt);
        return this.zzt;
    }

    @Override // com.google.android.gms.measurement.internal.zzgt
    public final Context zzm() {
        return this.zzc;
    }

    public final boolean zzk() {
        return TextUtils.isEmpty(this.zzd);
    }

    public final String zzn() {
        return this.zzd;
    }

    public final String zzo() {
        return this.zze;
    }

    public final String zzr() {
        return this.zzf;
    }

    public final boolean zzs() {
        return this.zzg;
    }

    @Override // com.google.android.gms.measurement.internal.zzgt
    public final Clock zzl() {
        return this.zzp;
    }

    public final zzii zzu() {
        zzb(this.zzq);
        return this.zzq;
    }

    public final zzir zzv() {
        zzb(this.zzv);
        return this.zzv;
    }

    public final zzak zzw() {
        zzb(this.zzw);
        return this.zzw;
    }

    public final zzen zzx() {
        zzb(this.zzx);
        return this.zzx;
    }

    public final zza zzy() {
        zza zzaVar = this.zzs;
        if (zzaVar != null) {
            return zzaVar;
        }
        throw new IllegalStateException("Component not created");
    }

    public static zzfu zza(Context context, com.google.android.gms.internal.measurement.zzae zzaeVar, Long l) {
        if (zzaeVar != null && (zzaeVar.zze == null || zzaeVar.zzf == null)) {
            zzaeVar = new com.google.android.gms.internal.measurement.zzae(zzaeVar.zza, zzaeVar.zzb, zzaeVar.zzc, zzaeVar.zzd, null, null, zzaeVar.zzg);
        }
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzb == null) {
            synchronized (zzfu.class) {
                if (zzb == null) {
                    zzb = new zzfu(new zzgy(context, zzaeVar, l));
                }
            }
        } else if (zzaeVar != null && zzaeVar.zzg != null && zzaeVar.zzg.containsKey("dataCollectionDefaultEnabled")) {
            zzb.zza(zzaeVar.zzg.getBoolean("dataCollectionDefaultEnabled"));
        }
        return zzb;
    }

    private static void zzb(zzgq zzgqVar) {
        if (zzgqVar == null) {
            throw new IllegalStateException("Component not created");
        }
        if (zzgqVar.zzaa()) {
            return;
        }
        String strValueOf = String.valueOf(zzgqVar.getClass());
        throw new IllegalStateException(new StringBuilder(String.valueOf(strValueOf).length() + 27).append("Component not initialized: ").append(strValueOf).toString());
    }

    private static void zzb(zzg zzgVar) {
        if (zzgVar == null) {
            throw new IllegalStateException("Component not created");
        }
        if (zzgVar.zzu()) {
            return;
        }
        String strValueOf = String.valueOf(zzgVar.getClass());
        throw new IllegalStateException(new StringBuilder(String.valueOf(strValueOf).length() + 27).append("Component not initialized: ").append(strValueOf).toString());
    }

    private static void zza(zzgr zzgrVar) {
        if (zzgrVar == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    final void zza(boolean z) {
        this.zzac = Boolean.valueOf(z);
    }

    public final boolean zzz() {
        return this.zzac != null && this.zzac.booleanValue();
    }

    public final boolean zzaa() {
        return zzab() == 0;
    }

    public final int zzab() {
        zzp().zzc();
        if (this.zzi.zzf()) {
            return 1;
        }
        Boolean bool = this.zzae;
        if (bool != null && bool.booleanValue()) {
            return 2;
        }
        if (zzml.zzb() && this.zzi.zza(zzas.zzcg) && !zzac()) {
            return 8;
        }
        Boolean boolZzu = zzb().zzu();
        if (boolZzu != null) {
            return boolZzu.booleanValue() ? 0 : 3;
        }
        Boolean boolZzf = this.zzi.zzf("firebase_analytics_collection_enabled");
        if (boolZzf != null) {
            return boolZzf.booleanValue() ? 0 : 4;
        }
        Boolean bool2 = this.zzad;
        if (bool2 != null) {
            return bool2.booleanValue() ? 0 : 5;
        }
        if (GoogleServices.isMeasurementExplicitlyDisabled()) {
            return 6;
        }
        return (!this.zzi.zza(zzas.zzar) || this.zzac == null || this.zzac.booleanValue()) ? 0 : 7;
    }

    public final void zzb(boolean z) {
        zzp().zzc();
        this.zzaf = z;
    }

    public final boolean zzac() {
        zzp().zzc();
        return this.zzaf;
    }

    final void zzad() {
        throw new IllegalStateException("Unexpected call on client side");
    }

    final void zza(zzgq zzgqVar) {
        this.zzag++;
    }

    final void zza(zzg zzgVar) {
        this.zzag++;
    }

    final void zzae() {
        this.zzah.incrementAndGet();
    }

    protected final boolean zzaf() {
        if (!this.zzz) {
            throw new IllegalStateException("AppMeasurement is not initialized");
        }
        zzp().zzc();
        Boolean bool = this.zzaa;
        if (bool == null || this.zzab == 0 || (bool != null && !bool.booleanValue() && Math.abs(this.zzp.elapsedRealtime() - this.zzab) > 1000)) {
            this.zzab = this.zzp.elapsedRealtime();
            boolean z = true;
            Boolean boolValueOf = Boolean.valueOf(zzh().zzc("android.permission.INTERNET") && zzh().zzc("android.permission.ACCESS_NETWORK_STATE") && (Wrappers.packageManager(this.zzc).isCallerInstantApp() || this.zzi.zzy() || (zzfm.zza(this.zzc) && zzkv.zza(this.zzc, false))));
            this.zzaa = boolValueOf;
            if (boolValueOf.booleanValue()) {
                if (!zzh().zza(zzx().zzab(), zzx().zzac(), zzx().zzad()) && TextUtils.isEmpty(zzx().zzac())) {
                    z = false;
                }
                this.zzaa = Boolean.valueOf(z);
            }
        }
        return this.zzaa.booleanValue();
    }

    public final void zzag() throws IllegalStateException {
        zzp().zzc();
        zzb(zzah());
        String strZzaa = zzx().zzaa();
        Pair<String, Boolean> pairZza = zzb().zza(strZzaa);
        if (!this.zzi.zzg().booleanValue() || ((Boolean) pairZza.second).booleanValue() || TextUtils.isEmpty((CharSequence) pairZza.first)) {
            zzq().zzv().zza("ADID unavailable to retrieve Deferred Deep Link. Skipping");
            return;
        }
        if (!zzah().zzf()) {
            zzq().zzh().zza("Network is not available for Deferred Deep Link request. Skipping");
            return;
        }
        zzkv zzkvVarZzh = zzh();
        zzx();
        URL urlZza = zzkvVarZzh.zza(33025L, strZzaa, (String) pairZza.first, zzb().zzt.zza() - 1);
        zzid zzidVarZzah = zzah();
        zzic zzicVar = new zzic(this) { // from class: com.google.android.gms.measurement.internal.zzfx
            private final zzfu zza;

            {
                this.zza = this;
            }

            @Override // com.google.android.gms.measurement.internal.zzic
            public final void zza(String str, int i, Throwable th, byte[] bArr, Map map) throws IllegalStateException {
                this.zza.zza(str, i, th, bArr, map);
            }
        };
        zzidVarZzah.zzc();
        zzidVarZzah.zzab();
        Preconditions.checkNotNull(urlZza);
        Preconditions.checkNotNull(zzicVar);
        zzidVarZzah.zzp().zzc(new zzif(zzidVarZzah, strZzaa, urlZza, null, null, zzicVar));
    }

    final /* synthetic */ void zza(String str, int i, Throwable th, byte[] bArr, Map map) throws IllegalStateException {
        List<ResolveInfo> listQueryIntentActivities;
        boolean z = true;
        if (!((i == 200 || i == 204 || i == 304) && th == null)) {
            zzq().zzh().zza("Network Request for Deferred Deep Link failed. response, exception", Integer.valueOf(i), th);
            return;
        }
        zzb().zzs.zza(true);
        if (bArr.length == 0) {
            zzq().zzv().zza("Deferred Deep Link response empty.");
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(new String(bArr));
            String strOptString = jSONObject.optString("deeplink", "");
            String strOptString2 = jSONObject.optString("gclid", "");
            double dOptDouble = jSONObject.optDouble("timestamp", 0.0d);
            if (TextUtils.isEmpty(strOptString)) {
                zzq().zzv().zza("Deferred Deep Link is empty.");
                return;
            }
            zzkv zzkvVarZzh = zzh();
            if (TextUtils.isEmpty(strOptString) || (listQueryIntentActivities = zzkvVarZzh.zzm().getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse(strOptString)), 0)) == null || listQueryIntentActivities.isEmpty()) {
                z = false;
            }
            if (!z) {
                zzq().zzh().zza("Deferred Deep Link validation failed. gclid, deep link", strOptString2, strOptString);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("gclid", strOptString2);
            bundle.putString("_cis", "ddp");
            this.zzr.zza("auto", Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, bundle);
            zzkv zzkvVarZzh2 = zzh();
            if (TextUtils.isEmpty(strOptString) || !zzkvVarZzh2.zza(strOptString, dOptDouble)) {
                return;
            }
            zzkvVarZzh2.zzm().sendBroadcast(new Intent("android.google.analytics.action.DEEPLINK_ACTION"));
        } catch (JSONException e) {
            zzq().zze().zza("Failed to parse the Deferred Deep Link response. exception", e);
        }
    }
}
