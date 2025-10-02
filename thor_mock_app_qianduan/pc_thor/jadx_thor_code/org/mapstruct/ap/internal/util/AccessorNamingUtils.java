package org.mapstruct.ap.internal.util;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.SimpleTypeVisitor6;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.AccessorType;
import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.MethodType;

/* loaded from: classes3.dex */
public final class AccessorNamingUtils {
    private final AccessorNamingStrategy accessorNamingStrategy;

    public AccessorNamingUtils(AccessorNamingStrategy accessorNamingStrategy) {
        this.accessorNamingStrategy = accessorNamingStrategy;
    }

    public boolean isGetterMethod(ExecutableElement executableElement) {
        return executableElement != null && Executables.isPublicNotStatic(executableElement) && executableElement.getParameters().isEmpty() && this.accessorNamingStrategy.getMethodType(executableElement) == MethodType.GETTER;
    }

    public boolean isPresenceCheckMethod(ExecutableElement executableElement) {
        return executableElement != null && Executables.isPublicNotStatic(executableElement) && executableElement.getParameters().isEmpty() && (executableElement.getReturnType().getKind() == TypeKind.BOOLEAN || "java.lang.Boolean".equals(getQualifiedName(executableElement.getReturnType()))) && this.accessorNamingStrategy.getMethodType(executableElement) == MethodType.PRESENCE_CHECKER;
    }

    public boolean isSetterMethod(ExecutableElement executableElement) {
        return executableElement != null && Executables.isPublicNotStatic(executableElement) && executableElement.getParameters().size() == 1 && this.accessorNamingStrategy.getMethodType(executableElement) == MethodType.SETTER;
    }

    public boolean isAdderMethod(ExecutableElement executableElement) {
        return executableElement != null && Executables.isPublicNotStatic(executableElement) && executableElement.getParameters().size() == 1 && this.accessorNamingStrategy.getMethodType(executableElement) == MethodType.ADDER;
    }

    public String getPropertyName(ExecutableElement executableElement) {
        return this.accessorNamingStrategy.getPropertyName(executableElement);
    }

    public String getElementNameForAdder(Accessor accessor) {
        if (accessor.getAccessorType() == AccessorType.ADDER) {
            return this.accessorNamingStrategy.getElementName((ExecutableElement) accessor.getElement());
        }
        return null;
    }

    private static String getQualifiedName(TypeMirror typeMirror) {
        TypeElement typeElement;
        DeclaredType declaredType = (DeclaredType) typeMirror.accept(new SimpleTypeVisitor6<DeclaredType, Void>() { // from class: org.mapstruct.ap.internal.util.AccessorNamingUtils.1
            public DeclaredType visitDeclared(DeclaredType declaredType2, Void r2) {
                return declaredType2;
            }
        }, (Object) null);
        if (declaredType == null || (typeElement = (TypeElement) declaredType.asElement().accept(new SimpleElementVisitor6<TypeElement, Void>() { // from class: org.mapstruct.ap.internal.util.AccessorNamingUtils.2
            public TypeElement visitType(TypeElement typeElement2, Void r2) {
                return typeElement2;
            }
        }, (Object) null)) == null) {
            return null;
        }
        return typeElement.getQualifiedName().toString();
    }
}
