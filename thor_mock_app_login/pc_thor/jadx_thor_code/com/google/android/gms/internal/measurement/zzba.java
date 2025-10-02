package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.internal.measurement.zzag;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@18.0.0 */
/* loaded from: classes2.dex */
final class zzba extends zzag.zzb {
    private final /* synthetic */ String zzc;
    private final /* synthetic */ String zzd;
    private final /* synthetic */ boolean zze;
    private final /* synthetic */ zzt zzf;
    private final /* synthetic */ zzag zzg;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzba(zzag zzagVar, String str, String str2, boolean z, zzt zztVar) {
        super(zzagVar);
        this.zzg = zzagVar;
        this.zzc = str;
        this.zzd = str2;
        this.zze = z;
        this.zzf = zztVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzag.zzb
    final void zza() throws RemoteException {
        this.zzg.zzm.getUserProperties(this.zzc, this.zzd, this.zze, this.zzf);
    }

    @Override // com.google.android.gms.internal.measurement.zzag.zzb
    protected final void zzb() {
        this.zzf.zza((Bundle) null);
    }
}
