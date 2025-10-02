package org.mapstruct.ap.shaded.freemarker.core;

import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
public final class ReturnInstruction extends TemplateElement {
    private Expression exp;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#return";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 1;
    }

    ReturnInstruction(Expression expression) {
        this.exp = expression;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException {
        Expression expression = this.exp;
        if (expression != null) {
            environment.setLastReturnValue(expression.eval(environment));
        }
        if (nextSibling() != null) {
            throw Return.INSTANCE;
        }
        if (!(getParent() instanceof Macro) && !(getParent().getParent() instanceof Macro)) {
            throw Return.INSTANCE;
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(Typography.less);
        }
        stringBuffer.append(getNodeTypeSymbol());
        if (this.exp != null) {
            stringBuffer.append(' ');
            stringBuffer.append(this.exp.getCanonicalForm());
        }
        if (z) {
            stringBuffer.append("/>");
        }
        return stringBuffer.toString();
    }

    public static class Return extends RuntimeException {
        static final Return INSTANCE = new Return();

        private Return() {
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this.exp;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return ParameterRole.VALUE;
    }
}
