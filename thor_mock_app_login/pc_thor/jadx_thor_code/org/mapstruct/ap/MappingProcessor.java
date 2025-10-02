package org.mapstruct.ap;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementKindVisitor6;
import javax.tools.Diagnostic;
import org.mapstruct.ap.internal.gem.MapperGem;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.processor.DefaultModelElementProcessorContext;
import org.mapstruct.ap.internal.processor.ModelElementProcessor;
import org.mapstruct.ap.internal.util.AnnotationProcessingException;
import org.mapstruct.ap.internal.util.AnnotationProcessorContext;
import org.mapstruct.ap.internal.util.RoundContext;
import org.mapstruct.ap.spi.TypeHierarchyErroneousException;

@SupportedOptions({MappingProcessor.SUPPRESS_GENERATOR_TIMESTAMP, MappingProcessor.SUPPRESS_GENERATOR_VERSION_INFO_COMMENT, MappingProcessor.UNMAPPED_TARGET_POLICY, MappingProcessor.DEFAULT_COMPONENT_MODEL, MappingProcessor.DEFAULT_INJECTION_STRATEGY, MappingProcessor.VERBOSE})
@SupportedAnnotationTypes({"org.mapstruct.Mapper"})
/* loaded from: classes3.dex */
public class MappingProcessor extends AbstractProcessor {
    protected static final String ALWAYS_GENERATE_SERVICE_FILE = "mapstruct.alwaysGenerateServicesFile";
    private static final boolean ANNOTATIONS_CLAIMED_EXCLUSIVELY = false;
    protected static final String DEFAULT_COMPONENT_MODEL = "mapstruct.defaultComponentModel";
    protected static final String DEFAULT_INJECTION_STRATEGY = "mapstruct.defaultInjectionStrategy";
    protected static final String SUPPRESS_GENERATOR_TIMESTAMP = "mapstruct.suppressGeneratorTimestamp";
    protected static final String SUPPRESS_GENERATOR_VERSION_INFO_COMMENT = "mapstruct.suppressGeneratorVersionInfoComment";
    protected static final String UNMAPPED_TARGET_POLICY = "mapstruct.unmappedTargetPolicy";
    protected static final String VERBOSE = "mapstruct.verbose";
    private AnnotationProcessorContext annotationProcessorContext;
    private Set<DeferredMapper> deferredMappers = new HashSet();
    private Options options;

