package com.google.android.gms.internal.play_billing;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import sun.misc.Unsafe;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
final class zzdi<T> implements zzdp<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzeq.zzg();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzdf zzg;
    private final boolean zzh;
    private final int[] zzi;
    private final int zzj;
    private final int zzk;
    private final zzct zzl;
    private final zzeg zzm;
    private final zzbo zzn;
    private final int zzo;
    private final zzdk zzp;
    private final zzda zzq;

    private zzdi(int[] iArr, Object[] objArr, int i, int i2, zzdf zzdfVar, int i3, boolean z, int[] iArr2, int i4, int i5, zzdk zzdkVar, zzct zzctVar, zzeg zzegVar, zzbo zzboVar, zzda zzdaVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzo = i3;
        boolean z2 = false;
        if (zzboVar != null && zzboVar.zzc(zzdfVar)) {
            z2 = true;
        }
        this.zzh = z2;
        this.zzi = iArr2;
        this.zzj = i4;
        this.zzk = i5;
        this.zzp = zzdkVar;
        this.zzl = zzctVar;
        this.zzm = zzegVar;
        this.zzn = zzboVar;
        this.zzg = zzdfVar;
        this.zzq = zzdaVar;
    }

    private final zzce zzA(int i) {
        int i2 = i / 3;
        return (zzce) this.zzd[i2 + i2 + 1];
    }

    private final zzdp zzB(int i) {
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzdp zzdpVar = (zzdp) this.zzd[i3];
        if (zzdpVar != null) {
            return zzdpVar;
        }
        zzdp zzdpVarZzb = zzdn.zza().zzb((Class) this.zzd[i3 + 1]);
        this.zzd[i3] = zzdpVarZzb;
        return zzdpVarZzb;
    }

    private final Object zzC(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private final Object zzD(Object obj, int i) {
        zzdp zzdpVarZzB = zzB(i);
        int iZzy = zzy(i) & 1048575;
        if (!zzP(obj, i)) {
            return zzdpVarZzB.zze();
        }
        Object object = zzb.getObject(obj, iZzy);
        if (zzS(object)) {
            return object;
        }
        Object objZze = zzdpVarZzB.zze();
        if (object != null) {
            zzdpVarZzB.zzg(objZze, object);
        }
        return objZze;
    }

    private final Object zzE(Object obj, int i, int i2) {
        zzdp zzdpVarZzB = zzB(i2);
        if (!zzT(obj, i, i2)) {
            return zzdpVarZzB.zze();
        }
        Object object = zzb.getObject(obj, zzy(i2) & 1048575);
        if (zzS(object)) {
            return object;
        }
        Object objZze = zzdpVarZzB.zze();
        if (object != null) {
            zzdpVarZzB.zzg(objZze, object);
        }
        return objZze;
    }

    private static Field zzF(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + str + " for " + cls.getName() + " not found. Known fields are " + Arrays.toString(declaredFields));
        }
    }

    private static void zzG(Object obj) {
        if (!zzS(obj)) {
            throw new IllegalArgumentException("Mutating immutable message: ".concat(String.valueOf(String.valueOf(obj))));
        }
    }

    private final void zzH(Object obj, Object obj2, int i) {
        if (zzP(obj2, i)) {
            int iZzy = zzy(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzy;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzdp zzdpVarZzB = zzB(i);
            if (!zzP(obj, i)) {
                if (zzS(object)) {
                    Object objZze = zzdpVarZzB.zze();
                    zzdpVarZzB.zzg(objZze, object);
                    unsafe.putObject(obj, j, objZze);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzJ(obj, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzS(object2)) {
                Object objZze2 = zzdpVarZzB.zze();
                zzdpVarZzB.zzg(objZze2, object2);
                unsafe.putObject(obj, j, objZze2);
                object2 = objZze2;
            }
            zzdpVarZzB.zzg(object2, object);
        }
    }

    private final void zzI(Object obj, Object obj2, int i) {
        int i2 = this.zzc[i];
        if (zzT(obj2, i2, i)) {
            int iZzy = zzy(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzy;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzdp zzdpVarZzB = zzB(i);
            if (!zzT(obj, i2, i)) {
                if (zzS(object)) {
                    Object objZze = zzdpVarZzB.zze();
                    zzdpVarZzB.zzg(objZze, object);
                    unsafe.putObject(obj, j, objZze);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzK(obj, i2, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzS(object2)) {
                Object objZze2 = zzdpVarZzB.zze();
                zzdpVarZzB.zzg(objZze2, object2);
                unsafe.putObject(obj, j, objZze2);
                object2 = objZze2;
            }
            zzdpVarZzB.zzg(object2, object);
        }
    }

    private final void zzJ(Object obj, int i) {
        int iZzv = zzv(i);
        long j = 1048575 & iZzv;
        if (j == 1048575) {
            return;
        }
        zzeq.zzq(obj, j, (1 << (iZzv >>> 20)) | zzeq.zzc(obj, j));
    }

    private final void zzK(Object obj, int i, int i2) {
        zzeq.zzq(obj, zzv(i2) & 1048575, i);
    }

    private final void zzL(Object obj, int i, Object obj2) {
        zzb.putObject(obj, zzy(i) & 1048575, obj2);
        zzJ(obj, i);
    }

    private final void zzM(Object obj, int i, int i2, Object obj2) {
        zzb.putObject(obj, zzy(i2) & 1048575, obj2);
        zzK(obj, i, i2);
    }

    private final void zzN(zzey zzeyVar, int i, Object obj, int i2) throws IOException {
        if (obj == null) {
            return;
        }
        throw null;
    }

    private final boolean zzO(Object obj, Object obj2, int i) {
        return zzP(obj, i) == zzP(obj2, i);
    }

    private final boolean zzP(Object obj, int i) {
        int iZzv = zzv(i);
        long j = iZzv & 1048575;
        if (j != 1048575) {
            return (zzeq.zzc(obj, j) & (1 << (iZzv >>> 20))) != 0;
        }
        int iZzy = zzy(i);
        long j2 = iZzy & 1048575;
        switch (zzx(iZzy)) {
            case 0:
                return Double.doubleToRawLongBits(zzeq.zza(obj, j2)) != 0;
            case 1:
                return Float.floatToRawIntBits(zzeq.zzb(obj, j2)) != 0;
            case 2:
                return zzeq.zzd(obj, j2) != 0;
            case 3:
                return zzeq.zzd(obj, j2) != 0;
            case 4:
                return zzeq.zzc(obj, j2) != 0;
            case 5:
                return zzeq.zzd(obj, j2) != 0;
            case 6:
                return zzeq.zzc(obj, j2) != 0;
            case 7:
                return zzeq.zzw(obj, j2);
            case 8:
                Object objZzf = zzeq.zzf(obj, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zzba) {
                    return !zzba.zzb.equals(objZzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzeq.zzf(obj, j2) != null;
            case 10:
                return !zzba.zzb.equals(zzeq.zzf(obj, j2));
            case 11:
                return zzeq.zzc(obj, j2) != 0;
            case 12:
                return zzeq.zzc(obj, j2) != 0;
            case 13:
                return zzeq.zzc(obj, j2) != 0;
            case 14:
                return zzeq.zzd(obj, j2) != 0;
            case 15:
                return zzeq.zzc(obj, j2) != 0;
            case 16:
                return zzeq.zzd(obj, j2) != 0;
            case 17:
                return zzeq.zzf(obj, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zzQ(Object obj, int i, int i2, int i3, int i4) {
        return i2 == 1048575 ? zzP(obj, i) : (i3 & i4) != 0;
    }

    private static boolean zzR(Object obj, int i, zzdp zzdpVar) {
        return zzdpVar.zzk(zzeq.zzf(obj, i & 1048575));
    }

    private static boolean zzS(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zzcb) {
            return ((zzcb) obj).zzt();
        }
        return true;
    }

    private final boolean zzT(Object obj, int i, int i2) {
        return zzeq.zzc(obj, (long) (zzv(i2) & 1048575)) == i;
    }

    private static boolean zzU(Object obj, long j) {
        return ((Boolean) zzeq.zzf(obj, j)).booleanValue();
    }

    private static final void zzV(int i, Object obj, zzey zzeyVar) throws IOException {
        if (obj instanceof String) {
            zzeyVar.zzF(i, (String) obj);
        } else {
            zzeyVar.zzd(i, (zzba) obj);
        }
    }

    static zzeh zzd(Object obj) {
        zzcb zzcbVar = (zzcb) obj;
        zzeh zzehVar = zzcbVar.zzc;
        if (zzehVar != zzeh.zzc()) {
            return zzehVar;
        }
        zzeh zzehVarZzf = zzeh.zzf();
        zzcbVar.zzc = zzehVarZzf;
        return zzehVarZzf;
    }

    /* JADX WARN: Removed duplicated region for block: B:123:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x026a  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x026d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static com.google.android.gms.internal.play_billing.zzdi zzl(java.lang.Class r30, com.google.android.gms.internal.play_billing.zzdc r31, com.google.android.gms.internal.play_billing.zzdk r32, com.google.android.gms.internal.play_billing.zzct r33, com.google.android.gms.internal.play_billing.zzeg r34, com.google.android.gms.internal.play_billing.zzbo r35, com.google.android.gms.internal.play_billing.zzda r36) {
        /*
            Method dump skipped, instructions count: 996
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzdi.zzl(java.lang.Class, com.google.android.gms.internal.play_billing.zzdc, com.google.android.gms.internal.play_billing.zzdk, com.google.android.gms.internal.play_billing.zzct, com.google.android.gms.internal.play_billing.zzeg, com.google.android.gms.internal.play_billing.zzbo, com.google.android.gms.internal.play_billing.zzda):com.google.android.gms.internal.play_billing.zzdi");
    }

    private static double zzm(Object obj, long j) {
        return ((Double) zzeq.zzf(obj, j)).doubleValue();
    }

    private static float zzn(Object obj, long j) {
        return ((Float) zzeq.zzf(obj, j)).floatValue();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final int zzo(Object obj) {
        int i;
        int iZzx;
        int iZzx2;
        int iZzy;
        int iZzx3;
        int iZzx4;
        int iZzx5;
        int iZzx6;
        int iZzt;
        boolean z;
        int iZzc;
        int iZzh;
        int iZzx7;
        int iZzx8;
        int iZzx9;
        int iZzx10;
        int iZzx11;
        int iZzx12;
        int iZzx13;
        Unsafe unsafe = zzb;
        int i2 = 1048575;
        int i3 = 1048575;
        int i4 = 0;
        int iZzx14 = 0;
        int i5 = 0;
        while (i4 < this.zzc.length) {
            int iZzy2 = zzy(i4);
            int[] iArr = this.zzc;
            int i6 = iArr[i4];
            int iZzx15 = zzx(iZzy2);
            if (iZzx15 <= 17) {
                int i7 = iArr[i4 + 2];
                int i8 = i7 & i2;
                int i9 = i7 >>> 20;
                if (i8 != i3) {
                    i5 = unsafe.getInt(obj, i8);
                    i3 = i8;
                }
                i = 1 << i9;
            } else {
                i = 0;
            }
            long j = iZzy2 & i2;
            switch (iZzx15) {
                case 0:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        iZzx = zzbi.zzx(i6 << 3);
                        iZzx4 = iZzx + 8;
                        iZzx14 += iZzx4;
                        break;
                    }
                case 1:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        iZzx2 = zzbi.zzx(i6 << 3);
                        iZzx4 = iZzx2 + 4;
                        iZzx14 += iZzx4;
                        break;
                    }
                case 2:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        iZzy = zzbi.zzy(unsafe.getLong(obj, j));
                        iZzx3 = zzbi.zzx(i6 << 3);
                        iZzx14 += iZzx3 + iZzy;
                        break;
                    }
                case 3:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        iZzy = zzbi.zzy(unsafe.getLong(obj, j));
                        iZzx3 = zzbi.zzx(i6 << 3);
                        iZzx14 += iZzx3 + iZzy;
                        break;
                    }
                case 4:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        iZzy = zzbi.zzu(unsafe.getInt(obj, j));
                        iZzx3 = zzbi.zzx(i6 << 3);
                        iZzx14 += iZzx3 + iZzy;
                        break;
                    }
                case 5:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        iZzx = zzbi.zzx(i6 << 3);
                        iZzx4 = iZzx + 8;
                        iZzx14 += iZzx4;
                        break;
                    }
                case 6:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        iZzx2 = zzbi.zzx(i6 << 3);
                        iZzx4 = iZzx2 + 4;
                        iZzx14 += iZzx4;
                        break;
                    }
                case 7:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        iZzx4 = zzbi.zzx(i6 << 3) + 1;
                        iZzx14 += iZzx4;
                        break;
                    }
                case 8:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        Object object = unsafe.getObject(obj, j);
                        if (!(object instanceof zzba)) {
                            iZzy = zzbi.zzw((String) object);
                            iZzx3 = zzbi.zzx(i6 << 3);
                            iZzx14 += iZzx3 + iZzy;
                            break;
                        } else {
                            int i10 = zzbi.zzb;
                            int iZzd = ((zzba) object).zzd();
                            iZzx5 = zzbi.zzx(iZzd) + iZzd;
                            iZzx6 = zzbi.zzx(i6 << 3);
                            iZzx4 = iZzx6 + iZzx5;
                            iZzx14 += iZzx4;
                            break;
                        }
                    }
                case 9:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        iZzx4 = zzdr.zzn(i6, unsafe.getObject(obj, j), zzB(i4));
                        iZzx14 += iZzx4;
                        break;
                    }
                case 10:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        zzba zzbaVar = (zzba) unsafe.getObject(obj, j);
                        int i11 = zzbi.zzb;
                        int iZzd2 = zzbaVar.zzd();
                        iZzx5 = zzbi.zzx(iZzd2) + iZzd2;
                        iZzx6 = zzbi.zzx(i6 << 3);
                        iZzx4 = iZzx6 + iZzx5;
                        iZzx14 += iZzx4;
                        break;
                    }
                case 11:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        iZzy = zzbi.zzx(unsafe.getInt(obj, j));
                        iZzx3 = zzbi.zzx(i6 << 3);
                        iZzx14 += iZzx3 + iZzy;
                        break;
                    }
                case 12:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        iZzy = zzbi.zzu(unsafe.getInt(obj, j));
                        iZzx3 = zzbi.zzx(i6 << 3);
                        iZzx14 += iZzx3 + iZzy;
                        break;
                    }
                case 13:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        iZzx2 = zzbi.zzx(i6 << 3);
                        iZzx4 = iZzx2 + 4;
                        iZzx14 += iZzx4;
                        break;
                    }
                case 14:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        iZzx = zzbi.zzx(i6 << 3);
                        iZzx4 = iZzx + 8;
                        iZzx14 += iZzx4;
                        break;
                    }
                case 15:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        int i12 = unsafe.getInt(obj, j);
                        iZzx3 = zzbi.zzx(i6 << 3);
                        iZzy = zzbi.zzx((i12 >> 31) ^ (i12 + i12));
                        iZzx14 += iZzx3 + iZzy;
                        break;
                    }
                case 16:
                    if ((i & i5) == 0) {
                        break;
                    } else {
                        long j2 = unsafe.getLong(obj, j);
                        iZzx14 += zzbi.zzx(i6 << 3) + zzbi.zzy((j2 >> 63) ^ (j2 + j2));
                        break;
                    }
                case 17:
                    if ((i5 & i) == 0) {
                        break;
                    } else {
                        iZzx4 = zzbi.zzt(i6, (zzdf) unsafe.getObject(obj, j), zzB(i4));
                        iZzx14 += iZzx4;
                        break;
                    }
                case 18:
                    iZzx4 = zzdr.zzg(i6, (List) unsafe.getObject(obj, j), false);
                    iZzx14 += iZzx4;
                    break;
                case 19:
                    iZzx4 = zzdr.zze(i6, (List) unsafe.getObject(obj, j), false);
                    iZzx14 += iZzx4;
                    break;
                case 20:
                    iZzx4 = zzdr.zzl(i6, (List) unsafe.getObject(obj, j), false);
                    iZzx14 += iZzx4;
                    break;
                case 21:
                    iZzx4 = zzdr.zzw(i6, (List) unsafe.getObject(obj, j), false);
                    iZzx14 += iZzx4;
                    break;
                case 22:
                    iZzx4 = zzdr.zzj(i6, (List) unsafe.getObject(obj, j), false);
                    iZzx14 += iZzx4;
                    break;
                case 23:
                    iZzx4 = zzdr.zzg(i6, (List) unsafe.getObject(obj, j), false);
                    iZzx14 += iZzx4;
                    break;
                case 24:
                    iZzx4 = zzdr.zze(i6, (List) unsafe.getObject(obj, j), false);
                    iZzx14 += iZzx4;
                    break;
                case 25:
                    iZzx4 = zzdr.zza(i6, (List) unsafe.getObject(obj, j), false);
                    iZzx14 += iZzx4;
                    break;
                case 26:
                    iZzt = zzdr.zzt(i6, (List) unsafe.getObject(obj, j));
                    iZzx14 += iZzt;
                    break;
                case 27:
                    iZzt = zzdr.zzo(i6, (List) unsafe.getObject(obj, j), zzB(i4));
                    iZzx14 += iZzt;
                    break;
                case 28:
                    iZzt = zzdr.zzb(i6, (List) unsafe.getObject(obj, j));
                    iZzx14 += iZzt;
                    break;
                case 29:
                    iZzt = zzdr.zzu(i6, (List) unsafe.getObject(obj, j), false);
                    iZzx14 += iZzt;
                    break;
                case 30:
                    z = false;
                    iZzc = zzdr.zzc(i6, (List) unsafe.getObject(obj, j), false);
                    iZzx14 += iZzc;
                    break;
                case 31:
                    z = false;
                    iZzc = zzdr.zze(i6, (List) unsafe.getObject(obj, j), false);
                    iZzx14 += iZzc;
                    break;
                case 32:
                    z = false;
                    iZzc = zzdr.zzg(i6, (List) unsafe.getObject(obj, j), false);
                    iZzx14 += iZzc;
                    break;
                case 33:
                    z = false;
                    iZzc = zzdr.zzp(i6, (List) unsafe.getObject(obj, j), false);
                    iZzx14 += iZzc;
                    break;
                case 34:
                    z = false;
                    iZzc = zzdr.zzr(i6, (List) unsafe.getObject(obj, j), false);
                    iZzx14 += iZzc;
                    break;
                case 35:
                    iZzh = zzdr.zzh((List) unsafe.getObject(obj, j));
                    if (iZzh > 0) {
                        iZzx7 = zzbi.zzx(iZzh);
                        iZzx8 = zzbi.zzx(i6 << 3);
                        iZzx9 = iZzx8 + iZzx7;
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 36:
                    iZzh = zzdr.zzf((List) unsafe.getObject(obj, j));
                    if (iZzh > 0) {
                        iZzx7 = zzbi.zzx(iZzh);
                        iZzx8 = zzbi.zzx(i6 << 3);
                        iZzx9 = iZzx8 + iZzx7;
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 37:
                    iZzh = zzdr.zzm((List) unsafe.getObject(obj, j));
                    if (iZzh > 0) {
                        iZzx7 = zzbi.zzx(iZzh);
                        iZzx8 = zzbi.zzx(i6 << 3);
                        iZzx9 = iZzx8 + iZzx7;
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 38:
                    iZzh = zzdr.zzx((List) unsafe.getObject(obj, j));
                    if (iZzh > 0) {
                        iZzx7 = zzbi.zzx(iZzh);
                        iZzx8 = zzbi.zzx(i6 << 3);
                        iZzx9 = iZzx8 + iZzx7;
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 39:
                    iZzh = zzdr.zzk((List) unsafe.getObject(obj, j));
                    if (iZzh > 0) {
                        iZzx7 = zzbi.zzx(iZzh);
                        iZzx8 = zzbi.zzx(i6 << 3);
                        iZzx9 = iZzx8 + iZzx7;
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 40:
                    iZzh = zzdr.zzh((List) unsafe.getObject(obj, j));
                    if (iZzh > 0) {
                        iZzx7 = zzbi.zzx(iZzh);
                        iZzx8 = zzbi.zzx(i6 << 3);
                        iZzx9 = iZzx8 + iZzx7;
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 41:
                    iZzh = zzdr.zzf((List) unsafe.getObject(obj, j));
                    if (iZzh > 0) {
                        iZzx7 = zzbi.zzx(iZzh);
                        iZzx8 = zzbi.zzx(i6 << 3);
                        iZzx9 = iZzx8 + iZzx7;
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 42:
                    List list = (List) unsafe.getObject(obj, j);
                    int i13 = zzdr.zza;
                    iZzh = list.size();
                    if (iZzh > 0) {
                        iZzx7 = zzbi.zzx(iZzh);
                        iZzx8 = zzbi.zzx(i6 << 3);
                        iZzx9 = iZzx8 + iZzx7;
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 43:
                    iZzh = zzdr.zzv((List) unsafe.getObject(obj, j));
                    if (iZzh > 0) {
                        iZzx7 = zzbi.zzx(iZzh);
                        iZzx8 = zzbi.zzx(i6 << 3);
                        iZzx9 = iZzx8 + iZzx7;
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 44:
                    iZzh = zzdr.zzd((List) unsafe.getObject(obj, j));
                    if (iZzh > 0) {
                        iZzx7 = zzbi.zzx(iZzh);
                        iZzx8 = zzbi.zzx(i6 << 3);
                        iZzx9 = iZzx8 + iZzx7;
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 45:
                    iZzh = zzdr.zzf((List) unsafe.getObject(obj, j));
                    if (iZzh > 0) {
                        iZzx7 = zzbi.zzx(iZzh);
                        iZzx8 = zzbi.zzx(i6 << 3);
                        iZzx9 = iZzx8 + iZzx7;
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 46:
                    iZzh = zzdr.zzh((List) unsafe.getObject(obj, j));
                    if (iZzh > 0) {
                        iZzx7 = zzbi.zzx(iZzh);
                        iZzx8 = zzbi.zzx(i6 << 3);
                        iZzx9 = iZzx8 + iZzx7;
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 47:
                    iZzh = zzdr.zzq((List) unsafe.getObject(obj, j));
                    if (iZzh > 0) {
                        iZzx7 = zzbi.zzx(iZzh);
                        iZzx8 = zzbi.zzx(i6 << 3);
                        iZzx9 = iZzx8 + iZzx7;
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 48:
                    iZzh = zzdr.zzs((List) unsafe.getObject(obj, j));
                    if (iZzh > 0) {
                        iZzx7 = zzbi.zzx(iZzh);
                        iZzx8 = zzbi.zzx(i6 << 3);
                        iZzx9 = iZzx8 + iZzx7;
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 49:
                    iZzt = zzdr.zzi(i6, (List) unsafe.getObject(obj, j), zzB(i4));
                    iZzx14 += iZzt;
                    break;
                case 50:
                    zzda.zza(i6, unsafe.getObject(obj, j), zzC(i4));
                    break;
                case 51:
                    if (zzT(obj, i6, i4)) {
                        iZzx10 = zzbi.zzx(i6 << 3);
                        iZzt = iZzx10 + 8;
                        iZzx14 += iZzt;
                    }
                    break;
                case 52:
                    if (zzT(obj, i6, i4)) {
                        iZzx11 = zzbi.zzx(i6 << 3);
                        iZzt = iZzx11 + 4;
                        iZzx14 += iZzt;
                    }
                    break;
                case 53:
                    if (zzT(obj, i6, i4)) {
                        iZzh = zzbi.zzy(zzz(obj, j));
                        iZzx9 = zzbi.zzx(i6 << 3);
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 54:
                    if (zzT(obj, i6, i4)) {
                        iZzh = zzbi.zzy(zzz(obj, j));
                        iZzx9 = zzbi.zzx(i6 << 3);
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 55:
                    if (zzT(obj, i6, i4)) {
                        iZzh = zzbi.zzu(zzp(obj, j));
                        iZzx9 = zzbi.zzx(i6 << 3);
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 56:
                    if (zzT(obj, i6, i4)) {
                        iZzx10 = zzbi.zzx(i6 << 3);
                        iZzt = iZzx10 + 8;
                        iZzx14 += iZzt;
                    }
                    break;
                case 57:
                    if (zzT(obj, i6, i4)) {
                        iZzx11 = zzbi.zzx(i6 << 3);
                        iZzt = iZzx11 + 4;
                        iZzx14 += iZzt;
                    }
                    break;
                case 58:
                    if (zzT(obj, i6, i4)) {
                        iZzt = zzbi.zzx(i6 << 3) + 1;
                        iZzx14 += iZzt;
                    }
                    break;
                case 59:
                    if (zzT(obj, i6, i4)) {
                        Object object2 = unsafe.getObject(obj, j);
                        if (object2 instanceof zzba) {
                            int i14 = zzbi.zzb;
                            int iZzd3 = ((zzba) object2).zzd();
                            iZzx12 = zzbi.zzx(iZzd3) + iZzd3;
                            iZzx13 = zzbi.zzx(i6 << 3);
                            iZzt = iZzx13 + iZzx12;
                            iZzx14 += iZzt;
                        } else {
                            iZzh = zzbi.zzw((String) object2);
                            iZzx9 = zzbi.zzx(i6 << 3);
                            iZzx14 += iZzx9 + iZzh;
                        }
                    }
                    break;
                case 60:
                    if (zzT(obj, i6, i4)) {
                        iZzt = zzdr.zzn(i6, unsafe.getObject(obj, j), zzB(i4));
                        iZzx14 += iZzt;
                    }
                    break;
                case 61:
                    if (zzT(obj, i6, i4)) {
                        zzba zzbaVar2 = (zzba) unsafe.getObject(obj, j);
                        int i15 = zzbi.zzb;
                        int iZzd4 = zzbaVar2.zzd();
                        iZzx12 = zzbi.zzx(iZzd4) + iZzd4;
                        iZzx13 = zzbi.zzx(i6 << 3);
                        iZzt = iZzx13 + iZzx12;
                        iZzx14 += iZzt;
                    }
                    break;
                case 62:
                    if (zzT(obj, i6, i4)) {
                        iZzh = zzbi.zzx(zzp(obj, j));
                        iZzx9 = zzbi.zzx(i6 << 3);
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 63:
                    if (zzT(obj, i6, i4)) {
                        iZzh = zzbi.zzu(zzp(obj, j));
                        iZzx9 = zzbi.zzx(i6 << 3);
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 64:
                    if (zzT(obj, i6, i4)) {
                        iZzx11 = zzbi.zzx(i6 << 3);
                        iZzt = iZzx11 + 4;
                        iZzx14 += iZzt;
                    }
                    break;
                case 65:
                    if (zzT(obj, i6, i4)) {
                        iZzx10 = zzbi.zzx(i6 << 3);
                        iZzt = iZzx10 + 8;
                        iZzx14 += iZzt;
                    }
                    break;
                case 66:
                    if (zzT(obj, i6, i4)) {
                        int iZzp = zzp(obj, j);
                        iZzx9 = zzbi.zzx(i6 << 3);
                        iZzh = zzbi.zzx((iZzp >> 31) ^ (iZzp + iZzp));
                        iZzx14 += iZzx9 + iZzh;
                    }
                    break;
                case 67:
                    if (zzT(obj, i6, i4)) {
                        long jZzz = zzz(obj, j);
                        iZzx14 += zzbi.zzx(i6 << 3) + zzbi.zzy((jZzz >> 63) ^ (jZzz + jZzz));
                    }
                    break;
                case 68:
                    if (zzT(obj, i6, i4)) {
                        iZzt = zzbi.zzt(i6, (zzdf) unsafe.getObject(obj, j), zzB(i4));
                        iZzx14 += iZzt;
                    }
                    break;
            }
            i4 += 3;
            i2 = 1048575;
        }
        zzeg zzegVar = this.zzm;
        int iZza = iZzx14 + zzegVar.zza(zzegVar.zzd(obj));
        if (!this.zzh) {
            return iZza;
        }
        this.zzn.zza(obj);
        throw null;
    }

    private static int zzp(Object obj, long j) {
        return ((Integer) zzeq.zzf(obj, j)).intValue();
    }

    private final int zzq(Object obj, byte[] bArr, int i, int i2, int i3, long j, zzan zzanVar) throws IOException {
        Unsafe unsafe = zzb;
        Object objZzC = zzC(i3);
        Object object = unsafe.getObject(obj, j);
        if (!((zzcz) object).zze()) {
            zzcz zzczVarZzb = zzcz.zza().zzb();
            zzda.zzb(zzczVarZzb, object);
            unsafe.putObject(obj, j, zzczVarZzb);
        }
        throw null;
    }

    private final int zzr(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzan zzanVar) throws IOException {
        Unsafe unsafe = zzb;
        long j2 = this.zzc[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 == 1) {
                    unsafe.putObject(obj, j, Double.valueOf(Double.longBitsToDouble(zzao.zzp(bArr, i))));
                    int i9 = i + 8;
                    unsafe.putInt(obj, j2, i4);
                    return i9;
                }
                break;
            case 52:
                if (i5 == 5) {
                    unsafe.putObject(obj, j, Float.valueOf(Float.intBitsToFloat(zzao.zzb(bArr, i))));
                    int i10 = i + 4;
                    unsafe.putInt(obj, j2, i4);
                    return i10;
                }
                break;
            case 53:
            case 54:
                if (i5 == 0) {
                    int iZzm = zzao.zzm(bArr, i, zzanVar);
                    unsafe.putObject(obj, j, Long.valueOf(zzanVar.zzb));
                    unsafe.putInt(obj, j2, i4);
                    return iZzm;
                }
                break;
            case 55:
            case 62:
                if (i5 == 0) {
                    int iZzj = zzao.zzj(bArr, i, zzanVar);
                    unsafe.putObject(obj, j, Integer.valueOf(zzanVar.zza));
                    unsafe.putInt(obj, j2, i4);
                    return iZzj;
                }
                break;
            case 56:
            case 65:
                if (i5 == 1) {
                    unsafe.putObject(obj, j, Long.valueOf(zzao.zzp(bArr, i)));
                    int i11 = i + 8;
                    unsafe.putInt(obj, j2, i4);
                    return i11;
                }
                break;
            case 57:
            case 64:
                if (i5 == 5) {
                    unsafe.putObject(obj, j, Integer.valueOf(zzao.zzb(bArr, i)));
                    int i12 = i + 4;
                    unsafe.putInt(obj, j2, i4);
                    return i12;
                }
                break;
            case 58:
                if (i5 == 0) {
                    int iZzm2 = zzao.zzm(bArr, i, zzanVar);
                    unsafe.putObject(obj, j, Boolean.valueOf(zzanVar.zzb != 0));
                    unsafe.putInt(obj, j2, i4);
                    return iZzm2;
                }
                break;
            case 59:
                if (i5 == 2) {
                    int iZzj2 = zzao.zzj(bArr, i, zzanVar);
                    int i13 = zzanVar.zza;
                    if (i13 == 0) {
                        unsafe.putObject(obj, j, "");
                    } else {
                        if ((i6 & 536870912) != 0 && !zzev.zze(bArr, iZzj2, iZzj2 + i13)) {
                            throw zzci.zzc();
                        }
                        unsafe.putObject(obj, j, new String(bArr, iZzj2, i13, zzcg.zzb));
                        iZzj2 += i13;
                    }
                    unsafe.putInt(obj, j2, i4);
                    return iZzj2;
                }
                break;
            case 60:
                if (i5 == 2) {
                    Object objZzE = zzE(obj, i4, i8);
                    int iZzo = zzao.zzo(objZzE, zzB(i8), bArr, i, i2, zzanVar);
                    zzM(obj, i4, i8, objZzE);
                    return iZzo;
                }
                break;
            case 61:
                if (i5 == 2) {
                    int iZza = zzao.zza(bArr, i, zzanVar);
                    unsafe.putObject(obj, j, zzanVar.zzc);
                    unsafe.putInt(obj, j2, i4);
                    return iZza;
                }
                break;
            case 63:
                if (i5 == 0) {
                    int iZzj3 = zzao.zzj(bArr, i, zzanVar);
                    int i14 = zzanVar.zza;
                    zzce zzceVarZzA = zzA(i8);
                    if (zzceVarZzA == null || zzceVarZzA.zza(i14)) {
                        unsafe.putObject(obj, j, Integer.valueOf(i14));
                        unsafe.putInt(obj, j2, i4);
                    } else {
                        zzd(obj).zzj(i3, Long.valueOf(i14));
                    }
                    return iZzj3;
                }
                break;
            case 66:
                if (i5 == 0) {
                    int iZzj4 = zzao.zzj(bArr, i, zzanVar);
                    unsafe.putObject(obj, j, Integer.valueOf(zzbe.zzb(zzanVar.zza)));
                    unsafe.putInt(obj, j2, i4);
                    return iZzj4;
                }
                break;
            case 67:
                if (i5 == 0) {
                    int iZzm3 = zzao.zzm(bArr, i, zzanVar);
                    unsafe.putObject(obj, j, Long.valueOf(zzbe.zzc(zzanVar.zzb)));
                    unsafe.putInt(obj, j2, i4);
                    return iZzm3;
                }
                break;
            case 68:
                if (i5 == 3) {
                    Object objZzE2 = zzE(obj, i4, i8);
                    int iZzn = zzao.zzn(objZzE2, zzB(i8), bArr, i, i2, (i3 & (-8)) | 4, zzanVar);
                    zzM(obj, i4, i8, objZzE2);
                    return iZzn;
                }
                break;
        }
        return i;
    }

    private final int zzs(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzan zzanVar) throws IOException {
        int i8;
        int i9;
        int i10;
        int i11;
        int iZzl;
        int iZzj = i;
        Unsafe unsafe = zzb;
        zzcf zzcfVarZzd = (zzcf) unsafe.getObject(obj, j2);
        if (!zzcfVarZzd.zzc()) {
            int size = zzcfVarZzd.size();
            zzcfVarZzd = zzcfVarZzd.zzd(size == 0 ? 10 : size + size);
            unsafe.putObject(obj, j2, zzcfVarZzd);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    zzbk zzbkVar = (zzbk) zzcfVarZzd;
                    int iZzj2 = zzao.zzj(bArr, iZzj, zzanVar);
                    int i12 = zzanVar.zza + iZzj2;
                    while (iZzj2 < i12) {
                        zzbkVar.zze(Double.longBitsToDouble(zzao.zzp(bArr, iZzj2)));
                        iZzj2 += 8;
                    }
                    if (iZzj2 == i12) {
                        return iZzj2;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 1) {
                    zzbk zzbkVar2 = (zzbk) zzcfVarZzd;
                    zzbkVar2.zze(Double.longBitsToDouble(zzao.zzp(bArr, i)));
                    while (true) {
                        i8 = iZzj + 8;
                        if (i8 < i2) {
                            iZzj = zzao.zzj(bArr, i8, zzanVar);
                            if (i3 == zzanVar.zza) {
                                zzbkVar2.zze(Double.longBitsToDouble(zzao.zzp(bArr, iZzj)));
                            }
                        }
                    }
                    return i8;
                }
                break;
            case 19:
            case 36:
                if (i5 == 2) {
                    zzbu zzbuVar = (zzbu) zzcfVarZzd;
                    int iZzj3 = zzao.zzj(bArr, iZzj, zzanVar);
                    int i13 = zzanVar.zza + iZzj3;
                    while (iZzj3 < i13) {
                        zzbuVar.zze(Float.intBitsToFloat(zzao.zzb(bArr, iZzj3)));
                        iZzj3 += 4;
                    }
                    if (iZzj3 == i13) {
                        return iZzj3;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 5) {
                    zzbu zzbuVar2 = (zzbu) zzcfVarZzd;
                    zzbuVar2.zze(Float.intBitsToFloat(zzao.zzb(bArr, i)));
                    while (true) {
                        i9 = iZzj + 4;
                        if (i9 < i2) {
                            iZzj = zzao.zzj(bArr, i9, zzanVar);
                            if (i3 == zzanVar.zza) {
                                zzbuVar2.zze(Float.intBitsToFloat(zzao.zzb(bArr, iZzj)));
                            }
                        }
                    }
                    return i9;
                }
                break;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    zzcu zzcuVar = (zzcu) zzcfVarZzd;
                    int iZzj4 = zzao.zzj(bArr, iZzj, zzanVar);
                    int i14 = zzanVar.zza + iZzj4;
                    while (iZzj4 < i14) {
                        iZzj4 = zzao.zzm(bArr, iZzj4, zzanVar);
                        zzcuVar.zzf(zzanVar.zzb);
                    }
                    if (iZzj4 == i14) {
                        return iZzj4;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 0) {
                    zzcu zzcuVar2 = (zzcu) zzcfVarZzd;
                    int iZzm = zzao.zzm(bArr, iZzj, zzanVar);
                    zzcuVar2.zzf(zzanVar.zzb);
                    while (iZzm < i2) {
                        int iZzj5 = zzao.zzj(bArr, iZzm, zzanVar);
                        if (i3 != zzanVar.zza) {
                            return iZzm;
                        }
                        iZzm = zzao.zzm(bArr, iZzj5, zzanVar);
                        zzcuVar2.zzf(zzanVar.zzb);
                    }
                    return iZzm;
                }
                break;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return zzao.zzf(bArr, iZzj, zzcfVarZzd, zzanVar);
                }
                if (i5 == 0) {
                    return zzao.zzl(i3, bArr, i, i2, zzcfVarZzd, zzanVar);
                }
                break;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    zzcu zzcuVar3 = (zzcu) zzcfVarZzd;
                    int iZzj6 = zzao.zzj(bArr, iZzj, zzanVar);
                    int i15 = zzanVar.zza + iZzj6;
                    while (iZzj6 < i15) {
                        zzcuVar3.zzf(zzao.zzp(bArr, iZzj6));
                        iZzj6 += 8;
                    }
                    if (iZzj6 == i15) {
                        return iZzj6;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 1) {
                    zzcu zzcuVar4 = (zzcu) zzcfVarZzd;
                    zzcuVar4.zzf(zzao.zzp(bArr, i));
                    while (true) {
                        i10 = iZzj + 8;
                        if (i10 < i2) {
                            iZzj = zzao.zzj(bArr, i10, zzanVar);
                            if (i3 == zzanVar.zza) {
                                zzcuVar4.zzf(zzao.zzp(bArr, iZzj));
                            }
                        }
                    }
                    return i10;
                }
                break;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    zzcc zzccVar = (zzcc) zzcfVarZzd;
                    int iZzj7 = zzao.zzj(bArr, iZzj, zzanVar);
                    int i16 = zzanVar.zza + iZzj7;
                    while (iZzj7 < i16) {
                        zzccVar.zzf(zzao.zzb(bArr, iZzj7));
                        iZzj7 += 4;
                    }
                    if (iZzj7 == i16) {
                        return iZzj7;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 5) {
                    zzcc zzccVar2 = (zzcc) zzcfVarZzd;
                    zzccVar2.zzf(zzao.zzb(bArr, i));
                    while (true) {
                        i11 = iZzj + 4;
                        if (i11 < i2) {
                            iZzj = zzao.zzj(bArr, i11, zzanVar);
                            if (i3 == zzanVar.zza) {
                                zzccVar2.zzf(zzao.zzb(bArr, iZzj));
                            }
                        }
                    }
                    return i11;
                }
                break;
            case 25:
            case 42:
                if (i5 == 2) {
                    zzap zzapVar = (zzap) zzcfVarZzd;
                    int iZzj8 = zzao.zzj(bArr, iZzj, zzanVar);
                    int i17 = zzanVar.zza + iZzj8;
                    while (iZzj8 < i17) {
                        iZzj8 = zzao.zzm(bArr, iZzj8, zzanVar);
                        zzapVar.zze(zzanVar.zzb != 0);
                    }
                    if (iZzj8 == i17) {
                        return iZzj8;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 0) {
                    zzap zzapVar2 = (zzap) zzcfVarZzd;
                    int iZzm2 = zzao.zzm(bArr, iZzj, zzanVar);
                    zzapVar2.zze(zzanVar.zzb != 0);
                    while (iZzm2 < i2) {
                        int iZzj9 = zzao.zzj(bArr, iZzm2, zzanVar);
                        if (i3 != zzanVar.zza) {
                            return iZzm2;
                        }
                        iZzm2 = zzao.zzm(bArr, iZzj9, zzanVar);
                        zzapVar2.zze(zzanVar.zzb != 0);
                    }
                    return iZzm2;
                }
                break;
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) == 0) {
                        int iZzj10 = zzao.zzj(bArr, iZzj, zzanVar);
                        int i18 = zzanVar.zza;
                        if (i18 < 0) {
                            throw zzci.zzd();
                        }
                        if (i18 == 0) {
                            zzcfVarZzd.add("");
                        } else {
                            zzcfVarZzd.add(new String(bArr, iZzj10, i18, zzcg.zzb));
                            iZzj10 += i18;
                        }
                        while (iZzj10 < i2) {
                            int iZzj11 = zzao.zzj(bArr, iZzj10, zzanVar);
                            if (i3 != zzanVar.zza) {
                                return iZzj10;
                            }
                            iZzj10 = zzao.zzj(bArr, iZzj11, zzanVar);
                            int i19 = zzanVar.zza;
                            if (i19 < 0) {
                                throw zzci.zzd();
                            }
                            if (i19 == 0) {
                                zzcfVarZzd.add("");
                            } else {
                                zzcfVarZzd.add(new String(bArr, iZzj10, i19, zzcg.zzb));
                                iZzj10 += i19;
                            }
                        }
                        return iZzj10;
                    }
                    int iZzj12 = zzao.zzj(bArr, iZzj, zzanVar);
                    int i20 = zzanVar.zza;
                    if (i20 < 0) {
                        throw zzci.zzd();
                    }
                    if (i20 == 0) {
                        zzcfVarZzd.add("");
                    } else {
                        int i21 = iZzj12 + i20;
                        if (!zzev.zze(bArr, iZzj12, i21)) {
                            throw zzci.zzc();
                        }
                        zzcfVarZzd.add(new String(bArr, iZzj12, i20, zzcg.zzb));
                        iZzj12 = i21;
                    }
                    while (iZzj12 < i2) {
                        int iZzj13 = zzao.zzj(bArr, iZzj12, zzanVar);
                        if (i3 != zzanVar.zza) {
                            return iZzj12;
                        }
                        iZzj12 = zzao.zzj(bArr, iZzj13, zzanVar);
                        int i22 = zzanVar.zza;
                        if (i22 < 0) {
                            throw zzci.zzd();
                        }
                        if (i22 == 0) {
                            zzcfVarZzd.add("");
                        } else {
                            int i23 = iZzj12 + i22;
                            if (!zzev.zze(bArr, iZzj12, i23)) {
                                throw zzci.zzc();
                            }
                            zzcfVarZzd.add(new String(bArr, iZzj12, i22, zzcg.zzb));
                            iZzj12 = i23;
                        }
                    }
                    return iZzj12;
                }
                break;
            case 27:
                if (i5 == 2) {
                    return zzao.zze(zzB(i6), i3, bArr, i, i2, zzcfVarZzd, zzanVar);
                }
                break;
            case 28:
                if (i5 == 2) {
                    int iZzj14 = zzao.zzj(bArr, iZzj, zzanVar);
                    int i24 = zzanVar.zza;
                    if (i24 < 0) {
                        throw zzci.zzd();
                    }
                    if (i24 > bArr.length - iZzj14) {
                        throw zzci.zzg();
                    }
                    if (i24 == 0) {
                        zzcfVarZzd.add(zzba.zzb);
                    } else {
                        zzcfVarZzd.add(zzba.zzl(bArr, iZzj14, i24));
                        iZzj14 += i24;
                    }
                    while (iZzj14 < i2) {
                        int iZzj15 = zzao.zzj(bArr, iZzj14, zzanVar);
                        if (i3 != zzanVar.zza) {
                            return iZzj14;
                        }
                        iZzj14 = zzao.zzj(bArr, iZzj15, zzanVar);
                        int i25 = zzanVar.zza;
                        if (i25 < 0) {
                            throw zzci.zzd();
                        }
                        if (i25 > bArr.length - iZzj14) {
                            throw zzci.zzg();
                        }
                        if (i25 == 0) {
                            zzcfVarZzd.add(zzba.zzb);
                        } else {
                            zzcfVarZzd.add(zzba.zzl(bArr, iZzj14, i25));
                            iZzj14 += i25;
                        }
                    }
                    return iZzj14;
                }
                break;
            case 30:
            case 44:
                if (i5 == 2) {
                    iZzl = zzao.zzf(bArr, iZzj, zzcfVarZzd, zzanVar);
                } else if (i5 == 0) {
                    iZzl = zzao.zzl(i3, bArr, i, i2, zzcfVarZzd, zzanVar);
                }
                zzce zzceVarZzA = zzA(i6);
                zzeg zzegVar = this.zzm;
                int i26 = zzdr.zza;
                if (zzceVarZzA != null) {
                    Object objZzA = null;
                    if (zzcfVarZzd instanceof RandomAccess) {
                        int size2 = zzcfVarZzd.size();
                        int i27 = 0;
                        for (int i28 = 0; i28 < size2; i28++) {
                            int iIntValue = ((Integer) zzcfVarZzd.get(i28)).intValue();
                            if (zzceVarZzA.zza(iIntValue)) {
                                if (i28 != i27) {
                                    zzcfVarZzd.set(i27, Integer.valueOf(iIntValue));
                                }
                                i27++;
                            } else {
                                objZzA = zzdr.zzA(obj, i4, iIntValue, objZzA, zzegVar);
                            }
                        }
                        if (i27 != size2) {
                            zzcfVarZzd.subList(i27, size2).clear();
                            return iZzl;
                        }
                    } else {
                        Iterator it = zzcfVarZzd.iterator();
                        while (it.hasNext()) {
                            int iIntValue2 = ((Integer) it.next()).intValue();
                            if (!zzceVarZzA.zza(iIntValue2)) {
                                objZzA = zzdr.zzA(obj, i4, iIntValue2, objZzA, zzegVar);
                                it.remove();
                            }
                        }
                    }
                }
                return iZzl;
            case 33:
            case 47:
                if (i5 == 2) {
                    zzcc zzccVar3 = (zzcc) zzcfVarZzd;
                    int iZzj16 = zzao.zzj(bArr, iZzj, zzanVar);
                    int i29 = zzanVar.zza + iZzj16;
                    while (iZzj16 < i29) {
                        iZzj16 = zzao.zzj(bArr, iZzj16, zzanVar);
                        zzccVar3.zzf(zzbe.zzb(zzanVar.zza));
                    }
                    if (iZzj16 == i29) {
                        return iZzj16;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 0) {
                    zzcc zzccVar4 = (zzcc) zzcfVarZzd;
                    int iZzj17 = zzao.zzj(bArr, iZzj, zzanVar);
                    zzccVar4.zzf(zzbe.zzb(zzanVar.zza));
                    while (iZzj17 < i2) {
                        int iZzj18 = zzao.zzj(bArr, iZzj17, zzanVar);
                        if (i3 != zzanVar.zza) {
                            return iZzj17;
                        }
                        iZzj17 = zzao.zzj(bArr, iZzj18, zzanVar);
                        zzccVar4.zzf(zzbe.zzb(zzanVar.zza));
                    }
                    return iZzj17;
                }
                break;
            case 34:
            case 48:
                if (i5 == 2) {
                    zzcu zzcuVar5 = (zzcu) zzcfVarZzd;
                    int iZzj19 = zzao.zzj(bArr, iZzj, zzanVar);
                    int i30 = zzanVar.zza + iZzj19;
                    while (iZzj19 < i30) {
                        iZzj19 = zzao.zzm(bArr, iZzj19, zzanVar);
                        zzcuVar5.zzf(zzbe.zzc(zzanVar.zzb));
                    }
                    if (iZzj19 == i30) {
                        return iZzj19;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 0) {
                    zzcu zzcuVar6 = (zzcu) zzcfVarZzd;
                    int iZzm3 = zzao.zzm(bArr, iZzj, zzanVar);
                    zzcuVar6.zzf(zzbe.zzc(zzanVar.zzb));
                    while (iZzm3 < i2) {
                        int iZzj20 = zzao.zzj(bArr, iZzm3, zzanVar);
                        if (i3 != zzanVar.zza) {
                            return iZzm3;
                        }
                        iZzm3 = zzao.zzm(bArr, iZzj20, zzanVar);
                        zzcuVar6.zzf(zzbe.zzc(zzanVar.zzb));
                    }
                    return iZzm3;
                }
                break;
            default:
                if (i5 == 3) {
                    zzdp zzdpVarZzB = zzB(i6);
                    int i31 = (i3 & (-8)) | 4;
                    int iZzc = zzao.zzc(zzdpVarZzB, bArr, i, i2, i31, zzanVar);
                    zzcfVarZzd.add(zzanVar.zzc);
                    while (iZzc < i2) {
                        int iZzj21 = zzao.zzj(bArr, iZzc, zzanVar);
                        if (i3 != zzanVar.zza) {
                            return iZzc;
                        }
                        iZzc = zzao.zzc(zzdpVarZzB, bArr, iZzj21, i2, i31, zzanVar);
                        zzcfVarZzd.add(zzanVar.zzc);
                    }
                    return iZzc;
                }
                break;
        }
        return iZzj;
    }

    private final int zzt(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzw(i, 0);
    }

    private final int zzu(int i, int i2) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzw(i, i2);
    }

    private final int zzv(int i) {
        return this.zzc[i + 2];
    }

    private final int zzw(int i, int i2) {
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

    private static int zzx(int i) {
        return (i >>> 20) & 255;
    }

    private final int zzy(int i) {
        return this.zzc[i + 1];
    }

    private static long zzz(Object obj, long j) {
        return ((Long) zzeq.zzf(obj, j)).longValue();
    }

    @Override // com.google.android.gms.internal.play_billing.zzdp
    public final int zza(Object obj) {
        int iZzx;
        int iZzx2;
        int iZzy;
        int iZzx3;
        int iZzx4;
        int iZzx5;
        int iZzx6;
        int iZzn;
        int iZzx7;
        int iZzy2;
        int iZzx8;
        int iZzx9;
        zzew zzewVar = zzew.DOUBLE;
        if (this.zzo - 1 == 0) {
            return zzo(obj);
        }
        Unsafe unsafe = zzb;
        int i = 0;
        for (int i2 = 0; i2 < this.zzc.length; i2 += 3) {
            int iZzy3 = zzy(i2);
            int iZzx10 = zzx(iZzy3);
            int i3 = this.zzc[i2];
            int i4 = iZzy3 & 1048575;
            if (iZzx10 >= zzbt.DOUBLE_LIST_PACKED.zza() && iZzx10 <= zzbt.SINT64_LIST_PACKED.zza()) {
                int i5 = this.zzc[i2 + 2];
            }
            long j = i4;
            switch (iZzx10) {
                case 0:
                    if (zzP(obj, i2)) {
                        iZzx = zzbi.zzx(i3 << 3);
                        iZzn = iZzx + 8;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzP(obj, i2)) {
                        iZzx2 = zzbi.zzx(i3 << 3);
                        iZzn = iZzx2 + 4;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzP(obj, i2)) {
                        iZzy = zzbi.zzy(zzeq.zzd(obj, j));
                        iZzx3 = zzbi.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzP(obj, i2)) {
                        iZzy = zzbi.zzy(zzeq.zzd(obj, j));
                        iZzx3 = zzbi.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzP(obj, i2)) {
                        iZzy = zzbi.zzu(zzeq.zzc(obj, j));
                        iZzx3 = zzbi.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzP(obj, i2)) {
                        iZzx = zzbi.zzx(i3 << 3);
                        iZzn = iZzx + 8;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzP(obj, i2)) {
                        iZzx2 = zzbi.zzx(i3 << 3);
                        iZzn = iZzx2 + 4;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzP(obj, i2)) {
                        iZzx4 = zzbi.zzx(i3 << 3);
                        iZzn = iZzx4 + 1;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzP(obj, i2)) {
                        Object objZzf = zzeq.zzf(obj, j);
                        if (objZzf instanceof zzba) {
                            int i6 = i3 << 3;
                            int i7 = zzbi.zzb;
                            int iZzd = ((zzba) objZzf).zzd();
                            iZzx5 = zzbi.zzx(iZzd) + iZzd;
                            iZzx6 = zzbi.zzx(i6);
                            iZzn = iZzx6 + iZzx5;
                            i += iZzn;
                            break;
                        } else {
                            iZzy = zzbi.zzw((String) objZzf);
                            iZzx3 = zzbi.zzx(i3 << 3);
                            i += iZzx3 + iZzy;
                            break;
                        }
                    } else {
                        break;
                    }
                case 9:
                    if (zzP(obj, i2)) {
                        iZzn = zzdr.zzn(i3, zzeq.zzf(obj, j), zzB(i2));
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if (zzP(obj, i2)) {
                        zzba zzbaVar = (zzba) zzeq.zzf(obj, j);
                        int i8 = i3 << 3;
                        int i9 = zzbi.zzb;
                        int iZzd2 = zzbaVar.zzd();
                        iZzx5 = zzbi.zzx(iZzd2) + iZzd2;
                        iZzx6 = zzbi.zzx(i8);
                        iZzn = iZzx6 + iZzx5;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzP(obj, i2)) {
                        iZzy = zzbi.zzx(zzeq.zzc(obj, j));
                        iZzx3 = zzbi.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzP(obj, i2)) {
                        iZzy = zzbi.zzu(zzeq.zzc(obj, j));
                        iZzx3 = zzbi.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzP(obj, i2)) {
                        iZzx2 = zzbi.zzx(i3 << 3);
                        iZzn = iZzx2 + 4;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzP(obj, i2)) {
                        iZzx = zzbi.zzx(i3 << 3);
                        iZzn = iZzx + 8;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzP(obj, i2)) {
                        int iZzc = zzeq.zzc(obj, j);
                        iZzx3 = zzbi.zzx(i3 << 3);
                        iZzy = zzbi.zzx((iZzc >> 31) ^ (iZzc + iZzc));
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzP(obj, i2)) {
                        long jZzd = zzeq.zzd(obj, j);
                        iZzx7 = zzbi.zzx(i3 << 3);
                        iZzy2 = zzbi.zzy((jZzd + jZzd) ^ (jZzd >> 63));
                        iZzn = iZzx7 + iZzy2;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if (zzP(obj, i2)) {
                        iZzn = zzbi.zzt(i3, (zzdf) zzeq.zzf(obj, j), zzB(i2));
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 18:
                    iZzn = zzdr.zzg(i3, (List) zzeq.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 19:
                    iZzn = zzdr.zze(i3, (List) zzeq.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 20:
                    iZzn = zzdr.zzl(i3, (List) zzeq.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 21:
                    iZzn = zzdr.zzw(i3, (List) zzeq.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 22:
                    iZzn = zzdr.zzj(i3, (List) zzeq.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 23:
                    iZzn = zzdr.zzg(i3, (List) zzeq.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 24:
                    iZzn = zzdr.zze(i3, (List) zzeq.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 25:
                    iZzn = zzdr.zza(i3, (List) zzeq.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 26:
                    iZzn = zzdr.zzt(i3, (List) zzeq.zzf(obj, j));
                    i += iZzn;
                    break;
                case 27:
                    iZzn = zzdr.zzo(i3, (List) zzeq.zzf(obj, j), zzB(i2));
                    i += iZzn;
                    break;
                case 28:
                    iZzn = zzdr.zzb(i3, (List) zzeq.zzf(obj, j));
                    i += iZzn;
                    break;
                case 29:
                    iZzn = zzdr.zzu(i3, (List) zzeq.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 30:
                    iZzn = zzdr.zzc(i3, (List) zzeq.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 31:
                    iZzn = zzdr.zze(i3, (List) zzeq.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 32:
                    iZzn = zzdr.zzg(i3, (List) zzeq.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 33:
                    iZzn = zzdr.zzp(i3, (List) zzeq.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 34:
                    iZzn = zzdr.zzr(i3, (List) zzeq.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 35:
                    iZzy = zzdr.zzh((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i10 = i3 << 3;
                        iZzx8 = zzbi.zzx(iZzy);
                        iZzx9 = zzbi.zzx(i10);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 36:
                    iZzy = zzdr.zzf((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i11 = i3 << 3;
                        iZzx8 = zzbi.zzx(iZzy);
                        iZzx9 = zzbi.zzx(i11);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 37:
                    iZzy = zzdr.zzm((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i12 = i3 << 3;
                        iZzx8 = zzbi.zzx(iZzy);
                        iZzx9 = zzbi.zzx(i12);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 38:
                    iZzy = zzdr.zzx((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i13 = i3 << 3;
                        iZzx8 = zzbi.zzx(iZzy);
                        iZzx9 = zzbi.zzx(i13);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 39:
                    iZzy = zzdr.zzk((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i14 = i3 << 3;
                        iZzx8 = zzbi.zzx(iZzy);
                        iZzx9 = zzbi.zzx(i14);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 40:
                    iZzy = zzdr.zzh((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i15 = i3 << 3;
                        iZzx8 = zzbi.zzx(iZzy);
                        iZzx9 = zzbi.zzx(i15);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 41:
                    iZzy = zzdr.zzf((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i16 = i3 << 3;
                        iZzx8 = zzbi.zzx(iZzy);
                        iZzx9 = zzbi.zzx(i16);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 42:
                    List list = (List) unsafe.getObject(obj, j);
                    int i17 = zzdr.zza;
                    iZzy = list.size();
                    if (iZzy > 0) {
                        int i18 = i3 << 3;
                        iZzx8 = zzbi.zzx(iZzy);
                        iZzx9 = zzbi.zzx(i18);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 43:
                    iZzy = zzdr.zzv((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i19 = i3 << 3;
                        iZzx8 = zzbi.zzx(iZzy);
                        iZzx9 = zzbi.zzx(i19);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 44:
                    iZzy = zzdr.zzd((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i20 = i3 << 3;
                        iZzx8 = zzbi.zzx(iZzy);
                        iZzx9 = zzbi.zzx(i20);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 45:
                    iZzy = zzdr.zzf((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i21 = i3 << 3;
                        iZzx8 = zzbi.zzx(iZzy);
                        iZzx9 = zzbi.zzx(i21);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 46:
                    iZzy = zzdr.zzh((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i22 = i3 << 3;
                        iZzx8 = zzbi.zzx(iZzy);
                        iZzx9 = zzbi.zzx(i22);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 47:
                    iZzy = zzdr.zzq((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i23 = i3 << 3;
                        iZzx8 = zzbi.zzx(iZzy);
                        iZzx9 = zzbi.zzx(i23);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 48:
                    iZzy = zzdr.zzs((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i24 = i3 << 3;
                        iZzx8 = zzbi.zzx(iZzy);
                        iZzx9 = zzbi.zzx(i24);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 49:
                    iZzn = zzdr.zzi(i3, (List) zzeq.zzf(obj, j), zzB(i2));
                    i += iZzn;
                    break;
                case 50:
                    zzda.zza(i3, zzeq.zzf(obj, j), zzC(i2));
                    break;
                case 51:
                    if (zzT(obj, i3, i2)) {
                        iZzx = zzbi.zzx(i3 << 3);
                        iZzn = iZzx + 8;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzT(obj, i3, i2)) {
                        iZzx2 = zzbi.zzx(i3 << 3);
                        iZzn = iZzx2 + 4;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzT(obj, i3, i2)) {
                        iZzy = zzbi.zzy(zzz(obj, j));
                        iZzx3 = zzbi.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzT(obj, i3, i2)) {
                        iZzy = zzbi.zzy(zzz(obj, j));
                        iZzx3 = zzbi.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzT(obj, i3, i2)) {
                        iZzy = zzbi.zzu(zzp(obj, j));
                        iZzx3 = zzbi.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzT(obj, i3, i2)) {
                        iZzx = zzbi.zzx(i3 << 3);
                        iZzn = iZzx + 8;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzT(obj, i3, i2)) {
                        iZzx2 = zzbi.zzx(i3 << 3);
                        iZzn = iZzx2 + 4;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzT(obj, i3, i2)) {
                        iZzx4 = zzbi.zzx(i3 << 3);
                        iZzn = iZzx4 + 1;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzT(obj, i3, i2)) {
                        Object objZzf2 = zzeq.zzf(obj, j);
                        if (objZzf2 instanceof zzba) {
                            int i25 = i3 << 3;
                            int i26 = zzbi.zzb;
                            int iZzd3 = ((zzba) objZzf2).zzd();
                            iZzx5 = zzbi.zzx(iZzd3) + iZzd3;
                            iZzx6 = zzbi.zzx(i25);
                            iZzn = iZzx6 + iZzx5;
                            i += iZzn;
                            break;
                        } else {
                            iZzy = zzbi.zzw((String) objZzf2);
                            iZzx3 = zzbi.zzx(i3 << 3);
                            i += iZzx3 + iZzy;
                            break;
                        }
                    } else {
                        break;
                    }
                case 60:
                    if (zzT(obj, i3, i2)) {
                        iZzn = zzdr.zzn(i3, zzeq.zzf(obj, j), zzB(i2));
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzT(obj, i3, i2)) {
                        zzba zzbaVar2 = (zzba) zzeq.zzf(obj, j);
                        int i27 = i3 << 3;
                        int i28 = zzbi.zzb;
                        int iZzd4 = zzbaVar2.zzd();
                        iZzx5 = zzbi.zzx(iZzd4) + iZzd4;
                        iZzx6 = zzbi.zzx(i27);
                        iZzn = iZzx6 + iZzx5;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzT(obj, i3, i2)) {
                        iZzy = zzbi.zzx(zzp(obj, j));
                        iZzx3 = zzbi.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzT(obj, i3, i2)) {
                        iZzy = zzbi.zzu(zzp(obj, j));
                        iZzx3 = zzbi.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzT(obj, i3, i2)) {
                        iZzx2 = zzbi.zzx(i3 << 3);
                        iZzn = iZzx2 + 4;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzT(obj, i3, i2)) {
                        iZzx = zzbi.zzx(i3 << 3);
                        iZzn = iZzx + 8;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzT(obj, i3, i2)) {
                        int iZzp = zzp(obj, j);
                        iZzx3 = zzbi.zzx(i3 << 3);
                        iZzy = zzbi.zzx((iZzp >> 31) ^ (iZzp + iZzp));
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzT(obj, i3, i2)) {
                        long jZzz = zzz(obj, j);
                        iZzx7 = zzbi.zzx(i3 << 3);
                        iZzy2 = zzbi.zzy((jZzz + jZzz) ^ (jZzz >> 63));
                        iZzn = iZzx7 + iZzy2;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzT(obj, i3, i2)) {
                        iZzn = zzbi.zzt(i3, (zzdf) zzeq.zzf(obj, j), zzB(i2));
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
            }
        }
        zzeg zzegVar = this.zzm;
        return i + zzegVar.zza(zzegVar.zzd(obj));
    }

    @Override // com.google.android.gms.internal.play_billing.zzdp
    public final int zzb(Object obj) {
        int i;
        long jDoubleToLongBits;
        int iFloatToIntBits;
        int length = this.zzc.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int iZzy = zzy(i3);
            int i4 = this.zzc[i3];
            long j = 1048575 & iZzy;
            int iHashCode = 37;
            switch (zzx(iZzy)) {
                case 0:
                    i = i2 * 53;
                    jDoubleToLongBits = Double.doubleToLongBits(zzeq.zza(obj, j));
                    byte[] bArr = zzcg.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i2 = i + iFloatToIntBits;
                    break;
                case 1:
                    i = i2 * 53;
                    iFloatToIntBits = Float.floatToIntBits(zzeq.zzb(obj, j));
                    i2 = i + iFloatToIntBits;
                    break;
                case 2:
                    i = i2 * 53;
                    jDoubleToLongBits = zzeq.zzd(obj, j);
                    byte[] bArr2 = zzcg.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i2 = i + iFloatToIntBits;
                    break;
                case 3:
                    i = i2 * 53;
                    jDoubleToLongBits = zzeq.zzd(obj, j);
                    byte[] bArr3 = zzcg.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i2 = i + iFloatToIntBits;
                    break;
                case 4:
                    i = i2 * 53;
                    iFloatToIntBits = zzeq.zzc(obj, j);
                    i2 = i + iFloatToIntBits;
                    break;
                case 5:
                    i = i2 * 53;
                    jDoubleToLongBits = zzeq.zzd(obj, j);
                    byte[] bArr4 = zzcg.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i2 = i + iFloatToIntBits;
                    break;
                case 6:
                    i = i2 * 53;
                    iFloatToIntBits = zzeq.zzc(obj, j);
                    i2 = i + iFloatToIntBits;
                    break;
                case 7:
                    i = i2 * 53;
                    iFloatToIntBits = zzcg.zza(zzeq.zzw(obj, j));
                    i2 = i + iFloatToIntBits;
                    break;
                case 8:
                    i = i2 * 53;
                    iFloatToIntBits = ((String) zzeq.zzf(obj, j)).hashCode();
                    i2 = i + iFloatToIntBits;
                    break;
                case 9:
                    Object objZzf = zzeq.zzf(obj, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iFloatToIntBits = zzeq.zzf(obj, j).hashCode();
                    i2 = i + iFloatToIntBits;
                    break;
                case 11:
                    i = i2 * 53;
                    iFloatToIntBits = zzeq.zzc(obj, j);
                    i2 = i + iFloatToIntBits;
                    break;
                case 12:
                    i = i2 * 53;
                    iFloatToIntBits = zzeq.zzc(obj, j);
                    i2 = i + iFloatToIntBits;
                    break;
                case 13:
                    i = i2 * 53;
                    iFloatToIntBits = zzeq.zzc(obj, j);
                    i2 = i + iFloatToIntBits;
                    break;
                case 14:
                    i = i2 * 53;
                    jDoubleToLongBits = zzeq.zzd(obj, j);
                    byte[] bArr5 = zzcg.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i2 = i + iFloatToIntBits;
                    break;
                case 15:
                    i = i2 * 53;
                    iFloatToIntBits = zzeq.zzc(obj, j);
                    i2 = i + iFloatToIntBits;
                    break;
                case 16:
                    i = i2 * 53;
                    jDoubleToLongBits = zzeq.zzd(obj, j);
                    byte[] bArr6 = zzcg.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i2 = i + iFloatToIntBits;
                    break;
                case 17:
                    Object objZzf2 = zzeq.zzf(obj, j);
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
                    iFloatToIntBits = zzeq.zzf(obj, j).hashCode();
                    i2 = i + iFloatToIntBits;
                    break;
                case 50:
                    i = i2 * 53;
                    iFloatToIntBits = zzeq.zzf(obj, j).hashCode();
                    i2 = i + iFloatToIntBits;
                    break;
                case 51:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        jDoubleToLongBits = Double.doubleToLongBits(zzm(obj, j));
                        byte[] bArr7 = zzcg.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        iFloatToIntBits = Float.floatToIntBits(zzn(obj, j));
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        jDoubleToLongBits = zzz(obj, j);
                        byte[] bArr8 = zzcg.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        jDoubleToLongBits = zzz(obj, j);
                        byte[] bArr9 = zzcg.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        iFloatToIntBits = zzp(obj, j);
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        jDoubleToLongBits = zzz(obj, j);
                        byte[] bArr10 = zzcg.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        iFloatToIntBits = zzp(obj, j);
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        iFloatToIntBits = zzcg.zza(zzU(obj, j));
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        iFloatToIntBits = ((String) zzeq.zzf(obj, j)).hashCode();
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        iFloatToIntBits = zzeq.zzf(obj, j).hashCode();
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        iFloatToIntBits = zzeq.zzf(obj, j).hashCode();
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        iFloatToIntBits = zzp(obj, j);
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        iFloatToIntBits = zzp(obj, j);
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        iFloatToIntBits = zzp(obj, j);
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        jDoubleToLongBits = zzz(obj, j);
                        byte[] bArr11 = zzcg.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        iFloatToIntBits = zzp(obj, j);
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        jDoubleToLongBits = zzz(obj, j);
                        byte[] bArr12 = zzcg.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzT(obj, i4, i3)) {
                        i = i2 * 53;
                        iFloatToIntBits = zzeq.zzf(obj, j).hashCode();
                        i2 = i + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzm.zzd(obj).hashCode();
        if (!this.zzh) {
            return iHashCode2;
        }
        this.zzn.zza(obj);
        throw null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:109:0x030e, code lost:
    
        if (r0 != r22) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0310, code lost:
    
        r15 = r28;
        r14 = r29;
        r12 = r30;
        r1 = r31;
        r13 = r32;
        r11 = r33;
        r9 = r34;
        r8 = r19;
        r5 = r20;
        r3 = r21;
        r2 = r22;
        r6 = r24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x032c, code lost:
    
        r2 = r0;
        r7 = r21;
        r6 = r24;
        r0 = r33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0360, code lost:
    
        if (r0 != r15) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0388, code lost:
    
        if (r0 != r15) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0403, code lost:
    
        if (r6 == 1048575) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0405, code lost:
    
        r27.putInt(r12, r6, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x040b, code lost:
    
        r2 = r8.zzj;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x040f, code lost:
    
        if (r2 >= r8.zzk) goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0411, code lost:
    
        r4 = r8.zzi[r2];
        r5 = r8.zzc[r4];
        r5 = com.google.android.gms.internal.play_billing.zzeq.zzf(r12, r8.zzy(r4) & 1048575);
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0423, code lost:
    
        if (r5 != null) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x042a, code lost:
    
        if (r8.zzA(r4) != null) goto L209;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x042c, code lost:
    
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x042f, code lost:
    
        r5 = (com.google.android.gms.internal.play_billing.zzcz) r5;
        r0 = (com.google.android.gms.internal.play_billing.zzcy) r8.zzC(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x0437, code lost:
    
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x0438, code lost:
    
        if (r9 != 0) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x043c, code lost:
    
        if (r0 != r32) goto L159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x0443, code lost:
    
        throw com.google.android.gms.internal.play_billing.zzci.zze();
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0446, code lost:
    
        if (r0 > r32) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0448, code lost:
    
        if (r3 != r9) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x044a, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x044f, code lost:
    
        throw com.google.android.gms.internal.play_billing.zzci.zze();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final int zzc(java.lang.Object r29, byte[] r30, int r31, int r32, int r33, com.google.android.gms.internal.play_billing.zzan r34) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1142
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzdi.zzc(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.play_billing.zzan):int");
    }

    @Override // com.google.android.gms.internal.play_billing.zzdp
    public final Object zze() {
        return ((zzcb) this.zzg).zzi();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x006c  */
    @Override // com.google.android.gms.internal.play_billing.zzdp
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zzf(java.lang.Object r8) {
        /*
            r7 = this;
            boolean r0 = zzS(r8)
            if (r0 != 0) goto L7
            return
        L7:
            boolean r0 = r8 instanceof com.google.android.gms.internal.play_billing.zzcb
            r1 = 0
            if (r0 == 0) goto L1a
            r0 = r8
            com.google.android.gms.internal.play_billing.zzcb r0 = (com.google.android.gms.internal.play_billing.zzcb) r0
            r2 = 2147483647(0x7fffffff, float:NaN)
            r0.zzq(r2)
            r0.zza = r1
            r0.zzo()
        L1a:
            int[] r0 = r7.zzc
            int r0 = r0.length
        L1d:
            if (r1 >= r0) goto L82
            int r2 = r7.zzy(r1)
            r3 = 1048575(0xfffff, float:1.469367E-39)
            r3 = r3 & r2
            int r2 = zzx(r2)
            long r3 = (long) r3
            r5 = 9
            if (r2 == r5) goto L6c
            r5 = 60
            if (r2 == r5) goto L54
            r5 = 68
            if (r2 == r5) goto L54
            switch(r2) {
                case 17: goto L6c;
                case 18: goto L4e;
                case 19: goto L4e;
                case 20: goto L4e;
                case 21: goto L4e;
                case 22: goto L4e;
                case 23: goto L4e;
                case 24: goto L4e;
                case 25: goto L4e;
                case 26: goto L4e;
                case 27: goto L4e;
                case 28: goto L4e;
                case 29: goto L4e;
                case 30: goto L4e;
                case 31: goto L4e;
                case 32: goto L4e;
                case 33: goto L4e;
                case 34: goto L4e;
                case 35: goto L4e;
                case 36: goto L4e;
                case 37: goto L4e;
                case 38: goto L4e;
                case 39: goto L4e;
                case 40: goto L4e;
                case 41: goto L4e;
                case 42: goto L4e;
                case 43: goto L4e;
                case 44: goto L4e;
                case 45: goto L4e;
                case 46: goto L4e;
                case 47: goto L4e;
                case 48: goto L4e;
                case 49: goto L4e;
                case 50: goto L3c;
                default: goto L3b;
            }
        L3b:
            goto L7f
        L3c:
            sun.misc.Unsafe r2 = com.google.android.gms.internal.play_billing.zzdi.zzb
            java.lang.Object r5 = r2.getObject(r8, r3)
            if (r5 == 0) goto L7f
            r6 = r5
            com.google.android.gms.internal.play_billing.zzcz r6 = (com.google.android.gms.internal.play_billing.zzcz) r6
            r6.zzc()
            r2.putObject(r8, r3, r5)
            goto L7f
        L4e:
            com.google.android.gms.internal.play_billing.zzct r2 = r7.zzl
            r2.zza(r8, r3)
            goto L7f
        L54:
            int[] r2 = r7.zzc
            r2 = r2[r1]
            boolean r2 = r7.zzT(r8, r2, r1)
            if (r2 == 0) goto L7f
            com.google.android.gms.internal.play_billing.zzdp r2 = r7.zzB(r1)
            sun.misc.Unsafe r5 = com.google.android.gms.internal.play_billing.zzdi.zzb
            java.lang.Object r3 = r5.getObject(r8, r3)
            r2.zzf(r3)
            goto L7f
        L6c:
            boolean r2 = r7.zzP(r8, r1)
            if (r2 == 0) goto L7f
            com.google.android.gms.internal.play_billing.zzdp r2 = r7.zzB(r1)
            sun.misc.Unsafe r5 = com.google.android.gms.internal.play_billing.zzdi.zzb
            java.lang.Object r3 = r5.getObject(r8, r3)
            r2.zzf(r3)
        L7f:
            int r1 = r1 + 3
            goto L1d
        L82:
            com.google.android.gms.internal.play_billing.zzeg r0 = r7.zzm
            r0.zzg(r8)
            boolean r0 = r7.zzh
            if (r0 == 0) goto L90
            com.google.android.gms.internal.play_billing.zzbo r0 = r7.zzn
            r0.zzb(r8)
        L90:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzdi.zzf(java.lang.Object):void");
    }

    @Override // com.google.android.gms.internal.play_billing.zzdp
    public final void zzg(Object obj, Object obj2) {
        zzG(obj);
        obj2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzy = zzy(i);
            int i2 = this.zzc[i];
            long j = 1048575 & iZzy;
            switch (zzx(iZzy)) {
                case 0:
                    if (zzP(obj2, i)) {
                        zzeq.zzo(obj, j, zzeq.zza(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzP(obj2, i)) {
                        zzeq.zzp(obj, j, zzeq.zzb(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzP(obj2, i)) {
                        zzeq.zzr(obj, j, zzeq.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzP(obj2, i)) {
                        zzeq.zzr(obj, j, zzeq.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzP(obj2, i)) {
                        zzeq.zzq(obj, j, zzeq.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzP(obj2, i)) {
                        zzeq.zzr(obj, j, zzeq.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzP(obj2, i)) {
                        zzeq.zzq(obj, j, zzeq.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzP(obj2, i)) {
                        zzeq.zzm(obj, j, zzeq.zzw(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzP(obj2, i)) {
                        zzeq.zzs(obj, j, zzeq.zzf(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zzH(obj, obj2, i);
                    break;
                case 10:
                    if (zzP(obj2, i)) {
                        zzeq.zzs(obj, j, zzeq.zzf(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzP(obj2, i)) {
                        zzeq.zzq(obj, j, zzeq.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzP(obj2, i)) {
                        zzeq.zzq(obj, j, zzeq.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzP(obj2, i)) {
                        zzeq.zzq(obj, j, zzeq.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzP(obj2, i)) {
                        zzeq.zzr(obj, j, zzeq.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzP(obj2, i)) {
                        zzeq.zzq(obj, j, zzeq.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzP(obj2, i)) {
                        zzeq.zzr(obj, j, zzeq.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zzH(obj, obj2, i);
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
                    this.zzl.zzb(obj, obj2, j);
                    break;
                case 50:
                    int i3 = zzdr.zza;
                    zzeq.zzs(obj, j, zzda.zzb(zzeq.zzf(obj, j), zzeq.zzf(obj2, j)));
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
                    if (zzT(obj2, i2, i)) {
                        zzeq.zzs(obj, j, zzeq.zzf(obj2, j));
                        zzK(obj, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzI(obj, obj2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzT(obj2, i2, i)) {
                        zzeq.zzs(obj, j, zzeq.zzf(obj2, j));
                        zzK(obj, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzI(obj, obj2, i);
                    break;
            }
        }
        zzdr.zzB(this.zzm, obj, obj2);
        if (this.zzh) {
            this.zzn.zza(obj2);
            throw null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x02d9, code lost:
    
        if (r0 != r15) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x02fd, code lost:
    
        if (r0 != r15) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0294, code lost:
    
        if (r0 != r5) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0296, code lost:
    
        r15 = r30;
        r14 = r31;
        r12 = r32;
        r13 = r34;
        r11 = r35;
        r9 = r18;
        r1 = r19;
        r2 = r22;
        r6 = r25;
        r7 = r26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x02ac, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:27:0x008d. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v12, types: [int] */
    @Override // com.google.android.gms.internal.play_billing.zzdp
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zzh(java.lang.Object r31, byte[] r32, int r33, int r34, com.google.android.gms.internal.play_billing.zzan r35) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 892
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzdi.zzh(java.lang.Object, byte[], int, int, com.google.android.gms.internal.play_billing.zzan):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.android.gms.internal.play_billing.zzdp
    public final void zzi(Object obj, zzey zzeyVar) throws IOException {
        int i;
        int i2;
        int i3;
        zzew zzewVar = zzew.DOUBLE;
        int i4 = 0;
        int i5 = 1048575;
        if (this.zzo - 1 != 0) {
            if (this.zzh) {
                this.zzn.zza(obj);
                throw null;
            }
            int length = this.zzc.length;
            for (int i6 = 0; i6 < length; i6 += 3) {
                int iZzy = zzy(i6);
                int i7 = this.zzc[i6];
                switch (zzx(iZzy)) {
                    case 0:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzf(i7, zzeq.zza(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 1:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzo(i7, zzeq.zzb(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzt(i7, zzeq.zzd(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzJ(i7, zzeq.zzd(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzr(i7, zzeq.zzc(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 5:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzm(i7, zzeq.zzd(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 6:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzk(i7, zzeq.zzc(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 7:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzb(i7, zzeq.zzw(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 8:
                        if (zzP(obj, i6)) {
                            zzV(i7, zzeq.zzf(obj, iZzy & 1048575), zzeyVar);
                            break;
                        } else {
                            break;
                        }
                    case 9:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzv(i7, zzeq.zzf(obj, iZzy & 1048575), zzB(i6));
                            break;
                        } else {
                            break;
                        }
                    case 10:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzd(i7, (zzba) zzeq.zzf(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 11:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzH(i7, zzeq.zzc(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 12:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzi(i7, zzeq.zzc(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 13:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzw(i7, zzeq.zzc(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 14:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzy(i7, zzeq.zzd(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 15:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzA(i7, zzeq.zzc(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzC(i7, zzeq.zzd(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 17:
                        if (zzP(obj, i6)) {
                            zzeyVar.zzq(i7, zzeq.zzf(obj, iZzy & 1048575), zzB(i6));
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        zzdr.zzF(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, false);
                        break;
                    case 19:
                        zzdr.zzJ(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, false);
                        break;
                    case 20:
                        zzdr.zzM(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, false);
                        break;
                    case 21:
                        zzdr.zzU(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, false);
                        break;
                    case 22:
                        zzdr.zzL(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, false);
                        break;
                    case 23:
                        zzdr.zzI(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, false);
                        break;
                    case 24:
                        zzdr.zzH(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, false);
                        break;
                    case 25:
                        zzdr.zzD(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, false);
                        break;
                    case 26:
                        zzdr.zzS(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar);
                        break;
                    case 27:
                        zzdr.zzN(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, zzB(i6));
                        break;
                    case 28:
                        zzdr.zzE(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar);
                        break;
                    case 29:
                        zzdr.zzT(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, false);
                        break;
                    case 30:
                        zzdr.zzG(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, false);
                        break;
                    case 31:
                        zzdr.zzO(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, false);
                        break;
                    case 32:
                        zzdr.zzP(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, false);
                        break;
                    case 33:
                        zzdr.zzQ(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, false);
                        break;
                    case 34:
                        zzdr.zzR(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, false);
                        break;
                    case 35:
                        zzdr.zzF(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, true);
                        break;
                    case 36:
                        zzdr.zzJ(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, true);
                        break;
                    case 37:
                        zzdr.zzM(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, true);
                        break;
                    case 38:
                        zzdr.zzU(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, true);
                        break;
                    case 39:
                        zzdr.zzL(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, true);
                        break;
                    case 40:
                        zzdr.zzI(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, true);
                        break;
                    case 41:
                        zzdr.zzH(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, true);
                        break;
                    case 42:
                        zzdr.zzD(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, true);
                        break;
                    case 43:
                        zzdr.zzT(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, true);
                        break;
                    case 44:
                        zzdr.zzG(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, true);
                        break;
                    case 45:
                        zzdr.zzO(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, true);
                        break;
                    case 46:
                        zzdr.zzP(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, true);
                        break;
                    case 47:
                        zzdr.zzQ(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, true);
                        break;
                    case 48:
                        zzdr.zzR(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, true);
                        break;
                    case 49:
                        zzdr.zzK(i7, (List) zzeq.zzf(obj, iZzy & 1048575), zzeyVar, zzB(i6));
                        break;
                    case 50:
                        zzN(zzeyVar, i7, zzeq.zzf(obj, iZzy & 1048575), i6);
                        break;
                    case 51:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzf(i7, zzm(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 52:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzo(i7, zzn(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 53:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzt(i7, zzz(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 54:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzJ(i7, zzz(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 55:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzr(i7, zzp(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 56:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzm(i7, zzz(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 57:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzk(i7, zzp(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 58:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzb(i7, zzU(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 59:
                        if (zzT(obj, i7, i6)) {
                            zzV(i7, zzeq.zzf(obj, iZzy & 1048575), zzeyVar);
                            break;
                        } else {
                            break;
                        }
                    case 60:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzv(i7, zzeq.zzf(obj, iZzy & 1048575), zzB(i6));
                            break;
                        } else {
                            break;
                        }
                    case 61:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzd(i7, (zzba) zzeq.zzf(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 62:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzH(i7, zzp(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 63:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzi(i7, zzp(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 64:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzw(i7, zzp(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 65:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzy(i7, zzz(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 66:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzA(i7, zzp(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 67:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzC(i7, zzz(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 68:
                        if (zzT(obj, i7, i6)) {
                            zzeyVar.zzq(i7, zzeq.zzf(obj, iZzy & 1048575), zzB(i6));
                            break;
                        } else {
                            break;
                        }
                }
            }
            zzeg zzegVar = this.zzm;
            zzegVar.zzi(zzegVar.zzd(obj), zzeyVar);
            return;
        }
        if (this.zzh) {
            this.zzn.zza(obj);
            throw null;
        }
        int length2 = this.zzc.length;
        Unsafe unsafe = zzb;
        int i8 = 0;
        int i9 = 0;
        int i10 = 1048575;
        while (i8 < length2) {
            int iZzy2 = zzy(i8);
            int[] iArr = this.zzc;
            int i11 = iArr[i8];
            int iZzx = zzx(iZzy2);
            if (iZzx <= 17) {
                int i12 = iArr[i8 + 2];
                int i13 = i12 & i5;
                if (i13 != i10) {
                    i9 = unsafe.getInt(obj, i13);
                    i10 = i13;
                }
                i = 1 << (i12 >>> 20);
            } else {
                i = i4;
            }
            long j = iZzy2 & i5;
            switch (iZzx) {
                case 0:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzf(i11, zzeq.zza(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 1:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzo(i11, zzeq.zzb(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 2:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzt(i11, unsafe.getLong(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 3:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzJ(i11, unsafe.getLong(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 4:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzr(i11, unsafe.getInt(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 5:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzm(i11, unsafe.getLong(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 6:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzk(i11, unsafe.getInt(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 7:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzb(i11, zzeq.zzw(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 8:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzV(i11, unsafe.getObject(obj, j), zzeyVar);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzv(i11, unsafe.getObject(obj, j), zzB(i8));
                        break;
                    } else {
                        break;
                    }
                case 10:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzd(i11, (zzba) unsafe.getObject(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 11:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzH(i11, unsafe.getInt(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 12:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzi(i11, unsafe.getInt(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 13:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzw(i11, unsafe.getInt(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 14:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzy(i11, unsafe.getLong(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 15:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzA(i11, unsafe.getInt(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 16:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzC(i11, unsafe.getLong(obj, j));
                        break;
                    } else {
                        break;
                    }
                case 17:
                    i2 = 0;
                    if ((i9 & i) != 0) {
                        zzeyVar.zzq(i11, unsafe.getObject(obj, j), zzB(i8));
                        break;
                    } else {
                        break;
                    }
                case 18:
                    i2 = 0;
                    zzdr.zzF(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, false);
                    break;
                case 19:
                    i2 = 0;
                    zzdr.zzJ(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, false);
                    break;
                case 20:
                    i2 = 0;
                    zzdr.zzM(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, false);
                    break;
                case 21:
                    i2 = 0;
                    zzdr.zzU(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, false);
                    break;
                case 22:
                    i2 = 0;
                    zzdr.zzL(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, false);
                    break;
                case 23:
                    i2 = 0;
                    zzdr.zzI(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, false);
                    break;
                case 24:
                    i2 = 0;
                    zzdr.zzH(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, false);
                    break;
                case 25:
                    i2 = 0;
                    zzdr.zzD(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, false);
                    break;
                case 26:
                    zzdr.zzS(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar);
                    i2 = 0;
                    break;
                case 27:
                    zzdr.zzN(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, zzB(i8));
                    i2 = 0;
                    break;
                case 28:
                    zzdr.zzE(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar);
                    i2 = 0;
                    break;
                case 29:
                    i3 = 0;
                    zzdr.zzT(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, false);
                    i2 = i3;
                    break;
                case 30:
                    i3 = 0;
                    zzdr.zzG(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, false);
                    i2 = i3;
                    break;
                case 31:
                    i3 = 0;
                    zzdr.zzO(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, false);
                    i2 = i3;
                    break;
                case 32:
                    i3 = 0;
                    zzdr.zzP(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, false);
                    i2 = i3;
                    break;
                case 33:
                    i3 = 0;
                    zzdr.zzQ(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, false);
                    i2 = i3;
                    break;
                case 34:
                    i3 = 0;
                    zzdr.zzR(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, false);
                    i2 = i3;
                    break;
                case 35:
                    zzdr.zzF(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, true);
                    i2 = 0;
                    break;
                case 36:
                    zzdr.zzJ(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, true);
                    i2 = 0;
                    break;
                case 37:
                    zzdr.zzM(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, true);
                    i2 = 0;
                    break;
                case 38:
                    zzdr.zzU(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, true);
                    i2 = 0;
                    break;
                case 39:
                    zzdr.zzL(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, true);
                    i2 = 0;
                    break;
                case 40:
                    zzdr.zzI(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, true);
                    i2 = 0;
                    break;
                case 41:
                    zzdr.zzH(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, true);
                    i2 = 0;
                    break;
                case 42:
                    zzdr.zzD(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, true);
                    i2 = 0;
                    break;
                case 43:
                    zzdr.zzT(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, true);
                    i2 = 0;
                    break;
                case 44:
                    zzdr.zzG(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, true);
                    i2 = 0;
                    break;
                case 45:
                    zzdr.zzO(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, true);
                    i2 = 0;
                    break;
                case 46:
                    zzdr.zzP(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, true);
                    i2 = 0;
                    break;
                case 47:
                    zzdr.zzQ(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, true);
                    i2 = 0;
                    break;
                case 48:
                    zzdr.zzR(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, true);
                    i2 = 0;
                    break;
                case 49:
                    zzdr.zzK(this.zzc[i8], (List) unsafe.getObject(obj, j), zzeyVar, zzB(i8));
                    i2 = 0;
                    break;
                case 50:
                    zzN(zzeyVar, i11, unsafe.getObject(obj, j), i8);
                    i2 = 0;
                    break;
                case 51:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzf(i11, zzm(obj, j));
                    }
                    i2 = 0;
                    break;
                case 52:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzo(i11, zzn(obj, j));
                    }
                    i2 = 0;
                    break;
                case 53:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzt(i11, zzz(obj, j));
                    }
                    i2 = 0;
                    break;
                case 54:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzJ(i11, zzz(obj, j));
                    }
                    i2 = 0;
                    break;
                case 55:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzr(i11, zzp(obj, j));
                    }
                    i2 = 0;
                    break;
                case 56:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzm(i11, zzz(obj, j));
                    }
                    i2 = 0;
                    break;
                case 57:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzk(i11, zzp(obj, j));
                    }
                    i2 = 0;
                    break;
                case 58:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzb(i11, zzU(obj, j));
                    }
                    i2 = 0;
                    break;
                case 59:
                    if (zzT(obj, i11, i8)) {
                        zzV(i11, unsafe.getObject(obj, j), zzeyVar);
                    }
                    i2 = 0;
                    break;
                case 60:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzv(i11, unsafe.getObject(obj, j), zzB(i8));
                    }
                    i2 = 0;
                    break;
                case 61:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzd(i11, (zzba) unsafe.getObject(obj, j));
                    }
                    i2 = 0;
                    break;
                case 62:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzH(i11, zzp(obj, j));
                    }
                    i2 = 0;
                    break;
                case 63:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzi(i11, zzp(obj, j));
                    }
                    i2 = 0;
                    break;
                case 64:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzw(i11, zzp(obj, j));
                    }
                    i2 = 0;
                    break;
                case 65:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzy(i11, zzz(obj, j));
                    }
                    i2 = 0;
                    break;
                case 66:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzA(i11, zzp(obj, j));
                    }
                    i2 = 0;
                    break;
                case 67:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzC(i11, zzz(obj, j));
                    }
                    i2 = 0;
                    break;
                case 68:
                    if (zzT(obj, i11, i8)) {
                        zzeyVar.zzq(i11, unsafe.getObject(obj, j), zzB(i8));
                    }
                    i2 = 0;
                    break;
                default:
                    i2 = 0;
                    break;
            }
            i8 += 3;
            i4 = i2;
            i5 = 1048575;
        }
        zzeg zzegVar2 = this.zzm;
        zzegVar2.zzi(zzegVar2.zzd(obj), zzeyVar);
    }

    @Override // com.google.android.gms.internal.play_billing.zzdp
    public final boolean zzj(Object obj, Object obj2) {
        boolean zZzV;
        int length = this.zzc.length;
        for (int i = 0; i < length; i += 3) {
            int iZzy = zzy(i);
            long j = iZzy & 1048575;
            switch (zzx(iZzy)) {
                case 0:
                    if (!zzO(obj, obj2, i) || Double.doubleToLongBits(zzeq.zza(obj, j)) != Double.doubleToLongBits(zzeq.zza(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 1:
                    if (!zzO(obj, obj2, i) || Float.floatToIntBits(zzeq.zzb(obj, j)) != Float.floatToIntBits(zzeq.zzb(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 2:
                    if (!zzO(obj, obj2, i) || zzeq.zzd(obj, j) != zzeq.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 3:
                    if (!zzO(obj, obj2, i) || zzeq.zzd(obj, j) != zzeq.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 4:
                    if (!zzO(obj, obj2, i) || zzeq.zzc(obj, j) != zzeq.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 5:
                    if (!zzO(obj, obj2, i) || zzeq.zzd(obj, j) != zzeq.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 6:
                    if (!zzO(obj, obj2, i) || zzeq.zzc(obj, j) != zzeq.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 7:
                    if (!zzO(obj, obj2, i) || zzeq.zzw(obj, j) != zzeq.zzw(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 8:
                    if (!zzO(obj, obj2, i) || !zzdr.zzV(zzeq.zzf(obj, j), zzeq.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 9:
                    if (!zzO(obj, obj2, i) || !zzdr.zzV(zzeq.zzf(obj, j), zzeq.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 10:
                    if (!zzO(obj, obj2, i) || !zzdr.zzV(zzeq.zzf(obj, j), zzeq.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 11:
                    if (!zzO(obj, obj2, i) || zzeq.zzc(obj, j) != zzeq.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 12:
                    if (!zzO(obj, obj2, i) || zzeq.zzc(obj, j) != zzeq.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 13:
                    if (!zzO(obj, obj2, i) || zzeq.zzc(obj, j) != zzeq.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 14:
                    if (!zzO(obj, obj2, i) || zzeq.zzd(obj, j) != zzeq.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 15:
                    if (!zzO(obj, obj2, i) || zzeq.zzc(obj, j) != zzeq.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 16:
                    if (!zzO(obj, obj2, i) || zzeq.zzd(obj, j) != zzeq.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 17:
                    if (!zzO(obj, obj2, i) || !zzdr.zzV(zzeq.zzf(obj, j), zzeq.zzf(obj2, j))) {
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
                    zZzV = zzdr.zzV(zzeq.zzf(obj, j), zzeq.zzf(obj2, j));
                    break;
                case 50:
                    zZzV = zzdr.zzV(zzeq.zzf(obj, j), zzeq.zzf(obj2, j));
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
                    long jZzv = zzv(i) & 1048575;
                    if (zzeq.zzc(obj, jZzv) != zzeq.zzc(obj2, jZzv) || !zzdr.zzV(zzeq.zzf(obj, j), zzeq.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                default:
            }
            if (!zZzV) {
                return false;
            }
        }
        if (!this.zzm.zzd(obj).equals(this.zzm.zzd(obj2))) {
            return false;
        }
        if (!this.zzh) {
            return true;
        }
        this.zzn.zza(obj);
        this.zzn.zza(obj2);
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x009e  */
    @Override // com.google.android.gms.internal.play_billing.zzdp
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzk(java.lang.Object r19) {
        /*
            Method dump skipped, instructions count: 244
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzdi.zzk(java.lang.Object):boolean");
    }
}
