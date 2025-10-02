package org.mapstruct.ap.shaded.freemarker.template.utility;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.time.TimeZones;

/* loaded from: classes3.dex */
public class DateUtil {
    public static final int ACCURACY_HOURS = 4;
    public static final int ACCURACY_MILLISECONDS = 7;
    public static final int ACCURACY_MILLISECONDS_FORCED = 8;
    public static final int ACCURACY_MINUTES = 5;
    public static final int ACCURACY_SECONDS = 6;
    private static final String MSG_YEAR_0_NOT_ALLOWED = "Year 0 is not allowed in XML schema dates. BC 1 is -1, AD 1 is 1.";
    private static final String REGEX_ISO8601_BASIC_OPTIONAL_TIME_ZONE = "(Z|(?:[-+][0-9]{2}(?:[0-9]{2})?))?";
    private static final String REGEX_ISO8601_BASIC_TIME_BASE = "([0-9]{2})(?:([0-9]{2})(?:([0-9]{2})(?:[\\.,]([0-9]+))?)?)?";
    private static final String REGEX_ISO8601_BASIC_TIME_ZONE = "Z|(?:[-+][0-9]{2}(?:[0-9]{2})?)";
    private static final String REGEX_ISO8601_EXTENDED_OPTIONAL_TIME_ZONE = "(Z|(?:[-+][0-9]{2}(?::[0-9]{2})?))?";
    private static final String REGEX_ISO8601_EXTENDED_TIME_BASE = "([0-9]{2})(?::([0-9]{2})(?::([0-9]{2})(?:[\\.,]([0-9]+))?)?)?";
    private static final String REGEX_ISO8601_EXTENDED_TIME_ZONE = "Z|(?:[-+][0-9]{2}(?::[0-9]{2})?)";
    private static final String REGEX_XS_DATE_BASE = "(-?[0-9]+)-([0-9]{2})-([0-9]{2})";
    private static final String REGEX_XS_OPTIONAL_TIME_ZONE = "(Z|(?:[-+][0-9]{2}:[0-9]{2}))?";
    private static final String REGEX_XS_TIME_BASE = "([0-9]{2}):([0-9]{2}):([0-9]{2})(?:\\.([0-9]+))?";
    public static final TimeZone UTC = TimeZone.getTimeZone("UTC");
    private static final Pattern PATTERN_XS_DATE = Pattern.compile("(-?[0-9]+)-([0-9]{2})-([0-9]{2})(Z|(?:[-+][0-9]{2}:[0-9]{2}))?");
    private static final String REGEX_ISO8601_BASIC_DATE_BASE = "(-?[0-9]{4,}?)([0-9]{2})([0-9]{2})";
    private static final Pattern PATTERN_ISO8601_BASIC_DATE = Pattern.compile(REGEX_ISO8601_BASIC_DATE_BASE);
    private static final String REGEX_ISO8601_EXTENDED_DATE_BASE = "(-?[0-9]{4,})-([0-9]{2})-([0-9]{2})";
    private static final Pattern PATTERN_ISO8601_EXTENDED_DATE = Pattern.compile(REGEX_ISO8601_EXTENDED_DATE_BASE);
    private static final Pattern PATTERN_XS_TIME = Pattern.compile("([0-9]{2}):([0-9]{2}):([0-9]{2})(?:\\.([0-9]+))?(Z|(?:[-+][0-9]{2}:[0-9]{2}))?");
    private static final Pattern PATTERN_ISO8601_BASIC_TIME = Pattern.compile("([0-9]{2})(?:([0-9]{2})(?:([0-9]{2})(?:[\\.,]([0-9]+))?)?)?(Z|(?:[-+][0-9]{2}(?:[0-9]{2})?))?");
    private static final Pattern PATTERN_ISO8601_EXTENDED_TIME = Pattern.compile("([0-9]{2})(?::([0-9]{2})(?::([0-9]{2})(?:[\\.,]([0-9]+))?)?)?(Z|(?:[-+][0-9]{2}(?::[0-9]{2})?))?");
    private static final Pattern PATTERN_XS_DATE_TIME = Pattern.compile("(-?[0-9]+)-([0-9]{2})-([0-9]{2})T([0-9]{2}):([0-9]{2}):([0-9]{2})(?:\\.([0-9]+))?(Z|(?:[-+][0-9]{2}:[0-9]{2}))?");
    private static final Pattern PATTERN_ISO8601_BASIC_DATE_TIME = Pattern.compile("(-?[0-9]{4,}?)([0-9]{2})([0-9]{2})T([0-9]{2})(?:([0-9]{2})(?:([0-9]{2})(?:[\\.,]([0-9]+))?)?)?(Z|(?:[-+][0-9]{2}(?:[0-9]{2})?))?");
    private static final Pattern PATTERN_ISO8601_EXTENDED_DATE_TIME = Pattern.compile("(-?[0-9]{4,})-([0-9]{2})-([0-9]{2})T([0-9]{2})(?::([0-9]{2})(?::([0-9]{2})(?:[\\.,]([0-9]+))?)?)?(Z|(?:[-+][0-9]{2}(?::[0-9]{2})?))?");
    private static final String REGEX_XS_TIME_ZONE = "Z|(?:[-+][0-9]{2}:[0-9]{2})";
    private static final Pattern PATTERN_XS_TIME_ZONE = Pattern.compile(REGEX_XS_TIME_ZONE);

