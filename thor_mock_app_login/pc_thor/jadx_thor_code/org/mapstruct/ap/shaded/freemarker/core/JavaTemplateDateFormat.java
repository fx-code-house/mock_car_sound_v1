package org.mapstruct.ap.shaded.freemarker.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
class JavaTemplateDateFormat extends TemplateDateFormat {
    private final DateFormat javaDateFormat;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateDateFormat
    public boolean isLocaleBound() {
        return true;
    }

    public JavaTemplateDateFormat(DateFormat dateFormat) {
        this.javaDateFormat = dateFormat;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateDateFormat
    public String format(TemplateDateModel templateDateModel) throws TemplateModelException {
        return this.javaDateFormat.format(templateDateModel.getAsDate());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateDateFormat
    public Date parse(String str) throws java.text.ParseException {
        return this.javaDateFormat.parse(str);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateDateFormat
    public String getDescription() {
        DateFormat dateFormat = this.javaDateFormat;
        if (dateFormat instanceof SimpleDateFormat) {
            return ((SimpleDateFormat) dateFormat).toPattern();
        }
        return dateFormat.toString();
    }
}
