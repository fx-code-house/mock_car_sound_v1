package com.thor.app.gui.fragments.shop.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.billingclient.api.SkuDetails;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.FragmentMainShopBinding;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.gms.common.util.Hex;
import com.thor.app.bus.events.PurchaseBillingSuccessEvent;
import com.thor.app.bus.events.shop.main.DeleteSoundDone;
import com.thor.app.bus.events.shop.main.DeleteSoundPackageEvent;
import com.thor.app.bus.events.shop.main.DeletedSoundPackageEvent;
import com.thor.app.bus.events.shop.main.UploadSoundPackageSuccessfulEvent;
import com.thor.app.databinding.adapters.ShopSoundPackageDataBindingAdapter;
import com.thor.app.gui.adapters.shop.main.ShopSoundPackagesDiffUtilCallback;
import com.thor.app.gui.adapters.shop.main.ShopSoundPackagesRvAdapter;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.fragments.presets.main.MainSoundsFragment;
import com.thor.app.gui.fragments.shop.main.MainShopFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.entity.InstalledSoundPackageEntity;
import com.thor.app.settings.Settings;
import com.thor.app.utils.extensions.ContextKt;
import com.thor.businessmodule.bluetooth.model.other.InstalledPreset;
import com.thor.businessmodule.bluetooth.response.other.InstalledPresetsResponse;
import com.thor.networkmodule.model.ModeType;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import javax.inject.Inject;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.StringCompanionObject;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: MainShopFragment.kt */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 N2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001NB\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0002J\b\u00100\u001a\u000201H\u0002J\u0010\u00102\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0002J\u001a\u00103\u001a\u00020-2\b\u00104\u001a\u0004\u0018\u0001052\u0006\u00106\u001a\u00020\bH\u0002J\b\u00107\u001a\u00020-H\u0002J\b\u00108\u001a\u00020-H\u0016J\b\u00109\u001a\u00020-H\u0002J\b\u0010:\u001a\u00020-H\u0002J\b\u0010;\u001a\u00020-H\u0002J\u001c\u0010<\u001a\u00020-2\u0012\u0010=\u001a\u000e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020-0>H\u0002J\b\u0010@\u001a\u00020-H\u0002J\b\u0010A\u001a\u00020-H\u0016J\u0010\u0010B\u001a\u00020-2\u0006\u0010.\u001a\u00020CH\u0007J\u0010\u0010B\u001a\u00020-2\u0006\u0010.\u001a\u00020DH\u0007J\u0010\u0010B\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0007J\u0010\u0010B\u001a\u00020-2\u0006\u0010.\u001a\u00020EH\u0007J\b\u0010F\u001a\u00020-H\u0016J\b\u0010G\u001a\u00020-H\u0002J\u0016\u0010H\u001a\u00020-2\f\u0010I\u001a\b\u0012\u0004\u0012\u00020K0JH\u0002J\u0016\u0010L\u001a\u00020-2\f\u0010I\u001a\b\u0012\u0004\u0012\u00020M0JH\u0002R.\u0010\u0004\u001a\u001c\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u001e\u0010\u0011\u001a\u00020\u00128\u0006@\u0006X\u0087.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001b\u0010\u0017\u001a\u00020\u00188BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u0010\u001a\u0004\b\u0019\u0010\u001aR\u001b\u0010\u001c\u001a\u00020\u001d8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010\u0010\u001a\u0004\b\u001e\u0010\u001fR\u001e\u0010!\u001a\u00020\"8\u0006@\u0006X\u0087.¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001b\u0010'\u001a\u00020(8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b+\u0010\u0010\u001a\u0004\b)\u0010*¨\u0006O"}, d2 = {"Lcom/thor/app/gui/fragments/shop/main/MainShopFragment;", "Lcom/thor/app/gui/fragments/base/BaseBindingFragment;", "Lcom/carsystems/thor/app/databinding/FragmentMainShopBinding;", "()V", "bindingInflater", "Lkotlin/Function3;", "Landroid/view/LayoutInflater;", "Landroid/view/ViewGroup;", "", "getBindingInflater", "()Lkotlin/jvm/functions/Function3;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "database", "Lcom/thor/app/room/ThorDatabase;", "getDatabase", "()Lcom/thor/app/room/ThorDatabase;", "setDatabase", "(Lcom/thor/app/room/ThorDatabase;)V", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "shopAdapter", "Lcom/thor/app/gui/adapters/shop/main/ShopSoundPackagesRvAdapter;", "getShopAdapter", "()Lcom/thor/app/gui/adapters/shop/main/ShopSoundPackagesRvAdapter;", "shopAdapter$delegate", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "setUsersManager", "(Lcom/thor/app/managers/UsersManager;)V", "viewModel", "Lcom/thor/app/gui/fragments/shop/main/MainShopViewModel;", "getViewModel", "()Lcom/thor/app/gui/fragments/shop/main/MainShopViewModel;", "viewModel$delegate", "afterPoling", "", "event", "Lcom/thor/app/bus/events/shop/main/DeletedSoundPackageEvent;", "createProgressLoading", "Landroid/app/Dialog;", "executePoiling", "handleError", "message", "", "showToUser", "hideRefreshing", "init", "initAdapter", "initListener", "initSwipeContainer", "loadInstalledPresets", "action", "Lkotlin/Function1;", "Lcom/thor/businessmodule/bluetooth/response/other/InstalledPresetsResponse;", "observeUiState", "onDestroyView", "onMessageEvent", "Lcom/thor/app/bus/events/PurchaseBillingSuccessEvent;", "Lcom/thor/app/bus/events/shop/main/DeleteSoundPackageEvent;", "Lcom/thor/app/bus/events/shop/main/UploadSoundPackageSuccessfulEvent;", "onResume", "showRefreshing", "updateShopData", "data", "", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "updateSkuData", "Lcom/android/billingclient/api/SkuDetails;", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class MainShopFragment extends Hilt_MainShopFragment<FragmentMainShopBinding> {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Function3<LayoutInflater, ViewGroup, Boolean, FragmentMainShopBinding> bindingInflater = MainShopFragment$bindingInflater$1.INSTANCE;

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager;

    @Inject
    public ThorDatabase database;

    /* renamed from: handler$delegate, reason: from kotlin metadata */
    private final Lazy handler;

    /* renamed from: shopAdapter$delegate, reason: from kotlin metadata */
    private final Lazy shopAdapter;

    @Inject
    public UsersManager usersManager;

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    public MainShopFragment() {
        final MainShopFragment mainShopFragment = this;
        final Function0<Fragment> function0 = new Function0<Fragment>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Fragment invoke() {
                return mainShopFragment;
            }
        };
        final Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<ViewModelStoreOwner>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$special$$inlined$viewModels$default$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStoreOwner invoke() {
                return (ViewModelStoreOwner) function0.invoke();
            }
        });
        final Function0 function02 = null;
        this.viewModel = FragmentViewModelLazyKt.createViewModelLazy(mainShopFragment, Reflection.getOrCreateKotlinClass(MainShopViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$special$$inlined$viewModels$default$3
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = FragmentViewModelLazyKt.m79viewModels$lambda1(lazy).getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "owner.viewModelStore");
                return viewModelStore;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$special$$inlined$viewModels$default$4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function03 = function02;
                if (function03 != null && (creationExtras = (CreationExtras) function03.invoke()) != null) {
                    return creationExtras;
                }
                ViewModelStoreOwner viewModelStoreOwnerM79viewModels$lambda1 = FragmentViewModelLazyKt.m79viewModels$lambda1(lazy);
                HasDefaultViewModelProviderFactory hasDefaultViewModelProviderFactory = viewModelStoreOwnerM79viewModels$lambda1 instanceof HasDefaultViewModelProviderFactory ? (HasDefaultViewModelProviderFactory) viewModelStoreOwnerM79viewModels$lambda1 : null;
                CreationExtras defaultViewModelCreationExtras = hasDefaultViewModelProviderFactory != null ? hasDefaultViewModelProviderFactory.getDefaultViewModelCreationExtras() : null;
                return defaultViewModelCreationExtras == null ? CreationExtras.Empty.INSTANCE : defaultViewModelCreationExtras;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$special$$inlined$viewModels$default$5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory;
                ViewModelStoreOwner viewModelStoreOwnerM79viewModels$lambda1 = FragmentViewModelLazyKt.m79viewModels$lambda1(lazy);
                HasDefaultViewModelProviderFactory hasDefaultViewModelProviderFactory = viewModelStoreOwnerM79viewModels$lambda1 instanceof HasDefaultViewModelProviderFactory ? (HasDefaultViewModelProviderFactory) viewModelStoreOwnerM79viewModels$lambda1 : null;
                if (hasDefaultViewModelProviderFactory == null || (defaultViewModelProviderFactory = hasDefaultViewModelProviderFactory.getDefaultViewModelProviderFactory()) == null) {
                    defaultViewModelProviderFactory = mainShopFragment.getDefaultViewModelProviderFactory();
                }
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "(owner as? HasDefaultVie…tViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        });
        this.shopAdapter = LazyKt.lazy(new Function0<ShopSoundPackagesRvAdapter>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$shopAdapter$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ShopSoundPackagesRvAdapter invoke() {
                return new ShopSoundPackagesRvAdapter(new ShopSoundPackagesDiffUtilCallback());
            }
        });
        this.bleManager = LazyKt.lazy(new Function0<BleManager>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$bleManager$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final BleManager invoke() {
                return BleManager.INSTANCE.from(this.this$0.requireContext());
            }
        });
        this.handler = LazyKt.lazy(new Function0<Handler>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$handler$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Handler invoke() {
                return new Handler(Looper.getMainLooper());
            }
        });
    }

    /* compiled from: MainShopFragment.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/thor/app/gui/fragments/shop/main/MainShopFragment$Companion;", "", "()V", "newInstance", "Lcom/thor/app/gui/fragments/shop/main/MainShopFragment;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MainShopFragment newInstance() {
            return new MainShopFragment();
        }
    }

    @Override // com.thor.app.gui.fragments.base.BaseBindingFragment
    public Function3<LayoutInflater, ViewGroup, Boolean, FragmentMainShopBinding> getBindingInflater() {
        return this.bindingInflater;
    }

    public final ThorDatabase getDatabase() {
        ThorDatabase thorDatabase = this.database;
        if (thorDatabase != null) {
            return thorDatabase;
        }
        Intrinsics.throwUninitializedPropertyAccessException("database");
        return null;
    }

    public final void setDatabase(ThorDatabase thorDatabase) {
        Intrinsics.checkNotNullParameter(thorDatabase, "<set-?>");
        this.database = thorDatabase;
    }

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

    /* JADX INFO: Access modifiers changed from: private */
    public final MainShopViewModel getViewModel() {
        return (MainShopViewModel) this.viewModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ShopSoundPackagesRvAdapter getShopAdapter() {
        return (ShopSoundPackagesRvAdapter) this.shopAdapter.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Handler getHandler() {
        return (Handler) this.handler.getValue();
    }

    @Override // com.thor.app.gui.fragments.base.BaseFragment
    public void init() throws SecurityException {
        initSwipeContainer();
        initAdapter();
        observeUiState();
        getViewModel().loadData();
        initListener();
        EventBus.getDefault().register(this);
    }

    @Override // com.thor.app.gui.fragments.base.BaseBindingFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    private final void initListener() {
        final Dialog dialogCreateProgressLoading = createProgressLoading();
        final Function1<Boolean, Unit> function1 = new Function1<Boolean, Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.initListener.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke2(bool);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Boolean bool) {
                if (Intrinsics.areEqual((Object) bool, (Object) true)) {
                    dialogCreateProgressLoading.show();
                } else if (Intrinsics.areEqual((Object) bool, (Object) false)) {
                    dialogCreateProgressLoading.dismiss();
                }
            }
        };
        getViewModel().getPoilingState().observe(this, new Observer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainShopFragment.initListener$lambda$0(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListener$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        getBleManager().connect();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void initSwipeContainer() {
        WindowManager windowManager;
        Display defaultDisplay;
        SwipeRefreshLayout swipeRefreshLayout = ((FragmentMainShopBinding) getBinding()).swipeContainer;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        swipeRefreshLayout.setColorSchemeColors(ContextKt.fetchAttrValue(contextRequireContext, R.attr.colorAccent));
        ((FragmentMainShopBinding) getBinding()).swipeContainer.setProgressBackgroundColorSchemeResource(R.color.colorRefreshLayoutBackground);
        ((FragmentMainShopBinding) getBinding()).swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda6
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                MainShopFragment.initSwipeContainer$lambda$1(this.f$0);
            }
        });
        DisplayMetrics displayMetrics = new DisplayMetrics();
        FragmentActivity activity = getActivity();
        if (activity != null && (windowManager = activity.getWindowManager()) != null && (defaultDisplay = windowManager.getDefaultDisplay()) != null) {
            defaultDisplay.getMetrics(displayMetrics);
        }
        ((FragmentMainShopBinding) getBinding()).swipeContainer.setDistanceToTriggerSync(displayMetrics.heightPixels / 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initSwipeContainer$lambda$1(MainShopFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getViewModel().loadData();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void initAdapter() {
        getShopAdapter().setRecyclerView(((FragmentMainShopBinding) getBinding()).recyclerView);
        ((FragmentMainShopBinding) getBinding()).recyclerView.setAdapter(getShopAdapter());
        ((FragmentMainShopBinding) getBinding()).nestedScrollView.setOnScrollChangeListener(getShopAdapter().getOnNestedScrollListener());
        ((FragmentMainShopBinding) getBinding()).recyclerView.setItemAnimator(new DefaultItemAnimator() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.initAdapter.1
            @Override // androidx.recyclerview.widget.SimpleItemAnimator, androidx.recyclerview.widget.RecyclerView.ItemAnimator
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
                return true;
            }
        });
    }

    private final void observeUiState() {
        LiveData<MainShopUIState> shopUiState = getViewModel().getShopUiState();
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        final Function1<MainShopUIState, Unit> function1 = new Function1<MainShopUIState, Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.observeUiState.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(MainShopUIState mainShopUIState) {
                invoke2(mainShopUIState);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(MainShopUIState mainShopUIState) {
                if (mainShopUIState instanceof MainShopDataLoading) {
                    MainShopFragment.this.showRefreshing();
                    return;
                }
                if (mainShopUIState instanceof MainShopError) {
                    MainShopError mainShopError = (MainShopError) mainShopUIState;
                    MainShopFragment.this.handleError(mainShopError.getMessage(), mainShopError.getShowToUser());
                } else if (mainShopUIState instanceof MainShopDataLoaded) {
                    MainShopFragment.this.updateShopData(((MainShopDataLoaded) mainShopUIState).getData());
                } else if (mainShopUIState instanceof MainShopSkuLoaded) {
                    MainShopFragment.this.updateSkuData(((MainShopSkuLoaded) mainShopUIState).getData());
                }
            }
        };
        shopUiState.observe(viewLifecycleOwner, new Observer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainShopFragment.observeUiState$lambda$2(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeUiState$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateShopData(List<ShopSoundPackage> data) {
        hideRefreshing();
        getShopAdapter().updateAll(data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateSkuData(List<? extends SkuDetails> data) {
        getShopAdapter().setSkuDetailsList(data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(String message, boolean showToUser) {
        hideRefreshing();
        Timber.INSTANCE.e(message, new Object[0]);
        if (showToUser) {
            AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(getContext(), message, 0);
            if (alertDialogCreateErrorAlertDialogWithSendLogOption != null) {
                alertDialogCreateErrorAlertDialogWithSendLogOption.show();
            }
            Toast.makeText(requireContext(), message, 1).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void showRefreshing() {
        ((FragmentMainShopBinding) getBinding()).swipeContainer.setRefreshing(true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void hideRefreshing() {
        ((FragmentMainShopBinding) getBinding()).swipeContainer.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(final DeleteSoundPackageEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        getViewModel().setPoilingActiveState(true);
        Log.i("FIND", "12");
        BleManager.executeActivatePresetCommand$default(getBleManager(), (short) 0, getBleManager().getMActivatedPreset(), null, 4, null);
        getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MainShopFragment.onMessageEvent$lambda$3(this.f$0, event);
            }
        }, 800L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$3(MainShopFragment this$0, DeleteSoundPackageEvent event) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(event, "$event");
        this$0.getBleManager().executeDeleteInstalledSoundPackageCommand((short) event.getSoundPackage().getId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(final DeletedSoundPackageEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                MainShopFragment.onMessageEvent$lambda$4(this.f$0, event);
            }
        }, 4000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$4(MainShopFragment this$0, DeletedSoundPackageEvent event) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(event, "$event");
        this$0.afterPoling(event);
    }

    private final Dialog createProgressLoading() {
        Dialog dialog = new Dialog(requireContext(), 2131886084);
        dialog.setContentView(R.layout.dialog_progres_delete);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        Window window2 = dialog.getWindow();
        ProgressBar progressBar = window2 != null ? (ProgressBar) window2.findViewById(R.id.progressBar) : null;
        int carFuelType = Settings.INSTANCE.getCarFuelType();
        if (carFuelType != 1) {
            if (carFuelType == 2 && progressBar != null) {
                progressBar.setIndeterminateDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_progressbar, null));
            }
        } else if (progressBar != null) {
            progressBar.setIndeterminateDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_progressbar_blue, null));
        }
        return dialog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void executePoiling(DeletedSoundPackageEvent event) {
        Observable<ByteArrayOutputStream> observableObserveOn = getBleManager().executePoilingCommand().observeOn(Schedulers.io());
        final MainShopFragment$executePoiling$commandDisposable$1 mainShopFragment$executePoiling$commandDisposable$1 = new MainShopFragment$executePoiling$commandDisposable$1(this, event);
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainShopFragment.executePoiling$lambda$5(mainShopFragment$executePoiling$commandDisposable$1, obj);
            }
        };
        final MainShopFragment$executePoiling$commandDisposable$2 mainShopFragment$executePoiling$commandDisposable$2 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$executePoiling$commandDisposable$2
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
        Disposable commandDisposable = observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainShopFragment.executePoiling$lambda$6(mainShopFragment$executePoiling$commandDisposable$2, obj);
            }
        });
        BleManager bleManager = getBleManager();
        Intrinsics.checkNotNullExpressionValue(commandDisposable, "commandDisposable");
        bleManager.addCommandDisposable(commandDisposable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executePoiling$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executePoiling$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: MainShopFragment.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "installedPresets", "Lcom/thor/businessmodule/bluetooth/response/other/InstalledPresetsResponse;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.fragments.shop.main.MainShopFragment$afterPoling$1, reason: invalid class name */
    static final class AnonymousClass1 extends Lambda implements Function1<InstalledPresetsResponse, Unit> {
        final /* synthetic */ DeletedSoundPackageEvent $event;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(DeletedSoundPackageEvent deletedSoundPackageEvent) {
            super(1);
            this.$event = deletedSoundPackageEvent;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(InstalledPresetsResponse installedPresetsResponse) {
            invoke2(installedPresetsResponse);
            return Unit.INSTANCE;
        }

        /* compiled from: MainShopFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
        @DebugMetadata(c = "com.thor.app.gui.fragments.shop.main.MainShopFragment$afterPoling$1$1", f = "MainShopFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.thor.app.gui.fragments.shop.main.MainShopFragment$afterPoling$1$1, reason: invalid class name and collision with other inner class name */
        static final class C00721 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ DeletedSoundPackageEvent $event;
            int label;
            final /* synthetic */ MainShopFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00721(MainShopFragment mainShopFragment, DeletedSoundPackageEvent deletedSoundPackageEvent, Continuation<? super C00721> continuation) {
                super(2, continuation);
                this.this$0 = mainShopFragment;
                this.$event = deletedSoundPackageEvent;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00721(this.this$0, this.$event, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00721) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Completable completableDeleteById = this.this$0.getDatabase().installedSoundPackageDao().deleteById(this.$event.getPackageId());
                Action action = new Action() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$afterPoling$1$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Action
                    public final void run() {
                        MainShopFragment.AnonymousClass1.C00721.invokeSuspend$lambda$0();
                    }
                };
                final AnonymousClass2 anonymousClass2 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.afterPoling.1.1.2
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
                completableDeleteById.subscribe(action, new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$afterPoling$1$1$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj2) {
                        anonymousClass2.invoke(obj2);
                    }
                });
                return Unit.INSTANCE;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static final void invokeSuspend$lambda$0() {
                Timber.INSTANCE.i("Success", new Object[0]);
            }
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(InstalledPresetsResponse installedPresets) {
            Observable<BaseResponse> observableObserveOn;
            Intrinsics.checkNotNullParameter(installedPresets, "installedPresets");
            MainShopFragment.this.getShopAdapter().removeInstalledPackage(this.$event.getPackageId());
            Object obj = null;
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00721(MainShopFragment.this, this.$event, null), 3, null);
            Observable<BaseResponse> observableSubscribeOn = MainShopFragment.this.getUsersManager().sendStatisticsAboutDeleteSoundPackage(this.$event.getPackageId()).subscribeOn(Schedulers.io());
            if (observableSubscribeOn != null && (observableObserveOn = observableSubscribeOn.observeOn(AndroidSchedulers.mainThread())) != null) {
                final MainShopFragment mainShopFragment = MainShopFragment.this;
                final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.afterPoling.1.2
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
                        AlertDialog alertDialogCreateErrorAlertDialog;
                        if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(mainShopFragment.getContext(), baseResponse.getError(), baseResponse.getCode())) != null) {
                            alertDialogCreateErrorAlertDialog.show();
                        }
                        Timber.INSTANCE.i(baseResponse.toString(), new Object[0]);
                    }
                };
                Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$afterPoling$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj2) {
                        MainShopFragment.AnonymousClass1.invoke$lambda$0(function1, obj2);
                    }
                };
                final MainShopFragment mainShopFragment2 = MainShopFragment.this;
                final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.afterPoling.1.3
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
                        AlertDialog alertDialogCreateErrorAlertDialog;
                        if (Intrinsics.areEqual(th.getMessage(), "HTTP 400")) {
                            AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(mainShopFragment2.getContext(), th.getMessage(), 0);
                            if (alertDialogCreateErrorAlertDialog2 != null) {
                                alertDialogCreateErrorAlertDialog2.show();
                            }
                        } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(mainShopFragment2.getContext(), th.getMessage(), 0)) != null) {
                            alertDialogCreateErrorAlertDialog.show();
                        }
                        Timber.INSTANCE.e(th);
                    }
                };
                observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$afterPoling$1$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj2) {
                        MainShopFragment.AnonymousClass1.invoke$lambda$1(function12, obj2);
                    }
                });
            }
            try {
                if (!installedPresets.getInstalledPresets().getPresets().isEmpty()) {
                    LinkedHashSet<InstalledPreset> presets = installedPresets.getInstalledPresets().getPresets();
                    InstalledPreset mActivatedPreset = MainShopFragment.this.getBleManager().getMActivatedPreset();
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    DeletedSoundPackageEvent deletedSoundPackageEvent = this.$event;
                    for (InstalledPreset installedPreset : presets) {
                        if (installedPreset.getPackageId() == deletedSoundPackageEvent.getPackageId()) {
                            linkedHashSet.add(installedPreset);
                        }
                    }
                    presets.removeAll(linkedHashSet);
                    installedPresets.getInstalledPresets().setPresets(presets);
                    installedPresets.getInstalledPresets().setCurrentPresetId(MainShopFragment.this.getBleManager().getMActivatedPresetIndex());
                    installedPresets.getInstalledPresets().setAmount((short) presets.size());
                    Iterator<T> it = installedPresets.getInstalledPresets().getPresets().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Object next = it.next();
                        InstalledPreset installedPreset2 = (InstalledPreset) next;
                        if (installedPreset2.getPackageId() == mActivatedPreset.getPackageId() && installedPreset2.getMode() == mActivatedPreset.getMode()) {
                            obj = next;
                            break;
                        }
                    }
                    BleManager.executeWriteInstalledPresetsCommand$default(MainShopFragment.this.getBleManager(), installedPresets.getInstalledPresets(), true, (short) (CollectionsKt.indexOf(presets, (InstalledPreset) obj) + 1), false, null, 24, null);
                    MainShopFragment.this.getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$afterPoling$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            MainShopFragment.AnonymousClass1.invoke$lambda$4();
                        }
                    }, SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            MainShopFragment.this.getViewModel().setPoilingActiveState(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4() {
            EventBus.getDefault().post(new DeleteSoundDone());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void afterPoling(DeletedSoundPackageEvent event) {
        MainSoundsFragment.INSTANCE.setDelRefresh(true);
        loadInstalledPresets(new AnonymousClass1(event));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadInstalledPresets(final Function1<? super InstalledPresetsResponse, Unit> action) {
        Observable<ByteArrayOutputStream> observableObserveOn = getBleManager().executeReadInstalledPresetsCommand().observeOn(AndroidSchedulers.mainThread());
        final C02831 c02831 = new Function1<ByteArrayOutputStream, InstalledPresetsResponse>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.loadInstalledPresets.1
            @Override // kotlin.jvm.functions.Function1
            public final InstalledPresetsResponse invoke(ByteArrayOutputStream outputStream) {
                Intrinsics.checkNotNullParameter(outputStream, "outputStream");
                byte[] byteArray = outputStream.toByteArray();
                Timber.INSTANCE.i("Take data: %s", Hex.bytesToStringUppercase(byteArray));
                return new InstalledPresetsResponse(byteArray);
            }
        };
        Observable<R> map = observableObserveOn.map(new Function() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return MainShopFragment.loadInstalledPresets$lambda$7(c02831, obj);
            }
        });
        final Function1<InstalledPresetsResponse, Unit> function1 = new Function1<InstalledPresetsResponse, Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.loadInstalledPresets.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InstalledPresetsResponse installedPresetsResponse) {
                invoke2(installedPresetsResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InstalledPresetsResponse response) {
                if (!response.getStatus()) {
                    BleManager bleManager = this.getBleManager();
                    final MainShopFragment mainShopFragment = this;
                    final Function1<InstalledPresetsResponse, Unit> function12 = action;
                    bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.loadInstalledPresets.2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
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
                            mainShopFragment.loadInstalledPresets(function12);
                        }
                    });
                    Timber.INSTANCE.e("Response is not correct: %s", response.getErrorCode());
                    return;
                }
                response.getInstalledPresets().getPresets().size();
                Function1<InstalledPresetsResponse, Unit> function13 = action;
                Intrinsics.checkNotNullExpressionValue(response, "response");
                function13.invoke(response);
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainShopFragment.loadInstalledPresets$lambda$8(function1, obj);
            }
        };
        final AnonymousClass3 anonymousClass3 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.loadInstalledPresets.3
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
        Disposable it = map.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainShopFragment.loadInstalledPresets$lambda$9(anonymousClass3, obj);
            }
        });
        BleManager bleManager = getBleManager();
        Intrinsics.checkNotNullExpressionValue(it, "it");
        bleManager.addCommandDisposable(it);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final InstalledPresetsResponse loadInstalledPresets$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (InstalledPresetsResponse) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadInstalledPresets$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadInstalledPresets$lambda$9(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UploadSoundPackageSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        Toast.makeText(getContext(), getString(R.string.message_download_sound_package_successful, event.getSoundPackage().getPkgName()), 1).show();
        final ShopSoundPackage soundPackage = event.getSoundPackage();
        int id = soundPackage.getId();
        int pkgVer = soundPackage.getPkgVer();
        ArrayList<ModeType> modeImages = soundPackage.getModeImages();
        Intrinsics.checkNotNull(modeImages != null ? Integer.valueOf(modeImages.size()) : null);
        Completable completableObserveOn = getDatabase().installedSoundPackageDao().insert(new InstalledSoundPackageEntity(id, pkgVer, r2.intValue() - 1, false, 8, null)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Action action = new Action() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Action
            public final void run() {
                MainShopFragment.onMessageEvent$lambda$12(soundPackage, this);
            }
        };
        final AnonymousClass4 anonymousClass4 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.onMessageEvent.4
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
        completableObserveOn.subscribe(action, new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainShopFragment.onMessageEvent$lambda$13(anonymousClass4, obj);
            }
        });
        Observable<BaseResponse> observableObserveOn = getUsersManager().sendStatisticsAboutUploadSoundPackage(soundPackage.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.onMessageEvent.5
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
                AlertDialog alertDialogCreateErrorAlertDialog;
                if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(MainShopFragment.this.getContext(), baseResponse.getError(), baseResponse.getCode())) != null) {
                    alertDialogCreateErrorAlertDialog.show();
                }
                Timber.INSTANCE.i(baseResponse.toString(), new Object[0]);
            }
        };
        Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainShopFragment.onMessageEvent$lambda$14(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.onMessageEvent.6
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
                AlertDialog alertDialogCreateErrorAlertDialog;
                if (Intrinsics.areEqual(th.getMessage(), "HTTP 400")) {
                    AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(MainShopFragment.this.getContext(), th.getMessage(), 0);
                    if (alertDialogCreateErrorAlertDialog2 != null) {
                        alertDialogCreateErrorAlertDialog2.show();
                    }
                } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(MainShopFragment.this.getContext(), th.getMessage(), 0)) != null) {
                    alertDialogCreateErrorAlertDialog.show();
                }
                Timber.INSTANCE.e(th);
            }
        };
        observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainShopFragment.onMessageEvent$lambda$15(function12, obj);
            }
        });
        getViewModel().loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$12(ShopSoundPackage soundPackage, MainShopFragment this$0) {
        Intrinsics.checkNotNullParameter(soundPackage, "$soundPackage");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Timber.INSTANCE.i("Success", new Object[0]);
        ShopSoundPackage shopSoundPackage = new ShopSoundPackage(soundPackage);
        shopSoundPackage.setLoadedOnBoard(true);
        shopSoundPackage.setNeedUpdate(false);
        this$0.getShopAdapter().updateItem(shopSoundPackage);
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
    public final void onMessageEvent(PurchaseBillingSuccessEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.isSuccessful()) {
            Observable<BaseResponse> observableSendGooglePaymentInfo = getUsersManager().sendGooglePaymentInfo(ShopSoundPackageDataBindingAdapter.INSTANCE.getClickedItem());
            if (observableSendGooglePaymentInfo != null) {
                final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.onMessageEvent.7
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
                                MainShopFragment mainShopFragment = MainShopFragment.this;
                                ShopSoundPackage shopSoundPackageCopy = ShopSoundPackage.INSTANCE.copy(clickedItem);
                                shopSoundPackageCopy.setPurchasedState();
                                mainShopFragment.getShopAdapter().updateItem(shopSoundPackageCopy);
                                return;
                            }
                            return;
                        }
                        Toast.makeText(MainShopFragment.this.requireContext(), R.string.warning_unknown_error, 1).show();
                    }
                };
                Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda2
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        MainShopFragment.onMessageEvent$lambda$16(function1, obj);
                    }
                };
                final AnonymousClass8 anonymousClass8 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment.onMessageEvent.8
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
                observableSendGooglePaymentInfo.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.shop.main.MainShopFragment$$ExternalSyntheticLambda3
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        MainShopFragment.onMessageEvent$lambda$17(anonymousClass8, obj);
                    }
                });
            }
            getViewModel().loadData();
            return;
        }
        Integer errorCode = event.getErrorCode();
        if (errorCode != null) {
            errorCode.intValue();
            Context contextRequireContext = requireContext();
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str = String.format("Error Code : %d", Arrays.copyOf(new Object[]{event.getErrorCode()}, 1));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            Toast.makeText(contextRequireContext, str, 1).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$16(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$17(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }
}
