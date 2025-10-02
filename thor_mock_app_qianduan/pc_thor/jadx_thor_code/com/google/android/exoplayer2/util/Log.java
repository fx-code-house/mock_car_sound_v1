package com.google.android.exoplayer2.util;

import android.text.TextUtils;
import java.net.UnknownHostException;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes.dex */
public final class Log {
    public static final int LOG_LEVEL_ALL = 0;
    public static final int LOG_LEVEL_ERROR = 3;
    public static final int LOG_LEVEL_INFO = 1;
    public static final int LOG_LEVEL_OFF = Integer.MAX_VALUE;
    public static final int LOG_LEVEL_WARNING = 2;
    private static int logLevel = 0;
    private static boolean logStackTraces = true;

    private Log() {
    }

    @Pure
    public static int getLogLevel() {
        return logLevel;
    }

    public static void setLogLevel(int logLevel2) {
        logLevel = logLevel2;
    }

    public static void setLogStackTraces(boolean logStackTraces2) {
        logStackTraces = logStackTraces2;
    }

    @Pure
    public static void d(String tag, String message) {
        if (logLevel == 0) {
            android.util.Log.d(tag, message);
        }
    }

    @Pure
    public static void d(String tag, String message, Throwable throwable) {
        d(tag, appendThrowableString(message, throwable));
    }

    @Pure
    public static void i(String tag, String message) {
        if (logLevel <= 1) {
            android.util.Log.i(tag, message);
        }
    }

    @Pure
    public static void i(String tag, String message, Throwable throwable) {
        i(tag, appendThrowableString(message, throwable));
    }

    @Pure
    public static void w(String tag, String message) {
        if (logLevel <= 2) {
            android.util.Log.w(tag, message);
        }
    }

    @Pure
    public static void w(String tag, String message, Throwable throwable) {
        w(tag, appendThrowableString(message, throwable));
    }

    @Pure
    public static void e(String tag, String message) {
        if (logLevel <= 3) {
            android.util.Log.e(tag, message);
        }
    }

    @Pure
    public static void e(String tag, String message, Throwable throwable) {
        e(tag, appendThrowableString(message, throwable));
    }

    @Pure
    public static String getThrowableString(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        if (isCausedByUnknownHostException(throwable)) {
            return "UnknownHostException (no network)";
        }
        if (!logStackTraces) {
            return throwable.getMessage();
        }
        return android.util.Log.getStackTraceString(throwable).trim().replace("\t", "    ");
    }

    @Pure
    private static String appendThrowableString(String message, Throwable throwable) {
        String throwableString = getThrowableString(throwable);
        if (TextUtils.isEmpty(throwableString)) {
            return message;
        }
        String strValueOf = String.valueOf(message);
        String strReplace = throwableString.replace(StringUtils.LF, "\n  ");
        return new StringBuilder(String.valueOf(strValueOf).length() + 4 + String.valueOf(strReplace).length()).append(strValueOf).append("\n  ").append(strReplace).append('\n').toString();
    }

    @Pure
    private static boolean isCausedByUnknownHostException(Throwable throwable) {
        while (throwable != null) {
            if (throwable instanceof UnknownHostException) {
                return true;
            }
            throwable = throwable.getCause();
        }
        return false;
    }
}
