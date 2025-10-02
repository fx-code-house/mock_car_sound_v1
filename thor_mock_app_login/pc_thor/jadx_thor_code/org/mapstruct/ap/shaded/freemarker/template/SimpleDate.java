package org.mapstruct.ap.shaded.freemarker.template;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/* loaded from: classes3.dex */
public class SimpleDate implements TemplateDateModel {
    private final Date date;
    private final int type;

    public SimpleDate(java.sql.Date date) {
        this(date, 2);
    }

    public SimpleDate(Time time) {
        this(time, 1);
    }

    public SimpleDate(Timestamp timestamp) {
        this(timestamp, 3);
    }

    public SimpleDate(Date date, int i) {
        if (date == null) {
            throw new IllegalArgumentException("date == null");
        }
        this.date = date;
        this.type = i;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel
    public Date getAsDate() {
        return this.date;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel
    public int getDateType() {
        return this.type;
    }

    public String toString() {
        return this.date.toString();
    }
}
