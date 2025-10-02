package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.gem.NamedGem;
import org.mapstruct.ap.internal.gem.QualifierGem;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SourceMethod;

/* loaded from: classes3.dex */
public class QualifierSelector implements MethodSelector {
    private final TypeMirror namedAnnotationTypeMirror;
    private final Types typeUtils;

    public QualifierSelector(Types types, Elements elements) {
        this.typeUtils = types;
        this.namedAnnotationTypeMirror = elements.getTypeElement("org.mapstruct.Named").asType();
    }

    @Override // org.mapstruct.ap.internal.model.source.selector.MethodSelector
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method method, List<SelectedMethod<T>> list, List<Type> list2, Type type, SelectionCriteria selectionCriteria) {
        int size;
        ArrayList arrayList = new ArrayList();
        if (selectionCriteria.getQualifiers() != null) {
            arrayList.addAll(selectionCriteria.getQualifiers());
            size = selectionCriteria.getQualifiers().size() + 0;
        } else {
            size = 0;
        }
        ArrayList arrayList2 = new ArrayList();
        if (selectionCriteria.getQualifiedByNames() != null) {
            arrayList2.addAll(selectionCriteria.getQualifiedByNames());
            size += selectionCriteria.getQualifiedByNames().size();
        }
        if (!arrayList2.isEmpty()) {
            arrayList.add(this.namedAnnotationTypeMirror);
        }
        if (arrayList.isEmpty()) {
            ArrayList arrayList3 = new ArrayList(list.size());
            for (SelectedMethod<T> selectedMethod : list) {
                if (selectedMethod.getMethod() instanceof SourceMethod) {
                    if (getQualifierAnnotationMirrors(selectedMethod.getMethod()).isEmpty()) {
                        arrayList3.add(selectedMethod);
                    }
                } else {
                    arrayList3.add(selectedMethod);
                }
            }
            return arrayList3;
        }
        ArrayList arrayList4 = new ArrayList(list.size());
        for (SelectedMethod<T> selectedMethod2 : list) {
            if (selectedMethod2.getMethod() instanceof SourceMethod) {
                int i = 0;
                for (AnnotationMirror annotationMirror : getQualifierAnnotationMirrors(selectedMethod2.getMethod())) {
                    Iterator it = arrayList.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            TypeMirror typeMirror = (TypeMirror) it.next();
                            DeclaredType annotationType = annotationMirror.getAnnotationType();
                            if (this.typeUtils.isSameType(typeMirror, annotationType)) {
                                if (this.typeUtils.isSameType(annotationType, this.namedAnnotationTypeMirror)) {
                                    NamedGem namedGemInstanceOn = NamedGem.instanceOn(annotationMirror);
                                    if (!namedGemInstanceOn.value().hasValue() || !arrayList2.contains(namedGemInstanceOn.value().get())) {
                                    }
                                }
                                i++;
                            }
                        }
                    }
                }
                if (i == size) {
                    arrayList4.add(selectedMethod2);
                }
            }
        }
        return arrayList4;
    }

    private Set<AnnotationMirror> getQualifierAnnotationMirrors(Method method) {
        HashSet hashSet = new HashSet();
        Iterator it = ((SourceMethod) method).getExecutable().getAnnotationMirrors().iterator();
        while (it.hasNext()) {
            addOnlyWhenQualifier(hashSet, (AnnotationMirror) it.next());
        }
        Type declaringMapper = method.getDeclaringMapper();
        if (declaringMapper != null) {
            Iterator it2 = declaringMapper.getTypeElement().getAnnotationMirrors().iterator();
            while (it2.hasNext()) {
                addOnlyWhenQualifier(hashSet, (AnnotationMirror) it2.next());
            }
        }
        return hashSet;
    }

    private void addOnlyWhenQualifier(Set<AnnotationMirror> set, AnnotationMirror annotationMirror) {
        if (QualifierGem.instanceOn(annotationMirror.getAnnotationType().asElement()) != null) {
            set.add(annotationMirror);
        }
    }
}
