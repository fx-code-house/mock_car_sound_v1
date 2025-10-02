package org.mapstruct.ap.shaded.freemarker.core;

import java.sql.Time;
import java.util.Date;
import java.util.TimeZone;
import org.mapstruct.ap.shaded.freemarker.template.SimpleDate;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template._TemplateAPI;
import org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil;

/* loaded from: classes3.dex */
class BuiltInsForDates {
    static /* synthetic */ Class class$java$util$TimeZone;

    static class dateType_if_unknownBI extends BuiltIn {
        private final int dateType;

        protected TemplateModel calculateResult(Date date, int i, Environment environment) throws TemplateException {
            return null;
        }

        dateType_if_unknownBI(int i) {
            this.dateType = i;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            if (templateModelEval instanceof TemplateDateModel) {
                TemplateDateModel templateDateModel = (TemplateDateModel) templateModelEval;
                return templateDateModel.getDateType() != 0 ? templateDateModel : new SimpleDate(EvalUtil.modelToDate(templateDateModel, this.target), this.dateType);
            }
            throw BuiltInForDate.newNonDateException(environment, templateModelEval, this.target);
        }
    }

    static class iso_BI extends AbstractISOBI {

        class Result implements TemplateMethodModelEx {
            private final Date date;
            private final int dateType;
            private final Environment env;

            Result(Date date, int i, Environment environment) {
                this.date = date;
                this.dateType = i;
                this.env = environment;
            }

            /* JADX WARN: Removed duplicated region for block: B:12:0x0030  */
            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public java.lang.Object exec(java.util.List r12) throws java.lang.Throwable {
                /*
                    r11 = this;
                    org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates$iso_BI r0 = org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates.iso_BI.this
                    r1 = 1
                    r0.checkMethodArgCount(r12, r1)
                    r0 = 0
                    java.lang.Object r12 = r12.get(r0)
                    org.mapstruct.ap.shaded.freemarker.template.TemplateModel r12 = (org.mapstruct.ap.shaded.freemarker.template.TemplateModel) r12
                    boolean r2 = r12 instanceof org.mapstruct.ap.shaded.freemarker.template.AdapterTemplateModel
                    r3 = 2
                    if (r2 == 0) goto L30
                    r2 = r12
                    org.mapstruct.ap.shaded.freemarker.template.AdapterTemplateModel r2 = (org.mapstruct.ap.shaded.freemarker.template.AdapterTemplateModel) r2
                    java.lang.Class r4 = org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates.class$java$util$TimeZone
                    if (r4 != 0) goto L22
                    java.lang.String r4 = "java.util.TimeZone"
                    java.lang.Class r4 = org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates.class$(r4)
                    org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates.class$java$util$TimeZone = r4
                    goto L24
                L22:
                    java.lang.Class r4 = org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates.class$java$util$TimeZone
                L24:
                    java.lang.Object r2 = r2.getAdaptedObject(r4)
                    boolean r4 = r2 instanceof java.util.TimeZone
                    if (r4 == 0) goto L30
                    java.util.TimeZone r2 = (java.util.TimeZone) r2
                L2e:
                    r9 = r2
                    goto L40
                L30:
                    boolean r2 = r12 instanceof org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
                    if (r2 == 0) goto L89
                    org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel r12 = (org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel) r12
                    r2 = 0
                    java.lang.String r12 = org.mapstruct.ap.shaded.freemarker.core.EvalUtil.modelToString(r12, r2, r2)
                    java.util.TimeZone r2 = org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil.getTimeZone(r12)     // Catch: org.mapstruct.ap.shaded.freemarker.template.utility.UnrecognizedTimeZoneException -> L6a
                    goto L2e
                L40:
                    org.mapstruct.ap.shaded.freemarker.template.SimpleScalar r12 = new org.mapstruct.ap.shaded.freemarker.template.SimpleScalar
                    java.util.Date r4 = r11.date
                    int r2 = r11.dateType
                    if (r2 == r1) goto L4a
                    r5 = r1
                    goto L4b
                L4a:
                    r5 = r0
                L4b:
                    if (r2 == r3) goto L4f
                    r6 = r1
                    goto L50
                L4f:
                    r6 = r0
                L50:
                    org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates$iso_BI r0 = org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates.iso_BI.this
                    org.mapstruct.ap.shaded.freemarker.core.Environment r1 = r11.env
                    boolean r7 = r0.shouldShowOffset(r4, r2, r1)
                    org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates$iso_BI r0 = org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates.iso_BI.this
                    int r8 = r0.accuracy
                    org.mapstruct.ap.shaded.freemarker.core.Environment r0 = r11.env
                    org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil$DateToISO8601CalendarFactory r10 = r0.getISOBuiltInCalendarFactory()
                    java.lang.String r0 = org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil.dateToISO8601String(r4, r5, r6, r7, r8, r9, r10)
                    r12.<init>(r0)
                    return r12
                L6a:
                    org.mapstruct.ap.shaded.freemarker.core._TemplateModelException r2 = new org.mapstruct.ap.shaded.freemarker.core._TemplateModelException
                    r4 = 4
                    java.lang.Object[] r4 = new java.lang.Object[r4]
                    java.lang.String r5 = "The time zone string specified for ?"
                    r4[r0] = r5
                    org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates$iso_BI r0 = org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates.iso_BI.this
                    java.lang.String r0 = r0.key
                    r4[r1] = r0
                    java.lang.String r0 = "(...) is not recognized as a valid time zone name: "
                    r4[r3] = r0
                    org.mapstruct.ap.shaded.freemarker.core._DelayedJQuote r0 = new org.mapstruct.ap.shaded.freemarker.core._DelayedJQuote
                    r0.<init>(r12)
                    r12 = 3
                    r4[r12] = r0
                    r2.<init>(r4)
                    throw r2
                L89:
                    java.lang.StringBuffer r1 = new java.lang.StringBuffer
                    java.lang.String r2 = "?"
                    r1.<init>(r2)
                    org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates$iso_BI r2 = org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates.iso_BI.this
                    java.lang.String r2 = r2.key
                    java.lang.StringBuffer r1 = r1.append(r2)
                    java.lang.String r1 = r1.toString()
                    java.lang.String r2 = "string or java.util.TimeZone"
                    org.mapstruct.ap.shaded.freemarker.template.TemplateModelException r12 = org.mapstruct.ap.shaded.freemarker.core.MessageUtil.newMethodArgUnexpectedTypeException(r1, r0, r2, r12)
                    throw r12
                */
                throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates.iso_BI.Result.exec(java.util.List):java.lang.Object");
            }
        }

