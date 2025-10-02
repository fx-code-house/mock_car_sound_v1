package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/* loaded from: classes3.dex */
public class FactoryParameterSelector implements MethodSelector {
    @Override // org.mapstruct.ap.internal.model.source.selector.MethodSelector
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method method, List<SelectedMethod<T>> list, List<Type> list2, Type type, SelectionCriteria selectionCriteria) {
        if (selectionCriteria.isObjectFactoryRequired() && list.size() > 1) {
            ArrayList arrayList = new ArrayList(list.size());
            for (SelectedMethod<T> selectedMethod : list) {
                if (!selectedMethod.getMethod().getSourceParameters().isEmpty()) {
                    arrayList.add(selectedMethod);
                }
            }
            if (arrayList.size() == 1) {
                return arrayList;
            }
        }
        return list;
    }
}
