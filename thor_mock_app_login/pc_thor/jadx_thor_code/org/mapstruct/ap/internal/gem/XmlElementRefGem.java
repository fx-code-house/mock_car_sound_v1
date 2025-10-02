package org.mapstruct.ap.internal.gem;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
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
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem;
import org.mapstruct.ap.shaded.org.mapstruct.tools.gem.GemValue;

/* loaded from: classes3.dex */
public class XmlElementRefGem implements Gem {
    private final boolean isValid;
    private final AnnotationMirror mirror;
    private final GemValue<String> name;
    private final GemValue<String> namespace;
    private final GemValue<Boolean> required;
    private final GemValue<TypeMirror> type;

    public interface Builder<T> {
        T build();

        Builder setMirror(AnnotationMirror annotationMirror);

        Builder setName(GemValue<String> gemValue);

        Builder setNamespace(GemValue<String> gemValue);

        Builder setRequired(GemValue<Boolean> gemValue);

        Builder setType(GemValue<TypeMirror> gemValue);
    }

    private XmlElementRefGem(BuilderImpl builderImpl) {
        GemValue<TypeMirror> gemValue = builderImpl.type;
        this.type = gemValue;
        GemValue<String> gemValue2 = builderImpl.namespace;
        this.namespace = gemValue2;
        GemValue<String> gemValue3 = builderImpl.name;
        this.name = gemValue3;
        GemValue<Boolean> gemValue4 = builderImpl.required;
        this.required = gemValue4;
        this.isValid = gemValue != null && gemValue.isValid() && gemValue2 != null && gemValue2.isValid() && gemValue3 != null && gemValue3.isValid() && gemValue4 != null && gemValue4.isValid();
        this.mirror = builderImpl.mirror;
    }

    public GemValue<TypeMirror> type() {
        return this.type;
    }

    public GemValue<String> namespace() {
        return this.namespace;
    }

    public GemValue<String> name() {
        return this.name;
    }

    public GemValue<Boolean> required() {
        return this.required;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public AnnotationMirror mirror() {
        return this.mirror;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public boolean isValid() {
        return this.isValid;
    }

    public static XmlElementRefGem instanceOn(Element element) {
        return (XmlElementRefGem) build(element, new BuilderImpl());
    }

    public static XmlElementRefGem instanceOn(AnnotationMirror annotationMirror) {
        return (XmlElementRefGem) build(annotationMirror, new BuilderImpl());
    }

    public static <T> T build(Element element, Builder<T> builder) {
        return (T) build((AnnotationMirror) element.getAnnotationMirrors().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.gem.XmlElementRefGem$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return "javax.xml.bind.annotation.XmlElementRef".contentEquals((CharSequence) ((AnnotationMirror) obj).getAnnotationType().asElement().getQualifiedName());
            }
        }).findAny().orElse(null), builder);
    }

    public static <T> T build(AnnotationMirror annotationMirror, Builder<T> builder) {
        if (annotationMirror == null || builder == null) {
            return null;
        }
        List listMethodsIn = ElementFilter.methodsIn(annotationMirror.getAnnotationType().asElement().getEnclosedElements());
        final HashMap map = new HashMap(listMethodsIn.size());
        listMethodsIn.forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.XmlElementRefGem$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                XmlElementRefGem.lambda$build$1(map, (ExecutableElement) obj);
            }
        });
        final HashMap map2 = new HashMap(listMethodsIn.size());
        annotationMirror.getElementValues().entrySet().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.XmlElementRefGem$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                XmlElementRefGem.lambda$build$2(map2, (Map.Entry) obj);
            }
        });
        for (String str : map.keySet()) {
            if (SessionDescription.ATTR_TYPE.equals(str)) {
                builder.setType(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("namespace".equals(str)) {
                builder.setNamespace(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if (AppMeasurementSdk.ConditionalUserProperty.NAME.equals(str)) {
                builder.setName(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("required".equals(str)) {
                builder.setRequired(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), Boolean.class));
            }
        }
        builder.setMirror(annotationMirror);
        return builder.build();
    }

    static /* synthetic */ void lambda$build$1(Map map, ExecutableElement executableElement) {
    }

    static /* synthetic */ void lambda$build$2(Map map, Map.Entry entry) {
    }

    private static class BuilderImpl implements Builder<XmlElementRefGem> {
        private AnnotationMirror mirror;
        private GemValue<String> name;
        private GemValue<String> namespace;
        private GemValue<Boolean> required;
        private GemValue<TypeMirror> type;

        private BuilderImpl() {
        }

        @Override // org.mapstruct.ap.internal.gem.XmlElementRefGem.Builder
        public Builder setType(GemValue<TypeMirror> gemValue) {
            this.type = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.XmlElementRefGem.Builder
        public Builder setNamespace(GemValue<String> gemValue) {
            this.namespace = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.XmlElementRefGem.Builder
        public Builder setName(GemValue<String> gemValue) {
            this.name = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.XmlElementRefGem.Builder
        public Builder setRequired(GemValue<Boolean> gemValue) {
            this.required = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.XmlElementRefGem.Builder
        public Builder setMirror(AnnotationMirror annotationMirror) {
            this.mirror = annotationMirror;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.XmlElementRefGem.Builder
        public XmlElementRefGem build() {
            return new XmlElementRefGem(this);
        }
    }
}
