package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzfj extends zzfg {
    zzfj() {
    }

    @Override // com.google.android.gms.internal.vision.zzfg
    public final void zza(Throwable th, Throwable th2) {
        th.addSuppressed(th2);
    }

    @Override // com.google.android.gms.internal.vision.zzfg
    public final void zza(Throwable th) {
        th.printStackTrace();
    }
}
