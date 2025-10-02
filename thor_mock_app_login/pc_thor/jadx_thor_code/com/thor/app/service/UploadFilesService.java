package com.thor.app.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import com.carsystems.thor.app.R;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.wallet.WalletConstants;
import com.google.gson.Gson;
import com.thor.app.managers.BleManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.service.UploadFilesService;
import com.thor.app.service.models.UploadFileGroupModel;
import com.thor.app.service.models.UploadFileModel;
import com.thor.app.service.state.UploadServiceState;
import com.thor.app.serviceg.BleParseRequest;
import com.thor.app.settings.Settings;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.businessmodule.bluetooth.model.other.DownloadGetStatusModel;
import com.thor.businessmodule.bluetooth.model.other.FileType;
import com.thor.businessmodule.bluetooth.model.other.HardwareProfile;
import com.thor.businessmodule.bluetooth.model.other.IdFileModel;
import com.thor.businessmodule.bluetooth.model.other.InstalledPreset;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresets;
import com.thor.businessmodule.bluetooth.request.other.ActivatePresetBleRequest;
import com.thor.businessmodule.bluetooth.request.other.ApplyUploadSoundPackageBleRequestNew;
import com.thor.businessmodule.bluetooth.request.other.BaseBleRequest;
import com.thor.businessmodule.bluetooth.request.other.DownloadCommitFileBleRequest;
import com.thor.businessmodule.bluetooth.request.other.DownloadGetStatusBleRequest;
import com.thor.businessmodule.bluetooth.request.other.DownloadStartGroupBleRequest;
import com.thor.businessmodule.bluetooth.request.other.HandShakeBleRequest;
import com.thor.businessmodule.bluetooth.request.other.HardwareBleRequest;
import com.thor.businessmodule.bluetooth.request.other.UploadStartFileBleRequest;
import com.thor.businessmodule.bluetooth.request.other.WriteBlockFileBleRequestNew;
import com.thor.businessmodule.bluetooth.request.other.WriteInstalledPresetsBleRequest;
import com.thor.businessmodule.bluetooth.util.BleCommands;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import com.thor.businessmodule.crypto.CryptoManager;
import com.thor.businessmodule.crypto.EncryptionHelper;
import com.thor.businessmodule.settings.Variables;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import com.welie.blessed.BluetoothCentralManager;
import com.welie.blessed.BluetoothPeripheral;
import com.welie.blessed.ConnectionState;
import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.SharedFlowKt;
import org.apache.commons.lang3.StringUtils;

