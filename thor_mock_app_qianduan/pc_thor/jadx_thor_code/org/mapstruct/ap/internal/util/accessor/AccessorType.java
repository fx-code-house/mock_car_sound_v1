package org.mapstruct.ap.internal.util.accessor;

/* loaded from: classes3.dex */
public enum AccessorType {
    PARAMETER,
    FIELD,
    GETTER,
    SETTER,
    ADDER,
    PRESENCE_CHECKER;

    public boolean isFieldAssignment() {
        return this == FIELD || this == PARAMETER;
    }
}
