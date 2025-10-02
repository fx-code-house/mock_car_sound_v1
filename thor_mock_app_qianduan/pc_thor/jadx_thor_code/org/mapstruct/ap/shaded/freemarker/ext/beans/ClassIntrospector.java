package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.ap.shaded.freemarker.core.BugException;
import org.mapstruct.ap.shaded.freemarker.core._ConcurrentMapFactory;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper;
import org.mapstruct.ap.shaded.freemarker.ext.util.ModelCache;
import org.mapstruct.ap.shaded.freemarker.log.Logger;
import org.mapstruct.ap.shaded.freemarker.template.utility.NullArgumentException;
import org.mapstruct.ap.shaded.freemarker.template.utility.SecurityUtilities;

/* loaded from: classes3.dex */
class ClassIntrospector {
    private static final Object ARGTYPES_KEY;
    private static final ClassChangeNotifier CLASS_CHANGE_NOTIFIER;
    static final Object CONSTRUCTORS_KEY;
    static final Object GENERIC_GET_KEY;
    private static final String JREBEL_INTEGRATION_ERROR_MSG = "Error initializing JRebel integration. JRebel integration disabled.";
    private static final String JREBEL_SDK_CLASS_NAME = "org.zeroturnaround.javarebel.ClassEventListener";
    static /* synthetic */ Class class$java$lang$Object;
    static /* synthetic */ Class class$java$lang$String;
    final boolean bugfixed;
    private final Map cache;
    private final Set cacheClassNames;
    private final Set classIntrospectionsInProgress;
    private int clearingCounter;
    final boolean exposeFields;
    final int exposureLevel;
    private final boolean hasSharedInstanceRestrictons;
    private final boolean isCacheConcurrentMap;
    final MethodAppearanceFineTuner methodAppearanceFineTuner;
    final MethodSorter methodSorter;
    private final List modelFactories;
    private final ReferenceQueue modelFactoriesRefQueue;
    private final boolean shared;
    private final Object sharedLock;
    private static final Logger LOG = Logger.getLogger("org.mapstruct.ap.shaded.freemarker.beans");
    static final boolean DEVELOPMENT_MODE = BooleanUtils.TRUE.equals(SecurityUtilities.getSystemProperty("org.mapstruct.ap.shaded.freemarker.development", BooleanUtils.FALSE));

    static {
        boolean z;
        try {
            Class.forName(JREBEL_SDK_CLASS_NAME);
            z = true;
        } catch (Throwable th) {
            try {
                if (!(th instanceof ClassNotFoundException)) {
                    LOG.error(JREBEL_INTEGRATION_ERROR_MSG, th);
                }
            } catch (Throwable unused) {
            }
            z = false;
        }
        ClassChangeNotifier classChangeNotifier = null;
        if (z) {
            try {
                classChangeNotifier = (ClassChangeNotifier) Class.forName("org.mapstruct.ap.shaded.freemarker.ext.beans.JRebelClassChangeNotifier").newInstance();
            } catch (Throwable th2) {
                try {
                    LOG.error(JREBEL_INTEGRATION_ERROR_MSG, th2);
                } catch (Throwable unused2) {
                }
            }
        }
        CLASS_CHANGE_NOTIFIER = classChangeNotifier;
        ARGTYPES_KEY = new Object();
        CONSTRUCTORS_KEY = new Object();
        GENERIC_GET_KEY = new Object();
    }

    ClassIntrospector(ClassIntrospectorBuilder classIntrospectorBuilder, Object obj) {
        this(classIntrospectorBuilder, obj, false, false);
    }

