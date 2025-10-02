package com.google.android.gms.internal.wearable;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import sun.misc.Unsafe;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzda<T> implements zzdi<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzeg.zzq();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzcx zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final int[] zzj;
    private final int zzk;
    private final int zzl;
    private final zzcl zzm;
    private final zzdw<?, ?> zzn;
    private final zzbh<?> zzo;
    private final zzdc zzp;
    private final zzcs zzq;

    /* JADX WARN: Multi-variable type inference failed */
    private zzda(int[] iArr, int[] iArr2, Object[] objArr, int i, int i2, zzcx zzcxVar, boolean z, boolean z2, int[] iArr3, int i3, int i4, zzdc zzdcVar, zzcl zzclVar, zzdw<?, ?> zzdwVar, zzbh<?> zzbhVar, zzcs zzcsVar) {
        this.zzc = iArr;
        this.zzd = iArr2;
        this.zze = objArr;
        this.zzf = i;
        this.zzi = zzcxVar;
        boolean z3 = false;
        if (zzdwVar != 0 && zzdwVar.zza(i2)) {
            z3 = true;
        }
        this.zzh = z3;
        this.zzj = z2;
        this.zzk = iArr3;
        this.zzl = i3;
        this.zzp = i4;
        this.zzm = zzdcVar;
        this.zzn = zzclVar;
        this.zzo = zzdwVar;
        this.zzg = i2;
        this.zzq = zzbhVar;
    }

    private final int zzA(int i) {
        return this.zzc[i + 1];
    }

    private final int zzB(int i) {
        return this.zzc[i + 2];
    }

    private static int zzC(int i) {
        return (i >>> 20) & 255;
    }

    private static <T> double zzD(T t, long j) {
        return ((Double) zzeg.zzn(t, j)).doubleValue();
    }

    private static <T> float zzE(T t, long j) {
        return ((Float) zzeg.zzn(t, j)).floatValue();
    }

    private static <T> int zzF(T t, long j) {
        return ((Integer) zzeg.zzn(t, j)).intValue();
    }

    private static <T> long zzG(T t, long j) {
        return ((Long) zzeg.zzn(t, j)).longValue();
    }

    private static <T> boolean zzH(T t, long j) {
        return ((Boolean) zzeg.zzn(t, j)).booleanValue();
    }

    private final boolean zzI(T t, T t2, int i) {
        return zzK(t, i) == zzK(t2, i);
    }

    private final boolean zzJ(T t, int i, int i2, int i3, int i4) {
        return i2 == 1048575 ? zzK(t, i) : (i3 & i4) != 0;
    }

    private final boolean zzK(T t, int i) {
        int iZzB = zzB(i);
        long j = iZzB & 1048575;
        if (j != 1048575) {
            return (zzeg.zzd(t, j) & (1 << (iZzB >>> 20))) != 0;
        }
        int iZzA = zzA(i);
        long j2 = iZzA & 1048575;
        switch (zzC(iZzA)) {
            case 0:
                return zzeg.zzl(t, j2) != 0.0d;
            case 1:
                return zzeg.zzj(t, j2) != 0.0f;
            case 2:
                return zzeg.zzf(t, j2) != 0;
            case 3:
                return zzeg.zzf(t, j2) != 0;
            case 4:
                return zzeg.zzd(t, j2) != 0;
            case 5:
                return zzeg.zzf(t, j2) != 0;
            case 6:
                return zzeg.zzd(t, j2) != 0;
            case 7:
                return zzeg.zzh(t, j2);
            case 8:
                Object objZzn = zzeg.zzn(t, j2);
                if (objZzn instanceof String) {
                    return !((String) objZzn).isEmpty();
                }
                if (objZzn instanceof zzau) {
                    return !zzau.zzb.equals(objZzn);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzeg.zzn(t, j2) != null;
            case 10:
                return !zzau.zzb.equals(zzeg.zzn(t, j2));
            case 11:
                return zzeg.zzd(t, j2) != 0;
            case 12:
                return zzeg.zzd(t, j2) != 0;
            case 13:
                return zzeg.zzd(t, j2) != 0;
            case 14:
                return zzeg.zzf(t, j2) != 0;
            case 15:
                return zzeg.zzd(t, j2) != 0;
            case 16:
                return zzeg.zzf(t, j2) != 0;
            case 17:
                return zzeg.zzn(t, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final void zzL(T t, int i) {
        int iZzB = zzB(i);
        long j = 1048575 & iZzB;
        if (j == 1048575) {
            return;
        }
        zzeg.zze(t, j, (1 << (iZzB >>> 20)) | zzeg.zzd(t, j));
    }

    private final boolean zzM(T t, int i, int i2) {
        return zzeg.zzd(t, (long) (zzB(i2) & 1048575)) == i;
    }

    private final void zzN(T t, int i, int i2) {
        zzeg.zze(t, zzB(i2) & 1048575, i);
    }

    private final int zzO(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzQ(i, 0);
    }

    private final int zzP(int i, int i2) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzQ(i, i2);
    }

    private final int zzQ(int i, int i2) {
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final void zzR(T t, zzbc zzbcVar) throws IOException {
        int i;
        boolean z;
        if (this.zzh) {
            this.zzo.zzb(t);
            throw null;
        }
        int length = this.zzc.length;
        Unsafe unsafe = zzb;
        int i2 = 1048575;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        while (i4 < length) {
            int iZzA = zzA(i4);
            int i6 = this.zzc[i4];
            int iZzC = zzC(iZzA);
            if (iZzC <= 17) {
                int i7 = this.zzc[i4 + 2];
                int i8 = i7 & i2;
                if (i8 != i3) {
                    i5 = unsafe.getInt(t, i8);
                    i3 = i8;
                }
                i = 1 << (i7 >>> 20);
            } else {
                i = 0;
            }
            long j = iZzA & i2;
            switch (iZzC) {
                case 0:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzf(i6, zzeg.zzl(t, j));
                        break;
                    }
                case 1:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zze(i6, zzeg.zzj(t, j));
                        break;
                    }
                case 2:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzc(i6, unsafe.getLong(t, j));
                        break;
                    }
                case 3:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzh(i6, unsafe.getLong(t, j));
                        break;
                    }
                case 4:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzi(i6, unsafe.getInt(t, j));
                        break;
                    }
                case 5:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzj(i6, unsafe.getLong(t, j));
                        break;
                    }
                case 6:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzk(i6, unsafe.getInt(t, j));
                        break;
                    }
                case 7:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzl(i6, zzeg.zzh(t, j));
                        break;
                    }
                case 8:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzT(i6, unsafe.getObject(t, j), zzbcVar);
                        break;
                    }
                case 9:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzr(i6, unsafe.getObject(t, j), zzv(i4));
                        break;
                    }
                case 10:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzn(i6, (zzau) unsafe.getObject(t, j));
                        break;
                    }
                case 11:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzo(i6, unsafe.getInt(t, j));
                        break;
                    }
                case 12:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzg(i6, unsafe.getInt(t, j));
                        break;
                    }
                case 13:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzb(i6, unsafe.getInt(t, j));
                        break;
                    }
                case 14:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzd(i6, unsafe.getLong(t, j));
                        break;
                    }
                case 15:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzp(i6, unsafe.getInt(t, j));
                        break;
                    }
                case 16:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzq(i6, unsafe.getLong(t, j));
                        break;
                    }
                case 17:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzbcVar.zzs(i6, unsafe.getObject(t, j), zzv(i4));
                        break;
                    }
                case 18:
                    zzdk.zzJ(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, false);
                    break;
                case 19:
                    zzdk.zzK(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, false);
                    break;
                case 20:
                    zzdk.zzL(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, false);
                    break;
                case 21:
                    zzdk.zzM(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, false);
                    break;
                case 22:
                    zzdk.zzQ(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, false);
                    break;
                case 23:
                    zzdk.zzO(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, false);
                    break;
                case 24:
                    zzdk.zzT(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, false);
                    break;
                case 25:
                    zzdk.zzW(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, false);
                    break;
                case 26:
                    zzdk.zzX(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar);
                    break;
                case 27:
                    zzdk.zzZ(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, zzv(i4));
                    break;
                case 28:
                    zzdk.zzY(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar);
                    break;
                case 29:
                    z = false;
                    zzdk.zzR(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, false);
                    break;
                case 30:
                    z = false;
                    zzdk.zzV(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, false);
                    break;
                case 31:
                    z = false;
                    zzdk.zzU(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, false);
                    break;
                case 32:
                    z = false;
                    zzdk.zzP(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, false);
                    break;
                case 33:
                    z = false;
                    zzdk.zzS(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, false);
                    break;
                case 34:
                    z = false;
                    zzdk.zzN(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, false);
                    break;
                case 35:
                    zzdk.zzJ(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, true);
                    break;
                case 36:
                    zzdk.zzK(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, true);
                    break;
                case 37:
                    zzdk.zzL(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, true);
                    break;
                case 38:
                    zzdk.zzM(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, true);
                    break;
                case 39:
                    zzdk.zzQ(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, true);
                    break;
                case 40:
                    zzdk.zzO(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, true);
                    break;
                case 41:
                    zzdk.zzT(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, true);
                    break;
                case 42:
                    zzdk.zzW(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, true);
                    break;
                case 43:
                    zzdk.zzR(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, true);
                    break;
                case 44:
                    zzdk.zzV(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, true);
                    break;
                case 45:
                    zzdk.zzU(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, true);
                    break;
                case 46:
                    zzdk.zzP(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, true);
                    break;
                case 47:
                    zzdk.zzS(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, true);
                    break;
                case 48:
                    zzdk.zzN(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, true);
                    break;
                case 49:
                    zzdk.zzaa(this.zzc[i4], (List) unsafe.getObject(t, j), zzbcVar, zzv(i4));
                    break;
                case 50:
                    zzS(zzbcVar, i6, unsafe.getObject(t, j), i4);
                    break;
                case 51:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzf(i6, zzD(t, j));
                    }
                    break;
                case 52:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zze(i6, zzE(t, j));
                    }
                    break;
                case 53:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzc(i6, zzG(t, j));
                    }
                    break;
                case 54:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzh(i6, zzG(t, j));
                    }
                    break;
                case 55:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzi(i6, zzF(t, j));
                    }
                    break;
                case 56:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzj(i6, zzG(t, j));
                    }
                    break;
                case 57:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzk(i6, zzF(t, j));
                    }
                    break;
                case 58:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzl(i6, zzH(t, j));
                    }
                    break;
                case 59:
                    if (zzM(t, i6, i4)) {
                        zzT(i6, unsafe.getObject(t, j), zzbcVar);
                    }
                    break;
                case 60:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzr(i6, unsafe.getObject(t, j), zzv(i4));
                    }
                    break;
                case 61:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzn(i6, (zzau) unsafe.getObject(t, j));
                    }
                    break;
                case 62:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzo(i6, zzF(t, j));
                    }
                    break;
                case 63:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzg(i6, zzF(t, j));
                    }
                    break;
                case 64:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzb(i6, zzF(t, j));
                    }
                    break;
                case 65:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzd(i6, zzG(t, j));
                    }
                    break;
                case 66:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzp(i6, zzF(t, j));
                    }
                    break;
                case 67:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzq(i6, zzG(t, j));
                    }
                    break;
                case 68:
                    if (zzM(t, i6, i4)) {
                        zzbcVar.zzs(i6, unsafe.getObject(t, j), zzv(i4));
                    }
                    break;
            }
            i4 += 3;
            i2 = 1048575;
        }
        zzdw<?, ?> zzdwVar = this.zzn;
        zzdwVar.zzi(zzdwVar.zzd(t), zzbcVar);
    }

    private final <K, V> void zzS(zzbc zzbcVar, int i, Object obj, int i2) throws IOException {
        if (obj == null) {
            return;
        }
        throw null;
    }

    private static final void zzT(int i, Object obj, zzbc zzbcVar) throws IOException {
        if (obj instanceof String) {
            zzbcVar.zzm(i, (String) obj);
        } else {
            zzbcVar.zzn(i, (zzau) obj);
        }
    }

    static zzdx zzf(Object obj) {
        zzbs zzbsVar = (zzbs) obj;
        zzdx zzdxVar = zzbsVar.zzc;
        if (zzdxVar != zzdx.zza()) {
            return zzdxVar;
        }
        zzdx zzdxVarZzb = zzdx.zzb();
        zzbsVar.zzc = zzdxVarZzb;
        return zzdxVarZzb;
    }

    static <T> zzda<T> zzk(Class<T> cls, zzcu zzcuVar, zzdc zzdcVar, zzcl zzclVar, zzdw<?, ?> zzdwVar, zzbh<?> zzbhVar, zzcs zzcsVar) {
        if (zzcuVar instanceof zzdh) {
            return zzl((zzdh) zzcuVar, zzdcVar, zzclVar, zzdwVar, zzbhVar, zzcsVar);
        }
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:123:0x025e  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0261  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0279  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0378  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0391  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static <T> com.google.android.gms.internal.wearable.zzda<T> zzl(com.google.android.gms.internal.wearable.zzdh r34, com.google.android.gms.internal.wearable.zzdc r35, com.google.android.gms.internal.wearable.zzcl r36, com.google.android.gms.internal.wearable.zzdw<?, ?> r37, com.google.android.gms.internal.wearable.zzbh<?> r38, com.google.android.gms.internal.wearable.zzcs r39) {
        /*
            Method dump skipped, instructions count: 1017
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.wearable.zzda.zzl(com.google.android.gms.internal.wearable.zzdh, com.google.android.gms.internal.wearable.zzdc, com.google.android.gms.internal.wearable.zzcl, com.google.android.gms.internal.wearable.zzdw, com.google.android.gms.internal.wearable.zzbh, com.google.android.gms.internal.wearable.zzcs):com.google.android.gms.internal.wearable.zzda");
    }

    private static Field zzn(Class<?> cls, String str) {
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
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name).length() + String.valueOf(string).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(string);
            throw new RuntimeException(sb.toString());
        }
    }

    private final void zzo(T t, T t2, int i) {
        long jZzA = zzA(i) & 1048575;
        if (zzK(t2, i)) {
            Object objZzn = zzeg.zzn(t, jZzA);
            Object objZzn2 = zzeg.zzn(t2, jZzA);
            if (objZzn != null && objZzn2 != null) {
                zzeg.zzo(t, jZzA, zzca.zzi(objZzn, objZzn2));
                zzL(t, i);
            } else if (objZzn2 != null) {
                zzeg.zzo(t, jZzA, objZzn2);
                zzL(t, i);
            }
        }
    }

    private final void zzp(T t, T t2, int i) {
        int iZzA = zzA(i);
        int i2 = this.zzc[i];
        long j = iZzA & 1048575;
        if (zzM(t2, i2, i)) {
            Object objZzn = zzM(t, i2, i) ? zzeg.zzn(t, j) : null;
            Object objZzn2 = zzeg.zzn(t2, j);
            if (objZzn != null && objZzn2 != null) {
                zzeg.zzo(t, j, zzca.zzi(objZzn, objZzn2));
                zzN(t, i2, i);
            } else if (objZzn2 != null) {
                zzeg.zzo(t, j, objZzn2);
                zzN(t, i2, i);
            }
        }
    }

    private final int zzq(T t) {
        int i;
        int iZzw;
        int iZzw2;
        int iZzw3;
        int iZzx;
        int iZzw4;
        int iZzv;
        int iZzw5;
        int iZzw6;
        int iZzc;
        int iZzw7;
        int iZzw8;
        int iZzu;
        int iZzw9;
        int i2;
        Unsafe unsafe = zzb;
        int i3 = 0;
        int i4 = 0;
        int i5 = 1048575;
        for (int i6 = 0; i6 < this.zzc.length; i6 += 3) {
            int iZzA = zzA(i6);
            int i7 = this.zzc[i6];
            int iZzC = zzC(iZzA);
            if (iZzC <= 17) {
                int i8 = this.zzc[i6 + 2];
                int i9 = i8 & 1048575;
                i = 1 << (i8 >>> 20);
                if (i9 != i5) {
                    i4 = unsafe.getInt(t, i9);
                    i5 = i9;
                }
            } else {
                i = 0;
            }
            long j = iZzA & 1048575;
            switch (iZzC) {
                case 0:
                    if ((i4 & i) != 0) {
                        iZzw = zzbb.zzw(i7 << 3);
                        iZzw8 = iZzw + 8;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if ((i4 & i) != 0) {
                        iZzw2 = zzbb.zzw(i7 << 3);
                        iZzw8 = iZzw2 + 4;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if ((i4 & i) != 0) {
                        long j2 = unsafe.getLong(t, j);
                        iZzw3 = zzbb.zzw(i7 << 3);
                        iZzx = zzbb.zzx(j2);
                        iZzw8 = iZzw3 + iZzx;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if ((i4 & i) != 0) {
                        long j3 = unsafe.getLong(t, j);
                        iZzw3 = zzbb.zzw(i7 << 3);
                        iZzx = zzbb.zzx(j3);
                        iZzw8 = iZzw3 + iZzx;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if ((i4 & i) != 0) {
                        int i10 = unsafe.getInt(t, j);
                        iZzw4 = zzbb.zzw(i7 << 3);
                        iZzv = zzbb.zzv(i10);
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if ((i4 & i) != 0) {
                        iZzw = zzbb.zzw(i7 << 3);
                        iZzw8 = iZzw + 8;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if ((i4 & i) != 0) {
                        iZzw2 = zzbb.zzw(i7 << 3);
                        iZzw8 = iZzw2 + 4;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if ((i4 & i) != 0) {
                        iZzw5 = zzbb.zzw(i7 << 3);
                        iZzw8 = iZzw5 + 1;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if ((i4 & i) == 0) {
                        break;
                    } else {
                        Object object = unsafe.getObject(t, j);
                        if (object instanceof zzau) {
                            iZzw6 = zzbb.zzw(i7 << 3);
                            iZzc = ((zzau) object).zzc();
                            iZzw7 = zzbb.zzw(iZzc);
                            i2 = iZzw6 + iZzw7 + iZzc;
                            i3 += i2;
                            break;
                        } else {
                            iZzw4 = zzbb.zzw(i7 << 3);
                            iZzv = zzbb.zzy((String) object);
                            i2 = iZzw4 + iZzv;
                            i3 += i2;
                        }
                    }
                case 9:
                    if ((i4 & i) != 0) {
                        iZzw8 = zzdk.zzw(i7, unsafe.getObject(t, j), zzv(i6));
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if ((i4 & i) != 0) {
                        zzau zzauVar = (zzau) unsafe.getObject(t, j);
                        iZzw6 = zzbb.zzw(i7 << 3);
                        iZzc = zzauVar.zzc();
                        iZzw7 = zzbb.zzw(iZzc);
                        i2 = iZzw6 + iZzw7 + iZzc;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if ((i4 & i) != 0) {
                        int i11 = unsafe.getInt(t, j);
                        iZzw4 = zzbb.zzw(i7 << 3);
                        iZzv = zzbb.zzw(i11);
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if ((i4 & i) != 0) {
                        int i12 = unsafe.getInt(t, j);
                        iZzw4 = zzbb.zzw(i7 << 3);
                        iZzv = zzbb.zzv(i12);
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if ((i4 & i) != 0) {
                        iZzw2 = zzbb.zzw(i7 << 3);
                        iZzw8 = iZzw2 + 4;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if ((i4 & i) != 0) {
                        iZzw = zzbb.zzw(i7 << 3);
                        iZzw8 = iZzw + 8;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if ((i4 & i) != 0) {
                        int i13 = unsafe.getInt(t, j);
                        iZzw4 = zzbb.zzw(i7 << 3);
                        iZzv = zzbb.zzw((i13 >> 31) ^ (i13 + i13));
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if ((i4 & i) != 0) {
                        long j4 = unsafe.getLong(t, j);
                        iZzw4 = zzbb.zzw(i7 << 3);
                        iZzv = zzbb.zzx((j4 >> 63) ^ (j4 + j4));
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if ((i4 & i) != 0) {
                        iZzw8 = zzbb.zzE(i7, (zzcx) unsafe.getObject(t, j), zzv(i6));
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 18:
                    iZzw8 = zzdk.zzs(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 19:
                    iZzw8 = zzdk.zzq(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 20:
                    iZzw8 = zzdk.zzc(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 21:
                    iZzw8 = zzdk.zze(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 22:
                    iZzw8 = zzdk.zzk(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 23:
                    iZzw8 = zzdk.zzs(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 24:
                    iZzw8 = zzdk.zzq(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 25:
                    iZzw8 = zzdk.zzu(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 26:
                    iZzw8 = zzdk.zzv(i7, (List) unsafe.getObject(t, j));
                    i3 += iZzw8;
                    break;
                case 27:
                    iZzw8 = zzdk.zzx(i7, (List) unsafe.getObject(t, j), zzv(i6));
                    i3 += iZzw8;
                    break;
                case 28:
                    iZzw8 = zzdk.zzy(i7, (List) unsafe.getObject(t, j));
                    i3 += iZzw8;
                    break;
                case 29:
                    iZzw8 = zzdk.zzm(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 30:
                    iZzw8 = zzdk.zzi(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 31:
                    iZzw8 = zzdk.zzq(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 32:
                    iZzw8 = zzdk.zzs(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 33:
                    iZzw8 = zzdk.zzo(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 34:
                    iZzw8 = zzdk.zzg(i7, (List) unsafe.getObject(t, j), false);
                    i3 += iZzw8;
                    break;
                case 35:
                    iZzv = zzdk.zzr((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i7);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 36:
                    iZzv = zzdk.zzp((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i7);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 37:
                    iZzv = zzdk.zzb((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i7);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 38:
                    iZzv = zzdk.zzd((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i7);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 39:
                    iZzv = zzdk.zzj((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i7);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 40:
                    iZzv = zzdk.zzr((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i7);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 41:
                    iZzv = zzdk.zzp((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i7);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 42:
                    iZzv = zzdk.zzt((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i7);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 43:
                    iZzv = zzdk.zzl((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i7);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 44:
                    iZzv = zzdk.zzh((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i7);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 45:
                    iZzv = zzdk.zzp((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i7);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 46:
                    iZzv = zzdk.zzr((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i7);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 47:
                    iZzv = zzdk.zzn((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i7);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 48:
                    iZzv = zzdk.zzf((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i7);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 49:
                    iZzw8 = zzdk.zzz(i7, (List) unsafe.getObject(t, j), zzv(i6));
                    i3 += iZzw8;
                    break;
                case 50:
                    zzcs.zza(i7, unsafe.getObject(t, j), zzw(i6));
                    break;
                case 51:
                    if (zzM(t, i7, i6)) {
                        iZzw = zzbb.zzw(i7 << 3);
                        iZzw8 = iZzw + 8;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzM(t, i7, i6)) {
                        iZzw2 = zzbb.zzw(i7 << 3);
                        iZzw8 = iZzw2 + 4;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzM(t, i7, i6)) {
                        long jZzG = zzG(t, j);
                        iZzw3 = zzbb.zzw(i7 << 3);
                        iZzx = zzbb.zzx(jZzG);
                        iZzw8 = iZzw3 + iZzx;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzM(t, i7, i6)) {
                        long jZzG2 = zzG(t, j);
                        iZzw3 = zzbb.zzw(i7 << 3);
                        iZzx = zzbb.zzx(jZzG2);
                        iZzw8 = iZzw3 + iZzx;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzM(t, i7, i6)) {
                        int iZzF = zzF(t, j);
                        iZzw4 = zzbb.zzw(i7 << 3);
                        iZzv = zzbb.zzv(iZzF);
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzM(t, i7, i6)) {
                        iZzw = zzbb.zzw(i7 << 3);
                        iZzw8 = iZzw + 8;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzM(t, i7, i6)) {
                        iZzw2 = zzbb.zzw(i7 << 3);
                        iZzw8 = iZzw2 + 4;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzM(t, i7, i6)) {
                        iZzw5 = zzbb.zzw(i7 << 3);
                        iZzw8 = iZzw5 + 1;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (!zzM(t, i7, i6)) {
                        break;
                    } else {
                        Object object2 = unsafe.getObject(t, j);
                        if (object2 instanceof zzau) {
                            iZzw6 = zzbb.zzw(i7 << 3);
                            iZzc = ((zzau) object2).zzc();
                            iZzw7 = zzbb.zzw(iZzc);
                            i2 = iZzw6 + iZzw7 + iZzc;
                            i3 += i2;
                            break;
                        } else {
                            iZzw4 = zzbb.zzw(i7 << 3);
                            iZzv = zzbb.zzy((String) object2);
                            i2 = iZzw4 + iZzv;
                            i3 += i2;
                        }
                    }
                case 60:
                    if (zzM(t, i7, i6)) {
                        iZzw8 = zzdk.zzw(i7, unsafe.getObject(t, j), zzv(i6));
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzM(t, i7, i6)) {
                        zzau zzauVar2 = (zzau) unsafe.getObject(t, j);
                        iZzw6 = zzbb.zzw(i7 << 3);
                        iZzc = zzauVar2.zzc();
                        iZzw7 = zzbb.zzw(iZzc);
                        i2 = iZzw6 + iZzw7 + iZzc;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzM(t, i7, i6)) {
                        int iZzF2 = zzF(t, j);
                        iZzw4 = zzbb.zzw(i7 << 3);
                        iZzv = zzbb.zzw(iZzF2);
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzM(t, i7, i6)) {
                        int iZzF3 = zzF(t, j);
                        iZzw4 = zzbb.zzw(i7 << 3);
                        iZzv = zzbb.zzv(iZzF3);
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzM(t, i7, i6)) {
                        iZzw2 = zzbb.zzw(i7 << 3);
                        iZzw8 = iZzw2 + 4;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzM(t, i7, i6)) {
                        iZzw = zzbb.zzw(i7 << 3);
                        iZzw8 = iZzw + 8;
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzM(t, i7, i6)) {
                        int iZzF4 = zzF(t, j);
                        iZzw4 = zzbb.zzw(i7 << 3);
                        iZzv = zzbb.zzw((iZzF4 >> 31) ^ (iZzF4 + iZzF4));
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzM(t, i7, i6)) {
                        long jZzG3 = zzG(t, j);
                        iZzw4 = zzbb.zzw(i7 << 3);
                        iZzv = zzbb.zzx((jZzG3 >> 63) ^ (jZzG3 + jZzG3));
                        i2 = iZzw4 + iZzv;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzM(t, i7, i6)) {
                        iZzw8 = zzbb.zzE(i7, (zzcx) unsafe.getObject(t, j), zzv(i6));
                        i3 += iZzw8;
                        break;
                    } else {
                        break;
                    }
            }
        }
        zzdw<?, ?> zzdwVar = this.zzn;
        int iZzh = i3 + zzdwVar.zzh(zzdwVar.zzd(t));
        if (!this.zzh) {
            return iZzh;
        }
        this.zzo.zzb(t);
        throw null;
    }

    private final int zzr(T t) {
        int iZzw;
        int iZzw2;
        int iZzw3;
        int iZzx;
        int iZzw4;
        int iZzv;
        int iZzw5;
        int iZzw6;
        int iZzc;
        int iZzw7;
        int iZzw8;
        int iZzu;
        int iZzw9;
        int i;
        Unsafe unsafe = zzb;
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzc.length; i3 += 3) {
            int iZzA = zzA(i3);
            int iZzC = zzC(iZzA);
            int i4 = this.zzc[i3];
            long j = iZzA & 1048575;
            if (iZzC >= zzbm.DOUBLE_LIST_PACKED.zza() && iZzC <= zzbm.SINT64_LIST_PACKED.zza()) {
                int i5 = this.zzc[i3 + 2];
            }
            switch (iZzC) {
                case 0:
                    if (zzK(t, i3)) {
                        iZzw = zzbb.zzw(i4 << 3);
                        iZzw8 = iZzw + 8;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzK(t, i3)) {
                        iZzw2 = zzbb.zzw(i4 << 3);
                        iZzw8 = iZzw2 + 4;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzK(t, i3)) {
                        long jZzf = zzeg.zzf(t, j);
                        iZzw3 = zzbb.zzw(i4 << 3);
                        iZzx = zzbb.zzx(jZzf);
                        i2 += iZzw3 + iZzx;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzK(t, i3)) {
                        long jZzf2 = zzeg.zzf(t, j);
                        iZzw3 = zzbb.zzw(i4 << 3);
                        iZzx = zzbb.zzx(jZzf2);
                        i2 += iZzw3 + iZzx;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzK(t, i3)) {
                        int iZzd = zzeg.zzd(t, j);
                        iZzw4 = zzbb.zzw(i4 << 3);
                        iZzv = zzbb.zzv(iZzd);
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzK(t, i3)) {
                        iZzw = zzbb.zzw(i4 << 3);
                        iZzw8 = iZzw + 8;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzK(t, i3)) {
                        iZzw2 = zzbb.zzw(i4 << 3);
                        iZzw8 = iZzw2 + 4;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzK(t, i3)) {
                        iZzw5 = zzbb.zzw(i4 << 3);
                        iZzw8 = iZzw5 + 1;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (!zzK(t, i3)) {
                        break;
                    } else {
                        Object objZzn = zzeg.zzn(t, j);
                        if (objZzn instanceof zzau) {
                            iZzw6 = zzbb.zzw(i4 << 3);
                            iZzc = ((zzau) objZzn).zzc();
                            iZzw7 = zzbb.zzw(iZzc);
                            i = iZzw6 + iZzw7 + iZzc;
                            i2 += i;
                            break;
                        } else {
                            iZzw4 = zzbb.zzw(i4 << 3);
                            iZzv = zzbb.zzy((String) objZzn);
                            i = iZzw4 + iZzv;
                            i2 += i;
                        }
                    }
                case 9:
                    if (zzK(t, i3)) {
                        iZzw8 = zzdk.zzw(i4, zzeg.zzn(t, j), zzv(i3));
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if (zzK(t, i3)) {
                        zzau zzauVar = (zzau) zzeg.zzn(t, j);
                        iZzw6 = zzbb.zzw(i4 << 3);
                        iZzc = zzauVar.zzc();
                        iZzw7 = zzbb.zzw(iZzc);
                        i = iZzw6 + iZzw7 + iZzc;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzK(t, i3)) {
                        int iZzd2 = zzeg.zzd(t, j);
                        iZzw4 = zzbb.zzw(i4 << 3);
                        iZzv = zzbb.zzw(iZzd2);
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzK(t, i3)) {
                        int iZzd3 = zzeg.zzd(t, j);
                        iZzw4 = zzbb.zzw(i4 << 3);
                        iZzv = zzbb.zzv(iZzd3);
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzK(t, i3)) {
                        iZzw2 = zzbb.zzw(i4 << 3);
                        iZzw8 = iZzw2 + 4;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzK(t, i3)) {
                        iZzw = zzbb.zzw(i4 << 3);
                        iZzw8 = iZzw + 8;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzK(t, i3)) {
                        int iZzd4 = zzeg.zzd(t, j);
                        iZzw4 = zzbb.zzw(i4 << 3);
                        iZzv = zzbb.zzw((iZzd4 >> 31) ^ (iZzd4 + iZzd4));
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzK(t, i3)) {
                        long jZzf3 = zzeg.zzf(t, j);
                        iZzw4 = zzbb.zzw(i4 << 3);
                        iZzv = zzbb.zzx((jZzf3 >> 63) ^ (jZzf3 + jZzf3));
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if (zzK(t, i3)) {
                        iZzw8 = zzbb.zzE(i4, (zzcx) zzeg.zzn(t, j), zzv(i3));
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 18:
                    iZzw8 = zzdk.zzs(i4, (List) zzeg.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 19:
                    iZzw8 = zzdk.zzq(i4, (List) zzeg.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 20:
                    iZzw8 = zzdk.zzc(i4, (List) zzeg.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 21:
                    iZzw8 = zzdk.zze(i4, (List) zzeg.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 22:
                    iZzw8 = zzdk.zzk(i4, (List) zzeg.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 23:
                    iZzw8 = zzdk.zzs(i4, (List) zzeg.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 24:
                    iZzw8 = zzdk.zzq(i4, (List) zzeg.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 25:
                    iZzw8 = zzdk.zzu(i4, (List) zzeg.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 26:
                    iZzw8 = zzdk.zzv(i4, (List) zzeg.zzn(t, j));
                    i2 += iZzw8;
                    break;
                case 27:
                    iZzw8 = zzdk.zzx(i4, (List) zzeg.zzn(t, j), zzv(i3));
                    i2 += iZzw8;
                    break;
                case 28:
                    iZzw8 = zzdk.zzy(i4, (List) zzeg.zzn(t, j));
                    i2 += iZzw8;
                    break;
                case 29:
                    iZzw8 = zzdk.zzm(i4, (List) zzeg.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 30:
                    iZzw8 = zzdk.zzi(i4, (List) zzeg.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 31:
                    iZzw8 = zzdk.zzq(i4, (List) zzeg.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 32:
                    iZzw8 = zzdk.zzs(i4, (List) zzeg.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 33:
                    iZzw8 = zzdk.zzo(i4, (List) zzeg.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 34:
                    iZzw8 = zzdk.zzg(i4, (List) zzeg.zzn(t, j), false);
                    i2 += iZzw8;
                    break;
                case 35:
                    iZzv = zzdk.zzr((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i4);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 36:
                    iZzv = zzdk.zzp((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i4);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 37:
                    iZzv = zzdk.zzb((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i4);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 38:
                    iZzv = zzdk.zzd((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i4);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 39:
                    iZzv = zzdk.zzj((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i4);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 40:
                    iZzv = zzdk.zzr((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i4);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 41:
                    iZzv = zzdk.zzp((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i4);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 42:
                    iZzv = zzdk.zzt((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i4);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 43:
                    iZzv = zzdk.zzl((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i4);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 44:
                    iZzv = zzdk.zzh((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i4);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 45:
                    iZzv = zzdk.zzp((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i4);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 46:
                    iZzv = zzdk.zzr((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i4);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 47:
                    iZzv = zzdk.zzn((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i4);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 48:
                    iZzv = zzdk.zzf((List) unsafe.getObject(t, j));
                    if (iZzv > 0) {
                        iZzu = zzbb.zzu(i4);
                        iZzw9 = zzbb.zzw(iZzv);
                        iZzw4 = iZzu + iZzw9;
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 49:
                    iZzw8 = zzdk.zzz(i4, (List) zzeg.zzn(t, j), zzv(i3));
                    i2 += iZzw8;
                    break;
                case 50:
                    zzcs.zza(i4, zzeg.zzn(t, j), zzw(i3));
                    break;
                case 51:
                    if (zzM(t, i4, i3)) {
                        iZzw = zzbb.zzw(i4 << 3);
                        iZzw8 = iZzw + 8;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzM(t, i4, i3)) {
                        iZzw2 = zzbb.zzw(i4 << 3);
                        iZzw8 = iZzw2 + 4;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzM(t, i4, i3)) {
                        long jZzG = zzG(t, j);
                        iZzw3 = zzbb.zzw(i4 << 3);
                        iZzx = zzbb.zzx(jZzG);
                        i2 += iZzw3 + iZzx;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzM(t, i4, i3)) {
                        long jZzG2 = zzG(t, j);
                        iZzw3 = zzbb.zzw(i4 << 3);
                        iZzx = zzbb.zzx(jZzG2);
                        i2 += iZzw3 + iZzx;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzM(t, i4, i3)) {
                        int iZzF = zzF(t, j);
                        iZzw4 = zzbb.zzw(i4 << 3);
                        iZzv = zzbb.zzv(iZzF);
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzM(t, i4, i3)) {
                        iZzw = zzbb.zzw(i4 << 3);
                        iZzw8 = iZzw + 8;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzM(t, i4, i3)) {
                        iZzw2 = zzbb.zzw(i4 << 3);
                        iZzw8 = iZzw2 + 4;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzM(t, i4, i3)) {
                        iZzw5 = zzbb.zzw(i4 << 3);
                        iZzw8 = iZzw5 + 1;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (!zzM(t, i4, i3)) {
                        break;
                    } else {
                        Object objZzn2 = zzeg.zzn(t, j);
                        if (objZzn2 instanceof zzau) {
                            iZzw6 = zzbb.zzw(i4 << 3);
                            iZzc = ((zzau) objZzn2).zzc();
                            iZzw7 = zzbb.zzw(iZzc);
                            i = iZzw6 + iZzw7 + iZzc;
                            i2 += i;
                            break;
                        } else {
                            iZzw4 = zzbb.zzw(i4 << 3);
                            iZzv = zzbb.zzy((String) objZzn2);
                            i = iZzw4 + iZzv;
                            i2 += i;
                        }
                    }
                case 60:
                    if (zzM(t, i4, i3)) {
                        iZzw8 = zzdk.zzw(i4, zzeg.zzn(t, j), zzv(i3));
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzM(t, i4, i3)) {
                        zzau zzauVar2 = (zzau) zzeg.zzn(t, j);
                        iZzw6 = zzbb.zzw(i4 << 3);
                        iZzc = zzauVar2.zzc();
                        iZzw7 = zzbb.zzw(iZzc);
                        i = iZzw6 + iZzw7 + iZzc;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzM(t, i4, i3)) {
                        int iZzF2 = zzF(t, j);
                        iZzw4 = zzbb.zzw(i4 << 3);
                        iZzv = zzbb.zzw(iZzF2);
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzM(t, i4, i3)) {
                        int iZzF3 = zzF(t, j);
                        iZzw4 = zzbb.zzw(i4 << 3);
                        iZzv = zzbb.zzv(iZzF3);
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzM(t, i4, i3)) {
                        iZzw2 = zzbb.zzw(i4 << 3);
                        iZzw8 = iZzw2 + 4;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzM(t, i4, i3)) {
                        iZzw = zzbb.zzw(i4 << 3);
                        iZzw8 = iZzw + 8;
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzM(t, i4, i3)) {
                        int iZzF4 = zzF(t, j);
                        iZzw4 = zzbb.zzw(i4 << 3);
                        iZzv = zzbb.zzw((iZzF4 >> 31) ^ (iZzF4 + iZzF4));
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzM(t, i4, i3)) {
                        long jZzG3 = zzG(t, j);
                        iZzw4 = zzbb.zzw(i4 << 3);
                        iZzv = zzbb.zzx((jZzG3 >> 63) ^ (jZzG3 + jZzG3));
                        i = iZzw4 + iZzv;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzM(t, i4, i3)) {
                        iZzw8 = zzbb.zzE(i4, (zzcx) zzeg.zzn(t, j), zzv(i3));
                        i2 += iZzw8;
                        break;
                    } else {
                        break;
                    }
            }
        }
        zzdw<?, ?> zzdwVar = this.zzn;
        return i2 + zzdwVar.zzh(zzdwVar.zzd(t));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final int zzs(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzai zzaiVar) throws IOException {
        int i8;
        int i9;
        int i10;
        int i11;
        int iZza;
        int iZza2 = i;
        Unsafe unsafe = zzb;
        zzbz zzbzVarZze = (zzbz) unsafe.getObject(t, j2);
        if (!zzbzVarZze.zza()) {
            int size = zzbzVarZze.size();
            zzbzVarZze = zzbzVarZze.zze(size == 0 ? 10 : size + size);
            unsafe.putObject(t, j2, zzbzVarZze);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    zzbd zzbdVar = (zzbd) zzbzVarZze;
                    int iZza3 = zzaj.zza(bArr, iZza2, zzaiVar);
                    int i12 = zzaiVar.zza + iZza3;
                    while (iZza3 < i12) {
                        zzbdVar.zzd(Double.longBitsToDouble(zzaj.zze(bArr, iZza3)));
                        iZza3 += 8;
                    }
                    if (iZza3 == i12) {
                        return iZza3;
                    }
                    throw zzcc.zzb();
                }
                if (i5 == 1) {
                    zzbd zzbdVar2 = (zzbd) zzbzVarZze;
                    zzbdVar2.zzd(Double.longBitsToDouble(zzaj.zze(bArr, i)));
                    while (true) {
                        i8 = iZza2 + 8;
                        if (i8 < i2) {
                            iZza2 = zzaj.zza(bArr, i8, zzaiVar);
                            if (i3 == zzaiVar.zza) {
                                zzbdVar2.zzd(Double.longBitsToDouble(zzaj.zze(bArr, iZza2)));
                            }
                        }
                    }
                    return i8;
                }
                return iZza2;
            case 19:
            case 36:
                if (i5 == 2) {
                    zzbn zzbnVar = (zzbn) zzbzVarZze;
                    int iZza4 = zzaj.zza(bArr, iZza2, zzaiVar);
                    int i13 = zzaiVar.zza + iZza4;
                    while (iZza4 < i13) {
                        zzbnVar.zzg(Float.intBitsToFloat(zzaj.zzd(bArr, iZza4)));
                        iZza4 += 4;
                    }
                    if (iZza4 == i13) {
                        return iZza4;
                    }
                    throw zzcc.zzb();
                }
                if (i5 == 5) {
                    zzbn zzbnVar2 = (zzbn) zzbzVarZze;
                    zzbnVar2.zzg(Float.intBitsToFloat(zzaj.zzd(bArr, i)));
                    while (true) {
                        i9 = iZza2 + 4;
                        if (i9 < i2) {
                            iZza2 = zzaj.zza(bArr, i9, zzaiVar);
                            if (i3 == zzaiVar.zza) {
                                zzbnVar2.zzg(Float.intBitsToFloat(zzaj.zzd(bArr, iZza2)));
                            }
                        }
                    }
                    return i9;
                }
                return iZza2;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    zzcm zzcmVar = (zzcm) zzbzVarZze;
                    int iZza5 = zzaj.zza(bArr, iZza2, zzaiVar);
                    int i14 = zzaiVar.zza + iZza5;
                    while (iZza5 < i14) {
                        iZza5 = zzaj.zzc(bArr, iZza5, zzaiVar);
                        zzcmVar.zzg(zzaiVar.zzb);
                    }
                    if (iZza5 == i14) {
                        return iZza5;
                    }
                    throw zzcc.zzb();
                }
                if (i5 == 0) {
                    zzcm zzcmVar2 = (zzcm) zzbzVarZze;
                    int iZzc = zzaj.zzc(bArr, iZza2, zzaiVar);
                    zzcmVar2.zzg(zzaiVar.zzb);
                    while (iZzc < i2) {
                        int iZza6 = zzaj.zza(bArr, iZzc, zzaiVar);
                        if (i3 != zzaiVar.zza) {
                            return iZzc;
                        }
                        iZzc = zzaj.zzc(bArr, iZza6, zzaiVar);
                        zzcmVar2.zzg(zzaiVar.zzb);
                    }
                    return iZzc;
                }
                return iZza2;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return zzaj.zzl(bArr, iZza2, zzbzVarZze, zzaiVar);
                }
                if (i5 == 0) {
                    return zzaj.zzk(i3, bArr, i, i2, zzbzVarZze, zzaiVar);
                }
                return iZza2;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    zzcm zzcmVar3 = (zzcm) zzbzVarZze;
                    int iZza7 = zzaj.zza(bArr, iZza2, zzaiVar);
                    int i15 = zzaiVar.zza + iZza7;
                    while (iZza7 < i15) {
                        zzcmVar3.zzg(zzaj.zze(bArr, iZza7));
                        iZza7 += 8;
                    }
                    if (iZza7 == i15) {
                        return iZza7;
                    }
                    throw zzcc.zzb();
                }
                if (i5 == 1) {
                    zzcm zzcmVar4 = (zzcm) zzbzVarZze;
                    zzcmVar4.zzg(zzaj.zze(bArr, i));
                    while (true) {
                        i10 = iZza2 + 8;
                        if (i10 < i2) {
                            iZza2 = zzaj.zza(bArr, i10, zzaiVar);
                            if (i3 == zzaiVar.zza) {
                                zzcmVar4.zzg(zzaj.zze(bArr, iZza2));
                            }
                        }
                    }
                    return i10;
                }
                return iZza2;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    zzbt zzbtVar = (zzbt) zzbzVarZze;
                    int iZza8 = zzaj.zza(bArr, iZza2, zzaiVar);
                    int i16 = zzaiVar.zza + iZza8;
                    while (iZza8 < i16) {
                        zzbtVar.zzf(zzaj.zzd(bArr, iZza8));
                        iZza8 += 4;
                    }
                    if (iZza8 == i16) {
                        return iZza8;
                    }
                    throw zzcc.zzb();
                }
                if (i5 == 5) {
                    zzbt zzbtVar2 = (zzbt) zzbzVarZze;
                    zzbtVar2.zzf(zzaj.zzd(bArr, i));
                    while (true) {
                        i11 = iZza2 + 4;
                        if (i11 < i2) {
                            iZza2 = zzaj.zza(bArr, i11, zzaiVar);
                            if (i3 == zzaiVar.zza) {
                                zzbtVar2.zzf(zzaj.zzd(bArr, iZza2));
                            }
                        }
                    }
                    return i11;
                }
                return iZza2;
            case 25:
            case 42:
                if (i5 == 2) {
                    zzak zzakVar = (zzak) zzbzVarZze;
                    iZza = zzaj.zza(bArr, iZza2, zzaiVar);
                    int i17 = zzaiVar.zza + iZza;
                    while (iZza < i17) {
                        iZza = zzaj.zzc(bArr, iZza, zzaiVar);
                        zzakVar.zzd(zzaiVar.zzb != 0);
                    }
                    if (iZza != i17) {
                        throw zzcc.zzb();
                    }
                    return iZza;
                }
                if (i5 == 0) {
                    zzak zzakVar2 = (zzak) zzbzVarZze;
                    int iZzc2 = zzaj.zzc(bArr, iZza2, zzaiVar);
                    zzakVar2.zzd(zzaiVar.zzb != 0);
                    while (iZzc2 < i2) {
                        int iZza9 = zzaj.zza(bArr, iZzc2, zzaiVar);
                        if (i3 != zzaiVar.zza) {
                            return iZzc2;
                        }
                        iZzc2 = zzaj.zzc(bArr, iZza9, zzaiVar);
                        zzakVar2.zzd(zzaiVar.zzb != 0);
                    }
                    return iZzc2;
                }
                return iZza2;
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) == 0) {
                        int iZza10 = zzaj.zza(bArr, iZza2, zzaiVar);
                        int i18 = zzaiVar.zza;
                        if (i18 < 0) {
                            throw zzcc.zzc();
                        }
                        if (i18 == 0) {
                            zzbzVarZze.add("");
                        } else {
                            zzbzVarZze.add(new String(bArr, iZza10, i18, zzca.zza));
                            iZza10 += i18;
                        }
                        while (iZza10 < i2) {
                            int iZza11 = zzaj.zza(bArr, iZza10, zzaiVar);
                            if (i3 != zzaiVar.zza) {
                                return iZza10;
                            }
                            iZza10 = zzaj.zza(bArr, iZza11, zzaiVar);
                            int i19 = zzaiVar.zza;
                            if (i19 < 0) {
                                throw zzcc.zzc();
                            }
                            if (i19 == 0) {
                                zzbzVarZze.add("");
                            } else {
                                zzbzVarZze.add(new String(bArr, iZza10, i19, zzca.zza));
                                iZza10 += i19;
                            }
                        }
                        return iZza10;
                    }
                    int iZza12 = zzaj.zza(bArr, iZza2, zzaiVar);
                    int i20 = zzaiVar.zza;
                    if (i20 < 0) {
                        throw zzcc.zzc();
                    }
                    if (i20 == 0) {
                        zzbzVarZze.add("");
                    } else {
                        int i21 = iZza12 + i20;
                        if (!zzel.zzb(bArr, iZza12, i21)) {
                            throw zzcc.zzg();
                        }
                        zzbzVarZze.add(new String(bArr, iZza12, i20, zzca.zza));
                        iZza12 = i21;
                    }
                    while (iZza12 < i2) {
                        int iZza13 = zzaj.zza(bArr, iZza12, zzaiVar);
                        if (i3 != zzaiVar.zza) {
                            return iZza12;
                        }
                        iZza12 = zzaj.zza(bArr, iZza13, zzaiVar);
                        int i22 = zzaiVar.zza;
                        if (i22 < 0) {
                            throw zzcc.zzc();
                        }
                        if (i22 == 0) {
                            zzbzVarZze.add("");
                        } else {
                            int i23 = iZza12 + i22;
                            if (!zzel.zzb(bArr, iZza12, i23)) {
                                throw zzcc.zzg();
                            }
                            zzbzVarZze.add(new String(bArr, iZza12, i22, zzca.zza));
                            iZza12 = i23;
                        }
                    }
                    return iZza12;
                }
                return iZza2;
            case 27:
                if (i5 == 2) {
                    return zzaj.zzm(zzv(i6), i3, bArr, i, i2, zzbzVarZze, zzaiVar);
                }
                return iZza2;
            case 28:
                if (i5 == 2) {
                    int iZza14 = zzaj.zza(bArr, iZza2, zzaiVar);
                    int i24 = zzaiVar.zza;
                    if (i24 < 0) {
                        throw zzcc.zzc();
                    }
                    if (i24 > bArr.length - iZza14) {
                        throw zzcc.zzb();
                    }
                    if (i24 == 0) {
                        zzbzVarZze.add(zzau.zzb);
                    } else {
                        zzbzVarZze.add(zzau.zzk(bArr, iZza14, i24));
                        iZza14 += i24;
                    }
                    while (iZza14 < i2) {
                        int iZza15 = zzaj.zza(bArr, iZza14, zzaiVar);
                        if (i3 != zzaiVar.zza) {
                            return iZza14;
                        }
                        iZza14 = zzaj.zza(bArr, iZza15, zzaiVar);
                        int i25 = zzaiVar.zza;
                        if (i25 < 0) {
                            throw zzcc.zzc();
                        }
                        if (i25 > bArr.length - iZza14) {
                            throw zzcc.zzb();
                        }
                        if (i25 == 0) {
                            zzbzVarZze.add(zzau.zzb);
                        } else {
                            zzbzVarZze.add(zzau.zzk(bArr, iZza14, i25));
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
                        iZza = zzaj.zzk(i3, bArr, i, i2, zzbzVarZze, zzaiVar);
                    }
                    return iZza2;
                }
                iZza = zzaj.zzl(bArr, iZza2, zzbzVarZze, zzaiVar);
                zzbs zzbsVar = (zzbs) t;
                zzdx zzdxVar = zzbsVar.zzc;
                if (zzdxVar == zzdx.zza()) {
                    zzdxVar = null;
                }
                Object objZzG = zzdk.zzG(i4, zzbzVarZze, zzx(i6), zzdxVar, this.zzn);
                if (objZzG != null) {
                    zzbsVar.zzc = (zzdx) objZzG;
                    return iZza;
                }
                return iZza;
            case 33:
            case 47:
                if (i5 == 2) {
                    zzbt zzbtVar3 = (zzbt) zzbzVarZze;
                    int iZza16 = zzaj.zza(bArr, iZza2, zzaiVar);
                    int i26 = zzaiVar.zza + iZza16;
                    while (iZza16 < i26) {
                        iZza16 = zzaj.zza(bArr, iZza16, zzaiVar);
                        zzbtVar3.zzf(zzax.zzb(zzaiVar.zza));
                    }
                    if (iZza16 == i26) {
                        return iZza16;
                    }
                    throw zzcc.zzb();
                }
                if (i5 == 0) {
                    zzbt zzbtVar4 = (zzbt) zzbzVarZze;
                    int iZza17 = zzaj.zza(bArr, iZza2, zzaiVar);
                    zzbtVar4.zzf(zzax.zzb(zzaiVar.zza));
                    while (iZza17 < i2) {
                        int iZza18 = zzaj.zza(bArr, iZza17, zzaiVar);
                        if (i3 != zzaiVar.zza) {
                            return iZza17;
                        }
                        iZza17 = zzaj.zza(bArr, iZza18, zzaiVar);
                        zzbtVar4.zzf(zzax.zzb(zzaiVar.zza));
                    }
                    return iZza17;
                }
                return iZza2;
            case 34:
            case 48:
                if (i5 == 2) {
                    zzcm zzcmVar5 = (zzcm) zzbzVarZze;
                    int iZza19 = zzaj.zza(bArr, iZza2, zzaiVar);
                    int i27 = zzaiVar.zza + iZza19;
                    while (iZza19 < i27) {
                        iZza19 = zzaj.zzc(bArr, iZza19, zzaiVar);
                        zzcmVar5.zzg(zzax.zzc(zzaiVar.zzb));
                    }
                    if (iZza19 == i27) {
                        return iZza19;
                    }
                    throw zzcc.zzb();
                }
                if (i5 == 0) {
                    zzcm zzcmVar6 = (zzcm) zzbzVarZze;
                    int iZzc3 = zzaj.zzc(bArr, iZza2, zzaiVar);
                    zzcmVar6.zzg(zzax.zzc(zzaiVar.zzb));
                    while (iZzc3 < i2) {
                        int iZza20 = zzaj.zza(bArr, iZzc3, zzaiVar);
                        if (i3 != zzaiVar.zza) {
                            return iZzc3;
                        }
                        iZzc3 = zzaj.zzc(bArr, iZza20, zzaiVar);
                        zzcmVar6.zzg(zzax.zzc(zzaiVar.zzb));
                    }
                    return iZzc3;
                }
                return iZza2;
            default:
                if (i5 == 3) {
                    zzdi zzdiVarZzv = zzv(i6);
                    int i28 = (i3 & (-8)) | 4;
                    int iZzj = zzaj.zzj(zzdiVarZzv, bArr, i, i2, i28, zzaiVar);
                    zzbzVarZze.add(zzaiVar.zzc);
                    while (iZzj < i2) {
                        int iZza21 = zzaj.zza(bArr, iZzj, zzaiVar);
                        if (i3 != zzaiVar.zza) {
                            return iZzj;
                        }
                        iZzj = zzaj.zzj(zzdiVarZzv, bArr, iZza21, i2, i28, zzaiVar);
                        zzbzVarZze.add(zzaiVar.zzc);
                    }
                    return iZzj;
                }
                return iZza2;
        }
    }

    private final <K, V> int zzt(T t, byte[] bArr, int i, int i2, int i3, long j, zzai zzaiVar) throws IOException {
        Unsafe unsafe = zzb;
        Object objZzw = zzw(i3);
        Object object = unsafe.getObject(t, j);
        if (!((zzcr) object).zze()) {
            zzcr<K, V> zzcrVarZzc = zzcr.zza().zzc();
            zzcs.zzb(zzcrVarZzc, object);
            unsafe.putObject(t, j, zzcrVarZzc);
        }
        throw null;
    }

    private final int zzu(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzai zzaiVar) throws IOException {
        Unsafe unsafe = zzb;
        long j2 = this.zzc[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Double.valueOf(Double.longBitsToDouble(zzaj.zze(bArr, i))));
                    unsafe.putInt(t, j2, i4);
                    return i + 8;
                }
                break;
            case 52:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Float.valueOf(Float.intBitsToFloat(zzaj.zzd(bArr, i))));
                    unsafe.putInt(t, j2, i4);
                    return i + 4;
                }
                break;
            case 53:
            case 54:
                if (i5 == 0) {
                    int iZzc = zzaj.zzc(bArr, i, zzaiVar);
                    unsafe.putObject(t, j, Long.valueOf(zzaiVar.zzb));
                    unsafe.putInt(t, j2, i4);
                    return iZzc;
                }
                break;
            case 55:
            case 62:
                if (i5 == 0) {
                    int iZza = zzaj.zza(bArr, i, zzaiVar);
                    unsafe.putObject(t, j, Integer.valueOf(zzaiVar.zza));
                    unsafe.putInt(t, j2, i4);
                    return iZza;
                }
                break;
            case 56:
            case 65:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Long.valueOf(zzaj.zze(bArr, i)));
                    unsafe.putInt(t, j2, i4);
                    return i + 8;
                }
                break;
            case 57:
            case 64:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Integer.valueOf(zzaj.zzd(bArr, i)));
                    unsafe.putInt(t, j2, i4);
                    return i + 4;
                }
                break;
            case 58:
                if (i5 == 0) {
                    int iZzc2 = zzaj.zzc(bArr, i, zzaiVar);
                    unsafe.putObject(t, j, Boolean.valueOf(zzaiVar.zzb != 0));
                    unsafe.putInt(t, j2, i4);
                    return iZzc2;
                }
                break;
            case 59:
                if (i5 == 2) {
                    int iZza2 = zzaj.zza(bArr, i, zzaiVar);
                    int i9 = zzaiVar.zza;
                    if (i9 == 0) {
                        unsafe.putObject(t, j, "");
                    } else {
                        if ((i6 & 536870912) != 0 && !zzel.zzb(bArr, iZza2, iZza2 + i9)) {
                            throw zzcc.zzg();
                        }
                        unsafe.putObject(t, j, new String(bArr, iZza2, i9, zzca.zza));
                        iZza2 += i9;
                    }
                    unsafe.putInt(t, j2, i4);
                    return iZza2;
                }
                break;
            case 60:
                if (i5 == 2) {
                    int iZzi = zzaj.zzi(zzv(i8), bArr, i, i2, zzaiVar);
                    Object object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object == null) {
                        unsafe.putObject(t, j, zzaiVar.zzc);
                    } else {
                        unsafe.putObject(t, j, zzca.zzi(object, zzaiVar.zzc));
                    }
                    unsafe.putInt(t, j2, i4);
                    return iZzi;
                }
                break;
            case 61:
                if (i5 == 2) {
                    int iZzh = zzaj.zzh(bArr, i, zzaiVar);
                    unsafe.putObject(t, j, zzaiVar.zzc);
                    unsafe.putInt(t, j2, i4);
                    return iZzh;
                }
                break;
            case 63:
                if (i5 == 0) {
                    int iZza3 = zzaj.zza(bArr, i, zzaiVar);
                    int i10 = zzaiVar.zza;
                    zzbw zzbwVarZzx = zzx(i8);
                    if (zzbwVarZzx == null || zzbwVarZzx.zza(i10)) {
                        unsafe.putObject(t, j, Integer.valueOf(i10));
                        unsafe.putInt(t, j2, i4);
                    } else {
                        zzf(t).zzh(i3, Long.valueOf(i10));
                    }
                    return iZza3;
                }
                break;
            case 66:
                if (i5 == 0) {
                    int iZza4 = zzaj.zza(bArr, i, zzaiVar);
                    unsafe.putObject(t, j, Integer.valueOf(zzax.zzb(zzaiVar.zza)));
                    unsafe.putInt(t, j2, i4);
                    return iZza4;
                }
                break;
            case 67:
                if (i5 == 0) {
                    int iZzc3 = zzaj.zzc(bArr, i, zzaiVar);
                    unsafe.putObject(t, j, Long.valueOf(zzax.zzc(zzaiVar.zzb)));
                    unsafe.putInt(t, j2, i4);
                    return iZzc3;
                }
                break;
            case 68:
                if (i5 == 3) {
                    int iZzj = zzaj.zzj(zzv(i8), bArr, i, i2, (i3 & (-8)) | 4, zzaiVar);
                    Object object2 = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object2 == null) {
                        unsafe.putObject(t, j, zzaiVar.zzc);
                    } else {
                        unsafe.putObject(t, j, zzca.zzi(object2, zzaiVar.zzc));
                    }
                    unsafe.putInt(t, j2, i4);
                    return iZzj;
                }
                break;
        }
        return i;
    }

    private final zzdi zzv(int i) {
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzdi zzdiVar = (zzdi) this.zzd[i3];
        if (zzdiVar != null) {
            return zzdiVar;
        }
        zzdi<T> zzdiVarZzb = zzdf.zza().zzb((Class) this.zzd[i3 + 1]);
        this.zzd[i3] = zzdiVarZzb;
        return zzdiVarZzb;
    }

    private final Object zzw(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private final zzbw zzx(int i) {
        int i2 = i / 3;
        return (zzbw) this.zzd[i2 + i2 + 1];
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x02a1, code lost:
    
        if (r0 != r15) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x02a3, code lost:
    
        r15 = r29;
        r14 = r30;
        r12 = r31;
        r13 = r33;
        r11 = r34;
        r2 = r18;
        r1 = r19;
        r6 = r23;
        r7 = r26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x02b7, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x02e8, code lost:
    
        if (r0 != r15) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x030b, code lost:
    
        if (r0 != r15) goto L101;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:25:0x0085. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v14, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int zzy(T r30, byte[] r31, int r32, int r33, com.google.android.gms.internal.wearable.zzai r34) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 886
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.wearable.zzda.zzy(java.lang.Object, byte[], int, int, com.google.android.gms.internal.wearable.zzai):int");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zzz(Object obj, int i, zzdi zzdiVar) {
        return zzdiVar.zzj(zzeg.zzn(obj, i & 1048575));
    }

    @Override // com.google.android.gms.internal.wearable.zzdi
    public final T zza() {
        return (T) ((zzbs) this.zzg).zzG(4, null, null);
    }

    @Override // com.google.android.gms.internal.wearable.zzdi
    public final boolean zzb(T t, T t2) {
        boolean zZzD;
        int length = this.zzc.length;
        for (int i = 0; i < length; i += 3) {
            int iZzA = zzA(i);
            long j = iZzA & 1048575;
            switch (zzC(iZzA)) {
                case 0:
                    if (!zzI(t, t2, i) || Double.doubleToLongBits(zzeg.zzl(t, j)) != Double.doubleToLongBits(zzeg.zzl(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 1:
                    if (!zzI(t, t2, i) || Float.floatToIntBits(zzeg.zzj(t, j)) != Float.floatToIntBits(zzeg.zzj(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 2:
                    if (!zzI(t, t2, i) || zzeg.zzf(t, j) != zzeg.zzf(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 3:
                    if (!zzI(t, t2, i) || zzeg.zzf(t, j) != zzeg.zzf(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 4:
                    if (!zzI(t, t2, i) || zzeg.zzd(t, j) != zzeg.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 5:
                    if (!zzI(t, t2, i) || zzeg.zzf(t, j) != zzeg.zzf(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 6:
                    if (!zzI(t, t2, i) || zzeg.zzd(t, j) != zzeg.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 7:
                    if (!zzI(t, t2, i) || zzeg.zzh(t, j) != zzeg.zzh(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 8:
                    if (!zzI(t, t2, i) || !zzdk.zzD(zzeg.zzn(t, j), zzeg.zzn(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 9:
                    if (!zzI(t, t2, i) || !zzdk.zzD(zzeg.zzn(t, j), zzeg.zzn(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 10:
                    if (!zzI(t, t2, i) || !zzdk.zzD(zzeg.zzn(t, j), zzeg.zzn(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 11:
                    if (!zzI(t, t2, i) || zzeg.zzd(t, j) != zzeg.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 12:
                    if (!zzI(t, t2, i) || zzeg.zzd(t, j) != zzeg.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 13:
                    if (!zzI(t, t2, i) || zzeg.zzd(t, j) != zzeg.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 14:
                    if (!zzI(t, t2, i) || zzeg.zzf(t, j) != zzeg.zzf(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 15:
                    if (!zzI(t, t2, i) || zzeg.zzd(t, j) != zzeg.zzd(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 16:
                    if (!zzI(t, t2, i) || zzeg.zzf(t, j) != zzeg.zzf(t2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 17:
                    if (!zzI(t, t2, i) || !zzdk.zzD(zzeg.zzn(t, j), zzeg.zzn(t2, j))) {
                        return false;
                    }
                    continue;
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
                    zZzD = zzdk.zzD(zzeg.zzn(t, j), zzeg.zzn(t2, j));
                    break;
                case 50:
                    zZzD = zzdk.zzD(zzeg.zzn(t, j), zzeg.zzn(t2, j));
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
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                    long jZzB = zzB(i) & 1048575;
                    if (zzeg.zzd(t, jZzB) != zzeg.zzd(t2, jZzB) || !zzdk.zzD(zzeg.zzn(t, j), zzeg.zzn(t2, j))) {
                        return false;
                    }
                    continue;
                    break;
                default:
            }
            if (!zZzD) {
                return false;
            }
        }
        if (!this.zzn.zzd(t).equals(this.zzn.zzd(t2))) {
            return false;
        }
        if (!this.zzh) {
            return true;
        }
        this.zzo.zzb(t);
        this.zzo.zzb(t2);
        throw null;
    }

    @Override // com.google.android.gms.internal.wearable.zzdi
    public final int zzc(T t) {
        int i;
        int iZze;
        int length = this.zzc.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int iZzA = zzA(i3);
            int i4 = this.zzc[i3];
            long j = 1048575 & iZzA;
            int iHashCode = 37;
            switch (zzC(iZzA)) {
                case 0:
                    i = i2 * 53;
                    iZze = zzca.zze(Double.doubleToLongBits(zzeg.zzl(t, j)));
                    i2 = i + iZze;
                    break;
                case 1:
                    i = i2 * 53;
                    iZze = Float.floatToIntBits(zzeg.zzj(t, j));
                    i2 = i + iZze;
                    break;
                case 2:
                    i = i2 * 53;
                    iZze = zzca.zze(zzeg.zzf(t, j));
                    i2 = i + iZze;
                    break;
                case 3:
                    i = i2 * 53;
                    iZze = zzca.zze(zzeg.zzf(t, j));
                    i2 = i + iZze;
                    break;
                case 4:
                    i = i2 * 53;
                    iZze = zzeg.zzd(t, j);
                    i2 = i + iZze;
                    break;
                case 5:
                    i = i2 * 53;
                    iZze = zzca.zze(zzeg.zzf(t, j));
                    i2 = i + iZze;
                    break;
                case 6:
                    i = i2 * 53;
                    iZze = zzeg.zzd(t, j);
                    i2 = i + iZze;
                    break;
                case 7:
                    i = i2 * 53;
                    iZze = zzca.zzf(zzeg.zzh(t, j));
                    i2 = i + iZze;
                    break;
                case 8:
                    i = i2 * 53;
                    iZze = ((String) zzeg.zzn(t, j)).hashCode();
                    i2 = i + iZze;
                    break;
                case 9:
                    Object objZzn = zzeg.zzn(t, j);
                    if (objZzn != null) {
                        iHashCode = objZzn.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iZze = zzeg.zzn(t, j).hashCode();
                    i2 = i + iZze;
                    break;
                case 11:
                    i = i2 * 53;
                    iZze = zzeg.zzd(t, j);
                    i2 = i + iZze;
                    break;
                case 12:
                    i = i2 * 53;
                    iZze = zzeg.zzd(t, j);
                    i2 = i + iZze;
                    break;
                case 13:
                    i = i2 * 53;
                    iZze = zzeg.zzd(t, j);
                    i2 = i + iZze;
                    break;
                case 14:
                    i = i2 * 53;
                    iZze = zzca.zze(zzeg.zzf(t, j));
                    i2 = i + iZze;
                    break;
                case 15:
                    i = i2 * 53;
                    iZze = zzeg.zzd(t, j);
                    i2 = i + iZze;
                    break;
                case 16:
                    i = i2 * 53;
                    iZze = zzca.zze(zzeg.zzf(t, j));
                    i2 = i + iZze;
                    break;
                case 17:
                    Object objZzn2 = zzeg.zzn(t, j);
                    if (objZzn2 != null) {
                        iHashCode = objZzn2.hashCode();
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
                    iZze = zzeg.zzn(t, j).hashCode();
                    i2 = i + iZze;
                    break;
                case 50:
                    i = i2 * 53;
                    iZze = zzeg.zzn(t, j).hashCode();
                    i2 = i + iZze;
                    break;
                case 51:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzca.zze(Double.doubleToLongBits(zzD(t, j)));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = Float.floatToIntBits(zzE(t, j));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzca.zze(zzG(t, j));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzca.zze(zzG(t, j));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzF(t, j);
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzca.zze(zzG(t, j));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzF(t, j);
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzca.zzf(zzH(t, j));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = ((String) zzeg.zzn(t, j)).hashCode();
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzeg.zzn(t, j).hashCode();
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzeg.zzn(t, j).hashCode();
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzF(t, j);
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzF(t, j);
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzF(t, j);
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzca.zze(zzG(t, j));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzF(t, j);
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzca.zze(zzG(t, j));
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzM(t, i4, i3)) {
                        i = i2 * 53;
                        iZze = zzeg.zzn(t, j).hashCode();
                        i2 = i + iZze;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzn.zzd(t).hashCode();
        if (!this.zzh) {
            return iHashCode2;
        }
        this.zzo.zzb(t);
        throw null;
    }

    @Override // com.google.android.gms.internal.wearable.zzdi
    public final int zze(T t) {
        return this.zzi ? zzr(t) : zzq(t);
    }

    /* JADX WARN: Code restructure failed: missing block: B:145:0x044d, code lost:
    
        if (r6 == 1048575) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x044f, code lost:
    
        r27.putInt(r12, r6, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0455, code lost:
    
        r3 = r8.zzk;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0459, code lost:
    
        if (r3 >= r8.zzl) goto L218;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x045b, code lost:
    
        r4 = r8.zzj[r3];
        r5 = r8.zzc[r4];
        r5 = com.google.android.gms.internal.wearable.zzeg.zzn(r12, r8.zzA(r4) & 1048575);
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x046d, code lost:
    
        if (r5 != null) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x0474, code lost:
    
        if (r8.zzx(r4) != null) goto L219;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x0476, code lost:
    
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0479, code lost:
    
        r5 = (com.google.android.gms.internal.wearable.zzcr) r5;
        r0 = (com.google.android.gms.internal.wearable.zzcq) r8.zzw(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x0481, code lost:
    
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0482, code lost:
    
        if (r9 != 0) goto L164;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x0486, code lost:
    
        if (r0 != r32) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x048d, code lost:
    
        throw com.google.android.gms.internal.wearable.zzcc.zzf();
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0490, code lost:
    
        if (r0 > r32) goto L168;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0492, code lost:
    
        if (r1 != r9) goto L168;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0494, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0499, code lost:
    
        throw com.google.android.gms.internal.wearable.zzcc.zzf();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final int zzg(T r29, byte[] r30, int r31, int r32, int r33, com.google.android.gms.internal.wearable.zzai r34) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1216
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.wearable.zzda.zzg(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.wearable.zzai):int");
    }

    @Override // com.google.android.gms.internal.wearable.zzdi
    public final void zzh(T t, byte[] bArr, int i, int i2, zzai zzaiVar) throws IOException {
        if (this.zzi) {
            zzy(t, bArr, i, i2, zzaiVar);
        } else {
            zzg(t, bArr, i, i2, 0, zzaiVar);
        }
    }

    @Override // com.google.android.gms.internal.wearable.zzdi
    public final void zzi(T t) {
        int i;
        int i2 = this.zzk;
        while (true) {
            i = this.zzl;
            if (i2 >= i) {
                break;
            }
            long jZzA = zzA(this.zzj[i2]) & 1048575;
            Object objZzn = zzeg.zzn(t, jZzA);
            if (objZzn != null) {
                ((zzcr) objZzn).zzd();
                zzeg.zzo(t, jZzA, objZzn);
            }
            i2++;
        }
        int length = this.zzj.length;
        while (i < length) {
            this.zzm.zza(t, this.zzj[i]);
            i++;
        }
        this.zzn.zze(t);
        if (this.zzh) {
            this.zzo.zzc(t);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:42:0x009e  */
    @Override // com.google.android.gms.internal.wearable.zzdi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzj(T r19) {
        /*
            Method dump skipped, instructions count: 244
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.wearable.zzda.zzj(java.lang.Object):boolean");
    }

    @Override // com.google.android.gms.internal.wearable.zzdi
    public final void zzm(T t, zzbc zzbcVar) throws IOException {
        if (!this.zzi) {
            zzR(t, zzbcVar);
            return;
        }
        if (this.zzh) {
            this.zzo.zzb(t);
            throw null;
        }
        int length = this.zzc.length;
        for (int i = 0; i < length; i += 3) {
            int iZzA = zzA(i);
            int i2 = this.zzc[i];
            switch (zzC(iZzA)) {
                case 0:
                    if (zzK(t, i)) {
                        zzbcVar.zzf(i2, zzeg.zzl(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzK(t, i)) {
                        zzbcVar.zze(i2, zzeg.zzj(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzK(t, i)) {
                        zzbcVar.zzc(i2, zzeg.zzf(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzK(t, i)) {
                        zzbcVar.zzh(i2, zzeg.zzf(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzK(t, i)) {
                        zzbcVar.zzi(i2, zzeg.zzd(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzK(t, i)) {
                        zzbcVar.zzj(i2, zzeg.zzf(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzK(t, i)) {
                        zzbcVar.zzk(i2, zzeg.zzd(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzK(t, i)) {
                        zzbcVar.zzl(i2, zzeg.zzh(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzK(t, i)) {
                        zzT(i2, zzeg.zzn(t, iZzA & 1048575), zzbcVar);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    if (zzK(t, i)) {
                        zzbcVar.zzr(i2, zzeg.zzn(t, iZzA & 1048575), zzv(i));
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if (zzK(t, i)) {
                        zzbcVar.zzn(i2, (zzau) zzeg.zzn(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzK(t, i)) {
                        zzbcVar.zzo(i2, zzeg.zzd(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzK(t, i)) {
                        zzbcVar.zzg(i2, zzeg.zzd(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzK(t, i)) {
                        zzbcVar.zzb(i2, zzeg.zzd(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzK(t, i)) {
                        zzbcVar.zzd(i2, zzeg.zzf(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzK(t, i)) {
                        zzbcVar.zzp(i2, zzeg.zzd(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzK(t, i)) {
                        zzbcVar.zzq(i2, zzeg.zzf(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if (zzK(t, i)) {
                        zzbcVar.zzs(i2, zzeg.zzn(t, iZzA & 1048575), zzv(i));
                        break;
                    } else {
                        break;
                    }
                case 18:
                    zzdk.zzJ(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, false);
                    break;
                case 19:
                    zzdk.zzK(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, false);
                    break;
                case 20:
                    zzdk.zzL(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, false);
                    break;
                case 21:
                    zzdk.zzM(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, false);
                    break;
                case 22:
                    zzdk.zzQ(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, false);
                    break;
                case 23:
                    zzdk.zzO(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, false);
                    break;
                case 24:
                    zzdk.zzT(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, false);
                    break;
                case 25:
                    zzdk.zzW(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, false);
                    break;
                case 26:
                    zzdk.zzX(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar);
                    break;
                case 27:
                    zzdk.zzZ(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, zzv(i));
                    break;
                case 28:
                    zzdk.zzY(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar);
                    break;
                case 29:
                    zzdk.zzR(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, false);
                    break;
                case 30:
                    zzdk.zzV(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, false);
                    break;
                case 31:
                    zzdk.zzU(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, false);
                    break;
                case 32:
                    zzdk.zzP(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, false);
                    break;
                case 33:
                    zzdk.zzS(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, false);
                    break;
                case 34:
                    zzdk.zzN(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, false);
                    break;
                case 35:
                    zzdk.zzJ(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, true);
                    break;
                case 36:
                    zzdk.zzK(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, true);
                    break;
                case 37:
                    zzdk.zzL(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, true);
                    break;
                case 38:
                    zzdk.zzM(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, true);
                    break;
                case 39:
                    zzdk.zzQ(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, true);
                    break;
                case 40:
                    zzdk.zzO(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, true);
                    break;
                case 41:
                    zzdk.zzT(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, true);
                    break;
                case 42:
                    zzdk.zzW(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, true);
                    break;
                case 43:
                    zzdk.zzR(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, true);
                    break;
                case 44:
                    zzdk.zzV(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, true);
                    break;
                case 45:
                    zzdk.zzU(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, true);
                    break;
                case 46:
                    zzdk.zzP(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, true);
                    break;
                case 47:
                    zzdk.zzS(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, true);
                    break;
                case 48:
                    zzdk.zzN(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, true);
                    break;
                case 49:
                    zzdk.zzaa(this.zzc[i], (List) zzeg.zzn(t, iZzA & 1048575), zzbcVar, zzv(i));
                    break;
                case 50:
                    zzS(zzbcVar, i2, zzeg.zzn(t, iZzA & 1048575), i);
                    break;
                case 51:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzf(i2, zzD(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zze(i2, zzE(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzc(i2, zzG(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzh(i2, zzG(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzi(i2, zzF(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzj(i2, zzG(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzk(i2, zzF(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzl(i2, zzH(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzM(t, i2, i)) {
                        zzT(i2, zzeg.zzn(t, iZzA & 1048575), zzbcVar);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzr(i2, zzeg.zzn(t, iZzA & 1048575), zzv(i));
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzn(i2, (zzau) zzeg.zzn(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzo(i2, zzF(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzg(i2, zzF(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzb(i2, zzF(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzd(i2, zzG(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzp(i2, zzF(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzq(i2, zzG(t, iZzA & 1048575));
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzM(t, i2, i)) {
                        zzbcVar.zzs(i2, zzeg.zzn(t, iZzA & 1048575), zzv(i));
                        break;
                    } else {
                        break;
                    }
            }
        }
        zzdw<?, ?> zzdwVar = this.zzn;
        zzdwVar.zzi(zzdwVar.zzd(t), zzbcVar);
    }

    @Override // com.google.android.gms.internal.wearable.zzdi
    public final void zzd(T t, T t2) {
        t2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzA = zzA(i);
            long j = 1048575 & iZzA;
            int i2 = this.zzc[i];
            switch (zzC(iZzA)) {
                case 0:
                    if (zzK(t2, i)) {
                        zzeg.zzm(t, j, zzeg.zzl(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzK(t2, i)) {
                        zzeg.zzk(t, j, zzeg.zzj(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzK(t2, i)) {
                        zzeg.zzg(t, j, zzeg.zzf(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzK(t2, i)) {
                        zzeg.zzg(t, j, zzeg.zzf(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzK(t2, i)) {
                        zzeg.zze(t, j, zzeg.zzd(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzK(t2, i)) {
                        zzeg.zzg(t, j, zzeg.zzf(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzK(t2, i)) {
                        zzeg.zze(t, j, zzeg.zzd(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzK(t2, i)) {
                        zzeg.zzi(t, j, zzeg.zzh(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzK(t2, i)) {
                        zzeg.zzo(t, j, zzeg.zzn(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zzo(t, t2, i);
                    break;
                case 10:
                    if (zzK(t2, i)) {
                        zzeg.zzo(t, j, zzeg.zzn(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzK(t2, i)) {
                        zzeg.zze(t, j, zzeg.zzd(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzK(t2, i)) {
                        zzeg.zze(t, j, zzeg.zzd(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzK(t2, i)) {
                        zzeg.zze(t, j, zzeg.zzd(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzK(t2, i)) {
                        zzeg.zzg(t, j, zzeg.zzf(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzK(t2, i)) {
                        zzeg.zze(t, j, zzeg.zzd(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzK(t2, i)) {
                        zzeg.zzg(t, j, zzeg.zzf(t2, j));
                        zzL(t, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zzo(t, t2, i);
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
                    this.zzm.zzb(t, t2, j);
                    break;
                case 50:
                    zzdk.zzI(this.zzq, t, t2, j);
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
                    if (zzM(t2, i2, i)) {
                        zzeg.zzo(t, j, zzeg.zzn(t2, j));
                        zzN(t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzp(t, t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzM(t2, i2, i)) {
                        zzeg.zzo(t, j, zzeg.zzn(t2, j));
                        zzN(t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzp(t, t2, i);
                    break;
            }
        }
        zzdk.zzF(this.zzn, t, t2);
        if (this.zzh) {
            zzdk.zzE(this.zzo, t, t2);
        }
    }
}
