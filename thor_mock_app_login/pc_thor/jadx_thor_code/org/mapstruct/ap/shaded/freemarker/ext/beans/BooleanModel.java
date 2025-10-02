package org.mapstruct.ap.shaded.freemarker.ext.beans;

import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;

/* loaded from: classes3.dex */
public class BooleanModel extends BeanModel implements TemplateBooleanModel {
    private final boolean value;

    public BooleanModel(Boolean bool, BeansWrapper beansWrapper) {
        super(bool, beansWrapper, false);
        this.value = bool.booleanValue();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel
    public boolean getAsBoolean() {
        return this.value;
    }
}
