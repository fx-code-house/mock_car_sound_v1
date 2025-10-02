package org.mapstruct.ap.internal.processor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.Mapper;

/* loaded from: classes3.dex */
public class CdiComponentProcessor extends AnnotationBasedComponentModelProcessor {
    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected boolean additionalPublicEmptyConstructor() {
        return true;
    }

    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected String getComponentModelIdentifier() {
        return "cdi";
    }

    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected boolean requiresGenerationOfDecoratorClass() {
        return false;
    }

    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        return Collections.singletonList(new Annotation(getTypeFactory().getType("javax.enterprise.context.ApplicationScoped")));
    }

    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected List<Annotation> getMapperReferenceAnnotations() {
        return Arrays.asList(new Annotation(getTypeFactory().getType("javax.inject.Inject")));
    }
}
