package com.android.billingclient.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.ResultReceiver;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes.dex */
public class ProxyBillingActivity extends Activity {
    static final String KEY_IN_APP_MESSAGE_RESULT_RECEIVER = "in_app_message_result_receiver";
    static final String KEY_PRICE_CHANGE_RESULT_RECEIVER = "result_receiver";
    private static final String KEY_SEND_CANCELLED_BROADCAST_IF_FINISHED = "send_cancelled_broadcast_if_finished";
    private static final int REQUEST_CODE_FIRST_PARTY_PURCHASE_FLOW = 110;
    private static final int REQUEST_CODE_IN_APP_MESSAGE_FLOW = 101;
    private static final int REQUEST_CODE_LAUNCH_ACTIVITY = 100;
    private static final String TAG = "ProxyBillingActivity";
    private ResultReceiver inAppMessageResultReceiver;
    private boolean isFlowFromFirstPartyClient;
    private ResultReceiver priceChangeResultReceiver;
    private boolean sendCancelledBroadcastIfFinished;

    private Intent makeAlternativeBillingIntent(String str) {
        Intent intent = new Intent("com.android.vending.billing.ALTERNATIVE_BILLING");
        intent.setPackage(getApplicationContext().getPackageName());
        intent.putExtra("ALTERNATIVE_BILLING_USER_CHOICE_DATA", str);
        return intent;
    }

