package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
public class NonStringException extends UnexpectedTypeException {
    private static final String DEFAULT_DESCRIPTION = "Expecting string or something automatically convertible to string (number, date or boolean) value here";
    static final Class[] STRING_COERCABLE_TYPES;
    static final String STRING_COERCABLE_TYPES_DESC = "string or something automatically convertible to string (number, date or boolean)";
    static /* synthetic */ Class class$freemarker$template$TemplateBooleanModel;
    static /* synthetic */ Class class$freemarker$template$TemplateDateModel;
    static /* synthetic */ Class class$freemarker$template$TemplateNumberModel;
    static /* synthetic */ Class class$freemarker$template$TemplateScalarModel;

    static {
        Class[] clsArr = new Class[4];
        Class clsClass$ = class$freemarker$template$TemplateScalarModel;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel");
            class$freemarker$template$TemplateScalarModel = clsClass$;
        }
        clsArr[0] = clsClass$;
        Class clsClass$2 = class$freemarker$template$TemplateNumberModel;
        if (clsClass$2 == null) {
            clsClass$2 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel");
            class$freemarker$template$TemplateNumberModel = clsClass$2;
        }
        clsArr[1] = clsClass$2;
        Class clsClass$3 = class$freemarker$template$TemplateDateModel;
        if (clsClass$3 == null) {
            clsClass$3 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel");
            class$freemarker$template$TemplateDateModel = clsClass$3;
        }
        clsArr[2] = clsClass$3;
        Class clsClass$4 = class$freemarker$template$TemplateBooleanModel;
        if (clsClass$4 == null) {
            clsClass$4 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel");
            class$freemarker$template$TemplateBooleanModel = clsClass$4;
        }
        clsArr[3] = clsClass$4;
        STRING_COERCABLE_TYPES = clsArr;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public NonStringException(Environment environment) {
        super(environment, DEFAULT_DESCRIPTION);
    }

    public NonStringException(String str, Environment environment) {
        super(environment, str);
    }

    NonStringException(Environment environment, _ErrorDescriptionBuilder _errordescriptionbuilder) {
        super(environment, _errordescriptionbuilder);
    }

    NonStringException(Expression expression, TemplateModel templateModel, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, STRING_COERCABLE_TYPES_DESC, STRING_COERCABLE_TYPES, environment);
    }

    NonStringException(Expression expression, TemplateModel templateModel, String str, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, STRING_COERCABLE_TYPES_DESC, STRING_COERCABLE_TYPES, str, environment);
    }

    NonStringException(Expression expression, TemplateModel templateModel, String[] strArr, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, STRING_COERCABLE_TYPES_DESC, STRING_COERCABLE_TYPES, strArr, environment);
    }
}
