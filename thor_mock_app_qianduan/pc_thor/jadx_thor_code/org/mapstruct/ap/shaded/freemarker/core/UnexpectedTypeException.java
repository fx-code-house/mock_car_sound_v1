package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
public class UnexpectedTypeException extends TemplateException {
    public UnexpectedTypeException(Environment environment, String str) {
        super(str, environment);
    }

    UnexpectedTypeException(Environment environment, _ErrorDescriptionBuilder _errordescriptionbuilder) {
        super(null, environment, null, _errordescriptionbuilder);
    }

    UnexpectedTypeException(Expression expression, TemplateModel templateModel, String str, Class[] clsArr, Environment environment) throws InvalidReferenceException {
        super(null, environment, expression, newDesciptionBuilder(expression, templateModel, str, clsArr, environment));
    }

    UnexpectedTypeException(Expression expression, TemplateModel templateModel, String str, Class[] clsArr, String str2, Environment environment) throws InvalidReferenceException {
        super(null, environment, expression, newDesciptionBuilder(expression, templateModel, str, clsArr, environment).tip(str2));
    }

    UnexpectedTypeException(Expression expression, TemplateModel templateModel, String str, Class[] clsArr, String[] strArr, Environment environment) throws InvalidReferenceException {
        super(null, environment, expression, newDesciptionBuilder(expression, templateModel, str, clsArr, environment).tips(strArr));
    }

    private static _ErrorDescriptionBuilder newDesciptionBuilder(Expression expression, TemplateModel templateModel, String str, Class[] clsArr, Environment environment) throws InvalidReferenceException {
        Object[] objArrExplainTypeError;
        if (templateModel == null) {
            throw InvalidReferenceException.getInstance(expression, environment);
        }
        _ErrorDescriptionBuilder _errordescriptionbuilderShowBlamer = new _ErrorDescriptionBuilder(unexpectedTypeErrorDescription(str, templateModel)).blame(expression).showBlamer(true);
        if ((templateModel instanceof _UnexpectedTypeErrorExplainerTemplateModel) && (objArrExplainTypeError = ((_UnexpectedTypeErrorExplainerTemplateModel) templateModel).explainTypeError(clsArr)) != null) {
            _errordescriptionbuilderShowBlamer.tip(objArrExplainTypeError);
        }
        return _errordescriptionbuilderShowBlamer;
    }

    private static Object[] unexpectedTypeErrorDescription(String str, TemplateModel templateModel) {
        return new Object[]{"Expected ", new _DelayedAOrAn(str), ", but this evaluated to ", new _DelayedAOrAn(new _DelayedFTLTypeDescription(templateModel)), ":"};
    }
}
