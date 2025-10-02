package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public final class PesReader implements TsPayloadReader {
    private static final int HEADER_SIZE = 9;
    private static final int MAX_HEADER_EXTENSION_SIZE = 10;
    private static final int PES_SCRATCH_SIZE = 10;
    private static final int STATE_FINDING_HEADER = 0;
    private static final int STATE_READING_BODY = 3;
    private static final int STATE_READING_HEADER = 1;
    private static final int STATE_READING_HEADER_EXTENSION = 2;
    private static final String TAG = "PesReader";
    private int bytesRead;
    private boolean dataAlignmentIndicator;
    private boolean dtsFlag;
    private int extendedHeaderLength;
    private int payloadSize;
    private boolean ptsFlag;
    private final ElementaryStreamReader reader;
    private boolean seenFirstDts;
    private long timeUs;
    private TimestampAdjuster timestampAdjuster;
    private final ParsableBitArray pesScratch = new ParsableBitArray(new byte[10]);
    private int state = 0;

    public PesReader(ElementaryStreamReader reader) {
        this.reader = reader;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader
    public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        this.timestampAdjuster = timestampAdjuster;
        this.reader.createTracks(extractorOutput, idGenerator);
    }

    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader
    public final void seek() {
        this.state = 0;
        this.bytesRead = 0;
        this.seenFirstDts = false;
        this.reader.seek();
    }

    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader
    public final void consume(ParsableByteArray data, int flags) throws ParserException {
        Assertions.checkStateNotNull(this.timestampAdjuster);
        if ((flags & 1) != 0) {
            int i = this.state;
            if (i != 0 && i != 1) {
                if (i == 2) {
                    Log.w(TAG, "Unexpected start indicator reading extended header");
                } else if (i == 3) {
                    int i2 = this.payloadSize;
                    if (i2 != -1) {
                        Log.w(TAG, new StringBuilder(59).append("Unexpected start indicator: expected ").append(i2).append(" more bytes").toString());
                    }
                    this.reader.packetFinished();
                } else {
                    throw new IllegalStateException();
                }
            }
            setState(1);
        }
        while (data.bytesLeft() > 0) {
            int i3 = this.state;
            if (i3 != 0) {
                if (i3 != 1) {
                    if (i3 == 2) {
                        if (continueRead(data, this.pesScratch.data, Math.min(10, this.extendedHeaderLength)) && continueRead(data, null, this.extendedHeaderLength)) {
                            parseHeaderExtension();
                            flags |= this.dataAlignmentIndicator ? 4 : 0;
                            this.reader.packetStarted(this.timeUs, flags);
                            setState(3);
                        }
                    } else if (i3 == 3) {
                        int iBytesLeft = data.bytesLeft();
                        int i4 = this.payloadSize;
                        int i5 = i4 != -1 ? iBytesLeft - i4 : 0;
                        if (i5 > 0) {
                            iBytesLeft -= i5;
                            data.setLimit(data.getPosition() + iBytesLeft);
                        }
                        this.reader.consume(data);
                        int i6 = this.payloadSize;
                        if (i6 != -1) {
                            int i7 = i6 - iBytesLeft;
                            this.payloadSize = i7;
                            if (i7 == 0) {
                                this.reader.packetFinished();
                                setState(1);
                            }
                        }
                    } else {
                        throw new IllegalStateException();
                    }
                } else if (continueRead(data, this.pesScratch.data, 9)) {
                    setState(parseHeader() ? 2 : 0);
                }
            } else {
                data.skipBytes(data.bytesLeft());
            }
        }
    }

    private void setState(int state) {
        this.state = state;
        this.bytesRead = 0;
    }

    private boolean continueRead(ParsableByteArray source, byte[] target, int targetLength) {
        int iMin = Math.min(source.bytesLeft(), targetLength - this.bytesRead);
        if (iMin <= 0) {
            return true;
        }
        if (target == null) {
            source.skipBytes(iMin);
        } else {
            source.readBytes(target, this.bytesRead, iMin);
        }
        int i = this.bytesRead + iMin;
        this.bytesRead = i;
        return i == targetLength;
    }

    private boolean parseHeader() {
        this.pesScratch.setPosition(0);
        int bits = this.pesScratch.readBits(24);
        if (bits != 1) {
            Log.w(TAG, new StringBuilder(41).append("Unexpected start code prefix: ").append(bits).toString());
            this.payloadSize = -1;
            return false;
        }
        this.pesScratch.skipBits(8);
        int bits2 = this.pesScratch.readBits(16);
        this.pesScratch.skipBits(5);
        this.dataAlignmentIndicator = this.pesScratch.readBit();
        this.pesScratch.skipBits(2);
        this.ptsFlag = this.pesScratch.readBit();
        this.dtsFlag = this.pesScratch.readBit();
        this.pesScratch.skipBits(6);
        int bits3 = this.pesScratch.readBits(8);
        this.extendedHeaderLength = bits3;
        if (bits2 == 0) {
            this.payloadSize = -1;
        } else {
            int i = ((bits2 + 6) - 9) - bits3;
            this.payloadSize = i;
            if (i < 0) {
                Log.w(TAG, new StringBuilder(47).append("Found negative packet payload size: ").append(i).toString());
                this.payloadSize = -1;
            }
        }
        return true;
    }

    @RequiresNonNull({"timestampAdjuster"})
    private void parseHeaderExtension() {
        this.pesScratch.setPosition(0);
        this.timeUs = C.TIME_UNSET;
        if (this.ptsFlag) {
            this.pesScratch.skipBits(4);
            this.pesScratch.skipBits(1);
            this.pesScratch.skipBits(1);
            long bits = (this.pesScratch.readBits(3) << 30) | (this.pesScratch.readBits(15) << 15) | this.pesScratch.readBits(15);
            this.pesScratch.skipBits(1);
            if (!this.seenFirstDts && this.dtsFlag) {
                this.pesScratch.skipBits(4);
                this.pesScratch.skipBits(1);
                this.pesScratch.skipBits(1);
                this.pesScratch.skipBits(1);
                this.timestampAdjuster.adjustTsTimestamp((this.pesScratch.readBits(3) << 30) | (this.pesScratch.readBits(15) << 15) | this.pesScratch.readBits(15));
                this.seenFirstDts = true;
            }
            this.timeUs = this.timestampAdjuster.adjustTsTimestamp(bits);
        }
    }
}
