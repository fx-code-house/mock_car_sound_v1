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
public @interface MapMapping {
    String keyDateFormat() default "";

    Class<? extends Annotation> keyMappingControl() default MappingControl.class;

    String keyNumberFormat() default "";

    Class<? extends Annotation>[] keyQualifiedBy() default {};

    String[] keyQualifiedByName() default {};

    Class<?> keyTargetType() default void.class;

    NullValueMappingStrategy nullValueMappingStrategy() default NullValueMappingStrategy.RETURN_NULL;

    String valueDateFormat() default "";

    Class<? extends Annotation> valueMappingControl() default MappingControl.class;

    String valueNumberFormat() default "";

    Class<? extends Annotation>[] valueQualifiedBy() default {};

    String[] valueQualifiedByName() default {};

    Class<?> valueTargetType() default void.class;
}
