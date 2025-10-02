package org.mapstruct.ap.internal.model.source.builtin;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class CalendarToZonedDateTime extends BuiltInMethod {
    private final Set<Type> importedTypes;
    private final Parameter parameter;
    private final Type returnType;

    CalendarToZonedDateTime(TypeFactory typeFactory) {
        Type type = typeFactory.getType(ZonedDateTime.class);
        this.returnType = type;
        Parameter parameter = new Parameter("cal", typeFactory.getType(Calendar.class));
        this.parameter = parameter;
        this.importedTypes = Collections.asSet(type, parameter.getType());
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
        return this.importedTypes;
    }
}
