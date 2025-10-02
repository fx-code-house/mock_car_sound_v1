package com.google.android.exoplayer2.metadata.id3;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.metadata.SimpleMetadataDecoder;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import org.apache.commons.lang3.CharEncoding;

/* loaded from: classes.dex */
public final class Id3Decoder extends SimpleMetadataDecoder {
    private static final int FRAME_FLAG_V3_HAS_GROUP_IDENTIFIER = 32;
    private static final int FRAME_FLAG_V3_IS_COMPRESSED = 128;
    private static final int FRAME_FLAG_V3_IS_ENCRYPTED = 64;
    private static final int FRAME_FLAG_V4_HAS_DATA_LENGTH = 1;
    private static final int FRAME_FLAG_V4_HAS_GROUP_IDENTIFIER = 64;
    private static final int FRAME_FLAG_V4_IS_COMPRESSED = 8;
    private static final int FRAME_FLAG_V4_IS_ENCRYPTED = 4;
    private static final int FRAME_FLAG_V4_IS_UNSYNCHRONIZED = 2;
    public static final int ID3_HEADER_LENGTH = 10;
    public static final int ID3_TAG = 4801587;
    private static final int ID3_TEXT_ENCODING_ISO_8859_1 = 0;
    private static final int ID3_TEXT_ENCODING_UTF_16 = 1;
    private static final int ID3_TEXT_ENCODING_UTF_16BE = 2;
    private static final int ID3_TEXT_ENCODING_UTF_8 = 3;
    public static final FramePredicate NO_FRAMES_PREDICATE = new FramePredicate() { // from class: com.google.android.exoplayer2.metadata.id3.Id3Decoder$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate
        public final boolean evaluate(int i, int i2, int i3, int i4, int i5) {
            return Id3Decoder.lambda$static$0(i, i2, i3, i4, i5);
        }
    };
    private static final String TAG = "Id3Decoder";
    private final FramePredicate framePredicate;

    public interface FramePredicate {
        boolean evaluate(int majorVersion, int id0, int id1, int id2, int id3);
    }

    private static int delimiterLength(int encodingByte) {
        return (encodingByte == 0 || encodingByte == 3) ? 1 : 2;
    }

    private static String getCharsetName(int encodingByte) {
        return encodingByte != 1 ? encodingByte != 2 ? encodingByte != 3 ? "ISO-8859-1" : "UTF-8" : CharEncoding.UTF_16BE : "UTF-16";
    }

    static /* synthetic */ boolean lambda$static$0(int i, int i2, int i3, int i4, int i5) {
        return false;
    }

    public Id3Decoder() {
        this(null);
    }

    public Id3Decoder(FramePredicate framePredicate) {
        this.framePredicate = framePredicate;
    }

    @Override // com.google.android.exoplayer2.metadata.SimpleMetadataDecoder
    protected Metadata decode(MetadataInputBuffer inputBuffer, ByteBuffer buffer) {
        return decode(buffer.array(), buffer.limit());
    }

    public Metadata decode(byte[] data, int size) {
        ArrayList arrayList = new ArrayList();
        ParsableByteArray parsableByteArray = new ParsableByteArray(data, size);
        Id3Header id3HeaderDecodeHeader = decodeHeader(parsableByteArray);
        if (id3HeaderDecodeHeader == null) {
            return null;
        }
        int position = parsableByteArray.getPosition();
        int i = id3HeaderDecodeHeader.majorVersion == 2 ? 6 : 10;
        int iRemoveUnsynchronization = id3HeaderDecodeHeader.framesSize;
        if (id3HeaderDecodeHeader.isUnsynchronized) {
            iRemoveUnsynchronization = removeUnsynchronization(parsableByteArray, id3HeaderDecodeHeader.framesSize);
        }
        parsableByteArray.setLimit(position + iRemoveUnsynchronization);
        boolean z = false;
        if (!validateFrames(parsableByteArray, id3HeaderDecodeHeader.majorVersion, i, false)) {
            if (id3HeaderDecodeHeader.majorVersion != 4 || !validateFrames(parsableByteArray, 4, i, true)) {
                Log.w(TAG, new StringBuilder(56).append("Failed to validate ID3 tag with majorVersion=").append(id3HeaderDecodeHeader.majorVersion).toString());
                return null;
            }
            z = true;
        }
        while (parsableByteArray.bytesLeft() >= i) {
            Id3Frame id3FrameDecodeFrame = decodeFrame(id3HeaderDecodeHeader.majorVersion, parsableByteArray, z, i, this.framePredicate);
            if (id3FrameDecodeFrame != null) {
                arrayList.add(id3FrameDecodeFrame);
            }
        }
        return new Metadata(arrayList);
    }

