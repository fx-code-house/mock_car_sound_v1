package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
abstract class BuiltInForString extends BuiltIn {
    abstract TemplateModel calculateResult(String str, Environment environment) throws TemplateException;

    BuiltInForString() {
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        return calculateResult(this.target.evalAndCoerceToString(environment), environment);
    }
}
