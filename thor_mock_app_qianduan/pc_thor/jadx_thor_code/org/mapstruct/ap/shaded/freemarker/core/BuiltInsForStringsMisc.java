package org.mapstruct.ap.shaded.freemarker.core;

import java.io.StringReader;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template._TemplateAPI;

/* loaded from: classes3.dex */
class BuiltInsForStringsMisc {

    static class booleanBI extends BuiltInForString {
        booleanBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateException {
            boolean z = true;
            if (!str.equals(BooleanUtils.TRUE)) {
                if (str.equals(BooleanUtils.FALSE)) {
                    z = false;
                } else if (!str.equals(environment.getTrueStringValue())) {
                    if (!str.equals(environment.getFalseStringValue())) {
                        throw new _MiscTemplateException(this, environment, new Object[]{"Can't convert this string to boolean: ", new _DelayedJQuote(str)});
                    }
                    z = false;
                }
            }
            return z ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class evalBI extends BuiltInForString {
        evalBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws ParseException, TemplateException {
            FMParserTokenManager fMParserTokenManager = new FMParserTokenManager(new SimpleCharStream(new StringReader(new StringBuffer("(").append(str).append(")").toString()), -1000000000, 1, str.length() + 2));
            fMParserTokenManager.incompatibleImprovements = _TemplateAPI.getTemplateLanguageVersionAsInt(this);
            fMParserTokenManager.SwitchTo(2);
            FMParser fMParser = new FMParser(fMParserTokenManager);
            fMParser.setTemplate(getTemplate());
            try {
                try {
                    try {
                        return fMParser.Expression().eval(environment);
                    } catch (TemplateException e) {
                        throw new _MiscTemplateException(this, environment, new Object[]{"Failed to \"?", this.key, "\" string with this error:\n\n", "---begin-message---\n", new _DelayedGetMessageWithoutStackTop(e), "\n---end-message---", "\n\nThe failing expression:"});
                    }
                } catch (TokenMgrError e2) {
                    throw e2.toParseException(getTemplate());
                }
            } catch (ParseException e3) {
                throw new _MiscTemplateException(this, environment, new Object[]{"Failed to \"?", this.key, "\" string with this error:\n\n", "---begin-message---\n", new _DelayedGetMessage(e3), "\n---end-message---", "\n\nThe failing expression:"});
            }
        }
    }

    static class numberBI extends BuiltInForString {
        numberBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateException {
            try {
                return new SimpleNumber(environment.getArithmeticEngine().toNumber(str));
            } catch (NumberFormatException unused) {
                throw NonNumericalException.newMalformedNumberException(this, str, environment);
            }
        }
    }

    private BuiltInsForStringsMisc() {
    }
}
