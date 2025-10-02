package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.template.EmptyMap;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDirectiveModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel;

/* loaded from: classes3.dex */
final class UnifiedCall extends TemplateElement {
    private List bodyParameterNames;
    boolean legacySyntax;
    private Expression nameExp;
    private Map namedArgs;
    private List positionalArgs;
    private volatile transient SoftReference sortedNamedArgsCache;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "@";
    }

    UnifiedCall(Expression expression, Map map, TemplateElement templateElement, List list) {
        this.nameExp = expression;
        this.namedArgs = map;
        this.nestedBlock = templateElement;
        this.bodyParameterNames = list;
    }

    UnifiedCall(Expression expression, List list, TemplateElement templateElement, List list2) {
        this.nameExp = expression;
        this.positionalArgs = list;
        this.nestedBlock = templateElement == TextBlock.EMPTY_BLOCK ? null : templateElement;
        this.bodyParameterNames = list2;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException, IOException {
        Map map;
        TemplateModel templateModelEval = this.nameExp.eval(environment);
        if (templateModelEval == Macro.DO_NOTHING_MACRO) {
            return;
        }
        if (templateModelEval instanceof Macro) {
            Macro macro = (Macro) templateModelEval;
            if (macro.isFunction && !this.legacySyntax) {
                throw new _MiscTemplateException(environment, new Object[]{"Routine ", new _DelayedJQuote(macro.getName()), " is a function, not a directive. Functions can only be called from expressions, like in ${f()}, ${x + f()} or ", "<@someDirective someParam=f() />", "."});
            }
            environment.visit(macro, this.namedArgs, this.positionalArgs, this.bodyParameterNames, this.nestedBlock);
            return;
        }
        boolean z = templateModelEval instanceof TemplateDirectiveModel;
        if (!z && !(templateModelEval instanceof TemplateTransformModel)) {
            if (templateModelEval == null) {
                throw InvalidReferenceException.getInstance(this.nameExp, environment);
            }
            throw new NonUserDefinedDirectiveLikeException(this.nameExp, templateModelEval, environment);
        }
        Map map2 = this.namedArgs;
        if (map2 != null && !map2.isEmpty()) {
            map = new HashMap();
            for (Map.Entry entry : this.namedArgs.entrySet()) {
                map.put((String) entry.getKey(), ((Expression) entry.getValue()).eval(environment));
            }
        } else {
            map = EmptyMap.instance;
        }
        if (z) {
            environment.visit(this.nestedBlock, (TemplateDirectiveModel) templateModelEval, map, this.bodyParameterNames);
        } else {
            environment.visitAndTransform(this.nestedBlock, (TemplateTransformModel) templateModelEval, map);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(Typography.less);
        }
        stringBuffer.append('@');
        MessageUtil.appendExpressionAsUntearable(stringBuffer, this.nameExp);
        int i = 0;
        boolean z2 = stringBuffer.charAt(stringBuffer.length() - 1) == ')';
        if (this.positionalArgs != null) {
            while (i < this.positionalArgs.size()) {
                Expression expression = (Expression) this.positionalArgs.get(i);
                if (i != 0) {
                    stringBuffer.append(',');
                }
                stringBuffer.append(' ');
                stringBuffer.append(expression.getCanonicalForm());
                i++;
            }
        } else {
            List sortedNamedArgs = getSortedNamedArgs();
            while (i < sortedNamedArgs.size()) {
                Map.Entry entry = (Map.Entry) sortedNamedArgs.get(i);
                Expression expression2 = (Expression) entry.getValue();
                stringBuffer.append(' ');
                stringBuffer.append(entry.getKey());
                stringBuffer.append('=');
                MessageUtil.appendExpressionAsUntearable(stringBuffer, expression2);
                i++;
            }
        }
        if (z) {
            if (this.nestedBlock == null) {
                stringBuffer.append("/>");
            } else {
                stringBuffer.append(Typography.greater);
                stringBuffer.append(this.nestedBlock.getCanonicalForm());
                stringBuffer.append("</@");
                if (!z2) {
                    Expression expression3 = this.nameExp;
                    if ((expression3 instanceof Identifier) || ((expression3 instanceof Dot) && ((Dot) expression3).onlyHasIdentifiers())) {
                        stringBuffer.append(this.nameExp.getCanonicalForm());
                    }
                }
                stringBuffer.append(Typography.greater);
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        List list = this.positionalArgs;
        int size = (list != null ? list.size() : 0) + 1;
        Map map = this.namedArgs;
        int size2 = size + (map != null ? map.size() * 2 : 0);
        List list2 = this.bodyParameterNames;
        return size2 + (list2 != null ? list2.size() : 0);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.nameExp;
        }
        List list = this.positionalArgs;
        int size = list != null ? list.size() : 0;
        int i2 = i - 1;
        if (i2 < size) {
            return this.positionalArgs.get(i2);
        }
        int i3 = size + 1;
        Map map = this.namedArgs;
        int i4 = i - i3;
        int size2 = (map != null ? map.size() : 0) * 2;
        if (i4 < size2) {
            Map.Entry entry = (Map.Entry) getSortedNamedArgs().get(i4 / 2);
            return i4 % 2 == 0 ? entry.getKey() : entry.getValue();
        }
        int i5 = i3 + size2;
        List list2 = this.bodyParameterNames;
        int i6 = i - i5;
        if (i6 < (list2 != null ? list2.size() : 0)) {
            return this.bodyParameterNames.get(i6);
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.CALLEE;
        }
        List list = this.positionalArgs;
        int size = list != null ? list.size() : 0;
        if (i - 1 < size) {
            return ParameterRole.ARGUMENT_VALUE;
        }
        int i2 = size + 1;
        Map map = this.namedArgs;
        int i3 = i - i2;
        int size2 = (map != null ? map.size() : 0) * 2;
        if (i3 < size2) {
            return i3 % 2 == 0 ? ParameterRole.ARGUMENT_NAME : ParameterRole.ARGUMENT_VALUE;
        }
        int i4 = i2 + size2;
        List list2 = this.bodyParameterNames;
        if (i - i4 < (list2 != null ? list2.size() : 0)) {
            return ParameterRole.TARGET_LOOP_VARIABLE;
        }
        throw new IndexOutOfBoundsException();
    }

    private List getSortedNamedArgs() {
        List list;
        SoftReference softReference = this.sortedNamedArgsCache;
        if (softReference != null && (list = (List) softReference.get()) != null) {
            return list;
        }
        List listSortMapOfExpressions = MiscUtil.sortMapOfExpressions(this.namedArgs);
        this.sortedNamedArgsCache = new SoftReference(listSortMapOfExpressions);
        return listSortMapOfExpressions;
    }
}
