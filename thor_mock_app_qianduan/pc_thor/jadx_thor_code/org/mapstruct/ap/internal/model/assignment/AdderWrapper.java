package org.mapstruct.ap.internal.model.assignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Nouns;

/* loaded from: classes3.dex */
public class AdderWrapper extends AssignmentWrapper {
    private final Type adderType;
    private final List<Type> thrownTypesToExclude;

    public boolean isIncludeSourceNullCheck() {
        return true;
    }

    public boolean isSetExplicitlyToDefault() {
        return false;
    }

    public boolean isSetExplicitlyToNull() {
        return false;
    }

    public AdderWrapper(Assignment assignment, List<Type> list, boolean z, String str) {
        super(assignment, z);
        this.thrownTypesToExclude = list;
        assignment.setSourceLoopVarName(assignment.createUniqueVarName(Nouns.singularize(str)));
        this.adderType = (Type) Collections.first(getSourceType().determineTypeArguments(Collection.class));
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

    public Type getAdderType() {
        return this.adderType;
    }

    @Override // org.mapstruct.ap.internal.model.assignment.AssignmentWrapper, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        HashSet hashSet = new HashSet(super.getImportTypes());
        hashSet.add(this.adderType.getTypeBound());
        return hashSet;
    }
}
