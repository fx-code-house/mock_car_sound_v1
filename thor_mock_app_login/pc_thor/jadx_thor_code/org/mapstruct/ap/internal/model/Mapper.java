package org.mapstruct.ap.internal.model;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import org.mapstruct.ap.internal.model.GeneratedType;
import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.version.VersionInformation;

/* loaded from: classes3.dex */
public class Mapper extends GeneratedType {
    static final String CLASS_NAME_PLACEHOLDER = "<CLASS_NAME>";
    static final String DEFAULT_IMPLEMENTATION_CLASS = "<CLASS_NAME>Impl";
    static final String DEFAULT_IMPLEMENTATION_PACKAGE = "<PACKAGE_NAME>";
    static final String PACKAGE_NAME_PLACEHOLDER = "<PACKAGE_NAME>";
    private final boolean customImplName;
    private final boolean customPackage;
    private Decorator decorator;

    public static class Builder extends GeneratedType.GeneratedTypeBuilder<Builder> {
        private boolean customName;
        private boolean customPackage;
        private Decorator decorator;
        private TypeElement element;
        private List<Field> fields;
        private Set<SupportingConstructorFragment> fragments;
        private String implName;
        private String implPackage;

        public Builder() {
            super(Builder.class);
        }

        public Builder element(TypeElement typeElement) {
            this.element = typeElement;
            return this;
        }

        public Builder fields(List<Field> list) {
            this.fields = list;
            return this;
        }

        public Builder constructorFragments(Set<SupportingConstructorFragment> set) {
            this.fragments = set;
            return this;
        }

        public Builder decorator(Decorator decorator) {
            this.decorator = decorator;
            return this;
        }

        public Builder implName(String str) {
            this.implName = str;
            this.customName = !Mapper.DEFAULT_IMPLEMENTATION_CLASS.equals(str);
            return this;
        }

        public Builder implPackage(String str) {
            this.implPackage = str;
            this.customPackage = !"<PACKAGE_NAME>".equals(str);
            return this;
        }

        public Mapper build() {
            String str = this.implName.replace(Mapper.CLASS_NAME_PLACEHOLDER, Mapper.getFlatName(this.element)) + (this.decorator == null ? "" : "_");
            String string = this.elementUtils.getPackageOf(this.element).getQualifiedName().toString();
            return new Mapper(this.typeFactory, this.implPackage.replace("<PACKAGE_NAME>", string), str, this.element.getKind() != ElementKind.INTERFACE ? this.element.getSimpleName().toString() : null, string, this.element.getKind() == ElementKind.INTERFACE ? this.element.getSimpleName().toString() : null, this.customPackage, this.customName, this.methods, this.options, this.versionInformation, Accessibility.fromModifiers(this.element.getModifiers()), this.fields, !this.fragments.isEmpty() ? new NoArgumentConstructor(str, this.fragments) : null, this.decorator, this.extraImportedTypes);
        }
    }

    private Mapper(TypeFactory typeFactory, String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, List<MappingMethod> list, Options options, VersionInformation versionInformation, Accessibility accessibility, List<Field> list2, Constructor constructor, Decorator decorator, SortedSet<Type> sortedSet) {
        super(typeFactory, str, str2, str3, str4, str5, list, list2, options, versionInformation, accessibility, sortedSet, constructor);
        this.customPackage = z;
        this.customImplName = z2;
        this.decorator = decorator;
    }

    public Decorator getDecorator() {
        return this.decorator;
    }

    public void removeDecorator() {
        this.decorator = null;
    }

    public boolean hasCustomImplementation() {
        return this.customImplName || this.customPackage;
    }

    @Override // org.mapstruct.ap.internal.writer.FreeMarkerWritable
    protected String getTemplateName() {
        return getTemplateNameForClass(GeneratedType.class);
    }

    public static String getFlatName(TypeElement typeElement) {
        if (!(typeElement.getEnclosingElement() instanceof TypeElement)) {
            return typeElement.getSimpleName().toString();
        }
        StringBuilder sb = new StringBuilder(typeElement.getSimpleName().toString());
        for (Element enclosingElement = typeElement.getEnclosingElement(); enclosingElement instanceof TypeElement; enclosingElement = enclosingElement.getEnclosingElement()) {
            sb.insert(0, '$');
            sb.insert(0, enclosingElement.getSimpleName().toString());
        }
        return sb.toString();
    }
}
