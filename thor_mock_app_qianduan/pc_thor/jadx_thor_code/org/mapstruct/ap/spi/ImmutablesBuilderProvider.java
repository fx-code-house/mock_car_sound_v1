package org.mapstruct.ap.spi;

import java.util.Iterator;
import java.util.regex.Pattern;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/* loaded from: classes3.dex */
public class ImmutablesBuilderProvider extends DefaultBuilderProvider {
    private static final String IMMUTABLE_FQN = "org.immutables.value.Value.Immutable";
    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile("^javax?\\..*");

    @Override // org.mapstruct.ap.spi.DefaultBuilderProvider
    protected BuilderInfo findBuilderInfo(TypeElement typeElement) {
        BuilderInfo builderInfoFindBuilderInfoForImmutables;
        CharSequence qualifiedName = typeElement.getQualifiedName();
        if (qualifiedName.length() == 0 || JAVA_JAVAX_PACKAGE.matcher(qualifiedName).matches()) {
            return null;
        }
        TypeElement typeElement2 = this.elementUtils.getTypeElement("org.immutables.value.Value.Immutable");
        return (typeElement2 == null || (builderInfoFindBuilderInfoForImmutables = findBuilderInfoForImmutables(typeElement, typeElement2)) == null) ? super.findBuilderInfo(typeElement) : builderInfoFindBuilderInfoForImmutables;
    }

    protected BuilderInfo findBuilderInfoForImmutables(TypeElement typeElement, TypeElement typeElement2) {
        Iterator it = this.elementUtils.getAllAnnotationMirrors(typeElement).iterator();
        while (it.hasNext()) {
            if (this.typeUtils.isSameType(((AnnotationMirror) it.next()).getAnnotationType(), typeElement2.asType())) {
                TypeElement typeElementAsImmutableElement = asImmutableElement(typeElement);
                if (typeElementAsImmutableElement != null) {
                    return super.findBuilderInfo(typeElementAsImmutableElement);
                }
                throw new TypeHierarchyErroneousException(typeElement);
            }
        }
        return null;
    }

    protected TypeElement asImmutableElement(TypeElement typeElement) {
        PackageElement enclosingElement = typeElement.getEnclosingElement();
        StringBuilder sb = new StringBuilder(typeElement.getQualifiedName().length() + 17);
        if (enclosingElement.getKind() == ElementKind.PACKAGE) {
            sb.append(enclosingElement.getQualifiedName().toString());
        } else {
            sb.append(((TypeElement) enclosingElement).getQualifiedName().toString());
        }
        if (sb.length() > 0) {
            sb.append(".");
        }
        sb.append("Immutable").append((CharSequence) typeElement.getSimpleName());
        return this.elementUtils.getTypeElement(sb);
    }
}
