package com.google.android.gms.wallet;

import android.app.Activity;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.Wallet;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public class WalletObjectsClient extends GoogleApi<Wallet.WalletOptions> {
    WalletObjectsClient(Activity activity, Wallet.WalletOptions walletOptions) {
        super(activity, Wallet.API, walletOptions, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public Task<AutoResolvableVoidResult> createWalletObjects(CreateWalletObjectsRequest createWalletObjectsRequest) {
        return doWrite(new zzak(this, createWalletObjectsRequest));
    }
}
