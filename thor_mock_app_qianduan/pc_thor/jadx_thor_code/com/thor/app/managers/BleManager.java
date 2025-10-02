package com.thor.app.managers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import androidx.databinding.ObservableField;
import com.carsystems.thor.app.R;
import com.google.android.exoplayer2.C;
import com.google.android.gms.common.util.Hex;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import com.thor.app.ThorApplication;
import com.thor.app.bus.events.BluetoothDeviceConnectionChangedEvent;
import com.thor.app.bus.events.BluetoothDeviceRssiEvent;
import com.thor.app.bus.events.BluetoothScanResultEvent;
import com.thor.app.bus.events.CryptoInitErrorEvent;
import com.thor.app.bus.events.FormatFlashExecuteEvent;
import com.thor.app.bus.events.StartCheckEmergencySituationsEvent;
import com.thor.app.bus.events.bluetooth.StopScanBluetoothDevicesEvent;
import com.thor.app.bus.events.bluetooth.UpdateCanConfigurationsErrorEvent;
import com.thor.app.bus.events.bluetooth.UpdateCanConfigurationsSuccessfulEvent;
import com.thor.app.bus.events.bluetooth.firmware.FirmwareFileCommittedEvent;
import com.thor.app.bus.events.bluetooth.firmware.UpdateFirmwareErrorEvent;
import com.thor.app.bus.events.bluetooth.firmware.UpdateFirmwareProgressEvent;
import com.thor.app.bus.events.bluetooth.firmware.UpdateFirmwareSuccessfulEvent;
import com.thor.app.bus.events.bluetooth.sgu.UploadSguSoundAppliedEvent;
import com.thor.app.bus.events.bluetooth.sgu.UploadSguSoundErrorEvent;
import com.thor.app.bus.events.bluetooth.sgu.UploadSguSoundProgressEvent;
import com.thor.app.bus.events.bluetooth.sgu.UploadSguSoundSuccessfulEvent;
import com.thor.app.bus.events.mainpreset.DoneWritePreset;
import com.thor.app.bus.events.mainpreset.FailAddPreset;
import com.thor.app.bus.events.mainpreset.OnCancelDownloadPresetEvent;
import com.thor.app.bus.events.mainpreset.OnDamagePckEvent;
import com.thor.app.bus.events.mainpreset.WriteInstalledPresetsSuccessfulEvent;
import com.thor.app.bus.events.shop.main.DeletedSoundPackageEvent;
import com.thor.app.bus.events.shop.main.OnApplySoundPackageSendEvent;
import com.thor.app.bus.events.shop.main.SendUploadChangeUiEvent;
import com.thor.app.bus.events.shop.main.StartUploadRulesPackageEvent;
import com.thor.app.bus.events.shop.main.UploadSoundPackageApply;
import com.thor.app.bus.events.shop.main.UploadSoundPackageErrorEvent;
import com.thor.app.bus.events.shop.main.UploadSoundPackageProgressEvent;
import com.thor.app.bus.events.shop.main.UploadSoundPackageRuleSuccessfulEvent;
import com.thor.app.bus.events.shop.main.UploadSoundPackageSamplesSuccessfulEvent;
import com.thor.app.bus.events.shop.main.UploadSoundPackageSamplesSuccessfulEventNew;
import com.thor.app.bus.events.shop.main.UploadSoundSampleRuleSuccessfulEvent;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.BleManager$mGattCallback$2;
import com.thor.app.managers.BleManager$mLeScanAllThorDevicesCallback$2;
import com.thor.app.managers.BleManager$mLeScanCallback$2;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.entity.InstalledSoundPackageEntity;
import com.thor.app.service.UploadFilesService;
import com.thor.app.settings.Settings;
import com.thor.app.utils.TimeHelper;
import com.thor.app.utils.extensions.ContextKt;
import com.thor.app.utils.extensions.CorotineScopeKt;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.businessmodule.bluetooth.model.other.DeviceSettings;
import com.thor.businessmodule.bluetooth.model.other.DownloadGetStatusModel;
import com.thor.businessmodule.bluetooth.model.other.FileType;
import com.thor.businessmodule.bluetooth.model.other.HardwareProfile;
import com.thor.businessmodule.bluetooth.model.other.InstalledPreset;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresetRules;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresets;
import com.thor.businessmodule.bluetooth.model.other.InstalledSoundPackage;
import com.thor.businessmodule.bluetooth.model.other.PresetRule;
import com.thor.businessmodule.bluetooth.model.other.settings.DeviceId;
import com.thor.businessmodule.bluetooth.model.other.settings.DeviceSettingsModel;
import com.thor.businessmodule.bluetooth.model.other.settings.DeviceStatus;
import com.thor.businessmodule.bluetooth.model.other.settings.HWInfo;
import com.thor.businessmodule.bluetooth.model.other.settings.SoundPacket;
import com.thor.businessmodule.bluetooth.model.other.settings.SoundPackets;
import com.thor.businessmodule.bluetooth.model.other.settings.SoundPreset;
import com.thor.businessmodule.bluetooth.model.other.settings.SoundPresets;
import com.thor.businessmodule.bluetooth.request.other.ActivatePresetBleRequest;
import com.thor.businessmodule.bluetooth.request.other.ApplyUpdateFirmwareBleRequest;
import com.thor.businessmodule.bluetooth.request.other.ApplyUploadSoundPackageBleRequest;
import com.thor.businessmodule.bluetooth.request.other.ApplyUploadSoundPackageBleRequestNew;
import com.thor.businessmodule.bluetooth.request.other.ControlDetectDriveModeBleRequest;
import com.thor.businessmodule.bluetooth.request.other.DeleteInstalledSoundPackageBleRequest;
import com.thor.businessmodule.bluetooth.request.other.DownloadCommitFileBleRequest;
import com.thor.businessmodule.bluetooth.request.other.DownloadGetStatusBleRequest;
import com.thor.businessmodule.bluetooth.request.other.DownloadStartGroupBleRequest;
import com.thor.businessmodule.bluetooth.request.other.HandShakeBleRequest;
import com.thor.businessmodule.bluetooth.request.other.HardwareBleRequest;
import com.thor.businessmodule.bluetooth.request.other.PoilingRequest;
import com.thor.businessmodule.bluetooth.request.other.PoilingRequestOld;
import com.thor.businessmodule.bluetooth.request.other.ReadCanInfoBleRequest;
import com.thor.businessmodule.bluetooth.request.other.ReadDriveModesBleRequest;
import com.thor.businessmodule.bluetooth.request.other.ReadInstalledSoundPackageRulesRequest;
import com.thor.businessmodule.bluetooth.request.other.ReadSettingsBleRequest;
import com.thor.businessmodule.bluetooth.request.other.ReadUniversalDataStatisticBleRequest;
import com.thor.businessmodule.bluetooth.request.other.ReloadUploadingFirmwareBleRequest;
import com.thor.businessmodule.bluetooth.request.other.ReloadUploadingSoundPackageBleRequest;
import com.thor.businessmodule.bluetooth.request.other.StartUploadSoundPackageBleRequest;
import com.thor.businessmodule.bluetooth.request.other.UploadReadBlockRequest;
import com.thor.businessmodule.bluetooth.request.other.UploadSoundPackageFileBleRequest;
import com.thor.businessmodule.bluetooth.request.other.UploadStartBleRequest;
import com.thor.businessmodule.bluetooth.request.other.UploadStartFileBleRequest;
import com.thor.businessmodule.bluetooth.request.other.UploadStopRequest;
import com.thor.businessmodule.bluetooth.request.other.WriteBlockFileBleRequestNew;
import com.thor.businessmodule.bluetooth.request.other.WriteCanConfigurationsFileRequest;
import com.thor.businessmodule.bluetooth.request.other.WriteDriveModesBleRequest;
import com.thor.businessmodule.bluetooth.request.other.WriteFactoryPresetSettingBleRequest;
import com.thor.businessmodule.bluetooth.request.other.WriteFirmwareBleRequest;
import com.thor.businessmodule.bluetooth.request.other.WriteInstalledPresetsBleRequest;
import com.thor.businessmodule.bluetooth.request.other.WriteInstalledSoundPackageRulesRequest;
import com.thor.businessmodule.bluetooth.request.other.WriteLockDeviceBleRequest;
import com.thor.businessmodule.bluetooth.request.other.WriteSettingsBleRequest;
import com.thor.businessmodule.bluetooth.request.sgu.ApplySguSoundBleRequest;
import com.thor.businessmodule.bluetooth.request.sgu.PlaySguSoundBleRequest;
import com.thor.businessmodule.bluetooth.request.sgu.ReadSguSoundsBleRequest;
import com.thor.businessmodule.bluetooth.request.sgu.StartWriteSguSoundBleRequest;
import com.thor.businessmodule.bluetooth.request.sgu.StopSguSoundBleRequest;
import com.thor.businessmodule.bluetooth.request.sgu.WriteSguSoundBleRequest;
import com.thor.businessmodule.bluetooth.request.sgu.WriteSguSoundSequenceBleRequest;
import com.thor.businessmodule.bluetooth.response.other.ActivatePresetBleResponse;
import com.thor.businessmodule.bluetooth.response.other.ApplyUploadSoundPackageResponse;
import com.thor.businessmodule.bluetooth.response.other.ControlDetectDriveModeBleResponse;
import com.thor.businessmodule.bluetooth.response.other.DeleteInstalledSoundPackageBleResponse;
import com.thor.businessmodule.bluetooth.response.other.DownloadFileCommitResponse;
import com.thor.businessmodule.bluetooth.response.other.DownloadGetStatusResponse;
import com.thor.businessmodule.bluetooth.response.other.DownloadStartFileBleResponse;
import com.thor.businessmodule.bluetooth.response.other.DownloadStartGroupBleResponse;
import com.thor.businessmodule.bluetooth.response.other.DownloadWriteBlockBleResponse;
import com.thor.businessmodule.bluetooth.response.other.FormatFlashBleResponse;
import com.thor.businessmodule.bluetooth.response.other.FormatFlashBleResponseOld;
import com.thor.businessmodule.bluetooth.response.other.HandShakeBleResponse;
import com.thor.businessmodule.bluetooth.response.other.HardwareBleResponse;
import com.thor.businessmodule.bluetooth.response.other.HardwareBleResponseOld;
import com.thor.businessmodule.bluetooth.response.other.InstalledSoundPackagesResponse;
import com.thor.businessmodule.bluetooth.response.other.UploadReadBlockBleResponse;
import com.thor.businessmodule.bluetooth.response.other.UploadStartBleResponse;
import com.thor.businessmodule.bluetooth.response.other.UploadStopResponse;
import com.thor.businessmodule.bluetooth.response.other.WriteCanConfigurationsBlockBleResponse;
import com.thor.businessmodule.bluetooth.response.other.WriteFirmwareBlockBleResponse;
import com.thor.businessmodule.bluetooth.response.other.WriteInstalledPresetsBleResponse;
import com.thor.businessmodule.bluetooth.response.other.WriteInstalledSoundPackageRulesResponse;
import com.thor.businessmodule.bluetooth.response.other.WriteSoundPackageFileBlockBleResponse;
import com.thor.businessmodule.bluetooth.response.sgu.ApplySguSoundBleResponse;
import com.thor.businessmodule.bluetooth.response.sgu.PlaySguSoundBleResponse;
import com.thor.businessmodule.bluetooth.response.sgu.ReadSguSoundsResponse;
import com.thor.businessmodule.bluetooth.response.sgu.StartWriteSguSoundBleResponse;
import com.thor.businessmodule.bluetooth.response.sgu.StopSguSoundBleResponse;
import com.thor.businessmodule.bluetooth.response.sgu.WriteSguSoundBleResponse;
import com.thor.businessmodule.bluetooth.response.sgu.WriteSguSoundSequenceBleResponse;
import com.thor.businessmodule.bluetooth.util.BleCommands;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import com.thor.businessmodule.bluetooth.util.ModelsKt;
import com.thor.businessmodule.bus.events.FormatFlashReconnecting;
import com.thor.businessmodule.crypto.CryptoManager;
import com.thor.businessmodule.crypto.EncryptionHelper;
import com.thor.businessmodule.model.MainPresetPackage;
import com.thor.businessmodule.settings.Variables;
import com.thor.networkmodule.model.firmware.FirmwareProfile;
import com.thor.networkmodule.model.responses.SignInResponse;
import com.thor.networkmodule.model.responses.sgu.SguSoundConfig;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

