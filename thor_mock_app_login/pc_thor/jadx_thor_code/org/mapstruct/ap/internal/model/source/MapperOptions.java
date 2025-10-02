package org.mapstruct.ap.internal.model.source;

import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.gem.InjectionStrategyGem;
import org.mapstruct.ap.internal.gem.MapperConfigGem;
import org.mapstruct.ap.internal.gem.MapperGem;
import org.mapstruct.ap.internal.gem.MappingInheritanceStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;
import org.mapstruct.ap.internal.option.Options;

/* loaded from: classes3.dex */
public class MapperOptions extends DelegatingOptions {
    private final MapperGem mapper;
    private final DeclaredType mapperConfigType;

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public boolean hasAnnotation() {
        return true;
    }

    public static MapperOptions getInstanceOn(TypeElement typeElement, Options options) {
        MapperGem mapperGemInstanceOn = MapperGem.instanceOn((Element) typeElement);
        DefaultOptions defaultOptions = new DefaultOptions(mapperGemInstanceOn, options);
        DeclaredType declaredType = (mapperGemInstanceOn.config().hasValue() && mapperGemInstanceOn.config().getValue().getKind() == TypeKind.DECLARED) ? (DeclaredType) mapperGemInstanceOn.config().get() : null;
        if (declaredType != null) {
            return new MapperOptions(mapperGemInstanceOn, declaredType, new MapperConfigOptions(MapperConfigGem.instanceOn(declaredType.asElement()), defaultOptions));
        }
        return new MapperOptions(mapperGemInstanceOn, null, defaultOptions);
    }

    private MapperOptions(MapperGem mapperGem, DeclaredType declaredType, DelegatingOptions delegatingOptions) {
        super(delegatingOptions);
        this.mapper = mapperGem;
        this.mapperConfigType = declaredType;
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public String implementationName() {
        return this.mapper.implementationName().hasValue() ? this.mapper.implementationName().get() : next().implementationName();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public String implementationPackage() {
        return this.mapper.implementationPackage().hasValue() ? this.mapper.implementationPackage().get() : next().implementationPackage();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public Set<DeclaredType> uses() {
        return toDeclaredTypes(this.mapper.uses().get(), next().uses());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public Set<DeclaredType> imports() {
        return toDeclaredTypes(this.mapper.imports().get(), next().imports());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public ReportingPolicyGem unmappedTargetPolicy() {
        return this.mapper.unmappedTargetPolicy().hasValue() ? ReportingPolicyGem.valueOf(this.mapper.unmappedTargetPolicy().get()) : next().unmappedTargetPolicy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public ReportingPolicyGem unmappedSourcePolicy() {
        return this.mapper.unmappedSourcePolicy().hasValue() ? ReportingPolicyGem.valueOf(this.mapper.unmappedSourcePolicy().get()) : next().unmappedSourcePolicy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public ReportingPolicyGem typeConversionPolicy() {
        return this.mapper.typeConversionPolicy().hasValue() ? ReportingPolicyGem.valueOf(this.mapper.typeConversionPolicy().get()) : next().typeConversionPolicy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public String componentModel() {
        return this.mapper.componentModel().hasValue() ? this.mapper.componentModel().get() : next().componentModel();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public MappingInheritanceStrategyGem getMappingInheritanceStrategy() {
        if (this.mapper.mappingInheritanceStrategy().hasValue()) {
            return MappingInheritanceStrategyGem.valueOf(this.mapper.mappingInheritanceStrategy().get());
        }
        return next().getMappingInheritanceStrategy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public InjectionStrategyGem getInjectionStrategy() {
        if (this.mapper.injectionStrategy().hasValue()) {
            return InjectionStrategyGem.valueOf(this.mapper.injectionStrategy().get());
        }
        return next().getInjectionStrategy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public Boolean isDisableSubMappingMethodsGeneration() {
        if (this.mapper.disableSubMappingMethodsGeneration().hasValue()) {
            return this.mapper.disableSubMappingMethodsGeneration().get();
        }
        return next().isDisableSubMappingMethodsGeneration();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public CollectionMappingStrategyGem getCollectionMappingStrategy() {
        if (this.mapper.collectionMappingStrategy().hasValue()) {
            return CollectionMappingStrategyGem.valueOf(this.mapper.collectionMappingStrategy().get());
        }
        return next().getCollectionMappingStrategy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValueCheckStrategyGem getNullValueCheckStrategy() {
        if (this.mapper.nullValueCheckStrategy().hasValue()) {
            return NullValueCheckStrategyGem.valueOf(this.mapper.nullValueCheckStrategy().get());
        }
        return next().getNullValueCheckStrategy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValuePropertyMappingStrategyGem getNullValuePropertyMappingStrategy() {
        if (this.mapper.nullValuePropertyMappingStrategy().hasValue()) {
            return NullValuePropertyMappingStrategyGem.valueOf(this.mapper.nullValuePropertyMappingStrategy().get());
        }
        return next().getNullValuePropertyMappingStrategy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        if (this.mapper.nullValueMappingStrategy().hasValue()) {
            return NullValueMappingStrategyGem.valueOf(this.mapper.nullValueMappingStrategy().get());
        }
        return next().getNullValueMappingStrategy();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public BuilderGem getBuilder() {
        return this.mapper.builder().hasValue() ? this.mapper.builder().get() : next().getBuilder();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public MappingControl getMappingControl(Elements elements) {
        if (this.mapper.mappingControl().hasValue()) {
            return MappingControl.fromTypeMirror(this.mapper.mappingControl().getValue(), elements);
        }
        return next().getMappingControl(elements);
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public TypeMirror getUnexpectedValueMappingException() {
        if (this.mapper.unexpectedValueMappingException().hasValue()) {
            return this.mapper.unexpectedValueMappingException().get();
        }
        return next().getUnexpectedValueMappingException();
    }

    public DeclaredType mapperConfigType() {
        return this.mapperConfigType;
    }

    public boolean hasMapperConfig() {
        return this.mapperConfigType != null;
    }

    public boolean isValid() {
        return this.mapper.isValid();
    }

    public AnnotationMirror getAnnotationMirror() {
        return this.mapper.mirror();
    }
}
