package com.thor.app.gui.fragments.shop.sgu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.thor.app.billing.BillingManager;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.UsersManager;
import com.thor.businessmodule.bluetooth.response.sgu.ReadSguSoundsResponse;
import com.thor.businessmodule.viewmodel.base.DisposableViewModel;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import com.thor.networkmodule.model.responses.sgu.SguSoundFile;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import com.thor.networkmodule.model.responses.sgu.SguSoundSetsResponse;
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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import timber.log.Timber;

/* compiled from: SguShopViewModel.kt */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ0\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0018\u0010\u0015\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u0017\u0012\u0004\u0012\u00020\u00110\u0016H\u0002J\"\u0010\u0018\u001a\u00020\u00112\u0018\u0010\u0015\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u0017\u0012\u0004\u0012\u00020\u00110\u0016H\u0002J\u0010\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0006\u0010 \u001a\u00020\u0011J0\u0010!\u001a\u00020\u00112\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00190\u00172\u0018\u0010\u0015\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\u0017\u0012\u0004\u0012\u00020\u00110\u0016H\u0002J\"\u0010$\u001a\u00020\u00112\u0018\u0010\u0015\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u0017\u0012\u0004\u0012\u00020\u00110\u0016H\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/thor/app/gui/fragments/shop/sgu/SguShopViewModel;", "Lcom/thor/businessmodule/viewmodel/base/DisposableViewModel;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "billingManager", "Lcom/thor/app/billing/BillingManager;", "bleManager", "Lcom/thor/app/managers/BleManager;", "(Lcom/thor/app/managers/UsersManager;Lcom/thor/app/billing/BillingManager;Lcom/thor/app/managers/BleManager;)V", "_shopUiState", "Landroidx/lifecycle/MutableLiveData;", "Lcom/thor/app/gui/fragments/shop/sgu/SguShopUIState;", "shopUiState", "Landroidx/lifecycle/LiveData;", "getShopUiState", "()Landroidx/lifecycle/LiveData;", "fetchInstalledSetsInfo", "", "sguSoundSets", "", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "onComplete", "Lkotlin/Function1;", "", "fetchPurchasedInfo", "", "handleError", "throwable", "", "handleResponse", "response", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSetsResponse;", "loadData", "loadSkuDetails", "skuList", "Lcom/android/billingclient/api/SkuDetails;", "loadSoundsFromDevice", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SguShopViewModel extends DisposableViewModel {
    private final MutableLiveData<SguShopUIState> _shopUiState;
    private final BillingManager billingManager;
    private final BleManager bleManager;
    private final LiveData<SguShopUIState> shopUiState;
    private final UsersManager usersManager;

    @Inject
    public SguShopViewModel(UsersManager usersManager, BillingManager billingManager, BleManager bleManager) {
        Intrinsics.checkNotNullParameter(usersManager, "usersManager");
        Intrinsics.checkNotNullParameter(billingManager, "billingManager");
        Intrinsics.checkNotNullParameter(bleManager, "bleManager");
        this.usersManager = usersManager;
        this.billingManager = billingManager;
        this.bleManager = bleManager;
        MutableLiveData<SguShopUIState> mutableLiveData = new MutableLiveData<>();
        this._shopUiState = mutableLiveData;
        this.shopUiState = mutableLiveData;
    }

    public final LiveData<SguShopUIState> getShopUiState() {
        return this.shopUiState;
    }

    public final void loadData() {
        this._shopUiState.postValue(SguShopDataLoading.INSTANCE);
        Observable<SguSoundSetsResponse> observableFetchSoundSguPackages = this.usersManager.fetchSoundSguPackages();
        if (observableFetchSoundSguPackages != null) {
            final C02911 c02911 = new C02911(this);
            Consumer<? super SguSoundSetsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopViewModel$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SguShopViewModel.loadData$lambda$0(c02911, obj);
                }
            };
            final AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
            Disposable disposableSubscribe = observableFetchSoundSguPackages.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopViewModel$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SguShopViewModel.loadData$lambda$1(anonymousClass2, obj);
                }
            });
            if (disposableSubscribe != null) {
                getDisposables().addAll(disposableSubscribe);
            }
        }
    }

    /* compiled from: SguShopViewModel.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.fragments.shop.sgu.SguShopViewModel$loadData$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C02911 extends FunctionReferenceImpl implements Function1<SguSoundSetsResponse, Unit> {
        C02911(Object obj) {
            super(1, obj, SguShopViewModel.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/sgu/SguSoundSetsResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(SguSoundSetsResponse sguSoundSetsResponse) {
            invoke2(sguSoundSetsResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(SguSoundSetsResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SguShopViewModel) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: SguShopViewModel.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.fragments.shop.sgu.SguShopViewModel$loadData$2, reason: invalid class name */
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj, SguShopViewModel.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SguShopViewModel) this.receiver).handleError(p0);
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

    private final void loadSkuDetails(List<String> skuList, final Function1<? super List<? extends SkuDetails>, Unit> onComplete) {
        this.billingManager.queryProducts(skuList, new BillingManager.OnQueryProductsListener() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopViewModel.loadSkuDetails.1
            @Override // com.thor.app.billing.BillingManager.OnQueryProductsListener
            public void onSuccess(List<? extends SkuDetails> products) {
                Intrinsics.checkNotNullParameter(products, "products");
                onComplete.invoke(products);
            }

            @Override // com.thor.app.billing.BillingManager.OnQueryProductsListener
            public void onFailure(BillingManager.Error error) {
                Intrinsics.checkNotNullParameter(error, "error");
                onComplete.invoke(CollectionsKt.emptyList());
                this._shopUiState.postValue(new SguShopError(error.toString(), true));
            }
        });
    }

    private final void loadSoundsFromDevice(final Function1<? super List<Short>, Unit> onComplete) {
        Observable<ReadSguSoundsResponse> observableExecuteReadSguSoundsCommand = this.bleManager.executeReadSguSoundsCommand();
        final Function1<ReadSguSoundsResponse, Unit> function1 = new Function1<ReadSguSoundsResponse, Unit>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopViewModel.loadSoundsFromDevice.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ReadSguSoundsResponse readSguSoundsResponse) {
                invoke2(readSguSoundsResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ReadSguSoundsResponse readSguSoundsResponse) {
                if (readSguSoundsResponse.getStatus()) {
                    onComplete.invoke(readSguSoundsResponse.getSoundIds());
                } else {
                    onComplete.invoke(CollectionsKt.emptyList());
                    this.handleError(new Exception("Can't read SGU sounds"));
                }
            }
        };
        Consumer<? super ReadSguSoundsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopViewModel$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SguShopViewModel.loadSoundsFromDevice$lambda$3(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopViewModel.loadSoundsFromDevice.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable error) {
                onComplete.invoke(CollectionsKt.emptyList());
                SguShopViewModel sguShopViewModel = this;
                Intrinsics.checkNotNullExpressionValue(error, "error");
                sguShopViewModel.handleError(error);
            }
        };
        this.bleManager.getMCompositeDisposable().add(observableExecuteReadSguSoundsCommand.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopViewModel$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SguShopViewModel.loadSoundsFromDevice$lambda$4(function12, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadSoundsFromDevice$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadSoundsFromDevice$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fetchInstalledSetsInfo(final List<SguSoundSet> sguSoundSets, final Function1<? super List<SguSoundSet>, Unit> onComplete) {
        loadSoundsFromDevice(new Function1<List<? extends Short>, Unit>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopViewModel.fetchInstalledSetsInfo.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends Short> list) {
                invoke2((List<Short>) list);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<Short> deviceSoundFileIds) {
                Intrinsics.checkNotNullParameter(deviceSoundFileIds, "deviceSoundFileIds");
                if (deviceSoundFileIds.isEmpty()) {
                    onComplete.invoke(sguSoundSets);
                    return;
                }
                for (SguSoundSet sguSoundSet : sguSoundSets) {
                    List<SguSound> files = sguSoundSet.getFiles();
                    ArrayList arrayList = new ArrayList();
                    Iterator<T> it = files.iterator();
                    while (it.hasNext()) {
                        CollectionsKt.addAll(arrayList, ((SguSound) it.next()).getSoundFiles());
                    }
                    ArrayList arrayList2 = arrayList;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                    Iterator it2 = arrayList2.iterator();
                    while (it2.hasNext()) {
                        arrayList3.add(Short.valueOf((short) ((SguSoundFile) it2.next()).getId()));
                    }
                    ArrayList arrayList4 = arrayList3;
                    System.out.println(arrayList4);
                    ArrayList arrayList5 = arrayList4;
                    System.out.println(deviceSoundFileIds.containsAll(arrayList5));
                    boolean z = true;
                    if (!(!arrayList5.isEmpty()) || !deviceSoundFileIds.containsAll(arrayList5)) {
                        z = false;
                    }
                    sguSoundSet.setLoadedOnBoard(z);
                }
                onComplete.invoke(sguSoundSets);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(SguSoundSetsResponse response) {
        if (response.isSuccessful()) {
            List<SguSoundSet> soundSetsItems = response.getSoundSetsItems();
            if (soundSetsItems == null) {
                soundSetsItems = CollectionsKt.emptyList();
            }
            final List mutableList = CollectionsKt.toMutableList((Collection) soundSetsItems);
            List list = mutableList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String iapIdentifier = ((SguSoundSet) it.next()).getIapIdentifier();
                if (iapIdentifier == null) {
                    iapIdentifier = "";
                }
                arrayList.add(iapIdentifier);
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : arrayList) {
                if (!StringsKt.isBlank((String) obj)) {
                    arrayList2.add(obj);
                }
            }
            loadSkuDetails(arrayList2, new Function1<List<? extends SkuDetails>, Unit>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopViewModel.handleResponse.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(List<? extends SkuDetails> list2) {
                    invoke2(list2);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(final List<? extends SkuDetails> skus) {
                    Intrinsics.checkNotNullParameter(skus, "skus");
                    SguShopViewModel sguShopViewModel = SguShopViewModel.this;
                    final List<SguSoundSet> list2 = mutableList;
                    final SguShopViewModel sguShopViewModel2 = SguShopViewModel.this;
                    sguShopViewModel.fetchPurchasedInfo(new Function1<List<? extends String>, Unit>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopViewModel.handleResponse.1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(List<? extends String> list3) {
                            invoke2((List<String>) list3);
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(List<String> purchasedSkus) {
                            Intrinsics.checkNotNullParameter(purchasedSkus, "purchasedSkus");
                            for (SguSoundSet sguSoundSet : list2) {
                                if (CollectionsKt.contains(purchasedSkus, sguSoundSet.getIapIdentifier())) {
                                    sguSoundSet.setSetStatus(true);
                                }
                            }
                            if (!sguShopViewModel2.bleManager.isBleEnabledAndDeviceConnected()) {
                                sguShopViewModel2._shopUiState.postValue(new SguShopDataLoaded(list2, skus));
                                return;
                            }
                            SguShopViewModel sguShopViewModel3 = sguShopViewModel2;
                            List<SguSoundSet> list3 = list2;
                            final SguShopViewModel sguShopViewModel4 = sguShopViewModel2;
                            final List<SkuDetails> list4 = skus;
                            sguShopViewModel3.fetchInstalledSetsInfo(list3, new Function1<List<? extends SguSoundSet>, Unit>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopViewModel.handleResponse.1.1.2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                /* JADX WARN: Multi-variable type inference failed */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(List<? extends SguSoundSet> list5) {
                                    invoke2((List<SguSoundSet>) list5);
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(List<SguSoundSet> sguSetsWithInstallInfo) {
                                    Intrinsics.checkNotNullParameter(sguSetsWithInstallInfo, "sguSetsWithInstallInfo");
                                    sguShopViewModel4._shopUiState.postValue(new SguShopDataLoaded(sguSetsWithInstallInfo, list4));
                                }
                            });
                        }
                    });
                }
            });
            return;
        }
        this._shopUiState.postValue(new SguShopError(response.getError(), true));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fetchPurchasedInfo(final Function1<? super List<String>, Unit> onComplete) {
        this.billingManager.querySyncedActivePurchases(new BillingManager.OnQueryActivePurchasesListener() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopViewModel.fetchPurchasedInfo.1
            @Override // com.thor.app.billing.BillingManager.OnQueryActivePurchasesListener
            public void onSuccess(List<? extends Purchase> activePurchases) {
                Intrinsics.checkNotNullParameter(activePurchases, "activePurchases");
                Function1<List<String>, Unit> function1 = onComplete;
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = activePurchases.iterator();
                while (it.hasNext()) {
                    ArrayList<String> skus = ((Purchase) it.next()).getSkus();
                    Intrinsics.checkNotNullExpressionValue(skus, "it.skus");
                    CollectionsKt.addAll(arrayList, skus);
                }
                function1.invoke(arrayList);
            }

            @Override // com.thor.app.billing.BillingManager.OnQueryActivePurchasesListener
            public void onFailure(BillingManager.Error error) {
                Intrinsics.checkNotNullParameter(error, "error");
                onComplete.invoke(CollectionsKt.emptyList());
                this.handleError(new Exception(error.toString()));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable throwable) {
        this._shopUiState.postValue(new SguShopError(throwable.getMessage(), false, 2, null));
        Timber.INSTANCE.e(throwable);
    }
}
