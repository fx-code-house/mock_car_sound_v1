package com.welie.blessed;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: BluetoothCentralManager.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "com.welie.blessed.BluetoothCentralManager$internalCallback$1$disconnected$2", f = "BluetoothCentralManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class BluetoothCentralManager$internalCallback$1$disconnected$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ BluetoothPeripheral $peripheral;
    int label;
    final /* synthetic */ BluetoothCentralManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    BluetoothCentralManager$internalCallback$1$disconnected$2(BluetoothCentralManager bluetoothCentralManager, BluetoothPeripheral bluetoothPeripheral, Continuation<? super BluetoothCentralManager$internalCallback$1$disconnected$2> continuation) {
        super(2, continuation);
        this.this$0 = bluetoothCentralManager;
        this.$peripheral = bluetoothPeripheral;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new BluetoothCentralManager$internalCallback$1$disconnected$2(this.this$0, this.$peripheral, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((BluetoothCentralManager$internalCallback$1$disconnected$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.connectionStateCallback.invoke(this.$peripheral, ConnectionState.DISCONNECTED);
        return Unit.INSTANCE;
    }
}
