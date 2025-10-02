package org.mapstruct.ap.shaded.freemarker.core;

import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
final class Assignment extends TemplateElement {
    static final int GLOBAL = 3;
    static final int LOCAL = 2;
    static final int NAMESPACE = 1;
    private Expression namespaceExp;
    private int scope;
    private Expression value;
    private String variableName;

    static String getDirectiveName(int i) {
        return i == 2 ? "#local" : i == 3 ? "#global" : i == 1 ? "#assign" : "#{unknown_assignment_type}";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 4;
    }

    Assignment(String str, Expression expression, int i) {
        this.variableName = str;
        this.value = expression;
        this.scope = i;
    }

    void setNamespaceExp(Expression expression) {
        this.namespaceExp = expression;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException {
        Environment.Namespace currentNamespace;
        Expression expression = this.namespaceExp;
        if (expression != null) {
            TemplateModel templateModelEval = expression.eval(environment);
            try {
                currentNamespace = (Environment.Namespace) templateModelEval;
                if (currentNamespace == null) {
                    throw InvalidReferenceException.getInstance(this.namespaceExp, environment);
                }
            } catch (ClassCastException unused) {
                throw new NonNamespaceException(this.namespaceExp, templateModelEval, environment);
            }
        } else {
            currentNamespace = null;
        }
        TemplateModel templateModelEval2 = this.value.eval(environment);
        if (templateModelEval2 == null) {
            if (environment.isClassicCompatible()) {
                templateModelEval2 = TemplateScalarModel.EMPTY_STRING;
            } else {
                throw InvalidReferenceException.getInstance(this.value, environment);
            }
        }
        int i = this.scope;
        if (i == 2) {
            environment.setLocalVariable(this.variableName, templateModelEval2);
            return;
        }
        if (currentNamespace == null) {
            if (i == 3) {
                currentNamespace = environment.getGlobalNamespace();
            } else if (i == 1) {
                currentNamespace = environment.getCurrentNamespace();
            } else {
                throw new BugException(new StringBuffer("Unexpected scope type: ").append(this.scope).toString());
            }
        }
        currentNamespace.put(this.variableName, templateModelEval2);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        String nodeTypeSymbol = this.parent instanceof AssignmentInstruction ? null : getNodeTypeSymbol();
        if (nodeTypeSymbol != null) {
            if (z) {
                stringBuffer.append("<");
            }
            stringBuffer.append(nodeTypeSymbol);
            stringBuffer.append(' ');
        }
        boolean z2 = true;
        if (this.variableName.length() > 0 && Character.isJavaIdentifierStart(this.variableName.charAt(0))) {
            int i = 1;
            while (i < this.variableName.length() && Character.isJavaIdentifierPart(this.variableName.charAt(i))) {
                i++;
            }
            if (i >= this.variableName.length()) {
                z2 = false;
            }
        }
        if (z2) {
            stringBuffer.append(Typography.quote);
            stringBuffer.append(StringUtil.FTLStringLiteralEnc(this.variableName));
            stringBuffer.append(Typography.quote);
        } else {
            stringBuffer.append(this.variableName);
        }
        stringBuffer.append(" = ");
        stringBuffer.append(this.value.getCanonicalForm());
        if (nodeTypeSymbol != null) {
            if (this.namespaceExp != null) {
                stringBuffer.append(" in ");
                stringBuffer.append(this.namespaceExp.getCanonicalForm());
            }
            if (z) {
                stringBuffer.append(">");
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return getDirectiveName(this.scope);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.variableName;
        }
        if (i == 1) {
            return this.value;
        }
        if (i == 2) {
            return new Integer(this.scope);
        }
        if (i == 3) {
            return this.namespaceExp;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.ASSIGNMENT_TARGET;
        }
        if (i == 1) {
            return ParameterRole.ASSIGNMENT_SOURCE;
        }
        if (i == 2) {
            return ParameterRole.VARIABLE_SCOPE;
        }
        if (i == 3) {
            return ParameterRole.NAMESPACE;
        }
        throw new IndexOutOfBoundsException();
    }
}
