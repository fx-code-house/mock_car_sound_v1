package org.mapstruct.ap.shaded.freemarker.core;

import java.util.Date;
import java.util.TimeZone;
import org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil;

/* loaded from: classes3.dex */
final class XSTemplateDateFormat extends ISOLikeTemplateDateFormat {
    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected String getDateDescription() {
        return "W3C XML Schema date";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected String getDateTimeDescription() {
        return "W3C XML Schema dateTime";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected String getTimeDescription() {
        return "W3C XML Schema time";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected boolean isXSMode() {
        return true;
    }

    XSTemplateDateFormat(String str, int i, int i2, boolean z, TimeZone timeZone, ISOLikeTemplateDateFormatFactory iSOLikeTemplateDateFormatFactory) throws UnknownDateTypeFormattingUnsupportedException, java.text.ParseException {
        super(str, i, i2, z, timeZone, iSOLikeTemplateDateFormatFactory);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected String format(Date date, boolean z, boolean z2, boolean z3, int i, TimeZone timeZone, DateUtil.DateToISO8601CalendarFactory dateToISO8601CalendarFactory) {
        return DateUtil.dateToXSString(date, z, z2, z3, i, timeZone, dateToISO8601CalendarFactory);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected Date parseDate(String str, TimeZone timeZone, DateUtil.CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateUtil.DateParseException {
        return DateUtil.parseXSDate(str, timeZone, calendarFieldsToDateConverter);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected Date parseTime(String str, TimeZone timeZone, DateUtil.CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateUtil.DateParseException {
        return DateUtil.parseXSTime(str, timeZone, calendarFieldsToDateConverter);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected Date parseDateTime(String str, TimeZone timeZone, DateUtil.CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateUtil.DateParseException {
        return DateUtil.parseXSDateTime(str, timeZone, calendarFieldsToDateConverter);
    }
}
