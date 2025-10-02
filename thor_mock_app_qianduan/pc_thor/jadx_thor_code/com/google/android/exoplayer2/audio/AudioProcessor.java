package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public interface AudioProcessor {
    public static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocateDirect(0).order(ByteOrder.nativeOrder());

    AudioFormat configure(AudioFormat inputAudioFormat) throws UnhandledAudioFormatException;

    void flush();

    ByteBuffer getOutput();

    boolean isActive();

    boolean isEnded();

    void queueEndOfStream();

    void queueInput(ByteBuffer inputBuffer);

    void reset();

    public static final class AudioFormat {
        public static final AudioFormat NOT_SET = new AudioFormat(-1, -1, -1);
        public final int bytesPerFrame;
        public final int channelCount;
        public final int encoding;
        public final int sampleRate;

        public AudioFormat(int sampleRate, int channelCount, int encoding) {
            this.sampleRate = sampleRate;
            this.channelCount = channelCount;
            this.encoding = encoding;
            this.bytesPerFrame = Util.isEncodingLinearPcm(encoding) ? Util.getPcmFrameSize(encoding, channelCount) : -1;
        }

        public String toString() {
            int i = this.sampleRate;
            int i2 = this.channelCount;
            return new StringBuilder(83).append("AudioFormat[sampleRate=").append(i).append(", channelCount=").append(i2).append(", encoding=").append(this.encoding).append(']').toString();
        }
    }

    public static final class UnhandledAudioFormatException extends Exception {
        /* JADX WARN: Illegal instructions before constructor call */
        public UnhandledAudioFormatException(AudioFormat inputAudioFormat) {
            String strValueOf = String.valueOf(inputAudioFormat);
            super(new StringBuilder(String.valueOf(strValueOf).length() + 18).append("Unhandled format: ").append(strValueOf).toString());
        }
    }
}
