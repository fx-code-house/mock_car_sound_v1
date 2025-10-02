package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class InheritanceSelector implements MethodSelector {
    @Override // org.mapstruct.ap.internal.model.source.selector.MethodSelector
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method method, List<SelectedMethod<T>> list, List<Type> list2, Type type, SelectionCriteria selectionCriteria) {
        if (list2.size() != 1) {
            return list;
        }
        Type type2 = (Type) Collections.first(list2);
        ArrayList arrayList = new ArrayList();
        int iAddToCandidateListIfMinimal = Integer.MAX_VALUE;
        for (SelectedMethod<T> selectedMethod : list) {
            iAddToCandidateListIfMinimal = addToCandidateListIfMinimal(arrayList, iAddToCandidateListIfMinimal, selectedMethod, type2.distanceTo(((Parameter) Collections.first(selectedMethod.getMethod().getSourceParameters())).getType()));
        }
        return arrayList;
    }

    private <T extends Method> int addToCandidateListIfMinimal(List<SelectedMethod<T>> list, int i, SelectedMethod<T> selectedMethod, int i2) {
        if (i2 == i) {
            list.add(selectedMethod);
            return i;
        }
        if (i2 >= i) {
            return i;
        }
        list.clear();
        list.add(selectedMethod);
        return i2;
    }
}
