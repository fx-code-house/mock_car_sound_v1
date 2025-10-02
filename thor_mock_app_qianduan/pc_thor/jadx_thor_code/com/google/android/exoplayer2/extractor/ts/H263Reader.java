package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Collections;

/* loaded from: classes.dex */
public final class H263Reader implements ElementaryStreamReader {
    private static final float[] PIXEL_WIDTH_HEIGHT_RATIO_BY_ASPECT_RATIO_INFO = {1.0f, 1.0f, 1.0909091f, 0.90909094f, 1.4545455f, 1.2121212f, 1.0f};
    private static final int START_CODE_VALUE_GROUP_OF_VOP = 179;
    private static final int START_CODE_VALUE_MAX_VIDEO_OBJECT = 31;
    private static final int START_CODE_VALUE_UNSET = -1;
    private static final int START_CODE_VALUE_USER_DATA = 178;
    private static final int START_CODE_VALUE_VISUAL_OBJECT = 181;
    private static final int START_CODE_VALUE_VISUAL_OBJECT_SEQUENCE = 176;
    private static final int START_CODE_VALUE_VOP = 182;
    private static final String TAG = "H263Reader";
    private static final int VIDEO_OBJECT_LAYER_SHAPE_RECTANGULAR = 0;
    private final CsdBuffer csdBuffer;
    private String formatId;
    private boolean hasOutputFormat;
    private TrackOutput output;
    private long pesTimeUs;
    private final boolean[] prefixFlags;
    private SampleReader sampleReader;
    private long totalBytesWritten;
    private final NalUnitTargetBuffer userData;
    private final ParsableByteArray userDataParsable;
    private final UserDataReader userDataReader;

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public H263Reader() {
        this(null);
    }

