package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
abstract class BuiltInForHashEx extends BuiltIn {
    abstract TemplateModel calculateResult(TemplateHashModelEx templateHashModelEx, Environment environment) throws InvalidReferenceException, TemplateModelException;

    BuiltInForHashEx() {
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        TemplateModel templateModelEval = this.target.eval(environment);
        if (templateModelEval instanceof TemplateHashModelEx) {
            return calculateResult((TemplateHashModelEx) templateModelEval, environment);
        }
        throw new NonExtendedHashException(this.target, templateModelEval, environment);
    }

    protected InvalidReferenceException newNullPropertyException(String str, TemplateModel templateModel, Environment environment) {
        if (environment.getFastInvalidReferenceExceptions()) {
            return InvalidReferenceException.FAST_INSTANCE;
        }
        return new InvalidReferenceException(new _ErrorDescriptionBuilder(new Object[]{"The exteneded hash (of class ", templateModel.getClass().getName(), ") has returned null for its \"", str, "\" property. This is maybe a bug. The extended hash was returned by this expression:"}).blame(this.target), environment, this);
    }
}
