package org.mapstruct.ap.shaded.freemarker.core;

import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.SimpleSequence;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;
import org.mapstruct.ap.shaded.freemarker.template._TemplateAPI;
import org.mapstruct.ap.shaded.freemarker.template.utility.Constants;

/* loaded from: classes3.dex */
final class DynamicKeyName extends Expression {
    private static Class[] NUMERICAL_KEY_LHO_EXPECTED_TYPES;
    static /* synthetic */ Class class$freemarker$core$Range;
    static /* synthetic */ Class class$freemarker$template$TemplateNumberModel;
    static /* synthetic */ Class class$freemarker$template$TemplateScalarModel;
    static /* synthetic */ Class class$freemarker$template$TemplateSequenceModel;
    private final Expression keyExpression;
    private final Expression target;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "...[...]";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    DynamicKeyName(Expression expression, Expression expression2) {
        this.target = expression;
        this.keyExpression = expression2;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws Throwable {
        TemplateModel templateModelEval = this.target.eval(environment);
        if (templateModelEval == null) {
            if (environment.isClassicCompatible()) {
                return null;
            }
            throw InvalidReferenceException.getInstance(this.target, environment);
        }
        TemplateModel templateModelEval2 = this.keyExpression.eval(environment);
        if (templateModelEval2 == null) {
            if (environment.isClassicCompatible()) {
                templateModelEval2 = TemplateScalarModel.EMPTY_STRING;
            } else {
                this.keyExpression.assertNonNull(null, environment);
            }
        }
        TemplateModel templateModel = templateModelEval2;
        if (templateModel instanceof TemplateNumberModel) {
            return dealWithNumericalKey(templateModelEval, this.keyExpression.modelToNumber(templateModel, environment).intValue(), environment);
        }
        if (templateModel instanceof TemplateScalarModel) {
            return dealWithStringKey(templateModelEval, EvalUtil.modelToString((TemplateScalarModel) templateModel, this.keyExpression, environment), environment);
        }
        if (templateModel instanceof RangeModel) {
            return dealWithRangeKey(templateModelEval, (RangeModel) templateModel, environment);
        }
        Expression expression = this.keyExpression;
        Class[] clsArr = new Class[3];
        Class clsClass$ = class$freemarker$template$TemplateNumberModel;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel");
            class$freemarker$template$TemplateNumberModel = clsClass$;
        }
        clsArr[0] = clsClass$;
        Class clsClass$2 = class$freemarker$template$TemplateScalarModel;
        if (clsClass$2 == null) {
            clsClass$2 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel");
            class$freemarker$template$TemplateScalarModel = clsClass$2;
        }
        clsArr[1] = clsClass$2;
        Class clsClass$3 = class$freemarker$core$Range;
        if (clsClass$3 == null) {
            clsClass$3 = class$("org.mapstruct.ap.shaded.freemarker.core.Range");
            class$freemarker$core$Range = clsClass$3;
        }
        clsArr[2] = clsClass$3;
        throw new UnexpectedTypeException(expression, templateModel, "number, range, or string", clsArr, environment);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static {
        Class[] clsArr = new Class[NonStringException.STRING_COERCABLE_TYPES.length + 1];
        NUMERICAL_KEY_LHO_EXPECTED_TYPES = clsArr;
        Class clsClass$ = class$freemarker$template$TemplateSequenceModel;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel");
            class$freemarker$template$TemplateSequenceModel = clsClass$;
        }
        int i = 0;
        clsArr[0] = clsClass$;
        while (i < NonStringException.STRING_COERCABLE_TYPES.length) {
            int i2 = i + 1;
            NUMERICAL_KEY_LHO_EXPECTED_TYPES[i2] = NonStringException.STRING_COERCABLE_TYPES[i];
            i = i2;
        }
    }

    private TemplateModel dealWithNumericalKey(TemplateModel templateModel, int i, Environment environment) throws TemplateException {
        int size;
        if (templateModel instanceof TemplateSequenceModel) {
            TemplateSequenceModel templateSequenceModel = (TemplateSequenceModel) templateModel;
            try {
                size = templateSequenceModel.size();
            } catch (Exception unused) {
                size = Integer.MAX_VALUE;
            }
            if (i < size) {
                return templateSequenceModel.get(i);
            }
            return null;
        }
        try {
            String strEvalAndCoerceToString = this.target.evalAndCoerceToString(environment);
            try {
                return new SimpleScalar(strEvalAndCoerceToString.substring(i, i + 1));
            } catch (IndexOutOfBoundsException e) {
                if (i < 0) {
                    throw new _MiscTemplateException(new Object[]{"Negative index not allowed: ", new Integer(i)});
                }
                if (i >= strEvalAndCoerceToString.length()) {
                    throw new _MiscTemplateException(new Object[]{"String index out of range: The index was ", new Integer(i), " (0-based), but the length of the string is only ", new Integer(strEvalAndCoerceToString.length()), "."});
                }
                throw new RuntimeException("Can't explain exception", e);
            }
        } catch (NonStringException unused2) {
            throw new UnexpectedTypeException(this.target, templateModel, "sequence or string or something automatically convertible to string (number, date or boolean)", NUMERICAL_KEY_LHO_EXPECTED_TYPES, environment);
        }
    }

    private TemplateModel dealWithStringKey(TemplateModel templateModel, String str, Environment environment) throws TemplateException {
        if (templateModel instanceof TemplateHashModel) {
            return ((TemplateHashModel) templateModel).get(str);
        }
        throw new NonHashException(this.target, templateModel, environment);
    }

