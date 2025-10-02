package org.mapstruct.ap.shaded.freemarker.ext.beans;

import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
class StaticModels extends ClassBasedModelFactory {
    StaticModels(BeansWrapper beansWrapper) {
        super(beansWrapper);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.ClassBasedModelFactory
    protected TemplateModel createModel(Class cls) throws TemplateModelException {
        return new StaticModel(cls, getWrapper());
    }
}
