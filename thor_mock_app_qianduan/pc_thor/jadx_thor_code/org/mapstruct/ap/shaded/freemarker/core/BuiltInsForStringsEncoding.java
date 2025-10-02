package org.mapstruct.ap.shaded.freemarker.core;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template._TemplateAPI;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
class BuiltInsForStringsEncoding {

    static class htmlBI extends BuiltInForString implements ICIChainMember {
        private final BIBeforeICI2d3d20 prevICIObj = new BIBeforeICI2d3d20();

        htmlBI() {
        }

        static class BIBeforeICI2d3d20 extends BuiltInForString {
            BIBeforeICI2d3d20() {
            }

            @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
            TemplateModel calculateResult(String str, Environment environment) {
                return new SimpleScalar(StringUtil.HTMLEnc(str));
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            return new SimpleScalar(StringUtil.XHTMLEnc(str));
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ICIChainMember
        public int getMinimumICIVersion() {
            return _TemplateAPI.VERSION_INT_2_3_20;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ICIChainMember
        public Object getPreviousICIChainMember() {
            return this.prevICIObj;
        }
    }

    static class j_stringBI extends BuiltInForString {
        j_stringBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            return new SimpleScalar(StringUtil.javaStringEnc(str));
        }
    }

    static class js_stringBI extends BuiltInForString {
        js_stringBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            return new SimpleScalar(StringUtil.javaScriptStringEnc(str));
        }
    }

    static class json_stringBI extends BuiltInForString {
        json_stringBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            return new SimpleScalar(StringUtil.jsonStringEnc(str));
        }
    }

    static class rtfBI extends BuiltInForString {
        rtfBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            return new SimpleScalar(StringUtil.RTFEnc(str));
        }
    }

    static class urlBI extends BuiltInForString {
        urlBI() {
        }

        static class UrlBIResult extends AbstractUrlBIResult {
            protected UrlBIResult(BuiltIn builtIn, String str, Environment environment) {
                super(builtIn, str, environment);
            }

            @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInsForStringsEncoding.AbstractUrlBIResult
            protected String encodeWithCharset(String str) throws UnsupportedEncodingException {
                return StringUtil.URLEnc(this.targetAsString, str);
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            return new UrlBIResult(this, str, environment);
        }
    }

    static class urlPathBI extends BuiltInForString {
        urlPathBI() {
        }

        static class UrlPathBIResult extends AbstractUrlBIResult {
            protected UrlPathBIResult(BuiltIn builtIn, String str, Environment environment) {
                super(builtIn, str, environment);
            }

            @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInsForStringsEncoding.AbstractUrlBIResult
            protected String encodeWithCharset(String str) throws UnsupportedEncodingException {
                return StringUtil.URLPathEnc(this.targetAsString, str);
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            return new UrlPathBIResult(this, str, environment);
        }
    }

    static class xhtmlBI extends BuiltInForString {
        xhtmlBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            return new SimpleScalar(StringUtil.XHTMLEnc(str));
        }
    }

    static class xmlBI extends BuiltInForString {
        xmlBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            return new SimpleScalar(StringUtil.XMLEnc(str));
        }
    }

    private BuiltInsForStringsEncoding() {
    }

    static abstract class AbstractUrlBIResult implements TemplateScalarModel, TemplateMethodModel {
        private String cachedResult;
        private final Environment env;
        protected final BuiltIn parent;
        protected final String targetAsString;

        protected abstract String encodeWithCharset(String str) throws UnsupportedEncodingException;

        protected AbstractUrlBIResult(BuiltIn builtIn, String str, Environment environment) {
            this.parent = builtIn;
            this.targetAsString = str;
            this.env = environment;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
        public Object exec(List list) throws TemplateModelException {
            this.parent.checkMethodArgCount(list.size(), 1);
            try {
                return new SimpleScalar(encodeWithCharset((String) list.get(0)));
            } catch (UnsupportedEncodingException e) {
                throw new _TemplateModelException(e, "Failed to execute URL encoding.");
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
        public String getAsString() throws TemplateModelException {
            if (this.cachedResult == null) {
                String effectiveURLEscapingCharset = this.env.getEffectiveURLEscapingCharset();
                if (effectiveURLEscapingCharset == null) {
                    throw new _TemplateModelException("To do URL encoding, the framework that encloses FreeMarker must specify the output encoding or the URL encoding charset, so ask the programmers to fix it. Or, as a last chance, you can set the url_encoding_charset setting in the template, e.g. <#setting url_escaping_charset='ISO-8859-1'>, or give the charset explicitly to the buit-in, e.g. foo?url('ISO-8859-1').");
                }
                try {
                    this.cachedResult = encodeWithCharset(effectiveURLEscapingCharset);
                } catch (UnsupportedEncodingException e) {
                    throw new _TemplateModelException(e, "Failed to execute URL encoding.");
                }
            }
            return this.cachedResult;
        }
    }
}
