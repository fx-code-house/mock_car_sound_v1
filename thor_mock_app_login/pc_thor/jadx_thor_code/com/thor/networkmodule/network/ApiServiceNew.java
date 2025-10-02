package com.thor.networkmodule.network;

import com.thor.networkmodule.model.responses.versions.CurrentAppVersion;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import retrofit2.Response;
import retrofit2.http.GET;

/* compiled from: ApiServiceNew.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006"}, d2 = {"Lcom/thor/networkmodule/network/ApiServiceNew;", "", "getAppVersionsFromApi", "Lretrofit2/Response;", "Lcom/thor/networkmodule/model/responses/versions/CurrentAppVersion;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public interface ApiServiceNew {
    @GET("/api/version")
    Object getAppVersionsFromApi(Continuation<? super Response<CurrentAppVersion>> continuation);
}
