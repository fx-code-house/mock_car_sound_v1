package com.google.android.gms.phenotype;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class zza {
    private static final ConcurrentHashMap<Uri, zza> zzg = new ConcurrentHashMap<>();
    private static final String[] zzl = {"key", "value"};
    private final Uri uri;
    private final ContentResolver zzh;
    private volatile Map<String, String> zzk;
    private final Object zzj = new Object();
    private final ContentObserver zzi = new zzb(this, null);

    private zza(ContentResolver contentResolver, Uri uri) {
        this.zzh = contentResolver;
        this.uri = uri;
    }

    public static zza zza(ContentResolver contentResolver, Uri uri) {
        ConcurrentHashMap<Uri, zza> concurrentHashMap = zzg;
        zza zzaVar = concurrentHashMap.get(uri);
        if (zzaVar != null) {
            return zzaVar;
        }
        zza zzaVar2 = new zza(contentResolver, uri);
        zza zzaVarPutIfAbsent = concurrentHashMap.putIfAbsent(uri, zzaVar2);
        if (zzaVarPutIfAbsent != null) {
            return zzaVarPutIfAbsent;
        }
        zzaVar2.zzh.registerContentObserver(zzaVar2.uri, false, zzaVar2.zzi);
        return zzaVar2;
    }

    private final Map<String, String> zzc() {
        HashMap map = new HashMap();
        Cursor cursorQuery = this.zzh.query(this.uri, zzl, null, null, null);
        if (cursorQuery != null) {
            while (cursorQuery.moveToNext()) {
                try {
                    map.put(cursorQuery.getString(0), cursorQuery.getString(1));
                } finally {
                    cursorQuery.close();
                }
            }
        }
        return map;
    }

    public final Map<String, String> zza() {
        Map<String, String> mapZzc = PhenotypeFlag.zza("gms:phenotype:phenotype_flag:debug_disable_caching", false) ? zzc() : this.zzk;
        if (mapZzc == null) {
            synchronized (this.zzj) {
                mapZzc = this.zzk;
                if (mapZzc == null) {
                    mapZzc = zzc();
                    this.zzk = mapZzc;
                }
            }
        }
        return mapZzc;
    }

    public final void zzb() {
        synchronized (this.zzj) {
            this.zzk = null;
        }
    }
}
