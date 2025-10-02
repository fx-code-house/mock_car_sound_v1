package com.google.android.gms.internal.icing;

import android.database.ContentObserver;
import android.os.Handler;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzbj extends ContentObserver {
    zzbj(zzbh zzbhVar, Handler handler) {
        super(null);
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        zzbq.zzt();
    }
}
