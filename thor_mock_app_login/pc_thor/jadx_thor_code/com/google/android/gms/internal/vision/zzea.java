package com.google.android.gms.internal.vision;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzea {
    static int zzb(int i, int i2, int i3) {
        return (i & (~i3)) | (i2 & i3);
    }

    static int zzw(int i) {
        return (i < 32 ? 4 : 2) * (i + 1);
    }

    static Object zzv(int i) {
        if (i < 2 || i > 1073741824 || Integer.highestOneBit(i) != i) {
            throw new IllegalArgumentException(new StringBuilder(52).append("must be power of 2 between 2^1 and 2^30: ").append(i).toString());
        }
        return i <= 256 ? new byte[i] : i <= 65536 ? new short[i] : new int[i];
    }

    static int zza(Object obj, int i) {
        if (obj instanceof byte[]) {
            return ((byte[]) obj)[i] & 255;
        }
        if (obj instanceof short[]) {
            return ((short[]) obj)[i] & 65535;
        }
        return ((int[]) obj)[i];
    }

    static void zza(Object obj, int i, int i2) {
        if (obj instanceof byte[]) {
            ((byte[]) obj)[i] = (byte) i2;
        } else if (obj instanceof short[]) {
            ((short[]) obj)[i] = (short) i2;
        } else {
            ((int[]) obj)[i] = i2;
        }
    }

    static int zza(@NullableDecl Object obj, @NullableDecl Object obj2, int i, Object obj3, int[] iArr, Object[] objArr, @NullableDecl Object[] objArr2) {
        int i2;
        int i3;
        int iZzf = zzec.zzf(obj);
        int i4 = iZzf & i;
        int iZza = zza(obj3, i4);
        if (iZza == 0) {
            return -1;
        }
        int i5 = ~i;
        int i6 = iZzf & i5;
        int i7 = -1;
        while (true) {
            i2 = iZza - 1;
            i3 = iArr[i2];
            if ((i3 & i5) == i6 && zzcz.equal(obj, objArr[i2]) && (objArr2 == null || zzcz.equal(obj2, objArr2[i2]))) {
                break;
            }
            int i8 = i3 & i;
            if (i8 == 0) {
                return -1;
            }
            i7 = i2;
            iZza = i8;
        }
        int i9 = i3 & i;
        if (i7 == -1) {
            zza(obj3, i4, i9);
        } else {
            iArr[i7] = zzb(iArr[i7], i9, i);
        }
        return i2;
    }
}
