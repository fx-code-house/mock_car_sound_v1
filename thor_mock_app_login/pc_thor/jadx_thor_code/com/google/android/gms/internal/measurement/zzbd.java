package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.measurement.zzag;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@18.0.0 */
/* loaded from: classes2.dex */
final class zzbd extends zzag.zzb {
    private final /* synthetic */ String zzd;
    private final /* synthetic */ Object zze;
    private final /* synthetic */ zzag zzh;
    private final /* synthetic */ int zzc = 5;
    private final /* synthetic */ Object zzf = null;
    private final /* synthetic */ Object zzg = null;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbd(zzag zzagVar, boolean z, int i, String str, Object obj, Object obj2, Object obj3) {
        super(false);
        this.zzh = zzagVar;
        this.zzd = str;
        this.zze = obj;
    }

    @Override // com.google.android.gms.internal.measurement.zzag.zzb
    final void zza() throws RemoteException {
        this.zzh.zzm.logHealthData(this.zzc, this.zzd, ObjectWrapper.wrap(this.zze), ObjectWrapper.wrap(null), ObjectWrapper.wrap(null));
    }
}
