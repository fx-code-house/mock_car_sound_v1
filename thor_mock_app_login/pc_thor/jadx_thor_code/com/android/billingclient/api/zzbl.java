package com.android.billingclient.api;

import android.os.Bundle;
import com.android.billingclient.api.BillingResult;
import java.util.ArrayList;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes.dex */
final class zzbl {
    static zzbk zza(Bundle bundle, String str, String str2) {
        BillingResult billingResult = zzat.zzj;
        if (bundle == null) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", String.format("%s got null owned items list", str2));
            return new zzbk(billingResult, 54);
        }
        int iZzb = com.google.android.gms.internal.play_billing.zzb.zzb(bundle, "BillingClient");
        String strZzf = com.google.android.gms.internal.play_billing.zzb.zzf(bundle, "BillingClient");
        BillingResult.Builder builderNewBuilder = BillingResult.newBuilder();
        builderNewBuilder.setResponseCode(iZzb);
        builderNewBuilder.setDebugMessage(strZzf);
        BillingResult billingResultBuild = builderNewBuilder.build();
        if (iZzb != 0) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", String.format("%s failed. Response code: %s", str2, Integer.valueOf(iZzb)));
            return new zzbk(billingResultBuild, 23);
        }
        if (!bundle.containsKey("INAPP_PURCHASE_ITEM_LIST") || !bundle.containsKey("INAPP_PURCHASE_DATA_LIST") || !bundle.containsKey("INAPP_DATA_SIGNATURE_LIST")) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", String.format("Bundle returned from %s doesn't contain required fields.", str2));
            return new zzbk(billingResult, 55);
        }
        ArrayList<String> stringArrayList = bundle.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
        ArrayList<String> stringArrayList2 = bundle.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
        ArrayList<String> stringArrayList3 = bundle.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
        if (stringArrayList == null) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", String.format("Bundle returned from %s contains null SKUs list.", str2));
            return new zzbk(billingResult, 56);
        }
        if (stringArrayList2 == null) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", String.format("Bundle returned from %s contains null purchases list.", str2));
            return new zzbk(billingResult, 57);
        }
        if (stringArrayList3 != null) {
            return new zzbk(zzat.zzl, 1);
        }
        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", String.format("Bundle returned from %s contains null signatures list.", str2));
        return new zzbk(billingResult, 58);
    }
}
