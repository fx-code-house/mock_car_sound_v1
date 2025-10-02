package com.thor.app.utils.logs.loggers;

/* loaded from: classes3.dex */
public interface Logger {
    void d(String message, Object... args);

    void d(Throwable t);

    void d(Throwable t, String message, Object... args);

    void e(String message, Object... args);

    void e(Throwable t);

    void e(Throwable t, String message, Object... args);

    String format(String message, Object... args);

    void i(String message, Object... args);

    void i(Throwable t);

    void i(Throwable t, String message, Object... args);

    void v(String message, Object... args);

    void v(Throwable t);

    void v(Throwable t, String message, Object... args);

    void w(String message, Object... args);

    void w(Throwable t);

    void w(Throwable t, String message, Object... args);
}
