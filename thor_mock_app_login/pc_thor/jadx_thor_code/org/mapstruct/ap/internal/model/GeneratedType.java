package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.version.VersionInformation;

/* loaded from: classes3.dex */
public abstract class GeneratedType extends ModelElement {
    private static final String JAVA_LANG_PACKAGE = "java.lang";
    private final Accessibility accessibility;
    private final List<Annotation> annotations = new ArrayList();
    private Constructor constructor;
    private final SortedSet<Type> extraImportedTypes;
    private List<Field> fields;
    private final Type generatedType;
    private final boolean generatedTypeAvailable;
    private final String interfaceName;
    private final String interfacePackage;
    private final List<MappingMethod> methods;
    private final String name;
    private final String packageName;
    private final String superClassName;
    private final boolean suppressGeneratorTimestamp;
    private final boolean suppressGeneratorVersionComment;
    private final VersionInformation versionInformation;

    /* JADX INFO: Access modifiers changed from: protected */
    public static abstract class GeneratedTypeBuilder<T extends GeneratedTypeBuilder> {
        protected Elements elementUtils;
        protected SortedSet<Type> extraImportedTypes;
        protected List<MappingMethod> methods;
        private T myself;
        protected Options options;
        protected TypeFactory typeFactory;
        protected VersionInformation versionInformation;

        GeneratedTypeBuilder(Class<T> cls) {
            this.myself = cls.cast(this);
        }

        public T elementUtils(Elements elements) {
            this.elementUtils = elements;
            return this.myself;
        }

        public T typeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this.myself;
        }

        public T options(Options options) {
            this.options = options;
            return this.myself;
        }

        public T versionInformation(VersionInformation versionInformation) {
            this.versionInformation = versionInformation;
            return this.myself;
        }

        public T extraImports(SortedSet<Type> sortedSet) {
            this.extraImportedTypes = sortedSet;
            return this.myself;
        }

        public T methods(List<MappingMethod> list) {
            this.methods = list;
            return this.myself;
        }
    }

    protected GeneratedType(TypeFactory typeFactory, String str, String str2, String str3, String str4, String str5, List<MappingMethod> list, List<Field> list2, Options options, VersionInformation versionInformation, Accessibility accessibility, SortedSet<Type> sortedSet, Constructor constructor) {
        this.packageName = str;
        this.name = str2;
        this.superClassName = str3;
        this.interfacePackage = str4;
        this.interfaceName = str5;
        this.extraImportedTypes = sortedSet;
        this.methods = list;
        this.fields = list2;
        this.suppressGeneratorTimestamp = options.isSuppressGeneratorTimestamp();
        this.suppressGeneratorVersionComment = options.isSuppressGeneratorVersionComment();
        this.versionInformation = versionInformation;
        this.accessibility = accessibility;
        if (versionInformation.isSourceVersionAtLeast9() && typeFactory.isTypeAvailable("javax.annotation.processing.Generated")) {
            this.generatedType = typeFactory.getType("javax.annotation.processing.Generated");
            this.generatedTypeAvailable = true;
        } else if (typeFactory.isTypeAvailable("javax.annotation.Generated")) {
            this.generatedType = typeFactory.getType("javax.annotation.Generated");
            this.generatedTypeAvailable = true;
        } else {
            this.generatedType = null;
            this.generatedTypeAvailable = false;
        }
        this.constructor = constructor;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public boolean hasPackageName() {
        return !Strings.isEmpty(this.packageName);
    }

    public String getName() {
        return this.name;
    }

    public String getSuperClassName() {
        return this.superClassName;
    }

    public String getInterfacePackage() {
        return this.interfacePackage;
    }

    public String getInterfaceName() {
        return this.interfaceName;
    }

    public List<Annotation> getAnnotations() {
        return this.annotations;
    }

    public void addAnnotation(Annotation annotation) {
        this.annotations.add(annotation);
    }

    public List<MappingMethod> getMethods() {
        return this.methods;
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public void setFields(List<Field> list) {
        this.fields = list;
    }

    public boolean isSuppressGeneratorTimestamp() {
        return this.suppressGeneratorTimestamp;
    }

    public boolean isSuppressGeneratorVersionComment() {
        return this.suppressGeneratorVersionComment;
    }

    public boolean isGeneratedTypeAvailable() {
        return this.generatedTypeAvailable;
    }

    public VersionInformation getVersionInformation() {
        return this.versionInformation;
    }

    public Accessibility getAccessibility() {
        return this.accessibility;
    }

    public void setConstructor(Constructor constructor) {
        this.constructor = constructor;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public SortedSet<Type> getImportTypes() {
        TreeSet treeSet = new TreeSet();
        addIfImportRequired(treeSet, this.generatedType);
        Iterator<MappingMethod> it = this.methods.iterator();
        while (it.hasNext()) {
            Iterator<Type> it2 = it.next().getImportTypes().iterator();
            while (it2.hasNext()) {
                addIfImportRequired(treeSet, it2.next());
            }
        }
        for (Field field : this.fields) {
            if (field.isTypeRequiresImport()) {
                Iterator<Type> it3 = field.getImportTypes().iterator();
                while (it3.hasNext()) {
                    addIfImportRequired(treeSet, it3.next());
                }
            }
        }
        Iterator<Annotation> it4 = this.annotations.iterator();
        while (it4.hasNext()) {
            addIfImportRequired(treeSet, it4.next().getType());
        }
        Iterator<Type> it5 = this.extraImportedTypes.iterator();
        while (it5.hasNext()) {
            addIfImportRequired(treeSet, it5.next());
        }
        Constructor constructor = this.constructor;
        if (constructor != null) {
            Iterator<Type> it6 = constructor.getImportTypes().iterator();
            while (it6.hasNext()) {
                addIfImportRequired(treeSet, it6.next());
            }
        }
        return treeSet;
    }

    public SortedSet<String> getImportTypeNames() {
        TreeSet treeSet = new TreeSet();
        Iterator<Type> it = getImportTypes().iterator();
        while (it.hasNext()) {
            treeSet.add(it.next().getImportName());
        }
        return treeSet;
    }

    public Constructor getConstructor() {
        return this.constructor;
    }

    public void removeConstructor() {
        this.constructor = null;
    }

    protected void addIfImportRequired(Collection<Type> collection, Type type) {
        if (type != null && needsImportDeclaration(type)) {
            collection.add(type);
        }
    }

    private boolean needsImportDeclaration(Type type) {
        if (!type.isToBeImported()) {
            return false;
        }
        if (type.getTypeMirror().getKind() != TypeKind.DECLARED && !type.isArrayType()) {
            return false;
        }
        if (type.getPackageName() == null) {
            return true;
        }
        if (type.getPackageName().equals(JAVA_LANG_PACKAGE)) {
            return false;
        }
        if (type.getPackageName().equals(this.packageName)) {
            return type.getTypeElement() != null ? type.getTypeElement().getNestingKind().isNested() : type.getComponentType() == null || type.getComponentType().getTypeElement().getNestingKind().isNested();
        }
        return true;
    }
}
