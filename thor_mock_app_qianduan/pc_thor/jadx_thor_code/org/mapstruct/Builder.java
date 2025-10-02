package org.mapstruct;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes3.dex */
public @interface Builder {
    String buildMethod() default "build";

    boolean disableBuilder() default false;
}
