package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
class ExistsExpression extends Expression {
    protected final Expression exp;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "??";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 1;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return false;
    }

    ExistsExpression(Expression expression) {
        this.exp = expression;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        TemplateModel templateModelEval;
        Expression expression = this.exp;
        if (expression instanceof ParentheticalExpression) {
            boolean fastInvalidReferenceExceptions = environment.setFastInvalidReferenceExceptions(true);
            try {
                templateModelEval = this.exp.eval(environment);
                environment.setFastInvalidReferenceExceptions(fastInvalidReferenceExceptions);
            } catch (InvalidReferenceException unused) {
                environment.setFastInvalidReferenceExceptions(fastInvalidReferenceExceptions);
                templateModelEval = null;
            } catch (Throwable th) {
                environment.setFastInvalidReferenceExceptions(fastInvalidReferenceExceptions);
                throw th;
            }
        } else {
            templateModelEval = expression.eval(environment);
        }
        return templateModelEval == null ? TemplateBooleanModel.FALSE : TemplateBooleanModel.TRUE;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        return new ExistsExpression(this.exp.deepCloneWithIdentifierReplaced(str, expression, replacemenetState));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return new StringBuffer().append(this.exp.getCanonicalForm()).append(getNodeTypeSymbol()).toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        return this.exp;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        return ParameterRole.LEFT_HAND_OPERAND;
    }
}
