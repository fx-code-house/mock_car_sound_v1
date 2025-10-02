package com.thor.app.service;

import com.welie.blessed.BluetoothPeripheral;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: UploadFilesService.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "com.thor.app.service.UploadFilesService$startObservingRxCharacteristic$1$1$1", f = "UploadFilesService.kt", i = {}, l = {447, 449, 450}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class UploadFilesService$startObservingRxCharacteristic$1$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ BluetoothPeripheral $peripheral;
    int label;
    final /* synthetic */ UploadFilesService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UploadFilesService$startObservingRxCharacteristic$1$1$1(BluetoothPeripheral bluetoothPeripheral, UploadFilesService uploadFilesService, Continuation<? super UploadFilesService$startObservingRxCharacteristic$1$1$1> continuation) {
        super(2, continuation);
        this.$peripheral = bluetoothPeripheral;
        this.this$0 = uploadFilesService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new UploadFilesService$startObservingRxCharacteristic$1$1$1(this.$peripheral, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((UploadFilesService$startObservingRxCharacteristic$1$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0043 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0055 A[RETURN] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0053 -> B:15:0x0034). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r6.label
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L22
            if (r1 == r4) goto L11
            if (r1 == r3) goto L1d
            if (r1 != r2) goto L15
        L11:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L33
        L15:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L1d:
            kotlin.ResultKt.throwOnFailure(r7)
            r7 = r6
            goto L44
        L22:
            kotlin.ResultKt.throwOnFailure(r7)
            r7 = r6
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r6.label = r4
            r4 = 20000(0x4e20, double:9.8813E-320)
            java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r4, r7)
            if (r7 != r0) goto L33
            return r0
        L33:
            r7 = r6
        L34:
            com.welie.blessed.BluetoothPeripheral r1 = r7.$peripheral
            com.welie.blessed.ConnectionPriority r4 = com.welie.blessed.ConnectionPriority.HIGH
            r5 = r7
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r7.label = r3
            java.lang.Object r1 = r1.requestConnectionPriority(r4, r5)
            if (r1 != r0) goto L44
            return r0
        L44:
            com.thor.app.service.UploadFilesService r1 = r7.this$0
            long r4 = com.thor.app.service.UploadFilesService.access$getPriorityTimeRequest$p(r1)
            r1 = r7
            kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
            r7.label = r2
            java.lang.Object r1 = kotlinx.coroutines.DelayKt.delay(r4, r1)
            if (r1 != r0) goto L34
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.service.UploadFilesService$startObservingRxCharacteristic$1$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
