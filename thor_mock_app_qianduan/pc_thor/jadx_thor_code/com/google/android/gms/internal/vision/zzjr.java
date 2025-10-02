package com.google.android.gms.internal.vision;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzjr<T> implements zzkc<T> {
    private static final int[] zzaao = new int[0];
    private static final Unsafe zzaap = zzla.zzjg();
    private final int[] zzaaq;
    private final Object[] zzaar;
    private final int zzaas;
    private final int zzaat;
    private final zzjn zzaau;
    private final boolean zzaav;
    private final boolean zzaaw;
    private final boolean zzaax;
    private final boolean zzaay;
    private final int[] zzaaz;
    private final int zzaba;
    private final int zzabb;
    private final zzjv zzabc;
    private final zzix zzabd;
    private final zzku<?, ?> zzabe;
    private final zzhq<?> zzabf;
    private final zzjg zzabg;

    private zzjr(int[] iArr, Object[] objArr, int i, int i2, zzjn zzjnVar, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzjv zzjvVar, zzix zzixVar, zzku<?, ?> zzkuVar, zzhq<?> zzhqVar, zzjg zzjgVar) {
        this.zzaaq = iArr;
        this.zzaar = objArr;
        this.zzaas = i;
        this.zzaat = i2;
        this.zzaaw = zzjnVar instanceof zzid;
        this.zzaax = z;
        this.zzaav = zzhqVar != null && zzhqVar.zze(zzjnVar);
        this.zzaay = false;
        this.zzaaz = iArr2;
        this.zzaba = i3;
        this.zzabb = i4;
        this.zzabc = zzjvVar;
        this.zzabd = zzixVar;
        this.zzabe = zzkuVar;
        this.zzabf = zzhqVar;
        this.zzaau = zzjnVar;
        this.zzabg = zzjgVar;
    }

    private static boolean zzbz(int i) {
        return (i & 536870912) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:163:0x0338  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0388  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static <T> com.google.android.gms.internal.vision.zzjr<T> zza(java.lang.Class<T> r33, com.google.android.gms.internal.vision.zzjl r34, com.google.android.gms.internal.vision.zzjv r35, com.google.android.gms.internal.vision.zzix r36, com.google.android.gms.internal.vision.zzku<?, ?> r37, com.google.android.gms.internal.vision.zzhq<?> r38, com.google.android.gms.internal.vision.zzjg r39) {
        /*
            Method dump skipped, instructions count: 1045
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzjr.zza(java.lang.Class, com.google.android.gms.internal.vision.zzjl, com.google.android.gms.internal.vision.zzjv, com.google.android.gms.internal.vision.zzix, com.google.android.gms.internal.vision.zzku, com.google.android.gms.internal.vision.zzhq, com.google.android.gms.internal.vision.zzjg):com.google.android.gms.internal.vision.zzjr");
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String string = Arrays.toString(declaredFields);
            throw new RuntimeException(new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name).length() + String.valueOf(string).length()).append("Field ").append(str).append(" for ").append(name).append(" not found. Known fields are ").append(string).toString());
        }
    }

    @Override // com.google.android.gms.internal.vision.zzkc
    public final T newInstance() {
        return (T) this.zzabc.newInstance(this.zzaau);
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x01c1  */
    @Override // com.google.android.gms.internal.vision.zzkc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean equals(T r10, T r11) {
        /*
            Method dump skipped, instructions count: 640
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzjr.equals(java.lang.Object, java.lang.Object):boolean");
    }

    @Override // com.google.android.gms.internal.vision.zzkc
    public final int hashCode(T t) {
        int i;
        int iZzab;
        int length = this.zzaaq.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int iZzbx = zzbx(i3);
            int i4 = this.zzaaq[i3];
            long j = 1048575 & iZzbx;
            int iHashCode = 37;
            switch ((iZzbx & 267386880) >>> 20) {
                case 0:
                    i = i2 * 53;
                    iZzab = zzie.zzab(Double.doubleToLongBits(zzla.zzo(t, j)));
                    i2 = i + iZzab;
                    break;
                case 1:
                    i = i2 * 53;
                    iZzab = Float.floatToIntBits(zzla.zzn(t, j));
                    i2 = i + iZzab;
                    break;
                case 2:
                    i = i2 * 53;
                    iZzab = zzie.zzab(zzla.zzl(t, j));
                    i2 = i + iZzab;
                    break;
                case 3:
                    i = i2 * 53;
                    iZzab = zzie.zzab(zzla.zzl(t, j));
                    i2 = i + iZzab;
                    break;
                case 4:
                    i = i2 * 53;
                    iZzab = zzla.zzk(t, j);
                    i2 = i + iZzab;
                    break;
                case 5:
                    i = i2 * 53;
                    iZzab = zzie.zzab(zzla.zzl(t, j));
                    i2 = i + iZzab;
                    break;
                case 6:
                    i = i2 * 53;
                    iZzab = zzla.zzk(t, j);
                    i2 = i + iZzab;
                    break;
                case 7:
                    i = i2 * 53;
                    iZzab = zzie.zzm(zzla.zzm(t, j));
                    i2 = i + iZzab;
                    break;
                case 8:
                    i = i2 * 53;
                    iZzab = ((String) zzla.zzp(t, j)).hashCode();
                    i2 = i + iZzab;
                    break;
                case 9:
                    Object objZzp = zzla.zzp(t, j);
                    if (objZzp != null) {
                        iHashCode = objZzp.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iZzab = zzla.zzp(t, j).hashCode();
                    i2 = i + iZzab;
                    break;
                case 11:
                    i = i2 * 53;
                    iZzab = zzla.zzk(t, j);
                    i2 = i + iZzab;
                    break;
                case 12:
                    i = i2 * 53;
                    iZzab = zzla.zzk(t, j);
                    i2 = i + iZzab;
                    break;
                case 13:
                    i = i2 * 53;
                    iZzab = zzla.zzk(t, j);
                    i2 = i + iZzab;
                    break;
                case 14:
                    i = i2 * 53;
                    iZzab = zzie.zzab(zzla.zzl(t, j));
                    i2 = i + iZzab;
                    break;
                case 15:
                    i = i2 * 53;
                    iZzab = zzla.zzk(t, j);
                    i2 = i + iZzab;
                    break;
                case 16:
                    i = i2 * 53;
                    iZzab = zzie.zzab(zzla.zzl(t, j));
                    i2 = i + iZzab;
                    break;
                case 17:
                    Object objZzp2 = zzla.zzp(t, j);
                    if (objZzp2 != null) {
                        iHashCode = objZzp2.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = i2 * 53;
                    iZzab = zzla.zzp(t, j).hashCode();
                    i2 = i + iZzab;
                    break;
                case 50:
                    i = i2 * 53;
                    iZzab = zzla.zzp(t, j).hashCode();
                    i2 = i + iZzab;
                    break;
                case 51:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzie.zzab(Double.doubleToLongBits(zzf(t, j)));
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = Float.floatToIntBits(zzg(t, j));
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzie.zzab(zzi(t, j));
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzie.zzab(zzi(t, j));
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzh(t, j);
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzie.zzab(zzi(t, j));
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzh(t, j);
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzie.zzm(zzj(t, j));
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = ((String) zzla.zzp(t, j)).hashCode();
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzla.zzp(t, j).hashCode();
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzla.zzp(t, j).hashCode();
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzh(t, j);
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzh(t, j);
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzh(t, j);
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzie.zzab(zzi(t, j));
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzh(t, j);
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzie.zzab(zzi(t, j));
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzb((zzjr<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzab = zzla.zzp(t, j).hashCode();
                        i2 = i + iZzab;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzabe.zzy(t).hashCode();
        return this.zzaav ? (iHashCode2 * 53) + this.zzabf.zzh(t).hashCode() : iHashCode2;
    }

    @Override // com.google.android.gms.internal.vision.zzkc
    public final void zzd(T t, T t2) {
        t2.getClass();
        for (int i = 0; i < this.zzaaq.length; i += 3) {
            int iZzbx = zzbx(i);
            long j = 1048575 & iZzbx;
            int i2 = this.zzaaq[i];
            switch ((iZzbx & 267386880) >>> 20) {
                case 0:
                    if (zzc(t2, i)) {
                        zzla.zza(t, j, zzla.zzo(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzc(t2, i)) {
                        zzla.zza((Object) t, j, zzla.zzn(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzc(t2, i)) {
                        zzla.zza((Object) t, j, zzla.zzl(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzc(t2, i)) {
                        zzla.zza((Object) t, j, zzla.zzl(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzc(t2, i)) {
                        zzla.zzb(t, j, zzla.zzk(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzc(t2, i)) {
                        zzla.zza((Object) t, j, zzla.zzl(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzc(t2, i)) {
                        zzla.zzb(t, j, zzla.zzk(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzc(t2, i)) {
                        zzla.zza(t, j, zzla.zzm(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzc(t2, i)) {
                        zzla.zza(t, j, zzla.zzp(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zza(t, t2, i);
                    break;
                case 10:
                    if (zzc(t2, i)) {
                        zzla.zza(t, j, zzla.zzp(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzc(t2, i)) {
                        zzla.zzb(t, j, zzla.zzk(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzc(t2, i)) {
                        zzla.zzb(t, j, zzla.zzk(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzc(t2, i)) {
                        zzla.zzb(t, j, zzla.zzk(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzc(t2, i)) {
                        zzla.zza((Object) t, j, zzla.zzl(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzc(t2, i)) {
                        zzla.zzb(t, j, zzla.zzk(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzc(t2, i)) {
                        zzla.zza((Object) t, j, zzla.zzl(t2, j));
                        zzd((zzjr<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zza(t, t2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.zzabd.zza(t, t2, j);
                    break;
                case 50:
                    zzke.zza(this.zzabg, t, t2, j);
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zzb((zzjr<T>) t2, i2, i)) {
                        zzla.zza(t, j, zzla.zzp(t2, j));
                        zzc((zzjr<T>) t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzb(t, t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzb((zzjr<T>) t2, i2, i)) {
                        zzla.zza(t, j, zzla.zzp(t2, j));
                        zzc((zzjr<T>) t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzb(t, t2, i);
                    break;
            }
        }
        zzke.zza(this.zzabe, t, t2);
        if (this.zzaav) {
            zzke.zza(this.zzabf, t, t2);
        }
    }

    private final void zza(T t, T t2, int i) {
        long jZzbx = zzbx(i) & 1048575;
        if (zzc(t2, i)) {
            Object objZzp = zzla.zzp(t, jZzbx);
            Object objZzp2 = zzla.zzp(t2, jZzbx);
            if (objZzp != null && objZzp2 != null) {
                zzla.zza(t, jZzbx, zzie.zzb(objZzp, objZzp2));
                zzd((zzjr<T>) t, i);
            } else if (objZzp2 != null) {
                zzla.zza(t, jZzbx, objZzp2);
                zzd((zzjr<T>) t, i);
            }
        }
    }

    private final void zzb(T t, T t2, int i) {
        int iZzbx = zzbx(i);
        int i2 = this.zzaaq[i];
        long j = iZzbx & 1048575;
        if (zzb((zzjr<T>) t2, i2, i)) {
            Object objZzp = zzla.zzp(t, j);
            Object objZzp2 = zzla.zzp(t2, j);
            if (objZzp != null && objZzp2 != null) {
                zzla.zza(t, j, zzie.zzb(objZzp, objZzp2));
                zzc((zzjr<T>) t, i2, i);
            } else if (objZzp2 != null) {
                zzla.zza(t, j, objZzp2);
                zzc((zzjr<T>) t, i2, i);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:328:0x0809 A[PHI: r5
      0x0809: PHI (r5v4 int) = 
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v16 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v1 int)
      (r5v17 int)
      (r5v1 int)
     binds: [B:204:0x04b2, B:366:0x08ad, B:360:0x0891, B:357:0x087f, B:354:0x0870, B:351:0x0863, B:348:0x0856, B:344:0x084b, B:341:0x0840, B:338:0x0833, B:335:0x0826, B:332:0x0813, B:305:0x0722, B:302:0x070d, B:299:0x06f8, B:296:0x06e3, B:293:0x06ce, B:290:0x06b9, B:287:0x06a3, B:284:0x068d, B:281:0x0677, B:278:0x0661, B:275:0x064b, B:272:0x0635, B:269:0x061f, B:266:0x0609, B:261:0x05d5, B:258:0x05c8, B:255:0x05b8, B:252:0x05a8, B:249:0x0598, B:246:0x058a, B:243:0x057d, B:240:0x0570, B:234:0x0552, B:231:0x053e, B:228:0x052c, B:225:0x051c, B:222:0x050c, B:346:0x0852, B:219:0x04ff, B:216:0x04f1, B:213:0x04e1, B:210:0x04d1, B:327:0x0808, B:207:0x04bb] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.gms.internal.vision.zzkc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int zzu(T r19) {
        /*
            Method dump skipped, instructions count: 2726
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzjr.zzu(java.lang.Object):int");
    }

    private static <UT, UB> int zza(zzku<UT, UB> zzkuVar, T t) {
        return zzkuVar.zzu(zzkuVar.zzy(t));
    }

    private static List<?> zze(Object obj, long j) {
        return (List) zzla.zzp(obj, j);
    }

    /* JADX WARN: Removed duplicated region for block: B:178:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0032  */
    @Override // com.google.android.gms.internal.vision.zzkc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r14, com.google.android.gms.internal.vision.zzlr r15) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 2916
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzjr.zza(java.lang.Object, com.google.android.gms.internal.vision.zzlr):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void zzb(T r18, com.google.android.gms.internal.vision.zzlr r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1336
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzjr.zzb(java.lang.Object, com.google.android.gms.internal.vision.zzlr):void");
    }

    private final <K, V> void zza(zzlr zzlrVar, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzlrVar.zza(i, this.zzabg.zzs(zzbv(i2)), this.zzabg.zzo(obj));
        }
    }

    private static <UT, UB> void zza(zzku<UT, UB> zzkuVar, T t, zzlr zzlrVar) throws IOException {
        zzkuVar.zza((zzku<UT, UB>) zzkuVar.zzy(t), zzlrVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:164:0x05cc A[LOOP:5: B:162:0x05c8->B:164:0x05cc, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:166:0x05d9  */
    @Override // com.google.android.gms.internal.vision.zzkc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r13, com.google.android.gms.internal.vision.zzkd r14, com.google.android.gms.internal.vision.zzho r15) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1644
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzjr.zza(java.lang.Object, com.google.android.gms.internal.vision.zzkd, com.google.android.gms.internal.vision.zzho):void");
    }

    private static zzkx zzv(Object obj) {
        zzid zzidVar = (zzid) obj;
        zzkx zzkxVar = zzidVar.zzxz;
        if (zzkxVar != zzkx.zzjb()) {
            return zzkxVar;
        }
        zzkx zzkxVarZzjc = zzkx.zzjc();
        zzidVar.zzxz = zzkxVarZzjc;
        return zzkxVarZzjc;
    }

    private static int zza(byte[] bArr, int i, int i2, zzll zzllVar, Class<?> cls, zzgm zzgmVar) throws IOException {
        switch (zzjq.zztn[zzllVar.ordinal()]) {
            case 1:
                int iZzb = zzgk.zzb(bArr, i, zzgmVar);
                zzgmVar.zztm = Boolean.valueOf(zzgmVar.zztl != 0);
                return iZzb;
            case 2:
                return zzgk.zze(bArr, i, zzgmVar);
            case 3:
                zzgmVar.zztm = Double.valueOf(zzgk.zzc(bArr, i));
                return i + 8;
            case 4:
            case 5:
                zzgmVar.zztm = Integer.valueOf(zzgk.zza(bArr, i));
                return i + 4;
            case 6:
            case 7:
                zzgmVar.zztm = Long.valueOf(zzgk.zzb(bArr, i));
                return i + 8;
            case 8:
                zzgmVar.zztm = Float.valueOf(zzgk.zzd(bArr, i));
                return i + 4;
            case 9:
            case 10:
            case 11:
                int iZza = zzgk.zza(bArr, i, zzgmVar);
                zzgmVar.zztm = Integer.valueOf(zzgmVar.zztk);
                return iZza;
            case 12:
            case 13:
                int iZzb2 = zzgk.zzb(bArr, i, zzgmVar);
                zzgmVar.zztm = Long.valueOf(zzgmVar.zztl);
                return iZzb2;
            case 14:
                return zzgk.zza(zzjy.zzij().zzf(cls), bArr, i, i2, zzgmVar);
            case 15:
                int iZza2 = zzgk.zza(bArr, i, zzgmVar);
                zzgmVar.zztm = Integer.valueOf(zzhe.zzbb(zzgmVar.zztk));
                return iZza2;
            case 16:
                int iZzb3 = zzgk.zzb(bArr, i, zzgmVar);
                zzgmVar.zztm = Long.valueOf(zzhe.zzr(zzgmVar.zztl));
                return iZzb3;
            case 17:
                return zzgk.zzd(bArr, i, zzgmVar);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzgm zzgmVar) throws IOException {
        int iZza;
        int iZza2 = i;
        Unsafe unsafe = zzaap;
        zzik zzikVarZzan = (zzik) unsafe.getObject(t, j2);
        if (!zzikVarZzan.zzei()) {
            int size = zzikVarZzan.size();
            zzikVarZzan = zzikVarZzan.zzan(size == 0 ? 10 : size << 1);
            unsafe.putObject(t, j2, zzikVarZzan);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    zzhm zzhmVar = (zzhm) zzikVarZzan;
                    int iZza3 = zzgk.zza(bArr, iZza2, zzgmVar);
                    int i8 = zzgmVar.zztk + iZza3;
                    while (iZza3 < i8) {
                        zzhmVar.zzc(zzgk.zzc(bArr, iZza3));
                        iZza3 += 8;
                    }
                    if (iZza3 == i8) {
                        return iZza3;
                    }
                    throw zzin.zzhh();
                }
                if (i5 == 1) {
                    zzhm zzhmVar2 = (zzhm) zzikVarZzan;
                    zzhmVar2.zzc(zzgk.zzc(bArr, i));
                    while (true) {
                        int i9 = iZza2 + 8;
                        if (i9 >= i2) {
                            return i9;
                        }
                        iZza2 = zzgk.zza(bArr, i9, zzgmVar);
                        if (i3 != zzgmVar.zztk) {
                            return i9;
                        }
                        zzhmVar2.zzc(zzgk.zzc(bArr, iZza2));
                    }
                }
                return iZza2;
            case 19:
            case 36:
                if (i5 == 2) {
                    zzhz zzhzVar = (zzhz) zzikVarZzan;
                    int iZza4 = zzgk.zza(bArr, iZza2, zzgmVar);
                    int i10 = zzgmVar.zztk + iZza4;
                    while (iZza4 < i10) {
                        zzhzVar.zzu(zzgk.zzd(bArr, iZza4));
                        iZza4 += 4;
                    }
                    if (iZza4 == i10) {
                        return iZza4;
                    }
                    throw zzin.zzhh();
                }
                if (i5 == 5) {
                    zzhz zzhzVar2 = (zzhz) zzikVarZzan;
                    zzhzVar2.zzu(zzgk.zzd(bArr, i));
                    while (true) {
                        int i11 = iZza2 + 4;
                        if (i11 >= i2) {
                            return i11;
                        }
                        iZza2 = zzgk.zza(bArr, i11, zzgmVar);
                        if (i3 != zzgmVar.zztk) {
                            return i11;
                        }
                        zzhzVar2.zzu(zzgk.zzd(bArr, iZza2));
                    }
                }
                return iZza2;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    zzjb zzjbVar = (zzjb) zzikVarZzan;
                    int iZza5 = zzgk.zza(bArr, iZza2, zzgmVar);
                    int i12 = zzgmVar.zztk + iZza5;
                    while (iZza5 < i12) {
                        iZza5 = zzgk.zzb(bArr, iZza5, zzgmVar);
                        zzjbVar.zzac(zzgmVar.zztl);
                    }
                    if (iZza5 == i12) {
                        return iZza5;
                    }
                    throw zzin.zzhh();
                }
                if (i5 == 0) {
                    zzjb zzjbVar2 = (zzjb) zzikVarZzan;
                    int iZzb = zzgk.zzb(bArr, iZza2, zzgmVar);
                    zzjbVar2.zzac(zzgmVar.zztl);
                    while (iZzb < i2) {
                        int iZza6 = zzgk.zza(bArr, iZzb, zzgmVar);
                        if (i3 != zzgmVar.zztk) {
                            return iZzb;
                        }
                        iZzb = zzgk.zzb(bArr, iZza6, zzgmVar);
                        zzjbVar2.zzac(zzgmVar.zztl);
                    }
                    return iZzb;
                }
                return iZza2;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return zzgk.zza(bArr, iZza2, (zzik<?>) zzikVarZzan, zzgmVar);
                }
                if (i5 == 0) {
                    return zzgk.zza(i3, bArr, i, i2, (zzik<?>) zzikVarZzan, zzgmVar);
                }
                return iZza2;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    zzjb zzjbVar3 = (zzjb) zzikVarZzan;
                    int iZza7 = zzgk.zza(bArr, iZza2, zzgmVar);
                    int i13 = zzgmVar.zztk + iZza7;
                    while (iZza7 < i13) {
                        zzjbVar3.zzac(zzgk.zzb(bArr, iZza7));
                        iZza7 += 8;
                    }
                    if (iZza7 == i13) {
                        return iZza7;
                    }
                    throw zzin.zzhh();
                }
                if (i5 == 1) {
                    zzjb zzjbVar4 = (zzjb) zzikVarZzan;
                    zzjbVar4.zzac(zzgk.zzb(bArr, i));
                    while (true) {
                        int i14 = iZza2 + 8;
                        if (i14 >= i2) {
                            return i14;
                        }
                        iZza2 = zzgk.zza(bArr, i14, zzgmVar);
                        if (i3 != zzgmVar.zztk) {
                            return i14;
                        }
                        zzjbVar4.zzac(zzgk.zzb(bArr, iZza2));
                    }
                }
                return iZza2;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    zzif zzifVar = (zzif) zzikVarZzan;
                    int iZza8 = zzgk.zza(bArr, iZza2, zzgmVar);
                    int i15 = zzgmVar.zztk + iZza8;
                    while (iZza8 < i15) {
                        zzifVar.zzbs(zzgk.zza(bArr, iZza8));
                        iZza8 += 4;
                    }
                    if (iZza8 == i15) {
                        return iZza8;
                    }
                    throw zzin.zzhh();
                }
                if (i5 == 5) {
                    zzif zzifVar2 = (zzif) zzikVarZzan;
                    zzifVar2.zzbs(zzgk.zza(bArr, i));
                    while (true) {
                        int i16 = iZza2 + 4;
                        if (i16 >= i2) {
                            return i16;
                        }
                        iZza2 = zzgk.zza(bArr, i16, zzgmVar);
                        if (i3 != zzgmVar.zztk) {
                            return i16;
                        }
                        zzifVar2.zzbs(zzgk.zza(bArr, iZza2));
                    }
                }
                return iZza2;
            case 25:
            case 42:
                if (i5 == 2) {
                    zzgq zzgqVar = (zzgq) zzikVarZzan;
                    iZza = zzgk.zza(bArr, iZza2, zzgmVar);
                    int i17 = zzgmVar.zztk + iZza;
                    while (iZza < i17) {
                        iZza = zzgk.zzb(bArr, iZza, zzgmVar);
                        zzgqVar.addBoolean(zzgmVar.zztl != 0);
                    }
                    if (iZza != i17) {
                        throw zzin.zzhh();
                    }
                    return iZza;
                }
                if (i5 == 0) {
                    zzgq zzgqVar2 = (zzgq) zzikVarZzan;
                    iZza2 = zzgk.zzb(bArr, iZza2, zzgmVar);
                    zzgqVar2.addBoolean(zzgmVar.zztl != 0);
                    while (iZza2 < i2) {
                        int iZza9 = zzgk.zza(bArr, iZza2, zzgmVar);
                        if (i3 == zzgmVar.zztk) {
                            iZza2 = zzgk.zzb(bArr, iZza9, zzgmVar);
                            zzgqVar2.addBoolean(zzgmVar.zztl != 0);
                        }
                    }
                }
                return iZza2;
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) == 0) {
                        int iZza10 = zzgk.zza(bArr, iZza2, zzgmVar);
                        int i18 = zzgmVar.zztk;
                        if (i18 < 0) {
                            throw zzin.zzhi();
                        }
                        if (i18 == 0) {
                            zzikVarZzan.add("");
                        } else {
                            zzikVarZzan.add(new String(bArr, iZza10, i18, zzie.UTF_8));
                            iZza10 += i18;
                        }
                        while (iZza10 < i2) {
                            int iZza11 = zzgk.zza(bArr, iZza10, zzgmVar);
                            if (i3 != zzgmVar.zztk) {
                                return iZza10;
                            }
                            iZza10 = zzgk.zza(bArr, iZza11, zzgmVar);
                            int i19 = zzgmVar.zztk;
                            if (i19 < 0) {
                                throw zzin.zzhi();
                            }
                            if (i19 == 0) {
                                zzikVarZzan.add("");
                            } else {
                                zzikVarZzan.add(new String(bArr, iZza10, i19, zzie.UTF_8));
                                iZza10 += i19;
                            }
                        }
                        return iZza10;
                    }
                    int iZza12 = zzgk.zza(bArr, iZza2, zzgmVar);
                    int i20 = zzgmVar.zztk;
                    if (i20 < 0) {
                        throw zzin.zzhi();
                    }
                    if (i20 == 0) {
                        zzikVarZzan.add("");
                    } else {
                        int i21 = iZza12 + i20;
                        if (!zzld.zzf(bArr, iZza12, i21)) {
                            throw zzin.zzho();
                        }
                        zzikVarZzan.add(new String(bArr, iZza12, i20, zzie.UTF_8));
                        iZza12 = i21;
                    }
                    while (iZza12 < i2) {
                        int iZza13 = zzgk.zza(bArr, iZza12, zzgmVar);
                        if (i3 != zzgmVar.zztk) {
                            return iZza12;
                        }
                        iZza12 = zzgk.zza(bArr, iZza13, zzgmVar);
                        int i22 = zzgmVar.zztk;
                        if (i22 < 0) {
                            throw zzin.zzhi();
                        }
                        if (i22 == 0) {
                            zzikVarZzan.add("");
                        } else {
                            int i23 = iZza12 + i22;
                            if (!zzld.zzf(bArr, iZza12, i23)) {
                                throw zzin.zzho();
                            }
                            zzikVarZzan.add(new String(bArr, iZza12, i22, zzie.UTF_8));
                            iZza12 = i23;
                        }
                    }
                    return iZza12;
                }
                return iZza2;
            case 27:
                if (i5 == 2) {
                    return zzgk.zza(zzbu(i6), i3, bArr, i, i2, zzikVarZzan, zzgmVar);
                }
                return iZza2;
            case 28:
                if (i5 == 2) {
                    int iZza14 = zzgk.zza(bArr, iZza2, zzgmVar);
                    int i24 = zzgmVar.zztk;
                    if (i24 < 0) {
                        throw zzin.zzhi();
                    }
                    if (i24 > bArr.length - iZza14) {
                        throw zzin.zzhh();
                    }
                    if (i24 == 0) {
                        zzikVarZzan.add(zzgs.zztt);
                    } else {
                        zzikVarZzan.add(zzgs.zza(bArr, iZza14, i24));
                        iZza14 += i24;
                    }
                    while (iZza14 < i2) {
                        int iZza15 = zzgk.zza(bArr, iZza14, zzgmVar);
                        if (i3 != zzgmVar.zztk) {
                            return iZza14;
                        }
                        iZza14 = zzgk.zza(bArr, iZza15, zzgmVar);
                        int i25 = zzgmVar.zztk;
                        if (i25 < 0) {
                            throw zzin.zzhi();
                        }
                        if (i25 > bArr.length - iZza14) {
                            throw zzin.zzhh();
                        }
                        if (i25 == 0) {
                            zzikVarZzan.add(zzgs.zztt);
                        } else {
                            zzikVarZzan.add(zzgs.zza(bArr, iZza14, i25));
                            iZza14 += i25;
                        }
                    }
                    return iZza14;
                }
                return iZza2;
            case 30:
            case 44:
                if (i5 != 2) {
                    if (i5 == 0) {
                        iZza = zzgk.zza(i3, bArr, i, i2, (zzik<?>) zzikVarZzan, zzgmVar);
                    }
                    return iZza2;
                }
                iZza = zzgk.zza(bArr, iZza2, (zzik<?>) zzikVarZzan, zzgmVar);
                zzid zzidVar = (zzid) t;
                zzkx zzkxVar = zzidVar.zzxz;
                if (zzkxVar == zzkx.zzjb()) {
                    zzkxVar = null;
                }
                zzkx zzkxVar2 = (zzkx) zzke.zza(i4, zzikVarZzan, zzbw(i6), zzkxVar, this.zzabe);
                if (zzkxVar2 != null) {
                    zzidVar.zzxz = zzkxVar2;
                }
                return iZza;
            case 33:
            case 47:
                if (i5 == 2) {
                    zzif zzifVar3 = (zzif) zzikVarZzan;
                    int iZza16 = zzgk.zza(bArr, iZza2, zzgmVar);
                    int i26 = zzgmVar.zztk + iZza16;
                    while (iZza16 < i26) {
                        iZza16 = zzgk.zza(bArr, iZza16, zzgmVar);
                        zzifVar3.zzbs(zzhe.zzbb(zzgmVar.zztk));
                    }
                    if (iZza16 == i26) {
                        return iZza16;
                    }
                    throw zzin.zzhh();
                }
                if (i5 == 0) {
                    zzif zzifVar4 = (zzif) zzikVarZzan;
                    int iZza17 = zzgk.zza(bArr, iZza2, zzgmVar);
                    zzifVar4.zzbs(zzhe.zzbb(zzgmVar.zztk));
                    while (iZza17 < i2) {
                        int iZza18 = zzgk.zza(bArr, iZza17, zzgmVar);
                        if (i3 != zzgmVar.zztk) {
                            return iZza17;
                        }
                        iZza17 = zzgk.zza(bArr, iZza18, zzgmVar);
                        zzifVar4.zzbs(zzhe.zzbb(zzgmVar.zztk));
                    }
                    return iZza17;
                }
                return iZza2;
            case 34:
            case 48:
                if (i5 == 2) {
                    zzjb zzjbVar5 = (zzjb) zzikVarZzan;
                    int iZza19 = zzgk.zza(bArr, iZza2, zzgmVar);
                    int i27 = zzgmVar.zztk + iZza19;
                    while (iZza19 < i27) {
                        iZza19 = zzgk.zzb(bArr, iZza19, zzgmVar);
                        zzjbVar5.zzac(zzhe.zzr(zzgmVar.zztl));
                    }
                    if (iZza19 == i27) {
                        return iZza19;
                    }
                    throw zzin.zzhh();
                }
                if (i5 == 0) {
                    zzjb zzjbVar6 = (zzjb) zzikVarZzan;
                    int iZzb2 = zzgk.zzb(bArr, iZza2, zzgmVar);
                    zzjbVar6.zzac(zzhe.zzr(zzgmVar.zztl));
                    while (iZzb2 < i2) {
                        int iZza20 = zzgk.zza(bArr, iZzb2, zzgmVar);
                        if (i3 != zzgmVar.zztk) {
                            return iZzb2;
                        }
                        iZzb2 = zzgk.zzb(bArr, iZza20, zzgmVar);
                        zzjbVar6.zzac(zzhe.zzr(zzgmVar.zztl));
                    }
                    return iZzb2;
                }
                return iZza2;
            case 49:
                if (i5 == 3) {
                    zzkc zzkcVarZzbu = zzbu(i6);
                    int i28 = (i3 & (-8)) | 4;
                    iZza2 = zzgk.zza(zzkcVarZzbu, bArr, i, i2, i28, zzgmVar);
                    zzikVarZzan.add(zzgmVar.zztm);
                    while (iZza2 < i2) {
                        int iZza21 = zzgk.zza(bArr, iZza2, zzgmVar);
                        if (i3 == zzgmVar.zztk) {
                            iZza2 = zzgk.zza(zzkcVarZzbu, bArr, iZza21, i2, i28, zzgmVar);
                            zzikVarZzan.add(zzgmVar.zztm);
                        }
                    }
                }
                return iZza2;
            default:
                return iZza2;
        }
    }

    private final <K, V> int zza(T t, byte[] bArr, int i, int i2, int i3, long j, zzgm zzgmVar) throws IOException {
        Unsafe unsafe = zzaap;
        Object objZzbv = zzbv(i3);
        Object object = unsafe.getObject(t, j);
        if (this.zzabg.zzp(object)) {
            Object objZzr = this.zzabg.zzr(objZzbv);
            this.zzabg.zzc(objZzr, object);
            unsafe.putObject(t, j, objZzr);
            object = objZzr;
        }
        zzje<?, ?> zzjeVarZzs = this.zzabg.zzs(objZzbv);
        Map<?, ?> mapZzn = this.zzabg.zzn(object);
        int iZza = zzgk.zza(bArr, i, zzgmVar);
        int i4 = zzgmVar.zztk;
        if (i4 < 0 || i4 > i2 - iZza) {
            throw zzin.zzhh();
        }
        int i5 = i4 + iZza;
        K k = zzjeVarZzs.zzaaj;
        V v = zzjeVarZzs.zzgk;
        while (iZza < i5) {
            int iZza2 = iZza + 1;
            int i6 = bArr[iZza];
            if (i6 < 0) {
                iZza2 = zzgk.zza(i6, bArr, iZza2, zzgmVar);
                i6 = zzgmVar.zztk;
            }
            int i7 = iZza2;
            int i8 = i6 >>> 3;
            int i9 = i6 & 7;
            if (i8 == 1) {
                if (i9 == zzjeVarZzs.zzaai.zzjl()) {
                    iZza = zza(bArr, i7, i2, zzjeVarZzs.zzaai, (Class<?>) null, zzgmVar);
                    k = (K) zzgmVar.zztm;
                } else {
                    iZza = zzgk.zza(i6, bArr, i7, i2, zzgmVar);
                }
            } else if (i8 == 2 && i9 == zzjeVarZzs.zzaak.zzjl()) {
                iZza = zza(bArr, i7, i2, zzjeVarZzs.zzaak, zzjeVarZzs.zzgk.getClass(), zzgmVar);
                v = zzgmVar.zztm;
            } else {
                iZza = zzgk.zza(i6, bArr, i7, i2, zzgmVar);
            }
        }
        if (iZza != i5) {
            throw zzin.zzhn();
        }
        mapZzn.put(k, v);
        return i5;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzgm zzgmVar) throws IOException {
        int iZzb;
        Unsafe unsafe = zzaap;
        long j2 = this.zzaaq[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Double.valueOf(zzgk.zzc(bArr, i)));
                    iZzb = i + 8;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 52:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Float.valueOf(zzgk.zzd(bArr, i)));
                    iZzb = i + 4;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 53:
            case 54:
                if (i5 == 0) {
                    iZzb = zzgk.zzb(bArr, i, zzgmVar);
                    unsafe.putObject(t, j, Long.valueOf(zzgmVar.zztl));
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 55:
            case 62:
                if (i5 == 0) {
                    iZzb = zzgk.zza(bArr, i, zzgmVar);
                    unsafe.putObject(t, j, Integer.valueOf(zzgmVar.zztk));
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 56:
            case 65:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Long.valueOf(zzgk.zzb(bArr, i)));
                    iZzb = i + 8;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 57:
            case 64:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Integer.valueOf(zzgk.zza(bArr, i)));
                    iZzb = i + 4;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 58:
                if (i5 == 0) {
                    iZzb = zzgk.zzb(bArr, i, zzgmVar);
                    unsafe.putObject(t, j, Boolean.valueOf(zzgmVar.zztl != 0));
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 59:
                if (i5 == 2) {
                    int iZza = zzgk.zza(bArr, i, zzgmVar);
                    int i9 = zzgmVar.zztk;
                    if (i9 == 0) {
                        unsafe.putObject(t, j, "");
                    } else {
                        if ((i6 & 536870912) != 0 && !zzld.zzf(bArr, iZza, iZza + i9)) {
                            throw zzin.zzho();
                        }
                        unsafe.putObject(t, j, new String(bArr, iZza, i9, zzie.UTF_8));
                        iZza += i9;
                    }
                    unsafe.putInt(t, j2, i4);
                    return iZza;
                }
                return i;
            case 60:
                if (i5 == 2) {
                    int iZza2 = zzgk.zza(zzbu(i8), bArr, i, i2, zzgmVar);
                    Object object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object == null) {
                        unsafe.putObject(t, j, zzgmVar.zztm);
                    } else {
                        unsafe.putObject(t, j, zzie.zzb(object, zzgmVar.zztm));
                    }
                    unsafe.putInt(t, j2, i4);
                    return iZza2;
                }
                return i;
            case 61:
                if (i5 == 2) {
                    iZzb = zzgk.zze(bArr, i, zzgmVar);
                    unsafe.putObject(t, j, zzgmVar.zztm);
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 63:
                if (i5 == 0) {
                    int iZza3 = zzgk.zza(bArr, i, zzgmVar);
                    int i10 = zzgmVar.zztk;
                    zzij zzijVarZzbw = zzbw(i8);
                    if (zzijVarZzbw == null || zzijVarZzbw.zzg(i10)) {
                        unsafe.putObject(t, j, Integer.valueOf(i10));
                        iZzb = iZza3;
                        unsafe.putInt(t, j2, i4);
                        return iZzb;
                    }
                    zzv(t).zzb(i3, Long.valueOf(i10));
                    return iZza3;
                }
                return i;
            case 66:
                if (i5 == 0) {
                    iZzb = zzgk.zza(bArr, i, zzgmVar);
                    unsafe.putObject(t, j, Integer.valueOf(zzhe.zzbb(zzgmVar.zztk)));
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 67:
                if (i5 == 0) {
                    iZzb = zzgk.zzb(bArr, i, zzgmVar);
                    unsafe.putObject(t, j, Long.valueOf(zzhe.zzr(zzgmVar.zztl)));
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 68:
                if (i5 == 3) {
                    iZzb = zzgk.zza(zzbu(i8), bArr, i, i2, (i3 & (-8)) | 4, zzgmVar);
                    Object object2 = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object2 == null) {
                        unsafe.putObject(t, j, zzgmVar.zztm);
                    } else {
                        unsafe.putObject(t, j, zzie.zzb(object2, zzgmVar.zztm));
                    }
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            default:
                return i;
        }
    }

    private final zzkc zzbu(int i) {
        int i2 = (i / 3) << 1;
        zzkc zzkcVar = (zzkc) this.zzaar[i2];
        if (zzkcVar != null) {
            return zzkcVar;
        }
        zzkc<T> zzkcVarZzf = zzjy.zzij().zzf((Class) this.zzaar[i2 + 1]);
        this.zzaar[i2] = zzkcVarZzf;
        return zzkcVarZzf;
    }

    private final Object zzbv(int i) {
        return this.zzaar[(i / 3) << 1];
    }

    private final zzij zzbw(int i) {
        return (zzij) this.zzaar[((i / 3) << 1) + 1];
    }

    /* JADX WARN: Code restructure failed: missing block: B:197:0x065a, code lost:
    
        if (r2 == r4) goto L199;
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x065c, code lost:
    
        r31.putInt(r13, r2, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x0662, code lost:
    
        r2 = r8.zzaba;
        r4 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x0667, code lost:
    
        if (r2 >= r8.zzabb) goto L290;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x0669, code lost:
    
        r4 = (com.google.android.gms.internal.vision.zzkx) r8.zza((java.lang.Object) r13, r8.zzaaz[r2], (int) r4, (com.google.android.gms.internal.vision.zzku<UT, int>) r8.zzabe);
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x0679, code lost:
    
        if (r4 == null) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x067b, code lost:
    
        r8.zzabe.zzg(r13, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x0680, code lost:
    
        if (r9 != 0) goto L210;
     */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x0682, code lost:
    
        if (r0 != r6) goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x0689, code lost:
    
        throw com.google.android.gms.internal.vision.zzin.zzhn();
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x068a, code lost:
    
        if (r0 > r6) goto L213;
     */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x068c, code lost:
    
        if (r3 != r9) goto L213;
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x068e, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x0693, code lost:
    
        throw com.google.android.gms.internal.vision.zzin.zzhn();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:178:0x05c2  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x05c8  */
    /* JADX WARN: Type inference failed for: r13v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final int zza(T r35, byte[] r36, int r37, int r38, int r39, com.google.android.gms.internal.vision.zzgm r40) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1764
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzjr.zza(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.vision.zzgm):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x02d1, code lost:
    
        if (r0 == r5) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x02d5, code lost:
    
        r15 = r30;
        r14 = r31;
        r12 = r32;
        r13 = r34;
        r11 = r35;
        r2 = r18;
        r10 = r20;
        r1 = r25;
        r6 = r27;
        r7 = r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x031a, code lost:
    
        if (r0 == r15) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x033d, code lost:
    
        if (r0 == r15) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x033f, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:29:0x0094. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v11, types: [int] */
    @Override // com.google.android.gms.internal.vision.zzkc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r31, byte[] r32, int r33, int r34, com.google.android.gms.internal.vision.zzgm r35) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 956
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzjr.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.vision.zzgm):void");
    }

    @Override // com.google.android.gms.internal.vision.zzkc
    public final void zzj(T t) {
        int i;
        int i2 = this.zzaba;
        while (true) {
            i = this.zzabb;
            if (i2 >= i) {
                break;
            }
            long jZzbx = zzbx(this.zzaaz[i2]) & 1048575;
            Object objZzp = zzla.zzp(t, jZzbx);
            if (objZzp != null) {
                zzla.zza(t, jZzbx, this.zzabg.zzq(objZzp));
            }
            i2++;
        }
        int length = this.zzaaz.length;
        while (i < length) {
            this.zzabd.zzb(t, this.zzaaz[i]);
            i++;
        }
        this.zzabe.zzj(t);
        if (this.zzaav) {
            this.zzabf.zzj(t);
        }
    }

    private final <UT, UB> UB zza(Object obj, int i, UB ub, zzku<UT, UB> zzkuVar) {
        zzij zzijVarZzbw;
        int i2 = this.zzaaq[i];
        Object objZzp = zzla.zzp(obj, zzbx(i) & 1048575);
        return (objZzp == null || (zzijVarZzbw = zzbw(i)) == null) ? ub : (UB) zza(i, i2, this.zzabg.zzn(objZzp), zzijVarZzbw, (zzij) ub, (zzku<UT, zzij>) zzkuVar);
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzij zzijVar, UB ub, zzku<UT, UB> zzkuVar) {
        zzje<?, ?> zzjeVarZzs = this.zzabg.zzs(zzbv(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (!zzijVar.zzg(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = zzkuVar.zzja();
                }
                zzha zzhaVarZzaw = zzgs.zzaw(zzjf.zza(zzjeVarZzs, next.getKey(), next.getValue()));
                try {
                    zzjf.zza(zzhaVarZzaw.zzfq(), zzjeVarZzs, next.getKey(), next.getValue());
                    zzkuVar.zza((zzku<UT, UB>) ub, i2, zzhaVarZzaw.zzfp());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00dd  */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v22 */
    /* JADX WARN: Type inference failed for: r1v23, types: [com.google.android.gms.internal.vision.zzkc] */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.google.android.gms.internal.vision.zzkc] */
    @Override // com.google.android.gms.internal.vision.zzkc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzw(T r19) {
        /*
            Method dump skipped, instructions count: 318
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzjr.zzw(java.lang.Object):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i, zzkc zzkcVar) {
        return zzkcVar.zzw(zzla.zzp(obj, i & 1048575));
    }

    private static void zza(int i, Object obj, zzlr zzlrVar) throws IOException {
        if (obj instanceof String) {
            zzlrVar.zza(i, (String) obj);
        } else {
            zzlrVar.zza(i, (zzgs) obj);
        }
    }

    private final void zza(Object obj, int i, zzkd zzkdVar) throws IOException {
        if (zzbz(i)) {
            zzla.zza(obj, i & 1048575, zzkdVar.zzew());
        } else if (this.zzaaw) {
            zzla.zza(obj, i & 1048575, zzkdVar.readString());
        } else {
            zzla.zza(obj, i & 1048575, zzkdVar.zzex());
        }
    }

    private final int zzbx(int i) {
        return this.zzaaq[i + 1];
    }

    private final int zzby(int i) {
        return this.zzaaq[i + 2];
    }

    private static <T> double zzf(T t, long j) {
        return ((Double) zzla.zzp(t, j)).doubleValue();
    }

    private static <T> float zzg(T t, long j) {
        return ((Float) zzla.zzp(t, j)).floatValue();
    }

    private static <T> int zzh(T t, long j) {
        return ((Integer) zzla.zzp(t, j)).intValue();
    }

    private static <T> long zzi(T t, long j) {
        return ((Long) zzla.zzp(t, j)).longValue();
    }

    private static <T> boolean zzj(T t, long j) {
        return ((Boolean) zzla.zzp(t, j)).booleanValue();
    }

    private final boolean zzc(T t, T t2, int i) {
        return zzc(t, i) == zzc(t2, i);
    }

    private final boolean zza(T t, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zzc(t, i);
        }
        return (i3 & i4) != 0;
    }

    private final boolean zzc(T t, int i) {
        int iZzby = zzby(i);
        long j = iZzby & 1048575;
        if (j != 1048575) {
            return (zzla.zzk(t, j) & (1 << (iZzby >>> 20))) != 0;
        }
        int iZzbx = zzbx(i);
        long j2 = iZzbx & 1048575;
        switch ((iZzbx & 267386880) >>> 20) {
            case 0:
                return zzla.zzo(t, j2) != 0.0d;
            case 1:
                return zzla.zzn(t, j2) != 0.0f;
            case 2:
                return zzla.zzl(t, j2) != 0;
            case 3:
                return zzla.zzl(t, j2) != 0;
            case 4:
                return zzla.zzk(t, j2) != 0;
            case 5:
                return zzla.zzl(t, j2) != 0;
            case 6:
                return zzla.zzk(t, j2) != 0;
            case 7:
                return zzla.zzm(t, j2);
            case 8:
                Object objZzp = zzla.zzp(t, j2);
                if (objZzp instanceof String) {
                    return !((String) objZzp).isEmpty();
                }
                if (objZzp instanceof zzgs) {
                    return !zzgs.zztt.equals(objZzp);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzla.zzp(t, j2) != null;
            case 10:
                return !zzgs.zztt.equals(zzla.zzp(t, j2));
            case 11:
                return zzla.zzk(t, j2) != 0;
            case 12:
                return zzla.zzk(t, j2) != 0;
            case 13:
                return zzla.zzk(t, j2) != 0;
            case 14:
                return zzla.zzl(t, j2) != 0;
            case 15:
                return zzla.zzk(t, j2) != 0;
            case 16:
                return zzla.zzl(t, j2) != 0;
            case 17:
                return zzla.zzp(t, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final void zzd(T t, int i) {
        int iZzby = zzby(i);
        long j = 1048575 & iZzby;
        if (j == 1048575) {
            return;
        }
        zzla.zzb(t, j, (1 << (iZzby >>> 20)) | zzla.zzk(t, j));
    }

    private final boolean zzb(T t, int i, int i2) {
        return zzla.zzk(t, (long) (zzby(i2) & 1048575)) == i;
    }

    private final void zzc(T t, int i, int i2) {
        zzla.zzb(t, zzby(i2) & 1048575, i);
    }

    private final int zzca(int i) {
        if (i < this.zzaas || i > this.zzaat) {
            return -1;
        }
        return zzw(i, 0);
    }

    private final int zzv(int i, int i2) {
        if (i < this.zzaas || i > this.zzaat) {
            return -1;
        }
        return zzw(i, i2);
    }

    private final int zzw(int i, int i2) {
        int length = (this.zzaaq.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzaaq[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }
}
