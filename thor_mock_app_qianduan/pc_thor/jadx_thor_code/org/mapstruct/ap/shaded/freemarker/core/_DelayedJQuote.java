package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
public class _DelayedJQuote extends _DelayedConversionToString {
    public _DelayedJQuote(Object obj) {
        super(obj);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core._DelayedConversionToString
    protected String doConversion(Object obj) {
        return StringUtil.jQuote(_ErrorDescriptionBuilder.toString(obj));
    }
}
