package com.google.android.gms.internal.play_billing;

import com.google.common.base.Ascii;
import java.io.IOException;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
final class zzao {
    static int zza(byte[] bArr, int i, zzan zzanVar) throws zzci {
        int iZzj = zzj(bArr, i, zzanVar);
        int i2 = zzanVar.zza;
        if (i2 < 0) {
            throw zzci.zzd();
        }
        if (i2 > bArr.length - iZzj) {
            throw zzci.zzg();
        }
        if (i2 == 0) {
            zzanVar.zzc = zzba.zzb;
            return iZzj;
        }
        zzanVar.zzc = zzba.zzl(bArr, iZzj, i2);
        return iZzj + i2;
    }

    static int zzb(byte[] bArr, int i) {
        int i2 = bArr[i] & 255;
        int i3 = bArr[i + 1] & 255;
        int i4 = bArr[i + 2] & 255;
        return ((bArr[i + 3] & 255) << 24) | (i3 << 8) | i2 | (i4 << 16);
    }

    static int zzc(zzdp zzdpVar, byte[] bArr, int i, int i2, int i3, zzan zzanVar) throws IOException {
        Object objZze = zzdpVar.zze();
        int iZzn = zzn(objZze, zzdpVar, bArr, i, i2, i3, zzanVar);
        zzdpVar.zzf(objZze);
        zzanVar.zzc = objZze;
        return iZzn;
    }

    static int zzd(zzdp zzdpVar, byte[] bArr, int i, int i2, zzan zzanVar) throws IOException {
        Object objZze = zzdpVar.zze();
        int iZzo = zzo(objZze, zzdpVar, bArr, i, i2, zzanVar);
        zzdpVar.zzf(objZze);
        zzanVar.zzc = objZze;
        return iZzo;
    }

    static int zze(zzdp zzdpVar, int i, byte[] bArr, int i2, int i3, zzcf zzcfVar, zzan zzanVar) throws IOException {
        int iZzd = zzd(zzdpVar, bArr, i2, i3, zzanVar);
        zzcfVar.add(zzanVar.zzc);
        while (iZzd < i3) {
            int iZzj = zzj(bArr, iZzd, zzanVar);
            if (i != zzanVar.zza) {
                break;
            }
            iZzd = zzd(zzdpVar, bArr, iZzj, i3, zzanVar);
            zzcfVar.add(zzanVar.zzc);
        }
        return iZzd;
    }

    static int zzf(byte[] bArr, int i, zzcf zzcfVar, zzan zzanVar) throws IOException {
        zzcc zzccVar = (zzcc) zzcfVar;
        int iZzj = zzj(bArr, i, zzanVar);
        int i2 = zzanVar.zza + iZzj;
        while (iZzj < i2) {
            iZzj = zzj(bArr, iZzj, zzanVar);
            zzccVar.zzf(zzanVar.zza);
        }
        if (iZzj == i2) {
            return iZzj;
        }
        throw zzci.zzg();
    }

    static int zzg(byte[] bArr, int i, zzan zzanVar) throws zzci {
        int iZzj = zzj(bArr, i, zzanVar);
        int i2 = zzanVar.zza;
        if (i2 < 0) {
            throw zzci.zzd();
        }
        if (i2 == 0) {
            zzanVar.zzc = "";
            return iZzj;
        }
        zzanVar.zzc = new String(bArr, iZzj, i2, zzcg.zzb);
        return iZzj + i2;
    }

