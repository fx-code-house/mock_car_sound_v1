package org.mapstruct.ap.shaded.freemarker.cache;

/* loaded from: classes3.dex */
public interface CacheStorage {
    void clear();

    Object get(Object obj);

    void put(Object obj, Object obj2);

    void remove(Object obj);
}
