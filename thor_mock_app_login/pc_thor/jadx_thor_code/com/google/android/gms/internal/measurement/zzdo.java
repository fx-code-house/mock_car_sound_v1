package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import androidx.collection.ArrayMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzdo implements zzcx {
    private static final Map<String, zzdo> zza = new ArrayMap();
    private final SharedPreferences zzb;
    private final SharedPreferences.OnSharedPreferenceChangeListener zzc;
    private final Object zzd;
    private volatile Map<String, ?> zze;
    private final List<zzcu> zzf;

    static zzdo zza(Context context, String str) {
        zzdo zzdoVar;
        String str2 = null;
        if (zzcr.zza()) {
            str2.startsWith("direct_boot:");
            throw null;
        }
        synchronized (zzdo.class) {
            Map<String, zzdo> map = zza;
            zzdoVar = map.get(null);
            if (zzdoVar == null) {
                zzdoVar = new zzdo(zzb(context, null));
                map.put(null, zzdoVar);
            }
        }
        return zzdoVar;
    }

    private static SharedPreferences zzb(Context context, String str) {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            if (str.startsWith("direct_boot:")) {
                if (zzcr.zza()) {
                    context = context.createDeviceProtectedStorageContext();
                }
                return context.getSharedPreferences(str.substring(12), 0);
            }
            return context.getSharedPreferences(str, 0);
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }

    private zzdo(SharedPreferences sharedPreferences) {
        SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener(this) { // from class: com.google.android.gms.internal.measurement.zzdr
            private final zzdo zza;

            {
                this.zza = this;
            }

            @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
            public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences2, String str) {
                this.zza.zza(sharedPreferences2, str);
            }
        };
        this.zzc = onSharedPreferenceChangeListener;
        this.zzd = new Object();
        this.zzf = new ArrayList();
        this.zzb = sharedPreferences;
        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override // com.google.android.gms.internal.measurement.zzcx
    public final Object zza(String str) {
        Map<String, ?> map = this.zze;
        if (map == null) {
            synchronized (this.zzd) {
                map = this.zze;
                if (map == null) {
                    StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
                    try {
                        Map<String, ?> all = this.zzb.getAll();
                        this.zze = all;
                        StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                        map = all;
                    } catch (Throwable th) {
                        StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                        throw th;
                    }
                }
            }
        }
        if (map != null) {
            return map.get(str);
        }
        return null;
    }

    static synchronized void zza() {
        for (zzdo zzdoVar : zza.values()) {
            zzdoVar.zzb.unregisterOnSharedPreferenceChangeListener(zzdoVar.zzc);
        }
        zza.clear();
    }

    final /* synthetic */ void zza(SharedPreferences sharedPreferences, String str) {
        synchronized (this.zzd) {
            this.zze = null;
            zzdh.zza();
        }
        synchronized (this) {
            Iterator<zzcu> it = this.zzf.iterator();
            while (it.hasNext()) {
                it.next().zza();
            }
        }
    }
}
