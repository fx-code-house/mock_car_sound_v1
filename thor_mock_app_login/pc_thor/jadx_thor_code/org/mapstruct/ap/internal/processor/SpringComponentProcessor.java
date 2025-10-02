package org.mapstruct.ap.internal.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.Mapper;

/* loaded from: classes3.dex */
public class SpringComponentProcessor extends AnnotationBasedComponentModelProcessor {
    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected String getComponentModelIdentifier() {
        return "spring";
    }

    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected boolean requiresGenerationOfDecoratorClass() {
        return true;
    }

    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(component());
        if (mapper.getDecorator() != null) {
            arrayList.add(qualifierDelegate());
        }
        return arrayList;
    }

    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected List<Annotation> getDecoratorAnnotations() {
        return Arrays.asList(component(), primary());
    }

    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected List<Annotation> getMapperReferenceAnnotations() {
        return Collections.singletonList(autowired());
    }

    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected List<Annotation> getDelegatorReferenceAnnotations(Mapper mapper) {
        return Arrays.asList(autowired(), qualifierDelegate());
    }

    private Annotation autowired() {
        return new Annotation(getTypeFactory().getType("org.springframework.beans.factory.annotation.Autowired"));
    }

    private Annotation qualifierDelegate() {
        return new Annotation(getTypeFactory().getType("org.springframework.beans.factory.annotation.Qualifier"), Collections.singletonList("\"delegate\""));
    }

    private Annotation primary() {
        return new Annotation(getTypeFactory().getType("org.springframework.context.annotation.Primary"));
    }

    private Annotation component() {
        return new Annotation(getTypeFactory().getType("org.springframework.stereotype.Component"));
    }
}
