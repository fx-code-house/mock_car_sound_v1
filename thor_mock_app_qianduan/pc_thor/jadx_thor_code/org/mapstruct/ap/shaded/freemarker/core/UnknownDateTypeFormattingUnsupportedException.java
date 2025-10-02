package org.mapstruct.ap.shaded.freemarker.core;

/* loaded from: classes3.dex */
final class UnknownDateTypeFormattingUnsupportedException extends UnformattableDateException {
    public UnknownDateTypeFormattingUnsupportedException() {
        super("Can't convert the date-like value to string because it isn't known if it's a date (no time part), time or date-time value.");
    }
}
