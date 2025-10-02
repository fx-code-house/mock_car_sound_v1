package org.mapstruct.ap.internal.util;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import org.mapstruct.ap.internal.util.workarounds.SpecificCompilerWorkarounds;
import org.mapstruct.ap.spi.TypeHierarchyErroneousException;

/* loaded from: classes3.dex */
public class Fields {
    private Fields() {
    }

    public static boolean isFieldAccessor(VariableElement variableElement) {
        return isPublic(variableElement) && isNotStatic(variableElement);
    }

    static boolean isPublic(VariableElement variableElement) {
        return variableElement.getModifiers().contains(Modifier.PUBLIC);
    }

    private static boolean isNotStatic(VariableElement variableElement) {
        return !variableElement.getModifiers().contains(Modifier.STATIC);
    }

    public static List<VariableElement> getAllEnclosedFields(Elements elements, TypeElement typeElement) {
        ArrayList arrayList = new ArrayList();
        TypeElement typeElementReplaceTypeElementIfNecessary = SpecificCompilerWorkarounds.replaceTypeElementIfNecessary(elements, typeElement);
        addEnclosedElementsInHierarchy(elements, arrayList, typeElementReplaceTypeElementIfNecessary, typeElementReplaceTypeElementIfNecessary);
        return arrayList;
    }

    private static void addEnclosedElementsInHierarchy(Elements elements, List<VariableElement> list, TypeElement typeElement, TypeElement typeElement2) {
        if (typeElement != typeElement2) {
            typeElement = SpecificCompilerWorkarounds.replaceTypeElementIfNecessary(elements, typeElement);
        }
        if (typeElement.asType().getKind() == TypeKind.ERROR) {
            throw new TypeHierarchyErroneousException(typeElement);
        }
        addFields(list, ElementFilter.fieldsIn(typeElement.getEnclosedElements()));
        if (hasNonObjectSuperclass(typeElement)) {
            addEnclosedElementsInHierarchy(elements, list, asTypeElement(typeElement.getSuperclass()), typeElement2);
        }
    }

    private static void addFields(List<VariableElement> list, List<VariableElement> list2) {
        ArrayList arrayList = new ArrayList(list2.size());
        arrayList.addAll(list2);
        list.addAll(0, arrayList);
    }

    private static TypeElement asTypeElement(TypeMirror typeMirror) {
        return ((DeclaredType) typeMirror).asElement();
    }

    private static boolean hasNonObjectSuperclass(TypeElement typeElement) {
        if (typeElement.getSuperclass().getKind() != TypeKind.ERROR) {
            return typeElement.getSuperclass().getKind() == TypeKind.DECLARED && !asTypeElement(typeElement.getSuperclass()).getQualifiedName().toString().equals("java.lang.Object");
        }
        throw new TypeHierarchyErroneousException(typeElement);
    }
}
