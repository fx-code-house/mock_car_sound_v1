package org.mapstruct.ap.internal.util;

import java.util.Iterator;
import java.util.ServiceLoader;

/* loaded from: classes3.dex */
public class Services {
    private Services() {
    }

    public static <T> T get(Class<T> cls, T t) {
        Iterator it = ServiceLoader.load(cls, Services.class.getClassLoader()).iterator();
        if (it.hasNext()) {
            t = (T) it.next();
        }
        if (it.hasNext()) {
            throw new IllegalStateException("Multiple implementations have been found for the service provider interface");
        }
        return t;
    }
}
