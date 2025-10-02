package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public final class MpegAudioReader implements ElementaryStreamReader {
    private static final int HEADER_SIZE = 4;
    private static final int STATE_FINDING_HEADER = 0;
    private static final int STATE_READING_FRAME = 2;
    private static final int STATE_READING_HEADER = 1;
    private String formatId;
    private int frameBytesRead;
    private long frameDurationUs;
    private int frameSize;
    private boolean hasOutputFormat;
    private final MpegAudioUtil.Header header;
    private final ParsableByteArray headerScratch;
    private final String language;
    private boolean lastByteWasFF;
    private TrackOutput output;
    private int state;
    private long timeUs;

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public MpegAudioReader() {
        this(null);
    }

    public MpegAudioReader(String language) {
        this.state = 0;
        ParsableByteArray parsableByteArray = new ParsableByteArray(4);
        this.headerScratch = parsableByteArray;
        parsableByteArray.getData()[0] = -1;
        this.header = new MpegAudioUtil.Header();
        this.language = language;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.state = 0;
        this.frameBytesRead = 0;
        this.lastByteWasFF = false;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        idGenerator.generateNewId();
        this.formatId = idGenerator.getFormatId();
        this.output = extractorOutput.track(idGenerator.getTrackId(), 1);
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long pesTimeUs, int flags) {
        this.timeUs = pesTimeUs;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray data) {
        Assertions.checkStateNotNull(this.output);
        while (data.bytesLeft() > 0) {
            int i = this.state;
            if (i == 0) {
                findHeader(data);
            } else if (i == 1) {
                readHeaderRemainder(data);
            } else if (i == 2) {
                readFrameRemainder(data);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private void findHeader(ParsableByteArray source) {
        byte[] data = source.getData();
        int iLimit = source.limit();
        for (int position = source.getPosition(); position < iLimit; position++) {
            byte b = data[position];
            boolean z = (b & 255) == 255;
            boolean z2 = this.lastByteWasFF && (b & 224) == 224;
            this.lastByteWasFF = z;
            if (z2) {
                source.setPosition(position + 1);
                this.lastByteWasFF = false;
                this.headerScratch.getData()[1] = data[position];
                this.frameBytesRead = 2;
                this.state = 1;
                return;
            }
        }
        source.setPosition(iLimit);
    }

    @RequiresNonNull({"output"})
    private void readHeaderRemainder(ParsableByteArray source) {
        int iMin = Math.min(source.bytesLeft(), 4 - this.frameBytesRead);
        source.readBytes(this.headerScratch.getData(), this.frameBytesRead, iMin);
        int i = this.frameBytesRead + iMin;
        this.frameBytesRead = i;
        if (i < 4) {
            return;
        }
        this.headerScratch.setPosition(0);
        if (!this.header.setForHeaderData(this.headerScratch.readInt())) {
            this.frameBytesRead = 0;
            this.state = 1;
            return;
        }
        this.frameSize = this.header.frameSize;
        if (!this.hasOutputFormat) {
            this.frameDurationUs = (this.header.samplesPerFrame * 1000000) / this.header.sampleRate;
            this.output.format(new Format.Builder().setId(this.formatId).setSampleMimeType(this.header.mimeType).setMaxInputSize(4096).setChannelCount(this.header.channels).setSampleRate(this.header.sampleRate).setLanguage(this.language).build());
            this.hasOutputFormat = true;
        }
        this.headerScratch.setPosition(0);
        this.output.sampleData(this.headerScratch, 4);
        this.state = 2;
    }

    @RequiresNonNull({"output"})
    private void readFrameRemainder(ParsableByteArray source) {
        int iMin = Math.min(source.bytesLeft(), this.frameSize - this.frameBytesRead);
        this.output.sampleData(source, iMin);
        int i = this.frameBytesRead + iMin;
        this.frameBytesRead = i;
        int i2 = this.frameSize;
        if (i < i2) {
            return;
        }
        this.output.sampleMetadata(this.timeUs, 1, i2, 0, null);
        this.timeUs += this.frameDurationUs;
        this.frameBytesRead = 0;
        this.state = 0;
    }
}
