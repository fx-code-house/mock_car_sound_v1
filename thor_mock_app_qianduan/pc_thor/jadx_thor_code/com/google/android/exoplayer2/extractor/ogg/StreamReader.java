package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
abstract class StreamReader {
    private static final int STATE_END_OF_INPUT = 3;
    private static final int STATE_READ_HEADERS = 0;
    private static final int STATE_READ_PAYLOAD = 2;
    private static final int STATE_SKIP_HEADERS = 1;
    private long currentGranule;
    private ExtractorOutput extractorOutput;
    private boolean formatSet;
    private long lengthOfReadPacket;
    private OggSeeker oggSeeker;
    private long payloadStartPosition;
    private int sampleRate;
    private boolean seekMapSet;
    private int state;
    private long targetGranule;
    private TrackOutput trackOutput;
    private final OggPacket oggPacket = new OggPacket();
    private SetupData setupData = new SetupData();

    protected abstract long preparePayload(ParsableByteArray packet);

    @EnsuresNonNullIf(expression = {"#3.format"}, result = false)
    protected abstract boolean readHeaders(ParsableByteArray packet, long position, SetupData setupData) throws IOException;

    static class SetupData {
        Format format;
        OggSeeker oggSeeker;

        SetupData() {
        }
    }

    void init(ExtractorOutput output, TrackOutput trackOutput) {
        this.extractorOutput = output;
        this.trackOutput = trackOutput;
        reset(true);
    }

    protected void reset(boolean headerData) {
        if (headerData) {
            this.setupData = new SetupData();
            this.payloadStartPosition = 0L;
            this.state = 0;
        } else {
            this.state = 1;
        }
        this.targetGranule = -1L;
        this.currentGranule = 0L;
    }

    final void seek(long position, long timeUs) {
        this.oggPacket.reset();
        if (position == 0) {
            reset(!this.seekMapSet);
        } else if (this.state != 0) {
            this.targetGranule = convertTimeToGranule(timeUs);
            ((OggSeeker) Util.castNonNull(this.oggSeeker)).startSeek(this.targetGranule);
            this.state = 2;
        }
    }

    final int read(ExtractorInput input, PositionHolder seekPosition) throws IOException {
        assertInitialized();
        int i = this.state;
        if (i == 0) {
            return readHeadersAndUpdateState(input);
        }
        if (i == 1) {
            input.skipFully((int) this.payloadStartPosition);
            this.state = 2;
            return 0;
        }
        if (i == 2) {
            Util.castNonNull(this.oggSeeker);
            return readPayload(input, seekPosition);
        }
        if (i == 3) {
            return -1;
        }
        throw new IllegalStateException();
    }

    @EnsuresNonNull({"trackOutput", "extractorOutput"})
    private void assertInitialized() {
        Assertions.checkStateNotNull(this.trackOutput);
        Util.castNonNull(this.extractorOutput);
    }

    @EnsuresNonNullIf(expression = {"setupData.format"}, result = true)
    private boolean readHeaders(ExtractorInput input) throws IOException {
        while (this.oggPacket.populate(input)) {
            this.lengthOfReadPacket = input.getPosition() - this.payloadStartPosition;
            if (!readHeaders(this.oggPacket.getPayload(), this.payloadStartPosition, this.setupData)) {
                return true;
            }
            this.payloadStartPosition = input.getPosition();
        }
        this.state = 3;
        return false;
    }

    @RequiresNonNull({"trackOutput"})
    private int readHeadersAndUpdateState(ExtractorInput input) throws IOException {
        if (!readHeaders(input)) {
            return -1;
        }
        this.sampleRate = this.setupData.format.sampleRate;
        if (!this.formatSet) {
            this.trackOutput.format(this.setupData.format);
            this.formatSet = true;
        }
        if (this.setupData.oggSeeker != null) {
            this.oggSeeker = this.setupData.oggSeeker;
        } else if (input.getLength() == -1) {
            this.oggSeeker = new UnseekableOggSeeker();
        } else {
            OggPageHeader pageHeader = this.oggPacket.getPageHeader();
            this.oggSeeker = new DefaultOggSeeker(this, this.payloadStartPosition, input.getLength(), pageHeader.headerSize + pageHeader.bodySize, pageHeader.granulePosition, (pageHeader.type & 4) != 0);
        }
        this.state = 2;
        this.oggPacket.trimPayload();
        return 0;
    }

    @RequiresNonNull({"trackOutput", "oggSeeker", "extractorOutput"})
    private int readPayload(ExtractorInput input, PositionHolder seekPosition) throws IOException {
        long j = this.oggSeeker.read(input);
        if (j >= 0) {
            seekPosition.position = j;
            return 1;
        }
        if (j < -1) {
            onSeekEnd(-(j + 2));
        }
        if (!this.seekMapSet) {
            this.extractorOutput.seekMap((SeekMap) Assertions.checkStateNotNull(this.oggSeeker.createSeekMap()));
            this.seekMapSet = true;
        }
        if (this.lengthOfReadPacket > 0 || this.oggPacket.populate(input)) {
            this.lengthOfReadPacket = 0L;
            ParsableByteArray payload = this.oggPacket.getPayload();
            long jPreparePayload = preparePayload(payload);
            if (jPreparePayload >= 0) {
                long j2 = this.currentGranule;
                if (j2 + jPreparePayload >= this.targetGranule) {
                    long jConvertGranuleToTime = convertGranuleToTime(j2);
                    this.trackOutput.sampleData(payload, payload.limit());
                    this.trackOutput.sampleMetadata(jConvertGranuleToTime, 1, payload.limit(), 0, null);
                    this.targetGranule = -1L;
                }
            }
            this.currentGranule += jPreparePayload;
            return 0;
        }
        this.state = 3;
        return -1;
    }

    protected long convertGranuleToTime(long granule) {
        return (granule * 1000000) / this.sampleRate;
    }

    protected long convertTimeToGranule(long timeUs) {
        return (this.sampleRate * timeUs) / 1000000;
    }

    protected void onSeekEnd(long currentGranule) {
        this.currentGranule = currentGranule;
    }

    private static final class UnseekableOggSeeker implements OggSeeker {
        @Override // com.google.android.exoplayer2.extractor.ogg.OggSeeker
        public long read(ExtractorInput input) {
            return -1L;
        }

        @Override // com.google.android.exoplayer2.extractor.ogg.OggSeeker
        public void startSeek(long targetGranule) {
        }

        private UnseekableOggSeeker() {
        }

        @Override // com.google.android.exoplayer2.extractor.ogg.OggSeeker
        public SeekMap createSeekMap() {
            return new SeekMap.Unseekable(C.TIME_UNSET);
        }
    }
}
