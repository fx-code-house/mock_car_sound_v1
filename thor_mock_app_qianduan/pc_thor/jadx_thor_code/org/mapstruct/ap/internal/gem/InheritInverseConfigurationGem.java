package org.mapstruct.ap.internal.gem;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
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
public class InheritInverseConfigurationGem implements Gem {
    private final boolean isValid;
    private final AnnotationMirror mirror;
    private final GemValue<String> name;

    public interface Builder<T> {
        T build();

        Builder setMirror(AnnotationMirror annotationMirror);

        Builder setName(GemValue<String> gemValue);
    }

    private InheritInverseConfigurationGem(BuilderImpl builderImpl) {
        GemValue<String> gemValue = builderImpl.name;
        this.name = gemValue;
        this.isValid = gemValue != null ? gemValue.isValid() : false;
        this.mirror = builderImpl.mirror;
    }

    public GemValue<String> name() {
        return this.name;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public AnnotationMirror mirror() {
        return this.mirror;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public boolean isValid() {
        return this.isValid;
    }

    public static InheritInverseConfigurationGem instanceOn(Element element) {
        return (InheritInverseConfigurationGem) build(element, new BuilderImpl());
    }

    public static InheritInverseConfigurationGem instanceOn(AnnotationMirror annotationMirror) {
        return (InheritInverseConfigurationGem) build(annotationMirror, new BuilderImpl());
    }

    public static <T> T build(Element element, Builder<T> builder) {
        return (T) build((AnnotationMirror) element.getAnnotationMirrors().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.gem.InheritInverseConfigurationGem$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return "org.mapstruct.InheritInverseConfiguration".contentEquals((CharSequence) ((AnnotationMirror) obj).getAnnotationType().asElement().getQualifiedName());
            }
        }).findAny().orElse(null), builder);
    }

    public static <T> T build(AnnotationMirror annotationMirror, Builder<T> builder) {
        if (annotationMirror == null || builder == null) {
            return null;
        }
        List listMethodsIn = ElementFilter.methodsIn(annotationMirror.getAnnotationType().asElement().getEnclosedElements());
        final HashMap map = new HashMap(listMethodsIn.size());
        listMethodsIn.forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.InheritInverseConfigurationGem$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InheritInverseConfigurationGem.lambda$build$1(map, (ExecutableElement) obj);
            }
        });
        final HashMap map2 = new HashMap(listMethodsIn.size());
        annotationMirror.getElementValues().entrySet().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.InheritInverseConfigurationGem$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InheritInverseConfigurationGem.lambda$build$2(map2, (Map.Entry) obj);
            }
        });
        for (String str : map.keySet()) {
            if (AppMeasurementSdk.ConditionalUserProperty.NAME.equals(str)) {
                builder.setName(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            }
        }
        builder.setMirror(annotationMirror);
        return builder.build();
    }

    static /* synthetic */ void lambda$build$1(Map map, ExecutableElement executableElement) {
    }

    static /* synthetic */ void lambda$build$2(Map map, Map.Entry entry) {
    }

    private static class BuilderImpl implements Builder<InheritInverseConfigurationGem> {
        private AnnotationMirror mirror;
        private GemValue<String> name;

        private BuilderImpl() {
        }

        @Override // org.mapstruct.ap.internal.gem.InheritInverseConfigurationGem.Builder
        public Builder setName(GemValue<String> gemValue) {
            this.name = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.InheritInverseConfigurationGem.Builder
        public Builder setMirror(AnnotationMirror annotationMirror) {
            this.mirror = annotationMirror;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.InheritInverseConfigurationGem.Builder
        public InheritInverseConfigurationGem build() {
            return new InheritInverseConfigurationGem(this);
        }
    }
}
