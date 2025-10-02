package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public final class H265Reader implements ElementaryStreamReader {
    private static final int AUD_NUT = 35;
    private static final int BLA_W_LP = 16;
    private static final int CRA_NUT = 21;
    private static final int PPS_NUT = 34;
    private static final int PREFIX_SEI_NUT = 39;
    private static final int RASL_R = 9;
    private static final int SPS_NUT = 33;
    private static final int SUFFIX_SEI_NUT = 40;
    private static final String TAG = "H265Reader";
    private static final int VPS_NUT = 32;
    private String formatId;
    private boolean hasOutputFormat;
    private TrackOutput output;
    private long pesTimeUs;
    private SampleReader sampleReader;
    private final SeiReader seiReader;
    private long totalBytesWritten;
    private final boolean[] prefixFlags = new boolean[3];
    private final NalUnitTargetBuffer vps = new NalUnitTargetBuffer(32, 128);
    private final NalUnitTargetBuffer sps = new NalUnitTargetBuffer(33, 128);
    private final NalUnitTargetBuffer pps = new NalUnitTargetBuffer(34, 128);
    private final NalUnitTargetBuffer prefixSei = new NalUnitTargetBuffer(39, 128);
    private final NalUnitTargetBuffer suffixSei = new NalUnitTargetBuffer(40, 128);
    private final ParsableByteArray seiWrapper = new ParsableByteArray();

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public H265Reader(SeiReader seiReader) {
        this.seiReader = seiReader;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.totalBytesWritten = 0L;
        NalUnitUtil.clearPrefixFlags(this.prefixFlags);
        this.vps.reset();
        this.sps.reset();
        this.pps.reset();
        this.prefixSei.reset();
        this.suffixSei.reset();
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
        this.sampleReader = new SampleReader(this.output);
        this.seiReader.createTracks(extractorOutput, idGenerator);
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long pesTimeUs, int flags) {
        this.pesTimeUs = pesTimeUs;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray data) {
        assertTracksCreated();
        while (data.bytesLeft() > 0) {
            int position = data.getPosition();
            int iLimit = data.limit();
            byte[] data2 = data.getData();
            this.totalBytesWritten += data.bytesLeft();
            this.output.sampleData(data, data.bytesLeft());
            while (position < iLimit) {
                int iFindNalUnit = NalUnitUtil.findNalUnit(data2, position, iLimit, this.prefixFlags);
                if (iFindNalUnit == iLimit) {
                    nalUnitData(data2, position, iLimit);
                    return;
                }
                int h265NalUnitType = NalUnitUtil.getH265NalUnitType(data2, iFindNalUnit);
                int i = iFindNalUnit - position;
                if (i > 0) {
                    nalUnitData(data2, position, iFindNalUnit);
                }
                int i2 = iLimit - iFindNalUnit;
                long j = this.totalBytesWritten - i2;
                endNalUnit(j, i2, i < 0 ? -i : 0, this.pesTimeUs);
                startNalUnit(j, i2, h265NalUnitType, this.pesTimeUs);
                position = iFindNalUnit + 3;
            }
        }
    }

    @RequiresNonNull({"sampleReader"})
    private void startNalUnit(long position, int offset, int nalUnitType, long pesTimeUs) {
        this.sampleReader.startNalUnit(position, offset, nalUnitType, pesTimeUs, this.hasOutputFormat);
        if (!this.hasOutputFormat) {
            this.vps.startNalUnit(nalUnitType);
            this.sps.startNalUnit(nalUnitType);
            this.pps.startNalUnit(nalUnitType);
        }
        this.prefixSei.startNalUnit(nalUnitType);
        this.suffixSei.startNalUnit(nalUnitType);
    }

    @RequiresNonNull({"sampleReader"})
    private void nalUnitData(byte[] dataArray, int offset, int limit) {
        this.sampleReader.readNalUnitData(dataArray, offset, limit);
        if (!this.hasOutputFormat) {
            this.vps.appendToNalUnit(dataArray, offset, limit);
            this.sps.appendToNalUnit(dataArray, offset, limit);
            this.pps.appendToNalUnit(dataArray, offset, limit);
        }
        this.prefixSei.appendToNalUnit(dataArray, offset, limit);
        this.suffixSei.appendToNalUnit(dataArray, offset, limit);
    }

    @RequiresNonNull({"output", "sampleReader"})
    private void endNalUnit(long position, int offset, int discardPadding, long pesTimeUs) {
        this.sampleReader.endNalUnit(position, offset, this.hasOutputFormat);
        if (!this.hasOutputFormat) {
            this.vps.endNalUnit(discardPadding);
            this.sps.endNalUnit(discardPadding);
            this.pps.endNalUnit(discardPadding);
            if (this.vps.isCompleted() && this.sps.isCompleted() && this.pps.isCompleted()) {
                this.output.format(parseMediaFormat(this.formatId, this.vps, this.sps, this.pps));
                this.hasOutputFormat = true;
            }
        }
        if (this.prefixSei.endNalUnit(discardPadding)) {
            this.seiWrapper.reset(this.prefixSei.nalData, NalUnitUtil.unescapeStream(this.prefixSei.nalData, this.prefixSei.nalLength));
            this.seiWrapper.skipBytes(5);
            this.seiReader.consume(pesTimeUs, this.seiWrapper);
        }
        if (this.suffixSei.endNalUnit(discardPadding)) {
            this.seiWrapper.reset(this.suffixSei.nalData, NalUnitUtil.unescapeStream(this.suffixSei.nalData, this.suffixSei.nalLength));
            this.seiWrapper.skipBytes(5);
            this.seiReader.consume(pesTimeUs, this.seiWrapper);
        }
    }

    private static Format parseMediaFormat(String formatId, NalUnitTargetBuffer vps, NalUnitTargetBuffer sps, NalUnitTargetBuffer pps) {
        byte[] bArr = new byte[vps.nalLength + sps.nalLength + pps.nalLength];
        System.arraycopy(vps.nalData, 0, bArr, 0, vps.nalLength);
        System.arraycopy(sps.nalData, 0, bArr, vps.nalLength, sps.nalLength);
        System.arraycopy(pps.nalData, 0, bArr, vps.nalLength + sps.nalLength, pps.nalLength);
        ParsableNalUnitBitArray parsableNalUnitBitArray = new ParsableNalUnitBitArray(sps.nalData, 0, sps.nalLength);
        parsableNalUnitBitArray.skipBits(44);
        int bits = parsableNalUnitBitArray.readBits(3);
        parsableNalUnitBitArray.skipBit();
        parsableNalUnitBitArray.skipBits(88);
        parsableNalUnitBitArray.skipBits(8);
        int i = 0;
        for (int i2 = 0; i2 < bits; i2++) {
            if (parsableNalUnitBitArray.readBit()) {
                i += 89;
            }
            if (parsableNalUnitBitArray.readBit()) {
                i += 8;
            }
        }
        parsableNalUnitBitArray.skipBits(i);
        if (bits > 0) {
            parsableNalUnitBitArray.skipBits((8 - bits) * 2);
        }
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int unsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (unsignedExpGolombCodedInt == 3) {
            parsableNalUnitBitArray.skipBit();
        }
        int unsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int unsignedExpGolombCodedInt3 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (parsableNalUnitBitArray.readBit()) {
            int unsignedExpGolombCodedInt4 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt5 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt7 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            unsignedExpGolombCodedInt2 -= ((unsignedExpGolombCodedInt == 1 || unsignedExpGolombCodedInt == 2) ? 2 : 1) * (unsignedExpGolombCodedInt4 + unsignedExpGolombCodedInt5);
            unsignedExpGolombCodedInt3 -= (unsignedExpGolombCodedInt == 1 ? 2 : 1) * (unsignedExpGolombCodedInt6 + unsignedExpGolombCodedInt7);
        }
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int unsignedExpGolombCodedInt8 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        for (int i3 = parsableNalUnitBitArray.readBit() ? 0 : bits; i3 <= bits; i3++) {
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        }
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (parsableNalUnitBitArray.readBit() && parsableNalUnitBitArray.readBit()) {
            skipScalingList(parsableNalUnitBitArray);
        }
        parsableNalUnitBitArray.skipBits(2);
        if (parsableNalUnitBitArray.readBit()) {
            parsableNalUnitBitArray.skipBits(8);
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.skipBit();
        }
        skipShortTermRefPicSets(parsableNalUnitBitArray);
        if (parsableNalUnitBitArray.readBit()) {
            for (int i4 = 0; i4 < parsableNalUnitBitArray.readUnsignedExpGolombCodedInt(); i4++) {
                parsableNalUnitBitArray.skipBits(unsignedExpGolombCodedInt8 + 4 + 1);
            }
        }
        parsableNalUnitBitArray.skipBits(2);
        float f = 1.0f;
        if (parsableNalUnitBitArray.readBit()) {
            if (parsableNalUnitBitArray.readBit()) {
                int bits2 = parsableNalUnitBitArray.readBits(8);
                if (bits2 == 255) {
                    int bits3 = parsableNalUnitBitArray.readBits(16);
                    int bits4 = parsableNalUnitBitArray.readBits(16);
                    if (bits3 != 0 && bits4 != 0) {
                        f = bits3 / bits4;
                    }
                } else if (bits2 < NalUnitUtil.ASPECT_RATIO_IDC_VALUES.length) {
                    f = NalUnitUtil.ASPECT_RATIO_IDC_VALUES[bits2];
                } else {
                    Log.w(TAG, new StringBuilder(46).append("Unexpected aspect_ratio_idc value: ").append(bits2).toString());
                }
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.skipBit();
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.skipBits(4);
                if (parsableNalUnitBitArray.readBit()) {
                    parsableNalUnitBitArray.skipBits(24);
                }
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            }
            parsableNalUnitBitArray.skipBit();
            if (parsableNalUnitBitArray.readBit()) {
                unsignedExpGolombCodedInt3 *= 2;
            }
        }
        parsableNalUnitBitArray.reset(sps.nalData, 0, sps.nalLength);
        parsableNalUnitBitArray.skipBits(24);
        return new Format.Builder().setId(formatId).setSampleMimeType(MimeTypes.VIDEO_H265).setCodecs(CodecSpecificDataUtil.buildHevcCodecStringFromSps(parsableNalUnitBitArray)).setWidth(unsignedExpGolombCodedInt2).setHeight(unsignedExpGolombCodedInt3).setPixelWidthHeightRatio(f).setInitializationData(Collections.singletonList(bArr)).build();
    }

    private static void skipScalingList(ParsableNalUnitBitArray bitArray) {
        for (int i = 0; i < 4; i++) {
            int i2 = 0;
            while (i2 < 6) {
                int i3 = 1;
                if (!bitArray.readBit()) {
                    bitArray.readUnsignedExpGolombCodedInt();
                } else {
                    int iMin = Math.min(64, 1 << ((i << 1) + 4));
                    if (i > 1) {
                        bitArray.readSignedExpGolombCodedInt();
                    }
                    for (int i4 = 0; i4 < iMin; i4++) {
                        bitArray.readSignedExpGolombCodedInt();
                    }
                }
                if (i == 3) {
                    i3 = 3;
                }
                i2 += i3;
            }
        }
    }

    private static void skipShortTermRefPicSets(ParsableNalUnitBitArray bitArray) {
        int unsignedExpGolombCodedInt = bitArray.readUnsignedExpGolombCodedInt();
        boolean bit = false;
        int i = 0;
        for (int i2 = 0; i2 < unsignedExpGolombCodedInt; i2++) {
            if (i2 != 0) {
                bit = bitArray.readBit();
            }
            if (bit) {
                bitArray.skipBit();
                bitArray.readUnsignedExpGolombCodedInt();
                for (int i3 = 0; i3 <= i; i3++) {
                    if (bitArray.readBit()) {
                        bitArray.skipBit();
                    }
                }
            } else {
                int unsignedExpGolombCodedInt2 = bitArray.readUnsignedExpGolombCodedInt();
                int unsignedExpGolombCodedInt3 = bitArray.readUnsignedExpGolombCodedInt();
                int i4 = unsignedExpGolombCodedInt2 + unsignedExpGolombCodedInt3;
                for (int i5 = 0; i5 < unsignedExpGolombCodedInt2; i5++) {
                    bitArray.readUnsignedExpGolombCodedInt();
                    bitArray.skipBit();
                }
                for (int i6 = 0; i6 < unsignedExpGolombCodedInt3; i6++) {
                    bitArray.readUnsignedExpGolombCodedInt();
                    bitArray.skipBit();
                }
                i = i4;
            }
        }
    }

    @EnsuresNonNull({"output", "sampleReader"})
    private void assertTracksCreated() {
        Assertions.checkStateNotNull(this.output);
        Util.castNonNull(this.sampleReader);
    }

    private static final class SampleReader {
        private static final int FIRST_SLICE_FLAG_OFFSET = 2;
        private boolean isFirstPrefixNalUnit;
        private boolean isFirstSlice;
        private boolean lookingForFirstSliceFlag;
        private int nalUnitBytesRead;
        private boolean nalUnitHasKeyframeData;
        private long nalUnitPosition;
        private long nalUnitTimeUs;
        private final TrackOutput output;
        private boolean readingPrefix;
        private boolean readingSample;
        private boolean sampleIsKeyframe;
        private long samplePosition;
        private long sampleTimeUs;

        private static boolean isPrefixNalUnit(int nalUnitType) {
            return (32 <= nalUnitType && nalUnitType <= 35) || nalUnitType == 39;
        }

        private static boolean isVclBodyNalUnit(int nalUnitType) {
            return nalUnitType < 32 || nalUnitType == 40;
        }

        public SampleReader(TrackOutput output) {
            this.output = output;
        }

        public void reset() {
            this.lookingForFirstSliceFlag = false;
            this.isFirstSlice = false;
            this.isFirstPrefixNalUnit = false;
            this.readingSample = false;
            this.readingPrefix = false;
        }

        public void startNalUnit(long position, int offset, int nalUnitType, long pesTimeUs, boolean hasOutputFormat) {
            this.isFirstSlice = false;
            this.isFirstPrefixNalUnit = false;
            this.nalUnitTimeUs = pesTimeUs;
            this.nalUnitBytesRead = 0;
            this.nalUnitPosition = position;
            if (!isVclBodyNalUnit(nalUnitType)) {
                if (this.readingSample && !this.readingPrefix) {
                    if (hasOutputFormat) {
                        outputSample(offset);
                    }
                    this.readingSample = false;
                }
                if (isPrefixNalUnit(nalUnitType)) {
                    this.isFirstPrefixNalUnit = !this.readingPrefix;
                    this.readingPrefix = true;
                }
            }
            boolean z = nalUnitType >= 16 && nalUnitType <= 21;
            this.nalUnitHasKeyframeData = z;
            this.lookingForFirstSliceFlag = z || nalUnitType <= 9;
        }

        public void readNalUnitData(byte[] data, int offset, int limit) {
            if (this.lookingForFirstSliceFlag) {
                int i = this.nalUnitBytesRead;
                int i2 = (offset + 2) - i;
                if (i2 < limit) {
                    this.isFirstSlice = (data[i2] & 128) != 0;
                    this.lookingForFirstSliceFlag = false;
                } else {
                    this.nalUnitBytesRead = i + (limit - offset);
                }
            }
        }

        public void endNalUnit(long position, int offset, boolean hasOutputFormat) {
            if (this.readingPrefix && this.isFirstSlice) {
                this.sampleIsKeyframe = this.nalUnitHasKeyframeData;
                this.readingPrefix = false;
            } else if (this.isFirstPrefixNalUnit || this.isFirstSlice) {
                if (hasOutputFormat && this.readingSample) {
                    outputSample(offset + ((int) (position - this.nalUnitPosition)));
                }
                this.samplePosition = this.nalUnitPosition;
                this.sampleTimeUs = this.nalUnitTimeUs;
                this.sampleIsKeyframe = this.nalUnitHasKeyframeData;
                this.readingSample = true;
            }
        }

        private void outputSample(int i) {
            boolean z = this.sampleIsKeyframe;
            this.output.sampleMetadata(this.sampleTimeUs, z ? 1 : 0, (int) (this.nalUnitPosition - this.samplePosition), i, null);
        }
    }
}
