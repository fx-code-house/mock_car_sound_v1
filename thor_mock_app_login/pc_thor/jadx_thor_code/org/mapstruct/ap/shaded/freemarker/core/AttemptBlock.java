package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.util.ArrayList;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
final class AttemptBlock extends TemplateElement {
    private TemplateElement attemptBlock;
    private RecoveryBlock recoveryBlock;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#attempt";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 1;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    boolean isShownInStackTrace() {
        return false;
    }

    AttemptBlock(TemplateElement templateElement, RecoveryBlock recoveryBlock) {
        this.attemptBlock = templateElement;
        this.recoveryBlock = recoveryBlock;
        this.nestedElements = new ArrayList();
        this.nestedElements.add(templateElement);
        this.nestedElements.add(recoveryBlock);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws IOException, TemplateException {
        environment.visitAttemptRecover(this.attemptBlock, this.recoveryBlock);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        if (!z) {
            return getNodeTypeSymbol();
        }
        StringBuffer stringBuffer = new StringBuffer("<");
        stringBuffer.append(getNodeTypeSymbol());
        stringBuffer.append(">");
        TemplateElement templateElement = this.attemptBlock;
        if (templateElement != null) {
            stringBuffer.append(templateElement.getCanonicalForm());
        }
        RecoveryBlock recoveryBlock = this.recoveryBlock;
        if (recoveryBlock != null) {
            stringBuffer.append(recoveryBlock.getCanonicalForm());
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this.recoveryBlock;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return ParameterRole.ERROR_HANDLER;
    }
}
