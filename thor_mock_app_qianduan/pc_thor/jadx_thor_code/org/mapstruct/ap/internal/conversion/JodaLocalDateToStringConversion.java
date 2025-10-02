package org.mapstruct.ap.internal.conversion;

/* loaded from: classes3.dex */
public class JodaLocalDateToStringConversion extends AbstractJodaTypeToStringConversion {
    @Override // org.mapstruct.ap.internal.conversion.AbstractJodaTypeToStringConversion
    protected String formatStyle() {
        return "L-";
    }

    @Override // org.mapstruct.ap.internal.conversion.AbstractJodaTypeToStringConversion
    protected String parseMethod() {
        return "parseLocalDate";
    }
}
