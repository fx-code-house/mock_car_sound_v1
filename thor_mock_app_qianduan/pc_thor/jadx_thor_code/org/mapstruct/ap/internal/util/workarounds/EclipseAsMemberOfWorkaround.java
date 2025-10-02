package org.mapstruct.ap.internal.util.workarounds;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.apt.model.ElementImpl;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* loaded from: classes3.dex */
final class EclipseAsMemberOfWorkaround {
    private EclipseAsMemberOfWorkaround() {
    }

    static TypeMirror asMemberOf(ProcessingEnvironment processingEnvironment, DeclaredType declaredType, Element element) {
        ElementImpl elementImpl = (ElementImpl) tryCast(element, ElementImpl.class);
        BaseProcessingEnvImpl baseProcessingEnvImpl = (BaseProcessingEnvImpl) tryCast(processingEnvironment, BaseProcessingEnvImpl.class);
        if (elementImpl != null && baseProcessingEnvImpl != null) {
            ReferenceBinding referenceBinding = processingEnvironment.getTypeUtils().asElement(declaredType)._binding;
            MethodBinding methodBinding = elementImpl._binding;
            MethodBinding methodBindingFindInSuperclassHierarchy = findInSuperclassHierarchy(methodBinding, referenceBinding);
            if (methodBindingFindInSuperclassHierarchy != null) {
                return baseProcessingEnvImpl.getFactory().newTypeMirror(methodBindingFindInSuperclassHierarchy);
            }
            ArrayList arrayList = new ArrayList();
            collectFromInterfaces(methodBinding, referenceBinding, new HashSet(), arrayList);
            arrayList.sort(MostSpecificMethodBindingComparator.INSTANCE);
            if (!arrayList.isEmpty()) {
                return baseProcessingEnvImpl.getFactory().newTypeMirror((Binding) arrayList.get(0));
            }
        }
        return null;
    }

    private static <T> T tryCast(Object obj, Class<T> cls) {
        if (cls.isInstance(obj)) {
            return cls.cast(obj);
        }
        return null;
    }

    private static void collectFromInterfaces(MethodBinding methodBinding, ReferenceBinding referenceBinding, Set<ReferenceBinding> set, List<MethodBinding> list) {
        if (referenceBinding == null) {
            return;
        }
        collectFromInterfaces(methodBinding, referenceBinding.superclass(), set, list);
        for (ReferenceBinding referenceBinding2 : referenceBinding.superInterfaces()) {
            if (!set.contains(referenceBinding2)) {
                set.add(referenceBinding2);
                MethodBinding methodBindingFindMatchingMethodBinding = findMatchingMethodBinding(methodBinding, referenceBinding2.methods());
                if (methodBindingFindMatchingMethodBinding == null) {
                    collectFromInterfaces(methodBinding, referenceBinding2, set, list);
                } else {
                    list.add(methodBindingFindMatchingMethodBinding);
                }
            }
        }
    }

    private static MethodBinding findMatchingMethodBinding(MethodBinding methodBinding, MethodBinding[] methodBindingArr) {
        for (MethodBinding methodBinding2 : methodBindingArr) {
            if (CharOperation.equals(methodBinding2.selector, methodBinding.selector) && (methodBinding2.original() == methodBinding || methodBinding2.areParameterErasuresEqual(methodBinding))) {
                return methodBinding2;
            }
        }
        return null;
    }

    private static MethodBinding findInSuperclassHierarchy(MethodBinding methodBinding, ReferenceBinding referenceBinding) {
        while (referenceBinding != null) {
            MethodBinding methodBindingFindMatchingMethodBinding = findMatchingMethodBinding(methodBinding, referenceBinding.methods());
            if (methodBindingFindMatchingMethodBinding != null) {
                return methodBindingFindMatchingMethodBinding;
            }
            referenceBinding = referenceBinding.superclass();
        }
        return null;
    }

    private static final class MostSpecificMethodBindingComparator implements Comparator<MethodBinding> {
        private static final MostSpecificMethodBindingComparator INSTANCE = new MostSpecificMethodBindingComparator();

        private MostSpecificMethodBindingComparator() {
        }

        @Override // java.util.Comparator
        public int compare(MethodBinding methodBinding, MethodBinding methodBinding2) {
            boolean zAreParametersCompatibleWith = methodBinding.areParametersCompatibleWith(methodBinding2.parameters);
            if (zAreParametersCompatibleWith != methodBinding2.areParametersCompatibleWith(methodBinding.parameters)) {
                return zAreParametersCompatibleWith ? 1 : -1;
            }
            if (TypeBinding.equalsEquals(methodBinding.returnType, methodBinding2.returnType)) {
                return 0;
            }
            return methodBinding2.returnType.isCompatibleWith(methodBinding.returnType) ? 1 : -1;
        }
    }
}
