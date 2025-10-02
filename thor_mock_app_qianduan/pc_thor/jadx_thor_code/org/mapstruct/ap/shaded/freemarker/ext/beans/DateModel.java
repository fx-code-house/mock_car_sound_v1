package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
public class DateModel extends BeanModel implements TemplateDateModel {
    static final ModelFactory FACTORY = new ModelFactory() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.DateModel.1
        @Override // org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory
        public TemplateModel create(Object obj, ObjectWrapper objectWrapper) {
            return new DateModel((Date) obj, (BeansWrapper) objectWrapper);
        }
    };
    private final int type;

    public DateModel(Date date, BeansWrapper beansWrapper) {
        super(date, beansWrapper);
        if (date instanceof java.sql.Date) {
            this.type = 2;
            return;
        }
        if (date instanceof Time) {
            this.type = 1;
        } else if (date instanceof Timestamp) {
            this.type = 3;
        } else {
            this.type = beansWrapper.getDefaultDateType();
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel
    public Date getAsDate() {
        return (Date) this.object;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel
    public int getDateType() {
        return this.type;
    }
}
