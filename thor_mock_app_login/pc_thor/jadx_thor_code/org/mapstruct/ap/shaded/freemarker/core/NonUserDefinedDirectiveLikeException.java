package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
class NonUserDefinedDirectiveLikeException extends UnexpectedTypeException {
    private static final Class[] EXPECTED_TYPES;
    static /* synthetic */ Class class$freemarker$core$Macro;
    static /* synthetic */ Class class$freemarker$template$TemplateDirectiveModel;
    static /* synthetic */ Class class$freemarker$template$TemplateTransformModel;

    static {
        Class[] clsArr = new Class[3];
        Class clsClass$ = class$freemarker$template$TemplateDirectiveModel;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateDirectiveModel");
            class$freemarker$template$TemplateDirectiveModel = clsClass$;
        }
        clsArr[0] = clsClass$;
        Class clsClass$2 = class$freemarker$template$TemplateTransformModel;
        if (clsClass$2 == null) {
            clsClass$2 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel");
            class$freemarker$template$TemplateTransformModel = clsClass$2;
        }
        clsArr[1] = clsClass$2;
        Class clsClass$3 = class$freemarker$core$Macro;
        if (clsClass$3 == null) {
            clsClass$3 = class$("org.mapstruct.ap.shaded.freemarker.core.Macro");
            class$freemarker$core$Macro = clsClass$3;
        }
        clsArr[2] = clsClass$3;
        EXPECTED_TYPES = clsArr;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public NonUserDefinedDirectiveLikeException(Environment environment) {
        super(environment, "Expecting user-defined directive, transform or macro value here");
    }

    public NonUserDefinedDirectiveLikeException(String str, Environment environment) {
        super(environment, str);
    }

    NonUserDefinedDirectiveLikeException(Environment environment, _ErrorDescriptionBuilder _errordescriptionbuilder) {
        super(environment, _errordescriptionbuilder);
    }

    NonUserDefinedDirectiveLikeException(Expression expression, TemplateModel templateModel, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "user-defined directive, transform or macro", EXPECTED_TYPES, environment);
    }

    NonUserDefinedDirectiveLikeException(Expression expression, TemplateModel templateModel, String str, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "user-defined directive, transform or macro", EXPECTED_TYPES, str, environment);
    }

    NonUserDefinedDirectiveLikeException(Expression expression, TemplateModel templateModel, String[] strArr, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "user-defined directive, transform or macro", EXPECTED_TYPES, strArr, environment);
    }
}
