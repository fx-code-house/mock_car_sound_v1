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
public class MapMappingGem implements Gem {
    private final boolean isValid;
    private final GemValue<String> keyDateFormat;
    private final GemValue<TypeMirror> keyMappingControl;
    private final GemValue<String> keyNumberFormat;
    private final GemValue<List<TypeMirror>> keyQualifiedBy;
    private final GemValue<List<String>> keyQualifiedByName;
    private final GemValue<TypeMirror> keyTargetType;
    private final AnnotationMirror mirror;
    private final GemValue<String> nullValueMappingStrategy;
    private final GemValue<String> valueDateFormat;
    private final GemValue<TypeMirror> valueMappingControl;
    private final GemValue<String> valueNumberFormat;
    private final GemValue<List<TypeMirror>> valueQualifiedBy;
    private final GemValue<List<String>> valueQualifiedByName;
    private final GemValue<TypeMirror> valueTargetType;

    public interface Builder<T> {
        T build();

        Builder setKeydateformat(GemValue<String> gemValue);

        Builder setKeymappingcontrol(GemValue<TypeMirror> gemValue);

        Builder setKeynumberformat(GemValue<String> gemValue);

        Builder setKeyqualifiedby(GemValue<List<TypeMirror>> gemValue);

        Builder setKeyqualifiedbyname(GemValue<List<String>> gemValue);

        Builder setKeytargettype(GemValue<TypeMirror> gemValue);

        Builder setMirror(AnnotationMirror annotationMirror);

        Builder setNullvaluemappingstrategy(GemValue<String> gemValue);

        Builder setValuedateformat(GemValue<String> gemValue);

        Builder setValuemappingcontrol(GemValue<TypeMirror> gemValue);

        Builder setValuenumberformat(GemValue<String> gemValue);

        Builder setValuequalifiedby(GemValue<List<TypeMirror>> gemValue);

        Builder setValuequalifiedbyname(GemValue<List<String>> gemValue);

        Builder setValuetargettype(GemValue<TypeMirror> gemValue);
    }

    private MapMappingGem(BuilderImpl builderImpl) {
        GemValue<String> gemValue = builderImpl.keyDateFormat;
        this.keyDateFormat = gemValue;
        GemValue<String> gemValue2 = builderImpl.valueDateFormat;
        this.valueDateFormat = gemValue2;
        GemValue<String> gemValue3 = builderImpl.keyNumberFormat;
        this.keyNumberFormat = gemValue3;
        GemValue<String> gemValue4 = builderImpl.valueNumberFormat;
        this.valueNumberFormat = gemValue4;
        GemValue<List<TypeMirror>> gemValue5 = builderImpl.keyQualifiedBy;
        this.keyQualifiedBy = gemValue5;
        GemValue<List<String>> gemValue6 = builderImpl.keyQualifiedByName;
        this.keyQualifiedByName = gemValue6;
        GemValue<List<TypeMirror>> gemValue7 = builderImpl.valueQualifiedBy;
        this.valueQualifiedBy = gemValue7;
        GemValue<List<String>> gemValue8 = builderImpl.valueQualifiedByName;
        this.valueQualifiedByName = gemValue8;
        GemValue<TypeMirror> gemValue9 = builderImpl.keyTargetType;
        this.keyTargetType = gemValue9;
        GemValue<TypeMirror> gemValue10 = builderImpl.valueTargetType;
        this.valueTargetType = gemValue10;
        GemValue<String> gemValue11 = builderImpl.nullValueMappingStrategy;
        this.nullValueMappingStrategy = gemValue11;
        GemValue<TypeMirror> gemValue12 = builderImpl.keyMappingControl;
        this.keyMappingControl = gemValue12;
        GemValue<TypeMirror> gemValue13 = builderImpl.valueMappingControl;
        this.valueMappingControl = gemValue13;
        this.isValid = gemValue != null && gemValue.isValid() && gemValue2 != null && gemValue2.isValid() && gemValue3 != null && gemValue3.isValid() && gemValue4 != null && gemValue4.isValid() && gemValue5 != null && gemValue5.isValid() && gemValue6 != null && gemValue6.isValid() && gemValue7 != null && gemValue7.isValid() && gemValue8 != null && gemValue8.isValid() && gemValue9 != null && gemValue9.isValid() && gemValue10 != null && gemValue10.isValid() && gemValue11 != null && gemValue11.isValid() && gemValue12 != null && gemValue12.isValid() && gemValue13 != null && gemValue13.isValid();
        this.mirror = builderImpl.mirror;
    }

    public GemValue<String> keyDateFormat() {
        return this.keyDateFormat;
    }

    public GemValue<String> valueDateFormat() {
        return this.valueDateFormat;
    }

    public GemValue<String> keyNumberFormat() {
        return this.keyNumberFormat;
    }

    public GemValue<String> valueNumberFormat() {
        return this.valueNumberFormat;
    }

    public GemValue<List<TypeMirror>> keyQualifiedBy() {
        return this.keyQualifiedBy;
    }

    public GemValue<List<String>> keyQualifiedByName() {
        return this.keyQualifiedByName;
    }

    public GemValue<List<TypeMirror>> valueQualifiedBy() {
        return this.valueQualifiedBy;
    }

    public GemValue<List<String>> valueQualifiedByName() {
        return this.valueQualifiedByName;
    }

    public GemValue<TypeMirror> keyTargetType() {
        return this.keyTargetType;
    }

    public GemValue<TypeMirror> valueTargetType() {
        return this.valueTargetType;
    }

    public GemValue<String> nullValueMappingStrategy() {
        return this.nullValueMappingStrategy;
    }

