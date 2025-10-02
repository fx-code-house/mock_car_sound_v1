package com.thor.networkmodule.utils;

import com.google.gson.Gson;
import com.thor.networkmodule.model.responses.BaseResponse;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.HttpException;

/* compiled from: ThrowExt.kt */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"toErrorClass", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "", "networkmodule_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ThrowExtKt {
    public static final BaseResponse toErrorClass(Throwable th) throws IOException {
        Response<?> response;
        ResponseBody responseBodyErrorBody;
        Intrinsics.checkNotNullParameter(th, "<this>");
        String strString = null;
        HttpException httpException = th instanceof HttpException ? (HttpException) th : null;
        if (httpException != null && (response = httpException.response()) != null && (responseBodyErrorBody = response.errorBody()) != null) {
            strString = responseBodyErrorBody.string();
        }
        BaseResponse it = (BaseResponse) new Gson().fromJson(strString, BaseResponse.class);
        Intrinsics.checkNotNullExpressionValue(it, "it");
        return it;
    }
}
