package com.google.android.gms.internal.clearcut;

import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

/* loaded from: classes2.dex */
public final class zzfs {
    private final ByteBuffer zzgd;
    private zzbn zzrh;
    private int zzri;

    private zzfs(ByteBuffer byteBuffer) {
        this.zzgd = byteBuffer;
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzfs(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    private static int zza(CharSequence charSequence) {
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
                                throw new IllegalArgumentException(new StringBuilder(39).append("Unpaired surrogate at index ").append(i2).toString());
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

    private final void zzao(int i) throws IOException {
        byte b = (byte) i;
        if (!this.zzgd.hasRemaining()) {
            throw new zzft(this.zzgd.position(), this.zzgd.limit());
        }
        this.zzgd.put(b);
    }

    private final void zzap(int i) throws IOException {
        while ((i & (-128)) != 0) {
            zzao((i & 127) | 128);
            i >>>= 7;
        }
        zzao(i);
    }

    public static int zzb(int i, zzfz zzfzVar) {
        int iZzr = zzr(i);
        int iZzas = zzfzVar.zzas();
        return iZzr + zzz(iZzas) + iZzas;
    }

    public static int zzb(int i, String str) {
        return zzr(i) + zzh(str);
    }

    public static int zzb(int i, byte[] bArr) {
        return zzr(i) + zzh(bArr);
    }

    public static int zzd(int i, long j) {
        return zzr(i) + zzo(j);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v26 */
    private static void zzd(CharSequence charSequence, ByteBuffer byteBuffer) {
        int i;
        int i2;
        char cCharAt;
        int i3;
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        int i4 = 0;
        if (!byteBuffer.hasArray()) {
            int length = charSequence.length();
            while (i4 < length) {
                char cCharAt2 = charSequence.charAt(i4);
                char c = cCharAt2;
                if (cCharAt2 >= 128) {
                    if (cCharAt2 < 2048) {
                        i3 = (cCharAt2 >>> 6) | 960;
                    } else {
                        if (cCharAt2 >= 55296 && 57343 >= cCharAt2) {
                            int i5 = i4 + 1;
                            if (i5 != charSequence.length()) {
                                char cCharAt3 = charSequence.charAt(i5);
                                if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                    int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                    byteBuffer.put((byte) ((codePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                                    byteBuffer.put((byte) (((codePoint >>> 12) & 63) | 128));
                                    byteBuffer.put((byte) (((codePoint >>> 6) & 63) | 128));
                                    byteBuffer.put((byte) ((codePoint & 63) | 128));
                                    i4 = i5;
                                } else {
                                    i4 = i5;
                                }
                            }
                            throw new IllegalArgumentException(new StringBuilder(39).append("Unpaired surrogate at index ").append(i4 - 1).toString());
                        }
                        byteBuffer.put((byte) ((cCharAt2 >>> '\f') | 480));
                        i3 = ((cCharAt2 >>> 6) & 63) | 128;
                    }
                    byteBuffer.put((byte) i3);
                    c = (cCharAt2 & '?') | 128;
                    byteBuffer.put((byte) c);
                } else {
                    byteBuffer.put((byte) c);
                }
                i4++;
            }
            return;
        }
        try {
            byte[] bArrArray = byteBuffer.array();
            int iArrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
            int iRemaining = byteBuffer.remaining();
            int length2 = charSequence.length();
            int i6 = iRemaining + iArrayOffset;
            while (i4 < length2) {
                int i7 = i4 + iArrayOffset;
                if (i7 >= i6 || (cCharAt = charSequence.charAt(i4)) >= 128) {
                    break;
                }
                bArrArray[i7] = (byte) cCharAt;
                i4++;
            }
            if (i4 == length2) {
                i = iArrayOffset + length2;
            } else {
                i = iArrayOffset + i4;
                while (i4 < length2) {
                    char cCharAt4 = charSequence.charAt(i4);
                    if (cCharAt4 >= 128 || i >= i6) {
                        if (cCharAt4 < 2048 && i <= i6 - 2) {
                            int i8 = i + 1;
                            bArrArray[i] = (byte) ((cCharAt4 >>> 6) | 960);
                            i = i8 + 1;
                            bArrArray[i8] = (byte) ((cCharAt4 & '?') | 128);
                        } else {
                            if ((cCharAt4 >= 55296 && 57343 >= cCharAt4) || i > i6 - 3) {
                                if (i > i6 - 4) {
                                    throw new ArrayIndexOutOfBoundsException(new StringBuilder(37).append("Failed writing ").append(cCharAt4).append(" at index ").append(i).toString());
                                }
                                int i9 = i4 + 1;
                                if (i9 != charSequence.length()) {
                                    char cCharAt5 = charSequence.charAt(i9);
                                    if (Character.isSurrogatePair(cCharAt4, cCharAt5)) {
                                        int codePoint2 = Character.toCodePoint(cCharAt4, cCharAt5);
                                        int i10 = i + 1;
                                        bArrArray[i] = (byte) ((codePoint2 >>> 18) | PsExtractor.VIDEO_STREAM_MASK);
                                        int i11 = i10 + 1;
                                        bArrArray[i10] = (byte) (((codePoint2 >>> 12) & 63) | 128);
                                        int i12 = i11 + 1;
                                        bArrArray[i11] = (byte) (((codePoint2 >>> 6) & 63) | 128);
                                        i = i12 + 1;
                                        bArrArray[i12] = (byte) ((codePoint2 & 63) | 128);
                                        i4 = i9;
                                    } else {
                                        i4 = i9;
                                    }
                                }
                                throw new IllegalArgumentException(new StringBuilder(39).append("Unpaired surrogate at index ").append(i4 - 1).toString());
                            }
                            int i13 = i + 1;
                            bArrArray[i] = (byte) ((cCharAt4 >>> '\f') | 480);
                            int i14 = i13 + 1;
                            bArrArray[i13] = (byte) (((cCharAt4 >>> 6) & 63) | 128);
                            i2 = i14 + 1;
                            bArrArray[i14] = (byte) ((cCharAt4 & '?') | 128);
                        }
                        i4++;
                    } else {
                        i2 = i + 1;
                        bArrArray[i] = (byte) cCharAt4;
                    }
                    i = i2;
                    i4++;
                }
            }
            byteBuffer.position(i - byteBuffer.arrayOffset());
        } catch (ArrayIndexOutOfBoundsException e) {
            BufferOverflowException bufferOverflowException = new BufferOverflowException();
            bufferOverflowException.initCause(e);
            throw bufferOverflowException;
        }
    }

    public static zzfs zzg(byte[] bArr) {
        return zzh(bArr, 0, bArr.length);
    }

    public static int zzh(String str) {
        int iZza = zza(str);
        return zzz(iZza) + iZza;
    }

    public static int zzh(byte[] bArr) {
        return zzz(bArr.length) + bArr.length;
    }

    public static zzfs zzh(byte[] bArr, int i, int i2) {
        return new zzfs(bArr, 0, i2);
    }

    public static long zzj(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public static int zzo(long j) {
        if (((-128) & j) == 0) {
            return 1;
        }
        if (((-16384) & j) == 0) {
            return 2;
        }
        if (((-2097152) & j) == 0) {
            return 3;
        }
        if (((-268435456) & j) == 0) {
            return 4;
        }
        if (((-34359738368L) & j) == 0) {
            return 5;
        }
        if (((-4398046511104L) & j) == 0) {
            return 6;
        }
        if (((-562949953421312L) & j) == 0) {
            return 7;
        }
        if (((-72057594037927936L) & j) == 0) {
            return 8;
        }
        return (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    public static int zzr(int i) {
        return zzz(i << 3);
    }

    public static int zzs(int i) {
        if (i >= 0) {
            return zzz(i);
        }
        return 10;
    }

    private static int zzz(int i) {
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

    public final void zza(int i, zzfz zzfzVar) throws IOException {
        zzb(i, 2);
        if (zzfzVar.zzrs < 0) {
            zzfzVar.zzas();
        }
        zzap(zzfzVar.zzrs);
        zzfzVar.zza(this);
    }

    public final void zza(int i, String str) throws IOException {
        zzb(i, 2);
        try {
            int iZzz = zzz(str.length());
            if (iZzz != zzz(str.length() * 3)) {
                zzap(zza(str));
                zzd(str, this.zzgd);
                return;
            }
            int iPosition = this.zzgd.position();
            if (this.zzgd.remaining() < iZzz) {
                throw new zzft(iPosition + iZzz, this.zzgd.limit());
            }
            this.zzgd.position(iPosition + iZzz);
            zzd(str, this.zzgd);
            int iPosition2 = this.zzgd.position();
            this.zzgd.position(iPosition);
            zzap((iPosition2 - iPosition) - iZzz);
            this.zzgd.position(iPosition2);
        } catch (BufferOverflowException e) {
            zzft zzftVar = new zzft(this.zzgd.position(), this.zzgd.limit());
            zzftVar.initCause(e);
            throw zzftVar;
        }
    }

    public final void zza(int i, byte[] bArr) throws IOException {
        zzb(i, 2);
        zzap(bArr.length);
        int length = bArr.length;
        if (this.zzgd.remaining() < length) {
            throw new zzft(this.zzgd.position(), this.zzgd.limit());
        }
        this.zzgd.put(bArr, 0, length);
    }

    public final void zzb(int i, int i2) throws IOException {
        zzap((i << 3) | i2);
    }

    public final void zzb(int i, boolean z) throws IOException {
        zzb(25, 0);
        byte b = z ? (byte) 1 : (byte) 0;
        if (!this.zzgd.hasRemaining()) {
            throw new zzft(this.zzgd.position(), this.zzgd.limit());
        }
        this.zzgd.put(b);
    }

    public final void zzc(int i, int i2) throws IOException {
        zzb(i, 0);
        if (i2 >= 0) {
            zzap(i2);
        } else {
            zzn(i2);
        }
    }

    public final void zze(int i, zzdo zzdoVar) throws IOException {
        if (this.zzrh != null) {
            if (this.zzri != this.zzgd.position()) {
                this.zzrh.write(this.zzgd.array(), this.zzri, this.zzgd.position() - this.zzri);
            }
            zzbn zzbnVar = this.zzrh;
            zzbnVar.zza(i, zzdoVar);
            zzbnVar.flush();
            this.zzri = this.zzgd.position();
        }
        this.zzrh = zzbn.zza(this.zzgd);
        this.zzri = this.zzgd.position();
        zzbn zzbnVar2 = this.zzrh;
        zzbnVar2.zza(i, zzdoVar);
        zzbnVar2.flush();
        this.zzri = this.zzgd.position();
    }

    public final void zzem() {
        if (this.zzgd.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", Integer.valueOf(this.zzgd.remaining())));
        }
    }

    public final void zzi(int i, long j) throws IOException {
        zzb(i, 0);
        zzn(j);
    }

    public final void zzn(long j) throws IOException {
        while (((-128) & j) != 0) {
            zzao((((int) j) & 127) | 128);
            j >>>= 7;
        }
        zzao((int) j);
    }
}
