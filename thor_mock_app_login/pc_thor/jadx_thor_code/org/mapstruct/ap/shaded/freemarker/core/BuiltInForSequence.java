package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
abstract class BuiltInForSequence extends BuiltIn {
    abstract TemplateModel calculateResult(TemplateSequenceModel templateSequenceModel) throws TemplateModelException;

    BuiltInForSequence() {
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        TemplateModel templateModelEval = this.target.eval(environment);
        if (!(templateModelEval instanceof TemplateSequenceModel)) {
            throw new NonSequenceException(this.target, templateModelEval, environment);
        }
        return calculateResult((TemplateSequenceModel) templateModelEval);
    }
}
