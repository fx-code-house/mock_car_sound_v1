package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;
import java.io.IOException;
import java.util.Arrays;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzgp {
    private static final zzgp zzof = new zzgp(0, new int[0], new Object[0], false);
    private int count;
    private boolean zzgb;
    private int zzkd;
    private Object[] zzmu;
    private int[] zzog;

    public static zzgp zzdl() {
        return zzof;
    }

    static zzgp zza(zzgp zzgpVar, zzgp zzgpVar2) {
        int i = zzgpVar.count + zzgpVar2.count;
        int[] iArrCopyOf = Arrays.copyOf(zzgpVar.zzog, i);
        System.arraycopy(zzgpVar2.zzog, 0, iArrCopyOf, zzgpVar.count, zzgpVar2.count);
        Object[] objArrCopyOf = Arrays.copyOf(zzgpVar.zzmu, i);
        System.arraycopy(zzgpVar2.zzmu, 0, objArrCopyOf, zzgpVar.count, zzgpVar2.count);
        return new zzgp(i, iArrCopyOf, objArrCopyOf, true);
    }

    private zzgp() {
        this(0, new int[8], new Object[8], true);
    }

    private zzgp(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zzkd = -1;
        this.count = i;
        this.zzog = iArr;
        this.zzmu = objArr;
        this.zzgb = z;
    }

    public final void zzai() {
        this.zzgb = false;
    }

    final void zza(zzhg zzhgVar) throws IOException {
        if (zzhgVar.zzay() == zzdx.zze.zzky) {
            for (int i = this.count - 1; i >= 0; i--) {
                zzhgVar.zza(this.zzog[i] >>> 3, this.zzmu[i]);
            }
            return;
        }
        for (int i2 = 0; i2 < this.count; i2++) {
            zzhgVar.zza(this.zzog[i2] >>> 3, this.zzmu[i2]);
        }
    }

    public final void zzb(zzhg zzhgVar) throws IOException {
        if (this.count == 0) {
            return;
        }
        if (zzhgVar.zzay() == zzdx.zze.zzkx) {
            for (int i = 0; i < this.count; i++) {
                zzb(this.zzog[i], this.zzmu[i], zzhgVar);
            }
            return;
        }
        for (int i2 = this.count - 1; i2 >= 0; i2--) {
            zzb(this.zzog[i2], this.zzmu[i2], zzhgVar);
        }
    }

    private static void zzb(int i, Object obj, zzhg zzhgVar) throws IOException {
        int i2 = i >>> 3;
        int i3 = i & 7;
        if (i3 == 0) {
            zzhgVar.zzi(i2, ((Long) obj).longValue());
            return;
        }
        if (i3 == 1) {
            zzhgVar.zzc(i2, ((Long) obj).longValue());
            return;
        }
        if (i3 == 2) {
            zzhgVar.zza(i2, (zzct) obj);
            return;
        }
        if (i3 != 3) {
            if (i3 == 5) {
                zzhgVar.zzf(i2, ((Integer) obj).intValue());
                return;
            }
            throw new RuntimeException(zzeh.zzbz());
        }
        if (zzhgVar.zzay() == zzdx.zze.zzkx) {
            zzhgVar.zzab(i2);
            ((zzgp) obj).zzb(zzhgVar);
            zzhgVar.zzac(i2);
        } else {
            zzhgVar.zzac(i2);
            ((zzgp) obj).zzb(zzhgVar);
            zzhgVar.zzab(i2);
        }
    }

    public final int zzdm() {
        int i = this.zzkd;
        if (i != -1) {
            return i;
        }
        int iZzd = 0;
        for (int i2 = 0; i2 < this.count; i2++) {
            iZzd += zzdk.zzd(this.zzog[i2] >>> 3, (zzct) this.zzmu[i2]);
        }
        this.zzkd = iZzd;
        return iZzd;
    }

    public final int zzbl() {
        int iZze;
        int i = this.zzkd;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.count; i3++) {
            int i4 = this.zzog[i3];
            int i5 = i4 >>> 3;
            int i6 = i4 & 7;
            if (i6 == 0) {
                iZze = zzdk.zze(i5, ((Long) this.zzmu[i3]).longValue());
            } else if (i6 == 1) {
                iZze = zzdk.zzg(i5, ((Long) this.zzmu[i3]).longValue());
            } else if (i6 == 2) {
                iZze = zzdk.zzc(i5, (zzct) this.zzmu[i3]);
            } else if (i6 == 3) {
                iZze = (zzdk.zzs(i5) << 1) + ((zzgp) this.zzmu[i3]).zzbl();
            } else if (i6 == 5) {
                iZze = zzdk.zzj(i5, ((Integer) this.zzmu[i3]).intValue());
            } else {
                throw new IllegalStateException(zzeh.zzbz());
            }
            i2 += iZze;
        }
        this.zzkd = i2;
        return i2;
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzgp)) {
            return false;
        }
        zzgp zzgpVar = (zzgp) obj;
        int i = this.count;
        if (i == zzgpVar.count) {
            int[] iArr = this.zzog;
            int[] iArr2 = zzgpVar.zzog;
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
                Object[] objArr = this.zzmu;
                Object[] objArr2 = zzgpVar.zzmu;
                int i3 = this.count;
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
        int i = this.count;
        int i2 = (i + 527) * 31;
        int[] iArr = this.zzog;
        int iHashCode = 17;
        int i3 = 17;
        for (int i4 = 0; i4 < i; i4++) {
            i3 = (i3 * 31) + iArr[i4];
        }
        int i5 = (i2 + i3) * 31;
        Object[] objArr = this.zzmu;
        int i6 = this.count;
        for (int i7 = 0; i7 < i6; i7++) {
            iHashCode = (iHashCode * 31) + objArr[i7].hashCode();
        }
        return i5 + iHashCode;
    }

    final void zza(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.count; i2++) {
            zzfi.zza(sb, i, String.valueOf(this.zzog[i2] >>> 3), this.zzmu[i2]);
        }
    }
}
