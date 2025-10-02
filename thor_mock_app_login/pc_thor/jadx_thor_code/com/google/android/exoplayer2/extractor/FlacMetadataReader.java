package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.FlacStreamMetadata;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.flac.PictureFrame;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.base.Charsets;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class FlacMetadataReader {
    private static final int SEEK_POINT_SIZE = 18;
    private static final int STREAM_MARKER = 1716281667;
    private static final int SYNC_CODE = 16382;

    public static final class FlacStreamMetadataHolder {
        public FlacStreamMetadata flacStreamMetadata;

        public FlacStreamMetadataHolder(FlacStreamMetadata flacStreamMetadata) {
            this.flacStreamMetadata = flacStreamMetadata;
        }
    }

    public static Metadata peekId3Metadata(ExtractorInput input, boolean parseData) throws IOException {
        Metadata metadataPeekId3Data = new Id3Peeker().peekId3Data(input, parseData ? null : Id3Decoder.NO_FRAMES_PREDICATE);
        if (metadataPeekId3Data == null || metadataPeekId3Data.length() == 0) {
            return null;
        }
        return metadataPeekId3Data;
    }

    public static boolean checkAndPeekStreamMarker(ExtractorInput input) throws IOException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(4);
        input.peekFully(parsableByteArray.getData(), 0, 4);
        return parsableByteArray.readUnsignedInt() == 1716281667;
    }

    public static Metadata readId3Metadata(ExtractorInput input, boolean parseData) throws IOException {
        input.resetPeekPosition();
        long peekPosition = input.getPeekPosition();
        Metadata metadataPeekId3Metadata = peekId3Metadata(input, parseData);
        input.skipFully((int) (input.getPeekPosition() - peekPosition));
        return metadataPeekId3Metadata;
    }

    public static void readStreamMarker(ExtractorInput input) throws IOException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(4);
        input.readFully(parsableByteArray.getData(), 0, 4);
        if (parsableByteArray.readUnsignedInt() != 1716281667) {
            throw ParserException.createForMalformedContainer("Failed to read FLAC stream marker.", null);
        }
    }

    public static boolean readMetadataBlock(ExtractorInput input, FlacStreamMetadataHolder metadataHolder) throws IOException {
        input.resetPeekPosition();
        ParsableBitArray parsableBitArray = new ParsableBitArray(new byte[4]);
        input.peekFully(parsableBitArray.data, 0, 4);
        boolean bit = parsableBitArray.readBit();
        int bits = parsableBitArray.readBits(7);
        int bits2 = parsableBitArray.readBits(24) + 4;
        if (bits == 0) {
            metadataHolder.flacStreamMetadata = readStreamInfoBlock(input);
        } else {
            FlacStreamMetadata flacStreamMetadata = metadataHolder.flacStreamMetadata;
            if (flacStreamMetadata == null) {
                throw new IllegalArgumentException();
            }
            if (bits == 3) {
                metadataHolder.flacStreamMetadata = flacStreamMetadata.copyWithSeekTable(readSeekTableMetadataBlock(input, bits2));
            } else if (bits == 4) {
                metadataHolder.flacStreamMetadata = flacStreamMetadata.copyWithVorbisComments(readVorbisCommentMetadataBlock(input, bits2));
            } else if (bits == 6) {
                metadataHolder.flacStreamMetadata = flacStreamMetadata.copyWithPictureFrames(Collections.singletonList(readPictureMetadataBlock(input, bits2)));
            } else {
                input.skipFully(bits2);
            }
        }
        return bit;
    }

    public static FlacStreamMetadata.SeekTable readSeekTableMetadataBlock(ParsableByteArray data) {
        data.skipBytes(1);
        int unsignedInt24 = data.readUnsignedInt24();
        long position = data.getPosition() + unsignedInt24;
        int i = unsignedInt24 / 18;
        long[] jArrCopyOf = new long[i];
        long[] jArrCopyOf2 = new long[i];
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                break;
            }
            long j = data.readLong();
            if (j == -1) {
                jArrCopyOf = Arrays.copyOf(jArrCopyOf, i2);
                jArrCopyOf2 = Arrays.copyOf(jArrCopyOf2, i2);
                break;
            }
            jArrCopyOf[i2] = j;
            jArrCopyOf2[i2] = data.readLong();
            data.skipBytes(2);
            i2++;
        }
        data.skipBytes((int) (position - data.getPosition()));
        return new FlacStreamMetadata.SeekTable(jArrCopyOf, jArrCopyOf2);
    }

    public static int getFrameStartMarker(ExtractorInput input) throws IOException {
        input.resetPeekPosition();
        ParsableByteArray parsableByteArray = new ParsableByteArray(2);
        input.peekFully(parsableByteArray.getData(), 0, 2);
        int unsignedShort = parsableByteArray.readUnsignedShort();
        if ((unsignedShort >> 2) != SYNC_CODE) {
            input.resetPeekPosition();
            throw ParserException.createForMalformedContainer("First frame does not start with sync code.", null);
        }
        input.resetPeekPosition();
        return unsignedShort;
    }

    private static FlacStreamMetadata readStreamInfoBlock(ExtractorInput input) throws IOException {
        byte[] bArr = new byte[38];
        input.readFully(bArr, 0, 38);
        return new FlacStreamMetadata(bArr, 4);
    }

    private static FlacStreamMetadata.SeekTable readSeekTableMetadataBlock(ExtractorInput input, int length) throws IOException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(length);
        input.readFully(parsableByteArray.getData(), 0, length);
        return readSeekTableMetadataBlock(parsableByteArray);
    }

    private static List<String> readVorbisCommentMetadataBlock(ExtractorInput input, int length) throws IOException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(length);
        input.readFully(parsableByteArray.getData(), 0, length);
        parsableByteArray.skipBytes(4);
        return Arrays.asList(VorbisUtil.readVorbisCommentHeader(parsableByteArray, false, false).comments);
    }

    private static PictureFrame readPictureMetadataBlock(ExtractorInput input, int length) throws IOException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(length);
        input.readFully(parsableByteArray.getData(), 0, length);
        parsableByteArray.skipBytes(4);
        int i = parsableByteArray.readInt();
        String string = parsableByteArray.readString(parsableByteArray.readInt(), Charsets.US_ASCII);
        String string2 = parsableByteArray.readString(parsableByteArray.readInt());
        int i2 = parsableByteArray.readInt();
        int i3 = parsableByteArray.readInt();
        int i4 = parsableByteArray.readInt();
        int i5 = parsableByteArray.readInt();
        int i6 = parsableByteArray.readInt();
        byte[] bArr = new byte[i6];
        parsableByteArray.readBytes(bArr, 0, i6);
        return new PictureFrame(i, string, string2, i2, i3, i4, i5, bArr);
    }

    private FlacMetadataReader() {
    }
}
