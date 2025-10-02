package com.thor.app.gui.fragments.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.FragmentNotificationsBinding;
import com.fourksoft.base.ui.adapter.decorators.SpaceItemDecorator;
import com.fourksoft.base.ui.adapter.decorators.SpaceItemForLastDecoration;
import com.fourksoft.base.ui.adapter.listener.OnItemClickListener;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.thor.app.gui.activities.notifications.NotificationsViewModel;
import com.thor.app.gui.adapters.NotificationsRvAdapter;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment;
import com.thor.app.gui.dialog.UpdateFirmwareDialogFragment;
import com.thor.app.gui.fragments.notification.NotificationsFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.SchemaEmergencySituationsManager;
import com.thor.app.utils.extensions.ContextKt;
import com.thor.app.utils.extensions.ViewKt;
import com.thor.basemodule.extensions.MeasureKt;
import com.thor.networkmodule.model.notifications.NotificationInfo;
import com.thor.networkmodule.model.notifications.NotificationType;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import timber.log.Timber;

/* compiled from: NotificationsFragment.kt */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u0000 E2\u00020\u0001:\u0002EFB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\"\u001a\u00020#H\u0002J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0002J\b\u0010(\u001a\u00020#H\u0016J\b\u0010)\u001a\u00020#H\u0002J\b\u0010*\u001a\u00020#H\u0002J\"\u0010+\u001a\u00020#2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-2\b\u0010/\u001a\u0004\u0018\u000100H\u0016J\u0010\u00101\u001a\u00020#2\u0006\u0010&\u001a\u00020'H\u0016J$\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\b\u00106\u001a\u0004\u0018\u0001072\b\u00108\u001a\u0004\u0018\u000109H\u0016J\b\u0010:\u001a\u00020#H\u0016J\b\u0010;\u001a\u00020#H\u0016J\u0010\u0010<\u001a\u00020#2\u0006\u0010=\u001a\u00020>H\u0002J\b\u0010?\u001a\u00020#H\u0002J\b\u0010@\u001a\u00020#H\u0016J\b\u0010A\u001a\u00020#H\u0002J\u001a\u0010B\u001a\u00020#2\u0006\u0010C\u001a\u0002032\b\u00108\u001a\u0004\u0018\u000109H\u0016J\b\u0010D\u001a\u00020#H\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\n\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0018\u001a\u00020\u00198BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\n\u001a\u0004\b\u001a\u0010\u001bR\u001b\u0010\u001d\u001a\u00020\u001e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\n\u001a\u0004\b\u001f\u0010 ¨\u0006G"}, d2 = {"Lcom/thor/app/gui/fragments/notification/NotificationsFragment;", "Lcom/thor/app/gui/fragments/base/BaseFragment;", "()V", "_binding", "Lcom/carsystems/thor/app/databinding/FragmentNotificationsBinding;", "adapter", "Lcom/thor/app/gui/adapters/NotificationsRvAdapter;", "getAdapter", "()Lcom/thor/app/gui/adapters/NotificationsRvAdapter;", "adapter$delegate", "Lkotlin/Lazy;", "binding", "getBinding", "()Lcom/carsystems/thor/app/databinding/FragmentNotificationsBinding;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "mListener", "Lcom/thor/app/gui/fragments/notification/NotificationsFragment$OnLinkItemSelectedListener;", "mLoadedData", "", "mRequestLoadData", "notificationManager", "Landroid/app/NotificationManager;", "getNotificationManager", "()Landroid/app/NotificationManager;", "notificationManager$delegate", "viewModel", "Lcom/thor/app/gui/activities/notifications/NotificationsViewModel;", "getViewModel", "()Lcom/thor/app/gui/activities/notifications/NotificationsViewModel;", "viewModel$delegate", "cancelCurrentNotificationFromStatusBar", "", "createDisableUpdateAlertDialog", "Landroidx/appcompat/app/AlertDialog;", "context", "Landroid/content/Context;", "init", "initAdapter", "initListeners", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onAttach", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onDestroyView", "onDownloadPackage", "selectedShopSoundPackage", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "onLoadData", "onResume", "onUpdateSoftware", "onViewCreated", "view", "openUpdateFirmwareDialog", "Companion", "OnLinkItemSelectedListener", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class NotificationsFragment extends Hilt_NotificationsFragment {
    public static final String BUNDLE_ACTION = "action";

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int REQUEST_SETTINGS = 1001;
    private FragmentNotificationsBinding _binding;
    private OnLinkItemSelectedListener mListener;
    private boolean mLoadedData;

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager>() { // from class: com.thor.app.gui.fragments.notification.NotificationsFragment$bleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0.getContext());
        }
    });

    /* renamed from: notificationManager$delegate, reason: from kotlin metadata */
    private final Lazy notificationManager = LazyKt.lazy(new Function0<NotificationManager>() { // from class: com.thor.app.gui.fragments.notification.NotificationsFragment$notificationManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final NotificationManager invoke() {
            Object systemService = this.this$0.requireActivity().getSystemService("notification");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
            return (NotificationManager) systemService;
        }
    });

    /* renamed from: adapter$delegate, reason: from kotlin metadata */
    private final Lazy adapter = LazyKt.lazy(new Function0<NotificationsRvAdapter>() { // from class: com.thor.app.gui.fragments.notification.NotificationsFragment$adapter$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final NotificationsRvAdapter invoke() {
            return new NotificationsRvAdapter();
        }
    });
    private boolean mRequestLoadData = true;

    /* compiled from: NotificationsFragment.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/thor/app/gui/fragments/notification/NotificationsFragment$OnLinkItemSelectedListener;", "", "onLinkItemSelected", "", "info", "Lcom/thor/networkmodule/model/notifications/NotificationInfo;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnLinkItemSelectedListener {
        void onLinkItemSelected(NotificationInfo info);
    }

    @JvmStatic
    public static final NotificationsFragment newInstance(String str) {
        return INSTANCE.newInstance(str);
    }

    public NotificationsFragment() {
        final NotificationsFragment notificationsFragment = this;
        final Function0 function0 = null;
        this.viewModel = FragmentViewModelLazyKt.createViewModelLazy(notificationsFragment, Reflection.getOrCreateKotlinClass(NotificationsViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.fragments.notification.NotificationsFragment$special$$inlined$activityViewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = notificationsFragment.requireActivity().getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "requireActivity().viewModelStore");
                return viewModelStore;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.fragments.notification.NotificationsFragment$special$$inlined$activityViewModels$default$2
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
                CreationExtras defaultViewModelCreationExtras = notificationsFragment.requireActivity().getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "requireActivity().defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.fragments.notification.NotificationsFragment$special$$inlined$activityViewModels$default$3
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = notificationsFragment.requireActivity().getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "requireActivity().defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FragmentNotificationsBinding getBinding() {
        FragmentNotificationsBinding fragmentNotificationsBinding = this._binding;
        if (fragmentNotificationsBinding != null) {
            return fragmentNotificationsBinding;
        }
        throw new IllegalArgumentException("Required value was null.".toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NotificationsViewModel getViewModel() {
        return (NotificationsViewModel) this.viewModel.getValue();
    }

    private final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    private final NotificationManager getNotificationManager() {
        return (NotificationManager) this.notificationManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NotificationsRvAdapter getAdapter() {
        return (NotificationsRvAdapter) this.adapter.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.thor.app.gui.fragments.notification.Hilt_NotificationsFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.onAttach(context);
        if (context instanceof OnLinkItemSelectedListener) {
            this.mListener = (OnLinkItemSelectedListener) context;
            return;
        }
        throw new ClassCastException(context + " must implement MyListFragment.OnItemSelectedListener");
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = (FragmentNotificationsBinding) DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false);
        View root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return root;
    }

    @Override // com.thor.app.gui.fragments.base.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        SwipeRefreshLayout swipeRefreshLayout = getBinding().swipeContainer;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        swipeRefreshLayout.setColorSchemeColors(ContextKt.fetchAttrValue(contextRequireContext, R.attr.colorAccent));
        getBinding().swipeContainer.setProgressBackgroundColorSchemeResource(R.color.colorRefreshLayoutBackground);
        getBinding().swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.thor.app.gui.fragments.notification.NotificationsFragment$$ExternalSyntheticLambda0
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                NotificationsFragment.onViewCreated$lambda$0(this.f$0);
            }
        });
        this.mRequestLoadData = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$0(NotificationsFragment this$0) {
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
        cancelCurrentNotificationFromStatusBar();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            this.mRequestLoadData = false;
        }
    }

    private final void initListeners() {
        LiveData<List<NotificationInfo>> notificationsListLiveData = getViewModel().getNotificationsListLiveData();
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        final Function1<List<? extends NotificationInfo>, Unit> function1 = new Function1<List<? extends NotificationInfo>, Unit>() { // from class: com.thor.app.gui.fragments.notification.NotificationsFragment.initListeners.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends NotificationInfo> list) {
                invoke2((List<NotificationInfo>) list);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<NotificationInfo> list) {
                NotificationsFragment.this.getAdapter().clearAndAddAll(list);
                NotificationsFragment.this.getBinding().swipeContainer.setRefreshing(false);
            }
        };
        notificationsListLiveData.observe(viewLifecycleOwner, new Observer() { // from class: com.thor.app.gui.fragments.notification.NotificationsFragment$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NotificationsFragment.initListeners$lambda$1(function1, obj);
            }
        });
        getAdapter().setOnItemClickListener(new OnItemClickListener<NotificationInfo>() { // from class: com.thor.app.gui.fragments.notification.NotificationsFragment$initListeners$$inlined$doOnItemClick$1
            @Override // com.fourksoft.base.ui.adapter.listener.OnItemClickListener
            public void onItemClick(View view) {
            }

            @Override // com.fourksoft.base.ui.adapter.listener.OnItemClickListener
            public void onItemClick(NotificationInfo item, View view) {
            }

            @Override // com.fourksoft.base.ui.adapter.listener.OnItemClickListener
            public void onItemClick(NotificationInfo item) {
                NotificationInfo notificationInfo = item;
                if (notificationInfo.getNotificationType() == NotificationType.TYPE_FIRMWARE) {
                    this.this$0.onUpdateSoftware();
                    return;
                }
                this.this$0.getViewModel().set_selectedNotification(notificationInfo);
                this.this$0.getViewModel().clearNotification(notificationInfo.getNotificationId());
                NotificationsFragment.OnLinkItemSelectedListener onLinkItemSelectedListener = this.this$0.mListener;
                if (onLinkItemSelectedListener != null) {
                    onLinkItemSelectedListener.onLinkItemSelected(notificationInfo);
                }
            }
        });
        getAdapter().setOnUpdateClickListener(new Function1<Integer, Unit>() { // from class: com.thor.app.gui.fragments.notification.NotificationsFragment.initListeners.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke(num.intValue());
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final void invoke(int i) {
                List<ShopSoundPackage> list = NotificationsFragment.this.getViewModel().get_shopSoundPackages();
                ShopSoundPackage shopSoundPackage = null;
                if (list != null) {
                    Iterator<T> it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Object next = it.next();
                        if (((ShopSoundPackage) next).getId() == i) {
                            shopSoundPackage = next;
                            break;
                        }
                    }
                    shopSoundPackage = shopSoundPackage;
                }
                if (!(shopSoundPackage != null && shopSoundPackage.isLoadedOnBoard()) || shopSoundPackage.isNeedUpdate()) {
                    if (shopSoundPackage != null) {
                        NotificationsFragment.this.onDownloadPackage(shopSoundPackage);
                    }
                } else {
                    Toast.makeText(NotificationsFragment.this.getContext(), "Sound already installed", 0).show();
                    FragmentActivity activity = NotificationsFragment.this.getActivity();
                    if (activity != null) {
                        activity.onBackPressed();
                    }
                }
            }
        });
        getBinding().textViewUpdateAll.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.fragments.notification.NotificationsFragment$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationsFragment.initListeners$lambda$3(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$3(NotificationsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getViewModel().saveListToUpdate();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println((Object) "onDestroy");
    }

    private final void initAdapter() {
        getBinding().recyclerView.setItemAnimator(new DefaultItemAnimator());
        getBinding().recyclerView.setAdapter(getAdapter());
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), 1, false));
        getBinding().recyclerView.addItemDecoration(new SpaceItemDecorator(0, MeasureKt.getDp(5), 0, 0, 13, null));
        getBinding().recyclerView.addItemDecoration(new SpaceItemForLastDecoration(0, MeasureKt.getDp(5), 0, 0, 13, null));
        getBinding().nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: com.thor.app.gui.fragments.notification.NotificationsFragment$$ExternalSyntheticLambda1
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
                NotificationsFragment.initAdapter$lambda$4(this.f$0, nestedScrollView, i, i2, i3, i4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initAdapter$lambda$4(NotificationsFragment this$0, NestedScrollView v, int i, int i2, int i3, int i4) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(v, "v");
        if (!this$0.getBinding().nestedScrollView.canScrollVertically(1)) {
            TextView textView = this$0.getBinding().textViewUpdateAll;
            Intrinsics.checkNotNullExpressionValue(textView, "binding.textViewUpdateAll");
            ViewKt.hide(textView);
        } else {
            TextView textView2 = this$0.getBinding().textViewUpdateAll;
            Intrinsics.checkNotNullExpressionValue(textView2, "binding.textViewUpdateAll");
            ViewKt.show(textView2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onUpdateSoftware() {
        Timber.INSTANCE.i("onUpdateSoftware", new Object[0]);
        if (getBleManager().getMStateConnected()) {
            if (SchemaEmergencySituationsManager.INSTANCE.from(getContext()).checkFirmwareVersionOnDifference() != null) {
                openUpdateFirmwareDialog();
                return;
            }
            getViewModel().disableFirmwareNotification();
            onLoadData();
            Context contextRequireContext = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
            createDisableUpdateAlertDialog(contextRequireContext).show();
            return;
        }
        DialogManager dialogManager = DialogManager.INSTANCE;
        Context contextRequireContext2 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext2, "requireContext()");
        dialogManager.createNoConnectionToBoardAlertDialog(contextRequireContext2).show();
    }

    private final void onLoadData() {
        Timber.INSTANCE.i("onLoadData", new Object[0]);
        if (this.mLoadedData) {
            return;
        }
        getViewModel().fetchAllNotifications();
    }

    protected final void openUpdateFirmwareDialog() {
        UpdateFirmwareDialogFragment updateFirmwareDialogFragmentNewInstance = UpdateFirmwareDialogFragment.INSTANCE.newInstance();
        if (updateFirmwareDialogFragmentNewInstance.isAdded()) {
            return;
        }
        updateFirmwareDialogFragmentNewInstance.show(getParentFragmentManager(), "UpdateFirmwareDialogFragment");
    }

    private final AlertDialog createDisableUpdateAlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2131886084);
        String string = getString(R.string.text_latest_update_firmware);
        Intrinsics.checkNotNullExpressionValue(string, "getString(\n            R…update_firmware\n        )");
        builder.setMessage(string).setPositiveButton(android.R.string.yes, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onDownloadPackage(ShopSoundPackage selectedShopSoundPackage) {
        if (BleManager.INSTANCE.from(requireContext()).getMStateConnected()) {
            DownloadSoundPackageDialogFragment downloadSoundPackageDialogFragmentNewInstance = DownloadSoundPackageDialogFragment.INSTANCE.newInstance(selectedShopSoundPackage);
            if (downloadSoundPackageDialogFragmentNewInstance.isAdded()) {
                return;
            }
            downloadSoundPackageDialogFragmentNewInstance.show(getParentFragmentManager(), "DownloadSoundPackageDialogFragment");
            return;
        }
        DialogManager dialogManager = DialogManager.INSTANCE;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        dialogManager.createNoConnectionToBoardAlertDialog(contextRequireContext).show();
    }

    private final void cancelCurrentNotificationFromStatusBar() {
        getNotificationManager().cancelAll();
    }

    /* compiled from: NotificationsFragment.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/thor/app/gui/fragments/notification/NotificationsFragment$Companion;", "", "()V", "BUNDLE_ACTION", "", "REQUEST_SETTINGS", "", "newInstance", "Lcom/thor/app/gui/fragments/notification/NotificationsFragment;", "action", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ NotificationsFragment newInstance$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.newInstance(str);
        }

        @JvmStatic
        public final NotificationsFragment newInstance(String action) {
            NotificationsFragment notificationsFragment = new NotificationsFragment();
            if (action != null) {
                Bundle bundle = new Bundle();
                bundle.putString("action", action);
                notificationsFragment.setArguments(bundle);
            }
            return notificationsFragment;
        }
    }
}
