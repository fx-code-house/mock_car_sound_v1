package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.template.SimpleSequence;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
final class RecurseNode extends TemplateElement {
    Expression namespaces;
    Expression targetNode;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#recurse";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    RecurseNode(Expression expression, Expression expression2) {
        this.targetNode = expression;
        this.namespaces = expression2;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws IOException, TemplateException {
        Expression expression = this.targetNode;
        TemplateModel templateModelEval = expression == null ? null : expression.eval(environment);
        if (templateModelEval != null && !(templateModelEval instanceof TemplateNodeModel)) {
            throw new NonNodeException(this.targetNode, templateModelEval, "node", environment);
        }
        Expression expression2 = this.namespaces;
        TemplateModel templateModelEval2 = expression2 == null ? null : expression2.eval(environment);
        Expression expression3 = this.namespaces;
        if (expression3 instanceof StringLiteral) {
            templateModelEval2 = environment.importLib(((TemplateScalarModel) templateModelEval2).getAsString(), (String) null);
        } else if (expression3 instanceof ListLiteral) {
            templateModelEval2 = ((ListLiteral) expression3).evaluateStringsToNamespaces(environment);
        }
        if (templateModelEval2 != null) {
            if (templateModelEval2 instanceof TemplateHashModel) {
                SimpleSequence simpleSequence = new SimpleSequence(1);
                simpleSequence.add(templateModelEval2);
                templateModelEval2 = simpleSequence;
            } else if (!(templateModelEval2 instanceof TemplateSequenceModel)) {
                if (this.namespaces != null) {
                    throw new NonSequenceException(this.namespaces, templateModelEval2, environment);
                }
                throw new _MiscTemplateException(environment, "Expecting a sequence of namespaces after \"using\"");
            }
        }
        environment.recurse((TemplateNodeModel) templateModelEval, (TemplateSequenceModel) templateModelEval2);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(Typography.less);
        }
        stringBuffer.append(getNodeTypeSymbol());
        if (this.targetNode != null) {
            stringBuffer.append(' ');
            stringBuffer.append(this.targetNode.getCanonicalForm());
        }
        if (this.namespaces != null) {
            stringBuffer.append(" using ");
            stringBuffer.append(this.namespaces.getCanonicalForm());
        }
        if (z) {
            stringBuffer.append("/>");
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.targetNode;
        }
        if (i == 1) {
            return this.namespaces;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.NODE;
        }
        if (i == 1) {
            return ParameterRole.NAMESPACE;
        }
        throw new IndexOutOfBoundsException();
    }
}
