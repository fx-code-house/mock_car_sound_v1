package com.thor.app.gui.activities.shop.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityShopOldBinding;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.firebase.messaging.Constants;
import com.thor.app.bus.events.BluetoothDeviceConnectionChangedEvent;
import com.thor.app.bus.events.BluetoothDeviceRssiEvent;
import com.thor.app.bus.events.PurchaseBillingSuccessEvent;
import com.thor.app.bus.events.shop.main.DeleteSoundPackageEvent;
import com.thor.app.bus.events.shop.main.DeletedSoundPackageEvent;
import com.thor.app.bus.events.shop.main.UploadSoundPackageInterruptedEvent;
import com.thor.app.bus.events.shop.main.UploadSoundPackageSuccessfulEvent;
import com.thor.app.databinding.adapters.ShopSoundPackageDataBindingAdapter;
import com.thor.app.databinding.model.RunningAppOnPhoneStatus;
import com.thor.app.gui.activities.EmergencySituationBaseActivity;
import com.thor.app.gui.adapters.shop.main.ShopSoundPackagesDiffUtilCallback;
import com.thor.app.gui.adapters.shop.main.ShopSoundPackagesRvAdapter;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.entity.InstalledSoundPackageEntity;
import com.thor.app.settings.Settings;
import com.thor.app.utils.extensions.ContextKt;
import com.thor.app.utils.security.DeviceLockingUtilsKt;
import com.thor.businessmodule.billing.BillingManager;
import com.thor.businessmodule.settings.Variables;
import com.thor.networkmodule.model.ModeType;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.ShopSoundPackagesResponse;
import com.thor.networkmodule.model.responses.SignInResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import com.thor.networkmodule.network.OnLoadDataListener;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: ShopActivityOld.kt */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 32\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u00013B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0003H\u0016J\b\u0010\u0017\u001a\u00020\u0010H\u0002J\u0012\u0010\u0018\u001a\u00020\u00102\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\b\u0010\u001b\u001a\u00020\u0010H\u0016J\u0010\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\u0010\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u001fH\u0007J\u0010\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020 H\u0007J\u0010\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020!H\u0007J\u0010\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\"H\u0007J\u0010\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020#H\u0007J\u0010\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020$H\u0007J\b\u0010%\u001a\u00020\u0010H\u0014J\b\u0010&\u001a\u00020\u0010H\u0014J\b\u0010'\u001a\u00020\u0010H\u0002J\u0016\u0010(\u001a\u00020\u00102\f\u0010)\u001a\b\u0012\u0004\u0012\u00020+0*H\u0003J$\u0010,\u001a\u00020\u00102\f\u0010-\u001a\b\u0012\u0004\u0012\u00020.0*2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020+0*H\u0002J&\u00100\u001a\u00020\u00102\u000e\u00101\u001a\n\u0012\u0004\u0012\u000202\u0018\u00010*2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020+0*H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u00064"}, d2 = {"Lcom/thor/app/gui/activities/shop/main/ShopActivityOld;", "Lcom/thor/app/gui/activities/EmergencySituationBaseActivity;", "Lcom/thor/networkmodule/network/OnLoadDataListener;", "Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityShopOldBinding;", "mAdapter", "Lcom/thor/app/gui/adapters/shop/main/ShopSoundPackagesRvAdapter;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "Lkotlin/Lazy;", "handleError", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleReAuth", "response", "Lcom/thor/networkmodule/model/responses/SignInResponse;", "handleResponse", "initAdapter", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onLoadData", "onMessageEvent", "event", "Lcom/thor/app/bus/events/BluetoothDeviceConnectionChangedEvent;", "Lcom/thor/app/bus/events/BluetoothDeviceRssiEvent;", "Lcom/thor/app/bus/events/PurchaseBillingSuccessEvent;", "Lcom/thor/app/bus/events/shop/main/DeleteSoundPackageEvent;", "Lcom/thor/app/bus/events/shop/main/DeletedSoundPackageEvent;", "Lcom/thor/app/bus/events/shop/main/UploadSoundPackageInterruptedEvent;", "Lcom/thor/app/bus/events/shop/main/UploadSoundPackageSuccessfulEvent;", "onResume", "onStart", "reAuth", "setPriceFromSkuDetailList", "shopSoundPackages", "", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "updateBoardLoadedAndUpdateStatuses", "installedSoundPackages", "Lcom/thor/app/room/entity/InstalledSoundPackageEntity;", "shopPackages", "updatePurchaseStatuses", "purchases", "Lcom/android/billingclient/api/Purchase;", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(message = "")
/* loaded from: classes3.dex */
public final class ShopActivityOld extends EmergencySituationBaseActivity implements OnLoadDataListener<ShopSoundPackagesResponse> {
    private static boolean isNeedSendInterruptedSoundEvent;
    private static int stableSignalCounter;
    private ActivityShopOldBinding binding;
    private ShopSoundPackagesRvAdapter mAdapter;

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    private final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws Exception {
        super.onCreate(savedInstanceState);
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, R.layout.activity_shop_old);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.layout.activity_shop_old)");
        ActivityShopOldBinding activityShopOldBinding = (ActivityShopOldBinding) contentView;
        this.binding = activityShopOldBinding;
        ActivityShopOldBinding activityShopOldBinding2 = null;
        if (activityShopOldBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopOldBinding = null;
        }
        activityShopOldBinding.toolbarWidget.setHomeButtonVisibility(true);
        ActivityShopOldBinding activityShopOldBinding3 = this.binding;
        if (activityShopOldBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopOldBinding3 = null;
        }
        activityShopOldBinding3.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ShopActivityOld.onCreate$lambda$0(this.f$0, view);
            }
        });
        ActivityShopOldBinding activityShopOldBinding4 = this.binding;
        if (activityShopOldBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopOldBinding4 = null;
        }
        activityShopOldBinding4.toolbarWidget.enableBluetoothIndicator(true);
        ActivityShopOldBinding activityShopOldBinding5 = this.binding;
        if (activityShopOldBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopOldBinding5 = null;
        }
        activityShopOldBinding5.swipeContainer.setColorSchemeColors(ContextKt.fetchAttrValue(this, R.attr.colorAccent));
        ActivityShopOldBinding activityShopOldBinding6 = this.binding;
        if (activityShopOldBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopOldBinding6 = null;
        }
        activityShopOldBinding6.swipeContainer.setProgressBackgroundColorSchemeResource(R.color.colorRefreshLayoutBackground);
        ActivityShopOldBinding activityShopOldBinding7 = this.binding;
        if (activityShopOldBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityShopOldBinding2 = activityShopOldBinding7;
        }
        activityShopOldBinding2.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda17
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                ShopActivityOld.onCreate$lambda$1(this.f$0);
            }
        });
        initAdapter();
        initInternetConnectionListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(ShopActivityOld this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(ShopActivityOld this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onLoadData();
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        DataLayerManager.INSTANCE.from(this).sendIsRunningAppOnPhone(RunningAppOnPhoneStatus.OFF);
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() throws SecurityException {
        super.onStart();
        reAuth();
    }

    private final void initAdapter() {
        this.mAdapter = new ShopSoundPackagesRvAdapter(new ShopSoundPackagesDiffUtilCallback());
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$initAdapter$animator$1
            @Override // androidx.recyclerview.widget.SimpleItemAnimator, androidx.recyclerview.widget.RecyclerView.ItemAnimator
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
                return true;
            }
        };
        ActivityShopOldBinding activityShopOldBinding = this.binding;
        ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter = null;
        if (activityShopOldBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopOldBinding = null;
        }
        activityShopOldBinding.recyclerView.setItemAnimator(defaultItemAnimator);
        ActivityShopOldBinding activityShopOldBinding2 = this.binding;
        if (activityShopOldBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopOldBinding2 = null;
        }
        RecyclerView recyclerView = activityShopOldBinding2.recyclerView;
        ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter2 = this.mAdapter;
        if (shopSoundPackagesRvAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            shopSoundPackagesRvAdapter2 = null;
        }
        recyclerView.setAdapter(shopSoundPackagesRvAdapter2);
        ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter3 = this.mAdapter;
        if (shopSoundPackagesRvAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            shopSoundPackagesRvAdapter3 = null;
        }
        ActivityShopOldBinding activityShopOldBinding3 = this.binding;
        if (activityShopOldBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopOldBinding3 = null;
        }
        shopSoundPackagesRvAdapter3.setRecyclerView(activityShopOldBinding3.recyclerView);
        ActivityShopOldBinding activityShopOldBinding4 = this.binding;
        if (activityShopOldBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopOldBinding4 = null;
        }
        NestedScrollView nestedScrollView = activityShopOldBinding4.nestedScrollView;
        ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter4 = this.mAdapter;
        if (shopSoundPackagesRvAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            shopSoundPackagesRvAdapter = shopSoundPackagesRvAdapter4;
        }
        nestedScrollView.setOnScrollChangeListener(shopSoundPackagesRvAdapter.getOnNestedScrollListener());
    }

    private final void reAuth() {
        ActivityShopOldBinding activityShopOldBinding = this.binding;
        if (activityShopOldBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopOldBinding = null;
        }
        activityShopOldBinding.swipeContainer.setRefreshing(true);
        onLoadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadData$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Override // com.thor.networkmodule.network.OnLoadDataListener
    public void onLoadData() {
        Observable<ShopSoundPackagesResponse> observableDoOnTerminate;
        Observable<ShopSoundPackagesResponse> observableFetchShopSoundPackages = getUsersManager().fetchShopSoundPackages();
        if (observableFetchShopSoundPackages != null) {
            final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$onLoadData$disposable$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                    invoke2(disposable);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Disposable disposable) {
                    ActivityShopOldBinding activityShopOldBinding = this.this$0.binding;
                    if (activityShopOldBinding == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        activityShopOldBinding = null;
                    }
                    activityShopOldBinding.swipeContainer.setRefreshing(true);
                }
            };
            Observable<ShopSoundPackagesResponse> observableDoOnSubscribe = observableFetchShopSoundPackages.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ShopActivityOld.onLoadData$lambda$2(function1, obj);
                }
            });
            if (observableDoOnSubscribe == null || (observableDoOnTerminate = observableDoOnSubscribe.doOnTerminate(new Action() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ShopActivityOld.onLoadData$lambda$3(this.f$0);
                }
            })) == null) {
                return;
            }
            final ShopActivityOld$onLoadData$disposable$3 shopActivityOld$onLoadData$disposable$3 = new ShopActivityOld$onLoadData$disposable$3(this);
            Consumer<? super ShopSoundPackagesResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ShopActivityOld.onLoadData$lambda$4(shopActivityOld$onLoadData$disposable$3, obj);
                }
            };
            final ShopActivityOld$onLoadData$disposable$4 shopActivityOld$onLoadData$disposable$4 = new ShopActivityOld$onLoadData$disposable$4(this);
            observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ShopActivityOld.onLoadData$lambda$5(shopActivityOld$onLoadData$disposable$4, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadData$lambda$3(ShopActivityOld this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityShopOldBinding activityShopOldBinding = this$0.binding;
        if (activityShopOldBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopOldBinding = null;
        }
        activityShopOldBinding.swipeContainer.setRefreshing(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadData$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadData$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void handleReAuth(SignInResponse response) {
        if (response.isSuccessful()) {
            if (response.getUserId() == 0 || TextUtils.isEmpty(response.getToken())) {
                return;
            }
            Settings.saveUserId(response.getUserId());
            String token = response.getToken();
            Intrinsics.checkNotNull(token);
            Settings.saveAccessToken(token);
            Settings.saveProfile(response);
            onLoadData();
            return;
        }
        if (response.getCode() == 888) {
            DeviceLockingUtilsKt.onDeviceLocked(this);
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, response.getError(), response.getCode());
        if (alertDialogCreateErrorAlertDialog != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
    }

    @Override // com.thor.networkmodule.network.OnLoadDataListener
    public void handleResponse(ShopSoundPackagesResponse response) {
        Observable observableSubscribeOn;
        Observable observableObserveOn;
        Intrinsics.checkNotNullParameter(response, "response");
        if (response.isSuccessful()) {
            List<ShopSoundPackage> soundItems = response.getSoundItems();
            if (soundItems == null) {
                soundItems = CollectionsKt.emptyList();
            }
            final List<ShopSoundPackage> mutableList = CollectionsKt.toMutableList((Collection) soundItems);
            setPriceFromSkuDetailList(mutableList);
            Observable observableZip = Observable.zip(new BillingManager(this).queryPurchases().toObservable(), ThorDatabase.INSTANCE.from(this).installedSoundPackageDao().getEntities().toObservable(), new BiFunction() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda18
                @Override // io.reactivex.functions.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ShopActivityOld.handleResponse$lambda$6(this.f$0, mutableList, (List) obj, (List) obj2);
                }
            });
            if (observableZip == null || (observableSubscribeOn = observableZip.subscribeOn(Schedulers.io())) == null || (observableObserveOn = observableSubscribeOn.observeOn(AndroidSchedulers.mainThread())) == null) {
                return;
            }
            final AnonymousClass2 anonymousClass2 = new Function1<Unit, Unit>() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld.handleResponse.2
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Unit unit) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Unit unit) {
                    invoke2(unit);
                    return Unit.INSTANCE;
                }
            };
            Consumer consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda19
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ShopActivityOld.handleResponse$lambda$7(anonymousClass2, obj);
                }
            };
            final AnonymousClass3 anonymousClass3 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld.handleResponse.3
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable th) {
                    Timber.INSTANCE.e(th);
                }
            };
            observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda20
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ShopActivityOld.handleResponse$lambda$8(anonymousClass3, obj);
                }
            });
            return;
        }
        ShopActivityOld shopActivityOld = this;
        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption$default = DialogManager.createErrorAlertDialogWithSendLogOption$default(DialogManager.INSTANCE, shopActivityOld, response.getError(), null, 4, null);
        if (alertDialogCreateErrorAlertDialogWithSendLogOption$default != null) {
            alertDialogCreateErrorAlertDialogWithSendLogOption$default.show();
        }
        ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter = this.mAdapter;
        if (shopSoundPackagesRvAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            shopSoundPackagesRvAdapter = null;
        }
        shopSoundPackagesRvAdapter.clear();
        Toast.makeText(shopActivityOld, "Error: " + response.getError(), 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit handleResponse$lambda$6(ShopActivityOld this$0, List shopPackages, List billingResponse, List databaseResponse) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(shopPackages, "$shopPackages");
        Intrinsics.checkNotNullParameter(billingResponse, "billingResponse");
        Intrinsics.checkNotNullParameter(databaseResponse, "databaseResponse");
        this$0.updatePurchaseStatuses(billingResponse, shopPackages);
        this$0.updateBoardLoadedAndUpdateStatuses(databaseResponse, shopPackages);
        if (Variables.INSTANCE.getSUBSCRIPTION_FEATURE_REQUIREMENTS_SATISFIED() && !Settings.INSTANCE.isSubscriptionActive()) {
            shopPackages.add(0, new ShopSoundPackage(0, null, 0, null, null, 0.0f, null, null, false, false, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null));
        }
        ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter = this$0.mAdapter;
        if (shopSoundPackagesRvAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            shopSoundPackagesRvAdapter = null;
        }
        shopSoundPackagesRvAdapter.updateAll(shopPackages);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleResponse$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleResponse$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void updatePurchaseStatuses(List<? extends Purchase> purchases, List<ShopSoundPackage> shopPackages) {
        if (purchases != null) {
            for (Purchase purchase : purchases) {
                Timber.INSTANCE.i("Purchase: %s", purchase.toString());
                Iterator<ShopSoundPackage> it = shopPackages.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ShopSoundPackage next = it.next();
                    Timber.INSTANCE.i("shopPackageId: %s SKU: %s", next.getGooglePlayId(), purchase.getSkus());
                    if (purchase.getSkus().contains(next.getGooglePlayId())) {
                        next.setPurchasedState();
                        break;
                    }
                }
                ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter = null;
                if (Variables.INSTANCE.getSUBSCRIPTION_FEATURE_REQUIREMENTS_SATISFIED() && !Settings.INSTANCE.isSubscriptionActive()) {
                    List mutableList = CollectionsKt.toMutableList((Collection) shopPackages);
                    mutableList.add(0, new ShopSoundPackage(0, null, 0, null, null, 0.0f, null, null, false, false, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null));
                    ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter2 = this.mAdapter;
                    if (shopSoundPackagesRvAdapter2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                    } else {
                        shopSoundPackagesRvAdapter = shopSoundPackagesRvAdapter2;
                    }
                    shopSoundPackagesRvAdapter.updateAll(mutableList);
                } else {
                    ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter3 = this.mAdapter;
                    if (shopSoundPackagesRvAdapter3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                    } else {
                        shopSoundPackagesRvAdapter = shopSoundPackagesRvAdapter3;
                    }
                    shopSoundPackagesRvAdapter.updateAll(shopPackages);
                }
            }
        }
    }

    private final void updateBoardLoadedAndUpdateStatuses(List<InstalledSoundPackageEntity> installedSoundPackages, List<ShopSoundPackage> shopPackages) {
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

    @Override // com.thor.networkmodule.network.OnLoadDataListener
    public void handleError(Throwable error) {
        AlertDialog alertDialogCreateErrorAlertDialog$default;
        Intrinsics.checkNotNullParameter(error, "error");
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400")) {
            AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, error.getMessage(), null, 4, null);
            if (alertDialogCreateErrorAlertDialog$default2 != null) {
                alertDialogCreateErrorAlertDialog$default2.show();
            }
        } else if (Intrinsics.areEqual(error.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, error.getMessage(), null, 4, null)) != null) {
            alertDialogCreateErrorAlertDialog$default.show();
        }
        ActivityShopOldBinding activityShopOldBinding = this.binding;
        if (activityShopOldBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopOldBinding = null;
        }
        activityShopOldBinding.swipeContainer.setRefreshing(false);
        Timber.INSTANCE.e(error);
    }

    private final void setPriceFromSkuDetailList(final List<ShopSoundPackage> shopSoundPackages) {
        ArrayList arrayList = new ArrayList();
        Iterator<ShopSoundPackage> it = shopSoundPackages.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getGooglePlayId());
        }
        Single<List<SkuDetails>> singleObserveOn = new BillingManager(this).getSkuDetailsList(arrayList, "inapp").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        final Function2<List<? extends SkuDetails>, Throwable, Unit> function2 = new Function2<List<? extends SkuDetails>, Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld.setPriceFromSkuDetailList.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends SkuDetails> list, Throwable th) {
                invoke2(list, th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<? extends SkuDetails> list, Throwable th) {
                if (list != null) {
                    for (SkuDetails skuDetails : list) {
                        for (ShopSoundPackage shopSoundPackage : shopSoundPackages) {
                            if (skuDetails.getSku().equals(shopSoundPackage.getGooglePlayId())) {
                                shopSoundPackage.setPrice(skuDetails.getPriceAmountMicros() / 1000000.0f);
                                String priceCurrencyCode = skuDetails.getPriceCurrencyCode();
                                Intrinsics.checkNotNullExpressionValue(priceCurrencyCode, "skuDetail.priceCurrencyCode");
                                shopSoundPackage.setCurrency(priceCurrencyCode);
                            }
                        }
                        Timber.INSTANCE.i("sku: %s", skuDetails.getSku().toString());
                        Timber.INSTANCE.i("price: %s", skuDetails.getPrice());
                    }
                    ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter = this.mAdapter;
                    if (shopSoundPackagesRvAdapter == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                        shopSoundPackagesRvAdapter = null;
                    }
                    shopSoundPackagesRvAdapter.setSkuDetailsList(list);
                }
            }
        };
        singleObserveOn.subscribe(new BiConsumer() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ShopActivityOld.setPriceFromSkuDetailList$lambda$11(function2, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setPriceFromSkuDetailList$lambda$11(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj, obj2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(DeleteSoundPackageEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        BleManager.INSTANCE.from(this).executeDeleteInstalledSoundPackageCommand((short) event.getSoundPackage().getId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(DeletedSoundPackageEvent event) {
        Observable<BaseResponse> observableSendStatisticsAboutDeleteSoundPackage;
        Observable<BaseResponse> observableSubscribeOn;
        Observable<BaseResponse> observableObserveOn;
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter = this.mAdapter;
        if (shopSoundPackagesRvAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            shopSoundPackagesRvAdapter = null;
        }
        shopSoundPackagesRvAdapter.removeInstalledPackage(event.getPackageId());
        ShopActivityOld shopActivityOld = this;
        Completable completableDeleteById = ThorDatabase.INSTANCE.from(shopActivityOld).installedSoundPackageDao().deleteById(event.getPackageId());
        Action action = new Action() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Action
            public final void run() {
                ShopActivityOld.onMessageEvent$lambda$12();
            }
        };
        final C02262 c02262 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld.onMessageEvent.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        completableDeleteById.subscribe(action, new Consumer() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ShopActivityOld.onMessageEvent$lambda$13(c02262, obj);
            }
        });
        UsersManager usersManagerFrom = UsersManager.INSTANCE.from(shopActivityOld);
        if (usersManagerFrom == null || (observableSendStatisticsAboutDeleteSoundPackage = usersManagerFrom.sendStatisticsAboutDeleteSoundPackage(event.getPackageId())) == null || (observableSubscribeOn = observableSendStatisticsAboutDeleteSoundPackage.subscribeOn(Schedulers.io())) == null || (observableObserveOn = observableSubscribeOn.observeOn(AndroidSchedulers.mainThread())) == null) {
            return;
        }
        final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld.onMessageEvent.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                invoke2(baseResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BaseResponse baseResponse) {
                AlertDialog alertDialogCreateErrorAlertDialog$default;
                if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, ShopActivityOld.this, baseResponse.getError(), null, 4, null)) != null) {
                    alertDialogCreateErrorAlertDialog$default.show();
                }
                Timber.INSTANCE.i(baseResponse.toString(), new Object[0]);
            }
        };
        Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ShopActivityOld.onMessageEvent$lambda$14(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld.onMessageEvent.4
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
                AlertDialog alertDialogCreateErrorAlertDialog$default;
                if (Intrinsics.areEqual(th.getMessage(), "HTTP 400")) {
                    AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, ShopActivityOld.this, th.getMessage(), null, 4, null);
                    if (alertDialogCreateErrorAlertDialog$default2 != null) {
                        alertDialogCreateErrorAlertDialog$default2.show();
                    }
                } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, ShopActivityOld.this, th.getMessage(), null, 4, null)) != null) {
                    alertDialogCreateErrorAlertDialog$default.show();
                }
                Timber.INSTANCE.e(th);
            }
        };
        observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ShopActivityOld.onMessageEvent$lambda$15(function12, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$12() {
        Timber.INSTANCE.i("Success", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$15(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UploadSoundPackageSuccessfulEvent event) {
        Observable<BaseResponse> observableSendStatisticsAboutUploadSoundPackage;
        Observable<BaseResponse> observableSubscribeOn;
        Observable<BaseResponse> observableObserveOn;
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        final ShopSoundPackage soundPackage = event.getSoundPackage();
        int id = soundPackage.getId();
        int pkgVer = soundPackage.getPkgVer();
        ArrayList<ModeType> modeImages = soundPackage.getModeImages();
        Intrinsics.checkNotNull(modeImages != null ? Integer.valueOf(modeImages.size()) : null);
        InstalledSoundPackageEntity installedSoundPackageEntity = new InstalledSoundPackageEntity(id, pkgVer, r2.intValue() - 1, false, 8, null);
        ShopActivityOld shopActivityOld = this;
        Completable completableInsert = ThorDatabase.INSTANCE.from(shopActivityOld).installedSoundPackageDao().insert(installedSoundPackageEntity);
        Action action = new Action() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Action
            public final void run() {
                ShopActivityOld.onMessageEvent$lambda$17(soundPackage, this);
            }
        };
        final AnonymousClass6 anonymousClass6 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld.onMessageEvent.6
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        completableInsert.subscribe(action, new Consumer() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ShopActivityOld.onMessageEvent$lambda$18(anonymousClass6, obj);
            }
        });
        UsersManager usersManagerFrom = UsersManager.INSTANCE.from(shopActivityOld);
        if (usersManagerFrom == null || (observableSendStatisticsAboutUploadSoundPackage = usersManagerFrom.sendStatisticsAboutUploadSoundPackage(soundPackage.getId())) == null || (observableSubscribeOn = observableSendStatisticsAboutUploadSoundPackage.subscribeOn(Schedulers.io())) == null || (observableObserveOn = observableSubscribeOn.observeOn(AndroidSchedulers.mainThread())) == null) {
            return;
        }
        final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld.onMessageEvent.7
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                invoke2(baseResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BaseResponse baseResponse) {
                AlertDialog alertDialogCreateErrorAlertDialog$default;
                if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, ShopActivityOld.this, baseResponse.getError(), null, 4, null)) != null) {
                    alertDialogCreateErrorAlertDialog$default.show();
                }
                Timber.INSTANCE.i(baseResponse.toString(), new Object[0]);
            }
        };
        Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ShopActivityOld.onMessageEvent$lambda$19(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld.onMessageEvent.8
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
                AlertDialog alertDialogCreateErrorAlertDialog$default;
                if (Intrinsics.areEqual(th.getMessage(), "HTTP 400")) {
                    AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, ShopActivityOld.this, th.getMessage(), null, 4, null);
                    if (alertDialogCreateErrorAlertDialog$default2 != null) {
                        alertDialogCreateErrorAlertDialog$default2.show();
                    }
                } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, ShopActivityOld.this, th.getMessage(), null, 4, null)) != null) {
                    alertDialogCreateErrorAlertDialog$default.show();
                }
                Timber.INSTANCE.e(th);
            }
        };
        observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ShopActivityOld.onMessageEvent$lambda$20(function12, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$17(ShopSoundPackage soundPackage, ShopActivityOld this$0) {
        Intrinsics.checkNotNullParameter(soundPackage, "$soundPackage");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Timber.INSTANCE.i("Success", new Object[0]);
        ShopSoundPackage shopSoundPackage = new ShopSoundPackage(soundPackage);
        shopSoundPackage.setLoadedOnBoard(true);
        shopSoundPackage.setNeedUpdate(false);
        ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter = this$0.mAdapter;
        if (shopSoundPackagesRvAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            shopSoundPackagesRvAdapter = null;
        }
        shopSoundPackagesRvAdapter.updateItem(shopSoundPackage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$18(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$19(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$20(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(PurchaseBillingSuccessEvent event) {
        Observable<BaseResponse> observableSendGooglePaymentInfo;
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.isSuccessful()) {
            UsersManager usersManagerFrom = UsersManager.INSTANCE.from(this);
            if (usersManagerFrom != null && (observableSendGooglePaymentInfo = usersManagerFrom.sendGooglePaymentInfo(ShopSoundPackageDataBindingAdapter.INSTANCE.getClickedItem())) != null) {
                final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld.onMessageEvent.9
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                        invoke2(baseResponse);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(BaseResponse baseResponse) {
                        Timber.INSTANCE.i("Payment response: %s", baseResponse);
                        if (baseResponse.isSuccessful()) {
                            ShopSoundPackage clickedItem = ShopSoundPackageDataBindingAdapter.INSTANCE.getClickedItem();
                            if (clickedItem != null) {
                                ShopActivityOld shopActivityOld = ShopActivityOld.this;
                                ShopSoundPackage shopSoundPackageCopy = ShopSoundPackage.INSTANCE.copy(clickedItem);
                                shopSoundPackageCopy.setPurchasedState();
                                ShopSoundPackagesRvAdapter shopSoundPackagesRvAdapter = shopActivityOld.mAdapter;
                                if (shopSoundPackagesRvAdapter == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                                    shopSoundPackagesRvAdapter = null;
                                }
                                shopSoundPackagesRvAdapter.updateItem(shopSoundPackageCopy);
                                return;
                            }
                            return;
                        }
                        Toast.makeText(ShopActivityOld.this, R.string.warning_unknown_error, 1).show();
                    }
                };
                Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda10
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ShopActivityOld.onMessageEvent$lambda$21(function1, obj);
                    }
                };
                final AnonymousClass10 anonymousClass10 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld.onMessageEvent.10
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                        invoke2(th);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(Throwable th) {
                        Timber.INSTANCE.e(th);
                    }
                };
                observableSendGooglePaymentInfo.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda12
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ShopActivityOld.onMessageEvent$lambda$22(anonymousClass10, obj);
                    }
                });
            }
            onLoadData();
            return;
        }
        Integer errorCode = event.getErrorCode();
        if (errorCode != null) {
            errorCode.intValue();
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str = String.format("Error Code : %d", Arrays.copyOf(new Object[]{event.getErrorCode()}, 1));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            Toast.makeText(this, str, 1).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$21(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$22(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Subscribe
    public final void onMessageEvent(BluetoothDeviceConnectionChangedEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (getBleManager().isBleEnabledAndDeviceConnected() && Settings.INSTANCE.isLoadSoundInterrupted() && Settings.INSTANCE.getSoundPackageParcel() != null) {
            isNeedSendInterruptedSoundEvent = true;
            getBleManager().readRemoteRssi();
        }
    }

    @Subscribe
    public final void onMessageEvent(UploadSoundPackageInterruptedEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (getBleManager().isBleEnabledAndDeviceConnected() && Settings.INSTANCE.isLoadSoundInterrupted() && Settings.INSTANCE.getSoundPackageParcel() != null) {
            isNeedSendInterruptedSoundEvent = true;
            getBleManager().readRemoteRssi();
        }
    }

    @Subscribe
    public final void onMessageEvent(BluetoothDeviceRssiEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (isNeedSendInterruptedSoundEvent) {
            if (event.getRssi() >= -80) {
                stableSignalCounter++;
            } else {
                stableSignalCounter = 0;
            }
            if (event.getRssi() >= -80 && stableSignalCounter > 50) {
                isNeedSendInterruptedSoundEvent = false;
                runOnUiThread(new Runnable() { // from class: com.thor.app.gui.activities.shop.main.ShopActivityOld$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        ShopActivityOld.onMessageEvent$lambda$24(this.f$0);
                    }
                });
            } else {
                getBleManager().readRemoteRssi();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$24(ShopActivityOld this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ShopSoundPackage soundPackageParcel = Settings.INSTANCE.getSoundPackageParcel();
        Intrinsics.checkNotNull(soundPackageParcel);
        this$0.createErrorUploadSoundPackageAlertDialog(soundPackageParcel).show();
    }
}
