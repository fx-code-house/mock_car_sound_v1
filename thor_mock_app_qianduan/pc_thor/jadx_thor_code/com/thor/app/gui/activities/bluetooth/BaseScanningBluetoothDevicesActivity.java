package com.thor.app.gui.activities.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Lifecycle;
import com.carsystems.thor.app.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.thor.app.gui.activities.EmergencySituationBaseActivity;
import com.thor.app.utils.extensions.ContextKt;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: BaseScanningBluetoothDevicesActivity.kt */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u000e\b&\u0018\u0000 -2\u00020\u0001:\u0001-B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\u0004H\u0002J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u0006\u0010\u000f\u001a\u00020\rJ\u0006\u0010\u0010\u001a\u00020\u0004J\u0006\u0010\u0011\u001a\u00020\u0004J\"\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\u0010\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u0004H\u0004J\b\u0010\u001a\u001a\u00020\rH\u0014J-\u0010\u001b\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00142\u000e\u0010\u001c\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001e0\u001d2\u0006\u0010\u001f\u001a\u00020 H\u0016¢\u0006\u0002\u0010!J\u0010\u0010\"\u001a\u00020\r2\u0006\u0010#\u001a\u00020\u0014H&J\b\u0010$\u001a\u00020\rH&J\b\u0010%\u001a\u00020\rH&J\b\u0010&\u001a\u00020\rH\u0016J\b\u0010'\u001a\u00020\rH\u0016J\u0010\u0010(\u001a\u00020\r2\u0006\u0010)\u001a\u00020\u001eH\u0016J\b\u0010*\u001a\u00020\rH\u0004J\b\u0010+\u001a\u00020\u0004H\u0002J\u0006\u0010,\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lcom/thor/app/gui/activities/bluetooth/BaseScanningBluetoothDevicesActivity;", "Lcom/thor/app/gui/activities/EmergencySituationBaseActivity;", "()V", "dialogShows", "", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "permissionDialogShows", "showsEnableBluetooth", "checkLocationEnabled", "doScanDevices", "", "doStartScanning", "doStopScanning", "isDialogShows", "isPermissionsGranted", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onBluetoothScanStateChanged", "isStarted", "onPause", "onRequestPermissionsResult", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onScanFailed", "errorCode", "onStartScanning", "onStopScanning", "permissionsGranted", "permissionsIsDenied", "permissionsNotGranted", "permission", "promptBluetoothEnabling", "requestPermission", "requestPermissions", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class BaseScanningBluetoothDevicesActivity extends EmergencySituationBaseActivity {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String[] PERMISSIONS_BLUETOOTH = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT"};
    private static final String[] PERMISSIONS_LOCATION = {"android.permission.ACCESS_FINE_LOCATION"};
    public static final int REQUEST_ENABLE_BLUETOOTH = 10011;
    public static final int REQUEST_ENABLE_LOCATION = 10013;
    public static final int REQUEST_LOCATION = 10012;
    private boolean dialogShows;
    private final Handler handler = new Handler();
    private boolean permissionDialogShows;
    private boolean showsEnableBluetooth;

    public abstract void onScanFailed(int errorCode);

    public abstract void onStartScanning();

    public abstract void onStopScanning();

    public void permissionsGranted() {
    }

    protected final Handler getHandler() {
        return this.handler;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        doStopScanning();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.INSTANCE.i("onActivityResult: %s, %s, %s", Integer.valueOf(requestCode), Integer.valueOf(resultCode), data);
        if (requestCode == 10011) {
            this.showsEnableBluetooth = false;
            doScanDevices();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        if (requestCode == 10012) {
            if (!(grantResults.length == 0)) {
                ArrayList arrayList = new ArrayList();
                int length = grantResults.length;
                for (int i = 0; i < length; i++) {
                    int i2 = grantResults[i];
                    if (i2 == -1) {
                        arrayList.add(Integer.valueOf(i2));
                    }
                }
                if (!arrayList.isEmpty()) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, (String) ArraysKt.first(permissions))) {
                        return;
                    }
                    permissionsIsDenied();
                    return;
                }
                permissionsGranted();
            }
        }
    }

    public final void doScanDevices() {
        if (requestPermission()) {
            doStartScanning();
        }
    }

    public final void doStartScanning() {
        BluetoothAdapter mBluetoothAdapter = getBleManager().getMBluetoothAdapter();
        if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isEnabled()) {
                onStartScanning();
                Timber.INSTANCE.i("Start scanning devices", new Object[0]);
                getBleManager().startScan();
                this.handler.postDelayed(new Runnable() { // from class: com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseScanningBluetoothDevicesActivity.doStartScanning$lambda$2$lambda$1(this.f$0);
                    }
                }, 3000L);
                return;
            }
            promptBluetoothEnabling();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doStartScanning$lambda$2$lambda$1(BaseScanningBluetoothDevicesActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        boolean z = false;
        Timber.INSTANCE.i("Scanning is on: " + this$0.getBleManager().getIsStartedScan(), new Object[0]);
        if (this$0.getBleManager().getIsStartedScan() && this$0.checkLocationEnabled()) {
            z = true;
        }
        this$0.onBluetoothScanStateChanged(z);
    }

    public final void doStopScanning() {
        onStopScanning();
    }

    private final boolean requestPermission() {
        boolean zCheckLocationPermission;
        if (Build.VERSION.SDK_INT >= 31) {
            zCheckLocationPermission = ContextKt.checkBluetoothPermissions(this);
        } else {
            zCheckLocationPermission = ContextKt.checkLocationPermission(this);
        }
        if (!zCheckLocationPermission) {
            permissionsNotGranted("android.permission.ACCESS_FINE_LOCATION");
        } else {
            permissionsGranted();
        }
        return zCheckLocationPermission;
    }

    public final boolean isPermissionsGranted() {
        return ActivityCompat.checkSelfPermission(this, (String) ArraysKt.first(PERMISSIONS_LOCATION)) == 0;
    }

    public void permissionsNotGranted(String permission) {
        Intrinsics.checkNotNullParameter(permission, "permission");
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            requestPermissions();
        } else {
            requestPermissions();
        }
    }

    /* renamed from: isDialogShows, reason: from getter */
    public final boolean getPermissionDialogShows() {
        return this.permissionDialogShows;
    }

    public void permissionsIsDenied() {
        this.permissionDialogShows = true;
        new AlertDialog.Builder(this, 2131886084).setMessage(getString(R.string.text_explanation_for_permissions)).setCancelable(false).setPositiveButton(getString(R.string.action_opne_settings), new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BaseScanningBluetoothDevicesActivity.permissionsIsDenied$lambda$3(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                BaseScanningBluetoothDevicesActivity.permissionsIsDenied$lambda$4(this.f$0, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void permissionsIsDenied$lambda$3(BaseScanningBluetoothDevicesActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        Uri uriFromParts = Uri.fromParts("package", this$0.getPackageName(), null);
        Intrinsics.checkNotNullExpressionValue(uriFromParts, "fromParts(\"package\", packageName, null)");
        intent.setData(uriFromParts);
        this$0.permissionDialogShows = false;
        this$0.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void permissionsIsDenied$lambda$4(BaseScanningBluetoothDevicesActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.permissionDialogShows = false;
        dialogInterface.dismiss();
    }

    public final void requestPermissions() {
        if (Build.VERSION.SDK_INT >= 31) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_BLUETOOTH, REQUEST_LOCATION);
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_LOCATION, REQUEST_LOCATION);
        }
    }

    private final boolean checkLocationEnabled() {
        boolean zIsProviderEnabled;
        boolean zIsProviderEnabled2;
        Object systemService = getSystemService(FirebaseAnalytics.Param.LOCATION);
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.location.LocationManager");
        LocationManager locationManager = (LocationManager) systemService;
        try {
            zIsProviderEnabled = locationManager.isProviderEnabled("gps");
        } catch (Exception e) {
            e = e;
            zIsProviderEnabled = false;
        }
        try {
            zIsProviderEnabled2 = locationManager.isProviderEnabled("network");
        } catch (Exception e2) {
            e = e2;
            Timber.INSTANCE.e(e);
            zIsProviderEnabled2 = false;
            if (zIsProviderEnabled) {
            }
            return true;
        }
        if (!zIsProviderEnabled || zIsProviderEnabled2 || this.dialogShows) {
            return true;
        }
        if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
            this.dialogShows = true;
            new AlertDialog.Builder(this, 2131886084).setMessage(R.string.text_enable_location).setCancelable(false).setPositiveButton(R.string.button_enabled_location, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    BaseScanningBluetoothDevicesActivity.checkLocationEnabled$lambda$5(this.f$0, dialogInterface, i);
                }
            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    BaseScanningBluetoothDevicesActivity.checkLocationEnabled$lambda$6(this.f$0, dialogInterface, i);
                }
            }).show();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkLocationEnabled$lambda$5(BaseScanningBluetoothDevicesActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dialogShows = false;
        this$0.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), REQUEST_ENABLE_LOCATION);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkLocationEnabled$lambda$6(BaseScanningBluetoothDevicesActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dialogShows = false;
    }

    protected final void promptBluetoothEnabling() {
        if (this.showsEnableBluetooth) {
            return;
        }
        ContextKt.checkBluetoothPermissionAndInvoke(this, new Function0<Unit>() { // from class: com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity.promptBluetoothEnabling.1
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
                BaseScanningBluetoothDevicesActivity.this.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), BaseScanningBluetoothDevicesActivity.REQUEST_ENABLE_BLUETOOTH);
                BaseScanningBluetoothDevicesActivity.this.showsEnableBluetooth = true;
            }
        });
    }

    protected final void onBluetoothScanStateChanged(boolean isStarted) {
        if (isStarted || getBleManager().getMStateConnected()) {
            return;
        }
        this.handler.postDelayed(new Runnable() { // from class: com.thor.app.gui.activities.bluetooth.BaseScanningBluetoothDevicesActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BaseScanningBluetoothDevicesActivity.onBluetoothScanStateChanged$lambda$7(this.f$0);
            }
        }, 2500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBluetoothScanStateChanged$lambda$7(BaseScanningBluetoothDevicesActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.getBleManager().getMStateConnected()) {
            return;
        }
        this$0.getBleManager().connect();
    }

    /* compiled from: BaseScanningBluetoothDevicesActivity.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0019\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\n\u0010\u0007R\u000e\u0010\u000b\u001a\u00020\fX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/thor/app/gui/activities/bluetooth/BaseScanningBluetoothDevicesActivity$Companion;", "", "()V", "PERMISSIONS_BLUETOOTH", "", "", "getPERMISSIONS_BLUETOOTH", "()[Ljava/lang/String;", "[Ljava/lang/String;", "PERMISSIONS_LOCATION", "getPERMISSIONS_LOCATION", "REQUEST_ENABLE_BLUETOOTH", "", "REQUEST_ENABLE_LOCATION", "REQUEST_LOCATION", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String[] getPERMISSIONS_BLUETOOTH() {
            return BaseScanningBluetoothDevicesActivity.PERMISSIONS_BLUETOOTH;
        }

        public final String[] getPERMISSIONS_LOCATION() {
            return BaseScanningBluetoothDevicesActivity.PERMISSIONS_LOCATION;
        }
    }
}
