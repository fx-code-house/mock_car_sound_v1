package org.mapstruct.ap.internal.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.DecoratedWithGem;
import org.mapstruct.ap.internal.gem.InheritConfigurationGem;
import org.mapstruct.ap.internal.gem.InheritInverseConfigurationGem;
import org.mapstruct.ap.internal.gem.MapperGem;
import org.mapstruct.ap.internal.gem.MappingInheritanceStrategyGem;
import org.mapstruct.ap.internal.model.BeanMappingMethod;
import org.mapstruct.ap.internal.model.ContainerMappingMethod;
import org.mapstruct.ap.internal.model.ContainerMappingMethodBuilder;
import org.mapstruct.ap.internal.model.Decorator;
import org.mapstruct.ap.internal.model.DefaultMapperReference;
import org.mapstruct.ap.internal.model.DelegatingMethod;
import org.mapstruct.ap.internal.model.IterableMappingMethod;
import org.mapstruct.ap.internal.model.MapMappingMethod;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.MapperReference;
import org.mapstruct.ap.internal.model.MappingBuilderContext;
import org.mapstruct.ap.internal.model.MappingMethod;
import org.mapstruct.ap.internal.model.StreamMappingMethod;
import org.mapstruct.ap.internal.model.SupportingConstructorFragment;
import org.mapstruct.ap.internal.model.SupportingField;
import org.mapstruct.ap.internal.model.ValueMappingMethod;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MapperOptions;
import org.mapstruct.ap.internal.model.source.MappingMethodOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.processor.ModelElementProcessor;
import org.mapstruct.ap.internal.processor.creation.MappingResolverImpl;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.version.VersionInformation;

/* loaded from: classes3.dex */
public class MapperCreationProcessor implements ModelElementProcessor<List<SourceMethod>, Mapper> {
    private AccessorNamingUtils accessorNaming;
    private Elements elementUtils;
    private MappingBuilderContext mappingContext;
    private FormattingMessager messager;
    private Options options;
    private TypeFactory typeFactory;
    private Types typeUtils;
    private VersionInformation versionInformation;

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor
    public int getPriority() {
        return 1000;
    }

    @Override // org.mapstruct.ap.internal.processor.ModelElementProcessor
    public Mapper process(ModelElementProcessor.ProcessorContext processorContext, TypeElement typeElement, List<SourceMethod> list) {
        this.elementUtils = processorContext.getElementUtils();
        this.typeUtils = processorContext.getTypeUtils();
        this.messager = processorContext.getMessager();
        this.options = processorContext.getOptions();
        this.versionInformation = processorContext.getVersionInformation();
        this.typeFactory = processorContext.getTypeFactory();
        this.accessorNaming = processorContext.getAccessorNaming();
        MapperOptions instanceOn = MapperOptions.getInstanceOn(typeElement, processorContext.getOptions());
        List<MapperReference> listInitReferencedMappers = initReferencedMappers(typeElement, instanceOn);
        this.mappingContext = new MappingBuilderContext(this.typeFactory, this.elementUtils, this.typeUtils, this.messager, this.accessorNaming, processorContext.getEnumMappingStrategy(), processorContext.getEnumTransformationStrategies(), this.options, new MappingResolverImpl(this.messager, this.elementUtils, this.typeUtils, this.typeFactory, new ArrayList(list), listInitReferencedMappers, this.options.isVerbose()), typeElement, Collections.unmodifiableList(list), listInitReferencedMappers);
        return getMapper(typeElement, instanceOn, list);
    }

    private List<MapperReference> initReferencedMappers(TypeElement typeElement, MapperOptions mapperOptions) {
        LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = new LinkedList();
        for (TypeMirror typeMirror : mapperOptions.uses()) {
            DefaultMapperReference defaultMapperReference = DefaultMapperReference.getInstance(this.typeFactory.getType(typeMirror), MapperGem.instanceOn(this.typeUtils.asElement(typeMirror)) != null, this.typeFactory, linkedList2);
            linkedList.add(defaultMapperReference);
            linkedList2.add(defaultMapperReference.getVariableName());
        }
        return linkedList;
    }

