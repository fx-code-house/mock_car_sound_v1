package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class AnnotatedConstructor extends ModelElement implements Constructor {
    private final List<Annotation> annotations;
    private final Set<SupportingConstructorFragment> fragments;
    private final List<AnnotationMapperReference> mapperReferences;
    private String name;
    private final NoArgumentConstructor noArgumentConstructor;

    public static AnnotatedConstructor forComponentModels(String str, List<AnnotationMapperReference> list, List<Annotation> list2, Constructor constructor, boolean z) {
        Set<SupportingConstructorFragment> set;
        NoArgumentConstructor noArgumentConstructor;
        NoArgumentConstructor noArgumentConstructor2 = null;
        NoArgumentConstructor noArgumentConstructor3 = constructor instanceof NoArgumentConstructor ? (NoArgumentConstructor) constructor : null;
        Set<SupportingConstructorFragment> setEmptySet = Collections.emptySet();
        if (z) {
            if (noArgumentConstructor3 == null) {
                noArgumentConstructor2 = new NoArgumentConstructor(str, setEmptySet);
            } else {
                noArgumentConstructor = noArgumentConstructor3;
                set = setEmptySet;
                return new AnnotatedConstructor(str, list, list2, noArgumentConstructor, set);
            }
        } else if (noArgumentConstructor3 != null) {
            setEmptySet = noArgumentConstructor3.getFragments();
        }
        set = setEmptySet;
        noArgumentConstructor = noArgumentConstructor2;
        return new AnnotatedConstructor(str, list, list2, noArgumentConstructor, set);
    }

    private AnnotatedConstructor(String str, List<AnnotationMapperReference> list, List<Annotation> list2, NoArgumentConstructor noArgumentConstructor, Set<SupportingConstructorFragment> set) {
        this.name = str;
        this.mapperReferences = list;
        this.annotations = list2;
        this.noArgumentConstructor = noArgumentConstructor;
        this.fragments = set;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        HashSet hashSet = new HashSet();
        Iterator<AnnotationMapperReference> it = this.mapperReferences.iterator();
        while (it.hasNext()) {
            hashSet.addAll(it.next().getImportTypes());
        }
        Iterator<Annotation> it2 = this.annotations.iterator();
        while (it2.hasNext()) {
            hashSet.addAll(it2.next().getImportTypes());
        }
        return hashSet;
    }

    @Override // org.mapstruct.ap.internal.model.Constructor
    public String getName() {
        return this.name;
    }

    public List<AnnotationMapperReference> getMapperReferences() {
        return this.mapperReferences;
    }

    public List<Annotation> getAnnotations() {
        return this.annotations;
    }

    public NoArgumentConstructor getNoArgumentConstructor() {
        return this.noArgumentConstructor;
    }

    public Set<SupportingConstructorFragment> getFragments() {
        return this.fragments;
    }
}
