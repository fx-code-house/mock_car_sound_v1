package org.mapstruct.ap.shaded.freemarker.template.utility;

/* loaded from: classes3.dex */
public class NullArgumentException extends IllegalArgumentException {
    public NullArgumentException(String str) {
        super(new StringBuffer("The \"").append(str).append("\" argument can't be null").toString());
    }

    public static void check(String str, Object obj) {
        if (obj == null) {
            throw new NullArgumentException(str);
        }
    }
}
