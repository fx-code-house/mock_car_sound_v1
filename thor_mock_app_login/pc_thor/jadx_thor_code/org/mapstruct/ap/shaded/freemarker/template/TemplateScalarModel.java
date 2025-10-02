package org.mapstruct.ap.shaded.freemarker.template;

/* loaded from: classes3.dex */
public interface TemplateScalarModel extends TemplateModel {
    public static final TemplateModel EMPTY_STRING = new SimpleScalar("");

    String getAsString() throws TemplateModelException;
}
