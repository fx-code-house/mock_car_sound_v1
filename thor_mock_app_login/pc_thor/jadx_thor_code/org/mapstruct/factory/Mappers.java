package org.mapstruct.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/* loaded from: classes3.dex */
public class Mappers {
    private static final String IMPLEMENTATION_SUFFIX = "Impl";

    private Mappers() {
    }

    public static <T> T getMapper(Class<T> cls) {
        try {
            return (T) getMapper(cls, collectClassLoaders(cls.getClassLoader()));
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T getMapper(Class<T> cls, Iterable<ClassLoader> iterable) throws NoSuchMethodException, ClassNotFoundException {
        Iterator<ClassLoader> it = iterable.iterator();
        while (it.hasNext()) {
            T t = (T) doGetMapper(cls, it.next());
            if (t != null) {
                return t;
            }
        }
        throw new ClassNotFoundException("Cannot find implementation for " + cls.getName());
    }

    private static <T> T doGetMapper(Class<T> cls, ClassLoader classLoader) throws NoSuchMethodException, SecurityException {
        try {
            Constructor<?> declaredConstructor = classLoader.loadClass(cls.getName() + IMPLEMENTATION_SUFFIX).getDeclaredConstructor(new Class[0]);
            declaredConstructor.setAccessible(true);
            return (T) declaredConstructor.newInstance(new Object[0]);
        } catch (ClassNotFoundException unused) {
            return (T) getMapperFromServiceLoader(cls, classLoader);
        } catch (IllegalAccessException e) {
            e = e;
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            e = e2;
            throw new RuntimeException(e);
        } catch (InvocationTargetException e3) {
            e = e3;
            throw new RuntimeException(e);
        }
    }

    public static <T> Class<? extends T> getMapperClass(Class<T> cls) {
        try {
            return getMapperClass(cls, collectClassLoaders(cls.getClassLoader()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> Class<? extends T> getMapperClass(Class<T> cls, Iterable<ClassLoader> iterable) throws ClassNotFoundException {
        Iterator<ClassLoader> it = iterable.iterator();
        while (it.hasNext()) {
            Class<? extends T> clsDoGetMapperClass = doGetMapperClass(cls, it.next());
            if (clsDoGetMapperClass != null) {
                return clsDoGetMapperClass;
            }
        }
        throw new ClassNotFoundException("Cannot find implementation for " + cls.getName());
    }

    private static <T> Class<? extends T> doGetMapperClass(Class<T> cls, ClassLoader classLoader) {
        try {
            return (Class<? extends T>) classLoader.loadClass(cls.getName() + IMPLEMENTATION_SUFFIX);
        } catch (ClassNotFoundException unused) {
            Object mapperFromServiceLoader = getMapperFromServiceLoader(cls, classLoader);
            if (mapperFromServiceLoader != null) {
                return (Class<? extends T>) mapperFromServiceLoader.getClass();
            }
            return null;
        }
    }

    private static <T> T getMapperFromServiceLoader(Class<T> cls, ClassLoader classLoader) {
        Iterator it = ServiceLoader.load(cls, classLoader).iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            if (t != null) {
                return t;
            }
        }
        return null;
    }

    private static List<ClassLoader> collectClassLoaders(ClassLoader classLoader) {
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(classLoader);
        if (Thread.currentThread().getContextClassLoader() != null) {
            arrayList.add(Thread.currentThread().getContextClassLoader());
        }
        arrayList.add(Mappers.class.getClassLoader());
        return arrayList;
    }
}
