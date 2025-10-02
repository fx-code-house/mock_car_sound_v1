package org.mapstruct.ap.internal.gem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem;
import org.mapstruct.ap.shaded.org.mapstruct.tools.gem.GemValue;

/* loaded from: classes3.dex */
public class MapperConfigGem implements Gem {
    private final GemValue<BuilderGem> builder;
    private final GemValue<String> collectionMappingStrategy;
    private final GemValue<String> componentModel;
    private final GemValue<Boolean> disableSubMappingMethodsGeneration;
    private final GemValue<String> implementationName;
    private final GemValue<String> implementationPackage;
    private final GemValue<List<TypeMirror>> imports;
    private final GemValue<String> injectionStrategy;
    private final boolean isValid;
    private final GemValue<TypeMirror> mappingControl;
    private final GemValue<String> mappingInheritanceStrategy;
    private final AnnotationMirror mirror;
    private final GemValue<String> nullValueCheckStrategy;
    private final GemValue<String> nullValueMappingStrategy;
    private final GemValue<String> nullValuePropertyMappingStrategy;
    private final GemValue<String> typeConversionPolicy;
    private final GemValue<TypeMirror> unexpectedValueMappingException;
    private final GemValue<String> unmappedSourcePolicy;
    private final GemValue<String> unmappedTargetPolicy;
    private final GemValue<List<TypeMirror>> uses;

    public interface Builder<T> {
        T build();

        Builder setBuilder(GemValue<BuilderGem> gemValue);

        Builder setCollectionmappingstrategy(GemValue<String> gemValue);

        Builder setComponentmodel(GemValue<String> gemValue);

        Builder setDisablesubmappingmethodsgeneration(GemValue<Boolean> gemValue);

        Builder setImplementationname(GemValue<String> gemValue);

        Builder setImplementationpackage(GemValue<String> gemValue);

        Builder setImports(GemValue<List<TypeMirror>> gemValue);

        Builder setInjectionstrategy(GemValue<String> gemValue);

        Builder setMappingcontrol(GemValue<TypeMirror> gemValue);

        Builder setMappinginheritancestrategy(GemValue<String> gemValue);

        Builder setMirror(AnnotationMirror annotationMirror);

        Builder setNullvaluecheckstrategy(GemValue<String> gemValue);

        Builder setNullvaluemappingstrategy(GemValue<String> gemValue);

        Builder setNullvaluepropertymappingstrategy(GemValue<String> gemValue);

        Builder setTypeconversionpolicy(GemValue<String> gemValue);

        Builder setUnexpectedvaluemappingexception(GemValue<TypeMirror> gemValue);

        Builder setUnmappedsourcepolicy(GemValue<String> gemValue);

        Builder setUnmappedtargetpolicy(GemValue<String> gemValue);

        Builder setUses(GemValue<List<TypeMirror>> gemValue);
    }

    private MapperConfigGem(BuilderImpl builderImpl) {
        GemValue<List<TypeMirror>> gemValue = builderImpl.uses;
        this.uses = gemValue;
        GemValue<List<TypeMirror>> gemValue2 = builderImpl.imports;
        this.imports = gemValue2;
        GemValue<String> gemValue3 = builderImpl.unmappedSourcePolicy;
        this.unmappedSourcePolicy = gemValue3;
        GemValue<String> gemValue4 = builderImpl.unmappedTargetPolicy;
        this.unmappedTargetPolicy = gemValue4;
        GemValue<String> gemValue5 = builderImpl.typeConversionPolicy;
        this.typeConversionPolicy = gemValue5;
        GemValue<String> gemValue6 = builderImpl.componentModel;
        this.componentModel = gemValue6;
        GemValue<String> gemValue7 = builderImpl.implementationName;
        this.implementationName = gemValue7;
        GemValue<String> gemValue8 = builderImpl.implementationPackage;
        this.implementationPackage = gemValue8;
        GemValue<String> gemValue9 = builderImpl.collectionMappingStrategy;
        this.collectionMappingStrategy = gemValue9;
        GemValue<String> gemValue10 = builderImpl.nullValueMappingStrategy;
        this.nullValueMappingStrategy = gemValue10;
        GemValue<String> gemValue11 = builderImpl.nullValuePropertyMappingStrategy;
        this.nullValuePropertyMappingStrategy = gemValue11;
        GemValue<String> gemValue12 = builderImpl.mappingInheritanceStrategy;
        this.mappingInheritanceStrategy = gemValue12;
        GemValue<String> gemValue13 = builderImpl.nullValueCheckStrategy;
        this.nullValueCheckStrategy = gemValue13;
        GemValue<String> gemValue14 = builderImpl.injectionStrategy;
        this.injectionStrategy = gemValue14;
        GemValue<Boolean> gemValue15 = builderImpl.disableSubMappingMethodsGeneration;
        this.disableSubMappingMethodsGeneration = gemValue15;
        GemValue<BuilderGem> gemValue16 = builderImpl.builder;
        this.builder = gemValue16;
        GemValue<TypeMirror> gemValue17 = builderImpl.mappingControl;
        this.mappingControl = gemValue17;
        GemValue<TypeMirror> gemValue18 = builderImpl.unexpectedValueMappingException;
        this.unexpectedValueMappingException = gemValue18;
        this.isValid = gemValue != null && gemValue.isValid() && gemValue2 != null && gemValue2.isValid() && gemValue3 != null && gemValue3.isValid() && gemValue4 != null && gemValue4.isValid() && gemValue5 != null && gemValue5.isValid() && gemValue6 != null && gemValue6.isValid() && gemValue7 != null && gemValue7.isValid() && gemValue8 != null && gemValue8.isValid() && gemValue9 != null && gemValue9.isValid() && gemValue10 != null && gemValue10.isValid() && gemValue11 != null && gemValue11.isValid() && gemValue12 != null && gemValue12.isValid() && gemValue13 != null && gemValue13.isValid() && gemValue14 != null && gemValue14.isValid() && gemValue15 != null && gemValue15.isValid() && gemValue16 != null && gemValue16.isValid() && gemValue17 != null && gemValue17.isValid() && gemValue18 != null && gemValue18.isValid();
        this.mirror = builderImpl.mirror;
    }

