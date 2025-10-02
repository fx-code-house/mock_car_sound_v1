package com.google.android.gms.internal.icing;

import android.database.ContentObserver;
import android.os.Handler;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzbe extends ContentObserver {
    private final /* synthetic */ zzbc zzcq;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbe(zzbc zzbcVar, Handler handler) {
        super(null);
        this.zzcq = zzbcVar;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        this.zzcq.zzn();
    }
}
