package com.thor.app.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.text.TextUtils;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.lifecycle.MutableLiveData;
import com.carsystems.thor.app.R;
import com.carsystems.thor.datalayermodule.datalayer.SettingsFromService;
import com.carsystems.thor.datalayermodule.datalayer.WearableDataLayerSender;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.SettingsWearableDataLayer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.gms.common.util.Hex;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.firebase.messaging.Constants;
import com.thor.app.databinding.model.RunningAppOnPhoneStatus;
import com.thor.app.databinding.viewmodels.workers.BleCommandsWorker;
import com.thor.app.gui.activities.main.MainActivity;
import com.thor.app.gui.adapters.main.MainSoundPackagesDiffUtilCallback;
import com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.entity.ShopSoundPackageEntity;
import com.thor.app.services.ListenerServiceFromWear;
import com.thor.app.settings.Settings;
import com.thor.app.utils.converters.JsonConverterKt;
import com.thor.app.utils.converters.mappers.MappersKt;
import com.thor.app.utils.data.DataLayerHelper;
import com.thor.businessmodule.bluetooth.model.other.InstalledPreset;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresets;
import com.thor.businessmodule.bluetooth.response.other.InstalledPresetsResponse;
import com.thor.businessmodule.bluetooth.response.sgu.PlaySguSoundBleResponse;
import com.thor.businessmodule.model.MainPresetPackage;
import com.thor.networkmodule.model.SignUpInfo;
import com.thor.networkmodule.model.bluetooth.BluetoothDeviceItem;
import com.thor.networkmodule.model.responses.SignInResponse;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import com.thor.networkmodule.model.responses.sgu.SguSoundConfig;
import com.thor.networkmodule.model.responses.sgu.SguSoundFile;
import com.thor.networkmodule.model.responses.sgu.SguSoundJson;
import com.thor.networkmodule.model.responses.sgu.SguSoundJsonWrapper;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import com.thor.networkmodule.model.responses.sgu.SguSoundSetsResponse;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.text.StringsKt;
import timber.log.Timber;

