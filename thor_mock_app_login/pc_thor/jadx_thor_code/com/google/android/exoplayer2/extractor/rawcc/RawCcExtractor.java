package com.google.android.exoplayer2.extractor.rawcc;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public final class RawCcExtractor implements Extractor {
    private static final int HEADER_ID = 1380139777;
    private static final int HEADER_SIZE = 8;
    private static final int SCRATCH_SIZE = 9;
    private static final int STATE_READING_HEADER = 0;
    private static final int STATE_READING_SAMPLES = 2;
    private static final int STATE_READING_TIMESTAMP_AND_COUNT = 1;
    private static final int TIMESTAMP_SIZE_V0 = 4;
    private static final int TIMESTAMP_SIZE_V1 = 8;
    private final Format format;
    private int remainingSampleCount;
    private int sampleBytesWritten;
    private long timestampUs;
    private TrackOutput trackOutput;
    private int version;
    private final ParsableByteArray dataScratch = new ParsableByteArray(9);
    private int parserState = 0;

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    public RawCcExtractor(Format format) {
        this.format = format;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput output) {
        output.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
        TrackOutput trackOutputTrack = output.track(0, 3);
        this.trackOutput = trackOutputTrack;
        trackOutputTrack.format(this.format);
        output.endTracks();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput input) throws IOException {
        this.dataScratch.reset(8);
        input.peekFully(this.dataScratch.getData(), 0, 8);
        return this.dataScratch.readInt() == HEADER_ID;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException {
        Assertions.checkStateNotNull(this.trackOutput);
        while (true) {
            int i = this.parserState;
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        parseSamples(input);
                        this.parserState = 1;
                        return 0;
                    }
                    throw new IllegalStateException();
                }
                if (parseTimestampAndSampleCount(input)) {
                    this.parserState = 2;
                } else {
                    this.parserState = 0;
                    return -1;
                }
            } else {
                if (!parseHeader(input)) {
                    return -1;
                }
                this.parserState = 1;
            }
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long position, long timeUs) {
        this.parserState = 0;
    }

    private boolean parseHeader(ExtractorInput input) throws IOException {
        this.dataScratch.reset(8);
        if (!input.readFully(this.dataScratch.getData(), 0, 8, true)) {
            return false;
        }
        if (this.dataScratch.readInt() != HEADER_ID) {
            throw new IOException("Input not RawCC");
        }
        this.version = this.dataScratch.readUnsignedByte();
        return true;
    }

    private boolean parseTimestampAndSampleCount(ExtractorInput input) throws IOException {
        int i = this.version;
        if (i == 0) {
            this.dataScratch.reset(5);
            if (!input.readFully(this.dataScratch.getData(), 0, 5, true)) {
                return false;
            }
            this.timestampUs = (this.dataScratch.readUnsignedInt() * 1000) / 45;
        } else if (i == 1) {
            this.dataScratch.reset(9);
            if (!input.readFully(this.dataScratch.getData(), 0, 9, true)) {
                return false;
            }
            this.timestampUs = this.dataScratch.readLong();
        } else {
            throw ParserException.createForMalformedContainer(new StringBuilder(39).append("Unsupported version number: ").append(i).toString(), null);
        }
        this.remainingSampleCount = this.dataScratch.readUnsignedByte();
        this.sampleBytesWritten = 0;
        return true;
    }

    @RequiresNonNull({"trackOutput"})
    private void parseSamples(ExtractorInput input) throws IOException {
        while (this.remainingSampleCount > 0) {
            this.dataScratch.reset(3);
            input.readFully(this.dataScratch.getData(), 0, 3);
            this.trackOutput.sampleData(this.dataScratch, 3);
            this.sampleBytesWritten += 3;
            this.remainingSampleCount--;
        }
        int i = this.sampleBytesWritten;
        if (i > 0) {
            this.trackOutput.sampleMetadata(this.timestampUs, 1, i, 0, null);
        }
    }
}
