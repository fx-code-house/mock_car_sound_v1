package org.mapstruct.ap.internal.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Locale;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.util.JodaTimeConstants;

/* loaded from: classes3.dex */
public final class ConversionUtils {
    private ConversionUtils() {
    }

    private static String typeReferenceName(ConversionContext conversionContext, String str) {
        return conversionContext.getTypeFactory().getType(str).createReferenceName();
    }

    private static String typeReferenceName(ConversionContext conversionContext, Class<?> cls) {
        return conversionContext.getTypeFactory().getType(cls).createReferenceName();
    }

    public static String bigDecimal(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) BigDecimal.class);
    }

    public static String bigInteger(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) BigInteger.class);
    }

    public static String locale(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) Locale.class);
    }

    public static String currency(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) Currency.class);
    }

    public static String sqlDate(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) Date.class);
    }

    public static String time(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) Time.class);
    }

    public static String timestamp(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) Timestamp.class);
    }

    public static String decimalFormat(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) DecimalFormat.class);
    }

    public static String simpleDateFormat(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) SimpleDateFormat.class);
    }

    public static String date(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) java.util.Date.class);
    }

    public static String zoneOffset(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) ZoneOffset.class);
    }

    public static String zoneId(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) ZoneId.class);
    }

    public static String localDateTime(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) LocalDateTime.class);
    }

    public static String zonedDateTime(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) ZonedDateTime.class);
    }

    public static String dateTimeFormatter(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, (Class<?>) DateTimeFormatter.class);
    }

    public static String dateTimeFormat(ConversionContext conversionContext) {
        return typeReferenceName(conversionContext, JodaTimeConstants.DATE_TIME_FORMAT_FQN);
    }
}
