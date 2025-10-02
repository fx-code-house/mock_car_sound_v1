package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
final class zzfd implements Runnable {
    private final /* synthetic */ boolean zza;
    private final /* synthetic */ zzfa zzb;

    zzfd(zzfa zzfaVar, boolean z) {
        this.zzb = zzfaVar;
        this.zza = z;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zzb.zzb.zza(this.zza);
    }
}
