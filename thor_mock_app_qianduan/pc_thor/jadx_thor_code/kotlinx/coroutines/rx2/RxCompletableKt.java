package kotlinx.coroutines.rx2;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.Job;

/* compiled from: RxCompletable.kt */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001aA\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032'\u0010\u0004\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0005¢\u0006\u0002\b\nø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001aI\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u00032'\u0010\u0004\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0005¢\u0006\u0002\b\nH\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u000e\u001aG\u0010\u0000\u001a\u00020\u0001*\u00020\u00062\b\b\u0002\u0010\u0002\u001a\u00020\u00032'\u0010\u0004\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0005¢\u0006\u0002\b\nH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u000e\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, d2 = {"rxCompletable", "Lio/reactivex/Completable;", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lio/reactivex/Completable;", "rxCompletableInternal", "scope", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lio/reactivex/Completable;", "kotlinx-coroutines-rx2"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class RxCompletableKt {
    public static /* synthetic */ Completable rxCompletable$default(CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        return rxCompletable(coroutineContext, function2);
    }

    public static final Completable rxCompletable(CoroutineContext coroutineContext, Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> function2) {
        if (!(coroutineContext.get(Job.INSTANCE) == null)) {
            throw new IllegalArgumentException(("Completable context cannot contain job in it. Its lifecycle should be managed via Disposable handle. Had " + coroutineContext).toString());
        }
        return rxCompletableInternal(GlobalScope.INSTANCE, coroutineContext, function2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Completable rxCompletableInternal(final CoroutineScope coroutineScope, final CoroutineContext coroutineContext, final Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> function2) {
        return Completable.create(new CompletableOnSubscribe() { // from class: kotlinx.coroutines.rx2.RxCompletableKt$$ExternalSyntheticLambda0
            @Override // io.reactivex.CompletableOnSubscribe
            public final void subscribe(CompletableEmitter completableEmitter) {
                RxCompletableKt.rxCompletableInternal$lambda$1(coroutineScope, coroutineContext, function2, completableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void rxCompletableInternal$lambda$1(CoroutineScope coroutineScope, CoroutineContext coroutineContext, Function2 function2, CompletableEmitter completableEmitter) {
        RxCompletableCoroutine rxCompletableCoroutine = new RxCompletableCoroutine(CoroutineContextKt.newCoroutineContext(coroutineScope, coroutineContext), completableEmitter);
        completableEmitter.setCancellable(new RxCancellable(rxCompletableCoroutine));
        rxCompletableCoroutine.start(CoroutineStart.DEFAULT, rxCompletableCoroutine, function2);
    }

    public static /* synthetic */ Completable rxCompletable$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        return rxCompletableInternal(coroutineScope, coroutineContext, function2);
    }
}
