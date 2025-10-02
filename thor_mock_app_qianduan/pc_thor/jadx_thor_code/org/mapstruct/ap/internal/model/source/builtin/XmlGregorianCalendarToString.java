package org.mapstruct.ap.internal.model.source.builtin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import javax.xml.datatype.XMLGregorianCalendar;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class XmlGregorianCalendarToString extends BuiltInMethod {
    private final Set<Type> importTypes;
    private final Parameter parameter;
    private final Type returnType;

    public XmlGregorianCalendarToString(TypeFactory typeFactory) {
        Parameter parameter = new Parameter("xcal", typeFactory.getType(XMLGregorianCalendar.class));
        this.parameter = parameter;
        this.returnType = typeFactory.getType(String.class);
        this.importTypes = Collections.asSet(parameter.getType(), typeFactory.getType(Date.class), typeFactory.getType(SimpleDateFormat.class));
    }

    @Override // org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod
    public Set<Type> getImportTypes() {
        return this.importTypes;
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
    public String getContextParameter(ConversionContext conversionContext) {
        return conversionContext.getDateFormat() != null ? "\"" + conversionContext.getDateFormat() + "\"" : "null";
    }
}
