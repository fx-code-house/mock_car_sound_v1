package com.google.android.gms.internal.icing;

import android.content.Context;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public abstract class zzbq<T> {
    private static Context zzcs = null;
    private static boolean zzda = false;
    private static zzcc<zzbx<zzbm>> zzdb;
    private final String name;
    private final zzbu zzdc;
    private final T zzdd;
    private volatile int zzdf;
    private volatile T zzdg;
    private static final Object zzcz = new Object();
    private static final AtomicInteger zzde = new AtomicInteger();

    public static void zzg(Context context) {
        synchronized (zzcz) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                context = applicationContext;
            }
            if (zzcs != context) {
                zzbc.zzp();
                zzbt.zzp();
                zzbh.zzs();
                zzde.incrementAndGet();
                zzcs = context;
                zzdb = zzcb.zza(zzbp.zzcy);
            }
        }
    }

    abstract T zza(Object obj);

    static void zzt() {
        zzde.incrementAndGet();
    }

    private zzbq(zzbu zzbuVar, String str, T t) {
        this.zzdf = -1;
        if (zzbuVar.zzdl == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        }
        this.zzdc = zzbuVar;
        this.name = str;
        this.zzdd = t;
    }

    private final String zzm(String str) {
        if (str != null && str.isEmpty()) {
            return this.name;
        }
        String strValueOf = String.valueOf(str);
        String strValueOf2 = String.valueOf(this.name);
        return strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
    }

    public final String zzu() {
        return zzm(this.zzdc.zzdn);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009a A[Catch: all -> 0x00f3, TryCatch #0 {, blocks: (B:5:0x000b, B:7:0x000f, B:9:0x0013, B:11:0x0021, B:17:0x0033, B:19:0x0039, B:21:0x0045, B:25:0x005e, B:27:0x0068, B:45:0x00b9, B:47:0x00c7, B:49:0x00dd, B:50:0x00e0, B:51:0x00e4, B:38:0x009a, B:40:0x00ae, B:44:0x00b7, B:23:0x0056, B:28:0x006d, B:30:0x0076, B:32:0x0088, B:34:0x0093, B:33:0x008d, B:52:0x00e9, B:53:0x00f0, B:54:0x00f1), top: B:61:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c7 A[Catch: all -> 0x00f3, TryCatch #0 {, blocks: (B:5:0x000b, B:7:0x000f, B:9:0x0013, B:11:0x0021, B:17:0x0033, B:19:0x0039, B:21:0x0045, B:25:0x005e, B:27:0x0068, B:45:0x00b9, B:47:0x00c7, B:49:0x00dd, B:50:0x00e0, B:51:0x00e4, B:38:0x009a, B:40:0x00ae, B:44:0x00b7, B:23:0x0056, B:28:0x006d, B:30:0x0076, B:32:0x0088, B:34:0x0093, B:33:0x008d, B:52:0x00e9, B:53:0x00f0, B:54:0x00f1), top: B:61:0x000b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final T get() {
        /*
            Method dump skipped, instructions count: 249
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzbq.get():java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static zzbq<Boolean> zza(zzbu zzbuVar, String str, boolean z) {
        return new zzbr(zzbuVar, str, Boolean.valueOf(z));
    }

    static final /* synthetic */ zzbx zzv() {
        new zzbl();
        return zzbl.zzd(zzcs);
    }

    /* synthetic */ zzbq(zzbu zzbuVar, String str, Object obj, zzbs zzbsVar) {
        this(zzbuVar, str, obj);
    }
}
