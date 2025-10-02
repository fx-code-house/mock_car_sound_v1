package org.mapstruct.ap.internal.model.assignment;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class WrapperForCollectionsAndMaps extends AssignmentWrapper {
    private final String nullCheckLocalVarName;
    private final Type nullCheckLocalVarType;
    private final List<Type> thrownTypesToExclude;

    public WrapperForCollectionsAndMaps(Assignment assignment, List<Type> list, Type type, boolean z) {
        super(assignment, z);
        this.thrownTypesToExclude = list;
        if (assignment.getType() == Assignment.AssignmentType.DIRECT && assignment.getSourceType() != null) {
            this.nullCheckLocalVarType = assignment.getSourceType();
        } else {
            this.nullCheckLocalVarType = type;
        }
        this.nullCheckLocalVarName = assignment.createUniqueVarName(this.nullCheckLocalVarType.getName());
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

    public String getNullCheckLocalVarName() {
        return this.nullCheckLocalVarName;
    }

    public Type getNullCheckLocalVarType() {
        return this.nullCheckLocalVarType;
    }
}
