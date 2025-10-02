package org.mapstruct.ap.internal.model.source.builtin;

import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class ZonedDateTimeToXmlGregorianCalendar extends AbstractToXmlGregorianCalendar {
    private final Set<Type> importTypes;
    private final Parameter parameter;

    public ZonedDateTimeToXmlGregorianCalendar(TypeFactory typeFactory) {
        super(typeFactory);
        Parameter parameter = new Parameter("zdt ", typeFactory.getType(ZonedDateTime.class));
        this.parameter = parameter;
        this.importTypes = Collections.asSet(parameter.getType(), typeFactory.getType(GregorianCalendar.class));
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
