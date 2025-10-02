package com.google.android.gms.internal.wallet;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wallet.CreateWalletObjectsRequest;
import com.google.android.gms.wallet.wobs.WalletObjects;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzaa implements WalletObjects {
    @Override // com.google.android.gms.wallet.wobs.WalletObjects
    public final void createWalletObjects(GoogleApiClient googleApiClient, CreateWalletObjectsRequest createWalletObjectsRequest, int i) {
        googleApiClient.enqueue(new zzac(this, googleApiClient, createWalletObjectsRequest, i));
    }
}
