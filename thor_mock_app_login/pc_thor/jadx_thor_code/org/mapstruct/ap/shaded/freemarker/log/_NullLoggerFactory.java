package org.mapstruct.ap.shaded.freemarker.log;

/* loaded from: classes3.dex */
public class _NullLoggerFactory implements LoggerFactory {
    private static final Logger INSTANCE = new Logger() { // from class: org.mapstruct.ap.shaded.freemarker.log._NullLoggerFactory.1
        @Override // org.mapstruct.ap.shaded.freemarker.log.Logger
        public void debug(String str) {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.log.Logger
        public void debug(String str, Throwable th) {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.log.Logger
        public void error(String str) {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.log.Logger
        public void error(String str, Throwable th) {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.log.Logger
        public void info(String str) {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.log.Logger
        public void info(String str, Throwable th) {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.log.Logger
        public boolean isDebugEnabled() {
            return false;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.log.Logger
        public boolean isErrorEnabled() {
            return false;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.log.Logger
        public boolean isFatalEnabled() {
            return false;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.log.Logger
        public boolean isInfoEnabled() {
            return false;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.log.Logger
        public boolean isWarnEnabled() {
            return false;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.log.Logger
        public void warn(String str) {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.log.Logger
        public void warn(String str, Throwable th) {
        }
    };

    _NullLoggerFactory() {
    }

    @Override // org.mapstruct.ap.shaded.freemarker.log.LoggerFactory
    public Logger getLogger(String str) {
        return INSTANCE;
    }
}
