package com.google.android.exoplayer2.source.smoothstreaming.offline;

import androidx.credentials.CredentialManager$$ExternalSyntheticLambda0;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.offline.SegmentDownloader;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class SsDownloader extends SegmentDownloader<SsManifest> {
    public SsDownloader(MediaItem mediaItem, CacheDataSource.Factory cacheDataSourceFactory) {
        this(mediaItem, cacheDataSourceFactory, new CredentialManager$$ExternalSyntheticLambda0());
    }

    public SsDownloader(MediaItem mediaItem, CacheDataSource.Factory cacheDataSourceFactory, Executor executor) {
        this(mediaItem.buildUpon().setUri(Util.fixSmoothStreamingIsmManifestUri(((MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties)).uri)).build(), new SsManifestParser(), cacheDataSourceFactory, executor);
    }

    public SsDownloader(MediaItem mediaItem, ParsingLoadable.Parser<SsManifest> manifestParser, CacheDataSource.Factory cacheDataSourceFactory, Executor executor) {
        super(mediaItem, manifestParser, cacheDataSourceFactory, executor);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.offline.SegmentDownloader
    public List<SegmentDownloader.Segment> getSegments(DataSource dataSource, SsManifest manifest, boolean allowIncompleteList) {
        ArrayList arrayList = new ArrayList();
        for (SsManifest.StreamElement streamElement : manifest.streamElements) {
            for (int i = 0; i < streamElement.formats.length; i++) {
                for (int i2 = 0; i2 < streamElement.chunkCount; i2++) {
                    arrayList.add(new SegmentDownloader.Segment(streamElement.getStartTimeUs(i2), new DataSpec(streamElement.buildRequestUri(i, i2))));
                }
            }
        }
        return arrayList;
    }
}
