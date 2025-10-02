package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class Annotation extends ModelElement {
    private List<String> properties;
    private final Type type;

    public Annotation(Type type) {
        this(type, Collections.emptyList());
    }

    public Annotation(Type type, List<String> list) {
        this.type = type;
        this.properties = list;
    }

    public Type getType() {
        return this.type;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        return Collections.singleton(this.type);
    }

    public List<String> getProperties() {
        return this.properties;
    }
}
