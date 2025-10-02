package com.google.android.gms.internal.wallet;

import android.os.Bundle;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
final class zzw extends zzx {
    private final TaskCompletionSource<Boolean> zzes;

    zzw(TaskCompletionSource<Boolean> taskCompletionSource) {
        this.zzes = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.wallet.zzx, com.google.android.gms.internal.wallet.zzq
    public final void zza(int i, boolean z, Bundle bundle) {
        TaskUtil.setResultOrApiException(new Status(i), Boolean.valueOf(z), this.zzes);
    }

    @Override // com.google.android.gms.internal.wallet.zzx, com.google.android.gms.internal.wallet.zzq
    public final void zza(Status status, boolean z, Bundle bundle) {
        TaskUtil.setResultOrApiException(status, Boolean.valueOf(z), this.zzes);
    }
}
