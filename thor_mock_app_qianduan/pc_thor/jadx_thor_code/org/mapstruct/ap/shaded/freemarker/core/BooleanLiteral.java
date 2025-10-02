package org.mapstruct.ap.shaded.freemarker.core;

import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
final class BooleanLiteral extends Expression {
    private final boolean val;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 0;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return true;
    }

    public BooleanLiteral(boolean z) {
        this.val = z;
    }

    static TemplateBooleanModel getTemplateModel(boolean z) {
        return z ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean evalToBoolean(Environment environment) {
        return this.val;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return this.val ? BooleanUtils.TRUE : BooleanUtils.FALSE;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return getCanonicalForm();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String toString() {
        return this.val ? BooleanUtils.TRUE : BooleanUtils.FALSE;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) {
        return this.val ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        return new BooleanLiteral(this.val);
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
