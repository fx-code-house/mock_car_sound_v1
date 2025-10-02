package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.OpusUtil;
import com.google.android.exoplayer2.extractor.ogg.StreamReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Arrays;
import okio.Utf8;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* loaded from: classes.dex */
final class OpusReader extends StreamReader {
    private static final int OPUS_CODE = 1332770163;
    private static final byte[] OPUS_SIGNATURE = {79, 112, 117, 115, 72, 101, 97, 100};
    private boolean headerRead;

    OpusReader() {
    }

    public static boolean verifyBitstreamType(ParsableByteArray data) {
        int iBytesLeft = data.bytesLeft();
        byte[] bArr = OPUS_SIGNATURE;
        if (iBytesLeft < bArr.length) {
            return false;
        }
        byte[] bArr2 = new byte[bArr.length];
        data.readBytes(bArr2, 0, bArr.length);
        return Arrays.equals(bArr2, bArr);
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    protected void reset(boolean headerData) {
        super.reset(headerData);
        if (headerData) {
            this.headerRead = false;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    protected long preparePayload(ParsableByteArray packet) {
        return convertTimeToGranule(getPacketDurationUs(packet.getData()));
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    @EnsuresNonNullIf(expression = {"#3.format"}, result = false)
    protected boolean readHeaders(ParsableByteArray packet, long position, StreamReader.SetupData setupData) {
        if (!this.headerRead) {
            byte[] bArrCopyOf = Arrays.copyOf(packet.getData(), packet.limit());
            setupData.format = new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_OPUS).setChannelCount(OpusUtil.getChannelCount(bArrCopyOf)).setSampleRate(OpusUtil.SAMPLE_RATE).setInitializationData(OpusUtil.buildInitializationData(bArrCopyOf)).build();
            this.headerRead = true;
            return true;
        }
        Assertions.checkNotNull(setupData.format);
        boolean z = packet.readInt() == 1332770163;
        packet.setPosition(0);
        return z;
    }

    private long getPacketDurationUs(byte[] packet) {
        int i;
        int i2 = packet[0] & 255;
        int i3 = i2 & 3;
        if (i3 != 0) {
            i = 2;
            if (i3 != 1 && i3 != 2) {
                i = packet[1] & Utf8.REPLACEMENT_BYTE;
            }
        } else {
            i = 1;
        }
        int i4 = i2 >> 3;
        return i * (i4 >= 16 ? DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS << r1 : i4 >= 12 ? 10000 << (r1 & 1) : (i4 & 3) == 3 ? 60000 : 10000 << r1);
    }
}
