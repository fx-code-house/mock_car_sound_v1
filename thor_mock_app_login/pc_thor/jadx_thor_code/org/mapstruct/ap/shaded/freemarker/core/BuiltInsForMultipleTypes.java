package org.mapstruct.ap.shaded.freemarker.core;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethodsModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans.SimpleMethodModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans._BeansAPI;
import org.mapstruct.ap.shaded.freemarker.template.SimpleDate;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDirectiveModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel;
import org.mapstruct.ap.shaded.freemarker.template._TemplateAPI;

/* loaded from: classes3.dex */
class BuiltInsForMultipleTypes {
    static /* synthetic */ Class class$freemarker$core$Macro;
    static /* synthetic */ Class class$freemarker$template$TemplateBooleanModel;
    static /* synthetic */ Class class$freemarker$template$TemplateDateModel;
    static /* synthetic */ Class class$freemarker$template$TemplateHashModelEx;
    static /* synthetic */ Class class$freemarker$template$TemplateNumberModel;
    static /* synthetic */ Class class$freemarker$template$TemplateScalarModel;
    static /* synthetic */ Class class$freemarker$template$TemplateSequenceModel;
    static /* synthetic */ Class class$java$util$Date;

    static class cBI extends AbstractCBI implements ICIChainMember {
        private final BIBeforeICE2d3d21 prevICIObj = new BIBeforeICE2d3d21();

        cBI() {
        }

        static class BIBeforeICE2d3d21 extends AbstractCBI {
            BIBeforeICE2d3d21() {
            }

