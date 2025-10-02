package com.google.android.exoplayer2.transformer;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.SlowMotionData;
import com.google.android.exoplayer2.metadata.mp4.SmtaMetadataEntry;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableList;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
final class SefSlowMotionVideoSampleTransformer implements SampleTransformer {
    static final int INPUT_FRAME_RATE = 30;
    private static final int NAL_START_CODE_LENGTH = NalUnitUtil.NAL_START_CODE.length;
    private static final int NAL_UNIT_TYPE_PREFIX = 14;
    private static final int TARGET_OUTPUT_FRAME_RATE = 30;
    private final float captureFrameRate;
    private SegmentInfo currentSegmentInfo;
    private long frameTimeDeltaUs;
    private final int inputMaxLayer;
    private SegmentInfo nextSegmentInfo;
    private final int normalSpeedMaxLayer;
    private final byte[] scratch = new byte[NAL_START_CODE_LENGTH];
    private final Iterator<SlowMotionData.Segment> segmentIterator;
    private final SlowMotionData slowMotionData;

    private static final class MetadataInfo {
        public float captureFrameRate = -3.4028235E38f;
        public int inputMaxLayer = -1;
        public int normalSpeedMaxLayer = -1;
        public SlowMotionData slowMotionData;
    }

    public SefSlowMotionVideoSampleTransformer(Format format) {
        MetadataInfo metadataInfo = getMetadataInfo(format.metadata);
        SlowMotionData slowMotionData = metadataInfo.slowMotionData;
        this.slowMotionData = slowMotionData;
        Iterator<SlowMotionData.Segment> it = (slowMotionData != null ? slowMotionData.segments : ImmutableList.of()).iterator();
        this.segmentIterator = it;
        this.captureFrameRate = metadataInfo.captureFrameRate;
        int i = metadataInfo.inputMaxLayer;
        this.inputMaxLayer = i;
        int i2 = metadataInfo.normalSpeedMaxLayer;
        this.normalSpeedMaxLayer = i2;
        this.nextSegmentInfo = it.hasNext() ? new SegmentInfo(it.next(), i, i2) : null;
        if (slowMotionData != null) {
            boolean zEquals = MimeTypes.VIDEO_H264.equals(format.sampleMimeType);
            String strValueOf = String.valueOf(format.sampleMimeType);
            Assertions.checkArgument(zEquals, strValueOf.length() != 0 ? "Unsupported MIME type for SEF slow motion video track: ".concat(strValueOf) : new String("Unsupported MIME type for SEF slow motion video track: "));
        }
    }

    @Override // com.google.android.exoplayer2.transformer.SampleTransformer
    public void transformSample(DecoderInputBuffer buffer) {
        if (this.slowMotionData == null) {
            return;
        }
        ByteBuffer byteBuffer = (ByteBuffer) Util.castNonNull(buffer.data);
        byteBuffer.position(byteBuffer.position() + NAL_START_CODE_LENGTH);
        boolean z = false;
        byteBuffer.get(this.scratch, 0, 4);
        byte[] bArr = this.scratch;
        int i = bArr[0] & Ascii.US;
        boolean z2 = ((bArr[1] & 255) >> 7) == 1;
        if (i == 14 && z2) {
            z = true;
        }
        Assertions.checkState(z, "Missing SVC extension prefix NAL unit.");
        if (processCurrentFrame((this.scratch[3] & 255) >> 5, buffer.timeUs)) {
            buffer.timeUs = getCurrentFrameOutputTimeUs(buffer.timeUs);
            skipToNextNalUnit(byteBuffer);
        } else {
            buffer.data = null;
        }
    }

    boolean processCurrentFrame(int layer, long timeUs) {
        while (true) {
            SegmentInfo segmentInfo = this.nextSegmentInfo;
            if (segmentInfo == null || timeUs < segmentInfo.endTimeUs) {
                break;
            }
            enterNextSegment();
        }
        SegmentInfo segmentInfo2 = this.nextSegmentInfo;
        if (segmentInfo2 != null && timeUs >= segmentInfo2.startTimeUs) {
            enterNextSegment();
        } else {
            SegmentInfo segmentInfo3 = this.currentSegmentInfo;
            if (segmentInfo3 != null && timeUs >= segmentInfo3.endTimeUs) {
                leaveCurrentSegment();
            }
        }
        SegmentInfo segmentInfo4 = this.currentSegmentInfo;
        return layer <= (segmentInfo4 != null ? segmentInfo4.maxLayer : this.normalSpeedMaxLayer) || shouldKeepFrameForOutputValidity(layer, timeUs);
    }

    private void enterNextSegment() {
        if (this.currentSegmentInfo != null) {
            leaveCurrentSegment();
        }
        this.currentSegmentInfo = this.nextSegmentInfo;
        this.nextSegmentInfo = this.segmentIterator.hasNext() ? new SegmentInfo(this.segmentIterator.next(), this.inputMaxLayer, this.normalSpeedMaxLayer) : null;
    }

