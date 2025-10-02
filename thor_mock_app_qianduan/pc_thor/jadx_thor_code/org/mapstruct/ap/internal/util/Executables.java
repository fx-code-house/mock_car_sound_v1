package org.mapstruct.ap.internal.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import org.mapstruct.ap.internal.gem.AfterMappingGem;
import org.mapstruct.ap.internal.gem.BeforeMappingGem;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.workarounds.SpecificCompilerWorkarounds;
import org.mapstruct.ap.spi.TypeHierarchyErroneousException;

/* loaded from: classes3.dex */
public class Executables {
    private static final Method DEFAULT_METHOD;

    static {
        Method method;
        try {
            method = ExecutableElement.class.getMethod("isDefault", new Class[0]);
        } catch (NoSuchMethodException unused) {
            method = null;
        }
        DEFAULT_METHOD = method;
    }

    private Executables() {
    }

    static boolean isPublicNotStatic(ExecutableElement executableElement) {
        return isPublic(executableElement) && isNotStatic(executableElement);
    }

    static boolean isPublic(ExecutableElement executableElement) {
        return executableElement.getModifiers().contains(Modifier.PUBLIC);
    }

    private static boolean isNotStatic(ExecutableElement executableElement) {
        return !executableElement.getModifiers().contains(Modifier.STATIC);
    }

    public static boolean isFinal(Accessor accessor) {
        return accessor != null && accessor.getModifiers().contains(Modifier.FINAL);
    }

    public static boolean isDefaultMethod(ExecutableElement executableElement) {
        try {
            Method method = DEFAULT_METHOD;
            if (method != null) {
                return Boolean.TRUE.equals(method.invoke(executableElement, new Object[0]));
            }
            return false;
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    private static TypeElement asTypeElement(TypeMirror typeMirror) {
        return ((DeclaredType) typeMirror).asElement();
    }

    public static List<ExecutableElement> getAllEnclosedExecutableElements(Elements elements, TypeElement typeElement) {
        ArrayList arrayList = new ArrayList();
        TypeElement typeElementReplaceTypeElementIfNecessary = SpecificCompilerWorkarounds.replaceTypeElementIfNecessary(elements, typeElement);
        addEnclosedElementsInHierarchy(elements, arrayList, typeElementReplaceTypeElementIfNecessary, typeElementReplaceTypeElementIfNecessary);
        return arrayList;
    }

    private static void addEnclosedElementsInHierarchy(Elements elements, List<ExecutableElement> list, TypeElement typeElement, TypeElement typeElement2) {
        if (typeElement != typeElement2) {
            typeElement = SpecificCompilerWorkarounds.replaceTypeElementIfNecessary(elements, typeElement);
        }
        if (typeElement.asType().getKind() == TypeKind.ERROR) {
            throw new TypeHierarchyErroneousException(typeElement);
        }
        addNotYetOverridden(elements, list, ElementFilter.methodsIn(typeElement.getEnclosedElements()), typeElement2);
        if (hasNonObjectSuperclass(typeElement)) {
            addEnclosedElementsInHierarchy(elements, list, asTypeElement(typeElement.getSuperclass()), typeElement2);
        }
        Iterator it = typeElement.getInterfaces().iterator();
        while (it.hasNext()) {
            addEnclosedElementsInHierarchy(elements, list, asTypeElement((TypeMirror) it.next()), typeElement2);
        }
    }

    private static void addNotYetOverridden(Elements elements, List<ExecutableElement> list, List<ExecutableElement> list2, TypeElement typeElement) {
        ArrayList arrayList = new ArrayList(list2.size());
        for (ExecutableElement executableElement : list2) {
            if (isNotPrivate(executableElement) && isNotObjectEquals(executableElement) && wasNotYetOverridden(elements, list, executableElement, typeElement)) {
                arrayList.add(executableElement);
            }
        }
        list.addAll(0, arrayList);
    }

    private static boolean isNotObjectEquals(ExecutableElement executableElement) {
        return (executableElement.getSimpleName().contentEquals("equals") && executableElement.getParameters().size() == 1 && asTypeElement(((VariableElement) executableElement.getParameters().get(0)).asType()).getQualifiedName().contentEquals("java.lang.Object")) ? false : true;
    }

    private static boolean isNotPrivate(ExecutableElement executableElement) {
        return !executableElement.getModifiers().contains(Modifier.PRIVATE);
    }

    private static boolean wasNotYetOverridden(Elements elements, List<ExecutableElement> list, ExecutableElement executableElement, TypeElement typeElement) {
        ListIterator<ExecutableElement> listIterator = list.listIterator();
        while (true) {
            if (!listIterator.hasNext()) {
                break;
            }
            ExecutableElement next = listIterator.next();
            if (next != null) {
                if (elements.overrides(next, executableElement, typeElement)) {
                    return false;
                }
                if (elements.overrides(executableElement, next, typeElement)) {
                    listIterator.remove();
                    break;
                }
            }
        }
        return true;
    }

    private static boolean hasNonObjectSuperclass(TypeElement typeElement) {
        if (typeElement.getSuperclass().getKind() != TypeKind.ERROR) {
            return typeElement.getSuperclass().getKind() == TypeKind.DECLARED && !asTypeElement(typeElement.getSuperclass()).getQualifiedName().toString().equals("java.lang.Object");
        }
        throw new TypeHierarchyErroneousException(typeElement);
    }

    public static boolean isLifecycleCallbackMethod(ExecutableElement executableElement) {
        return isBeforeMappingMethod(executableElement) || isAfterMappingMethod(executableElement);
    }

    public static boolean isAfterMappingMethod(ExecutableElement executableElement) {
        return AfterMappingGem.instanceOn((Element) executableElement) != null;
    }

    public static boolean isBeforeMappingMethod(ExecutableElement executableElement) {
        return BeforeMappingGem.instanceOn((Element) executableElement) != null;
    }
}
