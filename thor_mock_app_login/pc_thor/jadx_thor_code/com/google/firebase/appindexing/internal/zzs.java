package com.google.firebase.appindexing.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.icing.zzah;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
abstract class zzs extends TaskApiCall<zzah, Void> implements BaseImplementation.ResultHolder<Status> {
    private TaskCompletionSource<Void> zzfn;

    private zzs() {
    }

    protected abstract void zza(com.google.android.gms.internal.icing.zzaa zzaaVar) throws RemoteException;

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder
    public void setFailedResult(Status status) {
        Preconditions.checkArgument(!status.isSuccess(), "Failed result must not be success.");
        this.zzfn.setException(zzaf.zza(status, status.getStatusMessage()));
    }

    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    protected /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        this.zzfn = taskCompletionSource;
        zza((com.google.android.gms.internal.icing.zzaa) ((zzah) anyClient).getService());
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder
    public /* synthetic */ void setResult(Object obj) {
        Status status = (Status) obj;
        if (status.isSuccess()) {
            this.zzfn.setResult(null);
        } else {
            this.zzfn.setException(zzaf.zza(status, "User Action indexing error, please try again."));
        }
    }

    /* synthetic */ zzs(zzq zzqVar) {
        this();
    }
}
