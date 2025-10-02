package org.mapstruct.ap.internal.model.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import okhttp3.HttpUrl;
import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.util.AnnotationProcessingException;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Extractor;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.JavaStreamConstants;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.NativeTypes;
import org.mapstruct.ap.internal.util.RoundContext;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;
import org.mapstruct.ap.spi.BuilderInfo;
import org.mapstruct.ap.spi.MoreThanOneBuilderCreationMethodException;
import org.mapstruct.ap.spi.TypeHierarchyErroneousException;

/* loaded from: classes3.dex */
public class TypeFactory {
    private static final Extractor<BuilderInfo, String> BUILDER_INFO_CREATION_METHOD_EXTRACTOR = new Extractor() { // from class: org.mapstruct.ap.internal.model.common.TypeFactory$$ExternalSyntheticLambda0
        @Override // org.mapstruct.ap.internal.util.Extractor
        public final Object apply(Object obj) {
            return TypeFactory.lambda$static$0((BuilderInfo) obj);
        }
    };
    private final TypeMirror collectionType;
    private final Elements elementUtils;
    private final Map<String, ImplementationType> implementationTypes;
    private final TypeMirror iterableType;
    private final boolean loggingVerbose;
    private final TypeMirror mapType;
    private final FormattingMessager messager;
    private final Map<String, String> notToBeImportedTypes;
    private final RoundContext roundContext;
    private final TypeMirror streamType;
    private final Map<String, String> toBeImportedTypes;
    private final Types typeUtils;

    static /* synthetic */ String lambda$static$0(BuilderInfo builderInfo) {
        ExecutableElement builderCreationMethod = builderInfo.getBuilderCreationMethod();
        StringBuilder sb = new StringBuilder((CharSequence) builderCreationMethod.getSimpleName());
        sb.append('(');
        Iterator it = builderCreationMethod.getParameters().iterator();
        while (it.hasNext()) {
            sb.append((VariableElement) it.next());
        }
        sb.append(')');
        return sb.toString();
    }

    public TypeFactory(Elements elements, Types types, FormattingMessager formattingMessager, RoundContext roundContext, Map<String, String> map, boolean z) {
        HashMap map2 = new HashMap();
        this.implementationTypes = map2;
        this.toBeImportedTypes = new HashMap();
        this.elementUtils = elements;
        this.typeUtils = types;
        this.messager = formattingMessager;
        this.roundContext = roundContext;
        this.notToBeImportedTypes = map;
        this.iterableType = types.erasure(elements.getTypeElement(Iterable.class.getCanonicalName()).asType());
        this.collectionType = types.erasure(elements.getTypeElement(Collection.class.getCanonicalName()).asType());
        this.mapType = types.erasure(elements.getTypeElement(Map.class.getCanonicalName()).asType());
        TypeElement typeElement = elements.getTypeElement(JavaStreamConstants.STREAM_FQN);
        this.streamType = typeElement == null ? null : types.erasure(typeElement.asType());
        map2.put(Iterable.class.getName(), ImplementationType.withInitialCapacity(getType(ArrayList.class)));
        map2.put(Collection.class.getName(), ImplementationType.withInitialCapacity(getType(ArrayList.class)));
        map2.put(List.class.getName(), ImplementationType.withInitialCapacity(getType(ArrayList.class)));
        map2.put(Set.class.getName(), ImplementationType.withLoadFactorAdjustment(getType(HashSet.class)));
        map2.put(SortedSet.class.getName(), ImplementationType.withDefaultConstructor(getType(TreeSet.class)));
        map2.put(NavigableSet.class.getName(), ImplementationType.withDefaultConstructor(getType(TreeSet.class)));
        map2.put(Map.class.getName(), ImplementationType.withLoadFactorAdjustment(getType(HashMap.class)));
        map2.put(SortedMap.class.getName(), ImplementationType.withDefaultConstructor(getType(TreeMap.class)));
        map2.put(NavigableMap.class.getName(), ImplementationType.withDefaultConstructor(getType(TreeMap.class)));
        map2.put(ConcurrentMap.class.getName(), ImplementationType.withLoadFactorAdjustment(getType(ConcurrentHashMap.class)));
        map2.put(ConcurrentNavigableMap.class.getName(), ImplementationType.withDefaultConstructor(getType(ConcurrentSkipListMap.class)));
        this.loggingVerbose = z;
    }

    public Type getTypeForLiteral(Class<?> cls) {
        return cls.isPrimitive() ? getType(getPrimitiveType(cls), true) : getType(cls.getCanonicalName(), true);
    }

    public Type getType(Class<?> cls) {
        return cls.isPrimitive() ? getType(getPrimitiveType(cls)) : getType(cls.getCanonicalName());
    }

    public Type getType(String str) {
        return getType(str, false);
    }

