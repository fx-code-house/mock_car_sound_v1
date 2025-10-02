package org.mapstruct.ap.internal.model.source.builtin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class StringToXmlGregorianCalendar extends AbstractToXmlGregorianCalendar {
    private final Set<Type> importTypes;
    private final Parameter parameter;

    public StringToXmlGregorianCalendar(TypeFactory typeFactory) {
        super(typeFactory);
        this.parameter = new Parameter("date", typeFactory.getType(String.class));
        this.importTypes = Collections.asSet(typeFactory.getType(GregorianCalendar.class), typeFactory.getType(SimpleDateFormat.class), typeFactory.getType(DateFormat.class), typeFactory.getType(ParseException.class));
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

    @Override // org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod
    public String getContextParameter(ConversionContext conversionContext) {
        return conversionContext.getDateFormat() != null ? "\"" + conversionContext.getDateFormat() + "\"" : "null";
    }
}
