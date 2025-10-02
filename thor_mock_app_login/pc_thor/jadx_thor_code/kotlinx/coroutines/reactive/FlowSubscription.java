package kotlinx.coroutines.reactive;

import androidx.exifinterface.media.ExifInterface;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.Volatile;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.intrinsics.CancellableKt;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* compiled from: ReactiveFlow.kt */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0000\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u00022\b\u0012\u0004\u0012\u00020\u00040\u0003B+\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0000\u0012\u00028\u00000\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\b\u0010\u0013\u001a\u00020\u0004H\u0016J\u0011\u0010\u0014\u001a\u00020\u0004H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0015J\u000e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u0010H\u0002J\u0011\u0010\u0017\u001a\u00020\u0004H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0015J\u0010\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u001aH\u0016R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00100\u000fX\u0082\u0004R\t\u0010\u0011\u001a\u00020\u0012X\u0082\u0004R\u0018\u0010\u0007\u001a\n\u0012\u0006\b\u0000\u0012\u00028\u00000\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001b"}, d2 = {"Lkotlinx/coroutines/reactive/FlowSubscription;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/reactivestreams/Subscription;", "Lkotlinx/coroutines/AbstractCoroutine;", "", "flow", "Lkotlinx/coroutines/flow/Flow;", "subscriber", "Lorg/reactivestreams/Subscriber;", "context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlinx/coroutines/flow/Flow;Lorg/reactivestreams/Subscriber;Lkotlin/coroutines/CoroutineContext;)V", "cancellationRequested", "", "producer", "Lkotlinx/atomicfu/AtomicRef;", "Lkotlin/coroutines/Continuation;", "requested", "Lkotlinx/atomicfu/AtomicLong;", "cancel", "consumeFlow", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createInitialContinuation", "flowProcessing", "request", "n", "", "kotlinx-coroutines-reactive"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class FlowSubscription<T> extends AbstractCoroutine<Unit> implements Subscription {
    private volatile boolean cancellationRequested;
    public final Flow<T> flow;

    @Volatile
    private volatile Object producer;

    @Volatile
    private volatile long requested;
    public final Subscriber<? super T> subscriber;
    private static final AtomicLongFieldUpdater requested$FU = AtomicLongFieldUpdater.newUpdater(FlowSubscription.class, "requested");
    private static final AtomicReferenceFieldUpdater producer$FU = AtomicReferenceFieldUpdater.newUpdater(FlowSubscription.class, Object.class, "producer");

    /* compiled from: ReactiveFlow.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.reactive.FlowSubscription", f = "ReactiveFlow.kt", i = {0}, l = {209}, m = "flowProcessing", n = {"this"}, s = {"L$0"})
    /* renamed from: kotlinx.coroutines.reactive.FlowSubscription$flowProcessing$1, reason: invalid class name */
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ FlowSubscription<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(FlowSubscription<T> flowSubscription, Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
            this.this$0 = flowSubscription;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.flowProcessing(this);
        }
    }

    private final long getAndUpdate$atomicfu(AtomicLongFieldUpdater atomicLongFieldUpdater, Function1<? super Long, Long> function1, Object obj) {
        long j;
        do {
            j = atomicLongFieldUpdater.get(obj);
        } while (!atomicLongFieldUpdater.compareAndSet(obj, j, function1.invoke(Long.valueOf(j)).longValue()));
        return j;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FlowSubscription(Flow<? extends T> flow, Subscriber<? super T> subscriber, CoroutineContext coroutineContext) {
        super(coroutineContext, false, true);
        this.flow = flow;
        this.subscriber = subscriber;
        this.producer = createInitialContinuation();
    }

    private final Continuation<Unit> createInitialContinuation() {
        final CoroutineContext coroutineContext = getCoroutineContext();
        return new Continuation<Unit>() { // from class: kotlinx.coroutines.reactive.FlowSubscription$createInitialContinuation$$inlined$Continuation$1
            @Override // kotlin.coroutines.Continuation
            /* renamed from: getContext, reason: from getter */
            public CoroutineContext get$context() {
                return coroutineContext;
            }

            @Override // kotlin.coroutines.Continuation
            public void resumeWith(Object result) throws Throwable {
                CancellableKt.startCoroutineCancellable(new FlowSubscription$createInitialContinuation$1$1(this), this);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0076 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object flowProcessing(kotlin.coroutines.Continuation<? super kotlin.Unit> r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof kotlinx.coroutines.reactive.FlowSubscription.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r5
            kotlinx.coroutines.reactive.FlowSubscription$flowProcessing$1 r0 = (kotlinx.coroutines.reactive.FlowSubscription.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r5 = r0.label
            int r5 = r5 - r2
            r0.label = r5
            goto L19
        L14:
            kotlinx.coroutines.reactive.FlowSubscription$flowProcessing$1 r0 = new kotlinx.coroutines.reactive.FlowSubscription$flowProcessing$1
            r0.<init>(r4, r5)
        L19:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L38
            if (r2 != r3) goto L30
            java.lang.Object r0 = r0.L$0
            kotlinx.coroutines.reactive.FlowSubscription r0 = (kotlinx.coroutines.reactive.FlowSubscription) r0
            kotlin.ResultKt.throwOnFailure(r5)     // Catch: java.lang.Throwable -> L2e
            goto L47
        L2e:
            r5 = move-exception
            goto L5a
        L30:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r0)
            throw r5
        L38:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.L$0 = r4     // Catch: java.lang.Throwable -> L58
            r0.label = r3     // Catch: java.lang.Throwable -> L58
            java.lang.Object r5 = r4.consumeFlow(r0)     // Catch: java.lang.Throwable -> L58
            if (r5 != r1) goto L46
            return r1
        L46:
            r0 = r4
        L47:
            org.reactivestreams.Subscriber<? super T> r5 = r0.subscriber     // Catch: java.lang.Throwable -> L4d
            r5.onComplete()     // Catch: java.lang.Throwable -> L4d
            goto L55
        L4d:
            r5 = move-exception
            kotlin.coroutines.CoroutineContext r0 = r0.getCoroutineContext()
            kotlinx.coroutines.CoroutineExceptionHandlerKt.handleCoroutineException(r0, r5)
        L55:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L58:
            r5 = move-exception
            r0 = r4
        L5a:
            boolean r1 = kotlinx.coroutines.DebugKt.getRECOVER_STACK_TRACES()
            if (r1 != 0) goto L62
            r1 = r5
            goto L66
        L62:
            java.lang.Throwable r1 = kotlinx.coroutines.internal.StackTraceRecoveryKt.unwrapImpl(r5)
        L66:
            boolean r2 = r0.cancellationRequested
            if (r2 == 0) goto L76
            boolean r2 = r0.isActive()
            if (r2 != 0) goto L76
            java.util.concurrent.CancellationException r2 = r0.getCancellationException()
            if (r1 == r2) goto L87
        L76:
            org.reactivestreams.Subscriber<? super T> r1 = r0.subscriber     // Catch: java.lang.Throwable -> L7c
            r1.onError(r5)     // Catch: java.lang.Throwable -> L7c
            goto L87
        L7c:
            r1 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r5, r1)
            kotlin.coroutines.CoroutineContext r0 = r0.getCoroutineContext()
            kotlinx.coroutines.CoroutineExceptionHandlerKt.handleCoroutineException(r0, r5)
        L87:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.reactive.FlowSubscription.flowProcessing(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object consumeFlow(Continuation<? super Unit> continuation) {
        Object objCollect = this.flow.collect(new FlowCollector(this) { // from class: kotlinx.coroutines.reactive.FlowSubscription.consumeFlow.2
            final /* synthetic */ FlowSubscription<T> this$0;

            {
                this.this$0 = this;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(T t, Continuation<? super Unit> continuation2) {
                this.this$0.subscriber.onNext(t);
                if (FlowSubscription.requested$FU.decrementAndGet(this.this$0) <= 0) {
                    FlowSubscription<T> flowSubscription = this.this$0;
                    CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation2), 1);
                    cancellableContinuationImpl.initCancellability();
                    FlowSubscription.producer$FU.set(flowSubscription, cancellableContinuationImpl);
                    Object result = cancellableContinuationImpl.getResult();
                    if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        DebugProbesKt.probeCoroutineSuspended(continuation2);
                    }
                    return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
                }
                JobKt.ensureActive(this.this$0.getCoroutineContext());
                return Unit.INSTANCE;
            }
        }, continuation);
        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.JobSupport, kotlinx.coroutines.Job
    public void cancel() {
        this.cancellationRequested = true;
        cancel((CancellationException) null);
    }

    @Override // org.reactivestreams.Subscription
    public void request(long n) {
        long j;
        long j2;
        Continuation continuation;
        if (n <= 0) {
            return;
        }
        AtomicLongFieldUpdater atomicLongFieldUpdater = requested$FU;
        do {
            j = atomicLongFieldUpdater.get(this);
            j2 = j + n;
            if (j2 <= 0) {
                j2 = Long.MAX_VALUE;
            }
        } while (!atomicLongFieldUpdater.compareAndSet(this, j, j2));
        if (j <= 0) {
            do {
                continuation = (Continuation) producer$FU.getAndSet(this, null);
            } while (continuation == null);
            Result.Companion companion = Result.INSTANCE;
            continuation.resumeWith(Result.m624constructorimpl(Unit.INSTANCE));
        }
    }
}
