package com.google.android.gms.internal.icing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
abstract class zzer {
    private static final zzer zzma;
    private static final zzer zzmb;

    private zzer() {
    }

    abstract void zza(Object obj, long j);

    abstract <L> void zza(Object obj, Object obj2, long j);

    static zzer zzcf() {
        return zzma;
    }

    static zzer zzcg() {
        return zzmb;
    }

    static {
        zzeq zzeqVar = null;
        zzma = new zzet();
        zzmb = new zzes();
    }
}
