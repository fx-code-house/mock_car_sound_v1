package com.google.android.exoplayer2.upstream.cache;

import android.database.SQLException;
import android.os.ConditionVariable;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes.dex */
public final class SimpleCache implements Cache {
    private static final int SUBDIRECTORY_COUNT = 10;
    private static final String TAG = "SimpleCache";
    private static final String UID_FILE_SUFFIX = ".uid";
    private static final HashSet<File> lockedCacheDirs = new HashSet<>();
    private final File cacheDir;
    private final CachedContentIndex contentIndex;
    private final CacheEvictor evictor;
    private final CacheFileMetadataIndex fileIndex;
    private Cache.CacheException initializationException;
    private final HashMap<String, ArrayList<Cache.Listener>> listeners;
    private final Random random;
    private boolean released;
    private long totalSpace;
    private final boolean touchCacheSpans;
    private long uid;

    public static synchronized boolean isCacheFolderLocked(File cacheFolder) {
        return lockedCacheDirs.contains(cacheFolder.getAbsoluteFile());
    }

    public static void delete(File cacheDir, DatabaseProvider databaseProvider) {
        if (cacheDir.exists()) {
            File[] fileArrListFiles = cacheDir.listFiles();
            if (fileArrListFiles == null) {
                cacheDir.delete();
                return;
            }
            if (databaseProvider != null) {
                long jLoadUid = loadUid(fileArrListFiles);
                if (jLoadUid != -1) {
                    try {
                        CacheFileMetadataIndex.delete(databaseProvider, jLoadUid);
                    } catch (DatabaseIOException unused) {
                        Log.w(TAG, new StringBuilder(52).append("Failed to delete file metadata: ").append(jLoadUid).toString());
                    }
                    try {
                        CachedContentIndex.delete(databaseProvider, jLoadUid);
                    } catch (DatabaseIOException unused2) {
                        Log.w(TAG, new StringBuilder(52).append("Failed to delete file metadata: ").append(jLoadUid).toString());
                    }
                }
            }
            Util.recursiveDelete(cacheDir);
        }
    }

    @Deprecated
    public SimpleCache(File cacheDir, CacheEvictor evictor) {
        this(cacheDir, evictor, (byte[]) null, false);
    }

    @Deprecated
    public SimpleCache(File cacheDir, CacheEvictor evictor, byte[] secretKey) {
        this(cacheDir, evictor, secretKey, secretKey != null);
    }

    @Deprecated
    public SimpleCache(File cacheDir, CacheEvictor evictor, byte[] secretKey, boolean encrypt) {
        this(cacheDir, evictor, null, secretKey, encrypt, true);
    }

    public SimpleCache(File cacheDir, CacheEvictor evictor, DatabaseProvider databaseProvider) {
        this(cacheDir, evictor, databaseProvider, null, false, false);
    }

