package org.mapstruct.ap.internal.model.assignment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

/* loaded from: classes3.dex */
public class ExistingInstanceSetterWrapperForCollectionsAndMaps extends SetterWrapperForCollectionsAndMapsWithNullCheck {
    private final boolean includeElseBranch;
    private final boolean mapNullToDefault;
    private final Type targetType;

    public ExistingInstanceSetterWrapperForCollectionsAndMaps(Assignment assignment, List<Type> list, Type type, NullValueCheckStrategyGem nullValueCheckStrategyGem, NullValuePropertyMappingStrategyGem nullValuePropertyMappingStrategyGem, TypeFactory typeFactory, boolean z) {
        super(assignment, list, type, typeFactory, z);
        this.mapNullToDefault = NullValuePropertyMappingStrategyGem.SET_TO_DEFAULT == nullValuePropertyMappingStrategyGem;
        this.targetType = type;
        this.includeElseBranch = (NullValueCheckStrategyGem.ALWAYS == nullValueCheckStrategyGem || NullValuePropertyMappingStrategyGem.IGNORE == nullValuePropertyMappingStrategyGem) ? false : true;
    }

    @Override // org.mapstruct.ap.internal.model.assignment.SetterWrapperForCollectionsAndMapsWithNullCheck, org.mapstruct.ap.internal.model.assignment.AssignmentWrapper, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        HashSet hashSet = new HashSet(super.getImportTypes());
        if (isMapNullToDefault() && this.targetType.getImplementationType() != null) {
            hashSet.add(this.targetType.getImplementationType());
        }
        return hashSet;
    }

    public boolean isIncludeElseBranch() {
        return this.includeElseBranch;
    }

    public boolean isMapNullToDefault() {
        return this.mapNullToDefault;
    }
}
