package com.google.android.gms.internal.vision;

import com.google.common.base.Ascii;
import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzgk {
    static int zza(byte[] bArr, int i, zzgm zzgmVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zza(b, bArr, i2, zzgmVar);
        }
        zzgmVar.zztk = b;
        return i2;
    }

    static int zza(int i, byte[] bArr, int i2, zzgm zzgmVar) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzgmVar.zztk = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzgmVar.zztk = i5 | (b2 << Ascii.SO);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzgmVar.zztk = i7 | (b3 << Ascii.NAK);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzgmVar.zztk = i9 | (b4 << Ascii.FS);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzgmVar.zztk = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzb(byte[] bArr, int i, zzgm zzgmVar) {
        int i2 = i + 1;
        long j = bArr[i];
        if (j >= 0) {
            zzgmVar.zztl = j;
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
        zzgmVar.zztl = j2;
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

    static int zzc(byte[] bArr, int i, zzgm zzgmVar) throws zzin {
        int iZza = zza(bArr, i, zzgmVar);
        int i2 = zzgmVar.zztk;
        if (i2 < 0) {
            throw zzin.zzhi();
        }
        if (i2 == 0) {
            zzgmVar.zztm = "";
            return iZza;
        }
        zzgmVar.zztm = new String(bArr, iZza, i2, zzie.UTF_8);
        return iZza + i2;
    }

    static int zzd(byte[] bArr, int i, zzgm zzgmVar) throws zzin {
        int iZza = zza(bArr, i, zzgmVar);
        int i2 = zzgmVar.zztk;
        if (i2 < 0) {
            throw zzin.zzhi();
        }
        if (i2 == 0) {
            zzgmVar.zztm = "";
            return iZza;
        }
        zzgmVar.zztm = zzld.zzh(bArr, iZza, i2);
        return iZza + i2;
    }

    static int zze(byte[] bArr, int i, zzgm zzgmVar) throws zzin {
        int iZza = zza(bArr, i, zzgmVar);
        int i2 = zzgmVar.zztk;
        if (i2 < 0) {
            throw zzin.zzhi();
        }
        if (i2 > bArr.length - iZza) {
            throw zzin.zzhh();
        }
        if (i2 == 0) {
            zzgmVar.zztm = zzgs.zztt;
            return iZza;
        }
        zzgmVar.zztm = zzgs.zza(bArr, iZza, i2);
        return iZza + i2;
    }

    static int zza(zzkc zzkcVar, byte[] bArr, int i, int i2, zzgm zzgmVar) throws IOException {
        int iZza = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZza = zza(i3, bArr, iZza, zzgmVar);
            i3 = zzgmVar.zztk;
        }
        int i4 = iZza;
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzin.zzhh();
        }
        Object objNewInstance = zzkcVar.newInstance();
        int i5 = i3 + i4;
        zzkcVar.zza(objNewInstance, bArr, i4, i5, zzgmVar);
        zzkcVar.zzj(objNewInstance);
        zzgmVar.zztm = objNewInstance;
        return i5;
    }

    static int zza(zzkc zzkcVar, byte[] bArr, int i, int i2, int i3, zzgm zzgmVar) throws IOException {
        zzjr zzjrVar = (zzjr) zzkcVar;
        Object objNewInstance = zzjrVar.newInstance();
        int iZza = zzjrVar.zza((zzjr) objNewInstance, bArr, i, i2, i3, zzgmVar);
        zzjrVar.zzj(objNewInstance);
        zzgmVar.zztm = objNewInstance;
        return iZza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzik<?> zzikVar, zzgm zzgmVar) {
        zzif zzifVar = (zzif) zzikVar;
        int iZza = zza(bArr, i2, zzgmVar);
        zzifVar.zzbs(zzgmVar.zztk);
        while (iZza < i3) {
            int iZza2 = zza(bArr, iZza, zzgmVar);
            if (i != zzgmVar.zztk) {
                break;
            }
            iZza = zza(bArr, iZza2, zzgmVar);
            zzifVar.zzbs(zzgmVar.zztk);
        }
        return iZza;
    }

    static int zza(byte[] bArr, int i, zzik<?> zzikVar, zzgm zzgmVar) throws IOException {
        zzif zzifVar = (zzif) zzikVar;
        int iZza = zza(bArr, i, zzgmVar);
        int i2 = zzgmVar.zztk + iZza;
        while (iZza < i2) {
            iZza = zza(bArr, iZza, zzgmVar);
            zzifVar.zzbs(zzgmVar.zztk);
        }
        if (iZza == i2) {
            return iZza;
        }
        throw zzin.zzhh();
    }

    static int zza(zzkc<?> zzkcVar, int i, byte[] bArr, int i2, int i3, zzik<?> zzikVar, zzgm zzgmVar) throws IOException {
        int iZza = zza(zzkcVar, bArr, i2, i3, zzgmVar);
        zzikVar.add(zzgmVar.zztm);
        while (iZza < i3) {
            int iZza2 = zza(bArr, iZza, zzgmVar);
            if (i != zzgmVar.zztk) {
                break;
            }
            iZza = zza(zzkcVar, bArr, iZza2, i3, zzgmVar);
            zzikVar.add(zzgmVar.zztm);
        }
        return iZza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzkx zzkxVar, zzgm zzgmVar) throws zzin {
        if ((i >>> 3) == 0) {
            throw zzin.zzhk();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            int iZzb = zzb(bArr, i2, zzgmVar);
            zzkxVar.zzb(i, Long.valueOf(zzgmVar.zztl));
            return iZzb;
        }
        if (i4 == 1) {
            zzkxVar.zzb(i, Long.valueOf(zzb(bArr, i2)));
            return i2 + 8;
        }
        if (i4 == 2) {
            int iZza = zza(bArr, i2, zzgmVar);
            int i5 = zzgmVar.zztk;
            if (i5 < 0) {
                throw zzin.zzhi();
            }
            if (i5 > bArr.length - iZza) {
                throw zzin.zzhh();
            }
            if (i5 == 0) {
                zzkxVar.zzb(i, zzgs.zztt);
            } else {
                zzkxVar.zzb(i, zzgs.zza(bArr, iZza, i5));
            }
            return iZza + i5;
        }
        if (i4 != 3) {
            if (i4 == 5) {
                zzkxVar.zzb(i, Integer.valueOf(zza(bArr, i2)));
                return i2 + 4;
            }
            throw zzin.zzhk();
        }
        zzkx zzkxVarZzjc = zzkx.zzjc();
        int i6 = (i & (-8)) | 4;
        int i7 = 0;
        while (true) {
            if (i2 >= i3) {
                break;
            }
            int iZza2 = zza(bArr, i2, zzgmVar);
            int i8 = zzgmVar.zztk;
            i7 = i8;
            if (i8 == i6) {
                i2 = iZza2;
                break;
            }
            int iZza3 = zza(i7, bArr, iZza2, i3, zzkxVarZzjc, zzgmVar);
            i7 = i8;
            i2 = iZza3;
        }
        if (i2 > i3 || i7 != i6) {
            throw zzin.zzhn();
        }
        zzkxVar.zzb(i, zzkxVarZzjc);
        return i2;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzgm zzgmVar) throws zzin {
        if ((i >>> 3) == 0) {
            throw zzin.zzhk();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            return zzb(bArr, i2, zzgmVar);
        }
        if (i4 == 1) {
            return i2 + 8;
        }
        if (i4 == 2) {
            return zza(bArr, i2, zzgmVar) + zzgmVar.zztk;
        }
        if (i4 != 3) {
            if (i4 == 5) {
                return i2 + 4;
            }
            throw zzin.zzhk();
        }
        int i5 = (i & (-8)) | 4;
        int i6 = 0;
        while (i2 < i3) {
            i2 = zza(bArr, i2, zzgmVar);
            i6 = zzgmVar.zztk;
            if (i6 == i5) {
                break;
            }
            i2 = zza(i6, bArr, i2, i3, zzgmVar);
        }
        if (i2 > i3 || i6 != i5) {
            throw zzin.zzhn();
        }
        return i2;
    }
}
