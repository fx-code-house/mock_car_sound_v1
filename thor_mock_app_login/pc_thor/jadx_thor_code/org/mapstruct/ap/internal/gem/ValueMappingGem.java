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
import javax.lang.model.util.ElementFilter;
import org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem;
import org.mapstruct.ap.shaded.org.mapstruct.tools.gem.GemValue;

/* loaded from: classes3.dex */
public class ValueMappingGem implements Gem {
    private final boolean isValid;
    private final AnnotationMirror mirror;
    private final GemValue<String> source;
    private final GemValue<String> target;

    public interface Builder<T> {
        T build();

        Builder setMirror(AnnotationMirror annotationMirror);

        Builder setSource(GemValue<String> gemValue);

        Builder setTarget(GemValue<String> gemValue);
    }

    private ValueMappingGem(BuilderImpl builderImpl) {
        GemValue<String> gemValue = builderImpl.source;
        this.source = gemValue;
        GemValue<String> gemValue2 = builderImpl.target;
        this.target = gemValue2;
        this.isValid = gemValue != null && gemValue.isValid() && gemValue2 != null && gemValue2.isValid();
        this.mirror = builderImpl.mirror;
    }

    public GemValue<String> source() {
        return this.source;
    }

    public GemValue<String> target() {
        return this.target;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public AnnotationMirror mirror() {
        return this.mirror;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public boolean isValid() {
        return this.isValid;
    }

    public static ValueMappingGem instanceOn(Element element) {
        return (ValueMappingGem) build(element, new BuilderImpl());
    }

    public static ValueMappingGem instanceOn(AnnotationMirror annotationMirror) {
        return (ValueMappingGem) build(annotationMirror, new BuilderImpl());
    }

    public static <T> T build(Element element, Builder<T> builder) {
        return (T) build((AnnotationMirror) element.getAnnotationMirrors().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.gem.ValueMappingGem$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return "org.mapstruct.ValueMapping".contentEquals((CharSequence) ((AnnotationMirror) obj).getAnnotationType().asElement().getQualifiedName());
            }
        }).findAny().orElse(null), builder);
    }

    public static <T> T build(AnnotationMirror annotationMirror, Builder<T> builder) {
        if (annotationMirror == null || builder == null) {
            return null;
        }
        List listMethodsIn = ElementFilter.methodsIn(annotationMirror.getAnnotationType().asElement().getEnclosedElements());
        final HashMap map = new HashMap(listMethodsIn.size());
        listMethodsIn.forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.ValueMappingGem$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ValueMappingGem.lambda$build$1(map, (ExecutableElement) obj);
            }
        });
        final HashMap map2 = new HashMap(listMethodsIn.size());
        annotationMirror.getElementValues().entrySet().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.ValueMappingGem$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ValueMappingGem.lambda$build$2(map2, (Map.Entry) obj);
            }
        });
        for (String str : map.keySet()) {
            if ("source".equals(str)) {
                builder.setSource(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("target".equals(str)) {
                builder.setTarget(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            }
        }
        builder.setMirror(annotationMirror);
        return builder.build();
    }

    static /* synthetic */ void lambda$build$1(Map map, ExecutableElement executableElement) {
    }

    static /* synthetic */ void lambda$build$2(Map map, Map.Entry entry) {
    }

    private static class BuilderImpl implements Builder<ValueMappingGem> {
        private AnnotationMirror mirror;
        private GemValue<String> source;
        private GemValue<String> target;

        private BuilderImpl() {
        }

        @Override // org.mapstruct.ap.internal.gem.ValueMappingGem.Builder
        public Builder setSource(GemValue<String> gemValue) {
            this.source = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.ValueMappingGem.Builder
        public Builder setTarget(GemValue<String> gemValue) {
            this.target = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.ValueMappingGem.Builder
        public Builder setMirror(AnnotationMirror annotationMirror) {
            this.mirror = annotationMirror;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.ValueMappingGem.Builder
        public ValueMappingGem build() {
            return new ValueMappingGem(this);
        }
    }
}
