package com.google.android.exoplayer2.source.chunk;

import android.util.SparseArray;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.rawcc.RawCcExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public final class BundledChunkExtractor implements ExtractorOutput, ChunkExtractor {
    public static final ChunkExtractor.Factory FACTORY = new ChunkExtractor.Factory() { // from class: com.google.android.exoplayer2.source.chunk.BundledChunkExtractor$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor.Factory
        public final ChunkExtractor createProgressiveMediaExtractor(int i, Format format, boolean z, List list, TrackOutput trackOutput) {
            return BundledChunkExtractor.lambda$static$0(i, format, z, list, trackOutput);
        }
    };
    private static final PositionHolder POSITION_HOLDER = new PositionHolder();
    private final SparseArray<BindingTrackOutput> bindingTrackOutputs = new SparseArray<>();
    private long endTimeUs;
    private final Extractor extractor;
    private boolean extractorInitialized;
    private final Format primaryTrackManifestFormat;
    private final int primaryTrackType;
    private Format[] sampleFormats;
    private SeekMap seekMap;
    private ChunkExtractor.TrackOutputProvider trackOutputProvider;

    static /* synthetic */ ChunkExtractor lambda$static$0(int i, Format format, boolean z, List list, TrackOutput trackOutput) {
        Extractor fragmentedMp4Extractor;
        String str = format.containerMimeType;
        if (MimeTypes.isText(str)) {
            if (!MimeTypes.APPLICATION_RAWCC.equals(str)) {
                return null;
            }
            fragmentedMp4Extractor = new RawCcExtractor(format);
        } else if (MimeTypes.isMatroska(str)) {
            fragmentedMp4Extractor = new MatroskaExtractor(1);
        } else {
            fragmentedMp4Extractor = new FragmentedMp4Extractor(z ? 4 : 0, null, null, list, trackOutput);
        }
        return new BundledChunkExtractor(fragmentedMp4Extractor, i, format);
    }

    public BundledChunkExtractor(Extractor extractor, int primaryTrackType, Format primaryTrackManifestFormat) {
        this.extractor = extractor;
        this.primaryTrackType = primaryTrackType;
        this.primaryTrackManifestFormat = primaryTrackManifestFormat;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public ChunkIndex getChunkIndex() {
        SeekMap seekMap = this.seekMap;
        if (seekMap instanceof ChunkIndex) {
            return (ChunkIndex) seekMap;
        }
        return null;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public Format[] getSampleFormats() {
        return this.sampleFormats;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public void init(ChunkExtractor.TrackOutputProvider trackOutputProvider, long startTimeUs, long endTimeUs) {
        this.trackOutputProvider = trackOutputProvider;
        this.endTimeUs = endTimeUs;
        if (!this.extractorInitialized) {
            this.extractor.init(this);
            if (startTimeUs != C.TIME_UNSET) {
                this.extractor.seek(0L, startTimeUs);
            }
            this.extractorInitialized = true;
            return;
        }
        Extractor extractor = this.extractor;
        if (startTimeUs == C.TIME_UNSET) {
            startTimeUs = 0;
        }
        extractor.seek(0L, startTimeUs);
        for (int i = 0; i < this.bindingTrackOutputs.size(); i++) {
            this.bindingTrackOutputs.valueAt(i).bind(trackOutputProvider, endTimeUs);
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public void release() {
        this.extractor.release();
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public boolean read(ExtractorInput input) throws IOException {
        int i = this.extractor.read(input, POSITION_HOLDER);
        Assertions.checkState(i != 1);
        return i == 0;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public TrackOutput track(int id, int type) {
        BindingTrackOutput bindingTrackOutput = this.bindingTrackOutputs.get(id);
        if (bindingTrackOutput == null) {
            Assertions.checkState(this.sampleFormats == null);
            bindingTrackOutput = new BindingTrackOutput(id, type, type == this.primaryTrackType ? this.primaryTrackManifestFormat : null);
            bindingTrackOutput.bind(this.trackOutputProvider, this.endTimeUs);
            this.bindingTrackOutputs.put(id, bindingTrackOutput);
        }
        return bindingTrackOutput;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void endTracks() {
        Format[] formatArr = new Format[this.bindingTrackOutputs.size()];
        for (int i = 0; i < this.bindingTrackOutputs.size(); i++) {
            formatArr[i] = (Format) Assertions.checkStateNotNull(this.bindingTrackOutputs.valueAt(i).sampleFormat);
        }
        this.sampleFormats = formatArr;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void seekMap(SeekMap seekMap) {
        this.seekMap = seekMap;
    }

    private static final class BindingTrackOutput implements TrackOutput {
        private long endTimeUs;
        private final DummyTrackOutput fakeTrackOutput = new DummyTrackOutput();
        private final int id;
        private final Format manifestFormat;
        public Format sampleFormat;
        private TrackOutput trackOutput;
        private final int type;

        public BindingTrackOutput(int id, int type, Format manifestFormat) {
            this.id = id;
            this.type = type;
            this.manifestFormat = manifestFormat;
        }

        public void bind(ChunkExtractor.TrackOutputProvider trackOutputProvider, long endTimeUs) {
            if (trackOutputProvider == null) {
                this.trackOutput = this.fakeTrackOutput;
                return;
            }
            this.endTimeUs = endTimeUs;
            TrackOutput trackOutputTrack = trackOutputProvider.track(this.id, this.type);
            this.trackOutput = trackOutputTrack;
            Format format = this.sampleFormat;
            if (format != null) {
                trackOutputTrack.format(format);
            }
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void format(Format format) {
            Format format2 = this.manifestFormat;
            if (format2 != null) {
                format = format.withManifestFormatInfo(format2);
            }
            this.sampleFormat = format;
            ((TrackOutput) Util.castNonNull(this.trackOutput)).format(this.sampleFormat);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public int sampleData(DataReader input, int length, boolean allowEndOfInput, int sampleDataPart) throws IOException {
            return ((TrackOutput) Util.castNonNull(this.trackOutput)).sampleData(input, length, allowEndOfInput);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleData(ParsableByteArray data, int length, int sampleDataPart) {
            ((TrackOutput) Util.castNonNull(this.trackOutput)).sampleData(data, length);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleMetadata(long timeUs, int flags, int size, int offset, TrackOutput.CryptoData cryptoData) {
            long j = this.endTimeUs;
            if (j != C.TIME_UNSET && timeUs >= j) {
                this.trackOutput = this.fakeTrackOutput;
            }
            ((TrackOutput) Util.castNonNull(this.trackOutput)).sampleMetadata(timeUs, flags, size, offset, cryptoData);
        }
    }
}
