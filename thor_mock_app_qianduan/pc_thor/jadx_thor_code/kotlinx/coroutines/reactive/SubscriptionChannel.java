package kotlinx.coroutines.reactive;

import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.Volatile;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.channels.BufferedChannel;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* compiled from: Channel.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\b\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0015\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0014J\b\u0010\u0015\u001a\u00020\rH\u0016J\b\u0010\u0016\u001a\u00020\rH\u0016J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u000bH\u0016R\t\u0010\u0007\u001a\u00020\bX\u0082\u0004R\u0011\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lkotlinx/coroutines/reactive/SubscriptionChannel;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/BufferedChannel;", "Lorg/reactivestreams/Subscriber;", "request", "", "(I)V", "_requested", "Lkotlinx/atomicfu/AtomicInt;", "_subscription", "Lkotlinx/atomicfu/AtomicRef;", "Lorg/reactivestreams/Subscription;", "onClosedIdempotent", "", "onComplete", "onError", "e", "", "onNext", "t", "(Ljava/lang/Object;)V", "onReceiveDequeued", "onReceiveEnqueued", "onSubscribe", "s", "kotlinx-coroutines-reactive"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
final class SubscriptionChannel<T> extends BufferedChannel<T> implements Subscriber<T> {

    @Volatile
    private volatile int _requested;

    @Volatile
    private volatile Object _subscription;
    private final int request;
    private static final AtomicReferenceFieldUpdater _subscription$FU = AtomicReferenceFieldUpdater.newUpdater(SubscriptionChannel.class, Object.class, "_subscription");
    private static final AtomicIntegerFieldUpdater _requested$FU = AtomicIntegerFieldUpdater.newUpdater(SubscriptionChannel.class, "_requested");

    private final void loop$atomicfu(AtomicIntegerFieldUpdater atomicIntegerFieldUpdater, Function1<? super Integer, Unit> function1, Object obj) {
        while (true) {
            function1.invoke(Integer.valueOf(atomicIntegerFieldUpdater.get(obj)));
        }
    }

    public SubscriptionChannel(int i) {
        super(Integer.MAX_VALUE, null, 2, null);
        this.request = i;
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("Invalid request size: " + i).toString());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0021, code lost:
    
        r2.request(r6.request - r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0028, code lost:
    
        return;
     */
    @Override // kotlinx.coroutines.channels.BufferedChannel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onReceiveEnqueued() {
        /*
            r6 = this;
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r0 = kotlinx.coroutines.reactive.SubscriptionChannel._requested$FU
        L2:
            int r1 = r0.get(r6)
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r2 = kotlinx.coroutines.reactive.SubscriptionChannel._subscription$FU
            java.lang.Object r2 = r2.get(r6)
            org.reactivestreams.Subscription r2 = (org.reactivestreams.Subscription) r2
            int r3 = r1 + (-1)
            if (r2 == 0) goto L29
            if (r3 >= 0) goto L29
            int r4 = r6.request
            if (r1 == r4) goto L21
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r5 = kotlinx.coroutines.reactive.SubscriptionChannel._requested$FU
            boolean r1 = r5.compareAndSet(r6, r1, r4)
            if (r1 != 0) goto L21
            goto L2
        L21:
            int r0 = r6.request
            int r0 = r0 - r3
            long r0 = (long) r0
            r2.request(r0)
            return
        L29:
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r2 = kotlinx.coroutines.reactive.SubscriptionChannel._requested$FU
            boolean r1 = r2.compareAndSet(r6, r1, r3)
            if (r1 == 0) goto L2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.reactive.SubscriptionChannel.onReceiveEnqueued():void");
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel
    public void onReceiveDequeued() {
        _requested$FU.incrementAndGet(this);
    }

    @Override // kotlinx.coroutines.channels.BufferedChannel
    public void onClosedIdempotent() {
        Subscription subscription = (Subscription) _subscription$FU.getAndSet(this, null);
        if (subscription != null) {
            subscription.cancel();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(Subscription s) {
        _subscription$FU.set(this, s);
        while (!isClosedForSend()) {
            AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = _requested$FU;
            int i = atomicIntegerFieldUpdater.get(this);
            int i2 = this.request;
            if (i >= i2) {
                return;
            }
            if (atomicIntegerFieldUpdater.compareAndSet(this, i, i2)) {
                s.request(this.request - i);
                return;
            }
        }
        s.cancel();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        _requested$FU.decrementAndGet(this);
        mo2127trySendJP2dKIU(t);
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        close(null);
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable e) {
        close(e);
    }
}
