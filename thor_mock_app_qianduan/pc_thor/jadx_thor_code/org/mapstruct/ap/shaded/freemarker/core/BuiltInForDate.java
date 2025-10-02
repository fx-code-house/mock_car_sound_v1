package org.mapstruct.ap.shaded.freemarker.core;

import java.util.Date;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
abstract class BuiltInForDate extends BuiltIn {
    protected abstract TemplateModel calculateResult(Date date, int i, Environment environment) throws TemplateException;

    BuiltInForDate() {
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        TemplateModel templateModelEval = this.target.eval(environment);
        if (templateModelEval instanceof TemplateDateModel) {
            TemplateDateModel templateDateModel = (TemplateDateModel) templateModelEval;
            return calculateResult(EvalUtil.modelToDate(templateDateModel, this.target), templateDateModel.getDateType(), environment);
        }
        throw newNonDateException(environment, templateModelEval, this.target);
    }

    static TemplateException newNonDateException(Environment environment, TemplateModel templateModel, Expression expression) throws InvalidReferenceException {
        if (templateModel == null) {
            return InvalidReferenceException.getInstance(expression, environment);
        }
        return new NonDateException(expression, templateModel, "date", environment);
    }
}
