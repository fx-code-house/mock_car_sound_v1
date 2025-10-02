package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
final class ParentheticalExpression extends Expression {
    private final Expression nested;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "(...)";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 1;
    }

    ParentheticalExpression(Expression expression) {
        this.nested = expression;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean evalToBoolean(Environment environment) throws TemplateException {
        return this.nested.evalToBoolean(environment);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return new StringBuffer("(").append(this.nested.getCanonicalForm()).append(")").toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        return this.nested.eval(environment);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    public boolean isLiteral() {
        return this.nested.isLiteral();
    }

    Expression getNestedExpression() {
        return this.nested;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        return new ParentheticalExpression(this.nested.deepCloneWithIdentifierReplaced(str, expression, replacemenetState));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this.nested;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return ParameterRole.ENCLOSED_OPERAND;
    }
}
