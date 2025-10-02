package org.mapstruct.ap.shaded.freemarker.template.utility;

/* loaded from: classes3.dex */
public class UnsupportedNumberClassException extends RuntimeException {
    private final Class fClass;

    public UnsupportedNumberClassException(Class cls) {
        super(new StringBuffer("Unsupported number class: ").append(cls.getName()).toString());
        this.fClass = cls;
    }

    public Class getUnsupportedClass() {
        return this.fClass;
    }
}
