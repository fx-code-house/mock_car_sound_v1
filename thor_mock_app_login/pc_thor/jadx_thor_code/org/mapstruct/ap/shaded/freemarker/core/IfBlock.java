package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.util.ArrayList;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
final class IfBlock extends TemplateElement {
    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#if-#elseif-#else-container";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 0;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    boolean isShownInStackTrace() {
        return false;
    }

    IfBlock(ConditionalBlock conditionalBlock) {
        this.nestedElements = new ArrayList();
        addBlock(conditionalBlock);
    }

    void addBlock(ConditionalBlock conditionalBlock) {
        this.nestedElements.add(conditionalBlock);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException, IOException {
        for (int i = 0; i < this.nestedElements.size(); i++) {
            ConditionalBlock conditionalBlock = (ConditionalBlock) this.nestedElements.get(i);
            Expression expression = conditionalBlock.condition;
            environment.replaceElementStackTop(conditionalBlock);
            if (expression == null || expression.evalToBoolean(environment)) {
                if (conditionalBlock.nestedBlock != null) {
                    environment.visitByHiddingParent(conditionalBlock.nestedBlock);
                    return;
                }
                return;
            }
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    TemplateElement postParseCleanup(boolean z) throws ParseException {
        if (this.nestedElements.size() == 1) {
            ConditionalBlock conditionalBlock = (ConditionalBlock) this.nestedElements.get(0);
            conditionalBlock.isLonelyIf = true;
            conditionalBlock.setLocation(getTemplate(), conditionalBlock, this);
            return conditionalBlock.postParseCleanup(z);
        }
        return super.postParseCleanup(z);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        if (z) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < this.nestedElements.size(); i++) {
                stringBuffer.append(((ConditionalBlock) this.nestedElements.get(i)).dump(z));
            }
            stringBuffer.append("</#if>");
            return stringBuffer.toString();
        }
        return getNodeTypeSymbol();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        throw new IndexOutOfBoundsException();
    }
}
