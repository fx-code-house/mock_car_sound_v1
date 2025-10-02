package org.mapstruct.ap.shaded.freemarker.core;

import java.util.Date;
import java.util.TimeZone;
import org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil;

/* loaded from: classes3.dex */
final class ISOTemplateDateFormat extends ISOLikeTemplateDateFormat {
    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected String getDateDescription() {
        return "ISO 8601 (subset) date";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected String getDateTimeDescription() {
        return "ISO 8601 (subset) date-time";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected String getTimeDescription() {
        return "ISO 8601 (subset) time";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected boolean isXSMode() {
        return false;
    }

    ISOTemplateDateFormat(String str, int i, int i2, boolean z, TimeZone timeZone, ISOLikeTemplateDateFormatFactory iSOLikeTemplateDateFormatFactory) throws UnknownDateTypeFormattingUnsupportedException, java.text.ParseException {
        super(str, i, i2, z, timeZone, iSOLikeTemplateDateFormatFactory);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected String format(Date date, boolean z, boolean z2, boolean z3, int i, TimeZone timeZone, DateUtil.DateToISO8601CalendarFactory dateToISO8601CalendarFactory) {
        return DateUtil.dateToISO8601String(date, z, z2, z2 && z3, i, timeZone, dateToISO8601CalendarFactory);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected Date parseDate(String str, TimeZone timeZone, DateUtil.CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateUtil.DateParseException {
        return DateUtil.parseISO8601Date(str, timeZone, calendarFieldsToDateConverter);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected Date parseTime(String str, TimeZone timeZone, DateUtil.CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateUtil.DateParseException {
        return DateUtil.parseISO8601Time(str, timeZone, calendarFieldsToDateConverter);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat
    protected Date parseDateTime(String str, TimeZone timeZone, DateUtil.CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateUtil.DateParseException {
        return DateUtil.parseISO8601DateTime(str, timeZone, calendarFieldsToDateConverter);
    }
}
