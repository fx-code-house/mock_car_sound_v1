package org.mapstruct.ap.spi;

import java.util.regex.Pattern;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.SimpleTypeVisitor6;
import javax.lang.model.util.Types;
import org.mapstruct.ap.spi.util.IntrospectorUtils;

/* loaded from: classes3.dex */
public class DefaultAccessorNamingStrategy implements AccessorNamingStrategy {
    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile("^javax?\\..*");
    protected Elements elementUtils;
    protected Types typeUtils;

    @Override // org.mapstruct.ap.spi.AccessorNamingStrategy
    public void init(MapStructProcessingEnvironment mapStructProcessingEnvironment) {
        this.elementUtils = mapStructProcessingEnvironment.getElementUtils();
        this.typeUtils = mapStructProcessingEnvironment.getTypeUtils();
    }

    @Override // org.mapstruct.ap.spi.AccessorNamingStrategy
    public MethodType getMethodType(ExecutableElement executableElement) {
        if (isGetterMethod(executableElement)) {
            return MethodType.GETTER;
        }
        if (isSetterMethod(executableElement)) {
            return MethodType.SETTER;
        }
        if (isAdderMethod(executableElement)) {
            return MethodType.ADDER;
        }
        if (isPresenceCheckMethod(executableElement)) {
            return MethodType.PRESENCE_CHECKER;
        }
        return MethodType.OTHER;
    }

    public boolean isGetterMethod(ExecutableElement executableElement) {
        if (!executableElement.getParameters().isEmpty()) {
            return false;
        }
        String string = executableElement.getSimpleName().toString();
        return (string.startsWith("get") && string.length() > 3 && executableElement.getReturnType().getKind() != TypeKind.VOID) || ((string.startsWith("is") && string.length() > 2) && (executableElement.getReturnType().getKind() == TypeKind.BOOLEAN || "java.lang.Boolean".equals(getQualifiedName(executableElement.getReturnType()))));
    }

    public boolean isSetterMethod(ExecutableElement executableElement) {
        String string = executableElement.getSimpleName().toString();
        return (string.startsWith("set") && string.length() > 3) || isFluentSetter(executableElement);
    }

    protected boolean isFluentSetter(ExecutableElement executableElement) {
        return executableElement.getParameters().size() == 1 && !JAVA_JAVAX_PACKAGE.matcher(executableElement.getEnclosingElement().asType().toString()).matches() && !isAdderWithUpperCase4thCharacter(executableElement) && this.typeUtils.isAssignable(executableElement.getReturnType(), executableElement.getEnclosingElement().asType());
    }

    private boolean isAdderWithUpperCase4thCharacter(ExecutableElement executableElement) {
        return isAdderMethod(executableElement) && Character.isUpperCase(executableElement.getSimpleName().toString().charAt(3));
    }

    public boolean isAdderMethod(ExecutableElement executableElement) {
        String string = executableElement.getSimpleName().toString();
        return string.startsWith("add") && string.length() > 3;
    }

    public boolean isPresenceCheckMethod(ExecutableElement executableElement) {
        String string = executableElement.getSimpleName().toString();
        return string.startsWith("has") && string.length() > 3;
    }

    @Override // org.mapstruct.ap.spi.AccessorNamingStrategy
    public String getPropertyName(ExecutableElement executableElement) {
        String string = executableElement.getSimpleName().toString();
        if (isFluentSetter(executableElement)) {
            return (string.startsWith("set") && string.length() > 3 && Character.isUpperCase(string.charAt(3))) ? IntrospectorUtils.decapitalize(string.substring(3)) : string;
        }
        return IntrospectorUtils.decapitalize(string.substring(string.startsWith("is") ? 2 : 3));
    }

    @Override // org.mapstruct.ap.spi.AccessorNamingStrategy
    public String getElementName(ExecutableElement executableElement) {
        return IntrospectorUtils.decapitalize(executableElement.getSimpleName().toString().substring(3));
    }

    protected static String getQualifiedName(TypeMirror typeMirror) {
        TypeElement typeElement;
        DeclaredType declaredType = (DeclaredType) typeMirror.accept(new SimpleTypeVisitor6<DeclaredType, Void>() { // from class: org.mapstruct.ap.spi.DefaultAccessorNamingStrategy.1
            public DeclaredType visitDeclared(DeclaredType declaredType2, Void r2) {
                return declaredType2;
            }
        }, (Object) null);
        if (declaredType == null || (typeElement = (TypeElement) declaredType.asElement().accept(new SimpleElementVisitor6<TypeElement, Void>() { // from class: org.mapstruct.ap.spi.DefaultAccessorNamingStrategy.2
            public TypeElement visitType(TypeElement typeElement2, Void r2) {
                return typeElement2;
            }
        }, (Object) null)) == null) {
            return null;
        }
        return typeElement.getQualifiedName().toString();
    }

    @Override // org.mapstruct.ap.spi.AccessorNamingStrategy
    public String getCollectionGetterName(String str) {
        throw new IllegalStateException("This method is not intended to be called anymore and will be removed in future versions.");
    }
}
