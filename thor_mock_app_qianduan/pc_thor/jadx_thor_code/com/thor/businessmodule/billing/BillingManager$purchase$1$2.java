package com.thor.businessmodule.billing;

import io.reactivex.SingleEmitter;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BillingManager.kt */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
/* synthetic */ class BillingManager$purchase$1$2 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
    BillingManager$purchase$1$2(Object obj) {
        super(1, obj, SingleEmitter.class, "onError", "onError(Ljava/lang/Throwable;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
        invoke2(th);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(Throwable p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        ((SingleEmitter) this.receiver).onError(p0);
    }
}
