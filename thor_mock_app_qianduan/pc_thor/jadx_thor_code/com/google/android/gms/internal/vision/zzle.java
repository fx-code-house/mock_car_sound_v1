package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
abstract class zzle {
    zzle() {
    }

    abstract int zzb(int i, byte[] bArr, int i2, int i3);

    abstract int zzb(CharSequence charSequence, byte[] bArr, int i, int i2);

    abstract String zzh(byte[] bArr, int i, int i2) throws zzin;

    final boolean zzf(byte[] bArr, int i, int i2) {
        return zzb(0, bArr, i, i2) == 0;
    }
}
