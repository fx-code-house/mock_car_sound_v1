package com.google.android.exoplayer2.upstream.cache;

import java.io.File;
import java.io.IOException;
import java.util.NavigableSet;
import java.util.Set;

/* loaded from: classes.dex */
public interface Cache {
    public static final long UID_UNSET = -1;

    public interface Listener {
        void onSpanAdded(Cache cache, CacheSpan span);

        void onSpanRemoved(Cache cache, CacheSpan span);

        void onSpanTouched(Cache cache, CacheSpan oldSpan, CacheSpan newSpan);
    }

    NavigableSet<CacheSpan> addListener(String key, Listener listener);

    void applyContentMetadataMutations(String key, ContentMetadataMutations mutations) throws CacheException;

    void commitFile(File file, long length) throws CacheException;

    long getCacheSpace();

    long getCachedBytes(String key, long position, long length);

    long getCachedLength(String key, long position, long length);

    NavigableSet<CacheSpan> getCachedSpans(String key);

    ContentMetadata getContentMetadata(String key);

    Set<String> getKeys();

    long getUid();

    boolean isCached(String key, long position, long length);

    void release();

    void releaseHoleSpan(CacheSpan holeSpan);

    void removeListener(String key, Listener listener);

    void removeResource(String key);

    void removeSpan(CacheSpan span);

    File startFile(String key, long position, long length) throws CacheException;

    CacheSpan startReadWrite(String key, long position, long length) throws InterruptedException, CacheException;

    CacheSpan startReadWriteNonBlocking(String key, long position, long length) throws CacheException;

    public static class CacheException extends IOException {
        public CacheException(String message) {
            super(message);
        }

        public CacheException(Throwable cause) {
            super(cause);
        }

        public CacheException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
