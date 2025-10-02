package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.mapstruct.ap.shaded.freemarker.core._ConcurrentMapFactory;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;

/* loaded from: classes3.dex */
abstract class ClassBasedModelFactory implements TemplateHashModel {
    private final Map cache;
    private final Set classIntrospectionsInProgress;
    private final boolean isCacheConcurrentMap;
    private final BeansWrapper wrapper;

    protected abstract TemplateModel createModel(Class cls) throws TemplateModelException;

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        return false;
    }

    protected ClassBasedModelFactory(BeansWrapper beansWrapper) {
        Map mapNewMaybeConcurrentHashMap = _ConcurrentMapFactory.newMaybeConcurrentHashMap();
        this.cache = mapNewMaybeConcurrentHashMap;
        this.isCacheConcurrentMap = _ConcurrentMapFactory.isConcurrent(mapNewMaybeConcurrentHashMap);
        this.classIntrospectionsInProgress = new HashSet();
        this.wrapper = beansWrapper;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public TemplateModel get(String str) throws TemplateModelException {
        try {
            return getInternal(str);
        } catch (Exception e) {
            if (e instanceof TemplateModelException) {
                throw ((TemplateModelException) e);
            }
            throw new TemplateModelException(e);
        }
    }

    private TemplateModel getInternal(String str) throws TemplateModelException, ClassNotFoundException {
        TemplateModel templateModel;
        if (this.isCacheConcurrentMap && (templateModel = (TemplateModel) this.cache.get(str)) != null) {
            return templateModel;
        }
        Object sharedInrospectionLock = this.wrapper.getSharedInrospectionLock();
        synchronized (sharedInrospectionLock) {
            TemplateModel templateModel2 = (TemplateModel) this.cache.get(str);
            if (templateModel2 != null) {
                return templateModel2;
            }
            while (templateModel2 == null && this.classIntrospectionsInProgress.contains(str)) {
                try {
                    sharedInrospectionLock.wait();
                    templateModel2 = (TemplateModel) this.cache.get(str);
                } catch (InterruptedException e) {
                    throw new RuntimeException(new StringBuffer().append("Class inrospection data lookup aborded: ").append(e).toString());
                }
            }
            if (templateModel2 != null) {
                return templateModel2;
            }
            this.classIntrospectionsInProgress.add(str);
            ClassIntrospector classIntrospector = this.wrapper.getClassIntrospector();
            int clearingCounter = classIntrospector.getClearingCounter();
            try {
                Class clsForName = ClassUtil.forName(str);
                classIntrospector.get(clsForName);
                TemplateModel templateModelCreateModel = createModel(clsForName);
                if (templateModelCreateModel != null) {
                    synchronized (sharedInrospectionLock) {
                        if (classIntrospector == this.wrapper.getClassIntrospector() && clearingCounter == classIntrospector.getClearingCounter()) {
                            this.cache.put(str, templateModelCreateModel);
                        }
                    }
                }
                synchronized (sharedInrospectionLock) {
                    this.classIntrospectionsInProgress.remove(str);
                    sharedInrospectionLock.notifyAll();
                }
                return templateModelCreateModel;
            } catch (Throwable th) {
                synchronized (sharedInrospectionLock) {
                    this.classIntrospectionsInProgress.remove(str);
                    sharedInrospectionLock.notifyAll();
                    throw th;
                }
            }
        }
    }

    void clearCache() {
        synchronized (this.wrapper.getSharedInrospectionLock()) {
            this.cache.clear();
        }
    }

    void removeFromCache(Class cls) {
        synchronized (this.wrapper.getSharedInrospectionLock()) {
            this.cache.remove(cls.getName());
        }
    }

    protected BeansWrapper getWrapper() {
        return this.wrapper;
    }
}
