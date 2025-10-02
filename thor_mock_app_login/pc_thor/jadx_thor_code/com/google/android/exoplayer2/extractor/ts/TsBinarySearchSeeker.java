package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes.dex */
final class TsBinarySearchSeeker extends BinarySearchSeeker {
    private static final int MINIMUM_SEARCH_RANGE_BYTES = 940;
    private static final long SEEK_TOLERANCE_US = 100000;

    public TsBinarySearchSeeker(TimestampAdjuster pcrTimestampAdjuster, long streamDurationUs, long inputLength, int pcrPid, int timestampSearchBytes) {
        super(new BinarySearchSeeker.DefaultSeekTimestampConverter(), new TsPcrSeeker(pcrPid, pcrTimestampAdjuster, timestampSearchBytes), streamDurationUs, 0L, streamDurationUs + 1, 0L, inputLength, 188L, MINIMUM_SEARCH_RANGE_BYTES);
    }

    private static final class TsPcrSeeker implements BinarySearchSeeker.TimestampSeeker {
        private final ParsableByteArray packetBuffer = new ParsableByteArray();
        private final int pcrPid;
        private final TimestampAdjuster pcrTimestampAdjuster;
        private final int timestampSearchBytes;

        public TsPcrSeeker(int pcrPid, TimestampAdjuster pcrTimestampAdjuster, int timestampSearchBytes) {
            this.pcrPid = pcrPid;
            this.pcrTimestampAdjuster = pcrTimestampAdjuster;
            this.timestampSearchBytes = timestampSearchBytes;
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public BinarySearchSeeker.TimestampSearchResult searchForTimestamp(ExtractorInput input, long targetTimestamp) throws IOException {
            long position = input.getPosition();
            int iMin = (int) Math.min(this.timestampSearchBytes, input.getLength() - position);
            this.packetBuffer.reset(iMin);
            input.peekFully(this.packetBuffer.getData(), 0, iMin);
            return searchForPcrValueInBuffer(this.packetBuffer, targetTimestamp, position);
        }

        private BinarySearchSeeker.TimestampSearchResult searchForPcrValueInBuffer(ParsableByteArray packetBuffer, long targetPcrTimeUs, long bufferStartOffset) {
            int iFindSyncBytePosition;
            int iFindSyncBytePosition2;
            int iLimit = packetBuffer.limit();
            long j = -1;
            long j2 = -1;
            long j3 = -9223372036854775807L;
            while (packetBuffer.bytesLeft() >= 188 && (iFindSyncBytePosition2 = (iFindSyncBytePosition = TsUtil.findSyncBytePosition(packetBuffer.getData(), packetBuffer.getPosition(), iLimit)) + TsExtractor.TS_PACKET_SIZE) <= iLimit) {
                long pcrFromPacket = TsUtil.readPcrFromPacket(packetBuffer, iFindSyncBytePosition, this.pcrPid);
                if (pcrFromPacket != C.TIME_UNSET) {
                    long jAdjustTsTimestamp = this.pcrTimestampAdjuster.adjustTsTimestamp(pcrFromPacket);
                    if (jAdjustTsTimestamp > targetPcrTimeUs) {
                        if (j3 == C.TIME_UNSET) {
                            return BinarySearchSeeker.TimestampSearchResult.overestimatedResult(jAdjustTsTimestamp, bufferStartOffset);
                        }
                        return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(bufferStartOffset + j2);
                    }
                    if (TsBinarySearchSeeker.SEEK_TOLERANCE_US + jAdjustTsTimestamp > targetPcrTimeUs) {
                        return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(bufferStartOffset + iFindSyncBytePosition);
                    }
                    j2 = iFindSyncBytePosition;
                    j3 = jAdjustTsTimestamp;
                }
                packetBuffer.setPosition(iFindSyncBytePosition2);
                j = iFindSyncBytePosition2;
            }
            if (j3 != C.TIME_UNSET) {
                return BinarySearchSeeker.TimestampSearchResult.underestimatedResult(j3, bufferStartOffset + j);
            }
            return BinarySearchSeeker.TimestampSearchResult.NO_TIMESTAMP_IN_RANGE_RESULT;
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public void onSeekFinished() {
            this.packetBuffer.reset(Util.EMPTY_BYTE_ARRAY);
        }
    }
}
