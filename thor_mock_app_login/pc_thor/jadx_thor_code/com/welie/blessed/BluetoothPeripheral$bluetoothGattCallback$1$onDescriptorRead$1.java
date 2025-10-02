package com.welie.blessed;

import android.bluetooth.BluetoothGattDescriptor;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: BluetoothPeripheral.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$bluetoothGattCallback$1$onDescriptorRead$1", f = "BluetoothPeripheral.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class BluetoothPeripheral$bluetoothGattCallback$1$onDescriptorRead$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ BluetoothGattDescriptor $descriptor;
    final /* synthetic */ GattStatus $gattStatus;
    final /* synthetic */ BluetoothPeripheralCallback $resultCallback;
    final /* synthetic */ byte[] $value;
    int label;
    final /* synthetic */ BluetoothPeripheral this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    BluetoothPeripheral$bluetoothGattCallback$1$onDescriptorRead$1(BluetoothPeripheralCallback bluetoothPeripheralCallback, BluetoothPeripheral bluetoothPeripheral, byte[] bArr, BluetoothGattDescriptor bluetoothGattDescriptor, GattStatus gattStatus, Continuation<? super BluetoothPeripheral$bluetoothGattCallback$1$onDescriptorRead$1> continuation) {
        super(2, continuation);
        this.$resultCallback = bluetoothPeripheralCallback;
        this.this$0 = bluetoothPeripheral;
        this.$value = bArr;
        this.$descriptor = bluetoothGattDescriptor;
        this.$gattStatus = gattStatus;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new BluetoothPeripheral$bluetoothGattCallback$1$onDescriptorRead$1(this.$resultCallback, this.this$0, this.$value, this.$descriptor, this.$gattStatus, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((BluetoothPeripheral$bluetoothGattCallback$1$onDescriptorRead$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$resultCallback.onDescriptorRead(this.this$0, this.$value, this.$descriptor, this.$gattStatus);
        return Unit.INSTANCE;
    }
}
