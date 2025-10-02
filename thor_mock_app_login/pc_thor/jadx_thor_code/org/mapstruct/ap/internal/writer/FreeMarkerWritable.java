package org.mapstruct.ap.internal.writer;

import java.io.Writer;
import org.apache.commons.lang3.ClassUtils;
import org.mapstruct.ap.internal.writer.Writable;

/* loaded from: classes3.dex */
public abstract class FreeMarkerWritable implements Writable {
    @Override // org.mapstruct.ap.internal.writer.Writable
    public void write(Writable.Context context, Writer writer) throws Exception {
        new FreeMarkerModelElementWriter().write(this, context, writer);
    }

    protected String getTemplateName() {
        return getTemplateNameForClass(getClass());
    }

    protected String getTemplateNameForClass(Class<?> cls) {
        return cls.getName().replace(ClassUtils.PACKAGE_SEPARATOR_CHAR, '/') + ".ftl";
    }
}
