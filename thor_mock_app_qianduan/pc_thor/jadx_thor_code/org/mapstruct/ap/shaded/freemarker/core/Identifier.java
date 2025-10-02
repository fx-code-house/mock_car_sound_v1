package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
final class Identifier extends Expression {
    private final String name;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 0;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return false;
    }

    Identifier(String str) {
        this.name = str;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        try {
            return environment.getVariable(this.name);
        } catch (NullPointerException e) {
            if (environment == null) {
                throw new _MiscTemplateException(new Object[]{"Variables are not available (certainly you are in a parse-time executed directive). The name of the variable you tried to read: ", this.name});
            }
            throw e;
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String toString() {
        return this.name;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return this.name;
    }

    String getName() {
        return this.name;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return getCanonicalForm();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        if (this.name.equals(str)) {
            if (replacemenetState.replacementAlreadyInUse) {
                Expression expressionDeepCloneWithIdentifierReplaced = expression.deepCloneWithIdentifierReplaced(null, null, replacemenetState);
                expressionDeepCloneWithIdentifierReplaced.copyLocationFrom(expression);
                return expressionDeepCloneWithIdentifierReplaced;
            }
            replacemenetState.replacementAlreadyInUse = true;
            return expression;
        }
        return new Identifier(this.name);
    }
}
