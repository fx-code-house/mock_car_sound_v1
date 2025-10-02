package org.mapstruct.ap.internal.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.AccessorType;
import org.mapstruct.ap.internal.util.accessor.ExecutableElementAccessor;
import org.mapstruct.ap.internal.util.accessor.FieldElementAccessor;

/* loaded from: classes3.dex */
public class Filters {
    private static final Method RECORD_COMPONENTS_METHOD;
    private static final Method RECORD_COMPONENT_ACCESSOR_METHOD;
    private final AccessorNamingUtils accessorNaming;
    private final TypeMirror typeMirror;
    private final Types typeUtils;

    public static /* synthetic */ LinkedList $r8$lambda$3kEwb89y_3mqdjFd9lZCoRLWtdA() {
        return new LinkedList();
    }

    static {
        Method method;
        Method method2;
        try {
            method = TypeElement.class.getMethod("getRecordComponents", new Class[0]);
            method2 = Class.forName("javax.lang.model.element.RecordComponentElement").getMethod("getAccessor", new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException unused) {
            method = null;
            method2 = null;
        }
        RECORD_COMPONENTS_METHOD = method;
        RECORD_COMPONENT_ACCESSOR_METHOD = method2;
    }

    public Filters(AccessorNamingUtils accessorNamingUtils, Types types, TypeMirror typeMirror) {
        this.accessorNaming = accessorNamingUtils;
        this.typeUtils = types;
        this.typeMirror = typeMirror;
    }

    public List<Accessor> getterMethodsIn(List<ExecutableElement> list) {
        Stream<ExecutableElement> stream = list.stream();
        final AccessorNamingUtils accessorNamingUtils = this.accessorNaming;
        accessorNamingUtils.getClass();
        return (List) stream.filter(new Predicate() { // from class: org.mapstruct.ap.internal.util.Filters$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return accessorNamingUtils.isGetterMethod((ExecutableElement) obj);
            }
        }).map(new Function() { // from class: org.mapstruct.ap.internal.util.Filters$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2377lambda$getterMethodsIn$0$orgmapstructapinternalutilFilters((ExecutableElement) obj);
            }
        }).collect(Collectors.toCollection(new Filters$$ExternalSyntheticLambda3()));
    }

    /* renamed from: lambda$getterMethodsIn$0$org-mapstruct-ap-internal-util-Filters, reason: not valid java name */
    /* synthetic */ ExecutableElementAccessor m2377lambda$getterMethodsIn$0$orgmapstructapinternalutilFilters(ExecutableElement executableElement) {
        return new ExecutableElementAccessor(executableElement, getReturnType(executableElement), AccessorType.GETTER);
    }

    public List<Element> recordComponentsIn(TypeElement typeElement) {
        Method method = RECORD_COMPONENTS_METHOD;
        if (method == null) {
            return java.util.Collections.emptyList();
        }
        try {
            return (List) method.invoke(typeElement, new Object[0]);
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return java.util.Collections.emptyList();
        }
    }

    public Map<String, Accessor> recordAccessorsIn(Collection<Element> collection) {
        if (RECORD_COMPONENT_ACCESSOR_METHOD == null) {
            return java.util.Collections.emptyMap();
        }
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Element element : collection) {
                ExecutableElement executableElement = (ExecutableElement) RECORD_COMPONENT_ACCESSOR_METHOD.invoke(element, new Object[0]);
                linkedHashMap.put(element.getSimpleName().toString(), new ExecutableElementAccessor(executableElement, getReturnType(executableElement), AccessorType.GETTER));
            }
            return linkedHashMap;
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return java.util.Collections.emptyMap();
        }
    }

    private TypeMirror getReturnType(ExecutableElement executableElement) {
        return getWithinContext(executableElement).getReturnType();
    }

    public List<Accessor> fieldsIn(List<VariableElement> list) {
        return (List) list.stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.util.Filters$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Fields.isFieldAccessor((VariableElement) obj);
            }
        }).map(new Function() { // from class: org.mapstruct.ap.internal.util.Filters$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new FieldElementAccessor((VariableElement) obj);
            }
        }).collect(Collectors.toCollection(new Filters$$ExternalSyntheticLambda3()));
    }

    public List<Accessor> presenceCheckMethodsIn(List<ExecutableElement> list) {
        Stream<ExecutableElement> stream = list.stream();
        final AccessorNamingUtils accessorNamingUtils = this.accessorNaming;
        accessorNamingUtils.getClass();
        return (List) stream.filter(new Predicate() { // from class: org.mapstruct.ap.internal.util.Filters$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return accessorNamingUtils.isPresenceCheckMethod((ExecutableElement) obj);
            }
        }).map(new Function() { // from class: org.mapstruct.ap.internal.util.Filters$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2378xd7ea74d7((ExecutableElement) obj);
            }
        }).collect(Collectors.toCollection(new Filters$$ExternalSyntheticLambda3()));
    }

    /* renamed from: lambda$presenceCheckMethodsIn$1$org-mapstruct-ap-internal-util-Filters, reason: not valid java name */
    /* synthetic */ ExecutableElementAccessor m2378xd7ea74d7(ExecutableElement executableElement) {
        return new ExecutableElementAccessor(executableElement, getReturnType(executableElement), AccessorType.PRESENCE_CHECKER);
    }

    public List<Accessor> setterMethodsIn(List<ExecutableElement> list) {
        Stream<ExecutableElement> stream = list.stream();
        final AccessorNamingUtils accessorNamingUtils = this.accessorNaming;
        accessorNamingUtils.getClass();
        return (List) stream.filter(new Predicate() { // from class: org.mapstruct.ap.internal.util.Filters$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return accessorNamingUtils.isSetterMethod((ExecutableElement) obj);
            }
        }).map(new Function() { // from class: org.mapstruct.ap.internal.util.Filters$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2379lambda$setterMethodsIn$2$orgmapstructapinternalutilFilters((ExecutableElement) obj);
            }
        }).collect(Collectors.toCollection(new Filters$$ExternalSyntheticLambda3()));
    }

    /* renamed from: lambda$setterMethodsIn$2$org-mapstruct-ap-internal-util-Filters, reason: not valid java name */
    /* synthetic */ ExecutableElementAccessor m2379lambda$setterMethodsIn$2$orgmapstructapinternalutilFilters(ExecutableElement executableElement) {
        return new ExecutableElementAccessor(executableElement, getFirstParameter(executableElement), AccessorType.SETTER);
    }

    private TypeMirror getFirstParameter(ExecutableElement executableElement) {
        return (TypeMirror) Collections.first(getWithinContext(executableElement).getParameterTypes());
    }

    private ExecutableType getWithinContext(ExecutableElement executableElement) {
        return this.typeUtils.asMemberOf(this.typeMirror, executableElement);
    }

    public List<Accessor> adderMethodsIn(List<ExecutableElement> list) {
        Stream<ExecutableElement> stream = list.stream();
        final AccessorNamingUtils accessorNamingUtils = this.accessorNaming;
        accessorNamingUtils.getClass();
        return (List) stream.filter(new Predicate() { // from class: org.mapstruct.ap.internal.util.Filters$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return accessorNamingUtils.isAdderMethod((ExecutableElement) obj);
            }
        }).map(new Function() { // from class: org.mapstruct.ap.internal.util.Filters$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2376lambda$adderMethodsIn$3$orgmapstructapinternalutilFilters((ExecutableElement) obj);
            }
        }).collect(Collectors.toCollection(new Filters$$ExternalSyntheticLambda3()));
    }

    /* renamed from: lambda$adderMethodsIn$3$org-mapstruct-ap-internal-util-Filters, reason: not valid java name */
    /* synthetic */ ExecutableElementAccessor m2376lambda$adderMethodsIn$3$orgmapstructapinternalutilFilters(ExecutableElement executableElement) {
        return new ExecutableElementAccessor(executableElement, getFirstParameter(executableElement), AccessorType.ADDER);
    }
}
