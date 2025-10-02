package org.mapstruct.ap.shaded.freemarker.cache;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.core._ConcurrentMapFactory;
import org.mapstruct.ap.shaded.freemarker.template.utility.UndeclaredThrowableException;

/* loaded from: classes3.dex */
public class SoftCacheStorage implements ConcurrentCacheStorage, CacheStorageWithGetSize {
    private static final Method atomicRemove = getAtomicRemoveMethod();
    static /* synthetic */ Class class$java$lang$Object;
    private final boolean concurrent;
    private final Map map;
    private final ReferenceQueue queue;

    public SoftCacheStorage() {
        this(_ConcurrentMapFactory.newMaybeConcurrentHashMap());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.ConcurrentCacheStorage
    public boolean isConcurrent() {
        return this.concurrent;
    }

    public SoftCacheStorage(Map map) {
        this.queue = new ReferenceQueue();
        this.map = map;
        this.concurrent = _ConcurrentMapFactory.isConcurrent(map);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorage
    public Object get(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        processQueue();
        Reference reference = (Reference) this.map.get(obj);
        if (reference == null) {
            return null;
        }
        return reference.get();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorage
    public void put(Object obj, Object obj2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        processQueue();
        this.map.put(obj, new SoftValueReference(obj, obj2, this.queue));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorage
    public void remove(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        processQueue();
        this.map.remove(obj);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorage
    public void clear() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.map.clear();
        processQueue();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.CacheStorageWithGetSize
    public int getSize() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        processQueue();
        return this.map.size();
    }

    private void processQueue() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        while (true) {
            SoftValueReference softValueReference = (SoftValueReference) this.queue.poll();
            if (softValueReference == null) {
                return;
            }
            Object key = softValueReference.getKey();
            if (this.concurrent) {
                try {
                    atomicRemove.invoke(this.map, key, softValueReference);
                } catch (IllegalAccessException e) {
                    throw new UndeclaredThrowableException(e);
                } catch (InvocationTargetException e2) {
                    throw new UndeclaredThrowableException(e2);
                }
            } else if (this.map.get(key) == softValueReference) {
                this.map.remove(key);
            }
        }
    }

    private static final class SoftValueReference extends SoftReference {
        private final Object key;

        SoftValueReference(Object obj, Object obj2, ReferenceQueue referenceQueue) {
            super(obj2, referenceQueue);
            this.key = obj;
        }

        Object getKey() {
            return this.key;
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private static Method getAtomicRemoveMethod() throws Throwable {
        try {
            Class<?> cls = Class.forName("java.util.concurrent.ConcurrentMap");
            Class<?>[] clsArr = new Class[2];
            Class<?> clsClass$ = class$java$lang$Object;
            if (clsClass$ == null) {
                clsClass$ = class$("java.lang.Object");
                class$java$lang$Object = clsClass$;
            }
            clsArr[0] = clsClass$;
            Class<?> clsClass$2 = class$java$lang$Object;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.lang.Object");
                class$java$lang$Object = clsClass$2;
            }
            clsArr[1] = clsClass$2;
            return cls.getMethod("remove", clsArr);
        } catch (ClassNotFoundException unused) {
            return null;
        } catch (NoSuchMethodException e) {
            throw new UndeclaredThrowableException(e);
        }
    }
}