    ClassIntrospector(ClassIntrospectorBuilder classIntrospectorBuilder, Object obj, boolean z, boolean z2) {
        Map mapNewMaybeConcurrentHashMap = _ConcurrentMapFactory.newMaybeConcurrentHashMap(0, 0.75f, 16);
        this.cache = mapNewMaybeConcurrentHashMap;
        this.isCacheConcurrentMap = _ConcurrentMapFactory.isConcurrent(mapNewMaybeConcurrentHashMap);
        this.cacheClassNames = new HashSet(0);
        this.classIntrospectionsInProgress = new HashSet(0);
        this.modelFactories = new LinkedList();
        this.modelFactoriesRefQueue = new ReferenceQueue();
        NullArgumentException.check("sharedLock", obj);
        this.exposureLevel = classIntrospectorBuilder.getExposureLevel();
        this.exposeFields = classIntrospectorBuilder.getExposeFields();
        this.methodAppearanceFineTuner = classIntrospectorBuilder.getMethodAppearanceFineTuner();
        this.methodSorter = classIntrospectorBuilder.getMethodSorter();
        this.bugfixed = classIntrospectorBuilder.isBugfixed();
        this.sharedLock = obj;
        this.hasSharedInstanceRestrictons = z;
        this.shared = z2;
        ClassChangeNotifier classChangeNotifier = CLASS_CHANGE_NOTIFIER;
        if (classChangeNotifier != null) {
            classChangeNotifier.subscribe(this);
        }
    }

    ClassIntrospectorBuilder getPropertyAssignments() {
        return new ClassIntrospectorBuilder(this);
    }

    Map get(Class cls) {
        Map map;
        if (this.isCacheConcurrentMap && (map = (Map) this.cache.get(cls)) != null) {
            return map;
        }
        synchronized (this.sharedLock) {
            Map map2 = (Map) this.cache.get(cls);
            if (map2 != null) {
                return map2;
            }
            String name = cls.getName();
            if (this.cacheClassNames.contains(name)) {
                onSameNameClassesDetected(name);
            }
            while (map2 == null && this.classIntrospectionsInProgress.contains(cls)) {
                try {
                    this.sharedLock.wait();
                    map2 = (Map) this.cache.get(cls);
                } catch (InterruptedException e) {
                    throw new RuntimeException(new StringBuffer().append("Class inrospection data lookup aborded: ").append(e).toString());
                }
            }
            if (map2 != null) {
                return map2;
            }
            this.classIntrospectionsInProgress.add(cls);
            try {
                Map mapCreateClassIntrospectionData = createClassIntrospectionData(cls);
                synchronized (this.sharedLock) {
                    this.cache.put(cls, mapCreateClassIntrospectionData);
                    this.cacheClassNames.add(name);
                }
                synchronized (this.sharedLock) {
                    this.classIntrospectionsInProgress.remove(cls);
                    this.sharedLock.notifyAll();
                }
                return mapCreateClassIntrospectionData;
            } catch (Throwable th) {
                synchronized (this.sharedLock) {
                    this.classIntrospectionsInProgress.remove(cls);
                    this.sharedLock.notifyAll();
                    throw th;
                }
            }
        }
    }

    private Map createClassIntrospectionData(Class cls) throws SecurityException {
        HashMap map = new HashMap();
        if (this.exposeFields) {
            addFieldsToClassIntrospectionData(map, cls);
        }
        Map mapDiscoverAccessibleMethods = discoverAccessibleMethods(cls);
        addGenericGetToClassIntrospectionData(map, mapDiscoverAccessibleMethods);
        if (this.exposureLevel != 3) {
            try {
                addBeanInfoToClassIntrospectionData(map, cls, mapDiscoverAccessibleMethods);
            } catch (IntrospectionException e) {
                LOG.warn(new StringBuffer("Couldn't properly perform introspection for class ").append(cls).toString(), e);
                map.clear();
            }
        }
        addConstructorsToClassIntrospectionData(map, cls);
        if (map.size() > 1) {
            return map;
        }
        if (map.size() == 0) {
            return Collections.EMPTY_MAP;
        }
        Map.Entry entry = (Map.Entry) map.entrySet().iterator().next();
        return Collections.singletonMap(entry.getKey(), entry.getValue());
    }

    private void addFieldsToClassIntrospectionData(Map map, Class cls) throws SecurityException {
        for (Field field : cls.getFields()) {
            if ((field.getModifiers() & 8) == 0) {
                map.put(field.getName(), field);
            }
        }
    }

