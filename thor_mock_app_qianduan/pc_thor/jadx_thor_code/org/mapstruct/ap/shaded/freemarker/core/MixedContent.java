package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.util.ArrayList;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
final class MixedContent extends TemplateElement {
    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#mixed_content";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 0;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    boolean isShownInStackTrace() {
        return false;
    }

    MixedContent() {
        this.nestedElements = new ArrayList();
    }

    void addElement(TemplateElement templateElement) {
        this.nestedElements.add(templateElement);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    TemplateElement postParseCleanup(boolean z) throws ParseException {
        super.postParseCleanup(z);
        return this.nestedElements.size() == 1 ? (TemplateElement) this.nestedElements.get(0) : this;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException, IOException {
        for (int i = 0; i < this.nestedElements.size(); i++) {
            environment.visit((TemplateElement) this.nestedElements.get(i));
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        if (!z) {
            return this.parent == null ? "root" : getNodeTypeSymbol();
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.nestedElements.size(); i++) {
            stringBuffer.append(((TemplateElement) this.nestedElements.get(i)).getCanonicalForm());
        }
        return stringBuffer.toString();
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
        return this.nestedElements == null || this.nestedElements.size() == 0;
    }
}
