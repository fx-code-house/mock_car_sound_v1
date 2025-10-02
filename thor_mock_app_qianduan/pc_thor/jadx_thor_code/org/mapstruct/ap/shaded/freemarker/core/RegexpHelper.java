package org.mapstruct.ap.shaded.freemarker.core;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import okhttp3.internal.ws.WebSocketProtocol;
import org.mapstruct.ap.shaded.freemarker.cache.MruCacheStorage;
import org.mapstruct.ap.shaded.freemarker.log.Logger;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
final class RegexpHelper {
    private static final Logger LOG;
    private static final int MAX_FLAG_WARNINGS_LOGGED = 25;
    static final long RE_FLAG_CASE_INSENSITIVE;
    static final long RE_FLAG_COMMENTS;
    static final long RE_FLAG_DOTALL;
    static final long RE_FLAG_FIRST_ONLY = 8589934592L;
    static final long RE_FLAG_MULTILINE;
    static final long RE_FLAG_REGEXP = 4294967296L;
    private static int flagWarningsCnt;
    private static final Object flagWarningsCntSync;
    private static volatile boolean flagWarningsEnabled;
    private static final MruCacheStorage patternCache;

    private static long intFlagToLong(int i) {
        return i & WebSocketProtocol.PAYLOAD_SHORT_MAX;
    }

    static {
        Logger logger = Logger.getLogger("org.mapstruct.ap.shaded.freemarker.runtime");
        LOG = logger;
        flagWarningsEnabled = logger.isWarnEnabled();
        flagWarningsCntSync = new Object();
        patternCache = new MruCacheStorage(50, 150);
        RE_FLAG_CASE_INSENSITIVE = intFlagToLong(2);
        RE_FLAG_MULTILINE = intFlagToLong(8);
        RE_FLAG_COMMENTS = intFlagToLong(4);
        RE_FLAG_DOTALL = intFlagToLong(32);
    }

    private RegexpHelper() {
    }

    static Pattern getPattern(String str, int i) throws TemplateModelException {
        Pattern pattern;
        PatternCacheKey patternCacheKey = new PatternCacheKey(str, i);
        MruCacheStorage mruCacheStorage = patternCache;
        synchronized (mruCacheStorage) {
            pattern = (Pattern) mruCacheStorage.get(patternCacheKey);
        }
        if (pattern != null) {
            return pattern;
        }
        try {
            Pattern patternCompile = Pattern.compile(str, i);
            synchronized (mruCacheStorage) {
                mruCacheStorage.put(patternCacheKey, patternCompile);
            }
            return patternCompile;
        } catch (PatternSyntaxException e) {
            throw new _TemplateModelException(e, new Object[]{"Malformed regular expression: ", new _DelayedGetMessage(e)});
        }
    }

    private static class PatternCacheKey {
        private final int flags;
        private final int hashCode;
        private final String patternString;

        public PatternCacheKey(String str, int i) {
            this.patternString = str;
            this.flags = i;
            this.hashCode = str.hashCode() + (i * 31);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof PatternCacheKey)) {
                return false;
            }
            PatternCacheKey patternCacheKey = (PatternCacheKey) obj;
            return patternCacheKey.flags == this.flags && patternCacheKey.patternString.equals(this.patternString);
        }

        public int hashCode() {
            return this.hashCode;
        }
    }

    static long parseFlagString(String str) {
        long j;
        long j2 = 0;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == 'c') {
                j = RE_FLAG_COMMENTS;
            } else if (cCharAt == 'f') {
                j = RE_FLAG_FIRST_ONLY;
            } else if (cCharAt == 'i') {
                j = RE_FLAG_CASE_INSENSITIVE;
            } else if (cCharAt == 'm') {
                j = RE_FLAG_MULTILINE;
            } else if (cCharAt == 'r') {
                j = RE_FLAG_REGEXP;
            } else if (cCharAt == 's') {
                j = RE_FLAG_DOTALL;
            } else {
                if (flagWarningsEnabled) {
                    logFlagWarning(new StringBuffer("Unrecognized regular expression flag: ").append(StringUtil.jQuote(String.valueOf(cCharAt))).append(".").toString());
                }
            }
            j2 |= j;
        }
        return j2;
    }

    static void logFlagWarning(String str) {
        if (flagWarningsEnabled) {
            synchronized (flagWarningsCntSync) {
                int i = flagWarningsCnt;
                if (i < 25) {
                    flagWarningsCnt = i + 1;
                    String string = new StringBuffer().append(str).append(" This will be an error in some later FreeMarker version!").toString();
                    if (i + 1 == 25) {
                        string = new StringBuffer().append(string).append(" [Will not log more regular expression flag problems until restart!]").toString();
                    }
                    LOG.warn(string);
                    return;
                }
                flagWarningsEnabled = false;
            }
        }
    }

    static void checkNonRegexpFlags(String str, long j) throws _TemplateModelException {
        checkNonRegexpFlags(str, j, false);
    }

    static void checkNonRegexpFlags(String str, long j, boolean z) throws _TemplateModelException {
        String str2;
        if (z || flagWarningsEnabled) {
            if ((RE_FLAG_MULTILINE & j) != 0) {
                str2 = "m";
            } else if ((RE_FLAG_DOTALL & j) != 0) {
                str2 = "s";
            } else if ((j & RE_FLAG_COMMENTS) == 0) {
                return;
            } else {
                str2 = "c";
            }
            Object[] objArr = {"?", str, " doesn't support the \"", str2, "\" flag without the \"r\" flag."};
            if (z) {
                throw new _TemplateModelException(objArr);
            }
            logFlagWarning(new _ErrorDescriptionBuilder(objArr).toString());
        }
    }
}
