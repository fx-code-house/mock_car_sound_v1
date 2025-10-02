package org.mapstruct.ap.internal.model.source.selector;

import java.util.List;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/* loaded from: classes3.dex */
interface MethodSelector {
    <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method method, List<SelectedMethod<T>> list, List<Type> list2, Type type, SelectionCriteria selectionCriteria);
}
