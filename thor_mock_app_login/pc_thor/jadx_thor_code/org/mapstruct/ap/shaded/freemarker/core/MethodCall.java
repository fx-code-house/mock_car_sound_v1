package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.NullWriter;

/* loaded from: classes3.dex */
final class MethodCall extends Expression {
    private final ListLiteral arguments;
    private final Expression target;

    TemplateModel getConstantValue() {
        return null;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "...(...)";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return false;
    }

    MethodCall(Expression expression, ArrayList arrayList) {
        this(expression, new ListLiteral(arrayList));
    }

    private MethodCall(Expression expression, ListLiteral listLiteral) {
        this.target = expression;
        this.arguments = listLiteral;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        List valueList;
        TemplateModel templateModelEval = this.target.eval(environment);
        if (templateModelEval instanceof TemplateMethodModel) {
            TemplateMethodModel templateMethodModel = (TemplateMethodModel) templateModelEval;
            if (templateMethodModel instanceof TemplateMethodModelEx) {
                valueList = this.arguments.getModelList(environment);
            } else {
                valueList = this.arguments.getValueList(environment);
            }
            return environment.getObjectWrapper().wrap(templateMethodModel.exec(valueList));
        }
        if (templateModelEval instanceof Macro) {
            Macro macro = (Macro) templateModelEval;
            environment.setLastReturnValue(null);
            if (!macro.isFunction) {
                throw new _MiscTemplateException(environment, "A macro cannot be called in an expression.");
            }
            Writer out = environment.getOut();
            try {
                try {
                    environment.setOut(NullWriter.INSTANCE);
                    environment.visit(macro, null, this.arguments.items, null, null);
                    environment.setOut(out);
                    return environment.getLastReturnValue();
                } catch (IOException unused) {
                    throw new InternalError("This should be impossible.");
                }
            } catch (Throwable th) {
                environment.setOut(out);
                throw th;
            }
        }
        throw new NonMethodException(this.target, templateModelEval, environment);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.target.getCanonicalForm());
        stringBuffer.append("(");
        String canonicalForm = this.arguments.getCanonicalForm();
        stringBuffer.append(canonicalForm.substring(1, canonicalForm.length() - 1));
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        return new MethodCall(this.target.deepCloneWithIdentifierReplaced(str, expression, replacemenetState), (ListLiteral) this.arguments.deepCloneWithIdentifierReplaced(str, expression, replacemenetState));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return this.arguments.items.size() + 1;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.target;
        }
        if (i < getParameterCount()) {
            return this.arguments.items.get(i - 1);
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.CALLEE;
        }
        if (i < getParameterCount()) {
            return ParameterRole.ARGUMENT_VALUE;
        }
        throw new IndexOutOfBoundsException();
    }
}
