package com.welie.blessed;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.messaging.Constants;
import com.thor.app.databinding.viewmodels.workers.BleCommandsWorker;
import com.welie.blessed.BluetoothPeripheralCallback;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;

/* compiled from: BluetoothPeripheral.kt */
@Metadata(d1 = {"\u0000\u0086\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u0007\u0018\u0000 °\u00012\u00020\u0001:\u0004°\u0001±\u0001B\u001f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0006\u0010T\u001a\u00020\u001cJ\r\u0010U\u001a\u00020\u001cH\u0000¢\u0006\u0002\bVJ\b\u0010W\u001a\u00020\u001cH\u0002J\b\u0010X\u001a\u00020\u001cH\u0002J\u0011\u0010Y\u001a\u00020\u0012H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010ZJ\u0018\u0010[\u001a\u00020\u001c2\u0006\u0010\\\u001a\u00020\u00122\u0006\u0010]\u001a\u00020^H\u0002J\b\u0010_\u001a\u00020\u001cH\u0002J\u0006\u0010`\u001a\u00020\u001cJ \u0010a\u001a\u00020\u001c2\u0006\u0010]\u001a\u00020^2\u0006\u0010b\u001a\u00020-2\u0006\u0010c\u001a\u00020-H\u0002J\u0012\u0010d\u001a\u0002052\b\u0010e\u001a\u0004\u0018\u000105H\u0002J\u0006\u0010f\u001a\u00020\u0012J\b\u0010g\u001a\u00020\u001cH\u0002J\u0006\u0010h\u001a\u00020\u001cJ\b\u0010i\u001a\u00020\u001cH\u0003J\u0010\u0010j\u001a\u00020\u00122\u0006\u0010k\u001a\u00020(H\u0002J\u0018\u0010l\u001a\u0004\u0018\u00010A2\u0006\u0010m\u001a\u00020n2\u0006\u0010o\u001a\u00020nJ\u000e\u0010p\u001a\u00020-2\u0006\u0010q\u001a\u00020rJ\f\u0010s\u001a\b\u0012\u0004\u0012\u00020A0tJ\u0010\u0010u\u001a\u0004\u0018\u00010J2\u0006\u0010m\u001a\u00020nJ\u0006\u0010v\u001a\u00020wJ\u0018\u0010x\u001a\u00020\u001c2\u0006\u0010\u0013\u001a\u00020-2\u0006\u0010y\u001a\u00020-H\u0002J \u0010z\u001a\u00020\u00122\u0006\u0010{\u001a\u00020A2\u0006\u0010E\u001a\u0002052\u0006\u0010q\u001a\u00020rH\u0002J\u0018\u0010|\u001a\u00020\u00122\u0006\u0010}\u001a\u00020~2\u0006\u0010E\u001a\u000205H\u0002J\u000e\u0010\u007f\u001a\u00020\u00122\u0006\u0010{\u001a\u00020AJ\t\u0010\u0080\u0001\u001a\u00020\u001cH\u0002J\u0011\u0010\u0081\u0001\u001a\u0002052\b\u0010e\u001a\u0004\u0018\u000105J\t\u0010\u0082\u0001\u001a\u00020\u0012H\u0002J?\u0010\u0083\u0001\u001a\u00020\u00122\u0006\u0010{\u001a\u00020A2\"\u0010\u0084\u0001\u001a\u001d\u0012\u0013\u0012\u001105¢\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(E\u0012\u0004\u0012\u00020\u001c0\u0018H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010\u0085\u0001J+\u0010\u0086\u0001\u001a\u00020\u001c2\"\u0010\u0084\u0001\u001a\u001d\u0012\u0013\u0012\u00110\u0014¢\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u00020\u001c0\u0018J\u0012\u0010\u0087\u0001\u001a\u00020\n2\u0007\u0010\u0088\u0001\u001a\u00020-H\u0002J\u001b\u0010\u0089\u0001\u001a\u0002052\u0006\u0010{\u001a\u00020AH\u0086@ø\u0001\u0000¢\u0006\u0003\u0010\u008a\u0001J\u001a\u0010\u0089\u0001\u001a\u00020\u00122\u0006\u0010{\u001a\u00020A2\u0007\u0010\u008b\u0001\u001a\u000203H\u0002J#\u0010\u0089\u0001\u001a\u0002052\u0006\u0010m\u001a\u00020n2\u0006\u0010o\u001a\u00020nH\u0086@ø\u0001\u0000¢\u0006\u0003\u0010\u008c\u0001J\u001b\u0010\u008d\u0001\u001a\u0002052\u0006\u0010}\u001a\u00020~H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010\u008e\u0001J\u001a\u0010\u008d\u0001\u001a\u00020\u00122\u0006\u0010}\u001a\u00020~2\u0007\u0010\u008b\u0001\u001a\u000203H\u0002J\u0013\u0010\u008f\u0001\u001a\u00030\u0090\u0001H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010ZJ\u0012\u0010\u008f\u0001\u001a\u00020\u00122\u0007\u0010\u008b\u0001\u001a\u000203H\u0002J\u0012\u0010\u0091\u0001\u001a\u00020-H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010ZJ\u0012\u0010\u0091\u0001\u001a\u00020\u00122\u0007\u0010\u008b\u0001\u001a\u000203H\u0002J\t\u0010\u0092\u0001\u001a\u00020\u001cH\u0002J\u001d\u0010\u0093\u0001\u001a\u00020\u00122\b\u0010\u0094\u0001\u001a\u00030\u0095\u0001H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010\u0096\u0001J\u001c\u0010\u0093\u0001\u001a\u00020\u00122\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\u0007\u0010\u008b\u0001\u001a\u000203H\u0002J\u001c\u0010\u0097\u0001\u001a\u00020-2\u0007\u0010\u0098\u0001\u001a\u00020-H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010\u0099\u0001J\u001b\u0010\u0097\u0001\u001a\u00020\u00122\u0007\u0010\u0098\u0001\u001a\u00020-2\u0007\u0010\u008b\u0001\u001a\u000203H\u0002J\u0010\u0010\u009a\u0001\u001a\u00020\u001c2\u0007\u0010\u009b\u0001\u001a\u00020\u0005J#\u0010\u009c\u0001\u001a\u00020\u00122\u0006\u0010{\u001a\u00020A2\u0007\u0010\u009d\u0001\u001a\u00020\u00122\u0007\u0010\u008b\u0001\u001a\u000203H\u0002J+\u0010\u009c\u0001\u001a\u00020\u00122\u0006\u0010m\u001a\u00020n2\u0006\u0010o\u001a\u00020n2\u0007\u0010\u009d\u0001\u001a\u00020\u00122\u0007\u0010\u008b\u0001\u001a\u000203H\u0002J2\u0010\u009e\u0001\u001a\u00030\u0090\u00012\b\u0010\u009f\u0001\u001a\u00030 \u00012\b\u0010¡\u0001\u001a\u00030 \u00012\b\u0010¢\u0001\u001a\u00030£\u0001H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010¤\u0001J0\u0010\u009e\u0001\u001a\u00020\u00122\b\u0010\u009f\u0001\u001a\u00030 \u00012\b\u0010¡\u0001\u001a\u00030 \u00012\b\u0010¢\u0001\u001a\u00030£\u00012\u0007\u0010\u008b\u0001\u001a\u000203H\u0002J\u0012\u0010¥\u0001\u001a\u00020\u001c2\u0007\u0010¦\u0001\u001a\u00020\u0000H\u0002J\u001b\u0010§\u0001\u001a\u00020\u00122\u0006\u0010{\u001a\u00020AH\u0086@ø\u0001\u0000¢\u0006\u0003\u0010\u008a\u0001J\t\u0010¨\u0001\u001a\u00020\u001cH\u0002J\u0011\u0010©\u0001\u001a\u00020\u001c2\u0006\u0010b\u001a\u00020-H\u0002J\u0019\u0010ª\u0001\u001a\u00020\u00122\u0006\u0010E\u001a\u0002052\u0006\u0010q\u001a\u00020rH\u0002J+\u0010«\u0001\u001a\u0002052\u0006\u0010{\u001a\u00020A2\u0006\u0010E\u001a\u0002052\u0006\u0010q\u001a\u00020rH\u0086@ø\u0001\u0000¢\u0006\u0003\u0010¬\u0001J*\u0010«\u0001\u001a\u00020\u00122\u0006\u0010{\u001a\u00020A2\u0006\u0010E\u001a\u0002052\u0006\u0010q\u001a\u00020r2\u0007\u0010\u008b\u0001\u001a\u000203H\u0002J3\u0010«\u0001\u001a\u0002052\u0006\u0010m\u001a\u00020n2\u0006\u0010o\u001a\u00020n2\u0006\u0010E\u001a\u0002052\u0006\u0010q\u001a\u00020rH\u0086@ø\u0001\u0000¢\u0006\u0003\u0010\u00ad\u0001J#\u0010®\u0001\u001a\u0002052\u0006\u0010}\u001a\u00020~2\u0006\u0010E\u001a\u000205H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010¯\u0001J\"\u0010®\u0001\u001a\u00020\u00122\u0006\u0010}\u001a\u00020~2\u0006\u0010E\u001a\u0002052\u0007\u0010\u008b\u0001\u001a\u000203H\u0002R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R5\u0010\u0017\u001a\u001d\u0012\u0013\u0012\u00110\u0014¢\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u00020\u001c0\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010/\u001a\u00020-2\u0006\u0010.\u001a\u00020-@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u000e\u00102\u001a\u000203X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u000205X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00106\u001a\u0004\u0018\u000107X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u00109\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b9\u0010:R\u000e\u0010;\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010<\u001a\u00020\u00128F¢\u0006\u0006\u001a\u0004\b<\u0010:R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u001a\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b>\u0010\fR\u0014\u0010?\u001a\b\u0012\u0004\u0012\u00020A0@X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020-X\u0082\u000e¢\u0006\u0002\n\u0000R5\u0010C\u001a)\u0012\u0004\u0012\u00020A\u0012\u001f\u0012\u001d\u0012\u0013\u0012\u001105¢\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(E\u0012\u0004\u0012\u00020\u001c0\u00180DX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010H\u001a\b\u0012\u0004\u0012\u00020J0I8F¢\u0006\u0006\u001a\u0004\bK\u0010LR\u000e\u0010\u001b\u001a\u00020-X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010M\u001a\u0004\u0018\u000107X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010N\u001a\u00020-8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bO\u00101R\u0011\u0010P\u001a\u00020Q8F¢\u0006\u0006\u001a\u0004\bR\u0010S\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006²\u0001"}, d2 = {"Lcom/welie/blessed/BluetoothPeripheral;", "", "context", "Landroid/content/Context;", "device", "Landroid/bluetooth/BluetoothDevice;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/welie/blessed/BluetoothPeripheral$InternalCallback;", "(Landroid/content/Context;Landroid/bluetooth/BluetoothDevice;Lcom/welie/blessed/BluetoothPeripheral$InternalCallback;)V", "address", "", "getAddress", "()Ljava/lang/String;", "bluetoothGatt", "Landroid/bluetooth/BluetoothGatt;", "bluetoothGattCallback", "Landroid/bluetooth/BluetoothGattCallback;", "bondLost", "", "bondState", "Lcom/welie/blessed/BondState;", "getBondState", "()Lcom/welie/blessed/BondState;", "bondStateCallback", "Lkotlin/Function1;", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "state", "", "getBondStateCallback", "()Lkotlin/jvm/functions/Function1;", "setBondStateCallback", "(Lkotlin/jvm/functions/Function1;)V", "bondStateReceiver", "Landroid/content/BroadcastReceiver;", "cachedName", "callbackScope", "Lkotlinx/coroutines/CoroutineScope;", "commandQueue", "Ljava/util/Queue;", "Ljava/lang/Runnable;", "commandQueueBusy", "connectTimestamp", "", "currentCommand", "", "<set-?>", "currentMtu", "getCurrentMtu", "()I", "currentResultCallback", "Lcom/welie/blessed/BluetoothPeripheralCallback;", "currentWriteBytes", "", "discoverJob", "Lkotlinx/coroutines/Job;", "discoveryStarted", "isConnected", "()Z", "isRetrying", "isUncached", "manuallyBonding", "getName", "notifyingCharacteristics", "", "Landroid/bluetooth/BluetoothGattCharacteristic;", "nrTries", "observeMap", "", "value", "pairingRequestBroadcastReceiver", "scope", "services", "", "Landroid/bluetooth/BluetoothGattService;", "getServices", "()Ljava/util/List;", "timeoutJob", "timeoutThreshold", "getTimeoutThreshold", SessionDescription.ATTR_TYPE, "Lcom/welie/blessed/PeripheralType;", "getType", "()Lcom/welie/blessed/PeripheralType;", "autoConnect", "cancelConnection", "cancelConnection$blessed_release", "cancelConnectionTimer", "cancelPendingServiceDiscovery", "clearServicesCache", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "completeDisconnect", "notify", NotificationCompat.CATEGORY_STATUS, "Lcom/welie/blessed/HciStatus;", "completedCommand", "connect", "connectionStateChangeUnsuccessful", "previousState", "newState", "copyOf", "source", "createBond", "disconnect", "disconnectWhenBluetoothOff", "discoverServices", "enqueue", BleCommandsWorker.INPUT_DATA_COMMAND, "getCharacteristic", "serviceUUID", "Ljava/util/UUID;", "characteristicUUID", "getMaximumWriteValueLength", "writeType", "Lcom/welie/blessed/WriteType;", "getNotifyingCharacteristics", "", "getService", "getState", "Lcom/welie/blessed/ConnectionState;", "handleBondStateChange", "previousBondState", "internalWriteCharacteristic", "characteristic", "internalWriteDescriptor", "descriptor", "Landroid/bluetooth/BluetoothGattDescriptor;", "isNotifying", "nextCommand", "nonnullOf", "notConnected", "observe", "callback", "(Landroid/bluetooth/BluetoothGattCharacteristic;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeBondState", "pairingVariantToString", "variant", "readCharacteristic", "(Landroid/bluetooth/BluetoothGattCharacteristic;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resultCallback", "(Ljava/util/UUID;Ljava/util/UUID;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readDescriptor", "(Landroid/bluetooth/BluetoothGattDescriptor;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readPhy", "Lcom/welie/blessed/Phy;", "readRemoteRssi", "registerBondingBroadcastReceivers", "requestConnectionPriority", Constants.FirelogAnalytics.PARAM_PRIORITY, "Lcom/welie/blessed/ConnectionPriority;", "(Lcom/welie/blessed/ConnectionPriority;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "requestMtu", "mtu", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setDevice", "bluetoothDevice", "setNotify", "enable", "setPreferredPhy", "txPhy", "Lcom/welie/blessed/PhyType;", "rxPhy", "phyOptions", "Lcom/welie/blessed/PhyOptions;", "(Lcom/welie/blessed/PhyType;Lcom/welie/blessed/PhyType;Lcom/welie/blessed/PhyOptions;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startConnectionTimer", "peripheral", "stopObserving", "successfullyConnected", "successfullyDisconnected", "willCauseLongWrite", "writeCharacteristic", "(Landroid/bluetooth/BluetoothGattCharacteristic;[BLcom/welie/blessed/WriteType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Ljava/util/UUID;Ljava/util/UUID;[BLcom/welie/blessed/WriteType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "writeDescriptor", "(Landroid/bluetooth/BluetoothGattDescriptor;[BLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "InternalCallback", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BluetoothPeripheral {
    private static final long AVG_REQUEST_CONNECTION_PRIORITY_DURATION = 500;
    private static final long CONNECTION_TIMEOUT_IN_MS = 35000;
    private static final int DEFAULT_MTU = 23;
    private static final long DELAY_AFTER_BOND_LOST = 1000;
    private static final long DIRECT_CONNECTION_DELAY_IN_MS = 100;
    private static final int IDLE = 0;
    public static final int MAX_MTU = 517;
    private static final int MAX_TRIES = 2;
    private static final int PAIRING_VARIANT_CONSENT = 3;
    private static final int PAIRING_VARIANT_DISPLAY_PASSKEY = 4;
    private static final int PAIRING_VARIANT_DISPLAY_PIN = 5;
    private static final int PAIRING_VARIANT_OOB_CONSENT = 6;
    private static final int PAIRING_VARIANT_PASSKEY = 1;
    private static final int PAIRING_VARIANT_PASSKEY_CONFIRMATION = 2;
    private static final int PAIRING_VARIANT_PIN = 0;
    private static final String PERIPHERAL_NOT_CONNECTED = "peripheral not connected";
    private static final int REQUEST_MTU_COMMAND = 1;
    private static final int SET_PHY_TYPE_COMMAND = 2;
    private static final int TIMEOUT_THRESHOLD_DEFAULT = 25000;
    private static final int TIMEOUT_THRESHOLD_SAMSUNG = 4500;
    private static final String VALUE_BYTE_ARRAY_IS_EMPTY = "value byte array is empty";
    private static final String VALUE_BYTE_ARRAY_IS_TOO_LONG = "value byte array is too long";
    private volatile BluetoothGatt bluetoothGatt;
    private final BluetoothGattCallback bluetoothGattCallback;
    private boolean bondLost;
    private Function1<? super BondState, Unit> bondStateCallback;
    private final BroadcastReceiver bondStateReceiver;
    private String cachedName;
    private final CoroutineScope callbackScope;
    private final Queue<Runnable> commandQueue;
    private volatile boolean commandQueueBusy;
    private long connectTimestamp;
    private final Context context;
    private int currentCommand;
    private int currentMtu;
    private BluetoothPeripheralCallback currentResultCallback;
    private byte[] currentWriteBytes;
    private BluetoothDevice device;
    private Job discoverJob;
    private boolean discoveryStarted;
    private boolean isRetrying;
    private final InternalCallback listener;
    private boolean manuallyBonding;
    private final Set<BluetoothGattCharacteristic> notifyingCharacteristics;
    private int nrTries;
    private Map<BluetoothGattCharacteristic, Function1<byte[], Unit>> observeMap;
    private final BroadcastReceiver pairingRequestBroadcastReceiver;
    private final CoroutineScope scope;
    private volatile int state;
    private Job timeoutJob;
    private static final String TAG = String.valueOf(Reflection.getOrCreateKotlinClass(BluetoothPeripheral.class).getSimpleName());
    private static final UUID CCC_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    /* compiled from: BluetoothPeripheral.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u000e"}, d2 = {"Lcom/welie/blessed/BluetoothPeripheral$InternalCallback;", "", "connectFailed", "", "peripheral", "Lcom/welie/blessed/BluetoothPeripheral;", NotificationCompat.CATEGORY_STATUS, "Lcom/welie/blessed/HciStatus;", "connected", "connecting", "disconnected", "disconnecting", "getPincode", "", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface InternalCallback {
        void connectFailed(BluetoothPeripheral peripheral, HciStatus status);

        void connected(BluetoothPeripheral peripheral);

        void connecting(BluetoothPeripheral peripheral);

        void disconnected(BluetoothPeripheral peripheral, HciStatus status);

        void disconnecting(BluetoothPeripheral peripheral);

        String getPincode(BluetoothPeripheral peripheral);
    }

    /* compiled from: BluetoothPeripheral.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WriteType.values().length];
            try {
                iArr[WriteType.WITH_RESPONSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WriteType.SIGNED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* compiled from: BluetoothPeripheral.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral", f = "BluetoothPeripheral.kt", i = {0}, l = {1473}, m = "clearServicesCache", n = {"result"}, s = {"I$0"})
    /* renamed from: com.welie.blessed.BluetoothPeripheral$clearServicesCache$1, reason: invalid class name and case insensitive filesystem */
    static final class C04451 extends ContinuationImpl {
        int I$0;
        int label;
        /* synthetic */ Object result;

        C04451(Continuation<? super C04451> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BluetoothPeripheral.this.clearServicesCache(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String pairingVariantToString(int variant) {
        switch (variant) {
            case 0:
                return "PAIRING_VARIANT_PIN";
            case 1:
                return "PAIRING_VARIANT_PASSKEY";
            case 2:
                return "PAIRING_VARIANT_PASSKEY_CONFIRMATION";
            case 3:
                return "PAIRING_VARIANT_CONSENT";
            case 4:
                return "PAIRING_VARIANT_DISPLAY_PASSKEY";
            case 5:
                return "PAIRING_VARIANT_DISPLAY_PIN";
            case 6:
                return "PAIRING_VARIANT_OOB_CONSENT";
            default:
                return "UNKNOWN";
        }
    }

    public final byte[] nonnullOf(byte[] source) {
        return source == null ? new byte[0] : source;
    }

    public BluetoothPeripheral(Context context, BluetoothDevice device, InternalCallback listener) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(device, "device");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.context = context;
        this.device = device;
        this.listener = listener;
        this.commandQueue = new ConcurrentLinkedQueue();
        this.cachedName = "";
        this.currentWriteBytes = new byte[0];
        this.currentResultCallback = new BluetoothPeripheralCallback.NULL();
        this.notifyingCharacteristics = new HashSet();
        this.observeMap = new HashMap();
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
        Intrinsics.checkNotNullExpressionValue(executorServiceNewSingleThreadExecutor, "newSingleThreadExecutor()");
        this.callbackScope = CoroutineScopeKt.CoroutineScope(ExecutorsKt.from(executorServiceNewSingleThreadExecutor));
        this.scope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)));
        this.currentMtu = 23;
        this.bluetoothGattCallback = new BluetoothGattCallback() { // from class: com.welie.blessed.BluetoothPeripheral$bluetoothGattCallback$1
            @Override // android.bluetooth.BluetoothGattCallback
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                if (newState != 1) {
                    this.this$0.cancelConnectionTimer();
                }
                int i = this.this$0.state;
                this.this$0.state = newState;
                HciStatus hciStatusFromValue = HciStatus.INSTANCE.fromValue(status);
                if (hciStatusFromValue != HciStatus.SUCCESS) {
                    this.this$0.connectionStateChangeUnsuccessful(hciStatusFromValue, i, newState);
                    return;
                }
                if (newState == 0) {
                    this.this$0.successfullyDisconnected(i);
                    return;
                }
                if (newState == 1) {
                    Logger.INSTANCE.d(BluetoothPeripheral.TAG, "peripheral is connecting");
                    this.this$0.listener.connecting(this.this$0);
                } else if (newState == 2) {
                    this.this$0.successfullyConnected();
                } else if (newState != 3) {
                    Logger.INSTANCE.e(BluetoothPeripheral.TAG, "unknown state received");
                } else {
                    Logger.INSTANCE.d(BluetoothPeripheral.TAG, "peripheral is disconnecting");
                    this.this$0.listener.disconnecting(this.this$0);
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                GattStatus gattStatusFromValue = GattStatus.INSTANCE.fromValue(status);
                if (gattStatusFromValue != GattStatus.SUCCESS) {
                    Logger.INSTANCE.e(BluetoothPeripheral.TAG, "service discovery failed due to internal error '%s', disconnecting", gattStatusFromValue);
                    this.this$0.disconnect();
                } else {
                    Logger.INSTANCE.d(BluetoothPeripheral.TAG, "discovered %d services for '%s'", Integer.valueOf(gatt.getServices().size()), this.this$0.getName());
                    this.this$0.listener.connected(this.this$0);
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                GattStatus gattStatusFromValue = GattStatus.INSTANCE.fromValue(status);
                BluetoothGattCharacteristic parentCharacteristic = descriptor.getCharacteristic();
                BluetoothPeripheralCallback bluetoothPeripheralCallback = this.this$0.currentResultCallback;
                if (gattStatusFromValue != GattStatus.SUCCESS) {
                    Logger logger = Logger.INSTANCE;
                    String str = BluetoothPeripheral.TAG;
                    UUID uuid = parentCharacteristic.getUuid();
                    Intrinsics.checkNotNullExpressionValue(uuid, "parentCharacteristic.uuid");
                    logger.e(str, "failed to write <%s> to descriptor of characteristic <%s> for device: '%s', status '%s' ", BluetoothBytesParser.INSTANCE.bytes2String(this.this$0.currentWriteBytes), uuid, this.this$0.getAddress(), gattStatusFromValue);
                }
                byte[] bArr = this.this$0.currentWriteBytes;
                this.this$0.currentWriteBytes = new byte[0];
                if (!Intrinsics.areEqual(descriptor.getUuid(), BluetoothPeripheral.CCC_DESCRIPTOR_UUID)) {
                    BuildersKt__Builders_commonKt.launch$default(this.this$0.callbackScope, null, null, new BluetoothPeripheral$bluetoothGattCallback$1$onDescriptorWrite$2(bluetoothPeripheralCallback, this.this$0, bArr, descriptor, gattStatusFromValue, null), 3, null);
                } else {
                    if (gattStatusFromValue == GattStatus.SUCCESS) {
                        if (Arrays.equals(bArr, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE) || Arrays.equals(bArr, BluetoothGattDescriptor.ENABLE_INDICATION_VALUE)) {
                            Set set = this.this$0.notifyingCharacteristics;
                            Intrinsics.checkNotNullExpressionValue(parentCharacteristic, "parentCharacteristic");
                            set.add(parentCharacteristic);
                        } else if (Arrays.equals(bArr, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)) {
                            this.this$0.notifyingCharacteristics.remove(parentCharacteristic);
                        }
                    }
                    BuildersKt__Builders_commonKt.launch$default(this.this$0.callbackScope, null, null, new BluetoothPeripheral$bluetoothGattCallback$1$onDescriptorWrite$1(bluetoothPeripheralCallback, this.this$0, parentCharacteristic, gattStatusFromValue, null), 3, null);
                }
                this.this$0.completedCommand();
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status, byte[] value) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                Intrinsics.checkNotNullParameter(value, "value");
                GattStatus gattStatusFromValue = GattStatus.INSTANCE.fromValue(status);
                if (gattStatusFromValue != GattStatus.SUCCESS) {
                    Logger logger = Logger.INSTANCE;
                    String str = BluetoothPeripheral.TAG;
                    UUID uuid = descriptor.getUuid();
                    Intrinsics.checkNotNullExpressionValue(uuid, "descriptor.uuid");
                    logger.e(str, "reading descriptor <%s> failed for device '%s, status '%s'", uuid, this.this$0.getAddress(), gattStatusFromValue);
                }
                BuildersKt__Builders_commonKt.launch$default(this.this$0.callbackScope, null, null, new BluetoothPeripheral$bluetoothGattCallback$1$onDescriptorRead$1(this.this$0.currentResultCallback, this.this$0, value, descriptor, gattStatusFromValue, null), 3, null);
                this.this$0.completedCommand();
            }

            @Override // android.bluetooth.BluetoothGattCallback
            @Deprecated(message = "Deprecated in Java")
            public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                if (Build.VERSION.SDK_INT < 33) {
                    onDescriptorRead(gatt, descriptor, status, this.this$0.nonnullOf(descriptor.getValue()));
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, byte[] value) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                Intrinsics.checkNotNullParameter(characteristic, "characteristic");
                Intrinsics.checkNotNullParameter(value, "value");
                BuildersKt__Builders_commonKt.launch$default(this.this$0.callbackScope, null, null, new BluetoothPeripheral$bluetoothGattCallback$1$onCharacteristicChanged$1(this.this$0, characteristic, value, null), 3, null);
            }

            @Override // android.bluetooth.BluetoothGattCallback
            @Deprecated(message = "Deprecated in Java")
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                Intrinsics.checkNotNullParameter(characteristic, "characteristic");
                if (Build.VERSION.SDK_INT < 33) {
                    onCharacteristicChanged(gatt, characteristic, this.this$0.nonnullOf(characteristic.getValue()));
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, byte[] value, int status) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                Intrinsics.checkNotNullParameter(characteristic, "characteristic");
                Intrinsics.checkNotNullParameter(value, "value");
                GattStatus gattStatusFromValue = GattStatus.INSTANCE.fromValue(status);
                if (gattStatusFromValue != GattStatus.SUCCESS) {
                    Logger logger = Logger.INSTANCE;
                    String str = BluetoothPeripheral.TAG;
                    UUID uuid = characteristic.getUuid();
                    Intrinsics.checkNotNullExpressionValue(uuid, "characteristic.uuid");
                    logger.e(str, "read failed for characteristic <%s>, status '%s'", uuid, gattStatusFromValue);
                }
                BuildersKt__Builders_commonKt.launch$default(this.this$0.callbackScope, null, null, new BluetoothPeripheral$bluetoothGattCallback$1$onCharacteristicRead$1(this.this$0.currentResultCallback, this.this$0, value, characteristic, gattStatusFromValue, null), 3, null);
                this.this$0.completedCommand();
            }

            @Override // android.bluetooth.BluetoothGattCallback
            @Deprecated(message = "Deprecated in Java")
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                Intrinsics.checkNotNullParameter(characteristic, "characteristic");
                if (Build.VERSION.SDK_INT < 33) {
                    onCharacteristicRead(gatt, characteristic, this.this$0.nonnullOf(characteristic.getValue()), status);
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                Intrinsics.checkNotNullParameter(characteristic, "characteristic");
                GattStatus gattStatusFromValue = GattStatus.INSTANCE.fromValue(status);
                if (gattStatusFromValue != GattStatus.SUCCESS) {
                    Logger logger = Logger.INSTANCE;
                    String str = BluetoothPeripheral.TAG;
                    UUID uuid = characteristic.getUuid();
                    Intrinsics.checkNotNullExpressionValue(uuid, "characteristic.uuid");
                    logger.e(str, "writing <%s> to characteristic <%s> failed, status '%s'", BluetoothBytesParser.INSTANCE.bytes2String(this.this$0.currentWriteBytes), uuid, gattStatusFromValue);
                }
                byte[] bArr = this.this$0.currentWriteBytes;
                this.this$0.currentWriteBytes = new byte[0];
                BuildersKt__Builders_commonKt.launch$default(this.this$0.callbackScope, null, null, new BluetoothPeripheral$bluetoothGattCallback$1$onCharacteristicWrite$1(this.this$0.currentResultCallback, this.this$0, bArr, characteristic, gattStatusFromValue, null), 3, null);
                this.this$0.completedCommand();
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                GattStatus gattStatusFromValue = GattStatus.INSTANCE.fromValue(status);
                if (gattStatusFromValue != GattStatus.SUCCESS) {
                    Logger.INSTANCE.e(BluetoothPeripheral.TAG, "reading RSSI failed, status '%s'", gattStatusFromValue);
                }
                BuildersKt__Builders_commonKt.launch$default(this.this$0.callbackScope, null, null, new BluetoothPeripheral$bluetoothGattCallback$1$onReadRemoteRssi$1(this.this$0.currentResultCallback, this.this$0, rssi, gattStatusFromValue, null), 3, null);
                this.this$0.completedCommand();
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                GattStatus gattStatusFromValue = GattStatus.INSTANCE.fromValue(status);
                if (gattStatusFromValue != GattStatus.SUCCESS) {
                    Logger.INSTANCE.e(BluetoothPeripheral.TAG, "change MTU failed, status '%s'", gattStatusFromValue);
                }
                this.this$0.currentMtu = mtu;
                BuildersKt__Builders_commonKt.launch$default(this.this$0.callbackScope, null, null, new BluetoothPeripheral$bluetoothGattCallback$1$onMtuChanged$1(this.this$0.currentResultCallback, this.this$0, mtu, gattStatusFromValue, null), 3, null);
                if (this.this$0.currentCommand == 1) {
                    this.this$0.currentCommand = 0;
                    this.this$0.completedCommand();
                }
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                GattStatus gattStatusFromValue = GattStatus.INSTANCE.fromValue(status);
                if (gattStatusFromValue != GattStatus.SUCCESS) {
                    Logger.INSTANCE.e(BluetoothPeripheral.TAG, "read Phy failed, status '%s'", gattStatusFromValue);
                } else {
                    Logger.INSTANCE.d(BluetoothPeripheral.TAG, "updated Phy: tx = %s, rx = %s", PhyType.INSTANCE.fromValue(txPhy), PhyType.INSTANCE.fromValue(rxPhy));
                }
                BuildersKt__Builders_commonKt.launch$default(this.this$0.callbackScope, null, null, new BluetoothPeripheral$bluetoothGattCallback$1$onPhyRead$1(this.this$0.currentResultCallback, this.this$0, txPhy, rxPhy, gattStatusFromValue, null), 3, null);
                this.this$0.completedCommand();
            }

            @Override // android.bluetooth.BluetoothGattCallback
            public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                GattStatus gattStatusFromValue = GattStatus.INSTANCE.fromValue(status);
                if (gattStatusFromValue != GattStatus.SUCCESS) {
                    Logger.INSTANCE.e(BluetoothPeripheral.TAG, "update Phy failed, status '%s'", gattStatusFromValue);
                } else {
                    Logger.INSTANCE.d(BluetoothPeripheral.TAG, "updated Phy: tx = %s, rx = %s", PhyType.INSTANCE.fromValue(txPhy), PhyType.INSTANCE.fromValue(rxPhy));
                }
                BuildersKt__Builders_commonKt.launch$default(this.this$0.callbackScope, null, null, new BluetoothPeripheral$bluetoothGattCallback$1$onPhyUpdate$1(this.this$0.currentResultCallback, this.this$0, txPhy, rxPhy, gattStatusFromValue, null), 3, null);
                if (this.this$0.currentCommand == 2) {
                    this.this$0.currentCommand = 0;
                    this.this$0.completedCommand();
                }
            }

            public final void onConnectionUpdated(BluetoothGatt gatt, int interval, int latency, int timeout, int status) {
                Intrinsics.checkNotNullParameter(gatt, "gatt");
                GattStatus gattStatusFromValue = GattStatus.INSTANCE.fromValue(status);
                if (gattStatusFromValue != GattStatus.SUCCESS) {
                    Logger.INSTANCE.e(BluetoothPeripheral.TAG, "connection parameters update failed with status '%s'", gattStatusFromValue);
                    return;
                }
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String str = String.format(Locale.ENGLISH, "connection parameters: interval=%.1fms latency=%d timeout=%ds", Arrays.copyOf(new Object[]{Float.valueOf(interval * 1.25f), Integer.valueOf(latency), Integer.valueOf(timeout / 100)}, 3));
                Intrinsics.checkNotNullExpressionValue(str, "format(locale, format, *args)");
                Logger.INSTANCE.d(BluetoothPeripheral.TAG, str);
            }
        };
        this.bondStateReceiver = new BroadcastReceiver() { // from class: com.welie.blessed.BluetoothPeripheral$bondStateReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                BluetoothDevice bluetoothDevice;
                Intrinsics.checkNotNullParameter(context2, "context");
                Intrinsics.checkNotNullParameter(intent, "intent");
                String action = intent.getAction();
                if (action != null && (bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")) != null && StringsKt.equals(bluetoothDevice.getAddress(), this.this$0.getAddress(), true) && Intrinsics.areEqual(action, "android.bluetooth.device.action.BOND_STATE_CHANGED")) {
                    this.this$0.handleBondStateChange(intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", Integer.MIN_VALUE), intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", Integer.MIN_VALUE));
                }
            }
        };
        this.pairingRequestBroadcastReceiver = new BroadcastReceiver() { // from class: com.welie.blessed.BluetoothPeripheral$pairingRequestBroadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String pincode;
                Intrinsics.checkNotNullParameter(context2, "context");
                Intrinsics.checkNotNullParameter(intent, "intent");
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (bluetoothDevice != null && StringsKt.equals(bluetoothDevice.getAddress(), this.this$0.getAddress(), true)) {
                    int intExtra = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_VARIANT", Integer.MIN_VALUE);
                    Logger.INSTANCE.d(BluetoothPeripheral.TAG, "pairing request received: %s (%s)", this.this$0.pairingVariantToString(intExtra), Integer.valueOf(intExtra));
                    if (intExtra != 0 || (pincode = this.this$0.listener.getPincode(this.this$0)) == null) {
                        return;
                    }
                    Logger.INSTANCE.d(BluetoothPeripheral.TAG, "setting PIN code for this peripheral using '%s'", pincode);
                    byte[] bytes = pincode.getBytes(Charsets.UTF_8);
                    Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
                    bluetoothDevice.setPin(bytes);
                    abortBroadcast();
                }
            }
        };
        this.bondStateCallback = new Function1<BondState, Unit>() { // from class: com.welie.blessed.BluetoothPeripheral$bondStateCallback$1
            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BondState it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BondState bondState) {
                invoke2(bondState);
                return Unit.INSTANCE;
            }
        };
    }

    public final int getCurrentMtu() {
        return this.currentMtu;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void successfullyConnected() {
        long jElapsedRealtime = SystemClock.elapsedRealtime() - this.connectTimestamp;
        Logger logger = Logger.INSTANCE;
        String str = TAG;
        logger.d(str, "connected to '%s' (%s) in %.1fs", getName(), getBondState(), Float.valueOf(jElapsedRealtime / 1000.0f));
        if (getBondState() == BondState.NONE || getBondState() == BondState.BONDED) {
            discoverServices();
        } else if (getBondState() == BondState.BONDING) {
            Logger.INSTANCE.d(str, "waiting for bonding to complete");
        }
    }

    /* compiled from: BluetoothPeripheral.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$discoverServices$1", f = "BluetoothPeripheral.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.welie.blessed.BluetoothPeripheral$discoverServices$1, reason: invalid class name and case insensitive filesystem */
    static final class C04471 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C04471(Continuation<? super C04471> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return BluetoothPeripheral.this.new C04471(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04471) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0040  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                r5 = this;
                kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r0 = r5.label
                if (r0 != 0) goto L4e
                kotlin.ResultKt.throwOnFailure(r6)
                com.welie.blessed.Logger r6 = com.welie.blessed.Logger.INSTANCE
                java.lang.String r0 = com.welie.blessed.BluetoothPeripheral.access$getTAG$cp()
                r1 = 1
                java.lang.Object[] r2 = new java.lang.Object[r1]
                com.welie.blessed.BluetoothPeripheral r3 = com.welie.blessed.BluetoothPeripheral.this
                java.lang.String r3 = r3.getName()
                r4 = 0
                r2[r4] = r3
                java.lang.String r3 = "discovering services of '%s'"
                r6.d(r0, r3, r2)
                com.welie.blessed.BluetoothPeripheral r6 = com.welie.blessed.BluetoothPeripheral.this
                android.bluetooth.BluetoothGatt r6 = com.welie.blessed.BluetoothPeripheral.access$getBluetoothGatt$p(r6)
                if (r6 == 0) goto L40
                com.welie.blessed.BluetoothPeripheral r6 = com.welie.blessed.BluetoothPeripheral.this
                android.bluetooth.BluetoothGatt r6 = com.welie.blessed.BluetoothPeripheral.access$getBluetoothGatt$p(r6)
                if (r6 == 0) goto L38
                boolean r6 = r6.discoverServices()
                if (r6 != r1) goto L38
                r4 = r1
            L38:
                if (r4 == 0) goto L40
                com.welie.blessed.BluetoothPeripheral r6 = com.welie.blessed.BluetoothPeripheral.this
                com.welie.blessed.BluetoothPeripheral.access$setDiscoveryStarted$p(r6, r1)
                goto L4b
            L40:
                com.welie.blessed.Logger r6 = com.welie.blessed.Logger.INSTANCE
                java.lang.String r0 = com.welie.blessed.BluetoothPeripheral.access$getTAG$cp()
                java.lang.String r1 = "discoverServices failed to start"
                r6.e(r0, r1)
            L4b:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            L4e:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r0)
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.welie.blessed.BluetoothPeripheral.C04471.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    private final void discoverServices() {
        this.discoverJob = BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C04471(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void successfullyDisconnected(int previousState) {
        if (previousState == 1) {
            Logger.INSTANCE.d(TAG, "cancelling connect attempt");
        } else if (previousState == 2 || previousState == 3) {
            Logger.INSTANCE.d(TAG, "disconnected '%s' on request", getName());
        }
        if (this.bondLost) {
            completeDisconnect(false, HciStatus.SUCCESS);
            BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C04511(null), 3, null);
        } else {
            completeDisconnect(true, HciStatus.SUCCESS);
        }
    }

    /* compiled from: BluetoothPeripheral.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$successfullyDisconnected$1", f = "BluetoothPeripheral.kt", i = {}, l = {356}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.welie.blessed.BluetoothPeripheral$successfullyDisconnected$1, reason: invalid class name and case insensitive filesystem */
    static final class C04511 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C04511(Continuation<? super C04511> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return BluetoothPeripheral.this.new C04511(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04511) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
            BluetoothPeripheral.this.listener.connectFailed(BluetoothPeripheral.this, HciStatus.SUCCESS);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void connectionStateChangeUnsuccessful(HciStatus status, int previousState, int newState) {
        cancelPendingServiceDiscovery();
        boolean z = !getServices().isEmpty();
        if (previousState == 1) {
            boolean z2 = SystemClock.elapsedRealtime() - this.connectTimestamp > ((long) getTimeoutThreshold());
            if (status == HciStatus.ERROR && z2) {
                status = HciStatus.CONNECTION_FAILED_ESTABLISHMENT;
            }
            Logger.INSTANCE.d(TAG, "connection failed with status '%s'", status);
            completeDisconnect(false, status);
            this.listener.connectFailed(this, status);
            return;
        }
        if (previousState == 2 && newState == 0 && !z) {
            Logger.INSTANCE.d(TAG, "peripheral '%s' disconnected with status '%s' (%d) before completing service discovery", getName(), status, Integer.valueOf(status.getValue()));
            completeDisconnect(false, status);
            this.listener.connectFailed(this, status);
        } else {
            if (newState == 0) {
                Logger.INSTANCE.d(TAG, "peripheral '%s' disconnected with status '%s' (%d)", getName(), status, Integer.valueOf(status.getValue()));
            } else {
                Logger.INSTANCE.d(TAG, "unexpected connection state change for '%s' status '%s' (%d)", getName(), status, Integer.valueOf(status.getValue()));
            }
            completeDisconnect(true, status);
        }
    }

    private final void cancelPendingServiceDiscovery() {
        Job job = this.discoverJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        this.discoverJob = null;
    }

    /* compiled from: BluetoothPeripheral.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$handleBondStateChange$1", f = "BluetoothPeripheral.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.welie.blessed.BluetoothPeripheral$handleBondStateChange$1, reason: invalid class name and case insensitive filesystem */
    static final class C04481 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $bondState;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04481(int i, Continuation<? super C04481> continuation) {
            super(2, continuation);
            this.$bondState = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return BluetoothPeripheral.this.new C04481(this.$bondState, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04481) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            BluetoothPeripheral.this.getBondStateCallback().invoke(BondState.INSTANCE.fromValue(this.$bondState));
            return Unit.INSTANCE;
        }
    }

    /* compiled from: BluetoothPeripheral.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$handleBondStateChange$2", f = "BluetoothPeripheral.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.welie.blessed.BluetoothPeripheral$handleBondStateChange$2, reason: invalid class name and case insensitive filesystem */
    static final class C04492 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $bondState;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04492(int i, Continuation<? super C04492> continuation) {
            super(2, continuation);
            this.$bondState = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return BluetoothPeripheral.this.new C04492(this.$bondState, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04492) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            BluetoothPeripheral.this.getBondStateCallback().invoke(BondState.INSTANCE.fromValue(this.$bondState));
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleBondStateChange(int bondState, int previousBondState) {
        switch (bondState) {
            case 10:
                if (previousBondState == 11) {
                    Logger.INSTANCE.e(TAG, "bonding failed for '%s', disconnecting device", getName());
                    BuildersKt__Builders_commonKt.launch$default(this.callbackScope, null, null, new AnonymousClass3(null), 3, null);
                } else {
                    Logger.INSTANCE.e(TAG, "bond lost for '%s'", getName());
                    this.bondLost = true;
                    cancelPendingServiceDiscovery();
                    BuildersKt__Builders_commonKt.launch$default(this.callbackScope, null, null, new AnonymousClass4(null), 3, null);
                }
                disconnect();
                break;
            case 11:
                Logger.INSTANCE.d(TAG, "starting bonding with '%s' (%s)", getName(), getAddress());
                BuildersKt__Builders_commonKt.launch$default(this.callbackScope, null, null, new C04481(bondState, null), 3, null);
                break;
            case 12:
                Logger.INSTANCE.d(TAG, "bonded with '%s' (%s)", getName(), getAddress());
                BuildersKt__Builders_commonKt.launch$default(this.callbackScope, null, null, new C04492(bondState, null), 3, null);
                if (getServices().isEmpty() && !this.discoveryStarted) {
                    discoverServices();
                }
                if (this.manuallyBonding) {
                    this.manuallyBonding = false;
                    completedCommand();
                    break;
                }
                break;
        }
    }

    /* compiled from: BluetoothPeripheral.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$handleBondStateChange$3", f = "BluetoothPeripheral.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.welie.blessed.BluetoothPeripheral$handleBondStateChange$3, reason: invalid class name */
    static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return BluetoothPeripheral.this.new AnonymousClass3(continuation);
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
            BluetoothPeripheral.this.getBondStateCallback().invoke(BondState.BONDING_FAILED);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: BluetoothPeripheral.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$handleBondStateChange$4", f = "BluetoothPeripheral.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.welie.blessed.BluetoothPeripheral$handleBondStateChange$4, reason: invalid class name */
    static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass4(Continuation<? super AnonymousClass4> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return BluetoothPeripheral.this.new AnonymousClass4(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            BluetoothPeripheral.this.getBondStateCallback().invoke(BondState.BOND_LOST);
            return Unit.INSTANCE;
        }
    }

    public final void setDevice(BluetoothDevice bluetoothDevice) {
        Intrinsics.checkNotNullParameter(bluetoothDevice, "bluetoothDevice");
        this.device = bluetoothDevice;
    }

    /* compiled from: BluetoothPeripheral.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$connect$1", f = "BluetoothPeripheral.kt", i = {}, l = {480}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.welie.blessed.BluetoothPeripheral$connect$1, reason: invalid class name and case insensitive filesystem */
    static final class C04461 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C04461(Continuation<? super C04461> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return BluetoothPeripheral.this.new C04461(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04461) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            BluetoothGatt bluetoothGattConnectGatt;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(BluetoothPeripheral.DIRECT_CONNECTION_DELAY_IN_MS, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            Logger.INSTANCE.d(BluetoothPeripheral.TAG, "connect to '%s' (%s) using TRANSPORT_LE", BluetoothPeripheral.this.getName(), BluetoothPeripheral.this.getAddress());
            BluetoothPeripheral.this.registerBondingBroadcastReceivers();
            BluetoothPeripheral.this.discoveryStarted = false;
            BluetoothPeripheral bluetoothPeripheral = BluetoothPeripheral.this;
            try {
                bluetoothGattConnectGatt = bluetoothPeripheral.device.connectGatt(BluetoothPeripheral.this.context, false, BluetoothPeripheral.this.bluetoothGattCallback, 2);
            } catch (SecurityException unused) {
                Logger.INSTANCE.d(BluetoothPeripheral.TAG, "exception");
                bluetoothGattConnectGatt = null;
            }
            bluetoothPeripheral.bluetoothGatt = bluetoothGattConnectGatt;
            BluetoothGatt bluetoothGatt = BluetoothPeripheral.this.bluetoothGatt;
            if (bluetoothGatt != null) {
                BluetoothPeripheral bluetoothPeripheral2 = BluetoothPeripheral.this;
                bluetoothPeripheral2.bluetoothGattCallback.onConnectionStateChange(bluetoothGatt, HciStatus.SUCCESS.getValue(), 1);
                bluetoothPeripheral2.connectTimestamp = SystemClock.elapsedRealtime();
                bluetoothPeripheral2.startConnectionTimer(bluetoothPeripheral2);
            }
            return Unit.INSTANCE;
        }
    }

    public final void connect() {
        if (this.state == 0) {
            BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C04461(null), 3, null);
        } else {
            Logger.INSTANCE.e(TAG, "peripheral '%s' not yet disconnected, will not connect", getName());
        }
    }

    /* compiled from: BluetoothPeripheral.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$autoConnect$1", f = "BluetoothPeripheral.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.welie.blessed.BluetoothPeripheral$autoConnect$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return BluetoothPeripheral.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            BluetoothGatt bluetoothGattConnectGatt;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Logger.INSTANCE.d(BluetoothPeripheral.TAG, "autoConnect to '%s' (%s) using TRANSPORT_LE", BluetoothPeripheral.this.getName(), BluetoothPeripheral.this.getAddress());
                BluetoothPeripheral.this.registerBondingBroadcastReceivers();
                BluetoothPeripheral.this.discoveryStarted = false;
                BluetoothPeripheral bluetoothPeripheral = BluetoothPeripheral.this;
                try {
                    bluetoothGattConnectGatt = bluetoothPeripheral.device.connectGatt(BluetoothPeripheral.this.context, true, BluetoothPeripheral.this.bluetoothGattCallback, 2);
                } catch (SecurityException unused) {
                    Logger.INSTANCE.e(BluetoothPeripheral.TAG, "connectGatt exception");
                    bluetoothGattConnectGatt = null;
                }
                bluetoothPeripheral.bluetoothGatt = bluetoothGattConnectGatt;
                BluetoothGatt bluetoothGatt = BluetoothPeripheral.this.bluetoothGatt;
                if (bluetoothGatt != null) {
                    BluetoothPeripheral bluetoothPeripheral2 = BluetoothPeripheral.this;
                    bluetoothPeripheral2.bluetoothGattCallback.onConnectionStateChange(bluetoothGatt, HciStatus.SUCCESS.getValue(), 1);
                    bluetoothPeripheral2.connectTimestamp = SystemClock.elapsedRealtime();
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    public final void autoConnect() {
        if (this.state == 0) {
            BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new AnonymousClass1(null), 3, null);
        } else {
            Logger.INSTANCE.e(TAG, "peripheral '%s' not yet disconnected, will not connect", getName());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void registerBondingBroadcastReceivers() {
        this.context.registerReceiver(this.bondStateReceiver, new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED"));
        this.context.registerReceiver(this.pairingRequestBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST"));
    }

    public final boolean createBond() {
        if (this.bluetoothGatt == null) {
            registerBondingBroadcastReceivers();
            return this.device.createBond();
        }
        return enqueue(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheral$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheral.createBond$lambda$0(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createBond$lambda$0(BluetoothPeripheral this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.manuallyBonding = true;
        if (!this$0.device.createBond()) {
            Logger.INSTANCE.e(TAG, "bonding failed for %s", this$0.getAddress());
            this$0.completedCommand();
        } else {
            Logger.INSTANCE.d(TAG, "manually bonding %s", this$0.getAddress());
            this$0.nrTries++;
        }
    }

    public final void cancelConnection$blessed_release() {
        if (this.bluetoothGatt == null) {
            Logger.INSTANCE.w(TAG, "cannot cancel connection because no connection attempt is made yet");
            return;
        }
        if (this.state == 0 || this.state == 3) {
            return;
        }
        cancelConnectionTimer();
        if (this.state == 1) {
            disconnect();
            BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new BluetoothPeripheral$cancelConnection$1(this, null), 3, null);
        } else {
            disconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void disconnect() {
        if (this.state == 2 || this.state == 1) {
            BluetoothGatt bluetoothGatt = this.bluetoothGatt;
            if (bluetoothGatt != null) {
                this.bluetoothGattCallback.onConnectionStateChange(bluetoothGatt, HciStatus.SUCCESS.getValue(), 3);
            }
            BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new AnonymousClass2(null), 3, null);
            return;
        }
        this.listener.disconnected(this, HciStatus.SUCCESS);
    }

    /* compiled from: BluetoothPeripheral.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$disconnect$2", f = "BluetoothPeripheral.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.welie.blessed.BluetoothPeripheral$disconnect$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return BluetoothPeripheral.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                if (BluetoothPeripheral.this.state == 3 && BluetoothPeripheral.this.bluetoothGatt != null) {
                    BluetoothGatt bluetoothGatt = BluetoothPeripheral.this.bluetoothGatt;
                    if (bluetoothGatt != null) {
                        bluetoothGatt.disconnect();
                    }
                    Logger.INSTANCE.i(BluetoothPeripheral.TAG, "force disconnect '%s' (%s)", BluetoothPeripheral.this.getName(), BluetoothPeripheral.this.getAddress());
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    public final void disconnectWhenBluetoothOff() {
        completeDisconnect(true, HciStatus.SUCCESS);
    }

    private final void completeDisconnect(boolean notify, HciStatus status) {
        if (this.bluetoothGatt != null) {
            BluetoothGatt bluetoothGatt = this.bluetoothGatt;
            if (bluetoothGatt != null) {
                bluetoothGatt.close();
            }
            this.bluetoothGatt = null;
        }
        this.commandQueue.clear();
        this.commandQueueBusy = false;
        this.notifyingCharacteristics.clear();
        this.currentMtu = 23;
        this.currentCommand = 0;
        this.manuallyBonding = false;
        this.discoveryStarted = false;
        try {
            this.context.unregisterReceiver(this.bondStateReceiver);
            this.context.unregisterReceiver(this.pairingRequestBroadcastReceiver);
        } catch (IllegalArgumentException unused) {
        }
        this.bondLost = false;
        if (notify) {
            this.listener.disconnected(this, status);
        }
    }

    public final String getAddress() {
        String address = this.device.getAddress();
        Intrinsics.checkNotNullExpressionValue(address, "device.address");
        return address;
    }

    public final PeripheralType getType() {
        return PeripheralType.INSTANCE.fromValue(this.device.getType());
    }

    public final String getName() {
        String name = this.device.getName();
        if (name != null) {
            this.cachedName = name;
            return name;
        }
        return this.cachedName;
    }

    public final BondState getBondState() {
        return BondState.INSTANCE.fromValue(this.device.getBondState());
    }

    public final Function1<BondState, Unit> getBondStateCallback() {
        return this.bondStateCallback;
    }

    public final void setBondStateCallback(Function1<? super BondState, Unit> function1) {
        Intrinsics.checkNotNullParameter(function1, "<set-?>");
        this.bondStateCallback = function1;
    }

    public final void observeBondState(Function1<? super BondState, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.bondStateCallback = callback;
    }

    public final List<BluetoothGattService> getServices() {
        BluetoothGatt bluetoothGatt = this.bluetoothGatt;
        List<BluetoothGattService> services = bluetoothGatt != null ? bluetoothGatt.getServices() : null;
        return services == null ? CollectionsKt.emptyList() : services;
    }

    public final BluetoothGattService getService(UUID serviceUUID) {
        Intrinsics.checkNotNullParameter(serviceUUID, "serviceUUID");
        BluetoothGatt bluetoothGatt = this.bluetoothGatt;
        if (bluetoothGatt != null) {
            return bluetoothGatt.getService(serviceUUID);
        }
        return null;
    }

    public final BluetoothGattCharacteristic getCharacteristic(UUID serviceUUID, UUID characteristicUUID) {
        Intrinsics.checkNotNullParameter(serviceUUID, "serviceUUID");
        Intrinsics.checkNotNullParameter(characteristicUUID, "characteristicUUID");
        BluetoothGattService service = getService(serviceUUID);
        if (service != null) {
            return service.getCharacteristic(characteristicUUID);
        }
        return null;
    }

    public final ConnectionState getState() {
        return ConnectionState.INSTANCE.fromValue(this.state);
    }

    public final int getMaximumWriteValueLength(WriteType writeType) {
        Intrinsics.checkNotNullParameter(writeType, "writeType");
        int i = WhenMappings.$EnumSwitchMapping$0[writeType.ordinal()];
        if (i == 1) {
            return 512;
        }
        if (i == 2) {
            return this.currentMtu - 15;
        }
        return this.currentMtu - 3;
    }

    public final boolean isNotifying(BluetoothGattCharacteristic characteristic) {
        Intrinsics.checkNotNullParameter(characteristic, "characteristic");
        return this.notifyingCharacteristics.contains(characteristic);
    }

    public final Set<BluetoothGattCharacteristic> getNotifyingCharacteristics() {
        Set<BluetoothGattCharacteristic> setUnmodifiableSet = Collections.unmodifiableSet(this.notifyingCharacteristics);
        Intrinsics.checkNotNullExpressionValue(setUnmodifiableSet, "unmodifiableSet(notifyingCharacteristics)");
        return setUnmodifiableSet;
    }

    private final boolean isConnected() {
        return this.bluetoothGatt != null && this.state == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean notConnected() {
        return !isConnected();
    }

    public final boolean isUncached() {
        return getType() == PeripheralType.UNKNOWN;
    }

    public final Object readCharacteristic(UUID uuid, UUID uuid2, Continuation<? super byte[]> continuation) {
        if (!isConnected()) {
            throw new IllegalArgumentException(PERIPHERAL_NOT_CONNECTED.toString());
        }
        BluetoothGattCharacteristic characteristic = getCharacteristic(uuid, uuid2);
        return characteristic != null ? readCharacteristic(characteristic, continuation) : new byte[0];
    }

    public final Object readCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic, Continuation<? super byte[]> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        try {
            if (!readCharacteristic(bluetoothGattCharacteristic, new BluetoothPeripheralCallback() { // from class: com.welie.blessed.BluetoothPeripheral$readCharacteristic$4$result$1
                @Override // com.welie.blessed.BluetoothPeripheralCallback
                public void onCharacteristicRead(BluetoothPeripheral peripheral, byte[] value, BluetoothGattCharacteristic characteristic, GattStatus status) {
                    Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                    Intrinsics.checkNotNullParameter(value, "value");
                    Intrinsics.checkNotNullParameter(characteristic, "characteristic");
                    Intrinsics.checkNotNullParameter(status, "status");
                    if (status == GattStatus.SUCCESS) {
                        Continuation<byte[]> continuation2 = safeContinuation2;
                        Result.Companion companion = Result.INSTANCE;
                        continuation2.resumeWith(Result.m624constructorimpl(value));
                    } else {
                        Continuation<byte[]> continuation3 = safeContinuation2;
                        Result.Companion companion2 = Result.INSTANCE;
                        continuation3.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(new GattException(status))));
                    }
                }
            })) {
                Result.Companion companion = Result.INSTANCE;
                safeContinuation2.resumeWith(Result.m624constructorimpl(new byte[0]));
            }
        } catch (IllegalArgumentException e) {
            Result.Companion companion2 = Result.INSTANCE;
            safeContinuation2.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(e)));
        }
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    private final boolean readCharacteristic(final BluetoothGattCharacteristic characteristic, final BluetoothPeripheralCallback resultCallback) {
        if (!ExtensionsKt.supportsReading(characteristic)) {
            throw new IllegalArgumentException("characteristic does not have read property".toString());
        }
        if (!isConnected()) {
            throw new IllegalArgumentException(PERIPHERAL_NOT_CONNECTED.toString());
        }
        return enqueue(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheral$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheral.readCharacteristic$lambda$6(this.f$0, resultCallback, characteristic);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void readCharacteristic$lambda$6(BluetoothPeripheral this$0, BluetoothPeripheralCallback resultCallback, BluetoothGattCharacteristic characteristic) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(resultCallback, "$resultCallback");
        Intrinsics.checkNotNullParameter(characteristic, "$characteristic");
        if (this$0.isConnected()) {
            this$0.currentResultCallback = resultCallback;
            BluetoothGatt bluetoothGatt = this$0.bluetoothGatt;
            if (bluetoothGatt != null && bluetoothGatt.readCharacteristic(characteristic)) {
                Logger logger = Logger.INSTANCE;
                String str = TAG;
                UUID uuid = characteristic.getUuid();
                Intrinsics.checkNotNullExpressionValue(uuid, "characteristic.uuid");
                logger.d(str, "reading characteristic <%s>", uuid);
                this$0.nrTries++;
                return;
            }
            Logger logger2 = Logger.INSTANCE;
            String str2 = TAG;
            UUID uuid2 = characteristic.getUuid();
            Intrinsics.checkNotNullExpressionValue(uuid2, "characteristic.uuid");
            logger2.e(str2, "readCharacteristic failed for characteristic: %s", uuid2);
            resultCallback.onCharacteristicRead(this$0, new byte[0], characteristic, GattStatus.READ_NOT_PERMITTED);
            this$0.completedCommand();
            return;
        }
        resultCallback.onCharacteristicRead(this$0, new byte[0], characteristic, GattStatus.CONNECTION_CANCELLED);
        this$0.completedCommand();
    }

    public final Object writeCharacteristic(UUID uuid, UUID uuid2, byte[] bArr, WriteType writeType, Continuation<? super byte[]> continuation) {
        if (!isConnected()) {
            throw new IllegalArgumentException(PERIPHERAL_NOT_CONNECTED.toString());
        }
        BluetoothGattCharacteristic characteristic = getCharacteristic(uuid, uuid2);
        return characteristic != null ? writeCharacteristic(characteristic, bArr, writeType, continuation) : new byte[0];
    }

    public final Object writeCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr, WriteType writeType, Continuation<? super byte[]> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        try {
            if (!writeCharacteristic(bluetoothGattCharacteristic, bArr, writeType, new BluetoothPeripheralCallback() { // from class: com.welie.blessed.BluetoothPeripheral$writeCharacteristic$4$result$1
                @Override // com.welie.blessed.BluetoothPeripheralCallback
                public void onCharacteristicWrite(BluetoothPeripheral peripheral, byte[] value, BluetoothGattCharacteristic characteristic, GattStatus status) {
                    Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                    Intrinsics.checkNotNullParameter(value, "value");
                    Intrinsics.checkNotNullParameter(characteristic, "characteristic");
                    Intrinsics.checkNotNullParameter(status, "status");
                    if (status == GattStatus.SUCCESS) {
                        Continuation<byte[]> continuation2 = safeContinuation2;
                        Result.Companion companion = Result.INSTANCE;
                        continuation2.resumeWith(Result.m624constructorimpl(value));
                    } else {
                        Continuation<byte[]> continuation3 = safeContinuation2;
                        Result.Companion companion2 = Result.INSTANCE;
                        continuation3.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(new GattException(status))));
                    }
                }
            })) {
                Result.Companion companion = Result.INSTANCE;
                safeContinuation2.resumeWith(Result.m624constructorimpl(new byte[0]));
            }
        } catch (IllegalArgumentException e) {
            Result.Companion companion2 = Result.INSTANCE;
            safeContinuation2.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(e)));
        }
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    private final boolean writeCharacteristic(final BluetoothGattCharacteristic characteristic, byte[] value, final WriteType writeType, final BluetoothPeripheralCallback resultCallback) {
        if (!isConnected()) {
            throw new IllegalArgumentException(PERIPHERAL_NOT_CONNECTED.toString());
        }
        if (!(!(value.length == 0))) {
            throw new IllegalArgumentException(VALUE_BYTE_ARRAY_IS_EMPTY.toString());
        }
        if (!(value.length <= getMaximumWriteValueLength(writeType))) {
            throw new IllegalArgumentException(VALUE_BYTE_ARRAY_IS_TOO_LONG.toString());
        }
        if (!ExtensionsKt.supportsWriteType(characteristic, writeType)) {
            throw new IllegalArgumentException(("characteristic <" + characteristic.getUuid() + "> does not support writeType '" + writeType + "'").toString());
        }
        final byte[] bArrCopyOf = copyOf(value);
        return enqueue(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheral$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheral.writeCharacteristic$lambda$13(this.f$0, resultCallback, bArrCopyOf, characteristic, writeType);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void writeCharacteristic$lambda$13(BluetoothPeripheral this$0, BluetoothPeripheralCallback resultCallback, byte[] bytesToWrite, BluetoothGattCharacteristic characteristic, WriteType writeType) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(resultCallback, "$resultCallback");
        Intrinsics.checkNotNullParameter(bytesToWrite, "$bytesToWrite");
        Intrinsics.checkNotNullParameter(characteristic, "$characteristic");
        Intrinsics.checkNotNullParameter(writeType, "$writeType");
        if (this$0.isConnected()) {
            this$0.currentResultCallback = resultCallback;
            this$0.currentWriteBytes = bytesToWrite;
            characteristic.setWriteType(writeType.getWriteType());
            if (this$0.willCauseLongWrite(bytesToWrite, writeType)) {
                Logger.INSTANCE.w(TAG, "value byte array is longer than allowed by MTU, write will fail if peripheral does not support long writes");
            }
            if (this$0.internalWriteCharacteristic(characteristic, bytesToWrite, writeType)) {
                Logger logger = Logger.INSTANCE;
                String str = TAG;
                UUID uuid = characteristic.getUuid();
                Intrinsics.checkNotNullExpressionValue(uuid, "characteristic.uuid");
                logger.d(str, "writing <%s> to characteristic <%s>", ExtensionsKt.asHexString(bytesToWrite), uuid);
                this$0.nrTries++;
                return;
            }
            Logger logger2 = Logger.INSTANCE;
            String str2 = TAG;
            UUID uuid2 = characteristic.getUuid();
            Intrinsics.checkNotNullExpressionValue(uuid2, "characteristic.uuid");
            logger2.e(str2, "writeCharacteristic failed for characteristic: %s", uuid2);
            resultCallback.onCharacteristicWrite(this$0, new byte[0], characteristic, GattStatus.WRITE_NOT_PERMITTED);
            this$0.completedCommand();
            return;
        }
        resultCallback.onCharacteristicWrite(this$0, new byte[0], characteristic, GattStatus.CONNECTION_CANCELLED);
        this$0.completedCommand();
    }

    private final boolean willCauseLongWrite(byte[] value, WriteType writeType) {
        return value.length > this.currentMtu + (-3) && writeType == WriteType.WITH_RESPONSE;
    }

    private final boolean internalWriteCharacteristic(BluetoothGattCharacteristic characteristic, byte[] value, WriteType writeType) {
        if (this.bluetoothGatt == null) {
            return false;
        }
        this.currentWriteBytes = value;
        if (Build.VERSION.SDK_INT >= 33) {
            BluetoothGatt bluetoothGatt = this.bluetoothGatt;
            Integer numValueOf = bluetoothGatt != null ? Integer.valueOf(bluetoothGatt.writeCharacteristic(characteristic, this.currentWriteBytes, writeType.getWriteType())) : null;
            return numValueOf != null && numValueOf.intValue() == 0;
        }
        characteristic.setWriteType(writeType.getWriteType());
        characteristic.setValue(value);
        BluetoothGatt bluetoothGatt2 = this.bluetoothGatt;
        Intrinsics.checkNotNull(bluetoothGatt2);
        return bluetoothGatt2.writeCharacteristic(characteristic);
    }

    public final Object readDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor, Continuation<? super byte[]> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        try {
            if (!readDescriptor(bluetoothGattDescriptor, new BluetoothPeripheralCallback() { // from class: com.welie.blessed.BluetoothPeripheral$readDescriptor$2$result$1
                @Override // com.welie.blessed.BluetoothPeripheralCallback
                public void onDescriptorRead(BluetoothPeripheral peripheral, byte[] value, BluetoothGattDescriptor descriptor, GattStatus status) {
                    Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                    Intrinsics.checkNotNullParameter(value, "value");
                    Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                    Intrinsics.checkNotNullParameter(status, "status");
                    if (status == GattStatus.SUCCESS) {
                        Continuation<byte[]> continuation2 = safeContinuation2;
                        Result.Companion companion = Result.INSTANCE;
                        continuation2.resumeWith(Result.m624constructorimpl(value));
                    } else {
                        Continuation<byte[]> continuation3 = safeContinuation2;
                        Result.Companion companion2 = Result.INSTANCE;
                        continuation3.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(new GattException(status))));
                    }
                }
            })) {
                Result.Companion companion = Result.INSTANCE;
                safeContinuation2.resumeWith(Result.m624constructorimpl(new byte[0]));
            }
        } catch (IllegalArgumentException e) {
            Result.Companion companion2 = Result.INSTANCE;
            safeContinuation2.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(e)));
        }
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    private final boolean readDescriptor(final BluetoothGattDescriptor descriptor, final BluetoothPeripheralCallback resultCallback) {
        if (!isConnected()) {
            throw new IllegalArgumentException(PERIPHERAL_NOT_CONNECTED.toString());
        }
        return enqueue(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheral$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheral.readDescriptor$lambda$16(this.f$0, resultCallback, descriptor);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void readDescriptor$lambda$16(BluetoothPeripheral this$0, BluetoothPeripheralCallback resultCallback, BluetoothGattDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(resultCallback, "$resultCallback");
        Intrinsics.checkNotNullParameter(descriptor, "$descriptor");
        if (this$0.isConnected()) {
            this$0.currentResultCallback = resultCallback;
            BluetoothGatt bluetoothGatt = this$0.bluetoothGatt;
            if (bluetoothGatt != null && bluetoothGatt.readDescriptor(descriptor)) {
                Logger logger = Logger.INSTANCE;
                String str = TAG;
                UUID uuid = descriptor.getUuid();
                Intrinsics.checkNotNullExpressionValue(uuid, "descriptor.uuid");
                logger.d(str, "reading descriptor <%s>", uuid);
                this$0.nrTries++;
                return;
            }
            Logger logger2 = Logger.INSTANCE;
            String str2 = TAG;
            UUID uuid2 = descriptor.getUuid();
            Intrinsics.checkNotNullExpressionValue(uuid2, "descriptor.uuid");
            logger2.e(str2, "readDescriptor failed for characteristic: %s", uuid2);
            resultCallback.onDescriptorRead(this$0, new byte[0], descriptor, GattStatus.READ_NOT_PERMITTED);
            this$0.completedCommand();
            return;
        }
        resultCallback.onDescriptorRead(this$0, new byte[0], descriptor, GattStatus.CONNECTION_CANCELLED);
        this$0.completedCommand();
    }

    public final Object writeDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor, byte[] bArr, Continuation<? super byte[]> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        try {
            if (!writeDescriptor(bluetoothGattDescriptor, bArr, new BluetoothPeripheralCallback() { // from class: com.welie.blessed.BluetoothPeripheral$writeDescriptor$2$result$1
                @Override // com.welie.blessed.BluetoothPeripheralCallback
                public void onDescriptorWrite(BluetoothPeripheral peripheral, byte[] value, BluetoothGattDescriptor descriptor, GattStatus status) {
                    Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                    Intrinsics.checkNotNullParameter(value, "value");
                    Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                    Intrinsics.checkNotNullParameter(status, "status");
                    if (status == GattStatus.SUCCESS) {
                        Continuation<byte[]> continuation2 = safeContinuation2;
                        Result.Companion companion = Result.INSTANCE;
                        continuation2.resumeWith(Result.m624constructorimpl(value));
                    } else {
                        Continuation<byte[]> continuation3 = safeContinuation2;
                        Result.Companion companion2 = Result.INSTANCE;
                        continuation3.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(new GattException(status))));
                    }
                }
            })) {
                Result.Companion companion = Result.INSTANCE;
                safeContinuation2.resumeWith(Result.m624constructorimpl(new byte[0]));
            }
        } catch (IllegalArgumentException e) {
            Result.Companion companion2 = Result.INSTANCE;
            safeContinuation2.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(e)));
        }
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    private final boolean writeDescriptor(final BluetoothGattDescriptor descriptor, byte[] value, final BluetoothPeripheralCallback resultCallback) {
        if (!isConnected()) {
            throw new IllegalArgumentException(PERIPHERAL_NOT_CONNECTED.toString());
        }
        if (!(!(value.length == 0))) {
            throw new IllegalArgumentException(VALUE_BYTE_ARRAY_IS_EMPTY.toString());
        }
        if (!(value.length <= getMaximumWriteValueLength(WriteType.WITH_RESPONSE))) {
            throw new IllegalArgumentException(VALUE_BYTE_ARRAY_IS_TOO_LONG.toString());
        }
        final byte[] bArrCopyOf = copyOf(value);
        return enqueue(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheral$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheral.writeDescriptor$lambda$21(this.f$0, resultCallback, descriptor, bArrCopyOf);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void writeDescriptor$lambda$21(BluetoothPeripheral this$0, BluetoothPeripheralCallback resultCallback, BluetoothGattDescriptor descriptor, byte[] bytesToWrite) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(resultCallback, "$resultCallback");
        Intrinsics.checkNotNullParameter(descriptor, "$descriptor");
        Intrinsics.checkNotNullParameter(bytesToWrite, "$bytesToWrite");
        if (this$0.isConnected()) {
            this$0.currentResultCallback = resultCallback;
            if (this$0.internalWriteDescriptor(descriptor, bytesToWrite)) {
                Logger logger = Logger.INSTANCE;
                String str = TAG;
                UUID uuid = descriptor.getUuid();
                Intrinsics.checkNotNullExpressionValue(uuid, "descriptor.uuid");
                logger.d(str, "writing <%s> to descriptor <%s>", ExtensionsKt.asHexString(bytesToWrite), uuid);
                this$0.nrTries++;
                return;
            }
            Logger logger2 = Logger.INSTANCE;
            String str2 = TAG;
            UUID uuid2 = descriptor.getUuid();
            Intrinsics.checkNotNullExpressionValue(uuid2, "descriptor.uuid");
            logger2.e(str2, "writeDescriptor failed for descriptor: %s", uuid2);
            resultCallback.onDescriptorWrite(this$0, new byte[0], descriptor, GattStatus.WRITE_NOT_PERMITTED);
            this$0.completedCommand();
            return;
        }
        resultCallback.onDescriptorWrite(this$0, new byte[0], descriptor, GattStatus.CONNECTION_CANCELLED);
        this$0.completedCommand();
    }

    private final boolean internalWriteDescriptor(BluetoothGattDescriptor descriptor, byte[] value) {
        if (this.bluetoothGatt == null) {
            return false;
        }
        this.currentWriteBytes = value;
        if (Build.VERSION.SDK_INT >= 33) {
            BluetoothGatt bluetoothGatt = this.bluetoothGatt;
            Integer numValueOf = bluetoothGatt != null ? Integer.valueOf(bluetoothGatt.writeDescriptor(descriptor, value)) : null;
            return numValueOf != null && numValueOf.intValue() == 0;
        }
        descriptor.setValue(value);
        BluetoothGatt bluetoothGatt2 = this.bluetoothGatt;
        Intrinsics.checkNotNull(bluetoothGatt2);
        return bluetoothGatt2.writeDescriptor(descriptor);
    }

    public final Object observe(BluetoothGattCharacteristic bluetoothGattCharacteristic, Function1<? super byte[], Unit> function1, Continuation<? super Boolean> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        try {
            this.observeMap.put(bluetoothGattCharacteristic, function1);
            if (!setNotify(bluetoothGattCharacteristic, true, new BluetoothPeripheralCallback() { // from class: com.welie.blessed.BluetoothPeripheral$observe$2$result$1
                @Override // com.welie.blessed.BluetoothPeripheralCallback
                public void onNotificationStateUpdate(BluetoothPeripheral peripheral, BluetoothGattCharacteristic characteristic, GattStatus status) {
                    Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                    Intrinsics.checkNotNullParameter(characteristic, "characteristic");
                    Intrinsics.checkNotNullParameter(status, "status");
                    if (status != GattStatus.SUCCESS) {
                        this.observeMap.remove(characteristic);
                        Continuation<Boolean> continuation2 = safeContinuation2;
                        Result.Companion companion = Result.INSTANCE;
                        continuation2.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(new GattException(status))));
                        return;
                    }
                    Logger.INSTANCE.d(BluetoothPeripheral.TAG, "observing <" + characteristic.getUuid() + "> succeeded");
                    Continuation<Boolean> continuation3 = safeContinuation2;
                    Result.Companion companion2 = Result.INSTANCE;
                    continuation3.resumeWith(Result.m624constructorimpl(true));
                }
            })) {
                Result.Companion companion = Result.INSTANCE;
                safeContinuation2.resumeWith(Result.m624constructorimpl(Boxing.boxBoolean(false)));
            }
        } catch (IllegalArgumentException e) {
            Result.Companion companion2 = Result.INSTANCE;
            safeContinuation2.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(e)));
        }
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    public final Object stopObserving(BluetoothGattCharacteristic bluetoothGattCharacteristic, Continuation<? super Boolean> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        try {
            this.observeMap.remove(bluetoothGattCharacteristic);
            if (!setNotify(bluetoothGattCharacteristic, false, new BluetoothPeripheralCallback() { // from class: com.welie.blessed.BluetoothPeripheral$stopObserving$2$result$1
                @Override // com.welie.blessed.BluetoothPeripheralCallback
                public void onNotificationStateUpdate(BluetoothPeripheral peripheral, BluetoothGattCharacteristic characteristic, GattStatus status) {
                    Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                    Intrinsics.checkNotNullParameter(characteristic, "characteristic");
                    Intrinsics.checkNotNullParameter(status, "status");
                    if (status == GattStatus.SUCCESS) {
                        Logger.INSTANCE.d(BluetoothPeripheral.TAG, "stopped observing <" + characteristic.getUuid() + ">");
                        Continuation<Boolean> continuation2 = safeContinuation2;
                        Result.Companion companion = Result.INSTANCE;
                        continuation2.resumeWith(Result.m624constructorimpl(true));
                        return;
                    }
                    Continuation<Boolean> continuation3 = safeContinuation2;
                    Result.Companion companion2 = Result.INSTANCE;
                    continuation3.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(new GattException(status))));
                }
            })) {
                Result.Companion companion = Result.INSTANCE;
                safeContinuation2.resumeWith(Result.m624constructorimpl(Boxing.boxBoolean(false)));
            }
        } catch (IllegalArgumentException e) {
            Result.Companion companion2 = Result.INSTANCE;
            safeContinuation2.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(e)));
        }
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    private final boolean setNotify(UUID serviceUUID, UUID characteristicUUID, boolean enable, BluetoothPeripheralCallback resultCallback) {
        BluetoothGattCharacteristic characteristic = getCharacteristic(serviceUUID, characteristicUUID);
        if (characteristic != null) {
            return setNotify(characteristic, enable, resultCallback);
        }
        return false;
    }

    private final boolean setNotify(final BluetoothGattCharacteristic characteristic, final boolean enable, final BluetoothPeripheralCallback resultCallback) {
        byte[] bArr;
        if (!isConnected()) {
            throw new IllegalArgumentException(PERIPHERAL_NOT_CONNECTED.toString());
        }
        if (!ExtensionsKt.supportsNotifying(characteristic)) {
            throw new IllegalArgumentException(("characteristic <" + characteristic.getUuid() + "> does not have notify or indicate property").toString());
        }
        final BluetoothGattDescriptor descriptor = characteristic.getDescriptor(CCC_DESCRIPTOR_UUID);
        if (descriptor == null) {
            Logger logger = Logger.INSTANCE;
            String str = TAG;
            UUID uuid = characteristic.getUuid();
            Intrinsics.checkNotNullExpressionValue(uuid, "characteristic.uuid");
            logger.e(str, "could not get CCC descriptor for characteristic %s", uuid);
            return false;
        }
        int properties = characteristic.getProperties();
        if ((properties & 16) > 0) {
            bArr = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
        } else if ((properties & 32) > 0) {
            bArr = BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
        } else {
            Logger logger2 = Logger.INSTANCE;
            String str2 = TAG;
            UUID uuid2 = characteristic.getUuid();
            Intrinsics.checkNotNullExpressionValue(uuid2, "characteristic.uuid");
            logger2.e(str2, "characteristic %s does not have notify or indicate property", uuid2);
            return false;
        }
        if (!enable) {
            bArr = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
        }
        final byte[] bArr2 = bArr;
        return enqueue(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheral$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheral.setNotify$lambda$27(this.f$0, resultCallback, descriptor, characteristic, enable, bArr2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setNotify$lambda$27(BluetoothPeripheral this$0, BluetoothPeripheralCallback resultCallback, BluetoothGattDescriptor descriptor, BluetoothGattCharacteristic characteristic, boolean z, byte[] finalValue) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(resultCallback, "$resultCallback");
        Intrinsics.checkNotNullParameter(characteristic, "$characteristic");
        if (this$0.notConnected()) {
            Intrinsics.checkNotNullExpressionValue(descriptor, "descriptor");
            resultCallback.onDescriptorWrite(this$0, new byte[0], descriptor, GattStatus.CONNECTION_CANCELLED);
            this$0.completedCommand();
            return;
        }
        this$0.currentResultCallback = resultCallback;
        BluetoothGatt bluetoothGatt = this$0.bluetoothGatt;
        if ((bluetoothGatt == null || bluetoothGatt.setCharacteristicNotification(characteristic, z)) ? false : true) {
            Logger logger = Logger.INSTANCE;
            String str = TAG;
            UUID uuid = characteristic.getUuid();
            Intrinsics.checkNotNullExpressionValue(uuid, "characteristic.uuid");
            logger.e(str, "setCharacteristicNotification failed for characteristic: %s", uuid);
            this$0.completedCommand();
            return;
        }
        Intrinsics.checkNotNullExpressionValue(finalValue, "finalValue");
        this$0.currentWriteBytes = finalValue;
        Intrinsics.checkNotNullExpressionValue(descriptor, "descriptor");
        if (this$0.internalWriteDescriptor(descriptor, finalValue)) {
            this$0.nrTries++;
            return;
        }
        Logger logger2 = Logger.INSTANCE;
        String str2 = TAG;
        UUID uuid2 = descriptor.getUuid();
        Intrinsics.checkNotNullExpressionValue(uuid2, "descriptor.uuid");
        logger2.e(str2, "writeDescriptor failed for descriptor: %s", uuid2);
        resultCallback.onDescriptorWrite(this$0, new byte[0], descriptor, GattStatus.WRITE_NOT_PERMITTED);
        this$0.completedCommand();
    }

    public final Object readRemoteRssi(Continuation<? super Integer> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        if (!readRemoteRssi(new BluetoothPeripheralCallback() { // from class: com.welie.blessed.BluetoothPeripheral$readRemoteRssi$2$result$1
            @Override // com.welie.blessed.BluetoothPeripheralCallback
            public void onReadRemoteRssi(BluetoothPeripheral peripheral, int rssi, GattStatus status) {
                Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                Intrinsics.checkNotNullParameter(status, "status");
                if (status == GattStatus.SUCCESS) {
                    Integer numValueOf = Integer.valueOf(rssi);
                    Continuation<Integer> continuation2 = safeContinuation2;
                    Result.Companion companion = Result.INSTANCE;
                    continuation2.resumeWith(Result.m624constructorimpl(numValueOf));
                    return;
                }
                Continuation<Integer> continuation3 = safeContinuation2;
                Result.Companion companion2 = Result.INSTANCE;
                continuation3.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(new GattException(status))));
            }
        })) {
            Result.Companion companion = Result.INSTANCE;
            safeContinuation2.resumeWith(Result.m624constructorimpl(Boxing.boxInt(-255)));
        }
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    private final boolean readRemoteRssi(final BluetoothPeripheralCallback resultCallback) {
        if (!isConnected()) {
            throw new IllegalArgumentException(PERIPHERAL_NOT_CONNECTED.toString());
        }
        return enqueue(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheral$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheral.readRemoteRssi$lambda$30(this.f$0, resultCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void readRemoteRssi$lambda$30(BluetoothPeripheral this$0, BluetoothPeripheralCallback resultCallback) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(resultCallback, "$resultCallback");
        if (this$0.isConnected()) {
            this$0.currentResultCallback = resultCallback;
            BluetoothGatt bluetoothGatt = this$0.bluetoothGatt;
            if ((bluetoothGatt == null || bluetoothGatt.readRemoteRssi()) ? false : true) {
                Logger.INSTANCE.e(TAG, "readRemoteRssi failed");
                resultCallback.onReadRemoteRssi(this$0, 0, GattStatus.ERROR);
                this$0.completedCommand();
                return;
            }
            return;
        }
        resultCallback.onReadRemoteRssi(this$0, 0, GattStatus.CONNECTION_CANCELLED);
        this$0.completedCommand();
    }

    public final Object requestMtu(int i, Continuation<? super Integer> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        try {
            if (!requestMtu(i, new BluetoothPeripheralCallback() { // from class: com.welie.blessed.BluetoothPeripheral$requestMtu$2$result$1
                @Override // com.welie.blessed.BluetoothPeripheralCallback
                public void onMtuChanged(BluetoothPeripheral peripheral, int mtu, GattStatus status) {
                    Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                    Intrinsics.checkNotNullParameter(status, "status");
                    if (status == GattStatus.SUCCESS) {
                        Integer numValueOf = Integer.valueOf(mtu);
                        Continuation<Integer> continuation2 = safeContinuation2;
                        Result.Companion companion = Result.INSTANCE;
                        continuation2.resumeWith(Result.m624constructorimpl(numValueOf));
                        return;
                    }
                    Continuation<Integer> continuation3 = safeContinuation2;
                    Result.Companion companion2 = Result.INSTANCE;
                    continuation3.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(new GattException(status))));
                }
            })) {
                Result.Companion companion = Result.INSTANCE;
                safeContinuation2.resumeWith(Result.m624constructorimpl(Boxing.boxInt(this.currentMtu)));
            }
        } catch (IllegalArgumentException e) {
            Result.Companion companion2 = Result.INSTANCE;
            safeContinuation2.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(e)));
        }
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    private final boolean requestMtu(final int mtu, final BluetoothPeripheralCallback resultCallback) {
        boolean z = false;
        if (23 <= mtu && mtu < 518) {
            z = true;
        }
        if (!z) {
            throw new IllegalArgumentException("mtu must be between 23 and 517".toString());
        }
        if (!isConnected()) {
            throw new IllegalArgumentException(PERIPHERAL_NOT_CONNECTED.toString());
        }
        return enqueue(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheral$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheral.requestMtu$lambda$34(this.f$0, resultCallback, mtu);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void requestMtu$lambda$34(BluetoothPeripheral this$0, BluetoothPeripheralCallback resultCallback, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(resultCallback, "$resultCallback");
        if (this$0.isConnected()) {
            this$0.currentResultCallback = resultCallback;
            BluetoothGatt bluetoothGatt = this$0.bluetoothGatt;
            if (bluetoothGatt != null && bluetoothGatt.requestMtu(i)) {
                this$0.currentCommand = 1;
                Logger.INSTANCE.d(TAG, "requesting MTU of %d", Integer.valueOf(i));
                return;
            } else {
                Logger.INSTANCE.e(TAG, "requestMtu failed");
                resultCallback.onMtuChanged(this$0, 0, GattStatus.ERROR);
                this$0.completedCommand();
                return;
            }
        }
        resultCallback.onMtuChanged(this$0, 0, GattStatus.CONNECTION_CANCELLED);
        this$0.completedCommand();
    }

    public final Object requestConnectionPriority(ConnectionPriority connectionPriority, Continuation<? super Boolean> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        try {
            if (!requestConnectionPriority(connectionPriority, new BluetoothPeripheralCallback() { // from class: com.welie.blessed.BluetoothPeripheral$requestConnectionPriority$2$result$1
                @Override // com.welie.blessed.BluetoothPeripheralCallback
                public void onRequestedConnectionPriority(BluetoothPeripheral peripheral) {
                    Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                    Continuation<Boolean> continuation2 = safeContinuation2;
                    Result.Companion companion = Result.INSTANCE;
                    continuation2.resumeWith(Result.m624constructorimpl(true));
                }
            })) {
                Result.Companion companion = Result.INSTANCE;
                safeContinuation2.resumeWith(Result.m624constructorimpl(Boxing.boxBoolean(false)));
            }
        } catch (IllegalArgumentException e) {
            Result.Companion companion2 = Result.INSTANCE;
            safeContinuation2.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(e)));
        }
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    private final boolean requestConnectionPriority(final ConnectionPriority priority, final BluetoothPeripheralCallback resultCallback) {
        if (!isConnected()) {
            throw new IllegalArgumentException(PERIPHERAL_NOT_CONNECTED.toString());
        }
        return enqueue(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheral$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheral.requestConnectionPriority$lambda$37(this.f$0, resultCallback, priority);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void requestConnectionPriority$lambda$37(BluetoothPeripheral this$0, BluetoothPeripheralCallback resultCallback, ConnectionPriority priority) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(resultCallback, "$resultCallback");
        Intrinsics.checkNotNullParameter(priority, "$priority");
        if (this$0.isConnected()) {
            this$0.currentResultCallback = resultCallback;
            BluetoothGatt bluetoothGatt = this$0.bluetoothGatt;
            if (bluetoothGatt != null && bluetoothGatt.requestConnectionPriority(priority.getValue())) {
                Logger.INSTANCE.d(TAG, "requesting connection priority %s", priority);
            } else {
                Logger.INSTANCE.e(TAG, "could not request connection priority");
            }
        }
        BuildersKt__Builders_commonKt.launch$default(this$0.callbackScope, null, null, new BluetoothPeripheral$requestConnectionPriority$4$1(this$0, null), 3, null);
    }

    public final Object setPreferredPhy(PhyType phyType, PhyType phyType2, PhyOptions phyOptions, Continuation<? super Phy> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        if (!setPreferredPhy(phyType, phyType2, phyOptions, new BluetoothPeripheralCallback() { // from class: com.welie.blessed.BluetoothPeripheral$setPreferredPhy$2$result$1
            @Override // com.welie.blessed.BluetoothPeripheralCallback
            public void onPhyUpdate(BluetoothPeripheral peripheral, PhyType txPhy, PhyType rxPhy, GattStatus status) {
                Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                Intrinsics.checkNotNullParameter(txPhy, "txPhy");
                Intrinsics.checkNotNullParameter(rxPhy, "rxPhy");
                Intrinsics.checkNotNullParameter(status, "status");
                if (status == GattStatus.SUCCESS) {
                    Continuation<Phy> continuation2 = safeContinuation2;
                    Result.Companion companion = Result.INSTANCE;
                    continuation2.resumeWith(Result.m624constructorimpl(new Phy(txPhy, rxPhy)));
                } else {
                    Continuation<Phy> continuation3 = safeContinuation2;
                    Result.Companion companion2 = Result.INSTANCE;
                    continuation3.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(new GattException(status))));
                }
            }
        })) {
            Result.Companion companion = Result.INSTANCE;
            safeContinuation2.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(new IllegalStateException("could not execute operation"))));
        }
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    private final boolean setPreferredPhy(final PhyType txPhy, final PhyType rxPhy, final PhyOptions phyOptions, final BluetoothPeripheralCallback resultCallback) {
        if (!isConnected()) {
            throw new IllegalArgumentException(PERIPHERAL_NOT_CONNECTED.toString());
        }
        return enqueue(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheral$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheral.setPreferredPhy$lambda$40(this.f$0, txPhy, rxPhy, phyOptions, resultCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setPreferredPhy$lambda$40(BluetoothPeripheral this$0, PhyType txPhy, PhyType rxPhy, PhyOptions phyOptions, BluetoothPeripheralCallback resultCallback) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(txPhy, "$txPhy");
        Intrinsics.checkNotNullParameter(rxPhy, "$rxPhy");
        Intrinsics.checkNotNullParameter(phyOptions, "$phyOptions");
        Intrinsics.checkNotNullParameter(resultCallback, "$resultCallback");
        if (this$0.isConnected()) {
            Logger.INSTANCE.d(TAG, "setting preferred Phy: tx = %s, rx = %s, options = %s", txPhy, rxPhy, phyOptions);
            this$0.currentResultCallback = resultCallback;
            this$0.currentCommand = 2;
            BluetoothGatt bluetoothGatt = this$0.bluetoothGatt;
            if (bluetoothGatt != null) {
                bluetoothGatt.setPreferredPhy(txPhy.getMask(), rxPhy.getMask(), phyOptions.getValue());
                return;
            }
            return;
        }
        resultCallback.onPhyUpdate(this$0, txPhy, rxPhy, GattStatus.CONNECTION_CANCELLED);
        this$0.completedCommand();
    }

    public final Object readPhy(Continuation<? super Phy> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        readPhy(new BluetoothPeripheralCallback() { // from class: com.welie.blessed.BluetoothPeripheral$readPhy$2$1
            @Override // com.welie.blessed.BluetoothPeripheralCallback
            public void onPhyUpdate(BluetoothPeripheral peripheral, PhyType txPhy, PhyType rxPhy, GattStatus status) {
                Intrinsics.checkNotNullParameter(peripheral, "peripheral");
                Intrinsics.checkNotNullParameter(txPhy, "txPhy");
                Intrinsics.checkNotNullParameter(rxPhy, "rxPhy");
                Intrinsics.checkNotNullParameter(status, "status");
                if (status == GattStatus.SUCCESS) {
                    Continuation<Phy> continuation2 = safeContinuation2;
                    Result.Companion companion = Result.INSTANCE;
                    continuation2.resumeWith(Result.m624constructorimpl(new Phy(txPhy, rxPhy)));
                } else {
                    Continuation<Phy> continuation3 = safeContinuation2;
                    Result.Companion companion2 = Result.INSTANCE;
                    continuation3.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(new GattException(status))));
                }
            }
        });
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    private final boolean readPhy(final BluetoothPeripheralCallback resultCallback) {
        if (!isConnected()) {
            throw new IllegalArgumentException(PERIPHERAL_NOT_CONNECTED.toString());
        }
        return enqueue(new Runnable() { // from class: com.welie.blessed.BluetoothPeripheral$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothPeripheral.readPhy$lambda$43(this.f$0, resultCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void readPhy$lambda$43(BluetoothPeripheral this$0, BluetoothPeripheralCallback resultCallback) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(resultCallback, "$resultCallback");
        if (this$0.isConnected()) {
            this$0.currentResultCallback = resultCallback;
            BluetoothGatt bluetoothGatt = this$0.bluetoothGatt;
            if (bluetoothGatt != null) {
                bluetoothGatt.readPhy();
            }
            Logger.INSTANCE.d(TAG, "reading Phy");
            return;
        }
        resultCallback.onPhyUpdate(this$0, PhyType.UNKNOWN_PHY_TYPE, PhyType.UNKNOWN_PHY_TYPE, GattStatus.CONNECTION_CANCELLED);
        this$0.completedCommand();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object clearServicesCache(kotlin.coroutines.Continuation<? super java.lang.Boolean> r8) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r7 = this;
            boolean r0 = r8 instanceof com.welie.blessed.BluetoothPeripheral.C04451
            if (r0 == 0) goto L14
            r0 = r8
            com.welie.blessed.BluetoothPeripheral$clearServicesCache$1 r0 = (com.welie.blessed.BluetoothPeripheral.C04451) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L19
        L14:
            com.welie.blessed.BluetoothPeripheral$clearServicesCache$1 r0 = new com.welie.blessed.BluetoothPeripheral$clearServicesCache$1
            r0.<init>(r8)
        L19:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            int r0 = r0.I$0
            kotlin.ResultKt.throwOnFailure(r8)
            goto L83
        L2d:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L35:
            kotlin.ResultKt.throwOnFailure(r8)
            android.bluetooth.BluetoothGatt r8 = r7.bluetoothGatt
            if (r8 != 0) goto L41
            java.lang.Boolean r8 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)
            return r8
        L41:
            android.bluetooth.BluetoothGatt r8 = r7.bluetoothGatt     // Catch: java.lang.Exception -> L6b
            if (r8 == 0) goto L54
            java.lang.Class r8 = r8.getClass()     // Catch: java.lang.Exception -> L6b
            if (r8 == 0) goto L54
            java.lang.String r2 = "refresh"
            java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch: java.lang.Exception -> L6b
            java.lang.reflect.Method r8 = r8.getMethod(r2, r5)     // Catch: java.lang.Exception -> L6b
            goto L55
        L54:
            r8 = 0
        L55:
            if (r8 == 0) goto L74
            android.bluetooth.BluetoothGatt r2 = r7.bluetoothGatt     // Catch: java.lang.Exception -> L6b
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch: java.lang.Exception -> L6b
            java.lang.Object r8 = r8.invoke(r2, r5)     // Catch: java.lang.Exception -> L6b
            java.lang.String r2 = "null cannot be cast to non-null type kotlin.Boolean"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r8, r2)     // Catch: java.lang.Exception -> L6b
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.lang.Exception -> L6b
            boolean r8 = r8.booleanValue()     // Catch: java.lang.Exception -> L6b
            goto L75
        L6b:
            com.welie.blessed.Logger r8 = com.welie.blessed.Logger.INSTANCE
            java.lang.String r2 = com.welie.blessed.BluetoothPeripheral.TAG
            java.lang.String r5 = "could not invoke refresh method"
            r8.e(r2, r5)
        L74:
            r8 = r4
        L75:
            r0.I$0 = r8
            r0.label = r3
            r5 = 100
            java.lang.Object r0 = kotlinx.coroutines.DelayKt.delay(r5, r0)
            if (r0 != r1) goto L82
            return r1
        L82:
            r0 = r8
        L83:
            if (r0 == 0) goto L86
            goto L87
        L86:
            r3 = r4
        L87:
            java.lang.Boolean r8 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.welie.blessed.BluetoothPeripheral.clearServicesCache(kotlin.coroutines.Continuation):java.lang.Object");
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
        this.isRetrying = false;
        this.commandQueue.poll();
        this.commandQueueBusy = false;
        nextCommand();
    }

    private final void nextCommand() {
        synchronized (this) {
            if (this.commandQueueBusy) {
                return;
            }
            Runnable runnablePeek = this.commandQueue.peek();
            if (runnablePeek == null) {
                return;
            }
            Intrinsics.checkNotNullExpressionValue(runnablePeek, "commandQueue.peek() ?: return");
            if (this.bluetoothGatt == null) {
                Logger.INSTANCE.e(TAG, "gatt is 'null' for peripheral '%s', clearing command queue", getAddress());
                this.commandQueue.clear();
                this.commandQueueBusy = false;
            } else {
                this.commandQueueBusy = true;
                if (!this.isRetrying) {
                    this.nrTries = 0;
                }
                BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new BluetoothPeripheral$nextCommand$1$1(runnablePeek, this, null), 3, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startConnectionTimer(BluetoothPeripheral peripheral) {
        cancelConnectionTimer();
        this.timeoutJob = BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C04501(peripheral, null), 3, null);
    }

    /* compiled from: BluetoothPeripheral.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$startConnectionTimer$1", f = "BluetoothPeripheral.kt", i = {}, l = {1597}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.welie.blessed.BluetoothPeripheral$startConnectionTimer$1, reason: invalid class name and case insensitive filesystem */
    static final class C04501 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ BluetoothPeripheral $peripheral;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04501(BluetoothPeripheral bluetoothPeripheral, Continuation<? super C04501> continuation) {
            super(2, continuation);
            this.$peripheral = bluetoothPeripheral;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return BluetoothPeripheral.this.new C04501(this.$peripheral, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04501) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(BluetoothPeripheral.CONNECTION_TIMEOUT_IN_MS, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            if (BluetoothPeripheral.this.notConnected()) {
                Logger.INSTANCE.e(BluetoothPeripheral.TAG, "connection timeout, disconnecting '%s'", this.$peripheral.getName());
                BluetoothPeripheral.this.disconnect();
                BuildersKt__Builders_commonKt.launch$default(BluetoothPeripheral.this.scope, null, null, new C00851(BluetoothPeripheral.this, null), 3, null);
            }
            return Unit.INSTANCE;
        }

        /* compiled from: BluetoothPeripheral.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
        @DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$startConnectionTimer$1$1", f = "BluetoothPeripheral.kt", i = {}, l = {1602}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.welie.blessed.BluetoothPeripheral$startConnectionTimer$1$1, reason: invalid class name and collision with other inner class name */
        static final class C00851 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ BluetoothPeripheral this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00851(BluetoothPeripheral bluetoothPeripheral, Continuation<? super C00851> continuation) {
                super(2, continuation);
                this.this$0 = bluetoothPeripheral;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00851(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00851) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.label = 1;
                    if (DelayKt.delay(50L, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                if (this.this$0.bluetoothGatt != null) {
                    BluetoothPeripheral bluetoothPeripheral = this.this$0;
                    bluetoothPeripheral.bluetoothGattCallback.onConnectionStateChange(bluetoothPeripheral.bluetoothGatt, HciStatus.CONNECTION_FAILED_ESTABLISHMENT.getValue(), 0);
                }
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cancelConnectionTimer() {
        Job job = this.timeoutJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        this.timeoutJob = null;
    }

    private final int getTimeoutThreshold() {
        if (StringsKt.equals(Build.MANUFACTURER, "samsung", true)) {
            return TIMEOUT_THRESHOLD_SAMSUNG;
        }
        return 25000;
    }

    private final byte[] copyOf(byte[] source) {
        if (source == null) {
            return new byte[0];
        }
        byte[] bArrCopyOf = Arrays.copyOf(source, source.length);
        Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(source, source.size)");
        return bArrCopyOf;
    }
}
