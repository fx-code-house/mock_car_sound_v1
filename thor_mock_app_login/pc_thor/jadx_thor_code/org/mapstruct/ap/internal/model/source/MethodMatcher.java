package org.mapstruct.ap.internal.model.source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.SimpleTypeVisitor6;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

/* loaded from: classes3.dex */
public class MethodMatcher {
    private final SourceMethod candidateMethod;
    private final TypeFactory typeFactory;
    private final Types typeUtils;

    MethodMatcher(Types types, TypeFactory typeFactory, SourceMethod sourceMethod) {
        this.typeUtils = types;
        this.candidateMethod = sourceMethod;
        this.typeFactory = typeFactory;
    }

    boolean matches(List<Type> list, Type type) {
        HashMap map = new HashMap();
        if (this.candidateMethod.getParameters().size() != list.size()) {
            return false;
        }
        int i = 0;
        for (Parameter parameter : this.candidateMethod.getParameters()) {
            int i2 = i + 1;
            Type type2 = list.get(i);
            if (type2 == null || !matchSourceType(type2, parameter.getType(), map)) {
                return false;
            }
            i = i2;
        }
        Parameter targetTypeParameter = this.candidateMethod.getTargetTypeParameter();
        if ((targetTypeParameter != null && !matchSourceType(this.typeFactory.classTypeOf(type), targetTypeParameter.getType(), map)) || !matchResultType(type, map) || this.candidateMethod.getExecutable().getTypeParameters().size() != map.size()) {
            return false;
        }
        for (Map.Entry<TypeVariable, TypeMirror> entry : map.entrySet()) {
            if (!isWithinBounds(entry.getValue(), getTypeParamFromCandidate((TypeMirror) entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    private boolean matchSourceType(Type type, Type type2, Map<TypeVariable, TypeMirror> map) {
        if (isJavaLangObject(type2.getTypeMirror())) {
            return true;
        }
        TypeMatcher typeMatcher = new TypeMatcher(Assignability.VISITED_ASSIGNABLE_FROM, map);
        if (((Boolean) typeMatcher.visit(type2.getTypeMirror(), type.getTypeMirror())).booleanValue()) {
            return true;
        }
        if (type.isPrimitive()) {
            if (((Boolean) typeMatcher.visit(type2.getTypeMirror(), this.typeUtils.boxedClass(type.getTypeMirror()).asType())).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    private boolean matchResultType(Type type, Map<TypeVariable, TypeMirror> map) {
        Assignability assignability;
        Type resultType = this.candidateMethod.getResultType();
        if (isJavaLangObject(resultType.getTypeMirror()) || resultType.isVoid()) {
            return true;
        }
        if (this.candidateMethod.getReturnType().isVoid()) {
            assignability = Assignability.VISITED_ASSIGNABLE_FROM;
        } else {
            assignability = Assignability.VISITED_ASSIGNABLE_TO;
        }
        if (((Boolean) new TypeMatcher(assignability, map).visit(resultType.getTypeMirror(), type.getTypeMirror())).booleanValue()) {
            return true;
        }
        if (type.isPrimitive()) {
            return ((Boolean) new TypeMatcher(assignability, map).visit(resultType.getTypeMirror(), this.typeUtils.boxedClass(type.getTypeMirror()).asType())).booleanValue();
        }
        if (resultType.getTypeMirror().getKind().isPrimitive()) {
            if (((Boolean) new TypeMatcher(assignability, map).visit(this.typeUtils.boxedClass(resultType.getTypeMirror()).asType(), type.getTypeMirror())).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    private boolean isJavaLangObject(TypeMirror typeMirror) {
        return typeMirror.getKind() == TypeKind.DECLARED && ((DeclaredType) typeMirror).asElement().getQualifiedName().contentEquals(Object.class.getName());
    }

    private enum Assignability {
        VISITED_ASSIGNABLE_FROM,
        VISITED_ASSIGNABLE_TO;

        Assignability invert() {
            Assignability assignability = VISITED_ASSIGNABLE_FROM;
            return this == assignability ? VISITED_ASSIGNABLE_TO : assignability;
        }
    }

    private class TypeMatcher extends SimpleTypeVisitor6<Boolean, TypeMirror> {
        private final Assignability assignability;
        private final Map<TypeVariable, TypeMirror> genericTypesMap;
        private final TypeMatcher inverse;

        TypeMatcher(Assignability assignability, Map<TypeVariable, TypeMirror> map) {
            super(Boolean.FALSE);
            this.assignability = assignability;
            this.genericTypesMap = map;
            this.inverse = MethodMatcher.this.new TypeMatcher(this, map);
        }

        TypeMatcher(TypeMatcher typeMatcher, Map<TypeVariable, TypeMirror> map) {
            super(Boolean.FALSE);
            this.assignability = typeMatcher.assignability.invert();
            this.genericTypesMap = map;
            this.inverse = typeMatcher;
        }

        public Boolean visitPrimitive(PrimitiveType primitiveType, TypeMirror typeMirror) {
            return Boolean.valueOf(MethodMatcher.this.typeUtils.isSameType(primitiveType, typeMirror));
        }

        public Boolean visitArray(ArrayType arrayType, TypeMirror typeMirror) {
            if (typeMirror.getKind().equals(TypeKind.ARRAY)) {
                return (Boolean) arrayType.getComponentType().accept(this, ((ArrayType) typeMirror).getComponentType());
            }
            return Boolean.FALSE;
        }

        public Boolean visitDeclared(DeclaredType declaredType, TypeMirror typeMirror) {
            if (typeMirror.getKind() == TypeKind.DECLARED) {
                DeclaredType declaredType2 = (DeclaredType) typeMirror;
                if (rawAssignabilityMatches(declaredType, declaredType2)) {
                    z = false;
                    boolean z = false;
                    if (declaredType.getTypeArguments().size() == declaredType2.getTypeArguments().size()) {
                        for (int i = 0; i < declaredType.getTypeArguments().size(); i++) {
                            if (!((Boolean) visit((TypeMirror) declaredType.getTypeArguments().get(i), declaredType2.getTypeArguments().get(i))).booleanValue()) {
                                return Boolean.FALSE;
                            }
                        }
                        return Boolean.TRUE;
                    }
                    if (this.assignability != Assignability.VISITED_ASSIGNABLE_TO ? !declaredType.getTypeArguments().isEmpty() : !declaredType2.getTypeArguments().isEmpty()) {
                        z = true;
                    }
                    return Boolean.valueOf(z);
                }
                return Boolean.FALSE;
            }
            if (typeMirror.getKind() == TypeKind.WILDCARD) {
                return (Boolean) this.inverse.visit(typeMirror, declaredType);
            }
            return Boolean.FALSE;
        }

        private boolean rawAssignabilityMatches(DeclaredType declaredType, DeclaredType declaredType2) {
            return this.assignability == Assignability.VISITED_ASSIGNABLE_TO ? MethodMatcher.this.typeUtils.isAssignable(toRawType(declaredType), toRawType(declaredType2)) : MethodMatcher.this.typeUtils.isAssignable(toRawType(declaredType2), toRawType(declaredType));
        }

        private DeclaredType toRawType(DeclaredType declaredType) {
            return MethodMatcher.this.typeUtils.getDeclaredType(declaredType.asElement(), new TypeMirror[0]);
        }

        public Boolean visitTypeVariable(TypeVariable typeVariable, TypeMirror typeMirror) {
            if (this.genericTypesMap.containsKey(typeVariable)) {
                return (Boolean) visit(typeMirror, this.genericTypesMap.get(typeVariable));
            }
            TypeMirror lowerBound = typeVariable.getLowerBound();
            TypeMirror upperBound = typeVariable.getUpperBound();
            if ((isNullType(lowerBound) || MethodMatcher.this.typeUtils.isSubtype(lowerBound, typeMirror)) && (isNullType(upperBound) || MethodMatcher.this.typeUtils.isSubtype(typeMirror, upperBound))) {
                this.genericTypesMap.put(typeVariable, typeMirror);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }

        private boolean isNullType(TypeMirror typeMirror) {
            return typeMirror == null || typeMirror.getKind() == TypeKind.NULL;
        }

        public Boolean visitWildcard(WildcardType wildcardType, TypeMirror typeMirror) {
            TypeMirror extendsBound = wildcardType.getExtendsBound();
            boolean z = true;
            if (!isNullType(extendsBound)) {
                int i = AnonymousClass1.$SwitchMap$javax$lang$model$type$TypeKind[extendsBound.getKind().ordinal()];
                if (i == 1) {
                    return (Boolean) visit(extendsBound, typeMirror);
                }
                if (i == 2) {
                    MethodMatcher methodMatcher = MethodMatcher.this;
                    return Boolean.valueOf(methodMatcher.isWithinBounds(typeMirror, methodMatcher.getTypeParamFromCandidate(extendsBound)));
                }
                return Boolean.FALSE;
            }
            TypeMirror superBound = wildcardType.getSuperBound();
            if (!isNullType(superBound)) {
                int i2 = AnonymousClass1.$SwitchMap$javax$lang$model$type$TypeKind[superBound.getKind().ordinal()];
                if (i2 == 1) {
                    if (!MethodMatcher.this.typeUtils.isSubtype(superBound, typeMirror) && !MethodMatcher.this.typeUtils.isSameType(typeMirror, superBound)) {
                        z = false;
                    }
                    return Boolean.valueOf(z);
                }
                if (i2 == 2) {
                    TypeParameterElement typeParamFromCandidate = MethodMatcher.this.getTypeParamFromCandidate(superBound);
                    if (!MethodMatcher.this.isWithinBounds(typeMirror, typeParamFromCandidate)) {
                        return Boolean.FALSE;
                    }
                    TypeMirror typeMirror2 = (TypeMirror) typeParamFromCandidate.getBounds().get(0);
                    if (!MethodMatcher.this.typeUtils.isSubtype(typeMirror2, typeMirror) && !MethodMatcher.this.typeUtils.isSameType(typeMirror, typeMirror2)) {
                        z = false;
                    }
                    return Boolean.valueOf(z);
                }
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }
    }

    /* renamed from: org.mapstruct.ap.internal.model.source.MethodMatcher$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$javax$lang$model$type$TypeKind;

        static {
            int[] iArr = new int[TypeKind.values().length];
            $SwitchMap$javax$lang$model$type$TypeKind = iArr;
            try {
                iArr[TypeKind.DECLARED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$javax$lang$model$type$TypeKind[TypeKind.TYPEVAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TypeParameterElement getTypeParamFromCandidate(TypeMirror typeMirror) {
        for (TypeParameterElement typeParameterElement : this.candidateMethod.getExecutable().getTypeParameters()) {
            if (this.typeUtils.isSameType(typeParameterElement.asType(), typeMirror)) {
                return typeParameterElement;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isWithinBounds(TypeMirror typeMirror, TypeParameterElement typeParameterElement) {
        List<TypeMirror> bounds = typeParameterElement != null ? typeParameterElement.getBounds() : null;
        if (typeMirror == null || bounds == null) {
            return false;
        }
        for (TypeMirror typeMirror2 : bounds) {
            if (typeMirror2.getKind() != TypeKind.DECLARED || !this.typeUtils.isSubtype(typeMirror, typeMirror2)) {
                return false;
            }
        }
        return true;
    }
}
