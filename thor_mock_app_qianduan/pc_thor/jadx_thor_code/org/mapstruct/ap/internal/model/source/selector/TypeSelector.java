package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/* loaded from: classes3.dex */
public class TypeSelector implements MethodSelector {
    private FormattingMessager messager;
    private TypeFactory typeFactory;

    public TypeSelector(TypeFactory typeFactory, FormattingMessager formattingMessager) {
        this.typeFactory = typeFactory;
        this.messager = formattingMessager;
    }

    @Override // org.mapstruct.ap.internal.model.source.selector.MethodSelector
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method method, List<SelectedMethod<T>> list, List<Type> list2, Type type, SelectionCriteria selectionCriteria) {
        List<ParameterBinding> availableParameterBindingsFromSourceTypes;
        SelectedMethod<T> matchingParameterBinding;
        if (list.isEmpty()) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        if (list2.isEmpty()) {
            availableParameterBindingsFromSourceTypes = getAvailableParameterBindingsFromMethod(method, type, selectionCriteria.getSourceRHS());
        } else {
            availableParameterBindingsFromSourceTypes = getAvailableParameterBindingsFromSourceTypes(list2, type, method);
        }
        for (SelectedMethod<T> selectedMethod : list) {
            List<List<ParameterBinding>> candidateParameterBindingPermutations = getCandidateParameterBindingPermutations(availableParameterBindingsFromSourceTypes, selectedMethod.getMethod().getParameters());
            if (candidateParameterBindingPermutations != null && (matchingParameterBinding = getMatchingParameterBinding(type, method, selectedMethod, candidateParameterBindingPermutations)) != null) {
                arrayList.add(matchingParameterBinding);
            }
        }
        return arrayList;
    }

    private List<ParameterBinding> getAvailableParameterBindingsFromMethod(Method method, Type type, SourceRHS sourceRHS) {
        ArrayList arrayList = new ArrayList(method.getParameters().size() + 3);
        if (sourceRHS != null) {
            arrayList.addAll(ParameterBinding.fromParameters(method.getContextParameters()));
            arrayList.add(ParameterBinding.fromSourceRHS(sourceRHS));
        } else {
            arrayList.addAll(ParameterBinding.fromParameters(method.getParameters()));
        }
        addMappingTargetAndTargetTypeBindings(arrayList, type);
        return arrayList;
    }

    private List<ParameterBinding> getAvailableParameterBindingsFromSourceTypes(List<Type> list, Type type, Method method) {
        ArrayList arrayList = new ArrayList(list.size() + 2);
        Iterator<Type> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(ParameterBinding.forSourceTypeBinding(it.next()));
        }
        for (Parameter parameter : method.getParameters()) {
            if (parameter.isMappingContext()) {
                arrayList.add(ParameterBinding.fromParameter(parameter));
            }
        }
        addMappingTargetAndTargetTypeBindings(arrayList, type);
        return arrayList;
    }

    private void addMappingTargetAndTargetTypeBindings(List<ParameterBinding> list, Type type) {
        boolean z = false;
        boolean z2 = false;
        for (ParameterBinding parameterBinding : list) {
            if (parameterBinding.isMappingTarget()) {
                z = true;
            } else if (parameterBinding.isTargetType()) {
                z2 = true;
            }
        }
        if (!z) {
            list.add(ParameterBinding.forMappingTargetBinding(type));
        }
        if (z2) {
            return;
        }
        list.add(ParameterBinding.forTargetTypeBinding(this.typeFactory.classTypeOf(type)));
    }

    private <T extends Method> SelectedMethod<T> getMatchingParameterBinding(final Type type, Method method, SelectedMethod<T> selectedMethod, List<List<ParameterBinding>> list) {
        ArrayList arrayList = new ArrayList(list);
        final Method method2 = selectedMethod.getMethod();
        arrayList.removeIf(new Predicate() { // from class: org.mapstruct.ap.internal.model.source.selector.TypeSelector$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return TypeSelector.lambda$getMatchingParameterBinding$0(method2, type, (List) obj);
            }
        });
        if (arrayList.isEmpty()) {
            return null;
        }
        if (arrayList.size() == 1) {
            selectedMethod.setParameterBindings((List) Collections.first(arrayList));
            return selectedMethod;
        }
        final List<Parameter> parameters = method2.getParameters();
        arrayList.removeIf(new Predicate() { // from class: org.mapstruct.ap.internal.model.source.selector.TypeSelector$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m2371xce4f8c75(parameters, (List) obj);
            }
        });
        if (arrayList.isEmpty()) {
            this.messager.printMessage(method2.getExecutable(), Message.LIFECYCLEMETHOD_AMBIGUOUS_PARAMETERS, method);
            return null;
        }
        selectedMethod.setParameterBindings((List) Collections.first(arrayList));
        return selectedMethod;
    }

    static /* synthetic */ boolean lambda$getMatchingParameterBinding$0(Method method, Type type, List list) {
        return !method.matches(extractTypes(list), type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: parameterBindingNotMatchesParameterVariableNames, reason: merged with bridge method [inline-methods] */
    public boolean m2371xce4f8c75(List<ParameterBinding> list, List<Parameter> list2) {
        if (list.size() != list2.size()) {
            return true;
        }
        int i = 0;
        for (ParameterBinding parameterBinding : list) {
            int i2 = i + 1;
            Parameter parameter = list2.get(i);
            if (parameterBinding.getVariableName() != null && !parameter.getName().equals(parameterBinding.getVariableName())) {
                return true;
            }
            i = i2;
        }
        return false;
    }

    private static List<List<ParameterBinding>> getCandidateParameterBindingPermutations(List<ParameterBinding> list, List<Parameter> list2) {
        if (list2.size() > list.size()) {
            return null;
        }
        ArrayList<List> arrayList = new ArrayList(1);
        arrayList.add(new ArrayList(list2.size()));
        Iterator<Parameter> it = list2.iterator();
        while (it.hasNext()) {
            List<ParameterBinding> listFindCandidateBindingsForParameter = findCandidateBindingsForParameter(list, it.next());
            if (listFindCandidateBindingsForParameter.isEmpty()) {
                return null;
            }
            if (listFindCandidateBindingsForParameter.size() == 1) {
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    ((List) it2.next()).add(Collections.first(listFindCandidateBindingsForParameter));
                }
            } else {
                ArrayList arrayList2 = new ArrayList(arrayList.size() * listFindCandidateBindingsForParameter.size());
                for (List list3 : arrayList) {
                    for (ParameterBinding parameterBinding : listFindCandidateBindingsForParameter) {
                        ArrayList arrayList3 = new ArrayList(list2.size());
                        arrayList3.addAll(list3);
                        arrayList3.add(parameterBinding);
                        arrayList2.add(arrayList3);
                    }
                }
                arrayList = arrayList2;
            }
        }
        return arrayList;
    }

    private static List<ParameterBinding> findCandidateBindingsForParameter(List<ParameterBinding> list, Parameter parameter) {
        ArrayList arrayList = new ArrayList(list.size());
        for (ParameterBinding parameterBinding : list) {
            if (parameter.isTargetType() == parameterBinding.isTargetType() && parameter.isMappingTarget() == parameterBinding.isMappingTarget() && parameter.isMappingContext() == parameterBinding.isMappingContext()) {
                arrayList.add(parameterBinding);
            }
        }
        return arrayList;
    }

    private static List<Type> extractTypes(List<ParameterBinding> list) {
        return (List) list.stream().map(new Function() { // from class: org.mapstruct.ap.internal.model.source.selector.TypeSelector$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ParameterBinding) obj).getType();
            }
        }).collect(Collectors.toList());
    }
}
