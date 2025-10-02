package com.google.android.gms.internal.measurement;

import com.google.common.base.Ascii;
import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzgl {
    static int zza(byte[] bArr, int i, zzgo zzgoVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zza(b, bArr, i2, zzgoVar);
        }
        zzgoVar.zza = b;
        return i2;
    }

    static int zza(int i, byte[] bArr, int i2, zzgo zzgoVar) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzgoVar.zza = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzgoVar.zza = i5 | (b2 << Ascii.SO);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzgoVar.zza = i7 | (b3 << Ascii.NAK);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzgoVar.zza = i9 | (b4 << Ascii.FS);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzgoVar.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzb(byte[] bArr, int i, zzgo zzgoVar) {
        int i2 = i + 1;
        long j = bArr[i];
        if (j >= 0) {
            zzgoVar.zzb = j;
            return i2;
        }
        int i3 = i2 + 1;
        byte b = bArr[i2];
        long j2 = (j & 127) | ((b & Byte.MAX_VALUE) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            i4 += 7;
            j2 |= (r10 & Byte.MAX_VALUE) << i4;
            b = bArr[i3];
            i3 = i5;
        }
        zzgoVar.zzb = j2;
        return i3;
    }

    static int zza(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    static long zzb(byte[] bArr, int i) {
        return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
    }

    static double zzc(byte[] bArr, int i) {
        return Double.longBitsToDouble(zzb(bArr, i));
    }

    static float zzd(byte[] bArr, int i) {
        return Float.intBitsToFloat(zza(bArr, i));
    }

    static int zzc(byte[] bArr, int i, zzgo zzgoVar) throws zzij {
        int iZza = zza(bArr, i, zzgoVar);
        int i2 = zzgoVar.zza;
        if (i2 < 0) {
            throw zzij.zzb();
        }
        if (i2 == 0) {
            zzgoVar.zzc = "";
            return iZza;
        }
        zzgoVar.zzc = new String(bArr, iZza, i2, zzia.zza);
        return iZza + i2;
    }

    static int zzd(byte[] bArr, int i, zzgo zzgoVar) throws zzij {
        int iZza = zza(bArr, i, zzgoVar);
        int i2 = zzgoVar.zza;
        if (i2 < 0) {
            throw zzij.zzb();
        }
        if (i2 == 0) {
            zzgoVar.zzc = "";
            return iZza;
        }
        zzgoVar.zzc = zzlb.zzb(bArr, iZza, i2);
        return iZza + i2;
    }

    static int zze(byte[] bArr, int i, zzgo zzgoVar) throws zzij {
        int iZza = zza(bArr, i, zzgoVar);
        int i2 = zzgoVar.zza;
        if (i2 < 0) {
            throw zzij.zzb();
        }
        if (i2 > bArr.length - iZza) {
            throw zzij.zza();
        }
        if (i2 == 0) {
            zzgoVar.zzc = zzgp.zza;
            return iZza;
        }
        zzgoVar.zzc = zzgp.zza(bArr, iZza, i2);
        return iZza + i2;
    }

    static int zza(zzkb zzkbVar, byte[] bArr, int i, int i2, zzgo zzgoVar) throws IOException {
        int iZza = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZza = zza(i3, bArr, iZza, zzgoVar);
            i3 = zzgoVar.zza;
        }
        int i4 = iZza;
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzij.zza();
        }
        Object objZza = zzkbVar.zza();
        int i5 = i3 + i4;
        zzkbVar.zza(objZza, bArr, i4, i5, zzgoVar);
        zzkbVar.zzc(objZza);
        zzgoVar.zzc = objZza;
        return i5;
    }

    static int zza(zzkb zzkbVar, byte[] bArr, int i, int i2, int i3, zzgo zzgoVar) throws IOException {
        zzjn zzjnVar = (zzjn) zzkbVar;
        Object objZza = zzjnVar.zza();
        int iZza = zzjnVar.zza((zzjn) objZza, bArr, i, i2, i3, zzgoVar);
        zzjnVar.zzc((zzjn) objZza);
        zzgoVar.zzc = objZza;
        return iZza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzig<?> zzigVar, zzgo zzgoVar) {
        zzib zzibVar = (zzib) zzigVar;
        int iZza = zza(bArr, i2, zzgoVar);
        zzibVar.zzd(zzgoVar.zza);
        while (iZza < i3) {
            int iZza2 = zza(bArr, iZza, zzgoVar);
            if (i != zzgoVar.zza) {
                break;
            }
            iZza = zza(bArr, iZza2, zzgoVar);
            zzibVar.zzd(zzgoVar.zza);
        }
        return iZza;
    }

    static int zza(byte[] bArr, int i, zzig<?> zzigVar, zzgo zzgoVar) throws IOException {
        zzib zzibVar = (zzib) zzigVar;
        int iZza = zza(bArr, i, zzgoVar);
        int i2 = zzgoVar.zza + iZza;
        while (iZza < i2) {
            iZza = zza(bArr, iZza, zzgoVar);
            zzibVar.zzd(zzgoVar.zza);
        }
        if (iZza == i2) {
            return iZza;
        }
        throw zzij.zza();
    }

    static int zza(zzkb<?> zzkbVar, int i, byte[] bArr, int i2, int i3, zzig<?> zzigVar, zzgo zzgoVar) throws IOException {
        int iZza = zza(zzkbVar, bArr, i2, i3, zzgoVar);
        zzigVar.add(zzgoVar.zzc);
        while (iZza < i3) {
            int iZza2 = zza(bArr, iZza, zzgoVar);
            if (i != zzgoVar.zza) {
                break;
            }
            iZza = zza(zzkbVar, bArr, iZza2, i3, zzgoVar);
            zzigVar.add(zzgoVar.zzc);
        }
        return iZza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzks zzksVar, zzgo zzgoVar) throws zzij {
        if ((i >>> 3) == 0) {
            throw zzij.zzd();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            int iZzb = zzb(bArr, i2, zzgoVar);
            zzksVar.zza(i, Long.valueOf(zzgoVar.zzb));
            return iZzb;
        }
        if (i4 == 1) {
            zzksVar.zza(i, Long.valueOf(zzb(bArr, i2)));
            return i2 + 8;
        }
        if (i4 == 2) {
            int iZza = zza(bArr, i2, zzgoVar);
            int i5 = zzgoVar.zza;
            if (i5 < 0) {
                throw zzij.zzb();
            }
            if (i5 > bArr.length - iZza) {
                throw zzij.zza();
            }
            if (i5 == 0) {
                zzksVar.zza(i, zzgp.zza);
            } else {
                zzksVar.zza(i, zzgp.zza(bArr, iZza, i5));
            }
            return iZza + i5;
        }
        if (i4 != 3) {
            if (i4 == 5) {
                zzksVar.zza(i, Integer.valueOf(zza(bArr, i2)));
                return i2 + 4;
            }
            throw zzij.zzd();
        }
        zzks zzksVarZzb = zzks.zzb();
        int i6 = (i & (-8)) | 4;
        int i7 = 0;
        while (true) {
            if (i2 >= i3) {
                break;
            }
            int iZza2 = zza(bArr, i2, zzgoVar);
            int i8 = zzgoVar.zza;
            i7 = i8;
            if (i8 == i6) {
                i2 = iZza2;
                break;
            }
            int iZza3 = zza(i7, bArr, iZza2, i3, zzksVarZzb, zzgoVar);
            i7 = i8;
            i2 = iZza3;
        }
        if (i2 > i3 || i7 != i6) {
            throw zzij.zzg();
        }
        zzksVar.zza(i, zzksVarZzb);
        return i2;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzgo zzgoVar) throws zzij {
        if ((i >>> 3) == 0) {
            throw zzij.zzd();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            return zzb(bArr, i2, zzgoVar);
        }
        if (i4 == 1) {
            return i2 + 8;
        }
        if (i4 == 2) {
            return zza(bArr, i2, zzgoVar) + zzgoVar.zza;
        }
        if (i4 != 3) {
            if (i4 == 5) {
                return i2 + 4;
            }
            throw zzij.zzd();
        }
        int i5 = (i & (-8)) | 4;
        int i6 = 0;
        while (i2 < i3) {
            i2 = zza(bArr, i2, zzgoVar);
            i6 = zzgoVar.zza;
            if (i6 == i5) {
                break;
            }
            i2 = zza(i6, bArr, i2, i3, zzgoVar);
        }
        if (i2 > i3 || i6 != i5) {
            throw zzij.zzg();
        }
        return i2;
    }
}