        iso_BI(Boolean bool, int i) {
            super(bool, i);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForDate
        protected TemplateModel calculateResult(Date date, int i, Environment environment) throws TemplateException {
            checkDateTypeNotUnknown(i);
            return new Result(date, i, environment);
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static class iso_utc_or_local_BI extends AbstractISOBI {
        private final boolean useUTC;

        iso_utc_or_local_BI(Boolean bool, int i, boolean z) {
            super(bool, i);
            this.useUTC = z;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForDate
        protected TemplateModel calculateResult(Date date, int i, Environment environment) throws TemplateException {
            TimeZone timeZone;
            checkDateTypeNotUnknown(i);
            boolean z = i != 1;
            boolean z2 = i != 2;
            boolean zShouldShowOffset = shouldShowOffset(date, i, environment);
            int i2 = this.accuracy;
            if (this.useUTC) {
                timeZone = DateUtil.UTC;
            } else if (environment.shouldUseSQLDTTZ(date.getClass())) {
                timeZone = environment.getSQLDateAndTimeTimeZone();
            } else {
                timeZone = environment.getTimeZone();
            }
            return new SimpleScalar(DateUtil.dateToISO8601String(date, z, z2, zShouldShowOffset, i2, timeZone, environment.getISOBuiltInCalendarFactory()));
        }
    }

    private BuiltInsForDates() {
    }

    static abstract class AbstractISOBI extends BuiltInForDate {
        protected final int accuracy;
        protected final Boolean showOffset;

        protected AbstractISOBI(Boolean bool, int i) {
            this.showOffset = bool;
            this.accuracy = i;
        }

        protected void checkDateTypeNotUnknown(int i) throws TemplateException {
            if (i == 0) {
                throw new _MiscTemplateException(new _ErrorDescriptionBuilder(new Object[]{"The value of the following has unknown date type, but ?", this.key, " needs a value where it's known if it's a date (no time part), time, or date-time value:"}).blame(this.target).tip("Use ?date, ?time, or ?datetime to tell FreeMarker the exact type."));
            }
        }

        protected boolean shouldShowOffset(Date date, int i, Environment environment) {
            if (i == 2) {
                return false;
            }
            Boolean bool = this.showOffset;
            if (bool != null) {
                return bool.booleanValue();
            }
            return !(date instanceof Time) || _TemplateAPI.getTemplateLanguageVersionAsInt(this) < _TemplateAPI.VERSION_INT_2_3_21;
        }
    }
}
