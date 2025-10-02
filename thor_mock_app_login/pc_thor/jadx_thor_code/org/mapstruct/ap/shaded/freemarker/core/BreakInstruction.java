package org.mapstruct.ap.shaded.freemarker.core;

/* loaded from: classes3.dex */
final class BreakInstruction extends TemplateElement {
    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#break";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 0;
    }

    BreakInstruction() {
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) {
        throw Break.INSTANCE;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        return z ? new StringBuffer("<").append(getNodeTypeSymbol()).append("/>").toString() : getNodeTypeSymbol();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        throw new IndexOutOfBoundsException();
    }

    static class Break extends RuntimeException {
        static final Break INSTANCE = new Break();

        private Break() {
        }
    }
}
