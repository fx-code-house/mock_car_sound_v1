package com.google.android.gms.internal.clearcut;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes2.dex */
public final class zzk {
    private static int zza(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    private static long zza(long j, long j2, long j3) {
        long j4 = (j ^ j2) * j3;
        long j5 = ((j4 ^ (j4 >>> 47)) ^ j2) * j3;
        return (j5 ^ (j5 >>> 47)) * j3;
    }

    public static long zza(byte[] bArr) {
        int length = bArr.length;
        if (length < 0 || length > bArr.length) {
            throw new IndexOutOfBoundsException(new StringBuilder(67).append("Out of bound index with offput: 0 and length: ").append(length).toString());
        }
        int i = 37;
        if (length <= 32) {
            if (length > 16) {
                long j = (length << 1) - 7286425919675154353L;
                long jZzb = zzb(bArr, 0) * (-5435081209227447693L);
                long jZzb2 = zzb(bArr, 8);
                int i2 = length + 0;
                long jZzb3 = zzb(bArr, i2 - 8) * j;
                return zza(Long.rotateRight(jZzb + jZzb2, 43) + Long.rotateRight(jZzb3, 30) + (zzb(bArr, i2 - 16) * (-7286425919675154353L)), jZzb + Long.rotateRight(jZzb2 - 7286425919675154353L, 18) + jZzb3, j);
            }
            if (length >= 8) {
                long j2 = (length << 1) - 7286425919675154353L;
                long jZzb4 = zzb(bArr, 0) - 7286425919675154353L;
                long jZzb5 = zzb(bArr, (length + 0) - 8);
                return zza((Long.rotateRight(jZzb5, 37) * j2) + jZzb4, (Long.rotateRight(jZzb4, 25) + jZzb5) * j2, j2);
            }
            if (length >= 4) {
                return zza(length + ((zza(bArr, 0) & 4294967295L) << 3), zza(bArr, (length + 0) - 4) & 4294967295L, (length << 1) - 7286425919675154353L);
            }
            if (length <= 0) {
                return -7286425919675154353L;
            }
            long j3 = (((bArr[0] & 255) + ((bArr[(length >> 1) + 0] & 255) << 8)) * (-7286425919675154353L)) ^ ((length + ((bArr[(length - 1) + 0] & 255) << 2)) * (-4348849565147123417L));
            return (j3 ^ (j3 >>> 47)) * (-7286425919675154353L);
        }
        if (length <= 64) {
            long j4 = (length << 1) - 7286425919675154353L;
            long jZzb6 = zzb(bArr, 0) * (-7286425919675154353L);
            long jZzb7 = zzb(bArr, 8);
            int i3 = length + 0;
            long jZzb8 = zzb(bArr, i3 - 8) * j4;
            long jRotateRight = Long.rotateRight(jZzb6 + jZzb7, 43) + Long.rotateRight(jZzb8, 30) + (zzb(bArr, i3 - 16) * (-7286425919675154353L));
            long jZza = zza(jRotateRight, Long.rotateRight((-7286425919675154353L) + jZzb7, 18) + jZzb6 + jZzb8, j4);
            long jZzb9 = zzb(bArr, 16) * j4;
            long jZzb10 = zzb(bArr, 24);
            long jZzb11 = (jRotateRight + zzb(bArr, i3 - 32)) * j4;
            return zza(Long.rotateRight(jZzb9 + jZzb10, 43) + Long.rotateRight(jZzb11, 30) + ((jZza + zzb(bArr, i3 - 24)) * j4), jZzb9 + Long.rotateRight(jZzb10 + jZzb6, 18) + jZzb11, j4);
        }
        long[] jArr = new long[2];
        long[] jArr2 = new long[2];
        long jZzb12 = zzb(bArr, 0) + 95310865018149119L;
        int i4 = length - 1;
        int i5 = ((i4 / 64) << 6) + 0;
        int i6 = i4 & 63;
        int i7 = (i5 + i6) - 63;
        long j5 = 2480279821605975764L;
        long j6 = 1390051526045402406L;
        int i8 = 0;
        while (true) {
            long jRotateRight2 = Long.rotateRight(jZzb12 + j5 + jArr[0] + zzb(bArr, i8 + 8), i) * (-5435081209227447693L);
            long jRotateRight3 = Long.rotateRight(j5 + jArr[1] + zzb(bArr, i8 + 48), 42) * (-5435081209227447693L);
            long j7 = jRotateRight2 ^ jArr2[1];
            long jZzb13 = jRotateRight3 + jArr[0] + zzb(bArr, i8 + 40);
            long jRotateRight4 = Long.rotateRight(j6 + jArr2[0], 33) * (-5435081209227447693L);
            int i9 = i6;
            int i10 = i5;
            zza(bArr, i8, jArr[1] * (-5435081209227447693L), j7 + jArr2[0], jArr);
            zza(bArr, i8 + 32, jRotateRight4 + jArr2[1], jZzb13 + zzb(bArr, i8 + 16), jArr2);
            int i11 = i8 + 64;
            if (i11 == i10) {
                long j8 = ((j7 & 255) << 1) - 5435081209227447693L;
                long j9 = jArr2[0] + i9;
                jArr2[0] = j9;
                long j10 = jArr[0] + j9;
                jArr[0] = j10;
                jArr2[0] = jArr2[0] + j10;
                long jRotateRight5 = Long.rotateRight(jRotateRight4 + jZzb13 + jArr[0] + zzb(bArr, i7 + 8), 37) * j8;
                long jRotateRight6 = Long.rotateRight(jZzb13 + jArr[1] + zzb(bArr, i7 + 48), 42) * j8;
                long j11 = jRotateRight5 ^ (jArr2[1] * 9);
                long jZzb14 = jRotateRight6 + (jArr[0] * 9) + zzb(bArr, i7 + 40);
                long jRotateRight7 = Long.rotateRight(j7 + jArr2[0], 33) * j8;
                zza(bArr, i7, jArr[1] * j8, j11 + jArr2[0], jArr);
                zza(bArr, i7 + 32, jRotateRight7 + jArr2[1], zzb(bArr, i7 + 16) + jZzb14, jArr2);
                return zza(zza(jArr[0], jArr2[0], j8) + (((jZzb14 >>> 47) ^ jZzb14) * (-4348849565147123417L)) + j11, zza(jArr[1], jArr2[1], j8) + jRotateRight7, j8);
            }
            i8 = i11;
            i5 = i10;
            i6 = i9;
            jZzb12 = jRotateRight4;
            j6 = j7;
            j5 = jZzb13;
            i = 37;
        }
    }

    private static void zza(byte[] bArr, int i, long j, long j2, long[] jArr) {
        long jZzb = zzb(bArr, i);
        long jZzb2 = zzb(bArr, i + 8);
        long jZzb3 = zzb(bArr, i + 16);
        long jZzb4 = zzb(bArr, i + 24);
        long j3 = j + jZzb;
        long j4 = jZzb2 + j3 + jZzb3;
        long jRotateRight = Long.rotateRight(j2 + j3 + jZzb4, 21) + Long.rotateRight(j4, 44);
        jArr[0] = j4 + jZzb4;
        jArr[1] = jRotateRight + j3;
    }

    private static long zzb(byte[] bArr, int i) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr, i, 8);
        byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
        return byteBufferWrap.getLong();
    }
}
