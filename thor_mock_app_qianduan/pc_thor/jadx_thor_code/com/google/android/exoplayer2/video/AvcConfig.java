package com.google.android.exoplayer2.video;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class AvcConfig {
    public final String codecs;
    public final int height;
    public final List<byte[]> initializationData;
    public final int nalUnitLengthFieldLength;
    public final float pixelWidthAspectRatio;
    public final int width;

    public static AvcConfig parse(ParsableByteArray data) throws ParserException {
        int i;
        int i2;
        float f;
        String strBuildAvcCodecString;
        try {
            data.skipBytes(4);
            int unsignedByte = (data.readUnsignedByte() & 3) + 1;
            if (unsignedByte == 3) {
                throw new IllegalStateException();
            }
            ArrayList arrayList = new ArrayList();
            int unsignedByte2 = data.readUnsignedByte() & 31;
            for (int i3 = 0; i3 < unsignedByte2; i3++) {
                arrayList.add(buildNalUnitForChild(data));
            }
            int unsignedByte3 = data.readUnsignedByte();
            for (int i4 = 0; i4 < unsignedByte3; i4++) {
                arrayList.add(buildNalUnitForChild(data));
            }
            if (unsignedByte2 > 0) {
                NalUnitUtil.SpsData spsNalUnit = NalUnitUtil.parseSpsNalUnit((byte[]) arrayList.get(0), unsignedByte, ((byte[]) arrayList.get(0)).length);
                int i5 = spsNalUnit.width;
                int i6 = spsNalUnit.height;
                float f2 = spsNalUnit.pixelWidthAspectRatio;
                strBuildAvcCodecString = CodecSpecificDataUtil.buildAvcCodecString(spsNalUnit.profileIdc, spsNalUnit.constraintsFlagsAndReservedZero2Bits, spsNalUnit.levelIdc);
                i = i5;
                i2 = i6;
                f = f2;
            } else {
                i = -1;
                i2 = -1;
                f = 1.0f;
                strBuildAvcCodecString = null;
            }
            return new AvcConfig(arrayList, unsignedByte, i, i2, f, strBuildAvcCodecString);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw ParserException.createForMalformedContainer("Error parsing AVC config", e);
        }
    }

    private AvcConfig(List<byte[]> initializationData, int nalUnitLengthFieldLength, int width, int height, float pixelWidthAspectRatio, String codecs) {
        this.initializationData = initializationData;
        this.nalUnitLengthFieldLength = nalUnitLengthFieldLength;
        this.width = width;
        this.height = height;
        this.pixelWidthAspectRatio = pixelWidthAspectRatio;
        this.codecs = codecs;
    }

    private static byte[] buildNalUnitForChild(ParsableByteArray data) {
        int unsignedShort = data.readUnsignedShort();
        int position = data.getPosition();
        data.skipBytes(unsignedShort);
        return CodecSpecificDataUtil.buildNalUnit(data.getData(), position, unsignedShort);
    }
}
