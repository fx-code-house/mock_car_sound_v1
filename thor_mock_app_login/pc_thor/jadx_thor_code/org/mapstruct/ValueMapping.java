package org.mapstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Repeatable(ValueMappings.class)
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes3.dex */
public @interface ValueMapping {
    String source();

    String target();
}
