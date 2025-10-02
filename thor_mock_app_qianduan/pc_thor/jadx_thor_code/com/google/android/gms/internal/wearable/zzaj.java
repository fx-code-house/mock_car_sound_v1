package com.google.android.gms.internal.wearable;

import com.google.common.base.Ascii;
import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzaj {
    static int zza(byte[] bArr, int i, zzai zzaiVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zzb(b, bArr, i2, zzaiVar);
        }
        zzaiVar.zza = b;
        return i2;
    }

    static int zzb(int i, byte[] bArr, int i2, zzai zzaiVar) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzaiVar.zza = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzaiVar.zza = i5 | (b2 << Ascii.SO);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzaiVar.zza = i7 | (b3 << Ascii.NAK);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzaiVar.zza = i9 | (b4 << Ascii.FS);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzaiVar.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzc(byte[] bArr, int i, zzai zzaiVar) {
        int i2 = i + 1;
        long j = bArr[i];
        if (j >= 0) {
            zzaiVar.zzb = j;
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
        zzaiVar.zzb = j2;
        return i3;
    }

    static int zzd(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    static long zze(byte[] bArr, int i) {
        return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
    }

    static int zzf(byte[] bArr, int i, zzai zzaiVar) throws zzcc {
        int iZza = zza(bArr, i, zzaiVar);
        int i2 = zzaiVar.zza;
        if (i2 < 0) {
            throw zzcc.zzc();
        }
        if (i2 == 0) {
            zzaiVar.zzc = "";
            return iZza;
        }
        zzaiVar.zzc = new String(bArr, iZza, i2, zzca.zza);
        return iZza + i2;
    }

    static int zzg(byte[] bArr, int i, zzai zzaiVar) throws zzcc {
        int iZza = zza(bArr, i, zzaiVar);
        int i2 = zzaiVar.zza;
        if (i2 < 0) {
            throw zzcc.zzc();
        }
        if (i2 == 0) {
            zzaiVar.zzc = "";
            return iZza;
        }
        zzaiVar.zzc = zzel.zze(bArr, iZza, i2);
        return iZza + i2;
    }

    static int zzh(byte[] bArr, int i, zzai zzaiVar) throws zzcc {
        int iZza = zza(bArr, i, zzaiVar);
        int i2 = zzaiVar.zza;
        if (i2 < 0) {
            throw zzcc.zzc();
        }
        if (i2 > bArr.length - iZza) {
            throw zzcc.zzb();
        }
        if (i2 == 0) {
            zzaiVar.zzc = zzau.zzb;
            return iZza;
        }
        zzaiVar.zzc = zzau.zzk(bArr, iZza, i2);
        return iZza + i2;
    }

    static int zzi(zzdi zzdiVar, byte[] bArr, int i, int i2, zzai zzaiVar) throws IOException {
        int iZzb = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZzb = zzb(i3, bArr, iZzb, zzaiVar);
            i3 = zzaiVar.zza;
        }
        int i4 = iZzb;
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzcc.zzb();
        }
        Object objZza = zzdiVar.zza();
        int i5 = i3 + i4;
        zzdiVar.zzh(objZza, bArr, i4, i5, zzaiVar);
        zzdiVar.zzi(objZza);
        zzaiVar.zzc = objZza;
        return i5;
    }

    static int zzj(zzdi zzdiVar, byte[] bArr, int i, int i2, int i3, zzai zzaiVar) throws IOException {
        zzda zzdaVar = (zzda) zzdiVar;
        Object objZza = zzdaVar.zza();
        int iZzg = zzdaVar.zzg(objZza, bArr, i, i2, i3, zzaiVar);
        zzdaVar.zzi(objZza);
        zzaiVar.zzc = objZza;
        return iZzg;
    }

    static int zzk(int i, byte[] bArr, int i2, int i3, zzbz<?> zzbzVar, zzai zzaiVar) {
        zzbt zzbtVar = (zzbt) zzbzVar;
        int iZza = zza(bArr, i2, zzaiVar);
        zzbtVar.zzf(zzaiVar.zza);
        while (iZza < i3) {
            int iZza2 = zza(bArr, iZza, zzaiVar);
            if (i != zzaiVar.zza) {
                break;
            }
            iZza = zza(bArr, iZza2, zzaiVar);
            zzbtVar.zzf(zzaiVar.zza);
        }
        return iZza;
    }

    static int zzl(byte[] bArr, int i, zzbz<?> zzbzVar, zzai zzaiVar) throws IOException {
        zzbt zzbtVar = (zzbt) zzbzVar;
        int iZza = zza(bArr, i, zzaiVar);
        int i2 = zzaiVar.zza + iZza;
        while (iZza < i2) {
            iZza = zza(bArr, iZza, zzaiVar);
            zzbtVar.zzf(zzaiVar.zza);
        }
        if (iZza == i2) {
            return iZza;
        }
        throw zzcc.zzb();
    }

    static int zzm(zzdi<?> zzdiVar, int i, byte[] bArr, int i2, int i3, zzbz<?> zzbzVar, zzai zzaiVar) throws IOException {
        int iZzi = zzi(zzdiVar, bArr, i2, i3, zzaiVar);
        zzbzVar.add(zzaiVar.zzc);
        while (iZzi < i3) {
            int iZza = zza(bArr, iZzi, zzaiVar);
            if (i != zzaiVar.zza) {
                break;
            }
            iZzi = zzi(zzdiVar, bArr, iZza, i3, zzaiVar);
            zzbzVar.add(zzaiVar.zzc);
        }
        return iZzi;
    }

    static int zzn(int i, byte[] bArr, int i2, int i3, zzdx zzdxVar, zzai zzaiVar) throws zzcc {
        if ((i >>> 3) == 0) {
            throw zzcc.zzd();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            int iZzc = zzc(bArr, i2, zzaiVar);
            zzdxVar.zzh(i, Long.valueOf(zzaiVar.zzb));
            return iZzc;
        }
        if (i4 == 1) {
            zzdxVar.zzh(i, Long.valueOf(zze(bArr, i2)));
            return i2 + 8;
        }
        if (i4 == 2) {
            int iZza = zza(bArr, i2, zzaiVar);
            int i5 = zzaiVar.zza;
            if (i5 < 0) {
                throw zzcc.zzc();
            }
            if (i5 > bArr.length - iZza) {
                throw zzcc.zzb();
            }
            if (i5 == 0) {
                zzdxVar.zzh(i, zzau.zzb);
            } else {
                zzdxVar.zzh(i, zzau.zzk(bArr, iZza, i5));
            }
            return iZza + i5;
        }
        if (i4 != 3) {
            if (i4 != 5) {
                throw zzcc.zzd();
            }
            zzdxVar.zzh(i, Integer.valueOf(zzd(bArr, i2)));
            return i2 + 4;
        }
        int i6 = (i & (-8)) | 4;
        zzdx zzdxVarZzb = zzdx.zzb();
        int i7 = 0;
        while (true) {
            if (i2 >= i3) {
                break;
            }
            int iZza2 = zza(bArr, i2, zzaiVar);
            int i8 = zzaiVar.zza;
            if (i8 == i6) {
                i7 = i8;
                i2 = iZza2;
                break;
            }
            i7 = i8;
            i2 = zzn(i8, bArr, iZza2, i3, zzdxVarZzb, zzaiVar);
        }
        if (i2 > i3 || i7 != i6) {
            throw zzcc.zzf();
        }
        zzdxVar.zzh(i, zzdxVarZzb);
        return i2;
    }
}
