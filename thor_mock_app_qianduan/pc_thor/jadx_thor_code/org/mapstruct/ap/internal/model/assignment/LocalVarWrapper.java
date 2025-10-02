package org.mapstruct.ap.internal.model.assignment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class LocalVarWrapper extends AssignmentWrapper {
    private final Type targetType;
    private final List<Type> thrownTypesToExclude;

    public LocalVarWrapper(Assignment assignment, List<Type> list, Type type, boolean z) {
        super(assignment, z);
        this.thrownTypesToExclude = list;
        this.targetType = type;
    }

    @Override // org.mapstruct.ap.internal.model.assignment.AssignmentWrapper, org.mapstruct.ap.internal.model.common.Assignment
    public List<Type> getThrownTypes() {
        List<Type> thrownTypes = super.getThrownTypes();
        ArrayList arrayList = new ArrayList(thrownTypes);
        for (Type type : this.thrownTypesToExclude) {
            for (Type type2 : thrownTypes) {
                if (type2.isAssignableTo(type)) {
                    arrayList.remove(type2);
                }
            }
        }
        return arrayList;
    }

    @Override // org.mapstruct.ap.internal.model.assignment.AssignmentWrapper, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        HashSet hashSet = new HashSet(getAssignment().getImportTypes());
        hashSet.add(this.targetType);
        hashSet.addAll(this.targetType.getTypeParameters());
        return hashSet;
    }
}
