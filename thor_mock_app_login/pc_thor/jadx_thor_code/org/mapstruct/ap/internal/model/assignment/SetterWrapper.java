package org.mapstruct.ap.internal.model.assignment;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class SetterWrapper extends AssignmentWrapper {
    private final boolean includeSourceNullCheck;
    private final boolean setExplicitlyToDefault;
    private final boolean setExplicitlyToNull;
    private final List<Type> thrownTypesToExclude;

    public SetterWrapper(Assignment assignment, List<Type> list, boolean z, boolean z2, boolean z3, boolean z4) {
        super(assignment, z);
        this.thrownTypesToExclude = list;
        this.includeSourceNullCheck = z2;
        this.setExplicitlyToDefault = z4;
        this.setExplicitlyToNull = z3;
    }

    public SetterWrapper(Assignment assignment, List<Type> list, boolean z) {
        super(assignment, z);
        this.thrownTypesToExclude = list;
        this.includeSourceNullCheck = false;
        this.setExplicitlyToNull = false;
        this.setExplicitlyToDefault = false;
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

    public boolean isSetExplicitlyToNull() {
        return this.setExplicitlyToNull;
    }

    public boolean isSetExplicitlyToDefault() {
        return this.setExplicitlyToDefault;
    }

    public boolean isIncludeSourceNullCheck() {
        return this.includeSourceNullCheck;
    }

    public static boolean doSourceNullCheck(Assignment assignment, NullValueCheckStrategyGem nullValueCheckStrategyGem, NullValuePropertyMappingStrategyGem nullValuePropertyMappingStrategyGem, Type type) {
        return (assignment.isSourceReferenceParameter() || assignment.getSourceType().isPrimitive() || (NullValueCheckStrategyGem.ALWAYS != nullValueCheckStrategyGem && NullValuePropertyMappingStrategyGem.SET_TO_DEFAULT != nullValuePropertyMappingStrategyGem && NullValuePropertyMappingStrategyGem.IGNORE != nullValuePropertyMappingStrategyGem && !assignment.getType().isConverted() && (!assignment.getType().isDirect() || !type.isPrimitive()))) ? false : true;
    }
}
