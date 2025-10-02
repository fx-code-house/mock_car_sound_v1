package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
public class NonExtendedHashException extends UnexpectedTypeException {
    private static final Class[] EXPECTED_TYPES;
    static /* synthetic */ Class class$freemarker$template$TemplateHashModelEx;

    static {
        Class[] clsArr = new Class[1];
        Class clsClass$ = class$freemarker$template$TemplateHashModelEx;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx");
            class$freemarker$template$TemplateHashModelEx = clsClass$;
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

    public NonExtendedHashException(Environment environment) {
        super(environment, "Expecting extended hash value here");
    }

    public NonExtendedHashException(String str, Environment environment) {
        super(environment, str);
    }

    NonExtendedHashException(Environment environment, _ErrorDescriptionBuilder _errordescriptionbuilder) {
        super(environment, _errordescriptionbuilder);
    }

    NonExtendedHashException(Expression expression, TemplateModel templateModel, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "extended hash", EXPECTED_TYPES, environment);
    }

    NonExtendedHashException(Expression expression, TemplateModel templateModel, String str, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "extended hash", EXPECTED_TYPES, str, environment);
    }

    NonExtendedHashException(Expression expression, TemplateModel templateModel, String[] strArr, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "extended hash", EXPECTED_TYPES, strArr, environment);
    }
}
