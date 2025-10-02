package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class DecoratorConstructor extends ModelElement implements Constructor {
    private final String delegateName;
    private final boolean invokeSuperConstructor;
    private final String name;

    public DecoratorConstructor(String str, String str2, boolean z) {
        this.name = str;
        this.delegateName = str2;
        this.invokeSuperConstructor = z;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    @Override // org.mapstruct.ap.internal.model.Constructor
    public String getName() {
        return this.name;
    }

    public String getDelegateName() {
        return this.delegateName;
    }

    public boolean isInvokeSuperConstructor() {
        return this.invokeSuperConstructor;
    }
}
