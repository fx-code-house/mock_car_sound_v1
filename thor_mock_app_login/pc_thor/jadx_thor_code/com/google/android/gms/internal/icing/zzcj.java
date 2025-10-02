package com.google.android.gms.internal.icing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzcj extends zzci {
    private final zzch zzee = new zzch();

    zzcj() {
    }

    @Override // com.google.android.gms.internal.icing.zzci
    public final void zza(Throwable th, Throwable th2) {
        if (th2 == th) {
            throw new IllegalArgumentException("Self suppression is not allowed.", th2);
        }
        if (th2 == null) {
            throw new NullPointerException("The suppressed exception cannot be null.");
        }
        this.zzee.zza(th, true).add(th2);
    }
}
