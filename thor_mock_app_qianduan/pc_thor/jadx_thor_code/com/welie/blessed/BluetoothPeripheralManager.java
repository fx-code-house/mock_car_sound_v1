package com.welie.blessed;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import com.thor.app.databinding.viewmodels.workers.BleCommandsWorker;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: BluetoothPeripheralManager.kt */
@Metadata(d1 = {"\u0000Æ\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u0000 v2\u00020\u0001:\u0001vB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010A\u001a\u00020%2\u0006\u0010B\u001a\u00020<J\u0018\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020\u001b2\u0006\u0010F\u001a\u00020(H\u0002J\u0018\u0010G\u001a\u00020D2\u0006\u0010E\u001a\u00020\u001b2\u0006\u0010F\u001a\u00020(H\u0002J\b\u0010H\u001a\u00020DH\u0002J\u0010\u0010I\u001a\u00020D2\u0006\u0010J\u001a\u00020/H\u0002J\u000e\u0010I\u001a\u00020D2\u0006\u0010K\u001a\u00020(J\u001a\u0010L\u001a\u0002042\b\u0010M\u001a\u0004\u0018\u0001042\u0006\u0010N\u001a\u00020OH\u0002J\u0006\u0010P\u001a\u00020DJ\b\u0010Q\u001a\u00020DH\u0002J \u0010R\u001a\u0002042\u0006\u0010S\u001a\u0002042\u0006\u0010N\u001a\u00020O2\u0006\u0010T\u001a\u00020OH\u0002J\u0010\u0010R\u001a\u0002042\b\u0010S\u001a\u0004\u0018\u000104J\u0010\u0010U\u001a\u00020%2\u0006\u0010E\u001a\u00020\u001bH\u0002J\u0010\u0010V\u001a\u00020%2\u0006\u0010W\u001a\u00020!H\u0002J\u0010\u0010X\u001a\u00020(2\u0006\u0010Y\u001a\u00020/H\u0002J\u0010\u0010X\u001a\u0004\u0018\u00010(2\u0006\u0010Z\u001a\u00020\u001dJ\u001c\u0010[\u001a\b\u0012\u0004\u0012\u00020(0'2\f\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u001d0'H\u0002J\u0014\u0010]\u001a\b\u0012\u0004\u0012\u00020(0'2\u0006\u0010E\u001a\u00020\u001bJ\u0014\u0010^\u001a\b\u0012\u0004\u0012\u00020(0'2\u0006\u0010E\u001a\u00020\u001bJ\u0010\u0010_\u001a\u00020D2\u0006\u0010`\u001a\u00020OH\u0002J(\u0010a\u001a\u00020%2\u0006\u0010Y\u001a\u00020/2\u0006\u0010E\u001a\u00020\u001b2\u0006\u0010M\u001a\u0002042\u0006\u0010b\u001a\u00020%H\u0002J\b\u0010c\u001a\u00020DH\u0002J\u0012\u0010d\u001a\u0002042\b\u0010S\u001a\u0004\u0018\u000104H\u0002J(\u0010e\u001a\u00020%2\u0006\u0010M\u001a\u0002042\u0006\u0010J\u001a\u00020/2\u0006\u0010E\u001a\u00020\u001b2\u0006\u0010b\u001a\u00020%H\u0002J\u001e\u0010e\u001a\u00020%2\u0006\u0010M\u001a\u0002042\u0006\u0010K\u001a\u00020(2\u0006\u0010E\u001a\u00020\u001bJ\b\u0010f\u001a\u00020DH\u0002J\u000e\u0010g\u001a\u00020%2\u0006\u0010B\u001a\u00020<J\u0006\u0010h\u001a\u00020DJ\u0010\u0010i\u001a\u00020D2\u0006\u0010Y\u001a\u00020/H\u0002J\u0010\u0010j\u001a\u00020D2\u0006\u0010F\u001a\u00020(H\u0002J\u0018\u0010k\u001a\u00020D2\u0006\u0010E\u001a\u00020\u001b2\u0006\u0010F\u001a\u00020(H\u0002J\u0018\u0010l\u001a\u00020D2\u0006\u0010E\u001a\u00020\u001b2\u0006\u0010F\u001a\u00020(H\u0002J\u001e\u0010m\u001a\u00020D2\u0006\u0010n\u001a\u00020o2\u0006\u0010p\u001a\u00020q2\u0006\u0010r\u001a\u00020qJ\u0006\u0010s\u001a\u00020DJ\u0010\u0010t\u001a\u00020%2\u0006\u0010E\u001a\u00020\u001bH\u0002J\u0010\u0010u\u001a\u00020%2\u0006\u0010E\u001a\u00020\u001bH\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u001b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\u001c0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00020\u001b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\u001c0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 ¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u000e\u0010$\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'8F¢\u0006\u0006\u001a\u0004\b)\u0010*R\u001a\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020(0,X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010-\u001a\b\u0012\u0004\u0012\u00020/0.8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u000204X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000204X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u00107\u001a\u00020%2\u0006\u00106\u001a\u00020%@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u000e\u00109\u001a\u00020:X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010;\u001a\b\u0012\u0004\u0012\u00020<0.8F¢\u0006\u0006\u001a\u0004\b=\u00101R\u001a\u0010>\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u0002040\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010?\u001a\u000e\u0012\u0004\u0012\u00020@\u0012\u0004\u0012\u0002040\u001aX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006w"}, d2 = {"Lcom/welie/blessed/BluetoothPeripheralManager;", "", "context", "Landroid/content/Context;", "bluetoothManager", "Landroid/bluetooth/BluetoothManager;", "callback", "Lcom/welie/blessed/BluetoothPeripheralManagerCallback;", "(Landroid/content/Context;Landroid/bluetooth/BluetoothManager;Lcom/welie/blessed/BluetoothPeripheralManagerCallback;)V", "adapterStateReceiver", "Landroid/content/BroadcastReceiver;", "advertiseCallback", "Landroid/bluetooth/le/AdvertiseCallback;", "getAdvertiseCallback$blessed_release", "()Landroid/bluetooth/le/AdvertiseCallback;", "bluetoothAdapter", "Landroid/bluetooth/BluetoothAdapter;", "bluetoothGattServer", "Landroid/bluetooth/BluetoothGattServer;", "bluetoothGattServerCallback", "Landroid/bluetooth/BluetoothGattServerCallback;", "getBluetoothGattServerCallback", "()Landroid/bluetooth/BluetoothGattServerCallback;", "bluetoothLeAdvertiser", "Landroid/bluetooth/le/BluetoothLeAdvertiser;", "centralsWantingIndications", "Ljava/util/HashMap;", "Landroid/bluetooth/BluetoothGattCharacteristic;", "", "", "centralsWantingNotifications", "commandQueue", "Ljava/util/Queue;", "Ljava/lang/Runnable;", "getCommandQueue", "()Ljava/util/Queue;", "commandQueueBusy", "", "connectedCentrals", "", "Lcom/welie/blessed/BluetoothCentral;", "getConnectedCentrals", "()Ljava/util/Set;", "connectedCentralsMap", "", "connectedDevices", "", "Landroid/bluetooth/BluetoothDevice;", "getConnectedDevices", "()Ljava/util/List;", "currentNotifyCharacteristic", "currentNotifyValue", "", "currentReadValue", "<set-?>", "isAdvertising", "()Z", "mainHandler", "Landroid/os/Handler;", "services", "Landroid/bluetooth/BluetoothGattService;", "getServices", "writeLongCharacteristicTemporaryBytes", "writeLongDescriptorTemporaryBytes", "Landroid/bluetooth/BluetoothGattDescriptor;", "add", NotificationCompat.CATEGORY_SERVICE, "addCentralWantingIndications", "", "characteristic", "central", "addCentralWantingNotifications", "cancelAllConnectionsWhenBluetoothOff", "cancelConnection", "bluetoothDevice", "bluetoothCentral", "chopValue", "value", "offset", "", "close", "completedCommand", "copyOf", "source", "maxSize", "doesNotSupportNotifying", "enqueue", BleCommandsWorker.INPUT_DATA_COMMAND, "getCentral", "device", "address", "getCentralsByAddress", "centralAddresses", "getCentralsWantingIndications", "getCentralsWantingNotifications", "handleAdapterState", "state", "internalNotifyCharacteristicChanged", "confirm", "nextCommand", "nonnullOf", "notifyCharacteristicChanged", "onAdvertisingStopped", "remove", "removeAllServices", "removeCentral", "removeCentralFromWantingAnything", "removeCentralWantingIndications", "removeCentralWantingNotifications", "startAdvertising", "settings", "Landroid/bluetooth/le/AdvertiseSettings;", "advertiseData", "Landroid/bluetooth/le/AdvertiseData;", "scanResponse", "stopAdvertising", "supportsIndicate", "supportsNotify", "Companion", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BluetoothPeripheralManager {
    private static final String ADDRESS_IS_NULL = "address is null";
    private static final UUID CCC_DESCRIPTOR_UUID;
    private static final String CENTRAL_IS_NULL = "central is null";
    private static final String CHARACTERISTIC_IS_NULL = "characteristic is null";
    private static final String CHARACTERISTIC_VALUE_IS_NULL = "characteristic value is null";
    private static final String DEVICE_IS_NULL = "device is null";
    private static final String SERVICE_IS_NULL = "service is null";
    private final BroadcastReceiver adapterStateReceiver;
    private final AdvertiseCallback advertiseCallback;
    private final BluetoothAdapter bluetoothAdapter;
    private final BluetoothGattServer bluetoothGattServer;
    private final BluetoothGattServerCallback bluetoothGattServerCallback;
    private final BluetoothLeAdvertiser bluetoothLeAdvertiser;
    private final BluetoothManager bluetoothManager;
    private final BluetoothPeripheralManagerCallback callback;
    private final HashMap<BluetoothGattCharacteristic, Set<String>> centralsWantingIndications;
    private final HashMap<BluetoothGattCharacteristic, Set<String>> centralsWantingNotifications;
    private final Queue<Runnable> commandQueue;
    private volatile boolean commandQueueBusy;
    private final Map<String, BluetoothCentral> connectedCentralsMap;
    private final Context context;
    private BluetoothGattCharacteristic currentNotifyCharacteristic;
    private byte[] currentNotifyValue;
    private byte[] currentReadValue;
    private boolean isAdvertising;
    private final Handler mainHandler;
    private final HashMap<BluetoothGattCharacteristic, byte[]> writeLongCharacteristicTemporaryBytes;
    private final HashMap<BluetoothGattDescriptor, byte[]> writeLongDescriptorTemporaryBytes;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = String.valueOf(Reflection.getOrCreateKotlinClass(BluetoothPeripheralManager.class).getSimpleName());

    /* JADX INFO: Access modifiers changed from: private */
    public final byte[] nonnullOf(byte[] source) {
        return source == null ? new byte[0] : source;
    }

    public BluetoothPeripheralManager(Context context, BluetoothManager bluetoothManager, BluetoothPeripheralManagerCallback callback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bluetoothManager, "bluetoothManager");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.context = context;
        this.bluetoothManager = bluetoothManager;
        this.callback = callback;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.commandQueue = new ConcurrentLinkedQueue();
        this.writeLongCharacteristicTemporaryBytes = new HashMap<>();
        this.writeLongDescriptorTemporaryBytes = new HashMap<>();
        this.connectedCentralsMap = new ConcurrentHashMap();
        this.centralsWantingNotifications = new HashMap<>();
        this.centralsWantingIndications = new HashMap<>();
        this.currentNotifyValue = new byte[0];
        this.currentReadValue = new byte[0];
        BluetoothPeripheralManager$bluetoothGattServerCallback$1 bluetoothPeripheralManager$bluetoothGattServerCallback$1 = new BluetoothPeripheralManager$bluetoothGattServerCallback$1(this);
        this.bluetoothGattServerCallback = bluetoothPeripheralManager$bluetoothGattServerCallback$1;
        this.advertiseCallback = new BluetoothPeripheralManager$advertiseCallback$1(this);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.welie.blessed.BluetoothPeripheralManager$adapterStateReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Intrinsics.checkNotNullParameter(context2, "context");
                Intrinsics.checkNotNullParameter(intent, "intent");
                String action = intent.getAction();
                if (action != null && Intrinsics.areEqual(action, "android.bluetooth.adapter.action.STATE_CHANGED")) {
                    this.this$0.handleAdapterState(intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE));
                }
            }
        };
        this.adapterStateReceiver = broadcastReceiver;
        BluetoothGattServer bluetoothGattServerOpenGattServer = bluetoothManager.openGattServer(context, bluetoothPeripheralManager$bluetoothGattServerCallback$1);
        Intrinsics.checkNotNullExpressionValue(bluetoothGattServerOpenGattServer, "bluetoothManager.openGat…etoothGattServerCallback)");
        this.bluetoothGattServer = bluetoothGattServerOpenGattServer;
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        Intrinsics.checkNotNullExpressionValue(adapter, "bluetoothManager.adapter");
        this.bluetoothAdapter = adapter;
        BluetoothLeAdvertiser bluetoothLeAdvertiser = adapter.getBluetoothLeAdvertiser();
        Intrinsics.checkNotNullExpressionValue(bluetoothLeAdvertiser, "bluetoothAdapter.bluetoothLeAdvertiser");
        this.bluetoothLeAdvertiser = bluetoothLeAdvertiser;
        context.registerReceiver(broadcastReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
    }

    /* renamed from: isAdvertising, reason: from getter */
    public final boolean getIsAdvertising() {
        return this.isAdvertising;
    }

    public final Queue<Runnable> getCommandQueue() {
        return this.commandQueue;
    }

    public final BluetoothGattServerCallback getBluetoothGattServerCallback() {
        return this.bluetoothGattServerCallback;
    }

    /* renamed from: getAdvertiseCallback$blessed_release, reason: from getter */
    public final AdvertiseCallback getAdvertiseCallback() {
        return this.advertiseCallback;
    }

    private final void onAdvertisingStopped() {
        Logger.INSTANCE.i(TAG, "advertising stopped");
        this.isAdvertising = false;
        this.mainHandler.post(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheralManager.onAdvertisingStopped$lambda$0(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAdvertisingStopped$lambda$0(BluetoothPeripheralManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.callback.onAdvertisingStopped();
    }

    public final void close() {
        stopAdvertising();
        this.context.unregisterReceiver(this.adapterStateReceiver);
        this.bluetoothGattServer.close();
    }

    public final void startAdvertising(AdvertiseSettings settings, AdvertiseData advertiseData, AdvertiseData scanResponse) {
        Intrinsics.checkNotNullParameter(settings, "settings");
        Intrinsics.checkNotNullParameter(advertiseData, "advertiseData");
        Intrinsics.checkNotNullParameter(scanResponse, "scanResponse");
        if (!this.bluetoothAdapter.isMultipleAdvertisementSupported()) {
            Logger.INSTANCE.e(TAG, "device does not support advertising");
            return;
        }
        if (this.isAdvertising) {
            Logger.INSTANCE.d(TAG, "already advertising, stopping advertising first");
            stopAdvertising();
        }
        this.bluetoothLeAdvertiser.startAdvertising(settings, advertiseData, scanResponse, this.advertiseCallback);
    }

    public final void stopAdvertising() {
        this.bluetoothLeAdvertiser.stopAdvertising(this.advertiseCallback);
        onAdvertisingStopped();
    }

    public final boolean add(final BluetoothGattService service) {
        Intrinsics.checkNotNullParameter(service, "service");
        return enqueue(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheralManager.add$lambda$1(this.f$0, service);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void add$lambda$1(BluetoothPeripheralManager this$0, BluetoothGattService service) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(service, "$service");
        if (this$0.bluetoothGattServer.addService(service)) {
            return;
        }
        Logger logger = Logger.INSTANCE;
        String str = TAG;
        UUID uuid = service.getUuid();
        Intrinsics.checkNotNullExpressionValue(uuid, "service.uuid");
        logger.e(str, "adding service %s failed", uuid);
        this$0.completedCommand();
    }

    public final boolean remove(BluetoothGattService service) {
        Intrinsics.checkNotNullParameter(service, "service");
        return this.bluetoothGattServer.removeService(service);
    }

    public final void removeAllServices() {
        this.bluetoothGattServer.clearServices();
    }

    public final List<BluetoothGattService> getServices() {
        List<BluetoothGattService> services = this.bluetoothGattServer.getServices();
        Intrinsics.checkNotNullExpressionValue(services, "bluetoothGattServer.services");
        return services;
    }

    public final boolean notifyCharacteristicChanged(byte[] value, BluetoothCentral bluetoothCentral, BluetoothGattCharacteristic characteristic) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(bluetoothCentral, "bluetoothCentral");
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
        BluetoothDevice device = bluetoothCentral.getDevice();
        boolean z = supportsIndicate(characteristic) && getCentralsWantingIndications(characteristic).contains(bluetoothCentral);
        if (z || getCentralsWantingNotifications(characteristic).contains(bluetoothCentral)) {
            return notifyCharacteristicChanged(copyOf(value), device, characteristic, z);
        }
        return false;
    }

    private final boolean notifyCharacteristicChanged(final byte[] value, final BluetoothDevice bluetoothDevice, final BluetoothGattCharacteristic characteristic, final boolean confirm) {
        if (doesNotSupportNotifying(characteristic)) {
            return false;
        }
        return enqueue(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheralManager.notifyCharacteristicChanged$lambda$2(this.f$0, bluetoothDevice, characteristic, value, confirm);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void notifyCharacteristicChanged$lambda$2(BluetoothPeripheralManager this$0, BluetoothDevice bluetoothDevice, BluetoothGattCharacteristic characteristic, byte[] value, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(bluetoothDevice, "$bluetoothDevice");
        Intrinsics.checkNotNullParameter(characteristic, "$characteristic");
        Intrinsics.checkNotNullParameter(value, "$value");
        if (this$0.internalNotifyCharacteristicChanged(bluetoothDevice, characteristic, value, z)) {
            return;
        }
        Logger logger = Logger.INSTANCE;
        String str = TAG;
        UUID uuid = characteristic.getUuid();
        Intrinsics.checkNotNullExpressionValue(uuid, "characteristic.uuid");
        logger.e(str, "notifying characteristic changed failed for <%s>", uuid);
        this$0.completedCommand();
    }

    private final boolean internalNotifyCharacteristicChanged(BluetoothDevice device, BluetoothGattCharacteristic characteristic, byte[] value, boolean confirm) {
        this.currentNotifyValue = value;
        this.currentNotifyCharacteristic = characteristic;
        if (Build.VERSION.SDK_INT >= 33) {
            return this.bluetoothGattServer.notifyCharacteristicChanged(device, characteristic, confirm, value) == 0;
        }
        characteristic.setValue(value);
        return this.bluetoothGattServer.notifyCharacteristicChanged(device, characteristic, confirm);
    }

    public final void cancelConnection(BluetoothCentral bluetoothCentral) {
        Intrinsics.checkNotNullParameter(bluetoothCentral, "bluetoothCentral");
        cancelConnection(bluetoothCentral.getDevice());
    }

    private final void cancelConnection(BluetoothDevice bluetoothDevice) {
        Logger logger = Logger.INSTANCE;
        String str = TAG;
        Object[] objArr = new Object[2];
        String name = bluetoothDevice.getName();
        if (name == null) {
            name = "null";
        }
        objArr[0] = name;
        String address = bluetoothDevice.getAddress();
        Intrinsics.checkNotNullExpressionValue(address, "bluetoothDevice.address");
        objArr[1] = address;
        logger.i(str, "cancelConnection with '%s' (%s)", objArr);
        this.bluetoothGattServer.cancelConnection(bluetoothDevice);
    }

    private final List<BluetoothDevice> getConnectedDevices() {
        List<BluetoothDevice> connectedDevices = this.bluetoothManager.getConnectedDevices(7);
        Intrinsics.checkNotNullExpressionValue(connectedDevices, "bluetoothManager.getConn…BluetoothGattServer.GATT)");
        return connectedDevices;
    }

    public final Set<BluetoothCentral> getConnectedCentrals() {
        Set<BluetoothCentral> setUnmodifiableSet = Collections.unmodifiableSet(new HashSet(this.connectedCentralsMap.values()));
        Intrinsics.checkNotNullExpressionValue(setUnmodifiableSet, "unmodifiableSet(bluetoothCentrals)");
        return setUnmodifiableSet;
    }

    private final boolean enqueue(Runnable command) {
        boolean zAdd = this.commandQueue.add(command);
        if (zAdd) {
            nextCommand();
        } else {
            Logger.INSTANCE.e(TAG, "could not enqueue command");
        }
        return zAdd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void completedCommand() {
        this.commandQueue.poll();
        this.commandQueueBusy = false;
        nextCommand();
    }

    private final void nextCommand() {
        synchronized (this) {
            if (this.commandQueueBusy) {
                return;
            }
            final Runnable runnablePeek = this.commandQueue.peek();
            if (runnablePeek == null) {
                return;
            }
            Intrinsics.checkNotNullExpressionValue(runnablePeek, "commandQueue.peek() ?: return");
            this.commandQueueBusy = true;
            this.mainHandler.post(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheralManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothPeripheralManager.nextCommand$lambda$4$lambda$3(runnablePeek, this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void nextCommand$lambda$4$lambda$3(Runnable bluetoothCommand, BluetoothPeripheralManager this$0) {
        Intrinsics.checkNotNullParameter(bluetoothCommand, "$bluetoothCommand");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            bluetoothCommand.run();
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "command exception");
            Logger.INSTANCE.e(TAG, e.toString());
            this$0.completedCommand();
        }
    }

    public final BluetoothCentral getCentral(String address) {
        Intrinsics.checkNotNullParameter(address, "address");
        return this.connectedCentralsMap.get(address);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BluetoothCentral getCentral(BluetoothDevice device) {
        BluetoothCentral bluetoothCentral = this.connectedCentralsMap.get(device.getAddress());
        return bluetoothCentral == null ? new BluetoothCentral(device) : bluetoothCentral;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removeCentral(BluetoothDevice device) {
        this.connectedCentralsMap.remove(device.getAddress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleAdapterState(int state) {
        switch (state) {
            case 10:
                Logger.INSTANCE.d(TAG, "bluetooth turned off");
                cancelAllConnectionsWhenBluetoothOff();
                break;
            case 11:
                Logger.INSTANCE.d(TAG, "bluetooth turning on");
                break;
            case 12:
                Logger.INSTANCE.d(TAG, "bluetooth turned on");
                break;
            case 13:
                Logger.INSTANCE.d(TAG, "bluetooth turning off");
                break;
        }
    }

    private final void cancelAllConnectionsWhenBluetoothOff() {
        Iterator<BluetoothCentral> it = getConnectedCentrals().iterator();
        while (it.hasNext()) {
            this.bluetoothGattServerCallback.onConnectionStateChange(this.bluetoothAdapter.getRemoteDevice(it.next().getAddress()), 0, 0);
        }
        onAdvertisingStopped();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final byte[] chopValue(byte[] value, int offset) {
        byte[] bArr = new byte[0];
        return (value != null && offset <= value.length) ? ArraysKt.copyOfRange(value, offset, value.length) : bArr;
    }

    private final byte[] copyOf(byte[] source, int offset, int maxSize) {
        if (source.length > maxSize) {
            byte[] bArrCopyOfRange = Arrays.copyOfRange(source, offset, Math.min(source.length - offset, maxSize) + offset);
            Intrinsics.checkNotNullExpressionValue(bArrCopyOfRange, "copyOfRange(source, offset, offset + chunkSize)");
            return bArrCopyOfRange;
        }
        byte[] bArrCopyOf = Arrays.copyOf(source, source.length);
        Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(source, source.size)");
        return bArrCopyOf;
    }

    public final byte[] copyOf(byte[] source) {
        if (source == null) {
            return new byte[0];
        }
        byte[] bArrCopyOf = Arrays.copyOf(source, source.length);
        Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(source, source.size)");
        return bArrCopyOf;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean supportsNotify(BluetoothGattCharacteristic characteristic) {
        return (characteristic.getProperties() & 16) > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean supportsIndicate(BluetoothGattCharacteristic characteristic) {
        return (characteristic.getProperties() & 32) > 0;
    }

    private final boolean doesNotSupportNotifying(BluetoothGattCharacteristic characteristic) {
        return (supportsIndicate(characteristic) || supportsNotify(characteristic)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addCentralWantingIndications(BluetoothGattCharacteristic characteristic, BluetoothCentral central) {
        HashSet hashSet = this.centralsWantingIndications.get(characteristic);
        if (hashSet == null) {
            hashSet = new HashSet();
            this.centralsWantingIndications.put(characteristic, hashSet);
        }
        hashSet.add(central.getAddress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addCentralWantingNotifications(BluetoothGattCharacteristic characteristic, BluetoothCentral central) {
        HashSet hashSet = this.centralsWantingNotifications.get(characteristic);
        if (hashSet == null) {
            hashSet = new HashSet();
            this.centralsWantingNotifications.put(characteristic, hashSet);
        }
        hashSet.add(central.getAddress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removeCentralWantingIndications(BluetoothGattCharacteristic characteristic, BluetoothCentral central) {
        Set<String> set = this.centralsWantingIndications.get(characteristic);
        if (set != null) {
            set.remove(central.getAddress());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removeCentralWantingNotifications(BluetoothGattCharacteristic characteristic, BluetoothCentral central) {
        Set<String> set = this.centralsWantingNotifications.get(characteristic);
        if (set != null) {
            set.remove(central.getAddress());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removeCentralFromWantingAnything(BluetoothCentral central) {
        String address = central.getAddress();
        Iterator<Map.Entry<BluetoothGattCharacteristic, Set<String>>> it = this.centralsWantingIndications.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().remove(address);
        }
        Iterator<Map.Entry<BluetoothGattCharacteristic, Set<String>>> it2 = this.centralsWantingNotifications.entrySet().iterator();
        while (it2.hasNext()) {
            it2.next().getValue().remove(address);
        }
    }

    public final Set<BluetoothCentral> getCentralsWantingIndications(BluetoothGattCharacteristic characteristic) {
        Set<BluetoothCentral> centralsByAddress;
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
        Set<String> set = this.centralsWantingIndications.get(characteristic);
        return (set == null || (centralsByAddress = getCentralsByAddress(set)) == null) ? SetsKt.emptySet() : centralsByAddress;
    }

    public final Set<BluetoothCentral> getCentralsWantingNotifications(BluetoothGattCharacteristic characteristic) {
        Set<BluetoothCentral> centralsByAddress;
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
        Set<String> set = this.centralsWantingNotifications.get(characteristic);
        return (set == null || (centralsByAddress = getCentralsByAddress(set)) == null) ? SetsKt.emptySet() : centralsByAddress;
    }

    private final Set<BluetoothCentral> getCentralsByAddress(Set<String> centralAddresses) {
        HashSet hashSet = new HashSet();
        Iterator<String> it = centralAddresses.iterator();
        while (it.hasNext()) {
            BluetoothCentral central = getCentral(it.next());
            if (central != null) {
                hashSet.add(central);
            }
        }
        return hashSet;
    }

    /* compiled from: BluetoothPeripheralManager.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/welie/blessed/BluetoothPeripheralManager$Companion;", "", "()V", "ADDRESS_IS_NULL", "", "CCC_DESCRIPTOR_UUID", "Ljava/util/UUID;", "getCCC_DESCRIPTOR_UUID", "()Ljava/util/UUID;", "CENTRAL_IS_NULL", "CHARACTERISTIC_IS_NULL", "CHARACTERISTIC_VALUE_IS_NULL", "DEVICE_IS_NULL", "SERVICE_IS_NULL", "TAG", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final UUID getCCC_DESCRIPTOR_UUID() {
            return BluetoothPeripheralManager.CCC_DESCRIPTOR_UUID;
        }
    }

    static {
        UUID uuidFromString = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        Intrinsics.checkNotNullExpressionValue(uuidFromString, "fromString(\"00002902-0000-1000-8000-00805f9b34fb\")");
        CCC_DESCRIPTOR_UUID = uuidFromString;
    }
}
