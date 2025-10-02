package com.thor.app.gui.activities.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityBluetoothDevicesBinding;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.BluetoothDevicesWearableDataLayer;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.BooleanWearableDataLayer;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.Wearable;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.Constants;
import com.thor.app.bus.events.BluetoothDeviceAuthorizeEvent;
import com.thor.app.bus.events.BluetoothScanResultEvent;
import com.thor.app.bus.events.BluetoothStateChangedEvent;
import com.thor.app.databinding.model.RunningAppOnPhoneStatus;
import com.thor.app.gui.activities.barcode.BarcodeCaptureActivity;
import com.thor.app.gui.activities.demo.DemoActivity;
import com.thor.app.gui.activities.login.SignUpAddDeviceActivity;
import com.thor.app.gui.activities.login.SignUpCarInfoActivity;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.adapters.bluetooth.BluetoothDevicesRvAdapter;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.dialogs.ProgressDialogFragment;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.receiver.BluetoothBroadcastReceiver;
import com.thor.app.settings.CarInfoPreference;
import com.thor.app.settings.Settings;
import com.thor.app.utils.data.DataLayerHelper;
import com.thor.app.utils.extensions.ContextKt;
import com.thor.networkmodule.model.SignUpInfo;
import com.thor.networkmodule.model.bluetooth.BluetoothDeviceItem;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.CarInfoAuthorizeResponse;
import com.thor.networkmodule.model.responses.SignInResponse;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.Iterator;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.greenrobot.eventbus.Subscribe;
import timber.log.Timber;

