package com.google.android.gms.internal.vision;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzfh extends zzfg {
    private final zzff zzob = new zzff();

    zzfh() {
    }

    @Override // com.google.android.gms.internal.vision.zzfg
    public final void zza(Throwable th, Throwable th2) {
        if (th2 == th) {
            throw new IllegalArgumentException("Self suppression is not allowed.", th2);
        }
        if (th2 == null) {
            throw new NullPointerException("The suppressed exception cannot be null.");
        }
        this.zzob.zza(th, true).add(th2);
    }

    @Override // com.google.android.gms.internal.vision.zzfg
    public final void zza(Throwable th) {
        th.printStackTrace();
        List<Throwable> listZza = this.zzob.zza(th, false);
        if (listZza == null) {
            return;
        }
        synchronized (listZza) {
            for (Throwable th2 : listZza) {
                System.err.print("Suppressed: ");
                th2.printStackTrace();
            }
        }
    }
}
