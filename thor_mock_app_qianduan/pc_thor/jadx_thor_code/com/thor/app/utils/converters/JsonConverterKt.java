package com.thor.app.utils.converters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thor.networkmodule.model.responses.sgu.SguSoundJsonWrapper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: JsonConverter.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0005\u001a\u00020\u0001¨\u0006\u0006"}, d2 = {"toSguSoundJson", "", "list", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundJsonWrapper;", "toSguSoundList", "data", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class JsonConverterKt {
    public static final SguSoundJsonWrapper toSguSoundList(String data) {
        Intrinsics.checkNotNullParameter(data, "data");
        return (SguSoundJsonWrapper) new Gson().fromJson(data, new TypeToken<SguSoundJsonWrapper>() { // from class: com.thor.app.utils.converters.JsonConverterKt$toSguSoundList$listType$1
        }.getType());
    }

    public static final String toSguSoundJson(SguSoundJsonWrapper list) {
        Intrinsics.checkNotNullParameter(list, "list");
        String json = new Gson().toJson(list, new TypeToken<SguSoundJsonWrapper>() { // from class: com.thor.app.utils.converters.JsonConverterKt$toSguSoundJson$type$1
        }.getType());
        Intrinsics.checkNotNullExpressionValue(json, "Gson().toJson(list, type)");
        return json;
    }
}
