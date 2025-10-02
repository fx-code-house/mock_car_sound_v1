package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzgz extends zzhc {
    private final int zztx;
    private final int zzty;

    zzgz(byte[] bArr, int i, int i2) {
        super(bArr);
        zze(i, i + i2, bArr.length);
        this.zztx = i;
        this.zzty = i2;
    }

    @Override // com.google.android.gms.internal.vision.zzhc, com.google.android.gms.internal.vision.zzgs
    public final byte zzau(int i) {
        int size = size();
        if (((size - (i + 1)) | i) >= 0) {
            return this.zzua[this.zztx + i];
        }
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException(new StringBuilder(22).append("Index < 0: ").append(i).toString());
        }
        throw new ArrayIndexOutOfBoundsException(new StringBuilder(40).append("Index > length: ").append(i).append(", ").append(size).toString());
    }

    @Override // com.google.android.gms.internal.vision.zzhc, com.google.android.gms.internal.vision.zzgs
    final byte zzav(int i) {
        return this.zzua[this.zztx + i];
    }

    @Override // com.google.android.gms.internal.vision.zzhc, com.google.android.gms.internal.vision.zzgs
    public final int size() {
        return this.zzty;
    }

    @Override // com.google.android.gms.internal.vision.zzhc
    protected final int zzfo() {
        return this.zztx;
    }

    @Override // com.google.android.gms.internal.vision.zzhc, com.google.android.gms.internal.vision.zzgs
    protected final void zza(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zzua, zzfo(), bArr, 0, i3);
    }
}