    private Intent makePurchasesUpdatedIntent() {
        Intent intent = new Intent("com.android.vending.billing.PURCHASES_UPDATED");
        intent.setPackage(getApplicationContext().getPackageName());
        return intent;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007a  */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void onActivityResult(int r8, int r9, android.content.Intent r10) {
        /*
            r7 = this;
            super.onActivityResult(r8, r9, r10)
            r0 = 100
            r1 = 0
            r2 = 0
            r3 = 110(0x6e, float:1.54E-43)
            java.lang.String r4 = "ProxyBillingActivity"
            if (r8 == r0) goto L40
            if (r8 != r3) goto L10
            goto L40
        L10:
            r9 = 101(0x65, float:1.42E-43)
            if (r8 != r9) goto L28
            int r8 = com.google.android.gms.internal.play_billing.zzb.zza(r10, r4)
            android.os.ResultReceiver r9 = r7.inAppMessageResultReceiver
            if (r9 == 0) goto Lf0
            if (r10 != 0) goto L1f
            goto L23
        L1f:
            android.os.Bundle r1 = r10.getExtras()
        L23:
            r9.send(r8, r1)
            goto Lf0
        L28:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "Got onActivityResult with wrong requestCode: "
            r9.<init>(r10)
            r9.append(r8)
            java.lang.String r8 = "; skipping..."
            r9.append(r8)
            java.lang.String r8 = r9.toString()
            com.google.android.gms.internal.play_billing.zzb.zzj(r4, r8)
            goto Lf0
        L40:
            com.android.billingclient.api.BillingResult r0 = com.google.android.gms.internal.play_billing.zzb.zzd(r10, r4)
            int r0 = r0.getResponseCode()
            r5 = -1
            if (r9 != r5) goto L51
            if (r0 == 0) goto L4f
            r9 = r5
            goto L51
        L4f:
            r0 = r2
            goto L6a
        L51:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Activity finished with resultCode "
            r5.<init>(r6)
            r5.append(r9)
            java.lang.String r9 = " and billing's responseCode: "
            r5.append(r9)
            r5.append(r0)
            java.lang.String r9 = r5.toString()
            com.google.android.gms.internal.play_billing.zzb.zzj(r4, r9)
        L6a:
            android.os.ResultReceiver r9 = r7.priceChangeResultReceiver
            if (r9 == 0) goto L7a
            if (r10 != 0) goto L71
            goto L75
        L71:
            android.os.Bundle r1 = r10.getExtras()
        L75:
            r9.send(r0, r1)
            goto Lf0
        L7a:
            if (r10 == 0) goto Le1
            android.os.Bundle r9 = r10.getExtras()
            java.lang.String r0 = "LAUNCH_BILLING_FLOW"
            java.lang.String r1 = "INTENT_SOURCE"
            if (r9 == 0) goto La9
            android.os.Bundle r9 = r10.getExtras()
            java.lang.String r4 = "ALTERNATIVE_BILLING_USER_CHOICE_DATA"
            java.lang.String r9 = r9.getString(r4)
            if (r9 == 0) goto L9a
            android.content.Intent r9 = r7.makeAlternativeBillingIntent(r9)
            r9.putExtra(r1, r0)
            goto Le5
        L9a:
            android.content.Intent r9 = r7.makePurchasesUpdatedIntent()
            android.os.Bundle r10 = r10.getExtras()
            r9.putExtras(r10)
            r9.putExtra(r1, r0)
            goto Le5
        La9:
            android.content.Intent r9 = r7.makePurchasesUpdatedIntent()
            java.lang.String r10 = "Got null bundle!"
            com.google.android.gms.internal.play_billing.zzb.zzj(r4, r10)
            java.lang.String r10 = "RESPONSE_CODE"
            r4 = 6
            r9.putExtra(r10, r4)
            java.lang.String r10 = "DEBUG_MESSAGE"
            java.lang.String r5 = "An internal error occurred."
            r9.putExtra(r10, r5)
            com.android.billingclient.api.BillingResult$Builder r10 = com.android.billingclient.api.BillingResult.newBuilder()
            r10.setResponseCode(r4)
            r10.setDebugMessage(r5)
            com.android.billingclient.api.BillingResult r10 = r10.build()
            r4 = 22
            r5 = 2
            com.google.android.gms.internal.play_billing.zzfb r10 = com.android.billingclient.api.zzaq.zza(r4, r5, r10)
            byte[] r10 = r10.zzc()
            java.lang.String r4 = "FAILURE_LOGGING_PAYLOAD"
            r9.putExtra(r4, r10)
            r9.putExtra(r1, r0)
            goto Le5
        Le1:
            android.content.Intent r9 = r7.makePurchasesUpdatedIntent()
        Le5:
            if (r8 != r3) goto Led
            java.lang.String r8 = "IS_FIRST_PARTY_PURCHASE"
            r10 = 1
            r9.putExtra(r8, r10)
        Led:
            r7.sendBroadcast(r9)
        Lf0:
            r7.sendCancelledBroadcastIfFinished = r2
            r7.finish()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.ProxyBillingActivity.onActivityResult(int, int, android.content.Intent):void");
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        int i;
        PendingIntent pendingIntent;
        int i2;
        super.onCreate(bundle);
        if (bundle != null) {
            com.google.android.gms.internal.play_billing.zzb.zzi(TAG, "Launching Play Store billing flow from savedInstanceState");
            this.sendCancelledBroadcastIfFinished = bundle.getBoolean(KEY_SEND_CANCELLED_BROADCAST_IF_FINISHED, false);
            if (bundle.containsKey(KEY_PRICE_CHANGE_RESULT_RECEIVER)) {
                this.priceChangeResultReceiver = (ResultReceiver) bundle.getParcelable(KEY_PRICE_CHANGE_RESULT_RECEIVER);
            } else if (bundle.containsKey(KEY_IN_APP_MESSAGE_RESULT_RECEIVER)) {
                this.inAppMessageResultReceiver = (ResultReceiver) bundle.getParcelable(KEY_IN_APP_MESSAGE_RESULT_RECEIVER);
            }
            this.isFlowFromFirstPartyClient = bundle.getBoolean("IS_FLOW_FROM_FIRST_PARTY_CLIENT", false);
            return;
        }
        com.google.android.gms.internal.play_billing.zzb.zzi(TAG, "Launching Play Store billing flow");
        if (getIntent().hasExtra("BUY_INTENT")) {
            pendingIntent = (PendingIntent) getIntent().getParcelableExtra("BUY_INTENT");
            if (getIntent().hasExtra("IS_FLOW_FROM_FIRST_PARTY_CLIENT") && getIntent().getBooleanExtra("IS_FLOW_FROM_FIRST_PARTY_CLIENT", false)) {
                this.isFlowFromFirstPartyClient = true;
                i2 = 110;
                i = i2;
            }
            i = 100;
        } else if (getIntent().hasExtra("SUBS_MANAGEMENT_INTENT")) {
            pendingIntent = (PendingIntent) getIntent().getParcelableExtra("SUBS_MANAGEMENT_INTENT");
            this.priceChangeResultReceiver = (ResultReceiver) getIntent().getParcelableExtra(KEY_PRICE_CHANGE_RESULT_RECEIVER);
            i = 100;
        } else if (getIntent().hasExtra("IN_APP_MESSAGE_INTENT")) {
            pendingIntent = (PendingIntent) getIntent().getParcelableExtra("IN_APP_MESSAGE_INTENT");
            this.inAppMessageResultReceiver = (ResultReceiver) getIntent().getParcelableExtra(KEY_IN_APP_MESSAGE_RESULT_RECEIVER);
            i2 = 101;
            i = i2;
        } else {
            i = 100;
            pendingIntent = null;
        }
        try {
            this.sendCancelledBroadcastIfFinished = true;
            startIntentSenderForResult(pendingIntent.getIntentSender(), i, new Intent(), 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            com.google.android.gms.internal.play_billing.zzb.zzk(TAG, "Got exception while trying to start a purchase flow.", e);
            ResultReceiver resultReceiver = this.priceChangeResultReceiver;
            if (resultReceiver != null) {
                resultReceiver.send(6, null);
            } else {
                ResultReceiver resultReceiver2 = this.inAppMessageResultReceiver;
                if (resultReceiver2 != null) {
                    resultReceiver2.send(0, null);
                } else {
                    Intent intentMakePurchasesUpdatedIntent = makePurchasesUpdatedIntent();
                    if (this.isFlowFromFirstPartyClient) {
                        intentMakePurchasesUpdatedIntent.putExtra("IS_FIRST_PARTY_PURCHASE", true);
                    }
                    intentMakePurchasesUpdatedIntent.putExtra("RESPONSE_CODE", 6);
                    intentMakePurchasesUpdatedIntent.putExtra("DEBUG_MESSAGE", "An internal error occurred.");
                    sendBroadcast(intentMakePurchasesUpdatedIntent);
                }
            }
            this.sendCancelledBroadcastIfFinished = false;
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing() && this.sendCancelledBroadcastIfFinished) {
            Intent intentMakePurchasesUpdatedIntent = makePurchasesUpdatedIntent();
            intentMakePurchasesUpdatedIntent.putExtra("RESPONSE_CODE", 1);
            intentMakePurchasesUpdatedIntent.putExtra("DEBUG_MESSAGE", "Billing dialog closed.");
            sendBroadcast(intentMakePurchasesUpdatedIntent);
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        ResultReceiver resultReceiver = this.priceChangeResultReceiver;
        if (resultReceiver != null) {
            bundle.putParcelable(KEY_PRICE_CHANGE_RESULT_RECEIVER, resultReceiver);
        }
        ResultReceiver resultReceiver2 = this.inAppMessageResultReceiver;
        if (resultReceiver2 != null) {
            bundle.putParcelable(KEY_IN_APP_MESSAGE_RESULT_RECEIVER, resultReceiver2);
        }
        bundle.putBoolean(KEY_SEND_CANCELLED_BROADCAST_IF_FINISHED, this.sendCancelledBroadcastIfFinished);
        bundle.putBoolean("IS_FLOW_FROM_FIRST_PARTY_CLIENT", this.isFlowFromFirstPartyClient);
    }
}