    public SimpleCache(File cacheDir, CacheEvictor evictor, DatabaseProvider databaseProvider, byte[] legacyIndexSecretKey, boolean legacyIndexEncrypt, boolean preferLegacyIndex) {
        this(cacheDir, evictor, new CachedContentIndex(databaseProvider, cacheDir, legacyIndexSecretKey, legacyIndexEncrypt, preferLegacyIndex), (databaseProvider == null || preferLegacyIndex) ? null : new CacheFileMetadataIndex(databaseProvider));
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [com.google.android.exoplayer2.upstream.cache.SimpleCache$1] */
    SimpleCache(File cacheDir, CacheEvictor evictor, CachedContentIndex contentIndex, CacheFileMetadataIndex fileIndex) {
        if (!lockFolder(cacheDir)) {
            String strValueOf = String.valueOf(cacheDir);
            throw new IllegalStateException(new StringBuilder(String.valueOf(strValueOf).length() + 46).append("Another SimpleCache instance uses the folder: ").append(strValueOf).toString());
        }
        this.cacheDir = cacheDir;
        this.evictor = evictor;
        this.contentIndex = contentIndex;
        this.fileIndex = fileIndex;
        this.listeners = new HashMap<>();
        this.random = new Random();
        this.touchCacheSpans = evictor.requiresCacheSpanTouches();
        this.uid = -1L;
        final ConditionVariable conditionVariable = new ConditionVariable();
        new Thread("ExoPlayer:SimpleCacheInit") { // from class: com.google.android.exoplayer2.upstream.cache.SimpleCache.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                synchronized (SimpleCache.this) {
                    conditionVariable.open();
                    SimpleCache.this.initialize();
                    SimpleCache.this.evictor.onCacheInitialized();
                }
            }
        }.start();
        conditionVariable.block();
    }

    public synchronized void checkInitialization() throws Cache.CacheException {
        Cache.CacheException cacheException = this.initializationException;
        if (cacheException != null) {
            throw cacheException;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized long getUid() {
        return this.uid;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void release() {
        if (this.released) {
            return;
        }
        this.listeners.clear();
        removeStaleSpans();
        try {
            try {
                this.contentIndex.store();
                unlockFolder(this.cacheDir);
            } catch (IOException e) {
                Log.e(TAG, "Storing index file failed", e);
                unlockFolder(this.cacheDir);
            }
            this.released = true;
        } catch (Throwable th) {
            unlockFolder(this.cacheDir);
            this.released = true;
            throw th;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized NavigableSet<CacheSpan> addListener(String key, Cache.Listener listener) {
        Assertions.checkState(!this.released);
        Assertions.checkNotNull(key);
        Assertions.checkNotNull(listener);
        ArrayList<Cache.Listener> arrayList = this.listeners.get(key);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.listeners.put(key, arrayList);
        }
        arrayList.add(listener);
        return getCachedSpans(key);
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void removeListener(String key, Cache.Listener listener) {
        if (this.released) {
            return;
        }
        ArrayList<Cache.Listener> arrayList = this.listeners.get(key);
        if (arrayList != null) {
            arrayList.remove(listener);
            if (arrayList.isEmpty()) {
                this.listeners.remove(key);
            }
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized NavigableSet<CacheSpan> getCachedSpans(String key) {
        TreeSet treeSet;
        Assertions.checkState(!this.released);
        CachedContent cachedContent = this.contentIndex.get(key);
        if (cachedContent == null || cachedContent.isEmpty()) {
            treeSet = new TreeSet();
        } else {
            treeSet = new TreeSet((Collection) cachedContent.getSpans());
        }
        return treeSet;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized Set<String> getKeys() {
        Assertions.checkState(!this.released);
        return new HashSet(this.contentIndex.getKeys());
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized long getCacheSpace() {
        Assertions.checkState(!this.released);
        return this.totalSpace;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized CacheSpan startReadWrite(String key, long position, long length) throws InterruptedException, Cache.CacheException {
        CacheSpan cacheSpanStartReadWriteNonBlocking;
        Assertions.checkState(!this.released);
        checkInitialization();
        while (true) {
            cacheSpanStartReadWriteNonBlocking = startReadWriteNonBlocking(key, position, length);
            if (cacheSpanStartReadWriteNonBlocking == null) {
                wait();
            }
        }
        return cacheSpanStartReadWriteNonBlocking;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized CacheSpan startReadWriteNonBlocking(String key, long position, long length) throws Cache.CacheException {
        Assertions.checkState(!this.released);
        checkInitialization();
        SimpleCacheSpan span = getSpan(key, position, length);
        if (span.isCached) {
            return touchSpan(key, span);
        }
        if (this.contentIndex.getOrAdd(key).lockRange(position, span.length)) {
            return span;
        }
        return null;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized File startFile(String key, long position, long length) throws Cache.CacheException {
        CachedContent cachedContent;
        File file;
        Assertions.checkState(!this.released);
        checkInitialization();
        cachedContent = this.contentIndex.get(key);
        Assertions.checkNotNull(cachedContent);
        Assertions.checkState(cachedContent.isFullyLocked(position, length));
        if (!this.cacheDir.exists()) {
            createCacheDirectories(this.cacheDir);
            removeStaleSpans();
        }
        this.evictor.onStartFile(this, key, position, length);
        file = new File(this.cacheDir, Integer.toString(this.random.nextInt(10)));
        if (!file.exists()) {
            createCacheDirectories(file);
        }
        return SimpleCacheSpan.getCacheFile(file, cachedContent.id, position, System.currentTimeMillis());
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void commitFile(File file, long length) throws Cache.CacheException {
        boolean z = true;
        Assertions.checkState(!this.released);
        if (file.exists()) {
            if (length == 0) {
                file.delete();
                return;
            }
            SimpleCacheSpan simpleCacheSpan = (SimpleCacheSpan) Assertions.checkNotNull(SimpleCacheSpan.createCacheEntry(file, length, this.contentIndex));
            CachedContent cachedContent = (CachedContent) Assertions.checkNotNull(this.contentIndex.get(simpleCacheSpan.key));
            Assertions.checkState(cachedContent.isFullyLocked(simpleCacheSpan.position, simpleCacheSpan.length));
            long contentLength = ContentMetadata.getContentLength(cachedContent.getMetadata());
            if (contentLength != -1) {
                if (simpleCacheSpan.position + simpleCacheSpan.length > contentLength) {
                    z = false;
                }
                Assertions.checkState(z);
            }
            if (this.fileIndex != null) {
                try {
                    this.fileIndex.set(file.getName(), simpleCacheSpan.length, simpleCacheSpan.lastTouchTimestamp);
                    addSpan(simpleCacheSpan);
                    try {
                        this.contentIndex.store();
                        notifyAll();
                        return;
                    } catch (IOException e) {
                        throw new Cache.CacheException(e);
                    }
                } catch (IOException e2) {
                    throw new Cache.CacheException(e2);
                }
            }
            addSpan(simpleCacheSpan);
            this.contentIndex.store();
            notifyAll();
            return;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void releaseHoleSpan(CacheSpan holeSpan) {
        Assertions.checkState(!this.released);
        CachedContent cachedContent = (CachedContent) Assertions.checkNotNull(this.contentIndex.get(holeSpan.key));
        cachedContent.unlockRange(holeSpan.position);
        this.contentIndex.maybeRemove(cachedContent.key);
        notifyAll();
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void removeResource(String key) {
        Assertions.checkState(!this.released);
        Iterator<CacheSpan> it = getCachedSpans(key).iterator();
        while (it.hasNext()) {
            removeSpanInternal(it.next());
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void removeSpan(CacheSpan span) {
        Assertions.checkState(!this.released);
        removeSpanInternal(span);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001e  */
    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized boolean isCached(java.lang.String r4, long r5, long r7) {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.released     // Catch: java.lang.Throwable -> L21
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L9
            r0 = r1
            goto La
        L9:
            r0 = r2
        La:
            com.google.android.exoplayer2.util.Assertions.checkState(r0)     // Catch: java.lang.Throwable -> L21
            com.google.android.exoplayer2.upstream.cache.CachedContentIndex r0 = r3.contentIndex     // Catch: java.lang.Throwable -> L21
            com.google.android.exoplayer2.upstream.cache.CachedContent r4 = r0.get(r4)     // Catch: java.lang.Throwable -> L21
            if (r4 == 0) goto L1e
            long r4 = r4.getCachedBytesLength(r5, r7)     // Catch: java.lang.Throwable -> L21
            int r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r4 < 0) goto L1e
            goto L1f
        L1e:
            r1 = r2
        L1f:
            monitor-exit(r3)
            return r1
        L21:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.SimpleCache.isCached(java.lang.String, long, long):boolean");
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized long getCachedLength(String key, long position, long length) {
        CachedContent cachedContent;
        Assertions.checkState(!this.released);
        if (length == -1) {
            length = Long.MAX_VALUE;
        }
        cachedContent = this.contentIndex.get(key);
        return cachedContent != null ? cachedContent.getCachedBytesLength(position, length) : -length;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized long getCachedBytes(String key, long position, long length) {
        long j;
        long j2 = length == -1 ? Long.MAX_VALUE : position + length;
        long j3 = j2 < 0 ? Long.MAX_VALUE : j2;
        long j4 = position;
        j = 0;
        while (j4 < j3) {
            long cachedLength = getCachedLength(key, j4, j3 - j4);
            if (cachedLength > 0) {
                j += cachedLength;
            } else {
                cachedLength = -cachedLength;
            }
            j4 += cachedLength;
        }
        return j;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void applyContentMetadataMutations(String key, ContentMetadataMutations mutations) throws Cache.CacheException {
        Assertions.checkState(!this.released);
        checkInitialization();
        this.contentIndex.applyContentMetadataMutations(key, mutations);
        try {
            this.contentIndex.store();
        } catch (IOException e) {
            throw new Cache.CacheException(e);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized ContentMetadata getContentMetadata(String key) {
        Assertions.checkState(!this.released);
        return this.contentIndex.getContentMetadata(key);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initialize() {
        if (!this.cacheDir.exists()) {
            try {
                createCacheDirectories(this.cacheDir);
            } catch (Cache.CacheException e) {
                this.initializationException = e;
                return;
            }
        }
        File[] fileArrListFiles = this.cacheDir.listFiles();
        if (fileArrListFiles == null) {
            String strValueOf = String.valueOf(this.cacheDir);
            String string = new StringBuilder(String.valueOf(strValueOf).length() + 38).append("Failed to list cache directory files: ").append(strValueOf).toString();
            Log.e(TAG, string);
            this.initializationException = new Cache.CacheException(string);
            return;
        }
        long jLoadUid = loadUid(fileArrListFiles);
        this.uid = jLoadUid;
        if (jLoadUid == -1) {
            try {
                this.uid = createUid(this.cacheDir);
            } catch (IOException e2) {
                String strValueOf2 = String.valueOf(this.cacheDir);
                String string2 = new StringBuilder(String.valueOf(strValueOf2).length() + 28).append("Failed to create cache UID: ").append(strValueOf2).toString();
                Log.e(TAG, string2, e2);
                this.initializationException = new Cache.CacheException(string2, e2);
                return;
            }
        }
        try {
            this.contentIndex.initialize(this.uid);
            CacheFileMetadataIndex cacheFileMetadataIndex = this.fileIndex;
            if (cacheFileMetadataIndex != null) {
                cacheFileMetadataIndex.initialize(this.uid);
                Map<String, CacheFileMetadata> all = this.fileIndex.getAll();
                loadDirectory(this.cacheDir, true, fileArrListFiles, all);
                this.fileIndex.removeAll(all.keySet());
            } else {
                loadDirectory(this.cacheDir, true, fileArrListFiles, null);
            }
            this.contentIndex.removeEmpty();
            try {
                this.contentIndex.store();
            } catch (IOException e3) {
                Log.e(TAG, "Storing index file failed", e3);
            }
        } catch (IOException e4) {
            String strValueOf3 = String.valueOf(this.cacheDir);
            String string3 = new StringBuilder(String.valueOf(strValueOf3).length() + 36).append("Failed to initialize cache indices: ").append(strValueOf3).toString();
            Log.e(TAG, string3, e4);
            this.initializationException = new Cache.CacheException(string3, e4);
        }
    }

    private void loadDirectory(File directory, boolean isRoot, File[] files, Map<String, CacheFileMetadata> fileMetadata) {
        long j;
        long j2;
        if (files == null || files.length == 0) {
            if (isRoot) {
                return;
            }
            directory.delete();
            return;
        }
        for (File file : files) {
            String name = file.getName();
            if (isRoot && name.indexOf(46) == -1) {
                loadDirectory(file, false, file.listFiles(), fileMetadata);
            } else if (!isRoot || (!CachedContentIndex.isIndexFile(name) && !name.endsWith(UID_FILE_SUFFIX))) {
                CacheFileMetadata cacheFileMetadataRemove = fileMetadata != null ? fileMetadata.remove(name) : null;
                if (cacheFileMetadataRemove != null) {
                    j2 = cacheFileMetadataRemove.length;
                    j = cacheFileMetadataRemove.lastTouchTimestamp;
                } else {
                    j = -9223372036854775807L;
                    j2 = -1;
                }
                SimpleCacheSpan simpleCacheSpanCreateCacheEntry = SimpleCacheSpan.createCacheEntry(file, j2, j, this.contentIndex);
                if (simpleCacheSpanCreateCacheEntry != null) {
                    addSpan(simpleCacheSpanCreateCacheEntry);
                } else {
                    file.delete();
                }
            }
        }
    }

    private SimpleCacheSpan touchSpan(String key, SimpleCacheSpan span) throws SQLException {
        boolean z;
        if (!this.touchCacheSpans) {
            return span;
        }
        String name = ((File) Assertions.checkNotNull(span.file)).getName();
        long j = span.length;
        long jCurrentTimeMillis = System.currentTimeMillis();
        CacheFileMetadataIndex cacheFileMetadataIndex = this.fileIndex;
        if (cacheFileMetadataIndex != null) {
            try {
                cacheFileMetadataIndex.set(name, j, jCurrentTimeMillis);
            } catch (IOException unused) {
                Log.w(TAG, "Failed to update index with new touch timestamp.");
            }
            z = false;
        } else {
            z = true;
        }
        SimpleCacheSpan lastTouchTimestamp = this.contentIndex.get(key).setLastTouchTimestamp(span, jCurrentTimeMillis, z);
        notifySpanTouched(span, lastTouchTimestamp);
        return lastTouchTimestamp;
    }

    private SimpleCacheSpan getSpan(String key, long position, long length) {
        SimpleCacheSpan span;
        CachedContent cachedContent = this.contentIndex.get(key);
        if (cachedContent == null) {
            return SimpleCacheSpan.createHole(key, position, length);
        }
        while (true) {
            span = cachedContent.getSpan(position, length);
            if (!span.isCached || span.file.length() == span.length) {
                break;
            }
            removeStaleSpans();
        }
        return span;
    }

    private void addSpan(SimpleCacheSpan span) {
        this.contentIndex.getOrAdd(span.key).addSpan(span);
        this.totalSpace += span.length;
        notifySpanAdded(span);
    }

    private void removeSpanInternal(CacheSpan span) {
        CachedContent cachedContent = this.contentIndex.get(span.key);
        if (cachedContent == null || !cachedContent.removeSpan(span)) {
            return;
        }
        this.totalSpace -= span.length;
        if (this.fileIndex != null) {
            String name = span.file.getName();
            try {
                this.fileIndex.remove(name);
            } catch (IOException unused) {
                String strValueOf = String.valueOf(name);
                Log.w(TAG, strValueOf.length() != 0 ? "Failed to remove file index entry for: ".concat(strValueOf) : new String("Failed to remove file index entry for: "));
            }
        }
        this.contentIndex.maybeRemove(cachedContent.key);
        notifySpanRemoved(span);
    }

    private void removeStaleSpans() {
        ArrayList arrayList = new ArrayList();
        Iterator<CachedContent> it = this.contentIndex.getAll().iterator();
        while (it.hasNext()) {
            Iterator<SimpleCacheSpan> it2 = it.next().getSpans().iterator();
            while (it2.hasNext()) {
                SimpleCacheSpan next = it2.next();
                if (next.file.length() != next.length) {
                    arrayList.add(next);
                }
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            removeSpanInternal((CacheSpan) arrayList.get(i));
        }
    }

    private void notifySpanRemoved(CacheSpan span) {
        ArrayList<Cache.Listener> arrayList = this.listeners.get(span.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                arrayList.get(size).onSpanRemoved(this, span);
            }
        }
        this.evictor.onSpanRemoved(this, span);
    }

    private void notifySpanAdded(SimpleCacheSpan span) {
        ArrayList<Cache.Listener> arrayList = this.listeners.get(span.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                arrayList.get(size).onSpanAdded(this, span);
            }
        }
        this.evictor.onSpanAdded(this, span);
    }

    private void notifySpanTouched(SimpleCacheSpan oldSpan, CacheSpan newSpan) {
        ArrayList<Cache.Listener> arrayList = this.listeners.get(oldSpan.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                arrayList.get(size).onSpanTouched(this, oldSpan, newSpan);
            }
        }
        this.evictor.onSpanTouched(this, oldSpan, newSpan);
    }

    private static long loadUid(File[] files) {
        int length = files.length;
        for (int i = 0; i < length; i++) {
            File file = files[i];
            String name = file.getName();
            if (name.endsWith(UID_FILE_SUFFIX)) {
                try {
                    return parseUid(name);
                } catch (NumberFormatException unused) {
                    String strValueOf = String.valueOf(file);
                    Log.e(TAG, new StringBuilder(String.valueOf(strValueOf).length() + 20).append("Malformed UID file: ").append(strValueOf).toString());
                    file.delete();
                }
            }
        }
        return -1L;
    }

    private static long createUid(File directory) throws IOException {
        long jNextLong = new SecureRandom().nextLong();
        long jAbs = jNextLong == Long.MIN_VALUE ? 0L : Math.abs(jNextLong);
        String strValueOf = String.valueOf(Long.toString(jAbs, 16));
        File file = new File(directory, UID_FILE_SUFFIX.length() != 0 ? strValueOf.concat(UID_FILE_SUFFIX) : new String(strValueOf));
        if (file.createNewFile()) {
            return jAbs;
        }
        String strValueOf2 = String.valueOf(file);
        throw new IOException(new StringBuilder(String.valueOf(strValueOf2).length() + 27).append("Failed to create UID file: ").append(strValueOf2).toString());
    }

    private static long parseUid(String fileName) {
        return Long.parseLong(fileName.substring(0, fileName.indexOf(46)), 16);
    }

    private static void createCacheDirectories(File cacheDir) throws Cache.CacheException {
        if (cacheDir.mkdirs() || cacheDir.isDirectory()) {
            return;
        }
        String strValueOf = String.valueOf(cacheDir);
        String string = new StringBuilder(String.valueOf(strValueOf).length() + 34).append("Failed to create cache directory: ").append(strValueOf).toString();
        Log.e(TAG, string);
        throw new Cache.CacheException(string);
    }

    private static synchronized boolean lockFolder(File cacheDir) {
        return lockedCacheDirs.add(cacheDir.getAbsoluteFile());
    }

    private static synchronized void unlockFolder(File cacheDir) {
        lockedCacheDirs.remove(cacheDir.getAbsoluteFile());
    }
}
