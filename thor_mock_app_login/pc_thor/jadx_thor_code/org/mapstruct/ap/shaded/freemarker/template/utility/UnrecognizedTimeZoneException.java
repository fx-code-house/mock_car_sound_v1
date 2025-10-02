package org.mapstruct.ap.shaded.freemarker.template.utility;

/* loaded from: classes3.dex */
public class UnrecognizedTimeZoneException extends Exception {
    private final String timeZoneName;

    public UnrecognizedTimeZoneException(String str) {
        super(new StringBuffer("Unrecognized time zone: ").append(StringUtil.jQuote(str)).toString());
        this.timeZoneName = str;
    }

    public String getTimeZoneName() {
        return this.timeZoneName;
    }
}
