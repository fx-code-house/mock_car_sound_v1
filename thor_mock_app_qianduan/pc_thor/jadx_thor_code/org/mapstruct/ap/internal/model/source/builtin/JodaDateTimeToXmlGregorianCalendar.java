package org.mapstruct.ap.internal.model.source.builtin;

import java.util.Set;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.JodaTimeConstants;

/* loaded from: classes3.dex */
public class JodaDateTimeToXmlGregorianCalendar extends AbstractToXmlGregorianCalendar {
    private final Parameter parameter;

    public JodaDateTimeToXmlGregorianCalendar(TypeFactory typeFactory) {
        super(typeFactory);
        this.parameter = new Parameter("dt", typeFactory.getType(JodaTimeConstants.DATE_TIME_FQN));
    }

    @Override // org.mapstruct.ap.internal.model.source.builtin.AbstractToXmlGregorianCalendar, org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod
    public Set<Type> getImportTypes() {
        Set<Type> importTypes = super.getImportTypes();
        importTypes.add(this.parameter.getType());
        return importTypes;
    }

    @Override // org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod
    public Parameter getParameter() {
        return this.parameter;
    }
}
