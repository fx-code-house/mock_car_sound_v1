package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;

/* loaded from: classes3.dex */
final class UnaryPlusMinusExpression extends Expression {
    private static final Integer MINUS_ONE = new Integer(-1);
    private final int TYPE_MINUS = 0;
    private final int TYPE_PLUS = 1;
    private final boolean isMinus;
    private final Expression target;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    boolean isIgnorable() {
        return true;
    }

    UnaryPlusMinusExpression(Expression expression, boolean z) {
        this.target = expression;
        this.isMinus = z;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        TemplateModel templateModelEval = this.target.eval(environment);
        try {
            TemplateNumberModel templateNumberModel = (TemplateNumberModel) templateModelEval;
            if (!this.isMinus) {
                return templateNumberModel;
            }
            this.target.assertNonNull(templateNumberModel, environment);
            return new SimpleNumber(ArithmeticEngine.CONSERVATIVE_ENGINE.multiply(MINUS_ONE, templateNumberModel.getAsNumber()));
        } catch (ClassCastException unused) {
            throw new NonNumericalException(this.target, templateModelEval, environment);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return new StringBuffer().append(this.isMinus ? "-" : "+").append(this.target.getCanonicalForm()).toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return this.isMinus ? "-..." : "+...";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return this.target.isLiteral();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        return new UnaryPlusMinusExpression(this.target.deepCloneWithIdentifierReplaced(str, expression, replacemenetState), this.isMinus);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.target;
        }
        if (i == 1) {
            return new Integer(1 ^ (this.isMinus ? 1 : 0));
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.RIGHT_HAND_OPERAND;
        }
        if (i == 1) {
            return ParameterRole.AST_NODE_SUBTYPE;
        }
        throw new IndexOutOfBoundsException();
    }
}
