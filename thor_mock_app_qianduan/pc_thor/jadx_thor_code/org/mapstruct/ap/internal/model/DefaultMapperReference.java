package org.mapstruct.ap.internal.model;

import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public class DefaultMapperReference extends MapperReference {
    private final Set<Type> importTypes;
    private final boolean isAnnotatedMapper;

    private DefaultMapperReference(Type type, boolean z, Set<Type> set, String str) {
        super(type, str);
        this.isAnnotatedMapper = z;
        this.importTypes = set;
    }

    public static DefaultMapperReference getInstance(Type type, boolean z, TypeFactory typeFactory, List<String> list) {
        Set setAsSet = Collections.asSet(type);
        if (z) {
            setAsSet.add(typeFactory.getType("org.mapstruct.factory.Mappers"));
        }
        return new DefaultMapperReference(type, z, setAsSet, Strings.getSafeVariableName(type.getName(), list));
    }

    @Override // org.mapstruct.ap.internal.model.Field, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        return this.importTypes;
    }

    public boolean isAnnotatedMapper() {
        return this.isAnnotatedMapper;
    }
}