    H263Reader(UserDataReader userDataReader) {
        this.userDataReader = userDataReader;
        this.prefixFlags = new boolean[4];
        this.csdBuffer = new CsdBuffer(128);
        if (userDataReader != null) {
            this.userData = new NalUnitTargetBuffer(START_CODE_VALUE_USER_DATA, 128);
            this.userDataParsable = new ParsableByteArray();
        } else {
            this.userData = null;
            this.userDataParsable = null;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        NalUnitUtil.clearPrefixFlags(this.prefixFlags);
        this.csdBuffer.reset();
        SampleReader sampleReader = this.sampleReader;
        if (sampleReader != null) {
            sampleReader.reset();
        }
        NalUnitTargetBuffer nalUnitTargetBuffer = this.userData;
        if (nalUnitTargetBuffer != null) {
            nalUnitTargetBuffer.reset();
        }
        this.totalBytesWritten = 0L;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        idGenerator.generateNewId();
        this.formatId = idGenerator.getFormatId();
        this.output = extractorOutput.track(idGenerator.getTrackId(), 2);
        this.sampleReader = new SampleReader(this.output);
        UserDataReader userDataReader = this.userDataReader;
        if (userDataReader != null) {
            userDataReader.createTracks(extractorOutput, idGenerator);
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long pesTimeUs, int flags) {
        this.pesTimeUs = pesTimeUs;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray data) {
        Assertions.checkStateNotNull(this.sampleReader);
        Assertions.checkStateNotNull(this.output);
        int position = data.getPosition();
        int iLimit = data.limit();
        byte[] data2 = data.getData();
        this.totalBytesWritten += data.bytesLeft();
        this.output.sampleData(data, data.bytesLeft());
        while (true) {
            int iFindNalUnit = NalUnitUtil.findNalUnit(data2, position, iLimit, this.prefixFlags);
            if (iFindNalUnit == iLimit) {
                break;
            }
            int i = iFindNalUnit + 3;
            int i2 = data.getData()[i] & 255;
            int i3 = iFindNalUnit - position;
            int i4 = 0;
            if (!this.hasOutputFormat) {
                if (i3 > 0) {
                    this.csdBuffer.onData(data2, position, iFindNalUnit);
                }
                if (this.csdBuffer.onStartCode(i2, i3 < 0 ? -i3 : 0)) {
                    TrackOutput trackOutput = this.output;
                    CsdBuffer csdBuffer = this.csdBuffer;
                    trackOutput.format(parseCsdBuffer(csdBuffer, csdBuffer.volStartPosition, (String) Assertions.checkNotNull(this.formatId)));
                    this.hasOutputFormat = true;
                }
            }
            this.sampleReader.onData(data2, position, iFindNalUnit);
            NalUnitTargetBuffer nalUnitTargetBuffer = this.userData;
            if (nalUnitTargetBuffer != null) {
                if (i3 > 0) {
                    nalUnitTargetBuffer.appendToNalUnit(data2, position, iFindNalUnit);
                } else {
                    i4 = -i3;
                }
                if (this.userData.endNalUnit(i4)) {
                    ((ParsableByteArray) Util.castNonNull(this.userDataParsable)).reset(this.userData.nalData, NalUnitUtil.unescapeStream(this.userData.nalData, this.userData.nalLength));
                    ((UserDataReader) Util.castNonNull(this.userDataReader)).consume(this.pesTimeUs, this.userDataParsable);
                }
                if (i2 == START_CODE_VALUE_USER_DATA && data.getData()[iFindNalUnit + 2] == 1) {
                    this.userData.startNalUnit(i2);
                }
            }
            int i5 = iLimit - iFindNalUnit;
            this.sampleReader.onDataEnd(this.totalBytesWritten - i5, i5, this.hasOutputFormat);
            this.sampleReader.onStartCode(i2, this.pesTimeUs);
            position = i;
        }
        if (!this.hasOutputFormat) {
            this.csdBuffer.onData(data2, position, iLimit);
        }
        this.sampleReader.onData(data2, position, iLimit);
        NalUnitTargetBuffer nalUnitTargetBuffer2 = this.userData;
        if (nalUnitTargetBuffer2 != null) {
            nalUnitTargetBuffer2.appendToNalUnit(data2, position, iLimit);
        }
    }

    private static Format parseCsdBuffer(CsdBuffer csdBuffer, int volStartPosition, String formatId) {
        byte[] bArrCopyOf = Arrays.copyOf(csdBuffer.data, csdBuffer.length);
        ParsableBitArray parsableBitArray = new ParsableBitArray(bArrCopyOf);
        parsableBitArray.skipBytes(volStartPosition);
        parsableBitArray.skipBytes(4);
        parsableBitArray.skipBit();
        parsableBitArray.skipBits(8);
        if (parsableBitArray.readBit()) {
            parsableBitArray.skipBits(4);
            parsableBitArray.skipBits(3);
        }
        int bits = parsableBitArray.readBits(4);
        float f = 1.0f;
        if (bits == 15) {
            int bits2 = parsableBitArray.readBits(8);
            int bits3 = parsableBitArray.readBits(8);
            if (bits3 == 0) {
                Log.w(TAG, "Invalid aspect ratio");
            } else {
                f = bits2 / bits3;
            }
        } else {
            float[] fArr = PIXEL_WIDTH_HEIGHT_RATIO_BY_ASPECT_RATIO_INFO;
            if (bits < fArr.length) {
                f = fArr[bits];
            } else {
                Log.w(TAG, "Invalid aspect ratio");
            }
        }
        if (parsableBitArray.readBit()) {
            parsableBitArray.skipBits(2);
            parsableBitArray.skipBits(1);
            if (parsableBitArray.readBit()) {
                parsableBitArray.skipBits(15);
                parsableBitArray.skipBit();
                parsableBitArray.skipBits(15);
                parsableBitArray.skipBit();
                parsableBitArray.skipBits(15);
                parsableBitArray.skipBit();
                parsableBitArray.skipBits(3);
                parsableBitArray.skipBits(11);
                parsableBitArray.skipBit();
                parsableBitArray.skipBits(15);
                parsableBitArray.skipBit();
            }
        }
        if (parsableBitArray.readBits(2) != 0) {
            Log.w(TAG, "Unhandled video object layer shape");
        }
        parsableBitArray.skipBit();
        int bits4 = parsableBitArray.readBits(16);
        parsableBitArray.skipBit();
        if (parsableBitArray.readBit()) {
            if (bits4 == 0) {
                Log.w(TAG, "Invalid vop_increment_time_resolution");
            } else {
                int i = 0;
                for (int i2 = bits4 - 1; i2 > 0; i2 >>= 1) {
                    i++;
                }
                parsableBitArray.skipBits(i);
            }
        }
        parsableBitArray.skipBit();
        int bits5 = parsableBitArray.readBits(13);
        parsableBitArray.skipBit();
        int bits6 = parsableBitArray.readBits(13);
        parsableBitArray.skipBit();
        parsableBitArray.skipBit();
        return new Format.Builder().setId(formatId).setSampleMimeType(MimeTypes.VIDEO_MP4V).setWidth(bits5).setHeight(bits6).setPixelWidthHeightRatio(f).setInitializationData(Collections.singletonList(bArrCopyOf)).build();
    }

    private static final class CsdBuffer {
        private static final byte[] START_CODE = {0, 0, 1};
        private static final int STATE_EXPECT_VIDEO_OBJECT_LAYER_START = 3;
        private static final int STATE_EXPECT_VIDEO_OBJECT_START = 2;
        private static final int STATE_EXPECT_VISUAL_OBJECT_START = 1;
        private static final int STATE_SKIP_TO_VISUAL_OBJECT_SEQUENCE_START = 0;
        private static final int STATE_WAIT_FOR_VOP_START = 4;
        public byte[] data;
        private boolean isFilling;
        public int length;
        private int state;
        public int volStartPosition;

        public CsdBuffer(int initialCapacity) {
            this.data = new byte[initialCapacity];
        }

        public void reset() {
            this.isFilling = false;
            this.length = 0;
            this.state = 0;
        }

        public boolean onStartCode(int startCodeValue, int bytesAlreadyPassed) {
            int i = this.state;
            if (i != 0) {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            if (i != 4) {
                                throw new IllegalStateException();
                            }
                            if (startCodeValue == H263Reader.START_CODE_VALUE_GROUP_OF_VOP || startCodeValue == H263Reader.START_CODE_VALUE_VISUAL_OBJECT) {
                                this.length -= bytesAlreadyPassed;
                                this.isFilling = false;
                                return true;
                            }
                        } else if ((startCodeValue & PsExtractor.VIDEO_STREAM_MASK) != 32) {
                            Log.w(H263Reader.TAG, "Unexpected start code value");
                            reset();
                        } else {
                            this.volStartPosition = this.length;
                            this.state = 4;
                        }
                    } else if (startCodeValue > 31) {
                        Log.w(H263Reader.TAG, "Unexpected start code value");
                        reset();
                    } else {
                        this.state = 3;
                    }
                } else if (startCodeValue != H263Reader.START_CODE_VALUE_VISUAL_OBJECT) {
                    Log.w(H263Reader.TAG, "Unexpected start code value");
                    reset();
                } else {
                    this.state = 2;
                }
            } else if (startCodeValue == H263Reader.START_CODE_VALUE_VISUAL_OBJECT_SEQUENCE) {
                this.state = 1;
                this.isFilling = true;
            }
            byte[] bArr = START_CODE;
            onData(bArr, 0, bArr.length);
            return false;
        }

