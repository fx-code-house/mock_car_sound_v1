package com.welie.blessed;

import android.bluetooth.BluetoothGattCharacteristic;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: BluetoothPeripheral.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$bluetoothGattCallback$1$onCharacteristicChanged$1", f = "BluetoothPeripheral.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class BluetoothPeripheral$bluetoothGattCallback$1$onCharacteristicChanged$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ BluetoothGattCharacteristic $characteristic;
    final /* synthetic */ byte[] $value;
    int label;
    final /* synthetic */ BluetoothPeripheral this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    BluetoothPeripheral$bluetoothGattCallback$1$onCharacteristicChanged$1(BluetoothPeripheral bluetoothPeripheral, BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr, Continuation<? super BluetoothPeripheral$bluetoothGattCallback$1$onCharacteristicChanged$1> continuation) {
        super(2, continuation);
        this.this$0 = bluetoothPeripheral;
        this.$characteristic = bluetoothGattCharacteristic;
        this.$value = bArr;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new BluetoothPeripheral$bluetoothGattCallback$1$onCharacteristicChanged$1(this.this$0, this.$characteristic, this.$value, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((BluetoothPeripheral$bluetoothGattCallback$1$onCharacteristicChanged$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Function1 function1 = (Function1) this.this$0.observeMap.get(this.$characteristic);
        if (function1 != null) {
            function1.invoke(this.$value);
        }
        return Unit.INSTANCE;
    }
}
