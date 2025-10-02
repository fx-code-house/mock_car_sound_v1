package org.mapstruct.ap.shaded.freemarker.log;

import java.util.HashMap;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.core.BugException;

/* loaded from: classes3.dex */
public abstract class Logger {
    public static final int LIBRARY_AUTO = -1;
    public static final int LIBRARY_AVALON = 2;
    public static final int LIBRARY_COMMONS = 4;
    public static final int LIBRARY_JAVA = 1;
    public static final int LIBRARY_LOG4J = 3;
    public static final int LIBRARY_NONE = 0;
    public static final int LIBRARY_SLF4J = 5;
    static /* synthetic */ Class class$freemarker$log$Logger;
    private static LoggerFactory factory;
    private static int logLibrary;
    private static final String[] LIBINIT = {"org.mapstruct.ap.shaded.freemarker.log.Logger", "_Null", "java.util.logging.Logger", "_JDK14", "org.apache.log.Logger", "_Avalon", "org.apache.log4j.Logger", "_Log4J", "org.apache.commons.logging.Log", "CommonsLogging", "org.slf4j.Logger", "SLF4J"};
    private static String categoryPrefix = "";
    private static final Map loggers = new HashMap();

    public abstract void debug(String str);

    public abstract void debug(String str, Throwable th);

    public abstract void error(String str);

    public abstract void error(String str, Throwable th);

    public abstract void info(String str);

    public abstract void info(String str, Throwable th);

    public abstract boolean isDebugEnabled();

    public abstract boolean isErrorEnabled();

    public abstract boolean isFatalEnabled();

    public abstract boolean isInfoEnabled();

    public abstract boolean isWarnEnabled();

    public abstract void warn(String str);

    public abstract void warn(String str, Throwable th);

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public static void selectLoggerLibrary(int i) throws Throwable {
        Class clsClass$ = class$freemarker$log$Logger;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.log.Logger");
            class$freemarker$log$Logger = clsClass$;
        }
        synchronized (clsClass$) {
            if (i >= -1) {
                if (i * 2 < LIBINIT.length) {
                    logLibrary = i;
                    factory = createFactory();
                }
            }
            throw new IllegalArgumentException();
        }
    }

    public static void setCategoryPrefix(String str) throws Throwable {
        Class clsClass$ = class$freemarker$log$Logger;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.log.Logger");
            class$freemarker$log$Logger = clsClass$;
        }
        synchronized (clsClass$) {
            try {
                if (str == null) {
                    throw new IllegalArgumentException();
                }
                categoryPrefix = str;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static Logger getLogger(String str) throws Throwable {
        Logger logger;
        if (factory == null) {
            Class clsClass$ = class$freemarker$log$Logger;
            if (clsClass$ == null) {
                clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.log.Logger");
                class$freemarker$log$Logger = clsClass$;
            }
            synchronized (clsClass$) {
                if (factory == null) {
                    try {
                        selectLoggerLibrary(-1);
                    } catch (ClassNotFoundException e) {
                        throw new BugException(e);
                    }
                }
            }
        }
        String string = new StringBuffer().append(categoryPrefix).append(str).toString();
        Map map = loggers;
        synchronized (map) {
            logger = (Logger) map.get(string);
            if (logger == null) {
                logger = factory.getLogger(string);
                map.put(string, logger);
            }
        }
        return logger;
    }

    private static LoggerFactory createFactory() throws ClassNotFoundException {
        int i = logLibrary;
        if (i == -1) {
            for (int length = (LIBINIT.length / 2) - 1; length > 0; length--) {
                if (length != 5 && length != 4) {
                    try {
                        return createFactory(length);
                    } catch (ClassNotFoundException | LinkageError unused) {
                        continue;
                    }
                }
            }
            System.err.println("*** WARNING: FreeMarker logging suppressed.");
            return new _NullLoggerFactory();
        }
        return createFactory(i);
    }

    private static LoggerFactory createFactory(int i) throws ClassNotFoundException {
        String[] strArr = LIBINIT;
        int i2 = i * 2;
        String str = strArr[i2];
        String str2 = strArr[i2 + 1];
        try {
            Class.forName(str);
            return (LoggerFactory) Class.forName(new StringBuffer("org.mapstruct.ap.shaded.freemarker.log.").append(str2).append("LoggerFactory").toString()).newInstance();
        } catch (IllegalAccessException e) {
            throw new IllegalAccessError(e.getMessage());
        } catch (InstantiationException e2) {
            throw new InstantiationError(e2.getMessage());
        }
    }
}
