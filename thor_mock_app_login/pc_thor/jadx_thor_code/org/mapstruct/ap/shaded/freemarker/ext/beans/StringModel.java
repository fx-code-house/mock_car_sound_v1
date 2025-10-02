package org.mapstruct.ap.shaded.freemarker.ext.beans;

import org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;

/* loaded from: classes3.dex */
public class StringModel extends BeanModel implements TemplateScalarModel {
    static final ModelFactory FACTORY = new ModelFactory() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.StringModel.1
        @Override // org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory
        public TemplateModel create(Object obj, ObjectWrapper objectWrapper) {
            return new StringModel(obj, (BeansWrapper) objectWrapper);
        }
    };

    public StringModel(Object obj, BeansWrapper beansWrapper) {
        super(obj, beansWrapper);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
    public String getAsString() {
        return this.object.toString();
    }
}
