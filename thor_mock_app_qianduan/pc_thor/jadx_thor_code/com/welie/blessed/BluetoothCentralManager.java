package com.welie.blessed;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.welie.blessed.BluetoothCentralManagerCallback;
import com.welie.blessed.BluetoothPeripheral;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;

/* compiled from: BluetoothCentralManager.kt */
@Metadata(d1 = {"\u0000Ð\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\"\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u0007\u0018\u0000 \u0081\u00012\u00020\u0001:\u0002\u0081\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010F\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u001dJ\u0010\u0010G\u001a\u00020\u000b2\u0006\u0010H\u001a\u00020\u001cH\u0002J\u0014\u0010I\u001a\u00020\u000b2\f\u0010J\u001a\b\u0012\u0004\u0012\u00020\u001d0KJ\b\u0010L\u001a\u000202H\u0002J\b\u0010M\u001a\u00020\u000bH\u0002J\b\u0010N\u001a\u00020\u000bH\u0002J\u0019\u0010O\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u001dH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010PJ\u0018\u0010O\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u001d2\u0006\u0010Q\u001a\u00020%H\u0002J\b\u0010R\u001a\u00020\u000bH\u0002J\u0010\u0010S\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u001dH\u0002J\u0006\u0010T\u001a\u00020\u000bJ\u0019\u0010U\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u001dH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010PJ\u0018\u0010U\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u001d2\u0006\u0010Q\u001a\u00020%H\u0002J\u0006\u0010V\u001a\u00020\u000bJ\u0006\u0010W\u001a\u00020\u000bJ\f\u0010X\u001a\b\u0012\u0004\u0012\u00020\u001d0'J\u000e\u0010Y\u001a\u00020\u001d2\u0006\u0010H\u001a\u00020\u001cJ\u0010\u0010Z\u001a\u00020\u00132\u0006\u0010[\u001a\u00020\\H\u0002J\u0010\u0010]\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u0007H\u0002J)\u0010^\u001a\u00020\u000b2!\u0010_\u001a\u001d\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0006J>\u0010`\u001a\u00020\u000b26\u0010a\u001a2\u0012\u0013\u0012\u00110\u001d¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(!\u0012\u0013\u0012\u00110\"¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0 J\b\u0010b\u001a\u000202H\u0002J\u000e\u0010c\u001a\u0002022\u0006\u0010H\u001a\u00020\u001cJ\b\u0010d\u001a\u00020\u000bH\u0002J4\u0010e\u001a\u00020\u000b2\u0018\u0010f\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u000b0 2\u0012\u0010g\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u000b0\u0006JB\u0010h\u001a\u00020\u000b2\f\u0010i\u001a\b\u0012\u0004\u0012\u00020(0'2\u0018\u0010f\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u000b0 2\u0012\u0010g\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u000b0\u0006JG\u0010j\u001a\u00020\u000b2\f\u0010k\u001a\b\u0012\u0004\u0012\u00020\u001c0>2\u0018\u0010f\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u000b0 2\u0012\u0010g\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u000b0\u0006¢\u0006\u0002\u0010lJG\u0010m\u001a\u00020\u000b2\f\u0010n\u001a\b\u0012\u0004\u0012\u00020\u001c0>2\u0018\u0010f\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u000b0 2\u0012\u0010g\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u000b0\u0006¢\u0006\u0002\u0010lJG\u0010o\u001a\u00020\u000b2\f\u0010p\u001a\b\u0012\u0004\u0012\u00020q0>2\u0018\u0010f\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u000b0 2\u0012\u0010g\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u000b0\u0006¢\u0006\u0002\u0010rJ\u0010\u0010s\u001a\u00020\u000b2\u0006\u0010t\u001a\u00020,H\u0002J\u0010\u0010u\u001a\u00020\u000b2\u0006\u0010v\u001a\u00020*H\u0002J\b\u0010w\u001a\u00020\u000bH\u0002J\u0016\u0010x\u001a\u0002022\u0006\u0010H\u001a\u00020\u001c2\u0006\u0010y\u001a\u00020\u001cJ\u000e\u0010z\u001a\u00020\u000b2\u0006\u0010[\u001a\u00020\\J\b\u0010{\u001a\u00020\u000bH\u0002J\u0006\u0010|\u001a\u00020\u000bJ&\u0010}\u001a\u00020\u000b2\f\u0010i\u001a\b\u0012\u0004\u0012\u00020(0'2\u0006\u0010@\u001a\u00020\u00132\u0006\u0010~\u001a\u00020\u0011H\u0002J\b\u0010\u007f\u001a\u00020\u000bH\u0002J\u0007\u0010\u0080\u0001\u001a\u00020\u000bR)\u0010\u0005\u001a\u001d\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001d0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00070\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R>\u0010\u001f\u001a2\u0012\u0013\u0012\u00110\u001d¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(!\u0012\u0013\u0012\u00110\"¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010&\u001a\n\u0012\u0004\u0012\u00020(\u0018\u00010'X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010)\u001a\u0016\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u000b\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010+\u001a\u0010\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u0002008\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0014\u00101\u001a\u0002028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b1\u00103R\u0014\u00104\u001a\u0002028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b4\u00103R\u0011\u00105\u001a\u0002028F¢\u0006\u0006\u001a\u0004\b5\u00103R\u0011\u00106\u001a\u0002028F¢\u0006\u0006\u001a\u0004\b6\u00103R\u000e\u00107\u001a\u000208X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00109\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001c0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010:\u001a\b\u0012\u0004\u0012\u00020\u001c0;X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010=\u001a\b\u0012\u0004\u0012\u00020\u001c0>X\u0082\u000e¢\u0006\u0004\n\u0002\u0010?R\u000e\u0010@\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010A\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001d0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020CX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010D\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010E\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001d0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0082\u0001"}, d2 = {"Lcom/welie/blessed/BluetoothCentralManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "adapterStateCallback", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "state", "", "adapterStateReceiver", "Landroid/content/BroadcastReceiver;", "autoConnectRunnable", "Ljava/lang/Runnable;", "autoConnectScanCallback", "Landroid/bluetooth/le/ScanCallback;", "autoConnectScanSettings", "Landroid/bluetooth/le/ScanSettings;", "autoConnectScanner", "Landroid/bluetooth/le/BluetoothLeScanner;", "bluetoothAdapter", "Landroid/bluetooth/BluetoothAdapter;", "bluetoothScanner", "connectLock", "connectedPeripherals", "", "", "Lcom/welie/blessed/BluetoothPeripheral;", "connectionRetries", "connectionStateCallback", "Lkotlin/Function2;", "peripheral", "Lcom/welie/blessed/ConnectionState;", "currentCallback", "currentCentralManagerCallback", "Lcom/welie/blessed/BluetoothCentralManagerCallback;", "currentFilters", "", "Landroid/bluetooth/le/ScanFilter;", "currentResultCallback", "Landroid/bluetooth/le/ScanResult;", "currentScanErrorCallback", "Lcom/welie/blessed/ScanFailure;", "defaultScanCallback", "disconnectRunnable", "internalCallback", "Lcom/welie/blessed/BluetoothPeripheral$InternalCallback;", "isAutoScanning", "", "()Z", "isBleSupported", "isBluetoothEnabled", "isScanning", "mainHandler", "Landroid/os/Handler;", "pinCodes", "reconnectPeripheralAddresses", "", "scanByNameCallback", "scanPeripheralNames", "", "[Ljava/lang/String;", "scanSettings", "scannedPeripherals", "scope", "Lkotlinx/coroutines/CoroutineScope;", "timeoutRunnable", "unconnectedPeripherals", "autoConnectPeripheral", "autoConnectPeripheralByScan", "peripheralAddress", "autoConnectPeripheralsBatch", "batch", "", "bleNotReady", "cancelAllConnectionsWhenBluetoothOff", "cancelAutoConnectTimer", "cancelConnection", "(Lcom/welie/blessed/BluetoothPeripheral;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resultCentralManagerCallback", "cancelTimeoutTimer", "checkPeripheralStatus", "close", "connectPeripheral", "disableLogging", "enableLogging", "getConnectedPeripherals", "getPeripheral", "getScanSettings", "scanMode", "Lcom/welie/blessed/ScanMode;", "handleAdapterState", "observeAdapterState", "callback", "observeConnectionState", "connectionCallback", "permissionsGranted", "removeBond", "scanForAutoConnectPeripherals", "scanForPeripherals", "resultCallback", "scanError", "scanForPeripheralsUsingFilters", "filters", "scanForPeripheralsWithAddresses", "peripheralAddresses", "([Ljava/lang/String;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;)V", "scanForPeripheralsWithNames", "peripheralNames", "scanForPeripheralsWithServices", "serviceUUIDs", "Ljava/util/UUID;", "([Ljava/util/UUID;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;)V", "sendScanFailed", "scanFailure", "sendScanResult", "result", "setAutoConnectTimer", "setPinCodeForPeripheral", "pin", "setScanMode", "setScanTimer", "startPairingPopupHack", "startScan", "scanCallback", "stopAutoconnectScan", "stopScan", "Companion", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BluetoothCentralManager {
    private static final int MAX_CONNECTION_RETRIES = 1;
    private static final String NO_PERIPHERAL_ADDRESS_PROVIDED = "no peripheral address provided";
    private static final String NO_VALID_PERIPHERAL_CALLBACK_SPECIFIED = "no valid peripheral callback specified";
    private static final String NO_VALID_PERIPHERAL_PROVIDED = "no valid peripheral provided";
    private static final long SCAN_RESTART_DELAY = 1000;
    private static final long SCAN_TIMEOUT = 180000;
    private Function1<? super Integer, Unit> adapterStateCallback;
    private final BroadcastReceiver adapterStateReceiver;
    private Runnable autoConnectRunnable;
    private final ScanCallback autoConnectScanCallback;
    private final ScanSettings autoConnectScanSettings;
    private BluetoothLeScanner autoConnectScanner;
    private final BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothScanner;
    private final Object connectLock;
    private final Map<String, BluetoothPeripheral> connectedPeripherals;
    private final Map<String, Integer> connectionRetries;
    private Function2<? super BluetoothPeripheral, ? super ConnectionState, Unit> connectionStateCallback;
    private final Context context;
    private ScanCallback currentCallback;
    private BluetoothCentralManagerCallback currentCentralManagerCallback;
    private List<ScanFilter> currentFilters;
    private Function2<? super BluetoothPeripheral, ? super ScanResult, Unit> currentResultCallback;
    private Function1<? super ScanFailure, Unit> currentScanErrorCallback;
    private final ScanCallback defaultScanCallback;
    private Runnable disconnectRunnable;
    public final BluetoothPeripheral.InternalCallback internalCallback;
    private final Handler mainHandler;
    private final Map<String, String> pinCodes;
    private final List<String> reconnectPeripheralAddresses;
    private final ScanCallback scanByNameCallback;
    private String[] scanPeripheralNames;
    private ScanSettings scanSettings;
    private final Map<String, BluetoothPeripheral> scannedPeripherals;
    private final CoroutineScope scope;
    private Runnable timeoutRunnable;
    private final Map<String, BluetoothPeripheral> unconnectedPeripherals;
    private static final String TAG = String.valueOf(Reflection.getOrCreateKotlinClass(BluetoothCentralManager.class).getSimpleName());

    public BluetoothCentralManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.scope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)));
        this.currentCentralManagerCallback = new BluetoothCentralManagerCallback.NULL();
        this.connectedPeripherals = new ConcurrentHashMap();
        this.unconnectedPeripherals = new ConcurrentHashMap();
        this.scannedPeripherals = new ConcurrentHashMap();
        this.reconnectPeripheralAddresses = new ArrayList();
        this.scanPeripheralNames = new String[0];
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.connectLock = new Object();
        this.connectionRetries = new ConcurrentHashMap();
        this.pinCodes = new ConcurrentHashMap();
        this.adapterStateCallback = new Function1<Integer, Unit>() { // from class: com.welie.blessed.BluetoothCentralManager$adapterStateCallback$1
            public final void invoke(int i) {
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke(num.intValue());
                return Unit.INSTANCE;
            }
        };
        this.scanByNameCallback = new ScanCallback() { // from class: com.welie.blessed.BluetoothCentralManager$scanByNameCallback$1
            @Override // android.bluetooth.le.ScanCallback
            public void onScanResult(int callbackType, ScanResult result) {
                Intrinsics.checkNotNullParameter(result, "result");
                BluetoothCentralManager bluetoothCentralManager = this.this$0;
                synchronized (this) {
                    String name = result.getDevice().getName();
                    if (name == null) {
                        return;
                    }
                    Intrinsics.checkNotNullExpressionValue(name, "result.device.name ?: return");
                    for (String str : bluetoothCentralManager.scanPeripheralNames) {
                        if (StringsKt.contains$default((CharSequence) name, (CharSequence) str, false, 2, (Object) null)) {
                            bluetoothCentralManager.sendScanResult(result);
                            return;
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onScanFailed(int errorCode) {
                this.this$0.sendScanFailed(ScanFailure.INSTANCE.fromValue(errorCode));
            }
        };
        this.defaultScanCallback = new ScanCallback() { // from class: com.welie.blessed.BluetoothCentralManager$defaultScanCallback$1
            @Override // android.bluetooth.le.ScanCallback
            public void onScanResult(int callbackType, ScanResult result) {
                Intrinsics.checkNotNullParameter(result, "result");
                BluetoothCentralManager bluetoothCentralManager = this.this$0;
                synchronized (this) {
                    bluetoothCentralManager.sendScanResult(result);
                    Unit unit = Unit.INSTANCE;
                }
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onScanFailed(int errorCode) {
                this.this$0.sendScanFailed(ScanFailure.INSTANCE.fromValue(errorCode));
            }
        };
        this.autoConnectScanCallback = new ScanCallback() { // from class: com.welie.blessed.BluetoothCentralManager$autoConnectScanCallback$1
            @Override // android.bluetooth.le.ScanCallback
            public void onScanResult(int callbackType, ScanResult result) {
                Intrinsics.checkNotNullParameter(result, "result");
                BluetoothCentralManager bluetoothCentralManager = this.this$0;
                synchronized (this) {
                    if (bluetoothCentralManager.isAutoScanning()) {
                        Logger logger = Logger.INSTANCE;
                        String str = BluetoothCentralManager.TAG;
                        String address = result.getDevice().getAddress();
                        Intrinsics.checkNotNullExpressionValue(address, "result.device.address");
                        logger.d(str, "peripheral with address '%s' found", address);
                        bluetoothCentralManager.stopAutoconnectScan();
                        String address2 = result.getDevice().getAddress();
                        BluetoothPeripheral bluetoothPeripheral = (BluetoothPeripheral) bluetoothCentralManager.unconnectedPeripherals.get(address2);
                        bluetoothCentralManager.reconnectPeripheralAddresses.remove(address2);
                        bluetoothCentralManager.unconnectedPeripherals.remove(address2);
                        bluetoothCentralManager.scannedPeripherals.remove(address2);
                        if (bluetoothPeripheral != null) {
                            bluetoothCentralManager.autoConnectPeripheral(bluetoothPeripheral);
                        }
                        if (bluetoothCentralManager.reconnectPeripheralAddresses.size() > 0) {
                            bluetoothCentralManager.scanForAutoConnectPeripherals();
                        }
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onScanFailed(int errorCode) {
                Logger.INSTANCE.e(BluetoothCentralManager.TAG, "autoConnect scan failed with error code %d (%s)", Integer.valueOf(errorCode), ScanFailure.INSTANCE.fromValue(errorCode));
                this.this$0.autoConnectScanner = null;
            }
        };
        this.internalCallback = new BluetoothPeripheral.InternalCallback() { // from class: com.welie.blessed.BluetoothCentralManager$internalCallback$1
            @Override // com.welie.blessed.BluetoothPeripheral.InternalCallback
            public void connecting(BluetoothPeripheral peripheral) {
                Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                BuildersKt__Builders_commonKt.launch$default(this.this$0.scope, null, null, new BluetoothCentralManager$internalCallback$1$connecting$1(this.this$0, peripheral, null), 3, null);
            }

            @Override // com.welie.blessed.BluetoothPeripheral.InternalCallback
            public void connected(BluetoothPeripheral peripheral) {
                Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                this.this$0.connectionRetries.remove(peripheral.getAddress());
                this.this$0.unconnectedPeripherals.remove(peripheral.getAddress());
                this.this$0.scannedPeripherals.remove(peripheral.getAddress());
                this.this$0.connectedPeripherals.put(peripheral.getAddress(), peripheral);
                BuildersKt__Builders_commonKt.launch$default(this.this$0.scope, null, null, new BluetoothCentralManager$internalCallback$1$connected$1(this.this$0, peripheral, null), 3, null);
                BuildersKt__Builders_commonKt.launch$default(this.this$0.scope, null, null, new BluetoothCentralManager$internalCallback$1$connected$2(this.this$0, peripheral, null), 3, null);
            }

            @Override // com.welie.blessed.BluetoothPeripheral.InternalCallback
            public void connectFailed(BluetoothPeripheral peripheral, HciStatus status) {
                Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                Intrinsics.checkNotNullParameter(status, "status");
                this.this$0.unconnectedPeripherals.remove(peripheral.getAddress());
                this.this$0.scannedPeripherals.remove(peripheral.getAddress());
                Integer num = (Integer) this.this$0.connectionRetries.get(peripheral.getAddress());
                int iIntValue = num != null ? num.intValue() : 0;
                if (iIntValue >= 1 || status == HciStatus.CONNECTION_FAILED_ESTABLISHMENT) {
                    Logger.INSTANCE.i(BluetoothCentralManager.TAG, "connection to '%s' (%s) failed", peripheral.getName(), peripheral.getAddress());
                    this.this$0.connectionRetries.remove(peripheral.getAddress());
                    BuildersKt__Builders_commonKt.launch$default(this.this$0.scope, null, null, new BluetoothCentralManager$internalCallback$1$connectFailed$1(this.this$0, peripheral, status, null), 3, null);
                    BuildersKt__Builders_commonKt.launch$default(this.this$0.scope, null, null, new BluetoothCentralManager$internalCallback$1$connectFailed$2(this.this$0, peripheral, null), 3, null);
                    return;
                }
                Logger.INSTANCE.i(BluetoothCentralManager.TAG, "retrying connection to '%s' (%s)", peripheral.getName(), peripheral.getAddress());
                this.this$0.connectionRetries.put(peripheral.getAddress(), Integer.valueOf(iIntValue + 1));
                this.this$0.unconnectedPeripherals.put(peripheral.getAddress(), peripheral);
                peripheral.connect();
            }

            @Override // com.welie.blessed.BluetoothPeripheral.InternalCallback
            public void disconnecting(BluetoothPeripheral peripheral) {
                Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                BuildersKt__Builders_commonKt.launch$default(this.this$0.scope, null, null, new BluetoothCentralManager$internalCallback$1$disconnecting$1(this.this$0, peripheral, null), 3, null);
            }

            @Override // com.welie.blessed.BluetoothPeripheral.InternalCallback
            public void disconnected(BluetoothPeripheral peripheral, HciStatus status) {
                Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                Intrinsics.checkNotNullParameter(status, "status");
                this.this$0.connectedPeripherals.remove(peripheral.getAddress());
                this.this$0.unconnectedPeripherals.remove(peripheral.getAddress());
                this.this$0.scannedPeripherals.remove(peripheral.getAddress());
                this.this$0.connectionRetries.remove(peripheral.getAddress());
                BuildersKt__Builders_commonKt.launch$default(this.this$0.scope, null, null, new BluetoothCentralManager$internalCallback$1$disconnected$1(this.this$0, peripheral, status, null), 3, null);
                BuildersKt__Builders_commonKt.launch$default(this.this$0.scope, null, null, new BluetoothCentralManager$internalCallback$1$disconnected$2(this.this$0, peripheral, null), 3, null);
            }

            @Override // com.welie.blessed.BluetoothPeripheral.InternalCallback
            public String getPincode(BluetoothPeripheral peripheral) {
                Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                return (String) this.this$0.pinCodes.get(peripheral.getAddress());
            }
        };
        this.connectionStateCallback = new Function2<BluetoothPeripheral, ConnectionState, Unit>() { // from class: com.welie.blessed.BluetoothCentralManager$connectionStateCallback$1
            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BluetoothPeripheral bluetoothPeripheral, ConnectionState connectionState) {
                Intrinsics.checkNotNullParameter(bluetoothPeripheral, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(connectionState, "<anonymous parameter 1>");
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(BluetoothPeripheral bluetoothPeripheral, ConnectionState connectionState) {
                invoke2(bluetoothPeripheral, connectionState);
                return Unit.INSTANCE;
            }
        };
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.welie.blessed.BluetoothCentralManager$adapterStateReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Intrinsics.checkNotNullParameter(context2, "context");
                Intrinsics.checkNotNullParameter(intent, "intent");
                String action = intent.getAction();
                if (action != null && Intrinsics.areEqual(action, "android.bluetooth.adapter.action.STATE_CHANGED")) {
                    int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                    this.this$0.handleAdapterState(intExtra);
                    this.this$0.adapterStateCallback.invoke(Integer.valueOf(intExtra));
                }
            }
        };
        this.adapterStateReceiver = broadcastReceiver;
        Object systemService = context.getSystemService("bluetooth");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.bluetooth.BluetoothManager");
        BluetoothAdapter adapter = ((BluetoothManager) systemService).getAdapter();
        Intrinsics.checkNotNullExpressionValue(adapter, "manager.adapter");
        this.bluetoothAdapter = adapter;
        this.autoConnectScanSettings = getScanSettings(ScanMode.LOW_POWER);
        this.scanSettings = getScanSettings(ScanMode.LOW_LATENCY);
        try {
            context.registerReceiver(broadcastReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Error in init: " + e);
        }
    }

    /* compiled from: BluetoothCentralManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothCentralManager$sendScanResult$1", f = "BluetoothCentralManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.welie.blessed.BluetoothCentralManager$sendScanResult$1, reason: invalid class name and case insensitive filesystem */
    static final class C04431 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ScanResult $result;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04431(ScanResult scanResult, Continuation<? super C04431> continuation) {
            super(2, continuation);
            this.$result = scanResult;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return BluetoothCentralManager.this.new C04431(this.$result, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04431) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (BluetoothCentralManager.this.isScanning()) {
                BluetoothCentralManager bluetoothCentralManager = BluetoothCentralManager.this;
                String address = this.$result.getDevice().getAddress();
                Intrinsics.checkNotNullExpressionValue(address, "result.device.address");
                BluetoothPeripheral peripheral = bluetoothCentralManager.getPeripheral(address);
                BluetoothDevice device = this.$result.getDevice();
                Intrinsics.checkNotNullExpressionValue(device, "result.device");
                peripheral.setDevice(device);
                Function2 function2 = BluetoothCentralManager.this.currentResultCallback;
                if (function2 != null) {
                    function2.invoke(peripheral, this.$result);
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendScanResult(ScanResult result) {
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C04431(result, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendScanFailed(ScanFailure scanFailure) {
        this.currentCallback = null;
        this.currentFilters = null;
        cancelTimeoutTimer();
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new AnonymousClass1(scanFailure, this, null), 3, null);
    }

    /* compiled from: BluetoothCentralManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothCentralManager$sendScanFailed$1", f = "BluetoothCentralManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.welie.blessed.BluetoothCentralManager$sendScanFailed$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ScanFailure $scanFailure;
        int label;
        final /* synthetic */ BluetoothCentralManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(ScanFailure scanFailure, BluetoothCentralManager bluetoothCentralManager, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$scanFailure = scanFailure;
            this.this$0 = bluetoothCentralManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$scanFailure, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Logger.INSTANCE.e(BluetoothCentralManager.TAG, "scan failed with error code %d (%s)", Boxing.boxInt(this.$scanFailure.getValue()), this.$scanFailure);
                Function1 function1 = this.this$0.currentScanErrorCallback;
                if (function1 != null) {
                    function1.invoke(this.$scanFailure);
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    public final void close() {
        this.unconnectedPeripherals.clear();
        this.connectedPeripherals.clear();
        this.reconnectPeripheralAddresses.clear();
        this.scannedPeripherals.clear();
        this.context.unregisterReceiver(this.adapterStateReceiver);
    }

    private final ScanSettings getScanSettings(ScanMode scanMode) {
        ScanSettings scanSettingsBuild = new ScanSettings.Builder().setScanMode(scanMode.getValue()).setCallbackType(1).setMatchMode(1).setNumOfMatches(1).setReportDelay(0L).build();
        Intrinsics.checkNotNullExpressionValue(scanSettingsBuild, "Builder()\n            .s…(0L)\n            .build()");
        return scanSettingsBuild;
    }

    public final void setScanMode(ScanMode scanMode) {
        Intrinsics.checkNotNullParameter(scanMode, "scanMode");
        this.scanSettings = getScanSettings(scanMode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startScan(List<ScanFilter> filters, ScanSettings scanSettings, ScanCallback scanCallback) {
        if (bleNotReady()) {
            return;
        }
        if (this.bluetoothScanner == null) {
            this.bluetoothScanner = this.bluetoothAdapter.getBluetoothLeScanner();
        }
        if (this.bluetoothScanner != null) {
            setScanTimer();
            this.currentCallback = scanCallback;
            this.currentFilters = filters;
            BluetoothLeScanner bluetoothLeScanner = this.bluetoothScanner;
            if (bluetoothLeScanner != null) {
                bluetoothLeScanner.startScan(filters, scanSettings, scanCallback);
            }
            Logger.INSTANCE.i(TAG, "scan started");
            return;
        }
        Logger.INSTANCE.e(TAG, "starting scan failed");
    }

    public final void scanForPeripheralsWithServices(UUID[] serviceUUIDs, Function2<? super BluetoothPeripheral, ? super ScanResult, Unit> resultCallback, Function1<? super ScanFailure, Unit> scanError) {
        Intrinsics.checkNotNullParameter(serviceUUIDs, "serviceUUIDs");
        Intrinsics.checkNotNullParameter(resultCallback, "resultCallback");
        Intrinsics.checkNotNullParameter(scanError, "scanError");
        if (!(!(serviceUUIDs.length == 0))) {
            throw new IllegalArgumentException("at least one service UUID  must be supplied".toString());
        }
        if (isScanning()) {
            stopScan();
        }
        ArrayList arrayList = new ArrayList();
        for (UUID uuid : serviceUUIDs) {
            ScanFilter filter = new ScanFilter.Builder().setServiceUuid(new ParcelUuid(uuid)).build();
            Intrinsics.checkNotNullExpressionValue(filter, "filter");
            arrayList.add(filter);
        }
        this.currentResultCallback = resultCallback;
        this.currentScanErrorCallback = scanError;
        startScan(arrayList, this.scanSettings, this.defaultScanCallback);
    }

    public final void scanForPeripheralsWithNames(String[] peripheralNames, Function2<? super BluetoothPeripheral, ? super ScanResult, Unit> resultCallback, Function1<? super ScanFailure, Unit> scanError) {
        Intrinsics.checkNotNullParameter(peripheralNames, "peripheralNames");
        Intrinsics.checkNotNullParameter(resultCallback, "resultCallback");
        Intrinsics.checkNotNullParameter(scanError, "scanError");
        if (!(!(peripheralNames.length == 0))) {
            throw new IllegalArgumentException("at least one peripheral name must be supplied".toString());
        }
        if (isScanning()) {
            stopScan();
        }
        this.currentResultCallback = resultCallback;
        this.currentScanErrorCallback = scanError;
        this.scanPeripheralNames = peripheralNames;
        startScan(CollectionsKt.emptyList(), this.scanSettings, this.scanByNameCallback);
    }

    public final void scanForPeripheralsWithAddresses(String[] peripheralAddresses, Function2<? super BluetoothPeripheral, ? super ScanResult, Unit> resultCallback, Function1<? super ScanFailure, Unit> scanError) {
        Intrinsics.checkNotNullParameter(peripheralAddresses, "peripheralAddresses");
        Intrinsics.checkNotNullParameter(resultCallback, "resultCallback");
        Intrinsics.checkNotNullParameter(scanError, "scanError");
        if (!(!(peripheralAddresses.length == 0))) {
            throw new IllegalArgumentException("at least one peripheral address must be supplied".toString());
        }
        if (isScanning()) {
            stopScan();
        }
        this.currentResultCallback = resultCallback;
        this.currentScanErrorCallback = scanError;
        ArrayList arrayList = new ArrayList();
        for (String str : peripheralAddresses) {
            if (BluetoothAdapter.checkBluetoothAddress(str)) {
                ScanFilter filter = new ScanFilter.Builder().setDeviceAddress(str).build();
                Intrinsics.checkNotNullExpressionValue(filter, "filter");
                arrayList.add(filter);
            } else {
                Logger.INSTANCE.e(TAG, "%s is not a valid address. Make sure all alphabetic characters are uppercase.", str);
            }
        }
        startScan(arrayList, this.scanSettings, this.defaultScanCallback);
    }

    public final void scanForPeripheralsUsingFilters(List<ScanFilter> filters, Function2<? super BluetoothPeripheral, ? super ScanResult, Unit> resultCallback, Function1<? super ScanFailure, Unit> scanError) {
        Intrinsics.checkNotNullParameter(filters, "filters");
        Intrinsics.checkNotNullParameter(resultCallback, "resultCallback");
        Intrinsics.checkNotNullParameter(scanError, "scanError");
        if (!(!filters.isEmpty())) {
            throw new IllegalArgumentException("at least one scan filter must be supplied".toString());
        }
        if (isScanning()) {
            stopScan();
        }
        this.currentResultCallback = resultCallback;
        this.currentScanErrorCallback = scanError;
        startScan(filters, this.scanSettings, this.defaultScanCallback);
    }

    public final void scanForPeripherals(Function2<? super BluetoothPeripheral, ? super ScanResult, Unit> resultCallback, Function1<? super ScanFailure, Unit> scanError) {
        Intrinsics.checkNotNullParameter(resultCallback, "resultCallback");
        Intrinsics.checkNotNullParameter(scanError, "scanError");
        if (isScanning()) {
            stopScan();
        }
        this.currentResultCallback = resultCallback;
        this.currentScanErrorCallback = scanError;
        startScan(CollectionsKt.emptyList(), this.scanSettings, this.defaultScanCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void scanForAutoConnectPeripherals() {
        if (bleNotReady()) {
            return;
        }
        if (this.autoConnectScanner != null) {
            stopAutoconnectScan();
        }
        BluetoothLeScanner bluetoothLeScanner = this.bluetoothAdapter.getBluetoothLeScanner();
        this.autoConnectScanner = bluetoothLeScanner;
        if (bluetoothLeScanner != null) {
            ArrayList arrayList = new ArrayList();
            Iterator<String> it = this.reconnectPeripheralAddresses.iterator();
            while (it.hasNext()) {
                ScanFilter filter = new ScanFilter.Builder().setDeviceAddress(it.next()).build();
                Intrinsics.checkNotNullExpressionValue(filter, "filter");
                arrayList.add(filter);
            }
            BluetoothLeScanner bluetoothLeScanner2 = this.autoConnectScanner;
            Intrinsics.checkNotNull(bluetoothLeScanner2);
            bluetoothLeScanner2.startScan(arrayList, this.autoConnectScanSettings, this.autoConnectScanCallback);
            Logger.INSTANCE.d(TAG, "started scanning to autoconnect peripherals (" + this.reconnectPeripheralAddresses.size() + ")");
            setAutoConnectTimer();
            return;
        }
        Logger.INSTANCE.e(TAG, "starting autoconnect scan failed");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void stopAutoconnectScan() {
        cancelAutoConnectTimer();
        try {
            BluetoothLeScanner bluetoothLeScanner = this.autoConnectScanner;
            if (bluetoothLeScanner != null) {
                bluetoothLeScanner.stopScan(this.autoConnectScanCallback);
            }
        } catch (Exception unused) {
        }
        this.autoConnectScanner = null;
        Logger.INSTANCE.i(TAG, "autoscan stopped");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isAutoScanning() {
        return this.autoConnectScanner != null;
    }

    public final void stopScan() {
        cancelTimeoutTimer();
        if (isScanning()) {
            try {
                BluetoothLeScanner bluetoothLeScanner = this.bluetoothScanner;
                if (bluetoothLeScanner != null) {
                    if (bluetoothLeScanner != null) {
                        bluetoothLeScanner.stopScan(this.currentCallback);
                    }
                    this.currentCallback = null;
                    this.currentFilters = null;
                    Logger.INSTANCE.i(TAG, "scan stopped");
                }
            } catch (Exception unused) {
                Logger.INSTANCE.e(TAG, "caught exception in stopScan");
            }
        } else {
            Logger.INSTANCE.d(TAG, "no scan to stop because no scan is running");
        }
        this.currentCallback = null;
        this.currentFilters = null;
        this.scannedPeripherals.clear();
    }

    public final boolean isScanning() {
        return (this.bluetoothScanner == null || this.currentCallback == null) ? false : true;
    }

    public final void observeConnectionState(Function2<? super BluetoothPeripheral, ? super ConnectionState, Unit> connectionCallback) {
        Intrinsics.checkNotNullParameter(connectionCallback, "connectionCallback");
        this.connectionStateCallback = connectionCallback;
    }

    public final Object connectPeripheral(BluetoothPeripheral bluetoothPeripheral, Continuation<? super Unit> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        try {
            connectPeripheral(bluetoothPeripheral, new BluetoothCentralManagerCallback() { // from class: com.welie.blessed.BluetoothCentralManager$connectPeripheral$2$1
                @Override // com.welie.blessed.BluetoothCentralManagerCallback
                public void onConnectedPeripheral(BluetoothPeripheral peripheral) {
                    Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                    Continuation<Unit> continuation2 = safeContinuation2;
                    Result.Companion companion = Result.INSTANCE;
                    continuation2.resumeWith(Result.m624constructorimpl(Unit.INSTANCE));
                }

                @Override // com.welie.blessed.BluetoothCentralManagerCallback
                public void onConnectionFailed(BluetoothPeripheral peripheral, HciStatus status) {
                    Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                    Intrinsics.checkNotNullParameter(status, "status");
                    Continuation<Unit> continuation2 = safeContinuation2;
                    Result.Companion companion = Result.INSTANCE;
                    continuation2.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(new ConnectionFailedException(status))));
                }
            });
        } catch (ConnectionFailedException e) {
            Result.Companion companion = Result.INSTANCE;
            safeContinuation2.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(e)));
        }
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? orThrow : Unit.INSTANCE;
    }

    private final void connectPeripheral(BluetoothPeripheral peripheral, BluetoothCentralManagerCallback resultCentralManagerCallback) {
        synchronized (this.connectLock) {
            if (bleNotReady()) {
                Logger.INSTANCE.e(TAG, "cannot connect peripheral '%s' because Bluetooth is off", peripheral.getAddress());
                return;
            }
            checkPeripheralStatus(peripheral);
            if (peripheral.isUncached()) {
                Logger.INSTANCE.w(TAG, "peripheral with address '%s' is not in the Bluetooth cache, hence connection may fail", peripheral.getAddress());
            }
            this.scannedPeripherals.remove(peripheral.getAddress());
            this.unconnectedPeripherals.put(peripheral.getAddress(), peripheral);
            this.currentCentralManagerCallback = resultCentralManagerCallback;
            peripheral.connect();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void autoConnectPeripheral(BluetoothPeripheral peripheral) {
        Intrinsics.checkNotNullParameter(peripheral, "peripheral");
        synchronized (this.connectLock) {
            if (bleNotReady()) {
                Logger.INSTANCE.e(TAG, "cannot autoConnectPeripheral '%s' because Bluetooth is off", peripheral.getAddress());
                return;
            }
            checkPeripheralStatus(peripheral);
            if (peripheral.isUncached()) {
                Logger.INSTANCE.d(TAG, "peripheral with address '%s' not in Bluetooth cache, autoconnecting by scanning", peripheral.getAddress());
                this.scannedPeripherals.remove(peripheral.getAddress());
                this.unconnectedPeripherals.put(peripheral.getAddress(), peripheral);
                autoConnectPeripheralByScan(peripheral.getAddress());
                return;
            }
            this.scannedPeripherals.remove(peripheral.getAddress());
            this.unconnectedPeripherals.put(peripheral.getAddress(), peripheral);
            peripheral.autoConnect();
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void checkPeripheralStatus(BluetoothPeripheral peripheral) {
        if (this.connectedPeripherals.containsKey(peripheral.getAddress())) {
            Logger.INSTANCE.e(TAG, "already connected to %s'", peripheral.getAddress());
            throw new ConnectionFailedException(HciStatus.CONNECTION_ALREADY_EXISTS);
        }
        if (this.unconnectedPeripherals.containsKey(peripheral.getAddress())) {
            Logger.INSTANCE.e(TAG, "already issued autoconnect for '%s' ", peripheral.getAddress());
            throw new ConnectionFailedException(HciStatus.COMMAND_DISALLOWED);
        }
        if (peripheral.getType() != PeripheralType.CLASSIC) {
            return;
        }
        Logger.INSTANCE.e(TAG, "peripheral does not support Bluetooth LE");
        throw new ConnectionFailedException(HciStatus.UNSUPPORTED_PARAMETER_VALUE);
    }

    private final void autoConnectPeripheralByScan(String peripheralAddress) {
        if (this.reconnectPeripheralAddresses.contains(peripheralAddress)) {
            Logger.INSTANCE.w(TAG, "peripheral already on list for reconnection");
        } else {
            this.reconnectPeripheralAddresses.add(peripheralAddress);
            scanForAutoConnectPeripherals();
        }
    }

    public final Object cancelConnection(BluetoothPeripheral bluetoothPeripheral, Continuation<? super Unit> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        cancelConnection(bluetoothPeripheral, new BluetoothCentralManagerCallback() { // from class: com.welie.blessed.BluetoothCentralManager$cancelConnection$2$1
            @Override // com.welie.blessed.BluetoothCentralManagerCallback
            public void onDisconnectedPeripheral(BluetoothPeripheral peripheral, HciStatus status) {
                Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                Intrinsics.checkNotNullParameter(status, "status");
                Continuation<Unit> continuation2 = safeContinuation2;
                Result.Companion companion = Result.INSTANCE;
                continuation2.resumeWith(Result.m624constructorimpl(Unit.INSTANCE));
            }
        });
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? orThrow : Unit.INSTANCE;
    }

    private final void cancelConnection(BluetoothPeripheral peripheral, BluetoothCentralManagerCallback resultCentralManagerCallback) {
        String address = peripheral.getAddress();
        if (this.reconnectPeripheralAddresses.contains(address)) {
            this.reconnectPeripheralAddresses.remove(address);
            this.unconnectedPeripherals.remove(address);
            stopAutoconnectScan();
            Logger.INSTANCE.d(TAG, "cancelling autoconnect for %s", address);
            BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new AnonymousClass3(resultCentralManagerCallback, peripheral, null), 3, null);
            if (this.reconnectPeripheralAddresses.size() > 0) {
                scanForAutoConnectPeripherals();
                return;
            }
            return;
        }
        if (this.unconnectedPeripherals.containsKey(address) || this.connectedPeripherals.containsKey(address)) {
            this.currentCentralManagerCallback = resultCentralManagerCallback;
            peripheral.cancelConnection$blessed_release();
        } else {
            Logger.INSTANCE.e(TAG, "cannot cancel connection to unknown peripheral %s", address);
            throw new IllegalArgumentException("tyring to disconnect peripheral outside of library");
        }
    }

    /* compiled from: BluetoothCentralManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothCentralManager$cancelConnection$3", f = "BluetoothCentralManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.welie.blessed.BluetoothCentralManager$cancelConnection$3, reason: invalid class name */
    static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ BluetoothPeripheral $peripheral;
        final /* synthetic */ BluetoothCentralManagerCallback $resultCentralManagerCallback;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(BluetoothCentralManagerCallback bluetoothCentralManagerCallback, BluetoothPeripheral bluetoothPeripheral, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.$resultCentralManagerCallback = bluetoothCentralManagerCallback;
            this.$peripheral = bluetoothPeripheral;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass3(this.$resultCentralManagerCallback, this.$peripheral, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$resultCentralManagerCallback.onDisconnectedPeripheral(this.$peripheral, HciStatus.SUCCESS);
            return Unit.INSTANCE;
        }
    }

    public final void autoConnectPeripheralsBatch(Set<BluetoothPeripheral> batch) {
        Intrinsics.checkNotNullParameter(batch, "batch");
        HashSet<BluetoothPeripheral> hashSet = new HashSet();
        for (BluetoothPeripheral bluetoothPeripheral : batch) {
            if (bluetoothPeripheral.isUncached()) {
                hashSet.add(bluetoothPeripheral);
            } else {
                autoConnectPeripheral(bluetoothPeripheral);
            }
        }
        if (!hashSet.isEmpty()) {
            for (BluetoothPeripheral bluetoothPeripheral2 : hashSet) {
                String address = bluetoothPeripheral2.getAddress();
                this.reconnectPeripheralAddresses.add(address);
                this.unconnectedPeripherals.put(address, bluetoothPeripheral2);
            }
            scanForAutoConnectPeripherals();
        }
    }

    public final BluetoothPeripheral getPeripheral(String peripheralAddress) {
        Intrinsics.checkNotNullParameter(peripheralAddress, "peripheralAddress");
        if (!BluetoothAdapter.checkBluetoothAddress(peripheralAddress)) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str = String.format("%s is not a valid bluetooth address. Make sure all alphabetic characters are uppercase.", Arrays.copyOf(new Object[]{peripheralAddress}, 1));
            Intrinsics.checkNotNullExpressionValue(str, "format(format, *args)");
            throw new IllegalArgumentException(str);
        }
        if (this.connectedPeripherals.containsKey(peripheralAddress)) {
            BluetoothPeripheral bluetoothPeripheral = this.connectedPeripherals.get(peripheralAddress);
            if (bluetoothPeripheral != null) {
                return bluetoothPeripheral;
            }
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        if (this.unconnectedPeripherals.containsKey(peripheralAddress)) {
            BluetoothPeripheral bluetoothPeripheral2 = this.unconnectedPeripherals.get(peripheralAddress);
            if (bluetoothPeripheral2 != null) {
                return bluetoothPeripheral2;
            }
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        if (this.scannedPeripherals.containsKey(peripheralAddress)) {
            BluetoothPeripheral bluetoothPeripheral3 = this.scannedPeripherals.get(peripheralAddress);
            if (bluetoothPeripheral3 != null) {
                return bluetoothPeripheral3;
            }
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        Context context = this.context;
        BluetoothDevice remoteDevice = this.bluetoothAdapter.getRemoteDevice(peripheralAddress);
        Intrinsics.checkNotNullExpressionValue(remoteDevice, "bluetoothAdapter.getRemo…Device(peripheralAddress)");
        BluetoothPeripheral bluetoothPeripheral4 = new BluetoothPeripheral(context, remoteDevice, this.internalCallback);
        this.scannedPeripherals.put(peripheralAddress, bluetoothPeripheral4);
        return bluetoothPeripheral4;
    }

    public final List<BluetoothPeripheral> getConnectedPeripherals() {
        return new ArrayList(this.connectedPeripherals.values());
    }

    private final boolean bleNotReady() {
        if (isBleSupported() && isBluetoothEnabled()) {
            return !permissionsGranted();
        }
        return true;
    }

    private final boolean isBleSupported() {
        if (this.context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            return true;
        }
        Logger.INSTANCE.e(TAG, "BLE not supported");
        return false;
    }

    public final boolean isBluetoothEnabled() {
        if (this.bluetoothAdapter.isEnabled()) {
            return true;
        }
        Logger.INSTANCE.e(TAG, "Bluetooth disabled");
        return false;
    }

    private final boolean permissionsGranted() {
        int i = this.context.getApplicationInfo().targetSdkVersion;
        if (Build.VERSION.SDK_INT >= 31 && i >= 31) {
            if (this.context.checkSelfPermission("android.permission.BLUETOOTH_SCAN") != 0) {
                throw new SecurityException("app does not have BLUETOOTH_SCAN permission, cannot start scan");
            }
            if (this.context.checkSelfPermission("android.permission.BLUETOOTH_CONNECT") == 0) {
                return true;
            }
            throw new SecurityException("app does not have BLUETOOTH_CONNECT permission, cannot connect");
        }
        if (Build.VERSION.SDK_INT >= 29 && i >= 29) {
            if (this.context.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
                return true;
            }
            Logger.INSTANCE.e(TAG, "no ACCESS_FINE_LOCATION permission, cannot scan");
        } else {
            if (this.context.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
                return true;
            }
            Logger.INSTANCE.e(TAG, "no ACCESS_COARSE_LOCATION permission, cannot scan");
        }
        return false;
    }

    private final void setScanTimer() {
        cancelTimeoutTimer();
        Runnable runnable = new Runnable() { // from class: com.welie.blessed.BluetoothCentralManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothCentralManager.setScanTimer$lambda$8(this.f$0);
            }
        };
        this.timeoutRunnable = runnable;
        Handler handler = this.mainHandler;
        Intrinsics.checkNotNull(runnable);
        handler.postDelayed(runnable, SCAN_TIMEOUT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setScanTimer$lambda$8(BluetoothCentralManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Logger.INSTANCE.d(TAG, "scanning timeout, restarting scan");
        ScanCallback scanCallback = this$0.currentCallback;
        List<ScanFilter> list = this$0.currentFilters;
        this$0.stopScan();
        BuildersKt__Builders_commonKt.launch$default(this$0.scope, null, null, new BluetoothCentralManager$setScanTimer$1$1(scanCallback, this$0, list, null), 3, null);
    }

    private final void cancelTimeoutTimer() {
        Runnable runnable = this.timeoutRunnable;
        if (runnable != null) {
            Handler handler = this.mainHandler;
            Intrinsics.checkNotNull(runnable);
            handler.removeCallbacks(runnable);
            this.timeoutRunnable = null;
        }
    }

    private final void setAutoConnectTimer() {
        cancelAutoConnectTimer();
        Runnable runnable = new Runnable() { // from class: com.welie.blessed.BluetoothCentralManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothCentralManager.setAutoConnectTimer$lambda$10(this.f$0);
            }
        };
        this.autoConnectRunnable = runnable;
        Handler handler = this.mainHandler;
        Intrinsics.checkNotNull(runnable);
        handler.postDelayed(runnable, SCAN_TIMEOUT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setAutoConnectTimer$lambda$10(final BluetoothCentralManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Logger.INSTANCE.d(TAG, "autoconnect scan timeout, restarting scan");
        this$0.stopAutoconnectScan();
        this$0.mainHandler.postDelayed(new Runnable() { // from class: com.welie.blessed.BluetoothCentralManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothCentralManager.setAutoConnectTimer$lambda$10$lambda$9(this.f$0);
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setAutoConnectTimer$lambda$10$lambda$9(BluetoothCentralManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.scanForAutoConnectPeripherals();
    }

    private final void cancelAutoConnectTimer() {
        Runnable runnable = this.autoConnectRunnable;
        if (runnable != null) {
            Handler handler = this.mainHandler;
            Intrinsics.checkNotNull(runnable);
            handler.removeCallbacks(runnable);
            this.autoConnectRunnable = null;
        }
    }

    public final boolean setPinCodeForPeripheral(String peripheralAddress, String pin) {
        Intrinsics.checkNotNullParameter(peripheralAddress, "peripheralAddress");
        Intrinsics.checkNotNullParameter(pin, "pin");
        if (!BluetoothAdapter.checkBluetoothAddress(peripheralAddress)) {
            Logger.INSTANCE.e(TAG, "%s is not a valid address. Make sure all alphabetic characters are uppercase.", peripheralAddress);
            return false;
        }
        if (pin.length() != 6) {
            Logger.INSTANCE.e(TAG, "%s is not 6 digits long", pin);
            return false;
        }
        this.pinCodes.put(peripheralAddress, pin);
        return true;
    }

    public final boolean removeBond(String peripheralAddress) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(peripheralAddress, "peripheralAddress");
        Set<BluetoothDevice> bondedDevices = this.bluetoothAdapter.getBondedDevices();
        if (bondedDevices.size() <= 0) {
            return true;
        }
        BluetoothDevice bluetoothDevice = null;
        for (BluetoothDevice bluetoothDevice2 : bondedDevices) {
            if (Intrinsics.areEqual(bluetoothDevice2.getAddress(), peripheralAddress)) {
                bluetoothDevice = bluetoothDevice2;
            }
        }
        if (bluetoothDevice == null) {
            return true;
        }
        try {
            Object objInvoke = bluetoothDevice.getClass().getMethod("removeBond", new Class[0]).invoke(bluetoothDevice, new Object[0]);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Boolean");
            boolean zBooleanValue = ((Boolean) objInvoke).booleanValue();
            if (zBooleanValue) {
                Logger logger = Logger.INSTANCE;
                String str = TAG;
                String name = bluetoothDevice.getName();
                Intrinsics.checkNotNullExpressionValue(name, "peripheralToUnBond.name");
                logger.i(str, "Succesfully removed bond for '%s'", name);
            }
            return zBooleanValue;
        } catch (Exception e) {
            Logger.INSTANCE.i(TAG, "could not remove bond");
            e.printStackTrace();
            return false;
        }
    }

    public final void startPairingPopupHack() {
        if (StringsKt.equals(Build.MANUFACTURER, "samsung", true)) {
            return;
        }
        this.bluetoothAdapter.startDiscovery();
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C04441(null), 3, null);
    }

    /* compiled from: BluetoothCentralManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothCentralManager$startPairingPopupHack$1", f = "BluetoothCentralManager.kt", i = {}, l = {953}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.welie.blessed.BluetoothCentralManager$startPairingPopupHack$1, reason: invalid class name and case insensitive filesystem */
    static final class C04441 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C04441(Continuation<? super C04441> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return BluetoothCentralManager.this.new C04441(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04441) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(1000L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            Logger.INSTANCE.d(BluetoothCentralManager.TAG, "popup hack completed");
            BluetoothCentralManager.this.bluetoothAdapter.cancelDiscovery();
            return Unit.INSTANCE;
        }
    }

    private final void cancelAllConnectionsWhenBluetoothOff() {
        Logger.INSTANCE.d(TAG, "disconnect all peripherals because bluetooth is off");
        Iterator<BluetoothPeripheral> it = this.connectedPeripherals.values().iterator();
        while (it.hasNext()) {
            it.next().disconnectWhenBluetoothOff();
        }
        this.connectedPeripherals.clear();
        Iterator<BluetoothPeripheral> it2 = this.unconnectedPeripherals.values().iterator();
        while (it2.hasNext()) {
            it2.next().disconnectWhenBluetoothOff();
        }
        this.unconnectedPeripherals.clear();
        this.reconnectPeripheralAddresses.clear();
    }

    public final void observeAdapterState(Function1<? super Integer, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.adapterStateCallback = callback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleAdapterState(int state) {
        switch (state) {
            case 10:
                if ((!this.connectedPeripherals.isEmpty()) || (!this.unconnectedPeripherals.isEmpty())) {
                    cancelAllConnectionsWhenBluetoothOff();
                }
                Logger.INSTANCE.d(TAG, "bluetooth turned off");
                break;
            case 11:
                Logger.INSTANCE.d(TAG, "bluetooth turning on");
                break;
            case 12:
                BluetoothLeScanner bluetoothLeScanner = this.bluetoothAdapter.getBluetoothLeScanner();
                this.bluetoothScanner = bluetoothLeScanner;
                if (bluetoothLeScanner != null) {
                    try {
                        bluetoothLeScanner.stopScan(this.defaultScanCallback);
                    } catch (Exception unused) {
                    }
                }
                Logger.INSTANCE.d(TAG, "bluetooth turned on");
                break;
            case 13:
                Iterator<Map.Entry<String, BluetoothPeripheral>> it = this.connectedPeripherals.entrySet().iterator();
                while (it.hasNext()) {
                    it.next().getValue().cancelConnection$blessed_release();
                }
                Iterator<Map.Entry<String, BluetoothPeripheral>> it2 = this.unconnectedPeripherals.entrySet().iterator();
                while (it2.hasNext()) {
                    it2.next().getValue().cancelConnection$blessed_release();
                }
                this.reconnectPeripheralAddresses.clear();
                if (isScanning()) {
                    stopScan();
                }
                if (isAutoScanning()) {
                    stopAutoconnectScan();
                }
                cancelTimeoutTimer();
                cancelAutoConnectTimer();
                this.currentCallback = null;
                this.currentFilters = null;
                this.autoConnectScanner = null;
                this.bluetoothScanner = null;
                Logger.INSTANCE.d(TAG, "bluetooth turning off");
                break;
        }
    }

    public final void disableLogging() {
        Logger.INSTANCE.setEnabled(false);
    }

    public final void enableLogging() {
        Logger.INSTANCE.setEnabled(false);
    }
}
