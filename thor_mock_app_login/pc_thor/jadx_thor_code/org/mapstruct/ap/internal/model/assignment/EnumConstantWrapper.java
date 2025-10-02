package org.mapstruct.ap.internal.model.assignment;

import java.util.HashSet;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class EnumConstantWrapper extends AssignmentWrapper {
    private final Type enumType;

    public EnumConstantWrapper(Assignment assignment, Type type) {
        super(assignment, false);
        this.enumType = type;
    }

    @Override // org.mapstruct.ap.internal.model.assignment.AssignmentWrapper, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        HashSet hashSet = new HashSet(getAssignment().getImportTypes());
        hashSet.add(this.enumType);
        return hashSet;
    }

    public String toString() {
        return this.enumType.getName() + "." + getAssignment();
    }
}
