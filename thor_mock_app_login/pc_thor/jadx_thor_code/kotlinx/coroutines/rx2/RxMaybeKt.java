package kotlinx.coroutines.rx2;

import androidx.exifinterface.media.ExifInterface;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.Job;

/* compiled from: RxMaybe.kt */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001aO\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042)\u0010\u0005\u001a%\b\u0001\u0012\u0004\u0012\u00020\u0007\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006¢\u0006\u0002\b\nø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001aW\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00042)\u0010\u0005\u001a%\b\u0001\u0012\u0004\u0012\u00020\u0007\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006¢\u0006\u0002\b\nH\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u000e\u001aU\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00072\b\b\u0002\u0010\u0003\u001a\u00020\u00042)\u0010\u0005\u001a%\b\u0001\u0012\u0004\u0012\u00020\u0007\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006¢\u0006\u0002\b\nH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u000e\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, d2 = {"rxMaybe", "Lio/reactivex/Maybe;", ExifInterface.GPS_DIRECTION_TRUE, "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lio/reactivex/Maybe;", "rxMaybeInternal", "scope", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lio/reactivex/Maybe;", "kotlinx-coroutines-rx2"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class RxMaybeKt {
    public static /* synthetic */ Maybe rxMaybe$default(CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        return rxMaybe(coroutineContext, function2);
    }

    public static final <T> Maybe<T> rxMaybe(CoroutineContext coroutineContext, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2) {
        if (!(coroutineContext.get(Job.INSTANCE) == null)) {
            throw new IllegalArgumentException(("Maybe context cannot contain job in it.Its lifecycle should be managed via Disposable handle. Had " + coroutineContext).toString());
        }
        return rxMaybeInternal(GlobalScope.INSTANCE, coroutineContext, function2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> Maybe<T> rxMaybeInternal(final CoroutineScope coroutineScope, final CoroutineContext coroutineContext, final Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2) {
        return Maybe.create(new MaybeOnSubscribe() { // from class: kotlinx.coroutines.rx2.RxMaybeKt$$ExternalSyntheticLambda0
            @Override // io.reactivex.MaybeOnSubscribe
            public final void subscribe(MaybeEmitter maybeEmitter) {
                RxMaybeKt.rxMaybeInternal$lambda$1(coroutineScope, coroutineContext, function2, maybeEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void rxMaybeInternal$lambda$1(CoroutineScope coroutineScope, CoroutineContext coroutineContext, Function2 function2, MaybeEmitter maybeEmitter) {
        RxMaybeCoroutine rxMaybeCoroutine = new RxMaybeCoroutine(CoroutineContextKt.newCoroutineContext(coroutineScope, coroutineContext), maybeEmitter);
        maybeEmitter.setCancellable(new RxCancellable(rxMaybeCoroutine));
        rxMaybeCoroutine.start(CoroutineStart.DEFAULT, rxMaybeCoroutine, function2);
    }

    public static /* synthetic */ Maybe rxMaybe$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        return rxMaybeInternal(coroutineScope, coroutineContext, function2);
    }
}
