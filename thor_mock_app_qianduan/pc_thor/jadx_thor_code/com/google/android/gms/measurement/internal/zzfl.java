package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.wrappers.PackageManagerWrapper;
import com.google.android.gms.common.wrappers.Wrappers;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzfl {
    final zzfu zza;

    zzfl(zzfu zzfuVar) {
        this.zza = zzfuVar;
    }

    protected final void zza(String str) {
        if (str == null || str.isEmpty()) {
            this.zza.zzq().zzi().zza("Install Referrer Reporter was called with invalid app package name");
            return;
        }
        this.zza.zzp().zzc();
        if (!zza()) {
            this.zza.zzq().zzu().zza("Install Referrer Reporter is not available");
            return;
        }
        zzfk zzfkVar = new zzfk(this, str);
        this.zza.zzp().zzc();
        Intent intent = new Intent("com.google.android.finsky.BIND_GET_INSTALL_REFERRER_SERVICE");
        intent.setComponent(new ComponentName("com.android.vending", "com.google.android.finsky.externalreferrer.GetInstallReferrerService"));
        PackageManager packageManager = this.zza.zzm().getPackageManager();
        if (packageManager == null) {
            this.zza.zzq().zzi().zza("Failed to obtain Package Manager to verify binding conditions for Install Referrer");
            return;
        }
        List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 0);
        if (listQueryIntentServices != null && !listQueryIntentServices.isEmpty()) {
            ResolveInfo resolveInfo = listQueryIntentServices.get(0);
            if (resolveInfo.serviceInfo != null) {
                String str2 = resolveInfo.serviceInfo.packageName;
                if (resolveInfo.serviceInfo.name != null && "com.android.vending".equals(str2) && zza()) {
                    try {
                        this.zza.zzq().zzw().zza("Install Referrer Service is", ConnectionTracker.getInstance().bindService(this.zza.zzm(), new Intent(intent), zzfkVar, 1) ? "available" : "not available");
                        return;
                    } catch (Exception e) {
                        this.zza.zzq().zze().zza("Exception occurred while binding to Install Referrer Service", e.getMessage());
                        return;
                    }
                }
                this.zza.zzq().zzh().zza("Play Store version 8.3.73 or higher required for Install Referrer");
                return;
            }
            return;
        }
        this.zza.zzq().zzu().zza("Play Service for fetching Install Referrer is unavailable on device");
    }

    private final boolean zza() throws IllegalStateException {
        try {
            PackageManagerWrapper packageManagerWrapperPackageManager = Wrappers.packageManager(this.zza.zzm());
            if (packageManagerWrapperPackageManager != null) {
                return packageManagerWrapperPackageManager.getPackageInfo("com.android.vending", 128).versionCode >= 80837300;
            }
            this.zza.zzq().zzw().zza("Failed to get PackageManager for Install Referrer Play Store compatibility check");
            return false;
        } catch (Exception e) {
            this.zza.zzq().zzw().zza("Failed to retrieve Play Store version for Install Referrer", e);
            return false;
        }
    }

    final Bundle zza(String str, com.google.android.gms.internal.measurement.zzd zzdVar) throws IllegalStateException {
        this.zza.zzp().zzc();
        if (zzdVar == null) {
            this.zza.zzq().zzh().zza("Attempting to use Install Referrer Service while it is not initialized");
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString("package_name", str);
        try {
            Bundle bundleZza = zzdVar.zza(bundle);
            if (bundleZza != null) {
                return bundleZza;
            }
            this.zza.zzq().zze().zza("Install Referrer Service returned a null response");
            return null;
        } catch (Exception e) {
            this.zza.zzq().zze().zza("Exception occurred while retrieving the Install Referrer", e.getMessage());
            return null;
        }
    }
}
