package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzgx {
    private final zzhi zza;
    private final byte[] zzb;

    private zzgx(int i) {
        byte[] bArr = new byte[i];
        this.zzb = bArr;
        this.zza = zzhi.zza(bArr);
    }

    public final zzgp zza() {
        this.zza.zzb();
        return new zzgz(this.zzb);
    }

    public final zzhi zzb() {
        return this.zza;
    }

    /* synthetic */ zzgx(int i, zzgs zzgsVar) {
        this(i);
    }
}
