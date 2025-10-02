package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
final class AndExpression extends BooleanExpression {
    private final Expression lho;
    private final Expression rho;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "&&";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    AndExpression(Expression expression, Expression expression2) {
        this.lho = expression;
        this.rho = expression2;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean evalToBoolean(Environment environment) throws TemplateException {
        return this.lho.evalToBoolean(environment) && this.rho.evalToBoolean(environment);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return new StringBuffer().append(this.lho.getCanonicalForm()).append(" && ").append(this.rho.getCanonicalForm()).toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return this.constantValue != null || (this.lho.isLiteral() && this.rho.isLiteral());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        return new AndExpression(this.lho.deepCloneWithIdentifierReplaced(str, expression, replacemenetState), this.rho.deepCloneWithIdentifierReplaced(str, expression, replacemenetState));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.lho;
        }
        if (i == 1) {
            return this.rho;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        return ParameterRole.forBinaryOperatorOperand(i);
    }
}
