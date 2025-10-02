package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjn<T> implements zzkb<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzkz.zzc();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzjj zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final boolean zzj;
    private final boolean zzk;
    private final int[] zzl;
    private final int zzm;
    private final int zzn;
    private final zzjr zzo;
    private final zzit zzp;
    private final zzkt<?, ?> zzq;
    private final zzhn<?> zzr;
    private final zzjc zzs;

    private zzjn(int[] iArr, Object[] objArr, int i, int i2, zzjj zzjjVar, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzjr zzjrVar, zzit zzitVar, zzkt<?, ?> zzktVar, zzhn<?> zzhnVar, zzjc zzjcVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = zzjjVar instanceof zzhy;
        this.zzj = z;
        this.zzh = zzhnVar != null && zzhnVar.zza(zzjjVar);
        this.zzk = false;
        this.zzl = iArr2;
        this.zzm = i3;
        this.zzn = i4;
        this.zzo = zzjrVar;
        this.zzp = zzitVar;
        this.zzq = zzktVar;
        this.zzr = zzhnVar;
        this.zzg = zzjjVar;
        this.zzs = zzjcVar;
    }

    private static boolean zzf(int i) {
        return (i & 536870912) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:163:0x0338  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0388  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static <T> com.google.android.gms.internal.measurement.zzjn<T> zza(java.lang.Class<T> r33, com.google.android.gms.internal.measurement.zzjh r34, com.google.android.gms.internal.measurement.zzjr r35, com.google.android.gms.internal.measurement.zzit r36, com.google.android.gms.internal.measurement.zzkt<?, ?> r37, com.google.android.gms.internal.measurement.zzhn<?> r38, com.google.android.gms.internal.measurement.zzjc r39) {
        /*
            Method dump skipped, instructions count: 1045
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zza(java.lang.Class, com.google.android.gms.internal.measurement.zzjh, com.google.android.gms.internal.measurement.zzjr, com.google.android.gms.internal.measurement.zzit, com.google.android.gms.internal.measurement.zzkt, com.google.android.gms.internal.measurement.zzhn, com.google.android.gms.internal.measurement.zzjc):com.google.android.gms.internal.measurement.zzjn");
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

    @Override // com.google.android.gms.internal.measurement.zzkb
    public final T zza() {
        return (T) this.zzo.zza(this.zzg);
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x01c1  */
    @Override // com.google.android.gms.internal.measurement.zzkb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zza(T r10, T r11) {
        /*
            Method dump skipped, instructions count: 640
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zza(java.lang.Object, java.lang.Object):boolean");
    }

    @Override // com.google.android.gms.internal.measurement.zzkb
    public final int zza(T t) {
        int i;
        int iZza;
        int length = this.zzc.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int iZzd = zzd(i3);
            int i4 = this.zzc[i3];
            long j = 1048575 & iZzd;
            int iHashCode = 37;
            switch ((iZzd & 267386880) >>> 20) {
                case 0:
                    i = i2 * 53;
                    iZza = zzia.zza(Double.doubleToLongBits(zzkz.zze(t, j)));
                    i2 = i + iZza;
                    break;
                case 1:
                    i = i2 * 53;
                    iZza = Float.floatToIntBits(zzkz.zzd(t, j));
                    i2 = i + iZza;
                    break;
                case 2:
                    i = i2 * 53;
                    iZza = zzia.zza(zzkz.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 3:
                    i = i2 * 53;
                    iZza = zzia.zza(zzkz.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 4:
                    i = i2 * 53;
                    iZza = zzkz.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 5:
                    i = i2 * 53;
                    iZza = zzia.zza(zzkz.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 6:
                    i = i2 * 53;
                    iZza = zzkz.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 7:
                    i = i2 * 53;
                    iZza = zzia.zza(zzkz.zzc(t, j));
                    i2 = i + iZza;
                    break;
                case 8:
                    i = i2 * 53;
                    iZza = ((String) zzkz.zzf(t, j)).hashCode();
                    i2 = i + iZza;
                    break;
                case 9:
                    Object objZzf = zzkz.zzf(t, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iZza = zzkz.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 11:
                    i = i2 * 53;
                    iZza = zzkz.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 12:
                    i = i2 * 53;
                    iZza = zzkz.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 13:
                    i = i2 * 53;
                    iZza = zzkz.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 14:
                    i = i2 * 53;
                    iZza = zzia.zza(zzkz.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 15:
                    i = i2 * 53;
                    iZza = zzkz.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 16:
                    i = i2 * 53;
                    iZza = zzia.zza(zzkz.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 17:
                    Object objZzf2 = zzkz.zzf(t, j);
                    if (objZzf2 != null) {
                        iHashCode = objZzf2.hashCode();
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
                    iZza = zzkz.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 50:
                    i = i2 * 53;
                    iZza = zzkz.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 51:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzia.zza(Double.doubleToLongBits(zzb(t, j)));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = Float.floatToIntBits(zzc(t, j));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzia.zza(zze(t, j));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzia.zza(zze(t, j));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzia.zza(zze(t, j));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzia.zza(zzf(t, j));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = ((String) zzkz.zzf(t, j)).hashCode();
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzkz.zzf(t, j).hashCode();
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzkz.zzf(t, j).hashCode();
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzia.zza(zze(t, j));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzia.zza(zze(t, j));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zza((zzjn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzkz.zzf(t, j).hashCode();
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzq.zzb(t).hashCode();
        return this.zzh ? (iHashCode2 * 53) + this.zzr.zza(t).hashCode() : iHashCode2;
    }

    @Override // com.google.android.gms.internal.measurement.zzkb
    public final void zzb(T t, T t2) {
        t2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzd = zzd(i);
            long j = 1048575 & iZzd;
            int i2 = this.zzc[i];
            switch ((iZzd & 267386880) >>> 20) {
                case 0:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza(t, j, zzkz.zze(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza((Object) t, j, zzkz.zzd(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza((Object) t, j, zzkz.zzb(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza((Object) t, j, zzkz.zzb(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza((Object) t, j, zzkz.zza(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza((Object) t, j, zzkz.zzb(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza((Object) t, j, zzkz.zza(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza(t, j, zzkz.zzc(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza(t, j, zzkz.zzf(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zza(t, t2, i);
                    break;
                case 10:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza(t, j, zzkz.zzf(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza((Object) t, j, zzkz.zza(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza((Object) t, j, zzkz.zza(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza((Object) t, j, zzkz.zza(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza((Object) t, j, zzkz.zzb(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza((Object) t, j, zzkz.zza(t2, j));
                        zzb((zzjn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zza((zzjn<T>) t2, i)) {
                        zzkz.zza((Object) t, j, zzkz.zzb(t2, j));
                        zzb((zzjn<T>) t, i);
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
                    this.zzp.zza(t, t2, j);
                    break;
                case 50:
                    zzkd.zza(this.zzs, t, t2, j);
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
                    if (zza((zzjn<T>) t2, i2, i)) {
                        zzkz.zza(t, j, zzkz.zzf(t2, j));
                        zzb((zzjn<T>) t, i2, i);
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
                    if (zza((zzjn<T>) t2, i2, i)) {
                        zzkz.zza(t, j, zzkz.zzf(t2, j));
                        zzb((zzjn<T>) t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzb(t, t2, i);
                    break;
            }
        }
        zzkd.zza(this.zzq, t, t2);
        if (this.zzh) {
            zzkd.zza(this.zzr, t, t2);
        }
    }

    private final void zza(T t, T t2, int i) {
        long jZzd = zzd(i) & 1048575;
        if (zza((zzjn<T>) t2, i)) {
            Object objZzf = zzkz.zzf(t, jZzd);
            Object objZzf2 = zzkz.zzf(t2, jZzd);
            if (objZzf != null && objZzf2 != null) {
                zzkz.zza(t, jZzd, zzia.zza(objZzf, objZzf2));
                zzb((zzjn<T>) t, i);
            } else if (objZzf2 != null) {
                zzkz.zza(t, jZzd, objZzf2);
                zzb((zzjn<T>) t, i);
            }
        }
    }

    private final void zzb(T t, T t2, int i) {
        int iZzd = zzd(i);
        int i2 = this.zzc[i];
        long j = iZzd & 1048575;
        if (zza((zzjn<T>) t2, i2, i)) {
            Object objZzf = zza((zzjn<T>) t, i2, i) ? zzkz.zzf(t, j) : null;
            Object objZzf2 = zzkz.zzf(t2, j);
            if (objZzf != null && objZzf2 != null) {
                zzkz.zza(t, j, zzia.zza(objZzf, objZzf2));
                zzb((zzjn<T>) t, i2, i);
            } else if (objZzf2 != null) {
                zzkz.zza(t, j, objZzf2);
                zzb((zzjn<T>) t, i2, i);
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
    @Override // com.google.android.gms.internal.measurement.zzkb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int zzb(T r19) {
        /*
            Method dump skipped, instructions count: 2726
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zzb(java.lang.Object):int");
    }

    private static <UT, UB> int zza(zzkt<UT, UB> zzktVar, T t) {
        return zzktVar.zzf(zzktVar.zzb(t));
    }

    private static List<?> zza(Object obj, long j) {
        return (List) zzkz.zzf(obj, j);
    }

    /* JADX WARN: Removed duplicated region for block: B:178:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0032  */
    @Override // com.google.android.gms.internal.measurement.zzkb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r14, com.google.android.gms.internal.measurement.zzlm r15) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 2916
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zza(java.lang.Object, com.google.android.gms.internal.measurement.zzlm):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void zzb(T r18, com.google.android.gms.internal.measurement.zzlm r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1332
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zzb(java.lang.Object, com.google.android.gms.internal.measurement.zzlm):void");
    }

    private final <K, V> void zza(zzlm zzlmVar, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzlmVar.zza(i, this.zzs.zzf(zzb(i2)), this.zzs.zzb(obj));
        }
    }

    private static <UT, UB> void zza(zzkt<UT, UB> zzktVar, T t, zzlm zzlmVar) throws IOException {
        zzktVar.zza((zzkt<UT, UB>) zzktVar.zzb(t), zzlmVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:164:0x05cc A[LOOP:5: B:162:0x05c8->B:164:0x05cc, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:166:0x05d9  */
    @Override // com.google.android.gms.internal.measurement.zzkb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r13, com.google.android.gms.internal.measurement.zzjy r14, com.google.android.gms.internal.measurement.zzhl r15) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1644
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zza(java.lang.Object, com.google.android.gms.internal.measurement.zzjy, com.google.android.gms.internal.measurement.zzhl):void");
    }

    private static zzks zze(Object obj) {
        zzhy zzhyVar = (zzhy) obj;
        zzks zzksVar = zzhyVar.zzb;
        if (zzksVar != zzks.zza()) {
            return zzksVar;
        }
        zzks zzksVarZzb = zzks.zzb();
        zzhyVar.zzb = zzksVarZzb;
        return zzksVarZzb;
    }

    private static int zza(byte[] bArr, int i, int i2, zzlg zzlgVar, Class<?> cls, zzgo zzgoVar) throws IOException {
        switch (zzjm.zza[zzlgVar.ordinal()]) {
            case 1:
                int iZzb = zzgl.zzb(bArr, i, zzgoVar);
                zzgoVar.zzc = Boolean.valueOf(zzgoVar.zzb != 0);
                return iZzb;
            case 2:
                return zzgl.zze(bArr, i, zzgoVar);
            case 3:
                zzgoVar.zzc = Double.valueOf(zzgl.zzc(bArr, i));
                return i + 8;
            case 4:
            case 5:
                zzgoVar.zzc = Integer.valueOf(zzgl.zza(bArr, i));
                return i + 4;
            case 6:
            case 7:
                zzgoVar.zzc = Long.valueOf(zzgl.zzb(bArr, i));
                return i + 8;
            case 8:
                zzgoVar.zzc = Float.valueOf(zzgl.zzd(bArr, i));
                return i + 4;
            case 9:
            case 10:
            case 11:
                int iZza = zzgl.zza(bArr, i, zzgoVar);
                zzgoVar.zzc = Integer.valueOf(zzgoVar.zza);
                return iZza;
            case 12:
            case 13:
                int iZzb2 = zzgl.zzb(bArr, i, zzgoVar);
                zzgoVar.zzc = Long.valueOf(zzgoVar.zzb);
                return iZzb2;
            case 14:
                return zzgl.zza(zzjx.zza().zza((Class) cls), bArr, i, i2, zzgoVar);
            case 15:
                int iZza2 = zzgl.zza(bArr, i, zzgoVar);
                zzgoVar.zzc = Integer.valueOf(zzhb.zze(zzgoVar.zza));
                return iZza2;
            case 16:
                int iZzb3 = zzgl.zzb(bArr, i, zzgoVar);
                zzgoVar.zzc = Long.valueOf(zzhb.zza(zzgoVar.zzb));
                return iZzb3;
            case 17:
                return zzgl.zzd(bArr, i, zzgoVar);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzgo zzgoVar) throws IOException {
        int iZza;
        int iZza2 = i;
        Unsafe unsafe = zzb;
        zzig zzigVarZza = (zzig) unsafe.getObject(t, j2);
        if (!zzigVarZza.zza()) {
            int size = zzigVarZza.size();
            zzigVarZza = zzigVarZza.zza(size == 0 ? 10 : size << 1);
            unsafe.putObject(t, j2, zzigVarZza);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    zzhj zzhjVar = (zzhj) zzigVarZza;
                    int iZza3 = zzgl.zza(bArr, iZza2, zzgoVar);
                    int i8 = zzgoVar.zza + iZza3;
                    while (iZza3 < i8) {
                        zzhjVar.zza(zzgl.zzc(bArr, iZza3));
                        iZza3 += 8;
                    }
                    if (iZza3 == i8) {
                        return iZza3;
                    }
                    throw zzij.zza();
                }
                if (i5 == 1) {
                    zzhj zzhjVar2 = (zzhj) zzigVarZza;
                    zzhjVar2.zza(zzgl.zzc(bArr, i));
                    while (true) {
                        int i9 = iZza2 + 8;
                        if (i9 >= i2) {
                            return i9;
                        }
                        iZza2 = zzgl.zza(bArr, i9, zzgoVar);
                        if (i3 != zzgoVar.zza) {
                            return i9;
                        }
                        zzhjVar2.zza(zzgl.zzc(bArr, iZza2));
                    }
                }
                return iZza2;
            case 19:
            case 36:
                if (i5 == 2) {
                    zzhx zzhxVar = (zzhx) zzigVarZza;
                    int iZza4 = zzgl.zza(bArr, iZza2, zzgoVar);
                    int i10 = zzgoVar.zza + iZza4;
                    while (iZza4 < i10) {
                        zzhxVar.zza(zzgl.zzd(bArr, iZza4));
                        iZza4 += 4;
                    }
                    if (iZza4 == i10) {
                        return iZza4;
                    }
                    throw zzij.zza();
                }
                if (i5 == 5) {
                    zzhx zzhxVar2 = (zzhx) zzigVarZza;
                    zzhxVar2.zza(zzgl.zzd(bArr, i));
                    while (true) {
                        int i11 = iZza2 + 4;
                        if (i11 >= i2) {
                            return i11;
                        }
                        iZza2 = zzgl.zza(bArr, i11, zzgoVar);
                        if (i3 != zzgoVar.zza) {
                            return i11;
                        }
                        zzhxVar2.zza(zzgl.zzd(bArr, iZza2));
                    }
                }
                return iZza2;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    zzix zzixVar = (zzix) zzigVarZza;
                    int iZza5 = zzgl.zza(bArr, iZza2, zzgoVar);
                    int i12 = zzgoVar.zza + iZza5;
                    while (iZza5 < i12) {
                        iZza5 = zzgl.zzb(bArr, iZza5, zzgoVar);
                        zzixVar.zza(zzgoVar.zzb);
                    }
                    if (iZza5 == i12) {
                        return iZza5;
                    }
                    throw zzij.zza();
                }
                if (i5 == 0) {
                    zzix zzixVar2 = (zzix) zzigVarZza;
                    int iZzb = zzgl.zzb(bArr, iZza2, zzgoVar);
                    zzixVar2.zza(zzgoVar.zzb);
                    while (iZzb < i2) {
                        int iZza6 = zzgl.zza(bArr, iZzb, zzgoVar);
                        if (i3 != zzgoVar.zza) {
                            return iZzb;
                        }
                        iZzb = zzgl.zzb(bArr, iZza6, zzgoVar);
                        zzixVar2.zza(zzgoVar.zzb);
                    }
                    return iZzb;
                }
                return iZza2;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return zzgl.zza(bArr, iZza2, (zzig<?>) zzigVarZza, zzgoVar);
                }
                if (i5 == 0) {
                    return zzgl.zza(i3, bArr, i, i2, (zzig<?>) zzigVarZza, zzgoVar);
                }
                return iZza2;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    zzix zzixVar3 = (zzix) zzigVarZza;
                    int iZza7 = zzgl.zza(bArr, iZza2, zzgoVar);
                    int i13 = zzgoVar.zza + iZza7;
                    while (iZza7 < i13) {
                        zzixVar3.zza(zzgl.zzb(bArr, iZza7));
                        iZza7 += 8;
                    }
                    if (iZza7 == i13) {
                        return iZza7;
                    }
                    throw zzij.zza();
                }
                if (i5 == 1) {
                    zzix zzixVar4 = (zzix) zzigVarZza;
                    zzixVar4.zza(zzgl.zzb(bArr, i));
                    while (true) {
                        int i14 = iZza2 + 8;
                        if (i14 >= i2) {
                            return i14;
                        }
                        iZza2 = zzgl.zza(bArr, i14, zzgoVar);
                        if (i3 != zzgoVar.zza) {
                            return i14;
                        }
                        zzixVar4.zza(zzgl.zzb(bArr, iZza2));
                    }
                }
                return iZza2;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    zzib zzibVar = (zzib) zzigVarZza;
                    int iZza8 = zzgl.zza(bArr, iZza2, zzgoVar);
                    int i15 = zzgoVar.zza + iZza8;
                    while (iZza8 < i15) {
                        zzibVar.zzd(zzgl.zza(bArr, iZza8));
                        iZza8 += 4;
                    }
                    if (iZza8 == i15) {
                        return iZza8;
                    }
                    throw zzij.zza();
                }
                if (i5 == 5) {
                    zzib zzibVar2 = (zzib) zzigVarZza;
                    zzibVar2.zzd(zzgl.zza(bArr, i));
                    while (true) {
                        int i16 = iZza2 + 4;
                        if (i16 >= i2) {
                            return i16;
                        }
                        iZza2 = zzgl.zza(bArr, i16, zzgoVar);
                        if (i3 != zzgoVar.zza) {
                            return i16;
                        }
                        zzibVar2.zzd(zzgl.zza(bArr, iZza2));
                    }
                }
                return iZza2;
            case 25:
            case 42:
                if (i5 == 2) {
                    zzgn zzgnVar = (zzgn) zzigVarZza;
                    iZza = zzgl.zza(bArr, iZza2, zzgoVar);
                    int i17 = zzgoVar.zza + iZza;
                    while (iZza < i17) {
                        iZza = zzgl.zzb(bArr, iZza, zzgoVar);
                        zzgnVar.zza(zzgoVar.zzb != 0);
                    }
                    if (iZza != i17) {
                        throw zzij.zza();
                    }
                    return iZza;
                }
                if (i5 == 0) {
                    zzgn zzgnVar2 = (zzgn) zzigVarZza;
                    iZza2 = zzgl.zzb(bArr, iZza2, zzgoVar);
                    zzgnVar2.zza(zzgoVar.zzb != 0);
                    while (iZza2 < i2) {
                        int iZza9 = zzgl.zza(bArr, iZza2, zzgoVar);
                        if (i3 == zzgoVar.zza) {
                            iZza2 = zzgl.zzb(bArr, iZza9, zzgoVar);
                            zzgnVar2.zza(zzgoVar.zzb != 0);
                        }
                    }
                }
                return iZza2;
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) == 0) {
                        int iZza10 = zzgl.zza(bArr, iZza2, zzgoVar);
                        int i18 = zzgoVar.zza;
                        if (i18 < 0) {
                            throw zzij.zzb();
                        }
                        if (i18 == 0) {
                            zzigVarZza.add("");
                        } else {
                            zzigVarZza.add(new String(bArr, iZza10, i18, zzia.zza));
                            iZza10 += i18;
                        }
                        while (iZza10 < i2) {
                            int iZza11 = zzgl.zza(bArr, iZza10, zzgoVar);
                            if (i3 != zzgoVar.zza) {
                                return iZza10;
                            }
                            iZza10 = zzgl.zza(bArr, iZza11, zzgoVar);
                            int i19 = zzgoVar.zza;
                            if (i19 < 0) {
                                throw zzij.zzb();
                            }
                            if (i19 == 0) {
                                zzigVarZza.add("");
                            } else {
                                zzigVarZza.add(new String(bArr, iZza10, i19, zzia.zza));
                                iZza10 += i19;
                            }
                        }
                        return iZza10;
                    }
                    int iZza12 = zzgl.zza(bArr, iZza2, zzgoVar);
                    int i20 = zzgoVar.zza;
                    if (i20 < 0) {
                        throw zzij.zzb();
                    }
                    if (i20 == 0) {
                        zzigVarZza.add("");
                    } else {
                        int i21 = iZza12 + i20;
                        if (!zzlb.zza(bArr, iZza12, i21)) {
                            throw zzij.zzh();
                        }
                        zzigVarZza.add(new String(bArr, iZza12, i20, zzia.zza));
                        iZza12 = i21;
                    }
                    while (iZza12 < i2) {
                        int iZza13 = zzgl.zza(bArr, iZza12, zzgoVar);
                        if (i3 != zzgoVar.zza) {
                            return iZza12;
                        }
                        iZza12 = zzgl.zza(bArr, iZza13, zzgoVar);
                        int i22 = zzgoVar.zza;
                        if (i22 < 0) {
                            throw zzij.zzb();
                        }
                        if (i22 == 0) {
                            zzigVarZza.add("");
                        } else {
                            int i23 = iZza12 + i22;
                            if (!zzlb.zza(bArr, iZza12, i23)) {
                                throw zzij.zzh();
                            }
                            zzigVarZza.add(new String(bArr, iZza12, i22, zzia.zza));
                            iZza12 = i23;
                        }
                    }
                    return iZza12;
                }
                return iZza2;
            case 27:
                if (i5 == 2) {
                    return zzgl.zza(zza(i6), i3, bArr, i, i2, zzigVarZza, zzgoVar);
                }
                return iZza2;
            case 28:
                if (i5 == 2) {
                    int iZza14 = zzgl.zza(bArr, iZza2, zzgoVar);
                    int i24 = zzgoVar.zza;
                    if (i24 < 0) {
                        throw zzij.zzb();
                    }
                    if (i24 > bArr.length - iZza14) {
                        throw zzij.zza();
                    }
                    if (i24 == 0) {
                        zzigVarZza.add(zzgp.zza);
                    } else {
                        zzigVarZza.add(zzgp.zza(bArr, iZza14, i24));
                        iZza14 += i24;
                    }
                    while (iZza14 < i2) {
                        int iZza15 = zzgl.zza(bArr, iZza14, zzgoVar);
                        if (i3 != zzgoVar.zza) {
                            return iZza14;
                        }
                        iZza14 = zzgl.zza(bArr, iZza15, zzgoVar);
                        int i25 = zzgoVar.zza;
                        if (i25 < 0) {
                            throw zzij.zzb();
                        }
                        if (i25 > bArr.length - iZza14) {
                            throw zzij.zza();
                        }
                        if (i25 == 0) {
                            zzigVarZza.add(zzgp.zza);
                        } else {
                            zzigVarZza.add(zzgp.zza(bArr, iZza14, i25));
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
                        iZza = zzgl.zza(i3, bArr, i, i2, (zzig<?>) zzigVarZza, zzgoVar);
                    }
                    return iZza2;
                }
                iZza = zzgl.zza(bArr, iZza2, (zzig<?>) zzigVarZza, zzgoVar);
                zzhy zzhyVar = (zzhy) t;
                zzks zzksVar = zzhyVar.zzb;
                if (zzksVar == zzks.zza()) {
                    zzksVar = null;
                }
                zzks zzksVar2 = (zzks) zzkd.zza(i4, zzigVarZza, zzc(i6), zzksVar, this.zzq);
                if (zzksVar2 != null) {
                    zzhyVar.zzb = zzksVar2;
                }
                return iZza;
            case 33:
            case 47:
                if (i5 == 2) {
                    zzib zzibVar3 = (zzib) zzigVarZza;
                    int iZza16 = zzgl.zza(bArr, iZza2, zzgoVar);
                    int i26 = zzgoVar.zza + iZza16;
                    while (iZza16 < i26) {
                        iZza16 = zzgl.zza(bArr, iZza16, zzgoVar);
                        zzibVar3.zzd(zzhb.zze(zzgoVar.zza));
                    }
                    if (iZza16 == i26) {
                        return iZza16;
                    }
                    throw zzij.zza();
                }
                if (i5 == 0) {
                    zzib zzibVar4 = (zzib) zzigVarZza;
                    int iZza17 = zzgl.zza(bArr, iZza2, zzgoVar);
                    zzibVar4.zzd(zzhb.zze(zzgoVar.zza));
                    while (iZza17 < i2) {
                        int iZza18 = zzgl.zza(bArr, iZza17, zzgoVar);
                        if (i3 != zzgoVar.zza) {
                            return iZza17;
                        }
                        iZza17 = zzgl.zza(bArr, iZza18, zzgoVar);
                        zzibVar4.zzd(zzhb.zze(zzgoVar.zza));
                    }
                    return iZza17;
                }
                return iZza2;
            case 34:
            case 48:
                if (i5 == 2) {
                    zzix zzixVar5 = (zzix) zzigVarZza;
                    int iZza19 = zzgl.zza(bArr, iZza2, zzgoVar);
                    int i27 = zzgoVar.zza + iZza19;
                    while (iZza19 < i27) {
                        iZza19 = zzgl.zzb(bArr, iZza19, zzgoVar);
                        zzixVar5.zza(zzhb.zza(zzgoVar.zzb));
                    }
                    if (iZza19 == i27) {
                        return iZza19;
                    }
                    throw zzij.zza();
                }
                if (i5 == 0) {
                    zzix zzixVar6 = (zzix) zzigVarZza;
                    int iZzb2 = zzgl.zzb(bArr, iZza2, zzgoVar);
                    zzixVar6.zza(zzhb.zza(zzgoVar.zzb));
                    while (iZzb2 < i2) {
                        int iZza20 = zzgl.zza(bArr, iZzb2, zzgoVar);
                        if (i3 != zzgoVar.zza) {
                            return iZzb2;
                        }
                        iZzb2 = zzgl.zzb(bArr, iZza20, zzgoVar);
                        zzixVar6.zza(zzhb.zza(zzgoVar.zzb));
                    }
                    return iZzb2;
                }
                return iZza2;
            case 49:
                if (i5 == 3) {
                    zzkb zzkbVarZza = zza(i6);
                    int i28 = (i3 & (-8)) | 4;
                    iZza2 = zzgl.zza(zzkbVarZza, bArr, i, i2, i28, zzgoVar);
                    zzigVarZza.add(zzgoVar.zzc);
                    while (iZza2 < i2) {
                        int iZza21 = zzgl.zza(bArr, iZza2, zzgoVar);
                        if (i3 == zzgoVar.zza) {
                            iZza2 = zzgl.zza(zzkbVarZza, bArr, iZza21, i2, i28, zzgoVar);
                            zzigVarZza.add(zzgoVar.zzc);
                        }
                    }
                }
                return iZza2;
            default:
                return iZza2;
        }
    }

    private final <K, V> int zza(T t, byte[] bArr, int i, int i2, int i3, long j, zzgo zzgoVar) throws IOException {
        Unsafe unsafe = zzb;
        Object objZzb = zzb(i3);
        Object object = unsafe.getObject(t, j);
        if (this.zzs.zzc(object)) {
            Object objZze = this.zzs.zze(objZzb);
            this.zzs.zza(objZze, object);
            unsafe.putObject(t, j, objZze);
            object = objZze;
        }
        zzja<?, ?> zzjaVarZzf = this.zzs.zzf(objZzb);
        Map<?, ?> mapZza = this.zzs.zza(object);
        int iZza = zzgl.zza(bArr, i, zzgoVar);
        int i4 = zzgoVar.zza;
        if (i4 < 0 || i4 > i2 - iZza) {
            throw zzij.zza();
        }
        int i5 = i4 + iZza;
        K k = zzjaVarZzf.zzb;
        V v = zzjaVarZzf.zzd;
        while (iZza < i5) {
            int iZza2 = iZza + 1;
            int i6 = bArr[iZza];
            if (i6 < 0) {
                iZza2 = zzgl.zza(i6, bArr, iZza2, zzgoVar);
                i6 = zzgoVar.zza;
            }
            int i7 = iZza2;
            int i8 = i6 >>> 3;
            int i9 = i6 & 7;
            if (i8 == 1) {
                if (i9 == zzjaVarZzf.zza.zzb()) {
                    iZza = zza(bArr, i7, i2, zzjaVarZzf.zza, (Class<?>) null, zzgoVar);
                    k = (K) zzgoVar.zzc;
                } else {
                    iZza = zzgl.zza(i6, bArr, i7, i2, zzgoVar);
                }
            } else if (i8 == 2 && i9 == zzjaVarZzf.zzc.zzb()) {
                iZza = zza(bArr, i7, i2, zzjaVarZzf.zzc, zzjaVarZzf.zzd.getClass(), zzgoVar);
                v = zzgoVar.zzc;
            } else {
                iZza = zzgl.zza(i6, bArr, i7, i2, zzgoVar);
            }
        }
        if (iZza != i5) {
            throw zzij.zzg();
        }
        mapZza.put(k, v);
        return i5;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzgo zzgoVar) throws IOException {
        int iZzb;
        Unsafe unsafe = zzb;
        long j2 = this.zzc[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Double.valueOf(zzgl.zzc(bArr, i)));
                    iZzb = i + 8;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 52:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Float.valueOf(zzgl.zzd(bArr, i)));
                    iZzb = i + 4;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 53:
            case 54:
                if (i5 == 0) {
                    iZzb = zzgl.zzb(bArr, i, zzgoVar);
                    unsafe.putObject(t, j, Long.valueOf(zzgoVar.zzb));
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 55:
            case 62:
                if (i5 == 0) {
                    iZzb = zzgl.zza(bArr, i, zzgoVar);
                    unsafe.putObject(t, j, Integer.valueOf(zzgoVar.zza));
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 56:
            case 65:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Long.valueOf(zzgl.zzb(bArr, i)));
                    iZzb = i + 8;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 57:
            case 64:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Integer.valueOf(zzgl.zza(bArr, i)));
                    iZzb = i + 4;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 58:
                if (i5 == 0) {
                    iZzb = zzgl.zzb(bArr, i, zzgoVar);
                    unsafe.putObject(t, j, Boolean.valueOf(zzgoVar.zzb != 0));
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 59:
                if (i5 == 2) {
                    int iZza = zzgl.zza(bArr, i, zzgoVar);
                    int i9 = zzgoVar.zza;
                    if (i9 == 0) {
                        unsafe.putObject(t, j, "");
                    } else {
                        if ((i6 & 536870912) != 0 && !zzlb.zza(bArr, iZza, iZza + i9)) {
                            throw zzij.zzh();
                        }
                        unsafe.putObject(t, j, new String(bArr, iZza, i9, zzia.zza));
                        iZza += i9;
                    }
                    unsafe.putInt(t, j2, i4);
                    return iZza;
                }
                return i;
            case 60:
                if (i5 == 2) {
                    int iZza2 = zzgl.zza(zza(i8), bArr, i, i2, zzgoVar);
                    Object object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object == null) {
                        unsafe.putObject(t, j, zzgoVar.zzc);
                    } else {
                        unsafe.putObject(t, j, zzia.zza(object, zzgoVar.zzc));
                    }
                    unsafe.putInt(t, j2, i4);
                    return iZza2;
                }
                return i;
            case 61:
                if (i5 == 2) {
                    iZzb = zzgl.zze(bArr, i, zzgoVar);
                    unsafe.putObject(t, j, zzgoVar.zzc);
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 63:
                if (i5 == 0) {
                    int iZza3 = zzgl.zza(bArr, i, zzgoVar);
                    int i10 = zzgoVar.zza;
                    zzif zzifVarZzc = zzc(i8);
                    if (zzifVarZzc == null || zzifVarZzc.zza(i10)) {
                        unsafe.putObject(t, j, Integer.valueOf(i10));
                        iZzb = iZza3;
                        unsafe.putInt(t, j2, i4);
                        return iZzb;
                    }
                    zze(t).zza(i3, Long.valueOf(i10));
                    return iZza3;
                }
                return i;
            case 66:
                if (i5 == 0) {
                    iZzb = zzgl.zza(bArr, i, zzgoVar);
                    unsafe.putObject(t, j, Integer.valueOf(zzhb.zze(zzgoVar.zza)));
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 67:
                if (i5 == 0) {
                    iZzb = zzgl.zzb(bArr, i, zzgoVar);
                    unsafe.putObject(t, j, Long.valueOf(zzhb.zza(zzgoVar.zzb)));
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            case 68:
                if (i5 == 3) {
                    iZzb = zzgl.zza(zza(i8), bArr, i, i2, (i3 & (-8)) | 4, zzgoVar);
                    Object object2 = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object2 == null) {
                        unsafe.putObject(t, j, zzgoVar.zzc);
                    } else {
                        unsafe.putObject(t, j, zzia.zza(object2, zzgoVar.zzc));
                    }
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                return i;
            default:
                return i;
        }
    }

    private final zzkb zza(int i) {
        int i2 = (i / 3) << 1;
        zzkb zzkbVar = (zzkb) this.zzd[i2];
        if (zzkbVar != null) {
            return zzkbVar;
        }
        zzkb<T> zzkbVarZza = zzjx.zza().zza((Class) this.zzd[i2 + 1]);
        this.zzd[i2] = zzkbVarZza;
        return zzkbVarZza;
    }

    private final Object zzb(int i) {
        return this.zzd[(i / 3) << 1];
    }

    private final zzif zzc(int i) {
        return (zzif) this.zzd[((i / 3) << 1) + 1];
    }

    /* JADX WARN: Code restructure failed: missing block: B:151:0x04c4, code lost:
    
        if (r6 == 1048575) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x04c6, code lost:
    
        r25.putInt(r12, r6, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x04cc, code lost:
    
        r1 = r9.zzm;
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x04d1, code lost:
    
        if (r1 >= r9.zzn) goto L238;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x04d3, code lost:
    
        r2 = (com.google.android.gms.internal.measurement.zzks) r9.zza(r12, r9.zzl[r1], (int) r2, (com.google.android.gms.internal.measurement.zzkt<UT, int>) r9.zzq);
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x04e2, code lost:
    
        if (r2 == null) goto L159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x04e4, code lost:
    
        r9.zzq.zzb(r12, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x04e9, code lost:
    
        if (r7 != 0) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x04ed, code lost:
    
        if (r0 != r32) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x04f4, code lost:
    
        throw com.google.android.gms.internal.measurement.zzij.zzg();
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x04f7, code lost:
    
        if (r0 > r32) goto L169;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x04f9, code lost:
    
        if (r3 != r7) goto L169;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x04fb, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x0500, code lost:
    
        throw com.google.android.gms.internal.measurement.zzij.zzg();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final int zza(T r29, byte[] r30, int r31, int r32, int r33, com.google.android.gms.internal.measurement.zzgo r34) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1322
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zza(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.measurement.zzgo):int");
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
    @Override // com.google.android.gms.internal.measurement.zzkb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r31, byte[] r32, int r33, int r34, com.google.android.gms.internal.measurement.zzgo r35) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 956
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.measurement.zzgo):void");
    }

    @Override // com.google.android.gms.internal.measurement.zzkb
    public final void zzc(T t) {
        int i;
        int i2 = this.zzm;
        while (true) {
            i = this.zzn;
            if (i2 >= i) {
                break;
            }
            long jZzd = zzd(this.zzl[i2]) & 1048575;
            Object objZzf = zzkz.zzf(t, jZzd);
            if (objZzf != null) {
                zzkz.zza(t, jZzd, this.zzs.zzd(objZzf));
            }
            i2++;
        }
        int length = this.zzl.length;
        while (i < length) {
            this.zzp.zzb(t, this.zzl[i]);
            i++;
        }
        this.zzq.zzd(t);
        if (this.zzh) {
            this.zzr.zzc(t);
        }
    }

    private final <UT, UB> UB zza(Object obj, int i, UB ub, zzkt<UT, UB> zzktVar) {
        zzif zzifVarZzc;
        int i2 = this.zzc[i];
        Object objZzf = zzkz.zzf(obj, zzd(i) & 1048575);
        return (objZzf == null || (zzifVarZzc = zzc(i)) == null) ? ub : (UB) zza(i, i2, this.zzs.zza(objZzf), zzifVarZzc, (zzif) ub, (zzkt<UT, zzif>) zzktVar);
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzif zzifVar, UB ub, zzkt<UT, UB> zzktVar) {
        zzja<?, ?> zzjaVarZzf = this.zzs.zzf(zzb(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (!zzifVar.zza(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = zzktVar.zza();
                }
                zzgx zzgxVarZzc = zzgp.zzc(zzjb.zza(zzjaVarZzf, next.getKey(), next.getValue()));
                try {
                    zzjb.zza(zzgxVarZzc.zzb(), zzjaVarZzf, next.getKey(), next.getValue());
                    zzktVar.zza((zzkt<UT, UB>) ub, i2, zzgxVarZzc.zza());
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
    /* JADX WARN: Type inference failed for: r1v23, types: [com.google.android.gms.internal.measurement.zzkb] */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.google.android.gms.internal.measurement.zzkb] */
    @Override // com.google.android.gms.internal.measurement.zzkb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzd(T r19) {
        /*
            Method dump skipped, instructions count: 318
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjn.zzd(java.lang.Object):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i, zzkb zzkbVar) {
        return zzkbVar.zzd(zzkz.zzf(obj, i & 1048575));
    }

    private static void zza(int i, Object obj, zzlm zzlmVar) throws IOException {
        if (obj instanceof String) {
            zzlmVar.zza(i, (String) obj);
        } else {
            zzlmVar.zza(i, (zzgp) obj);
        }
    }

    private final void zza(Object obj, int i, zzjy zzjyVar) throws IOException {
        if (zzf(i)) {
            zzkz.zza(obj, i & 1048575, zzjyVar.zzm());
        } else if (this.zzi) {
            zzkz.zza(obj, i & 1048575, zzjyVar.zzl());
        } else {
            zzkz.zza(obj, i & 1048575, zzjyVar.zzn());
        }
    }

    private final int zzd(int i) {
        return this.zzc[i + 1];
    }

    private final int zze(int i) {
        return this.zzc[i + 2];
    }

    private static <T> double zzb(T t, long j) {
        return ((Double) zzkz.zzf(t, j)).doubleValue();
    }

    private static <T> float zzc(T t, long j) {
        return ((Float) zzkz.zzf(t, j)).floatValue();
    }

    private static <T> int zzd(T t, long j) {
        return ((Integer) zzkz.zzf(t, j)).intValue();
    }

    private static <T> long zze(T t, long j) {
        return ((Long) zzkz.zzf(t, j)).longValue();
    }

    private static <T> boolean zzf(T t, long j) {
        return ((Boolean) zzkz.zzf(t, j)).booleanValue();
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza((zzjn<T>) t, i) == zza((zzjn<T>) t2, i);
    }

    private final boolean zza(T t, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zza((zzjn<T>) t, i);
        }
        return (i3 & i4) != 0;
    }

    private final boolean zza(T t, int i) {
        int iZze = zze(i);
        long j = iZze & 1048575;
        if (j != 1048575) {
            return (zzkz.zza(t, j) & (1 << (iZze >>> 20))) != 0;
        }
        int iZzd = zzd(i);
        long j2 = iZzd & 1048575;
        switch ((iZzd & 267386880) >>> 20) {
            case 0:
                return zzkz.zze(t, j2) != 0.0d;
            case 1:
                return zzkz.zzd(t, j2) != 0.0f;
            case 2:
                return zzkz.zzb(t, j2) != 0;
            case 3:
                return zzkz.zzb(t, j2) != 0;
            case 4:
                return zzkz.zza(t, j2) != 0;
            case 5:
                return zzkz.zzb(t, j2) != 0;
            case 6:
                return zzkz.zza(t, j2) != 0;
            case 7:
                return zzkz.zzc(t, j2);
            case 8:
                Object objZzf = zzkz.zzf(t, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zzgp) {
                    return !zzgp.zza.equals(objZzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzkz.zzf(t, j2) != null;
            case 10:
                return !zzgp.zza.equals(zzkz.zzf(t, j2));
            case 11:
                return zzkz.zza(t, j2) != 0;
            case 12:
                return zzkz.zza(t, j2) != 0;
            case 13:
                return zzkz.zza(t, j2) != 0;
            case 14:
                return zzkz.zzb(t, j2) != 0;
            case 15:
                return zzkz.zza(t, j2) != 0;
            case 16:
                return zzkz.zzb(t, j2) != 0;
            case 17:
                return zzkz.zzf(t, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final void zzb(T t, int i) {
        int iZze = zze(i);
        long j = 1048575 & iZze;
        if (j == 1048575) {
            return;
        }
        zzkz.zza((Object) t, j, (1 << (iZze >>> 20)) | zzkz.zza(t, j));
    }

    private final boolean zza(T t, int i, int i2) {
        return zzkz.zza(t, (long) (zze(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zzkz.zza((Object) t, zze(i2) & 1048575, i);
    }

    private final int zzg(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzb(i, 0);
    }

    private final int zza(int i, int i2) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzb(i, i2);
    }

    private final int zzb(int i, int i2) {
        int length = (this.zzc.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzc[i4];
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
