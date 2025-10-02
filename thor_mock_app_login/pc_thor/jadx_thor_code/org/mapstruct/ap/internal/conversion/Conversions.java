package org.mapstruct.ap.internal.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.JodaTimeConstants;

/* loaded from: classes3.dex */
public class Conversions {
    private final Map<Key, ConversionProvider> conversions = new HashMap();
    private final Type enumType;
    private final Type stringType;
    private final TypeFactory typeFactory;

    public Conversions(TypeFactory typeFactory) {
        this.typeFactory = typeFactory;
        this.enumType = typeFactory.getType(Enum.class);
        this.stringType = typeFactory.getType(String.class);
        registerNativeTypeConversion(Byte.TYPE, Byte.class);
        registerNativeTypeConversion(Byte.TYPE, Short.TYPE);
        registerNativeTypeConversion(Byte.TYPE, Short.class);
        registerNativeTypeConversion(Byte.TYPE, Integer.TYPE);
        registerNativeTypeConversion(Byte.TYPE, Integer.class);
        registerNativeTypeConversion(Byte.TYPE, Long.TYPE);
        registerNativeTypeConversion(Byte.TYPE, Long.class);
        registerNativeTypeConversion(Byte.TYPE, Float.TYPE);
        registerNativeTypeConversion(Byte.TYPE, Float.class);
        registerNativeTypeConversion(Byte.TYPE, Double.TYPE);
        registerNativeTypeConversion(Byte.TYPE, Double.class);
        registerNativeTypeConversion(Byte.class, Short.TYPE);
        registerNativeTypeConversion(Byte.class, Short.class);
        registerNativeTypeConversion(Byte.class, Integer.TYPE);
        registerNativeTypeConversion(Byte.class, Integer.class);
        registerNativeTypeConversion(Byte.class, Long.TYPE);
        registerNativeTypeConversion(Byte.class, Long.class);
        registerNativeTypeConversion(Byte.class, Float.TYPE);
        registerNativeTypeConversion(Byte.class, Float.class);
        registerNativeTypeConversion(Byte.class, Double.TYPE);
        registerNativeTypeConversion(Byte.class, Double.class);
        registerNativeTypeConversion(Short.TYPE, Short.class);
        registerNativeTypeConversion(Short.TYPE, Integer.TYPE);
        registerNativeTypeConversion(Short.TYPE, Integer.class);
        registerNativeTypeConversion(Short.TYPE, Long.TYPE);
        registerNativeTypeConversion(Short.TYPE, Long.class);
        registerNativeTypeConversion(Short.TYPE, Float.TYPE);
        registerNativeTypeConversion(Short.TYPE, Float.class);
        registerNativeTypeConversion(Short.TYPE, Double.TYPE);
        registerNativeTypeConversion(Short.TYPE, Double.class);
        registerNativeTypeConversion(Short.class, Integer.TYPE);
        registerNativeTypeConversion(Short.class, Integer.class);
        registerNativeTypeConversion(Short.class, Long.TYPE);
        registerNativeTypeConversion(Short.class, Long.class);
        registerNativeTypeConversion(Short.class, Float.TYPE);
        registerNativeTypeConversion(Short.class, Float.class);
        registerNativeTypeConversion(Short.class, Double.TYPE);
        registerNativeTypeConversion(Short.class, Double.class);
        registerNativeTypeConversion(Integer.TYPE, Integer.class);
        registerNativeTypeConversion(Integer.TYPE, Long.TYPE);
        registerNativeTypeConversion(Integer.TYPE, Long.class);
        registerNativeTypeConversion(Integer.TYPE, Float.TYPE);
        registerNativeTypeConversion(Integer.TYPE, Float.class);
        registerNativeTypeConversion(Integer.TYPE, Double.TYPE);
        registerNativeTypeConversion(Integer.TYPE, Double.class);
        registerNativeTypeConversion(Integer.class, Long.TYPE);
        registerNativeTypeConversion(Integer.class, Long.class);
        registerNativeTypeConversion(Integer.class, Float.TYPE);
        registerNativeTypeConversion(Integer.class, Float.class);
        registerNativeTypeConversion(Integer.class, Double.TYPE);
        registerNativeTypeConversion(Integer.class, Double.class);
        registerNativeTypeConversion(Long.TYPE, Long.class);
        registerNativeTypeConversion(Long.TYPE, Float.TYPE);
        registerNativeTypeConversion(Long.TYPE, Float.class);
        registerNativeTypeConversion(Long.TYPE, Double.TYPE);
        registerNativeTypeConversion(Long.TYPE, Double.class);
        registerNativeTypeConversion(Long.class, Float.TYPE);
        registerNativeTypeConversion(Long.class, Float.class);
        registerNativeTypeConversion(Long.class, Double.TYPE);
        registerNativeTypeConversion(Long.class, Double.class);
        registerNativeTypeConversion(Float.TYPE, Float.class);
        registerNativeTypeConversion(Float.TYPE, Double.TYPE);
        registerNativeTypeConversion(Float.TYPE, Double.class);
        registerNativeTypeConversion(Float.class, Double.TYPE);
        registerNativeTypeConversion(Float.class, Double.class);
        registerNativeTypeConversion(Double.TYPE, Double.class);
        registerNativeTypeConversion(Boolean.TYPE, Boolean.class);
        registerNativeTypeConversion(Character.TYPE, Character.class);
        registerBigIntegerConversion(Byte.TYPE);
        registerBigIntegerConversion(Byte.class);
        registerBigIntegerConversion(Short.TYPE);
        registerBigIntegerConversion(Short.class);
        registerBigIntegerConversion(Integer.TYPE);
        registerBigIntegerConversion(Integer.class);
        registerBigIntegerConversion(Long.TYPE);
        registerBigIntegerConversion(Long.class);
        registerBigIntegerConversion(Float.TYPE);
        registerBigIntegerConversion(Float.class);
        registerBigIntegerConversion(Double.TYPE);
        registerBigIntegerConversion(Double.class);
        registerBigDecimalConversion(Byte.TYPE);
        registerBigDecimalConversion(Byte.class);
        registerBigDecimalConversion(Short.TYPE);
        registerBigDecimalConversion(Short.class);
        registerBigDecimalConversion(Integer.TYPE);
        registerBigDecimalConversion(Integer.class);
        registerBigDecimalConversion(Long.TYPE);
        registerBigDecimalConversion(Long.class);
        registerBigDecimalConversion(Float.TYPE);
        registerBigDecimalConversion(Float.class);
        registerBigDecimalConversion(Double.TYPE);
        registerBigDecimalConversion(Double.class);
        registerToStringConversion(Byte.TYPE);
        registerToStringConversion(Byte.class);
        registerToStringConversion(Short.TYPE);
        registerToStringConversion(Short.class);
        registerToStringConversion(Integer.TYPE);
        registerToStringConversion(Integer.class);
        registerToStringConversion(Long.TYPE);
        registerToStringConversion(Long.class);
        registerToStringConversion(Float.TYPE);
        registerToStringConversion(Float.class);
        registerToStringConversion(Double.TYPE);
        registerToStringConversion(Double.class);
        registerToStringConversion(Boolean.TYPE);
        registerToStringConversion(Boolean.class);
        register(Character.TYPE, String.class, new CharToStringConversion());
        register(Character.class, String.class, new CharWrapperToStringConversion());
        register(BigInteger.class, String.class, new BigIntegerToStringConversion());
        register(BigDecimal.class, String.class, new BigDecimalToStringConversion());
        registerJodaConversions();
        registerJava8TimeConversions();
        register(Enum.class, String.class, new EnumStringConversion());
        register(Date.class, String.class, new DateToStringConversion());
        register(BigDecimal.class, BigInteger.class, new BigDecimalToBigIntegerConversion());
        registerJavaTimeSqlConversions();
        register(Currency.class, String.class, new CurrencyToStringConversion());
    }

