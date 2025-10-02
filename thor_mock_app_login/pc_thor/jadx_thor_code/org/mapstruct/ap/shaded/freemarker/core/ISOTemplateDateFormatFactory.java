package org.mapstruct.ap.shaded.freemarker.core;

import java.util.TimeZone;

/* loaded from: classes3.dex */
class ISOTemplateDateFormatFactory extends ISOLikeTemplateDateFormatFactory {
    public ISOTemplateDateFormatFactory(TimeZone timeZone) {
        super(timeZone);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateDateFormatFactory
    public TemplateDateFormat get(int i, boolean z, String str) throws UnknownDateTypeFormattingUnsupportedException, java.text.ParseException {
        return new ISOTemplateDateFormat(str, 3, i, z, getTimeZone(), this);
    }
}
