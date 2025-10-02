package com.thor.app.billing;

import android.app.Activity;
import android.content.Context;
import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryRecord;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import com.thor.app.billing.BillingManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Singleton;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: BillingManager.kt */
@Singleton
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001:\u0005;<=>?B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u001e\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u0018\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u001dH\u0002J\u0016\u0010\u001e\u001a\u00020\u00102\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00100 H\u0002J \u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020#2\u000e\u0010$\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010%H\u0016J\u001c\u0010&\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\n\b\u0002\u0010'\u001a\u0004\u0018\u00010(H\u0002J\"\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010)\u001a\u00020\u00192\n\b\u0002\u0010*\u001a\u0004\u0018\u00010\nJ\u000e\u0010+\u001a\u00020\u00102\u0006\u0010*\u001a\u00020,J\u0018\u0010-\u001a\u00020\u00102\u0006\u0010.\u001a\u00020(2\u0006\u0010*\u001a\u00020/H\u0002J\u001c\u00100\u001a\u00020\u00102\f\u00101\u001a\b\u0012\u0004\u0012\u00020(022\u0006\u0010*\u001a\u000203J&\u00104\u001a\u00020\u00102\f\u00101\u001a\b\u0012\u0004\u0012\u00020(022\u0006\u0010.\u001a\u00020(2\u0006\u0010*\u001a\u000205H\u0002J\u000e\u00106\u001a\u00020\u00102\u0006\u0010*\u001a\u000207J\u0018\u00108\u001a\u00020\u00102\u0006\u0010.\u001a\u00020(2\u0006\u0010*\u001a\u000209H\u0002J\u000e\u0010:\u001a\u00020\u00102\u0006\u0010*\u001a\u00020,R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006@"}, d2 = {"Lcom/thor/app/billing/BillingManager;", "Lcom/android/billingclient/api/PurchasesUpdatedListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "billingClient", "Lcom/android/billingclient/api/BillingClient;", "getContext", "()Landroid/content/Context;", "onPurchaseListener", "Lcom/thor/app/billing/BillingManager$OnPurchaseListener;", "getOnPurchaseListener", "()Lcom/thor/app/billing/BillingManager$OnPurchaseListener;", "setOnPurchaseListener", "(Lcom/thor/app/billing/BillingManager$OnPurchaseListener;)V", "acknowledgePurchase", "", FirebaseAnalytics.Event.PURCHASE, "Lcom/android/billingclient/api/Purchase;", "callback", "Lcom/android/billingclient/api/AcknowledgePurchaseResponseListener;", "changeSubscription", "activity", "Landroid/app/Activity;", "newSub", "Lcom/android/billingclient/api/SkuDetails;", "updateParams", "Lcom/android/billingclient/api/BillingFlowParams$SubscriptionUpdateParams;", "consumePurchase", "Lcom/android/billingclient/api/ConsumeResponseListener;", "onConnected", "block", "Lkotlin/Function0;", "onPurchasesUpdated", "billingResult", "Lcom/android/billingclient/api/BillingResult;", "purchaseList", "", "processPurchase", "consumableSku", "", "product", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "queryActivePurchases", "Lcom/thor/app/billing/BillingManager$OnQueryActivePurchasesListener;", "queryActivePurchasesForType", SessionDescription.ATTR_TYPE, "Lcom/android/billingclient/api/PurchasesResponseListener;", "queryProducts", "skusList", "", "Lcom/thor/app/billing/BillingManager$OnQueryProductsListener;", "queryProductsForType", "Lcom/android/billingclient/api/SkuDetailsResponseListener;", "queryPurchaseHistory", "Lcom/thor/app/billing/BillingManager$OnQueryPurchaseHistoryListener;", "queryPurchasesHistoryForType", "Lcom/android/billingclient/api/PurchaseHistoryResponseListener;", "querySyncedActivePurchases", "Error", "OnPurchaseListener", "OnQueryActivePurchasesListener", "OnQueryProductsListener", "OnQueryPurchaseHistoryListener", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class BillingManager implements PurchasesUpdatedListener {
    private final BillingClient billingClient;
    private final Context context;
    private OnPurchaseListener onPurchaseListener;

    /* compiled from: BillingManager.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0012\u0010\u0006\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&¨\u0006\t"}, d2 = {"Lcom/thor/app/billing/BillingManager$OnPurchaseListener;", "", "onPurchaseFailure", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "Lcom/thor/app/billing/BillingManager$Error;", "onPurchaseSuccess", FirebaseAnalytics.Event.PURCHASE, "Lcom/android/billingclient/api/Purchase;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPurchaseListener {
        void onPurchaseFailure(Error error);

        void onPurchaseSuccess(Purchase purchase);
    }

    /* compiled from: BillingManager.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0016\u0010\u0006\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH&¨\u0006\n"}, d2 = {"Lcom/thor/app/billing/BillingManager$OnQueryActivePurchasesListener;", "", "onFailure", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "Lcom/thor/app/billing/BillingManager$Error;", "onSuccess", "activePurchases", "", "Lcom/android/billingclient/api/Purchase;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnQueryActivePurchasesListener {
        void onFailure(Error error);

        void onSuccess(List<? extends Purchase> activePurchases);
    }

    /* compiled from: BillingManager.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0016\u0010\u0006\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH&¨\u0006\n"}, d2 = {"Lcom/thor/app/billing/BillingManager$OnQueryProductsListener;", "", "onFailure", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "Lcom/thor/app/billing/BillingManager$Error;", "onSuccess", "products", "", "Lcom/android/billingclient/api/SkuDetails;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnQueryProductsListener {
        void onFailure(Error error);

        void onSuccess(List<? extends SkuDetails> products);
    }

    /* compiled from: BillingManager.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0016\u0010\u0006\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH&¨\u0006\n"}, d2 = {"Lcom/thor/app/billing/BillingManager$OnQueryPurchaseHistoryListener;", "", "onFailure", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "Lcom/thor/app/billing/BillingManager$Error;", "onSuccess", "purchaseHistoryList", "", "Lcom/android/billingclient/api/PurchaseHistoryRecord;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnQueryPurchaseHistoryListener {
        void onFailure(Error error);

        void onSuccess(List<? extends PurchaseHistoryRecord> purchaseHistoryList);
    }

    public BillingManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        BillingClient billingClientBuild = BillingClient.newBuilder(context).enablePendingPurchases().setListener(this).build();
        Intrinsics.checkNotNullExpressionValue(billingClientBuild, "newBuilder(context)\n    …er(this)\n        .build()");
        this.billingClient = billingClientBuild;
    }

    public final Context getContext() {
        return this.context;
    }

    public final OnPurchaseListener getOnPurchaseListener() {
        return this.onPurchaseListener;
    }

    public final void setOnPurchaseListener(OnPurchaseListener onPurchaseListener) {
        this.onPurchaseListener = onPurchaseListener;
    }

    @Override // com.android.billingclient.api.PurchasesUpdatedListener
    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchaseList) {
        Intrinsics.checkNotNullParameter(billingResult, "billingResult");
        if (billingResult.getResponseCode() != 0) {
            OnPurchaseListener onPurchaseListener = this.onPurchaseListener;
            if (onPurchaseListener != null) {
                int responseCode = billingResult.getResponseCode();
                String debugMessage = billingResult.getDebugMessage();
                Intrinsics.checkNotNullExpressionValue(debugMessage, "billingResult.debugMessage");
                onPurchaseListener.onPurchaseFailure(new Error(responseCode, debugMessage));
                return;
            }
            return;
        }
        if (purchaseList == null) {
            OnPurchaseListener onPurchaseListener2 = this.onPurchaseListener;
            if (onPurchaseListener2 != null) {
                onPurchaseListener2.onPurchaseSuccess(null);
                return;
            }
            return;
        }
        Iterator<T> it = purchaseList.iterator();
        while (it.hasNext()) {
            processPurchase$default(this, (Purchase) it.next(), null, 2, null);
        }
    }

    private final void onConnected(final Function0<Unit> block) {
        this.billingClient.startConnection(new BillingClientStateListener() { // from class: com.thor.app.billing.BillingManager.onConnected.1
            @Override // com.android.billingclient.api.BillingClientStateListener
            public void onBillingServiceDisconnected() {
            }

            @Override // com.android.billingclient.api.BillingClientStateListener
            public void onBillingSetupFinished(BillingResult billingResult) {
                Intrinsics.checkNotNullParameter(billingResult, "billingResult");
                block.invoke();
            }
        });
    }

    public final void queryProducts(final List<String> skusList, final OnQueryProductsListener listener) {
        Intrinsics.checkNotNullParameter(skusList, "skusList");
        Intrinsics.checkNotNullParameter(listener, "listener");
        queryProductsForType(skusList, "subs", new SkuDetailsResponseListener() { // from class: com.thor.app.billing.BillingManager$$ExternalSyntheticLambda4
            @Override // com.android.billingclient.api.SkuDetailsResponseListener
            public final void onSkuDetailsResponse(BillingResult billingResult, List list) {
                BillingManager.queryProducts$lambda$1(this.f$0, skusList, listener, billingResult, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void queryProducts$lambda$1(BillingManager this$0, List skusList, final OnQueryProductsListener listener, BillingResult billingResult, final List list) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(skusList, "$skusList");
        Intrinsics.checkNotNullParameter(listener, "$listener");
        Intrinsics.checkNotNullParameter(billingResult, "billingResult");
        if (billingResult.getResponseCode() == 0) {
            if (list == null) {
                list = new ArrayList();
            }
            this$0.queryProductsForType(skusList, "inapp", new SkuDetailsResponseListener() { // from class: com.thor.app.billing.BillingManager$$ExternalSyntheticLambda2
                @Override // com.android.billingclient.api.SkuDetailsResponseListener
                public final void onSkuDetailsResponse(BillingResult billingResult2, List list2) {
                    BillingManager.queryProducts$lambda$1$lambda$0(list, listener, billingResult2, list2);
                }
            });
        } else {
            int responseCode = billingResult.getResponseCode();
            String debugMessage = billingResult.getDebugMessage();
            Intrinsics.checkNotNullExpressionValue(debugMessage, "billingResult.debugMessage");
            listener.onFailure(new Error(responseCode, debugMessage));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void queryProducts$lambda$1$lambda$0(List products, OnQueryProductsListener listener, BillingResult billingResult, List list) {
        Intrinsics.checkNotNullParameter(products, "$products");
        Intrinsics.checkNotNullParameter(listener, "$listener");
        Intrinsics.checkNotNullParameter(billingResult, "billingResult");
        if (billingResult.getResponseCode() == 0) {
            products.addAll(list == null ? CollectionsKt.emptyList() : list);
            listener.onSuccess(products);
        } else {
            int responseCode = billingResult.getResponseCode();
            String debugMessage = billingResult.getDebugMessage();
            Intrinsics.checkNotNullExpressionValue(debugMessage, "billingResult.debugMessage");
            listener.onFailure(new Error(responseCode, debugMessage));
        }
    }

    private final void queryProductsForType(final List<String> skusList, final String type, final SkuDetailsResponseListener listener) {
        onConnected(new Function0<Unit>() { // from class: com.thor.app.billing.BillingManager.queryProductsForType.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                BillingManager.this.billingClient.querySkuDetailsAsync(SkuDetailsParams.newBuilder().setSkusList(skusList).setType(type).build(), listener);
            }
        });
    }

    private final void queryActivePurchasesForType(final String type, final PurchasesResponseListener listener) {
        onConnected(new Function0<Unit>() { // from class: com.thor.app.billing.BillingManager.queryActivePurchasesForType.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                BillingManager.this.billingClient.queryPurchasesAsync(type, listener);
            }
        });
    }

    public final void queryActivePurchases(final OnQueryActivePurchasesListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        queryActivePurchasesForType("subs", new PurchasesResponseListener() { // from class: com.thor.app.billing.BillingManager$$ExternalSyntheticLambda0
            @Override // com.android.billingclient.api.PurchasesResponseListener
            public final void onQueryPurchasesResponse(BillingResult billingResult, List list) {
                BillingManager.queryActivePurchases$lambda$4(this.f$0, listener, billingResult, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void queryActivePurchases$lambda$4(BillingManager this$0, final OnQueryActivePurchasesListener listener, BillingResult billingResult, final List activeSubsList) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(listener, "$listener");
        Intrinsics.checkNotNullParameter(billingResult, "billingResult");
        Intrinsics.checkNotNullParameter(activeSubsList, "activeSubsList");
        if (billingResult.getResponseCode() == 0) {
            this$0.queryActivePurchasesForType("inapp", new PurchasesResponseListener() { // from class: com.thor.app.billing.BillingManager$$ExternalSyntheticLambda7
                @Override // com.android.billingclient.api.PurchasesResponseListener
                public final void onQueryPurchasesResponse(BillingResult billingResult2, List list) {
                    BillingManager.queryActivePurchases$lambda$4$lambda$3(listener, activeSubsList, billingResult2, list);
                }
            });
            return;
        }
        int responseCode = billingResult.getResponseCode();
        String debugMessage = billingResult.getDebugMessage();
        Intrinsics.checkNotNullExpressionValue(debugMessage, "billingResult.debugMessage");
        listener.onFailure(new Error(responseCode, debugMessage));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void queryActivePurchases$lambda$4$lambda$3(OnQueryActivePurchasesListener listener, List activeSubsList, BillingResult billingResult, List nonConsumableProductsList) {
        Intrinsics.checkNotNullParameter(listener, "$listener");
        Intrinsics.checkNotNullParameter(activeSubsList, "$activeSubsList");
        Intrinsics.checkNotNullParameter(billingResult, "billingResult");
        Intrinsics.checkNotNullParameter(nonConsumableProductsList, "nonConsumableProductsList");
        if (billingResult.getResponseCode() == 0) {
            activeSubsList.addAll(nonConsumableProductsList);
            listener.onSuccess(activeSubsList);
        } else {
            int responseCode = billingResult.getResponseCode();
            String debugMessage = billingResult.getDebugMessage();
            Intrinsics.checkNotNullExpressionValue(debugMessage, "billingResult.debugMessage");
            listener.onFailure(new Error(responseCode, debugMessage));
        }
    }

    private final void queryPurchasesHistoryForType(final String type, final PurchaseHistoryResponseListener listener) {
        onConnected(new Function0<Unit>() { // from class: com.thor.app.billing.BillingManager.queryPurchasesHistoryForType.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                BillingManager.this.billingClient.queryPurchaseHistoryAsync(type, listener);
            }
        });
    }

    public final void queryPurchaseHistory(final OnQueryPurchaseHistoryListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        queryPurchasesHistoryForType("subs", new PurchaseHistoryResponseListener() { // from class: com.thor.app.billing.BillingManager$$ExternalSyntheticLambda3
            @Override // com.android.billingclient.api.PurchaseHistoryResponseListener
            public final void onPurchaseHistoryResponse(BillingResult billingResult, List list) {
                BillingManager.queryPurchaseHistory$lambda$7(this.f$0, listener, billingResult, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void queryPurchaseHistory$lambda$7(BillingManager this$0, final OnQueryPurchaseHistoryListener listener, BillingResult billingResult, final List list) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(listener, "$listener");
        Intrinsics.checkNotNullParameter(billingResult, "billingResult");
        if (billingResult.getResponseCode() == 0) {
            this$0.queryPurchasesHistoryForType("inapp", new PurchaseHistoryResponseListener() { // from class: com.thor.app.billing.BillingManager$$ExternalSyntheticLambda1
                @Override // com.android.billingclient.api.PurchaseHistoryResponseListener
                public final void onPurchaseHistoryResponse(BillingResult billingResult2, List list2) {
                    BillingManager.queryPurchaseHistory$lambda$7$lambda$6(listener, list, billingResult2, list2);
                }
            });
            return;
        }
        int responseCode = billingResult.getResponseCode();
        String debugMessage = billingResult.getDebugMessage();
        Intrinsics.checkNotNullExpressionValue(debugMessage, "billingResult.debugMessage");
        listener.onFailure(new Error(responseCode, debugMessage));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void queryPurchaseHistory$lambda$7$lambda$6(OnQueryPurchaseHistoryListener listener, List list, BillingResult billingResult, List list2) {
        Intrinsics.checkNotNullParameter(listener, "$listener");
        Intrinsics.checkNotNullParameter(billingResult, "billingResult");
        if (billingResult.getResponseCode() == 0) {
            if (list != null) {
                if (list2 == null) {
                    list2 = CollectionsKt.emptyList();
                }
                list.addAll(list2);
            } else {
                list = null;
            }
            if (list == null) {
                list = CollectionsKt.emptyList();
            }
            listener.onSuccess(list);
            return;
        }
        int responseCode = billingResult.getResponseCode();
        String debugMessage = billingResult.getDebugMessage();
        Intrinsics.checkNotNullExpressionValue(debugMessage, "billingResult.debugMessage");
        listener.onFailure(new Error(responseCode, debugMessage));
    }

    public final void querySyncedActivePurchases(final OnQueryActivePurchasesListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        queryPurchaseHistory(new OnQueryPurchaseHistoryListener() { // from class: com.thor.app.billing.BillingManager.querySyncedActivePurchases.1
            @Override // com.thor.app.billing.BillingManager.OnQueryPurchaseHistoryListener
            public void onSuccess(List<? extends PurchaseHistoryRecord> purchaseHistoryList) {
                Intrinsics.checkNotNullParameter(purchaseHistoryList, "purchaseHistoryList");
                BillingManager.this.queryActivePurchases(listener);
            }

            @Override // com.thor.app.billing.BillingManager.OnQueryPurchaseHistoryListener
            public void onFailure(Error error) {
                Intrinsics.checkNotNullParameter(error, "error");
                listener.onFailure(error);
            }
        });
    }

    public static /* synthetic */ void purchase$default(BillingManager billingManager, Activity activity, SkuDetails skuDetails, OnPurchaseListener onPurchaseListener, int i, Object obj) {
        if ((i & 4) != 0) {
            onPurchaseListener = null;
        }
        billingManager.purchase(activity, skuDetails, onPurchaseListener);
    }

    public final void purchase(Activity activity, SkuDetails product, OnPurchaseListener listener) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(product, "product");
        if (listener != null) {
            this.onPurchaseListener = listener;
        }
        onConnected(new AnonymousClass2(activity, this, product));
    }

    /* compiled from: BillingManager.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.billing.BillingManager$purchase$2, reason: invalid class name */
    static final class AnonymousClass2 extends Lambda implements Function0<Unit> {
        final /* synthetic */ Activity $activity;
        final /* synthetic */ SkuDetails $product;
        final /* synthetic */ BillingManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Activity activity, BillingManager billingManager, SkuDetails skuDetails) {
            super(0);
            this.$activity = activity;
            this.this$0 = billingManager;
            this.$product = skuDetails;
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            final Activity activity = this.$activity;
            final BillingManager billingManager = this.this$0;
            final SkuDetails skuDetails = this.$product;
            activity.runOnUiThread(new Runnable() { // from class: com.thor.app.billing.BillingManager$purchase$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BillingManager.AnonymousClass2.invoke$lambda$0(billingManager, activity, skuDetails);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(BillingManager this$0, Activity activity, SkuDetails product) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(activity, "$activity");
            Intrinsics.checkNotNullParameter(product, "$product");
            this$0.billingClient.launchBillingFlow(activity, BillingFlowParams.newBuilder().setSkuDetails(product).build());
        }
    }

    private final void acknowledgePurchase(final Purchase purchase, final AcknowledgePurchaseResponseListener callback) {
        onConnected(new Function0<Unit>() { // from class: com.thor.app.billing.BillingManager.acknowledgePurchase.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                BillingClient billingClient = BillingManager.this.billingClient;
                AcknowledgePurchaseParams acknowledgePurchaseParamsBuild = AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build();
                final AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = callback;
                billingClient.acknowledgePurchase(acknowledgePurchaseParamsBuild, new AcknowledgePurchaseResponseListener() { // from class: com.thor.app.billing.BillingManager$acknowledgePurchase$1$$ExternalSyntheticLambda0
                    @Override // com.android.billingclient.api.AcknowledgePurchaseResponseListener
                    public final void onAcknowledgePurchaseResponse(BillingResult billingResult) {
                        acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(billingResult);
                    }
                });
            }
        });
    }

    /* compiled from: BillingManager.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.billing.BillingManager$consumePurchase$1, reason: invalid class name and case insensitive filesystem */
    static final class C01331 extends Lambda implements Function0<Unit> {
        final /* synthetic */ ConsumeResponseListener $callback;
        final /* synthetic */ Purchase $purchase;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01331(Purchase purchase, ConsumeResponseListener consumeResponseListener) {
            super(0);
            this.$purchase = purchase;
            this.$callback = consumeResponseListener;
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            BillingClient billingClient = BillingManager.this.billingClient;
            ConsumeParams consumeParamsBuild = ConsumeParams.newBuilder().setPurchaseToken(this.$purchase.getPurchaseToken()).build();
            final ConsumeResponseListener consumeResponseListener = this.$callback;
            billingClient.consumeAsync(consumeParamsBuild, new ConsumeResponseListener() { // from class: com.thor.app.billing.BillingManager$consumePurchase$1$$ExternalSyntheticLambda0
                @Override // com.android.billingclient.api.ConsumeResponseListener
                public final void onConsumeResponse(BillingResult billingResult, String str) {
                    BillingManager.C01331.invoke$lambda$0(consumeResponseListener, billingResult, str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(ConsumeResponseListener callback, BillingResult billingResult, String purchaseToken) {
            Intrinsics.checkNotNullParameter(callback, "$callback");
            Intrinsics.checkNotNullParameter(billingResult, "billingResult");
            Intrinsics.checkNotNullParameter(purchaseToken, "purchaseToken");
            callback.onConsumeResponse(billingResult, purchaseToken);
        }
    }

    private final void consumePurchase(Purchase purchase, ConsumeResponseListener callback) {
        onConnected(new C01331(purchase, callback));
    }

    static /* synthetic */ void processPurchase$default(BillingManager billingManager, Purchase purchase, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        billingManager.processPurchase(purchase, str);
    }

    private final void processPurchase(Purchase purchase, String consumableSku) {
        if (purchase.getPurchaseState() == 1) {
            OnPurchaseListener onPurchaseListener = this.onPurchaseListener;
            if (onPurchaseListener != null) {
                onPurchaseListener.onPurchaseSuccess(purchase);
            }
            if (consumableSku != null) {
                ArrayList<String> skus = purchase.getSkus();
                Intrinsics.checkNotNullExpressionValue(skus, "purchase.skus");
                if (Intrinsics.areEqual(CollectionsKt.firstOrNull((List) skus), consumableSku)) {
                    consumePurchase(purchase, new ConsumeResponseListener() { // from class: com.thor.app.billing.BillingManager$$ExternalSyntheticLambda5
                        @Override // com.android.billingclient.api.ConsumeResponseListener
                        public final void onConsumeResponse(BillingResult billingResult, String str) {
                            BillingManager.processPurchase$lambda$9(billingResult, str);
                        }
                    });
                    return;
                }
            }
            if (purchase.isAcknowledged()) {
                return;
            }
            acknowledgePurchase(purchase, new AcknowledgePurchaseResponseListener() { // from class: com.thor.app.billing.BillingManager$$ExternalSyntheticLambda6
                @Override // com.android.billingclient.api.AcknowledgePurchaseResponseListener
                public final void onAcknowledgePurchaseResponse(BillingResult billingResult) {
                    BillingManager.processPurchase$lambda$10(billingResult);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void processPurchase$lambda$9(BillingResult billingResult, String purchaseToken) {
        Intrinsics.checkNotNullParameter(billingResult, "billingResult");
        Intrinsics.checkNotNullParameter(purchaseToken, "purchaseToken");
        if (billingResult.getResponseCode() != 0) {
            throw new NotImplementedError("An operation is not implemented: implement retry logic or try to acknowledge again in onResume()");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void processPurchase$lambda$10(BillingResult billingResult) {
        Intrinsics.checkNotNullParameter(billingResult, "billingResult");
        if (billingResult.getResponseCode() != 0) {
            throw new NotImplementedError("An operation is not implemented: implement retry logic or try to acknowledge again in onResume()");
        }
    }

    /* compiled from: BillingManager.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.billing.BillingManager$changeSubscription$1, reason: invalid class name and case insensitive filesystem */
    static final class C01321 extends Lambda implements Function0<Unit> {
        final /* synthetic */ Activity $activity;
        final /* synthetic */ SkuDetails $newSub;
        final /* synthetic */ BillingFlowParams.SubscriptionUpdateParams $updateParams;
        final /* synthetic */ BillingManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01321(Activity activity, BillingManager billingManager, SkuDetails skuDetails, BillingFlowParams.SubscriptionUpdateParams subscriptionUpdateParams) {
            super(0);
            this.$activity = activity;
            this.this$0 = billingManager;
            this.$newSub = skuDetails;
            this.$updateParams = subscriptionUpdateParams;
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            final Activity activity = this.$activity;
            final BillingManager billingManager = this.this$0;
            final SkuDetails skuDetails = this.$newSub;
            final BillingFlowParams.SubscriptionUpdateParams subscriptionUpdateParams = this.$updateParams;
            activity.runOnUiThread(new Runnable() { // from class: com.thor.app.billing.BillingManager$changeSubscription$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BillingManager.C01321.invoke$lambda$0(billingManager, activity, skuDetails, subscriptionUpdateParams);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(BillingManager this$0, Activity activity, SkuDetails newSub, BillingFlowParams.SubscriptionUpdateParams updateParams) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(activity, "$activity");
            Intrinsics.checkNotNullParameter(newSub, "$newSub");
            Intrinsics.checkNotNullParameter(updateParams, "$updateParams");
            this$0.billingClient.launchBillingFlow(activity, BillingFlowParams.newBuilder().setSkuDetails(newSub).setSubscriptionUpdateParams(updateParams).build());
        }
    }

    public final void changeSubscription(Activity activity, SkuDetails newSub, BillingFlowParams.SubscriptionUpdateParams updateParams) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(newSub, "newSub");
        Intrinsics.checkNotNullParameter(updateParams, "updateParams");
        onConnected(new C01321(activity, this, newSub, updateParams));
    }

    /* compiled from: BillingManager.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001J\b\u0010\u0012\u001a\u00020\u0005H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lcom/thor/app/billing/BillingManager$Error;", "", "responseCode", "", "debugMessage", "", "(ILjava/lang/String;)V", "getDebugMessage", "()Ljava/lang/String;", "getResponseCode", "()I", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final /* data */ class Error {
        private final String debugMessage;
        private final int responseCode;

        public static /* synthetic */ Error copy$default(Error error, int i, String str, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = error.responseCode;
            }
            if ((i2 & 2) != 0) {
                str = error.debugMessage;
            }
            return error.copy(i, str);
        }

        /* renamed from: component1, reason: from getter */
        public final int getResponseCode() {
            return this.responseCode;
        }

        /* renamed from: component2, reason: from getter */
        public final String getDebugMessage() {
            return this.debugMessage;
        }

        public final Error copy(int responseCode, String debugMessage) {
            Intrinsics.checkNotNullParameter(debugMessage, "debugMessage");
            return new Error(responseCode, debugMessage);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Error)) {
                return false;
            }
            Error error = (Error) other;
            return this.responseCode == error.responseCode && Intrinsics.areEqual(this.debugMessage, error.debugMessage);
        }

        public int hashCode() {
            return (Integer.hashCode(this.responseCode) * 31) + this.debugMessage.hashCode();
        }

        public Error(int i, String debugMessage) {
            Intrinsics.checkNotNullParameter(debugMessage, "debugMessage");
            this.responseCode = i;
            this.debugMessage = debugMessage;
        }

        public final String getDebugMessage() {
            return this.debugMessage;
        }

        public final int getResponseCode() {
            return this.responseCode;
        }

        public String toString() {
            return "error code: " + this.responseCode + ";\nerror message: " + this.debugMessage + ";\n";
        }
    }
}
