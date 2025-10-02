package com.google.android.exoplayer2.source.dash.manifest;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Util;
import com.google.common.math.BigIntegerMath;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

/* loaded from: classes.dex */
public abstract class SegmentBase {
    final RangedUri initialization;
    final long presentationTimeOffset;
    final long timescale;

    public SegmentBase(RangedUri initialization, long timescale, long presentationTimeOffset) {
        this.initialization = initialization;
        this.timescale = timescale;
        this.presentationTimeOffset = presentationTimeOffset;
    }

    public RangedUri getInitialization(Representation representation) {
        return this.initialization;
    }

    public long getPresentationTimeOffsetUs() {
        return Util.scaleLargeTimestamp(this.presentationTimeOffset, 1000000L, this.timescale);
    }

    public static class SingleSegmentBase extends SegmentBase {
        final long indexLength;
        final long indexStart;

        public SingleSegmentBase(RangedUri initialization, long timescale, long presentationTimeOffset, long indexStart, long indexLength) {
            super(initialization, timescale, presentationTimeOffset);
            this.indexStart = indexStart;
            this.indexLength = indexLength;
        }

        public SingleSegmentBase() {
            this(null, 1L, 0L, 0L, 0L);
        }

        public RangedUri getIndex() {
            if (this.indexLength <= 0) {
                return null;
            }
            return new RangedUri(null, this.indexStart, this.indexLength);
        }
    }

    public static abstract class MultiSegmentBase extends SegmentBase {
        final long availabilityTimeOffsetUs;
        final long duration;
        private final long periodStartUnixTimeUs;
        final List<SegmentTimelineElement> segmentTimeline;
        final long startNumber;
        private final long timeShiftBufferDepthUs;

        public abstract long getSegmentCount(long periodDurationUs);

        public abstract RangedUri getSegmentUrl(Representation representation, long index);

        public MultiSegmentBase(RangedUri initialization, long timescale, long presentationTimeOffset, long startNumber, long duration, List<SegmentTimelineElement> segmentTimeline, long availabilityTimeOffsetUs, long timeShiftBufferDepthUs, long periodStartUnixTimeUs) {
            super(initialization, timescale, presentationTimeOffset);
            this.startNumber = startNumber;
            this.duration = duration;
            this.segmentTimeline = segmentTimeline;
            this.availabilityTimeOffsetUs = availabilityTimeOffsetUs;
            this.timeShiftBufferDepthUs = timeShiftBufferDepthUs;
            this.periodStartUnixTimeUs = periodStartUnixTimeUs;
        }

        public long getSegmentNum(long timeUs, long periodDurationUs) {
            long firstSegmentNum = getFirstSegmentNum();
            long segmentCount = getSegmentCount(periodDurationUs);
            if (segmentCount == 0) {
                return firstSegmentNum;
            }
            if (this.segmentTimeline == null) {
                long j = this.startNumber + (timeUs / ((this.duration * 1000000) / this.timescale));
                return j < firstSegmentNum ? firstSegmentNum : segmentCount == -1 ? j : Math.min(j, (firstSegmentNum + segmentCount) - 1);
            }
            long j2 = (segmentCount + firstSegmentNum) - 1;
            long j3 = firstSegmentNum;
            while (j3 <= j2) {
                long j4 = ((j2 - j3) / 2) + j3;
                long segmentTimeUs = getSegmentTimeUs(j4);
                if (segmentTimeUs < timeUs) {
                    j3 = j4 + 1;
                } else {
                    if (segmentTimeUs <= timeUs) {
                        return j4;
                    }
                    j2 = j4 - 1;
                }
            }
            return j3 == firstSegmentNum ? j3 : j2;
        }

        public final long getSegmentDurationUs(long sequenceNumber, long periodDurationUs) {
            List<SegmentTimelineElement> list = this.segmentTimeline;
            if (list != null) {
                return (list.get((int) (sequenceNumber - this.startNumber)).duration * 1000000) / this.timescale;
            }
            long segmentCount = getSegmentCount(periodDurationUs);
            if (segmentCount != -1 && sequenceNumber == (getFirstSegmentNum() + segmentCount) - 1) {
                return periodDurationUs - getSegmentTimeUs(sequenceNumber);
            }
            return (this.duration * 1000000) / this.timescale;
        }

        public final long getSegmentTimeUs(long sequenceNumber) {
            long j;
            List<SegmentTimelineElement> list = this.segmentTimeline;
            if (list != null) {
                j = list.get((int) (sequenceNumber - this.startNumber)).startTime - this.presentationTimeOffset;
            } else {
                j = (sequenceNumber - this.startNumber) * this.duration;
            }
            return Util.scaleLargeTimestamp(j, 1000000L, this.timescale);
        }

        public long getFirstSegmentNum() {
            return this.startNumber;
        }

        public long getFirstAvailableSegmentNum(long periodDurationUs, long nowUnixTimeUs) {
            if (getSegmentCount(periodDurationUs) == -1) {
                long j = this.timeShiftBufferDepthUs;
                if (j != C.TIME_UNSET) {
                    return Math.max(getFirstSegmentNum(), getSegmentNum((nowUnixTimeUs - this.periodStartUnixTimeUs) - j, periodDurationUs));
                }
            }
            return getFirstSegmentNum();
        }

