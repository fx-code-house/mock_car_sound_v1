package org.mapstruct.ap.internal.conversion;

import java.text.DecimalFormat;
import java.util.Set;
import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MappingMethodOptions;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class CreateDecimalFormat extends HelperMethod {
    private final Set<Type> importTypes;
    private final Parameter parameter;
    private final Type returnType;

    @Override // org.mapstruct.ap.internal.model.source.Method
    public String describe() {
        return null;
    }

    public CreateDecimalFormat(TypeFactory typeFactory) {
        Parameter parameter = new Parameter("numberFormat", typeFactory.getType(String.class));
        this.parameter = parameter;
        Type type = typeFactory.getType(DecimalFormat.class);
        this.returnType = type;
        this.importTypes = Collections.asSet(parameter.getType(), type);
    }

    @Override // org.mapstruct.ap.internal.model.HelperMethod
    public Set<Type> getImportTypes() {
        return this.importTypes;
    }

    @Override // org.mapstruct.ap.internal.model.HelperMethod
    public Parameter getParameter() {
        return this.parameter;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Type getReturnType() {
        return this.returnType;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public MappingMethodOptions getOptions() {
        return MappingMethodOptions.empty();
    }
}
