package org.mapstruct.ap.shaded.freemarker.cache;

import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.core._ConcurrentMapFactory;

/* loaded from: classes3.dex */
public class StrongCacheStorage implements ConcurrentCacheStorage, CacheStorageWithGetSize {
    private final Map map = _ConcurrentMapFactory.newMaybeConcurrentHashMap();

    @Override // org.mapstruct.ap.shaded.freemarker.cache.ConcurrentCacheStorage
    public boolean isConcurrent() {
        return _ConcurrentMapFactory.isConcurrent(this.map);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorage
    public Object get(Object obj) {
        return this.map.get(obj);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorage
    public void put(Object obj, Object obj2) {
        this.map.put(obj, obj2);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorage
    public void remove(Object obj) {
        this.map.remove(obj);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorageWithGetSize
    public int getSize() {
        return this.map.size();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorage
    public void clear() {
        this.map.clear();
    }
}
