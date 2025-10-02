package org.mapstruct.ap.shaded.freemarker.core;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.util.Date;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans._BeansAPI;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
class EvalUtil {
    static final int CMP_OP_EQUALS = 1;
    static final int CMP_OP_GREATER_THAN = 4;
    static final int CMP_OP_GREATER_THAN_EQUALS = 6;
    static final int CMP_OP_LESS_THAN = 3;
    static final int CMP_OP_LESS_THAN_EQUALS = 5;
    static final int CMP_OP_NOT_EQUALS = 2;
    private static final String VALUE_OF_THE_COMPARISON_IS_UNKNOWN_DATE_LIKE = "value of the comparison is a date-like value where it's not known if it's a date (no time part), time, or date-time, and thus can't be used in a comparison.";
    static /* synthetic */ Class class$java$lang$Number;
    static /* synthetic */ Class class$java$lang$String;
    static /* synthetic */ Class class$java$util$Date;

    private static String cmpOpToString(int i, String str) {
        if (str != null) {
            return str;
        }
        switch (i) {
            case 1:
                return "equals";
            case 2:
                return "not-equals";
            case 3:
                return "less-than";
            case 4:
                return "greater-than";
            case 5:
                return "less-than-equals";
            case 6:
                return "greater-than-equals";
            default:
                return "???";
        }
    }

    private EvalUtil() {
    }

