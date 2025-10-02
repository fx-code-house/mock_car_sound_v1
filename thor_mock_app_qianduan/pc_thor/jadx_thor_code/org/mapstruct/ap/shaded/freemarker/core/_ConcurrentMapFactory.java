package org.mapstruct.ap.shaded.freemarker.core;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.UndeclaredThrowableException;

/* loaded from: classes3.dex */
public class _ConcurrentMapFactory {
    private static final Class bestHashMapClass;
    private static final Constructor bestHashMapClassConstructor;
    private static final int bestHashMapClassConstructorParamCnt;
    static /* synthetic */ Class class$java$util$HashMap;
    private static final Class concurrentMapClass;

    static {
        Class clsForName;
        Class clsClass$;
        Constructor constructor;
        try {
            clsForName = ClassUtil.forName("java.util.concurrent.ConcurrentMap");
        } catch (ClassNotFoundException unused) {
            clsForName = null;
        }
        concurrentMapClass = clsForName;
        int i = 2;
        try {
            clsClass$ = ClassUtil.forName("java.util.concurrent.ConcurrentHashMap");
            try {
                constructor = clsClass$.getConstructor(Integer.TYPE, Float.TYPE, Integer.TYPE);
                i = 3;
            } catch (Exception e) {
                throw new RuntimeException("Failed to get ConcurrentHashMap constructor", e);
            }
        } catch (ClassNotFoundException unused2) {
            clsClass$ = class$java$util$HashMap;
            if (clsClass$ == null) {
                clsClass$ = class$("java.util.HashMap");
                class$java$util$HashMap = clsClass$;
            }
            try {
                constructor = clsClass$.getConstructor(Integer.TYPE, Float.TYPE);
            } catch (Exception e2) {
                throw new RuntimeException("Failed to get HashMap constructor", e2);
            }
        }
        bestHashMapClass = clsClass$;
        bestHashMapClassConstructor = constructor;
        bestHashMapClassConstructorParamCnt = i;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public static Map newMaybeConcurrentHashMap() {
        try {
            return (Map) bestHashMapClass.newInstance();
        } catch (Exception e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    public static Map newMaybeConcurrentHashMap(int i, float f, int i2) {
        try {
            int i3 = bestHashMapClassConstructorParamCnt;
            if (i3 == 3) {
                return (Map) bestHashMapClassConstructor.newInstance(new Integer(i), new Float(f), new Integer(i2));
            }
            if (i3 == 2) {
                return (Map) bestHashMapClassConstructor.newInstance(new Integer(i), new Float(f));
            }
            throw new BugException();
        } catch (Exception e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    public static Map newThreadSafeMap() {
        Map mapNewMaybeConcurrentHashMap = newMaybeConcurrentHashMap();
        return isConcurrent(mapNewMaybeConcurrentHashMap) ? mapNewMaybeConcurrentHashMap : Collections.synchronizedMap(mapNewMaybeConcurrentHashMap);
    }

    public static boolean concurrentMapsAvailable() {
        return concurrentMapClass != null;
    }

    public static boolean isConcurrent(Map map) {
        Class cls = concurrentMapClass;
        return cls != null && cls.isInstance(map);
    }
}
