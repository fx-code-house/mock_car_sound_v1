package kotlinx.coroutines.reactive;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import org.reactivestreams.Publisher;

/* compiled from: Await.kt */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0018\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a!\u0010\t\u001a\u0002H\n\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000bH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\f\u001a)\u0010\r\u001a\u0002H\n\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000b2\u0006\u0010\u000e\u001a\u0002H\nH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a/\u0010\u0010\u001a\u0002H\n\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\n0\u0012H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001a#\u0010\u0014\u001a\u0004\u0018\u0001H\n\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000bH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\f\u001a!\u0010\u0015\u001a\u0002H\n\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000bH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\f\u001a5\u0010\u0016\u001a\u0002H\n\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000b2\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u0001H\nH\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001a!\u0010\u0018\u001a\u0002H\n\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000bH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\f\u001a)\u0010\u0019\u001a\u0002H\n\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000b2\u0006\u0010\u000e\u001a\u0002H\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a/\u0010\u001a\u001a\u0002H\n\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\n0\u0012H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001a#\u0010\u001b\u001a\u0004\u0018\u0001H\n\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000bH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\f\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001c"}, d2 = {"gotSignalInTerminalStateException", "", "context", "Lkotlin/coroutines/CoroutineContext;", "signalName", "", "moreThanOneValueProvidedException", "mode", "Lkotlinx/coroutines/reactive/Mode;", "awaitFirst", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/reactivestreams/Publisher;", "(Lorg/reactivestreams/Publisher;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "awaitFirstOrDefault", "default", "(Lorg/reactivestreams/Publisher;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "awaitFirstOrElse", "defaultValue", "Lkotlin/Function0;", "(Lorg/reactivestreams/Publisher;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "awaitFirstOrNull", "awaitLast", "awaitOne", "(Lorg/reactivestreams/Publisher;Lkotlinx/coroutines/reactive/Mode;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "awaitSingle", "awaitSingleOrDefault", "awaitSingleOrElse", "awaitSingleOrNull", "kotlinx-coroutines-reactive"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class AwaitKt {

    /* compiled from: Await.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.reactive.AwaitKt", f = "Await.kt", i = {0}, l = {56}, m = "awaitFirstOrElse", n = {"defaultValue"}, s = {"L$0"})
    /* renamed from: kotlinx.coroutines.reactive.AwaitKt$awaitFirstOrElse$1, reason: invalid class name */
    static final class AnonymousClass1<T> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AwaitKt.awaitFirstOrElse(null, null, this);
        }
    }

    /* compiled from: Await.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.reactive.AwaitKt", f = "Await.kt", i = {0}, l = {170}, m = "awaitSingleOrElse", n = {"defaultValue"}, s = {"L$0"})
    /* renamed from: kotlinx.coroutines.reactive.AwaitKt$awaitSingleOrElse$1, reason: invalid class name and case insensitive filesystem */
    static final class C05601<T> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C05601(Continuation<? super C05601> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AwaitKt.awaitSingleOrElse(null, null, this);
        }
    }

    public static final <T> Object awaitFirst(Publisher<T> publisher, Continuation<? super T> continuation) {
        return awaitOne$default(publisher, Mode.FIRST, null, continuation, 2, null);
    }

    public static final <T> Object awaitFirstOrDefault(Publisher<T> publisher, T t, Continuation<? super T> continuation) {
        return awaitOne(publisher, Mode.FIRST_OR_DEFAULT, t, continuation);
    }

    public static final <T> Object awaitFirstOrNull(Publisher<T> publisher, Continuation<? super T> continuation) {
        return awaitOne$default(publisher, Mode.FIRST_OR_DEFAULT, null, continuation, 2, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <T> java.lang.Object awaitFirstOrElse(org.reactivestreams.Publisher<T> r7, kotlin.jvm.functions.Function0<? extends T> r8, kotlin.coroutines.Continuation<? super T> r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.reactive.AwaitKt.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r9
            kotlinx.coroutines.reactive.AwaitKt$awaitFirstOrElse$1 r0 = (kotlinx.coroutines.reactive.AwaitKt.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L19
        L14:
            kotlinx.coroutines.reactive.AwaitKt$awaitFirstOrElse$1 r0 = new kotlinx.coroutines.reactive.AwaitKt$awaitFirstOrElse$1
            r0.<init>(r9)
        L19:
            r4 = r0
            java.lang.Object r9 = r4.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r4.label
            r2 = 1
            if (r1 == 0) goto L38
            if (r1 != r2) goto L30
            java.lang.Object r7 = r4.L$0
            r8 = r7
            kotlin.jvm.functions.Function0 r8 = (kotlin.jvm.functions.Function0) r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L4d
        L30:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L38:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlinx.coroutines.reactive.Mode r9 = kotlinx.coroutines.reactive.Mode.FIRST_OR_DEFAULT
            r3 = 0
            r5 = 2
            r6 = 0
            r4.L$0 = r8
            r4.label = r2
            r1 = r7
            r2 = r9
            java.lang.Object r9 = awaitOne$default(r1, r2, r3, r4, r5, r6)
            if (r9 != r0) goto L4d
            return r0
        L4d:
            if (r9 != 0) goto L53
            java.lang.Object r9 = r8.invoke()
        L53:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.reactive.AwaitKt.awaitFirstOrElse(org.reactivestreams.Publisher, kotlin.jvm.functions.Function0, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final <T> Object awaitLast(Publisher<T> publisher, Continuation<? super T> continuation) {
        return awaitOne$default(publisher, Mode.LAST, null, continuation, 2, null);
    }

    public static final <T> Object awaitSingle(Publisher<T> publisher, Continuation<? super T> continuation) {
        return awaitOne$default(publisher, Mode.SINGLE, null, continuation, 2, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Deprecated without a replacement due to its name incorrectly conveying the behavior. Please consider using awaitFirstOrElse().")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final /* synthetic */ java.lang.Object awaitSingleOrElse(org.reactivestreams.Publisher r7, kotlin.jvm.functions.Function0 r8, kotlin.coroutines.Continuation r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.reactive.AwaitKt.C05601
            if (r0 == 0) goto L14
            r0 = r9
            kotlinx.coroutines.reactive.AwaitKt$awaitSingleOrElse$1 r0 = (kotlinx.coroutines.reactive.AwaitKt.C05601) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L19
        L14:
            kotlinx.coroutines.reactive.AwaitKt$awaitSingleOrElse$1 r0 = new kotlinx.coroutines.reactive.AwaitKt$awaitSingleOrElse$1
            r0.<init>(r9)
        L19:
            r4 = r0
            java.lang.Object r9 = r4.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r4.label
            r2 = 1
            if (r1 == 0) goto L38
            if (r1 != r2) goto L30
            java.lang.Object r7 = r4.L$0
            r8 = r7
            kotlin.jvm.functions.Function0 r8 = (kotlin.jvm.functions.Function0) r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L4d
        L30:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L38:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlinx.coroutines.reactive.Mode r9 = kotlinx.coroutines.reactive.Mode.SINGLE_OR_DEFAULT
            r3 = 0
            r5 = 2
            r6 = 0
            r4.L$0 = r8
            r4.label = r2
            r1 = r7
            r2 = r9
            java.lang.Object r9 = awaitOne$default(r1, r2, r3, r4, r5, r6)
            if (r9 != r0) goto L4d
            return r0
        L4d:
            if (r9 != 0) goto L53
            java.lang.Object r9 = r8.invoke()
        L53:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.reactive.AwaitKt.awaitSingleOrElse(org.reactivestreams.Publisher, kotlin.jvm.functions.Function0, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Object awaitOne$default(Publisher publisher, Mode mode, Object obj, Continuation continuation, int i, Object obj2) {
        if ((i & 2) != 0) {
            obj = null;
        }
        return awaitOne(publisher, mode, obj, continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void gotSignalInTerminalStateException(CoroutineContext coroutineContext, String str) {
        CoroutineExceptionHandlerKt.handleCoroutineException(coroutineContext, new IllegalStateException("'" + str + "' was called after the publisher already signalled being in a terminal state"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void moreThanOneValueProvidedException(CoroutineContext coroutineContext, Mode mode) {
        CoroutineExceptionHandlerKt.handleCoroutineException(coroutineContext, new IllegalStateException("Only a single value was requested in '" + mode + "', but the publisher provided more"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> Object awaitOne(Publisher<T> publisher, Mode mode, T t, Continuation<? super T> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        ReactiveFlowKt.injectCoroutineContext(publisher, cancellableContinuationImpl2.getContext()).subscribe(new AwaitKt$awaitOne$2$1(cancellableContinuationImpl2, mode, t));
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }
}