    private Mapper getMapper(TypeElement typeElement, MapperOptions mapperOptions, List<SourceMethod> list) {
        List<MappingMethod> mappingMethods = getMappingMethods(mapperOptions, list);
        mappingMethods.addAll(this.mappingContext.getUsedSupportedMappings());
        mappingMethods.addAll(this.mappingContext.getMappingsToGenerate());
        ArrayList arrayList = new ArrayList(this.mappingContext.getMapperReferences());
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        SupportingField.addAllFieldsIn(this.mappingContext.getUsedSupportedMappings(), linkedHashSet);
        arrayList.addAll(linkedHashSet);
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        SupportingConstructorFragment.addAllFragmentsIn(this.mappingContext.getUsedSupportedMappings(), linkedHashSet2);
        Mapper mapperBuild = new Mapper.Builder().element(typeElement).methods(mappingMethods).fields(arrayList).constructorFragments(linkedHashSet2).options(this.options).versionInformation(this.versionInformation).decorator(getDecorator(typeElement, list, mapperOptions.implementationName(), mapperOptions.implementationPackage(), getExtraImports(typeElement, mapperOptions))).typeFactory(this.typeFactory).elementUtils(this.elementUtils).extraImports(getExtraImports(typeElement, mapperOptions)).implName(mapperOptions.implementationName()).implPackage(mapperOptions.implementationPackage()).build();
        if (!this.mappingContext.getForgedMethodsUnderCreation().isEmpty()) {
            this.messager.printMessage(typeElement, Message.GENERAL_NOT_ALL_FORGED_CREATED, this.mappingContext.getForgedMethodsUnderCreation().keySet());
        }
        return mapperBuild;
    }

