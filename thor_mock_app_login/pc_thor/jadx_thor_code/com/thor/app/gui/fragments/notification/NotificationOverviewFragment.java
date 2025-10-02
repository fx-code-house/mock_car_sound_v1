package com.thor.app.gui.fragments.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.FragmentNotificationOverviewBinding;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.thor.app.bus.events.shop.main.UploadSoundPackageSuccessfulEvent;
import com.thor.app.gui.activities.notifications.NotificationsViewModel;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment;
import com.thor.app.managers.BleManager;
import com.thor.networkmodule.model.notifications.NotificationDetails;
import com.thor.networkmodule.model.notifications.NotificationInfo;
import com.thor.networkmodule.model.notifications.NotificationType;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: NotificationOverviewFragment.kt */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 -2\u00020\u0001:\u0001-B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0011H\u0002J\u0012\u0010\u0013\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\"\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J$\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\"\u001a\u00020\u0011H\u0016J\b\u0010#\u001a\u00020\u0011H\u0016J\u0010\u0010$\u001a\u00020\u00112\u0006\u0010%\u001a\u00020&H\u0002J\u0010\u0010'\u001a\u00020\u00112\u0006\u0010(\u001a\u00020)H\u0007J\b\u0010*\u001a\u00020\u0011H\u0016J\u001a\u0010+\u001a\u00020\u00112\u0006\u0010,\u001a\u00020\u001d2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\r¨\u0006."}, d2 = {"Lcom/thor/app/gui/fragments/notification/NotificationOverviewFragment;", "Lcom/thor/app/gui/fragments/base/BaseFragment;", "()V", "_binding", "Lcom/carsystems/thor/app/databinding/FragmentNotificationOverviewBinding;", "binding", "getBinding", "()Lcom/carsystems/thor/app/databinding/FragmentNotificationOverviewBinding;", "mRequestLoadData", "", "viewModel", "Lcom/thor/app/gui/activities/notifications/NotificationsViewModel;", "getViewModel", "()Lcom/thor/app/gui/activities/notifications/NotificationsViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "init", "", "initListeners", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "onDestroy", "onDestroyView", "onDownloadPackage", "selectedShopSoundPackage", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "onMessageEvent", "event", "Lcom/thor/app/bus/events/shop/main/UploadSoundPackageSuccessfulEvent;", "onResume", "onViewCreated", "view", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class NotificationOverviewFragment extends Hilt_NotificationOverviewFragment {
    public static final String BUNDLE_ACTION = "action";

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int REQUEST_SETTINGS = 1001;
    private FragmentNotificationOverviewBinding _binding;
    private boolean mRequestLoadData = true;

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    /* compiled from: NotificationOverviewFragment.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[NotificationType.values().length];
            try {
                iArr[NotificationType.TYPE_INFORMATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[NotificationType.TYPE_SOUND_PACKAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @JvmStatic
    public static final NotificationOverviewFragment newInstance(String str) {
        return INSTANCE.newInstance(str);
    }

    public NotificationOverviewFragment() {
        final NotificationOverviewFragment notificationOverviewFragment = this;
        final Function0 function0 = null;
        this.viewModel = FragmentViewModelLazyKt.createViewModelLazy(notificationOverviewFragment, Reflection.getOrCreateKotlinClass(NotificationsViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.fragments.notification.NotificationOverviewFragment$special$$inlined$activityViewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = notificationOverviewFragment.requireActivity().getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "requireActivity().viewModelStore");
                return viewModelStore;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.fragments.notification.NotificationOverviewFragment$special$$inlined$activityViewModels$default$2
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
                CreationExtras defaultViewModelCreationExtras = notificationOverviewFragment.requireActivity().getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "requireActivity().defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.fragments.notification.NotificationOverviewFragment$special$$inlined$activityViewModels$default$3
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = notificationOverviewFragment.requireActivity().getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "requireActivity().defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        });
    }

    private final FragmentNotificationOverviewBinding getBinding() {
        FragmentNotificationOverviewBinding fragmentNotificationOverviewBinding = this._binding;
        if (fragmentNotificationOverviewBinding != null) {
            return fragmentNotificationOverviewBinding;
        }
        throw new IllegalArgumentException("Required value was null.".toString());
    }

    private final NotificationsViewModel getViewModel() {
        return (NotificationsViewModel) this.viewModel.getValue();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = (FragmentNotificationOverviewBinding) DataBindingUtil.inflate(inflater, R.layout.fragment_notification_overview, container, false);
        View root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return root;
    }

    @Override // com.thor.app.gui.fragments.base.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        getBinding().setModel(getViewModel().get_selectedNotification());
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) throws SecurityException {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override // com.thor.app.gui.fragments.base.BaseFragment
    public void init() {
        initListeners();
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
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            this.mRequestLoadData = false;
        }
    }

    private final void initListeners() {
        getBinding().textViewClick.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.fragments.notification.NotificationOverviewFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationOverviewFragment.initListeners$lambda$3(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void initListeners$lambda$3(NotificationOverviewFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        NotificationInfo notificationInfo = this$0.getViewModel().get_selectedNotification();
        ShopSoundPackage shopSoundPackage = null;
        NotificationType notificationType = notificationInfo != null ? notificationInfo.getNotificationType() : null;
        int i = notificationType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[notificationType.ordinal()];
        if (i == 1) {
            FragmentActivity activity = this$0.getActivity();
            if (activity != null) {
                activity.onBackPressed();
                return;
            }
            return;
        }
        if (i == 2) {
            NotificationInfo notificationInfo2 = this$0.getViewModel().get_selectedNotification();
            if (notificationInfo2 != null) {
                Log.d("NotificationOverviewFragment", notificationInfo2.toString());
                List<ShopSoundPackage> list = this$0.getViewModel().get_shopSoundPackages();
                if (list != null) {
                    Iterator<T> it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Object next = it.next();
                        ShopSoundPackage shopSoundPackage2 = (ShopSoundPackage) next;
                        NotificationDetails details = notificationInfo2.getDetails();
                        if (details != null && shopSoundPackage2.getId() == details.getIdSoundPkg()) {
                            shopSoundPackage = next;
                            break;
                        }
                    }
                    shopSoundPackage = shopSoundPackage;
                }
                if (!(shopSoundPackage != null && shopSoundPackage.isLoadedOnBoard()) || shopSoundPackage.isNeedUpdate()) {
                    if (shopSoundPackage != null) {
                        this$0.onDownloadPackage(shopSoundPackage);
                        return;
                    }
                    return;
                } else {
                    Toast.makeText(this$0.getContext(), "Sound already installed", 0).show();
                    FragmentActivity activity2 = this$0.getActivity();
                    if (activity2 != null) {
                        activity2.onBackPressed();
                        return;
                    }
                    return;
                }
            }
            return;
        }
        FragmentActivity activity3 = this$0.getActivity();
        if (activity3 != null) {
            activity3.onBackPressed();
        }
    }

    private final void onDownloadPackage(ShopSoundPackage selectedShopSoundPackage) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UploadSoundPackageSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }

    /* compiled from: NotificationOverviewFragment.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/thor/app/gui/fragments/notification/NotificationOverviewFragment$Companion;", "", "()V", "BUNDLE_ACTION", "", "REQUEST_SETTINGS", "", "newInstance", "Lcom/thor/app/gui/fragments/notification/NotificationOverviewFragment;", "action", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ NotificationOverviewFragment newInstance$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.newInstance(str);
        }

        @JvmStatic
        public final NotificationOverviewFragment newInstance(String action) {
            NotificationOverviewFragment notificationOverviewFragment = new NotificationOverviewFragment();
            if (action != null) {
                Bundle bundle = new Bundle();
                bundle.putString("action", action);
                notificationOverviewFragment.setArguments(bundle);
            }
            return notificationOverviewFragment;
        }
    }
}
