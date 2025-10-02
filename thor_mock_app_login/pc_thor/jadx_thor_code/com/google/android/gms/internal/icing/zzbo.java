package com.google.android.gms.internal.icing;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.util.Log;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzbo {
    private static volatile zzbx<Boolean> zzcw = zzbx.zzw();
    private static final Object zzcx = new Object();

    private static boolean zzf(Context context) {
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
        if (zzcw.isPresent()) {
            return zzcw.get().booleanValue();
        }
        synchronized (zzcx) {
            if (zzcw.isPresent()) {
                return zzcw.get().booleanValue();
            }
            if (("com.google.android.gms".equals(context.getPackageName()) || ((providerInfoResolveContentProvider = context.getPackageManager().resolveContentProvider("com.google.android.gms.phenotype", 0)) != null && "com.google.android.gms".equals(providerInfoResolveContentProvider.packageName))) && zzf(context)) {
                z = true;
            }
            zzcw = zzbx.zzb(Boolean.valueOf(z));
            return zzcw.get().booleanValue();
        }
    }
}
