package org.mapstruct.ap.internal.model.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public class ParameterBinding {
    private final boolean mappingContext;
    private final boolean mappingTarget;
    private final SourceRHS sourceRHS;
    private final boolean targetType;
    private final Type type;
    private final String variableName;

    private ParameterBinding(Type type, String str, boolean z, boolean z2, boolean z3, SourceRHS sourceRHS) {
        this.type = type;
        this.variableName = str;
        this.targetType = z2;
        this.mappingTarget = z;
        this.mappingContext = z3;
        this.sourceRHS = sourceRHS;
    }

    public String getVariableName() {
        return this.variableName;
    }

    public boolean isTargetType() {
        return this.targetType;
    }

    public boolean isMappingTarget() {
        return this.mappingTarget;
    }

    public boolean isMappingContext() {
        return this.mappingContext;
    }

    public Type getType() {
        return this.type;
    }

    public SourceRHS getSourceRHS() {
        return this.sourceRHS;
    }

    public Set<Type> getImportTypes() {
        if (this.targetType) {
            return this.type.getImportTypes();
        }
        SourceRHS sourceRHS = this.sourceRHS;
        if (sourceRHS != null) {
            return sourceRHS.getImportTypes();
        }
        return Collections.emptySet();
    }

    public static ParameterBinding fromParameter(Parameter parameter) {
        return new ParameterBinding(parameter.getType(), parameter.getName(), parameter.isMappingTarget(), parameter.isTargetType(), parameter.isMappingContext(), null);
    }

    public static List<ParameterBinding> fromParameters(List<Parameter> list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<Parameter> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(fromParameter(it.next()));
        }
        return arrayList;
    }

    public static ParameterBinding fromTypeAndName(Type type, String str) {
        return new ParameterBinding(type, str, false, false, false, null);
    }

    public static ParameterBinding forTargetTypeBinding(Type type) {
        return new ParameterBinding(type, null, false, true, false, null);
    }

    public static ParameterBinding forMappingTargetBinding(Type type) {
        return new ParameterBinding(type, null, true, false, false, null);
    }

    public static ParameterBinding forSourceTypeBinding(Type type) {
        return new ParameterBinding(type, null, false, false, false, null);
    }

    public static ParameterBinding fromSourceRHS(SourceRHS sourceRHS) {
        return new ParameterBinding(sourceRHS.getSourceType(), null, false, false, false, sourceRHS);
    }
}
