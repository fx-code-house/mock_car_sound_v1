package com.google.android.gms.internal.icing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzda extends zzdd {
    private final int zzgm;
    private final int zzgn;

    zzda(byte[] bArr, int i, int i2) {
        super(bArr);
        zzb(i, i + i2, bArr.length);
        this.zzgm = i;
        this.zzgn = i2;
    }

    @Override // com.google.android.gms.internal.icing.zzdd, com.google.android.gms.internal.icing.zzct
    public final byte zzk(int i) {
        int size = size();
        if (((size - (i + 1)) | i) >= 0) {
            return this.zzgp[this.zzgm + i];
        }
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException(new StringBuilder(22).append("Index < 0: ").append(i).toString());
        }
        throw new ArrayIndexOutOfBoundsException(new StringBuilder(40).append("Index > length: ").append(i).append(", ").append(size).toString());
    }

    @Override // com.google.android.gms.internal.icing.zzdd, com.google.android.gms.internal.icing.zzct
    final byte zzl(int i) {
        return this.zzgp[this.zzgm + i];
    }

    @Override // com.google.android.gms.internal.icing.zzdd, com.google.android.gms.internal.icing.zzct
    public final int size() {
        return this.zzgn;
    }

    @Override // com.google.android.gms.internal.icing.zzdd
    protected final int zzaq() {
        return this.zzgm;
    }
}
