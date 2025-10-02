package com.google.android.gms.internal.icing;

import android.os.Binder;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class zzbf {
    public static <V> V zza(zzbi<V> zzbiVar) {
        try {
            return zzbiVar.zzl();
        } catch (SecurityException unused) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return zzbiVar.zzl();
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }
}