    static int zzh(byte[] bArr, int i, zzan zzanVar) throws zzci {
        int iZzj = zzj(bArr, i, zzanVar);
        int i2 = zzanVar.zza;
        if (i2 < 0) {
            throw zzci.zzd();
        }
        if (i2 == 0) {
            zzanVar.zzc = "";
            return iZzj;
        }
        int i3 = zzev.zza;
        int length = bArr.length;
        if ((((length - iZzj) - i2) | iZzj | i2) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(length), Integer.valueOf(iZzj), Integer.valueOf(i2)));
        }
        int i4 = iZzj + i2;
        char[] cArr = new char[i2];
        int i5 = 0;
        while (iZzj < i4) {
            byte b = bArr[iZzj];
            if (!zzer.zzd(b)) {
                break;
            }
            iZzj++;
            cArr[i5] = (char) b;
            i5++;
        }
        while (iZzj < i4) {
            int i6 = iZzj + 1;
            byte b2 = bArr[iZzj];
            if (zzer.zzd(b2)) {
                int i7 = i5 + 1;
                cArr[i5] = (char) b2;
                iZzj = i6;
                while (true) {
                    i5 = i7;
                    if (iZzj < i4) {
                        byte b3 = bArr[iZzj];
                        if (zzer.zzd(b3)) {
                            iZzj++;
                            i7 = i5 + 1;
                            cArr[i5] = (char) b3;
                        }
                    }
                }
            } else if (b2 < -32) {
                if (i6 >= i4) {
                    throw zzci.zzc();
                }
                zzer.zzc(b2, bArr[i6], cArr, i5);
                iZzj = i6 + 1;
                i5++;
            } else if (b2 < -16) {
                if (i6 >= i4 - 1) {
                    throw zzci.zzc();
                }
                int i8 = i6 + 1;
                zzer.zzb(b2, bArr[i6], bArr[i8], cArr, i5);
                iZzj = i8 + 1;
                i5++;
            } else {
                if (i6 >= i4 - 2) {
                    throw zzci.zzc();
                }
                int i9 = i6 + 1;
                byte b4 = bArr[i6];
                int i10 = i9 + 1;
                zzer.zza(b2, b4, bArr[i9], bArr[i10], cArr, i5);
                i5 += 2;
                iZzj = i10 + 1;
            }
        }
        zzanVar.zzc = new String(cArr, 0, i5);
        return i4;
    }

    static int zzi(int i, byte[] bArr, int i2, int i3, zzeh zzehVar, zzan zzanVar) throws zzci {
        if ((i >>> 3) == 0) {
            throw zzci.zzb();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            int iZzm = zzm(bArr, i2, zzanVar);
            zzehVar.zzj(i, Long.valueOf(zzanVar.zzb));
            return iZzm;
        }
        if (i4 == 1) {
            zzehVar.zzj(i, Long.valueOf(zzp(bArr, i2)));
            return i2 + 8;
        }
        if (i4 == 2) {
            int iZzj = zzj(bArr, i2, zzanVar);
            int i5 = zzanVar.zza;
            if (i5 < 0) {
                throw zzci.zzd();
            }
            if (i5 > bArr.length - iZzj) {
                throw zzci.zzg();
            }
            if (i5 == 0) {
                zzehVar.zzj(i, zzba.zzb);
            } else {
                zzehVar.zzj(i, zzba.zzl(bArr, iZzj, i5));
            }
            return iZzj + i5;
        }
        if (i4 != 3) {
            if (i4 != 5) {
                throw zzci.zzb();
            }
            zzehVar.zzj(i, Integer.valueOf(zzb(bArr, i2)));
            return i2 + 4;
        }
        int i6 = (i & (-8)) | 4;
        zzeh zzehVarZzf = zzeh.zzf();
        int i7 = 0;
        while (true) {
            if (i2 >= i3) {
                break;
            }
            int iZzj2 = zzj(bArr, i2, zzanVar);
            int i8 = zzanVar.zza;
            i7 = i8;
            if (i8 == i6) {
                i2 = iZzj2;
                break;
            }
            int iZzi = zzi(i7, bArr, iZzj2, i3, zzehVarZzf, zzanVar);
            i7 = i8;
            i2 = iZzi;
        }
        if (i2 > i3 || i7 != i6) {
            throw zzci.zze();
        }
        zzehVar.zzj(i, zzehVarZzf);
        return i2;
    }

    static int zzj(byte[] bArr, int i, zzan zzanVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zzk(b, bArr, i2, zzanVar);
        }
        zzanVar.zza = b;
        return i2;
    }

    static int zzk(int i, byte[] bArr, int i2, zzan zzanVar) {
        byte b = bArr[i2];
        int i3 = i2 + 1;
        int i4 = i & 127;
        if (b >= 0) {
            zzanVar.zza = i4 | (b << 7);
            return i3;
        }
        int i5 = i4 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i3 + 1;
        byte b2 = bArr[i3];
        if (b2 >= 0) {
            zzanVar.zza = i5 | (b2 << Ascii.SO);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzanVar.zza = i7 | (b3 << Ascii.NAK);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzanVar.zza = i9 | (b4 << Ascii.FS);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzanVar.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzl(int i, byte[] bArr, int i2, int i3, zzcf zzcfVar, zzan zzanVar) {
        zzcc zzccVar = (zzcc) zzcfVar;
        int iZzj = zzj(bArr, i2, zzanVar);
        zzccVar.zzf(zzanVar.zza);
        while (iZzj < i3) {
            int iZzj2 = zzj(bArr, iZzj, zzanVar);
            if (i != zzanVar.zza) {
                break;
            }
            iZzj = zzj(bArr, iZzj2, zzanVar);
            zzccVar.zzf(zzanVar.zza);
        }
        return iZzj;
    }

    static int zzm(byte[] bArr, int i, zzan zzanVar) {
        long j = bArr[i];
        int i2 = i + 1;
        if (j >= 0) {
            zzanVar.zzb = j;
            return i2;
        }
        int i3 = i2 + 1;
        byte b = bArr[i2];
        long j2 = (j & 127) | ((b & Byte.MAX_VALUE) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            i4 += 7;
            j2 |= (b2 & Byte.MAX_VALUE) << i4;
            i3 = i5;
            b = b2;
        }
        zzanVar.zzb = j2;
        return i3;
    }

    static int zzn(Object obj, zzdp zzdpVar, byte[] bArr, int i, int i2, int i3, zzan zzanVar) throws IOException {
        int iZzc = ((zzdi) zzdpVar).zzc(obj, bArr, i, i2, i3, zzanVar);
        zzanVar.zzc = obj;
        return iZzc;
    }

    static int zzo(Object obj, zzdp zzdpVar, byte[] bArr, int i, int i2, zzan zzanVar) throws IOException {
        int iZzk = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZzk = zzk(i3, bArr, iZzk, zzanVar);
            i3 = zzanVar.zza;
        }
        int i4 = iZzk;
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzci.zzg();
        }
        int i5 = i3 + i4;
        zzdpVar.zzh(obj, bArr, i4, i5, zzanVar);
        zzanVar.zzc = obj;
        return i5;
    }

    static long zzp(byte[] bArr, int i) {
        return (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48) | ((bArr[i + 7] & 255) << 56);
    }
}
