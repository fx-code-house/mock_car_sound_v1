package org.mapstruct.ap.internal.model.source.builtin;

import java.time.LocalDate;
import java.util.Set;
import javax.xml.datatype.DatatypeConstants;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class LocalDateToXmlGregorianCalendar extends AbstractToXmlGregorianCalendar {
    private final Set<Type> importTypes;
    private final Parameter parameter;

    public LocalDateToXmlGregorianCalendar(TypeFactory typeFactory) {
        super(typeFactory);
        Parameter parameter = new Parameter("localDate", typeFactory.getType(LocalDate.class));
        this.parameter = parameter;
        this.importTypes = Collections.asSet(parameter.getType(), typeFactory.getType(DatatypeConstants.class));
    }

    @Override // org.mapstruct.ap.internal.model.source.builtin.AbstractToXmlGregorianCalendar, org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod
    public Set<Type> getImportTypes() {
        Set<Type> importTypes = super.getImportTypes();
        importTypes.addAll(this.importTypes);
        return importTypes;
    }

    @Override // org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod
    public Parameter getParameter() {
        return this.parameter;
    }
}
