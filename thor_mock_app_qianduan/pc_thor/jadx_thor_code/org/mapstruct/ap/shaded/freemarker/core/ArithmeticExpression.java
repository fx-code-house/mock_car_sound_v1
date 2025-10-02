package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
final class ArithmeticExpression extends Expression {
    private static final char[] OPERATOR_IMAGES = {'-', '*', '/', '%'};
    static final int TYPE_DIVISION = 2;
    static final int TYPE_MODULO = 3;
    static final int TYPE_MULTIPLICATION = 1;
    static final int TYPE_SUBSTRACTION = 0;
    private final Expression lho;
    private final int operator;
    private final Expression rho;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 3;
    }

    ArithmeticExpression(Expression expression, Expression expression2, int i) {
        this.lho = expression;
        this.rho = expression2;
        this.operator = i;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        ArithmeticEngine arithmeticEngine;
        Number numberEvalToNumber = this.lho.evalToNumber(environment);
        Number numberEvalToNumber2 = this.rho.evalToNumber(environment);
        if (environment != null) {
            arithmeticEngine = environment.getArithmeticEngine();
        } else {
            arithmeticEngine = getTemplate().getArithmeticEngine();
        }
        int i = this.operator;
        if (i == 0) {
            return new SimpleNumber(arithmeticEngine.subtract(numberEvalToNumber, numberEvalToNumber2));
        }
        if (i == 1) {
            return new SimpleNumber(arithmeticEngine.multiply(numberEvalToNumber, numberEvalToNumber2));
        }
        if (i == 2) {
            return new SimpleNumber(arithmeticEngine.divide(numberEvalToNumber, numberEvalToNumber2));
        }
        if (i == 3) {
            return new SimpleNumber(arithmeticEngine.modulus(numberEvalToNumber, numberEvalToNumber2));
        }
        throw new _MiscTemplateException(this, new Object[]{"Unknown operation: ", new Integer(this.operator)});
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return new StringBuffer().append(this.lho.getCanonicalForm()).append(' ').append(OPERATOR_IMAGES[this.operator]).append(' ').append(this.rho.getCanonicalForm()).toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return String.valueOf(OPERATOR_IMAGES[this.operator]);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return this.constantValue != null || (this.lho.isLiteral() && this.rho.isLiteral());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        return new ArithmeticExpression(this.lho.deepCloneWithIdentifierReplaced(str, expression, replacemenetState), this.rho.deepCloneWithIdentifierReplaced(str, expression, replacemenetState), this.operator);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.lho;
        }
        if (i == 1) {
            return this.rho;
        }
        if (i == 2) {
            return new Integer(this.operator);
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.LEFT_HAND_OPERAND;
        }
        if (i == 1) {
            return ParameterRole.RIGHT_HAND_OPERAND;
        }
        if (i == 2) {
            return ParameterRole.AST_NODE_SUBTYPE;
        }
        throw new IndexOutOfBoundsException();
    }
}