    static String modelToString(TemplateScalarModel templateScalarModel, Expression expression, Environment environment) throws Throwable {
        String asString = templateScalarModel.getAsString();
        if (asString != null) {
            return asString;
        }
        if (environment == null) {
            environment = Environment.getCurrentEnvironment();
        }
        if (environment != null && environment.isClassicCompatible()) {
            return "";
        }
        Class clsClass$ = class$java$lang$String;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.String");
            class$java$lang$String = clsClass$;
        }
        throw newModelHasStoredNullException(clsClass$, templateScalarModel, expression);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static Number modelToNumber(TemplateNumberModel templateNumberModel, Expression expression) throws Throwable {
        Number asNumber = templateNumberModel.getAsNumber();
        if (asNumber != null) {
            return asNumber;
        }
        Class clsClass$ = class$java$lang$Number;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.Number");
            class$java$lang$Number = clsClass$;
        }
        throw newModelHasStoredNullException(clsClass$, templateNumberModel, expression);
    }

    static Date modelToDate(TemplateDateModel templateDateModel, Expression expression) throws Throwable {
        Date asDate = templateDateModel.getAsDate();
        if (asDate != null) {
            return asDate;
        }
        Class clsClass$ = class$java$util$Date;
        if (clsClass$ == null) {
            clsClass$ = class$("java.util.Date");
            class$java$util$Date = clsClass$;
        }
        throw newModelHasStoredNullException(clsClass$, templateDateModel, expression);
    }

    private static TemplateModelException newModelHasStoredNullException(Class cls, TemplateModel templateModel, Expression expression) {
        return new _TemplateModelException(expression, _TemplateModelException.modelHasStoredNullDescription(cls, templateModel));
    }

    static boolean compare(Expression expression, int i, String str, Expression expression2, Expression expression3, Environment environment) throws TemplateException {
        return compare(expression.eval(environment), expression, i, str, expression2.eval(environment), expression2, expression3, false, false, false, environment);
    }

    static boolean compare(TemplateModel templateModel, int i, TemplateModel templateModel2, Environment environment) throws TemplateException {
        return compare(templateModel, null, i, null, templateModel2, null, null, false, false, false, environment);
    }

    static boolean compareLenient(TemplateModel templateModel, int i, TemplateModel templateModel2, Environment environment) throws TemplateException {
        return compare(templateModel, null, i, null, templateModel2, null, null, true, false, false, environment);
    }

    static boolean compare(TemplateModel templateModel, Expression expression, int i, String str, TemplateModel templateModel2, Expression expression2, Expression expression3, boolean z, boolean z2, boolean z3, Environment environment) throws Throwable {
        TemplateModel templateModel3;
        TemplateModel templateModel4;
        int iCompare;
        String str2;
        ArithmeticEngine arithmeticEngine;
        Expression expression4 = expression;
        if (templateModel != null) {
            templateModel3 = templateModel;
        } else {
            if (environment == null || !environment.isClassicCompatible()) {
                if (z2) {
                    return false;
                }
                if (expression4 != null) {
                    throw InvalidReferenceException.getInstance(expression4, environment);
                }
                throw new _MiscTemplateException(expression3, environment, "The left operand of the comparison was undefined or null.");
            }
            templateModel3 = TemplateScalarModel.EMPTY_STRING;
        }
        if (templateModel2 != null) {
            templateModel4 = templateModel2;
        } else {
            if (environment == null || !environment.isClassicCompatible()) {
                if (z3) {
                    return false;
                }
                if (expression2 != null) {
                    throw InvalidReferenceException.getInstance(expression2, environment);
                }
                throw new _MiscTemplateException(expression3, environment, "The right operand of the comparison was undefined or null.");
            }
            templateModel4 = TemplateScalarModel.EMPTY_STRING;
        }
        if ((templateModel3 instanceof TemplateNumberModel) && (templateModel4 instanceof TemplateNumberModel)) {
            Number numberModelToNumber = modelToNumber((TemplateNumberModel) templateModel3, expression4);
            Number numberModelToNumber2 = modelToNumber((TemplateNumberModel) templateModel4, expression2);
            if (environment != null) {
                arithmeticEngine = environment.getArithmeticEngine();
            } else {
                arithmeticEngine = expression4 != null ? expression.getTemplate().getArithmeticEngine() : ArithmeticEngine.BIGDECIMAL_ENGINE;
            }
            try {
                iCompare = arithmeticEngine.compareNumbers(numberModelToNumber, numberModelToNumber2);
            } catch (RuntimeException e) {
                throw new _MiscTemplateException(expression3, e, environment, new Object[]{"Unexpected error while comparing two numbers: ", e});
            }
        } else if ((templateModel3 instanceof TemplateDateModel) && (templateModel4 instanceof TemplateDateModel)) {
            TemplateDateModel templateDateModel = (TemplateDateModel) templateModel3;
            TemplateDateModel templateDateModel2 = (TemplateDateModel) templateModel4;
            int dateType = templateDateModel.getDateType();
            int dateType2 = templateDateModel2.getDateType();
            if (dateType == 0 || dateType2 == 0) {
                if (dateType == 0) {
                    str2 = TtmlNode.LEFT;
                } else {
                    str2 = TtmlNode.RIGHT;
                    expression4 = expression2;
                }
                if (expression4 == null) {
                    expression4 = expression3;
                }
                throw new _MiscTemplateException(expression4, environment, new Object[]{"The ", str2, StringUtils.SPACE, VALUE_OF_THE_COMPARISON_IS_UNKNOWN_DATE_LIKE});
            }
            if (dateType != dateType2) {
                throw new _MiscTemplateException(expression3, environment, new Object[]{"Can't compare dates of different types. Left date type is ", TemplateDateModel.TYPE_NAMES.get(dateType), ", right date type is ", TemplateDateModel.TYPE_NAMES.get(dateType2), "."});
            }
            iCompare = modelToDate(templateDateModel, expression4).compareTo(modelToDate(templateDateModel2, expression2));
        } else if ((templateModel3 instanceof TemplateScalarModel) && (templateModel4 instanceof TemplateScalarModel)) {
            if (i != 1 && i != 2) {
                throw new _MiscTemplateException(expression3, environment, new Object[]{"Can't use operator \"", cmpOpToString(i, str), "\" on string values."});
            }
            iCompare = environment.getCollator().compare(modelToString((TemplateScalarModel) templateModel3, expression4, environment), modelToString((TemplateScalarModel) templateModel4, expression2, environment));
        } else if ((templateModel3 instanceof TemplateBooleanModel) && (templateModel4 instanceof TemplateBooleanModel)) {
            if (i != 1 && i != 2) {
                throw new _MiscTemplateException(expression3, environment, new Object[]{"Can't use operator \"", cmpOpToString(i, str), "\" on boolean values."});
            }
            iCompare = (((TemplateBooleanModel) templateModel3).getAsBoolean() ? 1 : 0) - (((TemplateBooleanModel) templateModel4).getAsBoolean() ? 1 : 0);
        } else if (environment.isClassicCompatible()) {
            iCompare = environment.getCollator().compare(expression4.evalAndCoerceToString(environment), expression2.evalAndCoerceToString(environment));
        } else {
            if (z) {
                if (i == 1) {
                    return false;
                }
                if (i == 2) {
                    return true;
                }
            }
            throw new _MiscTemplateException(expression3, environment, new Object[]{"Can't compare values of these types. ", "Allowed comparisons are between two numbers, two strings, two dates, or two booleans.\n", "Left hand operand is ", new _DelayedAOrAn(new _DelayedFTLTypeDescription(templateModel3)), ".\n", "Right hand operand is ", new _DelayedAOrAn(new _DelayedFTLTypeDescription(templateModel4)), "."});
        }
        switch (i) {
            case 1:
                return iCompare == 0;
            case 2:
                return iCompare != 0;
            case 3:
                return iCompare < 0;
            case 4:
                return iCompare > 0;
            case 5:
                return iCompare <= 0;
            case 6:
                return iCompare >= 0;
            default:
                throw new BugException(new StringBuffer("Unsupported comparator operator code: ").append(i).toString());
        }
    }

    static String coerceModelToString(TemplateModel templateModel, Expression expression, String str, Environment environment) throws TemplateException {
        if (templateModel instanceof TemplateNumberModel) {
            return environment.formatNumber(modelToNumber((TemplateNumberModel) templateModel, expression));
        }
        if (templateModel instanceof TemplateDateModel) {
            return environment.formatDate((TemplateDateModel) templateModel, expression);
        }
        if (templateModel instanceof TemplateScalarModel) {
            return modelToString((TemplateScalarModel) templateModel, expression, environment);
        }
        if (templateModel == null) {
            if (environment.isClassicCompatible()) {
                return "";
            }
            if (expression != null) {
                throw InvalidReferenceException.getInstance(expression, environment);
            }
            throw new InvalidReferenceException("Null/missing value (no more informatoin avilable)", environment);
        }
        if (templateModel instanceof TemplateBooleanModel) {
            boolean asBoolean = ((TemplateBooleanModel) templateModel).getAsBoolean();
            int classicCompatibleAsInt = environment.getClassicCompatibleAsInt();
            if (classicCompatibleAsInt == 0) {
                return environment.formatBoolean(asBoolean, false);
            }
            if (classicCompatibleAsInt == 1) {
                return asBoolean ? BooleanUtils.TRUE : "";
            }
            if (classicCompatibleAsInt == 2) {
                if (templateModel instanceof BeanModel) {
                    return _BeansAPI.getAsClassicCompatibleString((BeanModel) templateModel);
                }
                return asBoolean ? BooleanUtils.TRUE : "";
            }
            throw new BugException(new StringBuffer("Unsupported classic_compatible variation: ").append(classicCompatibleAsInt).toString());
        }
        if (environment.isClassicCompatible() && (templateModel instanceof BeanModel)) {
            return _BeansAPI.getAsClassicCompatibleString((BeanModel) templateModel);
        }
        if (str != null && ((templateModel instanceof TemplateSequenceModel) || (templateModel instanceof TemplateCollectionModel))) {
            throw new NonStringException(expression, templateModel, str, environment);
        }
        throw new NonStringException(expression, templateModel, environment);
    }
}
