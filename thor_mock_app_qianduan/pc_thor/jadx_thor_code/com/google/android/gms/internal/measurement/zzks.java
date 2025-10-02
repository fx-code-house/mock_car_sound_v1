package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.Arrays;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzks {
    private static final zzks zza = new zzks(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    public static zzks zza() {
        return zza;
    }

    static zzks zzb() {
        return new zzks();
    }

    static zzks zza(zzks zzksVar, zzks zzksVar2) {
        int i = zzksVar.zzb + zzksVar2.zzb;
        int[] iArrCopyOf = Arrays.copyOf(zzksVar.zzc, i);
        System.arraycopy(zzksVar2.zzc, 0, iArrCopyOf, zzksVar.zzb, zzksVar2.zzb);
        Object[] objArrCopyOf = Arrays.copyOf(zzksVar.zzd, i);
        System.arraycopy(zzksVar2.zzd, 0, objArrCopyOf, zzksVar.zzb, zzksVar2.zzb);
        return new zzks(i, iArrCopyOf, objArrCopyOf, true);
    }

    private zzks() {
        this(0, new int[8], new Object[8], true);
    }

    private zzks(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public final void zzc() {
        this.zzf = false;
    }

    final void zza(zzlm zzlmVar) throws IOException {
        if (zzlmVar.zza() == zzlp.zzb) {
            for (int i = this.zzb - 1; i >= 0; i--) {
                zzlmVar.zza(this.zzc[i] >>> 3, this.zzd[i]);
            }
            return;
        }
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzlmVar.zza(this.zzc[i2] >>> 3, this.zzd[i2]);
        }
    }

    public final void zzb(zzlm zzlmVar) throws IOException {
        if (this.zzb == 0) {
            return;
        }
        if (zzlmVar.zza() == zzlp.zza) {
            for (int i = 0; i < this.zzb; i++) {
                zza(this.zzc[i], this.zzd[i], zzlmVar);
            }
            return;
        }
        for (int i2 = this.zzb - 1; i2 >= 0; i2--) {
            zza(this.zzc[i2], this.zzd[i2], zzlmVar);
        }
    }

    private static void zza(int i, Object obj, zzlm zzlmVar) throws IOException {
        int i2 = i >>> 3;
        int i3 = i & 7;
        if (i3 == 0) {
            zzlmVar.zza(i2, ((Long) obj).longValue());
            return;
        }
        if (i3 == 1) {
            zzlmVar.zzd(i2, ((Long) obj).longValue());
            return;
        }
        if (i3 == 2) {
            zzlmVar.zza(i2, (zzgp) obj);
            return;
        }
        if (i3 != 3) {
            if (i3 == 5) {
                zzlmVar.zzd(i2, ((Integer) obj).intValue());
                return;
            }
            throw new RuntimeException(zzij.zzf());
        }
        if (zzlmVar.zza() == zzlp.zza) {
            zzlmVar.zza(i2);
            ((zzks) obj).zzb(zzlmVar);
            zzlmVar.zzb(i2);
        } else {
            zzlmVar.zzb(i2);
            ((zzks) obj).zzb(zzlmVar);
            zzlmVar.zza(i2);
        }
    }

    public final int zzd() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int iZzd = 0;
        for (int i2 = 0; i2 < this.zzb; i2++) {
            iZzd += zzhi.zzd(this.zzc[i2] >>> 3, (zzgp) this.zzd[i2]);
        }
        this.zze = iZzd;
        return iZzd;
    }

    public final int zze() {
        int iZze;
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzb; i3++) {
            int i4 = this.zzc[i3];
            int i5 = i4 >>> 3;
            int i6 = i4 & 7;
            if (i6 == 0) {
                iZze = zzhi.zze(i5, ((Long) this.zzd[i3]).longValue());
            } else if (i6 == 1) {
                iZze = zzhi.zzg(i5, ((Long) this.zzd[i3]).longValue());
            } else if (i6 == 2) {
                iZze = zzhi.zzc(i5, (zzgp) this.zzd[i3]);
            } else if (i6 == 3) {
                iZze = (zzhi.zze(i5) << 1) + ((zzks) this.zzd[i3]).zze();
            } else if (i6 == 5) {
                iZze = zzhi.zzi(i5, ((Integer) this.zzd[i3]).intValue());
            } else {
                throw new IllegalStateException(zzij.zzf());
            }
            i2 += iZze;
        }
        this.zze = i2;
        return i2;
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzks)) {
            return false;
        }
        zzks zzksVar = (zzks) obj;
        int i = this.zzb;
        if (i == zzksVar.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zzksVar.zzc;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    z = true;
                    break;
                }
                if (iArr[i2] != iArr2[i2]) {
                    z = false;
                    break;
                }
                i2++;
            }
            if (z) {
                Object[] objArr = this.zzd;
                Object[] objArr2 = zzksVar.zzd;
                int i3 = this.zzb;
                int i4 = 0;
                while (true) {
                    if (i4 >= i3) {
                        z2 = true;
                        break;
                    }
                    if (!objArr[i4].equals(objArr2[i4])) {
                        z2 = false;
                        break;
                    }
                    i4++;
                }
                if (z2) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = this.zzb;
        int i2 = (i + 527) * 31;
        int[] iArr = this.zzc;
        int iHashCode = 17;
        int i3 = 17;
        for (int i4 = 0; i4 < i; i4++) {
            i3 = (i3 * 31) + iArr[i4];
        }
        int i5 = (i2 + i3) * 31;
        Object[] objArr = this.zzd;
        int i6 = this.zzb;
        for (int i7 = 0; i7 < i6; i7++) {
            iHashCode = (iHashCode * 31) + objArr[i7].hashCode();
        }
        return i5 + iHashCode;
    }

    final void zza(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzjk.zza(sb, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
        }
    }

    final void zza(int i, Object obj) {
        if (!this.zzf) {
            throw new UnsupportedOperationException();
        }
        int i2 = this.zzb;
        int[] iArr = this.zzc;
        if (i2 == iArr.length) {
            int i3 = i2 + (i2 < 4 ? 8 : i2 >> 1);
            this.zzc = Arrays.copyOf(iArr, i3);
            this.zzd = Arrays.copyOf(this.zzd, i3);
        }
        int[] iArr2 = this.zzc;
        int i4 = this.zzb;
        iArr2[i4] = i;
        this.zzd[i4] = obj;
        this.zzb = i4 + 1;
    }
}
