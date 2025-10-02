package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorUtil;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;

/* loaded from: classes.dex */
final class DefaultOggSeeker implements OggSeeker {
    private static final int DEFAULT_OFFSET = 30000;
    private static final int MATCH_BYTE_RANGE = 100000;
    private static final int MATCH_RANGE = 72000;
    private static final int STATE_IDLE = 4;
    private static final int STATE_READ_LAST_PAGE = 1;
    private static final int STATE_SEEK = 2;
    private static final int STATE_SEEK_TO_END = 0;
    private static final int STATE_SKIP = 3;
    private long end;
    private long endGranule;
    private final OggPageHeader pageHeader;
    private final long payloadEndPosition;
    private final long payloadStartPosition;
    private long positionBeforeSeekToEnd;
    private long start;
    private long startGranule;
    private int state;
    private final StreamReader streamReader;
    private long targetGranule;
    private long totalGranules;

    public DefaultOggSeeker(StreamReader streamReader, long payloadStartPosition, long payloadEndPosition, long firstPayloadPageSize, long firstPayloadPageGranulePosition, boolean firstPayloadPageIsLastPage) {
        Assertions.checkArgument(payloadStartPosition >= 0 && payloadEndPosition > payloadStartPosition);
        this.streamReader = streamReader;
        this.payloadStartPosition = payloadStartPosition;
        this.payloadEndPosition = payloadEndPosition;
        if (firstPayloadPageSize == payloadEndPosition - payloadStartPosition || firstPayloadPageIsLastPage) {
            this.totalGranules = firstPayloadPageGranulePosition;
            this.state = 4;
        } else {
            this.state = 0;
        }
        this.pageHeader = new OggPageHeader();
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.OggSeeker
    public long read(ExtractorInput input) throws IOException {
        int i = this.state;
        if (i == 0) {
            long position = input.getPosition();
            this.positionBeforeSeekToEnd = position;
            this.state = 1;
            long j = this.payloadEndPosition - 65307;
            if (j > position) {
                return j;
            }
        } else if (i != 1) {
            if (i == 2) {
                long nextSeekPosition = getNextSeekPosition(input);
                if (nextSeekPosition != -1) {
                    return nextSeekPosition;
                }
                this.state = 3;
            } else if (i != 3) {
                if (i == 4) {
                    return -1L;
                }
                throw new IllegalStateException();
            }
            skipToPageOfTargetGranule(input);
            this.state = 4;
            return -(this.startGranule + 2);
        }
        this.totalGranules = readGranuleOfLastPage(input);
        this.state = 4;
        return this.positionBeforeSeekToEnd;
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.OggSeeker
    public OggSeekMap createSeekMap() {
        if (this.totalGranules != 0) {
            return new OggSeekMap();
        }
        return null;
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.OggSeeker
    public void startSeek(long targetGranule) {
        this.targetGranule = Util.constrainValue(targetGranule, 0L, this.totalGranules - 1);
        this.state = 2;
        this.start = this.payloadStartPosition;
        this.end = this.payloadEndPosition;
        this.startGranule = 0L;
        this.endGranule = this.totalGranules;
    }

    private long getNextSeekPosition(ExtractorInput input) throws IOException {
        if (this.start == this.end) {
            return -1L;
        }
        long position = input.getPosition();
        if (!this.pageHeader.skipToNextPage(input, this.end)) {
            long j = this.start;
            if (j != position) {
                return j;
            }
            throw new IOException("No ogg page can be found.");
        }
        this.pageHeader.populate(input, false);
        input.resetPeekPosition();
        long j2 = this.targetGranule - this.pageHeader.granulePosition;
        int i = this.pageHeader.headerSize + this.pageHeader.bodySize;
        if (0 <= j2 && j2 < 72000) {
            return -1L;
        }
        if (j2 < 0) {
            this.end = position;
            this.endGranule = this.pageHeader.granulePosition;
        } else {
            this.start = input.getPosition() + i;
            this.startGranule = this.pageHeader.granulePosition;
        }
        long j3 = this.end;
        long j4 = this.start;
        if (j3 - j4 < 100000) {
            this.end = j4;
            return j4;
        }
        long j5 = i;
        long j6 = j2 <= 0 ? 2L : 1L;
        long position2 = input.getPosition();
        long j7 = this.end;
        long j8 = this.start;
        return Util.constrainValue((position2 - (j5 * j6)) + ((j2 * (j7 - j8)) / (this.endGranule - this.startGranule)), j8, j7 - 1);
    }

    private void skipToPageOfTargetGranule(ExtractorInput input) throws IOException {
        while (true) {
            this.pageHeader.skipToNextPage(input);
            this.pageHeader.populate(input, false);
            if (this.pageHeader.granulePosition <= this.targetGranule) {
                input.skipFully(this.pageHeader.headerSize + this.pageHeader.bodySize);
                this.start = input.getPosition();
                this.startGranule = this.pageHeader.granulePosition;
            } else {
                input.resetPeekPosition();
                return;
            }
        }
    }

    long readGranuleOfLastPage(ExtractorInput input) throws IOException {
        this.pageHeader.reset();
        if (!this.pageHeader.skipToNextPage(input)) {
            throw new EOFException();
        }
        this.pageHeader.populate(input, false);
        input.skipFully(this.pageHeader.headerSize + this.pageHeader.bodySize);
        long j = this.pageHeader.granulePosition;
        while ((this.pageHeader.type & 4) != 4 && this.pageHeader.skipToNextPage(input) && input.getPosition() < this.payloadEndPosition && this.pageHeader.populate(input, true) && ExtractorUtil.skipFullyQuietly(input, this.pageHeader.headerSize + this.pageHeader.bodySize)) {
            j = this.pageHeader.granulePosition;
        }
        return j;
    }

    private final class OggSeekMap implements SeekMap {
        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public boolean isSeekable() {
            return true;
        }

        private OggSeekMap() {
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public SeekMap.SeekPoints getSeekPoints(long timeUs) {
            return new SeekMap.SeekPoints(new SeekPoint(timeUs, Util.constrainValue((DefaultOggSeeker.this.payloadStartPosition + ((DefaultOggSeeker.this.streamReader.convertTimeToGranule(timeUs) * (DefaultOggSeeker.this.payloadEndPosition - DefaultOggSeeker.this.payloadStartPosition)) / DefaultOggSeeker.this.totalGranules)) - 30000, DefaultOggSeeker.this.payloadStartPosition, DefaultOggSeeker.this.payloadEndPosition - 1)));
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public long getDurationUs() {
            return DefaultOggSeeker.this.streamReader.convertGranuleToTime(DefaultOggSeeker.this.totalGranules);
        }
    }
}
