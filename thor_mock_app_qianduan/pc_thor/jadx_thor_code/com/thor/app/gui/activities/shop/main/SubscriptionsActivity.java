package com.thor.app.gui.activities.shop.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivitySubscriptionBinding;
import com.fourksoft.base.ui.adapter.decorators.SpaceItemDecorator;
import com.fourksoft.base.ui.adapter.decorators.SpaceItemForFirstDecoration;
import com.fourksoft.base.ui.adapter.decorators.SpaceItemForLastDecoration;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.messaging.Constants;
import com.thor.app.billing.BillingManager;
import com.thor.app.databinding.model.SubscriptionPlanType;
import com.thor.app.gui.activities.shop.main.SubscriptionsActivity;
import com.thor.app.gui.adapters.shop.subscription.SoundsPresentationAdapter;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.views.subscription.SubscriptionPlanOption;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.Settings;
import com.thor.basemodule.extensions.MeasureKt;
import com.thor.basemodule.extensions.NumberKt;
import com.thor.networkmodule.model.responses.ShopSoundPackagesResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: SubscriptionsActivity.kt */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0007\u0018\u0000 .2\u00020\u0001:\u0001.B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0012H\u0002J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0016\u0010\u0017\u001a\u00020\u00122\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0002J\u0010\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u0012H\u0002J\b\u0010\u001f\u001a\u00020\u0012H\u0002J\b\u0010 \u001a\u00020\u0012H\u0002J\b\u0010!\u001a\u00020\u0012H\u0002J\b\u0010\"\u001a\u00020\u0012H\u0002J\b\u0010#\u001a\u00020\u0012H\u0002J\b\u0010$\u001a\u00020\u0012H\u0002J\u0012\u0010%\u001a\u00020\u00122\b\u0010&\u001a\u0004\u0018\u00010'H\u0014J\u0010\u0010(\u001a\u00020\u00122\u0006\u0010)\u001a\u00020*H\u0002J\b\u0010+\u001a\u00020\u0012H\u0014J\u0010\u0010,\u001a\u00020\u00122\u0006\u0010-\u001a\u00020*H\u0003R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\u000b\u001a\u00020\f8\u0006@\u0006X\u0087.¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006/"}, d2 = {"Lcom/thor/app/gui/activities/shop/main/SubscriptionsActivity;", "Lcom/thor/app/gui/activities/shop/main/SubscriptionCheckActivity;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivitySubscriptionBinding;", "packsAdapter", "Lcom/thor/app/gui/adapters/shop/subscription/SoundsPresentationAdapter;", "getPacksAdapter", "()Lcom/thor/app/gui/adapters/shop/subscription/SoundsPresentationAdapter;", "packsAdapter$delegate", "Lkotlin/Lazy;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "setUsersManager", "(Lcom/thor/app/managers/UsersManager;)V", "changeBackgroundForTheme", "", "fillAdapterData", "handleError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleProducts", "products", "", "Lcom/android/billingclient/api/SkuDetails;", "handleResponse", "response", "Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;", "init", "initAdapter", "initBottomLinks", "initListeners", "initPrices", "initSubscriptionPlanOptions", "launchLoopingRecyclerAutoscroll", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onOpenSite", "link", "", "onResume", "purchaseSubscription", "subscriptionSku", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class SubscriptionsActivity extends Hilt_SubscriptionsActivity {
    public static final String EULA_WEB_LINK_PAGE = "https://thor-tuning.com/eula/";
    public static final String PRIVACY_POLICY_WEB_LINK_PAGE = "https://thor-tuning.com/privacy_policy";
    private ActivitySubscriptionBinding binding;

    /* renamed from: packsAdapter$delegate, reason: from kotlin metadata */
    private final Lazy packsAdapter = LazyKt.lazy(new Function0<SoundsPresentationAdapter>() { // from class: com.thor.app.gui.activities.shop.main.SubscriptionsActivity$packsAdapter$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final SoundsPresentationAdapter invoke() {
            return new SoundsPresentationAdapter();
        }
    });

    @Inject
    public UsersManager usersManager;

    public final UsersManager getUsersManager() {
        UsersManager usersManager = this.usersManager;
        if (usersManager != null) {
            return usersManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("usersManager");
        return null;
    }

    public final void setUsersManager(UsersManager usersManager) {
        Intrinsics.checkNotNullParameter(usersManager, "<set-?>");
        this.usersManager = usersManager;
    }

    private final SoundsPresentationAdapter getPacksAdapter() {
        return (SoundsPresentationAdapter) this.packsAdapter.getValue();
    }

    @Override // com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySubscriptionBinding activitySubscriptionBindingInflate = ActivitySubscriptionBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activitySubscriptionBindingInflate, "inflate(layoutInflater)");
        this.binding = activitySubscriptionBindingInflate;
        if (activitySubscriptionBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBindingInflate = null;
        }
        setContentView(activitySubscriptionBindingInflate.getRoot());
        init();
    }

    private final void init() {
        initPrices();
        initAdapter();
        initListeners();
        initBottomLinks();
        initSubscriptionPlanOptions();
        changeBackgroundForTheme();
    }

    private final void initAdapter() {
        ActivitySubscriptionBinding activitySubscriptionBinding = this.binding;
        ActivitySubscriptionBinding activitySubscriptionBinding2 = null;
        if (activitySubscriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding = null;
        }
        activitySubscriptionBinding.autoscrollRecycler.setAdapter(getPacksAdapter());
        ActivitySubscriptionBinding activitySubscriptionBinding3 = this.binding;
        if (activitySubscriptionBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding3 = null;
        }
        activitySubscriptionBinding3.autoscrollRecycler.addItemDecoration(new SpaceItemDecorator(MeasureKt.getDp(4), 0, MeasureKt.getDp(4), 0, 10, null));
        ActivitySubscriptionBinding activitySubscriptionBinding4 = this.binding;
        if (activitySubscriptionBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding4 = null;
        }
        activitySubscriptionBinding4.autoscrollRecycler.addItemDecoration(new SpaceItemForFirstDecoration(MeasureKt.getDp(16), 0, 0, 0, 14, null));
        ActivitySubscriptionBinding activitySubscriptionBinding5 = this.binding;
        if (activitySubscriptionBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySubscriptionBinding2 = activitySubscriptionBinding5;
        }
        activitySubscriptionBinding2.autoscrollRecycler.addItemDecoration(new SpaceItemForLastDecoration(0, 0, MeasureKt.getDp(16), 0, 11, null));
        fillAdapterData();
    }

    private final void initListeners() {
        ActivitySubscriptionBinding activitySubscriptionBinding = this.binding;
        ActivitySubscriptionBinding activitySubscriptionBinding2 = null;
        if (activitySubscriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding = null;
        }
        activitySubscriptionBinding.actionClose.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.main.SubscriptionsActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscriptionsActivity.initListeners$lambda$0(this.f$0, view);
            }
        });
        ActivitySubscriptionBinding activitySubscriptionBinding3 = this.binding;
        if (activitySubscriptionBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding3 = null;
        }
        activitySubscriptionBinding3.actionSubscribe.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.main.SubscriptionsActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscriptionsActivity.initListeners$lambda$1(this.f$0, view);
            }
        });
        ActivitySubscriptionBinding activitySubscriptionBinding4 = this.binding;
        if (activitySubscriptionBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding4 = null;
        }
        activitySubscriptionBinding4.subscriptionOptionMonthly.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.main.SubscriptionsActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscriptionsActivity.initListeners$lambda$2(this.f$0, view);
            }
        });
        ActivitySubscriptionBinding activitySubscriptionBinding5 = this.binding;
        if (activitySubscriptionBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding5 = null;
        }
        activitySubscriptionBinding5.subscriptionOptionAnnually.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.main.SubscriptionsActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscriptionsActivity.initListeners$lambda$3(this.f$0, view);
            }
        });
        ActivitySubscriptionBinding activitySubscriptionBinding6 = this.binding;
        if (activitySubscriptionBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding6 = null;
        }
        activitySubscriptionBinding6.eulaLink.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.main.SubscriptionsActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscriptionsActivity.initListeners$lambda$4(this.f$0, view);
            }
        });
        ActivitySubscriptionBinding activitySubscriptionBinding7 = this.binding;
        if (activitySubscriptionBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySubscriptionBinding2 = activitySubscriptionBinding7;
        }
        activitySubscriptionBinding2.privacyPolicyLink.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.main.SubscriptionsActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscriptionsActivity.initListeners$lambda$5(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$0(SubscriptionsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$1(SubscriptionsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivitySubscriptionBinding activitySubscriptionBinding = this$0.binding;
        if (activitySubscriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding = null;
        }
        if (activitySubscriptionBinding.subscriptionOptionMonthly.getIsChecked()) {
            this$0.purchaseSubscription(com.thor.businessmodule.settings.Constants.THOR_PLUS_MONTHLY_SUB_ID);
        } else {
            this$0.purchaseSubscription(com.thor.businessmodule.settings.Constants.THOR_PLUS_ANNUAL_SUB_ID);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$2(SubscriptionsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivitySubscriptionBinding activitySubscriptionBinding = this$0.binding;
        ActivitySubscriptionBinding activitySubscriptionBinding2 = null;
        if (activitySubscriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding = null;
        }
        activitySubscriptionBinding.actionSubscribe.setText(this$0.getString(R.string.text_month_subscribe));
        ActivitySubscriptionBinding activitySubscriptionBinding3 = this$0.binding;
        if (activitySubscriptionBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding3 = null;
        }
        activitySubscriptionBinding3.subscriptionOptionMonthly.setChecked(true);
        ActivitySubscriptionBinding activitySubscriptionBinding4 = this$0.binding;
        if (activitySubscriptionBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySubscriptionBinding2 = activitySubscriptionBinding4;
        }
        activitySubscriptionBinding2.subscriptionOptionAnnually.setChecked(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$3(SubscriptionsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivitySubscriptionBinding activitySubscriptionBinding = this$0.binding;
        ActivitySubscriptionBinding activitySubscriptionBinding2 = null;
        if (activitySubscriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding = null;
        }
        activitySubscriptionBinding.actionSubscribe.setText(this$0.getString(R.string.text_try_subscription));
        ActivitySubscriptionBinding activitySubscriptionBinding3 = this$0.binding;
        if (activitySubscriptionBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding3 = null;
        }
        activitySubscriptionBinding3.subscriptionOptionAnnually.setChecked(true);
        ActivitySubscriptionBinding activitySubscriptionBinding4 = this$0.binding;
        if (activitySubscriptionBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySubscriptionBinding2 = activitySubscriptionBinding4;
        }
        activitySubscriptionBinding2.subscriptionOptionMonthly.setChecked(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$4(SubscriptionsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onOpenSite(EULA_WEB_LINK_PAGE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$5(SubscriptionsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onOpenSite(PRIVACY_POLICY_WEB_LINK_PAGE);
    }

    private final void initSubscriptionPlanOptions() {
        ActivitySubscriptionBinding activitySubscriptionBinding = this.binding;
        ActivitySubscriptionBinding activitySubscriptionBinding2 = null;
        if (activitySubscriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding = null;
        }
        activitySubscriptionBinding.subscriptionOptionMonthly.setSubscriptionType(SubscriptionPlanType.MONTHLY);
        ActivitySubscriptionBinding activitySubscriptionBinding3 = this.binding;
        if (activitySubscriptionBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding3 = null;
        }
        activitySubscriptionBinding3.subscriptionOptionAnnually.setSubscriptionType(SubscriptionPlanType.ANNUALLY);
        ActivitySubscriptionBinding activitySubscriptionBinding4 = this.binding;
        if (activitySubscriptionBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySubscriptionBinding2 = activitySubscriptionBinding4;
        }
        activitySubscriptionBinding2.subscriptionOptionAnnually.setChecked(true);
    }

    /* compiled from: SubscriptionsActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.shop.main.SubscriptionsActivity$fillAdapterData$1, reason: invalid class name */
    /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1<ShopSoundPackagesResponse, Unit> {
        AnonymousClass1(Object obj) {
            super(1, obj, SubscriptionsActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ShopSoundPackagesResponse shopSoundPackagesResponse) {
            invoke2(shopSoundPackagesResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(ShopSoundPackagesResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SubscriptionsActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: SubscriptionsActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.shop.main.SubscriptionsActivity$fillAdapterData$2, reason: invalid class name */
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj, SubscriptionsActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SubscriptionsActivity) this.receiver).handleError(p0);
        }
    }

    private final void fillAdapterData() {
        Observable<ShopSoundPackagesResponse> observableFetchShopSoundPackages = getUsersManager().fetchShopSoundPackages();
        if (observableFetchShopSoundPackages != null) {
            final AnonymousClass1 anonymousClass1 = new AnonymousClass1(this);
            Consumer<? super ShopSoundPackagesResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SubscriptionsActivity$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SubscriptionsActivity.fillAdapterData$lambda$6(anonymousClass1, obj);
                }
            };
            final AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
            Disposable disposableSubscribe = observableFetchShopSoundPackages.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SubscriptionsActivity$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SubscriptionsActivity.fillAdapterData$lambda$7(anonymousClass2, obj);
                }
            });
            if (disposableSubscribe != null) {
                getUsersManager().addDisposable(disposableSubscribe);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillAdapterData$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillAdapterData$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void initPrices() {
        getBillingManager().queryProducts(CollectionsKt.arrayListOf(com.thor.businessmodule.settings.Constants.THOR_PLUS_MONTHLY_SUB_ID, com.thor.businessmodule.settings.Constants.THOR_PLUS_ANNUAL_SUB_ID), new C02341());
    }

    /* compiled from: SubscriptionsActivity.kt */
    @Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0016\u0010\u0006\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016¨\u0006\n"}, d2 = {"com/thor/app/gui/activities/shop/main/SubscriptionsActivity$initPrices$1", "Lcom/thor/app/billing/BillingManager$OnQueryProductsListener;", "onFailure", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "Lcom/thor/app/billing/BillingManager$Error;", "onSuccess", "products", "", "Lcom/android/billingclient/api/SkuDetails;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.shop.main.SubscriptionsActivity$initPrices$1, reason: invalid class name and case insensitive filesystem */
    public static final class C02341 implements BillingManager.OnQueryProductsListener {
        C02341() {
        }

        @Override // com.thor.app.billing.BillingManager.OnQueryProductsListener
        public void onSuccess(final List<? extends SkuDetails> products) {
            Intrinsics.checkNotNullParameter(products, "products");
            final SubscriptionsActivity subscriptionsActivity = SubscriptionsActivity.this;
            subscriptionsActivity.runOnUiThread(new Runnable() { // from class: com.thor.app.gui.activities.shop.main.SubscriptionsActivity$initPrices$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SubscriptionsActivity.C02341.onSuccess$lambda$0(subscriptionsActivity, products);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSuccess$lambda$0(SubscriptionsActivity this$0, List products) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(products, "$products");
            this$0.handleProducts(products);
        }

        @Override // com.thor.app.billing.BillingManager.OnQueryProductsListener
        public void onFailure(BillingManager.Error error) {
            Intrinsics.checkNotNullParameter(error, "error");
            Timber.INSTANCE.e(error.toString(), new Object[0]);
        }
    }

    private final void initBottomLinks() {
        ActivitySubscriptionBinding activitySubscriptionBinding = this.binding;
        ActivitySubscriptionBinding activitySubscriptionBinding2 = null;
        if (activitySubscriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding = null;
        }
        activitySubscriptionBinding.eulaLink.setPaintFlags(8);
        ActivitySubscriptionBinding activitySubscriptionBinding3 = this.binding;
        if (activitySubscriptionBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySubscriptionBinding2 = activitySubscriptionBinding3;
        }
        activitySubscriptionBinding2.privacyPolicyLink.setPaintFlags(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleProducts(List<? extends SkuDetails> products) {
        ActivitySubscriptionBinding activitySubscriptionBinding;
        Iterator<? extends SkuDetails> it = products.iterator();
        double dRoundTo = 0.0d;
        double dRoundTo2 = 0.0d;
        String priceCurrencyCode = "";
        while (true) {
            activitySubscriptionBinding = null;
            if (!it.hasNext()) {
                break;
            }
            SkuDetails next = it.next();
            Timber.INSTANCE.i("sku: %s", next.getSku());
            Timber.INSTANCE.i("price: %s", next.getPrice());
            priceCurrencyCode = next.getPriceCurrencyCode();
            Intrinsics.checkNotNullExpressionValue(priceCurrencyCode, "skuDetail.priceCurrencyCode");
            if (Intrinsics.areEqual(next.getSku(), com.thor.businessmodule.settings.Constants.THOR_PLUS_MONTHLY_SUB_ID)) {
                ActivitySubscriptionBinding activitySubscriptionBinding2 = this.binding;
                if (activitySubscriptionBinding2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    activitySubscriptionBinding = activitySubscriptionBinding2;
                }
                SubscriptionPlanOption subscriptionPlanOption = activitySubscriptionBinding.subscriptionOptionMonthly;
                String priceCurrencyCode2 = next.getPriceCurrencyCode();
                Intrinsics.checkNotNullExpressionValue(priceCurrencyCode2, "skuDetail.priceCurrencyCode");
                subscriptionPlanOption.setPriceString(priceCurrencyCode2, NumberKt.roundTo(next.getPriceAmountMicros() / 1000000.0d, 2));
                dRoundTo = NumberKt.roundTo(next.getPriceAmountMicros() / 1000000.0d, 2);
            } else {
                ActivitySubscriptionBinding activitySubscriptionBinding3 = this.binding;
                if (activitySubscriptionBinding3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    activitySubscriptionBinding = activitySubscriptionBinding3;
                }
                SubscriptionPlanOption subscriptionPlanOption2 = activitySubscriptionBinding.subscriptionOptionAnnually;
                String priceCurrencyCode3 = next.getPriceCurrencyCode();
                Intrinsics.checkNotNullExpressionValue(priceCurrencyCode3, "skuDetail.priceCurrencyCode");
                subscriptionPlanOption2.setPriceString(priceCurrencyCode3, NumberKt.roundTo(next.getPriceAmountMicros() / 1000000.0d, 2));
                dRoundTo2 = NumberKt.roundTo(next.getPriceAmountMicros() / 1000000.0d, 2);
            }
        }
        ActivitySubscriptionBinding activitySubscriptionBinding4 = this.binding;
        if (activitySubscriptionBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySubscriptionBinding = activitySubscriptionBinding4;
        }
        activitySubscriptionBinding.subscriptionOptionAnnually.setDiscountAmount(priceCurrencyCode, dRoundTo, dRoundTo2);
    }

    private final void purchaseSubscription(String subscriptionSku) {
        Timber.INSTANCE.i("onBuyPackage", new Object[0]);
        getBillingManager().queryProducts(CollectionsKt.listOf(subscriptionSku), new BillingManager.OnQueryProductsListener() { // from class: com.thor.app.gui.activities.shop.main.SubscriptionsActivity.purchaseSubscription.1
            @Override // com.thor.app.billing.BillingManager.OnQueryProductsListener
            public void onSuccess(List<? extends SkuDetails> products) {
                Intrinsics.checkNotNullParameter(products, "products");
                BillingManager.purchase$default(SubscriptionsActivity.this.getBillingManager(), SubscriptionsActivity.this, (SkuDetails) CollectionsKt.first((List) products), null, 4, null);
            }

            @Override // com.thor.app.billing.BillingManager.OnQueryProductsListener
            public void onFailure(BillingManager.Error error) {
                Intrinsics.checkNotNullParameter(error, "error");
                SubscriptionsActivity.this.handleError(new Exception(error.toString()));
            }
        });
        getBillingManager().setOnPurchaseListener(new BillingManager.OnPurchaseListener() { // from class: com.thor.app.gui.activities.shop.main.SubscriptionsActivity.purchaseSubscription.2
            @Override // com.thor.app.billing.BillingManager.OnPurchaseListener
            public void onPurchaseSuccess(Purchase purchase) {
                ArrayList<String> skus;
                ArrayList<String> skus2;
                if ((purchase == null || (skus2 = purchase.getSkus()) == null || !skus2.isEmpty()) ? false : true) {
                    Settings.INSTANCE.saveSubscriptionActive(false);
                }
                if (purchase == null || (skus = purchase.getSkus()) == null) {
                    return;
                }
                for (String str : skus) {
                    if (str.equals(com.thor.businessmodule.settings.Constants.THOR_PLUS_MONTHLY_SUB_ID) || str.equals(com.thor.businessmodule.settings.Constants.THOR_PLUS_ANNUAL_SUB_ID)) {
                        Settings.INSTANCE.saveSubscriptionActive(true);
                    }
                }
            }

            @Override // com.thor.app.billing.BillingManager.OnPurchaseListener
            public void onPurchaseFailure(BillingManager.Error error) {
                Intrinsics.checkNotNullParameter(error, "error");
                SubscriptionsActivity.this.handleError(new Exception(error.toString()));
            }
        });
    }

    private final void launchLoopingRecyclerAutoscroll() {
        ActivitySubscriptionBinding activitySubscriptionBinding = this.binding;
        ActivitySubscriptionBinding activitySubscriptionBinding2 = null;
        if (activitySubscriptionBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding = null;
        }
        activitySubscriptionBinding.autoscrollRecycler.setCanTouch(true);
        ActivitySubscriptionBinding activitySubscriptionBinding3 = this.binding;
        if (activitySubscriptionBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySubscriptionBinding3 = null;
        }
        activitySubscriptionBinding3.autoscrollRecycler.setLoopEnabled(true);
        ActivitySubscriptionBinding activitySubscriptionBinding4 = this.binding;
        if (activitySubscriptionBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySubscriptionBinding2 = activitySubscriptionBinding4;
        }
        activitySubscriptionBinding2.autoscrollRecycler.startAutoScroll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(ShopSoundPackagesResponse response) {
        if (response.isSuccessful()) {
            List<ShopSoundPackage> soundItems = response.getSoundItems();
            if (soundItems == null) {
                soundItems = CollectionsKt.emptyList();
            }
            List mutableList = CollectionsKt.toMutableList((Collection) soundItems);
            SoundsPresentationAdapter packsAdapter = getPacksAdapter();
            List list = mutableList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((ShopSoundPackage) it.next()).getPkgImageUrl());
            }
            packsAdapter.clearAndAddAll(arrayList);
            launchLoopingRecyclerAutoscroll();
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(this, response.getError(), Integer.valueOf(response.getCode()));
        if (alertDialogCreateErrorAlertDialogWithSendLogOption != null) {
            alertDialogCreateErrorAlertDialogWithSendLogOption.show();
        }
        handleError(new Exception(response.getError()));
    }

    private final void onOpenSite(String link) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(link));
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable error) {
        AlertDialog alertDialogCreateErrorAlertDialog$default;
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400")) {
            AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, error.getMessage(), null, 4, null);
            if (alertDialogCreateErrorAlertDialog$default2 != null) {
                alertDialogCreateErrorAlertDialog$default2.show();
            }
        } else if (Intrinsics.areEqual(error.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, error.getMessage(), null, 4, null)) != null) {
            alertDialogCreateErrorAlertDialog$default.show();
        }
        Timber.INSTANCE.e(error);
        FirebaseCrashlytics.getInstance().recordException(error);
    }

    private final void changeBackgroundForTheme() {
        int carFuelType = Settings.INSTANCE.getCarFuelType();
        ActivitySubscriptionBinding activitySubscriptionBinding = null;
        if (carFuelType == 1) {
            ActivitySubscriptionBinding activitySubscriptionBinding2 = this.binding;
            if (activitySubscriptionBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySubscriptionBinding = activitySubscriptionBinding2;
            }
            activitySubscriptionBinding.scrollable.setBackgroundResource(R.drawable.subscription_blue_background);
            return;
        }
        if (carFuelType == 2) {
            ActivitySubscriptionBinding activitySubscriptionBinding3 = this.binding;
            if (activitySubscriptionBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activitySubscriptionBinding = activitySubscriptionBinding3;
            }
            activitySubscriptionBinding.scrollable.setBackgroundResource(R.drawable.subscription_background);
            return;
        }
        if (carFuelType != 3) {
            return;
        }
        ActivitySubscriptionBinding activitySubscriptionBinding4 = this.binding;
        if (activitySubscriptionBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySubscriptionBinding = activitySubscriptionBinding4;
        }
        activitySubscriptionBinding.scrollable.setBackgroundResource(R.drawable.subscription_green_background);
    }

    @Override // com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getPacksAdapter().notifyDataSetChanged();
        if (Settings.INSTANCE.isSubscriptionActive()) {
            finish();
        }
    }
}
