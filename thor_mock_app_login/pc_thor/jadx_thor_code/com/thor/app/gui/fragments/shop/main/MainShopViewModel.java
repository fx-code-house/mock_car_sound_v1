package com.thor.app.gui.fragments.shop.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.thor.app.billing.BillingManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.entity.InstalledSoundPackageEntity;
import com.thor.app.settings.Settings;
import com.thor.businessmodule.settings.Variables;
import com.thor.businessmodule.viewmodel.base.DisposableViewModel;
import com.thor.networkmodule.model.responses.ShopSoundPackagesResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: MainShopViewModel.kt */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\u000fJ\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0006\u0010\u001a\u001a\u00020\u0014J\u0016\u0010\u001b\u001a\u00020\u00142\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0002J\u000e\u0010\u001f\u001a\u00020\u00142\u0006\u0010 \u001a\u00020\rJ$\u0010!\u001a\u00020\u00142\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001d2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u001e0%H\u0002J,\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\u000e\u0010'\u001a\n\u0012\u0004\u0012\u00020(\u0018\u00010\u001d2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u001e0%H\u0002J$\u0010)\u001a\u00020\u00142\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0%2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00140+H\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/thor/app/gui/fragments/shop/main/MainShopViewModel;", "Lcom/thor/businessmodule/viewmodel/base/DisposableViewModel;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "database", "Lcom/thor/app/room/ThorDatabase;", "billingManager", "Lcom/thor/app/billing/BillingManager;", "(Lcom/thor/app/managers/UsersManager;Lcom/thor/app/room/ThorDatabase;Lcom/thor/app/billing/BillingManager;)V", "_shopUiState", "Landroidx/lifecycle/MutableLiveData;", "Lcom/thor/app/gui/fragments/shop/main/MainShopUIState;", "observablePoilingState", "", "shopUiState", "Landroidx/lifecycle/LiveData;", "getShopUiState", "()Landroidx/lifecycle/LiveData;", "getPoilingState", "handleError", "", "throwable", "", "handleResponse", "response", "Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;", "loadData", "loadSkuDetailsForShopSoundPackages", "shopSoundPackages", "", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "setPoilingActiveState", "state", "updateBoardLoadedAndUpdateStatuses", "installedSoundPackages", "Lcom/thor/app/room/entity/InstalledSoundPackageEntity;", "shopPackages", "", "updatePurchaseStatuses", "purchases", "Lcom/android/billingclient/api/Purchase;", "updateShopPackageStatuses", "onUpdated", "Lkotlin/Function0;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MainShopViewModel extends DisposableViewModel {
    private final MutableLiveData<MainShopUIState> _shopUiState;
    private final BillingManager billingManager;
    private final ThorDatabase database;
    private final MutableLiveData<Boolean> observablePoilingState;
    private final LiveData<MainShopUIState> shopUiState;
    private final UsersManager usersManager;

    @Inject
    public MainShopViewModel(UsersManager usersManager, ThorDatabase database, BillingManager billingManager) {
        Intrinsics.checkNotNullParameter(usersManager, "usersManager");
        Intrinsics.checkNotNullParameter(database, "database");
        Intrinsics.checkNotNullParameter(billingManager, "billingManager");
        this.usersManager = usersManager;
        this.database = database;
        this.billingManager = billingManager;
        MutableLiveData<MainShopUIState> mutableLiveData = new MutableLiveData<>();
        this._shopUiState = mutableLiveData;
        this.shopUiState = mutableLiveData;
        this.observablePoilingState = new MutableLiveData<>();
    }

    public final LiveData<MainShopUIState> getShopUiState() {
        return this.shopUiState;
    }

    public final void setPoilingActiveState(boolean state) {
        this.observablePoilingState.postValue(Boolean.valueOf(state));
    }

    public final LiveData<Boolean> getPoilingState() {
        return this.observablePoilingState;
    }

    public final void loadData() {
        this._shopUiState.postValue(MainShopDataLoading.INSTANCE);
        Observable<ShopSoundPackagesResponse> observableFetchShopSoundPackages = this.usersManager.fetchShopSoundPackages();
        if (observableFetchShopSoundPackages != null) {
            final C02851 c02851 = new C02851(this);
            Consumer<? super ShopSoundPackagesResponse> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopViewModel$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainShopViewModel.loadData$lambda$0(c02851, obj);
                }
            };
            final AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
            Disposable disposableSubscribe = observableFetchShopSoundPackages.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopViewModel$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainShopViewModel.loadData$lambda$1(anonymousClass2, obj);
                }
            });
            if (disposableSubscribe != null) {
                getDisposables().addAll(disposableSubscribe);
            }
        }
    }

    /* compiled from: MainShopViewModel.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.fragments.shop.main.MainShopViewModel$loadData$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C02851 extends FunctionReferenceImpl implements Function1<ShopSoundPackagesResponse, Unit> {
        C02851(Object obj) {
            super(1, obj, MainShopViewModel.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ShopSoundPackagesResponse shopSoundPackagesResponse) {
            invoke2(shopSoundPackagesResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(ShopSoundPackagesResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((MainShopViewModel) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: MainShopViewModel.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.fragments.shop.main.MainShopViewModel$loadData$2, reason: invalid class name */
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj, MainShopViewModel.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((MainShopViewModel) this.receiver).handleError(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadData$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadData$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(ShopSoundPackagesResponse response) {
        if (response.isSuccessful()) {
            List<ShopSoundPackage> soundItems = response.getSoundItems();
            if (soundItems == null) {
                soundItems = CollectionsKt.emptyList();
            }
            final List<ShopSoundPackage> mutableList = CollectionsKt.toMutableList((Collection) soundItems);
            updateShopPackageStatuses(mutableList, new Function0<Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopViewModel.handleResponse.1
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
                    MainShopViewModel.this._shopUiState.postValue(new MainShopDataLoaded(mutableList));
                    MainShopViewModel.this.loadSkuDetailsForShopSoundPackages(mutableList);
                }
            });
            return;
        }
        this._shopUiState.postValue(new MainShopError(response.getError(), true));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadSkuDetailsForShopSoundPackages(final List<ShopSoundPackage> shopSoundPackages) {
        List<ShopSoundPackage> list = shopSoundPackages;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ShopSoundPackage) it.next()).getGooglePlayId());
        }
        this.billingManager.queryProducts(arrayList, new BillingManager.OnQueryProductsListener() { // from class: com.thor.app.gui.fragments.shop.main.MainShopViewModel.loadSkuDetailsForShopSoundPackages.1
            @Override // com.thor.app.billing.BillingManager.OnQueryProductsListener
            public void onSuccess(List<? extends SkuDetails> products) {
                Intrinsics.checkNotNullParameter(products, "products");
                List<ShopSoundPackage> list2 = shopSoundPackages;
                for (SkuDetails skuDetails : products) {
                    Timber.INSTANCE.i("sku: %s", skuDetails.getSku());
                    Timber.INSTANCE.i("price: %s", skuDetails.getPrice());
                    for (ShopSoundPackage shopSoundPackage : list2) {
                        if (Intrinsics.areEqual(skuDetails.getSku(), shopSoundPackage.getGooglePlayId())) {
                            shopSoundPackage.setPrice(skuDetails.getPriceAmountMicros() / 1000000.0f);
                            String priceCurrencyCode = skuDetails.getPriceCurrencyCode();
                            Intrinsics.checkNotNullExpressionValue(priceCurrencyCode, "skuDetail.priceCurrencyCode");
                            shopSoundPackage.setCurrency(priceCurrencyCode);
                        }
                    }
                }
                MainShopViewModel.this._shopUiState.postValue(new MainShopSkuLoaded(products));
            }

            @Override // com.thor.app.billing.BillingManager.OnQueryProductsListener
            public void onFailure(BillingManager.Error error) {
                Intrinsics.checkNotNullParameter(error, "error");
                MainShopViewModel.this._shopUiState.postValue(new MainShopError(error.toString(), true));
            }
        });
    }

    private final void updateShopPackageStatuses(final List<ShopSoundPackage> shopSoundPackages, final Function0<Unit> onUpdated) {
        Observable<List<InstalledSoundPackageEntity>> observable = this.database.installedSoundPackageDao().getEntities().toObservable();
        final Function1<List<? extends InstalledSoundPackageEntity>, Unit> function1 = new Function1<List<? extends InstalledSoundPackageEntity>, Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopViewModel.updateShopPackageStatuses.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends InstalledSoundPackageEntity> list) {
                invoke2((List<InstalledSoundPackageEntity>) list);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(final List<InstalledSoundPackageEntity> list) {
                BillingManager billingManager = MainShopViewModel.this.billingManager;
                final MainShopViewModel mainShopViewModel = MainShopViewModel.this;
                final List<ShopSoundPackage> list2 = shopSoundPackages;
                final Function0<Unit> function0 = onUpdated;
                billingManager.querySyncedActivePurchases(new BillingManager.OnQueryActivePurchasesListener() { // from class: com.thor.app.gui.fragments.shop.main.MainShopViewModel.updateShopPackageStatuses.1.1
                    @Override // com.thor.app.billing.BillingManager.OnQueryActivePurchasesListener
                    public void onSuccess(List<? extends Purchase> activePurchases) {
                        Intrinsics.checkNotNullParameter(activePurchases, "activePurchases");
                        mainShopViewModel.updatePurchaseStatuses(activePurchases, list2);
                        MainShopViewModel mainShopViewModel2 = mainShopViewModel;
                        List<InstalledSoundPackageEntity> installedSoundPackages = list;
                        Intrinsics.checkNotNullExpressionValue(installedSoundPackages, "installedSoundPackages");
                        mainShopViewModel2.updateBoardLoadedAndUpdateStatuses(installedSoundPackages, list2);
                        if (Variables.INSTANCE.getSUBSCRIPTION_FEATURE_REQUIREMENTS_SATISFIED() && !Settings.INSTANCE.isSubscriptionActive()) {
                            list2.add(0, new ShopSoundPackage(0, null, 0, null, null, 0.0f, null, null, false, false, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null));
                        }
                        function0.invoke();
                    }

                    @Override // com.thor.app.billing.BillingManager.OnQueryActivePurchasesListener
                    public void onFailure(BillingManager.Error error) {
                        Intrinsics.checkNotNullParameter(error, "error");
                        mainShopViewModel._shopUiState.postValue(new MainShopError(error.getDebugMessage(), true));
                    }
                });
            }
        };
        Consumer<? super List<InstalledSoundPackageEntity>> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopViewModel$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainShopViewModel.updateShopPackageStatuses$lambda$4(function1, obj);
            }
        };
        final C02882 c02882 = new C02882(this);
        getDisposables().add(observable.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopViewModel$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainShopViewModel.updateShopPackageStatuses$lambda$5(c02882, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateShopPackageStatuses$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: MainShopViewModel.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.fragments.shop.main.MainShopViewModel$updateShopPackageStatuses$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C02882 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C02882(Object obj) {
            super(1, obj, MainShopViewModel.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((MainShopViewModel) this.receiver).handleError(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateShopPackageStatuses$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<ShopSoundPackage> updatePurchaseStatuses(List<? extends Purchase> purchases, List<ShopSoundPackage> shopPackages) {
        if (purchases != null) {
            for (Purchase purchase : purchases) {
                Timber.INSTANCE.i("Purchase: %s", purchase.toString());
                for (ShopSoundPackage shopSoundPackage : shopPackages) {
                    if (purchase.getSkus().contains(shopSoundPackage.getGooglePlayId())) {
                        shopSoundPackage.setPurchasedState();
                    }
                }
            }
        }
        return shopPackages;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateBoardLoadedAndUpdateStatuses(List<InstalledSoundPackageEntity> installedSoundPackages, List<ShopSoundPackage> shopPackages) {
        Timber.INSTANCE.i("Installed sound packages: %s", installedSoundPackages);
        if (!installedSoundPackages.isEmpty()) {
            for (ShopSoundPackage shopSoundPackage : shopPackages) {
                if (shopSoundPackage.isPurchased() || Settings.INSTANCE.isSubscriptionActive()) {
                    Iterator<InstalledSoundPackageEntity> it = installedSoundPackages.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            InstalledSoundPackageEntity next = it.next();
                            if (shopSoundPackage.getId() == next.getPackageId()) {
                                shopSoundPackage.setLoadedOnBoard(true);
                                if (next.getVersionId() < shopSoundPackage.getPkgVer()) {
                                    shopSoundPackage.setNeedUpdate(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable throwable) {
        DefaultConstructorMarker defaultConstructorMarker = null;
        int i = 2;
        boolean z = false;
        if (!Intrinsics.areEqual(throwable.getMessage(), "HTTP 400") && Intrinsics.areEqual(throwable.getMessage(), "HTTP 500")) {
            this._shopUiState.postValue(new MainShopError(throwable.getMessage(), z, i, defaultConstructorMarker));
        } else {
            this._shopUiState.postValue(new MainShopError(throwable.getMessage(), z, i, defaultConstructorMarker));
        }
        Timber.INSTANCE.e(throwable);
    }
}
