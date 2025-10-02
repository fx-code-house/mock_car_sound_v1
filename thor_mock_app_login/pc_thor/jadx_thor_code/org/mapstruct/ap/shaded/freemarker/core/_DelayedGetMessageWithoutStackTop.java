package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
public class _DelayedGetMessageWithoutStackTop extends _DelayedConversionToString {
    public _DelayedGetMessageWithoutStackTop(TemplateException templateException) {
        super(templateException);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core._DelayedConversionToString
    protected String doConversion(Object obj) {
        return ((TemplateException) obj).getMessageWithoutStackTop();
    }
}
