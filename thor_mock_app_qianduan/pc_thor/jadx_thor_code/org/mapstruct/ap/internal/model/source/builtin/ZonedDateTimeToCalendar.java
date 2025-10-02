package org.mapstruct.ap.internal.model.source.builtin;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class ZonedDateTimeToCalendar extends BuiltInMethod {
    private final Set<Type> importedTypes;
    private final Parameter parameter;
    private final Type returnType;

    ZonedDateTimeToCalendar(TypeFactory typeFactory) {
        Type type = typeFactory.getType(Calendar.class);
        this.returnType = type;
        Parameter parameter = new Parameter("dateTime", typeFactory.getType(ZonedDateTime.class));
        this.parameter = parameter;
        this.importedTypes = Collections.asSet(type, parameter.getType(), typeFactory.getType(TimeZone.class));
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
