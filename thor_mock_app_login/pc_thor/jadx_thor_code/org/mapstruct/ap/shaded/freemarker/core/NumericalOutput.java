package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
final class NumericalOutput extends TemplateElement {
    private final Expression expression;
    private volatile FormatHolder formatCache;
    private final boolean hasFormat;
    private final int maxFracDigits;
    private final int minFracDigits;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#{...}";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 3;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    boolean heedsOpeningWhitespace() {
        return true;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    boolean heedsTrailingWhitespace() {
        return true;
    }

    NumericalOutput(Expression expression) {
        this.expression = expression;
        this.hasFormat = false;
        this.minFracDigits = 0;
        this.maxFracDigits = 0;
    }

    NumericalOutput(Expression expression, int i, int i2) {
        this.expression = expression;
        this.hasFormat = true;
        this.minFracDigits = i;
        this.maxFracDigits = i2;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException, IOException {
        Number numberEvalToNumber = this.expression.evalToNumber(environment);
        FormatHolder formatHolder = this.formatCache;
        if (formatHolder == null || !formatHolder.locale.equals(environment.getLocale())) {
            synchronized (this) {
                formatHolder = this.formatCache;
                if (formatHolder == null || !formatHolder.locale.equals(environment.getLocale())) {
                    NumberFormat numberInstance = NumberFormat.getNumberInstance(environment.getLocale());
                    if (this.hasFormat) {
                        numberInstance.setMinimumFractionDigits(this.minFracDigits);
                        numberInstance.setMaximumFractionDigits(this.maxFracDigits);
                    } else {
                        numberInstance.setMinimumFractionDigits(0);
                        numberInstance.setMaximumFractionDigits(50);
                    }
                    numberInstance.setGroupingUsed(false);
                    this.formatCache = new FormatHolder(numberInstance, environment.getLocale());
                    formatHolder = this.formatCache;
                }
            }
        }
        environment.getOut().write(formatHolder.format.format(numberEvalToNumber));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer("#{");
        stringBuffer.append(this.expression.getCanonicalForm());
        if (this.hasFormat) {
            stringBuffer.append(" ; m");
            stringBuffer.append(this.minFracDigits);
            stringBuffer.append("M");
            stringBuffer.append(this.maxFracDigits);
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    private static class FormatHolder {
        final NumberFormat format;
        final Locale locale;

        FormatHolder(NumberFormat numberFormat, Locale locale) {
            this.format = numberFormat;
            this.locale = locale;
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.expression;
        }
        if (i == 1) {
            return new Integer(this.minFracDigits);
        }
        if (i == 2) {
            return new Integer(this.maxFracDigits);
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.CONTENT;
        }
        if (i == 1) {
            return ParameterRole.MINIMUM_DECIMALS;
        }
        if (i == 2) {
            return ParameterRole.MAXIMUM_DECIMALS;
        }
        throw new IndexOutOfBoundsException();
    }
}
