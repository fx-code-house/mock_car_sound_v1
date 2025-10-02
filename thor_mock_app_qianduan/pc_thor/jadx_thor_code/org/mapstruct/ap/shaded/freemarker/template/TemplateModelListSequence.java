package org.mapstruct.ap.shaded.freemarker.template;

import java.util.List;

/* loaded from: classes3.dex */
public class TemplateModelListSequence implements TemplateSequenceModel {
    private List list;

    public TemplateModelListSequence(List list) {
        this.list = list;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public TemplateModel get(int i) {
        return (TemplateModel) this.list.get(i);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public int size() {
        return this.list.size();
    }

    public Object getWrappedObject() {
        return this.list;
    }
}