    public GemValue<TypeMirror> keyMappingControl() {
        return this.keyMappingControl;
    }

    public GemValue<TypeMirror> valueMappingControl() {
        return this.valueMappingControl;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public AnnotationMirror mirror() {
        return this.mirror;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public boolean isValid() {
        return this.isValid;
    }

    public static MapMappingGem instanceOn(Element element) {
        return (MapMappingGem) build(element, new BuilderImpl());
    }

    public static MapMappingGem instanceOn(AnnotationMirror annotationMirror) {
        return (MapMappingGem) build(annotationMirror, new BuilderImpl());
    }

    public static <T> T build(Element element, Builder<T> builder) {
        return (T) build((AnnotationMirror) element.getAnnotationMirrors().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.gem.MapMappingGem$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return "org.mapstruct.MapMapping".contentEquals((CharSequence) ((AnnotationMirror) obj).getAnnotationType().asElement().getQualifiedName());
            }
        }).findAny().orElse(null), builder);
    }

    public static <T> T build(AnnotationMirror annotationMirror, Builder<T> builder) {
        if (annotationMirror == null || builder == null) {
            return null;
        }
        List listMethodsIn = ElementFilter.methodsIn(annotationMirror.getAnnotationType().asElement().getEnclosedElements());
        final HashMap map = new HashMap(listMethodsIn.size());
        listMethodsIn.forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.MapMappingGem$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MapMappingGem.lambda$build$1(map, (ExecutableElement) obj);
            }
        });
        final HashMap map2 = new HashMap(listMethodsIn.size());
        annotationMirror.getElementValues().entrySet().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.MapMappingGem$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MapMappingGem.lambda$build$2(map2, (Map.Entry) obj);
            }
        });
        for (String str : map.keySet()) {
            if ("keyDateFormat".equals(str)) {
                builder.setKeydateformat(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("valueDateFormat".equals(str)) {
                builder.setValuedateformat(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("keyNumberFormat".equals(str)) {
                builder.setKeynumberformat(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("valueNumberFormat".equals(str)) {
                builder.setValuenumberformat(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("keyQualifiedBy".equals(str)) {
                builder.setKeyqualifiedby(GemValue.createArray((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("keyQualifiedByName".equals(str)) {
                builder.setKeyqualifiedbyname(GemValue.createArray((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("valueQualifiedBy".equals(str)) {
                builder.setValuequalifiedby(GemValue.createArray((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("valueQualifiedByName".equals(str)) {
                builder.setValuequalifiedbyname(GemValue.createArray((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("keyTargetType".equals(str)) {
                builder.setKeytargettype(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("valueTargetType".equals(str)) {
                builder.setValuetargettype(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("nullValueMappingStrategy".equals(str)) {
                builder.setNullvaluemappingstrategy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("keyMappingControl".equals(str)) {
                builder.setKeymappingcontrol(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("valueMappingControl".equals(str)) {
                builder.setValuemappingcontrol(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            }
        }
        builder.setMirror(annotationMirror);
        return builder.build();
    }

    static /* synthetic */ void lambda$build$1(Map map, ExecutableElement executableElement) {
    }

    static /* synthetic */ void lambda$build$2(Map map, Map.Entry entry) {
    }

    private static class BuilderImpl implements Builder<MapMappingGem> {
        private GemValue<String> keyDateFormat;
        private GemValue<TypeMirror> keyMappingControl;
        private GemValue<String> keyNumberFormat;
        private GemValue<List<TypeMirror>> keyQualifiedBy;
        private GemValue<List<String>> keyQualifiedByName;
        private GemValue<TypeMirror> keyTargetType;
        private AnnotationMirror mirror;
        private GemValue<String> nullValueMappingStrategy;
        private GemValue<String> valueDateFormat;
        private GemValue<TypeMirror> valueMappingControl;
        private GemValue<String> valueNumberFormat;
        private GemValue<List<TypeMirror>> valueQualifiedBy;
        private GemValue<List<String>> valueQualifiedByName;
        private GemValue<TypeMirror> valueTargetType;

        private BuilderImpl() {
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public Builder setKeydateformat(GemValue<String> gemValue) {
            this.keyDateFormat = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public Builder setValuedateformat(GemValue<String> gemValue) {
            this.valueDateFormat = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public Builder setKeynumberformat(GemValue<String> gemValue) {
            this.keyNumberFormat = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public Builder setValuenumberformat(GemValue<String> gemValue) {
            this.valueNumberFormat = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public Builder setKeyqualifiedby(GemValue<List<TypeMirror>> gemValue) {
            this.keyQualifiedBy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public Builder setKeyqualifiedbyname(GemValue<List<String>> gemValue) {
            this.keyQualifiedByName = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public Builder setValuequalifiedby(GemValue<List<TypeMirror>> gemValue) {
            this.valueQualifiedBy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public Builder setValuequalifiedbyname(GemValue<List<String>> gemValue) {
            this.valueQualifiedByName = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public Builder setKeytargettype(GemValue<TypeMirror> gemValue) {
            this.keyTargetType = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public Builder setValuetargettype(GemValue<TypeMirror> gemValue) {
            this.valueTargetType = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public Builder setNullvaluemappingstrategy(GemValue<String> gemValue) {
            this.nullValueMappingStrategy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public Builder setKeymappingcontrol(GemValue<TypeMirror> gemValue) {
            this.keyMappingControl = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public Builder setValuemappingcontrol(GemValue<TypeMirror> gemValue) {
            this.valueMappingControl = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public Builder setMirror(AnnotationMirror annotationMirror) {
            this.mirror = annotationMirror;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MapMappingGem.Builder
        public MapMappingGem build() {
            return new MapMappingGem(this);
        }
    }
}
