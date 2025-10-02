package kotlinx.coroutines.reactive;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.channels.SendChannel;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* compiled from: ReactiveFlow.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\b\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u0010J\b\u0010\u0012\u001a\u00020\u0010H\u0016J\u0012\u0010\u0013\u001a\u00020\u00102\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\u0015\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0018J\u0010\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u000eH\u0016J\u0013\u0010\u001b\u001a\u0004\u0018\u00018\u0000H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u001cR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, d2 = {"Lkotlinx/coroutines/reactive/ReactiveSubscriber;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/reactivestreams/Subscriber;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "requestSize", "", "(ILkotlinx/coroutines/channels/BufferOverflow;J)V", "channel", "Lkotlinx/coroutines/channels/Channel;", "subscription", "Lorg/reactivestreams/Subscription;", "cancel", "", "makeRequest", "onComplete", "onError", "t", "", "onNext", "value", "(Ljava/lang/Object;)V", "onSubscribe", "s", "takeNextOrNull", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-reactive"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
final class ReactiveSubscriber<T> implements Subscriber<T> {
    private final Channel<T> channel;
    private final long requestSize;
    private Subscription subscription;

    /* compiled from: ReactiveFlow.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.reactive.ReactiveSubscriber", f = "ReactiveFlow.kt", i = {}, l = {129}, m = "takeNextOrNull", n = {}, s = {})
    /* renamed from: kotlinx.coroutines.reactive.ReactiveSubscriber$takeNextOrNull$1, reason: invalid class name */
    static final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ ReactiveSubscriber<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(ReactiveSubscriber<T> reactiveSubscriber, Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
            this.this$0 = reactiveSubscriber;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.takeNextOrNull(this);
        }
    }

    public ReactiveSubscriber(int i, BufferOverflow bufferOverflow, long j) {
        this.requestSize = j;
        this.channel = kotlinx.coroutines.channels.ChannelKt.Channel$default(i == 0 ? 1 : i, bufferOverflow, null, 4, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object takeNextOrNull(kotlin.coroutines.Continuation<? super T> r5) throws java.lang.Throwable {
        /*
            r4 = this;
            boolean r0 = r5 instanceof kotlinx.coroutines.reactive.ReactiveSubscriber.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r5
            kotlinx.coroutines.reactive.ReactiveSubscriber$takeNextOrNull$1 r0 = (kotlinx.coroutines.reactive.ReactiveSubscriber.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r5 = r0.label
            int r5 = r5 - r2
            r0.label = r5
            goto L19
        L14:
            kotlinx.coroutines.reactive.ReactiveSubscriber$takeNextOrNull$1 r0 = new kotlinx.coroutines.reactive.ReactiveSubscriber$takeNextOrNull$1
            r0.<init>(r4, r5)
        L19:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L38
            if (r2 != r3) goto L30
            kotlin.ResultKt.throwOnFailure(r5)
            kotlinx.coroutines.channels.ChannelResult r5 = (kotlinx.coroutines.channels.ChannelResult) r5
            java.lang.Object r5 = r5.getHolder()
            goto L46
        L30:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r0)
            throw r5
        L38:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlinx.coroutines.channels.Channel<T> r5 = r4.channel
            r0.label = r3
            java.lang.Object r5 = r5.mo2131receiveCatchingJP2dKIU(r0)
            if (r5 != r1) goto L46
            return r1
        L46:
            java.lang.Throwable r0 = kotlinx.coroutines.channels.ChannelResult.m2141exceptionOrNullimpl(r5)
            if (r0 != 0) goto L55
            boolean r0 = r5 instanceof kotlinx.coroutines.channels.ChannelResult.Failed
            if (r0 == 0) goto L54
            kotlinx.coroutines.channels.ChannelResult.m2141exceptionOrNullimpl(r5)
            r5 = 0
        L54:
            return r5
        L55:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.reactive.ReactiveSubscriber.takeNextOrNull(kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T value) {
        if (!ChannelResult.m2147isSuccessimpl(this.channel.mo2127trySendJP2dKIU(value))) {
            throw new IllegalArgumentException(("Element " + value + " was not added to channel because it was full, " + this.channel).toString());
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        SendChannel.DefaultImpls.close$default(this.channel, null, 1, null);
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable t) {
        this.channel.close(t);
    }

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        makeRequest();
    }

    public final void makeRequest() {
        Subscription subscription = this.subscription;
        if (subscription == null) {
            Intrinsics.throwUninitializedPropertyAccessException("subscription");
            subscription = null;
        }
        subscription.request(this.requestSize);
    }

    public final void cancel() {
        Subscription subscription = this.subscription;
        if (subscription == null) {
            Intrinsics.throwUninitializedPropertyAccessException("subscription");
            subscription = null;
        }
        subscription.cancel();
    }
}
