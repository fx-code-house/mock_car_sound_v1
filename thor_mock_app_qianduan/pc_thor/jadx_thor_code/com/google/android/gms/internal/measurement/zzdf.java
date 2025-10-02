package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.util.Log;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzdf {
    private static volatile zzdy<Boolean> zza = zzdy.zzc();
    private static final Object zzb = new Object();

    private static boolean zza(Context context) {
        return (context.getPackageManager().getApplicationInfo("com.google.android.gms", 0).flags & 129) != 0;
    }

    public static boolean zza(Context context, Uri uri) {
        ProviderInfo providerInfoResolveContentProvider;
        String authority = uri.getAuthority();
        boolean z = false;
        if (!"com.google.android.gms.phenotype".equals(authority)) {
            Log.e("PhenotypeClientHelper", new StringBuilder(String.valueOf(authority).length() + 91).append(authority).append(" is an unsupported authority. Only com.google.android.gms.phenotype authority is supported.").toString());
            return false;
        }
        if (zza.zza()) {
            return zza.zzb().booleanValue();
        }
        synchronized (zzb) {
            if (zza.zza()) {
                return zza.zzb().booleanValue();
            }
            if (("com.google.android.gms".equals(context.getPackageName()) || ((providerInfoResolveContentProvider = context.getPackageManager().resolveContentProvider("com.google.android.gms.phenotype", 0)) != null && "com.google.android.gms".equals(providerInfoResolveContentProvider.packageName))) && zza(context)) {
                z = true;
            }
            zza = zzdy.zza(Boolean.valueOf(z));
            return zza.zzb().booleanValue();
        }
    }
}
