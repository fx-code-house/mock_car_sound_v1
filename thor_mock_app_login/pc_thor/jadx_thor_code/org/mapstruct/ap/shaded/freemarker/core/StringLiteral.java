package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.io.StringReader;
import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateExceptionHandler;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
final class StringLiteral extends Expression implements TemplateScalarModel {
    private TemplateElement dynamicValue;
    private final String value;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 1;
    }

    StringLiteral(String str) {
        this.value = str;
    }

    void checkInterpolation() throws ParseException {
        if (this.value.length() > 3) {
            if (this.value.indexOf("${") >= 0 || this.value.indexOf("#{") >= 0) {
                FMParserTokenManager fMParserTokenManager = new FMParserTokenManager(new SimpleCharStream(new StringReader(this.value), this.beginLine, this.beginColumn + 1, this.value.length()));
                fMParserTokenManager.onlyTextOutput = true;
                FMParser fMParser = new FMParser(fMParserTokenManager);
                fMParser.setTemplate(getTemplate());
                try {
                    this.dynamicValue = fMParser.FreeMarkerText();
                    this.constantValue = null;
                } catch (ParseException e) {
                    e.setTemplateName(getTemplate().getName());
                    throw e;
                }
            }
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        return new SimpleScalar(evalAndCoerceToString(environment));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
    public String getAsString() {
        return this.value;
    }

    boolean isSingleInterpolationLiteral() {
        TemplateElement templateElement = this.dynamicValue;
        return templateElement != null && templateElement.getChildCount() == 1 && (this.dynamicValue.getChildAt(0) instanceof DollarVariable);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    String evalAndCoerceToString(Environment environment) throws TemplateException {
        if (this.dynamicValue == null) {
            return this.value;
        }
        TemplateExceptionHandler templateExceptionHandler = environment.getTemplateExceptionHandler();
        environment.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        try {
            try {
                return environment.renderElementToString(this.dynamicValue);
            } catch (IOException e) {
                throw new _MiscTemplateException(e, environment);
            }
        } finally {
            environment.setTemplateExceptionHandler(templateExceptionHandler);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return new StringBuffer("\"").append(StringUtil.FTLStringLiteralEnc(this.value)).append("\"").toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return this.dynamicValue == null ? getCanonicalForm() : "dynamic \"...\"";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return this.dynamicValue == null;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        StringLiteral stringLiteral = new StringLiteral(this.value);
        stringLiteral.dynamicValue = this.dynamicValue;
        return stringLiteral;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this.dynamicValue;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return ParameterRole.EMBEDDED_TEMPLATE;
    }
}
