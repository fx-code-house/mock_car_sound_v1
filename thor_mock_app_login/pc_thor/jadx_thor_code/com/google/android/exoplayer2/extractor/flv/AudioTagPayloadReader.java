package com.google.android.exoplayer2.extractor.flv;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.flv.TagPayloadReader;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Collections;

/* loaded from: classes.dex */
final class AudioTagPayloadReader extends TagPayloadReader {
    private static final int AAC_PACKET_TYPE_AAC_RAW = 1;
    private static final int AAC_PACKET_TYPE_SEQUENCE_HEADER = 0;
    private static final int AUDIO_FORMAT_AAC = 10;
    private static final int AUDIO_FORMAT_ALAW = 7;
    private static final int AUDIO_FORMAT_MP3 = 2;
    private static final int AUDIO_FORMAT_ULAW = 8;
    private static final int[] AUDIO_SAMPLING_RATE_TABLE = {5512, 11025, 22050, 44100};
    private int audioFormat;
    private boolean hasOutputFormat;
    private boolean hasParsedAudioDataHeader;

    @Override // com.google.android.exoplayer2.extractor.flv.TagPayloadReader
    public void seek() {
    }

    public AudioTagPayloadReader(TrackOutput output) {
        super(output);
    }

    @Override // com.google.android.exoplayer2.extractor.flv.TagPayloadReader
    protected boolean parseHeader(ParsableByteArray data) throws TagPayloadReader.UnsupportedFormatException {
        if (!this.hasParsedAudioDataHeader) {
            int unsignedByte = data.readUnsignedByte();
            int i = (unsignedByte >> 4) & 15;
            this.audioFormat = i;
            if (i == 2) {
                this.output.format(new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_MPEG).setChannelCount(1).setSampleRate(AUDIO_SAMPLING_RATE_TABLE[(unsignedByte >> 2) & 3]).build());
                this.hasOutputFormat = true;
            } else if (i == 7 || i == 8) {
                this.output.format(new Format.Builder().setSampleMimeType(i == 7 ? MimeTypes.AUDIO_ALAW : MimeTypes.AUDIO_MLAW).setChannelCount(1).setSampleRate(8000).build());
                this.hasOutputFormat = true;
            } else if (i != 10) {
                throw new TagPayloadReader.UnsupportedFormatException(new StringBuilder(39).append("Audio format not supported: ").append(this.audioFormat).toString());
            }
            this.hasParsedAudioDataHeader = true;
        } else {
            data.skipBytes(1);
        }
        return true;
    }

    @Override // com.google.android.exoplayer2.extractor.flv.TagPayloadReader
    protected boolean parsePayload(ParsableByteArray data, long timeUs) throws ParserException {
        if (this.audioFormat == 2) {
            int iBytesLeft = data.bytesLeft();
            this.output.sampleData(data, iBytesLeft);
            this.output.sampleMetadata(timeUs, 1, iBytesLeft, 0, null);
            return true;
        }
        int unsignedByte = data.readUnsignedByte();
        if (unsignedByte == 0 && !this.hasOutputFormat) {
            int iBytesLeft2 = data.bytesLeft();
            byte[] bArr = new byte[iBytesLeft2];
            data.readBytes(bArr, 0, iBytesLeft2);
            AacUtil.Config audioSpecificConfig = AacUtil.parseAudioSpecificConfig(bArr);
            this.output.format(new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_AAC).setCodecs(audioSpecificConfig.codecs).setChannelCount(audioSpecificConfig.channelCount).setSampleRate(audioSpecificConfig.sampleRateHz).setInitializationData(Collections.singletonList(bArr)).build());
            this.hasOutputFormat = true;
            return false;
        }
        if (this.audioFormat == 10 && unsignedByte != 1) {
            return false;
        }
        int iBytesLeft3 = data.bytesLeft();
        this.output.sampleData(data, iBytesLeft3);
        this.output.sampleMetadata(timeUs, 1, iBytesLeft3, 0, null);
        return true;
    }
}
