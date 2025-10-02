package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Channel;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzbb extends zzn<Channel.GetInputStreamResult> {
    final /* synthetic */ zzbi zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbb(zzbi zzbiVar, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zza = zzbiVar;
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public final /* bridge */ /* synthetic */ Result createFailedResult(Status status) {
        return new zzbg(status, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        String str = this.zza.zza;
        zzbs zzbsVar = new zzbs();
        ((zzeu) ((zzhv) anyClient).getService()).zzx(new zzhd(this, zzbsVar), zzbsVar, str);
    }
}
