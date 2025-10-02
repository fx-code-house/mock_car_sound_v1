package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.nio.charset.Charset;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
class zzgz extends zzha {
    protected final byte[] zzb;

    zzgz(byte[] bArr) {
        bArr.getClass();
        this.zzb = bArr;
    }

    protected int zze() {
        return 0;
    }

    @Override // com.google.android.gms.internal.measurement.zzgp
    public byte zza(int i) {
        return this.zzb[i];
    }

    @Override // com.google.android.gms.internal.measurement.zzgp
    byte zzb(int i) {
        return this.zzb[i];
    }

    @Override // com.google.android.gms.internal.measurement.zzgp
    public int zza() {
        return this.zzb.length;
    }

    @Override // com.google.android.gms.internal.measurement.zzgp
    public final zzgp zza(int i, int i2) {
        int iZzb = zzb(0, i2, zza());
        if (iZzb == 0) {
            return zzgp.zza;
        }
        return new zzgw(this.zzb, zze(), iZzb);
    }

    @Override // com.google.android.gms.internal.measurement.zzgp
    final void zza(zzgq zzgqVar) throws IOException {
        zzgqVar.zza(this.zzb, zze(), zza());
    }

    @Override // com.google.android.gms.internal.measurement.zzgp
    protected final String zza(Charset charset) {
        return new String(this.zzb, zze(), zza(), charset);
    }

    @Override // com.google.android.gms.internal.measurement.zzgp
    public final boolean zzc() {
        int iZze = zze();
        return zzlb.zza(this.zzb, iZze, zza() + iZze);
    }

    @Override // com.google.android.gms.internal.measurement.zzgp
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgp) || zza() != ((zzgp) obj).zza()) {
            return false;
        }
        if (zza() == 0) {
            return true;
        }
        if (obj instanceof zzgz) {
            zzgz zzgzVar = (zzgz) obj;
            int iZzd = zzd();
            int iZzd2 = zzgzVar.zzd();
            if (iZzd == 0 || iZzd2 == 0 || iZzd == iZzd2) {
                return zza(zzgzVar, 0, zza());
            }
            return false;
        }
        return obj.equals(this);
    }

    @Override // com.google.android.gms.internal.measurement.zzha
    final boolean zza(zzgp zzgpVar, int i, int i2) {
        if (i2 > zzgpVar.zza()) {
            throw new IllegalArgumentException(new StringBuilder(40).append("Length too large: ").append(i2).append(zza()).toString());
        }
        if (i2 > zzgpVar.zza()) {
            throw new IllegalArgumentException(new StringBuilder(59).append("Ran off end of other: 0, ").append(i2).append(", ").append(zzgpVar.zza()).toString());
        }
        if (zzgpVar instanceof zzgz) {
            zzgz zzgzVar = (zzgz) zzgpVar;
            byte[] bArr = this.zzb;
            byte[] bArr2 = zzgzVar.zzb;
            int iZze = zze() + i2;
            int iZze2 = zze();
            int iZze3 = zzgzVar.zze();
            while (iZze2 < iZze) {
                if (bArr[iZze2] != bArr2[iZze3]) {
                    return false;
                }
                iZze2++;
                iZze3++;
            }
            return true;
        }
        return zzgpVar.zza(0, i2).equals(zza(0, i2));
    }

    @Override // com.google.android.gms.internal.measurement.zzgp
    protected final int zza(int i, int i2, int i3) {
        return zzia.zza(i, this.zzb, zze(), i3);
    }
}
