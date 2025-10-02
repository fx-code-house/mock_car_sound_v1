package com.google.android.gms.internal.vision;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzhg extends zzhe {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzug;
    private int zzuh;
    private int zzui;
    private int zzuj;
    private int zzuk;

    private zzhg(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzuk = Integer.MAX_VALUE;
        this.buffer = bArr;
        this.limit = i2 + i;
        this.pos = i;
        this.zzui = i;
        this.zzug = z;
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final int zzfr() throws IOException {
        if (zzen()) {
            this.zzuj = 0;
            return 0;
        }
        int iZzfu = zzfu();
        this.zzuj = iZzfu;
        if ((iZzfu >>> 3) != 0) {
            return iZzfu;
        }
        throw zzin.zzhk();
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final void zzax(int i) throws zzin {
        if (this.zzuj != i) {
            throw zzin.zzhl();
        }
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final boolean zzay(int i) throws IOException {
        int iZzfr;
        int i2 = i & 7;
        int i3 = 0;
        if (i2 == 0) {
            if (this.limit - this.pos >= 10) {
                while (i3 < 10) {
                    byte[] bArr = this.buffer;
                    int i4 = this.pos;
                    this.pos = i4 + 1;
                    if (bArr[i4] < 0) {
                        i3++;
                    }
                }
                throw zzin.zzhj();
            }
            while (i3 < 10) {
                if (zzfz() < 0) {
                    i3++;
                }
            }
            throw zzin.zzhj();
            return true;
        }
        if (i2 == 1) {
            zzbc(8);
            return true;
        }
        if (i2 == 2) {
            zzbc(zzfu());
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                return false;
            }
            if (i2 == 5) {
                zzbc(4);
                return true;
            }
            throw zzin.zzhm();
        }
        do {
            iZzfr = zzfr();
            if (iZzfr == 0) {
                break;
            }
        } while (zzay(iZzfr));
        zzax(((i >>> 3) << 3) | 4);
        return true;
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(zzfx());
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(zzfw());
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final long zzeq() throws IOException {
        return zzfv();
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final long zzer() throws IOException {
        return zzfv();
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final int zzes() throws IOException {
        return zzfu();
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final long zzet() throws IOException {
        return zzfx();
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final int zzeu() throws IOException {
        return zzfw();
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final boolean zzev() throws IOException {
        return zzfv() != 0;
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final String readString() throws IOException {
        int iZzfu = zzfu();
        if (iZzfu > 0 && iZzfu <= this.limit - this.pos) {
            String str = new String(this.buffer, this.pos, iZzfu, zzie.UTF_8);
            this.pos += iZzfu;
            return str;
        }
        if (iZzfu == 0) {
            return "";
        }
        if (iZzfu < 0) {
            throw zzin.zzhi();
        }
        throw zzin.zzhh();
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final String zzew() throws IOException {
        int iZzfu = zzfu();
        if (iZzfu > 0) {
            int i = this.limit;
            int i2 = this.pos;
            if (iZzfu <= i - i2) {
                String strZzh = zzld.zzh(this.buffer, i2, iZzfu);
                this.pos += iZzfu;
                return strZzh;
            }
        }
        if (iZzfu == 0) {
            return "";
        }
        if (iZzfu <= 0) {
            throw zzin.zzhi();
        }
        throw zzin.zzhh();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
    @Override // com.google.android.gms.internal.vision.zzhe
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.google.android.gms.internal.vision.zzgs zzex() throws java.io.IOException {
        /*
            r3 = this;
            int r0 = r3.zzfu()
            if (r0 <= 0) goto L19
            int r1 = r3.limit
            int r2 = r3.pos
            int r1 = r1 - r2
            if (r0 > r1) goto L19
            byte[] r1 = r3.buffer
            com.google.android.gms.internal.vision.zzgs r1 = com.google.android.gms.internal.vision.zzgs.zza(r1, r2, r0)
            int r2 = r3.pos
            int r2 = r2 + r0
            r3.pos = r2
            return r1
        L19:
            if (r0 != 0) goto L1e
            com.google.android.gms.internal.vision.zzgs r0 = com.google.android.gms.internal.vision.zzgs.zztt
            return r0
        L1e:
            if (r0 <= 0) goto L31
            int r1 = r3.limit
            int r2 = r3.pos
            int r1 = r1 - r2
            if (r0 > r1) goto L31
            int r0 = r0 + r2
            r3.pos = r0
            byte[] r1 = r3.buffer
            byte[] r0 = java.util.Arrays.copyOfRange(r1, r2, r0)
            goto L37
        L31:
            if (r0 > 0) goto L41
            if (r0 != 0) goto L3c
            byte[] r0 = com.google.android.gms.internal.vision.zzie.zzyy
        L37:
            com.google.android.gms.internal.vision.zzgs r0 = com.google.android.gms.internal.vision.zzgs.zzd(r0)
            return r0
        L3c:
            com.google.android.gms.internal.vision.zzin r0 = com.google.android.gms.internal.vision.zzin.zzhi()
            throw r0
        L41:
            com.google.android.gms.internal.vision.zzin r0 = com.google.android.gms.internal.vision.zzin.zzhh()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzhg.zzex():com.google.android.gms.internal.vision.zzgs");
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final int zzey() throws IOException {
        return zzfu();
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final int zzez() throws IOException {
        return zzfu();
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final int zzfa() throws IOException {
        return zzfw();
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final long zzfb() throws IOException {
        return zzfx();
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final int zzfc() throws IOException {
        return zzbb(zzfu());
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final long zzfd() throws IOException {
        return zzr(zzfv());
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0066, code lost:
    
        if (r2[r3] >= 0) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int zzfu() throws java.io.IOException {
        /*
            r5 = this;
            int r0 = r5.pos
            int r1 = r5.limit
            if (r1 == r0) goto L6b
            byte[] r2 = r5.buffer
            int r3 = r0 + 1
            r0 = r2[r0]
            if (r0 < 0) goto L11
            r5.pos = r3
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
            r5.pos = r1
            return r0
        L6b:
            long r0 = r5.zzfs()
            int r0 = (int) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzhg.zzfu():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b0, code lost:
    
        if (r2[r0] >= 0) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final long zzfv() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 189
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzhg.zzfv():long");
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    final long zzfs() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            j |= (r3 & Byte.MAX_VALUE) << i;
            if ((zzfz() & 128) == 0) {
                return j;
            }
        }
        throw zzin.zzhj();
    }

    private final int zzfw() throws IOException {
        int i = this.pos;
        if (this.limit - i < 4) {
            throw zzin.zzhh();
        }
        byte[] bArr = this.buffer;
        this.pos = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    private final long zzfx() throws IOException {
        int i = this.pos;
        if (this.limit - i < 8) {
            throw zzin.zzhh();
        }
        byte[] bArr = this.buffer;
        this.pos = i + 8;
        return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final int zzaz(int i) throws zzin {
        if (i < 0) {
            throw zzin.zzhi();
        }
        int iZzft = i + zzft();
        int i2 = this.zzuk;
        if (iZzft > i2) {
            throw zzin.zzhh();
        }
        this.zzuk = iZzft;
        zzfy();
        return i2;
    }

    private final void zzfy() {
        int i = this.limit + this.zzuh;
        this.limit = i;
        int i2 = i - this.zzui;
        int i3 = this.zzuk;
        if (i2 > i3) {
            int i4 = i2 - i3;
            this.zzuh = i4;
            this.limit = i - i4;
            return;
        }
        this.zzuh = 0;
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final void zzba(int i) {
        this.zzuk = i;
        zzfy();
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final boolean zzen() throws IOException {
        return this.pos == this.limit;
    }

    @Override // com.google.android.gms.internal.vision.zzhe
    public final int zzft() {
        return this.pos - this.zzui;
    }

    private final byte zzfz() throws IOException {
        int i = this.pos;
        if (i == this.limit) {
            throw zzin.zzhh();
        }
        byte[] bArr = this.buffer;
        this.pos = i + 1;
        return bArr[i];
    }

    private final void zzbc(int i) throws IOException {
        if (i >= 0) {
            int i2 = this.limit;
            int i3 = this.pos;
            if (i <= i2 - i3) {
                this.pos = i3 + i;
                return;
            }
        }
        if (i < 0) {
            throw zzin.zzhi();
        }
        throw zzin.zzhh();
    }
}
