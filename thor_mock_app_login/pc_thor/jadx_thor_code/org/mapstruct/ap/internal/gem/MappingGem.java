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
public class MappingGem implements Gem {
    private final GemValue<String> constant;
    private final GemValue<String> dateFormat;
    private final GemValue<String> defaultExpression;
    private final GemValue<String> defaultValue;
    private final GemValue<List<String>> dependsOn;
    private final GemValue<String> expression;
    private final GemValue<Boolean> ignore;
    private final boolean isValid;
    private final GemValue<TypeMirror> mappingControl;
    private final AnnotationMirror mirror;
    private final GemValue<String> nullValueCheckStrategy;
    private final GemValue<String> nullValuePropertyMappingStrategy;
    private final GemValue<String> numberFormat;
    private final GemValue<List<TypeMirror>> qualifiedBy;
    private final GemValue<List<String>> qualifiedByName;
    private final GemValue<TypeMirror> resultType;
    private final GemValue<String> source;
    private final GemValue<String> target;

    public interface Builder<T> {
        T build();

        Builder setConstant(GemValue<String> gemValue);

        Builder setDateformat(GemValue<String> gemValue);

        Builder setDefaultexpression(GemValue<String> gemValue);

        Builder setDefaultvalue(GemValue<String> gemValue);

        Builder setDependson(GemValue<List<String>> gemValue);

        Builder setExpression(GemValue<String> gemValue);

        Builder setIgnore(GemValue<Boolean> gemValue);

        Builder setMappingcontrol(GemValue<TypeMirror> gemValue);

        Builder setMirror(AnnotationMirror annotationMirror);

        Builder setNullvaluecheckstrategy(GemValue<String> gemValue);

        Builder setNullvaluepropertymappingstrategy(GemValue<String> gemValue);

        Builder setNumberformat(GemValue<String> gemValue);

        Builder setQualifiedby(GemValue<List<TypeMirror>> gemValue);

        Builder setQualifiedbyname(GemValue<List<String>> gemValue);

        Builder setResulttype(GemValue<TypeMirror> gemValue);

        Builder setSource(GemValue<String> gemValue);

        Builder setTarget(GemValue<String> gemValue);
    }

    private MappingGem(BuilderImpl builderImpl) {
        GemValue<String> gemValue = builderImpl.target;
        this.target = gemValue;
        GemValue<String> gemValue2 = builderImpl.source;
        this.source = gemValue2;
        GemValue<String> gemValue3 = builderImpl.dateFormat;
        this.dateFormat = gemValue3;
        GemValue<String> gemValue4 = builderImpl.numberFormat;
        this.numberFormat = gemValue4;
        GemValue<String> gemValue5 = builderImpl.constant;
        this.constant = gemValue5;
        GemValue<String> gemValue6 = builderImpl.expression;
        this.expression = gemValue6;
        GemValue<String> gemValue7 = builderImpl.defaultExpression;
        this.defaultExpression = gemValue7;
        GemValue<Boolean> gemValue8 = builderImpl.ignore;
        this.ignore = gemValue8;
        GemValue<List<TypeMirror>> gemValue9 = builderImpl.qualifiedBy;
        this.qualifiedBy = gemValue9;
        GemValue<List<String>> gemValue10 = builderImpl.qualifiedByName;
        this.qualifiedByName = gemValue10;
        GemValue<TypeMirror> gemValue11 = builderImpl.resultType;
        this.resultType = gemValue11;
        GemValue<List<String>> gemValue12 = builderImpl.dependsOn;
        this.dependsOn = gemValue12;
        GemValue<String> gemValue13 = builderImpl.defaultValue;
        this.defaultValue = gemValue13;
        GemValue<String> gemValue14 = builderImpl.nullValueCheckStrategy;
        this.nullValueCheckStrategy = gemValue14;
        GemValue<String> gemValue15 = builderImpl.nullValuePropertyMappingStrategy;
        this.nullValuePropertyMappingStrategy = gemValue15;
        GemValue<TypeMirror> gemValue16 = builderImpl.mappingControl;
        this.mappingControl = gemValue16;
        this.isValid = gemValue != null && gemValue.isValid() && gemValue2 != null && gemValue2.isValid() && gemValue3 != null && gemValue3.isValid() && gemValue4 != null && gemValue4.isValid() && gemValue5 != null && gemValue5.isValid() && gemValue6 != null && gemValue6.isValid() && gemValue7 != null && gemValue7.isValid() && gemValue8 != null && gemValue8.isValid() && gemValue9 != null && gemValue9.isValid() && gemValue10 != null && gemValue10.isValid() && gemValue11 != null && gemValue11.isValid() && gemValue12 != null && gemValue12.isValid() && gemValue13 != null && gemValue13.isValid() && gemValue14 != null && gemValue14.isValid() && gemValue15 != null && gemValue15.isValid() && gemValue16 != null && gemValue16.isValid();
        this.mirror = builderImpl.mirror;
    }

    public GemValue<String> target() {
        return this.target;
    }

    public GemValue<String> source() {
        return this.source;
    }

    public GemValue<String> dateFormat() {
        return this.dateFormat;
    }

    public GemValue<String> numberFormat() {
        return this.numberFormat;
    }

    public GemValue<String> constant() {
        return this.constant;
    }

    public GemValue<String> expression() {
        return this.expression;
    }

    public GemValue<String> defaultExpression() {
        return this.defaultExpression;
    }

    public GemValue<Boolean> ignore() {
        return this.ignore;
    }

    public GemValue<List<TypeMirror>> qualifiedBy() {
        return this.qualifiedBy;
    }

