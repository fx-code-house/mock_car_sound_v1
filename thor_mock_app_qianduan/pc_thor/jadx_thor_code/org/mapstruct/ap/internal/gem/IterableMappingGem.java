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
public class IterableMappingGem implements Gem {
    private final GemValue<String> dateFormat;
    private final GemValue<TypeMirror> elementMappingControl;
    private final GemValue<TypeMirror> elementTargetType;
    private final boolean isValid;
    private final AnnotationMirror mirror;
    private final GemValue<String> nullValueMappingStrategy;
    private final GemValue<String> numberFormat;
    private final GemValue<List<TypeMirror>> qualifiedBy;
    private final GemValue<List<String>> qualifiedByName;

    public interface Builder<T> {
        T build();

        Builder setDateformat(GemValue<String> gemValue);

        Builder setElementmappingcontrol(GemValue<TypeMirror> gemValue);

        Builder setElementtargettype(GemValue<TypeMirror> gemValue);

        Builder setMirror(AnnotationMirror annotationMirror);

        Builder setNullvaluemappingstrategy(GemValue<String> gemValue);

        Builder setNumberformat(GemValue<String> gemValue);

        Builder setQualifiedby(GemValue<List<TypeMirror>> gemValue);

        Builder setQualifiedbyname(GemValue<List<String>> gemValue);
    }

    private IterableMappingGem(BuilderImpl builderImpl) {
        GemValue<String> gemValue = builderImpl.dateFormat;
        this.dateFormat = gemValue;
        GemValue<String> gemValue2 = builderImpl.numberFormat;
        this.numberFormat = gemValue2;
        GemValue<List<TypeMirror>> gemValue3 = builderImpl.qualifiedBy;
        this.qualifiedBy = gemValue3;
        GemValue<List<String>> gemValue4 = builderImpl.qualifiedByName;
        this.qualifiedByName = gemValue4;
        GemValue<TypeMirror> gemValue5 = builderImpl.elementTargetType;
        this.elementTargetType = gemValue5;
        GemValue<String> gemValue6 = builderImpl.nullValueMappingStrategy;
        this.nullValueMappingStrategy = gemValue6;
        GemValue<TypeMirror> gemValue7 = builderImpl.elementMappingControl;
        this.elementMappingControl = gemValue7;
        this.isValid = gemValue != null && gemValue.isValid() && gemValue2 != null && gemValue2.isValid() && gemValue3 != null && gemValue3.isValid() && gemValue4 != null && gemValue4.isValid() && gemValue5 != null && gemValue5.isValid() && gemValue6 != null && gemValue6.isValid() && gemValue7 != null && gemValue7.isValid();
        this.mirror = builderImpl.mirror;
    }

    public GemValue<String> dateFormat() {
        return this.dateFormat;
    }

    public GemValue<String> numberFormat() {
        return this.numberFormat;
    }

    public GemValue<List<TypeMirror>> qualifiedBy() {
        return this.qualifiedBy;
    }

    public GemValue<List<String>> qualifiedByName() {
        return this.qualifiedByName;
    }

    public GemValue<TypeMirror> elementTargetType() {
        return this.elementTargetType;
    }

    public GemValue<String> nullValueMappingStrategy() {
        return this.nullValueMappingStrategy;
    }

    public GemValue<TypeMirror> elementMappingControl() {
        return this.elementMappingControl;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public AnnotationMirror mirror() {
        return this.mirror;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public boolean isValid() {
        return this.isValid;
    }

    public static IterableMappingGem instanceOn(Element element) {
        return (IterableMappingGem) build(element, new BuilderImpl());
    }

    public static IterableMappingGem instanceOn(AnnotationMirror annotationMirror) {
        return (IterableMappingGem) build(annotationMirror, new BuilderImpl());
    }

    public static <T> T build(Element element, Builder<T> builder) {
        return (T) build((AnnotationMirror) element.getAnnotationMirrors().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.gem.IterableMappingGem$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return "org.mapstruct.IterableMapping".contentEquals((CharSequence) ((AnnotationMirror) obj).getAnnotationType().asElement().getQualifiedName());
            }
        }).findAny().orElse(null), builder);
    }

    public static <T> T build(AnnotationMirror annotationMirror, Builder<T> builder) {
        if (annotationMirror == null || builder == null) {
            return null;
        }
        List listMethodsIn = ElementFilter.methodsIn(annotationMirror.getAnnotationType().asElement().getEnclosedElements());
        final HashMap map = new HashMap(listMethodsIn.size());
        listMethodsIn.forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.IterableMappingGem$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                IterableMappingGem.lambda$build$1(map, (ExecutableElement) obj);
            }
        });
        final HashMap map2 = new HashMap(listMethodsIn.size());
        annotationMirror.getElementValues().entrySet().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.IterableMappingGem$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                IterableMappingGem.lambda$build$2(map2, (Map.Entry) obj);
            }
        });
        for (String str : map.keySet()) {
            if ("dateFormat".equals(str)) {
                builder.setDateformat(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("numberFormat".equals(str)) {
                builder.setNumberformat(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("qualifiedBy".equals(str)) {
                builder.setQualifiedby(GemValue.createArray((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("qualifiedByName".equals(str)) {
                builder.setQualifiedbyname(GemValue.createArray((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("elementTargetType".equals(str)) {
                builder.setElementtargettype(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("nullValueMappingStrategy".equals(str)) {
                builder.setNullvaluemappingstrategy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("elementMappingControl".equals(str)) {
                builder.setElementmappingcontrol(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            }
        }
        builder.setMirror(annotationMirror);
        return builder.build();
    }

    static /* synthetic */ void lambda$build$1(Map map, ExecutableElement executableElement) {
    }

    static /* synthetic */ void lambda$build$2(Map map, Map.Entry entry) {
    }

    private static class BuilderImpl implements Builder<IterableMappingGem> {
        private GemValue<String> dateFormat;
        private GemValue<TypeMirror> elementMappingControl;
        private GemValue<TypeMirror> elementTargetType;
        private AnnotationMirror mirror;
        private GemValue<String> nullValueMappingStrategy;
        private GemValue<String> numberFormat;
        private GemValue<List<TypeMirror>> qualifiedBy;
        private GemValue<List<String>> qualifiedByName;

        private BuilderImpl() {
        }

        @Override // org.mapstruct.ap.internal.gem.IterableMappingGem.Builder
        public Builder setDateformat(GemValue<String> gemValue) {
            this.dateFormat = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.IterableMappingGem.Builder
        public Builder setNumberformat(GemValue<String> gemValue) {
            this.numberFormat = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.IterableMappingGem.Builder
        public Builder setQualifiedby(GemValue<List<TypeMirror>> gemValue) {
            this.qualifiedBy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.IterableMappingGem.Builder
        public Builder setQualifiedbyname(GemValue<List<String>> gemValue) {
            this.qualifiedByName = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.IterableMappingGem.Builder
        public Builder setElementtargettype(GemValue<TypeMirror> gemValue) {
            this.elementTargetType = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.IterableMappingGem.Builder
        public Builder setNullvaluemappingstrategy(GemValue<String> gemValue) {
            this.nullValueMappingStrategy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.IterableMappingGem.Builder
        public Builder setElementmappingcontrol(GemValue<TypeMirror> gemValue) {
            this.elementMappingControl = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.IterableMappingGem.Builder
        public Builder setMirror(AnnotationMirror annotationMirror) {
            this.mirror = annotationMirror;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.IterableMappingGem.Builder
        public IterableMappingGem build() {
            return new IterableMappingGem(this);
        }
    }
}
