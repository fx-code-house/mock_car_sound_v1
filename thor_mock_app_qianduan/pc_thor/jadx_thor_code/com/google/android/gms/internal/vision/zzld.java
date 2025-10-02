package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzld {
    private static final zzle zzadg;

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzce(int i) {
        if (i > -12) {
            return -1;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzf(int i, int i2, int i3) {
        if (i > -12 || i2 > -65 || i3 > -65) {
            return -1;
        }
        return (i ^ (i2 << 8)) ^ (i3 << 16);
    }

    public static boolean zzg(byte[] bArr) {
        return zzadg.zzf(bArr, 0, bArr.length);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzx(int i, int i2) {
        if (i > -12 || i2 > -65) {
            return -1;
        }
        return i ^ (i2 << 8);
    }

    public static boolean zzf(byte[] bArr, int i, int i2) {
        return zzadg.zzf(bArr, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzg(byte[] bArr, int i, int i2) {
        byte b = bArr[i - 1];
        int i3 = i2 - i;
        if (i3 == 0) {
            return zzce(b);
        }
        if (i3 == 1) {
            return zzx(b, bArr[i]);
        }
        if (i3 == 2) {
            return zzf(b, bArr[i], bArr[i + 1]);
        }
        throw new AssertionError();
    }

    static int zza(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < 128) {
            i2++;
        }
        int i3 = length;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char cCharAt = charSequence.charAt(i2);
            if (cCharAt < 2048) {
                i3 += (127 - cCharAt) >>> 31;
                i2++;
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char cCharAt2 = charSequence.charAt(i2);
                    if (cCharAt2 < 2048) {
                        i += (127 - cCharAt2) >>> 31;
                    } else {
                        i += 2;
                        if (55296 <= cCharAt2 && cCharAt2 <= 57343) {
                            if (Character.codePointAt(charSequence, i2) < 65536) {
                                throw new zzlg(i2, length2);
                            }
                            i2++;
                        }
                    }
                    i2++;
                }
                i3 += i;
            }
        }
        if (i3 >= length) {
            return i3;
        }
        throw new IllegalArgumentException(new StringBuilder(54).append("UTF-8 length does not fit in int: ").append(i3 + 4294967296L).toString());
    }

    static int zza(CharSequence charSequence, byte[] bArr, int i, int i2) {
        return zzadg.zzb(charSequence, bArr, i, i2);
    }

    static String zzh(byte[] bArr, int i, int i2) throws zzin {
        return zzadg.zzh(bArr, i, i2);
    }

    static {
        zzle zzlhVar;
        if ((zzla.zzje() && zzla.zzjf()) && !zzgl.zzel()) {
            zzlhVar = new zzlj();
        } else {
            zzlhVar = new zzlh();
        }
        zzadg = zzlhVar;
    }
}
