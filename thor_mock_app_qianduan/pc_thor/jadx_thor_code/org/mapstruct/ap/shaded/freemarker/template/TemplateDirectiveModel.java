package org.mapstruct.ap.shaded.freemarker.template;

import java.io.IOException;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.core.Environment;

/* loaded from: classes3.dex */
public interface TemplateDirectiveModel extends TemplateModel {
    void execute(Environment environment, Map map, TemplateModel[] templateModelArr, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException;
}
