package org.mapstruct.ap.internal.model.source;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import org.mapstruct.ap.internal.gem.MappingControlGem;
import org.mapstruct.ap.internal.gem.MappingControlUseGem;
import org.mapstruct.ap.internal.gem.MappingControlsGem;

/* loaded from: classes3.dex */
public class MappingControl {
    private static final String JAVA_LANG_ANNOTATION_PGK = "java.lang.annotation";
    private static final String MAPPING_CONTROLS_FQN = "org.mapstruct.control.MappingControls";
    private static final String MAPPING_CONTROL_FQN = "org.mapstruct.control.MappingControl";
    private static final String ORG_MAPSTRUCT_PKG = "org.mapstruct";
    private boolean allowDirect = false;
    private boolean allowTypeConversion = false;
    private boolean allowMappingMethod = false;
    private boolean allow2Steps = false;

    public static MappingControl fromTypeMirror(TypeMirror typeMirror, Elements elements) {
        MappingControl mappingControl = new MappingControl();
        if (TypeKind.DECLARED == typeMirror.getKind()) {
            resolveControls(mappingControl, ((DeclaredType) typeMirror).asElement(), new HashSet(), elements);
        }
        return mappingControl;
    }

    private MappingControl() {
    }

    public boolean allowDirect() {
        return this.allowDirect;
    }

    public boolean allowTypeConversion() {
        return this.allowTypeConversion;
    }

    public boolean allowMappingMethod() {
        return this.allowMappingMethod;
    }

    public boolean allowBy2Steps() {
        return this.allow2Steps;
    }

    private static void resolveControls(final MappingControl mappingControl, Element element, Set<Element> set, Elements elements) {
        Iterator it = element.getAnnotationMirrors().iterator();
        while (it.hasNext()) {
            Element elementAsElement = ((AnnotationMirror) it.next()).getAnnotationType().asElement();
            if (isAnnotation(elementAsElement, MAPPING_CONTROL_FQN)) {
                determineMappingControl(mappingControl, MappingControlGem.instanceOn(element));
            } else if (isAnnotation(elementAsElement, MAPPING_CONTROLS_FQN)) {
                MappingControlsGem.instanceOn(element).value().get().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.model.source.MappingControl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        MappingControl.determineMappingControl(this.f$0, (MappingControlGem) obj);
                    }
                });
            } else if (!isAnnotationInPackage(elementAsElement, JAVA_LANG_ANNOTATION_PGK, elements) && !isAnnotationInPackage(elementAsElement, ORG_MAPSTRUCT_PKG, elements) && !set.contains(elementAsElement)) {
                set.add(elementAsElement);
                resolveControls(mappingControl, elementAsElement, set, elements);
            }
        }
    }

    /* renamed from: org.mapstruct.ap.internal.model.source.MappingControl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$mapstruct$ap$internal$gem$MappingControlUseGem;

        static {
            int[] iArr = new int[MappingControlUseGem.values().length];
            $SwitchMap$org$mapstruct$ap$internal$gem$MappingControlUseGem = iArr;
            try {
                iArr[MappingControlUseGem.DIRECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$mapstruct$ap$internal$gem$MappingControlUseGem[MappingControlUseGem.MAPPING_METHOD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$mapstruct$ap$internal$gem$MappingControlUseGem[MappingControlUseGem.BUILT_IN_CONVERSION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$mapstruct$ap$internal$gem$MappingControlUseGem[MappingControlUseGem.COMPLEX_MAPPING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void determineMappingControl(MappingControl mappingControl, MappingControlGem mappingControlGem) {
        int i = AnonymousClass1.$SwitchMap$org$mapstruct$ap$internal$gem$MappingControlUseGem[MappingControlUseGem.valueOf(mappingControlGem.value().get()).ordinal()];
        if (i == 1) {
            mappingControl.allowDirect = true;
            return;
        }
        if (i == 2) {
            mappingControl.allowMappingMethod = true;
        } else if (i == 3) {
            mappingControl.allowTypeConversion = true;
        } else {
            if (i != 4) {
                return;
            }
            mappingControl.allow2Steps = true;
        }
    }

    private static boolean isAnnotationInPackage(Element element, String str, Elements elements) {
        if (ElementKind.ANNOTATION_TYPE == element.getKind()) {
            return str.equals(elements.getPackageOf(element).getQualifiedName().toString());
        }
        return false;
    }

    private static boolean isAnnotation(Element element, String str) {
        if (ElementKind.ANNOTATION_TYPE == element.getKind()) {
            return str.equals(((TypeElement) element).getQualifiedName().toString());
        }
        return false;
    }
}
