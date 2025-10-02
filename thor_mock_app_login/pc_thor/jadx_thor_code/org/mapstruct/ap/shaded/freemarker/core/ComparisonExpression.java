package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
final class ComparisonExpression extends BooleanExpression {
    private final Expression left;
    private final String opString;
    private final int operation;
    private final Expression right;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    ComparisonExpression(Expression expression, Expression expression2, String str) {
        this.left = expression;
        this.right = expression2;
        String strIntern = str.intern();
        this.opString = strIntern;
        if (strIntern == "==" || strIntern == "=") {
            this.operation = 1;
            return;
        }
        if (strIntern == "!=") {
            this.operation = 2;
            return;
        }
        if (strIntern == "gt" || strIntern == "\\gt" || strIntern == ">" || strIntern == "&gt;") {
            this.operation = 4;
            return;
        }
        if (strIntern == "gte" || strIntern == "\\gte" || strIntern == ">=" || strIntern == "&gt;=") {
            this.operation = 6;
            return;
        }
        if (strIntern == "lt" || strIntern == "\\lt" || strIntern == "<" || strIntern == "&lt;") {
            this.operation = 3;
        } else {
            if (strIntern == "lte" || strIntern == "\\lte" || strIntern == "<=" || strIntern == "&lt;=") {
                this.operation = 5;
                return;
            }
            throw new BugException(new StringBuffer("Unknown comparison operator ").append(strIntern).toString());
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean evalToBoolean(Environment environment) throws TemplateException {
        return EvalUtil.compare(this.left, this.operation, this.opString, this.right, this, environment);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return new StringBuffer().append(this.left.getCanonicalForm()).append(' ').append(this.opString).append(' ').append(this.right.getCanonicalForm()).toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return this.opString;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return this.constantValue != null || (this.left.isLiteral() && this.right.isLiteral());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        return new ComparisonExpression(this.left.deepCloneWithIdentifierReplaced(str, expression, replacemenetState), this.right.deepCloneWithIdentifierReplaced(str, expression, replacemenetState), this.opString);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        return i == 0 ? this.left : this.right;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        return ParameterRole.forBinaryOperatorOperand(i);
    }
}
