package org.mapstruct;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.mapstruct.control.MappingControl;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Repeatable(Mappings.class)
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes3.dex */
public @interface Mapping {
    String constant() default "";

    String dateFormat() default "";

    String defaultExpression() default "";

    String defaultValue() default "";

    String[] dependsOn() default {};

    String expression() default "";

    boolean ignore() default false;

    Class<? extends Annotation> mappingControl() default MappingControl.class;

    NullValueCheckStrategy nullValueCheckStrategy() default NullValueCheckStrategy.ON_IMPLICIT_CONVERSION;

    NullValuePropertyMappingStrategy nullValuePropertyMappingStrategy() default NullValuePropertyMappingStrategy.SET_TO_NULL;

    String numberFormat() default "";

    Class<? extends Annotation>[] qualifiedBy() default {};

    String[] qualifiedByName() default {};

    Class<?> resultType() default void.class;

    String source() default "";

    String target();
}
