package org.mapstruct.ap.shaded.freemarker.template.utility;

import java.math.BigDecimal;
import java.math.BigInteger;

/* loaded from: classes3.dex */
public class NumberUtil {
    private NumberUtil() {
    }

    public static boolean isInfinite(Number number) {
        if (number instanceof Double) {
            return ((Double) number).isInfinite();
        }
        if (number instanceof Float) {
            return ((Float) number).isInfinite();
        }
        if (isNonFPNumberOfSupportedClass(number)) {
            return false;
        }
        throw new UnsupportedNumberClassException(number.getClass());
    }

    public static boolean isNaN(Number number) {
        if (number instanceof Double) {
            return ((Double) number).isNaN();
        }
        if (number instanceof Float) {
            return ((Float) number).isNaN();
        }
        if (isNonFPNumberOfSupportedClass(number)) {
            return false;
        }
        throw new UnsupportedNumberClassException(number.getClass());
    }

    public static int getSignum(Number number) throws ArithmeticException {
        if (number instanceof Integer) {
            int iIntValue = ((Integer) number).intValue();
            if (iIntValue > 0) {
                return 1;
            }
            return iIntValue == 0 ? 0 : -1;
        }
        if (number instanceof BigDecimal) {
            return ((BigDecimal) number).signum();
        }
        if (number instanceof Double) {
            double dDoubleValue = ((Double) number).doubleValue();
            if (dDoubleValue > 0.0d) {
                return 1;
            }
            if (dDoubleValue == 0.0d) {
                return 0;
            }
            if (dDoubleValue < 0.0d) {
                return -1;
            }
            throw new ArithmeticException(new StringBuffer("The signum of ").append(dDoubleValue).append(" is not defined.").toString());
        }
        if (number instanceof Float) {
            float fFloatValue = ((Float) number).floatValue();
            if (fFloatValue > 0.0f) {
                return 1;
            }
            if (fFloatValue == 0.0f) {
                return 0;
            }
            if (fFloatValue < 0.0f) {
                return -1;
            }
            throw new ArithmeticException(new StringBuffer("The signum of ").append(fFloatValue).append(" is not defined.").toString());
        }
        if (number instanceof Long) {
            long jLongValue = ((Long) number).longValue();
            if (jLongValue > 0) {
                return 1;
            }
            return jLongValue == 0 ? 0 : -1;
        }
        if (number instanceof Short) {
            short sShortValue = ((Short) number).shortValue();
            if (sShortValue > 0) {
                return 1;
            }
            return sShortValue == 0 ? 0 : -1;
        }
        if (number instanceof Byte) {
            byte bByteValue = ((Byte) number).byteValue();
            if (bByteValue > 0) {
                return 1;
            }
            return bByteValue == 0 ? 0 : -1;
        }
        if (number instanceof BigInteger) {
            return ((BigInteger) number).signum();
        }
        throw new UnsupportedNumberClassException(number.getClass());
    }

    public static boolean isIntegerBigDecimal(BigDecimal bigDecimal) {
        return bigDecimal.scale() <= 0 || bigDecimal.setScale(0, 1).compareTo(bigDecimal) == 0;
    }

    private static boolean isNonFPNumberOfSupportedClass(Number number) {
        return (number instanceof Integer) || (number instanceof BigDecimal) || (number instanceof Long) || (number instanceof Short) || (number instanceof Byte) || (number instanceof BigInteger);
    }
}
