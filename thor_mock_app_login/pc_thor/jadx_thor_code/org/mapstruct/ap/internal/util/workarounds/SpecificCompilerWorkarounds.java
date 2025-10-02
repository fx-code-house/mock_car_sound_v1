package org.mapstruct.ap.internal.util.workarounds;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.version.VersionInformation;

/* loaded from: classes3.dex */
public class SpecificCompilerWorkarounds {
    private SpecificCompilerWorkarounds() {
    }

    static boolean isAssignable(Types types, TypeMirror typeMirror, TypeMirror typeMirror2) {
        if (typeMirror.getKind() == TypeKind.VOID) {
            return false;
        }
        return types.isAssignable(typeMirror, typeMirror2);
    }

    static boolean isSubtype(Types types, TypeMirror typeMirror, TypeMirror typeMirror2) {
        if (typeMirror.getKind() == TypeKind.VOID) {
            return false;
        }
        return types.isSubtype(erasure(types, typeMirror), erasure(types, typeMirror2));
    }

    static TypeMirror erasure(Types types, TypeMirror typeMirror) {
        return (typeMirror.getKind() == TypeKind.VOID || typeMirror.getKind() == TypeKind.NULL) ? typeMirror : types.erasure(typeMirror);
    }

    public static TypeElement replaceTypeElementIfNecessary(Elements elements, TypeElement typeElement) {
        TypeElement typeElement2;
        return (!typeElement.getEnclosedElements().isEmpty() || (typeElement2 = elements.getTypeElement(typeElement.getQualifiedName())) == null || typeElement2.getEnclosedElements().isEmpty()) ? typeElement : typeElement2;
    }

    static TypeMirror asMemberOf(Types types, ProcessingEnvironment processingEnvironment, VersionInformation versionInformation, DeclaredType declaredType, Element element) throws Exception {
        TypeMirror typeMirrorInvokeAsMemberOfWorkaround = null;
        try {
            try {
                typeMirrorInvokeAsMemberOfWorkaround = types.asMemberOf(declaredType, element);
                e = null;
            } catch (IllegalArgumentException e) {
                e = e;
                if (versionInformation.isEclipseJDTCompiler()) {
                    typeMirrorInvokeAsMemberOfWorkaround = EclipseClassLoaderBridge.invokeAsMemberOfWorkaround(processingEnvironment, declaredType, element);
                }
            }
        } catch (Exception e2) {
            e = e2;
        }
        if (typeMirrorInvokeAsMemberOfWorkaround != null) {
            return typeMirrorInvokeAsMemberOfWorkaround;
        }
        throw new RuntimeException("Fallback implementation of asMemberOf didn't work for " + element + " in " + declaredType, e);
    }
}
