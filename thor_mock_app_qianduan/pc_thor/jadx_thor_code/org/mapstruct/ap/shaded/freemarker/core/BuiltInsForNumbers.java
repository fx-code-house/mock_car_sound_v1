package org.mapstruct.ap.shaded.freemarker.core;

import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import org.mapstruct.ap.shaded.freemarker.template.SimpleDate;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.NumberUtil;

/* loaded from: classes3.dex */
class BuiltInsForNumbers {
    private static final BigDecimal BIG_DECIMAL_ONE = new BigDecimal(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
    private static final BigDecimal BIG_DECIMAL_LONG_MIN = BigDecimal.valueOf(Long.MIN_VALUE);
    private static final BigDecimal BIG_DECIMAL_LONG_MAX = BigDecimal.valueOf(Long.MAX_VALUE);
    private static final BigInteger BIG_INTEGER_LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);
    private static final BigInteger BIG_INTEGER_LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);

    static class absBI extends BuiltInForNumber {
        absBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNumber
        TemplateModel calculateResult(Number number, TemplateModel templateModel) throws TemplateModelException {
            if (number instanceof Integer) {
                int iIntValue = ((Integer) number).intValue();
                return iIntValue < 0 ? new SimpleNumber(-iIntValue) : templateModel;
            }
            if (number instanceof BigDecimal) {
                BigDecimal bigDecimal = (BigDecimal) number;
                return bigDecimal.signum() < 0 ? new SimpleNumber(bigDecimal.negate()) : templateModel;
            }
            if (number instanceof Double) {
                double dDoubleValue = ((Double) number).doubleValue();
                return dDoubleValue < 0.0d ? new SimpleNumber(-dDoubleValue) : templateModel;
            }
            if (number instanceof Float) {
                float fFloatValue = ((Float) number).floatValue();
                return fFloatValue < 0.0f ? new SimpleNumber(-fFloatValue) : templateModel;
            }
            if (number instanceof Long) {
                long jLongValue = ((Long) number).longValue();
                return jLongValue < 0 ? new SimpleNumber(-jLongValue) : templateModel;
            }
            if (number instanceof Short) {
                short sShortValue = ((Short) number).shortValue();
                return sShortValue < 0 ? new SimpleNumber(-sShortValue) : templateModel;
            }
            if (number instanceof Byte) {
                byte bByteValue = ((Byte) number).byteValue();
                return bByteValue < 0 ? new SimpleNumber(-bByteValue) : templateModel;
            }
            if (number instanceof BigInteger) {
                BigInteger bigInteger = (BigInteger) number;
                return bigInteger.signum() < 0 ? new SimpleNumber(bigInteger.negate()) : templateModel;
            }
            throw new _TemplateModelException(new Object[]{"Unsupported number class: ", number.getClass()});
        }
    }

    static class byteBI extends BuiltInForNumber {
        byteBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNumber
        TemplateModel calculateResult(Number number, TemplateModel templateModel) {
            return number instanceof Byte ? templateModel : new SimpleNumber(new Byte(number.byteValue()));
        }
    }

    static class ceilingBI extends BuiltInForNumber {
        ceilingBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNumber
        TemplateModel calculateResult(Number number, TemplateModel templateModel) {
            return new SimpleNumber(new BigDecimal(number.doubleValue()).divide(BuiltInsForNumbers.BIG_DECIMAL_ONE, 0, 2));
        }
    }

    static class doubleBI extends BuiltInForNumber {
        doubleBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNumber
        TemplateModel calculateResult(Number number, TemplateModel templateModel) {
            return number instanceof Double ? templateModel : new SimpleNumber(number.doubleValue());
        }
    }

    static class floatBI extends BuiltInForNumber {
        floatBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNumber
        TemplateModel calculateResult(Number number, TemplateModel templateModel) {
            return number instanceof Float ? templateModel : new SimpleNumber(number.floatValue());
        }
    }

    static class floorBI extends BuiltInForNumber {
        floorBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNumber
        TemplateModel calculateResult(Number number, TemplateModel templateModel) {
            return new SimpleNumber(new BigDecimal(number.doubleValue()).divide(BuiltInsForNumbers.BIG_DECIMAL_ONE, 0, 3));
        }
    }

    static class intBI extends BuiltInForNumber {
        intBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNumber
        TemplateModel calculateResult(Number number, TemplateModel templateModel) {
            return number instanceof Integer ? templateModel : new SimpleNumber(number.intValue());
        }
    }

    static class is_infiniteBI extends BuiltInForNumber {
        is_infiniteBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNumber
        TemplateModel calculateResult(Number number, TemplateModel templateModel) throws TemplateModelException {
            return NumberUtil.isInfinite(number) ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_nanBI extends BuiltInForNumber {
        is_nanBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNumber
        TemplateModel calculateResult(Number number, TemplateModel templateModel) throws TemplateModelException {
            return NumberUtil.isNaN(number) ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class longBI extends BuiltIn {
        longBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            if (!(templateModelEval instanceof TemplateNumberModel) && (templateModelEval instanceof TemplateDateModel)) {
                return new SimpleNumber(EvalUtil.modelToDate((TemplateDateModel) templateModelEval, this.target).getTime());
            }
            Number numberModelToNumber = this.target.modelToNumber(templateModelEval, environment);
            return numberModelToNumber instanceof Long ? templateModelEval : new SimpleNumber(numberModelToNumber.longValue());
        }
    }

    static class number_to_dateBI extends BuiltInForNumber {
        private final int dateType;

        number_to_dateBI(int i) {
            this.dateType = i;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNumber
        TemplateModel calculateResult(Number number, TemplateModel templateModel) throws TemplateModelException {
            return new SimpleDate(new Date(BuiltInsForNumbers.safeToLong(number)), this.dateType);
        }
    }

    static class roundBI extends BuiltInForNumber {
        private static final BigDecimal half = new BigDecimal("0.5");

        roundBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNumber
        TemplateModel calculateResult(Number number, TemplateModel templateModel) {
            return new SimpleNumber(new BigDecimal(number.doubleValue()).add(half).divide(BuiltInsForNumbers.BIG_DECIMAL_ONE, 0, 3));
        }
    }

    static class shortBI extends BuiltInForNumber {
        shortBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForNumber
        TemplateModel calculateResult(Number number, TemplateModel templateModel) {
            return number instanceof Short ? templateModel : new SimpleNumber(new Short(number.shortValue()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final long safeToLong(Number number) throws TemplateModelException {
        if (number instanceof Double) {
            double dRound = Math.round(((Double) number).doubleValue());
            if (dRound > 9.223372036854776E18d || dRound < -9.223372036854776E18d) {
                throw new _TemplateModelException(new Object[]{"Number doesn't fit into a 64 bit signed integer (long): ", new Double(dRound)});
            }
            return (long) dRound;
        }
        if (number instanceof Float) {
            float fRound = Math.round(((Float) number).floatValue());
            if (fRound > 9.223372E18f || fRound < -9.223372E18f) {
                throw new _TemplateModelException(new Object[]{"Number doesn't fit into a 64 bit signed integer (long): ", new Float(fRound)});
            }
            return (long) fRound;
        }
        if (number instanceof BigDecimal) {
            BigDecimal scale = ((BigDecimal) number).setScale(0, 4);
            if (scale.compareTo(BIG_DECIMAL_LONG_MAX) > 0 || scale.compareTo(BIG_DECIMAL_LONG_MIN) < 0) {
                throw new _TemplateModelException(new Object[]{"Number doesn't fit into a 64 bit signed integer (long): ", scale});
            }
            return scale.longValue();
        }
        if (number instanceof BigInteger) {
            BigInteger bigInteger = (BigInteger) number;
            if (bigInteger.compareTo(BIG_INTEGER_LONG_MAX) > 0 || bigInteger.compareTo(BIG_INTEGER_LONG_MIN) < 0) {
                throw new _TemplateModelException(new Object[]{"Number doesn't fit into a 64 bit signed integer (long): ", bigInteger});
            }
            return bigInteger.longValue();
        }
        if ((number instanceof Long) || (number instanceof Integer) || (number instanceof Byte) || (number instanceof Short)) {
            return number.longValue();
        }
        throw new _TemplateModelException(new Object[]{"Unsupported number type: ", number.getClass()});
    }

    private BuiltInsForNumbers() {
    }
}
