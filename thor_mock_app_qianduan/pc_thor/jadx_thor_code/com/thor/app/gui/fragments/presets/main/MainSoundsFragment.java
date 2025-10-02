package com.thor.app.gui.fragments.presets.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.FragmentMainSoundsBinding;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.BooleanWearableDataLayer;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.IntegerWearableDataLayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.common.util.Hex;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.Wearable;
import com.thor.app.bus.events.BluetoothDeviceConnectionChangedEvent;
import com.thor.app.bus.events.CheckPlaceholderHintEvent;
import com.thor.app.bus.events.CryptoInitErrorEvent;
import com.thor.app.bus.events.FetchConfigEvent;
import com.thor.app.bus.events.FormatFlashExecuteEvent;
import com.thor.app.bus.events.LoaderChangedEvent;
import com.thor.app.bus.events.OpenSettingsEvent;
import com.thor.app.bus.events.bluetooth.firmware.ApplyUpdateFirmwareSuccessfulEventOld;
import com.thor.app.bus.events.mainpreset.DeleteEnabledMainPresetEvent;
import com.thor.app.bus.events.mainpreset.StartDownloadDamagePck;
import com.thor.app.bus.events.mainpreset.SwitchedPresetEvent;
import com.thor.app.bus.events.mainpreset.UpdateDamagePckEvent;
import com.thor.app.bus.events.mainpreset.WriteInstalledPresetsSuccessfulEvent;
import com.thor.app.bus.events.sgu.ActiveAdapterAfterSgu;
import com.thor.app.bus.events.shop.main.DeleteSoundDone;
import com.thor.app.bus.events.shop.main.DownloadSettingsFileSuccessEvent;
import com.thor.app.bus.events.shop.main.UploadSoundPackageSuccessfulEvent;
import com.thor.app.databinding.model.DriveSelectStatus;
import com.thor.app.gui.activities.main.MainActivityViewModel;
import com.thor.app.gui.activities.preset.PresetSoundSettingsActivity;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.activities.updatecheck.UpdateAppActivity;
import com.thor.app.gui.adapters.itemtouch.ItemMoveCallback;
import com.thor.app.gui.adapters.main.MainSoundPackagesDiffUtilCallback;
import com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment;
import com.thor.app.gui.fragments.presets.main.MainSoundsFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.CarManager;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.entity.ShopSoundPackageEntity;
import com.thor.app.settings.Settings;
import com.thor.app.utils.data.DataLayerHelper;
import com.thor.app.utils.extensions.ContextKt;
import com.thor.app.utils.security.DeviceLockingUtilsKt;
import com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter;
import com.thor.basemodule.gui.fragments.EventBusFragment;
import com.thor.businessmodule.bluetooth.model.other.DeviceSettings;
import com.thor.businessmodule.bluetooth.model.other.DriveMode;
import com.thor.businessmodule.bluetooth.model.other.HardwareProfile;
import com.thor.businessmodule.bluetooth.model.other.InstalledPreset;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresets;
import com.thor.businessmodule.bluetooth.model.other.InstalledSoundPackage;
import com.thor.businessmodule.bluetooth.model.other.UniversalDataParameter;
import com.thor.businessmodule.bluetooth.response.other.InstalledSoundPackagesResponse;
import com.thor.businessmodule.bluetooth.response.other.ReadDriveModesBleResponse;
import com.thor.businessmodule.bluetooth.response.other.ReadSettingsBleResponse;
import com.thor.businessmodule.bluetooth.response.other.ReadUniversalDataStatisticBleResponse;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import com.thor.businessmodule.model.MainPresetPackage;
import com.thor.networkmodule.model.DriveSelect;
import com.thor.networkmodule.model.ModeType;
import com.thor.networkmodule.model.StatisticsPack;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.DriveSelectResponse;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: MainSoundsFragment.kt */
@Metadata(d1 = {"\u0000¤\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\n\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u0089\u00012\u00020\u00012\u00020\u0002:\u0002\u0089\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010;\u001a\u00020<H\u0002J\u0018\u0010=\u001a\u00020<2\u000e\u0010>\u001a\n\u0012\u0004\u0012\u00020@\u0018\u00010?H\u0002J\u0010\u0010A\u001a\u00020<2\u0006\u0010B\u001a\u00020CH\u0003J\u0010\u0010D\u001a\u00020<2\u0006\u0010B\u001a\u00020EH\u0002J\b\u0010F\u001a\u00020<H\u0002J\u001e\u0010G\u001a\u00020\u001f2\u0006\u0010H\u001a\u00020I2\f\u0010J\u001a\b\u0012\u0004\u0012\u00020K0?H\u0002J\u0012\u0010L\u001a\u00020<2\b\u0010M\u001a\u0004\u0018\u00010NH\u0016J\"\u0010O\u001a\u00020<2\u0006\u0010P\u001a\u00020&2\u0006\u0010Q\u001a\u00020&2\b\u0010R\u001a\u0004\u0018\u00010SH\u0016J$\u0010T\u001a\u00020U2\u0006\u0010V\u001a\u00020W2\b\u0010X\u001a\u0004\u0018\u00010Y2\b\u0010M\u001a\u0004\u0018\u00010NH\u0016J\u0010\u0010Z\u001a\u00020<2\u0006\u0010[\u001a\u00020\\H\u0016J\b\u0010]\u001a\u00020<H\u0016J\b\u0010^\u001a\u00020<H\u0002J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020aH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020bH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020cH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020dH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020eH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020fH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020gH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020hH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020iH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020jH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020kH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020lH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020mH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020nH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020oH\u0007J\u0010\u0010_\u001a\u00020<2\u0006\u0010`\u001a\u00020pH\u0007J\b\u0010q\u001a\u00020<H\u0016J\b\u0010r\u001a\u00020<H\u0016J\u001a\u0010s\u001a\u00020<2\u0006\u0010t\u001a\u00020U2\b\u0010M\u001a\u0004\u0018\u00010NH\u0016J\u0010\u0010u\u001a\u00020<2\u0006\u0010v\u001a\u00020wH\u0002J\u0006\u0010x\u001a\u00020<J\u001e\u0010y\u001a\u00020<2\f\u0010J\u001a\b\u0012\u0004\u0012\u00020K0?2\u0006\u0010z\u001a\u00020CH\u0002J\u0010\u0010{\u001a\u00020|2\u0006\u0010}\u001a\u00020&H\u0003J\b\u0010~\u001a\u00020<H\u0002J\u0011\u0010\u007f\u001a\u00020<2\u0007\u0010\u0080\u0001\u001a\u00020wH\u0002J\u0013\u0010\u0081\u0001\u001a\u00020<2\b\u0010\u0082\u0001\u001a\u00030\u0083\u0001H\u0003J\u0019\u0010\u0084\u0001\u001a\u00020<2\u000e\u0010\u0085\u0001\u001a\t\u0012\u0005\u0012\u00030\u0086\u00010?H\u0003J \u0010\u0087\u0001\u001a\u00020<2\r\u0010\u0088\u0001\u001a\b\u0012\u0004\u0012\u00020K0?2\u0006\u0010z\u001a\u00020CH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0014\u001a\u00020\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u000e\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0019\u001a\u00020\u001a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u000e\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010 \u001a\u00020!8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u000e\u001a\u0004\b\"\u0010#R\u000e\u0010%\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010+\u001a\u0004\u0018\u00010,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001b\u00101\u001a\u0002028BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b5\u0010\u000e\u001a\u0004\b3\u00104R\u001b\u00106\u001a\u0002078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b:\u0010\u000e\u001a\u0004\b8\u00109¨\u0006\u008a\u0001"}, d2 = {"Lcom/thor/app/gui/fragments/presets/main/MainSoundsFragment;", "Lcom/thor/basemodule/gui/fragments/EventBusFragment;", "Lcom/google/android/gms/wearable/DataClient$OnDataChangedListener;", "()V", "_binding", "Lcom/carsystems/thor/app/databinding/FragmentMainSoundsBinding;", "binding", "getBinding", "()Lcom/carsystems/thor/app/databinding/FragmentMainSoundsBinding;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "dataLayerManager", "Lcom/thor/app/managers/DataLayerManager;", "getDataLayerManager", "()Lcom/thor/app/managers/DataLayerManager;", "dataLayerManager$delegate", "database", "Lcom/thor/app/room/ThorDatabase;", "getDatabase", "()Lcom/thor/app/room/ThorDatabase;", "database$delegate", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "isFirstLoad", "", "mAdapter", "Lcom/thor/app/gui/adapters/main/MainSoundPackagesRvAdapter;", "getMAdapter", "()Lcom/thor/app/gui/adapters/main/MainSoundPackagesRvAdapter;", "mAdapter$delegate", "mErrorCount", "", "mIsStatisticsReadDone", "mLoadedData", "mLoadingData", "mRequestLoadData", "sendDiagnosticUniversalStatistic", "Lkotlinx/coroutines/Job;", "getSendDiagnosticUniversalStatistic", "()Lkotlinx/coroutines/Job;", "setSendDiagnosticUniversalStatistic", "(Lkotlinx/coroutines/Job;)V", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "viewModel", "Lcom/thor/app/gui/activities/main/MainActivityViewModel;", "getViewModel", "()Lcom/thor/app/gui/activities/main/MainActivityViewModel;", "viewModel$delegate", "fetchDeviceSettings", "", "handleDriveSelect", "driveSelects", "", "Lcom/thor/networkmodule/model/DriveSelect;", "handleReadInstalledPresetsResponse", "response", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "handleReadSettingsResponse", "Lcom/thor/businessmodule/bluetooth/response/other/ReadSettingsBleResponse;", "initAdapter", "isPresetInStore", "preset", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPreset;", "shopSounds", "Lcom/thor/app/room/entity/ShopSoundPackageEntity;", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "onDataChanged", "dataEvents", "Lcom/google/android/gms/wearable/DataEventBuffer;", "onDestroy", "onLoadData", "onMessageEvent", "event", "Lcom/thor/app/bus/events/BluetoothDeviceConnectionChangedEvent;", "Lcom/thor/app/bus/events/CheckPlaceholderHintEvent;", "Lcom/thor/app/bus/events/CryptoInitErrorEvent;", "Lcom/thor/app/bus/events/FetchConfigEvent;", "Lcom/thor/app/bus/events/FormatFlashExecuteEvent;", "Lcom/thor/app/bus/events/OpenSettingsEvent;", "Lcom/thor/app/bus/events/bluetooth/firmware/ApplyUpdateFirmwareSuccessfulEventOld;", "Lcom/thor/app/bus/events/mainpreset/DeleteEnabledMainPresetEvent;", "Lcom/thor/app/bus/events/mainpreset/StartDownloadDamagePck;", "Lcom/thor/app/bus/events/mainpreset/SwitchedPresetEvent;", "Lcom/thor/app/bus/events/mainpreset/UpdateDamagePckEvent;", "Lcom/thor/app/bus/events/mainpreset/WriteInstalledPresetsSuccessfulEvent;", "Lcom/thor/app/bus/events/sgu/ActiveAdapterAfterSgu;", "Lcom/thor/app/bus/events/shop/main/DeleteSoundDone;", "Lcom/thor/app/bus/events/shop/main/DownloadSettingsFileSuccessEvent;", "Lcom/thor/app/bus/events/shop/main/UploadSoundPackageSuccessfulEvent;", "onResume", "onStop", "onViewCreated", "view", "startSendDiagnosticUniversalStatisticJob", SessionDescription.ATTR_TYPE, "", "stopSendDiagnosticUniversalStatisticJob", "syncShopAndInstalledPresets", "installedPresets", "testRandomDriveMode", "Lcom/thor/businessmodule/bluetooth/model/other/DriveMode$DRIVE_MODE;", TtmlNode.ATTR_ID, "tryFetchDriveSelect", "tryFetchUniversalStatisticData", "statisticType", "trySendStatistics", "mainPresetPackage", "Lcom/thor/businessmodule/model/MainPresetPackage;", "trySendUniversalDataStatistics", "universalDataParameters", "Lcom/thor/businessmodule/bluetooth/model/other/UniversalDataParameter;", "writeOwnPresetForEachInstalledPackage", "soundPackages", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MainSoundsFragment extends EventBusFragment implements DataClient.OnDataChangedListener {
    public static final String BUNDLE_ACTION = "action";

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int REQUEST_SETTINGS = 1001;
    private static boolean delRefresh;
    private FragmentMainSoundsBinding _binding;
    private int mErrorCount;
    private boolean mIsStatisticsReadDone;
    private boolean mLoadedData;
    private boolean mLoadingData;
    private boolean mRequestLoadData;
    private Job sendDiagnosticUniversalStatistic;

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    /* renamed from: mAdapter$delegate, reason: from kotlin metadata */
    private final Lazy mAdapter = LazyKt.lazy(new Function0<MainSoundPackagesRvAdapter>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$mAdapter$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final MainSoundPackagesRvAdapter invoke() {
            return new MainSoundPackagesRvAdapter(new MainSoundPackagesDiffUtilCallback(), this.this$0.getBleManager(), this.this$0.getUsersManager());
        }
    });

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$bleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0.getContext());
        }
    });

    /* renamed from: dataLayerManager$delegate, reason: from kotlin metadata */
    private final Lazy dataLayerManager = LazyKt.lazy(new Function0<DataLayerManager>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$dataLayerManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final DataLayerManager invoke() {
            DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
            Context contextRequireContext = this.this$0.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
            return companion.from(contextRequireContext);
        }
    });

    /* renamed from: database$delegate, reason: from kotlin metadata */
    private final Lazy database = LazyKt.lazy(new Function0<ThorDatabase>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$database$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThorDatabase invoke() {
            ThorDatabase.Companion companion = ThorDatabase.INSTANCE;
            Context contextRequireContext = this.this$0.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
            return companion.from(contextRequireContext);
        }
    });

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            UsersManager.Companion companion = UsersManager.INSTANCE;
            Context contextRequireContext = this.this$0.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
            return companion.from(contextRequireContext);
        }
    });

    /* renamed from: handler$delegate, reason: from kotlin metadata */
    private final Lazy handler = LazyKt.lazy(new Function0<Handler>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$handler$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Handler invoke() {
            return new Handler(Looper.getMainLooper());
        }
    });
    private boolean isFirstLoad = true;

    @JvmStatic
    public static final MainSoundsFragment newInstance(String str) {
        return INSTANCE.newInstance(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$27() {
    }

    public MainSoundsFragment() {
        final MainSoundsFragment mainSoundsFragment = this;
        final Function0 function0 = null;
        this.viewModel = FragmentViewModelLazyKt.createViewModelLazy(mainSoundsFragment, Reflection.getOrCreateKotlinClass(MainActivityViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$special$$inlined$activityViewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = mainSoundsFragment.requireActivity().getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "requireActivity().viewModelStore");
                return viewModelStore;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$special$$inlined$activityViewModels$default$2
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
                CreationExtras defaultViewModelCreationExtras = mainSoundsFragment.requireActivity().getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "requireActivity().defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$special$$inlined$activityViewModels$default$3
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = mainSoundsFragment.requireActivity().getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "requireActivity().defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FragmentMainSoundsBinding getBinding() {
        FragmentMainSoundsBinding fragmentMainSoundsBinding = this._binding;
        if (fragmentMainSoundsBinding != null) {
            return fragmentMainSoundsBinding;
        }
        throw new IllegalArgumentException("Required value was null.".toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MainActivityViewModel getViewModel() {
        return (MainActivityViewModel) this.viewModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MainSoundPackagesRvAdapter getMAdapter() {
        return (MainSoundPackagesRvAdapter) this.mAdapter.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    private final DataLayerManager getDataLayerManager() {
        return (DataLayerManager) this.dataLayerManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ThorDatabase getDatabase() {
        return (ThorDatabase) this.database.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    private final Handler getHandler() {
        return (Handler) this.handler.getValue();
    }

    public final Job getSendDiagnosticUniversalStatistic() {
        return this.sendDiagnosticUniversalStatistic;
    }

    public final void setSendDiagnosticUniversalStatistic(Job job) {
        this.sendDiagnosticUniversalStatistic = job;
    }

    private final void startSendDiagnosticUniversalStatisticJob(short type) {
        Job job = this.sendDiagnosticUniversalStatistic;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        this.sendDiagnosticUniversalStatistic = BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C02661(type, null), 3, null);
    }

    /* compiled from: MainSoundsFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.fragments.presets.main.MainSoundsFragment$startSendDiagnosticUniversalStatisticJob$1", f = "MainSoundsFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$startSendDiagnosticUniversalStatisticJob$1, reason: invalid class name and case insensitive filesystem */
    static final class C02661 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ short $type;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02661(short s, Continuation<? super C02661> continuation) {
            super(2, continuation);
            this.$type = s;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MainSoundsFragment.this.new C02661(this.$type, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02661) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                MainSoundsFragment.this.tryFetchUniversalStatisticData(this.$type);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    public final void stopSendDiagnosticUniversalStatisticJob() {
        Job job = this.sendDiagnosticUniversalStatistic;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = (FragmentMainSoundsBinding) DataBindingUtil.inflate(inflater, R.layout.fragment_main_sounds, container, false);
        View root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return root;
    }

    @Override // com.thor.basemodule.gui.fragments.EventBusFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) throws SecurityException {
        super.onActivityCreated(savedInstanceState);
        initAdapter();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        getBinding().setModel(getViewModel());
        this.mLoadedData = false;
        onLoadData();
        EventBus.getDefault().post(new LoaderChangedEvent(true));
        SwipeRefreshLayout swipeRefreshLayout = getBinding().swipeContainer;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        swipeRefreshLayout.setColorSchemeColors(ContextKt.fetchAttrValue(contextRequireContext, R.attr.colorAccent));
        getBinding().swipeContainer.setProgressBackgroundColorSchemeResource(R.color.colorRefreshLayoutBackground);
        getBinding().swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda0
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                MainSoundsFragment.onViewCreated$lambda$0(this.f$0);
            }
        });
        this.mRequestLoadData = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$0(MainSoundsFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mLoadedData = false;
        this$0.onLoadData();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        this._binding = null;
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Log.i("LOAD_T", "REFRESH " + delRefresh);
        if (delRefresh) {
            Log.i("LOAD_T", "REFRESH1");
            onLoadData();
            delRefresh = false;
        }
        Timber.INSTANCE.i("onResume", new Object[0]);
        Context context = getContext();
        if (context != null) {
            Wearable.getDataClient(context).addListener(this);
        }
        this.mLoadedData = false;
        getViewModel().getIsInstalledPresets().set(getMAdapter().getItemCount() > 0);
        this.mRequestLoadData = true;
        getUsersManager().fetchSoundPackages();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Context context = getContext();
        if (context != null) {
            Wearable.getDataClient(context).removeListener(this);
        }
    }

    @Override // com.google.android.gms.wearable.DataClient.OnDataChangedListener, com.google.android.gms.wearable.DataApi.DataListener
    public void onDataChanged(DataEventBuffer dataEvents) {
        Intrinsics.checkNotNullParameter(dataEvents, "dataEvents");
        Iterator<DataEvent> it = dataEvents.iterator();
        while (it.hasNext()) {
            DataEvent next = it.next();
            if (next.getType() == 1) {
                String path = next.getDataItem().getUri().getPath();
                if (path != null) {
                    int iHashCode = path.hashCode();
                    if (iHashCode != 517212461) {
                        if (iHashCode != 1045446980) {
                            if (iHashCode == 1712145961 && path.equals(IntegerWearableDataLayer.MAIN_PRESET_SELECTED_INDEX_PATH)) {
                                Timber.INSTANCE.i("Receiver data from MAIN_PRESET_SELECTED_INDEX_PATH", new Object[0]);
                                MainSoundPackagesRvAdapter mAdapter = getMAdapter();
                                Integer numConvertFromDataItem = IntegerWearableDataLayer.INSTANCE.convertFromDataItem(next.getDataItem());
                                Intrinsics.checkNotNullExpressionValue(numConvertFromDataItem, "IntegerWearableDataLayer…                        )");
                                mAdapter.onReceiveClickFromWatches(numConvertFromDataItem.intValue());
                            }
                        } else if (path.equals(BooleanWearableDataLayer.CURRENT_ACTIVITY_PATH)) {
                            if (Settings.INSTANCE.isLoadingSoundPackage()) {
                                DataLayerHelper.newInstance(getContext()).onStartUploadingWearableActivity();
                            } else {
                                DataLayerHelper.newInstance(getContext()).onStartMainWearableActivity();
                            }
                        }
                    } else if (path.equals(BooleanWearableDataLayer.CURRENT_DATA_PATH)) {
                        DataLayerManager dataLayerManager = getDataLayerManager();
                        Collection<MainPresetPackage> all = getMAdapter().getAll();
                        Intrinsics.checkNotNullExpressionValue(all, "mAdapter.all");
                        dataLayerManager.sendMainPresetPackages(all);
                    }
                }
                Timber.INSTANCE.d("Unrecognized path: " + path, new Object[0]);
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            this.mRequestLoadData = false;
        }
    }

    private final void initAdapter() {
        getBinding().recyclerView.setItemAnimator(new DefaultItemAnimator());
        getBinding().recyclerView.setAdapter(getMAdapter());
        Context context = getContext();
        if (context != null) {
            getMAdapter().enableSyncData(context);
        }
        getMAdapter().setRecyclerView(getBinding().recyclerView);
        getBinding().nestedScrollView.setOnScrollChangeListener(getMAdapter().getOnNestedScrollListener());
        ItemMoveCallback itemMoveCallback = new ItemMoveCallback(getMAdapter());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemMoveCallback);
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        if (com.thor.basemodule.extensions.ContextKt.isCarUIMode(contextRequireContext)) {
            itemMoveCallback.setIsLongPressDragEnabled(false);
        }
        itemTouchHelper.attachToRecyclerView(getBinding().recyclerView);
        getMAdapter().setOnItemClickListener(new RecyclerCollectionAdapter.OnItemClickListener<MainPresetPackage>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$initAdapter$$inlined$doOnViewPositionClick$1
            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(View view) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(MainPresetPackage item) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(MainPresetPackage item, View view) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(MainPresetPackage item, int position) {
                System.out.println(position);
            }
        });
    }

    private final void onLoadData() {
        Timber.INSTANCE.i("onLoadData", new Object[0]);
        if (this.mLoadedData || getContext() == null) {
            return;
        }
        if (getBleManager().isBleEnabledAndDeviceConnected() && !this.mLoadingData) {
            DataLayerHelper.newInstance(getContext()).sendConnectionData(true);
            this.mLoadingData = true;
            getBleManager().executeHardwareCommand();
        } else {
            this.mLoadingData = false;
            DataLayerHelper.newInstance(getContext()).sendConnectionData(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(DownloadSettingsFileSuccessEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.mLoadingData = false;
        handleReadInstalledPresetsResponse(event.getListPreset());
    }

    private final void handleReadInstalledPresetsResponse(InstalledPresets response) {
        Timber.INSTANCE.i("doHandlePresets", new Object[0]);
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        Single<List<ShopSoundPackageEntity>> singleObserveOn = getDatabase().shopSoundPackageDao().getEntities().observeOn(AndroidSchedulers.mainThread());
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment.handleReadInstalledPresetsResponse.1
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
                MainSoundsFragment.this.getBinding().swipeContainer.setRefreshing(true);
            }
        };
        Single<List<ShopSoundPackageEntity>> singleDoOnTerminate = singleObserveOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda18
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundsFragment.handleReadInstalledPresetsResponse$lambda$6(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda19
            @Override // io.reactivex.functions.Action
            public final void run() {
                MainSoundsFragment.handleReadInstalledPresetsResponse$lambda$7(this.f$0);
            }
        });
        final AnonymousClass3 anonymousClass3 = new AnonymousClass3(response, booleanRef);
        Consumer<? super List<ShopSoundPackageEntity>> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda20
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundsFragment.handleReadInstalledPresetsResponse$lambda$8(anonymousClass3, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment.handleReadInstalledPresetsResponse.4
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
                MainSoundsFragment.this.mLoadingData = false;
            }
        };
        singleDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundsFragment.handleReadInstalledPresetsResponse$lambda$9(function12, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleReadInstalledPresetsResponse$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleReadInstalledPresetsResponse$lambda$7(MainSoundsFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().swipeContainer.setRefreshing(false);
    }

    /* compiled from: MainSoundsFragment.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u001a\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004 \u0005*\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0006"}, d2 = {"<anonymous>", "", "soundPackages", "", "Lcom/thor/app/room/entity/ShopSoundPackageEntity;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$handleReadInstalledPresetsResponse$3, reason: invalid class name */
    static final class AnonymousClass3 extends Lambda implements Function1<List<? extends ShopSoundPackageEntity>, Unit> {
        final /* synthetic */ Ref.BooleanRef $dmgPck;
        final /* synthetic */ InstalledPresets $response;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(InstalledPresets installedPresets, Ref.BooleanRef booleanRef) {
            super(1);
            this.$response = installedPresets;
            this.$dmgPck = booleanRef;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(List<? extends ShopSoundPackageEntity> list) {
            invoke2((List<ShopSoundPackageEntity>) list);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(List<ShopSoundPackageEntity> soundPackages) {
            String pkgImageUrl;
            ModeType modeType;
            MainSoundsFragment.this.getBinding().swipeContainer.setRefreshing(false);
            ArrayList arrayList = new ArrayList();
            LinkedHashSet<InstalledPreset> presets = this.$response.getPresets();
            MainSoundsFragment mainSoundsFragment = MainSoundsFragment.this;
            final Ref.BooleanRef booleanRef = this.$dmgPck;
            for (InstalledPreset installedPreset : presets) {
                Maybe<Boolean> damagePck = mainSoundsFragment.getDatabase().installedSoundPackageDao().getDamagePck(installedPreset.getPackageId());
                final Function1<Boolean, Unit> function1 = new Function1<Boolean, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$handleReadInstalledPresetsResponse$3$1$1
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
                        Ref.BooleanRef booleanRef2 = booleanRef;
                        Intrinsics.checkNotNullExpressionValue(it, "it");
                        booleanRef2.element = it.booleanValue();
                    }
                };
                damagePck.subscribe(new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$handleReadInstalledPresetsResponse$3$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        MainSoundsFragment.AnonymousClass3.invoke$lambda$2$lambda$0(function1, obj);
                    }
                });
                Intrinsics.checkNotNullExpressionValue(soundPackages, "soundPackages");
                for (ShopSoundPackageEntity shopSoundPackageEntity : soundPackages) {
                    if (shopSoundPackageEntity.getId() == installedPreset.getPackageId()) {
                        MainPresetPackage mainPresetPackage = new MainPresetPackage(null, null, null, null, null, false, null, null, null, false, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null);
                        mainPresetPackage.setPackageId(Integer.valueOf(installedPreset.getPackageId()));
                        mainPresetPackage.setName(shopSoundPackageEntity.getPkgName());
                        List<ModeType> modeTypes = shopSoundPackageEntity.getModeTypes();
                        if (modeTypes == null || (modeType = modeTypes.get(installedPreset.getMode() - 1)) == null || (pkgImageUrl = modeType.getImage()) == null) {
                            pkgImageUrl = shopSoundPackageEntity.getPkgImageUrl();
                        }
                        mainPresetPackage.setImageUrl(pkgImageUrl);
                        mainPresetPackage.setModeType(Short.valueOf(installedPreset.getMode()));
                        mainPresetPackage.setDamageItem(booleanRef.element);
                        arrayList.add(mainPresetPackage);
                    }
                }
            }
            if (this.$response.getPresets().isEmpty()) {
                MainSoundsFragment mainSoundsFragment2 = MainSoundsFragment.this;
                Intrinsics.checkNotNullExpressionValue(soundPackages, "soundPackages");
                mainSoundsFragment2.writeOwnPresetForEachInstalledPackage(soundPackages, this.$response);
            }
            MainSoundsFragment.this.getMAdapter().updateAllNew(arrayList);
            MainSoundsFragment.this.getMAdapter().clearActiveStates();
            MainSoundsFragment.this.getMAdapter().setActivatedPreset(this.$response.getCurrentPresetId());
            MainSoundsFragment.this.getBleManager().setActivatedPresetIndex(this.$response.getCurrentPresetId());
            MainSoundsFragment.this.getBleManager().setActivatedPreset(MainSoundsFragment.this.getMAdapter().getActivatedPreset());
            int currentPresetId = this.$response.getCurrentPresetId() - 1;
            if (currentPresetId >= 0 && !MainSoundsFragment.this.getMAdapter().isEmpty() && currentPresetId < MainSoundsFragment.this.getMAdapter().getItemCount()) {
                MainSoundsFragment mainSoundsFragment3 = MainSoundsFragment.this;
                MainPresetPackage item = mainSoundsFragment3.getMAdapter().getItem(currentPresetId);
                Intrinsics.checkNotNullExpressionValue(item, "mAdapter.getItem(index)");
                mainSoundsFragment3.trySendStatistics(item);
            }
            MainSoundsFragment.this.getViewModel().getMoreInfo().set(MainSoundsFragment.this.getMAdapter().getItemCount() < 3);
            MainSoundsFragment.this.mLoadingData = false;
            MainSoundsFragment.this.getViewModel().getIsInstalledPresets().set(MainSoundsFragment.this.getMAdapter().getItemCount() > 0);
            EventBus.getDefault().post(new CheckPlaceholderHintEvent());
            MainSoundsFragment mainSoundsFragment4 = MainSoundsFragment.this;
            Intrinsics.checkNotNullExpressionValue(soundPackages, "soundPackages");
            mainSoundsFragment4.syncShopAndInstalledPresets(soundPackages, this.$response);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2$lambda$0(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleReadInstalledPresetsResponse$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleReadInstalledPresetsResponse$lambda$9(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeOwnPresetForEachInstalledPackage(final List<ShopSoundPackageEntity> soundPackages, final InstalledPresets installedPresets) {
        Timber.INSTANCE.d("writeOwnPresetForEachInstalledPackage", new Object[0]);
        final boolean zIsEmpty = installedPresets.getPresets().isEmpty();
        getBleManager().executeReadInstalledSoundPackagesCommand(new Function1<InstalledSoundPackagesResponse, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment.writeOwnPresetForEachInstalledPackage.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                Intrinsics.checkNotNullParameter(response, "response");
                if (!response.getPackages().getSoundPackages().isEmpty()) {
                    ArrayList<InstalledSoundPackage> soundPackages2 = response.getPackages().getSoundPackages();
                    MainSoundsFragment mainSoundsFragment = this;
                    List<ShopSoundPackageEntity> list = soundPackages;
                    InstalledPresets installedPresets2 = installedPresets;
                    Iterator<T> it = soundPackages2.iterator();
                    while (it.hasNext()) {
                        InstalledPreset installedPreset = new InstalledPreset(((InstalledSoundPackage) it.next()).getPackageId(), (short) 3, (short) 0, 4, null);
                        if (mainSoundsFragment.isPresetInStore(installedPreset, list)) {
                            installedPresets2.getPresets().add(installedPreset);
                        }
                    }
                    InstalledPresets installedPresets3 = installedPresets;
                    installedPresets3.setAmount((short) installedPresets3.getPresets().size());
                    boolean zIsEmpty2 = installedPresets.getPresets().isEmpty();
                    this.mLoadedData = false;
                    if (zIsEmpty && zIsEmpty2) {
                        return;
                    }
                    BleManager.executeWriteInstalledPresetsCommand$default(this.getBleManager(), installedPresets, false, (short) 0, false, null, 30, null);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void syncShopAndInstalledPresets(List<ShopSoundPackageEntity> shopSounds, InstalledPresets installedPresets) {
        ArrayList arrayList = new ArrayList();
        for (InstalledPreset installedPreset : installedPresets.getPresets()) {
            if (!isPresetInStore(installedPreset, shopSounds)) {
                arrayList.add(installedPreset);
            }
        }
        installedPresets.getPresets().removeAll(arrayList);
        installedPresets.setAmount((short) installedPresets.getPresets().size());
        if (!r0.isEmpty()) {
            BleManager.executeWriteInstalledPresetsCommand$default(getBleManager(), installedPresets, false, (short) 0, false, null, 30, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isPresetInStore(InstalledPreset preset, List<ShopSoundPackageEntity> shopSounds) {
        Object next;
        Iterator<T> it = shopSounds.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (((short) ((ShopSoundPackageEntity) next).getId()) == preset.getPackageId()) {
                break;
            }
        }
        return next != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void trySendStatistics(MainPresetPackage mainPresetPackage) {
        Observable<BaseResponse> observableSubscribeOn;
        Observable<BaseResponse> observableObserveOn;
        Timber.INSTANCE.i("trySendStatistics: " + mainPresetPackage, new Object[0]);
        UsersManager usersManager = getUsersManager();
        Integer packageId = mainPresetPackage.getPackageId();
        Intrinsics.checkNotNull(packageId);
        int iIntValue = packageId.intValue();
        Short modeType = mainPresetPackage.getModeType();
        Intrinsics.checkNotNull(modeType);
        Observable<BaseResponse> observableSendStatisticsAboutSoundPackage = usersManager.sendStatisticsAboutSoundPackage(iIntValue, modeType.shortValue());
        if (observableSendStatisticsAboutSoundPackage == null || (observableSubscribeOn = observableSendStatisticsAboutSoundPackage.subscribeOn(Schedulers.io())) == null || (observableObserveOn = observableSubscribeOn.observeOn(AndroidSchedulers.mainThread())) == null) {
            return;
        }
        final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment.trySendStatistics.1
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
                if (!baseResponse.isSuccessful()) {
                    if (baseResponse.getCode() == 888) {
                        FragmentActivity fragmentActivityRequireActivity = MainSoundsFragment.this.requireActivity();
                        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity()");
                        DeviceLockingUtilsKt.onDeviceLocked(fragmentActivityRequireActivity);
                    } else {
                        AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(MainSoundsFragment.this.getContext(), baseResponse.getError(), baseResponse.getCode());
                        if (alertDialogCreateErrorAlertDialog != null) {
                            alertDialogCreateErrorAlertDialog.show();
                        }
                    }
                }
                Timber.INSTANCE.i("sendStatisticsAboutSoundPackage%s", baseResponse.toString());
            }
        };
        Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundsFragment.trySendStatistics$lambda$12(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment.trySendStatistics.2
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
                    AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, MainSoundsFragment.this.getContext(), th.getMessage(), null, 4, null);
                    if (alertDialogCreateErrorAlertDialog$default2 != null) {
                        alertDialogCreateErrorAlertDialog$default2.show();
                    }
                } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, MainSoundsFragment.this.getContext(), th.getMessage(), null, 4, null)) != null) {
                    alertDialogCreateErrorAlertDialog$default.show();
                }
                Timber.INSTANCE.e(th);
            }
        };
        observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundsFragment.trySendStatistics$lambda$13(function12, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void trySendStatistics$lambda$12(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void trySendStatistics$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void tryFetchUniversalStatisticData(short statisticType) {
        if (this.mIsStatisticsReadDone) {
            return;
        }
        if (statisticType == 3) {
            this.mIsStatisticsReadDone = true;
        }
        BleManager bleManager = getBleManager();
        Observable<ByteArrayOutputStream> observableObserveOn = getBleManager().executeReadUniversalDataParametersCommand(statisticType).observeOn(AndroidSchedulers.mainThread());
        final C02671 c02671 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment.tryFetchUniversalStatisticData.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                invoke2(disposable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Disposable disposable) {
                Timber.INSTANCE.i("executeReadUniversalDataParametersCommand start", new Object[0]);
            }
        };
        Observable<ByteArrayOutputStream> observableDoOnTerminate = observableObserveOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundsFragment.tryFetchUniversalStatisticData$lambda$14(c02671, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Action
            public final void run() {
                MainSoundsFragment.tryFetchUniversalStatisticData$lambda$15();
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment.tryFetchUniversalStatisticData.3
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
                Timber.INSTANCE.i("Take data: %s", Hex.bytesToStringUppercase(bytes));
                Intrinsics.checkNotNullExpressionValue(bytes, "bytes");
                ReadUniversalDataStatisticBleResponse readUniversalDataStatisticBleResponse = new ReadUniversalDataStatisticBleResponse(bytes);
                if (readUniversalDataStatisticBleResponse.getStatus()) {
                    Timber.Companion companion = Timber.INSTANCE;
                    Object[] objArr = new Object[1];
                    byte[] bytes2 = readUniversalDataStatisticBleResponse.getBytes();
                    objArr[0] = bytes2 != null ? BleHelperKt.toHex(bytes2) : null;
                    companion.i("Response is correct: %s", objArr);
                    MainSoundsFragment.this.trySendUniversalDataStatistics(readUniversalDataStatisticBleResponse.getUniversalDataParameters());
                    return;
                }
                Timber.INSTANCE.e("Response is not correct: %s", readUniversalDataStatisticBleResponse.getErrorCode());
            }
        };
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundsFragment.tryFetchUniversalStatisticData$lambda$16(function1, obj);
            }
        };
        final C02694 c02694 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment.tryFetchUniversalStatisticData.4
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
        Disposable disposableSubscribe = observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundsFragment.tryFetchUniversalStatisticData$lambda$17(c02694, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(disposableSubscribe, "private fun tryFetchUniv…       })\n        )\n    }");
        bleManager.addCommandDisposable(disposableSubscribe);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tryFetchUniversalStatisticData$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tryFetchUniversalStatisticData$lambda$15() {
        Timber.INSTANCE.i("executeReadUniversalDataParametersCommand finish", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tryFetchUniversalStatisticData$lambda$16(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tryFetchUniversalStatisticData$lambda$17(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void trySendUniversalDataStatistics(List<UniversalDataParameter> universalDataParameters) {
        Observable<BaseResponse> observableSubscribeOn;
        Observable<BaseResponse> observableObserveOn;
        ArrayList arrayList = new ArrayList();
        for (UniversalDataParameter universalDataParameter : universalDataParameters) {
            arrayList.add(new StatisticsPack("0x" + Hex.bytesToStringUppercase(BleHelper.convertShortToByteArray(Short.valueOf(universalDataParameter.getGroupId()))), "0x" + Hex.bytesToStringUppercase(BleHelper.convertShortToByteArray(Short.valueOf(universalDataParameter.getParamId()))), universalDataParameter.getValue(), universalDataParameter.getTime()));
        }
        Observable<BaseResponse> observableSendUniversalDataStatistics = getUsersManager().sendUniversalDataStatistics(arrayList);
        if (observableSendUniversalDataStatistics == null || (observableSubscribeOn = observableSendUniversalDataStatistics.subscribeOn(Schedulers.io())) == null || (observableObserveOn = observableSubscribeOn.observeOn(AndroidSchedulers.mainThread())) == null) {
            return;
        }
        final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment.trySendUniversalDataStatistics.1
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
                if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, MainSoundsFragment.this.getContext(), baseResponse.getError(), null, 4, null)) != null) {
                    alertDialogCreateErrorAlertDialog$default.show();
                }
                Timber.INSTANCE.i(String.valueOf(baseResponse.getCode()), new Object[0]);
                MainSoundsFragment.this.tryFetchUniversalStatisticData((short) 3);
            }
        };
        Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundsFragment.trySendUniversalDataStatistics$lambda$18(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment.trySendUniversalDataStatistics.2
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
                    AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, MainSoundsFragment.this.getContext(), th.getMessage(), null, 4, null);
                    if (alertDialogCreateErrorAlertDialog$default2 != null) {
                        alertDialogCreateErrorAlertDialog$default2.show();
                    }
                } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, MainSoundsFragment.this.getContext(), th.getMessage(), null, 4, null)) != null) {
                    alertDialogCreateErrorAlertDialog$default.show();
                }
                Timber.INSTANCE.e(th);
                MainSoundsFragment.this.mIsStatisticsReadDone = false;
            }
        };
        observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundsFragment.trySendUniversalDataStatistics$lambda$19(function12, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void trySendUniversalDataStatistics$lambda$18(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void trySendUniversalDataStatistics$lambda$19(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void fetchDeviceSettings() {
        if (this.isFirstLoad) {
            startSendDiagnosticUniversalStatisticJob((short) 1);
            this.isFirstLoad = false;
        }
        tryFetchDriveSelect();
    }

    private final void handleReadSettingsResponse(ReadSettingsBleResponse response) {
        DeviceSettings deviceSettings = response.getDeviceSettings();
        if (deviceSettings == null || !deviceSettings.isDriveSelectActive()) {
            return;
        }
        tryFetchDriveSelect();
    }

    private final void tryFetchDriveSelect() {
        if (isAdded()) {
            HardwareProfile hardwareProfile = Settings.getHardwareProfile();
            Short shValueOf = hardwareProfile != null ? Short.valueOf(hardwareProfile.getVersionFirmware()) : null;
            if (!Settings.INSTANCE.isDeviceHasCanFileData() || shValueOf == null || shValueOf.shortValue() < 346) {
                return;
            }
            CarManager.Companion companion = CarManager.INSTANCE;
            Context contextRequireContext = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
            CarManager carManagerFrom = companion.from(contextRequireContext);
            Observable<DriveSelectResponse> observableFetchDriveSelect = carManagerFrom.fetchDriveSelect();
            final Function1<DriveSelectResponse, Unit> function1 = new Function1<DriveSelectResponse, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$tryFetchDriveSelect$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(DriveSelectResponse driveSelectResponse) {
                    invoke2(driveSelectResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(DriveSelectResponse driveSelectResponse) {
                    if (driveSelectResponse.isSuccessful()) {
                        this.this$0.handleDriveSelect(driveSelectResponse.getItems());
                        return;
                    }
                    AlertDialog alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this.this$0.getContext(), driveSelectResponse.getError(), null, 4, null);
                    if (alertDialogCreateErrorAlertDialog$default != null) {
                        alertDialogCreateErrorAlertDialog$default.show();
                    }
                }
            };
            Consumer<? super DriveSelectResponse> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda16
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainSoundsFragment.tryFetchDriveSelect$lambda$24$lambda$21(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$tryFetchDriveSelect$1$2
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
                        AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this.this$0.getContext(), th.getMessage(), null, 4, null);
                        if (alertDialogCreateErrorAlertDialog$default2 != null) {
                            alertDialogCreateErrorAlertDialog$default2.show();
                            return;
                        }
                        return;
                    }
                    if (!Intrinsics.areEqual(th.getMessage(), "HTTP 500") || (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this.this$0.getContext(), th.getMessage(), null, 4, null)) == null) {
                        return;
                    }
                    alertDialogCreateErrorAlertDialog$default.show();
                }
            };
            carManagerFrom.addDisposable(observableFetchDriveSelect.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda17
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainSoundsFragment.tryFetchDriveSelect$lambda$24$lambda$22(function12, obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tryFetchDriveSelect$lambda$24$lambda$21(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tryFetchDriveSelect$lambda$24$lambda$22(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleDriveSelect(final List<DriveSelect> driveSelects) {
        BleManager bleManager = getBleManager();
        Observable<ByteArrayOutputStream> observableObserveOn = getBleManager().executeReadDriveModes().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment.handleDriveSelect.1
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
                ArrayList<DriveSelectStatus> arrayList = new ArrayList<>();
                ReadDriveModesBleResponse readDriveModesBleResponse = new ReadDriveModesBleResponse(byteArrayOutputStream.toByteArray());
                if (!readDriveModesBleResponse.getStatus()) {
                    BleManager bleManager2 = this.getBleManager();
                    final MainSoundsFragment mainSoundsFragment = this;
                    final List<DriveSelect> list = driveSelects;
                    bleManager2.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment.handleDriveSelect.1.3
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
                            mainSoundsFragment.handleDriveSelect(list);
                        }
                    });
                } else {
                    List<DriveSelect> list2 = driveSelects;
                    if (list2 != null) {
                        CollectionsKt.sortedWith(list2, new Comparator() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$handleDriveSelect$1$invoke$$inlined$sortedBy$1
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // java.util.Comparator
                            public final int compare(T t, T t2) {
                                return ComparisonsKt.compareValues(Integer.valueOf(((DriveSelect) t).getRank()), Integer.valueOf(((DriveSelect) t2).getRank()));
                            }
                        });
                    }
                    ArrayList<DriveMode> driveModes = readDriveModesBleResponse.getDriveModes();
                    if (driveModes != null) {
                        List<DriveSelect> list3 = driveSelects;
                        Iterator<DriveMode> it = driveModes.iterator();
                        while (it.hasNext()) {
                            DriveMode next = it.next();
                            if (list3 != null) {
                                for (DriveSelect driveSelect : list3) {
                                    if (driveSelect.getId() == next.getModeId()) {
                                        arrayList.add(new DriveSelectStatus(driveSelect.getId(), driveSelect.getName(), next.getModeValue().getValue(), driveSelect.getRank()));
                                    }
                                }
                            }
                        }
                    }
                }
                ArrayList<DriveSelectStatus> arrayList2 = arrayList;
                if (arrayList2.size() > 1) {
                    CollectionsKt.sortWith(arrayList2, new Comparator() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$handleDriveSelect$1$invoke$$inlined$sortBy$1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            return ComparisonsKt.compareValues(Integer.valueOf(((DriveSelectStatus) t).getRank()), Integer.valueOf(((DriveSelectStatus) t2).getRank()));
                        }
                    });
                }
                this.getMAdapter().enableDriveSelect(arrayList);
            }
        };
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundsFragment.handleDriveSelect$lambda$25(function1, obj);
            }
        };
        final AnonymousClass2 anonymousClass2 = new AnonymousClass2(Timber.INSTANCE);
        Disposable disposableSubscribe = observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundsFragment.handleDriveSelect$lambda$26(anonymousClass2, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(disposableSubscribe, "private fun handleDriveS…imber::e)\n        )\n    }");
        bleManager.addCommandDisposable(disposableSubscribe);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleDriveSelect$lambda$25(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: MainSoundsFragment.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$handleDriveSelect$2, reason: invalid class name */
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj, Timber.Companion.class, "e", "e(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable th) {
            ((Timber.Companion) this.receiver).e(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleDriveSelect$lambda$26(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Deprecated(message = "Remove")
    private final DriveMode.DRIVE_MODE testRandomDriveMode(int id) {
        int i = id % 3;
        for (DriveMode.DRIVE_MODE drive_mode : DriveMode.DRIVE_MODE.values()) {
            if (drive_mode.ordinal() == i) {
                return drive_mode;
            }
        }
        return DriveMode.DRIVE_MODE.RESERVE;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(WriteInstalledPresetsSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        onLoadData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(CryptoInitErrorEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        getUsersManager().updateFirmwareProfile(new Function0<Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment.onMessageEvent.1
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
                Intent intent = new Intent(MainSoundsFragment.this.requireContext(), (Class<?>) UpdateAppActivity.class);
                intent.putExtra("isApp", false);
                intent.putExtra("isFirmwareOld", true);
                MainSoundsFragment.this.startActivity(intent);
                MainSoundsFragment.this.requireActivity().finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(SwitchedPresetEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        if (com.thor.basemodule.extensions.ContextKt.isCarUIMode(contextRequireContext)) {
            this.mLoadedData = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UploadSoundPackageSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        onLoadData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UpdateDamagePckEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Completable completableSubscribeOn = getDatabase().installedSoundPackageDao().updateDamagePckEndReturnId(event.getDamagePck()).subscribeOn(Schedulers.io());
        Action action = new Action() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Action
            public final void run() {
                MainSoundsFragment.onMessageEvent$lambda$27();
            }
        };
        final C02653 c02653 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment.onMessageEvent.3
            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }
        };
        completableSubscribeOn.subscribe(action, new Consumer() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundsFragment.onMessageEvent$lambda$28(c02653, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$28(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(DeleteEnabledMainPresetEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        getMAdapter().setDeleteEnabledPreset(event.getIndex());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BluetoothDeviceConnectionChangedEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                MainSoundsFragment.onMessageEvent$lambda$30(this.f$0);
            }
        }, 5000L);
        getViewModel().getIsBleConnected().set(event.isConnected());
        if (event.isConnected()) {
            return;
        }
        stopSendDiagnosticUniversalStatisticJob();
        this.mLoadedData = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$30(MainSoundsFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onMessageEvent(new CheckPlaceholderHintEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(FormatFlashExecuteEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        getMAdapter().clearAndAddAll(CollectionsKt.emptyList());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(OpenSettingsEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        MainPresetPackage presetPackage = event.getPresetPackage();
        if (presetPackage != null) {
            Intent intent = new Intent(getContext(), (Class<?>) PresetSoundSettingsActivity.class);
            intent.putExtra("preset_package", presetPackage);
            startActivityForResult(intent, 1001);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(ActiveAdapterAfterSgu event) {
        Intrinsics.checkNotNullParameter(event, "event");
        getMAdapter().setActivatedPreset(getBleManager().getServiceActivePresetIndex());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(CheckPlaceholderHintEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (isAdded()) {
            if (!getBleManager().isBleEnabledAndDeviceConnected() && getMAdapter().isEmpty()) {
                getBinding().textViewNoDevices.setText(getString(R.string.text_no_found_devices));
                getBinding().textViewNoDevices.setVisibility(0);
            } else if (getMAdapter().isEmpty()) {
                getBinding().textViewNoDevices.setText(getString(R.string.text_no_sound_presets));
                getBinding().textViewNoDevices.setVisibility(0);
            } else {
                getBinding().textViewNoDevices.setVisibility(8);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(StartDownloadDamagePck event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        DownloadSoundPackageDialogFragment.INSTANCE.newInstance(event.getSoundPacket()).show(getParentFragmentManager(), "DownloadSoundPackageDialogFragment");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(ApplyUpdateFirmwareSuccessfulEventOld event) {
        Intrinsics.checkNotNullParameter(event, "event");
        BleManager.executeInitCrypto$default(getBleManager(), null, 1, null);
        Intent intent = new Intent(getContext(), (Class<?>) SplashActivity.class);
        intent.addFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        startActivity(intent);
        requireActivity().finishAffinity();
    }

    @Subscribe
    public final void onMessageEvent(DeleteSoundDone event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        delRefresh = true;
        getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.fragments.presets.main.MainSoundsFragment$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                MainSoundsFragment.onMessageEvent$lambda$32(this.f$0);
            }
        }, 1000L);
        Log.i("LOAD_T", "refresh " + delRefresh);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$32(MainSoundsFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onLoadData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(FetchConfigEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        fetchDeviceSettings();
    }

    /* compiled from: MainSoundsFragment.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u0010"}, d2 = {"Lcom/thor/app/gui/fragments/presets/main/MainSoundsFragment$Companion;", "", "()V", "BUNDLE_ACTION", "", "REQUEST_SETTINGS", "", "delRefresh", "", "getDelRefresh", "()Z", "setDelRefresh", "(Z)V", "newInstance", "Lcom/thor/app/gui/fragments/presets/main/MainSoundsFragment;", "action", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean getDelRefresh() {
            return MainSoundsFragment.delRefresh;
        }

        public final void setDelRefresh(boolean z) {
            MainSoundsFragment.delRefresh = z;
        }

        public static /* synthetic */ MainSoundsFragment newInstance$default(Companion companion, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = null;
            }
            return companion.newInstance(str);
        }

        @JvmStatic
        public final MainSoundsFragment newInstance(String action) {
            MainSoundsFragment mainSoundsFragment = new MainSoundsFragment();
            if (action != null) {
                Bundle bundle = new Bundle();
                bundle.putString("action", action);
                mainSoundsFragment.setArguments(bundle);
            }
            return mainSoundsFragment;
        }
    }
}
