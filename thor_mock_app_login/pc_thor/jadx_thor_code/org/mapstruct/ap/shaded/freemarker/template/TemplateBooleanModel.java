package org.mapstruct.ap.shaded.freemarker.template;

/* loaded from: classes3.dex */
public interface TemplateBooleanModel extends TemplateModel {
    public static final TemplateBooleanModel FALSE = new SerializableTemplateBooleanModel() { // from class: org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel.1
        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel
        public boolean getAsBoolean() {
            return false;
        }

        private Object readResolve() {
            return FALSE;
        }
    };
    public static final TemplateBooleanModel TRUE = new SerializableTemplateBooleanModel() { // from class: org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel.2
        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel
        public boolean getAsBoolean() {
            return true;
        }

        private Object readResolve() {
            return TRUE;
        }
    };

    boolean getAsBoolean() throws TemplateModelException;
}
