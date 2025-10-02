package org.mapstruct.ap.shaded.freemarker.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
class JavaTemplateDateFormatFactory extends TemplateDateFormatFactory {
    private static final Map JAVA_DATE_FORMATS = new HashMap();
    private Map[] formatCache;
    private final Locale locale;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateDateFormatFactory
    public boolean isLocaleBound() {
        return true;
    }

    public JavaTemplateDateFormatFactory(TimeZone timeZone, Locale locale) {
        super(timeZone);
        this.locale = locale;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateDateFormatFactory
    public TemplateDateFormat get(int i, boolean z, String str) throws UnknownDateTypeFormattingUnsupportedException, TemplateModelException, java.text.ParseException {
        Map[] mapArr = this.formatCache;
        if (mapArr == null) {
            mapArr = new Map[4];
            this.formatCache = mapArr;
        }
        Map map = mapArr[i];
        if (map == null) {
            map = new HashMap();
            mapArr[i] = map;
        }
        TemplateDateFormat templateDateFormat = (TemplateDateFormat) map.get(str);
        if (templateDateFormat != null) {
            return templateDateFormat;
        }
        JavaTemplateDateFormat javaTemplateDateFormat = new JavaTemplateDateFormat(getJavaDateFormat(i, str));
        map.put(str, javaTemplateDateFormat);
        return javaTemplateDateFormat;
    }

    private DateFormat getJavaDateFormat(int i, String str) throws UnknownDateTypeFormattingUnsupportedException, java.text.ParseException {
        DateFormat simpleDateFormat;
        DateFormatKey dateFormatKey = new DateFormatKey(i, str, this.locale, getTimeZone());
        Map map = JAVA_DATE_FORMATS;
        synchronized (map) {
            simpleDateFormat = (DateFormat) map.get(dateFormatKey);
            if (simpleDateFormat == null) {
                StringTokenizer stringTokenizer = new StringTokenizer(str, "_");
                int dateStyleToken = stringTokenizer.hasMoreTokens() ? parseDateStyleToken(stringTokenizer.nextToken()) : 2;
                if (dateStyleToken != -1) {
                    if (i == 0) {
                        throw new UnknownDateTypeFormattingUnsupportedException();
                    }
                    if (i == 1) {
                        simpleDateFormat = DateFormat.getTimeInstance(dateStyleToken, dateFormatKey.locale);
                    } else if (i == 2) {
                        simpleDateFormat = DateFormat.getDateInstance(dateStyleToken, dateFormatKey.locale);
                    } else if (i == 3) {
                        int dateStyleToken2 = stringTokenizer.hasMoreTokens() ? parseDateStyleToken(stringTokenizer.nextToken()) : dateStyleToken;
                        if (dateStyleToken2 != -1) {
                            simpleDateFormat = DateFormat.getDateTimeInstance(dateStyleToken, dateStyleToken2, dateFormatKey.locale);
                        }
                    }
                }
                if (simpleDateFormat == null) {
                    try {
                        simpleDateFormat = new SimpleDateFormat(str, dateFormatKey.locale);
                    } catch (IllegalArgumentException e) {
                        String message = e.getMessage();
                        if (message == null) {
                            message = "Illegal SimpleDateFormat pattern";
                        }
                        throw new java.text.ParseException(message, 0);
                    }
                }
                simpleDateFormat.setTimeZone(dateFormatKey.timeZone);
                map.put(dateFormatKey, simpleDateFormat);
            }
        }
        return (DateFormat) simpleDateFormat.clone();
    }

    private static final class DateFormatKey {
        private final int dateType;
        private final Locale locale;
        private final String pattern;
        private final TimeZone timeZone;

        DateFormatKey(int i, String str, Locale locale, TimeZone timeZone) {
            this.dateType = i;
            this.pattern = str;
            this.locale = locale;
            this.timeZone = timeZone;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof DateFormatKey)) {
                return false;
            }
            DateFormatKey dateFormatKey = (DateFormatKey) obj;
            return this.dateType == dateFormatKey.dateType && dateFormatKey.pattern.equals(this.pattern) && dateFormatKey.locale.equals(this.locale) && dateFormatKey.timeZone.equals(this.timeZone);
        }

        public int hashCode() {
            return ((this.dateType ^ this.pattern.hashCode()) ^ this.locale.hashCode()) ^ this.timeZone.hashCode();
        }
    }

    private int parseDateStyleToken(String str) {
        if ("short".equals(str)) {
            return 3;
        }
        if ("medium".equals(str)) {
            return 2;
        }
        if ("long".equals(str)) {
            return 1;
        }
        return "full".equals(str) ? 0 : -1;
    }
}
