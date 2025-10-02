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

/* compiled from: BluetoothPeripheral.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "com.welie.blessed.BluetoothPeripheral$nextCommand$1$1", f = "BluetoothPeripheral.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class BluetoothPeripheral$nextCommand$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Runnable $bluetoothCommand;
    int label;
    final /* synthetic */ BluetoothPeripheral this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    BluetoothPeripheral$nextCommand$1$1(Runnable runnable, BluetoothPeripheral bluetoothPeripheral, Continuation<? super BluetoothPeripheral$nextCommand$1$1> continuation) {
        super(2, continuation);
        this.$bluetoothCommand = runnable;
        this.this$0 = bluetoothPeripheral;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new BluetoothPeripheral$nextCommand$1$1(this.$bluetoothCommand, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((BluetoothPeripheral$nextCommand$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            this.$bluetoothCommand.run();
        } catch (Exception e) {
            Logger.INSTANCE.e(BluetoothPeripheral.TAG, "command exception for device '%s'", this.this$0.getName());
            Logger.INSTANCE.e(BluetoothPeripheral.TAG, e.toString());
            this.this$0.completedCommand();
        }
        return Unit.INSTANCE;
    }
}
