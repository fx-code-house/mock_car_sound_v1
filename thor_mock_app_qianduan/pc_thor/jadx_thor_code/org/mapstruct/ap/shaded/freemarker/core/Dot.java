package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
final class Dot extends Expression {
    private final String key;
    private final Expression target;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return ".";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    Dot(Expression expression, String str) {
        this.target = expression;
        this.key = str;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        TemplateModel templateModelEval = this.target.eval(environment);
        if (templateModelEval instanceof TemplateHashModel) {
            return ((TemplateHashModel) templateModelEval).get(this.key);
        }
        if (templateModelEval == null && environment.isClassicCompatible()) {
            return null;
        }
        throw new NonHashException(this.target, templateModelEval, environment);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return new StringBuffer().append(this.target.getCanonicalForm()).append(getNodeTypeSymbol()).append(this.key).toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return this.target.isLiteral();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        return new Dot(this.target.deepCloneWithIdentifierReplaced(str, expression, replacemenetState), this.key);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        return i == 0 ? this.target : this.key;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        return ParameterRole.forBinaryOperatorOperand(i);
    }

    String getRHO() {
        return this.key;
    }

    boolean onlyHasIdentifiers() {
        Expression expression = this.target;
        return (expression instanceof Identifier) || ((expression instanceof Dot) && ((Dot) expression).onlyHasIdentifiers());
    }
}
