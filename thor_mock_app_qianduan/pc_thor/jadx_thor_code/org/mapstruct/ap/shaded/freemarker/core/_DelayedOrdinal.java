package org.mapstruct.ap.shaded.freemarker.core;

/* loaded from: classes3.dex */
public class _DelayedOrdinal extends _DelayedConversionToString {
    public _DelayedOrdinal(Object obj) {
        super(obj);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core._DelayedConversionToString
    protected String doConversion(Object obj) {
        if (obj instanceof Number) {
            long jLongValue = ((Number) obj).longValue();
            long j = jLongValue % 10;
            if (j == 1 && jLongValue % 100 != 11) {
                return new StringBuffer().append(jLongValue).append("st").toString();
            }
            if (j == 2 && jLongValue % 100 != 12) {
                return new StringBuffer().append(jLongValue).append("nd").toString();
            }
            if (j == 3 && jLongValue % 100 != 13) {
                return new StringBuffer().append(jLongValue).append("rd").toString();
            }
            return new StringBuffer().append(jLongValue).append("th").toString();
        }
        return new StringBuffer("").append(obj).toString();
    }
}
