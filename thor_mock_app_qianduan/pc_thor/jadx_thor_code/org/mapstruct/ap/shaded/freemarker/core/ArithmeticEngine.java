package org.mapstruct.ap.shaded.freemarker.core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.utility.NumberUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.OptimizerUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
public abstract class ArithmeticEngine {
    public static final BigDecimalEngine BIGDECIMAL_ENGINE = new BigDecimalEngine();
    public static final ConservativeEngine CONSERVATIVE_ENGINE = new ConservativeEngine();
    static /* synthetic */ Class class$java$lang$Byte;
    static /* synthetic */ Class class$java$lang$Double;
    static /* synthetic */ Class class$java$lang$Float;
    static /* synthetic */ Class class$java$lang$Integer;
    static /* synthetic */ Class class$java$lang$Long;
    static /* synthetic */ Class class$java$lang$Short;
    static /* synthetic */ Class class$java$math$BigDecimal;
    static /* synthetic */ Class class$java$math$BigInteger;
    protected int minScale = 12;
    protected int maxScale = 12;
    protected int roundingPolicy = 4;

    public abstract Number add(Number number, Number number2) throws TemplateException;

    public abstract int compareNumbers(Number number, Number number2) throws TemplateException;

    public abstract Number divide(Number number, Number number2) throws TemplateException;

    public abstract Number modulus(Number number, Number number2) throws TemplateException;

    public abstract Number multiply(Number number, Number number2) throws TemplateException;

    public abstract Number subtract(Number number, Number number2) throws TemplateException;

    public abstract Number toNumber(String str);

