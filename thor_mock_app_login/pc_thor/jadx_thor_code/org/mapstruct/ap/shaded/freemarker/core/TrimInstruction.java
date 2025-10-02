package org.mapstruct.ap.shaded.freemarker.core;

import kotlin.text.Typography;

/* loaded from: classes3.dex */
final class TrimInstruction extends TemplateElement {
    final boolean left;
    final boolean right;
    private final int TYPE_T = 0;
    private final int TYPE_LT = 1;
    private final int TYPE_RT = 2;
    private final int TYPE_NT = 3;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) {
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 1;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    boolean isIgnorable() {
        return true;
    }

    TrimInstruction(boolean z, boolean z2) {
        this.left = z;
        this.right = z2;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(Typography.less);
        }
        stringBuffer.append(getNodeTypeSymbol());
        if (z) {
            stringBuffer.append("/>");
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        boolean z = this.left;
        return (z && this.right) ? "#t" : z ? "#lt" : this.right ? "#rt" : "#nt";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        int i2;
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        boolean z = this.left;
        if (z && this.right) {
            i2 = 0;
        } else if (z) {
            i2 = 1;
        } else {
            i2 = this.right ? 2 : 3;
        }
        return new Integer(i2);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return ParameterRole.AST_NODE_SUBTYPE;
    }
}
