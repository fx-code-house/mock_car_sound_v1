package kotlinx.coroutines.rx2;

import io.reactivex.CompletableEmitter;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.AbstractCoroutine;

/* compiled from: RxCompletable.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\u0015\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u0002H\u0014¢\u0006\u0002\u0010\u000fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lkotlinx/coroutines/rx2/RxCompletableCoroutine;", "Lkotlinx/coroutines/AbstractCoroutine;", "", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "subscriber", "Lio/reactivex/CompletableEmitter;", "(Lkotlin/coroutines/CoroutineContext;Lio/reactivex/CompletableEmitter;)V", "onCancelled", "cause", "", "handled", "", "onCompleted", "value", "(Lkotlin/Unit;)V", "kotlinx-coroutines-rx2"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
final class RxCompletableCoroutine extends AbstractCoroutine<Unit> {
    private final CompletableEmitter subscriber;

    public RxCompletableCoroutine(CoroutineContext coroutineContext, CompletableEmitter completableEmitter) {
        super(coroutineContext, false, true);
        this.subscriber = completableEmitter;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlinx.coroutines.AbstractCoroutine
    public void onCompleted(Unit value) {
        try {
            this.subscriber.onComplete();
        } catch (Throwable th) {
            RxCancellableKt.handleUndeliverableException(th, get$context());
        }
    }

    @Override // kotlinx.coroutines.AbstractCoroutine
    protected void onCancelled(Throwable cause, boolean handled) {
        try {
            if (this.subscriber.tryOnError(cause)) {
                return;
            }
        } catch (Throwable th) {
            ExceptionsKt.addSuppressed(cause, th);
        }
        RxCancellableKt.handleUndeliverableException(cause, get$context());
    }
}
