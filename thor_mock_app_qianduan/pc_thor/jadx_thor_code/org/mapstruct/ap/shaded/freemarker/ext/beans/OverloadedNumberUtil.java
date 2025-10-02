package org.mapstruct.ap.shaded.freemarker.ext.beans;

import com.google.firebase.FirebaseError;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;

/* loaded from: classes3.dex */
class OverloadedNumberUtil {
    static final int BIG_MANTISSA_LOSS_PRICE = 40000;
    private static final double HIGHEST_BELOW_ONE = 0.999999d;
    private static final double LOWEST_ABOVE_ZERO = 1.0E-6d;
    private static final long MAX_DOUBLE_OR_LONG = 9007199254740992L;
    private static final int MAX_DOUBLE_OR_LONG_LOG_2 = 53;
    private static final int MAX_FLOAT_OR_INT = 16777216;
    private static final int MAX_FLOAT_OR_INT_LOG_2 = 24;
    private static final long MIN_DOUBLE_OR_LONG = -9007199254740992L;
    private static final int MIN_FLOAT_OR_INT = -16777216;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort;
    static /* synthetic */ Class class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte;
    static /* synthetic */ Class class$java$lang$Byte;
    static /* synthetic */ Class class$java$lang$Double;
    static /* synthetic */ Class class$java$lang$Float;
    static /* synthetic */ Class class$java$lang$Integer;
    static /* synthetic */ Class class$java$lang$Long;
    static /* synthetic */ Class class$java$lang$Short;
    static /* synthetic */ Class class$java$math$BigDecimal;
    static /* synthetic */ Class class$java$math$BigInteger;

    interface BigDecimalSource {
        BigDecimal bigDecimalValue();
    }

    interface BigIntegerSource {
        BigInteger bigIntegerValue();
    }

    interface ByteSource {
        Byte byteValue();
    }

    interface DoubleSource {
        Double doubleValue();
    }

    interface FloatSource {
        Float floatValue();
    }

    interface IntegerSource {
        Integer integerValue();
    }

    interface LongSource {
        Long longValue();
    }

    interface ShortSource {
        Short shortValue();
    }

    private OverloadedNumberUtil() {
    }

