package org.mapstruct.ap.internal.model.source.builtin;

import java.util.Set;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.JodaTimeConstants;

/* loaded from: classes3.dex */
public class XmlGregorianCalendarToJodaDateTime extends BuiltInMethod {
    private final Set<Type> importTypes;
    private final Parameter parameter;
    private final Type returnType;

    public XmlGregorianCalendarToJodaDateTime(TypeFactory typeFactory) {
        Parameter parameter = new Parameter("xcal", typeFactory.getType(XMLGregorianCalendar.class));
        this.parameter = parameter;
        Type type = typeFactory.getType(JodaTimeConstants.DATE_TIME_FQN);
        this.returnType = type;
        this.importTypes = Collections.asSet(typeFactory.getType(DatatypeConstants.class), typeFactory.getType(JodaTimeConstants.DATE_TIME_ZONE_FQN), type, parameter.getType());
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
