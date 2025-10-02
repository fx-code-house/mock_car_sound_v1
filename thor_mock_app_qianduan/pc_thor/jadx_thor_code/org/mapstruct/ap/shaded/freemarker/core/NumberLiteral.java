package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;

/* loaded from: classes3.dex */
final class NumberLiteral extends Expression implements TemplateNumberModel {
    private final Number value;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 0;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return true;
    }

    public NumberLiteral(Number number) {
        this.value = number;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) {
        return new SimpleNumber(this.value);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    public String evalAndCoerceToString(Environment environment) {
        return environment.formatNumber(this.value);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel
    public Number getAsNumber() {
        return this.value;
    }

    String getName() {
        return new StringBuffer("the number: '").append(this.value).append("'").toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return this.value.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return getCanonicalForm();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        return new NumberLiteral(this.value);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        throw new IndexOutOfBoundsException();
    }
}
