package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
final class DollarVariable extends TemplateElement {
    private final Expression escapedExpression;
    private final Expression expression;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "${...}";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 1;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    boolean heedsOpeningWhitespace() {
        return true;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    boolean heedsTrailingWhitespace() {
        return true;
    }

    DollarVariable(Expression expression, Expression expression2) {
        this.expression = expression;
        this.escapedExpression = expression2;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws IOException, TemplateException {
        environment.getOut().write(this.escapedExpression.evalAndCoerceToString(environment));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer("${");
        stringBuffer.append(this.expression.getCanonicalForm());
        stringBuffer.append("}");
        if (!z && this.expression != this.escapedExpression) {
            stringBuffer.append(" auto-escaped");
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this.expression;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return ParameterRole.CONTENT;
    }
}
