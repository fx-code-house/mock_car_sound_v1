package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.MethodSelectors;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;

/* loaded from: classes3.dex */
public final class LifecycleMethodResolver {
    private LifecycleMethodResolver() {
    }

    public static List<LifecycleCallbackMethodReference> beforeMappingMethods(Method method, Type type, SelectionParameters selectionParameters, MappingBuilderContext mappingBuilderContext, Set<String> set) {
        return collectLifecycleCallbackMethods(method, type, selectionParameters, filterBeforeMappingMethods(getAllAvailableMethods(method, mappingBuilderContext.getSourceModel())), mappingBuilderContext, set);
    }

    public static List<LifecycleCallbackMethodReference> afterMappingMethods(Method method, Type type, SelectionParameters selectionParameters, MappingBuilderContext mappingBuilderContext, Set<String> set) {
        return collectLifecycleCallbackMethods(method, type, selectionParameters, filterAfterMappingMethods(getAllAvailableMethods(method, mappingBuilderContext.getSourceModel())), mappingBuilderContext, set);
    }

    public static List<LifecycleCallbackMethodReference> beforeMappingMethods(Method method, SelectionParameters selectionParameters, MappingBuilderContext mappingBuilderContext, Set<String> set) {
        return collectLifecycleCallbackMethods(method, method.getResultType(), selectionParameters, filterBeforeMappingMethods(getAllAvailableMethods(method, mappingBuilderContext.getSourceModel())), mappingBuilderContext, set);
    }

    public static List<LifecycleCallbackMethodReference> afterMappingMethods(Method method, SelectionParameters selectionParameters, MappingBuilderContext mappingBuilderContext, Set<String> set) {
        return collectLifecycleCallbackMethods(method, method.getResultType(), selectionParameters, filterAfterMappingMethods(getAllAvailableMethods(method, mappingBuilderContext.getSourceModel())), mappingBuilderContext, set);
    }

    private static List<SourceMethod> getAllAvailableMethods(Method method, List<SourceMethod> list) {
        ParameterProvidedMethods contextProvidedMethods = method.getContextProvidedMethods();
        if (contextProvidedMethods.isEmpty()) {
            return list;
        }
        List<SourceMethod> allProvidedMethodsInParameterOrder = contextProvidedMethods.getAllProvidedMethodsInParameterOrder(method.getContextParameters());
        ArrayList arrayList = new ArrayList(allProvidedMethodsInParameterOrder.size() + list.size());
        arrayList.addAll(allProvidedMethodsInParameterOrder);
        arrayList.addAll(list);
        return arrayList;
    }

    private static List<LifecycleCallbackMethodReference> collectLifecycleCallbackMethods(Method method, Type type, SelectionParameters selectionParameters, List<SourceMethod> list, MappingBuilderContext mappingBuilderContext, Set<String> set) {
        return toLifecycleCallbackMethodRefs(method, new MethodSelectors(mappingBuilderContext.getTypeUtils(), mappingBuilderContext.getElementUtils(), mappingBuilderContext.getTypeFactory(), mappingBuilderContext.getMessager()).getMatchingMethods(method, list, Collections.emptyList(), type, SelectionCriteria.forLifecycleMethods(selectionParameters)), mappingBuilderContext, set);
    }

    private static List<LifecycleCallbackMethodReference> toLifecycleCallbackMethodRefs(Method method, List<SelectedMethod<SourceMethod>> list, MappingBuilderContext mappingBuilderContext, Set<String> set) {
        ArrayList arrayList = new ArrayList();
        for (SelectedMethod<SourceMethod> selectedMethod : list) {
            Parameter parameterForProvidedMethod = method.getContextProvidedMethods().getParameterForProvidedMethod(selectedMethod.getMethod());
            if (parameterForProvidedMethod != null) {
                arrayList.add(LifecycleCallbackMethodReference.forParameterProvidedMethod(selectedMethod, parameterForProvidedMethod, method, set));
            } else {
                arrayList.add(LifecycleCallbackMethodReference.forMethodReference(selectedMethod, MapperReference.findMapperReference(mappingBuilderContext.getMapperReferences(), (SourceMethod) selectedMethod.getMethod()), method, set));
            }
        }
        return arrayList;
    }

    private static List<SourceMethod> filterBeforeMappingMethods(List<SourceMethod> list) {
        return (List) list.stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.model.LifecycleMethodResolver$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((SourceMethod) obj).isBeforeMappingMethod();
            }
        }).collect(Collectors.toList());
    }

    private static List<SourceMethod> filterAfterMappingMethods(List<SourceMethod> list) {
        return (List) list.stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.model.LifecycleMethodResolver$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((SourceMethod) obj).isAfterMappingMethod();
            }
        }).collect(Collectors.toList());
    }
}
