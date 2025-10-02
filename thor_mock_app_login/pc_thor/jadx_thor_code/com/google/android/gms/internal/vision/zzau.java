package com.google.android.gms.internal.vision;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import androidx.collection.ArrayMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzau implements zzay {
    private static final Map<Uri, zzau> zzfp = new ArrayMap();
    private static final String[] zzfv = {"key", "value"};
    private final Uri uri;
    private final ContentResolver zzfq;
    private final ContentObserver zzfr;
    private final Object zzfs;
    private volatile Map<String, String> zzft;
    private final List<zzaz> zzfu;

    private zzau(ContentResolver contentResolver, Uri uri) {
        zzaw zzawVar = new zzaw(this, null);
        this.zzfr = zzawVar;
        this.zzfs = new Object();
        this.zzfu = new ArrayList();
        this.zzfq = contentResolver;
        this.uri = uri;
        contentResolver.registerContentObserver(uri, false, zzawVar);
    }

    public static zzau zza(ContentResolver contentResolver, Uri uri) {
        zzau zzauVar;
        synchronized (zzau.class) {
            Map<Uri, zzau> map = zzfp;
            zzauVar = map.get(uri);
            if (zzauVar == null) {
                try {
                    zzau zzauVar2 = new zzau(contentResolver, uri);
                    try {
                        map.put(uri, zzauVar2);
                    } catch (SecurityException unused) {
                    }
                    zzauVar = zzauVar2;
                } catch (SecurityException unused2) {
                }
            }
        }
        return zzauVar;
    }

    private final Map<String, String> zzv() {
        Map<String, String> mapZzx = this.zzft;
        if (mapZzx == null) {
            synchronized (this.zzfs) {
                mapZzx = this.zzft;
                if (mapZzx == null) {
                    mapZzx = zzx();
                    this.zzft = mapZzx;
                }
            }
        }
        return mapZzx != null ? mapZzx : Collections.emptyMap();
    }

    public final void zzw() {
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

    private final Map<String, String> zzx() {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            try {
                return (Map) zzbb.zza(new zzba(this) { // from class: com.google.android.gms.internal.vision.zzax
                    private final zzau zzfy;

                    {
                        this.zzfy = this;
                    }

                    @Override // com.google.android.gms.internal.vision.zzba
                    public final Object zzac() {
                        return this.zzfy.zzz();
                    }
                });
            } catch (SQLiteException | IllegalStateException | SecurityException unused) {
                Log.e("ConfigurationContentLoader", "PhenotypeFlag unable to load ContentProvider, using default values");
                StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                return null;
            }
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }

    static synchronized void zzy() {
        for (zzau zzauVar : zzfp.values()) {
            zzauVar.zzfq.unregisterContentObserver(zzauVar.zzfr);
        }
        zzfp.clear();
    }

    @Override // com.google.android.gms.internal.vision.zzay
    public final /* synthetic */ Object zzb(String str) {
        return zzv().get(str);
    }

    final /* synthetic */ Map zzz() {
        Map map;
        Cursor cursorQuery = this.zzfq.query(this.uri, zzfv, null, null, null);
        if (cursorQuery == null) {
            return Collections.emptyMap();
        }
        try {
            int count = cursorQuery.getCount();
            if (count == 0) {
                return Collections.emptyMap();
            }
            if (count <= 256) {
                map = new ArrayMap(count);
            } else {
                map = new HashMap(count, 1.0f);
            }
            while (cursorQuery.moveToNext()) {
                map.put(cursorQuery.getString(0), cursorQuery.getString(1));
            }
            return map;
        } finally {
            cursorQuery.close();
        }
    }
}
