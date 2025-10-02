package com.thor.app.gui.activities.barcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityBarcodeCaptureBinding;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.thor.app.gui.activities.login.SignUpCarInfoActivity;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.barcode.BarcodeGraphic;
import com.thor.app.gui.barcode.BarcodeGraphicTracker;
import com.thor.app.gui.barcode.BarcodeTrackerFactory;
import com.thor.app.gui.camera.GraphicOverlay;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.dialogs.ProgressDialogFragment;
import com.thor.app.gui.widget.DrawView;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.security.DeviceLockingUtilsKt;
import com.thor.businessmodule.viewmodel.main.BarcodeCaptureActivityViewModel;
import com.thor.networkmodule.model.SignUpInfo;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.SignInResponse;
import com.thor.networkmodule.network.ApiService;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.io.IOException;
import kotlin.Deprecated;
import timber.log.Timber;

@Deprecated(message = "not used")
/* loaded from: classes3.dex */
public final class BarcodeCaptureActivity extends AppCompatActivity implements BarcodeGraphicTracker.BarcodeUpdateListener, View.OnClickListener {
    public static final String AutoFocus = "AutoFocus";
    private static final int RC_HANDLE_CAMERA_PERM = 2;
    private static final int RC_HANDLE_GMS = 9001;
    public static final String UseFlash = "UseFlash";
    private ActivityBarcodeCaptureBinding binding;
    private CameraSource mCameraSource;
    private boolean mCheckingQRCode = false;
    private String mDeviceSn;
    private DrawView mDrawView;
    private GraphicOverlay<BarcodeGraphic> mGraphicOverlay;
    private ImageView mImageViewBack;
    private ProgressDialogFragment mProgressDialog;
    private UsersManager usersManager;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        ActivityBarcodeCaptureBinding activityBarcodeCaptureBinding = (ActivityBarcodeCaptureBinding) DataBindingUtil.setContentView(this, R.layout.activity_barcode_capture);
        this.binding = activityBarcodeCaptureBinding;
        activityBarcodeCaptureBinding.setModel(new BarcodeCaptureActivityViewModel());
        this.mGraphicOverlay = (GraphicOverlay) findViewById(R.id.graphic_overlay);
        this.mDrawView = (DrawView) findViewById(R.id.drawView);
        this.mImageViewBack = (ImageView) findViewById(R.id.image_view_back);
        this.usersManager = UsersManager.from(this);
        boolean booleanExtra = getIntent().getBooleanExtra(AutoFocus, true);
        boolean booleanExtra2 = getIntent().getBooleanExtra(UseFlash, false);
        if (ActivityCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0) {
            createCameraSource(booleanExtra, booleanExtra2);
            this.mDrawView.makehole();
            this.binding.getModel().getEnableCamera().set(true);
        } else {
            requestCameraPermission();
        }
        this.mProgressDialog = ProgressDialogFragment.newInstance();
        this.mImageViewBack.setOnClickListener(this);
    }

    private void requestCameraPermission() {
        Timber.i("Camera permission is not granted. Requesting permission", new Object[0]);
        String[] strArr = {"android.permission.CAMERA"};
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA")) {
            ActivityCompat.requestPermissions(this, strArr, 2);
        } else {
            ActivityCompat.requestPermissions(this, strArr, 2);
        }
    }

    private void createCameraSource(boolean autoFocus, boolean useFlash) {
        BarcodeDetector barcodeDetectorBuild = new BarcodeDetector.Builder(getApplicationContext()).build();
        barcodeDetectorBuild.setProcessor(new MultiProcessor.Builder(new BarcodeTrackerFactory(this.mGraphicOverlay, this)).build());
        if (!barcodeDetectorBuild.isOperational()) {
            Timber.i("Detector dependencies are not yet available.", new Object[0]);
            if (registerReceiver(null, new IntentFilter("android.intent.action.DEVICE_STORAGE_LOW")) != null) {
                Toast.makeText(this, R.string.low_storage_error, 1).show();
                Timber.i(getString(R.string.low_storage_error), new Object[0]);
            }
        }
        this.mCameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetectorBuild).setRequestedPreviewSize(getResources().getDisplayMetrics().heightPixels, getResources().getDisplayMetrics().widthPixels).setAutoFocusEnabled(true).setFacing(0).build();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() throws SecurityException {
        super.onResume();
        startCameraSource();
        this.mCheckingQRCode = false;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        if (this.binding.cameraPreview != null) {
            this.binding.cameraPreview.stop();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.binding.cameraPreview != null) {
            this.binding.cameraPreview.stop();
            this.binding.cameraPreview.release();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != 2) {
            Timber.i("Got unexpected permission result: " + requestCode, new Object[0]);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } else {
            if (grantResults.length != 0 && grantResults[0] == 0) {
                Timber.i("Camera permission granted - initialize the camera source", new Object[0]);
                createCameraSource(true, false);
                this.mDrawView.makehole();
                this.binding.getModel().getEnableCamera().set(true);
                return;
            }
            Timber.i("Permission not granted: results len = " + grantResults.length + " Result code = " + (grantResults.length > 0 ? Integer.valueOf(grantResults[0]) : "(empty)"), new Object[0]);
            new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.barcode.BarcodeCaptureActivity.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int id) {
                    BarcodeCaptureActivity.this.finish();
                }
            };
        }
    }

    private void startCameraSource() throws SecurityException {
        int iIsGooglePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getApplicationContext());
        if (iIsGooglePlayServicesAvailable != 0) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, iIsGooglePlayServicesAvailable, 9001).show();
        }
        if (this.mCameraSource != null) {
            try {
                this.binding.cameraPreview.start(this.mCameraSource, this.mGraphicOverlay);
            } catch (IOException e) {
                Timber.e(e, "Unable to start camera source", new Object[0]);
                this.mCameraSource.release();
                this.mCameraSource = null;
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v.getId() != R.id.image_view_back) {
            return;
        }
        finish();
    }

    @Override // com.thor.app.gui.barcode.BarcodeGraphicTracker.BarcodeUpdateListener
    public void onBarcodeDetected(Barcode barcode) {
        Timber.i("QR code: %s", barcode.rawValue);
        if (this.mCheckingQRCode) {
            return;
        }
        checkQRcode(barcode.rawValue);
    }

    private void checkQRcode(String rawValue) {
        this.mCheckingQRCode = true;
        if (rawValue.length() <= 5 || !rawValue.toLowerCase().contains("thor")) {
            return;
        }
        this.mDeviceSn = rawValue;
        this.usersManager.signIn(rawValue.substring(4), true).doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.barcode.BarcodeCaptureActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$checkQRcode$0((Disposable) obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.gui.activities.barcode.BarcodeCaptureActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.lambda$checkQRcode$1();
            }
        }).subscribe(new Consumer() { // from class: com.thor.app.gui.activities.barcode.BarcodeCaptureActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                this.f$0.handleResponse((SignInResponse) obj);
            }
        }, new Consumer() { // from class: com.thor.app.gui.activities.barcode.BarcodeCaptureActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                this.f$0.handleError((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkQRcode$0(Disposable disposable) throws Exception {
        if (this.mProgressDialog.isAdded()) {
            return;
        }
        this.mProgressDialog.show(getSupportFragmentManager(), ProgressDialogFragment.TAG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkQRcode$1() throws Exception {
        if (this.mProgressDialog.isResumed()) {
            this.mProgressDialog.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleResponse(SignInResponse response) {
        Timber.i("handleResponse: %s", response);
        if (response.isSuccessful()) {
            if (response.getUserId() == 0 || TextUtils.isEmpty(response.getToken())) {
                return;
            }
            Settings.saveUserId(response.getUserId());
            Settings.saveAccessToken(response.getToken());
            Settings.saveProfile(response);
            this.usersManager.addNotificationToken().subscribe(new Consumer() { // from class: com.thor.app.gui.activities.barcode.BarcodeCaptureActivity$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.lambda$handleResponse$2((BaseResponse) obj);
                }
            }, new Consumer() { // from class: com.thor.app.gui.activities.barcode.BarcodeCaptureActivity$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    Timber.e((Throwable) obj);
                }
            });
            Intent intent = new Intent(this, (Class<?>) SplashActivity.class);
            intent.setFlags(268468224);
            startActivity(intent);
            return;
        }
        if (response.getCode() == 888) {
            DeviceLockingUtilsKt.onDeviceLocked(this);
            return;
        }
        DialogManager.INSTANCE.createErrorAlertDialog(this, response.getError(), 0).show();
        SignUpInfo signUpInfo = new SignUpInfo();
        signUpInfo.setDeviceSN(this.mDeviceSn.substring(4));
        Intent intent2 = new Intent(this, (Class<?>) SignUpCarInfoActivity.class);
        intent2.putExtra(SignUpInfo.BUNDLE_NAME, signUpInfo);
        startActivity(intent2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleResponse$2(BaseResponse baseResponse) throws Exception {
        if (!baseResponse.isSuccessful()) {
            DialogManager.INSTANCE.createErrorAlertDialog(this, baseResponse.getError(), 0).show();
        }
        Timber.i("Response: %s", baseResponse);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleError(Throwable error) {
        String message = error.getMessage();
        ApiService.Companion companion = ApiService.INSTANCE;
        if (message == "HTTP 400") {
            DialogManager.INSTANCE.createErrorAlertDialog(this, error.getMessage(), 0).show();
        } else {
            String message2 = error.getMessage();
            ApiService.Companion companion2 = ApiService.INSTANCE;
            if (message2 == "HTTP 500") {
                DialogManager.INSTANCE.createErrorAlertDialog(this, error.getMessage(), 0).show();
            }
        }
        Timber.e(error);
        this.usersManager.makeSnackBarNetworkError(this.binding.mainLayout).show();
        this.mCheckingQRCode = false;
    }
}
