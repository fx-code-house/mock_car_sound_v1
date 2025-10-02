package org.mapstruct.ap.internal.conversion;

/* loaded from: classes3.dex */
public class JavaZonedDateTimeToStringConversion extends AbstractJavaTimeToStringConversion {
    @Override // org.mapstruct.ap.internal.conversion.AbstractJavaTimeToStringConversion
    protected String defaultFormatterSuffix() {
        return "ISO_DATE_TIME";
    }
}
