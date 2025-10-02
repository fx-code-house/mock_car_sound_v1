package com.google.android.gms.internal.identity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes2.dex */
public final class zzf extends zzh {
    private Activity mActivity;
    private final int zzj;

    public zzf(int i, Activity activity) {
        this.zzj = i;
        this.mActivity = activity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setActivity(Activity activity) {
        this.mActivity = null;
    }

    @Override // com.google.android.gms.internal.identity.zzg
    public final void zza(int i, Bundle bundle) throws PendingIntent.CanceledException {
        String str;
        if (i == 1) {
            Intent intent = new Intent();
            intent.putExtras(bundle);
            PendingIntent pendingIntentCreatePendingResult = this.mActivity.createPendingResult(this.zzj, intent, 1073741824);
            if (pendingIntentCreatePendingResult == null) {
                return;
            }
            try {
                pendingIntentCreatePendingResult.send(1);
                return;
            } catch (PendingIntent.CanceledException e) {
                e = e;
                str = "Exception settng pending result";
            }
        } else {
            ConnectionResult connectionResult = new ConnectionResult(i, bundle != null ? (PendingIntent) bundle.getParcelable("com.google.android.gms.identity.intents.EXTRA_PENDING_INTENT") : null);
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this.mActivity, this.zzj);
                    return;
                } catch (IntentSender.SendIntentException e2) {
                    e = e2;
                    str = "Exception starting pending intent";
                }
            } else {
                try {
                    PendingIntent pendingIntentCreatePendingResult2 = this.mActivity.createPendingResult(this.zzj, new Intent(), 1073741824);
                    if (pendingIntentCreatePendingResult2 != null) {
                        pendingIntentCreatePendingResult2.send(1);
                        return;
                    }
                    return;
                } catch (PendingIntent.CanceledException e3) {
                    e = e3;
                    str = "Exception setting pending result";
                }
            }
        }
        Log.w("AddressClientImpl", str, e);
    }
}