/* compiled from: BleManager.kt */
@Singleton
@Metadata(d1 = {"\u0000\u008c\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b'\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u0000 Ç\u00022\u00020\u0001:\u0002Ç\u0002B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010z\u001a\u00020{2\u0006\u0010|\u001a\u00020}J\f\u0010~\u001a\b\u0012\u0004\u0012\u00020Y0\u007fJ\t\u0010\u0080\u0001\u001a\u00020{H\u0002J\t\u0010\u0081\u0001\u001a\u00020{H\u0002J\t\u0010\u0082\u0001\u001a\u00020{H\u0002J\t\u0010\u0083\u0001\u001a\u00020{H\u0002J\t\u0010\u0084\u0001\u001a\u00020{H\u0002J\t\u0010\u0085\u0001\u001a\u00020{H\u0002J \u0010\u0086\u0001\u001a\u00020{2\u0017\u0010\u0087\u0001\u001a\u0012\u0012\u0007\u0012\u0005\u0018\u00010\u0089\u0001\u0012\u0004\u0012\u00020{0\u0088\u0001J\u0007\u0010\u008a\u0001\u001a\u00020{J\u0007\u0010\u008b\u0001\u001a\u00020{J\u0007\u0010\u008c\u0001\u001a\u00020{J\u0013\u0010\u008d\u0001\u001a\u00020{2\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001H\u0002J\u0007\u0010\u0090\u0001\u001a\u00020{J\t\u0010\u0091\u0001\u001a\u00020{H\u0002J\u0010\u0010\u0092\u0001\u001a\u00020{2\u0007\u0010\u0093\u0001\u001a\u00020\fJ\u0007\u0010\u0094\u0001\u001a\u00020{J\t\u0010\u0095\u0001\u001a\u00020{H\u0002J,\u0010\u0096\u0001\u001a\u00020{2\u0007\u0010\u0097\u0001\u001a\u00020\u001a2\b\u0010\u008e\u0001\u001a\u00030\u008f\u00012\u0007\u0010\u0098\u0001\u001a\u00020\u00182\u0007\u0010\u0099\u0001\u001a\u00020\u0018J5\u0010\u009a\u0001\u001a\u00020{2\b\u0010\u008e\u0001\u001a\u00030\u008f\u00012\u0007\u0010\u009b\u0001\u001a\u00020\u00182\u0007\u0010\u009c\u0001\u001a\u00020\u001a2\u0007\u0010\u0098\u0001\u001a\u00020\u00182\u0007\u0010\u0099\u0001\u001a\u00020\u0018J\t\u0010\u009d\u0001\u001a\u00020\fH\u0002J-\u0010\u009e\u0001\u001a\u00020{2\u0007\u0010\u009f\u0001\u001a\u00020)2\u0007\u0010 \u0001\u001a\u00020'2\u0012\b\u0002\u0010¡\u0001\u001a\u000b\u0012\u0004\u0012\u00020{\u0018\u00010¢\u0001J\r\u0010£\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007fJ\u0017\u0010¤\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007f2\b\u0010¥\u0001\u001a\u00030¦\u0001J\u0017\u0010§\u0001\u001a\t\u0012\u0005\u0012\u00030¨\u00010\u007f2\u0007\u0010©\u0001\u001a\u00020)J\u0014\u0010ª\u0001\u001a\u00020{2\t\u0010«\u0001\u001a\u0004\u0018\u00010\u001aH\u0002J\u0010\u0010¬\u0001\u001a\u00020{2\u0007\u0010\u00ad\u0001\u001a\u00020\u0018J\u0010\u0010®\u0001\u001a\u00020{2\u0007\u0010¯\u0001\u001a\u00020)J\u000f\u0010°\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007fH\u0002J#\u0010±\u0001\u001a\u00020{2\u0007\u0010\u0097\u0001\u001a\u00020\u001a2\u0007\u0010²\u0001\u001a\u00020)2\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001J\u0010\u0010³\u0001\u001a\u00020{2\u0007\u0010´\u0001\u001a\u00020\fJ\u001d\u0010µ\u0001\u001a\u00020{2\u0014\u0010\u0087\u0001\u001a\u000f\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020{0\u0088\u0001J\r\u0010¶\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007fJ%\u0010·\u0001\u001a\u00020{2\b\u0010¸\u0001\u001a\u00030¹\u00012\u0010\u0010º\u0001\u001a\u000b\u0012\u0004\u0012\u00020{\u0018\u00010¢\u0001H\u0002J\t\u0010»\u0001\u001a\u00020{H\u0007J\t\u0010¼\u0001\u001a\u00020{H\u0007J\u001b\u0010½\u0001\u001a\u00020{2\u0012\b\u0002\u0010º\u0001\u001a\u000b\u0012\u0004\u0012\u00020{\u0018\u00010¢\u0001J!\u0010¾\u0001\u001a\t\u0012\u0005\u0012\u00030¿\u00010\u007f2\u0007\u0010©\u0001\u001a\u00020)2\b\u0010À\u0001\u001a\u00030Á\u0001J\u000f\u0010Â\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007fH\u0007J\u000f\u0010Ã\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007fH\u0007J\r\u0010Ä\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007fJ\r\u0010Å\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007fJ\r\u0010Æ\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007fJ\r\u0010Ç\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007fJ\u000f\u0010È\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010Y0\u007fJ\u0017\u0010É\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007f2\b\u0010Ê\u0001\u001a\u00030Ë\u0001J \u0010Ì\u0001\u001a\u00020{2\u0017\b\u0002\u0010Í\u0001\u001a\u0010\u0012\u0005\u0012\u00030Î\u0001\u0012\u0004\u0012\u00020{0\u0088\u0001J\r\u0010Ï\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007fJ\u000e\u0010Ð\u0001\u001a\t\u0012\u0005\u0012\u00030Ñ\u00010\u007fJ\u0016\u0010Ò\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007f2\u0007\u0010Ó\u0001\u001a\u00020)J(\u0010Ô\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007f2\u0007\u0010Õ\u0001\u001a\u00020\u00182\u0007\u0010Ö\u0001\u001a\u00020\u00182\u0007\u0010×\u0001\u001a\u00020\u0018J\u001f\u0010Ø\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007f2\u0007\u0010Ù\u0001\u001a\u00020\u00182\u0007\u0010Ú\u0001\u001a\u00020\u0018J\u0016\u0010Û\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007f2\u0007\u0010Ü\u0001\u001a\u00020\u0018J\u0017\u0010Ý\u0001\u001a\t\u0012\u0005\u0012\u00030Þ\u00010\u007f2\u0007\u0010©\u0001\u001a\u00020)J\u0017\u0010ß\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007f2\b\u0010¥\u0001\u001a\u00030¦\u0001J\u000e\u0010à\u0001\u001a\t\u0012\u0005\u0012\u00030á\u00010\u007fJ\u0007\u0010â\u0001\u001a\u00020{J\u0007\u0010ã\u0001\u001a\u00020{J\u0007\u0010ä\u0001\u001a\u00020{J\u0010\u0010å\u0001\u001a\u00020{2\u0007\u0010æ\u0001\u001a\u000206J\u0012\u0010å\u0001\u001a\u00020{2\t\u0010«\u0001\u001a\u0004\u0018\u00010\u001aJ\u0019\u0010ç\u0001\u001a\u00020{2\u0007\u0010\u0097\u0001\u001a\u00020\u001a2\u0007\u0010²\u0001\u001a\u00020)J\u0014\u0010ç\u0001\u001a\u00020{2\t\u0010«\u0001\u001a\u0004\u0018\u00010\u001aH\u0002J\u0014\u0010è\u0001\u001a\u00020{2\t\u0010«\u0001\u001a\u0004\u0018\u00010\u001aH\u0002J#\u0010é\u0001\u001a\u00020{2\b\u0010¥\u0001\u001a\u00030¦\u00012\u0007\u0010\u0097\u0001\u001a\u00020\u001a2\u0007\u0010²\u0001\u001a\u00020)J\u0019\u0010ê\u0001\u001a\u00020{2\u0007\u0010ë\u0001\u001a\u00020\u00182\u0007\u0010ì\u0001\u001a\u00020\u0018J#\u0010í\u0001\u001a\u00020{2\b\u0010¥\u0001\u001a\u00030¦\u00012\u0007\u0010\u0097\u0001\u001a\u00020\u001a2\u0007\u0010²\u0001\u001a\u00020)J\"\u0010î\u0001\u001a\u00020{2\u0007\u0010\u0097\u0001\u001a\u00020\u001a2\u0007\u0010©\u0001\u001a\u00020)2\u0007\u0010²\u0001\u001a\u00020)J\u0014\u0010î\u0001\u001a\u00020{2\t\u0010«\u0001\u001a\u0004\u0018\u00010\u001aH\u0002J\u0012\u0010ï\u0001\u001a\u00020{2\t\u0010«\u0001\u001a\u0004\u0018\u00010\u001aJ\u0012\u0010ð\u0001\u001a\u00020{2\t\u0010«\u0001\u001a\u0004\u0018\u00010\u001aJ#\u0010ñ\u0001\u001a\u00020{2\b\u0010¥\u0001\u001a\u00030¦\u00012\u0007\u0010\u0097\u0001\u001a\u00020\u001a2\u0007\u0010²\u0001\u001a\u00020)J\u0016\u0010ò\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007f2\u0007\u0010ó\u0001\u001a\u00020\u0018J2\u0010ô\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007f2\u0007\u0010Ù\u0001\u001a\u00020\u00182\u0007\u0010Ú\u0001\u001a\u00020\u00182\u0007\u0010\u0097\u0001\u001a\u00020\u001a2\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001J\t\u0010õ\u0001\u001a\u00020{H\u0002J\u0017\u0010ö\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007f2\b\u0010æ\u0001\u001a\u00030÷\u0001J\u001f\u0010ø\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u007f2\u0007\u0010\u0098\u0001\u001a\u00020)2\u0007\u0010ù\u0001\u001a\u00020)J\u001c\u0010ú\u0001\u001a\u00020{2\b\u0010û\u0001\u001a\u00030ü\u00012\t\b\u0002\u0010ý\u0001\u001a\u00020)JF\u0010þ\u0001\u001a\u00020{2\b\u0010û\u0001\u001a\u00030ü\u00012\t\b\u0002\u0010ÿ\u0001\u001a\u00020\f2\t\b\u0002\u0010ý\u0001\u001a\u00020)2\t\b\u0002\u0010\u0080\u0002\u001a\u00020\f2\u0012\b\u0002\u0010¡\u0001\u001a\u000b\u0012\u0004\u0012\u00020{\u0018\u00010¢\u0001J\u0017\u0010þ\u0001\u001a\u00020{2\u000e\u0010\u0081\u0002\u001a\t\u0012\u0005\u0012\u00030\u0082\u00020NJ\u001b\u0010\u0083\u0002\u001a\u00020{2\b\u0010\u0084\u0002\u001a\u00030\u0085\u00022\b\u0010\u0086\u0002\u001a\u00030\u0087\u0002J\u001b\u0010\u0088\u0002\u001a\u00020{2\b\u0010\u0084\u0002\u001a\u00030\u0085\u00022\b\u0010\u0086\u0002\u001a\u00030\u0087\u0002J\u0016\u0010\u0089\u0002\u001a\b\u0012\u0004\u0012\u00020Y0\u007f2\u0007\u0010\u008a\u0002\u001a\u00020\fJ\u001e\u0010\u008b\u0002\u001a\b\u0012\u0004\u0012\u00020Y0\u007f2\u000f\u0010\u008c\u0002\u001a\n\u0012\u0005\u0012\u00030\u008e\u00020\u008d\u0002J\u001d\u0010\u008f\u0002\u001a\t\u0012\u0005\u0012\u00030\u0090\u00020\u007f2\r\u0010\u0091\u0002\u001a\b\u0012\u0004\u0012\u00020)0NJ\u0007\u0010\u0092\u0002\u001a\u00020{J\u0007\u0010\u0093\u0002\u001a\u00020'J\u0007\u0010\u0094\u0002\u001a\u00020)J\t\u0010\u0095\u0002\u001a\u0004\u0018\u00010+J\t\u0010\u0096\u0002\u001a\u0004\u0018\u00010-J\u0007\u0010\u0097\u0002\u001a\u00020\u0018J\u001b\u0010\u0098\u0002\u001a\u00020{2\u0010\u0010\u0099\u0002\u001a\u000b\u0012\u0005\u0012\u00030\u009a\u0002\u0018\u00010NH\u0003J\u0013\u0010\u009b\u0002\u001a\u00020{2\b\u0010\u009c\u0002\u001a\u00030Î\u0001H\u0002J\t\u0010\u009d\u0002\u001a\u00020{H\u0002J\t\u0010\u009e\u0002\u001a\u00020{H\u0002J\t\u0010\u009f\u0002\u001a\u00020{H\u0002J\t\u0010 \u0002\u001a\u00020{H\u0002J\t\u0010¡\u0002\u001a\u00020{H\u0002J\t\u0010¢\u0002\u001a\u00020{H\u0002J\u0007\u0010£\u0002\u001a\u00020\fJ\u0007\u0010¤\u0002\u001a\u00020\fJ\u0007\u0010¥\u0002\u001a\u00020\fJ\u0007\u0010¦\u0002\u001a\u00020\fJ\t\u0010§\u0002\u001a\u00020{H\u0002J\u0011\u0010¨\u0002\u001a\u00020{2\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001J\u0007\u0010©\u0002\u001a\u00020{J\u0010\u0010ª\u0002\u001a\t\u0012\u0005\u0012\u00030Î\u00010\u007fH\u0002J\u0007\u0010«\u0002\u001a\u00020{J\t\u0010¬\u0002\u001a\u00020{H\u0002J\u0007\u0010\u00ad\u0002\u001a\u00020{J\u0013\u0010®\u0002\u001a\u00020{2\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001H\u0002J\u0013\u0010¯\u0002\u001a\u00020{2\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001H\u0002J\u0013\u0010°\u0002\u001a\u00020{2\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001H\u0002J\u0013\u0010±\u0002\u001a\u00020{2\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001H\u0002J\t\u0010²\u0002\u001a\u00020{H\u0002J\t\u0010³\u0002\u001a\u00020{H\u0002J\t\u0010´\u0002\u001a\u00020{H\u0002J\t\u0010µ\u0002\u001a\u00020{H\u0002J\u0010\u0010¶\u0002\u001a\u00020{2\u0007\u0010 \u0001\u001a\u00020'J\u0010\u0010·\u0002\u001a\u00020{2\u0007\u0010\u009f\u0001\u001a\u00020)J\u0010\u0010¸\u0002\u001a\u00020{2\u0007\u0010¹\u0002\u001a\u00020\fJ!\u0010º\u0002\u001a\b\u0012\u0004\u0012\u00020\u001a0N2\u0007\u0010»\u0002\u001a\u00020\u001a2\t\b\u0002\u0010¼\u0002\u001a\u00020\u0018J\u0007\u0010½\u0002\u001a\u00020{J\u0007\u0010¾\u0002\u001a\u00020{J\u0007\u0010¿\u0002\u001a\u00020{J\u0007\u0010À\u0002\u001a\u00020{J\u0019\u0010Á\u0002\u001a\u00020{2\u000e\u0010¥\u0001\u001a\t\u0012\u0005\u0012\u00030Â\u00020NH\u0002J\u0014\u0010Ã\u0002\u001a\u00020{2\t\b\u0002\u0010Ä\u0002\u001a\u00020\u0006H\u0002J\t\u0010Å\u0002\u001a\u00020{H\u0002J\u0014\u0010Å\u0002\u001a\u00020{2\t\u0010«\u0001\u001a\u0004\u0018\u00010\u001aH\u0002J\u001d\u0010Å\u0002\u001a\u00020{2\t\u0010«\u0001\u001a\u0004\u0018\u00010\u001a2\u0007\u0010Æ\u0002\u001a\u00020\u0006H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u000e\"\u0004\b\u001c\u0010\u0010R\u000e\u0010\u001d\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010 \u001a\u0004\u0018\u00010\u0018X\u0086\u000e¢\u0006\u0010\n\u0002\u0010%\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u000e\u0010&\u001a\u00020'X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u0004\u0018\u00010+X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u0004\u0018\u00010-X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u0004\u0018\u000100X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00101\u001a\u0004\u0018\u000102X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00103\u001a\u0004\u0018\u000102X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00105\u001a\u0004\u0018\u000106X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u00109\u001a\u00020:¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u000e\u0010=\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010>\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010?\u001a\u00020:¢\u0006\b\n\u0000\u001a\u0004\b@\u0010<R\u000e\u0010A\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020CX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020EX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010F\u001a\u0004\u0018\u00010GX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010H\u001a\u0004\u0018\u00010I8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bL\u0010\u0016\u001a\u0004\bJ\u0010KR\u0016\u0010M\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010NX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010P\u001a\u00020Q8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bT\u0010\u0016\u001a\u0004\bR\u0010SR\u001b\u0010U\u001a\u00020Q8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bW\u0010\u0016\u001a\u0004\bV\u0010SR\u000e\u0010X\u001a\u00020YX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010Z\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010[\u001a\u0004\u0018\u00010\\X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010]\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010^\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010_\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010`\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010a\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010b\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010c\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010d\u001a\u0004\u0018\u00010eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010f\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010g\u001a\u0004\u0018\u00010hX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010j\"\u0004\bk\u0010lR\u0014\u0010m\u001a\b\u0012\u0004\u0012\u00020\u00180nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010o\u001a\u00020\u0018X\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010p\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bq\u0010r\"\u0004\bs\u0010tR\u001a\u0010u\u001a\u00020)X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bv\u0010w\"\u0004\bx\u0010y¨\u0006È\u0002"}, d2 = {"Lcom/thor/app/managers/BleManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "TIME_FIX_DELAY", "", "getContext", "()Landroid/content/Context;", "fileDownloadSize", "", "firmwareUploaded", "", "getFirmwareUploaded", "()Z", "setFirmwareUploaded", "(Z)V", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "Lkotlin/Lazy;", "hightPriorityRequestThrottleTime", "", "inputByteFile", "", "isFromOldMode", "setFromOldMode", "isStartedScan", "lastHightPriorityRequestTime", "lastRssiUpdateTime", "lastSendBlock", "getLastSendBlock", "()Ljava/lang/Integer;", "setLastSendBlock", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "mActivatedPreset", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPreset;", "mActivatedPresetIndex", "", "mBluetoothAdapter", "Landroid/bluetooth/BluetoothAdapter;", "mBluetoothDevice", "Landroid/bluetooth/BluetoothDevice;", "mBluetoothEnabled", "mBluetoothGatt", "Landroid/bluetooth/BluetoothGatt;", "mBluetoothNotifyCharacteristic", "Landroid/bluetooth/BluetoothGattCharacteristic;", "mBluetoothWriteCharacteristic", "mCanConfigurationsLastBlockStateSuccessful", "mCanConfigurationsRequest", "Lcom/thor/businessmodule/bluetooth/request/other/WriteCanConfigurationsFileRequest;", "mCommandExecuting", "mCommandUploadExecuting", "mCompositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "getMCompositeDisposable", "()Lio/reactivex/disposables/CompositeDisposable;", "mCurrentBlock", "mCurrentCommandRequest", "mDatabaseCompositeDisposable", "getMDatabaseCompositeDisposable", "mErrorsCount", "mExecutorService", "Ljava/util/concurrent/ExecutorService;", "mFileLogger", "Lcom/thor/app/utils/logs/loggers/FileLogger;", "mFirmwareRequest", "Lcom/thor/businessmodule/bluetooth/request/other/WriteFirmwareBleRequest;", "mGattCallback", "Landroid/bluetooth/BluetoothGattCallback;", "getMGattCallback", "()Landroid/bluetooth/BluetoothGattCallback;", "mGattCallback$delegate", "mInputByteArrays", "", "mIsFormatFlash", "mLeScanAllThorDevicesCallback", "Landroid/bluetooth/le/ScanCallback;", "getMLeScanAllThorDevicesCallback", "()Landroid/bluetooth/le/ScanCallback;", "mLeScanAllThorDevicesCallback$delegate", "mLeScanCallback", "getMLeScanCallback", "mLeScanCallback$delegate", "mOutputStream", "Ljava/io/ByteArrayOutputStream;", "mReInitCrypto", "mSguSoundRequest", "Lcom/thor/businessmodule/bluetooth/request/sgu/WriteSguSoundBleRequest;", "mSingleRequestPart", "mStateConnected", "mStateConnecting", "mStateDisconnecting", "mStepRequest", "mStepsRequest", "mTimer", "mUploadSoundFileRequest", "Lcom/thor/businessmodule/bluetooth/request/other/UploadSoundPackageFileBleRequest;", "mUploadingData", "readBluetoothRssiJob", "Lkotlinx/coroutines/Job;", "getReadBluetoothRssiJob", "()Lkotlinx/coroutines/Job;", "setReadBluetoothRssiJob", "(Lkotlinx/coroutines/Job;)V", "rssiLevel", "Landroidx/databinding/ObservableField;", "rssiUpdateThrottleTime", "sendByteArray", "getSendByteArray", "()[B", "setSendByteArray", "([B)V", "serviceActivePresetIndex", "getServiceActivePresetIndex", "()S", "setServiceActivePresetIndex", "(S)V", "addCommandDisposable", "", "disposable", "Lio/reactivex/disposables/Disposable;", "applyUpdateFirmware", "Lio/reactivex/Observable;", "checkCrcForUpdatingCanConfigurations", "checkCrcForUpdatingFirmware", "checkCrcForUploadSguSound", "checkCrcForUploadingFileNew", "checkCrcForUploadingSound", "checkCrcForUploadingSoundNew", "checkDoUploadFile", "result", "Lkotlin/Function1;", "Lcom/thor/businessmodule/bluetooth/model/other/DownloadGetStatusModel;", "clear", "clearRequests", "clearTasks", "commitDownloadFile", "fileType", "Lcom/thor/businessmodule/bluetooth/model/other/FileType;", "connect", "disableNotification", "disconnect", "useNull", "disconnectNotNull", "doOnDeviceConnect", "downloadStartFile", "file", "packageId", "packageVersion", "downloadStartGroup", "countFile", "fileUpload", "enableNotification", "executeActivatePresetCommand", FirebaseAnalytics.Param.INDEX, "preset", "callFromDownload", "Lkotlin/Function0;", "executeApplyUploadPackage", "executeApplyUploadSoundPackage", "soundPackage", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "executeApplyUploadedSguSoundCommand", "Lcom/thor/businessmodule/bluetooth/response/sgu/ApplySguSoundBleResponse;", "soundId", "executeCommandRequest", "bytes", "executeControlDetectDriveMode", "activate", "executeDeleteInstalledSoundPackageCommand", "installedSoundPackageId", "executeDownloadCommitFile", "executeDownloadWriteBlock", "blockNumber", "executeFormatFlashCommand", "showDelaySecond", "executeFormatFlashCommandOld", "executeFormatFlashCommandOldNew", "executeHandShake", "hardwareProfile", "Lcom/thor/businessmodule/bluetooth/model/other/HardwareProfile;", "onReInitCrypto", "executeHardwareCommand", "executeHardwareCommandOld", "executeInitCrypto", "executePlaySguSoundCommand", "Lcom/thor/businessmodule/bluetooth/response/sgu/PlaySguSoundBleResponse;", "config", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundConfig;", "executePoilingCommand", "executePoilingCommandOld", "executeReDownloadFile", "executeReadCanInfoCommand", "executeReadDeviceParametersCommand", "executeReadDriveModes", "executeReadInstalledPresetsCommand", "executeReadInstalledSoundPackageRulesCommand", "installedSoundPackage", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledSoundPackage;", "executeReadInstalledSoundPackagesCommand", "action", "Lcom/thor/businessmodule/bluetooth/response/other/InstalledSoundPackagesResponse;", "executeReadSettingsCommand", "executeReadSguSoundsCommand", "Lcom/thor/businessmodule/bluetooth/response/sgu/ReadSguSoundsResponse;", "executeReadUniversalDataParametersCommand", "dataGroup", "executeReloadFirmware", "deviceId", "hardwareVersion", "firmwareVersion", "executeReloadUploadingSoundPackage", "soundPackageId", "soundPackageVersion", "executeStartDownloadGroup", "fileCount", "executeStartUploadSguSoundCommand", "Lcom/thor/businessmodule/bluetooth/response/sgu/StartWriteSguSoundBleResponse;", "executeStartUploadSoundPackage", "executeStopSguSoundCommand", "Lcom/thor/businessmodule/bluetooth/response/sgu/StopSguSoundBleResponse;", "executeTestCommand", "executeTurnOffDetectDriveMode", "executeTurnOnDetectDriveMode", "executeUpdateCanConfigurations", "request", "executeUpdateFirmware", "executeUploadFileNew", "executeUploadPackageRule", "executeUploadReadBlock", "fileSize", "maxBlockSize", "executeUploadSampleRule", "executeUploadSguSound", "executeUploadSound", "executeUploadSoundNew", "executeUploadSoundSamples", "executeUploadStartCommand", "fileId", "executeUploadStartFile", "executeUploadStop", "executeWriteDriveModes", "Lcom/thor/businessmodule/bluetooth/request/other/WriteDriveModesBleRequest;", "executeWriteFactoryPresetSetting", "modeIndex", "executeWriteInstalledAddPreset", "installedPresets", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "activePresetIndex", "executeWriteInstalledPresetsCommand", "isFromDownload", "postEvent", "presets", "Lcom/thor/businessmodule/model/MainPresetPackage;", "executeWriteInstalledSoundPackageRules", "installedPresetRules", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresetRules;", "presetRule", "Lcom/thor/businessmodule/bluetooth/model/other/PresetRule;", "executeWriteInstalledSoundPackageRulesStop", "executeWriteLockDeviceCommand", "lock", "executeWriteSettingsCommand", "settings", "Ljava/util/ArrayList;", "Lcom/thor/businessmodule/bluetooth/model/other/DeviceSettings$Setting;", "executeWriteSguSoundSequenceCommand", "Lcom/thor/businessmodule/bluetooth/response/sgu/WriteSguSoundSequenceBleResponse;", "soundIds", "finish", "getActivatedPreset", "getActivatedPresetIndex", "getBluetoothAdapter", "getBluetoothDevice", "getRssiLevel", "handleCharacteristic", "services", "Landroid/bluetooth/BluetoothGattService;", "handleInstalledSoundPackages", "response", "handleWriteCanConfigurationsResponse", "handleWriteFileResponseNew", "handleWriteFirmwareResponse", "handleWriteSguSoundResponse", "handleWriteSoundFileResponse", "handleWriteSoundFileResponseNew", "isBleEnabledAndDeviceConnected", "isConnected", "isDataUploading", "isDiscoveringDevices", "lunchCommandAfterCrypto", "onApplyPackage", "onDeviceConnect", "prepareReadInstalledSoundPackagesObservable", "readRemoteRssi", "requestHighPriority", "resetFormatFlashValue", "sendEventChangeUI", "sendEventCommitFile", "sendOnApplyPackageEvent", "sendOnCancelDownloadEvent", "sendUpdateFirmwareProgressEvent", "sendUploadSguSoundProgressEvent", "sendUploadSoundProgressEvent", "sendUploadSoundProgressEvent70", "setActivatedPreset", "setActivatedPresetIndex", "setBluetoothStateConnection", "isState", "splitByteArrayIntoChunks", "data", "chunkSize", "startReadBluetoothRssiJob", "startScan", "stopReadBluetoothRssiJob", "stopScan", "updateInstallPresetDB", "Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPacket;", "waitTime", "time", "writeCharacteristicValue", "duration", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BleManager {
    public static final int BLE_BUFFER = 20;
    public static final String CHARACTERISTIC_NORDIC_NOTIFY_UUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    public static final String CHARACTERISTIC_NORDIC_WRITE_UUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    public static final String CHARACTERISTIC_UUID = "0000ffe1-0000-1000-8000-00805f9b34fb";

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int ERRORS_COUNT_MAX = 10;
    public static final int GATT_ERROR_CODE = 133;
    public static final String SERVICE_NORDIC_UUID = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    public static final String SERVICE_UUID = "0000ffe0-0000-1000-8000-00805f9b34fb";
    public static final long TIMER_DURATION = 1750;
    private long TIME_FIX_DELAY;
    private final Context context;
    private double fileDownloadSize;
    private boolean firmwareUploaded;

    /* renamed from: handler$delegate, reason: from kotlin metadata */
    private final Lazy handler;
    private final int hightPriorityRequestThrottleTime;
    private byte[] inputByteFile;
    private boolean isFromOldMode;
    private boolean isStartedScan;
    private long lastHightPriorityRequestTime;
    private long lastRssiUpdateTime;
    private Integer lastSendBlock;
    private InstalledPreset mActivatedPreset;
    private short mActivatedPresetIndex;
    private final BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private boolean mBluetoothEnabled;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCharacteristic mBluetoothNotifyCharacteristic;
    private BluetoothGattCharacteristic mBluetoothWriteCharacteristic;
    private boolean mCanConfigurationsLastBlockStateSuccessful;
    private WriteCanConfigurationsFileRequest mCanConfigurationsRequest;
    private volatile boolean mCommandExecuting;
    private boolean mCommandUploadExecuting;
    private final CompositeDisposable mCompositeDisposable;
    private int mCurrentBlock;
    private byte[] mCurrentCommandRequest;
    private final CompositeDisposable mDatabaseCompositeDisposable;
    private volatile int mErrorsCount;
    private final ExecutorService mExecutorService;
    private final FileLogger mFileLogger;
    private WriteFirmwareBleRequest mFirmwareRequest;

    /* renamed from: mGattCallback$delegate, reason: from kotlin metadata */
    private final Lazy mGattCallback;
    private List<byte[]> mInputByteArrays;
    private boolean mIsFormatFlash;

    /* renamed from: mLeScanAllThorDevicesCallback$delegate, reason: from kotlin metadata */
    private final Lazy mLeScanAllThorDevicesCallback;

    /* renamed from: mLeScanCallback$delegate, reason: from kotlin metadata */
    private final Lazy mLeScanCallback;
    private final ByteArrayOutputStream mOutputStream;
    private int mReInitCrypto;
    private WriteSguSoundBleRequest mSguSoundRequest;
    private byte[] mSingleRequestPart;
    private volatile boolean mStateConnected;
    private boolean mStateConnecting;
    private boolean mStateDisconnecting;
    private volatile int mStepRequest;
    private int mStepsRequest;
    private long mTimer;
    private UploadSoundPackageFileBleRequest mUploadSoundFileRequest;
    private boolean mUploadingData;
    private Job readBluetoothRssiJob;
    private final ObservableField<Integer> rssiLevel;
    private final int rssiUpdateThrottleTime;
    private byte[] sendByteArray;
    private short serviceActivePresetIndex;

    /* compiled from: BleManager.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FileType.values().length];
            try {
                iArr[FileType.SoundSampleV3.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FileType.SoundSamplePackageV2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FileType.SoundRulesPackageV2.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FileType.SoundModeRulesPackageV2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[FileType.FirmwareFile.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[FileType.SGU.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[FileType.CAN.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @JvmStatic
    public static final BleManager from(Context context) {
        return INSTANCE.from(context);
    }

    public final void executeTestCommand() {
    }

    public BleManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.mCompositeDisposable = new CompositeDisposable();
        this.mDatabaseCompositeDisposable = new CompositeDisposable();
        this.mActivatedPreset = new InstalledPreset((short) 0, (short) 0, (short) 0);
        this.rssiUpdateThrottleTime = 2000;
        this.hightPriorityRequestThrottleTime = 3000;
        this.rssiLevel = new ObservableField<>(0);
        this.handler = LazyKt.lazy(new Function0<Handler>() { // from class: com.thor.app.managers.BleManager$handler$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Handler invoke() {
                return new Handler(Looper.getMainLooper());
            }
        });
        this.inputByteFile = new byte[0];
        this.sendByteArray = new byte[0];
        Object systemService = context.getSystemService("bluetooth");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.bluetooth.BluetoothManager");
        BluetoothAdapter adapter = ((BluetoothManager) systemService).getAdapter();
        this.mBluetoothAdapter = adapter;
        if (adapter != null) {
            this.mBluetoothEnabled = adapter.isEnabled();
        }
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
        Intrinsics.checkNotNullExpressionValue(executorServiceNewSingleThreadExecutor, "newSingleThreadExecutor()");
        this.mExecutorService = executorServiceNewSingleThreadExecutor;
        this.mOutputStream = new ByteArrayOutputStream();
        this.mFileLogger = FileLogger.INSTANCE.newInstance(context, "BleManager");
        this.mLeScanCallback = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager$mLeScanCallback$2.AnonymousClass1>() { // from class: com.thor.app.managers.BleManager$mLeScanCallback$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Type inference failed for: r0v0, types: [com.thor.app.managers.BleManager$mLeScanCallback$2$1] */
            @Override // kotlin.jvm.functions.Function0
            public final AnonymousClass1 invoke() {
                final BleManager bleManager = this.this$0;
                return new ScanCallback() { // from class: com.thor.app.managers.BleManager$mLeScanCallback$2.1
                    @Override // android.bluetooth.le.ScanCallback
                    public void onScanResult(int callbackType, final ScanResult result) {
                        super.onScanResult(callbackType, result);
                        bleManager.mFileLogger.i("onScanResult: " + callbackType + ", " + result, new Object[0]);
                        if (bleManager.mStateConnected || bleManager.mBluetoothDevice != null || bleManager.mStateConnecting) {
                            bleManager.stopScan();
                            bleManager.onDeviceConnect();
                        } else {
                            Context context2 = bleManager.getContext();
                            final BleManager bleManager2 = bleManager;
                            ContextKt.checkBluetoothPermissionAndInvoke(context2, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager$mLeScanCallback$2$1$onScanResult$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                                    BluetoothDevice device;
                                    String name;
                                    ScanResult scanResult = result;
                                    if (scanResult == null || (device = scanResult.getDevice()) == null || (name = device.getName()) == null) {
                                        return;
                                    }
                                    BleManager bleManager3 = bleManager2;
                                    ScanResult scanResult2 = result;
                                    SignInResponse profile = Settings.INSTANCE.getProfile();
                                    String fullDeviceSN = profile != null ? profile.getFullDeviceSN() : null;
                                    bleManager3.mFileLogger.i("DeviceSN: " + fullDeviceSN, new Object[0]);
                                    if (Intrinsics.areEqual(name, fullDeviceSN)) {
                                        bleManager3.stopScan();
                                        bleManager3.mBluetoothDevice = scanResult2.getDevice();
                                        Settings settings = Settings.INSTANCE;
                                        BluetoothDevice bluetoothDevice = bleManager3.mBluetoothDevice;
                                        settings.saveDeviceMacAddress(bluetoothDevice != null ? bluetoothDevice.getAddress() : null);
                                        EventBus.getDefault().post(new BluetoothScanResultEvent(scanResult2));
                                        EventBus.getDefault().post(new StopScanBluetoothDevicesEvent(true));
                                        bleManager3.doOnDeviceConnect();
                                    }
                                }
                            });
                        }
                    }
                };
            }
        });
        this.mLeScanAllThorDevicesCallback = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager$mLeScanAllThorDevicesCallback$2.AnonymousClass1>() { // from class: com.thor.app.managers.BleManager$mLeScanAllThorDevicesCallback$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Type inference failed for: r0v0, types: [com.thor.app.managers.BleManager$mLeScanAllThorDevicesCallback$2$1] */
            @Override // kotlin.jvm.functions.Function0
            public final AnonymousClass1 invoke() {
                final BleManager bleManager = this.this$0;
                return new ScanCallback() { // from class: com.thor.app.managers.BleManager$mLeScanAllThorDevicesCallback$2.1
                    @Override // android.bluetooth.le.ScanCallback
                    public void onScanResult(int callbackType, final ScanResult result) {
                        super.onScanResult(callbackType, result);
                        if (bleManager.mStateConnected) {
                            bleManager.stopScan();
                        }
                        Context context2 = bleManager.getContext();
                        final BleManager bleManager2 = bleManager;
                        ContextKt.checkBluetoothPermissionAndInvoke(context2, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager$mLeScanAllThorDevicesCallback$2$1$onScanResult$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                                BluetoothDevice device;
                                String name;
                                ScanResult scanResult = result;
                                if (scanResult == null || (device = scanResult.getDevice()) == null || (name = device.getName()) == null) {
                                    return;
                                }
                                BleManager bleManager3 = bleManager2;
                                ScanResult scanResult2 = result;
                                bleManager3.mFileLogger.i("AllDeviceSN: " + name, new Object[0]);
                                EventBus.getDefault().post(new BluetoothScanResultEvent(scanResult2));
                                EventBus.getDefault().post(new StopScanBluetoothDevicesEvent(true));
                            }
                        });
                    }
                };
            }
        });
        this.mGattCallback = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager$mGattCallback$2.AnonymousClass1>() { // from class: com.thor.app.managers.BleManager$mGattCallback$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Type inference failed for: r0v0, types: [com.thor.app.managers.BleManager$mGattCallback$2$1] */
            @Override // kotlin.jvm.functions.Function0
            public final AnonymousClass1 invoke() {
                final BleManager bleManager = this.this$0;
                return new BluetoothGattCallback() { // from class: com.thor.app.managers.BleManager$mGattCallback$2.1
                    @Override // android.bluetooth.BluetoothGattCallback
                    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
                        super.onReadRemoteRssi(gatt, rssi, status);
                        bleManager.mFileLogger.i("onReadRemoteRssi: " + rssi + ", " + status, new Object[0]);
                        bleManager.rssiLevel.set(Integer.valueOf(rssi));
                        EventBus.getDefault().post(new BluetoothDeviceRssiEvent(rssi));
                    }

                    @Override // android.bluetooth.BluetoothGattCallback
                    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                        super.onCharacteristicRead(gatt, characteristic, status);
                        bleManager.mFileLogger.i("onCharacteristicRead: " + characteristic + ", " + status, new Object[0]);
                    }

                    @Override // android.bluetooth.BluetoothGattCallback
                    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                        super.onCharacteristicWrite(gatt, characteristic, status);
                        if (UploadFilesService.INSTANCE.getServiceStatus()) {
                            return;
                        }
                        FileLogger fileLogger = bleManager.mFileLogger;
                        int i = bleManager.mStepRequest;
                        byte[] value = characteristic != null ? characteristic.getValue() : null;
                        if (value == null) {
                            value = new byte[0];
                        }
                        fileLogger.i("onCharacteristicWrite: step=" + i + " status=" + status + ", code=" + Hex.bytesToStringUppercase(value), new Object[0]);
                        if (bleManager.mSingleRequestPart == null || characteristic == null) {
                            bleManager.mFileLogger.i("mSingleRequestPart OR characteristic is NULL", new Object[0]);
                        } else {
                            byte[] bArr = bleManager.mSingleRequestPart;
                            Intrinsics.checkNotNull(bArr);
                            if (Arrays.equals(bArr, characteristic.getValue())) {
                                bleManager.writeCharacteristicValue();
                            } else {
                                bleManager.mFileLogger.i("Change mCommandExecuting on FALSE!", new Object[0]);
                                bleManager.mErrorsCount++;
                                bleManager.mCommandExecuting = false;
                            }
                        }
                        bleManager.readRemoteRssi();
                    }

                    @Override // android.bluetooth.BluetoothGattCallback
                    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                        super.onServicesDiscovered(gatt, status);
                        bleManager.mFileLogger.i("onServicesDiscovered: " + status, new Object[0]);
                        if (status == 0) {
                            BleManager bleManager2 = bleManager;
                            Intrinsics.checkNotNull(gatt);
                            bleManager2.mBluetoothGatt = gatt;
                            bleManager.handleCharacteristic(gatt.getServices());
                        }
                    }

                    @Override // android.bluetooth.BluetoothGattCallback
                    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                        super.onDescriptorWrite(gatt, descriptor, status);
                        bleManager.mFileLogger.i("onDescriptorWrite: " + descriptor + ", " + status, new Object[0]);
                    }

                    @Override // android.bluetooth.BluetoothGattCallback
                    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                        super.onCharacteristicChanged(gatt, characteristic);
                        if (UploadFilesService.INSTANCE.getServiceStatus()) {
                            return;
                        }
                        FileLogger fileLogger = bleManager.mFileLogger;
                        byte[] value = characteristic != null ? characteristic.getValue() : null;
                        if (value == null) {
                            value = new byte[0];
                        }
                        fileLogger.i("onCharacteristicChanged: " + Hex.bytesToStringUppercase(value), new Object[0]);
                        bleManager.mOutputStream.write(characteristic != null ? characteristic.getValue() : null);
                        Timber.INSTANCE.d("mUploadingData " + bleManager.mUploadingData, new Object[0]);
                        if (bleManager.mUploadingData) {
                            bleManager.mCommandExecuting = false;
                        }
                    }

                    @Override // android.bluetooth.BluetoothGattCallback
                    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                        super.onDescriptorRead(gatt, descriptor, status);
                        bleManager.mFileLogger.i("onDescriptorRead:  " + descriptor + ", " + status, new Object[0]);
                    }

                    @Override // android.bluetooth.BluetoothGattCallback
                    public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                        super.onPhyRead(gatt, txPhy, rxPhy, status);
                        bleManager.mFileLogger.i("onPhyRead: " + txPhy + ", " + rxPhy + ", " + status, new Object[0]);
                    }

                    @Override // android.bluetooth.BluetoothGattCallback
                    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                        super.onConnectionStateChange(gatt, status, newState);
                        bleManager.mFileLogger.i("onConnectionStateChange: status " + status + ", newState " + newState, new Object[0]);
                        if (newState != 0) {
                            if (newState == 1) {
                                bleManager.mFileLogger.i("onConnectionStateChange: STATE_DISCONNECTING", new Object[0]);
                                return;
                            }
                            if (newState != 2) {
                                if (newState != 3) {
                                    return;
                                }
                                bleManager.mFileLogger.i("onConnectionStateChange: STATE_DISCONNECTING", new Object[0]);
                                return;
                            }
                            bleManager.mFileLogger.i("onConnectionStateChange: STATE_CONNECTED", new Object[0]);
                            bleManager.mStateConnected = true;
                            bleManager.mStateConnecting = false;
                            bleManager.mStateDisconnecting = false;
                            Context context2 = bleManager.getContext();
                            final BleManager bleManager2 = bleManager;
                            ContextKt.checkBluetoothPermissionAndInvoke(context2, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager$mGattCallback$2$1$onConnectionStateChange$1
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
                                    BluetoothGatt bluetoothGatt = bleManager2.mBluetoothGatt;
                                    if (bluetoothGatt != null) {
                                        bluetoothGatt.discoverServices();
                                    }
                                }
                            });
                            return;
                        }
                        bleManager.mFileLogger.i("onConnectionStateChange: STATE_DISCONNECTED", new Object[0]);
                        bleManager.stopReadBluetoothRssiJob();
                        bleManager.clear();
                        bleManager.disableNotification();
                        bleManager.mStateConnected = false;
                        bleManager.mStateConnecting = false;
                        EventBus.getDefault().post(new BluetoothDeviceConnectionChangedEvent(false));
                        Context context3 = bleManager.getContext();
                        final BleManager bleManager3 = bleManager;
                        ContextKt.checkBluetoothPermissionAndInvoke(context3, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager$mGattCallback$2$1$onConnectionStateChange$2
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
                                BluetoothGatt bluetoothGatt = bleManager3.mBluetoothGatt;
                                if (bluetoothGatt != null) {
                                    bluetoothGatt.close();
                                }
                            }
                        });
                        bleManager.mBluetoothGatt = null;
                        bleManager.mBluetoothWriteCharacteristic = null;
                        bleManager.mBluetoothNotifyCharacteristic = null;
                        if (status == 133) {
                            bleManager.disconnect(false);
                            bleManager.onDeviceConnect();
                        }
                        if (bleManager.mStateDisconnecting) {
                            return;
                        }
                        bleManager.startScan();
                    }
                };
            }
        });
    }

    public final Context getContext() {
        return this.context;
    }

    /* renamed from: isFromOldMode, reason: from getter */
    public final boolean getIsFromOldMode() {
        return this.isFromOldMode;
    }

    public final void setFromOldMode(boolean z) {
        this.isFromOldMode = z;
    }

    public final boolean getFirmwareUploaded() {
        return this.firmwareUploaded;
    }

    public final void setFirmwareUploaded(boolean z) {
        this.firmwareUploaded = z;
    }

    public final CompositeDisposable getMCompositeDisposable() {
        return this.mCompositeDisposable;
    }

    public final CompositeDisposable getMDatabaseCompositeDisposable() {
        return this.mDatabaseCompositeDisposable;
    }

    public final short getServiceActivePresetIndex() {
        return this.serviceActivePresetIndex;
    }

    public final void setServiceActivePresetIndex(short s) {
        this.serviceActivePresetIndex = s;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Handler getHandler() {
        return (Handler) this.handler.getValue();
    }

    public final byte[] getSendByteArray() {
        return this.sendByteArray;
    }

    public final void setSendByteArray(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<set-?>");
        this.sendByteArray = bArr;
    }

    public final Integer getLastSendBlock() {
        return this.lastSendBlock;
    }

    public final void setLastSendBlock(Integer num) {
        this.lastSendBlock = num;
    }

    /* compiled from: BleManager.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/thor/app/managers/BleManager$Companion;", "", "()V", "BLE_BUFFER", "", "CHARACTERISTIC_NORDIC_NOTIFY_UUID", "", "CHARACTERISTIC_NORDIC_WRITE_UUID", "CHARACTERISTIC_UUID", "ERRORS_COUNT_MAX", "GATT_ERROR_CODE", "SERVICE_NORDIC_UUID", "SERVICE_UUID", "TIMER_DURATION", "", Constants.MessagePayloadKeys.FROM, "Lcom/thor/app/managers/BleManager;", "context", "Landroid/content/Context;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final BleManager from(Context context) {
            Context applicationContext = context != null ? context.getApplicationContext() : null;
            Intrinsics.checkNotNull(applicationContext, "null cannot be cast to non-null type com.thor.app.ThorApplication");
            return ((ThorApplication) applicationContext).getBleManager();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ScanCallback getMLeScanCallback() {
        return (ScanCallback) this.mLeScanCallback.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ScanCallback getMLeScanAllThorDevicesCallback() {
        return (ScanCallback) this.mLeScanAllThorDevicesCallback.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BluetoothGattCallback getMGattCallback() {
        return (BluetoothGattCallback) this.mGattCallback.getValue();
    }

    public final Job getReadBluetoothRssiJob() {
        return this.readBluetoothRssiJob;
    }

    public final void setReadBluetoothRssiJob(Job job) {
        this.readBluetoothRssiJob = job;
    }

    public final void startReadBluetoothRssiJob() {
        Job job = this.readBluetoothRssiJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        this.readBluetoothRssiJob = CorotineScopeKt.launchPeriodicAsync(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), 1000L, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.startReadBluetoothRssiJob.1
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
                BleManager.this.readRemoteRssi();
            }
        });
    }

    public final void stopReadBluetoothRssiJob() {
        Job job = this.readBluetoothRssiJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
    }

    public final void connect() {
        this.mFileLogger.i("SERVICE: " + (this.mBluetoothDevice != null) + StringUtils.SPACE + UploadFilesService.INSTANCE.getServiceStatus(), new Object[0]);
        if (this.mBluetoothDevice == null || !UploadFilesService.INSTANCE.getDisconnectedStatus()) {
            return;
        }
        doOnDeviceConnect();
    }

    public final void disconnect(boolean useNull) {
        this.mFileLogger.i("disconnect", new Object[0]);
        this.mStateConnected = false;
        this.mStateConnecting = false;
        this.mStateDisconnecting = true;
        if (useNull) {
            this.mBluetoothDevice = null;
        }
        clear();
        stopScan();
        ContextKt.checkBluetoothPermissionAndInvoke(this.context, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.disconnect.1
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
                BluetoothGatt bluetoothGatt = BleManager.this.mBluetoothGatt;
                if (bluetoothGatt != null) {
                    bluetoothGatt.disconnect();
                }
                BluetoothGatt bluetoothGatt2 = BleManager.this.mBluetoothGatt;
                if (bluetoothGatt2 != null) {
                    bluetoothGatt2.close();
                }
            }
        });
        this.mBluetoothGatt = null;
    }

    public final void disconnectNotNull() {
        this.mFileLogger.i("disconnect", new Object[0]);
        this.mStateConnected = false;
        this.mStateConnecting = false;
        this.mStateDisconnecting = true;
        clear();
        stopScan();
        ContextKt.checkBluetoothPermissionAndInvoke(this.context, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.disconnectNotNull.1
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
                BluetoothGatt bluetoothGatt = BleManager.this.mBluetoothGatt;
                if (bluetoothGatt != null) {
                    bluetoothGatt.disconnect();
                }
                BluetoothGatt bluetoothGatt2 = BleManager.this.mBluetoothGatt;
                if (bluetoothGatt2 != null) {
                    bluetoothGatt2.close();
                }
            }
        });
        this.mBluetoothGatt = null;
    }

    /* renamed from: getBluetoothAdapter, reason: from getter */
    public final BluetoothAdapter getMBluetoothAdapter() {
        return this.mBluetoothAdapter;
    }

    /* renamed from: getBluetoothDevice, reason: from getter */
    public final BluetoothDevice getMBluetoothDevice() {
        return this.mBluetoothDevice;
    }

    /* renamed from: isDiscoveringDevices, reason: from getter */
    public final boolean getIsStartedScan() {
        return this.isStartedScan;
    }

    public final void startScan() {
        if (this.isStartedScan) {
            return;
        }
        final ScanSettings scanSettingsBuild = new ScanSettings.Builder().setScanMode(1).build();
        final ArrayList arrayList = new ArrayList();
        Timber.INSTANCE.i("Ble start scan", new Object[0]);
        ContextKt.checkBluetoothPermissionAndInvoke(this.context, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.startScan.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                BluetoothLeScanner bluetoothLeScanner;
                BluetoothLeScanner bluetoothLeScanner2;
                if (Settings.INSTANCE.isAccessSession() || Settings.INSTANCE.isDeviceLocked()) {
                    String deviceMacAddress = Settings.INSTANCE.getDeviceMacAddress();
                    Timber.INSTANCE.i("Ble start scan mac address " + deviceMacAddress, new Object[0]);
                    if (TextUtils.isEmpty(deviceMacAddress)) {
                        Timber.INSTANCE.i("Ble start scan 1", new Object[0]);
                        BleManager.this.isStartedScan = true;
                        BluetoothAdapter bluetoothAdapter = BleManager.this.mBluetoothAdapter;
                        if (bluetoothAdapter == null || (bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner()) == null) {
                            return;
                        }
                        bluetoothLeScanner.startScan(arrayList, scanSettingsBuild, BleManager.this.getMLeScanCallback());
                        return;
                    }
                    Timber.INSTANCE.i("Ble start scan 2", new Object[0]);
                    BleManager.this.onDeviceConnect();
                    return;
                }
                Timber.INSTANCE.i("Ble start scan 3", new Object[0]);
                BleManager.this.isStartedScan = true;
                BluetoothAdapter bluetoothAdapter2 = BleManager.this.mBluetoothAdapter;
                if (bluetoothAdapter2 == null || (bluetoothLeScanner2 = bluetoothAdapter2.getBluetoothLeScanner()) == null) {
                    return;
                }
                bluetoothLeScanner2.startScan(arrayList, scanSettingsBuild, BleManager.this.getMLeScanAllThorDevicesCallback());
            }
        });
    }

    public final void stopScan() {
        this.mFileLogger.i("stopScan", new Object[0]);
        ContextKt.checkBluetoothPermissionAndInvoke(this.context, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.stopScan.1
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
                BluetoothLeScanner bluetoothLeScanner;
                BluetoothLeScanner bluetoothLeScanner2;
                if (Settings.INSTANCE.isAccessSession()) {
                    BluetoothAdapter bluetoothAdapter = BleManager.this.mBluetoothAdapter;
                    if (bluetoothAdapter == null || (bluetoothLeScanner2 = bluetoothAdapter.getBluetoothLeScanner()) == null) {
                        return;
                    }
                    bluetoothLeScanner2.stopScan(BleManager.this.getMLeScanCallback());
                    return;
                }
                BluetoothAdapter bluetoothAdapter2 = BleManager.this.mBluetoothAdapter;
                if (bluetoothAdapter2 == null || (bluetoothLeScanner = bluetoothAdapter2.getBluetoothLeScanner()) == null) {
                    return;
                }
                bluetoothLeScanner.stopScan(BleManager.this.getMLeScanAllThorDevicesCallback());
            }
        });
        this.isStartedScan = false;
    }

    public final void onDeviceConnect() {
        this.mFileLogger.i("onDeviceConnect", new Object[0]);
        this.mFileLogger.d("mStateConnected: " + this.mStateConnected + ", mBluetoothDevice.notNull: " + (this.mBluetoothDevice != null) + ", mStateConnecting: " + this.mStateConnecting, new Object[0]);
        if (this.mStateConnected || this.mStateConnecting) {
            return;
        }
        this.mStateConnecting = true;
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        BluetoothDevice remoteDevice = bluetoothAdapter != null ? bluetoothAdapter.getRemoteDevice(Settings.INSTANCE.getDeviceMacAddress()) : null;
        this.mBluetoothDevice = remoteDevice;
        this.mFileLogger.i("mBluetoothDevice: " + remoteDevice, new Object[0]);
        doOnDeviceConnect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doOnDeviceConnect() {
        BluetoothDevice device;
        this.mFileLogger.i("doOnDeviceConnect", new Object[0]);
        if (this.mBluetoothDevice == null || this.mStateConnected) {
            this.mFileLogger.i("mBluetoothDevice: " + this.mBluetoothDevice, new Object[0]);
            return;
        }
        name = null;
        String name = null;
        if (this.mBluetoothGatt != null && !this.mStateConnected) {
            FileLogger fileLogger = this.mFileLogger;
            BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
            if (bluetoothGatt != null && (device = bluetoothGatt.getDevice()) != null) {
                name = device.getName();
            }
            fileLogger.i("mBluetoothGatt: " + name, new Object[0]);
            ContextKt.checkBluetoothPermissionAndInvoke(this.context, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.doOnDeviceConnect.1
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
                    BluetoothGatt bluetoothGatt2 = BleManager.this.mBluetoothGatt;
                    if (bluetoothGatt2 != null) {
                        bluetoothGatt2.connect();
                    }
                }
            });
            return;
        }
        FileLogger fileLogger2 = this.mFileLogger;
        BluetoothDevice bluetoothDevice = this.mBluetoothDevice;
        fileLogger2.i("BondState: " + (bluetoothDevice != null ? Integer.valueOf(bluetoothDevice.getBondState()) : null), new Object[0]);
        ContextKt.checkBluetoothPermissionAndInvoke(this.context, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.doOnDeviceConnect.2
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
                BleManager bleManager = BleManager.this;
                BluetoothDevice bluetoothDevice2 = bleManager.mBluetoothDevice;
                bleManager.mBluetoothGatt = bluetoothDevice2 != null ? bluetoothDevice2.connectGatt(BleManager.this.getContext(), false, BleManager.this.getMGattCallback(), 2) : null;
                BluetoothGatt bluetoothGatt2 = BleManager.this.mBluetoothGatt;
                if (bluetoothGatt2 != null) {
                    bluetoothGatt2.connect();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleCharacteristic(List<? extends BluetoothGattService> services) {
        this.mFileLogger.i("handleCharacteristic", new Object[0]);
        if (services != null) {
            for (BluetoothGattService bluetoothGattService : services) {
                if (SERVICE_UUID.equals(bluetoothGattService.getUuid().toString())) {
                    Variables.INSTANCE.setBLOCK_SIZE(200);
                    this.mFileLogger.i("Service: " + bluetoothGattService.getUuid(), new Object[0]);
                    List<BluetoothGattCharacteristic> characteristics = bluetoothGattService.getCharacteristics();
                    Intrinsics.checkNotNullExpressionValue(characteristics, "it.characteristics");
                    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : characteristics) {
                        this.mFileLogger.i("Characteristic: " + bluetoothGattCharacteristic.getUuid(), new Object[0]);
                        if (CHARACTERISTIC_UUID.equals(bluetoothGattCharacteristic.getUuid().toString())) {
                            Log.i("#######@@@@", "NOT NORDIC");
                            this.mBluetoothWriteCharacteristic = bluetoothGattCharacteristic;
                            this.mBluetoothNotifyCharacteristic = bluetoothGattCharacteristic;
                            this.mFileLogger.i("Enable enableNotification", new Object[0]);
                            enableNotification();
                            if (this.isFromOldMode) {
                                EventBus.getDefault().post(new FormatFlashReconnecting());
                            }
                            if (this.isFromOldMode) {
                                return;
                            } else {
                                getHandler().postDelayed(new Runnable() { // from class: com.thor.app.managers.BleManager$handleCharacteristic$lambda$5$lambda$2$$inlined$postDelayed$default$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        this.this$0.mFileLogger.i("Execute init crypto", new Object[0]);
                                        BleManager.executeInitCrypto$default(this.this$0, null, 1, null);
                                    }
                                }, 500L);
                            }
                        }
                    }
                } else if (SERVICE_NORDIC_UUID.equals(bluetoothGattService.getUuid().toString())) {
                    this.mFileLogger.i("Service: " + bluetoothGattService.getUuid(), new Object[0]);
                    Log.i("#######@@@@", "NORDIC");
                    Variables.INSTANCE.setBLOCK_SIZE(200);
                    List<BluetoothGattCharacteristic> characteristics2 = bluetoothGattService.getCharacteristics();
                    Intrinsics.checkNotNullExpressionValue(characteristics2, "it.characteristics");
                    for (BluetoothGattCharacteristic bluetoothGattCharacteristic2 : characteristics2) {
                        this.mFileLogger.i("Characteristic: " + bluetoothGattCharacteristic2.getUuid(), new Object[0]);
                        if (CHARACTERISTIC_NORDIC_WRITE_UUID.equals(bluetoothGattCharacteristic2.getUuid().toString())) {
                            this.mBluetoothWriteCharacteristic = bluetoothGattCharacteristic2;
                        }
                        if (CHARACTERISTIC_NORDIC_NOTIFY_UUID.equals(bluetoothGattCharacteristic2.getUuid().toString())) {
                            this.mBluetoothNotifyCharacteristic = bluetoothGattCharacteristic2;
                            this.mFileLogger.i("Enable enableNotification", new Object[0]);
                            enableNotification();
                            if (this.isFromOldMode) {
                                EventBus.getDefault().post(new FormatFlashReconnecting());
                            }
                            if (this.isFromOldMode) {
                                return;
                            } else {
                                getHandler().postDelayed(new Runnable() { // from class: com.thor.app.managers.BleManager$handleCharacteristic$lambda$5$lambda$4$$inlined$postDelayed$default$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        this.this$0.mFileLogger.i("Execute init crypto", new Object[0]);
                                        BleManager.executeInitCrypto$default(this.this$0, null, 1, null);
                                    }
                                }, 500L);
                            }
                        }
                    }
                } else {
                    continue;
                }
            }
        }
    }

    private final boolean enableNotification() {
        this.mFileLogger.i("enableNotification", new Object[0]);
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.mBluetoothNotifyCharacteristic;
        final BluetoothGattDescriptor descriptor = bluetoothGattCharacteristic != null ? bluetoothGattCharacteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")) : null;
        if (descriptor != null) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            ContextKt.checkBluetoothPermissionAndInvoke(this.context, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.enableNotification.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                    BluetoothGatt bluetoothGatt = BleManager.this.mBluetoothGatt;
                    if (bluetoothGatt != null) {
                        bluetoothGatt.writeDescriptor(descriptor);
                    }
                }
            });
        }
        ContextKt.checkBluetoothPermissionAndInvoke(this.context, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.enableNotification.2
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
                BluetoothGatt bluetoothGatt = BleManager.this.mBluetoothGatt;
                Intrinsics.checkNotNull(bluetoothGatt != null ? Boolean.valueOf(bluetoothGatt.setCharacteristicNotification(BleManager.this.mBluetoothNotifyCharacteristic, true)) : null);
            }
        });
        return this.mStateConnected;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void disableNotification() {
        this.mFileLogger.i("disableNotification", new Object[0]);
        ContextKt.checkBluetoothPermissionAndInvoke(this.context, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.disableNotification.1
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
                BluetoothGatt bluetoothGatt;
                if (BleManager.this.mBluetoothNotifyCharacteristic == null || (bluetoothGatt = BleManager.this.mBluetoothGatt) == null) {
                    return;
                }
                bluetoothGatt.setCharacteristicNotification(BleManager.this.mBluetoothNotifyCharacteristic, false);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void executeInitCrypto$default(BleManager bleManager, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = null;
        }
        bleManager.executeInitCrypto(function0);
    }

    public final void executeInitCrypto(final Function0<Unit> onReInitCrypto) {
        this.mFileLogger.i("executeInitCrypto data " + onReInitCrypto, new Object[0]);
        EventBus.getDefault().post(new BluetoothDeviceConnectionChangedEvent(true));
        this.mOutputStream.reset();
        this.mReInitCrypto++;
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda75
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeInitCrypto$lambda$6(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.io());
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeInitCrypto.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("executeInitCrypto Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                HardwareBleResponse hardwareBleResponse = new HardwareBleResponse(byteArray);
                if (!hardwareBleResponse.getStatus() || hardwareBleResponse.getHardwareProfile() == null) {
                    BleManager.this.mUploadingData = false;
                    if (BleManager.this.mReInitCrypto <= 3) {
                        BleManager.this.mFileLogger.i("executeInitCrypto Response is not correct: " + hardwareBleResponse.getErrorCode(), new Object[0]);
                        Handler handler = BleManager.this.getHandler();
                        final BleManager bleManager = BleManager.this;
                        handler.postDelayed(new Runnable() { // from class: com.thor.app.managers.BleManager$executeInitCrypto$1$invoke$$inlined$postDelayed$default$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                BleManager.executeInitCrypto$default(bleManager, null, 1, null);
                            }
                        }, 500L);
                        return;
                    }
                    BleManager.this.executeHardwareCommandOld();
                    BleManager.this.mReInitCrypto = 0;
                    return;
                }
                FileLogger fileLogger = BleManager.this.mFileLogger;
                byte[] bytes = hardwareBleResponse.getBytes();
                fileLogger.i("Response is correct: " + (bytes != null ? BleHelperKt.toHex(bytes) : null), new Object[0]);
                BleManager.this.mUploadingData = false;
                BleManager bleManager2 = BleManager.this;
                HardwareProfile hardwareProfile = hardwareBleResponse.getHardwareProfile();
                Intrinsics.checkNotNull(hardwareProfile);
                bleManager2.executeHandShake(hardwareProfile, onReInitCrypto);
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda76
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeInitCrypto$lambda$7(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeInitCrypto.2
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
                BleManager.this.mUploadingData = false;
                BleManager.this.mFileLogger.i("executeInitCrypto error: " + th.getMessage(), new Object[0]);
                Handler handler = BleManager.this.getHandler();
                final BleManager bleManager = BleManager.this;
                handler.postDelayed(new Runnable() { // from class: com.thor.app.managers.BleManager$executeInitCrypto$2$invoke$$inlined$postDelayed$default$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BleManager.executeInitCrypto$default(bleManager, null, 1, null);
                    }
                }, 500L);
            }
        };
        compositeDisposable.add(observableSubscribeOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda77
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeInitCrypto$lambda$8(function12, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeInitCrypto$lambda$6(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        HardwareBleRequest hardwareBleRequest = new HardwareBleRequest();
        byte[] bytes = hardwareBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = hardwareBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeInitCrypto$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeInitCrypto$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void executeHandShake(final HardwareProfile hardwareProfile, final Function0<Unit> onReInitCrypto) {
        this.mFileLogger.i("executeHandShake", new Object[0]);
        final byte[] bArrGenerateIVH = EncryptionHelper.INSTANCE.generateIVH();
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda103
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeHandShake$lambda$9(bArrGenerateIVH, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        this.mFileLogger.i("<= S " + BleHelperKt.toHex(bArrGenerateIVH), new Object[0]);
        Log.i("NEW_LOG", "<= S " + BleHelperKt.toHex(bArrGenerateIVH));
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeHandShake.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] bytes = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("executeHandShake Take data: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
                Intrinsics.checkNotNullExpressionValue(bytes, "bytes");
                HandShakeBleResponse handShakeBleResponse = new HandShakeBleResponse(bytes);
                Unit unit = null;
                if (handShakeBleResponse.getStatus()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes2 = handShakeBleResponse.getBytes();
                    if (bytes2 == null) {
                        bytes2 = new byte[0];
                    }
                    fileLogger.i("executeHandShake Response is correct: " + Hex.bytesToStringUppercase(bytes2), new Object[0]);
                    FileLogger fileLogger2 = BleManager.this.mFileLogger;
                    byte[] bytes3 = handShakeBleResponse.getBytes();
                    fileLogger2.i("=> S " + (bytes3 != null ? BleHelperKt.toHex(bytes3) : null), new Object[0]);
                    byte[] bytes4 = handShakeBleResponse.getBytes();
                    Log.i("NEW_LOG", "=> S " + (bytes4 != null ? BleHelperKt.toHex(bytes4) : null));
                    CryptoManager crypto = EncryptionHelper.INSTANCE.getCrypto();
                    byte[] bArr = bArrGenerateIVH;
                    byte[] bytes5 = handShakeBleResponse.getBytes();
                    if (bytes5 == null) {
                        bytes5 = new byte[0];
                    }
                    crypto.coreAesInit(ArraysKt.plus(bArr, bytes5), hardwareProfile.getVersionHardware(), hardwareProfile.getVersionFirmware(), hardwareProfile.getSerialNumber());
                    Function0<Unit> function0 = onReInitCrypto;
                    if (function0 != null) {
                        function0.invoke();
                        unit = Unit.INSTANCE;
                    }
                    if (unit == null) {
                        BleManager.this.lunchCommandAfterCrypto();
                        return;
                    }
                    return;
                }
                BleManager.this.mFileLogger.i("executeHandShake Response is not correct: " + handShakeBleResponse.getErrorCode(), new Object[0]);
                BleManager.executeInitCrypto$default(BleManager.this, null, 1, null);
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda104
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeHandShake$lambda$10(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeHandShake.2
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
                BleManager.this.mFileLogger.i("executeHandShake error: " + th.getMessage(), new Object[0]);
                BleManager.executeInitCrypto$default(BleManager.this, null, 1, null);
            }
        };
        compositeDisposable.add(observableSubscribeOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda105
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeHandShake$lambda$11(function12, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeHandShake$lambda$9(byte[] iv, BleManager this$0) {
        Intrinsics.checkNotNullParameter(iv, "$iv");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.writeCharacteristicValue(new HandShakeBleRequest(iv).getBytes(!UploadFilesService.INSTANCE.getServiceStatus()), TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeHandShake$lambda$10(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeHandShake$lambda$11(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void lunchCommandAfterCrypto() {
        this.mFileLogger.i("lunchCommandAfterCrypto", new Object[0]);
        executeHardwareCommand();
        if (Settings.getIsCheckedEmrgencySituations()) {
            return;
        }
        Observable<Long> observableTimer = Observable.timer(4L, TimeUnit.SECONDS);
        final C03741 c03741 = new Function1<Long, Unit>() { // from class: com.thor.app.managers.BleManager.lunchCommandAfterCrypto.1
            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Long l) {
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Long l) {
                invoke2(l);
                return Unit.INSTANCE;
            }
        };
        Consumer<? super Long> consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.lunchCommandAfterCrypto$lambda$12(c03741, obj);
            }
        };
        final Function1<Throwable, Unit> function1 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.lunchCommandAfterCrypto.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        observableTimer.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.lunchCommandAfterCrypto$lambda$13(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void lunchCommandAfterCrypto$lambda$12(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void lunchCommandAfterCrypto$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void executeReadInstalledSoundPackagesCommand$default(BleManager bleManager, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = new Function1<InstalledSoundPackagesResponse, Unit>() { // from class: com.thor.app.managers.BleManager.executeReadInstalledSoundPackagesCommand.1
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(InstalledSoundPackagesResponse it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(InstalledSoundPackagesResponse installedSoundPackagesResponse) {
                    invoke2(installedSoundPackagesResponse);
                    return Unit.INSTANCE;
                }
            };
        }
        bleManager.executeReadInstalledSoundPackagesCommand(function1);
    }

    public final void executeReadInstalledSoundPackagesCommand(final Function1<? super InstalledSoundPackagesResponse, Unit> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        this.mFileLogger.i("executeReadInstalledSoundPackagesCommand", new Object[0]);
        Observable<InstalledSoundPackagesResponse> observablePrepareReadInstalledSoundPackagesObservable = prepareReadInstalledSoundPackagesObservable();
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        final Function1<InstalledSoundPackagesResponse, Unit> function1 = new Function1<InstalledSoundPackagesResponse, Unit>() { // from class: com.thor.app.managers.BleManager.executeReadInstalledSoundPackagesCommand.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InstalledSoundPackagesResponse installedSoundPackagesResponse) {
                invoke2(installedSoundPackagesResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InstalledSoundPackagesResponse response) {
                if (!response.getStatus()) {
                    BleManager.this.mFileLogger.e("Response is not correct: " + response.getErrorCode(), new Object[0]);
                } else {
                    BleManager bleManager = BleManager.this;
                    Intrinsics.checkNotNullExpressionValue(response, "response");
                    bleManager.handleInstalledSoundPackages(response);
                    action.invoke(response);
                }
            }
        };
        Consumer<? super InstalledSoundPackagesResponse> consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda47
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeReadInstalledSoundPackagesCommand$lambda$14(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeReadInstalledSoundPackagesCommand.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        compositeDisposable.add(observablePrepareReadInstalledSoundPackagesObservable.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda48
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeReadInstalledSoundPackagesCommand$lambda$15(function12, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeReadInstalledSoundPackagesCommand$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeReadInstalledSoundPackagesCommand$lambda$15(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final Observable<InstalledSoundPackagesResponse> prepareReadInstalledSoundPackagesObservable() {
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda90
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.prepareReadInstalledSoundPackagesObservable$lambda$16(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.prepareReadInstalledSoundPackagesObservable.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mFileLogger.i("executeReadInstalledSoundPackagesCommand start", new Object[0]);
            }
        };
        Observable observableDoOnTerminate = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda91
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.prepareReadInstalledSoundPackagesObservable$lambda$17(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda92
            @Override // io.reactivex.functions.Action
            public final void run() {
                BleManager.prepareReadInstalledSoundPackagesObservable$lambda$18(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, InstalledSoundPackagesResponse> function12 = new Function1<ByteArrayOutputStream, InstalledSoundPackagesResponse>() { // from class: com.thor.app.managers.BleManager.prepareReadInstalledSoundPackagesObservable.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final InstalledSoundPackagesResponse invoke(ByteArrayOutputStream outputStream) {
                Intrinsics.checkNotNullParameter(outputStream, "outputStream");
                byte[] byteArray = outputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                return new InstalledSoundPackagesResponse(byteArray);
            }
        };
        Observable<InstalledSoundPackagesResponse> map = observableDoOnTerminate.map(new Function() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda93
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return BleManager.prepareReadInstalledSoundPackagesObservable$lambda$19(function12, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(map, "private fun prepareReadI…ytes)\n            }\n    }");
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource prepareReadInstalledSoundPackagesObservable$lambda$16(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        byte[] bArrFetchCommandRequest = BleHelper.fetchCommandRequest((short) 71);
        this$0.mFileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArrFetchCommandRequest), new Object[0]);
        this$0.writeCharacteristicValue(bArrFetchCommandRequest, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void prepareReadInstalledSoundPackagesObservable$lambda$17(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void prepareReadInstalledSoundPackagesObservable$lambda$18(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("executeReadInstalledSoundPackagesCommand finish", new Object[0]);
        this$0.mOutputStream.reset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final InstalledSoundPackagesResponse prepareReadInstalledSoundPackagesObservable$lambda$19(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (InstalledSoundPackagesResponse) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleInstalledSoundPackages(InstalledSoundPackagesResponse response) {
        this.mFileLogger.i("Response is correct: " + response.getPackages(), new Object[0]);
        ThorDatabase.INSTANCE.from(this.context).installedSoundPackageDao().deleteAllElements();
        for (InstalledSoundPackage installedSoundPackage : response.getPackages().getSoundPackages()) {
            InstalledSoundPackageEntity installedSoundPackageEntity = new InstalledSoundPackageEntity(installedSoundPackage.getPackageId(), installedSoundPackage.getVersionId(), installedSoundPackage.getMode(), false, 8, null);
            this.mFileLogger.i("Insert installed package: " + installedSoundPackageEntity, new Object[0]);
            Completable completableInsert = ThorDatabase.INSTANCE.from(this.context).installedSoundPackageDao().insert(installedSoundPackageEntity);
            Action action = new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda143
                @Override // io.reactivex.functions.Action
                public final void run() {
                    BleManager.handleInstalledSoundPackages$lambda$23$lambda$20(this.f$0);
                }
            };
            final Function1<Throwable, Unit> function1 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager$handleInstalledSoundPackages$1$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable it) {
                    FileLogger fileLogger = this.this$0.mFileLogger;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    fileLogger.e(it);
                }
            };
            this.mDatabaseCompositeDisposable.add(completableInsert.subscribe(action, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda144
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BleManager.handleInstalledSoundPackages$lambda$23$lambda$21(function1, obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleInstalledSoundPackages$lambda$23$lambda$20(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("Success", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleInstalledSoundPackages$lambda$23$lambda$21(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final Observable<ByteArrayOutputStream> executeReadInstalledPresetsCommand() {
        this.mFileLogger.i("executeReadInstalledPresetsCommand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda30
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeReadInstalledPresetsCommand$lambda$24(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable<ByteArrayOutputStream> observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        Intrinsics.checkNotNullExpressionValue(observableSubscribeOn, "observable.subscribeOn(S…s.from(mExecutorService))");
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeReadInstalledPresetsCommand$lambda$24(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        byte[] bArrFetchCommandRequest = BleHelper.fetchCommandRequest((short) 49);
        this$0.mFileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArrFetchCommandRequest), new Object[0]);
        this$0.writeCharacteristicValue(bArrFetchCommandRequest, 4000L);
        return Observable.just(this$0.mOutputStream);
    }

    public final void setBluetoothStateConnection(final boolean isState) {
        this.mBluetoothEnabled = isState;
        ContextKt.checkBluetoothPermissionAndInvoke(this.context, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.setBluetoothStateConnection.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                if (isState) {
                    BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
                    if (bluetoothGatt != null) {
                        bluetoothGatt.connect();
                        return;
                    }
                    return;
                }
                BluetoothGatt bluetoothGatt2 = this.mBluetoothGatt;
                if (bluetoothGatt2 != null) {
                    bluetoothGatt2.disconnect();
                }
            }
        });
    }

    /* renamed from: isConnected, reason: from getter */
    public final boolean getMStateConnected() {
        return this.mStateConnected;
    }

    public final boolean isBleEnabledAndDeviceConnected() {
        this.mFileLogger.i("mStateConnected = " + this.mStateConnected + " and mBluetoothStateConnection = " + this.mBluetoothEnabled, new Object[0]);
        return this.mStateConnected && this.mBluetoothEnabled;
    }

    public final int getRssiLevel() {
        Integer num = this.rssiLevel.get();
        int iAbs = num != null ? Math.abs(num.intValue()) : 100;
        if (iAbs >= 85) {
            return 1;
        }
        boolean z = false;
        if (70 <= iAbs && iAbs < 85) {
            return 2;
        }
        if (54 <= iAbs && iAbs < 70) {
            z = true;
        }
        if (z) {
            return 3;
        }
        return iAbs < 54 ? 4 : 1;
    }

    public final void addCommandDisposable(Disposable disposable) {
        Intrinsics.checkNotNullParameter(disposable, "disposable");
        this.mCompositeDisposable.add(disposable);
    }

    public final void finish() {
        ContextKt.checkBluetoothPermissionAndInvoke(this.context, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.finish.1
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
                BleManager.this.getMCompositeDisposable().clear();
                BluetoothGatt bluetoothGatt = BleManager.this.mBluetoothGatt;
                if (bluetoothGatt != null) {
                    bluetoothGatt.close();
                }
            }
        });
    }

    public final void executeFormatFlashCommand(final boolean showDelaySecond) {
        clear();
        this.mFileLogger.i("executeFormatFlashCommand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda5
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeFormatFlashCommand$lambda$26(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeFormatFlashCommand.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mFileLogger.i("executeFormatFlashCommand start", new Object[0]);
            }
        };
        Observable observableDoOnTerminate = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeFormatFlashCommand$lambda$27(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Action
            public final void run() {
                BleManager.executeFormatFlashCommand$lambda$28(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeFormatFlashCommand.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringLowercase(byteArray), new Object[0]);
                FormatFlashBleResponse formatFlashBleResponse = new FormatFlashBleResponse(byteArray);
                if (formatFlashBleResponse.getStatus()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes = formatFlashBleResponse.getBytes();
                    if (bytes == null) {
                        bytes = new byte[0];
                    }
                    fileLogger.i("Response is correct: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
                    EventBus.getDefault().post(new FormatFlashExecuteEvent(true, showDelaySecond));
                    BleManager.this.mIsFormatFlash = true;
                    return;
                }
                BleManager.this.mFileLogger.e("Response is not correct: " + formatFlashBleResponse.getErrorCode(), new Object[0]);
                EventBus.getDefault().post(new FormatFlashExecuteEvent(false, showDelaySecond));
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeFormatFlashCommand$lambda$29(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeFormatFlashCommand.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                Log.i("GGGGGGGG", "executeFormatFlashCommand error: " + it.getMessage());
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
                EventBus.getDefault().post(new FormatFlashExecuteEvent(false, showDelaySecond));
            }
        };
        compositeDisposable.add(observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeFormatFlashCommand$lambda$30(function13, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeFormatFlashCommand$lambda$26(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        byte[] bArrFetchCommandRequest = BleHelper.fetchCommandRequest((short) 19);
        this$0.mFileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArrFetchCommandRequest), new Object[0]);
        this$0.writeCharacteristicValue(bArrFetchCommandRequest, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeFormatFlashCommand$lambda$27(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeFormatFlashCommand$lambda$28(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("executeFormatFlashCommand finish", new Object[0]);
        this$0.mOutputStream.reset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeFormatFlashCommand$lambda$29(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeFormatFlashCommand$lambda$30(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void executeFormatFlashCommandOld(final Function1<? super Boolean, Unit> result) {
        Intrinsics.checkNotNullParameter(result, "result");
        this.mFileLogger.i("executeFormatFlashCommand", new Object[0]);
        this.mOutputStream.reset();
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda25
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeFormatFlashCommandOld$lambda$31(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeFormatFlashCommandOld.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                Log.i("GGGGGGGG", "Take data: " + Hex.bytesToStringLowercase(byteArray));
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringLowercase(byteArray), new Object[0]);
                if (new FormatFlashBleResponseOld(byteArray).getStatus()) {
                    result.invoke(true);
                } else {
                    result.invoke(false);
                }
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda26
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeFormatFlashCommandOld$lambda$32(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeFormatFlashCommandOld.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
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
                result.invoke(false);
            }
        };
        compositeDisposable.add(observableSubscribeOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda27
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeFormatFlashCommandOld$lambda$33(function12, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeFormatFlashCommandOld$lambda$31(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        byte[] bArrFetchCommandRequestOld = BleHelper.fetchCommandRequestOld((short) 19);
        this$0.mFileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArrFetchCommandRequestOld), new Object[0]);
        this$0.writeCharacteristicValue(bArrFetchCommandRequestOld, 10000L);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeFormatFlashCommandOld$lambda$32(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeFormatFlashCommandOld$lambda$33(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final Observable<ByteArrayOutputStream> executeFormatFlashCommandOldNew() {
        Log.i("GGGGGGGG", "executeFormatFlashCommand");
        this.mOutputStream.reset();
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda41
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeFormatFlashCommandOldNew$lambda$34(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeFormatFlashCommandOldNew$lambda$34(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        byte[] bArrFetchCommandRequestOld = BleHelper.fetchCommandRequestOld((short) 19);
        this$0.mFileLogger.i("GGGGGGGG: " + Hex.bytesToStringUppercase(bArrFetchCommandRequestOld), new Object[0]);
        this$0.writeCharacteristicValue(bArrFetchCommandRequestOld, 50000L);
        return Observable.just(this$0.mOutputStream);
    }

    public final void executeWriteInstalledPresetsCommand(final List<MainPresetPackage> presets) {
        Intrinsics.checkNotNullParameter(presets, "presets");
        if (presets.isEmpty()) {
            return;
        }
        this.mFileLogger.i("executeWriteInstalledPresetsCommand", new Object[0]);
        final InstalledPresets installedPresets = new InstalledPresets((short) 0, (short) 0, null, 7, null);
        installedPresets.setAmount((short) presets.size());
        installedPresets.setCurrentPresetId(this.mActivatedPresetIndex);
        int i = 0;
        for (Object obj : presets) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            MainPresetPackage mainPresetPackage = (MainPresetPackage) obj;
            this.mFileLogger.i("Preset: " + i + ", " + mainPresetPackage, new Object[0]);
            LinkedHashSet<InstalledPreset> presets2 = installedPresets.getPresets();
            Integer packageId = mainPresetPackage.getPackageId();
            Intrinsics.checkNotNull(packageId);
            short sIntValue = (short) packageId.intValue();
            Short modeType = mainPresetPackage.getModeType();
            Intrinsics.checkNotNull(modeType);
            presets2.add(new InstalledPreset(sIntValue, modeType.shortValue(), (short) 0, 4, null));
            i = i2;
        }
        Settings.INSTANCE.saveInstallPresets(installedPresets);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda117
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeWriteInstalledPresetsCommand$lambda$36(this.f$0, installedPresets);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            mOut…(mOutputStream)\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledPresetsCommand.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                WriteInstalledPresetsBleResponse writeInstalledPresetsBleResponse = new WriteInstalledPresetsBleResponse(byteArrayOutputStream.toByteArray());
                if (writeInstalledPresetsBleResponse.getStatus()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes = writeInstalledPresetsBleResponse.getBytes();
                    fileLogger.i("Response is correct: " + (bytes != null ? BleHelperKt.toHex(bytes) : null), new Object[0]);
                } else {
                    BleManager bleManager = BleManager.this;
                    final BleManager bleManager2 = BleManager.this;
                    final List<MainPresetPackage> list = presets;
                    bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledPresetsCommand.2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                            bleManager2.executeWriteInstalledPresetsCommand(list);
                        }
                    });
                }
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda119
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj2) {
                BleManager.executeWriteInstalledPresetsCommand$lambda$37(function1, obj2);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledPresetsCommand.3
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
                BleManager.this.mFileLogger.i("executeWriteInstalledPresetsCommand error: " + th.getMessage(), new Object[0]);
            }
        };
        compositeDisposable.add(observableDefer.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda120
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj2) {
                BleManager.executeWriteInstalledPresetsCommand$lambda$38(function12, obj2);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeWriteInstalledPresetsCommand$lambda$36(BleManager this$0, InstalledPresets installedPresets) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(installedPresets, "$installedPresets");
        this$0.mOutputStream.reset();
        WriteInstalledPresetsBleRequest writeInstalledPresetsBleRequest = new WriteInstalledPresetsBleRequest(installedPresets);
        byte[] bytes = writeInstalledPresetsBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = writeInstalledPresetsBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeWriteInstalledPresetsCommand$lambda$37(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeWriteInstalledPresetsCommand$lambda$38(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public static /* synthetic */ void executeWriteInstalledAddPreset$default(BleManager bleManager, InstalledPresets installedPresets, short s, int i, Object obj) {
        if ((i & 2) != 0) {
            s = 0;
        }
        bleManager.executeWriteInstalledAddPreset(installedPresets, s);
    }

    public final void executeWriteInstalledAddPreset(final InstalledPresets installedPresets, final short activePresetIndex) {
        Intrinsics.checkNotNullParameter(installedPresets, "installedPresets");
        Log.i("FIND_ERROR", "2 executeWriteInstalledAddPreset");
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda134
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeWriteInstalledAddPreset$lambda$39(installedPresets, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledAddPreset.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mFileLogger.i("executeWriteInstalledPresetsCommand start", new Object[0]);
            }
        };
        Observable observableDoOnSubscribe = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda135
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeWriteInstalledAddPreset$lambda$40(function1, obj);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledAddPreset.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                Log.i("FIND_ERROR", "3 subscribe");
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                WriteInstalledPresetsBleResponse writeInstalledPresetsBleResponse = new WriteInstalledPresetsBleResponse(byteArray);
                byte[] bytes = writeInstalledPresetsBleResponse.getBytes();
                Log.i("FIND_ERROR", "4 subscribe response " + (bytes != null ? BleHelperKt.toHex(bytes) : null));
                if (writeInstalledPresetsBleResponse.getStatus()) {
                    Log.i("FIND_ERROR", "5 status " + writeInstalledPresetsBleResponse.getStatus());
                    BleManager bleManager = BleManager.this;
                    bleManager.executeActivatePresetCommand(bleManager.getMActivatedPresetIndex(), new InstalledPreset((short) 0, (short) 0, (short) 0), new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledAddPreset.2.1
                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                            EventBus.getDefault().post(new WriteInstalledPresetsSuccessfulEvent(true));
                            EventBus.getDefault().post(new DoneWritePreset());
                        }
                    });
                    return;
                }
                byte[] bytes2 = writeInstalledPresetsBleResponse.getBytes();
                Log.i("FIND_ERROR", "-4 subscribe else " + (bytes2 != null ? BleHelperKt.toHex(bytes2) : null));
                BleManager.this.mFileLogger.e("Response is not correct: " + writeInstalledPresetsBleResponse.getErrorCode(), new Object[0]);
                FileLogger fileLogger = BleManager.this.mFileLogger;
                byte[] bytes3 = writeInstalledPresetsBleResponse.getBytes();
                fileLogger.i("ERROR Ble ADD PRESET else " + (bytes3 != null ? BleHelperKt.toHex(bytes3) : null), new Object[0]);
                BleManager bleManager2 = BleManager.this;
                final BleManager bleManager3 = BleManager.this;
                final InstalledPresets installedPresets2 = installedPresets;
                final short s = activePresetIndex;
                bleManager2.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledAddPreset.2.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                        bleManager3.executeWriteInstalledAddPreset(installedPresets2, s);
                    }
                });
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda136
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeWriteInstalledAddPreset$lambda$41(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledAddPreset.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                Log.i("FIND_ERROR", "-5 subscribe throw}");
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
                BleManager.this.mFileLogger.i("ERROR BLE ADD PRESET else crush " + it.getMessage(), new Object[0]);
                EventBus.getDefault().post(new FailAddPreset());
            }
        };
        compositeDisposable.add(observableDoOnSubscribe.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda137
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeWriteInstalledAddPreset$lambda$42(function13, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeWriteInstalledAddPreset$lambda$39(InstalledPresets installedPresets, BleManager this$0) {
        Intrinsics.checkNotNullParameter(installedPresets, "$installedPresets");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.writeCharacteristicValue(new WriteInstalledPresetsBleRequest(installedPresets).getBytes(!UploadFilesService.INSTANCE.getServiceStatus()), TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeWriteInstalledAddPreset$lambda$40(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeWriteInstalledAddPreset$lambda$41(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeWriteInstalledAddPreset$lambda$42(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public static /* synthetic */ void executeWriteInstalledPresetsCommand$default(BleManager bleManager, InstalledPresets installedPresets, boolean z, short s, boolean z2, Function0 function0, int i, Object obj) {
        boolean z3 = (i & 2) != 0 ? false : z;
        short s2 = (i & 4) != 0 ? (short) 0 : s;
        if ((i & 8) != 0) {
            z2 = true;
        }
        boolean z4 = z2;
        if ((i & 16) != 0) {
            function0 = null;
        }
        bleManager.executeWriteInstalledPresetsCommand(installedPresets, z3, s2, z4, function0);
    }

    public final void executeWriteInstalledPresetsCommand(final InstalledPresets installedPresets, final boolean isFromDownload, final short activePresetIndex, final boolean postEvent, final Function0<Unit> callFromDownload) {
        Intrinsics.checkNotNullParameter(installedPresets, "installedPresets");
        this.mFileLogger.i("executeWriteInstalledPresetsCommand: " + installedPresets, new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda35
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeWriteInstalledPresetsCommand$lambda$43(installedPresets, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledPresetsCommand.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mFileLogger.i("executeWriteInstalledPresetsCommand start", new Object[0]);
            }
        };
        Observable observableDoOnTerminate = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda36
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeWriteInstalledPresetsCommand$lambda$44(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda37
            @Override // io.reactivex.functions.Action
            public final void run() {
                BleManager.executeWriteInstalledPresetsCommand$lambda$45(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledPresetsCommand.6
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                WriteInstalledPresetsBleResponse writeInstalledPresetsBleResponse = new WriteInstalledPresetsBleResponse(byteArray);
                if (writeInstalledPresetsBleResponse.getStatus()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes = writeInstalledPresetsBleResponse.getBytes();
                    if (bytes == null) {
                        bytes = new byte[0];
                    }
                    fileLogger.i("Response is correct: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
                    if (postEvent && !isFromDownload) {
                        EventBus.getDefault().post(new WriteInstalledPresetsSuccessfulEvent(true));
                    }
                    if (isFromDownload) {
                        Log.i("FIND", "15");
                        BleManager bleManager = BleManager.this;
                        short s = activePresetIndex;
                        InstalledPreset installedPreset = bleManager.mActivatedPreset;
                        final Function0<Unit> function0 = callFromDownload;
                        bleManager.executeActivatePresetCommand(s, installedPreset, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledPresetsCommand.6.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                                Function0<Unit> function02 = function0;
                                if (function02 != null) {
                                    function02.invoke();
                                }
                            }
                        });
                    }
                    EventBus.getDefault().post(new DoneWritePreset());
                    return;
                }
                BleManager.this.mFileLogger.e("Response is not correct: " + writeInstalledPresetsBleResponse.getErrorCode(), new Object[0]);
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda38
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeWriteInstalledPresetsCommand$lambda$46(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledPresetsCommand.7
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        compositeDisposable.add(observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda39
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeWriteInstalledPresetsCommand$lambda$47(function13, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeWriteInstalledPresetsCommand$lambda$43(InstalledPresets installedPresets, BleManager this$0) {
        Intrinsics.checkNotNullParameter(installedPresets, "$installedPresets");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        WriteInstalledPresetsBleRequest writeInstalledPresetsBleRequest = new WriteInstalledPresetsBleRequest(installedPresets);
        byte[] bytes = writeInstalledPresetsBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = writeInstalledPresetsBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeWriteInstalledPresetsCommand$lambda$44(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeWriteInstalledPresetsCommand$lambda$45(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("executeWriteInstalledPresetsCommand finish", new Object[0]);
        this$0.mOutputStream.reset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeWriteInstalledPresetsCommand$lambda$46(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeWriteInstalledPresetsCommand$lambda$47(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final Observable<ByteArrayOutputStream> executeReadDeviceParametersCommand() {
        this.mFileLogger.i("executeReadDeviceParametersCommand", new Object[0]);
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda145
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeReadDeviceParametersCommand$lambda$48(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeReadDeviceParametersCommand$lambda$48(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        byte[] bArrFetchCommandRequest = BleHelper.fetchCommandRequest((short) 9);
        this$0.mFileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArrFetchCommandRequest), new Object[0]);
        this$0.writeCharacteristicValue(bArrFetchCommandRequest, 3500L);
        return Observable.just(this$0.mOutputStream);
    }

    public final Observable<ByteArrayOutputStream> executeReadInstalledSoundPackageRulesCommand(final InstalledSoundPackage installedSoundPackage) {
        Intrinsics.checkNotNullParameter(installedSoundPackage, "installedSoundPackage");
        this.mFileLogger.i("executeReadInstalledSoundPackageRulesCommmand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda28
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeReadInstalledSoundPackageRulesCommand$lambda$49(installedSoundPackage, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable<ByteArrayOutputStream> observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        Intrinsics.checkNotNullExpressionValue(observableSubscribeOn, "observable.subscribeOn(S…s.from(mExecutorService))");
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeReadInstalledSoundPackageRulesCommand$lambda$49(InstalledSoundPackage installedSoundPackage, BleManager this$0) {
        Intrinsics.checkNotNullParameter(installedSoundPackage, "$installedSoundPackage");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ReadInstalledSoundPackageRulesRequest readInstalledSoundPackageRulesRequest = new ReadInstalledSoundPackageRulesRequest(installedSoundPackage);
        byte[] bytes = readInstalledSoundPackageRulesRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = readInstalledSoundPackageRulesRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    public final void executeDeleteInstalledSoundPackageCommand(final short installedSoundPackageId) {
        this.mFileLogger.i("executeDeleteInstalledSoundPackageCommand: " + ((int) installedSoundPackageId), new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda78
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeDeleteInstalledSoundPackageCommand$lambda$50(installedSoundPackageId, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeDeleteInstalledSoundPackageCommand.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mFileLogger.i("executeDeleteInstalledSoundPackageCommand start", new Object[0]);
            }
        };
        Observable observableDoOnTerminate = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda79
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeDeleteInstalledSoundPackageCommand$lambda$51(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda80
            @Override // io.reactivex.functions.Action
            public final void run() {
                BleManager.executeDeleteInstalledSoundPackageCommand$lambda$52(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeDeleteInstalledSoundPackageCommand.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                DeleteInstalledSoundPackageBleResponse deleteInstalledSoundPackageBleResponse = new DeleteInstalledSoundPackageBleResponse(byteArray);
                if (deleteInstalledSoundPackageBleResponse.getStatus()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes = deleteInstalledSoundPackageBleResponse.getBytes();
                    if (bytes == null) {
                        bytes = new byte[0];
                    }
                    fileLogger.i("Response is correct: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
                    EventBus.getDefault().post(new DeletedSoundPackageEvent(installedSoundPackageId));
                    return;
                }
                BleManager.this.mFileLogger.e("Response is not correct: " + deleteInstalledSoundPackageBleResponse.getErrorCode(), new Object[0]);
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda81
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeDeleteInstalledSoundPackageCommand$lambda$53(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeDeleteInstalledSoundPackageCommand.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        compositeDisposable.add(observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda82
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeDeleteInstalledSoundPackageCommand$lambda$54(function13, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeDeleteInstalledSoundPackageCommand$lambda$50(short s, BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DeleteInstalledSoundPackageBleRequest deleteInstalledSoundPackageBleRequest = new DeleteInstalledSoundPackageBleRequest(s);
        byte[] bytes = deleteInstalledSoundPackageBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = deleteInstalledSoundPackageBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeDeleteInstalledSoundPackageCommand$lambda$51(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeDeleteInstalledSoundPackageCommand$lambda$52(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("executeDeleteInstalledSoundPackageCommand finish", new Object[0]);
        this$0.mOutputStream.reset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeDeleteInstalledSoundPackageCommand$lambda$53(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeDeleteInstalledSoundPackageCommand$lambda$54(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final Observable<ByteArrayOutputStream> executeReadCanInfoCommand() {
        this.mFileLogger.i("executeReadCanInfoCommand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda114
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeReadCanInfoCommand$lambda$55(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable<ByteArrayOutputStream> observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        Intrinsics.checkNotNullExpressionValue(observableSubscribeOn, "observable.subscribeOn(S…s.from(mExecutorService))");
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeReadCanInfoCommand$lambda$55(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ReadCanInfoBleRequest readCanInfoBleRequest = new ReadCanInfoBleRequest();
        byte[] bytes = readCanInfoBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = readCanInfoBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void executeActivatePresetCommand$default(BleManager bleManager, short s, InstalledPreset installedPreset, Function0 function0, int i, Object obj) {
        if ((i & 4) != 0) {
            function0 = null;
        }
        bleManager.executeActivatePresetCommand(s, installedPreset, function0);
    }

    public final void executeActivatePresetCommand(final short index, final InstalledPreset preset, final Function0<Unit> callFromDownload) {
        Intrinsics.checkNotNullParameter(preset, "preset");
        Log.i("FIND_ERROR", "6 executeActivatePresetCommand");
        isBleEnabledAndDeviceConnected();
        this.mFileLogger.i("executeActivatePresetCommand", new Object[0]);
        setActivatedPresetIndex(index);
        setActivatedPreset(preset);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda123
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeActivatePresetCommand$lambda$56(index, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeActivatePresetCommand.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mFileLogger.i("executeActivatePresetCommand start", new Object[0]);
            }
        };
        Observable observableDoOnTerminate = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda124
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeActivatePresetCommand$lambda$57(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda125
            @Override // io.reactivex.functions.Action
            public final void run() {
                BleManager.executeActivatePresetCommand$lambda$58(this.f$0);
            }
        });
        final AnonymousClass3 anonymousClass3 = new AnonymousClass3(callFromDownload, preset, index);
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda126
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeActivatePresetCommand$lambda$59(anonymousClass3, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeActivatePresetCommand.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                Log.i("FIND_ERROR", "-8  throw " + (callFromDownload != null));
                if (callFromDownload != null) {
                    return;
                }
                Log.i("FIND_ERROR", "-9  throw executeInitCrypto start");
                BleManager bleManager = this;
                final BleManager bleManager2 = this;
                final short s = index;
                final InstalledPreset installedPreset = preset;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.executeActivatePresetCommand.4.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                        Log.i("FIND_ERROR", "-10  throw executeInitCrypto end");
                        BleManager.executeActivatePresetCommand$default(bleManager2, s, installedPreset, null, 4, null);
                    }
                });
                FileLogger fileLogger = this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        compositeDisposable.add(observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda127
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeActivatePresetCommand$lambda$60(function12, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeActivatePresetCommand$lambda$56(short s, BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivatePresetBleRequest activatePresetBleRequest = new ActivatePresetBleRequest(s);
        byte[] bytes = activatePresetBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = activatePresetBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeActivatePresetCommand$lambda$57(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeActivatePresetCommand$lambda$58(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("executeActivatePresetCommand finish", new Object[0]);
        this$0.mOutputStream.reset();
    }

    /* compiled from: BleManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Ljava/io/ByteArrayOutputStream;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.BleManager$executeActivatePresetCommand$3, reason: invalid class name */
    static final class AnonymousClass3 extends Lambda implements Function1<ByteArrayOutputStream, Unit> {
        final /* synthetic */ Function0<Unit> $callFromDownload;
        final /* synthetic */ short $index;
        final /* synthetic */ InstalledPreset $preset;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(Function0<Unit> function0, InstalledPreset installedPreset, short s) {
            super(1);
            this.$callFromDownload = function0;
            this.$preset = installedPreset;
            this.$index = s;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
            invoke2(byteArrayOutputStream);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
            Log.i("FIND_ERROR", "7 executeActivatePresetCommand subscribe");
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
            ActivatePresetBleResponse activatePresetBleResponse = new ActivatePresetBleResponse(byteArray);
            byte[] bytes = activatePresetBleResponse.getBytes();
            Log.i("FIND_ERROR", "8  subscribe " + (bytes != null ? BleHelperKt.toHex(bytes) : null));
            if (activatePresetBleResponse.getStatus()) {
                Log.i("FIND_ERROR", "9  status ");
                if (!activatePresetBleResponse.getIsError()) {
                    Log.i("FIND_ERROR", "10  " + (!activatePresetBleResponse.getIsError()) + StringUtils.SPACE);
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes2 = activatePresetBleResponse.getBytes();
                    fileLogger.i("Response is correct: " + (bytes2 != null ? BleHelperKt.toHex(bytes2) : null), new Object[0]);
                    Function0<Unit> function0 = this.$callFromDownload;
                    if (function0 != null) {
                        Log.i("FIND_ERROR", "11 callFromDownload " + (function0 != null) + StringUtils.SPACE);
                        this.$callFromDownload.invoke();
                        return;
                    }
                    return;
                }
                Log.i("FIND_ERROR", "12  DB update ");
                EventBus.getDefault().post(new OnDamagePckEvent(this.$preset.getPackageId()));
                Completable completableUpdateDamagePck = ThorDatabase.INSTANCE.from(BleManager.this.getContext()).installedSoundPackageDao().updateDamagePck(this.$preset.getPackageId());
                final BleManager bleManager = BleManager.this;
                final InstalledPreset installedPreset = this.$preset;
                Action action = new Action() { // from class: com.thor.app.managers.BleManager$executeActivatePresetCommand$3$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Action
                    public final void run() {
                        BleManager.AnonymousClass3.invoke$lambda$0(bleManager, installedPreset);
                    }
                };
                final BleManager bleManager2 = BleManager.this;
                final Function1<Throwable, Unit> function1 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeActivatePresetCommand.3.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                        invoke2(th);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(Throwable it) {
                        FileLogger fileLogger2 = bleManager2.mFileLogger;
                        Intrinsics.checkNotNullExpressionValue(it, "it");
                        fileLogger2.e(it);
                    }
                };
                BleManager.this.getMDatabaseCompositeDisposable().add(completableUpdateDamagePck.subscribe(action, new Consumer() { // from class: com.thor.app.managers.BleManager$executeActivatePresetCommand$3$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        BleManager.AnonymousClass3.invoke$lambda$1(function1, obj);
                    }
                }));
                return;
            }
            Log.i("FIND_ERROR", "-9  status false  ");
            BleManager bleManager3 = BleManager.this;
            final BleManager bleManager4 = BleManager.this;
            final short s = this.$index;
            final InstalledPreset installedPreset2 = this.$preset;
            final Function0<Unit> function02 = this.$callFromDownload;
            bleManager3.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.executeActivatePresetCommand.3.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                    Log.i("FIND_ERROR", "-10  init crypto  ");
                    bleManager4.executeActivatePresetCommand(s, installedPreset2, function02);
                }
            });
            BleManager.this.mFileLogger.e("Cant start active preset", new Object[0]);
            BleManager.this.mFileLogger.e("Response is not correct: " + activatePresetBleResponse.getErrorCode(), new Object[0]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(BleManager this$0, InstalledPreset preset) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(preset, "$preset");
            this$0.mFileLogger.i("damage pck preset: " + ((int) preset.getPackageId()), new Object[0]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeActivatePresetCommand$lambda$59(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeActivatePresetCommand$lambda$60(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void executeWriteInstalledSoundPackageRules(final InstalledPresetRules installedPresetRules, final PresetRule presetRule) {
        Intrinsics.checkNotNullParameter(installedPresetRules, "installedPresetRules");
        Intrinsics.checkNotNullParameter(presetRule, "presetRule");
        this.mFileLogger.i("executeWriteInstalledSoundPackageRules", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda58
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeWriteInstalledSoundPackageRules$lambda$61(this.f$0, installedPresetRules, presetRule);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            mOut…(mOutputStream)\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledSoundPackageRules.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mFileLogger.i("executeWriteInstalledSoundPackageRules start", new Object[0]);
            }
        };
        Observable observableDoOnTerminate = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda59
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeWriteInstalledSoundPackageRules$lambda$62(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda60
            @Override // io.reactivex.functions.Action
            public final void run() {
                BleManager.executeWriteInstalledSoundPackageRules$lambda$63(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledSoundPackageRules.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                WriteInstalledSoundPackageRulesResponse writeInstalledSoundPackageRulesResponse = new WriteInstalledSoundPackageRulesResponse(byteArray);
                if (writeInstalledSoundPackageRulesResponse.getStatus()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes = writeInstalledSoundPackageRulesResponse.getBytes();
                    if (bytes == null) {
                        bytes = new byte[0];
                    }
                    fileLogger.i("Response is correct: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
                    return;
                }
                BleManager.this.mFileLogger.e("Response is not correct: " + writeInstalledSoundPackageRulesResponse.getErrorCode(), new Object[0]);
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda61
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeWriteInstalledSoundPackageRules$lambda$64(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledSoundPackageRules.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        compositeDisposable.add(observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda62
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeWriteInstalledSoundPackageRules$lambda$65(function13, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeWriteInstalledSoundPackageRules$lambda$61(BleManager this$0, InstalledPresetRules installedPresetRules, PresetRule presetRule) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(installedPresetRules, "$installedPresetRules");
        Intrinsics.checkNotNullParameter(presetRule, "$presetRule");
        this$0.mOutputStream.reset();
        WriteInstalledSoundPackageRulesRequest writeInstalledSoundPackageRulesRequest = new WriteInstalledSoundPackageRulesRequest(installedPresetRules, presetRule);
        byte[] bytes = writeInstalledSoundPackageRulesRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = writeInstalledSoundPackageRulesRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeWriteInstalledSoundPackageRules$lambda$62(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeWriteInstalledSoundPackageRules$lambda$63(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("executeWriteInstalledSoundPackageRules finish", new Object[0]);
        this$0.mOutputStream.reset();
        Log.i("ERRORS", "RRR finish");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeWriteInstalledSoundPackageRules$lambda$64(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeWriteInstalledSoundPackageRules$lambda$65(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: BleManager.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.BleManager$executeWriteInstalledSoundPackageRulesStop$1, reason: invalid class name and case insensitive filesystem */
    static final class C03711 extends Lambda implements Function0<Unit> {
        final /* synthetic */ InstalledPresetRules $installedPresetRules;
        final /* synthetic */ PresetRule $presetRule;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03711(InstalledPresetRules installedPresetRules, PresetRule presetRule) {
            super(0);
            this.$installedPresetRules = installedPresetRules;
            this.$presetRule = presetRule;
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            BleManager.this.mFileLogger.i("executeWriteInstalledSoundPackageRules", new Object[0]);
            final BleManager bleManager = BleManager.this;
            final InstalledPresetRules installedPresetRules = this.$installedPresetRules;
            final PresetRule presetRule = this.$presetRule;
            Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$executeWriteInstalledSoundPackageRulesStop$1$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return BleManager.C03711.invoke$lambda$0(bleManager, installedPresetRules, presetRule);
                }
            });
            Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n                …tputStream)\n            }");
            CompositeDisposable mCompositeDisposable = BleManager.this.getMCompositeDisposable();
            Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(BleManager.this.mExecutorService));
            final BleManager bleManager2 = BleManager.this;
            final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledSoundPackageRulesStop.1.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                    invoke2(disposable);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Disposable disposable) {
                    bleManager2.mFileLogger.i("executeWriteInstalledSoundPackageRules start", new Object[0]);
                }
            };
            Observable observableDoOnSubscribe = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$executeWriteInstalledSoundPackageRulesStop$1$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BleManager.C03711.invoke$lambda$1(function1, obj);
                }
            });
            final BleManager bleManager3 = BleManager.this;
            Observable observableDoOnTerminate = observableDoOnSubscribe.doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$executeWriteInstalledSoundPackageRulesStop$1$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Action
                public final void run() {
                    BleManager.C03711.invoke$lambda$2(bleManager3);
                }
            });
            final BleManager bleManager4 = BleManager.this;
            final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledSoundPackageRulesStop.1.3
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                    invoke2(byteArrayOutputStream);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    bleManager4.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                    WriteInstalledSoundPackageRulesResponse writeInstalledSoundPackageRulesResponse = new WriteInstalledSoundPackageRulesResponse(byteArray);
                    if (writeInstalledSoundPackageRulesResponse.getStatus()) {
                        FileLogger fileLogger = bleManager4.mFileLogger;
                        byte[] bytes = writeInstalledSoundPackageRulesResponse.getBytes();
                        if (bytes == null) {
                            bytes = new byte[0];
                        }
                        fileLogger.i("Response is correct: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
                        return;
                    }
                    bleManager4.mFileLogger.e("Response is not correct: " + writeInstalledSoundPackageRulesResponse.getErrorCode(), new Object[0]);
                    Log.i("ERRORS", "executeWriteInstalledSoundPackageRulesStop " + writeInstalledSoundPackageRulesResponse.getErrorCode());
                }
            };
            Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$executeWriteInstalledSoundPackageRulesStop$1$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BleManager.C03711.invoke$lambda$3(function12, obj);
                }
            };
            final BleManager bleManager5 = BleManager.this;
            final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeWriteInstalledSoundPackageRulesStop.1.4
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable it) {
                    FileLogger fileLogger = bleManager5.mFileLogger;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    fileLogger.e(it);
                    Log.i("ERRORS", "executeWriteInstalledSoundPackageRulesStop " + it.getMessage());
                }
            };
            mCompositeDisposable.add(observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$executeWriteInstalledSoundPackageRulesStop$1$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BleManager.C03711.invoke$lambda$4(function13, obj);
                }
            }));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final ObservableSource invoke$lambda$0(BleManager this$0, InstalledPresetRules installedPresetRules, PresetRule presetRule) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(installedPresetRules, "$installedPresetRules");
            Intrinsics.checkNotNullParameter(presetRule, "$presetRule");
            this$0.mOutputStream.reset();
            WriteInstalledSoundPackageRulesRequest writeInstalledSoundPackageRulesRequest = new WriteInstalledSoundPackageRulesRequest(installedPresetRules, presetRule);
            byte[] bytes = writeInstalledSoundPackageRulesRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
            FileLogger fileLogger = this$0.mFileLogger;
            byte[] bArr = writeInstalledSoundPackageRulesRequest.getDeсryptoMessage();
            if (bArr == null) {
                bArr = new byte[0];
            }
            fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
            this$0.writeCharacteristicValue(bytes, BleManager.TIMER_DURATION);
            return Observable.just(this$0.mOutputStream);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(BleManager this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.mFileLogger.i("executeWriteInstalledSoundPackageRules finish", new Object[0]);
            this$0.mOutputStream.reset();
            Log.i("ERRORS", "executeWriteInstalledSoundPackageRulesStop finish");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }
    }

    public final void executeWriteInstalledSoundPackageRulesStop(InstalledPresetRules installedPresetRules, PresetRule presetRule) {
        Intrinsics.checkNotNullParameter(installedPresetRules, "installedPresetRules");
        Intrinsics.checkNotNullParameter(presetRule, "presetRule");
        this.mCompositeDisposable.clear();
        executeInitCrypto(new C03711(installedPresetRules, presetRule));
    }

    private final void executeUpdateFirmware(byte[] bytes) {
        this.mFileLogger.i("start executeUpdateFirmware: " + TimeHelper.getMinutesWithSeconds(), new Object[0]);
        if (bytes == null) {
            return;
        }
        if (this.mErrorsCount == 10) {
            this.mFileLogger.e("Errors count: %s", Integer.valueOf(this.mErrorsCount));
            this.mCommandExecuting = false;
            return;
        }
        sendUpdateFirmwareProgressEvent();
        this.mOutputStream.reset();
        this.mStepsRequest = (int) Math.ceil(bytes.length / 20.0d);
        this.mStepRequest = 0;
        this.mInputByteArrays = BleHelper.INSTANCE.splitByteArray(bytes, 20);
        writeCharacteristicValue();
        this.mCommandExecuting = true;
        this.mTimer = System.currentTimeMillis() + TIMER_DURATION;
        while (this.mCommandExecuting) {
            checkCrcForUpdatingFirmware();
            if (this.mTimer < System.currentTimeMillis()) {
                this.mFileLogger.i("!!! Timer rule worked !!!", new Object[0]);
                this.mCommandExecuting = false;
            }
        }
        this.mFileLogger.i("end executeUpdateFirmware: " + TimeHelper.getMinutesWithSeconds(), new Object[0]);
    }

    private final void checkCrcForUpdatingFirmware() {
        if (this.mStepRequest == this.mStepsRequest) {
            requestHighPriority();
            if (BleHelper.INSTANCE.checkCrcOld(this.mOutputStream.toByteArray())) {
                handleWriteFirmwareResponse();
            }
        }
    }

    private final void sendUpdateFirmwareProgressEvent() {
        List<byte[]> blocks;
        this.mFileLogger.i("sendUpdateFirmwareProgressEvent: block %s", Integer.valueOf(this.mCurrentBlock));
        double d = 100;
        WriteFirmwareBleRequest writeFirmwareBleRequest = this.mFirmwareRequest;
        Double dValueOf = (writeFirmwareBleRequest == null || (blocks = writeFirmwareBleRequest.getBlocks()) == null) ? null : Double.valueOf(blocks.size());
        Intrinsics.checkNotNull(dValueOf);
        EventBus.getDefault().post(new UpdateFirmwareProgressEvent((int) Math.ceil((d / dValueOf.doubleValue()) * this.mCurrentBlock)));
    }

    private final void handleWriteFirmwareResponse() {
        FileLogger fileLogger = this.mFileLogger;
        String strBytesToStringLowercase = Hex.bytesToStringLowercase(this.mOutputStream.toByteArray());
        Intrinsics.checkNotNullExpressionValue(strBytesToStringLowercase, "bytesToStringLowercase(m…tputStream.toByteArray())");
        fileLogger.i("handleWriteFirmwareResponse: %s", strBytesToStringLowercase);
        WriteFirmwareBlockBleResponse writeFirmwareBlockBleResponse = new WriteFirmwareBlockBleResponse(this.mOutputStream.toByteArray());
        this.mCommandExecuting = false;
        if (writeFirmwareBlockBleResponse.isSuccessful()) {
            this.mCurrentBlock++;
            this.mErrorsCount = 0;
        }
    }

    public final void executeUpdateFirmware(byte[] file, final short blockNumber) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.mFileLogger.i("executeUpdateFirmware", new Object[0]);
        clear();
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        Short shValueOf = hardwareProfile != null ? Short.valueOf(hardwareProfile.getVersionHardware()) : null;
        Intrinsics.checkNotNull(shValueOf);
        short sShortValue = shValueOf.shortValue();
        FirmwareProfile firmwareProfile = Settings.INSTANCE.getFirmwareProfile();
        Short shValueOf2 = firmwareProfile != null ? Short.valueOf((short) firmwareProfile.getVersionFW()) : null;
        Intrinsics.checkNotNull(shValueOf2);
        this.mFirmwareRequest = new WriteFirmwareBleRequest(file, sShortValue, shValueOf2.shortValue());
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda83
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeUpdateFirmware$lambda$66(this.f$0, blockNumber);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            mOut…)\n            }\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeUpdateFirmware.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mFileLogger.i("executeUpdateFirmware start", new Object[0]);
                BleManager.this.mUploadingData = true;
            }
        };
        Observable observableDoOnTerminate = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda84
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUpdateFirmware$lambda$67(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda86
            @Override // io.reactivex.functions.Action
            public final void run() {
                BleManager.executeUpdateFirmware$lambda$68(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeUpdateFirmware.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                WriteFirmwareBlockBleResponse writeFirmwareBlockBleResponse = new WriteFirmwareBlockBleResponse(byteArray);
                if (writeFirmwareBlockBleResponse.isSuccessful()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes = writeFirmwareBlockBleResponse.getBytes();
                    fileLogger.i("Response is correct: " + (bytes != null ? BleHelperKt.toHex(bytes) : null), new Object[0]);
                    EventBus.getDefault().post(new UpdateFirmwareSuccessfulEvent());
                    return;
                }
                EventBus.getDefault().post(new UpdateFirmwareErrorEvent());
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda87
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUpdateFirmware$lambda$69(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeUpdateFirmware.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        compositeDisposable.add(observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda88
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUpdateFirmware$lambda$70(function13, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeUpdateFirmware$lambda$66(BleManager this$0, short s) {
        Observable observableSubscribeOn;
        List<byte[]> blocks;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mOutputStream.reset();
        try {
            this$0.mCurrentBlock = s;
            this$0.mErrorsCount = 0;
            while (true) {
                int i = this$0.mCurrentBlock;
                WriteFirmwareBleRequest writeFirmwareBleRequest = this$0.mFirmwareRequest;
                byte[] bArr = null;
                List<byte[]> blocks2 = writeFirmwareBleRequest != null ? writeFirmwareBleRequest.getBlocks() : null;
                Intrinsics.checkNotNull(blocks2);
                if (i >= blocks2.size() || !this$0.mUploadingData) {
                    break;
                }
                WriteFirmwareBleRequest writeFirmwareBleRequest2 = this$0.mFirmwareRequest;
                if (writeFirmwareBleRequest2 != null && (blocks = writeFirmwareBleRequest2.getBlocks()) != null) {
                    bArr = blocks.get(this$0.mCurrentBlock);
                }
                this$0.executeUpdateFirmware(bArr);
                if (this$0.mErrorsCount == 10) {
                    this$0.mFileLogger.e("Errors count: %s", Integer.valueOf(this$0.mErrorsCount));
                    EventBus.getDefault().post(new UpdateFirmwareErrorEvent());
                    break;
                }
            }
            observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        } catch (Exception unused) {
            observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        }
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUpdateFirmware$lambda$67(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUpdateFirmware$lambda$68(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("executeUpdateFirmware finish", new Object[0]);
        this$0.mUploadingData = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUpdateFirmware$lambda$69(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUpdateFirmware$lambda$70(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final Observable<ByteArrayOutputStream> executeWriteDriveModes(final WriteDriveModesBleRequest request) {
        Intrinsics.checkNotNullParameter(request, "request");
        this.mFileLogger.i("executeWriteDriveModes", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda67
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeWriteDriveModes$lambda$71(request, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable<ByteArrayOutputStream> observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        Intrinsics.checkNotNullExpressionValue(observableSubscribeOn, "observable.subscribeOn(S…s.from(mExecutorService))");
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeWriteDriveModes$lambda$71(WriteDriveModesBleRequest request, BleManager this$0) {
        Intrinsics.checkNotNullParameter(request, "$request");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        byte[] bytes = request.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = request.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    public final Observable<ByteArrayOutputStream> executeReadDriveModes() {
        this.mFileLogger.i("executeReadDriveModes", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda73
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeReadDriveModes$lambda$72(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable<ByteArrayOutputStream> observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        Intrinsics.checkNotNullExpressionValue(observableSubscribeOn, "observable.subscribeOn(S…s.from(mExecutorService))");
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeReadDriveModes$lambda$72(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ReadDriveModesBleRequest readDriveModesBleRequest = new ReadDriveModesBleRequest();
        byte[] bytes = readDriveModesBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = readDriveModesBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    public final void executeTurnOnDetectDriveMode() {
        executeControlDetectDriveMode(1);
    }

    public final void executeTurnOffDetectDriveMode() {
        executeControlDetectDriveMode(0);
    }

    public final void executeControlDetectDriveMode(final int activate) {
        this.mFileLogger.i("executeTurnOnDetectDriveMode", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda146
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeControlDetectDriveMode$lambda$73(activate, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableObserveOn = observableDefer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeControlDetectDriveMode.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mFileLogger.i("executeTurnOnDetectDriveMode start", new Object[0]);
            }
        };
        Observable observableDoOnTerminate = observableObserveOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda147
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeControlDetectDriveMode$lambda$74(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda148
            @Override // io.reactivex.functions.Action
            public final void run() {
                BleManager.executeControlDetectDriveMode$lambda$75(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeControlDetectDriveMode.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                ControlDetectDriveModeBleResponse controlDetectDriveModeBleResponse = new ControlDetectDriveModeBleResponse(byteArray);
                if (controlDetectDriveModeBleResponse.getStatus()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes = controlDetectDriveModeBleResponse.getBytes();
                    if (bytes == null) {
                        bytes = new byte[0];
                    }
                    fileLogger.i("Response is correct: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
                    return;
                }
                BleManager.this.mFileLogger.e("Response is not correct: " + controlDetectDriveModeBleResponse.getErrorCode(), new Object[0]);
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda149
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeControlDetectDriveMode$lambda$76(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeControlDetectDriveMode.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        compositeDisposable.add(observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda150
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeControlDetectDriveMode$lambda$77(function13, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeControlDetectDriveMode$lambda$73(int i, BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ControlDetectDriveModeBleRequest controlDetectDriveModeBleRequest = new ControlDetectDriveModeBleRequest(i);
        byte[] bytes = controlDetectDriveModeBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = controlDetectDriveModeBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeControlDetectDriveMode$lambda$74(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeControlDetectDriveMode$lambda$75(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("executeTurnOnDetectDriveMode finish", new Object[0]);
        this$0.mOutputStream.reset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeControlDetectDriveMode$lambda$76(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeControlDetectDriveMode$lambda$77(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void executeUpdateCanConfigurations(WriteCanConfigurationsFileRequest request) {
        Intrinsics.checkNotNullParameter(request, "request");
        this.mFileLogger.i("executeUpdateCanConfigurations", new Object[0]);
        clear();
        this.mCanConfigurationsRequest = request;
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda128
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeUpdateCanConfigurations$lambda$78(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            try …)\n            }\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeUpdateCanConfigurations.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mFileLogger.i("executeUpdateCanConfigurations start", new Object[0]);
                BleManager.this.mUploadingData = true;
            }
        };
        Observable observableDoOnTerminate = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda130
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUpdateCanConfigurations$lambda$79(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda131
            @Override // io.reactivex.functions.Action
            public final void run() {
                BleManager.executeUpdateCanConfigurations$lambda$80(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeUpdateCanConfigurations.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                if (!new WriteCanConfigurationsBlockBleResponse(byteArray).isSuccessful()) {
                    if (!BleManager.this.mCanConfigurationsLastBlockStateSuccessful) {
                        BleManager.this.mFileLogger.d("Last block for can configurations was failed!", new Object[0]);
                        EventBus.getDefault().post(new UpdateCanConfigurationsErrorEvent(false, true));
                        return;
                    } else {
                        BleManager.this.mFileLogger.d("Last block for can configurations was Success!", new Object[0]);
                        EventBus.getDefault().post(new UpdateCanConfigurationsSuccessfulEvent(true));
                        return;
                    }
                }
                EventBus.getDefault().post(new UpdateCanConfigurationsSuccessfulEvent(true));
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda132
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUpdateCanConfigurations$lambda$81(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeUpdateCanConfigurations.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        compositeDisposable.add(observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda133
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUpdateCanConfigurations$lambda$82(function13, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeUpdateCanConfigurations$lambda$78(BleManager this$0) {
        Observable observableSubscribeOn;
        List<byte[]> blocks;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            this$0.mOutputStream.reset();
            this$0.mCurrentBlock = 0;
            this$0.mErrorsCount = 0;
            while (true) {
                int i = this$0.mCurrentBlock;
                WriteCanConfigurationsFileRequest writeCanConfigurationsFileRequest = this$0.mCanConfigurationsRequest;
                byte[] bArr = null;
                List<byte[]> blocks2 = writeCanConfigurationsFileRequest != null ? writeCanConfigurationsFileRequest.getBlocks() : null;
                Intrinsics.checkNotNull(blocks2);
                if (i >= blocks2.size() || !this$0.mUploadingData) {
                    break;
                }
                WriteCanConfigurationsFileRequest writeCanConfigurationsFileRequest2 = this$0.mCanConfigurationsRequest;
                if (writeCanConfigurationsFileRequest2 != null && (blocks = writeCanConfigurationsFileRequest2.getBlocks()) != null) {
                    bArr = blocks.get(this$0.mCurrentBlock);
                }
                this$0.executeUpdateCanConfigurations(bArr);
                if (this$0.mErrorsCount == 10) {
                    this$0.mFileLogger.e("Errors count: %s", Integer.valueOf(this$0.mErrorsCount));
                    EventBus.getDefault().post(new UpdateCanConfigurationsErrorEvent(false, true));
                    break;
                }
            }
            observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        } catch (Exception unused) {
            observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        }
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUpdateCanConfigurations$lambda$79(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUpdateCanConfigurations$lambda$80(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("executeUpdateCanConfigurations finish", new Object[0]);
        this$0.mUploadingData = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUpdateCanConfigurations$lambda$81(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUpdateCanConfigurations$lambda$82(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void executeUpdateCanConfigurations(byte[] bytes) {
        this.mFileLogger.i("start executeUpdateCanConfigurations: " + TimeHelper.getMinutesWithSeconds(), new Object[0]);
        if (bytes == null) {
            return;
        }
        if (this.mErrorsCount == 10) {
            this.mFileLogger.e("Errors count: %s", Integer.valueOf(this.mErrorsCount));
            this.mCommandExecuting = false;
            return;
        }
        this.mOutputStream.reset();
        this.mStepsRequest = (int) Math.ceil(bytes.length / 20.0d);
        this.mStepRequest = 0;
        this.mInputByteArrays = BleHelper.INSTANCE.splitByteArray(bytes, 20);
        writeCharacteristicValue();
        this.mCommandExecuting = true;
        this.mTimer = System.currentTimeMillis() + TIMER_DURATION;
        while (this.mCommandExecuting) {
            checkCrcForUpdatingCanConfigurations();
            if (this.mTimer < System.currentTimeMillis()) {
                this.mFileLogger.w("!!! Timer rule worked !!!", new Object[0]);
                this.mCommandExecuting = false;
            }
        }
        this.mFileLogger.i("end executeUpdateCanConfigurations: " + TimeHelper.getMinutesWithSeconds(), new Object[0]);
    }

    public final Observable<ByteArrayOutputStream> executeReadUniversalDataParametersCommand(final short dataGroup) {
        this.mFileLogger.i("executeReadUniversalDataParametersCommand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda11
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeReadUniversalDataParametersCommand$lambda$83(dataGroup, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable<ByteArrayOutputStream> observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        Intrinsics.checkNotNullExpressionValue(observableSubscribeOn, "observable.subscribeOn(S…s.from(mExecutorService))");
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeReadUniversalDataParametersCommand$lambda$83(short s, BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ReadUniversalDataStatisticBleRequest readUniversalDataStatisticBleRequest = new ReadUniversalDataStatisticBleRequest(s);
        byte[] bytes = readUniversalDataStatisticBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = readUniversalDataStatisticBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    public final Observable<ByteArrayOutputStream> executeWriteLockDeviceCommand(final boolean lock) {
        this.mFileLogger.i("executeWriteLockDeviceCommand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeWriteLockDeviceCommand$lambda$84(lock, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable<ByteArrayOutputStream> observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        Intrinsics.checkNotNullExpressionValue(observableSubscribeOn, "observable.subscribeOn(S…s.from(mExecutorService))");
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeWriteLockDeviceCommand$lambda$84(boolean z, BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        WriteLockDeviceBleRequest writeLockDeviceBleRequest = new WriteLockDeviceBleRequest(z);
        byte[] bytes = writeLockDeviceBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = writeLockDeviceBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    private final void checkCrcForUpdatingCanConfigurations() {
        if (this.mStepRequest == this.mStepsRequest) {
            requestHighPriority();
            if (BleHelper.checkCrc(this.mOutputStream.toByteArray())) {
                handleWriteCanConfigurationsResponse();
            }
        }
    }

    private final void handleWriteCanConfigurationsResponse() {
        this.mFileLogger.i("handleWriteCanConfigurationsResponse: " + Hex.bytesToStringLowercase(this.mOutputStream.toByteArray()), new Object[0]);
        WriteCanConfigurationsBlockBleResponse writeCanConfigurationsBlockBleResponse = new WriteCanConfigurationsBlockBleResponse(this.mOutputStream.toByteArray());
        this.mCommandExecuting = false;
        this.mCanConfigurationsLastBlockStateSuccessful = writeCanConfigurationsBlockBleResponse.isSuccessful();
        if (writeCanConfigurationsBlockBleResponse.isSuccessful()) {
            this.mCurrentBlock++;
            this.mErrorsCount = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeCharacteristicValue(byte[] bytes, long duration) {
        if (bytes == null) {
            return;
        }
        this.mOutputStream.reset();
        this.mSingleRequestPart = null;
        this.mErrorsCount = 0;
        this.mStepRequest = 0;
        this.mCurrentCommandRequest = bytes;
        this.mStepsRequest = (int) Math.ceil(bytes.length / 20.0d);
        this.mInputByteArrays = BleHelper.INSTANCE.splitByteArray(bytes, 20);
        writeCharacteristicValue();
        this.mCommandExecuting = true;
        this.mTimer = System.currentTimeMillis() + duration;
        while (this.mCommandExecuting) {
            if (BleHelper.checkCrc(this.mOutputStream.toByteArray()) || BleHelper.INSTANCE.checkCrcOld(this.mOutputStream.toByteArray())) {
                this.mCommandExecuting = false;
            }
            if (this.mTimer < System.currentTimeMillis()) {
                this.mFileLogger.w("!!! Timer rule worked !!!", new Object[0]);
                this.mCommandExecuting = false;
            }
        }
    }

    private final void writeCharacteristicValue(byte[] bytes) {
        BluetoothGatt bluetoothGatt;
        if (UploadFilesService.INSTANCE.getServiceStatus()) {
            return;
        }
        if (bytes == null) {
            Timber.INSTANCE.w("bytes NULL", new Object[0]);
            return;
        }
        this.mStepRequest++;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.mBluetoothWriteCharacteristic;
        if (bluetoothGattCharacteristic != null) {
            bluetoothGattCharacteristic.setValue(bytes);
        }
        BluetoothGattCharacteristic bluetoothGattCharacteristic2 = this.mBluetoothWriteCharacteristic;
        if (bluetoothGattCharacteristic2 == null || (bluetoothGatt = this.mBluetoothGatt) == null) {
            return;
        }
        bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeCharacteristicValue() {
        List<byte[]> list = this.mInputByteArrays;
        if (list == null) {
            Timber.INSTANCE.w("mInputByteArrays == null", new Object[0]);
            return;
        }
        if (list != null && this.mStepRequest == list.size()) {
            Timber.INSTANCE.w("mStepRequest == mInputByteArrays?.size", new Object[0]);
            return;
        }
        int i = this.mStepRequest;
        List<byte[]> list2 = this.mInputByteArrays;
        Intrinsics.checkNotNull(list2);
        if (i < list2.size()) {
            byte[] bArr = this.mSingleRequestPart;
            List<byte[]> list3 = this.mInputByteArrays;
            Intrinsics.checkNotNull(list3);
            if (Arrays.equals(bArr, list3.get(this.mStepRequest))) {
                Timber.INSTANCE.w("DUPLICATION", new Object[0]);
            }
            List<byte[]> list4 = this.mInputByteArrays;
            Intrinsics.checkNotNull(list4);
            byte[] bArr2 = list4.get(this.mStepRequest);
            this.mSingleRequestPart = bArr2;
            writeCharacteristicValue(bArr2);
            return;
        }
        Timber.INSTANCE.d("!mStepRequest < mInputByteArrays.size", new Object[0]);
    }

    public final void clear() {
        this.mUploadingData = false;
        clearRequests();
    }

    public final void clearRequests() {
        this.mCompositeDisposable.clear();
    }

    public final void clearTasks() {
        this.mExecutorService.shutdownNow();
    }

    public final Observable<ByteArrayOutputStream> applyUpdateFirmware() {
        this.mFileLogger.i("applyUpdateFirmware", new Object[0]);
        this.mOutputStream.reset();
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda16
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.applyUpdateFirmware$lambda$86(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …\n            }\n\n        }");
        observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource applyUpdateFirmware$lambda$86(BleManager this$0) {
        Observable observableJust;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        FirmwareProfile firmwareProfile = Settings.INSTANCE.getFirmwareProfile();
        if (hardwareProfile == null || firmwareProfile == null) {
            observableJust = Observable.just(this$0.mOutputStream);
        } else {
            byte[] bytes = new ApplyUpdateFirmwareBleRequest((short) 1, hardwareProfile.getVersionHardware(), (short) firmwareProfile.getVersionFW()).getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
            this$0.mFileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
            this$0.executeCommandRequest(bytes);
            observableJust = Observable.just(this$0.mOutputStream);
        }
        return observableJust;
    }

    private final void executeCommandRequest(byte[] bytes) {
        this.mFileLogger.i("executeCommandRequest: " + Hex.bytesToStringLowercase(bytes == null ? new byte[0] : bytes), new Object[0]);
        if (bytes == null) {
            return;
        }
        this.mOutputStream.reset();
        this.mCurrentCommandRequest = bytes;
        this.mErrorsCount = 0;
        this.mStepsRequest = (int) Math.ceil(bytes.length / 20.0d);
        this.mStepRequest = 0;
        this.mInputByteArrays = BleHelper.INSTANCE.splitByteArray(bytes, 20);
        writeCharacteristicValue();
        this.mCommandExecuting = true;
        this.mTimer = System.currentTimeMillis() + TIMER_DURATION;
        while (this.mCommandExecuting) {
            if (BleHelper.checkCrc(this.mOutputStream.toByteArray())) {
                this.mCommandExecuting = false;
            }
            if (this.mTimer < System.currentTimeMillis()) {
                this.mFileLogger.w("!!! Timer rule worked !!!", new Object[0]);
                this.mCommandExecuting = false;
            }
        }
    }

    public final Observable<ByteArrayOutputStream> executeStartUploadSoundPackage(final ShopSoundPackage soundPackage) {
        Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
        this.mFileLogger.i("executeStartUploadSoundPackage", new Object[0]);
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda40
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeStartUploadSoundPackage$lambda$87(this.f$0, soundPackage);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            mUpl…ecutorService))\n        }");
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeStartUploadSoundPackage$lambda$87(BleManager this$0, ShopSoundPackage soundPackage) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(soundPackage, "$soundPackage");
        this$0.mUploadingData = true;
        byte[] bytes = new StartUploadSoundPackageBleRequest((short) soundPackage.getId(), (short) soundPackage.getPkgVer()).getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        this$0.mFileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
        this$0.executeCommandRequest(bytes);
        return Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
    }

    public final Observable<ByteArrayOutputStream> executeReDownloadFile() {
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda31
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeReDownloadFile$lambda$88(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeReDownloadFile$lambda$88(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DownloadGetStatusBleRequest downloadGetStatusBleRequest = new DownloadGetStatusBleRequest();
        byte[] bytes = downloadGetStatusBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = downloadGetStatusBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    public final Observable<ByteArrayOutputStream> executeUploadStartFile(final int soundPackageId, final int soundPackageVersion, final byte[] file, final FileType fileType) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda140
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeUploadStartFile$lambda$89(soundPackageId, soundPackageVersion, file, fileType, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …ecutorService))\n        }");
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeUploadStartFile$lambda$89(int i, int i2, byte[] file, FileType fileType, BleManager this$0) {
        Intrinsics.checkNotNullParameter(file, "$file");
        Intrinsics.checkNotNullParameter(fileType, "$fileType");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        UploadStartFileBleRequest uploadStartFileBleRequest = new UploadStartFileBleRequest((short) i, (short) i2, file, fileType);
        byte[] bytes = uploadStartFileBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = uploadStartFileBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
    }

    public final void executeUploadSoundSamples(final ShopSoundPackage soundPackage, byte[] file, final short blockNumber) {
        Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
        Intrinsics.checkNotNullParameter(file, "file");
        this.mFileLogger.i("executeUploadSoundSamples", new Object[0]);
        this.mUploadSoundFileRequest = new UploadSoundPackageFileBleRequest((short) 97, (short) soundPackage.getId(), (short) soundPackage.getPkgVer(), file);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda51
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeUploadSoundSamples$lambda$90(this.f$0, blockNumber, soundPackage);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            mOut…)\n            }\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadSoundSamples.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mUploadingData = true;
                BleManager.this.mFileLogger.i("executeUploadSoundSamples start", new Object[0]);
            }
        };
        Observable observableDoOnTerminate = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda52
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadSoundSamples$lambda$91(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda53
            @Override // io.reactivex.functions.Action
            public final void run() {
                BleManager.executeUploadSoundSamples$lambda$92(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadSoundSamples.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                UploadSoundPackageFileBleRequest uploadSoundPackageFileBleRequest = BleManager.this.mUploadSoundFileRequest;
                Short shValueOf = uploadSoundPackageFileBleRequest != null ? Short.valueOf(uploadSoundPackageFileBleRequest.getCommand()) : null;
                Intrinsics.checkNotNull(shValueOf);
                WriteSoundPackageFileBlockBleResponse writeSoundPackageFileBlockBleResponse = new WriteSoundPackageFileBlockBleResponse(shValueOf.shortValue(), byteArray);
                if (writeSoundPackageFileBlockBleResponse.isSuccessful()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes = writeSoundPackageFileBlockBleResponse.getBytes();
                    fileLogger.i("Response is correct: " + (bytes != null ? BleHelperKt.toHex(bytes) : null), new Object[0]);
                    EventBus.getDefault().post(new UploadSoundPackageSamplesSuccessfulEvent(true));
                    return;
                }
                EventBus.getDefault().post(new UploadSoundPackageErrorEvent(true, soundPackage));
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda54
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadSoundSamples$lambda$93(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadSoundSamples.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        compositeDisposable.add(observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda56
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadSoundSamples$lambda$94(function13, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeUploadSoundSamples$lambda$90(BleManager this$0, short s, ShopSoundPackage soundPackage) {
        Observable observableSubscribeOn;
        List<byte[]> blocks;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(soundPackage, "$soundPackage");
        this$0.mOutputStream.reset();
        try {
            this$0.mCurrentBlock = s;
            this$0.mErrorsCount = 0;
            while (true) {
                int i = this$0.mCurrentBlock;
                UploadSoundPackageFileBleRequest uploadSoundPackageFileBleRequest = this$0.mUploadSoundFileRequest;
                byte[] bArr = null;
                List<byte[]> blocks2 = uploadSoundPackageFileBleRequest != null ? uploadSoundPackageFileBleRequest.getBlocks() : null;
                Intrinsics.checkNotNull(blocks2);
                if (i >= blocks2.size() || !this$0.mUploadingData) {
                    break;
                }
                UploadSoundPackageFileBleRequest uploadSoundPackageFileBleRequest2 = this$0.mUploadSoundFileRequest;
                if (uploadSoundPackageFileBleRequest2 != null && (blocks = uploadSoundPackageFileBleRequest2.getBlocks()) != null) {
                    bArr = blocks.get(this$0.mCurrentBlock);
                }
                this$0.executeUploadSound(bArr);
                if (this$0.mErrorsCount == 10) {
                    this$0.mFileLogger.e("Errors count: " + this$0.mErrorsCount, new Object[0]);
                    EventBus.getDefault().post(new UploadSoundPackageErrorEvent(true, soundPackage));
                    break;
                }
            }
            observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        } catch (Exception unused) {
            observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        }
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadSoundSamples$lambda$91(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadSoundSamples$lambda$92(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("executeUploadSoundSamples finish", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadSoundSamples$lambda$93(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadSoundSamples$lambda$94(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void executeHardwareCommandOld() {
        this.mFileLogger.i("executeHardwareCommand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda17
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeHardwareCommandOld$lambda$95(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeHardwareCommandOld.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mFileLogger.i("executeHardwareCommand start", new Object[0]);
            }
        };
        Observable observableDoOnTerminate = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda18
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeHardwareCommandOld$lambda$96(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda19
            @Override // io.reactivex.functions.Action
            public final void run() {
                BleManager.executeHardwareCommandOld$lambda$97(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeHardwareCommandOld.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                HardwareBleResponseOld hardwareBleResponseOld = new HardwareBleResponseOld(byteArray);
                if (hardwareBleResponseOld.getStatus()) {
                    BleManager.this.mFileLogger.i("Response is correct: " + hardwareBleResponseOld.getHardwareProfile(), new Object[0]);
                    Settings.saveHardwareProfile(hardwareBleResponseOld.getHardwareProfile());
                    EventBus.getDefault().post(new CryptoInitErrorEvent(true));
                    return;
                }
                BleManager.this.mFileLogger.e("Response is not correct: " + hardwareBleResponseOld.getErrorCode(), new Object[0]);
                BleManager.this.disconnect(false);
                BleManager.this.connect();
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda20
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeHardwareCommandOld$lambda$98(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeHardwareCommandOld.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        compositeDisposable.add(observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda21
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeHardwareCommandOld$lambda$99(function13, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeHardwareCommandOld$lambda$95(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        byte[] bArrFetchCommandRequestOld = BleHelper.fetchCommandRequestOld((short) 1);
        this$0.mFileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArrFetchCommandRequestOld), new Object[0]);
        this$0.writeCharacteristicValue(bArrFetchCommandRequestOld, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeHardwareCommandOld$lambda$96(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeHardwareCommandOld$lambda$97(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("executeHardwareCommand finish", new Object[0]);
        this$0.mOutputStream.reset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeHardwareCommandOld$lambda$98(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeHardwareCommandOld$lambda$99(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void executeHardwareCommand() {
        this.mFileLogger.i("executeHardwareCommand", new Object[0]);
        if (this.mUploadingData) {
            return;
        }
        this.mUploadingData = true;
        Observable<ByteArrayOutputStream> observableExecuteUploadStartCommand = executeUploadStartCommand(BleCommands.SETTINGS_DAT_FILE_ID);
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        final C03261 c03261 = new C03261();
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda106
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeHardwareCommand$lambda$100(c03261, obj);
            }
        };
        final Function1<Throwable, Unit> function1 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeHardwareCommand.2
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
                BleManager.this.mFileLogger.e("executeHardwareCommand error: " + th, new Object[0]);
                BleManager.this.mUploadingData = false;
                BleManager bleManager = BleManager.this;
                final BleManager bleManager2 = BleManager.this;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.executeHardwareCommand.2.1
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
                        bleManager2.executeHardwareCommand();
                    }
                });
            }
        };
        compositeDisposable.add(observableExecuteUploadStartCommand.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda108
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeHardwareCommand$lambda$101(function1, obj);
            }
        }));
    }

    /* compiled from: BleManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Ljava/io/ByteArrayOutputStream;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.BleManager$executeHardwareCommand$1, reason: invalid class name and case insensitive filesystem */
    static final class C03261 extends Lambda implements Function1<ByteArrayOutputStream, Unit> {
        C03261() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
            invoke2(byteArrayOutputStream);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            Intrinsics.checkNotNullExpressionValue(byteArray, "byte");
            UploadStartBleResponse uploadStartBleResponse = new UploadStartBleResponse(byteArray);
            if (uploadStartBleResponse.getStatus()) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                byte[] bytes = uploadStartBleResponse.getBytes();
                if (bytes == null) {
                    bytes = new byte[0];
                }
                fileLogger.i("executeHardwareCommand Response is correct: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
                Log.i("ERRORS", "executeHardwareCommand Response is correct: " + uploadStartBleResponse.getFileSize());
                if (uploadStartBleResponse.getFileSize() <= 10240 && uploadStartBleResponse.getFileSize() >= 0) {
                    BleManager.this.mUploadingData = false;
                    BleManager.this.executeUploadReadBlock(uploadStartBleResponse.getFileSize(), uploadStartBleResponse.getMaxBlockSize());
                    return;
                } else {
                    BleManager.this.disconnect(false);
                    Handler handler = BleManager.this.getHandler();
                    final BleManager bleManager = BleManager.this;
                    handler.postDelayed(new Runnable() { // from class: com.thor.app.managers.BleManager$executeHardwareCommand$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            BleManager.C03261.invoke$lambda$0(bleManager);
                        }
                    }, 1000L);
                    return;
                }
            }
            BleManager.this.mFileLogger.e("executeHardwareCommand Response is not correct: " + uploadStartBleResponse.getErrorCode(), new Object[0]);
            BleManager.this.mUploadingData = false;
            BleManager bleManager2 = BleManager.this;
            final BleManager bleManager3 = BleManager.this;
            bleManager2.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.executeHardwareCommand.1.2
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
                    bleManager3.executeHardwareCommand();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(BleManager this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.connect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeHardwareCommand$lambda$100(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeHardwareCommand$lambda$101(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void executeUploadReadBlock(final int fileSize, final int maxBlockSize) {
        Log.i("ERRORS", "executeUploadReadBlock  " + fileSize + "  " + maxBlockSize);
        this.mFileLogger.i("executeUploadReadBlock ", new Object[0]);
        if (this.mUploadingData) {
            return;
        }
        this.mUploadingData = true;
        this.mOutputStream.reset();
        final Ref.IntRef intRef = new Ref.IntRef();
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda138
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeUploadReadBlock$lambda$102(this.f$0, fileSize, maxBlockSize, intRef);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            try …)\n            }\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadReadBlock.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                BleManager.this.mFileLogger.i("executeUploadReadBlock Take data: " + Hex.bytesToStringUppercase(byteArrayOutputStream.toByteArray()), new Object[0]);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                if (BleHelper.checkCrc(byteArray)) {
                    BleManager.this.mFileLogger.i("executeUploadReadBlock Response is correct: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                    BleManager.this.mUploadingData = false;
                    BleManager.this.executeUploadStop();
                    return;
                }
                BleManager.this.mUploadingData = false;
                BleManager.this.mFileLogger.i("executeUploadReadBlock Response is not correct: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                BleManager bleManager = BleManager.this;
                final BleManager bleManager2 = BleManager.this;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadReadBlock.1.1
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
                        bleManager2.executeHardwareCommand();
                    }
                });
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda139
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadReadBlock$lambda$103(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadReadBlock.2
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
                BleManager.this.mUploadingData = false;
                BleManager.this.mFileLogger.e("executeUploadReadBlock error: " + th, new Object[0]);
                BleManager bleManager = BleManager.this;
                final BleManager bleManager2 = BleManager.this;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadReadBlock.2.1
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
                        bleManager2.executeHardwareCommand();
                    }
                });
            }
        };
        compositeDisposable.add(observableSubscribeOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda141
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadReadBlock$lambda$104(function12, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeUploadReadBlock$lambda$102(BleManager this$0, int i, int i2, Ref.IntRef tryIndex) {
        Observable observableSubscribeOn;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tryIndex, "$tryIndex");
        try {
            this$0.mCurrentBlock = 0;
            this$0.mErrorsCount = 0;
            this$0.inputByteFile = new byte[0];
            this$0.lastSendBlock = -1;
            double dCeil = Math.ceil(i / i2);
            Log.i("ERRORS", "mTotalBlocks " + dCeil);
            while (this$0.mCurrentBlock < dCeil && this$0.mErrorsCount < 10) {
                Log.i("ERRORS", "mCurrentBlock " + this$0.mCurrentBlock);
                Integer num = this$0.lastSendBlock;
                int i3 = this$0.mCurrentBlock;
                if (num == null || num.intValue() != i3) {
                    this$0.mOutputStream.reset();
                    this$0.mFileLogger.i("executeUploadReadBlock: " + this$0.mCurrentBlock, new Object[0]);
                    this$0.lastSendBlock = Integer.valueOf(this$0.mCurrentBlock);
                    tryIndex.element = 0;
                    UploadReadBlockRequest uploadReadBlockRequest = new UploadReadBlockRequest(i, this$0.mCurrentBlock, i2);
                    this$0.sendByteArray = uploadReadBlockRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
                    FileLogger fileLogger = this$0.mFileLogger;
                    byte[] bArr = uploadReadBlockRequest.getDeсryptoMessage();
                    if (bArr == null) {
                        bArr = new byte[0];
                    }
                    fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
                }
                tryIndex.element++;
                if (tryIndex.element > 5) {
                    break;
                }
                this$0.executeUploadFileNew(this$0.sendByteArray);
            }
        } catch (Exception unused) {
            observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        }
        if (tryIndex.element > 3) {
            return Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        }
        observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadReadBlock$lambda$103(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadReadBlock$lambda$104(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void executeUploadFileNew(byte[] bytes) throws Exception {
        this.mFileLogger.i("executeUploadFileNew: " + Hex.bytesToStringLowercase(bytes == null ? new byte[0] : bytes), new Object[0]);
        if (bytes == null) {
            return;
        }
        if (this.mErrorsCount == 10) {
            this.mFileLogger.e("executeUploadFileNew Errors count: %s", Integer.valueOf(this.mErrorsCount));
            this.mCommandExecuting = false;
            return;
        }
        this.mStepsRequest = (int) Math.ceil(bytes.length / 20.0d);
        if (this.mStepRequest == 0) {
            Timber.INSTANCE.d("mStepRequest already zero", new Object[0]);
        }
        this.mStepRequest = 0;
        this.mInputByteArrays = BleHelper.INSTANCE.splitByteArray(bytes, 20);
        writeCharacteristicValue();
        this.mCommandUploadExecuting = true;
        this.mTimer = System.currentTimeMillis() + TIMER_DURATION;
        while (this.mCommandUploadExecuting) {
            checkCrcForUploadingFileNew();
            if (this.mTimer < System.currentTimeMillis()) {
                throw new Exception("Timer rule worked");
            }
        }
    }

    private final void checkCrcForUploadingFileNew() {
        if (this.mStepRequest == this.mStepsRequest) {
            requestHighPriority();
            if (BleHelper.checkCrc(this.mOutputStream.toByteArray())) {
                handleWriteFileResponseNew();
            }
        }
    }

    private final void handleWriteFileResponseNew() {
        byte[] byteArray = this.mOutputStream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "mOutputStream.toByteArray()");
        UploadReadBlockBleResponse uploadReadBlockBleResponse = new UploadReadBlockBleResponse(byteArray);
        if (uploadReadBlockBleResponse.getStatus()) {
            FileLogger fileLogger = this.mFileLogger;
            byte[] bytes = uploadReadBlockBleResponse.getBytes();
            if (bytes == null) {
                bytes = new byte[0];
            }
            fileLogger.i("executeUploadFileNew Response is correct: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
            byte[] bytesData = uploadReadBlockBleResponse.getBytesData();
            if (bytesData != null) {
                this.inputByteFile = ArraysKt.plus(this.inputByteFile, bytesData);
            }
            this.mCommandUploadExecuting = false;
            this.mCurrentBlock++;
            this.mErrorsCount = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void executeUploadStop() {
        this.mFileLogger.i("executeUploadStop", new Object[0]);
        if (this.mUploadingData) {
            return;
        }
        this.mUploadingData = true;
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda64
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeUploadStop$lambda$106(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …ecutorService))\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadStop.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                String upperCase;
                List<InstalledPreset> listEmptyList;
                List<SoundPreset> soundPreset;
                String deviceId;
                List<SoundPacket> soundPacket;
                BleManager.this.waitTime(100L);
                BleManager.this.mFileLogger.i("executeUploadStop Take data: " + Hex.bytesToStringUppercase(byteArrayOutputStream.toByteArray()), new Object[0]);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                Intrinsics.checkNotNullExpressionValue(bytes, "bytes");
                UploadStopResponse uploadStopResponse = new UploadStopResponse(bytes);
                if (uploadStopResponse.getStatus()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes2 = uploadStopResponse.getBytes();
                    if (bytes2 == null) {
                        bytes2 = new byte[0];
                    }
                    fileLogger.i("executeUploadStop Response is correct: " + Hex.bytesToStringUppercase(bytes2), new Object[0]);
                    BleManager.this.mUploadingData = false;
                    DeviceSettingsModel deviceSettingsModel = new DeviceSettingsModel(BleManager.this.inputByteFile);
                    BleManager.this.mFileLogger.i("executeUploadStop model " + deviceSettingsModel, new Object[0]);
                    BleManager.this.inputByteFile = new byte[0];
                    if (deviceSettingsModel.getHwInfo() == null) {
                        return;
                    }
                    SoundPackets soundPacket2 = deviceSettingsModel.getSoundPacket();
                    if (soundPacket2 != null && (soundPacket = soundPacket2.getSoundPacket()) != null) {
                        BleManager.this.updateInstallPresetDB(soundPacket);
                    }
                    deviceSettingsModel.getCanFile();
                    Settings.INSTANCE.saveDeviceStatus(deviceSettingsModel.getDeviceStatus());
                    HWInfo hwInfo = deviceSettingsModel.getHwInfo();
                    Intrinsics.checkNotNull(hwInfo);
                    short serialNumber = (short) hwInfo.getSerialNumber();
                    HWInfo hwInfo2 = deviceSettingsModel.getHwInfo();
                    Intrinsics.checkNotNull(hwInfo2);
                    short hwVersion = (short) hwInfo2.getHwVersion();
                    HWInfo hwInfo3 = deviceSettingsModel.getHwInfo();
                    Intrinsics.checkNotNull(hwInfo3);
                    short fmVersion = (short) hwInfo3.getFmVersion();
                    DeviceId deviceId2 = deviceSettingsModel.getDeviceId();
                    if (deviceId2 == null || (deviceId = deviceId2.getDeviceId()) == null) {
                        upperCase = null;
                    } else {
                        upperCase = deviceId.toUpperCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                    }
                    HardwareProfile hardwareProfile = new HardwareProfile(serialNumber, fmVersion, hwVersion, String.valueOf(upperCase));
                    SoundPresets soundPreset2 = deviceSettingsModel.getSoundPreset();
                    if (soundPreset2 == null || (soundPreset = soundPreset2.getSoundPreset()) == null || (listEmptyList = ModelsKt.toListPresets(soundPreset)) == null) {
                        listEmptyList = CollectionsKt.emptyList();
                    }
                    LinkedHashSet linkedHashSet = new LinkedHashSet(listEmptyList);
                    DeviceStatus deviceStatus = deviceSettingsModel.getDeviceStatus();
                    InstalledPresets installedPresets = new InstalledPresets(deviceStatus != null ? (short) deviceStatus.getPresetIndex() : (short) 0, (short) 0, linkedHashSet, 2, null);
                    Settings.INSTANCE.setPresets(installedPresets);
                    Settings.saveHardwareProfile(hardwareProfile);
                    Settings.INSTANCE.saveInstallPresets(installedPresets);
                    EventBus.getDefault().post(new StartCheckEmergencySituationsEvent(BleManager.this.mIsFormatFlash));
                    BleManager.this.resetFormatFlashValue();
                    return;
                }
                BleManager.this.mUploadingData = false;
                BleManager.this.mFileLogger.i("executeUploadStop Response is not correct: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
                BleManager bleManager = BleManager.this;
                final BleManager bleManager2 = BleManager.this;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadStop.1.2
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
                        bleManager2.executeUploadStop();
                    }
                });
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda65
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadStop$lambda$107(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadStop.2
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
                BleManager.this.mFileLogger.e("executeUploadStop error: " + th, new Object[0]);
                BleManager.this.mUploadingData = false;
                BleManager bleManager = BleManager.this;
                final BleManager bleManager2 = BleManager.this;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadStop.2.1
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
                        bleManager2.executeUploadStop();
                    }
                });
            }
        };
        compositeDisposable.add(observableSubscribeOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda66
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadStop$lambda$108(function12, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeUploadStop$lambda$106(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        UploadStopRequest uploadStopRequest = new UploadStopRequest();
        byte[] bytes = uploadStopRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = uploadStopRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.executeCommandRequest(bytes);
        return Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadStop$lambda$107(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadStop$lambda$108(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateInstallPresetDB(List<SoundPacket> soundPackage) {
        for (SoundPacket soundPacket : soundPackage) {
            InstalledSoundPackageEntity installedSoundPackageEntity = new InstalledSoundPackageEntity(soundPacket.getSoundPacketId(), soundPacket.getSoundPacketVersion(), soundPacket.getModesMask().getRawValue(), false, 8, null);
            this.mFileLogger.i("Insert installed package: " + installedSoundPackageEntity, new Object[0]);
            Maybe<Integer> maybeUpdateEndReturnId = ThorDatabase.INSTANCE.from(this.context).installedSoundPackageDao().updateEndReturnId(installedSoundPackageEntity.getPackageId(), installedSoundPackageEntity.getVersionId(), installedSoundPackageEntity.getMode());
            final BleManager$updateInstallPresetDB$1$1 bleManager$updateInstallPresetDB$1$1 = new BleManager$updateInstallPresetDB$1$1(this, installedSoundPackageEntity);
            Consumer<? super Integer> consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda101
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BleManager.updateInstallPresetDB$lambda$112$lambda$109(bleManager$updateInstallPresetDB$1$1, obj);
                }
            };
            final Function1<Throwable, Unit> function1 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager$updateInstallPresetDB$1$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable it) {
                    FileLogger fileLogger = this.this$0.mFileLogger;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    fileLogger.e(it);
                }
            };
            this.mDatabaseCompositeDisposable.add(maybeUpdateEndReturnId.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda102
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BleManager.updateInstallPresetDB$lambda$112$lambda$110(function1, obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateInstallPresetDB$lambda$112$lambda$109(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateInstallPresetDB$lambda$112$lambda$110(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    static /* synthetic */ void waitTime$default(BleManager bleManager, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = 100;
        }
        bleManager.waitTime(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void waitTime(long time) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        while (!BleHelper.checkCrc(this.mOutputStream.toByteArray()) && jCurrentTimeMillis - System.currentTimeMillis() <= time) {
        }
    }

    public final void executeUploadSampleRule(final ShopSoundPackage soundPackage, byte[] file, final short blockNumber) {
        Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
        Intrinsics.checkNotNullParameter(file, "file");
        this.mFileLogger.i("executeUploadSampleRule", new Object[0]);
        this.mUploadSoundFileRequest = new UploadSoundPackageFileBleRequest((short) 98, (short) soundPackage.getId(), (short) soundPackage.getPkgVer(), file);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda95
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeUploadSampleRule$lambda$113(this.f$0, blockNumber, soundPackage);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            mOut…)\n            }\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadSampleRule.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mUploadingData = true;
                BleManager.this.mFileLogger.i("executeUploadSampleRule start", new Object[0]);
            }
        };
        Observable observableDoOnTerminate = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda97
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadSampleRule$lambda$114(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda98
            @Override // io.reactivex.functions.Action
            public final void run() {
                BleManager.executeUploadSampleRule$lambda$115(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadSampleRule.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                UploadSoundPackageFileBleRequest uploadSoundPackageFileBleRequest = BleManager.this.mUploadSoundFileRequest;
                Short shValueOf = uploadSoundPackageFileBleRequest != null ? Short.valueOf(uploadSoundPackageFileBleRequest.getCommand()) : null;
                Intrinsics.checkNotNull(shValueOf);
                WriteSoundPackageFileBlockBleResponse writeSoundPackageFileBlockBleResponse = new WriteSoundPackageFileBlockBleResponse(shValueOf.shortValue(), byteArray);
                if (writeSoundPackageFileBlockBleResponse.isSuccessful()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes = writeSoundPackageFileBlockBleResponse.getBytes();
                    fileLogger.i("Response is correct: " + (bytes != null ? BleHelperKt.toHex(bytes) : null), new Object[0]);
                    EventBus.getDefault().post(new UploadSoundSampleRuleSuccessfulEvent(true));
                    return;
                }
                EventBus.getDefault().post(new UploadSoundPackageErrorEvent(true, soundPackage));
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda99
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadSampleRule$lambda$116(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadSampleRule.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        compositeDisposable.add(observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda100
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadSampleRule$lambda$117(function13, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeUploadSampleRule$lambda$113(BleManager this$0, short s, ShopSoundPackage soundPackage) {
        Observable observableSubscribeOn;
        List<byte[]> blocks;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(soundPackage, "$soundPackage");
        this$0.mOutputStream.reset();
        try {
            this$0.mCurrentBlock = s;
            this$0.mErrorsCount = 0;
            while (true) {
                int i = this$0.mCurrentBlock;
                UploadSoundPackageFileBleRequest uploadSoundPackageFileBleRequest = this$0.mUploadSoundFileRequest;
                byte[] bArr = null;
                List<byte[]> blocks2 = uploadSoundPackageFileBleRequest != null ? uploadSoundPackageFileBleRequest.getBlocks() : null;
                Intrinsics.checkNotNull(blocks2);
                if (i >= blocks2.size() || !this$0.mUploadingData) {
                    break;
                }
                UploadSoundPackageFileBleRequest uploadSoundPackageFileBleRequest2 = this$0.mUploadSoundFileRequest;
                if (uploadSoundPackageFileBleRequest2 != null && (blocks = uploadSoundPackageFileBleRequest2.getBlocks()) != null) {
                    bArr = blocks.get(this$0.mCurrentBlock);
                }
                this$0.executeUploadSound(bArr);
                if (this$0.mErrorsCount == 10) {
                    this$0.mFileLogger.e("Errors count: " + this$0.mErrorsCount, new Object[0]);
                    EventBus.getDefault().post(new UploadSoundPackageErrorEvent(true, soundPackage));
                    break;
                }
            }
            observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        } catch (Exception unused) {
            observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        }
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadSampleRule$lambda$114(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadSampleRule$lambda$115(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("executeUploadSampleRule finish", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadSampleRule$lambda$116(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadSampleRule$lambda$117(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void executeUploadPackageRule(final ShopSoundPackage soundPackage, byte[] file, final short blockNumber) {
        Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
        Intrinsics.checkNotNullParameter(file, "file");
        this.mFileLogger.i("executeUploadPackageRule", new Object[0]);
        this.mUploadSoundFileRequest = new UploadSoundPackageFileBleRequest((short) 99, (short) soundPackage.getId(), (short) soundPackage.getPkgVer(), file);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda63
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeUploadPackageRule$lambda$118(this.f$0, blockNumber, soundPackage);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            mOut…)\n            }\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadPackageRule.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mUploadingData = true;
                BleManager.this.mFileLogger.i("executeUploadPackageRule start", new Object[0]);
            }
        };
        Observable observableDoOnTerminate = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda74
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadPackageRule$lambda$119(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda85
            @Override // io.reactivex.functions.Action
            public final void run() {
                BleManager.executeUploadPackageRule$lambda$120(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadPackageRule.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                UploadSoundPackageFileBleRequest uploadSoundPackageFileBleRequest = BleManager.this.mUploadSoundFileRequest;
                Short shValueOf = uploadSoundPackageFileBleRequest != null ? Short.valueOf(uploadSoundPackageFileBleRequest.getCommand()) : null;
                Intrinsics.checkNotNull(shValueOf);
                WriteSoundPackageFileBlockBleResponse writeSoundPackageFileBlockBleResponse = new WriteSoundPackageFileBlockBleResponse(shValueOf.shortValue(), byteArray);
                if (writeSoundPackageFileBlockBleResponse.isSuccessful()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes = writeSoundPackageFileBlockBleResponse.getBytes();
                    fileLogger.i("Response is correct: " + (bytes != null ? BleHelperKt.toHex(bytes) : null), new Object[0]);
                    EventBus.getDefault().post(new UploadSoundPackageRuleSuccessfulEvent(true));
                    return;
                }
                EventBus.getDefault().post(new UploadSoundPackageErrorEvent(true, soundPackage));
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda96
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadPackageRule$lambda$121(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadPackageRule.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        compositeDisposable.add(observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda107
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadPackageRule$lambda$122(function13, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeUploadPackageRule$lambda$118(BleManager this$0, short s, ShopSoundPackage soundPackage) {
        Observable observableSubscribeOn;
        List<byte[]> blocks;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(soundPackage, "$soundPackage");
        this$0.mOutputStream.reset();
        try {
            this$0.mCurrentBlock = s;
            this$0.mErrorsCount = 0;
            while (true) {
                int i = this$0.mCurrentBlock;
                UploadSoundPackageFileBleRequest uploadSoundPackageFileBleRequest = this$0.mUploadSoundFileRequest;
                byte[] bArr = null;
                List<byte[]> blocks2 = uploadSoundPackageFileBleRequest != null ? uploadSoundPackageFileBleRequest.getBlocks() : null;
                Intrinsics.checkNotNull(blocks2);
                if (i >= blocks2.size() || !this$0.mUploadingData) {
                    break;
                }
                UploadSoundPackageFileBleRequest uploadSoundPackageFileBleRequest2 = this$0.mUploadSoundFileRequest;
                if (uploadSoundPackageFileBleRequest2 != null && (blocks = uploadSoundPackageFileBleRequest2.getBlocks()) != null) {
                    bArr = blocks.get(this$0.mCurrentBlock);
                }
                this$0.executeUploadSound(bArr);
                if (this$0.mErrorsCount == 10) {
                    this$0.mFileLogger.e("Errors count: " + this$0.mErrorsCount, new Object[0]);
                    EventBus.getDefault().post(new UploadSoundPackageErrorEvent(true, soundPackage));
                    break;
                }
            }
            observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        } catch (Exception unused) {
            observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        }
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadPackageRule$lambda$119(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadPackageRule$lambda$120(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("executeUploadPackageRule finish", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadPackageRule$lambda$121(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadPackageRule$lambda$122(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void executeUploadSound(byte[] bytes) {
        this.mFileLogger.i("executeUploadSound: " + Hex.bytesToStringLowercase(bytes == null ? new byte[0] : bytes), new Object[0]);
        if (bytes == null) {
            return;
        }
        if (this.mErrorsCount == 10) {
            this.mFileLogger.e("Errors count: %s", Integer.valueOf(this.mErrorsCount));
            this.mCommandExecuting = false;
            return;
        }
        sendUploadSoundProgressEvent();
        this.mOutputStream.reset();
        this.mStepsRequest = (int) Math.ceil(bytes.length / 20.0d);
        if (this.mStepRequest == 0) {
            Timber.INSTANCE.d("mStepRequest already zero", new Object[0]);
        }
        this.mStepRequest = 0;
        this.mInputByteArrays = BleHelper.INSTANCE.splitByteArray(bytes, 20);
        writeCharacteristicValue();
        this.mCommandExecuting = true;
        this.mTimer = System.currentTimeMillis() + TIMER_DURATION;
        while (this.mCommandExecuting) {
            checkCrcForUploadingSound();
            if (this.mTimer < System.currentTimeMillis()) {
                this.mFileLogger.w("!!! Timer rule worked !!!", new Object[0]);
                this.mCommandExecuting = false;
            }
        }
    }

    public static /* synthetic */ List splitByteArrayIntoChunks$default(BleManager bleManager, byte[] bArr, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 2;
        }
        return bleManager.splitByteArrayIntoChunks(bArr, i);
    }

    public final List<byte[]> splitByteArrayIntoChunks(byte[] data, int chunkSize) {
        Intrinsics.checkNotNullParameter(data, "data");
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < data.length) {
            int i2 = i + chunkSize;
            arrayList.add(ArraysKt.copyOfRange(data, i, RangesKt.coerceAtMost(i2, data.length)));
            i = i2;
        }
        return arrayList;
    }

    public final void executeUploadSoundNew(byte[] bytes) throws InterruptedException {
        if (bytes == null) {
            return;
        }
        if (this.mErrorsCount == 10) {
            this.mFileLogger.e("Errors count: %s", Integer.valueOf(this.mErrorsCount));
            this.mCommandExecuting = false;
            return;
        }
        sendUploadSoundProgressEvent70();
        this.mOutputStream.reset();
        this.mStepsRequest = (int) Math.ceil(bytes.length / 20.0d);
        if (this.mStepRequest == 0) {
            Timber.INSTANCE.d("mStepRequest already zero", new Object[0]);
        }
        this.mStepRequest = 0;
        this.mInputByteArrays = BleHelper.INSTANCE.splitByteArray(bytes, 20);
        writeCharacteristicValue();
        Thread.sleep(this.TIME_FIX_DELAY);
        Log.i("TIME_DELAY", String.valueOf(this.TIME_FIX_DELAY));
        this.mCommandExecuting = true;
        this.mTimer = System.currentTimeMillis() + TIMER_DURATION;
        while (this.mCommandExecuting) {
            checkCrcForUploadingSoundNew();
            if (this.mTimer < System.currentTimeMillis()) {
                this.mFileLogger.w("!!! Timer rule worked !!!", new Object[0]);
                this.mCommandExecuting = false;
            }
        }
    }

    public final Observable<ByteArrayOutputStream> executeReadSettingsCommand() {
        this.mFileLogger.i("executeReadSettingsCommand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda70
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeReadSettingsCommand$lambda$123(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable<ByteArrayOutputStream> observableSubscribeOn = observableDefer.timeout(TIMER_DURATION, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.from(this.mExecutorService));
        Intrinsics.checkNotNullExpressionValue(observableSubscribeOn, "observable\n            .…s.from(mExecutorService))");
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeReadSettingsCommand$lambda$123(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ReadSettingsBleRequest readSettingsBleRequest = new ReadSettingsBleRequest();
        byte[] bytes = readSettingsBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = readSettingsBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    public final Observable<ByteArrayOutputStream> executeWriteSettingsCommand(final ArrayList<DeviceSettings.Setting> settings) {
        Intrinsics.checkNotNullParameter(settings, "settings");
        this.mFileLogger.i("executeWriteSettingsCommand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda34
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeWriteSettingsCommand$lambda$124(settings, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable<ByteArrayOutputStream> observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        Intrinsics.checkNotNullExpressionValue(observableSubscribeOn, "observable.subscribeOn(S…s.from(mExecutorService))");
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeWriteSettingsCommand$lambda$124(ArrayList settings, BleManager this$0) {
        Intrinsics.checkNotNullParameter(settings, "$settings");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        WriteSettingsBleRequest writeSettingsBleRequest = new WriteSettingsBleRequest(settings);
        byte[] bytes = writeSettingsBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = writeSettingsBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    public final Observable<ByteArrayOutputStream> executeWriteFactoryPresetSetting(final short packageId, final short modeIndex) {
        this.mFileLogger.i("executeWriteSettingsCommand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda57
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeWriteFactoryPresetSetting$lambda$125(packageId, modeIndex, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable<ByteArrayOutputStream> observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        Intrinsics.checkNotNullExpressionValue(observableSubscribeOn, "observable.subscribeOn(S…s.from(mExecutorService))");
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeWriteFactoryPresetSetting$lambda$125(short s, short s2, BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        WriteFactoryPresetSettingBleRequest writeFactoryPresetSettingBleRequest = new WriteFactoryPresetSettingBleRequest(s, s2);
        byte[] bytes = writeFactoryPresetSettingBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = writeFactoryPresetSettingBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    private final void sendUploadSoundProgressEvent() {
        List<byte[]> blocks;
        this.mFileLogger.i("sendUploadSoundProgressEvent: block " + this.mCurrentBlock, new Object[0]);
        double d = 100;
        UploadSoundPackageFileBleRequest uploadSoundPackageFileBleRequest = this.mUploadSoundFileRequest;
        Double dValueOf = (uploadSoundPackageFileBleRequest == null || (blocks = uploadSoundPackageFileBleRequest.getBlocks()) == null) ? null : Double.valueOf(blocks.size());
        Intrinsics.checkNotNull(dValueOf);
        EventBus.getDefault().post(new UploadSoundPackageProgressEvent((int) Math.ceil((d / dValueOf.doubleValue()) * this.mCurrentBlock)));
    }

    private final void sendUploadSoundProgressEvent70() {
        this.mFileLogger.i("sendUploadSoundProgressEvent: block " + this.mCurrentBlock, new Object[0]);
        double d = this.fileDownloadSize;
        double dCeil = Math.ceil((100 / d) * (d == 2.0d ? this.mCurrentBlock + 1 : this.mCurrentBlock));
        Log.i("PROGRESS", dCeil + "  fileSize " + this.fileDownloadSize + "  and currunt " + this.mCurrentBlock);
        int i = (int) dCeil;
        EventBus.getDefault().post(new UploadSoundPackageProgressEvent(i));
        EventBus.getDefault().post(new UpdateFirmwareProgressEvent(i));
        EventBus.getDefault().post(new UploadSguSoundProgressEvent(i));
    }

    private final void checkCrcForUploadingSound() {
        if (this.mStepRequest == this.mStepsRequest) {
            requestHighPriority();
            if (BleHelper.checkCrc(this.mOutputStream.toByteArray())) {
                handleWriteSoundFileResponse();
            }
        }
    }

    private final void checkCrcForUploadingSoundNew() {
        requestHighPriority();
        if (BleHelper.checkCrc(this.mOutputStream.toByteArray())) {
            handleWriteSoundFileResponseNew();
        }
    }

    private final void handleWriteSoundFileResponse() {
        this.mFileLogger.i("handleWriteSoundFileResponse: " + Hex.bytesToStringUppercase(this.mOutputStream.toByteArray()), new Object[0]);
        UploadSoundPackageFileBleRequest uploadSoundPackageFileBleRequest = this.mUploadSoundFileRequest;
        Short shValueOf = uploadSoundPackageFileBleRequest != null ? Short.valueOf(uploadSoundPackageFileBleRequest.getCommand()) : null;
        Intrinsics.checkNotNull(shValueOf);
        WriteSoundPackageFileBlockBleResponse writeSoundPackageFileBlockBleResponse = new WriteSoundPackageFileBlockBleResponse(shValueOf.shortValue(), this.mOutputStream.toByteArray());
        this.mCommandExecuting = false;
        if (writeSoundPackageFileBlockBleResponse.getStatus()) {
            this.mCurrentBlock++;
            this.mErrorsCount = 0;
        }
    }

    private final void handleWriteSoundFileResponseNew() {
        byte[] byteArray = this.mOutputStream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "mOutputStream.toByteArray()");
        new DownloadWriteBlockBleResponse(byteArray);
        this.mFileLogger.i("handleWriteSoundFileResponse: " + Hex.bytesToStringUppercase(this.mOutputStream.toByteArray()), new Object[0]);
        this.mCommandExecuting = false;
        this.mCurrentBlock++;
        this.mErrorsCount = 0;
    }

    public final Observable<ByteArrayOutputStream> executeApplyUploadSoundPackage(final ShopSoundPackage soundPackage) {
        Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
        this.mFileLogger.i("executeStartUploadSoundPackage", new Object[0]);
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda94
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeApplyUploadSoundPackage$lambda$126(this.f$0, soundPackage);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            mUpl…ecutorService))\n        }");
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeApplyUploadSoundPackage$lambda$126(BleManager this$0, ShopSoundPackage soundPackage) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(soundPackage, "$soundPackage");
        this$0.mUploadingData = false;
        byte[] bytes = new ApplyUploadSoundPackageBleRequest((short) soundPackage.getId(), (short) soundPackage.getPkgVer()).getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        this$0.mFileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
        this$0.executeCommandRequest(bytes);
        return Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
    }

    public final Observable<ByteArrayOutputStream> executeApplyUploadPackage() {
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda29
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeApplyUploadPackage$lambda$127(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            mUpl…ecutorService))\n        }");
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeApplyUploadPackage$lambda$127(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mUploadingData = false;
        ApplyUploadSoundPackageBleRequestNew applyUploadSoundPackageBleRequestNew = new ApplyUploadSoundPackageBleRequestNew();
        byte[] bytes = applyUploadSoundPackageBleRequestNew.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = applyUploadSoundPackageBleRequestNew.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.executeCommandRequest(bytes);
        return Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
    }

    public final Observable<ByteArrayOutputStream> executeStartDownloadGroup(final int fileCount) {
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda111
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeStartDownloadGroup$lambda$128(fileCount, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …ecutorService))\n        }");
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeStartDownloadGroup$lambda$128(int i, BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DownloadStartGroupBleRequest downloadStartGroupBleRequest = new DownloadStartGroupBleRequest((short) i);
        byte[] bytes = downloadStartGroupBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = downloadStartGroupBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.executeCommandRequest(bytes);
        return Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
    }

    private final Observable<ByteArrayOutputStream> executeDownloadCommitFile() {
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda142
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeDownloadCommitFile$lambda$129(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …ecutorService))\n        }");
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeDownloadCommitFile$lambda$129(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DownloadCommitFileBleRequest downloadCommitFileBleRequest = new DownloadCommitFileBleRequest();
        this$0.executeCommandRequest(downloadCommitFileBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus()));
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = downloadCommitFileBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        return Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
    }

    public final Observable<ByteArrayOutputStream> executeReloadUploadingSoundPackage(final int soundPackageId, final int soundPackageVersion) {
        this.mFileLogger.i("executeReloadUploadingSoundPackage", new Object[0]);
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda151
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeReloadUploadingSoundPackage$lambda$130(soundPackageId, soundPackageVersion, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …ecutorService))\n        }");
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeReloadUploadingSoundPackage$lambda$130(int i, int i2, BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ReloadUploadingSoundPackageBleRequest reloadUploadingSoundPackageBleRequest = new ReloadUploadingSoundPackageBleRequest((short) i, (short) i2);
        byte[] bytes = reloadUploadingSoundPackageBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = reloadUploadingSoundPackageBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
    }

    public final Observable<ByteArrayOutputStream> executeReloadFirmware(final int deviceId, final int hardwareVersion, final int firmwareVersion) {
        this.mFileLogger.i("executeReloadUploadingSoundPackage", new Object[0]);
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda89
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeReloadFirmware$lambda$131(deviceId, hardwareVersion, firmwareVersion, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …ecutorService))\n        }");
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeReloadFirmware$lambda$131(int i, int i2, int i3, BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        byte[] bytes = new ReloadUploadingFirmwareBleRequest((short) i, (short) i2, (short) i3).getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        this$0.mFileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
    }

    public final Observable<ByteArrayOutputStream> executePoilingCommand() {
        this.mFileLogger.i("executePoilingCommand", new Object[0]);
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda32
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executePoilingCommand$lambda$132(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            mUpl…ecutorService))\n        }");
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executePoilingCommand$lambda$132(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mUploadingData = false;
        PoilingRequest poilingRequest = new PoilingRequest();
        byte[] bytes = poilingRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = poilingRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
    }

    public final Observable<ByteArrayOutputStream> executePoilingCommandOld() {
        this.mFileLogger.i("executePoilingCommand", new Object[0]);
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda4
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executePoilingCommandOld$lambda$133(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            mUpl…ecutorService))\n        }");
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executePoilingCommandOld$lambda$133(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mUploadingData = false;
        byte[] bytes = new PoilingRequestOld().getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        this$0.mFileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
    }

    public final Observable<ByteArrayOutputStream> executeUploadStartCommand(final int fileId) {
        Observable<ByteArrayOutputStream> observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda1
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeUploadStartCommand$lambda$134(fileId, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …ecutorService))\n        }");
        return observableDefer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeUploadStartCommand$lambda$134(int i, BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        UploadStartBleRequest uploadStartBleRequest = new UploadStartBleRequest(i, (short) Variables.INSTANCE.getBLOCK_SIZE());
        byte[] bytes = uploadStartBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = uploadStartBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
    }

    public final Observable<PlaySguSoundBleResponse> executePlaySguSoundCommand(final short soundId, final SguSoundConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        this.mFileLogger.i("executePlaySguSoundCommand", new Object[0]);
        final short s = 100;
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda112
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executePlaySguSoundCommand$lambda$135(soundId, config, s, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final C03331 c03331 = new Function1<ByteArrayOutputStream, PlaySguSoundBleResponse>() { // from class: com.thor.app.managers.BleManager.executePlaySguSoundCommand.1
            @Override // kotlin.jvm.functions.Function1
            public final PlaySguSoundBleResponse invoke(ByteArrayOutputStream outputStream) {
                Intrinsics.checkNotNullParameter(outputStream, "outputStream");
                byte[] byteArray = outputStream.toByteArray();
                Timber.INSTANCE.i("Take data: %s", Hex.bytesToStringUppercase(byteArray));
                return new PlaySguSoundBleResponse(byteArray);
            }
        };
        Observable<PlaySguSoundBleResponse> map = observableSubscribeOn.map(new Function() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda113
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return BleManager.executePlaySguSoundCommand$lambda$136(c03331, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(map, "observable.subscribeOn(S…Response(bytes)\n        }");
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executePlaySguSoundCommand$lambda$135(short s, SguSoundConfig config, short s2, BleManager this$0) {
        Intrinsics.checkNotNullParameter(config, "$config");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        PlaySguSoundBleRequest playSguSoundBleRequest = new PlaySguSoundBleRequest(s, config.getRepeatCycle(), s2, config.getSoundVolume());
        byte[] bytes = playSguSoundBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = playSguSoundBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PlaySguSoundBleResponse executePlaySguSoundCommand$lambda$136(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (PlaySguSoundBleResponse) tmp0.invoke(obj);
    }

    public final Observable<StopSguSoundBleResponse> executeStopSguSoundCommand() {
        this.mFileLogger.i("executeStopSguSoundCommand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda121
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeStopSguSoundCommand$lambda$137(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final C03391 c03391 = new Function1<ByteArrayOutputStream, StopSguSoundBleResponse>() { // from class: com.thor.app.managers.BleManager.executeStopSguSoundCommand.1
            @Override // kotlin.jvm.functions.Function1
            public final StopSguSoundBleResponse invoke(ByteArrayOutputStream outputStream) {
                Intrinsics.checkNotNullParameter(outputStream, "outputStream");
                byte[] byteArray = outputStream.toByteArray();
                Timber.INSTANCE.i("Take data: %s", Hex.bytesToStringUppercase(byteArray));
                return new StopSguSoundBleResponse(byteArray);
            }
        };
        Observable<StopSguSoundBleResponse> map = observableSubscribeOn.map(new Function() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda122
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return BleManager.executeStopSguSoundCommand$lambda$138(c03391, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(map, "observable.subscribeOn(S…Response(bytes)\n        }");
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeStopSguSoundCommand$lambda$137(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        StopSguSoundBleRequest stopSguSoundBleRequest = new StopSguSoundBleRequest();
        byte[] bytes = stopSguSoundBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = stopSguSoundBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final StopSguSoundBleResponse executeStopSguSoundCommand$lambda$138(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (StopSguSoundBleResponse) tmp0.invoke(obj);
    }

    public final Observable<ReadSguSoundsResponse> executeReadSguSoundsCommand() {
        this.mFileLogger.i("executeReadSguSoundsCommand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda49
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeReadSguSoundsCommand$lambda$139(this.f$0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final C03371 c03371 = new Function1<ByteArrayOutputStream, ReadSguSoundsResponse>() { // from class: com.thor.app.managers.BleManager.executeReadSguSoundsCommand.1
            @Override // kotlin.jvm.functions.Function1
            public final ReadSguSoundsResponse invoke(ByteArrayOutputStream outputStream) {
                Intrinsics.checkNotNullParameter(outputStream, "outputStream");
                byte[] byteArray = outputStream.toByteArray();
                Timber.INSTANCE.i("Take data: %s", Hex.bytesToStringUppercase(byteArray));
                return new ReadSguSoundsResponse(byteArray);
            }
        };
        Observable<ReadSguSoundsResponse> map = observableSubscribeOn.map(new Function() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda50
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return BleManager.executeReadSguSoundsCommand$lambda$140(c03371, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(map, "observable.subscribeOn(S…Response(bytes)\n        }");
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeReadSguSoundsCommand$lambda$139(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ReadSguSoundsBleRequest readSguSoundsBleRequest = new ReadSguSoundsBleRequest();
        byte[] bytes = readSguSoundsBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = readSguSoundsBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ReadSguSoundsResponse executeReadSguSoundsCommand$lambda$140(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (ReadSguSoundsResponse) tmp0.invoke(obj);
    }

    public final Observable<WriteSguSoundSequenceBleResponse> executeWriteSguSoundSequenceCommand(final List<Short> soundIds) {
        Intrinsics.checkNotNullParameter(soundIds, "soundIds");
        this.mFileLogger.i("executeWriteSguSoundSequenceCommand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda44
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeWriteSguSoundSequenceCommand$lambda$141(soundIds, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final C03721 c03721 = new Function1<ByteArrayOutputStream, WriteSguSoundSequenceBleResponse>() { // from class: com.thor.app.managers.BleManager.executeWriteSguSoundSequenceCommand.1
            @Override // kotlin.jvm.functions.Function1
            public final WriteSguSoundSequenceBleResponse invoke(ByteArrayOutputStream outputStream) {
                Intrinsics.checkNotNullParameter(outputStream, "outputStream");
                byte[] byteArray = outputStream.toByteArray();
                Timber.INSTANCE.i("Take data: %s", Hex.bytesToStringUppercase(byteArray));
                return new WriteSguSoundSequenceBleResponse(byteArray);
            }
        };
        Observable<WriteSguSoundSequenceBleResponse> map = observableSubscribeOn.map(new Function() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda55
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return BleManager.executeWriteSguSoundSequenceCommand$lambda$142(c03721, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(map, "observable.subscribeOn(S…Response(bytes)\n        }");
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeWriteSguSoundSequenceCommand$lambda$141(List soundIds, BleManager this$0) {
        Intrinsics.checkNotNullParameter(soundIds, "$soundIds");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        byte[] bytes = new WriteSguSoundSequenceBleRequest(soundIds).getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        this$0.mFileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bytes), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final WriteSguSoundSequenceBleResponse executeWriteSguSoundSequenceCommand$lambda$142(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (WriteSguSoundSequenceBleResponse) tmp0.invoke(obj);
    }

    public final Observable<StartWriteSguSoundBleResponse> executeStartUploadSguSoundCommand(final short soundId) {
        this.mFileLogger.i("executeStartUploadSguSoundCommand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda115
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeStartUploadSguSoundCommand$lambda$143(soundId, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final C03381 c03381 = new Function1<ByteArrayOutputStream, StartWriteSguSoundBleResponse>() { // from class: com.thor.app.managers.BleManager.executeStartUploadSguSoundCommand.1
            @Override // kotlin.jvm.functions.Function1
            public final StartWriteSguSoundBleResponse invoke(ByteArrayOutputStream outputStream) {
                Intrinsics.checkNotNullParameter(outputStream, "outputStream");
                byte[] byteArray = outputStream.toByteArray();
                Timber.INSTANCE.i("Take data: %s", Hex.bytesToStringUppercase(byteArray));
                return new StartWriteSguSoundBleResponse(byteArray);
            }
        };
        Observable<StartWriteSguSoundBleResponse> map = observableSubscribeOn.map(new Function() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda116
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return BleManager.executeStartUploadSguSoundCommand$lambda$144(c03381, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(map, "observable.subscribeOn(S…Response(bytes)\n        }");
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeStartUploadSguSoundCommand$lambda$143(short s, BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        StartWriteSguSoundBleRequest startWriteSguSoundBleRequest = new StartWriteSguSoundBleRequest(s);
        byte[] bytes = startWriteSguSoundBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = startWriteSguSoundBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final StartWriteSguSoundBleResponse executeStartUploadSguSoundCommand$lambda$144(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (StartWriteSguSoundBleResponse) tmp0.invoke(obj);
    }

    public final Observable<ApplySguSoundBleResponse> executeApplyUploadedSguSoundCommand(final short soundId) {
        this.mFileLogger.i("executeApplyUploadedSguSoundCommand", new Object[0]);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda23
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeApplyUploadedSguSoundCommand$lambda$145(soundId, this);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            val …(mOutputStream)\n        }");
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final C03091 c03091 = new Function1<ByteArrayOutputStream, ApplySguSoundBleResponse>() { // from class: com.thor.app.managers.BleManager.executeApplyUploadedSguSoundCommand.1
            @Override // kotlin.jvm.functions.Function1
            public final ApplySguSoundBleResponse invoke(ByteArrayOutputStream outputStream) {
                Intrinsics.checkNotNullParameter(outputStream, "outputStream");
                byte[] byteArray = outputStream.toByteArray();
                Timber.INSTANCE.i("Take data: %s", Hex.bytesToStringUppercase(byteArray));
                return new ApplySguSoundBleResponse(byteArray);
            }
        };
        Observable<ApplySguSoundBleResponse> map = observableSubscribeOn.map(new Function() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda24
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return BleManager.executeApplyUploadedSguSoundCommand$lambda$146(c03091, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(map, "observable.subscribeOn(S…Response(bytes)\n        }");
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeApplyUploadedSguSoundCommand$lambda$145(short s, BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ApplySguSoundBleRequest applySguSoundBleRequest = new ApplySguSoundBleRequest(s);
        byte[] bytes = applySguSoundBleRequest.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
        FileLogger fileLogger = this$0.mFileLogger;
        byte[] bArr = applySguSoundBleRequest.getDeсryptoMessage();
        if (bArr == null) {
            bArr = new byte[0];
        }
        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
        this$0.writeCharacteristicValue(bytes, TIMER_DURATION);
        return Observable.just(this$0.mOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ApplySguSoundBleResponse executeApplyUploadedSguSoundCommand$lambda$146(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (ApplySguSoundBleResponse) tmp0.invoke(obj);
    }

    public final void executeUploadSguSound(byte[] file, final short soundId, final short blockNumber) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.mFileLogger.i("executeStartUploadSguSound", new Object[0]);
        clear();
        this.mSguSoundRequest = new WriteSguSoundBleRequest(file, soundId);
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda10
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeUploadSguSound$lambda$147(this.f$0, blockNumber);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            mOut…)\n            }\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableObserveOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService)).observeOn(AndroidSchedulers.mainThread());
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadSguSound.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mFileLogger.i("executeWriteSguSound start", new Object[0]);
                BleManager.this.mUploadingData = true;
            }
        };
        Observable observableDoOnTerminate = observableObserveOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadSguSound$lambda$148(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Action
            public final void run() {
                BleManager.executeUploadSguSound$lambda$149(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadSguSound.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                WriteSguSoundBleResponse writeSguSoundBleResponse = new WriteSguSoundBleResponse(byteArray);
                if (writeSguSoundBleResponse.isSuccessful()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes = writeSguSoundBleResponse.getBytes();
                    fileLogger.i("Response is correct: " + (bytes != null ? BleHelperKt.toHex(bytes) : null), new Object[0]);
                    EventBus.getDefault().post(new UploadSguSoundSuccessfulEvent(soundId));
                    return;
                }
                EventBus.getDefault().post(new UploadSguSoundErrorEvent());
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadSguSound$lambda$150(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeUploadSguSound.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        compositeDisposable.add(observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeUploadSguSound$lambda$151(function13, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeUploadSguSound$lambda$147(BleManager this$0, short s) {
        Observable observableSubscribeOn;
        List<byte[]> blocks;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mOutputStream.reset();
        try {
            this$0.mCurrentBlock = s;
            this$0.mErrorsCount = 0;
            while (true) {
                int i = this$0.mCurrentBlock;
                WriteSguSoundBleRequest writeSguSoundBleRequest = this$0.mSguSoundRequest;
                byte[] bArr = null;
                List<byte[]> blocks2 = writeSguSoundBleRequest != null ? writeSguSoundBleRequest.getBlocks() : null;
                Intrinsics.checkNotNull(blocks2);
                if (i >= blocks2.size() || !this$0.mUploadingData) {
                    break;
                }
                WriteSguSoundBleRequest writeSguSoundBleRequest2 = this$0.mSguSoundRequest;
                if (writeSguSoundBleRequest2 != null && (blocks = writeSguSoundBleRequest2.getBlocks()) != null) {
                    bArr = blocks.get(this$0.mCurrentBlock);
                }
                this$0.executeUploadSguSound(bArr);
                if (this$0.mErrorsCount == 10) {
                    this$0.mFileLogger.e("Errors count: %s", Integer.valueOf(this$0.mErrorsCount));
                    EventBus.getDefault().post(new UploadSguSoundErrorEvent());
                    break;
                }
            }
            observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        } catch (Exception unused) {
            observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        }
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadSguSound$lambda$148(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadSguSound$lambda$149(BleManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mFileLogger.i("executeWriteSguSound finish", new Object[0]);
        this$0.mUploadingData = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadSguSound$lambda$150(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeUploadSguSound$lambda$151(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void executeUploadSguSound(byte[] bytes) {
        this.mFileLogger.i("start executeUploadSguSound: " + TimeHelper.getMinutesWithSeconds(), new Object[0]);
        if (bytes == null) {
            return;
        }
        if (this.mErrorsCount == 10) {
            this.mFileLogger.e("Errors count: %s", Integer.valueOf(this.mErrorsCount));
            this.mCommandExecuting = false;
            return;
        }
        sendUploadSguSoundProgressEvent();
        this.mOutputStream.reset();
        this.mStepsRequest = (int) Math.ceil(bytes.length / 20.0d);
        this.mStepRequest = 0;
        this.mInputByteArrays = BleHelper.INSTANCE.splitByteArray(bytes, 20);
        Log.i("ITS_WRITE", "6");
        writeCharacteristicValue();
        this.mCommandExecuting = true;
        this.mTimer = System.currentTimeMillis() + TIMER_DURATION;
        while (this.mCommandExecuting) {
            checkCrcForUploadSguSound();
            if (this.mTimer < System.currentTimeMillis()) {
                this.mFileLogger.i("!!! Timer rule worked !!!", new Object[0]);
                this.mCommandExecuting = false;
            }
        }
        this.mFileLogger.i("end executeUploadSguSound: " + TimeHelper.getMinutesWithSeconds(), new Object[0]);
    }

    private final void sendUploadSguSoundProgressEvent() {
        List<byte[]> blocks;
        this.mFileLogger.i("sendUploadSguSoundProgressEvent: block " + this.mCurrentBlock, new Object[0]);
        double d = 100;
        WriteSguSoundBleRequest writeSguSoundBleRequest = this.mSguSoundRequest;
        Double dValueOf = (writeSguSoundBleRequest == null || (blocks = writeSguSoundBleRequest.getBlocks()) == null) ? null : Double.valueOf(blocks.size());
        Intrinsics.checkNotNull(dValueOf);
        EventBus.getDefault().post(new UploadSguSoundProgressEvent((int) Math.ceil((d / dValueOf.doubleValue()) * this.mCurrentBlock)));
    }

    private final void checkCrcForUploadSguSound() {
        if (this.mStepRequest == this.mStepsRequest) {
            requestHighPriority();
            if (BleHelper.checkCrc(this.mOutputStream.toByteArray())) {
                handleWriteSguSoundResponse();
            }
        }
    }

    private final void handleWriteSguSoundResponse() {
        FileLogger fileLogger = this.mFileLogger;
        String strBytesToStringLowercase = Hex.bytesToStringLowercase(this.mOutputStream.toByteArray());
        Intrinsics.checkNotNullExpressionValue(strBytesToStringLowercase, "bytesToStringLowercase(m…tputStream.toByteArray())");
        fileLogger.i("handleWriteSguSoundResponse: %s", strBytesToStringLowercase);
        WriteSguSoundBleResponse writeSguSoundBleResponse = new WriteSguSoundBleResponse(this.mOutputStream.toByteArray());
        this.mCommandExecuting = false;
        if (writeSguSoundBleResponse.isSuccessful()) {
            this.mCurrentBlock++;
            this.mErrorsCount = 0;
        }
    }

    public final void resetFormatFlashValue() {
        this.mIsFormatFlash = false;
    }

    public final void setActivatedPresetIndex(short index) {
        this.mActivatedPresetIndex = index;
    }

    public final void setActivatedPreset(InstalledPreset preset) {
        Intrinsics.checkNotNullParameter(preset, "preset");
        this.mActivatedPreset = preset;
    }

    /* renamed from: getActivatedPresetIndex, reason: from getter */
    public final short getMActivatedPresetIndex() {
        return this.mActivatedPresetIndex;
    }

    /* renamed from: getActivatedPreset, reason: from getter */
    public final InstalledPreset getMActivatedPreset() {
        return this.mActivatedPreset;
    }

    public final void readRemoteRssi() {
        if (System.currentTimeMillis() - this.lastRssiUpdateTime > this.rssiUpdateThrottleTime) {
            ContextKt.checkBluetoothPermissionAndInvoke(this.context, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.readRemoteRssi.1
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
                    BluetoothGatt bluetoothGatt = BleManager.this.mBluetoothGatt;
                    if (bluetoothGatt != null) {
                        bluetoothGatt.readRemoteRssi();
                    }
                }
            });
            this.lastRssiUpdateTime = System.currentTimeMillis();
        }
    }

    private final void requestHighPriority() {
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.mBluetoothWriteCharacteristic;
        if (!CHARACTERISTIC_UUID.equals(bluetoothGattCharacteristic != null ? bluetoothGattCharacteristic.getUuid() : null) && System.currentTimeMillis() - this.lastHightPriorityRequestTime > this.hightPriorityRequestThrottleTime) {
            ContextKt.checkBluetoothPermissionAndInvoke(this.context, new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.requestHighPriority.1
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
                    BluetoothGatt bluetoothGatt = BleManager.this.mBluetoothGatt;
                    if (bluetoothGatt != null) {
                        bluetoothGatt.requestConnectionPriority(1);
                    }
                }
            });
            this.lastHightPriorityRequestTime = System.currentTimeMillis();
        }
    }

    /* renamed from: isDataUploading, reason: from getter */
    public final boolean getMUploadingData() {
        return this.mUploadingData;
    }

    public final void checkDoUploadFile(final Function1<? super DownloadGetStatusModel, Unit> result) {
        Intrinsics.checkNotNullParameter(result, "result");
        Observable<ByteArrayOutputStream> observableExecuteReDownloadFile = executeReDownloadFile();
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.checkDoUploadFile.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray, "it.toByteArray()");
                DownloadGetStatusResponse downloadGetStatusResponse = new DownloadGetStatusResponse(byteArray);
                if (downloadGetStatusResponse.getStatus()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes = downloadGetStatusResponse.getBytes();
                    fileLogger.i("Response is correct: " + (bytes != null ? BleHelperKt.toHex(bytes) : null), new Object[0]);
                    result.invoke(downloadGetStatusResponse.getResponseModel());
                    return;
                }
                result.invoke(null);
            }
        };
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda118
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.checkDoUploadFile$lambda$152(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.checkDoUploadFile.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
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
                result.invoke(null);
            }
        };
        compositeDisposable.add(observableExecuteReDownloadFile.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda129
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.checkDoUploadFile$lambda$153(function12, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkDoUploadFile$lambda$152(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkDoUploadFile$lambda$153(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void downloadStartGroup(final FileType fileType, final int countFile, final byte[] fileUpload, final int packageId, final int packageVersion) {
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        Intrinsics.checkNotNullParameter(fileUpload, "fileUpload");
        Observable<ByteArrayOutputStream> observableExecuteStartDownloadGroup = executeStartDownloadGroup(countFile);
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable<ByteArrayOutputStream> observableObserveOn = observableExecuteStartDownloadGroup.observeOn(AndroidSchedulers.mainThread());
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.downloadStartGroup.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                DownloadStartGroupBleResponse downloadStartGroupBleResponse = new DownloadStartGroupBleResponse(byteArrayOutputStream.toByteArray());
                if (downloadStartGroupBleResponse.getStatus()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes = downloadStartGroupBleResponse.getBytes();
                    fileLogger.i("Response is correct: " + (bytes != null ? BleHelperKt.toHex(bytes) : null), new Object[0]);
                    BleManager.this.downloadStartFile(fileUpload, fileType, packageId, packageVersion);
                    return;
                }
                BleManager bleManager = BleManager.this;
                final BleManager bleManager2 = BleManager.this;
                final FileType fileType2 = fileType;
                final int i = countFile;
                final byte[] bArr = fileUpload;
                final int i2 = packageId;
                final int i3 = packageVersion;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.downloadStartGroup.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                        bleManager2.downloadStartGroup(fileType2, i, bArr, i2, i3);
                    }
                });
            }
        };
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda71
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.downloadStartGroup$lambda$154(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.downloadStartGroup.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                BleManager.this.mFileLogger.i(String.valueOf(th.getMessage()), new Object[0]);
                BleManager bleManager = BleManager.this;
                final BleManager bleManager2 = BleManager.this;
                final FileType fileType2 = fileType;
                final int i = countFile;
                final byte[] bArr = fileUpload;
                final int i2 = packageId;
                final int i3 = packageVersion;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.downloadStartGroup.2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                        bleManager2.downloadStartGroup(fileType2, i, bArr, i2, i3);
                    }
                });
            }
        };
        compositeDisposable.add(observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda72
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.downloadStartGroup$lambda$155(function12, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void downloadStartGroup$lambda$154(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void downloadStartGroup$lambda$155(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void downloadStartFile(final byte[] file, final FileType fileType, final int packageId, final int packageVersion) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        if (getMStateConnected()) {
            Observable<ByteArrayOutputStream> observableObserveOn = executeUploadStartFile(packageId, packageVersion, file, fileType).observeOn(AndroidSchedulers.mainThread());
            CompositeDisposable compositeDisposable = this.mCompositeDisposable;
            final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.downloadStartFile.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                    invoke2(byteArrayOutputStream);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                    DownloadStartFileBleResponse downloadStartFileBleResponse = new DownloadStartFileBleResponse(byteArrayOutputStream.toByteArray());
                    if (downloadStartFileBleResponse.getStatus()) {
                        FileLogger fileLogger = BleManager.this.mFileLogger;
                        byte[] bytes = downloadStartFileBleResponse.getBytes();
                        fileLogger.i("Response is correct: " + (bytes != null ? BleHelperKt.toHex(bytes) : null), new Object[0]);
                        BleManager.this.executeDownloadWriteBlock(file, (short) 0, fileType);
                        return;
                    }
                    BleManager bleManager = BleManager.this;
                    final BleManager bleManager2 = BleManager.this;
                    final byte[] bArr = file;
                    final FileType fileType2 = fileType;
                    final int i = packageId;
                    final int i2 = packageVersion;
                    bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.downloadStartFile.1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                            bleManager2.downloadStartFile(bArr, fileType2, i, i2);
                        }
                    });
                }
            };
            Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda109
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BleManager.downloadStartFile$lambda$156(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.downloadStartFile.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                    BleManager bleManager = BleManager.this;
                    final BleManager bleManager2 = BleManager.this;
                    final byte[] bArr = file;
                    final FileType fileType2 = fileType;
                    final int i = packageId;
                    final int i2 = packageVersion;
                    bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.downloadStartFile.2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                            bleManager2.downloadStartFile(bArr, fileType2, i, i2);
                        }
                    });
                }
            };
            compositeDisposable.add(observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda110
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BleManager.downloadStartFile$lambda$157(function12, obj);
                }
            }));
            return;
        }
        sendOnCancelDownloadEvent(fileType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void downloadStartFile$lambda$156(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void downloadStartFile$lambda$157(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void executeDownloadWriteBlock(final byte[] file, final short blockNumber, final FileType fileType) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        sendEventChangeUI(fileType);
        final Ref.IntRef intRef = new Ref.IntRef();
        Observable observableDefer = Observable.defer(new Callable() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda42
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return BleManager.executeDownloadWriteBlock$lambda$158(this.f$0, blockNumber, file, intRef, fileType);
            }
        });
        Intrinsics.checkNotNullExpressionValue(observableDefer, "defer {\n            mOut…)\n            }\n        }");
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable observableSubscribeOn = observableDefer.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.managers.BleManager.executeDownloadWriteBlock.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                BleManager.this.mUploadingData = true;
            }
        };
        Observable observableDoOnSubscribe = observableSubscribeOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda43
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeDownloadWriteBlock$lambda$159(function1, obj);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.executeDownloadWriteBlock.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                BleManager.this.mFileLogger.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                if (BleHelper.checkCrc(byteArray)) {
                    BleManager.this.commitDownloadFile(fileType);
                    return;
                }
                if (intRef.element <= 2) {
                    BleManager.this.sendOnCancelDownloadEvent(fileType);
                    return;
                }
                BleManager bleManager = BleManager.this;
                final BleManager bleManager2 = BleManager.this;
                final byte[] bArr = file;
                final FileType fileType2 = fileType;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.executeDownloadWriteBlock.2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                        BleManager bleManager3 = bleManager2;
                        bleManager3.executeDownloadWriteBlock(bArr, (short) bleManager3.mCurrentBlock, fileType2);
                    }
                });
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda45
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeDownloadWriteBlock$lambda$160(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.executeDownloadWriteBlock.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                BleManager.this.sendOnCancelDownloadEvent(fileType);
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
            }
        };
        compositeDisposable.add(observableDoOnSubscribe.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda46
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.executeDownloadWriteBlock$lambda$161(function13, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource executeDownloadWriteBlock$lambda$158(BleManager this$0, short s, byte[] file, Ref.IntRef tryIndex, FileType fileType) {
        Observable observableSubscribeOn;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(file, "$file");
        Intrinsics.checkNotNullParameter(tryIndex, "$tryIndex");
        Intrinsics.checkNotNullParameter(fileType, "$fileType");
        this$0.mOutputStream.reset();
        try {
            this$0.mCurrentBlock = s;
            this$0.mErrorsCount = 0;
            this$0.lastSendBlock = -1;
            double dCeil = Math.ceil(file.length / Variables.INSTANCE.getBLOCK_SIZE());
            this$0.fileDownloadSize = dCeil;
            List<byte[]> listTakeDataBlock = WriteBlockFileBleRequestNew.INSTANCE.takeDataBlock(file);
            while (true) {
                int i = this$0.mCurrentBlock;
                if (i < dCeil && this$0.mUploadingData) {
                    Integer num = this$0.lastSendBlock;
                    if (num == null || num.intValue() != i) {
                        this$0.lastSendBlock = Integer.valueOf(this$0.mCurrentBlock);
                        tryIndex.element = 0;
                        WriteBlockFileBleRequestNew writeBlockFileBleRequestNew = new WriteBlockFileBleRequestNew(listTakeDataBlock, this$0.mCurrentBlock);
                        this$0.sendByteArray = writeBlockFileBleRequestNew.getBytes(!UploadFilesService.INSTANCE.getServiceStatus());
                        FileLogger fileLogger = this$0.mFileLogger;
                        byte[] bArr = writeBlockFileBleRequestNew.getDeсryptoMessage();
                        if (bArr == null) {
                            bArr = new byte[0];
                        }
                        fileLogger.i("commandRequest: " + Hex.bytesToStringUppercase(bArr), new Object[0]);
                    }
                    this$0.executeUploadSoundNew(this$0.sendByteArray);
                    tryIndex.element++;
                    if (tryIndex.element > 5) {
                        break;
                    }
                    if (this$0.mErrorsCount == 10) {
                        this$0.sendOnCancelDownloadEvent(fileType);
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (Exception unused) {
            observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        }
        if (tryIndex.element > 3) {
            this$0.TIME_FIX_DELAY += 50;
            return Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        }
        observableSubscribeOn = Observable.just(this$0.mOutputStream).subscribeOn(Schedulers.from(this$0.mExecutorService));
        return observableSubscribeOn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeDownloadWriteBlock$lambda$159(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeDownloadWriteBlock$lambda$160(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeDownloadWriteBlock$lambda$161(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void commitDownloadFile(final FileType fileType) {
        Observable<ByteArrayOutputStream> observableExecuteDownloadCommitFile = executeDownloadCommitFile();
        CompositeDisposable compositeDisposable = this.mCompositeDisposable;
        Observable<ByteArrayOutputStream> observableSubscribeOn = observableExecuteDownloadCommitFile.subscribeOn(Schedulers.from(this.mExecutorService));
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager.commitDownloadFile.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] bytes = byteArrayOutputStream.toByteArray();
                Intrinsics.checkNotNullExpressionValue(bytes, "bytes");
                DownloadFileCommitResponse downloadFileCommitResponse = new DownloadFileCommitResponse(bytes);
                if (downloadFileCommitResponse.getStatus()) {
                    FileLogger fileLogger = BleManager.this.mFileLogger;
                    byte[] bytes2 = downloadFileCommitResponse.getBytes();
                    fileLogger.i("Response is correct: " + (bytes2 != null ? BleHelperKt.toHex(bytes2) : null), new Object[0]);
                    BleManager.this.sendEventCommitFile(fileType);
                    return;
                }
                BleManager bleManager = BleManager.this;
                final BleManager bleManager2 = BleManager.this;
                final FileType fileType2 = fileType;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.commitDownloadFile.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                        bleManager2.commitDownloadFile(fileType2);
                    }
                });
            }
        };
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda68
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.commitDownloadFile$lambda$162(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager.commitDownloadFile.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable it) {
                FileLogger fileLogger = BleManager.this.mFileLogger;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                fileLogger.e(it);
                BleManager bleManager = BleManager.this;
                final BleManager bleManager2 = BleManager.this;
                final FileType fileType2 = fileType;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager.commitDownloadFile.2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                        bleManager2.commitDownloadFile(fileType2);
                    }
                });
            }
        };
        compositeDisposable.add(observableSubscribeOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda69
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.commitDownloadFile$lambda$163(function12, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void commitDownloadFile$lambda$162(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void commitDownloadFile$lambda$163(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void onApplyPackage(final FileType fileType) {
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        Observable<ByteArrayOutputStream> observableObserveOn = executeApplyUploadPackage().observeOn(AndroidSchedulers.mainThread());
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.managers.BleManager$onApplyPackage$commandDisposable$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] bytes = byteArrayOutputStream.toByteArray();
                Intrinsics.checkNotNullExpressionValue(bytes, "bytes");
                ApplyUploadSoundPackageResponse applyUploadSoundPackageResponse = new ApplyUploadSoundPackageResponse(bytes);
                if (applyUploadSoundPackageResponse.getStatus()) {
                    FileLogger fileLogger = this.this$0.mFileLogger;
                    byte[] bytes2 = applyUploadSoundPackageResponse.getBytes();
                    fileLogger.i("Response is correct: " + (bytes2 != null ? BleHelperKt.toHex(bytes2) : null), new Object[0]);
                    this.this$0.sendOnApplyPackageEvent(fileType);
                    return;
                }
                BleManager bleManager = this.this$0;
                final BleManager bleManager2 = this.this$0;
                final FileType fileType2 = fileType;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager$onApplyPackage$commandDisposable$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                        bleManager2.onApplyPackage(fileType2);
                    }
                });
            }
        };
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda22
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.onApplyPackage$lambda$164(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.BleManager$onApplyPackage$commandDisposable$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                Timber.INSTANCE.e(th);
                Toast.makeText(this.this$0.getContext(), R.string.warning_unknown_error, 1).show();
                BleManager bleManager = this.this$0;
                final BleManager bleManager2 = this.this$0;
                final FileType fileType2 = fileType;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.managers.BleManager$onApplyPackage$commandDisposable$2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                        bleManager2.onApplyPackage(fileType2);
                    }
                });
            }
        };
        Disposable commandDisposable = observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.BleManager$$ExternalSyntheticLambda33
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleManager.onApplyPackage$lambda$165(function12, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(commandDisposable, "commandDisposable");
        addCommandDisposable(commandDisposable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onApplyPackage$lambda$164(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onApplyPackage$lambda$165(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendOnApplyPackageEvent(FileType fileType) {
        switch (WhenMappings.$EnumSwitchMapping$0[fileType.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                EventBus.getDefault().post(new OnApplySoundPackageSendEvent());
                break;
            case 5:
                EventBus.getDefault().post(new FirmwareFileCommittedEvent());
                break;
            case 6:
                EventBus.getDefault().post(new UploadSguSoundAppliedEvent((short) 0));
                break;
            case 7:
                EventBus.getDefault().post(new UpdateCanConfigurationsSuccessfulEvent(true));
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendEventCommitFile(FileType fileType) {
        switch (WhenMappings.$EnumSwitchMapping$0[fileType.ordinal()]) {
            case 1:
            case 4:
                EventBus.getDefault().post(new UploadSoundPackageApply(true));
                break;
            case 2:
                EventBus.getDefault().post(new UploadSoundPackageSamplesSuccessfulEventNew(true));
                break;
            case 3:
                EventBus.getDefault().post(new StartUploadRulesPackageEvent());
                break;
            case 5:
                onApplyPackage(fileType);
                break;
            case 6:
                if (fileType.getFileLast() > 0) {
                    EventBus.getDefault().post(new UploadSguSoundAppliedEvent((short) 0));
                    break;
                } else {
                    onApplyPackage(fileType);
                    break;
                }
            case 7:
                onApplyPackage(fileType);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendOnCancelDownloadEvent(FileType fileType) {
        int i = WhenMappings.$EnumSwitchMapping$0[fileType.ordinal()];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            EventBus.getDefault().post(new OnCancelDownloadPresetEvent());
        } else if (i == 5) {
            EventBus.getDefault().post(new UpdateFirmwareErrorEvent());
        } else {
            if (i != 7) {
                return;
            }
            EventBus.getDefault().post(new UpdateCanConfigurationsErrorEvent(false, true));
        }
    }

    private final void sendEventChangeUI(FileType fileType) {
        EventBus.getDefault().post(new SendUploadChangeUiEvent(fileType));
    }
}
