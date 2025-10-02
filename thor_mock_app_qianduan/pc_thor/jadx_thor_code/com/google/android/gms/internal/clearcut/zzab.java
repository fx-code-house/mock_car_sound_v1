package com.google.android.gms.internal.clearcut;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class zzab {
    private static final ConcurrentHashMap<Uri, zzab> zzde = new ConcurrentHashMap<>();
    private static final String[] zzdl = {"key", "value"};
    private final Uri uri;
    private final ContentResolver zzdf;
    private volatile Map<String, String> zzdi;
    private final Object zzdh = new Object();
    private final Object zzdj = new Object();
    private final List<zzad> zzdk = new ArrayList();
    private final ContentObserver zzdg = new zzac(this, null);

    private zzab(ContentResolver contentResolver, Uri uri) {
        this.zzdf = contentResolver;
        this.uri = uri;
    }

    public static zzab zza(ContentResolver contentResolver, Uri uri) {
        ConcurrentHashMap<Uri, zzab> concurrentHashMap = zzde;
        zzab zzabVar = concurrentHashMap.get(uri);
        if (zzabVar != null) {
            return zzabVar;
        }
        zzab zzabVar2 = new zzab(contentResolver, uri);
        zzab zzabVarPutIfAbsent = concurrentHashMap.putIfAbsent(uri, zzabVar2);
        if (zzabVarPutIfAbsent != null) {
            return zzabVarPutIfAbsent;
        }
        zzabVar2.zzdf.registerContentObserver(zzabVar2.uri, false, zzabVar2.zzdg);
        return zzabVar2;
    }

    private final Map<String, String> zzi() {
        try {
            HashMap map = new HashMap();
            Cursor cursorQuery = this.zzdf.query(this.uri, zzdl, null, null, null);
            if (cursorQuery != null) {
                while (cursorQuery.moveToNext()) {
                    try {
                        map.put(cursorQuery.getString(0), cursorQuery.getString(1));
                    } catch (Throwable th) {
                        cursorQuery.close();
                        throw th;
                    }
                }
                cursorQuery.close();
            }
            return map;
        } catch (SQLiteException | SecurityException unused) {
            Log.e("ConfigurationContentLoader", "PhenotypeFlag unable to load ContentProvider, using default values");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzj() {
        synchronized (this.zzdj) {
            Iterator<zzad> it = this.zzdk.iterator();
            while (it.hasNext()) {
                it.next().zzk();
            }
        }
    }

    public final Map<String, String> zzg() {
        Map<String, String> mapZzi = zzae.zza("gms:phenotype:phenotype_flag:debug_disable_caching", false) ? zzi() : this.zzdi;
        if (mapZzi == null) {
            synchronized (this.zzdh) {
                mapZzi = this.zzdi;
                if (mapZzi == null) {
                    mapZzi = zzi();
                    this.zzdi = mapZzi;
                }
            }
        }
        return mapZzi != null ? mapZzi : Collections.emptyMap();
    }

    public final void zzh() {
        synchronized (this.zzdh) {
            this.zzdi = null;
        }
    }
}
