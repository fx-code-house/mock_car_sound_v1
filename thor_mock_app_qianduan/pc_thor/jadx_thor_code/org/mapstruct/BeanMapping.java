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
public @interface BeanMapping {
    Builder builder() default @Builder;

    boolean ignoreByDefault() default false;

    String[] ignoreUnmappedSourceProperties() default {};

    Class<? extends Annotation> mappingControl() default MappingControl.class;

    NullValueCheckStrategy nullValueCheckStrategy() default NullValueCheckStrategy.ON_IMPLICIT_CONVERSION;

    NullValueMappingStrategy nullValueMappingStrategy() default NullValueMappingStrategy.RETURN_NULL;

    NullValuePropertyMappingStrategy nullValuePropertyMappingStrategy() default NullValuePropertyMappingStrategy.SET_TO_NULL;

    Class<? extends Annotation>[] qualifiedBy() default {};

    String[] qualifiedByName() default {};

    Class<?> resultType() default void.class;
}
