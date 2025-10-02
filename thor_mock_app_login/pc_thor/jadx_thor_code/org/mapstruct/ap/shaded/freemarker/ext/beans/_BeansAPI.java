package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.core.BugException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.utility.CollectionUtils;

/* loaded from: classes3.dex */
public class _BeansAPI {

    public interface _BeansWrapperSubclassFactory {
        BeansWrapper create(BeansWrapperConfiguration beansWrapperConfiguration);
    }

    private _BeansAPI() {
    }

    public static String getAsClassicCompatibleString(BeanModel beanModel) {
        return beanModel.getAsClassicCompatibleString();
    }

    public static Object newInstance(Class cls, Object[] objArr, BeansWrapper beansWrapper) throws IllegalAccessException, NoSuchMethodException, InstantiationException, TemplateModelException, IllegalArgumentException, InvocationTargetException {
        return newInstance(getConstructorDescriptor(cls, objArr), objArr, beansWrapper);
    }

    private static CallableMemberDescriptor getConstructorDescriptor(Class cls, Object[] objArr) throws NoSuchMethodException, SecurityException {
        if (objArr == null) {
            objArr = CollectionUtils.EMPTY_OBJECT_ARRAY;
        }
        ArgumentTypes argumentTypes = new ArgumentTypes(objArr, true);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Constructor<?> constructor : cls.getConstructors()) {
            ReflectionCallableMemberDescriptor reflectionCallableMemberDescriptor = new ReflectionCallableMemberDescriptor(constructor, constructor.getParameterTypes());
            if (!_MethodUtil.isVarargs(constructor)) {
                arrayList.add(reflectionCallableMemberDescriptor);
            } else {
                arrayList2.add(reflectionCallableMemberDescriptor);
            }
        }
        MaybeEmptyCallableMemberDescriptor mostSpecific = argumentTypes.getMostSpecific(arrayList, false);
        if (mostSpecific == EmptyCallableMemberDescriptor.NO_SUCH_METHOD) {
            mostSpecific = argumentTypes.getMostSpecific(arrayList2, true);
        }
        if (mostSpecific instanceof EmptyCallableMemberDescriptor) {
            if (mostSpecific == EmptyCallableMemberDescriptor.NO_SUCH_METHOD) {
                throw new NoSuchMethodException(new StringBuffer("There's no public ").append(cls.getName()).append(" constructor with compatible parameter list.").toString());
            }
            if (mostSpecific == EmptyCallableMemberDescriptor.AMBIGUOUS_METHOD) {
                throw new NoSuchMethodException(new StringBuffer("There are multiple public ").append(cls.getName()).append(" constructors that match the compatible parameter list with the same preferability.").toString());
            }
            throw new NoSuchMethodException();
        }
        return (CallableMemberDescriptor) mostSpecific;
    }

    private static Object newInstance(CallableMemberDescriptor callableMemberDescriptor, Object[] objArr, BeansWrapper beansWrapper) throws IllegalAccessException, InstantiationException, TemplateModelException, ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException, InvocationTargetException {
        if (objArr == null) {
            objArr = CollectionUtils.EMPTY_OBJECT_ARRAY;
        }
        if (callableMemberDescriptor.isVarargs()) {
            Class[] paramTypes = callableMemberDescriptor.getParamTypes();
            int length = paramTypes.length - 1;
            Object[] objArr2 = new Object[length + 1];
            for (int i = 0; i < length; i++) {
                objArr2[i] = objArr[i];
            }
            Class<?> componentType = paramTypes[length].getComponentType();
            int length2 = objArr.length - length;
            Object objNewInstance = Array.newInstance(componentType, length2);
            for (int i2 = 0; i2 < length2; i2++) {
                Array.set(objNewInstance, i2, objArr[length + i2]);
            }
            objArr2[length] = objNewInstance;
            objArr = objArr2;
        }
        return callableMemberDescriptor.invokeConstructor(beansWrapper, objArr);
    }

    public static BeansWrapper getBeansWrapperSubclassSingleton(BeansWrapperConfiguration beansWrapperConfiguration, Map map, ReferenceQueue referenceQueue, _BeansWrapperSubclassFactory _beanswrappersubclassfactory) {
        Map map2;
        Reference reference;
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        synchronized (map) {
            map2 = (Map) map.get(contextClassLoader);
            if (map2 == null) {
                map2 = new HashMap();
                map.put(contextClassLoader, map2);
                reference = null;
            } else {
                reference = (Reference) map2.get(beansWrapperConfiguration);
            }
        }
        BeansWrapper beansWrapper = reference != null ? (BeansWrapper) reference.get() : null;
        if (beansWrapper != null) {
            return beansWrapper;
        }
        BeansWrapperConfiguration beansWrapperConfiguration2 = (BeansWrapperConfiguration) beansWrapperConfiguration.clone(true);
        BeansWrapper beansWrapperCreate = _beanswrappersubclassfactory.create(beansWrapperConfiguration2);
        if (!beansWrapperCreate.isWriteProtected()) {
            throw new BugException();
        }
        synchronized (map) {
            Reference reference2 = (Reference) map2.get(beansWrapperConfiguration2);
            BeansWrapper beansWrapper2 = reference2 != null ? (BeansWrapper) reference2.get() : null;
            if (beansWrapper2 == null) {
                map2.put(beansWrapperConfiguration2, new WeakReference(beansWrapperCreate, referenceQueue));
            } else {
                beansWrapperCreate = beansWrapper2;
            }
        }
        removeClearedReferencesFromCache(map, referenceQueue);
        return beansWrapperCreate;
    }

    private static void removeClearedReferencesFromCache(Map map, ReferenceQueue referenceQueue) {
        while (true) {
            Reference referencePoll = referenceQueue.poll();
            if (referencePoll == null) {
                return;
            }
            synchronized (map) {
                Iterator it = map.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Iterator it2 = ((Map) it.next()).values().iterator();
                    while (it2.hasNext()) {
                        if (it2.next() == referencePoll) {
                            it2.remove();
                            break;
                        }
                    }
                }
            }
        }
    }
}
