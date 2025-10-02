package com.google.android.gms.internal.vision;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
abstract class zzku<T, B> {
    zzku() {
    }

    abstract void zza(B b, int i, long j);

    abstract void zza(B b, int i, zzgs zzgsVar);

    abstract void zza(B b, int i, T t);

    abstract void zza(T t, zzlr zzlrVar) throws IOException;

    abstract boolean zza(zzkd zzkdVar);

    abstract int zzaa(T t);

    abstract void zzb(B b, int i, long j);

    abstract void zzc(T t, zzlr zzlrVar) throws IOException;

    abstract void zzd(B b, int i, int i2);

    abstract void zzf(Object obj, T t);

    abstract void zzg(Object obj, B b);

    abstract T zzh(T t, T t2);

    abstract void zzj(Object obj);

    abstract B zzja();

    abstract T zzq(B b);

    abstract int zzu(T t);

    abstract T zzy(Object obj);

    abstract B zzz(Object obj);

    final boolean zza(B b, zzkd zzkdVar) throws IOException {
        int tag = zzkdVar.getTag();
        int i = tag >>> 3;
        int i2 = tag & 7;
        if (i2 == 0) {
            zza((zzku<T, B>) b, i, zzkdVar.zzer());
            return true;
        }
        if (i2 == 1) {
            zzb(b, i, zzkdVar.zzet());
            return true;
        }
        if (i2 == 2) {
            zza((zzku<T, B>) b, i, zzkdVar.zzex());
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                return false;
            }
            if (i2 != 5) {
                throw zzin.zzhm();
            }
            zzd(b, i, zzkdVar.zzeu());
            return true;
        }
        B bZzja = zzja();
        int i3 = 4 | (i << 3);
        while (zzkdVar.zzeo() != Integer.MAX_VALUE && zza((zzku<T, B>) bZzja, zzkdVar)) {
        }
        if (i3 != zzkdVar.getTag()) {
            throw zzin.zzhl();
        }
        zza((zzku<T, B>) b, i, (int) zzq(bZzja));
        return true;
    }
}