    public GemValue<List<TypeMirror>> uses() {
        return this.uses;
    }

    public GemValue<List<TypeMirror>> imports() {
        return this.imports;
    }

    public GemValue<String> unmappedSourcePolicy() {
        return this.unmappedSourcePolicy;
    }

    public GemValue<String> unmappedTargetPolicy() {
        return this.unmappedTargetPolicy;
    }

    public GemValue<String> typeConversionPolicy() {
        return this.typeConversionPolicy;
    }

    public GemValue<String> componentModel() {
        return this.componentModel;
    }

    public GemValue<String> implementationName() {
        return this.implementationName;
    }

    public GemValue<String> implementationPackage() {
        return this.implementationPackage;
    }

    public GemValue<String> collectionMappingStrategy() {
        return this.collectionMappingStrategy;
    }

    public GemValue<String> nullValueMappingStrategy() {
        return this.nullValueMappingStrategy;
    }

    public GemValue<String> nullValuePropertyMappingStrategy() {
        return this.nullValuePropertyMappingStrategy;
    }

    public GemValue<String> mappingInheritanceStrategy() {
        return this.mappingInheritanceStrategy;
    }

    public GemValue<String> nullValueCheckStrategy() {
        return this.nullValueCheckStrategy;
    }

    public GemValue<String> injectionStrategy() {
        return this.injectionStrategy;
    }

    public GemValue<Boolean> disableSubMappingMethodsGeneration() {
        return this.disableSubMappingMethodsGeneration;
    }

    public GemValue<BuilderGem> builder() {
        return this.builder;
    }

    public GemValue<TypeMirror> mappingControl() {
        return this.mappingControl;
    }

