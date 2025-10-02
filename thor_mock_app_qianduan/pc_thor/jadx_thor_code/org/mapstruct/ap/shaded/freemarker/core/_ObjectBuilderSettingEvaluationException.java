package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
public class _ObjectBuilderSettingEvaluationException extends Exception {
    public _ObjectBuilderSettingEvaluationException(String str, Throwable th) {
        super(str, th);
    }

    public _ObjectBuilderSettingEvaluationException(String str) {
        super(str);
    }

    public _ObjectBuilderSettingEvaluationException(String str, String str2, int i) {
        super(new StringBuffer("Expression syntax error: Expected a(n) ").append(str).append(", but ").append(i < str2.length() ? new StringBuffer("found character ").append(StringUtil.jQuote(new StringBuffer("").append(str2.charAt(i)).toString())).append(" at position ").append(i + 1).append(".").toString() : "the end of the parsed string was reached.").toString());
    }
}
