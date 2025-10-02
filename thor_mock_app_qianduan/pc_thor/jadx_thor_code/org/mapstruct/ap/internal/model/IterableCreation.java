package org.mapstruct.ap.internal.model;

import java.util.HashSet;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class IterableCreation extends ModelElement {
    private final boolean canUseSize;
    private final MethodReference factoryMethod;
    private final boolean loadFactorAdjustment;
    private final Type resultType;
    private final Parameter sourceParameter;

    private IterableCreation(Type type, Parameter parameter, MethodReference methodReference) {
        this.resultType = type;
        this.sourceParameter = parameter;
        this.factoryMethod = methodReference;
        boolean z = (parameter.getType().isCollectionOrMapType() || parameter.getType().isArrayType()) && type.getImplementation() != null && type.getImplementation().hasInitialCapacityConstructor();
        this.canUseSize = z;
        this.loadFactorAdjustment = z && type.getImplementation().isLoadFactorAdjustment();
    }

    public static IterableCreation create(NormalTypeMappingMethod normalTypeMappingMethod, Parameter parameter) {
        return new IterableCreation(normalTypeMappingMethod.getResultType(), parameter, normalTypeMappingMethod.getFactoryMethod());
    }

    public Type getResultType() {
        return this.resultType;
    }

    public Parameter getSourceParameter() {
        return this.sourceParameter;
    }

    public MethodReference getFactoryMethod() {
        return this.factoryMethod;
    }

    public boolean isCanUseSize() {
        return this.canUseSize;
    }

    public boolean isLoadFactorAdjustment() {
        return this.loadFactorAdjustment;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        HashSet hashSet = new HashSet();
        if (this.factoryMethod == null && this.resultType.getImplementationType() != null) {
            hashSet.addAll(this.resultType.getImplementationType().getImportTypes());
        }
        if (isEnumSet()) {
            hashSet.add(getEnumSetElementType());
            hashSet.add(this.resultType);
        }
        return hashSet;
    }

    public Type getEnumSetElementType() {
        return (Type) Collections.first(getResultType().determineTypeArguments(Iterable.class));
    }

    public boolean isEnumSet() {
        return "java.util.EnumSet".equals(this.resultType.getFullyQualifiedName());
    }
}