    private Decorator getDecorator(TypeElement typeElement, List<SourceMethod> list, String str, String str2, SortedSet<Type> sortedSet) {
        DecoratedWithGem decoratedWithGemInstanceOn = DecoratedWithGem.instanceOn((Element) typeElement);
        if (decoratedWithGemInstanceOn == null) {
            return null;
        }
        TypeElement typeElementAsElement = this.typeUtils.asElement(decoratedWithGemInstanceOn.value().get());
        if (!this.typeUtils.isAssignable(typeElementAsElement.asType(), typeElement.asType())) {
            this.messager.printMessage(typeElement, decoratedWithGemInstanceOn.mirror(), Message.DECORATOR_NO_SUBTYPE, new Object[0]);
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<SourceMethod> it = list.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                break;
            }
            SourceMethod next = it.next();
            Iterator it2 = ElementFilter.methodsIn(typeElementAsElement.getEnclosedElements()).iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                if (this.elementUtils.overrides((ExecutableElement) it2.next(), next.getExecutable(), typeElementAsElement)) {
                    z = false;
                    break;
                }
            }
            Type declaringMapper = next.getDeclaringMapper();
            if (z && !next.isDefault() && !next.isStatic() && (declaringMapper == null || declaringMapper.equals(this.typeFactory.getType(typeElement)))) {
                arrayList.add(new DelegatingMethod(next));
            }
        }
        boolean z2 = false;
        boolean z3 = false;
        for (ExecutableElement executableElement : ElementFilter.constructorsIn(typeElementAsElement.getEnclosedElements())) {
            if (executableElement.getParameters().isEmpty()) {
                z3 = true;
            } else if (executableElement.getParameters().size() == 1 && this.typeUtils.isAssignable(typeElement.asType(), ((VariableElement) org.mapstruct.ap.internal.util.Collections.first(executableElement.getParameters())).asType())) {
                z2 = true;
            }
        }
        if (!z2 && !z3) {
            this.messager.printMessage(typeElement, decoratedWithGemInstanceOn.mirror(), Message.DECORATOR_CONSTRUCTOR, new Object[0]);
        }
        return new Decorator.Builder().elementUtils(this.elementUtils).typeFactory(this.typeFactory).mapperElement(typeElement).decoratedWith(decoratedWithGemInstanceOn).methods(arrayList).hasDelegateConstructor(z2).options(this.options).versionInformation(this.versionInformation).implName(str).implPackage(str2).extraImports(sortedSet).build();
    }

    private SortedSet<Type> getExtraImports(TypeElement typeElement, MapperOptions mapperOptions) {
        TreeSet treeSet = new TreeSet();
        Iterator<DeclaredType> it = mapperOptions.imports().iterator();
        while (it.hasNext()) {
            treeSet.add(this.typeFactory.getType(it.next()));
        }
        if (!"default".equals(mapperOptions.implementationPackage())) {
            treeSet.add(this.typeFactory.getType(typeElement));
        }
        return treeSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private List<MappingMethod> getMappingMethods(MapperOptions mapperOptions, List<SourceMethod> list) {
        SelectionParameters keySelectionParameters;
        FormattingParameters keyFormattingParameters;
        SelectionParameters valueSelectionParameters;
        FormattingParameters valueFormattingParameters;
        ArrayList arrayList = new ArrayList();
        for (SourceMethod sourceMethod : list) {
            if (sourceMethod.overridesMethod()) {
                mergeInheritedOptions(sourceMethod, mapperOptions, list, new ArrayList());
                MappingMethodOptions options = sourceMethod.getOptions();
                if (sourceMethod.isIterableMapping()) {
                    this.messager.note(1, Message.ITERABLEMAPPING_CREATE_NOTE, sourceMethod);
                    IterableMappingMethod iterableMappingMethod = (IterableMappingMethod) createWithElementMappingMethod(sourceMethod, options, new IterableMappingMethod.Builder());
                    z = iterableMappingMethod.getFactoryMethod() != null;
                    arrayList.add(iterableMappingMethod);
                } else if (sourceMethod.isMapMapping()) {
                    MapMappingMethod.Builder builder = new MapMappingMethod.Builder();
                    if (options.getMapMapping() != null) {
                        keySelectionParameters = options.getMapMapping().getKeySelectionParameters();
                        keyFormattingParameters = options.getMapMapping().getKeyFormattingParameters();
                        valueSelectionParameters = options.getMapMapping().getValueSelectionParameters();
                        valueFormattingParameters = options.getMapMapping().getValueFormattingParameters();
                        options.getMapMapping().getNullValueMappingStrategy();
                    } else {
                        keySelectionParameters = null;
                        keyFormattingParameters = null;
                        valueSelectionParameters = null;
                        valueFormattingParameters = null;
                    }
                    this.messager.note(1, Message.MAPMAPPING_CREATE_NOTE, sourceMethod);
                    MapMappingMethod mapMappingMethodBuild = ((MapMappingMethod.Builder) ((MapMappingMethod.Builder) builder.mappingContext(this.mappingContext)).method(sourceMethod)).keyFormattingParameters(keyFormattingParameters).keySelectionParameters(keySelectionParameters).valueFormattingParameters(valueFormattingParameters).valueSelectionParameters(valueSelectionParameters).build();
                    z = mapMappingMethodBuild.getFactoryMethod() != null;
                    arrayList.add(mapMappingMethodBuild);
                } else if (sourceMethod.isValueMapping()) {
                    this.messager.note(1, Message.VALUEMAPPING_CREATE_NOTE, sourceMethod);
                    ValueMappingMethod valueMappingMethodBuild = new ValueMappingMethod.Builder().mappingContext(this.mappingContext).method(sourceMethod).valueMappings(options.getValueMappings()).enumMapping(options.getEnumMappingOptions()).build();
                    if (valueMappingMethodBuild != null) {
                        arrayList.add(valueMappingMethodBuild);
                    }
                } else if (sourceMethod.isRemovedEnumMapping()) {
                    this.messager.printMessage(sourceMethod.getExecutable(), Message.ENUMMAPPING_REMOVED, new Object[0]);
                } else if (sourceMethod.isStreamMapping()) {
                    this.messager.note(1, Message.STREAMMAPPING_CREATE_NOTE, sourceMethod);
                    StreamMappingMethod streamMappingMethod = (StreamMappingMethod) createWithElementMappingMethod(sourceMethod, options, new StreamMappingMethod.Builder());
                    z = streamMappingMethod.getFactoryMethod() != null || sourceMethod.getResultType().isStreamType();
                    arrayList.add(streamMappingMethod);
                } else {
                    this.messager.note(1, Message.BEANMAPPING_CREATE_NOTE, sourceMethod);
                    BuilderGem builder2 = sourceMethod.getOptions().getBeanMapping().getBuilder();
                    Type userDesiredReturnType = getUserDesiredReturnType(sourceMethod);
                    BeanMappingMethod beanMappingMethodBuild = new BeanMappingMethod.Builder().mappingContext(this.mappingContext).sourceMethod(sourceMethod).userDefinedReturnType(userDesiredReturnType).returnTypeBuilder(this.typeFactory.builderTypeFor(userDesiredReturnType != null ? userDesiredReturnType : sourceMethod.getReturnType(), builder2)).build();
                    if (beanMappingMethodBuild != null) {
                        arrayList.add(beanMappingMethodBuild);
                    }
                    z = true;
                }
                if (!z) {
                    reportErrorIfNoImplementationTypeIsRegisteredForInterfaceReturnType(sourceMethod);
                }
            }
        }
        return arrayList;
    }

    private Type getUserDesiredReturnType(SourceMethod sourceMethod) {
        SelectionParameters selectionParameters = sourceMethod.getOptions().getBeanMapping().getSelectionParameters();
        if (selectionParameters == null || selectionParameters.getResultType() == null) {
            return null;
        }
        return this.typeFactory.getType(selectionParameters.getResultType());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <M extends ContainerMappingMethod> M createWithElementMappingMethod(SourceMethod sourceMethod, MappingMethodOptions mappingMethodOptions, ContainerMappingMethodBuilder<?, M> containerMappingMethodBuilder) {
        FormattingParameters formattingParameters;
        SelectionParameters selectionParameters;
        if (mappingMethodOptions.getIterableMapping() != null) {
            formattingParameters = mappingMethodOptions.getIterableMapping().getFormattingParameters();
            selectionParameters = mappingMethodOptions.getIterableMapping().getSelectionParameters();
        } else {
            formattingParameters = null;
            selectionParameters = null;
        }
        return (M) ((ContainerMappingMethodBuilder) ((ContainerMappingMethodBuilder) containerMappingMethodBuilder.mappingContext(this.mappingContext)).method(sourceMethod)).formattingParameters(formattingParameters).selectionParameters(selectionParameters).build();
    }

    private void mergeInheritedOptions(SourceMethod sourceMethod, MapperOptions mapperOptions, List<SourceMethod> list, List<SourceMethod> list2) {
        if (list2.contains(sourceMethod)) {
            list2.add(sourceMethod);
            this.messager.printMessage(sourceMethod.getExecutable(), Message.INHERITCONFIGURATION_CYCLE, Strings.join(list2, " -> "));
            return;
        }
        list2.add(sourceMethod);
        MappingMethodOptions options = sourceMethod.getOptions();
        List<SourceMethod> applicableReversePrototypeMethods = sourceMethod.getApplicableReversePrototypeMethods();
        SourceMethod inverseTemplateMethod = getInverseTemplateMethod(org.mapstruct.ap.internal.util.Collections.join(list, applicableReversePrototypeMethods), sourceMethod, list2, mapperOptions);
        List<SourceMethod> applicablePrototypeMethods = sourceMethod.getApplicablePrototypeMethods();
        SourceMethod forwardTemplateMethod = getForwardTemplateMethod(org.mapstruct.ap.internal.util.Collections.join(list, applicablePrototypeMethods), sourceMethod, list2, mapperOptions);
        if (forwardTemplateMethod != null) {
            options.applyInheritedOptions(forwardTemplateMethod, false);
        }
        if (inverseTemplateMethod != null) {
            options.applyInheritedOptions(inverseTemplateMethod, true);
        }
        MappingInheritanceStrategyGem mappingInheritanceStrategy = mapperOptions.getMappingInheritanceStrategy();
        if (mappingInheritanceStrategy.isAutoInherit()) {
            if (forwardTemplateMethod == null && mappingInheritanceStrategy.isApplyForward()) {
                if (applicablePrototypeMethods.size() == 1) {
                    options.applyInheritedOptions((SourceMethod) org.mapstruct.ap.internal.util.Collections.first(applicablePrototypeMethods), false);
                } else if (applicablePrototypeMethods.size() > 1) {
                    this.messager.printMessage(sourceMethod.getExecutable(), Message.INHERITCONFIGURATION_MULTIPLE_PROTOTYPE_METHODS_MATCH, Strings.join(applicablePrototypeMethods, ", "));
                }
            }
            if (inverseTemplateMethod == null && mappingInheritanceStrategy.isApplyReverse()) {
                if (applicableReversePrototypeMethods.size() == 1) {
                    options.applyInheritedOptions((SourceMethod) org.mapstruct.ap.internal.util.Collections.first(applicableReversePrototypeMethods), true);
                } else if (applicableReversePrototypeMethods.size() > 1) {
                    this.messager.printMessage(sourceMethod.getExecutable(), Message.INHERITINVERSECONFIGURATION_MULTIPLE_PROTOTYPE_METHODS_MATCH, Strings.join(applicableReversePrototypeMethods, ", "));
                }
            }
        }
        if (options.getBeanMapping() != null && options.getBeanMapping().isignoreByDefault()) {
            options.applyIgnoreAll(sourceMethod, this.typeFactory, this.mappingContext.getMessager());
        }
        options.markAsFullyInitialized();
    }

    private void reportErrorIfNoImplementationTypeIsRegisteredForInterfaceReturnType(Method method) {
        if (method.getReturnType().getTypeMirror().getKind() != TypeKind.VOID && method.getReturnType().isInterface() && method.getReturnType().getImplementationType() == null) {
            this.messager.printMessage(method.getExecutable(), Message.GENERAL_NO_IMPLEMENTATION, method.getReturnType());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private org.mapstruct.ap.internal.model.source.SourceMethod getInverseTemplateMethod(java.util.List<org.mapstruct.ap.internal.model.source.SourceMethod> r10, org.mapstruct.ap.internal.model.source.SourceMethod r11, java.util.List<org.mapstruct.ap.internal.model.source.SourceMethod> r12, org.mapstruct.ap.internal.model.source.MapperOptions r13) {
        /*
            r9 = this;
            javax.lang.model.element.ExecutableElement r0 = r11.getExecutable()
            org.mapstruct.ap.internal.gem.InheritInverseConfigurationGem r0 = org.mapstruct.ap.internal.gem.InheritInverseConfigurationGem.instanceOn(r0)
            if (r0 == 0) goto Lac
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.util.Iterator r2 = r10.iterator()
        L13:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L29
            java.lang.Object r3 = r2.next()
            org.mapstruct.ap.internal.model.source.SourceMethod r3 = (org.mapstruct.ap.internal.model.source.SourceMethod) r3
            boolean r4 = r11.inverses(r3)
            if (r4 == 0) goto L13
            r1.add(r3)
            goto L13
        L29:
            org.mapstruct.ap.shaded.org.mapstruct.tools.gem.GemValue r2 = r0.name()
            java.lang.Object r2 = r2.get()
            java.lang.String r2 = (java.lang.String) r2
            int r3 = r1.size()
            r4 = 1
            r5 = 0
            if (r3 != r4) goto L69
            boolean r3 = r2.isEmpty()
            if (r3 == 0) goto L48
            java.lang.Object r11 = r1.get(r5)
            org.mapstruct.ap.internal.model.source.SourceMethod r11 = (org.mapstruct.ap.internal.model.source.SourceMethod) r11
            goto Lad
        L48:
            java.lang.Object r3 = r1.get(r5)
            org.mapstruct.ap.internal.model.source.SourceMethod r3 = (org.mapstruct.ap.internal.model.source.SourceMethod) r3
            java.lang.String r3 = r3.getName()
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L5f
            java.lang.Object r11 = r1.get(r5)
            org.mapstruct.ap.internal.model.source.SourceMethod r11 = (org.mapstruct.ap.internal.model.source.SourceMethod) r11
            goto Lad
        L5f:
            java.lang.Object r1 = r1.get(r5)
            org.mapstruct.ap.internal.model.source.SourceMethod r1 = (org.mapstruct.ap.internal.model.source.SourceMethod) r1
            r9.reportErrorWhenNonMatchingName(r1, r11, r0)
            goto Lac
        L69:
            int r3 = r1.size()
            if (r3 <= r4) goto Lac
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.Iterator r6 = r1.iterator()
        L78:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L92
            java.lang.Object r7 = r6.next()
            org.mapstruct.ap.internal.model.source.SourceMethod r7 = (org.mapstruct.ap.internal.model.source.SourceMethod) r7
            java.lang.String r8 = r7.getName()
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L78
            r3.add(r7)
            goto L78
        L92:
            int r2 = r3.size()
            if (r2 != r4) goto L9f
            java.lang.Object r11 = r3.get(r5)
            org.mapstruct.ap.internal.model.source.SourceMethod r11 = (org.mapstruct.ap.internal.model.source.SourceMethod) r11
            goto Lad
        L9f:
            int r2 = r3.size()
            if (r2 <= r4) goto La9
            r9.reportErrorWhenSeveralNamesMatch(r3, r11, r0)
            goto Lac
        La9:
            r9.reportErrorWhenAmbigousReverseMapping(r1, r11, r0)
        Lac:
            r11 = 0
        Lad:
            org.mapstruct.ap.internal.model.source.SourceMethod r10 = r9.extractInitializedOptions(r11, r10, r13, r12)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.internal.processor.MapperCreationProcessor.getInverseTemplateMethod(java.util.List, org.mapstruct.ap.internal.model.source.SourceMethod, java.util.List, org.mapstruct.ap.internal.model.source.MapperOptions):org.mapstruct.ap.internal.model.source.SourceMethod");
    }

    private SourceMethod extractInitializedOptions(SourceMethod sourceMethod, List<SourceMethod> list, MapperOptions mapperOptions, List<SourceMethod> list2) {
        if (sourceMethod == null) {
            return null;
        }
        if (!sourceMethod.getOptions().isFullyInitialized()) {
            mergeInheritedOptions(sourceMethod, mapperOptions, list, list2);
        }
        return sourceMethod;
    }

    private SourceMethod getForwardTemplateMethod(List<SourceMethod> list, SourceMethod sourceMethod, List<SourceMethod> list2, MapperOptions mapperOptions) {
        InheritConfigurationGem inheritConfigurationGemInstanceOn = InheritConfigurationGem.instanceOn((Element) sourceMethod.getExecutable());
        SourceMethod sourceMethod2 = null;
        if (inheritConfigurationGemInstanceOn != null) {
            ArrayList arrayList = new ArrayList();
            for (SourceMethod sourceMethod3 : list) {
                if (sourceMethod.canInheritFrom(sourceMethod3) && !sourceMethod3.equals(sourceMethod)) {
                    arrayList.add(sourceMethod3);
                }
            }
            String str = inheritConfigurationGemInstanceOn.name().get();
            if (arrayList.size() == 1) {
                SourceMethod sourceMethod4 = (SourceMethod) org.mapstruct.ap.internal.util.Collections.first(arrayList);
                if (str.isEmpty() || sourceMethod4.getName().equals(str)) {
                    sourceMethod2 = sourceMethod4;
                } else {
                    reportErrorWhenNonMatchingName(sourceMethod4, sourceMethod, inheritConfigurationGemInstanceOn);
                }
            } else if (arrayList.size() > 1) {
                ArrayList arrayList2 = new ArrayList();
                for (SourceMethod sourceMethod5 : arrayList) {
                    if (sourceMethod5.getName().equals(str)) {
                        arrayList2.add(sourceMethod5);
                    }
                }
                if (arrayList2.size() == 1) {
                    sourceMethod2 = (SourceMethod) org.mapstruct.ap.internal.util.Collections.first(arrayList2);
                } else if (arrayList2.size() > 1) {
                    reportErrorWhenSeveralNamesMatch(arrayList2, sourceMethod, inheritConfigurationGemInstanceOn);
                } else {
                    reportErrorWhenAmbigousMapping(arrayList, sourceMethod, inheritConfigurationGemInstanceOn);
                }
            }
        }
        return extractInitializedOptions(sourceMethod2, list, mapperOptions, list2);
    }

    private void reportErrorWhenAmbigousReverseMapping(List<SourceMethod> list, SourceMethod sourceMethod, InheritInverseConfigurationGem inheritInverseConfigurationGem) {
        ArrayList arrayList = new ArrayList();
        Iterator<SourceMethod> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        String str = inheritInverseConfigurationGem.name().get();
        if (str.isEmpty()) {
            this.messager.printMessage(sourceMethod.getExecutable(), inheritInverseConfigurationGem.mirror(), Message.INHERITINVERSECONFIGURATION_DUPLICATES, Strings.join(arrayList, "(), "));
        } else {
            this.messager.printMessage(sourceMethod.getExecutable(), inheritInverseConfigurationGem.mirror(), Message.INHERITINVERSECONFIGURATION_INVALID_NAME, Strings.join(arrayList, "(), "), str);
        }
    }

    private void reportErrorWhenSeveralNamesMatch(List<SourceMethod> list, SourceMethod sourceMethod, InheritInverseConfigurationGem inheritInverseConfigurationGem) {
        this.messager.printMessage(sourceMethod.getExecutable(), inheritInverseConfigurationGem.mirror(), Message.INHERITINVERSECONFIGURATION_DUPLICATE_MATCHES, inheritInverseConfigurationGem.name().get(), Strings.join(list, ", "));
    }

    private void reportErrorWhenNonMatchingName(SourceMethod sourceMethod, SourceMethod sourceMethod2, InheritInverseConfigurationGem inheritInverseConfigurationGem) {
        this.messager.printMessage(sourceMethod2.getExecutable(), inheritInverseConfigurationGem.mirror(), Message.INHERITINVERSECONFIGURATION_NO_NAME_MATCH, inheritInverseConfigurationGem.name().get(), sourceMethod.getName());
    }

    private void reportErrorWhenAmbigousMapping(List<SourceMethod> list, SourceMethod sourceMethod, InheritConfigurationGem inheritConfigurationGem) {
        ArrayList arrayList = new ArrayList();
        Iterator<SourceMethod> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        String str = inheritConfigurationGem.name().get();
        if (str.isEmpty()) {
            this.messager.printMessage(sourceMethod.getExecutable(), inheritConfigurationGem.mirror(), Message.INHERITCONFIGURATION_DUPLICATES, Strings.join(arrayList, "(), "));
        } else {
            this.messager.printMessage(sourceMethod.getExecutable(), inheritConfigurationGem.mirror(), Message.INHERITCONFIGURATION_INVALIDNAME, Strings.join(arrayList, "(), "), str);
        }
    }

    private void reportErrorWhenSeveralNamesMatch(List<SourceMethod> list, SourceMethod sourceMethod, InheritConfigurationGem inheritConfigurationGem) {
        this.messager.printMessage(sourceMethod.getExecutable(), inheritConfigurationGem.mirror(), Message.INHERITCONFIGURATION_DUPLICATE_MATCHES, inheritConfigurationGem.name().get(), Strings.join(list, ", "));
    }

    private void reportErrorWhenNonMatchingName(SourceMethod sourceMethod, SourceMethod sourceMethod2, InheritConfigurationGem inheritConfigurationGem) {
        this.messager.printMessage(sourceMethod2.getExecutable(), inheritConfigurationGem.mirror(), Message.INHERITCONFIGURATION_NO_NAME_MATCH, inheritConfigurationGem.name().get(), sourceMethod.getName());
    }
}
