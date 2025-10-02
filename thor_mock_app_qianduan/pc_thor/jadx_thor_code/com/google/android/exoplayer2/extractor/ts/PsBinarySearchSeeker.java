package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.audio.SilenceSkippingAudioProcessor;
import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes.dex */
final class PsBinarySearchSeeker extends BinarySearchSeeker {
    private static final int MINIMUM_SEARCH_RANGE_BYTES = 1000;
    private static final long SEEK_TOLERANCE_US = 100000;
    private static final int TIMESTAMP_SEARCH_BYTES = 20000;

    public PsBinarySearchSeeker(TimestampAdjuster scrTimestampAdjuster, long streamDurationUs, long inputLength) {
        super(new BinarySearchSeeker.DefaultSeekTimestampConverter(), new PsScrSeeker(scrTimestampAdjuster), streamDurationUs, 0L, streamDurationUs + 1, 0L, inputLength, 188L, 1000);
    }

    private static final class PsScrSeeker implements BinarySearchSeeker.TimestampSeeker {
        private final ParsableByteArray packetBuffer;
        private final TimestampAdjuster scrTimestampAdjuster;

        private PsScrSeeker(TimestampAdjuster scrTimestampAdjuster) {
            this.scrTimestampAdjuster = scrTimestampAdjuster;
            this.packetBuffer = new ParsableByteArray();
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public BinarySearchSeeker.TimestampSearchResult searchForTimestamp(ExtractorInput input, long targetTimestamp) throws IOException {
            long position = input.getPosition();
            int iMin = (int) Math.min(SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US, input.getLength() - position);
            this.packetBuffer.reset(iMin);
            input.peekFully(this.packetBuffer.getData(), 0, iMin);
            return searchForScrValueInBuffer(this.packetBuffer, targetTimestamp, position);
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public void onSeekFinished() {
            this.packetBuffer.reset(Util.EMPTY_BYTE_ARRAY);
        }

        private BinarySearchSeeker.TimestampSearchResult searchForScrValueInBuffer(ParsableByteArray packetBuffer, long targetScrTimeUs, long bufferStartOffset) {
            int position = -1;
            int position2 = -1;
            long j = -9223372036854775807L;
            while (packetBuffer.bytesLeft() >= 4) {
                if (PsBinarySearchSeeker.peekIntAtPosition(packetBuffer.getData(), packetBuffer.getPosition()) != 442) {
                    packetBuffer.skipBytes(1);
                } else {
                    packetBuffer.skipBytes(4);
                    long scrValueFromPack = PsDurationReader.readScrValueFromPack(packetBuffer);
                    if (scrValueFromPack != C.TIME_UNSET) {
                        long jAdjustTsTimestamp = this.scrTimestampAdjuster.adjustTsTimestamp(scrValueFromPack);
                        if (jAdjustTsTimestamp > targetScrTimeUs) {
                            if (j == C.TIME_UNSET) {
                                return BinarySearchSeeker.TimestampSearchResult.overestimatedResult(jAdjustTsTimestamp, bufferStartOffset);
                            }
                            return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(bufferStartOffset + position2);
                        }
                        if (PsBinarySearchSeeker.SEEK_TOLERANCE_US + jAdjustTsTimestamp > targetScrTimeUs) {
                            return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(bufferStartOffset + packetBuffer.getPosition());
                        }
                        position2 = packetBuffer.getPosition();
                        j = jAdjustTsTimestamp;
                    }
                    skipToEndOfCurrentPack(packetBuffer);
                    position = packetBuffer.getPosition();
                }
            }
            if (j != C.TIME_UNSET) {
                return BinarySearchSeeker.TimestampSearchResult.underestimatedResult(j, bufferStartOffset + position);
            }
            return BinarySearchSeeker.TimestampSearchResult.NO_TIMESTAMP_IN_RANGE_RESULT;
        }

        private static void skipToEndOfCurrentPack(ParsableByteArray packetBuffer) {
            int iPeekIntAtPosition;
            int iLimit = packetBuffer.limit();
            if (packetBuffer.bytesLeft() < 10) {
                packetBuffer.setPosition(iLimit);
                return;
            }
            packetBuffer.skipBytes(9);
            int unsignedByte = packetBuffer.readUnsignedByte() & 7;
            if (packetBuffer.bytesLeft() < unsignedByte) {
                packetBuffer.setPosition(iLimit);
                return;
            }
            packetBuffer.skipBytes(unsignedByte);
            if (packetBuffer.bytesLeft() >= 4) {
                if (PsBinarySearchSeeker.peekIntAtPosition(packetBuffer.getData(), packetBuffer.getPosition()) == 443) {
                    packetBuffer.skipBytes(4);
                    int unsignedShort = packetBuffer.readUnsignedShort();
                    if (packetBuffer.bytesLeft() < unsignedShort) {
                        packetBuffer.setPosition(iLimit);
                        return;
                    }
                    packetBuffer.skipBytes(unsignedShort);
                }
                while (packetBuffer.bytesLeft() >= 4 && (iPeekIntAtPosition = PsBinarySearchSeeker.peekIntAtPosition(packetBuffer.getData(), packetBuffer.getPosition())) != 442 && iPeekIntAtPosition != 441 && (iPeekIntAtPosition >>> 8) == 1) {
                    packetBuffer.skipBytes(4);
                    if (packetBuffer.bytesLeft() < 2) {
                        packetBuffer.setPosition(iLimit);
                        return;
                    }
                    packetBuffer.setPosition(Math.min(packetBuffer.limit(), packetBuffer.getPosition() + packetBuffer.readUnsignedShort()));
                }
                return;
            }
            packetBuffer.setPosition(iLimit);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int peekIntAtPosition(byte[] data, int position) {
        return (data[position + 3] & 255) | ((data[position] & 255) << 24) | ((data[position + 1] & 255) << 16) | ((data[position + 2] & 255) << 8);
    }
}
