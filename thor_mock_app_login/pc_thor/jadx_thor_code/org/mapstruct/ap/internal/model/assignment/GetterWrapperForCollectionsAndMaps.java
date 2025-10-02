package org.mapstruct.ap.internal.model.assignment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class GetterWrapperForCollectionsAndMaps extends WrapperForCollectionsAndMaps {
    public GetterWrapperForCollectionsAndMaps(Assignment assignment, List<Type> list, Type type, boolean z) {
        super(assignment, list, type, z);
    }

    @Override // org.mapstruct.ap.internal.model.assignment.AssignmentWrapper, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        HashSet hashSet = new HashSet(super.getImportTypes());
        if (getSourcePresenceCheckerReference() == null) {
            hashSet.addAll(getNullCheckLocalVarType().getImportTypes());
        }
        return hashSet;
    }
}
