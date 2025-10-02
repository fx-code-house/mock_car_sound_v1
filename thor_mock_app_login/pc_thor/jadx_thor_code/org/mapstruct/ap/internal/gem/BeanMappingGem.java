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
public class BeanMappingGem implements Gem {
    private final GemValue<BuilderGem> builder;
    private final GemValue<Boolean> ignoreByDefault;
    private final GemValue<List<String>> ignoreUnmappedSourceProperties;
    private final boolean isValid;
    private final GemValue<TypeMirror> mappingControl;
    private final AnnotationMirror mirror;
    private final GemValue<String> nullValueCheckStrategy;
    private final GemValue<String> nullValueMappingStrategy;
    private final GemValue<String> nullValuePropertyMappingStrategy;
    private final GemValue<List<TypeMirror>> qualifiedBy;
    private final GemValue<List<String>> qualifiedByName;
    private final GemValue<TypeMirror> resultType;

    public interface Builder<T> {
        T build();

        Builder setBuilder(GemValue<BuilderGem> gemValue);

        Builder setIgnorebydefault(GemValue<Boolean> gemValue);

        Builder setIgnoreunmappedsourceproperties(GemValue<List<String>> gemValue);

        Builder setMappingcontrol(GemValue<TypeMirror> gemValue);

        Builder setMirror(AnnotationMirror annotationMirror);

        Builder setNullvaluecheckstrategy(GemValue<String> gemValue);

        Builder setNullvaluemappingstrategy(GemValue<String> gemValue);

        Builder setNullvaluepropertymappingstrategy(GemValue<String> gemValue);

        Builder setQualifiedby(GemValue<List<TypeMirror>> gemValue);

        Builder setQualifiedbyname(GemValue<List<String>> gemValue);

        Builder setResulttype(GemValue<TypeMirror> gemValue);
    }

    private BeanMappingGem(BuilderImpl builderImpl) {
        GemValue<TypeMirror> gemValue = builderImpl.resultType;
        this.resultType = gemValue;
        GemValue<List<TypeMirror>> gemValue2 = builderImpl.qualifiedBy;
        this.qualifiedBy = gemValue2;
        GemValue<List<String>> gemValue3 = builderImpl.qualifiedByName;
        this.qualifiedByName = gemValue3;
        GemValue<String> gemValue4 = builderImpl.nullValueMappingStrategy;
        this.nullValueMappingStrategy = gemValue4;
        GemValue<String> gemValue5 = builderImpl.nullValuePropertyMappingStrategy;
        this.nullValuePropertyMappingStrategy = gemValue5;
        GemValue<String> gemValue6 = builderImpl.nullValueCheckStrategy;
        this.nullValueCheckStrategy = gemValue6;
        GemValue<Boolean> gemValue7 = builderImpl.ignoreByDefault;
        this.ignoreByDefault = gemValue7;
        GemValue<List<String>> gemValue8 = builderImpl.ignoreUnmappedSourceProperties;
        this.ignoreUnmappedSourceProperties = gemValue8;
        GemValue<BuilderGem> gemValue9 = builderImpl.builder;
        this.builder = gemValue9;
        GemValue<TypeMirror> gemValue10 = builderImpl.mappingControl;
        this.mappingControl = gemValue10;
        this.isValid = gemValue != null && gemValue.isValid() && gemValue2 != null && gemValue2.isValid() && gemValue3 != null && gemValue3.isValid() && gemValue4 != null && gemValue4.isValid() && gemValue5 != null && gemValue5.isValid() && gemValue6 != null && gemValue6.isValid() && gemValue7 != null && gemValue7.isValid() && gemValue8 != null && gemValue8.isValid() && gemValue9 != null && gemValue9.isValid() && gemValue10 != null && gemValue10.isValid();
        this.mirror = builderImpl.mirror;
    }

    public GemValue<TypeMirror> resultType() {
        return this.resultType;
    }

    public GemValue<List<TypeMirror>> qualifiedBy() {
        return this.qualifiedBy;
    }

    public GemValue<List<String>> qualifiedByName() {
        return this.qualifiedByName;
    }

    public GemValue<String> nullValueMappingStrategy() {
        return this.nullValueMappingStrategy;
    }

    public GemValue<String> nullValuePropertyMappingStrategy() {
        return this.nullValuePropertyMappingStrategy;
    }

    public GemValue<String> nullValueCheckStrategy() {
        return this.nullValueCheckStrategy;
    }

    public GemValue<Boolean> ignoreByDefault() {
        return this.ignoreByDefault;
    }

    public GemValue<List<String>> ignoreUnmappedSourceProperties() {
        return this.ignoreUnmappedSourceProperties;
    }

    public GemValue<BuilderGem> builder() {
        return this.builder;
    }