    private Type getType(String str, boolean z) {
        TypeElement typeElement = this.elementUtils.getTypeElement(str);
        if (typeElement == null) {
            throw new AnnotationProcessingException("Couldn't find type " + str + ". Are you missing a dependency on your classpath?");
        }
        return getType(typeElement, z);
    }

    public boolean isTypeAvailable(String str) {
        return this.elementUtils.getTypeElement(str) != null;
    }

    public Type getWrappedType(Type type) {
        if (!type.isPrimitive()) {
            return type;
        }
        return getType(this.typeUtils.boxedClass(type.getTypeMirror()));
    }

    public Type getType(TypeElement typeElement) {
        return getType(typeElement.asType(), false);
    }

    private Type getType(TypeElement typeElement, boolean z) {
        return getType(typeElement.asType(), z);
    }

    public Type getType(TypeMirror typeMirror) {
        return getType(typeMirror, false);
    }

    private Type getType(TypeMirror typeMirror, boolean z) {
        String name;
        String str;
        Boolean bool;
        boolean z2;
        boolean z3;
        TypeElement typeElement;
        Type type;
        String str2;
        String string;
        boolean z4;
        Boolean bool2;
        String str3;
        String string2;
        String string3;
        String string4;
        if (!canBeProcessed(typeMirror)) {
            throw new TypeHierarchyErroneousException(typeMirror);
        }
        ImplementationType implementationType = getImplementationType(typeMirror);
        boolean zIsSubtype = this.typeUtils.isSubtype(typeMirror, this.iterableType);
        boolean zIsSubtype2 = this.typeUtils.isSubtype(typeMirror, this.collectionType);
        boolean zIsSubtype3 = this.typeUtils.isSubtype(typeMirror, this.mapType);
        TypeMirror typeMirror2 = this.streamType;
        boolean z5 = typeMirror2 != null && this.typeUtils.isSubtype(typeMirror, typeMirror2);
        if (typeMirror.getKind() == TypeKind.DECLARED) {
            DeclaredType declaredType = (DeclaredType) typeMirror;
            boolean z6 = declaredType.asElement().getKind() == ElementKind.ENUM;
            boolean z7 = declaredType.asElement().getKind() == ElementKind.INTERFACE;
            String string5 = declaredType.asElement().getSimpleName().toString();
            TypeElement typeElement2 = (TypeElement) declaredType.asElement();
            if (typeElement2 != null) {
                string3 = this.elementUtils.getPackageOf(typeElement2).getQualifiedName().toString();
                string4 = typeElement2.getQualifiedName().toString();
            } else {
                string3 = null;
                string4 = string5;
            }
            z2 = z7;
            z3 = z6;
            bool = null;
            name = string5;
            str2 = string3;
            str = string4;
            typeElement = typeElement2;
            type = null;
        } else if (typeMirror.getKind() == TypeKind.ARRAY) {
            TypeMirror componentType = getComponentType(typeMirror);
            StringBuilder sb = new StringBuilder(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            while (componentType.getKind() == TypeKind.ARRAY) {
                componentType = getComponentType(componentType);
                sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            }
            if (componentType.getKind() == TypeKind.DECLARED) {
                TypeElement typeElementAsElement = ((DeclaredType) componentType).asElement();
                String string6 = sb.toString();
                string = typeElementAsElement.getSimpleName().toString() + string6;
                string2 = this.elementUtils.getPackageOf(typeElementAsElement).getQualifiedName().toString();
                str3 = typeElementAsElement.getQualifiedName().toString() + string6;
                bool2 = null;
            } else {
                if (componentType.getKind().isPrimitive()) {
                    string = NativeTypes.getName(componentType.getKind()) + sb.toString();
                    z4 = false;
                } else {
                    string = typeMirror.toString();
                    z4 = false;
                }
                bool2 = z4;
                str3 = string;
                string2 = null;
            }
            str = str3;
            bool = bool2;
            name = string;
            z2 = false;
            z3 = false;
            str2 = string2;
            type = getType(getComponentType(typeMirror));
            typeElement = null;
        } else {
            name = typeMirror.getKind().isPrimitive() ? NativeTypes.getName(typeMirror.getKind()) : typeMirror.toString();
            str = name;
            bool = false;
            z2 = false;
            z3 = false;
            typeElement = null;
            type = null;
            str2 = null;
        }
        return new Type(this.typeUtils, this.elementUtils, this, this.roundContext.getAnnotationProcessorContext().getAccessorNaming(), typeMirror, typeElement, getTypeParameters(typeMirror, false), implementationType, type, str2, name, str, z2, z3, zIsSubtype, zIsSubtype2, zIsSubtype3, z5, this.toBeImportedTypes, this.notToBeImportedTypes, bool, z, this.loggingVerbose);
    }

    public Type classTypeOf(Type type) {
        TypeMirror typeMirror;
        if (type.isVoid()) {
            return null;
        }
        if (type.isPrimitive()) {
            typeMirror = this.typeUtils.boxedClass(type.getTypeMirror()).asType();
        } else {
            typeMirror = type.getTypeMirror();
        }
        return getType((TypeMirror) this.typeUtils.getDeclaredType(this.elementUtils.getTypeElement("java.lang.Class"), new TypeMirror[]{typeMirror}));
    }

    public ExecutableType getMethodType(DeclaredType declaredType, ExecutableElement executableElement) {
        return this.typeUtils.asMemberOf(declaredType, executableElement);
    }

    public TypeMirror getMethodType(DeclaredType declaredType, Element element) {
        return this.typeUtils.asMemberOf(declaredType, element);
    }

    public Parameter getSingleParameter(DeclaredType declaredType, Accessor accessor) {
        if (!accessor.getAccessorType().isFieldAssignment() && accessor.getElement().getParameters().size() == 1) {
            return (Parameter) Collections.first(getParameters(declaredType, accessor));
        }
        return null;
    }

    public List<Parameter> getParameters(DeclaredType declaredType, Accessor accessor) {
        return getParameters(declaredType, (ExecutableElement) accessor.getElement());
    }

    public List<Parameter> getParameters(DeclaredType declaredType, ExecutableElement executableElement) {
        ExecutableType methodType = getMethodType(declaredType, executableElement);
        if (executableElement == null || methodType.getKind() != TypeKind.EXECUTABLE) {
            return new ArrayList();
        }
        return getParameters(methodType, executableElement);
    }

    public List<Parameter> getParameters(ExecutableType executableType, ExecutableElement executableElement) {
        List parameterTypes = executableType.getParameterTypes();
        List parameters = executableElement.getParameters();
        ArrayList arrayList = new ArrayList(parameters.size());
        Iterator it = parameters.iterator();
        Iterator it2 = parameterTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(Parameter.forElementAndType((VariableElement) it.next(), getType((TypeMirror) it2.next()), !it.hasNext() && executableElement.isVarArgs()));
        }
        return arrayList;
    }

