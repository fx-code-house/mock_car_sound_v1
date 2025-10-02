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
public class MappingControlGem implements Gem {
    private final boolean isValid;
    private final AnnotationMirror mirror;
    private final GemValue<String> value;

    public interface Builder<T> {
        T build();

        Builder setMirror(AnnotationMirror annotationMirror);

        Builder setValue(GemValue<String> gemValue);
    }

    private MappingControlGem(BuilderImpl builderImpl) {
        GemValue<String> gemValue = builderImpl.value;
        this.value = gemValue;
        this.isValid = gemValue != null ? gemValue.isValid() : false;
        this.mirror = builderImpl.mirror;
    }

    public GemValue<String> value() {
        return this.value;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public AnnotationMirror mirror() {
        return this.mirror;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public boolean isValid() {
        return this.isValid;
    }

    public static MappingControlGem instanceOn(Element element) {
        return (MappingControlGem) build(element, new BuilderImpl());
    }

    public static MappingControlGem instanceOn(AnnotationMirror annotationMirror) {
        return (MappingControlGem) build(annotationMirror, new BuilderImpl());
    }

    public static <T> T build(Element element, Builder<T> builder) {
        return (T) build((AnnotationMirror) element.getAnnotationMirrors().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.gem.MappingControlGem$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return "org.mapstruct.control.MappingControl".contentEquals((CharSequence) ((AnnotationMirror) obj).getAnnotationType().asElement().getQualifiedName());
            }
        }).findAny().orElse(null), builder);
    }

    public static <T> T build(AnnotationMirror annotationMirror, Builder<T> builder) {
        if (annotationMirror == null || builder == null) {
            return null;
        }
        List listMethodsIn = ElementFilter.methodsIn(annotationMirror.getAnnotationType().asElement().getEnclosedElements());
        final HashMap map = new HashMap(listMethodsIn.size());
        listMethodsIn.forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.MappingControlGem$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MappingControlGem.lambda$build$1(map, (ExecutableElement) obj);
            }
        });
        final HashMap map2 = new HashMap(listMethodsIn.size());
        annotationMirror.getElementValues().entrySet().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.MappingControlGem$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MappingControlGem.lambda$build$2(map2, (Map.Entry) obj);
            }
        });
        for (String str : map.keySet()) {
            if ("value".equals(str)) {
                builder.setValue(GemValue.createEnum((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str)));
            }
        }
        builder.setMirror(annotationMirror);
        return builder.build();
    }

    static /* synthetic */ void lambda$build$1(Map map, ExecutableElement executableElement) {
    }

    static /* synthetic */ void lambda$build$2(Map map, Map.Entry entry) {
    }

    private static class BuilderImpl implements Builder<MappingControlGem> {
        private AnnotationMirror mirror;
        private GemValue<String> value;

        private BuilderImpl() {
        }

        @Override // org.mapstruct.ap.internal.gem.MappingControlGem.Builder
        public Builder setValue(GemValue<String> gemValue) {
            this.value = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingControlGem.Builder
        public Builder setMirror(AnnotationMirror annotationMirror) {
            this.mirror = annotationMirror;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.MappingControlGem.Builder
        public MappingControlGem build() {
            return new MappingControlGem(this);
        }
    }
}
