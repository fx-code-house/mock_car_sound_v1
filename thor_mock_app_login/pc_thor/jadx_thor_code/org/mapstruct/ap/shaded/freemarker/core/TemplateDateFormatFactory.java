package org.mapstruct.ap.shaded.freemarker.core;

import java.util.TimeZone;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
abstract class TemplateDateFormatFactory {
    private final TimeZone timeZone;

    public abstract TemplateDateFormat get(int i, boolean z, String str) throws UnknownDateTypeFormattingUnsupportedException, TemplateModelException, java.text.ParseException;

    public abstract boolean isLocaleBound();

    public TemplateDateFormatFactory(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }
}