        public long getAvailableSegmentCount(long periodDurationUs, long nowUnixTimeUs) {
            long segmentCount = getSegmentCount(periodDurationUs);
            return segmentCount != -1 ? segmentCount : (int) (getSegmentNum((nowUnixTimeUs - this.periodStartUnixTimeUs) + this.availabilityTimeOffsetUs, periodDurationUs) - getFirstAvailableSegmentNum(periodDurationUs, nowUnixTimeUs));
        }

        public long getNextSegmentAvailableTimeUs(long periodDurationUs, long nowUnixTimeUs) {
            if (this.segmentTimeline != null) {
                return C.TIME_UNSET;
            }
            long firstAvailableSegmentNum = getFirstAvailableSegmentNum(periodDurationUs, nowUnixTimeUs) + getAvailableSegmentCount(periodDurationUs, nowUnixTimeUs);
            return (getSegmentTimeUs(firstAvailableSegmentNum) + getSegmentDurationUs(firstAvailableSegmentNum, periodDurationUs)) - this.availabilityTimeOffsetUs;
        }

        public boolean isExplicit() {
            return this.segmentTimeline != null;
        }
    }

    public static final class SegmentList extends MultiSegmentBase {
        final List<RangedUri> mediaSegments;

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public boolean isExplicit() {
            return true;
        }

        public SegmentList(RangedUri initialization, long timescale, long presentationTimeOffset, long startNumber, long duration, List<SegmentTimelineElement> segmentTimeline, long availabilityTimeOffsetUs, List<RangedUri> mediaSegments, long timeShiftBufferDepthUs, long periodStartUnixTimeUs) {
            super(initialization, timescale, presentationTimeOffset, startNumber, duration, segmentTimeline, availabilityTimeOffsetUs, timeShiftBufferDepthUs, periodStartUnixTimeUs);
            this.mediaSegments = mediaSegments;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public RangedUri getSegmentUrl(Representation representation, long sequenceNumber) {
            return this.mediaSegments.get((int) (sequenceNumber - this.startNumber));
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public long getSegmentCount(long periodDurationUs) {
            return this.mediaSegments.size();
        }
    }

    public static final class SegmentTemplate extends MultiSegmentBase {
        final long endNumber;
        final UrlTemplate initializationTemplate;
        final UrlTemplate mediaTemplate;

        public SegmentTemplate(RangedUri initialization, long timescale, long presentationTimeOffset, long startNumber, long endNumber, long duration, List<SegmentTimelineElement> segmentTimeline, long availabilityTimeOffsetUs, UrlTemplate initializationTemplate, UrlTemplate mediaTemplate, long timeShiftBufferDepthUs, long periodStartUnixTimeUs) {
            super(initialization, timescale, presentationTimeOffset, startNumber, duration, segmentTimeline, availabilityTimeOffsetUs, timeShiftBufferDepthUs, periodStartUnixTimeUs);
            this.initializationTemplate = initializationTemplate;
            this.mediaTemplate = mediaTemplate;
            this.endNumber = endNumber;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase
        public RangedUri getInitialization(Representation representation) {
            UrlTemplate urlTemplate = this.initializationTemplate;
            if (urlTemplate != null) {
                return new RangedUri(urlTemplate.buildUri(representation.format.id, 0L, representation.format.bitrate, 0L), 0L, -1L);
            }
            return super.getInitialization(representation);
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public RangedUri getSegmentUrl(Representation representation, long sequenceNumber) {
            long j;
            if (this.segmentTimeline != null) {
                j = this.segmentTimeline.get((int) (sequenceNumber - this.startNumber)).startTime;
            } else {
                j = (sequenceNumber - this.startNumber) * this.duration;
            }
            return new RangedUri(this.mediaTemplate.buildUri(representation.format.id, sequenceNumber, representation.format.bitrate, j), 0L, -1L);
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public long getSegmentCount(long periodDurationUs) {
            if (this.segmentTimeline != null) {
                return this.segmentTimeline.size();
            }
            long j = this.endNumber;
            if (j != -1) {
                return (j - this.startNumber) + 1;
            }
            if (periodDurationUs != C.TIME_UNSET) {
                return BigIntegerMath.divide(BigInteger.valueOf(periodDurationUs).multiply(BigInteger.valueOf(this.timescale)), BigInteger.valueOf(this.duration).multiply(BigInteger.valueOf(1000000L)), RoundingMode.CEILING).longValue();
            }
            return -1L;
        }
    }

    public static final class SegmentTimelineElement {
        final long duration;
        final long startTime;

        public SegmentTimelineElement(long startTime, long duration) {
            this.startTime = startTime;
            this.duration = duration;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SegmentTimelineElement segmentTimelineElement = (SegmentTimelineElement) o;
            return this.startTime == segmentTimelineElement.startTime && this.duration == segmentTimelineElement.duration;
        }

        public int hashCode() {
            return (((int) this.startTime) * 31) + ((int) this.duration);
        }
    }
}
