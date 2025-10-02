package org.mapstruct.ap.shaded.freemarker.template;

/* loaded from: classes3.dex */
public abstract class WrappingTemplateModel {
    private static ObjectWrapper defaultObjectWrapper = DefaultObjectWrapper.instance;
    private ObjectWrapper objectWrapper;

    public static void setDefaultObjectWrapper(ObjectWrapper objectWrapper) {
        defaultObjectWrapper = objectWrapper;
    }

    public static ObjectWrapper getDefaultObjectWrapper() {
        return defaultObjectWrapper;
    }

    protected WrappingTemplateModel() {
        this(defaultObjectWrapper);
    }

    protected WrappingTemplateModel(ObjectWrapper objectWrapper) {
        objectWrapper = objectWrapper == null ? defaultObjectWrapper : objectWrapper;
        this.objectWrapper = objectWrapper;
        if (objectWrapper == null) {
            DefaultObjectWrapper defaultObjectWrapper2 = new DefaultObjectWrapper();
            defaultObjectWrapper = defaultObjectWrapper2;
            this.objectWrapper = defaultObjectWrapper2;
        }
    }

    public ObjectWrapper getObjectWrapper() {
        return this.objectWrapper;
    }

    public void setObjectWrapper(ObjectWrapper objectWrapper) {
        this.objectWrapper = objectWrapper;
    }

    protected final TemplateModel wrap(Object obj) throws TemplateModelException {
        return this.objectWrapper.wrap(obj);
    }
}
