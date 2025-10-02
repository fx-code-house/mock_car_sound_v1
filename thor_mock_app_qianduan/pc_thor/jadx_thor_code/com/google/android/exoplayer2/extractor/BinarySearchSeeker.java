package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class BinarySearchSeeker {
    private static final long MAX_SKIP_BYTES = 262144;
    private final int minimumSearchRange;
    protected final BinarySearchSeekMap seekMap;
    protected SeekOperationParams seekOperationParams;
    protected final TimestampSeeker timestampSeeker;

    public static final class DefaultSeekTimestampConverter implements SeekTimestampConverter {
        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.SeekTimestampConverter
        public long timeUsToTargetTime(long timeUs) {
            return timeUs;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public interface SeekTimestampConverter {
        long timeUsToTargetTime(long timeUs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public interface TimestampSeeker {
        default void onSeekFinished() {
        }

        TimestampSearchResult searchForTimestamp(ExtractorInput input, long targetTimestamp) throws IOException;
    }

    protected void onSeekOperationFinished(boolean foundTargetFrame, long resultPosition) {
    }

    protected BinarySearchSeeker(SeekTimestampConverter seekTimestampConverter, TimestampSeeker timestampSeeker, long durationUs, long floorTimePosition, long ceilingTimePosition, long floorBytePosition, long ceilingBytePosition, long approxBytesPerFrame, int minimumSearchRange) {
        this.timestampSeeker = timestampSeeker;
        this.minimumSearchRange = minimumSearchRange;
        this.seekMap = new BinarySearchSeekMap(seekTimestampConverter, durationUs, floorTimePosition, ceilingTimePosition, floorBytePosition, ceilingBytePosition, approxBytesPerFrame);
    }

    public final SeekMap getSeekMap() {
        return this.seekMap;
    }

    public final void setSeekTargetUs(long timeUs) {
        SeekOperationParams seekOperationParams = this.seekOperationParams;
        if (seekOperationParams == null || seekOperationParams.getSeekTimeUs() != timeUs) {
            this.seekOperationParams = createSeekParamsForTargetTimeUs(timeUs);
        }
    }

    public final boolean isSeeking() {
        return this.seekOperationParams != null;
    }

    public int handlePendingSeek(ExtractorInput input, PositionHolder seekPositionHolder) throws IOException {
        while (true) {
            SeekOperationParams seekOperationParams = (SeekOperationParams) Assertions.checkStateNotNull(this.seekOperationParams);
            long floorBytePosition = seekOperationParams.getFloorBytePosition();
            long ceilingBytePosition = seekOperationParams.getCeilingBytePosition();
            long nextSearchBytePosition = seekOperationParams.getNextSearchBytePosition();
            if (ceilingBytePosition - floorBytePosition <= this.minimumSearchRange) {
                markSeekOperationFinished(false, floorBytePosition);
                return seekToPosition(input, floorBytePosition, seekPositionHolder);
            }
            if (!skipInputUntilPosition(input, nextSearchBytePosition)) {
                return seekToPosition(input, nextSearchBytePosition, seekPositionHolder);
            }
            input.resetPeekPosition();
            TimestampSearchResult timestampSearchResultSearchForTimestamp = this.timestampSeeker.searchForTimestamp(input, seekOperationParams.getTargetTimePosition());
            int i = timestampSearchResultSearchForTimestamp.type;
            if (i == -3) {
                markSeekOperationFinished(false, nextSearchBytePosition);
                return seekToPosition(input, nextSearchBytePosition, seekPositionHolder);
            }
            if (i == -2) {
                seekOperationParams.updateSeekFloor(timestampSearchResultSearchForTimestamp.timestampToUpdate, timestampSearchResultSearchForTimestamp.bytePositionToUpdate);
            } else {
                if (i != -1) {
                    if (i != 0) {
                        throw new IllegalStateException("Invalid case");
                    }
                    skipInputUntilPosition(input, timestampSearchResultSearchForTimestamp.bytePositionToUpdate);
                    markSeekOperationFinished(true, timestampSearchResultSearchForTimestamp.bytePositionToUpdate);
                    return seekToPosition(input, timestampSearchResultSearchForTimestamp.bytePositionToUpdate, seekPositionHolder);
                }
                seekOperationParams.updateSeekCeiling(timestampSearchResultSearchForTimestamp.timestampToUpdate, timestampSearchResultSearchForTimestamp.bytePositionToUpdate);
            }
        }
    }

    protected SeekOperationParams createSeekParamsForTargetTimeUs(long timeUs) {
        return new SeekOperationParams(timeUs, this.seekMap.timeUsToTargetTime(timeUs), this.seekMap.floorTimePosition, this.seekMap.ceilingTimePosition, this.seekMap.floorBytePosition, this.seekMap.ceilingBytePosition, this.seekMap.approxBytesPerFrame);
    }

    protected final void markSeekOperationFinished(boolean foundTargetFrame, long resultPosition) {
        this.seekOperationParams = null;
        this.timestampSeeker.onSeekFinished();
        onSeekOperationFinished(foundTargetFrame, resultPosition);
    }

    protected final boolean skipInputUntilPosition(ExtractorInput input, long position) throws IOException {
        long position2 = position - input.getPosition();
        if (position2 < 0 || position2 > 262144) {
            return false;
        }
        input.skipFully((int) position2);
        return true;
    }

    protected final int seekToPosition(ExtractorInput input, long position, PositionHolder seekPositionHolder) {
        if (position == input.getPosition()) {
            return 0;
        }
        seekPositionHolder.position = position;
        return 1;
    }

    protected static class SeekOperationParams {
        private final long approxBytesPerFrame;
        private long ceilingBytePosition;
        private long ceilingTimePosition;
        private long floorBytePosition;
        private long floorTimePosition;
        private long nextSearchBytePosition;
        private final long seekTimeUs;
        private final long targetTimePosition;

        protected static long calculateNextSearchBytePosition(long targetTimePosition, long floorTimePosition, long ceilingTimePosition, long floorBytePosition, long ceilingBytePosition, long approxBytesPerFrame) {
            if (floorBytePosition + 1 >= ceilingBytePosition || floorTimePosition + 1 >= ceilingTimePosition) {
                return floorBytePosition;
            }
            long j = (long) ((targetTimePosition - floorTimePosition) * ((ceilingBytePosition - floorBytePosition) / (ceilingTimePosition - floorTimePosition)));
            return Util.constrainValue(((j + floorBytePosition) - approxBytesPerFrame) - (j / 20), floorBytePosition, ceilingBytePosition - 1);
        }

        protected SeekOperationParams(long seekTimeUs, long targetTimePosition, long floorTimePosition, long ceilingTimePosition, long floorBytePosition, long ceilingBytePosition, long approxBytesPerFrame) {
            this.seekTimeUs = seekTimeUs;
            this.targetTimePosition = targetTimePosition;
            this.floorTimePosition = floorTimePosition;
            this.ceilingTimePosition = ceilingTimePosition;
            this.floorBytePosition = floorBytePosition;
            this.ceilingBytePosition = ceilingBytePosition;
            this.approxBytesPerFrame = approxBytesPerFrame;
            this.nextSearchBytePosition = calculateNextSearchBytePosition(targetTimePosition, floorTimePosition, ceilingTimePosition, floorBytePosition, ceilingBytePosition, approxBytesPerFrame);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getFloorBytePosition() {
            return this.floorBytePosition;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getCeilingBytePosition() {
            return this.ceilingBytePosition;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getTargetTimePosition() {
            return this.targetTimePosition;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getSeekTimeUs() {
            return this.seekTimeUs;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateSeekFloor(long floorTimePosition, long floorBytePosition) {
            this.floorTimePosition = floorTimePosition;
            this.floorBytePosition = floorBytePosition;
            updateNextSearchBytePosition();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateSeekCeiling(long ceilingTimePosition, long ceilingBytePosition) {
            this.ceilingTimePosition = ceilingTimePosition;
            this.ceilingBytePosition = ceilingBytePosition;
            updateNextSearchBytePosition();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getNextSearchBytePosition() {
            return this.nextSearchBytePosition;
        }

        private void updateNextSearchBytePosition() {
            this.nextSearchBytePosition = calculateNextSearchBytePosition(this.targetTimePosition, this.floorTimePosition, this.ceilingTimePosition, this.floorBytePosition, this.ceilingBytePosition, this.approxBytesPerFrame);
        }
    }

    public static final class TimestampSearchResult {
        public static final TimestampSearchResult NO_TIMESTAMP_IN_RANGE_RESULT = new TimestampSearchResult(-3, C.TIME_UNSET, -1);
        public static final int TYPE_NO_TIMESTAMP = -3;
        public static final int TYPE_POSITION_OVERESTIMATED = -1;
        public static final int TYPE_POSITION_UNDERESTIMATED = -2;
        public static final int TYPE_TARGET_TIMESTAMP_FOUND = 0;
        private final long bytePositionToUpdate;
        private final long timestampToUpdate;
        private final int type;

        private TimestampSearchResult(int type, long timestampToUpdate, long bytePositionToUpdate) {
            this.type = type;
            this.timestampToUpdate = timestampToUpdate;
            this.bytePositionToUpdate = bytePositionToUpdate;
        }

        public static TimestampSearchResult overestimatedResult(long newCeilingTimestamp, long newCeilingBytePosition) {
            return new TimestampSearchResult(-1, newCeilingTimestamp, newCeilingBytePosition);
        }

        public static TimestampSearchResult underestimatedResult(long newFloorTimestamp, long newCeilingBytePosition) {
            return new TimestampSearchResult(-2, newFloorTimestamp, newCeilingBytePosition);
        }

        public static TimestampSearchResult targetFoundResult(long resultBytePosition) {
            return new TimestampSearchResult(0, C.TIME_UNSET, resultBytePosition);
        }
    }

    public static class BinarySearchSeekMap implements SeekMap {
        private final long approxBytesPerFrame;
        private final long ceilingBytePosition;
        private final long ceilingTimePosition;
        private final long durationUs;
        private final long floorBytePosition;
        private final long floorTimePosition;
        private final SeekTimestampConverter seekTimestampConverter;

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public boolean isSeekable() {
            return true;
        }

        public BinarySearchSeekMap(SeekTimestampConverter seekTimestampConverter, long durationUs, long floorTimePosition, long ceilingTimePosition, long floorBytePosition, long ceilingBytePosition, long approxBytesPerFrame) {
            this.seekTimestampConverter = seekTimestampConverter;
            this.durationUs = durationUs;
            this.floorTimePosition = floorTimePosition;
            this.ceilingTimePosition = ceilingTimePosition;
            this.floorBytePosition = floorBytePosition;
            this.ceilingBytePosition = ceilingBytePosition;
            this.approxBytesPerFrame = approxBytesPerFrame;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public SeekMap.SeekPoints getSeekPoints(long timeUs) {
            return new SeekMap.SeekPoints(new SeekPoint(timeUs, SeekOperationParams.calculateNextSearchBytePosition(this.seekTimestampConverter.timeUsToTargetTime(timeUs), this.floorTimePosition, this.ceilingTimePosition, this.floorBytePosition, this.ceilingBytePosition, this.approxBytesPerFrame)));
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public long getDurationUs() {
            return this.durationUs;
        }

        public long timeUsToTargetTime(long timeUs) {
            return this.seekTimestampConverter.timeUsToTargetTime(timeUs);
        }
    }
}
