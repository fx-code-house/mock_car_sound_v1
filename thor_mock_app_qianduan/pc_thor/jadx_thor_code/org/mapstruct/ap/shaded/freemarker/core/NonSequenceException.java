package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
public class NonSequenceException extends UnexpectedTypeException {
    private static final Class[] EXPECTED_TYPES;
    static /* synthetic */ Class class$freemarker$template$TemplateSequenceModel;

    static {
        Class[] clsArr = new Class[1];
        Class clsClass$ = class$freemarker$template$TemplateSequenceModel;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel");
            class$freemarker$template$TemplateSequenceModel = clsClass$;
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

    public NonSequenceException(Environment environment) {
        super(environment, "Expecting sequence value here");
    }

    public NonSequenceException(String str, Environment environment) {
        super(environment, str);
    }

    NonSequenceException(Environment environment, _ErrorDescriptionBuilder _errordescriptionbuilder) {
        super(environment, _errordescriptionbuilder);
    }

    NonSequenceException(Expression expression, TemplateModel templateModel, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "sequence", EXPECTED_TYPES, environment);
    }

    NonSequenceException(Expression expression, TemplateModel templateModel, String str, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "sequence", EXPECTED_TYPES, str, environment);
    }

    NonSequenceException(Expression expression, TemplateModel templateModel, String[] strArr, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "sequence", EXPECTED_TYPES, strArr, environment);
    }
}
