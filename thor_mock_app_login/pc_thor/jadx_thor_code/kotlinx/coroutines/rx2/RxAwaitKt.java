package kotlinx.coroutines.rx2;

import androidx.exifinterface.media.ExifInterface;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* compiled from: RxAwait.kt */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0003\u001a#\u0010\u0000\u001a\u0004\u0018\u0001H\u0004\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0005H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u001a!\u0010\u0000\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0007H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\b\u001a!\u0010\t\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\nH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a)\u0010\f\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\n2\u0006\u0010\r\u001a\u0002H\u0004H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000e\u001a/\u0010\u000f\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\n2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0011H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a#\u0010\u0013\u001a\u0004\u0018\u0001H\u0004\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\nH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a!\u0010\u0014\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\nH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a5\u0010\u0015\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\n2\u0006\u0010\u0016\u001a\u00020\u00172\n\b\u0002\u0010\r\u001a\u0004\u0018\u0001H\u0004H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0018\u001a)\u0010\u0019\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00052\u0006\u0010\r\u001a\u0002H\u0004H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u001a\u001a!\u0010\u001b\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0005H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u001a!\u0010\u001b\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\nH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a#\u0010\u001c\u001a\u0004\u0018\u0001H\u0004\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0005H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u001a\u0018\u0010\u001d\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006!"}, d2 = {"await", "", "Lio/reactivex/CompletableSource;", "(Lio/reactivex/CompletableSource;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", ExifInterface.GPS_DIRECTION_TRUE, "Lio/reactivex/MaybeSource;", "(Lio/reactivex/MaybeSource;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lio/reactivex/SingleSource;", "(Lio/reactivex/SingleSource;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "awaitFirst", "Lio/reactivex/ObservableSource;", "(Lio/reactivex/ObservableSource;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "awaitFirstOrDefault", "default", "(Lio/reactivex/ObservableSource;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "awaitFirstOrElse", "defaultValue", "Lkotlin/Function0;", "(Lio/reactivex/ObservableSource;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "awaitFirstOrNull", "awaitLast", "awaitOne", "mode", "Lkotlinx/coroutines/rx2/Mode;", "(Lio/reactivex/ObservableSource;Lkotlinx/coroutines/rx2/Mode;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "awaitOrDefault", "(Lio/reactivex/MaybeSource;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "awaitSingle", "awaitSingleOrNull", "disposeOnCancellation", "Lkotlinx/coroutines/CancellableContinuation;", "d", "Lio/reactivex/disposables/Disposable;", "kotlinx-coroutines-rx2"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class RxAwaitKt {

    /* compiled from: RxAwait.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.rx2.RxAwaitKt", f = "RxAwait.kt", i = {0}, l = {200}, m = "awaitFirstOrElse", n = {"defaultValue"}, s = {"L$0"})
    /* renamed from: kotlinx.coroutines.rx2.RxAwaitKt$awaitFirstOrElse$1, reason: invalid class name */
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
            return RxAwaitKt.awaitFirstOrElse(null, null, this);
        }
    }

    /* compiled from: RxAwait.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.rx2.RxAwaitKt", f = "RxAwait.kt", i = {0}, l = {126}, m = "awaitOrDefault", n = {"default"}, s = {"L$0"})
    /* renamed from: kotlinx.coroutines.rx2.RxAwaitKt$awaitOrDefault$1, reason: invalid class name and case insensitive filesystem */
    static final class C05621<T> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C05621(Continuation<? super C05621> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return RxAwaitKt.awaitOrDefault(null, null, this);
        }
    }

    /* compiled from: RxAwait.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.rx2.RxAwaitKt", f = "RxAwait.kt", i = {}, l = {82}, m = "awaitSingle", n = {}, s = {})
    /* renamed from: kotlinx.coroutines.rx2.RxAwaitKt$awaitSingle$1, reason: invalid class name and case insensitive filesystem */
    static final class C05631<T> extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C05631(Continuation<? super C05631> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return RxAwaitKt.awaitSingle((MaybeSource) null, this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <T> java.lang.Object awaitSingle(io.reactivex.MaybeSource<T> r4, kotlin.coroutines.Continuation<? super T> r5) {
        /*
            boolean r0 = r5 instanceof kotlinx.coroutines.rx2.RxAwaitKt.C05631
            if (r0 == 0) goto L14
            r0 = r5
            kotlinx.coroutines.rx2.RxAwaitKt$awaitSingle$1 r0 = (kotlinx.coroutines.rx2.RxAwaitKt.C05631) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r5 = r0.label
            int r5 = r5 - r2
            r0.label = r5
            goto L19
        L14:
            kotlinx.coroutines.rx2.RxAwaitKt$awaitSingle$1 r0 = new kotlinx.coroutines.rx2.RxAwaitKt$awaitSingle$1
            r0.<init>(r5)
        L19:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r5)
            goto L3e
        L2a:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L32:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.label = r3
            java.lang.Object r5 = awaitSingleOrNull(r4, r0)
            if (r5 != r1) goto L3e
            return r1
        L3e:
            if (r5 == 0) goto L41
            return r5
        L41:
            java.util.NoSuchElementException r4 = new java.util.NoSuchElementException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.rx2.RxAwaitKt.awaitSingle(io.reactivex.MaybeSource, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Deprecated in favor of awaitSingleOrNull()", replaceWith = @kotlin.ReplaceWith(expression = "this.awaitSingleOrNull() ?: default", imports = {}))
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final /* synthetic */ java.lang.Object awaitOrDefault(io.reactivex.MaybeSource r4, java.lang.Object r5, kotlin.coroutines.Continuation r6) {
        /*
            boolean r0 = r6 instanceof kotlinx.coroutines.rx2.RxAwaitKt.C05621
            if (r0 == 0) goto L14
            r0 = r6
            kotlinx.coroutines.rx2.RxAwaitKt$awaitOrDefault$1 r0 = (kotlinx.coroutines.rx2.RxAwaitKt.C05621) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r6 = r0.label
            int r6 = r6 - r2
            r0.label = r6
            goto L19
        L14:
            kotlinx.coroutines.rx2.RxAwaitKt$awaitOrDefault$1 r0 = new kotlinx.coroutines.rx2.RxAwaitKt$awaitOrDefault$1
            r0.<init>(r6)
        L19:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L34
            if (r2 != r3) goto L2c
            java.lang.Object r5 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r6)
            goto L42
        L2c:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L34:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r6 = awaitSingleOrNull(r4, r0)
            if (r6 != r1) goto L42
            return r1
        L42:
            if (r6 != 0) goto L45
            goto L46
        L45:
            r5 = r6
        L46:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.rx2.RxAwaitKt.awaitOrDefault(io.reactivex.MaybeSource, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final <T> Object awaitFirst(ObservableSource<T> observableSource, Continuation<? super T> continuation) {
        return awaitOne$default(observableSource, Mode.FIRST, null, continuation, 2, null);
    }

    public static final <T> Object awaitFirstOrDefault(ObservableSource<T> observableSource, T t, Continuation<? super T> continuation) {
        return awaitOne(observableSource, Mode.FIRST_OR_DEFAULT, t, continuation);
    }

    public static final <T> Object awaitFirstOrNull(ObservableSource<T> observableSource, Continuation<? super T> continuation) {
        return awaitOne$default(observableSource, Mode.FIRST_OR_DEFAULT, null, continuation, 2, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <T> java.lang.Object awaitFirstOrElse(io.reactivex.ObservableSource<T> r7, kotlin.jvm.functions.Function0<? extends T> r8, kotlin.coroutines.Continuation<? super T> r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.rx2.RxAwaitKt.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r9
            kotlinx.coroutines.rx2.RxAwaitKt$awaitFirstOrElse$1 r0 = (kotlinx.coroutines.rx2.RxAwaitKt.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L19
        L14:
            kotlinx.coroutines.rx2.RxAwaitKt$awaitFirstOrElse$1 r0 = new kotlinx.coroutines.rx2.RxAwaitKt$awaitFirstOrElse$1
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
            kotlinx.coroutines.rx2.Mode r9 = kotlinx.coroutines.rx2.Mode.FIRST_OR_DEFAULT
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
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.rx2.RxAwaitKt.awaitFirstOrElse(io.reactivex.ObservableSource, kotlin.jvm.functions.Function0, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final <T> Object awaitLast(ObservableSource<T> observableSource, Continuation<? super T> continuation) {
        return awaitOne$default(observableSource, Mode.LAST, null, continuation, 2, null);
    }

    public static final <T> Object awaitSingle(ObservableSource<T> observableSource, Continuation<? super T> continuation) {
        return awaitOne$default(observableSource, Mode.SINGLE, null, continuation, 2, null);
    }

    public static final void disposeOnCancellation(CancellableContinuation<?> cancellableContinuation, final Disposable disposable) {
        cancellableContinuation.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.rx2.RxAwaitKt.disposeOnCancellation.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                disposable.dispose();
            }
        });
    }

    static /* synthetic */ Object awaitOne$default(ObservableSource observableSource, Mode mode, Object obj, Continuation continuation, int i, Object obj2) {
        if ((i & 2) != 0) {
            obj = null;
        }
        return awaitOne(observableSource, mode, obj, continuation);
    }

    public static final Object await(CompletableSource completableSource, Continuation<? super Unit> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        completableSource.subscribe(new CompletableObserver() { // from class: kotlinx.coroutines.rx2.RxAwaitKt$await$2$1
            @Override // io.reactivex.CompletableObserver
            public void onSubscribe(Disposable d) {
                RxAwaitKt.disposeOnCancellation(cancellableContinuationImpl2, d);
            }

            @Override // io.reactivex.CompletableObserver, io.reactivex.MaybeObserver
            public void onComplete() {
                CancellableContinuation<Unit> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m624constructorimpl(Unit.INSTANCE));
            }

            @Override // io.reactivex.CompletableObserver
            public void onError(Throwable e) {
                CancellableContinuation<Unit> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(e)));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    public static final <T> Object awaitSingleOrNull(MaybeSource<T> maybeSource, Continuation<? super T> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        maybeSource.subscribe(new MaybeObserver<T>() { // from class: kotlinx.coroutines.rx2.RxAwaitKt$awaitSingleOrNull$2$1
            @Override // io.reactivex.MaybeObserver
            public void onSubscribe(Disposable d) {
                RxAwaitKt.disposeOnCancellation(cancellableContinuationImpl2, d);
            }

            @Override // io.reactivex.MaybeObserver
            public void onComplete() {
                CancellableContinuation<T> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m624constructorimpl(null));
            }

            @Override // io.reactivex.MaybeObserver
            public void onSuccess(T t) {
                CancellableContinuation<T> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m624constructorimpl(t));
            }

            @Override // io.reactivex.MaybeObserver
            public void onError(Throwable error) {
                CancellableContinuation<T> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(error)));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    public static final <T> Object await(SingleSource<T> singleSource, Continuation<? super T> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        singleSource.subscribe(new SingleObserver<T>() { // from class: kotlinx.coroutines.rx2.RxAwaitKt$await$5$1
            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable d) {
                RxAwaitKt.disposeOnCancellation(cancellableContinuationImpl2, d);
            }

            @Override // io.reactivex.SingleObserver
            public void onSuccess(T t) {
                CancellableContinuation<T> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m624constructorimpl(t));
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable error) {
                CancellableContinuation<T> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(error)));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> Object awaitOne(ObservableSource<T> observableSource, final Mode mode, final T t, Continuation<? super T> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        observableSource.subscribe(new Observer<T>() { // from class: kotlinx.coroutines.rx2.RxAwaitKt$awaitOne$2$1
            private boolean seenValue;
            private Disposable subscription;
            private T value;

            /* compiled from: RxAwait.kt */
            @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[Mode.values().length];
                    try {
                        iArr[Mode.FIRST.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[Mode.FIRST_OR_DEFAULT.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[Mode.LAST.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    try {
                        iArr[Mode.SINGLE.ordinal()] = 4;
                    } catch (NoSuchFieldError unused4) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(final Disposable sub) {
                this.subscription = sub;
                cancellableContinuationImpl2.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.rx2.RxAwaitKt$awaitOne$2$1$onSubscribe$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                        invoke2(th);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(Throwable th) {
                        sub.dispose();
                    }
                });
            }

            @Override // io.reactivex.Observer
            public void onNext(T t2) {
                int i = WhenMappings.$EnumSwitchMapping$0[mode.ordinal()];
                Disposable disposable = null;
                if (i == 1 || i == 2) {
                    if (this.seenValue) {
                        return;
                    }
                    this.seenValue = true;
                    CancellableContinuation<T> cancellableContinuation = cancellableContinuationImpl2;
                    Result.Companion companion = Result.INSTANCE;
                    cancellableContinuation.resumeWith(Result.m624constructorimpl(t2));
                    Disposable disposable2 = this.subscription;
                    if (disposable2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("subscription");
                    } else {
                        disposable = disposable2;
                    }
                    disposable.dispose();
                    return;
                }
                if (i == 3 || i == 4) {
                    if (mode == Mode.SINGLE && this.seenValue) {
                        if (cancellableContinuationImpl2.isActive()) {
                            CancellableContinuation<T> cancellableContinuation2 = cancellableContinuationImpl2;
                            Result.Companion companion2 = Result.INSTANCE;
                            cancellableContinuation2.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(new IllegalArgumentException("More than one onNext value for " + mode))));
                        }
                        Disposable disposable3 = this.subscription;
                        if (disposable3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("subscription");
                        } else {
                            disposable = disposable3;
                        }
                        disposable.dispose();
                        return;
                    }
                    this.value = t2;
                    this.seenValue = true;
                }
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                if (this.seenValue) {
                    if (cancellableContinuationImpl2.isActive()) {
                        CancellableContinuation<T> cancellableContinuation = cancellableContinuationImpl2;
                        Result.Companion companion = Result.INSTANCE;
                        cancellableContinuation.resumeWith(Result.m624constructorimpl(this.value));
                        return;
                    }
                    return;
                }
                if (mode == Mode.FIRST_OR_DEFAULT) {
                    CancellableContinuation<T> cancellableContinuation2 = cancellableContinuationImpl2;
                    Result.Companion companion2 = Result.INSTANCE;
                    cancellableContinuation2.resumeWith(Result.m624constructorimpl(t));
                } else if (cancellableContinuationImpl2.isActive()) {
                    CancellableContinuation<T> cancellableContinuation3 = cancellableContinuationImpl2;
                    Result.Companion companion3 = Result.INSTANCE;
                    cancellableContinuation3.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(new NoSuchElementException("No value received via onNext for " + mode))));
                }
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                CancellableContinuation<T> cancellableContinuation = cancellableContinuationImpl2;
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuation.resumeWith(Result.m624constructorimpl(ResultKt.createFailure(e)));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }
}
