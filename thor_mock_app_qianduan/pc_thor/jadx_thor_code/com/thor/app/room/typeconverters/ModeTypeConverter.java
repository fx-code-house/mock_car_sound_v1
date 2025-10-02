package com.thor.app.room.typeconverters;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.thor.networkmodule.model.ModeType;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ModeTypeConverter.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0007J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\t\u001a\u00020\u0004H\u0007¨\u0006\n"}, d2 = {"Lcom/thor/app/room/typeconverters/ModeTypeConverter;", "", "()V", "toJson", "", "list", "", "Lcom/thor/networkmodule/model/ModeType;", "toList", "data", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ModeTypeConverter {
    public final List<ModeType> toList(String data) throws JsonSyntaxException {
        Intrinsics.checkNotNullParameter(data, "data");
        Object objFromJson = new Gson().fromJson(data, new TypeToken<List<? extends ModeType>>() { // from class: com.thor.app.room.typeconverters.ModeTypeConverter$toList$listType$1
        }.getType());
        Intrinsics.checkNotNullExpressionValue(objFromJson, "Gson().fromJson(data, listType)");
        return (List) objFromJson;
    }

    public final String toJson(List<ModeType> list) throws JsonIOException {
        Intrinsics.checkNotNullParameter(list, "list");
        String json = new Gson().toJson(list, new TypeToken<List<? extends ModeType>>() { // from class: com.thor.app.room.typeconverters.ModeTypeConverter$toJson$type$1
        }.getType());
        Intrinsics.checkNotNullExpressionValue(json, "Gson().toJson(list, type)");
        return json;
    }
}
