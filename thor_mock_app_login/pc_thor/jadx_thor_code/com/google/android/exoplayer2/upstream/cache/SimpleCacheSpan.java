package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
final class SimpleCacheSpan extends CacheSpan {
    private static final Pattern CACHE_FILE_PATTERN_V1 = Pattern.compile("^(.+)\\.(\\d+)\\.(\\d+)\\.v1\\.exo$", 32);
    private static final Pattern CACHE_FILE_PATTERN_V2 = Pattern.compile("^(.+)\\.(\\d+)\\.(\\d+)\\.v2\\.exo$", 32);
    private static final Pattern CACHE_FILE_PATTERN_V3 = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)\\.v3\\.exo$", 32);
    static final String COMMON_SUFFIX = ".exo";
    private static final String SUFFIX = ".v3.exo";

    public static File getCacheFile(File cacheDir, int id, long position, long timestamp) {
        return new File(cacheDir, new StringBuilder(60).append(id).append(".").append(position).append(".").append(timestamp).append(SUFFIX).toString());
    }

    public static SimpleCacheSpan createLookup(String key, long position) {
        return new SimpleCacheSpan(key, position, -1L, C.TIME_UNSET, null);
    }

    public static SimpleCacheSpan createHole(String key, long position, long length) {
        return new SimpleCacheSpan(key, position, length, C.TIME_UNSET, null);
    }

    public static SimpleCacheSpan createCacheEntry(File file, long length, CachedContentIndex index) {
        return createCacheEntry(file, length, C.TIME_UNSET, index);
    }

    public static SimpleCacheSpan createCacheEntry(File file, long length, long lastTouchTimestamp, CachedContentIndex index) {
        File file2;
        String keyForId;
        String name = file.getName();
        if (name.endsWith(SUFFIX)) {
            file2 = file;
        } else {
            File fileUpgradeFile = upgradeFile(file, index);
            if (fileUpgradeFile == null) {
                return null;
            }
            file2 = fileUpgradeFile;
            name = fileUpgradeFile.getName();
        }
        Matcher matcher = CACHE_FILE_PATTERN_V3.matcher(name);
        if (!matcher.matches() || (keyForId = index.getKeyForId(Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1))))) == null) {
            return null;
        }
        long length2 = length == -1 ? file2.length() : length;
        if (length2 == 0) {
            return null;
        }
        return new SimpleCacheSpan(keyForId, Long.parseLong((String) Assertions.checkNotNull(matcher.group(2))), length2, lastTouchTimestamp == C.TIME_UNSET ? Long.parseLong((String) Assertions.checkNotNull(matcher.group(3))) : lastTouchTimestamp, file2);
    }

    private static File upgradeFile(File file, CachedContentIndex index) {
        String strUnescapeFileName;
        String name = file.getName();
        Matcher matcher = CACHE_FILE_PATTERN_V2.matcher(name);
        if (matcher.matches()) {
            strUnescapeFileName = Util.unescapeFileName((String) Assertions.checkNotNull(matcher.group(1)));
        } else {
            matcher = CACHE_FILE_PATTERN_V1.matcher(name);
            strUnescapeFileName = matcher.matches() ? (String) Assertions.checkNotNull(matcher.group(1)) : null;
        }
        if (strUnescapeFileName == null) {
            return null;
        }
        File cacheFile = getCacheFile((File) Assertions.checkStateNotNull(file.getParentFile()), index.assignIdForKey(strUnescapeFileName), Long.parseLong((String) Assertions.checkNotNull(matcher.group(2))), Long.parseLong((String) Assertions.checkNotNull(matcher.group(3))));
        if (file.renameTo(cacheFile)) {
            return cacheFile;
        }
        return null;
    }

    private SimpleCacheSpan(String key, long position, long length, long lastTouchTimestamp, File file) {
        super(key, position, length, lastTouchTimestamp, file);
    }

    public SimpleCacheSpan copyWithFileAndLastTouchTimestamp(File file, long lastTouchTimestamp) {
        Assertions.checkState(this.isCached);
        return new SimpleCacheSpan(this.key, this.position, this.length, lastTouchTimestamp, file);
    }
}
