package org.mapstruct.ap.shaded.freemarker.core;

import java.util.Date;
import java.util.TimeZone;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil;

/* loaded from: classes3.dex */
abstract class ISOLikeTemplateDateFormat extends TemplateDateFormat {
    private static final String XS_LESS_THAN_SECONDS_ACCURACY_ERROR_MESSAGE = "Less than seconds accuracy isn't allowed by the XML Schema format";
    protected final int accuracy;
    protected final int dateType;
    private final ISOLikeTemplateDateFormatFactory factory;
    protected final Boolean forceUTC;
    protected final Boolean showZoneOffset;
    protected final TimeZone timeZone;
    protected final boolean zonelessInput;

    protected abstract String format(Date date, boolean z, boolean z2, boolean z3, int i, TimeZone timeZone, DateUtil.DateToISO8601CalendarFactory dateToISO8601CalendarFactory);

    protected abstract String getDateDescription();

    protected abstract String getDateTimeDescription();

    protected abstract String getTimeDescription();

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateDateFormat
    public final boolean isLocaleBound() {
        return false;
    }

    protected abstract boolean isXSMode();

    protected abstract Date parseDate(String str, TimeZone timeZone, DateUtil.CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateUtil.DateParseException;

    protected abstract Date parseDateTime(String str, TimeZone timeZone, DateUtil.CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateUtil.DateParseException;

    protected abstract Date parseTime(String str, TimeZone timeZone, DateUtil.CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateUtil.DateParseException;

    /* JADX WARN: Removed duplicated region for block: B:25:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00b4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0113 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public ISOLikeTemplateDateFormat(java.lang.String r17, int r18, int r19, boolean r20, java.util.TimeZone r21, org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormatFactory r22) throws org.mapstruct.ap.shaded.freemarker.core.UnknownDateTypeFormattingUnsupportedException, java.text.ParseException {
        /*
            Method dump skipped, instructions count: 349
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormat.<init>(java.lang.String, int, int, boolean, java.util.TimeZone, org.mapstruct.ap.shaded.freemarker.core.ISOLikeTemplateDateFormatFactory):void");
    }

    private void checkForceUTCNotSet(Boolean bool, int i) throws java.text.ParseException {
        if (bool != Boolean.FALSE) {
            throw new java.text.ParseException("The UTC usage option was already set earlier.", i);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateDateFormat
    public final String format(TemplateDateModel templateDateModel) throws TemplateModelException {
        boolean z;
        Date asDate = templateDateModel.getAsDate();
        int i = this.dateType;
        boolean zBooleanValue = false;
        boolean z2 = i != 1;
        boolean z3 = i != 2;
        Boolean bool = this.showZoneOffset;
        if (bool == null) {
            if (!this.zonelessInput) {
                z = true;
            }
            int i2 = this.accuracy;
            Boolean bool2 = this.forceUTC;
            return format(asDate, z2, z3, z, i2, (bool2 == null ? !bool2.booleanValue() : this.zonelessInput) ? this.timeZone : DateUtil.UTC, this.factory.getISOBuiltInCalendar());
        }
        zBooleanValue = bool.booleanValue();
        z = zBooleanValue;
        int i22 = this.accuracy;
        Boolean bool22 = this.forceUTC;
        return format(asDate, z2, z3, z, i22, (bool22 == null ? !bool22.booleanValue() : this.zonelessInput) ? this.timeZone : DateUtil.UTC, this.factory.getISOBuiltInCalendar());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateDateFormat
    public final Date parse(String str) throws java.text.ParseException {
        DateUtil.CalendarFieldsToDateConverter calendarFieldsToDateCalculator = this.factory.getCalendarFieldsToDateCalculator();
        TimeZone timeZone = this.forceUTC != Boolean.FALSE ? DateUtil.UTC : this.timeZone;
        int i = this.dateType;
        if (i == 2) {
            return parseDate(str, timeZone, calendarFieldsToDateCalculator);
        }
        if (i == 1) {
            return parseTime(str, timeZone, calendarFieldsToDateCalculator);
        }
        if (i == 3) {
            return parseDateTime(str, timeZone, calendarFieldsToDateCalculator);
        }
        throw new BugException(new StringBuffer("Unexpected date type: ").append(this.dateType).toString());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateDateFormat
    public final String getDescription() {
        int i = this.dateType;
        if (i == 1) {
            return getTimeDescription();
        }
        if (i != 2) {
            return i != 3 ? "<error: wrong format dateType>" : getDateTimeDescription();
        }
        return getDateDescription();
    }
}