    private TemplateModel dealWithRangeKey(TemplateModel templateModel, RangeModel rangeModel, Environment environment) throws TemplateException {
        String strEvalAndCoerceToString;
        TemplateSequenceModel templateSequenceModel;
        int i;
        if (templateModel instanceof TemplateSequenceModel) {
            templateSequenceModel = (TemplateSequenceModel) templateModel;
            strEvalAndCoerceToString = null;
        } else {
            try {
                strEvalAndCoerceToString = this.target.evalAndCoerceToString(environment);
                templateSequenceModel = null;
            } catch (NonStringException unused) {
                Expression expression = this.target;
                throw new UnexpectedTypeException(expression, expression.eval(environment), "sequence or string or something automatically convertible to string (number, date or boolean)", NUMERICAL_KEY_LHO_EXPECTED_TYPES, environment);
            }
        }
        int size = rangeModel.size();
        boolean zIsRightUnbounded = rangeModel.isRightUnbounded();
        boolean zIsRightAdaptive = rangeModel.isRightAdaptive();
        if (!zIsRightUnbounded && size == 0) {
            return emptyResult(templateSequenceModel != null);
        }
        int begining = rangeModel.getBegining();
        if (begining < 0) {
            throw new _MiscTemplateException(this.keyExpression, new Object[]{"Negative range start index (", new Integer(begining), ") isn't allowed for a range used for slicing."});
        }
        int length = strEvalAndCoerceToString != null ? strEvalAndCoerceToString.length() : templateSequenceModel.size();
        int step = rangeModel.getStep();
        if (!(zIsRightAdaptive && step == 1) ? begining < length : begining <= length) {
            Expression expression2 = this.keyExpression;
            Object[] objArr = new Object[10];
            objArr[0] = "Range start index ";
            objArr[1] = new Integer(begining);
            objArr[2] = " is out of bounds, because the sliced ";
            objArr[3] = strEvalAndCoerceToString == null ? "sequence" : "string";
            objArr[4] = " has only ";
            objArr[5] = new Integer(length);
            objArr[6] = StringUtils.SPACE;
            objArr[7] = strEvalAndCoerceToString == null ? "element(s)" : "character(s)";
            objArr[8] = ". ";
            objArr[9] = "(Note that indices are 0-based).";
            throw new _MiscTemplateException(expression2, objArr);
        }
        if (zIsRightUnbounded) {
            size = length - begining;
        } else {
            int i2 = ((size - 1) * step) + begining;
            if (i2 < 0) {
                if (!zIsRightAdaptive) {
                    throw new _MiscTemplateException(this.keyExpression, new Object[]{"Negative range end index (", new Integer(i2), ") isn't allowed for a range used for slicing."});
                }
                size = begining + 1;
            } else if (i2 >= length) {
                if (!zIsRightAdaptive) {
                    Expression expression3 = this.keyExpression;
                    Object[] objArr2 = new Object[9];
                    objArr2[0] = "Range end index ";
                    objArr2[1] = new Integer(i2);
                    objArr2[2] = " is out of bounds, because the sliced ";
                    objArr2[3] = strEvalAndCoerceToString == null ? "sequence" : "string";
                    objArr2[4] = " has only ";
                    objArr2[5] = new Integer(length);
                    objArr2[6] = StringUtils.SPACE;
                    objArr2[7] = strEvalAndCoerceToString == null ? "element(s)" : "character(s)";
                    objArr2[8] = ". (Note that indices are 0-based).";
                    throw new _MiscTemplateException(expression3, objArr2);
                }
                size = Math.abs(length - begining);
            }
        }
        if (size == 0) {
            return emptyResult(templateSequenceModel != null);
        }
        if (templateSequenceModel != null) {
            ArrayList arrayList = new ArrayList(size);
            for (int i3 = 0; i3 < size; i3++) {
                arrayList.add(templateSequenceModel.get(begining));
                begining += step;
            }
            return new SimpleSequence(arrayList, (ObjectWrapper) null);
        }
        if (step >= 0 || size <= 1) {
            i = size + begining;
        } else {
            if (!rangeModel.isAffactedByStringSlicingBug() || size != 2) {
                throw new _MiscTemplateException(this.keyExpression, new Object[]{"Decreasing ranges aren't allowed for slicing strings (as it would give reversed text). The index range was: first = ", new Integer(begining), ", last = ", new Integer(begining + ((size - 1) * step))});
            }
            i = begining;
        }
        return new SimpleScalar(strEvalAndCoerceToString.substring(begining, i));
    }

    private TemplateModel emptyResult(boolean z) {
        return z ? _TemplateAPI.getTemplateLanguageVersionAsInt(this) < _TemplateAPI.VERSION_INT_2_3_21 ? new SimpleSequence(Collections.EMPTY_LIST, (ObjectWrapper) null) : Constants.EMPTY_SEQUENCE : TemplateScalarModel.EMPTY_STRING;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return new StringBuffer().append(this.target.getCanonicalForm()).append("[").append(this.keyExpression.getCanonicalForm()).append("]").toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return this.constantValue != null || (this.target.isLiteral() && this.keyExpression.isLiteral());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        return i == 0 ? this.target : this.keyExpression;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        return i == 0 ? ParameterRole.LEFT_HAND_OPERAND : ParameterRole.ENCLOSED_OPERAND;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        return new DynamicKeyName(this.target.deepCloneWithIdentifierReplaced(str, expression, replacemenetState), this.keyExpression.deepCloneWithIdentifierReplaced(str, expression, replacemenetState));
    }
}
