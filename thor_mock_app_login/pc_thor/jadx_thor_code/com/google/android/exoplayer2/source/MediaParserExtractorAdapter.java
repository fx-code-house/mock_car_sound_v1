package com.google.android.exoplayer2.source;

import android.media.MediaParser;
import android.net.Uri;
import android.util.Pair;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.source.ProgressiveMediaExtractor;
import com.google.android.exoplayer2.source.mediaparser.InputReaderAdapterV30;
import com.google.android.exoplayer2.source.mediaparser.MediaParserUtil;
import com.google.android.exoplayer2.source.mediaparser.OutputConsumerAdapterV30;
import com.google.android.exoplayer2.upstream.DataReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class MediaParserExtractorAdapter implements ProgressiveMediaExtractor {
    public static final ProgressiveMediaExtractor.Factory FACTORY = new ProgressiveMediaExtractor.Factory() { // from class: com.google.android.exoplayer2.source.MediaParserExtractorAdapter$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor.Factory
        public final ProgressiveMediaExtractor createProgressiveMediaExtractor() {
            return new MediaParserExtractorAdapter();
        }
    };
    private final InputReaderAdapterV30 inputReaderAdapter;
    private final MediaParser mediaParser;
    private final OutputConsumerAdapterV30 outputConsumerAdapter;
    private String parserName;

    public MediaParserExtractorAdapter() {
        OutputConsumerAdapterV30 outputConsumerAdapterV30 = new OutputConsumerAdapterV30();
        this.outputConsumerAdapter = outputConsumerAdapterV30;
        this.inputReaderAdapter = new InputReaderAdapterV30();
        MediaParser mediaParserCreate = MediaParser.create(outputConsumerAdapterV30, new String[0]);
        this.mediaParser = mediaParserCreate;
        mediaParserCreate.setParameter(MediaParserUtil.PARAMETER_EAGERLY_EXPOSE_TRACK_TYPE, true);
        mediaParserCreate.setParameter(MediaParserUtil.PARAMETER_IN_BAND_CRYPTO_INFO, true);
        mediaParserCreate.setParameter(MediaParserUtil.PARAMETER_INCLUDE_SUPPLEMENTAL_DATA, true);
        this.parserName = "android.media.mediaparser.UNKNOWN";
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void init(DataReader dataReader, Uri uri, Map<String, List<String>> responseHeaders, long position, long length, ExtractorOutput output) throws IOException {
        this.outputConsumerAdapter.setExtractorOutput(output);
        this.inputReaderAdapter.setDataReader(dataReader, length);
        this.inputReaderAdapter.setCurrentPosition(position);
        String parserName = this.mediaParser.getParserName();
        if ("android.media.mediaparser.UNKNOWN".equals(parserName)) {
            this.mediaParser.advance(this.inputReaderAdapter);
            String parserName2 = this.mediaParser.getParserName();
            this.parserName = parserName2;
            this.outputConsumerAdapter.setSelectedParserName(parserName2);
            return;
        }
        if (parserName.equals(this.parserName)) {
            return;
        }
        String parserName3 = this.mediaParser.getParserName();
        this.parserName = parserName3;
        this.outputConsumerAdapter.setSelectedParserName(parserName3);
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void release() {
        this.mediaParser.release();
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void disableSeekingOnMp3Streams() {
        if ("android.media.mediaparser.Mp3Parser".equals(this.parserName)) {
            this.outputConsumerAdapter.disableSeeking();
        }
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public long getCurrentInputPosition() {
        return this.inputReaderAdapter.getPosition();
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void seek(long position, long seekTimeUs) {
        this.inputReaderAdapter.setCurrentPosition(position);
        Pair<MediaParser.SeekPoint, MediaParser.SeekPoint> seekPoints = this.outputConsumerAdapter.getSeekPoints(seekTimeUs);
        this.mediaParser.seek((MediaParser.SeekPoint) (((MediaParser.SeekPoint) seekPoints.second).position == position ? seekPoints.second : seekPoints.first));
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public int read(PositionHolder positionHolder) throws IOException {
        boolean zAdvance = this.mediaParser.advance(this.inputReaderAdapter);
        positionHolder.position = this.inputReaderAdapter.getAndResetSeekPosition();
        if (zAdvance) {
            return positionHolder.position != -1 ? 1 : 0;
        }
        return -1;
    }
}
