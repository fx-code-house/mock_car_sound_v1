package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzfm {
    private final zzfp zza;

    public zzfm(zzfp zzfpVar) {
        Preconditions.checkNotNull(zzfpVar);
        this.zza = zzfpVar;
    }

    public static boolean zza(Context context) {
        ActivityInfo receiverInfo;
        Preconditions.checkNotNull(context);
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null && (receiverInfo = packageManager.getReceiverInfo(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementReceiver"), 0)) != null) {
                if (receiverInfo.enabled) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    public final void zza(Context context, Intent intent) throws IllegalStateException {
        zzeq zzeqVarZzq = zzfu.zza(context, null, null).zzq();
        if (intent == null) {
            zzeqVarZzq.zzh().zza("Receiver called with null intent");
            return;
        }
        String action = intent.getAction();
        zzeqVarZzq.zzw().zza("Local receiver got", action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            Intent className = new Intent().setClassName(context, "com.google.android.gms.measurement.AppMeasurementService");
            className.setAction("com.google.android.gms.measurement.UPLOAD");
            zzeqVarZzq.zzw().zza("Starting wakeful intent.");
            this.zza.doStartService(context, className);
            return;
        }
        if ("com.android.vending.INSTALL_REFERRER".equals(action)) {
            zzeqVarZzq.zzh().zza("Install Referrer Broadcasts are deprecated");
        }
    }
}