    static /* synthetic */ String lambda$getDeclaredTypesNotToBeImported$1(String str) {
        return str;
    }

    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.options = createOptions();
        this.annotationProcessorContext = new AnnotationProcessorContext(processingEnvironment.getElementUtils(), processingEnvironment.getTypeUtils(), processingEnvironment.getMessager(), this.options.isVerbose());
    }

    private Options createOptions() {
        String str = (String) this.processingEnv.getOptions().get(UNMAPPED_TARGET_POLICY);
        return new Options(Boolean.valueOf((String) this.processingEnv.getOptions().get(SUPPRESS_GENERATOR_TIMESTAMP)).booleanValue(), Boolean.valueOf((String) this.processingEnv.getOptions().get(SUPPRESS_GENERATOR_VERSION_INFO_COMMENT)).booleanValue(), str != null ? ReportingPolicyGem.valueOf(str.toUpperCase()) : null, (String) this.processingEnv.getOptions().get(DEFAULT_COMPONENT_MODEL), (String) this.processingEnv.getOptions().get(DEFAULT_INJECTION_STRATEGY), Boolean.valueOf((String) this.processingEnv.getOptions().get(ALWAYS_GENERATE_SERVICE_FILE)).booleanValue(), Boolean.valueOf((String) this.processingEnv.getOptions().get(VERBOSE)).booleanValue());
    }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        String string;
        if (!roundEnvironment.processingOver()) {
            RoundContext roundContext = new RoundContext(this.annotationProcessorContext);
            processMapperElements(getAndResetDeferredMappers(), roundContext);
            processMapperElements(getMappers(set, roundEnvironment), roundContext);
            return false;
        }
        if (this.deferredMappers.isEmpty()) {
            return false;
        }
        for (DeferredMapper deferredMapper : this.deferredMappers) {
            TypeElement typeElement = deferredMapper.deferredMapperElement;
            QualifiedNameable qualifiedNameable = deferredMapper.erroneousElement;
            if (qualifiedNameable instanceof QualifiedNameable) {
                string = qualifiedNameable.getQualifiedName().toString();
            } else {
                string = qualifiedNameable != null ? qualifiedNameable.getSimpleName().toString() : null;
            }
            TypeElement typeElement2 = this.annotationProcessorContext.getElementUtils().getTypeElement(typeElement.getQualifiedName());
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "No implementation was created for " + typeElement2.getSimpleName() + " due to having a problem in the erroneous element " + string + ". Hint: this often means that some other annotation processor was supposed to process the erroneous element. You can also enable MapStruct verbose mode by setting -Amapstruct.verbose=true as a compilation argument.", typeElement2);
        }
        return false;
    }

    private Set<TypeElement> getAndResetDeferredMappers() {
        HashSet hashSet = new HashSet(this.deferredMappers.size());
        Iterator<DeferredMapper> it = this.deferredMappers.iterator();
        while (it.hasNext()) {
            hashSet.add(this.processingEnv.getElementUtils().getTypeElement(it.next().deferredMapperElement.getQualifiedName()));
        }
        this.deferredMappers.clear();
        return hashSet;
    }

    private Set<TypeElement> getMappers(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        HashSet hashSet = new HashSet();
        for (TypeElement typeElement : set) {
            if (typeElement.getKind() == ElementKind.ANNOTATION_TYPE) {
                try {
                    Iterator it = roundEnvironment.getElementsAnnotatedWith(typeElement).iterator();
                    while (it.hasNext()) {
                        TypeElement typeElementAsTypeElement = asTypeElement((Element) it.next());
                        if (typeElementAsTypeElement != null && MapperGem.instanceOn((Element) typeElementAsTypeElement) != null) {
                            hashSet.add(typeElementAsTypeElement);
                        }
                    }
                } catch (Throwable th) {
                    handleUncaughtError(typeElement, th);
                }
            }
        }
        return hashSet;
    }

    private void processMapperElements(Set<TypeElement> set, RoundContext roundContext) {
        for (TypeElement typeElement : set) {
            try {
                typeElement.getEnclosedElements();
                processMapperTypeElement(new DefaultModelElementProcessorContext(this.processingEnv, this.options, roundContext, getDeclaredTypesNotToBeImported(typeElement)), typeElement);
            } catch (TypeHierarchyErroneousException e) {
                TypeMirror type = e.getType();
                Element elementAsElement = type != null ? roundContext.getAnnotationProcessorContext().getTypeUtils().asElement(type) : null;
                if (this.options.isVerbose()) {
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "MapStruct: referred types not available (yet), deferring mapper: " + typeElement);
                }
                this.deferredMappers.add(new DeferredMapper(typeElement, elementAsElement));
            } catch (Throwable th) {
                handleUncaughtError(typeElement, th);
                return;
            }
        }
    }

    private Map<String, String> getDeclaredTypesNotToBeImported(final TypeElement typeElement) {
        return (Map) typeElement.getEnclosedElements().stream().filter(new Predicate() { // from class: org.mapstruct.ap.MappingProcessor$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ElementKind.CLASS.equals(((Element) obj).getKind());
            }
        }).map(new MappingProcessor$$ExternalSyntheticLambda1()).map(new Function() { // from class: org.mapstruct.ap.MappingProcessor$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Name) obj).toString();
            }
        }).collect(Collectors.toMap(new Function() { // from class: org.mapstruct.ap.MappingProcessor$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MappingProcessor.lambda$getDeclaredTypesNotToBeImported$1((String) obj);
            }
        }, new Function() { // from class: org.mapstruct.ap.MappingProcessor$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MappingProcessor.lambda$getDeclaredTypesNotToBeImported$2(typeElement, (String) obj);
            }
        }));
    }

    static /* synthetic */ String lambda$getDeclaredTypesNotToBeImported$2(TypeElement typeElement, String str) {
        return typeElement.getQualifiedName().toString() + "." + str;
    }

    private void handleUncaughtError(Element element, Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Internal error in the mapping processor: " + stringWriter.toString().replace(System.lineSeparator(), "  "), element);
    }

    private void processMapperTypeElement(ModelElementProcessor.ProcessorContext processorContext, TypeElement typeElement) {
        Iterator<ModelElementProcessor<?, ?>> it = getProcessors().iterator();
        Object objProcess = null;
        while (it.hasNext()) {
            try {
                objProcess = process(processorContext, it.next(), typeElement, objProcess);
            } catch (AnnotationProcessingException e) {
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), e.getElement(), e.getAnnotationMirror(), e.getAnnotationValue());
                return;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <P, R> R process(ModelElementProcessor.ProcessorContext processorContext, ModelElementProcessor<P, R> modelElementProcessor, TypeElement typeElement, Object obj) {
        return modelElementProcessor.process(processorContext, typeElement, obj);
    }

    private Iterable<ModelElementProcessor<?, ?>> getProcessors() {
        Iterator it = ServiceLoader.load(ModelElementProcessor.class, MappingProcessor.class.getClassLoader()).iterator();
        ArrayList arrayList = new ArrayList();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        Collections.sort(arrayList, new ProcessorComparator());
        return arrayList;
    }

    private TypeElement asTypeElement(Element element) {
        return (TypeElement) element.accept(new ElementKindVisitor6<TypeElement, Void>() { // from class: org.mapstruct.ap.MappingProcessor.1
            public TypeElement visitTypeAsClass(TypeElement typeElement, Void r2) {
                return typeElement;
            }

            public TypeElement visitTypeAsInterface(TypeElement typeElement, Void r2) {
                return typeElement;
            }
        }, (Object) null);
    }

    private static class ProcessorComparator implements Comparator<ModelElementProcessor<?, ?>> {
        private ProcessorComparator() {
        }

        @Override // java.util.Comparator
        public int compare(ModelElementProcessor<?, ?> modelElementProcessor, ModelElementProcessor<?, ?> modelElementProcessor2) {
            return Integer.compare(modelElementProcessor.getPriority(), modelElementProcessor2.getPriority());
        }
    }

    private static class DeferredMapper {
        private final TypeElement deferredMapperElement;
        private final Element erroneousElement;

        private DeferredMapper(TypeElement typeElement, Element element) {
            this.deferredMapperElement = typeElement;
            this.erroneousElement = element;
        }
    }
}