    public interface CalendarFieldsToDateConverter {
        Date calculate(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z, TimeZone timeZone);
    }

    public interface DateToISO8601CalendarFactory {
        GregorianCalendar get(TimeZone timeZone, Date date);
    }

    private DateUtil() {
    }

    public static TimeZone getTimeZone(String str) throws UnrecognizedTimeZoneException {
        if (isGMTish(str)) {
            if (str.equalsIgnoreCase("UTC")) {
                return UTC;
            }
            return TimeZone.getTimeZone(str);
        }
        TimeZone timeZone = TimeZone.getTimeZone(str);
        if (isGMTish(timeZone.getID())) {
            throw new UnrecognizedTimeZoneException(str);
        }
        return timeZone;
    }

    private static boolean isGMTish(String str) {
        if (str.length() < 3) {
            return false;
        }
        char cCharAt = str.charAt(0);
        char cCharAt2 = str.charAt(1);
        char cCharAt3 = str.charAt(2);
        if (((cCharAt != 'G' && cCharAt != 'g') || ((cCharAt2 != 'M' && cCharAt2 != 'm') || (cCharAt3 != 'T' && cCharAt3 != 't'))) && (((cCharAt != 'U' && cCharAt != 'u') || ((cCharAt2 != 'T' && cCharAt2 != 't') || (cCharAt3 != 'C' && cCharAt3 != 'c'))) && ((cCharAt != 'U' && cCharAt != 'u') || ((cCharAt2 != 'T' && cCharAt2 != 't') || cCharAt3 != '1')))) {
            return false;
        }
        if (str.length() == 3) {
            return true;
        }
        String strSubstring = str.substring(3);
        return strSubstring.startsWith("+") ? strSubstring.equals("+0") || strSubstring.equals("+00") || strSubstring.equals("+00:00") : strSubstring.equals("-0") || strSubstring.equals("-00") || strSubstring.equals("-00:00");
    }

    public static String dateToISO8601String(Date date, boolean z, boolean z2, boolean z3, int i, TimeZone timeZone, DateToISO8601CalendarFactory dateToISO8601CalendarFactory) {
        return dateToString(date, z, z2, z3, i, timeZone, false, dateToISO8601CalendarFactory);
    }

    public static String dateToXSString(Date date, boolean z, boolean z2, boolean z3, int i, TimeZone timeZone, DateToISO8601CalendarFactory dateToISO8601CalendarFactory) {
        return dateToString(date, z, z2, z3, i, timeZone, true, dateToISO8601CalendarFactory);
    }

