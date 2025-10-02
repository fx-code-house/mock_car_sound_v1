package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.Set;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.gem.InjectionStrategyGem;
import org.mapstruct.ap.internal.gem.MapperGem;
import org.mapstruct.ap.internal.gem.MappingInheritanceStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;
import org.mapstruct.ap.internal.option.Options;

/* loaded from: classes3.dex */
public class DefaultOptions extends DelegatingOptions {
    private final MapperGem mapper;
    private final Options options;

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public BuilderGem getBuilder() {
        return null;
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public TypeMirror getUnexpectedValueMappingException() {
        return null;
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public boolean hasAnnotation() {
        return false;
    }

    DefaultOptions(MapperGem mapperGem, Options options) {
        super(null);
        this.mapper = mapperGem;
        this.options = options;
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public String implementationName() {
        return this.mapper.implementationName().getDefaultValue();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public String implementationPackage() {
        return this.mapper.implementationPackage().getDefaultValue();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public Set<DeclaredType> uses() {
        return Collections.emptySet();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public Set<DeclaredType> imports() {
        return Collections.emptySet();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public ReportingPolicyGem unmappedTargetPolicy() {
        if (this.options.getUnmappedTargetPolicy() != null) {
            return this.options.getUnmappedTargetPolicy();
        }
        return ReportingPolicyGem.valueOf(this.mapper.unmappedTargetPolicy().getDefaultValue());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public ReportingPolicyGem unmappedSourcePolicy() {
        return ReportingPolicyGem.valueOf(this.mapper.unmappedSourcePolicy().getDefaultValue());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public ReportingPolicyGem typeConversionPolicy() {
        return ReportingPolicyGem.valueOf(this.mapper.typeConversionPolicy().getDefaultValue());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public String componentModel() {
        if (this.options.getDefaultComponentModel() != null) {
            return this.options.getDefaultComponentModel();
        }
        return this.mapper.componentModel().getDefaultValue();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public MappingInheritanceStrategyGem getMappingInheritanceStrategy() {
        return MappingInheritanceStrategyGem.valueOf(this.mapper.mappingInheritanceStrategy().getDefaultValue());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public InjectionStrategyGem getInjectionStrategy() {
        if (this.options.getDefaultInjectionStrategy() != null) {
            return InjectionStrategyGem.valueOf(this.options.getDefaultInjectionStrategy().toUpperCase());
        }
        return InjectionStrategyGem.valueOf(this.mapper.injectionStrategy().getDefaultValue());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public Boolean isDisableSubMappingMethodsGeneration() {
        return this.mapper.disableSubMappingMethodsGeneration().getDefaultValue();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public CollectionMappingStrategyGem getCollectionMappingStrategy() {
        return CollectionMappingStrategyGem.valueOf(this.mapper.collectionMappingStrategy().getDefaultValue());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValueCheckStrategyGem getNullValueCheckStrategy() {
        return NullValueCheckStrategyGem.valueOf(this.mapper.nullValueCheckStrategy().getDefaultValue());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValuePropertyMappingStrategyGem getNullValuePropertyMappingStrategy() {
        return NullValuePropertyMappingStrategyGem.valueOf(this.mapper.nullValuePropertyMappingStrategy().getDefaultValue());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        return NullValueMappingStrategyGem.valueOf(this.mapper.nullValueMappingStrategy().getDefaultValue());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public MappingControl getMappingControl(Elements elements) {
        return MappingControl.fromTypeMirror(this.mapper.mappingControl().getDefaultValue(), elements);
    }
}
