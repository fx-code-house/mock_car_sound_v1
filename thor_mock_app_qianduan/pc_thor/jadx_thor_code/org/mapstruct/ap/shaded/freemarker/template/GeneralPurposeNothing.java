package org.mapstruct.ap.shaded.freemarker.template;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
final class GeneralPurposeNothing implements TemplateBooleanModel, TemplateScalarModel, TemplateSequenceModel, TemplateHashModelEx, TemplateMethodModelEx {
    private static final TemplateModel instance = new GeneralPurposeNothing();
    private static final TemplateCollectionModel EMPTY_COLLECTION = new SimpleCollection(new ArrayList(0));

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
    public Object exec(List list) {
        return null;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public TemplateModel get(String str) {
        return null;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel
    public boolean getAsBoolean() {
        return false;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
    public String getAsString() {
        return "";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        return true;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public int size() {
        return 0;
    }

    private GeneralPurposeNothing() {
    }

    static TemplateModel getInstance() {
        return instance;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public TemplateModel get(int i) throws TemplateModelException {
        throw new TemplateModelException("Empty list");
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public TemplateCollectionModel keys() {
        return EMPTY_COLLECTION;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public TemplateCollectionModel values() {
        return EMPTY_COLLECTION;
    }
}
