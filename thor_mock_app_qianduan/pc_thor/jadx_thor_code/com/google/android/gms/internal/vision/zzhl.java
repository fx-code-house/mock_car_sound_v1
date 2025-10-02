package com.google.android.gms.internal.vision;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public abstract class zzhl extends zzgt {
    private static final Logger logger = Logger.getLogger(zzhl.class.getName());
    private static final boolean zzun = zzla.zzje();
    zzhn zzuo;

    private static long zzaa(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public static int zzb(double d) {
        return 8;
    }

    public static int zzbj(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    public static int zzbl(int i) {
        return 4;
    }

    public static int zzbm(int i) {
        return 4;
    }

    private static int zzbo(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static zzhl zze(byte[] bArr) {
        return new zzb(bArr, 0, bArr.length);
    }

    public static int zzl(boolean z) {
        return 1;
    }

    public static int zzt(float f) {
        return 4;
    }

    public static int zzw(long j) {
        int i;
        if (((-128) & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        if (((-34359738368L) & j) != 0) {
            j >>>= 28;
            i = 6;
        } else {
            i = 2;
        }
        if (((-2097152) & j) != 0) {
            i += 2;
            j >>>= 14;
        }
        return (j & (-16384)) != 0 ? i + 1 : i;
    }

    public static int zzy(long j) {
        return 8;
    }

    public static int zzz(long j) {
        return 8;
    }

    public abstract void writeTag(int i, int i2) throws IOException;

    public abstract void zza(int i, long j) throws IOException;

    public abstract void zza(int i, zzgs zzgsVar) throws IOException;

    public abstract void zza(int i, zzjn zzjnVar) throws IOException;

    abstract void zza(int i, zzjn zzjnVar, zzkc zzkcVar) throws IOException;

    public abstract void zza(int i, String str) throws IOException;

    public abstract void zza(int i, boolean z) throws IOException;

    public abstract void zza(zzgs zzgsVar) throws IOException;

    public abstract void zzb(int i, zzgs zzgsVar) throws IOException;

    public abstract void zzb(zzjn zzjnVar) throws IOException;

    public abstract void zzbd(int i) throws IOException;

    public abstract void zzbe(int i) throws IOException;

    public abstract void zzbg(int i) throws IOException;

    public abstract void zzc(byte b) throws IOException;

    public abstract void zzc(int i, long j) throws IOException;

    abstract void zze(byte[] bArr, int i, int i2) throws IOException;

    public abstract int zzga();

    public abstract void zzj(int i, int i2) throws IOException;

    public abstract void zzk(int i, int i2) throws IOException;

    public abstract void zzm(int i, int i2) throws IOException;

    public abstract void zzs(long j) throws IOException;

    public abstract void zzu(long j) throws IOException;

    public abstract void zzw(String str) throws IOException;

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    public static class zza extends IOException {
        zza() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        zza(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        zza(String str, Throwable th) {
            String strValueOf = String.valueOf(str);
            super(strValueOf.length() != 0 ? "CodedOutputStream was writing to a flat byte array and ran out of space.: ".concat(strValueOf) : new String("CodedOutputStream was writing to a flat byte array and ran out of space.: "), th);
        }
    }

    private zzhl() {
    }

    public final void zzl(int i, int i2) throws IOException {
        zzk(i, zzbo(i2));
    }

    public final void zzb(int i, long j) throws IOException {
        zza(i, zzaa(j));
    }

    public final void zza(int i, float f) throws IOException {
        zzm(i, Float.floatToRawIntBits(f));
    }

    public final void zza(int i, double d) throws IOException {
        zzc(i, Double.doubleToRawLongBits(d));
    }

    public final void zzbf(int i) throws IOException {
        zzbe(zzbo(i));
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
    static class zzb extends zzhl {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        zzb(byte[] bArr, int i, int i2) {
            super();
            if (bArr == null) {
                throw new NullPointerException("buffer");
            }
            if ((i2 | 0 | (bArr.length - i2)) < 0) {
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", Integer.valueOf(bArr.length), 0, Integer.valueOf(i2)));
            }
            this.buffer = bArr;
            this.offset = 0;
            this.position = 0;
            this.limit = i2;
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void writeTag(int i, int i2) throws IOException {
            zzbe((i << 3) | i2);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zzj(int i, int i2) throws IOException {
            writeTag(i, 0);
            zzbd(i2);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zzk(int i, int i2) throws IOException {
            writeTag(i, 0);
            zzbe(i2);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zzm(int i, int i2) throws IOException {
            writeTag(i, 5);
            zzbg(i2);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zza(int i, long j) throws IOException {
            writeTag(i, 0);
            zzs(j);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zzc(int i, long j) throws IOException {
            writeTag(i, 1);
            zzu(j);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zza(int i, boolean z) throws IOException {
            writeTag(i, 0);
            zzc(z ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zza(int i, String str) throws IOException {
            writeTag(i, 2);
            zzw(str);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zza(int i, zzgs zzgsVar) throws IOException {
            writeTag(i, 2);
            zza(zzgsVar);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zza(zzgs zzgsVar) throws IOException {
            zzbe(zzgsVar.size());
            zzgsVar.zza(this);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zze(byte[] bArr, int i, int i2) throws IOException {
            zzbe(i2);
            write(bArr, 0, i2);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        final void zza(int i, zzjn zzjnVar, zzkc zzkcVar) throws IOException {
            writeTag(i, 2);
            zzge zzgeVar = (zzge) zzjnVar;
            int iZzef = zzgeVar.zzef();
            if (iZzef == -1) {
                iZzef = zzkcVar.zzu(zzgeVar);
                zzgeVar.zzak(iZzef);
            }
            zzbe(iZzef);
            zzkcVar.zza(zzjnVar, this.zzuo);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zza(int i, zzjn zzjnVar) throws IOException {
            writeTag(1, 3);
            zzk(2, i);
            writeTag(3, 2);
            zzb(zzjnVar);
            writeTag(1, 4);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zzb(int i, zzgs zzgsVar) throws IOException {
            writeTag(1, 3);
            zzk(2, i);
            zza(3, zzgsVar);
            writeTag(1, 4);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zzb(zzjn zzjnVar) throws IOException {
            zzbe(zzjnVar.zzgz());
            zzjnVar.zzb(this);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zzc(byte b) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = b;
            } catch (IndexOutOfBoundsException e) {
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zzbd(int i) throws IOException {
            if (i >= 0) {
                zzbe(i);
            } else {
                zzs(i);
            }
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zzbe(int i) throws IOException {
            if (!zzhl.zzun || zzgl.zzel() || zzga() < 5) {
                while ((i & (-128)) != 0) {
                    try {
                        byte[] bArr = this.buffer;
                        int i2 = this.position;
                        this.position = i2 + 1;
                        bArr[i2] = (byte) ((i & 127) | 128);
                        i >>>= 7;
                    } catch (IndexOutOfBoundsException e) {
                        throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
                    }
                }
                byte[] bArr2 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr2[i3] = (byte) i;
                return;
            }
            if ((i & (-128)) == 0) {
                byte[] bArr3 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                zzla.zza(bArr3, i4, (byte) i);
                return;
            }
            byte[] bArr4 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            zzla.zza(bArr4, i5, (byte) (i | 128));
            int i6 = i >>> 7;
            if ((i6 & (-128)) == 0) {
                byte[] bArr5 = this.buffer;
                int i7 = this.position;
                this.position = i7 + 1;
                zzla.zza(bArr5, i7, (byte) i6);
                return;
            }
            byte[] bArr6 = this.buffer;
            int i8 = this.position;
            this.position = i8 + 1;
            zzla.zza(bArr6, i8, (byte) (i6 | 128));
            int i9 = i6 >>> 7;
            if ((i9 & (-128)) == 0) {
                byte[] bArr7 = this.buffer;
                int i10 = this.position;
                this.position = i10 + 1;
                zzla.zza(bArr7, i10, (byte) i9);
                return;
            }
            byte[] bArr8 = this.buffer;
            int i11 = this.position;
            this.position = i11 + 1;
            zzla.zza(bArr8, i11, (byte) (i9 | 128));
            int i12 = i9 >>> 7;
            if ((i12 & (-128)) == 0) {
                byte[] bArr9 = this.buffer;
                int i13 = this.position;
                this.position = i13 + 1;
                zzla.zza(bArr9, i13, (byte) i12);
                return;
            }
            byte[] bArr10 = this.buffer;
            int i14 = this.position;
            this.position = i14 + 1;
            zzla.zza(bArr10, i14, (byte) (i12 | 128));
            byte[] bArr11 = this.buffer;
            int i15 = this.position;
            this.position = i15 + 1;
            zzla.zza(bArr11, i15, (byte) (i12 >>> 7));
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zzbg(int i) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                int i3 = i2 + 1;
                bArr[i2] = (byte) i;
                int i4 = i3 + 1;
                bArr[i3] = (byte) (i >> 8);
                int i5 = i4 + 1;
                bArr[i4] = (byte) (i >> 16);
                this.position = i5 + 1;
                bArr[i5] = (byte) (i >>> 24);
            } catch (IndexOutOfBoundsException e) {
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zzs(long j) throws IOException {
            if (zzhl.zzun && zzga() >= 10) {
                while ((j & (-128)) != 0) {
                    byte[] bArr = this.buffer;
                    int i = this.position;
                    this.position = i + 1;
                    zzla.zza(bArr, i, (byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                }
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                zzla.zza(bArr2, i2, (byte) j);
                return;
            }
            while ((j & (-128)) != 0) {
                try {
                    byte[] bArr3 = this.buffer;
                    int i3 = this.position;
                    this.position = i3 + 1;
                    bArr3[i3] = (byte) ((((int) j) & 127) | 128);
                    j >>>= 7;
                } catch (IndexOutOfBoundsException e) {
                    throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
                }
            }
            byte[] bArr4 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            bArr4[i4] = (byte) j;
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zzu(long j) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                int i2 = i + 1;
                bArr[i] = (byte) j;
                int i3 = i2 + 1;
                bArr[i2] = (byte) (j >> 8);
                int i4 = i3 + 1;
                bArr[i3] = (byte) (j >> 16);
                int i5 = i4 + 1;
                bArr[i4] = (byte) (j >> 24);
                int i6 = i5 + 1;
                bArr[i5] = (byte) (j >> 32);
                int i7 = i6 + 1;
                bArr[i6] = (byte) (j >> 40);
                int i8 = i7 + 1;
                bArr[i7] = (byte) (j >> 48);
                this.position = i8 + 1;
                bArr[i8] = (byte) (j >> 56);
            } catch (IndexOutOfBoundsException e) {
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        private final void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                System.arraycopy(bArr, i, this.buffer, this.position, i2);
                this.position += i2;
            } catch (IndexOutOfBoundsException e) {
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(i2)), e);
            }
        }

        @Override // com.google.android.gms.internal.vision.zzgt
        public final void zzc(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final void zzw(String str) throws IOException {
            int i = this.position;
            try {
                int iZzbj = zzbj(str.length() * 3);
                int iZzbj2 = zzbj(str.length());
                if (iZzbj2 == iZzbj) {
                    int i2 = i + iZzbj2;
                    this.position = i2;
                    int iZza = zzld.zza(str, this.buffer, i2, zzga());
                    this.position = i;
                    zzbe((iZza - i) - iZzbj2);
                    this.position = iZza;
                    return;
                }
                zzbe(zzld.zza(str));
                this.position = zzld.zza(str, this.buffer, this.position, zzga());
            } catch (zzlg e) {
                this.position = i;
                zza(str, e);
            } catch (IndexOutOfBoundsException e2) {
                throw new zza(e2);
            }
        }

        @Override // com.google.android.gms.internal.vision.zzhl
        public final int zzga() {
            return this.limit - this.position;
        }
    }

    public final void zzt(long j) throws IOException {
        zzs(zzaa(j));
    }

    public final void zzs(float f) throws IOException {
        zzbg(Float.floatToRawIntBits(f));
    }

    public final void zza(double d) throws IOException {
        zzu(Double.doubleToRawLongBits(d));
    }

    public final void zzk(boolean z) throws IOException {
        zzc(z ? (byte) 1 : (byte) 0);
    }

    public static int zzn(int i, int i2) {
        return zzbj(i << 3) + zzbi(i2);
    }

    public static int zzo(int i, int i2) {
        return zzbj(i << 3) + zzbj(i2);
    }

    public static int zzp(int i, int i2) {
        return zzbj(i << 3) + zzbj(zzbo(i2));
    }

    public static int zzq(int i, int i2) {
        return zzbj(i << 3) + 4;
    }

    public static int zzr(int i, int i2) {
        return zzbj(i << 3) + 4;
    }

    public static int zzd(int i, long j) {
        return zzbj(i << 3) + zzw(j);
    }

    public static int zze(int i, long j) {
        return zzbj(i << 3) + zzw(j);
    }

    public static int zzf(int i, long j) {
        return zzbj(i << 3) + zzw(zzaa(j));
    }

    public static int zzg(int i, long j) {
        return zzbj(i << 3) + 8;
    }

    public static int zzh(int i, long j) {
        return zzbj(i << 3) + 8;
    }

    public static int zzb(int i, float f) {
        return zzbj(i << 3) + 4;
    }

    public static int zzb(int i, double d) {
        return zzbj(i << 3) + 8;
    }

    public static int zzb(int i, boolean z) {
        return zzbj(i << 3) + 1;
    }

    public static int zzs(int i, int i2) {
        return zzbj(i << 3) + zzbi(i2);
    }

    public static int zzb(int i, String str) {
        return zzbj(i << 3) + zzx(str);
    }

    public static int zzc(int i, zzgs zzgsVar) {
        int iZzbj = zzbj(i << 3);
        int size = zzgsVar.size();
        return iZzbj + zzbj(size) + size;
    }

    public static int zza(int i, zzis zzisVar) {
        int iZzbj = zzbj(i << 3);
        int iZzgz = zzisVar.zzgz();
        return iZzbj + zzbj(iZzgz) + iZzgz;
    }

    static int zzb(int i, zzjn zzjnVar, zzkc zzkcVar) {
        return zzbj(i << 3) + zza(zzjnVar, zzkcVar);
    }

    public static int zzb(int i, zzjn zzjnVar) {
        return (zzbj(8) << 1) + zzo(2, i) + zzbj(24) + zzc(zzjnVar);
    }

    public static int zzd(int i, zzgs zzgsVar) {
        return (zzbj(8) << 1) + zzo(2, i) + zzc(3, zzgsVar);
    }

    public static int zzb(int i, zzis zzisVar) {
        return (zzbj(8) << 1) + zzo(2, i) + zza(3, zzisVar);
    }

    public static int zzbh(int i) {
        return zzbj(i << 3);
    }

    public static int zzbi(int i) {
        if (i >= 0) {
            return zzbj(i);
        }
        return 10;
    }

    public static int zzbk(int i) {
        return zzbj(zzbo(i));
    }

    public static int zzv(long j) {
        return zzw(j);
    }

    public static int zzx(long j) {
        return zzw(zzaa(j));
    }

    public static int zzbn(int i) {
        return zzbi(i);
    }

    public static int zzx(String str) {
        int length;
        try {
            length = zzld.zza(str);
        } catch (zzlg unused) {
            length = str.getBytes(zzie.UTF_8).length;
        }
        return zzbj(length) + length;
    }

    public static int zza(zzis zzisVar) {
        int iZzgz = zzisVar.zzgz();
        return zzbj(iZzgz) + iZzgz;
    }

    public static int zzb(zzgs zzgsVar) {
        int size = zzgsVar.size();
        return zzbj(size) + size;
    }

    public static int zzf(byte[] bArr) {
        int length = bArr.length;
        return zzbj(length) + length;
    }

    public static int zzc(zzjn zzjnVar) {
        int iZzgz = zzjnVar.zzgz();
        return zzbj(iZzgz) + iZzgz;
    }

    static int zza(zzjn zzjnVar, zzkc zzkcVar) {
        zzge zzgeVar = (zzge) zzjnVar;
        int iZzef = zzgeVar.zzef();
        if (iZzef == -1) {
            iZzef = zzkcVar.zzu(zzgeVar);
            zzgeVar.zzak(iZzef);
        }
        return zzbj(iZzef) + iZzef;
    }

    public final void zzgb() {
        if (zzga() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    final void zza(String str, zzlg zzlgVar) throws IOException {
        logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", (Throwable) zzlgVar);
        byte[] bytes = str.getBytes(zzie.UTF_8);
        try {
            zzbe(bytes.length);
            zzc(bytes, 0, bytes.length);
        } catch (zza e) {
            throw e;
        } catch (IndexOutOfBoundsException e2) {
            throw new zza(e2);
        }
    }

    @Deprecated
    static int zzc(int i, zzjn zzjnVar, zzkc zzkcVar) {
        int iZzbj = zzbj(i << 3) << 1;
        zzge zzgeVar = (zzge) zzjnVar;
        int iZzef = zzgeVar.zzef();
        if (iZzef == -1) {
            iZzef = zzkcVar.zzu(zzgeVar);
            zzgeVar.zzak(iZzef);
        }
        return iZzbj + iZzef;
    }

    @Deprecated
    public static int zzd(zzjn zzjnVar) {
        return zzjnVar.zzgz();
    }

    @Deprecated
    public static int zzbp(int i) {
        return zzbj(i);
    }
}
