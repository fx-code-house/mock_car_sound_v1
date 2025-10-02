package org.mapstruct.ap.internal.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.gem.BeanMappingGem;
import org.mapstruct.ap.internal.gem.IterableMappingGem;
import org.mapstruct.ap.internal.gem.MapMappingGem;
import org.mapstruct.ap.internal.gem.MappingGem;
import org.mapstruct.ap.internal.gem.MappingsGem;
import org.mapstruct.ap.internal.gem.ObjectFactoryGem;
import org.mapstruct.ap.internal.gem.ValueMappingGem;
import org.mapstruct.ap.internal.gem.ValueMappingsGem;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.BeanMappingOptions;
import org.mapstruct.ap.internal.model.source.EnumMappingOptions;
import org.mapstruct.ap.internal.model.source.IterableMappingOptions;
import org.mapstruct.ap.internal.model.source.MapMappingOptions;
import org.mapstruct.ap.internal.model.source.MapperOptions;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.ValueMappingOptions;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.processor.ModelElementProcessor;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.AnnotationProcessingException;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.spi.EnumTransformationStrategy;

/* loaded from: classes3.dex */
public class MethodRetrievalProcessor implements ModelElementProcessor<Void, List<SourceMethod>> {
    private static final String JAVA_LANG_ANNOTATION_PGK = "java.lang.annotation";
    private static final String MAPPINGS_FQN = "org.mapstruct.Mappings";
    private static final String MAPPING_FQN = "org.mapstruct.Mapping";
    private static final String ORG_MAPSTRUCT_PKG = "org.mapstruct";
    private AccessorNamingUtils accessorNaming;
    private Elements elementUtils;
    private Map<String, EnumTransformationStrategy> enumTransformationStrategies;
    private FormattingMessager messager;
    private Options options;
    private TypeFactory typeFactory;
    private Types typeUtils;

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor
    public int getPriority() {
        return 1;
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor
    public List<SourceMethod> process(ModelElementProcessor.ProcessorContext processorContext, TypeElement typeElement, Void r7) {
        this.messager = processorContext.getMessager();
        this.typeFactory = processorContext.getTypeFactory();
        this.accessorNaming = processorContext.getAccessorNaming();
        this.typeUtils = processorContext.getTypeUtils();
        this.elementUtils = processorContext.getElementUtils();
        this.enumTransformationStrategies = processorContext.getEnumTransformationStrategies();
        this.options = processorContext.getOptions();
        this.messager.note(0, Message.PROCESSING_NOTE, typeElement);
        MapperOptions instanceOn = MapperOptions.getInstanceOn(typeElement, processorContext.getOptions());
        if (instanceOn.hasMapperConfig()) {
            this.messager.note(0, Message.CONFIG_NOTE, instanceOn.mapperConfigType().asElement().getSimpleName());
        }
        if (!instanceOn.isValid()) {
            throw new AnnotationProcessingException("Couldn't retrieve @Mapper annotation", typeElement, instanceOn.getAnnotationMirror());
        }
        return retrieveMethods(typeElement, typeElement, instanceOn, retrievePrototypeMethods(typeElement, instanceOn));
    }

    private List<SourceMethod> retrievePrototypeMethods(TypeElement typeElement, MapperOptions mapperOptions) {
        if (!mapperOptions.hasMapperConfig()) {
            return Collections.emptyList();
        }
        TypeElement typeElementAsTypeElement = asTypeElement(mapperOptions.mapperConfigType());
        ArrayList arrayList = new ArrayList();
        for (ExecutableElement executableElement : Executables.getAllEnclosedExecutableElements(this.elementUtils, typeElementAsTypeElement)) {
            ExecutableType methodType = this.typeFactory.getMethodType(mapperOptions.mapperConfigType(), executableElement);
            List<Parameter> parameters = this.typeFactory.getParameters(methodType, executableElement);
            SourceMethod methodRequiringImplementation = getMethodRequiringImplementation(methodType, executableElement, parameters, SourceMethod.containsTargetTypeParameter(parameters), mapperOptions, Collections.emptyList(), typeElement);
            if (methodRequiringImplementation != null) {
                arrayList.add(methodRequiringImplementation);
            }
        }
        return arrayList;
    }

    private List<SourceMethod> retrieveMethods(TypeElement typeElement, TypeElement typeElement2, MapperOptions mapperOptions, List<SourceMethod> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<ExecutableElement> it = Executables.getAllEnclosedExecutableElements(this.elementUtils, typeElement).iterator();
        while (it.hasNext()) {
            SourceMethod method = getMethod(typeElement, it.next(), typeElement2, mapperOptions, list);
            if (method != null) {
                arrayList.add(method);
            }
        }
        if (typeElement.equals(typeElement2)) {
            Iterator<DeclaredType> it2 = mapperOptions.uses().iterator();
            while (it2.hasNext()) {
                TypeElement typeElementAsTypeElement = asTypeElement(it2.next());
                if (!typeElement2.equals(typeElementAsTypeElement)) {
                    arrayList.addAll(retrieveMethods(typeElementAsTypeElement, typeElement2, mapperOptions, list));
                } else {
                    this.messager.printMessage(typeElement2, mapperOptions.getAnnotationMirror(), Message.RETRIEVAL_MAPPER_USES_CYCLE, typeElement2);
                }
            }
        }
        return arrayList;
    }

    private TypeElement asTypeElement(DeclaredType declaredType) {
        return declaredType.asElement();
    }

    private SourceMethod getMethod(TypeElement typeElement, ExecutableElement executableElement, TypeElement typeElement2, MapperOptions mapperOptions, List<SourceMethod> list) {
        ExecutableType methodType = this.typeFactory.getMethodType((DeclaredType) typeElement.asType(), executableElement);
        List<Parameter> parameters = this.typeFactory.getParameters(methodType, executableElement);
        Type returnType = this.typeFactory.getReturnType(methodType);
        boolean zContains = executableElement.getModifiers().contains(Modifier.ABSTRACT);
        boolean zContainsTargetTypeParameter = SourceMethod.containsTargetTypeParameter(parameters);
        if (typeElement.equals(typeElement2) && zContains) {
            return getMethodRequiringImplementation(methodType, executableElement, parameters, zContainsTargetTypeParameter, mapperOptions, list, typeElement2);
        }
        if (isValidReferencedMethod(parameters) || isValidFactoryMethod(executableElement, parameters, returnType) || isValidLifecycleCallbackMethod(executableElement)) {
            return getReferencedMethod(typeElement, methodType, executableElement, typeElement2, parameters);
        }
        return null;
    }

    private SourceMethod getMethodRequiringImplementation(ExecutableType executableType, ExecutableElement executableElement, List<Parameter> list, boolean z, MapperOptions mapperOptions, List<SourceMethod> list2, TypeElement typeElement) {
        Type returnType = this.typeFactory.getReturnType(executableType);
        List<Type> thrownTypes = this.typeFactory.getThrownTypes(executableType);
        List<Parameter> sourceParameters = Parameter.getSourceParameters(list);
        List<Parameter> contextParameters = Parameter.getContextParameters(list);
        Parameter parameterExtractTargetParameter = extractTargetParameter(list);
        if (!checkParameterAndReturnType(executableElement, sourceParameters, parameterExtractTargetParameter, contextParameters, selectResultType(returnType, parameterExtractTargetParameter), returnType, z)) {
            return null;
        }
        ParameterProvidedMethods parameterProvidedMethodsRetrieveContextProvidedMethods = retrieveContextProvidedMethods(contextParameters, typeElement, mapperOptions);
        BeanMappingOptions instanceOn = BeanMappingOptions.getInstanceOn(BeanMappingGem.instanceOn((Element) executableElement), mapperOptions, executableElement, this.messager, this.typeUtils, this.typeFactory);
        Set<MappingOptions> mappings = getMappings(executableElement, executableElement, instanceOn, new LinkedHashSet(), new HashSet());
        IterableMappingOptions iterableMappingOptionsFromGem = IterableMappingOptions.fromGem(IterableMappingGem.instanceOn((Element) executableElement), mapperOptions, executableElement, this.messager, this.typeUtils);
        MapMappingOptions mapMappingOptionsFromGem = MapMappingOptions.fromGem(MapMappingGem.instanceOn((Element) executableElement), mapperOptions, executableElement, this.messager, this.typeUtils);
        return new SourceMethod.Builder().setExecutable(executableElement).setParameters(list).setReturnType(returnType).setExceptionTypes(thrownTypes).setMapper(mapperOptions).setBeanMappingOptions(instanceOn).setMappingOptions(mappings).setIterableMappingOptions(iterableMappingOptionsFromGem).setMapMappingOptions(mapMappingOptionsFromGem).setValueMappingOptionss(getValueMappings(executableElement)).setEnumMappingOptions(EnumMappingOptions.getInstanceOn(executableElement, mapperOptions, this.enumTransformationStrategies, this.messager)).setTypeUtils(this.typeUtils).setTypeFactory(this.typeFactory).setPrototypeMethods(list2).setContextProvidedMethods(parameterProvidedMethodsRetrieveContextProvidedMethods).setVerboseLogging(this.options.isVerbose()).build();
    }

    private ParameterProvidedMethods retrieveContextProvidedMethods(List<Parameter> list, TypeElement typeElement, MapperOptions mapperOptions) {
        ParameterProvidedMethods.Builder builder = ParameterProvidedMethods.builder();
        for (Parameter parameter : list) {
            if (!parameter.getType().isPrimitive() && !parameter.getType().isArrayType()) {
                List<SourceMethod> listRetrieveMethods = retrieveMethods(parameter.getType().getTypeElement(), typeElement, mapperOptions, Collections.emptyList());
                ArrayList arrayList = new ArrayList(listRetrieveMethods.size());
                for (SourceMethod sourceMethod : listRetrieveMethods) {
                    if (sourceMethod.isLifecycleCallbackMethod() || sourceMethod.isObjectFactory()) {
                        arrayList.add(sourceMethod);
                    }
                }
                builder.addMethodsForParameter(parameter, arrayList);
            }
        }
        return builder.build();
    }

    private SourceMethod getReferencedMethod(TypeElement typeElement, ExecutableType executableType, ExecutableElement executableElement, TypeElement typeElement2, List<Parameter> list) {
        Type returnType = this.typeFactory.getReturnType(executableType);
        List<Type> thrownTypes = this.typeFactory.getThrownTypes(executableType);
        Type type = this.typeFactory.getType(typeElement);
        if (!this.typeFactory.getType(typeElement2).canAccess(type, executableElement)) {
            return null;
        }
        Type type2 = this.typeFactory.getType(executableElement.getEnclosingElement().asType());
        SourceMethod.Builder builder = new SourceMethod.Builder();
        if (typeElement.equals(typeElement2)) {
            type = null;
        }
        return builder.setDeclaringMapper(type).setDefininingType(type2).setExecutable(executableElement).setParameters(list).setReturnType(returnType).setExceptionTypes(thrownTypes).setTypeUtils(this.typeUtils).setTypeFactory(this.typeFactory).setVerboseLogging(this.options.isVerbose()).build();
    }

    private boolean isValidLifecycleCallbackMethod(ExecutableElement executableElement) {
        return Executables.isLifecycleCallbackMethod(executableElement);
    }

    private boolean isValidReferencedMethod(List<Parameter> list) {
        return isValidReferencedOrFactoryMethod(1, 1, list);
    }

    private boolean isValidFactoryMethod(ExecutableElement executableElement, List<Parameter> list, Type type) {
        if (isVoid(type)) {
            return false;
        }
        return isValidReferencedOrFactoryMethod(0, 0, list) || hasFactoryAnnotation(executableElement);
    }

    private boolean hasFactoryAnnotation(ExecutableElement executableElement) {
        return ObjectFactoryGem.instanceOn((Element) executableElement) != null;
    }

    private boolean isVoid(Type type) {
        return type.getTypeMirror().getKind() == TypeKind.VOID;
    }

    private boolean isValidReferencedOrFactoryMethod(int i, int i2, List<Parameter> list) {
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (Parameter parameter : list) {
            if (parameter.isMappingTarget()) {
                i4++;
            } else if (parameter.isTargetType()) {
                i5++;
            } else if (!parameter.isMappingContext()) {
                i3++;
            }
        }
        return i3 == i && i4 <= i2 && i5 <= 1;
    }

    private Parameter extractTargetParameter(List<Parameter> list) {
        for (Parameter parameter : list) {
            if (parameter.isMappingTarget()) {
                return parameter;
            }
        }
        return null;
    }

    private Type selectResultType(Type type, Parameter parameter) {
        return parameter != null ? parameter.getType() : type;
    }

    private boolean checkParameterAndReturnType(ExecutableElement executableElement, List<Parameter> list, Parameter parameter, List<Parameter> list2, Type type, Type type2, boolean z) {
        if (list.isEmpty()) {
            this.messager.printMessage(executableElement, Message.RETRIEVAL_NO_INPUT_ARGS, new Object[0]);
            return false;
        }
        if (parameter != null && list.size() + list2.size() + 1 != executableElement.getParameters().size()) {
            this.messager.printMessage(executableElement, Message.RETRIEVAL_DUPLICATE_MAPPING_TARGETS, new Object[0]);
            return false;
        }
        if (isVoid(type)) {
            this.messager.printMessage(executableElement, Message.RETRIEVAL_VOID_MAPPING_METHOD, new Object[0]);
            return false;
        }
        if (type2.getTypeMirror().getKind() != TypeKind.VOID && !type.isAssignableTo(type2) && !type.isAssignableTo(this.typeFactory.effectiveResultTypeFor(type2, null))) {
            this.messager.printMessage(executableElement, Message.RETRIEVAL_NON_ASSIGNABLE_RESULTTYPE, new Object[0]);
            return false;
        }
        Iterator<Parameter> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().getType().isTypeVar()) {
                this.messager.printMessage(executableElement, Message.RETRIEVAL_TYPE_VAR_SOURCE, new Object[0]);
                return false;
            }
        }
        HashSet hashSet = new HashSet();
        Iterator<Parameter> it2 = list2.iterator();
        while (it2.hasNext()) {
            if (!hashSet.add(it2.next().getType())) {
                this.messager.printMessage(executableElement, Message.RETRIEVAL_CONTEXT_PARAMS_WITH_SAME_TYPE, new Object[0]);
                return false;
            }
        }
        if (type2.isTypeVar() || type.isTypeVar()) {
            this.messager.printMessage(executableElement, Message.RETRIEVAL_TYPE_VAR_RESULT, new Object[0]);
            return false;
        }
        Type type3 = list.get(0).getType();
        if (type3.isIterableOrStreamType() && !type.isIterableOrStreamType()) {
            this.messager.printMessage(executableElement, Message.RETRIEVAL_ITERABLE_TO_NON_ITERABLE, new Object[0]);
            return false;
        }
        if (z) {
            this.messager.printMessage(executableElement, Message.RETRIEVAL_MAPPING_HAS_TARGET_TYPE_PARAMETER, new Object[0]);
            return false;
        }
        if (!type3.isIterableOrStreamType() && type.isIterableOrStreamType()) {
            this.messager.printMessage(executableElement, Message.RETRIEVAL_NON_ITERABLE_TO_ITERABLE, new Object[0]);
            return false;
        }
        if (type3.isPrimitive()) {
            this.messager.printMessage(executableElement, Message.RETRIEVAL_PRIMITIVE_PARAMETER, new Object[0]);
            return false;
        }
        if (type.isPrimitive()) {
            this.messager.printMessage(executableElement, Message.RETRIEVAL_PRIMITIVE_RETURN, new Object[0]);
            return false;
        }
        for (Type type4 : type.getTypeParameters()) {
            if (type4.isTypeVar()) {
                this.messager.printMessage(executableElement, Message.RETRIEVAL_TYPE_VAR_RESULT, new Object[0]);
                return false;
            }
            if (type4.isWildCardExtendsBound()) {
                this.messager.printMessage(executableElement, Message.RETRIEVAL_WILDCARD_EXTENDS_BOUND_RESULT, new Object[0]);
                return false;
            }
        }
        for (Type type5 : type3.getTypeParameters()) {
            if (type5.isWildCardSuperBound()) {
                this.messager.printMessage(executableElement, Message.RETRIEVAL_WILDCARD_SUPER_BOUND_SOURCE, new Object[0]);
                return false;
            }
            if (type5.isTypeVar()) {
                this.messager.printMessage(executableElement, Message.RETRIEVAL_TYPE_VAR_SOURCE, new Object[0]);
                return false;
            }
        }
        return true;
    }

    private Set<MappingOptions> getMappings(ExecutableElement executableElement, Element element, BeanMappingOptions beanMappingOptions, Set<MappingOptions> set, Set<Element> set2) {
        Iterator it = element.getAnnotationMirrors().iterator();
        while (it.hasNext()) {
            Element elementAsElement = ((AnnotationMirror) it.next()).getAnnotationType().asElement();
            if (isAnnotation(elementAsElement, MAPPING_FQN)) {
                MappingOptions.addInstance(MappingGem.instanceOn(element), executableElement, beanMappingOptions, this.messager, this.typeUtils, set);
            } else if (isAnnotation(elementAsElement, MAPPINGS_FQN)) {
                MappingOptions.addInstances(MappingsGem.instanceOn(element), executableElement, beanMappingOptions, this.messager, this.typeUtils, set);
            } else if (!isAnnotationInPackage(elementAsElement, JAVA_LANG_ANNOTATION_PGK) && !isAnnotationInPackage(elementAsElement, ORG_MAPSTRUCT_PKG) && !set2.contains(elementAsElement)) {
                set2.add(elementAsElement);
                getMappings(executableElement, elementAsElement, beanMappingOptions, set, set2);
            }
        }
        return set;
    }

    private boolean isAnnotationInPackage(Element element, String str) {
        if (ElementKind.ANNOTATION_TYPE == element.getKind()) {
            return str.equals(this.elementUtils.getPackageOf(element).getQualifiedName().toString());
        }
        return false;
    }

    private boolean isAnnotation(Element element, String str) {
        if (ElementKind.ANNOTATION_TYPE == element.getKind()) {
            return str.equals(((TypeElement) element).getQualifiedName().toString());
        }
        return false;
    }

    private List<ValueMappingOptions> getValueMappings(ExecutableElement executableElement) {
        ValueMappingOptions valueMappingOptionsFromMappingGem;
        ArrayList arrayList = new ArrayList();
        ValueMappingGem valueMappingGemInstanceOn = ValueMappingGem.instanceOn((Element) executableElement);
        ValueMappingsGem valueMappingsGemInstanceOn = ValueMappingsGem.instanceOn((Element) executableElement);
        if (valueMappingGemInstanceOn != null && (valueMappingOptionsFromMappingGem = ValueMappingOptions.fromMappingGem(valueMappingGemInstanceOn)) != null) {
            arrayList.add(valueMappingOptionsFromMappingGem);
        }
        if (valueMappingsGemInstanceOn != null) {
            ValueMappingOptions.fromMappingsGem(valueMappingsGemInstanceOn, executableElement, this.messager, arrayList);
        }
        return arrayList;
    }
}
