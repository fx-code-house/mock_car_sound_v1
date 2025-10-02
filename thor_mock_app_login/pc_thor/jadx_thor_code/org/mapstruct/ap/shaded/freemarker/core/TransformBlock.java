package org.mapstruct.ap.shaded.freemarker.core;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.template.EmptyMap;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel;

/* loaded from: classes3.dex */
final class TransformBlock extends TemplateElement {
    static /* synthetic */ Class class$freemarker$template$TemplateTransformModel;
    Map namedArgs;
    private volatile transient SoftReference sortedNamedArgsCache;
    private Expression transformExpression;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#transform";
    }

    TransformBlock(Expression expression, Map map, TemplateElement templateElement) {
        this.transformExpression = expression;
        this.namedArgs = map;
        this.nestedBlock = templateElement;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws Throwable {
        Map map;
        TemplateTransformModel transform = environment.getTransform(this.transformExpression);
        if (transform != null) {
            Map map2 = this.namedArgs;
            if (map2 != null && !map2.isEmpty()) {
                map = new HashMap();
                for (Map.Entry entry : this.namedArgs.entrySet()) {
                    map.put((String) entry.getKey(), ((Expression) entry.getValue()).eval(environment));
                }
            } else {
                map = EmptyMap.instance;
            }
            environment.visitAndTransform(this.nestedBlock, transform, map);
            return;
        }
        TemplateModel templateModelEval = this.transformExpression.eval(environment);
        Expression expression = this.transformExpression;
        Class[] clsArr = new Class[1];
        Class clsClass$ = class$freemarker$template$TemplateTransformModel;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel");
            class$freemarker$template$TemplateTransformModel = clsClass$;
        }
        clsArr[0] = clsClass$;
        throw new UnexpectedTypeException(expression, templateModelEval, "transform", clsArr, environment);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(Typography.less);
        }
        stringBuffer.append(getNodeTypeSymbol());
        stringBuffer.append(' ');
        stringBuffer.append(this.transformExpression);
        if (this.namedArgs != null) {
            for (Map.Entry entry : getSortedNamedArgs()) {
                stringBuffer.append(' ');
                stringBuffer.append(entry.getKey());
                stringBuffer.append('=');
                MessageUtil.appendExpressionAsUntearable(stringBuffer, (Expression) entry.getValue());
            }
        }
        if (z) {
            stringBuffer.append(">");
            if (this.nestedBlock != null) {
                stringBuffer.append(this.nestedBlock.getCanonicalForm());
            }
            stringBuffer.append("</").append(getNodeTypeSymbol()).append(Typography.greater);
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        Map map = this.namedArgs;
        return (map != null ? map.size() * 2 : 0) + 1;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        int i2;
        if (i == 0) {
            return this.transformExpression;
        }
        Map map = this.namedArgs;
        if (map != null && i - 1 < map.size() * 2) {
            Map.Entry entry = (Map.Entry) getSortedNamedArgs().get(i2 / 2);
            return i2 % 2 == 0 ? entry.getKey() : entry.getValue();
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.CALLEE;
        }
        int i2 = i - 1;
        if (i2 < this.namedArgs.size() * 2) {
            return i2 % 2 == 0 ? ParameterRole.ARGUMENT_NAME : ParameterRole.ARGUMENT_VALUE;
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
