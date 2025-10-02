package com.google.android.exoplayer2.source.dash.offline;

import androidx.credentials.CredentialManager$$ExternalSyntheticLambda0;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.offline.DownloadException;
import com.google.android.exoplayer2.offline.SegmentDownloader;
import com.google.android.exoplayer2.source.dash.DashSegmentIndex;
import com.google.android.exoplayer2.source.dash.DashUtil;
import com.google.android.exoplayer2.source.dash.DashWrappingSegmentIndex;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.RunnableFutureTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class DashDownloader extends SegmentDownloader<DashManifest> {
    public DashDownloader(MediaItem mediaItem, CacheDataSource.Factory cacheDataSourceFactory) {
        this(mediaItem, cacheDataSourceFactory, new CredentialManager$$ExternalSyntheticLambda0());
    }

    public DashDownloader(MediaItem mediaItem, CacheDataSource.Factory cacheDataSourceFactory, Executor executor) {
        this(mediaItem, new DashManifestParser(), cacheDataSourceFactory, executor);
    }

    public DashDownloader(MediaItem mediaItem, ParsingLoadable.Parser<DashManifest> manifestParser, CacheDataSource.Factory cacheDataSourceFactory, Executor executor) {
        super(mediaItem, manifestParser, cacheDataSourceFactory, executor);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.offline.SegmentDownloader
    public List<SegmentDownloader.Segment> getSegments(DataSource dataSource, DashManifest manifest, boolean removing) throws InterruptedException, IOException {
        ArrayList<SegmentDownloader.Segment> arrayList = new ArrayList<>();
        for (int i = 0; i < manifest.getPeriodCount(); i++) {
            Period period = manifest.getPeriod(i);
            long jMsToUs = C.msToUs(period.startMs);
            long periodDurationUs = manifest.getPeriodDurationUs(i);
            int i2 = 0;
            for (List<AdaptationSet> list = period.adaptationSets; i2 < list.size(); list = list) {
                addSegmentsForAdaptationSet(dataSource, list.get(i2), jMsToUs, periodDurationUs, removing, arrayList);
                i2++;
            }
        }
        return arrayList;
    }

    private void addSegmentsForAdaptationSet(DataSource dataSource, AdaptationSet adaptationSet, long periodStartUs, long periodDurationUs, boolean removing, ArrayList<SegmentDownloader.Segment> out) throws InterruptedException, IOException {
        DashSegmentIndex segmentIndex;
        AdaptationSet adaptationSet2 = adaptationSet;
        int i = 0;
        int i2 = 0;
        while (i2 < adaptationSet2.representations.size()) {
            Representation representation = adaptationSet2.representations.get(i2);
            try {
                segmentIndex = getSegmentIndex(dataSource, adaptationSet2.type, representation, removing);
            } catch (IOException e) {
                e = e;
            }
            if (segmentIndex == null) {
                try {
                    throw new DownloadException("Missing segment index");
                } catch (IOException e2) {
                    e = e2;
                    if (!removing) {
                        throw e;
                    }
                    i2++;
                    adaptationSet2 = adaptationSet;
                    i = 0;
                }
            } else {
                long segmentCount = segmentIndex.getSegmentCount(periodDurationUs);
                if (segmentCount == -1) {
                    throw new DownloadException("Unbounded segment index");
                }
                String str = representation.baseUrls.get(i).url;
                RangedUri initializationUri = representation.getInitializationUri();
                if (initializationUri != null) {
                    addSegment(periodStartUs, str, initializationUri, out);
                }
                RangedUri indexUri = representation.getIndexUri();
                if (indexUri != null) {
                    addSegment(periodStartUs, str, indexUri, out);
                }
                long firstSegmentNum = segmentIndex.getFirstSegmentNum();
                long j = (firstSegmentNum + segmentCount) - 1;
                for (long j2 = firstSegmentNum; j2 <= j; j2++) {
                    addSegment(periodStartUs + segmentIndex.getTimeUs(j2), str, segmentIndex.getSegmentUrl(j2), out);
                }
                i2++;
                adaptationSet2 = adaptationSet;
                i = 0;
            }
        }
    }

    private static void addSegment(long startTimeUs, String baseUrl, RangedUri rangedUri, ArrayList<SegmentDownloader.Segment> out) {
        out.add(new SegmentDownloader.Segment(startTimeUs, new DataSpec(rangedUri.resolveUri(baseUrl), rangedUri.start, rangedUri.length)));
    }

    private DashSegmentIndex getSegmentIndex(final DataSource dataSource, final int trackType, final Representation representation, boolean removing) throws InterruptedException, IOException {
        DashSegmentIndex index = representation.getIndex();
        if (index != null) {
            return index;
        }
        ChunkIndex chunkIndex = (ChunkIndex) execute(new RunnableFutureTask<ChunkIndex, IOException>(this) { // from class: com.google.android.exoplayer2.source.dash.offline.DashDownloader.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.google.android.exoplayer2.util.RunnableFutureTask
            public ChunkIndex doWork() throws IOException {
                return DashUtil.loadChunkIndex(dataSource, trackType, representation);
            }
        }, removing);
        if (chunkIndex == null) {
            return null;
        }
        return new DashWrappingSegmentIndex(chunkIndex, representation.presentationTimeOffsetUs);
    }
}