    private void addBeanInfoToClassIntrospectionData(Map map, Class cls, Map map2) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(cls);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int length = (propertyDescriptors != null ? propertyDescriptors.length : 0) - 1; length >= 0; length--) {
            addPropertyDescriptorToClassIntrospectionData(map, propertyDescriptors[length], cls, map2);
        }
        if (this.exposureLevel < 2) {
            BeansWrapper.MethodAppearanceDecision methodAppearanceDecision = new BeansWrapper.MethodAppearanceDecision();
            MethodDescriptor[] methodDescriptorArrSortMethodDescriptors = sortMethodDescriptors(beanInfo.getMethodDescriptors());
            BeansWrapper.MethodAppearanceDecisionInput methodAppearanceDecisionInput = null;
            for (int length2 = (methodDescriptorArrSortMethodDescriptors != null ? methodDescriptorArrSortMethodDescriptors.length : 0) - 1; length2 >= 0; length2--) {
                Method matchingAccessibleMethod = getMatchingAccessibleMethod(methodDescriptorArrSortMethodDescriptors[length2].getMethod(), map2);
                if (matchingAccessibleMethod != null && isAllowedToExpose(matchingAccessibleMethod)) {
                    methodAppearanceDecision.setDefaults(matchingAccessibleMethod);
                    if (this.methodAppearanceFineTuner != null) {
                        if (methodAppearanceDecisionInput == null) {
                            methodAppearanceDecisionInput = new BeansWrapper.MethodAppearanceDecisionInput();
                        }
                        methodAppearanceDecisionInput.setContainingClass(cls);
                        methodAppearanceDecisionInput.setMethod(matchingAccessibleMethod);
                        this.methodAppearanceFineTuner.process(methodAppearanceDecisionInput, methodAppearanceDecision);
                    }
                    PropertyDescriptor exposeAsProperty = methodAppearanceDecision.getExposeAsProperty();
                    if (exposeAsProperty != null && !(map.get(exposeAsProperty.getName()) instanceof PropertyDescriptor)) {
                        addPropertyDescriptorToClassIntrospectionData(map, exposeAsProperty, cls, map2);
                    }
                    String exposeMethodAs = methodAppearanceDecision.getExposeMethodAs();
                    if (exposeMethodAs != null) {
                        Object obj = map.get(exposeMethodAs);
                        if (obj instanceof Method) {
                            OverloadedMethods overloadedMethods = new OverloadedMethods(this.bugfixed);
                            overloadedMethods.addMethod((Method) obj);
                            overloadedMethods.addMethod(matchingAccessibleMethod);
                            map.put(exposeMethodAs, overloadedMethods);
                            getArgTypes(map).remove(obj);
                        } else if (obj instanceof OverloadedMethods) {
                            ((OverloadedMethods) obj).addMethod(matchingAccessibleMethod);
                        } else if (methodAppearanceDecision.getMethodShadowsProperty() || !(obj instanceof PropertyDescriptor)) {
                            map.put(exposeMethodAs, matchingAccessibleMethod);
                            getArgTypes(map).put(matchingAccessibleMethod, matchingAccessibleMethod.getParameterTypes());
                        }
                    }
                }
            }
        }
    }

    private void addPropertyDescriptorToClassIntrospectionData(Map map, PropertyDescriptor propertyDescriptor, Class cls, Map map2) {
        if (propertyDescriptor instanceof IndexedPropertyDescriptor) {
            IndexedPropertyDescriptor indexedPropertyDescriptor = (IndexedPropertyDescriptor) propertyDescriptor;
            Method indexedReadMethod = indexedPropertyDescriptor.getIndexedReadMethod();
            Method matchingAccessibleMethod = getMatchingAccessibleMethod(indexedReadMethod, map2);
            if (matchingAccessibleMethod == null || !isAllowedToExpose(matchingAccessibleMethod)) {
                return;
            }
            if (indexedReadMethod != matchingAccessibleMethod) {
                try {
                    indexedPropertyDescriptor = new IndexedPropertyDescriptor(indexedPropertyDescriptor.getName(), indexedPropertyDescriptor.getReadMethod(), (Method) null, matchingAccessibleMethod, (Method) null);
                } catch (IntrospectionException e) {
                    LOG.warn(new StringBuffer("Failed creating a publicly-accessible property descriptor for ").append(cls.getName()).append(" indexed property ").append(propertyDescriptor.getName()).append(", read method ").append(matchingAccessibleMethod).toString(), e);
                    return;
                }
            }
            map.put(indexedPropertyDescriptor.getName(), indexedPropertyDescriptor);
            getArgTypes(map).put(matchingAccessibleMethod, matchingAccessibleMethod.getParameterTypes());
            return;
        }
        Method readMethod = propertyDescriptor.getReadMethod();
        Method matchingAccessibleMethod2 = getMatchingAccessibleMethod(readMethod, map2);
        if (matchingAccessibleMethod2 == null || !isAllowedToExpose(matchingAccessibleMethod2)) {
            return;
        }
        if (readMethod != matchingAccessibleMethod2) {
            try {
                PropertyDescriptor propertyDescriptor2 = new PropertyDescriptor(propertyDescriptor.getName(), matchingAccessibleMethod2, (Method) null);
                try {
                    propertyDescriptor2.setReadMethod(matchingAccessibleMethod2);
                    propertyDescriptor = propertyDescriptor2;
                } catch (IntrospectionException e2) {
                    e = e2;
                    propertyDescriptor = propertyDescriptor2;
                    LOG.warn(new StringBuffer("Failed creating a publicly-accessible property descriptor for ").append(cls.getName()).append(" property ").append(propertyDescriptor.getName()).append(", read method ").append(matchingAccessibleMethod2).toString(), e);
                    return;
                }
            } catch (IntrospectionException e3) {
                e = e3;
            }
        }
        map.put(propertyDescriptor.getName(), propertyDescriptor);
    }

    private void addGenericGetToClassIntrospectionData(Map map, Map map2) {
        Method firstAccessibleMethod = getFirstAccessibleMethod(MethodSignature.GET_STRING_SIGNATURE, map2);
        if (firstAccessibleMethod == null) {
            firstAccessibleMethod = getFirstAccessibleMethod(MethodSignature.GET_OBJECT_SIGNATURE, map2);
        }
        if (firstAccessibleMethod != null) {
            map.put(GENERIC_GET_KEY, firstAccessibleMethod);
        }
    }

    private void addConstructorsToClassIntrospectionData(Map map, Class cls) throws SecurityException {
        try {
            Constructor<?>[] constructors = cls.getConstructors();
            if (constructors.length == 1) {
                Constructor<?> constructor = constructors[0];
                map.put(CONSTRUCTORS_KEY, new SimpleMethod(constructor, constructor.getParameterTypes()));
            } else if (constructors.length > 1) {
                OverloadedMethods overloadedMethods = new OverloadedMethods(this.bugfixed);
                for (Constructor<?> constructor2 : constructors) {
                    overloadedMethods.addConstructor(constructor2);
                }
                map.put(CONSTRUCTORS_KEY, overloadedMethods);
            }
        } catch (SecurityException e) {
            LOG.warn(new StringBuffer("Can't discover constructors for class ").append(cls.getName()).toString(), e);
        }
    }

    private static Map discoverAccessibleMethods(Class cls) throws SecurityException {
        HashMap map = new HashMap();
        discoverAccessibleMethods(cls, map);
        return map;
    }

    private static void discoverAccessibleMethods(Class cls, Map map) throws SecurityException {
        if (Modifier.isPublic(cls.getModifiers())) {
            try {
                for (Method method : cls.getMethods()) {
                    MethodSignature methodSignature = new MethodSignature(method);
                    List linkedList = (List) map.get(methodSignature);
                    if (linkedList == null) {
                        linkedList = new LinkedList();
                        map.put(methodSignature, linkedList);
                    }
                    linkedList.add(method);
                }
                return;
            } catch (SecurityException e) {
                LOG.warn(new StringBuffer("Could not discover accessible methods of class ").append(cls.getName()).append(", attemping superclasses/interfaces.").toString(), e);
            }
        }
        for (Class<?> cls2 : cls.getInterfaces()) {
            discoverAccessibleMethods(cls2, map);
        }
        Class superclass = cls.getSuperclass();
        if (superclass != null) {
            discoverAccessibleMethods(superclass, map);
        }
    }

    private static Method getMatchingAccessibleMethod(Method method, Map map) {
        List<Method> list;
        if (method == null || (list = (List) map.get(new MethodSignature(method))) == null) {
            return null;
        }
        for (Method method2 : list) {
            if (method2.getReturnType() == method.getReturnType()) {
                return method2;
            }
        }
        return null;
    }

    private static Method getFirstAccessibleMethod(MethodSignature methodSignature, Map map) {
        List list = (List) map.get(methodSignature);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return (Method) list.iterator().next();
    }

    private MethodDescriptor[] sortMethodDescriptors(MethodDescriptor[] methodDescriptorArr) {
        MethodSorter methodSorter = this.methodSorter;
        return methodSorter != null ? methodSorter.sortMethodDescriptors(methodDescriptorArr) : methodDescriptorArr;
    }

    boolean isAllowedToExpose(Method method) {
        return this.exposureLevel < 1 || !UnsafeMethods.isUnsafeMethod(method);
    }

    private static Map getArgTypes(Map map) {
        Object obj = ARGTYPES_KEY;
        Map map2 = (Map) map.get(obj);
        if (map2 != null) {
            return map2;
        }
        HashMap map3 = new HashMap();
        map.put(obj, map3);
        return map3;
    }

    private static final class MethodSignature {
        private static final MethodSignature GET_OBJECT_SIGNATURE;
        private static final MethodSignature GET_STRING_SIGNATURE;
        private final Class[] args;
        private final String name;

        static {
            Class clsClass$;
            Class clsClass$2;
            Class[] clsArr = new Class[1];
            if (ClassIntrospector.class$java$lang$String == null) {
                clsClass$ = ClassIntrospector.class$("java.lang.String");
                ClassIntrospector.class$java$lang$String = clsClass$;
            } else {
                clsClass$ = ClassIntrospector.class$java$lang$String;
            }
            clsArr[0] = clsClass$;
            GET_STRING_SIGNATURE = new MethodSignature("get", clsArr);
            Class[] clsArr2 = new Class[1];
            if (ClassIntrospector.class$java$lang$Object == null) {
                clsClass$2 = ClassIntrospector.class$("java.lang.Object");
                ClassIntrospector.class$java$lang$Object = clsClass$2;
            } else {
                clsClass$2 = ClassIntrospector.class$java$lang$Object;
            }
            clsArr2[0] = clsClass$2;
            GET_OBJECT_SIGNATURE = new MethodSignature("get", clsArr2);
        }

        private MethodSignature(String str, Class[] clsArr) {
            this.name = str;
            this.args = clsArr;
        }

        MethodSignature(Method method) {
            this(method.getName(), method.getParameterTypes());
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof MethodSignature)) {
                return false;
            }
            MethodSignature methodSignature = (MethodSignature) obj;
            return methodSignature.name.equals(this.name) && Arrays.equals(this.args, methodSignature.args);
        }

        public int hashCode() {
            return this.name.hashCode() ^ this.args.length;
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    void clearCache() {
        if (getHasSharedInstanceRestrictons()) {
            throw new IllegalStateException(new StringBuffer("It's not allowed to clear the whole cache in a read-only ").append(getClass().getName()).append("instance. Use removeFromClassIntrospectionCache(String prefix) instead.").toString());
        }
        forcedClearCache();
    }

    private void forcedClearCache() {
        synchronized (this.sharedLock) {
            this.cache.clear();
            this.cacheClassNames.clear();
            this.clearingCounter++;
            Iterator it = this.modelFactories.iterator();
            while (it.hasNext()) {
                Object obj = ((WeakReference) it.next()).get();
                if (obj != null) {
                    if (obj instanceof ClassBasedModelFactory) {
                        ((ClassBasedModelFactory) obj).clearCache();
                    } else if (obj instanceof ModelCache) {
                        ((ModelCache) obj).clearCache();
                    } else {
                        throw new BugException();
                    }
                }
            }
            removeClearedModelFactoryReferences();
        }
    }

    void remove(Class cls) {
        synchronized (this.sharedLock) {
            this.cache.remove(cls);
            this.cacheClassNames.remove(cls.getName());
            this.clearingCounter++;
            Iterator it = this.modelFactories.iterator();
            while (it.hasNext()) {
                Object obj = ((WeakReference) it.next()).get();
                if (obj != null) {
                    if (obj instanceof ClassBasedModelFactory) {
                        ((ClassBasedModelFactory) obj).removeFromCache(cls);
                    } else if (obj instanceof ModelCache) {
                        ((ModelCache) obj).clearCache();
                    } else {
                        throw new BugException();
                    }
                }
            }
            removeClearedModelFactoryReferences();
        }
    }

    int getClearingCounter() {
        int i;
        synchronized (this.sharedLock) {
            i = this.clearingCounter;
        }
        return i;
    }

    private void onSameNameClassesDetected(String str) {
        Logger logger = LOG;
        if (logger.isInfoEnabled()) {
            logger.info(new StringBuffer("Detected multiple classes with the same name, \"").append(str).append("\". Assuming it was a class-reloading. Clearing class introspection caches to release old data.").toString());
        }
        forcedClearCache();
    }

    void registerModelFactory(ClassBasedModelFactory classBasedModelFactory) {
        registerModelFactory((Object) classBasedModelFactory);
    }

    void registerModelFactory(ModelCache modelCache) {
        registerModelFactory((Object) modelCache);
    }

    private void registerModelFactory(Object obj) {
        synchronized (this.sharedLock) {
            this.modelFactories.add(new WeakReference(obj, this.modelFactoriesRefQueue));
            removeClearedModelFactoryReferences();
        }
    }

    void unregisterModelFactory(ClassBasedModelFactory classBasedModelFactory) {
        unregisterModelFactory((Object) classBasedModelFactory);
    }

    void unregisterModelFactory(ModelCache modelCache) {
        unregisterModelFactory((Object) modelCache);
    }

    void unregisterModelFactory(Object obj) {
        synchronized (this.sharedLock) {
            Iterator it = this.modelFactories.iterator();
            while (it.hasNext()) {
                if (((Reference) it.next()).get() == obj) {
                    it.remove();
                }
            }
        }
    }

    private void removeClearedModelFactoryReferences() {
        while (true) {
            Reference referencePoll = this.modelFactoriesRefQueue.poll();
            if (referencePoll == null) {
                return;
            }
            synchronized (this.sharedLock) {
                Iterator it = this.modelFactories.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else if (it.next() == referencePoll) {
                        it.remove();
                        break;
                    }
                }
            }
        }
    }

    static Class[] getArgTypes(Map map, AccessibleObject accessibleObject) {
        return (Class[]) ((Map) map.get(ARGTYPES_KEY)).get(accessibleObject);
    }

    int keyCount(Class cls) {
        Map map = get(cls);
        int size = map.size();
        if (map.containsKey(CONSTRUCTORS_KEY)) {
            size--;
        }
        if (map.containsKey(GENERIC_GET_KEY)) {
            size--;
        }
        return map.containsKey(ARGTYPES_KEY) ? size - 1 : size;
    }

    Set keySet(Class cls) {
        HashSet hashSet = new HashSet(get(cls).keySet());
        hashSet.remove(CONSTRUCTORS_KEY);
        hashSet.remove(GENERIC_GET_KEY);
        hashSet.remove(ARGTYPES_KEY);
        return hashSet;
    }

    int getExposureLevel() {
        return this.exposureLevel;
    }

    boolean getExposeFields() {
        return this.exposeFields;
    }

    MethodAppearanceFineTuner getMethodAppearanceFineTuner() {
        return this.methodAppearanceFineTuner;
    }

    MethodSorter getMethodSorter() {
        return this.methodSorter;
    }

    boolean getHasSharedInstanceRestrictons() {
        return this.hasSharedInstanceRestrictons;
    }

    boolean isShared() {
        return this.shared;
    }

    Object getSharedLock() {
        return this.sharedLock;
    }

    Object[] getRegisteredModelFactoriesSnapshot() {
        Object[] array;
        synchronized (this.sharedLock) {
            array = this.modelFactories.toArray();
        }
        return array;
    }
}
