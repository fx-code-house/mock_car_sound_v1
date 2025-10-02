package com.thor.app.gui.activities.login;

import com.thor.networkmodule.model.responses.SignUpResponse;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SignUpActivity.kt */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
/* synthetic */ class SignUpActivity$onSignUp$1$3 extends FunctionReferenceImpl implements Function1<SignUpResponse, Unit> {
    SignUpActivity$onSignUp$1$3(Object obj) {
        super(1, obj, SignUpActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/SignUpResponse;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(SignUpResponse signUpResponse) {
        invoke2(signUpResponse);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(SignUpResponse p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        ((SignUpActivity) this.receiver).handleResponse(p0);
    }
}
