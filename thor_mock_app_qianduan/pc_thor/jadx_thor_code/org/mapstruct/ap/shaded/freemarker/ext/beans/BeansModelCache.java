package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.mapstruct.ap.shaded.freemarker.core._ConcurrentMapFactory;
import org.mapstruct.ap.shaded.freemarker.ext.util.ModelCache;
import org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
public class BeansModelCache extends ModelCache {
    static /* synthetic */ Class class$java$lang$Boolean;
    private final Map classToFactory;
    private final boolean classToFactoryIsConcurrent;
    private final Set mappedClassNames;
    private final BeansWrapper wrapper;

    BeansModelCache(BeansWrapper beansWrapper) {
        Map mapNewMaybeConcurrentHashMap = _ConcurrentMapFactory.newMaybeConcurrentHashMap();
        this.classToFactory = mapNewMaybeConcurrentHashMap;
        this.classToFactoryIsConcurrent = _ConcurrentMapFactory.isConcurrent(mapNewMaybeConcurrentHashMap);
        this.mappedClassNames = new HashSet();
        this.wrapper = beansWrapper;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.util.ModelCache
    protected boolean isCacheable(Object obj) throws Throwable {
        Class<?> cls = obj.getClass();
        Class<?> clsClass$ = class$java$lang$Boolean;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.Boolean");
            class$java$lang$Boolean = clsClass$;
        }
        return cls != clsClass$;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.util.ModelCache
    protected TemplateModel create(Object obj) {
        Class<?> cls = obj.getClass();
        ModelFactory modelFactory = this.classToFactoryIsConcurrent ? (ModelFactory) this.classToFactory.get(cls) : null;
        if (modelFactory == null) {
            synchronized (this.classToFactory) {
                modelFactory = (ModelFactory) this.classToFactory.get(cls);
                if (modelFactory == null) {
                    String name = cls.getName();
                    if (!this.mappedClassNames.add(name)) {
                        this.classToFactory.clear();
                        this.mappedClassNames.clear();
                        this.mappedClassNames.add(name);
                    }
                    modelFactory = this.wrapper.getModelFactory(cls);
                    this.classToFactory.put(cls, modelFactory);
                }
            }
        }
        return modelFactory.create(obj, this.wrapper);
    }
}
