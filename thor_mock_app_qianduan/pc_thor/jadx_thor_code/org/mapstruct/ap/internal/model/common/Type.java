package org.mapstruct.ap.internal.model.common;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleTypeVisitor8;
import javax.lang.model.util.Types;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.Fields;
import org.mapstruct.ap.internal.util.Filters;
import org.mapstruct.ap.internal.util.JavaStreamConstants;
import org.mapstruct.ap.internal.util.NativeTypes;
import org.mapstruct.ap.internal.util.Nouns;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.AccessorType;

/* loaded from: classes3.dex */
public class Type extends ModelElement implements Comparable<Type> {
    private final AccessorNamingUtils accessorNaming;
    private final Type componentType;
    private final Elements elementUtils;
    private final List<String> enumConstants;
    private final Filters filters;
    private Boolean hasAccessibleConstructor;
    private final ImplementationType implementationType;
    private final boolean isCollectionType;
    private final boolean isEnumType;
    private final boolean isInterface;
    private final boolean isIterableType;
    private final boolean isLiteral;
    private final boolean isMapType;
    private final boolean isStream;
    private Boolean isToBeImported;
    private final boolean isVoid;
    private final boolean loggingVerbose;
    private final String name;
    private final Map<String, String> notToBeImportedTypes;
    private final String packageName;
    private final String qualifiedName;
    private final Map<String, String> toBeImportedTypes;
    private final TypeElement typeElement;
    private final TypeFactory typeFactory;
    private final TypeMirror typeMirror;
    private final List<Type> typeParameters;
    private final Types typeUtils;
    private Map<String, Accessor> readAccessors = null;
    private Map<String, Accessor> presenceCheckers = null;
    private List<ExecutableElement> allMethods = null;
    private List<VariableElement> allFields = null;
    private List<Element> recordComponents = null;
    private List<Accessor> setters = null;
    private List<Accessor> adders = null;
    private List<Accessor> alternativeTargetAccessors = null;
    private Map<String, Accessor> constructorAccessors = null;
    private Type boundingBase = null;

    public Type(Types types, Elements elements, TypeFactory typeFactory, AccessorNamingUtils accessorNamingUtils, TypeMirror typeMirror, TypeElement typeElement, List<Type> list, ImplementationType implementationType, Type type, String str, String str2, String str3, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, Map<String, String> map, Map<String, String> map2, Boolean bool, boolean z7, boolean z8) {
        this.typeUtils = types;
        this.elementUtils = elements;
        this.typeFactory = typeFactory;
        this.accessorNaming = accessorNamingUtils;
        this.typeMirror = typeMirror;
        this.typeElement = typeElement;
        this.typeParameters = list;
        this.componentType = type;
        this.implementationType = implementationType;
        this.packageName = str;
        this.name = str2;
        this.qualifiedName = str3;
        this.isInterface = z;
        this.isEnumType = z2;
        this.isIterableType = z3;
        this.isCollectionType = z4;
        this.isMapType = z5;
        this.isStream = z6;
        this.isVoid = typeMirror.getKind() == TypeKind.VOID;
        this.isLiteral = z7;
        if (z2) {
            this.enumConstants = new ArrayList();
            for (Element element : typeElement.getEnclosedElements()) {
                if (element.getKind() == ElementKind.ENUM_CONSTANT && element.getModifiers().contains(Modifier.PUBLIC)) {
                    this.enumConstants.add(element.getSimpleName().toString());
                }
            }
        } else {
            this.enumConstants = Collections.emptyList();
        }
        this.isToBeImported = bool;
        this.toBeImportedTypes = map;
        this.notToBeImportedTypes = map2;
        this.filters = new Filters(accessorNamingUtils, types, typeMirror);
        this.loggingVerbose = z8;
    }

    public TypeMirror getTypeMirror() {
        return this.typeMirror;
    }

