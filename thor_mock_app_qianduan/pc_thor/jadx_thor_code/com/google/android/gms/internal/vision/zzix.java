package com.google.android.gms.internal.vision;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
abstract class zzix {
    private static final zzix zzaaa;
    private static final zzix zzaab;

    private zzix() {
    }

    abstract <L> List<L> zza(Object obj, long j);

    abstract <L> void zza(Object obj, Object obj2, long j);

    abstract void zzb(Object obj, long j);

    static zzix zzhu() {
        return zzaaa;
    }

    static zzix zzhv() {
        return zzaab;
    }

    static {
        zziw zziwVar = null;
        zzaaa = new zziz();
        zzaab = new zziy();
    }
}
