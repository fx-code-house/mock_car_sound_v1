package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
class NonNamespaceException extends UnexpectedTypeException {
    private static final Class[] EXPECTED_TYPES;
    static /* synthetic */ Class class$freemarker$core$Environment$Namespace;

    static {
        Class[] clsArr = new Class[1];
        Class clsClass$ = class$freemarker$core$Environment$Namespace;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.core.Environment$Namespace");
            class$freemarker$core$Environment$Namespace = clsClass$;
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

    public NonNamespaceException(Environment environment) {
        super(environment, "Expecting namespace value here");
    }

    public NonNamespaceException(String str, Environment environment) {
        super(environment, str);
    }

    NonNamespaceException(Environment environment, _ErrorDescriptionBuilder _errordescriptionbuilder) {
        super(environment, _errordescriptionbuilder);
    }

    NonNamespaceException(Expression expression, TemplateModel templateModel, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "namespace", EXPECTED_TYPES, environment);
    }

    NonNamespaceException(Expression expression, TemplateModel templateModel, String str, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "namespace", EXPECTED_TYPES, str, environment);
    }

    NonNamespaceException(Expression expression, TemplateModel templateModel, String[] strArr, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "namespace", EXPECTED_TYPES, strArr, environment);
    }
}
