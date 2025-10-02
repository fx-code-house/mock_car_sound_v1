package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
class EscapeBlock extends TemplateElement {
    private Expression escapedExpr;
    private final Expression expr;
    private final String variable;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#escape";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    boolean isShownInStackTrace() {
        return false;
    }

    EscapeBlock(String str, Expression expression, Expression expression2) {
        this.variable = str;
        this.expr = expression;
        this.escapedExpr = expression2;
    }

    void setContent(TemplateElement templateElement) {
        this.nestedBlock = templateElement;
        this.escapedExpr = null;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException, IOException {
        if (this.nestedBlock != null) {
            environment.visit(this.nestedBlock);
        }
    }

    Expression doEscape(Expression expression) {
        return this.escapedExpr.deepCloneWithIdentifierReplaced(this.variable, expression, new Expression.ReplacemenetState());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(Typography.less);
        }
        stringBuffer.append(getNodeTypeSymbol()).append(' ').append(this.variable).append(" as ").append(this.expr.getCanonicalForm());
        if (z) {
            stringBuffer.append(Typography.greater).append(this.nestedBlock.getCanonicalForm()).append("</").append(getNodeTypeSymbol()).append(Typography.greater);
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.variable;
        }
        if (i == 1) {
            return this.expr;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.PLACEHOLDER_VARIABLE;
        }
        if (i == 1) {
            return ParameterRole.EXPRESSION_TEMPLATE;
        }
        throw new IndexOutOfBoundsException();
    }
}
