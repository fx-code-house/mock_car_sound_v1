package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel;

/* loaded from: classes3.dex */
abstract class BuiltInForNode extends BuiltIn {
    abstract TemplateModel calculateResult(TemplateNodeModel templateNodeModel, Environment environment) throws TemplateModelException;

    BuiltInForNode() {
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        TemplateModel templateModelEval = this.target.eval(environment);
        if (templateModelEval instanceof TemplateNodeModel) {
            return calculateResult((TemplateNodeModel) templateModelEval, environment);
        }
        throw new NonNodeException(this.target, templateModelEval, environment);
    }
}
