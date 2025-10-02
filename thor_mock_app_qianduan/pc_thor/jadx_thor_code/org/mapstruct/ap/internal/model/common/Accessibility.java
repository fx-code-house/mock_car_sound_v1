package org.mapstruct.ap.internal.model.common;

import java.util.Set;
import javax.lang.model.element.Modifier;

/* loaded from: classes3.dex */
public enum Accessibility {
    PRIVATE("private"),
    DEFAULT(""),
    PROTECTED("protected"),
    PUBLIC("public");

    private final String keyword;

    Accessibility(String str) {
        this.keyword = str;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public static Accessibility fromModifiers(Set<Modifier> set) {
        if (set.contains(Modifier.PUBLIC)) {
            return PUBLIC;
        }
        if (set.contains(Modifier.PROTECTED)) {
            return PROTECTED;
        }
        if (set.contains(Modifier.PRIVATE)) {
            return PRIVATE;
        }
        return DEFAULT;
    }
}
