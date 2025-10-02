package com.google.android.exoplayer2.source.hls.offline;

import android.net.Uri;
import androidx.credentials.CredentialManager$$ExternalSyntheticLambda0;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.offline.SegmentDownloader;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.UriUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class HlsDownloader extends SegmentDownloader<HlsPlaylist> {
    public HlsDownloader(MediaItem mediaItem, CacheDataSource.Factory cacheDataSourceFactory) {
        this(mediaItem, cacheDataSourceFactory, new CredentialManager$$ExternalSyntheticLambda0());
    }

    public HlsDownloader(MediaItem mediaItem, CacheDataSource.Factory cacheDataSourceFactory, Executor executor) {
        this(mediaItem, new HlsPlaylistParser(), cacheDataSourceFactory, executor);
    }

    public HlsDownloader(MediaItem mediaItem, ParsingLoadable.Parser<HlsPlaylist> manifestParser, CacheDataSource.Factory cacheDataSourceFactory, Executor executor) {
        super(mediaItem, manifestParser, cacheDataSourceFactory, executor);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.offline.SegmentDownloader
    public List<SegmentDownloader.Segment> getSegments(DataSource dataSource, HlsPlaylist playlist, boolean removing) throws InterruptedException, IOException {
        ArrayList arrayList = new ArrayList();
        if (playlist instanceof HlsMasterPlaylist) {
            addMediaPlaylistDataSpecs(((HlsMasterPlaylist) playlist).mediaPlaylistUrls, arrayList);
        } else {
            arrayList.add(SegmentDownloader.getCompressibleDataSpec(Uri.parse(playlist.baseUri)));
        }
        ArrayList<SegmentDownloader.Segment> arrayList2 = new ArrayList<>();
        HashSet<Uri> hashSet = new HashSet<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            DataSpec dataSpec = (DataSpec) it.next();
            arrayList2.add(new SegmentDownloader.Segment(0L, dataSpec));
            try {
                HlsMediaPlaylist hlsMediaPlaylist = (HlsMediaPlaylist) getManifest(dataSource, dataSpec, removing);
                List<HlsMediaPlaylist.Segment> list = hlsMediaPlaylist.segments;
                HlsMediaPlaylist.Segment segment = null;
                for (int i = 0; i < list.size(); i++) {
                    HlsMediaPlaylist.Segment segment2 = list.get(i);
                    HlsMediaPlaylist.Segment segment3 = segment2.initializationSegment;
                    if (segment3 != null && segment3 != segment) {
                        addSegment(hlsMediaPlaylist, segment3, hashSet, arrayList2);
                        segment = segment3;
                    }
                    addSegment(hlsMediaPlaylist, segment2, hashSet, arrayList2);
                }
            } catch (IOException e) {
                if (!removing) {
                    throw e;
                }
            }
        }
        return arrayList2;
    }

    private void addMediaPlaylistDataSpecs(List<Uri> mediaPlaylistUrls, List<DataSpec> out) {
        for (int i = 0; i < mediaPlaylistUrls.size(); i++) {
            out.add(SegmentDownloader.getCompressibleDataSpec(mediaPlaylistUrls.get(i)));
        }
    }

    private void addSegment(HlsMediaPlaylist mediaPlaylist, HlsMediaPlaylist.Segment segment, HashSet<Uri> seenEncryptionKeyUris, ArrayList<SegmentDownloader.Segment> out) {
        String str = mediaPlaylist.baseUri;
        long j = mediaPlaylist.startTimeUs + segment.relativeStartTimeUs;
        if (segment.fullSegmentEncryptionKeyUri != null) {
            Uri uriResolveToUri = UriUtil.resolveToUri(str, segment.fullSegmentEncryptionKeyUri);
            if (seenEncryptionKeyUris.add(uriResolveToUri)) {
                out.add(new SegmentDownloader.Segment(j, SegmentDownloader.getCompressibleDataSpec(uriResolveToUri)));
            }
        }
        out.add(new SegmentDownloader.Segment(j, new DataSpec(UriUtil.resolveToUri(str, segment.url), segment.byteRangeOffset, segment.byteRangeLength)));
    }
}
