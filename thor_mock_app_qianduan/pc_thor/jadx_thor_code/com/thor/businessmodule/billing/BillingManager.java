package com.thor.businessmodule.billing;

import android.app.Activity;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.Arrays;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import timber.log.Timber;

/* compiled from: BillingManager.kt */
@Deprecated(message = "use billing manager in app module")
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001!B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0002J(\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000f2\u0006\u0010\u0013\u001a\u00020\u0012J \u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u000e\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0019H\u0016J\"\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u000f0\u000e2\u0006\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\nJ\u0012\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u000f0\u000eJ\b\u0010\u001f\u001a\u00020 H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/thor/businessmodule/billing/BillingManager;", "Lcom/android/billingclient/api/PurchasesUpdatedListener;", "activity", "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "getActivity", "()Landroid/app/Activity;", "billingClient", "Lcom/android/billingclient/api/BillingClient;", "mPurchaseBillingListener", "Lcom/thor/businessmodule/billing/BillingManager$PurchaseBillingListener;", "areSubscriptionsSupported", "", "getSkuDetailsList", "Lio/reactivex/Single;", "", "Lcom/android/billingclient/api/SkuDetails;", "skuList", "", "skuType", "onPurchasesUpdated", "", "billingResult", "Lcom/android/billingclient/api/BillingResult;", "purchases", "", "Lcom/android/billingclient/api/Purchase;", FirebaseAnalytics.Event.PURCHASE, "skuDetails", "purchaseBillingListener", "queryPurchases", "tryConnect", "Lio/reactivex/Completable;", "PurchaseBillingListener", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BillingManager implements PurchasesUpdatedListener {
    private final Activity activity;
    private BillingClient billingClient;
    private PurchaseBillingListener mPurchaseBillingListener;

    /* compiled from: BillingManager.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0017\u0010\u0004\u001a\u00020\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&¨\u0006\u000b"}, d2 = {"Lcom/thor/businessmodule/billing/BillingManager$PurchaseBillingListener;", "", "handleCancelled", "", "handleError", "errorCode", "", "(Ljava/lang/Integer;)V", "handlePurchase", FirebaseAnalytics.Event.PURCHASE, "Lcom/android/billingclient/api/Purchase;", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface PurchaseBillingListener {
        void handleCancelled();

        void handleError(Integer errorCode);

        void handlePurchase(Purchase purchase);
    }

    public BillingManager(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.activity = activity;
        BillingClient billingClientBuild = BillingClient.newBuilder(activity).enablePendingPurchases().setListener(this).build();
        Intrinsics.checkNotNullExpressionValue(billingClientBuild, "newBuilder(activity)\n   …his)\n            .build()");
        this.billingClient = billingClientBuild;
    }

    public final Activity getActivity() {
        return this.activity;
    }

    @Override // com.android.billingclient.api.PurchasesUpdatedListener
    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
        Intrinsics.checkNotNullParameter(billingResult, "billingResult");
        if (billingResult.getResponseCode() == 0 && purchases != null) {
            for (Purchase purchase : purchases) {
                PurchaseBillingListener purchaseBillingListener = this.mPurchaseBillingListener;
                if (purchaseBillingListener != null) {
                    purchaseBillingListener.handlePurchase(purchase);
                }
            }
            return;
        }
        if (billingResult.getResponseCode() == 1) {
            PurchaseBillingListener purchaseBillingListener2 = this.mPurchaseBillingListener;
            if (purchaseBillingListener2 != null) {
                purchaseBillingListener2.handleCancelled();
                return;
            }
            return;
        }
        PurchaseBillingListener purchaseBillingListener3 = this.mPurchaseBillingListener;
        if (purchaseBillingListener3 != null) {
            purchaseBillingListener3.handleError(Integer.valueOf(billingResult.getResponseCode()));
        }
    }

    public final Single<List<Purchase>> queryPurchases() {
        Single<List<Purchase>> singleCreate = Single.create(new SingleOnSubscribe() { // from class: com.thor.businessmodule.billing.BillingManager$$ExternalSyntheticLambda4
            @Override // io.reactivex.SingleOnSubscribe
            public final void subscribe(SingleEmitter singleEmitter) {
                Intrinsics.checkNotNullParameter(singleEmitter, "emitter");
            }
        });
        Intrinsics.checkNotNullExpressionValue(singleCreate, "create { emitter ->\n//  …//            )\n        }");
        return singleCreate;
    }

    public final Single<List<Purchase>> purchase(final SkuDetails skuDetails, PurchaseBillingListener purchaseBillingListener) {
        Intrinsics.checkNotNullParameter(skuDetails, "skuDetails");
        Intrinsics.checkNotNullParameter(purchaseBillingListener, "purchaseBillingListener");
        this.mPurchaseBillingListener = purchaseBillingListener;
        Single<List<Purchase>> singleCreate = Single.create(new SingleOnSubscribe() { // from class: com.thor.businessmodule.billing.BillingManager$$ExternalSyntheticLambda5
            @Override // io.reactivex.SingleOnSubscribe
            public final void subscribe(SingleEmitter singleEmitter) {
                BillingManager.purchase$lambda$3(this.f$0, skuDetails, singleEmitter);
            }
        });
        Intrinsics.checkNotNullExpressionValue(singleCreate, "create { emitter ->\n    …r\n            )\n        }");
        return singleCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void purchase$lambda$3(final BillingManager this$0, final SkuDetails skuDetails, SingleEmitter emitter) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(skuDetails, "$skuDetails");
        Intrinsics.checkNotNullParameter(emitter, "emitter");
        Completable completableTryConnect = this$0.tryConnect();
        Action action = new Action() { // from class: com.thor.businessmodule.billing.BillingManager$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Action
            public final void run() {
                BillingManager.purchase$lambda$3$lambda$1(skuDetails, this$0);
            }
        };
        final BillingManager$purchase$1$2 billingManager$purchase$1$2 = new BillingManager$purchase$1$2(emitter);
        completableTryConnect.subscribe(action, new Consumer() { // from class: com.thor.businessmodule.billing.BillingManager$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BillingManager.purchase$lambda$3$lambda$2(billingManager$purchase$1$2, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void purchase$lambda$3$lambda$1(SkuDetails skuDetails, BillingManager this$0) {
        Intrinsics.checkNotNullParameter(skuDetails, "$skuDetails");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Timber.i(FirebaseAnalytics.Event.PURCHASE, new Object[0]);
        BillingFlowParams billingFlowParamsBuild = BillingFlowParams.newBuilder().setSkuDetails(skuDetails).build();
        Intrinsics.checkNotNullExpressionValue(billingFlowParamsBuild, "newBuilder()\n           …                 .build()");
        this$0.billingClient.launchBillingFlow(this$0.activity, billingFlowParamsBuild);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void purchase$lambda$3$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final Single<List<SkuDetails>> getSkuDetailsList(final List<String> skuList, final String skuType) {
        Intrinsics.checkNotNullParameter(skuList, "skuList");
        Intrinsics.checkNotNullParameter(skuType, "skuType");
        Single<List<SkuDetails>> singleCreate = Single.create(new SingleOnSubscribe() { // from class: com.thor.businessmodule.billing.BillingManager$$ExternalSyntheticLambda2
            @Override // io.reactivex.SingleOnSubscribe
            public final void subscribe(SingleEmitter singleEmitter) {
                BillingManager.getSkuDetailsList$lambda$7(this.f$0, skuList, skuType, singleEmitter);
            }
        });
        Intrinsics.checkNotNullExpressionValue(singleCreate, "create { emitter ->\n    …}\n            )\n        }");
        return singleCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getSkuDetailsList$lambda$7(final BillingManager this$0, final List skuList, final String skuType, final SingleEmitter emitter) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(skuList, "$skuList");
        Intrinsics.checkNotNullParameter(skuType, "$skuType");
        Intrinsics.checkNotNullParameter(emitter, "emitter");
        Completable completableTryConnect = this$0.tryConnect();
        Action action = new Action() { // from class: com.thor.businessmodule.billing.BillingManager$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Action
            public final void run() {
                BillingManager.getSkuDetailsList$lambda$7$lambda$5(skuList, skuType, this$0, emitter);
            }
        };
        final Function1<Throwable, Unit> function1 = new Function1<Throwable, Unit>() { // from class: com.thor.businessmodule.billing.BillingManager$getSkuDetailsList$1$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                emitter.onError(th);
            }
        };
        completableTryConnect.subscribe(action, new Consumer() { // from class: com.thor.businessmodule.billing.BillingManager$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BillingManager.getSkuDetailsList$lambda$7$lambda$6(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getSkuDetailsList$lambda$7$lambda$5(List skuList, String skuType, BillingManager this$0, final SingleEmitter emitter) {
        Intrinsics.checkNotNullParameter(skuList, "$skuList");
        Intrinsics.checkNotNullParameter(skuType, "$skuType");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(emitter, "$emitter");
        SkuDetailsParams skuDetailsParamsBuild = SkuDetailsParams.newBuilder().setSkusList(skuList).setType(skuType).build();
        Intrinsics.checkNotNullExpressionValue(skuDetailsParamsBuild, "newBuilder()\n           …                 .build()");
        this$0.billingClient.querySkuDetailsAsync(skuDetailsParamsBuild, new SkuDetailsResponseListener() { // from class: com.thor.businessmodule.billing.BillingManager$$ExternalSyntheticLambda6
            @Override // com.android.billingclient.api.SkuDetailsResponseListener
            public final void onSkuDetailsResponse(BillingResult billingResult, List list) {
                BillingManager.getSkuDetailsList$lambda$7$lambda$5$lambda$4(emitter, billingResult, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getSkuDetailsList$lambda$7$lambda$5$lambda$4(SingleEmitter emitter, BillingResult billingResult, List list) {
        Intrinsics.checkNotNullParameter(emitter, "$emitter");
        Intrinsics.checkNotNullParameter(billingResult, "billingResult");
        if (billingResult.getResponseCode() == 0) {
            List list2 = list;
            if (!(list2 == null || list2.isEmpty())) {
                emitter.onSuccess(list);
                return;
            }
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("SkuDetails Failure with code %d", Arrays.copyOf(new Object[]{Integer.valueOf(billingResult.getResponseCode())}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(format, *args)");
        emitter.onError(new Throwable(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getSkuDetailsList$lambda$7$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final Completable tryConnect() {
        Completable completableCreate = Completable.create(new CompletableOnSubscribe() { // from class: com.thor.businessmodule.billing.BillingManager$$ExternalSyntheticLambda3
            @Override // io.reactivex.CompletableOnSubscribe
            public final void subscribe(CompletableEmitter completableEmitter) {
                BillingManager.tryConnect$lambda$8(this.f$0, completableEmitter);
            }
        });
        Intrinsics.checkNotNullExpressionValue(completableCreate, "create { emitter ->\n    …)\n            }\n        }");
        return completableCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tryConnect$lambda$8(BillingManager this$0, final CompletableEmitter emitter) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(emitter, "emitter");
        if (this$0.billingClient.isReady()) {
            emitter.onComplete();
        } else {
            this$0.billingClient.startConnection(new BillingClientStateListener() { // from class: com.thor.businessmodule.billing.BillingManager$tryConnect$1$1
                @Override // com.android.billingclient.api.BillingClientStateListener
                public void onBillingSetupFinished(BillingResult billingResult) {
                    Intrinsics.checkNotNullParameter(billingResult, "billingResult");
                    if (billingResult.getResponseCode() == 0) {
                        emitter.onComplete();
                        return;
                    }
                    emitter.onError(new Throwable("billingClient: " + billingResult.getResponseCode()));
                }

                @Override // com.android.billingclient.api.BillingClientStateListener
                public void onBillingServiceDisconnected() {
                    Timber.d("onBillingServiceDisconnected", new Object[0]);
                }
            });
        }
    }

    private final boolean areSubscriptionsSupported() {
        BillingResult billingResultIsFeatureSupported = this.billingClient.isFeatureSupported(BillingClient.FeatureType.SUBSCRIPTIONS);
        Intrinsics.checkNotNullExpressionValue(billingResultIsFeatureSupported, "billingClient.isFeatureS…eatureType.SUBSCRIPTIONS)");
        return billingResultIsFeatureSupported.getResponseCode() == 0;
    }
}
