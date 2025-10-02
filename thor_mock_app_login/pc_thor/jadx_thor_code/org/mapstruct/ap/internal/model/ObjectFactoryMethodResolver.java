package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import org.mapstruct.ap.internal.model.common.BuilderType;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.MethodSelectors;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Message;

/* loaded from: classes3.dex */
public class ObjectFactoryMethodResolver {
    private ObjectFactoryMethodResolver() {
    }

    public static MethodReference getFactoryMethod(Method method, SelectionParameters selectionParameters, MappingBuilderContext mappingBuilderContext) {
        return getFactoryMethod(method, method.getResultType(), selectionParameters, mappingBuilderContext);
    }

    public static MethodReference getFactoryMethod(Method method, Type type, SelectionParameters selectionParameters, MappingBuilderContext mappingBuilderContext) {
        List<SelectedMethod<SourceMethod>> matchingFactoryMethods = getMatchingFactoryMethods(method, type, selectionParameters, mappingBuilderContext);
        if (matchingFactoryMethods.isEmpty()) {
            return null;
        }
        if (matchingFactoryMethods.size() > 1) {
            mappingBuilderContext.getMessager().printMessage(method.getExecutable(), Message.GENERAL_AMBIGUOUS_FACTORY_METHOD, type.describe(), matchingFactoryMethods.stream().map(new BeanMappingMethod$Builder$$ExternalSyntheticLambda0()).map(new BeanMappingMethod$Builder$$ExternalSyntheticLambda1()).collect(Collectors.joining(", ")));
            return null;
        }
        return getFactoryMethodReference(method, (SelectedMethod) Collections.first(matchingFactoryMethods), mappingBuilderContext);
    }

    public static MethodReference getFactoryMethodReference(Method method, SelectedMethod<SourceMethod> selectedMethod, MappingBuilderContext mappingBuilderContext) {
        Parameter parameterForProvidedMethod = method.getContextProvidedMethods().getParameterForProvidedMethod(selectedMethod.getMethod());
        if (parameterForProvidedMethod != null) {
            return MethodReference.forParameterProvidedMethod(selectedMethod.getMethod(), parameterForProvidedMethod, selectedMethod.getParameterBindings());
        }
        return MethodReference.forMapperReference(selectedMethod.getMethod(), MapperReference.findMapperReference(mappingBuilderContext.getMapperReferences(), (SourceMethod) selectedMethod.getMethod()), selectedMethod.getParameterBindings());
    }

    public static List<SelectedMethod<SourceMethod>> getMatchingFactoryMethods(Method method, Type type, SelectionParameters selectionParameters, MappingBuilderContext mappingBuilderContext) {
        return new MethodSelectors(mappingBuilderContext.getTypeUtils(), mappingBuilderContext.getElementUtils(), mappingBuilderContext.getTypeFactory(), mappingBuilderContext.getMessager()).getMatchingMethods(method, getAllAvailableMethods(method, mappingBuilderContext.getSourceModel()), java.util.Collections.emptyList(), type, SelectionCriteria.forFactoryMethods(selectionParameters));
    }

    public static MethodReference getBuilderFactoryMethod(Method method, BuilderType builderType) {
        if (builderType == null) {
            return null;
        }
        ExecutableElement builderCreationMethod = builderType.getBuilderCreationMethod();
        if (builderCreationMethod.getKind() != ElementKind.CONSTRUCTOR && builderType.getBuildingType().isAssignableTo(method.getReturnType())) {
            return MethodReference.forStaticBuilder(builderCreationMethod.getSimpleName().toString(), builderType.getOwningType());
        }
        return null;
    }

    private static List<SourceMethod> getAllAvailableMethods(Method method, List<SourceMethod> list) {
        ParameterProvidedMethods contextProvidedMethods = method.getContextProvidedMethods();
        if (contextProvidedMethods.isEmpty()) {
            return list;
        }
        List<SourceMethod> allProvidedMethodsInParameterOrder = contextProvidedMethods.getAllProvidedMethodsInParameterOrder(method.getContextParameters());
        ArrayList arrayList = new ArrayList(allProvidedMethodsInParameterOrder.size() + list.size());
        for (SourceMethod sourceMethod : allProvidedMethodsInParameterOrder) {
            if (sourceMethod.hasObjectFactoryAnnotation()) {
                arrayList.add(sourceMethod);
            }
        }
        arrayList.addAll(list);
        return arrayList;
    }
}