    private static String dateToString(Date date, boolean z, boolean z2, boolean z3, int i, TimeZone timeZone, boolean z4, DateToISO8601CalendarFactory dateToISO8601CalendarFactory) {
        Date date2;
        TimeZone timeZone2;
        int i2;
        int iAppend00;
        int i3;
        if (!z4 && !z2 && z3) {
            throw new IllegalArgumentException("ISO 8601:2004 doesn't specify any formats where the offset is shown but the time isn't.");
        }
        if (timeZone == null) {
            timeZone2 = UTC;
            date2 = date;
        } else {
            date2 = date;
            timeZone2 = timeZone;
        }
        GregorianCalendar gregorianCalendar = dateToISO8601CalendarFactory.get(timeZone2, date2);
        if (z2) {
            i2 = !z ? 18 : 29;
        } else {
            i2 = (z4 ? 6 : 0) + 10;
        }
        char[] cArr = new char[i2];
        boolean z5 = true;
        if (z) {
            int i4 = gregorianCalendar.get(1);
            if (i4 > 0 && gregorianCalendar.get(0) == 0) {
                i4 = (-i4) + (!z4 ? 1 : 0);
            }
            int i5 = 4;
            if (i4 >= 0 && i4 < 9999) {
                cArr[0] = (char) ((i4 / 1000) + 48);
                cArr[1] = (char) (((i4 % 1000) / 100) + 48);
                cArr[2] = (char) (((i4 % 100) / 10) + 48);
                cArr[3] = (char) ((i4 % 10) + 48);
            } else {
                String strValueOf = String.valueOf(i4);
                char[] cArr2 = new char[(i2 - 4) + strValueOf.length()];
                int i6 = 0;
                i5 = 0;
                while (i6 < strValueOf.length()) {
                    cArr2[i5] = strValueOf.charAt(i6);
                    i6++;
                    i5++;
                }
                cArr = cArr2;
            }
            cArr[i5] = '-';
            int iAppend002 = append00(cArr, i5 + 1, gregorianCalendar.get(2) + 1);
            cArr[iAppend002] = '-';
            iAppend00 = append00(cArr, iAppend002 + 1, gregorianCalendar.get(5));
            if (z2) {
                cArr[iAppend00] = 'T';
                iAppend00++;
            }
        } else {
            iAppend00 = 0;
        }
        if (z2) {
            iAppend00 = append00(cArr, iAppend00, gregorianCalendar.get(11));
            if (i >= 5) {
                cArr[iAppend00] = ':';
                iAppend00 = append00(cArr, iAppend00 + 1, gregorianCalendar.get(12));
                if (i >= 6) {
                    cArr[iAppend00] = ':';
                    iAppend00 = append00(cArr, iAppend00 + 1, gregorianCalendar.get(13));
                    if (i >= 7) {
                        int i7 = gregorianCalendar.get(14);
                        int i8 = i != 8 ? 0 : 3;
                        if (i7 != 0 || i8 != 0) {
                            if (i7 > 999) {
                                throw new RuntimeException("Calendar.MILLISECOND > 999");
                            }
                            int i9 = iAppend00 + 1;
                            cArr[iAppend00] = ClassUtils.PACKAGE_SEPARATOR_CHAR;
                            while (true) {
                                iAppend00 = i9 + 1;
                                cArr[i9] = (char) ((i7 / 100) + 48);
                                i8--;
                                i7 = (i7 % 100) * 10;
                                if (i7 == 0 && i8 <= 0) {
                                    break;
                                }
                                i9 = iAppend00;
                            }
                        }
                    }
                }
            }
        }
        if (z3) {
            if (timeZone2 == UTC) {
                i3 = iAppend00 + 1;
                cArr[iAppend00] = 'Z';
            } else {
                int offset = timeZone2.getOffset(date.getTime());
                if (offset < 0) {
                    offset = -offset;
                    z5 = false;
                }
                int i10 = offset / 1000;
                int i11 = i10 % 60;
                int i12 = i10 / 60;
                int i13 = i12 % 60;
                int i14 = i12 / 60;
                if (i11 == 0 && i13 == 0 && i14 == 0) {
                    i3 = iAppend00 + 1;
                    cArr[iAppend00] = 'Z';
                } else {
                    int i15 = iAppend00 + 1;
                    cArr[iAppend00] = z5 ? '+' : '-';
                    int iAppend003 = append00(cArr, i15, i14);
                    cArr[iAppend003] = ':';
                    iAppend00 = append00(cArr, iAppend003 + 1, i13);
                    if (i11 != 0) {
                        cArr[iAppend00] = ':';
                        iAppend00 = append00(cArr, iAppend00 + 1, i11);
                    }
                }
            }
            iAppend00 = i3;
        }
        return new String(cArr, 0, iAppend00);
    }

    private static int append00(char[] cArr, int i, int i2) {
        int i3 = i + 1;
        cArr[i] = (char) ((i2 / 10) + 48);
        int i4 = i3 + 1;
        cArr[i3] = (char) ((i2 % 10) + 48);
        return i4;
    }

    public static Date parseXSDate(String str, TimeZone timeZone, CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateParseException {
        Pattern pattern = PATTERN_XS_DATE;
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            throw new DateParseException(new StringBuffer("The value didn't match the expected pattern: ").append(pattern).toString());
        }
        return parseDate_parseMatcher(matcher, timeZone, true, calendarFieldsToDateConverter);
    }

