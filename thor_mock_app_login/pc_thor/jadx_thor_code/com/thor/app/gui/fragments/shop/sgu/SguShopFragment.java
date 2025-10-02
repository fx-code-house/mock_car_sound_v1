package com.thor.app.gui.fragments.shop.sgu;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.billingclient.api.SkuDetails;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.FragmentSguShopBinding;
import com.thor.app.bus.events.shop.sgu.UploadSguSoundPackageSuccessfulEvent;
import com.thor.app.gui.fragments.shop.sgu.item.ShopSguSoundPackageItem;
import com.thor.app.managers.BleManager;
import com.thor.app.utils.extensions.ContextKt;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import com.xwray.groupie.GroupieAdapter;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: SguShopFragment.kt */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 62\b\u0012\u0004\u0012\u00020\u00020\u0001:\u00016B\u0005¢\u0006\u0002\u0010\u0003J\u001a\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010$\u001a\u00020\u000eH\u0002J\b\u0010%\u001a\u00020!H\u0002J\b\u0010&\u001a\u00020!H\u0016J\b\u0010'\u001a\u00020!H\u0002J\b\u0010(\u001a\u00020!H\u0002J\b\u0010)\u001a\u00020!H\u0002J\b\u0010*\u001a\u00020!H\u0016J\u0010\u0010+\u001a\u00020!2\u0006\u0010,\u001a\u00020-H\u0007J\b\u0010.\u001a\u00020!H\u0016J\b\u0010/\u001a\u00020!H\u0002J$\u00100\u001a\u00020!2\f\u00101\u001a\b\u0012\u0004\u0012\u000203022\f\u00104\u001a\b\u0012\u0004\u0012\u00020502H\u0002R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R.\u0010\n\u001a\u001c\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\t\u001a\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0016\u001a\u00020\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\t\u001a\u0004\b\u0018\u0010\u0019R\u001b\u0010\u001b\u001a\u00020\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\t\u001a\u0004\b\u001d\u0010\u001e¨\u00067"}, d2 = {"Lcom/thor/app/gui/fragments/shop/sgu/SguShopFragment;", "Lcom/thor/app/gui/fragments/base/BaseBindingFragment;", "Lcom/carsystems/thor/app/databinding/FragmentSguShopBinding;", "()V", "adapter", "Lcom/xwray/groupie/GroupieAdapter;", "getAdapter", "()Lcom/xwray/groupie/GroupieAdapter;", "adapter$delegate", "Lkotlin/Lazy;", "bindingInflater", "Lkotlin/Function3;", "Landroid/view/LayoutInflater;", "Landroid/view/ViewGroup;", "", "getBindingInflater", "()Lkotlin/jvm/functions/Function3;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "viewModel", "Lcom/thor/app/gui/fragments/shop/sgu/SguShopViewModel;", "getViewModel", "()Lcom/thor/app/gui/fragments/shop/sgu/SguShopViewModel;", "viewModel$delegate", "handleError", "", "message", "", "showToUser", "hideRefreshing", "init", "initAdapter", "initSwipeContainer", "observeUiState", "onDestroyView", "onMessageEvent", "event", "Lcom/thor/app/bus/events/shop/sgu/UploadSguSoundPackageSuccessfulEvent;", "onResume", "showRefreshing", "updateShop", "models", "", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "skus", "Lcom/android/billingclient/api/SkuDetails;", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class SguShopFragment extends Hilt_SguShopFragment<FragmentSguShopBinding> {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* renamed from: adapter$delegate, reason: from kotlin metadata */
    private final Lazy adapter;

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager = LazyKt.lazy(new Function0<BleManager>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopFragment$bleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0.requireContext());
        }
    });

    /* renamed from: handler$delegate, reason: from kotlin metadata */
    private final Lazy handler = LazyKt.lazy(new Function0<Handler>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopFragment$handler$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Handler invoke() {
            return new Handler(Looper.getMainLooper());
        }
    });
    private final Function3<LayoutInflater, ViewGroup, Boolean, FragmentSguShopBinding> bindingInflater = SguShopFragment$bindingInflater$1.INSTANCE;

    public SguShopFragment() {
        final SguShopFragment sguShopFragment = this;
        final Function0<Fragment> function0 = new Function0<Fragment>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopFragment$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Fragment invoke() {
                return sguShopFragment;
            }
        };
        final Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<ViewModelStoreOwner>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopFragment$special$$inlined$viewModels$default$2
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
        this.viewModel = FragmentViewModelLazyKt.createViewModelLazy(sguShopFragment, Reflection.getOrCreateKotlinClass(SguShopViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopFragment$special$$inlined$viewModels$default$3
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
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopFragment$special$$inlined$viewModels$default$4
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
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopFragment$special$$inlined$viewModels$default$5
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
                    defaultViewModelProviderFactory = sguShopFragment.getDefaultViewModelProviderFactory();
                }
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "(owner as? HasDefaultVie…tViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        });
        this.adapter = LazyKt.lazy(new Function0<GroupieAdapter>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopFragment$adapter$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final GroupieAdapter invoke() {
                return new GroupieAdapter();
            }
        });
    }

    /* compiled from: SguShopFragment.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/thor/app/gui/fragments/shop/sgu/SguShopFragment$Companion;", "", "()V", "newInstance", "Lcom/thor/app/gui/fragments/shop/sgu/SguShopFragment;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SguShopFragment newInstance() {
            return new SguShopFragment();
        }
    }

    private final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    private final Handler getHandler() {
        return (Handler) this.handler.getValue();
    }

    @Override // com.thor.app.gui.fragments.base.BaseBindingFragment
    public Function3<LayoutInflater, ViewGroup, Boolean, FragmentSguShopBinding> getBindingInflater() {
        return this.bindingInflater;
    }

    private final SguShopViewModel getViewModel() {
        return (SguShopViewModel) this.viewModel.getValue();
    }

    private final GroupieAdapter getAdapter() {
        return (GroupieAdapter) this.adapter.getValue();
    }

    @Override // com.thor.app.gui.fragments.base.BaseFragment
    public void init() throws SecurityException {
        initSwipeContainer();
        initAdapter();
        observeUiState();
        EventBus.getDefault().register(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        getViewModel().loadData();
        if (getBleManager().getMStateConnected()) {
            return;
        }
        getBleManager().connect();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void initSwipeContainer() {
        SwipeRefreshLayout swipeRefreshLayout = ((FragmentSguShopBinding) getBinding()).swipeContainer;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        swipeRefreshLayout.setColorSchemeColors(ContextKt.fetchAttrValue(contextRequireContext, R.attr.colorAccent));
        ((FragmentSguShopBinding) getBinding()).swipeContainer.setProgressBackgroundColorSchemeResource(R.color.colorRefreshLayoutBackground);
        ((FragmentSguShopBinding) getBinding()).swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopFragment$$ExternalSyntheticLambda1
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                SguShopFragment.initSwipeContainer$lambda$0(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initSwipeContainer$lambda$0(SguShopFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getViewModel().loadData();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void initAdapter() {
        getAdapter().setHasStableIds(true);
        ((FragmentSguShopBinding) getBinding()).recyclerView.setAdapter(getAdapter());
    }

    private final void observeUiState() {
        LiveData<SguShopUIState> shopUiState = getViewModel().getShopUiState();
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        final Function1<SguShopUIState, Unit> function1 = new Function1<SguShopUIState, Unit>() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopFragment.observeUiState.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(SguShopUIState sguShopUIState) {
                invoke2(sguShopUIState);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(SguShopUIState sguShopUIState) {
                if (sguShopUIState instanceof SguShopDataLoading) {
                    SguShopFragment.this.showRefreshing();
                    return;
                }
                if (sguShopUIState instanceof SguShopError) {
                    SguShopError sguShopError = (SguShopError) sguShopUIState;
                    SguShopFragment.this.handleError(sguShopError.getMessage(), sguShopError.getShowToUser());
                } else if (sguShopUIState instanceof SguShopDataLoaded) {
                    SguShopDataLoaded sguShopDataLoaded = (SguShopDataLoaded) sguShopUIState;
                    SguShopFragment.this.updateShop(sguShopDataLoaded.getModels(), sguShopDataLoaded.getSkus());
                }
            }
        };
        shopUiState.observe(viewLifecycleOwner, new Observer() { // from class: com.thor.app.gui.fragments.shop.sgu.SguShopFragment$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SguShopFragment.observeUiState$lambda$1(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeUiState$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateShop(List<SguSoundSet> models, List<? extends SkuDetails> skus) {
        Object next;
        hideRefreshing();
        List<SguSoundSet> list = models;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (SguSoundSet sguSoundSet : list) {
            Iterator<T> it = skus.iterator();
            while (true) {
                if (it.hasNext()) {
                    next = it.next();
                    if (Intrinsics.areEqual(((SkuDetails) next).getSku(), sguSoundSet.getIapIdentifier())) {
                        break;
                    }
                } else {
                    next = null;
                    break;
                }
            }
            arrayList.add(new ShopSguSoundPackageItem(sguSoundSet, (SkuDetails) next));
        }
        getAdapter().replaceAll(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(String message, boolean showToUser) {
        hideRefreshing();
        Timber.INSTANCE.e(message, new Object[0]);
        if (showToUser) {
            Toast.makeText(requireContext(), message, 1).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void showRefreshing() {
        ((FragmentSguShopBinding) getBinding()).swipeContainer.setRefreshing(true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void hideRefreshing() {
        ((FragmentSguShopBinding) getBinding()).swipeContainer.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UploadSguSoundPackageSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        getViewModel().loadData();
        Log.i("FIND", ExifInterface.GPS_MEASUREMENT_3D);
    }

    @Override // com.thor.app.gui.fragments.base.BaseBindingFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
