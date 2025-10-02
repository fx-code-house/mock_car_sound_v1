package org.mapstruct.ap.internal.model.source.builtin;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class XmlGregorianCalendarToLocalDateTime extends BuiltInMethod {
    private final Set<Type> importTypes;
    private final Parameter parameter;
    private final Type returnType;

    public XmlGregorianCalendarToLocalDateTime(TypeFactory typeFactory) {
        Parameter parameter = new Parameter("xcal", typeFactory.getType(XMLGregorianCalendar.class));
        this.parameter = parameter;
        Type type = typeFactory.getType(LocalDateTime.class);
        this.returnType = type;
        this.importTypes = Collections.asSet(type, parameter.getType(), typeFactory.getType(DatatypeConstants.class), typeFactory.getType(Duration.class));
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
