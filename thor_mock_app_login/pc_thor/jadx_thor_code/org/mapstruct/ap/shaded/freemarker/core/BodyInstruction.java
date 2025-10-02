package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.core.Environment.Namespace;
import org.mapstruct.ap.shaded.freemarker.core.Macro;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
final class BodyInstruction extends TemplateElement {
    private List bodyParameters;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#nested";
    }

    BodyInstruction(List list) {
        this.bodyParameters = list;
    }

    List getBodyParameters() {
        return this.bodyParameters;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException, IOException {
        environment.visit(new Context(environment));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(Typography.less);
        }
        stringBuffer.append(getNodeTypeSymbol());
        if (this.bodyParameters != null) {
            for (int i = 0; i < this.bodyParameters.size(); i++) {
                stringBuffer.append(' ');
                stringBuffer.append(this.bodyParameters.get(i));
            }
        }
        if (z) {
            stringBuffer.append(Typography.greater);
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        List list = this.bodyParameters;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        checkIndex(i);
        return this.bodyParameters.get(i);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        checkIndex(i);
        return ParameterRole.PASSED_VALUE;
    }

    private void checkIndex(int i) {
        List list = this.bodyParameters;
        if (list == null || i >= list.size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    class Context implements LocalContext {
        Environment.Namespace bodyVars;
        Macro.Context invokingMacroContext;

        Context(Environment environment) throws TemplateException {
            Macro.Context currentMacroContext = environment.getCurrentMacroContext();
            this.invokingMacroContext = currentMacroContext;
            List list = currentMacroContext.bodyParameterNames;
            if (BodyInstruction.this.bodyParameters != null) {
                for (int i = 0; i < BodyInstruction.this.bodyParameters.size(); i++) {
                    TemplateModel templateModelEval = ((Expression) BodyInstruction.this.bodyParameters.get(i)).eval(environment);
                    if (list != null && i < list.size()) {
                        String str = (String) list.get(i);
                        if (this.bodyVars == null) {
                            environment.getClass();
                            this.bodyVars = environment.new Namespace();
                        }
                        this.bodyVars.put(str, templateModelEval);
                    }
                }
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.LocalContext
        public TemplateModel getLocalVariable(String str) throws TemplateModelException {
            Environment.Namespace namespace = this.bodyVars;
            if (namespace == null) {
                return null;
            }
            return namespace.get(str);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.LocalContext
        public Collection getLocalVariableNames() {
            List list = this.invokingMacroContext.bodyParameterNames;
            return list == null ? Collections.EMPTY_LIST : list;
        }
    }
}
