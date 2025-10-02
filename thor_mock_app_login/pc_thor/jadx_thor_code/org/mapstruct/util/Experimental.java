package org.mapstruct.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes3.dex */
public @interface Experimental {
    String value() default "";
}
