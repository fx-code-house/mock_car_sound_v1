package org.mapstruct.ap.shaded.freemarker.core;

import java.util.Date;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
abstract class TemplateDateFormat {
    public abstract String format(TemplateDateModel templateDateModel) throws TemplateModelException, UnformattableDateException;

    public abstract String getDescription();

    public abstract boolean isLocaleBound();

    public abstract Date parse(String str) throws java.text.ParseException;

    TemplateDateFormat() {
    }
}
