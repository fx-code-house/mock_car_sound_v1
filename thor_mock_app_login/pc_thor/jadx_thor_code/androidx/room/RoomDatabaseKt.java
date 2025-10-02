package androidx.room;

import java.util.concurrent.RejectedExecutionException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ThreadContextElementKt;

/* compiled from: RoomDatabaseExt.kt */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001aL\u0010\u0005\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012'\u0010\b\u001a#\b\u0001\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00060\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\t¢\u0006\u0002\b\rH\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u000e\u001a9\u0010\u000f\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006*\u00020\u00022\u001c\u0010\u0010\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00060\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0011H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0013"}, d2 = {"createTransactionContext", "Lkotlin/coroutines/CoroutineContext;", "Landroidx/room/RoomDatabase;", "dispatcher", "Lkotlin/coroutines/ContinuationInterceptor;", "startTransactionCoroutine", "R", "context", "transactionBlock", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Landroidx/room/RoomDatabase;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withTransaction", "block", "Lkotlin/Function1;", "(Landroidx/room/RoomDatabase;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "room-ktx_release"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class RoomDatabaseKt {
    public static final <R> Object withTransaction(RoomDatabase roomDatabase, Function1<? super Continuation<? super R>, ? extends Object> function1, Continuation<? super R> continuation) {
        RoomDatabaseKt$withTransaction$transactionBlock$1 roomDatabaseKt$withTransaction$transactionBlock$1 = new RoomDatabaseKt$withTransaction$transactionBlock$1(roomDatabase, function1, null);
        TransactionElement transactionElement = (TransactionElement) continuation.get$context().get(TransactionElement.INSTANCE);
        ContinuationInterceptor transactionDispatcher = transactionElement != null ? transactionElement.getTransactionDispatcher() : null;
        if (transactionDispatcher != null) {
            return BuildersKt.withContext(transactionDispatcher, roomDatabaseKt$withTransaction$transactionBlock$1, continuation);
        }
        return startTransactionCoroutine(roomDatabase, continuation.get$context(), roomDatabaseKt$withTransaction$transactionBlock$1, continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CoroutineContext createTransactionContext(RoomDatabase roomDatabase, ContinuationInterceptor continuationInterceptor) {
        TransactionElement transactionElement = new TransactionElement(continuationInterceptor);
        return continuationInterceptor.plus(transactionElement).plus(ThreadContextElementKt.asContextElement(roomDatabase.getSuspendingTransactionId(), Integer.valueOf(System.identityHashCode(transactionElement))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <R> Object startTransactionCoroutine(final RoomDatabase roomDatabase, final CoroutineContext coroutineContext, final Function2<? super CoroutineScope, ? super Continuation<? super R>, ? extends Object> function2, Continuation<? super R> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        try {
            roomDatabase.getTransactionExecutor().execute(new Runnable() { // from class: androidx.room.RoomDatabaseKt$startTransactionCoroutine$2$1

                /* compiled from: RoomDatabaseExt.kt */
                @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "R", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
                @DebugMetadata(c = "androidx.room.RoomDatabaseKt$startTransactionCoroutine$2$1$1", f = "RoomDatabaseExt.kt", i = {}, l = {97}, m = "invokeSuspend", n = {}, s = {})
                /* renamed from: androidx.room.RoomDatabaseKt$startTransactionCoroutine$2$1$1, reason: invalid class name */
                static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                    final /* synthetic */ CancellableContinuation<R> $continuation;
                    final /* synthetic */ RoomDatabase $this_startTransactionCoroutine;
                    final /* synthetic */ Function2<CoroutineScope, Continuation<? super R>, Object> $transactionBlock;
                    private /* synthetic */ Object L$0;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    AnonymousClass1(RoomDatabase roomDatabase, CancellableContinuation<? super R> cancellableContinuation, Function2<? super CoroutineScope, ? super Continuation<? super R>, ? extends Object> function2, Continuation<? super AnonymousClass1> continuation) {
                        super(2, continuation);
                        this.$this_startTransactionCoroutine = roomDatabase;
                        this.$continuation = cancellableContinuation;
                        this.$transactionBlock = function2;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_startTransactionCoroutine, this.$continuation, this.$transactionBlock, continuation);
                        anonymousClass1.L$0 = obj;
                        return anonymousClass1;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                        return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) throws Throwable {
                        Continuation continuation;
                        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            CoroutineContext.Element element = ((CoroutineScope) this.L$0).getCoroutineContext().get(ContinuationInterceptor.INSTANCE);
                            Intrinsics.checkNotNull(element);
                            CoroutineContext coroutineContextCreateTransactionContext = RoomDatabaseKt.createTransactionContext(this.$this_startTransactionCoroutine, (ContinuationInterceptor) element);
                            Continuation continuation2 = this.$continuation;
                            this.L$0 = continuation2;
                            this.label = 1;
                            obj = BuildersKt.withContext(coroutineContextCreateTransactionContext, this.$transactionBlock, this);
                            if (obj == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            continuation = continuation2;
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            continuation = (Continuation) this.L$0;
                            ResultKt.throwOnFailure(obj);
                        }
                        Result.Companion companion = Result.INSTANCE;
                        continuation.resumeWith(Result.m624constructorimpl(obj));
                        return Unit.INSTANCE;
                    }
                }

                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        BuildersKt.runBlocking(coroutineContext.minusKey(ContinuationInterceptor.INSTANCE), new AnonymousClass1(roomDatabase, cancellableContinuationImpl2, function2, null));
                    } catch (Throwable th) {
                        cancellableContinuationImpl2.cancel(th);
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            cancellableContinuationImpl2.cancel(new IllegalStateException("Unable to acquire a thread to perform the database transaction.", e));
        }
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }
}
