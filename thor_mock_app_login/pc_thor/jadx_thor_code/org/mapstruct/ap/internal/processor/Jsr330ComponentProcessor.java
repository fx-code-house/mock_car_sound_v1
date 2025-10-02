package org.mapstruct.ap.internal.processor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kotlin.text.Typography;
import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.Mapper;

/* loaded from: classes3.dex */
public class Jsr330ComponentProcessor extends AnnotationBasedComponentModelProcessor {
    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected String getComponentModelIdentifier() {
        return "jsr330";
    }

    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected boolean requiresGenerationOfDecoratorClass() {
        return true;
    }

    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        return mapper.getDecorator() == null ? Arrays.asList(singleton(), named()) : Arrays.asList(singleton(), namedDelegate(mapper));
    }

    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected List<Annotation> getDecoratorAnnotations() {
        return Arrays.asList(singleton(), named());
    }

    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected List<Annotation> getDelegatorReferenceAnnotations(Mapper mapper) {
        return Arrays.asList(inject(), namedDelegate(mapper));
    }

    @Override // org.mapstruct.ap.internal.processor.AnnotationBasedComponentModelProcessor
    protected List<Annotation> getMapperReferenceAnnotations() {
        return Collections.singletonList(inject());
    }

    private Annotation singleton() {
        return new Annotation(getTypeFactory().getType("javax.inject.Singleton"));
    }

    private Annotation named() {
        return new Annotation(getTypeFactory().getType("javax.inject.Named"));
    }

    private Annotation namedDelegate(Mapper mapper) {
        return new Annotation(getTypeFactory().getType("javax.inject.Named"), Collections.singletonList("\"" + mapper.getPackageName() + "." + mapper.getName() + Typography.quote));
    }

    private Annotation inject() {
        return new Annotation(getTypeFactory().getType("javax.inject.Inject"));
    }
}
