package org.mapstruct.ap.internal.util;

/* loaded from: classes3.dex */
class ClassUtils {
    private ClassUtils() {
    }

    static boolean isPresent(String str, ClassLoader classLoader) throws ClassNotFoundException {
        if (classLoader == null) {
            try {
                classLoader = getDefaultClassLoader();
            } catch (ClassNotFoundException unused) {
                return false;
            }
        }
        classLoader.loadClass(str);
        return true;
    }

    private static ClassLoader getDefaultClassLoader() {
        ClassLoader contextClassLoader;
        try {
            contextClassLoader = Thread.currentThread().getContextClassLoader();
        } catch (Throwable unused) {
            contextClassLoader = null;
        }
        return contextClassLoader == null ? ClassUtils.class.getClassLoader() : contextClassLoader;
    }
}
