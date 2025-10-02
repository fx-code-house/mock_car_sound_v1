package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.os.RemoteException;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.measurement.zzag;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@18.0.0 */
/* loaded from: classes2.dex */
final class zzbs extends zzag.zzb {
    private final /* synthetic */ Activity zzc;
    private final /* synthetic */ zzt zzd;
    private final /* synthetic */ zzag.zzc zze;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbs(zzag.zzc zzcVar, Activity activity, zzt zztVar) {
        super(zzag.this);
        this.zze = zzcVar;
        this.zzc = activity;
        this.zzd = zztVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzag.zzb
    final void zza() throws RemoteException {
        zzag.this.zzm.onActivitySaveInstanceState(ObjectWrapper.wrap(this.zzc), this.zzd, this.zzb);
    }
}
