package com.google.android.gms.internal.measurement;

import com.google.android.exoplayer2.extractor.ts.PsExtractor;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzle extends zzld {
    zzle() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0065, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x008f, code lost:
    
        return -1;
     */
    @Override // com.google.android.gms.internal.measurement.zzld
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final int zza(int r18, byte[] r19, int r20, int r21) {
        /*
            Method dump skipped, instructions count: 221
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzle.zza(int, byte[], int, int):int");
    }

    @Override // com.google.android.gms.internal.measurement.zzld
    final String zza(byte[] bArr, int i, int i2) throws zzij {
        if ((i | i2 | ((bArr.length - i) - i2)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
        }
        int i3 = i + i2;
        char[] cArr = new char[i2];
        int i4 = 0;
        while (i < i3) {
            byte bZza = zzkz.zza(bArr, i);
            if (!zzla.zzd(bZza)) {
                break;
            }
            i++;
            zzla.zzb(bZza, cArr, i4);
            i4++;
        }
        int i5 = i4;
        while (i < i3) {
            int i6 = i + 1;
            byte bZza2 = zzkz.zza(bArr, i);
            if (zzla.zzd(bZza2)) {
                int i7 = i5 + 1;
                zzla.zzb(bZza2, cArr, i5);
                while (i6 < i3) {
                    byte bZza3 = zzkz.zza(bArr, i6);
                    if (!zzla.zzd(bZza3)) {
                        break;
                    }
                    i6++;
                    zzla.zzb(bZza3, cArr, i7);
                    i7++;
                }
                i = i6;
                i5 = i7;
            } else if (zzla.zze(bZza2)) {
                if (i6 < i3) {
                    zzla.zzb(bZza2, zzkz.zza(bArr, i6), cArr, i5);
                    i = i6 + 1;
                    i5++;
                } else {
                    throw zzij.zzh();
                }
            } else if (zzla.zzf(bZza2)) {
                if (i6 < i3 - 1) {
                    int i8 = i6 + 1;
                    zzla.zzb(bZza2, zzkz.zza(bArr, i6), zzkz.zza(bArr, i8), cArr, i5);
                    i = i8 + 1;
                    i5++;
                } else {
                    throw zzij.zzh();
                }
            } else {
                if (i6 >= i3 - 2) {
                    throw zzij.zzh();
                }
                int i9 = i6 + 1;
                byte bZza4 = zzkz.zza(bArr, i6);
                int i10 = i9 + 1;
                zzla.zzb(bZza2, bZza4, zzkz.zza(bArr, i9), zzkz.zza(bArr, i10), cArr, i5);
                i = i10 + 1;
                i5 = i5 + 1 + 1;
            }
        }
        return new String(cArr, 0, i5);
    }

    @Override // com.google.android.gms.internal.measurement.zzld
    final int zza(CharSequence charSequence, byte[] bArr, int i, int i2) {
        char c;
        long j;
        long j2;
        long j3;
        char c2;
        int i3;
        char cCharAt;
        long j4 = i;
        long j5 = i2 + j4;
        int length = charSequence.length();
        if (length > i2 || bArr.length - i2 < i) {
            throw new ArrayIndexOutOfBoundsException(new StringBuilder(37).append("Failed writing ").append(charSequence.charAt(length - 1)).append(" at index ").append(i + i2).toString());
        }
        int i4 = 0;
        while (true) {
            c = 128;
            j = 1;
            if (i4 >= length || (cCharAt = charSequence.charAt(i4)) >= 128) {
                break;
            }
            zzkz.zza(bArr, j4, (byte) cCharAt);
            i4++;
            j4 = 1 + j4;
        }
        if (i4 == length) {
            return (int) j4;
        }
        while (i4 < length) {
            char cCharAt2 = charSequence.charAt(i4);
            if (cCharAt2 < c && j4 < j5) {
                long j6 = j4 + j;
                zzkz.zza(bArr, j4, (byte) cCharAt2);
                j3 = j;
                j2 = j6;
                c2 = c;
            } else if (cCharAt2 < 2048 && j4 <= j5 - 2) {
                long j7 = j4 + j;
                zzkz.zza(bArr, j4, (byte) ((cCharAt2 >>> 6) | 960));
                long j8 = j7 + j;
                zzkz.zza(bArr, j7, (byte) ((cCharAt2 & '?') | 128));
                long j9 = j;
                c2 = 128;
                j2 = j8;
                j3 = j9;
            } else {
                if ((cCharAt2 >= 55296 && 57343 >= cCharAt2) || j4 > j5 - 3) {
                    if (j4 <= j5 - 4) {
                        int i5 = i4 + 1;
                        if (i5 != length) {
                            char cCharAt3 = charSequence.charAt(i5);
                            if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                long j10 = j4 + 1;
                                zzkz.zza(bArr, j4, (byte) ((codePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                                long j11 = j10 + 1;
                                c2 = 128;
                                zzkz.zza(bArr, j10, (byte) (((codePoint >>> 12) & 63) | 128));
                                long j12 = j11 + 1;
                                zzkz.zza(bArr, j11, (byte) (((codePoint >>> 6) & 63) | 128));
                                j3 = 1;
                                j2 = j12 + 1;
                                zzkz.zza(bArr, j12, (byte) ((codePoint & 63) | 128));
                                i4 = i5;
                            } else {
                                i4 = i5;
                            }
                        }
                        throw new zzlf(i4 - 1, length);
                    }
                    if (55296 <= cCharAt2 && cCharAt2 <= 57343 && ((i3 = i4 + 1) == length || !Character.isSurrogatePair(cCharAt2, charSequence.charAt(i3)))) {
                        throw new zzlf(i4, length);
                    }
                    throw new ArrayIndexOutOfBoundsException(new StringBuilder(46).append("Failed writing ").append(cCharAt2).append(" at index ").append(j4).toString());
                }
                long j13 = j4 + j;
                zzkz.zza(bArr, j4, (byte) ((cCharAt2 >>> '\f') | 480));
                long j14 = j13 + j;
                zzkz.zza(bArr, j13, (byte) (((cCharAt2 >>> 6) & 63) | 128));
                zzkz.zza(bArr, j14, (byte) ((cCharAt2 & '?') | 128));
                j2 = j14 + 1;
                j3 = 1;
                c2 = 128;
            }
            i4++;
            c = c2;
            long j15 = j3;
            j4 = j2;
            j = j15;
        }
        return (int) j4;
    }

    private static int zza(byte[] bArr, int i, long j, int i2) {
        if (i2 == 0) {
            return zzlb.zzb(i);
        }
        if (i2 == 1) {
            return zzlb.zzb(i, zzkz.zza(bArr, j));
        }
        if (i2 == 2) {
            return zzlb.zzb(i, zzkz.zza(bArr, j), zzkz.zza(bArr, j + 1));
        }
        throw new AssertionError();
    }
}
