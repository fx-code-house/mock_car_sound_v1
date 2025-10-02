package org.mapstruct.ap.internal.model.assignment;

import java.util.HashSet;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class Java8FunctionWrapper extends AssignmentWrapper {
    private final Type functionType;

    public Java8FunctionWrapper(Assignment assignment) {
        this(assignment, null);
    }

    public Java8FunctionWrapper(Assignment assignment, Type type) {
        super(assignment, false);
        this.functionType = type;
    }

    @Override // org.mapstruct.ap.internal.model.assignment.AssignmentWrapper, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        Type type;
        HashSet hashSet = new HashSet(super.getImportTypes());
        if (isDirectAssignment() && (type = this.functionType) != null) {
            hashSet.add(type);
        }
        return hashSet;
    }

    public boolean isDirectAssignment() {
        return getAssignment().getType() == Assignment.AssignmentType.DIRECT;
    }
}
