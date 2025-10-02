package org.mapstruct.ap.internal.model.source;

import java.util.Set;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.gem.InjectionStrategyGem;
import org.mapstruct.ap.internal.gem.MapperConfigGem;
import org.mapstruct.ap.internal.gem.MappingInheritanceStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;

/* loaded from: classes3.dex */
public class MapperConfigOptions extends DelegatingOptions {
    private final MapperConfigGem mapperConfig;

    MapperConfigOptions(MapperConfigGem mapperConfigGem, DelegatingOptions delegatingOptions) {
        super(delegatingOptions);
        this.mapperConfig = mapperConfigGem;
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public String implementationName() {
        return this.mapperConfig.implementationName().hasValue() ? this.mapperConfig.implementationName().get() : next().implementationName();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public String implementationPackage() {
        return this.mapperConfig.implementationPackage().hasValue() ? this.mapperConfig.implementationPackage().get() : next().implementationPackage();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public Set<DeclaredType> uses() {
        return toDeclaredTypes(this.mapperConfig.uses().get(), next().uses());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public Set<DeclaredType> imports() {
        return toDeclaredTypes(this.mapperConfig.imports().get(), next().imports());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public ReportingPolicyGem unmappedTargetPolicy() {
        return this.mapperConfig.unmappedTargetPolicy().hasValue() ? ReportingPolicyGem.valueOf(this.mapperConfig.unmappedTargetPolicy().get()) : next().unmappedTargetPolicy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public ReportingPolicyGem unmappedSourcePolicy() {
        return this.mapperConfig.unmappedSourcePolicy().hasValue() ? ReportingPolicyGem.valueOf(this.mapperConfig.unmappedSourcePolicy().get()) : next().unmappedSourcePolicy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public ReportingPolicyGem typeConversionPolicy() {
        return this.mapperConfig.typeConversionPolicy().hasValue() ? ReportingPolicyGem.valueOf(this.mapperConfig.typeConversionPolicy().get()) : next().typeConversionPolicy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public String componentModel() {
        return this.mapperConfig.componentModel().hasValue() ? this.mapperConfig.componentModel().get() : next().componentModel();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public MappingInheritanceStrategyGem getMappingInheritanceStrategy() {
        if (this.mapperConfig.mappingInheritanceStrategy().hasValue()) {
            return MappingInheritanceStrategyGem.valueOf(this.mapperConfig.mappingInheritanceStrategy().get());
        }
        return next().getMappingInheritanceStrategy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public InjectionStrategyGem getInjectionStrategy() {
        if (this.mapperConfig.injectionStrategy().hasValue()) {
            return InjectionStrategyGem.valueOf(this.mapperConfig.injectionStrategy().get());
        }
        return next().getInjectionStrategy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public Boolean isDisableSubMappingMethodsGeneration() {
        if (this.mapperConfig.disableSubMappingMethodsGeneration().hasValue()) {
            return this.mapperConfig.disableSubMappingMethodsGeneration().get();
        }
        return next().isDisableSubMappingMethodsGeneration();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public CollectionMappingStrategyGem getCollectionMappingStrategy() {
        if (this.mapperConfig.collectionMappingStrategy().hasValue()) {
            return CollectionMappingStrategyGem.valueOf(this.mapperConfig.collectionMappingStrategy().get());
        }
        return next().getCollectionMappingStrategy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValueCheckStrategyGem getNullValueCheckStrategy() {
        if (this.mapperConfig.nullValueCheckStrategy().hasValue()) {
            return NullValueCheckStrategyGem.valueOf(this.mapperConfig.nullValueCheckStrategy().get());
        }
        return next().getNullValueCheckStrategy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValuePropertyMappingStrategyGem getNullValuePropertyMappingStrategy() {
        if (this.mapperConfig.nullValuePropertyMappingStrategy().hasValue()) {
            return NullValuePropertyMappingStrategyGem.valueOf(this.mapperConfig.nullValuePropertyMappingStrategy().get());
        }
        return next().getNullValuePropertyMappingStrategy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        if (this.mapperConfig.nullValueMappingStrategy().hasValue()) {
            return NullValueMappingStrategyGem.valueOf(this.mapperConfig.nullValueMappingStrategy().get());
        }
        return next().getNullValueMappingStrategy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public BuilderGem getBuilder() {
        return this.mapperConfig.builder().hasValue() ? this.mapperConfig.builder().get() : next().getBuilder();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public MappingControl getMappingControl(Elements elements) {
        if (this.mapperConfig.mappingControl().hasValue()) {
            return MappingControl.fromTypeMirror(this.mapperConfig.mappingControl().getValue(), elements);
        }
        return next().getMappingControl(elements);
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public TypeMirror getUnexpectedValueMappingException() {
        if (this.mapperConfig.unexpectedValueMappingException().hasValue()) {
            return this.mapperConfig.unexpectedValueMappingException().get();
        }
        return next().getUnexpectedValueMappingException();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public boolean hasAnnotation() {
        return this.mapperConfig != null;
    }
}
