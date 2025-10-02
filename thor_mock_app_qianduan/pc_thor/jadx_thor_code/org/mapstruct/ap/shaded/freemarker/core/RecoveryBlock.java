package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
final class RecoveryBlock extends TemplateElement {
    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#recover";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 0;
    }

    RecoveryBlock(TemplateElement templateElement) {
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
        if (z) {
            StringBuffer stringBuffer = new StringBuffer("<");
            stringBuffer.append(getNodeTypeSymbol()).append(Typography.greater);
            if (this.nestedBlock != null) {
                stringBuffer.append(this.nestedBlock.getCanonicalForm());
            }
            stringBuffer.append("</").append(getNodeTypeSymbol()).append(Typography.greater);
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
