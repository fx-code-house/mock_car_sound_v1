package org.mapstruct;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.mapstruct.control.MappingControl;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes3.dex */
public @interface Mapper {
    Builder builder() default @Builder;

    CollectionMappingStrategy collectionMappingStrategy() default CollectionMappingStrategy.ACCESSOR_ONLY;

    String componentModel() default "default";

    Class<?> config() default void.class;

    boolean disableSubMappingMethodsGeneration() default false;

    String implementationName() default "<CLASS_NAME>Impl";

    String implementationPackage() default "<PACKAGE_NAME>";

    Class<?>[] imports() default {};

    InjectionStrategy injectionStrategy() default InjectionStrategy.FIELD;

    Class<? extends Annotation> mappingControl() default MappingControl.class;

    MappingInheritanceStrategy mappingInheritanceStrategy() default MappingInheritanceStrategy.EXPLICIT;

    NullValueCheckStrategy nullValueCheckStrategy() default NullValueCheckStrategy.ON_IMPLICIT_CONVERSION;

    NullValueMappingStrategy nullValueMappingStrategy() default NullValueMappingStrategy.RETURN_NULL;

    NullValuePropertyMappingStrategy nullValuePropertyMappingStrategy() default NullValuePropertyMappingStrategy.SET_TO_NULL;

    ReportingPolicy typeConversionPolicy() default ReportingPolicy.IGNORE;

    Class<? extends Exception> unexpectedValueMappingException() default IllegalArgumentException.class;

    ReportingPolicy unmappedSourcePolicy() default ReportingPolicy.IGNORE;

    ReportingPolicy unmappedTargetPolicy() default ReportingPolicy.WARN;

    Class<?>[] uses() default {};
}
