package com.thor.app.gui.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.BuildConfig;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityLockedDeviceBinding;
import com.thor.app.bus.events.BluetoothDeviceConnectionChangedEvent;
import com.thor.app.bus.events.bluetooth.StartScanBluetoothDevicesEvent;
import com.thor.app.bus.events.bluetooth.StopScanBluetoothDevicesEvent;
import com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.receiver.BluetoothBroadcastReceiver;
import com.thor.app.room.ThorDatabase;
import com.thor.app.settings.Settings;
import com.thor.app.utils.data.DataLayerHelper;
import com.thor.networkmodule.model.firmware.FirmwareProfile;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.SignInResponse;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.Subscribe;
import timber.log.Timber;

/* compiled from: LockedDeviceActivity.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0011H\u0002J\b\u0010\u0013\u001a\u00020\u0011H\u0016J\u0012\u0010\u0014\u001a\u00020\u00112\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J\b\u0010\u0017\u001a\u00020\u0011H\u0014J\u0010\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aH\u0007J\u0010\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001bH\u0007J\u0010\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001cH\u0007J\b\u0010\u001d\u001a\u00020\u0011H\u0002J\b\u0010\u001e\u001a\u00020\u0011H\u0002J\b\u0010\u001f\u001a\u00020\u0011H\u0002J\b\u0010 \u001a\u00020\u0011H\u0014J\u0010\u0010!\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020\u0011H\u0016J\b\u0010%\u001a\u00020\u0011H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000e¨\u0006&"}, d2 = {"Lcom/thor/app/gui/activities/LockedDeviceActivity;", "Lcom/thor/app/gui/activities/bluetooth/BaseScanningBluetoothDevicesActivity;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityLockedDeviceBinding;", "mReceiver", "Lcom/thor/app/receiver/BluetoothBroadcastReceiver;", "getMReceiver", "()Lcom/thor/app/receiver/BluetoothBroadcastReceiver;", "mReceiver$delegate", "Lkotlin/Lazy;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "init", "", "initListeners", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onMessageEvent", "event", "Lcom/thor/app/bus/events/BluetoothDeviceConnectionChangedEvent;", "Lcom/thor/app/bus/events/bluetooth/StartScanBluetoothDevicesEvent;", "Lcom/thor/app/bus/events/bluetooth/StopScanBluetoothDevicesEvent;", "onNewDeviceAction", "onOpenDownloadTelegramLink", "onOpenThorTechSupport", "onResume", "onScanFailed", "errorCode", "", "onStartScanning", "onStopScanning", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class LockedDeviceActivity extends BaseScanningBluetoothDevicesActivity {
    private ActivityLockedDeviceBinding binding;

    /* renamed from: mReceiver$delegate, reason: from kotlin metadata */
    private final Lazy mReceiver = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BluetoothBroadcastReceiver>() { // from class: com.thor.app.gui.activities.LockedDeviceActivity$mReceiver$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BluetoothBroadcastReceiver invoke() {
            return new BluetoothBroadcastReceiver();
        }
    });

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.LockedDeviceActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    private final BluetoothBroadcastReceiver getMReceiver() {
        return (BluetoothBroadcastReceiver) this.mReceiver.getValue();
    }

    private final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    @Override // com.thor.basemodule.gui.activity.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        Log.i("backpressed", "back pressed in locked activity");
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        String deviceSN;
        super.onCreate(savedInstanceState);
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, R.layout.activity_locked_device);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.l…t.activity_locked_device)");
        this.binding = (ActivityLockedDeviceBinding) contentView;
        init();
        ActivityLockedDeviceBinding activityLockedDeviceBinding = this.binding;
        ActivityLockedDeviceBinding activityLockedDeviceBinding2 = null;
        if (activityLockedDeviceBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityLockedDeviceBinding = null;
        }
        AppCompatTextView appCompatTextView = activityLockedDeviceBinding.deviceSn;
        Object[] objArr = new Object[1];
        SignInResponse profile = Settings.INSTANCE.getProfile();
        if (profile == null || (deviceSN = profile.getDeviceSN()) == null) {
            deviceSN = "";
        }
        objArr[0] = deviceSN;
        appCompatTextView.setText(getString(R.string.lock_info_sn, objArr));
        ActivityLockedDeviceBinding activityLockedDeviceBinding3 = this.binding;
        if (activityLockedDeviceBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityLockedDeviceBinding3 = null;
        }
        AppCompatTextView appCompatTextView2 = activityLockedDeviceBinding3.userId;
        Object[] objArr2 = new Object[1];
        SignInResponse profile2 = Settings.INSTANCE.getProfile();
        objArr2[0] = String.valueOf(profile2 != null ? Integer.valueOf(profile2.getUserId()) : null);
        appCompatTextView2.setText(getString(R.string.lock_info_user_id, objArr2));
        ActivityLockedDeviceBinding activityLockedDeviceBinding4 = this.binding;
        if (activityLockedDeviceBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityLockedDeviceBinding4 = null;
        }
        AppCompatTextView appCompatTextView3 = activityLockedDeviceBinding4.fwVersion;
        Object[] objArr3 = new Object[1];
        FirmwareProfile firmwareProfile = Settings.INSTANCE.getFirmwareProfile();
        objArr3[0] = String.valueOf(firmwareProfile != null ? Integer.valueOf(firmwareProfile.getVersionFW()) : null);
        appCompatTextView3.setText(getString(R.string.lock_info_fw, objArr3));
        ActivityLockedDeviceBinding activityLockedDeviceBinding5 = this.binding;
        if (activityLockedDeviceBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityLockedDeviceBinding2 = activityLockedDeviceBinding5;
        }
        activityLockedDeviceBinding2.appVersion.setText(getString(R.string.lock_info_app, new Object[]{BuildConfig.VERSION_NAME}));
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (getPermissionDialogShows()) {
            return;
        }
        doScanDevices();
    }

    @Override // com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(getMReceiver());
    }

    private final void init() {
        Settings.INSTANCE.saveDeviceLockState(true);
        registerReceiver(getMReceiver(), new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        initListeners();
        if (getBleManager().isBleEnabledAndDeviceConnected()) {
            changeLockDeviceState(true);
        } else {
            doScanDevices();
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

    private final void initListeners() {
        ActivityLockedDeviceBinding activityLockedDeviceBinding = this.binding;
        ActivityLockedDeviceBinding activityLockedDeviceBinding2 = null;
        if (activityLockedDeviceBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityLockedDeviceBinding = null;
        }
        activityLockedDeviceBinding.buttonUpdate.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.LockedDeviceActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LockedDeviceActivity.initListeners$lambda$0(this.f$0, view);
            }
        });
        ActivityLockedDeviceBinding activityLockedDeviceBinding3 = this.binding;
        if (activityLockedDeviceBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityLockedDeviceBinding2 = activityLockedDeviceBinding3;
        }
        activityLockedDeviceBinding2.buttonNewDevice.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.LockedDeviceActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LockedDeviceActivity.initListeners$lambda$1(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$0(LockedDeviceActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onOpenThorTechSupport();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$1(LockedDeviceActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onNewDeviceAction();
    }

    private final void onOpenThorTechSupport() {
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

    private final void onNewDeviceAction() {
        Settings.saveIsCheckedEmrgencySituations(false);
        Observable<BaseResponse> observableRemoveNotification = getUsersManager().removeNotification();
        if (observableRemoveNotification != null) {
            final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.LockedDeviceActivity.onNewDeviceAction.1
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
                    if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(LockedDeviceActivity.this, baseResponse.getError(), Integer.valueOf(baseResponse.getCode()))) != null) {
                        alertDialogCreateErrorAlertDialogWithSendLogOption.show();
                    }
                    Timber.INSTANCE.i("Response: %s", baseResponse);
                }
            };
            Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.LockedDeviceActivity$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    LockedDeviceActivity.onNewDeviceAction$lambda$2(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.LockedDeviceActivity.onNewDeviceAction.2
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
                        AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(LockedDeviceActivity.this, th.getMessage(), 0);
                        if (alertDialogCreateErrorAlertDialog2 != null) {
                            alertDialogCreateErrorAlertDialog2.show();
                        }
                    } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(LockedDeviceActivity.this, th.getMessage(), 0)) != null) {
                        alertDialogCreateErrorAlertDialog.show();
                    }
                    Timber.INSTANCE.e(th);
                }
            };
            observableRemoveNotification.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.LockedDeviceActivity$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    LockedDeviceActivity.onNewDeviceAction$lambda$3(function12, obj);
                }
            });
        }
        getBleManager().disconnect(true);
        Settings.removeAllProperties();
        LockedDeviceActivity lockedDeviceActivity = this;
        Settings.clearCookies(lockedDeviceActivity);
        DataLayerManager.INSTANCE.from(lockedDeviceActivity).sendIsAccessSession(false);
        ThorDatabase.INSTANCE.from(lockedDeviceActivity).installedSoundPackageDao().deleteAllElements();
        ThorDatabase.INSTANCE.from(lockedDeviceActivity).shopSoundPackageDao().deleteAllElements();
        Intent intent = new Intent(lockedDeviceActivity, (Class<?>) SplashActivity.class);
        intent.setFlags(268468224);
        startActivity(intent);
        DataLayerHelper.newInstance(lockedDeviceActivity).onStartBluetoothSearchWearableActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onNewDeviceAction$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onNewDeviceAction$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Subscribe
    public final void onMessageEvent(BluetoothDeviceConnectionChangedEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (getBleManager().isBleEnabledAndDeviceConnected()) {
            changeLockDeviceState(true);
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
}
