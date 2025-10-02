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
public class EnumMappingGem implements Gem {
    private final GemValue<String> configuration;
    private final boolean isValid;
    private final AnnotationMirror mirror;
    private final GemValue<String> nameTransformationStrategy;
    private final GemValue<TypeMirror> unexpectedValueMappingException;

    public interface Builder<T> {
        T build();

        Builder setConfiguration(GemValue<String> gemValue);

        Builder setMirror(AnnotationMirror annotationMirror);

        Builder setNametransformationstrategy(GemValue<String> gemValue);

        Builder setUnexpectedvaluemappingexception(GemValue<TypeMirror> gemValue);
    }

    private EnumMappingGem(BuilderImpl builderImpl) {
        GemValue<String> gemValue = builderImpl.nameTransformationStrategy;
        this.nameTransformationStrategy = gemValue;
        GemValue<String> gemValue2 = builderImpl.configuration;
        this.configuration = gemValue2;
        GemValue<TypeMirror> gemValue3 = builderImpl.unexpectedValueMappingException;
        this.unexpectedValueMappingException = gemValue3;
        this.isValid = gemValue != null && gemValue.isValid() && gemValue2 != null && gemValue2.isValid() && gemValue3 != null && gemValue3.isValid();
        this.mirror = builderImpl.mirror;
    }

    public GemValue<String> nameTransformationStrategy() {
        return this.nameTransformationStrategy;
    }

    public GemValue<String> configuration() {
        return this.configuration;
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

    public static EnumMappingGem instanceOn(Element element) {
        return (EnumMappingGem) build(element, new BuilderImpl());
    }

    public static EnumMappingGem instanceOn(AnnotationMirror annotationMirror) {
        return (EnumMappingGem) build(annotationMirror, new BuilderImpl());
    }

    public static <T> T build(Element element, Builder<T> builder) {
        return (T) build((AnnotationMirror) element.getAnnotationMirrors().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.gem.EnumMappingGem$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return "org.mapstruct.EnumMapping".contentEquals((CharSequence) ((AnnotationMirror) obj).getAnnotationType().asElement().getQualifiedName());
            }
        }).findAny().orElse(null), builder);
    }

    public static <T> T build(AnnotationMirror annotationMirror, Builder<T> builder) {
        if (annotationMirror == null || builder == null) {
            return null;
        }
        List listMethodsIn = ElementFilter.methodsIn(annotationMirror.getAnnotationType().asElement().getEnclosedElements());
        final HashMap map = new HashMap(listMethodsIn.size());
        listMethodsIn.forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.EnumMappingGem$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                EnumMappingGem.lambda$build$1(map, (ExecutableElement) obj);
            }
        });
        final HashMap map2 = new HashMap(listMethodsIn.size());
        annotationMirror.getElementValues().entrySet().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.EnumMappingGem$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                EnumMappingGem.lambda$build$2(map2, (Map.Entry) obj);
            }
        });
        for (String str : map.keySet()) {
            if ("nameTransformationStrategy".equals(str)) {
                builder.setNametransformationstrategy(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("configuration".equals(str)) {
                builder.setConfiguration(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
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

    private static class BuilderImpl implements Builder<EnumMappingGem> {
        private GemValue<String> configuration;
        private AnnotationMirror mirror;
        private GemValue<String> nameTransformationStrategy;
        private GemValue<TypeMirror> unexpectedValueMappingException;

        private BuilderImpl() {
        }

        @Override // org.mapstruct.ap.internal.gem.EnumMappingGem.Builder
        public Builder setNametransformationstrategy(GemValue<String> gemValue) {
            this.nameTransformationStrategy = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.EnumMappingGem.Builder
        public Builder setConfiguration(GemValue<String> gemValue) {
            this.configuration = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.EnumMappingGem.Builder
        public Builder setUnexpectedvaluemappingexception(GemValue<TypeMirror> gemValue) {
            this.unexpectedValueMappingException = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.EnumMappingGem.Builder
        public Builder setMirror(AnnotationMirror annotationMirror) {
            this.mirror = annotationMirror;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.EnumMappingGem.Builder
        public EnumMappingGem build() {
            return new EnumMappingGem(this);
        }
    }
}
