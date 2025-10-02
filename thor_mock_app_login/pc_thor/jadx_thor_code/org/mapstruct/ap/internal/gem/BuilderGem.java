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
public class BuilderGem implements Gem {
    private final GemValue<String> buildMethod;
    private final GemValue<Boolean> disableBuilder;
    private final boolean isValid;
    private final AnnotationMirror mirror;

    public interface Builder_<T> {
        T build();

        Builder_ setBuildmethod(GemValue<String> gemValue);

        Builder_ setDisablebuilder(GemValue<Boolean> gemValue);

        Builder_ setMirror(AnnotationMirror annotationMirror);
    }

    private BuilderGem(BuilderImpl builderImpl) {
        GemValue<String> gemValue = builderImpl.buildMethod;
        this.buildMethod = gemValue;
        GemValue<Boolean> gemValue2 = builderImpl.disableBuilder;
        this.disableBuilder = gemValue2;
        this.isValid = gemValue != null && gemValue.isValid() && gemValue2 != null && gemValue2.isValid();
        this.mirror = builderImpl.mirror;
    }

    public GemValue<String> buildMethod() {
        return this.buildMethod;
    }

    public GemValue<Boolean> disableBuilder() {
        return this.disableBuilder;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public AnnotationMirror mirror() {
        return this.mirror;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public boolean isValid() {
        return this.isValid;
    }

    public static BuilderGem instanceOn(Element element) {
        return (BuilderGem) build(element, new BuilderImpl());
    }

    public static BuilderGem instanceOn(AnnotationMirror annotationMirror) {
        return (BuilderGem) build(annotationMirror, new BuilderImpl());
    }

    public static <T> T build(Element element, Builder_<T> builder_) {
        return (T) build((AnnotationMirror) element.getAnnotationMirrors().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.gem.BuilderGem$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return "org.mapstruct.Builder".contentEquals((CharSequence) ((AnnotationMirror) obj).getAnnotationType().asElement().getQualifiedName());
            }
        }).findAny().orElse(null), builder_);
    }

    public static <T> T build(AnnotationMirror annotationMirror, Builder_<T> builder_) {
        if (annotationMirror == null || builder_ == null) {
            return null;
        }
        List listMethodsIn = ElementFilter.methodsIn(annotationMirror.getAnnotationType().asElement().getEnclosedElements());
        final HashMap map = new HashMap(listMethodsIn.size());
        listMethodsIn.forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.BuilderGem$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BuilderGem.lambda$build$1(map, (ExecutableElement) obj);
            }
        });
        final HashMap map2 = new HashMap(listMethodsIn.size());
        annotationMirror.getElementValues().entrySet().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.BuilderGem$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BuilderGem.lambda$build$2(map2, (Map.Entry) obj);
            }
        });
        for (String str : map.keySet()) {
            if ("buildMethod".equals(str)) {
                builder_.setBuildmethod(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("disableBuilder".equals(str)) {
                builder_.setDisablebuilder(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), Boolean.class));
            }
        }
        builder_.setMirror(annotationMirror);
        return builder_.build();
    }

    static /* synthetic */ void lambda$build$1(Map map, ExecutableElement executableElement) {
    }

    static /* synthetic */ void lambda$build$2(Map map, Map.Entry entry) {
    }

    private static class BuilderImpl implements Builder_<BuilderGem> {
        private GemValue<String> buildMethod;
        private GemValue<Boolean> disableBuilder;
        private AnnotationMirror mirror;

        private BuilderImpl() {
        }

        @Override // org.mapstruct.ap.internal.gem.BuilderGem.Builder_
        public Builder_ setBuildmethod(GemValue<String> gemValue) {
            this.buildMethod = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.BuilderGem.Builder_
        public Builder_ setDisablebuilder(GemValue<Boolean> gemValue) {
            this.disableBuilder = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.BuilderGem.Builder_
        public Builder_ setMirror(AnnotationMirror annotationMirror) {
            this.mirror = annotationMirror;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.BuilderGem.Builder_
        public BuilderGem build() {
            return new BuilderGem(this);
        }
    }
}
