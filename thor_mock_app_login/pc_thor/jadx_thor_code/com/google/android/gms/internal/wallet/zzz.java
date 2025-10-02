package com.google.android.gms.internal.wallet;

import android.app.PendingIntent;
import android.os.Bundle;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wallet.AutoResolvableVoidResult;
import com.google.android.gms.wallet.AutoResolveHelper;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
final class zzz extends zzx {
    private final TaskCompletionSource<AutoResolvableVoidResult> zzes;

    public zzz(TaskCompletionSource<AutoResolvableVoidResult> taskCompletionSource) {
        this.zzes = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.wallet.zzx, com.google.android.gms.internal.wallet.zzq
    public final void zza(int i, Bundle bundle) {
        Status status;
        PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT");
        if (pendingIntent != null && i == 6) {
            status = new Status(i, "Need to resolve PendingIntent", pendingIntent);
        } else {
            status = new Status(i);
        }
        AutoResolveHelper.zza(status, new AutoResolvableVoidResult(), this.zzes);
    }
}