/* compiled from: UploadFilesService.kt */
@Metadata(d1 = {"\u0000æ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0014\u0018\u0000 \u0091\u00012\u00020\u0001:\u0004\u0091\u0001\u0092\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010N\u001a\u0002062\u0006\u0010O\u001a\u00020#H\u0002J\b\u0010P\u001a\u000206H\u0002J\b\u0010Q\u001a\u000206H\u0002J\u0010\u0010R\u001a\u0002062\u0006\u0010S\u001a\u00020TH\u0002J\u0010\u0010U\u001a\u0002062\u0006\u0010V\u001a\u00020\u0018H\u0002J\u0010\u0010W\u001a\u0002062\u0006\u0010C\u001a\u00020@H\u0002J\b\u0010X\u001a\u000206H\u0002J\u0010\u0010\u001e\u001a\u0002062\u0006\u0010Y\u001a\u000202H\u0002J\u0010\u0010Z\u001a\u0002062\u0006\u0010[\u001a\u00020\\H\u0002J\b\u0010]\u001a\u000206H\u0002J\b\u0010^\u001a\u000206H\u0002J\u0010\u0010_\u001a\u0002062\u0006\u0010`\u001a\u00020\u000bH\u0002J\b\u0010a\u001a\u000206H\u0002J\u0019\u0010b\u001a\u0002062\u0006\u0010c\u001a\u00020\u0018H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010dJ\u0019\u0010e\u001a\u0002062\u0006\u0010f\u001a\u00020#H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010gJ\u0019\u0010h\u001a\u0002062\u0006\u0010i\u001a\u00020jH\u0082@ø\u0001\u0000¢\u0006\u0002\u0010kJ\u0012\u0010l\u001a\u00020m2\b\u0010n\u001a\u0004\u0018\u00010oH\u0016J\b\u0010p\u001a\u000206H\u0016J\b\u0010q\u001a\u000206H\u0016J\"\u0010r\u001a\u00020#2\b\u0010n\u001a\u0004\u0018\u00010o2\u0006\u0010s\u001a\u00020#2\u0006\u0010t\u001a\u00020#H\u0016J\b\u0010u\u001a\u000206H\u0002J \u0010v\u001a\b\u0012\u0004\u0012\u00020\u000b0w2\u0006\u0010`\u001a\u00020\u000b2\b\b\u0002\u0010x\u001a\u00020#H\u0002J\b\u0010y\u001a\u000206H\u0002J\b\u0010z\u001a\u000206H\u0002J\b\u0010{\u001a\u000206H\u0002J\b\u0010|\u001a\u000206H\u0002J\u0010\u0010}\u001a\u0002062\u0006\u0010~\u001a\u00020\u007fH\u0002J\u0011\u0010\u0080\u0001\u001a\u0002062\u0006\u0010~\u001a\u00020\u007fH\u0002J\u0011\u0010\u0081\u0001\u001a\u0002062\u0006\u0010~\u001a\u00020\u007fH\u0002J\u0012\u0010\u0082\u0001\u001a\u0002062\u0007\u0010\u0083\u0001\u001a\u00020\bH\u0002J\u0011\u0010\u0084\u0001\u001a\u0002062\u0006\u0010~\u001a\u00020\u007fH\u0002J\t\u0010\u0085\u0001\u001a\u000206H\u0002J\t\u0010\u0086\u0001\u001a\u000206H\u0002J\t\u0010\u0087\u0001\u001a\u000206H\u0002J\t\u0010\u0088\u0001\u001a\u000206H\u0002J\t\u0010\u0089\u0001\u001a\u000206H\u0002J\t\u0010\u008a\u0001\u001a\u000206H\u0002J\u0011\u0010\u008b\u0001\u001a\u0002062\u0006\u0010`\u001a\u00020*H\u0002J,\u0010\u008c\u0001\u001a\u0002062\u0006\u0010C\u001a\u00020@2\u0007\u0010\u008d\u0001\u001a\u00020<2\u0006\u0010`\u001a\u00020\u000bH\u0082@ø\u0001\u0000¢\u0006\u0003\u0010\u008e\u0001J\u001a\u0010\u008f\u0001\u001a\u0002062\u0006\u0010`\u001a\u00020\u000b2\u0007\u0010\u0090\u0001\u001a\u00020*H\u0002R\u0012\u0010\u0003\u001a\u00060\u0004R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0019\u001a\u00020\u001a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u0016\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010*X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010+\u001a\u00020,8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b/\u0010\u0016\u001a\u0004\b-\u0010.R\u000e\u00100\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u000202X\u0082\u000e¢\u0006\u0002\n\u0000R(\u00103\u001a\u0010\u0012\u0004\u0012\u000205\u0012\u0004\u0012\u000206\u0018\u000104X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u0010\u0010;\u001a\u0004\u0018\u00010<X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R>\u0010>\u001a2\u0012\u0013\u0012\u00110@¢\u0006\f\bA\u0012\b\bB\u0012\u0004\b\b(C\u0012\u0013\u0012\u00110\r¢\u0006\f\bA\u0012\b\bB\u0012\u0004\b\b(D\u0012\u0004\u0012\u0002060?X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u000202X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010F\u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020#X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010I\u001a\u0004\u0018\u00010<X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010J\u001a\b\u0012\u0004\u0012\u00020\u000b0KX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020MX\u0082.¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0093\u0001"}, d2 = {"Lcom/thor/app/service/UploadFilesService;", "Landroid/app/Service;", "()V", "binder", "Lcom/thor/app/service/UploadFilesService$UploadServiceBinder;", "btManager", "Lcom/welie/blessed/BluetoothCentralManager;", "closeResponse", "Lcom/thor/app/service/state/UploadServiceState$Stop;", "commandFlow", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "connectionState", "Lcom/welie/blessed/ConnectionState;", "currentIv", "currentUploadFile", "Lcom/thor/app/service/models/UploadFileModel;", "database", "Lcom/thor/app/room/ThorDatabase;", "getDatabase", "()Lcom/thor/app/room/ThorDatabase;", "database$delegate", "Lkotlin/Lazy;", "deviceAddressConnection", "", "fileLogger", "Lcom/thor/app/utils/logs/loggers/FileLogger;", "getFileLogger", "()Lcom/thor/app/utils/logs/loggers/FileLogger;", "fileLogger$delegate", "forceStop", "Lkotlinx/coroutines/CoroutineScope;", "hardwareProfile", "Lcom/thor/businessmodule/bluetooth/model/other/HardwareProfile;", "initCount", "", "isBordAnswer", "", "isDeActivePreset", "isNordic", "isStatusCancel", "lastWriteCommand", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "mBleOldManager", "Lcom/thor/app/managers/BleManager;", "getMBleOldManager", "()Lcom/thor/app/managers/BleManager;", "mBleOldManager$delegate", "priorityScope", "priorityTimeRequest", "", "progressUpload", "Lkotlin/Function1;", "Lcom/thor/app/service/state/UploadServiceState;", "", "getProgressUpload", "()Lkotlin/jvm/functions/Function1;", "setProgressUpload", "(Lkotlin/jvm/functions/Function1;)V", "rxCharacteristic", "Landroid/bluetooth/BluetoothGattCharacteristic;", "scope", "stateConnectionCallBack", "Lkotlin/Function2;", "Lcom/welie/blessed/BluetoothPeripheral;", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "peripheral", "state", "timeReconnect", "timerScope", "totalBlockNeedUpload", "tryTimeReconnect", "txCharacteristic", "uploadingFile", "", "uploadingFileGroupModel", "Lcom/thor/app/service/models/UploadFileGroupModel;", "calculateProgress", "blockLeftUpload", "calculateUploadingFile", "clearResources", "controlInstallPresets", "installPreset", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "createNotification", NotificationCompat.CATEGORY_MESSAGE, "createRxTxCharacteristic", "deActivePreset", "time", "getFile", "fileType", "Lcom/thor/businessmodule/bluetooth/model/other/FileType;", "getStatusUploadFiles", "holderPollingSuccess", "initHandShake", "data", "loadInstallPresets", "loadingUploadingFirmwareFile", "fwUrl", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadingUploadingPresetFile", "packageId", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadingUploadingSguFile", "sguModel", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "(Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "flags", "startId", "registerNotificationChannel", "splitByteArray", "", "blockSize", "startHandShake", "startObservingRxCharacteristic", "startPolingStatus", "startScanningAndConnectToDevice", "statusDownloading", "model", "Lcom/thor/businessmodule/bluetooth/model/other/DownloadGetStatusModel;", "statusFileCommitted", "statusFileStarted", "stop", "reason", "tryReUploadFiles", "tryStartInitialHandShake", "uploadCommitFile", "uploadCommitGroupFiles", "uploadStartFile", "uploadStartGroupFiles", "uploadWriteBlockFile", "waitResponse", "writeData", "characteristic", "(Lcom/welie/blessed/BluetoothPeripheral;Landroid/bluetooth/BluetoothGattCharacteristic;[BLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "writeDataToCharacteristic", "request", "Companion", "UploadServiceBinder", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UploadFilesService extends Service {
    public static final String ACTION_START_FW = "com.thor.app.service.ACTION_START_FW";
    public static final String ACTION_START_PRESET = "com.thor.app.service.ACTION_START_PRESET";
    public static final String ACTION_START_SGU = "com.thor.app.service.ACTION_START_SGU";
    public static final String DEVICE_CONNECTION_ADDRESS_KEY = "com.thor.app.service.DEVICE_CONNECTION_SERIAL_KEY";
    public static final String DEVICE_CONNECTION_FIRMWARE_KEY = "com.thor.app.service.DEVICE_CONNECTION_FIRMWARE_KEY";
    public static final String DEVICE_CONNECTION_PACKAGE_ID_KEY = "com.thor.app.service.DEVICE_CONNECTION_HARDWARE_KEY";
    public static final String DEVICE_CONNECTION_SGU_SOUND_SET_KEY = "com.thor.app.service.DEVICE_CONNECTION_SGU_SOUND_SET_KEY";
    public static final String NOTIFICATION_CHANNEL_ID = "tor_channel_service";
    private static SguSoundSet loadSguModel;
    private static boolean serviceIsWorked;
    private BluetoothCentralManager btManager;
    private UploadFileModel currentUploadFile;
    private String deviceAddressConnection;
    private HardwareProfile hardwareProfile;
    private int initCount;
    private boolean isStatusCancel;
    private BaseBleRequest lastWriteCommand;
    private long priorityTimeRequest;
    private Function1<? super UploadServiceState, Unit> progressUpload;
    private BluetoothGattCharacteristic rxCharacteristic;
    private long timeReconnect;
    private CoroutineScope timerScope;
    private int totalBlockNeedUpload;
    private int tryTimeReconnect;
    private BluetoothGattCharacteristic txCharacteristic;
    private UploadFileGroupModel uploadingFileGroupModel;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static String uploadingName = "";
    private static UploadFileGroupModel.TypeGroupFile typeFile = UploadFileGroupModel.TypeGroupFile.SOUND;
    private static boolean disconnectedStatus = true;
    private static final UUID SERVICE_UUID = UUID.fromString(BleManager.SERVICE_UUID);
    private static final UUID CHARACTERISTIC_UUID = UUID.fromString(BleManager.CHARACTERISTIC_UUID);
    private static final UUID SERVICE_NORDIC_UUID = UUID.fromString(BleManager.SERVICE_NORDIC_UUID);
    private static final UUID CHARACTERISTIC_NORDIC_TX_UUID = UUID.fromString(BleManager.CHARACTERISTIC_NORDIC_WRITE_UUID);
    private static final UUID CHARACTERISTIC_NORDIC_RX_UUID = UUID.fromString(BleManager.CHARACTERISTIC_NORDIC_NOTIFY_UUID);
    private final MutableSharedFlow<byte[]> commandFlow = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6, null);

    /* renamed from: fileLogger$delegate, reason: from kotlin metadata */
    private final Lazy fileLogger = LazyKt.lazy(new Function0<FileLogger>() { // from class: com.thor.app.service.UploadFilesService$fileLogger$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final FileLogger invoke() {
            return FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, this.this$0.getBaseContext(), null, 2, null);
        }
    });

    /* renamed from: database$delegate, reason: from kotlin metadata */
    private final Lazy database = LazyKt.lazy(new Function0<ThorDatabase>() { // from class: com.thor.app.service.UploadFilesService$database$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThorDatabase invoke() {
            ThorDatabase.Companion companion = ThorDatabase.INSTANCE;
            Context baseContext = this.this$0.getBaseContext();
            Intrinsics.checkNotNullExpressionValue(baseContext, "this.baseContext");
            return companion.from(baseContext);
        }
    });
    private boolean isNordic = true;
    private final CoroutineScope scope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
    private final CoroutineScope priorityScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
    private CoroutineScope forceStop = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
    private byte[] currentIv = new byte[0];
    private List<byte[]> uploadingFile = new ArrayList();
    private ConnectionState connectionState = ConnectionState.DISCONNECTED;
    private UploadServiceState.Stop closeResponse = UploadServiceState.Stop.Cancel.INSTANCE;
    private final UploadServiceBinder binder = new UploadServiceBinder();
    private boolean isDeActivePreset = true;
    private boolean isBordAnswer = true;

    /* renamed from: mBleOldManager$delegate, reason: from kotlin metadata */
    private final Lazy mBleOldManager = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager>() { // from class: com.thor.app.service.UploadFilesService$mBleOldManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0.getBaseContext());
        }
    });
    private final Function2<BluetoothPeripheral, ConnectionState, Unit> stateConnectionCallBack = new Function2<BluetoothPeripheral, ConnectionState, Unit>() { // from class: com.thor.app.service.UploadFilesService$stateConnectionCallBack$1

        /* compiled from: UploadFilesService.kt */
        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ConnectionState.values().length];
                try {
                    iArr[ConnectionState.CONNECTED.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ConnectionState.DISCONNECTING.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ConnectionState.DISCONNECTED.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        {
            super(2);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(BluetoothPeripheral bluetoothPeripheral, ConnectionState connectionState) throws InterruptedException {
            invoke2(bluetoothPeripheral, connectionState);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(BluetoothPeripheral peripheral, ConnectionState state) throws InterruptedException {
            Intrinsics.checkNotNullParameter(peripheral, "peripheral");
            Intrinsics.checkNotNullParameter(state, "state");
            this.this$0.getFileLogger().i("SERVICE: State " + state, new Object[0]);
            if (state == ConnectionState.DISCONNECTED) {
                UploadFilesService.Companion companion = UploadFilesService.INSTANCE;
                UploadFilesService.disconnectedStatus = true;
            } else {
                UploadFilesService.Companion companion2 = UploadFilesService.INSTANCE;
                UploadFilesService.disconnectedStatus = false;
            }
            int i = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
            if (i != 1) {
                if (i == 3) {
                    this.this$0.clearResources();
                    this.this$0.timeReconnect = System.currentTimeMillis();
                    while (!this.this$0.getMBleOldManager().getMStateConnected()) {
                        UploadFileGroupModel uploadFileGroupModel = this.this$0.uploadingFileGroupModel;
                        if (uploadFileGroupModel == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
                            uploadFileGroupModel = null;
                        }
                        if (uploadFileGroupModel.getTypeGroupFile() == UploadFileGroupModel.TypeGroupFile.FIRMWARE) {
                            break;
                        }
                        Function1<UploadServiceState, Unit> progressUpload = this.this$0.getProgressUpload();
                        if (progressUpload != null) {
                            progressUpload.invoke(UploadServiceState.Reconnect.INSTANCE);
                        }
                        if (System.currentTimeMillis() - this.this$0.timeReconnect > 5000) {
                            this.this$0.getFileLogger().i("SERVICE: Reconnect " + this.this$0.getMBleOldManager().getMStateConnected(), new Object[0]);
                            this.this$0.timeReconnect = System.currentTimeMillis();
                            this.this$0.tryTimeReconnect++;
                            this.this$0.getFileLogger().i("SERVICE: Reconnect try " + this.this$0.tryTimeReconnect, new Object[0]);
                            if (this.this$0.tryTimeReconnect > 5) {
                                this.this$0.forceStop(0L);
                            }
                            BuildersKt__BuildersKt.runBlocking$default(null, new AnonymousClass1(this.this$0, null), 1, null);
                        }
                    }
                    Function1<UploadServiceState, Unit> progressUpload2 = this.this$0.getProgressUpload();
                    if (progressUpload2 != null) {
                        progressUpload2.invoke(this.this$0.closeResponse);
                    }
                    this.this$0.stopSelf();
                }
            } else if (this.this$0.connectionState != ConnectionState.CONNECTED) {
                this.this$0.createRxTxCharacteristic(peripheral);
                this.this$0.startObservingRxCharacteristic();
            }
            this.this$0.connectionState = state;
        }

        /* compiled from: UploadFilesService.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
        @DebugMetadata(c = "com.thor.app.service.UploadFilesService$stateConnectionCallBack$1$1", f = "UploadFilesService.kt", i = {}, l = {162, 166}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.thor.app.service.UploadFilesService$stateConnectionCallBack$1$1, reason: invalid class name */
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            Object L$0;
            Object L$1;
            int label;
            final /* synthetic */ UploadFilesService this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(UploadFilesService uploadFilesService, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.this$0 = uploadFilesService;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                UploadFilesService uploadFilesService;
                Iterator it;
                AnonymousClass1 anonymousClass1;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    UploadFilesService.Companion companion = UploadFilesService.INSTANCE;
                    UploadFilesService.disconnectedStatus = true;
                    BluetoothCentralManager bluetoothCentralManager = this.this$0.btManager;
                    if (bluetoothCentralManager == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("btManager");
                        bluetoothCentralManager = null;
                    }
                    List<BluetoothPeripheral> connectedPeripherals = bluetoothCentralManager.getConnectedPeripherals();
                    uploadFilesService = this.this$0;
                    it = connectedPeripherals.iterator();
                } else {
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        anonymousClass1 = this;
                        anonymousClass1.this$0.getFileLogger().i("SERVICE: Reconnect connect", new Object[0]);
                        anonymousClass1.this$0.getMBleOldManager().connect();
                        return Unit.INSTANCE;
                    }
                    it = (Iterator) this.L$1;
                    uploadFilesService = (UploadFilesService) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                anonymousClass1 = this;
                while (it.hasNext()) {
                    BluetoothPeripheral bluetoothPeripheral = (BluetoothPeripheral) it.next();
                    uploadFilesService.getFileLogger().i("SERVICE: Reconnect disconnect " + bluetoothPeripheral.getAddress() + StringUtils.SPACE + bluetoothPeripheral.getName(), new Object[0]);
                    BluetoothCentralManager bluetoothCentralManager2 = uploadFilesService.btManager;
                    if (bluetoothCentralManager2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("btManager");
                        bluetoothCentralManager2 = null;
                    }
                    anonymousClass1.L$0 = uploadFilesService;
                    anonymousClass1.L$1 = it;
                    anonymousClass1.label = 1;
                    if (bluetoothCentralManager2.cancelConnection(bluetoothPeripheral, anonymousClass1) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
                anonymousClass1.this$0.getFileLogger().i("SERVICE: Reconnect disconnect", new Object[0]);
                anonymousClass1.this$0.getMBleOldManager().disconnect(false);
                anonymousClass1.L$0 = null;
                anonymousClass1.L$1 = null;
                anonymousClass1.label = 2;
                if (DelayKt.delay(1000L, anonymousClass1) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                anonymousClass1.this$0.getFileLogger().i("SERVICE: Reconnect connect", new Object[0]);
                anonymousClass1.this$0.getMBleOldManager().connect();
                return Unit.INSTANCE;
            }
        }
    };

    /* compiled from: UploadFilesService.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            int[] iArr = new int[DownloadGetStatusModel.Status.values().length];
            try {
                iArr[DownloadGetStatusModel.Status.GROUP_STARTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DownloadGetStatusModel.Status.FILE_STARTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DownloadGetStatusModel.Status.DOWNLOADING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DownloadGetStatusModel.Status.FILE_COMMITTED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[DownloadGetStatusModel.Status.GROUP_COMMITTED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[FileType.values().length];
            try {
                iArr2[FileType.SoundRulesPackageV2.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[FileType.SoundModeRulesPackageV2.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[FileType.SoundSamplePackageV2.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[FileType.SGU.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[FileType.FirmwareFile.ordinal()] = 5;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[UploadFileGroupModel.TypeGroupFile.values().length];
            try {
                iArr3[UploadFileGroupModel.TypeGroupFile.SOUND.ordinal()] = 1;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr3[UploadFileGroupModel.TypeGroupFile.SGU.ordinal()] = 2;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr3[UploadFileGroupModel.TypeGroupFile.FIRMWARE.ordinal()] = 3;
            } catch (NoSuchFieldError unused13) {
            }
            $EnumSwitchMapping$2 = iArr3;
        }
    }

    /* compiled from: UploadFilesService.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.UploadFilesService", f = "UploadFilesService.kt", i = {0}, l = {317}, m = "loadingUploadingFirmwareFile", n = {"this"}, s = {"L$0"})
    /* renamed from: com.thor.app.service.UploadFilesService$loadingUploadingFirmwareFile$1, reason: invalid class name and case insensitive filesystem */
    static final class C04171 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C04171(Continuation<? super C04171> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UploadFilesService.this.loadingUploadingFirmwareFile(null, this);
        }
    }

    /* compiled from: UploadFilesService.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.UploadFilesService", f = "UploadFilesService.kt", i = {0}, l = {292}, m = "loadingUploadingPresetFile", n = {"this"}, s = {"L$0"})
    /* renamed from: com.thor.app.service.UploadFilesService$loadingUploadingPresetFile$1, reason: invalid class name and case insensitive filesystem */
    static final class C04181 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C04181(Continuation<? super C04181> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UploadFilesService.this.loadingUploadingPresetFile(0, this);
        }
    }

    /* compiled from: UploadFilesService.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.UploadFilesService", f = "UploadFilesService.kt", i = {0}, l = {302}, m = "loadingUploadingSguFile", n = {"this"}, s = {"L$0"})
    /* renamed from: com.thor.app.service.UploadFilesService$loadingUploadingSguFile$1, reason: invalid class name and case insensitive filesystem */
    static final class C04191 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C04191(Continuation<? super C04191> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UploadFilesService.this.loadingUploadingSguFile(null, this);
        }
    }

    /* compiled from: UploadFilesService.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.UploadFilesService", f = "UploadFilesService.kt", i = {1, 1}, l = {397, WalletConstants.ERROR_CODE_MERCHANT_ACCOUNT_ERROR}, m = "writeData", n = {"peripheral", "characteristic"}, s = {"L$0", "L$1"})
    /* renamed from: com.thor.app.service.UploadFilesService$writeData$1, reason: invalid class name and case insensitive filesystem */
    static final class C04261 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        C04261(Continuation<? super C04261> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UploadFilesService.this.writeData(null, null, null, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void controlInstallPresets$lambda$30() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FileLogger getFileLogger() {
        return (FileLogger) this.fileLogger.getValue();
    }

    private final ThorDatabase getDatabase() {
        return (ThorDatabase) this.database.getValue();
    }

    public final Function1<UploadServiceState, Unit> getProgressUpload() {
        return this.progressUpload;
    }

    public final void setProgressUpload(Function1<? super UploadServiceState, Unit> function1) {
        this.progressUpload = function1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BleManager getMBleOldManager() {
        return (BleManager) this.mBleOldManager.getValue();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        try {
            getMBleOldManager().setServiceActivePresetIndex(getMBleOldManager().getMActivatedPresetIndex());
            getFileLogger().i("SERVICE: Create service", new Object[0]);
            Context baseContext = getBaseContext();
            Intrinsics.checkNotNullExpressionValue(baseContext, "this.baseContext");
            this.btManager = new BluetoothCentralManager(baseContext);
            Function1<? super UploadServiceState, Unit> function1 = this.progressUpload;
            if (function1 != null) {
                function1.invoke(UploadServiceState.Start.INSTANCE);
            }
            serviceIsWorked = true;
            BluetoothCentralManager bluetoothCentralManager = this.btManager;
            if (bluetoothCentralManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btManager");
                bluetoothCentralManager = null;
            }
            bluetoothCentralManager.observeConnectionState(this.stateConnectionCallBack);
        } catch (Exception e) {
            getFileLogger().i("SERVICE: Error create service " + e.getMessage(), new Object[0]);
            stop(new UploadServiceState.Stop.Error("Error create service"));
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        getFileLogger().i("SERVICE: Start service " + (intent != null ? intent.getAction() : null), new Object[0]);
        registerNotificationChannel();
        String string = getBaseContext().getString(R.string.service_downloading);
        Intrinsics.checkNotNullExpressionValue(string, "this.baseContext.getStri…ring.service_downloading)");
        createNotification(string);
        String action = intent != null ? intent.getAction() : null;
        if (action != null) {
            int iHashCode = action.hashCode();
            if (iHashCode != -964120441) {
                if (iHashCode != 177049483) {
                    if (iHashCode == 185185173 && action.equals(ACTION_START_PRESET)) {
                        typeFile = UploadFileGroupModel.TypeGroupFile.SOUND;
                        int intExtra = intent.getIntExtra(DEVICE_CONNECTION_PACKAGE_ID_KEY, -1);
                        String stringExtra = intent.getStringExtra(DEVICE_CONNECTION_ADDRESS_KEY);
                        if (intExtra != -1 && stringExtra != null) {
                            BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C04201(stringExtra, intExtra, null), 3, null);
                            return 2;
                        }
                        stop(new UploadServiceState.Stop.Error("Error launch service"));
                        return 2;
                    }
                } else if (action.equals(ACTION_START_SGU)) {
                    typeFile = UploadFileGroupModel.TypeGroupFile.SGU;
                    this.deviceAddressConnection = intent.getStringExtra(DEVICE_CONNECTION_ADDRESS_KEY);
                    SguSoundSet sguSoundSet = (SguSoundSet) new Gson().fromJson(intent.getStringExtra(DEVICE_CONNECTION_SGU_SOUND_SET_KEY), SguSoundSet.class);
                    loadSguModel = sguSoundSet;
                    if (!sguSoundSet.getFiles().isEmpty()) {
                        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C04212(sguSoundSet, null), 3, null);
                        return 2;
                    }
                    stop(new UploadServiceState.Stop.Error("Error launch service"));
                    return 2;
                }
            } else if (action.equals(ACTION_START_FW)) {
                getFileLogger().i("SERVICE: Start firmware", new Object[0]);
                typeFile = UploadFileGroupModel.TypeGroupFile.FIRMWARE;
                this.deviceAddressConnection = intent.getStringExtra(DEVICE_CONNECTION_ADDRESS_KEY);
                String stringExtra2 = intent.getStringExtra(DEVICE_CONNECTION_FIRMWARE_KEY);
                if (stringExtra2 != null && BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new UploadFilesService$onStartCommand$3$1(this, stringExtra2, null), 3, null) != null) {
                    return 2;
                }
                stop(new UploadServiceState.Stop.Error("Error launch service"));
                Unit unit = Unit.INSTANCE;
                return 2;
            }
        }
        getFileLogger().i("SERVICE: Error action " + (intent != null ? intent.getAction() : null), new Object[0]);
        stop(new UploadServiceState.Stop.Error("Error action " + (intent != null ? intent.getAction() : null)));
        return 2;
    }

    /* compiled from: UploadFilesService.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.UploadFilesService$onStartCommand$1", f = "UploadFilesService.kt", i = {}, l = {212}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.service.UploadFilesService$onStartCommand$1, reason: invalid class name and case insensitive filesystem */
    static final class C04201 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $connectionAddress;
        final /* synthetic */ int $packageId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04201(String str, int i, Continuation<? super C04201> continuation) {
            super(2, continuation);
            this.$connectionAddress = str;
            this.$packageId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UploadFilesService.this.new C04201(this.$connectionAddress, this.$packageId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04201) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                UploadFilesService.this.deviceAddressConnection = this.$connectionAddress;
                this.label = 1;
                if (UploadFilesService.this.loadingUploadingPresetFile(this.$packageId, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            UploadFilesService.this.startScanningAndConnectToDevice();
            return Unit.INSTANCE;
        }
    }

    /* compiled from: UploadFilesService.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.UploadFilesService$onStartCommand$2", f = "UploadFilesService.kt", i = {}, l = {228}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.service.UploadFilesService$onStartCommand$2, reason: invalid class name and case insensitive filesystem */
    static final class C04212 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SguSoundSet $sgu;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04212(SguSoundSet sguSoundSet, Continuation<? super C04212> continuation) {
            super(2, continuation);
            this.$sgu = sguSoundSet;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UploadFilesService.this.new C04212(this.$sgu, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04212) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                UploadFilesService uploadFilesService = UploadFilesService.this;
                SguSoundSet sgu = this.$sgu;
                Intrinsics.checkNotNullExpressionValue(sgu, "sgu");
                this.label = 1;
                if (uploadFilesService.loadingUploadingSguFile(sgu, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    private final void registerNotificationChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Your Channel Name", 2);
        notificationChannel.setSound(null, null);
        Object systemService = getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        ((NotificationManager) systemService).createNotificationChannel(notificationChannel);
    }

    private final void createNotification(String msg) {
        Notification notificationBuild = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID).setSmallIcon(R.drawable.ic_notification).setContentTitle("Upload Files Service").setContentText(msg).setPriority(-1).setSound(null).build();
        Intrinsics.checkNotNullExpressionValue(notificationBuild, "Builder(this, NOTIFICATI…ull)\n            .build()");
        if (Build.VERSION.SDK_INT >= 29) {
            startForeground(1, notificationBuild, 16);
        } else {
            startForeground(1, notificationBuild);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object loadingUploadingPresetFile(int r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.thor.app.service.UploadFilesService.C04181
            if (r0 == 0) goto L14
            r0 = r7
            com.thor.app.service.UploadFilesService$loadingUploadingPresetFile$1 r0 = (com.thor.app.service.UploadFilesService.C04181) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.thor.app.service.UploadFilesService$loadingUploadingPresetFile$1 r0 = new com.thor.app.service.UploadFilesService$loadingUploadingPresetFile$1
            r0.<init>(r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3a
            if (r2 != r3) goto L32
            java.lang.Object r6 = r0.L$1
            com.thor.app.service.UploadFilesService r6 = (com.thor.app.service.UploadFilesService) r6
            java.lang.Object r0 = r0.L$0
            com.thor.app.service.UploadFilesService r0 = (com.thor.app.service.UploadFilesService) r0
            kotlin.ResultKt.throwOnFailure(r7)
            goto L63
        L32:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3a:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.jvm.functions.Function1<? super com.thor.app.service.state.UploadServiceState, kotlin.Unit> r7 = r5.progressUpload
            if (r7 == 0) goto L46
            com.thor.app.service.state.UploadServiceState$Downloading r2 = com.thor.app.service.state.UploadServiceState.Downloading.INSTANCE
            r7.invoke(r2)
        L46:
            com.thor.app.service.data.LoadSoundPresetsUseCase r7 = new com.thor.app.service.data.LoadSoundPresetsUseCase
            android.content.Context r2 = r5.getBaseContext()
            java.lang.String r4 = "this@UploadFilesService.baseContext"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r4)
            r7.<init>(r2)
            r0.L$0 = r5
            r0.L$1 = r5
            r0.label = r3
            java.lang.Object r7 = r7.loadSoundPresets(r6, r0)
            if (r7 != r1) goto L61
            return r1
        L61:
            r6 = r5
            r0 = r6
        L63:
            com.thor.app.service.models.UploadFileGroupModel r7 = (com.thor.app.service.models.UploadFileGroupModel) r7
            r6.uploadingFileGroupModel = r7
            com.thor.app.service.models.UploadFileGroupModel r6 = r0.uploadingFileGroupModel
            r7 = 0
            java.lang.String r1 = "uploadingFileGroupModel"
            if (r6 != 0) goto L72
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r1)
            r6 = r7
        L72:
            java.util.List r6 = r6.getUploadListFiles()
            boolean r6 = r6.isEmpty()
            if (r6 == 0) goto L88
            com.thor.app.service.state.UploadServiceState$Stop$Error r6 = new com.thor.app.service.state.UploadServiceState$Stop$Error
            java.lang.String r2 = "Error load sound presets"
            r6.<init>(r2)
            com.thor.app.service.state.UploadServiceState$Stop r6 = (com.thor.app.service.state.UploadServiceState.Stop) r6
            r0.stop(r6)
        L88:
            com.thor.app.service.models.UploadFileGroupModel r6 = r0.uploadingFileGroupModel
            if (r6 != 0) goto L90
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r1)
            goto L91
        L90:
            r7 = r6
        L91:
            java.lang.String r6 = r7.getPckName()
            com.thor.app.service.UploadFilesService.uploadingName = r6
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.service.UploadFilesService.loadingUploadingPresetFile(int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object loadingUploadingSguFile(com.thor.networkmodule.model.responses.sgu.SguSoundSet r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.thor.app.service.UploadFilesService.C04191
            if (r0 == 0) goto L14
            r0 = r7
            com.thor.app.service.UploadFilesService$loadingUploadingSguFile$1 r0 = (com.thor.app.service.UploadFilesService.C04191) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.thor.app.service.UploadFilesService$loadingUploadingSguFile$1 r0 = new com.thor.app.service.UploadFilesService$loadingUploadingSguFile$1
            r0.<init>(r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r6 = r0.L$0
            com.thor.app.service.UploadFilesService r6 = (com.thor.app.service.UploadFilesService) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5c
        L2e:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L36:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.jvm.functions.Function1<? super com.thor.app.service.state.UploadServiceState, kotlin.Unit> r7 = r5.progressUpload
            if (r7 == 0) goto L42
            com.thor.app.service.state.UploadServiceState$Downloading r2 = com.thor.app.service.state.UploadServiceState.Downloading.INSTANCE
            r7.invoke(r2)
        L42:
            com.thor.app.service.data.LoadSoundSguUseCase r7 = new com.thor.app.service.data.LoadSoundSguUseCase
            android.content.Context r2 = r5.getBaseContext()
            java.lang.String r4 = "this@UploadFilesService.baseContext"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r4)
            r7.<init>(r2)
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r7 = r7.loadSoundSgu(r6, r0)
            if (r7 != r1) goto L5b
            return r1
        L5b:
            r6 = r5
        L5c:
            com.thor.app.service.models.UploadFileGroupModel r7 = (com.thor.app.service.models.UploadFileGroupModel) r7
            java.lang.String r0 = "Error load sound presets"
            if (r7 != 0) goto L6d
            com.thor.app.service.state.UploadServiceState$Stop$Error r7 = new com.thor.app.service.state.UploadServiceState$Stop$Error
            r7.<init>(r0)
            com.thor.app.service.state.UploadServiceState$Stop r7 = (com.thor.app.service.state.UploadServiceState.Stop) r7
            r6.stop(r7)
            goto L8e
        L6d:
            r6.startScanningAndConnectToDevice()
            r6.uploadingFileGroupModel = r7
            if (r7 != 0) goto L7a
            java.lang.String r7 = "uploadingFileGroupModel"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r7)
            r7 = 0
        L7a:
            java.util.List r7 = r7.getUploadListFiles()
            boolean r7 = r7.isEmpty()
            if (r7 == 0) goto L8e
            com.thor.app.service.state.UploadServiceState$Stop$Error r7 = new com.thor.app.service.state.UploadServiceState$Stop$Error
            r7.<init>(r0)
            com.thor.app.service.state.UploadServiceState$Stop r7 = (com.thor.app.service.state.UploadServiceState.Stop) r7
            r6.stop(r7)
        L8e:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.service.UploadFilesService.loadingUploadingSguFile(com.thor.networkmodule.model.responses.sgu.SguSoundSet, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object loadingUploadingFirmwareFile(java.lang.String r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.thor.app.service.UploadFilesService.C04171
            if (r0 == 0) goto L14
            r0 = r7
            com.thor.app.service.UploadFilesService$loadingUploadingFirmwareFile$1 r0 = (com.thor.app.service.UploadFilesService.C04171) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.thor.app.service.UploadFilesService$loadingUploadingFirmwareFile$1 r0 = new com.thor.app.service.UploadFilesService$loadingUploadingFirmwareFile$1
            r0.<init>(r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r6 = r0.L$0
            com.thor.app.service.UploadFilesService r6 = (com.thor.app.service.UploadFilesService) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5c
        L2e:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L36:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.jvm.functions.Function1<? super com.thor.app.service.state.UploadServiceState, kotlin.Unit> r7 = r5.progressUpload
            if (r7 == 0) goto L42
            com.thor.app.service.state.UploadServiceState$Downloading r2 = com.thor.app.service.state.UploadServiceState.Downloading.INSTANCE
            r7.invoke(r2)
        L42:
            com.thor.app.service.data.LoadFirmwareUseCase r7 = new com.thor.app.service.data.LoadFirmwareUseCase
            android.content.Context r2 = r5.getBaseContext()
            java.lang.String r4 = "this@UploadFilesService.baseContext"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r4)
            r7.<init>(r2)
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r7 = r7.loadFirmware(r6, r0)
            if (r7 != r1) goto L5b
            return r1
        L5b:
            r6 = r5
        L5c:
            com.thor.app.service.models.UploadFileGroupModel r7 = (com.thor.app.service.models.UploadFileGroupModel) r7
            r0 = 0
            if (r7 == 0) goto L87
            r6.uploadingFileGroupModel = r7
            if (r7 != 0) goto L6b
            java.lang.String r7 = "uploadingFileGroupModel"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r7)
            r7 = r0
        L6b:
            java.util.List r7 = r7.getUploadListFiles()
            boolean r7 = r7.isEmpty()
            if (r7 == 0) goto L82
            com.thor.app.service.state.UploadServiceState$Stop$Error r7 = new com.thor.app.service.state.UploadServiceState$Stop$Error
            java.lang.String r0 = "No files to upload in the firmware"
            r7.<init>(r0)
            com.thor.app.service.state.UploadServiceState$Stop r7 = (com.thor.app.service.state.UploadServiceState.Stop) r7
            r6.stop(r7)
            goto L85
        L82:
            r6.startScanningAndConnectToDevice()
        L85:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
        L87:
            if (r0 != 0) goto L95
            com.thor.app.service.state.UploadServiceState$Stop$Error r7 = new com.thor.app.service.state.UploadServiceState$Stop$Error
            java.lang.String r0 = "Failed to load firmware"
            r7.<init>(r0)
            com.thor.app.service.state.UploadServiceState$Stop r7 = (com.thor.app.service.state.UploadServiceState.Stop) r7
            r6.stop(r7)
        L95:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.service.UploadFilesService.loadingUploadingFirmwareFile(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startScanningAndConnectToDevice() {
        getMBleOldManager().disconnect(false);
        BluetoothCentralManager bluetoothCentralManager = this.btManager;
        if (bluetoothCentralManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btManager");
            bluetoothCentralManager = null;
        }
        bluetoothCentralManager.getPeripheral(Settings.INSTANCE.getDeviceMacAddress()).connect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void createRxTxCharacteristic(BluetoothPeripheral peripheral) {
        Unit unit;
        UUID SERVICE_UUID2 = SERVICE_UUID;
        Intrinsics.checkNotNullExpressionValue(SERVICE_UUID2, "SERVICE_UUID");
        if (peripheral.getService(SERVICE_UUID2) != null) {
            Intrinsics.checkNotNullExpressionValue(SERVICE_UUID2, "SERVICE_UUID");
            UUID CHARACTERISTIC_UUID2 = CHARACTERISTIC_UUID;
            Intrinsics.checkNotNullExpressionValue(CHARACTERISTIC_UUID2, "CHARACTERISTIC_UUID");
            BluetoothGattCharacteristic characteristic = peripheral.getCharacteristic(SERVICE_UUID2, CHARACTERISTIC_UUID2);
            this.txCharacteristic = characteristic;
            this.rxCharacteristic = characteristic;
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            UUID SERVICE_NORDIC_UUID2 = SERVICE_NORDIC_UUID;
            Intrinsics.checkNotNullExpressionValue(SERVICE_NORDIC_UUID2, "SERVICE_NORDIC_UUID");
            UUID CHARACTERISTIC_NORDIC_RX_UUID2 = CHARACTERISTIC_NORDIC_RX_UUID;
            Intrinsics.checkNotNullExpressionValue(CHARACTERISTIC_NORDIC_RX_UUID2, "CHARACTERISTIC_NORDIC_RX_UUID");
            this.rxCharacteristic = peripheral.getCharacteristic(SERVICE_NORDIC_UUID2, CHARACTERISTIC_NORDIC_RX_UUID2);
            Intrinsics.checkNotNullExpressionValue(SERVICE_NORDIC_UUID2, "SERVICE_NORDIC_UUID");
            UUID CHARACTERISTIC_NORDIC_TX_UUID2 = CHARACTERISTIC_NORDIC_TX_UUID;
            Intrinsics.checkNotNullExpressionValue(CHARACTERISTIC_NORDIC_TX_UUID2, "CHARACTERISTIC_NORDIC_TX_UUID");
            this.txCharacteristic = peripheral.getCharacteristic(SERVICE_NORDIC_UUID2, CHARACTERISTIC_NORDIC_TX_UUID2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeDataToCharacteristic(byte[] data, BaseBleRequest request) {
        try {
            BluetoothGattCharacteristic bluetoothGattCharacteristic = this.txCharacteristic;
            if (bluetoothGattCharacteristic != null) {
                BluetoothCentralManager bluetoothCentralManager = this.btManager;
                Object obj = null;
                if (bluetoothCentralManager == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("btManager");
                    bluetoothCentralManager = null;
                }
                Iterator<T> it = bluetoothCentralManager.getConnectedPeripherals().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Object next = it.next();
                    if (Intrinsics.areEqual(((BluetoothPeripheral) next).getAddress(), this.deviceAddressConnection)) {
                        obj = next;
                        break;
                    }
                }
                BluetoothPeripheral bluetoothPeripheral = (BluetoothPeripheral) obj;
                if (bluetoothPeripheral != null) {
                    BuildersKt__Builders_commonKt.launch$default(this.scope, Dispatchers.getIO(), null, new UploadFilesService$writeDataToCharacteristic$1$2$1(this, request, bluetoothPeripheral, bluetoothGattCharacteristic, data, null), 2, null);
                }
            }
        } catch (Exception e) {
            stop(new UploadServiceState.Stop.Error("Error write data " + e.getMessage()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object writeData(com.welie.blessed.BluetoothPeripheral r8, android.bluetooth.BluetoothGattCharacteristic r9, byte[] r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            r7 = this;
            boolean r0 = r11 instanceof com.thor.app.service.UploadFilesService.C04261
            if (r0 == 0) goto L14
            r0 = r11
            com.thor.app.service.UploadFilesService$writeData$1 r0 = (com.thor.app.service.UploadFilesService.C04261) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L19
        L14:
            com.thor.app.service.UploadFilesService$writeData$1 r0 = new com.thor.app.service.UploadFilesService$writeData$1
            r0.<init>(r11)
        L19:
            java.lang.Object r11 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 2
            if (r2 == 0) goto L48
            if (r2 == r3) goto L44
            if (r2 != r4) goto L3c
            java.lang.Object r8 = r0.L$2
            java.util.Iterator r8 = (java.util.Iterator) r8
            java.lang.Object r9 = r0.L$1
            android.bluetooth.BluetoothGattCharacteristic r9 = (android.bluetooth.BluetoothGattCharacteristic) r9
            java.lang.Object r10 = r0.L$0
            com.welie.blessed.BluetoothPeripheral r10 = (com.welie.blessed.BluetoothPeripheral) r10
            kotlin.ResultKt.throwOnFailure(r11)
            r6 = r10
            r10 = r9
            r9 = r6
            goto L83
        L3c:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L44:
            kotlin.ResultKt.throwOnFailure(r11)
            goto L66
        L48:
            kotlin.ResultKt.throwOnFailure(r11)
            boolean r11 = r7.isNordic
            r2 = 0
            if (r11 == 0) goto L69
            com.thor.app.utils.logs.loggers.FileLogger r11 = r7.getFileLogger()
            java.lang.String r4 = "SERVICE: Write data Nordic"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r11.i(r4, r2)
            com.welie.blessed.WriteType r11 = com.welie.blessed.WriteType.WITH_RESPONSE
            r0.label = r3
            java.lang.Object r8 = r8.writeCharacteristic(r9, r10, r11, r0)
            if (r8 != r1) goto L66
            return r1
        L66:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L69:
            com.thor.app.utils.logs.loggers.FileLogger r11 = r7.getFileLogger()
            java.lang.String r3 = "SERVICE: Write data not Nordic"
            java.lang.Object[] r5 = new java.lang.Object[r2]
            r11.i(r3, r5)
            r11 = 0
            java.util.List r10 = splitByteArray$default(r7, r10, r2, r4, r11)
            java.lang.Iterable r10 = (java.lang.Iterable) r10
            java.util.Iterator r10 = r10.iterator()
            r6 = r9
            r9 = r8
            r8 = r10
            r10 = r6
        L83:
            boolean r11 = r8.hasNext()
            if (r11 == 0) goto La0
            java.lang.Object r11 = r8.next()
            byte[] r11 = (byte[]) r11
            com.welie.blessed.WriteType r2 = com.welie.blessed.WriteType.WITHOUT_RESPONSE
            r0.L$0 = r9
            r0.L$1 = r10
            r0.L$2 = r8
            r0.label = r4
            java.lang.Object r11 = r9.writeCharacteristic(r10, r11, r2, r0)
            if (r11 != r1) goto L83
            return r1
        La0:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.service.UploadFilesService.writeData(com.welie.blessed.BluetoothPeripheral, android.bluetooth.BluetoothGattCharacteristic, byte[], kotlin.coroutines.Continuation):java.lang.Object");
    }

    static /* synthetic */ List splitByteArray$default(UploadFilesService uploadFilesService, byte[] bArr, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 20;
        }
        return uploadFilesService.splitByteArray(bArr, i);
    }

    private final List<byte[]> splitByteArray(byte[] data, int blockSize) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < data.length) {
            int i2 = i + blockSize;
            arrayList.add(ArraysKt.copyOfRange(data, i, RangesKt.coerceAtMost(i2, data.length)));
            i = i2;
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void waitResponse(BaseBleRequest data) {
        CoroutineScope coroutineScope = this.timerScope;
        if (coroutineScope != null) {
            CoroutineScopeKt.cancel$default(coroutineScope, null, 1, null);
        }
        this.isBordAnswer = false;
        CoroutineScope CoroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
        this.timerScope = CoroutineScope;
        if (CoroutineScope != null) {
            BuildersKt__Builders_commonKt.launch$default(CoroutineScope, Dispatchers.getIO(), null, new C04251(data, null), 2, null);
        }
    }

    /* compiled from: UploadFilesService.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.UploadFilesService$waitResponse$1", f = "UploadFilesService.kt", i = {}, l = {433}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.service.UploadFilesService$waitResponse$1, reason: invalid class name and case insensitive filesystem */
    static final class C04251 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ BaseBleRequest $data;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04251(BaseBleRequest baseBleRequest, Continuation<? super C04251> continuation) {
            super(2, continuation);
            this.$data = baseBleRequest;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UploadFilesService.this.new C04251(this.$data, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04251) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(6000L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            UploadFilesService.this.getFileLogger().i("SERVICE: Wait response " + this.$data.getCommand(), new Object[0]);
            UploadFilesService.this.lastWriteCommand = this.$data;
            if (!UploadFilesService.this.isBordAnswer) {
                UploadFilesService.this.tryStartInitialHandShake();
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startObservingRxCharacteristic() {
        Object next;
        FileLogger fileLogger = getFileLogger();
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.rxCharacteristic;
        fileLogger.i("SERVICE: Start observing " + (bluetoothGattCharacteristic != null ? bluetoothGattCharacteristic.getUuid() : null), new Object[0]);
        BluetoothGattCharacteristic bluetoothGattCharacteristic2 = this.rxCharacteristic;
        if (bluetoothGattCharacteristic2 != null) {
            BluetoothCentralManager bluetoothCentralManager = this.btManager;
            if (bluetoothCentralManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btManager");
                bluetoothCentralManager = null;
            }
            Iterator<T> it = bluetoothCentralManager.getConnectedPeripherals().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                } else {
                    next = it.next();
                    if (Intrinsics.areEqual(((BluetoothPeripheral) next).getAddress(), this.deviceAddressConnection)) {
                        break;
                    }
                }
            }
            BluetoothPeripheral bluetoothPeripheral = (BluetoothPeripheral) next;
            if (bluetoothPeripheral != null) {
                BuildersKt__Builders_commonKt.launch$default(this.priorityScope, null, null, new UploadFilesService$startObservingRxCharacteristic$1$1$1(bluetoothPeripheral, this, null), 3, null);
                try {
                    BuildersKt__Builders_commonKt.launch$default(this.scope, Dispatchers.getIO(), null, new UploadFilesService$startObservingRxCharacteristic$1$1$2(bluetoothPeripheral, bluetoothGattCharacteristic2, this, null), 2, null);
                } catch (Exception unused) {
                    stop(new UploadServiceState.Stop.Error("Error Connection"));
                    Unit unit = Unit.INSTANCE;
                }
            }
            tryStartInitialHandShake();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void tryStartInitialHandShake() {
        if (this.isStatusCancel) {
            return;
        }
        getFileLogger().i("SERVICE: Try start handshake " + this.initCount, new Object[0]);
        if (this.initCount > 6) {
            stop(new UploadServiceState.Stop.Error("Error Upload preset"));
            getFileLogger().i("SERVICE: Error init handshake " + this.initCount, new Object[0]);
            return;
        }
        this.priorityTimeRequest += PlaybackException.ERROR_CODE_DRM_UNSPECIFIED;
        BuildersKt__Builders_commonKt.launch$default(this.scope, Dispatchers.getIO(), null, new C04241(null), 2, null);
    }

    /* compiled from: UploadFilesService.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.UploadFilesService$tryStartInitialHandShake$1", f = "UploadFilesService.kt", i = {}, l = {575}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.service.UploadFilesService$tryStartInitialHandShake$1, reason: invalid class name and case insensitive filesystem */
    static final class C04241 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C04241(Continuation<? super C04241> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UploadFilesService.this.new C04241(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04241) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            UploadFilesService.this.initCount++;
            HardwareBleRequest hardwareBleRequest = new HardwareBleRequest();
            UploadFilesService.this.writeDataToCharacteristic(hardwareBleRequest.getBytes(true), hardwareBleRequest);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startHandShake() {
        getFileLogger().i("SERVICE: Start handshake", new Object[0]);
        this.currentIv = EncryptionHelper.INSTANCE.generateIVH();
        HandShakeBleRequest handShakeBleRequest = new HandShakeBleRequest(this.currentIv);
        writeDataToCharacteristic(handShakeBleRequest.getBytes(true), handShakeBleRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initHandShake(byte[] data) {
        FileLogger fileLogger = getFileLogger();
        BaseBleRequest baseBleRequest = this.lastWriteCommand;
        Unit unit = null;
        fileLogger.i("SERVICE: Init handshake " + BleHelperKt.toHex(BleHelper.convertShortToByteArray(baseBleRequest != null ? baseBleRequest.getCommand() : null)), new Object[0]);
        CryptoManager crypto = EncryptionHelper.INSTANCE.getCrypto();
        byte[] bArrPlus = ArraysKt.plus(this.currentIv, data);
        HardwareProfile hardwareProfile = this.hardwareProfile;
        short versionHardware = hardwareProfile != null ? hardwareProfile.getVersionHardware() : (short) 0;
        HardwareProfile hardwareProfile2 = this.hardwareProfile;
        short versionFirmware = hardwareProfile2 != null ? hardwareProfile2.getVersionFirmware() : (short) 0;
        HardwareProfile hardwareProfile3 = this.hardwareProfile;
        crypto.coreAesInit(bArrPlus, versionHardware, versionFirmware, hardwareProfile3 != null ? hardwareProfile3.getSerialNumber() : (short) 0);
        BaseBleRequest baseBleRequest2 = this.lastWriteCommand;
        if (baseBleRequest2 != null) {
            getFileLogger().i("SERVICE: Write data after handshake " + BleHelperKt.toHex(BleHelper.convertShortToByteArray(baseBleRequest2.getCommand())), new Object[0]);
            writeDataToCharacteristic(baseBleRequest2.getBytes(true), baseBleRequest2);
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            getFileLogger().i("SERVICE: get data", new Object[0]);
            deActivePreset();
        }
    }

    private final void deActivePreset() {
        ActivatePresetBleRequest activatePresetBleRequest = new ActivatePresetBleRequest((short) 0);
        writeDataToCharacteristic(activatePresetBleRequest.getBytes(true), activatePresetBleRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getStatusUploadFiles() {
        DownloadGetStatusBleRequest downloadGetStatusBleRequest = new DownloadGetStatusBleRequest();
        writeDataToCharacteristic(downloadGetStatusBleRequest.getBytes(true), downloadGetStatusBleRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void tryReUploadFiles(com.thor.businessmodule.bluetooth.model.other.DownloadGetStatusModel r7) {
        /*
            r6 = this;
            com.welie.blessed.ConnectionState r0 = r6.connectionState
            com.welie.blessed.ConnectionState r1 = com.welie.blessed.ConnectionState.CONNECTED
            if (r0 != r1) goto La0
            com.thor.businessmodule.bluetooth.model.other.IdFileModel r0 = r7.getId()
            r1 = 0
            java.lang.String r2 = "uploadingFileGroupModel"
            r3 = 1
            r4 = 0
            if (r0 == 0) goto L36
            java.lang.Short r0 = r0.getPackageId()
            if (r0 == 0) goto L36
            com.thor.app.service.models.UploadFileGroupModel r5 = r6.uploadingFileGroupModel
            if (r5 != 0) goto L1f
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            r5 = r1
        L1f:
            java.util.List r5 = r5.getUploadListFiles()
            java.lang.Object r5 = kotlin.collections.CollectionsKt.first(r5)
            com.thor.app.service.models.UploadFileModel r5 = (com.thor.app.service.models.UploadFileModel) r5
            int r5 = r5.getPackageId()
            short r5 = (short) r5
            short r0 = r0.shortValue()
            if (r5 != r0) goto L36
            r0 = r3
            goto L37
        L36:
            r0 = r4
        L37:
            if (r0 == 0) goto L9c
            com.thor.businessmodule.bluetooth.model.other.IdFileModel r0 = r7.getId()
            if (r0 == 0) goto L64
            java.lang.Short r0 = r0.getVersionId()
            if (r0 == 0) goto L64
            com.thor.app.service.models.UploadFileGroupModel r5 = r6.uploadingFileGroupModel
            if (r5 != 0) goto L4d
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            goto L4e
        L4d:
            r1 = r5
        L4e:
            java.util.List r1 = r1.getUploadListFiles()
            java.lang.Object r1 = kotlin.collections.CollectionsKt.first(r1)
            com.thor.app.service.models.UploadFileModel r1 = (com.thor.app.service.models.UploadFileModel) r1
            int r1 = r1.getVersionId()
            short r1 = (short) r1
            short r0 = r0.shortValue()
            if (r1 != r0) goto L64
            r4 = r3
        L64:
            if (r4 == 0) goto L9c
            com.thor.businessmodule.bluetooth.model.other.DownloadGetStatusModel$Status r0 = r7.getStatus()
            if (r0 != 0) goto L6e
            r0 = -1
            goto L76
        L6e:
            int[] r1 = com.thor.app.service.UploadFilesService.WhenMappings.$EnumSwitchMapping$0
            int r0 = r0.ordinal()
            r0 = r1[r0]
        L76:
            if (r0 == r3) goto L98
            r1 = 2
            if (r0 == r1) goto L94
            r1 = 3
            if (r0 == r1) goto L90
            r1 = 4
            if (r0 == r1) goto L8c
            r7 = 5
            if (r0 == r7) goto L88
            r6.uploadStartGroupFiles()
            goto Lac
        L88:
            r6.uploadStartGroupFiles()
            goto Lac
        L8c:
            r6.statusFileCommitted(r7)
            goto Lac
        L90:
            r6.statusDownloading(r7)
            goto Lac
        L94:
            r6.statusFileStarted(r7)
            goto Lac
        L98:
            r6.calculateUploadingFile()
            goto Lac
        L9c:
            r6.uploadStartGroupFiles()
            goto Lac
        La0:
            com.thor.app.service.state.UploadServiceState$Stop$Error r7 = new com.thor.app.service.state.UploadServiceState$Stop$Error
            java.lang.String r0 = "Error connection"
            r7.<init>(r0)
            com.thor.app.service.state.UploadServiceState$Stop r7 = (com.thor.app.service.state.UploadServiceState.Stop) r7
            r6.stop(r7)
        Lac:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.service.UploadFilesService.tryReUploadFiles(com.thor.businessmodule.bluetooth.model.other.DownloadGetStatusModel):void");
    }

    private final void statusFileStarted(DownloadGetStatusModel model) {
        FileType idFileType;
        IdFileModel id = model.getId();
        if (id == null || (idFileType = id.getIdFileType()) == null) {
            idFileType = FileType.SoundSamplePackageV2;
        }
        getFile(idFileType);
        Short errorCode = model.getErrorCode();
        boolean z = false;
        if (errorCode != null && errorCode.shortValue() == 0) {
            z = true;
        }
        if (z) {
            UploadFileGroupModel uploadFileGroupModel = this.uploadingFileGroupModel;
            Unit unit = null;
            if (uploadFileGroupModel == null) {
                Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
                uploadFileGroupModel = null;
            }
            UploadFileModel uploadFileModel = (UploadFileModel) CollectionsKt.removeFirst(uploadFileGroupModel.getUploadListFiles());
            this.currentUploadFile = uploadFileModel;
            if (uploadFileModel != null) {
                List<byte[]> mutableList = CollectionsKt.toMutableList((Collection) WriteBlockFileBleRequestNew.INSTANCE.takeDataBlock(uploadFileModel.getFile()));
                this.uploadingFile = mutableList;
                this.totalBlockNeedUpload = mutableList.size();
                uploadWriteBlockFile();
                unit = Unit.INSTANCE;
            }
            if (unit == null) {
                stop(new UploadServiceState.Stop.Error("Error upload file"));
                return;
            }
            return;
        }
        calculateUploadingFile();
    }

    private final void statusDownloading(DownloadGetStatusModel model) {
        FileType idFileType;
        IdFileModel id = model.getId();
        if (id == null || (idFileType = id.getIdFileType()) == null) {
            idFileType = FileType.SoundSamplePackageV2;
        }
        getFile(idFileType);
        Short errorCode = model.getErrorCode();
        boolean z = false;
        if (errorCode != null && errorCode.shortValue() == 0) {
            z = true;
        }
        if (z) {
            UploadFileGroupModel uploadFileGroupModel = this.uploadingFileGroupModel;
            Unit unit = null;
            if (uploadFileGroupModel == null) {
                Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
                uploadFileGroupModel = null;
            }
            UploadFileModel uploadFileModel = (UploadFileModel) CollectionsKt.removeFirst(uploadFileGroupModel.getUploadListFiles());
            this.currentUploadFile = uploadFileModel;
            if (uploadFileModel != null) {
                List<byte[]> mutableList = CollectionsKt.toMutableList((Collection) WriteBlockFileBleRequestNew.INSTANCE.takeDataBlock(uploadFileModel.getFile()));
                this.uploadingFile = mutableList;
                this.totalBlockNeedUpload = mutableList.size();
                this.uploadingFile = this.uploadingFile.subList(model.getCount() / Variables.INSTANCE.getBLOCK_SIZE(), this.uploadingFile.size());
                uploadWriteBlockFile();
                unit = Unit.INSTANCE;
            }
            if (unit == null) {
                stop(new UploadServiceState.Stop.Error("Error upload file"));
                return;
            }
            return;
        }
        calculateUploadingFile();
    }

    private final void statusFileCommitted(DownloadGetStatusModel model) {
        FileType idFileType;
        IdFileModel id = model.getId();
        if (id == null || (idFileType = id.getIdFileType()) == null) {
            idFileType = FileType.SoundSamplePackageV2;
        }
        getFile(idFileType);
        UploadFileGroupModel uploadFileGroupModel = this.uploadingFileGroupModel;
        if (uploadFileGroupModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
            uploadFileGroupModel = null;
        }
        this.currentUploadFile = (UploadFileModel) CollectionsKt.removeFirst(uploadFileGroupModel.getUploadListFiles());
        uploadCommitFile();
    }

    private final void getFile(FileType fileType) {
        Object next;
        Object next2;
        Object next3;
        int i = WhenMappings.$EnumSwitchMapping$1[fileType.ordinal()];
        UploadFileGroupModel uploadFileGroupModel = null;
        if (i == 1) {
            UploadFileGroupModel uploadFileGroupModel2 = this.uploadingFileGroupModel;
            if (uploadFileGroupModel2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
                uploadFileGroupModel2 = null;
            }
            Iterator<T> it = uploadFileGroupModel2.getUploadListFiles().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                } else {
                    next = it.next();
                    if (((UploadFileModel) next).getFileType() == FileType.SoundRulesPackageV2) {
                        break;
                    }
                }
            }
            UploadFileModel uploadFileModel = (UploadFileModel) next;
            if (uploadFileModel != null) {
                UploadFileGroupModel uploadFileGroupModel3 = this.uploadingFileGroupModel;
                if (uploadFileGroupModel3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
                } else {
                    uploadFileGroupModel = uploadFileGroupModel3;
                }
                uploadFileGroupModel.getUploadListFiles().remove(uploadFileModel);
                return;
            }
            return;
        }
        if (i != 2) {
            return;
        }
        UploadFileGroupModel uploadFileGroupModel4 = this.uploadingFileGroupModel;
        if (uploadFileGroupModel4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
            uploadFileGroupModel4 = null;
        }
        Iterator<T> it2 = uploadFileGroupModel4.getUploadListFiles().iterator();
        while (true) {
            if (!it2.hasNext()) {
                next2 = null;
                break;
            } else {
                next2 = it2.next();
                if (((UploadFileModel) next2).getFileType() == FileType.SoundSamplePackageV2) {
                    break;
                }
            }
        }
        UploadFileModel uploadFileModel2 = (UploadFileModel) next2;
        if (uploadFileModel2 != null) {
            UploadFileGroupModel uploadFileGroupModel5 = this.uploadingFileGroupModel;
            if (uploadFileGroupModel5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
                uploadFileGroupModel5 = null;
            }
            uploadFileGroupModel5.getUploadListFiles().remove(uploadFileModel2);
        }
        UploadFileGroupModel uploadFileGroupModel6 = this.uploadingFileGroupModel;
        if (uploadFileGroupModel6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
            uploadFileGroupModel6 = null;
        }
        Iterator<T> it3 = uploadFileGroupModel6.getUploadListFiles().iterator();
        while (true) {
            if (!it3.hasNext()) {
                next3 = null;
                break;
            } else {
                next3 = it3.next();
                if (((UploadFileModel) next3).getFileType() == FileType.SoundRulesPackageV2) {
                    break;
                }
            }
        }
        UploadFileModel uploadFileModel3 = (UploadFileModel) next3;
        if (uploadFileModel3 != null) {
            UploadFileGroupModel uploadFileGroupModel7 = this.uploadingFileGroupModel;
            if (uploadFileGroupModel7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
            } else {
                uploadFileGroupModel = uploadFileGroupModel7;
            }
            uploadFileGroupModel.getUploadListFiles().remove(uploadFileModel3);
        }
    }

    private final void uploadStartGroupFiles() {
        getFileLogger().i("SERVICE: Start group files", new Object[0]);
        UploadFileGroupModel uploadFileGroupModel = this.uploadingFileGroupModel;
        UploadFileGroupModel uploadFileGroupModel2 = null;
        if (uploadFileGroupModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
            uploadFileGroupModel = null;
        }
        DownloadStartGroupBleRequest downloadStartGroupBleRequest = new DownloadStartGroupBleRequest((short) uploadFileGroupModel.getUploadListFiles().size());
        Context baseContext = getBaseContext();
        Object[] objArr = new Object[1];
        UploadFileGroupModel uploadFileGroupModel3 = this.uploadingFileGroupModel;
        if (uploadFileGroupModel3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
        } else {
            uploadFileGroupModel2 = uploadFileGroupModel3;
        }
        objArr[0] = uploadFileGroupModel2.getPckName();
        String string = baseContext.getString(R.string.service_uploading, objArr);
        Intrinsics.checkNotNullExpressionValue(string, "this.baseContext.getStri…del.pckName\n            )");
        createNotification(string);
        writeDataToCharacteristic(downloadStartGroupBleRequest.getBytes(true), downloadStartGroupBleRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void calculateUploadingFile() {
        UploadFileGroupModel uploadFileGroupModel = this.uploadingFileGroupModel;
        Unit unit = null;
        if (uploadFileGroupModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
            uploadFileGroupModel = null;
        }
        UploadFileModel uploadFileModel = (UploadFileModel) CollectionsKt.removeFirst(uploadFileGroupModel.getUploadListFiles());
        this.currentUploadFile = uploadFileModel;
        if (uploadFileModel != null) {
            List<byte[]> mutableList = CollectionsKt.toMutableList((Collection) WriteBlockFileBleRequestNew.INSTANCE.takeDataBlock(uploadFileModel.getFile()));
            this.uploadingFile = mutableList;
            this.totalBlockNeedUpload = mutableList.size();
            uploadStartFile();
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            stop(new UploadServiceState.Stop.Error("Error upload file"));
        }
    }

    private final void uploadStartFile() {
        Unit unit;
        UploadFileModel uploadFileModel = this.currentUploadFile;
        if (uploadFileModel != null) {
            UploadFileModel uploadFileModel2 = this.currentUploadFile;
            short packageId = uploadFileModel2 != null ? (short) uploadFileModel2.getPackageId() : (short) 0;
            UploadFileModel uploadFileModel3 = this.currentUploadFile;
            UploadStartFileBleRequest uploadStartFileBleRequest = new UploadStartFileBleRequest(packageId, uploadFileModel3 != null ? (short) uploadFileModel3.getVersionId() : (short) 0, uploadFileModel.getFile(), uploadFileModel.getFileType());
            writeDataToCharacteristic(uploadStartFileBleRequest.getBytes(true), uploadStartFileBleRequest);
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            stop(new UploadServiceState.Stop.Error("Error upload file"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void uploadWriteBlockFile() {
        calculateProgress(this.uploadingFile.size());
        BleParseRequest bleParseRequest = new BleParseRequest(BleCommands.COMMAND_DOWNLOAD_WRITE_BLOCK, (byte[]) CollectionsKt.removeFirst(this.uploadingFile));
        writeDataToCharacteristic(bleParseRequest.getBytes(true), bleParseRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void uploadCommitFile() {
        DownloadCommitFileBleRequest downloadCommitFileBleRequest = new DownloadCommitFileBleRequest();
        writeDataToCharacteristic(downloadCommitFileBleRequest.getBytes(true), downloadCommitFileBleRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void uploadCommitGroupFiles() {
        ApplyUploadSoundPackageBleRequestNew applyUploadSoundPackageBleRequestNew = new ApplyUploadSoundPackageBleRequestNew();
        writeDataToCharacteristic(applyUploadSoundPackageBleRequestNew.getBytes(true), applyUploadSoundPackageBleRequestNew);
    }

    private final void calculateProgress(int blockLeftUpload) {
        UploadServiceState uploadServiceState;
        double dCeil = Math.ceil((100 / this.totalBlockNeedUpload) * (r2 - blockLeftUpload));
        UploadFileModel uploadFileModel = this.currentUploadFile;
        UploadFileGroupModel uploadFileGroupModel = null;
        FileType fileType = uploadFileModel != null ? uploadFileModel.getFileType() : null;
        int i = fileType == null ? -1 : WhenMappings.$EnumSwitchMapping$1[fileType.ordinal()];
        if (i == 1) {
            uploadServiceState = (UploadServiceState.ProgressUploading) new UploadServiceState.ProgressUploading.UploadingSoundV2.UploadRules((int) dCeil);
        } else if (i == 2) {
            uploadServiceState = (UploadServiceState.ProgressUploading) new UploadServiceState.ProgressUploading.UploadingSoundV2.UploadRulesModel((int) dCeil);
        } else if (i == 3) {
            uploadServiceState = (UploadServiceState.ProgressUploading) new UploadServiceState.ProgressUploading.UploadingSoundV2.UploadSample((int) dCeil);
        } else if (i == 4) {
            int i2 = (int) dCeil;
            UploadFileGroupModel uploadFileGroupModel2 = this.uploadingFileGroupModel;
            if (uploadFileGroupModel2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
            } else {
                uploadFileGroupModel = uploadFileGroupModel2;
            }
            uploadServiceState = (UploadServiceState.ProgressUploading) new UploadServiceState.ProgressUploading.UploadingSgu(i2, uploadFileGroupModel.getUploadListFiles().size());
        } else if (i == 5) {
            uploadServiceState = (UploadServiceState.ProgressUploading) new UploadServiceState.ProgressUploading.UploadingFirmware((int) dCeil);
        } else {
            uploadServiceState = (UploadServiceState.ProgressUploading) new UploadServiceState.ProgressUploading.UploadingSoundV3((int) dCeil);
        }
        Function1<? super UploadServiceState, Unit> function1 = this.progressUpload;
        if (function1 != null) {
            function1.invoke(uploadServiceState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startPolingStatus() throws InterruptedException {
        Function1<? super UploadServiceState, Unit> function1 = this.progressUpload;
        if (function1 != null) {
            function1.invoke(UploadServiceState.Reconnect.INSTANCE);
        }
        getFileLogger().i("SERVICE: Start poling status", new Object[0]);
        CoroutineScope coroutineScope = this.timerScope;
        if (coroutineScope != null) {
            CoroutineScopeKt.cancel$default(coroutineScope, null, 1, null);
        }
        Context baseContext = getBaseContext();
        Object[] objArr = new Object[1];
        UploadFileGroupModel uploadFileGroupModel = this.uploadingFileGroupModel;
        if (uploadFileGroupModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
            uploadFileGroupModel = null;
        }
        objArr[0] = uploadFileGroupModel.getPckName();
        String string = baseContext.getString(R.string.service_application, objArr);
        Intrinsics.checkNotNullExpressionValue(string, "this.baseContext.getStri…del.pckName\n            )");
        createNotification(string);
        UploadFileGroupModel uploadFileGroupModel2 = this.uploadingFileGroupModel;
        if (uploadFileGroupModel2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
            uploadFileGroupModel2 = null;
        }
        if (uploadFileGroupModel2.getTypeGroupFile() != UploadFileGroupModel.TypeGroupFile.FIRMWARE) {
            BuildersKt__BuildersKt.runBlocking$default(null, new C04221(null), 1, null);
        } else {
            holderPollingSuccess();
        }
    }

    /* compiled from: UploadFilesService.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.UploadFilesService$startPolingStatus$1", f = "UploadFilesService.kt", i = {}, l = {861}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.service.UploadFilesService$startPolingStatus$1, reason: invalid class name and case insensitive filesystem */
    static final class C04221 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C04221(Continuation<? super C04221> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UploadFilesService.this.new C04221(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04221) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(RtspMediaSource.DEFAULT_TIMEOUT_MS, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            UploadFilesService.this.holderPollingSuccess();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void holderPollingSuccess() {
        getFileLogger().i("holderPollingSuccess", new Object[0]);
        UploadFileGroupModel uploadFileGroupModel = this.uploadingFileGroupModel;
        if (uploadFileGroupModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
            uploadFileGroupModel = null;
        }
        int i = WhenMappings.$EnumSwitchMapping$2[uploadFileGroupModel.getTypeGroupFile().ordinal()];
        if (i == 1) {
            getFileLogger().i("SERVICE: Sound uploaded", new Object[0]);
            InstalledPresets installPresets = Settings.INSTANCE.getInstallPresets();
            if (installPresets == null) {
                installPresets = new InstalledPresets((short) 0, (short) 0, null, 7, null);
            }
            controlInstallPresets(installPresets);
            return;
        }
        if (i == 2) {
            getFileLogger().i("SERVICE: Sgu uploaded", new Object[0]);
            stop(UploadServiceState.Stop.Success.INSTANCE);
        } else {
            if (i != 3) {
                return;
            }
            getFileLogger().i("SERVICE: Firmware uploaded", new Object[0]);
            Settings.INSTANCE.setUpdatedFirmware(true);
            stop(UploadServiceState.Stop.Success.INSTANCE);
        }
    }

    private final void loadInstallPresets() {
        BaseBleRequest baseBleRequest = new BaseBleRequest((short) 49);
        writeDataToCharacteristic(baseBleRequest.getBytes(true), baseBleRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void controlInstallPresets(InstalledPresets installPreset) {
        UploadFileGroupModel uploadFileGroupModel;
        Object next;
        UploadFileModel uploadFileModel = this.currentUploadFile;
        InstalledPreset installedPreset = new InstalledPreset(uploadFileModel != null ? (short) uploadFileModel.getPackageId() : (short) 0, (short) 3, (short) 0, 4, null);
        Iterator<T> it = installPreset.getPresets().iterator();
        while (true) {
            uploadFileGroupModel = null;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            InstalledPreset installedPreset2 = (InstalledPreset) next;
            if (installedPreset2.getPackageId() == installedPreset.getPackageId() && installedPreset2.getMode() == installedPreset.getMode()) {
                break;
            }
        }
        boolean z = next != null;
        UploadFileGroupModel uploadFileGroupModel2 = this.uploadingFileGroupModel;
        if (uploadFileGroupModel2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
        } else {
            uploadFileGroupModel = uploadFileGroupModel2;
        }
        if (uploadFileGroupModel.getTypeGroupFile() == UploadFileGroupModel.TypeGroupFile.SOUND) {
            Completable completableUpdateDamagePckEndReturnId = getDatabase().installedSoundPackageDao().updateDamagePckEndReturnId(installedPreset.getPackageId());
            Action action = new Action() { // from class: com.thor.app.service.UploadFilesService$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Action
                public final void run() {
                    UploadFilesService.controlInstallPresets$lambda$30();
                }
            };
            final AnonymousClass2 anonymousClass2 = new Function1<Throwable, Unit>() { // from class: com.thor.app.service.UploadFilesService.controlInstallPresets.2
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable th) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }
            };
            completableUpdateDamagePckEndReturnId.subscribe(action, new Consumer() { // from class: com.thor.app.service.UploadFilesService$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    UploadFilesService.controlInstallPresets$lambda$31(anonymousClass2, obj);
                }
            });
        }
        if (!z) {
            installPreset.getPresets().add(installedPreset);
            installPreset.setAmount((short) installPreset.getPresets().size());
            installPreset.setCurrentPresetId(getMBleOldManager().getMActivatedPresetIndex());
            WriteInstalledPresetsBleRequest writeInstalledPresetsBleRequest = new WriteInstalledPresetsBleRequest(installPreset);
            writeDataToCharacteristic(writeInstalledPresetsBleRequest.getBytes(true), writeInstalledPresetsBleRequest);
            return;
        }
        ActivatePresetBleRequest activatePresetBleRequest = new ActivatePresetBleRequest(getMBleOldManager().getMActivatedPresetIndex());
        writeDataToCharacteristic(activatePresetBleRequest.getBytes(true), activatePresetBleRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void controlInstallPresets$lambda$31(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void stop(UploadServiceState.Stop reason) {
        try {
            getFileLogger().i("SERVICE: Stop service " + reason, new Object[0]);
            BluetoothCentralManager bluetoothCentralManager = null;
            if (this.connectionState == ConnectionState.CONNECTED) {
                BuildersKt__Builders_commonKt.launch$default(this.scope, Dispatchers.getIO(), null, new C04231(reason, null), 2, null);
                return;
            }
            getFileLogger().i("connectionState != ConnectionState.CONNECTED", new Object[0]);
            BluetoothCentralManager bluetoothCentralManager2 = this.btManager;
            if (bluetoothCentralManager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btManager");
            } else {
                bluetoothCentralManager = bluetoothCentralManager2;
            }
            bluetoothCentralManager.close();
            clearResources();
            Function1<? super UploadServiceState, Unit> function1 = this.progressUpload;
            if (function1 != null) {
                function1.invoke(reason);
            }
            stopSelf();
        } catch (Exception e) {
            getFileLogger().i("SERVICE: Error stop " + e.getMessage(), new Object[0]);
            stopSelf();
        }
    }

    /* compiled from: UploadFilesService.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.UploadFilesService$stop$1", f = "UploadFilesService.kt", i = {}, l = {946}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.service.UploadFilesService$stop$1, reason: invalid class name and case insensitive filesystem */
    static final class C04231 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ UploadServiceState.Stop $reason;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04231(UploadServiceState.Stop stop, Continuation<? super C04231> continuation) {
            super(2, continuation);
            this.$reason = stop;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UploadFilesService.this.new C04231(this.$reason, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04231) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v0, types: [int] */
        /* JADX WARN: Type inference failed for: r1v10 */
        /* JADX WARN: Type inference failed for: r1v11 */
        /* JADX WARN: Type inference failed for: r1v7, types: [com.thor.app.service.UploadFilesService] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object next;
            UploadServiceState.Stop stop;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            UploadFilesService uploadFilesService = this.label;
            BluetoothCentralManager bluetoothCentralManager = null;
            try {
            } catch (Exception e) {
                uploadFilesService.getFileLogger().i("SERVICE: Error stop " + e.getMessage(), new Object[0]);
            }
            if (uploadFilesService == 0) {
                ResultKt.throwOnFailure(obj);
                UploadFilesService.this.getFileLogger().i("else", new Object[0]);
                BluetoothCentralManager bluetoothCentralManager2 = UploadFilesService.this.btManager;
                if (bluetoothCentralManager2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("btManager");
                    bluetoothCentralManager2 = null;
                }
                List<BluetoothPeripheral> connectedPeripherals = bluetoothCentralManager2.getConnectedPeripherals();
                UploadFilesService uploadFilesService2 = UploadFilesService.this;
                Iterator<T> it = connectedPeripherals.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    if (Intrinsics.areEqual(((BluetoothPeripheral) next).getAddress(), uploadFilesService2.deviceAddressConnection)) {
                        break;
                    }
                }
                BluetoothPeripheral bluetoothPeripheral = (BluetoothPeripheral) next;
                if (bluetoothPeripheral != null) {
                    UploadFilesService uploadFilesService3 = UploadFilesService.this;
                    UploadServiceState.Stop stop2 = this.$reason;
                    BluetoothCentralManager bluetoothCentralManager3 = uploadFilesService3.btManager;
                    if (bluetoothCentralManager3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("btManager");
                        bluetoothCentralManager3 = null;
                    }
                    this.L$0 = uploadFilesService3;
                    this.L$1 = stop2;
                    this.label = 1;
                    if (bluetoothCentralManager3.cancelConnection(bluetoothPeripheral, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    stop = stop2;
                    uploadFilesService = uploadFilesService3;
                }
                return Unit.INSTANCE;
            }
            if (uploadFilesService != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            stop = (UploadServiceState.Stop) this.L$1;
            UploadFilesService uploadFilesService4 = (UploadFilesService) this.L$0;
            ResultKt.throwOnFailure(obj);
            uploadFilesService = uploadFilesService4;
            BluetoothCentralManager bluetoothCentralManager4 = ((UploadFilesService) uploadFilesService).btManager;
            if (bluetoothCentralManager4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btManager");
            } else {
                bluetoothCentralManager = bluetoothCentralManager4;
            }
            bluetoothCentralManager.close();
            ((UploadFilesService) uploadFilesService).closeResponse = stop;
            uploadFilesService.getFileLogger().i("SERVICE: Stop service Done " + stop, new Object[0]);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clearResources() {
        UploadFileGroupModel uploadFileGroupModel = null;
        CoroutineScopeKt.cancel$default(this.scope, null, 1, null);
        CoroutineScope coroutineScope = this.timerScope;
        if (coroutineScope != null) {
            CoroutineScopeKt.cancel$default(coroutineScope, null, 1, null);
        }
        CoroutineScopeKt.cancel$default(this.priorityScope, null, 1, null);
        try {
            UploadFileGroupModel uploadFileGroupModel2 = this.uploadingFileGroupModel;
            if (uploadFileGroupModel2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("uploadingFileGroupModel");
            } else {
                uploadFileGroupModel = uploadFileGroupModel2;
            }
            if (uploadFileGroupModel.getTypeGroupFile() != UploadFileGroupModel.TypeGroupFile.FIRMWARE) {
                getMBleOldManager().clear();
                getMBleOldManager().connect();
            }
        } catch (Exception unused) {
            getMBleOldManager().connect();
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        CoroutineScopeKt.cancel$default(this.forceStop, null, 1, null);
        serviceIsWorked = false;
        getFileLogger().i("SERVICE: Destroy service", new Object[0]);
    }

    /* compiled from: UploadFilesService.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.service.UploadFilesService$forceStop$1", f = "UploadFilesService.kt", i = {}, l = {988, 993}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.service.UploadFilesService$forceStop$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ long $time;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(long j, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$time = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UploadFilesService.this.new AnonymousClass1(this.$time, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:34:0x00a9  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00ad  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x00c5  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r10) {
            /*
                r9 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r9.label
                r2 = 0
                r3 = 2
                r4 = 1
                java.lang.String r5 = "btManager"
                r6 = 0
                if (r1 == 0) goto L27
                if (r1 == r4) goto L23
                if (r1 != r3) goto L1b
                java.lang.Object r0 = r9.L$0
                com.thor.app.service.UploadFilesService r0 = (com.thor.app.service.UploadFilesService) r0
                kotlin.ResultKt.throwOnFailure(r10)
                goto La3
            L1b:
                java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r10.<init>(r0)
                throw r10
            L23:
                kotlin.ResultKt.throwOnFailure(r10)
                goto L45
            L27:
                kotlin.ResultKt.throwOnFailure(r10)
                com.thor.app.service.UploadFilesService r10 = com.thor.app.service.UploadFilesService.this
                com.thor.app.utils.logs.loggers.FileLogger r10 = com.thor.app.service.UploadFilesService.access$getFileLogger(r10)
                java.lang.String r1 = "SERVICE: force stop start"
                java.lang.Object[] r7 = new java.lang.Object[r2]
                r10.i(r1, r7)
                long r7 = r9.$time
                r10 = r9
                kotlin.coroutines.Continuation r10 = (kotlin.coroutines.Continuation) r10
                r9.label = r4
                java.lang.Object r10 = kotlinx.coroutines.DelayKt.delay(r7, r10)
                if (r10 != r0) goto L45
                return r0
            L45:
                com.thor.app.service.UploadFilesService r10 = com.thor.app.service.UploadFilesService.this
                com.thor.app.utils.logs.loggers.FileLogger r10 = com.thor.app.service.UploadFilesService.access$getFileLogger(r10)
                java.lang.String r1 = "SERVICE: force stop"
                java.lang.Object[] r2 = new java.lang.Object[r2]
                r10.i(r1, r2)
                com.thor.app.service.UploadFilesService r10 = com.thor.app.service.UploadFilesService.this
                com.welie.blessed.BluetoothCentralManager r10 = com.thor.app.service.UploadFilesService.access$getBtManager$p(r10)
                if (r10 != 0) goto L5e
                kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r5)
                r10 = r6
            L5e:
                java.util.List r10 = r10.getConnectedPeripherals()
                java.lang.Iterable r10 = (java.lang.Iterable) r10
                com.thor.app.service.UploadFilesService r1 = com.thor.app.service.UploadFilesService.this
                java.util.Iterator r10 = r10.iterator()
            L6a:
                boolean r2 = r10.hasNext()
                if (r2 == 0) goto L86
                java.lang.Object r2 = r10.next()
                r4 = r2
                com.welie.blessed.BluetoothPeripheral r4 = (com.welie.blessed.BluetoothPeripheral) r4
                java.lang.String r4 = r4.getAddress()
                java.lang.String r7 = com.thor.app.service.UploadFilesService.access$getDeviceAddressConnection$p(r1)
                boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r7)
                if (r4 == 0) goto L6a
                goto L87
            L86:
                r2 = r6
            L87:
                com.welie.blessed.BluetoothPeripheral r2 = (com.welie.blessed.BluetoothPeripheral) r2
                if (r2 == 0) goto Lb8
                com.thor.app.service.UploadFilesService r10 = com.thor.app.service.UploadFilesService.this
                com.welie.blessed.BluetoothCentralManager r1 = com.thor.app.service.UploadFilesService.access$getBtManager$p(r10)
                if (r1 != 0) goto L97
                kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r5)
                r1 = r6
            L97:
                r9.L$0 = r10
                r9.label = r3
                java.lang.Object r1 = r1.cancelConnection(r2, r9)
                if (r1 != r0) goto La2
                return r0
            La2:
                r0 = r10
            La3:
                com.welie.blessed.BluetoothCentralManager r10 = com.thor.app.service.UploadFilesService.access$getBtManager$p(r0)
                if (r10 != 0) goto Lad
                kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r5)
                goto Lae
            Lad:
                r6 = r10
            Lae:
                r6.close()
                com.thor.app.managers.BleManager r10 = com.thor.app.service.UploadFilesService.access$getMBleOldManager(r0)
                r10.connect()
            Lb8:
                com.thor.app.service.UploadFilesService r10 = com.thor.app.service.UploadFilesService.this
                com.thor.app.service.UploadFilesService.access$clearResources(r10)
                com.thor.app.service.UploadFilesService r10 = com.thor.app.service.UploadFilesService.this
                kotlin.jvm.functions.Function1 r10 = r10.getProgressUpload()
                if (r10 == 0) goto Lca
                com.thor.app.service.state.UploadServiceState$Stop$ForceStop r0 = com.thor.app.service.state.UploadServiceState.Stop.ForceStop.INSTANCE
                r10.invoke(r0)
            Lca:
                com.thor.app.service.UploadFilesService r10 = com.thor.app.service.UploadFilesService.this
                r10.stopSelf()
                kotlin.Unit r10 = kotlin.Unit.INSTANCE
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: com.thor.app.service.UploadFilesService.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void forceStop(long time) {
        BuildersKt__Builders_commonKt.launch$default(this.forceStop, null, null, new AnonymousClass1(time, null), 3, null);
    }

    /* compiled from: UploadFilesService.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/thor/app/service/UploadFilesService$UploadServiceBinder;", "Landroid/os/Binder;", "(Lcom/thor/app/service/UploadFilesService;)V", "getService", "Lcom/thor/app/service/UploadFilesService;", "stopService", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class UploadServiceBinder extends Binder {
        public UploadServiceBinder() {
        }

        /* renamed from: getService, reason: from getter */
        public final UploadFilesService getThis$0() {
            return UploadFilesService.this;
        }

        public final void stopService() {
            UploadFilesService.this.isStatusCancel = true;
            UploadFilesService.this.forceStop(1000L);
        }
    }

    /* compiled from: UploadFilesService.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u001b\u001a\u00020\u0014J\b\u0010\u001c\u001a\u0004\u0018\u00010\u0016J\u0006\u0010\u001d\u001a\u00020\u0014J\u0006\u0010\u001e\u001a\u00020\u0019J\u0006\u0010\u001f\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0012\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/thor/app/service/UploadFilesService$Companion;", "", "()V", "ACTION_START_FW", "", "ACTION_START_PRESET", "ACTION_START_SGU", "CHARACTERISTIC_NORDIC_RX_UUID", "Ljava/util/UUID;", "kotlin.jvm.PlatformType", "CHARACTERISTIC_NORDIC_TX_UUID", "CHARACTERISTIC_UUID", "DEVICE_CONNECTION_ADDRESS_KEY", "DEVICE_CONNECTION_FIRMWARE_KEY", "DEVICE_CONNECTION_PACKAGE_ID_KEY", "DEVICE_CONNECTION_SGU_SOUND_SET_KEY", "NOTIFICATION_CHANNEL_ID", "SERVICE_NORDIC_UUID", "SERVICE_UUID", "disconnectedStatus", "", "loadSguModel", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "serviceIsWorked", "typeFile", "Lcom/thor/app/service/models/UploadFileGroupModel$TypeGroupFile;", "uploadingName", "getDisconnectedStatus", "getLoadSguModel", "getServiceStatus", "getUploadingFileType", "getUploadingName", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean getServiceStatus() {
            return UploadFilesService.serviceIsWorked;
        }

        public final boolean getDisconnectedStatus() {
            return UploadFilesService.disconnectedStatus;
        }

        public final String getUploadingName() {
            return UploadFilesService.uploadingName;
        }

        public final UploadFileGroupModel.TypeGroupFile getUploadingFileType() {
            return UploadFilesService.typeFile;
        }

        public final SguSoundSet getLoadSguModel() {
            return UploadFilesService.loadSguModel;
        }
    }
}
