package org.mapstruct.ap.internal.model.source.builtin;

import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class FinalField implements BuiltInFieldReference {
    private final Type type;
    private String variableName;

    public FinalField(Type type, String str) {
        this.type = type;
        this.variableName = str;
    }

    @Override // org.mapstruct.ap.internal.model.source.builtin.BuiltInFieldReference
    public String getVariableName() {
        return this.variableName;
    }

    @Override // org.mapstruct.ap.internal.model.source.builtin.BuiltInFieldReference
    public Type getType() {
        return this.type;
    }
}