    private static Id3Header decodeHeader(ParsableByteArray data) {
        if (data.bytesLeft() < 10) {
            Log.w(TAG, "Data too short to be an ID3 tag");
            return null;
        }
        int unsignedInt24 = data.readUnsignedInt24();
        boolean z = false;
        if (unsignedInt24 != 4801587) {
            String strValueOf = String.valueOf(String.format("%06X", Integer.valueOf(unsignedInt24)));
            Log.w(TAG, strValueOf.length() != 0 ? "Unexpected first three bytes of ID3 tag header: 0x".concat(strValueOf) : new String("Unexpected first three bytes of ID3 tag header: 0x"));
            return null;
        }
        int unsignedByte = data.readUnsignedByte();
        data.skipBytes(1);
        int unsignedByte2 = data.readUnsignedByte();
        int synchSafeInt = data.readSynchSafeInt();
        if (unsignedByte == 2) {
            if ((unsignedByte2 & 64) != 0) {
                Log.w(TAG, "Skipped ID3 tag with majorVersion=2 and undefined compression scheme");
                return null;
            }
        } else if (unsignedByte == 3) {
            if ((unsignedByte2 & 64) != 0) {
                int i = data.readInt();
                data.skipBytes(i);
                synchSafeInt -= i + 4;
            }
        } else {
            if (unsignedByte != 4) {
                Log.w(TAG, new StringBuilder(57).append("Skipped ID3 tag with unsupported majorVersion=").append(unsignedByte).toString());
                return null;
            }
            if ((unsignedByte2 & 64) != 0) {
                int synchSafeInt2 = data.readSynchSafeInt();
                data.skipBytes(synchSafeInt2 - 4);
                synchSafeInt -= synchSafeInt2;
            }
            if ((unsignedByte2 & 16) != 0) {
                synchSafeInt -= 10;
            }
        }
        if (unsignedByte < 4 && (unsignedByte2 & 128) != 0) {
            z = true;
        }
        return new Id3Header(unsignedByte, z, synchSafeInt);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0079 A[PHI: r3
      0x0079: PHI (r3v16 int) = (r3v5 int), (r3v19 int) binds: [B:40:0x0086, B:31:0x0076] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean validateFrames(com.google.android.exoplayer2.util.ParsableByteArray r18, int r19, int r20, boolean r21) {
        /*
            r1 = r18
            r0 = r19
            int r2 = r18.getPosition()
        L8:
            int r3 = r18.bytesLeft()     // Catch: java.lang.Throwable -> Laf
            r4 = 1
            r5 = r20
            if (r3 < r5) goto Lab
            r3 = 3
            r6 = 0
            if (r0 < r3) goto L22
            int r7 = r18.readInt()     // Catch: java.lang.Throwable -> Laf
            long r8 = r18.readUnsignedInt()     // Catch: java.lang.Throwable -> Laf
            int r10 = r18.readUnsignedShort()     // Catch: java.lang.Throwable -> Laf
            goto L2c
        L22:
            int r7 = r18.readUnsignedInt24()     // Catch: java.lang.Throwable -> Laf
            int r8 = r18.readUnsignedInt24()     // Catch: java.lang.Throwable -> Laf
            long r8 = (long) r8
            r10 = r6
        L2c:
            r11 = 0
            if (r7 != 0) goto L3a
            int r7 = (r8 > r11 ? 1 : (r8 == r11 ? 0 : -1))
            if (r7 != 0) goto L3a
            if (r10 != 0) goto L3a
            r1.setPosition(r2)
            return r4
        L3a:
            r7 = 4
            if (r0 != r7) goto L6b
            if (r21 != 0) goto L6b
            r13 = 8421504(0x808080, double:4.160776E-317)
            long r13 = r13 & r8
            int r11 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r11 == 0) goto L4b
            r1.setPosition(r2)
            return r6
        L4b:
            r11 = 255(0xff, double:1.26E-321)
            long r13 = r8 & r11
            r15 = 8
            long r15 = r8 >> r15
            long r15 = r15 & r11
            r17 = 7
            long r15 = r15 << r17
            long r13 = r13 | r15
            r15 = 16
            long r15 = r8 >> r15
            long r15 = r15 & r11
            r17 = 14
            long r15 = r15 << r17
            long r13 = r13 | r15
            r15 = 24
            long r8 = r8 >> r15
            long r8 = r8 & r11
            r11 = 21
            long r8 = r8 << r11
            long r8 = r8 | r13
        L6b:
            if (r0 != r7) goto L7b
            r3 = r10 & 64
            if (r3 == 0) goto L73
            r3 = r4
            goto L74
        L73:
            r3 = r6
        L74:
            r7 = r10 & 1
            if (r7 == 0) goto L79
            goto L8b
        L79:
            r4 = r6
            goto L8b
        L7b:
            if (r0 != r3) goto L89
            r3 = r10 & 32
            if (r3 == 0) goto L83
            r3 = r4
            goto L84
        L83:
            r3 = r6
        L84:
            r7 = r10 & 128(0x80, float:1.8E-43)
            if (r7 == 0) goto L79
            goto L8b
        L89:
            r3 = r6
            r4 = r3
        L8b:
            if (r4 == 0) goto L8f
            int r3 = r3 + 4
        L8f:
            long r3 = (long) r3
            int r3 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r3 >= 0) goto L98
            r1.setPosition(r2)
            return r6
        L98:
            int r3 = r18.bytesLeft()     // Catch: java.lang.Throwable -> Laf
            long r3 = (long) r3
            int r3 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
            if (r3 >= 0) goto La5
            r1.setPosition(r2)
            return r6
        La5:
            int r3 = (int) r8
            r1.skipBytes(r3)     // Catch: java.lang.Throwable -> Laf
            goto L8
        Lab:
            r1.setPosition(r2)
            return r4
        Laf:
            r0 = move-exception
            r1.setPosition(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.id3.Id3Decoder.validateFrames(com.google.android.exoplayer2.util.ParsableByteArray, int, int, boolean):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:140:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01ea A[Catch: all -> 0x012e, UnsupportedEncodingException -> 0x0226, TryCatch #0 {UnsupportedEncodingException -> 0x0226, blocks: (B:91:0x011c, B:159:0x01f4, B:93:0x0124, B:102:0x013d, B:104:0x0145, B:112:0x015f, B:121:0x0177, B:132:0x0192, B:139:0x01a4, B:145:0x01b3, B:150:0x01cb, B:156:0x01e5, B:157:0x01ea), top: B:169:0x0112, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.google.android.exoplayer2.metadata.id3.Id3Frame decodeFrame(int r19, com.google.android.exoplayer2.util.ParsableByteArray r20, boolean r21, int r22, com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate r23) {
        /*
            Method dump skipped, instructions count: 572
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.id3.Id3Decoder.decodeFrame(int, com.google.android.exoplayer2.util.ParsableByteArray, boolean, int, com.google.android.exoplayer2.metadata.id3.Id3Decoder$FramePredicate):com.google.android.exoplayer2.metadata.id3.Id3Frame");
    }

    private static TextInformationFrame decodeTxxxFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        if (frameSize < 1) {
            return null;
        }
        int unsignedByte = id3Data.readUnsignedByte();
        String charsetName = getCharsetName(unsignedByte);
        int i = frameSize - 1;
        byte[] bArr = new byte[i];
        id3Data.readBytes(bArr, 0, i);
        int iIndexOfEos = indexOfEos(bArr, 0, unsignedByte);
        String str = new String(bArr, 0, iIndexOfEos, charsetName);
        int iDelimiterLength = iIndexOfEos + delimiterLength(unsignedByte);
        return new TextInformationFrame("TXXX", str, decodeStringIfValid(bArr, iDelimiterLength, indexOfEos(bArr, iDelimiterLength, unsignedByte), charsetName));
    }

    private static TextInformationFrame decodeTextInformationFrame(ParsableByteArray id3Data, int frameSize, String id) throws UnsupportedEncodingException {
        if (frameSize < 1) {
            return null;
        }
        int unsignedByte = id3Data.readUnsignedByte();
        String charsetName = getCharsetName(unsignedByte);
        int i = frameSize - 1;
        byte[] bArr = new byte[i];
        id3Data.readBytes(bArr, 0, i);
        return new TextInformationFrame(id, null, new String(bArr, 0, indexOfEos(bArr, 0, unsignedByte), charsetName));
    }

    private static UrlLinkFrame decodeWxxxFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        if (frameSize < 1) {
            return null;
        }
        int unsignedByte = id3Data.readUnsignedByte();
        String charsetName = getCharsetName(unsignedByte);
        int i = frameSize - 1;
        byte[] bArr = new byte[i];
        id3Data.readBytes(bArr, 0, i);
        int iIndexOfEos = indexOfEos(bArr, 0, unsignedByte);
        String str = new String(bArr, 0, iIndexOfEos, charsetName);
        int iDelimiterLength = iIndexOfEos + delimiterLength(unsignedByte);
        return new UrlLinkFrame("WXXX", str, decodeStringIfValid(bArr, iDelimiterLength, indexOfZeroByte(bArr, iDelimiterLength), "ISO-8859-1"));
    }

    private static UrlLinkFrame decodeUrlLinkFrame(ParsableByteArray id3Data, int frameSize, String id) throws UnsupportedEncodingException {
        byte[] bArr = new byte[frameSize];
        id3Data.readBytes(bArr, 0, frameSize);
        return new UrlLinkFrame(id, null, new String(bArr, 0, indexOfZeroByte(bArr, 0), "ISO-8859-1"));
    }

    private static PrivFrame decodePrivFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        byte[] bArr = new byte[frameSize];
        id3Data.readBytes(bArr, 0, frameSize);
        int iIndexOfZeroByte = indexOfZeroByte(bArr, 0);
        return new PrivFrame(new String(bArr, 0, iIndexOfZeroByte, "ISO-8859-1"), copyOfRangeIfValid(bArr, iIndexOfZeroByte + 1, frameSize));
    }

    private static GeobFrame decodeGeobFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        int unsignedByte = id3Data.readUnsignedByte();
        String charsetName = getCharsetName(unsignedByte);
        int i = frameSize - 1;
        byte[] bArr = new byte[i];
        id3Data.readBytes(bArr, 0, i);
        int iIndexOfZeroByte = indexOfZeroByte(bArr, 0);
        String str = new String(bArr, 0, iIndexOfZeroByte, "ISO-8859-1");
        int i2 = iIndexOfZeroByte + 1;
        int iIndexOfEos = indexOfEos(bArr, i2, unsignedByte);
        String strDecodeStringIfValid = decodeStringIfValid(bArr, i2, iIndexOfEos, charsetName);
        int iDelimiterLength = iIndexOfEos + delimiterLength(unsignedByte);
        int iIndexOfEos2 = indexOfEos(bArr, iDelimiterLength, unsignedByte);
        return new GeobFrame(str, strDecodeStringIfValid, decodeStringIfValid(bArr, iDelimiterLength, iIndexOfEos2, charsetName), copyOfRangeIfValid(bArr, iIndexOfEos2 + delimiterLength(unsignedByte), i));
    }

    private static ApicFrame decodeApicFrame(ParsableByteArray id3Data, int frameSize, int majorVersion) throws UnsupportedEncodingException {
        int iIndexOfZeroByte;
        String strConcat;
        int unsignedByte = id3Data.readUnsignedByte();
        String charsetName = getCharsetName(unsignedByte);
        int i = frameSize - 1;
        byte[] bArr = new byte[i];
        id3Data.readBytes(bArr, 0, i);
        if (majorVersion == 2) {
            String strValueOf = String.valueOf(Ascii.toLowerCase(new String(bArr, 0, 3, "ISO-8859-1")));
            strConcat = strValueOf.length() != 0 ? "image/".concat(strValueOf) : new String("image/");
            if ("image/jpg".equals(strConcat)) {
                strConcat = MimeTypes.IMAGE_JPEG;
            }
            iIndexOfZeroByte = 2;
        } else {
            iIndexOfZeroByte = indexOfZeroByte(bArr, 0);
            String lowerCase = Ascii.toLowerCase(new String(bArr, 0, iIndexOfZeroByte, "ISO-8859-1"));
            if (lowerCase.indexOf(47) == -1) {
                String strValueOf2 = String.valueOf(lowerCase);
                if (strValueOf2.length() != 0) {
                    strConcat = "image/".concat(strValueOf2);
                } else {
                    lowerCase = new String("image/");
                    strConcat = lowerCase;
                }
            } else {
                strConcat = lowerCase;
            }
        }
        int i2 = bArr[iIndexOfZeroByte + 1] & 255;
        int i3 = iIndexOfZeroByte + 2;
        int iIndexOfEos = indexOfEos(bArr, i3, unsignedByte);
        return new ApicFrame(strConcat, new String(bArr, i3, iIndexOfEos - i3, charsetName), i2, copyOfRangeIfValid(bArr, iIndexOfEos + delimiterLength(unsignedByte), i));
    }

    private static CommentFrame decodeCommentFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        if (frameSize < 4) {
            return null;
        }
        int unsignedByte = id3Data.readUnsignedByte();
        String charsetName = getCharsetName(unsignedByte);
        byte[] bArr = new byte[3];
        id3Data.readBytes(bArr, 0, 3);
        String str = new String(bArr, 0, 3);
        int i = frameSize - 4;
        byte[] bArr2 = new byte[i];
        id3Data.readBytes(bArr2, 0, i);
        int iIndexOfEos = indexOfEos(bArr2, 0, unsignedByte);
        String str2 = new String(bArr2, 0, iIndexOfEos, charsetName);
        int iDelimiterLength = iIndexOfEos + delimiterLength(unsignedByte);
        return new CommentFrame(str, str2, decodeStringIfValid(bArr2, iDelimiterLength, indexOfEos(bArr2, iDelimiterLength, unsignedByte), charsetName));
    }

    private static ChapterFrame decodeChapterFrame(ParsableByteArray id3Data, int frameSize, int majorVersion, boolean unsignedIntFrameSizeHack, int frameHeaderSize, FramePredicate framePredicate) throws UnsupportedEncodingException {
        int position = id3Data.getPosition();
        int iIndexOfZeroByte = indexOfZeroByte(id3Data.getData(), position);
        String str = new String(id3Data.getData(), position, iIndexOfZeroByte - position, "ISO-8859-1");
        id3Data.setPosition(iIndexOfZeroByte + 1);
        int i = id3Data.readInt();
        int i2 = id3Data.readInt();
        long unsignedInt = id3Data.readUnsignedInt();
        long j = unsignedInt == 4294967295L ? -1L : unsignedInt;
        long unsignedInt2 = id3Data.readUnsignedInt();
        long j2 = unsignedInt2 == 4294967295L ? -1L : unsignedInt2;
        ArrayList arrayList = new ArrayList();
        int i3 = position + frameSize;
        while (id3Data.getPosition() < i3) {
            Id3Frame id3FrameDecodeFrame = decodeFrame(majorVersion, id3Data, unsignedIntFrameSizeHack, frameHeaderSize, framePredicate);
            if (id3FrameDecodeFrame != null) {
                arrayList.add(id3FrameDecodeFrame);
            }
        }
        return new ChapterFrame(str, i, i2, j, j2, (Id3Frame[]) arrayList.toArray(new Id3Frame[0]));
    }

    private static ChapterTocFrame decodeChapterTOCFrame(ParsableByteArray id3Data, int frameSize, int majorVersion, boolean unsignedIntFrameSizeHack, int frameHeaderSize, FramePredicate framePredicate) throws UnsupportedEncodingException {
        int position = id3Data.getPosition();
        int iIndexOfZeroByte = indexOfZeroByte(id3Data.getData(), position);
        String str = new String(id3Data.getData(), position, iIndexOfZeroByte - position, "ISO-8859-1");
        id3Data.setPosition(iIndexOfZeroByte + 1);
        int unsignedByte = id3Data.readUnsignedByte();
        boolean z = (unsignedByte & 2) != 0;
        boolean z2 = (unsignedByte & 1) != 0;
        int unsignedByte2 = id3Data.readUnsignedByte();
        String[] strArr = new String[unsignedByte2];
        for (int i = 0; i < unsignedByte2; i++) {
            int position2 = id3Data.getPosition();
            int iIndexOfZeroByte2 = indexOfZeroByte(id3Data.getData(), position2);
            strArr[i] = new String(id3Data.getData(), position2, iIndexOfZeroByte2 - position2, "ISO-8859-1");
            id3Data.setPosition(iIndexOfZeroByte2 + 1);
        }
        ArrayList arrayList = new ArrayList();
        int i2 = position + frameSize;
        while (id3Data.getPosition() < i2) {
            Id3Frame id3FrameDecodeFrame = decodeFrame(majorVersion, id3Data, unsignedIntFrameSizeHack, frameHeaderSize, framePredicate);
            if (id3FrameDecodeFrame != null) {
                arrayList.add(id3FrameDecodeFrame);
            }
        }
        return new ChapterTocFrame(str, z, z2, strArr, (Id3Frame[]) arrayList.toArray(new Id3Frame[0]));
    }

    private static MlltFrame decodeMlltFrame(ParsableByteArray id3Data, int frameSize) {
        int unsignedShort = id3Data.readUnsignedShort();
        int unsignedInt24 = id3Data.readUnsignedInt24();
        int unsignedInt242 = id3Data.readUnsignedInt24();
        int unsignedByte = id3Data.readUnsignedByte();
        int unsignedByte2 = id3Data.readUnsignedByte();
        ParsableBitArray parsableBitArray = new ParsableBitArray();
        parsableBitArray.reset(id3Data);
        int i = ((frameSize - 10) * 8) / (unsignedByte + unsignedByte2);
        int[] iArr = new int[i];
        int[] iArr2 = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            int bits = parsableBitArray.readBits(unsignedByte);
            int bits2 = parsableBitArray.readBits(unsignedByte2);
            iArr[i2] = bits;
            iArr2[i2] = bits2;
        }
        return new MlltFrame(unsignedShort, unsignedInt24, unsignedInt242, iArr, iArr2);
    }

    private static BinaryFrame decodeBinaryFrame(ParsableByteArray id3Data, int frameSize, String id) {
        byte[] bArr = new byte[frameSize];
        id3Data.readBytes(bArr, 0, frameSize);
        return new BinaryFrame(id, bArr);
    }

    private static int removeUnsynchronization(ParsableByteArray data, int length) {
        byte[] data2 = data.getData();
        int position = data.getPosition();
        int i = position;
        while (true) {
            int i2 = i + 1;
            if (i2 >= position + length) {
                return length;
            }
            if ((data2[i] & 255) == 255 && data2[i2] == 0) {
                System.arraycopy(data2, i + 2, data2, i2, (length - (i - position)) - 2);
                length--;
            }
            i = i2;
        }
    }

    private static String getFrameId(int majorVersion, int frameId0, int frameId1, int frameId2, int frameId3) {
        return majorVersion == 2 ? String.format(Locale.US, "%c%c%c", Integer.valueOf(frameId0), Integer.valueOf(frameId1), Integer.valueOf(frameId2)) : String.format(Locale.US, "%c%c%c%c", Integer.valueOf(frameId0), Integer.valueOf(frameId1), Integer.valueOf(frameId2), Integer.valueOf(frameId3));
    }

    private static int indexOfEos(byte[] data, int fromIndex, int encoding) {
        int iIndexOfZeroByte = indexOfZeroByte(data, fromIndex);
        if (encoding == 0 || encoding == 3) {
            return iIndexOfZeroByte;
        }
        while (iIndexOfZeroByte < data.length - 1) {
            if (iIndexOfZeroByte % 2 == 0 && data[iIndexOfZeroByte + 1] == 0) {
                return iIndexOfZeroByte;
            }
            iIndexOfZeroByte = indexOfZeroByte(data, iIndexOfZeroByte + 1);
        }
        return data.length;
    }

    private static int indexOfZeroByte(byte[] data, int fromIndex) {
        while (fromIndex < data.length) {
            if (data[fromIndex] == 0) {
                return fromIndex;
            }
            fromIndex++;
        }
        return data.length;
    }

    private static byte[] copyOfRangeIfValid(byte[] data, int from, int to) {
        if (to <= from) {
            return Util.EMPTY_BYTE_ARRAY;
        }
        return Arrays.copyOfRange(data, from, to);
    }

    private static String decodeStringIfValid(byte[] data, int from, int to, String charsetName) throws UnsupportedEncodingException {
        return (to <= from || to > data.length) ? "" : new String(data, from, to - from, charsetName);
    }

    private static final class Id3Header {
        private final int framesSize;
        private final boolean isUnsynchronized;
        private final int majorVersion;

        public Id3Header(int majorVersion, boolean isUnsynchronized, int framesSize) {
            this.majorVersion = majorVersion;
            this.isUnsynchronized = isUnsynchronized;
            this.framesSize = framesSize;
        }
    }
}
