package org.mapstruct.ap.shaded.freemarker.core;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
public class NonMethodException extends UnexpectedTypeException {
    private static final Class[] EXPECTED_TYPES;
    static /* synthetic */ Class class$freemarker$template$TemplateMethodModel;

    static {
        Class[] clsArr = new Class[1];
        Class clsClass$ = class$freemarker$template$TemplateMethodModel;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel");
            class$freemarker$template$TemplateMethodModel = clsClass$;
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

    public NonMethodException(Environment environment) {
        super(environment, "Expecting method value here");
    }

    public NonMethodException(String str, Environment environment) {
        super(environment, str);
    }

    NonMethodException(Environment environment, _ErrorDescriptionBuilder _errordescriptionbuilder) {
        super(environment, _errordescriptionbuilder);
    }

    NonMethodException(Expression expression, TemplateModel templateModel, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, FirebaseAnalytics.Param.METHOD, EXPECTED_TYPES, environment);
    }

    NonMethodException(Expression expression, TemplateModel templateModel, String str, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, FirebaseAnalytics.Param.METHOD, EXPECTED_TYPES, str, environment);
    }

    NonMethodException(Expression expression, TemplateModel templateModel, String[] strArr, Environment environment) throws InvalidReferenceException {
        super(expression, templateModel, FirebaseAnalytics.Param.METHOD, EXPECTED_TYPES, strArr, environment);
    }
}
