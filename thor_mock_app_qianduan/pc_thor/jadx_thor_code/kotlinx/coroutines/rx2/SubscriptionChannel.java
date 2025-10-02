package kotlinx.coroutines.rx2;

import androidx.exifinterface.media.ExifInterface;
import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.Volatile;
import kotlinx.coroutines.channels.BufferedChannel;

/* compiled from: RxChannel.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0007\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0012\u0004\u0012\u0002H\u00010\u0004B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\nH\u0016J\u0010\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001a\u0010\u000f\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\b\u00028\u0000H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u0011J\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\bH\u0016J\u001a\u0010\u0014\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\b\u00028\u0000H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u0011R\u0011\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007X\u0082\u0004\u0082\u0002\u0004\n\u0002\b9¨\u0006\u0015"}, d2 = {"Lkotlinx/coroutines/rx2/SubscriptionChannel;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/BufferedChannel;", "Lio/reactivex/Observer;", "Lio/reactivex/MaybeObserver;", "()V", "_subscription", "Lkotlinx/atomicfu/AtomicRef;", "Lio/reactivex/disposables/Disposable;", "onClosedIdempotent", "", "onComplete", "onError", "e", "", "onNext", "t", "(Ljava/lang/Object;)V", "onSubscribe", "sub", "onSuccess", "kotlinx-coroutines-rx2"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
final class SubscriptionChannel<T> extends BufferedChannel<T> implements Observer<T>, MaybeObserver<T> {
    private static final AtomicReferenceFieldUpdater _subscription$FU = AtomicReferenceFieldUpdater.newUpdater(SubscriptionChannel.class, Object.class, "_subscription");

    @Volatile
    private volatile Object _subscription;

    public SubscriptionChannel() {
        super(Integer.MAX_VALUE, null, 2, null);
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel
    public void onClosedIdempotent() {
        Disposable disposable = (Disposable) _subscription$FU.getAndSet(this, null);
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable sub) {
        _subscription$FU.set(this, sub);
    }

    @Override // io.reactivex.MaybeObserver
    public void onSuccess(T t) {
        mo2127trySendJP2dKIU(t);
        close(null);
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        mo2127trySendJP2dKIU(t);
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        close(null);
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable e) {
        close(e);
    }
}