    private void registerJodaConversions() {
        if (isJodaTimeAvailable()) {
            register(JodaTimeConstants.DATE_TIME_FQN, String.class, new JodaDateTimeToStringConversion());
            register(JodaTimeConstants.LOCAL_DATE_FQN, String.class, new JodaLocalDateToStringConversion());
            register(JodaTimeConstants.LOCAL_DATE_TIME_FQN, String.class, new JodaLocalDateTimeToStringConversion());
            register(JodaTimeConstants.LOCAL_TIME_FQN, String.class, new JodaLocalTimeToStringConversion());
            register(JodaTimeConstants.DATE_TIME_FQN, Date.class, new JodaTimeToDateConversion());
            register(JodaTimeConstants.LOCAL_DATE_FQN, Date.class, new JodaTimeToDateConversion());
            register(JodaTimeConstants.LOCAL_DATE_TIME_FQN, Date.class, new JodaTimeToDateConversion());
            register(JodaTimeConstants.DATE_TIME_FQN, Calendar.class, new JodaDateTimeToCalendarConversion());
        }
    }

    private void registerJava8TimeConversions() {
        register(ZonedDateTime.class, String.class, new JavaZonedDateTimeToStringConversion());
        register(LocalDate.class, String.class, new JavaLocalDateToStringConversion());
        register(LocalDateTime.class, String.class, new JavaLocalDateTimeToStringConversion());
        register(LocalTime.class, String.class, new JavaLocalTimeToStringConversion());
        register(Instant.class, String.class, new StaticParseToStringConversion());
        register(Period.class, String.class, new StaticParseToStringConversion());
        register(Duration.class, String.class, new StaticParseToStringConversion());
        register(ZonedDateTime.class, Date.class, new JavaZonedDateTimeToDateConversion());
        register(LocalDateTime.class, Date.class, new JavaLocalDateTimeToDateConversion());
        register(LocalDate.class, Date.class, new JavaLocalDateToDateConversion());
        register(Instant.class, Date.class, new JavaInstantToDateConversion());
    }

