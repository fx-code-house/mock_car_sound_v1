package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.util.LinkedList;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.core.BreakInstruction;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
final class SwitchBlock extends TemplateElement {
    private Case defaultCase;
    private final Expression searched;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#switch";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 1;
    }

    SwitchBlock(Expression expression) {
        this.searched = expression;
        this.nestedElements = new LinkedList();
    }

    void addCase(Case r2) {
        if (r2.condition == null) {
            this.defaultCase = r2;
        }
        this.nestedElements.add(r2);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException, IOException {
        TemplateElement templateElement;
        boolean zCompare;
        boolean z = false;
        for (Case r3 : this.nestedElements) {
            try {
                if (z) {
                    zCompare = true;
                } else {
                    zCompare = r3.condition != null ? EvalUtil.compare(this.searched, 1, "case==", r3.condition, r3.condition, environment) : false;
                }
                if (zCompare) {
                    environment.visitByHiddingParent(r3);
                    z = true;
                }
            } catch (BreakInstruction.Break unused) {
                return;
            }
        }
        if (z || (templateElement = this.defaultCase) == null) {
            return;
        }
        environment.visitByHiddingParent(templateElement);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(Typography.less);
        }
        stringBuffer.append(getNodeTypeSymbol());
        stringBuffer.append(' ');
        stringBuffer.append(this.searched.getCanonicalForm());
        if (z) {
            stringBuffer.append(Typography.greater);
            for (int i = 0; i < this.nestedElements.size(); i++) {
                stringBuffer.append(((Case) this.nestedElements.get(i)).getCanonicalForm());
            }
            stringBuffer.append("</").append(getNodeTypeSymbol()).append(Typography.greater);
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this.searched;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return ParameterRole.VALUE;
    }
}