    /* JADX WARN: Removed duplicated region for block: B:126:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0270  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x027a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static java.lang.Number addFallbackType(java.lang.Number r25, int r26) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 845
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.addFallbackType(java.lang.Number, int):java.lang.Number");
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static abstract class NumberWithFallbackType extends Number implements Comparable {
        protected abstract Number getSourceNumber();

        NumberWithFallbackType() {
        }

        @Override // java.lang.Number
        public int intValue() {
            return getSourceNumber().intValue();
        }

        @Override // java.lang.Number
        public long longValue() {
            return getSourceNumber().longValue();
        }

        @Override // java.lang.Number
        public float floatValue() {
            return getSourceNumber().floatValue();
        }

        @Override // java.lang.Number
        public double doubleValue() {
            return getSourceNumber().doubleValue();
        }

        @Override // java.lang.Number
        public byte byteValue() {
            return getSourceNumber().byteValue();
        }

        @Override // java.lang.Number
        public short shortValue() {
            return getSourceNumber().shortValue();
        }

        public int hashCode() {
            return getSourceNumber().hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return getSourceNumber().equals(((NumberWithFallbackType) obj).getSourceNumber());
        }

        public String toString() {
            return getSourceNumber().toString();
        }

        @Override // java.lang.Comparable
        public int compareTo(Object obj) {
            Object sourceNumber = getSourceNumber();
            if (sourceNumber instanceof Comparable) {
                return ((Comparable) sourceNumber).compareTo(obj);
            }
            throw new ClassCastException(new StringBuffer().append(sourceNumber.getClass().getName()).append(" is not Comparable.").toString());
        }
    }

    static final class IntegerBigDecimal extends NumberWithFallbackType {
        private final BigDecimal n;

        IntegerBigDecimal(BigDecimal bigDecimal) {
            this.n = bigDecimal;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType
        protected Number getSourceNumber() {
            return this.n;
        }

        public BigInteger bigIntegerValue() {
            return this.n.toBigInteger();
        }
    }

    static abstract class LongOrSmallerInteger extends NumberWithFallbackType {
        private final Long n;

        protected LongOrSmallerInteger(Long l) {
            this.n = l;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType
        protected Number getSourceNumber() {
            return this.n;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public long longValue() {
            return this.n.longValue();
        }
    }

    static class LongOrByte extends LongOrSmallerInteger {
        private final byte w;

        LongOrByte(Long l, byte b) {
            super(l);
            this.w = b;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public byte byteValue() {
            return this.w;
        }
    }

    static class LongOrShort extends LongOrSmallerInteger {
        private final short w;

        LongOrShort(Long l, short s) {
            super(l);
            this.w = s;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public short shortValue() {
            return this.w;
        }
    }

    static class LongOrInteger extends LongOrSmallerInteger {
        private final int w;

        LongOrInteger(Long l, int i) {
            super(l);
            this.w = i;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public int intValue() {
            return this.w;
        }
    }

    static abstract class IntegerOrSmallerInteger extends NumberWithFallbackType {
        private final Integer n;

        protected IntegerOrSmallerInteger(Integer num) {
            this.n = num;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType
        protected Number getSourceNumber() {
            return this.n;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public int intValue() {
            return this.n.intValue();
        }
    }

    static class IntegerOrByte extends IntegerOrSmallerInteger {
        private final byte w;

        IntegerOrByte(Integer num, byte b) {
            super(num);
            this.w = b;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public byte byteValue() {
            return this.w;
        }
    }

    static class IntegerOrShort extends IntegerOrSmallerInteger {
        private final short w;

        IntegerOrShort(Integer num, short s) {
            super(num);
            this.w = s;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public short shortValue() {
            return this.w;
        }
    }

    static class ShortOrByte extends NumberWithFallbackType {
        private final Short n;
        private final byte w;

        protected ShortOrByte(Short sh, byte b) {
            this.n = sh;
            this.w = b;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType
        protected Number getSourceNumber() {
            return this.n;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public short shortValue() {
            return this.n.shortValue();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public byte byteValue() {
            return this.w;
        }
    }

    static abstract class DoubleOrWholeNumber extends NumberWithFallbackType {
        private final Double n;

        protected DoubleOrWholeNumber(Double d) {
            this.n = d;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType
        protected Number getSourceNumber() {
            return this.n;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public double doubleValue() {
            return this.n.doubleValue();
        }
    }

    static final class DoubleOrByte extends DoubleOrWholeNumber {
        private final byte w;

        DoubleOrByte(Double d, byte b) {
            super(d);
            this.w = b;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public byte byteValue() {
            return this.w;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public short shortValue() {
            return this.w;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public int intValue() {
            return this.w;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public long longValue() {
            return this.w;
        }
    }

    static final class DoubleOrShort extends DoubleOrWholeNumber {
        private final short w;

        DoubleOrShort(Double d, short s) {
            super(d);
            this.w = s;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public short shortValue() {
            return this.w;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public int intValue() {
            return this.w;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public long longValue() {
            return this.w;
        }
    }

    static final class DoubleOrIntegerOrFloat extends DoubleOrWholeNumber {
        private final int w;

        DoubleOrIntegerOrFloat(Double d, int i) {
            super(d);
            this.w = i;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public int intValue() {
            return this.w;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public long longValue() {
            return this.w;
        }
    }

    static final class DoubleOrInteger extends DoubleOrWholeNumber {
        private final int w;

        DoubleOrInteger(Double d, int i) {
            super(d);
            this.w = i;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public int intValue() {
            return this.w;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public long longValue() {
            return this.w;
        }
    }

    static final class DoubleOrLong extends DoubleOrWholeNumber {
        private final long w;

        DoubleOrLong(Double d, long j) {
            super(d);
            this.w = j;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public long longValue() {
            return this.w;
        }
    }

    static final class DoubleOrFloat extends NumberWithFallbackType {
        private final Double n;

        DoubleOrFloat(Double d) {
            this.n = d;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public float floatValue() {
            return this.n.floatValue();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public double doubleValue() {
            return this.n.doubleValue();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType
        protected Number getSourceNumber() {
            return this.n;
        }
    }

    static abstract class FloatOrWholeNumber extends NumberWithFallbackType {
        private final Float n;

        FloatOrWholeNumber(Float f) {
            this.n = f;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType
        protected Number getSourceNumber() {
            return this.n;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public float floatValue() {
            return this.n.floatValue();
        }
    }

    static final class FloatOrByte extends FloatOrWholeNumber {
        private final byte w;

        FloatOrByte(Float f, byte b) {
            super(f);
            this.w = b;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public byte byteValue() {
            return this.w;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public short shortValue() {
            return this.w;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public int intValue() {
            return this.w;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public long longValue() {
            return this.w;
        }
    }

    static final class FloatOrShort extends FloatOrWholeNumber {
        private final short w;

        FloatOrShort(Float f, short s) {
            super(f);
            this.w = s;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public short shortValue() {
            return this.w;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public int intValue() {
            return this.w;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public long longValue() {
            return this.w;
        }
    }

    static final class FloatOrInteger extends FloatOrWholeNumber {
        private final int w;

        FloatOrInteger(Float f, int i) {
            super(f);
            this.w = i;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public int intValue() {
            return this.w;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public long longValue() {
            return this.w;
        }
    }

    static abstract class BigIntegerOrPrimitive extends NumberWithFallbackType {
        protected final BigInteger n;

        BigIntegerOrPrimitive(BigInteger bigInteger) {
            this.n = bigInteger;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType
        protected Number getSourceNumber() {
            return this.n;
        }
    }

    static final class BigIntegerOrByte extends BigIntegerOrPrimitive {
        BigIntegerOrByte(BigInteger bigInteger) {
            super(bigInteger);
        }
    }

    static final class BigIntegerOrShort extends BigIntegerOrPrimitive {
        BigIntegerOrShort(BigInteger bigInteger) {
            super(bigInteger);
        }
    }

    static final class BigIntegerOrInteger extends BigIntegerOrPrimitive {
        BigIntegerOrInteger(BigInteger bigInteger) {
            super(bigInteger);
        }
    }

    static final class BigIntegerOrLong extends BigIntegerOrPrimitive {
        BigIntegerOrLong(BigInteger bigInteger) {
            super(bigInteger);
        }
    }

    static abstract class BigIntegerOrFPPrimitive extends BigIntegerOrPrimitive {
        BigIntegerOrFPPrimitive(BigInteger bigInteger) {
            super(bigInteger);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public float floatValue() {
            return this.n.longValue();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil.NumberWithFallbackType, java.lang.Number
        public double doubleValue() {
            return this.n.longValue();
        }
    }

    static final class BigIntegerOrFloat extends BigIntegerOrFPPrimitive {
        BigIntegerOrFloat(BigInteger bigInteger) {
            super(bigInteger);
        }
    }

    static final class BigIntegerOrDouble extends BigIntegerOrFPPrimitive {
        BigIntegerOrDouble(BigInteger bigInteger) {
            super(bigInteger);
        }
    }

    static int getArgumentConversionPrice(Class cls, Class cls2) throws Throwable {
        if (cls2 == cls) {
            return 0;
        }
        Class clsClass$ = class$java$lang$Integer;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.Integer");
            class$java$lang$Integer = clsClass$;
        }
        if (cls2 == clsClass$) {
            Class clsClass$2 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal;
            if (clsClass$2 == null) {
                clsClass$2 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerBigDecimal");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal = clsClass$2;
            }
            if (cls == clsClass$2) {
                return 31003;
            }
            Class clsClass$3 = class$java$math$BigDecimal;
            if (clsClass$3 == null) {
                clsClass$3 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$3;
            }
            if (cls == clsClass$3) {
                return 41003;
            }
            Class clsClass$4 = class$java$lang$Long;
            if (clsClass$4 == null) {
                clsClass$4 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$4;
            }
            if (cls == clsClass$4) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$5 = class$java$lang$Double;
            if (clsClass$5 == null) {
                clsClass$5 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$5;
            }
            if (cls == clsClass$5) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$6 = class$java$lang$Float;
            if (clsClass$6 == null) {
                clsClass$6 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$6;
            }
            if (cls == clsClass$6) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$7 = class$java$lang$Byte;
            if (clsClass$7 == null) {
                clsClass$7 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$7;
            }
            if (cls == clsClass$7) {
                return 10003;
            }
            Class clsClass$8 = class$java$math$BigInteger;
            if (clsClass$8 == null) {
                clsClass$8 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$8;
            }
            if (cls == clsClass$8) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$9 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger;
            if (clsClass$9 == null) {
                clsClass$9 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger = clsClass$9;
            }
            if (cls == clsClass$9) {
                return 21003;
            }
            Class clsClass$10 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat;
            if (clsClass$10 == null) {
                clsClass$10 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat = clsClass$10;
            }
            if (cls == clsClass$10) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$11 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat;
            if (clsClass$11 == null) {
                clsClass$11 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat = clsClass$11;
            }
            if (cls == clsClass$11) {
                return 22003;
            }
            Class clsClass$12 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger;
            if (clsClass$12 == null) {
                clsClass$12 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger = clsClass$12;
            }
            if (cls == clsClass$12) {
                return 22003;
            }
            Class clsClass$13 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong;
            if (clsClass$13 == null) {
                clsClass$13 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong = clsClass$13;
            }
            if (cls == clsClass$13) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$14 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte;
            if (clsClass$14 == null) {
                clsClass$14 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte = clsClass$14;
            }
            if (cls == clsClass$14) {
                return 0;
            }
            Class clsClass$15 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte;
            if (clsClass$15 == null) {
                clsClass$15 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte = clsClass$15;
            }
            if (cls == clsClass$15) {
                return 22003;
            }
            Class clsClass$16 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte;
            if (clsClass$16 == null) {
                clsClass$16 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte = clsClass$16;
            }
            if (cls == clsClass$16) {
                return 21003;
            }
            Class clsClass$17 = class$java$lang$Short;
            if (clsClass$17 == null) {
                clsClass$17 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$17;
            }
            if (cls == clsClass$17) {
                return 10003;
            }
            Class clsClass$18 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort;
            if (clsClass$18 == null) {
                clsClass$18 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort = clsClass$18;
            }
            if (cls == clsClass$18) {
                return 21003;
            }
            Class clsClass$19 = class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte;
            if (clsClass$19 == null) {
                clsClass$19 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$ShortOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte = clsClass$19;
            }
            if (cls == clsClass$19) {
                return 10003;
            }
            Class clsClass$20 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger;
            if (clsClass$20 == null) {
                clsClass$20 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger = clsClass$20;
            }
            if (cls == clsClass$20) {
                return 21003;
            }
            Class clsClass$21 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte;
            if (clsClass$21 == null) {
                clsClass$21 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte = clsClass$21;
            }
            if (cls == clsClass$21) {
                return 21003;
            }
            Class clsClass$22 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort;
            if (clsClass$22 == null) {
                clsClass$22 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort = clsClass$22;
            }
            if (cls == clsClass$22) {
                return 21003;
            }
            Class clsClass$23 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger;
            if (clsClass$23 == null) {
                clsClass$23 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger = clsClass$23;
            }
            if (cls == clsClass$23) {
                return 16003;
            }
            Class clsClass$24 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong;
            if (clsClass$24 == null) {
                clsClass$24 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong = clsClass$24;
            }
            if (cls == clsClass$24) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$25 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble;
            if (clsClass$25 == null) {
                clsClass$25 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrDouble");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble = clsClass$25;
            }
            if (cls == clsClass$25) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$26 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat;
            if (clsClass$26 == null) {
                clsClass$26 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat = clsClass$26;
            }
            if (cls == clsClass$26) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$27 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte;
            if (clsClass$27 == null) {
                clsClass$27 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte = clsClass$27;
            }
            if (cls == clsClass$27) {
                return 16003;
            }
            Class clsClass$28 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort;
            if (clsClass$28 == null) {
                clsClass$28 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort = clsClass$28;
            }
            if (cls == clsClass$28) {
                return 0;
            }
            Class clsClass$29 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort;
            if (clsClass$29 == null) {
                clsClass$29 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort = clsClass$29;
            }
            if (cls == clsClass$29) {
                return 22003;
            }
            Class clsClass$30 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort;
            if (clsClass$30 == null) {
                clsClass$30 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort = clsClass$30;
            }
            return cls == clsClass$30 ? 16003 : Integer.MAX_VALUE;
        }
        Class clsClass$31 = class$java$lang$Long;
        if (clsClass$31 == null) {
            clsClass$31 = class$("java.lang.Long");
            class$java$lang$Long = clsClass$31;
        }
        if (cls2 == clsClass$31) {
            Class clsClass$32 = class$java$lang$Integer;
            if (clsClass$32 == null) {
                clsClass$32 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$32;
            }
            if (cls == clsClass$32) {
                return 10004;
            }
            Class clsClass$33 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal;
            if (clsClass$33 == null) {
                clsClass$33 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerBigDecimal");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal = clsClass$33;
            }
            if (cls == clsClass$33) {
                return 31004;
            }
            Class clsClass$34 = class$java$math$BigDecimal;
            if (clsClass$34 == null) {
                clsClass$34 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$34;
            }
            if (cls == clsClass$34) {
                return 41004;
            }
            Class clsClass$35 = class$java$lang$Double;
            if (clsClass$35 == null) {
                clsClass$35 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$35;
            }
            if (cls == clsClass$35) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$36 = class$java$lang$Float;
            if (clsClass$36 == null) {
                clsClass$36 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$36;
            }
            if (cls == clsClass$36) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$37 = class$java$lang$Byte;
            if (clsClass$37 == null) {
                clsClass$37 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$37;
            }
            if (cls == clsClass$37) {
                return 10004;
            }
            Class clsClass$38 = class$java$math$BigInteger;
            if (clsClass$38 == null) {
                clsClass$38 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$38;
            }
            if (cls == clsClass$38) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$39 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger;
            if (clsClass$39 == null) {
                clsClass$39 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger = clsClass$39;
            }
            if (cls == clsClass$39) {
                return 0;
            }
            Class clsClass$40 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat;
            if (clsClass$40 == null) {
                clsClass$40 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat = clsClass$40;
            }
            if (cls == clsClass$40) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$41 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat;
            if (clsClass$41 == null) {
                clsClass$41 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat = clsClass$41;
            }
            if (cls == clsClass$41) {
                return 21004;
            }
            Class clsClass$42 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger;
            if (clsClass$42 == null) {
                clsClass$42 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger = clsClass$42;
            }
            if (cls == clsClass$42) {
                return 21004;
            }
            Class clsClass$43 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong;
            if (clsClass$43 == null) {
                clsClass$43 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong = clsClass$43;
            }
            if (cls == clsClass$43) {
                return 21004;
            }
            Class clsClass$44 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte;
            if (clsClass$44 == null) {
                clsClass$44 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte = clsClass$44;
            }
            if (cls == clsClass$44) {
                return 10004;
            }
            Class clsClass$45 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte;
            if (clsClass$45 == null) {
                clsClass$45 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte = clsClass$45;
            }
            if (cls == clsClass$45) {
                return 21004;
            }
            Class clsClass$46 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte;
            if (clsClass$46 == null) {
                clsClass$46 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte = clsClass$46;
            }
            if (cls == clsClass$46) {
                return 0;
            }
            Class clsClass$47 = class$java$lang$Short;
            if (clsClass$47 == null) {
                clsClass$47 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$47;
            }
            if (cls == clsClass$47) {
                return 10004;
            }
            Class clsClass$48 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort;
            if (clsClass$48 == null) {
                clsClass$48 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort = clsClass$48;
            }
            if (cls == clsClass$48) {
                return 0;
            }
            Class clsClass$49 = class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte;
            if (clsClass$49 == null) {
                clsClass$49 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$ShortOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte = clsClass$49;
            }
            if (cls == clsClass$49) {
                return 10004;
            }
            Class clsClass$50 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger;
            if (clsClass$50 == null) {
                clsClass$50 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger = clsClass$50;
            }
            if (cls == clsClass$50) {
                return 21004;
            }
            Class clsClass$51 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte;
            if (clsClass$51 == null) {
                clsClass$51 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte = clsClass$51;
            }
            if (cls == clsClass$51) {
                return 21004;
            }
            Class clsClass$52 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort;
            if (clsClass$52 == null) {
                clsClass$52 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort = clsClass$52;
            }
            if (cls == clsClass$52) {
                return 21004;
            }
            Class clsClass$53 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger;
            if (clsClass$53 == null) {
                clsClass$53 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger = clsClass$53;
            }
            if (cls == clsClass$53) {
                return 15004;
            }
            Class clsClass$54 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong;
            if (clsClass$54 == null) {
                clsClass$54 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong = clsClass$54;
            }
            if (cls == clsClass$54) {
                return 15004;
            }
            Class clsClass$55 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble;
            if (clsClass$55 == null) {
                clsClass$55 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrDouble");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble = clsClass$55;
            }
            if (cls == clsClass$55) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$56 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat;
            if (clsClass$56 == null) {
                clsClass$56 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat = clsClass$56;
            }
            if (cls == clsClass$56) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$57 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte;
            if (clsClass$57 == null) {
                clsClass$57 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte = clsClass$57;
            }
            if (cls == clsClass$57) {
                return 15004;
            }
            Class clsClass$58 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort;
            if (clsClass$58 == null) {
                clsClass$58 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort = clsClass$58;
            }
            if (cls == clsClass$58) {
                return 10004;
            }
            Class clsClass$59 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort;
            if (clsClass$59 == null) {
                clsClass$59 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort = clsClass$59;
            }
            if (cls == clsClass$59) {
                return 21004;
            }
            Class clsClass$60 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort;
            if (clsClass$60 == null) {
                clsClass$60 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort = clsClass$60;
            }
            return cls == clsClass$60 ? 15004 : Integer.MAX_VALUE;
        }
        Class clsClass$61 = class$java$lang$Double;
        if (clsClass$61 == null) {
            clsClass$61 = class$("java.lang.Double");
            class$java$lang$Double = clsClass$61;
        }
        if (cls2 == clsClass$61) {
            Class clsClass$62 = class$java$lang$Integer;
            if (clsClass$62 == null) {
                clsClass$62 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$62;
            }
            if (cls == clsClass$62) {
                return 20007;
            }
            Class clsClass$63 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal;
            if (clsClass$63 == null) {
                clsClass$63 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerBigDecimal");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal = clsClass$63;
            }
            if (cls == clsClass$63) {
                return 32007;
            }
            Class clsClass$64 = class$java$math$BigDecimal;
            if (clsClass$64 == null) {
                clsClass$64 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$64;
            }
            if (cls == clsClass$64) {
                return 32007;
            }
            Class clsClass$65 = class$java$lang$Long;
            if (clsClass$65 == null) {
                clsClass$65 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$65;
            }
            if (cls == clsClass$65) {
                return 30007;
            }
            Class clsClass$66 = class$java$lang$Float;
            if (clsClass$66 == null) {
                clsClass$66 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$66;
            }
            if (cls == clsClass$66) {
                return 10007;
            }
            Class clsClass$67 = class$java$lang$Byte;
            if (clsClass$67 == null) {
                clsClass$67 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$67;
            }
            if (cls == clsClass$67) {
                return 20007;
            }
            Class clsClass$68 = class$java$math$BigInteger;
            if (clsClass$68 == null) {
                clsClass$68 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$68;
            }
            if (cls == clsClass$68) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$69 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger;
            if (clsClass$69 == null) {
                clsClass$69 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger = clsClass$69;
            }
            if (cls == clsClass$69) {
                return 21007;
            }
            Class clsClass$70 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat;
            if (clsClass$70 == null) {
                clsClass$70 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat = clsClass$70;
            }
            if (cls == clsClass$70) {
                return 0;
            }
            Class clsClass$71 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat;
            if (clsClass$71 == null) {
                clsClass$71 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat = clsClass$71;
            }
            if (cls == clsClass$71) {
                return 0;
            }
            Class clsClass$72 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger;
            if (clsClass$72 == null) {
                clsClass$72 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger = clsClass$72;
            }
            if (cls == clsClass$72) {
                return 0;
            }
            Class clsClass$73 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong;
            if (clsClass$73 == null) {
                clsClass$73 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong = clsClass$73;
            }
            if (cls == clsClass$73) {
                return 0;
            }
            Class clsClass$74 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte;
            if (clsClass$74 == null) {
                clsClass$74 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte = clsClass$74;
            }
            if (cls == clsClass$74) {
                return 20007;
            }
            Class clsClass$75 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte;
            if (clsClass$75 == null) {
                clsClass$75 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte = clsClass$75;
            }
            if (cls == clsClass$75) {
                return 0;
            }
            Class clsClass$76 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte;
            if (clsClass$76 == null) {
                clsClass$76 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte = clsClass$76;
            }
            if (cls == clsClass$76) {
                return 21007;
            }
            Class clsClass$77 = class$java$lang$Short;
            if (clsClass$77 == null) {
                clsClass$77 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$77;
            }
            if (cls == clsClass$77) {
                return 20007;
            }
            Class clsClass$78 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort;
            if (clsClass$78 == null) {
                clsClass$78 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort = clsClass$78;
            }
            if (cls == clsClass$78) {
                return 21007;
            }
            Class clsClass$79 = class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte;
            if (clsClass$79 == null) {
                clsClass$79 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$ShortOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte = clsClass$79;
            }
            if (cls == clsClass$79) {
                return 20007;
            }
            Class clsClass$80 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger;
            if (clsClass$80 == null) {
                clsClass$80 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger = clsClass$80;
            }
            if (cls == clsClass$80) {
                return 10007;
            }
            Class clsClass$81 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte;
            if (clsClass$81 == null) {
                clsClass$81 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte = clsClass$81;
            }
            if (cls == clsClass$81) {
                return 10007;
            }
            Class clsClass$82 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort;
            if (clsClass$82 == null) {
                clsClass$82 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort = clsClass$82;
            }
            if (cls == clsClass$82) {
                return 10007;
            }
            Class clsClass$83 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger;
            if (clsClass$83 == null) {
                clsClass$83 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger = clsClass$83;
            }
            if (cls == clsClass$83) {
                return 20007;
            }
            Class clsClass$84 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong;
            if (clsClass$84 == null) {
                clsClass$84 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong = clsClass$84;
            }
            if (cls == clsClass$84) {
                return 30007;
            }
            Class clsClass$85 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble;
            if (clsClass$85 == null) {
                clsClass$85 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrDouble");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble = clsClass$85;
            }
            if (cls == clsClass$85) {
                return 20007;
            }
            Class clsClass$86 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat;
            if (clsClass$86 == null) {
                clsClass$86 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat = clsClass$86;
            }
            if (cls == clsClass$86) {
                return 20007;
            }
            Class clsClass$87 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte;
            if (clsClass$87 == null) {
                clsClass$87 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte = clsClass$87;
            }
            if (cls == clsClass$87) {
                return 20007;
            }
            Class clsClass$88 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort;
            if (clsClass$88 == null) {
                clsClass$88 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort = clsClass$88;
            }
            if (cls == clsClass$88) {
                return 20007;
            }
            Class clsClass$89 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort;
            if (clsClass$89 == null) {
                clsClass$89 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort = clsClass$89;
            }
            if (cls == clsClass$89) {
                return 0;
            }
            Class clsClass$90 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort;
            if (clsClass$90 == null) {
                clsClass$90 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort = clsClass$90;
            }
            return cls == clsClass$90 ? 20007 : Integer.MAX_VALUE;
        }
        Class clsClass$91 = class$java$lang$Float;
        if (clsClass$91 == null) {
            clsClass$91 = class$("java.lang.Float");
            class$java$lang$Float = clsClass$91;
        }
        if (cls2 == clsClass$91) {
            Class clsClass$92 = class$java$lang$Integer;
            if (clsClass$92 == null) {
                clsClass$92 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$92;
            }
            if (cls == clsClass$92) {
                return 30006;
            }
            Class clsClass$93 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal;
            if (clsClass$93 == null) {
                clsClass$93 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerBigDecimal");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal = clsClass$93;
            }
            if (cls == clsClass$93) {
                return 33006;
            }
            Class clsClass$94 = class$java$math$BigDecimal;
            if (clsClass$94 == null) {
                clsClass$94 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$94;
            }
            if (cls == clsClass$94) {
                return 33006;
            }
            Class clsClass$95 = class$java$lang$Long;
            if (clsClass$95 == null) {
                clsClass$95 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$95;
            }
            if (cls == clsClass$95) {
                return 40006;
            }
            Class clsClass$96 = class$java$lang$Double;
            if (clsClass$96 == null) {
                clsClass$96 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$96;
            }
            if (cls == clsClass$96) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$97 = class$java$lang$Byte;
            if (clsClass$97 == null) {
                clsClass$97 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$97;
            }
            if (cls == clsClass$97) {
                return 20006;
            }
            Class clsClass$98 = class$java$math$BigInteger;
            if (clsClass$98 == null) {
                clsClass$98 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$98;
            }
            if (cls == clsClass$98) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$99 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger;
            if (clsClass$99 == null) {
                clsClass$99 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger = clsClass$99;
            }
            if (cls == clsClass$99) {
                return 30006;
            }
            Class clsClass$100 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat;
            if (clsClass$100 == null) {
                clsClass$100 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat = clsClass$100;
            }
            if (cls == clsClass$100) {
                return 30006;
            }
            Class clsClass$101 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat;
            if (clsClass$101 == null) {
                clsClass$101 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat = clsClass$101;
            }
            if (cls == clsClass$101) {
                return 23006;
            }
            Class clsClass$102 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger;
            if (clsClass$102 == null) {
                clsClass$102 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger = clsClass$102;
            }
            if (cls == clsClass$102) {
                return 30006;
            }
            Class clsClass$103 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong;
            if (clsClass$103 == null) {
                clsClass$103 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong = clsClass$103;
            }
            if (cls == clsClass$103) {
                return 40006;
            }
            Class clsClass$104 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte;
            if (clsClass$104 == null) {
                clsClass$104 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte = clsClass$104;
            }
            if (cls == clsClass$104) {
                return 24006;
            }
            Class clsClass$105 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte;
            if (clsClass$105 == null) {
                clsClass$105 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte = clsClass$105;
            }
            if (cls == clsClass$105) {
                return 23006;
            }
            Class clsClass$106 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte;
            if (clsClass$106 == null) {
                clsClass$106 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte = clsClass$106;
            }
            if (cls == clsClass$106) {
                return 24006;
            }
            Class clsClass$107 = class$java$lang$Short;
            if (clsClass$107 == null) {
                clsClass$107 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$107;
            }
            if (cls == clsClass$107) {
                return 20006;
            }
            Class clsClass$108 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort;
            if (clsClass$108 == null) {
                clsClass$108 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort = clsClass$108;
            }
            if (cls == clsClass$108) {
                return 24006;
            }
            Class clsClass$109 = class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte;
            if (clsClass$109 == null) {
                clsClass$109 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$ShortOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte = clsClass$109;
            }
            if (cls == clsClass$109) {
                return 20006;
            }
            Class clsClass$110 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger;
            if (clsClass$110 == null) {
                clsClass$110 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger = clsClass$110;
            }
            if (cls == clsClass$110) {
                return 0;
            }
            Class clsClass$111 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte;
            if (clsClass$111 == null) {
                clsClass$111 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte = clsClass$111;
            }
            if (cls == clsClass$111) {
                return 0;
            }
            Class clsClass$112 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort;
            if (clsClass$112 == null) {
                clsClass$112 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort = clsClass$112;
            }
            if (cls == clsClass$112) {
                return 0;
            }
            Class clsClass$113 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger;
            if (clsClass$113 == null) {
                clsClass$113 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger = clsClass$113;
            }
            if (cls == clsClass$113) {
                return 30006;
            }
            Class clsClass$114 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong;
            if (clsClass$114 == null) {
                clsClass$114 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong = clsClass$114;
            }
            if (cls == clsClass$114) {
                return 40006;
            }
            Class clsClass$115 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble;
            if (clsClass$115 == null) {
                clsClass$115 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrDouble");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble = clsClass$115;
            }
            if (cls == clsClass$115) {
                return 40006;
            }
            Class clsClass$116 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat;
            if (clsClass$116 == null) {
                clsClass$116 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat = clsClass$116;
            }
            if (cls == clsClass$116) {
                return 24006;
            }
            Class clsClass$117 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte;
            if (clsClass$117 == null) {
                clsClass$117 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte = clsClass$117;
            }
            if (cls == clsClass$117) {
                return 24006;
            }
            Class clsClass$118 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort;
            if (clsClass$118 == null) {
                clsClass$118 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort = clsClass$118;
            }
            if (cls == clsClass$118) {
                return 24006;
            }
            Class clsClass$119 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort;
            if (clsClass$119 == null) {
                clsClass$119 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort = clsClass$119;
            }
            if (cls == clsClass$119) {
                return 23006;
            }
            Class clsClass$120 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort;
            if (clsClass$120 == null) {
                clsClass$120 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort = clsClass$120;
            }
            return cls == clsClass$120 ? 24006 : Integer.MAX_VALUE;
        }
        Class clsClass$121 = class$java$lang$Byte;
        if (clsClass$121 == null) {
            clsClass$121 = class$("java.lang.Byte");
            class$java$lang$Byte = clsClass$121;
        }
        if (cls2 == clsClass$121) {
            Class clsClass$122 = class$java$lang$Integer;
            if (clsClass$122 == null) {
                clsClass$122 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$122;
            }
            if (cls == clsClass$122) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$123 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal;
            if (clsClass$123 == null) {
                clsClass$123 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerBigDecimal");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal = clsClass$123;
            }
            if (cls == clsClass$123) {
                return 35001;
            }
            Class clsClass$124 = class$java$math$BigDecimal;
            if (clsClass$124 == null) {
                clsClass$124 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$124;
            }
            if (cls == clsClass$124) {
                return 45001;
            }
            Class clsClass$125 = class$java$lang$Long;
            if (clsClass$125 == null) {
                clsClass$125 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$125;
            }
            if (cls == clsClass$125) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$126 = class$java$lang$Double;
            if (clsClass$126 == null) {
                clsClass$126 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$126;
            }
            if (cls == clsClass$126) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$127 = class$java$lang$Float;
            if (clsClass$127 == null) {
                clsClass$127 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$127;
            }
            if (cls == clsClass$127) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$128 = class$java$math$BigInteger;
            if (clsClass$128 == null) {
                clsClass$128 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$128;
            }
            if (cls == clsClass$128) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$129 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger;
            if (clsClass$129 == null) {
                clsClass$129 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger = clsClass$129;
            }
            if (cls == clsClass$129) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$130 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat;
            if (clsClass$130 == null) {
                clsClass$130 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat = clsClass$130;
            }
            if (cls == clsClass$130) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$131 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat;
            if (clsClass$131 == null) {
                clsClass$131 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat = clsClass$131;
            }
            if (cls == clsClass$131) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$132 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger;
            if (clsClass$132 == null) {
                clsClass$132 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger = clsClass$132;
            }
            if (cls == clsClass$132) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$133 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong;
            if (clsClass$133 == null) {
                clsClass$133 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong = clsClass$133;
            }
            if (cls == clsClass$133) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$134 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte;
            if (clsClass$134 == null) {
                clsClass$134 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte = clsClass$134;
            }
            if (cls == clsClass$134) {
                return 22001;
            }
            Class clsClass$135 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte;
            if (clsClass$135 == null) {
                clsClass$135 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte = clsClass$135;
            }
            if (cls == clsClass$135) {
                return 25001;
            }
            Class clsClass$136 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte;
            if (clsClass$136 == null) {
                clsClass$136 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte = clsClass$136;
            }
            if (cls == clsClass$136) {
                return 23001;
            }
            Class clsClass$137 = class$java$lang$Short;
            if (clsClass$137 == null) {
                clsClass$137 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$137;
            }
            if (cls == clsClass$137) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$138 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort;
            if (clsClass$138 == null) {
                clsClass$138 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort = clsClass$138;
            }
            if (cls == clsClass$138) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$139 = class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte;
            if (clsClass$139 == null) {
                clsClass$139 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$ShortOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte = clsClass$139;
            }
            if (cls == clsClass$139) {
                return 21001;
            }
            Class clsClass$140 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger;
            if (clsClass$140 == null) {
                clsClass$140 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger = clsClass$140;
            }
            if (cls == clsClass$140) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$141 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte;
            if (clsClass$141 == null) {
                clsClass$141 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte = clsClass$141;
            }
            if (cls == clsClass$141) {
                return 23001;
            }
            Class clsClass$142 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort;
            if (clsClass$142 == null) {
                clsClass$142 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort = clsClass$142;
            }
            if (cls == clsClass$142) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$143 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger;
            if (clsClass$143 == null) {
                clsClass$143 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger = clsClass$143;
            }
            if (cls == clsClass$143) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$144 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong;
            if (clsClass$144 == null) {
                clsClass$144 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong = clsClass$144;
            }
            if (cls == clsClass$144) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$145 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble;
            if (clsClass$145 == null) {
                clsClass$145 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrDouble");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble = clsClass$145;
            }
            if (cls == clsClass$145) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$146 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat;
            if (clsClass$146 == null) {
                clsClass$146 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat = clsClass$146;
            }
            if (cls == clsClass$146) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$147 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte;
            if (clsClass$147 == null) {
                clsClass$147 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte = clsClass$147;
            }
            if (cls == clsClass$147) {
                return 18001;
            }
            Class clsClass$148 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort;
            if (clsClass$148 == null) {
                clsClass$148 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort = clsClass$148;
            }
            if (cls == clsClass$148) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$149 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort;
            if (clsClass$149 == null) {
                clsClass$149 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort = clsClass$149;
            }
            if (cls != clsClass$149 && class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort == null) {
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrShort");
            }
            return Integer.MAX_VALUE;
        }
        Class clsClass$150 = class$java$lang$Short;
        if (clsClass$150 == null) {
            clsClass$150 = class$("java.lang.Short");
            class$java$lang$Short = clsClass$150;
        }
        if (cls2 == clsClass$150) {
            Class clsClass$151 = class$java$lang$Integer;
            if (clsClass$151 == null) {
                clsClass$151 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$151;
            }
            if (cls == clsClass$151) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$152 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal;
            if (clsClass$152 == null) {
                clsClass$152 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerBigDecimal");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal = clsClass$152;
            }
            if (cls == clsClass$152) {
                return 34002;
            }
            Class clsClass$153 = class$java$math$BigDecimal;
            if (clsClass$153 == null) {
                clsClass$153 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$153;
            }
            if (cls == clsClass$153) {
                return 44002;
            }
            Class clsClass$154 = class$java$lang$Long;
            if (clsClass$154 == null) {
                clsClass$154 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$154;
            }
            if (cls == clsClass$154) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$155 = class$java$lang$Double;
            if (clsClass$155 == null) {
                clsClass$155 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$155;
            }
            if (cls == clsClass$155) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$156 = class$java$lang$Float;
            if (clsClass$156 == null) {
                clsClass$156 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$156;
            }
            if (cls == clsClass$156) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$157 = class$java$lang$Byte;
            if (clsClass$157 == null) {
                clsClass$157 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$157;
            }
            if (cls == clsClass$157) {
                return 10002;
            }
            Class clsClass$158 = class$java$math$BigInteger;
            if (clsClass$158 == null) {
                clsClass$158 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$158;
            }
            if (cls == clsClass$158) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$159 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger;
            if (clsClass$159 == null) {
                clsClass$159 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger = clsClass$159;
            }
            if (cls == clsClass$159) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$160 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat;
            if (clsClass$160 == null) {
                clsClass$160 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat = clsClass$160;
            }
            if (cls == clsClass$160) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$161 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat;
            if (clsClass$161 == null) {
                clsClass$161 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat = clsClass$161;
            }
            if (cls == clsClass$161) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$162 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger;
            if (clsClass$162 == null) {
                clsClass$162 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger = clsClass$162;
            }
            if (cls == clsClass$162) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$163 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong;
            if (clsClass$163 == null) {
                clsClass$163 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong = clsClass$163;
            }
            if (cls == clsClass$163) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$164 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte;
            if (clsClass$164 == null) {
                clsClass$164 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte = clsClass$164;
            }
            if (cls == clsClass$164) {
                return 21002;
            }
            Class clsClass$165 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte;
            if (clsClass$165 == null) {
                clsClass$165 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte = clsClass$165;
            }
            if (cls == clsClass$165) {
                return 24002;
            }
            Class clsClass$166 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte;
            if (clsClass$166 == null) {
                clsClass$166 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte = clsClass$166;
            }
            if (cls == clsClass$166) {
                return 22002;
            }
            Class clsClass$167 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort;
            if (clsClass$167 == null) {
                clsClass$167 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort = clsClass$167;
            }
            if (cls == clsClass$167) {
                return 22002;
            }
            Class clsClass$168 = class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte;
            if (clsClass$168 == null) {
                clsClass$168 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$ShortOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte = clsClass$168;
            }
            if (cls == clsClass$168) {
                return 0;
            }
            Class clsClass$169 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger;
            if (clsClass$169 == null) {
                clsClass$169 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger = clsClass$169;
            }
            if (cls == clsClass$169) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$170 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte;
            if (clsClass$170 == null) {
                clsClass$170 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte = clsClass$170;
            }
            if (cls == clsClass$170) {
                return 22002;
            }
            Class clsClass$171 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort;
            if (clsClass$171 == null) {
                clsClass$171 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort = clsClass$171;
            }
            if (cls == clsClass$171) {
                return 22002;
            }
            Class clsClass$172 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger;
            if (clsClass$172 == null) {
                clsClass$172 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger = clsClass$172;
            }
            if (cls == clsClass$172) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$173 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong;
            if (clsClass$173 == null) {
                clsClass$173 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong = clsClass$173;
            }
            if (cls == clsClass$173) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$174 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble;
            if (clsClass$174 == null) {
                clsClass$174 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrDouble");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble = clsClass$174;
            }
            if (cls == clsClass$174) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$175 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat;
            if (clsClass$175 == null) {
                clsClass$175 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat = clsClass$175;
            }
            if (cls == clsClass$175) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$176 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte;
            if (clsClass$176 == null) {
                clsClass$176 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte = clsClass$176;
            }
            if (cls == clsClass$176) {
                return FirebaseError.ERROR_CUSTOM_TOKEN_MISMATCH;
            }
            Class clsClass$177 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort;
            if (clsClass$177 == null) {
                clsClass$177 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort = clsClass$177;
            }
            if (cls == clsClass$177) {
                return 21002;
            }
            Class clsClass$178 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort;
            if (clsClass$178 == null) {
                clsClass$178 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort = clsClass$178;
            }
            if (cls == clsClass$178) {
                return 24002;
            }
            Class clsClass$179 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort;
            if (clsClass$179 == null) {
                clsClass$179 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort = clsClass$179;
            }
            if (cls == clsClass$179) {
                return FirebaseError.ERROR_CUSTOM_TOKEN_MISMATCH;
            }
            return Integer.MAX_VALUE;
        }
        Class clsClass$180 = class$java$math$BigDecimal;
        if (clsClass$180 == null) {
            clsClass$180 = class$("java.math.BigDecimal");
            class$java$math$BigDecimal = clsClass$180;
        }
        if (cls2 == clsClass$180) {
            Class clsClass$181 = class$java$lang$Integer;
            if (clsClass$181 == null) {
                clsClass$181 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$181;
            }
            if (cls == clsClass$181) {
                return 20008;
            }
            Class clsClass$182 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal;
            if (clsClass$182 == null) {
                clsClass$182 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerBigDecimal");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal = clsClass$182;
            }
            if (cls == clsClass$182) {
                return 0;
            }
            Class clsClass$183 = class$java$lang$Long;
            if (clsClass$183 == null) {
                clsClass$183 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$183;
            }
            if (cls == clsClass$183) {
                return 20008;
            }
            Class clsClass$184 = class$java$lang$Double;
            if (clsClass$184 == null) {
                clsClass$184 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$184;
            }
            if (cls == clsClass$184) {
                return 20008;
            }
            Class clsClass$185 = class$java$lang$Float;
            if (clsClass$185 == null) {
                clsClass$185 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$185;
            }
            if (cls == clsClass$185) {
                return 20008;
            }
            Class clsClass$186 = class$java$lang$Byte;
            if (clsClass$186 == null) {
                clsClass$186 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$186;
            }
            if (cls == clsClass$186) {
                return 20008;
            }
            Class clsClass$187 = class$java$math$BigInteger;
            if (clsClass$187 == null) {
                clsClass$187 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$187;
            }
            if (cls == clsClass$187) {
                return 10008;
            }
            Class clsClass$188 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger;
            if (clsClass$188 == null) {
                clsClass$188 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger = clsClass$188;
            }
            if (cls == clsClass$188) {
                return 20008;
            }
            Class clsClass$189 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat;
            if (clsClass$189 == null) {
                clsClass$189 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat = clsClass$189;
            }
            if (cls == clsClass$189) {
                return 20008;
            }
            Class clsClass$190 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat;
            if (clsClass$190 == null) {
                clsClass$190 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat = clsClass$190;
            }
            if (cls == clsClass$190) {
                return 20008;
            }
            Class clsClass$191 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger;
            if (clsClass$191 == null) {
                clsClass$191 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger = clsClass$191;
            }
            if (cls == clsClass$191) {
                return 20008;
            }
            Class clsClass$192 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong;
            if (clsClass$192 == null) {
                clsClass$192 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong = clsClass$192;
            }
            if (cls == clsClass$192) {
                return 20008;
            }
            Class clsClass$193 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte;
            if (clsClass$193 == null) {
                clsClass$193 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte = clsClass$193;
            }
            if (cls == clsClass$193) {
                return 20008;
            }
            Class clsClass$194 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte;
            if (clsClass$194 == null) {
                clsClass$194 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte = clsClass$194;
            }
            if (cls == clsClass$194) {
                return 20008;
            }
            Class clsClass$195 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte;
            if (clsClass$195 == null) {
                clsClass$195 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte = clsClass$195;
            }
            if (cls == clsClass$195) {
                return 20008;
            }
            Class clsClass$196 = class$java$lang$Short;
            if (clsClass$196 == null) {
                clsClass$196 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$196;
            }
            if (cls == clsClass$196) {
                return 20008;
            }
            Class clsClass$197 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort;
            if (clsClass$197 == null) {
                clsClass$197 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort = clsClass$197;
            }
            if (cls == clsClass$197) {
                return 20008;
            }
            Class clsClass$198 = class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte;
            if (clsClass$198 == null) {
                clsClass$198 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$ShortOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte = clsClass$198;
            }
            if (cls == clsClass$198) {
                return 20008;
            }
            Class clsClass$199 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger;
            if (clsClass$199 == null) {
                clsClass$199 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger = clsClass$199;
            }
            if (cls == clsClass$199) {
                return 20008;
            }
            Class clsClass$200 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte;
            if (clsClass$200 == null) {
                clsClass$200 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte = clsClass$200;
            }
            if (cls == clsClass$200) {
                return 20008;
            }
            Class clsClass$201 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort;
            if (clsClass$201 == null) {
                clsClass$201 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort = clsClass$201;
            }
            if (cls == clsClass$201) {
                return 20008;
            }
            Class clsClass$202 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger;
            if (clsClass$202 == null) {
                clsClass$202 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger = clsClass$202;
            }
            if (cls == clsClass$202) {
                return 10008;
            }
            Class clsClass$203 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong;
            if (clsClass$203 == null) {
                clsClass$203 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong = clsClass$203;
            }
            if (cls == clsClass$203) {
                return 10008;
            }
            Class clsClass$204 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble;
            if (clsClass$204 == null) {
                clsClass$204 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrDouble");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble = clsClass$204;
            }
            if (cls == clsClass$204) {
                return 10008;
            }
            Class clsClass$205 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat;
            if (clsClass$205 == null) {
                clsClass$205 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat = clsClass$205;
            }
            if (cls == clsClass$205) {
                return 10008;
            }
            Class clsClass$206 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte;
            if (clsClass$206 == null) {
                clsClass$206 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte = clsClass$206;
            }
            if (cls == clsClass$206) {
                return 10008;
            }
            Class clsClass$207 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort;
            if (clsClass$207 == null) {
                clsClass$207 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort = clsClass$207;
            }
            if (cls == clsClass$207) {
                return 20008;
            }
            Class clsClass$208 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort;
            if (clsClass$208 == null) {
                clsClass$208 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort = clsClass$208;
            }
            if (cls == clsClass$208) {
                return 20008;
            }
            Class clsClass$209 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort;
            if (clsClass$209 == null) {
                clsClass$209 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort = clsClass$209;
            }
            return cls == clsClass$209 ? 10008 : Integer.MAX_VALUE;
        }
        Class clsClass$210 = class$java$math$BigInteger;
        if (clsClass$210 == null) {
            clsClass$210 = class$("java.math.BigInteger");
            class$java$math$BigInteger = clsClass$210;
        }
        if (cls2 == clsClass$210) {
            Class clsClass$211 = class$java$lang$Integer;
            if (clsClass$211 == null) {
                clsClass$211 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$211;
            }
            if (cls == clsClass$211) {
                return 10005;
            }
            Class clsClass$212 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal;
            if (clsClass$212 == null) {
                clsClass$212 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerBigDecimal");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerBigDecimal = clsClass$212;
            }
            if (cls == clsClass$212) {
                return 10005;
            }
            Class clsClass$213 = class$java$math$BigDecimal;
            if (clsClass$213 == null) {
                clsClass$213 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$213;
            }
            if (cls == clsClass$213) {
                return 40005;
            }
            Class clsClass$214 = class$java$lang$Long;
            if (clsClass$214 == null) {
                clsClass$214 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$214;
            }
            if (cls == clsClass$214) {
                return 10005;
            }
            Class clsClass$215 = class$java$lang$Double;
            if (clsClass$215 == null) {
                clsClass$215 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$215;
            }
            if (cls == clsClass$215) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$216 = class$java$lang$Float;
            if (clsClass$216 == null) {
                clsClass$216 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$216;
            }
            if (cls == clsClass$216) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$217 = class$java$lang$Byte;
            if (clsClass$217 == null) {
                clsClass$217 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$217;
            }
            if (cls == clsClass$217) {
                return 10005;
            }
            Class clsClass$218 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger;
            if (clsClass$218 == null) {
                clsClass$218 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrInteger = clsClass$218;
            }
            if (cls == clsClass$218) {
                return 10005;
            }
            Class clsClass$219 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat;
            if (clsClass$219 == null) {
                clsClass$219 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrFloat = clsClass$219;
            }
            if (cls == clsClass$219) {
                return Integer.MAX_VALUE;
            }
            Class clsClass$220 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat;
            if (clsClass$220 == null) {
                clsClass$220 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrIntegerOrFloat = clsClass$220;
            }
            if (cls == clsClass$220) {
                return 21005;
            }
            Class clsClass$221 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger;
            if (clsClass$221 == null) {
                clsClass$221 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrInteger = clsClass$221;
            }
            if (cls == clsClass$221) {
                return 21005;
            }
            Class clsClass$222 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong;
            if (clsClass$222 == null) {
                clsClass$222 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrLong = clsClass$222;
            }
            if (cls == clsClass$222) {
                return 21005;
            }
            Class clsClass$223 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte;
            if (clsClass$223 == null) {
                clsClass$223 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrByte = clsClass$223;
            }
            if (cls == clsClass$223) {
                return 10005;
            }
            Class clsClass$224 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte;
            if (clsClass$224 == null) {
                clsClass$224 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrByte = clsClass$224;
            }
            if (cls == clsClass$224) {
                return 21005;
            }
            Class clsClass$225 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte;
            if (clsClass$225 == null) {
                clsClass$225 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrByte = clsClass$225;
            }
            if (cls == clsClass$225) {
                return 10005;
            }
            Class clsClass$226 = class$java$lang$Short;
            if (clsClass$226 == null) {
                clsClass$226 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$226;
            }
            if (cls == clsClass$226) {
                return 10005;
            }
            Class clsClass$227 = class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort;
            if (clsClass$227 == null) {
                clsClass$227 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$LongOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$LongOrShort = clsClass$227;
            }
            if (cls == clsClass$227) {
                return 10005;
            }
            Class clsClass$228 = class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte;
            if (clsClass$228 == null) {
                clsClass$228 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$ShortOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$ShortOrByte = clsClass$228;
            }
            if (cls == clsClass$228) {
                return 10005;
            }
            Class clsClass$229 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger;
            if (clsClass$229 == null) {
                clsClass$229 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrInteger = clsClass$229;
            }
            if (cls == clsClass$229) {
                return 25005;
            }
            Class clsClass$230 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte;
            if (clsClass$230 == null) {
                clsClass$230 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrByte = clsClass$230;
            }
            if (cls == clsClass$230) {
                return 25005;
            }
            Class clsClass$231 = class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort;
            if (clsClass$231 == null) {
                clsClass$231 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$FloatOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$FloatOrShort = clsClass$231;
            }
            if (cls == clsClass$231) {
                return 25005;
            }
            Class clsClass$232 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger;
            if (clsClass$232 == null) {
                clsClass$232 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrInteger");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrInteger = clsClass$232;
            }
            if (cls == clsClass$232) {
                return 0;
            }
            Class clsClass$233 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong;
            if (clsClass$233 == null) {
                clsClass$233 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrLong");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrLong = clsClass$233;
            }
            if (cls == clsClass$233) {
                return 0;
            }
            Class clsClass$234 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble;
            if (clsClass$234 == null) {
                clsClass$234 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrDouble");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrDouble = clsClass$234;
            }
            if (cls == clsClass$234) {
                return 0;
            }
            Class clsClass$235 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat;
            if (clsClass$235 == null) {
                clsClass$235 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrFloat");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrFloat = clsClass$235;
            }
            if (cls == clsClass$235) {
                return 0;
            }
            Class clsClass$236 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte;
            if (clsClass$236 == null) {
                clsClass$236 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrByte");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrByte = clsClass$236;
            }
            if (cls == clsClass$236) {
                return 0;
            }
            Class clsClass$237 = class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort;
            if (clsClass$237 == null) {
                clsClass$237 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$IntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$IntegerOrShort = clsClass$237;
            }
            if (cls == clsClass$237) {
                return 10005;
            }
            Class clsClass$238 = class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort;
            if (clsClass$238 == null) {
                clsClass$238 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$DoubleOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$DoubleOrShort = clsClass$238;
            }
            if (cls == clsClass$238) {
                return 21005;
            }
            Class clsClass$239 = class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort;
            if (clsClass$239 == null) {
                clsClass$239 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil$BigIntegerOrShort");
                class$freemarker$ext$beans$OverloadedNumberUtil$BigIntegerOrShort = clsClass$239;
            }
            if (cls == clsClass$239) {
                return 0;
            }
        }
        return Integer.MAX_VALUE;
    }

    static int compareNumberTypeSpecificity(Class cls, Class cls2) throws Throwable {
        Class clsPrimitiveClassToBoxingClass = ClassUtil.primitiveClassToBoxingClass(cls);
        Class clsPrimitiveClassToBoxingClass2 = ClassUtil.primitiveClassToBoxingClass(cls2);
        if (clsPrimitiveClassToBoxingClass == clsPrimitiveClassToBoxingClass2) {
            return 0;
        }
        Class clsClass$ = class$java$lang$Integer;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.Integer");
            class$java$lang$Integer = clsClass$;
        }
        if (clsPrimitiveClassToBoxingClass == clsClass$) {
            Class clsClass$2 = class$java$lang$Long;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$2;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$2) {
                return 1;
            }
            Class clsClass$3 = class$java$lang$Double;
            if (clsClass$3 == null) {
                clsClass$3 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$3;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$3) {
                return 4;
            }
            Class clsClass$4 = class$java$lang$Float;
            if (clsClass$4 == null) {
                clsClass$4 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$4;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$4) {
                return 3;
            }
            Class clsClass$5 = class$java$lang$Byte;
            if (clsClass$5 == null) {
                clsClass$5 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$5;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$5) {
                return -2;
            }
            Class clsClass$6 = class$java$lang$Short;
            if (clsClass$6 == null) {
                clsClass$6 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$6;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$6) {
                return -1;
            }
            Class clsClass$7 = class$java$math$BigDecimal;
            if (clsClass$7 == null) {
                clsClass$7 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$7;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$7) {
                return 5;
            }
            Class clsClass$8 = class$java$math$BigInteger;
            if (clsClass$8 == null) {
                clsClass$8 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$8;
            }
            return clsPrimitiveClassToBoxingClass2 == clsClass$8 ? 2 : 0;
        }
        Class clsClass$9 = class$java$lang$Long;
        if (clsClass$9 == null) {
            clsClass$9 = class$("java.lang.Long");
            class$java$lang$Long = clsClass$9;
        }
        if (clsPrimitiveClassToBoxingClass == clsClass$9) {
            Class clsClass$10 = class$java$lang$Integer;
            if (clsClass$10 == null) {
                clsClass$10 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$10;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$10) {
                return -1;
            }
            Class clsClass$11 = class$java$lang$Double;
            if (clsClass$11 == null) {
                clsClass$11 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$11;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$11) {
                return 3;
            }
            Class clsClass$12 = class$java$lang$Float;
            if (clsClass$12 == null) {
                clsClass$12 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$12;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$12) {
                return 2;
            }
            Class clsClass$13 = class$java$lang$Byte;
            if (clsClass$13 == null) {
                clsClass$13 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$13;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$13) {
                return -3;
            }
            Class clsClass$14 = class$java$lang$Short;
            if (clsClass$14 == null) {
                clsClass$14 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$14;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$14) {
                return -2;
            }
            Class clsClass$15 = class$java$math$BigDecimal;
            if (clsClass$15 == null) {
                clsClass$15 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$15;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$15) {
                return 4;
            }
            Class clsClass$16 = class$java$math$BigInteger;
            if (clsClass$16 == null) {
                clsClass$16 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$16;
            }
            return clsPrimitiveClassToBoxingClass2 == clsClass$16 ? 1 : 0;
        }
        Class clsClass$17 = class$java$lang$Double;
        if (clsClass$17 == null) {
            clsClass$17 = class$("java.lang.Double");
            class$java$lang$Double = clsClass$17;
        }
        if (clsPrimitiveClassToBoxingClass == clsClass$17) {
            Class clsClass$18 = class$java$lang$Integer;
            if (clsClass$18 == null) {
                clsClass$18 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$18;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$18) {
                return -4;
            }
            Class clsClass$19 = class$java$lang$Long;
            if (clsClass$19 == null) {
                clsClass$19 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$19;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$19) {
                return -3;
            }
            Class clsClass$20 = class$java$lang$Float;
            if (clsClass$20 == null) {
                clsClass$20 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$20;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$20) {
                return -1;
            }
            Class clsClass$21 = class$java$lang$Byte;
            if (clsClass$21 == null) {
                clsClass$21 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$21;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$21) {
                return -6;
            }
            Class clsClass$22 = class$java$lang$Short;
            if (clsClass$22 == null) {
                clsClass$22 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$22;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$22) {
                return -5;
            }
            Class clsClass$23 = class$java$math$BigDecimal;
            if (clsClass$23 == null) {
                clsClass$23 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$23;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$23) {
                return 1;
            }
            Class clsClass$24 = class$java$math$BigInteger;
            if (clsClass$24 == null) {
                clsClass$24 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$24;
            }
            return clsPrimitiveClassToBoxingClass2 == clsClass$24 ? -2 : 0;
        }
        Class clsClass$25 = class$java$lang$Float;
        if (clsClass$25 == null) {
            clsClass$25 = class$("java.lang.Float");
            class$java$lang$Float = clsClass$25;
        }
        if (clsPrimitiveClassToBoxingClass == clsClass$25) {
            Class clsClass$26 = class$java$lang$Integer;
            if (clsClass$26 == null) {
                clsClass$26 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$26;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$26) {
                return -3;
            }
            Class clsClass$27 = class$java$lang$Long;
            if (clsClass$27 == null) {
                clsClass$27 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$27;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$27) {
                return -2;
            }
            Class clsClass$28 = class$java$lang$Double;
            if (clsClass$28 == null) {
                clsClass$28 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$28;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$28) {
                return 1;
            }
            Class clsClass$29 = class$java$lang$Byte;
            if (clsClass$29 == null) {
                clsClass$29 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$29;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$29) {
                return -5;
            }
            Class clsClass$30 = class$java$lang$Short;
            if (clsClass$30 == null) {
                clsClass$30 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$30;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$30) {
                return -4;
            }
            Class clsClass$31 = class$java$math$BigDecimal;
            if (clsClass$31 == null) {
                clsClass$31 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$31;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$31) {
                return 2;
            }
            Class clsClass$32 = class$java$math$BigInteger;
            if (clsClass$32 == null) {
                clsClass$32 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$32;
            }
            return clsPrimitiveClassToBoxingClass2 == clsClass$32 ? -1 : 0;
        }
        Class clsClass$33 = class$java$lang$Byte;
        if (clsClass$33 == null) {
            clsClass$33 = class$("java.lang.Byte");
            class$java$lang$Byte = clsClass$33;
        }
        if (clsPrimitiveClassToBoxingClass == clsClass$33) {
            Class clsClass$34 = class$java$lang$Integer;
            if (clsClass$34 == null) {
                clsClass$34 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$34;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$34) {
                return 2;
            }
            Class clsClass$35 = class$java$lang$Long;
            if (clsClass$35 == null) {
                clsClass$35 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$35;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$35) {
                return 3;
            }
            Class clsClass$36 = class$java$lang$Double;
            if (clsClass$36 == null) {
                clsClass$36 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$36;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$36) {
                return 6;
            }
            Class clsClass$37 = class$java$lang$Float;
            if (clsClass$37 == null) {
                clsClass$37 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$37;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$37) {
                return 5;
            }
            Class clsClass$38 = class$java$lang$Short;
            if (clsClass$38 == null) {
                clsClass$38 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$38;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$38) {
                return 1;
            }
            Class clsClass$39 = class$java$math$BigDecimal;
            if (clsClass$39 == null) {
                clsClass$39 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$39;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$39) {
                return 7;
            }
            Class clsClass$40 = class$java$math$BigInteger;
            if (clsClass$40 == null) {
                clsClass$40 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$40;
            }
            return clsPrimitiveClassToBoxingClass2 == clsClass$40 ? 4 : 0;
        }
        Class clsClass$41 = class$java$lang$Short;
        if (clsClass$41 == null) {
            clsClass$41 = class$("java.lang.Short");
            class$java$lang$Short = clsClass$41;
        }
        if (clsPrimitiveClassToBoxingClass == clsClass$41) {
            Class clsClass$42 = class$java$lang$Integer;
            if (clsClass$42 == null) {
                clsClass$42 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$42;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$42) {
                return 1;
            }
            Class clsClass$43 = class$java$lang$Long;
            if (clsClass$43 == null) {
                clsClass$43 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$43;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$43) {
                return 2;
            }
            Class clsClass$44 = class$java$lang$Double;
            if (clsClass$44 == null) {
                clsClass$44 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$44;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$44) {
                return 5;
            }
            Class clsClass$45 = class$java$lang$Float;
            if (clsClass$45 == null) {
                clsClass$45 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$45;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$45) {
                return 4;
            }
            Class clsClass$46 = class$java$lang$Byte;
            if (clsClass$46 == null) {
                clsClass$46 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$46;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$46) {
                return -1;
            }
            Class clsClass$47 = class$java$math$BigDecimal;
            if (clsClass$47 == null) {
                clsClass$47 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$47;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$47) {
                return 6;
            }
            Class clsClass$48 = class$java$math$BigInteger;
            if (clsClass$48 == null) {
                clsClass$48 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$48;
            }
            return clsPrimitiveClassToBoxingClass2 == clsClass$48 ? 3 : 0;
        }
        Class clsClass$49 = class$java$math$BigDecimal;
        if (clsClass$49 == null) {
            clsClass$49 = class$("java.math.BigDecimal");
            class$java$math$BigDecimal = clsClass$49;
        }
        if (clsPrimitiveClassToBoxingClass == clsClass$49) {
            Class clsClass$50 = class$java$lang$Integer;
            if (clsClass$50 == null) {
                clsClass$50 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$50;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$50) {
                return -5;
            }
            Class clsClass$51 = class$java$lang$Long;
            if (clsClass$51 == null) {
                clsClass$51 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$51;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$51) {
                return -4;
            }
            Class clsClass$52 = class$java$lang$Double;
            if (clsClass$52 == null) {
                clsClass$52 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$52;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$52) {
                return -1;
            }
            Class clsClass$53 = class$java$lang$Float;
            if (clsClass$53 == null) {
                clsClass$53 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$53;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$53) {
                return -2;
            }
            Class clsClass$54 = class$java$lang$Byte;
            if (clsClass$54 == null) {
                clsClass$54 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$54;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$54) {
                return -7;
            }
            Class clsClass$55 = class$java$lang$Short;
            if (clsClass$55 == null) {
                clsClass$55 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$55;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$55) {
                return -6;
            }
            Class clsClass$56 = class$java$math$BigInteger;
            if (clsClass$56 == null) {
                clsClass$56 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$56;
            }
            return clsPrimitiveClassToBoxingClass2 == clsClass$56 ? -3 : 0;
        }
        Class clsClass$57 = class$java$math$BigInteger;
        if (clsClass$57 == null) {
            clsClass$57 = class$("java.math.BigInteger");
            class$java$math$BigInteger = clsClass$57;
        }
        if (clsPrimitiveClassToBoxingClass == clsClass$57) {
            Class clsClass$58 = class$java$lang$Integer;
            if (clsClass$58 == null) {
                clsClass$58 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$58;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$58) {
                return -2;
            }
            Class clsClass$59 = class$java$lang$Long;
            if (clsClass$59 == null) {
                clsClass$59 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$59;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$59) {
                return -1;
            }
            Class clsClass$60 = class$java$lang$Double;
            if (clsClass$60 == null) {
                clsClass$60 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$60;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$60) {
                return 2;
            }
            Class clsClass$61 = class$java$lang$Float;
            if (clsClass$61 == null) {
                clsClass$61 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$61;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$61) {
                return 1;
            }
            Class clsClass$62 = class$java$lang$Byte;
            if (clsClass$62 == null) {
                clsClass$62 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$62;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$62) {
                return -4;
            }
            Class clsClass$63 = class$java$lang$Short;
            if (clsClass$63 == null) {
                clsClass$63 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$63;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$63) {
                return -3;
            }
            Class clsClass$64 = class$java$math$BigDecimal;
            if (clsClass$64 == null) {
                clsClass$64 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$64;
            }
            if (clsPrimitiveClassToBoxingClass2 == clsClass$64) {
                return 3;
            }
        }
        return 0;
    }
}
