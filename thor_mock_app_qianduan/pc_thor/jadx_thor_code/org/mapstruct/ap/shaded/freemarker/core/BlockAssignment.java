package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel;

/* loaded from: classes3.dex */
final class BlockAssignment extends TemplateElement {
    private final Expression namespaceExp;
    private final int scope;
    private final String varName;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 3;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    boolean isIgnorable() {
        return false;
    }

    BlockAssignment(TemplateElement templateElement, String str, int i, Expression expression) {
        this.nestedBlock = templateElement;
        this.varName = str;
        this.namespaceExp = expression;
        this.scope = i;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws IOException, TemplateException {
        if (this.nestedBlock != null) {
            environment.visitAndTransform(this.nestedBlock, new CaptureOutput(environment), null);
            return;
        }
        SimpleScalar simpleScalar = new SimpleScalar("");
        Expression expression = this.namespaceExp;
        if (expression != null) {
            ((Environment.Namespace) expression.eval(environment)).put(this.varName, simpleScalar);
            return;
        }
        int i = this.scope;
        if (i == 1) {
            environment.setVariable(this.varName, simpleScalar);
        } else if (i == 3) {
            environment.setGlobalVariable(this.varName, simpleScalar);
        } else if (i == 2) {
            environment.setLocalVariable(this.varName, simpleScalar);
        }
    }

    private class CaptureOutput implements TemplateTransformModel {
        private final Environment env;
        private final Environment.Namespace fnsModel;

        CaptureOutput(Environment environment) throws TemplateException {
            TemplateModel templateModelEval;
            this.env = environment;
            if (BlockAssignment.this.namespaceExp != null) {
                templateModelEval = BlockAssignment.this.namespaceExp.eval(environment);
                if (!(templateModelEval instanceof Environment.Namespace)) {
                    throw new NonNamespaceException(BlockAssignment.this.namespaceExp, templateModelEval, environment);
                }
            } else {
                templateModelEval = null;
            }
            this.fnsModel = (Environment.Namespace) templateModelEval;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel
        public Writer getWriter(Writer writer, Map map) {
            return new StringWriter() { // from class: org.mapstruct.ap.shaded.freemarker.core.BlockAssignment.CaptureOutput.1
                @Override // java.io.StringWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
                public void close() {
                    SimpleScalar simpleScalar = new SimpleScalar(toString());
                    int i = BlockAssignment.this.scope;
                    if (i == 1) {
                        if (CaptureOutput.this.fnsModel != null) {
                            CaptureOutput.this.fnsModel.put(BlockAssignment.this.varName, simpleScalar);
                            return;
                        } else {
                            CaptureOutput.this.env.setVariable(BlockAssignment.this.varName, simpleScalar);
                            return;
                        }
                    }
                    if (i == 2) {
                        CaptureOutput.this.env.setLocalVariable(BlockAssignment.this.varName, simpleScalar);
                    } else {
                        if (i != 3) {
                            return;
                        }
                        CaptureOutput.this.env.setGlobalVariable(BlockAssignment.this.varName, simpleScalar);
                    }
                }
            };
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append("<");
        }
        stringBuffer.append(getNodeTypeSymbol());
        stringBuffer.append(' ');
        stringBuffer.append(this.varName);
        if (this.namespaceExp != null) {
            stringBuffer.append(" in ");
            stringBuffer.append(this.namespaceExp.getCanonicalForm());
        }
        if (z) {
            stringBuffer.append(Typography.greater);
            stringBuffer.append(this.nestedBlock == null ? "" : this.nestedBlock.getCanonicalForm());
            stringBuffer.append("</");
            stringBuffer.append(getNodeTypeSymbol());
            stringBuffer.append(Typography.greater);
        } else {
            stringBuffer.append(" = .nested_output");
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return Assignment.getDirectiveName(this.scope);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.varName;
        }
        if (i == 1) {
            return new Integer(this.scope);
        }
        if (i == 2) {
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
            return ParameterRole.VARIABLE_SCOPE;
        }
        if (i == 2) {
            return ParameterRole.NAMESPACE;
        }
        throw new IndexOutOfBoundsException();
    }
}
