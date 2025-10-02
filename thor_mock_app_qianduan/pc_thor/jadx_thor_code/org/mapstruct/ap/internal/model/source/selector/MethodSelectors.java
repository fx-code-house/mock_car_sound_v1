package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.FormattingMessager;

/* loaded from: classes3.dex */
public class MethodSelectors {
    private final List<MethodSelector> selectors;

    public MethodSelectors(Types types, Elements elements, TypeFactory typeFactory, FormattingMessager formattingMessager) {
        this.selectors = Arrays.asList(new MethodFamilySelector(), new TypeSelector(typeFactory, formattingMessager), new QualifierSelector(types, elements), new TargetTypeSelector(types), new XmlElementDeclSelector(types), new InheritanceSelector(), new CreateOrUpdateSelector(), new FactoryParameterSelector());
    }

    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method method, List<T> list, List<Type> list2, Type type, SelectionCriteria selectionCriteria) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new SelectedMethod(it.next()));
        }
        Iterator<MethodSelector> it2 = this.selectors.iterator();
        List<SelectedMethod<T>> matchingMethods = arrayList;
        while (it2.hasNext()) {
            matchingMethods = it2.next().getMatchingMethods(method, matchingMethods, list2, type, selectionCriteria);
        }
        return matchingMethods;
    }
}
