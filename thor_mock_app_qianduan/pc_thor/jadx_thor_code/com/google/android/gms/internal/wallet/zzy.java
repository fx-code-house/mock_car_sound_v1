package com.google.android.gms.internal.wallet;

import android.os.Bundle;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
final class zzy extends zzx {
    private final TaskCompletionSource<PaymentData> zzes;

    public zzy(TaskCompletionSource<PaymentData> taskCompletionSource) {
        this.zzes = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.wallet.zzx, com.google.android.gms.internal.wallet.zzq
    public final void zza(Status status, PaymentData paymentData, Bundle bundle) {
        AutoResolveHelper.zza(status, paymentData, this.zzes);
    }
}