    @RequiresNonNull({"currentSegmentInfo"})
    private void leaveCurrentSegment() {
        this.frameTimeDeltaUs += (this.currentSegmentInfo.endTimeUs - this.currentSegmentInfo.startTimeUs) * (this.currentSegmentInfo.speedDivisor - 1);
        this.currentSegmentInfo = null;
    }

    private boolean shouldKeepFrameForOutputValidity(int layer, long timeUs) {
        SegmentInfo segmentInfo = this.nextSegmentInfo;
        if (segmentInfo != null && layer < segmentInfo.maxLayer) {
            long j = ((this.nextSegmentInfo.startTimeUs - timeUs) * 30) / 1000000;
            float f = (-(1 << (this.inputMaxLayer - this.nextSegmentInfo.maxLayer))) + 0.45f;
            for (int i = 1; i < this.nextSegmentInfo.maxLayer && j < (1 << (this.inputMaxLayer - i)) + f; i++) {
                if (layer <= i) {
                    return true;
                }
            }
        }
        return false;
    }

    long getCurrentFrameOutputTimeUs(long inputTimeUs) {
        long j = this.frameTimeDeltaUs + inputTimeUs;
        SegmentInfo segmentInfo = this.currentSegmentInfo;
        if (segmentInfo != null) {
            j += (inputTimeUs - segmentInfo.startTimeUs) * (this.currentSegmentInfo.speedDivisor - 1);
        }
        return Math.round((j * 30) / this.captureFrameRate);
    }

    private void skipToNextNalUnit(ByteBuffer data) {
        int iPosition = data.position();
        while (true) {
            int iRemaining = data.remaining();
            int i = NAL_START_CODE_LENGTH;
            if (iRemaining >= i) {
                data.get(this.scratch, 0, i);
                if (Arrays.equals(this.scratch, NalUnitUtil.NAL_START_CODE)) {
                    data.position(iPosition);
                    return;
                } else {
                    iPosition++;
                    data.position(iPosition);
                }
            } else {
                throw new IllegalStateException("Could not find NAL unit start code.");
            }
        }
    }

    private static MetadataInfo getMetadataInfo(Metadata metadata) {
        MetadataInfo metadataInfo = new MetadataInfo();
        if (metadata == null) {
            return metadataInfo;
        }
        for (int i = 0; i < metadata.length(); i++) {
            Metadata.Entry entry = metadata.get(i);
            if (entry instanceof SmtaMetadataEntry) {
                SmtaMetadataEntry smtaMetadataEntry = (SmtaMetadataEntry) entry;
                metadataInfo.captureFrameRate = smtaMetadataEntry.captureFrameRate;
                metadataInfo.inputMaxLayer = smtaMetadataEntry.svcTemporalLayerCount - 1;
            } else if (entry instanceof SlowMotionData) {
                metadataInfo.slowMotionData = (SlowMotionData) entry;
            }
        }
        if (metadataInfo.slowMotionData == null) {
            return metadataInfo;
        }
        Assertions.checkState(metadataInfo.inputMaxLayer != -1, "SVC temporal layer count not found.");
        Assertions.checkState(metadataInfo.captureFrameRate != -3.4028235E38f, "Capture frame rate not found.");
        Assertions.checkState(metadataInfo.captureFrameRate % 1.0f == 0.0f && metadataInfo.captureFrameRate % 30.0f == 0.0f, new StringBuilder(43).append("Invalid capture frame rate: ").append(metadataInfo.captureFrameRate).toString());
        int i2 = ((int) metadataInfo.captureFrameRate) / 30;
        int i3 = metadataInfo.inputMaxLayer;
        while (true) {
            if (i3 < 0) {
                break;
            }
            if ((i2 & 1) == 1) {
                Assertions.checkState((i2 >> 1) == 0, new StringBuilder(84).append("Could not compute normal speed max SVC layer for capture frame rate  ").append(metadataInfo.captureFrameRate).toString());
                metadataInfo.normalSpeedMaxLayer = i3;
            } else {
                i2 >>= 1;
                i3--;
            }
        }
        return metadataInfo;
    }

    private static final class SegmentInfo {
        public final long endTimeUs;
        public final int maxLayer;
        public final int speedDivisor;
        public final long startTimeUs;

        public SegmentInfo(SlowMotionData.Segment segment, int inputMaxLayer, int normalSpeedLayer) {
            this.startTimeUs = C.msToUs(segment.startTimeMs);
            this.endTimeUs = C.msToUs(segment.endTimeMs);
            int i = segment.speedDivisor;
            this.speedDivisor = i;
            this.maxLayer = getSlowMotionMaxLayer(i, inputMaxLayer, normalSpeedLayer);
        }

        private static int getSlowMotionMaxLayer(int speedDivisor, int inputMaxLayer, int normalSpeedMaxLayer) {
            int i = speedDivisor;
            while (true) {
                if (i <= 0) {
                    break;
                }
                if ((i & 1) == 1) {
                    Assertions.checkState((i >> 1) == 0, new StringBuilder(34).append("Invalid speed divisor: ").append(speedDivisor).toString());
                } else {
                    normalSpeedMaxLayer++;
                    i >>= 1;
                }
            }
            return Math.min(normalSpeedMaxLayer, inputMaxLayer);
        }
    }
}
