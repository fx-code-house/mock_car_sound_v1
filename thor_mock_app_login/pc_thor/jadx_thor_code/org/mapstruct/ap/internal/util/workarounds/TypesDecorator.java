package org.mapstruct.ap.internal.util.workarounds;

import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.version.VersionInformation;

/* loaded from: classes3.dex */
public class TypesDecorator implements Types {
    private final Types delegate;
    private final ProcessingEnvironment processingEnv;
    private final VersionInformation versionInformation;

    public TypesDecorator(ProcessingEnvironment processingEnvironment, VersionInformation versionInformation) {
        this.delegate = processingEnvironment.getTypeUtils();
        this.processingEnv = processingEnvironment;
        this.versionInformation = versionInformation;
    }

    public Element asElement(TypeMirror typeMirror) {
        return this.delegate.asElement(typeMirror);
    }

    public boolean isSameType(TypeMirror typeMirror, TypeMirror typeMirror2) {
        return this.delegate.isSameType(typeMirror, typeMirror2);
    }

    public boolean isSubtype(TypeMirror typeMirror, TypeMirror typeMirror2) {
        return SpecificCompilerWorkarounds.isSubtype(this.delegate, typeMirror, typeMirror2);
    }

    public boolean isAssignable(TypeMirror typeMirror, TypeMirror typeMirror2) {
        return SpecificCompilerWorkarounds.isAssignable(this.delegate, typeMirror, typeMirror2);
    }

    public boolean contains(TypeMirror typeMirror, TypeMirror typeMirror2) {
        return this.delegate.contains(typeMirror, typeMirror2);
    }

    public boolean isSubsignature(ExecutableType executableType, ExecutableType executableType2) {
        return this.delegate.isSubsignature(executableType, executableType2);
    }

    public List<? extends TypeMirror> directSupertypes(TypeMirror typeMirror) {
        return this.delegate.directSupertypes(typeMirror);
    }

    public TypeMirror erasure(TypeMirror typeMirror) {
        return SpecificCompilerWorkarounds.erasure(this.delegate, typeMirror);
    }

    public TypeElement boxedClass(PrimitiveType primitiveType) {
        return this.delegate.boxedClass(primitiveType);
    }

    public PrimitiveType unboxedType(TypeMirror typeMirror) {
        return this.delegate.unboxedType(typeMirror);
    }

    public TypeMirror capture(TypeMirror typeMirror) {
        return this.delegate.capture(typeMirror);
    }

    public PrimitiveType getPrimitiveType(TypeKind typeKind) {
        return this.delegate.getPrimitiveType(typeKind);
    }

    public NullType getNullType() {
        return this.delegate.getNullType();
    }

    public NoType getNoType(TypeKind typeKind) {
        return this.delegate.getNoType(typeKind);
    }

    public ArrayType getArrayType(TypeMirror typeMirror) {
        return this.delegate.getArrayType(typeMirror);
    }

    public WildcardType getWildcardType(TypeMirror typeMirror, TypeMirror typeMirror2) {
        return this.delegate.getWildcardType(typeMirror, typeMirror2);
    }

    public DeclaredType getDeclaredType(TypeElement typeElement, TypeMirror... typeMirrorArr) {
        return this.delegate.getDeclaredType(typeElement, typeMirrorArr);
    }

    public DeclaredType getDeclaredType(DeclaredType declaredType, TypeElement typeElement, TypeMirror... typeMirrorArr) {
        return this.delegate.getDeclaredType(declaredType, typeElement, typeMirrorArr);
    }

    public TypeMirror asMemberOf(DeclaredType declaredType, Element element) {
        return SpecificCompilerWorkarounds.asMemberOf(this.delegate, this.processingEnv, this.versionInformation, declaredType, element);
    }
}
