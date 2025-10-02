package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.util.AbstractList;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelAdapter;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.UndeclaredThrowableException;

/* loaded from: classes3.dex */
class SequenceAdapter extends AbstractList implements TemplateModelAdapter {
    private final TemplateSequenceModel model;
    private final BeansWrapper wrapper;

    SequenceAdapter(TemplateSequenceModel templateSequenceModel, BeansWrapper beansWrapper) {
        this.model = templateSequenceModel;
        this.wrapper = beansWrapper;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelAdapter
    public TemplateModel getTemplateModel() {
        return this.model;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        try {
            return this.model.size();
        } catch (TemplateModelException e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public Object get(int i) {
        try {
            return this.wrapper.unwrap(this.model.get(i));
        } catch (TemplateModelException e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    public TemplateSequenceModel getTemplateSequenceModel() {
        return this.model;
    }
}