    public Type getReturnType(DeclaredType declaredType, Accessor accessor) {
        ExecutableType methodType = getMethodType(declaredType, accessor.getElement());
        if (isExecutableType(methodType)) {
            return getType(methodType.getReturnType());
        }
        return getType((TypeMirror) methodType);
    }

    private boolean isExecutableType(TypeMirror typeMirror) {
        return typeMirror.getKind() == TypeKind.EXECUTABLE;
    }

    public Type getReturnType(ExecutableType executableType) {
        return getType(executableType.getReturnType());
    }

    public List<Type> getThrownTypes(ExecutableType executableType) {
        return extractTypes(executableType.getThrownTypes());
    }

    public List<Type> getThrownTypes(Accessor accessor) {
        if (accessor.getAccessorType().isFieldAssignment()) {
            return new ArrayList();
        }
        return extractTypes(accessor.getElement().getThrownTypes());
    }

    private List<Type> extractTypes(List<? extends TypeMirror> list) {
        HashSet hashSet = new HashSet(list.size());
        Iterator<? extends TypeMirror> it = list.iterator();
        while (it.hasNext()) {
            hashSet.add(getType(it.next()));
        }
        return new ArrayList(hashSet);
    }

    private List<Type> getTypeParameters(TypeMirror typeMirror, boolean z) {
        if (typeMirror.getKind() != TypeKind.DECLARED) {
            return java.util.Collections.emptyList();
        }
        DeclaredType declaredType = (DeclaredType) typeMirror;
        ArrayList arrayList = new ArrayList(declaredType.getTypeArguments().size());
        for (TypeMirror typeMirror2 : declaredType.getTypeArguments()) {
            if (z) {
                arrayList.add(getType(typeMirror2).getTypeBound());
            } else {
                arrayList.add(getType(typeMirror2));
            }
        }
        return arrayList;
    }

    private TypeMirror getPrimitiveType(Class<?> cls) {
        if (cls == Byte.TYPE) {
            return this.typeUtils.getPrimitiveType(TypeKind.BYTE);
        }
        if (cls == Short.TYPE) {
            return this.typeUtils.getPrimitiveType(TypeKind.SHORT);
        }
        if (cls == Integer.TYPE) {
            return this.typeUtils.getPrimitiveType(TypeKind.INT);
        }
        if (cls == Long.TYPE) {
            return this.typeUtils.getPrimitiveType(TypeKind.LONG);
        }
        if (cls == Float.TYPE) {
            return this.typeUtils.getPrimitiveType(TypeKind.FLOAT);
        }
        if (cls == Double.TYPE) {
            return this.typeUtils.getPrimitiveType(TypeKind.DOUBLE);
        }
        if (cls == Boolean.TYPE) {
            return this.typeUtils.getPrimitiveType(TypeKind.BOOLEAN);
        }
        if (cls == Character.TYPE) {
            return this.typeUtils.getPrimitiveType(TypeKind.CHAR);
        }
        return this.typeUtils.getPrimitiveType(TypeKind.VOID);
    }

