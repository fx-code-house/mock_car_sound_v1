package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Collections;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public final class LatmReader implements ElementaryStreamReader {
    private static final int INITIAL_BUFFER_SIZE = 1024;
    private static final int STATE_FINDING_SYNC_1 = 0;
    private static final int STATE_FINDING_SYNC_2 = 1;
    private static final int STATE_READING_HEADER = 2;
    private static final int STATE_READING_SAMPLE = 3;
    private static final int SYNC_BYTE_FIRST = 86;
    private static final int SYNC_BYTE_SECOND = 224;
    private int audioMuxVersionA;
    private int bytesRead;
    private int channelCount;
    private String codecs;
    private Format format;
    private String formatId;
    private int frameLengthType;
    private final String language;
    private int numSubframes;
    private long otherDataLenBits;
    private boolean otherDataPresent;
    private TrackOutput output;
    private final ParsableBitArray sampleBitArray;
    private final ParsableByteArray sampleDataBuffer;
    private long sampleDurationUs;
    private int sampleRateHz;
    private int sampleSize;
    private int secondHeaderByte;
    private int state;
    private boolean streamMuxRead;
    private long timeUs;

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public LatmReader(String language) {
        this.language = language;
        ParsableByteArray parsableByteArray = new ParsableByteArray(1024);
        this.sampleDataBuffer = parsableByteArray;
        this.sampleBitArray = new ParsableBitArray(parsableByteArray.getData());
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.state = 0;
        this.streamMuxRead = false;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        idGenerator.generateNewId();
        this.output = extractorOutput.track(idGenerator.getTrackId(), 1);
        this.formatId = idGenerator.getFormatId();
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long pesTimeUs, int flags) {
        this.timeUs = pesTimeUs;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray data) throws ParserException {
        Assertions.checkStateNotNull(this.output);
        while (data.bytesLeft() > 0) {
            int i = this.state;
            if (i != 0) {
                if (i == 1) {
                    int unsignedByte = data.readUnsignedByte();
                    if ((unsignedByte & 224) == 224) {
                        this.secondHeaderByte = unsignedByte;
                        this.state = 2;
                    } else if (unsignedByte != 86) {
                        this.state = 0;
                    }
                } else if (i == 2) {
                    int unsignedByte2 = ((this.secondHeaderByte & (-225)) << 8) | data.readUnsignedByte();
                    this.sampleSize = unsignedByte2;
                    if (unsignedByte2 > this.sampleDataBuffer.getData().length) {
                        resetBufferForSize(this.sampleSize);
                    }
                    this.bytesRead = 0;
                    this.state = 3;
                } else if (i == 3) {
                    int iMin = Math.min(data.bytesLeft(), this.sampleSize - this.bytesRead);
                    data.readBytes(this.sampleBitArray.data, this.bytesRead, iMin);
                    int i2 = this.bytesRead + iMin;
                    this.bytesRead = i2;
                    if (i2 == this.sampleSize) {
                        this.sampleBitArray.setPosition(0);
                        parseAudioMuxElement(this.sampleBitArray);
                        this.state = 0;
                    }
                } else {
                    throw new IllegalStateException();
                }
            } else if (data.readUnsignedByte() == 86) {
                this.state = 1;
            }
        }
    }

    @RequiresNonNull({"output"})
    private void parseAudioMuxElement(ParsableBitArray data) throws ParserException {
        if (!data.readBit()) {
            this.streamMuxRead = true;
            parseStreamMuxConfig(data);
        } else if (!this.streamMuxRead) {
            return;
        }
        if (this.audioMuxVersionA == 0) {
            if (this.numSubframes != 0) {
                throw ParserException.createForMalformedContainer(null, null);
            }
            parsePayloadMux(data, parsePayloadLengthInfo(data));
            if (this.otherDataPresent) {
                data.skipBits((int) this.otherDataLenBits);
                return;
            }
            return;
        }
        throw ParserException.createForMalformedContainer(null, null);
    }

    @RequiresNonNull({"output"})
    private void parseStreamMuxConfig(ParsableBitArray data) throws ParserException {
        boolean bit;
        int bits = data.readBits(1);
        int bits2 = bits == 1 ? data.readBits(1) : 0;
        this.audioMuxVersionA = bits2;
        if (bits2 == 0) {
            if (bits == 1) {
                latmGetValue(data);
            }
            if (!data.readBit()) {
                throw ParserException.createForMalformedContainer(null, null);
            }
            this.numSubframes = data.readBits(6);
            int bits3 = data.readBits(4);
            int bits4 = data.readBits(3);
            if (bits3 != 0 || bits4 != 0) {
                throw ParserException.createForMalformedContainer(null, null);
            }
            if (bits == 0) {
                int position = data.getPosition();
                int audioSpecificConfig = parseAudioSpecificConfig(data);
                data.setPosition(position);
                byte[] bArr = new byte[(audioSpecificConfig + 7) / 8];
                data.readBits(bArr, 0, audioSpecificConfig);
                Format formatBuild = new Format.Builder().setId(this.formatId).setSampleMimeType(MimeTypes.AUDIO_AAC).setCodecs(this.codecs).setChannelCount(this.channelCount).setSampleRate(this.sampleRateHz).setInitializationData(Collections.singletonList(bArr)).setLanguage(this.language).build();
                if (!formatBuild.equals(this.format)) {
                    this.format = formatBuild;
                    this.sampleDurationUs = 1024000000 / formatBuild.sampleRate;
                    this.output.format(formatBuild);
                }
            } else {
                data.skipBits(((int) latmGetValue(data)) - parseAudioSpecificConfig(data));
            }
            parseFrameLength(data);
            boolean bit2 = data.readBit();
            this.otherDataPresent = bit2;
            this.otherDataLenBits = 0L;
            if (bit2) {
                if (bits == 1) {
                    this.otherDataLenBits = latmGetValue(data);
                } else {
                    do {
                        bit = data.readBit();
                        this.otherDataLenBits = (this.otherDataLenBits << 8) + data.readBits(8);
                    } while (bit);
                }
            }
            if (data.readBit()) {
                data.skipBits(8);
                return;
            }
            return;
        }
        throw ParserException.createForMalformedContainer(null, null);
    }

    private void parseFrameLength(ParsableBitArray data) {
        int bits = data.readBits(3);
        this.frameLengthType = bits;
        if (bits == 0) {
            data.skipBits(8);
            return;
        }
        if (bits == 1) {
            data.skipBits(9);
            return;
        }
        if (bits == 3 || bits == 4 || bits == 5) {
            data.skipBits(6);
        } else {
            if (bits == 6 || bits == 7) {
                data.skipBits(1);
                return;
            }
            throw new IllegalStateException();
        }
    }

    private int parseAudioSpecificConfig(ParsableBitArray data) throws ParserException {
        int iBitsLeft = data.bitsLeft();
        AacUtil.Config audioSpecificConfig = AacUtil.parseAudioSpecificConfig(data, true);
        this.codecs = audioSpecificConfig.codecs;
        this.sampleRateHz = audioSpecificConfig.sampleRateHz;
        this.channelCount = audioSpecificConfig.channelCount;
        return iBitsLeft - data.bitsLeft();
    }

    private int parsePayloadLengthInfo(ParsableBitArray data) throws ParserException {
        int bits;
        if (this.frameLengthType != 0) {
            throw ParserException.createForMalformedContainer(null, null);
        }
        int i = 0;
        do {
            bits = data.readBits(8);
            i += bits;
        } while (bits == 255);
        return i;
    }

    @RequiresNonNull({"output"})
    private void parsePayloadMux(ParsableBitArray data, int muxLengthBytes) {
        int position = data.getPosition();
        if ((position & 7) == 0) {
            this.sampleDataBuffer.setPosition(position >> 3);
        } else {
            data.readBits(this.sampleDataBuffer.getData(), 0, muxLengthBytes * 8);
            this.sampleDataBuffer.setPosition(0);
        }
        this.output.sampleData(this.sampleDataBuffer, muxLengthBytes);
        this.output.sampleMetadata(this.timeUs, 1, muxLengthBytes, 0, null);
        this.timeUs += this.sampleDurationUs;
    }

    private void resetBufferForSize(int newSize) {
        this.sampleDataBuffer.reset(newSize);
        this.sampleBitArray.reset(this.sampleDataBuffer.getData());
    }

    private static long latmGetValue(ParsableBitArray data) {
        return data.readBits((data.readBits(2) + 1) * 8);
    }
}
