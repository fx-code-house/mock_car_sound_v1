package com.google.android.gms.internal.wallet;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wallet.AutoResolvableVoidResult;
import com.google.android.gms.wallet.CreateWalletObjectsRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzv extends GmsClient<zzo> {
    private final int environment;
    private final int theme;
    private final String zzcc;
    private final boolean zzef;
    private final Context zzer;

    public zzv(Context context, Looper looper, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, int i, int i2, boolean z) {
        super(context, looper, 4, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzer = context;
        this.environment = i;
        this.zzcc = clientSettings.getAccountName();
        this.theme = i2;
        this.zzef = z;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final int getMinApkVersion() {
        return 12600000;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.wallet.internal.IOwService";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final String getStartServiceAction() {
        return "com.google.android.gms.wallet.service.BIND";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final boolean requiresAccount() {
        return true;
    }

    public final void zza(CreateWalletObjectsRequest createWalletObjectsRequest, int i) {
        zzu zzuVar = new zzu((Activity) this.zzer, i);
        try {
            ((zzo) getService()).zza(createWalletObjectsRequest, zzd(), zzuVar);
        } catch (RemoteException e) {
            Log.e("WalletClientImpl", "RemoteException creating wallet objects", e);
            zzuVar.zza(8, Bundle.EMPTY);
        }
    }

    public final void zza(CreateWalletObjectsRequest createWalletObjectsRequest, TaskCompletionSource<AutoResolvableVoidResult> taskCompletionSource) {
        Bundle bundleZzd = zzd();
        bundleZzd.putBoolean("com.google.android.gms.wallet.EXTRA_USING_AUTO_RESOLVABLE_RESULT", true);
        zzz zzzVar = new zzz(taskCompletionSource);
        try {
            ((zzo) getService()).zza(createWalletObjectsRequest, bundleZzd, zzzVar);
        } catch (RemoteException e) {
            Log.e("WalletClientImpl", "RemoteException creating wallet objects", e);
            zzzVar.zza(8, Bundle.EMPTY);
        }
    }

    public final void zza(IsReadyToPayRequest isReadyToPayRequest, TaskCompletionSource<Boolean> taskCompletionSource) throws RemoteException {
        zzw zzwVar = new zzw(taskCompletionSource);
        try {
            ((zzo) getService()).zza(isReadyToPayRequest, zzd(), zzwVar);
        } catch (RemoteException e) {
            Log.e("WalletClientImpl", "RemoteException during isReadyToPay", e);
            zzwVar.zza(Status.RESULT_INTERNAL_ERROR, false, Bundle.EMPTY);
        }
    }

    public final void zza(PaymentDataRequest paymentDataRequest, TaskCompletionSource<PaymentData> taskCompletionSource) {
        Bundle bundleZzd = zzd();
        bundleZzd.putBoolean("com.google.android.gms.wallet.EXTRA_USING_AUTO_RESOLVABLE_RESULT", true);
        zzy zzyVar = new zzy(taskCompletionSource);
        try {
            ((zzo) getService()).zza(paymentDataRequest, bundleZzd, zzyVar);
        } catch (RemoteException e) {
            Log.e("WalletClientImpl", "RemoteException getting payment data", e);
            zzyVar.zza(Status.RESULT_INTERNAL_ERROR, (PaymentData) null, Bundle.EMPTY);
        }
    }

    private final Bundle zzd() {
        int i = this.environment;
        String packageName = this.zzer.getPackageName();
        String str = this.zzcc;
        int i2 = this.theme;
        boolean z = this.zzef;
        Bundle bundle = new Bundle();
        bundle.putInt("com.google.android.gms.wallet.EXTRA_ENVIRONMENT", i);
        bundle.putBoolean("com.google.android.gms.wallet.EXTRA_USING_ANDROID_PAY_BRAND", z);
        bundle.putString("androidPackageName", packageName);
        if (!TextUtils.isEmpty(str)) {
            bundle.putParcelable("com.google.android.gms.wallet.EXTRA_BUYER_ACCOUNT", new Account(str, "com.google"));
        }
        bundle.putInt("com.google.android.gms.wallet.EXTRA_THEME", i2);
        return bundle;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final Feature[] getApiFeatures() {
        return com.google.android.gms.wallet.zzh.zzbg;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.internal.IOwService");
        if (iInterfaceQueryLocalInterface instanceof zzo) {
            return (zzo) iInterfaceQueryLocalInterface;
        }
        return new zzr(iBinder);
    }
}
