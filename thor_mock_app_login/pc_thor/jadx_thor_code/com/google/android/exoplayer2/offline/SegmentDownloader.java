package com.google.android.exoplayer2.offline;

import android.net.Uri;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.offline.Downloader;
import com.google.android.exoplayer2.offline.FilterableManifest;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory;
import com.google.android.exoplayer2.upstream.cache.CacheWriter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.RunnableFutureTask;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public abstract class SegmentDownloader<M extends FilterableManifest<M>> implements Downloader {
    private static final int BUFFER_SIZE_BYTES = 131072;
    private static final long MAX_MERGED_SEGMENT_START_TIME_DIFF_US = 20000000;
    private final ArrayList<RunnableFutureTask<?, ?>> activeRunnables;
    private final Cache cache;
    private final CacheDataSource.Factory cacheDataSourceFactory;
    private final CacheKeyFactory cacheKeyFactory;
    private final Executor executor;
    private volatile boolean isCanceled;
    private final DataSpec manifestDataSpec;
    private final ParsingLoadable.Parser<M> manifestParser;
    private final PriorityTaskManager priorityTaskManager;
    private final ArrayList<StreamKey> streamKeys;

    protected abstract List<Segment> getSegments(DataSource dataSource, M manifest, boolean removing) throws InterruptedException, IOException;

    protected static class Segment implements Comparable<Segment> {
        public final DataSpec dataSpec;
        public final long startTimeUs;

        public Segment(long startTimeUs, DataSpec dataSpec) {
            this.startTimeUs = startTimeUs;
            this.dataSpec = dataSpec;
        }

        @Override // java.lang.Comparable
        public int compareTo(Segment other) {
            return Util.compareLong(this.startTimeUs, other.startTimeUs);
        }
    }

    public SegmentDownloader(MediaItem mediaItem, ParsingLoadable.Parser<M> manifestParser, CacheDataSource.Factory cacheDataSourceFactory, Executor executor) {
        Assertions.checkNotNull(mediaItem.playbackProperties);
        this.manifestDataSpec = getCompressibleDataSpec(mediaItem.playbackProperties.uri);
        this.manifestParser = manifestParser;
        this.streamKeys = new ArrayList<>(mediaItem.playbackProperties.streamKeys);
        this.cacheDataSourceFactory = cacheDataSourceFactory;
        this.executor = executor;
        this.cache = (Cache) Assertions.checkNotNull(cacheDataSourceFactory.getCache());
        this.cacheKeyFactory = cacheDataSourceFactory.getCacheKeyFactory();
        this.priorityTaskManager = cacheDataSourceFactory.getUpstreamPriorityTaskManager();
        this.activeRunnables = new ArrayList<>();
    }

    /* JADX WARN: Removed duplicated region for block: B:86:0x01a6 A[LOOP:1: B:84:0x019e->B:86:0x01a6, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01bf A[LOOP:2: B:88:0x01bd->B:89:0x01bf, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01d4  */
    @Override // com.google.android.exoplayer2.offline.Downloader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void download(com.google.android.exoplayer2.offline.Downloader.ProgressListener r26) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 474
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.SegmentDownloader.download(com.google.android.exoplayer2.offline.Downloader$ProgressListener):void");
    }

    @Override // com.google.android.exoplayer2.offline.Downloader
    public void cancel() {
        synchronized (this.activeRunnables) {
            this.isCanceled = true;
            for (int i = 0; i < this.activeRunnables.size(); i++) {
                this.activeRunnables.get(i).cancel(true);
            }
        }
    }

    @Override // com.google.android.exoplayer2.offline.Downloader
    public final void remove() {
        CacheDataSource cacheDataSourceCreateDataSourceForRemovingDownload = this.cacheDataSourceFactory.createDataSourceForRemovingDownload();
        try {
            try {
                List<Segment> segments = getSegments(cacheDataSourceCreateDataSourceForRemovingDownload, getManifest(cacheDataSourceCreateDataSourceForRemovingDownload, this.manifestDataSpec, true), true);
                for (int i = 0; i < segments.size(); i++) {
                    this.cache.removeResource(this.cacheKeyFactory.buildCacheKey(segments.get(i).dataSpec));
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            } catch (Exception unused2) {
            }
        } finally {
            this.cache.removeResource(this.cacheKeyFactory.buildCacheKey(this.manifestDataSpec));
        }
    }

    protected final M getManifest(final DataSource dataSource, final DataSpec dataSpec, boolean removing) throws InterruptedException, IOException {
        return (M) execute(new RunnableFutureTask<M, IOException>() { // from class: com.google.android.exoplayer2.offline.SegmentDownloader.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.exoplayer2.util.RunnableFutureTask
            public M doWork() throws IOException {
                return (M) ParsingLoadable.load(dataSource, SegmentDownloader.this.manifestParser, dataSpec, 4);
            }
        }, removing);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(5:23|37|24|(2:27|(2:29|45)(3:44|31|32))(2:26|46)|30) */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0040, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0042, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0043, code lost:
    
        r0 = (java.lang.Throwable) com.google.android.exoplayer2.util.Assertions.checkNotNull(r4.getCause());
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x004f, code lost:
    
        if ((r0 instanceof com.google.android.exoplayer2.util.PriorityTaskManager.PriorityTooLowException) == false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0054, code lost:
    
        if ((r0 instanceof java.io.IOException) == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0056, code lost:
    
        com.google.android.exoplayer2.util.Util.sneakyThrow(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0062, code lost:
    
        throw ((java.io.IOException) r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0063, code lost:
    
        r3.blockUntilFinished();
        removeActiveRunnable((com.google.android.exoplayer2.util.RunnableFutureTask<?, ?>) r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0069, code lost:
    
        throw r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected final <T> T execute(com.google.android.exoplayer2.util.RunnableFutureTask<T, ?> r3, boolean r4) throws java.lang.Throwable {
        /*
            r2 = this;
            if (r4 == 0) goto L20
            r3.run()
            java.lang.Object r3 = r3.get()     // Catch: java.util.concurrent.ExecutionException -> La
            return r3
        La:
            r4 = move-exception
            java.lang.Throwable r0 = r4.getCause()
            java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r0)
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            boolean r1 = r0 instanceof java.io.IOException
            if (r1 != 0) goto L1d
            com.google.android.exoplayer2.util.Util.sneakyThrow(r4)
            goto L20
        L1d:
            java.io.IOException r0 = (java.io.IOException) r0
            throw r0
        L20:
            boolean r4 = r2.isCanceled
            if (r4 != 0) goto L6a
            com.google.android.exoplayer2.util.PriorityTaskManager r4 = r2.priorityTaskManager
            if (r4 == 0) goto L2d
            r0 = -1000(0xfffffffffffffc18, float:NaN)
            r4.proceed(r0)
        L2d:
            r2.addActiveRunnable(r3)
            java.util.concurrent.Executor r4 = r2.executor
            r4.execute(r3)
            java.lang.Object r4 = r3.get()     // Catch: java.lang.Throwable -> L40 java.util.concurrent.ExecutionException -> L42
            r3.blockUntilFinished()
            r2.removeActiveRunnable(r3)
            return r4
        L40:
            r4 = move-exception
            goto L63
        L42:
            r4 = move-exception
            java.lang.Throwable r0 = r4.getCause()     // Catch: java.lang.Throwable -> L40
            java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r0)     // Catch: java.lang.Throwable -> L40
            java.lang.Throwable r0 = (java.lang.Throwable) r0     // Catch: java.lang.Throwable -> L40
            boolean r1 = r0 instanceof com.google.android.exoplayer2.util.PriorityTaskManager.PriorityTooLowException     // Catch: java.lang.Throwable -> L40
            if (r1 == 0) goto L52
            goto L59
        L52:
            boolean r1 = r0 instanceof java.io.IOException     // Catch: java.lang.Throwable -> L40
            if (r1 != 0) goto L60
            com.google.android.exoplayer2.util.Util.sneakyThrow(r4)     // Catch: java.lang.Throwable -> L40
        L59:
            r3.blockUntilFinished()
            r2.removeActiveRunnable(r3)
            goto L20
        L60:
            java.io.IOException r0 = (java.io.IOException) r0     // Catch: java.lang.Throwable -> L40
            throw r0     // Catch: java.lang.Throwable -> L40
        L63:
            r3.blockUntilFinished()
            r2.removeActiveRunnable(r3)
            throw r4
        L6a:
            java.lang.InterruptedException r3 = new java.lang.InterruptedException
            r3.<init>()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.SegmentDownloader.execute(com.google.android.exoplayer2.util.RunnableFutureTask, boolean):java.lang.Object");
    }

    protected static DataSpec getCompressibleDataSpec(Uri uri) {
        return new DataSpec.Builder().setUri(uri).setFlags(1).build();
    }

    private <T> void addActiveRunnable(RunnableFutureTask<T, ?> runnable) throws InterruptedException {
        synchronized (this.activeRunnables) {
            if (this.isCanceled) {
                throw new InterruptedException();
            }
            this.activeRunnables.add(runnable);
        }
    }

    private void removeActiveRunnable(RunnableFutureTask<?, ?> runnable) {
        synchronized (this.activeRunnables) {
            this.activeRunnables.remove(runnable);
        }
    }

    private void removeActiveRunnable(int index) {
        synchronized (this.activeRunnables) {
            this.activeRunnables.remove(index);
        }
    }

    private static void mergeSegments(List<Segment> segments, CacheKeyFactory keyFactory) {
        HashMap map = new HashMap();
        int i = 0;
        for (int i2 = 0; i2 < segments.size(); i2++) {
            Segment segment = segments.get(i2);
            String strBuildCacheKey = keyFactory.buildCacheKey(segment.dataSpec);
            Integer num = (Integer) map.get(strBuildCacheKey);
            Segment segment2 = num == null ? null : segments.get(num.intValue());
            if (segment2 == null || segment.startTimeUs > segment2.startTimeUs + MAX_MERGED_SEGMENT_START_TIME_DIFF_US || !canMergeSegments(segment2.dataSpec, segment.dataSpec)) {
                map.put(strBuildCacheKey, Integer.valueOf(i));
                segments.set(i, segment);
                i++;
            } else {
                segments.set(((Integer) Assertions.checkNotNull(num)).intValue(), new Segment(segment2.startTimeUs, segment2.dataSpec.subrange(0L, segment.dataSpec.length != -1 ? segment2.dataSpec.length + segment.dataSpec.length : -1L)));
            }
        }
        Util.removeRange(segments, i, segments.size());
    }

    private static boolean canMergeSegments(DataSpec dataSpec1, DataSpec dataSpec2) {
        return dataSpec1.uri.equals(dataSpec2.uri) && dataSpec1.length != -1 && dataSpec1.position + dataSpec1.length == dataSpec2.position && Util.areEqual(dataSpec1.key, dataSpec2.key) && dataSpec1.flags == dataSpec2.flags && dataSpec1.httpMethod == dataSpec2.httpMethod && dataSpec1.httpRequestHeaders.equals(dataSpec2.httpRequestHeaders);
    }

    private static final class SegmentDownloadRunnable extends RunnableFutureTask<Void, IOException> {
        private final CacheWriter cacheWriter;
        public final CacheDataSource dataSource;
        private final ProgressNotifier progressNotifier;
        public final Segment segment;
        public final byte[] temporaryBuffer;

        public SegmentDownloadRunnable(Segment segment, CacheDataSource dataSource, ProgressNotifier progressNotifier, byte[] temporaryBuffer) {
            this.segment = segment;
            this.dataSource = dataSource;
            this.progressNotifier = progressNotifier;
            this.temporaryBuffer = temporaryBuffer;
            this.cacheWriter = new CacheWriter(dataSource, segment.dataSpec, temporaryBuffer, progressNotifier);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.exoplayer2.util.RunnableFutureTask
        public Void doWork() throws IOException {
            this.cacheWriter.cache();
            ProgressNotifier progressNotifier = this.progressNotifier;
            if (progressNotifier == null) {
                return null;
            }
            progressNotifier.onSegmentDownloaded();
            return null;
        }

        @Override // com.google.android.exoplayer2.util.RunnableFutureTask
        protected void cancelWork() {
            this.cacheWriter.cancel();
        }
    }

    private static final class ProgressNotifier implements CacheWriter.ProgressListener {
        private long bytesDownloaded;
        private final long contentLength;
        private final Downloader.ProgressListener progressListener;
        private int segmentsDownloaded;
        private final int totalSegments;

        public ProgressNotifier(Downloader.ProgressListener progressListener, long contentLength, int totalSegments, long bytesDownloaded, int segmentsDownloaded) {
            this.progressListener = progressListener;
            this.contentLength = contentLength;
            this.totalSegments = totalSegments;
            this.bytesDownloaded = bytesDownloaded;
            this.segmentsDownloaded = segmentsDownloaded;
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CacheWriter.ProgressListener
        public void onProgress(long requestLength, long bytesCached, long newBytesCached) {
            long j = this.bytesDownloaded + newBytesCached;
            this.bytesDownloaded = j;
            this.progressListener.onProgress(this.contentLength, j, getPercentDownloaded());
        }

        public void onSegmentDownloaded() {
            this.segmentsDownloaded++;
            this.progressListener.onProgress(this.contentLength, this.bytesDownloaded, getPercentDownloaded());
        }

        private float getPercentDownloaded() {
            long j = this.contentLength;
            if (j != -1 && j != 0) {
                return (this.bytesDownloaded * 100.0f) / j;
            }
            int i = this.totalSegments;
            if (i != 0) {
                return (this.segmentsDownloaded * 100.0f) / i;
            }
            return -1.0f;
        }
    }
}
