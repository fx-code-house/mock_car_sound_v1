package org.mapstruct.ap.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.SimpleTypeVisitor6;
import javax.lang.model.util.Types;
import org.mapstruct.ap.spi.BuilderInfo;

/* loaded from: classes3.dex */
public class DefaultBuilderProvider implements BuilderProvider {
    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile("^javax?\\..*");
    protected Elements elementUtils;
    protected Types typeUtils;

    @Override // org.mapstruct.ap.spi.BuilderProvider
    public void init(MapStructProcessingEnvironment mapStructProcessingEnvironment) {
        this.elementUtils = mapStructProcessingEnvironment.getElementUtils();
        this.typeUtils = mapStructProcessingEnvironment.getTypeUtils();
    }

    @Override // org.mapstruct.ap.spi.BuilderProvider
    public BuilderInfo findBuilderInfo(TypeMirror typeMirror) {
        TypeElement typeElement = getTypeElement(typeMirror);
        if (typeElement == null) {
            return null;
        }
        return findBuilderInfo(typeElement);
    }

    protected TypeElement getTypeElement(TypeMirror typeMirror) {
        if (typeMirror.getKind() == TypeKind.ERROR) {
            throw new TypeHierarchyErroneousException(typeMirror);
        }
        DeclaredType declaredType = (DeclaredType) typeMirror.accept(new SimpleTypeVisitor6<DeclaredType, Void>() { // from class: org.mapstruct.ap.spi.DefaultBuilderProvider.1
            public DeclaredType visitDeclared(DeclaredType declaredType2, Void r2) {
                return declaredType2;
            }
        }, (Object) null);
        if (declaredType == null) {
            return null;
        }
        return (TypeElement) declaredType.asElement().accept(new SimpleElementVisitor6<TypeElement, Void>() { // from class: org.mapstruct.ap.spi.DefaultBuilderProvider.2
            public TypeElement visitType(TypeElement typeElement, Void r2) {
                return typeElement;
            }
        }, (Object) null);
    }

    protected BuilderInfo findBuilderInfo(TypeElement typeElement) {
        if (shouldIgnore(typeElement)) {
            return null;
        }
        List<ExecutableElement> listMethodsIn = ElementFilter.methodsIn(typeElement.getEnclosedElements());
        ArrayList arrayList = new ArrayList();
        for (ExecutableElement executableElement : listMethodsIn) {
            if (isPossibleBuilderCreationMethod(executableElement, typeElement)) {
                Collection<ExecutableElement> collectionFindBuildMethods = findBuildMethods(getTypeElement(executableElement.getReturnType()), typeElement);
                if (!collectionFindBuildMethods.isEmpty()) {
                    arrayList.add(new BuilderInfo.Builder().builderCreationMethod(executableElement).buildMethod(collectionFindBuildMethods).build());
                }
            }
        }
        if (arrayList.size() == 1) {
            return (BuilderInfo) arrayList.get(0);
        }
        if (arrayList.size() > 1) {
            throw new MoreThanOneBuilderCreationMethodException(typeElement.asType(), arrayList);
        }
        return findBuilderInfo(typeElement.getSuperclass());
    }

    protected boolean isPossibleBuilderCreationMethod(ExecutableElement executableElement, TypeElement typeElement) {
        if (executableElement.getParameters().isEmpty() && executableElement.getModifiers().contains(Modifier.PUBLIC) && executableElement.getModifiers().contains(Modifier.STATIC) && executableElement.getReturnType().getKind() != TypeKind.VOID) {
            Types types = this.typeUtils;
            if (!types.isSameType(types.erasure(executableElement.getReturnType()), this.typeUtils.erasure(typeElement.asType()))) {
                return true;
            }
        }
        return false;
    }

    protected Collection<ExecutableElement> findBuildMethods(TypeElement typeElement, TypeElement typeElement2) {
        if (shouldIgnore(typeElement)) {
            return Collections.emptyList();
        }
        List<ExecutableElement> listMethodsIn = ElementFilter.methodsIn(typeElement.getEnclosedElements());
        ArrayList arrayList = new ArrayList();
        for (ExecutableElement executableElement : listMethodsIn) {
            if (isBuildMethod(executableElement, typeElement2)) {
                arrayList.add(executableElement);
            }
        }
        return arrayList.isEmpty() ? findBuildMethods(getTypeElement(typeElement.getSuperclass()), typeElement2) : arrayList;
    }

    protected boolean isBuildMethod(ExecutableElement executableElement, TypeElement typeElement) {
        return executableElement.getParameters().isEmpty() && executableElement.getModifiers().contains(Modifier.PUBLIC) && this.typeUtils.isAssignable(executableElement.getReturnType(), typeElement.asType());
    }

    protected boolean shouldIgnore(TypeElement typeElement) {
        return typeElement == null || JAVA_JAVAX_PACKAGE.matcher(typeElement.getQualifiedName()).matches();
    }
}
