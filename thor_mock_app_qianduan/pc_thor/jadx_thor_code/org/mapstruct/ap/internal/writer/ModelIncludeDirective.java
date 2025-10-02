package org.mapstruct.ap.internal.writer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.mapstruct.ap.internal.writer.ModelWriter;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel;
import org.mapstruct.ap.shaded.freemarker.template.Configuration;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDirectiveBody;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDirectiveModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
public class ModelIncludeDirective implements TemplateDirectiveModel {
    private final Configuration configuration;

    public ModelIncludeDirective(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateDirectiveModel
    public void execute(Environment environment, Map map, TemplateModel[] templateModelArr, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        Writable modelElement = getModelElement(map);
        ModelWriter.DefaultModelElementWriterContext defaultModelElementWriterContextCreateContext = createContext(map);
        if (modelElement != null) {
            try {
                modelElement.write(defaultModelElementWriterContextCreateContext, environment.getOut());
            } catch (IOException e) {
            } catch (RuntimeException e2) {
            } catch (TemplateException e3) {
                throw e3;
            } catch (Exception e4) {
                throw new RuntimeException(e4);
            }
        }
    }

    private Writable getModelElement(Map map) {
        if (!map.containsKey("object")) {
            throw new IllegalArgumentException("Object to be included must be passed to this directive via the 'object' parameter");
        }
        BeanModel beanModel = (BeanModel) map.get("object");
        if (beanModel == null) {
            return null;
        }
        if (!(beanModel.getWrappedObject() instanceof Writable)) {
            throw new IllegalArgumentException("Given object isn't a Writable:" + beanModel.getWrappedObject());
        }
        return (Writable) beanModel.getWrappedObject();
    }

    private ModelWriter.DefaultModelElementWriterContext createContext(Map map) {
        HashMap map2 = new HashMap(map);
        map2.remove("object");
        HashMap map3 = new HashMap();
        map3.put(Configuration.class, this.configuration);
        map3.put(Map.class, map2);
        return new ModelWriter.DefaultModelElementWriterContext(map3);
    }
}
