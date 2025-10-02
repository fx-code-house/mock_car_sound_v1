package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class VorbisUtil {
    private static final String TAG = "VorbisUtil";

    public static int iLog(int x) {
        int i = 0;
        while (x > 0) {
            i++;
            x >>>= 1;
        }
        return i;
    }

    public static final class CommentHeader {
        public final String[] comments;
        public final int length;
        public final String vendor;

        public CommentHeader(String vendor, String[] comments, int length) {
            this.vendor = vendor;
            this.comments = comments;
            this.length = length;
        }
    }

    public static final class VorbisIdHeader {
        public final int bitrateMaximum;
        public final int bitrateMinimum;
        public final int bitrateNominal;
        public final int blockSize0;
        public final int blockSize1;
        public final int channels;
        public final byte[] data;
        public final boolean framingFlag;
        public final int sampleRate;
        public final int version;

        public VorbisIdHeader(int version, int channels, int sampleRate, int bitrateMaximum, int bitrateNominal, int bitrateMinimum, int blockSize0, int blockSize1, boolean framingFlag, byte[] data) {
            this.version = version;
            this.channels = channels;
            this.sampleRate = sampleRate;
            this.bitrateMaximum = bitrateMaximum;
            this.bitrateNominal = bitrateNominal;
            this.bitrateMinimum = bitrateMinimum;
            this.blockSize0 = blockSize0;
            this.blockSize1 = blockSize1;
            this.framingFlag = framingFlag;
            this.data = data;
        }
    }

    public static final class Mode {
        public final boolean blockFlag;
        public final int mapping;
        public final int transformType;
        public final int windowType;

        public Mode(boolean blockFlag, int windowType, int transformType, int mapping) {
            this.blockFlag = blockFlag;
            this.windowType = windowType;
            this.transformType = transformType;
            this.mapping = mapping;
        }
    }

    public static VorbisIdHeader readVorbisIdentificationHeader(ParsableByteArray headerData) throws ParserException {
        verifyVorbisHeaderCapturePattern(1, headerData, false);
        int littleEndianUnsignedIntToInt = headerData.readLittleEndianUnsignedIntToInt();
        int unsignedByte = headerData.readUnsignedByte();
        int littleEndianUnsignedIntToInt2 = headerData.readLittleEndianUnsignedIntToInt();
        int littleEndianInt = headerData.readLittleEndianInt();
        if (littleEndianInt <= 0) {
            littleEndianInt = -1;
        }
        int littleEndianInt2 = headerData.readLittleEndianInt();
        if (littleEndianInt2 <= 0) {
            littleEndianInt2 = -1;
        }
        int littleEndianInt3 = headerData.readLittleEndianInt();
        if (littleEndianInt3 <= 0) {
            littleEndianInt3 = -1;
        }
        int unsignedByte2 = headerData.readUnsignedByte();
        return new VorbisIdHeader(littleEndianUnsignedIntToInt, unsignedByte, littleEndianUnsignedIntToInt2, littleEndianInt, littleEndianInt2, littleEndianInt3, (int) Math.pow(2.0d, unsignedByte2 & 15), (int) Math.pow(2.0d, (unsignedByte2 & PsExtractor.VIDEO_STREAM_MASK) >> 4), (headerData.readUnsignedByte() & 1) > 0, Arrays.copyOf(headerData.getData(), headerData.limit()));
    }

    public static CommentHeader readVorbisCommentHeader(ParsableByteArray headerData) throws ParserException {
        return readVorbisCommentHeader(headerData, true, true);
    }

    public static CommentHeader readVorbisCommentHeader(ParsableByteArray headerData, boolean hasMetadataHeader, boolean hasFramingBit) throws ParserException {
        if (hasMetadataHeader) {
            verifyVorbisHeaderCapturePattern(3, headerData, false);
        }
        String string = headerData.readString((int) headerData.readLittleEndianUnsignedInt());
        int length = 11 + string.length();
        long littleEndianUnsignedInt = headerData.readLittleEndianUnsignedInt();
        String[] strArr = new String[(int) littleEndianUnsignedInt];
        int length2 = length + 4;
        for (int i = 0; i < littleEndianUnsignedInt; i++) {
            String string2 = headerData.readString((int) headerData.readLittleEndianUnsignedInt());
            strArr[i] = string2;
            length2 = length2 + 4 + string2.length();
        }
        if (hasFramingBit && (headerData.readUnsignedByte() & 1) == 0) {
            throw ParserException.createForMalformedContainer("framing bit expected to be set", null);
        }
        return new CommentHeader(string, strArr, length2 + 1);
    }

    public static boolean verifyVorbisHeaderCapturePattern(int headerType, ParsableByteArray header, boolean quiet) throws ParserException {
        if (header.bytesLeft() < 7) {
            if (quiet) {
                return false;
            }
            throw ParserException.createForMalformedContainer(new StringBuilder(29).append("too short header: ").append(header.bytesLeft()).toString(), null);
        }
        if (header.readUnsignedByte() != headerType) {
            if (quiet) {
                return false;
            }
            String strValueOf = String.valueOf(Integer.toHexString(headerType));
            throw ParserException.createForMalformedContainer(strValueOf.length() != 0 ? "expected header type ".concat(strValueOf) : new String("expected header type "), null);
        }
        if (header.readUnsignedByte() == 118 && header.readUnsignedByte() == 111 && header.readUnsignedByte() == 114 && header.readUnsignedByte() == 98 && header.readUnsignedByte() == 105 && header.readUnsignedByte() == 115) {
            return true;
        }
        if (quiet) {
            return false;
        }
        throw ParserException.createForMalformedContainer("expected characters 'vorbis'", null);
    }

    public static Mode[] readVorbisModes(ParsableByteArray headerData, int channels) throws ParserException {
        verifyVorbisHeaderCapturePattern(5, headerData, false);
        int unsignedByte = headerData.readUnsignedByte() + 1;
        VorbisBitArray vorbisBitArray = new VorbisBitArray(headerData.getData());
        vorbisBitArray.skipBits(headerData.getPosition() * 8);
        for (int i = 0; i < unsignedByte; i++) {
            readBook(vorbisBitArray);
        }
        int bits = vorbisBitArray.readBits(6) + 1;
        for (int i2 = 0; i2 < bits; i2++) {
            if (vorbisBitArray.readBits(16) != 0) {
                throw ParserException.createForMalformedContainer("placeholder of time domain transforms not zeroed out", null);
            }
        }
        readFloors(vorbisBitArray);
        readResidues(vorbisBitArray);
        readMappings(channels, vorbisBitArray);
        Mode[] modes = readModes(vorbisBitArray);
        if (vorbisBitArray.readBit()) {
            return modes;
        }
        throw ParserException.createForMalformedContainer("framing bit after modes not set as expected", null);
    }

    private static Mode[] readModes(VorbisBitArray bitArray) {
        int bits = bitArray.readBits(6) + 1;
        Mode[] modeArr = new Mode[bits];
        for (int i = 0; i < bits; i++) {
            modeArr[i] = new Mode(bitArray.readBit(), bitArray.readBits(16), bitArray.readBits(16), bitArray.readBits(8));
        }
        return modeArr;
    }

    private static void readMappings(int channels, VorbisBitArray bitArray) throws ParserException {
        int bits = bitArray.readBits(6) + 1;
        for (int i = 0; i < bits; i++) {
            int bits2 = bitArray.readBits(16);
            if (bits2 != 0) {
                Log.e(TAG, new StringBuilder(52).append("mapping type other than 0 not supported: ").append(bits2).toString());
            } else {
                int bits3 = bitArray.readBit() ? bitArray.readBits(4) + 1 : 1;
                if (bitArray.readBit()) {
                    int bits4 = bitArray.readBits(8) + 1;
                    for (int i2 = 0; i2 < bits4; i2++) {
                        int i3 = channels - 1;
                        bitArray.skipBits(iLog(i3));
                        bitArray.skipBits(iLog(i3));
                    }
                }
                if (bitArray.readBits(2) != 0) {
                    throw ParserException.createForMalformedContainer("to reserved bits must be zero after mapping coupling steps", null);
                }
                if (bits3 > 1) {
                    for (int i4 = 0; i4 < channels; i4++) {
                        bitArray.skipBits(4);
                    }
                }
                for (int i5 = 0; i5 < bits3; i5++) {
                    bitArray.skipBits(8);
                    bitArray.skipBits(8);
                    bitArray.skipBits(8);
                }
            }
        }
    }

    private static void readResidues(VorbisBitArray bitArray) throws ParserException {
        int bits = bitArray.readBits(6) + 1;
        for (int i = 0; i < bits; i++) {
            if (bitArray.readBits(16) > 2) {
                throw ParserException.createForMalformedContainer("residueType greater than 2 is not decodable", null);
            }
            bitArray.skipBits(24);
            bitArray.skipBits(24);
            bitArray.skipBits(24);
            int bits2 = bitArray.readBits(6) + 1;
            bitArray.skipBits(8);
            int[] iArr = new int[bits2];
            for (int i2 = 0; i2 < bits2; i2++) {
                iArr[i2] = ((bitArray.readBit() ? bitArray.readBits(5) : 0) * 8) + bitArray.readBits(3);
            }
            for (int i3 = 0; i3 < bits2; i3++) {
                for (int i4 = 0; i4 < 8; i4++) {
                    if ((iArr[i3] & (1 << i4)) != 0) {
                        bitArray.skipBits(8);
                    }
                }
            }
        }
    }

    private static void readFloors(VorbisBitArray bitArray) throws ParserException {
        int bits = bitArray.readBits(6) + 1;
        for (int i = 0; i < bits; i++) {
            int bits2 = bitArray.readBits(16);
            if (bits2 == 0) {
                bitArray.skipBits(8);
                bitArray.skipBits(16);
                bitArray.skipBits(16);
                bitArray.skipBits(6);
                bitArray.skipBits(8);
                int bits3 = bitArray.readBits(4) + 1;
                for (int i2 = 0; i2 < bits3; i2++) {
                    bitArray.skipBits(8);
                }
            } else if (bits2 == 1) {
                int bits4 = bitArray.readBits(5);
                int[] iArr = new int[bits4];
                int i3 = -1;
                for (int i4 = 0; i4 < bits4; i4++) {
                    int bits5 = bitArray.readBits(4);
                    iArr[i4] = bits5;
                    if (bits5 > i3) {
                        i3 = bits5;
                    }
                }
                int i5 = i3 + 1;
                int[] iArr2 = new int[i5];
                for (int i6 = 0; i6 < i5; i6++) {
                    iArr2[i6] = bitArray.readBits(3) + 1;
                    int bits6 = bitArray.readBits(2);
                    if (bits6 > 0) {
                        bitArray.skipBits(8);
                    }
                    for (int i7 = 0; i7 < (1 << bits6); i7++) {
                        bitArray.skipBits(8);
                    }
                }
                bitArray.skipBits(2);
                int bits7 = bitArray.readBits(4);
                int i8 = 0;
                int i9 = 0;
                for (int i10 = 0; i10 < bits4; i10++) {
                    i8 += iArr2[iArr[i10]];
                    while (i9 < i8) {
                        bitArray.skipBits(bits7);
                        i9++;
                    }
                }
            } else {
                throw ParserException.createForMalformedContainer(new StringBuilder(52).append("floor type greater than 1 not decodable: ").append(bits2).toString(), null);
            }
        }
    }

    private static CodeBook readBook(VorbisBitArray bitArray) throws ParserException {
        if (bitArray.readBits(24) != 5653314) {
            throw ParserException.createForMalformedContainer(new StringBuilder(66).append("expected code book to start with [0x56, 0x43, 0x42] at ").append(bitArray.getPosition()).toString(), null);
        }
        int bits = bitArray.readBits(16);
        int bits2 = bitArray.readBits(24);
        long[] jArr = new long[bits2];
        boolean bit = bitArray.readBit();
        long jMapType1QuantValues = 0;
        if (!bit) {
            boolean bit2 = bitArray.readBit();
            for (int i = 0; i < bits2; i++) {
                if (bit2) {
                    if (bitArray.readBit()) {
                        jArr[i] = bitArray.readBits(5) + 1;
                    } else {
                        jArr[i] = 0;
                    }
                } else {
                    jArr[i] = bitArray.readBits(5) + 1;
                }
            }
        } else {
            int bits3 = bitArray.readBits(5) + 1;
            int i2 = 0;
            while (i2 < bits2) {
                int bits4 = bitArray.readBits(iLog(bits2 - i2));
                for (int i3 = 0; i3 < bits4 && i2 < bits2; i3++) {
                    jArr[i2] = bits3;
                    i2++;
                }
                bits3++;
            }
        }
        int bits5 = bitArray.readBits(4);
        if (bits5 > 2) {
            throw ParserException.createForMalformedContainer(new StringBuilder(53).append("lookup type greater than 2 not decodable: ").append(bits5).toString(), null);
        }
        if (bits5 == 1 || bits5 == 2) {
            bitArray.skipBits(32);
            bitArray.skipBits(32);
            int bits6 = bitArray.readBits(4) + 1;
            bitArray.skipBits(1);
            if (bits5 != 1) {
                jMapType1QuantValues = bits2 * bits;
            } else if (bits != 0) {
                jMapType1QuantValues = mapType1QuantValues(bits2, bits);
            }
            bitArray.skipBits((int) (jMapType1QuantValues * bits6));
        }
        return new CodeBook(bits, bits2, jArr, bits5, bit);
    }

    private static long mapType1QuantValues(long entries, long dimension) {
        return (long) Math.floor(Math.pow(entries, 1.0d / dimension));
    }

    private VorbisUtil() {
    }

    private static final class CodeBook {
        public final int dimensions;
        public final int entries;
        public final boolean isOrdered;
        public final long[] lengthMap;
        public final int lookupType;

        public CodeBook(int dimensions, int entries, long[] lengthMap, int lookupType, boolean isOrdered) {
            this.dimensions = dimensions;
            this.entries = entries;
            this.lengthMap = lengthMap;
            this.lookupType = lookupType;
            this.isOrdered = isOrdered;
        }
    }
}
