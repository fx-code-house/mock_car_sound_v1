package org.mapstruct.ap.internal.gem;

import java.util.function.Predicate;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem;

/* loaded from: classes3.dex */
public class QualifierGem implements Gem {
    private final boolean isValid;
    private final AnnotationMirror mirror;

    public interface Builder<T> {
        T build();

        Builder setMirror(AnnotationMirror annotationMirror);
    }

    private QualifierGem(BuilderImpl builderImpl) {
        this.isValid = true;
        this.mirror = builderImpl.mirror;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public AnnotationMirror mirror() {
        return this.mirror;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public boolean isValid() {
        return this.isValid;
    }

    public static QualifierGem instanceOn(Element element) {
        return (QualifierGem) build(element, new BuilderImpl());
    }

    public static QualifierGem instanceOn(AnnotationMirror annotationMirror) {
        return (QualifierGem) build(annotationMirror, new BuilderImpl());
    }

    public static <T> T build(Element element, Builder<T> builder) {
        return (T) build((AnnotationMirror) element.getAnnotationMirrors().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.gem.QualifierGem$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return "org.mapstruct.Qualifier".contentEquals((CharSequence) ((AnnotationMirror) obj).getAnnotationType().asElement().getQualifiedName());
            }
        }).findAny().orElse(null), builder);
    }

    public static <T> T build(AnnotationMirror annotationMirror, Builder<T> builder) {
        if (annotationMirror == null || builder == null) {
            return null;
        }
        builder.setMirror(annotationMirror);
        return builder.build();
    }

    private static class BuilderImpl implements Builder<QualifierGem> {
        private AnnotationMirror mirror;

        private BuilderImpl() {
        }

        @Override // org.mapstruct.ap.internal.gem.QualifierGem.Builder
        public Builder setMirror(AnnotationMirror annotationMirror) {
            this.mirror = annotationMirror;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.QualifierGem.Builder
        public QualifierGem build() {
            return new QualifierGem(this);
        }
    }
}
