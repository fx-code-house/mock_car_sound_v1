package com.google.android.gms.internal.measurement;

import android.database.ContentObserver;
import android.os.Handler;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
final class zzcv extends ContentObserver {
    private final /* synthetic */ zzct zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcv(zzct zzctVar, Handler handler) {
        super(null);
        this.zza = zzctVar;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        this.zza.zzb();
    }
}