    public void setMinScale(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("minScale < 0");
        }
        this.minScale = i;
    }

    public void setMaxScale(int i) {
        if (i < this.minScale) {
            throw new IllegalArgumentException("maxScale < minScale");
        }
        this.maxScale = i;
    }

    public void setRoundingPolicy(int i) {
        if (i != 2 && i != 1 && i != 3 && i != 5 && i != 6 && i != 4 && i != 7 && i != 0) {
            throw new IllegalArgumentException("invalid rounding policy");
        }
        this.roundingPolicy = i;
    }

    public static class BigDecimalEngine extends ArithmeticEngine {
        @Override // org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine
        public int compareNumbers(Number number, Number number2) throws ArithmeticException {
            int signum = NumberUtil.getSignum(number);
            int signum2 = NumberUtil.getSignum(number2);
            if (signum != signum2) {
                if (signum < signum2) {
                    return -1;
                }
                return signum > signum2 ? 1 : 0;
            }
            if (signum == 0 && signum2 == 0) {
                return 0;
            }
            return ArithmeticEngine.toBigDecimal(number).compareTo(ArithmeticEngine.toBigDecimal(number2));
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine
        public Number add(Number number, Number number2) {
            return ArithmeticEngine.toBigDecimal(number).add(ArithmeticEngine.toBigDecimal(number2));
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine
        public Number subtract(Number number, Number number2) {
            return ArithmeticEngine.toBigDecimal(number).subtract(ArithmeticEngine.toBigDecimal(number2));
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine
        public Number multiply(Number number, Number number2) {
            BigDecimal bigDecimalMultiply = ArithmeticEngine.toBigDecimal(number).multiply(ArithmeticEngine.toBigDecimal(number2));
            return bigDecimalMultiply.scale() > this.maxScale ? bigDecimalMultiply.setScale(this.maxScale, this.roundingPolicy) : bigDecimalMultiply;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine
        public Number divide(Number number, Number number2) {
            return divide(ArithmeticEngine.toBigDecimal(number), ArithmeticEngine.toBigDecimal(number2));
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine
        public Number modulus(Number number, Number number2) {
            return new Long(number.longValue() % number2.longValue());
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine
        public Number toNumber(String str) {
            return ArithmeticEngine.toBigDecimalOrDouble(str);
        }

        private BigDecimal divide(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
            return bigDecimal.divide(bigDecimal2, Math.max(this.minScale, Math.max(bigDecimal.scale(), bigDecimal2.scale())), this.roundingPolicy);
        }
    }

    public static class ConservativeEngine extends ArithmeticEngine {
        private static final int BIGDECIMAL = 5;
        private static final int BIGINTEGER = 4;
        private static final int DOUBLE = 3;
        private static final int FLOAT = 2;
        private static final int INTEGER = 0;
        private static final int LONG = 1;
        private static final Map classCodes = createClassCodesMap();

        @Override // org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine
        public int compareNumbers(Number number, Number number2) throws TemplateException {
            int commonClassCode = getCommonClassCode(number, number2);
            if (commonClassCode == 0) {
                int iIntValue = number.intValue();
                int iIntValue2 = number2.intValue();
                if (iIntValue < iIntValue2) {
                    return -1;
                }
                return iIntValue == iIntValue2 ? 0 : 1;
            }
            if (commonClassCode == 1) {
                long jLongValue = number.longValue();
                long jLongValue2 = number2.longValue();
                if (jLongValue < jLongValue2) {
                    return -1;
                }
                return jLongValue == jLongValue2 ? 0 : 1;
            }
            if (commonClassCode == 2) {
                float fFloatValue = number.floatValue();
                float fFloatValue2 = number2.floatValue();
                if (fFloatValue < fFloatValue2) {
                    return -1;
                }
                return fFloatValue == fFloatValue2 ? 0 : 1;
            }
            if (commonClassCode != 3) {
                if (commonClassCode == 4) {
                    return toBigInteger(number).compareTo(toBigInteger(number2));
                }
                if (commonClassCode == 5) {
                    return ArithmeticEngine.toBigDecimal(number).compareTo(ArithmeticEngine.toBigDecimal(number2));
                }
                throw new Error();
            }
            double dDoubleValue = number.doubleValue();
            double dDoubleValue2 = number2.doubleValue();
            if (dDoubleValue < dDoubleValue2) {
                return -1;
            }
            return dDoubleValue == dDoubleValue2 ? 0 : 1;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine
        public Number add(Number number, Number number2) throws TemplateException {
            int commonClassCode = getCommonClassCode(number, number2);
            if (commonClassCode == 0) {
                int iIntValue = number.intValue();
                int iIntValue2 = number2.intValue();
                int i = iIntValue + iIntValue2;
                return ((i ^ iIntValue) >= 0 || (i ^ iIntValue2) >= 0) ? new Integer(i) : new Long(iIntValue + iIntValue2);
            }
            if (commonClassCode == 1) {
                long jLongValue = number.longValue();
                long jLongValue2 = number2.longValue();
                long j = jLongValue + jLongValue2;
                return ((jLongValue ^ j) >= 0 || (j ^ jLongValue2) >= 0) ? new Long(j) : toBigInteger(number).add(toBigInteger(number2));
            }
            if (commonClassCode == 2) {
                return new Float(number.floatValue() + number2.floatValue());
            }
            if (commonClassCode == 3) {
                return new Double(number.doubleValue() + number2.doubleValue());
            }
            if (commonClassCode == 4) {
                return toBigInteger(number).add(toBigInteger(number2));
            }
            if (commonClassCode == 5) {
                return ArithmeticEngine.toBigDecimal(number).add(ArithmeticEngine.toBigDecimal(number2));
            }
            throw new Error();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine
        public Number subtract(Number number, Number number2) throws TemplateException {
            int commonClassCode = getCommonClassCode(number, number2);
            if (commonClassCode == 0) {
                int iIntValue = number.intValue();
                int iIntValue2 = number2.intValue();
                int i = iIntValue - iIntValue2;
                return ((i ^ iIntValue) >= 0 || ((~iIntValue2) ^ i) >= 0) ? new Integer(i) : new Long(iIntValue - iIntValue2);
            }
            if (commonClassCode == 1) {
                long jLongValue = number.longValue();
                long jLongValue2 = number2.longValue();
                long j = jLongValue - jLongValue2;
                return ((jLongValue ^ j) >= 0 || ((~jLongValue2) ^ j) >= 0) ? new Long(j) : toBigInteger(number).subtract(toBigInteger(number2));
            }
            if (commonClassCode == 2) {
                return new Float(number.floatValue() - number2.floatValue());
            }
            if (commonClassCode == 3) {
                return new Double(number.doubleValue() - number2.doubleValue());
            }
            if (commonClassCode == 4) {
                return toBigInteger(number).subtract(toBigInteger(number2));
            }
            if (commonClassCode == 5) {
                return ArithmeticEngine.toBigDecimal(number).subtract(ArithmeticEngine.toBigDecimal(number2));
            }
            throw new Error();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine
        public Number multiply(Number number, Number number2) throws TemplateException {
            int commonClassCode = getCommonClassCode(number, number2);
            if (commonClassCode == 0) {
                int iIntValue = number.intValue();
                int iIntValue2 = number2.intValue();
                int i = iIntValue * iIntValue2;
                return (iIntValue == 0 || i / iIntValue == iIntValue2) ? new Integer(i) : new Long(iIntValue * iIntValue2);
            }
            if (commonClassCode == 1) {
                long jLongValue = number.longValue();
                long jLongValue2 = number2.longValue();
                long j = jLongValue * jLongValue2;
                if (jLongValue == 0 || j / jLongValue == jLongValue2) {
                    return new Long(j);
                }
                return toBigInteger(number).multiply(toBigInteger(number2));
            }
            if (commonClassCode == 2) {
                return new Float(number.floatValue() * number2.floatValue());
            }
            if (commonClassCode == 3) {
                return new Double(number.doubleValue() * number2.doubleValue());
            }
            if (commonClassCode == 4) {
                return toBigInteger(number).multiply(toBigInteger(number2));
            }
            if (commonClassCode == 5) {
                BigDecimal bigDecimalMultiply = ArithmeticEngine.toBigDecimal(number).multiply(ArithmeticEngine.toBigDecimal(number2));
                return bigDecimalMultiply.scale() > this.maxScale ? bigDecimalMultiply.setScale(this.maxScale, this.roundingPolicy) : bigDecimalMultiply;
            }
            throw new Error();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine
        public Number divide(Number number, Number number2) throws TemplateException {
            int commonClassCode = getCommonClassCode(number, number2);
            if (commonClassCode == 0) {
                int iIntValue = number.intValue();
                int iIntValue2 = number2.intValue();
                if (iIntValue % iIntValue2 == 0) {
                    return new Integer(iIntValue / iIntValue2);
                }
                return new Double(iIntValue / iIntValue2);
            }
            if (commonClassCode == 1) {
                long jLongValue = number.longValue();
                long jLongValue2 = number2.longValue();
                if (jLongValue % jLongValue2 == 0) {
                    return new Long(jLongValue / jLongValue2);
                }
                return new Double(jLongValue / jLongValue2);
            }
            if (commonClassCode == 2) {
                return new Float(number.floatValue() / number2.floatValue());
            }
            if (commonClassCode == 3) {
                return new Double(number.doubleValue() / number2.doubleValue());
            }
            if (commonClassCode != 4) {
                if (commonClassCode == 5) {
                    BigDecimal bigDecimal = ArithmeticEngine.toBigDecimal(number);
                    BigDecimal bigDecimal2 = ArithmeticEngine.toBigDecimal(number2);
                    return bigDecimal.divide(bigDecimal2, Math.max(this.minScale, Math.max(bigDecimal.scale(), bigDecimal2.scale())), this.roundingPolicy);
                }
                throw new Error();
            }
            BigInteger bigInteger = toBigInteger(number);
            BigInteger bigInteger2 = toBigInteger(number2);
            BigInteger[] bigIntegerArrDivideAndRemainder = bigInteger.divideAndRemainder(bigInteger2);
            if (bigIntegerArrDivideAndRemainder[1].equals(BigInteger.ZERO)) {
                return bigIntegerArrDivideAndRemainder[0];
            }
            return new BigDecimal(bigInteger).divide(new BigDecimal(bigInteger2), this.minScale, this.roundingPolicy);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine
        public Number modulus(Number number, Number number2) throws TemplateException {
            int commonClassCode = getCommonClassCode(number, number2);
            if (commonClassCode == 0) {
                return new Integer(number.intValue() % number2.intValue());
            }
            if (commonClassCode == 1) {
                return new Long(number.longValue() % number2.longValue());
            }
            if (commonClassCode == 2) {
                return new Float(number.floatValue() % number2.floatValue());
            }
            if (commonClassCode == 3) {
                return new Double(number.doubleValue() % number2.doubleValue());
            }
            if (commonClassCode == 4) {
                return toBigInteger(number).mod(toBigInteger(number2));
            }
            if (commonClassCode == 5) {
                throw new _MiscTemplateException("Can't calculate remainder on BigDecimals");
            }
            throw new Error();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine
        public Number toNumber(String str) {
            Number bigDecimalOrDouble = ArithmeticEngine.toBigDecimalOrDouble(str);
            return bigDecimalOrDouble instanceof BigDecimal ? OptimizerUtil.optimizeNumberRepresentation(bigDecimalOrDouble) : bigDecimalOrDouble;
        }

        private static Map createClassCodesMap() throws Throwable {
            Class clsClass$;
            Class clsClass$2;
            Class clsClass$3;
            Class clsClass$4;
            Class clsClass$5;
            Class clsClass$6;
            Class clsClass$7;
            Class clsClass$8;
            HashMap map = new HashMap(17);
            Integer num = new Integer(0);
            if (ArithmeticEngine.class$java$lang$Byte == null) {
                clsClass$ = ArithmeticEngine.class$("java.lang.Byte");
                ArithmeticEngine.class$java$lang$Byte = clsClass$;
            } else {
                clsClass$ = ArithmeticEngine.class$java$lang$Byte;
            }
            map.put(clsClass$, num);
            if (ArithmeticEngine.class$java$lang$Short == null) {
                clsClass$2 = ArithmeticEngine.class$("java.lang.Short");
                ArithmeticEngine.class$java$lang$Short = clsClass$2;
            } else {
                clsClass$2 = ArithmeticEngine.class$java$lang$Short;
            }
            map.put(clsClass$2, num);
            if (ArithmeticEngine.class$java$lang$Integer == null) {
                clsClass$3 = ArithmeticEngine.class$("java.lang.Integer");
                ArithmeticEngine.class$java$lang$Integer = clsClass$3;
            } else {
                clsClass$3 = ArithmeticEngine.class$java$lang$Integer;
            }
            map.put(clsClass$3, num);
            if (ArithmeticEngine.class$java$lang$Long == null) {
                clsClass$4 = ArithmeticEngine.class$("java.lang.Long");
                ArithmeticEngine.class$java$lang$Long = clsClass$4;
            } else {
                clsClass$4 = ArithmeticEngine.class$java$lang$Long;
            }
            map.put(clsClass$4, new Integer(1));
            if (ArithmeticEngine.class$java$lang$Float == null) {
                clsClass$5 = ArithmeticEngine.class$("java.lang.Float");
                ArithmeticEngine.class$java$lang$Float = clsClass$5;
            } else {
                clsClass$5 = ArithmeticEngine.class$java$lang$Float;
            }
            map.put(clsClass$5, new Integer(2));
            if (ArithmeticEngine.class$java$lang$Double == null) {
                clsClass$6 = ArithmeticEngine.class$("java.lang.Double");
                ArithmeticEngine.class$java$lang$Double = clsClass$6;
            } else {
                clsClass$6 = ArithmeticEngine.class$java$lang$Double;
            }
            map.put(clsClass$6, new Integer(3));
            if (ArithmeticEngine.class$java$math$BigInteger == null) {
                clsClass$7 = ArithmeticEngine.class$("java.math.BigInteger");
                ArithmeticEngine.class$java$math$BigInteger = clsClass$7;
            } else {
                clsClass$7 = ArithmeticEngine.class$java$math$BigInteger;
            }
            map.put(clsClass$7, new Integer(4));
            if (ArithmeticEngine.class$java$math$BigDecimal == null) {
                clsClass$8 = ArithmeticEngine.class$("java.math.BigDecimal");
                ArithmeticEngine.class$java$math$BigDecimal = clsClass$8;
            } else {
                clsClass$8 = ArithmeticEngine.class$java$math$BigDecimal;
            }
            map.put(clsClass$8, new Integer(5));
            return map;
        }

        private static int getClassCode(Number number) throws TemplateException {
            try {
                return ((Integer) classCodes.get(number.getClass())).intValue();
            } catch (NullPointerException unused) {
                if (number == null) {
                    throw new _MiscTemplateException("The Number object was null.");
                }
                throw new _MiscTemplateException(new Object[]{"Unknown number type ", number.getClass().getName()});
            }
        }

        private static int getCommonClassCode(Number number, Number number2) throws TemplateException {
            int classCode = getClassCode(number);
            int classCode2 = getClassCode(number2);
            int i = classCode > classCode2 ? classCode : classCode2;
            if (i == 2) {
                if (classCode >= classCode2) {
                    classCode = classCode2;
                }
                if (classCode == 1) {
                    return 3;
                }
            } else if (i == 4) {
                if (classCode >= classCode2) {
                    classCode = classCode2;
                }
                if (classCode == 3 || classCode == 2) {
                    return 5;
                }
            }
            return i;
        }

        private static BigInteger toBigInteger(Number number) {
            return number instanceof BigInteger ? (BigInteger) number : new BigInteger(number.toString());
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static BigDecimal toBigDecimal(Number number) {
        try {
            return number instanceof BigDecimal ? (BigDecimal) number : new BigDecimal(number.toString());
        } catch (NumberFormatException unused) {
            throw new NumberFormatException(new StringBuffer("Can't parse this as BigDecimal number: ").append(StringUtil.jQuote(number)).toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Number toBigDecimalOrDouble(String str) {
        if (str.length() > 2) {
            char cCharAt = str.charAt(0);
            if (cCharAt == 'I' && (str.equals("INF") || str.equals("Infinity"))) {
                return new Double(Double.POSITIVE_INFINITY);
            }
            if (cCharAt == 'N' && str.equals("NaN")) {
                return new Double(Double.NaN);
            }
            if (cCharAt == '-' && str.charAt(1) == 'I' && (str.equals("-INF") || str.equals("-Infinity"))) {
                return new Double(Double.NEGATIVE_INFINITY);
            }
        }
        return new BigDecimal(str);
    }
}
