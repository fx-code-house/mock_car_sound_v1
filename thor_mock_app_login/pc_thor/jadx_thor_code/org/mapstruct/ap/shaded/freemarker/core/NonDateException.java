package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
public class NonDateException extends UnexpectedTypeException {
    private static final Class[] EXPECTED_TYPES;
    static /* synthetic */ Class class$freemarker$template$TemplateDateModel;

    static {
        Class[] clsArr = new Class[1];
        Class clsClass$ = class$freemarker$template$TemplateDateModel;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel");
            class$freemarker$template$TemplateDateModel = clsClass$;
        }
        clsArr[0] = clsClass$;
        EXPECTED_TYPES = clsArr;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public NonDateException(Environment environment) {
        super(environment, "Expecting date/time value here");
    }

    public NonDateException(String str, Environment environment) {
        super(environment, str);
    }

    NonDateException(Expression expression, TemplateModel templateModel, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "date/time", EXPECTED_TYPES, environment);
    }

    NonDateException(Expression expression, TemplateModel templateModel, String str, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "date/time", EXPECTED_TYPES, str, environment);
    }

    NonDateException(Expression expression, TemplateModel templateModel, String[] strArr, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "date/time", EXPECTED_TYPES, strArr, environment);
    }
}
