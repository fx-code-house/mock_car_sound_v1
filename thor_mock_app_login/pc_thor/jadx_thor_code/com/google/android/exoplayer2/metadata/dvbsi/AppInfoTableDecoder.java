package com.google.android.exoplayer2.metadata.dvbsi;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.metadata.SimpleMetadataDecoder;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.common.base.Charsets;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class AppInfoTableDecoder extends SimpleMetadataDecoder {
    public static final int APPLICATION_INFORMATION_TABLE_ID = 116;
    private static final int DESCRIPTOR_SIMPLE_APPLICATION_LOCATION = 21;
    private static final int DESCRIPTOR_TRANSPORT_PROTOCOL = 2;
    private static final int TRANSPORT_PROTOCOL_HTTP = 3;

    @Override // com.google.android.exoplayer2.metadata.SimpleMetadataDecoder
    protected Metadata decode(MetadataInputBuffer inputBuffer, ByteBuffer buffer) {
        if (buffer.get() == 116) {
            return parseAit(new ParsableBitArray(buffer.array(), buffer.limit()));
        }
        return null;
    }

    private static Metadata parseAit(ParsableBitArray sectionData) {
        sectionData.skipBits(12);
        int bytePosition = (sectionData.getBytePosition() + sectionData.readBits(12)) - 4;
        sectionData.skipBits(44);
        sectionData.skipBytes(sectionData.readBits(12));
        sectionData.skipBits(16);
        ArrayList arrayList = new ArrayList();
        while (true) {
            String bytesAsString = null;
            if (sectionData.getBytePosition() >= bytePosition) {
                break;
            }
            sectionData.skipBits(48);
            int bits = sectionData.readBits(8);
            sectionData.skipBits(4);
            int bytePosition2 = sectionData.getBytePosition() + sectionData.readBits(12);
            String bytesAsString2 = null;
            while (sectionData.getBytePosition() < bytePosition2) {
                int bits2 = sectionData.readBits(8);
                int bits3 = sectionData.readBits(8);
                int bytePosition3 = sectionData.getBytePosition() + bits3;
                if (bits2 == 2) {
                    int bits4 = sectionData.readBits(16);
                    sectionData.skipBits(8);
                    if (bits4 == 3) {
                        while (sectionData.getBytePosition() < bytePosition3) {
                            bytesAsString = sectionData.readBytesAsString(sectionData.readBits(8), Charsets.US_ASCII);
                            int bits5 = sectionData.readBits(8);
                            for (int i = 0; i < bits5; i++) {
                                sectionData.skipBytes(sectionData.readBits(8));
                            }
                        }
                    }
                } else if (bits2 == 21) {
                    bytesAsString2 = sectionData.readBytesAsString(bits3, Charsets.US_ASCII);
                }
                sectionData.setPosition(bytePosition3 * 8);
            }
            sectionData.setPosition(bytePosition2 * 8);
            if (bytesAsString != null && bytesAsString2 != null) {
                String strValueOf = String.valueOf(bytesAsString);
                String strValueOf2 = String.valueOf(bytesAsString2);
                arrayList.add(new AppInfoTable(bits, strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf)));
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }
}
