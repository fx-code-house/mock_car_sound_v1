package com.google.android.gms.internal.vision;

import android.os.Binder;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final /* synthetic */ class zzbb {
    public static <V> V zza(zzba<V> zzbaVar) {
        try {
            return zzbaVar.zzac();
        } catch (SecurityException unused) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return zzbaVar.zzac();
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }
}
