package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzgw extends zzgz {
    private final int zzc;
    private final int zzd;

    zzgw(byte[] bArr, int i, int i2) {
        super(bArr);
        zzb(i, i + i2, bArr.length);
        this.zzc = i;
        this.zzd = i2;
    }

    @Override // com.google.android.gms.internal.measurement.zzgz, com.google.android.gms.internal.measurement.zzgp
    public final byte zza(int i) {
        int iZza = zza();
        if (((iZza - (i + 1)) | i) >= 0) {
            return this.zzb[this.zzc + i];
        }
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException(new StringBuilder(22).append("Index < 0: ").append(i).toString());
        }
        throw new ArrayIndexOutOfBoundsException(new StringBuilder(40).append("Index > length: ").append(i).append(", ").append(iZza).toString());
    }

    @Override // com.google.android.gms.internal.measurement.zzgz, com.google.android.gms.internal.measurement.zzgp
    final byte zzb(int i) {
        return this.zzb[this.zzc + i];
    }

    @Override // com.google.android.gms.internal.measurement.zzgz, com.google.android.gms.internal.measurement.zzgp
    public final int zza() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.measurement.zzgz
    protected final int zze() {
        return this.zzc;
    }
}
