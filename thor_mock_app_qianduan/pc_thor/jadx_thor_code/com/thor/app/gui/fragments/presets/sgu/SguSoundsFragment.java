package com.thor.app.gui.fragments.presets.sgu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.FragmentSguSoundsBinding;
import com.thor.app.bus.events.CheckPlaceholderHintEvent;
import com.thor.app.bus.events.FormatFlashExecuteEvent;
import com.thor.app.bus.events.shop.sgu.StopPlayingSguSoundEvent;
import com.thor.app.gui.activities.main.MainActivityViewModel;
import com.thor.app.gui.adapters.itemtouch.ItemMoveCallback;
import com.thor.app.gui.adapters.main.SguSoundPackagesDiffUtilCallback;
import com.thor.app.gui.adapters.main.SguSoundPackagesRvAdapter;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.BleManager;
import com.thor.app.utils.extensions.ContextKt;
import com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import com.thor.networkmodule.model.responses.sgu.SguSoundConfig;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
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

/* compiled from: SguSoundsFragment.kt */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\b\u0007\u0018\u0000 :2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001:B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020!H\u0016J\b\u0010#\u001a\u00020!H\u0002J\b\u0010$\u001a\u00020!H\u0002J\b\u0010%\u001a\u00020!H\u0002J\u0010\u0010&\u001a\u00020!2\u0006\u0010'\u001a\u00020(H\u0002J\b\u0010)\u001a\u00020!H\u0016J\b\u0010*\u001a\u00020!H\u0002J\u0010\u0010+\u001a\u00020!2\u0006\u0010,\u001a\u00020-H\u0007J\u0010\u0010+\u001a\u00020!2\u0006\u0010,\u001a\u00020.H\u0007J\u0010\u0010+\u001a\u00020!2\u0006\u0010,\u001a\u00020/H\u0007J\u0010\u00100\u001a\u00020!2\u0006\u0010'\u001a\u00020(H\u0002J\b\u00101\u001a\u00020!H\u0016J\u001a\u00102\u001a\u00020!2\b\u00103\u001a\u0004\u0018\u0001042\u0006\u00105\u001a\u00020\u000eH\u0002J\b\u00106\u001a\u00020!H\u0002J\u0016\u00107\u001a\u00020!2\f\u00108\u001a\b\u0012\u0004\u0012\u00020(09H\u0002R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R.\u0010\n\u001a\u001c\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\t\u001a\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0016\u001a\u00020\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\t\u001a\u0004\b\u0018\u0010\u0019R\u001b\u0010\u001b\u001a\u00020\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\t\u001a\u0004\b\u001d\u0010\u001e¨\u0006;"}, d2 = {"Lcom/thor/app/gui/fragments/presets/sgu/SguSoundsFragment;", "Lcom/thor/app/gui/fragments/base/BaseBindingFragment;", "Lcom/carsystems/thor/app/databinding/FragmentSguSoundsBinding;", "()V", "activityViewModel", "Lcom/thor/app/gui/activities/main/MainActivityViewModel;", "getActivityViewModel", "()Lcom/thor/app/gui/activities/main/MainActivityViewModel;", "activityViewModel$delegate", "Lkotlin/Lazy;", "bindingInflater", "Lkotlin/Function3;", "Landroid/view/LayoutInflater;", "Landroid/view/ViewGroup;", "", "getBindingInflater", "()Lkotlin/jvm/functions/Function3;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "soundsAdapter", "Lcom/thor/app/gui/adapters/main/SguSoundPackagesRvAdapter;", "getSoundsAdapter", "()Lcom/thor/app/gui/adapters/main/SguSoundPackagesRvAdapter;", "soundsAdapter$delegate", "viewModel", "Lcom/thor/app/gui/fragments/presets/sgu/SguSoundsViewModel;", "getViewModel", "()Lcom/thor/app/gui/fragments/presets/sgu/SguSoundsViewModel;", "viewModel$delegate", "hideRefreshing", "", "init", "initAdapter", "initSwipeContainer", "observeUiState", "onConfigSound", "sound", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "onDestroyView", "onDismissConfigSound", "onMessageEvent", "event", "Lcom/thor/app/bus/events/CheckPlaceholderHintEvent;", "Lcom/thor/app/bus/events/FormatFlashExecuteEvent;", "Lcom/thor/app/bus/events/shop/sgu/StopPlayingSguSoundEvent;", "onPlaySound", "onResume", "showError", "message", "", "showToUser", "showRefreshing", "updateData", "data", "", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class SguSoundsFragment extends Hilt_SguSoundsFragment<FragmentSguSoundsBinding> {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* renamed from: activityViewModel$delegate, reason: from kotlin metadata */
    private final Lazy activityViewModel;
    private final Function3<LayoutInflater, ViewGroup, Boolean, FragmentSguSoundsBinding> bindingInflater = SguSoundsFragment$bindingInflater$1.INSTANCE;

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager = LazyKt.lazy(new Function0<BleManager>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment$bleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0.requireContext());
        }
    });

    /* renamed from: soundsAdapter$delegate, reason: from kotlin metadata */
    private final Lazy soundsAdapter = LazyKt.lazy(new Function0<SguSoundPackagesRvAdapter>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment$soundsAdapter$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final SguSoundPackagesRvAdapter invoke() {
            return new SguSoundPackagesRvAdapter(new SguSoundPackagesDiffUtilCallback());
        }
    });

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    public SguSoundsFragment() {
        final SguSoundsFragment sguSoundsFragment = this;
        final Function0 function0 = null;
        this.viewModel = FragmentViewModelLazyKt.createViewModelLazy(sguSoundsFragment, Reflection.getOrCreateKotlinClass(SguSoundsViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment$special$$inlined$activityViewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = sguSoundsFragment.requireActivity().getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "requireActivity().viewModelStore");
                return viewModelStore;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment$special$$inlined$activityViewModels$default$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function02 = function0;
                if (function02 != null && (creationExtras = (CreationExtras) function02.invoke()) != null) {
                    return creationExtras;
                }
                CreationExtras defaultViewModelCreationExtras = sguSoundsFragment.requireActivity().getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "requireActivity().defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment$special$$inlined$activityViewModels$default$3
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = sguSoundsFragment.requireActivity().getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "requireActivity().defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        });
        this.activityViewModel = FragmentViewModelLazyKt.createViewModelLazy(sguSoundsFragment, Reflection.getOrCreateKotlinClass(MainActivityViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment$special$$inlined$activityViewModels$default$4
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = sguSoundsFragment.requireActivity().getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "requireActivity().viewModelStore");
                return viewModelStore;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment$special$$inlined$activityViewModels$default$5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function02 = function0;
                if (function02 != null && (creationExtras = (CreationExtras) function02.invoke()) != null) {
                    return creationExtras;
                }
                CreationExtras defaultViewModelCreationExtras = sguSoundsFragment.requireActivity().getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "requireActivity().defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment$special$$inlined$activityViewModels$default$6
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = sguSoundsFragment.requireActivity().getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "requireActivity().defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        });
    }

    /* compiled from: SguSoundsFragment.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/thor/app/gui/fragments/presets/sgu/SguSoundsFragment$Companion;", "", "()V", "newInstance", "Lcom/thor/app/gui/fragments/presets/sgu/SguSoundsFragment;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SguSoundsFragment newInstance() {
            return new SguSoundsFragment();
        }
    }

    @Override // com.thor.app.gui.fragments.base.BaseBindingFragment
    public Function3<LayoutInflater, ViewGroup, Boolean, FragmentSguSoundsBinding> getBindingInflater() {
        return this.bindingInflater;
    }

    private final SguSoundsViewModel getViewModel() {
        return (SguSoundsViewModel) this.viewModel.getValue();
    }

    private final MainActivityViewModel getActivityViewModel() {
        return (MainActivityViewModel) this.activityViewModel.getValue();
    }

    private final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    private final SguSoundPackagesRvAdapter getSoundsAdapter() {
        return (SguSoundPackagesRvAdapter) this.soundsAdapter.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.thor.app.gui.fragments.base.BaseFragment
    public void init() throws SecurityException {
        initSwipeContainer();
        initAdapter();
        observeUiState();
        ((FragmentSguSoundsBinding) getBinding()).setModel(getActivityViewModel());
        EventBus.getDefault().register(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        getViewModel().loadData();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void initSwipeContainer() {
        SwipeRefreshLayout swipeRefreshLayout = ((FragmentSguSoundsBinding) getBinding()).swipeContainer;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        swipeRefreshLayout.setColorSchemeColors(ContextKt.fetchAttrValue(contextRequireContext, R.attr.colorAccent));
        ((FragmentSguSoundsBinding) getBinding()).swipeContainer.setProgressBackgroundColorSchemeResource(R.color.colorRefreshLayoutBackground);
        ((FragmentSguSoundsBinding) getBinding()).swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment$$ExternalSyntheticLambda0
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                SguSoundsFragment.initSwipeContainer$lambda$0(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initSwipeContainer$lambda$0(SguSoundsFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getViewModel().loadData();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void initAdapter() {
        ((FragmentSguSoundsBinding) getBinding()).recyclerView.setItemAnimator(new DefaultItemAnimator());
        ((FragmentSguSoundsBinding) getBinding()).recyclerView.setAdapter(getSoundsAdapter());
        getSoundsAdapter().setRecyclerView(((FragmentSguSoundsBinding) getBinding()).recyclerView);
        Context context = getContext();
        if (context != null) {
            getSoundsAdapter().enableSyncData(context);
        }
        ((FragmentSguSoundsBinding) getBinding()).nestedScrollView.setOnScrollChangeListener(getSoundsAdapter().getOnNestedScrollListener());
        ItemMoveCallback itemMoveCallback = new ItemMoveCallback(getSoundsAdapter());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemMoveCallback);
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        if (com.thor.basemodule.extensions.ContextKt.isCarUIMode(contextRequireContext)) {
            itemMoveCallback.setIsLongPressDragEnabled(false);
        }
        getSoundsAdapter().setOnItemClickListener(new RecyclerCollectionAdapter.OnItemClickListener<SguSound>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment$initAdapter$$inlined$addItemClickListener$default$1
            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(View view) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(SguSound item, int position) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(SguSound item, View view) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(SguSound item) {
                SguSound it = item;
                SguSoundsFragment sguSoundsFragment = this.this$0;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                sguSoundsFragment.onPlaySound(it);
                this.this$0.onDismissConfigSound();
            }
        });
        getSoundsAdapter().setOnActionConfigListener(new RecyclerCollectionAdapter.OnRecyclerActionConfigListener() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment$initAdapter$$inlined$doOnActionConfig$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnRecyclerActionConfigListener
            public final void onConfig(M m) {
                SguSound it = (SguSound) m;
                SguSoundsFragment sguSoundsFragment = this.this$0;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                sguSoundsFragment.onConfigSound(it);
            }
        });
        itemTouchHelper.attachToRecyclerView(((FragmentSguSoundsBinding) getBinding()).recyclerView);
    }

    private final void observeUiState() {
        LiveData<SguSoundsUIState> uiState = getViewModel().getUiState();
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        final Function1<SguSoundsUIState, Unit> function1 = new Function1<SguSoundsUIState, Unit>() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment.observeUiState.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(SguSoundsUIState sguSoundsUIState) {
                invoke2(sguSoundsUIState);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(SguSoundsUIState sguSoundsUIState) {
                if (sguSoundsUIState instanceof SguSoundsDataLoading) {
                    SguSoundsFragment.this.showRefreshing();
                    return;
                }
                if (sguSoundsUIState instanceof SguSoundsError) {
                    SguSoundsError sguSoundsError = (SguSoundsError) sguSoundsUIState;
                    SguSoundsFragment.this.showError(sguSoundsError.getMessage(), sguSoundsError.getShowToUser());
                } else if (sguSoundsUIState instanceof SguSoundsDataLoaded) {
                    SguSoundsFragment.this.updateData(((SguSoundsDataLoaded) sguSoundsUIState).getData());
                }
            }
        };
        uiState.observe(viewLifecycleOwner, new Observer() { // from class: com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SguSoundsFragment.observeUiState$lambda$4(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeUiState$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateData(List<SguSound> data) {
        hideRefreshing();
        getSoundsAdapter().updateAll(data);
        getActivityViewModel().getMoreInfo().set(getSoundsAdapter().getItemCount() < 3);
        EventBus.getDefault().post(new CheckPlaceholderHintEvent());
        getActivityViewModel().getIsInstalledSgu().set(!r5.isEmpty());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onConfigSound(SguSound sound) {
        getActivityViewModel().getIsSguSoundConfig().set(true);
        getActivityViewModel().isSguDriveSelect().postValue(Boolean.valueOf(sound.getDriveSelect()));
        getViewModel().getSelectedSguSound().set(sound);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onDismissConfigSound() {
        getActivityViewModel().getIsSguSoundConfig().set(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onPlaySound(SguSound sound) {
        SguSoundsViewModel viewModel = getViewModel();
        SguSoundConfig sguSoundConfig = getActivityViewModel().getSguConfig().get();
        Intrinsics.checkNotNull(sguSoundConfig);
        viewModel.playSguSound(sound, sguSoundConfig);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showError(String message, boolean showToUser) {
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
        ((FragmentSguSoundsBinding) getBinding()).swipeContainer.setRefreshing(true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void hideRefreshing() {
        ((FragmentSguSoundsBinding) getBinding()).swipeContainer.setRefreshing(false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(CheckPlaceholderHintEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (isAdded()) {
            if (!getBleManager().isBleEnabledAndDeviceConnected() && getSoundsAdapter().getItemCount() == 0) {
                ((FragmentSguSoundsBinding) getBinding()).textViewNoDevices.setText(getString(R.string.text_no_found_devices));
                ((FragmentSguSoundsBinding) getBinding()).textViewNoDevices.setVisibility(0);
            } else if (getSoundsAdapter().getItemCount() == 0) {
                ((FragmentSguSoundsBinding) getBinding()).textViewNoDevices.setText(getString(R.string.text_no_sound_presets));
                ((FragmentSguSoundsBinding) getBinding()).textViewNoDevices.setVisibility(0);
            } else {
                ((FragmentSguSoundsBinding) getBinding()).textViewNoDevices.setVisibility(8);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(StopPlayingSguSoundEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        getViewModel().stopPlayingSguSound();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(FormatFlashExecuteEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        getSoundsAdapter().clearAndAddAll(CollectionsKt.emptyList());
    }

    @Override // com.thor.app.gui.fragments.base.BaseBindingFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
