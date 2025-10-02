package org.mapstruct.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE})
@Repeatable(MappingControls.class)
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes3.dex */
public @interface MappingControl {

    public enum Use {
        BUILT_IN_CONVERSION,
        COMPLEX_MAPPING,
        DIRECT,
        MAPPING_METHOD
    }

    Use value();
}
