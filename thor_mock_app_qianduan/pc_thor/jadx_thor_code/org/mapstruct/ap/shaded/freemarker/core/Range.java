package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template._TemplateAPI;

/* loaded from: classes3.dex */
final class Range extends Expression {
    static final int END_EXCLUSIVE = 1;
    static final int END_INCLUSIVE = 0;
    static final int END_SIZE_LIMITED = 3;
    static final int END_UNBOUND = 2;
    final int endType;
    final Expression lho;
    final Expression rho;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    Range(Expression expression, Expression expression2, int i) {
        this.lho = expression;
        this.rho = expression2;
        this.endType = i;
    }

    int getEndType() {
        return this.endType;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        int iIntValue = this.lho.evalToNumber(environment).intValue();
        if (this.endType == 2) {
            return _TemplateAPI.getTemplateLanguageVersionAsInt(this) >= _TemplateAPI.VERSION_INT_2_3_21 ? new ListableRightUnboundedRangeModel(iIntValue) : new NonListableRightUnboundedRangeModel(iIntValue);
        }
        int iIntValue2 = this.rho.evalToNumber(environment).intValue();
        int i = this.endType;
        if (i == 3) {
            iIntValue2 += iIntValue;
        }
        return new BoundedRangeModel(iIntValue, iIntValue2, i == 0, i == 3);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean evalToBoolean(Environment environment) throws TemplateException {
        throw new NonBooleanException(this, new BoundedRangeModel(0, 0, false, false), environment);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        Expression expression = this.rho;
        return new StringBuffer().append(this.lho.getCanonicalForm()).append(getNodeTypeSymbol()).append(expression != null ? expression.getCanonicalForm() : "").toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        int i = this.endType;
        if (i == 0) {
            return "..";
        }
        if (i == 1) {
            return "..<";
        }
        if (i == 2) {
            return "..";
        }
        if (i == 3) {
            return "..*";
        }
        throw new BugException(this.endType);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        Expression expression = this.rho;
        return this.constantValue != null || (this.lho.isLiteral() && (expression == null || expression.isLiteral()));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        return new Range(this.lho.deepCloneWithIdentifierReplaced(str, expression, replacemenetState), this.rho.deepCloneWithIdentifierReplaced(str, expression, replacemenetState), this.endType);
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