/* compiled from: ListenerServiceFromWear.kt */
@Metadata(d1 = {"\u0000²\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 Z2\u00020\u0001:\u0001ZB\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0002J\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 H\u0003J\u0010\u0010#\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020%H\u0003J\u0010\u0010&\u001a\u00020\u001e2\u0006\u0010'\u001a\u00020(H\u0002J\u0010\u0010)\u001a\u00020\u001e2\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020\u001e2\u0006\u0010-\u001a\u00020.H\u0002J\b\u0010/\u001a\u00020\u001eH\u0002J\u0016\u00100\u001a\u00020\u001e2\f\u00101\u001a\b\u0012\u0004\u0012\u00020302H\u0002J\u0010\u00104\u001a\u00020\u001e2\u0006\u00105\u001a\u00020\u000fH\u0002J\b\u00106\u001a\u00020\u001eH\u0016J\u0012\u00107\u001a\u00020\u001e2\b\u00108\u001a\u0004\u0018\u000109H\u0016J\b\u0010:\u001a\u00020\u001eH\u0016J\b\u0010;\u001a\u00020\u001eH\u0002J\b\u0010<\u001a\u00020\u001eH\u0002J\b\u0010=\u001a\u00020\u001eH\u0002J\b\u0010>\u001a\u00020\u001eH\u0002J\u0012\u0010?\u001a\u00020\u001e2\b\u0010@\u001a\u0004\u0018\u00010AH\u0016J\"\u0010B\u001a\u00020+2\b\u0010C\u001a\u0004\u0018\u00010D2\u0006\u0010E\u001a\u00020+2\u0006\u0010F\u001a\u00020+H\u0016J\u0016\u0010G\u001a\u00020\u001e2\u0006\u0010H\u001a\u00020\u00132\u0006\u0010I\u001a\u00020JJ\b\u0010K\u001a\u00020\u001eH\u0002J\u0010\u0010L\u001a\u00020\u001e2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010M\u001a\u00020\u001e2\u0006\u0010N\u001a\u00020OH\u0002J\u0010\u0010P\u001a\u00020\u001e2\u0006\u0010Q\u001a\u00020 H\u0002J\u0016\u0010R\u001a\u00020\u001e2\f\u0010S\u001a\b\u0012\u0004\u0012\u00020U0TH\u0002J\b\u0010V\u001a\u00020\u001eH\u0002J\b\u0010W\u001a\u00020\u001eH\u0002J\u0016\u0010X\u001a\u00020\u001e2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u001302H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0016\u001a\u00020\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\b\u001a\u0004\b\u0018\u0010\u0019¨\u0006["}, d2 = {"Lcom/thor/app/services/ListenerServiceFromWear;", "Lcom/google/android/gms/wearable/WearableListenerService;", "()V", "mAdapter", "Lcom/thor/app/gui/adapters/main/MainSoundPackagesRvAdapter;", "getMAdapter", "()Lcom/thor/app/gui/adapters/main/MainSoundPackagesRvAdapter;", "mAdapter$delegate", "Lkotlin/Lazy;", "mBleManager", "Lcom/thor/app/managers/BleManager;", "getMBleManager", "()Lcom/thor/app/managers/BleManager;", "mBleManager$delegate", "mBluetoothDeviceItem", "Lcom/thor/networkmodule/model/bluetooth/BluetoothDeviceItem;", "mutableSguList", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "timeoutHandler", "Landroid/os/Handler;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "buildNotification", "Landroid/app/Notification;", "checkWearableNodes", "", "createNotificationChannel", "", "channelId", "channelName", "doHandlePresets", "installedPresets", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "handleError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleMainStatesOnWatches", BleCommandsWorker.INPUT_DATA_COMMAND, "", "handleResponse", "response", "Lcom/thor/networkmodule/model/responses/SignInResponse;", "launchApplication", "loadSguSounds", "soundFilesOnDeviceIds", "", "", "onAuthorize", "device", "onCreate", "onDataChanged", "dataEvents", "Lcom/google/android/gms/wearable/DataEventBuffer;", "onDestroy", "onLaunchedState", "onLoadPresets", "onLoadSguSounds", "onMainSoundsState", "onMessageReceived", "messageEvent", "Lcom/google/android/gms/wearable/MessageEvent;", "onStartCommand", "intent", "Landroid/content/Intent;", "flags", "startId", "playSguSound", "sound", "config", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundConfig;", "runLoadingScreenOnWatches", "saveInSettings", "saveInSettingsFromApp", "settings", "Lcom/carsystems/thor/datalayermodule/datalayer/SettingsFromService;", "saveSguJsonToSettings", "json", "showDiscoveredNodes", "nodes", "", "Lcom/google/android/gms/wearable/Node;", "updateInfo", "updateLocale", "updateSguSounds", "list", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ListenerServiceFromWear extends WearableListenerService {
    public static final int FOREGROUND_SERVICE_ID = 11001;
    public static final String START_ACTIVITY_ON_PHONE_PATH = "/start-activity-on-phone";
    private static boolean isRunningAppOnWatches;
    private static boolean mLoadedData;
    private static boolean mLoadingData;
    private static int runningAppOnWatchesState;
    private BluetoothDeviceItem mBluetoothDeviceItem;
    private Handler timeoutHandler;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static RunningAppOnPhoneStatus runningAppOnPhoneState = RunningAppOnPhoneStatus.OFF;

    /* renamed from: mBleManager$delegate, reason: from kotlin metadata */
    private final Lazy mBleManager = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager>() { // from class: com.thor.app.services.ListenerServiceFromWear$mBleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0);
        }
    });

    /* renamed from: mAdapter$delegate, reason: from kotlin metadata */
    private final Lazy mAdapter = LazyKt.lazy(new Function0<MainSoundPackagesRvAdapter>() { // from class: com.thor.app.services.ListenerServiceFromWear$mAdapter$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final MainSoundPackagesRvAdapter invoke() {
            return new MainSoundPackagesRvAdapter(new MainSoundPackagesDiffUtilCallback(), this.this$0.getMBleManager(), this.this$0.getUsersManager());
        }
    });

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.services.ListenerServiceFromWear$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });
    private MutableLiveData<Collection<SguSound>> mutableSguList = new MutableLiveData<>();

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 2;
    }

    protected final BleManager getMBleManager() {
        return (BleManager) this.mBleManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MainSoundPackagesRvAdapter getMAdapter() {
        return (MainSoundPackagesRvAdapter) this.mAdapter.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    @Override // com.google.android.gms.wearable.WearableListenerService, android.app.Service
    public void onCreate() {
        super.onCreate();
        this.timeoutHandler = new Handler(getMainLooper());
        startForeground(FOREGROUND_SERVICE_ID, buildNotification());
        checkWearableNodes();
        getMAdapter().enableSyncData(this);
    }

    private final Notification buildNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, createNotificationChannel("ble_connection_service", "Service for connection with schema"));
        builder.setContentTitle("Bluetooth connection").setContentText("Bluetooth connection with Wear").setSmallIcon(R.drawable.ic_notification).setAutoCancel(true).setPriority(-2).setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL));
        return builder.build();
    }

    private final String createNotificationChannel(String channelId, String channelName) {
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, 0);
        notificationChannel.setLockscreenVisibility(0);
        Object systemService = getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        ((NotificationManager) systemService).createNotificationChannel(notificationChannel);
        return channelId;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:85:0x037a  */
    @Override // com.google.android.gms.wearable.WearableListenerService, com.google.android.gms.wearable.DataApi.DataListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onDataChanged(com.google.android.gms.wearable.DataEventBuffer r12) {
        /*
            Method dump skipped, instructions count: 962
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.services.ListenerServiceFromWear.onDataChanged(com.google.android.gms.wearable.DataEventBuffer):void");
    }

    @Override // com.google.android.gms.wearable.WearableListenerService, com.google.android.gms.wearable.MessageApi.MessageListener
    public void onMessageReceived(MessageEvent messageEvent) {
        Timber.INSTANCE.d("onMessageReceived: " + messageEvent, new Object[0]);
        StringsKt.equals$default(messageEvent != null ? messageEvent.getPath() : null, START_ACTIVITY_ON_PHONE_PATH, false, 2, null);
    }

    private final void handleMainStatesOnWatches(int command) {
        Timber.INSTANCE.i("runningAppOnWatchesState = " + runningAppOnWatchesState + ", command = " + command, new Object[0]);
        if (runningAppOnPhoneState == RunningAppOnPhoneStatus.LOADING_PACKAGE) {
            runLoadingScreenOnWatches();
            return;
        }
        if (runningAppOnWatchesState != command) {
            runningAppOnWatchesState = command;
            if (command == 1) {
                onLaunchedState();
            } else if (command == 2) {
                onMainSoundsState();
            } else {
                if (command != 4) {
                    return;
                }
                onLoadSguSounds();
            }
        }
    }

    private final void runLoadingScreenOnWatches() {
        getMBleManager().disconnect(false);
        DataLayerHelper.newInstance(this).onStartUploadingWearableActivity();
    }

    private final void onLaunchedState() {
        Timber.INSTANCE.i("STATE_LAUNCHED", new Object[0]);
        if (Settings.INSTANCE.isAccessSession()) {
            DataLayerHelper.newInstance(this).onStartMainWearableActivity();
        }
    }

    private final void onMainSoundsState() {
        Timber.INSTANCE.i("STATE_MAIN_SOUNDS", new Object[0]);
        Timber.INSTANCE.i("runningAppOnPhoneState = " + runningAppOnPhoneState, new Object[0]);
        if (RunningAppOnPhoneStatus.INSTANCE.isBlockUploadingPresetsFromService(runningAppOnPhoneState)) {
            return;
        }
        if (getMAdapter().isEmpty()) {
            onLoadPresets();
            return;
        }
        DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        DataLayerManager dataLayerManagerFrom = companion.from(applicationContext);
        Collection<MainPresetPackage> all = getMAdapter().getAll();
        Intrinsics.checkNotNullExpressionValue(all, "mAdapter.all");
        dataLayerManagerFrom.sendMainPresetPackages(all);
    }

    private final void launchApplication() {
        Intent intent = new Intent(this, (Class<?>) MainActivity.class);
        intent.setFlags(268468224);
        getApplication().startActivity(intent);
    }

    private final void onAuthorize(BluetoothDeviceItem device) {
        String strSubstring;
        String name;
        this.mBluetoothDeviceItem = device;
        if (device == null || (name = device.getName()) == null) {
            strSubstring = null;
        } else {
            strSubstring = name.substring(4);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        }
        Observable observableSignIn$default = UsersManager.signIn$default(getUsersManager(), strSubstring, false, 2, null);
        if (observableSignIn$default != null) {
            final C04301 c04301 = new C04301(this);
            Consumer consumer = new Consumer() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ListenerServiceFromWear.onAuthorize$lambda$3(c04301, obj);
                }
            };
            final C04312 c04312 = new C04312(this);
            observableSignIn$default.subscribe(consumer, new Consumer() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ListenerServiceFromWear.onAuthorize$lambda$4(c04312, obj);
                }
            });
        }
    }

    /* compiled from: ListenerServiceFromWear.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.services.ListenerServiceFromWear$onAuthorize$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C04301 extends FunctionReferenceImpl implements Function1<SignInResponse, Unit> {
        C04301(Object obj) {
            super(1, obj, ListenerServiceFromWear.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/SignInResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(SignInResponse signInResponse) {
            invoke2(signInResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(SignInResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ListenerServiceFromWear) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: ListenerServiceFromWear.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.services.ListenerServiceFromWear$onAuthorize$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C04312 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C04312(Object obj) {
            super(1, obj, ListenerServiceFromWear.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((ListenerServiceFromWear) this.receiver).handleError(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAuthorize$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAuthorize$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(SignInResponse response) {
        String strSubstring;
        String name;
        Timber.INSTANCE.i("handleResponse: %s", response);
        if (response.isSuccessful()) {
            if (response.getUserId() == 0 || TextUtils.isEmpty(response.getToken())) {
                return;
            }
            saveInSettings(response);
            Timber.INSTANCE.i("Sign In Success", new Object[0]);
            DataLayerHelper.newInstance(this).onStartMainWearableActivity();
            updateInfo();
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, getApplicationContext(), response.getError(), null, 4, null);
        if (alertDialogCreateErrorAlertDialog$default != null) {
            alertDialogCreateErrorAlertDialog$default.show();
        }
        SignUpInfo signUpInfo = new SignUpInfo(null, 0, null, 0, null, 0, null, 0, null, null, null, 2047, null);
        BluetoothDeviceItem bluetoothDeviceItem = this.mBluetoothDeviceItem;
        if (bluetoothDeviceItem == null || (name = bluetoothDeviceItem.getName()) == null) {
            strSubstring = null;
        } else {
            strSubstring = name.substring(4);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        }
        signUpInfo.setDeviceSN(strSubstring);
        Settings settings = Settings.INSTANCE;
        BluetoothDeviceItem bluetoothDeviceItem2 = this.mBluetoothDeviceItem;
        settings.saveDeviceMacAddress(bluetoothDeviceItem2 != null ? bluetoothDeviceItem2.getMacAddress() : null);
        Timber.INSTANCE.i("Sign Up Success", new Object[0]);
        DataLayerHelper.newInstance(this).onStartSignUpWearableActivity();
    }

    private final void saveInSettings(final SignInResponse response) {
        WearableDataLayerSender wearableDataLayerSenderFrom = WearableDataLayerSender.from(this);
        SettingsWearableDataLayer settingsWearableDataLayer = SettingsWearableDataLayer.INSTANCE;
        BluetoothDeviceItem bluetoothDeviceItem = this.mBluetoothDeviceItem;
        String macAddress = bluetoothDeviceItem != null ? bluetoothDeviceItem.getMacAddress() : null;
        Intrinsics.checkNotNull(macAddress);
        wearableDataLayerSenderFrom.sendRxDataItem(SettingsWearableDataLayer.SETTINGS_PATH, settingsWearableDataLayer, new SettingsFromService(response, macAddress)).doOnSuccess(new Consumer() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ListenerServiceFromWear.saveInSettings$lambda$5(response, this, obj);
            }
        }).subscribe();
        Settings.saveUserId(response.getUserId());
        String token = response.getToken();
        Intrinsics.checkNotNull(token);
        Settings.saveAccessToken(token);
        Settings.saveProfile(response);
        Settings settings = Settings.INSTANCE;
        BluetoothDeviceItem bluetoothDeviceItem2 = this.mBluetoothDeviceItem;
        settings.saveDeviceMacAddress(bluetoothDeviceItem2 != null ? bluetoothDeviceItem2.getMacAddress() : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveInSettings$lambda$5(SignInResponse response, ListenerServiceFromWear this$0, Object obj) {
        Intrinsics.checkNotNullParameter(response, "$response");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Timber.Companion companion = Timber.INSTANCE;
        BluetoothDeviceItem bluetoothDeviceItem = this$0.mBluetoothDeviceItem;
        String macAddress = bluetoothDeviceItem != null ? bluetoothDeviceItem.getMacAddress() : null;
        Intrinsics.checkNotNull(macAddress);
        companion.i("Settings send from phone to wear: " + response + ", " + macAddress, new Object[0]);
    }

    private final void saveInSettingsFromApp(SettingsFromService settings) {
        Settings.saveUserId(settings.getResponse().getUserId());
        String token = settings.getResponse().getToken();
        Intrinsics.checkNotNull(token);
        Settings.saveAccessToken(token);
        Settings.saveProfile(settings.getResponse());
        Settings.INSTANCE.saveDeviceMacAddress(settings.getBluetoothDeviceMacAddress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onLoadPresets() {
        Timber.INSTANCE.i("onLoadPresets", new Object[0]);
        if (mLoadedData) {
            if (getMAdapter().isEmpty()) {
                mLoadedData = false;
            }
            onLoadPresets();
            return;
        }
        Timber.INSTANCE.i("Adapter size = " + getMAdapter().getItemCount() + ", Adapter is empty = " + getMAdapter().isEmpty(), new Object[0]);
        if (!getMAdapter().isEmpty()) {
            DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
            Context applicationContext = getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
            DataLayerManager dataLayerManagerFrom = companion.from(applicationContext);
            Collection<MainPresetPackage> all = getMAdapter().getAll();
            Intrinsics.checkNotNullExpressionValue(all, "mAdapter.all");
            dataLayerManagerFrom.sendMainPresetPackages(all);
            return;
        }
        boolean zIsBleEnabledAndDeviceConnected = getMBleManager().isBleEnabledAndDeviceConnected();
        Timber.INSTANCE.i("isConnected = " + zIsBleEnabledAndDeviceConnected + ", isLoading = " + mLoadingData, new Object[0]);
        if (zIsBleEnabledAndDeviceConnected) {
            getMBleManager().stopScan();
            if (mLoadingData) {
                return;
            }
            mLoadingData = true;
            Observable<ByteArrayOutputStream> observableObserveOn = getMBleManager().executeReadInstalledPresetsCommand().observeOn(AndroidSchedulers.mainThread());
            final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.services.ListenerServiceFromWear$onLoadPresets$commandDisposable$1
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
                    byte[] byteArray = byteArrayOutputStream != null ? byteArrayOutputStream.toByteArray() : null;
                    Timber.Companion companion2 = Timber.INSTANCE;
                    Object[] objArr = new Object[1];
                    objArr[0] = Hex.bytesToStringUppercase(byteArray == null ? new byte[0] : byteArray);
                    companion2.i("Take data: %s", objArr);
                    InstalledPresetsResponse installedPresetsResponse = new InstalledPresetsResponse(byteArray);
                    Timber.INSTANCE.i("Response status: " + installedPresetsResponse.getStatus(), new Object[0]);
                    if (installedPresetsResponse.getStatus()) {
                        Timber.INSTANCE.i("Response is correct: %s", installedPresetsResponse.getInstalledPresets());
                        this.this$0.doHandlePresets(installedPresetsResponse.getInstalledPresets());
                        ListenerServiceFromWear.Companion companion3 = ListenerServiceFromWear.INSTANCE;
                        ListenerServiceFromWear.mLoadedData = true;
                        return;
                    }
                    Timber.INSTANCE.e("Response is not correct: %s", installedPresetsResponse.getErrorCode());
                    ListenerServiceFromWear.Companion companion4 = ListenerServiceFromWear.INSTANCE;
                    ListenerServiceFromWear.mLoadingData = false;
                    this.this$0.getMBleManager().clear();
                    Timer timer = new Timer();
                    final ListenerServiceFromWear listenerServiceFromWear = this.this$0;
                    timer.schedule(new TimerTask() { // from class: com.thor.app.services.ListenerServiceFromWear$onLoadPresets$commandDisposable$1.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            listenerServiceFromWear.onLoadPresets();
                        }
                    }, 1000L);
                }
            };
            Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda7
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ListenerServiceFromWear.onLoadPresets$lambda$6(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.services.ListenerServiceFromWear$onLoadPresets$commandDisposable$2
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
                    ListenerServiceFromWear.Companion companion2 = ListenerServiceFromWear.INSTANCE;
                    ListenerServiceFromWear.mLoadingData = false;
                    this.this$0.getMBleManager().clear();
                    Timer timer = new Timer();
                    final ListenerServiceFromWear listenerServiceFromWear = this.this$0;
                    timer.schedule(new TimerTask() { // from class: com.thor.app.services.ListenerServiceFromWear$onLoadPresets$commandDisposable$2.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            listenerServiceFromWear.onLoadPresets();
                        }
                    }, 1000L);
                }
            };
            Disposable commandDisposable = observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ListenerServiceFromWear.onLoadPresets$lambda$7(function12, obj);
                }
            });
            BleManager mBleManager = getMBleManager();
            Intrinsics.checkNotNullExpressionValue(commandDisposable, "commandDisposable");
            mBleManager.addCommandDisposable(commandDisposable);
            return;
        }
        getMBleManager().disconnect(false);
        getMBleManager().startScan();
        new Timer().schedule(new TimerTask() { // from class: com.thor.app.services.ListenerServiceFromWear.onLoadPresets.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                ListenerServiceFromWear.this.onLoadPresets();
            }
        }, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadPresets$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadPresets$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doHandlePresets(final InstalledPresets installedPresets) {
        Timber.INSTANCE.i("doHandlePresets", new Object[0]);
        Flowable<List<ShopSoundPackageEntity>> flowableObserveOn = ThorDatabase.INSTANCE.from(this).shopSoundPackageDao().getEntitiesFlow().observeOn(AndroidSchedulers.mainThread());
        if (flowableObserveOn != null) {
            final Function1<List<? extends ShopSoundPackageEntity>, Unit> function1 = new Function1<List<? extends ShopSoundPackageEntity>, Unit>() { // from class: com.thor.app.services.ListenerServiceFromWear.doHandlePresets.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(List<? extends ShopSoundPackageEntity> list) {
                    invoke2((List<ShopSoundPackageEntity>) list);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(List<ShopSoundPackageEntity> list) {
                    ListenerServiceFromWear.this.getMAdapter().clear();
                }
            };
            Flowable<List<ShopSoundPackageEntity>> flowableDoOnNext = flowableObserveOn.doOnNext(new Consumer() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda12
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ListenerServiceFromWear.doHandlePresets$lambda$8(function1, obj);
                }
            });
            if (flowableDoOnNext != null) {
                final Function1<List<? extends ShopSoundPackageEntity>, Unit> function12 = new Function1<List<? extends ShopSoundPackageEntity>, Unit>() { // from class: com.thor.app.services.ListenerServiceFromWear.doHandlePresets.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(List<? extends ShopSoundPackageEntity> list) {
                        invoke2((List<ShopSoundPackageEntity>) list);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(List<ShopSoundPackageEntity> soundPackages) {
                        ArrayList<MainPresetPackage> arrayList = new ArrayList<>();
                        for (InstalledPreset installedPreset : installedPresets.getPresets()) {
                            Intrinsics.checkNotNullExpressionValue(soundPackages, "soundPackages");
                            for (ShopSoundPackageEntity shopSoundPackageEntity : soundPackages) {
                                if (shopSoundPackageEntity.getId() == installedPreset.getPackageId()) {
                                    MainPresetPackage mainPresetPackage = new MainPresetPackage(null, null, null, null, null, false, null, null, null, false, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null);
                                    mainPresetPackage.setPackageId(Integer.valueOf(installedPreset.getPackageId()));
                                    mainPresetPackage.setName(shopSoundPackageEntity.getPkgName());
                                    mainPresetPackage.setImageUrl(shopSoundPackageEntity.getPkgImageUrl());
                                    mainPresetPackage.setModeType(Short.valueOf(installedPreset.getMode()));
                                    arrayList.add(mainPresetPackage);
                                }
                            }
                        }
                        this.getMAdapter().saveListOfPackagesForFutureUse(arrayList);
                        this.getMAdapter().clearAndAddAll(arrayList);
                        this.getMAdapter().setActivatedPreset(installedPresets.getCurrentPresetId());
                        InstalledPreset activatedPreset = this.getMAdapter().getActivatedPreset();
                        this.getMBleManager().setActivatedPresetIndex(installedPresets.getCurrentPresetId());
                        this.getMBleManager().setActivatedPreset(activatedPreset);
                        DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
                        Context applicationContext = this.getApplicationContext();
                        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
                        DataLayerManager dataLayerManagerFrom = companion.from(applicationContext);
                        Collection<MainPresetPackage> all = this.getMAdapter().getAll();
                        Intrinsics.checkNotNullExpressionValue(all, "mAdapter.all");
                        dataLayerManagerFrom.sendMainPresetPackages(all);
                    }
                };
                Consumer<? super List<ShopSoundPackageEntity>> consumer = new Consumer() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda13
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ListenerServiceFromWear.doHandlePresets$lambda$9(function12, obj);
                    }
                };
                final AnonymousClass3 anonymousClass3 = new Function1<Throwable, Unit>() { // from class: com.thor.app.services.ListenerServiceFromWear.doHandlePresets.3
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
                flowableDoOnNext.subscribe(consumer, new Consumer() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda14
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ListenerServiceFromWear.doHandlePresets$lambda$10(anonymousClass3, obj);
                    }
                });
            }
        }
        mLoadingData = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doHandlePresets$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doHandlePresets$lambda$9(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doHandlePresets$lambda$10(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void updateInfo() {
        updateLocale();
        if (Settings.INSTANCE.isAccessSession()) {
            UsersManager.updateFirmwareProfile$default(getUsersManager(), null, 1, null);
            getUsersManager().fetchSoundPackages();
            getMBleManager().startScan();
            onLoadPresets();
        }
    }

    private final void updateLocale() {
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
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onLoadSguSounds() {
        DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        DataLayerManager dataLayerManagerFrom = companion.from(applicationContext);
        List value = this.mutableSguList.getValue();
        if (value == null) {
            value = CollectionsKt.emptyList();
        }
        dataLayerManagerFrom.sendSguPresetPackages(value);
        boolean zIsBleEnabledAndDeviceConnected = getMBleManager().isBleEnabledAndDeviceConnected();
        Timber.INSTANCE.i("isConnected = " + zIsBleEnabledAndDeviceConnected + ", isLoading = " + mLoadingData, new Object[0]);
        if (zIsBleEnabledAndDeviceConnected) {
            return;
        }
        getMBleManager().disconnect(false);
        getMBleManager().startScan();
        new Timer().schedule(new TimerTask() { // from class: com.thor.app.services.ListenerServiceFromWear.onLoadSguSounds.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                ListenerServiceFromWear.this.onLoadSguSounds();
            }
        }, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS);
    }

    private final void updateSguSounds(List<SguSound> list) {
        System.out.println((Object) ("list to update SguSounds: " + list));
        this.mutableSguList.postValue(list);
        DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        companion.from(applicationContext).sendSguPresetPackages(list);
    }

    private final void loadSguSounds(final List<Short> soundFilesOnDeviceIds) {
        Observable<SguSoundSetsResponse> observableFetchSoundSguPackages;
        if (soundFilesOnDeviceIds.isEmpty() || (observableFetchSoundSguPackages = getUsersManager().fetchSoundSguPackages()) == null) {
            return;
        }
        final Function1<SguSoundSetsResponse, Unit> function1 = new Function1<SguSoundSetsResponse, Unit>() { // from class: com.thor.app.services.ListenerServiceFromWear.loadSguSounds.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(SguSoundSetsResponse sguSoundSetsResponse) {
                invoke2(sguSoundSetsResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(SguSoundSetsResponse sguSoundSetsResponse) {
                Object next;
                SguSound sguSoundModel;
                Timber.INSTANCE.d(sguSoundSetsResponse.toString(), new Object[0]);
                if (sguSoundSetsResponse.isSuccessful()) {
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    ArrayList arrayList = new ArrayList();
                    SguSoundJsonWrapper sguSoundList = JsonConverterKt.toSguSoundList(Settings.INSTANCE.getSguSoundFavoritesJSON());
                    if (sguSoundList == null) {
                        sguSoundList = new SguSoundJsonWrapper(new ArrayList());
                    }
                    List<SguSoundSet> soundSetsItems = sguSoundSetsResponse.getSoundSetsItems();
                    if (soundSetsItems != null) {
                        Iterator<T> it = soundSetsItems.iterator();
                        while (it.hasNext()) {
                            Iterator<T> it2 = ((SguSoundSet) it.next()).getFiles().iterator();
                            while (it2.hasNext()) {
                                arrayList.add((SguSound) it2.next());
                            }
                        }
                    }
                    List<SguSoundSet> soundSetsItems2 = sguSoundSetsResponse.getSoundSetsItems();
                    if (soundSetsItems2 != null) {
                        List<Short> list = soundFilesOnDeviceIds;
                        Iterator<T> it3 = soundSetsItems2.iterator();
                        while (it3.hasNext()) {
                            for (SguSound sguSound : ((SguSoundSet) it3.next()).getFiles()) {
                                List<SguSoundFile> soundFiles = sguSound.getSoundFiles();
                                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(soundFiles, 10));
                                Iterator<T> it4 = soundFiles.iterator();
                                while (it4.hasNext()) {
                                    arrayList2.add(Short.valueOf((short) ((SguSoundFile) it4.next()).getId()));
                                }
                                if (list.containsAll(arrayList2) && (!r8.isEmpty())) {
                                    linkedHashSet.add(sguSound);
                                }
                            }
                        }
                    }
                    LinkedHashSet linkedHashSet2 = new LinkedHashSet();
                    List<SguSoundJson> presets = sguSoundList.getPresets();
                    if (presets != null) {
                        for (SguSoundJson sguSoundJson : presets) {
                            Iterator it5 = linkedHashSet.iterator();
                            while (true) {
                                if (it5.hasNext()) {
                                    next = it5.next();
                                    if (((SguSound) next).getId() == sguSoundJson.getId()) {
                                        break;
                                    }
                                } else {
                                    next = null;
                                    break;
                                }
                            }
                            if (next != null && (sguSoundModel = MappersKt.toSguSoundModel(sguSoundJson)) != null) {
                                linkedHashSet2.add(sguSoundModel);
                            }
                        }
                    }
                    ListenerServiceFromWear.this.mutableSguList.postValue(linkedHashSet2);
                    DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
                    Context applicationContext = ListenerServiceFromWear.this.getApplicationContext();
                    Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
                    companion.from(applicationContext).sendSguPresetPackages(linkedHashSet2);
                    return;
                }
                AlertDialog alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, ListenerServiceFromWear.this.getApplicationContext(), sguSoundSetsResponse.getError(), null, 4, null);
                if (alertDialogCreateErrorAlertDialog$default != null) {
                    alertDialogCreateErrorAlertDialog$default.show();
                }
            }
        };
        Consumer<? super SguSoundSetsResponse> consumer = new Consumer() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ListenerServiceFromWear.loadSguSounds$lambda$13(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.services.ListenerServiceFromWear.loadSguSounds.2
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
                ListenerServiceFromWear listenerServiceFromWear = ListenerServiceFromWear.this;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                listenerServiceFromWear.handleError(it);
            }
        };
        Disposable disposableSubscribe = observableFetchSoundSguPackages.subscribe(consumer, new Consumer() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ListenerServiceFromWear.loadSguSounds$lambda$14(function12, obj);
            }
        });
        if (disposableSubscribe != null) {
            getUsersManager().addDisposable(disposableSubscribe);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadSguSounds$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadSguSounds$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void playSguSound(final SguSound sound, final SguSoundConfig config) {
        Intrinsics.checkNotNullParameter(sound, "sound");
        Intrinsics.checkNotNullParameter(config, "config");
        if (getMBleManager().isBleEnabledAndDeviceConnected()) {
            Observable<PlaySguSoundBleResponse> observableExecutePlaySguSoundCommand = getMBleManager().executePlaySguSoundCommand((short) ((SguSoundFile) CollectionsKt.random(sound.getSoundFiles(), Random.INSTANCE)).getId(), config);
            final Function1<PlaySguSoundBleResponse, Unit> function1 = new Function1<PlaySguSoundBleResponse, Unit>() { // from class: com.thor.app.services.ListenerServiceFromWear.playSguSound.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(PlaySguSoundBleResponse playSguSoundBleResponse) {
                    invoke2(playSguSoundBleResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(PlaySguSoundBleResponse playSguSoundBleResponse) {
                    if (playSguSoundBleResponse.isSuccessful()) {
                        Timber.INSTANCE.d("playSguSound successful; sound: " + sound + "; config: " + config, new Object[0]);
                        return;
                    }
                    this.handleError(new Exception("Can't play SGU sound: " + sound));
                }
            };
            Consumer<? super PlaySguSoundBleResponse> consumer = new Consumer() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ListenerServiceFromWear.playSguSound$lambda$16(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.services.ListenerServiceFromWear.playSguSound.2
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
                    ListenerServiceFromWear listenerServiceFromWear = ListenerServiceFromWear.this;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    listenerServiceFromWear.handleError(it);
                }
            };
            getMBleManager().getMCompositeDisposable().add(observableExecutePlaySguSoundCommand.subscribe(consumer, new Consumer() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ListenerServiceFromWear.playSguSound$lambda$17(function12, obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void playSguSound$lambda$16(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void playSguSound$lambda$17(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void saveSguJsonToSettings(String json) {
        Timber.INSTANCE.i("New json file " + json, new Object[0]);
        Settings.INSTANCE.saveSguSoundFavoritesJSON(json);
    }

    private final void checkWearableNodes() {
        Handler handler = this.timeoutHandler;
        if (handler == null) {
            Intrinsics.throwUninitializedPropertyAccessException("timeoutHandler");
            handler = null;
        }
        handler.postDelayed(new Runnable() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ListenerServiceFromWear.checkWearableNodes$lambda$19(this.f$0);
            }
        }, 10000L);
        Task<List<Node>> connectedNodes = Wearable.getNodeClient(this).getConnectedNodes();
        final Function1<List<Node>, Unit> function1 = new Function1<List<Node>, Unit>() { // from class: com.thor.app.services.ListenerServiceFromWear$checkWearableNodes$2$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<Node> list) {
                invoke2(list);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<Node> it) {
                Intrinsics.checkNotNullExpressionValue(it, "it");
                this.this$0.showDiscoveredNodes(CollectionsKt.toSet(it));
                Handler handler2 = this.this$0.timeoutHandler;
                if (handler2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("timeoutHandler");
                    handler2 = null;
                }
                handler2.removeCallbacksAndMessages(null);
            }
        };
        connectedNodes.addOnSuccessListener(new OnSuccessListener() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda4
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                ListenerServiceFromWear.checkWearableNodes$lambda$22$lambda$20(function1, obj);
            }
        });
        connectedNodes.addOnFailureListener(new OnFailureListener() { // from class: com.thor.app.services.ListenerServiceFromWear$$ExternalSyntheticLambda5
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                ListenerServiceFromWear.checkWearableNodes$lambda$22$lambda$21(this.f$0, exc);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkWearableNodes$lambda$19(ListenerServiceFromWear this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            this$0.stopSelf();
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkWearableNodes$lambda$22$lambda$20(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkWearableNodes$lambda$22$lambda$21(ListenerServiceFromWear this$0, Exception it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        Timber.INSTANCE.w(it);
        this$0.stopSelf();
    }

    @Override // com.google.android.gms.wearable.WearableListenerService, android.app.Service
    public void onDestroy() {
        Handler handler = this.timeoutHandler;
        if (handler == null) {
            Intrinsics.throwUninitializedPropertyAccessException("timeoutHandler");
            handler = null;
        }
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showDiscoveredNodes(Set<? extends Node> nodes) {
        Set<? extends Node> set = nodes;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(((Node) it.next()).getDisplayName());
        }
        Set set2 = CollectionsKt.toSet(arrayList);
        if (set2.isEmpty()) {
            Timber.INSTANCE.d("Connected Nodes: No connected device was found for the given capabilities", new Object[0]);
            stopSelf();
        } else {
            Timber.INSTANCE.d("Connected Nodes: " + CollectionsKt.joinToString$default(set2, ", ", null, null, 0, null, null, 62, null), new Object[0]);
        }
    }

    /* compiled from: ListenerServiceFromWear.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/thor/app/services/ListenerServiceFromWear$Companion;", "", "()V", "FOREGROUND_SERVICE_ID", "", "START_ACTIVITY_ON_PHONE_PATH", "", "isRunningAppOnWatches", "", "mLoadedData", "mLoadingData", "runningAppOnPhoneState", "Lcom/thor/app/databinding/model/RunningAppOnPhoneStatus;", "runningAppOnWatchesState", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