    private void registerJavaTimeSqlConversions() {
        if (isJavaSqlAvailable()) {
            register(LocalDate.class, java.sql.Date.class, new JavaLocalDateToSqlDateConversion());
            register(Date.class, Time.class, new DateToSqlTimeConversion());
            register(Date.class, java.sql.Date.class, new DateToSqlDateConversion());
            register(Date.class, Timestamp.class, new DateToSqlTimestampConversion());
        }
    }

    private boolean isJodaTimeAvailable() {
        return this.typeFactory.isTypeAvailable(JodaTimeConstants.DATE_TIME_FQN);
    }

    private boolean isJavaSqlAvailable() {
        return this.typeFactory.isTypeAvailable("java.sql.Date");
    }

    private void registerNativeTypeConversion(Class<?> cls, Class<?> cls2) {
        if (cls.isPrimitive() && cls2.isPrimitive()) {
            register(cls, cls2, new PrimitiveToPrimitiveConversion(cls));
            return;
        }
        if (cls.isPrimitive() && !cls2.isPrimitive()) {
            register(cls, cls2, new PrimitiveToWrapperConversion(cls, cls2));
        } else if (!cls.isPrimitive() && cls2.isPrimitive()) {
            register(cls, cls2, ReverseConversion.inverse(new PrimitiveToWrapperConversion(cls2, cls)));
        } else {
            register(cls, cls2, new WrapperToWrapperConversion(cls, cls2));
        }
    }

    private void registerToStringConversion(Class<?> cls) {
        if (cls.isPrimitive()) {
            register(cls, String.class, new PrimitiveToStringConversion(cls));
        } else {
            register(cls, String.class, new WrapperToStringConversion(cls));
        }
    }

    private void registerBigIntegerConversion(Class<?> cls) {
        if (cls.isPrimitive()) {
            register(BigInteger.class, cls, new BigIntegerToPrimitiveConversion(cls));
        } else {
            register(BigInteger.class, cls, new BigIntegerToWrapperConversion(cls));
        }
    }

    private void registerBigDecimalConversion(Class<?> cls) {
        if (cls.isPrimitive()) {
            register(BigDecimal.class, cls, new BigDecimalToPrimitiveConversion(cls));
        } else {
            register(BigDecimal.class, cls, new BigDecimalToWrapperConversion(cls));
        }
    }

    private void register(Class<?> cls, Class<?> cls2, ConversionProvider conversionProvider) {
        Type type = this.typeFactory.getType(cls);
        Type type2 = this.typeFactory.getType(cls2);
        this.conversions.put(new Key(type, type2), conversionProvider);
        this.conversions.put(new Key(type2, type), ReverseConversion.inverse(conversionProvider));
    }

    private void register(String str, Class<?> cls, ConversionProvider conversionProvider) {
        Type type = this.typeFactory.getType(str);
        Type type2 = this.typeFactory.getType(cls);
        this.conversions.put(new Key(type, type2), conversionProvider);
        this.conversions.put(new Key(type2, type), ReverseConversion.inverse(conversionProvider));
    }

    public ConversionProvider getConversion(Type type, Type type2) {
        if (type.isEnumType() && type2.equals(this.stringType)) {
            type = this.enumType;
        } else if (type2.isEnumType() && type.equals(this.stringType)) {
            type2 = this.enumType;
        }
        return this.conversions.get(new Key(type, type2));
    }

    private static class Key {
        private final Type sourceType;
        private final Type targetType;

        private Key(Type type, Type type2) {
            this.sourceType = type;
            this.targetType = type2;
        }

        public String toString() {
            return "Key [sourceType=" + this.sourceType + ", targetType=" + this.targetType + "]";
        }

        public int hashCode() {
            Type type = this.sourceType;
            int iHashCode = ((type == null ? 0 : type.hashCode()) + 31) * 31;
            Type type2 = this.targetType;
            return iHashCode + (type2 != null ? type2.hashCode() : 0);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Key key = (Key) obj;
            return Objects.equals(this.sourceType, key.sourceType) && Objects.equals(this.targetType, key.targetType);
        }
    }
}
