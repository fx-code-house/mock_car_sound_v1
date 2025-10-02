package com.google.android.gms.internal.icing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
abstract class zzgx {
    zzgx() {
    }

    abstract int zzb(int i, byte[] bArr, int i2, int i3);

    abstract int zzb(CharSequence charSequence, byte[] bArr, int i, int i2);

    final boolean zzc(byte[] bArr, int i, int i2) {
        return zzb(0, bArr, i, i2) == 0;
    }
}
