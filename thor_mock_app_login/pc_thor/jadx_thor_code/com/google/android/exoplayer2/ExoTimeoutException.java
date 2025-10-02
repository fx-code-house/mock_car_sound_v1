package com.google.android.exoplayer2;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class ExoTimeoutException extends RuntimeException {
    public static final int TIMEOUT_OPERATION_DETACH_SURFACE = 3;
    public static final int TIMEOUT_OPERATION_RELEASE = 1;
    public static final int TIMEOUT_OPERATION_SET_FOREGROUND_MODE = 2;
    public static final int TIMEOUT_OPERATION_UNDEFINED = 0;
    public final int timeoutOperation;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeoutOperation {
    }

    private static String getErrorMessage(int timeoutOperation) {
        return timeoutOperation != 1 ? timeoutOperation != 2 ? timeoutOperation != 3 ? "Undefined timeout." : "Detaching surface timed out." : "Setting foreground mode timed out." : "Player release timed out.";
    }

    public ExoTimeoutException(int timeoutOperation) {
        super(getErrorMessage(timeoutOperation));
        this.timeoutOperation = timeoutOperation;
    }
}
