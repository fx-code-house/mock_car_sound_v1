package kotlinx.coroutines.rx2;

import androidx.exifinterface.media.ExifInterface;
import io.reactivex.SingleEmitter;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.AbstractCoroutine;

/* compiled from: RxSingle.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0014J\u0015\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u0011R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lkotlinx/coroutines/rx2/RxSingleCoroutine;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lkotlinx/coroutines/AbstractCoroutine;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "subscriber", "Lio/reactivex/SingleEmitter;", "(Lkotlin/coroutines/CoroutineContext;Lio/reactivex/SingleEmitter;)V", "onCancelled", "", "cause", "", "handled", "", "onCompleted", "value", "(Ljava/lang/Object;)V", "kotlinx-coroutines-rx2"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
final class RxSingleCoroutine<T> extends AbstractCoroutine<T> {
    private final SingleEmitter<T> subscriber;

    public RxSingleCoroutine(CoroutineContext coroutineContext, SingleEmitter<T> singleEmitter) {
        super(coroutineContext, false, true);
        this.subscriber = singleEmitter;
    }

    @Override // kotlinx.coroutines.AbstractCoroutine
    protected void onCompleted(T value) {
        try {
            this.subscriber.onSuccess(value);
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
