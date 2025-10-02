package com.google.android.gms.internal.icing;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.icing.zzaj;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzai extends zzaj.zzd<Status> {
    private final /* synthetic */ zzw[] zzat;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzai(zzaj zzajVar, GoogleApiClient googleApiClient, zzw[] zzwVarArr) {
        super(googleApiClient);
        this.zzat = zzwVarArr;
    }

    @Override // com.google.android.gms.internal.icing.zzaj.zza
    protected final void zza(zzaa zzaaVar) throws RemoteException {
        zzaaVar.zza(new zzaj.zzc(this), null, this.zzat);
    }
}
