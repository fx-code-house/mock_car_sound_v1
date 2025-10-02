package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class NoArgumentConstructor extends ModelElement implements Constructor {
    private final Set<SupportingConstructorFragment> fragments;
    private final String name;

    public NoArgumentConstructor(String str, Set<SupportingConstructorFragment> set) {
        this.name = str;
        this.fragments = set;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    @Override // org.mapstruct.ap.internal.model.Constructor
    public String getName() {
        return this.name;
    }

    public Set<SupportingConstructorFragment> getFragments() {
        return this.fragments;
    }
}
