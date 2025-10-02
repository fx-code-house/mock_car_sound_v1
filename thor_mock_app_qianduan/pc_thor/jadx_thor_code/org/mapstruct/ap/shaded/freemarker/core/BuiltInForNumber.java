package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
abstract class BuiltInForNumber extends BuiltIn {
    abstract TemplateModel calculateResult(Number number, TemplateModel templateModel) throws TemplateModelException;

    BuiltInForNumber() {
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        TemplateModel templateModelEval = this.target.eval(environment);
        return calculateResult(this.target.modelToNumber(templateModelEval, environment), templateModelEval);
    }
}
