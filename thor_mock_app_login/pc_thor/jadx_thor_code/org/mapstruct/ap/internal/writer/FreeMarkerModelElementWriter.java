package org.mapstruct.ap.internal.writer;

import java.io.Writer;
import java.util.Map;
import org.mapstruct.ap.internal.writer.Writable;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper;
import org.mapstruct.ap.shaded.freemarker.ext.beans.SimpleMapModel;
import org.mapstruct.ap.shaded.freemarker.template.Configuration;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
public class FreeMarkerModelElementWriter {
    public void write(FreeMarkerWritable freeMarkerWritable, Writable.Context context, Writer writer) throws Exception {
        ((Configuration) context.get(Configuration.class)).getTemplate(freeMarkerWritable.getTemplateName()).process(new ExternalParamsTemplateModel(new BeanModel(freeMarkerWritable, BeansWrapper.getDefaultInstance()), new SimpleMapModel((Map) context.get(Map.class), BeansWrapper.getDefaultInstance())), writer);
    }

    private static class ExternalParamsTemplateModel implements TemplateHashModel {
        private final SimpleMapModel extParams;
        private final BeanModel object;

        ExternalParamsTemplateModel(BeanModel beanModel, SimpleMapModel simpleMapModel) {
            this.object = beanModel;
            this.extParams = simpleMapModel;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public TemplateModel get(String str) throws TemplateModelException {
            if (str.equals("ext")) {
                return this.extParams;
            }
            return this.object.get(str);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public boolean isEmpty() throws TemplateModelException {
            return this.object.isEmpty() && this.extParams.isEmpty();
        }
    }
}
