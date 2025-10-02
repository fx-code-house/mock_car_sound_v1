package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
final class ConditionalBlock extends TemplateElement {
    static final int TYPE_ELSE = 1;
    static final int TYPE_ELSE_IF = 2;
    static final int TYPE_IF = 0;
    final Expression condition;
    boolean isLonelyIf;
    private final int type;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    ConditionalBlock(Expression expression, TemplateElement templateElement, int i) {
        this.condition = expression;
        this.nestedBlock = templateElement;
        this.type = i;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException, IOException {
        Expression expression = this.condition;
        if ((expression == null || expression.evalToBoolean(environment)) && this.nestedBlock != null) {
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
            stringBuffer.append(">");
            if (this.nestedBlock != null) {
                stringBuffer.append(this.nestedBlock.getCanonicalForm());
            }
            if (this.isLonelyIf) {
                stringBuffer.append("</#if>");
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        int i = this.type;
        if (i == 1) {
            return "#else";
        }
        if (i == 0) {
            return "#if";
        }
        if (i == 2) {
            return "#elseif";
        }
        throw new BugException("Unknown type");
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.condition;
        }
        if (i == 1) {
            return new Integer(this.type);
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
