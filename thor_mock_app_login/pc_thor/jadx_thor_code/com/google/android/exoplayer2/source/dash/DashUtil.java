package com.google.android.exoplayer2.source.dash;

import android.net.Uri;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.source.chunk.BundledChunkExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.chunk.InitializationChunk;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public final class DashUtil {
    public static DataSpec buildDataSpec(String baseUrl, RangedUri requestUri, String cacheKey, int flags) {
        return new DataSpec.Builder().setUri(requestUri.resolveUri(baseUrl)).setPosition(requestUri.start).setLength(requestUri.length).setKey(cacheKey).setFlags(flags).build();
    }

    public static DataSpec buildDataSpec(Representation representation, RangedUri requestUri, int flags) {
        return buildDataSpec(representation.baseUrls.get(0).url, requestUri, representation.getCacheKey(), flags);
    }

    public static DashManifest loadManifest(DataSource dataSource, Uri uri) throws IOException {
        return (DashManifest) ParsingLoadable.load(dataSource, new DashManifestParser(), uri, 4);
    }

    public static Format loadFormatWithDrmInitData(DataSource dataSource, Period period) throws IOException {
        int i = 2;
        Representation firstRepresentation = getFirstRepresentation(period, 2);
        if (firstRepresentation == null) {
            i = 1;
            firstRepresentation = getFirstRepresentation(period, 1);
            if (firstRepresentation == null) {
                return null;
            }
        }
        Format format = firstRepresentation.format;
        Format formatLoadSampleFormat = loadSampleFormat(dataSource, i, firstRepresentation);
        return formatLoadSampleFormat == null ? format : formatLoadSampleFormat.withManifestFormatInfo(format);
    }

    public static Format loadSampleFormat(DataSource dataSource, int trackType, Representation representation, int baseUrlIndex) throws IOException {
        if (representation.getInitializationUri() == null) {
            return null;
        }
        ChunkExtractor chunkExtractorNewChunkExtractor = newChunkExtractor(trackType, representation.format);
        try {
            loadInitializationData(chunkExtractorNewChunkExtractor, dataSource, representation, baseUrlIndex, false);
            chunkExtractorNewChunkExtractor.release();
            return ((Format[]) Assertions.checkStateNotNull(chunkExtractorNewChunkExtractor.getSampleFormats()))[0];
        } catch (Throwable th) {
            chunkExtractorNewChunkExtractor.release();
            throw th;
        }
    }

    public static Format loadSampleFormat(DataSource dataSource, int trackType, Representation representation) throws IOException {
        return loadSampleFormat(dataSource, trackType, representation, 0);
    }

    public static ChunkIndex loadChunkIndex(DataSource dataSource, int trackType, Representation representation, int baseUrlIndex) throws IOException {
        if (representation.getInitializationUri() == null) {
            return null;
        }
        ChunkExtractor chunkExtractorNewChunkExtractor = newChunkExtractor(trackType, representation.format);
        try {
            loadInitializationData(chunkExtractorNewChunkExtractor, dataSource, representation, baseUrlIndex, true);
            chunkExtractorNewChunkExtractor.release();
            return chunkExtractorNewChunkExtractor.getChunkIndex();
        } catch (Throwable th) {
            chunkExtractorNewChunkExtractor.release();
            throw th;
        }
    }

    public static ChunkIndex loadChunkIndex(DataSource dataSource, int trackType, Representation representation) throws IOException {
        return loadChunkIndex(dataSource, trackType, representation, 0);
    }

    private static void loadInitializationData(ChunkExtractor chunkExtractor, DataSource dataSource, Representation representation, int baseUrlIndex, boolean loadIndex) throws IOException {
        RangedUri rangedUri = (RangedUri) Assertions.checkNotNull(representation.getInitializationUri());
        if (loadIndex) {
            RangedUri indexUri = representation.getIndexUri();
            if (indexUri == null) {
                return;
            }
            RangedUri rangedUriAttemptMerge = rangedUri.attemptMerge(indexUri, representation.baseUrls.get(baseUrlIndex).url);
            if (rangedUriAttemptMerge == null) {
                loadInitializationData(dataSource, representation, baseUrlIndex, chunkExtractor, rangedUri);
                rangedUri = indexUri;
            } else {
                rangedUri = rangedUriAttemptMerge;
            }
        }
        loadInitializationData(dataSource, representation, baseUrlIndex, chunkExtractor, rangedUri);
    }

    public static void loadInitializationData(ChunkExtractor chunkExtractor, DataSource dataSource, Representation representation, boolean loadIndex) throws IOException {
        loadInitializationData(chunkExtractor, dataSource, representation, 0, loadIndex);
    }

    private static void loadInitializationData(DataSource dataSource, Representation representation, int baseUrlIndex, ChunkExtractor chunkExtractor, RangedUri requestUri) throws IOException {
        new InitializationChunk(dataSource, buildDataSpec(representation.baseUrls.get(baseUrlIndex).url, requestUri, representation.getCacheKey(), 0), representation.format, 0, null, chunkExtractor).load();
    }

    private static ChunkExtractor newChunkExtractor(int trackType, Format format) {
        String str = format.containerMimeType;
        return new BundledChunkExtractor(str != null && (str.startsWith(MimeTypes.VIDEO_WEBM) || str.startsWith(MimeTypes.AUDIO_WEBM)) ? new MatroskaExtractor() : new FragmentedMp4Extractor(), trackType, format);
    }

    private static Representation getFirstRepresentation(Period period, int type) {
        int adaptationSetIndex = period.getAdaptationSetIndex(type);
        if (adaptationSetIndex == -1) {
            return null;
        }
        List<Representation> list = period.adaptationSets.get(adaptationSetIndex).representations;
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private DashUtil() {
    }
}