    public GemValue<TypeMirror> mappingControl() {
        return this.mappingControl;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public AnnotationMirror mirror() {
        return this.mirror;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public boolean isValid() {
        return this.isValid;
    }

    public static BeanMappingGem instanceOn(Element element) {
        return (BeanMappingGem) build(element, new BuilderImpl());
    }

    public static BeanMappingGem instanceOn(AnnotationMirror annotationMirror) {
        return (BeanMappingGem) build(annotationMirror, new BuilderImpl());
    }

    public static <T> T build(Element element, Builder<T> builder) {
        return (T) build((AnnotationMirror) element.getAnnotationMirrors().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.gem.BeanMappingGem$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return "org.mapstruct.BeanMapping".contentEquals((CharSequence) ((AnnotationMirror) obj).getAnnotationType().asElement().getQualifiedName());
            }
        }).findAny().orElse(null), builder);
    }

    public static <T> T build(AnnotationMirror annotationMirror, Builder<T> builder) {
        if (annotationMirror == null || builder == null) {
            return null;
        }
        List listMethodsIn = ElementFilter.methodsIn(annotationMirror.getAnnotationType().asElement().getEnclosedElements());
        final HashMap map = new HashMap(listMethodsIn.size());
        listMethodsIn.forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.BeanMappingGem$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BeanMappingGem.lambda$build$1(map, (ExecutableElement) obj);
            }
        });
        final HashMap map2 = new HashMap(listMethodsIn.size());
        annotationMirror.getElementValues().entrySet().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.BeanMappingGem$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BeanMappingGem.lambda$build$2(map2, (Map.Entry) obj);
            }
        });
        for (String str : map.keySet()) {
            if ("resultType".equals(str)) {
                builder.setResulttype(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("qualifiedBy".equals(str)) {
                builder.setQualifiedby(GemValue.createArray((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("qualifiedByName".equals(str)) {
                builder.setQualifiedbyname(GemValue.createArray((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("nullValueMappingStrategy".equals(str)) {
                builder.setNullvaluemappingstrategy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("nullValuePropertyMappingStrategy".equals(str)) {
                builder.setNullvaluepropertymappingstrategy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("nullValueCheckStrategy".equals(str)) {
                builder.setNullvaluecheckstrategy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("ignoreByDefault".equals(str)) {
                builder.setIgnorebydefault(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), Boolean.class));
            } else if ("ignoreUnmappedSourceProperties".equals(str)) {
                builder.setIgnoreunmappedsourceproperties(GemValue.createArray((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("builder".equals(str)) {
                builder.setBuilder(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), new BeanMappingGem$$ExternalSyntheticLambda2()));
            } else if ("mappingControl".equals(str)) {
                builder.setMappingcontrol(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            }
        }
        builder.setMirror(annotationMirror);
        return builder.build();
    }

    static /* synthetic */ void lambda$build$1(Map map, ExecutableElement executableElement) {
    }

    static /* synthetic */ void lambda$build$2(Map map, Map.Entry entry) {
    }

    private static class BuilderImpl implements Builder<BeanMappingGem> {
        private GemValue<BuilderGem> builder;
        private GemValue<Boolean> ignoreByDefault;
        private GemValue<List<String>> ignoreUnmappedSourceProperties;
        private GemValue<TypeMirror> mappingControl;
        private AnnotationMirror mirror;
        private GemValue<String> nullValueCheckStrategy;
        private GemValue<String> nullValueMappingStrategy;
        private GemValue<String> nullValuePropertyMappingStrategy;
        private GemValue<List<TypeMirror>> qualifiedBy;
        private GemValue<List<String>> qualifiedByName;
        private GemValue<TypeMirror> resultType;

        private BuilderImpl() {
        }

        @Override // org.mapstruct.ap.internal.gem.BeanMappingGem.Builder
        public Builder setResulttype(GemValue<TypeMirror> gemValue) {
            this.resultType = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.BeanMappingGem.Builder
        public Builder setQualifiedby(GemValue<List<TypeMirror>> gemValue) {
            this.qualifiedBy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.BeanMappingGem.Builder
        public Builder setQualifiedbyname(GemValue<List<String>> gemValue) {
            this.qualifiedByName = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.BeanMappingGem.Builder
        public Builder setNullvaluemappingstrategy(GemValue<String> gemValue) {
            this.nullValueMappingStrategy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.BeanMappingGem.Builder
        public Builder setNullvaluepropertymappingstrategy(GemValue<String> gemValue) {
            this.nullValuePropertyMappingStrategy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.BeanMappingGem.Builder
        public Builder setNullvaluecheckstrategy(GemValue<String> gemValue) {
            this.nullValueCheckStrategy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.BeanMappingGem.Builder
        public Builder setIgnorebydefault(GemValue<Boolean> gemValue) {
            this.ignoreByDefault = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.BeanMappingGem.Builder
        public Builder setIgnoreunmappedsourceproperties(GemValue<List<String>> gemValue) {
            this.ignoreUnmappedSourceProperties = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.BeanMappingGem.Builder
        public Builder setBuilder(GemValue<BuilderGem> gemValue) {
            this.builder = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.BeanMappingGem.Builder
        public Builder setMappingcontrol(GemValue<TypeMirror> gemValue) {
            this.mappingControl = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.BeanMappingGem.Builder
        public Builder setMirror(AnnotationMirror annotationMirror) {
            this.mirror = annotationMirror;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.BeanMappingGem.Builder
        public BeanMappingGem build() {
            return new BeanMappingGem(this);
        }
    }
}
