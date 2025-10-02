package com.google.android.exoplayer2.text.pgs;

import android.graphics.Bitmap;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.zip.Inflater;

/* loaded from: classes.dex */
public final class PgsDecoder extends SimpleSubtitleDecoder {
    private static final byte INFLATE_HEADER = 120;
    private static final int SECTION_TYPE_BITMAP_PICTURE = 21;
    private static final int SECTION_TYPE_END = 128;
    private static final int SECTION_TYPE_IDENTIFIER = 22;
    private static final int SECTION_TYPE_PALETTE = 20;
    private final ParsableByteArray buffer;
    private final CueBuilder cueBuilder;
    private final ParsableByteArray inflatedBuffer;
    private Inflater inflater;

    public PgsDecoder() {
        super("PgsDecoder");
        this.buffer = new ParsableByteArray();
        this.inflatedBuffer = new ParsableByteArray();
        this.cueBuilder = new CueBuilder();
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    protected Subtitle decode(byte[] data, int size, boolean reset) throws SubtitleDecoderException {
        this.buffer.reset(data, size);
        maybeInflateData(this.buffer);
        this.cueBuilder.reset();
        ArrayList arrayList = new ArrayList();
        while (this.buffer.bytesLeft() >= 3) {
            Cue nextSection = readNextSection(this.buffer, this.cueBuilder);
            if (nextSection != null) {
                arrayList.add(nextSection);
            }
        }
        return new PgsSubtitle(Collections.unmodifiableList(arrayList));
    }

    private void maybeInflateData(ParsableByteArray buffer) {
        if (buffer.bytesLeft() <= 0 || buffer.peekUnsignedByte() != 120) {
            return;
        }
        if (this.inflater == null) {
            this.inflater = new Inflater();
        }
        if (Util.inflate(buffer, this.inflatedBuffer, this.inflater)) {
            buffer.reset(this.inflatedBuffer.getData(), this.inflatedBuffer.limit());
        }
    }

    private static Cue readNextSection(ParsableByteArray buffer, CueBuilder cueBuilder) {
        int iLimit = buffer.limit();
        int unsignedByte = buffer.readUnsignedByte();
        int unsignedShort = buffer.readUnsignedShort();
        int position = buffer.getPosition() + unsignedShort;
        Cue cueBuild = null;
        if (position > iLimit) {
            buffer.setPosition(iLimit);
            return null;
        }
        if (unsignedByte == 128) {
            cueBuild = cueBuilder.build();
            cueBuilder.reset();
        } else {
            switch (unsignedByte) {
                case 20:
                    cueBuilder.parsePaletteSection(buffer, unsignedShort);
                    break;
                case 21:
                    cueBuilder.parseBitmapSection(buffer, unsignedShort);
                    break;
                case 22:
                    cueBuilder.parseIdentifierSection(buffer, unsignedShort);
                    break;
            }
        }
        buffer.setPosition(position);
        return cueBuild;
    }

    private static final class CueBuilder {
        private int bitmapHeight;
        private int bitmapWidth;
        private int bitmapX;
        private int bitmapY;
        private boolean colorsSet;
        private int planeHeight;
        private int planeWidth;
        private final ParsableByteArray bitmapData = new ParsableByteArray();
        private final int[] colors = new int[256];

        /* JADX INFO: Access modifiers changed from: private */
        public void parsePaletteSection(ParsableByteArray buffer, int sectionLength) {
            if (sectionLength % 5 != 2) {
                return;
            }
            buffer.skipBytes(2);
            Arrays.fill(this.colors, 0);
            int i = sectionLength / 5;
            for (int i2 = 0; i2 < i; i2++) {
                int unsignedByte = buffer.readUnsignedByte();
                int unsignedByte2 = buffer.readUnsignedByte();
                int unsignedByte3 = buffer.readUnsignedByte();
                int unsignedByte4 = buffer.readUnsignedByte();
                double d = unsignedByte2;
                double d2 = unsignedByte3 - 128;
                double d3 = unsignedByte4 - 128;
                this.colors[unsignedByte] = (Util.constrainValue((int) ((d - (0.34414d * d3)) - (d2 * 0.71414d)), 0, 255) << 8) | (buffer.readUnsignedByte() << 24) | (Util.constrainValue((int) ((1.402d * d2) + d), 0, 255) << 16) | Util.constrainValue((int) (d + (d3 * 1.772d)), 0, 255);
            }
            this.colorsSet = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void parseBitmapSection(ParsableByteArray buffer, int sectionLength) {
            int unsignedInt24;
            if (sectionLength < 4) {
                return;
            }
            buffer.skipBytes(3);
            int i = sectionLength - 4;
            if ((buffer.readUnsignedByte() & 128) != 0) {
                if (i < 7 || (unsignedInt24 = buffer.readUnsignedInt24()) < 4) {
                    return;
                }
                this.bitmapWidth = buffer.readUnsignedShort();
                this.bitmapHeight = buffer.readUnsignedShort();
                this.bitmapData.reset(unsignedInt24 - 4);
                i -= 7;
            }
            int position = this.bitmapData.getPosition();
            int iLimit = this.bitmapData.limit();
            if (position >= iLimit || i <= 0) {
                return;
            }
            int iMin = Math.min(i, iLimit - position);
            buffer.readBytes(this.bitmapData.getData(), position, iMin);
            this.bitmapData.setPosition(position + iMin);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void parseIdentifierSection(ParsableByteArray buffer, int sectionLength) {
            if (sectionLength < 19) {
                return;
            }
            this.planeWidth = buffer.readUnsignedShort();
            this.planeHeight = buffer.readUnsignedShort();
            buffer.skipBytes(11);
            this.bitmapX = buffer.readUnsignedShort();
            this.bitmapY = buffer.readUnsignedShort();
        }

        public Cue build() {
            int unsignedByte;
            if (this.planeWidth == 0 || this.planeHeight == 0 || this.bitmapWidth == 0 || this.bitmapHeight == 0 || this.bitmapData.limit() == 0 || this.bitmapData.getPosition() != this.bitmapData.limit() || !this.colorsSet) {
                return null;
            }
            this.bitmapData.setPosition(0);
            int i = this.bitmapWidth * this.bitmapHeight;
            int[] iArr = new int[i];
            int i2 = 0;
            while (i2 < i) {
                int unsignedByte2 = this.bitmapData.readUnsignedByte();
                if (unsignedByte2 != 0) {
                    unsignedByte = i2 + 1;
                    iArr[i2] = this.colors[unsignedByte2];
                } else {
                    int unsignedByte3 = this.bitmapData.readUnsignedByte();
                    if (unsignedByte3 != 0) {
                        unsignedByte = ((unsignedByte3 & 64) == 0 ? unsignedByte3 & 63 : ((unsignedByte3 & 63) << 8) | this.bitmapData.readUnsignedByte()) + i2;
                        Arrays.fill(iArr, i2, unsignedByte, (unsignedByte3 & 128) == 0 ? 0 : this.colors[this.bitmapData.readUnsignedByte()]);
                    }
                }
                i2 = unsignedByte;
            }
            return new Cue.Builder().setBitmap(Bitmap.createBitmap(iArr, this.bitmapWidth, this.bitmapHeight, Bitmap.Config.ARGB_8888)).setPosition(this.bitmapX / this.planeWidth).setPositionAnchor(0).setLine(this.bitmapY / this.planeHeight, 0).setLineAnchor(0).setSize(this.bitmapWidth / this.planeWidth).setBitmapHeight(this.bitmapHeight / this.planeHeight).build();
        }

        public void reset() {
            this.planeWidth = 0;
            this.planeHeight = 0;
            this.bitmapX = 0;
            this.bitmapY = 0;
            this.bitmapWidth = 0;
            this.bitmapHeight = 0;
            this.bitmapData.reset(0);
            this.colorsSet = false;
        }
    }
}
