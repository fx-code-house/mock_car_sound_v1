package com.orhanobut.hawk;

import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
final class HawkConverter implements Converter {
    private final Parser parser;

    public HawkConverter(Parser parser) {
        if (parser == null) {
            throw new NullPointerException("Parser should not be null");
        }
        this.parser = parser;
    }

    @Override // com.orhanobut.hawk.Converter
    public <T> String toString(T t) {
        if (t == null) {
            return null;
        }
        return this.parser.toJson(t);
    }

    @Override // com.orhanobut.hawk.Converter
    public <T> T fromString(String str, DataInfo dataInfo) throws Exception {
        if (str == null) {
            return null;
        }
        HawkUtils.checkNull("data info", dataInfo);
        Class<?> cls = dataInfo.keyClazz;
        Class<?> cls2 = dataInfo.valueClazz;
        switch (dataInfo.dataType) {
        }
        return null;
    }

    private <T> T toObject(String str, Class<?> cls) throws Exception {
        return (T) this.parser.fromJson(str, cls);
    }

    /* JADX WARN: Type inference failed for: r5v2, types: [T, java.util.List] */
    private <T> T toList(String str, Class<?> cls) throws Exception {
        if (cls == null) {
            return (T) new ArrayList();
        }
        ?? r5 = (T) ((List) this.parser.fromJson(str, new TypeToken<List<T>>() { // from class: com.orhanobut.hawk.HawkConverter.1
        }.getType()));
        int size = r5.size();
        for (int i = 0; i < size; i++) {
            Parser parser = this.parser;
            r5.set(i, parser.fromJson(parser.toJson(r5.get(i)), cls));
        }
        return r5;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, java.util.HashSet, java.util.Set] */
    private <T> T toSet(String str, Class<?> cls) throws Exception {
        ?? r0 = (T) new HashSet();
        if (cls == null) {
            return r0;
        }
        Iterator it = ((Set) this.parser.fromJson(str, new TypeToken<Set<T>>() { // from class: com.orhanobut.hawk.HawkConverter.2
        }.getType())).iterator();
        while (it.hasNext()) {
            r0.add(this.parser.fromJson(this.parser.toJson(it.next()), cls));
        }
        return r0;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, java.util.HashMap, java.util.Map] */
    private <K, V, T> T toMap(String str, Class<?> cls, Class<?> cls2) throws Exception {
        ?? r0 = (T) new HashMap();
        if (cls != null && cls2 != null) {
            for (Map.Entry<K, V> entry : ((Map) this.parser.fromJson(str, new TypeToken<Map<K, V>>() { // from class: com.orhanobut.hawk.HawkConverter.3
            }.getType())).entrySet()) {
                r0.put(this.parser.fromJson(this.parser.toJson(entry.getKey()), cls), this.parser.fromJson(this.parser.toJson(entry.getValue()), cls2));
            }
        }
        return r0;
    }
}
