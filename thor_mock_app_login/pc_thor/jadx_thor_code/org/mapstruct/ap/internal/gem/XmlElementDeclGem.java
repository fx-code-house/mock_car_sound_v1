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
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem;
import org.mapstruct.ap.shaded.org.mapstruct.tools.gem.GemValue;

/* loaded from: classes3.dex */
public class XmlElementDeclGem implements Gem {
    private final GemValue<String> defaultValue;
    private final boolean isValid;
    private final AnnotationMirror mirror;
    private final GemValue<String> name;
    private final GemValue<String> namespace;
    private final GemValue<TypeMirror> scope;
    private final GemValue<String> substitutionHeadName;
    private final GemValue<String> substitutionHeadNamespace;

    public interface Builder<T> {
        T build();

        Builder setDefaultvalue(GemValue<String> gemValue);

        Builder setMirror(AnnotationMirror annotationMirror);

        Builder setName(GemValue<String> gemValue);

        Builder setNamespace(GemValue<String> gemValue);

        Builder setScope(GemValue<TypeMirror> gemValue);

        Builder setSubstitutionheadname(GemValue<String> gemValue);

        Builder setSubstitutionheadnamespace(GemValue<String> gemValue);
    }

    private XmlElementDeclGem(BuilderImpl builderImpl) {
        GemValue<TypeMirror> gemValue = builderImpl.scope;
        this.scope = gemValue;
        GemValue<String> gemValue2 = builderImpl.namespace;
        this.namespace = gemValue2;
        GemValue<String> gemValue3 = builderImpl.name;
        this.name = gemValue3;
        GemValue<String> gemValue4 = builderImpl.substitutionHeadNamespace;
        this.substitutionHeadNamespace = gemValue4;
        GemValue<String> gemValue5 = builderImpl.substitutionHeadName;
        this.substitutionHeadName = gemValue5;
        GemValue<String> gemValue6 = builderImpl.defaultValue;
        this.defaultValue = gemValue6;
        this.isValid = gemValue != null && gemValue.isValid() && gemValue2 != null && gemValue2.isValid() && gemValue3 != null && gemValue3.isValid() && gemValue4 != null && gemValue4.isValid() && gemValue5 != null && gemValue5.isValid() && gemValue6 != null && gemValue6.isValid();
        this.mirror = builderImpl.mirror;
    }

    public GemValue<TypeMirror> scope() {
        return this.scope;
    }

    public GemValue<String> namespace() {
        return this.namespace;
    }

    public GemValue<String> name() {
        return this.name;
    }

    public GemValue<String> substitutionHeadNamespace() {
        return this.substitutionHeadNamespace;
    }

    public GemValue<String> substitutionHeadName() {
        return this.substitutionHeadName;
    }

    public GemValue<String> defaultValue() {
        return this.defaultValue;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public AnnotationMirror mirror() {
        return this.mirror;
    }

    @Override // org.mapstruct.ap.shaded.org.mapstruct.tools.gem.Gem
    public boolean isValid() {
        return this.isValid;
    }

    public static XmlElementDeclGem instanceOn(Element element) {
        return (XmlElementDeclGem) build(element, new BuilderImpl());
    }

    public static XmlElementDeclGem instanceOn(AnnotationMirror annotationMirror) {
        return (XmlElementDeclGem) build(annotationMirror, new BuilderImpl());
    }

    public static <T> T build(Element element, Builder<T> builder) {
        return (T) build((AnnotationMirror) element.getAnnotationMirrors().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.gem.XmlElementDeclGem$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return "javax.xml.bind.annotation.XmlElementDecl".contentEquals((CharSequence) ((AnnotationMirror) obj).getAnnotationType().asElement().getQualifiedName());
            }
        }).findAny().orElse(null), builder);
    }

    public static <T> T build(AnnotationMirror annotationMirror, Builder<T> builder) {
        if (annotationMirror == null || builder == null) {
            return null;
        }
        List listMethodsIn = ElementFilter.methodsIn(annotationMirror.getAnnotationType().asElement().getEnclosedElements());
        final HashMap map = new HashMap(listMethodsIn.size());
        listMethodsIn.forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.XmlElementDeclGem$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                XmlElementDeclGem.lambda$build$1(map, (ExecutableElement) obj);
            }
        });
        final HashMap map2 = new HashMap(listMethodsIn.size());
        annotationMirror.getElementValues().entrySet().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.gem.XmlElementDeclGem$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                XmlElementDeclGem.lambda$build$2(map2, (Map.Entry) obj);
            }
        });
        for (String str : map.keySet()) {
            if ("scope".equals(str)) {
                builder.setScope(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), TypeMirror.class));
            } else if ("namespace".equals(str)) {
                builder.setNamespace(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if (AppMeasurementSdk.ConditionalUserProperty.NAME.equals(str)) {
                builder.setName(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("substitutionHeadNamespace".equals(str)) {
                builder.setSubstitutionheadnamespace(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("substitutionHeadName".equals(str)) {
                builder.setSubstitutionheadname(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            } else if ("defaultValue".equals(str)) {
                builder.setDefaultvalue(GemValue.create((AnnotationValue) map2.get(str), (AnnotationValue) map.get(str), String.class));
            }
        }
        builder.setMirror(annotationMirror);
        return builder.build();
    }

    static /* synthetic */ void lambda$build$1(Map map, ExecutableElement executableElement) {
    }

    static /* synthetic */ void lambda$build$2(Map map, Map.Entry entry) {
    }

    private static class BuilderImpl implements Builder<XmlElementDeclGem> {
        private GemValue<String> defaultValue;
        private AnnotationMirror mirror;
        private GemValue<String> name;
        private GemValue<String> namespace;
        private GemValue<TypeMirror> scope;
        private GemValue<String> substitutionHeadName;
        private GemValue<String> substitutionHeadNamespace;

        private BuilderImpl() {
        }

        @Override // org.mapstruct.ap.internal.gem.XmlElementDeclGem.Builder
        public Builder setScope(GemValue<TypeMirror> gemValue) {
            this.scope = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.XmlElementDeclGem.Builder
        public Builder setNamespace(GemValue<String> gemValue) {
            this.namespace = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.XmlElementDeclGem.Builder
        public Builder setName(GemValue<String> gemValue) {
            this.name = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.XmlElementDeclGem.Builder
        public Builder setSubstitutionheadnamespace(GemValue<String> gemValue) {
            this.substitutionHeadNamespace = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.XmlElementDeclGem.Builder
        public Builder setSubstitutionheadname(GemValue<String> gemValue) {
            this.substitutionHeadName = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.XmlElementDeclGem.Builder
        public Builder setDefaultvalue(GemValue<String> gemValue) {
            this.defaultValue = gemValue;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.XmlElementDeclGem.Builder
        public Builder setMirror(AnnotationMirror annotationMirror) {
            this.mirror = annotationMirror;
            return this;
        }

        @Override // org.mapstruct.ap.internal.gem.XmlElementDeclGem.Builder
        public XmlElementDeclGem build() {
            return new XmlElementDeclGem(this);
        }
    }
}
