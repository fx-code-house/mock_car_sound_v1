package com.google.android.gms.internal.vision;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzfi extends WeakReference<Throwable> {
    private final int zzoc;

    public zzfi(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, referenceQueue);
        if (th == null) {
            throw new NullPointerException("The referent cannot be null");
        }
        this.zzoc = System.identityHashCode(th);
    }

    public final int hashCode() {
        return this.zzoc;
    }

    public final boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (this == obj) {
                return true;
            }
            zzfi zzfiVar = (zzfi) obj;
            if (this.zzoc == zzfiVar.zzoc && get() == zzfiVar.get()) {
                return true;
            }
        }
        return false;
    }
}
