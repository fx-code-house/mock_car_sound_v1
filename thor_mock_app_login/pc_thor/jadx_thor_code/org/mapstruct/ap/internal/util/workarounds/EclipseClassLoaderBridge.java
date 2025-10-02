package org.mapstruct.ap.internal.util.workarounds;

import java.lang.reflect.Method;
import java.net.URLClassLoader;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

/* loaded from: classes3.dex */
class EclipseClassLoaderBridge {
    private static final String ECLIPSE_AS_MEMBER_OF_WORKAROUND = "org.mapstruct.ap.internal.util.workarounds.EclipseAsMemberOfWorkaround";
    private static Method asMemberOf;
    private static ClassLoader classLoader;

    private EclipseClassLoaderBridge() {
    }

    static TypeMirror invokeAsMemberOfWorkaround(ProcessingEnvironment processingEnvironment, DeclaredType declaredType, Element element) throws Exception {
        return (TypeMirror) getAsMemberOf(element.getClass().getClassLoader()).invoke(null, processingEnvironment, declaredType, element);
    }

    private static ClassLoader getOrCreateClassLoader(ClassLoader classLoader2) {
        if (classLoader == null) {
            classLoader = new URLClassLoader(((URLClassLoader) EclipseClassLoaderBridge.class.getClassLoader()).getURLs(), classLoader2);
        }
        return classLoader;
    }

    private static Method getAsMemberOf(ClassLoader classLoader2) throws Exception {
        if (asMemberOf == null) {
            Method declaredMethod = getOrCreateClassLoader(classLoader2).loadClass(ECLIPSE_AS_MEMBER_OF_WORKAROUND).getDeclaredMethod("asMemberOf", ProcessingEnvironment.class, DeclaredType.class, Element.class);
            declaredMethod.setAccessible(true);
            asMemberOf = declaredMethod;
        }
        return asMemberOf;
    }
}
