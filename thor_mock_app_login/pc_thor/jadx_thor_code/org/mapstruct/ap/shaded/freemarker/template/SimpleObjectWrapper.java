package org.mapstruct.ap.shaded.freemarker.template;

/* loaded from: classes3.dex */
public class SimpleObjectWrapper extends DefaultObjectWrapper {
    static final SimpleObjectWrapper instance = new SimpleObjectWrapper();

    public SimpleObjectWrapper() {
    }

    public SimpleObjectWrapper(Version version) {
        super(version);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.DefaultObjectWrapper
    protected TemplateModel handleUnknownType(Object obj) throws TemplateModelException {
        throw new TemplateModelException(new StringBuffer("Don't know how to present an object of this type to a template: ").append(obj.getClass().getName()).toString());
    }
}
