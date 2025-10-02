package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSourceException;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DummyDataSource;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.PriorityDataSource;
import com.google.android.exoplayer2.upstream.TeeDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Util;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class CacheDataSource implements DataSource {
    public static final int CACHE_IGNORED_REASON_ERROR = 0;
    public static final int CACHE_IGNORED_REASON_UNSET_LENGTH = 1;
    private static final int CACHE_NOT_IGNORED = -1;
    public static final int FLAG_BLOCK_ON_CACHE = 1;
    public static final int FLAG_IGNORE_CACHE_FOR_UNSET_LENGTH_REQUESTS = 4;
    public static final int FLAG_IGNORE_CACHE_ON_ERROR = 2;
    private static final long MIN_READ_BEFORE_CHECKING_CACHE = 102400;
    private Uri actualUri;
    private final boolean blockOnCache;
    private long bytesRemaining;
    private final Cache cache;
    private final CacheKeyFactory cacheKeyFactory;
    private final DataSource cacheReadDataSource;
    private final DataSource cacheWriteDataSource;
    private long checkCachePosition;
    private DataSource currentDataSource;
    private long currentDataSourceBytesRead;
    private DataSpec currentDataSpec;
    private CacheSpan currentHoleSpan;
    private boolean currentRequestIgnoresCache;
    private final EventListener eventListener;
    private final boolean ignoreCacheForUnsetLengthRequests;
    private final boolean ignoreCacheOnError;
    private long readPosition;
    private DataSpec requestDataSpec;
    private boolean seenCacheError;
    private long totalCachedBytesRead;
    private final DataSource upstreamDataSource;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface CacheIgnoredReason {
    }

    public interface EventListener {
        void onCacheIgnored(int reason);

        void onCachedBytesRead(long cacheSizeBytes, long cachedBytesRead);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static final class Factory implements DataSource.Factory {
        private Cache cache;
        private boolean cacheIsReadOnly;
        private DataSink.Factory cacheWriteDataSinkFactory;
        private EventListener eventListener;
        private int flags;
        private DataSource.Factory upstreamDataSourceFactory;
        private int upstreamPriority;
        private PriorityTaskManager upstreamPriorityTaskManager;
        private DataSource.Factory cacheReadDataSourceFactory = new FileDataSource.Factory();
        private CacheKeyFactory cacheKeyFactory = CacheKeyFactory.DEFAULT;

        public Factory setCache(Cache cache) {
            this.cache = cache;
            return this;
        }

        public Cache getCache() {
            return this.cache;
        }

        public Factory setCacheReadDataSourceFactory(DataSource.Factory cacheReadDataSourceFactory) {
            this.cacheReadDataSourceFactory = cacheReadDataSourceFactory;
            return this;
        }

        public Factory setCacheWriteDataSinkFactory(DataSink.Factory cacheWriteDataSinkFactory) {
            this.cacheWriteDataSinkFactory = cacheWriteDataSinkFactory;
            this.cacheIsReadOnly = cacheWriteDataSinkFactory == null;
            return this;
        }

        public Factory setCacheKeyFactory(CacheKeyFactory cacheKeyFactory) {
            this.cacheKeyFactory = cacheKeyFactory;
            return this;
        }

        public CacheKeyFactory getCacheKeyFactory() {
            return this.cacheKeyFactory;
        }

        public Factory setUpstreamDataSourceFactory(DataSource.Factory upstreamDataSourceFactory) {
            this.upstreamDataSourceFactory = upstreamDataSourceFactory;
            return this;
        }

        public Factory setUpstreamPriorityTaskManager(PriorityTaskManager upstreamPriorityTaskManager) {
            this.upstreamPriorityTaskManager = upstreamPriorityTaskManager;
            return this;
        }

        public PriorityTaskManager getUpstreamPriorityTaskManager() {
            return this.upstreamPriorityTaskManager;
        }

        public Factory setUpstreamPriority(int upstreamPriority) {
            this.upstreamPriority = upstreamPriority;
            return this;
        }

        public Factory setFlags(int flags) {
            this.flags = flags;
            return this;
        }

        public Factory setEventListener(EventListener eventListener) {
            this.eventListener = eventListener;
            return this;
        }

        @Override // com.google.android.exoplayer2.upstream.DataSource.Factory
        public CacheDataSource createDataSource() {
            DataSource.Factory factory = this.upstreamDataSourceFactory;
            return createDataSourceInternal(factory != null ? factory.createDataSource() : null, this.flags, this.upstreamPriority);
        }

        public CacheDataSource createDataSourceForDownloading() {
            DataSource.Factory factory = this.upstreamDataSourceFactory;
            return createDataSourceInternal(factory != null ? factory.createDataSource() : null, this.flags | 1, -1000);
        }

        public CacheDataSource createDataSourceForRemovingDownload() {
            return createDataSourceInternal(null, this.flags | 1, -1000);
        }

        private CacheDataSource createDataSourceInternal(DataSource upstreamDataSource, int flags, int upstreamPriority) {
            DataSink dataSinkCreateDataSink;
            Cache cache = (Cache) Assertions.checkNotNull(this.cache);
            if (this.cacheIsReadOnly || upstreamDataSource == null) {
                dataSinkCreateDataSink = null;
            } else {
                DataSink.Factory factory = this.cacheWriteDataSinkFactory;
                if (factory != null) {
                    dataSinkCreateDataSink = factory.createDataSink();
                } else {
                    dataSinkCreateDataSink = new CacheDataSink.Factory().setCache(cache).createDataSink();
                }
            }
            return new CacheDataSource(cache, upstreamDataSource, this.cacheReadDataSourceFactory.createDataSource(), dataSinkCreateDataSink, this.cacheKeyFactory, flags, this.upstreamPriorityTaskManager, upstreamPriority, this.eventListener);
        }
    }

    public CacheDataSource(Cache cache, DataSource upstreamDataSource) {
        this(cache, upstreamDataSource, 0);
    }

    public CacheDataSource(Cache cache, DataSource upstreamDataSource, int flags) {
        this(cache, upstreamDataSource, new FileDataSource(), new CacheDataSink(cache, CacheDataSink.DEFAULT_FRAGMENT_SIZE), flags, null);
    }

    public CacheDataSource(Cache cache, DataSource upstreamDataSource, DataSource cacheReadDataSource, DataSink cacheWriteDataSink, int flags, EventListener eventListener) {
        this(cache, upstreamDataSource, cacheReadDataSource, cacheWriteDataSink, flags, eventListener, null);
    }

    public CacheDataSource(Cache cache, DataSource upstreamDataSource, DataSource cacheReadDataSource, DataSink cacheWriteDataSink, int flags, EventListener eventListener, CacheKeyFactory cacheKeyFactory) {
        this(cache, upstreamDataSource, cacheReadDataSource, cacheWriteDataSink, cacheKeyFactory, flags, null, 0, eventListener);
    }

    private CacheDataSource(Cache cache, DataSource upstreamDataSource, DataSource cacheReadDataSource, DataSink cacheWriteDataSink, CacheKeyFactory cacheKeyFactory, int flags, PriorityTaskManager upstreamPriorityTaskManager, int upstreamPriority, EventListener eventListener) {
        this.cache = cache;
        this.cacheReadDataSource = cacheReadDataSource;
        this.cacheKeyFactory = cacheKeyFactory == null ? CacheKeyFactory.DEFAULT : cacheKeyFactory;
        this.blockOnCache = (flags & 1) != 0;
        this.ignoreCacheOnError = (flags & 2) != 0;
        this.ignoreCacheForUnsetLengthRequests = (flags & 4) != 0;
        if (upstreamDataSource != null) {
            upstreamDataSource = upstreamPriorityTaskManager != null ? new PriorityDataSource(upstreamDataSource, upstreamPriorityTaskManager, upstreamPriority) : upstreamDataSource;
            this.upstreamDataSource = upstreamDataSource;
            this.cacheWriteDataSource = cacheWriteDataSink != null ? new TeeDataSource(upstreamDataSource, cacheWriteDataSink) : null;
        } else {
            this.upstreamDataSource = DummyDataSource.INSTANCE;
            this.cacheWriteDataSource = null;
        }
        this.eventListener = eventListener;
    }

    public Cache getCache() {
        return this.cache;
    }

    public CacheKeyFactory getCacheKeyFactory() {
        return this.cacheKeyFactory;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        this.cacheReadDataSource.addTransferListener(transferListener);
        this.upstreamDataSource.addTransferListener(transferListener);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        long jMin;
        try {
            String strBuildCacheKey = this.cacheKeyFactory.buildCacheKey(dataSpec);
            DataSpec dataSpecBuild = dataSpec.buildUpon().setKey(strBuildCacheKey).build();
            this.requestDataSpec = dataSpecBuild;
            this.actualUri = getRedirectedUriOrDefault(this.cache, strBuildCacheKey, dataSpecBuild.uri);
            this.readPosition = dataSpec.position;
            int iShouldIgnoreCacheForRequest = shouldIgnoreCacheForRequest(dataSpec);
            boolean z = iShouldIgnoreCacheForRequest != -1;
            this.currentRequestIgnoresCache = z;
            if (z) {
                notifyCacheIgnored(iShouldIgnoreCacheForRequest);
            }
            if (this.currentRequestIgnoresCache) {
                this.bytesRemaining = -1L;
            } else {
                long contentLength = ContentMetadata.getContentLength(this.cache.getContentMetadata(strBuildCacheKey));
                this.bytesRemaining = contentLength;
                if (contentLength != -1) {
                    long j = contentLength - dataSpec.position;
                    this.bytesRemaining = j;
                    if (j < 0) {
                        throw new DataSourceException(2008);
                    }
                }
            }
            if (dataSpec.length != -1) {
                long j2 = this.bytesRemaining;
                if (j2 == -1) {
                    jMin = dataSpec.length;
                } else {
                    jMin = Math.min(j2, dataSpec.length);
                }
                this.bytesRemaining = jMin;
            }
            long j3 = this.bytesRemaining;
            if (j3 > 0 || j3 == -1) {
                openNextSource(dataSpecBuild, false);
            }
            return dataSpec.length != -1 ? dataSpec.length : this.bytesRemaining;
        } catch (Throwable th) {
            handleBeforeThrow(th);
            throw th;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] buffer, int offset, int length) throws IOException {
        int i;
        DataSpec dataSpec = (DataSpec) Assertions.checkNotNull(this.requestDataSpec);
        DataSpec dataSpec2 = (DataSpec) Assertions.checkNotNull(this.currentDataSpec);
        if (length == 0) {
            return 0;
        }
        if (this.bytesRemaining == 0) {
            return -1;
        }
        try {
            if (this.readPosition >= this.checkCachePosition) {
                openNextSource(dataSpec, true);
            }
            int i2 = ((DataSource) Assertions.checkNotNull(this.currentDataSource)).read(buffer, offset, length);
            if (i2 != -1) {
                if (isReadingFromCache()) {
                    this.totalCachedBytesRead += i2;
                }
                long j = i2;
                this.readPosition += j;
                this.currentDataSourceBytesRead += j;
                long j2 = this.bytesRemaining;
                if (j2 != -1) {
                    this.bytesRemaining = j2 - j;
                }
                return i2;
            }
            if (isReadingFromUpstream()) {
                if (dataSpec2.length != -1) {
                    i = i2;
                    if (this.currentDataSourceBytesRead < dataSpec2.length) {
                    }
                } else {
                    i = i2;
                }
                setNoBytesRemainingAndMaybeStoreLength((String) Util.castNonNull(dataSpec.key));
                return i;
            }
            i = i2;
            long j3 = this.bytesRemaining;
            if (j3 <= 0 && j3 != -1) {
                return i;
            }
            closeCurrentSource();
            openNextSource(dataSpec, false);
            return read(buffer, offset, length);
        } catch (Throwable th) {
            handleBeforeThrow(th);
            throw th;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Uri getUri() {
        return this.actualUri;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Map<String, List<String>> getResponseHeaders() {
        if (isReadingFromUpstream()) {
            return this.upstreamDataSource.getResponseHeaders();
        }
        return Collections.emptyMap();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() throws IOException {
        this.requestDataSpec = null;
        this.actualUri = null;
        this.readPosition = 0L;
        notifyBytesRead();
        try {
            closeCurrentSource();
        } catch (Throwable th) {
            handleBeforeThrow(th);
            throw th;
        }
    }

    private void openNextSource(DataSpec requestDataSpec, boolean checkCache) throws IOException {
        CacheSpan cacheSpanStartReadWrite;
        long jMin;
        DataSpec dataSpecBuild;
        DataSource dataSource;
        String str = (String) Util.castNonNull(requestDataSpec.key);
        if (this.currentRequestIgnoresCache) {
            cacheSpanStartReadWrite = null;
        } else if (this.blockOnCache) {
            try {
                cacheSpanStartReadWrite = this.cache.startReadWrite(str, this.readPosition, this.bytesRemaining);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                throw new InterruptedIOException();
            }
        } else {
            cacheSpanStartReadWrite = this.cache.startReadWriteNonBlocking(str, this.readPosition, this.bytesRemaining);
        }
        if (cacheSpanStartReadWrite == null) {
            dataSource = this.upstreamDataSource;
            dataSpecBuild = requestDataSpec.buildUpon().setPosition(this.readPosition).setLength(this.bytesRemaining).build();
        } else if (cacheSpanStartReadWrite.isCached) {
            Uri uriFromFile = Uri.fromFile((File) Util.castNonNull(cacheSpanStartReadWrite.file));
            long j = cacheSpanStartReadWrite.position;
            long j2 = this.readPosition - j;
            long jMin2 = cacheSpanStartReadWrite.length - j2;
            long j3 = this.bytesRemaining;
            if (j3 != -1) {
                jMin2 = Math.min(jMin2, j3);
            }
            dataSpecBuild = requestDataSpec.buildUpon().setUri(uriFromFile).setUriPositionOffset(j).setPosition(j2).setLength(jMin2).build();
            dataSource = this.cacheReadDataSource;
        } else {
            if (cacheSpanStartReadWrite.isOpenEnded()) {
                jMin = this.bytesRemaining;
            } else {
                jMin = cacheSpanStartReadWrite.length;
                long j4 = this.bytesRemaining;
                if (j4 != -1) {
                    jMin = Math.min(jMin, j4);
                }
            }
            dataSpecBuild = requestDataSpec.buildUpon().setPosition(this.readPosition).setLength(jMin).build();
            dataSource = this.cacheWriteDataSource;
            if (dataSource == null) {
                dataSource = this.upstreamDataSource;
                this.cache.releaseHoleSpan(cacheSpanStartReadWrite);
                cacheSpanStartReadWrite = null;
            }
        }
        this.checkCachePosition = (this.currentRequestIgnoresCache || dataSource != this.upstreamDataSource) ? Long.MAX_VALUE : this.readPosition + MIN_READ_BEFORE_CHECKING_CACHE;
        if (checkCache) {
            Assertions.checkState(isBypassingCache());
            if (dataSource == this.upstreamDataSource) {
                return;
            }
            try {
                closeCurrentSource();
            } finally {
            }
        }
        if (cacheSpanStartReadWrite != null && cacheSpanStartReadWrite.isHoleSpan()) {
            this.currentHoleSpan = cacheSpanStartReadWrite;
        }
        this.currentDataSource = dataSource;
        this.currentDataSpec = dataSpecBuild;
        this.currentDataSourceBytesRead = 0L;
        long jOpen = dataSource.open(dataSpecBuild);
        ContentMetadataMutations contentMetadataMutations = new ContentMetadataMutations();
        if (dataSpecBuild.length == -1 && jOpen != -1) {
            this.bytesRemaining = jOpen;
            ContentMetadataMutations.setContentLength(contentMetadataMutations, this.readPosition + jOpen);
        }
        if (isReadingFromUpstream()) {
            this.actualUri = dataSource.getUri();
            ContentMetadataMutations.setRedirectedUri(contentMetadataMutations, requestDataSpec.uri.equals(this.actualUri) ^ true ? this.actualUri : null);
        }
        if (isWritingToCache()) {
            this.cache.applyContentMetadataMutations(str, contentMetadataMutations);
        }
    }

    private void setNoBytesRemainingAndMaybeStoreLength(String key) throws IOException {
        this.bytesRemaining = 0L;
        if (isWritingToCache()) {
            ContentMetadataMutations contentMetadataMutations = new ContentMetadataMutations();
            ContentMetadataMutations.setContentLength(contentMetadataMutations, this.readPosition);
            this.cache.applyContentMetadataMutations(key, contentMetadataMutations);
        }
    }

    private static Uri getRedirectedUriOrDefault(Cache cache, String key, Uri defaultUri) {
        Uri redirectedUri = ContentMetadata.getRedirectedUri(cache.getContentMetadata(key));
        return redirectedUri != null ? redirectedUri : defaultUri;
    }

    private boolean isReadingFromUpstream() {
        return !isReadingFromCache();
    }

    private boolean isBypassingCache() {
        return this.currentDataSource == this.upstreamDataSource;
    }

    private boolean isReadingFromCache() {
        return this.currentDataSource == this.cacheReadDataSource;
    }

    private boolean isWritingToCache() {
        return this.currentDataSource == this.cacheWriteDataSource;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void closeCurrentSource() throws IOException {
        DataSource dataSource = this.currentDataSource;
        if (dataSource == null) {
            return;
        }
        try {
            dataSource.close();
        } finally {
            this.currentDataSpec = null;
            this.currentDataSource = null;
            CacheSpan cacheSpan = this.currentHoleSpan;
            if (cacheSpan != null) {
                this.cache.releaseHoleSpan(cacheSpan);
                this.currentHoleSpan = null;
            }
        }
    }

    private void handleBeforeThrow(Throwable exception) {
        if (isReadingFromCache() || (exception instanceof Cache.CacheException)) {
            this.seenCacheError = true;
        }
    }

    private int shouldIgnoreCacheForRequest(DataSpec dataSpec) {
        if (this.ignoreCacheOnError && this.seenCacheError) {
            return 0;
        }
        return (this.ignoreCacheForUnsetLengthRequests && dataSpec.length == -1) ? 1 : -1;
    }

    private void notifyCacheIgnored(int reason) {
        EventListener eventListener = this.eventListener;
        if (eventListener != null) {
            eventListener.onCacheIgnored(reason);
        }
    }

    private void notifyBytesRead() {
        EventListener eventListener = this.eventListener;
        if (eventListener == null || this.totalCachedBytesRead <= 0) {
            return;
        }
        eventListener.onCachedBytesRead(this.cache.getCacheSpace(), this.totalCachedBytesRead);
        this.totalCachedBytesRead = 0L;
    }
}
