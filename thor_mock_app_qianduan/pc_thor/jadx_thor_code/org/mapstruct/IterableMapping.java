package org.mapstruct;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.mapstruct.control.MappingControl;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes3.dex */
public @interface IterableMapping {
    String dateFormat() default "";

    Class<? extends Annotation> elementMappingControl() default MappingControl.class;

    Class<?> elementTargetType() default void.class;

    NullValueMappingStrategy nullValueMappingStrategy() default NullValueMappingStrategy.RETURN_NULL;

    String numberFormat() default "";

    Class<? extends Annotation>[] qualifiedBy() default {};

    String[] qualifiedByName() default {};
}