    public GemValue<List<String>> qualifiedByName() {
        return this.qualifiedByName;
    }

    public GemValue<TypeMirror> resultType() {
        return this.resultType;
    }

    public GemValue<List<String>> dependsOn() {
        return this.dependsOn;
    }

    public GemValue<String> defaultValue() {
        return this.defaultValue;
    }

    public GemValue<String> nullValueCheckStrategy() {
        return this.nullValueCheckStrategy;
    }

    public GemValue<String> nullValuePropertyMappingStrategy() {
        return this.nullValuePropertyMappingStrategy;
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

    public static MappingGem instanceOn(Element element) {
        return (MappingGem) build(element, new BuilderImpl());
    }

    public static MappingGem instanceOn(AnnotationMirror annotationMirror) {
        return (MappingGem) build(annotationMirror, new BuilderImpl());
    }

    public static <T> T build(Element element, Builder<T> builder) {
        return (T) build((AnnotationMirror) element.getAnnotationMirrors().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.gem.MappingGem$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return "org.mapstruct.Mapping".contentEquals((CharSequence) ((AnnotationMirror) obj).getAnnotationType().asElement().getQualifiedName());
            }
        }).findAny().orElse(null), builder);
    }

    public static <T> T build(AnnotationMirror annotationMirror, Builder<T> builder) {
        if (annotationMirror == null || builder == null) {
            return null;
        }
        List listMethodsIn = ElementFilter.methodsIn(annotationMirror.getAnnotationType().asElement().getEnclosedElements());
        final HashMap map = new HashMap(listMethodsIn.size());
        listMethodsIn.forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.MappingGem$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MappingGem.lambda$build$1(map, (ExecutableElement) obj);
            }
        });
        final HashMap map2 = new HashMap(listMethodsIn.size());
        annotationMirror.getElementValues().entrySet().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.MappingGem$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MappingGem.lambda$build$2(map2, (Map.Entry) obj);
            }
        });
        for (String str : map.keySet()) {
            if ("target".equals(str)) {
                builder.setTarget(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("source".equals(str)) {
                builder.setSource(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("dateFormat".equals(str)) {
                builder.setDateformat(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("numberFormat".equals(str)) {
                builder.setNumberformat(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("constant".equals(str)) {
                builder.setConstant(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("expression".equals(str)) {
                builder.setExpression(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("defaultExpression".equals(str)) {
                builder.setDefaultexpression(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("ignore".equals(str)) {
                builder.setIgnore(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), Boolean.class));
            } else if ("qualifiedBy".equals(str)) {
                builder.setQualifiedby(GemValue.createArray((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("qualifiedByName".equals(str)) {
                builder.setQualifiedbyname(GemValue.createArray((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("resultType".equals(str)) {
                builder.setResulttype(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("dependsOn".equals(str)) {
                builder.setDependson(GemValue.createArray((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("defaultValue".equals(str)) {
                builder.setDefaultvalue(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("nullValueCheckStrategy".equals(str)) {
                builder.setNullvaluecheckstrategy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            } else if ("nullValuePropertyMappingStrategy".equals(str)) {
                builder.setNullvaluepropertymappingstrategy(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
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

    private static class BuilderImpl implements Builder<MappingGem> {
        private GemValue<String> constant;
        private GemValue<String> dateFormat;
        private GemValue<String> defaultExpression;
        private GemValue<String> defaultValue;
        private GemValue<List<String>> dependsOn;
        private GemValue<String> expression;
        private GemValue<Boolean> ignore;
        private GemValue<TypeMirror> mappingControl;
        private AnnotationMirror mirror;
        private GemValue<String> nullValueCheckStrategy;
        private GemValue<String> nullValuePropertyMappingStrategy;
        private GemValue<String> numberFormat;
        private GemValue<List<TypeMirror>> qualifiedBy;
        private GemValue<List<String>> qualifiedByName;
        private GemValue<TypeMirror> resultType;
        private GemValue<String> source;
        private GemValue<String> target;

        private BuilderImpl() {
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setTarget(GemValue<String> gemValue) {
            this.target = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setSource(GemValue<String> gemValue) {
            this.source = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setDateformat(GemValue<String> gemValue) {
            this.dateFormat = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setNumberformat(GemValue<String> gemValue) {
            this.numberFormat = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setConstant(GemValue<String> gemValue) {
            this.constant = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setExpression(GemValue<String> gemValue) {
            this.expression = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setDefaultexpression(GemValue<String> gemValue) {
            this.defaultExpression = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setIgnore(GemValue<Boolean> gemValue) {
            this.ignore = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setQualifiedby(GemValue<List<TypeMirror>> gemValue) {
            this.qualifiedBy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setQualifiedbyname(GemValue<List<String>> gemValue) {
            this.qualifiedByName = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setResulttype(GemValue<TypeMirror> gemValue) {
            this.resultType = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setDependson(GemValue<List<String>> gemValue) {
            this.dependsOn = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setDefaultvalue(GemValue<String> gemValue) {
            this.defaultValue = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setNullvaluecheckstrategy(GemValue<String> gemValue) {
            this.nullValueCheckStrategy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setNullvaluepropertymappingstrategy(GemValue<String> gemValue) {
            this.nullValuePropertyMappingStrategy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setMappingcontrol(GemValue<TypeMirror> gemValue) {
            this.mappingControl = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public Builder setMirror(AnnotationMirror annotationMirror) {
            this.mirror = annotationMirror;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingGem.Builder
        public MappingGem build() {
            return new MappingGem(this);
        }
    }
}