        public void onData(byte[] newData, int offset, int limit) {
            if (this.isFilling) {
                int i = limit - offset;
                byte[] bArr = this.data;
                int length = bArr.length;
                int i2 = this.length;
                if (length < i2 + i) {
                    this.data = Arrays.copyOf(bArr, (i2 + i) * 2);
                }
                System.arraycopy(newData, offset, this.data, this.length, i);
                this.length += i;
            }
        }
    }

    private static final class SampleReader {
        private static final int OFFSET_VOP_CODING_TYPE = 1;
        private static final int VOP_CODING_TYPE_INTRA = 0;
        private boolean lookingForVopCodingType;
        private final TrackOutput output;
        private boolean readingSample;
        private boolean sampleIsKeyframe;
        private long samplePosition;
        private long sampleTimeUs;
        private int startCodeValue;
        private int vopBytesRead;

        public SampleReader(TrackOutput output) {
            this.output = output;
        }

        public void reset() {
            this.readingSample = false;
            this.lookingForVopCodingType = false;
            this.sampleIsKeyframe = false;
            this.startCodeValue = -1;
        }

        public void onStartCode(int startCodeValue, long pesTimeUs) {
            this.startCodeValue = startCodeValue;
            this.sampleIsKeyframe = false;
            this.readingSample = startCodeValue == H263Reader.START_CODE_VALUE_VOP || startCodeValue == H263Reader.START_CODE_VALUE_GROUP_OF_VOP;
            this.lookingForVopCodingType = startCodeValue == H263Reader.START_CODE_VALUE_VOP;
            this.vopBytesRead = 0;
            this.sampleTimeUs = pesTimeUs;
        }

        public void onData(byte[] data, int offset, int limit) {
            if (this.lookingForVopCodingType) {
                int i = this.vopBytesRead;
                int i2 = (offset + 1) - i;
                if (i2 < limit) {
                    this.sampleIsKeyframe = ((data[i2] & 192) >> 6) == 0;
                    this.lookingForVopCodingType = false;
                } else {
                    this.vopBytesRead = i + (limit - offset);
                }
            }
        }

        public void onDataEnd(long j, int i, boolean z) {
            if (this.startCodeValue == H263Reader.START_CODE_VALUE_VOP && z && this.readingSample) {
                this.output.sampleMetadata(this.sampleTimeUs, this.sampleIsKeyframe ? 1 : 0, (int) (j - this.samplePosition), i, null);
            }
            if (this.startCodeValue != H263Reader.START_CODE_VALUE_GROUP_OF_VOP) {
                this.samplePosition = j;
            }
        }
    }
}
