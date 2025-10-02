package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.utility.StandardCompress;

/* loaded from: classes3.dex */
final class CompressedBlock extends TemplateElement {
    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#compress";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 0;
    }

    CompressedBlock(TemplateElement templateElement) {
        this.nestedBlock = templateElement;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws IOException, TemplateException {
        if (this.nestedBlock != null) {
            environment.visitAndTransform(this.nestedBlock, StandardCompress.INSTANCE, null);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        if (z) {
            return new StringBuffer("<").append(getNodeTypeSymbol()).append(">").append(this.nestedBlock != null ? this.nestedBlock.getCanonicalForm() : "").append("</").append(getNodeTypeSymbol()).append(">").toString();
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

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    boolean isIgnorable() {
        return this.nestedBlock == null || this.nestedBlock.isIgnorable();
    }
}
