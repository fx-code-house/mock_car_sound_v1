package org.mapstruct.ap.shaded.freemarker.ext.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelAdapter;

/* loaded from: classes3.dex */
public abstract class ModelCache {
    private boolean useCache = false;
    private Map modelCache = null;
    private ReferenceQueue refQueue = null;

    protected abstract TemplateModel create(Object obj);

    protected abstract boolean isCacheable(Object obj);

    protected ModelCache() {
    }

    public synchronized void setUseCache(boolean z) {
        this.useCache = z;
        if (z) {
            this.modelCache = new IdentityHashMap();
            this.refQueue = new ReferenceQueue();
        } else {
            this.modelCache = null;
            this.refQueue = null;
        }
    }

    public synchronized boolean getUseCache() {
        return this.useCache;
    }

    public TemplateModel getInstance(Object obj) {
        if (obj instanceof TemplateModel) {
            return (TemplateModel) obj;
        }
        if (obj instanceof TemplateModelAdapter) {
            return ((TemplateModelAdapter) obj).getTemplateModel();
        }
        if (this.useCache && isCacheable(obj)) {
            TemplateModel templateModelLookup = lookup(obj);
            if (templateModelLookup != null) {
                return templateModelLookup;
            }
            TemplateModel templateModelCreate = create(obj);
            register(templateModelCreate, obj);
            return templateModelCreate;
        }
        return create(obj);
    }

    public void clearCache() {
        Map map = this.modelCache;
        if (map != null) {
            synchronized (map) {
                this.modelCache.clear();
            }
        }
    }

    private final TemplateModel lookup(Object obj) {
        ModelReference modelReference;
        synchronized (this.modelCache) {
            modelReference = (ModelReference) this.modelCache.get(obj);
        }
        if (modelReference != null) {
            return modelReference.getModel();
        }
        return null;
    }

    private final void register(TemplateModel templateModel, Object obj) {
        synchronized (this.modelCache) {
            while (true) {
                ModelReference modelReference = (ModelReference) this.refQueue.poll();
                if (modelReference != null) {
                    this.modelCache.remove(modelReference.object);
                } else {
                    this.modelCache.put(obj, new ModelReference(templateModel, obj, this.refQueue));
                }
            }
        }
    }

    private static final class ModelReference extends SoftReference {
        Object object;

        ModelReference(TemplateModel templateModel, Object obj, ReferenceQueue referenceQueue) {
            super(templateModel, referenceQueue);
            this.object = obj;
        }

        TemplateModel getModel() {
            return (TemplateModel) get();
        }
    }
}
