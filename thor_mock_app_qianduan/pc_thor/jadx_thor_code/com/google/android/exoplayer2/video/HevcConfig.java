package com.google.android.exoplayer2.video;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class HevcConfig {
    private static final int SPS_NAL_UNIT_TYPE = 33;
    public final String codecs;
    public final List<byte[]> initializationData;
    public final int nalUnitLengthFieldLength;

    public static HevcConfig parse(ParsableByteArray data) throws ParserException {
        try {
            data.skipBytes(21);
            int unsignedByte = data.readUnsignedByte() & 3;
            int unsignedByte2 = data.readUnsignedByte();
            int position = data.getPosition();
            int i = 0;
            for (int i2 = 0; i2 < unsignedByte2; i2++) {
                data.skipBytes(1);
                int unsignedShort = data.readUnsignedShort();
                for (int i3 = 0; i3 < unsignedShort; i3++) {
                    int unsignedShort2 = data.readUnsignedShort();
                    i += unsignedShort2 + 4;
                    data.skipBytes(unsignedShort2);
                }
            }
            data.setPosition(position);
            byte[] bArr = new byte[i];
            int i4 = 0;
            String strBuildHevcCodecStringFromSps = null;
            for (int i5 = 0; i5 < unsignedByte2; i5++) {
                int unsignedByte3 = data.readUnsignedByte() & 127;
                int unsignedShort3 = data.readUnsignedShort();
                for (int i6 = 0; i6 < unsignedShort3; i6++) {
                    int unsignedShort4 = data.readUnsignedShort();
                    System.arraycopy(NalUnitUtil.NAL_START_CODE, 0, bArr, i4, NalUnitUtil.NAL_START_CODE.length);
                    int length = i4 + NalUnitUtil.NAL_START_CODE.length;
                    System.arraycopy(data.getData(), data.getPosition(), bArr, length, unsignedShort4);
                    if (unsignedByte3 == 33 && i6 == 0) {
                        strBuildHevcCodecStringFromSps = CodecSpecificDataUtil.buildHevcCodecStringFromSps(new ParsableNalUnitBitArray(bArr, length, length + unsignedShort4));
                    }
                    i4 = length + unsignedShort4;
                    data.skipBytes(unsignedShort4);
                }
            }
            return new HevcConfig(i == 0 ? null : Collections.singletonList(bArr), unsignedByte + 1, strBuildHevcCodecStringFromSps);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw ParserException.createForMalformedContainer("Error parsing HEVC config", e);
        }
    }

    private HevcConfig(List<byte[]> initializationData, int nalUnitLengthFieldLength, String codecs) {
        this.initializationData = initializationData;
        this.nalUnitLengthFieldLength = nalUnitLengthFieldLength;
        this.codecs = codecs;
    }
}
