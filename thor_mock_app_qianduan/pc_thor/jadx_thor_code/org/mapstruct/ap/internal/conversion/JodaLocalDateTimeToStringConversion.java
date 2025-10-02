package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.util.JodaTimeConstants;

/* loaded from: classes3.dex */
public class JodaLocalDateTimeToStringConversion extends AbstractJodaTypeToStringConversion {
    @Override // org.mapstruct.ap.internal.conversion.AbstractJodaTypeToStringConversion
    protected String formatStyle() {
        return JodaTimeConstants.DATE_TIME_FORMAT;
    }

    @Override // org.mapstruct.ap.internal.conversion.AbstractJodaTypeToStringConversion
    protected String parseMethod() {
        return "parseLocalDateTime";
    }
}
