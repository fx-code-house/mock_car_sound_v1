package org.mapstruct.ap.shaded.freemarker.core;

/* loaded from: classes3.dex */
public class BugException extends RuntimeException {
    private static final String COMMON_MESSAGE = "A bug was detected in FreeMarker; please report it with stack-trace";

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BugException() {
        this((Throwable) null);
    }

    public BugException(String str) {
        this(str, null);
    }

    public BugException(Throwable th) {
        super(COMMON_MESSAGE, th);
    }

    public BugException(String str, Throwable th) {
        super(new StringBuffer("A bug was detected in FreeMarker; please report it with stack-trace: ").append(str).toString(), th);
    }

    public BugException(int i) {
        this(String.valueOf(i));
    }
}
