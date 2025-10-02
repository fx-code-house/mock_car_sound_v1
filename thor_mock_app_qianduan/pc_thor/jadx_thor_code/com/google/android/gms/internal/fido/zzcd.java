package com.google.android.gms.internal.fido;

import java.math.RoundingMode;
import java.util.Arrays;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
final class zzcd {
    final int zza;
    final int zzb;
    final int zzc;
    final int zzd;
    private final String zze;
    private final char[] zzf;
    private final byte[] zzg;
    private final boolean zzh;

    /* JADX WARN: Illegal instructions before constructor call */
    zzcd(String str, char[] cArr) {
        byte[] bArr = new byte[128];
        Arrays.fill(bArr, (byte) -1);
        for (int i = 0; i < cArr.length; i++) {
            char c = cArr[i];
            boolean z = true;
            zzap.zzd(c < 128, "Non-ASCII character: %s", c);
            if (bArr[c] != -1) {
                z = false;
            }
            zzap.zzd(z, "Duplicate character: %s", c);
            bArr[c] = (byte) i;
        }
        this(str, cArr, bArr, false);
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (obj instanceof zzcd) {
            zzcd zzcdVar = (zzcd) obj;
            if (this.zzh == zzcdVar.zzh && Arrays.equals(this.zzf, zzcdVar.zzf)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(this.zzf) + (true != this.zzh ? 1237 : 1231);
    }

    public final String toString() {
        return this.zze;
    }

    final char zza(int i) {
        return this.zzf[i];
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v15 */
    final zzcd zzb() {
        int i;
        boolean z;
        for (char c : this.zzf) {
            if (zzad.zza(c)) {
                char[] cArr = this.zzf;
                int length = cArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        z = false;
                        break;
                    }
                    char c2 = cArr[i2];
                    if (c2 >= 'A' && c2 <= 'Z') {
                        z = true;
                        break;
                    }
                    i2++;
                }
                if (!(!z)) {
                    throw new IllegalStateException("Cannot call upperCase() on a mixed-case alphabet");
                }
                char[] cArr2 = new char[this.zzf.length];
                int i3 = 0;
                while (true) {
                    char[] cArr3 = this.zzf;
                    if (i3 >= cArr3.length) {
                        break;
                    }
                    char c3 = cArr3[i3];
                    if (zzad.zza(c3)) {
                        c3 ^= 32;
                    }
                    cArr2[i3] = (char) c3;
                    i3++;
                }
                zzcd zzcdVar = new zzcd(this.zze.concat(".upperCase()"), cArr2);
                if (!this.zzh || zzcdVar.zzh) {
                    return zzcdVar;
                }
                byte[] bArr = zzcdVar.zzg;
                byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
                for (i = 65; i <= 90; i++) {
                    int i4 = i | 32;
                    byte[] bArr2 = zzcdVar.zzg;
                    byte b = bArr2[i];
                    byte b2 = bArr2[i4];
                    if (b == -1) {
                        bArrCopyOf[i] = b2;
                    } else {
                        char c4 = (char) i;
                        char c5 = (char) i4;
                        if (b2 != -1) {
                            throw new IllegalStateException(zzaq.zza("Can't ignoreCase() since '%s' and '%s' encode different values", Character.valueOf(c4), Character.valueOf(c5)));
                        }
                        bArrCopyOf[i4] = b;
                    }
                }
                return new zzcd(zzcdVar.zze.concat(".ignoreCase()"), zzcdVar.zzf, bArrCopyOf, true);
            }
        }
        return this;
    }

    public final boolean zzc(char c) {
        byte[] bArr = this.zzg;
        return bArr.length > 61 && bArr[61] != -1;
    }

    private zzcd(String str, char[] cArr, byte[] bArr, boolean z) {
        this.zze = str;
        cArr.getClass();
        this.zzf = cArr;
        try {
            int length = cArr.length;
            int iZzb = zzcj.zzb(length, RoundingMode.UNNECESSARY);
            this.zzb = iZzb;
            int iNumberOfTrailingZeros = Integer.numberOfTrailingZeros(iZzb);
            int i = 1 << (3 - iNumberOfTrailingZeros);
            this.zzc = i;
            this.zzd = iZzb >> iNumberOfTrailingZeros;
            this.zza = length - 1;
            this.zzg = bArr;
            boolean[] zArr = new boolean[i];
            for (int i2 = 0; i2 < this.zzd; i2++) {
                zArr[zzcj.zza(i2 * 8, this.zzb, RoundingMode.CEILING)] = true;
            }
            this.zzh = z;
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("Illegal alphabet length " + cArr.length, e);
        }
    }
}
