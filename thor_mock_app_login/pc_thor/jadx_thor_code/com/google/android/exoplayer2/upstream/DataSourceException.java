package com.google.android.exoplayer2.upstream;

import java.io.IOException;

/* loaded from: classes.dex */
public class DataSourceException extends IOException {

    @Deprecated
    public static final int POSITION_OUT_OF_RANGE = 2008;
    public final int reason;

    public static boolean isCausedByPositionOutOfRange(IOException iOException) {
        for (IOException cause = iOException; cause != null; cause = cause.getCause()) {
            if ((cause instanceof DataSourceException) && ((DataSourceException) cause).reason == 2008) {
                return true;
            }
        }
        return false;
    }

    public DataSourceException(int reason) {
        this.reason = reason;
    }

    public DataSourceException(Throwable cause, int reason) {
        super(cause);
        this.reason = reason;
    }

    public DataSourceException(String message, int reason) {
        super(message);
        this.reason = reason;
    }

    public DataSourceException(String message, Throwable cause, int reason) {
        super(message, cause);
        this.reason = reason;
    }
}
