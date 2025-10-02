package org.mapstruct.ap.internal.model.common;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import org.mapstruct.ap.internal.util.JodaTimeConstants;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.XmlConstants;

/* loaded from: classes3.dex */
final class DateFormatValidatorFactory {
    private static final String FOR_PATTERN = "forPattern";
    private static final String JAVA_TIME_FORMAT_DATE_TIME_FORMATTER = "java.time.format.DateTimeFormatter";
    private static final String JAVA_UTIL_DATE = "java.util.Date";
    private static final String OF_PATTERN = "ofPattern";
    private static final String ORG_JODA_TIME_FORMAT_DATE_TIME_FORMAT = "org.joda.time.format.DateTimeFormat";

    private DateFormatValidatorFactory() {
    }

    public static DateFormatValidator forTypes(final Type type, final Type type2) {
        if (isJavaUtilDateSupposed(type, type2)) {
            return new SimpleDateFormatValidator();
        }
        if (isXmlGregorianCalendarSupposedToBeMapped(type, type2)) {
            return new SimpleDateFormatValidator();
        }
        if (isJava8DateTimeSupposed(type, type2)) {
            return new JavaDateTimeDateFormatValidator();
        }
        if (isJodaDateTimeSupposed(type, type2)) {
            return new JodaTimeDateFormatValidator();
        }
        return new DateFormatValidator() { // from class: org.mapstruct.ap.internal.model.common.DateFormatValidatorFactory.1
            @Override // org.mapstruct.ap.internal.model.common.DateFormatValidator
            public DateFormatValidationResult validate(String str) {
                return new DateFormatValidationResult(true, Message.GENERAL_UNSUPPORTED_DATE_FORMAT_CHECK, type, type2);
            }
        };
    }

    private static boolean isXmlGregorianCalendarSupposedToBeMapped(Type type, Type type2) {
        return typesEqualsOneOf(type, type2, XmlConstants.JAVAX_XML_DATATYPE_XMLGREGORIAN_CALENDAR);
    }

    private static boolean isJodaDateTimeSupposed(Type type, Type type2) {
        return typesEqualsOneOf(type, type2, JodaTimeConstants.LOCAL_DATE_FQN, JodaTimeConstants.LOCAL_TIME_FQN, JodaTimeConstants.LOCAL_DATE_TIME_FQN, JodaTimeConstants.DATE_TIME_FQN);
    }

    private static boolean isJava8DateTimeSupposed(Type type, Type type2) {
        return typesEqualsOneOf(type, type2, LocalDate.class.getCanonicalName(), LocalTime.class.getCanonicalName(), LocalDateTime.class.getCanonicalName(), ZonedDateTime.class.getCanonicalName());
    }

    private static boolean isJavaUtilDateSupposed(Type type, Type type2) {
        return JAVA_UTIL_DATE.equals(type.getFullyQualifiedName()) || JAVA_UTIL_DATE.equals(type2.getFullyQualifiedName());
    }

    private static boolean typesEqualsOneOf(Type type, Type type2, String... strArr) {
        for (String str : strArr) {
            if (str.equals(type.getFullyQualifiedName()) || str.equals(type2.getFullyQualifiedName())) {
                return true;
            }
        }
        return false;
    }

    private static class JavaDateTimeDateFormatValidator implements DateFormatValidator {
        private JavaDateTimeDateFormatValidator() {
        }

        @Override // org.mapstruct.ap.internal.model.common.DateFormatValidator
        public DateFormatValidationResult validate(String str) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
            try {
                Class<?> cls = Class.forName(DateFormatValidatorFactory.JAVA_TIME_FORMAT_DATE_TIME_FORMATTER);
                cls.getMethod(DateFormatValidatorFactory.OF_PATTERN, String.class).invoke(cls, str);
                return DateFormatValidatorFactory.validDateFormat(str);
            } catch (InvocationTargetException e) {
                return DateFormatValidatorFactory.invalidDateFormat(str, e.getCause());
            } catch (Exception e2) {
                return DateFormatValidatorFactory.invalidDateFormat(str, e2);
            }
        }
    }

    private static class JodaTimeDateFormatValidator implements DateFormatValidator {
        private JodaTimeDateFormatValidator() {
        }

        @Override // org.mapstruct.ap.internal.model.common.DateFormatValidator
        public DateFormatValidationResult validate(String str) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
            try {
                Class<?> cls = Class.forName("org.joda.time.format.DateTimeFormat");
                cls.getMethod(DateFormatValidatorFactory.FOR_PATTERN, String.class).invoke(cls, str);
                return DateFormatValidatorFactory.validDateFormat(str);
            } catch (ClassNotFoundException unused) {
                return DateFormatValidatorFactory.noJodaOnClassPath();
            } catch (InvocationTargetException e) {
                return DateFormatValidatorFactory.invalidDateFormat(str, e.getCause());
            } catch (Exception e2) {
                return DateFormatValidatorFactory.invalidDateFormat(str, e2);
            }
        }
    }

    private static class SimpleDateFormatValidator implements DateFormatValidator {
        private SimpleDateFormatValidator() {
        }

        @Override // org.mapstruct.ap.internal.model.common.DateFormatValidator
        public DateFormatValidationResult validate(String str) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
            try {
                Class.forName(SimpleDateFormat.class.getCanonicalName()).getConstructor(String.class).newInstance(str);
                return DateFormatValidatorFactory.validDateFormat(str);
            } catch (InvocationTargetException e) {
                return DateFormatValidatorFactory.invalidDateFormat(str, e.getCause());
            } catch (Exception e2) {
                return DateFormatValidatorFactory.invalidDateFormat(str, e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DateFormatValidationResult validDateFormat(String str) {
        return new DateFormatValidationResult(true, Message.GENERAL_VALID_DATE, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DateFormatValidationResult invalidDateFormat(String str, Throwable th) {
        return new DateFormatValidationResult(false, Message.GENERAL_INVALID_DATE, str, th.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DateFormatValidationResult noJodaOnClassPath() {
        return new DateFormatValidationResult(false, Message.GENERAL_JODA_NOT_ON_CLASSPATH, new Object[0]);
    }
}
