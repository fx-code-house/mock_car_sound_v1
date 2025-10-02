package com.thor.app.managers;

import android.content.Context;
import android.util.Log;
import com.google.firebase.messaging.Constants;
import com.thor.app.ThorApplication;
import com.thor.app.bus.events.HandleFormatFlashDialogCloseEvent;
import com.thor.app.bus.events.StartCheckEmergencySituationsEvent;
import com.thor.app.bus.events.device.DeviceCanInfoUpdated;
import com.thor.app.bus.events.device.DeviceLockedStateEvent;
import com.thor.app.bus.events.device.FormatFlashEvent;
import com.thor.app.bus.events.device.ReloadParametersEvent;
import com.thor.app.bus.events.device.UpdateHardwareProfileEvent;
import com.thor.app.bus.events.shop.main.DownloadSettingsFileSuccessEvent;
import com.thor.app.gui.dialog.CanFileManager;
import com.thor.app.settings.Settings;
import com.thor.businessmodule.bluetooth.model.other.DeviceParameters;
import com.thor.businessmodule.bluetooth.model.other.HardwareProfile;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresets;
import com.thor.businessmodule.bluetooth.model.other.settings.DeviceStatus;
import com.thor.networkmodule.model.firmware.FirmwareProfile;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: SchemaEmergencySituationsManager.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \"2\u00020\u0001:\u0001\"B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0011J\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aJ\b\u0010\u001b\u001a\u00020\u0017H\u0002J\u0010\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\u0010\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u001fH\u0007J\u0006\u0010 \u001a\u00020\u0017J\u0006\u0010!\u001a\u00020\u0017R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006#"}, d2 = {"Lcom/thor/app/managers/SchemaEmergencySituationsManager;", "", "mContext", "Landroid/content/Context;", "(Landroid/content/Context;)V", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "canFileManager", "Lcom/thor/app/gui/dialog/CanFileManager;", "getCanFileManager", "()Lcom/thor/app/gui/dialog/CanFileManager;", "canFileManager$delegate", "isEmergencyUpdate", "", "isUpdateN", "", "getMContext", "()Landroid/content/Context;", "checkEmergencySituations", "", "isFormatFlash", "checkFirmwareVersionOnDifference", "Lcom/thor/businessmodule/bluetooth/model/other/DeviceParameters$PARAMS;", "doHandleEmergencySituations", "onMessageEvent", "event", "Lcom/thor/app/bus/events/HandleFormatFlashDialogCloseEvent;", "Lcom/thor/app/bus/events/StartCheckEmergencySituationsEvent;", "startCheckForEmergencySituations", "stopCheckForEmergencySituations", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SchemaEmergencySituationsManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager;

    /* renamed from: canFileManager$delegate, reason: from kotlin metadata */
    private final Lazy canFileManager;
    private boolean isEmergencyUpdate;
    private int isUpdateN;
    private final Context mContext;

    public SchemaEmergencySituationsManager(Context mContext) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        this.mContext = mContext;
        this.bleManager = LazyKt.lazy(new Function0<BleManager>() { // from class: com.thor.app.managers.SchemaEmergencySituationsManager$bleManager$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final BleManager invoke() {
                return BleManager.INSTANCE.from(this.this$0.getMContext());
            }
        });
        this.canFileManager = LazyKt.lazy(new Function0<CanFileManager>() { // from class: com.thor.app.managers.SchemaEmergencySituationsManager$canFileManager$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final CanFileManager invoke() {
                return CanFileManager.INSTANCE.from(this.this$0.getMContext());
            }
        });
    }

    public final Context getMContext() {
        return this.mContext;
    }

    private final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    private final CanFileManager getCanFileManager() {
        return (CanFileManager) this.canFileManager.getValue();
    }

    public final void startCheckForEmergencySituations() throws SecurityException {
        if (EventBus.getDefault().isRegistered(this)) {
            return;
        }
        EventBus.getDefault().register(this);
    }

    public final void stopCheckForEmergencySituations() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public final void checkEmergencySituations(boolean isFormatFlash) {
        Log.i("NEW_LOG", "checkEmergencySituations isFormatFlash: " + isFormatFlash);
        doHandleEmergencySituations();
    }

    private final void doHandleEmergencySituations() {
        Settings.saveIsCheckedEmrgencySituations(true);
        DeviceStatus deviceStatus = Settings.INSTANCE.getDeviceStatus();
        if (deviceStatus != null) {
            this.isEmergencyUpdate = false;
            EventBus.getDefault().post(new DeviceCanInfoUpdated());
            EventBus.getDefault().post(new DeviceLockedStateEvent(deviceStatus.getDeviceBlocked()));
            boolean presetError = deviceStatus.getPresetError();
            Timber.INSTANCE.i("isNeedReloadSoundPackages: %s", Boolean.valueOf(presetError));
            Timber.INSTANCE.i("isNeedUploadCan = " + (deviceStatus.getCanFileError() | Settings.INSTANCE.getSignUp()), new Object[0]);
            Settings.INSTANCE.setSignUp(false);
            if (deviceStatus.getFileSystemError()) {
                EventBus.getDefault().post(new FormatFlashEvent(true));
            } else if (deviceStatus.getCanFileError()) {
                int i = this.isUpdateN;
                if (i < 10) {
                    this.isUpdateN = i + 1;
                    getCanFileManager().startUpdate();
                    if (presetError) {
                        EventBus.getDefault().post(new ReloadParametersEvent(true));
                    }
                } else {
                    InstalledPresets installPresets = Settings.INSTANCE.getInstallPresets();
                    if (installPresets == null) {
                        installPresets = new InstalledPresets((short) 0, (short) 0, null, 6, null);
                    }
                    EventBus.getDefault().post(new DownloadSettingsFileSuccessEvent(true, installPresets));
                }
            } else {
                InstalledPresets installPresets2 = Settings.INSTANCE.getInstallPresets();
                if (installPresets2 == null) {
                    installPresets2 = new InstalledPresets((short) 0, (short) 0, null, 6, null);
                }
                EventBus.getDefault().post(new DownloadSettingsFileSuccessEvent(true, installPresets2));
            }
            if (deviceStatus.getFileSystemError()) {
                EventBus.getDefault().post(new FormatFlashEvent(true));
            }
        }
    }

    public final DeviceParameters.PARAMS checkFirmwareVersionOnDifference() {
        Timber.INSTANCE.i("checkFirmwareVersionOnDifference", new Object[0]);
        FirmwareProfile firmwareProfile = Settings.INSTANCE.getFirmwareProfile();
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        if (firmwareProfile != null && hardwareProfile != null) {
            Timber.INSTANCE.tag("TEST1212").i("version firmware: %s, hardware version: %s", Integer.valueOf(firmwareProfile.getVersionFW()), Short.valueOf(hardwareProfile.getVersionFirmware()));
            EventBus.getDefault().post(new UpdateHardwareProfileEvent(hardwareProfile));
            if (firmwareProfile.getVersionFW() > hardwareProfile.getVersionFirmware()) {
                if (!hardwareProfile.isDefaultValuesSet() && hardwareProfile.getVersionFirmware() <= 305) {
                    this.isEmergencyUpdate = true;
                }
                return DeviceParameters.PARAMS.NEED_UPDATE_FIRMWARE;
            }
        }
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(StartCheckEmergencySituationsEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        checkEmergencySituations(event.isFormatFlash());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(HandleFormatFlashDialogCloseEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        getBleManager().executeInitCrypto(null);
    }

    /* compiled from: SchemaEmergencySituationsManager.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\u0007"}, d2 = {"Lcom/thor/app/managers/SchemaEmergencySituationsManager$Companion;", "", "()V", Constants.MessagePayloadKeys.FROM, "Lcom/thor/app/managers/SchemaEmergencySituationsManager;", "context", "Landroid/content/Context;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SchemaEmergencySituationsManager from(Context context) {
            Context applicationContext = context != null ? context.getApplicationContext() : null;
            Intrinsics.checkNotNull(applicationContext, "null cannot be cast to non-null type com.thor.app.ThorApplication");
            return ((ThorApplication) applicationContext).getSchemaEmergencySituationsManager();
        }
    }
}
