package com.google.android.gms.internal.icing;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public abstract class zzdk extends zzcu {
    private static final Logger logger = Logger.getLogger(zzdk.class.getName());
    private static final boolean zzgx = zzgs.zzdn();
    zzdm zzgy;

    public static int zzb(double d) {
        return 8;
    }

    public static int zzb(float f) {
        return 4;
    }

    public static zzdk zzb(byte[] bArr) {
        return new zzb(bArr, 0, bArr.length);
    }

    public static int zzf(long j) {
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

    public static int zzf(boolean z) {
        return 1;
    }

    public static int zzh(long j) {
        return 8;
    }

    public static int zzi(long j) {
        return 8;
    }

    private static long zzj(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public static int zzu(int i) {
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

    public static int zzw(int i) {
        return 4;
    }

    public static int zzx(int i) {
        return 4;
    }

    private static int zzz(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public abstract void zza(int i, long j) throws IOException;

    public abstract void zza(int i, zzct zzctVar) throws IOException;

    public abstract void zza(int i, zzfh zzfhVar) throws IOException;

    abstract void zza(int i, zzfh zzfhVar, zzfu zzfuVar) throws IOException;

    public abstract void zza(int i, String str) throws IOException;

    public abstract void zza(int i, boolean z) throws IOException;

    public abstract void zza(zzct zzctVar) throws IOException;

    public abstract int zzau();

    public abstract void zzb(int i, int i2) throws IOException;

    public abstract void zzb(int i, zzct zzctVar) throws IOException;

    public abstract void zzb(long j) throws IOException;

    public abstract void zzb(zzfh zzfhVar) throws IOException;

    abstract void zzb(byte[] bArr, int i, int i2) throws IOException;

    public abstract void zzc(byte b) throws IOException;

    public abstract void zzc(int i, int i2) throws IOException;

    public abstract void zzc(int i, long j) throws IOException;

    public abstract void zzd(int i, int i2) throws IOException;

    public abstract void zzd(long j) throws IOException;

    public abstract void zzf(int i, int i2) throws IOException;

    public abstract void zzo(int i) throws IOException;

    public abstract void zzp(int i) throws IOException;

    public abstract void zzq(String str) throws IOException;

    public abstract void zzr(int i) throws IOException;

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
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

    private zzdk() {
    }

    public final void zze(int i, int i2) throws IOException {
        zzd(i, zzz(i2));
    }

    public final void zzb(int i, long j) throws IOException {
        zza(i, zzj(j));
    }

    public final void zza(int i, float f) throws IOException {
        zzf(i, Float.floatToRawIntBits(f));
    }

    public final void zza(int i, double d) throws IOException {
        zzc(i, Double.doubleToRawLongBits(d));
    }

    public final void zzq(int i) throws IOException {
        zzp(zzz(i));
    }

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    static class zzb extends zzdk {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        zzb(byte[] bArr, int i, int i2) {
            super();
            if (bArr == null) {
                throw new NullPointerException("buffer");
            }
            int i3 = i2 + 0;
            if ((i2 | 0 | (bArr.length - i3)) < 0) {
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", Integer.valueOf(bArr.length), 0, Integer.valueOf(i2)));
            }
            this.buffer = bArr;
            this.offset = 0;
            this.position = 0;
            this.limit = i3;
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzb(int i, int i2) throws IOException {
            zzp((i << 3) | i2);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzc(int i, int i2) throws IOException {
            zzb(i, 0);
            zzo(i2);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzd(int i, int i2) throws IOException {
            zzb(i, 0);
            zzp(i2);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzf(int i, int i2) throws IOException {
            zzb(i, 5);
            zzr(i2);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zza(int i, long j) throws IOException {
            zzb(i, 0);
            zzb(j);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzc(int i, long j) throws IOException {
            zzb(i, 1);
            zzd(j);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zza(int i, boolean z) throws IOException {
            zzb(i, 0);
            zzc(z ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zza(int i, String str) throws IOException {
            zzb(i, 2);
            zzq(str);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zza(int i, zzct zzctVar) throws IOException {
            zzb(i, 2);
            zza(zzctVar);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zza(zzct zzctVar) throws IOException {
            zzp(zzctVar.size());
            zzctVar.zza(this);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzb(byte[] bArr, int i, int i2) throws IOException {
            zzp(i2);
            write(bArr, 0, i2);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        final void zza(int i, zzfh zzfhVar, zzfu zzfuVar) throws IOException {
            zzb(i, 2);
            zzcm zzcmVar = (zzcm) zzfhVar;
            int iZzae = zzcmVar.zzae();
            if (iZzae == -1) {
                iZzae = zzfuVar.zzn(zzcmVar);
                zzcmVar.zzg(iZzae);
            }
            zzp(iZzae);
            zzfuVar.zza(zzfhVar, this.zzgy);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zza(int i, zzfh zzfhVar) throws IOException {
            zzb(1, 3);
            zzd(2, i);
            zzb(3, 2);
            zzb(zzfhVar);
            zzb(1, 4);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzb(int i, zzct zzctVar) throws IOException {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzctVar);
            zzb(1, 4);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzb(zzfh zzfhVar) throws IOException {
            zzp(zzfhVar.zzbl());
            zzfhVar.zzb(this);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
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

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzo(int i) throws IOException {
            if (i >= 0) {
                zzp(i);
            } else {
                zzb(i);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzp(int i) throws IOException {
            if (!zzdk.zzgx || zzcs.zzal() || zzau() < 5) {
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
                zzgs.zza(bArr3, i4, (byte) i);
                return;
            }
            byte[] bArr4 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            zzgs.zza(bArr4, i5, (byte) (i | 128));
            int i6 = i >>> 7;
            if ((i6 & (-128)) == 0) {
                byte[] bArr5 = this.buffer;
                int i7 = this.position;
                this.position = i7 + 1;
                zzgs.zza(bArr5, i7, (byte) i6);
                return;
            }
            byte[] bArr6 = this.buffer;
            int i8 = this.position;
            this.position = i8 + 1;
            zzgs.zza(bArr6, i8, (byte) (i6 | 128));
            int i9 = i6 >>> 7;
            if ((i9 & (-128)) == 0) {
                byte[] bArr7 = this.buffer;
                int i10 = this.position;
                this.position = i10 + 1;
                zzgs.zza(bArr7, i10, (byte) i9);
                return;
            }
            byte[] bArr8 = this.buffer;
            int i11 = this.position;
            this.position = i11 + 1;
            zzgs.zza(bArr8, i11, (byte) (i9 | 128));
            int i12 = i9 >>> 7;
            if ((i12 & (-128)) == 0) {
                byte[] bArr9 = this.buffer;
                int i13 = this.position;
                this.position = i13 + 1;
                zzgs.zza(bArr9, i13, (byte) i12);
                return;
            }
            byte[] bArr10 = this.buffer;
            int i14 = this.position;
            this.position = i14 + 1;
            zzgs.zza(bArr10, i14, (byte) (i12 | 128));
            byte[] bArr11 = this.buffer;
            int i15 = this.position;
            this.position = i15 + 1;
            zzgs.zza(bArr11, i15, (byte) (i12 >>> 7));
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzr(int i) throws IOException {
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

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzb(long j) throws IOException {
            if (zzdk.zzgx && zzau() >= 10) {
                while ((j & (-128)) != 0) {
                    byte[] bArr = this.buffer;
                    int i = this.position;
                    this.position = i + 1;
                    zzgs.zza(bArr, i, (byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                }
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                zzgs.zza(bArr2, i2, (byte) j);
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

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzd(long j) throws IOException {
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

        @Override // com.google.android.gms.internal.icing.zzcu
        public final void zza(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzq(String str) throws IOException {
            int i = this.position;
            try {
                int iZzu = zzu(str.length() * 3);
                int iZzu2 = zzu(str.length());
                if (iZzu2 == iZzu) {
                    int i2 = i + iZzu2;
                    this.position = i2;
                    int iZza = zzgv.zza(str, this.buffer, i2, zzau());
                    this.position = i;
                    zzp((iZza - i) - iZzu2);
                    this.position = iZza;
                    return;
                }
                zzp(zzgv.zza(str));
                this.position = zzgv.zza(str, this.buffer, this.position, zzau());
            } catch (zzgz e) {
                this.position = i;
                zza(str, e);
            } catch (IndexOutOfBoundsException e2) {
                throw new zza(e2);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final int zzau() {
            return this.limit - this.position;
        }
    }

    public final void zzc(long j) throws IOException {
        zzb(zzj(j));
    }

    public final void zza(float f) throws IOException {
        zzr(Float.floatToRawIntBits(f));
    }

    public final void zza(double d) throws IOException {
        zzd(Double.doubleToRawLongBits(d));
    }

    public final void zze(boolean z) throws IOException {
        zzc(z ? (byte) 1 : (byte) 0);
    }

    public static int zzg(int i, int i2) {
        return zzs(i) + zzt(i2);
    }

    public static int zzh(int i, int i2) {
        return zzs(i) + zzu(i2);
    }

    public static int zzi(int i, int i2) {
        return zzs(i) + zzu(zzz(i2));
    }

    public static int zzj(int i, int i2) {
        return zzs(i) + 4;
    }

    public static int zzk(int i, int i2) {
        return zzs(i) + 4;
    }

    public static int zzd(int i, long j) {
        return zzs(i) + zzf(j);
    }

    public static int zze(int i, long j) {
        return zzs(i) + zzf(j);
    }

    public static int zzf(int i, long j) {
        return zzs(i) + zzf(zzj(j));
    }

    public static int zzg(int i, long j) {
        return zzs(i) + 8;
    }

    public static int zzh(int i, long j) {
        return zzs(i) + 8;
    }

    public static int zzb(int i, float f) {
        return zzs(i) + 4;
    }

    public static int zzb(int i, double d) {
        return zzs(i) + 8;
    }

    public static int zzb(int i, boolean z) {
        return zzs(i) + 1;
    }

    public static int zzl(int i, int i2) {
        return zzs(i) + zzt(i2);
    }

    public static int zzb(int i, String str) {
        return zzs(i) + zzr(str);
    }

    public static int zzc(int i, zzct zzctVar) {
        int iZzs = zzs(i);
        int size = zzctVar.size();
        return iZzs + zzu(size) + size;
    }

    public static int zza(int i, zzem zzemVar) {
        int iZzs = zzs(i);
        int iZzbl = zzemVar.zzbl();
        return iZzs + zzu(iZzbl) + iZzbl;
    }

    static int zzb(int i, zzfh zzfhVar, zzfu zzfuVar) {
        return zzs(i) + zza(zzfhVar, zzfuVar);
    }

    public static int zzb(int i, zzfh zzfhVar) {
        return (zzs(1) << 1) + zzh(2, i) + zzs(3) + zzc(zzfhVar);
    }

    public static int zzd(int i, zzct zzctVar) {
        return (zzs(1) << 1) + zzh(2, i) + zzc(3, zzctVar);
    }

    public static int zzb(int i, zzem zzemVar) {
        return (zzs(1) << 1) + zzh(2, i) + zza(3, zzemVar);
    }

    public static int zzs(int i) {
        return zzu(i << 3);
    }

    public static int zzt(int i) {
        if (i >= 0) {
            return zzu(i);
        }
        return 10;
    }

    public static int zzv(int i) {
        return zzu(zzz(i));
    }

    public static int zze(long j) {
        return zzf(j);
    }

    public static int zzg(long j) {
        return zzf(zzj(j));
    }

    public static int zzy(int i) {
        return zzt(i);
    }

    public static int zzr(String str) {
        int length;
        try {
            length = zzgv.zza(str);
        } catch (zzgz unused) {
            length = str.getBytes(zzeb.UTF_8).length;
        }
        return zzu(length) + length;
    }

    public static int zza(zzem zzemVar) {
        int iZzbl = zzemVar.zzbl();
        return zzu(iZzbl) + iZzbl;
    }

    public static int zzb(zzct zzctVar) {
        int size = zzctVar.size();
        return zzu(size) + size;
    }

    public static int zzc(byte[] bArr) {
        int length = bArr.length;
        return zzu(length) + length;
    }

    public static int zzc(zzfh zzfhVar) {
        int iZzbl = zzfhVar.zzbl();
        return zzu(iZzbl) + iZzbl;
    }

    static int zza(zzfh zzfhVar, zzfu zzfuVar) {
        zzcm zzcmVar = (zzcm) zzfhVar;
        int iZzae = zzcmVar.zzae();
        if (iZzae == -1) {
            iZzae = zzfuVar.zzn(zzcmVar);
            zzcmVar.zzg(iZzae);
        }
        return zzu(iZzae) + iZzae;
    }

    public final void zzav() {
        if (zzau() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    final void zza(String str, zzgz zzgzVar) throws IOException {
        logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", (Throwable) zzgzVar);
        byte[] bytes = str.getBytes(zzeb.UTF_8);
        try {
            zzp(bytes.length);
            zza(bytes, 0, bytes.length);
        } catch (zza e) {
            throw e;
        } catch (IndexOutOfBoundsException e2) {
            throw new zza(e2);
        }
    }

    @Deprecated
    static int zzc(int i, zzfh zzfhVar, zzfu zzfuVar) {
        int iZzs = zzs(i) << 1;
        zzcm zzcmVar = (zzcm) zzfhVar;
        int iZzae = zzcmVar.zzae();
        if (iZzae == -1) {
            iZzae = zzfuVar.zzn(zzcmVar);
            zzcmVar.zzg(iZzae);
        }
        return iZzs + iZzae;
    }

    @Deprecated
    public static int zzd(zzfh zzfhVar) {
        return zzfhVar.zzbl();
    }

    @Deprecated
    public static int zzaa(int i) {
        return zzu(i);
    }
}
