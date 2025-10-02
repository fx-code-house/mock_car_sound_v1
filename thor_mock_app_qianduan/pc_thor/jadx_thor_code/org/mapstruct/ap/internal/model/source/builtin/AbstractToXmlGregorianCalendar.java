package org.mapstruct.ap.internal.model.source.builtin;

import java.util.Set;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public abstract class AbstractToXmlGregorianCalendar extends BuiltInMethod {
    private final Type dataTypeFactoryType;
    private final Set<Type> importTypes;
    private final Type returnType;

    public AbstractToXmlGregorianCalendar(TypeFactory typeFactory) {
        Type type = typeFactory.getType(XMLGregorianCalendar.class);
        this.returnType = type;
        Type type2 = typeFactory.getType(DatatypeFactory.class);
        this.dataTypeFactoryType = type2;
        this.importTypes = Collections.asSet(type, type2, typeFactory.getType(DatatypeConfigurationException.class));
    }

    @Override // org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod
    public Set<Type> getImportTypes() {
        return this.importTypes;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Type getReturnType() {
        return this.returnType;
    }

    @Override // org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod
    public BuiltInFieldReference getFieldReference() {
        return new FinalField(this.dataTypeFactoryType, Strings.decapitalize(DatatypeFactory.class.getSimpleName()));
    }

    @Override // org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod
    public BuiltInConstructorFragment getConstructorFragment() {
        return new NewDatatypeFactoryConstructorFragment();
    }
}
