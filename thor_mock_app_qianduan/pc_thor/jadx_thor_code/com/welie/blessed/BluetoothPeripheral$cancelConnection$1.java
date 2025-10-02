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
import kotlinx.coroutines.DelayKt;

/* compiled from: BluetoothPeripheral.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$cancelConnection$1", f = "BluetoothPeripheral.kt", i = {}, l = {594}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class BluetoothPeripheral$cancelConnection$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ BluetoothPeripheral this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    BluetoothPeripheral$cancelConnection$1(BluetoothPeripheral bluetoothPeripheral, Continuation<? super BluetoothPeripheral$cancelConnection$1> continuation) {
        super(2, continuation);
        this.this$0 = bluetoothPeripheral;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new BluetoothPeripheral$cancelConnection$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((BluetoothPeripheral$cancelConnection$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
            bluetoothPeripheral.bluetoothGattCallback.onConnectionStateChange(bluetoothPeripheral.bluetoothGatt, HciStatus.SUCCESS.getValue(), 0);
        }
        return Unit.INSTANCE;
    }
}
