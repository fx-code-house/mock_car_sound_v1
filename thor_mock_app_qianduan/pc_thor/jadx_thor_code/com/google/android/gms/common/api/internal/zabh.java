package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* loaded from: classes2.dex */
final class zabh extends com.google.android.gms.internal.base.zaq {
    final /* synthetic */ zabi zaa;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zabh(zabi zabiVar, Looper looper) {
        super(looper);
        this.zaa = zabiVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            ((zabg) message.obj).zab(this.zaa);
            return;
        }
        if (i == 2) {
            throw ((RuntimeException) message.obj);
        }
        int i2 = message.what;
        StringBuilder sb = new StringBuilder(31);
        sb.append("Unknown message id: ");
        sb.append(i2);
        Log.w("GACStateManager", sb.toString());
    }
}