    public TypeElement getTypeElement() {
        return this.typeElement;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getName() {
        return this.name;
    }

    public String createReferenceName() {
        return (isToBeImported() || shouldUseSimpleName()) ? this.name : this.qualifiedName;
    }

    public List<Type> getTypeParameters() {
        return this.typeParameters;
    }

    public Type getComponentType() {
        return this.componentType;
    }

    public boolean isPrimitive() {
        return this.typeMirror.getKind().isPrimitive();
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public boolean isEnumType() {
        return this.isEnumType;
    }

    public boolean isVoid() {
        return this.isVoid;
    }

    public boolean isAbstract() {
        TypeElement typeElement = this.typeElement;
        return typeElement != null && typeElement.getModifiers().contains(Modifier.ABSTRACT);
    }

    public boolean isString() {
        return String.class.getName().equals(getFullyQualifiedName());
    }

    public List<String> getEnumConstants() {
        return this.enumConstants;
    }

    public Type getImplementationType() {
        ImplementationType implementationType = this.implementationType;
        if (implementationType != null) {
            return implementationType.getType();
        }
        return null;
    }

    public ImplementationType getImplementation() {
        return this.implementationType;
    }

    public boolean isIterableType() {
        return this.isIterableType || isArrayType();
    }

    public boolean isIterableOrStreamType() {
        return isIterableType() || isStreamType();
    }

    public boolean isCollectionType() {
        return this.isCollectionType;
    }

    public boolean isMapType() {
        return this.isMapType;
    }

    public boolean isCollectionOrMapType() {
        return this.isCollectionType || this.isMapType;
    }

    public boolean isArrayType() {
        return this.componentType != null;
    }

    public boolean isTypeVar() {
        return this.typeMirror.getKind() == TypeKind.TYPEVAR;
    }

    public boolean isJavaLangType() {
        String str = this.packageName;
        return str != null && str.startsWith("java.");
    }

    public boolean isRecord() {
        return this.typeElement.getKind().name().equals("RECORD");
    }

    public boolean isStreamType() {
        return this.isStream;
    }

    public boolean isWildCardSuperBound() {
        return this.typeMirror.getKind() == TypeKind.WILDCARD && this.typeMirror.getSuperBound() != null;
    }

    public boolean isWildCardExtendsBound() {
        return this.typeMirror.getKind() == TypeKind.WILDCARD && this.typeMirror.getExtendsBound() != null;
    }

    public String getFullyQualifiedName() {
        return this.qualifiedName;
    }

    public String getImportName() {
        return isArrayType() ? trimSimpleClassName(this.qualifiedName) : this.qualifiedName;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        HashSet hashSet = new HashSet();
        if (getTypeMirror().getKind() == TypeKind.DECLARED) {
            hashSet.add(this);
        }
        Type type = this.componentType;
        if (type != null) {
            hashSet.addAll(type.getImportTypes());
        }
        Iterator<Type> it = this.typeParameters.iterator();
        while (it.hasNext()) {
            hashSet.addAll(it.next().getImportTypes());
        }
        if ((isWildCardExtendsBound() || isWildCardSuperBound()) && getTypeBound() != null) {
            hashSet.addAll(getTypeBound().getImportTypes());
        }
        return hashSet;
    }

    public boolean isToBeImported() {
        if (this.isToBeImported == null) {
            String strTrimSimpleClassName = trimSimpleClassName(this.name);
            if (this.notToBeImportedTypes.containsKey(strTrimSimpleClassName)) {
                Boolean bool = false;
                this.isToBeImported = bool;
                return bool.booleanValue();
            }
            String strTrimSimpleClassName2 = trimSimpleClassName(this.qualifiedName);
            String str = this.toBeImportedTypes.get(strTrimSimpleClassName);
            this.isToBeImported = false;
            if (str != null) {
                if (str.equals(strTrimSimpleClassName2)) {
                    this.isToBeImported = true;
                }
            } else {
                this.toBeImportedTypes.put(strTrimSimpleClassName, strTrimSimpleClassName2);
                this.isToBeImported = true;
            }
        }
        return this.isToBeImported.booleanValue();
    }

    private boolean shouldUseSimpleName() {
        return this.qualifiedName.equals(this.notToBeImportedTypes.get(this.name));
    }

    public Type erasure() {
        Types types = this.typeUtils;
        return new Type(types, this.elementUtils, this.typeFactory, this.accessorNaming, types.erasure(this.typeMirror), this.typeElement, this.typeParameters, this.implementationType, this.componentType, this.packageName, this.name, this.qualifiedName, this.isInterface, this.isEnumType, this.isIterableType, this.isCollectionType, this.isMapType, this.isStream, this.toBeImportedTypes, this.notToBeImportedTypes, this.isToBeImported, this.isLiteral, this.loggingVerbose);
    }

    public Type withoutBounds() {
        if (this.typeParameters.isEmpty()) {
            return this;
        }
        ArrayList arrayList = new ArrayList(this.typeParameters.size());
        ArrayList arrayList2 = new ArrayList(this.typeParameters.size());
        for (Type type : this.typeParameters) {
            arrayList.add(type.getTypeBound());
            arrayList2.add(type.getTypeBound().getTypeMirror());
        }
        DeclaredType declaredType = this.typeUtils.getDeclaredType(this.typeElement, (TypeMirror[]) arrayList2.toArray(new TypeMirror[0]));
        return new Type(this.typeUtils, this.elementUtils, this.typeFactory, this.accessorNaming, declaredType, declaredType.asElement(), arrayList, this.implementationType, this.componentType, this.packageName, this.name, this.qualifiedName, this.isInterface, this.isEnumType, this.isIterableType, this.isCollectionType, this.isMapType, this.isStream, this.toBeImportedTypes, this.notToBeImportedTypes, this.isToBeImported, this.isLiteral, this.loggingVerbose);
    }

    public boolean isAssignableTo(Type type) {
        if (equals(type)) {
            return true;
        }
        return this.typeUtils.isAssignable(isWildCardExtendsBound() ? getTypeBound().typeMirror : this.typeMirror, type.typeMirror);
    }

    public boolean isRawAssignableTo(Type type) {
        if (isTypeVar() || type.isTypeVar() || equals(type)) {
            return true;
        }
        Types types = this.typeUtils;
        return types.isAssignable(types.erasure(this.typeMirror), this.typeUtils.erasure(type.typeMirror));
    }

    public Map<String, Accessor> getPropertyReadAccessors() {
        if (this.readAccessors == null) {
            List<Accessor> list = this.filters.getterMethodsIn(getAllMethods());
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Accessor accessor : list) {
                String propertyName = getPropertyName(accessor);
                if (linkedHashMap.containsKey(propertyName)) {
                    if (!accessor.getSimpleName().startsWith("is")) {
                        linkedHashMap.put(propertyName, accessor);
                    }
                } else {
                    linkedHashMap.put(propertyName, accessor);
                }
            }
            for (Map.Entry<String, Accessor> entry : this.filters.recordAccessorsIn(getRecordComponents()).entrySet()) {
                linkedHashMap.putIfAbsent(entry.getKey(), entry.getValue());
            }
            for (Accessor accessor2 : this.filters.fieldsIn(getAllFields())) {
                linkedHashMap.putIfAbsent(getPropertyName(accessor2), accessor2);
            }
            this.readAccessors = Collections.unmodifiableMap(linkedHashMap);
        }
        return this.readAccessors;
    }

    public Map<String, Accessor> getPropertyPresenceCheckers() {
        if (this.presenceCheckers == null) {
            List<Accessor> listPresenceCheckMethodsIn = this.filters.presenceCheckMethodsIn(getAllMethods());
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Accessor accessor : listPresenceCheckMethodsIn) {
                linkedHashMap.put(getPropertyName(accessor), accessor);
            }
            this.presenceCheckers = Collections.unmodifiableMap(linkedHashMap);
        }
        return this.presenceCheckers;
    }

