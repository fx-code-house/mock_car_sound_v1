package com.orhanobut.hawk;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;

/* loaded from: classes2.dex */
public final class GsonParser implements Parser {
    private final Gson gson;

    public GsonParser(Gson gson) {
        this.gson = gson;
    }

    @Override // com.orhanobut.hawk.Parser
    public <T> T fromJson(String str, Type type) throws JsonSyntaxException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (T) this.gson.fromJson(str, type);
    }

    @Override // com.orhanobut.hawk.Parser
    public String toJson(Object obj) {
        return this.gson.toJson(obj);
    }
}
