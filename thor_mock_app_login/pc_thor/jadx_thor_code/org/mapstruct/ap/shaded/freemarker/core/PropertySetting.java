package org.mapstruct.ap.shaded.freemarker.core;

import kotlin.text.Typography;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;

/* loaded from: classes3.dex */
final class PropertySetting extends TemplateElement {
    private final String key;
    private final Expression value;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#setting";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    PropertySetting(String str, Expression expression) {
        this.key = str;
        this.value = expression;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    void setLocation(Template template, int i, int i2, int i3, int i4) throws ParseException {
        super.setLocation(template, i, i2, i3, i4);
        if (!this.key.equals(Configurable.LOCALE_KEY) && !this.key.equals(Configurable.NUMBER_FORMAT_KEY) && !this.key.equals(Configurable.TIME_FORMAT_KEY) && !this.key.equals(Configurable.DATE_FORMAT_KEY) && !this.key.equals(Configurable.DATETIME_FORMAT_KEY) && !this.key.equals(Configurable.TIME_ZONE_KEY) && !this.key.equals(Configurable.SQL_DATE_AND_TIME_TIME_ZONE_KEY) && !this.key.equals(Configurable.BOOLEAN_FORMAT_KEY) && !this.key.equals(Configurable.CLASSIC_COMPATIBLE_KEY) && !this.key.equals(Configurable.URL_ESCAPING_CHARSET_KEY) && !this.key.equals(Configurable.OUTPUT_ENCODING_KEY)) {
            throw new ParseException(new StringBuffer("Invalid setting name, or it's not allowed to change the value of the setting with FTL: ").append(this.key).toString(), template, i2, i, i4, i3);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException {
        String strEvalAndCoerceToString;
        TemplateModel templateModelEval = this.value.eval(environment);
        if (templateModelEval instanceof TemplateScalarModel) {
            strEvalAndCoerceToString = ((TemplateScalarModel) templateModelEval).getAsString();
        } else if (templateModelEval instanceof TemplateBooleanModel) {
            strEvalAndCoerceToString = ((TemplateBooleanModel) templateModelEval).getAsBoolean() ? BooleanUtils.TRUE : BooleanUtils.FALSE;
        } else if (templateModelEval instanceof TemplateNumberModel) {
            strEvalAndCoerceToString = ((TemplateNumberModel) templateModelEval).getAsNumber().toString();
        } else {
            strEvalAndCoerceToString = this.value.evalAndCoerceToString(environment);
        }
        environment.setSetting(this.key, strEvalAndCoerceToString);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(Typography.less);
        }
        stringBuffer.append(getNodeTypeSymbol());
        stringBuffer.append(' ');
        stringBuffer.append(this.key);
        stringBuffer.append('=');
        stringBuffer.append(this.value.getCanonicalForm());
        if (z) {
            stringBuffer.append("/>");
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.key;
        }
        if (i == 1) {
            return this.value;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.ITEM_KEY;
        }
        if (i == 1) {
            return ParameterRole.ITEM_VALUE;
        }
        throw new IndexOutOfBoundsException();
    }
}
