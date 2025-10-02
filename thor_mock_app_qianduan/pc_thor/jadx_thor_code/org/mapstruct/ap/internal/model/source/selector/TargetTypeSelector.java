package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/* loaded from: classes3.dex */
public class TargetTypeSelector implements MethodSelector {
    private final Types typeUtils;

    public TargetTypeSelector(Types types) {
        this.typeUtils = types;
    }

    @Override // org.mapstruct.ap.internal.model.source.selector.MethodSelector
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method method, List<SelectedMethod<T>> list, List<Type> list2, Type type, SelectionCriteria selectionCriteria) {
        TypeMirror qualifyingResultType = selectionCriteria.getQualifyingResultType();
        if (qualifyingResultType == null || selectionCriteria.isLifecycleCallbackRequired()) {
            return list;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (SelectedMethod<T> selectedMethod : list) {
            if (this.typeUtils.isSameType(qualifyingResultType, selectedMethod.getMethod().getResultType().getTypeElement().asType())) {
                arrayList.add(selectedMethod);
            }
        }
        return arrayList;
    }
}
