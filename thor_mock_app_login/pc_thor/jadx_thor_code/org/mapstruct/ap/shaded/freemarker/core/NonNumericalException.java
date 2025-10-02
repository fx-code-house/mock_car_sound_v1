package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
public class NonNumericalException extends UnexpectedTypeException {
    private static final Class[] EXPECTED_TYPES;
    static /* synthetic */ Class class$freemarker$template$TemplateNumberModel;

    static {
        Class[] clsArr = new Class[1];
        Class clsClass$ = class$freemarker$template$TemplateNumberModel;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel");
            class$freemarker$template$TemplateNumberModel = clsClass$;
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

    public NonNumericalException(Environment environment) {
        super(environment, "Expecting numerical value here");
    }

    public NonNumericalException(String str, Environment environment) {
        super(environment, str);
    }

    NonNumericalException(_ErrorDescriptionBuilder _errordescriptionbuilder, Environment environment) {
        super(environment, _errordescriptionbuilder);
    }

    NonNumericalException(Expression expression, TemplateModel templateModel, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "number", EXPECTED_TYPES, environment);
    }

    NonNumericalException(Expression expression, TemplateModel templateModel, String str, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "number", EXPECTED_TYPES, str, environment);
    }

    NonNumericalException(Expression expression, TemplateModel templateModel, String[] strArr, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "number", EXPECTED_TYPES, strArr, environment);
    }

    static NonNumericalException newMalformedNumberException(Expression expression, String str, Environment environment) {
        return new NonNumericalException(new _ErrorDescriptionBuilder(new Object[]{"Can't convert this string to number: ", new _DelayedJQuote(str)}).blame(expression), environment);
    }
}
