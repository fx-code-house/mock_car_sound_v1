package org.mapstruct.ap.internal.model.source;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.gem.InjectionStrategyGem;
import org.mapstruct.ap.internal.gem.MappingInheritanceStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;
import org.mapstruct.ap.spi.TypeHierarchyErroneousException;

/* loaded from: classes3.dex */
public abstract class DelegatingOptions {
    private final DelegatingOptions next;

    public abstract boolean hasAnnotation();

    public DelegatingOptions(DelegatingOptions delegatingOptions) {
        this.next = delegatingOptions;
    }

    public String implementationName() {
        return this.next.implementationName();
    }

    public String implementationPackage() {
        return this.next.implementationPackage();
    }

    public Set<DeclaredType> uses() {
        return this.next.uses();
    }

    public Set<DeclaredType> imports() {
        return this.next.imports();
    }

    public ReportingPolicyGem unmappedTargetPolicy() {
        return this.next.unmappedTargetPolicy();
    }

    public ReportingPolicyGem unmappedSourcePolicy() {
        return this.next.unmappedSourcePolicy();
    }

    public ReportingPolicyGem typeConversionPolicy() {
        return this.next.typeConversionPolicy();
    }

    public String componentModel() {
        return this.next.componentModel();
    }

    public MappingInheritanceStrategyGem getMappingInheritanceStrategy() {
        return this.next.getMappingInheritanceStrategy();
    }

    public InjectionStrategyGem getInjectionStrategy() {
        return this.next.getInjectionStrategy();
    }

    public Boolean isDisableSubMappingMethodsGeneration() {
        return this.next.isDisableSubMappingMethodsGeneration();
    }

    public CollectionMappingStrategyGem getCollectionMappingStrategy() {
        return this.next.getCollectionMappingStrategy();
    }

    public NullValueCheckStrategyGem getNullValueCheckStrategy() {
        return this.next.getNullValueCheckStrategy();
    }

    public NullValuePropertyMappingStrategyGem getNullValuePropertyMappingStrategy() {
        return this.next.getNullValuePropertyMappingStrategy();
    }

    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        return this.next.getNullValueMappingStrategy();
    }

    public BuilderGem getBuilder() {
        return this.next.getBuilder();
    }

    public MappingControl getMappingControl(Elements elements) {
        return this.next.getMappingControl(elements);
    }

    public TypeMirror getUnexpectedValueMappingException() {
        return this.next.getUnexpectedValueMappingException();
    }

    DelegatingOptions next() {
        return this.next;
    }

    protected Set<DeclaredType> toDeclaredTypes(List<TypeMirror> list, Set<DeclaredType> set) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<TypeMirror> it = list.iterator();
        while (it.hasNext()) {
            DeclaredType declaredType = (TypeMirror) it.next();
            if (declaredType == null) {
                throw new TypeHierarchyErroneousException((TypeMirror) declaredType);
            }
            linkedHashSet.add(declaredType);
        }
        linkedHashSet.addAll(set);
        return linkedHashSet;
    }
}
