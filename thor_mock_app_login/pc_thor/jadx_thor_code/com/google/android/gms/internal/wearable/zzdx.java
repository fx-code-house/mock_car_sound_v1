package com.google.android.gms.internal.wearable;

import java.io.IOException;
import java.util.Arrays;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzdx {
    private static final zzdx zza = new zzdx(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    private zzdx() {
        this(0, new int[8], new Object[8], true);
    }

    private zzdx(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public static zzdx zza() {
        return zza;
    }

    static zzdx zzb() {
        return new zzdx(0, new int[8], new Object[8], true);
    }

    static zzdx zzc(zzdx zzdxVar, zzdx zzdxVar2) {
        int i = zzdxVar.zzb + zzdxVar2.zzb;
        int[] iArrCopyOf = Arrays.copyOf(zzdxVar.zzc, i);
        System.arraycopy(zzdxVar2.zzc, 0, iArrCopyOf, zzdxVar.zzb, zzdxVar2.zzb);
        Object[] objArrCopyOf = Arrays.copyOf(zzdxVar.zzd, i);
        System.arraycopy(zzdxVar2.zzd, 0, objArrCopyOf, zzdxVar.zzb, zzdxVar2.zzb);
        return new zzdx(i, iArrCopyOf, objArrCopyOf, true);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzdx)) {
            return false;
        }
        zzdx zzdxVar = (zzdx) obj;
        int i = this.zzb;
        if (i == zzdxVar.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zzdxVar.zzc;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    Object[] objArr = this.zzd;
                    Object[] objArr2 = zzdxVar.zzd;
                    int i3 = this.zzb;
                    for (int i4 = 0; i4 < i3; i4++) {
                        if (objArr[i4].equals(objArr2[i4])) {
                        }
                    }
                    return true;
                }
                if (iArr[i2] != iArr2[i2]) {
                    break;
                }
                i2++;
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

    public final void zzd() {
        this.zzf = false;
    }

    public final int zze() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int iZzw = 0;
        for (int i2 = 0; i2 < this.zzb; i2++) {
            int i3 = this.zzc[i2];
            zzau zzauVar = (zzau) this.zzd[i2];
            int iZzw2 = zzbb.zzw(8);
            int iZzc = zzauVar.zzc();
            iZzw += iZzw2 + iZzw2 + zzbb.zzw(16) + zzbb.zzw(i3 >>> 3) + zzbb.zzw(24) + zzbb.zzw(iZzc) + iZzc;
        }
        this.zze = iZzw;
        return iZzw;
    }

    public final int zzf() {
        int iZzw;
        int iZzx;
        int iZzw2;
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int iZzw3 = 0;
        for (int i2 = 0; i2 < this.zzb; i2++) {
            int i3 = this.zzc[i2];
            int i4 = i3 >>> 3;
            int i5 = i3 & 7;
            if (i5 != 0) {
                if (i5 == 1) {
                    ((Long) this.zzd[i2]).longValue();
                    iZzw2 = zzbb.zzw(i4 << 3) + 8;
                } else if (i5 == 2) {
                    zzau zzauVar = (zzau) this.zzd[i2];
                    int iZzw4 = zzbb.zzw(i4 << 3);
                    int iZzc = zzauVar.zzc();
                    iZzw3 += iZzw4 + zzbb.zzw(iZzc) + iZzc;
                } else if (i5 == 3) {
                    int iZzu = zzbb.zzu(i4);
                    iZzw = iZzu + iZzu;
                    iZzx = ((zzdx) this.zzd[i2]).zzf();
                } else {
                    if (i5 != 5) {
                        throw new IllegalStateException(zzcc.zze());
                    }
                    ((Integer) this.zzd[i2]).intValue();
                    iZzw2 = zzbb.zzw(i4 << 3) + 4;
                }
                iZzw3 += iZzw2;
            } else {
                long jLongValue = ((Long) this.zzd[i2]).longValue();
                iZzw = zzbb.zzw(i4 << 3);
                iZzx = zzbb.zzx(jLongValue);
            }
            iZzw2 = iZzw + iZzx;
            iZzw3 += iZzw2;
        }
        this.zze = iZzw3;
        return iZzw3;
    }

    final void zzg(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzcz.zzb(sb, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
        }
    }

    final void zzh(int i, Object obj) {
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

    public final void zzi(zzbc zzbcVar) throws IOException {
        if (this.zzb != 0) {
            for (int i = 0; i < this.zzb; i++) {
                int i2 = this.zzc[i];
                Object obj = this.zzd[i];
                int i3 = i2 >>> 3;
                int i4 = i2 & 7;
                if (i4 == 0) {
                    zzbcVar.zzc(i3, ((Long) obj).longValue());
                } else if (i4 == 1) {
                    zzbcVar.zzj(i3, ((Long) obj).longValue());
                } else if (i4 == 2) {
                    zzbcVar.zzn(i3, (zzau) obj);
                } else if (i4 == 3) {
                    zzbcVar.zzt(i3);
                    ((zzdx) obj).zzi(zzbcVar);
                    zzbcVar.zzu(i3);
                } else {
                    if (i4 != 5) {
                        throw new RuntimeException(zzcc.zze());
                    }
                    zzbcVar.zzk(i3, ((Integer) obj).intValue());
                }
            }
        }
    }
}
