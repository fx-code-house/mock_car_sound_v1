package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
public class NonSequenceOrCollectionException extends UnexpectedTypeException {
    private static final Class[] EXPECTED_TYPES;
    static /* synthetic */ Class class$freemarker$template$TemplateCollectionModel;
    static /* synthetic */ Class class$freemarker$template$TemplateSequenceModel;

    static {
        Class[] clsArr = new Class[2];
        Class clsClass$ = class$freemarker$template$TemplateSequenceModel;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel");
            class$freemarker$template$TemplateSequenceModel = clsClass$;
        }
        clsArr[0] = clsClass$;
        Class clsClass$2 = class$freemarker$template$TemplateCollectionModel;
        if (clsClass$2 == null) {
            clsClass$2 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel");
            class$freemarker$template$TemplateCollectionModel = clsClass$2;
        }
        clsArr[1] = clsClass$2;
        EXPECTED_TYPES = clsArr;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public NonSequenceOrCollectionException(Environment environment) {
        super(environment, "Expecting sequence or collection value here");
    }

    public NonSequenceOrCollectionException(String str, Environment environment) {
        super(environment, str);
    }

    NonSequenceOrCollectionException(Environment environment, _ErrorDescriptionBuilder _errordescriptionbuilder) {
        super(environment, _errordescriptionbuilder);
    }

    NonSequenceOrCollectionException(Expression expression, TemplateModel templateModel, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "sequence or collection", EXPECTED_TYPES, environment);
    }

    NonSequenceOrCollectionException(Expression expression, TemplateModel templateModel, String str, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "sequence or collection", EXPECTED_TYPES, str, environment);
    }

    NonSequenceOrCollectionException(Expression expression, TemplateModel templateModel, String[] strArr, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, "sequence or collection", EXPECTED_TYPES, strArr, environment);
    }
}
