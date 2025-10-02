package com.thor.app.gui.activities.login;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SignUpActivity.kt */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
/* synthetic */ class SignUpActivity$onSignUp$1$4 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
    SignUpActivity$onSignUp$1$4(Object obj) {
        super(1, obj, SignUpActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
        invoke2(th);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(Throwable p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        ((SignUpActivity) this.receiver).handleError(p0);
    }
}
