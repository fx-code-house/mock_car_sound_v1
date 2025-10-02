package org.mapstruct.ap.internal.model;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import org.mapstruct.ap.internal.gem.DecoratedWithGem;
import org.mapstruct.ap.internal.model.GeneratedType;
import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.version.VersionInformation;

/* loaded from: classes3.dex */
public class Decorator extends GeneratedType {
    private final Type decoratorType;

    public static class Builder extends GeneratedType.GeneratedTypeBuilder<Builder> {
        private DecoratedWithGem decorator;
        private boolean hasDelegateConstructor;
        private String implName;
        private String implPackage;
        private TypeElement mapperElement;

        public Builder() {
            super(Builder.class);
        }

        public Builder mapperElement(TypeElement typeElement) {
            this.mapperElement = typeElement;
            return this;
        }

        public Builder decoratedWith(DecoratedWithGem decoratedWithGem) {
            this.decorator = decoratedWithGem;
            return this;
        }

        public Builder hasDelegateConstructor(boolean z) {
            this.hasDelegateConstructor = z;
            return this;
        }

        public Builder implName(String str) {
            if ("default".equals(str)) {
                str = "<CLASS_NAME>Impl";
            }
            this.implName = str;
            return this;
        }

        public Builder implPackage(String str) {
            if ("default".equals(str)) {
                str = "<PACKAGE_NAME>";
            }
            this.implPackage = str;
            return this;
        }

        public Decorator build() {
            String strReplace = this.implName.replace("<CLASS_NAME>", Mapper.getFlatName(this.mapperElement));
            Type type = this.typeFactory.getType(this.decorator.value().get());
            DecoratorConstructor decoratorConstructor = new DecoratorConstructor(strReplace, strReplace + "_", this.hasDelegateConstructor);
            String string = this.elementUtils.getPackageOf(this.mapperElement).getQualifiedName().toString();
            return new Decorator(this.typeFactory, this.implPackage.replace("<PACKAGE_NAME>", string), strReplace, type, string, this.mapperElement.getKind() == ElementKind.INTERFACE ? this.mapperElement.getSimpleName().toString() : null, this.methods, Arrays.asList(new Field(this.typeFactory.getType(this.mapperElement), "delegate", true)), this.options, this.versionInformation, Accessibility.fromModifiers(this.mapperElement.getModifiers()), this.extraImportedTypes, decoratorConstructor);
        }
    }

    private Decorator(TypeFactory typeFactory, String str, String str2, Type type, String str3, String str4, List<MappingMethod> list, List<Field> list2, Options options, VersionInformation versionInformation, Accessibility accessibility, SortedSet<Type> sortedSet, DecoratorConstructor decoratorConstructor) {
        super(typeFactory, str, str2, type.getName(), str3, str4, list, list2, options, versionInformation, accessibility, sortedSet, decoratorConstructor);
        this.decoratorType = type;
    }

    @Override // org.mapstruct.ap.internal.model.GeneratedType, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public SortedSet<Type> getImportTypes() {
        SortedSet<Type> importTypes = super.getImportTypes();
        if (this.decoratorType.getPackageName().equalsIgnoreCase(getPackageName())) {
            if (this.decoratorType.getTypeElement() != null && this.decoratorType.getTypeElement().getNestingKind().isNested()) {
                importTypes.add(this.decoratorType);
            }
        } else {
            importTypes.add(this.decoratorType);
        }
        return importTypes;
    }

    @Override // org.mapstruct.ap.internal.writer.FreeMarkerWritable
    protected String getTemplateName() {
        return getTemplateNameForClass(GeneratedType.class);
    }
}
