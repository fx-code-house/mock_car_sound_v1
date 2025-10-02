package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
final class Case extends TemplateElement {
    final int TYPE_CASE = 0;
    final int TYPE_DEFAULT = 1;
    Expression condition;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    Case(Expression expression, TemplateElement templateElement) {
        this.condition = expression;
        this.nestedBlock = templateElement;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException, IOException {
        if (this.nestedBlock != null) {
            environment.visitByHiddingParent(this.nestedBlock);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(Typography.less);
        }
        stringBuffer.append(getNodeTypeSymbol());
        if (this.condition != null) {
            stringBuffer.append(' ');
            stringBuffer.append(this.condition.getCanonicalForm());
        }
        if (z) {
            stringBuffer.append(Typography.greater);
            if (this.nestedBlock != null) {
                stringBuffer.append(this.nestedBlock.getCanonicalForm());
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return this.condition != null ? "#case" : "#default";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.condition;
        }
        if (i == 1) {
            return new Integer(this.condition != null ? 0 : 1);
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.CONDITION;
        }
        if (i == 1) {
            return ParameterRole.AST_NODE_SUBTYPE;
        }
        throw new IndexOutOfBoundsException();
    }
}
