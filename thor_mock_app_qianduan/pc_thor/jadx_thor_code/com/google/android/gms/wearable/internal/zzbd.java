package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzbd extends zzn<Status> {
    final /* synthetic */ Uri zza;
    final /* synthetic */ boolean zzb;
    final /* synthetic */ zzbi zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbd(zzbi zzbiVar, GoogleApiClient googleApiClient, Uri uri, boolean z) {
        super(googleApiClient);
        this.zzc = zzbiVar;
        this.zza = uri;
        this.zzb = z;
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public final /* bridge */ /* synthetic */ Result createFailedResult(Status status) {
        return status;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzhv) anyClient).zzr(this, this.zzc.zza, this.zza, this.zzb);
    }
}
