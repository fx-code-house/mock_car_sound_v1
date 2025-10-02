package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzhd extends zzhb {
    private final byte[] zzd;
    private final boolean zze;
    private int zzf;
    private int zzg;
    private int zzh;
    private int zzi;
    private int zzj;
    private int zzk;

    private zzhd(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzk = Integer.MAX_VALUE;
        this.zzd = bArr;
        this.zzf = i2 + i;
        this.zzh = i;
        this.zzi = i;
        this.zze = z;
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final int zza() throws IOException {
        if (zzt()) {
            this.zzj = 0;
            return 0;
        }
        int iZzv = zzv();
        this.zzj = iZzv;
        if ((iZzv >>> 3) != 0) {
            return iZzv;
        }
        throw zzij.zzd();
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final void zza(int i) throws zzij {
        if (this.zzj != i) {
            throw zzij.zze();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final boolean zzb(int i) throws IOException {
        int iZza;
        int i2 = i & 7;
        int i3 = 0;
        if (i2 == 0) {
            if (this.zzf - this.zzh >= 10) {
                while (i3 < 10) {
                    byte[] bArr = this.zzd;
                    int i4 = this.zzh;
                    this.zzh = i4 + 1;
                    if (bArr[i4] < 0) {
                        i3++;
                    }
                }
                throw zzij.zzc();
            }
            while (i3 < 10) {
                if (zzaa() < 0) {
                    i3++;
                }
            }
            throw zzij.zzc();
            return true;
        }
        if (i2 == 1) {
            zzf(8);
            return true;
        }
        if (i2 == 2) {
            zzf(zzv());
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                return false;
            }
            if (i2 == 5) {
                zzf(4);
                return true;
            }
            throw zzij.zzf();
        }
        do {
            iZza = zza();
            if (iZza == 0) {
                break;
            }
        } while (zzb(iZza));
        zza(((i >>> 3) << 3) | 4);
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final double zzb() throws IOException {
        return Double.longBitsToDouble(zzy());
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final float zzc() throws IOException {
        return Float.intBitsToFloat(zzx());
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final long zzd() throws IOException {
        return zzw();
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final long zze() throws IOException {
        return zzw();
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final int zzf() throws IOException {
        return zzv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final long zzg() throws IOException {
        return zzy();
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final int zzh() throws IOException {
        return zzx();
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final boolean zzi() throws IOException {
        return zzw() != 0;
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final String zzj() throws IOException {
        int iZzv = zzv();
        if (iZzv > 0 && iZzv <= this.zzf - this.zzh) {
            String str = new String(this.zzd, this.zzh, iZzv, zzia.zza);
            this.zzh += iZzv;
            return str;
        }
        if (iZzv == 0) {
            return "";
        }
        if (iZzv < 0) {
            throw zzij.zzb();
        }
        throw zzij.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final String zzk() throws IOException {
        int iZzv = zzv();
        if (iZzv > 0) {
            int i = this.zzf;
            int i2 = this.zzh;
            if (iZzv <= i - i2) {
                String strZzb = zzlb.zzb(this.zzd, i2, iZzv);
                this.zzh += iZzv;
                return strZzb;
            }
        }
        if (iZzv == 0) {
            return "";
        }
        if (iZzv <= 0) {
            throw zzij.zzb();
        }
        throw zzij.zza();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
    @Override // com.google.android.gms.internal.measurement.zzhb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.google.android.gms.internal.measurement.zzgp zzl() throws java.io.IOException {
        /*
            r3 = this;
            int r0 = r3.zzv()
            if (r0 <= 0) goto L19
            int r1 = r3.zzf
            int r2 = r3.zzh
            int r1 = r1 - r2
            if (r0 > r1) goto L19
            byte[] r1 = r3.zzd
            com.google.android.gms.internal.measurement.zzgp r1 = com.google.android.gms.internal.measurement.zzgp.zza(r1, r2, r0)
            int r2 = r3.zzh
            int r2 = r2 + r0
            r3.zzh = r2
            return r1
        L19:
            if (r0 != 0) goto L1e
            com.google.android.gms.internal.measurement.zzgp r0 = com.google.android.gms.internal.measurement.zzgp.zza
            return r0
        L1e:
            if (r0 <= 0) goto L31
            int r1 = r3.zzf
            int r2 = r3.zzh
            int r1 = r1 - r2
            if (r0 > r1) goto L31
            int r0 = r0 + r2
            r3.zzh = r0
            byte[] r1 = r3.zzd
            byte[] r0 = java.util.Arrays.copyOfRange(r1, r2, r0)
            goto L37
        L31:
            if (r0 > 0) goto L41
            if (r0 != 0) goto L3c
            byte[] r0 = com.google.android.gms.internal.measurement.zzia.zzb
        L37:
            com.google.android.gms.internal.measurement.zzgp r0 = com.google.android.gms.internal.measurement.zzgp.zza(r0)
            return r0
        L3c:
            com.google.android.gms.internal.measurement.zzij r0 = com.google.android.gms.internal.measurement.zzij.zzb()
            throw r0
        L41:
            com.google.android.gms.internal.measurement.zzij r0 = com.google.android.gms.internal.measurement.zzij.zza()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzhd.zzl():com.google.android.gms.internal.measurement.zzgp");
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final int zzm() throws IOException {
        return zzv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final int zzn() throws IOException {
        return zzv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final int zzo() throws IOException {
        return zzx();
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final long zzp() throws IOException {
        return zzy();
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final int zzq() throws IOException {
        return zze(zzv());
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final long zzr() throws IOException {
        return zza(zzw());
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0066, code lost:
    
        if (r2[r3] >= 0) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int zzv() throws java.io.IOException {
        /*
            r5 = this;
            int r0 = r5.zzh
            int r1 = r5.zzf
            if (r1 == r0) goto L6b
            byte[] r2 = r5.zzd
            int r3 = r0 + 1
            r0 = r2[r0]
            if (r0 < 0) goto L11
            r5.zzh = r3
            return r0
        L11:
            int r1 = r1 - r3
            r4 = 9
            if (r1 < r4) goto L6b
            int r1 = r3 + 1
            r3 = r2[r3]
            int r3 = r3 << 7
            r0 = r0 ^ r3
            if (r0 >= 0) goto L22
            r0 = r0 ^ (-128(0xffffffffffffff80, float:NaN))
            goto L68
        L22:
            int r3 = r1 + 1
            r1 = r2[r1]
            int r1 = r1 << 14
            r0 = r0 ^ r1
            if (r0 < 0) goto L2f
            r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
        L2d:
            r1 = r3
            goto L68
        L2f:
            int r1 = r3 + 1
            r3 = r2[r3]
            int r3 = r3 << 21
            r0 = r0 ^ r3
            if (r0 >= 0) goto L3d
            r2 = -2080896(0xffffffffffe03f80, float:NaN)
            r0 = r0 ^ r2
            goto L68
        L3d:
            int r3 = r1 + 1
            r1 = r2[r1]
            int r4 = r1 << 28
            r0 = r0 ^ r4
            r4 = 266354560(0xfe03f80, float:2.2112565E-29)
            r0 = r0 ^ r4
            if (r1 >= 0) goto L2d
            int r1 = r3 + 1
            r3 = r2[r3]
            if (r3 >= 0) goto L68
            int r3 = r1 + 1
            r1 = r2[r1]
            if (r1 >= 0) goto L2d
            int r1 = r3 + 1
            r3 = r2[r3]
            if (r3 >= 0) goto L68
            int r3 = r1 + 1
            r1 = r2[r1]
            if (r1 >= 0) goto L2d
            int r1 = r3 + 1
            r2 = r2[r3]
            if (r2 < 0) goto L6b
        L68:
            r5.zzh = r1
            return r0
        L6b:
            long r0 = r5.zzs()
            int r0 = (int) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzhd.zzv():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b0, code lost:
    
        if (r2[r0] >= 0) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final long zzw() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 189
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzhd.zzw():long");
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    final long zzs() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            j |= (r3 & Byte.MAX_VALUE) << i;
            if ((zzaa() & 128) == 0) {
                return j;
            }
        }
        throw zzij.zzc();
    }

    private final int zzx() throws IOException {
        int i = this.zzh;
        if (this.zzf - i < 4) {
            throw zzij.zza();
        }
        byte[] bArr = this.zzd;
        this.zzh = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    private final long zzy() throws IOException {
        int i = this.zzh;
        if (this.zzf - i < 8) {
            throw zzij.zza();
        }
        byte[] bArr = this.zzd;
        this.zzh = i + 8;
        return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final int zzc(int i) throws zzij {
        if (i < 0) {
            throw zzij.zzb();
        }
        int iZzu = i + zzu();
        int i2 = this.zzk;
        if (iZzu > i2) {
            throw zzij.zza();
        }
        this.zzk = iZzu;
        zzz();
        return i2;
    }

    private final void zzz() {
        int i = this.zzf + this.zzg;
        this.zzf = i;
        int i2 = i - this.zzi;
        int i3 = this.zzk;
        if (i2 > i3) {
            int i4 = i2 - i3;
            this.zzg = i4;
            this.zzf = i - i4;
            return;
        }
        this.zzg = 0;
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final void zzd(int i) {
        this.zzk = i;
        zzz();
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final boolean zzt() throws IOException {
        return this.zzh == this.zzf;
    }

    @Override // com.google.android.gms.internal.measurement.zzhb
    public final int zzu() {
        return this.zzh - this.zzi;
    }

    private final byte zzaa() throws IOException {
        int i = this.zzh;
        if (i == this.zzf) {
            throw zzij.zza();
        }
        byte[] bArr = this.zzd;
        this.zzh = i + 1;
        return bArr[i];
    }

    private final void zzf(int i) throws IOException {
        if (i >= 0) {
            int i2 = this.zzf;
            int i3 = this.zzh;
            if (i <= i2 - i3) {
                this.zzh = i3 + i;
                return;
            }
        }
        if (i < 0) {
            throw zzij.zzb();
        }
        throw zzij.zza();
    }
}
