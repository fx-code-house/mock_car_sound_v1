package org.mapstruct.ap.internal.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class AnnotationMapperReference extends MapperReference {
    private final List<Annotation> annotations;
    private final boolean fieldFinal;
    private final boolean includeAnnotationsOnField;

    public AnnotationMapperReference(Type type, String str, List<Annotation> list, boolean z, boolean z2, boolean z3) {
        super(type, str, z);
        this.annotations = list;
        this.fieldFinal = z2;
        this.includeAnnotationsOnField = z3;
    }

    public List<Annotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.mapstruct.ap.internal.model.Field, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        HashSet hashSet = new HashSet();
        hashSet.add(getType());
        Iterator<Annotation> it = this.annotations.iterator();
        while (it.hasNext()) {
            hashSet.addAll(it.next().getImportTypes());
        }
        return hashSet;
    }

    public boolean isFieldFinal() {
        return this.fieldFinal;
    }

    public boolean isIncludeAnnotationsOnField() {
        return this.includeAnnotationsOnField;
    }

    public AnnotationMapperReference withNewAnnotations(List<Annotation> list) {
        return new AnnotationMapperReference(getType(), getVariableName(), list, isUsed(), isFieldFinal(), isIncludeAnnotationsOnField());
    }
}
