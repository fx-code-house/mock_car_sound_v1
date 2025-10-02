package org.mapstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes3.dex */
public @interface EnumMapping {
    String configuration() default "";

    String nameTransformationStrategy() default "";

    Class<? extends Exception> unexpectedValueMappingException() default IllegalArgumentException.class;
}
