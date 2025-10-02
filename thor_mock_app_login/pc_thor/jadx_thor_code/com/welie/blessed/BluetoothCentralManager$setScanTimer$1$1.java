package com.welie.blessed;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: BluetoothCentralManager.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "com.welie.blessed.BluetoothCentralManager$setScanTimer$1$1", f = "BluetoothCentralManager.kt", i = {}, l = {824}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class BluetoothCentralManager$setScanTimer$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ScanCallback $callback;
    final /* synthetic */ List<ScanFilter> $filters;
    int label;
    final /* synthetic */ BluetoothCentralManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    BluetoothCentralManager$setScanTimer$1$1(ScanCallback scanCallback, BluetoothCentralManager bluetoothCentralManager, List<ScanFilter> list, Continuation<? super BluetoothCentralManager$setScanTimer$1$1> continuation) {
        super(2, continuation);
        this.$callback = scanCallback;
        this.this$0 = bluetoothCentralManager;
        this.$filters = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new BluetoothCentralManager$setScanTimer$1$1(this.$callback, this.this$0, this.$filters, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((BluetoothCentralManager$setScanTimer$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
        if (this.$callback != null) {
            BluetoothCentralManager bluetoothCentralManager = this.this$0;
            List<ScanFilter> list = this.$filters;
            Intrinsics.checkNotNull(list);
            bluetoothCentralManager.startScan(list, this.this$0.scanSettings, this.$callback);
        }
        return Unit.INSTANCE;
    }
}
