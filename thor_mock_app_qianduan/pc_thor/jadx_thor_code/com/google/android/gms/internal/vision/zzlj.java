package com.google.android.gms.internal.vision;

import com.google.android.exoplayer2.extractor.ts.PsExtractor;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzlj extends zzle {
    zzlj() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0065, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x008f, code lost:
    
        return -1;
     */
    @Override // com.google.android.gms.internal.vision.zzle
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final int zzb(int r18, byte[] r19, int r20, int r21) {
        /*
            Method dump skipped, instructions count: 221
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzlj.zzb(int, byte[], int, int):int");
    }

    @Override // com.google.android.gms.internal.vision.zzle
    final String zzh(byte[] bArr, int i, int i2) throws zzin {
        if ((i | i2 | ((bArr.length - i) - i2)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
        }
        int i3 = i + i2;
        char[] cArr = new char[i2];
        int i4 = 0;
        while (i < i3) {
            byte bZza = zzla.zza(bArr, i);
            if (!zzlf.zzd(bZza)) {
                break;
            }
            i++;
            zzlf.zza(bZza, cArr, i4);
            i4++;
        }
        int i5 = i4;
        while (i < i3) {
            int i6 = i + 1;
            byte bZza2 = zzla.zza(bArr, i);
            if (zzlf.zzd(bZza2)) {
                int i7 = i5 + 1;
                zzlf.zza(bZza2, cArr, i5);
                while (i6 < i3) {
                    byte bZza3 = zzla.zza(bArr, i6);
                    if (!zzlf.zzd(bZza3)) {
                        break;
                    }
                    i6++;
                    zzlf.zza(bZza3, cArr, i7);
                    i7++;
                }
                i = i6;
                i5 = i7;
            } else if (zzlf.zze(bZza2)) {
                if (i6 < i3) {
                    zzlf.zza(bZza2, zzla.zza(bArr, i6), cArr, i5);
                    i = i6 + 1;
                    i5++;
                } else {
                    throw zzin.zzho();
                }
            } else if (zzlf.zzf(bZza2)) {
                if (i6 < i3 - 1) {
                    int i8 = i6 + 1;
                    zzlf.zza(bZza2, zzla.zza(bArr, i6), zzla.zza(bArr, i8), cArr, i5);
                    i = i8 + 1;
                    i5++;
                } else {
                    throw zzin.zzho();
                }
            } else {
                if (i6 >= i3 - 2) {
                    throw zzin.zzho();
                }
                int i9 = i6 + 1;
                byte bZza4 = zzla.zza(bArr, i6);
                int i10 = i9 + 1;
                zzlf.zza(bZza2, bZza4, zzla.zza(bArr, i9), zzla.zza(bArr, i10), cArr, i5);
                i = i10 + 1;
                i5 = i5 + 1 + 1;
            }
        }
        return new String(cArr, 0, i5);
    }

    @Override // com.google.android.gms.internal.vision.zzle
    final int zzb(CharSequence charSequence, byte[] bArr, int i, int i2) {
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
            zzla.zza(bArr, j4, (byte) cCharAt);
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
                zzla.zza(bArr, j4, (byte) cCharAt2);
                j3 = j;
                j2 = j6;
                c2 = c;
            } else if (cCharAt2 < 2048 && j4 <= j5 - 2) {
                long j7 = j4 + j;
                zzla.zza(bArr, j4, (byte) ((cCharAt2 >>> 6) | 960));
                long j8 = j7 + j;
                zzla.zza(bArr, j7, (byte) ((cCharAt2 & '?') | 128));
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
                                zzla.zza(bArr, j4, (byte) ((codePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                                long j11 = j10 + 1;
                                c2 = 128;
                                zzla.zza(bArr, j10, (byte) (((codePoint >>> 12) & 63) | 128));
                                long j12 = j11 + 1;
                                zzla.zza(bArr, j11, (byte) (((codePoint >>> 6) & 63) | 128));
                                j3 = 1;
                                j2 = j12 + 1;
                                zzla.zza(bArr, j12, (byte) ((codePoint & 63) | 128));
                                i4 = i5;
                            } else {
                                i4 = i5;
                            }
                        }
                        throw new zzlg(i4 - 1, length);
                    }
                    if (55296 <= cCharAt2 && cCharAt2 <= 57343 && ((i3 = i4 + 1) == length || !Character.isSurrogatePair(cCharAt2, charSequence.charAt(i3)))) {
                        throw new zzlg(i4, length);
                    }
                    throw new ArrayIndexOutOfBoundsException(new StringBuilder(46).append("Failed writing ").append(cCharAt2).append(" at index ").append(j4).toString());
                }
                long j13 = j4 + j;
                zzla.zza(bArr, j4, (byte) ((cCharAt2 >>> '\f') | 480));
                long j14 = j13 + j;
                zzla.zza(bArr, j13, (byte) (((cCharAt2 >>> 6) & 63) | 128));
                zzla.zza(bArr, j14, (byte) ((cCharAt2 & '?') | 128));
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
            return zzld.zzce(i);
        }
        if (i2 == 1) {
            return zzld.zzx(i, zzla.zza(bArr, j));
        }
        if (i2 == 2) {
            return zzld.zzf(i, zzla.zza(bArr, j), zzla.zza(bArr, j + 1));
        }
        throw new AssertionError();
    }
}