    public Map<String, Accessor> getPropertyWriteAccessors(CollectionMappingStrategyGem collectionMappingStrategyGem) {
        ArrayList<Accessor> arrayList = new ArrayList(getSetters());
        arrayList.addAll(getAlternativeTargetAccessors());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Accessor accessor : arrayList) {
            String propertyName = getPropertyName(accessor);
            Type typeDeterminePreferredType = determinePreferredType(getPropertyReadAccessors().get(propertyName));
            Type typeDetermineTargetType = determineTargetType(accessor);
            if (collectionMappingStrategyGem == CollectionMappingStrategyGem.SETTER_PREFERRED || collectionMappingStrategyGem == CollectionMappingStrategyGem.ADDER_PREFERRED || collectionMappingStrategyGem == CollectionMappingStrategyGem.TARGET_IMMUTABLE) {
                Accessor adderForType = ((accessor.getAccessorType() == AccessorType.SETTER && collectionMappingStrategyGem == CollectionMappingStrategyGem.ADDER_PREFERRED) || accessor.getAccessorType() == AccessorType.GETTER) ? getAdderForType(typeDetermineTargetType, propertyName) : null;
                if (adderForType != null) {
                    accessor = adderForType;
                }
                if (((Accessor) linkedHashMap.get(propertyName)) != null || typeDeterminePreferredType == null || (typeDetermineTargetType != null && this.typeUtils.isAssignable(typeDeterminePreferredType.getTypeMirror(), typeDetermineTargetType.getTypeMirror()))) {
                    linkedHashMap.put(propertyName, accessor);
                }
            } else if (accessor.getAccessorType() != AccessorType.FIELD || (!Executables.isFinal(accessor) && !linkedHashMap.containsKey(propertyName))) {
                if (((Accessor) linkedHashMap.get(propertyName)) != null) {
                }
                linkedHashMap.put(propertyName, accessor);
            }
        }
        return linkedHashMap;
    }

    public List<Element> getRecordComponents() {
        if (this.recordComponents == null) {
            this.recordComponents = this.filters.recordComponentsIn(this.typeElement);
        }
        return this.recordComponents;
    }

    private Type determinePreferredType(Accessor accessor) {
        if (accessor != null) {
            return this.typeFactory.getReturnType((DeclaredType) this.typeMirror, accessor);
        }
        return null;
    }

    private Type determineTargetType(Accessor accessor) {
        Parameter singleParameter = this.typeFactory.getSingleParameter((DeclaredType) this.typeMirror, accessor);
        if (singleParameter != null) {
            return singleParameter.getType();
        }
        if (accessor.getAccessorType() == AccessorType.GETTER || accessor.getAccessorType().isFieldAssignment()) {
            return this.typeFactory.getReturnType((DeclaredType) this.typeMirror, accessor);
        }
        return null;
    }

    private List<ExecutableElement> getAllMethods() {
        if (this.allMethods == null) {
            this.allMethods = Executables.getAllEnclosedExecutableElements(this.elementUtils, this.typeElement);
        }
        return this.allMethods;
    }

    private List<VariableElement> getAllFields() {
        if (this.allFields == null) {
            this.allFields = Fields.getAllEnclosedFields(this.elementUtils, this.typeElement);
        }
        return this.allFields;
    }

    private String getPropertyName(Accessor accessor) {
        ExecutableElement element = accessor.getElement();
        if (element instanceof ExecutableElement) {
            return this.accessorNaming.getPropertyName(element);
        }
        return accessor.getSimpleName();
    }

    private Accessor getAdderForType(Type type, String str) {
        List<Accessor> accessorCandidates;
        if (type.isCollectionType()) {
            accessorCandidates = getAccessorCandidates(type, Iterable.class);
        } else {
            if (type.isStreamType()) {
                accessorCandidates = getAccessorCandidates(type, Stream.class);
            }
            return null;
        }
        if (accessorCandidates.isEmpty()) {
            return null;
        }
        if (accessorCandidates.size() == 1) {
            return accessorCandidates.get(0);
        }
        for (Accessor accessor : accessorCandidates) {
            String elementNameForAdder = this.accessorNaming.getElementNameForAdder(accessor);
            if (elementNameForAdder != null && elementNameForAdder.equals(Nouns.singularize(str))) {
                return accessor;
            }
        }
        return null;
    }

    private List<Accessor> getAccessorCandidates(Type type, Class<?> cls) {
        TypeMirror typeMirror = ((Type) org.mapstruct.ap.internal.util.Collections.first(type.determineTypeArguments(cls))).getTypeBound().getTypeMirror();
        List<Accessor> adders = getAdders();
        ArrayList arrayList = new ArrayList();
        for (Accessor accessor : adders) {
            if (this.typeUtils.isSameType(boxed(((VariableElement) accessor.getElement().getParameters().get(0)).asType()), boxed(typeMirror))) {
                arrayList.add(accessor);
            }
        }
        return arrayList;
    }

    private TypeMirror boxed(TypeMirror typeMirror) {
        return typeMirror.getKind().isPrimitive() ? this.typeUtils.boxedClass((PrimitiveType) typeMirror).asType() : typeMirror;
    }

    private List<Accessor> getSetters() {
        if (this.setters == null) {
            this.setters = Collections.unmodifiableList(this.filters.setterMethodsIn(getAllMethods()));
        }
        return this.setters;
    }

    private List<Accessor> getAdders() {
        if (this.adders == null) {
            this.adders = Collections.unmodifiableList(this.filters.adderMethodsIn(getAllMethods()));
        }
        return this.adders;
    }

    private List<Accessor> getAlternativeTargetAccessors() {
        if (this.alternativeTargetAccessors == null) {
            ArrayList arrayList = new ArrayList();
            List<Accessor> setters = getSetters();
            ArrayList<Accessor> arrayList2 = new ArrayList(getPropertyReadAccessors().values());
            arrayList2.addAll(this.filters.fieldsIn(getAllFields()));
            for (Accessor accessor : arrayList2) {
                if (isCollectionOrMapOrStream(accessor) && !correspondingSetterMethodExists(accessor, setters)) {
                    arrayList.add(accessor);
                } else if (accessor.getAccessorType() == AccessorType.FIELD && !correspondingSetterMethodExists(accessor, setters)) {
                    arrayList.add(accessor);
                }
            }
            this.alternativeTargetAccessors = Collections.unmodifiableList(arrayList);
        }
        return this.alternativeTargetAccessors;
    }

    private boolean correspondingSetterMethodExists(Accessor accessor, List<Accessor> list) {
        String propertyName = getPropertyName(accessor);
        Iterator<Accessor> it = list.iterator();
        while (it.hasNext()) {
            if (propertyName.equals(getPropertyName(it.next()))) {
                return true;
            }
        }
        return false;
    }

    private boolean isCollectionOrMapOrStream(Accessor accessor) {
        return isCollection(accessor.getAccessedType()) || isMap(accessor.getAccessedType()) || isStream(accessor.getAccessedType());
    }

    private boolean isCollection(TypeMirror typeMirror) {
        return isSubType(typeMirror, Collection.class);
    }

    private boolean isStream(TypeMirror typeMirror) {
        TypeElement typeElement = this.elementUtils.getTypeElement(JavaStreamConstants.STREAM_FQN);
        TypeMirror typeMirrorErasure = typeElement == null ? null : this.typeUtils.erasure(typeElement.asType());
        return typeMirrorErasure != null && this.typeUtils.isSubtype(typeMirror, typeMirrorErasure);
    }

    private boolean isMap(TypeMirror typeMirror) {
        return isSubType(typeMirror, Map.class);
    }

    private boolean isSubType(TypeMirror typeMirror, Class<?> cls) {
        return this.typeUtils.isSubtype(typeMirror, this.typeUtils.erasure(this.elementUtils.getTypeElement(cls.getCanonicalName()).asType()));
    }

    public int distanceTo(Type type) {
        return distanceTo(this.typeMirror, type.typeMirror);
    }

    private int distanceTo(TypeMirror typeMirror, TypeMirror typeMirror2) {
        if (this.typeUtils.isSameType(typeMirror, typeMirror2)) {
            return 0;
        }
        if (!this.typeUtils.isAssignable(typeMirror, typeMirror2)) {
            return -1;
        }
        Iterator it = this.typeUtils.directSupertypes(typeMirror).iterator();
        int iMin = Integer.MAX_VALUE;
        while (it.hasNext()) {
            int iDistanceTo = distanceTo((TypeMirror) it.next(), typeMirror2);
            if (iDistanceTo >= 0) {
                iMin = Math.min(iMin, iDistanceTo);
            }
        }
        return iMin + 1;
    }

    public boolean canAccess(Type type, ExecutableElement executableElement) {
        if (executableElement.getModifiers().contains(Modifier.PRIVATE)) {
            return false;
        }
        if (executableElement.getModifiers().contains(Modifier.PROTECTED)) {
            return isAssignableTo(type) || getPackageName().equals(type.getPackageName());
        }
        if (executableElement.getModifiers().contains(Modifier.PUBLIC)) {
            return true;
        }
        return getPackageName().equals(type.getPackageName());
    }

    public String getNull() {
        if (!isPrimitive() || isArrayType()) {
            return "null";
        }
        if ("boolean".equals(getName())) {
            return BooleanUtils.FALSE;
        }
        if ("byte".equals(getName()) || "char".equals(getName())) {
            return SessionDescription.SUPPORTED_SDP_VERSION;
        }
        if ("double".equals(getName())) {
            return "0.0d";
        }
        if ("float".equals(getName())) {
            return "0.0f";
        }
        if ("int".equals(getName())) {
            return SessionDescription.SUPPORTED_SDP_VERSION;
        }
        if ("long".equals(getName())) {
            return "0L";
        }
        if ("short".equals(getName())) {
            return SessionDescription.SUPPORTED_SDP_VERSION;
        }
        throw new UnsupportedOperationException(getName());
    }

    public String getSensibleDefault() {
        if (isPrimitive()) {
            return getNull();
        }
        if ("String".equals(getName())) {
            return "\"\"";
        }
        if (isNative()) {
            return this.typeFactory.getType((TypeMirror) this.typeUtils.unboxedType(this.typeMirror)).getNull();
        }
        return null;
    }

    public int hashCode() {
        String str = this.name;
        int iHashCode = ((str == null ? 0 : str.hashCode()) + 31) * 31;
        String str2 = this.packageName;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return this.typeUtils.isSameType(this.typeMirror, ((Type) obj).typeMirror);
        }
        return false;
    }

    @Override // java.lang.Comparable
    public int compareTo(Type type) {
        return getFullyQualifiedName().compareTo(type.getFullyQualifiedName());
    }

    public String toString() {
        return this.typeMirror.toString();
    }

    public String describe() {
        if (this.loggingVerbose) {
            return toString();
        }
        String strReplaceFirst = getFullyQualifiedName().replaceFirst("^" + getPackageName() + ".", "");
        List<Type> typeParameters = getTypeParameters();
        return typeParameters.isEmpty() ? strReplaceFirst : String.format("%s<%s>", strReplaceFirst, (String) typeParameters.stream().map(new Function() { // from class: org.mapstruct.ap.internal.model.common.Type$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Type) obj).describe();
            }
        }).collect(Collectors.joining(",")));
    }

    public String getIdentification() {
        if (isArrayType()) {
            return this.componentType.getName() + "Array";
        }
        return getTypeBound().getName();
    }

    public Type getTypeBound() {
        Type type = this.boundingBase;
        if (type != null) {
            return type;
        }
        TypeFactory typeFactory = this.typeFactory;
        Type type2 = typeFactory.getType(typeFactory.getTypeBound(getTypeMirror()));
        this.boundingBase = type2;
        return type2;
    }

    public boolean hasAccessibleConstructor() {
        if (this.hasAccessibleConstructor == null) {
            this.hasAccessibleConstructor = false;
            Iterator it = ElementFilter.constructorsIn(this.typeElement.getEnclosedElements()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (!((ExecutableElement) it.next()).getModifiers().contains(Modifier.PRIVATE)) {
                    this.hasAccessibleConstructor = true;
                    break;
                }
            }
        }
        return this.hasAccessibleConstructor.booleanValue();
    }

    public List<Type> determineTypeArguments(Class<?> cls) {
        if (this.qualifiedName.equals(cls.getName())) {
            return getTypeParameters();
        }
        Iterator it = this.typeUtils.directSupertypes(this.typeMirror).iterator();
        while (it.hasNext()) {
            List<Type> listDetermineTypeArguments = this.typeFactory.getType((TypeMirror) it.next()).determineTypeArguments(cls);
            if (listDetermineTypeArguments != null) {
                return listDetermineTypeArguments;
            }
        }
        return null;
    }

    public boolean isNative() {
        return NativeTypes.isNative(this.qualifiedName);
    }

    public boolean isLiteral() {
        return this.isLiteral;
    }

    public Type resolveTypeVarToType(Type type, Type type2) {
        return isTypeVar() ? (Type) new TypeVarMatcher(this.typeUtils, this).visit(type2.getTypeMirror(), type) : this;
    }

    private static class TypeVarMatcher extends SimpleTypeVisitor8<Type, Type> {
        private TypeVariable typeVarToMatch;
        private Types types;

        TypeVarMatcher(Types types, Type type) {
            super((Object) null);
            this.typeVarToMatch = type.getTypeMirror();
            this.types = types;
        }

        public Type visitTypeVariable(TypeVariable typeVariable, Type type) {
            return this.types.isSameType(typeVariable, this.typeVarToMatch) ? type : (Type) super.visitTypeVariable(typeVariable, type);
        }

        public Type visitDeclared(DeclaredType declaredType, Type type) {
            Types types = this.types;
            if (types.isAssignable(types.erasure(declaredType), this.types.erasure(type.getTypeMirror()))) {
                if (declaredType.getTypeArguments().size() != type.getTypeParameters().size()) {
                    return (Type) super.visitDeclared(declaredType, type);
                }
                for (int i = 0; i < declaredType.getTypeArguments().size(); i++) {
                    Type type2 = (Type) visit((TypeMirror) declaredType.getTypeArguments().get(i), type.getTypeParameters().get(i));
                    if (type2 != null) {
                        return type2;
                    }
                }
            }
            return (Type) super.visitDeclared(declaredType, type);
        }
    }

    private String trimSimpleClassName(String str) {
        if (str == null) {
            return null;
        }
        while (str.endsWith(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI)) {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }
}
