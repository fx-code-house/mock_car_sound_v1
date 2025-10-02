package com.thor.app.gui.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentKt;
import com.carsystems.thor.app.R;
import com.google.android.gms.common.util.Hex;
import com.thor.app.bus.events.FetchConfigEvent;
import com.thor.app.bus.events.FormatFlashExecuteEvent;
import com.thor.app.bus.events.HandleFormatFlashDialogCloseEvent;
import com.thor.app.bus.events.LoaderChangedEvent;
import com.thor.app.bus.events.bluetooth.UpdateCanConfigurationsErrorEvent;
import com.thor.app.bus.events.bluetooth.UpdateCanConfigurationsSuccessfulEvent;
import com.thor.app.bus.events.bluetooth.firmware.StartUpdateFirmwareEvent;
import com.thor.app.bus.events.device.FormatFlashEvent;
import com.thor.app.bus.events.device.ReloadParametersEvent;
import com.thor.app.gui.activities.EmergencySituationBaseActivity;
import com.thor.app.gui.activities.main.carinfo.ChangeCarActivity;
import com.thor.app.gui.activities.shop.ShopActivity;
import com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity;
import com.thor.app.gui.dialog.CanFileManager;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment;
import com.thor.app.gui.dialog.FormatProgressDialogFragment;
import com.thor.app.gui.dialog.UpdateFirmwareDialogFragment;
import com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.service.UploadFilesService;
import com.thor.app.service.models.UploadFileGroupModel;
import com.thor.app.settings.Settings;
import com.thor.businessmodule.bluetooth.response.other.WriteLockDeviceBleResponse;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bus.events.BluetoothCommandErrorEvent;
import com.thor.businessmodule.bus.events.ResponseErrorCodeEvent;
import com.thor.networkmodule.model.responses.SignInResponse;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.ByteArrayOutputStream;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: EmergencySituationBaseActivity.kt */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000fH\u0004J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u001dH\u0002J\u0010\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020!H\u0004J\u0010\u0010$\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020&H\u0004J\u0006\u0010'\u001a\u00020\u001dJ\u0010\u0010(\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\u000fH\u0004J\u0010\u0010*\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\u000fH\u0004J\u0010\u0010+\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\u000fH\u0004J\u0010\u0010,\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\u000fH\u0004J\b\u0010-\u001a\u00020\u001dH\u0002J\u0006\u0010.\u001a\u00020\u001dJ\b\u0010/\u001a\u00020\u0015H\u0002J\b\u00100\u001a\u00020\u001dH\u0002J\u0018\u00101\u001a\u00020\u001d2\u0006\u00102\u001a\u00020\u000f2\u0006\u00103\u001a\u00020\u000fH\u0002J\b\u00104\u001a\u00020\u001aH\u0014J\b\u00105\u001a\u00020\u001aH\u0014J\b\u00106\u001a\u00020\u001aH\u0004J\u0010\u00107\u001a\u00020\u001a2\u0006\u00108\u001a\u000209H\u0007J\u0012\u0010:\u001a\u00020\u001a2\b\u0010;\u001a\u0004\u0018\u00010<H\u0014J\u0010\u0010=\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020\u000fH\u0002J\u0010\u0010>\u001a\u00020\u001a2\u0006\u00108\u001a\u00020?H\u0007J\u0010\u0010>\u001a\u00020\u001a2\u0006\u00108\u001a\u00020@H\u0007J\u0010\u0010>\u001a\u00020\u001a2\u0006\u00108\u001a\u00020AH\u0007J\u0010\u0010>\u001a\u00020\u001a2\u0006\u00108\u001a\u00020BH\u0017J\u0010\u0010>\u001a\u00020\u001a2\u0006\u00108\u001a\u00020CH\u0007J\u0010\u0010>\u001a\u00020\u001a2\u0006\u00108\u001a\u00020DH\u0007J\u0010\u0010>\u001a\u00020\u001a2\u0006\u00108\u001a\u00020EH\u0007J\b\u0010F\u001a\u00020\u001aH\u0014J\b\u0010G\u001a\u00020\u001aH\u0014J\b\u0010H\u001a\u00020\u001aH\u0014J\b\u0010I\u001a\u00020\u001aH\u0004R\u001b\u0010\u0003\u001a\u00020\u00048DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001b\u0010\u0014\u001a\u00020\u00158FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\b\u001a\u0004\b\u0016\u0010\u0017¨\u0006J"}, d2 = {"Lcom/thor/app/gui/activities/EmergencySituationBaseActivity;", "Lcom/thor/app/gui/activities/shop/main/SubscriptionCheckActivity;", "()V", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "canFileManager", "Lcom/thor/app/gui/dialog/CanFileManager;", "getCanFileManager", "()Lcom/thor/app/gui/dialog/CanFileManager;", "canFileManager$delegate", "formatFlash", "", "getFormatFlash", "()Z", "setFormatFlash", "(Z)V", "mLoadingDialog", "Landroid/app/Dialog;", "getMLoadingDialog", "()Landroid/app/Dialog;", "mLoadingDialog$delegate", "changeLockDeviceState", "", "isLocked", "createCanConfigurationSuccessfulAlertDialog", "Landroidx/appcompat/app/AlertDialog;", "createEmergencyUpdateFirmwareAlertDialog", "createErrorLogAlertDialog", "errorBytes", "", "createErrorMessageAlertDialog", "message", "createErrorUploadSoundPackageAlertDialog", "soundPackage", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "createFinishFormatFlashDialog", "createFormatFlashAlertDialog", "showDelayDialog", "createFormatFlashAlertDialogForce", "createFormatFlashConfirmationAlertDialog", "createFormatFlashEmergencyAlertDialog", "createNoCanFileOnServerAlertDialog", "createNoCarChosenAlertDialog", "createProgressLoadingFormatFlash", "createReloadParametersAlertDialog", "createUpdateFirmwareAlertDialog", "isNeedReload", "isNeedUploadCanFile", "disableUpdateSoftware", "enableUpdateSoftware", "initInternetConnectionListener", "omMessageEvent", "event", "Lcom/thor/businessmodule/bus/events/ResponseErrorCodeEvent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onFinishFormatFlash", "onMessageEvent", "Lcom/thor/app/bus/events/FormatFlashExecuteEvent;", "Lcom/thor/app/bus/events/bluetooth/UpdateCanConfigurationsErrorEvent;", "Lcom/thor/app/bus/events/bluetooth/UpdateCanConfigurationsSuccessfulEvent;", "Lcom/thor/app/bus/events/bluetooth/firmware/StartUpdateFirmwareEvent;", "Lcom/thor/app/bus/events/device/FormatFlashEvent;", "Lcom/thor/app/bus/events/device/ReloadParametersEvent;", "Lcom/thor/businessmodule/bus/events/BluetoothCommandErrorEvent;", "onResume", "onStart", "onStop", "openUpdateFirmwareDialog", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public class EmergencySituationBaseActivity extends SubscriptionCheckActivity {
    private boolean formatFlash;

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager>() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$bleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0);
        }
    });

    /* renamed from: mLoadingDialog$delegate, reason: from kotlin metadata */
    private final Lazy mLoadingDialog = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Dialog>() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$mLoadingDialog$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Dialog invoke() {
            return this.this$0.createProgressLoadingFormatFlash();
        }
    });

    /* renamed from: canFileManager$delegate, reason: from kotlin metadata */
    private final Lazy canFileManager = LazyKt.lazy(new Function0<CanFileManager>() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$canFileManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final CanFileManager invoke() {
            return CanFileManager.INSTANCE.from(this.this$0);
        }
    });

    /* compiled from: EmergencySituationBaseActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[UploadFileGroupModel.TypeGroupFile.values().length];
            try {
                iArr[UploadFileGroupModel.TypeGroupFile.SOUND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[UploadFileGroupModel.TypeGroupFile.SGU.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[UploadFileGroupModel.TypeGroupFile.FIRMWARE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    protected void disableUpdateSoftware() {
    }

    protected void enableUpdateSoftware() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    public final Dialog getMLoadingDialog() {
        return (Dialog) this.mLoadingDialog.getValue();
    }

    public final boolean getFormatFlash() {
        return this.formatFlash;
    }

    public final void setFormatFlash(boolean z) {
        this.formatFlash = z;
    }

    protected final CanFileManager getCanFileManager() {
        return (CanFileManager) this.canFileManager.getValue();
    }

    @Override // com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws Exception {
        super.onCreate(savedInstanceState);
        if (UploadFilesService.INSTANCE.getServiceStatus()) {
            int i = WhenMappings.$EnumSwitchMapping$0[UploadFilesService.INSTANCE.getUploadingFileType().ordinal()];
            if (i == 1) {
                DownloadSoundPackageDialogFragment downloadSoundPackageDialogFragmentNewInstance = DownloadSoundPackageDialogFragment.INSTANCE.newInstance(new ShopSoundPackage(0, UploadFilesService.INSTANCE.getUploadingName(), 0, null, null, 0.0f, null, null, false, false, 1021, null));
                if (downloadSoundPackageDialogFragmentNewInstance.isAdded()) {
                    return;
                }
                downloadSoundPackageDialogFragmentNewInstance.show(getSupportFragmentManager(), "DownloadSoundPackageDialogFragment");
                return;
            }
            if (i != 2) {
                if (i != 3) {
                    return;
                }
                UpdateFirmwareDialogFragment updateFirmwareDialogFragmentNewInstance = UpdateFirmwareDialogFragment.INSTANCE.newInstance();
                if (updateFirmwareDialogFragmentNewInstance.isAdded()) {
                    return;
                }
                updateFirmwareDialogFragmentNewInstance.show(getSupportFragmentManager(), "UpdateFirmwareDialogFragment");
                return;
            }
            UploadSguSoundSetDialogFragment.Companion companion = UploadSguSoundSetDialogFragment.INSTANCE;
            SguSoundSet loadSguModel = UploadFilesService.INSTANCE.getLoadSguModel();
            if (loadSguModel != null) {
                UploadSguSoundSetDialogFragment uploadSguSoundSetDialogFragmentNewInstance = companion.newInstance(loadSguModel);
                if (uploadSguSoundSetDialogFragmentNewInstance.isAdded()) {
                    return;
                }
                uploadSguSoundSetDialogFragmentNewInstance.show(getSupportFragmentManager(), "UploadSguSoundSetDialogFragment");
                return;
            }
            throw new Exception("bad sgu");
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() throws SecurityException {
        Timber.INSTANCE.i("onStart", new Object[0]);
        super.onStart();
        if (EventBus.getDefault().isRegistered(this)) {
            return;
        }
        EventBus.getDefault().register(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        Timber.INSTANCE.i("onStop", new Object[0]);
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override // com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    private final AlertDialog createReloadParametersAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886084);
        builder.setMessage(R.string.text_need_reload_parameters).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EmergencySituationBaseActivity.createReloadParametersAlertDialog$lambda$0(this.f$0, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createReloadParametersAlertDialog$lambda$0(EmergencySituationBaseActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) ShopActivity.class));
    }

    private final AlertDialog createUpdateFirmwareAlertDialog(final boolean isNeedReload, final boolean isNeedUploadCanFile) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886084);
        builder.setMessage(R.string.message_update_firmware).setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda14
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EmergencySituationBaseActivity.createUpdateFirmwareAlertDialog$lambda$1(isNeedUploadCanFile, this, isNeedReload, dialogInterface, i);
            }
        }).setPositiveButton(R.string.text_update, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EmergencySituationBaseActivity.createUpdateFirmwareAlertDialog$lambda$2(this.f$0, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createUpdateFirmwareAlertDialog$lambda$1(boolean z, EmergencySituationBaseActivity this$0, boolean z2, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (z) {
            this$0.getCanFileManager().startUpdate();
        }
        if (z2) {
            this$0.createReloadParametersAlertDialog().show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createUpdateFirmwareAlertDialog$lambda$2(EmergencySituationBaseActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.openUpdateFirmwareDialog();
    }

    private final AlertDialog createEmergencyUpdateFirmwareAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886084);
        builder.setMessage(R.string.message_update_firmware).setPositiveButton(R.string.text_update, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EmergencySituationBaseActivity.createEmergencyUpdateFirmwareAlertDialog$lambda$3(this.f$0, dialogInterface, i);
            }
        }).setCancelable(false);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createEmergencyUpdateFirmwareAlertDialog$lambda$3(EmergencySituationBaseActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.openUpdateFirmwareDialog();
    }

    protected final AlertDialog createFormatFlashAlertDialog(final boolean showDelayDialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886083);
        builder.setTitle(R.string.text_format_flash).setMessage(R.string.text_format_flash_tip).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda9
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EmergencySituationBaseActivity.createFormatFlashAlertDialog$lambda$4(this.f$0, showDelayDialog, dialogInterface, i);
            }
        }).setNegativeButton(R.string.dialog_button_cancel, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createFormatFlashAlertDialog$lambda$4(EmergencySituationBaseActivity this$0, boolean z, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.createFormatFlashConfirmationAlertDialog(z).show();
    }

    protected final AlertDialog createFormatFlashAlertDialogForce(final boolean showDelayDialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886083);
        builder.setTitle(R.string.text_format_flash).setMessage(R.string.text_format_flash_tip).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EmergencySituationBaseActivity.createFormatFlashAlertDialogForce$lambda$5(this.f$0, showDelayDialog, dialogInterface, i);
            }
        }).setNegativeButton(R.string.dialog_button_cancel, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createFormatFlashAlertDialogForce$lambda$5(EmergencySituationBaseActivity this$0, boolean z, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.createFormatFlashConfirmationAlertDialog(z).show();
    }

    protected final AlertDialog createFormatFlashConfirmationAlertDialog(final boolean showDelayDialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886083);
        builder.setTitle(R.string.text_format_flash).setMessage(R.string.text_format_flash_tip_confirm).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EmergencySituationBaseActivity.createFormatFlashConfirmationAlertDialog$lambda$6(this.f$0, showDelayDialog, dialogInterface, i);
            }
        }).setNegativeButton(R.string.dialog_button_cancel, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createFormatFlashConfirmationAlertDialog$lambda$6(EmergencySituationBaseActivity this$0, boolean z, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Settings.saveIsCheckedEmrgencySituations(false);
        this$0.getMLoadingDialog().show();
        this$0.getBleManager().executeFormatFlashCommand(z);
    }

    protected final AlertDialog createFormatFlashEmergencyAlertDialog(final boolean showDelayDialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886083);
        builder.setTitle(R.string.text_format_flash).setMessage(R.string.message_format_flash_warning).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EmergencySituationBaseActivity.createFormatFlashEmergencyAlertDialog$lambda$7(this.f$0, showDelayDialog, dialogInterface, i);
            }
        }).setCancelable(false);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createFormatFlashEmergencyAlertDialog$lambda$7(EmergencySituationBaseActivity this$0, boolean z, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getMLoadingDialog().show();
        this$0.getBleManager().executeFormatFlashCommand(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Dialog createProgressLoadingFormatFlash() {
        Dialog dialog = new Dialog(this, 2131886084);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        Window window2 = dialog.getWindow();
        ProgressBar progressBar = window2 != null ? (ProgressBar) window2.findViewById(R.id.progressBar) : null;
        int carFuelType = Settings.INSTANCE.getCarFuelType();
        if (carFuelType != 1) {
            if (carFuelType == 2 && progressBar != null) {
                progressBar.setIndeterminateDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_progressbar, null));
            }
        } else if (progressBar != null) {
            progressBar.setIndeterminateDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_progressbar_blue, null));
        }
        return dialog;
    }

    public final AlertDialog createFinishFormatFlashDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886083);
        builder.setMessage(R.string.message_flash_format_success).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EmergencySituationBaseActivity.createFinishFormatFlashDialog$lambda$8(dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createFinishFormatFlashDialog$lambda$8(DialogInterface dialogInterface, int i) {
        EventBus.getDefault().post(new HandleFormatFlashDialogCloseEvent(true));
    }

    protected final AlertDialog createErrorMessageAlertDialog(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886084);
        builder.setMessage(message).setPositiveButton(android.R.string.yes, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    private final AlertDialog createNoCanFileOnServerAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886084);
        SignInResponse profile = Settings.INSTANCE.getProfile();
        String str = getString(R.string.text_can_file_not_found) + StringUtils.LF;
        Object[] objArr = new Object[4];
        objArr[0] = profile != null ? profile.getCarMarkName() : null;
        objArr[1] = profile != null ? profile.getCarModelName() : null;
        objArr[2] = profile != null ? profile.getCarGenerationName() : null;
        objArr[3] = profile != null ? profile.getCarSerieName() : null;
        String string = getString(R.string.text_your_car_info, objArr);
        Intrinsics.checkNotNullExpressionValue(string, "getString(\n            R…e?.carSerieName\n        )");
        builder.setMessage(str + string).setPositiveButton(android.R.string.yes, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    public final AlertDialog createNoCarChosenAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886084);
        String string = getString(R.string.alert_title_no_car_chosen);
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.alert_title_no_car_chosen)");
        String string2 = getString(R.string.button_text_choose_car);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.button_text_choose_car)");
        builder.setMessage(string).setPositiveButton(string2, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda13
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EmergencySituationBaseActivity.createNoCarChosenAlertDialog$lambda$9(this.f$0, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createNoCarChosenAlertDialog$lambda$9(EmergencySituationBaseActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EventBus.getDefault().post(new LoaderChangedEvent(false));
        this$0.startActivity(new Intent(this$0, (Class<?>) ChangeCarActivity.class));
    }

    private final AlertDialog createErrorLogAlertDialog(String errorBytes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886083);
        builder.setTitle(R.string.warning_unknown_error).setMessage("0x" + errorBytes).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda10
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EmergencySituationBaseActivity.createErrorLogAlertDialog$lambda$10(this.f$0, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createErrorLogAlertDialog$lambda$10(EmergencySituationBaseActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBleManager().executeTestCommand();
    }

    protected final AlertDialog createErrorUploadSoundPackageAlertDialog(final ShopSoundPackage soundPackage) {
        Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886084);
        builder.setMessage(getString(R.string.text_error_uploading_sound_package)).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda11
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EmergencySituationBaseActivity.createErrorUploadSoundPackageAlertDialog$lambda$11(dialogInterface, i);
            }
        }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda12
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EmergencySituationBaseActivity.createErrorUploadSoundPackageAlertDialog$lambda$12(this.f$0, soundPackage, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createErrorUploadSoundPackageAlertDialog$lambda$11(DialogInterface dialogInterface, int i) {
        Settings.INSTANCE.saveLoadSoundInterrupted(false);
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createErrorUploadSoundPackageAlertDialog$lambda$12(EmergencySituationBaseActivity this$0, ShopSoundPackage soundPackage, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(soundPackage, "$soundPackage");
        if (this$0.getBleManager().getMStateConnected()) {
            DownloadSoundPackageDialogFragment downloadSoundPackageDialogFragmentNewInstance = DownloadSoundPackageDialogFragment.INSTANCE.newInstance(soundPackage);
            if (downloadSoundPackageDialogFragmentNewInstance.isAdded()) {
                return;
            }
            downloadSoundPackageDialogFragmentNewInstance.show(this$0.getSupportFragmentManager(), "DownloadSoundPackageDialogFragment");
            return;
        }
        DialogManager.INSTANCE.createNoConnectionToBoardAlertDialog(this$0).show();
    }

    private final AlertDialog createCanConfigurationSuccessfulAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131886083);
        builder.setTitle(R.string.title_done).setMessage(R.string.title_can_file_configured).setPositiveButton(android.R.string.yes, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    protected final void openUpdateFirmwareDialog() {
        UpdateFirmwareDialogFragment updateFirmwareDialogFragmentNewInstance = UpdateFirmwareDialogFragment.INSTANCE.newInstance();
        if (updateFirmwareDialogFragmentNewInstance.isAdded()) {
            return;
        }
        updateFirmwareDialogFragmentNewInstance.show(getSupportFragmentManager(), "UpdateFirmwareDialogFragment");
    }

    private final void onFinishFormatFlash(boolean showDelayDialog) {
        getMLoadingDialog().dismiss();
        if (showDelayDialog) {
            final FormatProgressDialogFragment formatProgressDialogFragmentNewInstance = FormatProgressDialogFragment.INSTANCE.newInstance();
            formatProgressDialogFragmentNewInstance.show(getSupportFragmentManager(), "FormatProgressDialogFragment");
            FragmentKt.setFragmentResultListener(formatProgressDialogFragmentNewInstance, FormatProgressDialogFragment.INSTANCE.getBUNDLE_NAME(), new Function2<String, Bundle, Unit>() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity.onFinishFormatFlash.1
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
                    EmergencySituationBaseActivity.this.createFinishFormatFlashDialog().show();
                    FragmentKt.clearFragmentResultListener(formatProgressDialogFragmentNewInstance, FormatProgressDialogFragment.INSTANCE.getBUNDLE_NAME());
                }
            });
        }
    }

    @Subscribe
    public void onMessageEvent(StartUpdateFirmwareEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(FormatFlashExecuteEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (event.isSuccess()) {
            ThorDatabase.INSTANCE.from(this).installedSoundPackageDao().deleteAllElements();
            onFinishFormatFlash(event.getShowFormatDelay());
        } else {
            getMLoadingDialog().dismiss();
            String string = getString(R.string.text_format_flash_error);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.text_format_flash_error)");
            createErrorMessageAlertDialog(string).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(FormatFlashEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (!event.isStart() || this.formatFlash) {
            return;
        }
        this.formatFlash = true;
        createFormatFlashAlertDialog(true).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(ReloadParametersEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (event.isStart()) {
            createReloadParametersAlertDialog().show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UpdateCanConfigurationsErrorEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (event.isNetworkError()) {
            createNoCarChosenAlertDialog().show();
        } else if (event.isUploadingOnSchemaError()) {
            String string = getString(R.string.text_error_write_file);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.text_error_write_file)");
            createErrorMessageAlertDialog(string).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BluetoothCommandErrorEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        Timber.INSTANCE.e("Error: %s", Hex.bytesToStringUppercase(event.getBytesError()));
        if (!event.getIsTrash()) {
            String strBytesToStringUppercase = Hex.bytesToStringUppercase(event.getBytesError());
            Intrinsics.checkNotNullExpressionValue(strBytesToStringUppercase, "bytesToStringUppercase(event.bytesError)");
            createErrorLogAlertDialog(strBytesToStringUppercase).show();
        }
        getBleManager().clear();
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
    public final void onMessageEvent(UpdateCanConfigurationsSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        getBleManager().executeInitCrypto(null);
        createCanConfigurationSuccessfulAlertDialog().show();
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected final void initInternetConnectionListener() {
        /*
            r5 = this;
            android.os.Handler r0 = new android.os.Handler
            android.os.Looper r1 = r5.getMainLooper()
            r0.<init>(r1)
            r1 = 2131821011(0x7f1101d3, float:1.9274753E38)
            java.lang.String r1 = r5.getString(r1)
            java.lang.String r2 = "getString(R.string.text_…internet_connection_lost)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
            androidx.appcompat.app.AlertDialog r1 = r5.createErrorMessageAlertDialog(r1)
            com.thor.app.gui.activities.EmergencySituationBaseActivity$initInternetConnectionListener$networkCallback$1 r2 = new com.thor.app.gui.activities.EmergencySituationBaseActivity$initInternetConnectionListener$networkCallback$1
            r2.<init>(r0, r1, r5)
            android.net.ConnectivityManager$NetworkCallback r2 = (android.net.ConnectivityManager.NetworkCallback) r2
            java.lang.String r0 = "connectivity"
            java.lang.Object r0 = r5.getSystemService(r0)
            java.lang.String r3 = "null cannot be cast to non-null type android.net.ConnectivityManager"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0, r3)
            android.net.ConnectivityManager r0 = (android.net.ConnectivityManager) r0
            r0.registerDefaultNetworkCallback(r2)
            android.net.Network r2 = r0.getActiveNetwork()
            r3 = 0
            if (r2 == 0) goto L55
            android.net.Network r2 = r0.getActiveNetwork()
            android.net.NetworkCapabilities r0 = r0.getNetworkCapabilities(r2)
            if (r0 == 0) goto L46
            boolean r2 = r0.hasTransport(r3)
            goto L47
        L46:
            r2 = r3
        L47:
            r4 = 1
            if (r2 != 0) goto L54
            if (r0 == 0) goto L51
            boolean r0 = r0.hasTransport(r4)
            goto L52
        L51:
            r0 = r3
        L52:
            if (r0 == 0) goto L55
        L54:
            r3 = r4
        L55:
            if (r3 != 0) goto L5a
            r1.show()
        L5a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.gui.activities.EmergencySituationBaseActivity.initInternetConnectionListener():void");
    }

    /* compiled from: EmergencySituationBaseActivity.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Ljava/io/ByteArrayOutputStream;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.EmergencySituationBaseActivity$changeLockDeviceState$1, reason: invalid class name */
    static final class AnonymousClass1 extends Lambda implements Function1<ByteArrayOutputStream, Unit> {
        final /* synthetic */ boolean $isLocked;
        final /* synthetic */ EmergencySituationBaseActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(boolean z, EmergencySituationBaseActivity emergencySituationBaseActivity) {
            super(1);
            this.$isLocked = z;
            this.this$0 = emergencySituationBaseActivity;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
            invoke2(byteArrayOutputStream);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
            byte[] bytes = byteArrayOutputStream.toByteArray();
            Timber.INSTANCE.i("Take data: %s", Hex.bytesToStringUppercase(bytes));
            Intrinsics.checkNotNullExpressionValue(bytes, "bytes");
            WriteLockDeviceBleResponse writeLockDeviceBleResponse = new WriteLockDeviceBleResponse(bytes);
            if (writeLockDeviceBleResponse.getStatus()) {
                Timber.INSTANCE.i("lockDevice: " + this.$isLocked, new Object[0]);
                EventBus.getDefault().post(new FetchConfigEvent());
                return;
            }
            Timber.INSTANCE.e("Response is not correct: %s\nDevice connected: %s", writeLockDeviceBleResponse.getErrorCode(), Boolean.valueOf(this.this$0.getBleManager().isBleEnabledAndDeviceConnected()));
            Handler handler = new Handler(this.this$0.getMainLooper());
            final EmergencySituationBaseActivity emergencySituationBaseActivity = this.this$0;
            final boolean z = this.$isLocked;
            handler.postDelayed(new Runnable() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$changeLockDeviceState$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EmergencySituationBaseActivity.AnonymousClass1.invoke$lambda$2(emergencySituationBaseActivity, z);
                }
            }, 3000L);
        }

        private static final void invoke$lambda$0(EmergencySituationBaseActivity this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Toast.makeText(this$0, "Device fully LOCKED", 0).show();
        }

        private static final void invoke$lambda$1(EmergencySituationBaseActivity this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Toast.makeText(this$0, "Device fully UNLOCKED", 0).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(EmergencySituationBaseActivity this$0, boolean z) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.changeLockDeviceState(z);
        }
    }

    protected final void changeLockDeviceState(boolean isLocked) {
        BleManager bleManager = getBleManager();
        Observable<ByteArrayOutputStream> observableExecuteWriteLockDeviceCommand = getBleManager().executeWriteLockDeviceCommand(isLocked);
        final AnonymousClass1 anonymousClass1 = new AnonymousClass1(isLocked, this);
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                EmergencySituationBaseActivity.changeLockDeviceState$lambda$13(anonymousClass1, obj);
            }
        };
        final AnonymousClass2 anonymousClass2 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity.changeLockDeviceState.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        Disposable disposableSubscribe = observableExecuteWriteLockDeviceCommand.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.EmergencySituationBaseActivity$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                EmergencySituationBaseActivity.changeLockDeviceState$lambda$14(anonymousClass2, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(disposableSubscribe, "protected fun changeLock…       })\n        )\n    }");
        bleManager.addCommandDisposable(disposableSubscribe);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void changeLockDeviceState$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void changeLockDeviceState$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }
}