/* compiled from: BluetoothDevicesActivity.kt */
@Metadata(d1 = {"\u0000¬\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 V2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001VB\u0005¢\u0006\u0002\u0010\u0004J\b\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020+2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010/\u001a\u00020+2\u0006\u00100\u001a\u000201H\u0002J\u0010\u00102\u001a\u00020+2\u0006\u0010-\u001a\u000203H\u0003J\b\u00104\u001a\u00020+H\u0002J\u0010\u00105\u001a\u00020+2\u0006\u00106\u001a\u00020\u0010H\u0003J\b\u00107\u001a\u00020+H\u0016J\u0012\u00108\u001a\u00020+2\b\u00109\u001a\u0004\u0018\u00010:H\u0016J\u0012\u0010;\u001a\u00020+2\b\u0010<\u001a\u0004\u0018\u00010=H\u0014J\u0010\u0010>\u001a\u00020+2\u0006\u0010?\u001a\u00020@H\u0016J\b\u0010A\u001a\u00020+H\u0014J\u0010\u0010B\u001a\u00020+2\u0006\u0010C\u001a\u00020DH\u0007J\u0010\u0010B\u001a\u00020+2\u0006\u0010C\u001a\u00020EH\u0007J\u0010\u0010B\u001a\u00020+2\u0006\u0010C\u001a\u00020FH\u0007J\b\u0010G\u001a\u00020+H\u0014J\b\u0010H\u001a\u00020+H\u0014J\u0010\u0010I\u001a\u00020+2\u0006\u0010J\u001a\u00020KH\u0016J\b\u0010L\u001a\u00020+H\u0002J\b\u0010M\u001a\u00020+H\u0016J\b\u0010N\u001a\u00020+H\u0016J\b\u0010O\u001a\u00020+H\u0002J\b\u0010P\u001a\u00020+H\u0016J\u0010\u0010Q\u001a\u00020+2\u0006\u0010R\u001a\u00020SH\u0016J\b\u0010T\u001a\u00020+H\u0002J\b\u0010U\u001a\u00020+H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u000e\u001a\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0016\u001a\u00020\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u000e\u001a\u0004\b\u0018\u0010\u0019R\u001b\u0010\u001b\u001a\u00020\u001c8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u000e\u001a\u0004\b\u001d\u0010\u001eR\u001d\u0010 \u001a\u0004\u0018\u00010!8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u000e\u001a\u0004\b\"\u0010#R\u001b\u0010%\u001a\u00020&8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b)\u0010\u000e\u001a\u0004\b'\u0010(¨\u0006W"}, d2 = {"Lcom/thor/app/gui/activities/bluetooth/BluetoothDevicesActivity;", "Lcom/thor/app/gui/activities/bluetooth/BaseScanningBluetoothDevicesActivity;", "Landroid/view/View$OnClickListener;", "Lcom/google/android/gms/wearable/DataClient$OnDataChangedListener;", "()V", "TIME_INTERVAL_SCANNING", "", "binding", "Lcom/carsystems/thor/app/databinding/ActivityBluetoothDevicesBinding;", "mAdapter", "Lcom/thor/app/gui/adapters/bluetooth/BluetoothDevicesRvAdapter;", "getMAdapter", "()Lcom/thor/app/gui/adapters/bluetooth/BluetoothDevicesRvAdapter;", "mAdapter$delegate", "Lkotlin/Lazy;", "mBluetoothDeviceItem", "Lcom/thor/networkmodule/model/bluetooth/BluetoothDeviceItem;", "mDataLayerHelper", "Lcom/thor/app/utils/data/DataLayerHelper;", "getMDataLayerHelper", "()Lcom/thor/app/utils/data/DataLayerHelper;", "mDataLayerHelper$delegate", "mDataLayerManager", "Lcom/thor/app/managers/DataLayerManager;", "getMDataLayerManager", "()Lcom/thor/app/managers/DataLayerManager;", "mDataLayerManager$delegate", "mProgressDialog", "Lcom/thor/app/gui/dialog/dialogs/ProgressDialogFragment;", "getMProgressDialog", "()Lcom/thor/app/gui/dialog/dialogs/ProgressDialogFragment;", "mProgressDialog$delegate", "mReceiver", "Lcom/thor/app/receiver/BluetoothBroadcastReceiver;", "getMReceiver", "()Lcom/thor/app/receiver/BluetoothBroadcastReceiver;", "mReceiver$delegate", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "doOnCheckAutoMode", "", "handleCarInfoFromAuthorize", "response", "Lcom/thor/networkmodule/model/responses/CarInfoAuthorizeResponse;", "handleError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleResponse", "Lcom/thor/networkmodule/model/responses/SignInResponse;", "initAdapter", "onAuthorize", "device", "onBackPressed", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDataChanged", "dataEvents", "Lcom/google/android/gms/wearable/DataEventBuffer;", "onDestroy", "onMessageEvent", "event", "Lcom/thor/app/bus/events/BluetoothDeviceAuthorizeEvent;", "Lcom/thor/app/bus/events/BluetoothScanResultEvent;", "Lcom/thor/app/bus/events/BluetoothStateChangedEvent;", "onPause", "onResume", "onScanFailed", "errorCode", "", "onShareLogs", "onStartScanning", "onStopScanning", "openDemoScreen", "permissionsGranted", "permissionsNotGranted", "permission", "", "startBarcodeReader", "tryGetCarInfoFromAuthorize", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BluetoothDevicesActivity extends BaseScanningBluetoothDevicesActivity implements View.OnClickListener, DataClient.OnDataChangedListener {
    public static final int RC_BARCODE_CAPTURE = 9001;
    private ActivityBluetoothDevicesBinding binding;
    private BluetoothDeviceItem mBluetoothDeviceItem;
    private final long TIME_INTERVAL_SCANNING = 10000;

    /* renamed from: mProgressDialog$delegate, reason: from kotlin metadata */
    private final Lazy mProgressDialog = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<ProgressDialogFragment>() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$mProgressDialog$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ProgressDialogFragment invoke() {
            return ProgressDialogFragment.newInstance();
        }
    });

    /* renamed from: mAdapter$delegate, reason: from kotlin metadata */
    private final Lazy mAdapter = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BluetoothDevicesRvAdapter>() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$mAdapter$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BluetoothDevicesRvAdapter invoke() {
            return new BluetoothDevicesRvAdapter();
        }
    });

    /* renamed from: mReceiver$delegate, reason: from kotlin metadata */
    private final Lazy mReceiver = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BluetoothBroadcastReceiver>() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$mReceiver$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BluetoothBroadcastReceiver invoke() {
            return new BluetoothBroadcastReceiver();
        }
    });

    /* renamed from: mDataLayerHelper$delegate, reason: from kotlin metadata */
    private final Lazy mDataLayerHelper = LazyKt.lazy(new Function0<DataLayerHelper>() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$mDataLayerHelper$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final DataLayerHelper invoke() {
            return DataLayerHelper.newInstance(this.this$0);
        }
    });

    /* renamed from: mDataLayerManager$delegate, reason: from kotlin metadata */
    private final Lazy mDataLayerManager = LazyKt.lazy(new Function0<DataLayerManager>() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$mDataLayerManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final DataLayerManager invoke() {
            return DataLayerManager.INSTANCE.from(this.this$0);
        }
    });

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    private final void onShareLogs() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void permissionsGranted$lambda$6(View view) {
    }

    private final ProgressDialogFragment getMProgressDialog() {
        Object value = this.mProgressDialog.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-mProgressDialog>(...)");
        return (ProgressDialogFragment) value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BluetoothDevicesRvAdapter getMAdapter() {
        return (BluetoothDevicesRvAdapter) this.mAdapter.getValue();
    }

    private final BluetoothBroadcastReceiver getMReceiver() {
        return (BluetoothBroadcastReceiver) this.mReceiver.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DataLayerHelper getMDataLayerHelper() {
        Object value = this.mDataLayerHelper.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-mDataLayerHelper>(...)");
        return (DataLayerHelper) value;
    }

    private final DataLayerManager getMDataLayerManager() {
        return (DataLayerManager) this.mDataLayerManager.getValue();
    }

    private final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, R.layout.activity_bluetooth_devices);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.l…tivity_bluetooth_devices)");
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding = (ActivityBluetoothDevicesBinding) contentView;
        this.binding = activityBluetoothDevicesBinding;
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding2 = null;
        if (activityBluetoothDevicesBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding = null;
        }
        activityBluetoothDevicesBinding.swipeContainer.setColorSchemeColors(ContextKt.fetchAttrValue(this, R.attr.colorAccent));
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding3 = this.binding;
        if (activityBluetoothDevicesBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding3 = null;
        }
        activityBluetoothDevicesBinding3.swipeContainer.setProgressBackgroundColorSchemeResource(R.color.colorRefreshLayoutBackground);
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding4 = this.binding;
        if (activityBluetoothDevicesBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding4 = null;
        }
        activityBluetoothDevicesBinding4.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda3
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                BluetoothDevicesActivity.onCreate$lambda$0(this.f$0);
            }
        });
        initAdapter();
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding5 = this.binding;
        if (activityBluetoothDevicesBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding5 = null;
        }
        activityBluetoothDevicesBinding5.recyclerView.setLayoutManager(new LinearLayoutManager() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity.onCreate.2
            {
                super(BluetoothDevicesActivity.this, 1, false);
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding6 = null;
                if (BluetoothDevicesActivity.this.getMAdapter().getItemCount() > 0) {
                    ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding7 = BluetoothDevicesActivity.this.binding;
                    if (activityBluetoothDevicesBinding7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    } else {
                        activityBluetoothDevicesBinding6 = activityBluetoothDevicesBinding7;
                    }
                    activityBluetoothDevicesBinding6.textViewNoDevices.setVisibility(8);
                    return;
                }
                if (BluetoothDevicesActivity.this.isPermissionsGranted()) {
                    ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding8 = BluetoothDevicesActivity.this.binding;
                    if (activityBluetoothDevicesBinding8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    } else {
                        activityBluetoothDevicesBinding6 = activityBluetoothDevicesBinding8;
                    }
                    activityBluetoothDevicesBinding6.textViewNoDevices.setVisibility(0);
                }
            }
        });
        registerReceiver(getMReceiver(), new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding6 = this.binding;
        if (activityBluetoothDevicesBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding6 = null;
        }
        BluetoothDevicesActivity bluetoothDevicesActivity = this;
        activityBluetoothDevicesBinding6.textViewQrCode.setOnClickListener(bluetoothDevicesActivity);
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding7 = this.binding;
        if (activityBluetoothDevicesBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding7 = null;
        }
        activityBluetoothDevicesBinding7.textViewEnterCode.setOnClickListener(bluetoothDevicesActivity);
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding8 = this.binding;
        if (activityBluetoothDevicesBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityBluetoothDevicesBinding2 = activityBluetoothDevicesBinding8;
        }
        activityBluetoothDevicesBinding2.buttonDemo.setOnClickListener(bluetoothDevicesActivity);
        Single<Boolean> singleIsConnectedToWear = getMDataLayerHelper().isConnectedToWear();
        final Function1<Boolean, Unit> function1 = new Function1<Boolean, Unit>() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity.onCreate.3
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
                BluetoothDevicesActivity.this.getMDataLayerHelper().onStartBluetoothSearchWearableActivity();
            }
        };
        singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BluetoothDevicesActivity.onCreate$lambda$1(function1, obj);
            }
        });
        getMDataLayerManager().sendIsRunningAppOnPhone(RunningAppOnPhoneStatus.MAIN);
        initInternetConnectionListener();
        doOnCheckAutoMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(BluetoothDevicesActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.doScanDevices();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private static final void onCreate$lambda$2(BluetoothDevicesActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onShareLogs();
    }

    @Override // com.thor.basemodule.gui.activity.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        getBleManager().stopScan();
        super.onBackPressed();
    }

    @Override // com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        Wearable.getDataClient((Activity) this).removeListener(this);
        getBleManager().stopScan();
    }

    @Override // com.thor.app.gui.activities.EmergencySituationBaseActivity, com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Wearable.getDataClient((Activity) this).addListener(this);
        if (getPermissionDialogShows()) {
            return;
        }
        doScanDevices();
    }

    @Override // com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        unregisterReceiver(getMReceiver());
        super.onDestroy();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_qr_code) {
            startBarcodeReader();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_enter_code) {
            startActivity(new Intent(this, (Class<?>) SignUpAddDeviceActivity.class));
        } else if (numValueOf != null && numValueOf.intValue() == R.id.button_demo) {
            openDemoScreen();
        }
    }

    private final void openDemoScreen() {
        startActivity(new Intent(this, (Class<?>) DemoActivity.class));
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.google.android.gms.wearable.DataClient.OnDataChangedListener, com.google.android.gms.wearable.DataApi.DataListener
    public void onDataChanged(DataEventBuffer dataEvents) {
        Intrinsics.checkNotNullParameter(dataEvents, "dataEvents");
        Iterator<DataEvent> it = dataEvents.iterator();
        while (it.hasNext()) {
            DataEvent next = it.next();
            int type = next.getType();
            if (type == 1) {
                String path = next.getDataItem().getUri().getPath();
                if (path != null) {
                    switch (path.hashCode()) {
                        case -2070582438:
                            if (!path.equals(BluetoothDevicesWearableDataLayer.BLUETOOTH_SELECTED_DEVICE_PATH)) {
                                break;
                            } else {
                                Timber.INSTANCE.i("Received data from BLUETOOTH_SELECTED_DEVICE_PATH", new Object[0]);
                                BluetoothDeviceItem bluetoothDeviceItemConvertFromDataItem = BluetoothDevicesWearableDataLayer.INSTANCE.convertFromDataItem(next.getDataItem());
                                Intrinsics.checkNotNullExpressionValue(bluetoothDeviceItemConvertFromDataItem, "BluetoothDevicesWearable…mDataItem(event.dataItem)");
                                onAuthorize(bluetoothDeviceItemConvertFromDataItem);
                                break;
                            }
                        case -402180377:
                            if (!path.equals(BooleanWearableDataLayer.BLUETOOTH_REFRESHING_DEVICES_WEAR_PATH)) {
                                break;
                            } else {
                                Timber.INSTANCE.i("Received data from BLUETOOTH_REFRESHING_DEVICES_WEAR_PATH", new Object[0]);
                                Boolean boolConvertFromDataItem = BooleanWearableDataLayer.INSTANCE.convertFromDataItem(next.getDataItem());
                                Intrinsics.checkNotNullExpressionValue(boolConvertFromDataItem, "BooleanWearableDataLayer…mDataItem(event.dataItem)");
                                if (!boolConvertFromDataItem.booleanValue()) {
                                    break;
                                } else {
                                    doScanDevices();
                                    break;
                                }
                            }
                        case 517212461:
                            if (!path.equals(BooleanWearableDataLayer.CURRENT_DATA_PATH)) {
                                break;
                            } else {
                                Timber.INSTANCE.i("Received data from CURRENT_DATA_PATH", new Object[0]);
                                getMDataLayerManager().sendBluetoothDevices(getMAdapter().getItems());
                                break;
                            }
                        case 1045446980:
                            if (!path.equals(BooleanWearableDataLayer.CURRENT_ACTIVITY_PATH)) {
                                break;
                            } else {
                                Timber.INSTANCE.i("Received data from CURRENT_ACTIVITY_PATH", new Object[0]);
                                getMDataLayerHelper().onStartBluetoothSearchWearableActivity();
                                break;
                            }
                    }
                }
                Timber.INSTANCE.d("Unrecognized path: " + path, new Object[0]);
            } else if (type != 2) {
                Timber.INSTANCE.d("Unknown data event type. Type = " + next.getType(), new Object[0]);
            } else {
                Timber.INSTANCE.i("DataItem Deleted " + next.getDataItem(), new Object[0]);
            }
        }
    }

    @Override // com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity
    public void permissionsNotGranted(String permission) {
        Intrinsics.checkNotNullParameter(permission, "permission");
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding = this.binding;
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding2 = null;
        if (activityBluetoothDevicesBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding = null;
        }
        activityBluetoothDevicesBinding.textViewNoDevices.setVisibility(8);
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding3 = this.binding;
        if (activityBluetoothDevicesBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding3 = null;
        }
        activityBluetoothDevicesBinding3.textViewNeedPermissions.setVisibility(0);
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding4 = this.binding;
        if (activityBluetoothDevicesBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding4 = null;
        }
        activityBluetoothDevicesBinding4.textViewTitleNeedPermissions.setVisibility(0);
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding5 = this.binding;
        if (activityBluetoothDevicesBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding5 = null;
        }
        activityBluetoothDevicesBinding5.buttonRequestPermissions.setVisibility(0);
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding6 = this.binding;
        if (activityBluetoothDevicesBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding6 = null;
        }
        activityBluetoothDevicesBinding6.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda13
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                BluetoothDevicesActivity.permissionsNotGranted$lambda$3(this.f$0);
            }
        });
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding7 = this.binding;
        if (activityBluetoothDevicesBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityBluetoothDevicesBinding2 = activityBluetoothDevicesBinding7;
        }
        activityBluetoothDevicesBinding2.buttonRequestPermissions.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BluetoothDevicesActivity.permissionsNotGranted$lambda$4(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void permissionsNotGranted$lambda$3(BluetoothDevicesActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding = this$0.binding;
        if (activityBluetoothDevicesBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding = null;
        }
        activityBluetoothDevicesBinding.swipeContainer.setRefreshing(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void permissionsNotGranted$lambda$4(BluetoothDevicesActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.requestPermissions();
    }

    @Override // com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity
    public void permissionsGranted() {
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding = this.binding;
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding2 = null;
        if (activityBluetoothDevicesBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding = null;
        }
        activityBluetoothDevicesBinding.textViewNeedPermissions.setVisibility(8);
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding3 = this.binding;
        if (activityBluetoothDevicesBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding3 = null;
        }
        activityBluetoothDevicesBinding3.textViewTitleNeedPermissions.setVisibility(8);
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding4 = this.binding;
        if (activityBluetoothDevicesBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding4 = null;
        }
        activityBluetoothDevicesBinding4.buttonRequestPermissions.setVisibility(8);
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding5 = this.binding;
        if (activityBluetoothDevicesBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding5 = null;
        }
        activityBluetoothDevicesBinding5.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda10
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                BluetoothDevicesActivity.permissionsGranted$lambda$5(this.f$0);
            }
        });
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding6 = this.binding;
        if (activityBluetoothDevicesBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityBluetoothDevicesBinding2 = activityBluetoothDevicesBinding6;
        }
        activityBluetoothDevicesBinding2.buttonRequestPermissions.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BluetoothDevicesActivity.permissionsGranted$lambda$6(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void permissionsGranted$lambda$5(BluetoothDevicesActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.doScanDevices();
    }

    private final void startBarcodeReader() {
        Intent intent = new Intent(this, (Class<?>) BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    @Subscribe
    public final void onMessageEvent(BluetoothStateChangedEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (event.getState() == 10) {
            promptBluetoothEnabling();
        }
    }

    @Subscribe
    public final void onMessageEvent(BluetoothDeviceAuthorizeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding = this.binding;
        if (activityBluetoothDevicesBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding = null;
        }
        activityBluetoothDevicesBinding.progressBar.setVisibility(0);
        onAuthorize(event.getDevice());
    }

    @Subscribe
    public final void onMessageEvent(BluetoothScanResultEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        BluetoothDevice device = event.getScanResult().getDevice();
        if (device != null) {
            String deviceName = device.getName();
            if (TextUtils.isEmpty(deviceName)) {
                return;
            }
            Intrinsics.checkNotNullExpressionValue(deviceName, "deviceName");
            Locale locale = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
            String lowerCase = deviceName.toLowerCase(locale);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding = null;
            if (StringsKt.startsWith$default(lowerCase, "thor", false, 2, (Object) null)) {
                ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding2 = this.binding;
                if (activityBluetoothDevicesBinding2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    activityBluetoothDevicesBinding = activityBluetoothDevicesBinding2;
                }
                activityBluetoothDevicesBinding.swipeContainer.setRefreshing(false);
                String address = device.getAddress();
                Intrinsics.checkNotNullExpressionValue(address, "it.address");
                BluetoothDeviceItem bluetoothDeviceItem = new BluetoothDeviceItem(deviceName, address);
                getMAdapter().addBluetoothDevice(bluetoothDeviceItem);
                getMDataLayerManager().sendBluetoothDevice(bluetoothDeviceItem);
            }
        }
    }

    private final void onAuthorize(BluetoothDeviceItem device) {
        String strSubstring;
        String name;
        getBleManager().stopScan();
        this.mBluetoothDeviceItem = device;
        if (device == null || (name = device.getName()) == null) {
            strSubstring = null;
        } else {
            strSubstring = name.substring(4);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        }
        Observable<SignInResponse> observableDoOnTerminate = getUsersManager().fetchCarInfo(strSubstring).doOnTerminate(new Action() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Action
            public final void run() {
                BluetoothDevicesActivity.onAuthorize$lambda$8(this.f$0);
            }
        });
        final C01442 c01442 = new C01442(this);
        Consumer<? super SignInResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BluetoothDevicesActivity.onAuthorize$lambda$9(c01442, obj);
            }
        };
        final AnonymousClass3 anonymousClass3 = new AnonymousClass3(this);
        observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BluetoothDevicesActivity.onAuthorize$lambda$10(anonymousClass3, obj);
            }
        });
    }

    /* compiled from: BluetoothDevicesActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$onAuthorize$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01442 extends FunctionReferenceImpl implements Function1<SignInResponse, Unit> {
        C01442(Object obj) {
            super(1, obj, BluetoothDevicesActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/SignInResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(SignInResponse signInResponse) {
            invoke2(signInResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(SignInResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((BluetoothDevicesActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: BluetoothDevicesActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$onAuthorize$3, reason: invalid class name */
    /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass3(Object obj) {
            super(1, obj, BluetoothDevicesActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((BluetoothDevicesActivity) this.receiver).handleError(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAuthorize$lambda$8(BluetoothDevicesActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.getMProgressDialog().isResumed()) {
            this$0.getMProgressDialog().dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAuthorize$lambda$10(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAuthorize$lambda$9(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(SignInResponse response) {
        String name;
        if (response.getStatus()) {
            CarInfoPreference.INSTANCE.setCarModelID(response.getCarModelId());
            CarInfoPreference.INSTANCE.setCarMarkId(response.getCarMarkId());
            CarInfoPreference.INSTANCE.setCarSerieId(response.getCarSerieId());
            CarInfoPreference.INSTANCE.setCarGenerationId(response.getCarGenerationId());
            Settings.saveUserId(response.getUserId());
            String token = response.getToken();
            Intrinsics.checkNotNull(token);
            Settings.saveAccessToken(token);
            Settings.saveProfile(response);
            Settings settings = Settings.INSTANCE;
            BluetoothDeviceItem bluetoothDeviceItem = this.mBluetoothDeviceItem;
            settings.saveDeviceMacAddress(bluetoothDeviceItem != null ? bluetoothDeviceItem.getMacAddress() : null);
            getMDataLayerHelper().onStartMainWearableActivity();
            getMDataLayerManager().m558sendurrentAppSettings(response, Settings.INSTANCE.getDeviceMacAddress());
            Observable<BaseResponse> observableAddNotificationToken = getUsersManager().addNotificationToken();
            if (observableAddNotificationToken != null) {
                final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity.handleResponse.1
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
                        AlertDialog alertDialogCreateErrorAlertDialog$default;
                        if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, BluetoothDevicesActivity.this, baseResponse.getError(), null, 4, null)) != null) {
                            alertDialogCreateErrorAlertDialog$default.show();
                        }
                        Timber.INSTANCE.i("Response: %s", baseResponse);
                    }
                };
                Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        BluetoothDevicesActivity.handleResponse$lambda$11(function1, obj);
                    }
                };
                final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity.handleResponse.2
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
                            AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, BluetoothDevicesActivity.this, th.getMessage(), null, 4, null);
                            if (alertDialogCreateErrorAlertDialog$default2 != null) {
                                alertDialogCreateErrorAlertDialog$default2.show();
                            }
                        } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, BluetoothDevicesActivity.this, th.getMessage(), null, 4, null)) != null) {
                            alertDialogCreateErrorAlertDialog$default.show();
                        }
                        Timber.INSTANCE.e(th);
                    }
                };
                observableAddNotificationToken.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda2
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        BluetoothDevicesActivity.handleResponse$lambda$12(function12, obj);
                    }
                });
            }
            Intent intent = new Intent(this, (Class<?>) SplashActivity.class);
            intent.setFlags(268468224);
            startActivity(intent);
            return;
        }
        Settings.saveAccessToken(Settings.getUserGoogleToken());
        SignInResponse signInResponse = new SignInResponse(0, 0, null, 0, null, 0, null, 0, null, null, null, 2047, null);
        BluetoothDeviceItem bluetoothDeviceItem2 = this.mBluetoothDeviceItem;
        if (bluetoothDeviceItem2 != null && (name = bluetoothDeviceItem2.getName()) != null) {
            strSubstring = name.substring(4);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        }
        signInResponse.setDeviceSN(strSubstring);
        Settings.saveProfile(signInResponse);
        Settings.saveUserId(Integer.parseInt(Settings.getUserGoogleUserId()));
        tryGetCarInfoFromAuthorize();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleResponse$lambda$11(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleResponse$lambda$12(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void tryGetCarInfoFromAuthorize() {
        String strSubstring;
        String name;
        BluetoothDeviceItem bluetoothDeviceItem = this.mBluetoothDeviceItem;
        if (bluetoothDeviceItem == null || (name = bluetoothDeviceItem.getName()) == null) {
            strSubstring = null;
        } else {
            strSubstring = name.substring(4);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        }
        UsersManager usersManager = getUsersManager();
        if (strSubstring == null) {
            strSubstring = "";
        }
        Observable<CarInfoAuthorizeResponse> observableDoOnTerminate = usersManager.getCarInfoFromAuthorize(strSubstring).doOnTerminate(new Action() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Action
            public final void run() {
                BluetoothDevicesActivity.tryGetCarInfoFromAuthorize$lambda$13(this.f$0);
            }
        });
        final Function1<CarInfoAuthorizeResponse, Unit> function1 = new Function1<CarInfoAuthorizeResponse, Unit>() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity.tryGetCarInfoFromAuthorize.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(CarInfoAuthorizeResponse carInfoAuthorizeResponse) {
                invoke2(carInfoAuthorizeResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(CarInfoAuthorizeResponse it) {
                BluetoothDevicesActivity bluetoothDevicesActivity = BluetoothDevicesActivity.this;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                bluetoothDevicesActivity.handleCarInfoFromAuthorize(it);
            }
        };
        Consumer<? super CarInfoAuthorizeResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BluetoothDevicesActivity.tryGetCarInfoFromAuthorize$lambda$14(function1, obj);
            }
        };
        final C01483 c01483 = new C01483(this);
        observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BluetoothDevicesActivity.tryGetCarInfoFromAuthorize$lambda$15(c01483, obj);
            }
        });
    }

    /* compiled from: BluetoothDevicesActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$tryGetCarInfoFromAuthorize$3, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C01483 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C01483(Object obj) {
            super(1, obj, BluetoothDevicesActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((BluetoothDevicesActivity) this.receiver).handleError(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tryGetCarInfoFromAuthorize$lambda$13(BluetoothDevicesActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.getMProgressDialog().isResumed()) {
            this$0.getMProgressDialog().dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tryGetCarInfoFromAuthorize$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tryGetCarInfoFromAuthorize$lambda$15(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleCarInfoFromAuthorize(CarInfoAuthorizeResponse response) {
        String strSubstring;
        String strSubstring2;
        String name;
        String name2;
        if (response.getStatus()) {
            CarInfoPreference.INSTANCE.setCarModelID(response.getCarModelId());
            CarInfoPreference.INSTANCE.setCarMarkId(response.getCarMarkId());
            CarInfoPreference.INSTANCE.setCarSerieId(response.getCarSerieId());
            CarInfoPreference.INSTANCE.setCarGenerationId(response.getCarGenerationId());
            Settings.saveProfile(new SignInResponse(response.getIdUser(), response.getCarMarkId(), response.getCarMarkName(), response.getCarModelId(), response.getCarModelName(), response.getCarGenerationId(), response.getCarGenerationName(), response.getCarSerieId(), response.getCarSerieName(), response.getDeviceSn(), response.getToken()));
            Settings.saveUserId(response.getIdUser());
            Settings.saveAccessToken(response.getToken());
            Settings settings = Settings.INSTANCE;
            BluetoothDeviceItem bluetoothDeviceItem = this.mBluetoothDeviceItem;
            settings.saveDeviceMacAddress(bluetoothDeviceItem != null ? bluetoothDeviceItem.getMacAddress() : null);
            Intent intent = new Intent(this, (Class<?>) SplashActivity.class);
            intent.setFlags(268468224);
            startActivity(intent);
            return;
        }
        getMDataLayerHelper().onStartSignUpWearableActivity();
        SignUpInfo signUpInfo = new SignUpInfo(null, 0, null, 0, null, 0, null, 0, null, null, null, 2047, null);
        SignInResponse signInResponse = new SignInResponse(0, 0, null, 0, null, 0, null, 0, null, null, null, 2047, null);
        BluetoothDeviceItem bluetoothDeviceItem2 = this.mBluetoothDeviceItem;
        if (bluetoothDeviceItem2 == null || (name2 = bluetoothDeviceItem2.getName()) == null) {
            strSubstring = null;
        } else {
            strSubstring = name2.substring(4);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        }
        signInResponse.setDeviceSN(strSubstring);
        Settings.saveProfile(signInResponse);
        BluetoothDeviceItem bluetoothDeviceItem3 = this.mBluetoothDeviceItem;
        if (bluetoothDeviceItem3 == null || (name = bluetoothDeviceItem3.getName()) == null) {
            strSubstring2 = null;
        } else {
            strSubstring2 = name.substring(4);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        }
        signUpInfo.setDeviceSN(strSubstring2);
        Settings settings2 = Settings.INSTANCE;
        BluetoothDeviceItem bluetoothDeviceItem4 = this.mBluetoothDeviceItem;
        settings2.saveDeviceMacAddress(bluetoothDeviceItem4 != null ? bluetoothDeviceItem4.getMacAddress() : null);
        Intent intent2 = new Intent(this, (Class<?>) SignUpCarInfoActivity.class);
        intent2.putExtra(SignUpInfo.BUNDLE_NAME, signUpInfo);
        startActivity(intent2);
    }

    @Override // com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity
    public void onScanFailed(final int errorCode) {
        runOnUiThread(new Runnable() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothDevicesActivity.onScanFailed$lambda$16(errorCode, this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onScanFailed$lambda$16(int i, BluetoothDevicesActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Timber.INSTANCE.e("onScanFailed: %s", Integer.valueOf(i));
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding = this$0.binding;
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding2 = null;
        if (activityBluetoothDevicesBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding = null;
        }
        Snackbar.make(activityBluetoothDevicesBinding.mainLayout, "Bluetooth scan device failed, error " + i, -1).show();
        this$0.doStopScanning();
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding3 = this$0.binding;
        if (activityBluetoothDevicesBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityBluetoothDevicesBinding2 = activityBluetoothDevicesBinding3;
        }
        activityBluetoothDevicesBinding2.swipeContainer.setRefreshing(false);
        this$0.getMDataLayerManager().sendBluetoothDevicesRefreshingState(false);
    }

    @Override // com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity
    public void onStartScanning() {
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding = this.binding;
        if (activityBluetoothDevicesBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding = null;
        }
        activityBluetoothDevicesBinding.swipeContainer.setRefreshing(true);
        getMDataLayerManager().sendBluetoothDevicesRefreshingState(true);
        getMAdapter().clear();
        new Handler().postDelayed(new Runnable() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothDevicesActivity.onStartScanning$lambda$17(this.f$0);
            }
        }, this.TIME_INTERVAL_SCANNING);
        new Handler().postDelayed(new Runnable() { // from class: com.thor.app.gui.activities.bluetooth.BluetoothDevicesActivity$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothDevicesActivity.onStartScanning$lambda$18(this.f$0);
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStartScanning$lambda$17(BluetoothDevicesActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.doStopScanning();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStartScanning$lambda$18(BluetoothDevicesActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.doStopScanning();
    }

    @Override // com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity
    public void onStopScanning() {
        getMDataLayerManager().sendBluetoothDevicesRefreshingState(false);
    }

    private final void initAdapter() {
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding = this.binding;
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding2 = null;
        if (activityBluetoothDevicesBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding = null;
        }
        activityBluetoothDevicesBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding3 = this.binding;
        if (activityBluetoothDevicesBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityBluetoothDevicesBinding2 = activityBluetoothDevicesBinding3;
        }
        activityBluetoothDevicesBinding2.recyclerView.setAdapter(getMAdapter());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable error) {
        AlertDialog alertDialogCreateErrorAlertDialog$default;
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400")) {
            AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, error.getMessage(), null, 4, null);
            if (alertDialogCreateErrorAlertDialog$default2 != null) {
                alertDialogCreateErrorAlertDialog$default2.show();
            }
        } else if (Intrinsics.areEqual(error.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, error.getMessage(), null, 4, null)) != null) {
            alertDialogCreateErrorAlertDialog$default.show();
        }
        Timber.INSTANCE.e(error);
        UsersManager usersManager = getUsersManager();
        ActivityBluetoothDevicesBinding activityBluetoothDevicesBinding = this.binding;
        if (activityBluetoothDevicesBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityBluetoothDevicesBinding = null;
        }
        Snackbar snackbarMakeSnackBarNetworkError = usersManager.makeSnackBarNetworkError(activityBluetoothDevicesBinding.mainLayout);
        if (snackbarMakeSnackBarNetworkError != null) {
            snackbarMakeSnackBarNetworkError.show();
        }
    }

    private final void doOnCheckAutoMode() {
        if (com.thor.basemodule.extensions.ContextKt.isCarUIMode(this)) {
            openDemoScreen();
        }
    }
}