    private ImplementationType getImplementationType(TypeMirror typeMirror) {
        if (typeMirror.getKind() != TypeKind.DECLARED) {
            return null;
        }
        DeclaredType declaredType = (DeclaredType) typeMirror;
        ImplementationType implementationType = this.implementationTypes.get(declaredType.asElement().getQualifiedName().toString());
        if (implementationType == null) {
            return null;
        }
        Type type = implementationType.getType();
        return implementationType.createNew(new Type(this.typeUtils, this.elementUtils, this, this.roundContext.getAnnotationProcessorContext().getAccessorNaming(), this.typeUtils.getDeclaredType(type.getTypeElement(), (TypeMirror[]) declaredType.getTypeArguments().toArray(new TypeMirror[0])), type.getTypeElement(), getTypeParameters(typeMirror, true), null, null, type.getPackageName(), type.getName(), type.getFullyQualifiedName(), type.isInterface(), type.isEnumType(), type.isIterableType(), type.isCollectionType(), type.isMapType(), type.isStreamType(), this.toBeImportedTypes, this.notToBeImportedTypes, null, type.isLiteral(), this.loggingVerbose));
    }

    private BuilderInfo findBuilder(TypeMirror typeMirror, BuilderGem builderGem, boolean z) {
        if (builderGem != null && builderGem.disableBuilder().get().booleanValue()) {
            return null;
        }
        try {
            return this.roundContext.getAnnotationProcessorContext().getBuilderProvider().findBuilderInfo(typeMirror);
        } catch (MoreThanOneBuilderCreationMethodException e) {
            if (z) {
                this.messager.printMessage(this.typeUtils.asElement(typeMirror), Message.BUILDER_MORE_THAN_ONE_BUILDER_CREATION_METHOD, typeMirror, Strings.join(e.getBuilderInfo(), ", ", BUILDER_INFO_CREATION_METHOD_EXTRACTOR));
            }
            return null;
        }
    }

    private TypeMirror getComponentType(TypeMirror typeMirror) {
        if (typeMirror.getKind() != TypeKind.ARRAY) {
            return null;
        }
        return ((ArrayType) typeMirror).getComponentType();
    }

    public Type createVoidType() {
        return getType((TypeMirror) this.typeUtils.getNoType(TypeKind.VOID));
    }

    public TypeMirror getTypeBound(TypeMirror typeMirror) {
        if (typeMirror.getKind() == TypeKind.WILDCARD) {
            WildcardType wildcardType = (WildcardType) typeMirror;
            if (wildcardType.getExtendsBound() != null) {
                return wildcardType.getExtendsBound();
            }
            if (wildcardType.getSuperBound() != null) {
                return wildcardType.getSuperBound();
            }
            return "?".equals(wildcardType.toString()) ? this.elementUtils.getTypeElement(Object.class.getCanonicalName()).asType() : typeMirror;
        }
        if (typeMirror.getKind() != TypeKind.TYPEVAR) {
            return typeMirror;
        }
        TypeVariable typeVariable = (TypeVariable) typeMirror;
        return typeVariable.getUpperBound() != null ? typeVariable.getUpperBound() : typeMirror;
    }

    private boolean canBeProcessed(TypeMirror typeMirror) {
        if (typeMirror.getKind() == TypeKind.ERROR) {
            return false;
        }
        if (typeMirror.getKind() != TypeKind.DECLARED || this.roundContext.isReadyForProcessing(typeMirror)) {
            return true;
        }
        Iterator<AstModifyingAnnotationProcessor> it = this.roundContext.getAnnotationProcessorContext().getAstModifyingAnnotationProcessors().iterator();
        while (it.hasNext()) {
            if (!it.next().isTypeComplete(typeMirror)) {
                return false;
            }
        }
        this.roundContext.addTypeReadyForProcessing(typeMirror);
        return true;
    }

    public BuilderType builderTypeFor(Type type, BuilderGem builderGem) {
        if (type != null) {
            return BuilderType.create(findBuilder(type.getTypeMirror(), builderGem, true), type, this, this.typeUtils);
        }
        return null;
    }

    public Type effectiveResultTypeFor(Type type, BuilderGem builderGem) {
        BuilderType builderTypeCreate;
        return (type == null || (builderTypeCreate = BuilderType.create(findBuilder(type.getTypeMirror(), builderGem, false), type, this, this.typeUtils)) == null) ? type : builderTypeCreate.getBuilder();
    }
}
