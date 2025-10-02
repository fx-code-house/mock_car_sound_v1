package com.google.android.gms.internal.vision;

import android.database.ContentObserver;
import android.os.Handler;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzaw extends ContentObserver {
    private final /* synthetic */ zzau zzfx;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaw(zzau zzauVar, Handler handler) {
        super(null);
        this.zzfx = zzauVar;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        this.zzfx.zzw();
    }
}