            @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInsForMultipleTypes.AbstractCBI
            protected TemplateModel formatNumber(Environment environment, TemplateModel templateModel) throws Throwable {
                Number numberModelToNumber = EvalUtil.modelToNumber((TemplateNumberModel) templateModel, this.target);
                if ((numberModelToNumber instanceof Integer) || (numberModelToNumber instanceof Long)) {
                    return new SimpleScalar(numberModelToNumber.toString());
                }
                return new SimpleScalar(environment.getCNumberFormat().format(numberModelToNumber));
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInsForMultipleTypes.AbstractCBI, org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws Throwable {
            Class clsClass$;
            Class clsClass$2;
            TemplateModel templateModelEval = this.target.eval(environment);
            if (templateModelEval instanceof TemplateNumberModel) {
                return formatNumber(environment, templateModelEval);
            }
            if (templateModelEval instanceof TemplateBooleanModel) {
                return new SimpleScalar(((TemplateBooleanModel) templateModelEval).getAsBoolean() ? BooleanUtils.TRUE : BooleanUtils.FALSE);
            }
            Expression expression = this.target;
            Class[] clsArr = new Class[2];
            if (BuiltInsForMultipleTypes.class$freemarker$template$TemplateNumberModel == null) {
                clsClass$ = BuiltInsForMultipleTypes.class$("org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel");
                BuiltInsForMultipleTypes.class$freemarker$template$TemplateNumberModel = clsClass$;
            } else {
                clsClass$ = BuiltInsForMultipleTypes.class$freemarker$template$TemplateNumberModel;
            }
            clsArr[0] = clsClass$;
            if (BuiltInsForMultipleTypes.class$freemarker$template$TemplateBooleanModel == null) {
                clsClass$2 = BuiltInsForMultipleTypes.class$("org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel");
                BuiltInsForMultipleTypes.class$freemarker$template$TemplateBooleanModel = clsClass$2;
            } else {
                clsClass$2 = BuiltInsForMultipleTypes.class$freemarker$template$TemplateBooleanModel;
            }
            clsArr[1] = clsClass$2;
            throw new UnexpectedTypeException(expression, templateModelEval, "number or boolean", clsArr, environment);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInsForMultipleTypes.AbstractCBI
        protected TemplateModel formatNumber(Environment environment, TemplateModel templateModel) throws Throwable {
            Number numberModelToNumber = EvalUtil.modelToNumber((TemplateNumberModel) templateModel, this.target);
            if ((numberModelToNumber instanceof Integer) || (numberModelToNumber instanceof Long)) {
                return new SimpleScalar(numberModelToNumber.toString());
            }
            if (numberModelToNumber instanceof Double) {
                double dDoubleValue = numberModelToNumber.doubleValue();
                if (dDoubleValue == Double.POSITIVE_INFINITY) {
                    return new SimpleScalar("INF");
                }
                if (dDoubleValue == Double.NEGATIVE_INFINITY) {
                    return new SimpleScalar("-INF");
                }
                if (Double.isNaN(dDoubleValue)) {
                    return new SimpleScalar("NaN");
                }
            } else if (numberModelToNumber instanceof Float) {
                float fFloatValue = numberModelToNumber.floatValue();
                if (fFloatValue == Float.POSITIVE_INFINITY) {
                    return new SimpleScalar("INF");
                }
                if (fFloatValue == Float.NEGATIVE_INFINITY) {
                    return new SimpleScalar("-INF");
                }
                if (Float.isNaN(fFloatValue)) {
                    return new SimpleScalar("NaN");
                }
            }
            return new SimpleScalar(environment.getCNumberFormat().format(numberModelToNumber));
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ICIChainMember
        public int getMinimumICIVersion() {
            return _TemplateAPI.VERSION_INT_2_3_21;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.ICIChainMember
        public Object getPreviousICIChainMember() {
            return this.prevICIObj;
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static class dateBI extends BuiltIn {
        private final int dateType;

        private class DateParser implements TemplateDateModel, TemplateMethodModel, TemplateHashModel {
            private Date cachedValue;
            private final TemplateDateFormat defaultFormat;
            private final Environment env;
            private final String text;

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
            public boolean isEmpty() {
                return false;
            }

            DateParser(String str, Environment environment) throws Throwable {
                Class clsClass$;
                this.text = str;
                this.env = environment;
                int i = dateBI.this.dateType;
                if (BuiltInsForMultipleTypes.class$java$util$Date == null) {
                    clsClass$ = BuiltInsForMultipleTypes.class$("java.util.Date");
                    BuiltInsForMultipleTypes.class$java$util$Date = clsClass$;
                } else {
                    clsClass$ = BuiltInsForMultipleTypes.class$java$util$Date;
                }
                this.defaultFormat = environment.getTemplateDateFormat(i, clsClass$, dateBI.this.target);
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                dateBI.this.checkMethodArgCount(list, 1);
                return get((String) list.get(0));
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
            public TemplateModel get(String str) throws Throwable {
                Class clsClass$;
                Environment environment = this.env;
                int i = dateBI.this.dateType;
                if (BuiltInsForMultipleTypes.class$java$util$Date == null) {
                    clsClass$ = BuiltInsForMultipleTypes.class$("java.util.Date");
                    BuiltInsForMultipleTypes.class$java$util$Date = clsClass$;
                } else {
                    clsClass$ = BuiltInsForMultipleTypes.class$java$util$Date;
                }
                return new SimpleDate(parse(environment.getTemplateDateFormat(i, clsClass$, str, dateBI.this.target)), dateBI.this.dateType);
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel
            public Date getAsDate() throws TemplateModelException {
                if (this.cachedValue == null) {
                    this.cachedValue = parse(this.defaultFormat);
                }
                return this.cachedValue;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel
            public int getDateType() {
                return dateBI.this.dateType;
            }

            private Date parse(TemplateDateFormat templateDateFormat) throws TemplateModelException {
                try {
                    return templateDateFormat.parse(this.text);
                } catch (java.text.ParseException e) {
                    Object[] objArr = new Object[8];
                    objArr[0] = "The string doesn't match the expected date/time/date-time format. The string to parse was: ";
                    objArr[1] = new _DelayedJQuote(this.text);
                    objArr[2] = ". ";
                    objArr[3] = "The expected format was: ";
                    objArr[4] = new _DelayedJQuote(templateDateFormat.getDescription());
                    objArr[5] = ".";
                    objArr[6] = e.getMessage() != null ? "\nThe nested reason given follows:\n" : "";
                    objArr[7] = e.getMessage() != null ? e.getMessage() : "";
                    throw new _TemplateModelException(e, objArr);
                }
            }
        }

        dateBI(int i) {
            this.dateType = i;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            if (templateModelEval instanceof TemplateDateModel) {
                TemplateDateModel templateDateModel = (TemplateDateModel) templateModelEval;
                int dateType = templateDateModel.getDateType();
                if (this.dateType == dateType) {
                    return templateModelEval;
                }
                if (dateType == 0 || dateType == 3) {
                    return new SimpleDate(templateDateModel.getAsDate(), this.dateType);
                }
                throw new _MiscTemplateException(this, new Object[]{"Cannot convert ", TemplateDateModel.TYPE_NAMES.get(dateType), " to ", TemplateDateModel.TYPE_NAMES.get(this.dateType)});
            }
            return new DateParser(this.target.evalAndCoerceToString(environment), environment);
        }
    }

    static class is_booleanBI extends BuiltIn {
        is_booleanBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return templateModelEval instanceof TemplateBooleanModel ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_collectionBI extends BuiltIn {
        is_collectionBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return templateModelEval instanceof TemplateCollectionModel ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_dateLikeBI extends BuiltIn {
        is_dateLikeBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return templateModelEval instanceof TemplateDateModel ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_dateOfTypeBI extends BuiltIn {
        private final int dateType;

        is_dateOfTypeBI(int i) {
            this.dateType = i;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return ((templateModelEval instanceof TemplateDateModel) && ((TemplateDateModel) templateModelEval).getDateType() == this.dateType) ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_directiveBI extends BuiltIn {
        is_directiveBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return ((templateModelEval instanceof TemplateTransformModel) || (templateModelEval instanceof Macro) || (templateModelEval instanceof TemplateDirectiveModel)) ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_enumerableBI extends BuiltIn {
        is_enumerableBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return (((templateModelEval instanceof TemplateSequenceModel) || (templateModelEval instanceof TemplateCollectionModel)) && (_TemplateAPI.getTemplateLanguageVersionAsInt(this) < _TemplateAPI.VERSION_INT_2_3_21 || !((templateModelEval instanceof SimpleMethodModel) || (templateModelEval instanceof OverloadedMethodsModel)))) ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_hash_exBI extends BuiltIn {
        is_hash_exBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return templateModelEval instanceof TemplateHashModelEx ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_hashBI extends BuiltIn {
        is_hashBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return templateModelEval instanceof TemplateHashModel ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_indexableBI extends BuiltIn {
        is_indexableBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return templateModelEval instanceof TemplateSequenceModel ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_macroBI extends BuiltIn {
        is_macroBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return templateModelEval instanceof Macro ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_methodBI extends BuiltIn {
        is_methodBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return templateModelEval instanceof TemplateMethodModel ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_nodeBI extends BuiltIn {
        is_nodeBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return templateModelEval instanceof TemplateNodeModel ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_numberBI extends BuiltIn {
        is_numberBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return templateModelEval instanceof TemplateNumberModel ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_sequenceBI extends BuiltIn {
        is_sequenceBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return templateModelEval instanceof TemplateSequenceModel ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_stringBI extends BuiltIn {
        is_stringBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return templateModelEval instanceof TemplateScalarModel ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class is_transformBI extends BuiltIn {
        is_transformBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            this.target.assertNonNull(templateModelEval, environment);
            return templateModelEval instanceof TemplateTransformModel ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    static class namespaceBI extends BuiltIn {
        namespaceBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws Throwable {
            Class clsClass$;
            TemplateModel templateModelEval = this.target.eval(environment);
            if (!(templateModelEval instanceof Macro)) {
                Expression expression = this.target;
                Class[] clsArr = new Class[1];
                if (BuiltInsForMultipleTypes.class$freemarker$core$Macro == null) {
                    clsClass$ = BuiltInsForMultipleTypes.class$("org.mapstruct.ap.shaded.freemarker.core.Macro");
                    BuiltInsForMultipleTypes.class$freemarker$core$Macro = clsClass$;
                } else {
                    clsClass$ = BuiltInsForMultipleTypes.class$freemarker$core$Macro;
                }
                clsArr[0] = clsClass$;
                throw new UnexpectedTypeException(expression, templateModelEval, "macro or function", clsArr, environment);
            }
            return environment.getMacroNamespace((Macro) templateModelEval);
        }
    }

    static class sizeBI extends BuiltIn {
        sizeBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws Throwable {
            Class clsClass$;
            Class clsClass$2;
            TemplateModel templateModelEval = this.target.eval(environment);
            if (templateModelEval instanceof TemplateSequenceModel) {
                return new SimpleNumber(((TemplateSequenceModel) templateModelEval).size());
            }
            if (templateModelEval instanceof TemplateHashModelEx) {
                return new SimpleNumber(((TemplateHashModelEx) templateModelEval).size());
            }
            Expression expression = this.target;
            Class[] clsArr = new Class[2];
            if (BuiltInsForMultipleTypes.class$freemarker$template$TemplateHashModelEx == null) {
                clsClass$ = BuiltInsForMultipleTypes.class$("org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx");
                BuiltInsForMultipleTypes.class$freemarker$template$TemplateHashModelEx = clsClass$;
            } else {
                clsClass$ = BuiltInsForMultipleTypes.class$freemarker$template$TemplateHashModelEx;
            }
            clsArr[0] = clsClass$;
            if (BuiltInsForMultipleTypes.class$freemarker$template$TemplateSequenceModel == null) {
                clsClass$2 = BuiltInsForMultipleTypes.class$("org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel");
                BuiltInsForMultipleTypes.class$freemarker$template$TemplateSequenceModel = clsClass$2;
            } else {
                clsClass$2 = BuiltInsForMultipleTypes.class$freemarker$template$TemplateSequenceModel;
            }
            clsArr[1] = clsClass$2;
            throw new UnexpectedTypeException(expression, templateModelEval, "extended-hash or sequence", clsArr, environment);
        }
    }

    static class stringBI extends BuiltIn {
        stringBI() {
        }

        private class BooleanFormatter implements TemplateScalarModel, TemplateMethodModel {
            private final TemplateBooleanModel bool;
            private final Environment env;

            BooleanFormatter(TemplateBooleanModel templateBooleanModel, Environment environment) {
                this.bool = templateBooleanModel;
                this.env = environment;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                stringBI.this.checkMethodArgCount(list, 2);
                return new SimpleScalar((String) list.get(!this.bool.getAsBoolean() ? 1 : 0));
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
            public String getAsString() throws TemplateModelException {
                TemplateBooleanModel templateBooleanModel = this.bool;
                if (templateBooleanModel instanceof TemplateScalarModel) {
                    return ((TemplateScalarModel) templateBooleanModel).getAsString();
                }
                try {
                    return this.env.formatBoolean(templateBooleanModel.getAsBoolean(), true);
                } catch (TemplateException e) {
                    throw new TemplateModelException((Exception) e);
                }
            }
        }

        private class DateFormatter implements TemplateScalarModel, TemplateHashModel, TemplateMethodModel {
            private String cachedValue;
            private final TemplateDateModel dateModel;
            private final TemplateDateFormat defaultFormat;
            private final Environment env;

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
            public boolean isEmpty() {
                return false;
            }

            DateFormatter(TemplateDateModel templateDateModel, Environment environment) throws TemplateModelException {
                this.dateModel = templateDateModel;
                this.env = environment;
                int dateType = templateDateModel.getDateType();
                this.defaultFormat = dateType == 0 ? null : environment.getTemplateDateFormat(dateType, EvalUtil.modelToDate(templateDateModel, stringBI.this.target).getClass(), stringBI.this.target);
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                stringBI.this.checkMethodArgCount(list, 1);
                return get((String) list.get(0));
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
            public TemplateModel get(String str) throws TemplateModelException {
                return new SimpleScalar(this.env.formatDate(this.dateModel, str, stringBI.this.target));
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
            public String getAsString() throws TemplateModelException {
                if (this.cachedValue == null) {
                    try {
                        TemplateDateFormat templateDateFormat = this.defaultFormat;
                        if (templateDateFormat == null) {
                            if (this.dateModel.getDateType() == 0) {
                                throw MessageUtil.newCantFormatUnknownTypeDateException(stringBI.this.target, null);
                            }
                            throw new BugException();
                        }
                        this.cachedValue = templateDateFormat.format(this.dateModel);
                    } catch (UnformattableDateException e) {
                        throw MessageUtil.newCantFormatDateException(stringBI.this.target, e);
                    }
                }
                return this.cachedValue;
            }
        }

        private class NumberFormatter implements TemplateScalarModel, TemplateHashModel, TemplateMethodModel {
            private String cachedValue;
            private final NumberFormat defaultFormat;
            private final Environment env;
            private final Number number;

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
            public boolean isEmpty() {
                return false;
            }

            NumberFormatter(Number number, Environment environment) {
                this.number = number;
                this.env = environment;
                this.defaultFormat = environment.getNumberFormatObject(environment.getNumberFormat());
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                stringBI.this.checkMethodArgCount(list, 1);
                return get((String) list.get(0));
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
            public TemplateModel get(String str) {
                return new SimpleScalar(this.env.getNumberFormatObject(str).format(this.number));
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
            public String getAsString() {
                if (this.cachedValue == null) {
                    this.cachedValue = this.defaultFormat.format(this.number);
                }
                return this.cachedValue;
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws Throwable {
            Class clsClass$;
            Class clsClass$2;
            Class clsClass$3;
            Class clsClass$4;
            TemplateModel templateModelEval = this.target.eval(environment);
            if (templateModelEval instanceof TemplateNumberModel) {
                return new NumberFormatter(EvalUtil.modelToNumber((TemplateNumberModel) templateModelEval, this.target), environment);
            }
            if (templateModelEval instanceof TemplateDateModel) {
                return new DateFormatter((TemplateDateModel) templateModelEval, environment);
            }
            if (templateModelEval instanceof SimpleScalar) {
                return templateModelEval;
            }
            if (templateModelEval instanceof TemplateBooleanModel) {
                return new BooleanFormatter((TemplateBooleanModel) templateModelEval, environment);
            }
            if (templateModelEval instanceof TemplateScalarModel) {
                return new SimpleScalar(((TemplateScalarModel) templateModelEval).getAsString());
            }
            if (environment.isClassicCompatible() && (templateModelEval instanceof BeanModel)) {
                return new SimpleScalar(_BeansAPI.getAsClassicCompatibleString((BeanModel) templateModelEval));
            }
            Expression expression = this.target;
            Class[] clsArr = new Class[4];
            if (BuiltInsForMultipleTypes.class$freemarker$template$TemplateNumberModel == null) {
                clsClass$ = BuiltInsForMultipleTypes.class$("org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel");
                BuiltInsForMultipleTypes.class$freemarker$template$TemplateNumberModel = clsClass$;
            } else {
                clsClass$ = BuiltInsForMultipleTypes.class$freemarker$template$TemplateNumberModel;
            }
            clsArr[0] = clsClass$;
            if (BuiltInsForMultipleTypes.class$freemarker$template$TemplateDateModel == null) {
                clsClass$2 = BuiltInsForMultipleTypes.class$("org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel");
                BuiltInsForMultipleTypes.class$freemarker$template$TemplateDateModel = clsClass$2;
            } else {
                clsClass$2 = BuiltInsForMultipleTypes.class$freemarker$template$TemplateDateModel;
            }
            clsArr[1] = clsClass$2;
            if (BuiltInsForMultipleTypes.class$freemarker$template$TemplateBooleanModel == null) {
                clsClass$3 = BuiltInsForMultipleTypes.class$("org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel");
                BuiltInsForMultipleTypes.class$freemarker$template$TemplateBooleanModel = clsClass$3;
            } else {
                clsClass$3 = BuiltInsForMultipleTypes.class$freemarker$template$TemplateBooleanModel;
            }
            clsArr[2] = clsClass$3;
            if (BuiltInsForMultipleTypes.class$freemarker$template$TemplateScalarModel == null) {
                clsClass$4 = BuiltInsForMultipleTypes.class$("org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel");
                BuiltInsForMultipleTypes.class$freemarker$template$TemplateScalarModel = clsClass$4;
            } else {
                clsClass$4 = BuiltInsForMultipleTypes.class$freemarker$template$TemplateScalarModel;
            }
            clsArr[3] = clsClass$4;
            throw new UnexpectedTypeException(expression, templateModelEval, "number, date, boolean or string", clsArr, environment);
        }
    }

    private BuiltInsForMultipleTypes() {
    }

    static abstract class AbstractCBI extends BuiltIn {
        protected abstract TemplateModel formatNumber(Environment environment, TemplateModel templateModel) throws TemplateModelException;

        AbstractCBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws Throwable {
            Class clsClass$;
            Class clsClass$2;
            TemplateModel templateModelEval = this.target.eval(environment);
            if (templateModelEval instanceof TemplateNumberModel) {
                return formatNumber(environment, templateModelEval);
            }
            if (templateModelEval instanceof TemplateBooleanModel) {
                return new SimpleScalar(((TemplateBooleanModel) templateModelEval).getAsBoolean() ? BooleanUtils.TRUE : BooleanUtils.FALSE);
            }
            Expression expression = this.target;
            Class[] clsArr = new Class[2];
            if (BuiltInsForMultipleTypes.class$freemarker$template$TemplateNumberModel == null) {
                clsClass$ = BuiltInsForMultipleTypes.class$("org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel");
                BuiltInsForMultipleTypes.class$freemarker$template$TemplateNumberModel = clsClass$;
            } else {
                clsClass$ = BuiltInsForMultipleTypes.class$freemarker$template$TemplateNumberModel;
            }
            clsArr[0] = clsClass$;
            if (BuiltInsForMultipleTypes.class$freemarker$template$TemplateBooleanModel == null) {
                clsClass$2 = BuiltInsForMultipleTypes.class$("org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel");
                BuiltInsForMultipleTypes.class$freemarker$template$TemplateBooleanModel = clsClass$2;
            } else {
                clsClass$2 = BuiltInsForMultipleTypes.class$freemarker$template$TemplateBooleanModel;
            }
            clsArr[1] = clsClass$2;
            throw new UnexpectedTypeException(expression, templateModelEval, "number or boolean", clsArr, environment);
        }
    }
}
