package com.thor.app.gui.activities.updatecheck;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentKt;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityUpdateAppBinding;
import com.google.android.exoplayer2.C;
import com.thor.app.bus.events.SendLogsEvent;
import com.thor.app.bus.events.bluetooth.firmware.ApplyUpdateFirmwareSuccessfulEvent;
import com.thor.app.bus.events.bluetooth.firmware.ApplyUpdateFirmwareSuccessfulEventOld;
import com.thor.app.bus.events.bluetooth.firmware.UpdateFirmwareSuccessfulEvent;
import com.thor.app.bus.events.device.UpdateHardwareProfileEvent;
import com.thor.app.databinding.viewmodels.UpdateViewModel;
import com.thor.app.gui.activities.main.MainActivity;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.activities.splash.VersionState;
import com.thor.app.gui.activities.updatecheck.UpdateResult;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.FormatProgressDialogFragment;
import com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.SchemaEmergencySituationsManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.settings.Settings;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.businessmodule.bluetooth.model.other.HardwareProfile;
import com.thor.businessmodule.bus.events.BleDataRequestLogEvent;
import com.thor.businessmodule.bus.events.BleDataResponseLogEvent;
import com.thor.businessmodule.bus.events.FormatFlashReconnecting;
import com.thor.businessmodule.bus.events.UpdateFirmwareService;
import com.thor.networkmodule.model.responses.BaseResponse;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: UpdateAppActivity.kt */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u001aH\u0002J\u0010\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\fH\u0002J\u0010\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020\u001aH\u0002J\b\u0010\"\u001a\u00020\u001aH\u0002J\b\u0010#\u001a\u00020\u001aH\u0002J\b\u0010$\u001a\u00020\u001aH\u0002J\b\u0010%\u001a\u00020\u001aH\u0002J\b\u0010&\u001a\u00020\u001aH\u0016J\u0012\u0010'\u001a\u00020\u001a2\b\u0010(\u001a\u0004\u0018\u00010)H\u0014J\b\u0010*\u001a\u00020\u001aH\u0002J\u0010\u0010+\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020,H\u0007J\u0010\u0010+\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020-H\u0007J\u0010\u0010+\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020.H\u0007J\u0010\u0010+\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020/H\u0007J\u0010\u0010+\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u000200H\u0007J\u0010\u0010+\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u000201H\u0007J\u0010\u0010+\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u000202H\u0007J\u0010\u0010+\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u000203H\u0007J\u0010\u0010+\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u000204H\u0007J\b\u00105\u001a\u00020\u001aH\u0002J\b\u00106\u001a\u00020\u001aH\u0002J\u0010\u00107\u001a\u00020\u001a2\u0006\u00108\u001a\u000209H\u0016J\b\u0010:\u001a\u00020\u001aH\u0002J\b\u0010;\u001a\u00020\u001aH\u0002J\b\u0010<\u001a\u00020\u001aH\u0014J\b\u0010=\u001a\u00020\u001aH\u0016J\b\u0010>\u001a\u00020\u001aH\u0014J\b\u0010?\u001a\u00020\u001aH\u0016J\b\u0010@\u001a\u00020\u001aH\u0002J\u0018\u0010A\u001a\u00020\u001a2\u0006\u0010B\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\fH\u0002J\b\u0010C\u001a\u00020\u001aH\u0002J\b\u0010D\u001a\u00020\u001aH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\n\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\n\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0018\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006E"}, d2 = {"Lcom/thor/app/gui/activities/updatecheck/UpdateAppActivity;", "Lcom/thor/app/gui/activities/bluetooth/BaseScanningBluetoothDevicesActivity;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityUpdateAppBinding;", "database", "Lcom/thor/app/room/ThorDatabase;", "getDatabase", "()Lcom/thor/app/room/ThorDatabase;", "database$delegate", "Lkotlin/Lazy;", "isAppUpdate", "", "isUpdateFirmwareOld", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "viewModel", "Lcom/thor/app/databinding/viewmodels/UpdateViewModel;", "getViewModel", "()Lcom/thor/app/databinding/viewmodels/UpdateViewModel;", "viewModel$delegate", "waitFormatReconnecting", "checkIfUpdated", "", "createFormatFlashAlertDialogOld", "formatOld", "runFirmware", "handleEvent", "event", "Lcom/thor/app/databinding/viewmodels/UpdateViewModel$Companion$ApiErrorState;", "initClickListeners", "initFields", "logout", "observeUpdateResult", "observeVersions", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onFinishFormatFlash", "onMessageEvent", "Lcom/thor/app/bus/events/SendLogsEvent;", "Lcom/thor/app/bus/events/bluetooth/firmware/ApplyUpdateFirmwareSuccessfulEvent;", "Lcom/thor/app/bus/events/bluetooth/firmware/ApplyUpdateFirmwareSuccessfulEventOld;", "Lcom/thor/app/bus/events/bluetooth/firmware/UpdateFirmwareSuccessfulEvent;", "Lcom/thor/app/bus/events/device/UpdateHardwareProfileEvent;", "Lcom/thor/businessmodule/bus/events/BleDataRequestLogEvent;", "Lcom/thor/businessmodule/bus/events/BleDataResponseLogEvent;", "Lcom/thor/businessmodule/bus/events/FormatFlashReconnecting;", "Lcom/thor/businessmodule/bus/events/UpdateFirmwareService;", "onOpenDownloadTelegramLink", "onOpenThorTechSupport", "onScanFailed", "errorCode", "", "onSendLogsToApi", "onShareLogs", "onStart", "onStartScanning", "onStop", "onStopScanning", "openAppInGooglePlay", "showFormatDialog", "result", "showLogoutDialog", "updateHardware", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class UpdateAppActivity extends Hilt_UpdateAppActivity {
    private ActivityUpdateAppBinding binding;
    private boolean isUpdateFirmwareOld;

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;
    private boolean waitFormatReconnecting;
    private boolean isAppUpdate = true;

    /* renamed from: database$delegate, reason: from kotlin metadata */
    private final Lazy database = LazyKt.lazy(new Function0<ThorDatabase>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$database$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThorDatabase invoke() {
            return ThorDatabase.INSTANCE.from(this.this$0);
        }
    });

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    @Override // com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity
    public void onScanFailed(int errorCode) {
    }

    @Override // com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity
    public void onStartScanning() {
    }

    @Override // com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity
    public void onStopScanning() {
    }

    public UpdateAppActivity() {
        final UpdateAppActivity updateAppActivity = this;
        final Function0 function0 = null;
        this.viewModel = new ViewModelLazy(Reflection.getOrCreateKotlinClass(UpdateViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$special$$inlined$viewModels$default$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = updateAppActivity.getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "viewModelStore");
                return viewModelStore;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = updateAppActivity.getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$special$$inlined$viewModels$default$3
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
                CreationExtras defaultViewModelCreationExtras = updateAppActivity.getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "this.defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        });
    }

    private final UpdateViewModel getViewModel() {
        return (UpdateViewModel) this.viewModel.getValue();
    }

    private final ThorDatabase getDatabase() {
        return (ThorDatabase) this.database.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    @Override // com.thor.basemodule.gui.activity.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        Log.i("UpdateAppActivity", "Back pressed");
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIfUpdated();
        this.binding = (ActivityUpdateAppBinding) DataBindingUtil.setContentView(this, R.layout.activity_update_app);
        this.isAppUpdate = getIntent().getBooleanExtra("isApp", true);
        this.isUpdateFirmwareOld = getIntent().getBooleanExtra("isFirmwareOld", false);
        observeVersions();
        observeUpdateResult();
        final C02461 c02461 = new C02461(this);
        getViewModel().getApiErrorState().observe(this, new Observer() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                UpdateAppActivity.onCreate$lambda$0(c02461, obj);
            }
        });
    }

    /* compiled from: UpdateAppActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$onCreate$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C02461 extends FunctionReferenceImpl implements Function1<UpdateViewModel.Companion.ApiErrorState, Unit> {
        C02461(Object obj) {
            super(1, obj, UpdateAppActivity.class, "handleEvent", "handleEvent(Lcom/thor/app/databinding/viewmodels/UpdateViewModel$Companion$ApiErrorState;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(UpdateViewModel.Companion.ApiErrorState apiErrorState) {
            invoke2(apiErrorState);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(UpdateViewModel.Companion.ApiErrorState p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((UpdateAppActivity) this.receiver).handleEvent(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private static final void onCreate$lambda$1(UpdateAppActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onShareLogs();
    }

    private final void onShareLogs() {
        UpdateAppActivity updateAppActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(updateAppActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, updateAppActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", FileLogger.LOGS_FILE_NAME);
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        intent.addFlags(1);
        intent.setType("plain/*");
        startActivity(intent);
    }

    private final void showLogoutDialog() {
        DialogManager.INSTANCE.createLogOutAlertDialog(this, new Function0<Unit>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity.showLogoutDialog.1
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
                UpdateAppActivity.this.logout();
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void logout() {
        Settings.saveIsCheckedEmrgencySituations(false);
        Observable<BaseResponse> observableRemoveNotification = getUsersManager().removeNotification();
        if (observableRemoveNotification != null) {
            final C02431 c02431 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity.logout.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                    invoke2(baseResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(BaseResponse baseResponse) {
                    Timber.INSTANCE.i("Response: %s", baseResponse);
                }
            };
            Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda10
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    UpdateAppActivity.logout$lambda$2(c02431, obj);
                }
            };
            final Function1<Throwable, Unit> function1 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity.logout.2
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
                        AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, UpdateAppActivity.this, th.getMessage(), null, 4, null);
                        if (alertDialogCreateErrorAlertDialog$default2 != null) {
                            alertDialogCreateErrorAlertDialog$default2.show();
                        }
                    } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, UpdateAppActivity.this, th.getMessage(), null, 4, null)) != null) {
                        alertDialogCreateErrorAlertDialog$default.show();
                    }
                    Timber.INSTANCE.e(th);
                }
            };
            observableRemoveNotification.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda11
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    UpdateAppActivity.logout$lambda$3(function1, obj);
                }
            });
        }
        getBleManager().clear();
        getBleManager().disconnect(true);
        Settings.removeAllProperties();
        UpdateAppActivity updateAppActivity = this;
        Settings.clearCookies(updateAppActivity);
        DataLayerManager.INSTANCE.from(updateAppActivity).sendIsAccessSession(false);
        getDatabase().installedSoundPackageDao().deleteAllElements();
        getDatabase().shopSoundPackageDao().deleteAllElements();
        final Intent intent = new Intent(updateAppActivity, (Class<?>) SplashActivity.class);
        intent.setFlags(268468224);
        getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                UpdateAppActivity.logout$lambda$5(this.f$0, intent);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void logout$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void logout$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void logout$lambda$5(UpdateAppActivity this$0, Intent intent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(intent, "$intent");
        this$0.startActivity(intent);
    }

    private final void checkIfUpdated() {
        if (Settings.INSTANCE.isUpdatedFirmware()) {
            Settings.INSTANCE.setUpdatedFirmware(false);
            Toast.makeText(this, R.string.text_update_firmware_error, 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleEvent(UpdateViewModel.Companion.ApiErrorState event) {
        AlertDialog alertDialogCreateErrorAlertDialog$default;
        if (!Intrinsics.areEqual((Object) event.isError(), (Object) true) || (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, event.getMessage(), null, 4, null)) == null) {
            return;
        }
        alertDialogCreateErrorAlertDialog$default.show();
    }

    private final void observeUpdateResult() {
        final Function1<UpdateResult, Unit> function1 = new Function1<UpdateResult, Unit>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity.observeUpdateResult.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(UpdateResult updateResult) {
                invoke2(updateResult);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(UpdateResult updateResult) {
                if (updateResult instanceof UpdateResult.Success) {
                    UpdateAppActivity.this.startActivity(new Intent(UpdateAppActivity.this, (Class<?>) MainActivity.class));
                    UpdateAppActivity.this.finish();
                    return;
                }
                boolean z = updateResult instanceof UpdateResult.Failure;
            }
        };
        getViewModel().isUpdateSuccessfully().observe(this, new Observer() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                UpdateAppActivity.observeUpdateResult$lambda$6(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeUpdateResult$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void observeVersions() {
        final Function1<VersionState, Unit> function1 = new Function1<VersionState, Unit>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity.observeVersions.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(VersionState versionState) {
                invoke2(versionState);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(VersionState versionState) {
                if (versionState instanceof VersionState.actualVersion) {
                    UpdateAppActivity.this.startActivity(new Intent(UpdateAppActivity.this, (Class<?>) MainActivity.class));
                    UpdateAppActivity.this.finish();
                } else {
                    if (versionState instanceof VersionState.oldVersion) {
                        Intent intent = new Intent(UpdateAppActivity.this, (Class<?>) UpdateAppActivity.class);
                        intent.putExtra("isApp", false);
                        UpdateAppActivity.this.startActivity(intent);
                        UpdateAppActivity.this.finish();
                        return;
                    }
                    if (versionState instanceof VersionState.oldVersionApp) {
                        Intent intent2 = new Intent(UpdateAppActivity.this, (Class<?>) UpdateAppActivity.class);
                        intent2.putExtra("isApp", true);
                        UpdateAppActivity.this.startActivity(intent2);
                        UpdateAppActivity.this.finish();
                    }
                }
            }
        };
        getViewModel().getVersionState().observe(this, new Observer() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                UpdateAppActivity.observeVersions$lambda$7(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeVersions$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Subscribe
    public final void onMessageEvent(UpdateFirmwareSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEventUPDATE: %s", event);
    }

    @Subscribe
    public final void onMessageEvent(UpdateHardwareProfileEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEventUPDATE: %s", event);
    }

    @Subscribe
    public final void onMessageEvent(ApplyUpdateFirmwareSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEventUPDATE: %s", event);
        getViewModel().setUpdateResult(event.isSuccessful());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(ApplyUpdateFirmwareSuccessfulEventOld event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ActivityUpdateAppBinding activityUpdateAppBinding = this.binding;
        FrameLayout frameLayout = activityUpdateAppBinding != null ? activityUpdateAppBinding.progressBar : null;
        if (frameLayout != null) {
            frameLayout.setVisibility(0);
        }
        getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                UpdateAppActivity.onMessageEvent$lambda$8(this.f$0);
            }
        }, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$8(UpdateAppActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intent intent = new Intent(this$0, (Class<?>) SplashActivity.class);
        intent.setFlags(268468224);
        this$0.startActivity(intent);
        this$0.finish();
    }

    private final void initFields() {
        TextView textView;
        AppCompatButton appCompatButton;
        AppCompatButton appCompatButton2;
        TextView textView2;
        if (this.isAppUpdate) {
            ActivityUpdateAppBinding activityUpdateAppBinding = this.binding;
            if (activityUpdateAppBinding != null && (textView2 = activityUpdateAppBinding.textUpdate) != null) {
                textView2.setText(R.string.text_please_update_the_app);
            }
            ActivityUpdateAppBinding activityUpdateAppBinding2 = this.binding;
            if (activityUpdateAppBinding2 == null || (appCompatButton2 = activityUpdateAppBinding2.buttonUpdateApp) == null) {
                return;
            }
            appCompatButton2.setText(R.string.text_update_the_app);
            return;
        }
        ActivityUpdateAppBinding activityUpdateAppBinding3 = this.binding;
        if (activityUpdateAppBinding3 != null && (appCompatButton = activityUpdateAppBinding3.buttonUpdateApp) != null) {
            appCompatButton.setText(R.string.text_update_hardware);
        }
        ActivityUpdateAppBinding activityUpdateAppBinding4 = this.binding;
        if (activityUpdateAppBinding4 != null && (textView = activityUpdateAppBinding4.textUpdate) != null) {
            textView.setText(R.string.text_please_update_the_fw);
        }
        ActivityUpdateAppBinding activityUpdateAppBinding5 = this.binding;
        TextView textView3 = activityUpdateAppBinding5 != null ? activityUpdateAppBinding5.textUpdateScreen : null;
        if (textView3 == null) {
            return;
        }
        textView3.setVisibility(0);
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        initClickListeners();
        initFields();
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
    }

    private final void initClickListeners() {
        AppCompatButton appCompatButton;
        AppCompatButton appCompatButton2;
        AppCompatButton appCompatButton3;
        AppCompatButton appCompatButton4;
        AppCompatButton appCompatButton5;
        if (this.isAppUpdate) {
            ActivityUpdateAppBinding activityUpdateAppBinding = this.binding;
            if (activityUpdateAppBinding != null && (appCompatButton5 = activityUpdateAppBinding.buttonUpdateApp) != null) {
                appCompatButton5.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda13
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        UpdateAppActivity.initClickListeners$lambda$9(this.f$0, view);
                    }
                });
            }
        } else {
            ActivityUpdateAppBinding activityUpdateAppBinding2 = this.binding;
            if (activityUpdateAppBinding2 != null && (appCompatButton2 = activityUpdateAppBinding2.buttonUpdateApp) != null) {
                appCompatButton2.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda14
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        UpdateAppActivity.initClickListeners$lambda$10(this.f$0, view);
                    }
                });
            }
            ActivityUpdateAppBinding activityUpdateAppBinding3 = this.binding;
            if (activityUpdateAppBinding3 != null && (appCompatButton = activityUpdateAppBinding3.formatFlash) != null) {
                appCompatButton.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        UpdateAppActivity.initClickListeners$lambda$11(this.f$0, view);
                    }
                });
            }
        }
        ActivityUpdateAppBinding activityUpdateAppBinding4 = this.binding;
        if (activityUpdateAppBinding4 != null && (appCompatButton4 = activityUpdateAppBinding4.buttonSupport) != null) {
            appCompatButton4.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UpdateAppActivity.initClickListeners$lambda$12(this.f$0, view);
                }
            });
        }
        ActivityUpdateAppBinding activityUpdateAppBinding5 = this.binding;
        if (activityUpdateAppBinding5 == null || (appCompatButton3 = activityUpdateAppBinding5.changeCar) == null) {
            return;
        }
        appCompatButton3.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UpdateAppActivity.initClickListeners$lambda$13(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initClickListeners$lambda$9(UpdateAppActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.openAppInGooglePlay();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initClickListeners$lambda$10(UpdateAppActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.updateHardware();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initClickListeners$lambda$11(UpdateAppActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isUpdateFirmwareOld) {
            this$0.createFormatFlashAlertDialogOld();
        } else {
            this$0.createFormatFlashConfirmationAlertDialog(true).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initClickListeners$lambda$12(UpdateAppActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onOpenThorTechSupport();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initClickListeners$lambda$13(UpdateAppActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showLogoutDialog();
    }

    private final void createFormatFlashAlertDialogOld() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886083);
        builder.setTitle(R.string.text_format_flash).setMessage(R.string.text_format_flash_tip_confirm).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda9
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                UpdateAppActivity.createFormatFlashAlertDialogOld$lambda$14(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(R.string.dialog_button_cancel, (DialogInterface.OnClickListener) null);
        builder.create().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createFormatFlashAlertDialogOld$lambda$14(UpdateAppActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Settings.saveIsCheckedEmrgencySituations(false);
        this$0.getMLoadingDialog().show();
        this$0.formatOld(false);
        dialogInterface.dismiss();
    }

    private final void formatOld(final boolean runFirmware) {
        getBleManager().setFromOldMode(true);
        getBleManager().executeFormatFlashCommandOld(new Function1<Boolean, Unit>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity.formatOld.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke(bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(boolean z) {
                UpdateAppActivity.this.showFormatDialog(z, runFirmware);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showFormatDialog(boolean result, boolean runFirmware) {
        this.waitFormatReconnecting = result;
        long jCurrentTimeMillis = System.currentTimeMillis();
        while (this.waitFormatReconnecting && System.currentTimeMillis() - jCurrentTimeMillis <= C.DEFAULT_SEEK_FORWARD_INCREMENT_MS) {
        }
        if (result) {
            ThorDatabase.INSTANCE.from(this).installedSoundPackageDao().deleteAllElements();
            if (runFirmware) {
                updateHardware();
                return;
            } else {
                onFinishFormatFlash();
                return;
            }
        }
        getMLoadingDialog().dismiss();
        runOnUiThread(new Runnable() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                UpdateAppActivity.showFormatDialog$lambda$15(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showFormatDialog$lambda$15(UpdateAppActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String string = this$0.getString(R.string.text_format_flash_error);
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.text_format_flash_error)");
        this$0.createErrorMessageAlertDialog(string).show();
    }

    private final void onFinishFormatFlash() {
        getMLoadingDialog().dismiss();
        runOnUiThread(new Runnable() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                UpdateAppActivity.onFinishFormatFlash$lambda$16(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFinishFormatFlash$lambda$16(final UpdateAppActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        final FormatProgressDialogFragment formatProgressDialogFragmentNewInstance = FormatProgressDialogFragment.INSTANCE.newInstance();
        formatProgressDialogFragmentNewInstance.show(this$0.getSupportFragmentManager(), FormatProgressDialogFragment.INSTANCE.getBUNDLE_NAME());
        FragmentKt.setFragmentResultListener(formatProgressDialogFragmentNewInstance, FormatProgressDialogFragment.INSTANCE.getBUNDLE_NAME(), new Function2<String, Bundle, Unit>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$onFinishFormatFlash$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(String str, Bundle bundle) {
                invoke2(str, bundle);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String str, Bundle bundle) {
                Intrinsics.checkNotNullParameter(str, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(bundle, "bundle");
                this.this$0.createFinishFormatFlashDialog().show();
                FragmentKt.clearFragmentResultListener(formatProgressDialogFragmentNewInstance, FormatProgressDialogFragment.INSTANCE.getBUNDLE_NAME());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateHardware() {
        getMLoadingDialog().dismiss();
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        if (hardwareProfile != null) {
            hardwareProfile.getVersionFirmware();
            if (this.isUpdateFirmwareOld) {
                UpdateFirmwareDialogFragmentOld updateFirmwareDialogFragmentOldNewInstance = UpdateFirmwareDialogFragmentOld.INSTANCE.newInstance();
                if (!updateFirmwareDialogFragmentOldNewInstance.isAdded()) {
                    updateFirmwareDialogFragmentOldNewInstance.show(getSupportFragmentManager(), "UpdateFirmwareDialogFragmentOld");
                    return;
                }
            }
        }
        if (getBleManager().getMStateConnected()) {
            if (SchemaEmergencySituationsManager.INSTANCE.from(this).checkFirmwareVersionOnDifference() != null) {
                ActivityUpdateAppBinding activityUpdateAppBinding = this.binding;
                FrameLayout frameLayout = activityUpdateAppBinding != null ? activityUpdateAppBinding.progressBar : null;
                if (frameLayout != null) {
                    frameLayout.setVisibility(8);
                }
                openUpdateFirmwareDialog();
                return;
            }
            getUsersManager().updateFirmwareProfile(new Function0<Unit>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity.updateHardware.2
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
                    ActivityUpdateAppBinding activityUpdateAppBinding2 = UpdateAppActivity.this.binding;
                    FrameLayout frameLayout2 = activityUpdateAppBinding2 != null ? activityUpdateAppBinding2.progressBar : null;
                    if (frameLayout2 != null) {
                        frameLayout2.setVisibility(0);
                    }
                    UpdateAppActivity.this.updateHardware();
                }
            });
            return;
        }
        DialogManager.INSTANCE.createNoConnectionToBoardAlertDialog(this).show();
    }

    private final void openAppInGooglePlay() {
        String packageName = getPackageName();
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    private final void onOpenThorTechSupport() {
        onSendLogsToApi();
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://t.me/ThorTechSupport"));
        intent.setPackage("org.telegram.messenger");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            onOpenDownloadTelegramLink();
        }
    }

    private final void onSendLogsToApi() {
        UpdateAppActivity updateAppActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(updateAppActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, updateAppActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        getViewModel().sendLogs(uriForFile);
    }

    private final void onOpenDownloadTelegramLink() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=org.telegram.messenger")));
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://t.me/ThorTechSupport")));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UpdateFirmwareService event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Intent intent = new Intent(this, (Class<?>) MainActivity.class);
        intent.setFlags(268468224);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(FormatFlashReconnecting event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.waitFormatReconnecting = false;
    }

    @Subscribe
    public final void onMessageEvent(SendLogsEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        UpdateAppActivity updateAppActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(updateAppActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, updateAppActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), Dispatchers.getIO(), null, new C02472(uriForFile, event, null), 2, null);
    }

    /* compiled from: UpdateAppActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.updatecheck.UpdateAppActivity$onMessageEvent$2", f = "UpdateAppActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$onMessageEvent$2, reason: invalid class name and case insensitive filesystem */
    static final class C02472 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SendLogsEvent $event;
        final /* synthetic */ Uri $path;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02472(Uri uri, SendLogsEvent sendLogsEvent, Continuation<? super C02472> continuation) {
            super(2, continuation);
            this.$path = uri;
            this.$event = sendLogsEvent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UpdateAppActivity.this.new C02472(this.$path, this.$event, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02472) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Observable<BaseResponse> observableSendErrorLogToApi = UpdateAppActivity.this.getUsersManager().sendErrorLogToApi(this.$path, this.$event.getErrorMessage());
                if (observableSendErrorLogToApi != null) {
                    final AnonymousClass1 anonymousClass1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity.onMessageEvent.2.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<BaseResponse> observableDoOnSubscribe = observableSendErrorLogToApi.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$onMessageEvent$2$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            anonymousClass1.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final C00712 c00712 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity.onMessageEvent.2.2
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
                        observableDoOnSubscribe.subscribe(new Consumer() { // from class: com.thor.app.gui.activities.updatecheck.UpdateAppActivity$onMessageEvent$2$$ExternalSyntheticLambda1
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj2) {
                                c00712.invoke(obj2);
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
