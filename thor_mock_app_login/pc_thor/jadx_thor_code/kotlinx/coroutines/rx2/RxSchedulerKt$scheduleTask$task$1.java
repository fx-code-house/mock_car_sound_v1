package kotlinx.coroutines.rx2;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: RxScheduler.kt */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "kotlinx.coroutines.rx2.RxSchedulerKt", f = "RxScheduler.kt", i = {0}, l = {126}, m = "scheduleTask$task", n = {"ctx"}, s = {"L$0"})
/* loaded from: classes3.dex */
final class RxSchedulerKt$scheduleTask$task$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;

    RxSchedulerKt$scheduleTask$task$1(Continuation<? super RxSchedulerKt$scheduleTask$task$1> continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return RxSchedulerKt.scheduleTask$task(null, null, null, this);
    }
}
