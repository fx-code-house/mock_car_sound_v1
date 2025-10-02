package org.mapstruct.ap.shaded.freemarker.core;

import java.util.TimeZone;
import org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil;

/* loaded from: classes3.dex */
abstract class ISOLikeTemplateDateFormatFactory extends TemplateDateFormatFactory {
    private DateUtil.CalendarFieldsToDateConverter calendarFieldsToDateConverter;
    private DateUtil.DateToISO8601CalendarFactory dateToCalenderFieldsCalculator;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateDateFormatFactory
    public boolean isLocaleBound() {
        return false;
    }

    public ISOLikeTemplateDateFormatFactory(TimeZone timeZone) {
        super(timeZone);
    }

    public DateUtil.DateToISO8601CalendarFactory getISOBuiltInCalendar() {
        DateUtil.DateToISO8601CalendarFactory dateToISO8601CalendarFactory = this.dateToCalenderFieldsCalculator;
        if (dateToISO8601CalendarFactory != null) {
            return dateToISO8601CalendarFactory;
        }
        DateUtil.TrivialDateToISO8601CalendarFactory trivialDateToISO8601CalendarFactory = new DateUtil.TrivialDateToISO8601CalendarFactory();
        this.dateToCalenderFieldsCalculator = trivialDateToISO8601CalendarFactory;
        return trivialDateToISO8601CalendarFactory;
    }

    public DateUtil.CalendarFieldsToDateConverter getCalendarFieldsToDateCalculator() {
        DateUtil.CalendarFieldsToDateConverter calendarFieldsToDateConverter = this.calendarFieldsToDateConverter;
        if (calendarFieldsToDateConverter != null) {
            return calendarFieldsToDateConverter;
        }
        DateUtil.TrivialCalendarFieldsToDateConverter trivialCalendarFieldsToDateConverter = new DateUtil.TrivialCalendarFieldsToDateConverter();
        this.calendarFieldsToDateConverter = trivialCalendarFieldsToDateConverter;
        return trivialCalendarFieldsToDateConverter;
    }
}
