package org.mapstruct.ap.internal.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.lang.model.element.TypeElement;
import org.mapstruct.ap.internal.gem.InjectionStrategyGem;
import org.mapstruct.ap.internal.model.AnnotatedConstructor;
import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.AnnotationMapperReference;
import org.mapstruct.ap.internal.model.Decorator;
import org.mapstruct.ap.internal.model.Field;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.MapperReference;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MapperOptions;
import org.mapstruct.ap.internal.processor.ModelElementProcessor;

/* loaded from: classes3.dex */
public abstract class AnnotationBasedComponentModelProcessor implements ModelElementProcessor<Mapper, Mapper> {
    private TypeFactory typeFactory;

    protected boolean additionalPublicEmptyConstructor() {
        return false;
    }

    protected abstract String getComponentModelIdentifier();

    protected abstract List<Annotation> getMapperReferenceAnnotations();

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor
    public int getPriority() {
        return 1100;
    }

    protected abstract List<Annotation> getTypeAnnotations(Mapper mapper);

    protected abstract boolean requiresGenerationOfDecoratorClass();

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor
    public Mapper process(ModelElementProcessor.ProcessorContext processorContext, TypeElement typeElement, Mapper mapper) {
        this.typeFactory = processorContext.getTypeFactory();
        MapperOptions instanceOn = MapperOptions.getInstanceOn(typeElement, processorContext.getOptions());
        String strComponentModel = instanceOn.componentModel();
        InjectionStrategyGem injectionStrategy = instanceOn.getInjectionStrategy();
        if (!getComponentModelIdentifier().equalsIgnoreCase(strComponentModel)) {
            return mapper;
        }
        Iterator<Annotation> it = getTypeAnnotations(mapper).iterator();
        while (it.hasNext()) {
            mapper.addAnnotation(it.next());
        }
        if (!requiresGenerationOfDecoratorClass()) {
            mapper.removeDecorator();
        } else if (mapper.getDecorator() != null) {
            adjustDecorator(mapper, injectionStrategy);
        }
        List<Annotation> mapperReferenceAnnotations = getMapperReferenceAnnotations();
        ListIterator<Field> listIterator = mapper.getFields().listIterator();
        while (listIterator.hasNext()) {
            Field next = listIterator.next();
            if (next instanceof MapperReference) {
                listIterator.remove();
                listIterator.add(replacementMapperReference(next, mapperReferenceAnnotations, injectionStrategy));
            }
        }
        if (injectionStrategy == InjectionStrategyGem.CONSTRUCTOR) {
            buildConstructors(mapper);
        }
        return mapper;
    }

    protected void adjustDecorator(Mapper mapper, InjectionStrategyGem injectionStrategyGem) {
        Decorator decorator = mapper.getDecorator();
        Iterator<Annotation> it = getDecoratorAnnotations().iterator();
        while (it.hasNext()) {
            decorator.addAnnotation(it.next());
        }
        decorator.removeConstructor();
        List<Annotation> delegatorReferenceAnnotations = getDelegatorReferenceAnnotations(mapper);
        ArrayList arrayList = new ArrayList();
        if (!decorator.getMethods().isEmpty()) {
            Iterator<Field> it2 = decorator.getFields().iterator();
            while (it2.hasNext()) {
                arrayList.add(replacementMapperReference(it2.next(), delegatorReferenceAnnotations, injectionStrategyGem));
            }
        }
        decorator.setFields(arrayList);
    }

    private List<MapperReference> toMapperReferences(List<Field> list) {
        ArrayList arrayList = new ArrayList();
        for (Field field : list) {
            if (field instanceof MapperReference) {
                arrayList.add((MapperReference) field);
            }
        }
        return arrayList;
    }

    private void buildConstructors(Mapper mapper) {
        if (!toMapperReferences(mapper.getFields()).isEmpty()) {
            AnnotatedConstructor annotatedConstructorBuildAnnotatedConstructorForMapper = buildAnnotatedConstructorForMapper(mapper);
            if (!annotatedConstructorBuildAnnotatedConstructorForMapper.getMapperReferences().isEmpty()) {
                mapper.setConstructor(annotatedConstructorBuildAnnotatedConstructorForMapper);
            }
        }
        Decorator decorator = mapper.getDecorator();
        if (decorator != null) {
            AnnotatedConstructor annotatedConstructorBuildAnnotatedConstructorForDecorator = buildAnnotatedConstructorForDecorator(decorator);
            if (annotatedConstructorBuildAnnotatedConstructorForDecorator.getMapperReferences().isEmpty()) {
                return;
            }
            decorator.setConstructor(annotatedConstructorBuildAnnotatedConstructorForDecorator);
        }
    }

    private AnnotatedConstructor buildAnnotatedConstructorForMapper(Mapper mapper) {
        List<MapperReference> mapperReferences = toMapperReferences(mapper.getFields());
        ArrayList arrayList = new ArrayList(mapperReferences.size());
        for (MapperReference mapperReference : mapperReferences) {
            if (mapperReference.isUsed()) {
                arrayList.add((AnnotationMapperReference) mapperReference);
            }
        }
        List<Annotation> mapperReferenceAnnotations = getMapperReferenceAnnotations();
        removeDuplicateAnnotations(arrayList, mapperReferenceAnnotations);
        return AnnotatedConstructor.forComponentModels(mapper.getName(), arrayList, mapperReferenceAnnotations, mapper.getConstructor(), additionalPublicEmptyConstructor());
    }

    private AnnotatedConstructor buildAnnotatedConstructorForDecorator(Decorator decorator) {
        ArrayList arrayList = new ArrayList(decorator.getFields().size());
        for (Field field : decorator.getFields()) {
            if (field instanceof AnnotationMapperReference) {
                arrayList.add((AnnotationMapperReference) field);
            }
        }
        List<Annotation> mapperReferenceAnnotations = getMapperReferenceAnnotations();
        removeDuplicateAnnotations(arrayList, mapperReferenceAnnotations);
        return AnnotatedConstructor.forComponentModels(decorator.getName(), arrayList, mapperReferenceAnnotations, decorator.getConstructor(), additionalPublicEmptyConstructor());
    }

    private void removeDuplicateAnnotations(List<AnnotationMapperReference> list, List<Annotation> list2) {
        ListIterator<AnnotationMapperReference> listIterator = list.listIterator();
        HashSet hashSet = new HashSet();
        Iterator<Annotation> it = list2.iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().getType());
        }
        while (listIterator.hasNext()) {
            AnnotationMapperReference next = listIterator.next();
            listIterator.remove();
            ArrayList arrayList = new ArrayList();
            for (Annotation annotation : next.getAnnotations()) {
                if (!hashSet.contains(annotation.getType())) {
                    arrayList.add(annotation);
                }
            }
            listIterator.add(next.withNewAnnotations(arrayList));
        }
    }

    protected List<Annotation> getDelegatorReferenceAnnotations(Mapper mapper) {
        return Collections.emptyList();
    }

    protected Field replacementMapperReference(Field field, List<Annotation> list, InjectionStrategyGem injectionStrategyGem) {
        return new AnnotationMapperReference(field.getType(), field.getVariableName(), list, field.isUsed(), injectionStrategyGem == InjectionStrategyGem.CONSTRUCTOR && !additionalPublicEmptyConstructor(), injectionStrategyGem == InjectionStrategyGem.FIELD);
    }

    protected List<Annotation> getDecoratorAnnotations() {
        return Collections.emptyList();
    }

    protected TypeFactory getTypeFactory() {
        return this.typeFactory;
    }
}
