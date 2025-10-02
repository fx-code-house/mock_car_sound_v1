package org.mapstruct.ap.shaded.freemarker.core;

import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class _DelayedAOrAn extends _DelayedConversionToString {
    public _DelayedAOrAn(Object obj) {
        super(obj);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core._DelayedConversionToString
    protected String doConversion(Object obj) {
        String string = obj.toString();
        return new StringBuffer().append(MessageUtil.getAOrAn(string)).append(StringUtils.SPACE).append(string).toString();
    }
}
