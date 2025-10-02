package com.google.android.exoplayer2.extractor.mp4;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.SlowMotionData;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.base.Splitter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
final class SefReader {
    private static final int LENGTH_OF_ONE_SDR = 12;
    private static final int SAMSUNG_TAIL_SIGNATURE = 1397048916;
    private static final int STATE_CHECKING_FOR_SEF = 1;
    private static final int STATE_READING_SDRS = 2;
    private static final int STATE_READING_SEF_DATA = 3;
    private static final int STATE_SHOULD_CHECK_FOR_SEF = 0;
    private static final String TAG = "SefReader";
    private static final int TAIL_FOOTER_LENGTH = 8;
    private static final int TAIL_HEADER_LENGTH = 12;
    private static final int TYPE_SLOW_MOTION_DATA = 2192;
    private static final int TYPE_SUPER_SLOW_DEFLICKERING_ON = 2820;
    private static final int TYPE_SUPER_SLOW_MOTION_BGM = 2817;
    private static final int TYPE_SUPER_SLOW_MOTION_DATA = 2816;
    private static final int TYPE_SUPER_SLOW_MOTION_EDIT_DATA = 2819;
    private final List<DataReference> dataReferences = new ArrayList();
    private int readerState = 0;
    private int tailLength;
    private static final Splitter COLON_SPLITTER = Splitter.on(':');
    private static final Splitter ASTERISK_SPLITTER = Splitter.on('*');

    public void reset() {
        this.dataReferences.clear();
        this.readerState = 0;
    }

    public int read(ExtractorInput input, PositionHolder seekPosition, List<Metadata.Entry> slowMotionMetadataEntries) throws IOException {
        int i = this.readerState;
        long j = 0;
        if (i == 0) {
            long length = input.getLength();
            if (length != -1 && length >= 8) {
                j = length - 8;
            }
            seekPosition.position = j;
            this.readerState = 1;
        } else if (i == 1) {
            checkForSefData(input, seekPosition);
        } else if (i == 2) {
            readSdrs(input, seekPosition);
        } else if (i == 3) {
            readSefData(input, slowMotionMetadataEntries);
            seekPosition.position = 0L;
        } else {
            throw new IllegalStateException();
        }
        return 1;
    }

    private void checkForSefData(ExtractorInput input, PositionHolder seekPosition) throws IOException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(8);
        input.readFully(parsableByteArray.getData(), 0, 8);
        this.tailLength = parsableByteArray.readLittleEndianInt() + 8;
        if (parsableByteArray.readInt() != SAMSUNG_TAIL_SIGNATURE) {
            seekPosition.position = 0L;
        } else {
            seekPosition.position = input.getPosition() - (this.tailLength - 12);
            this.readerState = 2;
        }
    }

    private void readSdrs(ExtractorInput input, PositionHolder seekPosition) throws IOException {
        long length = input.getLength();
        int i = (this.tailLength - 12) - 8;
        ParsableByteArray parsableByteArray = new ParsableByteArray(i);
        input.readFully(parsableByteArray.getData(), 0, i);
        for (int i2 = 0; i2 < i / 12; i2++) {
            parsableByteArray.skipBytes(2);
            short littleEndianShort = parsableByteArray.readLittleEndianShort();
            if (littleEndianShort == TYPE_SLOW_MOTION_DATA || littleEndianShort == TYPE_SUPER_SLOW_MOTION_DATA || littleEndianShort == TYPE_SUPER_SLOW_MOTION_BGM || littleEndianShort == TYPE_SUPER_SLOW_MOTION_EDIT_DATA || littleEndianShort == TYPE_SUPER_SLOW_DEFLICKERING_ON) {
                this.dataReferences.add(new DataReference(littleEndianShort, (length - this.tailLength) - parsableByteArray.readLittleEndianInt(), parsableByteArray.readLittleEndianInt()));
            } else {
                parsableByteArray.skipBytes(8);
            }
        }
        if (this.dataReferences.isEmpty()) {
            seekPosition.position = 0L;
        } else {
            this.readerState = 3;
            seekPosition.position = this.dataReferences.get(0).startOffset;
        }
    }

    private void readSefData(ExtractorInput input, List<Metadata.Entry> slowMotionMetadataEntries) throws IOException {
        long position = input.getPosition();
        int length = (int) ((input.getLength() - input.getPosition()) - this.tailLength);
        ParsableByteArray parsableByteArray = new ParsableByteArray(length);
        input.readFully(parsableByteArray.getData(), 0, length);
        for (int i = 0; i < this.dataReferences.size(); i++) {
            DataReference dataReference = this.dataReferences.get(i);
            parsableByteArray.setPosition((int) (dataReference.startOffset - position));
            parsableByteArray.skipBytes(4);
            int littleEndianInt = parsableByteArray.readLittleEndianInt();
            int iNameToDataType = nameToDataType(parsableByteArray.readString(littleEndianInt));
            int i2 = dataReference.size - (littleEndianInt + 8);
            if (iNameToDataType == TYPE_SLOW_MOTION_DATA) {
                slowMotionMetadataEntries.add(readSlowMotionData(parsableByteArray, i2));
            } else if (iNameToDataType != TYPE_SUPER_SLOW_MOTION_DATA && iNameToDataType != TYPE_SUPER_SLOW_MOTION_BGM && iNameToDataType != TYPE_SUPER_SLOW_MOTION_EDIT_DATA && iNameToDataType != TYPE_SUPER_SLOW_DEFLICKERING_ON) {
                throw new IllegalStateException();
            }
        }
    }

    private static SlowMotionData readSlowMotionData(ParsableByteArray data, int dataLength) throws ParserException {
        ArrayList arrayList = new ArrayList();
        List<String> listSplitToList = ASTERISK_SPLITTER.splitToList(data.readString(dataLength));
        for (int i = 0; i < listSplitToList.size(); i++) {
            List<String> listSplitToList2 = COLON_SPLITTER.splitToList(listSplitToList.get(i));
            if (listSplitToList2.size() != 3) {
                throw ParserException.createForMalformedContainer(null, null);
            }
            try {
                arrayList.add(new SlowMotionData.Segment(Long.parseLong(listSplitToList2.get(0)), Long.parseLong(listSplitToList2.get(1)), 1 << (Integer.parseInt(listSplitToList2.get(2)) - 1)));
            } catch (NumberFormatException e) {
                throw ParserException.createForMalformedContainer(null, e);
            }
        }
        return new SlowMotionData(arrayList);
    }

    private static int nameToDataType(String name) throws ParserException {
        name.hashCode();
        switch (name) {
            case "SlowMotion_Data":
                return TYPE_SLOW_MOTION_DATA;
            case "Super_SlowMotion_Edit_Data":
                return TYPE_SUPER_SLOW_MOTION_EDIT_DATA;
            case "Super_SlowMotion_Data":
                return TYPE_SUPER_SLOW_MOTION_DATA;
            case "Super_SlowMotion_Deflickering_On":
                return TYPE_SUPER_SLOW_DEFLICKERING_ON;
            case "Super_SlowMotion_BGM":
                return TYPE_SUPER_SLOW_MOTION_BGM;
            default:
                throw ParserException.createForMalformedContainer("Invalid SEF name", null);
        }
    }

    private static final class DataReference {
        public final int dataType;
        public final int size;
        public final long startOffset;

        public DataReference(int dataType, long startOffset, int size) {
            this.dataType = dataType;
            this.startOffset = startOffset;
            this.size = size;
        }
    }
}
