package com.google.android.gms.internal.wallet;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.WalletConstants;
import java.lang.ref.WeakReference;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
final class zzu extends zzx {
    private final int zzag;
    private final WeakReference<Activity> zzeq;

    public zzu(Activity activity, int i) {
        this.zzeq = new WeakReference<>(activity);
        this.zzag = i;
    }

    @Override // com.google.android.gms.internal.wallet.zzx, com.google.android.gms.internal.wallet.zzq
    public final void zza(int i, MaskedWallet maskedWallet, Bundle bundle) throws PendingIntent.CanceledException {
        int i2;
        Activity activity = this.zzeq.get();
        if (activity == null) {
            Log.d("WalletClientImpl", "Ignoring onMaskedWalletLoaded, Activity has gone");
            return;
        }
        ConnectionResult connectionResult = new ConnectionResult(i, bundle != null ? (PendingIntent) bundle.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT") : null);
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(activity, this.zzag);
                return;
            } catch (IntentSender.SendIntentException e) {
                Log.w("WalletClientImpl", "Exception starting pending intent", e);
                return;
            }
        }
        Intent intent = new Intent();
        if (connectionResult.isSuccess()) {
            intent.putExtra("com.google.android.gms.wallet.EXTRA_MASKED_WALLET", maskedWallet);
            i2 = -1;
        } else {
            int i3 = i == 408 ? 0 : 1;
            intent.putExtra(WalletConstants.EXTRA_ERROR_CODE, i);
            i2 = i3;
        }
        PendingIntent pendingIntentCreatePendingResult = activity.createPendingResult(this.zzag, intent, 1073741824);
        if (pendingIntentCreatePendingResult == null) {
            Log.w("WalletClientImpl", "Null pending result returned for onMaskedWalletLoaded");
            return;
        }
        try {
            pendingIntentCreatePendingResult.send(i2);
        } catch (PendingIntent.CanceledException e2) {
            Log.w("WalletClientImpl", "Exception setting pending result", e2);
        }
    }

    @Override // com.google.android.gms.internal.wallet.zzx, com.google.android.gms.internal.wallet.zzq
    public final void zza(int i, FullWallet fullWallet, Bundle bundle) throws PendingIntent.CanceledException {
        int i2;
        Activity activity = this.zzeq.get();
        if (activity == null) {
            Log.d("WalletClientImpl", "Ignoring onFullWalletLoaded, Activity has gone");
            return;
        }
        ConnectionResult connectionResult = new ConnectionResult(i, bundle != null ? (PendingIntent) bundle.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT") : null);
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(activity, this.zzag);
                return;
            } catch (IntentSender.SendIntentException e) {
                Log.w("WalletClientImpl", "Exception starting pending intent", e);
                return;
            }
        }
        Intent intent = new Intent();
        if (connectionResult.isSuccess()) {
            intent.putExtra("com.google.android.gms.wallet.EXTRA_FULL_WALLET", fullWallet);
            i2 = -1;
        } else {
            int i3 = i == 408 ? 0 : 1;
            intent.putExtra(WalletConstants.EXTRA_ERROR_CODE, i);
            i2 = i3;
        }
        PendingIntent pendingIntentCreatePendingResult = activity.createPendingResult(this.zzag, intent, 1073741824);
        if (pendingIntentCreatePendingResult == null) {
            Log.w("WalletClientImpl", "Null pending result returned for onFullWalletLoaded");
            return;
        }
        try {
            pendingIntentCreatePendingResult.send(i2);
        } catch (PendingIntent.CanceledException e2) {
            Log.w("WalletClientImpl", "Exception setting pending result", e2);
        }
    }

    @Override // com.google.android.gms.internal.wallet.zzx, com.google.android.gms.internal.wallet.zzq
    public final void zza(int i, boolean z, Bundle bundle) throws PendingIntent.CanceledException {
        Activity activity = this.zzeq.get();
        if (activity == null) {
            Log.d("WalletClientImpl", "Ignoring onPreAuthorizationDetermined, Activity has gone");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(WalletConstants.EXTRA_IS_USER_PREAUTHORIZED, z);
        PendingIntent pendingIntentCreatePendingResult = activity.createPendingResult(this.zzag, intent, 1073741824);
        if (pendingIntentCreatePendingResult == null) {
            Log.w("WalletClientImpl", "Null pending result returned for onPreAuthorizationDetermined");
            return;
        }
        try {
            pendingIntentCreatePendingResult.send(-1);
        } catch (PendingIntent.CanceledException e) {
            Log.w("WalletClientImpl", "Exception setting pending result", e);
        }
    }

    @Override // com.google.android.gms.internal.wallet.zzx, com.google.android.gms.internal.wallet.zzq
    public final void zza(int i, Bundle bundle) throws PendingIntent.CanceledException {
        Preconditions.checkNotNull(bundle, "Bundle should not be null");
        Activity activity = this.zzeq.get();
        if (activity == null) {
            Log.d("WalletClientImpl", "Ignoring onWalletObjectsCreated, Activity has gone");
            return;
        }
        ConnectionResult connectionResult = new ConnectionResult(i, (PendingIntent) bundle.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT"));
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(activity, this.zzag);
                return;
            } catch (IntentSender.SendIntentException e) {
                Log.w("WalletClientImpl", "Exception starting pending intent", e);
                return;
            }
        }
        String strValueOf = String.valueOf(connectionResult);
        Log.e("WalletClientImpl", new StringBuilder(String.valueOf(strValueOf).length() + 75).append("Create Wallet Objects confirmation UI will not be shown connection result: ").append(strValueOf).toString());
        Intent intent = new Intent();
        intent.putExtra(WalletConstants.EXTRA_ERROR_CODE, WalletConstants.ERROR_CODE_UNKNOWN);
        PendingIntent pendingIntentCreatePendingResult = activity.createPendingResult(this.zzag, intent, 1073741824);
        if (pendingIntentCreatePendingResult == null) {
            Log.w("WalletClientImpl", "Null pending result returned for onWalletObjectsCreated");
            return;
        }
        try {
            pendingIntentCreatePendingResult.send(1);
        } catch (PendingIntent.CanceledException e2) {
            Log.w("WalletClientImpl", "Exception setting pending result", e2);
        }
    }
}
