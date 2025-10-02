package com.google.android.exoplayer2.extractor.ts;

import android.util.SparseArray;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public final class H264Reader implements ElementaryStreamReader {
    private static final int NAL_UNIT_TYPE_PPS = 8;
    private static final int NAL_UNIT_TYPE_SEI = 6;
    private static final int NAL_UNIT_TYPE_SPS = 7;
    private final boolean allowNonIdrKeyframes;
    private final boolean detectAccessUnits;
    private String formatId;
    private boolean hasOutputFormat;
    private TrackOutput output;
    private long pesTimeUs;
    private boolean randomAccessIndicator;
    private SampleReader sampleReader;
    private final SeiReader seiReader;
    private long totalBytesWritten;
    private final boolean[] prefixFlags = new boolean[3];
    private final NalUnitTargetBuffer sps = new NalUnitTargetBuffer(7, 128);
    private final NalUnitTargetBuffer pps = new NalUnitTargetBuffer(8, 128);
    private final NalUnitTargetBuffer sei = new NalUnitTargetBuffer(6, 128);
    private final ParsableByteArray seiWrapper = new ParsableByteArray();

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public H264Reader(SeiReader seiReader, boolean allowNonIdrKeyframes, boolean detectAccessUnits) {
        this.seiReader = seiReader;
        this.allowNonIdrKeyframes = allowNonIdrKeyframes;
        this.detectAccessUnits = detectAccessUnits;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.totalBytesWritten = 0L;
        this.randomAccessIndicator = false;
        NalUnitUtil.clearPrefixFlags(this.prefixFlags);
        this.sps.reset();
        this.pps.reset();
        this.sei.reset();
        SampleReader sampleReader = this.sampleReader;
        if (sampleReader != null) {
            sampleReader.reset();
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        idGenerator.generateNewId();
        this.formatId = idGenerator.getFormatId();
        this.output = extractorOutput.track(idGenerator.getTrackId(), 2);
        this.sampleReader = new SampleReader(this.output, this.allowNonIdrKeyframes, this.detectAccessUnits);
        this.seiReader.createTracks(extractorOutput, idGenerator);
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long pesTimeUs, int flags) {
        this.pesTimeUs = pesTimeUs;
        this.randomAccessIndicator |= (flags & 2) != 0;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray data) {
        assertTracksCreated();
        int position = data.getPosition();
        int iLimit = data.limit();
        byte[] data2 = data.getData();
        this.totalBytesWritten += data.bytesLeft();
        this.output.sampleData(data, data.bytesLeft());
        while (true) {
            int iFindNalUnit = NalUnitUtil.findNalUnit(data2, position, iLimit, this.prefixFlags);
            if (iFindNalUnit == iLimit) {
                nalUnitData(data2, position, iLimit);
                return;
            }
            int nalUnitType = NalUnitUtil.getNalUnitType(data2, iFindNalUnit);
            int i = iFindNalUnit - position;
            if (i > 0) {
                nalUnitData(data2, position, iFindNalUnit);
            }
            int i2 = iLimit - iFindNalUnit;
            long j = this.totalBytesWritten - i2;
            endNalUnit(j, i2, i < 0 ? -i : 0, this.pesTimeUs);
            startNalUnit(j, nalUnitType, this.pesTimeUs);
            position = iFindNalUnit + 3;
        }
    }

    @RequiresNonNull({"sampleReader"})
    private void startNalUnit(long position, int nalUnitType, long pesTimeUs) {
        if (!this.hasOutputFormat || this.sampleReader.needsSpsPps()) {
            this.sps.startNalUnit(nalUnitType);
            this.pps.startNalUnit(nalUnitType);
        }
        this.sei.startNalUnit(nalUnitType);
        this.sampleReader.startNalUnit(position, nalUnitType, pesTimeUs);
    }

    @RequiresNonNull({"sampleReader"})
    private void nalUnitData(byte[] dataArray, int offset, int limit) {
        if (!this.hasOutputFormat || this.sampleReader.needsSpsPps()) {
            this.sps.appendToNalUnit(dataArray, offset, limit);
            this.pps.appendToNalUnit(dataArray, offset, limit);
        }
        this.sei.appendToNalUnit(dataArray, offset, limit);
        this.sampleReader.appendToNalUnit(dataArray, offset, limit);
    }

    @RequiresNonNull({"output", "sampleReader"})
    private void endNalUnit(long position, int offset, int discardPadding, long pesTimeUs) {
        if (!this.hasOutputFormat || this.sampleReader.needsSpsPps()) {
            this.sps.endNalUnit(discardPadding);
            this.pps.endNalUnit(discardPadding);
            if (!this.hasOutputFormat) {
                if (this.sps.isCompleted() && this.pps.isCompleted()) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(Arrays.copyOf(this.sps.nalData, this.sps.nalLength));
                    arrayList.add(Arrays.copyOf(this.pps.nalData, this.pps.nalLength));
                    NalUnitUtil.SpsData spsNalUnit = NalUnitUtil.parseSpsNalUnit(this.sps.nalData, 3, this.sps.nalLength);
                    NalUnitUtil.PpsData ppsNalUnit = NalUnitUtil.parsePpsNalUnit(this.pps.nalData, 3, this.pps.nalLength);
                    this.output.format(new Format.Builder().setId(this.formatId).setSampleMimeType(MimeTypes.VIDEO_H264).setCodecs(CodecSpecificDataUtil.buildAvcCodecString(spsNalUnit.profileIdc, spsNalUnit.constraintsFlagsAndReservedZero2Bits, spsNalUnit.levelIdc)).setWidth(spsNalUnit.width).setHeight(spsNalUnit.height).setPixelWidthHeightRatio(spsNalUnit.pixelWidthAspectRatio).setInitializationData(arrayList).build());
                    this.hasOutputFormat = true;
                    this.sampleReader.putSps(spsNalUnit);
                    this.sampleReader.putPps(ppsNalUnit);
                    this.sps.reset();
                    this.pps.reset();
                }
            } else if (this.sps.isCompleted()) {
                this.sampleReader.putSps(NalUnitUtil.parseSpsNalUnit(this.sps.nalData, 3, this.sps.nalLength));
                this.sps.reset();
            } else if (this.pps.isCompleted()) {
                this.sampleReader.putPps(NalUnitUtil.parsePpsNalUnit(this.pps.nalData, 3, this.pps.nalLength));
                this.pps.reset();
            }
        }
        if (this.sei.endNalUnit(discardPadding)) {
            this.seiWrapper.reset(this.sei.nalData, NalUnitUtil.unescapeStream(this.sei.nalData, this.sei.nalLength));
            this.seiWrapper.setPosition(4);
            this.seiReader.consume(pesTimeUs, this.seiWrapper);
        }
        if (this.sampleReader.endNalUnit(position, offset, this.hasOutputFormat, this.randomAccessIndicator)) {
            this.randomAccessIndicator = false;
        }
    }

    @EnsuresNonNull({"output", "sampleReader"})
    private void assertTracksCreated() {
        Assertions.checkStateNotNull(this.output);
        Util.castNonNull(this.sampleReader);
    }

    private static final class SampleReader {
        private static final int DEFAULT_BUFFER_SIZE = 128;
        private static final int NAL_UNIT_TYPE_AUD = 9;
        private static final int NAL_UNIT_TYPE_IDR = 5;
        private static final int NAL_UNIT_TYPE_NON_IDR = 1;
        private static final int NAL_UNIT_TYPE_PARTITION_A = 2;
        private final boolean allowNonIdrKeyframes;
        private int bufferLength;
        private final boolean detectAccessUnits;
        private boolean isFilling;
        private long nalUnitStartPosition;
        private long nalUnitTimeUs;
        private int nalUnitType;
        private final TrackOutput output;
        private SliceHeaderData previousSliceHeader;
        private boolean readingSample;
        private boolean sampleIsKeyframe;
        private long samplePosition;
        private long sampleTimeUs;
        private SliceHeaderData sliceHeader;
        private final SparseArray<NalUnitUtil.SpsData> sps = new SparseArray<>();
        private final SparseArray<NalUnitUtil.PpsData> pps = new SparseArray<>();
        private byte[] buffer = new byte[128];
        private final ParsableNalUnitBitArray bitArray = new ParsableNalUnitBitArray(this.buffer, 0, 0);

        public SampleReader(TrackOutput output, boolean allowNonIdrKeyframes, boolean detectAccessUnits) {
            this.output = output;
            this.allowNonIdrKeyframes = allowNonIdrKeyframes;
            this.detectAccessUnits = detectAccessUnits;
            this.previousSliceHeader = new SliceHeaderData();
            this.sliceHeader = new SliceHeaderData();
            reset();
        }

        public boolean needsSpsPps() {
            return this.detectAccessUnits;
        }

        public void putSps(NalUnitUtil.SpsData spsData) {
            this.sps.append(spsData.seqParameterSetId, spsData);
        }

        public void putPps(NalUnitUtil.PpsData ppsData) {
            this.pps.append(ppsData.picParameterSetId, ppsData);
        }

        public void reset() {
            this.isFilling = false;
            this.readingSample = false;
            this.sliceHeader.clear();
        }

        public void startNalUnit(long position, int type, long pesTimeUs) {
            this.nalUnitType = type;
            this.nalUnitTimeUs = pesTimeUs;
            this.nalUnitStartPosition = position;
            if (!this.allowNonIdrKeyframes || type != 1) {
                if (!this.detectAccessUnits) {
                    return;
                }
                if (type != 5 && type != 1 && type != 2) {
                    return;
                }
            }
            SliceHeaderData sliceHeaderData = this.previousSliceHeader;
            this.previousSliceHeader = this.sliceHeader;
            this.sliceHeader = sliceHeaderData;
            sliceHeaderData.clear();
            this.bufferLength = 0;
            this.isFilling = true;
        }

        /* JADX WARN: Removed duplicated region for block: B:53:0x00ff  */
        /* JADX WARN: Removed duplicated region for block: B:54:0x0102  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x0106  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x0118  */
        /* JADX WARN: Removed duplicated region for block: B:63:0x011e  */
        /* JADX WARN: Removed duplicated region for block: B:74:0x0152  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void appendToNalUnit(byte[] r24, int r25, int r26) {
            /*
                Method dump skipped, instructions count: 416
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.H264Reader.SampleReader.appendToNalUnit(byte[], int, int):void");
        }

        public boolean endNalUnit(long position, int offset, boolean hasOutputFormat, boolean randomAccessIndicator) {
            boolean z = false;
            if (this.nalUnitType == 9 || (this.detectAccessUnits && this.sliceHeader.isFirstVclNalUnitOfPicture(this.previousSliceHeader))) {
                if (hasOutputFormat && this.readingSample) {
                    outputSample(offset + ((int) (position - this.nalUnitStartPosition)));
                }
                this.samplePosition = this.nalUnitStartPosition;
                this.sampleTimeUs = this.nalUnitTimeUs;
                this.sampleIsKeyframe = false;
                this.readingSample = true;
            }
            if (this.allowNonIdrKeyframes) {
                randomAccessIndicator = this.sliceHeader.isISlice();
            }
            boolean z2 = this.sampleIsKeyframe;
            int i = this.nalUnitType;
            if (i == 5 || (randomAccessIndicator && i == 1)) {
                z = true;
            }
            boolean z3 = z2 | z;
            this.sampleIsKeyframe = z3;
            return z3;
        }

        private void outputSample(int i) {
            boolean z = this.sampleIsKeyframe;
            this.output.sampleMetadata(this.sampleTimeUs, z ? 1 : 0, (int) (this.nalUnitStartPosition - this.samplePosition), i, null);
        }

        private static final class SliceHeaderData {
            private static final int SLICE_TYPE_ALL_I = 7;
            private static final int SLICE_TYPE_I = 2;
            private boolean bottomFieldFlag;
            private boolean bottomFieldFlagPresent;
            private int deltaPicOrderCnt0;
            private int deltaPicOrderCnt1;
            private int deltaPicOrderCntBottom;
            private boolean fieldPicFlag;
            private int frameNum;
            private boolean hasSliceType;
            private boolean idrPicFlag;
            private int idrPicId;
            private boolean isComplete;
            private int nalRefIdc;
            private int picOrderCntLsb;
            private int picParameterSetId;
            private int sliceType;
            private NalUnitUtil.SpsData spsData;

            private SliceHeaderData() {
            }

            public void clear() {
                this.hasSliceType = false;
                this.isComplete = false;
            }

            public void setSliceType(int sliceType) {
                this.sliceType = sliceType;
                this.hasSliceType = true;
            }

            public void setAll(NalUnitUtil.SpsData spsData, int nalRefIdc, int sliceType, int frameNum, int picParameterSetId, boolean fieldPicFlag, boolean bottomFieldFlagPresent, boolean bottomFieldFlag, boolean idrPicFlag, int idrPicId, int picOrderCntLsb, int deltaPicOrderCntBottom, int deltaPicOrderCnt0, int deltaPicOrderCnt1) {
                this.spsData = spsData;
                this.nalRefIdc = nalRefIdc;
                this.sliceType = sliceType;
                this.frameNum = frameNum;
                this.picParameterSetId = picParameterSetId;
                this.fieldPicFlag = fieldPicFlag;
                this.bottomFieldFlagPresent = bottomFieldFlagPresent;
                this.bottomFieldFlag = bottomFieldFlag;
                this.idrPicFlag = idrPicFlag;
                this.idrPicId = idrPicId;
                this.picOrderCntLsb = picOrderCntLsb;
                this.deltaPicOrderCntBottom = deltaPicOrderCntBottom;
                this.deltaPicOrderCnt0 = deltaPicOrderCnt0;
                this.deltaPicOrderCnt1 = deltaPicOrderCnt1;
                this.isComplete = true;
                this.hasSliceType = true;
            }

            public boolean isISlice() {
                int i;
                return this.hasSliceType && ((i = this.sliceType) == 7 || i == 2);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public boolean isFirstVclNalUnitOfPicture(SliceHeaderData other) {
                int i;
                int i2;
                boolean z;
                if (!this.isComplete) {
                    return false;
                }
                if (!other.isComplete) {
                    return true;
                }
                NalUnitUtil.SpsData spsData = (NalUnitUtil.SpsData) Assertions.checkStateNotNull(this.spsData);
                NalUnitUtil.SpsData spsData2 = (NalUnitUtil.SpsData) Assertions.checkStateNotNull(other.spsData);
                return (this.frameNum == other.frameNum && this.picParameterSetId == other.picParameterSetId && this.fieldPicFlag == other.fieldPicFlag && (!this.bottomFieldFlagPresent || !other.bottomFieldFlagPresent || this.bottomFieldFlag == other.bottomFieldFlag) && (((i = this.nalRefIdc) == (i2 = other.nalRefIdc) || (i != 0 && i2 != 0)) && ((spsData.picOrderCountType != 0 || spsData2.picOrderCountType != 0 || (this.picOrderCntLsb == other.picOrderCntLsb && this.deltaPicOrderCntBottom == other.deltaPicOrderCntBottom)) && ((spsData.picOrderCountType != 1 || spsData2.picOrderCountType != 1 || (this.deltaPicOrderCnt0 == other.deltaPicOrderCnt0 && this.deltaPicOrderCnt1 == other.deltaPicOrderCnt1)) && (z = this.idrPicFlag) == other.idrPicFlag && (!z || this.idrPicId == other.idrPicId))))) ? false : true;
            }
        }
    }
}
