package org.mapstruct.ap.internal.model.common;

import java.util.Collection;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import org.mapstruct.ap.spi.BuilderInfo;

/* loaded from: classes3.dex */
public class BuilderType {
    private final Collection<ExecutableElement> buildMethods;
    private final Type builder;
    private final ExecutableElement builderCreationMethod;
    private final Type buildingType;
    private final Type owningType;

    private BuilderType(Type type, Type type2, Type type3, ExecutableElement executableElement, Collection<ExecutableElement> collection) {
        this.builder = type;
        this.owningType = type2;
        this.buildingType = type3;
        this.builderCreationMethod = executableElement;
        this.buildMethods = collection;
    }

    public Type getBuilder() {
        return this.builder;
    }

    public Type getOwningType() {
        return this.owningType;
    }

    public Type getBuildingType() {
        return this.buildingType;
    }

    public ExecutableElement getBuilderCreationMethod() {
        return this.builderCreationMethod;
    }

    public Collection<ExecutableElement> getBuildMethods() {
        return this.buildMethods;
    }

    public static BuilderType create(BuilderInfo builderInfo, Type type, TypeFactory typeFactory, Types types) {
        Type type2;
        if (builderInfo == null) {
            return null;
        }
        Type type3 = typeFactory.getType(builderInfo.getBuilderCreationMethod().getReturnType());
        ExecutableElement builderCreationMethod = builderInfo.getBuilderCreationMethod();
        TypeMirror typeMirrorAsType = builderCreationMethod.getEnclosingElement().asType();
        if (types.isSameType(typeMirrorAsType, type.getTypeMirror())) {
            type2 = type;
        } else {
            type2 = types.isSameType(type3.getTypeMirror(), typeMirrorAsType) ? type3 : typeFactory.getType(typeMirrorAsType);
        }
        return new BuilderType(builderInfo.getBuilderCreationMethod().getKind() == ElementKind.CONSTRUCTOR ? type2 : type3, type2, type, builderCreationMethod, builderInfo.getBuildMethods());
    }
}
