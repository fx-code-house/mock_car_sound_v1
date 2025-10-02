package kotlinx.coroutines.rx2;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: RxScheduler.kt */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001c\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\u0010\f\u001a\u00060\rj\u0002`\u000eH\u0016J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J$\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\n\u0010\f\u001a\u00060\rj\u0002`\u000e2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001e\u0010\u0019\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u001e"}, d2 = {"Lkotlinx/coroutines/rx2/SchedulerCoroutineDispatcher;", "Lkotlinx/coroutines/CoroutineDispatcher;", "Lkotlinx/coroutines/Delay;", "scheduler", "Lio/reactivex/Scheduler;", "(Lio/reactivex/Scheduler;)V", "getScheduler", "()Lio/reactivex/Scheduler;", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "equals", "", "other", "", "hashCode", "", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "toString", "", "kotlinx-coroutines-rx2"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SchedulerCoroutineDispatcher extends CoroutineDispatcher implements Delay {
    private final Scheduler scheduler;

    @Override // kotlinx.coroutines.Delay
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
    public Object delay(long j, Continuation<? super Unit> continuation) {
        return Delay.DefaultImpls.delay(this, j, continuation);
    }

    public final Scheduler getScheduler() {
        return this.scheduler;
    }

    public SchedulerCoroutineDispatcher(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    /* renamed from: dispatch */
    public void mo2182dispatch(CoroutineContext context, Runnable block) {
        this.scheduler.scheduleDirect(block);
    }

    @Override // kotlinx.coroutines.Delay
    /* renamed from: scheduleResumeAfterDelay */
    public void mo2183scheduleResumeAfterDelay(long timeMillis, final CancellableContinuation<? super Unit> continuation) {
        RxAwaitKt.disposeOnCancellation(continuation, this.scheduler.scheduleDirect(new Runnable() { // from class: kotlinx.coroutines.rx2.SchedulerCoroutineDispatcher$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SchedulerCoroutineDispatcher.scheduleResumeAfterDelay$lambda$1(continuation, this);
            }
        }, timeMillis, TimeUnit.MILLISECONDS));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scheduleResumeAfterDelay$lambda$1(CancellableContinuation cancellableContinuation, SchedulerCoroutineDispatcher schedulerCoroutineDispatcher) {
        cancellableContinuation.resumeUndispatched(schedulerCoroutineDispatcher, Unit.INSTANCE);
    }

    @Override // kotlinx.coroutines.Delay
    public DisposableHandle invokeOnTimeout(long timeMillis, Runnable block, CoroutineContext context) {
        final Disposable disposableScheduleDirect = this.scheduler.scheduleDirect(block, timeMillis, TimeUnit.MILLISECONDS);
        return new DisposableHandle() { // from class: kotlinx.coroutines.rx2.SchedulerCoroutineDispatcher$$ExternalSyntheticLambda0
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                disposableScheduleDirect.dispose();
            }
        };
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public String toString() {
        return this.scheduler.toString();
    }

    public boolean equals(Object other) {
        return (other instanceof SchedulerCoroutineDispatcher) && ((SchedulerCoroutineDispatcher) other).scheduler == this.scheduler;
    }

    public int hashCode() {
        return System.identityHashCode(this.scheduler);
    }
}
