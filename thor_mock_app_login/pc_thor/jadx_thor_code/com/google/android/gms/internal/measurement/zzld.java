package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
abstract class zzld {
    zzld() {
    }

    abstract int zza(int i, byte[] bArr, int i2, int i3);

    abstract int zza(CharSequence charSequence, byte[] bArr, int i, int i2);

    abstract String zza(byte[] bArr, int i, int i2) throws zzij;

    final boolean zzb(byte[] bArr, int i, int i2) {
        return zza(0, bArr, i, i2) == 0;
    }
}
