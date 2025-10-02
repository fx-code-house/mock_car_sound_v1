package com.google.android.exoplayer2.source.chunk;

import android.media.MediaParser;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.mediaparser.InputReaderAdapterV30;
import com.google.android.exoplayer2.source.mediaparser.MediaParserUtil;
import com.google.android.exoplayer2.source.mediaparser.OutputConsumerAdapterV30;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class MediaParserChunkExtractor implements ChunkExtractor {
    public static final ChunkExtractor.Factory FACTORY = new ChunkExtractor.Factory() { // from class: com.google.android.exoplayer2.source.chunk.MediaParserChunkExtractor$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor.Factory
        public final ChunkExtractor createProgressiveMediaExtractor(int i, Format format, boolean z, List list, TrackOutput trackOutput) {
            return MediaParserChunkExtractor.lambda$static$0(i, format, z, list, trackOutput);
        }
    };
    private static final String TAG = "MediaPrsrChunkExtractor";
    private final DummyTrackOutput dummyTrackOutput;
    private final InputReaderAdapterV30 inputReaderAdapter;
    private final MediaParser mediaParser;
    private final OutputConsumerAdapterV30 outputConsumerAdapter;
    private long pendingSeekUs;
    private Format[] sampleFormats;
    private ChunkExtractor.TrackOutputProvider trackOutputProvider;
    private final TrackOutputProviderAdapter trackOutputProviderAdapter;

    static /* synthetic */ ChunkExtractor lambda$static$0(int i, Format format, boolean z, List list, TrackOutput trackOutput) {
        if (!MimeTypes.isText(format.containerMimeType)) {
            return new MediaParserChunkExtractor(i, format, list);
        }
        Log.w(TAG, "Ignoring an unsupported text track.");
        return null;
    }

    public MediaParserChunkExtractor(int primaryTrackType, Format manifestFormat, List<Format> closedCaptionFormats) {
        OutputConsumerAdapterV30 outputConsumerAdapterV30 = new OutputConsumerAdapterV30(manifestFormat, primaryTrackType, true);
        this.outputConsumerAdapter = outputConsumerAdapterV30;
        this.inputReaderAdapter = new InputReaderAdapterV30();
        String str = MimeTypes.isMatroska((String) Assertions.checkNotNull(manifestFormat.containerMimeType)) ? "android.media.mediaparser.MatroskaParser" : "android.media.mediaparser.FragmentedMp4Parser";
        outputConsumerAdapterV30.setSelectedParserName(str);
        MediaParser mediaParserCreateByName = MediaParser.createByName(str, outputConsumerAdapterV30);
        this.mediaParser = mediaParserCreateByName;
        mediaParserCreateByName.setParameter("android.media.mediaparser.matroska.disableCuesSeeking", true);
        mediaParserCreateByName.setParameter(MediaParserUtil.PARAMETER_IN_BAND_CRYPTO_INFO, true);
        mediaParserCreateByName.setParameter(MediaParserUtil.PARAMETER_INCLUDE_SUPPLEMENTAL_DATA, true);
        mediaParserCreateByName.setParameter(MediaParserUtil.PARAMETER_EAGERLY_EXPOSE_TRACK_TYPE, true);
        mediaParserCreateByName.setParameter(MediaParserUtil.PARAMETER_EXPOSE_DUMMY_SEEK_MAP, true);
        mediaParserCreateByName.setParameter(MediaParserUtil.PARAMETER_EXPOSE_CHUNK_INDEX_AS_MEDIA_FORMAT, true);
        mediaParserCreateByName.setParameter(MediaParserUtil.PARAMETER_OVERRIDE_IN_BAND_CAPTION_DECLARATIONS, true);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < closedCaptionFormats.size(); i++) {
            arrayList.add(MediaParserUtil.toCaptionsMediaFormat(closedCaptionFormats.get(i)));
        }
        this.mediaParser.setParameter(MediaParserUtil.PARAMETER_EXPOSE_CAPTION_FORMATS, arrayList);
        this.outputConsumerAdapter.setMuxedCaptionFormats(closedCaptionFormats);
        this.trackOutputProviderAdapter = new TrackOutputProviderAdapter();
        this.dummyTrackOutput = new DummyTrackOutput();
        this.pendingSeekUs = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public void init(ChunkExtractor.TrackOutputProvider trackOutputProvider, long startTimeUs, long endTimeUs) {
        this.trackOutputProvider = trackOutputProvider;
        this.outputConsumerAdapter.setSampleTimestampUpperLimitFilterUs(endTimeUs);
        this.outputConsumerAdapter.setExtractorOutput(this.trackOutputProviderAdapter);
        this.pendingSeekUs = startTimeUs;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public void release() {
        this.mediaParser.release();
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public boolean read(ExtractorInput input) throws IOException {
        maybeExecutePendingSeek();
        this.inputReaderAdapter.setDataReader(input, input.getLength());
        return this.mediaParser.advance(this.inputReaderAdapter);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public ChunkIndex getChunkIndex() {
        return this.outputConsumerAdapter.getChunkIndex();
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public Format[] getSampleFormats() {
        return this.sampleFormats;
    }

    private void maybeExecutePendingSeek() {
        MediaParser.SeekMap dummySeekMap = this.outputConsumerAdapter.getDummySeekMap();
        long j = this.pendingSeekUs;
        if (j == C.TIME_UNSET || dummySeekMap == null) {
            return;
        }
        this.mediaParser.seek((MediaParser.SeekPoint) dummySeekMap.getSeekPoints(j).first);
        this.pendingSeekUs = C.TIME_UNSET;
    }

    private class TrackOutputProviderAdapter implements ExtractorOutput {
        @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
        public void seekMap(SeekMap seekMap) {
        }

        private TrackOutputProviderAdapter() {
        }

        @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
        public TrackOutput track(int id, int type) {
            return MediaParserChunkExtractor.this.trackOutputProvider != null ? MediaParserChunkExtractor.this.trackOutputProvider.track(id, type) : MediaParserChunkExtractor.this.dummyTrackOutput;
        }

        @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
        public void endTracks() {
            MediaParserChunkExtractor mediaParserChunkExtractor = MediaParserChunkExtractor.this;
            mediaParserChunkExtractor.sampleFormats = mediaParserChunkExtractor.outputConsumerAdapter.getSampleFormats();
        }
    }
}
