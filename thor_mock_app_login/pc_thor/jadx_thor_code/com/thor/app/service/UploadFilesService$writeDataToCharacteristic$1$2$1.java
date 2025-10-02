package com.thor.app.service;

import android.bluetooth.BluetoothGattCharacteristic;
import com.thor.app.service.state.UploadServiceState;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.businessmodule.bluetooth.request.other.BaseBleRequest;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import com.welie.blessed.BluetoothPeripheral;
import com.welie.blessed.ConnectionState;
import com.welie.blessed.GattException;
import com.welie.blessed.GattStatus;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: UploadFilesService.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "com.thor.app.service.UploadFilesService$writeDataToCharacteristic$1$2$1", f = "UploadFilesService.kt", i = {}, l = {362}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class UploadFilesService$writeDataToCharacteristic$1$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ BluetoothGattCharacteristic $characteristic;
    final /* synthetic */ byte[] $data;
    final /* synthetic */ BluetoothPeripheral $peripheral;
    final /* synthetic */ BaseBleRequest $request;
    int label;
    final /* synthetic */ UploadFilesService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UploadFilesService$writeDataToCharacteristic$1$2$1(UploadFilesService uploadFilesService, BaseBleRequest baseBleRequest, BluetoothPeripheral bluetoothPeripheral, BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr, Continuation<? super UploadFilesService$writeDataToCharacteristic$1$2$1> continuation) {
        super(2, continuation);
        this.this$0 = uploadFilesService;
        this.$request = baseBleRequest;
        this.$peripheral = bluetoothPeripheral;
        this.$characteristic = bluetoothGattCharacteristic;
        this.$data = bArr;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new UploadFilesService$writeDataToCharacteristic$1$2$1(this.this$0, this.$request, this.$peripheral, this.$characteristic, this.$data, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((UploadFilesService$writeDataToCharacteristic$1$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.this$0.connectionState == ConnectionState.CONNECTED) {
                    Short command = this.$request.getCommand();
                    if (!(command != null && command.shortValue() == 114)) {
                        FileLogger fileLogger = this.this$0.getFileLogger();
                        byte[] bArr = this.$request.getDeсryptoMessage();
                        fileLogger.i("SERVICE: Write data " + (bArr != null ? BleHelperKt.toHex(bArr) : null), new Object[0]);
                    }
                    this.this$0.waitResponse(this.$request);
                    this.label = 1;
                    if (this.this$0.writeData(this.$peripheral, this.$characteristic, this.$data, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
        } catch (GattException e) {
            this.this$0.getFileLogger().i("SERVICE: Error write data GattException " + e.getMessage(), new Object[0]);
            if (e.getStatus() == GattStatus.PREPARE_QUEUE_FULL) {
                this.this$0.isNordic = false;
                this.this$0.lastWriteCommand = this.$request;
                this.this$0.tryStartInitialHandShake();
            }
        } catch (IllegalArgumentException e2) {
            this.this$0.getFileLogger().i("SERVICE: Error write data GattException " + e2.getMessage(), new Object[0]);
            this.this$0.isNordic = false;
            this.this$0.tryStartInitialHandShake();
        } catch (Exception e3) {
            this.this$0.stop(UploadServiceState.Stop.ForceStop.INSTANCE);
            this.this$0.getFileLogger().i("SERVICE: Error write data " + e3.getMessage(), new Object[0]);
        }
        return Unit.INSTANCE;
    }
}
