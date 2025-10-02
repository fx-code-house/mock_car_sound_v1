package kotlinx.coroutines.reactive;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlow;
import kotlinx.coroutines.flow.internal.SendingCollector;
import org.reactivestreams.Publisher;

/* compiled from: ReactiveFlow.kt */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B1\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u001f\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u0016H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0017J'\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u00072\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u0016H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u001aJ\u001f\u0010\u001b\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u0016H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0017J\u001f\u0010\u001c\u001a\u00020\u00142\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0094@ø\u0001\u0000¢\u0006\u0002\u0010\u001fJ&\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u000e8BX\u0082\u0004¢\u0006\f\u0012\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006!"}, d2 = {"Lkotlinx/coroutines/reactive/PublisherAsFlow;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "publisher", "Lorg/reactivestreams/Publisher;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lorg/reactivestreams/Publisher;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "requestSize", "", "getRequestSize$annotations", "()V", "getRequestSize", "()J", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectImpl", "injectContext", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectSlowPath", "collectTo", "scope", "Lkotlinx/coroutines/channels/ProducerScope;", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "create", "kotlinx-coroutines-reactive"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
final class PublisherAsFlow<T> extends ChannelFlow<T> {
    private final Publisher<T> publisher;

    /* compiled from: ReactiveFlow.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.reactive.PublisherAsFlow", f = "ReactiveFlow.kt", i = {0, 0, 0, 0, 1, 1, 1, 1}, l = {98, 100}, m = "collectImpl", n = {"this", "collector", "subscriber", "consumed", "this", "collector", "subscriber", "consumed"}, s = {"L$0", "L$1", "L$2", "J$0", "L$0", "L$1", "L$2", "J$0"})
    /* renamed from: kotlinx.coroutines.reactive.PublisherAsFlow$collectImpl$1, reason: invalid class name */
    static final class AnonymousClass1 extends ContinuationImpl {
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ PublisherAsFlow<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(PublisherAsFlow<T> publisherAsFlow, Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
            this.this$0 = publisherAsFlow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.collectImpl(null, null, this);
        }
    }

    private static /* synthetic */ void getRequestSize$annotations() {
    }

    public /* synthetic */ PublisherAsFlow(Publisher publisher, EmptyCoroutineContext emptyCoroutineContext, int i, BufferOverflow bufferOverflow, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(publisher, (i2 & 2) != 0 ? EmptyCoroutineContext.INSTANCE : emptyCoroutineContext, (i2 & 4) != 0 ? -2 : i, (i2 & 8) != 0 ? BufferOverflow.SUSPEND : bufferOverflow);
    }

    public PublisherAsFlow(Publisher<T> publisher, CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        super(coroutineContext, i, bufferOverflow);
        this.publisher = publisher;
    }

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow
    protected ChannelFlow<T> create(CoroutineContext context, int capacity, BufferOverflow onBufferOverflow) {
        return new PublisherAsFlow(this.publisher, context, capacity, onBufferOverflow);
    }

    private final long getRequestSize() {
        if (this.onBufferOverflow != BufferOverflow.SUSPEND) {
            return Long.MAX_VALUE;
        }
        int i = this.capacity;
        if (i == -2) {
            return Channel.INSTANCE.getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core();
        }
        if (i == 0) {
            return 1L;
        }
        if (i == Integer.MAX_VALUE) {
            return Long.MAX_VALUE;
        }
        long j = this.capacity;
        if (j >= 1) {
            return j;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow, kotlinx.coroutines.flow.Flow
    public Object collect(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        CoroutineContext coroutineContext = continuation.get$context();
        ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) this.context.get(ContinuationInterceptor.INSTANCE);
        if (continuationInterceptor == null || Intrinsics.areEqual(continuationInterceptor, coroutineContext.get(ContinuationInterceptor.INSTANCE))) {
            Object objCollectImpl = collectImpl(coroutineContext.plus(this.context), flowCollector, continuation);
            return objCollectImpl == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollectImpl : Unit.INSTANCE;
        }
        Object objCollectSlowPath = collectSlowPath(flowCollector, continuation);
        return objCollectSlowPath == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollectSlowPath : Unit.INSTANCE;
    }

    /* compiled from: ReactiveFlow.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u0004H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.reactive.PublisherAsFlow$collectSlowPath$2", f = "ReactiveFlow.kt", i = {}, l = {87}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: kotlinx.coroutines.reactive.PublisherAsFlow$collectSlowPath$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ FlowCollector<T> $collector;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ PublisherAsFlow<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass2(FlowCollector<? super T> flowCollector, PublisherAsFlow<T> publisherAsFlow, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$collector = flowCollector;
            this.this$0 = publisherAsFlow;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$collector, this.this$0, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                FlowCollector<T> flowCollector = this.$collector;
                PublisherAsFlow<T> publisherAsFlow = this.this$0;
                this.label = 1;
                if (kotlinx.coroutines.flow.FlowKt.emitAll(flowCollector, publisherAsFlow.produceImpl(CoroutineScopeKt.plus(coroutineScope, publisherAsFlow.context)), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object collectSlowPath(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass2(flowCollector, this, null), continuation);
        return objCoroutineScope == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCoroutineScope : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0093 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a1 A[Catch: all -> 0x005d, TRY_ENTER, TryCatch #0 {all -> 0x005d, blocks: (B:13:0x003d, B:33:0x00b9, B:35:0x00c5, B:23:0x0083, B:30:0x00a1, B:18:0x0059), top: B:40:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00c5 A[Catch: all -> 0x005d, TRY_LEAVE, TryCatch #0 {all -> 0x005d, blocks: (B:13:0x003d, B:33:0x00b9, B:35:0x00c5, B:23:0x0083, B:30:0x00a1, B:18:0x0059), top: B:40:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0018  */
    /* JADX WARN: Type inference failed for: r11v0, types: [java.lang.Object, kotlinx.coroutines.flow.FlowCollector] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v9 */
    /* JADX WARN: Type inference failed for: r4v0, types: [int] */
    /* JADX WARN: Type inference failed for: r4v1, types: [kotlinx.coroutines.reactive.ReactiveSubscriber] */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v8, types: [kotlinx.coroutines.reactive.ReactiveSubscriber] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x00b6 -> B:14:0x0040). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object collectImpl(kotlin.coroutines.CoroutineContext r18, kotlinx.coroutines.flow.FlowCollector<? super T> r19, kotlin.coroutines.Continuation<? super kotlin.Unit> r20) {
        /*
            Method dump skipped, instructions count: 207
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.reactive.PublisherAsFlow.collectImpl(kotlin.coroutines.CoroutineContext, kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow
    protected Object collectTo(ProducerScope<? super T> producerScope, Continuation<? super Unit> continuation) {
        Object objCollectImpl = collectImpl(producerScope.getCoroutineContext(), new SendingCollector(producerScope.getChannel()), continuation);
        return objCollectImpl == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollectImpl : Unit.INSTANCE;
    }
}
