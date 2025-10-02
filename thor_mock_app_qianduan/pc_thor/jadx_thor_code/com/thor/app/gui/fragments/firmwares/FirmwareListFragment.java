package com.thor.app.gui.fragments.firmwares;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.FragmentFirmwareListBinding;
import com.fourksoft.base.ui.adapter.decorators.SpaceItemDecorator;
import com.fourksoft.base.ui.adapter.decorators.SpaceItemForLastDecoration;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.thor.app.gui.activities.testers.FirmwareListViewModel;
import com.thor.app.gui.dialog.UpdateFirmwareDialogFragment;
import com.thor.app.gui.fragments.base.BaseFragment;
import com.thor.app.utils.extensions.ContextKt;
import com.thor.basemodule.extensions.MeasureKt;
import com.thor.networkmodule.model.firmware.FirmwareProfileList;
import com.thor.networkmodule.model.firmware.FirmwareProfileShort;
import com.xwray.groupie.GroupieAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import timber.log.Timber;

/* compiled from: FirmwareListFragment.kt */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 12\u00020\u0001:\u00011B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0017H\u0002J\b\u0010\u0019\u001a\u00020\u0017H\u0002J\"\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\u0010\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\"H\u0002J$\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\b\u0010'\u001a\u0004\u0018\u00010(2\b\u0010)\u001a\u0004\u0018\u00010*H\u0016J\b\u0010+\u001a\u00020\u0017H\u0016J\b\u0010,\u001a\u00020\u0017H\u0002J\b\u0010-\u001a\u00020\u0017H\u0016J\u001a\u0010.\u001a\u00020\u00172\u0006\u0010/\u001a\u00020$2\b\u0010)\u001a\u0004\u0018\u00010*H\u0016J\u0010\u00100\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\"H\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\n\u001a\u0004\b\u0013\u0010\u0014¨\u00062"}, d2 = {"Lcom/thor/app/gui/fragments/firmwares/FirmwareListFragment;", "Lcom/thor/app/gui/fragments/base/BaseFragment;", "()V", "_binding", "Lcom/carsystems/thor/app/databinding/FragmentFirmwareListBinding;", "adapter", "Lcom/xwray/groupie/GroupieAdapter;", "getAdapter", "()Lcom/xwray/groupie/GroupieAdapter;", "adapter$delegate", "Lkotlin/Lazy;", "binding", "getBinding", "()Lcom/carsystems/thor/app/databinding/FragmentFirmwareListBinding;", "mLoadedData", "", "mRequestLoadData", "viewModel", "Lcom/thor/app/gui/activities/testers/FirmwareListViewModel;", "getViewModel", "()Lcom/thor/app/gui/activities/testers/FirmwareListViewModel;", "viewModel$delegate", "init", "", "initAdapter", "initListeners", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onClick", "firmware", "Lcom/thor/networkmodule/model/firmware/FirmwareProfileShort;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onLoadData", "onResume", "onViewCreated", "view", "openUpdateFirmwareDialog", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class FirmwareListFragment extends BaseFragment {
    public static final String BUNDLE_ACTION = "action";

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int REQUEST_SETTINGS = 1001;
    private FragmentFirmwareListBinding _binding;
    private boolean mLoadedData;

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    /* renamed from: adapter$delegate, reason: from kotlin metadata */
    private final Lazy adapter = LazyKt.lazy(new Function0<GroupieAdapter>() { // from class: com.thor.app.gui.fragments.firmwares.FirmwareListFragment$adapter$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final GroupieAdapter invoke() {
            return new GroupieAdapter();
        }
    });
    private boolean mRequestLoadData = true;

    @JvmStatic
    public static final FirmwareListFragment newInstance(String str) {
        return INSTANCE.newInstance(str);
    }

    public FirmwareListFragment() {
        final FirmwareListFragment firmwareListFragment = this;
        final Function0 function0 = null;
        this.viewModel = FragmentViewModelLazyKt.createViewModelLazy(firmwareListFragment, Reflection.getOrCreateKotlinClass(FirmwareListViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.fragments.firmwares.FirmwareListFragment$special$$inlined$activityViewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = firmwareListFragment.requireActivity().getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "requireActivity().viewModelStore");
                return viewModelStore;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.fragments.firmwares.FirmwareListFragment$special$$inlined$activityViewModels$default$2
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
                CreationExtras defaultViewModelCreationExtras = firmwareListFragment.requireActivity().getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "requireActivity().defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.fragments.firmwares.FirmwareListFragment$special$$inlined$activityViewModels$default$3
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = firmwareListFragment.requireActivity().getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "requireActivity().defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        });
    }

    private final FragmentFirmwareListBinding getBinding() {
        FragmentFirmwareListBinding fragmentFirmwareListBinding = this._binding;
        if (fragmentFirmwareListBinding != null) {
            return fragmentFirmwareListBinding;
        }
        throw new IllegalArgumentException("Required value was null.".toString());
    }

    private final FirmwareListViewModel getViewModel() {
        return (FirmwareListViewModel) this.viewModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final GroupieAdapter getAdapter() {
        return (GroupieAdapter) this.adapter.getValue();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = (FragmentFirmwareListBinding) DataBindingUtil.inflate(inflater, R.layout.fragment_firmware_list, container, false);
        View root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return root;
    }

    @Override // com.thor.app.gui.fragments.base.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        getBinding().setModel(getViewModel());
        SwipeRefreshLayout swipeRefreshLayout = getBinding().swipeContainer;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        swipeRefreshLayout.setColorSchemeColors(ContextKt.fetchAttrValue(contextRequireContext, R.attr.colorAccent));
        getBinding().swipeContainer.setProgressBackgroundColorSchemeResource(R.color.colorRefreshLayoutBackground);
        getBinding().swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.thor.app.gui.fragments.firmwares.FirmwareListFragment$$ExternalSyntheticLambda1
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                FirmwareListFragment.onViewCreated$lambda$0(this.f$0);
            }
        });
        this.mRequestLoadData = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$0(FirmwareListFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mLoadedData = false;
        this$0.onLoadData();
    }

    @Override // com.thor.app.gui.fragments.base.BaseFragment
    public void init() {
        initListeners();
        initAdapter();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        this._binding = null;
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Timber.INSTANCE.i("onResume", new Object[0]);
        this.mLoadedData = false;
        if (this.mRequestLoadData) {
            onLoadData();
        }
        this.mRequestLoadData = true;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            this.mRequestLoadData = false;
        }
    }

    private final void initListeners() {
        final Function1<FirmwareProfileList, Unit> function1 = new Function1<FirmwareProfileList, Unit>() { // from class: com.thor.app.gui.fragments.firmwares.FirmwareListFragment.initListeners.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirmwareProfileList firmwareProfileList) {
                invoke2(firmwareProfileList);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirmwareProfileList firmwareProfileList) {
                System.out.println(firmwareProfileList.getFirmwares());
                GroupieAdapter adapter = FirmwareListFragment.this.getAdapter();
                List<FirmwareProfileShort> firmwares = firmwareProfileList.getFirmwares();
                FirmwareListFragment firmwareListFragment = FirmwareListFragment.this;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(firmwares, 10));
                Iterator<T> it = firmwares.iterator();
                while (it.hasNext()) {
                    arrayList.add(new FirmwareInfoItem((FirmwareProfileShort) it.next(), new FirmwareListFragment$initListeners$1$1$1(firmwareListFragment)));
                }
                adapter.replaceAll(arrayList);
            }
        };
        getViewModel().getFirmwareListLiveData().observe(this, new Observer() { // from class: com.thor.app.gui.fragments.firmwares.FirmwareListFragment$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FirmwareListFragment.initListeners$lambda$1(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void initAdapter() {
        getBinding().recyclerView.setItemAnimator(new DefaultItemAnimator());
        getAdapter().setHasStableIds(true);
        getBinding().recyclerView.setAdapter(getAdapter());
        getBinding().recyclerView.addItemDecoration(new SpaceItemDecorator(0, MeasureKt.getDp(5), 0, 0, 13, null));
        getBinding().recyclerView.addItemDecoration(new SpaceItemForLastDecoration(0, MeasureKt.getDp(5), 0, 0, 13, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClick(FirmwareProfileShort firmware) {
        openUpdateFirmwareDialog(firmware);
    }

    private final void onLoadData() {
        Timber.INSTANCE.i("onLoadData", new Object[0]);
        if (this.mLoadedData) {
            return;
        }
        getViewModel().fetchAllFirmwareList();
    }

    protected final void openUpdateFirmwareDialog(FirmwareProfileShort firmware) {
        Intrinsics.checkNotNullParameter(firmware, "firmware");
        UpdateFirmwareDialogFragment updateFirmwareDialogFragmentNewInstance = UpdateFirmwareDialogFragment.INSTANCE.newInstance(firmware);
        if (updateFirmwareDialogFragmentNewInstance.isAdded()) {
            return;
        }
        updateFirmwareDialogFragmentNewInstance.show(getChildFragmentManager(), "UpdateFirmwareDialogFragment");
    }

    /* compiled from: FirmwareListFragment.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/thor/app/gui/fragments/firmwares/FirmwareListFragment$Companion;", "", "()V", "BUNDLE_ACTION", "", "REQUEST_SETTINGS", "", "newInstance", "Lcom/thor/app/gui/fragments/firmwares/FirmwareListFragment;", "action", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ FirmwareListFragment newInstance$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.newInstance(str);
        }

        @JvmStatic
        public final FirmwareListFragment newInstance(String action) {
            FirmwareListFragment firmwareListFragment = new FirmwareListFragment();
            if (action != null) {
                Bundle bundle = new Bundle();
                bundle.putString("action", action);
                firmwareListFragment.setArguments(bundle);
            }
            return firmwareListFragment;
        }
    }
}
