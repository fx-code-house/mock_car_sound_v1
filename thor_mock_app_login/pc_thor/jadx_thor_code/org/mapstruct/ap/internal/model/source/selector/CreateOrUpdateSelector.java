package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/* loaded from: classes3.dex */
public class CreateOrUpdateSelector implements MethodSelector {
    @Override // org.mapstruct.ap.internal.model.source.selector.MethodSelector
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method method, List<SelectedMethod<T>> list, List<Type> list2, Type type, SelectionCriteria selectionCriteria) {
        if (selectionCriteria.isLifecycleCallbackRequired() || selectionCriteria.isObjectFactoryRequired()) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (SelectedMethod<T> selectedMethod : list) {
            if (selectedMethod.getMethod().getMappingTargetParameter() == null) {
                arrayList.add(selectedMethod);
            } else {
                arrayList2.add(selectedMethod);
            }
        }
        return (!selectionCriteria.isPreferUpdateMapping() || arrayList2.isEmpty()) ? arrayList : arrayList2;
    }
}
