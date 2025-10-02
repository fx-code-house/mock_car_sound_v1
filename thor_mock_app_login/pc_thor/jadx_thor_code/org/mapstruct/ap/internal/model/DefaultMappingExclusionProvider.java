package org.mapstruct.ap.internal.model;

import java.util.regex.Pattern;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import org.mapstruct.ap.spi.MappingExclusionProvider;

/* loaded from: classes3.dex */
class DefaultMappingExclusionProvider implements MappingExclusionProvider {
    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile("^javax?\\..*");

    DefaultMappingExclusionProvider() {
    }

    @Override // org.mapstruct.ap.spi.MappingExclusionProvider
    public boolean isExcluded(TypeElement typeElement) {
        Name qualifiedName = typeElement.getQualifiedName();
        return qualifiedName.length() != 0 && isFullyQualifiedNameExcluded(qualifiedName);
    }

    protected boolean isFullyQualifiedNameExcluded(Name name) {
        return JAVA_JAVAX_PACKAGE.matcher(name).matches();
    }
}
