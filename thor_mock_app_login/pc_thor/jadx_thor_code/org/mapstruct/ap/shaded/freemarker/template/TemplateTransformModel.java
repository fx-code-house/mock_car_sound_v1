package org.mapstruct.ap.shaded.freemarker.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/* loaded from: classes3.dex */
public interface TemplateTransformModel extends TemplateModel {
    Writer getWriter(Writer writer, Map map) throws TemplateModelException, IOException;
}
