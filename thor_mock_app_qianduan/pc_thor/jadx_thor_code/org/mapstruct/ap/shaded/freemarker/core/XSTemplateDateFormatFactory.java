package org.mapstruct.ap.shaded.freemarker.core;

import java.util.TimeZone;

/* loaded from: classes3.dex */
class XSTemplateDateFormatFactory extends ISOLikeTemplateDateFormatFactory {
    public XSTemplateDateFormatFactory(TimeZone timeZone) {
        super(timeZone);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateDateFormatFactory
    public TemplateDateFormat get(int i, boolean z, String str) throws UnknownDateTypeFormattingUnsupportedException, java.text.ParseException {
        return new XSTemplateDateFormat(str, 2, i, z, getTimeZone(), this);
    }
}
