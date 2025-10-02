package kotlinx.coroutines.rx2;

import androidx.exifinterface.media.ExifInterface;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
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

/* compiled from: RxSingle.kt */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001aQ\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052'\u0010\u0006\u001a#\b\u0001\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0007¢\u0006\u0002\b\nø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001aY\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00052'\u0010\u0006\u001a#\b\u0001\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0007¢\u0006\u0002\b\nH\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u000e\u001aW\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u00020\b2\b\b\u0002\u0010\u0004\u001a\u00020\u00052'\u0010\u0006\u001a#\b\u0001\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0007¢\u0006\u0002\b\nH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u000e\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, d2 = {"rxSingle", "Lio/reactivex/Single;", ExifInterface.GPS_DIRECTION_TRUE, "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lio/reactivex/Single;", "rxSingleInternal", "scope", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lio/reactivex/Single;", "kotlinx-coroutines-rx2"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class RxSingleKt {
    public static /* synthetic */ Single rxSingle$default(CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        return rxSingle(coroutineContext, function2);
    }

    public static final <T> Single<T> rxSingle(CoroutineContext coroutineContext, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2) {
        if (!(coroutineContext.get(Job.INSTANCE) == null)) {
            throw new IllegalArgumentException(("Single context cannot contain job in it.Its lifecycle should be managed via Disposable handle. Had " + coroutineContext).toString());
        }
        return rxSingleInternal(GlobalScope.INSTANCE, coroutineContext, function2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> Single<T> rxSingleInternal(final CoroutineScope coroutineScope, final CoroutineContext coroutineContext, final Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> function2) {
        return Single.create(new SingleOnSubscribe() { // from class: kotlinx.coroutines.rx2.RxSingleKt$$ExternalSyntheticLambda0
            @Override // io.reactivex.SingleOnSubscribe
            public final void subscribe(SingleEmitter singleEmitter) {
                RxSingleKt.rxSingleInternal$lambda$1(coroutineScope, coroutineContext, function2, singleEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void rxSingleInternal$lambda$1(CoroutineScope coroutineScope, CoroutineContext coroutineContext, Function2 function2, SingleEmitter singleEmitter) {
        RxSingleCoroutine rxSingleCoroutine = new RxSingleCoroutine(CoroutineContextKt.newCoroutineContext(coroutineScope, coroutineContext), singleEmitter);
        singleEmitter.setCancellable(new RxCancellable(rxSingleCoroutine));
        rxSingleCoroutine.start(CoroutineStart.DEFAULT, rxSingleCoroutine, function2);
    }

    public static /* synthetic */ Single rxSingle$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        return rxSingleInternal(coroutineScope, coroutineContext, function2);
    }
}
