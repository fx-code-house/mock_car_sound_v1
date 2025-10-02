package org.mapstruct.ap.shaded.freemarker.core;

import java.util.Date;
import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.core.Macro;
import org.mapstruct.ap.shaded.freemarker.template.Configuration;
import org.mapstruct.ap.shaded.freemarker.template.SimpleDate;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
final class BuiltinVariable extends Expression {
    static final String CURRENT_NODE = "current_node";
    static final String DATA_MODEL = "data_model";
    static final String ERROR = "error";
    static final String GLOBALS = "globals";
    static final String LANG = "lang";
    static final String LOCALE = "locale";
    static final String LOCALE_OBJECT = "locale_object";
    static final String LOCALS = "locals";
    static final String MAIN = "main";
    static final String NAMESPACE = "namespace";
    static final String NODE = "node";
    static final String NOW = "now";
    static final String OUTPUT_ENCODING = "output_encoding";
    static final String PASS = "pass";
    static final String TEMPLATE_NAME = "template_name";
    static final String URL_ESCAPING_CHARSET = "url_escaping_charset";
    static final String VARS = "vars";
    static final String VERSION = "version";
    private final String name;

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        return this;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 0;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return false;
    }

    BuiltinVariable(String str) throws ParseException {
        String strIntern = str.intern();
        this.name = strIntern;
        if (strIntern != TEMPLATE_NAME && strIntern != NAMESPACE && strIntern != MAIN && strIntern != GLOBALS && strIntern != LOCALS && strIntern != LANG && strIntern != "locale" && strIntern != LOCALE_OBJECT && strIntern != DATA_MODEL && strIntern != CURRENT_NODE && strIntern != NODE && strIntern != PASS && strIntern != VARS && strIntern != VERSION && strIntern != "output_encoding" && strIntern != "url_escaping_charset" && strIntern != "error" && strIntern != NOW) {
            throw new ParseException(new StringBuffer("Unknown built-in variable: ").append(strIntern).toString(), this);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        String str = this.name;
        if (str == NAMESPACE) {
            return environment.getCurrentNamespace();
        }
        if (str == MAIN) {
            return environment.getMainNamespace();
        }
        if (str == GLOBALS) {
            return environment.getGlobalVariables();
        }
        if (str == LOCALS) {
            Macro.Context currentMacroContext = environment.getCurrentMacroContext();
            if (currentMacroContext == null) {
                return null;
            }
            return currentMacroContext.getLocals();
        }
        if (str == DATA_MODEL) {
            return environment.getDataModel();
        }
        if (str == VARS) {
            return new VarsHash(environment);
        }
        if (str == "locale") {
            return new SimpleScalar(environment.getLocale().toString());
        }
        if (str == LOCALE_OBJECT) {
            return environment.getObjectWrapper().wrap(environment.getLocale());
        }
        if (str == LANG) {
            return new SimpleScalar(environment.getLocale().getLanguage());
        }
        if (str == CURRENT_NODE || str == NODE) {
            return environment.getCurrentVisitorNode();
        }
        if (str == TEMPLATE_NAME) {
            return new SimpleScalar(environment.getTemplate().getName());
        }
        if (str == PASS) {
            return Macro.DO_NOTHING_MACRO;
        }
        if (str == VERSION) {
            return new SimpleScalar(Configuration.getVersionNumber());
        }
        if (str == "output_encoding") {
            String outputEncoding = environment.getOutputEncoding();
            if (outputEncoding != null) {
                return new SimpleScalar(outputEncoding);
            }
            return null;
        }
        if (str == "url_escaping_charset") {
            String uRLEscapingCharset = environment.getURLEscapingCharset();
            if (uRLEscapingCharset != null) {
                return new SimpleScalar(uRLEscapingCharset);
            }
            return null;
        }
        if (str == "error") {
            return new SimpleScalar(environment.getCurrentRecoveredErrorMessage());
        }
        if (str == NOW) {
            return new SimpleDate(new Date(), 3);
        }
        throw new _MiscTemplateException(this, new Object[]{"Invalid built-in variable: ", this.name});
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String toString() {
        return new StringBuffer(".").append(this.name).toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return new StringBuffer(".").append(this.name).toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return getCanonicalForm();
    }

    static class VarsHash implements TemplateHashModel {
        Environment env;

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public boolean isEmpty() {
            return false;
        }

        VarsHash(Environment environment) {
            this.env = environment;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public TemplateModel get(String str) throws TemplateModelException {
            return this.env.getVariable(str);
        }
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
