package org.mapstruct.ap.shaded.freemarker.ext.beans;

import org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;

/* loaded from: classes3.dex */
public class NumberModel extends BeanModel implements TemplateNumberModel {
    static final ModelFactory FACTORY = new ModelFactory() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.NumberModel.1
        @Override // org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory
        public TemplateModel create(Object obj, ObjectWrapper objectWrapper) {
            return new NumberModel((Number) obj, (BeansWrapper) objectWrapper);
        }
    };

    public NumberModel(Number number, BeansWrapper beansWrapper) {
        super(number, beansWrapper);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel
    public Number getAsNumber() {
        return (Number) this.object;
    }
}
