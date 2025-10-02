package org.mapstruct.ap.internal.model.source;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public final class MappingMethodUtils {
    private MappingMethodUtils() {
    }

    public static boolean isEnumMapping(Method method) {
        if (method.getSourceParameters().size() != 1) {
            return false;
        }
        Type type = ((Parameter) Collections.first(method.getSourceParameters())).getType();
        Type resultType = method.getResultType();
        if (type.isEnumType() && resultType.isEnumType()) {
            return true;
        }
        if (type.isString() && resultType.isEnumType()) {
            return true;
        }
        return type.isEnumType() && resultType.isString();
    }
}
