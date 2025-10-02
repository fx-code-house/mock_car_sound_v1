package com.thor.app.gui.activities.notifications;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityNotificationsBinding;
import com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener;
import com.google.firebase.messaging.Constants;
import com.thor.app.bus.events.BadUserEvents;
import com.thor.app.bus.events.SendLogsEvent;
import com.thor.app.bus.events.bluetooth.firmware.ApplyUpdateFirmwareSuccessfulEvent;
import com.thor.app.bus.events.shop.main.UploadSoundPackageSuccessfulEvent;
import com.thor.app.gui.activities.notifications.NotificationsViewModel;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment;
import com.thor.app.gui.dialog.UpdateFirmwareDialogFragment;
import com.thor.app.gui.dialog.dialogs.SimpleDialogFragment;
import com.thor.app.gui.fragments.notification.NotificationOverviewFragment;
import com.thor.app.gui.fragments.notification.NotificationsFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.SchemaEmergencySituationsManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.entity.InstalledSoundPackageEntity;
import com.thor.app.settings.Settings;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bus.events.BleDataRequestLogEvent;
import com.thor.businessmodule.bus.events.BleDataResponseLogEvent;
import com.thor.businessmodule.bus.events.ResponseErrorCodeEvent;
import com.thor.businessmodule.model.TypeDialog;
import com.thor.networkmodule.model.ModeType;
import com.thor.networkmodule.model.notifications.NotificationDetails;
import com.thor.networkmodule.model.notifications.NotificationInfo;
import com.thor.networkmodule.model.notifications.NotificationType;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
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
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: NotificationsActivity.kt */
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\u001dH\u0002J\b\u0010$\u001a\u00020\u001dH\u0002J\b\u0010%\u001a\u00020\u001dH\u0002J\b\u0010&\u001a\u00020\u001dH\u0002J\u0010\u0010'\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020(H\u0007J\u0010\u0010'\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020)H\u0007J\u0012\u0010*\u001a\u00020\u001d2\b\u0010+\u001a\u0004\u0018\u00010,H\u0014J\u0010\u0010-\u001a\u00020\u001d2\u0006\u0010.\u001a\u00020/H\u0002J\b\u00100\u001a\u00020\u001dH\u0003J\u0012\u00101\u001a\u00020\u001d2\b\u00102\u001a\u0004\u0018\u000103H\u0016J\u0010\u00104\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u000205H\u0007J\u0010\u00104\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u000206H\u0007J\u0010\u00104\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u000207H\u0007J\u0010\u00104\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u000208H\u0007J\u0010\u00104\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u000209H\u0007J\b\u0010:\u001a\u00020\u001dH\u0002J\b\u0010;\u001a\u00020\u001dH\u0014J\b\u0010<\u001a\u00020\u001dH\u0014J\b\u0010=\u001a\u00020\u001dH\u0002J\b\u0010>\u001a\u00020\u001dH\u0004J\u0006\u0010?\u001a\u00020\u001dR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u000b\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u000b\u001a\u0004\b\u0015\u0010\u0016¨\u0006@"}, d2 = {"Lcom/thor/app/gui/activities/notifications/NotificationsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/thor/app/gui/fragments/notification/NotificationsFragment$OnLinkItemSelectedListener;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityNotificationsBinding;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "fileLogger", "Lcom/thor/app/utils/logs/loggers/FileLogger;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "viewModel", "Lcom/thor/app/gui/activities/notifications/NotificationsViewModel;", "getViewModel", "()Lcom/thor/app/gui/activities/notifications/NotificationsViewModel;", "viewModel$delegate", "createDisableUpdateAlertDialog", "Landroidx/appcompat/app/AlertDialog;", "context", "Landroid/content/Context;", "handleApiErrorUiState", "", "event", "Lcom/thor/app/gui/activities/notifications/NotificationsViewModel$Companion$ApiErrorUiState;", "handleError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "initNotificationOverviewFragment", "initStartFragment", "initToolbar", "observeUiState", "omMessageEvent", "Lcom/thor/app/bus/events/BadUserEvents;", "Lcom/thor/businessmodule/bus/events/ResponseErrorCodeEvent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDownloadPackage", "selectedShopSoundPackage", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "onFullLogout", "onLinkItemSelected", "info", "Lcom/thor/networkmodule/model/notifications/NotificationInfo;", "onMessageEvent", "Lcom/thor/app/bus/events/SendLogsEvent;", "Lcom/thor/app/bus/events/bluetooth/firmware/ApplyUpdateFirmwareSuccessfulEvent;", "Lcom/thor/app/bus/events/shop/main/UploadSoundPackageSuccessfulEvent;", "Lcom/thor/businessmodule/bus/events/BleDataRequestLogEvent;", "Lcom/thor/businessmodule/bus/events/BleDataResponseLogEvent;", "onOpenDeleteNotificationsDialog", "onStart", "onStop", "onUpdateSoftware", "openUpdateFirmwareDialog", "updateAll", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class NotificationsActivity extends Hilt_NotificationsActivity implements NotificationsFragment.OnLinkItemSelectedListener {
    private ActivityNotificationsBinding binding;

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager = LazyKt.lazy(new Function0<BleManager>() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$bleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0);
        }
    });
    private final FileLogger fileLogger = FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, this, null, 2, null);

    /* compiled from: NotificationsActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[NotificationType.values().length];
            try {
                iArr[NotificationType.TYPE_FIRMWARE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[NotificationType.TYPE_SOUND_PACKAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$8() {
    }

    public NotificationsActivity() {
        final NotificationsActivity notificationsActivity = this;
        final Function0 function0 = null;
        this.viewModel = new ViewModelLazy(Reflection.getOrCreateKotlinClass(NotificationsViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$special$$inlined$viewModels$default$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = notificationsActivity.getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "viewModelStore");
                return viewModelStore;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = notificationsActivity.getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$special$$inlined$viewModels$default$3
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
                CreationExtras defaultViewModelCreationExtras = notificationsActivity.getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "this.defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NotificationsViewModel getViewModel() {
        return (NotificationsViewModel) this.viewModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    private final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    @Override // com.thor.app.gui.activities.notifications.Hilt_NotificationsActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws Resources.NotFoundException {
        super.onCreate(savedInstanceState);
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, R.layout.activity_notifications);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.l…t.activity_notifications)");
        this.binding = (ActivityNotificationsBinding) contentView;
        initToolbar();
        initStartFragment();
        observeUiState();
        NotificationsViewModel viewModel = getViewModel();
        String string = getResources().getString(R.string.text_apply_update_firmware);
        Intrinsics.checkNotNullExpressionValue(string, "resources.getString(R.st…xt_apply_update_firmware)");
        viewModel.setUpdateFirmwareText(string);
        getViewModel().getSoundPackagesInfo();
        final C01971 c01971 = new C01971(this);
        getViewModel().getApiErrorUiState().observe(this, new Observer() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NotificationsActivity.onCreate$lambda$0(c01971, obj);
            }
        });
    }

    /* compiled from: NotificationsActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.notifications.NotificationsActivity$onCreate$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01971 extends FunctionReferenceImpl implements Function1<NotificationsViewModel.Companion.ApiErrorUiState, Unit> {
        C01971(Object obj) {
            super(1, obj, NotificationsActivity.class, "handleApiErrorUiState", "handleApiErrorUiState(Lcom/thor/app/gui/activities/notifications/NotificationsViewModel$Companion$ApiErrorUiState;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(NotificationsViewModel.Companion.ApiErrorUiState apiErrorUiState) {
            invoke2(apiErrorUiState);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(NotificationsViewModel.Companion.ApiErrorUiState p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((NotificationsActivity) this.receiver).handleApiErrorUiState(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleApiErrorUiState(NotificationsViewModel.Companion.ApiErrorUiState event) {
        String message;
        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption$default;
        if (!Intrinsics.areEqual((Object) event.isError(), (Object) true) || (message = event.getMessage()) == null || (alertDialogCreateErrorAlertDialogWithSendLogOption$default = DialogManager.createErrorAlertDialogWithSendLogOption$default(DialogManager.INSTANCE, this, message, null, 4, null)) == null) {
            return;
        }
        alertDialogCreateErrorAlertDialogWithSendLogOption$default.show();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() throws SecurityException {
        Timber.INSTANCE.i("onStart", new Object[0]);
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        Timber.INSTANCE.i("onStop", new Object[0]);
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private final void observeUiState() {
        final Function1<NotificationUiState, Unit> function1 = new Function1<NotificationUiState, Unit>() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity.observeUiState.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(NotificationUiState notificationUiState) {
                invoke2(notificationUiState);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(NotificationUiState notificationUiState) {
                if (Intrinsics.areEqual(notificationUiState, NotificationDefault.INSTANCE)) {
                    return;
                }
                if (Intrinsics.areEqual(notificationUiState, NotificationUpdateAll.INSTANCE)) {
                    NotificationsActivity.this.updateAll();
                } else {
                    Intrinsics.areEqual(notificationUiState, NotificationGetInfoSuccess.INSTANCE);
                }
            }
        };
        getViewModel().getUiState().observe(this, new Observer() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NotificationsActivity.observeUiState$lambda$2(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeUiState$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void initToolbar() {
        ActivityNotificationsBinding activityNotificationsBinding = this.binding;
        ActivityNotificationsBinding activityNotificationsBinding2 = null;
        if (activityNotificationsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityNotificationsBinding = null;
        }
        activityNotificationsBinding.toolbarWidget.enableBluetoothIndicator(true);
        ActivityNotificationsBinding activityNotificationsBinding3 = this.binding;
        if (activityNotificationsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityNotificationsBinding3 = null;
        }
        activityNotificationsBinding3.toolbarWidget.enableNotificationDeleteBtn(true);
        ActivityNotificationsBinding activityNotificationsBinding4 = this.binding;
        if (activityNotificationsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityNotificationsBinding4 = null;
        }
        activityNotificationsBinding4.toolbarWidget.setHomeButtonVisibility(true);
        ActivityNotificationsBinding activityNotificationsBinding5 = this.binding;
        if (activityNotificationsBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityNotificationsBinding5 = null;
        }
        activityNotificationsBinding5.toolbarWidget.setTitle(getResources().getString(R.string.text_notifications));
        getViewModel().getIsBluetoothConnected().set(getBleManager().isBleEnabledAndDeviceConnected());
        ActivityNotificationsBinding activityNotificationsBinding6 = this.binding;
        if (activityNotificationsBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityNotificationsBinding6 = null;
        }
        activityNotificationsBinding6.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationsActivity.initToolbar$lambda$3(this.f$0, view);
            }
        });
        ActivityNotificationsBinding activityNotificationsBinding7 = this.binding;
        if (activityNotificationsBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityNotificationsBinding2 = activityNotificationsBinding7;
        }
        activityNotificationsBinding2.toolbarWidget.setDeleteBtnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationsActivity.initToolbar$lambda$4(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initToolbar$lambda$3(NotificationsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentManager supportFragmentManager = this$0.getSupportFragmentManager();
        ActivityNotificationsBinding activityNotificationsBinding = this$0.binding;
        ActivityNotificationsBinding activityNotificationsBinding2 = null;
        if (activityNotificationsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityNotificationsBinding = null;
        }
        if (supportFragmentManager.findFragmentById(activityNotificationsBinding.fragmentContainer.getId()) instanceof NotificationsFragment) {
            this$0.finish();
            return;
        }
        ActivityNotificationsBinding activityNotificationsBinding3 = this$0.binding;
        if (activityNotificationsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityNotificationsBinding2 = activityNotificationsBinding3;
        }
        activityNotificationsBinding2.toolbarWidget.enableNotificationDeleteBtn(true);
        this$0.getSupportFragmentManager().popBackStack();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initToolbar$lambda$4(NotificationsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onOpenDeleteNotificationsDialog();
    }

    private final void initStartFragment() {
        getSupportFragmentManager().beginTransaction().disallowAddToBackStack().setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim).replace(R.id.fragment_container, NotificationsFragment.INSTANCE.newInstance(getIntent().getAction())).commit();
    }

    private final void initNotificationOverviewFragment() {
        ActivityNotificationsBinding activityNotificationsBinding = this.binding;
        if (activityNotificationsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityNotificationsBinding = null;
        }
        activityNotificationsBinding.toolbarWidget.enableNotificationDeleteBtn(false);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim).addToBackStack("").replace(R.id.fragment_container, NotificationOverviewFragment.INSTANCE.newInstance(getIntent().getAction())).commit();
    }

    private final void onOpenDeleteNotificationsDialog() {
        SimpleDialogFragment simpleDialogFragment = new SimpleDialogFragment(TypeDialog.NOTIFICATION_DELETE);
        simpleDialogFragment.setOnConfirmListener(new OnConfirmDialogListener() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$onOpenDeleteNotificationsDialog$$inlined$doOnConfirm$1
            @Override // com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener
            public void onConfirm(String info) {
                this.this$0.getViewModel().deleteEvent();
            }
        });
        simpleDialogFragment.show(getSupportFragmentManager(), simpleDialogFragment.getClass().getSimpleName());
    }

    private final void handleError(Throwable error) {
        Timber.INSTANCE.e(error);
    }

    @Override // com.thor.app.gui.fragments.notification.NotificationsFragment.OnLinkItemSelectedListener
    public void onLinkItemSelected(NotificationInfo info) {
        initNotificationOverviewFragment();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void updateAll() {
        if (!getViewModel().getListToUpdate().isEmpty()) {
            int i = WhenMappings.$EnumSwitchMapping$0[((NotificationInfo) CollectionsKt.first((List) getViewModel().getListToUpdate())).getNotificationType().ordinal()];
            if (i == 1) {
                onUpdateSoftware();
                CollectionsKt.removeFirst(getViewModel().getListToUpdate());
                return;
            }
            if (i != 2) {
                return;
            }
            NotificationInfo notificationInfo = (NotificationInfo) CollectionsKt.first((List) getViewModel().getListToUpdate());
            List<ShopSoundPackage> list = getViewModel().get_shopSoundPackages();
            ShopSoundPackage shopSoundPackage = null;
            if (list != null) {
                Iterator<T> it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Object next = it.next();
                    ShopSoundPackage shopSoundPackage2 = (ShopSoundPackage) next;
                    NotificationDetails details = notificationInfo.getDetails();
                    boolean z = false;
                    if (details != null && shopSoundPackage2.getId() == details.getIdSoundPkg()) {
                        z = true;
                    }
                    if (z) {
                        shopSoundPackage = next;
                        break;
                    }
                }
                shopSoundPackage = shopSoundPackage;
            }
            if (shopSoundPackage != null) {
                onDownloadPackage(shopSoundPackage);
                CollectionsKt.removeFirst(getViewModel().getListToUpdate());
                getViewModel().clearNotification(notificationInfo.getNotificationId());
            }
        }
    }

    private final void onDownloadPackage(ShopSoundPackage selectedShopSoundPackage) {
        NotificationsActivity notificationsActivity = this;
        if (BleManager.INSTANCE.from(notificationsActivity).getMStateConnected()) {
            DownloadSoundPackageDialogFragment downloadSoundPackageDialogFragmentNewInstance = DownloadSoundPackageDialogFragment.INSTANCE.newInstance(selectedShopSoundPackage);
            if (downloadSoundPackageDialogFragmentNewInstance.isAdded()) {
                return;
            }
            downloadSoundPackageDialogFragmentNewInstance.show(getSupportFragmentManager(), "DownloadSoundPackageDialogFragment");
            return;
        }
        DialogManager.INSTANCE.createNoConnectionToBoardAlertDialog(notificationsActivity).show();
    }

    private final void onUpdateSoftware() {
        Timber.INSTANCE.i("onUpdateSoftware", new Object[0]);
        if (getBleManager().getMStateConnected()) {
            NotificationsActivity notificationsActivity = this;
            if (SchemaEmergencySituationsManager.INSTANCE.from(notificationsActivity).checkFirmwareVersionOnDifference() != null) {
                openUpdateFirmwareDialog();
                return;
            } else {
                getViewModel().disableFirmwareNotification();
                createDisableUpdateAlertDialog(notificationsActivity).show();
                return;
            }
        }
        DialogManager.INSTANCE.createNoConnectionToBoardAlertDialog(this).show();
    }

    protected final void openUpdateFirmwareDialog() {
        UpdateFirmwareDialogFragment updateFirmwareDialogFragmentNewInstance = UpdateFirmwareDialogFragment.INSTANCE.newInstance();
        if (updateFirmwareDialogFragmentNewInstance.isAdded()) {
            return;
        }
        updateFirmwareDialogFragmentNewInstance.show(getSupportFragmentManager(), "UpdateFirmwareDialogFragment");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UploadSoundPackageSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        Log.i("DB_EVENT", "onMessageEvent: " + event);
        ShopSoundPackage soundPackage = event.getSoundPackage();
        int id = soundPackage.getId();
        int pkgVer = soundPackage.getPkgVer();
        ArrayList<ModeType> modeImages = soundPackage.getModeImages();
        Intrinsics.checkNotNull(modeImages != null ? Integer.valueOf(modeImages.size()) : null);
        ThorDatabase.INSTANCE.from(this).installedSoundPackageDao().insert(new InstalledSoundPackageEntity(id, pkgVer, r10.intValue() - 1, false, 8, null)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                NotificationsActivity.onMessageEvent$lambda$8();
            }
        });
        if (!getViewModel().getListToUpdate().isEmpty()) {
            updateAll();
        }
    }

    @Subscribe
    public final void onMessageEvent(ApplyUpdateFirmwareSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        getViewModel().disableFirmwareNotification();
        getViewModel().fetchAllNotifications();
        if (!getViewModel().getListToUpdate().isEmpty()) {
            updateAll();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void omMessageEvent(BadUserEvents event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        onFullLogout();
    }

    private final void onFullLogout() {
        Log.i("TEST21212_T", "onFullLogout");
        Settings.saveIsCheckedEmrgencySituations(false);
        Observable<BaseResponse> observableRemoveNotification = getUsersManager().removeNotification();
        if (observableRemoveNotification != null) {
            final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity.onFullLogout.1
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
                    AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption;
                    if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(NotificationsActivity.this, baseResponse.getError(), Integer.valueOf(baseResponse.getCode()))) != null) {
                        alertDialogCreateErrorAlertDialogWithSendLogOption.show();
                    }
                    Timber.INSTANCE.i("Response: %s", baseResponse);
                }
            };
            Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    NotificationsActivity.onFullLogout$lambda$9(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity.onFullLogout.2
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
                        AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(NotificationsActivity.this, th.getMessage(), 0);
                        if (alertDialogCreateErrorAlertDialog2 != null) {
                            alertDialogCreateErrorAlertDialog2.show();
                        }
                    } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(NotificationsActivity.this, th.getMessage(), 0)) != null) {
                        alertDialogCreateErrorAlertDialog.show();
                    }
                    Timber.INSTANCE.e(th);
                }
            };
            observableRemoveNotification.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    NotificationsActivity.onFullLogout$lambda$10(function12, obj);
                }
            });
        }
        getBleManager().disconnect(true);
        Settings.removeAllProperties();
        NotificationsActivity notificationsActivity = this;
        Settings.clearCookies(notificationsActivity);
        Settings.saveUserGoogleToken("");
        Settings.saveUserGoogleUserId("");
        DataLayerManager.INSTANCE.from(notificationsActivity).sendIsAccessSession(false);
        ThorDatabase.INSTANCE.from(notificationsActivity).installedSoundPackageDao().deleteAllElements();
        ThorDatabase.INSTANCE.from(notificationsActivity).shopSoundPackageDao().deleteAllElements();
        Intent intent = new Intent(notificationsActivity, (Class<?>) SplashActivity.class);
        intent.setFlags(268468224);
        Log.i("TEST21212_T", "onFullLogout");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFullLogout$lambda$9(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFullLogout$lambda$10(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void omMessageEvent(ResponseErrorCodeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        AlertDialog alertDialogCreateDialogBordError = DialogManager.INSTANCE.createDialogBordError(this, BleHelper.takeShort(event.getByteArray()));
        if (alertDialogCreateDialogBordError != null) {
            alertDialogCreateDialogBordError.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BleDataRequestLogEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Log.i("NEW_LOG", "<= D " + event.getDataDeCrypto() + " <= E " + event.getDataCrypto());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BleDataResponseLogEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Log.i("NEW_LOG", "=> D " + event.getDataDeCrypto() + " => E " + event.getDataCrypto());
    }

    @Subscribe
    public final void onMessageEvent(SendLogsEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        NotificationsActivity notificationsActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(notificationsActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, notificationsActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), Dispatchers.getIO(), null, new C01992(uriForFile, event, null), 2, null);
    }

    /* compiled from: NotificationsActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.notifications.NotificationsActivity$onMessageEvent$2", f = "NotificationsActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.notifications.NotificationsActivity$onMessageEvent$2, reason: invalid class name and case insensitive filesystem */
    static final class C01992 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SendLogsEvent $event;
        final /* synthetic */ Uri $path;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01992(Uri uri, SendLogsEvent sendLogsEvent, Continuation<? super C01992> continuation) {
            super(2, continuation);
            this.$path = uri;
            this.$event = sendLogsEvent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return NotificationsActivity.this.new C01992(this.$path, this.$event, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01992) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Observable<BaseResponse> observableSendErrorLogToApi = NotificationsActivity.this.getUsersManager().sendErrorLogToApi(this.$path, this.$event.getErrorMessage());
                if (observableSendErrorLogToApi != null) {
                    final AnonymousClass1 anonymousClass1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity.onMessageEvent.2.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<BaseResponse> observableDoOnSubscribe = observableSendErrorLogToApi.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$onMessageEvent$2$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            anonymousClass1.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final C00622 c00622 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity.onMessageEvent.2.2
                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                                invoke2(baseResponse);
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(BaseResponse baseResponse) {
                                Log.i("SEND_LOGS", "Response: " + baseResponse);
                            }
                        };
                        observableDoOnSubscribe.subscribe(new Consumer() { // from class: com.thor.app.gui.activities.notifications.NotificationsActivity$onMessageEvent$2$$ExternalSyntheticLambda1
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj2) {
                                c00622.invoke(obj2);
                            }
                        });
                    }
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
