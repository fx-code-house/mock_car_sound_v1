package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.model.common.SourceRHS;

/* loaded from: classes3.dex */
public class SelectionParameters {
    private final List<TypeMirror> qualifiers;
    private final List<String> qualifyingNames;
    private final TypeMirror resultType;
    private final SourceRHS sourceRHS;
    private final Types typeUtils;

    public static SelectionParameters forInheritance(SelectionParameters selectionParameters) {
        return new SelectionParameters(selectionParameters.qualifiers, selectionParameters.qualifyingNames, null, selectionParameters.typeUtils);
    }

    public SelectionParameters(List<TypeMirror> list, List<String> list2, TypeMirror typeMirror, Types types) {
        this(list, list2, typeMirror, types, null);
    }

    private SelectionParameters(List<TypeMirror> list, List<String> list2, TypeMirror typeMirror, Types types, SourceRHS sourceRHS) {
        this.qualifiers = list;
        this.qualifyingNames = list2;
        this.resultType = typeMirror;
        this.typeUtils = types;
        this.sourceRHS = sourceRHS;
    }

    public List<TypeMirror> getQualifiers() {
        return this.qualifiers;
    }

    public List<String> getQualifyingNames() {
        return this.qualifyingNames;
    }

    public TypeMirror getResultType() {
        return this.resultType;
    }

    public SourceRHS getSourceRHS() {
        return this.sourceRHS;
    }

    public int hashCode() {
        List<String> list = this.qualifyingNames;
        int iHashCode = (291 + (list != null ? list.hashCode() : 0)) * 97;
        TypeMirror typeMirror = this.resultType;
        return iHashCode + (typeMirror != null ? typeMirror.toString().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SelectionParameters selectionParameters = (SelectionParameters) obj;
        if (equals(this.qualifiers, selectionParameters.qualifiers) && Objects.equals(this.qualifyingNames, selectionParameters.qualifyingNames) && Objects.equals(this.sourceRHS, selectionParameters.sourceRHS)) {
            return equals(this.resultType, selectionParameters.resultType);
        }
        return false;
    }

    private boolean equals(List<TypeMirror> list, List<TypeMirror> list2) {
        if (list == null) {
            return list2 == null;
        }
        if (list2 == null || list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!equals(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean equals(TypeMirror typeMirror, TypeMirror typeMirror2) {
        return typeMirror == null ? typeMirror2 == null : typeMirror2 != null && this.typeUtils.isSameType(typeMirror, typeMirror2);
    }

    public static SelectionParameters forSourceRHS(SourceRHS sourceRHS) {
        return new SelectionParameters(Collections.emptyList(), Collections.emptyList(), null, null, sourceRHS);
    }
}
