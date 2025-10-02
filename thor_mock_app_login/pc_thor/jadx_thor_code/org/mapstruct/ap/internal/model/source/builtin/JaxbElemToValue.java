package org.mapstruct.ap.internal.model.source.builtin;

import java.util.Set;
import javax.xml.bind.JAXBElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class JaxbElemToValue extends BuiltInMethod {
    private final Set<Type> importTypes;
    private final Parameter parameter;
    private final Type returnType;

    public JaxbElemToValue(TypeFactory typeFactory) {
        Type type = typeFactory.getType(JAXBElement.class);
        Parameter parameter = new Parameter("element", type);
        this.parameter = parameter;
        this.returnType = type.getTypeParameters().get(0);
        this.importTypes = Collections.asSet(parameter.getType());
    }

    @Override // org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod
    public boolean doTypeVarsMatch(Type type, Type type2) {
        if (type.getTypeParameters().size() == 1) {
            return type.getTypeParameters().get(0).isAssignableTo(type2);
        }
        return false;
    }

    @Override // org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod
    public Parameter getParameter() {
        return this.parameter;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Type getReturnType() {
        return this.returnType;
    }

    @Override // org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod
    public Set<Type> getImportTypes() {
        return this.importTypes;
    }
}
