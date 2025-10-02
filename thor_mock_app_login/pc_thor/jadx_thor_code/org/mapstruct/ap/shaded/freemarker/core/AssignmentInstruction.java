package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.util.ArrayList;
import kotlin.text.Typography;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
final class AssignmentInstruction extends TemplateElement {
    private Expression namespaceExp;
    private int scope;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    AssignmentInstruction(int i) {
        this.scope = i;
        this.nestedElements = new ArrayList(1);
    }

    void addAssignment(Assignment assignment) {
        this.nestedElements.add(assignment);
    }

    void setNamespaceExp(Expression expression) {
        this.namespaceExp = expression;
        for (int i = 0; i < this.nestedElements.size(); i++) {
            ((Assignment) this.nestedElements.get(i)).setNamespaceExp(expression);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException, IOException {
        for (int i = 0; i < this.nestedElements.size(); i++) {
            environment.visit((Assignment) this.nestedElements.get(i));
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(Typography.less);
        }
        stringBuffer.append(Assignment.getDirectiveName(this.scope));
        if (z) {
            stringBuffer.append(' ');
            for (int i = 0; i < this.nestedElements.size(); i++) {
                stringBuffer.append(((Assignment) this.nestedElements.get(i)).getCanonicalForm());
                if (i < this.nestedElements.size() - 1) {
                    stringBuffer.append(StringUtils.SPACE);
                }
            }
        } else {
            stringBuffer.append("-container");
        }
        if (this.namespaceExp != null) {
            stringBuffer.append(" in ");
            stringBuffer.append(this.namespaceExp.getCanonicalForm());
        }
        if (z) {
            stringBuffer.append("/>");
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return new Integer(this.scope);
        }
        if (i != 1) {
            return null;
        }
        return this.namespaceExp;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.VARIABLE_SCOPE;
        }
        if (i != 1) {
            return null;
        }
        return ParameterRole.NAMESPACE;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return Assignment.getDirectiveName(this.scope);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    public TemplateElement postParseCleanup(boolean z) throws ParseException {
        super.postParseCleanup(z);
        if (this.nestedElements.size() != 1) {
            return this;
        }
        Assignment assignment = (Assignment) this.nestedElements.get(0);
        assignment.setLocation(getTemplate(), this, this);
        return assignment;
    }
}
