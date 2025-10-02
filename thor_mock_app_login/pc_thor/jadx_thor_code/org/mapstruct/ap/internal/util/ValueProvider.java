package org.mapstruct.ap.internal.util;

import org.mapstruct.ap.internal.util.accessor.Accessor;

/* loaded from: classes3.dex */
public class ValueProvider {
    private final String value;

    private ValueProvider(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }

    public static ValueProvider of(Accessor accessor) {
        if (accessor == null) {
            return null;
        }
        String simpleName = accessor.getSimpleName();
        if (!accessor.getAccessorType().isFieldAssignment()) {
            simpleName = simpleName + "()";
        }
        return new ValueProvider(simpleName);
    }
}
