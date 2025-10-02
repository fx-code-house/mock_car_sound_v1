package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzid;
import java.io.IOException;
import java.util.Arrays;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzkx {
    private static final zzkx zzacf = new zzkx(0, new int[0], new Object[0], false);
    private int count;
    private Object[] zzaar;
    private int[] zzacg;
    private boolean zztf;
    private int zzya;

    public static zzkx zzjb() {
        return zzacf;
    }

    static zzkx zzjc() {
        return new zzkx();
    }

    static zzkx zza(zzkx zzkxVar, zzkx zzkxVar2) {
        int i = zzkxVar.count + zzkxVar2.count;
        int[] iArrCopyOf = Arrays.copyOf(zzkxVar.zzacg, i);
        System.arraycopy(zzkxVar2.zzacg, 0, iArrCopyOf, zzkxVar.count, zzkxVar2.count);
        Object[] objArrCopyOf = Arrays.copyOf(zzkxVar.zzaar, i);
        System.arraycopy(zzkxVar2.zzaar, 0, objArrCopyOf, zzkxVar.count, zzkxVar2.count);
        return new zzkx(i, iArrCopyOf, objArrCopyOf, true);
    }

    private zzkx() {
        this(0, new int[8], new Object[8], true);
    }

    private zzkx(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zzya = -1;
        this.count = i;
        this.zzacg = iArr;
        this.zzaar = objArr;
        this.zztf = z;
    }

    public final void zzej() {
        this.zztf = false;
    }

    final void zza(zzlr zzlrVar) throws IOException {
        if (zzlrVar.zzgd() == zzid.zzf.zzyt) {
            for (int i = this.count - 1; i >= 0; i--) {
                zzlrVar.zza(this.zzacg[i] >>> 3, this.zzaar[i]);
            }
            return;
        }
        for (int i2 = 0; i2 < this.count; i2++) {
            zzlrVar.zza(this.zzacg[i2] >>> 3, this.zzaar[i2]);
        }
    }

    public final void zzb(zzlr zzlrVar) throws IOException {
        if (this.count == 0) {
            return;
        }
        if (zzlrVar.zzgd() == zzid.zzf.zzys) {
            for (int i = 0; i < this.count; i++) {
                zzb(this.zzacg[i], this.zzaar[i], zzlrVar);
            }
            return;
        }
        for (int i2 = this.count - 1; i2 >= 0; i2--) {
            zzb(this.zzacg[i2], this.zzaar[i2], zzlrVar);
        }
    }

    private static void zzb(int i, Object obj, zzlr zzlrVar) throws IOException {
        int i2 = i >>> 3;
        int i3 = i & 7;
        if (i3 == 0) {
            zzlrVar.zzi(i2, ((Long) obj).longValue());
            return;
        }
        if (i3 == 1) {
            zzlrVar.zzc(i2, ((Long) obj).longValue());
            return;
        }
        if (i3 == 2) {
            zzlrVar.zza(i2, (zzgs) obj);
            return;
        }
        if (i3 != 3) {
            if (i3 == 5) {
                zzlrVar.zzm(i2, ((Integer) obj).intValue());
                return;
            }
            throw new RuntimeException(zzin.zzhm());
        }
        if (zzlrVar.zzgd() == zzid.zzf.zzys) {
            zzlrVar.zzbq(i2);
            ((zzkx) obj).zzb(zzlrVar);
            zzlrVar.zzbr(i2);
        } else {
            zzlrVar.zzbr(i2);
            ((zzkx) obj).zzb(zzlrVar);
            zzlrVar.zzbq(i2);
        }
    }

    public final int zzjd() {
        int i = this.zzya;
        if (i != -1) {
            return i;
        }
        int iZzd = 0;
        for (int i2 = 0; i2 < this.count; i2++) {
            iZzd += zzhl.zzd(this.zzacg[i2] >>> 3, (zzgs) this.zzaar[i2]);
        }
        this.zzya = iZzd;
        return iZzd;
    }

    public final int zzgz() {
        int iZze;
        int i = this.zzya;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.count; i3++) {
            int i4 = this.zzacg[i3];
            int i5 = i4 >>> 3;
            int i6 = i4 & 7;
            if (i6 == 0) {
                iZze = zzhl.zze(i5, ((Long) this.zzaar[i3]).longValue());
            } else if (i6 == 1) {
                iZze = zzhl.zzg(i5, ((Long) this.zzaar[i3]).longValue());
            } else if (i6 == 2) {
                iZze = zzhl.zzc(i5, (zzgs) this.zzaar[i3]);
            } else if (i6 == 3) {
                iZze = (zzhl.zzbh(i5) << 1) + ((zzkx) this.zzaar[i3]).zzgz();
            } else if (i6 == 5) {
                iZze = zzhl.zzq(i5, ((Integer) this.zzaar[i3]).intValue());
            } else {
                throw new IllegalStateException(zzin.zzhm());
            }
            i2 += iZze;
        }
        this.zzya = i2;
        return i2;
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzkx)) {
            return false;
        }
        zzkx zzkxVar = (zzkx) obj;
        int i = this.count;
        if (i == zzkxVar.count) {
            int[] iArr = this.zzacg;
            int[] iArr2 = zzkxVar.zzacg;
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
                Object[] objArr = this.zzaar;
                Object[] objArr2 = zzkxVar.zzaar;
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
        int[] iArr = this.zzacg;
        int iHashCode = 17;
        int i3 = 17;
        for (int i4 = 0; i4 < i; i4++) {
            i3 = (i3 * 31) + iArr[i4];
        }
        int i5 = (i2 + i3) * 31;
        Object[] objArr = this.zzaar;
        int i6 = this.count;
        for (int i7 = 0; i7 < i6; i7++) {
            iHashCode = (iHashCode * 31) + objArr[i7].hashCode();
        }
        return i5 + iHashCode;
    }

    final void zza(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.count; i2++) {
            zzjo.zza(sb, i, String.valueOf(this.zzacg[i2] >>> 3), this.zzaar[i2]);
        }
    }

    final void zzb(int i, Object obj) {
        if (!this.zztf) {
            throw new UnsupportedOperationException();
        }
        int i2 = this.count;
        int[] iArr = this.zzacg;
        if (i2 == iArr.length) {
            int i3 = i2 + (i2 < 4 ? 8 : i2 >> 1);
            this.zzacg = Arrays.copyOf(iArr, i3);
            this.zzaar = Arrays.copyOf(this.zzaar, i3);
        }
        int[] iArr2 = this.zzacg;
        int i4 = this.count;
        iArr2[i4] = i;
        this.zzaar[i4] = obj;
        this.count = i4 + 1;
    }
}
