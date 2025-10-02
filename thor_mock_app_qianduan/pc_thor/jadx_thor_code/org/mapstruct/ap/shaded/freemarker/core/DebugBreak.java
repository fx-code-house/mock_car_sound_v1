package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import org.mapstruct.ap.shaded.freemarker.debug.impl.DebuggerService;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
public class DebugBreak extends TemplateElement {
    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#debug_break";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 0;
    }

    public DebugBreak(TemplateElement templateElement) {
        this.nestedBlock = templateElement;
        templateElement.parent = this;
        copyLocationFrom(templateElement);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected void accept(Environment environment) throws TemplateException, IOException {
        if (!DebuggerService.suspendEnvironment(environment, getTemplate().getName(), this.nestedBlock.getBeginLine())) {
            this.nestedBlock.accept(environment);
            return;
        }
        throw new StopException(environment, "Stopped by debugger");
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        if (!z) {
            return "debug break";
        }
        StringBuffer stringBuffer = new StringBuffer("<#-- debug break");
        if (this.nestedBlock == null) {
            stringBuffer.append(" /-->");
        } else {
            stringBuffer.append(" -->");
            stringBuffer.append(this.nestedBlock.getCanonicalForm());
            stringBuffer.append("<#--/ debug break -->");
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
}
