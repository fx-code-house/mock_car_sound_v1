package com.google.android.gms.vision.clearcut;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzb {
    private final Object lock = new Object();
    private long zzcc = Long.MIN_VALUE;
    private final long zzcb = Math.round(30000.0d);

    public zzb(double d) {
    }

    public final boolean tryAcquire() {
        synchronized (this.lock) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (this.zzcc + this.zzcb > jCurrentTimeMillis) {
                return false;
            }
            this.zzcc = jCurrentTimeMillis;
            return true;
        }
    }
}
