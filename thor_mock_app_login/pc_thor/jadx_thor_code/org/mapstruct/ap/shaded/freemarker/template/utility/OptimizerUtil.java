package org.mapstruct.ap.shaded.freemarker.template.utility;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class OptimizerUtil {
    private static final BigInteger INTEGER_MIN = new BigInteger(Integer.toString(Integer.MIN_VALUE));
    private static final BigInteger INTEGER_MAX = new BigInteger(Integer.toString(Integer.MAX_VALUE));
    private static final BigInteger LONG_MIN = new BigInteger(Long.toString(Long.MIN_VALUE));
    private static final BigInteger LONG_MAX = new BigInteger(Long.toString(Long.MAX_VALUE));

    private OptimizerUtil() {
    }

    public static List optimizeListStorage(List list) {
        int size = list.size();
        if (size == 0) {
            return Collections.EMPTY_LIST;
        }
        if (size == 1) {
            return Collections.singletonList(list.get(0));
        }
        if (list instanceof ArrayList) {
            ((ArrayList) list).trimToSize();
        }
        return list;
    }

    public static Number optimizeNumberRepresentation(Number number) {
        if (number instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) number;
            if (bigDecimal.scale() == 0) {
                number = bigDecimal.unscaledValue();
            } else {
                double dDoubleValue = bigDecimal.doubleValue();
                if (dDoubleValue != Double.POSITIVE_INFINITY && dDoubleValue != Double.NEGATIVE_INFINITY) {
                    return new Double(dDoubleValue);
                }
            }
        }
        if (!(number instanceof BigInteger)) {
            return number;
        }
        BigInteger bigInteger = (BigInteger) number;
        if (bigInteger.compareTo(INTEGER_MAX) > 0 || bigInteger.compareTo(INTEGER_MIN) < 0) {
            return (bigInteger.compareTo(LONG_MAX) > 0 || bigInteger.compareTo(LONG_MIN) < 0) ? number : new Long(bigInteger.longValue());
        }
        return new Integer(bigInteger.intValue());
    }
}
