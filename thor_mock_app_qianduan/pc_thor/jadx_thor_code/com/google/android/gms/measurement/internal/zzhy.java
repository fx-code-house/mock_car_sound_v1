package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.firebase.messaging.Constants;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzhy implements Application.ActivityLifecycleCallbacks {
    private final /* synthetic */ zzhb zza;

    private zzhy(zzhb zzhbVar) {
        this.zza = zzhbVar;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        try {
            this.zza.zzq().zzw().zza("onActivityCreated");
            Intent intent = activity.getIntent();
            if (intent == null) {
                return;
            }
            Uri data = intent.getData();
            if (data != null && data.isHierarchical()) {
                this.zza.zzo();
                this.zza.zzp().zza(new zzib(this, bundle == null, data, zzkv.zza(intent) ? "gs" : "auto", data.getQueryParameter("referrer")));
            }
        } catch (Exception e) {
            this.zza.zzq().zze().zza("Throwable caught in onActivityCreated", e);
        } finally {
            this.zza.zzh().zza(activity, bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(boolean z, Uri uri, String str, String str2) throws IllegalStateException {
        Bundle bundleZza;
        Bundle bundleZza2;
        this.zza.zzc();
        try {
            if (this.zza.zzs().zza(zzas.zzbd) || this.zza.zzs().zza(zzas.zzbf) || this.zza.zzs().zza(zzas.zzbe)) {
                zzkv zzkvVarZzo = this.zza.zzo();
                if (!TextUtils.isEmpty(str2)) {
                    if (!str2.contains("gclid") && !str2.contains("utm_campaign") && !str2.contains("utm_source") && !str2.contains("utm_medium")) {
                        zzkvVarZzo.zzq().zzv().zza("Activity created with data 'referrer' without required params");
                    } else {
                        String strValueOf = String.valueOf(str2);
                        bundleZza = zzkvVarZzo.zza(Uri.parse(strValueOf.length() != 0 ? "https://google.com/search?".concat(strValueOf) : new String("https://google.com/search?")));
                        if (bundleZza != null) {
                            bundleZza.putString("_cis", "referrer");
                        }
                    }
                }
                bundleZza = null;
            } else {
                bundleZza = null;
            }
            boolean z2 = false;
            if (z) {
                bundleZza2 = this.zza.zzo().zza(uri);
                if (bundleZza2 != null) {
                    bundleZza2.putString("_cis", "intent");
                    if (this.zza.zzs().zza(zzas.zzbd) && !bundleZza2.containsKey("gclid") && bundleZza != null && bundleZza.containsKey("gclid")) {
                        bundleZza2.putString("_cer", String.format("gclid=%s", bundleZza.getString("gclid")));
                    }
                    this.zza.zza(str, Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, bundleZza2);
                    if (this.zza.zzs().zza(zzas.zzcc)) {
                        this.zza.zzb.zza(str, bundleZza2);
                    }
                }
            } else {
                bundleZza2 = null;
            }
            if (this.zza.zzs().zza(zzas.zzbf) && !this.zza.zzs().zza(zzas.zzbe) && bundleZza != null && bundleZza.containsKey("gclid") && (bundleZza2 == null || !bundleZza2.containsKey("gclid"))) {
                this.zza.zza("auto", "_lgclid", (Object) bundleZza.getString("gclid"), true);
            }
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            this.zza.zzq().zzv().zza("Activity created with referrer", str2);
            if (this.zza.zzs().zza(zzas.zzbe)) {
                if (bundleZza != null) {
                    this.zza.zza(str, Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, bundleZza);
                    if (this.zza.zzs().zza(zzas.zzcc)) {
                        this.zza.zzb.zza(str, bundleZza);
                    }
                } else {
                    this.zza.zzq().zzv().zza("Referrer does not contain valid parameters", str2);
                }
                this.zza.zza("auto", "_ldl", (Object) null, true);
                return;
            }
            if (str2.contains("gclid") && (str2.contains("utm_campaign") || str2.contains("utm_source") || str2.contains("utm_medium") || str2.contains("utm_term") || str2.contains("utm_content"))) {
                z2 = true;
            }
            if (!z2) {
                this.zza.zzq().zzv().zza("Activity created with data 'referrer' without required params");
            } else {
                if (TextUtils.isEmpty(str2)) {
                    return;
                }
                this.zza.zza("auto", "_ldl", (Object) str2, true);
            }
        } catch (Exception e) {
            this.zza.zzq().zze().zza("Throwable caught in handleReferrerForOnActivityCreated", e);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        this.zza.zzh().zzc(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) throws IllegalStateException {
        this.zza.zzh().zzb(activity);
        zzjx zzjxVarZzj = this.zza.zzj();
        zzjxVarZzj.zzp().zza(new zzjz(zzjxVarZzj, zzjxVarZzj.zzl().elapsedRealtime()));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) throws IllegalStateException {
        zzjx zzjxVarZzj = this.zza.zzj();
        zzjxVarZzj.zzp().zza(new zzjw(zzjxVarZzj, zzjxVarZzj.zzl().elapsedRealtime()));
        this.zza.zzh().zza(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zza.zzh().zzb(activity, bundle);
    }

    /* synthetic */ zzhy(zzhb zzhbVar, zzhc zzhcVar) {
        this(zzhbVar);
    }
}
