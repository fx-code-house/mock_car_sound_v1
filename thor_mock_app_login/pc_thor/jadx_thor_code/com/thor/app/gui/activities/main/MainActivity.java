package com.thor.app.gui.activities.main;

import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.carsystems.thor.app.BuildConfig;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityMainBinding;
import com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.messaging.Constants;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.thor.app.bus.events.BadUserEvents;
import com.thor.app.bus.events.BluetoothDeviceConnectionChangedEvent;
import com.thor.app.bus.events.BluetoothDeviceRssiEvent;
import com.thor.app.bus.events.LoaderChangedEvent;
import com.thor.app.bus.events.SendLogsEvent;
import com.thor.app.bus.events.bluetooth.StartScanBluetoothDevicesEvent;
import com.thor.app.bus.events.bluetooth.StopScanBluetoothDevicesEvent;
import com.thor.app.bus.events.bluetooth.firmware.ApplyUpdateFirmwareSuccessfulEvent;
import com.thor.app.bus.events.bluetooth.firmware.StartUpdateFirmwareEvent;
import com.thor.app.bus.events.bluetooth.firmware.UpdateFirmwareSuccessfulEvent;
import com.thor.app.bus.events.device.DeviceLockedStateEvent;
import com.thor.app.bus.events.device.UpdateHardwareProfileEvent;
import com.thor.app.bus.events.shop.main.DownloadSettingsFileErrorEvent;
import com.thor.app.bus.events.shop.main.DownloadSettingsFileSuccessEvent;
import com.thor.app.bus.events.shop.main.UploadSoundPackageInterruptedEvent;
import com.thor.app.bus.events.shop.sgu.StopPlayingSguSoundEvent;
import com.thor.app.databinding.model.RunningAppOnPhoneStatus;
import com.thor.app.databinding.viewmodels.UpdateViewModel;
import com.thor.app.gui.activities.LockedDeviceActivity;
import com.thor.app.gui.activities.demo.DemoActivity;
import com.thor.app.gui.activities.main.MainActivity;
import com.thor.app.gui.activities.main.MainActivityViewModel;
import com.thor.app.gui.activities.main.carinfo.ChangeCarActivity;
import com.thor.app.gui.activities.notifications.NotificationsActivity;
import com.thor.app.gui.activities.settings.SettingsActivity;
import com.thor.app.gui.activities.shop.ShopActivity;
import com.thor.app.gui.activities.shop.main.SubscriptionsActivity;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.activities.splash.VersionState;
import com.thor.app.gui.activities.testers.FirmwareListActivity;
import com.thor.app.gui.activities.updatecheck.UpdateAppActivity;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.dialogs.TestersDialogFragment;
import com.thor.app.gui.fragments.presets.main.MainSoundsFragment;
import com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment;
import com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel;
import com.thor.app.gui.widget.ShopModeSwitchView;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.SchemaEmergencySituationsManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.receiver.BluetoothBroadcastReceiver;
import com.thor.app.room.ThorDatabase;
import com.thor.app.service.UploadFilesService;
import com.thor.app.settings.Settings;
import com.thor.app.utils.data.DataLayerHelper;
import com.thor.app.utils.extensions.ViewKt;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.app.utils.security.DeviceLockingUtilsKt;
import com.thor.businessmodule.bluetooth.model.other.DeviceParameters;
import com.thor.businessmodule.bluetooth.model.other.HardwareProfile;
import com.thor.businessmodule.bus.events.BleDataRequestLogEvent;
import com.thor.businessmodule.bus.events.BleDataResponseLogEvent;
import com.thor.businessmodule.settings.Variables;
import com.thor.businessmodule.viewmodel.menu.MainMenuViewModel;
import com.thor.networkmodule.model.ChangeCarInfo;
import com.thor.networkmodule.model.notifications.NotificationInfo;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.CanConfigurationsResponse;
import com.thor.networkmodule.model.responses.PasswordValidationResponse;
import com.thor.networkmodule.model.responses.SignInResponse;
import com.thor.networkmodule.model.responses.car.CarFuelTypeResponse;
import com.thor.networkmodule.model.responses.sgu.SguSoundConfig;
import com.thor.networkmodule.utils.ConnectivityAndInternetAccess;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
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
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: MainActivity.kt */
@Metadata(d1 = {"\u0000è\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0011\b\u0007\u0018\u0000 \u008b\u00012\u00020\u00012\u00020\u0002:\u0002\u008b\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010*\u001a\u00020+H\u0002J\b\u0010,\u001a\u00020+H\u0002J\b\u0010-\u001a\u00020+H\u0002J\b\u0010.\u001a\u00020+H\u0002J\u0006\u0010/\u001a\u00020+J\u0012\u00100\u001a\u0002012\b\b\u0002\u00102\u001a\u00020\u0007H\u0002J\b\u00103\u001a\u000201H\u0002J\b\u00104\u001a\u000201H\u0002J\b\u00105\u001a\u00020+H\u0014J\b\u00106\u001a\u00020+H\u0014J\b\u00107\u001a\u00020+H\u0002J\u0010\u00108\u001a\u00020+2\u0006\u00109\u001a\u00020:H\u0002J\u0010\u0010;\u001a\u00020+2\u0006\u0010<\u001a\u00020=H\u0002J\u0010\u0010>\u001a\u00020+2\u0006\u0010?\u001a\u00020@H\u0002J\u0010\u0010A\u001a\u00020+2\u0006\u00109\u001a\u00020BH\u0002J\b\u0010C\u001a\u00020+H\u0002J\b\u0010D\u001a\u00020+H\u0002J\b\u0010E\u001a\u00020+H\u0002J\b\u0010F\u001a\u00020+H\u0002J\b\u0010G\u001a\u00020+H\u0002J\u0012\u0010H\u001a\u00020+2\b\u0010I\u001a\u0004\u0018\u00010JH\u0002J\b\u0010K\u001a\u00020+H\u0002J\u0010\u0010L\u001a\u00020+2\u0006\u0010?\u001a\u00020MH\u0007J\b\u0010N\u001a\u00020+H\u0002J\b\u0010O\u001a\u00020+H\u0016J\b\u0010P\u001a\u00020+H\u0002J\u0010\u0010Q\u001a\u00020+2\u0006\u0010R\u001a\u00020JH\u0002J\u0012\u0010S\u001a\u00020+2\b\u0010T\u001a\u0004\u0018\u00010\nH\u0016J\b\u0010U\u001a\u00020+H\u0002J\u0012\u0010V\u001a\u00020+2\b\u0010W\u001a\u0004\u0018\u00010XH\u0014J\b\u0010Y\u001a\u00020+H\u0002J\b\u0010Z\u001a\u00020+H\u0014J\b\u0010[\u001a\u00020+H\u0002J\b\u0010\\\u001a\u00020+H\u0003J\b\u0010]\u001a\u00020+H\u0002J\b\u0010^\u001a\u00020+H\u0003J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020`H\u0007J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020aH\u0007J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020bH\u0007J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020cH\u0007J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020dH\u0007J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020eH\u0007J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020fH\u0007J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020gH\u0016J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020hH\u0007J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020iH\u0007J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020jH\u0007J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020kH\u0007J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020lH\u0007J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020mH\u0007J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020nH\u0007J\u0010\u0010_\u001a\u00020+2\u0006\u0010?\u001a\u00020oH\u0007J\b\u0010p\u001a\u00020+H\u0002J\b\u0010q\u001a\u00020+H\u0003J\b\u0010r\u001a\u00020+H\u0002J\b\u0010s\u001a\u00020+H\u0002J\b\u0010t\u001a\u00020+H\u0002J\b\u0010u\u001a\u00020+H\u0002J\b\u0010v\u001a\u00020+H\u0002J\b\u0010w\u001a\u00020+H\u0002J\b\u0010x\u001a\u00020+H\u0014J\u0010\u0010y\u001a\u00020+2\u0006\u0010z\u001a\u00020{H\u0016J\b\u0010|\u001a\u00020+H\u0002J\b\u0010}\u001a\u00020+H\u0002J\b\u0010~\u001a\u00020+H\u0014J\b\u0010\u007f\u001a\u00020+H\u0016J\t\u0010\u0080\u0001\u001a\u00020+H\u0014J\t\u0010\u0081\u0001\u001a\u00020+H\u0016J\t\u0010\u0082\u0001\u001a\u00020+H\u0002J\t\u0010\u0083\u0001\u001a\u00020+H\u0002J\t\u0010\u0084\u0001\u001a\u00020+H\u0002J\t\u0010\u0085\u0001\u001a\u00020+H\u0002J\t\u0010\u0086\u0001\u001a\u00020+H\u0002J\t\u0010\u0087\u0001\u001a\u00020+H\u0002J\t\u0010\u0088\u0001\u001a\u00020+H\u0002J\t\u0010\u0089\u0001\u001a\u00020+H\u0002J\t\u0010\u008a\u0001\u001a\u00020+H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u0011\u001a\u0004\u0018\u00010\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0010\u001a\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0016\u001a\u00020\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u0010\u001a\u0004\b\u0018\u0010\u0019R\u001b\u0010\u001b\u001a\u00020\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u0010\u001a\u0004\b\u001d\u0010\u001eR\u001b\u0010 \u001a\u00020!8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u0010\u001a\u0004\b\"\u0010#R\u001b\u0010%\u001a\u00020&8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b)\u0010\u0010\u001a\u0004\b'\u0010(¨\u0006\u008c\u0001"}, d2 = {"Lcom/thor/app/gui/activities/main/MainActivity;", "Lcom/thor/app/gui/activities/bluetooth/BaseScanningBluetoothDevicesActivity;", "Landroid/view/View$OnClickListener;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityMainBinding;", "firstshow", "", "languageSheetBehaviour", "Lcom/google/android/material/bottomsheet/BottomSheetBehavior;", "Landroid/view/View;", "mDataLayerHelper", "Lcom/thor/app/utils/data/DataLayerHelper;", "getMDataLayerHelper", "()Lcom/thor/app/utils/data/DataLayerHelper;", "mDataLayerHelper$delegate", "Lkotlin/Lazy;", "mReceiver", "Lcom/thor/app/receiver/BluetoothBroadcastReceiver;", "getMReceiver", "()Lcom/thor/app/receiver/BluetoothBroadcastReceiver;", "mReceiver$delegate", "sguSoundsViewModel", "Lcom/thor/app/gui/fragments/presets/sgu/SguSoundsViewModel;", "getSguSoundsViewModel", "()Lcom/thor/app/gui/fragments/presets/sgu/SguSoundsViewModel;", "sguSoundsViewModel$delegate", "updateViewModel", "Lcom/thor/app/databinding/viewmodels/UpdateViewModel;", "getUpdateViewModel", "()Lcom/thor/app/databinding/viewmodels/UpdateViewModel;", "updateViewModel$delegate", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "viewModel", "Lcom/thor/app/gui/activities/main/MainActivityViewModel;", "getViewModel", "()Lcom/thor/app/gui/activities/main/MainActivityViewModel;", "viewModel$delegate", "changeLanguage", "", "checkCarFuelType", "checkDeviceCanFileStatus", "checkNeedBlockDevice", "checkNeedUpdateFWM", "createCarInfoAlertDialog", "Landroidx/appcompat/app/AlertDialog;", "isNoCanFile", "createDisableUpdateAlertDialog", "createLogoutAlertDialog", "disableUpdateSoftware", "enableUpdateSoftware", "fetchCarCanInfo", "handleCarFuelTypeResponse", "response", "Lcom/thor/networkmodule/model/responses/car/CarFuelTypeResponse;", "handleError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleEvent", "event", "Lcom/thor/app/gui/activities/main/MainActivityViewModel$Companion$UiState;", "handleResponse", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "initLanguage", "initMainMenu", "initSguConfig", "initViewPager", "isNotificationsUnread", "logPasswordValidation", "password", "", "observeVersions", "omMessageEvent", "Lcom/thor/app/bus/events/BadUserEvents;", "onAddSound", "onBackPressed", "onChangeCar", "onChangeLanguage", "languageCode", "onClick", "v", "onCloseMenu", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDemoMode", "onDestroy", "onFormatFlash", "onFullLogout", "onLanguage", "onLogout", "onMessageEvent", "Lcom/thor/app/bus/events/BluetoothDeviceConnectionChangedEvent;", "Lcom/thor/app/bus/events/BluetoothDeviceRssiEvent;", "Lcom/thor/app/bus/events/LoaderChangedEvent;", "Lcom/thor/app/bus/events/SendLogsEvent;", "Lcom/thor/app/bus/events/bluetooth/StartScanBluetoothDevicesEvent;", "Lcom/thor/app/bus/events/bluetooth/StopScanBluetoothDevicesEvent;", "Lcom/thor/app/bus/events/bluetooth/firmware/ApplyUpdateFirmwareSuccessfulEvent;", "Lcom/thor/app/bus/events/bluetooth/firmware/StartUpdateFirmwareEvent;", "Lcom/thor/app/bus/events/bluetooth/firmware/UpdateFirmwareSuccessfulEvent;", "Lcom/thor/app/bus/events/device/DeviceLockedStateEvent;", "Lcom/thor/app/bus/events/device/UpdateHardwareProfileEvent;", "Lcom/thor/app/bus/events/shop/main/DownloadSettingsFileErrorEvent;", "Lcom/thor/app/bus/events/shop/main/DownloadSettingsFileSuccessEvent;", "Lcom/thor/app/bus/events/shop/main/UploadSoundPackageInterruptedEvent;", "Lcom/thor/businessmodule/bus/events/BleDataRequestLogEvent;", "Lcom/thor/businessmodule/bus/events/BleDataResponseLogEvent;", "onNewDevice", "onOpenCarInfo", "onOpenDownloadTelegramLink", "onOpenMenu", "onOpenNotificationsActivity", "onOpenSettings", "onOpenThorTechSupport", "onRestartApplication", "onResume", "onScanFailed", "errorCode", "", "onSendLogsToApi", "onShareLogs", "onStart", "onStartScanning", "onStop", "onStopScanning", "onSupportCall", "onUpdateSoftware", "openAppInGooglePlay", "openSubscriptionScreen", "remindToSubscribe", "showFirmwareEnterPasswordDialog", "showLogEnterPasswordDialog", "showServerSelectDialog", "updateProfileInfo", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class MainActivity extends Hilt_MainActivity implements View.OnClickListener {
    public static final String FIRMWARE_PASSWORD = "firmware_password";
    private static final int MAIN_SOUNDS_PAGE_INDEX = 0;
    private static final int SGU_SOUNDS_PAGE_INDEX = 1;
    private static boolean isNeedSendInterruptedSoundEvent;
    private static int stableSignalCounter;
    private ActivityMainBinding binding;
    private BottomSheetBehavior<View> languageSheetBehaviour;

    /* renamed from: sguSoundsViewModel$delegate, reason: from kotlin metadata */
    private final Lazy sguSoundsViewModel;

    /* renamed from: updateViewModel$delegate, reason: from kotlin metadata */
    private final Lazy updateViewModel;

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    /* renamed from: mReceiver$delegate, reason: from kotlin metadata */
    private final Lazy mReceiver = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BluetoothBroadcastReceiver>() { // from class: com.thor.app.gui.activities.main.MainActivity$mReceiver$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BluetoothBroadcastReceiver invoke() {
            return new BluetoothBroadcastReceiver();
        }
    });

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.main.MainActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    /* renamed from: mDataLayerHelper$delegate, reason: from kotlin metadata */
    private final Lazy mDataLayerHelper = LazyKt.lazy(new Function0<DataLayerHelper>() { // from class: com.thor.app.gui.activities.main.MainActivity$mDataLayerHelper$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final DataLayerHelper invoke() {
            return DataLayerHelper.newInstance(this.this$0);
        }
    });
    private boolean firstshow = true;

    private final void checkDeviceCanFileStatus() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(View view) {
    }

    public MainActivity() {
        final MainActivity mainActivity = this;
        final Function0 function0 = null;
        this.viewModel = new ViewModelLazy(Reflection.getOrCreateKotlinClass(MainActivityViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.activities.main.MainActivity$special$$inlined$viewModels$default$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = mainActivity.getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "viewModelStore");
                return viewModelStore;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.activities.main.MainActivity$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = mainActivity.getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.activities.main.MainActivity$special$$inlined$viewModels$default$3
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
                CreationExtras defaultViewModelCreationExtras = mainActivity.getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "this.defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        });
        this.updateViewModel = new ViewModelLazy(Reflection.getOrCreateKotlinClass(UpdateViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.activities.main.MainActivity$special$$inlined$viewModels$default$5
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = mainActivity.getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "viewModelStore");
                return viewModelStore;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.activities.main.MainActivity$special$$inlined$viewModels$default$4
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = mainActivity.getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.activities.main.MainActivity$special$$inlined$viewModels$default$6
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
                CreationExtras defaultViewModelCreationExtras = mainActivity.getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "this.defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        });
        this.sguSoundsViewModel = new ViewModelLazy(Reflection.getOrCreateKotlinClass(SguSoundsViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.activities.main.MainActivity$special$$inlined$viewModels$default$8
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = mainActivity.getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "viewModelStore");
                return viewModelStore;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.activities.main.MainActivity$special$$inlined$viewModels$default$7
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = mainActivity.getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.activities.main.MainActivity$special$$inlined$viewModels$default$9
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
                CreationExtras defaultViewModelCreationExtras = mainActivity.getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "this.defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        });
    }

    private final BluetoothBroadcastReceiver getMReceiver() {
        return (BluetoothBroadcastReceiver) this.mReceiver.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DataLayerHelper getMDataLayerHelper() {
        Object value = this.mDataLayerHelper.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-mDataLayerHelper>(...)");
        return (DataLayerHelper) value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MainActivityViewModel getViewModel() {
        return (MainActivityViewModel) this.viewModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UpdateViewModel getUpdateViewModel() {
        return (UpdateViewModel) this.updateViewModel.getValue();
    }

    private final SguSoundsViewModel getSguSoundsViewModel() {
        return (SguSoundsViewModel) this.sguSoundsViewModel.getValue();
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws SecurityException {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = null;
        UsersManager.updateFirmwareProfile$default(getUsersManager(), null, 1, null);
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.layout.activity_main)");
        ActivityMainBinding activityMainBinding2 = (ActivityMainBinding) contentView;
        this.binding = activityMainBinding2;
        if (activityMainBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding2 = null;
        }
        activityMainBinding2.progressBar.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.onCreate$lambda$0(view);
            }
        });
        ActivityMainBinding activityMainBinding3 = this.binding;
        if (activityMainBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding3 = null;
        }
        activityMainBinding3.setModel(getViewModel());
        ActivityMainBinding activityMainBinding4 = this.binding;
        if (activityMainBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityMainBinding = activityMainBinding4;
        }
        activityMainBinding.progressBar.setVisibility(0);
        getViewModel().getRssiLevel().set(Integer.valueOf(getBleManager().getRssiLevel()));
        final C01692 c01692 = new C01692(this);
        getViewModel().getUiEvent().observe(this, new Observer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda17
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.onCreate$lambda$1(c01692, obj);
            }
        });
        initViewPager();
        initInternetConnectionListener();
        initMainMenu();
        initLanguage();
        registerReceiver(getMReceiver(), new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        MainActivity mainActivity = this;
        SchemaEmergencySituationsManager.INSTANCE.from(mainActivity).startCheckForEmergencySituations();
        DataLayerManager.INSTANCE.from(mainActivity).sendIsAccessSession(Settings.INSTANCE.isAccessSession());
        remindToSubscribe();
        if (getIntent().getBooleanExtra("reload", false)) {
            getBleManager().executeHardwareCommand();
        }
        observeVersions();
        getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.onCreate$lambda$2(this.f$0);
            }
        }, 3000L);
        getUpdateViewModel().loadedAppVersionFromApi();
    }

    /* compiled from: MainActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.MainActivity$onCreate$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01692 extends FunctionReferenceImpl implements Function1<MainActivityViewModel.Companion.UiState, Unit> {
        C01692(Object obj) {
            super(1, obj, MainActivity.class, "handleEvent", "handleEvent(Lcom/thor/app/gui/activities/main/MainActivityViewModel$Companion$UiState;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(MainActivityViewModel.Companion.UiState uiState) {
            invoke2(uiState);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(MainActivityViewModel.Companion.UiState p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((MainActivity) this.receiver).handleEvent(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.getBleManager().getMStateConnected()) {
            return;
        }
        ActivityMainBinding activityMainBinding = this$0.binding;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        activityMainBinding.progressBar.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleEvent(MainActivityViewModel.Companion.UiState event) {
        String message;
        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption;
        if (!Intrinsics.areEqual((Object) event.isError(), (Object) true) || (message = event.getMessage()) == null || (alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(this, message, 0)) == null) {
            return;
        }
        alertDialogCreateErrorAlertDialogWithSendLogOption.show();
    }

    private final void observeVersions() {
        LiveData<VersionState> versionState = getUpdateViewModel().getVersionState();
        MainActivity mainActivity = this;
        final Function1<VersionState, Unit> function1 = new Function1<VersionState, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.observeVersions.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(VersionState versionState2) {
                invoke2(versionState2);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(VersionState versionState2) {
                Log.i("TEST21212", "observeVersions: " + versionState2);
                ActivityMainBinding activityMainBinding = null;
                if (versionState2 instanceof VersionState.actualVersion) {
                    ActivityMainBinding activityMainBinding2 = MainActivity.this.binding;
                    if (activityMainBinding2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        activityMainBinding2 = null;
                    }
                    activityMainBinding2.progressBar.setVisibility(8);
                    if (MainActivity.this.getBleManager().getFirmwareUploaded()) {
                        MainActivity.this.getBleManager().setFirmwareUploaded(false);
                        AlertDialog alertDialogCreateAlertDialogUpdate = DialogManager.INSTANCE.createAlertDialogUpdate(MainActivity.this, "Firmware update", null, new Function0<Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.observeVersions.1.1
                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }
                        });
                        if (alertDialogCreateAlertDialogUpdate != null) {
                            alertDialogCreateAlertDialogUpdate.show();
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (versionState2 instanceof VersionState.oldVersion) {
                    ActivityMainBinding activityMainBinding3 = MainActivity.this.binding;
                    if (activityMainBinding3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    } else {
                        activityMainBinding = activityMainBinding3;
                    }
                    activityMainBinding.progressBar.setVisibility(8);
                    Intent intent = new Intent(MainActivity.this, (Class<?>) UpdateAppActivity.class);
                    intent.putExtra("isApp", false);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();
                    return;
                }
                if (versionState2 instanceof VersionState.oldVersionApp) {
                    ActivityMainBinding activityMainBinding4 = MainActivity.this.binding;
                    if (activityMainBinding4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    } else {
                        activityMainBinding = activityMainBinding4;
                    }
                    activityMainBinding.progressBar.setVisibility(8);
                    Intent intent2 = new Intent(MainActivity.this, (Class<?>) UpdateAppActivity.class);
                    intent2.putExtra("isApp", true);
                    MainActivity.this.startActivity(intent2);
                    MainActivity.this.finish();
                    return;
                }
                if (versionState2 instanceof VersionState.None) {
                    ActivityMainBinding activityMainBinding5 = MainActivity.this.binding;
                    if (activityMainBinding5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    } else {
                        activityMainBinding = activityMainBinding5;
                    }
                    activityMainBinding.progressBar.setVisibility(0);
                    return;
                }
                ActivityMainBinding activityMainBinding6 = MainActivity.this.binding;
                if (activityMainBinding6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    activityMainBinding = activityMainBinding6;
                }
                activityMainBinding.progressBar.setVisibility(8);
            }
        };
        versionState.observe(mainActivity, new Observer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda21
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.observeVersions$lambda$4(function1, obj);
            }
        });
        LiveData<Boolean> showUpdateDialog = getUpdateViewModel().getShowUpdateDialog();
        final Function1<Boolean, Unit> function12 = new Function1<Boolean, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.observeVersions.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke2(bool);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Boolean it) {
                Intrinsics.checkNotNullExpressionValue(it, "it");
                if (it.booleanValue()) {
                    DialogManager dialogManager = DialogManager.INSTANCE;
                    MainActivity mainActivity2 = MainActivity.this;
                    String string = mainActivity2.getString(R.string.update_app_title);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.update_app_title)");
                    String string2 = MainActivity.this.getString(R.string.update_app_message);
                    final MainActivity mainActivity3 = MainActivity.this;
                    Function0<Unit> function0 = new Function0<Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity$observeVersions$2$dialog$1
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
                            mainActivity3.getUpdateViewModel().clearUpdateDialog();
                            mainActivity3.openAppInGooglePlay();
                        }
                    };
                    final MainActivity mainActivity4 = MainActivity.this;
                    AlertDialog alertDialogCreateAlertDialogUpdate = dialogManager.createAlertDialogUpdate(mainActivity2, string, string2, function0, new Function0<Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity$observeVersions$2$dialog$2
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
                            mainActivity4.getUpdateViewModel().clearUpdateDialog();
                        }
                    });
                    if (alertDialogCreateAlertDialogUpdate != null) {
                        alertDialogCreateAlertDialogUpdate.show();
                    }
                }
            }
        };
        showUpdateDialog.observe(mainActivity, new Observer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda22
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.observeVersions$lambda$5(function12, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeVersions$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeVersions$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openAppInGooglePlay() {
        String packageName = getPackageName();
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        ActivityMainBinding activityMainBinding = this.binding;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        if (activityMainBinding.switchShopMode.getCurrentMode() == ShopModeSwitchView.ShopMode.CAR_SOUND) {
            DataLayerManager.INSTANCE.from(this).sendIsRunningAppOnPhone(RunningAppOnPhoneStatus.MAIN);
        }
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        ObservableBoolean isBluetoothConnected;
        super.onResume();
        if (UploadFilesService.INSTANCE.getServiceStatus()) {
            return;
        }
        if (!getPermissionDialogShows()) {
            System.out.println((Object) "scan from main");
            doScanDevices();
        }
        BottomSheetBehavior<View> bottomSheetBehavior = this.languageSheetBehaviour;
        ActivityMainBinding activityMainBinding = null;
        if (bottomSheetBehavior == null) {
            Intrinsics.throwUninitializedPropertyAccessException("languageSheetBehaviour");
            bottomSheetBehavior = null;
        }
        bottomSheetBehavior.setState(5);
        ActivityMainBinding activityMainBinding2 = this.binding;
        if (activityMainBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityMainBinding = activityMainBinding2;
        }
        MainActivityViewModel model = activityMainBinding.getModel();
        if (model != null && (isBluetoothConnected = model.getIsBluetoothConnected()) != null) {
            isBluetoothConnected.set(getBleManager().isBleEnabledAndDeviceConnected());
        }
        checkCarFuelType();
        getViewModel().fetchAllNotifications();
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        DataLayerManager.INSTANCE.from(this).sendIsRunningAppOnPhone(RunningAppOnPhoneStatus.OFF);
    }

    @Override // com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(getMReceiver());
        SchemaEmergencySituationsManager.INSTANCE.from(this).stopCheckForEmergencySituations();
    }

    @Override // com.thor.basemodule.gui.activity.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        ActivityMainBinding activityMainBinding = this.binding;
        ActivityMainBinding activityMainBinding2 = null;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            ActivityMainBinding activityMainBinding3 = this.binding;
            if (activityMainBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityMainBinding2 = activityMainBinding3;
            }
            activityMainBinding2.drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
        new Handler().postDelayed(new Runnable() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.onBackPressed$lambda$6();
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBackPressed$lambda$6() {
        System.exit(0);
        throw new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.image_view_close) {
            onCloseMenu();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_update_software) {
            onUpdateSoftware();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_new_device) {
            onNewDevice();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_demo_mode) {
            onDemoMode();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_my_car) {
            onOpenCarInfo();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_share_logs) {
            onShareLogs();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_support_form) {
            onOpenThorTechSupport();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_settings) {
            onOpenSettings();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.floating_button_add_sound) {
            onAddSound();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.button_add_sound) {
            onAddSound();
        } else if (numValueOf != null && numValueOf.intValue() == R.id.text_notifications) {
            onOpenNotificationsActivity();
        }
    }

    @Override // com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity
    public void onScanFailed(int errorCode) {
        Timber.INSTANCE.e("onScanFailed: %s", Integer.valueOf(errorCode));
    }

    @Override // com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity
    public void onStartScanning() {
        Timber.INSTANCE.i("onStartScanning", new Object[0]);
    }

    @Override // com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity
    public void onStopScanning() {
        Timber.INSTANCE.i("onStopScanning", new Object[0]);
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity
    public void onMessageEvent(StartUpdateFirmwareEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        Settings.INSTANCE.saveIsNeedToUpdateFirmware(true);
    }

    private final void initViewPager() {
        ActivityMainBinding activityMainBinding = this.binding;
        ActivityMainBinding activityMainBinding2 = null;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        activityMainBinding.switchShopMode.setOnShopModeChangeListener(new ShopModeSwitchView.OnShopModeChangedListener() { // from class: com.thor.app.gui.activities.main.MainActivity.initViewPager.1

            /* compiled from: MainActivity.kt */
            @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
            /* renamed from: com.thor.app.gui.activities.main.MainActivity$initViewPager$1$WhenMappings */
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[ShopModeSwitchView.ShopMode.values().length];
                    try {
                        iArr[ShopModeSwitchView.ShopMode.CAR_SOUND.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[ShopModeSwitchView.ShopMode.BOOMBOX.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // com.thor.app.gui.widget.ShopModeSwitchView.OnShopModeChangedListener
            public void onModeChange(ShopModeSwitchView.ShopMode mode) {
                Intrinsics.checkNotNullParameter(mode, "mode");
                int i = WhenMappings.$EnumSwitchMapping$0[mode.ordinal()];
                if (i == 1) {
                    MainActivity.this.getViewModel().getIsSguSoundConfig().set(false);
                    MainActivity.this.getSupportFragmentManager().beginTransaction().disallowAddToBackStack().setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim).replace(R.id.fragment_container, MainSoundsFragment.INSTANCE.newInstance(MainActivity.this.getIntent().getAction())).commit();
                    DataLayerManager.INSTANCE.from(MainActivity.this).sendIsRunningAppOnPhone(RunningAppOnPhoneStatus.MAIN);
                } else {
                    if (i != 2) {
                        return;
                    }
                    MainActivity.this.getSupportFragmentManager().beginTransaction().disallowAddToBackStack().setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim).replace(R.id.fragment_container, SguSoundsFragment.INSTANCE.newInstance()).commit();
                    DataLayerManager.INSTANCE.from(MainActivity.this).sendIsRunningAppOnPhone(RunningAppOnPhoneStatus.OFF);
                }
            }
        });
        ActivityMainBinding activityMainBinding3 = this.binding;
        if (activityMainBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityMainBinding2 = activityMainBinding3;
        }
        activityMainBinding2.switchShopMode.setCurrentMode(ShopModeSwitchView.ShopMode.CAR_SOUND);
    }

    private final void initMainMenu() {
        ObservableField<String> userId;
        ObservableField<String> appVersion;
        ObservableField<String> phoneNumber;
        ActivityMainBinding activityMainBinding = this.binding;
        ActivityMainBinding activityMainBinding2 = null;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        activityMainBinding.toolbarWidget.enableBluetoothIndicator(true);
        ActivityMainBinding activityMainBinding3 = this.binding;
        if (activityMainBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding3 = null;
        }
        activityMainBinding3.toolbarWidget.setHomeImageResource(2131231141);
        ActivityMainBinding activityMainBinding4 = this.binding;
        if (activityMainBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding4 = null;
        }
        activityMainBinding4.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.initMainMenu$lambda$7(this.f$0, view);
            }
        });
        ActivityMainBinding activityMainBinding5 = this.binding;
        if (activityMainBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding5 = null;
        }
        MainActivity mainActivity = this;
        activityMainBinding5.layoutMainMenu.imageViewClose.setOnClickListener(mainActivity);
        ActivityMainBinding activityMainBinding6 = this.binding;
        if (activityMainBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding6 = null;
        }
        activityMainBinding6.layoutMainMenu.textViewVersion.setText("ver. 1.8.7");
        ActivityMainBinding activityMainBinding7 = this.binding;
        if (activityMainBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding7 = null;
        }
        TextView textView = activityMainBinding7.layoutMainMenu.textViewUpdateSoftware;
        Intrinsics.checkNotNullExpressionValue(textView, "binding.layoutMainMenu.textViewUpdateSoftware");
        ViewKt.setOnVeryLongClickListener$default(textView, 3000L, new C01602(this), false, 4, null);
        ActivityMainBinding activityMainBinding8 = this.binding;
        if (activityMainBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding8 = null;
        }
        TextView textView2 = activityMainBinding8.layoutMainMenu.textViewNewDevice;
        Intrinsics.checkNotNullExpressionValue(textView2, "binding.layoutMainMenu.textViewNewDevice");
        ViewKt.setOnVeryLongClickListener$default(textView2, 10000L, new AnonymousClass3(this), false, 4, null);
        ActivityMainBinding activityMainBinding9 = this.binding;
        if (activityMainBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding9 = null;
        }
        activityMainBinding9.layoutMainMenu.textViewUpdateSoftware.setOnClickListener(mainActivity);
        ActivityMainBinding activityMainBinding10 = this.binding;
        if (activityMainBinding10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding10 = null;
        }
        activityMainBinding10.layoutMainMenu.textNotifications.setOnClickListener(mainActivity);
        ActivityMainBinding activityMainBinding11 = this.binding;
        if (activityMainBinding11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding11 = null;
        }
        activityMainBinding11.layoutMainMenu.textViewNewDevice.setOnClickListener(mainActivity);
        ActivityMainBinding activityMainBinding12 = this.binding;
        if (activityMainBinding12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding12 = null;
        }
        activityMainBinding12.layoutMainMenu.textViewDemoMode.setOnClickListener(mainActivity);
        ActivityMainBinding activityMainBinding13 = this.binding;
        if (activityMainBinding13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding13 = null;
        }
        TextView textView3 = activityMainBinding13.layoutMainMenu.textViewDemoMode;
        Intrinsics.checkNotNullExpressionValue(textView3, "binding.layoutMainMenu.textViewDemoMode");
        ViewKt.setOnVeryLongClickListener$default(textView3, 3000L, new AnonymousClass4(this), false, 4, null);
        ActivityMainBinding activityMainBinding14 = this.binding;
        if (activityMainBinding14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding14 = null;
        }
        activityMainBinding14.layoutMainMenu.textViewMyCar.setOnClickListener(mainActivity);
        ActivityMainBinding activityMainBinding15 = this.binding;
        if (activityMainBinding15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding15 = null;
        }
        activityMainBinding15.layoutMainMenu.textViewLanguage.setOnClickListener(mainActivity);
        ActivityMainBinding activityMainBinding16 = this.binding;
        if (activityMainBinding16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding16 = null;
        }
        activityMainBinding16.layoutMainMenu.setMainMenu(new MainMenuViewModel());
        ActivityMainBinding activityMainBinding17 = this.binding;
        if (activityMainBinding17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding17 = null;
        }
        MainMenuViewModel mainMenu = activityMainBinding17.layoutMainMenu.getMainMenu();
        if (mainMenu != null && (phoneNumber = mainMenu.getPhoneNumber()) != null) {
            phoneNumber.set(getString(R.string.support_number_phone));
        }
        ActivityMainBinding activityMainBinding18 = this.binding;
        if (activityMainBinding18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding18 = null;
        }
        MainMenuViewModel mainMenu2 = activityMainBinding18.layoutMainMenu.getMainMenu();
        if (mainMenu2 != null && (appVersion = mainMenu2.getAppVersion()) != null) {
            appVersion.set(BuildConfig.VERSION_NAME);
        }
        ActivityMainBinding activityMainBinding19 = this.binding;
        if (activityMainBinding19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding19 = null;
        }
        MainMenuViewModel mainMenu3 = activityMainBinding19.layoutMainMenu.getMainMenu();
        if (mainMenu3 != null && (userId = mainMenu3.getUserId()) != null) {
            userId.set(String.valueOf(Settings.getUserId()));
        }
        ActivityMainBinding activityMainBinding20 = this.binding;
        if (activityMainBinding20 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding20 = null;
        }
        activityMainBinding20.layoutMainMenu.textViewShareLogs.setOnClickListener(mainActivity);
        ActivityMainBinding activityMainBinding21 = this.binding;
        if (activityMainBinding21 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding21 = null;
        }
        activityMainBinding21.layoutMainMenu.textViewSupportForm.setOnClickListener(mainActivity);
        ActivityMainBinding activityMainBinding22 = this.binding;
        if (activityMainBinding22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding22 = null;
        }
        AppCompatTextView appCompatTextView = activityMainBinding22.layoutMainMenu.textViewSupportForm;
        Intrinsics.checkNotNullExpressionValue(appCompatTextView, "binding.layoutMainMenu.textViewSupportForm");
        ViewKt.setOnVeryLongClickListener(appCompatTextView, 3000L, new AnonymousClass5(this), false);
        ActivityMainBinding activityMainBinding23 = this.binding;
        if (activityMainBinding23 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding23 = null;
        }
        activityMainBinding23.layoutMainMenu.textViewSettings.setOnClickListener(mainActivity);
        ActivityMainBinding activityMainBinding24 = this.binding;
        if (activityMainBinding24 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding24 = null;
        }
        AppCompatTextView appCompatTextView2 = activityMainBinding24.layoutMainMenu.textViewSettings;
        Intrinsics.checkNotNullExpressionValue(appCompatTextView2, "binding.layoutMainMenu.textViewSettings");
        ViewKt.setOnVeryLongClickListener(appCompatTextView2, 3000L, new AnonymousClass6(this), false);
        ActivityMainBinding activityMainBinding25 = this.binding;
        if (activityMainBinding25 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding25 = null;
        }
        activityMainBinding25.buttonAddSound.setOnClickListener(mainActivity);
        ActivityMainBinding activityMainBinding26 = this.binding;
        if (activityMainBinding26 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding26 = null;
        }
        activityMainBinding26.floatingButtonAddSound.setOnClickListener(mainActivity);
        ActivityMainBinding activityMainBinding27 = this.binding;
        if (activityMainBinding27 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding27 = null;
        }
        activityMainBinding27.layoutMainMenu.textViewNewDevice.setVisibility(8);
        ActivityMainBinding activityMainBinding28 = this.binding;
        if (activityMainBinding28 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding28 = null;
        }
        activityMainBinding28.layoutMainMenu.textViewMyCar.setVisibility(8);
        ActivityMainBinding activityMainBinding29 = this.binding;
        if (activityMainBinding29 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityMainBinding2 = activityMainBinding29;
        }
        activityMainBinding2.layoutMainMenu.textViewSettings.setVisibility(0);
        updateProfileInfo();
        initSguConfig();
        isNotificationsUnread();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initMainMenu$lambda$7(MainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onOpenMenu();
    }

    /* compiled from: MainActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.MainActivity$initMainMenu$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01602 extends FunctionReferenceImpl implements Function0<Unit> {
        C01602(Object obj) {
            super(0, obj, MainActivity.class, "onFormatFlash", "onFormatFlash()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((MainActivity) this.receiver).onFormatFlash();
        }
    }

    /* compiled from: MainActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.MainActivity$initMainMenu$3, reason: invalid class name */
    /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function0<Unit> {
        AnonymousClass3(Object obj) {
            super(0, obj, MainActivity.class, "showServerSelectDialog", "showServerSelectDialog()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((MainActivity) this.receiver).showServerSelectDialog();
        }
    }

    /* compiled from: MainActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.MainActivity$initMainMenu$4, reason: invalid class name */
    /* synthetic */ class AnonymousClass4 extends FunctionReferenceImpl implements Function0<Unit> {
        AnonymousClass4(Object obj) {
            super(0, obj, MainActivity.class, "onFormatFlash", "onFormatFlash()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((MainActivity) this.receiver).onFormatFlash();
        }
    }

    /* compiled from: MainActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.MainActivity$initMainMenu$5, reason: invalid class name */
    /* synthetic */ class AnonymousClass5 extends FunctionReferenceImpl implements Function0<Unit> {
        AnonymousClass5(Object obj) {
            super(0, obj, MainActivity.class, "showLogEnterPasswordDialog", "showLogEnterPasswordDialog()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((MainActivity) this.receiver).showLogEnterPasswordDialog();
        }
    }

    /* compiled from: MainActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.MainActivity$initMainMenu$6, reason: invalid class name */
    /* synthetic */ class AnonymousClass6 extends FunctionReferenceImpl implements Function0<Unit> {
        AnonymousClass6(Object obj) {
            super(0, obj, MainActivity.class, "showFirmwareEnterPasswordDialog", "showFirmwareEnterPasswordDialog()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((MainActivity) this.receiver).showFirmwareEnterPasswordDialog();
        }
    }

    private final void initSguConfig() {
        ActivityMainBinding activityMainBinding = this.binding;
        ActivityMainBinding activityMainBinding2 = null;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        activityMainBinding.seekBarSguVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.thor.app.gui.activities.main.MainActivity.initSguConfig.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ObservableField<SguSoundConfig> sguConfig = MainActivity.this.getViewModel().getSguConfig();
                SguSoundConfig sguSoundConfig = MainActivity.this.getViewModel().getSguConfig().get();
                if (sguSoundConfig != null) {
                    sguSoundConfig.setSoundVolume((short) progress);
                } else {
                    sguSoundConfig = null;
                }
                sguConfig.set(sguSoundConfig);
            }
        });
        ActivityMainBinding activityMainBinding3 = this.binding;
        if (activityMainBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding3 = null;
        }
        activityMainBinding3.iconRepeatSguConfig.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.initSguConfig$lambda$9(this.f$0, view);
            }
        });
        ActivityMainBinding activityMainBinding4 = this.binding;
        if (activityMainBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding4 = null;
        }
        activityMainBinding4.iconCloseSguCongig.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.initSguConfig$lambda$10(this.f$0, view);
            }
        });
        ActivityMainBinding activityMainBinding5 = this.binding;
        if (activityMainBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityMainBinding2 = activityMainBinding5;
        }
        activityMainBinding2.driveSelectSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.initSguConfig$lambda$11(this.f$0, view);
            }
        });
        final Function1<Boolean, Unit> function1 = new Function1<Boolean, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.initSguConfig.5
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke2(bool);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Boolean it) {
                ActivityMainBinding activityMainBinding6 = MainActivity.this.binding;
                if (activityMainBinding6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activityMainBinding6 = null;
                }
                SwitchMaterial switchMaterial = activityMainBinding6.driveSelectSwitch;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                switchMaterial.setChecked(it.booleanValue());
            }
        };
        getViewModel().isSguDriveSelect().observe(this, new Observer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.initSguConfig$lambda$12(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initSguConfig$lambda$9(MainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ObservableField<SguSoundConfig> sguConfig = this$0.getViewModel().getSguConfig();
        SguSoundConfig sguSoundConfig = this$0.getViewModel().getSguConfig().get();
        ActivityMainBinding activityMainBinding = null;
        if (sguSoundConfig != null) {
            short s = 1;
            if (sguSoundConfig.getRepeatCycle() == 1) {
                ActivityMainBinding activityMainBinding2 = this$0.binding;
                if (activityMainBinding2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    activityMainBinding = activityMainBinding2;
                }
                activityMainBinding.iconRepeatSguConfig.setAlpha(1.0f);
                s = Short.MAX_VALUE;
            } else {
                EventBus.getDefault().post(StopPlayingSguSoundEvent.INSTANCE);
                ActivityMainBinding activityMainBinding3 = this$0.binding;
                if (activityMainBinding3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    activityMainBinding = activityMainBinding3;
                }
                activityMainBinding.iconRepeatSguConfig.setAlpha(0.6f);
            }
            sguSoundConfig.setRepeatCycle(s);
        } else {
            sguSoundConfig = null;
        }
        sguConfig.set(sguSoundConfig);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initSguConfig$lambda$10(MainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getViewModel().getIsSguSoundConfig().set(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initSguConfig$lambda$11(MainActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        SguSoundsViewModel sguSoundsViewModel = this$0.getSguSoundsViewModel();
        ActivityMainBinding activityMainBinding = this$0.binding;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        sguSoundsViewModel.changeSoundDriveSelectStatus(activityMainBinding.driveSelectSwitch.isChecked());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initSguConfig$lambda$12(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void onAddSound() {
        MainActivity mainActivity = this;
        Intent intent = new Intent(mainActivity, (Class<?>) ShopActivity.class);
        ActivityMainBinding activityMainBinding = this.binding;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        intent.putExtra(ShopModeSwitchView.SHOP_MODE_BUNDLE, activityMainBinding.switchShopMode.getCurrentMode());
        mainActivity.startActivity(intent);
    }

    private final void onOpenMenu() {
        ActivityMainBinding activityMainBinding = this.binding;
        ActivityMainBinding activityMainBinding2 = null;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            ActivityMainBinding activityMainBinding3 = this.binding;
            if (activityMainBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityMainBinding2 = activityMainBinding3;
            }
            activityMainBinding2.drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        ActivityMainBinding activityMainBinding4 = this.binding;
        if (activityMainBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityMainBinding2 = activityMainBinding4;
        }
        activityMainBinding2.drawerLayout.openDrawer(GravityCompat.START);
    }

    private final void onUpdateSoftware() {
        Timber.INSTANCE.i("onUpdateSoftware", new Object[0]);
        ActivityMainBinding activityMainBinding = this.binding;
        ActivityMainBinding activityMainBinding2 = null;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            ActivityMainBinding activityMainBinding3 = this.binding;
            if (activityMainBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityMainBinding2 = activityMainBinding3;
            }
            activityMainBinding2.drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (getBleManager().getMStateConnected()) {
            if (SchemaEmergencySituationsManager.INSTANCE.from(this).checkFirmwareVersionOnDifference() != null) {
                openUpdateFirmwareDialog();
                return;
            } else {
                createDisableUpdateAlertDialog().show();
                return;
            }
        }
        DialogManager.INSTANCE.createNoConnectionToBoardAlertDialog(this).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onFormatFlash() {
        Timber.INSTANCE.i("onUpdateSoftware", new Object[0]);
        ActivityMainBinding activityMainBinding = this.binding;
        ActivityMainBinding activityMainBinding2 = null;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            ActivityMainBinding activityMainBinding3 = this.binding;
            if (activityMainBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityMainBinding2 = activityMainBinding3;
            }
            activityMainBinding2.drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (getBleManager().getMStateConnected()) {
            Log.i("TEST21212", "isConnected + onFormatFlash");
            createFormatFlashAlertDialog(false).show();
        } else {
            DialogManager.INSTANCE.createNoConnectionToBoardAlertDialog(this).show();
        }
    }

    private final void onNewDevice() {
        if (ConnectivityAndInternetAccess.INSTANCE.isConnected(this)) {
            createLogoutAlertDialog().show();
            return;
        }
        ActivityMainBinding activityMainBinding = this.binding;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        Snackbar.make(activityMainBinding.getRoot(), R.string.warning_service_is_unavailable, 0).show();
    }

    private final void onDemoMode() {
        onBackPressed();
        startActivity(new Intent(this, (Class<?>) DemoActivity.class));
    }

    private final void onOpenCarInfo() {
        if (getBleManager().isBleEnabledAndDeviceConnected()) {
            checkDeviceCanFileStatus();
        } else {
            fetchCarCanInfo();
        }
    }

    private final void fetchCarCanInfo() {
        Observable<CanConfigurationsResponse> observableFetchCanFileUrl;
        SignInResponse profile = Settings.INSTANCE.getProfile();
        if (profile == null || (observableFetchCanFileUrl = getUsersManager().fetchCanFileUrl(profile)) == null) {
            return;
        }
        final Function1<CanConfigurationsResponse, Unit> function1 = new Function1<CanConfigurationsResponse, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity$fetchCarCanInfo$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(CanConfigurationsResponse canConfigurationsResponse) {
                invoke2(canConfigurationsResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(CanConfigurationsResponse canConfigurationsResponse) {
                Timber.INSTANCE.i("CanConfigurationsResponse: %s", canConfigurationsResponse);
                if (canConfigurationsResponse.isSuccessful()) {
                    MainActivity.createCarInfoAlertDialog$default(this.this$0, false, 1, null).show();
                    return;
                }
                AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this.this$0, canConfigurationsResponse.getError(), canConfigurationsResponse.getCode());
                if (alertDialogCreateErrorAlertDialog != null) {
                    alertDialogCreateErrorAlertDialog.show();
                }
                this.this$0.createCarInfoAlertDialog(true).show();
            }
        };
        Consumer<? super CanConfigurationsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainActivity.fetchCarCanInfo$lambda$16$lambda$14(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity$fetchCarCanInfo$1$2
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
                    AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(this.this$0, th.getMessage(), 0);
                    if (alertDialogCreateErrorAlertDialog2 != null) {
                        alertDialogCreateErrorAlertDialog2.show();
                    }
                } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this.this$0, th.getMessage(), 0)) != null) {
                    alertDialogCreateErrorAlertDialog.show();
                }
                Timber.INSTANCE.e(th);
                MainActivity.createCarInfoAlertDialog$default(this.this$0, false, 1, null).show();
            }
        };
        observableFetchCanFileUrl.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda20
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainActivity.fetchCarCanInfo$lambda$16$lambda$15(function12, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchCarCanInfo$lambda$16$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchCarCanInfo$lambda$16$lambda$15(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void checkCarFuelType() {
        Observable<CarFuelTypeResponse> observableFetchCarFuelType;
        SignInResponse profile = Settings.INSTANCE.getProfile();
        if (profile == null || (observableFetchCarFuelType = getUsersManager().fetchCarFuelType(profile)) == null) {
            return;
        }
        final Function1<CarFuelTypeResponse, Unit> function1 = new Function1<CarFuelTypeResponse, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity$checkCarFuelType$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(CarFuelTypeResponse carFuelTypeResponse) {
                invoke2(carFuelTypeResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(CarFuelTypeResponse it) {
                MainActivity mainActivity = this.this$0;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                mainActivity.handleCarFuelTypeResponse(it);
            }
        };
        Consumer<? super CarFuelTypeResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainActivity.checkCarFuelType$lambda$19$lambda$17(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity$checkCarFuelType$1$2
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
                    AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(this.this$0, th.getMessage(), 0);
                    if (alertDialogCreateErrorAlertDialog2 != null) {
                        alertDialogCreateErrorAlertDialog2.show();
                    }
                } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this.this$0, th.getMessage(), 0)) != null) {
                    alertDialogCreateErrorAlertDialog.show();
                }
                Timber.INSTANCE.e(th);
            }
        };
        observableFetchCarFuelType.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainActivity.checkCarFuelType$lambda$19$lambda$18(function12, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkCarFuelType$lambda$19$lambda$17(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkCarFuelType$lambda$19$lambda$18(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleCarFuelTypeResponse(CarFuelTypeResponse response) {
        Timber.INSTANCE.i("CarFuelType: %s", response);
        if (response.isSuccessful()) {
            if (Settings.INSTANCE.getCarFuelType() != response.getFuelId()) {
                getBleManager().clear();
                Settings.INSTANCE.saveCarFuelType(response.getFuelId());
                Intent intent = new Intent(this, (Class<?>) SplashActivity.class);
                intent.setFlags(268468224);
                intent.putExtra("reload", true);
                startActivity(intent);
                return;
            }
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(this, response.getError(), Integer.valueOf(response.getCode()));
        if (alertDialogCreateErrorAlertDialogWithSendLogOption != null) {
            alertDialogCreateErrorAlertDialogWithSendLogOption.show();
        }
    }

    private final void onChangeCar() {
        Timber.INSTANCE.i("onChangeCar", new Object[0]);
        if (getBleManager().getMStateConnected()) {
            MainActivity mainActivity = this;
            if (ConnectivityAndInternetAccess.INSTANCE.isConnected(mainActivity)) {
                ChangeCarInfo changeCarInfo = new ChangeCarInfo(null, 0, 0, null, 0, null, 0, null, 0, null, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null);
                changeCarInfo.setToken(Settings.getUserGoogleToken());
                changeCarInfo.setUserId(Settings.getUserId());
                Intent intent = new Intent(mainActivity, (Class<?>) ChangeCarActivity.class);
                intent.putExtra(ChangeCarInfo.BUNDLE_NAME, changeCarInfo);
                startActivity(intent);
                return;
            }
        }
        if (!getBleManager().getMStateConnected()) {
            DialogManager.INSTANCE.createNoConnectionToBoardAlertDialog(this).show();
            return;
        }
        ActivityMainBinding activityMainBinding = this.binding;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        Snackbar.make(activityMainBinding.getRoot(), R.string.warning_service_is_unavailable, 0).show();
    }

    private final void onLanguage() {
        Timber.INSTANCE.i("onLanguage", new Object[0]);
        BottomSheetBehavior<View> bottomSheetBehavior = this.languageSheetBehaviour;
        BottomSheetBehavior<View> bottomSheetBehavior2 = null;
        if (bottomSheetBehavior == null) {
            Intrinsics.throwUninitializedPropertyAccessException("languageSheetBehaviour");
            bottomSheetBehavior = null;
        }
        if (bottomSheetBehavior.getState() == 3) {
            BottomSheetBehavior<View> bottomSheetBehavior3 = this.languageSheetBehaviour;
            if (bottomSheetBehavior3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("languageSheetBehaviour");
            } else {
                bottomSheetBehavior2 = bottomSheetBehavior3;
            }
            bottomSheetBehavior2.setState(5);
            return;
        }
        BottomSheetBehavior<View> bottomSheetBehavior4 = this.languageSheetBehaviour;
        if (bottomSheetBehavior4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("languageSheetBehaviour");
        } else {
            bottomSheetBehavior2 = bottomSheetBehavior4;
        }
        bottomSheetBehavior2.setState(3);
    }

    public final void checkNeedUpdateFWM() {
        if (SchemaEmergencySituationsManager.INSTANCE.from(this).checkFirmwareVersionOnDifference() == DeviceParameters.PARAMS.NEED_UPDATE_FIRMWARE) {
            enableUpdateSoftware();
        }
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity
    protected void enableUpdateSoftware() {
        ObservableBoolean isUpdateSoftware;
        ActivityMainBinding activityMainBinding = this.binding;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        MainMenuViewModel mainMenu = activityMainBinding.layoutMainMenu.getMainMenu();
        if (mainMenu != null && (isUpdateSoftware = mainMenu.getIsUpdateSoftware()) != null) {
            isUpdateSoftware.set(true);
        }
        Settings.INSTANCE.saveIsNeedToUpdateFirmware(true);
        if (this.firstshow) {
            this.firstshow = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886084);
            builder.setMessage(R.string.message_update_firmware).setPositiveButton(R.string.text_update, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda25
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.enableUpdateSoftware$lambda$20(this.f$0, dialogInterface, i);
                }
            }).setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda26
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).setCancelable(false);
            builder.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void enableUpdateSoftware$lambda$20(MainActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.openUpdateFirmwareDialog();
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity
    protected void disableUpdateSoftware() {
        ObservableBoolean isUpdateSoftware;
        Settings.INSTANCE.saveIsNeedToUpdateFirmware(false);
        ActivityMainBinding activityMainBinding = this.binding;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        MainMenuViewModel mainMenu = activityMainBinding.layoutMainMenu.getMainMenu();
        if (mainMenu == null || (isUpdateSoftware = mainMenu.getIsUpdateSoftware()) == null) {
            return;
        }
        isUpdateSoftware.set(false);
    }

    private final void initLanguage() {
        ActivityMainBinding activityMainBinding = this.binding;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        BottomSheetBehavior<View> bottomSheetBehaviorFrom = BottomSheetBehavior.from(activityMainBinding.layoutMainMenu.layoutLanguage);
        Intrinsics.checkNotNullExpressionValue(bottomSheetBehaviorFrom, "from(binding.layoutMainMenu.layoutLanguage)");
        this.languageSheetBehaviour = bottomSheetBehaviorFrom;
    }

    private final void onChangeLanguage(String languageCode) {
        Settings.INSTANCE.saveLanguageCode(languageCode);
        recreate();
    }

    private final void changeLanguage() {
        Settings settings = Settings.INSTANCE;
        String languageCode = Settings.getLanguageCode();
        if (TextUtils.isEmpty(languageCode)) {
            return;
        }
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration(getResources().getConfiguration());
        configuration.setLocale(locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        Timber.INSTANCE.i("Language code: %s", languageCode);
    }

    private final void onCloseMenu() {
        BottomSheetBehavior<View> bottomSheetBehavior = this.languageSheetBehaviour;
        ActivityMainBinding activityMainBinding = null;
        if (bottomSheetBehavior == null) {
            Intrinsics.throwUninitializedPropertyAccessException("languageSheetBehaviour");
            bottomSheetBehavior = null;
        }
        bottomSheetBehavior.setState(5);
        ActivityMainBinding activityMainBinding2 = this.binding;
        if (activityMainBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityMainBinding = activityMainBinding2;
        }
        activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onSupportCall() {
        Dexter.withActivity(this).withPermissions("android.permission.CALL_PHONE").withListener(new MultiplePermissionsListener() { // from class: com.thor.app.gui.activities.main.MainActivity.onSupportCall.1
            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                Boolean boolValueOf = report != null ? Boolean.valueOf(report.areAllPermissionsGranted()) : null;
                Intrinsics.checkNotNull(boolValueOf);
                if (!boolValueOf.booleanValue()) {
                    MainActivity.this.onSupportCall();
                    return;
                }
                Intent intent = new Intent("android.intent.action.CALL");
                intent.setData(Uri.parse(MainActivity.this.getString(R.string.support_number_phone_call)));
                MainActivity.this.startActivity(intent);
            }

            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                Timber.INSTANCE.i("onPermissionRationaleShouldBeShown", new Object[0]);
                if (token != null) {
                    token.continuePermissionRequest();
                }
            }
        }).check();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onShareLogs() {
        MainActivity mainActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(mainActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, mainActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", FileLogger.LOGS_FILE_NAME);
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        intent.addFlags(1);
        intent.setType("plain/*");
        startActivity(intent);
    }

    private final void onSendLogsToApi() {
        MainActivity mainActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(mainActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, mainActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        getViewModel().sendLogs(uriForFile);
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

    private final void onOpenDownloadTelegramLink() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=org.telegram.messenger")));
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://t.me/ThorTechSupport")));
        }
    }

    private final void onOpenSettings() {
        MainActivity mainActivity = this;
        mainActivity.startActivity(new Intent(mainActivity, (Class<?>) SettingsActivity.class));
    }

    private final void onOpenNotificationsActivity() {
        MainActivity mainActivity = this;
        mainActivity.startActivity(new Intent(mainActivity, (Class<?>) NotificationsActivity.class));
    }

    private final AlertDialog createDisableUpdateAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886084);
        String string = getString(R.string.text_latest_update_firmware);
        Intrinsics.checkNotNullExpressionValue(string, "getString(\n            R…update_firmware\n        )");
        builder.setMessage(string).setPositiveButton(android.R.string.yes, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    static /* synthetic */ AlertDialog createCarInfoAlertDialog$default(MainActivity mainActivity, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return mainActivity.createCarInfoAlertDialog(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final AlertDialog createCarInfoAlertDialog(boolean isNoCanFile) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886083);
        SignInResponse profile = Settings.INSTANCE.getProfile();
        String string = getString(R.string.text_your_car);
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.text_your_car)");
        String str = "";
        if (isNoCanFile) {
            string = "";
            str = getString(R.string.text_can_file_not_found) + "\n\n";
        }
        String string2 = Settings.INSTANCE.isDeviceHasCanFileData() ? getString(R.string.text_car_status_connected) : getString(R.string.text_car_status_no_connection);
        Intrinsics.checkNotNullExpressionValue(string2, "if (Settings.isDeviceHas…car_status_no_connection)");
        Object[] objArr = new Object[5];
        objArr[0] = profile != null ? profile.getCarMarkName() : null;
        objArr[1] = profile != null ? profile.getCarModelName() : null;
        objArr[2] = profile != null ? profile.getCarGenerationName() : null;
        objArr[3] = profile != null ? profile.getCarSerieName() : null;
        objArr[4] = string2;
        String string3 = getString(R.string.text_your_car_info_with_status, objArr);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(\n            R…rieName, status\n        )");
        builder.setTitle(string).setMessage(str + string3).setNegativeButton(android.R.string.yes, (DialogInterface.OnClickListener) null).setPositiveButton(R.string.text_change, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.createCarInfoAlertDialog$lambda$23(this.f$0, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createCarInfoAlertDialog$lambda$23(MainActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onChangeCar();
    }

    private final AlertDialog createLogoutAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886083);
        builder.setTitle(R.string.text_entering_new_device).setMessage(R.string.message_entering_new_device).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda19
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.createLogoutAlertDialog$lambda$25(this.f$0, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createLogoutAlertDialog$lambda$25(final MainActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onLogout();
        Single<Boolean> singleIsConnectedToWear = this$0.getMDataLayerHelper().isConnectedToWear();
        final Function1<Boolean, Unit> function1 = new Function1<Boolean, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity$createLogoutAlertDialog$1$1
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
                this.this$0.getMDataLayerHelper().onStartBluetoothSearchWearableActivity();
            }
        };
        singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda24
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainActivity.createLogoutAlertDialog$lambda$25$lambda$24(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createLogoutAlertDialog$lambda$25$lambda$24(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void onLogout() {
        Settings.saveIsCheckedEmrgencySituations(false);
        Observable<BaseResponse> observableRemoveNotification = getUsersManager().removeNotification();
        if (observableRemoveNotification != null) {
            final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.onLogout.1
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
                    if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(MainActivity.this, baseResponse.getError(), Integer.valueOf(baseResponse.getCode()))) != null) {
                        alertDialogCreateErrorAlertDialogWithSendLogOption.show();
                    }
                    Timber.INSTANCE.i("Response: %s", baseResponse);
                }
            };
            Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda12
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainActivity.onLogout$lambda$26(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.onLogout.2
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
                        AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(MainActivity.this, th.getMessage(), 0);
                        if (alertDialogCreateErrorAlertDialog2 != null) {
                            alertDialogCreateErrorAlertDialog2.show();
                        }
                    } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(MainActivity.this, th.getMessage(), 0)) != null) {
                        alertDialogCreateErrorAlertDialog.show();
                    }
                    Timber.INSTANCE.e(th);
                }
            };
            observableRemoveNotification.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda13
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainActivity.onLogout$lambda$27(function12, obj);
                }
            });
        }
        getBleManager().disconnect(true);
        Settings.removeAllProperties();
        MainActivity mainActivity = this;
        Settings.clearCookies(mainActivity);
        DataLayerManager.INSTANCE.from(mainActivity).sendIsAccessSession(false);
        ThorDatabase.INSTANCE.from(mainActivity).installedSoundPackageDao().deleteAllElements();
        ThorDatabase.INSTANCE.from(mainActivity).shopSoundPackageDao().deleteAllElements();
        Intent intent = new Intent(mainActivity, (Class<?>) SplashActivity.class);
        intent.setFlags(268468224);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLogout$lambda$26(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLogout$lambda$27(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void onFullLogout() {
        Settings.saveIsCheckedEmrgencySituations(false);
        Observable<BaseResponse> observableRemoveNotification = getUsersManager().removeNotification();
        if (observableRemoveNotification != null) {
            final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.onFullLogout.1
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
                    if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(MainActivity.this, baseResponse.getError(), Integer.valueOf(baseResponse.getCode()))) != null) {
                        alertDialogCreateErrorAlertDialogWithSendLogOption.show();
                    }
                    Timber.INSTANCE.i("Response: %s", baseResponse);
                }
            };
            Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda7
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainActivity.onFullLogout$lambda$28(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.onFullLogout.2
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
                        AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(MainActivity.this, th.getMessage(), 0);
                        if (alertDialogCreateErrorAlertDialog2 != null) {
                            alertDialogCreateErrorAlertDialog2.show();
                        }
                    } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(MainActivity.this, th.getMessage(), 0)) != null) {
                        alertDialogCreateErrorAlertDialog.show();
                    }
                    Timber.INSTANCE.e(th);
                }
            };
            observableRemoveNotification.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainActivity.onFullLogout$lambda$29(function12, obj);
                }
            });
        }
        getBleManager().disconnect(true);
        Settings.removeAllProperties();
        MainActivity mainActivity = this;
        Settings.clearCookies(mainActivity);
        Settings.saveUserGoogleToken("");
        Settings.saveUserGoogleUserId("");
        DataLayerManager.INSTANCE.from(mainActivity).sendIsAccessSession(false);
        ThorDatabase.INSTANCE.from(mainActivity).installedSoundPackageDao().deleteAllElements();
        ThorDatabase.INSTANCE.from(mainActivity).shopSoundPackageDao().deleteAllElements();
        Intent intent = new Intent(mainActivity, (Class<?>) SplashActivity.class);
        intent.setFlags(268468224);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFullLogout$lambda$28(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFullLogout$lambda$29(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void updateProfileInfo() {
        ObservableField<String> version;
        ObservableField<String> serialNumber;
        SignInResponse profile = Settings.INSTANCE.getProfile();
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        ActivityMainBinding activityMainBinding = null;
        Short shValueOf = hardwareProfile != null ? Short.valueOf(hardwareProfile.getVersionFirmware()) : null;
        Short shValueOf2 = hardwareProfile != null ? Short.valueOf(hardwareProfile.getVersionHardware()) : null;
        ActivityMainBinding activityMainBinding2 = this.binding;
        if (activityMainBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding2 = null;
        }
        MainMenuViewModel mainMenu = activityMainBinding2.layoutMainMenu.getMainMenu();
        if (mainMenu != null && (serialNumber = mainMenu.getSerialNumber()) != null) {
            serialNumber.set(BuildConfig.APP_PREFIX + (profile != null ? profile.getDeviceSN() : null));
        }
        boolean z = false;
        if (hardwareProfile != null && !hardwareProfile.isDefaultValuesSet()) {
            z = true;
        }
        if (!z || shValueOf == null) {
            return;
        }
        shValueOf.shortValue();
        ActivityMainBinding activityMainBinding3 = this.binding;
        if (activityMainBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityMainBinding = activityMainBinding3;
        }
        MainMenuViewModel mainMenu2 = activityMainBinding.layoutMainMenu.getMainMenu();
        if (mainMenu2 == null || (version = mainMenu2.getVersion()) == null) {
            return;
        }
        version.set(shValueOf + "." + shValueOf2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showServerSelectDialog() {
        DialogManager.INSTANCE.createServerSelectAlertDialog(this, new Function0<Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.showServerSelectDialog.1
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
                if (Settings.getSelectedServer() != 1092) {
                    Settings.INSTANCE.saveSelectedServer(com.thor.businessmodule.settings.Constants.SERVER_DEVELOPER);
                    MainActivity.this.onRestartApplication();
                }
            }
        }, new Function0<Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.showServerSelectDialog.2
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
                if (Settings.getSelectedServer() == 1092) {
                    Settings.INSTANCE.saveSelectedServer(com.thor.businessmodule.settings.Constants.SERVER_RELEASE);
                    MainActivity.this.onRestartApplication();
                }
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showLogEnterPasswordDialog() {
        ActivityMainBinding activityMainBinding = this.binding;
        ActivityMainBinding activityMainBinding2 = null;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            ActivityMainBinding activityMainBinding3 = this.binding;
            if (activityMainBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityMainBinding2 = activityMainBinding3;
            }
            activityMainBinding2.drawerLayout.closeDrawer(GravityCompat.START);
        }
        TestersDialogFragment testersDialogFragment = new TestersDialogFragment();
        testersDialogFragment.setOnConfirmListener(new OnConfirmDialogListener() { // from class: com.thor.app.gui.activities.main.MainActivity$showLogEnterPasswordDialog$$inlined$doOnConfirm$1
            @Override // com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener
            public void onConfirm(String info) {
                this.this$0.logPasswordValidation(info);
            }
        });
        testersDialogFragment.show(getSupportFragmentManager(), testersDialogFragment.getClass().getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showFirmwareEnterPasswordDialog() {
        ActivityMainBinding activityMainBinding = this.binding;
        ActivityMainBinding activityMainBinding2 = null;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            ActivityMainBinding activityMainBinding3 = this.binding;
            if (activityMainBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityMainBinding2 = activityMainBinding3;
            }
            activityMainBinding2.drawerLayout.closeDrawer(GravityCompat.START);
        }
        TestersDialogFragment testersDialogFragment = new TestersDialogFragment();
        testersDialogFragment.setOnConfirmListener(new OnConfirmDialogListener() { // from class: com.thor.app.gui.activities.main.MainActivity$showFirmwareEnterPasswordDialog$$inlined$doOnConfirm$1
            @Override // com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener
            public void onConfirm(String info) {
                MainActivity mainActivity = this.this$0;
                Intent intent = new Intent(mainActivity, (Class<?>) FirmwareListActivity.class);
                intent.putExtra(MainActivity.FIRMWARE_PASSWORD, info);
                mainActivity.startActivity(intent);
            }
        });
        testersDialogFragment.show(getSupportFragmentManager(), testersDialogFragment.getClass().getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void logPasswordValidation(String password) {
        getViewModel().logsPasswordValidationResponse(String.valueOf(password));
        getViewModel().setOnSuccessPasswordValidation(new Function1<PasswordValidationResponse, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.logPasswordValidation.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(PasswordValidationResponse passwordValidationResponse) {
                invoke2(passwordValidationResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(PasswordValidationResponse it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (Intrinsics.areEqual((Object) it.getStatus(), (Object) true)) {
                    MainActivity.this.onShareLogs();
                } else {
                    Toast.makeText(MainActivity.this, it.getError_description(), 0).show();
                }
            }
        });
        getViewModel().setOnFailurePasswordValidation(new Function0<Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.logPasswordValidation.2
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
                MainActivity mainActivity = MainActivity.this;
                Toast.makeText(mainActivity, mainActivity.getString(R.string.text_error_message), 0).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onRestartApplication() {
        onLogout();
    }

    private final void remindToSubscribe() {
        if (Settings.INSTANCE.isSubscriptionActive() || !Settings.INSTANCE.isSubscriptionNeedsRemind(7)) {
            return;
        }
        getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.remindToSubscribe$lambda$34(this.f$0);
            }
        }, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void remindToSubscribe$lambda$34(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (Variables.INSTANCE.getSUBSCRIPTION_FEATURE_REQUIREMENTS_SATISFIED()) {
            this$0.openSubscriptionScreen();
        }
    }

    private final void openSubscriptionScreen() {
        startActivity(new Intent(this, (Class<?>) SubscriptionsActivity.class), ActivityOptions.makeSceneTransitionAnimation(this, new Pair[0]).toBundle());
        Settings.INSTANCE.resetSubscriptionRemindDate();
    }

    private final void isNotificationsUnread() {
        final Function1<List<? extends NotificationInfo>, Unit> function1 = new Function1<List<? extends NotificationInfo>, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.isNotificationsUnread.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends NotificationInfo> list) {
                invoke2((List<NotificationInfo>) list);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<NotificationInfo> it) {
                Intrinsics.checkNotNullExpressionValue(it, "it");
                ActivityMainBinding activityMainBinding = null;
                if (!it.isEmpty()) {
                    ActivityMainBinding activityMainBinding2 = MainActivity.this.binding;
                    if (activityMainBinding2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    } else {
                        activityMainBinding = activityMainBinding2;
                    }
                    activityMainBinding.layoutMainMenu.flagNotificationsUnread.setVisibility(0);
                    return;
                }
                ActivityMainBinding activityMainBinding3 = MainActivity.this.binding;
                if (activityMainBinding3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    activityMainBinding = activityMainBinding3;
                }
                activityMainBinding.layoutMainMenu.flagNotificationsUnread.setVisibility(4);
            }
        };
        getViewModel().getNotificationsListLiveData().observe(this, new Observer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.isNotificationsUnread$lambda$35(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void isNotificationsUnread$lambda$35(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Subscribe
    public final void onMessageEvent(BluetoothDeviceConnectionChangedEvent event) {
        ObservableBoolean isBluetoothConnected;
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        ActivityMainBinding activityMainBinding = this.binding;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        MainActivityViewModel model = activityMainBinding.getModel();
        if (model != null && (isBluetoothConnected = model.getIsBluetoothConnected()) != null) {
            isBluetoothConnected.set(getBleManager().isBleEnabledAndDeviceConnected());
        }
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
            } else {
                if (getBleManager().getMUploadingData()) {
                    return;
                }
                getBleManager().readRemoteRssi();
            }
        }
    }

    @Subscribe
    public final void onMessageEvent(SendLogsEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        MainActivity mainActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(mainActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, mainActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), Dispatchers.getIO(), null, new C01741(uriForFile, event, null), 2, null);
    }

    /* compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.main.MainActivity$onMessageEvent$1", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.main.MainActivity$onMessageEvent$1, reason: invalid class name and case insensitive filesystem */
    static final class C01741 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SendLogsEvent $event;
        final /* synthetic */ Uri $path;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01741(Uri uri, SendLogsEvent sendLogsEvent, Continuation<? super C01741> continuation) {
            super(2, continuation);
            this.$path = uri;
            this.$event = sendLogsEvent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MainActivity.this.new C01741(this.$path, this.$event, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01741) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Observable<BaseResponse> observableSendErrorLogToApi = MainActivity.this.getUsersManager().sendErrorLogToApi(this.$path, this.$event.getErrorMessage());
                if (observableSendErrorLogToApi != null) {
                    final C00591 c00591 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.onMessageEvent.1.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<BaseResponse> observableDoOnSubscribe = observableSendErrorLogToApi.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$onMessageEvent$1$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            c00591.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final AnonymousClass2 anonymousClass2 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.onMessageEvent.1.2
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
                        observableDoOnSubscribe.subscribe(new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$onMessageEvent$1$$ExternalSyntheticLambda1
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj2) {
                                anonymousClass2.invoke(obj2);
                            }
                        });
                    }
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    @Subscribe
    public final void onMessageEvent(StopScanBluetoothDevicesEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        doStopScanning();
    }

    @Subscribe
    public final void onMessageEvent(StartScanBluetoothDevicesEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        doScanDevices();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UpdateHardwareProfileEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        updateProfileInfo();
        getUpdateViewModel().versionGet();
        updateProfileInfo();
        getUpdateViewModel().postUpdateResultEvent(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UpdateFirmwareSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        getUpdateViewModel().postUpdateResultEvent(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(ApplyUpdateFirmwareSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        getUpdateViewModel().postUpdateResultEvent(event.isSuccessful());
        disableUpdateSoftware();
        doScanDevices();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(DeviceLockedStateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (event.isLocked()) {
            changeLockDeviceState(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void omMessageEvent(BadUserEvents event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        onFullLogout();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(LoaderChangedEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ActivityMainBinding activityMainBinding = null;
        if (event.isStarted()) {
            ActivityMainBinding activityMainBinding2 = this.binding;
            if (activityMainBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityMainBinding = activityMainBinding2;
            }
            activityMainBinding.progressBar.setVisibility(0);
            return;
        }
        ActivityMainBinding activityMainBinding3 = this.binding;
        if (activityMainBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityMainBinding = activityMainBinding3;
        }
        activityMainBinding.progressBar.setVisibility(8);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(DownloadSettingsFileSuccessEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        updateProfileInfo();
        getUpdateViewModel().versionGet();
        checkNeedBlockDevice();
        checkNeedUpdateFWM();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(DownloadSettingsFileErrorEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        getBleManager().executeHardwareCommand();
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

    private final void checkNeedBlockDevice() {
        String userGoogleUserId = Settings.getUserGoogleUserId();
        if (userGoogleUserId.length() == 0) {
            startActivity(new Intent(this, (Class<?>) SplashActivity.class));
            finish();
            return;
        }
        Observable<SignInResponse> observableConnectToolsDevice = getUsersManager().connectToolsDevice();
        final AnonymousClass1 anonymousClass1 = new AnonymousClass1(userGoogleUserId);
        Consumer<? super SignInResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainActivity.checkNeedBlockDevice$lambda$36(anonymousClass1, obj);
            }
        };
        final AnonymousClass2 anonymousClass2 = new AnonymousClass2(userGoogleUserId);
        observableConnectToolsDevice.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainActivity.checkNeedBlockDevice$lambda$37(anonymousClass2, obj);
            }
        });
    }

    /* compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Lcom/thor/networkmodule/model/responses/SignInResponse;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.MainActivity$checkNeedBlockDevice$1, reason: invalid class name */
    static final class AnonymousClass1 extends Lambda implements Function1<SignInResponse, Unit> {
        final /* synthetic */ String $userId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(String str) {
            super(1);
            this.$userId = str;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(SignInResponse signInResponse) {
            invoke2(signInResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(SignInResponse signInResponse) {
            String deviceSN;
            if (signInResponse.getStatus()) {
                Observable<BaseResponse> observableUpdateVersion = MainActivity.this.getUsersManager().updateVersion();
                final C00561 c00561 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.checkNeedBlockDevice.1.1
                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(BaseResponse baseResponse) {
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                        invoke2(baseResponse);
                        return Unit.INSTANCE;
                    }
                };
                Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$checkNeedBlockDevice$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        MainActivity.AnonymousClass1.invoke$lambda$0(c00561, obj);
                    }
                };
                final AnonymousClass2 anonymousClass2 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.checkNeedBlockDevice.1.2
                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(Throwable th) {
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                        invoke2(th);
                        return Unit.INSTANCE;
                    }
                };
                observableUpdateVersion.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$checkNeedBlockDevice$1$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        MainActivity.AnonymousClass1.invoke$lambda$1(anonymousClass2, obj);
                    }
                });
                UsersManager usersManager = MainActivity.this.getUsersManager();
                String str = this.$userId;
                SignInResponse profile = Settings.INSTANCE.getProfile();
                if (profile == null || (deviceSN = profile.getDeviceSN()) == null) {
                    deviceSN = "";
                }
                Observable<BaseResponse> observableCheckBlockStatus = usersManager.checkBlockStatus(str, deviceSN);
                final AnonymousClass3 anonymousClass3 = new AnonymousClass3(MainActivity.this);
                Consumer<? super BaseResponse> consumer2 = new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$checkNeedBlockDevice$1$$ExternalSyntheticLambda2
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        MainActivity.AnonymousClass1.invoke$lambda$2(anonymousClass3, obj);
                    }
                };
                final AnonymousClass4 anonymousClass4 = new AnonymousClass4(MainActivity.this);
                observableCheckBlockStatus.subscribe(consumer2, new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$checkNeedBlockDevice$1$$ExternalSyntheticLambda3
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        MainActivity.AnonymousClass1.invoke$lambda$3(anonymousClass4, obj);
                    }
                });
                return;
            }
            if (signInResponse.getCode() == 888) {
                DeviceLockingUtilsKt.onDeviceLocked(MainActivity.this);
                return;
            }
            AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(MainActivity.this, signInResponse.getError(), signInResponse.getCode());
            if (alertDialogCreateErrorAlertDialog != null) {
                alertDialogCreateErrorAlertDialog.show();
            }
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

        /* compiled from: MainActivity.kt */
        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        /* renamed from: com.thor.app.gui.activities.main.MainActivity$checkNeedBlockDevice$1$3, reason: invalid class name */
        /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1<BaseResponse, Unit> {
            AnonymousClass3(Object obj) {
                super(1, obj, MainActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/BaseResponse;)V", 0);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                invoke2(baseResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BaseResponse p0) {
                Intrinsics.checkNotNullParameter(p0, "p0");
                ((MainActivity) this.receiver).handleResponse(p0);
            }
        }

        /* compiled from: MainActivity.kt */
        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        /* renamed from: com.thor.app.gui.activities.main.MainActivity$checkNeedBlockDevice$1$4, reason: invalid class name */
        /* synthetic */ class AnonymousClass4 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
            AnonymousClass4(Object obj) {
                super(1, obj, MainActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable p0) {
                Intrinsics.checkNotNullParameter(p0, "p0");
                ((MainActivity) this.receiver).handleError(p0);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }
    }

    /* compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.main.MainActivity$checkNeedBlockDevice$2, reason: invalid class name */
    static final class AnonymousClass2 extends Lambda implements Function1<Throwable, Unit> {
        final /* synthetic */ String $userId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(String str) {
            super(1);
            this.$userId = str;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable th) {
            AlertDialog alertDialogCreateErrorAlertDialog;
            String deviceSN;
            if (Intrinsics.areEqual(th.getMessage(), "HTTP 400")) {
                AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(MainActivity.this, th.getMessage(), 0);
                if (alertDialogCreateErrorAlertDialog2 != null) {
                    alertDialogCreateErrorAlertDialog2.show();
                }
            } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(MainActivity.this, th.getMessage(), 0)) != null) {
                alertDialogCreateErrorAlertDialog.show();
            }
            Observable<BaseResponse> observableUpdateVersion = MainActivity.this.getUsersManager().updateVersion();
            final AnonymousClass1 anonymousClass1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.checkNeedBlockDevice.2.1
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(BaseResponse baseResponse) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                    invoke2(baseResponse);
                    return Unit.INSTANCE;
                }
            };
            Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$checkNeedBlockDevice$2$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainActivity.AnonymousClass2.invoke$lambda$0(anonymousClass1, obj);
                }
            };
            final C00572 c00572 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.main.MainActivity.checkNeedBlockDevice.2.2
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable th2) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th2) {
                    invoke2(th2);
                    return Unit.INSTANCE;
                }
            };
            observableUpdateVersion.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$checkNeedBlockDevice$2$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainActivity.AnonymousClass2.invoke$lambda$1(c00572, obj);
                }
            });
            UsersManager usersManager = MainActivity.this.getUsersManager();
            String str = this.$userId;
            SignInResponse profile = Settings.INSTANCE.getProfile();
            if (profile == null || (deviceSN = profile.getDeviceSN()) == null) {
                deviceSN = "";
            }
            Observable<BaseResponse> observableCheckBlockStatus = usersManager.checkBlockStatus(str, deviceSN);
            final AnonymousClass3 anonymousClass3 = new AnonymousClass3(MainActivity.this);
            Consumer<? super BaseResponse> consumer2 = new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$checkNeedBlockDevice$2$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainActivity.AnonymousClass2.invoke$lambda$2(anonymousClass3, obj);
                }
            };
            final AnonymousClass4 anonymousClass4 = new AnonymousClass4(MainActivity.this);
            observableCheckBlockStatus.subscribe(consumer2, new Consumer() { // from class: com.thor.app.gui.activities.main.MainActivity$checkNeedBlockDevice$2$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainActivity.AnonymousClass2.invoke$lambda$3(anonymousClass4, obj);
                }
            });
            Log.e(Constants.IPC_BUNDLE_KEY_SEND_ERROR, "error " + th.getMessage());
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

        /* compiled from: MainActivity.kt */
        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        /* renamed from: com.thor.app.gui.activities.main.MainActivity$checkNeedBlockDevice$2$3, reason: invalid class name */
        /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1<BaseResponse, Unit> {
            AnonymousClass3(Object obj) {
                super(1, obj, MainActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/BaseResponse;)V", 0);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                invoke2(baseResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BaseResponse p0) {
                Intrinsics.checkNotNullParameter(p0, "p0");
                ((MainActivity) this.receiver).handleResponse(p0);
            }
        }

        /* compiled from: MainActivity.kt */
        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        /* renamed from: com.thor.app.gui.activities.main.MainActivity$checkNeedBlockDevice$2$4, reason: invalid class name */
        /* synthetic */ class AnonymousClass4 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
            AnonymousClass4(Object obj) {
                super(1, obj, MainActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable p0) {
                Intrinsics.checkNotNullParameter(p0, "p0");
                ((MainActivity) this.receiver).handleError(p0);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkNeedBlockDevice$lambda$36(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkNeedBlockDevice$lambda$37(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(BaseResponse response) {
        if (response.getStatus()) {
            changeLockDeviceState(true);
            startActivity(new Intent(this, (Class<?>) LockedDeviceActivity.class));
            finish();
        } else {
            if (response.getError() != null && !Intrinsics.areEqual(response.getError(), "")) {
                AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(this, response.getError(), 0);
                if (alertDialogCreateErrorAlertDialogWithSendLogOption != null) {
                    alertDialogCreateErrorAlertDialogWithSendLogOption.show();
                    return;
                }
                return;
            }
            changeLockDeviceState(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable error) {
        AlertDialog alertDialogCreateErrorAlertDialog;
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400")) {
            AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(this, error.getMessage(), 0);
            if (alertDialogCreateErrorAlertDialog2 != null) {
                alertDialogCreateErrorAlertDialog2.show();
            }
        } else if (Intrinsics.areEqual(error.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, error.getMessage(), 0)) != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
        Log.e("Lock", "Error " + error.getMessage());
    }
}