    public GemValue<TypeMirror> unexpectedValueMappingException() {
        return this.unexpectedValueMappingException;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public AnnotationMirror mirror() {
        return this.mirror;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public boolean isValid() {
        return this.isValid;
    }

    public static MapperConfigGem instanceOn(Element element) {
        return (MapperConfigGem) build(element, new BuilderImpl());
    }

    public static MapperConfigGem instanceOn(AnnotationMirror annotationMirror) {
        return (MapperConfigGem) build(annotationMirror, new BuilderImpl());
    }

    public static <T> T build(Element element, Builder<T> builder) {
        return (T) build((AnnotationMirror) element.getAnnotationMirrors().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.gem.MapperConfigGem$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return "org.mapstruct.MapperConfig".contentEquals((CharSequence) ((AnnotationMirror) obj).getAnnotationType().asElement().getQualifiedName());
            }
        }).findAny().orElse(null), builder);
    }

    public static <T> T build(AnnotationMirror annotationMirror, Builder<T> builder) {
        if (annotationMirror == null || builder == null) {
            return null;
        }
        List listMethodsIn = ElementFilter.methodsIn(annotationMirror.getAnnotationType().asElement().getEnclosedElements());
        final HashMap map = new HashMap(listMethodsIn.size());
        listMethodsIn.forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.MapperConfigGem$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MapperConfigGem.lambda$build$1(map, (ExecutableElement) obj);
            }
        });
        final HashMap map2 = new HashMap(listMethodsIn.size());
        annotationMirror.getElementValues().entrySet().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.MapperConfigGem$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MapperConfigGem.lambda$build$2(map2, (Map.Entry) obj);
            }
        });
        for (String str : map.keySet()) {
            if ("uses".equals(str)) {
                builder.setUses(GemValue.createArray((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("imports".equals(str)) {
                builder.setImports(GemValue.createArray((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("unmappedSourcePolicy".equals(str)) {
                builder.setUnmappedsourcepolicy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("unmappedTargetPolicy".equals(str)) {
                builder.setUnmappedtargetpolicy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("typeConversionPolicy".equals(str)) {
                builder.setTypeconversionpolicy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("componentModel".equals(str)) {
                builder.setComponentmodel(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("implementationName".equals(str)) {
                builder.setImplementationname(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("implementationPackage".equals(str)) {
                builder.setImplementationpackage(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("collectionMappingStrategy".equals(str)) {
                builder.setCollectionmappingstrategy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("nullValueMappingStrategy".equals(str)) {
                builder.setNullvaluemappingstrategy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("nullValuePropertyMappingStrategy".equals(str)) {
                builder.setNullvaluepropertymappingstrategy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("mappingInheritanceStrategy".equals(str)) {
                builder.setMappinginheritancestrategy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("nullValueCheckStrategy".equals(str)) {
                builder.setNullvaluecheckstrategy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("injectionStrategy".equals(str)) {
                builder.setInjectionstrategy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("disableSubMappingMethodsGeneration".equals(str)) {
                builder.setDisablesubmappingmethodsgeneration(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), Boolean.class));
            } else if ("builder".equals(str)) {
                builder.setBuilder(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), new BeanMappingGem$$ExternalSyntheticLambda2()));
            } else if ("mappingControl".equals(str)) {
                builder.setMappingcontrol(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("unexpectedValueMappingException".equals(str)) {
                builder.setUnexpectedvaluemappingexception(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            }
        }
        builder.setMirror(annotationMirror);
        return builder.build();
    }

    static /* synthetic */ void lambda$build$1(Map map, ExecutableElement executableElement) {
    }

    static /* synthetic */ void lambda$build$2(Map map, Map.Entry entry) {
    }

    private static class BuilderImpl implements Builder<MapperConfigGem> {
        private GemValue<BuilderGem> builder;
        private GemValue<String> collectionMappingStrategy;
        private GemValue<String> componentModel;
        private GemValue<Boolean> disableSubMappingMethodsGeneration;
        private GemValue<String> implementationName;
        private GemValue<String> implementationPackage;
        private GemValue<List<TypeMirror>> imports;
        private GemValue<String> injectionStrategy;
        private GemValue<TypeMirror> mappingControl;
        private GemValue<String> mappingInheritanceStrategy;
        private AnnotationMirror mirror;
        private GemValue<String> nullValueCheckStrategy;
        private GemValue<String> nullValueMappingStrategy;
        private GemValue<String> nullValuePropertyMappingStrategy;
        private GemValue<String> typeConversionPolicy;
        private GemValue<TypeMirror> unexpectedValueMappingException;
        private GemValue<String> unmappedSourcePolicy;
        private GemValue<String> unmappedTargetPolicy;
        private GemValue<List<TypeMirror>> uses;

        private BuilderImpl() {
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setUses(GemValue<List<TypeMirror>> gemValue) {
            this.uses = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setImports(GemValue<List<TypeMirror>> gemValue) {
            this.imports = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setUnmappedsourcepolicy(GemValue<String> gemValue) {
            this.unmappedSourcePolicy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setUnmappedtargetpolicy(GemValue<String> gemValue) {
            this.unmappedTargetPolicy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setTypeconversionpolicy(GemValue<String> gemValue) {
            this.typeConversionPolicy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setComponentmodel(GemValue<String> gemValue) {
            this.componentModel = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setImplementationname(GemValue<String> gemValue) {
            this.implementationName = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setImplementationpackage(GemValue<String> gemValue) {
            this.implementationPackage = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setCollectionmappingstrategy(GemValue<String> gemValue) {
            this.collectionMappingStrategy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setNullvaluemappingstrategy(GemValue<String> gemValue) {
            this.nullValueMappingStrategy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setNullvaluepropertymappingstrategy(GemValue<String> gemValue) {
            this.nullValuePropertyMappingStrategy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setMappinginheritancestrategy(GemValue<String> gemValue) {
            this.mappingInheritanceStrategy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setNullvaluecheckstrategy(GemValue<String> gemValue) {
            this.nullValueCheckStrategy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setInjectionstrategy(GemValue<String> gemValue) {
            this.injectionStrategy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setDisablesubmappingmethodsgeneration(GemValue<Boolean> gemValue) {
            this.disableSubMappingMethodsGeneration = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setBuilder(GemValue<BuilderGem> gemValue) {
            this.builder = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setMappingcontrol(GemValue<TypeMirror> gemValue) {
            this.mappingControl = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setUnexpectedvaluemappingexception(GemValue<TypeMirror> gemValue) {
            this.unexpectedValueMappingException = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public Builder setMirror(AnnotationMirror annotationMirror) {
            this.mirror = annotationMirror;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapperConfigGem.Builder
        public MapperConfigGem build() {
            return new MapperConfigGem(this);
        }
    }
}
