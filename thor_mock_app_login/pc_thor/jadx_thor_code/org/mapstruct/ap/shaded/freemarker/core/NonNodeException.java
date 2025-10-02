package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
public class NonNodeException extends UnexpectedTypeException {
    private static final Class[] EXPECTED_TYPES;
    static /* synthetic */ Class class$freemarker$template$TemplateNodeModel;

    static {
        Class[] clsArr = new Class[1];
        Class clsClass$ = class$freemarker$template$TemplateNodeModel;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel");
            class$freemarker$template$TemplateNodeModel = clsClass$;
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

    public NonNodeException(Environment environment) {
        super(environment, "Expecting node value here");
    }

    public NonNodeException(String str, Environment environment) {
        super(environment, str);
    }

    NonNodeException(Environment environment, _ErrorDescriptionBuilder _errordescriptionbuilder) {
        super(environment, _errordescriptionbuilder);
    }

    NonNodeException(Expression expression, TemplateModel templateModel, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "node", EXPECTED_TYPES, environment);
    }

    NonNodeException(Expression expression, TemplateModel templateModel, String str, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "node", EXPECTED_TYPES, str, environment);
    }

    NonNodeException(Expression expression, TemplateModel templateModel, String[] strArr, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "node", EXPECTED_TYPES, strArr, environment);
    }
}