    public static Date parseISO8601Date(String str, TimeZone timeZone, CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateParseException {
        Pattern pattern = PATTERN_ISO8601_EXTENDED_DATE;
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            Pattern pattern2 = PATTERN_ISO8601_BASIC_DATE;
            Matcher matcher2 = pattern2.matcher(str);
            if (!matcher2.matches()) {
                throw new DateParseException(new StringBuffer("The value didn't match the expected pattern: ").append(pattern).append(" or ").append(pattern2).toString());
            }
            matcher = matcher2;
        }
        return parseDate_parseMatcher(matcher, timeZone, false, calendarFieldsToDateConverter);
    }

    private static Date parseDate_parseMatcher(Matcher matcher, TimeZone timeZone, boolean z, CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateParseException {
        int i;
        int i2;
        NullArgumentException.check("defaultTZ", timeZone);
        try {
            int iGroupToInt = groupToInt(matcher.group(1), "year", Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (iGroupToInt <= 0) {
                int i3 = (-iGroupToInt) + (!z ? 1 : 0);
                if (i3 == 0) {
                    throw new DateParseException(MSG_YEAR_0_NOT_ALLOWED);
                }
                i = i3;
                i2 = 0;
            } else {
                i = iGroupToInt;
                i2 = 1;
            }
            int iGroupToInt2 = groupToInt(matcher.group(2), "month", 1, 12) - 1;
            int iGroupToInt3 = groupToInt(matcher.group(3), "day-of-month", 1, 31);
            if (z) {
                timeZone = parseMatchingTimeZone(matcher.group(4), timeZone);
            }
            return calendarFieldsToDateConverter.calculate(i2, i, iGroupToInt2, iGroupToInt3, 0, 0, 0, 0, false, timeZone);
        } catch (IllegalArgumentException unused) {
            throw new DateParseException("Date calculation faliure. Probably the date is formally correct, but refers to an unexistent date (like February 30).");
        }
    }

    public static Date parseXSTime(String str, TimeZone timeZone, CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateParseException {
        Pattern pattern = PATTERN_XS_TIME;
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            throw new DateParseException(new StringBuffer("The value didn't match the expected pattern: ").append(pattern).toString());
        }
        return parseTime_parseMatcher(matcher, timeZone, calendarFieldsToDateConverter);
    }

    public static Date parseISO8601Time(String str, TimeZone timeZone, CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateParseException {
        Pattern pattern = PATTERN_ISO8601_EXTENDED_TIME;
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            Pattern pattern2 = PATTERN_ISO8601_BASIC_TIME;
            Matcher matcher2 = pattern2.matcher(str);
            if (!matcher2.matches()) {
                throw new DateParseException(new StringBuffer("The value didn't match the expected pattern: ").append(pattern).append(" or ").append(pattern2).toString());
            }
            matcher = matcher2;
        }
        return parseTime_parseMatcher(matcher, timeZone, calendarFieldsToDateConverter);
    }

    private static Date parseTime_parseMatcher(Matcher matcher, TimeZone timeZone, CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateParseException {
        int i;
        boolean z;
        int i2;
        NullArgumentException.check("defaultTZ", timeZone);
        try {
            int iGroupToInt = groupToInt(matcher.group(1), "hour-of-day", 0, 24);
            if (iGroupToInt == 24) {
                z = true;
                i = 0;
            } else {
                i = iGroupToInt;
                z = false;
            }
            String strGroup = matcher.group(2);
            int iGroupToInt2 = strGroup != null ? groupToInt(strGroup, "minute", 0, 59) : 0;
            String strGroup2 = matcher.group(3);
            int iGroupToInt3 = strGroup2 != null ? groupToInt(strGroup2, "second", 0, 60) : 0;
            int iGroupToMillisecond = groupToMillisecond(matcher.group(4));
            TimeZone matchingTimeZone = parseMatchingTimeZone(matcher.group(5), timeZone);
            if (!z) {
                i2 = 1;
            } else {
                if (iGroupToInt2 != 0 || iGroupToInt3 != 0 || iGroupToMillisecond != 0) {
                    throw new DateParseException("Hour 24 is only allowed in the case of midnight.");
                }
                i2 = 2;
            }
            return calendarFieldsToDateConverter.calculate(1, 1970, 0, i2, i, iGroupToInt2, iGroupToInt3, iGroupToMillisecond, false, matchingTimeZone);
        } catch (IllegalArgumentException unused) {
            throw new DateParseException("Unexpected time calculation faliure.");
        }
    }

    public static Date parseXSDateTime(String str, TimeZone timeZone, CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateParseException {
        Pattern pattern = PATTERN_XS_DATE_TIME;
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            throw new DateParseException(new StringBuffer("The value didn't match the expected pattern: ").append(pattern).toString());
        }
        return parseDateTime_parseMatcher(matcher, timeZone, true, calendarFieldsToDateConverter);
    }

    public static Date parseISO8601DateTime(String str, TimeZone timeZone, CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateParseException {
        Pattern pattern = PATTERN_ISO8601_EXTENDED_DATE_TIME;
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            Pattern pattern2 = PATTERN_ISO8601_BASIC_DATE_TIME;
            Matcher matcher2 = pattern2.matcher(str);
            if (!matcher2.matches()) {
                throw new DateParseException(new StringBuffer("The value (").append(str).append(") didn't match the expected pattern: ").append(pattern).append(" or ").append(pattern2).toString());
            }
            matcher = matcher2;
        }
        return parseDateTime_parseMatcher(matcher, timeZone, false, calendarFieldsToDateConverter);
    }

    private static Date parseDateTime_parseMatcher(Matcher matcher, TimeZone timeZone, boolean z, CalendarFieldsToDateConverter calendarFieldsToDateConverter) throws DateParseException {
        int i;
        int i2;
        boolean z2;
        NullArgumentException.check("defaultTZ", timeZone);
        try {
            int iGroupToInt = groupToInt(matcher.group(1), "year", Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (iGroupToInt <= 0) {
                int i3 = (-iGroupToInt) + (!z ? 1 : 0);
                if (i3 == 0) {
                    throw new DateParseException(MSG_YEAR_0_NOT_ALLOWED);
                }
                i = i3;
                i2 = 0;
            } else {
                i = iGroupToInt;
                i2 = 1;
            }
            int iGroupToInt2 = groupToInt(matcher.group(2), "month", 1, 12) - 1;
            int iGroupToInt3 = groupToInt(matcher.group(3), "day-of-month", 1, 31);
            int iGroupToInt4 = groupToInt(matcher.group(4), "hour-of-day", 0, 24);
            if (iGroupToInt4 == 24) {
                z2 = true;
                iGroupToInt4 = 0;
            } else {
                z2 = false;
            }
            String strGroup = matcher.group(5);
            int iGroupToInt5 = strGroup != null ? groupToInt(strGroup, "minute", 0, 59) : 0;
            String strGroup2 = matcher.group(6);
            int iGroupToInt6 = strGroup2 != null ? groupToInt(strGroup2, "second", 0, 60) : 0;
            int iGroupToMillisecond = groupToMillisecond(matcher.group(7));
            TimeZone matchingTimeZone = parseMatchingTimeZone(matcher.group(8), timeZone);
            if (z2 && (iGroupToInt5 != 0 || iGroupToInt6 != 0 || iGroupToMillisecond != 0)) {
                throw new DateParseException("Hour 24 is only allowed in the case of midnight.");
            }
            return calendarFieldsToDateConverter.calculate(i2, i, iGroupToInt2, iGroupToInt3, iGroupToInt4, iGroupToInt5, iGroupToInt6, iGroupToMillisecond, z2, matchingTimeZone);
        } catch (IllegalArgumentException unused) {
            throw new DateParseException("Date-time calculation faliure. Probably the date-time is formally correct, but refers to an unexistent date-time (like February 30).");
        }
    }

    public static TimeZone parseXSTimeZone(String str) throws DateParseException {
        Pattern pattern = PATTERN_XS_TIME_ZONE;
        if (!pattern.matcher(str).matches()) {
            throw new DateParseException(new StringBuffer("The time zone offset didn't match the expected pattern: ").append(pattern).toString());
        }
        return parseMatchingTimeZone(str, null);
    }

    private static int groupToInt(String str, String str2, int i, int i2) throws DateParseException, NumberFormatException {
        if (str == null) {
            throw new DateParseException(new StringBuffer("The ").append(str2).append(" part is missing.").toString());
        }
        int i3 = str.startsWith("-") ? 1 : 0;
        int i4 = i3;
        while (i3 < str.length() - 1 && str.charAt(i3) == '0') {
            i3++;
        }
        if (i3 != 0) {
            str = str.substring(i3);
        }
        try {
            int i5 = Integer.parseInt(str);
            if (i4 != 0) {
                i5 = -i5;
            }
            if (i5 < i) {
                throw new DateParseException(new StringBuffer().append("The ").append(str2).append(" part ").append("must be at least ").append(i).append(".").toString());
            }
            if (i5 <= i2) {
                return i5;
            }
            throw new DateParseException(new StringBuffer().append("The ").append(str2).append(" part ").append("can't be more than ").append(i2).append(".").toString());
        } catch (NumberFormatException unused) {
            throw new DateParseException(new StringBuffer("The ").append(str2).append(" part is a malformed integer.").toString());
        }
    }

    private static TimeZone parseMatchingTimeZone(String str, TimeZone timeZone) throws DateParseException, NumberFormatException {
        if (str == null) {
            return timeZone;
        }
        if (str.equals("Z")) {
            return UTC;
        }
        StringBuffer stringBuffer = new StringBuffer(9);
        stringBuffer.append(TimeZones.GMT_ID);
        stringBuffer.append(str.charAt(0));
        String strSubstring = str.substring(1, 3);
        groupToInt(strSubstring, "offset-hours", 0, 23);
        stringBuffer.append(strSubstring);
        if (str.length() > 3) {
            int i = str.charAt(3) == ':' ? 4 : 3;
            String strSubstring2 = str.substring(i, i + 2);
            groupToInt(strSubstring2, "offset-minutes", 0, 59);
            stringBuffer.append(':');
            stringBuffer.append(strSubstring2);
        }
        return TimeZone.getTimeZone(stringBuffer.toString());
    }

    private static int groupToMillisecond(String str) throws DateParseException, NumberFormatException {
        if (str == null) {
            return 0;
        }
        if (str.length() > 3) {
            str = str.substring(0, 3);
        }
        int iGroupToInt = groupToInt(str, "partial-seconds", 0, Integer.MAX_VALUE);
        return str.length() == 1 ? iGroupToInt * 100 : str.length() == 2 ? iGroupToInt * 10 : iGroupToInt;
    }

    public static final class TrivialDateToISO8601CalendarFactory implements DateToISO8601CalendarFactory {
        private GregorianCalendar calendar;
        private TimeZone lastlySetTimeZone;

        @Override // org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil.DateToISO8601CalendarFactory
        public GregorianCalendar get(TimeZone timeZone, Date date) {
            GregorianCalendar gregorianCalendar = this.calendar;
            if (gregorianCalendar == null) {
                GregorianCalendar gregorianCalendar2 = new GregorianCalendar(timeZone, Locale.US);
                this.calendar = gregorianCalendar2;
                gregorianCalendar2.setGregorianChange(new Date(Long.MIN_VALUE));
            } else if (this.lastlySetTimeZone != timeZone) {
                gregorianCalendar.setTimeZone(timeZone);
                this.lastlySetTimeZone = timeZone;
            }
            this.calendar.setTime(date);
            return this.calendar;
        }
    }

    public static final class TrivialCalendarFieldsToDateConverter implements CalendarFieldsToDateConverter {
        private GregorianCalendar calendar;
        private TimeZone lastlySetTimeZone;

        @Override // org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil.CalendarFieldsToDateConverter
        public Date calculate(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z, TimeZone timeZone) {
            GregorianCalendar gregorianCalendar = this.calendar;
            if (gregorianCalendar == null) {
                GregorianCalendar gregorianCalendar2 = new GregorianCalendar(timeZone, Locale.US);
                this.calendar = gregorianCalendar2;
                gregorianCalendar2.setLenient(false);
                this.calendar.setGregorianChange(new Date(Long.MIN_VALUE));
            } else if (this.lastlySetTimeZone != timeZone) {
                gregorianCalendar.setTimeZone(timeZone);
                this.lastlySetTimeZone = timeZone;
            }
            this.calendar.set(0, i);
            this.calendar.set(1, i2);
            this.calendar.set(2, i3);
            this.calendar.set(5, i4);
            this.calendar.set(11, i5);
            this.calendar.set(12, i6);
            this.calendar.set(13, i7);
            this.calendar.set(14, i8);
            if (z) {
                this.calendar.add(5, 1);
            }
            return this.calendar.getTime();
        }
    }

    public static final class DateParseException extends ParseException {
        public DateParseException(String str) {
            super(str, 0);
        }
    }
}
