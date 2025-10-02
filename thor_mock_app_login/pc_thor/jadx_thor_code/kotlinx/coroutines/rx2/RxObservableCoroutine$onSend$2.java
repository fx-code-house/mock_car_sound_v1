package kotlinx.coroutines.rx2;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: RxObservable.kt */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
/* synthetic */ class RxObservableCoroutine$onSend$2 extends FunctionReferenceImpl implements Function3<RxObservableCoroutine<?>, Object, Object, Object> {
    public static final RxObservableCoroutine$onSend$2 INSTANCE = new RxObservableCoroutine$onSend$2();

    RxObservableCoroutine$onSend$2() {
        super(3, RxObservableCoroutine.class, "processResultSelectSend", "processResultSelectSend(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(RxObservableCoroutine<?> rxObservableCoroutine, Object obj, Object obj2) {
        return rxObservableCoroutine.processResultSelectSend(obj, obj2);
    }
}
