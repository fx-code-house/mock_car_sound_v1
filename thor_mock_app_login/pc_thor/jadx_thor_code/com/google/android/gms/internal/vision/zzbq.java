package com.google.android.gms.internal.vision;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import androidx.collection.ArrayMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzbq implements zzay {
    private static final Map<String, zzbq> zzhc = new ArrayMap();
    private final Object zzfs;
    private volatile Map<String, ?> zzft;
    private final List<zzaz> zzfu;
    private final SharedPreferences zzhd;
    private final SharedPreferences.OnSharedPreferenceChangeListener zzhe;

    static zzbq zzb(Context context, String str) {
        zzbq zzbqVar;
        if (!((!zzas.zzu() || str.startsWith("direct_boot:")) ? true : zzas.isUserUnlocked(context))) {
            return null;
        }
        synchronized (zzbq.class) {
            Map<String, zzbq> map = zzhc;
            zzbqVar = map.get(str);
            if (zzbqVar == null) {
                zzbqVar = new zzbq(zzc(context, str));
                map.put(str, zzbqVar);
            }
        }
        return zzbqVar;
    }

    private static SharedPreferences zzc(Context context, String str) {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            if (str.startsWith("direct_boot:")) {
                if (zzas.zzu()) {
                    context = context.createDeviceProtectedStorageContext();
                }
                return context.getSharedPreferences(str.substring(12), 0);
            }
            return context.getSharedPreferences(str, 0);
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }

    private zzbq(SharedPreferences sharedPreferences) {
        SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener(this) { // from class: com.google.android.gms.internal.vision.zzbt
            private final zzbq zzhg;

            {
                this.zzhg = this;
            }

            @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
            public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences2, String str) {
                this.zzhg.zza(sharedPreferences2, str);
            }
        };
        this.zzhe = onSharedPreferenceChangeListener;
        this.zzfs = new Object();
        this.zzfu = new ArrayList();
        this.zzhd = sharedPreferences;
        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override // com.google.android.gms.internal.vision.zzay
    public final Object zzb(String str) {
        Map<String, ?> map = this.zzft;
        if (map == null) {
            synchronized (this.zzfs) {
                map = this.zzft;
                if (map == null) {
                    StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
                    try {
                        Map<String, ?> all = this.zzhd.getAll();
                        this.zzft = all;
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

    static synchronized void zzy() {
        for (zzbq zzbqVar : zzhc.values()) {
            zzbqVar.zzhd.unregisterOnSharedPreferenceChangeListener(zzbqVar.zzhe);
        }
        zzhc.clear();
    }

    final /* synthetic */ void zza(SharedPreferences sharedPreferences, String str) {
        synchronized (this.zzfs) {
            this.zzft = null;
            zzbi.zzaf();
        }
        synchronized (this) {
            Iterator<zzaz> it = this.zzfu.iterator();
            while (it.hasNext()) {
                it.next().zzad();
            }
        }
    }
}
